// $Id: DsNo.java 3965 2009-04-06 02:55:05Z admin $
package jp.co.daifuku.emanager.database.entity;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.Serializable;


/**
 * DS番号に関するエンティティです。
 *
 *
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  Last commit: $Author: admin $
 */
public class DsNo
        extends BaseEntity
        implements Serializable
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
    // private String _instanceVar ;

    /**
     * DS番号
     */
    private String dsNo;

    /**
     * ページ名リソースキー
     */
    private String pageNameResourceKey;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

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


    /**
     * dsNoを返します。
     * @return dsNoを返します。
     */
    public String getDsNo()
    {
        return dsNo;
    }


    /**
     * dsNoを設定します。
     * @param dsNo dsNo
     */
    public void setDsNo(String dsNo)
    {
        this.dsNo = dsNo;
    }


    /**
     * pageNameResourceKeyを返します。
     * @return pageNameResourceKeyを返します。
     */
    public String getPageNameResourceKey()
    {
        return pageNameResourceKey;
    }


    /**
     * pageNameResourceKeyを設定します。
     * @param pageNameResourceKey pageNameResourceKey
     */
    public void setPageNameResourceKey(String pageNameResourceKey)
    {
        this.pageNameResourceKey = pageNameResourceKey;
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
        return "$Id: DsNo.java 3965 2009-04-06 02:55:05Z admin $";
    }
}
