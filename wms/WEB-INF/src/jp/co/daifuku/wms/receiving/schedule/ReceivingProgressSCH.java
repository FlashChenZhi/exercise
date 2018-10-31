// $Id: ReceivingProgressSCH.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.receiving.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
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
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.ReceivingPlan;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import static jp.co.daifuku.wms.receiving.schedule.ReceivingProgressSCHParams.*;

/**
 * 入荷作業進捗のスケジュール処理を行います。
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class ReceivingProgressSCH
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
    public ReceivingProgressSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @param searchParam 表示データ取得条件を持つ<CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> query(ScheduleParams searchParam)
            throws CommonException
    {
        String standardDay = null;

        // Searches a standard date, based on a work date,  when an initial display shows up. 
        List<Params> outParams = new ArrayList<Params>();

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
            outParams = setViewData(searchParam);

            return outParams;
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
     * Creates planned-date data to display.
     * @param searchParam Display data parameters
     * @throws CommonException An error when acquisition from a super class failed
     * @return Parameters to display
     */
    protected List<Params> setViewData(ScheduleParams searchParam)
            throws CommonException
    {

        List<Params> outParams = new ArrayList<Params>();

        //Receiving plan search
        ReceivingPlanHandler receivingHandler = new ReceivingPlanHandler(this.getConnection());
        ReceivingPlanSearchKey searchKey = new ReceivingPlanSearchKey();

        //Collects by planned receiving date
        searchKey.setPlanDayCollect();
        searchKey.setConsignorCodeCollect();
        searchKey.setPlanDayGroup();
        searchKey.setConsignorCodeGroup();
        searchKey.setStatusFlag(ReceivingPlan.STATUS_FLAG_DELETE, "!=");
        searchKey.setConsignorCode(searchParam.getString(CONSIGNOR_CODE));
        searchKey.setPlanDayOrder(true);

        //Button controls
        String btnControl = buttonContlor(searchParam);

        //When a display button on an initial or auto display is pushed 
        if (ReceivingInParameter.PROCESS_FLAG_VIEW.equals(searchParam.get(PROCESS_FLAG)))
        {
            //Search condition
            searchKey.setPlanDay(searchParam.getString(PLAN_DAY), ">=");
        }
        //When the next button is pushed
        else if (ReceivingInParameter.PROCESS_FLAG_NEXT_PAGE.equals(searchParam.get(PROCESS_FLAG)))
        {
            //Gets two planned date data that are greater than a standard date (the lower section)
            searchKey.setPlanDay(searchParam.getString(PLAN_DAY), ">");
        }
        //When the previous button is pushed
        else if (ReceivingInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(searchParam.get(PROCESS_FLAG)))
        {
            //Gets two planned date data that are smaller than a standard date (the upper section)
            searchKey.setPlanDay(searchParam.getString(PLAN_DAY), "<");
        }

        ReceivingPlan[] searchPlanDayEntity = (ReceivingPlan[])receivingHandler.find(searchKey);

        //Initializes an array-acquisition-search position
        int start = 0;
        int end = 2;

        //When the previous button is pushed
        if (searchPlanDayEntity.length == 0)
        {
            return outParams;
        }
        else if (ReceivingInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(searchParam.get(PROCESS_FLAG))
                && searchPlanDayEntity.length != 1)
        {
            start = searchPlanDayEntity.length - 2;
            end = searchPlanDayEntity.length;
        }

        outParams = createViewData(searchPlanDayEntity, start, end, btnControl);

        return outParams;
    }

    /**
     * Calculates display data  based on date data
     *
     * @param searchParam Display data
     * @param startPoint Starting position for an array to display
     * @param endPoint Ending position for an array to display
     * @param btnControl Acquired from the previous button control and added in an array  as parameter
     * @return outParam Display parameter
     * @throws CommonException DB access error
     */
    protected List<Params> createViewData(ReceivingPlan[] searchParam, int startPoint, int endPoint, String btnControl)
            throws CommonException
    {

        List<Params> outParams = new ArrayList<Params>();
        //Receiving plan search
        ReceivingPlanHandler receivingHandler = new ReceivingPlanHandler(this.getConnection());
        //Stores acquired data
        ReceivingPlanSearchKey searchProgressDateKey = new ReceivingPlanSearchKey();


        //Gets progress information of the two planned date since there are two display data
        for (int i = startPoint; i < endPoint; i++)
        {

            //Clears search keys
            searchProgressDateKey.clear();

            //Except "delete" status
            searchProgressDateKey.setConsignorCode(searchParam[i].getConsignorCode());
            searchProgressDateKey.setStatusFlag(ReceivingPlan.STATUS_FLAG_DELETE, "!=");
            searchProgressDateKey.setPlanDay(searchParam[i].getPlanDay(), "=");

            searchProgressDateKey.setPlanDayCollect();
            searchProgressDateKey.setPlanQtyCollect("SUM");
            searchProgressDateKey.setResultQtyCollect("SUM");
            searchProgressDateKey.setShortageQtyCollect("SUM");

            //Gets Item Master information
            searchProgressDateKey.setJoin(ReceivingPlan.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
            searchProgressDateKey.setJoin(ReceivingPlan.ITEM_CODE, Item.ITEM_CODE);

            //Grouping planned dates
            searchProgressDateKey.setPlanDayGroup();

            ReceivingPlan[] searchEntity = (ReceivingPlan[])receivingHandler.find(searchProgressDateKey);

            // Seacrh results are zero.
            if (searchEntity.length == 0)
            {
                // 6003011 = "Target data was not found."
                setMessage("6003011");
                return null;
            }

            Params param = new Params();
            //Stores acquired information in outParam
            //Planned Date
            param.set(PLAN_DAY, WmsFormatter.toDate(searchEntity[0].getPlanDay()));
            //Planned quantity
            param.set(PLAN_QTY, searchEntity[0].getPlanQty());
            //Received quantity
            param.set(RESULT_QTY, searchEntity[0].getResultQty());
            //Shortage quantity
            param.set(SHORTAGE_QTY, searchEntity[0].getShortageQty());
            //Progress rate : ((Received quantity+Shortage quantity)/Planned quantity)*100
            double progress =
                    (((double)(searchEntity[0].getResultQty() + searchEntity[0].getShortageQty()) / searchEntity[0].getPlanQty()) * 100);
            param.set(PROGRESS_RATE, progress);

            // Collects and groups Item Code. 
            searchProgressDateKey.setCollect(Item.ENTERING_QTY);
            searchProgressDateKey.setItemCodeCollect();
            searchProgressDateKey.setGroup(Item.ENTERING_QTY);
            searchProgressDateKey.setItemCodeGroup();

            searchEntity = (ReceivingPlan[])receivingHandler.find(searchProgressDateKey);
            //Detail counts
            param.set(PLAN_ITEM_CNT, searchEntity.length);

            //Gets quantity of cases and pieces by the "pieces of in one case" of different items.
            //After acquisition, calculates a total.
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

                //Planned case quantity
                sumPlanCaseQty += DisplayUtil.getCaseQty(planQty, enteringQty);
                //Received case quantity
                sumResultCaseQty += DisplayUtil.getCaseQty(resultQty, enteringQty);

                //Planned  pieces quantity
                sumPlanPieceQty += DisplayUtil.getPieceQty(planQty, enteringQty);
                //Received  pieces quantity
                sumResultPieceQty += DisplayUtil.getPieceQty(resultQty, enteringQty);
            }
            //Planned case quantity
            param.set(CASE_QTY, sumPlanCaseQty);
            //Received case quantity
            param.set(RESULT_CASE_QTY, sumResultCaseQty);
            //Planned  pieces quantity
            param.set(PIECE_QTY, sumPlanPieceQty);
            //Received  pieces quantity
            param.set(RESULT_PIECE_QTY, sumResultPieceQty);

            // Sets Status flag "Completion"
            //Result Detail counts
            searchProgressDateKey.setStatusFlag(ReceivingPlan.STATUS_FLAG_COMPLETION, "=");
            param.set(RESULT_ITEM_CNT, receivingHandler.find(searchProgressDateKey).length);

            param.set(DETAIL_COUNT, WmsFormatter.getNumFormat(param.getInt(RESULT_ITEM_CNT)) + "/"
                    + WmsFormatter.getNumFormat(param.getInt(PLAN_ITEM_CNT)));
            
            param.set(CASE_QTY, WmsFormatter.getNumFormat(param.getInt(RESULT_CASE_QTY)) + "/"
                    + WmsFormatter.getNumFormat(param.getInt(CASE_QTY)));
            
            param.set(RECEIVE_COUNT, WmsFormatter.getNumFormat(param.getInt(RESULT_QTY)) + "/"
                    + WmsFormatter.getNumFormat(param.getInt(PLAN_QTY)));
            
            param.set(PIECE_QTY, WmsFormatter.getNumFormat(param.getInt(RESULT_PIECE_QTY)) + "/"
                    + WmsFormatter.getNumFormat(param.getInt(PIECE_QTY)));

            //Button control flag
            param.set(BUTTON_CONTROL_FLAG, btnControl);
            //Increment an  index for the output array

            outParams.add(param);

            if (searchParam.length == 1)
            {

                break;
            }

        }

        return outParams;
    }


    /**
     * Gets a standard date when an initial display shows up.<BR>
     * Makes the corresponding Planned Date a standard date if there are any Planned Date Information corresponding to system work date.<BR>
     * Otherwise, searches the previous and next Planned Date of the work date and makes the closest Planned Date a standard date.<BR>
     * If the differences between work date and the previous/next Planned date are the same, then makes past Planned data a standard date.<bR>
     *
     *
     * @param getConsignorCode Consignor Code acquired when displaying data
     * @return standardDay Make Planned Date closest to work date a standard date, then returns it.
     * @throws CommonException An Error in DB access.
     */
    protected String standardDay(String getConsignorCode)
            throws CommonException
    {
        // Gets WARENAVI_SYSYTEM Table information
        WarenaviSystemController wWareNaviManager = new WarenaviSystemController(this.getConnection(), this.getClass());
        String nowDay = wWareNaviManager.getWorkDay();
        Date nowDayDate = WmsFormatter.toDate(wWareNaviManager.getWorkDay());


        ReceivingPlanSearchKey searchKey = new ReceivingPlanSearchKey();
        //Consignor Code
        searchKey.setConsignorCode(getConsignorCode);

        // Sorts by the order of Receiving Planned date
        searchKey.setPlanDayOrder(true);
        searchKey.setPlanDayCollect("DISTINCT");
        searchKey.setStatusFlag(ReceivingPlan.STATUS_FLAG_DELETE, "!=");

        ReceivingPlanHandler receivingHandler = new ReceivingPlanHandler(this.getConnection());
        ReceivingPlan[] resultEntity = (ReceivingPlan[])receivingHandler.find(searchKey);
        //Initializes a standard date
        String standardDay = null;

        //Error when no search result is found
        if (resultEntity.length == 0)
        {
            // 6003011 = "Target data was not found."
            setMessage("6003011");
            return null;
        }
        //If and  only if two search result are found, then display them.
        else if (resultEntity.length == 2)
        {
            standardDay = resultEntity[0].getPlanDay();
        }
        else
        {
            Date standardSmallDay = null;
            Date standardBigDay = null;

            //Standard Date search(Planned Date is smaller than work date)
            searchKey.setPlanDay(nowDay, "<");
            ReceivingPlan[] smallEntity = (ReceivingPlan[])receivingHandler.find(searchKey);

            if (smallEntity.length != 0)
            {
                standardSmallDay = WmsFormatter.toDate(smallEntity[smallEntity.length - 1].getPlanDay());
            }

            // Clears Search condition
            searchKey.clearKeys();
            //Standard Date search(Planned Date is greater than work date)
            searchKey.setPlanDay(nowDay, ">=");
            ReceivingPlan[] bigEntity = (ReceivingPlan[])receivingHandler.find(searchKey);

            if (bigEntity.length != 0)
            {
                standardBigDay = WmsFormatter.toDate(bigEntity[0].getPlanDay());
            }

            //If there exist smaller data then work date
            if (standardSmallDay != null && standardBigDay != null)
            {
                //Makes Planned Date closeset to work date a standard date
                long smallDifference = (nowDayDate.getTime() - standardSmallDay.getTime());
                long bigDifference = (standardBigDay.getTime() - nowDayDate.getTime());

                //If Planned Date closest to work date is paset date
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
     * Gets the number of the previous/next data of a standard, and controls the previous/next pages. 
     *
     * @param searchParam Standard data to display
     * @return btnControl Button control flag
     * @throws CommonException DB access error
     */
    protected String buttonContlor(ScheduleParams searchParam)
            throws CommonException
    {
        //Receiving plan search
        ReceivingPlanHandler receivingHandler = new ReceivingPlanHandler(this.getConnection());
        ReceivingPlanSearchKey lowSearchKey = new ReceivingPlanSearchKey();
        ReceivingPlanSearchKey highSearchKey = new ReceivingPlanSearchKey();

        lowSearchKey.setPlanDayCollect();
        lowSearchKey.setPlanDayGroup();
        lowSearchKey.setStatusFlag(ReceivingPlan.STATUS_FLAG_DELETE, "!=");
        lowSearchKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);

        highSearchKey.setPlanDayCollect();
        highSearchKey.setPlanDayGroup();
        highSearchKey.setStatusFlag(ReceivingPlan.STATUS_FLAG_DELETE, "!=");
        highSearchKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);

        //Gets the number of data
        int lowDayCount = 0;
        int highDayCount = 0;

        // Gets the number of the previous/next data due to Button control
        lowSearchKey.setPlanDay(searchParam.getString(PLAN_DAY), "<");
        lowDayCount = receivingHandler.find(lowSearchKey).length;

        highSearchKey.setPlanDay(searchParam.getString(PLAN_DAY), ">");
        highDayCount = receivingHandler.find(highSearchKey).length;

        //Button control flag
        String btnControl = null;

        //When a display button on an initial or auto display is pushed 
        if (ReceivingInParameter.PROCESS_FLAG_VIEW.equals(searchParam.get(PROCESS_FLAG)))
        {
            if (lowDayCount == 0 && highDayCount < 2)
            {
                //Disables the previous/next pages
                btnControl = ReceivingOutParameter.BUTTON_CONTROL_FLAG_ALL_OFF;
            }
            else if (lowDayCount == 0)
            {
                //Disables the previous page
                btnControl = ReceivingOutParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF;
            }
            else if (highDayCount < 2)
            {
                //Disables the next page
                btnControl = ReceivingOutParameter.BUTTON_CONTROL_FLAG_NEXT_OFF;
            }
            else
            {
                //Enables the previous/next pages
                btnControl = ReceivingOutParameter.BUTTON_CONTROL_FLAG_ALL_ON;
            }
        }
        //When the next button is pushed
        else if (ReceivingInParameter.PROCESS_FLAG_NEXT_PAGE.equals(searchParam.get(PROCESS_FLAG)))
        {
            if (highDayCount <= 2)
            {
                //Disables the next page
                btnControl = ReceivingOutParameter.BUTTON_CONTROL_FLAG_NEXT_OFF;
            }
            else
            {
                //Enables the previous/next pages
                btnControl = ReceivingOutParameter.BUTTON_CONTROL_FLAG_ALL_ON;
            }
        }
        //When the previous button is pushed
        else if (ReceivingInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(searchParam.get(PROCESS_FLAG)))
        {
            if (lowDayCount <= 2)
            {
                //Disables the previous page
                btnControl = ReceivingOutParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF;
            }
            else
            {
                //Enables the previous/next pages
                btnControl = ReceivingOutParameter.BUTTON_CONTROL_FLAG_ALL_ON;
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
