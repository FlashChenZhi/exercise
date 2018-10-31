// $Id: EnvironmentInfoDefine.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.common;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */



/**
 * 環境情報定義ファイルで使用する固定値を定義するクラスです<br>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  020246
 * @author  Last commit: $Author: admin $
 */


public class EnvironmentInfoDefine
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * 報告単位：予定単位(集約)
     */
    public static final String COLLECT_CONDITION_PLAN = "0";
    
    /**
     * 報告単位：予定単位(明細)
     */
    public static final String COLLECT_CONDITION_DETAIL = "1";
    
    /**
     * 報告単位：作業単位
     */
    public static final String COLLECT_CONDITION_WORK = "2";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * TODO default constructor.
     */
    public EnvironmentInfoDefine()
    {
        super();
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
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: EnvironmentInfoDefine.java 87 2008-10-04 03:07:38Z admin $";
    }
}

