// $Id: SupplierController.java 5028 2009-09-18 04:31:29Z kishimoto $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.emanager.util.P11Config;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.dbhandler.SupplierAlterKey;
import jp.co.daifuku.wms.base.dbhandler.SupplierHandler;
import jp.co.daifuku.wms.base.dbhandler.SupplierHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.SupplierSearchKey;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.entity.SupplierHistory;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;


/**
 * 仕入先マスタ情報コントローラクラスです。
 *
 *
 * @version $Revision: 5028 $, $Date: 2009-09-18 13:31:29 +0900 (金, 18 9 2009) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */


public class SupplierController
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
    // private String _instanceVar ;


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
    public SupplierController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * 該当荷主コードと仕入先コードのレコードをロックし、内容を返します。<br>
     * すでにロックされていた場合、ロックタイムアウトが発生することがあります。<br>
     * また、対象の仕入先マスタ情報が見つからなかったときは、要素数0の配列が
     * 返されます。
     * 
     * @param consignorCode 荷主コード
     * @param custmerCode 仕入先コード
     * @return 該当出荷先のマスタ情報
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public Supplier[] lock(String consignorCode, String custmerCode)
            throws ReadWriteException,
                LockTimeOutException
    {
        SupplierHandler ch = new SupplierHandler(getConnection());
        SupplierSearchKey key = new SupplierSearchKey();

        key.setConsignorCode(consignorCode);
        key.setSupplierCode(custmerCode);

        Supplier[] cents = (Supplier[])super.retryLock(key, ch);
        if (cents == null)
        {
            // no such records found.
            return new Supplier[0];
        }
        return cents;
    }

    /**
     * 仕入先マスタ情報を作成します。
     * 
     * @param src 未指定の項目はテーブルに保存されません。
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException すでにデータが登録済みの時スローされます。
     */
    public void insert(Supplier src, WmsUserInfo ui)
            throws DataExistsException,
                ReadWriteException
    {
        String pname = getCallerName();
        src.setRegistPname(pname);
        src.setRegistDate(new SysDate());
        src.setLastUpdatePname(pname);

        SupplierHandler ch = new SupplierHandler(getConnection());
        ch.create(src);

        // FIXME add log info create
    }

    /**
     * 仕入先マスタ情報を更新します。
     * 
     * @param oldCust 更新元の仕入先マスタ情報
     * @param newCust 更新データ
     * @param ui ユーザ情報
     * @throws ReadWriteException  データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException すでにデータが登録済みの時スローされます。
     * @throws NotFoundException 該当の仕入先マスタ情報が見つからなかったとき<br>
     * もしくは、すでに内容が変更されていたときスローされます。
     * @throws NoPrimaryException 対象仕入先マスタ情報が複数存在するときスローされます。
     */
    public void update(Supplier oldCust, Supplier newCust, WmsUserInfo ui)
            throws NotFoundException,
                DataExistsException,
                ReadWriteException,
                NoPrimaryException
    {
        SupplierAlterKey akey = new SupplierAlterKey();
        akey.setUpdateValues(newCust);

        String pname = getCallerName();
        newCust.setLastUpdatePname(pname);

        // keys to search
        FieldName[] keyFields = {
            Supplier.CONSIGNOR_CODE,
            Supplier.SUPPLIER_CODE,
        };

        SupplierSearchKey key = new SupplierSearchKey();
        key = (SupplierSearchKey)createKey(oldCust, key, keyFields);
        akey.setKey(key);

        SupplierHandler ch = new SupplierHandler(getConnection());
        int numrec = ch.modify(akey);
        if (numrec > 1)
        {
            throw new NoPrimaryException();
        }

        // FIXME add log info create
    }

    /**
     * 対象仕入先マスタ情報を削除します。
     * 
     * @param delCust 削除対象データの仕入先マスタ情報
     * @param ui ユーザ情報
     * @throws NotFoundException 該当の仕入先マスタ情報が見つからなかったとき<br>
     * もしくは、すでに内容が変更されていたときスローされます。
     * @throws DataExistsException すでにデータが登録済みの時スローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException 対象仕入先マスタ情報が複数存在するときスローされます。
     */
    public void delete(Supplier delCust, WmsUserInfo ui)
            throws NotFoundException,
                DataExistsException,
                ReadWriteException,
                NoPrimaryException
    {
        SupplierHandler ch = new SupplierHandler(getConnection());
        SupplierSearchKey key = new SupplierSearchKey();

        // keys to search
        FieldName[] keyFields = {
            Supplier.CONSIGNOR_CODE,
            Supplier.SUPPLIER_CODE,
        };

        key = (SupplierSearchKey)createKey(delCust, key, keyFields);

        int numrec = ch.drop(key);
        if (numrec > 1)
        {
            throw new NoPrimaryException();
        }

        // FIXME add log info create 
    }

    /**
     * 登録されている仕入先マスタ情報を更新します。<br>
     * まだ未登録の場合は仕入先マスタ情報を追加します。
     * 
     * @param src 更新する仕入先マスタ情報
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws NoPrimaryException 対象仕入先マスタ情報が複数存在するときスローされます。
     * @throws NotFoundException 対象仕入先マスタ情報が他のアプリケーションから変更されたときスローされます。
     * @throws DataExistsException 対象仕入先マスタ情報が他のアプリケーションから登録されたときスローされます。
     */
    public void autoCreate(Supplier src, WmsUserInfo ui)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException,
                NotFoundException,
                DataExistsException
    {
        // read master with lock
        Supplier[] cents = lock(src.getConsignorCode(), src.getSupplierCode());
        if (ArrayUtil.length(cents) > 1)
        {
            throw new NoPrimaryException();
        }

        if (ArrayUtil.isEmpty(cents))
        {
            // insert if no such record found
            insert(src, ui);
            return;
        }
        // update if exists
        update(cents[0], src, ui);
    }

    /**
     * 仕入先マスタ更新履歴情報登録処理を行います。
     * 
     * @param supplier      対象マスタ情報
     * @param operationKind 操作区分
     * @param ui            ユーザ情報
     * @throws ReadWriteException データベースエラー又は、マスタ情報がnullのとき
     * @throws DataExistsException 仕入先マスタ更新履歴情報登録済み
     */
    public void insertHistory(Supplier supplier, String operationKind, DfkUserInfo ui)
            throws ReadWriteException,
                DataExistsException
    {
        // PART11 Packageなし又は、改廃履歴不要指定時、処理を行わず復帰します。
        if (!P11Config.isPart11Log() || !P11Config.isMasterLog())
        {
            return;
        }

        if (null == supplier)
        {
            throw new ReadWriteException();
        }

        // create entity for insert
        SupplierHistoryHandler histch = new SupplierHistoryHandler(getConnection());
        SupplierHistory hist = new SupplierHistory();

        // ユーザID
        hist.setUserId(ui.getUserId());
        // ユーザ名称
        hist.setUserName(ui.getUserName());
        // 端末No
        hist.setTerminalNo(ui.getTerminalNumber());
        // 端末名称
        hist.setTerminalName(ui.getTerminalName());
        // 端末IPアドレス
        hist.setIpAddress(ui.getTerminalAddress());
        // DS番号
        hist.setDsNo(ui.getDsNumber());
        // リソース番号
        hist.setPagenameResourcekey(ui.getPageNameResourceKey());

        // 更新区分
        hist.setUpdateKind(operationKind);
        // 荷主コード
        hist.setConsignorCode(supplier.getConsignorCode());
        // 仕入先コード
        hist.setSupplierCode(supplier.getSupplierCode());

        if (SystemDefine.UPDATE_KIND_REGIST.equals(operationKind))
        {
            // 仕入先名称
            hist.setSupplierName("");
            // 修正後仕入先名称
            hist.setUpdateSupplierName(supplier.getSupplierName());
        }
        else
        {
            // 仕入先名称
            hist.setSupplierName(supplier.getSupplierName());
            // 修正後仕入先名称
            hist.setUpdateSupplierName("");
        }


        histch.create(hist);
    }

    /**
     * 仕入先マスタ更新履歴情報登録処理を行います。
     * 
     * @param oldsupplier   修正前マスタ情報
     * @param newsupplier   修正後マスタ情報
     * @param operationKind 操作区分
     * @param ui            ユーザ情報
     * @throws ReadWriteException データベースエラー又は、マスタ情報がnullのとき
     * @throws DataExistsException 仕入先マスタ更新履歴情報登録済み
     */
    public void insertHistory(Supplier oldsupplier, Supplier newsupplier, String operationKind, DfkUserInfo ui)
            throws ReadWriteException,
                DataExistsException
    {
        // PART11 Packageなし又は、改廃履歴不要指定時、処理を行わず復帰します。
        if (!P11Config.isPart11Log() || !P11Config.isMasterLog())
        {
            return;
        }

        if (null == oldsupplier)
        {
            throw new ReadWriteException();
        }
        if (null == newsupplier)
        {
            throw new ReadWriteException();
        }

        // create entity for insert
        SupplierHistoryHandler histch = new SupplierHistoryHandler(getConnection());
        SupplierHistory hist = new SupplierHistory();

        // ユーザID
        hist.setUserId(ui.getUserId());
        // ユーザ名称
        hist.setUserName(ui.getUserName());
        // 端末No
        hist.setTerminalNo(ui.getTerminalNumber());
        // 端末名称
        hist.setTerminalName(ui.getTerminalName());
        // 端末IPアドレス
        hist.setIpAddress(ui.getTerminalAddress());
        // DS番号
        hist.setDsNo(ui.getDsNumber());
        // リソース番号
        hist.setPagenameResourcekey(ui.getPageNameResourceKey());

        // 更新区分
        hist.setUpdateKind(operationKind);
        // 荷主コード
        hist.setConsignorCode(oldsupplier.getConsignorCode());
        // 仕入先コード
        hist.setSupplierCode(oldsupplier.getSupplierCode());
        // 仕入先名称
        hist.setSupplierName(oldsupplier.getSupplierName());
        // 修正後仕入先名称
        hist.setUpdateSupplierName(newsupplier.getSupplierName());

        histch.create(hist);
    }

    /**
     * 仕入先コードがマスタに存在するかどうかチェックして返します。
     * 
     * @param supcode 仕入先コード
     * @param cons 荷主コード
     * @return 存在する場合は true
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean exists(String supcode, String cons)
            throws ReadWriteException
    {
        SupplierHandler ch = new SupplierHandler(getConnection());
        SupplierSearchKey key = new SupplierSearchKey();

        key.setConsignorCode(cons);
        key.setSupplierCode(supcode);

        int cnt = ch.count(key);

        return (0 < cnt);
    }

    /**
     * 仕入先名称を取得します。<BR>
     * 該当仕入先が存在しなかった場合は0バイトの文字列を返します。
     * 
     * @param supplierCode 対象の仕入先コード
     * @param consignorCode 対象の荷主コード
     * @return 仕入先名称
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public String getName(String supplierCode, String consignorCode)
            throws ReadWriteException
    {
        try
        {
            SupplierHandler handler = new SupplierHandler(getConnection());
            SupplierSearchKey key = new SupplierSearchKey();

            key.setSupplierCode(supplierCode);
            key.setConsignorCode(consignorCode);
            key.setSupplierNameCollect();

            Supplier supplier = (Supplier)handler.findPrimary(key);
            return (null == supplier) ? ""
                                     : supplier.getSupplierName();
        }
        catch (NoPrimaryException e)
        {
            // 通常エラーですが名称取得時には空文字を返すようにします。
            return "";
        }
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
        return "$Id: SupplierController.java 5028 2009-09-18 04:31:29Z kishimoto $";
    }
}
