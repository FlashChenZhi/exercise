//$Id: SQLGenerator.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.handler.Entity;

/**
 * SQL文を生成するためのインターフェースです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */
public interface SQLGenerator
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    /** type name of dbms (ORACLE) */
    public static final String DBMS_TYPE_ORACLE = "oracle";

    /** type name of dbms (Informix) */
    public static final String DBMS_TYPE_INFORMIX = "informix";

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
     * LOCK SQL文の構築を行います。
     * @param storeName 対象ストア名
     * @param waitSec ロック待ち時間(秒)<br>
     * 0 を指定すると無限待ち<br>
     * -1 を指定すると<code>NOWAIT</code>
     * @return Lock用SQL文
     */
    public String getLOCKSQL(String storeName, int waitSec);

    /**
     * 検索用SQL文を構築します。
     * @param key 検索キー
     * @return SQL文
     */
    public String getFINDSQL(SQLSearchKey key);

    /**
     * 検索,更新用SQL文を構築します。
     * @param key 検索キー
     * @return SQL文
     */
    public String getFINDFORUPDATESQL(SQLSearchKey key);

    /**
     * 検索,更新用SQL文を構築します。
     * @param key 検索キー
     * @param waitSec ロック待ち時間 (秒), 0を指定すると無限待ち, -1を指定するとNOWAIT
     * @return SQL文
     */
    public String getFINDFORUPDATEWAITSQL(SQLSearchKey key, int waitSec);

    /**
     * COUNT用のSQL文を構築します。
     * 
     * @param key 検索キー
     * @return SQL文
     */
    public String getCOUNTSQL(SQLSearchKey key);

    /**
     * SQL文を実行した結果、何行返されるかを取得するためのSQLを生成します。
     * @param key 検索キー
     * @return 行数を返すSQL文
     */
    public String getRecCOUNTSQL(SQLSearchKey key);

    /**
     * INSERT用のSQL文を構築します。
     * 
     * @param insertValue 挿入するデータ
     * @return SQL文
     */
    public String getINSERTSQL(Entity insertValue);

    /**
     * UPDATE用のSQL文を構築します。
     * @param upkey 更新するキー・データ
     * @return SQL文
     */
    public String getUPDATESQL(SQLAlterKey upkey);

    /**
     * DELETE用のSQL文を構築します。
     * @param delEntity 対象になるデータ
     * @return SQL文
     */
    public String getDELETESQL(Entity delEntity);

    /**
     * DELETE用のSQL文を構築します。
     * @param delKey 対象になるキー
     * @return SQL文
     */
    public String getDELETESQL(SQLSearchKey delKey);

    /**
     * 検索キーから、WHERE句のみを取得します。
     * 
     * @param key 検索キー
     * @return WHERE句
     */
    public String getWhereClause(SQLSearchKey key);

    /**
     * 検索キーに集約関数が含まれるかどうかチェックします。
     * 
     * @param key 検索キー
     * @return 集約関数が含まれる数
     */
    public int countIntensiveFunction(SQLSearchKey key);

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * SQL文で使用される記号一覧を返します。
     * @return 記号一覧
     */
    public String[] getSymbols();

    /**
     * SQL文で使用される命令一覧を返します。
     * @return 命令一覧
     */
    public String[] getSQLSyntaxes();

    /**
     * SQL文として認識するキーワード,関数の一覧を返します。
     * @return キーワード,関数の一覧
     */
    public String[] getSQLKeywords();
}
