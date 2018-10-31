// $Id: FieldName.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.field;

import java.io.Serializable;

import jp.co.daifuku.common.text.StringUtil;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * パラメータやエンティティで使用するフィールド名を保持するクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  S.Suzuki
 * @author  Last commit: $Author: admin $
 */
public class FieldName
        extends Object
        implements Serializable
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    /** <code>MAX_ALIAS_LENGTH</code> */
    private static final int MAX_ALIAS_LENGTH = 30;

    /** <code>serialVersionUID</code>のコメント */
    private static final long serialVersionUID = 4479741555704185722L;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** すべてのフィールドを示す場合のフィールド名 */
    public static final String ALL_FIELDS = "__ALL_FIELDS__";

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    /** The name of this field. */
    private String p_name;

    /** alias name for SQL */
    private String p_alias;

    /** store name */
    private String p_storeName;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     * Creating a instance with this field name.
     * @param storeName ストア名
     * @param name String field name.
     */
    public FieldName(String storeName, String name)
    {
        this(storeName, name, createAlias(storeName, name));
    }

    /**
     * Creating a instance with this field name and alias for SQL.
     * @param storeName ストア名
     * @param name String field name.
     * @param alias 別名として使用するカラム名です
     */
    public FieldName(String storeName, String name, String alias)
    {
        setStoreName(storeName);
        setName(name);
        setAlias(alias);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * get Hash code.
     *
     * @return int
     */
    @Override
    public int hashCode()
    {
        if (StringUtil.isBlank(p_alias))
        {
            return p_name.hashCode();
        }
        else
        {
            return p_alias.hashCode();
        }
    }

    /**
     * Check same contents.
     *
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof FieldName)
        {
            FieldName f = (FieldName)o;

            if (!getAlias().equals(f.getAlias()))
            {
                // if not same alias, not same field
                return false;
            }
            else if (getFullName().equals(f.getFullName()))
            {
                return true;
            }
            else if (getName().equals(f.getName()))
            {
                return true;
            }
            else if (getAlias().equals(f.getName()))
            {
                return true;
            }
            else if (getName().equals(f.getAlias()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Getting the name of this field.
     * <br>Usable for coding as "String + FieldName".
     *
     * @return String
     */
    @Override
    public String toString()
    {
        return getName();
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * Getting the name of this field.
     *
     * @return String
     */
    public String getName()
    {
        return p_name;
    }

    /**
     * Setting the name of this field.
     * @param name name of this field
     */
    public void setName(String name)
    {
        if (name == null || name.length() == 0)
        {
            throw new RuntimeException("Empty name does not supported.");
        }
        if (ALL_FIELDS.equals(name))
        {
            p_name = "*";
        }
        else
        {
            p_name = name.toUpperCase();
        }
    }

    /**
     * @return aliasを返します。
     */
    public String getAlias()
    {
        return p_alias;
    }

    /**
     * @param alias aliasを設定します。
     */
    public void setAlias(String alias)
    {
        if (alias == null || alias.length() == 0)
        {
            throw new RuntimeException("Empty alias does not supported.");
        }
        p_alias = alias.toUpperCase();
    }

    /**
     * "ストア名.フィールド名"の形式名を返します。
     * 
     * @return full name.
     */
    public String getFullName()
    {
        String sname = getStoreName();
        if (sname == null || sname.length() == 0)
        {
            return getName();
        }
        return sname + "." + getName();
    }

    /**
     * @return storeNameを返します。
     */
    public String getStoreName()
    {
        return p_storeName;
    }

    /**
     * @param storeName storeNameを設定します。
     */
    public void setStoreName(String storeName)
    {
        if (storeName == null)
        {
            p_storeName = "";
            return;
        }
        p_storeName = storeName.toUpperCase();
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
    /**
     * ストア名称とフィールド名称からエリアス名を返します。
     * 
     * @param storeName ストア名称
     * @param name フィールド名称
     * @return エリアス名を返します
     */
    static String createAlias(String storeName, String name)
    {
        if (ALL_FIELDS.equals(name))
        {
            return name;
        }
        StringBuilder buff = new StringBuilder("A");
        buff.append(Integer.toHexString(name.hashCode()));
        buff.append("_");
        buff.append(Integer.toHexString(storeName.hashCode()));

        // 30文字に満たない場合はカラム名を別名に追加
        int appLen = MAX_ALIAS_LENGTH - buff.length();
        if (0 < appLen)
        {
            int endp = Math.min(name.length(), appLen);
            buff.append(name.substring(0, endp));
        }

        return String.valueOf(buff);
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: FieldName.java 87 2008-10-04 03:07:38Z admin $";
    }
}
