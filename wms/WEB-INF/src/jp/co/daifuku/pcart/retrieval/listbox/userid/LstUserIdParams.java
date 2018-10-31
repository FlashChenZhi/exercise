// $Id: LstUserIdParams.java 3469 2009-03-13 08:57:26Z dmori $
package jp.co.daifuku.pcart.retrieval.listbox.userid;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.util.Map;
import jp.co.daifuku.bluedog.util.StringParameters;
import jp.co.daifuku.foundation.common.Key;
import jp.co.daifuku.foundation.common.ParamKey;
import jp.co.daifuku.foundation.common.Params;

/**
 * 親画面とポップアップ画面間で使用されるパラメータのキーを定義するクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 3469 $, $Date:: 2009-03-13 17:57:26 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: dmori $
 */
public class LstUserIdParams
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** BATCH_SEQ_NO */
    public static final ParamKey BATCH_SEQ_NO = new ParamKey("BATCH_SEQ_NO");

    /** CONSIGNOR_CODE */
    public static final ParamKey CONSIGNOR_CODE = new ParamKey("CONSIGNOR_CODE");

    /** CUSTOMER_CODE */
    public static final ParamKey CUSTOMER_CODE = new ParamKey("CUSTOMER_CODE");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** JOB_STATUS */
    public static final ParamKey JOB_STATUS = new ParamKey("JOB_STATUS");

    /** ORDER_NO */
    public static final ParamKey ORDER_NO = new ParamKey("ORDER_NO");

    /** PLAN_DAY */
    public static final ParamKey PLAN_DAY = new ParamKey("PLAN_DAY");

    /** REGULAR_CUSTOMER_CODE */
    public static final ParamKey REGULAR_CUSTOMER_CODE = new ParamKey("REGULAR_CUSTOMER_CODE");

    /** ROLE_ID */
    public static final ParamKey ROLE_ID = new ParamKey("ROLE_ID");

    /** ROLE_NAME */
    public static final ParamKey ROLE_NAME = new ParamKey("ROLE_NAME");

    /** SEARCHTABLE */
    public static final ParamKey SEARCHTABLE = new ParamKey("SEARCHTABLE");

    /** USER_ID */
    public static final ParamKey USER_ID = new ParamKey("USER_ID");

    /** USER_NAME */
    public static final ParamKey USER_NAME = new ParamKey("USER_NAME");

    /** WORK_DAY */
    public static final ParamKey WORK_DAY = new ParamKey("WORK_DAY");


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
     * デフォルトコンストラクターです。
     */
    public LstUserIdParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public LstUserIdParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public LstUserIdParams(Map<Key, Object> initMap)
    {
        super(initMap);
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
