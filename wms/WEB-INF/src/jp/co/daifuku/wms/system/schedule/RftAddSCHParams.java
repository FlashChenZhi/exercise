package jp.co.daifuku.wms.system.schedule;

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
 * @version
 * @author BusiTune 1.0 Generator.
 */
public class RftAddSCHParams
        extends ScheduleParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** BTN_DISTINCTION */
    public static final ParamKey BTN_DISTINCTION = new ParamKey("BTN_DISTINCTION");

    /** IP_ADDRESS */
    public static final ParamKey IP_ADDRESS = new ParamKey("IP_ADDRESS");

    /** LANGUAGE */
    public static final ParamKey LANGUAGE = new ParamKey("LANGUAGE");

    /** MACHINE_NUMBER */
    public static final ParamKey MACHINE_NUMBER = new ParamKey("MACHINE_NUMBER");

    /** MODEL */
    public static final ParamKey MODEL = new ParamKey("MODEL");
    
    /** RFT_ASSORT */
    public static final ParamKey RFT_ASSORT = new ParamKey("RFT_ASSORT");
    
    /** TERMINALTYPE */
    public static final ParamKey TERMINALTYPE = new ParamKey("TERMINALTYPE");
    
    /** DC入荷メニュー */
    public static final ParamKey WORK_KIND_DC_RECEIVING = new ParamKey("WORK_KIND_DC_RECEIVING");

    /** 棚卸メニュー */
    public static final ParamKey WORK_KIND_INVENTRY = new ParamKey("WORK_KIND_INVENTRY");

    /** 予定外出庫メニュー */
    public static final ParamKey WORK_KIND_NO_PLAN_RETRIEVAL = new ParamKey("WORK_KIND_NO_PLAN_RETRIEVAL");

    /** 予定外入庫メニュー */
    public static final ParamKey WORK_KIND_NO_PLAN_STORAGE = new ParamKey("WORK_KIND_NO_PLAN_STORAGE");

    /** オーダー出庫メニュー */
    public static final ParamKey WORK_KIND_ORDER_RETRIEVAL = new ParamKey("WORK_KIND_ORDER_RETRIEVAL");

    /** 移動出庫メニュー */
    public static final ParamKey WORK_KIND_RELOCATINO_RETRIEV = new ParamKey("WORK_KIND_RELOCATINO_RETRIEV");

    /** 移動入庫メニュー */
    public static final ParamKey WORK_KIND_RELOCATINO_STORAGE = new ParamKey("WORK_KIND_RELOCATINO_STORAGE");

    /** 出荷積込メニュー */
    public static final ParamKey WORK_KIND_SHIPPING_LOADING = new ParamKey("WORK_KIND_SHIPPING_LOADING");

    /** 出荷検品(出荷先別)メニュー */
    public static final ParamKey WORK_KIND_SHIPPING_PICK = new ParamKey("WORK_KIND_SHIPPING_PICK");

    /** 仕分メニュー */
    public static final ParamKey WORK_KIND_SORT = new ParamKey("WORK_KIND_SORT");

    /** 入庫メニュー */
    public static final ParamKey WORK_KIND_STORAGE = new ParamKey("WORK_KIND_STORAGE");

    /** 入庫(入荷エリア)メニュー */
    public static final ParamKey WORK_KIND_STORAGE_RECEIVING = new ParamKey("WORK_KIND_STORAGE_RECEIVING");

    /** TC入荷メニュー */
    public static final ParamKey WORK_KIND_TC_RECEIVING = new ParamKey("WORK_KIND_TC_RECEIVING");

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
    public RftAddSCHParams()
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
