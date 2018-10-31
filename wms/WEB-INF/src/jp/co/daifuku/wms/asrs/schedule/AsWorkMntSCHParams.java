package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.foundation.common.ParamKey;
import jp.co.daifuku.foundation.common.ScheduleParams;

/**
 * BusinessクラスとSCH間で使用されるパラメータのキーを定義するクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7837 $, $Date: 2010-04-21 11:13:53 +0900 (水, 21 4 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class AsWorkMntSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** ASRS_PROCESS */
    public static final ParamKey ASRS_PROCESS = new ParamKey("ASRS_PROCESS");

    /** CARRYING_FLAG_NAME */
    public static final ParamKey CARRYING_FLAG_NAME = new ParamKey("CARRYING_FLAG_NAME");

    /** CARRYING_KEY */
    public static final ParamKey CARRYING_KEY = new ParamKey("CARRYING_KEY");

    /** CARRYING_STATUS_NAME */
    public static final ParamKey CARRYING_STATUS_NAME = new ParamKey("CARRYING_STATUS_NAME");

    /** FROM_CARRYING_NAME */
    public static final ParamKey FROM_CARRYING_NAME = new ParamKey("FROM_CARRYING_NAME");

    /** LOCATION */
    public static final ParamKey LOCATION = new ParamKey("LOCATION");

    /** RETRIEVAL_DETAIL_NAME */
    public static final ParamKey RETRIEVAL_DETAIL_NAME = new ParamKey("RETRIEVAL_DETAIL_NAME");

    /** SCHEDULE_NO */
    public static final ParamKey SCHEDULE_NO = new ParamKey("SCHEDULE_NO");

    /** TO_CARRYING_NAME */
    public static final ParamKey TO_CARRYING_NAME = new ParamKey("TO_CARRYING_NAME");

    /** WORK_KIND_NAME */
    public static final ParamKey WORK_KIND_NAME = new ParamKey("WORK_KIND_NAME");

    /** WORK_NO */
    public static final ParamKey WORK_NO = new ParamKey("WORK_NO");
    
    // DFKLOOK start
    /** CARRYING_FLAG */
    public static final ParamKey CARRYING_FLAG = new ParamKey("CARRYING_FLAG");

    /** CARRYING_STATUS */
    public static final ParamKey CARRYING_STATUS = new ParamKey("CARRYING_STATUS");

    /** FROM_CARRYING */
    public static final ParamKey FROM_CARRYING = new ParamKey("FROM_CARRYING");

    /** RETRIEVAL_DETAIL */
    public static final ParamKey RETRIEVAL_DETAIL = new ParamKey("RETRIEVAL_DETAIL");

    /** TO_CARRYING */
    public static final ParamKey TO_CARRYING = new ParamKey("TO_CARRYING");

    /** WORK_KIND */
    public static final ParamKey WORK_KIND = new ParamKey("WORK_KIND");
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
     * Default constructor
     */
    public AsWorkMntSCHParams()
    {
        super();
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
     * @return
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
