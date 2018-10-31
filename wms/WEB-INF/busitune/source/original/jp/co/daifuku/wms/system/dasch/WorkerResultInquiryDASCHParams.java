// $Id: WorkerResultInquiryDASCHParams.java 7442 2010-03-08 04:17:40Z okayama $
package jp.co.daifuku.wms.system.dasch;

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
import jp.co.daifuku.wms.base.common.WmsDASCHParams;

/**
 * BusinessクラスとDASCH間で使用されるパラメータのキーを定義するクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7442 $, $Date:: 2010-03-08 13:17:40 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class WorkerResultInquiryDASCHParams
        extends WmsDASCHParams
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** DFK_DS_NO */
    public static final ParamKey DFK_DS_NO = new ParamKey("DFK_DS_NO");

    /** DFK_USER_ID */
    public static final ParamKey DFK_USER_ID = new ParamKey("DFK_USER_ID");

    /** DFK_USER_NAME */
    public static final ParamKey DFK_USER_NAME = new ParamKey("DFK_USER_NAME");

    /** END_TIME */
    public static final ParamKey END_TIME = new ParamKey("END_TIME");

    /** F_WORK_CONTENTS */
    public static final ParamKey F_WORK_CONTENTS = new ParamKey("F_WORK_CONTENTS");

    /** FROM_WORK_DAY */
    public static final ParamKey FROM_WORK_DAY = new ParamKey("FROM_WORK_DAY");

    /** GROUP_CONDITION */
    public static final ParamKey GROUP_CONDITION = new ParamKey("GROUP_CONDITION");

    /** REAL_WORK_TIME */
    public static final ParamKey REAL_WORK_TIME = new ParamKey("REAL_WORK_TIME");

    /** RFT */
    public static final ParamKey RFT = new ParamKey("RFT");

    /** SORT_CONDITION */
    public static final ParamKey SORT_CONDITION = new ParamKey("SORT_CONDITION");

    /** START_TIME */
    public static final ParamKey START_TIME = new ParamKey("START_TIME");

    /** SYS_DAY */
    public static final ParamKey SYS_DAY = new ParamKey("SYS_DAY");

    /** SYS_TIME */
    public static final ParamKey SYS_TIME = new ParamKey("SYS_TIME");

    /** TERMINAL_NO */
    public static final ParamKey TERMINAL_NO = new ParamKey("TERMINAL_NO");

    /** TO_WORK_DAY */
    public static final ParamKey TO_WORK_DAY = new ParamKey("TO_WORK_DAY");

    /** USER_NAME */
    public static final ParamKey USER_NAME = new ParamKey("USER_NAME");

    /** WORK_CNT */
    public static final ParamKey WORK_CNT = new ParamKey("WORK_CNT");

    /** WORK_CNT_HOUR */
    public static final ParamKey WORK_CNT_HOUR = new ParamKey("WORK_CNT_HOUR");

    /** WORK_CONTENT */
    public static final ParamKey WORK_CONTENT = new ParamKey("WORK_CONTENT");

    /** WORK_DAY */
    public static final ParamKey WORK_DAY = new ParamKey("WORK_DAY");

    /** WORK_DAY_TO */
    public static final ParamKey WORK_DAY_TO = new ParamKey("WORK_DAY_TO");

    /** WORK_DETAIL */
    public static final ParamKey WORK_DETAIL = new ParamKey("WORK_DETAIL");

    /** WORK_DURATION */
    public static final ParamKey WORK_DURATION = new ParamKey("WORK_DURATION");

    /** WORK_END_TIME */
    public static final ParamKey WORK_END_TIME = new ParamKey("WORK_END_TIME");

    /** WORK_QTY */
    public static final ParamKey WORK_QTY = new ParamKey("WORK_QTY");

    /** WORK_QTY_HOUR */
    public static final ParamKey WORK_QTY_HOUR = new ParamKey("WORK_QTY_HOUR");

    /** WORK_START_TIME */
    public static final ParamKey WORK_START_TIME = new ParamKey("WORK_START_TIME");

    /** WORKED_COUNTS */
    public static final ParamKey WORKED_COUNTS = new ParamKey("WORKED_COUNTS");

    /** WORKED_COUNTSHR */
    public static final ParamKey WORKED_COUNTSHR = new ParamKey("WORKED_COUNTSHR");

    /** WORKED_QTY_IN_PIECE */
    public static final ParamKey WORKED_QTY_IN_PIECE = new ParamKey("WORKED_QTY_IN_PIECE");

    /** WORKED_QTY_IN_PIECEHR */
    public static final ParamKey WORKED_QTY_IN_PIECEHR = new ParamKey("WORKED_QTY_IN_PIECEHR");

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
    public WorkerResultInquiryDASCHParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public WorkerResultInquiryDASCHParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public WorkerResultInquiryDASCHParams(Map<Key, Object> initMap)
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
