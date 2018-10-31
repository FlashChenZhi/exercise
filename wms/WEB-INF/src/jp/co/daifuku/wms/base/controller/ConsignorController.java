// $Id: ConsignorController.java 5020 2009-09-17 10:25:05Z kishimoto $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.base.entity.SystemDefine.MANAGEMENT_TYPE_SYSTEM;
import static jp.co.daifuku.wms.base.entity.SystemDefine.MANAGEMENT_TYPE_USER;

import java.sql.Connection;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.dbhandler.ConsignorAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;


/**
 * 荷主マスタ情報コントローラクラスです。
 *
 * @version $Revision: 5020 $, $Date: 2009-09-17 19:25:05 +0900 (木, 17 9 2009) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */


public class ConsignorController
        extends AbstractController
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


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public ConsignorController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 該当荷主コードのレコードをロックし、内容を返します。<br>
     * すでにロックされていた場合、ロックタイムアウトが発生することがあります。<br>
     * また、対象の荷主マスタ情報が見つからなかったときは、要素数0の配列が
     * 返されます。
     * 
     * @param consignorCode 荷主コード
     * @return 該当荷主コードのマスタ情報
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public Consignor[] lock(String consignorCode)
            throws ReadWriteException,
                LockTimeOutException
    {
        ConsignorHandler ch = new ConsignorHandler(getConnection());
        ConsignorSearchKey key = new ConsignorSearchKey();
        key.setConsignorCode(consignorCode);

        Consignor[] cents = (Consignor[])super.retryLock(key, ch);
        if (cents == null)
        {
            // no such records found.
            return new Consignor[0];
        }
        return cents;
    }

    /**
     * 荷主マスタ情報を作成します。
     * 
     * @param src 未指定の項目はテーブルに保存されません。
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException すでにデータが登録済みの時スローされます。
     */
    public void insert(Consignor src, WmsUserInfo ui)
            throws DataExistsException,
                ReadWriteException
    {
        String pname = getCallerName();
        src.setRegistPname(pname);
        src.setRegistDate(new SysDate());
        src.setLastUpdatePname(pname);

        ConsignorHandler ch = new ConsignorHandler(getConnection());
        ch.create(src);

        // FIXME add log info create
    }

    /**
     * 荷主マスタ情報を更新します。
     * 
     * @param oldConsig 更新元の荷主マスタ情報
     * @param newConsig 更新データ
     * @param ui ユーザ情報
     * @throws ReadWriteException  データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当の荷主マスタ情報が見つからなかったとき<br>
     * もしくは、すでに内容が変更されていたときスローされます。
     * @throws NoPrimaryException 対象荷主マスタ情報が複数存在するときスローされます。
     */
    public void update(Consignor oldConsig, Consignor newConsig, WmsUserInfo ui)
            throws NotFoundException,
                ReadWriteException,
                NoPrimaryException
    {
        ConsignorAlterKey akey = new ConsignorAlterKey();
        akey.setUpdateValues(newConsig);

        String pname = getCallerName();
        newConsig.setLastUpdatePname(pname);

        // keys to search
        FieldName[] keyFields = {
            Consignor.CONSIGNOR_CODE,
        };

        ConsignorSearchKey key = new ConsignorSearchKey();
        key = (ConsignorSearchKey)createKey(oldConsig, key, keyFields);
        akey.setKey(key);

        ConsignorHandler ch = new ConsignorHandler(getConnection());
        int numrec = ch.modify(akey);
        if (numrec > 1)
        {
            throw new NoPrimaryException();
        }

        // FIXME add log info create
    }

    /**
     * 対象荷主マスタ情報を削除します。
     * 
     * @param delConsig 削除対象データの荷主マスタ情報
     * @param ui ユーザ情報
     * @throws NotFoundException 該当の荷主マスタ情報が見つからなかったとき<br>
     * もしくは、すでに内容が変更されていたときスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException 対象荷主マスタ情報が複数存在するときスローされます。
     */
    public void delete(Consignor delConsig, WmsUserInfo ui)
            throws NotFoundException,
                ReadWriteException,
                NoPrimaryException
    {
        ConsignorHandler ch = new ConsignorHandler(getConnection());
        ConsignorSearchKey key = new ConsignorSearchKey();

        // keys to search
        FieldName[] keyFields = {
            Consignor.CONSIGNOR_CODE,
        };

        key = (ConsignorSearchKey)createKey(delConsig, key, keyFields);

        int numrec = ch.drop(key);
        if (numrec > 1)
        {
            throw new NoPrimaryException();
        }

        // FIXME add log info create 
    }

    /**
     * 登録されている荷主マスタ情報を更新します。<br>
     * まだ未登録の場合は荷主マスタ情報を追加します。<br>
     * 更新対象の荷主マスタ情報は、システム管理区分が通常のものだけになります。
     * 
     * @param src 更新する荷主マスタ情報
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws NoPrimaryException 対象荷主マスタ情報が複数存在するときスローされます。
     * @throws NotFoundException 対象荷主マスタ情報が他のアプリケーションから変更されたときスローされます。
     * @throws DataExistsException 対象荷主マスタ情報が他のアプリケーションから登録されたときスローされます。
     */
    public void autoCreate(Consignor src, WmsUserInfo ui)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException,
                NotFoundException,
                DataExistsException
    {
        // read master with lock
        Consignor[] cents = lock(src.getConsignorCode());
        if (ArrayUtil.length(cents) > 1)
        {
            throw new NoPrimaryException();
        }

        if (ArrayUtil.isEmpty(cents))
        {
            // insert if no such consignor
            src.setManagementType(MANAGEMENT_TYPE_USER);
            insert(src, ui);
            return;
        }

        Consignor cons = cents[0];

        String mtype = cons.getManagementType();
        if (MANAGEMENT_TYPE_SYSTEM.equals(mtype))
        {
            // return if management type is SYSTEM
            return;
        }

        // update if exists
        update(cons, src, ui);
    }

    /**
     * 荷主コードがマスタに存在するかどうかチェックして返します。
     * 
     * @param cons 荷主コード
     * @return 存在する場合は true
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean exists(String cons)
            throws ReadWriteException
    {
        ConsignorHandler ch = new ConsignorHandler(getConnection());
        ConsignorSearchKey key = new ConsignorSearchKey();
        key.setConsignorCode(cons);

        int cnt = ch.count(key);
        return (0 < cnt);
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
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
        return "$Id: ConsignorController.java 5020 2009-09-17 10:25:05Z kishimoto $";
    }
}
