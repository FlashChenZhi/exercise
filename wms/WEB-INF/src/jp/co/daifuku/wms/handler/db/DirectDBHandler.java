// $Id: DirectDBHandler.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.ResultSet;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.handler.Entity;

/**
 * SQL文を直接実行するためのインターフェースです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */
public interface DirectDBHandler
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** execute()の結果 (問い合わせの場合) */
    public static final int RETURN_EXEC_QUERY = -1;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * SQL文を実行します。<br>
     * 問い合わせSQL文の場合は、このメソッドを実行後、
     * getResultSet()でResultSetを取得する事ができます。<br>
     * Entityとして取得したい場合は、getEntities()を使用してください。
     * 
     * @param SQL 実行するSQL文
     * @return 実行結果(更新系の場合は行数, 問い合わせ系の場合は RETURN_EXEC_QUERY)
     * @throws ReadWriteException SQLの実行に失敗したときスローされます。
     */
    public int execute(String SQL)
            throws ReadWriteException;

    /**
     * 問い合わせを実行します。
     * 
     * @param SQL 実行するSQL文
     * @param entityTemp エンティティ・テンプレート
     * @return 問い合わせ結果エンティティ
     * @throws ReadWriteException SQLの実行に失敗したときスローされます。
     */
    public Entity[] query(String SQL, Entity entityTemp)
            throws ReadWriteException;

    /**
     * 内部で使用するリソースをクローズします。<br>
     * ただし、コンストラクタで受け取ったデータベースコネクションは
     * クローズしません。
     */
    public void close();

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 問い合わせ実行結果のResultSetを取得します。
     * @return ResultSet (nullの場合があります)
     */
    public ResultSet getResultSet();

    /**
     * 問い合わせ実行結果からEntityを作成,取得します。<br>
     * execute()実行後、このメソッドを呼び出すと指定した件数分の
     * エンティティが取得され、2回目以降の呼び出しでは続きが
     * 取得されます。<br>
     * 最後まで読み出し終わった場合には、要素数0の配列が返されます。<br>
     * また、最後まで読み終わったとき、ResultSetはクローズされます。
     * 
     * @param numRec 取得する件数
     * @param entityTemp エンティティ・テンプレート
     * @return 問い合わせ結果エンティティ<br>
     * 取り出せる結果がないときは要素数0
     * @throws ReadWriteException SQLの実行に失敗したときスローされます。
     */
    public Entity[] getEntities(int numRec, Entity entityTemp)
            throws ReadWriteException;

    /**
     * @return データベースコネクションを返します。
     */
    public Connection getConnection();

    /**
     * @param connection データベースコネクションを設定します。
     */
    public void setConnection(Connection connection);

    /**
     * 実際には実行せずSQL文を標準出力に表示するモードを設定します。
     * 
     * @param modeOn trueの時シミュレーションモード
     */
    public void setSimulationMode(boolean modeOn);
}
