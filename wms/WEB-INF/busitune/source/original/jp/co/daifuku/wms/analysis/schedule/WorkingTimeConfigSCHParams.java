package jp.co.daifuku.wms.analysis.schedule;

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
 * @version
 * @author BusiTune 1.0 Generator.
 */
public class WorkingTimeConfigSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** KIND_WORK */
    public static final ParamKey KIND_WORK = new ParamKey("KIND_WORK");

    /** RETRIEVAL_AVE_PIECE */
    public static final ParamKey RETRIEVAL_AVE_PIECE = new ParamKey("RETRIEVAL_AVE_PIECE");

    /** RETRIEVAL_AVE_TIME_ITEM */
    public static final ParamKey RETRIEVAL_AVE_TIME_ITEM = new ParamKey("RETRIEVAL_AVE_TIME_ITEM");

    /** RETRIEVAL_AVE_TIME_PIECE */
    public static final ParamKey RETRIEVAL_AVE_TIME_PIECE = new ParamKey("RETRIEVAL_AVE_TIME_PIECE");

    /** RETRIEVAL_SEC_PER_ITEM */
    public static final ParamKey RETRIEVAL_SEC_PER_ITEM = new ParamKey("RETRIEVAL_SEC_PER_ITEM");

    /** RETRIEVAL_SEC_PER_PIECE */
    public static final ParamKey RETRIEVAL_SEC_PER_PIECE = new ParamKey("RETRIEVAL_SEC_PER_PIECE");

    /** STORAGE_AVE_PIECE */
    public static final ParamKey STORAGE_AVE_PIECE = new ParamKey("STORAGE_AVE_PIECE");

    /** STORAGE_AVE_TIME_ITEM */
    public static final ParamKey STORAGE_AVE_TIME_ITEM = new ParamKey("STORAGE_AVE_TIME_ITEM");

    /** STORAGE_AVE_TIME_PIECE */
    public static final ParamKey STORAGE_AVE_TIME_PIECE = new ParamKey("STORAGE_AVE_TIME_PIECE");

    /** STORAGE_SEC_PER_ITEM */
    public static final ParamKey STORAGE_SEC_PER_ITEM = new ParamKey("STORAGE_SEC_PER_ITEM");

    /** STORAGE_SEC_PER_PIECE */
    public static final ParamKey STORAGE_SEC_PER_PIECE = new ParamKey("STORAGE_SEC_PER_PIECE");

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
    public WorkingTimeConfigSCHParams()
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
