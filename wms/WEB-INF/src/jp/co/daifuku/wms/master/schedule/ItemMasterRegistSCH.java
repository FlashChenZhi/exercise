// $Id: ItemMasterRegistSCH.java 5282 2009-10-27 10:49:25Z fukuwa $
package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.master.schedule.ItemMasterRegistSCHParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**
 * 商品マスタ登録のスケジュール処理を行います。
 * 
 * @version $Revision: 5282 $, $Date: 2009-10-27 19:49:25 +0900 (火, 27 10 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: fukuwa $
 */
public class ItemMasterRegistSCH
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
    public ItemMasterRegistSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 商品マスタ登録<BR>
     * startParamで指定された内容をもとに、商品マスタの登録処理を行う。<BR>
     * 正常に登録出来た場合はtrueを、処理中に問題が発生した場合はfalseを返す。<BR>
     * 処理結果のメッセージはgetMessage()メソッドで取得できる。<BR>
     * <BR>
     * @param startParam 登録内容
     * @return 正常：<CODE>true</CODE>
     *          異常：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean startSCH(ItemMasterRegistSCHParams startParam)
            throws CommonException
    {
        // 商品マスタエンティティ
        Item itemEntity = new Item();
        // 商品マスタデータベースハンドラ
        ItemHandler iHandler = new ItemHandler(getConnection());
        // 商品マスタ検索キー
        ItemSearchKey ikey = new ItemSearchKey();
        // 商品マスタコントローラー
        ItemController aCtl = new ItemController(getConnection(), this.getClass());

        // クラス名
        String cName = this.getClass().getSimpleName();

        try
        {
            // 条件チェックを行う
            // 日次更新チェック
            if (!canStart() || isLoadData())
            {
                return false;
            }

            // 入力妥当性チェック
            if (startParam.getInt(UPPER_STOCK_QTY) < startParam.getInt(LOWER_STOCK_QTY))
            {
                // (6023025)下限在庫数が上限在庫数を上回っています。
                setMessage("6023025");

                // 異常の場合はfalseを返却
                return false;
            }

            // 重複チェック(DMItem内)
            // 荷主コード（システム定義）
            ikey.setConsignorCode(startParam.getString(CONSIGNOR_CODE));
            // 商品コード
            ikey.setItemCode(startParam.getString(ITEM_CODE));

            // 既に存在する場合
            if (iHandler.count(ikey) > 0)
            {
                // (6023007)入力された商品コードは既に登録されています
                setMessage("6023007");

                // 異常の場合はfalseを返却
                return false;
            }

            // 登録データのセット
            // システム管理区分（通常）
            itemEntity.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
            // 商品コード
            itemEntity.setItemCode(startParam.getString(ITEM_CODE));
            // 商品名称
            itemEntity.setItemName(startParam.getString(ITEM_NAME));
            // 荷主コード(システム定義)
            itemEntity.setConsignorCode(startParam.getString(CONSIGNOR_CODE));
            // ソフトゾーンID
            if (!StringUtil.isBlank(startParam.getString(SOFT_ZONE_ID)))
            {
                itemEntity.setSoftZoneId(startParam.getString(SOFT_ZONE_ID));
            }
            // JANコード
            itemEntity.setJan(startParam.getString(JAN_CODE));
            // ケースITF
            itemEntity.setItf(startParam.getString(CASE_ITF));
            // ボールITF
            itemEntity.setBundleItf(null);
            // ケース入数
            itemEntity.setEnteringQty(startParam.getInt(ENTERING_QTY));
            // ボール入数
            itemEntity.setBundleEnteringQty(0);
            // 上限在庫数
            itemEntity.setUpperQty(startParam.getInt(UPPER_STOCK_QTY));
            // 下限在庫数
            itemEntity.setLowerQty(startParam.getInt(LOWER_STOCK_QTY));
            // 登録処理名
            itemEntity.setRegistPname(cName);
            // 最終更新処理名
            itemEntity.setLastUpdatePname(cName);

            // 商品マスタ(新規登録)         
            iHandler.create(itemEntity);


            // 商品マスタ更新履歴情報
            aCtl.insertHistory(itemEntity, SystemDefine.UPDATE_KIND_REGIST, getWmsUserInfo().getDfkUserInfo());

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
            // (6023007)入力された商品コードは既に登録されています
            setMessage("6023007");

            // 異常の場合はfalseを返却
            return false;
        }
    }

    /**
     * AS/RSパッケージ導入フラグを取得します。<BR>
     * <BR>
     * @param p 検索条件をもつ<CODE>ItemMasterRegistSCHParams</CODE>クラスを継承したクラス
     * @return 検索結果が含まれた<CODE>ItemMasterRegistSCHParams</CODE>クラスを実装したインスタンス
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        // システム定義情報コントローラー
        WarenaviSystemController systemController = new WarenaviSystemController(getConnection(), this.getClass());

        // AS/RSパッケージ導入フラグを取得
        Params out = new Params();
        out.set(HAS_ASRS, systemController.hasAsrsPack());

        // 取得したフラグを返却
        return out;
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
