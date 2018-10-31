// $Id: ExporterParams_ja.java 113 2008-10-06 10:52:27Z admin $
package jp.co.daifuku.wms.retrieval.exporter;

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
 * BusinessクラスとExporter間で使用されるパラメータのキーを定義するクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 113 $, $Date:: 2008-10-06 19:52:27 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class RetrievalWorkListParams
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** AREA_NO */
    public static final ParamKey AREA_NO = new ParamKey("AREA_NO");

    /** BATCH_NO */
    public static final ParamKey BATCH_NO = new ParamKey("BATCH_NO");

    /** DFK_DS_NO */
    public static final ParamKey DFK_DS_NO = new ParamKey("DFK_DS_NO");

    /** DFK_USER_ID */
    public static final ParamKey DFK_USER_ID = new ParamKey("DFK_USER_ID");

    /** DFK_USER_NAME */
    public static final ParamKey DFK_USER_NAME = new ParamKey("DFK_USER_NAME");

    /** FROM_SEARCH_DAY */
    public static final ParamKey FROM_SEARCH_DAY = new ParamKey("FROM_SEARCH_DAY");

    /** FROM_SEARCH_TIME */
    public static final ParamKey FROM_SEARCH_TIME = new ParamKey("FROM_SEARCH_TIME");

    /** ITEM_CODE */
    public static final ParamKey ITEM_CODE = new ParamKey("ITEM_CODE");

    /** ITEM_NAME */
    public static final ParamKey ITEM_NAME = new ParamKey("ITEM_NAME");

    /** LINE_NO */
    public static final ParamKey LINE_NO = new ParamKey("LINE_NO");

    /** LIST_NO */
    public static final ParamKey LIST_NO = new ParamKey("LIST_NO");

    /** LOCATION_NO */
    public static final ParamKey LOCATION_NO = new ParamKey("LOCATION_NO");

    /** LOT_NO */
    public static final ParamKey LOT_NO = new ParamKey("LOT_NO");

    /** PRIORITY_FLAG */
    public static final ParamKey PRIORITY_FLAG = new ParamKey("PRIORITY_FLAG");

    /** RETRIEVAL_COMMAND_DETAIL */
    public static final ParamKey RETRIEVAL_COMMAND_DETAIL = new ParamKey("RETRIEVAL_COMMAND_DETAIL");

    /** STOCK_QTY */
    public static final ParamKey STOCK_QTY = new ParamKey("STOCK_QTY");

    /** SYS_DAY */
    public static final ParamKey SYS_DAY = new ParamKey("SYS_DAY");

    /** SYS_TIME */
    public static final ParamKey SYS_TIME = new ParamKey("SYS_TIME");

    /** TICKET_NO */
    public static final ParamKey TICKET_NO = new ParamKey("TICKET_NO");

    /** TO_SEARCH_DAY */
    public static final ParamKey TO_SEARCH_DAY = new ParamKey("TO_SEARCH_DAY");

    /** TO_SEARCH_TIME */
    public static final ParamKey TO_SEARCH_TIME = new ParamKey("TO_SEARCH_TIME");

    /** WORK_QTY */
    public static final ParamKey WORK_QTY = new ParamKey("WORK_QTY");

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
    public RetrievalWorkListParams()
    {
        super();
    }

    /**
     * StringParameterの情報を元にパラメータクラスを作成します。
     * @param param StringParameters
     * @throws IOException
     */
    public RetrievalWorkListParams(StringParameters param)
            throws IOException
    {
        super(param);
    }

    /**
     * Mapの情報を元にパラメータクラスを作成します。
     * @param initMap Map
     */
    public RetrievalWorkListParams(Map<Key, Object> initMap)
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
