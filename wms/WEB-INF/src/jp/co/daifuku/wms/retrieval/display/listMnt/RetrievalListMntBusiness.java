// $Id: RetrievalListMntBusiness.java,v 1.2 2009/02/24 02:38:50 ose Exp $
package jp.co.daifuku.wms.retrieval.display.listMnt;

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
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
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
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.retrieval.dasch.RetrievalListStartDASCH;
import jp.co.daifuku.wms.retrieval.dasch.RetrievalListStartDASCHParams;
import jp.co.daifuku.wms.retrieval.exporter.RetrievalStartListParams;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalListMntSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalListMntSCHParams;

/**
 * 出庫リスト作業メンテナンスの画面処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:38:50 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class RetrievalListMntBusiness
        extends RetrievalListMnt
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";
    // DFKLOOK:ここから修正    
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK:ここまで修正

    /** lst_RetrievalListMaintenance(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_RetrievalListMaintenance(LST_SETTING_UNIT_KEY) */
    private static final ListCellKey KEY_LST_SETTING_UNIT_KEY = new ListCellKey("LST_SETTING_UNIT_KEY", new StringCellColumn(), true, false);

    /** lst_RetrievalListMaintenance(LST_PLAN_DAY) */
    private static final ListCellKey KEY_LST_PLAN_DAY = new ListCellKey("LST_PLAN_DAY", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG, DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_RetrievalListMaintenance(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO = new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_RetrievalListMaintenance(LST_ORDER_NO) */
    private static final ListCellKey KEY_LST_ORDER_NO = new ListCellKey("LST_ORDER_NO", new StringCellColumn(), true, false);

    /** lst_RetrievalListMaintenance(LST_PLAN_AREA_NO) */
    private static final ListCellKey KEY_LST_PLAN_AREA_NO = new ListCellKey("LST_PLAN_AREA_NO", new StringCellColumn(), true, false);

    /** lst_RetrievalListMaintenance(LST_AREA_NAME) */
    private static final ListCellKey KEY_LST_AREA_NAME = new ListCellKey("LST_AREA_NAME", new StringCellColumn(), true, false);

    /** lst_RetrievalListMaintenance(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE = new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_RetrievalListMaintenance(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME = new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_RetrievalListMaintenance kyes */
    private static final ListCellKey[] LST_RETRIEVALLISTMAINTENANCE_KEYS = {
        KEY_LST_SELECT,
        KEY_LST_SETTING_UNIT_KEY,
        KEY_LST_PLAN_DAY,
        KEY_LST_BATCH_NO,
        KEY_LST_ORDER_NO,
        KEY_LST_PLAN_AREA_NO,
        KEY_LST_CUSTOMER_CODE,
        KEY_LST_AREA_NAME,
        KEY_LST_CUSTOMER_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_RetrievalListMaintenance */
    private ListCellModel _lcm_lst_RetrievalListMaintenance;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RetrievalListMntBusiness()
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
    public void page_ConfirmBack(ActionEvent e) throws Exception
    {
        // DFKLOOK:ここから修正
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
		if (eventSource.startsWith("btn_WorkCancel_Click"))
		{
			btn_WorkCancel_Click_Process(eventSource);
		}
		else if (eventSource.startsWith("btn_RePrint_Click"))
		{
			btn_RePrint_Click_Process(eventSource);
		}
        // DFKLOOK:ここまで修正
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
    public void btn_RePrint_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // process call.
        btn_RePrint_Click_Process(null);
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_WorkCancel_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // process call.
        btn_WorkCancel_Click_Process(null);
        // DFKLOOK:ここまで修正
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
    public void btn_ListClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ListClear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_RetrievalListMaintenance_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_RetrievalListMaintenance_ColumClick(ActionEvent e)
            throws Exception
    {
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

        // initialize lst_RetrievalListMaintenance.
        _lcm_lst_RetrievalListMaintenance = new ListCellModel(lst_RetrievalListMaintenance, LST_RETRIEVALLISTMAINTENANCE_KEYS, locale);
        _lcm_lst_RetrievalListMaintenance.setToolTipVisible(KEY_LST_SELECT, true);
        _lcm_lst_RetrievalListMaintenance.setToolTipVisible(KEY_LST_SETTING_UNIT_KEY, true);
        _lcm_lst_RetrievalListMaintenance.setToolTipVisible(KEY_LST_PLAN_DAY, true);
        _lcm_lst_RetrievalListMaintenance.setToolTipVisible(KEY_LST_BATCH_NO, true);
        _lcm_lst_RetrievalListMaintenance.setToolTipVisible(KEY_LST_ORDER_NO, true);
        _lcm_lst_RetrievalListMaintenance.setToolTipVisible(KEY_LST_PLAN_AREA_NO, true);
        _lcm_lst_RetrievalListMaintenance.setToolTipVisible(KEY_LST_AREA_NAME, true);
        _lcm_lst_RetrievalListMaintenance.setToolTipVisible(KEY_LST_CUSTOMER_CODE, true);
        _lcm_lst_RetrievalListMaintenance.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);

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
    private void dispose()
            throws Exception
    {
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_RetrievalListMaintenance_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0117", KEY_LST_AREA_NAME);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_RetrievalPlanDate);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_RePrint.setEnabled(false);
        btn_WorkCancel.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_ListClear.setEnabled(false);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_RetrievalPlanDate.validate(this, false);
        txt_BatchNo.validate(this, false);
        txt_OrderNo.validate(this, false);
        txt_To_OrderNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalListMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalListMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            // DFKLOOK コンストラクタにUserInfoをセット
            RetrievalListMntSCHParams inparam = new RetrievalListMntSCHParams(ui);
            // DFKLOOK ここまで修正
            inparam.set(RetrievalListMntSCHParams.PLAN_DAY, txt_RetrievalPlanDate.getValue());
            inparam.set(RetrievalListMntSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(RetrievalListMntSCHParams.FROM_ORDER_NO, txt_OrderNo.getValue());
            inparam.set(RetrievalListMntSCHParams.TO_ORDER_NO, txt_To_OrderNo.getValue());
            inparam.set(RetrievalListMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RetrievalListMaintenance.clear();
            
            // DFKLOOK ここから修正
            if(outparams == null || outparams.size() <= 0) 
            {
                btn_RePrint.setEnabled(false);
                btn_WorkCancel.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_ListClear.setEnabled(false);
                return;
            }
            // DFKLOOK ここまで修正
            
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RetrievalListMaintenance.getNewLine();
                line.setValue(KEY_LST_SETTING_UNIT_KEY, outparam.get(RetrievalListMntSCHParams.SETTING_UNIT_KEY));
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(RetrievalListMntSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(RetrievalListMntSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(RetrievalListMntSCHParams.ORDER_NO));
                line.setValue(KEY_LST_PLAN_AREA_NO, outparam.get(RetrievalListMntSCHParams.PLAN_AREA_NO));
                line.setValue(KEY_LST_AREA_NAME, outparam.get(RetrievalListMntSCHParams.AREA_NAME));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(RetrievalListMntSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(RetrievalListMntSCHParams.CUSTOMER_NAME));
                viewState.setObject(ViewStateKeys.PLAN_DAY, txt_RetrievalPlanDate.getValue());
                viewState.setObject(ViewStateKeys.BATCH_NO, txt_BatchNo.getValue());
                viewState.setObject(ViewStateKeys.FROM_ORDER_NO, txt_OrderNo.getValue());
                viewState.setObject(ViewStateKeys.TO_ORDER_NO, txt_To_OrderNo.getValue());
                lst_RetrievalListMaintenance_SetLineToolTip(line);
                _lcm_lst_RetrievalListMaintenance.add(line);
            }

            // clear.
            btn_RePrint.setEnabled(true);
            btn_WorkCancel.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_ListClear.setEnabled(true);

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
        txt_RetrievalPlanDate.setValue(null);
        txt_BatchNo.setValue(null);
        txt_OrderNo.setValue(null);
        txt_To_OrderNo.setValue(null);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_RePrint_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_RetrievalListMaintenance.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_RetrievalListMaintenance.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            // DFKLOOK メッセージを修正
        	// データを選択してください
            message.setMsgResourceKey("6123103");
            // DFKLOOK ここまで修正
            return;
        }
        
        // DFKLOOK:ここから修正
        if (StringUtil.isBlank(eventSource))
    	{
            // 印刷しますか？
            this.setConfirm("MSG-W0061", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_RePrint_Click");
            return;    		
    	}
        // DFKLOOK:ここまで修正
        
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalListStartDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new RetrievalListStartDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            main: for (int i = 1; i <= _lcm_lst_RetrievalListMaintenance.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_RetrievalListMaintenance.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                // set input parameters.
                RetrievalListStartDASCHParams inparam = new RetrievalListStartDASCHParams();
                inparam.set(RetrievalListStartDASCHParams.SETTING_UNIT_KEY, line.getValue(KEY_LST_SETTING_UNIT_KEY));

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
                if (exporter == null)
                {
                    ExporterFactory factory = new WmsExporterFactory(locale, ui);
                    exporter = factory.newPrinterExporter("RetrievalStartList", false);
                    exporter.open();
                }

                // export.
                while (dasch.next())
                {
                    Params outparam = dasch.get();
                    RetrievalStartListParams expparam = new RetrievalStartListParams();
                    expparam.set(RetrievalStartListParams.DFK_DS_NO, outparam.get(RetrievalListStartDASCHParams.DFK_DS_NO));
                    expparam.set(RetrievalStartListParams.DFK_USER_ID, outparam.get(RetrievalListStartDASCHParams.DFK_USER_ID));
                    expparam.set(RetrievalStartListParams.DFK_USER_NAME, outparam.get(RetrievalListStartDASCHParams.DFK_USER_NAME));
                    expparam.set(RetrievalStartListParams.JOB_NO, outparam.get(RetrievalListStartDASCHParams.JOB_NO));
                    expparam.set(RetrievalStartListParams.PLAN_DAY, outparam.get(RetrievalListStartDASCHParams.PLAN_DAY));
                    expparam.set(RetrievalStartListParams.BATCH_NO, outparam.get(RetrievalListStartDASCHParams.BATCH_NO));
                    expparam.set(RetrievalStartListParams.SYS_DAY, outparam.get(RetrievalListStartDASCHParams.SYS_DAY));
                    expparam.set(RetrievalStartListParams.SYS_TIME, outparam.get(RetrievalListStartDASCHParams.SYS_TIME));
                    expparam.set(RetrievalStartListParams.ORDER_NO, outparam.get(RetrievalListStartDASCHParams.ORDER_NO));
                    expparam.set(RetrievalStartListParams.CUSTOMER_CODE, outparam.get(RetrievalListStartDASCHParams.CUSTOMER_CODE));
                    expparam.set(RetrievalStartListParams.CUSTOMER_NAME, outparam.get(RetrievalListStartDASCHParams.CUSTOMER_NAME));
                    expparam.set(RetrievalStartListParams.AREA_NO, outparam.get(RetrievalListStartDASCHParams.AREA_NO));
                    expparam.set(RetrievalStartListParams.AREA_NAME, outparam.get(RetrievalListStartDASCHParams.AREA_NAME));
                    expparam.set(RetrievalStartListParams.PLAN_LOCATION_NO, outparam.get(RetrievalListStartDASCHParams.PLAN_LOCATION_NO));
                    expparam.set(RetrievalStartListParams.ITEM_CODE, outparam.get(RetrievalListStartDASCHParams.ITEM_CODE));
                    expparam.set(RetrievalStartListParams.ITEM_NAME, outparam.get(RetrievalListStartDASCHParams.ITEM_NAME));
                    expparam.set(RetrievalStartListParams.PLAN_LOT_NO, outparam.get(RetrievalListStartDASCHParams.PLAN_LOT_NO));
                    expparam.set(RetrievalStartListParams.ENTERING_QTY, outparam.get(RetrievalListStartDASCHParams.ENTERING_QTY));
                    expparam.set(RetrievalStartListParams.PLAN_CASE_QTY, outparam.get(RetrievalListStartDASCHParams.PLAN_CASE_QTY));
                    expparam.set(RetrievalStartListParams.PLAN_PIECE_QTY, outparam.get(RetrievalListStartDASCHParams.PLAN_PIECE_QTY));
                    if (!exporter.write(expparam))
                    {
                        break main;
                    }
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
            // write part11 log.
            for (int i = 1; i <= _lcm_lst_RetrievalListMaintenance.size(); i++)
            {
                ListCellLine line = _lcm_lst_RetrievalListMaintenance.get(i);
                lst_RetrievalListMaintenance.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_SETTING_UNIT_KEY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_DAY), "");
                part11List.add(line.getViewString(KEY_LST_BATCH_NO), "");
                part11List.add(line.getViewString(KEY_LST_ORDER_NO), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_AREA_NO), "");
                part11List.add(line.getViewString(KEY_LST_CUSTOMER_CODE), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PRINT), part11List);
            }
            // commit.
            conn.commit();

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
    private void btn_WorkCancel_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_RetrievalListMaintenance.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_RetrievalListMaintenance.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            // DFKLOOK メッセージを修正
            message.setMsgResourceKey("6123103");
            // DFKLOOK ここまで修正
            return;
        }
        
        // DFKLOOK:ここから修正
        if (StringUtil.isBlank(eventSource))
    	{
            // 作業のキャンセル処理を実行しますか？
            this.setConfirm("MSG-W0041", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_WorkCancel_Click");
            return;    		
    	}
        // DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalListMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalListMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_RetrievalListMaintenance.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_RetrievalListMaintenance.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                // DFKLOOK コンストラクタにUserInfoをセット
                RetrievalListMntSCHParams lineparam = new RetrievalListMntSCHParams(ui);
                // DFKLOOK ここまで修正
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(RetrievalListMntSCHParams.SETTING_UNIT_KEY, line.getValue(KEY_LST_SETTING_UNIT_KEY));
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
                _lcm_lst_RetrievalListMaintenance.resetEditRow();
                _lcm_lst_RetrievalListMaintenance.resetHighlight();
                _lcm_lst_RetrievalListMaintenance.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_RetrievalListMaintenance.size(); i++)
            {
                ListCellLine line = _lcm_lst_RetrievalListMaintenance.get(i);
                lst_RetrievalListMaintenance.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_SETTING_UNIT_KEY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_DAY), "");
                part11List.add(line.getViewString(KEY_LST_BATCH_NO), "");
                part11List.add(line.getViewString(KEY_LST_ORDER_NO), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_AREA_NO), "");
                part11List.add(line.getViewString(KEY_LST_CUSTOMER_CODE), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_RetrievalListMaintenance.resetEditRow();
            _lcm_lst_RetrievalListMaintenance.resetHighlight();

            // set input parameters.
            // DFKLOOK コンストラクタにUserInfoをセット
            RetrievalListMntSCHParams inparam = new RetrievalListMntSCHParams(ui);
            // DFKLOOK ここまで修正
            inparam.set(RetrievalListMntSCHParams.PLAN_DAY, viewState.getObject(ViewStateKeys.PLAN_DAY));
            inparam.set(RetrievalListMntSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.BATCH_NO));
            inparam.set(RetrievalListMntSCHParams.FROM_ORDER_NO, viewState.getObject(ViewStateKeys.FROM_ORDER_NO));
            inparam.set(RetrievalListMntSCHParams.TO_ORDER_NO, viewState.getObject(ViewStateKeys.TO_ORDER_NO));
            inparam.set(RetrievalListMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_RetrievalListMaintenance.clear();
            
            // DFKLOOK ここから修正
            if(outparams == null || outparams.size() <= 0) 
            {
                btn_RePrint.setEnabled(false);
                btn_WorkCancel.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_ListClear.setEnabled(false);
                return;
            }
            // DFKLOOK ここまで修正
            
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RetrievalListMaintenance.getNewLine();
                line.setValue(KEY_LST_SETTING_UNIT_KEY, outparam.get(RetrievalListMntSCHParams.SETTING_UNIT_KEY));
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(RetrievalListMntSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(RetrievalListMntSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(RetrievalListMntSCHParams.ORDER_NO));
                line.setValue(KEY_LST_PLAN_AREA_NO, outparam.get(RetrievalListMntSCHParams.PLAN_AREA_NO));
                line.setValue(KEY_LST_AREA_NAME, outparam.get(RetrievalListMntSCHParams.AREA_NAME));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(RetrievalListMntSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(RetrievalListMntSCHParams.CUSTOMER_NAME));
                lst_RetrievalListMaintenance_SetLineToolTip(line);
                _lcm_lst_RetrievalListMaintenance.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
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
        for (int i = 1; i <= _lcm_lst_RetrievalListMaintenance.size(); i++)
        {
            ListCellLine clearline = _lcm_lst_RetrievalListMaintenance.get(i);
            lst_RetrievalListMaintenance.setCurrentRow(i);
            clearline.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_RetrievalListMaintenance_SetLineToolTip(clearline);
            _lcm_lst_RetrievalListMaintenance.set(i, clearline);
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
        for (int i = 1; i <= _lcm_lst_RetrievalListMaintenance.size(); i++)
        {
            ListCellLine clearline = _lcm_lst_RetrievalListMaintenance.get(i);
            lst_RetrievalListMaintenance.setCurrentRow(i);
            clearline.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_RetrievalListMaintenance_SetLineToolTip(clearline);
            _lcm_lst_RetrievalListMaintenance.set(i, clearline);
        }

    }

    /**
     *
     * @throws Exception
     */
    private void btn_ListClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_RetrievalListMaintenance.clear();
        btn_RePrint.setEnabled(false);
        btn_WorkCancel.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_ListClear.setEnabled(false);

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
