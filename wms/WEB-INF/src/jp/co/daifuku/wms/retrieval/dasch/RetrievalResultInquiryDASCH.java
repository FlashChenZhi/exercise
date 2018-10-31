package jp.co.daifuku.wms.retrieval.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.retrieval.dasch.RetrievalResultInquiryDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.ResultViewFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewHandler;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 出庫実績照会に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 4690 $, $Date: 2009-07-16 09:24:27 +0900 (木, 16 7 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class RetrievalResultInquiryDASCH
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
     * @param locale ロケーる
     * @param ui ユーザ情報
     */
    public RetrievalResultInquiryDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new ResultViewFinder(getConnection());

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
        ResultView[] restData = (ResultView[])_finder.getEntities(1);

        Params p = new Params();

        // 取得情報をパラメータに編集
        for (ResultView rData : restData)
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
            // 集約条件
            p.set(GROUP_CONDITION, _Sort_Condition);

            // 出庫日
            if (rData.getValue(ResultView.WORK_DAY) != null)
            {
                p.set(PICKING_DATE, WmsFormatter.toDate(rData.getValue(ResultView.WORK_DAY).toString()));
            }
            // 出庫予定日
            p.set(PLAN_DATE, WmsFormatter.toDate(rData.getValue(ResultView.PLAN_DAY).toString()));
            // 伝票No
            p.set(TICKET, rData.getValue(ResultView.SHIP_TICKET_NO));
            // 行No
            p.set(LINE, rData.getValue(ResultView.SHIP_LINE_NO));
            // 作業枝番
            p.set(SERIAL, rData.getValue(ResultView.SHIP_BRANCH_NO));
            // バッチNo
            p.set(BATCH, rData.getValue(ResultView.BATCH_NO));
            // オーダーNo
            p.set(ORDER, rData.getValue(ResultView.ORDER_NO));
            // 出荷先コード
            p.set(CUSTOMER_CODE, rData.getValue(ResultView.CUSTOMER_CODE));
            // 出荷先名称
            p.set(CUSTOMER_NAME, rData.getValue(ResultView.CUSTOMER_NAME));
            // 商品コード
            p.set(ITEM_CODE, rData.getValue(ResultView.ITEM_CODE));
            // 商品名称
            p.set(ITEM_NAME, rData.getValue(ResultView.ITEM_NAME));
            // ロットNo
            p.set(LOT, rData.getValue(ResultView.RESULT_LOT_NO));
            // ケース入数
            int enteringQty = rData.getBigDecimal(ResultView.ENTERING_QTY).intValue();
            p.set(CASE_PACK, enteringQty);
            // 予定ケース数
            p.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(rData.getBigDecimal(ResultView.PLAN_QTY).intValue(),
                    enteringQty));
            // 予定ピース数
            p.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(rData.getBigDecimal(ResultView.PLAN_QTY).intValue(),
                    enteringQty));
            // 実績ケース数
            p.set(RESULT_CASE_QTY, DisplayUtil.getCaseQty(rData.getBigDecimal(ResultView.RESULT_QTY).intValue(),
                    enteringQty));
            // 実績ピース数
            p.set(RESULT_PIECE_QTY, DisplayUtil.getPieceQty(rData.getBigDecimal(ResultView.RESULT_QTY).intValue(),
                    enteringQty));
            // 欠品ケース数
            p.set(SHORTAGE_CASE_QTY, DisplayUtil.getCaseQty(rData.getBigDecimal(ResultView.SHORTAGE_QTY).intValue(),
                    enteringQty));
            // 欠品ピース数
            p.set(SHORTAGE_PIECE_QTY, DisplayUtil.getPieceQty(rData.getBigDecimal(ResultView.SHORTAGE_QTY).intValue(),
                    enteringQty));
            // 出庫エリア
            p.set(PICKING_AREA, rData.getValue(ResultView.RESULT_AREA_NO));
            // 出庫棚
            p.set(PICKING_LOCATION, rData.getValue(ResultView.RESULT_LOCATION_NO));
            // JANコード
            p.set(UPC_CODE, rData.getValue(ResultView.JAN));
            // ケースITF
            p.set(CASE_ITF, rData.getValue(ResultView.ITF));
            // ユーザ名称
            p.set(USER_NAME, rData.getValue(ResultView.USER_NAME));
            // 発生日
            p.set(WORK_DATE, rData.getValue(ResultView.REGIST_DATE));
            // 出庫日
            p.set(WORK_TIME, rData.getValue(ResultView.REGIST_DATE));
            // 端末No
            p.set(TERMINAL, rData.getValue(ResultView.TERMINAL_NO));
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
        ResultViewHandler handler = new ResultViewHandler(getConnection());
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
        ResultView[] restData = (ResultView[])_finder.getEntities(start, start + cnt);

        // 取得情報をパラメータに編集
        for (ResultView rData : restData)
        {
            Params p = new Params();

            // 出庫日
            if (rData.getValue(ResultView.WORK_DAY) != null)
            {
                p.set(PICKING_DATE, WmsFormatter.toDate(rData.getValue(ResultView.WORK_DAY).toString()));
            }
            // 出庫予定日
            p.set(PLAN_DATE, WmsFormatter.toDate(rData.getValue(ResultView.PLAN_DAY).toString()));
            // 伝票No
            p.set(TICKET, rData.getValue(ResultView.SHIP_TICKET_NO));
            // 行No
            p.set(LINE, rData.getValue(ResultView.SHIP_LINE_NO));
            // 作業枝番
            p.set(SERIAL, rData.getValue(ResultView.SHIP_BRANCH_NO));
            // バッチNo
            p.set(BATCH, rData.getValue(ResultView.BATCH_NO));
            // オーダーNo
            p.set(ORDER, rData.getValue(ResultView.ORDER_NO));
            // 出荷先コード
            p.set(CUSTOMER_CODE, rData.getValue(ResultView.CUSTOMER_CODE));
            // 出荷先名称
            p.set(CUSTOMER_NAME, rData.getValue(ResultView.CUSTOMER_NAME));
            // 商品コード
            p.set(ITEM_CODE, rData.getValue(ResultView.ITEM_CODE));
            // 商品名称
            p.set(ITEM_NAME, rData.getValue(ResultView.ITEM_NAME));
            // ロットNo
            p.set(LOT, rData.getValue(ResultView.RESULT_LOT_NO));
            // ケース入数
            int enteringQty = rData.getBigDecimal(ResultView.ENTERING_QTY).intValue();
            p.set(CASE_PACK, enteringQty);
            // 予定ケース数
            p.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(rData.getBigDecimal(ResultView.PLAN_QTY).intValue(),
                    enteringQty));
            // 予定ピース数
            p.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(rData.getBigDecimal(ResultView.PLAN_QTY).intValue(),
                    enteringQty));
            // 実績ケース数
            p.set(RESULT_CASE_QTY, DisplayUtil.getCaseQty(rData.getBigDecimal(ResultView.RESULT_QTY).intValue(),
                    enteringQty));
            // 実績ピース数
            p.set(RESULT_PIECE_QTY, DisplayUtil.getPieceQty(rData.getBigDecimal(ResultView.RESULT_QTY).intValue(),
                    enteringQty));
            // 欠品ケース数
            p.set(SHORTAGE_CASE_QTY, DisplayUtil.getCaseQty(rData.getBigDecimal(ResultView.SHORTAGE_QTY).intValue(),
                    enteringQty));
            // 欠品ピース数
            p.set(SHORTAGE_PIECE_QTY, DisplayUtil.getPieceQty(rData.getBigDecimal(ResultView.SHORTAGE_QTY).intValue(),
                    enteringQty));
            // 出庫エリア
            p.set(PICKING_AREA, rData.getValue(ResultView.RESULT_AREA_NO));
            // 出庫棚
            p.set(PICKING_LOCATION, rData.getValue(ResultView.RESULT_LOCATION_NO));
            // JANコード
            p.set(UPC_CODE, rData.getValue(ResultView.JAN));
            // ケースITF
            p.set(CASE_ITF, rData.getValue(ResultView.ITF));
            // ユーザ名称
            p.set(USER_NAME, rData.getValue(ResultView.USER_NAME));
            // 発生日
            p.set(WORK_DATE, rData.getValue(ResultView.REGIST_DATE));
            // 出庫日
            p.set(WORK_TIME, rData.getValue(ResultView.REGIST_DATE));
            // 端末No
            p.set(TERMINAL, rData.getValue(ResultView.TERMINAL_NO));

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
        // (1)パラメータで指定された内容を元に、DNRESULTVIEWテーブルの検索条件を作成します。<BR>
        ResultViewSearchKey searchKey = new ResultViewSearchKey();

        // 検索条件をセットします。
        // 荷主コード
        if (!StringUtil.isBlank(p.getString(CONSIGNOR_CODE)))
        {
            searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        }

        // 開始出庫日,終了出庫日
        if (!StringUtil.isBlank(p.getDate(RETRIEVAL_DATE_FROM)))
        {
            if (!StringUtil.isBlank(p.getDate(RETRIEVAL_DATE_TO)))
            {
                if (p.getDate(RETRIEVAL_DATE_TO).compareTo(p.getDate(RETRIEVAL_DATE_FROM)) >= 0)
                {
                    searchKey.setWorkDay(WmsFormatter.toParamDate(p.getDate(RETRIEVAL_DATE_FROM)), ">=");
                    searchKey.setWorkDay(WmsFormatter.toParamDate(p.getDate(RETRIEVAL_DATE_TO)), "<=");
                }
                else
                {
                    searchKey.setWorkDay(WmsFormatter.toParamDate(p.getDate(RETRIEVAL_DATE_FROM)), "<=");
                    searchKey.setWorkDay(WmsFormatter.toParamDate(p.getDate(RETRIEVAL_DATE_TO)), ">=");
                }
            }
            else
            {
                searchKey.setWorkDay(WmsFormatter.toParamDate(p.getDate(RETRIEVAL_DATE_FROM)), ">=");
            }
        }
        else if (!StringUtil.isBlank(p.getDate(RETRIEVAL_DATE_TO)))
        {
            searchKey.setWorkDay(WmsFormatter.toParamDate(p.getDate(RETRIEVAL_DATE_TO)), "<=");
        }

        // 伝票No.
        if (!StringUtil.isBlank(p.getString(SLIP_NUMBER)))
        {
            searchKey.setShipTicketNo(p.getString(SLIP_NUMBER));
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

        // 出荷先コード
        if (!StringUtil.isBlank(p.getString(CUSTOMER_CODE_NO)))
        {
            searchKey.setCustomerCode(p.getString(CUSTOMER_CODE_NO));
        }

        // 出庫エリア
        if (WmsParam.ALL_AREA_NO.equals(p.getString(RETRIEVAL_AREA)))
        {
            // 全エリアの場合は移動中エリア以外
            searchKey.setJoin(ResultView.RESULT_AREA_NO, "", Area.AREA_NO, "(+)");
            searchKey.setKey(Area.AREA_TYPE, SystemDefine.AREA_TYPE_MOVING, "!=", "(", "", false);
            searchKey.setKey(Area.AREA_NO, null, "=", "", ")", true);
        }
        else
        {
            searchKey.setResultAreaNo(p.getString(RETRIEVAL_AREA));
        }

        // 作業区分(出庫：03)
        searchKey.setJobType(SystemDefine.JOB_TYPE_RETRIEVAL);

        //集約条件
        if (p.getString(GROUP_CONDITION).equals(Parameter.COLLECT_CONDITION_PLAN))
        {
            // 予定単位
            // ソート順(出庫予定日、伝票No、行No、作業枝番)
            searchKey.setPlanDayOrder(true);
            searchKey.setShipTicketNoOrder(true);
            searchKey.setShipLineNoOrder(true);
            searchKey.setShipBranchNoOrder(true);

            // グループ条件(荷主コード、出庫予定日、バッチNo、伝票No、行No、作業枝番、ケース入数)
            searchKey.setConsignorCodeGroup();
            searchKey.setPlanDayGroup();
            searchKey.setBatchNoGroup();
            searchKey.setShipTicketNoGroup();
            searchKey.setShipLineNoGroup();
            searchKey.setShipBranchNoGroup();
            searchKey.setEnteringQtyGroup();

            // 表示項目
            // 荷主コード
            searchKey.setConsignorCodeCollect();
            // 予定日
            searchKey.setPlanDayCollect();
            // バッチNo
            searchKey.setBatchNoCollect();
            // 出荷伝票No.
            searchKey.setShipTicketNoCollect();
            // 出荷伝票行
            searchKey.setShipLineNoCollect();
            // 出荷伝票枝番
            searchKey.setShipBranchNoCollect();
            // ケース入数
            searchKey.setEnteringQtyCollect();

            // 商品コード
            searchKey.setItemCodeCollect("MAX");
            // 商品名称
            searchKey.setItemNameCollect("MAX");
            // オーダNo
            searchKey.setOrderNoCollect("MAX");
            // 出荷先コード
            searchKey.setCustomerCodeCollect("MAX");
            // 出荷先名称
            searchKey.setCustomerNameCollect("MAX");
            // JANコード
            searchKey.setJanCollect("MAX");
            // ケースITF
            searchKey.setItfCollect("MAX");
            // 予定数
            searchKey.setPlanQtyCollect("SUM");
            // 実績数
            searchKey.setResultQtyCollect("SUM");
            // 欠品数
            searchKey.setShortageQtyCollect("SUM");

            // 予定単位
            _Sort_Condition = DisplayText.getText("RDB-W0003");

        }
        else if (p.getString(GROUP_CONDITION).equals(Parameter.COLLECT_CONDITION_DETAIL))
        {
            // 詳細表示
            // エリア情報との結合指定を行う
            searchKey.setJoin(ResultView.RESULT_AREA_NO, "", Area.AREA_NO, "(+)");

            // ソート順(作業日、出庫予定日、伝票No、行No、作業枝番)
            searchKey.setWorkDayOrder(true);
            searchKey.setPlanDayOrder(true);
            searchKey.setShipTicketNoOrder(true);
            searchKey.setShipLineNoOrder(true);
            searchKey.setShipBranchNoOrder(true);

            // 表示項目
            // 作業日
            searchKey.setWorkDayCollect();
            // 予定日
            searchKey.setPlanDayCollect();
            // 出荷伝票No.
            searchKey.setShipTicketNoCollect();
            // 出荷伝票行
            searchKey.setShipLineNoCollect();
            // 出荷伝票枝番
            searchKey.setShipBranchNoCollect();
            // バッチNo
            searchKey.setBatchNoCollect();
            // オーダNo
            searchKey.setOrderNoCollect();
            // 出荷先コード
            searchKey.setCustomerCodeCollect();
            // 出荷先名称
            searchKey.setCustomerNameCollect();
            // 商品コード
            searchKey.setItemCodeCollect();
            // 商品名称
            searchKey.setItemNameCollect();
            // 実績エリア
            searchKey.setResultAreaNoCollect();
            // 実績棚
            searchKey.setResultLocationNoCollect();
            // 実績ロットNo.
            searchKey.setResultLotNoCollect();
            // JANコード
            searchKey.setJanCollect();
            // ケースITF
            searchKey.setItfCollect();
            // ユーザ名称
            searchKey.setUserNameCollect();
            // 端末No.
            searchKey.setTerminalNoCollect();
            // ｼｽﾃﾑ登録日時
            searchKey.setRegistDateCollect();
            // ケース入数
            searchKey.setEnteringQtyCollect();
            // 予定数
            searchKey.setPlanQtyCollect();
            // 実績数
            searchKey.setResultQtyCollect();
            // 欠品数
            searchKey.setShortageQtyCollect();
            // エリア名称
            searchKey.setCollect(Area.AREA_NAME);

            // 詳細表示
            _Sort_Condition = DisplayText.getText("RDB-W0007");

        }
        return searchKey;
    }

}
//end of class
