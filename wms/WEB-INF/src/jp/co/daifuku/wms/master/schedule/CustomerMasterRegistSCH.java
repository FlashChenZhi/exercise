// $Id: CustomerMasterRegistSCH.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.master.schedule.CustomerMasterRegistSCHParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.controller.CustomerController;
import jp.co.daifuku.wms.base.dbhandler.CustomerHandler;
import jp.co.daifuku.wms.base.dbhandler.CustomerSearchKey;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**
 * 出荷先マスタ登録のスケジュール処理を行います。
 * 
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class CustomerMasterRegistSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public CustomerMasterRegistSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 出荷先マスタ登録処理<BR>
     * startParamsで指定された内容をもとに、出荷先マスタの登録処理を行う。<BR>
     * 正常に登録出来た場合はtrueを、処理中に問題が発生した場合はfalseを返す。<BR>
     * 処理結果のメッセージはgetMessage()メソッドで取得できる。<BR>
     * <BR>
     * @param startParam 登録内容
     * @return 正常終了：<CODE>true</CODE>
     *          異常終了：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean startSCH(CustomerMasterRegistSCHParams startParam)
            throws CommonException
    {
        // 出荷先マスタエンティティ
        Customer ent = new Customer();
        // 出荷先マスタデータベースハンドラ
        CustomerHandler cHandler = new CustomerHandler(getConnection());
        // 出荷先マスタ検索キー
        CustomerSearchKey key = new CustomerSearchKey();
        // 出荷先マスタコントローラー
        CustomerController aCtl = new CustomerController(getConnection(), this.getClass());

        // クラス名
        String cName = this.getClass().getSimpleName();

        try
        {
            // 日次更新・取込中チェック
            if (!canStart() || isLoadData())
            {
                return false;
            }

            // 重複チェック(DMCustomer内)
            // 荷主コード（システム定義）
            key.setConsignorCode(startParam.getString(CONSIGNOR_CODE));
            // 出荷先コード
            key.setCustomerCode(startParam.getString(CUSTOMER_CODE));

            // 既に存在する場合
            if (cHandler.count(key) > 0)
            {
                // (6023006)入力された出荷先コードは既に登録されています
                setMessage("6023006");

                // 異常の場合はfalseを返却
                return false;
            }

            // 登録データのセット
            // 荷主コード(システム定義)
            ent.setConsignorCode(startParam.getString(CONSIGNOR_CODE));
            // 出荷先コード
            ent.setCustomerCode(startParam.getString(CUSTOMER_CODE));
            // 出荷先名称
            ent.setCustomerName(startParam.getString(CUSTOMER_NAME));
            // ルート
            ent.setRoute(startParam.getString(ROUTE));
            // 郵便番号
            ent.setPostalCode(startParam.getString(POSTAL_CODE));
            // 都道府県名
            ent.setPrefecture(startParam.getString(PREFECTURE));
            // 住所
            ent.setAddress1(startParam.getString(ADDRESS1));
            // ビル名等
            ent.setAddress2(startParam.getString(ADDRESS2));
            // TEL
            ent.setTelephone(startParam.getString(TELEPHONE));
            // 連絡先１
            ent.setContact1(startParam.getString(CONTACT1));
            // 連絡先２
            ent.setContact2(startParam.getString(CONTACT2));
            // 仕分場所
            ent.setSortingPlace(startParam.getString(SORT_PLACE));
            // 登録処理名
            ent.setRegistPname(cName);
            // 最終更新処理名
            ent.setLastUpdatePname(cName);

            // 出荷先マスタ(新規登録)
            cHandler.create(ent);


            // 出荷先マスタ更新履歴(新規登録)
            aCtl.insertHistory(ent, SystemDefine.UPDATE_KIND_REGIST, getWmsUserInfo().getDfkUserInfo());

            // (6001003)登録しました。
            setMessage("6001003");

            // 正常の場合はtrueを返却
            return true;
        }
        // DBアクセスエラーが発生した場合
        catch (ReadWriteException e)
        {
            // (6007002)データベースエラーが発生しました。メッセージログを参照してください
            setMessage("6007002");

            // 異常の場合はfalseを返却
            return false;
        }
        // データ重複エラーが発生した場合
        catch (DataExistsException e)
        {
            // (6023006)入力された出荷先コードは既に登録されています
            setMessage("6023006");

            // 異常の場合はfalseを返却
            return false;
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
     * このクラスのバージョン情報を返します。
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }
}
//end of class
