// $Id: PCTRetWorkingInquirySCH.java 4270 2009-05-13 03:57:38Z okayama $
package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.retrieval.schedule.PCTRetWorkingInquirySCHParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 作業状況照会のスケジュール処理を行います。
 *
 * @version $Revision: 4270 $, $Date:: 2009-05-13 12:57:38 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class PCTRetWorkingInquirySCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // private String _instanceVar ;
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
    public PCTRetWorkingInquirySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 初期表示を行います。<BR>
     * <BR>
     * 概要：PCT出庫作業情報に該当荷主が1件しかない場合は初期表示を行います。<BR>
     * 
     * @param p 検索条件
     * @return 表示パラメータ データがない場合、nullを返す。
     * @throws CommonException
     *             全ての例外を報告します。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        // PCT出庫作業情報
        PCTRetWorkInfoHandler workInfoHandler = new PCTRetWorkInfoHandler(getConnection());
        PCTRetWorkInfoSearchKey workSKey = new PCTRetWorkInfoSearchKey();

        // 検索条件
        // 荷主コード
        workSKey.setConsignorCodeCollect();
        // 荷主名称
        workSKey.setCollect(PCTRetPlan.CONSIGNOR_NAME, "MAX", _CONSIGNOR_NAME);
        workSKey.setConsignorCodeGroup();
        workSKey.setStatusFlag(PCTRetWorkInfo.STATUS_FLAG_DELETE, "!=");

        // 結合
        workSKey.setJoin(PCTRetWorkInfo.PLAN_UKEY, "", PCTRetPlan.PLAN_UKEY, "");

        if (workInfoHandler.count(workSKey) == 1)
        {
            PCTRetWorkInfo workInfo = (PCTRetWorkInfo)workInfoHandler.findPrimary(workSKey);

            Params lineparam = new Params();
            lineparam.set(CONSIGNOR_CODE, workInfo.getConsignorCode());
            lineparam.set(CONSIGNOR_NAME, (String)workInfo.getValue(PCTRetPlan.CONSIGNOR_NAME, ""));

            return lineparam;
        }
        return null;

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
