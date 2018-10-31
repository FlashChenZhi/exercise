// $Id: AsOperationCycleSCHParams.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.authentication.DfkUserInfo;

import jp.co.daifuku.foundation.common.ParamKey;
import jp.co.daifuku.foundation.common.ScheduleParams;

/**
 * BusinessクラスとSCH間で使用されるパラメータのキーを定義するクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class AsOperationCycleSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AISLE_NO */
    public static final ParamKey AISLE_NO = new ParamKey("AISLE_NO");

    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** FROM_SEARCH_DATE */
    public static final ParamKey FROM_SEARCH_DATE = new ParamKey("FROM_SEARCH_DATE");

    /** FROM_SEARCH_TIME */
    public static final ParamKey FROM_SEARCH_TIME = new ParamKey("FROM_SEARCH_TIME");

    /** IN_SEARCH_DATE */
    public static final ParamKey IN_SEARCH_DATE = new ParamKey("IN_SEARCH_DATE");

    /** IN_TO_SEARCH_DATE */
    public static final ParamKey IN_TO_SEARCH_DATE = new ParamKey("IN_TO_SEARCH_DATE");

    /** RETRIEVAL_COUNT */
    public static final ParamKey RETRIEVAL_COUNT = new ParamKey("RETRIEVAL_COUNT");

    /** STORAGE_COUNT */
    public static final ParamKey STORAGE_COUNT = new ParamKey("STORAGE_COUNT");

    /** TO_SEARCH_DATE */
    public static final ParamKey TO_SEARCH_DATE = new ParamKey("TO_SEARCH_DATE");

    /** TO_SEARCH_TIME */
    public static final ParamKey TO_SEARCH_TIME = new ParamKey("TO_SEARCH_TIME");

    /** TOTAL_COUNT */
    public static final ParamKey TOTAL_COUNT = new ParamKey("TOTAL_COUNT");

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
     * ユーザ情報を元にパラメータクラスを作成します。
     * @param dui ユーザ情報
     */
    public AsOperationCycleSCHParams(DfkUserInfo dui)
    {
        super(dui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

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
