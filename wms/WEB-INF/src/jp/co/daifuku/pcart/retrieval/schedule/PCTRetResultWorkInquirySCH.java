// $Id: PCTRetResultWorkInquirySCH.java 3623 2009-03-17 10:22:34Z rnakai $
package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.retrieval.schedule.PCTRetResultWorkInquirySCHParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.dbhandler.PCTRetResultHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetResultSearchKey;
import jp.co.daifuku.wms.base.entity.PCTRetResult;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * PCT作業実績照会のスケジュール処理を行います。
 *
 * @version $Revision: 3623 $, $Date:: 2009-03-17 19:22:34 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: rnakai $
 */
public class PCTRetResultWorkInquirySCH
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
    public PCTRetResultWorkInquirySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 初期表示を行います。<BR>
     * <BR>
     * 概要：PCT出庫実績情報に該当荷主が1件しかない場合は初期表示を行います。<BR>
     * 
     * @param p 検索条件
     * @return 表示パラメータ データがない場合、nullを返す。
     * @throws CommonException
     *             全ての例外を報告します。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        // PCT出庫実績情報
        PCTRetResultHandler resultHandler = new PCTRetResultHandler(getConnection());
        PCTRetResultSearchKey resultSKey = new PCTRetResultSearchKey();

        resultSKey.setConsignorCodeCollect();
        resultSKey.setCollect(PCTRetResult.CONSIGNOR_NAME, "MAX", _CONSIGNOR_NAME);
        resultSKey.setConsignorCodeGroup();
        resultSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");

        if (resultHandler.count(resultSKey) == 1)
        {
            PCTRetResult retResult = (PCTRetResult)resultHandler.findPrimary(resultSKey);

            Params lineparam = new Params();
            lineparam.set(CONSIGNOR_CODE, retResult.getConsignorCode());
            lineparam.set(CONSIGNOR_NAME, (String)retResult.getValue(PCTRetResult.CONSIGNOR_NAME, ""));

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
