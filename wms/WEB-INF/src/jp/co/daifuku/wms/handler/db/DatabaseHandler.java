//$Id: DatabaseHandler.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.db;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.EntityHandler;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerSysDefines;

/**
 * クラスをデータベースに保管したり、データベースから情報を取得して
 * インスタンスを生成したりするためのインターフェースです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public interface DatabaseHandler
        extends EntityHandler
{
    // Fields
    /** ロック待ち時間 (待ち時間なし) */
    public static final int WAIT_SEC_NOWAIT = HandlerSysDefines.WAIT_SEC_NOWAIT;

    /** ロック待ち時間 (無期限) */
    public static final int WAIT_SEC_UNLIMITED = HandlerSysDefines.WAIT_SEC_UNLIMITED;

    /** デフォルトのロック待ち時間 (文字列) */
    static final int WAIT_SEC_DEFAULT = HandlerSysDefines.WAIT_SEC_DEFAULT;

    // Public methods ------------------------------------------------
    /**
     * データベース接続用の<code>Connection</code>を設定します。
     * @param conn 設定するConnection
     */
    public void setConnection(Connection conn);

    /**
     * データベース接続用の<code>Connection</code>を取得します。
     * @return 現在保持している<code>Connection</code>
     */
    public Connection getConnection();

    /**
     * データベースから、パラメータに基づいて情報を検索し、オブジェクトを返します。
     * @param key 検索のためのKey
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public Entity[] find(SearchKey key)
            throws ReadWriteException;

    /**
     * データベースから、パラメータに基づいて情報を検索し、オブジェクトを返します。
     * @param key 検索のためのKey
     * @param limit 検索件数上限値
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public Entity[] find(SearchKey key, int limit)
            throws ReadWriteException;

    /**
     * データベースから、パラメータに基づいて情報を検索し、オブジェクトを返します。
     * 検索情報はPrimary(１件のみ）であること。（複数件存在した場合、NoPrimaryExceptionが通知されます。
     * @param key 検索のためのKey
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NoPrimaryException 取得データが一意とならなかった場合に通知されます。
     * @throws InvalidStatusException ステータスエラーを検出した場合に通知されます。
     */
    public Entity findPrimary(SearchKey key)
            throws ReadWriteException,
                NoPrimaryException,
                InvalidStatusException;

    /**
     * データベースから、パラメータに基づいて情報を検索し、オブジェクトを返します。<br>
     * 取得した情報をロックします。<br>
     * ロック待ち時間はシステムデフォルト(WAIT_SEC_DEFAULT)です。
     * 
     * @param key 検索のためのKey
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidStatusException ステータスエラーを検出した場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生したときに通知されます。 
     */
    public Entity[] findForUpdate(SearchKey key)
            throws ReadWriteException,
                InvalidStatusException,
                LockTimeOutException;

    /**
     * データベースから、パラメータに基づいて情報を検索し、オブジェクトを返します。<br>
     * 取得した情報をロックします。<br>
     * 取得上限件数は無制限です。
     * 
     * @param key 検索のためのKey
     * @param waitSec ロック待ち秒数<br>
     *  システムデフォルト:WAIT_SEC_DEFAULT<br>
     *  無制限待ち:WAIT_SEC_UNLIMITEDを指定します<br>
     *  待ち時間なし:WAIT_SEC_NOWAITを指定します<br>
     * @return 作成されたオブジェクトの配列<br>
     * ロックの取得に失敗したときは null
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidStatusException ステータスエラーを検出した場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生したときに通知されます。 
     */
    public Entity[] findForUpdate(SearchKey key, int waitSec)
            throws ReadWriteException,
                InvalidStatusException,
                LockTimeOutException;

    /**
     * データベースから、パラメータに基づいて情報を検索し、オブジェクトを返します。
     * 取得した情報をロックします。
     * @param key 検索のためのKey
     * @param limit 検索件数上限値
     * @param waitSec ロック待ち秒数<br>
     *  システムデフォルト:WAIT_SEC_DEFAULT<br>
     *  無制限待ち:WAIT_SEC_UNLIMITEDを指定します<br>
     *  待ち時間なし:WAIT_SEC_NOWAITを指定します<br>
     * @return 作成されたオブジェクトの配列<br>
     * ロックの取得に失敗したときは null
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidStatusException ステータスエラーを検出した場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生したときに通知されます。 
     */
    public Entity[] findForUpdate(SearchKey key, int limit, int waitSec)
            throws ReadWriteException,
                InvalidStatusException,
                LockTimeOutException;

    /**
     * データベースから、パラメータに基づいて情報を検索し、オブジェクトを返します。
     * 検索情報はPrimary(１件のみ）であること。（複数件存在した場合、NoPrimaryExceptionが通知されます。
     * 取得した情報をロックします。<br>
     * ロック待ち時間はシステムデフォルト(WAIT_SEC_DEFAULT)です。
     * 
     * @param key 検索のためのKey
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NoPrimaryException 取得データが一意とならなかった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生したときに通知されます。 
     */
    public Entity findPrimaryForUpdate(SearchKey key)
            throws ReadWriteException,
                NoPrimaryException,
                LockTimeOutException;

    /**
     * データベースから、パラメータに基づいて情報を検索し、オブジェクトを返します。
     * 検索情報はPrimary(１件のみ）であること。（複数件存在した場合、NoPrimaryExceptionが通知されます。
     * 取得した情報をロックします。
     * @param key 検索のためのKey
     * @param waitSec ロック待ち秒数<br>
     *  システムデフォルト:WAIT_SEC_DEFAULT<br>
     *  無制限待ち:WAIT_SEC_UNLIMITEDを指定します<br>
     *  待ち時間なし:WAIT_SEC_NOWAITを指定します<br>
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NoPrimaryException 取得データが一意とならなかった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生したときに通知されます。 
     */
    public Entity findPrimaryForUpdate(SearchKey key, int waitSec)
            throws ReadWriteException,
                NoPrimaryException,
                LockTimeOutException;

    /**
     * パラメータに基づいてテーブルのロックを行います。
     * 該当情報をロックします。他の処理がロック中でロックの獲得に失敗した場合
     * LockTimeOutExceptionを通知します。
     * @param key 検索のためのKey
     * @param waitSec ロック待ち秒数<br>
     *  システムデフォルト:WAIT_SEC_DEFAULT<br>
     *  無制限待ち:WAIT_SEC_UNLIMITEDを指定します<br>
     *  待ち時間なし:WAIT_SEC_NOWAITを指定します<br>
     * @return ロック対象があったときtrue
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生したときスローされます。
     */
    public boolean lock(SearchKey key, int waitSec)
            throws ReadWriteException,
                LockTimeOutException;

    /**
     * パラメータに基づいてテーブルのロックを行います。
     * 該当情報をロックします。他の処理がロック中でロックの獲得に失敗した場合
     * LockTimeOutExceptionを通知します。
     * ロック待ち時間は、WMSParamで定義された秒数が使用されます。
     * 
     * @param key 検索のためのKey
     * @return ロック対象があったときtrue
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生したときスローされます。
     */
    public boolean lock(SearchKey key)
            throws ReadWriteException,
                LockTimeOutException;

    /**
     * テーブルをロックします。
     * 
     * @param waitSec ロックタイムアウト秒数<br>
     * (Oracleでは<code>WAIT_SEC_NOWAIT</code>以外は無制限待ちになります)
     * 
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生したときスローされます。
     */
    public void lock(int waitSec)
            throws ReadWriteException,
                LockTimeOutException;

    /**
     * テーブルをロックします。<br>
     * ロックタイムアウト秒数は、デフォルトを使用します。<br>
     * Oracleでは無制限待ちになります。
     * 
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生したときスローされます。
     */
    public void lock()
            throws ReadWriteException,
                LockTimeOutException;

    /**
     * 関連するストア名 (テーブル名)を取得します。
     * 
     * @return ストア名
     */
    public String getStoreName();

    /**
     * 関連するストアメタデータを取得します。
     * 
     * @return ストアメタデータ
     */
    public StoreMetaData getStoreMetaData();

    /**
     * 関連するSQL文生成インスタンスを取得します。
     * 
     * @return SQL文生成インスタンス
     */
    public SQLGenerator getSQLGenerator();

    /**
     * 関連するSQLエラー解析インスタンスを取得します。
     * 
     * @return SQLエラー解析インスタンス
     */
    public SQLErrors getSQLErrors();

    /**
     * SQL文の実行は行わず、実行するSQL文のみ表示するモード
     * (Simulationモード) をON/OFFします。
     * 
     * @param modeOn trueのときSimulationモードON
     */
    public void setSimulationMode(boolean modeOn);
}
//end of interface
