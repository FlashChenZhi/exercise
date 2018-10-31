// $Id: PCTRetPlanWorkInquiryDASCH.java 4689 2009-07-16 00:23:45Z okayama $
package jp.co.daifuku.pcart.retrieval.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.pcart.retrieval.dasch.PCTRetPlanWorkInquiryDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 作業予定照会に必要なリストボックスや帳票の検索処理を行います。
 *
 * Designer : H.Okayama<BR>
 * Maker : H.Okayama<BR>
 * @version $Revision: 4689 $, $Date:: 2009-07-16 09:23:45 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class PCTRetPlanWorkInquiryDASCH
        extends AbstractWmsDASCH
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
     * @param locale ロケール
     * @param ui ユーザ情報
     */
    public PCTRetPlanWorkInquiryDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * DBの検索を行います。
     * 帳票出力、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public void search(Params p)
            throws CommonException
    {
        // PCT出庫予定情報ファインダーの生成
        _finder = new PCTRetPlanFinder(getConnection());

        // データ件数初期化(Excel大量出力対応)
        _finder.open(isForwardOnly());

        // 検索を行い、結果を保持
        _finder.search(createSearchKey(p, true));

        // 初期位置を記憶
        _current = -1;
    }

    /**
     * 次のデータが存在するかどうかを判定します。<BR>
     * 帳票出力で使用します。
     * @return データが存在する場合はtrueを返します。
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean next()
            throws CommonException
    {
        // 初期位置の移動
        _current++;

        // 存在すればtrue、違えばfalse
        return _total > _current;
    }

    /**
     * データを1件返します。<BR>
     * 帳票出力で使用します。
     * @return 取得データ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public Params get()
            throws CommonException
    {
        // PCT出庫予定情報エンティティ
        PCTRetPlan[] ents = (PCTRetPlan[])_finder.getEntities(1);
        // パラメータの生成
        Params p = new Params();

        // 一件のみ繰り返し
        for (PCTRetPlan ent : ents)
        {
            // システム日付
            p.set(SYS_DAY, getPrintDate());
            // システム日時
            p.set(SYS_TIME, getPrintDate());
            // 端末No.
            p.set(TERMINAL_NO, getTerminalNo());
            // 荷主コード
            p.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 荷主名称
            p.set(CONSIGNOR_NAME, ent.getValue(PCTRetPlan.CONSIGNOR_NAME, ""));
            // 予定日
            p.set(PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            // オーダーNo.
            p.set(ORDER_NO, ent.getPlanOrderNo());
            // バッチNo.
            p.set(BATCH_NO, ent.getBatchNo());
            // バッチSeqNo.
            p.set(BATCH_SEQ_NO, ent.getBatchSeqNo());
            // 得意先コード
            p.set(REGULAR_CUSTOMER_CODE, ent.getRegularCustomerCode());
            // 得意先名称
            p.set(REGULAR_CUSTOMER_NAME, ent.getValue(PCTRetPlan.REGULAR_CUSTOMER_NAME, ""));
            // 出荷先コード
            p.set(CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名称
            p.set(CUSTOMER_NAME, ent.getValue(PCTRetPlan.CUSTOMER_NAME, ""));
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(PCTRetPlan.ITEM_NAME, ""));
            // JANコード
            p.set(JAN, ent.getJan());
            // ケースITF
            p.set(ITF, ent.getItf());
            // ボールITF
            p.set(BUNDLE_ITF, ent.getBundleItf());
            // ロット入数
            p.set(LOT_QTY, ent.getLotEnteringQty());
            // 予定数
            p.set(PLAN_QTY, ent.getPlanQty());
            // 実績数
            p.set(RESULT_QTY, ent.getResultQty());
            // 状態フラグ
            // 欠品完了の場合
            if (PCTRetrievalInParameter.STATUS_FLAG_COMPLETION.equals(ent.getStatusFlag())
                    && ent.getPlanQty() != ent.getResultQty())
            {
                p.set(JOB_STATUS,
                        DisplayResource.getPctWorkingStatus(PCTRetrievalInParameter.STATUS_FLAG_SHORTAGE_COMPLETION));
            }
            // 上記以外の場合
            else
            {
                p.set(JOB_STATUS, DisplayResource.getPctWorkingStatus(ent.getStatusFlag()));
            }
            // エリアNo.
            p.set(AREA_NO, ent.getPlanAreaNo());
            // ゾーンNo.
            p.set(ZONE_NO, ent.getPlanZoneNo());
            // 棚No.
            p.set(LOCATION_NO, ent.getPlanLocationNo());

            // 一件取得し処理抜け
            break;
        }
        // 設定したパラメータを返却
        return p;
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
     * 帳票発行、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @return データ件数
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected int actualCount(Params p)
            throws CommonException
    {
        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler handler = new PCTRetPlanHandler(getConnection());

        // 件数を取得
        _total = handler.count(createSearchKey(p, false));

        // 取得した件数を返却
        return _total;
    }

    /**
     * 検索したデータのうち、start番目からcntで指定された件数のデータを返します。
     * 一覧表示で使用します。
     *
     * @param start 開始インデックス
     * @param cnt 件数
     * @return 対象データのリスト
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        // PCT出庫予定情報エンティティの生成 & 検索結果取得
        PCTRetPlan[] ents = (PCTRetPlan[])_finder.getEntities(start, start + cnt);
        // パラメータ配列の生成
        List<Params> params = new ArrayList<Params>();
        // パラメータの生成
        Params p = new Params();

        // 取得した件数分繰り返し
        for (PCTRetPlan ent : ents)
        {
            // パラメータの初期化
            p = new Params();

            // システム日付
            p.set(SYS_DAY, getPrintDate());
            // システム日時
            p.set(SYS_TIME, getPrintDate());
            // 端末No.
            p.set(TERMINAL_NO, getTerminalNo());
            // 荷主コード
            p.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 荷主名称
            p.set(CONSIGNOR_NAME, ent.getValue(PCTRetPlan.CONSIGNOR_NAME, ""));
            // 予定日
            p.set(PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            // オーダーNo.
            p.set(ORDER_NO, ent.getPlanOrderNo());
            // バッチNo.
            p.set(BATCH_NO, ent.getBatchNo());
            // バッチSeqNo.
            p.set(BATCH_SEQ_NO, ent.getBatchSeqNo());
            // 得意先コード
            p.set(REGULAR_CUSTOMER_CODE, ent.getRegularCustomerCode());
            // 得意先名称
            p.set(REGULAR_CUSTOMER_NAME, ent.getValue(PCTRetPlan.REGULAR_CUSTOMER_NAME, ""));
            // 出荷先コード
            p.set(CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名称
            p.set(CUSTOMER_NAME, ent.getValue(PCTRetPlan.CUSTOMER_NAME, ""));
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(PCTRetPlan.ITEM_NAME, ""));
            // JANコード
            p.set(JAN, ent.getJan());
            // ケースITF
            p.set(ITF, ent.getItf());
            // ボールITF
            p.set(BUNDLE_ITF, ent.getBundleItf());
            // ロット入数
            p.set(LOT_QTY, ent.getLotEnteringQty());
            // 予定数
            p.set(PLAN_QTY, ent.getPlanQty());
            // 実績数
            p.set(RESULT_QTY, ent.getResultQty());
            // 状態フラグ
            // 欠品完了の場合
            if (PCTRetrievalInParameter.STATUS_FLAG_COMPLETION.equals(ent.getStatusFlag())
                    && ent.getPlanQty() != ent.getResultQty())
            {
                p.set(JOB_STATUS,
                        DisplayResource.getPctWorkingStatus(PCTRetrievalInParameter.STATUS_FLAG_SHORTAGE_COMPLETION));
            }
            // 上記以外の場合
            else
            {
                p.set(JOB_STATUS, DisplayResource.getPctWorkingStatus(ent.getStatusFlag()));
            }
            // エリアNo.
            p.set(AREA_NO, ent.getPlanAreaNo());
            // ゾーンNo.
            p.set(ZONE_NO, ent.getPlanZoneNo());
            // 棚No.
            p.set(LOCATION_NO, ent.getPlanLocationNo());

            // 設定したパラメータを配列に格納
            params.add(p);
        }
        // 生成したパラメータ配列を返却
        return params;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 検索条件のセットを行います。<BR>
     * 
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return SearchKey PCT出庫予定情報検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        // PCT出庫予定情報検索キー
        PCTRetPlanSearchKey key = new PCTRetPlanSearchKey();

        // 検索条件、集約条件をセットする
        // 荷主コード
        if (!StringUtil.isBlank(param.getString(CONSIGNOR_CODE)))
        {
            key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        }
        // 予定日
        if (!StringUtil.isBlank(param.getDate(PLAN_DAY)))
        {
            key.setPlanDay(WmsFormatter.toParamDate(param.getDate(PLAN_DAY)));
        }
        // バッチNo.
        if (!StringUtil.isBlank(param.getString(BATCH_NO)))
        {
            key.setBatchNo(param.getString(BATCH_NO));
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(param.getString(BATCH_SEQ_NO)))
        {
            key.setBatchSeqNo(param.getString(BATCH_SEQ_NO));
        }
        // エリア
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            // 全エリア以外が選択されている場合
            if (!WmsParam.ALL_AREA_NO.equals(param.getString(AREA_NO)))
            {
                key.setPlanAreaNo(param.getString(AREA_NO));
            }
        }
        // 得意先コード
        if (!StringUtil.isBlank(param.getString(REGULAR_CUSTOMER_CODE)))
        {
            key.setRegularCustomerCode(param.getString(REGULAR_CUSTOMER_CODE));
        }
        // 出荷先コード
        if (!StringUtil.isBlank(param.getString(CUSTOMER_CODE)))
        {
            key.setCustomerCode(param.getString(CUSTOMER_CODE));
        }
        // オーダーNo.
        if (!StringUtil.isBlank(param.getString(ORDER_NO)))
        {
            key.setPlanOrderNo(param.getString(ORDER_NO));
        }
        // 状態フラグ
        if (!PCTRetrievalInParameter.SEARCH_ALL.equals(param.getString(JOB_STATUS)))
        {
            // 重量未登録の場合
            if (PCTRetrievalInParameter.STATUS_FLAG_WEIGHT_UNREGIST.equals(param.getString(JOB_STATUS)))
            {
                // 荷主コード
                key.setJoin(PCTRetPlan.CONSIGNOR_CODE, PCTItem.CONSIGNOR_CODE);
                // 商品コード
                key.setJoin(PCTRetPlan.ITEM_CODE, PCTItem.ITEM_CODE);
                // ロット入数
                key.setJoin(PCTRetPlan.LOT_ENTERING_QTY, PCTItem.LOT_ENTERING_QTY);
                // 単重量
                key.setKey(PCTItem.SINGLE_WEIGHT, 0.0, "<=", "(", "", false);
                String strNull = null;
                key.setKey(PCTItem.SINGLE_WEIGHT, strNull, "=", "", ")", true);
                // 状態フラグ
                key.setStatusFlag(PCTRetPlan.STATUS_FLAG_DELETE, "!=");
            }
            // 完了の場合
            else if (PCTRetrievalInParameter.STATUS_FLAG_COMPLETION.equals(param.getString(JOB_STATUS)))
            {
                // 状態フラグ(完了)
                key.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_COMPLETION, "=", "(", "", true);
                // 欠品数(欠品なし)
                key.setShortageQty(0, "=", "", ")", true);
            }
            // 欠品完了の場合
            else if (PCTRetrievalInParameter.STATUS_FLAG_SHORTAGE_COMPLETION.equals(param.getString(JOB_STATUS)))
            {
                // 状態フラグ(完了)
                key.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_COMPLETION, "=", "(", "", true);
                // 欠品数(欠品あり)
                key.setShortageQty(0, "!=", "", ")", true);
            }
            // 上記以外の場合
            else
            {
                // 状態フラグ(選択された値)
                key.setStatusFlag(param.getString(JOB_STATUS));
            }
        }
        else
        {
            // 状態フラグ(削除以外)
            key.setStatusFlag(PCTRetPlan.STATUS_FLAG_DELETE, "!=");
        }

        // 件数確認ではない場合
        if (isSet)
        {
            // 取得項目
            // 荷主コード
            key.setConsignorCodeCollect();
            // 荷主名称
            key.setConsignorNameCollect();
            // 予定日
            key.setPlanDayCollect();
            // オーダーNo.
            key.setPlanOrderNoCollect();
            // バッチNo.
            key.setBatchNoCollect();
            // バッチSeqNo.
            key.setBatchSeqNoCollect();
            // 得意先コード
            key.setRegularCustomerCodeCollect();
            // 得意先名称
            key.setRegularCustomerNameCollect();
            // 出荷先コード
            key.setCustomerCodeCollect();
            // 出荷先名称
            key.setCustomerNameCollect();
            // 商品コード
            key.setItemCodeCollect();
            // 商品名称
            key.setItemNameCollect();
            // JANコード
            key.setJanCollect();
            // ケースITF
            key.setItfCollect();
            // ボールITF
            key.setBundleItfCollect();
            // ロット入数
            key.setLotEnteringQtyCollect();
            // 予定数
            key.setPlanQtyCollect();
            // 実績数
            key.setResultQtyCollect();
            // 状態フラグ
            key.setStatusFlagCollect();
            // エリアNo.
            key.setPlanAreaNoCollect();
            // ゾーンNo.
            key.setPlanZoneNoCollect();
            // 棚No.
            key.setPlanLocationNoCollect();

            // 並び順
            // 予定日
            key.setPlanDayOrder(true);
            // バッチNo.
            key.setBatchNoOrder(true);
            // バッチSeqNo.
            key.setBatchSeqNoOrder(true);
            // 得意先コード
            key.setRegularCustomerCodeOrder(true);
            // 出荷先コード
            key.setCustomerCodeOrder(true);
            // オーダーNo.
            key.setPlanOrderNoOrder(true);
            // 商品コード
            key.setItemCodeOrder(true);
            // ロット入数
            key.setLotEnteringQtyOrder(true);
            // 作業状態
            key.setStatusFlagOrder(true);
            // エリアNo.
            key.setPlanAreaNoOrder(true);
            // ゾーンNo.
            key.setPlanZoneNoOrder(true);
            // 棚No.
            key.setPlanLotNoOrder(true);
        }
        // 生成したPCT出庫予定情報検索キーを返却
        return key;
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
