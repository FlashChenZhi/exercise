//$Id: AbstractDefineHandler.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.field;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 定義XMLファイルをハンドリングするためのスーパークラスです。
 * 
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public abstract class AbstractDefineHandler
        extends DefaultHandler
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    private static final String MSG_NOT_DEFINED = " does not defined.";

    /** 内容定義 (true) */
    public static final String DEFINE_TRUE = "true";

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    //  private String  p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private boolean p_inProcess = false;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * @return _inProcessを返します。
     */
    protected boolean isInProcess()
    {
        return p_inProcess;
    }

    /**
     * @param process _inProcessを設定します。
     */
    protected void setInProcess(boolean process)
    {
        p_inProcess = process;
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * ブーリアン値をAttributesから取得します。
     * @param attributes
     * @param key
     * @param require 必須定義のとき true.
     * @return 真偽値
     */
    protected static boolean getBoolean(Attributes attributes, String key, boolean require)
    {
        boolean rval = false;
        String value = attributes.getValue(key);
        if (value == null)
        {
            if (require)
            {
                throw new RuntimeException(key + MSG_NOT_DEFINED);
            }
            rval = false;
        }
        else
        {
            rval = (DEFINE_TRUE.equals(value.trim().toLowerCase()));
        }

        return rval;
    }

    /**
     * 文字列値をAttributesから取得します。
     * @param attributes
     * @param key
     * @param require 必須定義のとき true.
     * @return 定義文字列
     */
    protected static String getString(Attributes attributes, String key, boolean require)
    {
        String rval = attributes.getValue(key);
        if (rval == null)
        {
            if (require)
            {
                throw new RuntimeException(key + MSG_NOT_DEFINED);
            }
            rval = "";
        }
        return rval;
    }

    /**
     * 数値をAttributesから取得します。
     * @param attributes
     * @param key
     * @param require 必須定義のとき true.
     * @return 定義数値
     */
    protected static BigDecimal getNumber(Attributes attributes, String key, boolean require)
    {
        String value = attributes.getValue(key);
        if (value == null)
        {
            if (require)
            {
                throw new RuntimeException(key + MSG_NOT_DEFINED);
            }
            return new BigDecimal(0);
        }
        try
        {
            return new BigDecimal(value);
        }
        catch (Exception e)
        {
            if (require)
            {
                throw new RuntimeException(key + MSG_NOT_DEFINED);
            }
            return new BigDecimal(0);
        }
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: AbstractDefineHandler.java 87 2008-10-04 03:07:38Z admin $";
    }
}
