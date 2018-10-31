// $Id: RetrievalOrderAllocClearDASCH.java 4808 2009-08-10 06:32:12Z shibamoto $
package jp.co.daifuku.wms.retrieval.dasch;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

import jp.co.daifuku.foundation.da.AbstractDASCH;
import jp.co.daifuku.wms.retrieval.dasch.RetrievalOrderAllocClearDASCHParams;

/**
 * 出庫キャンセルに必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 4808 $, $Date: 2009-08-10 15:32:12 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class RetrievalOrderAllocClearDASCH
        extends AbstractDASCH
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

    /**
     * DB Finder
     */
    private AbstractDBFinder _finder = null;

    /**
     * 現在点
     */
    private int _current = -1;

    /**
     * レコード総数
     */

    private int _total = -1;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでDASCHを作成します。
     * @param conn Database DBコネクション
     * @param parent Caller 呼び出し元クラスクラス情報
     * @param locale ロケーる
     * @param ui ユーザ情報
     */
    public RetrievalOrderAllocClearDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // Create Finder Object
        _finder = new WorkInfoFinder(getConnection());
        // Initialize record counts
        _finder.open(isForwardOnly());
        // Create Search Key and search for Records
        _finder.search(createSearchKey(p));

        _current = -1;

//        setMessage("6001013");
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
        return _total > _current;

    }

    /**
     * データを1件返します。
     * @return 取得データ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public Params get()
            throws CommonException
    {
        throw new ScheduleException("This method is not implemented.");
    }

    /**
     * 
     * finder,Connection close
     */
    public void close()
    {
        if (_finder != null)
        {
            _finder.close();
        }
        super.close();
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
    protected int actualCount(Params p)
            throws CommonException
    {
        WorkInfoHandler handler = new WorkInfoHandler(getConnection());

        // find count
        _total = handler.count(createSearchKey(p));

        return _total;
    }

    /**
     * 検索したデータのうち、start番目からcntで指定された件数のデータを返します。
     * @param start 開始インデックス
     * @param cnt 件数
     * @return 対象データのリスト
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        List<Params> params = new ArrayList<Params>();
        WorkInfo[] works = (WorkInfo[])_finder.getEntities(start, start + cnt);

        for (WorkInfo work : works)
        {
            Params p = new Params();
            // バッチNo.
            p.set(RetrievalOrderAllocClearDASCHParams.BATCH, work.getBatchNo());
            // オーダーNo
            p.set(RetrievalOrderAllocClearDASCHParams.ORDER, work.getOrderNo());
            // 出荷先コード
            p.set(RetrievalOrderAllocClearDASCHParams.CUSTOMER_CODE, work.getCustomerCode());
            // 棚No.
            p.set(RetrievalOrderAllocClearDASCHParams.CUSTOMER_NAME, work.getValue(Customer.CUSTOMER_NAME));

            params.add(p);
        }
        return params;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 作業情報検索SearchKeyを生成します。
     * @param p 作業情報検索条件
     * @return StockSearchKey
     */
    private WorkInfoSearchKey createSearchKey(Params p)
    {
        WorkInfoSearchKey sKey = new WorkInfoSearchKey();

        // 荷主コード
        if (!StringUtil.isBlank(p.getString(RetrievalOrderAllocClearDASCHParams.CONSIGNOR_CODE)))
        {
            sKey.setConsignorCode(p.getString(RetrievalOrderAllocClearDASCHParams.CONSIGNOR_CODE));
        }
        // 出庫予定日
        if (!StringUtil.isBlank(p.getString(RetrievalOrderAllocClearDASCHParams.PLANDAY)))
        {
            sKey.setPlanDay(WmsFormatter.toParamDate(p.getDate(RetrievalOrderAllocClearDASCHParams.PLANDAY)));
        }
        // バッチNo.
        if (!StringUtil.isBlank(p.getString(RetrievalOrderAllocClearDASCHParams.BATCHNO)))
        {
            sKey.setBatchNo(p.getString(RetrievalOrderAllocClearDASCHParams.BATCHNO));
        }
        String[] loc =
                WmsFormatter.getFromTo(p.getString(RetrievalOrderAllocClearDASCHParams.FROMORDER),
                        p.getString(RetrievalOrderAllocClearDASCHParams.TOORDER));
        // 開始オーダーNo.
        if (!StringUtil.isBlank(loc[0]))
        {

            sKey.setOrderNo(loc[0], ">=");

        }
        // 終了オーダーNo.
        if (!StringUtil.isBlank(loc[1]))
        {

            sKey.setOrderNo(loc[1], "<=");

        }
        // 出庫作業
        sKey.setJobType(WorkInfo.JOB_TYPE_RETRIEVAL);
        // 作業状態(入出庫作業情報が未開始)
        sKey.setStatusFlag(WorkInfo.STATUS_FLAG_UNSTART);

        // 結合条件
        sKey.setJoin(WorkInfo.PLAN_UKEY, RetrievalPlan.PLAN_UKEY);
        sKey.setJoin(WorkInfo.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
        sKey.setJoin(WorkInfo.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");

        // 取得条件
        sKey.setBatchNoCollect();
        sKey.setOrderNoCollect();
        sKey.setCustomerCodeCollect();
        sKey.setCollect(Customer.CUSTOMER_NAME);
        // ソート順
        sKey.setBatchNoOrder(true);
        sKey.setOrderNoOrder(true);
        sKey.setCustomerCodeOrder(true);
        // グループ条件
        sKey.setBatchNoGroup();
        sKey.setOrderNoGroup();
        sKey.setCustomerCodeGroup();
        sKey.setGroup(Customer.CUSTOMER_NAME);


        return sKey;
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
