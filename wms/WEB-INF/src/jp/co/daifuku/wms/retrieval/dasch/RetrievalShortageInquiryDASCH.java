package jp.co.daifuku.wms.retrieval.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.retrieval.dasch.RetrievalShortageInquiryDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.AllocatePriorityHandler;
import jp.co.daifuku.wms.base.dbhandler.AllocatePrioritySearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoSearchKey;
import jp.co.daifuku.wms.base.entity.AllocatePriority;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.ShortageInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOutParameter;

/**
 * 欠品情報照会に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 4690 $, $Date: 2009-07-16 09:24:27 +0900 (木, 16 7 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class RetrievalShortageInquiryDASCH
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
     * @param locale ロケーる
     * @param ui ユーザ情報
     */
    public RetrievalShortageInquiryDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new ShortageInfoFinder(getConnection());

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
        ShortageInfo[] restData = (ShortageInfo[])_finder.getEntities(1);
        Params p = new Params();

        // 取得情報をパラメータに編集
        for (ShortageInfo rData : restData)
        {
            // DFK DS-Number
            p.set(DFK_DS_NO, getDsNumber());
            // DFK User-ID
            p.set(DFK_USER_ID, getUserId());
            // DFK User-Name
            p.set(DFK_USER_NAME, getUserName());
            // 予定日
            p.set(PLAN_DAY, WmsFormatter.toDate(rData.getValue(ShortageInfo.PLAN_DAY).toString()));
            // 印刷日付
            p.set(SYS_DAY, getPrintDate());
            // 印刷時刻
            p.set(SYS_TIME, getPrintDate());
            //出庫開始日時
            p.set(START_DAY, rData.getValue(ShortageInfo.RETRIEVAL_START_DATE));
            p.set(START_TIM, rData.getValue(ShortageInfo.RETRIEVAL_START_DATE));
            //バッチNo.
            p.set(BATCH, rData.getValue(ShortageInfo.BATCH_NO));
            // 引当パターンNo.
            p.set(ALLOCATED_PATTERN_NO, rData.getValue(ShortageInfo.ALLOCATE_NO));
            //オーダーNo.
            p.set(ORDER, rData.getValue(ShortageInfo.ORDER_NO));
            //商品コード
            //出荷先コード
            p.set(CUSTOMER_CODE, rData.getValue(ShortageInfo.CUSTOMER_CODE));
            //出荷先名称
            p.set(CUSTOMER_NAME, rData.getValue(Customer.CUSTOMER_NAME));
            p.set(ITEM_CODE, rData.getValue(ShortageInfo.ITEM_CODE));
            //商品名称
            p.set(ITEM_NAME, rData.getValue(Item.ITEM_NAME));
            // ロットNo.
            p.set(PLAN_LOT_NO, rData.getValue(ShortageInfo.PLAN_LOT_NO));
            // ケース入数
            int enteringQty = rData.getBigDecimal(Item.ENTERING_QTY).intValue();
            p.set(CASE_PACK, enteringQty);
            // 予定ケース数
            p.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(rData.getThisTimePlanQty(), enteringQty));
            // 予定ピース数
            p.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(rData.getThisTimePlanQty(), enteringQty));
            // 補充ケース数
            p.set(REP_CASE_QTY, DisplayUtil.getCaseQty(rData.getReplenishmentQty(), enteringQty));
            // 補充ピース数
            p.set(REP_PIECE_QTY, DisplayUtil.getPieceQty(rData.getReplenishmentQty(), enteringQty));
            // 欠品ケース数
            p.set(SHORTAGE_CASE_QTY, DisplayUtil.getCaseQty(rData.getShortageQty(), enteringQty));
            // 欠品ピース数
            p.set(SHORTAGE_PIECE_QTY, DisplayUtil.getPieceQty(rData.getShortageQty(), enteringQty));
            // 引当
            if (rData.getValue(ShortageInfo.SHORTAGE_FLAG).equals(RetrievalOutParameter.SHORTAGE_FLAG_SHORTAGE_NORMAL))
            {
                p.set(ALLOCATED, DisplayResource.getAllocate(RetrievalOutParameter.SHORTAGE_FLAG_SHORTAGE_NORMAL));
            }
            else if (rData.getValue(ShortageInfo.SHORTAGE_FLAG).equals(
                    RetrievalOutParameter.SHORTAGE_FLAG_SHORTAGE_COMPLETE))
            {
                p.set(ALLOCATED, DisplayResource.getAllocate(RetrievalOutParameter.SHORTAGE_FLAG_SHORTAGE_COMPLETE));
            }

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
        ShortageInfoHandler handler = new ShortageInfoHandler(getConnection());
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
        ShortageInfo[] restData = (ShortageInfo[])_finder.getEntities(start, start + cnt);

        // 取得情報をパラメータに編集
        for (ShortageInfo rData : restData)
        {
            Params p = new Params();
            //バッチNo.
            p.set(BATCH, rData.getValue(ShortageInfo.BATCH_NO));
            //オーダーNo.
            p.set(ORDER, rData.getValue(ShortageInfo.ORDER_NO));
            // 予定日
            p.set(PLAN_DAY, WmsFormatter.toDate(rData.getValue(ShortageInfo.PLAN_DAY).toString()));
            //出荷先コード
            p.set(CUSTOMER_CODE, rData.getValue(ShortageInfo.CUSTOMER_CODE));
            //出荷先名称
            p.set(CUSTOMER_NAME, rData.getValue(Customer.CUSTOMER_NAME));
            //商品コード
            p.set(ITEM_CODE, rData.getValue(ShortageInfo.ITEM_CODE));
            //商品名称
            p.set(ITEM_NAME, rData.getValue(Item.ITEM_NAME));
            // ケース入数
            int enteringQty = rData.getBigDecimal(Item.ENTERING_QTY).intValue();
            p.set(CASE_PACK, enteringQty);
            // 予定ケース数
            p.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(
                    rData.getBigDecimal(ShortageInfo.THIS_TIME_PLAN_QTY).intValue(), enteringQty));
            // 予定ピース数
            p.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(
                    rData.getBigDecimal(ShortageInfo.THIS_TIME_PLAN_QTY).intValue(), enteringQty));
            // 補充ケース数
            p.set(REP_CASE_QTY, DisplayUtil.getCaseQty(rData.getBigDecimal(ShortageInfo.REPLENISHMENT_QTY).intValue(),
                    enteringQty));
            // 補充ピース数
            p.set(REP_PIECE_QTY, DisplayUtil.getPieceQty(
                    rData.getBigDecimal(ShortageInfo.REPLENISHMENT_QTY).intValue(), enteringQty));
            // 欠品ケース数
            p.set(SHORTAGE_CASE_QTY, DisplayUtil.getCaseQty(rData.getBigDecimal(ShortageInfo.SHORTAGE_QTY).intValue(),
                    enteringQty));
            // 欠品ピース数
            p.set(SHORTAGE_PIECE_QTY, DisplayUtil.getPieceQty(
                    rData.getBigDecimal(ShortageInfo.SHORTAGE_QTY).intValue(), enteringQty));
            // 引当
            if (rData.getValue(ShortageInfo.SHORTAGE_FLAG).equals(RetrievalOutParameter.SHORTAGE_FLAG_SHORTAGE_NORMAL))
            {
                p.set(ALLOCATED, DisplayResource.getAllocate(RetrievalOutParameter.SHORTAGE_FLAG_SHORTAGE_NORMAL));
            }
            else if (rData.getValue(ShortageInfo.SHORTAGE_FLAG).equals(
                    RetrievalOutParameter.SHORTAGE_FLAG_SHORTAGE_COMPLETE))
            {
                p.set(ALLOCATED, DisplayResource.getAllocate(RetrievalOutParameter.SHORTAGE_FLAG_SHORTAGE_COMPLETE));
            }
            // ロットNo.
            p.set(PLAN_LOT_NO, rData.getValue(ShortageInfo.PLAN_LOT_NO));
            // 引当パターンNo.
            p.set(ALLOCATED_PATTERN_NO, rData.getValue(ShortageInfo.ALLOCATE_NO));
            // 引当パターン名称
            p.set(ALLOCATED_PATTEN_NAME,
                    getAllocatePatternName(rData.getValue(AllocatePriority.ALLOCATE_NO).toString()));

            params.add(p);
        }

        return params;
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

    /**
     * 検索条件の編集を行います。
     * @param p 検索条件パラメータ
     * @return SearchKey
     */
    private SearchKey createSearchKey(Params p)
    {
        // (1)パラメータで指定された内容を元に、DNSHORTAGEINFOテーブルの検索条件を作成します。<BR>
        ShortageInfoSearchKey searchKey = new ShortageInfoSearchKey();

        // 検索条件をセットします。
        // 荷主コード
        if (!StringUtil.isBlank(p.getString(CONSIGNOR_CODE)))
        {
            searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        }

        // 出庫開始日時
        if (!StringUtil.isBlank(p.getDate(PLAN_DATE)))
        {
            searchKey.setPlanDay(WmsFormatter.toParamDate(p.getDate(PLAN_DATE)));
        }

        // 出庫予定日
        if (!StringUtil.isBlank(p.getDate(START_DATE)))
        {
            Date _startdatetime = null;
            try
            {
                _startdatetime = WmsFormatter.getFromSearchDate(p.getDate(START_DATE), p.getDate(START_TIME));
            }
            catch (Exception ex)
            {
            }
            searchKey.setRetrievalStartDate(_startdatetime);
        }

        // バッチNo.
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            searchKey.setBatchNo(p.getString(BATCH_NO));
        }

        // 開始オーダーNo、終了オーダーNo
        if (!StringUtil.isBlank(p.getString(ORDER_NO)))
        {
            if (!StringUtil.isBlank(p.getString(ORDER_NO_TO)))
            {
                if (p.getString(ORDER_NO_TO).compareTo(p.getString(ORDER_NO)) >= 0)
                {
                    searchKey.setOrderNo(p.getString(ORDER_NO), ">=");
                    searchKey.setOrderNo(p.getString(ORDER_NO_TO), "<=");
                }
                else
                {
                    searchKey.setOrderNo(p.getString(ORDER_NO), "<=");
                    searchKey.setOrderNo(p.getString(ORDER_NO_TO), ">=");
                }
            }
            else
            {
                searchKey.setOrderNo(p.getString(ORDER_NO), ">=");
            }
        }
        else if (!StringUtil.isBlank(p.getString(ORDER_NO_TO)))
        {
            searchKey.setOrderNo(p.getString(ORDER_NO_TO), "<=");
        }

        // 出庫開始単位キー(出庫開始から呼ばれる場合に使用)
        if (!StringUtil.isBlank(p.getString(START_UNIT_KEY)))
        {
            searchKey.setStartUnitKey(p.getString(START_UNIT_KEY));
            // 補充欠品がある場合
            if (p.getBoolean(REPLENISHMENT_SHORTAGE_FLAG))
            {
                searchKey.setKey(ShortageInfo.SHORTAGE_QTY, ShortageInfo.REPLENISHMENT_QTY, "!=", "(", ")", true);
            }
        }

        //作業区分
        searchKey.setJobType(SystemDefine.JOB_TYPE_RETRIEVAL);

        //商品マスタ
        // 荷主コード
        searchKey.setJoin(ShortageInfo.CONSIGNOR_CODE, "", Item.CONSIGNOR_CODE, "(+)");
        // 商品コード
        searchKey.setJoin(ShortageInfo.ITEM_CODE, "", Item.ITEM_CODE, "(+)");

        //出荷先マスタ
        // 荷主コード
        searchKey.setJoin(ShortageInfo.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
        // 出荷先コード
        searchKey.setJoin(ShortageInfo.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");

        // ソート順
        // バッチNo.、オーダーNo.、出荷先コード、商品コード、ロットNo.
        searchKey.setBatchNoOrder(true);
        searchKey.setOrderNoOrder(true);
        searchKey.setCustomerCodeOrder(true);
        searchKey.setItemCodeOrder(true);
        searchKey.setPlanLotNoOrder(true);


        //表示項目
        searchKey.setRetrievalStartDateCollect();
        searchKey.setPlanDayCollect();
        searchKey.setBatchNoCollect();
        searchKey.setOrderNoCollect();
        searchKey.setItemCodeCollect();
        searchKey.setCustomerCodeCollect();
        searchKey.setThisTimePlanQtyCollect();
        searchKey.setReplenishmentQtyCollect();
        searchKey.setShortageQtyCollect();
        searchKey.setShortageFlagCollect();
        searchKey.setPlanLotNoCollect();
        searchKey.setAllocateNoCollect();

        searchKey.setCollect(Item.ITEM_NAME);
        searchKey.setCollect(Item.ENTERING_QTY);
        searchKey.setCollect(Customer.CUSTOMER_NAME);

        return searchKey;
    }

    /**
     * 引当パターン名称を取得します。
     * @param allocNo 名称を取得する対象の引当パターンNo.
     * @return 取得した引当パターン名称
     */
    private String getAllocatePatternName(String allocNo)
    {
        try
        {
            AllocatePriorityHandler hndl = new AllocatePriorityHandler(getConnection());
            AllocatePrioritySearchKey sKey = new AllocatePrioritySearchKey();

            // 検索条件 : 引当パターンNo.
            sKey.setAllocateNo(allocNo);
            sKey.setAllocateNameCollect();

            AllocatePriority[] allocPriority = (AllocatePriority[])hndl.find(sKey);

            return allocPriority[0].getAllocateName();
        }
        catch (ReadWriteException ex)
        {
            RmiMsgLogClient.write(new TraceHandler(6006020, ex), this.getClass().getName());
            return "";
        }
    }

}
//end of class
