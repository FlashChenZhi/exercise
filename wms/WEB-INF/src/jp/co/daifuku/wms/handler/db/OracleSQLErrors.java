// $Id: OracleSQLErrors.java 6466 2009-12-15 08:44:23Z okamura $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Oracleデータベースエラーをハンドリングするためのクラスです。
 *
 * @version $Revision: 6466 $, $Date: 2009-12-15 17:44:23 +0900 (火, 15 12 2009) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */

public class OracleSQLErrors
        extends AbstractSQLErrors
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     * インスタンスを生成します。<br>
     * インスタンスの生成は AbstractSQLErrors.getInstance()を使用してください。
     */
    protected OracleSQLErrors()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getLocalErrorCode(int errcode)
    {
        int localErr = ERR_UNKNOWN;
        switch (errcode)
        {
            case 30006:
                localErr = ERR_LOCK_TIMEOUT;
                break;
            case 54:
                localErr = ERR_LOCK_FAILED;
                break;
            case 1:
                localErr = ERR_DUPLICATED;
                break;
            case 60:
                localErr = ERR_DEADLOCK;
                break;
        }
        return localErr;
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
        return "$Id: OracleSQLErrors.java 6466 2009-12-15 08:44:23Z okamura $";
    }
}
