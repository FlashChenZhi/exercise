// $Id: SupplierMasterModifySCH.java 4730 2009-07-22 07:13:50Z shibamoto $
package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.master.schedule.CustomerMasterModifySCHParams.PROCESS_FLAG;
import static jp.co.daifuku.wms.master.schedule.SupplierMasterModifySCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.foundation.common.Params;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.SupplierController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SupplierAlterKey;
import jp.co.daifuku.wms.base.dbhandler.SupplierFinder;
import jp.co.daifuku.wms.base.dbhandler.SupplierHandler;
import jp.co.daifuku.wms.base.dbhandler.SupplierSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.SearchKey;

/**
 * 仕入先マスタ修正・削除のスケジュール処理を行います。
 * 
 * @version $Revision: 4730 $, $Date: 2009-07-22 16:13:50 +0900 (水, 22 7 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class SupplierMasterModifySCH
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
    public SupplierMasterModifySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * ２画面遷移入力チェック<BR>
     * checkParamで指定された内容をもとに、仕入先マスタ存在チェックを行う。<BR>
     * 該当データが存在した場合はtrueを返す。<BR>
     * パラメータの内容に問題がある場合はfalseを返す。<BR>
     * <BR>
     * @param checkParam 検索条件
     * @return 該当データが存在する場合：<CODE>true</CODE>
     *          該当データが存在しない場合：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean nextCheck(SupplierMasterModifySCHParams checkParam)
            throws CommonException
    {
        // 仕入先マスタデータベースハンドラ
        SupplierHandler sHandler = new SupplierHandler(getConnection());
        // 仕入先マスタ検索キー
        SupplierSearchKey skey = new SupplierSearchKey();

        //検索条件のセット
        // 荷主コード(システム定義)
        skey.setConsignorCode(checkParam.getString(CONSIGNOR_CODE));
        // 仕入先コード
        skey.setSupplierCode(checkParam.getString(SUPPLIER_CODE));

        // 仕入先マスタの検索
        if (sHandler.count(skey) > 0)
        {
            // データが存在する場合はtrueを返却
            return true;
        }

        // (6003011)対象データはありませんでした。
        setMessage("6003011");

        // データが存在しない場合はfalseを返却
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
        // DBファインダー生成
        AbstractDBFinder finder = null;

        try
        {
            // 仕入先マスタファインダークラス生成
            finder = new SupplierFinder(getConnection());
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
     * 修正入力チェック<BR>
     * 修正対象の仕入先情報が使用されているかどうかをチェックします。<BR>
     * <BR>
     * @param checkParam チェック条件
     * @return 修正データが使用されていない場合：<CODE>true</CODE>
     *          修正データが使用中の場合：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean check(SupplierMasterModifySCHParams checkParam)
            throws CommonException
    {
        // 対象情報が他データにて使用されていなかった場合
        if (!this.existRelation(checkParam))
        {
            // 修正処理の場合
            if (MasterInParameter.PROCESS_FLAG_MODIFY.equals(checkParam.getString(PROCESS_FLAG)))
            {
                // (MSG-W0010)修正を行うデータが使用されています。修正を行いますか？
                setDispMessage("MSG-W0010");
            }
            // 削除処理の場合
            else
            {
                // (6023003)削除を行うデータが使用されています。
                setMessage("6023003");
            }
            // データが使用されているためfalseを返却
            return false;
        }
        // データが使用されているためtrueを返却
        return true;
    }

    /**
     * 仕入先マスタ修正･削除スケジュール開始処理<BR>
     * <BR>
     * @param startParam 修正、削除内容
     * @return 正常に修正削除処理が終了した場合：<CODE>true</CODE>
     *          修正削除処理が異常終了した場合：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean startSCH(SupplierMasterModifySCHParams startParam)
            throws CommonException
    {
        // 仕入先マスタデータベースハンドラ
        SupplierHandler sHandler = new SupplierHandler(getConnection());
        // 仕入先マスタ検索キー
        SupplierSearchKey skey = new SupplierSearchKey();
        // 仕入先マスタ更新キー
        SupplierAlterKey akey = new SupplierAlterKey();
        // 仕入先マスタコントローラークラス
        SupplierController sCtl = new SupplierController(getConnection(), this.getClass());

        try
        {
            // 日次更新・取込中・排他チェック
            if (!canStart() || isLoadData() || !isNoModify(startParam))
            {
                return false;
            }

            // 検索キーの設定
            // 仕入先コード
            skey.setSupplierCode(startParam.getString(SUPPLIER_CODE));
            // 荷主コード
            skey.setConsignorCode(startParam.getString(CONSIGNOR_CODE));

            // 上記検索キーより一件のみ取得
            Supplier oldMaster = (Supplier)sHandler.findPrimary(skey);

            // 修正処理
            if (MasterInParameter.PROCESS_FLAG_MODIFY.equals(startParam.getString(PROCESS_FLAG)))
            {
                // 更新条件のセット
                // 仕入先コード
                akey.setSupplierCode(startParam.getString(SUPPLIER_CODE));
                // 荷主コード
                akey.setConsignorCode(startParam.getString(CONSIGNOR_CODE));

                // 更新値のセット
                // 仕入先名称
                akey.updateSupplierName(startParam.getString(SUPPLIER_NAME));
                // 最終更新処理名
                akey.updateLastUpdatePname(this.getClass().getSimpleName());
                // 最終使用日時
                akey.updateLastUsedDate(new SysDate());

                // 仕入先マスタ(更新)
                sHandler.modify(akey);


                // 更新後の仕入先マスタ取得
                // 検索キーのクリア
                skey.clear();

                // 検索キーのセット
                // 仕入先コード
                skey.setSupplierCode(startParam.getString(SUPPLIER_CODE));
                // 荷主コード
                skey.setConsignorCode(startParam.getString(CONSIGNOR_CODE));

                // 上記検索キーより一件のみ取得
                Supplier newMaster = (Supplier)sHandler.findPrimary(skey);


                // 仕入先マスタ更新履歴情報登録
                sCtl.insertHistory(oldMaster, newMaster, SystemDefine.UPDATE_KIND_MODIFY,
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
                // 仕入先コード
                skey.setSupplierCode(startParam.getString(SUPPLIER_CODE));
                // 荷主コード
                skey.setConsignorCode(startParam.getString(CONSIGNOR_CODE));

                // 仕入先マスタ(削除)
                sHandler.drop(skey);


                // 仕入先マスタ更新履歴情報登録
                sCtl.insertHistory(oldMaster, SystemDefine.UPDATE_KIND_DELETE, getWmsUserInfo().getDfkUserInfo());

                // (6001005)削除しました。
                setMessage("6001005");
            }
            // 問題ない場合はtrueを返却
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

            // 問題ありの場合はfalseを返却
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
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        // 仕入先マスタ検索キー
        SupplierSearchKey skey = new SupplierSearchKey();

        //検索キーのセット
        // 荷主コード
        skey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 仕入先コード
        skey.setSupplierCode(p.getString(SUPPLIER_CODE));

        // 取得項目のセット
        // 仕入先コード
        skey.setCollect(Supplier.SUPPLIER_CODE);
        // 仕入先名称
        skey.setCollect(Supplier.SUPPLIER_NAME);
        // 最終更新日
        skey.setCollect(Supplier.LAST_UPDATE_DATE);
        // 最終使用日
        skey.setCollect(Supplier.LAST_USED_DATE);

        // セットした検索キーを返却
        return skey;
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
        Supplier[] entities = (Supplier[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        // 返却用の配列を生成
        List<Params> result = new ArrayList<Params>();

        // 取得データ数分繰り返す
        for (Supplier ent : entities)
        {
            // パラメータクラス生成
            Params param = new Params();

            // 返却データをセット
            // 仕入先コード
            param.set(SUPPLIER_CODE, ent.getSupplierCode());
            // 仕入先名称
            param.set(SUPPLIER_NAME, ent.getValue(Supplier.SUPPLIER_NAME, ""));
            // 最終更新日
            param.set(LAST_UPDATE_DATE, ent.getLastUpdateDate());
            // 最終使用日
            param.set(LAST_USE_DATE, ent.getLastUsedDate());

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
     * 仕入先マスタ関連テーブルチェック<BR>
     * 修正対象の仕入先情報が他のテーブルに存在しないか確認します。<BR>
     * 修正処理、または削除処理のチェック処理で使用します。<BR>
     * <BR>
     * @param checkParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existRelation(SupplierMasterModifySCHParams checkParam)
            throws CommonException
    {
        // システム定義情報コントローラー
        WarenaviSystemController sysController = new WarenaviSystemController(getConnection(), getClass());

        // 入荷パッケージが導入されている場合
        if (sysController.hasReceivingPack())
        {
            // 入荷予定情報データ存在チェック
            if (!existReceivingPlan(checkParam))
            {
                // 対象データが使用されていた場合
                return false;
            }
        }

        // クロスドックパッケージが導入されている場合
        if (sysController.hasCrossdockPack())
        {
            // TC予定情報データ存在チェック
            if (!existXDPlan(checkParam))
            {
                // 対象データが使用されていた場合
                return false;
            }
        }

        // 入庫パッケージが導入されている場合
        if (sysController.hasStoragePack())
        {
            // 入庫予定情報データ存在チェック
            if (!existStoragePlan(checkParam))
            {
                // 対象データが使用されていた場合
                return false;
            }
        }

        // AS/RSパッケージが導入されている場合
        if (sysController.hasAsrsPack())
        {
            // 入出庫作業情報データ存在チェック
            if (!existWorkInfo(checkParam))
            {
                // 対象データが使用されていた場合
                return false;
            }
        }
        // 問題ない場合はtrueを返却
        return true;
    }

    /**
     * 仕入先マスタロック処理<BR>
     * 対象の仕入先情報が他の端末で更新されていないかを確認します。<BR>
     * <BR>
     * @param checkParam 仕入先マスタチェック条件
     * @return 更新されていない：<CODE>true</CODE>
     *          更新されている：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean isNoModify(SupplierMasterModifySCHParams checkParam)
            throws CommonException
    {
        // 仕入先マスタデータベースハンドラ
        SupplierHandler sHandler = new SupplierHandler(getConnection());
        // 仕入先マスタ検索キー
        SupplierSearchKey skey = new SupplierSearchKey();

        // 検索キーのセット
        // 仕入先コード
        skey.setSupplierCode(checkParam.getString(SUPPLIER_CODE));
        // 荷主コード
        skey.setConsignorCode(checkParam.getString(CONSIGNOR_CODE));
        // 最終更新日
        skey.setKey(Supplier.LAST_UPDATE_DATE, checkParam.getDate(LAST_UPDATE_DATE));

        // 対象データロック処理
        if (sHandler.findPrimaryForUpdate(skey, SupplierHandler.WAIT_SEC_NOWAIT) != null)
        {
            // 更新されていなかった場合はtrueを返却
            return true;
        }

        // (6023004)他端末で変更が行われました。前画面に戻り再度検索を行ってください。
        setMessage("6023004");

        // 更新されている場合はfalseを返却
        return false;
    }

    /**
     * 入荷予定情報データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existReceivingPlan(SupplierMasterModifySCHParams inParam)
            throws CommonException
    {
        // 入荷予定情報データベースハンドラ
        ReceivingPlanHandler receivePlanHandler = new ReceivingPlanHandler(getConnection());
        // 入荷予定情報検索キー
        ReceivingPlanSearchKey receivePlanKey = new ReceivingPlanSearchKey();

        // 検索キーのセット
        // 仕入先コード
        receivePlanKey.setSupplierCode(inParam.getString(SUPPLIER_CODE));
        // 荷主コード
        receivePlanKey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        // 作業状態(削除ではない)
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
     * TC予定情報データ存在チェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existXDPlan(SupplierMasterModifySCHParams inParam)
            throws CommonException
    {
        // TC予定情報データベースハンドラ
        CrossDockPlanHandler xdPlanHandler = new CrossDockPlanHandler(getConnection());
        // TC予定情報検索キー
        CrossDockPlanSearchKey xdPlanKey = new CrossDockPlanSearchKey();

        // 検索キーのセット
        // 仕入先コード
        xdPlanKey.setSupplierCode(inParam.getString(SUPPLIER_CODE));
        // 荷主コード
        xdPlanKey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        // 作業状態(削除ではない)
        xdPlanKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        // 検索処理
        if (xdPlanHandler.count(xdPlanKey) > 0)
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
    private boolean existStoragePlan(SupplierMasterModifySCHParams inParam)
            throws CommonException
    {
        // 入庫予定情報データベースハンドラ
        StoragePlanHandler storagePlanHandler = new StoragePlanHandler(getConnection());
        // 入庫予定情報検索キー
        StoragePlanSearchKey storagePlanKey = new StoragePlanSearchKey();

        // 検索キーのセット
        // 仕入先コード
        storagePlanKey.setSupplierCode(inParam.getString(SUPPLIER_CODE));
        // 荷主コード
        storagePlanKey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        // 作業状態(削除ではない)
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
     * 入出庫作業情報データチェック<BR>
     * <BR>
     * @param inParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existWorkInfo(SupplierMasterModifySCHParams inParam)
            throws CommonException
    {
        // 入出庫作業情報データベースハンドラ
        WorkInfoHandler workInfoHandler = new WorkInfoHandler(getConnection());
        // 入出庫作業情報検索キー
        WorkInfoSearchKey workInfoSearchKey = new WorkInfoSearchKey();

        // 検索キーのセット
        // 仕入先コード
        workInfoSearchKey.setSupplierCode(inParam.getString(SUPPLIER_CODE));
        // 荷主コード
        workInfoSearchKey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        // 作業状態(削除ではない)
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
