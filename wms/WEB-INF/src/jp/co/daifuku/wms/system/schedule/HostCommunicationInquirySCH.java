// $Id: Sch_ja.java 117 2008-10-06 11:00:54Z admin $
package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.system.schedule.HostCommunicationInquirySCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ExchangeHistoryFinder;
import jp.co.daifuku.wms.base.dbhandler.ExchangeHistorySearchKey;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.ExchangeHistory;
import jp.co.daifuku.wms.base.entity.LoadErrorInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 上位通信問合せのスケジュール処理を行います。
 *
 * @version $Revision: 117 $, $Date:: 2008-10-06 20:00:54 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class HostCommunicationInquirySCH
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
    public HostCommunicationInquirySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
            finder = new ExchangeHistoryFinder(getConnection());
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
     * 検索条件のセットを行います。<BR>
     * @param p 検索条件を含むパラメータ
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected SearchKey createSearchKey(ScheduleParams p)
            throws CommonException
    {
        ExchangeHistorySearchKey searchKey = new ExchangeHistorySearchKey();

        // 開始検索日時、終了検索日時
        if (!(StringUtil.isBlank(p.getDate(SEARCH_DATE_FROM)) && StringUtil.isBlank(p.getDate(SEARCH_DATE_TO))))
        {
            Date[] tmp =
                    WmsFormatter.getFromTo(p.getDate(SEARCH_DATE_FROM), p.getDate(SEARCH_TIME_FROM),
                            p.getDate(SEARCH_DATE_TO), p.getDate(SEARCH_TIME_TO));
            searchKey.setStartDate(tmp[0], ">=");
            searchKey.setStartDate(tmp[1], "<");
        }

        // set where
        if (!InParameter.SEARCH_ALL.equals(p.getString(COMMUNICATION_DATA)))
        {
            searchKey.setKey(ExchangeEnvironment.JOB_TYPE, p.getString(COMMUNICATION_DATA));
        }

        searchKey.setKey(ExchangeEnvironment.EXCHANGE_TYPE, p.getString(COMMUNICATION_TYPE));

        // set join
        searchKey.setJoin(ExchangeHistory.JOB_TYPE, ExchangeEnvironment.JOB_TYPE);
        searchKey.setJoin(ExchangeHistory.EXCHANGE_TYPE, ExchangeEnvironment.EXCHANGE_TYPE);

        // set join(LoadErrorInfo Table)
        searchKey.setJoin(ExchangeHistory.FILE_NAME, "", LoadErrorInfo.FILE_NAME, "(+)");
        searchKey.setJoin(ExchangeHistory.START_DATE, "", LoadErrorInfo.START_DATE, "(+)");

        // set order by
        searchKey.setStartDateOrder(false);
        searchKey.setFileNameOrder(false);
        searchKey.setStatusOrder(true);

        // set collect
        searchKey.setStartDateCollect();
        searchKey.setExchangeTypeCollect();
        searchKey.setFileNameCollect();
        searchKey.setStatusCollect();
        searchKey.setCollect(LoadErrorInfo.JOB_TYPE);

        // set group by
        searchKey.setStartDateGroup();
        searchKey.setExchangeTypeGroup();
        searchKey.setFileNameGroup();
        searchKey.setStatusGroup();
        searchKey.setGroup(LoadErrorInfo.JOB_TYPE);

        return searchKey;
    }

    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     * @throws ScheduleException 
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder)
            throws ReadWriteException,
                ScheduleException
    {
        // 最大表示件数分検索結果を取得する
        ExchangeHistory[] entities = (ExchangeHistory[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();
        int lstNo = 0;

        WarenaviSystemController wSysCtlr = new WarenaviSystemController(getConnection(), getClass());

        for (ExchangeHistory ent : entities)
        {
            // 返却データをセットする
            Params param = new Params();
            lstNo++;

            // No.
            param.set(NO, lstNo);
            // 送受信日時
            param.set(START_DATE, WmsFormatter.toDispDateTime(WmsFormatter.toParamDateTime(ent.getStartDate()),
                    this.getLocale()));
            // データ名称
            param.set(FILE_NAME, ent.getFileName());
            // 状態
            param.set(STATUS, getExchangeStatus(ent.getStatus()));
            // 詳細ボタン有効/無効
            param.set(IS_DETAILS, isDetails(ent.getStatus(), ent.getExchangeType(), String.valueOf(ent.getValue(
                    LoadErrorInfo.JOB_TYPE, ""))));

            // 送受信日時
            param.set(HID_START_DATE, ent.getStartDate());

            result.add(param);
        }

        return result;
    }

    /**
     * 交換データ通信履歴の状態フラグから、名称を返します。<BR>
     * <CODE>SystemDefine.EXCHANGE_STATUS_NORMAL</CODE> : 正常完了 <BR>
     * <code>SystemDefine.EXCHANGE_STATUS_SAME_FILE</code> : 同一ファイルを二度受信 <BR>
     * <code>SystemDefine.EXCHANGE_STATUS_RECEIVING_ERROR</code> : 受信中にエラー発生 <BR>
     * <code>SystemDefine.EXCHANGE_STATUS_VALIDATE_DATA</code> : 不正データを受信 <BR>
     * <code>SystemDefine.EXCHANGE_STATUS_REMOVE_ERROR</code> : 削除中にエラー発生 <BR>
     * @param  status 状態フラグ
     * @return 状態フラグ名称
     */
    protected String getExchangeStatus(String status)
    {
        if (ExchangeHistory.STATUS_NORMAL.equals(status))
        {
            // 正常完了
            return DisplayText.getText("LBL-W6001");
        }
        else if (ExchangeHistory.STATUS_SKIP.equals(status))
        {
            // 正常(スキップあり)
            return DisplayText.getText("LBL-W6011");
        }
        else if (ExchangeHistory.STATUS_ALL_SKIP.equals(status))
        {
            // 正常(全スキップ)
            return DisplayText.getText("LBL-W6012");
        }
        else if (ExchangeHistory.STATUS_ERR_FILE_NAME.equals(status))
        {
            // ファイル名が不正なため取得不可
            return DisplayText.getText("LBL-W6008");
        }
        else if (ExchangeHistory.STATUS_ERR_DOWNLOAD.equals(status))
        {
            // ファイルの取得に失敗
            return DisplayText.getText("LBL-W6009");
        }
        else if (ExchangeHistory.STATUS_ERR_DUPPLICATION.equals(status))
        {
            // 同一ファイル二度受信
            return DisplayText.getText("LBL-W6002");
        }
        else if (ExchangeHistory.STATUS_ERR_DATA.equals(status))
        {
            // 受信データ内エラー
            return DisplayText.getText("LBL-W6003");
        }
        else if (ExchangeHistory.STATUS_ERR_DELETE_FILE.equals(status))
        {
            // 削除中エラー発生
            return DisplayText.getText("LBL-W6005");
        }
        return "";
    }

    /***
     * 取込みエラー情報の存在有無を取得します。
     * @param status 状態
     * @param exchangeType 送受信区分
     * @param jobType 作業区分
     * @return 存在する場合：true、存在しない場合：false
     */
    protected boolean isDetails(String status, String exchangeType, String jobType)
    {
        // 状態チェック
        if (SystemDefine.EXCHANGE_STATUS_NORMAL.equals(status))
        {
            return false;
        }

        // 送受信区分チェック
        if (SystemDefine.EXCHANGE_TYPE_SEND.equals(exchangeType))
        {
            return false;
        }

        // ファイル存在チェック
        if (StringUtil.isBlank(jobType))
        {
            return false;
        }

        return true;
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
