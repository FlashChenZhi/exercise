// $Id: LstUserIdParams.java 3470 2009-03-13 08:58:14Z dmori $
package jp.co.daifuku.pcart.system.listbox.userid;

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
 * @version $Revision: 3470 $, $Date: 2009-03-13 17:58:14 +0900 (金, 13 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: dmori $
 */
public class LstUserIdParams
        extends Params
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** COLUMN_2 */
    public static final ParamKey COLUMN_2 = new ParamKey("COLUMN_2");

    /** COLUMN_3 */
    public static final ParamKey COLUMN_3 = new ParamKey("COLUMN_3");

    /** COLUMN_4 */
    public static final ParamKey COLUMN_4 = new ParamKey("COLUMN_4");

    /** COLUMN_5 */
    public static final ParamKey COLUMN_5 = new ParamKey("COLUMN_5");

    /** ROLE */
    public static final ParamKey ROLE = new ParamKey("ROLE");

    /** ROLE_ID */
    public static final ParamKey ROLE_ID = new ParamKey("ROLE_ID");

    /** USER_ID */
    public static final ParamKey USER_ID = new ParamKey("USER_ID");

    /** USER_NAME */
    public static final ParamKey USER_NAME = new ParamKey("USER_NAME");

    /** USER_ID_FROM */
    public static final ParamKey USER_ID_FROM = new ParamKey("USER_ID_FROM");

    /** USER_ID_TO */
    public static final ParamKey USER_ID_TO = new ParamKey("USER_ID_TO");

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
