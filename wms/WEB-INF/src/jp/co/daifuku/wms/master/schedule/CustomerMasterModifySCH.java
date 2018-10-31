// $Id: CustomerMasterModifySCH.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.master.schedule.CustomerMasterModifySCHParams.*;

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
import jp.co.daifuku.wms.base.controller.CustomerController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.CustomerAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CustomerFinder;
import jp.co.daifuku.wms.base.dbhandler.CustomerHandler;
import jp.co.daifuku.wms.base.dbhandler.CustomerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.SearchKey;

/**
 * 出荷先マスタ修正・削除のスケジュール処理を行います。
 * 
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class CustomerMasterModifySCH
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
    public CustomerMasterModifySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
     * @return 該当データあり：<CODE>true</CODE>
     *          該当データなし：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean nextCheck(CustomerMasterModifySCHParams checkParam)
            throws CommonException
    {
        // 出荷先マスタデータベースハンドラ
        CustomerHandler sHandler = new CustomerHandler(getConnection());
        // 出荷先マスタ検索キー
        CustomerSearchKey skey = new CustomerSearchKey();

        // 検索キーのセット
        // 荷主コード
        skey.setConsignorCode(checkParam.getString(CONSIGNOR_CODE));
        // 出荷先コード
        skey.setCustomerCode(checkParam.getString(CUSTOMER_CODE));

        // 出荷先マスタの検索
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
        // ハンドラインスタンス生成
        AbstractDBFinder finder = null;

        try
        {
            // 出荷先マスタファインダークラス生成
            finder = new CustomerFinder(getConnection());
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
     * 修正対象の出荷先情報が使用されているかどうかをチェックします。<BR>
     * <BR>
     * @param checkParam チェック内容
     * @return 他の作業でデータが使用されている場合：<CODE>true</CODE>
     *          他の作業でデータが使用されていない場合：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean check(CustomerMasterModifySCHParams checkParam)
            throws CommonException
    {
        // 対象データが使用されている場合
        if (!this.existRelation(checkParam))
        {
            // 処理フラグが修正の場合
            if (MasterInParameter.PROCESS_FLAG_MODIFY.equals(checkParam.getString(PROCESS_FLAG)))
            {
                //MSG-W0010=修正を行うデータが使用されています。修正を行いますか？
                setDispMessage("MSG-W0010");
            }
            // 処理フラグが削除の場合
            else
            {
                //6023003=削除を行うデータが使用されています。
                setMessage("6023003");
            }
            // 対象データが使用されている場合falseを返却
            return false;
        }
        // 対象データが使用されていない場合trueを返却
        return true;
    }

    /**
     * 出荷先マスタ修正･削除スケジュール開始処理<BR>
     * startParamで指定するParameterクラスはCustomerMasterModifySCHParams型であること。<BR>
     * <BR>
     * @param startParam 修正内容
     * @return 修正削除処理が正常に終了した場合：<CODE>true</CODE>
     *          修正削除処理が異常終了した場合：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean startSCH(CustomerMasterModifySCHParams startParam)
            throws CommonException
    {
        // 出荷先マスタデータベースハンドラ
        CustomerHandler cHandler = new CustomerHandler(getConnection());
        // 出荷先マスタ検索キー
        CustomerSearchKey skey = new CustomerSearchKey();
        // 出荷先マスタ更新キー
        CustomerAlterKey akey = new CustomerAlterKey();
        // 出荷先マスタコントローラー
        CustomerController aCtl = new CustomerController(getConnection(), this.getClass());

        try
        {
            // 日次更新・取込中・排他チェック
            if (!canStart() || isLoadData() || !isNoModify(startParam))
            {
                return false;
            }

            // 検索キーのセット
            // 検索キーのクリア
            skey.clear();
            // 荷主コード
            skey.setConsignorCode(startParam.getString(CONSIGNOR_CODE));
            // 出荷先コード
            skey.setCustomerCode(startParam.getString(CUSTOMER_CODE));

            // 上記検索キーより一件のみ取得
            Customer oldMaster = (Customer)cHandler.findPrimary(skey);

            // 修正処理
            if (MasterInParameter.PROCESS_FLAG_MODIFY.equals(startParam.getString(PROCESS_FLAG)))
            {
                // 更新条件のセット
                // 荷主コード
                akey.setConsignorCode(startParam.getString(CONSIGNOR_CODE));
                // 出荷先コード
                akey.setCustomerCode(startParam.getString(CUSTOMER_CODE));

                // 更新値のセット
                // 出荷先名称
                akey.updateCustomerName(startParam.getString(CUSTOMER_NAME));
                // ルート
                akey.updateRoute(startParam.getString(ROUTE));
                // 郵便番号
                akey.updatePostalCode(startParam.getString(POSTAL_CODE));
                // 都道府県名
                akey.updatePrefecture(startParam.getString(PREFECTURE));
                // 住所
                akey.updateAddress1(startParam.getString(ADDRESS1));
                // ビル名等
                akey.updateAddress2(startParam.getString(ADDRESS2));
                // TEL
                akey.updateTelephone(startParam.getString(TELEPHONE));
                // 連絡先１
                akey.updateContact1(startParam.getString(CONTACT1));
                // 連絡先２
                akey.updateContact2(startParam.getString(CONTACT2));
                // 仕分場所
                akey.updateSortingPlace(startParam.getString(SORT_PLACE));
                // 最終更新処理名
                akey.updateLastUpdatePname(this.getClass().getSimpleName());
                // 最終使用日
                akey.updateLastUsedDate(new SysDate());

                // 出荷先マスタ(更新)
                cHandler.modify(akey);


                // 更新後の出荷先マスタ取得
                // 検索キーのクリア
                skey.clear();
                // 出荷先コード
                skey.setCustomerCode(startParam.getString(CUSTOMER_CODE));
                // 荷主コード
                skey.setConsignorCode(startParam.getString(CONSIGNOR_CODE));

                // 上記検索キーより一件のみ取得
                Customer newMaster = (Customer)cHandler.findPrimary(skey);

                // 出荷先マスタ更新履歴情報(新規登録)
                aCtl.insertHistory(oldMaster, newMaster, SystemDefine.UPDATE_KIND_MODIFY,
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
                // 出荷先コード
                skey.setCustomerCode(startParam.getString(CUSTOMER_CODE));
                // 荷主コード
                skey.setConsignorCode(startParam.getString(CONSIGNOR_CODE));

                // 出荷先マスタ(削除)
                cHandler.drop(skey);

                // 出荷先マスタ更新履歴情報(新規登録)
                aCtl.insertHistory(oldMaster, SystemDefine.UPDATE_KIND_DELETE, getWmsUserInfo().getDfkUserInfo());

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
     * @return CustomerSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        // 出荷先マスタ検索キー
        CustomerSearchKey skey = new CustomerSearchKey();

        // 検索キーのセット
        // 荷主コード
        skey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 出荷先コード
        skey.setCustomerCode(p.getString(CUSTOMER_CODE));

        // 取得キーのセット
        // 出荷先コード
        skey.setCollect(Customer.CUSTOMER_CODE);
        // 出荷先名称
        skey.setCollect(Customer.CUSTOMER_NAME);
        // ルート
        skey.setCollect(Customer.ROUTE);
        // 郵便番号
        skey.setCollect(Customer.POSTAL_CODE);
        // 都道府県名
        skey.setCollect(Customer.PREFECTURE);
        // 住所
        skey.setCollect(Customer.ADDRESS1);
        // ビル名等
        skey.setCollect(Customer.ADDRESS2);
        // TEL
        skey.setCollect(Customer.TELEPHONE);
        // 連絡先１
        skey.setCollect(Customer.CONTACT1);
        // 連絡先２
        skey.setCollect(Customer.CONTACT2);
        // 仕分場所
        skey.setCollect(Customer.SORTING_PLACE);
        // 最終更新日時
        skey.setCollect(Customer.LAST_UPDATE_DATE);
        // 最終使用日
        skey.setCollect(Customer.LAST_USED_DATE);

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
        Customer[] entities = (Customer[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        // 返却用の配列を生成
        List<Params> result = new ArrayList<Params>();

        // 取得データ件数分、繰り返す
        for (Customer ent : entities)
        {
            // パラメータクラス生成
            Params param = new Params();

            // 返却データをセット
            // 出荷先コード
            param.set(CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名称
            param.set(CUSTOMER_NAME, ent.getValue(Customer.CUSTOMER_NAME, ""));
            // ルート
            param.set(ROUTE, ent.getValue(Customer.ROUTE));
            // 郵便番号
            param.set(POSTAL_CODE, ent.getValue(Customer.POSTAL_CODE));
            // 都道府県名
            param.set(PREFECTURE, ent.getValue(Customer.PREFECTURE));
            // 住所
            param.set(ADDRESS1, ent.getValue(Customer.ADDRESS1));
            // ビル名等
            param.set(ADDRESS2, ent.getValue(Customer.ADDRESS2));
            // TEL
            param.set(TELEPHONE, ent.getValue(Customer.TELEPHONE));
            // 連絡先１
            param.set(CONTACT1, ent.getValue(Customer.CONTACT1));
            // 連絡先２
            param.set(CONTACT2, ent.getValue(Customer.CONTACT2));
            // 仕分場所
            param.set(SORT_PLACE, ent.getValue(Customer.SORTING_PLACE, ""));
            // 最終更新日
            param.set(LAST_UPDATE_DATE, ent.getLastUpdateDate());
            // 最終使用日
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
     * 出荷先マスタロック処理<BR>
     * 対象の仕入先情報が他の端末で更新されていないかを確認します。<BR>
     * <BR>
     * @param checkParam 出荷先マスタチェック条件
     * @return 更新されていない：<CODE>True</CODE>
     *          更新されている：<CODE>False</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean isNoModify(CustomerMasterModifySCHParams checkParam)
            throws CommonException
    {
        // 出荷先マスタデータベースハンドラ
        CustomerHandler sHandler = new CustomerHandler(getConnection());
        // 出荷先マスタ検索キー
        CustomerSearchKey skey = new CustomerSearchKey();

        // 検索キーのセット
        // 荷主コード
        skey.setConsignorCode(checkParam.getString(CONSIGNOR_CODE));
        // 出荷先コード
        skey.setCustomerCode(checkParam.getString(CUSTOMER_CODE));
        // 最終更新日
        skey.setKey(Customer.LAST_UPDATE_DATE, checkParam.getDate(LAST_UPDATE_DATE));

        // 対象データロック処理
        if (sHandler.findPrimaryForUpdate(skey, CustomerHandler.WAIT_SEC_NOWAIT) != null)
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
     * 出荷先マスタ関連テーブルチェック<BR>
     * checkParam内の条件に合致する出荷先情報が他のテーブルに存在しないか確認します。<BR>
     * 他のテーブルに出荷先情報が存在する場合は<CODE>true</CODE>を、<BR>
     * 見つからない場合はfalseを返します。<BR>
     * 画面側の更新・削除事前チェック処理で使用します。<BR>
     * <BR>
     * @param checkParam 検索条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existRelation(CustomerMasterModifySCHParams checkParam)
            throws CommonException
    {
        // システム定義情報コントローラークラス
        WarenaviSystemController sysController = new WarenaviSystemController(getConnection(), getClass());

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

        // クロスドックパッケージ、または出荷パッケージが導入されている場合
        if (sysController.hasCrossdockPack() || sysController.hasShippingPack())
        {
            // 出荷予定情報データ存在チェック
            if (!existShipPlan(checkParam))
            {
                // 対象データが使用されていた場合falseを返却
                return false;
            }
        }
        // 問題ない場合はtrueを返却
        return true;
    }

    /**
     * 出庫予定情報データ存在チェック<BR>
     * <BR>
     * @param inParam 検索条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existRetrievalPlan(CustomerMasterModifySCHParams inParam)
            throws CommonException
    {
        // 出庫予定情報データベースハンドラ
        RetrievalPlanHandler retrievalPlanHandler = new RetrievalPlanHandler(getConnection());
        // 出庫予定情報検索キー
        RetrievalPlanSearchKey retrievalPlanKey = new RetrievalPlanSearchKey();

        // 検索キーのセット
        // 荷主コード
        retrievalPlanKey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        // 出荷先コード
        retrievalPlanKey.setCustomerCode(inParam.getString(CUSTOMER_CODE));
        // 状態フラグ(削除以外)
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
     * @param inParam 検索条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existShipPlan(CustomerMasterModifySCHParams inParam)
            throws CommonException
    {
        // 出荷予定情報データベースハンドラ
        ShipPlanHandler shipPlanHandler = new ShipPlanHandler(getConnection());
        // 出荷予定情報検索キー
        ShipPlanSearchKey shipPlanKey = new ShipPlanSearchKey();

        // 検索キーのセット
        // 荷主コード
        shipPlanKey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        // 出荷先コード
        shipPlanKey.setCustomerCode(inParam.getString(CUSTOMER_CODE));
        // 出荷検品状態フラグ(削除)
        shipPlanKey.setWorkStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=", "(", "", false);
        // バース状態フラグ(削除)
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
     * @param inParam 検索条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existWorkInfo(CustomerMasterModifySCHParams inParam)
            throws CommonException
    {
        // 入出庫情報データベースハンドラ
        WorkInfoHandler workInfoHandler = new WorkInfoHandler(getConnection());
        // 入出庫情報検索キー
        WorkInfoSearchKey workInfoSearchKey = new WorkInfoSearchKey();

        // 検索キーのセット
        // 荷主コード
        workInfoSearchKey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        // 出荷先コード
        workInfoSearchKey.setCustomerCode(inParam.getString(CUSTOMER_CODE));
        // 状態フラグ(削除)
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
