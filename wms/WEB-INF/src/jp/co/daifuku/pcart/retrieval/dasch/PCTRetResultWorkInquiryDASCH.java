// $Id: PCTRetResultWorkInquiryDASCH.java 4689 2009-07-16 00:23:45Z okayama $
package jp.co.daifuku.pcart.retrieval.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.retrieval.dasch.PCTRetResultWorkInquiryDASCHParams.*;

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
import jp.co.daifuku.wms.base.dbhandler.PCTRetResultFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetResultHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetResultSearchKey;
import jp.co.daifuku.wms.base.entity.PCTRetResult;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * PCT作業実績照会に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4689 $, $Date:: 2009-07-16 09:23:45 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class PCTRetResultWorkInquiryDASCH
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
    public PCTRetResultWorkInquiryDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new PCTRetResultFinder(getConnection());

        // Initialize record counts
        // データ件数初期化(Excel大量出力対応)
        _finder.open(isForwardOnly());

        // Create Search Key and search for Records
        _finder.search(createSearchKey(p, true));

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
        //throw new ScheduleException("This method is not implemented.");
        // get Next entity from finder class
        PCTRetResult[] ents = (PCTRetResult[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (PCTRetResult ent : ents)
        {
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());

            // 荷主コード
            p.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 荷主名称
            p.set(CONSIGNOR_NAME, (String)ent.getValue(PCTRetResult.CONSIGNOR_NAME, ""));
            // 作業日
            p.set(WORK_DAY, WmsFormatter.toDate(ent.getWorkDay()));
            // バッチSeqNo.
            p.set(BATCH_SEQ_NO, ent.getBatchSeqNo());
            // バッチNo.
            p.set(BATCH_NO, ent.getBatchNo());
            // 得意先コード
            p.set(REGULAR_CUSTOMER_CODE, ent.getRegularCustomerCode());
            // 得意先名称
            p.set(REGULAR_CUSTOMER_NAME, (String)ent.getValue(PCTRetResult.REGULAR_CUSTOMER_NAME, ""));
            // 出荷先コード
            p.set(CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名称
            p.set(CUSTOMER_NAME, (String)ent.getValue(PCTRetResult.CUSTOMER_NAME, ""));
            // 実績オーダーNo.
            p.set(ORDER_NO, ent.getResultOrderNo());
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, (String)ent.getValue(PCTRetResult.ITEM_NAME, ""));
            // ロット入数
            p.set(LOT_QTY, ent.getLotEnteringQty());
            // 予定数
            p.set(PLAN_QTY, ent.getPlanQty());
            // 実績数
            p.set(RESULT_QTY, ent.getResultQty());
            // 状態フラグ
            // 完了の場合
            if (PCTRetrievalInParameter.STATUS_FLAG_COMPLETION.equals(ent.getStatusFlag())
                    && ent.getPlanQty() == ent.getResultQty())
            {
                p.set(JOB_STATUS, DisplayResource.getPctWorkingStatus(ent.getStatusFlag()));
            }
            // 欠品完了の場合
            else if (PCTRetrievalInParameter.STATUS_FLAG_COMPLETION.equals(ent.getStatusFlag())
                    && ent.getPlanQty() != ent.getResultQty())
            {
                p.set(JOB_STATUS,
                        DisplayResource.getPctWorkingStatus(PCTRetrievalInParameter.STATUS_FLAG_SHORTAGE_COMPLETION));
            }
            // 上記以外
            else
            {
                p.set(JOB_STATUS, DisplayResource.getPctWorkingStatus(ent.getStatusFlag()));
            }
            // 予定エリア
            p.set(AREA_NO, ent.getPlanAreaNo());
            // ゾーンNo.
            p.set(ZONE_NO, ent.getPlanZoneNo());
            // 予定棚
            p.set(LOCATION_NO, ent.getPlanLocationNo());
            // ユーザ名称
            p.set(USER_NAME, (String)ent.getValue(PCTRetResult.USER_NAME, ""));
            // 号機No.
            p.set(TERMINAL_NO, ent.getTerminalNo());
            // JANコード
            p.set(JAN, ent.getJan());
            // ケースITF
            p.set(ITF, ent.getItf());
            // ボールITF
            p.set(BUNDLE_ITF, ent.getBundleItf());

            break;
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
        PCTRetResultHandler handler = new PCTRetResultHandler(getConnection());

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
        //throw new RuntimeException("This method is not implemented.");
        List<Params> params = new ArrayList<Params>();
        PCTRetResult[] ents = (PCTRetResult[])_finder.getEntities(start, start + cnt);

        for (PCTRetResult ent : ents)
        {
            Params p = new Params();
            // 荷主コード
            p.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 荷主名称
            p.set(CONSIGNOR_NAME, (String)ent.getValue(PCTRetResult.CONSIGNOR_NAME, ""));
            // 作業日
            p.set(WORK_DAY, WmsFormatter.toDate(ent.getWorkDay()));
            // バッチSeqNo.
            p.set(BATCH_SEQ_NO, ent.getBatchSeqNo());
            // バッチNo.
            p.set(BATCH_NO, ent.getBatchNo());
            // 得意先コード
            p.set(REGULAR_CUSTOMER_CODE, ent.getRegularCustomerCode());
            // 得意先名称
            p.set(REGULAR_CUSTOMER_NAME, (String)ent.getValue(PCTRetResult.REGULAR_CUSTOMER_NAME, ""));
            // 出荷先コード
            p.set(CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名称
            p.set(CUSTOMER_NAME, (String)ent.getValue(PCTRetResult.CUSTOMER_NAME, ""));
            // 実績オーダーNo.
            p.set(ORDER_NO, ent.getResultOrderNo());
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, (String)ent.getValue(PCTRetResult.ITEM_NAME, ""));
            // ロット入数
            p.set(LOT_QTY, ent.getLotEnteringQty());
            // 予定数
            p.set(PLAN_QTY, ent.getPlanQty());
            // 実績数
            p.set(RESULT_QTY, ent.getResultQty());
            // 状態フラグ
            // 完了の場合
            if (PCTRetrievalInParameter.STATUS_FLAG_COMPLETION.equals(ent.getStatusFlag())
                    && ent.getPlanQty() == ent.getResultQty())
            {
                p.set(JOB_STATUS, DisplayResource.getPctWorkingStatus(ent.getStatusFlag()));
            }
            // 欠品完了の場合
            else if (PCTRetrievalInParameter.STATUS_FLAG_COMPLETION.equals(ent.getStatusFlag())
                    && ent.getPlanQty() != ent.getResultQty())
            {
                p.set(JOB_STATUS,
                        DisplayResource.getPctWorkingStatus(PCTRetrievalInParameter.STATUS_FLAG_SHORTAGE_COMPLETION));
            }
            // 上記以外
            else
            {
                p.set(JOB_STATUS, DisplayResource.getPctWorkingStatus(ent.getStatusFlag()));
            }
            // 予定エリア
            p.set(AREA_NO, ent.getPlanAreaNo());
            // ゾーンNo.
            p.set(ZONE_NO, ent.getPlanZoneNo());
            // 予定棚
            p.set(LOCATION_NO, ent.getPlanLocationNo());
            // ユーザ名称
            p.set(USER_NAME, (String)ent.getValue(PCTRetResult.USER_NAME, ""));
            // 号機No.
            p.set(TERMINAL_NO, ent.getTerminalNo());
            // JANコード
            p.set(JAN, ent.getJan());
            // ケースITF
            p.set(ITF, ent.getItf());
            // ボールITF
            p.set(BUNDLE_ITF, ent.getBundleItf());

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
        PCTRetResultSearchKey key = new PCTRetResultSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        // 荷主コード
        if (!StringUtil.isBlank(param.getString(CONSIGNOR_CODE)))
        {
            key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        }
        // 作業日
        if (!StringUtil.isBlank(param.getString(WORK_DAY)))
        {
            key.setWorkDay(WmsFormatter.toParamDate(param.getDate(WORK_DAY)));
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
        // 予定エリア
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            // 全エリア以外が選ばれた場合
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
        // 実績オーダーNo.
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
                key.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_COMPLETION, "=", "(", "", true);
                key.setShortageQty(0, "!=", "", ")", true);
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
        if (!StringUtil.isBlank(param.getString(USER_ID)))
        {
            key.setUserId(param.getString(USER_ID));
        }

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            // 表示順
            // 作業日
            key.setWorkDayOrder(true);
            // バッチNo.
            key.setBatchNoOrder(true);
            // バッチSeqNo
            key.setBatchSeqNoOrder(true);
            // 得意先コード
            key.setRegularCustomerCodeOrder(true);
            // 出荷先コード
            key.setCustomerCodeOrder(true);
            // オーダーNo.
            key.setResultOrderNoOrder(true);
            // 商品コード
            key.setItemCodeOrder(true);
            // ロット入数
            key.setLotEnteringQtyOrder(true);
            // 作業状態
            key.setStatusFlagOrder(true);
            // 予定エリア
            key.setPlanAreaNoOrder(true);
            // ゾーンNo.
            key.setPlanZoneNoOrder(true);
            // 予定棚
            key.setPlanLocationNoOrder(true);

            // 取得項目
            // 荷主コード
            key.setConsignorCodeCollect();
            // 荷主名称
            key.setConsignorNameCollect();
            // 作業日
            key.setWorkDayCollect();
            // バッチSeqNo.
            key.setBatchSeqNoCollect();
            // バッチNo.
            key.setBatchNoCollect();
            // 得意先コード
            key.setRegularCustomerCodeCollect();
            // 得意先名称
            key.setRegularCustomerNameCollect();
            // 出荷先コード
            key.setCustomerCodeCollect();
            // 出荷先名称
            key.setCustomerNameCollect();
            // 実績オーダーNo.
            key.setResultOrderNoCollect();
            // 商品コード
            key.setItemCodeCollect();
            // 商品名称
            key.setItemNameCollect();
            // ロット入数
            key.setLotEnteringQtyCollect();
            // 予定数
            key.setPlanQtyCollect();
            // 実績数
            key.setResultQtyCollect();
            // 状態フラグ
            key.setStatusFlagCollect();
            // 予定エリア
            key.setPlanAreaNoCollect();
            // ゾーンNo.
            key.setPlanZoneNoCollect();
            // 予定棚
            key.setPlanLocationNoCollect();
            // ユーザ名称
            key.setUserNameCollect();
            // 号機No.
            key.setTerminalNoCollect();
            // JANコード
            key.setJanCollect();
            // ケースITF
            key.setItfCollect();
            // ボールITF
            key.setBundleItfCollect();
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
