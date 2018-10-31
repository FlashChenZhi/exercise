// $Id: WorkMntBusiness.java 7162 2010-02-19 09:32:59Z shibamoto $
package jp.co.daifuku.pcart.retrieval.display.workmnt;

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
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.pcart.base.controller.PCTConsignorController;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.pcart.retrieval.schedule.WorkMntSCH;
import jp.co.daifuku.pcart.retrieval.schedule.WorkMntSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;

/**
 * 作業メンテナンスの画面処理を行います。
 *
 * @version $Revision: 7162 $, $Date:: 2010-02-19 18:32:59 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class WorkMntBusiness
        extends WorkMnt
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
    
    /** lst_workMntList(HIDDEN_JAN) */
    private static final ListCellKey KEY_HIDDEN_JAN =
            new ListCellKey("HIDDEN_JAN", new StringCellColumn(), false, false);

    /** lst_workMntList(HIDDEN_ITF) */
    private static final ListCellKey KEY_HIDDEN_ITF =
            new ListCellKey("HIDDEN_ITF", new StringCellColumn(), false, false);

    /** lst_workMntList(HIDDEN_BUNDLE_ITF) */
    private static final ListCellKey KEY_HIDDEN_BUNDLE_ITF =
            new ListCellKey("HIDDEN_BUNDLE_ITF", new StringCellColumn(), false, false);

    /** lst_workMntList(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE =
            new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_workMntList(HIDDEN_LAST_UPDATE_DATE) */
    private static final ListCellKey KEY_HIDDEN_LAST_UPDATE_DATE =
            new ListCellKey("HIDDEN_LAST_UPDATE_DATE", new StringCellColumn(), false, false);

    /** lst_workMntList(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO =
            new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_workMntList(LST_BATCH_SEQ_NO) */
    private static final ListCellKey KEY_LST_BATCH_SEQ_NO =
            new ListCellKey("LST_BATCH_SEQ_NO", new StringCellColumn(), true, false);

    /** lst_workMntList(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME =
            new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_workMntList(LST_ORDER_NO) */
    private static final ListCellKey KEY_LST_ORDER_NO =
            new ListCellKey("LST_ORDER_NO", new StringCellColumn(), true, false);

    /** lst_workMntList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE =
            new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_workMntList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME =
            new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_workMntList(LST_LOT_QTY) */
    private static final ListCellKey KEY_LST_LOT_QTY =
            new ListCellKey("LST_LOT_QTY", new NumberCellColumn(0), true, false);

    /** lst_workMntList(LST_PLAN_QTY) */
    private static final ListCellKey KEY_LST_PLAN_QTY =
            new ListCellKey("LST_PLAN_QTY", new NumberCellColumn(0), true, false);

    /** lst_workMntList(LST_RESULT_QTY) */
    private static final ListCellKey KEY_LST_RESULT_QTY =
            new ListCellKey("LST_RESULT_QTY", new NumberCellColumn(0), true, true);

    /** lst_workMntList(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO =
            new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_workMntList(LST_ZONE_NO) */
    private static final ListCellKey KEY_LST_ZONE_NO =
            new ListCellKey("LST_ZONE_NO", new StringCellColumn(), true, false);

    /** lst_workMntList(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO =
            new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_workMntList kyes */
    private static final ListCellKey[] LST_WORKMNTLIST_KEYS = {
            KEY_HIDDEN_JAN,
            KEY_HIDDEN_ITF,
            KEY_HIDDEN_BUNDLE_ITF,
            KEY_HIDDEN_CONSIGNOR_CODE,
            KEY_HIDDEN_LAST_UPDATE_DATE,
            KEY_LST_BATCH_NO,
            KEY_LST_CUSTOMER_NAME,
            KEY_LST_ITEM_CODE,
            KEY_LST_LOT_QTY,
            KEY_LST_PLAN_QTY,
            KEY_LST_RESULT_QTY,
            KEY_LST_AREA_NO,
            KEY_LST_LOCATION_NO,
            KEY_LST_BATCH_SEQ_NO,
            KEY_LST_ORDER_NO,
            KEY_LST_ITEM_NAME,
            KEY_LST_ZONE_NO,
    };

    // DFKLOOK ここから修正
    private static final int SHORTAGE_QTY = 2;

    // DFKLOOK ここまで修正

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_WorkStatus */
    private DefaultPullDownModel _pdm_pul_WorkStatus;

    /** PullDownModel pul_AreaNo */
    private WmsAreaPullDownModel _pdm_pul_AreaNo;

    /** RadioButtonGroupModel rdo_WorkMnt */
    private RadioButtonGroup _grp_rdo_WorkMnt;

    /** ListCellModel lst_workMntList */
    private ListCellModel _lcm_lst_workMntList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public WorkMntBusiness()
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

    // DFKLOOK start
    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_ConfirmBack(ActionEvent e) throws Exception
	{
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
		if(eventSource.startsWith("btn_ModifySet_Click"))
		{
			// process call.
			btn_ModifySet_Click_Process(eventSource);
		}
	}

	// DFKLOOK end

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ConsignorCode_EnterKey(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // get locale.
        Connection conn = null;
        PCTConsignorController conController = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            conController = new PCTConsignorController(conn, this.getClass());
            txt_ConsignorName.setText(conController.getConsignorName(txt_ConsignorCode.getText(),
                    InParameter.SEARCH_TABLE_MASTER));

            setFocus(txt_PlanDate);
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
    public void btn_ModifySet_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ModifySet_Click_Process(null);
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
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_WorkStatus.
        _pdm_pul_WorkStatus = new DefaultPullDownModel(pul_WorkStatus, locale, ui);

        // initialize pul_AreaNo.
        _pdm_pul_AreaNo = new WmsAreaPullDownModel(pul_AreaNo, locale, ui);

        // initialize rdo_WorkMnt.
        _grp_rdo_WorkMnt = new RadioButtonGroup(new RadioButton[] {
                rdo_ItemCode,
                rdo_OrderNo
        }, locale);

        // initialize lst_workMntList.
        _lcm_lst_workMntList = new ListCellModel(lst_workMntList, LST_WORKMNTLIST_KEYS, locale);
        _lcm_lst_workMntList.setToolTipVisible(KEY_LST_BATCH_NO, true);
        _lcm_lst_workMntList.setToolTipVisible(KEY_LST_BATCH_SEQ_NO, true);
        _lcm_lst_workMntList.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);
        _lcm_lst_workMntList.setToolTipVisible(KEY_LST_ORDER_NO, true);
        _lcm_lst_workMntList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_workMntList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_workMntList.setToolTipVisible(KEY_LST_LOT_QTY, true);
        _lcm_lst_workMntList.setToolTipVisible(KEY_LST_PLAN_QTY, true);
        _lcm_lst_workMntList.setToolTipVisible(KEY_LST_RESULT_QTY, true);
        _lcm_lst_workMntList.setToolTipVisible(KEY_LST_AREA_NO, true);
        _lcm_lst_workMntList.setToolTipVisible(KEY_LST_ZONE_NO, true);
        _lcm_lst_workMntList.setToolTipVisible(KEY_LST_LOCATION_NO, true);

    }

    /**
     *
     * @throws Exception
     */
    private void initializePulldownModels()
            throws Exception
    {
        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // load pul_WorkStatus.
            _pdm_pul_WorkStatus.init(conn);

            // load pul_AreaNo.
            _pdm_pul_AreaNo.init(conn, AreaType.FLOOR, StationType.ALL, "", true);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_workMntList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);

        // DFKLOOK:ここから修正
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-W0002", KEY_HIDDEN_JAN);
        line.addToolTip("LBL-W0017", KEY_HIDDEN_ITF);
        line.addToolTip("LBL-P0143", KEY_HIDDEN_BUNDLE_ITF);
        // DFKLOOK:ここまで修正
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

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        WorkMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new WorkMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            WorkMntSCHParams inparam = new WorkMntSCHParams();

            // DFKLOOK:ここから修正
            Params outparam = sch.initFind(inparam);

            if (outparam != null)
            {
                txt_ConsignorCode.setValue(outparam.get(WorkMntSCHParams.CONSIGNOR_CODE));
                txt_ConsignorName.setValue(outparam.get(WorkMntSCHParams.CONSIGNOR_NAME));
            }
            // DFKLOOK:ここまで修正

            // clear.
            rdo_ItemCode.setChecked(true);
            btn_ModifySet.setEnabled(false);
            btn_ListClear.setEnabled(false);

            // DFKLOOK:ここから修正
            pul_WorkStatus.setSelectedIndex(SHORTAGE_QTY);
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
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_BatchSeqNo.validate(this, false);
        txt_OrderNo.validate(this, false);
        pul_AreaNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        WorkMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new WorkMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            WorkMntSCHParams inparam = new WorkMntSCHParams();
            inparam.set(WorkMntSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(WorkMntSCHParams.PLAN_DATE, txt_PlanDate.getValue());
            inparam.set(WorkMntSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(WorkMntSCHParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
            inparam.set(WorkMntSCHParams.ORDER_NO, txt_OrderNo.getValue());
            inparam.set(WorkMntSCHParams.JOB_STATUS, _pdm_pul_WorkStatus.getSelectedValue());
            inparam.set(WorkMntSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());

            // DFKLOOK:ここから修正
            // 表示順
            if (rdo_ItemCode.getChecked())
            {
                inparam.set(WorkMntSCHParams.DISP_ORDER, PCTRetrievalInParameter.ORDERING_ITEM_CODE);
            }
            else
            {
                inparam.set(WorkMntSCHParams.DISP_ORDER, PCTRetrievalInParameter.ORDERING_ORDER_NO);
            }

            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            viewState.setObject(ViewStateKeys.VS_PLAN_DATE, txt_PlanDate.getValue());
            viewState.setObject(ViewStateKeys.VS_BATCH_NO, txt_BatchNo.getValue());
            viewState.setObject(ViewStateKeys.VS_BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
            viewState.setObject(ViewStateKeys.VS_ORDER_NO, txt_OrderNo.getValue());
            viewState.setObject(ViewStateKeys.VS_JOB_STATUS, _pdm_pul_WorkStatus.getSelectedValue());
            viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            // 表示順
            if (rdo_ItemCode.getChecked())
            {
                viewState.setObject(ViewStateKeys.VS_DISP_ORDER, PCTRetrievalInParameter.ORDERING_ITEM_CODE);
            }
            else
            {
                viewState.setObject(ViewStateKeys.VS_DISP_ORDER, PCTRetrievalInParameter.ORDERING_ORDER_NO);
            }
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_workMntList.clear();

            // DFKLOOK:ここから修正
            if (outparams == null || outparams.size() == 0)
            {
                // clear.
                btn_ModifySet.setEnabled(false);
                btn_ListClear.setEnabled(false);

                return;
            }

            for (int i = 0; i < outparams.size(); i++)
            // DFKLOOK:ここまで修正
            {
                ListCellLine line = _lcm_lst_workMntList.getNewLine();

                // DFKLOOK:ここから修正
                Params outparam = outparams.get(i);
                // DFKLOOK:ここまで修正

                line.setValue(KEY_HIDDEN_JAN, outparam.get(WorkMntSCHParams.JAN));
                line.setValue(KEY_HIDDEN_ITF, outparam.get(WorkMntSCHParams.ITF));
                line.setValue(KEY_HIDDEN_BUNDLE_ITF, outparam.get(WorkMntSCHParams.BUNDLE_ITF));
                line.setValue(KEY_HIDDEN_CONSIGNOR_CODE, outparam.get(WorkMntSCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_HIDDEN_LAST_UPDATE_DATE, outparam.get(WorkMntSCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(WorkMntSCHParams.BATCH_NO));
                line.setValue(KEY_LST_BATCH_SEQ_NO, outparam.get(WorkMntSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(WorkMntSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(WorkMntSCHParams.ORDER_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(WorkMntSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(WorkMntSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(WorkMntSCHParams.LOT_QTY));
                line.setValue(KEY_LST_PLAN_QTY, outparam.get(WorkMntSCHParams.PLAN_QTY));
                line.setValue(KEY_LST_RESULT_QTY, outparam.get(WorkMntSCHParams.RESULT_QTY));
                line.setValue(KEY_LST_AREA_NO, outparam.get(WorkMntSCHParams.AREA_NO));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(WorkMntSCHParams.ZONE_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(WorkMntSCHParams.LOCATION_NO));

                // DFKLOOK:ここから修正
                viewState.setObject(ViewStateKeys.VS_WORK_NO_LIST.concat(String.valueOf(i)),
                        CollectionUtils.getConnectedString((List<String>)outparam.get(WorkMntSCHParams.WORK_NO_LIST)));
                viewState.setObject(ViewStateKeys.VS_PLAN_UKEY.concat(String.valueOf(i)),
                        outparam.get(WorkMntSCHParams.PLAN_UKEY));
                viewState.setObject(ViewStateKeys.VS_SETTING_UNIT_KEY.concat(String.valueOf(i)),
                        outparam.get(WorkMntSCHParams.SETTING_UNIT_KEY));
                // DFKLOOK:ここまで修正

                lst_workMntList_SetLineToolTip(line);
                _lcm_lst_workMntList.add(line);
            }

            // clear.
            btn_ModifySet.setEnabled(true);
            btn_ListClear.setEnabled(true);

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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_ConsignorCode.setValue(null);
        txt_ConsignorName.setValue(null);
        txt_PlanDate.setValue(null);
        txt_BatchNo.setValue(null);
        txt_BatchSeqNo.setValue(null);
        txt_OrderNo.setValue(null);
        _pdm_pul_WorkStatus.setSelectedValue(null);
        _pdm_pul_AreaNo.setSelectedValue(null);
        rdo_ItemCode.setChecked(true);

        // DFKLOOK:ここから修正
        pul_WorkStatus.setSelectedIndex(SHORTAGE_QTY);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        WorkMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new WorkMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            WorkMntSCHParams inparam = new WorkMntSCHParams();

            Params outparam = sch.initFind(inparam);

            if (outparam != null)
            {
                txt_ConsignorCode.setValue(outparam.get(WorkMntSCHParams.CONSIGNOR_CODE));
                txt_ConsignorName.setValue(outparam.get(WorkMntSCHParams.CONSIGNOR_NAME));
            }

            // clear.
            rdo_ItemCode.setChecked(true);

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
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ModifySet_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_workMntList.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_workMntList.get(i);
            if (!(checkline.isAppend() || checkline.isEdited()))
            {
                continue;
            }

            existEditedRow = true;
            lst_workMntList.setCurrentRow(i);
            lst_workMntList.validate(checkline.getIndex(KEY_LST_RESULT_QTY), false);
        }
        if (!existEditedRow)
        {
            // DFKLOOK:ここから修正 メッセージセット
            // 6023059=更新対象データがありません。
            message.setMsgResourceKey("6023059");
            // DFKLOOK:ここまで修正
            return;
        }

        // DFKLOOK start
        if (StringUtil.isBlank(eventSource))
        {         
            // 修正登録しますか?
            this.setConfirm("MSG-W0013", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_ModifySet_Click");
            return;         
        }
        // DFKLOOK end
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        WorkMntSCH sch = null;

        // DFKLOOK:ここから修正
        int planQty = 0;
        int resultQty = 0;
        // DFKLOOK:ここまで修正

        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new WorkMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_workMntList.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_workMntList.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                WorkMntSCHParams lineparam = new WorkMntSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);

                // DFKLOOK:ここから修正
                lineparam.set(WorkMntSCHParams.WORK_NO,
                        viewState.getObject(ViewStateKeys.VS_WORK_NO_LIST.concat(String.valueOf(i - 1))));
                lineparam.set(
                        WorkMntSCHParams.WORK_NO_LIST,
                        CollectionUtils.getList(viewState.getString(ViewStateKeys.VS_WORK_NO_LIST.concat(String.valueOf(i - 1)))));
                lineparam.set(WorkMntSCHParams.PLAN_UKEY,
                        viewState.getObject(ViewStateKeys.VS_PLAN_UKEY.concat(String.valueOf(i - 1))));
                lineparam.set(WorkMntSCHParams.SETTING_UNIT_KEY,
                        viewState.getObject(ViewStateKeys.VS_SETTING_UNIT_KEY.concat(String.valueOf(i - 1))));
                // DFKLOOK:ここまで修正

                lineparam.set(WorkMntSCHParams.PLAN_QTY, line.getValue(KEY_LST_PLAN_QTY));
                lineparam.set(WorkMntSCHParams.RESULT_QTY, line.getValue(KEY_LST_RESULT_QTY));
                lineparam.set(WorkMntSCHParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                lineparam.set(WorkMntSCHParams.LAST_UPDATE_DATE, line.getValue(KEY_HIDDEN_LAST_UPDATE_DATE));

                // DFKLOOK:ここから修正
                planQty = lineparam.getInt(WorkMntSCHParams.PLAN_QTY);
                resultQty = lineparam.getInt(WorkMntSCHParams.RESULT_QTY);
                // 実績数が予定数以下の場合
                if (resultQty > planQty)
                {
                    // 対象リストセルに警告色を表示
                    _lcm_lst_workMntList.resetEditRow();
                    _lcm_lst_workMntList.resetHighlight();
                    _lcm_lst_workMntList.addHighlight(i, ControlColor.Warning);
                    // 6123295=実績数には予定数以下の値を入力してください。
                    message.setMsgResourceKey(WmsMessageFormatter.format(6123295, WmsFormatter.getNumFormat(i)));
                    return;
                }
                // DFKLOOK:ここまで修正

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
                _lcm_lst_workMntList.resetEditRow();
                _lcm_lst_workMntList.resetHighlight();
                _lcm_lst_workMntList.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_workMntList.resetEditRow();
            _lcm_lst_workMntList.resetHighlight();

            // set input parameters.
            WorkMntSCHParams inparam = new WorkMntSCHParams();
            inparam.set(WorkMntSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(WorkMntSCHParams.PLAN_DATE, viewState.getObject(ViewStateKeys.VS_PLAN_DATE));
            inparam.set(WorkMntSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.VS_BATCH_NO));
            inparam.set(WorkMntSCHParams.BATCH_SEQ_NO, viewState.getObject(ViewStateKeys.VS_BATCH_SEQ_NO));
            inparam.set(WorkMntSCHParams.ORDER_NO, viewState.getObject(ViewStateKeys.VS_ORDER_NO));
            inparam.set(WorkMntSCHParams.JOB_STATUS, viewState.getObject(ViewStateKeys.VS_JOB_STATUS));
            inparam.set(WorkMntSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.VS_AREA_NO));
            inparam.set(WorkMntSCHParams.DISP_ORDER, viewState.getObject(ViewStateKeys.VS_DISP_ORDER));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_workMntList.clear();

            // DFKLOOK:ここから修正
            if (outparams == null || outparams.size() == 0)
            {
                // clear.
                btn_ModifySet.setEnabled(false);
                btn_ListClear.setEnabled(false);

                return;
            }

            //            for (Params outparam : outparams)
            for (int i = 0; i < outparams.size(); i++)
            // DFKLOOK:ここまで修正
            {
                ListCellLine line = _lcm_lst_workMntList.getNewLine();

                // DFKLOOK:ここから修正
                Params outparam = outparams.get(i);
                // DFKLOOK:ここまで修正

                line.setValue(KEY_HIDDEN_JAN, outparam.get(WorkMntSCHParams.JAN));
                line.setValue(KEY_HIDDEN_ITF, outparam.get(WorkMntSCHParams.ITF));
                line.setValue(KEY_HIDDEN_BUNDLE_ITF, outparam.get(WorkMntSCHParams.BUNDLE_ITF));
                line.setValue(KEY_HIDDEN_CONSIGNOR_CODE, outparam.get(WorkMntSCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_HIDDEN_LAST_UPDATE_DATE, outparam.get(WorkMntSCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(WorkMntSCHParams.BATCH_NO));
                line.setValue(KEY_LST_BATCH_SEQ_NO, outparam.get(WorkMntSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(WorkMntSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(WorkMntSCHParams.ORDER_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(WorkMntSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(WorkMntSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(WorkMntSCHParams.LOT_QTY));
                line.setValue(KEY_LST_PLAN_QTY, outparam.get(WorkMntSCHParams.PLAN_QTY));
                line.setValue(KEY_LST_RESULT_QTY, outparam.get(WorkMntSCHParams.RESULT_QTY));
                line.setValue(KEY_LST_AREA_NO, outparam.get(WorkMntSCHParams.AREA_NO));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(WorkMntSCHParams.ZONE_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(WorkMntSCHParams.LOCATION_NO));

                // DFKLOOK:ここから修正
                viewState.setObject(ViewStateKeys.VS_WORK_NO_LIST.concat(String.valueOf(i)),
                        CollectionUtils.getConnectedString((List<String>)outparam.get(WorkMntSCHParams.WORK_NO_LIST)));
                viewState.setObject(ViewStateKeys.VS_PLAN_UKEY.concat(String.valueOf(i)),
                        outparam.get(WorkMntSCHParams.PLAN_UKEY));
                viewState.setObject(ViewStateKeys.VS_SETTING_UNIT_KEY.concat(String.valueOf(i)),
                        outparam.get(WorkMntSCHParams.SETTING_UNIT_KEY));
                // DFKLOOK:ここまで修正

                lst_workMntList_SetLineToolTip(line);
                _lcm_lst_workMntList.add(line);
            }

            // clear.
            btn_ModifySet.setEnabled(true);
            btn_ListClear.setEnabled(true);

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
            DBUtil.rollback(conn);
            DBUtil.close(conn);
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
        _lcm_lst_workMntList.clear();
        btn_ModifySet.setEnabled(false);
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
