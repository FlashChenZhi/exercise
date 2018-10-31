package jp.co.daifuku.wms.system.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.system.dasch.WorkerResultInquiryDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultSearchKey;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * ユーザ別実績照会に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 4690 $, $Date: 2009-07-16 09:24:27 +0900 (木, 16 7 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class WorkerResultInquiryDASCH
        extends AbstractWmsDASCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * DB Finder定義
     */
    private AbstractDBFinder _finder = null;

    /**
     * 検索件数
     */
    private int _total = -1;

    /**
     * 取得完了件数
     */
    private int _current = -1;

    /**
     * 集約条件名称記憶
     */
    private String _Sort_Condition = "";

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
     * 指定されたパラメータでDASCHを作成します。
     * @param conn Database DBコネクション
     * @param parent Caller 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     */
    public WorkerResultInquiryDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * DBの検索を行います。
     * @param p 検索条件パラメータ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public void search(Params p)
            throws CommonException
    {
        // create Finder object
        _finder = new WorkerResultFinder(getConnection());

        // データ件数初期化(Excel大量出力対応)
        _finder.open(isForwardOnly());

        // 検索条件作成及び対象データ取得
        _finder.search(createSearchKey(p));

        _current = -1;

        return;
    }

    /**
     * 次のデータが存在するかどうかを判定します。
     * @return データが存在する場合はtrueを返します。
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean next()
            throws CommonException
    {
        _current++;
        return (_current < _total);
    }

    /**
     * データを1件返します。
     * @return 取得データ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public Params get()
            throws CommonException
    {
        // 対象情報取得
        WorkerResult[] restData = (WorkerResult[])_finder.getEntities(1);
        Params p = new Params();

        // 取得情報をパラメータに編集
        for (WorkerResult rData : restData)
        {
            //作業日
            if (rData.getValue(WorkerResult.WORK_DAY) != null)
            {
                p.set(WORK_DAY, WmsFormatter.toDate(rData.getValue(WorkerResult.WORK_DAY).toString()));
            }
            //ユーザ名称
            p.set(USER_NAME, rData.getValue(WorkerResult.USER_NAME));

            //作業内容
            p.set(WORK_CONTENT, DisplayResource.getJobType(rData.getValue(WorkerResult.JOB_TYPE).toString()));

            //開始時間START_TIME
            p.set(START_TIME, rData.getValue(WorkerResult.WORK_START_DATE));
            //終了時間
            p.set(END_TIME, rData.getValue(WorkerResult.WORK_END_DATE));
            //作業時間
            p.set(WORK_DURATION, DisplayUtil.getTimeToDate(rData.getValue(WorkerResult.WORK_TIME).toString()));
            //作業数量
            p.set(WORKED_QTY_IN_PIECE, rData.getValue(WorkerResult.WORK_QTY));
            //作業回数
            p.set(WORKED_COUNTS, rData.getValue(WorkerResult.WORK_CNT));
            // 作業時間/h
            double hour = (double)Integer.parseInt(rData.getValue(WorkerResult.WORK_TIME).toString()) / (double)3600;
            //作業数量/h
            p.set(WORKED_QTY_IN_PIECEHR, Integer.parseInt(rData.getValue(WorkerResult.WORK_QTY).toString()) / hour);
            //作業回数/h
            p.set(WORKED_COUNTSHR, Integer.parseInt(rData.getValue(WorkerResult.WORK_CNT).toString()) / hour);
            //RFT号機No.
            p.set(RFT, rData.getValue(WorkerResult.TERMINAL_NO));
            // DFK DS-Number
            p.set(DFK_DS_NO, getDsNumber());
            // DFK User-ID
            p.set(DFK_USER_ID, getUserId());
            // DFK User-Name
            p.set(DFK_USER_NAME, getUserName());
            // 印刷日付
            p.set(SYS_DAY, getPrintDate());
            // 印刷時刻
            p.set(SYS_TIME, getPrintDate());
            // 集約条件
            p.set(SORT_CONDITION, _Sort_Condition);
        }
        return p;
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
     * データ件数を返します。
     * @param p 検索条件パラメータ
     * @return データ件数
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    @Override
    protected int actualCount(Params p)
            throws CommonException
    {
        WorkerResultHandler handler = new WorkerResultHandler(getConnection());
        _total = handler.count(createSearchKey(p));

        return _total;
    }

    /**
     * 検索したデータのうち、start番目からcntで指定された件数のデータを返します。
     * @param start 開始インデックス
     * @param cnt 件数
     * @return 対象データのリスト
     */
    @Override
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        // 復帰パラメータ領域取得
        List<Params> params = new ArrayList<Params>();

        // 対象情報取得
        WorkerResult[] restData = (WorkerResult[])_finder.getEntities(start, start + cnt);

        // 取得情報をパラメータに編集
        for (WorkerResult rData : restData)
        {
            Params p = new Params();

            //作業日
            if (rData.getValue(WorkerResult.WORK_DAY) != null)
            {
                p.set(WORK_DAY, WmsFormatter.toDate(rData.getValue(WorkerResult.WORK_DAY).toString()));
            }
            //ユーザ名称
            p.set(USER_NAME, rData.getValue(WorkerResult.USER_NAME));

            //作業内容
            p.set(WORK_CONTENT, DisplayResource.getJobType(rData.getValue(WorkerResult.JOB_TYPE).toString()));

            //開始時間START_TIME
            p.set(START_TIME, rData.getValue(WorkerResult.WORK_START_DATE));
            //終了時間
            p.set(END_TIME, rData.getValue(WorkerResult.WORK_END_DATE));
            //作業時間
            p.set(WORK_DURATION, DisplayUtil.getTimeToDate(rData.getValue(WorkerResult.WORK_TIME).toString()));
            //作業数量
            p.set(WORKED_QTY_IN_PIECE, rData.getValue(WorkerResult.WORK_QTY));
            //作業回数
            p.set(WORKED_COUNTS, rData.getValue(WorkerResult.WORK_CNT));
            // 作業時間/h
            double hour = (double)Integer.parseInt(rData.getValue(WorkerResult.WORK_TIME).toString()) / (double)3600;
            //作業数量/h
            p.set(WORKED_QTY_IN_PIECEHR, Integer.parseInt(rData.getValue(WorkerResult.WORK_QTY).toString()) / hour);
            //作業回数/h
            p.set(WORKED_COUNTSHR, Integer.parseInt(rData.getValue(WorkerResult.WORK_CNT).toString()) / hour);
            //RFT号機No.
            p.set(RFT, rData.getValue(WorkerResult.TERMINAL_NO));

            params.add(p);
        }
        return params;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 検索条件の編集を行います。
     * @param p 検索条件パラメータ
     * @return SearchKey
     */
    private SearchKey createSearchKey(Params p)
    {
        // (1)パラメータで指定された内容を元に、DNWORKERRESULTテーブルの検索条件を作成します。<BR>
        WorkerResultSearchKey searchKey = new WorkerResultSearchKey();

        // 検索条件をセットします。
        // 作業内容
        if (!p.getString(F_WORK_CONTENTS).equals(InParameter.SEARCH_ALL))
        {
            searchKey.setJobType(p.getString(F_WORK_CONTENTS));
        }

        // 開始作業日,終了作業日
        if (!StringUtil.isBlank(p.getDate(WORK_DAY)))
        {
            if (!StringUtil.isBlank(p.getDate(WORK_DAY_TO)))
            {
                if (p.getDate(WORK_DAY_TO).compareTo(p.getDate(WORK_DAY)) >= 0)
                {
                    searchKey.setWorkDay(WmsFormatter.toParamDate(p.getDate(WORK_DAY)), ">=");
                    searchKey.setWorkDay(WmsFormatter.toParamDate(p.getDate(WORK_DAY_TO)), "<=");
                }
                else
                {
                    searchKey.setWorkDay(WmsFormatter.toParamDate(p.getDate(WORK_DAY)), "<=");
                    searchKey.setWorkDay(WmsFormatter.toParamDate(p.getDate(WORK_DAY_TO)), ">=");
                }
            }
            else
            {
                searchKey.setWorkDay(WmsFormatter.toParamDate(p.getDate(WORK_DAY)), ">=");
            }
        }
        else if (!StringUtil.isBlank(p.getDate(WORK_DAY_TO)))
        {
            searchKey.setWorkDay(WmsFormatter.toParamDate(p.getDate(WORK_DAY_TO)), "<=");
        }

        // ユーザID
        if (StringUtil.isBlank(p.getString(USER_NAME)))
        {
            searchKey.setUserId(EmConstants.ANONYMOUS_USER, "!=");
            searchKey.setUserId(EmConstants.DAIFUKU_SV_USER, "!=");
            searchKey.setUserId(EmConstants.USERMAINTENANCE_USER, "!=");
        }
        else
        {
            searchKey.setUserName(p.getString(USER_NAME));
        }

        // 作業時間
        searchKey.setWorkTime(0, ">");

        //集約条件
        if (p.getString(GROUP_CONDITION).equals(Parameter.COLLECT_CONDITION_TEAM))
        {
            // 期間内合計表示
            // ソート順(作業者コード、作業内容)
            searchKey.setUserIdOrder(true);
            searchKey.setJobTypeOrder(true);
            // グループ条件(作業者コード、作業者名、作業内容)
            searchKey.setUserIdGroup();
            searchKey.setUserNameGroup();
            searchKey.setJobTypeGroup();
            // 表示項目
            searchKey.setUserIdCollect();
            searchKey.setUserNameCollect();
            searchKey.setJobTypeCollect();
            searchKey.setWorkTimeCollect("SUM");
            searchKey.setWorkQtyCollect("SUM");
            searchKey.setWorkCntCollect("SUM");

            // 期間内合計表示
            _Sort_Condition = DisplayText.getText("TLE-W1503");
        }
        else if (p.getString(GROUP_CONDITION).equals(Parameter.COLLECT_CONDITION_DAILY))
        {
            // 日別合計表示
            // ソート順(作業日、作業者コード、作業内容、開始時刻)
            searchKey.setWorkDayOrder(true);
            searchKey.setUserIdOrder(true);
            searchKey.setJobTypeOrder(true);
            // グループ条件(作業者コード、作業者名、作業日、作業内容)
            searchKey.setUserIdGroup();
            searchKey.setUserNameGroup();
            searchKey.setWorkDayGroup();
            searchKey.setJobTypeGroup();
            // 表示項目
            searchKey.setUserIdCollect();
            searchKey.setWorkDayCollect();
            searchKey.setUserNameCollect();
            searchKey.setJobTypeCollect();
            searchKey.setWorkStartDateCollect("MIN");
            searchKey.setWorkEndDateCollect("MAX");
            searchKey.setWorkTimeCollect("SUM");
            searchKey.setWorkQtyCollect("SUM");
            searchKey.setWorkCntCollect("SUM");

            // 日別合計表示
            _Sort_Condition = DisplayText.getText("TLE-W1504");
        }
        else if (p.getString(GROUP_CONDITION).equals(Parameter.COLLECT_CONDITION_DETAIL))
        {
            // 詳細表示
            // ソート順(作業日、作業者コード、作業内容、開始時刻)
            searchKey.setWorkDayOrder(true);
            searchKey.setUserIdOrder(true);
            searchKey.setJobTypeOrder(true);
            searchKey.setWorkStartDateOrder(true);

            // 表示項目
            searchKey.setUserIdCollect();
            searchKey.setWorkDayCollect();
            searchKey.setUserNameCollect();
            searchKey.setJobTypeCollect();
            searchKey.setWorkStartDateCollect();
            searchKey.setWorkEndDateCollect();
            searchKey.setWorkTimeCollect();
            searchKey.setWorkQtyCollect();
            searchKey.setWorkCntCollect();
            searchKey.setTerminalNoCollect();

            // 詳細表示
            _Sort_Condition = DisplayText.getText("TLE-W1505");
        }
        return searchKey;
    }

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
