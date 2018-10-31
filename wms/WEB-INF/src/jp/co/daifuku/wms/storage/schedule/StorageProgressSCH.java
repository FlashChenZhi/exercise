// $Id: StorageProgressSCH.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.storage.schedule.StorageProgressSCHParams.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * 入庫作業進捗のスケジュール処理を行います。
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class StorageProgressSCH
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
    public StorageProgressSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 表示時処理
     * @param searchParam 表示データパラメータ
     * @throws CommonException スパークラスから取得時エラー
     * @return outParam
     */
    public List<Params> query(ScheduleParams searchParam)
            throws CommonException
    {
        List<Params> outParam = new ArrayList<Params>();

        String standardDay = null;

        //初期表示時、作業日を基に基準日の検索を行う
        if (searchParam.get(PLAN_DAY) == null || "".equals(searchParam.get(PLAN_DAY)))
        {
            standardDay = standardDay(searchParam.getString(CONSIGNOR_CODE));
        }
        else
        {
            standardDay = searchParam.getString(PLAN_DAY);
        }

        if (standardDay != null)
        {
            searchParam.set(PLAN_DAY, standardDay);
            outParam = setViewData(searchParam);

            return outParam;
        }

        return null;

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
     * 表示する予定日のデータを作成する。
     * @param searchParam 表示データパラメータ
     * @throws CommonException スパークラスから取得時エラー
     * @return 表示用パラメータ
     */
    protected List<Params> setViewData(ScheduleParams searchParam)
            throws CommonException
    {

        List<Params> outParams = new ArrayList<Params>();
        //入庫予定検索
        StoragePlanHandler storageHandler = new StoragePlanHandler(this.getConnection());
        StoragePlanSearchKey searchKey = new StoragePlanSearchKey();

        //入庫予定日で集約
        searchKey.setPlanDayCollect();
        searchKey.setConsignorCodeCollect();
        searchKey.setPlanDayGroup();
        searchKey.setConsignorCodeGroup();
        searchKey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");
        searchKey.setConsignorCode(searchParam.getString(CONSIGNOR_CODE));
        searchKey.setPlanDayOrder(true);

        //ボタン制御
        String btnControl = buttonContlor(searchParam);

        //初期表示、自動表示時、表示ボタン押下時
        if (StorageInParameter.PROCESS_FLAG_VIEW.equals(searchParam.getString(PROCESS_FLAG)))
        {
            //検索条件
            searchKey.setPlanDay(searchParam.getString(PLAN_DAY), ">=");
        }
        //次ボタン押下時
        else if (StorageInParameter.PROCESS_FLAG_NEXT_PAGE.equals(searchParam.getString(PROCESS_FLAG)))
        {
            //基準日（下段）より大きい予定日データ2件を取得
            searchKey.setPlanDay(searchParam.getString(PLAN_DAY), ">");
        }
        //前ボタン押下時
        else if (StorageInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(searchParam.getString(PROCESS_FLAG)))
        {
            //基準日（上段）より小さい予定日データ2件を取得
            searchKey.setPlanDay(searchParam.getString(PLAN_DAY), "<");
        }

        StoragePlan[] searchPlanDayEntity = (StoragePlan[])storageHandler.find(searchKey);

        //配列取得検索位置初期化
        int start = 0;
        int end = 2;

        //前頁ボタン押下時
        if (searchPlanDayEntity.length == 0)
        {
            return outParams;
        }
        else if (StorageInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(searchParam.getString(PROCESS_FLAG))
                && searchPlanDayEntity.length != 1)
        {
            start = searchPlanDayEntity.length - 2;
            end = searchPlanDayEntity.length;
        }

        outParams = createViewData(searchPlanDayEntity, start, end, btnControl);

        return outParams;
    }

    /**
     * 日付データを基に表示したいデータを算出
     *
     * @param searchParam 表示データ
     * @param startPoint 表示配列開始位置
     * @param endPoint 表示配列終了位置
     * @param btnControl 前ボタン制御処理より取得し、配列にパラメータとして加える
     * @return outParam 表示パラメータ
     * @throws CommonException DBアクセスエラー
     */
    protected List<Params> createViewData(StoragePlan[] searchParam, int startPoint, int endPoint, String btnControl)
            throws CommonException
    {
        List<Params> outParams = new ArrayList<Params>();

        //入庫予定検索
        StoragePlanHandler storageHandler = new StoragePlanHandler(this.getConnection());
        //取得データ格納
        StoragePlanSearchKey searchProgressDateKey = new StoragePlanSearchKey();

        //表示件数が2件の為、2件の予定日の進捗情報を取得する
        for (int i = startPoint; i < endPoint; i++)
        {
            //検索キークリア
            searchProgressDateKey.clear();

            //状態が削除以外
            searchProgressDateKey.setConsignorCode(searchParam[i].getConsignorCode());
            searchProgressDateKey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");
            searchProgressDateKey.setPlanDay(searchParam[i].getPlanDay(), "=");

            searchProgressDateKey.setPlanDayCollect();
            searchProgressDateKey.setPlanQtyCollect("SUM");
            searchProgressDateKey.setResultQtyCollect("SUM");
            searchProgressDateKey.setShortageQtyCollect("SUM");

            //商品マスタ情報取得
            searchProgressDateKey.setJoin(StoragePlan.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
            searchProgressDateKey.setJoin(StoragePlan.ITEM_CODE, Item.ITEM_CODE);

            //予定日の集約
            searchProgressDateKey.setPlanDayGroup();

            StoragePlan[] searchEntity = (StoragePlan[])storageHandler.find(searchProgressDateKey);

            //検索結果が
            if (searchEntity.length == 0)
            {
                // 6003011 対象データはありませんでした。
                setMessage("6003011");
                return null;
            }
            Params param = new Params();
            
            //予定日
            param.set(PLAN_DAY, WmsFormatter.toDate(searchEntity[0].getPlanDay()));
            //予定数
            param.set(PLAN_QTY, searchEntity[0].getPlanQty());
            //実績数
            param.set(RESULT_QTY, searchEntity[0].getResultQty());
            //欠品数
            param.set(SHORTAGE_QTY, searchEntity[0].getShortageQty());
            //進捗率 : ((実績数+欠品数)/予定数)*100
            double progress =
                    (((double)(searchEntity[0].getResultQty() + searchEntity[0].getShortageQty()) / searchEntity[0].getPlanQty()) * 100);
            param.set(PROGRESS_RATE, progress);

            //予定日と商品コードの集約で求められる
            searchProgressDateKey.setCollect(Item.ENTERING_QTY);
            searchProgressDateKey.setItemCodeCollect();
            searchProgressDateKey.setGroup(Item.ENTERING_QTY);
            searchProgressDateKey.setItemCodeGroup();

            searchEntity = (StoragePlan[])storageHandler.find(searchProgressDateKey);
            //明細数
            param.set(PLAN_ITEM_CNT, searchEntity.length);

            //商品別の入り数でケース、ピースを取得。
            //取得後、合計を算出する
            int sumPlanCaseQty = 0;
            int sumPlanPieceQty = 0;
            int sumResultCaseQty = 0;
            int sumResultPieceQty = 0;

            int planQty = 0;
            int resultQty = 0;
            int enteringQty = 0;

            for (int cnt = 0; cnt < searchEntity.length; cnt++)
            {
                planQty = searchEntity[cnt].getPlanQty();
                resultQty = searchEntity[cnt].getResultQty();
                enteringQty = searchEntity[cnt].getBigDecimal(Item.ENTERING_QTY).intValue();

                //予定ケース数
                sumPlanCaseQty += DisplayUtil.getCaseQty(planQty, enteringQty);
                //入庫ケース数
                sumResultCaseQty += DisplayUtil.getCaseQty(resultQty, enteringQty);

                //予定ピース数
                sumPlanPieceQty += DisplayUtil.getPieceQty(planQty, enteringQty);
                //入庫ピース数
                sumResultPieceQty += DisplayUtil.getPieceQty(resultQty, enteringQty);
            }
            
            //予定ケース数
            param.set(CASE_QTY, sumPlanCaseQty);
            //入庫ケース数
            param.set(RESULT_CASE_QTY, sumResultCaseQty);
            //予定ピース数
            param.set(PIECE_QTY, sumPlanPieceQty);
            //入庫ピース数
            param.set(RESULT_PIECE_QTY, sumResultPieceQty);

            //予定日と商品コードと作業状態「完了」の集約で求められる
            //実績明細数
            searchProgressDateKey.setStatusFlag(StoragePlan.STATUS_FLAG_COMPLETION, "=");
            param.set(RESULT_ITEM_CNT, storageHandler.find(searchProgressDateKey).length);

            //ボタン制御フラグ
            param.set(BUTTON_CONTROL_FLAG, btnControl);
            
            param.set(DETAIL_COUNT, WmsFormatter.getNumFormat(param.getInt(RESULT_ITEM_CNT)) + "/"
                    + WmsFormatter.getNumFormat(param.getInt(PLAN_ITEM_CNT)));
            
            param.set(CASE_QTY, WmsFormatter.getNumFormat(param.getInt(RESULT_CASE_QTY)) + "/"
                    + WmsFormatter.getNumFormat(param.getInt(CASE_QTY)));
            
            param.set(STORAGE_COUNT, WmsFormatter.getNumFormat(param.getInt(RESULT_QTY)) + "/"
                    + WmsFormatter.getNumFormat(param.getInt(PLAN_QTY)));
            
            param.set(PIECE_QTY, WmsFormatter.getNumFormat(param.getInt(RESULT_PIECE_QTY)) + "/"
                    + WmsFormatter.getNumFormat(param.getInt(PIECE_QTY)));
            
            outParams.add(param);

            if (searchParam.length == 1)
            {

                break;
            }
        }

        return outParams;
    }


    /**
     * 初期表示時、基準日を取得する。
     * システムの作業日と一致する予定日情報がある場合、一致する予定日を基準日とする。
     * システムの作業日と予定日が一致しない場合、作業日の前後予定日を検出しする。
     * より近い日付の予定日を基準日とする。
     * 作業日と前後予定日の差が同じ場合、過去の予定日を基準日とする。
     *
     * @param getConsignorCode 表示時取得した荷主コード
     * @return standardDay 作業日に一番近い予定日を基準日とし返す
     * @throws CommonException DBアクセス時エラー
     */
    protected String standardDay(String getConsignorCode)
            throws CommonException
    {
        //WARENAVI_SYSYTEMテーブルの情報を取得します。
        WarenaviSystemController wWareNaviManager = new WarenaviSystemController(this.getConnection(), this.getClass());
        String nowDay = wWareNaviManager.getWorkDay();
        Date nowDayDate = WmsFormatter.toDate(wWareNaviManager.getWorkDay());


        StoragePlanSearchKey searchKey = new StoragePlanSearchKey();
        //荷主コード
        searchKey.setConsignorCode(getConsignorCode);

        // 入庫予定日の順でソート
        searchKey.setPlanDayOrder(true);
        searchKey.setPlanDayCollect("DISTINCT");
        searchKey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");

        StoragePlanHandler storageHandler = new StoragePlanHandler(this.getConnection());
        StoragePlan[] resultEntity = (StoragePlan[])storageHandler.find(searchKey);
        //基準日初期化
        String standardDay = null;

        //検索結果が0件の場合エラー
        if (resultEntity.length == 0)
        {
            // 6003011 対象データはありませんでした。
            setMessage("6003011");
            return null;
        }
        //検索結果が2件の場合、一致した2件を表示
        else if (resultEntity.length == 2)
        {
            standardDay = resultEntity[0].getPlanDay();
        }
        else
        {
            Date standardSmallDay = null;
            Date standardBigDay = null;

            //基準日検索(予定日が作業日より小さい)
            searchKey.setPlanDay(nowDay, "<");
            StoragePlan[] smallEntity = (StoragePlan[])storageHandler.find(searchKey);

            if (smallEntity.length != 0)
            {
                standardSmallDay = WmsFormatter.toDate(smallEntity[smallEntity.length - 1].getPlanDay());
            }

            //検索条件クリア
            searchKey.clearKeys();
            //基準日検索(予定日が作業日より大きい)
            searchKey.setPlanDay(nowDay, ">=");
            StoragePlan[] bigEntity = (StoragePlan[])storageHandler.find(searchKey);

            if (bigEntity.length != 0)
            {
                standardBigDay = WmsFormatter.toDate(bigEntity[0].getPlanDay());
            }

            //作業日より小さいデータが存在する場合
            if (standardSmallDay != null && standardBigDay != null)
            {
                //作業日以上、以下の予定日で一番近い予定日を基準日とする。
                long smallDifference = (nowDayDate.getTime() - standardSmallDay.getTime());
                long bigDifference = (standardBigDay.getTime() - nowDayDate.getTime());

                //作業日と一番近い予定日が過去の場合
                if (smallDifference <= bigDifference)
                {
                    standardDay = WmsFormatter.toParamDate(standardSmallDay);
                }
                else
                {
                    standardDay = WmsFormatter.toParamDate(standardBigDay);
                }

            }
            else if (standardBigDay != null)
            {
                standardDay = WmsFormatter.toParamDate(standardBigDay);
            }
            else
            {
                standardDay = WmsFormatter.toParamDate(standardSmallDay);
            }
        }
        return standardDay;
    }

    /**
     * 基準日の前後データ数を取得し前頁、次頁の制御を行います
     *
     * @param searchParam 表示基準日
     * @return btnControl ボタン制御フラグ
     * @throws CommonException DBアクセスエラー
     */
    protected String buttonContlor(ScheduleParams searchParam)
            throws CommonException
    {
        //入庫予定検索
        StoragePlanHandler storageHandler = new StoragePlanHandler(this.getConnection());
        StoragePlanSearchKey lowSearchKey = new StoragePlanSearchKey();
        StoragePlanSearchKey highSearchKey = new StoragePlanSearchKey();

        lowSearchKey.setPlanDayCollect();
        lowSearchKey.setPlanDayGroup();
        lowSearchKey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");
        lowSearchKey.setConsignorCode(searchParam.getString(CONSIGNOR_CODE));

        highSearchKey.setPlanDayCollect();
        highSearchKey.setPlanDayGroup();
        highSearchKey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");
        highSearchKey.setConsignorCode(searchParam.getString(CONSIGNOR_CODE));

        //データ件数取得
        int lowDayCount = 0;
        int highDayCount = 0;

        //ボタン制御の為、前後のデータ数を取得します
        lowSearchKey.setPlanDay(searchParam.getString(PLAN_DAY), "<");
        lowDayCount = storageHandler.find(lowSearchKey).length;

        highSearchKey.setPlanDay(searchParam.getString(PLAN_DAY), ">");
        highDayCount = storageHandler.find(highSearchKey).length;

        //ボタン制御フラグ
        String btnControl = null;

        //初期表示、自動表示時、表示ボタン押下時
        if (StorageInParameter.PROCESS_FLAG_VIEW.equals(searchParam.getString(PROCESS_FLAG)))
        {
            if (lowDayCount == 0 && highDayCount < 2)
            {
                //前、次頁使用不可
                btnControl = StorageOutParameter.BUTTON_CONTROL_FLAG_ALL_OFF;
            }
            else if (lowDayCount == 0)
            {
                //前頁使用不可
                btnControl = StorageOutParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF;
            }
            else if (highDayCount < 2)
            {
                //次頁使用不可
                btnControl = StorageOutParameter.BUTTON_CONTROL_FLAG_NEXT_OFF;
            }
            else
            {
                //前、次頁使用可
                btnControl = StorageOutParameter.BUTTON_CONTROL_FLAG_ALL_ON;
            }
        }
        //次ボタン押下時
        else if (StorageInParameter.PROCESS_FLAG_NEXT_PAGE.equals(searchParam.getString(PROCESS_FLAG)))
        {
            if (highDayCount <= 2)
            {
                //次頁使用不可
                btnControl = StorageOutParameter.BUTTON_CONTROL_FLAG_NEXT_OFF;
            }
            else
            {
                //前、次頁使用可
                btnControl = StorageOutParameter.BUTTON_CONTROL_FLAG_ALL_ON;
            }
        }
        //前ボタン押下時
        else if (StorageInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(searchParam.getString(PROCESS_FLAG)))
        {
            if (lowDayCount <= 2)
            {
                //前頁使用不可
                btnControl = StorageOutParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF;
            }
            else
            {
                //前、次頁使用可
                btnControl = StorageOutParameter.BUTTON_CONTROL_FLAG_ALL_ON;
            }
        }
        return btnControl;
    }
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
