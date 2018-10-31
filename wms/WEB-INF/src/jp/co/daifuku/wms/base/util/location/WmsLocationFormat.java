// $Id: WmsLocationFormat.java 5194 2009-10-20 11:33:24Z shibamoto $
package jp.co.daifuku.wms.base.util.location;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.bluedog.model.StringSupport;
import jp.co.daifuku.foundation.common.location.LocationFormat;
import jp.co.daifuku.wms.base.common.LocationNumber;

/**
 * eWarenavi向けのロケーションフォーマットクラスです。<br>
 * 画面入力データならびにリストセル用のロケーションNo.を
 * フォーマットまたはパースします。
 *
 *
 * @version $Revision: 5194 $, $Date: 2009-10-20 20:33:24 +0900 (火, 20 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: shibamoto $
 */

public class WmsLocationFormat
        implements LocationFormat
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** フォーマットパターンの棚部分に使用されている文字(数値) */
    public static final char LOCATION_FORMAT_PATTERN_NUMBER = LocationNumber.LOCATION_FORMAT_PATTERN_NUMBER;

    /** フォーマットパターンの棚部分に使用されている文字 */
    public static final char LOCATION_FORMAT_PATTERN_CHAR = LocationNumber.LOCATION_FORMAT_PATTERN_CHAR;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;
    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private String _locationStyle;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * ロケーションスタイルを指定してフォーマッターを初期化します。
     * 
     * @param style ロケーションスタイル
     */
    public WmsLocationFormat(String style)
    {
        super();
        _locationStyle = style;
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * ロケーションをフォーマットします。
     * 
     * @param locationNo ロケーションNo.
     * @param areaNo 使用しません
     * @return フォーマット済みロケーションNo.
     */
    public String format(Object locationNo, Object... areaNo)
    {
        String locationStr = StringSupport.valueOf(locationNo);
        if (isEmptyValues(locationNo, areaNo))
        {
            return locationStr;
        }

        try
        {
            char[] styleChars = _locationStyle.toCharArray();
            StringBuilder buff = new StringBuilder();

            int sidx = 0;
            for (int i = 0; i < styleChars.length; i++)
            {
                char fchar = styleChars[i];
                if (fchar != LOCATION_FORMAT_PATTERN_CHAR &&
                		fchar != LOCATION_FORMAT_PATTERN_NUMBER)
                {
                    buff.append(fchar);
                }
                else
                {
                    buff.append(locationStr.charAt(sidx++));
                }
            }

            // return plain location number, if style changed.
            boolean styleChanged = (buff.length() != _locationStyle.length());
            return styleChanged ? locationStr
                               : StringSupport.valueOf(buff);
        }
        catch (RuntimeException e)
        {
            return locationStr;
        }
    }

    /**
     * 空のデータかどうかをチェックします。
     * 
     * @param locationNo ロケーションNo
     * @param areaNo エリアNo
     * @return いずれかがnullまたは空の文字列のとき true
     */
    private boolean isEmptyValues(Object locationNo, Object[] areaNo)
    {
        // check null
        if (null == locationNo || null == areaNo)
        {
            return true;
        } // check array size
        else if (0 == areaNo.length)
        {
            return true;
        } // check empty string of location number
        else if (0 == StringSupport.valueOf(locationNo).length())
        {
            return true;
        } // check empty string of area number
        else if (0 == StringSupport.valueOf(areaNo[0]).length())
        {
            return true;
        }
        return false;
    }

    /**
     * フォーマット済みロケーションNo.をパースします。
     * 
     * @param locationNo フォーマット済みロケーションNo.
     * @param areaNo 使用しません
     * @return パース後のロケーションNo.
     */
    public Object parse(String locationNo, Object... areaNo)
    {
        boolean styleChanged = (locationNo.length() != _locationStyle.length());
        if (styleChanged)
        {
            // if style changed, can not parse.
            // return original location number.
            return locationNo;
        }

        char[] styleChars = _locationStyle.toCharArray();

        String locationChars = StringSupport.valueOf(locationNo);
        StringBuilder buff = new StringBuilder();

        int sidx = 0;
        for (int i = 0; i < styleChars.length; i++)
        {
            char fchar = styleChars[i];
            if (fchar == LOCATION_FORMAT_PATTERN_CHAR ||
            		fchar == LOCATION_FORMAT_PATTERN_NUMBER)
            {
                buff.append(locationChars.charAt(sidx));
            }
            sidx++;
        }
        return StringSupport.valueOf(buff);
    }

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
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: WmsLocationFormat.java 5194 2009-10-20 11:33:24Z shibamoto $";
    }
}
