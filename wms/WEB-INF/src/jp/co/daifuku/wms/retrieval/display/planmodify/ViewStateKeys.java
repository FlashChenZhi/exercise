// $Id: ViewStateKeys.java,v 1.1.1.1 2009/02/10 08:55:45 arai Exp $
package jp.co.daifuku.wms.retrieval.display.planmodify;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * ViewStateのキーを定義するクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 1.1.1.1 $, $Date: 2009/02/10 08:55:45 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
interface ViewStateKeys
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    //
    public static final String CONSIGNOR_CODE = "CONSIGNOR_CODE";
    //
    public static final String LAST_UPDATE_DATE = "LAST_UPDATE_DATE";
    //
    public static final String LINE_NO = "LINE_NO";
    //
    public static final String PLAN_DAY = "PLAN_DAY";
    //
    public static final String PLAN_U_KEY = "PLAN_U_KEY";
    //
    public static final String SLIP_NUMBER = "SLIP_NUMBER";

    // DFKLOOK ここから修正
    public static final String MASTER = "MASTER";
    
    public static final String STOCKPACK = "STOCKPACK";
    // DFKLOOK ここまで修正
    
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

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

}
//end of class
