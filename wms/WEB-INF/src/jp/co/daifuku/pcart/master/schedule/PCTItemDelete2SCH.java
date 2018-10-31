// $Id: PCTItemDelete2SCH.java 6900 2010-01-25 11:21:11Z kumano $
package jp.co.daifuku.pcart.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.schedule.PCTItemDelete2SCHParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.pcart.base.util.DBUtil;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTSystem;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 商品マスタ削除(LOAD)のスケジュール処理を行います。
 *
 * @version $Revision: 6900 $, $Date:: 2010-01-25 20:21:11 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class PCTItemDelete2SCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public PCTItemDelete2SCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
     * PCTシステム情報を検索し、商品マスタ取込みフラグを確認します。
     * 
     * @param searchParam
     *            本メソッドでは使用しないのでnullをセットしてください。
     * @return 結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     * @throws NoPrimaryException NoPrimaryException 一意項目が重複している場合にスローされます。
     */
    public PCTMasterOutParameter initFind(Parameter searchParam)
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException
    {
        // 出力パラメータ
        PCTMasterOutParameter outParam = new PCTMasterOutParameter();

        // PCTシステム情報ハンドラ類インスタンス生成
        PCTSystemHandler wHandler = new PCTSystemHandler(getConnection());
        PCTSystemSearchKey wSKey = new PCTSystemSearchKey();
        PCTSystem pctSystem = (PCTSystem)wHandler.findPrimary(wSKey);

        if (pctSystem != null)
        {
            if (PCTSystem.PCTMASTER_LOAD_FLAG_SAVE.equals(pctSystem.getPctmasterLoadFlag()))
            {
                // 6024069=セーブ処理中のため作業できません。
                setMessage(WmsMessageFormatter.format(6024069));
                outParam.setPctMasterLoadFlag(PCTSystem.PCTMASTER_LOAD_FLAG_SAVE);
                return outParam;
            }

            if (PCTSystem.PCTMASTER_LOAD_FLAG_LOAD.equals(pctSystem.getPctmasterLoadFlag()))
            {
                // 6024068=ロード処理中のため作業できません。
                setMessage(WmsMessageFormatter.format(6024068));
                outParam.setPctMasterLoadFlag(PCTSystem.PCTMASTER_LOAD_FLAG_LOAD);
                return outParam;
            }
        }
        return null;
    }

    /**
     * ロード処理前のチェックを行います。<BR>
     * <CODE>checkParam</CODE>で指定されたパラメータにセットされた内容に従い、
     * パラメータ入力内容チェック処理を行います。<BR>
     * 必要に応じて、各継承クラスで実装してください。
     * @param checkParam 入力チェックを行う内容が含まれたパラメータクラス
     * @return true：入力内容が正常な場合  false：そうでない場合
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean check(Parameter checkParam)
            throws CommonException
    {
        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }

        // 取り込み中チェック
        if (isLoadData())
        {
            return false;
        }

        // RFT管理情報ハンドラ類インスタンス生成
        RftHandler wHandler = new RftHandler(getConnection());
        RftSearchKey wSKey = new RftSearchKey();

        // 検索条件セット
        wSKey.setTerminalType(Rft.TERMINAL_TYPE_PCART);
        wSKey.setStatusFlag(Rft.RFT_STATUS_FLAG_START);

        // Pカート起動中チェック
        if (wHandler.count(wSKey) > 0)
        {
            // 6023614=Pカート起動中のため開始できません。
            setMessage(WmsMessageFormatter.format(6023614));
            return false;
        }

        return true;
    }

    /**
     * スケジュールを開始します。入力チェックを行い、EnvironmentInformationへ書き込みます。
     * 正しく書き込めた場合はTrueを、書き込めなかった場合はメッセージをセットしFalseを返します。
     * @param startParam 画面で入力された内容が含まれたパラメータクラス
     * @return 正しく書き込めた場合はTrueを、書き込めなかった場合はFalseを返します。
     * @throws CommonException 発生した全ての例外を通知します。
     */
    public boolean startSCH(PCTItemDelete2SCHParams startParam)
            throws CommonException
    {
        boolean pctLoadFlag = false;
        boolean loadFlag = false;
        boolean errorFlag = true;
        DefaultDDBHandler ddbHandler = null;
        try
        {
            // PCT商品マスタ取込フラグをLOAD中に更新
            if (updatePCTMasterLoadFlag(PCTSystem.PCTMASTER_LOAD_FLAG_LOAD))
            {
                doCommit(this.getClass());
            }
            else
            {
                return false;
            }
            pctLoadFlag = true;

            // 取込フラグを取込中に更新
            if (doLoadStart())
            {
                doCommit(this.getClass());
            }
            else
            {
                return false;
            }
            loadFlag = true;

            // ダイレクトDBハンドラ
            ddbHandler = new DefaultDDBHandler(getConnection());

            // テーブル削除
            String sql = ("DROP TABLE DMPCTITEM");
            // SQLの実行
            ddbHandler.execute(sql);

            // ロード処理実行
            if (DBUtil.startImp(startParam.getString(DATA_FILE_FOLDER) + startParam.getString(DATA_FILE_NAME)))
            {
                // ロード処理後テーブル存在チェック
                sql = ("SELECT COUNT(*) CNT FROM USER_TABLES WHERE TABLE_NAME = 'DMPCTITEM'");
                ddbHandler.execute(sql);
                Entity[] countEntity = ddbHandler.getEntities(1, new PCTItem());
                int cnt = countEntity[0].getBigDecimal(new FieldName("", "CNT")).intValue();
                if (cnt == 0)
                {
                    // 6127001=データベース書き込みに失敗しました。
                    setMessage(WmsMessageFormatter.format(6127001));
                    return false;
                }
                // ロード後データの整合性チェック
                if (checkLoadData())
                {
                    // リサイクルビン削除
                    sql = ("PURGE USER_RECYCLEBIN");
                    ddbHandler.execute(sql);
                }
                else
                {
                    // 6127004=整合性チェックでエラーが発生しました。ログを参照してください。
                    setMessage(WmsMessageFormatter.format(6127004, startParam.getString(DATA_FILE_NAME)));
                    // 6026124=予定データで使用している商品コードがLOADデータ内に存在しません。LOADデータを確認してください。
                    RmiMsgLogClient.write(WmsMessageFormatter.format(6026124), getClass().getSimpleName());
                    return false;
                }
            }
            else
            {
                // 6127001=データベース書き込みに失敗しました。
                setMessage(WmsMessageFormatter.format(6127001));
                return false;
            }

            errorFlag = false;

            // 6001026=ロード処理が完了しました。
            setMessage(WmsMessageFormatter.format(6001026));
            return true;
        }
        catch (CommonException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            // 予期しないエラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006001, ex), (String)this.getClass().getName());
            // 致命的なエラーが発生しました。
            setMessage("6017011");
            return false;
        }
        finally
        {
            if (ddbHandler != null)
            {
                ddbHandler.close();
            }
            // 正常に処理が行われなかった場合はrollback
            if (errorFlag)
            {
                // ロード失敗処理
                loadErrorProc();

                doRollBack(this.getClass());
            }

            // PCT商品マスタ取込フラグがこのクラスから更新されたか判断
            if (pctLoadFlag)
            {
                // PCT商品マスタ取込フラグを停止中に更新
                if (updatePCTMasterLoadFlag(PCTSystem.PCTMASTER_LOAD_FLAG_STOP))
                {
                    doCommit(this.getClass());
                }
                else
                {
                    return false;
                }
            }
            // データ取込中フラグがこのクラスから更新されたか判断
            if (loadFlag)
            {
                // 取込フラグを停止中に更新
                if (doLoadEnd())
                {
                    doCommit(this.getClass());
                }
                else
                {
                    return false;
                }
            }
        }
    }

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
     * ロード失敗時の処理を行ないます。
     * 一時ファイルを元に戻します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void loadErrorProc()
            throws CommonException
    {
        // ダイレクトDBハンドラ
        DefaultDDBHandler ddbHandler = null;

        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
            // SQL実行
            String sql = ("SELECT COUNT(*) CNT FROM USER_TABLES WHERE TABLE_NAME = 'DMPCTITEM'");
            ddbHandler.execute(sql);
            Entity[] countEntity = ddbHandler.getEntities(1, new PCTItem());
            int cnt = countEntity[0].getBigDecimal(new FieldName("", "CNT")).intValue();
            if (cnt > 0)
            {
                sql = ("DROP TABLE DMPCTITEM PURGE");
                ddbHandler.execute(sql);
            }
            sql = ("FLASHBACK TABLE DMPCTITEM TO BEFORE DROP");
            ddbHandler.execute(sql);
            sql = ("PURGE USER_RECYCLEBIN");
            ddbHandler.execute(sql);
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
     * ロードされたPCT商品マスタの内容が正しいかチェックを行います。
     * @return true：正常な場合  false：そうでない場合
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkLoadData()
            throws CommonException
    {
        // SQL作成
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT COUNT(*) CNT ");
        sql.append("FROM ( ");
        sql.append("      SELECT DMPCTITEM.ITEM_CODE ");
        sql.append("      FROM DMPCTITEM, ");
        sql.append("           (SELECT CONSIGNOR_CODE, ");
        sql.append("                   ITEM_CODE, ");
        sql.append("                   LOT_ENTERING_QTY ");
        sql.append("            FROM DNPCTRETPLAN ");
        sql.append("            WHERE STATUS_FLAG != '").append(PCTRetPlan.STATUS_FLAG_DELETE).append("'");
        sql.append("            GROUP BY CONSIGNOR_CODE, ");
        sql.append("                     ITEM_CODE, ");
        sql.append("                     LOT_ENTERING_QTY) RETPLAN ");
        sql.append("      WHERE DMPCTITEM.CONSIGNOR_CODE(+) = RETPLAN.CONSIGNOR_CODE ");
        sql.append("        AND DMPCTITEM.ITEM_CODE(+) = RETPLAN.ITEM_CODE ");
        sql.append("        AND DMPCTITEM.LOT_ENTERING_QTY(+) = RETPLAN.LOT_ENTERING_QTY) a ");
        sql.append("WHERE a.ITEM_CODE IS NULL");

        // SQLの実行
        // ダイレクトDBハンドラ
        DefaultDDBHandler ddbHandler = null;
        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
            ddbHandler.execute(String.valueOf(sql));
            Entity[] countEntity = ddbHandler.getEntities(1, new PCTItem());
            int cnt = countEntity[0].getBigDecimal(new FieldName("", "CNT")).intValue();

            // マスタに存在しない商品が存在した場合、エラー
            if (cnt > 0)
            {
                return false;
            }

            return true;
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
     * 報告データ作成中フラグを停止中に更新します。<BR>
     * 正常に更新できた場合はtrue、更新できなかった場合はfalseを返します。<BR>
     * @return true：更新成功 false：更新失敗
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean updatePCTMasterLoadFlag(String flag)
            throws CommonException
    {
        // PCTシステム情報ハンドラ類インスタンス生成
        PCTSystemHandler wHandler = new PCTSystemHandler(getConnection());
        PCTSystemAlterKey wAKey = new PCTSystemAlterKey();

        try
        {
            // PCT商品マスタ取込フラグを更新
            wAKey.updatePctmasterLoadFlag(flag);
            wAKey.updateLastUpdatePname(this.getClass().getSimpleName());

            wHandler.modify(wAKey);

            return true;
        }
        catch (NotFoundException e)
        {
            return false;
        }
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
