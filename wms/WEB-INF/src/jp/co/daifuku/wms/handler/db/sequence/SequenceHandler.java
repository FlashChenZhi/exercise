// $Id: SequenceHandler.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.db.sequence;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.lang.reflect.Method;
import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;

/**
 * 順序オブジェクトを管理するためのインターフェースです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public interface SequenceHandler
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** シーケンスのチェッカメソッドのパラメータ定義 */
    public static final Class[] CHECKER_PARAMS = {
        Connection.class, // DB Connection
        SequenceInfo.class, // sequence for check
        Long.TYPE, // next number
    };

    /** シーケンスの最大シーケンス取得メソッドのパラメータ定義 */
    public static final Class[] MAX_PARAMS = {
        Connection.class, // DB Connection
        SequenceInfo.class
    // sequence information
            };

    /** チェッカメソッドの接尾語 */
    public static final String CHECKER_POSTFIX = "_check";

    /** 最大シーケンス取得メソッドの接尾語 */
    public static final String MAX_POSTFIX = "_max";

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
     * 次のシーケンスを取得します。<br>
     * 最大値チェックを行ったとき、次の値が最大値よりも小さければ
     * シーケンスオブジェクトを最大値まで引き上げます。<br>
     * したがって、チェックメソッドは呼び出されません。
     * 
     * @param seqinf シーケンス情報
     * @return シーケンス
     * @throws ReadWriteException データベースエラーを検出した場合スローされます。
     */
    public long getNext(SequenceInfo seqinf)
            throws ReadWriteException;

    /**
     * 次のシーケンスを取得します。<br>
     * 最大値チェックを行ったとき、次の値が最大値よりも小さければ
     * チェックメソッドを呼び出して、OKになるまで次の値を取得します。<br>
     * このとき、再取得数を指定可能です。(<code>RETRY_UNLIMITED</code>
     * を指定した場合は無制限に再取得します。)
     * 
     * @param seqinf
     * @param cnt 再取得回数(<code>RETRY_UNLIMITED</code> を指定すると
     * 無制限に再取得を行います)
     * @return シーケンス
     * @throws ReadWriteException データベースエラーを検出した場合スローされます。
     */
    public long getNext(SequenceInfo seqinf, long cnt)
            throws ReadWriteException;

    /**
     * シーケンスを初期化します。
     * @param seqinf シーケンス情報
     * @throws ReadWriteException データベースエラーを検出した場合スローされます。
     */
    public void reset(SequenceInfo seqinf)
            throws ReadWriteException;

    /**
     * シーケンス最大値を取得します。
     * @param seqinf シーケンス情報
     * @return シーケンス最大値
     */
    // public long getMax(SequenceInfo seqinf) ;
    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * データベースコネクションを設定します。
     * @param conn コネクション
     */
    public void setConnection(Connection conn);

    /**
     * データベースコネクションを取得します。
     * @return コネクション
     */
    public Connection getConnection();

    /**
     * チェッカーメソッドを取得します。<br>
     * チェッカーメソッドは、シーケンス名 + check という
     * 名称で表されます。
     * 
     * @param seqinf チェック対象のシーケンス情報
     * @return チェッカーメソッド
     * @throws NoSuchMethodException 該当のメソッドが存在しない
     * @throws SecurityException セキュリティマネージャに許可されていない
     */
    public Method getChecker(SequenceInfo seqinf)
            throws SecurityException,
                NoSuchMethodException;

    /**
     * 最大シーケンス取得メソッドを取得します。<br>
     * 最大シーケンス取得メソッドは、シーケンス名 + max という
     * 名称で表されます。
     * 
     * @param seqinf チェック対象のシーケンス情報
     * @return 最大シーケンス取得メソッド
     * @throws NoSuchMethodException 該当のメソッドが存在しない
     * @throws SecurityException セキュリティマネージャに許可されていない
     */
    public Method getMaxSeqMethod(SequenceInfo seqinf)
            throws SecurityException,
                NoSuchMethodException;

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
}
