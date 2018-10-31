// $Id: SQLErrors.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.SQLException;

/**
 * SQL実行エラーのチェックを行うためのインターフェースです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public interface SQLErrors
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 定義エラーコード(詳細不明) */
    public static final int ERR_UNKNOWN = -1;

    /** 定義エラーコード(正常) */
    public static final int ERR_NORMAL = 0;

    /** 定義エラーコード(ロック失敗) */
    public static final int ERR_LOCK_FAILED = 61;

    /** 定義エラーコード(ロックタイムアウト) */
    public static final int ERR_LOCK_TIMEOUT = 62;

    /** 定義エラーコード(キー重複) */
    public static final int ERR_DUPLICATED = 1;

    /** 定義エラーコード(デッドロック) */
    public static final int ERR_DEADLOCK = 60;

    //------------------------------------------------------------
    // DBMS defines
    //------------------------------------------------------------
    /** type name of dbms (ORACLE) */
    public static final String DBMS_TYPE_ORACLE = SQLGenerator.DBMS_TYPE_ORACLE;

    /** type name of dbms (Informix) */
    public static final String DBMS_TYPE_INFORMIX = SQLGenerator.DBMS_TYPE_INFORMIX;

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

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * SQLExceptionから、内部定義エラーコードへの変換を行います。
     * @param e SQL実行時に発生した例外
     * @return 内部定義エラーコード
     */
    public int parseErrorCode(SQLException e);

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
}
