// $Id: ShipLoadBusiness.java,v 1.3 2009/02/24 02:42:24 ose Exp $
package jp.co.daifuku.wms.ship.display.load;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.ShipHostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoHandler;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.ship.dasch.ShipLoadDASCH;
import jp.co.daifuku.wms.ship.dasch.ShipLoadDASCHParams;
import jp.co.daifuku.wms.ship.exporter.ShipLoadListParams;
import jp.co.daifuku.wms.ship.schedule.ShipLoadSCH;
import jp.co.daifuku.wms.ship.schedule.ShipLoadSCHParams;

/**
 * 出荷積込の画面処理を行います。
 *
 * @version $Revision: 1.3 $, $Date: 2009/02/24 02:42:24 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class ShipLoadBusiness
        extends ShipLoad
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    // DFKLOOK start
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK end

    /** lst_ShipLoading(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_ShipLoading(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE =
            new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_ShipLoading(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME =
            new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_ShipLoading(LST_TICKET_NO) */
    private static final ListCellKey KEY_LST_TICKET_NO =
            new ListCellKey("LST_TICKET_NO", new StringCellColumn(), true, false);

    /** lst_ShipLoading kyes */
    private static final ListCellKey[] LST_SHIPLOADING_KEYS = {
            KEY_LST_SELECT,
            KEY_LST_CUSTOMER_CODE,
            KEY_LST_CUSTOMER_NAME,
            KEY_LST_TICKET_NO,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_ShipLoading */
    private ListCellModel _lcm_lst_ShipLoading;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public ShipLoadBusiness()
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
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        // DFKLOOK start
		// get event source.
		String eventSource = viewState.getString(_KEY_CONFIRMSOURCE);
		if(eventSource == null)
		{
			return;
		}

		// remove event source.
		viewState.remove(_KEY_CONFIRMSOURCE);

		// check result.
		boolean isExecute = new Boolean(String.valueOf(e.getEventArgs().get(0))).booleanValue();
		if(!isExecute)
		{
			return;
		}

		// choose process.
		if(eventSource.startsWith("btn_Complete_Click"))
		{
			// process call.
			btn_Complete_Click_Process(eventSource);
		}
        // DFKLOOK end
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
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Clear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Complete_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Complete_Click_Process(null);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllCheck_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllCheck_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllCheckClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllCheckClear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllClear_Click_Process();
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
        // get locale.
        Locale locale = httpRequest.getLocale();

        // initialize lst_ShipLoading.
        _lcm_lst_ShipLoading = new ListCellModel(lst_ShipLoading, LST_SHIPLOADING_KEYS, locale);
        _lcm_lst_ShipLoading.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_ShipLoading.setToolTipVisible(KEY_LST_CUSTOMER_CODE, false);
        _lcm_lst_ShipLoading.setToolTipVisible(KEY_LST_CUSTOMER_NAME, false);
        _lcm_lst_ShipLoading.setToolTipVisible(KEY_LST_TICKET_NO, false);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_ShipLoading_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_PlanDate);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_Complete.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        txt_RPlanDate.setValue(null);
        txt_RPlanDate.setReadOnly(true);
        txt_BerthNo.setValue(null);
        txt_BerthNo.setReadOnly(true);
        _lcm_lst_ShipLoading.clear();

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_PlanDate.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_Route.validate(this, false);
        txt_CustomerCode.validate(this, false);
        txt_ToCustomerCode.validate(this, false);
        txt_TicketNo.validate(this, false);
        txt_ToTicketNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ShipLoadSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ShipLoadSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            ShipLoadSCHParams inparam = new ShipLoadSCHParams();
            inparam.set(ShipLoadSCHParams.PLAN_DAY, txt_PlanDate.getValue());
            inparam.set(ShipLoadSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(ShipLoadSCHParams.ROUTE, txt_Route.getValue());
            inparam.set(ShipLoadSCHParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
            inparam.set(ShipLoadSCHParams.TO_CUSTOMER_CODE, txt_ToCustomerCode.getValue());
            inparam.set(ShipLoadSCHParams.TICKET_NO, txt_TicketNo.getValue());
            inparam.set(ShipLoadSCHParams.TO_TICKET_NO, txt_ToTicketNo.getValue());
            inparam.set(ShipLoadSCHParams.ONLY_COMPLETED_INSPECTION, chk_OnlyCompletedInspection.getValue());
            inparam.set(ShipLoadSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK ここから修正
            if (outparams.size() == 0 || outparams == null)
            {
                // clear.
                _lcm_lst_ShipLoading.clear();
                btn_Complete.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_AllClear.setEnabled(false);
                txt_RPlanDate.setValue(null);
                txt_BerthNo.setValue(null);
                txt_BerthNo.setReadOnly(true);
                return;
            }
            // DFKLOOK ここまで修正

            // output display.
            _lcm_lst_ShipLoading.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ShipLoading.getNewLine();
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(ShipLoadSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(ShipLoadSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_TICKET_NO, outparam.get(ShipLoadSCHParams.TICKET_NO));
                viewState.setObject(ViewStateKeys.PLAN_DAY, txt_PlanDate.getValue());
                viewState.setObject(ViewStateKeys.BATCH_NO, txt_BatchNo.getValue());
                viewState.setObject(ViewStateKeys.ROUTE, txt_Route.getValue());
                viewState.setObject(ViewStateKeys.CUSTOMER_CODE, txt_CustomerCode.getValue());
                viewState.setObject(ViewStateKeys.TO_CUSTOMER_CODE, txt_ToCustomerCode.getValue());
                viewState.setObject(ViewStateKeys.TICKET_NO, txt_TicketNo.getValue());
                viewState.setObject(ViewStateKeys.TO_TICKET_NO, txt_ToTicketNo.getValue());
                viewState.setObject(ViewStateKeys.ONLY_COMPLETED_INSPECTION, chk_OnlyCompletedInspection.getValue());
                txt_RPlanDate.setValue(txt_PlanDate.getValue());
                lst_ShipLoading_SetLineToolTip(line);
                _lcm_lst_ShipLoading.add(line);
            }

            // clear.
            btn_Complete.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_AllClear.setEnabled(true);
            txt_BerthNo.setReadOnly(false);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_PlanDate.setValue(null);
        txt_BatchNo.setValue(null);
        txt_Route.setValue(null);
        txt_CustomerCode.setValue(null);
        txt_ToCustomerCode.setValue(null);
        txt_TicketNo.setValue(null);
        txt_ToTicketNo.setValue(null);
        chk_OnlyCompletedInspection.setChecked(false);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Complete_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        txt_BerthNo.validate(this, true);

        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_ShipLoading.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_ShipLoading.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003001");
            return;
        }

        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
        	// 完了しますか？
            this.setConfirm("MSG-W0043", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_Complete_Click");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ShipLoadSCH sch = null;

        ShipLoadDASCH dasch = null;
        PrintExporter exporter = null;

        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ShipLoadSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ShipLoading.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ShipLoading.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                ShipLoadSCHParams lineparam = new ShipLoadSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(ShipLoadSCHParams.PLAN_DAY, txt_RPlanDate.getValue());
                lineparam.set(ShipLoadSCHParams.BERTH_NO, txt_BerthNo.getValue());
                lineparam.set(ShipLoadSCHParams.CUSTOMER_CODE, line.getValue(KEY_LST_CUSTOMER_CODE));
                lineparam.set(ShipLoadSCHParams.TICKET_NO, line.getValue(KEY_LST_TICKET_NO));
                lineparam.set(ShipLoadSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.BATCH_NO));
                lineparam.set(ShipLoadSCHParams.ONLY_COMPLETED_INSPECTION,
                        viewState.getObject(ViewStateKeys.ONLY_COMPLETED_INSPECTION));
                lineparam.set(ShipLoadSCHParams.SETTING_UNIT_KEY, "");
                lineparam.set(ShipLoadSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highligiting error row.
                _lcm_lst_ShipLoading.resetEditRow();
                _lcm_lst_ShipLoading.resetHighlight();
                _lcm_lst_ShipLoading.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_ShipLoading.size(); i++)
            {
                ListCellLine line = _lcm_lst_ShipLoading.get(i);
                lst_ShipLoading.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(txt_RPlanDate.getStringValue(), "");
                part11List.add(txt_BerthNo.getStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_CUSTOMER_CODE), "");
                part11List.add(line.getViewString(KEY_LST_TICKET_NO), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK ここから修正
            // 統計情報の取得を行います。
            ShipWorkInfoHandler shipWorkInfoHandler = new ShipWorkInfoHandler(conn);
            ShipPlanHandler shipPlanHandler = new ShipPlanHandler(conn);
            ShipHostSendHandler shipHostSendHandler = new ShipHostSendHandler(conn);
            shipWorkInfoHandler.getStatics();
            shipPlanHandler.getStatics();
            shipHostSendHandler.getStatics();

            // 帳票発行追加
            if (inparams.length != 0)
            {
                // open exporter.
                ExporterFactory factory = new WmsExporterFactory(locale, ui);
                exporter = factory.newPrinterExporter("ShipLoadList", false);
                exporter.open();

                ShipLoadDASCHParams inparam = null;
                int count = 0;

                for (int i = 0; i < inparams.length; i++)
                {
                    dasch = new ShipLoadDASCH(conn, this.getClass(), locale, ui);
                    dasch.setForwardOnly(true);

                    // set input parameters.
                    inparam = new ShipLoadDASCHParams();
                    inparam.set(ShipLoadDASCHParams.SETTING_UNIT_KEY,
                            inparams[i].getString(ShipLoadSCHParams.SETTING_UNIT_KEY));

                    count = dasch.count(inparam);

                    if (count == 0)
                    {
                        message.setMsgResourceKey("6003011");
                    }
                    else
                    {
                        // DASCH call.
                        dasch.search(inparam);

                        while (dasch.next())
                        {
                            Params outparam = dasch.get();
                            ShipLoadListParams expparam = new ShipLoadListParams();
                            expparam.set(ShipLoadListParams.DFK_DS_NO, outparam.get(ShipLoadDASCHParams.DFK_DS_NO));
                            expparam.set(ShipLoadListParams.DFK_USER_ID, outparam.get(ShipLoadDASCHParams.DFK_USER_ID));
                            expparam.set(ShipLoadListParams.DFK_USER_NAME, outparam.get(ShipLoadDASCHParams.DFK_USER_NAME));
                            expparam.set(ShipLoadListParams.SYS_DAY, outparam.get(ShipLoadDASCHParams.SYS_DAY));
                            expparam.set(ShipLoadListParams.SYS_TIME, outparam.get(ShipLoadDASCHParams.SYS_TIME));
                            expparam.set(ShipLoadListParams.PLAN_DATE, outparam.get(ShipLoadDASCHParams.PLAN_DATE));
                            expparam.set(ShipLoadListParams.CUSTOMER_CODE, outparam.get(ShipLoadDASCHParams.CUSTOMER_CODE));
                            expparam.set(ShipLoadListParams.CUSTOMER_NAME, outparam.get(ShipLoadDASCHParams.CUSTOMER_NAME));
                            expparam.set(ShipLoadListParams.POSTAL_CODE, outparam.get(ShipLoadDASCHParams.POSTAL_CODE));
                            expparam.set(ShipLoadListParams.PREFECTURE, outparam.get(ShipLoadDASCHParams.PREFECTURE));
                            expparam.set(ShipLoadListParams.ADDRESS, outparam.get(ShipLoadDASCHParams.ADDRESS));
                            expparam.set(ShipLoadListParams.ADDRESS2, outparam.get(ShipLoadDASCHParams.ADDRESS2));
                            expparam.set(ShipLoadListParams.TELEPHONE, outparam.get(ShipLoadDASCHParams.TELEPHONE));
                            expparam.set(ShipLoadListParams.BATCH_NO, outparam.get(ShipLoadDASCHParams.BATCH_NO));
                            expparam.set(ShipLoadListParams.ROUTE, outparam.get(ShipLoadDASCHParams.ROUTE));
                            expparam.set(ShipLoadListParams.BERTH_NO, outparam.get(ShipLoadDASCHParams.BERTH_NO));
                            expparam.set(ShipLoadListParams.TICKET_NO, outparam.get(ShipLoadDASCHParams.TICKET_NO));
                            expparam.set(ShipLoadListParams.LINE_NO, outparam.get(ShipLoadDASCHParams.LINE_NO));
                            expparam.set(ShipLoadListParams.ITEM_CODE, outparam.get(ShipLoadDASCHParams.ITEM_CODE));
                            expparam.set(ShipLoadListParams.ITEM_NAME, outparam.get(ShipLoadDASCHParams.ITEM_NAME));
                            expparam.set(ShipLoadListParams.LOT_NO, outparam.get(ShipLoadDASCHParams.LOT_NO));
                            expparam.set(ShipLoadListParams.ENTERING_QTY, outparam.get(ShipLoadDASCHParams.ENTERING_QTY));
                            expparam.set(ShipLoadListParams.SHIPPING_CASE_QTY,
                                    outparam.get(ShipLoadDASCHParams.SHIPPING_CASE_QTY));
                            expparam.set(ShipLoadListParams.SHIPPING_PIECE_QTY,
                                    outparam.get(ShipLoadDASCHParams.SHIPPING_PIECE_QTY));
                            expparam.set(ShipLoadListParams.INSPECTION, outparam.get(ShipLoadDASCHParams.INSPECTION));
                            if (!exporter.write(expparam))
                            {
                                break;
                            }
                        }
                    }
                }

                exporter.print();
            }
            // DFKLOOK ここまで修正

            // reset editing row.
            _lcm_lst_ShipLoading.resetEditRow();
            _lcm_lst_ShipLoading.resetHighlight();

            // set input parameters.
            ShipLoadSCHParams inparam = new ShipLoadSCHParams();
            inparam.set(ShipLoadSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.BATCH_NO));
            inparam.set(ShipLoadSCHParams.CUSTOMER_CODE, viewState.getObject(ViewStateKeys.CUSTOMER_CODE));
            inparam.set(ShipLoadSCHParams.ONLY_COMPLETED_INSPECTION,
                    viewState.getObject(ViewStateKeys.ONLY_COMPLETED_INSPECTION));
            inparam.set(ShipLoadSCHParams.PLAN_DAY, viewState.getObject(ViewStateKeys.PLAN_DAY));
            inparam.set(ShipLoadSCHParams.ROUTE, viewState.getObject(ViewStateKeys.ROUTE));
            inparam.set(ShipLoadSCHParams.TICKET_NO, viewState.getObject(ViewStateKeys.TICKET_NO));
            inparam.set(ShipLoadSCHParams.TO_CUSTOMER_CODE, viewState.getObject(ViewStateKeys.TO_CUSTOMER_CODE));
            inparam.set(ShipLoadSCHParams.TO_TICKET_NO, viewState.getObject(ViewStateKeys.TO_TICKET_NO));
            inparam.set(ShipLoadSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // DFKLOOK ここから修正
            if (outparams.size() == 0 || outparams == null)
            {
                // clear.
                _lcm_lst_ShipLoading.clear();
                btn_Complete.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_AllClear.setEnabled(false);
                txt_RPlanDate.setValue(null);
                txt_BerthNo.setValue(null);
                txt_BerthNo.setReadOnly(true);
                return;
            }
            // DFKLOOK ここまで修正

            // output display.
            _lcm_lst_ShipLoading.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ShipLoading.getNewLine();
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(ShipLoadSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(ShipLoadSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_TICKET_NO, outparam.get(ShipLoadSCHParams.TICKET_NO));
                lst_ShipLoading_SetLineToolTip(line);
                _lcm_lst_ShipLoading.add(line);
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

            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllCheck_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_ShipLoading.size(); i++)
        {
            ListCellLine clearline = _lcm_lst_ShipLoading.get(i);
            lst_ShipLoading.setCurrentRow(i);
            clearline.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_ShipLoading_SetLineToolTip(clearline);
            _lcm_lst_ShipLoading.set(i, clearline);
        }

    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllCheckClear_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_ShipLoading.size(); i++)
        {
            ListCellLine clearline = _lcm_lst_ShipLoading.get(i);
            lst_ShipLoading.setCurrentRow(i);
            clearline.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_ShipLoading_SetLineToolTip(clearline);
            _lcm_lst_ShipLoading.set(i, clearline);
        }

    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_ShipLoading.clear();
        btn_Complete.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        txt_RPlanDate.setValue(null);
        txt_BerthNo.setValue(null);
        txt_BerthNo.setReadOnly(true);

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
