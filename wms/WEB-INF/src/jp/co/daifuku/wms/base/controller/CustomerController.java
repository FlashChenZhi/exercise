// $Id: CustomerController.java 5020 2009-09-17 10:25:05Z kishimoto $
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
import jp.co.daifuku.wms.base.dbhandler.CustomerAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CustomerHandler;
import jp.co.daifuku.wms.base.dbhandler.CustomerHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.CustomerSearchKey;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.CustomerHistory;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;


/**
 * 出荷先マスタ情報コントローラクラスです。
 *
 * @version $Revision: 5020 $, $Date: 2009-09-17 19:25:05 +0900 (木, 17 9 2009) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */


public class CustomerController
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
    public CustomerController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 該当荷主コードと出荷先コードのレコードをロックし、内容を返します。<br>
     * すでにロックされていた場合、ロックタイムアウトが発生することがあります。<br>
     * また、対象の出荷先マスタ情報が見つからなかったときは、要素数0の配列が
     * 返されます。
     * 
     * @param consignorCode 荷主コード
     * @param custmerCode 出荷先コード
     * @return 該当出荷先のマスタ情報
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public Customer[] lock(String consignorCode, String custmerCode)
            throws ReadWriteException,
                LockTimeOutException
    {
        CustomerHandler ch = new CustomerHandler(getConnection());
        CustomerSearchKey key = new CustomerSearchKey();

        key.setConsignorCode(consignorCode);
        key.setCustomerCode(custmerCode);

        Customer[] cents = (Customer[])super.retryLock(key, ch);
        if (cents == null)
        {
            // no such records found.
            return new Customer[0];
        }
        return cents;
    }

    /**
     * 出荷先マスタ情報を作成します。
     * 
     * @param src 未指定の項目はテーブルに保存されません。
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException すでにデータが登録済みの時スローされます。
     */
    public void insert(Customer src, WmsUserInfo ui)
            throws DataExistsException,
                ReadWriteException
    {
        String pname = getCallerName();
        src.setRegistPname(pname);
        src.setRegistDate(new SysDate());
        src.setLastUpdatePname(pname);

        CustomerHandler ch = new CustomerHandler(getConnection());
        ch.create(src);

        // FIXME add log info create
    }

    /**
     * 出荷先マスタ情報を更新します。
     * 
     * @param oldCust 更新元の出荷先マスタ情報
     * @param newCust 更新データ
     * @param ui ユーザ情報
     * @throws ReadWriteException  データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException すでにデータが登録済みの時スローされます。
     * @throws NotFoundException 該当の出荷先マスタ情報が見つからなかったとき<br>
     * もしくは、すでに内容が変更されていたときスローされます。
     * @throws NoPrimaryException 対象出荷先マスタ情報が複数存在するときスローされます。
     */
    public void update(Customer oldCust, Customer newCust, WmsUserInfo ui)
            throws NotFoundException,
                DataExistsException,
                ReadWriteException,
                NoPrimaryException
    {
        CustomerAlterKey akey = new CustomerAlterKey();
        akey.setUpdateValues(newCust);

        String pname = getCallerName();
        newCust.setLastUpdatePname(pname);

        // keys to search
        FieldName[] keyFields = {
            Customer.CONSIGNOR_CODE,
            Customer.CUSTOMER_CODE,
        };

        CustomerSearchKey key = new CustomerSearchKey();
        key = (CustomerSearchKey)createKey(oldCust, key, keyFields);
        akey.setKey(key);

        CustomerHandler ch = new CustomerHandler(getConnection());
        int numrec = ch.modify(akey);
        if (numrec > 1)
        {
            throw new NoPrimaryException();
        }

        // FIXME add log info create
    }

    /**
     * 対象出荷先マスタ情報を削除します。
     * 
     * @param delCust 削除対象データの出荷先マスタ情報
     * @param ui ユーザ情報
     * @throws NotFoundException 該当の出荷先マスタ情報が見つからなかったとき<br>
     * もしくは、すでに内容が変更されていたときスローされます。
     * @throws DataExistsException すでにデータが登録済みの時スローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException 対象出荷先マスタ情報が複数存在するときスローされます。
     */
    public void delete(Customer delCust, WmsUserInfo ui)
            throws NotFoundException,
                DataExistsException,
                ReadWriteException,
                NoPrimaryException
    {
        CustomerHandler ch = new CustomerHandler(getConnection());
        CustomerSearchKey key = new CustomerSearchKey();

        // keys to search
        FieldName[] keyFields = {
            Customer.CONSIGNOR_CODE,
            Customer.CUSTOMER_CODE,
        };

        key = (CustomerSearchKey)createKey(delCust, key, keyFields);

        int numrec = ch.drop(key);
        if (numrec > 1)
        {
            throw new NoPrimaryException();
        }

        // FIXME add log info create 
    }

    /**
     * 登録されている出荷先マスタ情報を更新します。<br>
     * まだ未登録の場合は出荷先マスタ情報を追加します。<br>
     * 更新対象の出荷先マスタ情報は、システム管理区分が通常のものだけになります。
     * 
     * @param src 更新する出荷先マスタ情報
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws NoPrimaryException 対象出荷先マスタ情報が複数存在するときスローされます。
     * @throws NotFoundException 対象出荷先マスタ情報が他のアプリケーションから変更されたときスローされます。
     * @throws DataExistsException 対象出荷先マスタ情報が他のアプリケーションから登録されたときスローされます。
     */
    public void autoCreate(Customer src, WmsUserInfo ui)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException,
                NotFoundException,
                DataExistsException
    {
        // read master with lock
        Customer[] cents = lock(src.getConsignorCode(), src.getCustomerCode());
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
     * 出荷先マスタ更新履歴情報登録処理を行います。
     * 
     * @param customer      対象マスタ情報
     * @param operationKind 操作区分
     * @param ui            ユーザ情報
     * @throws ReadWriteException データベースエラー又は、マスタ情報がnullのとき
     * @throws DataExistsException 出荷先マスタ更新履歴情報登録済み
     */
    public void insertHistory(Customer customer, String operationKind, DfkUserInfo ui)
            throws ReadWriteException,
                DataExistsException
    {
        // PART11 Packageなし又は、改廃履歴不要指定時、処理を行わず復帰します。
        if (!P11Config.isPart11Log() || !P11Config.isMasterLog())
        {
            return;
        }

        if (null == customer)
        {
            throw new ReadWriteException();
        }

        // create entity for insert
        CustomerHistoryHandler histch = new CustomerHistoryHandler(getConnection());
        CustomerHistory hist = new CustomerHistory();

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
        hist.setConsignorCode(customer.getConsignorCode());
        // 出荷先コード
        hist.setCustomerCode(customer.getCustomerCode());
        if (SystemDefine.UPDATE_KIND_REGIST.equals(operationKind))
        {
            // 出荷先名称
            hist.setCustomerName("");
            // 修正後出荷先名称
            hist.setUpdateCustomerName(customer.getCustomerName());
            // ルート
            hist.setRoute("");
            // 修正後ルート
            hist.setUpdateRoute(customer.getRoute());
            // 郵便番号
            hist.setPostalCode("");
            // 修正後郵便番号
            hist.setUpdatePostalCode(customer.getPostalCode());
            // 都道府県名
            hist.setPrefecture("");
            // 修正後都道府県名
            hist.setUpdatePrefecture(customer.getPrefecture());
            // 住所
            hist.setAddress1("");
            // 修正後住所
            hist.setUpdateAddress1(customer.getAddress1());
            // ビル名等
            hist.setAddress2("");
            // 修正後ビル名等
            hist.setUpdateAddress2(customer.getAddress2());
            // TEL
            hist.setTelephone("");
            // 修正後TEL
            hist.setUpdateTelephone(customer.getTelephone());
            // 連絡先１
            hist.setContact1("");
            // 修正後連絡先１
            hist.setUpdateContact1(customer.getContact1());
            // 連絡先２
            hist.setContact2("");
            // 修正後連絡先２
            hist.setUpdateContact2(customer.getContact2());
            // 仕分場所
            hist.setSortingPlace("");
            // 修正後仕分場所
            hist.setUpdateSortingPlace(customer.getSortingPlace());
        }
        else
        {
            // 出荷先名称
            hist.setCustomerName(customer.getCustomerName());
            // 修正後出荷先名称
            hist.setUpdateCustomerName("");
            // ルート
            hist.setRoute(customer.getRoute());
            // 修正後ルート
            hist.setUpdateRoute("");
            // 郵便番号
            hist.setPostalCode(customer.getPostalCode());
            // 修正後郵便番号
            hist.setUpdatePostalCode("");
            // 都道府県名
            hist.setPrefecture(customer.getPrefecture());
            // 修正後都道府県名
            hist.setUpdatePrefecture("");
            // 住所
            hist.setAddress1(customer.getAddress1());
            // 修正後住所
            hist.setUpdateAddress1("");
            // ビル名等
            hist.setAddress2(customer.getAddress2());
            // 修正後ビル名等
            hist.setUpdateAddress2("");
            // TEL
            hist.setTelephone(customer.getTelephone());
            // 修正後TEL
            hist.setUpdateTelephone("");
            // 連絡先１
            hist.setContact1(customer.getContact1());
            // 修正後連絡先１
            hist.setUpdateContact1("");
            // 連絡先２
            hist.setContact2(customer.getContact2());
            // 修正後連絡先２
            hist.setUpdateContact2("");
            // 仕分場所
            hist.setSortingPlace(customer.getSortingPlace());
            // 修正後仕分場所
            hist.setUpdateSortingPlace("");
        }
        histch.create(hist);
    }

    /**
     * 出荷先マスタ更新履歴情報登録処理を行います。
     * 
     * @param oldcustomer   修正前マスタ情報
     * @param newcustomer   修正後マスタ情報
     * @param operationKind 操作区分
     * @param ui            ユーザ情報
     * @throws ReadWriteException データベースエラー又は、マスタ情報がnullのとき
     * @throws DataExistsException 出荷先マスタ更新履歴情報登録済み
     */
    public void insertHistory(Customer oldcustomer, Customer newcustomer, String operationKind, DfkUserInfo ui)
            throws ReadWriteException,
                DataExistsException
    {
        // PART11 Packageなし又は、改廃履歴不要指定時、処理を行わず復帰します。
        if (!P11Config.isPart11Log() || !P11Config.isMasterLog())
        {
            return;
        }

        if (null == oldcustomer)
        {
            throw new ReadWriteException();
        }
        if (null == newcustomer)
        {
            throw new ReadWriteException();
        }

        // create entity for insert
        CustomerHistoryHandler histch = new CustomerHistoryHandler(getConnection());
        CustomerHistory hist = new CustomerHistory();

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
        hist.setConsignorCode(oldcustomer.getConsignorCode());
        // 出荷先コード
        hist.setCustomerCode(oldcustomer.getCustomerCode());
        // 出荷先名称
        hist.setCustomerName(oldcustomer.getCustomerName());
        // 修正後出荷先名称
        hist.setUpdateCustomerName(newcustomer.getCustomerName());
        // ルート
        hist.setRoute(oldcustomer.getRoute());
        // 修正後ルート
        hist.setUpdateRoute(newcustomer.getRoute());
        // 郵便番号
        hist.setPostalCode(oldcustomer.getPostalCode());
        // 修正後郵便番号
        hist.setUpdatePostalCode(newcustomer.getPostalCode());
        // 都道府県名
        hist.setPrefecture(oldcustomer.getPrefecture());
        // 修正後都道府県名
        hist.setUpdatePrefecture(newcustomer.getPrefecture());
        // 住所
        hist.setAddress1(oldcustomer.getAddress1());
        // 修正後住所
        hist.setUpdateAddress1(newcustomer.getAddress1());
        // ビル名等
        hist.setAddress2(oldcustomer.getAddress2());
        // 修正後ビル名等
        hist.setUpdateAddress2(newcustomer.getAddress2());
        // TEL
        hist.setTelephone(oldcustomer.getTelephone());
        // 修正後TEL
        hist.setUpdateTelephone(newcustomer.getTelephone());
        // 連絡先１
        hist.setContact1(oldcustomer.getContact1());
        // 修正後連絡先１
        hist.setUpdateContact1(newcustomer.getContact1());
        // 連絡先２
        hist.setContact2(oldcustomer.getContact2());
        // 修正後連絡先２
        hist.setUpdateContact2(newcustomer.getContact2());
        // 仕分場所
        hist.setSortingPlace(oldcustomer.getSortingPlace());
        // 修正後仕分場所
        hist.setUpdateSortingPlace(newcustomer.getSortingPlace());

        histch.create(hist);
    }

    /**
     * 出荷先コードがマスタに存在するかどうかチェックして返します。
     * 
     * @param custcode 出荷先コード
     * @param cons 荷主コード
     * @return 存在する場合は true
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean exists(String custcode, String cons)
            throws ReadWriteException
    {
        CustomerHandler ch = new CustomerHandler(getConnection());
        CustomerSearchKey key = new CustomerSearchKey();

        key.setConsignorCode(cons);
        key.setCustomerCode(custcode);

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
        return "$Id: CustomerController.java 5020 2009-09-17 10:25:05Z kishimoto $";
    }
}
