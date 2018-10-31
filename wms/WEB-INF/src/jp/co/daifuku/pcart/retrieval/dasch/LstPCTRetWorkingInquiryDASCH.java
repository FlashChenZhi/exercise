// $Id: LstPCTRetWorkingInquiryDASCH.java 4689 2009-07-16 00:23:45Z okayama $
package jp.co.daifuku.pcart.retrieval.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.retrieval.dasch.LstPCTRetWorkingInquiryDASCHParams.*;

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
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 作業状況照会に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4689 $, $Date:: 2009-07-16 09:23:45 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class LstPCTRetWorkingInquiryDASCH
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
    public LstPCTRetWorkingInquiryDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // Create Finder Object
        _finder = new PCTRetWorkInfoFinder(getConnection());

        // Initialize record counts
        // データ件数初期化(Excel大量出力対応)
        _finder.open(isForwardOnly());

        // Create Search Key and search for Records
        _finder.search(createSearchKey(p, true));

        _current = -1;

        setMessage("6001013");
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
        _current++;
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
        // get Next entity from finder class
        PCTRetWorkInfo[] ents = (PCTRetWorkInfo[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (PCTRetWorkInfo ent : ents)
        {
            // 荷主コード
            p.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 荷主名称
            p.set(CONSIGNOR_NAME, ent.getValue(PCTRetPlan.CONSIGNOR_NAME));
            // 予定日
            p.set(PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            // バッチSeqNo.
            p.set(BATCH_SEQ_NO, ent.getBatchSeqNo());
            // バッチNo.
            p.set(BATCH_NO, ent.getBatchNo());
            // オーダーNo.
            p.set(ORDER_NO, ent.getResultOrderNo());
            // 得意先コード
            p.set(REGULAR_CUSTOMER_CODE, ent.getRegularCustomerCode());
            // 得意先名称
            p.set(REGULAR_CUSTOMER_NAME, ent.getValue(PCTRetPlan.REGULAR_CUSTOMER_NAME));
            // 出荷先コード
            p.set(CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名称
            p.set(CUSTOMER_NAME, ent.getValue(PCTRetPlan.CUSTOMER_NAME));
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // JANコード
            p.set(JAN, ent.getValue(PCTRetPlan.JAN));
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(PCTRetPlan.ITEM_NAME));
            // 予定数
            p.set(PLAN_QTY, ent.getPlanQty());
            // 実績数
            p.set(RESULT_QTY, ent.getResultQty());
            // 状態フラグ
            // 完了の場合
            if (PCTRetrievalInParameter.STATUS_FLAG_COMPLETION.equals(ent.getStatusFlag())
                    && ent.getPlanQty() == ent.getResultQty())
            {
                p.set(JOB_STATUS, DisplayResource.getPctWorkingStatus2(ent.getStatusFlag()));
            }
            // 欠品完了の場合
            else if (PCTRetrievalInParameter.STATUS_FLAG_COMPLETION.equals(ent.getStatusFlag())
                    && ent.getPlanQty() != ent.getResultQty())
            {
                p.set(JOB_STATUS,
                        DisplayResource.getPctWorkingStatus2(PCTRetrievalInParameter.STATUS_FLAG_SHORTAGE_COMPLETION));
            }
            // 上記以外
            else
            {
                p.set(JOB_STATUS, DisplayResource.getPctWorkingStatus2(ent.getStatusFlag()));
            }
            // ロット入数
            p.set(LOT_QTY, ent.getValue(PCTRetPlan.LOT_ENTERING_QTY));
            // エリアNo.
            p.set(AREA_NO, ent.getPlanAreaNo());
            // ゾーンNo.
            p.set(ZONE_NO, ent.getPlanZoneNo());
            // 棚No.
            p.set(LOCATION_NO, ent.getPlanLocationNo());
            // ケースITF
            p.set(ITF, ent.getValue(PCTRetPlan.ITF));
            // ボールITF
            p.set(BUNDLE_ITF, ent.getValue(PCTRetPlan.BUNDLE_ITF));
            // 端末No.
            p.set(TERMINAL_NO, ent.getTerminalNo());
            // ユーザ名称
            p.set(USER_NAME, ent.getValue(Com_loginuser.USERNAME));
        }
        // return Pram objstc
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
        PCTRetWorkInfoHandler handler = new PCTRetWorkInfoHandler(getConnection());

        // find count
        _total = handler.count(createSearchKey(p, false));

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
        List<Params> params = new ArrayList<Params>();
        PCTRetWorkInfo[] ents = (PCTRetWorkInfo[])_finder.getEntities(start, start + cnt);

        for (PCTRetWorkInfo ent : ents)
        {
            Params p = new Params();
            // 荷主コード
            p.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 荷主名称
            p.set(CONSIGNOR_NAME, ent.getValue(PCTRetPlan.CONSIGNOR_NAME));
            // 予定日
            p.set(PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            // バッチSeqNo.
            p.set(BATCH_SEQ_NO, ent.getBatchSeqNo());
            // バッチNo.
            p.set(BATCH_NO, ent.getBatchNo());
            // オーダーNo.
            p.set(ORDER_NO, ent.getResultOrderNo());
            // 得意先コード
            p.set(REGULAR_CUSTOMER_CODE, ent.getRegularCustomerCode());
            // 得意先名称
            p.set(REGULAR_CUSTOMER_NAME, ent.getValue(PCTRetPlan.REGULAR_CUSTOMER_NAME));
            // 出荷先コード
            p.set(CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名称
            p.set(CUSTOMER_NAME, ent.getValue(PCTRetPlan.CUSTOMER_NAME));
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // JANコード
            p.set(JAN, ent.getValue(PCTRetPlan.JAN));
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(PCTRetPlan.ITEM_NAME));
            // 予定数
            p.set(PLAN_QTY, ent.getPlanQty());
            // 実績数
            p.set(RESULT_QTY, ent.getResultQty());
            // 状態フラグ
            // 完了の場合
            if (PCTRetrievalInParameter.STATUS_FLAG_COMPLETION.equals(ent.getStatusFlag())
                    && ent.getPlanQty() == ent.getResultQty())
            {
                p.set(JOB_STATUS, DisplayResource.getPctWorkingStatus2(ent.getStatusFlag()));
            }
            // 欠品完了の場合
            else if (PCTRetrievalInParameter.STATUS_FLAG_COMPLETION.equals(ent.getStatusFlag())
                    && ent.getPlanQty() != ent.getResultQty())
            {
                p.set(JOB_STATUS,
                        DisplayResource.getPctWorkingStatus2(PCTRetrievalInParameter.STATUS_FLAG_SHORTAGE_COMPLETION));
            }
            // 上記以外
            else
            {
                p.set(JOB_STATUS, DisplayResource.getPctWorkingStatus2(ent.getStatusFlag()));
            }
            // ロット入数
            p.set(LOT_QTY, ent.getValue(PCTRetPlan.LOT_ENTERING_QTY));
            // エリアNo.
            p.set(AREA_NO, ent.getPlanAreaNo());
            // ゾーンNo.
            p.set(ZONE_NO, ent.getPlanZoneNo());
            // 棚No.
            p.set(LOCATION_NO, ent.getPlanLocationNo());
            // ケースITF
            p.set(ITF, ent.getValue(PCTRetPlan.ITF));
            // ボールITF
            p.set(BUNDLE_ITF, ent.getValue(PCTRetPlan.BUNDLE_ITF));
            // 端末No.
            p.set(TERMINAL_NO, ent.getTerminalNo());
            // ユーザ名称
            p.set(USER_NAME, ent.getValue(Com_loginuser.USERNAME));

            params.add(p);
        }
        return params;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        PCTRetWorkInfoSearchKey key = new PCTRetWorkInfoSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
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
        // バッチNo
        if (!StringUtil.isBlank(param.getString(BATCH_NO)))
        {
            key.setBatchNo(param.getString(BATCH_NO));
        }
        // バッチSeqNo
        if (!StringUtil.isBlank(param.getString(BATCH_SEQ_NO)))
        {
            key.setBatchSeqNo(param.getString(BATCH_SEQ_NO));
        }
        // エリア
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            // 全エリア以外が選択された場合
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
            key.setResultOrderNo(param.getString(ORDER_NO));
        }
        // 商品コード
        if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
        {
            key.setItemCode(param.getString(ITEM_CODE));
        }
        // 状態フラグ全てがセットされていない場合
        if (!PCTRetrievalInParameter.SEARCH_ALL.equals(param.getString(JOB_STATUS)))
        {
            // 完了の場合
            if (PCTRetrievalInParameter.STATUS_FLAG_COMPLETION.equals(param.getString(JOB_STATUS)))
            {
                key.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_COMPLETION, "=", "(", "", true);
                key.setShortageQty(0, "=", "", ")", true);
            }
            // 欠品完了の場合
            else if (PCTRetrievalInParameter.STATUS_FLAG_SHORTAGE_COMPLETION.equals(param.getString(JOB_STATUS)))
            {
                key.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_COMPLETION, "=", "(", "", false);
                key.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION, "=", "", ")", true);
                key.setKey(PCTRetPlan.SHORTAGE_QTY, 0, ">", "", "", true);
            }
            // 上記以外
            else
            {
                // 状態フラグ
                key.setStatusFlag(param.getString(JOB_STATUS));
            }
        }
        else
        {
            key.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");
        }
        // ユーザID
        if (!StringUtil.isBlank(param.getString(USER)))
        {
            key.setUserId(param.getString(USER));
        }

        // 結合
        key.setJoin(PCTRetWorkInfo.PLAN_UKEY, PCTRetPlan.PLAN_UKEY);

        key.setJoin(PCTRetWorkInfo.USER_ID, "", Com_loginuser.USERID, "(+)");

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            // 荷主コード
            key.setConsignorCodeCollect();
            // 荷主名称
            key.setCollect(PCTRetPlan.CONSIGNOR_NAME);
            // 予定日
            key.setPlanDayCollect();
            // バッチSeqNo.
            key.setBatchSeqNoCollect();
            // バッチNo.
            key.setBatchNoCollect();
            // オーダーNo.
            key.setOrderNoCollect();
            // 実績オーダーNo.
            key.setResultOrderNoCollect();
            // 得意先コード
            key.setRegularCustomerCodeCollect();
            // 得意先名称
            key.setCollect(PCTRetPlan.REGULAR_CUSTOMER_NAME);
            // 出荷先コード
            key.setCustomerCodeCollect();
            // 出荷先名称
            key.setCollect(PCTRetPlan.CUSTOMER_NAME);
            // オーダー分割No.
            key.setOrderSeqCollect();
            // 商品コード
            key.setItemCodeCollect();
            // JANコード
            key.setCollect(PCTRetPlan.JAN);
            // 商品名称
            key.setCollect(PCTRetPlan.ITEM_NAME);
            // 予定数
            key.setPlanQtyCollect();
            // 実績数
            key.setResultQtyCollect();
            // ユーザ名称
            key.setCollect(Com_loginuser.USERNAME);
            // 状態フラグ
            key.setStatusFlagCollect();
            // ロット入数
            key.setCollect(PCTRetPlan.LOT_ENTERING_QTY);
            // 予定エリア
            key.setPlanAreaNoCollect();
            // ゾーンNo.
            key.setPlanZoneNoCollect();
            // 予定棚
            key.setPlanLocationNoCollect();
            // ケースITF
            key.setCollect(PCTRetPlan.ITF);
            // ボールITF
            key.setCollect(PCTRetPlan.BUNDLE_ITF);
            // 端末No.
            key.setTerminalNoCollect();


            // 予定日
            key.setPlanDayOrder(true);
            // バッチNo.
            key.setBatchNoOrder(true);
            // バッチSeqNo.
            key.setBatchSeqNoOrder(true);
            // 得意先
            key.setRegularCustomerCodeOrder(true);
            // 出荷先
            key.setCustomerCodeOrder(true);
            // 実績オーダーNo.
            key.setResultOrderNoOrder(true);
            // 商品コード
            key.setItemCodeOrder(true);
            // ロット入数
            key.setOrder(PCTRetPlan.LOT_ENTERING_QTY, true);
            // 作業状態
            key.setStatusFlagOrder(true);
            // 予定エリア
            key.setPlanAreaNoOrder(true);
            // ゾーンNo.
            key.setPlanZoneNoOrder(true);
            // 予定棚
            key.setPlanLocationNoOrder(true);
        }

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
