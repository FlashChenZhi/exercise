// $Id: PCTUndoneWorkSCH.java 6900 2010-01-25 11:21:11Z kumano $
package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.pcart.retrieval.schedule.PCTUndoneWorkSCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTOrderInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTOrderInfoSearchKey;
import jp.co.daifuku.wms.base.entity.PCTOrderInfo;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 残作業一覧のスケジュール処理を行います。
 *
 * @version $Revision: 6900 $, $Date:: 2010-01-25 20:21:11 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class PCTUndoneWorkSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 保存用のフィールド 荷主名称(<code>CONSIGNOR_NAME</code>) */
    private FieldName _CONSIGNOR_NAME = new FieldName("", "CONSIGNOR_NAME");

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
    public PCTUndoneWorkSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 初期表示を行います。<BR>
     * <BR>
     * 概要：PCT出庫状況情報に該当荷主が1件しかない場合は初期表示を行います。<BR>
     * 
     * @param p
     *            検索条件
     * @return 表示パラメータ データがない場合、nullを返す。
     * @throws CommonException
     *             全ての例外を報告します。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        int count = count(p);
        if (count == 1)
        {
            Params[] outParam = queryConsignor(p);
            if (!ArrayUtil.isEmpty(outParam))
            {
                return outParam[0];
            }
        }
        return null;
    }

    /**
     * PCT出庫作業件数の取得を行います。<BR>
     * 
     * @param p
     *            検索条件パラメータ
     * @return 対象データの件数
     * @throws CommonException
     *             処理中に何らかの例外が発生した場合にthrowします。
     */
    public int count(ScheduleParams p)
            throws CommonException
    {
        // PCT出庫作業情報ハンドラ
        PCTOrderInfoHandler handler = new PCTOrderInfoHandler(getConnection());
        PCTOrderInfoSearchKey shKy = new PCTOrderInfoSearchKey();

        // 荷主コード
        if (!StringUtil.isBlank(p.getString(CONSIGNOR_CODE)))
        {
            shKy.setConsignorCode(p.getString(CONSIGNOR_CODE));
        }
        // 予定エリアNo.
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            // 全エリア以外が選ばれている場合
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                shKy.setAreaNo(p.getString(AREA_NO));
            }
        }
        // 集約条件
        shKy.setConsignorCodeGroup();

        return handler.count(shKy);
    }

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
        // リスト用作業データを取得する
        return setListData(p);
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
     * リスト表示用データを取得します。<BR>
     * 
     * @param inParam
     *            検索パラメータ
     * @return 表示データ
     * @throws CommonException
     *             全ての例外をスローします。
     */
    protected List<Params> setListData(ScheduleParams p)
            throws CommonException
    {


        PCTOrderInfoHandler hand = new PCTOrderInfoHandler(getConnection());
        PCTOrderInfoSearchKey sKey = new PCTOrderInfoSearchKey();

        // 荷主コード
        sKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // エリアNo
        if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)) && !StringUtil.isBlank(p.getString(AREA_NO)))
        {
            sKey.setAreaNo(p.getString(AREA_NO));
        }
        sKey.setAreaNoGroup();
        sKey.setMinWorkZoneNoGroup();
        sKey.setConsignorCodeGroup();

        int count = hand.count(sKey);

        canLowerDisplay(count);

        StringBuffer sql = null;

        // ダイレクトDBハンドラ
        DefaultDDBHandler ddbHandler = null;

        sql = new StringBuffer();

        sql.append("SELECT");
        sql.append("        AREA_NO");
        sql.append("        ,MIN_WORK_ZONE_NO");
        sql.append("        ,SUM( CNT1 ) ALL_");
        sql.append("        ,SUM( CNT2 ) COMPLETE");
        sql.append("        ,SUM( CNT3 ) NOTSTART");
        sql.append("        ,CONSIGNOR_CODE");
        sql.append("    FROM");
        sql.append("        (");
        sql.append("            SELECT");
        sql.append("                    AREA_NO");
        sql.append("                    ,MIN_WORK_ZONE_NO");
        sql.append("                    ,COUNT( DISTINCT(RESULT_ORDER_NO) ) CNT1");
        sql.append("                    ,0 CNT2");
        sql.append("                    ,0 CNT3");
        sql.append("                    ,CONSIGNOR_CODE");
        sql.append("                FROM");
        sql.append("                    DNPCTORDERINFO");
        sql.append("                GROUP BY");
        sql.append("                    AREA_NO");
        sql.append("                    ,MIN_WORK_ZONE_NO");
        sql.append("                    ,CONSIGNOR_CODE");
        sql.append("            UNION");
        sql.append("            SELECT");
        sql.append("                    AREA_NO");
        sql.append("                    ,MIN_WORK_ZONE_NO");
        sql.append("                    ,0 CNT1");
        sql.append("                    ,COUNT( DISTINCT(RESULT_ORDER_NO) ) CNT2");
        sql.append("                    ,0 CNT3");
        sql.append("                    ,CONSIGNOR_CODE");
        sql.append("                FROM");
        sql.append("                    DNPCTORDERINFO");
        sql.append("                WHERE");
        sql.append("                    STATUS_FLAG = '").append(PCTRetrievalInParameter.STATUS_FLAG_COMPLETION).append(
                "' ");
        sql.append("                GROUP BY");
        sql.append("                    AREA_NO");
        sql.append("                    ,MIN_WORK_ZONE_NO");
        sql.append("                    ,CONSIGNOR_CODE");
        sql.append("            UNION");
        sql.append("            SELECT");
        sql.append("                    AREA_NO");
        sql.append("                    ,MIN_WORK_ZONE_NO");
        sql.append("                    ,0 CNT1");
        sql.append("                    ,0 CNT2");
        sql.append("                    ,COUNT( DISTINCT(RESULT_ORDER_NO) ) CNT3");
        sql.append("                    ,CONSIGNOR_CODE");
        sql.append("                FROM");
        sql.append("                    DNPCTORDERINFO");
        sql.append("                WHERE");
        sql.append("                    STATUS_FLAG = '").append(PCTRetrievalInParameter.STATUS_FLAG_UNWORK).append(
                "' ");
        sql.append("                GROUP BY");
        sql.append("                    AREA_NO");
        sql.append("                    ,MIN_WORK_ZONE_NO");
        sql.append("                    ,CONSIGNOR_CODE");
        sql.append("        )");
        sql.append("    WHERE ");
        sql.append("CONSIGNOR_CODE = ").append(DBFormat.format(p.getString(CONSIGNOR_CODE)));
        if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)) && !StringUtil.isBlank(p.getString(AREA_NO)))
        {
            sql.append("AND AREA_NO = ");
            sql.append(DBFormat.format(p.getString(AREA_NO)));
        }
        sql.append("    GROUP BY ");
        sql.append("        AREA_NO, MIN_WORK_ZONE_NO ,CONSIGNOR_CODE ");
        sql.append("    ORDER BY ");
        sql.append("        AREA_NO, MIN_WORK_ZONE_NO ,CONSIGNOR_CODE ");
        
        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
            
            ddbHandler.execute(String.valueOf(sql));

            Entity[] countEntity = ddbHandler.getEntities(100, new PCTOrderInfo());


            List<Params> outParams = new ArrayList<Params>();

            for (Entity entity : countEntity)
            {
                Params param = new Params();

                PCTOrderInfo orderInfo = (PCTOrderInfo)entity;
                // エリア
                param.set(AREA_NO, orderInfo.getAreaNo());
                // 最小ゾーン
                param.set(START_WORK_ZONE, orderInfo.getMinWorkZoneNo());
                // 予定ｵｰﾀﾞｰ数
                param.set(PLAN_ORDER_COUNT, entity.getBigDecimal(new FieldName("", "ALL_")).intValue());
                // 完了ｵｰﾀﾞｰ数
                param.set(COMPLETE_ORDER_COUNT, entity.getBigDecimal(new FieldName("", "COMPLETE")).intValue());
                // 残ｵｰﾀﾞｰ数
                param.set(SURPLUS_ORDER_COUNT, entity.getBigDecimal(new FieldName("", "NOTSTART")).intValue());


                double productionRate = 0;
                BigDecimal bdResultQty = param.getBigDecimal(COMPLETE_ORDER_COUNT);
                BigDecimal bdPlanQty = param.getBigDecimal(PLAN_ORDER_COUNT);
                BigDecimal bdQtyPer = new BigDecimal(0);
                if (param.getInt(COMPLETE_ORDER_COUNT) > 0 && param.getInt(PLAN_ORDER_COUNT) > 0)
                {
                    bdQtyPer = bdResultQty.divide(bdPlanQty, 3, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                    productionRate = bdQtyPer.divide(new BigDecimal(1), 3, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                param.set(PROGRESS_RATE, productionRate);

                outParams.add(param);
            }
            return outParams;
        }
        finally
        {
            if (ddbHandler != null)
            {
                ddbHandler.close();
            }
        }
    }

    /**
     * PCT出庫作業情報より荷主コードと名称を取得します。<BR>
     * 
     * @param p
     *            検索条件パラメータ
     * @return 表示パラメータ データがない場合、要素数0の配列を返す
     * @throws CommonException
     *             処理中に何らかの例外が発生した場合にthrowします。
     */
    protected Params[] queryConsignor(ScheduleParams p)
            throws CommonException
    {

        // PCT出庫作業情報ハンドラ
        PCTOrderInfoHandler handler = new PCTOrderInfoHandler(getConnection());
        PCTOrderInfoSearchKey shKy = new PCTOrderInfoSearchKey();
        // 荷主コード
        if (!StringUtil.isBlank(p.getString(CONSIGNOR_CODE)))
        {
            shKy.setConsignorCode(p.getString(CONSIGNOR_CODE));
        }
        // 予定エリアNo.
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            // 全エリア以外が選ばれている場合
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                shKy.setAreaNo(p.getString(AREA_NO));
            }
        }

        // 集約条件
        shKy.setConsignorCodeGroup();

        // 取得項目
        shKy.setConsignorCodeCollect();
        shKy.setCollect(PCTOrderInfo.CONSIGNOR_NAME, "MAX", _CONSIGNOR_NAME);

        // 検索結果を取得
        PCTOrderInfo[] entity = (PCTOrderInfo[])handler.find(shKy);
        if (ArrayUtil.isEmpty(entity))
        {
            return null;
        }
        // set input parameters.
        List<Params> inparamList = new ArrayList<Params>();

        for (int i = 0; i < entity.length; i++)
        {
            Params lineparam = new Params();
            lineparam.set(CONSIGNOR_CODE, entity[i].getConsignorCode());
            lineparam.set(CONSIGNOR_NAME, entity[i].getConsignorName());
            inparamList.add(lineparam);
        }

        Params[] inparams = new Params[inparamList.size()];
        inparamList.toArray(inparams);

        return inparams;
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
