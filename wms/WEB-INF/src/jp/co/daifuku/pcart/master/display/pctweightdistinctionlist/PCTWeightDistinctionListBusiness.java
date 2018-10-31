// $Id: PCTWeightDistinctionListBusiness.java 7522 2010-03-13 06:01:48Z shibamoto $
package jp.co.daifuku.pcart.master.display.pctweightdistinctionlist;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.sql.Connection;
import java.util.Date;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.pcart.master.dasch.PCTWeightDistinctionListDASCH;
import jp.co.daifuku.pcart.master.dasch.PCTWeightDistinctionListDASCHParams;
import jp.co.daifuku.pcart.master.exporter.PctWeightDistinctionListParams;
import jp.co.daifuku.pcart.master.listbox.pctitem.PCTLstItemParams;
import jp.co.daifuku.pcart.master.listbox.pctweightdistinctionlist.LstPCTWeightDistinctionListParams;
import jp.co.daifuku.pcart.master.schedule.PCTMasterInParameter;
import jp.co.daifuku.pcart.master.schedule.PCTWeightDistinctionListSCH;
import jp.co.daifuku.pcart.master.schedule.PCTWeightDistinctionListSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsChecker;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * 重量差異リスト発行の画面処理を行います。
 *
 * @version $Revision: 7522 $, $Date:: 2010-03-13 15:01:48 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class PCTWeightDistinctionListBusiness
        extends PCTWeightDistinctionList
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

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
     * Default constructor
     */
    public PCTWeightDistinctionListBusiness()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        setTitle();

        // save a popup event source.
        viewState.setString(_KEY_POPUPSOURCE, request.getParameter(_KEY_POPUPSOURCE));

        // initialize pulldown models.
        initializePulldownModels();

        // process call.
        page_Load_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        // initialize componenets.
        initializeComponents();

        // process call.
        page_Initialize_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        // get event source.
        DialogParameters dialogParams = ((DialogEvent)e).getDialogParameters();
        String eventSource = dialogParams.getParameter(_KEY_POPUPSOURCE);
        if (StringUtil.isBlank(eventSource))
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_SearchItem_Click"))
        {
            // process call.
            btn_SearchItem_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_Display_Click"))
        {
            // process call.
            btn_Display_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        // get event source.
        String eventSource = viewState.getString(_KEY_CONFIRMSOURCE);
        if (eventSource == null)
        {
            return;
        }

        // remove event source.
        viewState.remove(_KEY_CONFIRMSOURCE);

        // check result.
        boolean isExecute = new Boolean(String.valueOf(e.getEventArgs().get(0))).booleanValue();
        if (!isExecute)
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_Print_Click"))
        {
            // process call.
            btn_Print_Click_Process(false);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchItem_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchItem_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Display_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Display_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Print_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Print_Click_Process(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_XLS_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_XLS_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Clear_Click_Process();
    }

    /**
     * メニューへ遷移します。

     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
     */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
        // セッションからコネクションを削除する
        SessionUtil.deleteSession(getSession());
        // メニューへ遷移します
        forward(BusinessClassHelper.getSubMenuPath(viewState.getString(Constants.M_MENUID_KEY)));
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
    // DFKLOOK:ここから修正
    /**
     * 画面入力フィールドの条件チェックを行います。
     * <BR>
     * 概要:<BR>
     *<DIR>
     *    1.検索開始日＜＝検索終了日
     *    2.検索日付、時間の必須チェック、時間が入力されている場合、日付は必須入力
     *@return 入力に不正があればfalse 無ければtrueを返す
     */
    protected boolean checkInput()
    {
        WmsChecker checker = new WmsChecker();

        if (!checker.checkDate(txt_SearchDate.getDate(), txt_SearchTime.getTime()))
        {
            setFocus(txt_SearchDate);
            // 6023009 = 時刻入力時は、日付入力も行ってください。
            message.setMsgResourceKey("6023009");
            return false;
        }
        if (!checker.checkDate(txt_ToSearchDate.getDate(), txt_ToSearchTime.getTime()))
        {
            setFocus(txt_ToSearchDate);
            // 6023009 = 時刻入力時は、日付入力も行ってください。
            message.setMsgResourceKey("6023009");
            return false;
        }
        return true;
    }

    // DFKLOOK:ここまで修正
    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     *
     * @throws Exception
     */
    private void initializeComponents()
            throws Exception
    {
    }

    /**
     *
     * @throws Exception
     */
    private void initializePulldownModels()
            throws Exception
    {
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_ConsignorCode);

        // DFKLOOK:ここから修正
        Locale locale = httpRequest.getLocale();
        if (!StringUtil.isBlank(txt_SearchDate.getText()) && !StringUtil.isBlank(txt_ToSearchDate.getText()))
        {
            Date[] tmp =
                    WmsFormatter.getFromTo(txt_SearchDate.getDate(), txt_SearchTime.getTime(),
                            txt_ToSearchDate.getDate(), txt_ToSearchTime.getTime());
            txt_SearchDate.setText(WmsFormatter.toDispDate(tmp[0], locale));
            txt_SearchTime.setText(WmsFormatter.toDispTime(tmp[0], locale));
            txt_ToSearchDate.setText(WmsFormatter.toDispDate(tmp[1], locale));
            txt_ToSearchTime.setText(WmsFormatter.toDispTime(tmp[1], locale));
        }
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        txt_ConsignorCode.setValue(null);
        txt_ItemCode.setValue(null);
        txt_SearchDate.setValue(null);
        txt_SearchTime.setValue(null);
        txt_ToSearchDate.setValue(null);
        txt_ToSearchTime.setValue(null);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTWeightDistinctionListSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTWeightDistinctionListSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTWeightDistinctionListSCHParams inparam = new PCTWeightDistinctionListSCHParams();

            // SCH call.
            // DFKLOOK:ここから修正
            Params outparam = sch.initFind(inparam);

            if (outparam != null)
            {
                txt_ConsignorCode.setValue(outparam.get(PCTWeightDistinctionListSCHParams.CONSIGNOR_CODE));
            }
            // DFKLOOK:ここまで修正
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }
        }
        finally
        {
            DBUtil.close(conn);
        }


    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchItem_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        PCTLstItemParams inparam = new PCTLstItemParams();
        inparam.set(PCTLstItemParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(PCTLstItemParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(PCTLstItemParams.SEARCHTABLE, PCTMasterInParameter.SEARCH_TABLE_WEIGTHDISTINCT);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchItem_Click");
        redirect("/pcart/master/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchItem_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        PCTLstItemParams outparam = new PCTLstItemParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(PCTLstItemParams.ITEM_CODE));

        // set focus.
        setFocus(txt_ItemCode);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstPCTWeightDistinctionListParams inparam = new LstPCTWeightDistinctionListParams();

        // DFKLOOK:ここから修正
        Locale locale = httpRequest.getLocale();
        Date[] tmp =
                WmsFormatter.getFromTo(txt_SearchDate.getDate(), txt_SearchTime.getTime(), txt_ToSearchDate.getDate(),
                        txt_ToSearchTime.getTime());
        txt_SearchDate.setText(WmsFormatter.toDispDate(tmp[0], locale));
        txt_SearchTime.setText(WmsFormatter.toDispTime(tmp[0], locale));
        txt_ToSearchDate.setText(WmsFormatter.toDispDate(tmp[1], locale));
        txt_ToSearchTime.setText(WmsFormatter.toDispTime(tmp[1], locale));
        // DFKLOOK:ここまで修正

        inparam.set(LstPCTWeightDistinctionListParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstPCTWeightDistinctionListParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstPCTWeightDistinctionListParams.FROM_OCCUR_DAY, txt_SearchDate.getValue());
        inparam.set(LstPCTWeightDistinctionListParams.FROM_OCCUR_TIME, txt_SearchTime.getValue());
        inparam.set(LstPCTWeightDistinctionListParams.TO_OCCUR_DAY, txt_ToSearchDate.getValue());
        inparam.set(LstPCTWeightDistinctionListParams.TO_OCCUR_TIME, txt_ToSearchTime.getValue());

        // DFKLOOK:ここから修正
        // 補完済の日付、時刻を求める
        String fromday = WmsFormatter.toParamDate(txt_SearchDate.getDate());
        String fromtime = WmsFormatter.toParamTime(txt_SearchTime.getTime());
        String today = WmsFormatter.toParamDate(txt_ToSearchDate.getDate());
        String totime = WmsFormatter.toParamTime(txt_ToSearchTime.getTime());

        Date searchdatefrom = WmsFormatter.getSearchFromDate(fromday, fromtime);
        Date searchdateto = WmsFormatter.getSearchFromDate(today, totime);

        inparam.set(LstPCTWeightDistinctionListParams.FROM_OCCUR_DAY, searchdatefrom);
        inparam.set(LstPCTWeightDistinctionListParams.TO_OCCUR_DAY, searchdateto);
        // DFKLOOK:ここまで修正

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_Display_Click");
        redirect("/pcart/master/listbox/pctweightdistinctionlist/LstPCTWeightDistinctionList.do", forwardParam,
                "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_Display_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_Print_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);
        txt_ItemCode.validate(this, false);
        txt_SearchDate.validate(this, false);
        txt_SearchTime.validate(this, false);
        txt_ToSearchDate.validate(this, false);
        txt_ToSearchTime.validate(this, false);

        // DFKLOOK:ここから修正
        // 入力チェック        
        if (!checkInput())
        {
            return;
        }
        // DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTWeightDistinctionListDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new PCTWeightDistinctionListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            PCTWeightDistinctionListDASCHParams inparam = new PCTWeightDistinctionListDASCHParams();

            // DFKLOOK:ここから修正
            // 補完済の日付、時刻を求める
            Date searchdatefrom = null;
            Date searchdateto = null;
            if (!StringUtil.isBlank(txt_SearchDate.getText()) && !StringUtil.isBlank(txt_ToSearchDate.getText()))
            {
                Date[] tmp =
                        WmsFormatter.getFromTo(txt_SearchDate.getDate(), txt_SearchTime.getTime(),
                                txt_ToSearchDate.getDate(), txt_ToSearchTime.getTime());
                txt_SearchDate.setText(WmsFormatter.toDispDate(tmp[0], locale));
                txt_SearchTime.setText(WmsFormatter.toDispTime(tmp[0], locale));
                txt_ToSearchDate.setText(WmsFormatter.toDispDate(tmp[1], locale));
                txt_ToSearchTime.setText(WmsFormatter.toDispTime(tmp[1], locale));
                String fromday = WmsFormatter.toParamDate(txt_SearchDate.getDate());
                String fromtime = WmsFormatter.toParamTime(txt_SearchTime.getTime());
                String today = WmsFormatter.toParamDate(txt_ToSearchDate.getDate());
                String totime = WmsFormatter.toParamTime(txt_ToSearchTime.getTime());
                searchdatefrom = WmsFormatter.getSearchFromDate(fromday, fromtime);
                searchdateto = WmsFormatter.getSearchFromDate(today, totime);
            }
            Date[] fromTo = WmsFormatter.getFromTo(searchdatefrom, searchdateto);

            inparam.set(PCTWeightDistinctionListDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTWeightDistinctionListDASCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(PCTWeightDistinctionListDASCHParams.SEARCH_DATE, txt_SearchDate.getValue());
            inparam.set(PCTWeightDistinctionListDASCHParams.SEARCH_TIME, txt_SearchTime.getValue());
            inparam.set(PCTWeightDistinctionListDASCHParams.TO_SEARCH_DATE, txt_ToSearchDate.getValue());
            inparam.set(PCTWeightDistinctionListDASCHParams.TO_SEARCH_TIME, txt_ToSearchTime.getValue());
            inparam.set(PCTWeightDistinctionListDASCHParams.FROM_OCCUR_DAY, fromTo[0]);
            inparam.set(PCTWeightDistinctionListDASCHParams.TO_OCCUR_DAY, fromTo[1]);
            // DFKLOOK:ここまで修正

            // check count.
            int count = dasch.count(inparam);
            if (confirm && count > 0)
            {
                // show confirm message.
                this.setConfirm("MSG-W0018\t" + Formatter.getNumFormat(count), false, true);
                viewState.setString(_KEY_CONFIRMSOURCE, "btn_Print_Click");
                return;
            }
            else if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("PctWeightDistinctionList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PCTWeightDistinctionListDASCHParams expparam = new PCTWeightDistinctionListDASCHParams();
                expparam.set(PctWeightDistinctionListParams.CONSIGNOR_CODE,
                        outparam.get(PCTWeightDistinctionListDASCHParams.CONSIGNOR_CODE));
                expparam.set(PctWeightDistinctionListParams.CONSIGNOR_NAME,
                        outparam.get(PCTWeightDistinctionListDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctWeightDistinctionListParams.SYS_DAY, outparam.get(PCTWeightDistinctionListDASCHParams.SYS_DAY));
                expparam.set(PctWeightDistinctionListParams.SYS_TIME, outparam.get(PCTWeightDistinctionListDASCHParams.SYS_TIME));
                expparam.set(PctWeightDistinctionListParams.ITEM_CODE, outparam.get(PCTWeightDistinctionListDASCHParams.ITEM_CODE));
                expparam.set(PctWeightDistinctionListParams.ITEM_NAME, outparam.get(PCTWeightDistinctionListDASCHParams.ITEM_NAME));
                expparam.set(PctWeightDistinctionListParams.LOT_ENTERING_QTY,
                        outparam.get(PCTWeightDistinctionListDASCHParams.LOT_ENTERING_QTY));
                expparam.set(PctWeightDistinctionListParams.SINGLE_WEIGHT,
                        outparam.get(PCTWeightDistinctionListDASCHParams.SINGLE_WEIGHT));
                expparam.set(PctWeightDistinctionListParams.INSPECT_WEIGHT,
                        outparam.get(PCTWeightDistinctionListDASCHParams.INSPECT_WEIGHT));
                expparam.set(PctWeightDistinctionListParams.INSPECT_QTY, outparam.get(PCTWeightDistinctionListDASCHParams.INSPECT_QTY));
                expparam.set(PctWeightDistinctionListParams.CORRECT_QTY, outparam.get(PCTWeightDistinctionListDASCHParams.CORRECT_QTY));
                expparam.set(PctWeightDistinctionListParams.CORRECT_WEIGHT,
                        outparam.get(PCTWeightDistinctionListDASCHParams.CORRECT_WEIGHT));
                expparam.set(PctWeightDistinctionListParams.DIFFERENCE_WEIGHT,
                        outparam.get(PCTWeightDistinctionListDASCHParams.DIFFERENCE));
                expparam.set(PctWeightDistinctionListParams.DATE, outparam.get(PCTWeightDistinctionListDASCHParams.OCCUR_DAY));
                expparam.set(PctWeightDistinctionListParams.TIME, outparam.get(PCTWeightDistinctionListDASCHParams.OCCUR_TIME));
                expparam.set(PctWeightDistinctionListParams.TERMINAL_NO, outparam.get(PCTWeightDistinctionListDASCHParams.TERMINAL_NO));
                expparam.set(PctWeightDistinctionListParams.LOCATION_NO, outparam.get(PCTWeightDistinctionListDASCHParams.LOCATION_NO));
                expparam.set(PctWeightDistinctionListParams.USER_ID, outparam.get(PCTWeightDistinctionListDASCHParams.USER_ID));
                expparam.set(PctWeightDistinctionListParams.USER_NAME, outparam.get(PCTWeightDistinctionListDASCHParams.USER_NAME));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // execute print.
            try
            {
                exporter.print();
                message.setMsgResourceKey("6001010");
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                message.setMsgResourceKey("6007034");
                return;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_XLS_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);
        txt_ItemCode.validate(this, false);
        txt_SearchDate.validate(this, false);
        txt_SearchTime.validate(this, false);
        txt_ToSearchDate.validate(this, false);
        txt_ToSearchTime.validate(this, false);

        // DFKLOOK:ここから修正
        // 入力チェック        
        if (!checkInput())
        {
            return;
        }
        // DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTWeightDistinctionListDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new PCTWeightDistinctionListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            PCTWeightDistinctionListDASCHParams inparam = new PCTWeightDistinctionListDASCHParams();

            // DFKLOOK:ここから修正
            // 補完済の日付、時刻を求める
            Date searchdatefrom = null;
            Date searchdateto = null;
            if (!StringUtil.isBlank(txt_SearchDate.getText()) && !StringUtil.isBlank(txt_ToSearchDate.getText()))
            {

                Date[] tmp =
                        WmsFormatter.getFromTo(txt_SearchDate.getDate(), txt_SearchTime.getTime(),
                                txt_ToSearchDate.getDate(), txt_ToSearchTime.getTime());
                txt_SearchDate.setText(WmsFormatter.toDispDate(tmp[0], locale));
                txt_SearchTime.setText(WmsFormatter.toDispTime(tmp[0], locale));
                txt_ToSearchDate.setText(WmsFormatter.toDispDate(tmp[1], locale));
                txt_ToSearchTime.setText(WmsFormatter.toDispTime(tmp[1], locale));
                String fromday = WmsFormatter.toParamDate(txt_SearchDate.getDate());
                String fromtime = WmsFormatter.toParamTime(txt_SearchTime.getTime());
                String today = WmsFormatter.toParamDate(txt_ToSearchDate.getDate());
                String totime = WmsFormatter.toParamTime(txt_ToSearchTime.getTime());
                searchdatefrom = WmsFormatter.getSearchFromDate(fromday, fromtime);
                searchdateto = WmsFormatter.getSearchFromDate(today, totime);
            }
            Date[] fromTo = WmsFormatter.getFromTo(searchdatefrom, searchdateto);

            inparam.set(PCTWeightDistinctionListDASCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTWeightDistinctionListDASCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(PCTWeightDistinctionListDASCHParams.SEARCH_DATE, txt_SearchDate.getValue());
            inparam.set(PCTWeightDistinctionListDASCHParams.SEARCH_TIME, txt_SearchTime.getValue());
            inparam.set(PCTWeightDistinctionListDASCHParams.TO_SEARCH_DATE, txt_ToSearchDate.getValue());
            inparam.set(PCTWeightDistinctionListDASCHParams.TO_SEARCH_TIME, txt_ToSearchTime.getValue());
            inparam.set(PCTWeightDistinctionListDASCHParams.FROM_OCCUR_DAY, fromTo[0]);
            inparam.set(PCTWeightDistinctionListDASCHParams.TO_OCCUR_DAY, fromTo[1]);
            // DFKLOOK:ここまで修正

            // check count.
            int count = dasch.count(inparam);
            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newExcelExporter("PctWeightDistinctionList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                PctWeightDistinctionListParams expparam = new PctWeightDistinctionListParams();
                expparam.set(PctWeightDistinctionListParams.CONSIGNOR_CODE,
                        outparam.get(PCTWeightDistinctionListDASCHParams.CONSIGNOR_CODE));
                expparam.set(PctWeightDistinctionListParams.CONSIGNOR_NAME,
                        outparam.get(PCTWeightDistinctionListDASCHParams.CONSIGNOR_NAME));
                expparam.set(PctWeightDistinctionListParams.ITEM_CODE,
                        outparam.get(PCTWeightDistinctionListDASCHParams.ITEM_CODE));
                expparam.set(PctWeightDistinctionListParams.ITEM_NAME,
                        outparam.get(PCTWeightDistinctionListDASCHParams.ITEM_NAME));
                expparam.set(PctWeightDistinctionListParams.LOT_ENTERING_QTY,
                        outparam.get(PCTWeightDistinctionListDASCHParams.LOT_ENTERING_QTY));
                expparam.set(PctWeightDistinctionListParams.SINGLE_WEIGHT,
                        outparam.get(PCTWeightDistinctionListDASCHParams.SINGLE_WEIGHT));
                expparam.set(PctWeightDistinctionListParams.INSPECT_WEIGHT,
                        outparam.get(PCTWeightDistinctionListDASCHParams.INSPECT_WEIGHT));
                expparam.set(PctWeightDistinctionListParams.INSPECT_QTY,
                        outparam.get(PCTWeightDistinctionListDASCHParams.INSPECT_QTY));
                expparam.set(PctWeightDistinctionListParams.CORRECT_QTY,
                        outparam.get(PCTWeightDistinctionListDASCHParams.CORRECT_QTY));
                expparam.set(PctWeightDistinctionListParams.CORRECT_WEIGHT,
                        outparam.get(PCTWeightDistinctionListDASCHParams.CORRECT_WEIGHT));
                expparam.set(PctWeightDistinctionListParams.DIFFERENCE_WEIGHT,
                        outparam.get(PCTWeightDistinctionListDASCHParams.DIFFERENCE));
                expparam.set(PctWeightDistinctionListParams.DATE,
                        outparam.get(PCTWeightDistinctionListDASCHParams.OCCUR_DAY));
                expparam.set(PctWeightDistinctionListParams.TIME,
                        outparam.get(PCTWeightDistinctionListDASCHParams.OCCUR_TIME));
                expparam.set(PctWeightDistinctionListParams.TERMINAL_NO,
                        outparam.get(PCTWeightDistinctionListDASCHParams.TERMINAL_NO));
                expparam.set(PctWeightDistinctionListParams.LOCATION_NO,
                        outparam.get(PCTWeightDistinctionListDASCHParams.LOCATION_NO));
                expparam.set(PctWeightDistinctionListParams.USER_ID,
                        outparam.get(PCTWeightDistinctionListDASCHParams.USER_ID));
                expparam.set(PctWeightDistinctionListParams.USER_NAME,
                        outparam.get(PCTWeightDistinctionListDASCHParams.USER_NAME));
                if (!exporter.write(expparam))
                {
                    //DFKLOOK start
                    // XLS最大件数出力メッセージ対応
                    message.setMsgResourceKey("6001031\t"
                            + Formatter.getNumFormat(count) + "\t"
                            + Formatter.getNumFormat(CommonParam.getIntParam("MAX_NUMBER_OF_XLS")
                                    - exporter.getHeaderRowsCount()));
                    //DFKLOOK end
                    break;
                }
            }

            // redirect.
            download(downloadFile.getPath());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_ConsignorCode.setValue(null);
        txt_ItemCode.setValue(null);
        txt_SearchDate.setValue(null);
        txt_SearchTime.setValue(null);
        txt_ToSearchDate.setValue(null);
        txt_ToSearchTime.setValue(null);

        // DFKLOOK:ここから修正
        page_Load_Process();
        // DFKLOOK:ここまで修正
    }

    /**
     * 画面タイトルを設定します。
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void setTitle()
            throws Exception
    {
        // httpRequestからメニュー用パラメータを取得する
        String menuparam = this.getHttpRequest().getParameter(Constants.MENUPARAM);
        if (menuparam != null)
        {
            String title = CollectionUtils.getMenuParam(0, menuparam);
            String functionID = CollectionUtils.getMenuParam(1, menuparam);
            String menuID = CollectionUtils.getMenuParam(2, menuparam);

            // ViewStateへ保存する
            viewState.setString(Constants.M_TITLE_KEY, title);
            viewState.setString(Constants.M_FUNCTIONID_KEY, functionID);
            viewState.setString(Constants.M_MENUID_KEY, menuID);

            lbl_SettingName.setResourceKey(title);
        }
        else if (this.getTitleResourceKey() != null && !this.getTitleResourceKey().equals(""))
        {
            // リストボックスの場合はpage.xmlから取得する
            lbl_SettingName.setResourceKey(this.getTitleResourceKey());
        }
        else if (viewState.getString(Constants.M_TITLE_KEY) != null)
        {
            // 2画面遷移から戻ってきた場合はViewStateから取得する
            lbl_SettingName.setResourceKey(viewState.getString(Constants.M_TITLE_KEY));
        }
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * Returns current repository info for this class
     *
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }
}
//end of class
