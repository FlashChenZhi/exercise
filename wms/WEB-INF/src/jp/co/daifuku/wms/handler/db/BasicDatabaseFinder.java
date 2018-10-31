//$Id: BasicDatabaseFinder.java 4806 2009-08-10 06:29:24Z shibamoto $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.EntityHandler;
import jp.co.daifuku.wms.handler.util.HandlerSysDefines;

/**
 * クラスをデータベースに保管したり、データベースから情報を取得して
 * インスタンスを生成したりするための基本インターフェースです。<br>
 * 画面に一覧表示させる場合にこのクラスをimplementsして
 * <code>Entity</code>を返すクラスを実装してください。
 *
 * @version $Revision: 4806 $, $Date: 2009-08-10 15:29:24 +0900 (月, 10 8 2009) $
 * @author  ss
 * @author  Last commit: $Author: shibamoto $
 */

public interface BasicDatabaseFinder
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** リストボックス用最大取得件数 */
    public static final int MAXDISP = HandlerSysDefines.MAX_NUMBER_OF_DISP_LISTBOX;

    /** 検索時の件数指定(無制限) */
    public static final int LIMIT_UNLIMTED = EntityHandler.LIMIT_UNLIMTED;

    /** ロック待ち時間 (待ち時間なし) */
    public static final int WAIT_SEC_NOWAIT = HandlerSysDefines.WAIT_SEC_NOWAIT;

    /** ロック待ち時間 (無期限) */
    public static final int WAIT_SEC_UNLIMITED = HandlerSysDefines.WAIT_SEC_UNLIMITED;

    /** デフォルトのロック待ち時間 (文字列) */
    static final int WAIT_SEC_DEFAULT = HandlerSysDefines.WAIT_SEC_DEFAULT;

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
    /**
     * 内部情報の初期化を行います。
     * @param forwardOnly 検索方向が前方向のみの時true.
     */
    public void open(boolean forwardOnly);

    /**
     * ステートメント,結果セットをクローズします。<br>
     * コネクションはクローズしません。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public void close()
            throws ReadWriteException;

    /**
     * 検索結果を取得します。
     * @param start 取得開始行 (0 = 先頭)
     * @param end 取得終了行 (0 = 取得しない)
     * @return 検索結果
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public Entity[] getEntities(int start, int end)
            throws ReadWriteException;

    /**
     * 検索結果を取得します。
     * @param numRec 総取得行指定 (0 = 取得しない)
     * @return 検索結果
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public Entity[] getEntities(int numRec)
            throws ReadWriteException;

    /**
     * 次のデータが読み出せるかどうかを返します。<br>
     * <code>search()</code>後、内部ポインタがレコード件数よりも小さいとき
     * <code>true</code>が返されます。
     * @return <code>getEntities(int numRec)</code>にてデータが読み出せるとき
     * <code>true</code>を返します
     */
    public boolean hasNext();

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * ファインダで使用するコネクションを設定します。
     * @param conn データベースコネクション
     */
    public void setConnection(Connection conn);

    /**
     * ファインダで使用するコネクションを取得します。
     * @return データベースコネクション
     */
    public Connection getConnection();

    /**
     * 関連するSQLエラー解析インスタンスを取得します。
     * @return SQLエラー解析インスタンス
     */
    public SQLErrors getSQLErrors();
}
