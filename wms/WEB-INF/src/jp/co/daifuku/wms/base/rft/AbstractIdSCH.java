// $Id: AbstractIdSCH.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.rft;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.OutParameter;

/**
 * RFTServerの各IDに対応する実処理を実装するスケジュール処理クラスの
 * 基底クラスです。<br>
 *
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author ssuzuki@softecs.jp
 * @author Last commit: $Author: admin $
 * @since 2008-03-28 トランザクション関連メソッド追加
 */

public class AbstractIdSCH
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
    /** データベース接続を保持します。*/
    // TODO private化し、名称を _connection へ変更すること 2008-03-27 
    protected Connection _wConn;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * デフォルト・コンストラクタです。<br>
     * パッケージマネージャから生成されるため、各サブクラスでは
     * デフォルト・コンストラクタが必須です。
     */
    protected AbstractIdSCH()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /** 問合せ処理
     * 
     * @param inquiryParams     検索条件をもつ<CODE>InParameter</CODE>クラスを継承したクラス(配列)
     * @return                  取得データパラメータ。<CODE>OutParameter</CODE>クラスを継承したクラス(配列)
     * @throws CommonException  例外が発生した場合に通知されます。
     */
    public OutParameter[] inquirySCH(InParameter[] inquiryParams)
            throws CommonException
    {
        throw new ScheduleException("This method is not supported.");
    }

    /** 開始処理
     * 
     * @param startParams       開始条件をもつ<CODE>InParameter</CODE>クラスを継承したクラス(配列)
     * @return                  開始データパラメータ。<CODE>OutParameter</CODE>クラスを継承したクラス(配列)
     * @throws CommonException  例外が発生した場合に通知されます。
     */
    public OutParameter[] startSCH(InParameter[] startParams)
            throws CommonException
    {
        throw new ScheduleException("This method is not supported.");
    }

    /** 確定処理
     * 
     * @param compIdParam       完了データ(電文内容)。<CODE>InParameter</CODE>クラスを継承したクラス
     * @param compFileParams    完了データ(ファイル内容)。<CODE>InParameter</CODE>クラスを継承したクラス(配列)
     * @throws CommonException  例外が発生した場合に通知されます。
     */
    public void completeSCH(InParameter compIdParam, InParameter[] compFileParams)
            throws CommonException
    {
        throw new ScheduleException("This method is not supported.");
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * DBコネクションをセットします。
     * 
     * @param connection データベース接続用 Connection
     */
    public void setConnection(Connection connection)
    {
        _wConn = connection;
    }

    /**
     * DBコネクションを返します。
     * 
     * @return データベースコネクションを返します。
     */
    public Connection getConnection()
    {
        return _wConn;
    }

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
        return "$Id: AbstractIdSCH.java 87 2008-10-04 03:07:38Z admin $";
    }
}
