// $Id: DBStoredParameter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright(c) 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;

/**
 * ストアドプロシジャー呼び出しの際に使用するOutパラメータクラスです。<br>
 * ストアドプロシジャーからの戻り値の指定および値について管理します。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class DBStoredParameter
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** パラメータタイプ (文字列) */
    public static final int TYPE_STRING = Types.VARCHAR;

    /** パラメータタイプ (数値) */
    public static final int TYPE_NUMBER = Types.NUMERIC;

    /** パラメータタイプ (日付時刻) */
    public static final int TYPE_DATE = Types.TIMESTAMP;

    /** パラメータ方向 (IN: プロシジャに渡す) */
    public static final int PARAM_IN = 0x1;

    /** パラメータ方向 (OUT: プロシジャから受取る) */
    public static final int PARAM_OUT = 0x2;

    /** パラメータ方向 (INOUT: 双方向) */
    public static final int PARAM_INOUT = PARAM_IN | PARAM_OUT;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private int p_scale = 0;

    private int p_type = TYPE_STRING;

    private String p_paramName;

    private Object p_value = null;

    private int p_direction = PARAM_OUT;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * タイプを指定してパラメータインスタンスを生成します。<br>
     * <code>TYPE_NUMBER</code>を指定したとき、小数部の桁数
     * は0 (なし)になります。
     * 
     * @param type データのタイプ (TYPE_XXXを指定します)
     * @param inOut パラメータ方向 (PARAM_XXX を指定します)
     */
    public DBStoredParameter(int type, int inOut)
    {
        this(type, 0, inOut);
    }

    /**
     * タイプと小数部の桁数を指定してパラメータインスタンスを生成します。<br>
     * <code>TYPE_NUMBER</code>の時に、小数部の桁数が0でないとき
     * に使用します。
     * 
     * @param type データのタイプ (TYPE_XXXを指定します)
     * @param scale 小数部の桁数
     * @param inOut パラメータ方向 (PARAM_XXX を指定します)
     */
    public DBStoredParameter(int type, int scale, int inOut)
    {
        setType(type);
        setScale(scale);
        setDirection(inOut);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * @return paramNameを返します。
     * @deprecated 将来拡張用です。
     */
    public String getParamName()
    {
        return p_paramName;
    }

    /**
     * @param paramName paramNameを設定します。
     * @deprecated 将来拡張用です。
     */
    public void setParamName(String paramName)
    {
        p_paramName = paramName.toUpperCase();
    }

    /**
     * @return valueを返します。
     */
    public Object getValue()
    {
        return p_value;
    }

    /**
     * @param value valueを設定します。
     */
    public void setValue(Object value)
    {
        // check type of value
        boolean ctype = false;
        switch (getType())
        {
            case TYPE_DATE:
                ctype = (value instanceof Date);
                break;
            case TYPE_NUMBER:
                ctype = (value instanceof BigDecimal);
                break;
            case TYPE_STRING:
                ctype = (value instanceof String);
                break;
        }
        if (!ctype)
        {
            throw new RuntimeException("Value type mismatch, type=" + getType() + ",value=" + value.getClass());
        }
        p_value = value;
    }

    /**
     * パラメータ方向を取得します。
     * @return directionを返します。
     */
    public int getDirection()
    {
        return p_direction;
    }

    /**
     * パラメータ方向を設定します。
     * @param direction PARAM_XXX を指定します。
     */
    public void setDirection(int direction)
    {
        p_direction = direction;
    }

    /**
     * パラメータ方向がINであるかどうか返します。
     * 
     * @return INパラメータのとき<code>true</code>
     */
    public boolean isInParam()
    {
        return 0 != (getDirection() & PARAM_IN);
    }

    /**
     * パラメータ方向がOUTであるかどうか返します。
     * 
     * @return OUTパラメータのとき<code>true</code>
     */
    public boolean isOutParam()
    {
        return 0 != (getDirection() & PARAM_OUT);
    }

    /**
     * @return typeを返します。
     */
    public int getType()
    {
        return p_type;
    }

    /**
     * @param type typeを設定します。
     */
    public void setType(int type)
    {
        switch (type)
        {
            case TYPE_DATE:
            case TYPE_NUMBER:
            case TYPE_STRING:
                p_type = type;
                break;
            default:
                throw new RuntimeException("DBOutParameter: invalid data type (" + type + ")");
        }
    }

    /**
     * 数値タイプの時、小数部の桁数を取得します。
     * @return 桁数
     */
    public int getScale()
    {
        return p_scale;
    }

    /**
     * 数値タイプの時、小数部の桁数をセットします。
     * @param len 桁数
     */
    public void setScale(int len)
    {
        p_scale = len;
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

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
        return "$Id: DBStoredParameter.java 87 2008-10-04 03:07:38Z admin $";
    }
}
