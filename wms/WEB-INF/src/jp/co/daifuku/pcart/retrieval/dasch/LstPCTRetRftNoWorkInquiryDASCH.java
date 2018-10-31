// $Id: LstPCTRetRftNoWorkInquiryDASCH.java 4689 2009-07-16 00:23:45Z okayama $
package jp.co.daifuku.pcart.retrieval.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.retrieval.dasch.PCTRetRftNoWorkInquiryDASCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * Pカート別作業照会一覧に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4689 $, $Date: 2009-07-16 09:23:45 +0900 (木, 16 7 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class LstPCTRetRftNoWorkInquiryDASCH
        extends AbstractWmsDASCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 保存用のフィールド 商品名称(<code>ITEM_NAME</code>) */
    private FieldName _ITEM_NAME = new FieldName("", "ITEM_NAME");

    /** 保存用のフィールド ユーザ名称(<code>USER_NAME</code>) */
    private FieldName _USER_NAME = new FieldName("", "USER_NAME");

    /** 保存用のフィールド 得意先名称(<code>REGULAR_CUSTOMER_NAME</code>) */
    private FieldName _REGULAR_CUSTOMER_NAME = new FieldName("", "REGULAR_CUSTOMER_NAME");

    /** 保存用のフィールド 出荷先名称(<code>CUSTOMER_NAME</code>) */
    private FieldName _CUSTOMER_NAME = new FieldName("", "CUSTOMER_NAME");

    /** 保存用のフィールド JANコード(<code>JAN</code>) */
    private FieldName _JAN = new FieldName("", "JAN");

    /** 保存用のフィールド ケースITF(<code>ITF</code>) */
    private FieldName _ITF = new FieldName("", "ITF");

    /** 保存用のフィールド ボールITF(<code>BUNDLE_ITF</code>) */
    private FieldName _BUNDLE_ITF = new FieldName("", "BUNDLE_ITF");

    /** 保存用のフィールド エリアNo.(<code>PLAN_AREA_NO</code>) */
    private FieldName _PLAN_AREA_NO = new FieldName("", "PLAN_AREA_NO");

    /** 保存用のフィールド エリア名称(<code>AREA_NAME</code>) */
    private FieldName _AREA_NAME = new FieldName("", "AREA_NAME");

    /** 保存用のフィールド 号機No.(<code>RFT_NO</code>) */
    private FieldName _RFT_NO = new FieldName("", "RFT_NO");


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
    public LstPCTRetRftNoWorkInquiryDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        AreaController _aControl = new AreaController(getConnection(), this.getClass());
        Params p = new Params();

        // conver Entity to Param object
        for (PCTRetWorkInfo ent : ents)
        {
            // 号機No.
            p.set(PCART_RFT_NO, ent.getValue(_RFT_NO, ""));
            // ユーザID
            p.set(USER_ID, ent.getUserId());
            // ユーザ名称
            p.set(USER_NAME, ent.getValue(_USER_NAME, ""));
            // エリアNo.
            p.set(AREA_NO, ent.getPlanAreaNo());
            // エリア名称
            p.set(AREA_NAME, _aControl.getAreaName(ent.getPlanAreaNo()));
            // 得意先コード
            p.set(REGULAR_CUSTOMER_CODE, ent.getRegularCustomerCode());
            // 得意先名称
            p.set(REGULAR_CUSTOMER_NAME, ent.getValue(_REGULAR_CUSTOMER_NAME, ""));
            // 出荷先コード
            p.set(CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名称
            p.set(CUSTOMER_NAME, ent.getValue(_CUSTOMER_NAME, ""));
            // ゾーンNo.
            p.set(PLAN_ZONE_NO, ent.getPlanZoneNo());
            // 棚No.
            p.set(PLAN_LOCATION, ent.getPlanLocationNo());
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(_ITEM_NAME, ""));
            // ロット入数
            p.set(LOT_QTY, ent.getBigDecimal(PCTRetPlan.LOT_ENTERING_QTY, new BigDecimal(0)).intValue());
            // 予定数
            p.set(PLAN_QTY, ent.getPlanQty());
            // 実績数
            p.set(RESULT_QTY, ent.getResultQty());
            // オーダーNo.
            p.set(ORDER_NO, ent.getResultOrderNo());
            // JANコード
            p.set(JAN, ent.getValue(_JAN, ""));
            // ケースITF
            p.set(ITF, ent.getValue(_ITF, ""));
            // ボールITF
            p.set(BUNDLE_ITF, ent.getValue(_BUNDLE_ITF, ""));

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
        AreaController _aControl = new AreaController(getConnection(), this.getClass());

        int _point = 1;
        for (PCTRetWorkInfo ent : ents)
        {
            Params p = new Params();
            // 号機No.
            p.set(PCART_RFT_NO, ent.getValue(_RFT_NO, ""));
            // ユーザID
            p.set(USER_ID, ent.getUserId());
            // ユーザ名称
            p.set(USER_NAME, ent.getValue(_USER_NAME, ""));
            // エリアNo.
            p.set(AREA_NO, ent.getPlanAreaNo());
            // エリア名称
            p.set(AREA_NAME, _aControl.getAreaName(ent.getPlanAreaNo()));
            // 得意先コード
            p.set(REGULAR_CUSTOMER_CODE, ent.getRegularCustomerCode());
            // 得意先名称
            p.set(REGULAR_CUSTOMER_NAME, ent.getValue(_REGULAR_CUSTOMER_NAME, ""));
            // 出荷先コード
            p.set(CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名称
            p.set(CUSTOMER_NAME, ent.getValue(_CUSTOMER_NAME, ""));
            // ゾーンNo.
            p.set(PLAN_ZONE_NO, ent.getPlanZoneNo());
            // 棚No.
            p.set(PLAN_LOCATION, ent.getPlanLocationNo());
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(_ITEM_NAME, ""));
            // ロット入数
            p.set(LOT_QTY, ent.getBigDecimal(PCTRetPlan.LOT_ENTERING_QTY, new BigDecimal(0)).intValue());
            // 予定数
            p.set(PLAN_QTY, ent.getPlanQty());
            // 実績数
            p.set(RESULT_QTY, ent.getResultQty());
            // オーダーNo.
            p.set(ORDER_NO, ent.getResultOrderNo());
            // JANコード
            p.set(JAN, ent.getValue(_JAN, ""));
            // ケースITF
            p.set(ITF, ent.getValue(_ITF, ""));
            // ボールITF
            p.set(BUNDLE_ITF, ent.getValue(_BUNDLE_ITF, ""));

            params.add(p);
            _point++;
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
        // 号機No.
        if (!StringUtil.isBlank(param.getString(PCART_RFT_NO)))
        {
            key.setKey(Rft.RFT_NO, param.getString(PCART_RFT_NO));
        }
        // 状態フラグ
        key.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_EXIST_WORKING, "=", "(", "", false);
        key.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_EXIST_WORKED, "=", "", ")", true);

        // set join
        // 結合条件の指定(SETTING_UNIT_KEY)
        key.setJoin(Rft.SETTING_UNIT_KEY, PCTRetWorkInfo.SETTING_UNIT_KEY);
        // 結合条件の指定(PLAN_UKEY)
        key.setJoin(PCTRetPlan.PLAN_UKEY, PCTRetWorkInfo.PLAN_UKEY);
        // 結合条件の指定(AREA_NO)
        key.setJoin(PCTRetWorkInfo.PLAN_AREA_NO, Area.AREA_NO);
        // 結合条件の指定(USER_ID)
        key.setJoin(PCTRetWorkInfo.USER_ID, "", Com_loginuser.USERID, "(+)");

        // set group by
        // 得意先コード
        key.setRegularCustomerCodeGroup();
        // 出荷先コード
        key.setCustomerCodeGroup();
        // ゾーンNo.
        key.setPlanZoneNoGroup();
        // 予定棚
        key.setPlanLocationNoGroup();
        // 商品コード
        key.setItemCodeGroup();
        // ロット入数
        key.setGroup(PCTRetPlan.LOT_ENTERING_QTY);
        // オーダーNo.
        key.setResultOrderNoGroup();

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            // set order by
            // 得意先コード
            key.setRegularCustomerCodeOrder(true);
            // 出荷先コード
            key.setCustomerCodeOrder(true);
            // ゾーンNo.
            key.setPlanZoneNoOrder(true);
            // 予定棚
            key.setPlanLocationNoOrder(true);
            // 商品コード
            key.setItemCodeOrder(true);
            // ロット入数
            key.setOrder(PCTRetPlan.LOT_ENTERING_QTY, true);
            // オーダーNo.
            key.setResultOrderNoOrder(true);

            // set collect
            // RFT号機No.
            key.setCollect(Rft.RFT_NO, "MAX", _RFT_NO);
            // ユーザーID
            key.setUserIdCollect("MAX");
            // ユーザー名称
            key.setCollect(Com_loginuser.USERNAME, "MAX", _USER_NAME);
            // 得意先コード
            key.setRegularCustomerCodeCollect();
            // 得意先名称
            key.setCollect(PCTRetPlan.REGULAR_CUSTOMER_NAME, "MAX", _REGULAR_CUSTOMER_NAME);
            // 出荷先コード
            key.setCustomerCodeCollect();
            // 出荷先名称
            key.setCollect(PCTRetPlan.CUSTOMER_NAME, "MAX", _CUSTOMER_NAME);
            // ゾーンNo.
            key.setPlanZoneNoCollect();
            // 予定棚
            key.setPlanLocationNoCollect();
            // 商品コード
            key.setItemCodeCollect();
            // 商品名称
            key.setCollect(PCTRetPlan.ITEM_NAME, "MAX", _ITEM_NAME);
            // ロット入数
            key.setCollect(PCTRetPlan.LOT_ENTERING_QTY);
            // 予定数
            key.setPlanQtyCollect("SUM");
            // 実績数
            key.setResultQtyCollect("SUM");
            // オーダーNo.
            key.setResultOrderNoCollect();
            // オーダー分割No.
            key.setOrderSeqCollect("MAX");
            // エリアNo
            key.setPlanAreaNoCollect("MAX");
            // JANコード
            key.setCollect(PCTRetPlan.JAN, "MAX", _JAN);
            // ケースITF
            key.setCollect(PCTRetPlan.ITF, "MAX", _ITF);
            // ボールITF
            key.setCollect(PCTRetPlan.BUNDLE_ITF, "MAX", _BUNDLE_ITF);
            // エリアNo.
            key.setCollect(PCTRetWorkInfo.PLAN_AREA_NO, "MAX", _PLAN_AREA_NO);
            // エリア名称
            key.setCollect(Area.AREA_NAME, "MAX", _AREA_NAME);

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
