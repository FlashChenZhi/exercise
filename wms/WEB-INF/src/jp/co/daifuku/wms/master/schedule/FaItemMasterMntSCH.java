// $Id: FaItemMasterMntSCH.java 7358 2010-03-04 10:46:04Z kishimoto $
package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.master.schedule.FaItemMasterMntSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.master.schedule.FaItemMasterMntSCHParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.db.AbstractDBHandler;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.SearchKey;

/**
 * 商品マスタメンテナンスのSCHクラスです。
 *
 * @version $Revision: 7358 $, $Date:: 2010-03-04 19:46:04 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class FaItemMasterMntSCH
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
    public FaItemMasterMntSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * 商品マスタメンテナンス関連テーブルチェック<BR>
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
    public boolean check(ScheduleParams checkParam)
            throws CommonException
    {
        // 対象商品がシステム管理区分であるかのチェックを行います。
        if (managementTypeCheck(checkParam))
        {
            // システム管理商品コードのため、修正・削除できません。
            setMessage("6023199");
            return true;
        }
        // 各データ存在チェック
        if (!existRelation(checkParam))
        {
            if (MasterInParameter.PROCESS_FLAG_MODIFY.equals(checkParam.getString(VS_PROCESS_KEY)))
            {
                // MSG-W0010=修正を行うデータが使用されています。修正を行いますか？
                setDispMessage("MSG-W0010");
            }
            if (MasterInParameter.PROCESS_FLAG_DELETE.equals(checkParam.getString(VS_PROCESS_KEY)))
            {
                // 6023003=削除を行うデータが使用されています。
                setMessage("6023003");
            }
            // 対象データが使用されていた場合falseを返却
            return true;
        }
        return false;
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

        AbstractDBHandler handler = new ItemHandler(getConnection());
        Item[] itemChecks = (Item[])handler.find(createSearchKey(p));
        // 検索処理実行
        // 取得件数に応じてメッセージを設定
        if (itemChecks.length == 0)
        {
            // 修正ボタンor削除ボタン押下時に、メッセージのセットを行います。
            if (MasterInParameter.PROCESS_FLAG_MODIFY.equals(p.getString(VS_PROCESS_KEY))
                    || MasterInParameter.PROCESS_FLAG_DELETE.equals(p.getString(VS_PROCESS_KEY)))
            {
                // 商品コードがマスタに登録されていません。
                setMessage("6023021");
            }
            // 存在しなかった場合は空配列を返却
            return new ArrayList<Params>();
        }
        // 登録ボタンを押下時に、メッセージのセットを行います。
        if (MasterInParameter.PROCESS_FLAG_REGIST.equals(p.getString(VS_PROCESS_KEY)))
        {
            // 既に同一データが存在するため、入力できません。
            setMessage("6023020");
        }

        // エンティティを画面表示用にパラメータクラスにセットし返す
        return getDisplayData(itemChecks, p);

    }


    /**
     * 商品マスタメンテナンス登録・修正･削除スケジュール開始処理<BR>
     * startParamで指定された内容をもとに、商品マスタの登録・修正･削除処理を行う。<BR>
     * 正常に登録・修正･削除出来た場合はtrueを、処理中に問題が発生した場合はfalseを返す。<BR>
     * 処理結果のメッセージはgetMessage()メソッドで取得できる。<BR>
     * <BR>
     * @param startParam 登録内容
     * @return 正常：<CODE>true</CODE>
     *          異常：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean startSCH(FaItemMasterMntSCHParams startParam)
            throws CommonException
    {
        // 商品マスタエンティティ
        Item itemEntity = new Item();
        // 商品マスタデータベースハンドラ
        ItemHandler iHandler = new ItemHandler(getConnection());
        // 商品マスタコントローラー
        ItemController aCtl = new ItemController(getConnection(), this.getClass());

        // クラス名
        String cName = this.getClass().getSimpleName();

        try
        {
            // 条件チェックを行う
            // 日次更新・取込中チェック
            if (!canStart() || isLoadData())
            {
                return false;
            }


            // データ登録の場合
            if (MasterInParameter.PROCESS_FLAG_REGIST.equals(startParam.getString(VS_PROCESS_KEY)))
            {
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
                if (!StringUtil.isBlank(startParam.getString(SOFT_ZONE)))
                {
                    itemEntity.setSoftZoneId(startParam.getString(SOFT_ZONE));
                }
                // 一時商品区分
                itemEntity.setTemporaryType(startParam.getString(TEMPORARY_TYPE));
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
            }
            // データ修正の場合
            else if (MasterInParameter.PROCESS_FLAG_MODIFY.equals(startParam.getString(VS_PROCESS_KEY)))
            {
                // 商品マスタ検索キー
                ItemSearchKey skey = new ItemSearchKey();

                // 荷主コード
                skey.setConsignorCode(startParam.getString(CONSIGNOR_CODE));
                // 商品コード
                skey.setItemCode(startParam.getString(ITEM_CODE));
                // 上記検索キーより一件のみ取得
                Item oldMaster = (Item)iHandler.findPrimary(skey);

                // 商品マスタ更新キー
                ItemAlterKey akey = new ItemAlterKey();

                // 更新条件のセット
                // 荷主コード
                akey.setKey(Item.CONSIGNOR_CODE, startParam.getString(CONSIGNOR_CODE));
                akey.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
                // 商品コード
                akey.setItemCode(startParam.getString(ITEM_CODE));

                // 更新キーのセット
                // 商品名称
                akey.updateItemName(startParam.getString(ITEM_NAME));
                // ソフトゾーンID
                if (!StringUtil.isBlank(startParam.getString(SOFT_ZONE)))
                {
                    akey.updateSoftZoneId(startParam.getString(SOFT_ZONE));
                }
                // 一時商品区分
                akey.updateTemporaryType(startParam.getString(TEMPORARY_TYPE));

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
                aCtl.insertHistory(oldMaster, newMaster, SystemDefine.UPDATE_KIND_MODIFY,
                        getWmsUserInfo().getDfkUserInfo());

                // (6001004)修正しました。
                setMessage("6001004");

            }
            // データ削除の場合
            else
            {
                // 削除データのセット
                // システム管理区分（通常）
                itemEntity.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
                // 荷主コード(システム定義)
                itemEntity.setConsignorCode(startParam.getString(CONSIGNOR_CODE));
                // 商品コード
                itemEntity.setItemCode(startParam.getString(ITEM_CODE));


                // 商品マスタ(削除)         
                iHandler.drop(itemEntity);

                // 商品マスタ更新履歴情報
                aCtl.insertHistory(itemEntity, SystemDefine.UPDATE_KIND_DELETE, getWmsUserInfo().getDfkUserInfo());

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
        // データ重複エラーが発生した場合
        catch (DataExistsException e)
        {
            // (6023007)入力された商品コードは既に登録されています
            setMessage("6023007");

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
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        ItemSearchKey searchKey = new ItemSearchKey();

        searchKey.setItemCode(p.getString(ITEM_CODE));
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));

        return searchKey;
    }

    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(Item[] itemChecks, ScheduleParams p)
            throws ReadWriteException
    {
        // 最大表示件数分検索結果を取得する
        List<Params> result = new ArrayList<Params>();

        for (Item ent : itemChecks)
        {
            Params param = new Params();

            // 荷主コード
            param.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            param.set(ITEM_NAME, ent.getItemName());
            // ソフトゾーン名称
            param.set(SOFT_ZONE, ent.getSoftZoneId());
            // 一時商品区分
            param.set(TEMPORARY_TYPE, ent.getTemporaryType());
            result.add(param);
        }

        return result;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    /**
     * システム管理区分の商品であるかのチェック
     * 
     * @param inParam チェック条件
     * @return 対象商品がシステム管理区分の場合 (処理の続行が不可能な場合):<CODE>true</CODE>
     *          対象商品がシステム管理区分でない場合（処理の続行が可能な場合）：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean managementTypeCheck(ScheduleParams inParam)
            throws CommonException
    {
        AbstractDBHandler handler = new ItemHandler(getConnection());
        Item[] itemChecks = (Item[])handler.find(createSearchKey(inParam));

        for (Item ent : itemChecks)
        {
            // システム管理区分の商品は、処理を行なわないようにします。
            if (SystemDefine.MANAGEMENT_TYPE_SYSTEM.equals(ent.getManagementType()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 各データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existRelation(Params inParam)
            throws CommonException
    {
        // 出庫予定情報データ存在チェック
        if (retrievalPlanDoesExist(inParam))
        {
            return false;
        }
        // 再入庫データ存在チェック
        if (restoringDoesExist(inParam))
        {
            return false;
        }
        // 商品固定棚情報データ存在チェック
        if (locationInfoDoesExist(inParam))
        {
            return false;
        }
        // 在庫情報データ存在チェック
        if (stockDoesExist(inParam))
        {
            if (!WmsParam.MASTER_MODIFY_FLAG)
            {
                return false;    
            }
            
        }
        return true;
    }

    /**
     * 商品固定棚情報データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>false</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>true</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean locationInfoDoesExist(Params inParam)
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
            // データが存在する場合はtrueを返却
            return true;
        }
        // データが存在しない場合はfalseを返却
        return false;
    }

    /**
     * 出庫予定情報データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>false</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>true</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean retrievalPlanDoesExist(Params inParam)
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
            // データが存在する場合はtrueを返却
            return true;
        }
        // データが存在しない場合はfalseを返却
        return false;
    }

    /**
     * 再入庫データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>false</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>true</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean restoringDoesExist(Params inParam)
            throws CommonException
    {
        // 再入庫予定情報データベースハンドラ
        ReStoringPlanHandler restoringHandler = new ReStoringPlanHandler(getConnection());
        // 再入庫予定情報検索キー
        ReStoringPlanSearchKey restoringKey = new ReStoringPlanSearchKey();

        // 検索キーのセット
        // 荷主コード
        restoringKey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        // 商品コード
        restoringKey.setItemCode(inParam.getString(ITEM_CODE));
        // 状態フラグ(削除ではない)
        restoringKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        // 検索処理
        if (restoringHandler.count(restoringKey) > 0)
        {
            // データが存在する場合はtrueを返却
            return true;
        }
        // データが存在しない場合はfalseを返却
        return false;
    }

    /**
     * 在庫情報データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean stockDoesExist(Params inParam)
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
            // データが存在する場合はtrueを返却
            return true;
        }
        // データが存在しない場合はfalseを返却
        return false;
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
