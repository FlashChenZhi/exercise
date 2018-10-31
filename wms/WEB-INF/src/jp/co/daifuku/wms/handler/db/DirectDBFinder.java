//$Id: DirectDBFinder.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.ReadWriteException;

/**
 * クラスをデータベースに保管したり、データベースから情報を取得して
 * インスタンスを生成したりするためのインターフェースです。<br>
 * 画面に一覧表示させる場合にこのクラスをimplementsして
 * <code>Entity</code>を返すクラスを実装してください。<br>
 * 検索に用いる<code>SQL</code>は、直接文字列として記述してください。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public interface DirectDBFinder
        extends BasicDatabaseFinder
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

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
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * データベースを検索し、取得します。
     * 
     * @param sql 検索SQL
     * @param limit 最大取得件数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @deprecated SQL文を直接実行する場合に、上限を指定できなくなりました。<br>
     *   search(String sql) メソッドを使用してください。
     */
    @Deprecated
    public void search(String sql, int limit)
            throws ReadWriteException;

    /**
     * データベースを検索し、取得します。
     * 
     * @param sql 検索SQL
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public void search(String sql)
            throws ReadWriteException;

    /**
     * データベースを検索し、取得します。<br>
     * レコードロックを行います。
     * 
     * @param sql 検索SQL
     * @param waitsec ロック待ち秒数<br>
     *  システムデフォルト：WAIT_SEC_DEFAULT<br>
     *  無制限待ち：WAIT_SEC_UNLIMITEDを指定します<br>
     *  待ち時間なし：WAIT_SEC_NOWAITを指定します<br>
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     * @throws LockTimeOutException ロック待ちタイムアウトまたは待ち時間なしの指定で、他の
     * トランザクションがロックを獲得中の場合
     */
    public void searchForUpdate(String sql, int waitsec)
            throws ReadWriteException,
                LockTimeOutException;

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
}
