//$Id: AbstractMetaData.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.field;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * ストアやフィールド定義のスーパークラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public abstract class AbstractMetaData
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    /** 定義名 */
    protected String p_name = null;

    /** 名称リソースID */
    private String p_resourceID = null;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 保持している内容の文字列表現を返します。
     * @return 文字列表現
     */
    public String toString()
    {
        StringBuffer buff = new StringBuffer();

        buff.append("Name:");
        buff.append(getName());
        buff.append(",ResourceID:");
        buff.append(getResourceID());

        return buff.toString();
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * @return nameを返します。
     */
    public String getName()
    {
        return p_name;
    }

    /**
     * @param name nameを設定します。
     */
    public void setName(String name)
    {
        setName(name, true);
    }

    /**
     * @param name nameを設定します。
     * @param overwrite 上書きするときtrue.
     */
    public void setName(String name, boolean overwrite)
    {
        if (p_name == null || overwrite)
        {
            p_name = name.toUpperCase();
        }
    }

    /**
     * @return resourceIDを返します。
     */
    public String getResourceID()
    {
        return p_resourceID;
    }

    /**
     * @param resourceID resourceIDを設定します。
     */
    public void setResourceID(String resourceID)
    {
        setResourceID(resourceID, true);
    }

    /**
     * @param resourceID resourceIDを設定します。
     * @param overwrite 上書きするときtrue.
     */
    public void setResourceID(String resourceID, boolean overwrite)
    {
        if (p_resourceID == null || overwrite)
        {
            p_resourceID = resourceID;
        }
    }

    /**
     * 同一名称のメタデータかどうかチェックします。
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object t)
    {
        if (t instanceof AbstractMetaData)
        {
            AbstractMetaData tm = (AbstractMetaData)t;
            return tm.getName().equals(getName());
        }
        return false;
    }

    /**
     * ハッシュコードを返します。
     * @return 名称のハッシュコード
     */
    public int hashCode()
    {
        String name = getName();
        int hcode = (null == name) ? 0
                                  : name.hashCode();
        return hcode;
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
        return "$Id: AbstractMetaData.java 87 2008-10-04 03:07:38Z admin $";
    }
}
