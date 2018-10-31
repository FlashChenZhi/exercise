package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.asrs.schedule.AsWorkOperationSCHParams.*;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id03;
import jp.co.daifuku.wms.asrs.communication.as21.SystemTextTransmission;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerAlterKey;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerFinder;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.MachineAlterKey;
import jp.co.daifuku.wms.base.dbhandler.MachineHandler;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;

/**
 * AS/RS 作業開始、作業終了、強制終了のスケジュール処理を行います。
 *
 * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */

public class AsWorkOperationSCH
        extends AbstractAsrsSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ<BR>
     * データベース接続用コネクションをセットする。<BR>
     * トランザクションのコミット、ロールバックは呼び出し元で制御すること。<BR>
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public AsWorkOperationSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
        setMessage(null);
    }


    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {

        // ハンドラインスタンス生成
        AbstractDBFinder finder = null;
        try
        {
            finder = new GroupControllerFinder(getConnection());
            finder.open(true);
            // 検索処理実行
            // 取得件数に応じてメッセージを設定
            if (!canLowerDisplay(finder.search(createSearchKey(p))))
            {
                return new ArrayList<Params>();
            }

            // エンティティを画面表示用にパラメータクラスにセットし返す
            return getDisplayData(finder);
        }
        finally
        {
            // 検索で使用したFinderをcloseする
            closeFinder(finder);
        }
    }

    /**<jp>
     * パラメータチェック処理を行ないます。
     * 作業開始のチェック <BR>
     * バッチ処理中かどうかをチェックします。 <BR>
     * 日次更新、引当クリア中は作業開始することができません。 <BR>
     * 作業終了のチェック <BR>
     * データ保持指定時、該当コントローラーにて残作業有りの場合、エラー通知を行います。 <BR>
     * @param p 条件チェックを行う情報を持つ<CODE>AsrsInParameter</CODE>クラスのインスタンス。 <BR>
     *         <CODE>AsrsInParameter</CODE>インスタンス以外を指定された場合CommonExceptionをスローします。 <BR>
     * @return パラメータに異常が無い場合はtrue、異常がある場合はfalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     </jp>*/
    public boolean check(ScheduleParams p)
            throws CommonException
    {

        if (p.getString(PROCESS_TYPE).equals(AsrsInParameter.PROCESS_TYPE_WORK_START))
        {
            // 作業開始の場合、日次処理中はエラーとする。
            // 日次更新処理中のチェック
            if (isDailyUpdate())
            {
                return false;
            }
            // 搬送データクリアチェック
            if (isAllocationClear())
            {
                return false;
            }
        }
        else if (p.getString(PROCESS_TYPE).equals(AsrsInParameter.PROCESS_TYPE_WORK_END))
        {
            // 作業終了で通常終了の場合、残作業件数のチェックを行います。
            if (remainderWorksCount(p.getString(CONTROLLER_NO)) > 0)
            {
                // 6023082 = AGC-No.{0}は、残作業があるので作業終了する事ができません。
                setMessage(WmsMessageFormatter.format(6023082, p.getString(CONTROLLER_NO)));
                return false;
            }
        }
        else
        {
            // 強制終了の場合、チェックなし。
        }

        return true;
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、作業開始、終了、強制終了スケジュールを開始します。<BR>
     * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
     * 詳しい動作はクラス説明の項を参照してください。<BR>
     * @param ps 設定内容を持つ<CODE>AsSystemScheduleParameter</CODE>クラスのインスタンスの配列。 <BR>
     *         AsSystemScheduleParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
     *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
     * @return 正常終了した場合はtrue、それ以外はfalse
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    @Override
    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param startParams 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {

        for (int lc = 0; lc < ps.length; lc++)
        {

            if (ps[lc].getString(PROCESS_TYPE).equals(AsrsInParameter.PROCESS_TYPE_WORK_START))
            {
                // 作業開始
                if (!this.start(ps[lc].getString(CONTROLLER_NO)))
                {
                    return false;
                }
            }
            else if (ps[lc].getString(PROCESS_TYPE).equals(AsrsInParameter.PROCESS_TYPE_WORK_END))
            {
                // 通常作業終了
                if (!this.normalend(ps[lc].getString(CONTROLLER_NO)))
                {
                    return false;
                }
            }
            else if (ps[lc].getString(PROCESS_TYPE).equals(AsrsInParameter.PROCESS_TYPE_WORK_END_DATAKEEP))
            {
                // データ保持作業終了
                if (!this.endDatakeep(ps[lc].getString(CONTROLLER_NO)))
                {
                    return false;
                }
            }
            else if (ps[lc].getString(PROCESS_TYPE).equals(AsrsInParameter.PROCESS_TYPE_WORK_ONLYEND))
            {
                // 単独作業終了(強制終了設定)
                if (!this.onlyend(ps[lc].getString(CONTROLLER_NO)))
                {
                    return false;
                }
            }
        }

        return true;
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * 検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        GroupControllerSearchKey searchKey = new GroupControllerSearchKey();
        // AGC No.順に表示
        searchKey.setControllerNoOrder(true);

        return searchKey;
    }

    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder)
            throws CommonException
    {
        // 最大表示件数分検索結果を取得する
        GroupController[] entities = (GroupController[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (GroupController ent : entities)
        {
            Params param = new Params();

            // 返却データをセットする
            // 状態フラグ
            param.set(STATUS_FLAG, DisplayText.getText("GROUPCONTROLLER", "STATUS", ent.getStatusFlag()));
            // AGC No.
            param.set(CONTROLLER_NO, ent.getControllerNo());
            // 残作業件数
            param.set(WORK_COUNT, remainderWorksCount(ent.getControllerNo()));
            // システムフラグ
            param.set(SYSTEM_STATUS, ent.getStatusFlag());
            result.add(param);
        }

        return result;
    }

    /**
     * 指定されたAGC-Noの残作業件数を取得するメソッドです。 <BR>
     * @param wAgcNo 対象とするAgcNoをセットして下さい。 <BR>
     * @return int 指定されたAgcでの残作業件数を通知します。 <BR>
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected int remainderWorksCount(String wAgcNo)
            throws CommonException

    {
        int rCount = 0;
        // 残作業件数
        ResultSet wResultset = null;
        DefaultDDBHandler ddbHandler = null;

        String wSqlString = "SELECT COUNT(DISTINCT DNCARRYINFO.CARRY_KEY) COUNT ";
        // 2012/05/17 MODIFY START
        wSqlString += " FROM DNCARRYINFO, DMAISLE ,DMSTATION ";
        wSqlString += " WHERE ( DMAISLE.CONTROLLER_NO = ";
        wSqlString += DBFormat.format(wAgcNo);
        wSqlString += " AND DNCARRYINFO.AISLE_STATION_NO = DMAISLE.STATION_NO )";
        wSqlString += " OR ";
        wSqlString += " ( DMSTATION.CONTROLLER_NO = ";
        wSqlString += DBFormat.format(wAgcNo);
        wSqlString += " AND ( DNCARRYINFO.SOURCE_STATION_NO = DMSTATION.STATION_NO ";
        wSqlString += "     OR DNCARRYINFO.DEST_STATION_NO = DMSTATION.STATION_NO) )";
        // 2012/05/17 MODIFY END

        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
            // SQLの実行
            ddbHandler.execute(wSqlString);

            wResultset = ddbHandler.getResultSet();

            // フォーマット形式のセット
            while (wResultset.next())
            {
                // 取得したデータを返します
                rCount = wResultset.getInt(1);
                break;
            }

            return (rCount);
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.writeTraceOnly(e, DbDateUtil.class.getName());
            throw new ReadWriteException(e);
        }
        finally
        {
            if (ddbHandler != null)
            {
                ddbHandler.close();
            }
        }
    }

    /**
     * 作業開始処理を行います。
     * @param pAgcNo コントローラーNo.
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean start(String pAgcNo)
            throws CommonException
    {
        try
        {
            // 作業開始
            SystemTextTransmission.id01send(pAgcNo);
            // 作業開始要求しました
            setMessage("6011005");
            return true;
        }
        // rmiregistry、msgserverが起動されていない : 送信できません。
        catch (ConnectException ex)
        {
            // メッセージログ・サーバに接続されていないため、送信できません。
            setMessage("6017003");
            return false;
        }
        // rmiregistry、msgserverが起動されているがAGCと未接続 : 送信できません。
        catch (NotBoundException ex)
        {
            // メッセージログ・サーバには接続していますが、AWC-AGC間は未接続のため、送信できません。
            setMessage("6017004");
            return false;
        }
        // rmiは起動しているが他の理由で送信できない場合はLogを落とす。
        catch (Exception e)
        {
            // 予期しないエラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006001, e), (String)this.getClass().getName());
            // 致命的なエラーが発生しました。
            setMessage("6017011");
            return true;
        }
    }

    /**
     * 通常作業終了処理を行います。
     * @param pAgcNo コントローラーNo.
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected boolean normalend(String pAgcNo)
            throws CommonException
    {
        try
        {
            GroupControllerHandler wGCHandler = new GroupControllerHandler(getConnection());
            GroupControllerAlterKey wGCAlterKey = new GroupControllerAlterKey();

            // 作業終了
            // GroupControllerのStatusに終了予約をセットする。
            wGCAlterKey.clear();
            // コントローラーNoと一致
            wGCAlterKey.setControllerNo(pAgcNo);
            // 状態に終了予約をセットします。
            wGCAlterKey.updateStatusFlag(GroupController.GC_STATUS_END_RESERVATION);

            wGCHandler.modify(wGCAlterKey);

            // 作業終了要求電文
            SystemTextTransmission.id03send(pAgcNo, As21Id03.GENERAL_END);

            // 作業終了要求しました。
            setMessage("6011006");
            return true;
        }
        // rmiregistry、msgserverが起動されていない : 送信できません。
        catch (ConnectException ex)
        {
            // メッセージログ・サーバに接続されていないため、送信できません。
            setMessage("6017003");
            return false;
        }
        // rmiregistry、msgserverが起動されているがAGCと未接続 : 送信できません。
        catch (NotBoundException ex)
        {
            // メッセージログ・サーバには接続していますが、AWC-AGC間は未接続のため、送信できません。
            setMessage("6017004");
            return false;
        }
        // rmiは起動しているが他の理由で送信できない場合はLogを落とす。
        catch (Exception ex)
        {
            // 予期しないエラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006001, ex), (String)this.getClass().getName());
            // 致命的なエラーが発生しました。
            setMessage("6017011");
            return false;
        }
    }

    /**
     * 作業終了:データ保持処理を行います。
     * @param pAgcNo コントローラーNo.
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean endDatakeep(String pAgcNo)
            throws CommonException
    {
        try
        {
            GroupControllerHandler wGCHandler = new GroupControllerHandler(getConnection());
            GroupControllerAlterKey wGCAlterKey = new GroupControllerAlterKey();

            // 作業終了
            // GroupControllerのStatusに終了予約をセットする。
            wGCAlterKey.clear();
            // コントローラーNoと一致
            wGCAlterKey.setControllerNo(pAgcNo);
            // 状態に終了予約をセットします。
            wGCAlterKey.updateStatusFlag(GroupController.GC_STATUS_END_RESERVATION);

            wGCHandler.modify(wGCAlterKey);

            // 作業終了要求電文
            SystemTextTransmission.id03send(pAgcNo, As21Id03.EXTRAORDINARY_END_ONE);

            // 作業終了要求しました。
            setMessage("6011006");
            return true;
        }
        // rmiregistry、msgserverが起動されていない : 送信できません。
        catch (ConnectException ex)
        {
            // メッセージログ・サーバに接続されていないため、送信できません。
            setMessage("6017003");
            return false;
        }
        // rmiregistry、msgserverが起動されているがAGCと未接続 : 送信できません。
        catch (NotBoundException ex)
        {
            // メッセージログ・サーバには接続していますが、AWC-AGC間は未接続のため、送信できません。
            setMessage("6017004");
            return false;
        }
        // rmiは起動しているが他の理由で送信できない場合はLogを落とす。
        catch (Exception ex)
        {
            // 予期しないエラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006001, ex), (String)this.getClass().getName());
            // 致命的なエラーが発生しました。
            setMessage("6017011");
            return false;
        }
    }

    /**
     * 単独作業終了処理を行います。
     * @param pAgcNo コントローラーNo.
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean onlyend(String pAgcNo)
            throws CommonException
    {
        try
        {
            GroupControllerHandler wGCHandler = new GroupControllerHandler(getConnection());
            GroupControllerAlterKey wGCAlterKey = new GroupControllerAlterKey();

            //<jp> 単独作業終了</jp>
            //<jp> GroupControllerのStatusにオフラインをセットする。</jp>
            wGCAlterKey.clear();
            // コントローラーNoと一致
            wGCAlterKey.setControllerNo(pAgcNo);
            // 状態に終了予約をセットします。
            wGCAlterKey.updateStatusFlag(GroupController.GC_STATUS_OFFLINE);

            wGCHandler.modify(wGCAlterKey);
            // ASRS機器状態を更新する
            setStatusDetach(pAgcNo);

            //<jp> 単独オフラインを行いました。</jp>
            //<en> Set off-line solely.</en>
            setMessage("6011007");
            return true;
        }
        catch (NotFoundException ex)
        {
            throw new InvalidDefineException("Group Controller not found");
        }
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    /**
     * ASRS機器状態を未接続に変更します。
     * <DIR>
     *     ASRS機器情報を[未接続]に更新します。
     * <DIR>
     * @param pAgcNo コントローラーNo.
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    private void setStatusDetach(String pAgcNo)
            throws CommonException
    {
        // ASRS機器情報のハンドラを生成
        MachineHandler wMaHandler = new MachineHandler(getConnection());
        MachineAlterKey wMaAlterKey = new MachineAlterKey();

        wMaAlterKey.setControllerNo(pAgcNo);
        wMaAlterKey.updateStatusFlag(null);

        // 更新処理を実行
        wMaHandler.modify(wMaAlterKey);
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Revision: 8053 $,$Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $";
    }
}
