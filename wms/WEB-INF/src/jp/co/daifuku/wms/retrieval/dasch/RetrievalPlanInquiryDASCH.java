package jp.co.daifuku.wms.retrieval.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.retrieval.dasch.RetrievalPlanInquiryDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 出庫予定照会に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 4690 $, $Date: 2009-07-16 09:24:27 +0900 (木, 16 7 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class RetrievalPlanInquiryDASCH
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
    public RetrievalPlanInquiryDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new RetrievalPlanFinder(getConnection());

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
        RetrievalPlan[] restData = (RetrievalPlan[])_finder.getEntities(1);
        Params p = new Params();

        // 取得情報をパラメータに編集
        for (RetrievalPlan rData : restData)
        {
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

            // 出庫予定日
            p.set(PLAN_DATE, WmsFormatter.toDate(rData.getValue(RetrievalPlan.PLAN_DAY).toString()));
            // 伝票No
            p.set(TICKET, rData.getValue(RetrievalPlan.SHIP_TICKET_NO));
            // 行No
            p.set(LINE, rData.getValue(RetrievalPlan.SHIP_LINE_NO));
            // 作業枝番
            p.set(SERIAL, rData.getValue(RetrievalPlan.BRANCH_NO));
            // バッチNo
            p.set(BATCH, rData.getValue(RetrievalPlan.BATCH_NO));
            // オーダーNo
            p.set(ORDER, rData.getValue(RetrievalPlan.ORDER_NO));
            // 出荷先コード
            p.set(CUSTOMER_CODE, rData.getValue(RetrievalPlan.CUSTOMER_CODE));
            // 出荷先名称
            p.set(CUSTOMER_NAME, rData.getValue(Customer.CUSTOMER_NAME));
            // 商品コード
            p.set(ITEM_CODE, rData.getValue(RetrievalPlan.ITEM_CODE));
            // 商品名称
            p.set(ITEM_NAME, rData.getValue(Item.ITEM_NAME));
            // ロットNo
            p.set(LOT, rData.getValue(RetrievalPlan.PLAN_LOT_NO));
            // ケース入数
            int enteringQty = rData.getBigDecimal(Item.ENTERING_QTY).intValue();
            p.set(CASE_PACK, enteringQty);
            // 予定ケース数
            p.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(rData.getBigDecimal(RetrievalPlan.PLAN_QTY).intValue(),
                    enteringQty));
            // 予定ピース数
            p.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(rData.getBigDecimal(RetrievalPlan.PLAN_QTY).intValue(),
                    enteringQty));
            // 実績ケース数
            p.set(RESULT_CASE_QTY, DisplayUtil.getCaseQty(rData.getBigDecimal(RetrievalPlan.RESULT_QTY).intValue(),
                    enteringQty));
            // 実績ピース数
            p.set(RESULT_PIECE_QTY, DisplayUtil.getPieceQty(rData.getBigDecimal(RetrievalPlan.RESULT_QTY).intValue(),
                    enteringQty));
            // 欠品ケース数
            p.set(SHORTAGE_CASE_QTY, DisplayUtil.getCaseQty(rData.getBigDecimal(RetrievalPlan.SHORTAGE_QTY).intValue(),
                    enteringQty));
            // 欠品ピース数
            p.set(SHORTAGE_PIECE_QTY, DisplayUtil.getPieceQty(
                    rData.getBigDecimal(RetrievalPlan.SHORTAGE_QTY).intValue(), enteringQty));
            // 出庫エリア
            p.set(PICKING_AREA, rData.getValue(RetrievalPlan.PLAN_AREA_NO));
            // 出庫棚
            p.set(PICKING_LOCATION, rData.getValue(RetrievalPlan.PLAN_LOCATION_NO));
            // JANコード
            p.set(UPC_CODE, rData.getValue(Item.JAN));
            // ケースITF
            p.set(CASE_ITF, rData.getValue(Item.ITF));
            // 作業状態
            p.set(WORK_STATUS, DisplayResource.getPlanStatus(rData.getValue(RetrievalPlan.STATUS_FLAG).toString()));

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
        RetrievalPlanHandler handler = new RetrievalPlanHandler(getConnection());
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
        RetrievalPlan[] restData = (RetrievalPlan[])_finder.getEntities(start, start + cnt);

        // 取得情報をパラメータに編集
        for (RetrievalPlan rData : restData)
        {
            Params p = new Params();

            // 出庫予定日
            p.set(PLAN_DATE, WmsFormatter.toDate(rData.getValue(RetrievalPlan.PLAN_DAY).toString()));
            // 伝票No
            p.set(TICKET, rData.getValue(RetrievalPlan.SHIP_TICKET_NO));
            // 行No
            p.set(LINE, rData.getValue(RetrievalPlan.SHIP_LINE_NO));
            // 作業枝番
            p.set(SERIAL, rData.getValue(RetrievalPlan.BRANCH_NO));
            // バッチNo
            p.set(BATCH, rData.getValue(RetrievalPlan.BATCH_NO));
            // オーダーNo
            p.set(ORDER, rData.getValue(RetrievalPlan.ORDER_NO));
            // 出荷先コード
            p.set(CUSTOMER_CODE, rData.getValue(RetrievalPlan.CUSTOMER_CODE));
            // 出荷先名称
            p.set(CUSTOMER_NAME, rData.getValue(Customer.CUSTOMER_NAME));
            // 商品コード
            p.set(ITEM_CODE, rData.getValue(RetrievalPlan.ITEM_CODE));
            // 商品名称
            p.set(ITEM_NAME, rData.getValue(Item.ITEM_NAME));
            // ロットNo
            p.set(LOT, rData.getValue(RetrievalPlan.PLAN_LOT_NO));
            // ケース入数
            int enteringQty = rData.getBigDecimal(Item.ENTERING_QTY).intValue();
            p.set(CASE_PACK, enteringQty);
            // 予定ケース数
            p.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(rData.getBigDecimal(RetrievalPlan.PLAN_QTY).intValue(),
                    enteringQty));
            // 予定ピース数
            p.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(rData.getBigDecimal(RetrievalPlan.PLAN_QTY).intValue(),
                    enteringQty));
            // 実績ケース数
            p.set(RESULT_CASE_QTY, DisplayUtil.getCaseQty(rData.getBigDecimal(RetrievalPlan.RESULT_QTY).intValue(),
                    enteringQty));
            // 実績ピース数
            p.set(RESULT_PIECE_QTY, DisplayUtil.getPieceQty(rData.getBigDecimal(RetrievalPlan.RESULT_QTY).intValue(),
                    enteringQty));
            // 欠品ケース数
            p.set(SHORTAGE_CASE_QTY, DisplayUtil.getCaseQty(rData.getBigDecimal(RetrievalPlan.SHORTAGE_QTY).intValue(),
                    enteringQty));
            // 欠品ピース数
            p.set(SHORTAGE_PIECE_QTY, DisplayUtil.getPieceQty(
                    rData.getBigDecimal(RetrievalPlan.SHORTAGE_QTY).intValue(), enteringQty));
            // 出庫エリア
            p.set(PICKING_AREA, rData.getValue(RetrievalPlan.PLAN_AREA_NO));
            // 出庫棚
            p.set(PICKING_LOCATION, rData.getValue(RetrievalPlan.PLAN_LOCATION_NO));
            // JANコード
            p.set(UPC_CODE, rData.getValue(Item.JAN));
            // ケースITF
            p.set(CASE_ITF, rData.getValue(Item.ITF));
            // 作業状態
            p.set(WORK_STATUS, DisplayResource.getPlanStatus(rData.getValue(RetrievalPlan.STATUS_FLAG).toString()));
            // 開始状態
            p.set(START_STATUS, DisplayResource.getScheduleStatus(rData.getValue(RetrievalPlan.SCH_FLAG).toString()));
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
        // (1)パラメータで指定された内容を元に、DNRETRIEVALPLANテーブルの検索条件を作成します。<BR>
        RetrievalPlanSearchKey searchKey = new RetrievalPlanSearchKey();

        // 検索条件
        // 出庫予定日
        if (!StringUtil.isBlank(p.getDate(RETRIEVAL_PLAN_DATE)))
        {
            searchKey.setPlanDay(WmsFormatter.toParamDate(p.getDate(RETRIEVAL_PLAN_DATE)));
        }
        // 伝票No.
        if (!StringUtil.isBlank(p.getString(SLIP_NO)))
        {
            searchKey.setShipTicketNo(p.getString(SLIP_NO));
        }
        // バッチNo.
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            searchKey.setBatchNo(p.getString(BATCH_NO));
        }
        // オーダNo.
        if (!StringUtil.isBlank(p.getString(ORDER_NO)))
        {
            searchKey.setOrderNo(p.getString(ORDER_NO));
        }
        // 荷主コード
        if (!StringUtil.isBlank(p.getString(CONSIGNOR_CODE)))
        {
            searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        }
        // 出荷先コード
        if (!StringUtil.isBlank(p.getString(CUSTOMER_CODE_NO)))
        {
            searchKey.setCustomerCode(p.getString(CUSTOMER_CODE_NO));
        }

        // スケジュール処理フラグ(全て)
        if (InParameter.SEARCH_ALL.equals(p.getString(F_START_STATUS)))
        {
            // 全ての場合はセットしない
        }
        else
        {
            searchKey.setSchFlag(p.getString(F_START_STATUS));
        }

        // 状態フラグ
        if (InParameter.SEARCH_ALL.equals(p.getString(F_WORK_STATUS)))
        {
            // 削除以外を設定
            searchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        }
        else
        {
            searchKey.setStatusFlag(p.getString(F_WORK_STATUS));
        }

        /* 結合条件の指定 */
        // 荷主コード(商品検索時)
        searchKey.setJoin(RetrievalPlan.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        // 商品コード
        searchKey.setJoin(RetrievalPlan.ITEM_CODE, Item.ITEM_CODE);
        // 荷主コード(出荷先検索時)
        searchKey.setJoin(RetrievalPlan.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
        // 出荷先コード
        searchKey.setJoin(RetrievalPlan.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");


        // 集約条件：なし

        /* ソート順 */
        // 予定日
        searchKey.setPlanDayOrder(true);
        // 伝票No.
        searchKey.setShipTicketNoOrder(true);
        // 伝票行No.
        searchKey.setShipLineNoOrder(true);
        // 作業枝番
        searchKey.setBranchNoOrder(true);

        /* 取得項目 */

        // 予定日
        searchKey.setPlanDayCollect();
        // 伝票No.
        searchKey.setShipTicketNoCollect();
        // 伝票行No.
        searchKey.setShipLineNoCollect();
        // 作業枝番
        searchKey.setBranchNoCollect();
        // バッチNo.
        searchKey.setBatchNoCollect();
        // オーダNo.
        searchKey.setOrderNoCollect();
        // 出荷先コード
        searchKey.setCustomerCodeCollect();
        // 出荷先名称
        searchKey.setCollect(Customer.CUSTOMER_NAME);
        // 商品コード
        searchKey.setItemCodeCollect();
        // 商品名称
        searchKey.setCollect(Item.ITEM_NAME);
        // ケース入数
        searchKey.setCollect(Item.ENTERING_QTY);
        // 出庫予定数
        searchKey.setPlanQtyCollect();
        // 実績数
        searchKey.setResultQtyCollect();
        // 欠品数
        searchKey.setShortageQtyCollect();
        // 予定エリアNo.
        searchKey.setPlanAreaNoCollect();
        // 予定棚No.
        searchKey.setPlanLocationNoCollect();
        // 予定ロットNo.
        searchKey.setPlanLotNoCollect();
        // JANコード
        searchKey.setCollect(Item.JAN);
        // ケースITF
        searchKey.setCollect(Item.ITF);
        // 状態フラグ(作業状態)
        searchKey.setStatusFlagCollect();
        // スケジュール処理フラグ(開始状態)
        searchKey.setSchFlagCollect();

        return searchKey;

    }

}
//end of class
