// $Id: PrinterModifySCH.java,v 1.2 2009/02/24 02:52:44 ose Exp $
package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.system.schedule.PrinterModifySCHParams.PRINTER_NAME;
import static jp.co.daifuku.wms.system.schedule.PrinterModifySCHParams.TERMINAL;
import static jp.co.daifuku.wms.system.schedule.PrinterModifySCHParams.TERMINAL_NAME;
import static jp.co.daifuku.wms.system.schedule.PrinterModifySCHParams.HDN_UPDATA_DATE;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalAlterKey;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalFinder;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalHandler;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalSearchKey;
import jp.co.daifuku.wms.base.entity.Com_terminal;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.db.SysDate;

/**
 * プリンタ代替設定登録のスケジュール処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:52:44 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class PrinterModifySCH
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
    public PrinterModifySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
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
            finder = new Com_terminalFinder(getConnection());
            finder.open(true);
            // 検索処理実行
            // 取得件数に応じてメッセージを設定
            int end = finder.search(createSearchKey(p));
            if (!canLowerDisplay(end))
            {
                return new ArrayList<Params>();
            }

            // エンティティを画面表示用にパラメータクラスにセットし返す
            return getDisplayData(finder, end);
        }
        finally
        {
            // 検索で使用したFinderをcloseする
            closeFinder(finder);
        }
    }

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
        // 処理前のチェックを行う。
        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }

        // ロックを行う
        if (!lock(ps))
        {
            return false;
        }


        // ハンドラおよびエンティティのインスタンスを生成
        Com_terminalHandler allocHandler = new Com_terminalHandler(getConnection());
        Com_terminalAlterKey alterKey = new Com_terminalAlterKey();

        for (int i = 0; i < ps.length; i++)
        {
            // 更新条件の設定
            alterKey.clear();
            // 端末No.
            alterKey.setTerminalnumber(ps[i].getString(PrinterModifySCHParams.TERMINAL));

            // 更新値の設定
            // プリンタ名
            alterKey.updatePrintername(ps[i].getString(PrinterModifySCHParams.PRINTER_NAME));
            // 更新日時
            alterKey.updateUpdateDate(new SysDate());
            // 更新ユーザ
            alterKey.updateUpdateUser(getWmsUserInfo().getUserId());
            // 更新端末IP
            alterKey.updateUpdateTerminal(getWmsUserInfo().getTerminalAddress());
            // 更新区分(修正)
            alterKey.updateUpdateKind(EmConstants.UPDATE_KIND_UPDATE);
            // 更新処理名
            alterKey.updateUpdateProcess(getClass().getSimpleName());

            allocHandler.modify(alterKey);
        }

        // 修正登録しました
        setMessage("6001011");

        return true;
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
     * パラメータでわたされた、端末No.をキーに、COM_TERMINALをロックします。<BR>
     * 最終更新日時をキー<BR>
     * また、他端末から更新・ロックされていないかをチェックします。<BR>
     * 
     * @param params 端末No.、最終更新日時を含むパラメータクラス
     * @return true:ロック成功、false:ロック失敗
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean lock(ScheduleParams[] params)
            throws CommonException
    {
        Com_terminalHandler handler = new Com_terminalHandler(getConnection());
        Com_terminalSearchKey key = new Com_terminalSearchKey();

        int rowNo = 0;
        try
        {
            for (ScheduleParams inparam : params)
            {
                key.clear();
                key.setTerminalnumber(inparam.getString(TERMINAL));
                key.setUpdateDate(inparam.getDate(HDN_UPDATA_DATE));

                Com_terminal com = (Com_terminal)handler.findPrimaryForUpdate(key);

                if (com == null)
                {
                    // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                    setMessage(WmsMessageFormatter.format(6023015, inparam.getRowIndex()));
                    setErrorRowIndex(inparam.getRowIndex());
                    return false;
                }
                rowNo++;
            }
        }
        catch (LockTimeOutException e)
        {
            // 6023014=No.{0} 他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, params[rowNo].getRowIndex()));
            setErrorRowIndex(params[rowNo].getRowIndex());
            return false;
        }
        return true;
    }

    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder, int end)
            throws ReadWriteException
    {
        // 最大表示件数分検索結果を取得する
        Com_terminal[] entities = (Com_terminal[])finder.getEntities(0, end);
        List<Params> result = new ArrayList<Params>();

        for (Com_terminal ent : entities)
        {
            Params param = new Params();

            // 端末No.
            param.set(TERMINAL, ent.getTerminalnumber());
            // 端末名称
            param.set(TERMINAL_NAME, ent.getTerminalname());
            // プリンタ名
            param.set(PRINTER_NAME, ent.getPrintername());
            // 最終更新日時
            param.set(HDN_UPDATA_DATE, ent.getUpdateDate());

            result.add(param);
        }

        return result;
    }


    /**
     * 検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        Com_terminalSearchKey searchKey = new Com_terminalSearchKey();

        /* 検索条件の指定 */

        /* 取得項目と集約条件の指定 */
        // 端末No.
        searchKey.setTerminalnumberCollect();
        // 端末名称
        searchKey.setTerminalnameCollect();
        // プリンタ名
        searchKey.setPrinternameCollect();
        // 最終更新日時
        searchKey.setUpdateDateCollect();


        /* ソート順の指定 */
        // 端末No.
        searchKey.setTerminalnumberOrder(true);
        // 端末名称
        searchKey.setTerminalnameOrder(true);
        //プリンタ名
        searchKey.setPrinternameOrder(true);

        return searchKey;
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
