// $Id: WmsSuperLocationInfo.java 4476 2009-06-23 00:29:03Z okamura $
package jp.co.daifuku.wms.base.util.location;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.foundation.common.location.LocationFormat;
import jp.co.daifuku.foundation.common.location.SuperLocationInfo;

/**
 * エリアについて管理情報を保持するためのクラスです。<br>
 * このクラスのインスタンスは、一つのエリアを管理します。
 *
 *
 * @version $Revision: 4476 $, $Date: 2009-06-23 09:29:03 +0900 (火, 23 6 2009) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */

public class WmsSuperLocationInfo
        extends SuperLocationInfo
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private String _style;

    private WmsLocationFormat _locationFormat;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * eWareNavi用のスーパーロケーション情報を生成します。<br>
     * このインスタンスは　WmsSuperLocationHolder クラスを
     * 通じて生成されます。
     * 
     * @param id エリアNo.(エリア識別ID)
     */
    protected WmsSuperLocationInfo(Object id)
    {
        super(id);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * 同一のエリアかどうかをチェックします。
     * 
     * @param sli2 比較対照エリア
     * @return 同一エリアNo.を持つとき true.
     */
    @Override
    public boolean equals(Object sli2)
    {
        if (sli2 instanceof WmsSuperLocationInfo)
        {
            WmsSuperLocationInfo sl2 = (WmsSuperLocationInfo)sli2;
            Object id2 = sl2.getId();
            Object id1 = getId();
            if (id1 == id2)
            {
                return true;
            }
            else if (null == id1)
            {
                return (null == id2);
            }
            else
            {
                return id1.equals(id2);
            }
        }
        return false;
    }

    /**
     * エリアNo.に基づいてハッシュコードを生成します。
     * 
     * @return ハッシュコード
     */
    @Override
    public int hashCode()
    {
        Object id = getId();
        if (null == id)
        {
            return 0;
        }
        return id.hashCode();
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * ロケーションスタイルを設定します。
     * 
     * @param style ロケーションスタイル
     */
    public synchronized void setLocationStyle(String style)
    {
        _style = style;
    }

    /**
     * ロケーションNo. をフォーマットするためのフォーマッターを
     * 返します。
     * 
     * @return ロケーションフォーマッター
     */
    @Override
    public synchronized LocationFormat getLocationFormat()
    {
        if (null == _locationFormat)
        {
            _locationFormat = new WmsLocationFormat(_style);
        }
        return _locationFormat;
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.foundation.common.location.SuperLocationInfo#getFormatStyle()
     */
    @Override
    public String getFormatStyle()
    {
        return _style;
    }
    
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
        return "$Id: WmsSuperLocationInfo.java 4476 2009-06-23 00:29:03Z okamura $";
    }



}
