// $Id: ItemMasterModifySCH.java 4533 2009-06-30 01:06:18Z okayama $
package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.master.schedule.ItemMasterModifySCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ItemFinder;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.db.SysDate;

/**
 * 商品マスタ修正・削除のスケジュール処理を行います。
 * 
 * @version $Revision: 4533 $, $Date: 2009-06-30 10:06:18 +0900 (火, 30 6 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class ItemMasterModifySCH
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
    public ItemMasterModifySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * ２画面遷移入力チェック<BR>
     * checkParamで指定された内容をもとに、出荷先マスタ存在チェックを行う。<BR>
     * 該当データが存在した場合はtrueを返す。<BR>
     * パラメータの内容に問題がある場合はfalseを返す。<BR>
     * <BR>
     * @param checkParam チェック条件
     * @return 正常：<CODE>true</CODE>
     *          異常：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean nextCheck(ItemMasterModifySCHParams checkParam)
            throws CommonException
    {
        // 商品マスタエンティティ
        Item item = new Item();
        // 商品マスタデータベースハンドラ
        ItemHandler iHandler = new ItemHandler(getConnection());
        // 商品マスタ検索キー
        ItemSearchKey ikey = new ItemSearchKey();

        //検索キーのセット
        // 商品コード
        ikey.setItemCode(checkParam.getString(ITEM_CODE));
        // 荷主コード
        ikey.setConsignorCode(checkParam.getString(CONSIGNOR_CODE));

        // 商品マスタの検索
        item = (Item)iHandler.findPrimary(ikey);
        if (item == null)
        {
            // (6003011)対象データはありませんでした。
            setMessage("6003011");

            // データが存在しない場合はfalseを返却
            return false;
        }

        // 商品マスタのシステム区分が1(システム)の場合
        if (SystemDefine.MANAGEMENT_TYPE_SYSTEM.equals(item.getManagementType()))
        {
            // (6023199)システム管理商品コードのため、修正・削除できません。
            setMessage("6023199");

            // システムに管理されている場合はfalseを返却
            return false;
        }
        //データが存在する場合はtrueを返却
        return true;
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
        // DBファインダー生成
        AbstractDBFinder finder = null;

        try
        {
            // 商品マスタファインダークラス生成
            finder = new ItemFinder(getConnection());
            finder.open(true);

            // 検索処理実行
            if (!canLowerDisplay(finder.search(createSearchKey(p))))
            {
                // 存在しなかった場合は空配列を返却
                return new ArrayList<Params>();
            }
            // 画面表示用にパラメータクラスにセットし返却
            return getDisplayData(finder);
        }
        finally
        {
            // 検索で使用したFinderをcloseする
            closeFinder(finder);
        }
    }

    /**
     * 修正・削除チェック<BR>
     * 対象の商品情報が他のテーブルに存在しないか確認します。<BR>
     * 画面側の更新・削除事前チェック処理で使用します。<BR>
     * <BR>
     * @param checkParam チェック内容
     * @return 商品情報存在：<CODE>true</CODE>
     *          商品情報なし：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean check(ItemMasterModifySCHParams checkParam)
            throws CommonException
    {
        // 対象データが使用されている場合
        if (!this.existRelation(checkParam))
        {
            // 処理フラグが修正の場合
            if (MasterInParameter.PROCESS_FLAG_MODIFY.equals(checkParam.getString(PROCESS_FLAG)))
            {
                // (MSG-W0010)修正を行うデータが使用されています。修正を行いますか？
                setDispMessage("MSG-W0010");
            }
            // 処理フラグが削除の場合
            else
            {
                // (6023003)削除を行うデータが使用されています。
                setMessage("6023003");
            }
            // 対象データが使用されている場合falseを返却
            return false;
        }
        // 対象データが使用されていない場合trueを返却
        return true;
    }

    /**
     * 商品マスタ修正･削除スケジュール開始処理<BR>
     * startParamsで指定するParameterクラスはItemMasterModifySCHParams型であること。<BR>
     * <BR>
     * @param startParams 開始条件
     * @return 正常：<CODE>true</CODE>
     *          異常：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean startSCH(ItemMasterModifySCHParams startParam)
            throws CommonException
    {
        // 商品マスタデータベースハンドラ
        ItemHandler iHandler = new ItemHandler(getConnection());
        // 商品マスタ検索キー
        ItemSearchKey skey = new ItemSearchKey();
        // 商品マスタ更新キー
        ItemAlterKey akey = new ItemAlterKey();
        // 商品マスタコントローラー
        ItemController iCtl = new ItemController(getConnection(), this.getClass());

        try
        {
            // 日次更新・取込中・排他チェック
            if (!canStart() || isLoadData() || !isNoModify(startParam))
            {
                // 異常の場合はfalseを返却
                return false;
            }

            // 検索キーのセット
            // 検索キーのクリア
            skey.clear();
            // 荷主コード
            skey.setKey(Item.CONSIGNOR_CODE, startParam.getString(CONSIGNOR_CODE));
            // 商品コード
            skey.setKey(Item.ITEM_CODE, startParam.getString(ITEM_CODE));

            // 上記検索キーより一件のみ取得
            Item oldMaster = (Item)iHandler.findPrimary(skey);

            // 修正処理
            if (MasterInParameter.PROCESS_FLAG_MODIFY.equals(startParam.getString(PROCESS_FLAG)))
            {
                // 入力妥当性チェック
                if (startParam.getInt(UPPER_STOCK_QTY) < startParam.getInt(LOWER_STOCK_QTY))
                {
                    // (6023025)下限在庫数が上限在庫数を上回っています。
                    setMessage("6023025");

                    // 異常の場合はfalseを返却
                    return false;
                }

                // 更新条件のセット
                // 荷主コード
                akey.setKey(Item.CONSIGNOR_CODE, startParam.getString(CONSIGNOR_CODE));
                // 商品コード
                akey.setKey(Item.ITEM_CODE, startParam.getString(ITEM_CODE));

                // 更新キーのセット
                // 商品名称
                akey.updateItemName(startParam.getString(ITEM_NAME));
                // ソフトゾーンID
                if (!StringUtil.isBlank(startParam.getString(SOFT_ZONE_ID)))
                {
                    akey.updateSoftZoneId(startParam.getString(SOFT_ZONE_ID));
                }
                else
                {
                    akey.updateSoftZoneId("0");
                }
                // ケース入数
                akey.updateEnteringQty(startParam.getInt(CASE_ENTERING_QTY));
                // JANコード
                akey.updateJan(startParam.getString(JAN_CODE));
                // ケースITF
                akey.updateItf(startParam.getString(CASE_ITF));
                // 上限在庫数
                akey.updateUpperQty(startParam.getInt(UPPER_STOCK_QTY));
                // 下限在庫数
                akey.updateLowerQty(startParam.getInt(LOWER_STOCK_QTY));
                // 最終更新処理名
                akey.updateLastUpdatePname(this.getClass().getSimpleName());
                // 最終使用日時
                akey.updateLastUsedDate(new SysDate());

                // 商品マスタ(更新)
                iHandler.modify(akey);


                // 更新後の商品マスタ取得
                // 検索キーのクリア
                skey.clear();
                // 荷主コード
                skey.setKey(Item.CONSIGNOR_CODE, startParam.getString(CONSIGNOR_CODE));
                // 商品コード
                skey.setKey(Item.ITEM_CODE, startParam.getString(ITEM_CODE));

                // 上記検索キーより一件のみ取得
                Item newMaster = (Item)iHandler.findPrimary(skey);

                // 商品マスタ更新履歴情報(新規登録)
                iCtl.insertHistory(oldMaster, newMaster, SystemDefine.UPDATE_KIND_MODIFY,
                        getWmsUserInfo().getDfkUserInfo());

                // (6001004)修正しました。
                setMessage("6001004");
            }
            // 削除処理
            else
            {
                // 検索キーのクリア
                skey.clear();

                // 削除条件のセット
                // 商品コード
                skey.setItemCode(startParam.getString(ITEM_CODE));
                // 荷主コード
                skey.setConsignorCode(startParam.getString(CONSIGNOR_CODE));
                // システム管理区分
                skey.setManagementType(Item.MANAGEMENT_TYPE_USER);

                // 削除データ検索
                if (iHandler.count(skey) == 0)
                {
                    // (6003014)削除対象データがありませんでした。
                    setMessage("6003014");

                    // 異常の場合はfalseを返却
                    return false;
                }

                // 商品マスタ(削除)
                iHandler.drop(skey);

                // 商品マスタ更新履歴情報(新規登録)
                iCtl.insertHistory(oldMaster, SystemDefine.UPDATE_KIND_DELETE, getWmsUserInfo().getDfkUserInfo());

                // (6001005)削除しました。
                setMessage("6001005");
            }
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
        // データが存在しなかった場合
        catch (NotFoundException e)
        {
            // (6023004)他端末で変更が行われました。前画面に戻り再度検索を行ってください
            setMessage("6023004");

            // 異常の場合はfalseを返却
            return false;
        }
        // データがロックされていた場合
        catch (LockTimeOutException e)
        {
            // (6023004)他端末で変更が行われました。前画面に戻り再度検索を行ってください
            setMessage("6023004");

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
    /**
     * 検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return ItemSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        // 商品マスタ検索キー
        ItemSearchKey ikey = new ItemSearchKey();

        //検索キーのセット
        // 荷主コード
        ikey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 商品コード
        ikey.setItemCode(p.getString(ITEM_CODE));

        // 取得キーのセット
        // 商品コード
        ikey.setCollect(Item.ITEM_CODE);
        // 商品名称
        ikey.setCollect(Item.ITEM_NAME);
        // ソフトゾーンID
        ikey.setCollect(Item.SOFT_ZONE_ID);
        // ケース入数
        ikey.setCollect(Item.ENTERING_QTY);
        // JANコード
        ikey.setCollect(Item.JAN);
        // ケースITF
        ikey.setCollect(Item.ITF);
        // 上限在庫数
        ikey.setCollect(Item.UPPER_QTY);
        // 下限在庫数
        ikey.setCollect(Item.LOWER_QTY);
        // 最終更新日時
        ikey.setCollect(Item.LAST_UPDATE_DATE);
        // 最終使用日
        ikey.setCollect(Item.LAST_USED_DATE);

        // セットした検索キーを返却
        return ikey;
    }

    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder)
            throws ReadWriteException
    {
        // 最大表示件数分検索結果を取得する
        Item[] entities = (Item[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        // 返却用の配列を生成
        List<Params> result = new ArrayList<Params>();

        // 取得データ件数分、繰り返す
        for (Item ent : entities)
        {
            // パラメータクラス生成
            Params param = new Params();

            // 返却データをセット
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            param.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME, ""));
            // ソフトゾーンID
            param.set(SOFT_ZONE_ID, ent.getValue(Item.SOFT_ZONE_ID, ""));
            // ケース入数
            param.set(CASE_ENTERING_QTY, ent.getEnteringQty());
            // JANコード
            param.set(JAN_CODE, ent.getValue(Item.JAN, ""));
            // ケースITF
            param.set(CASE_ITF, ent.getValue(Item.ITF, ""));
            // 上限在庫数
            param.set(UPPER_STOCK_QTY, ent.getUpperQty());
            // 下限在庫数
            param.set(LOWER_STOCK_QTY, ent.getLowerQty());
            // 最新更新日時
            param.set(LAST_UPDATE_DATE, ent.getLastUpdateDate());
            // 最新使用日
            param.set(LAST_USED_DATE, ent.getLastUsedDate());

            // セットした返却データを配列に格納
            result.add(param);
        }
        // 設定されたリストを返却
        return result;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 商品マスタロック処理<BR>
     * 対象の商品情報が他の端末で更新されていないか確認します。<BR>
     * <BR>
     * @param checkParam チェック条件
     * @return 更新されていない：<CODE>true</CODE>
     *          更新されている：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean isNoModify(ItemMasterModifySCHParams checkParam)
            throws CommonException
    {
        // 商品マスタデータベースハンドラ
        ItemHandler iHandler = new ItemHandler(getConnection());
        // 商品マスタ検索キー
        ItemSearchKey ikey = new ItemSearchKey();

        // 検索キーのセット
        // 商品コード
        ikey.setItemCode(checkParam.getString(ITEM_CODE));
        // 荷主コード
        ikey.setConsignorCode(checkParam.getString(CONSIGNOR_CODE));
        // 最終更新日
        ikey.setKey(Item.LAST_UPDATE_DATE, checkParam.getDate(LAST_UPDATE_DATE));

        // 対象データロック処理
        if (iHandler.findPrimaryForUpdate(ikey, ItemHandler.WAIT_SEC_NOWAIT) != null)
        {
            // 更新されていなかった場合はtrueを返却
            return true;
        }

        // (6023004)他端末で変更が行われました。前画面に戻り再度検索を行ってください。
        setMessage("6023004");

        // 更新されていた場合はfalseを返却
        return false;
    }

    /**
     * 商品マスタ関連テーブルチェック<BR>
     * checkParam内の条件に合致する商品情報が他のテーブルに存在しないか確認します。<BR>
     * 他のテーブルに商品情報が存在する場合はtrueを、<BR>
     * 見つからない場合はfalseを返します。<BR>
     * 画面側の更新・削除事前チェック処理で使用します。<BR>
     * <BR>
     * @param checkParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existRelation(ItemMasterModifySCHParams checkParam)
            throws CommonException
    {
        // システム定義情報コントローラークラス
        WarenaviSystemController sysController = new WarenaviSystemController(getConnection(), getClass());

        // マスタパッケージが導入されている場合
        if (sysController.hasMasterPack())
        {
            // 商品固定棚情報データ存在チェック
            if (!existFixedLocateInfo(checkParam))
            {
                // 対象データが使用されていた場合falseを返却
                return false;
            }
        }

        // 入荷パッケージが導入されている場合
        if (sysController.hasReceivingPack())
        {
            // 入荷予定情報データ存在チェック
            if (!existReceivingPlan(checkParam))
            {
                // 対象データが使用されていた場合falseを返却
                return false;
            }
        }

        // 入庫パッケージが導入されている場合
        if (sysController.hasStoragePack())
        {
            // 入庫予定情報データ存在チェック
            if (!existStoragePlan(checkParam))
            {
                // 対象データが使用されていた場合falseを返却
                return false;
            }

            // 棚卸作業情報データ存在チェック
            if (!existInventoryWorkInfo(checkParam))
            {
                // 対象データが使用されていた場合falseを返却
                return false;
            }
        }

        // 出庫パッケージが導入されている場合
        if (sysController.hasRetrievalPack())
        {
            // 出庫予定情報データ存在チェック
            if (!existRetrievalPlan(checkParam))
            {
                // 対象データが使用されていた場合falseを返却
                return false;
            }
        }

        // AS/RSパッケージが導入されている場合
        if (sysController.hasAsrsPack())
        {
            // 入出庫作業情報データ存在チェック
            if (!existWorkInfo(checkParam))
            {
                // 対象データが使用されていた場合falseを返却
                return false;
            }
        }

        // クロスドックパッケージ、または出荷パッケージが導入されていた場合
        if (sysController.hasCrossdockPack() || sysController.hasShippingPack())
        {
            // 出荷予定情報データ存在チェック
            if (!existShipPlan(checkParam))
            {
                // 対象データが使用されていた場合falseを返却
                return false;
            }
        }

        // 在庫パッケージが導入されていた場合
        if (sysController.hasStockPack())
        {
            // 在庫情報データ存在チェック
            if (!existStock(checkParam))
            {
                // 対象データが使用されていた場合falseを返却
                return false;
            }

            // 移動作業情報データ存在チェック
            if (!existMoveWorkInfo(checkParam))
            {
                // 対象データが使用されていた場合falseを返却
                return false;
            }
        }
        // 問題ない場合はtrueを返却
        return true;
    }

    /**
     * 商品固定棚情報データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existFixedLocateInfo(ItemMasterModifySCHParams inParam)
            throws CommonException
    {
        // 商品固定棚情報データベースハンドラ
        FixedLocateInfoHandler fixedLocateInfoHandler = new FixedLocateInfoHandler(getConnection());
        // 商品肯定棚情報検索キー
        FixedLocateInfoSearchKey fixedLocateInfoKey = new FixedLocateInfoSearchKey();

        // 検索キーのセット
        // 荷主コード
        fixedLocateInfoKey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        // 商品コード
        fixedLocateInfoKey.setItemCode(inParam.getString(ITEM_CODE));

        // 検索処理
        if (fixedLocateInfoHandler.count(fixedLocateInfoKey) > 0)
        {
            // データが存在する場合はfalseを返却
            return false;
        }
        // データが存在しない場合はtrueを返却
        return true;
    }

    /**
     * 入荷予定情報データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existReceivingPlan(ItemMasterModifySCHParams inParam)
            throws CommonException
    {
        // 入荷予定情報データベースハンドラ
        ReceivingPlanHandler receivePlanHandler = new ReceivingPlanHandler(getConnection());
        // 入荷予定情報検索キー
        ReceivingPlanSearchKey receivePlanKey = new ReceivingPlanSearchKey();

        // 検索キーのセット
        // 荷主コード
        receivePlanKey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        // 商品コード
        receivePlanKey.setItemCode(inParam.getString(ITEM_CODE));
        // 状態フラグ(削除ではない)
        receivePlanKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        // 検索処理
        if (receivePlanHandler.count(receivePlanKey) > 0)
        {
            // データが存在する場合はfalseを返却
            return false;
        }
        // データが存在しない場合はtrueを返却
        return true;
    }

    /**
     * 入庫予定情報データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existStoragePlan(ItemMasterModifySCHParams inParam)
            throws CommonException
    {
        // 入庫予定情報データベースハンドラ
        StoragePlanHandler storagePlanHandler = new StoragePlanHandler(getConnection());
        // 入庫予定情報検索キー
        StoragePlanSearchKey storagePlanKey = new StoragePlanSearchKey();

        // 検索キーのセット
        // 荷主コード
        storagePlanKey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        // 商品コード
        storagePlanKey.setItemCode(inParam.getString(ITEM_CODE));
        // 状態フラグ(削除ではない)
        storagePlanKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        // 検索処理
        if (storagePlanHandler.count(storagePlanKey) > 0)
        {
            // データが存在する場合はfalseを返却
            return false;
        }
        // データが存在しない場合はtrueを返却
        return true;
    }

    /**
     * 出庫予定情報データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existRetrievalPlan(ItemMasterModifySCHParams inParam)
            throws CommonException
    {
        // 出庫予定情報データベースハンドラ
        RetrievalPlanHandler retrievalPlanHandler = new RetrievalPlanHandler(getConnection());
        // 出庫予定情報検索キー
        RetrievalPlanSearchKey retrievalPlanKey = new RetrievalPlanSearchKey();

        // 検索キーのセット
        // 荷主コード
        retrievalPlanKey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        // 商品コード
        retrievalPlanKey.setItemCode(inParam.getString(ITEM_CODE));
        // 状態フラグ(削除ではない)
        retrievalPlanKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        // 検索処理
        if (retrievalPlanHandler.count(retrievalPlanKey) > 0)
        {
            // データが存在する場合はfalseを返却
            return false;
        }
        // データが存在しない場合はtrueを返却
        return true;
    }

    /**
     * 出荷予定情報データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existShipPlan(ItemMasterModifySCHParams inParam)
            throws CommonException
    {
        // 出荷予定情報データベースハンドラ
        ShipPlanHandler shipPlanHandler = new ShipPlanHandler(getConnection());
        // 出荷予定情報検索キー
        ShipPlanSearchKey shipPlanKey = new ShipPlanSearchKey();

        // 検索キーのセット
        // 荷主コード
        shipPlanKey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        // 商品コード
        shipPlanKey.setItemCode(inParam.getString(ITEM_CODE));
        // 出荷検品状態フラグ(削除ではない)
        shipPlanKey.setWorkStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=", "(", "", false);
        // バース状態フラグ(削除ではない)
        shipPlanKey.setBerthStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=", "", ")", true);

        // 検索処理
        if (shipPlanHandler.count(shipPlanKey) > 0)
        {
            // データが存在する場合はfalseを返却
            return false;
        }
        // データが存在しない場合はtrueを返却
        return true;
    }

    /**
     * 入出庫作業情報データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existWorkInfo(ItemMasterModifySCHParams inParam)
            throws CommonException
    {
        // 入出庫作業情報データベースハンドラ
        WorkInfoHandler workInfoHandler = new WorkInfoHandler(getConnection());
        // 入出庫作業情報検索キー
        WorkInfoSearchKey workInfoSearchKey = new WorkInfoSearchKey();

        // 検索キーのセット
        // 荷主コード
        workInfoSearchKey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        // 商品コード
        workInfoSearchKey.setItemCode(inParam.getString(ITEM_CODE));
        // 状態フラグ(削除ではない)
        workInfoSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        // 検索処理
        if (workInfoHandler.count(workInfoSearchKey) > 0)
        {
            // データが存在する場合はfalseを返却
            return false;
        }
        // データが存在しない場合はtrueを返却
        return true;
    }

    /**
     * 在庫情報データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existStock(ItemMasterModifySCHParams inParam)
            throws CommonException
    {
        // 在庫情報データベースハンドラ
        StockHandler stockHandler = new StockHandler(getConnection());
        // 在庫情報検索キー
        StockSearchKey stockSearchKey = new StockSearchKey();

        // 検索キーのセット
        // 荷主コード
        stockSearchKey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        // 商品コード
        stockSearchKey.setItemCode(inParam.getString(ITEM_CODE));

        // 検索処理
        if (stockHandler.count(stockSearchKey) > 0)
        {
            // データが存在する場合はfalseを返却
            return false;
        }
        // データが存在しない場合はtrueを返却
        return true;
    }

    /**
     * 移動作業情報データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existMoveWorkInfo(ItemMasterModifySCHParams inParam)
            throws CommonException
    {
        // 移動作業情報データベースハンドラ
        MoveWorkInfoHandler moveWorkHandler = new MoveWorkInfoHandler(getConnection());
        // 移動作業情報検索キー
        MoveWorkInfoSearchKey moveWorkInfoSearchKey = new MoveWorkInfoSearchKey();

        // 検索キーのセット
        // 荷主コード
        moveWorkInfoSearchKey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        // 商品コード
        moveWorkInfoSearchKey.setItemCode(inParam.getString(ITEM_CODE));
        // 状態フラグ(削除ではない)
        moveWorkInfoSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        // 検索処理
        if (moveWorkHandler.count(moveWorkInfoSearchKey) > 0)
        {
            // データが存在する場合はfalseを返却
            return false;
        }
        // データが存在しない場合はtrueを返却
        return true;
    }

    /**
     * 棚卸情報データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existInventoryWorkInfo(ItemMasterModifySCHParams inParam)
            throws CommonException
    {
        // 棚卸情報データベースハンドラ
        InventWorkInfoHandler inventWorkHandler = new InventWorkInfoHandler(getConnection());
        // 棚卸情報検索キー
        InventWorkInfoSearchKey inventWorkInfoSearchKey = new InventWorkInfoSearchKey();

        // 検索キーのセット
        // 荷主コード
        inventWorkInfoSearchKey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        // 商品コード
        inventWorkInfoSearchKey.setItemCode(inParam.getString(ITEM_CODE));
        // 状態フラグ(削除ではない)
        inventWorkInfoSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        // 検索処理
        if (inventWorkHandler.count(inventWorkInfoSearchKey) > 0)
        {
            // データが存在する場合はfalseを返却
            return false;
        }
        // データが存在しない場合はtrueを返却
        return true;
    }

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
