// $Id: PCTItemModify2SCH.java 6900 2010-01-25 11:21:11Z kumano $
package jp.co.daifuku.pcart.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.schedule.PCTItemModify2SCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.pcart.base.util.PCTDisplayUtil;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTItemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTItemFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTItemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemSearchKey;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.entity.PCTSystem;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 商品マスタ修正・削除（詳細情報）のスケジュール処理を行います。
 *
 * @version $Revision: 6900 $, $Date:: 2010-01-25 20:21:11 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class PCTItemModify2SCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** カラム定義 商品画像(<code>ITEM_PICTURE</code>) */
    private static final FieldName ITEM_PICTURE = new FieldName(PCTItem.STORE_NAME, "ITEM_PICTURE");

    /** カラム定義 商品画像(あり・なし)(<code>PICTURE_FLAG</code>) */
    private static final FieldName PICTURE_FLAG = new FieldName(PCTItem.STORE_NAME, "PICTURE_FLAG");

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
    public PCTItemModify2SCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * 商品マスタ修正･削除スケジュール開始処理<BR>
     * startParamsで指定するParameterクラスはPCTMasterInParameter型であること。<BR>
     * @param startParams 開始条件
     * @return 正常：<CODE>true</CODE>異常：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        DefaultDDBHandler ddbHandler = null;
        try
        {
            // 日次更新チェック
            if (!canStart())
            {
                return false;
            }

            // 取り込み中チェック
            if (isLoadData())
            {
                return false;
            }

            // 排他チェック
            if (!isNoModify(ps[0]))
            {
                return false;
            }

            // 商品マスタチェック
            if (isItemLoad())
            {
                return false;
            }

            // 商品マスタ更新・削除処理
            PCTItemHandler iHandler = new PCTItemHandler(getConnection());

            // 商品マスタ修正処理を行う。
            // 処理フラグが0：修正の場合
            if (PCTMasterInParameter.PROCESS_FLAG_MODIFY.equals(ps[0].getString(PROCESS_FLAG)))
            {
                // 更新キーを生成
                PCTItemAlterKey akey = new PCTItemAlterKey();

                // 更新・削除条件
                // 荷主コード
                akey.setConsignorCode(ps[0].getString(CONSIGNOR_CODE));
                // 商品コード
                akey.setItemCode(ps[0].getString(ITEM_CODE));
                // ロット入数
                akey.setLotEnteringQty(ps[0].getInt(LOT_QTY));

                // 更新値                
                // 商品名称
                akey.updateItemName(ps[0].getString(ITEM_NAME));
                // JANコード
                akey.updateJan(ps[0].getString(JAN));
                // ケースITF
                akey.updateItf(ps[0].getString(ITF));
                // ボールITF
                akey.updateBundleItf(ps[0].getString(BUNDLE_ITF));
                // 単重量
                // 重量誤差率
                // 最大検品可能数
                if (StringUtil.isBlank(ps[0].getString(SINGLE_WEIGHT)))
                {
                    // 単重量未入力時０をセットします。
                    ps[0].set(SINGLE_WEIGHT, 0.0);
                    ps[0].set(MAX_INSPECTION_UNIT_QTY, 0);
                }
                else if (Double.valueOf(ps[0].getString(SINGLE_WEIGHT)) <= 0)
                {
                    // 単重量未入力時０をセットします。
                    ps[0].set(SINGLE_WEIGHT, 0.0);
                    ps[0].set(MAX_INSPECTION_UNIT_QTY, 0);
                }

                if (ps[0].getInt(WEIGHT_DISTINCT_RATE) <= 0)
                {
                    // 誤差率未入力時
                    ps[0].set(WEIGHT_DISTINCT_RATE, getDefaultDistinctRate());
                }
                // 単重量
                akey.updateSingleWeight(Double.valueOf(ps[0].getString(SINGLE_WEIGHT)));
                // 重量誤差率
                akey.updateWeightDistinctRate(ps[0].getInt(WEIGHT_DISTINCT_RATE));
                // 最大検品単位数
                akey.updateMaxInspectionUnitQty(PCTDisplayUtil.getMaxInspectionUnitQty(ps[0].getInt(WEIGHT_DISTINCT_RATE)));
                // メッセージ
                akey.updateInformation(ps[0].getString(INFORMATION));
                // ロケーションNo.
                akey.updateLocationNo1(ps[0].getString(LOCATION_NO));
                // 最終使用日
                akey.updateLastUsedDate(new SysDate());
                // 最終更新処理名
                akey.updateLastUpdatePname(this.getClass().getSimpleName());

                // 更新処理
                iHandler.modify(akey);

                // 商品画像登録
                if (ps[0].getBoolean(DELETE_FLAG))
                {
                    // ダイレクトDBハンドラ(商品画像削除用)
                    ddbHandler = new DefaultDDBHandler(getConnection());

                    StringBuffer sql = null;
                    sql = new StringBuffer();

                    // 商品画像削除SQL（NULLでUPDATEを行う）
                    sql.append("UPDATE DMPCTITEM SET ITEM_PICTURE = NULL WHERE ");
                    sql.append("CONSIGNOR_CODE = ");
                    sql.append(DBFormat.format(ps[0].getString(CONSIGNOR_CODE))).append(" AND ");
                    sql.append("ITEM_CODE = ").append(DBFormat.format(ps[0].getString(ITEM_CODE))).append(" AND ");
                    sql.append("LOT_ENTERING_QTY = ").append(ps[0].getInt(LOT_QTY));

                    ddbHandler.execute(String.valueOf(sql));
                }

                // 6001004=修正しました。
                setMessage(WmsMessageFormatter.format(6001004));
            }

            // 商品マスタ削除処理を行う。
            // 処理フラグが1：削除の場合
            else
            {
                // 検索キーを生成
                PCTItemSearchKey skey = new PCTItemSearchKey();

                // 更新・削除条件
                // 荷主コード
                skey.setConsignorCode(ps[0].getString(CONSIGNOR_CODE));
                // 商品コード
                skey.setItemCode(ps[0].getString(ITEM_CODE));
                // システム管理区分(ユーザ)
                skey.setManagementType(PCTItem.MANAGEMENT_TYPE_USER);
                // ロット入数
                skey.setLotEnteringQty(ps[0].getInt(LOT_QTY));

                // 削除条件
                // 出荷先コード
                iHandler.drop(skey);
                // 6001005=削除しました。
                setMessage(WmsMessageFormatter.format(6001005));
            }

            return true;

        }
        catch (NotFoundException e)
        {
            // =他端末で変更が行われました。前画面に戻り再度検索を行ってください
            setMessage(WmsMessageFormatter.format(6023004));
            return false;
        }
        finally
        {
            if (ddbHandler != null)
            {
                ddbHandler.close();
            }
        }
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
            finder = new PCTItemFinder(getConnection());
            finder.open(true);
            // 検索処理実行
            // 取得件数に応じてメッセージを設定
            if (!canLowerDisplay(finder.search(createSearchKey(p))))
            {
                return new ArrayList<Params>();
            }

            // エンティティを画面表示用にパラメータクラスにセットし返す
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
     * searchParam内の条件に合致する商品情報が他のテーブルに存在しないか確認します。<BR>
     * 他のテーブルに商品情報が存在する場合はtrueを、見つからない場合はfalseを返します。<BR>
     * 画面側の更新・削除事前チェック処理で使用します。<BR>
     * @param checkParam チェック内容
     * @return 商品情報存在：<CODE>true</CODE>商品情報なし<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean check(ScheduleParams p)
            throws CommonException
    {
        try
        {
            // 商品マスタ関連テーブルチェック
            if (!existRelation(p))
            {
                // 処理フラグが修正の場合
                if (PCTMasterInParameter.PROCESS_FLAG_MODIFY.equals(p.get(PROCESS_FLAG)))
                {
                    //MSG-W0010=修正を行うデータが使用されています。修正を行いますか？
                    setDispMessage("MSG-W0010");
                }
                // 処理フラグが削除の場合
                else
                {
                    //6023003=削除を行うデータが使用されています。
                    setMessage(WmsMessageFormatter.format(6023003));
                }
                return false;
            }
            return true;
        }
        catch (ReadWriteException e)
        {
            // 6007002=データベースエラーが発生しました。メッセージログを参照してください
            setMessage("6007002");
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
        PCTItemSearchKey searchKey = new PCTItemSearchKey();

        // set where
        // 荷主コード
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 商品コード
        searchKey.setItemCode(p.getString(ITEM_CODE));
        // ロット入数
        searchKey.setLotEnteringQty(p.getInt(LOT_QTY));

        // set collect
        // 荷主コード
        searchKey.setCollect(PCTItem.CONSIGNOR_CODE);
        // 商品コード
        searchKey.setCollect(PCTItem.ITEM_CODE);
        // 荷主名称
        searchKey.setCollect(PCTItem.CONSIGNOR_NAME);
        // JANコード
        searchKey.setCollect(PCTItem.JAN);
        // 商品名称
        searchKey.setCollect(PCTItem.ITEM_NAME);
        // ロット入数
        searchKey.setCollect(PCTItem.LOT_ENTERING_QTY);
        // ケースITF
        searchKey.setCollect(PCTItem.ITF);
        // ボールITF
        searchKey.setCollect(PCTItem.BUNDLE_ITF);
        // 単重量
        searchKey.setCollect(PCTItem.SINGLE_WEIGHT);
        // 重量誤差率
        searchKey.setCollect(PCTItem.WEIGHT_DISTINCT_RATE);
        // 最大検品可能数
        searchKey.setCollect(PCTItem.MAX_INSPECTION_UNIT_QTY);
        // メッセージ
        searchKey.setCollect(PCTItem.INFORMATION);
        // ロケーションNo.1
        searchKey.setCollect(PCTItem.LOCATION_NO_1);
        // 最終更新日時
        searchKey.setCollect(PCTItem.LAST_UPDATE_DATE);
        // 商品画像(あり・なし)
        searchKey.setCollect(ITEM_PICTURE, "NVL2({0},'true','false')", PICTURE_FLAG);

        return searchKey;
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
        PCTItem[] entities = (PCTItem[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (PCTItem ent : entities)
        {
            Params param = new Params();

            // 返却データをセットする
            // 荷主コード
            param.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // ロット入数
            param.set(LOT_QTY, WmsFormatter.getNumFormat(ent.getLotEnteringQty()));
            // 商品名称
            param.set(ITEM_NAME, ent.getItemName());
            // JANコード
            param.set(JAN, ent.getJan());
            // ケースITF
            param.set(ITF, ent.getItf());
            // ボールITF
            param.set(BUNDLE_ITF, ent.getBundleItf());
            // 単重量
            param.set(SINGLE_WEIGHT, ent.getSingleWeight());
            // 重量誤差率
            param.set(WEIGHT_DISTINCT_RATE, ent.getWeightDistinctRate());
            // 最大検品可能数
            param.set(MAX_INSPECTION_UNIT_QTY, ent.getMaxInspectionUnitQty());
            // メッセージ
            param.set(INFORMATION, ent.getInformation());
            // ロケーションNo.
            param.set(LOCATION_NO, ent.getLocationNo1());
            // 商品画像登録（あり・なし）            
            param.set(ITEM_PICTURE_REGIST, PCTDisplayUtil.getItemPictureString(new Boolean(String.valueOf(ent.getValue(
                    PICTURE_FLAG, false)))));
            // 最終更新日時
            param.set(LAST_UPDATE_DATE, ent.getLastUpdateDate());

            result.add(param);
        }

        return result;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 商品マスタ同時更新処理チェック<BR>
     * checkParam内の条件に合致する商品情報が他の端末で更新されていないか確認します。<BR>
     * 他の処理で商品情報が更新されていなかった場合はtrueを、更新されていた場合はfalseを返します。<BR>
     * 
     * @param p チェック条件
     * @return 更新されていない：<CODE>True</CODE><BR>
     *          更新されている：<CODE>False</CODE><BR>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean isNoModify(ScheduleParams p)
            throws CommonException
    {
        // 商品マスタの検索
        // 検索キー
        PCTItemHandler iHandler = new PCTItemHandler(getConnection());
        PCTItemSearchKey ikey = new PCTItemSearchKey();

        // 荷主コード
        ikey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 商品コード
        ikey.setItemCode(p.getString(ITEM_CODE));
        // 最終更新日時
        ikey.setLastUpdateDate(p.getDate(LAST_UPDATE_DATE));
        // ロット入数
        ikey.setLotEnteringQty(p.getInt(LOT_QTY));

        // ロック処理
        if (iHandler.findPrimaryForUpdate(ikey, PCTItemHandler.WAIT_SEC_NOWAIT) != null)
        {
            return true;
        }

        // 6023004=他端末で変更が行われました。前画面に戻り再度検索を行ってください。
        setMessage(WmsMessageFormatter.format(6023004));
        return false;
    }

    /**
     * 商品マスタ関連テーブルチェック<BR>
     * checkParam内の条件に合致する商品情報が他のテーブルに存在しないか確認します。<BR>
     * 他のテーブルに商品情報が存在する場合はtrueを、見つからない場合はfalseを返します。<BR>
     * 画面側の更新・削除事前チェック処理で使用します。<BR>
     * @param checkParam チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE><BR>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existRelation(ScheduleParams p)
            throws CommonException
    {
        // ----------PCT出庫予定情報----------
        if (!existRetrievalPlan(p))
        {
            return false;
        }

        return true;
    }

    /**
     * PCT出庫予定情報にパラメータの条件に合致するデータが存在しないかチェックします。<BR>
     * 該当データが存在する場合は<CODE>false</CODE>を、存在しない場合は<CODE>true</CODE>を
     * 返します。<BR>
     * @param p チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE><BR>
     *          検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean existRetrievalPlan(ScheduleParams p)
            throws CommonException
    {
        // PCT出庫予定情報の検索
        // 検索キー
        PCTRetPlanHandler handler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey key = new PCTRetPlanSearchKey();

        // 荷主コード
        key.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 商品コード
        key.setItemCode(p.getString(ITEM_CODE));
        // ロット入数
        key.setLotEnteringQty(p.getInt(LOT_QTY));
        // 状態フラグ
        key.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        // 検索処理
        if (handler.count(key) > 0)
        {
            return false;
        }

        return true;
    }

    /**
     * PCTシステム情報より、PCT商品マスタ取込フラグの確認を行います。<BR>
     * SAVE中、LOAD中の場合はtrue、未処理の場合はfalseを返します。<BR>
     * <CODE>getMessage()</CODE>メソッドを使用して結果を取得してください。<BR>
     * @return true:SAVE中、LOAD中 false:未処理
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    private boolean isItemLoad()
            throws CommonException
    {
        // PCTシステム情報ハンドラ類インスタンス生成
        PCTSystemHandler wHandler = new PCTSystemHandler(getConnection());
        PCTSystemSearchKey wSKey = new PCTSystemSearchKey();
        PCTSystem pctSystem = (PCTSystem)wHandler.findPrimary(wSKey);

        if (pctSystem != null)
        {
            if (PCTSystem.PCTMASTER_LOAD_FLAG_LOAD.equals(pctSystem.getPctmasterLoadFlag()))
            {
                // 6024068=ロード処理中のため処理できません。
                setMessage(WmsMessageFormatter.format(6024068));
                return true;
            }
        }
        return false;
    }

    /**
     * PCTシステム情報より、初期重量誤差率を取得します。<BR>
     * 単重量及び重量誤差率より、最大検品単位数を求める。<BR>
     * 単重量又は誤差率が０の場合、上限なし(0)を復帰します。<BR>
     * @return int 最大検品単位数
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private int getDefaultDistinctRate()
            throws CommonException
    {
        PCTSystemHandler sHandler = new PCTSystemHandler(getConnection());
        PCTSystemSearchKey sKey = new PCTSystemSearchKey();

        // 検索条件クリア
        sKey.clear();

        PCTSystem system = (PCTSystem)sHandler.findPrimary(sKey);

        if (system == null)
        {
            return 0;
        }
        return system.getDefultDistinctRate();
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
