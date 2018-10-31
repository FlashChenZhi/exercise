// $Id: AbstractSQLErrors.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.SQLException;

/**
 * データベースエラーをハンドリングするためのクラスです。<br>
 * エラーコードはDBMSごとに異なるため、このクラスを継承したクラスで
 * 内部エラーコードに変換を行ってください。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public abstract class AbstractSQLErrors
        implements SQLErrors
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
     * 内部情報については初期状態で初期化します。
     */
    protected AbstractSQLErrors()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * SQLExceptionから、内部定義エラーコードへの変換を行います。
     * @param e SQL実行時に発生した例外
     * @return 内部定義エラーコード
     */
    public int parseErrorCode(SQLException e)
    {
        int errcode = e.getErrorCode();
        return getLocalErrorCode(errcode);
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * SQLErrorsクラスの実装を返します。
     * @param dbmsType データベースタイプ
     * @return SQLErrorsクラス
     */
    public static SQLErrors getInstance(String dbmsType)
    {
        String tdb = dbmsType.trim().toLowerCase();
        if (DBMS_TYPE_ORACLE.equals(tdb))
        {
            return new OracleSQLErrors();
        }
        else if (DBMS_TYPE_INFORMIX.equals(tdb))
        {
            return null;
        }
        return null;
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
     * SQLエラーコードから内部エラーコードを取得します。
     * @param errcode SQLエラーコード
     * @return 内部エラーコード
     */
    protected abstract int getLocalErrorCode(int errcode);

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
        return "$Id: AbstractSQLErrors.java 87 2008-10-04 03:07:38Z admin $";
    }

}
