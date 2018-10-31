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
 * @version $Revision: 5557 $, $Date: 2009-11-09 19:41:54 +0900 (月, 09 11 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class AsStartInventoryCheckSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA */
    public static final ParamKey AREA = new ParamKey("AREA");

    /** COMMON_USE */
    public static final ParamKey COMMON_USE = new ParamKey("COMMON_USE");

    /** END_ITEM_CODE */
    public static final ParamKey END_ITEM_CODE = new ParamKey("END_ITEM_CODE");

    /** END_LOCATION */
    public static final ParamKey END_LOCATION = new ParamKey("END_LOCATION");

    /** FUNCTION_ID */
    public static final ParamKey FUNCTION_ID = new ParamKey("FUNCTION_ID");

    /** START_ITEM_CODE */
    public static final ParamKey START_ITEM_CODE = new ParamKey("START_ITEM_CODE");

    /** START_LOCATION */
    public static final ParamKey START_LOCATION = new ParamKey("START_LOCATION");

    /** STATION */
    public static final ParamKey STATION = new ParamKey("STATION");
    
    // DFKLOOK start
    /** SETTING_UKEYS */
    public static final ParamKey SETTING_UKEYS = new ParamKey("SETTING_UKEYS");
       
    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** PRINT_FLAG */
    public static final ParamKey PRINT_FLAG = new ParamKey("PRINT_FLAG");

    // DFKLOOK end

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
    public AsStartInventoryCheckSCHParams(DfkUserInfo dui)
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
