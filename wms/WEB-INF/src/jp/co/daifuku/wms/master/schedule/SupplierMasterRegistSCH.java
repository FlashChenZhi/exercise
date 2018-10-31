// $Id: SupplierMasterRegistSCH.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.master.schedule.SupplierMasterRegistSCHParams.*;

import java.sql.Connection;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.controller.SupplierController;
import jp.co.daifuku.wms.base.dbhandler.SupplierHandler;
import jp.co.daifuku.wms.base.dbhandler.SupplierSearchKey;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**
 * 仕入先マスタ登録のスケジュール処理を行います。
 * 
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class SupplierMasterRegistSCH
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
    public SupplierMasterRegistSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 仕入先マスタ登録処理<BR>
     * startParamで指定された内容をもとに、仕入先マスタの登録処理を行う。<BR>
     * 正常に登録出来た場合はtrueを、処理中に問題が発生した場合はfalseを返す。<BR>
     * 処理結果のメッセージはgetMessage()メソッドで取得できる。<BR>
     * <BR>
     * @param startParam 画面入力内容
     * @return 正常終了：<CODE>true</CODE>
     *          異常終了：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean startSCH(SupplierMasterRegistSCHParams startParam)
            throws CommonException
    {
        // 仕入先マスタエンティティ
        Supplier ent = new Supplier();
        // 仕入先マスタデータベースハンドラ
        SupplierHandler sHandler = new SupplierHandler(getConnection());
        // 仕入先マスタ検索キー
        SupplierSearchKey key = new SupplierSearchKey();
        // 仕入先マスタコントローラー
        SupplierController sCtl = new SupplierController(getConnection(), this.getClass());

        // クラス名
        String cName = this.getClass().getSimpleName();

        try
        {
            // 日次更新・取込中チェック
            if (!canStart() || isLoadData())
            {
                return false;
            }

            // 重複チェック(DMSupplier内)
            // 荷主コード（システム定義）
            key.setConsignorCode(startParam.getString(CONSIGNOR_CODE));
            // 仕入先コード
            key.setSupplierCode(startParam.getString(SUPPLIER_CODE));

            // 既に存在する場合
            if (sHandler.count(key) > 0)
            {
                // (6023005)入力された仕入先コードは既に登録されています
                setMessage("6023005");

                // 異常の場合はfalseを返却
                return false;
            }

            // 登録データのセット
            // 荷主コード(システム定義)
            ent.setConsignorCode(startParam.getString(CONSIGNOR_CODE));
            // 仕入先コード
            ent.setSupplierCode(startParam.getString(SUPPLIER_CODE));
            // 仕入先名称
            ent.setSupplierName(startParam.getString(SUPPLIER_NAME));
            // 登録処理名
            ent.setRegistPname(cName);
            // 最終更新処理名
            ent.setLastUpdatePname(cName);

            // 仕入先マスタ(新規登録)
            sHandler.create(ent);


            // 仕入先マスタ更新履歴(新規登録)
            sCtl.insertHistory(ent, SystemDefine.UPDATE_KIND_REGIST, getWmsUserInfo().getDfkUserInfo());

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
            // (6023005)入力された仕入先コードは既に登録されています
            setMessage("6023005");

            // 異常が発生した場合はfalseを返却
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
