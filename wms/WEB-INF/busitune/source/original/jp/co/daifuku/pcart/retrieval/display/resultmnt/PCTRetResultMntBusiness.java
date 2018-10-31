// $Id: PCTRetResultMntBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.pcart.retrieval.display.resultmnt;

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
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.bluedog.webapp.ViewState;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.pcart.retrieval.display.resultmnt.PCTRetResultMnt;
import jp.co.daifuku.pcart.retrieval.display.resultmnt.ViewStateKeys;
import jp.co.daifuku.pcart.retrieval.listbox.item.LstItemParams;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetResultMntSCH;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetResultMntSCHParams;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class PCTRetResultMntBusiness
        extends PCTRetResultMnt
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_resultMntList(HIDDEN_JAN) */
    private static final ListCellKey KEY_HIDDEN_JAN = new ListCellKey("HIDDEN_JAN", new StringCellColumn(), false, false);

    /** lst_resultMntList(HIDDEN_ITF) */
    private static final ListCellKey KEY_HIDDEN_ITF = new ListCellKey("HIDDEN_ITF", new StringCellColumn(), false, false);

    /** lst_resultMntList(HIDDEN_BUNDLE_ITF) */
    private static final ListCellKey KEY_HIDDEN_BUNDLE_ITF = new ListCellKey("HIDDEN_BUNDLE_ITF", new StringCellColumn(), false, false);

    /** lst_resultMntList(HIDDEN_USER_NAME) */
    private static final ListCellKey KEY_HIDDEN_USER_NAME = new ListCellKey("HIDDEN_USER_NAME", new StringCellColumn(), false, false);

    /** lst_resultMntList(HIDDEN_RFT_NO) */
    private static final ListCellKey KEY_HIDDEN_RFT_NO = new ListCellKey("HIDDEN_RFT_NO", new StringCellColumn(), false, false);

    /** lst_resultMntList(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE = new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_resultMntList(HIDDEN_LAST_UPDATE_DATE) */
    private static final ListCellKey KEY_HIDDEN_LAST_UPDATE_DATE = new ListCellKey("HIDDEN_LAST_UPDATE_DATE", new StringCellColumn(), false, false);

    /** lst_resultMntList(HIDDEN_AREA_NO) */
    private static final ListCellKey KEY_HIDDEN_AREA_NO = new ListCellKey("HIDDEN_AREA_NO", new AreaCellColumn(), false, false);

    /** lst_resultMntList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_resultMntList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_resultMntList(LST_PLAN_QTY) */
    private static final ListCellKey KEY_LST_PLAN_QTY = new ListCellKey("LST_PLAN_QTY", new NumberCellColumn(0), true, false);

    /** lst_resultMntList(LST_RESULT_QTY) */
    private static final ListCellKey KEY_LST_RESULT_QTY = new ListCellKey("LST_RESULT_QTY", new NumberCellColumn(0), true, true);

    /** lst_resultMntList(LST_LOT_QTY) */
    private static final ListCellKey KEY_LST_LOT_QTY = new ListCellKey("LST_LOT_QTY", new NumberCellColumn(0), true, false);

    /** lst_resultMntList(LST_ZONE_NO) */
    private static final ListCellKey KEY_LST_ZONE_NO = new ListCellKey("LST_ZONE_NO", new StringCellColumn(), true, false);

    /** lst_resultMntList(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO = new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_resultMntList keys */
    private static final ListCellKey[] LST_RESULTMNTLIST_KEYS = {
        KEY_HIDDEN_JAN,
        KEY_HIDDEN_ITF,
        KEY_HIDDEN_BUNDLE_ITF,
        KEY_HIDDEN_USER_NAME,
        KEY_HIDDEN_RFT_NO,
        KEY_HIDDEN_CONSIGNOR_CODE,
        KEY_HIDDEN_LAST_UPDATE_DATE,
        KEY_HIDDEN_AREA_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_ITEM_NAME,
        KEY_LST_PLAN_QTY,
        KEY_LST_RESULT_QTY,
        KEY_LST_LOT_QTY,
        KEY_LST_ZONE_NO,
        KEY_LST_LOCATION_NO,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_WorkStatusPCT4 */
    private DefaultPullDownModel _pdm_pul_WorkStatusPCT4;

    /** ListCellModel lst_resultMntList */
    private ListCellModel _lcm_lst_resultMntList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTRetResultMntBusiness()
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
        // initialize components.
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
        if (eventSource.equals("btn_SearchItemCode_Click"))
        {
            // process call.
            btn_SearchItemCode_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchItemCode_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchItemCode_Click_Process();
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
    public void btn_ItemUnit_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ItemUnit_Click_Process();
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
        btn_ModifySet_Click_Process();
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

        // initialize pul_WorkStatusPCT4.
        _pdm_pul_WorkStatusPCT4 = new DefaultPullDownModel(pul_WorkStatusPCT4, locale, ui);

        // initialize lst_resultMntList.
        _lcm_lst_resultMntList = new ListCellModel(lst_resultMntList, LST_RESULTMNTLIST_KEYS, locale);
        _lcm_lst_resultMntList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_resultMntList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_resultMntList.setToolTipVisible(KEY_LST_PLAN_QTY, true);
        _lcm_lst_resultMntList.setToolTipVisible(KEY_LST_RESULT_QTY, true);
        _lcm_lst_resultMntList.setToolTipVisible(KEY_LST_LOT_QTY, true);
        _lcm_lst_resultMntList.setToolTipVisible(KEY_LST_ZONE_NO, true);
        _lcm_lst_resultMntList.setToolTipVisible(KEY_LST_LOCATION_NO, true);
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

            // load pul_WorkStatusPCT4.
            _pdm_pul_WorkStatusPCT4.init(conn);
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
    private void dispose()
            throws Exception
    {
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_resultMntList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("", KEY_HIDDEN_JAN);
        line.addToolTip("", KEY_HIDDEN_ITF);
        line.addToolTip("", KEY_HIDDEN_BUNDLE_ITF);
        line.addToolTip("", KEY_HIDDEN_USER_NAME);
        line.addToolTip("", KEY_HIDDEN_RFT_NO);
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
        PCTRetResultMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTRetResultMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTRetResultMntSCHParams inparam = new PCTRetResultMntSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_ConsignorCode.setValue(outparam.get(PCTRetResultMntSCHParams.CONSIGNOR_CODE));
                txt_ConsignorName.setValue(outparam.get(PCTRetResultMntSCHParams.CONSIGNOR_NAME));
            }

            // clear.
            btn_ModifySet.setEnabled(false);
            btn_ListClear.setEnabled(false);
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
    private void btn_SearchItemCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstItemParams.ORDER_NO, txt_OrderNo.getValue());
        inparam.set(LstItemParams.STATUS_FLAG, PCTRetrievalInParameter.STATUS_FLAG_COMPLETION);
        inparam.set(LstItemParams.STATUS_FLAG2, PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION);
        inparam.set(LstItemParams.SEARCHTABLE, InParameter.SEARCH_TABLE_WORK);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchItemCode_Click");
        redirect("/pcart/retrieval/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchItemCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstItemParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemParams.ITEM_NAME));

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
        // input validation.
        txt_ConsignorCode.validate(this, true);
        txt_OrderNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTRetResultMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTRetResultMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTRetResultMntSCHParams inparam = new PCTRetResultMntSCHParams();
            inparam.set(PCTRetResultMntSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTRetResultMntSCHParams.ORDER_NO, txt_OrderNo.getValue());
            inparam.set(PCTRetResultMntSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(PCTRetResultMntSCHParams.JOB_STATUS, _pdm_pul_WorkStatusPCT4.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_resultMntList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_resultMntList.getNewLine();
                line.setValue(KEY_HIDDEN_JAN, outparam.get(PCTRetResultMntSCHParams.JAN));
                line.setValue(KEY_HIDDEN_ITF, outparam.get(PCTRetResultMntSCHParams.ITF));
                line.setValue(KEY_HIDDEN_BUNDLE_ITF, outparam.get(PCTRetResultMntSCHParams.BUNDLE_ITF));
                line.setValue(KEY_HIDDEN_USER_NAME, outparam.get(PCTRetResultMntSCHParams.USER_NAME));
                line.setValue(KEY_HIDDEN_RFT_NO, outparam.get(PCTRetResultMntSCHParams.RFT_NO));
                line.setValue(KEY_HIDDEN_CONSIGNOR_CODE, outparam.get(PCTRetResultMntSCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_HIDDEN_LAST_UPDATE_DATE, outparam.get(PCTRetResultMntSCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(PCTRetResultMntSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(PCTRetResultMntSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_PLAN_QTY, outparam.get(PCTRetResultMntSCHParams.PLAN_QTY));
                line.setValue(KEY_LST_RESULT_QTY, outparam.get(PCTRetResultMntSCHParams.RESULT_QTY));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(PCTRetResultMntSCHParams.LOT_QTY));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(PCTRetResultMntSCHParams.ZONE_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(PCTRetResultMntSCHParams.LOCATION_NO));
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                viewState.setObject(ViewStateKeys.VS_ORDER_NO, txt_OrderNo.getValue());
                viewState.setObject(ViewStateKeys.VS_ITEM_CODE, txt_ItemCode.getValue());
                viewState.setObject(ViewStateKeys.VS_JOB_STATUS, _pdm_pul_WorkStatusPCT4.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_WORK_NO_LIST, outparam.get(PCTRetResultMntSCHParams.WORK_NO_LIST));
                viewState.setObject(ViewStateKeys.VS_PLAN_UKEY, outparam.get(PCTRetResultMntSCHParams.PLAN_UKEY));
                viewState.setObject(ViewStateKeys.VS_SETTING_UNIT_KEY, outparam.get(PCTRetResultMntSCHParams.SETTING_UNIT_KEY));
                txt_PlanDay.setValue(outparam.get(PCTRetResultMntSCHParams.PLAN_DAY));
                txt_BatchNo.setValue(outparam.get(PCTRetResultMntSCHParams.BATCH_NO));
                txt_BatchSeqNoPCT.setValue(outparam.get(PCTRetResultMntSCHParams.BATCH_SEQ_NO));
                txt_Area.setValue(outparam.get(PCTRetResultMntSCHParams.AREA_NO));
                txt_AreaName.setValue(outparam.get(PCTRetResultMntSCHParams.AREA_NAME));
                txt_RegularCustomerCode.setValue(outparam.get(PCTRetResultMntSCHParams.REGULAR_CUSTOMER_CODE));
                txt_RegularCustomerName.setValue(outparam.get(PCTRetResultMntSCHParams.REGULAR_CUSTOMER_NAME));
                txt_CustomerCode.setValue(outparam.get(PCTRetResultMntSCHParams.CUSTOMER_CODE));
                txt_CustomerName.setValue(outparam.get(PCTRetResultMntSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_HIDDEN_AREA_NO, outparam.get(PCTRetResultMntSCHParams.AREA_NO));
                lst_resultMntList_SetLineToolTip(line);
                _lcm_lst_resultMntList.add(line);
            }

            // clear.
            btn_ModifySet.setEnabled(true);
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
        txt_ConsignorCode.setValue(null);
        txt_ConsignorName.setValue(null);
        txt_OrderNo.setValue(null);
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        _pdm_pul_WorkStatusPCT4.setSelectedValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ItemUnit_Click_Process()
            throws Exception
    {
        try
        {
            // forward.
            forward("/pcart/retrieval/resultmnt/PCTRetResultMnt2.do");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ModifySet_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_resultMntList.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_resultMntList.get(i);
            if (!(checkline.isAppend() || checkline.isEdited()))
            {
                continue;
            }

            existEditedRow = true;
            lst_resultMntList.setCurrentRow(i);
            lst_resultMntList.validate(checkline.getIndex(KEY_LST_RESULT_QTY), false);
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003001");
            return;
        }

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTRetResultMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTRetResultMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_resultMntList.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_resultMntList.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                PCTRetResultMntSCHParams lineparam = new PCTRetResultMntSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(PCTRetResultMntSCHParams.PLAN_QTY, line.getValue(KEY_LST_PLAN_QTY));
                lineparam.set(PCTRetResultMntSCHParams.RESULT_QTY, line.getValue(KEY_LST_RESULT_QTY));
                lineparam.set(PCTRetResultMntSCHParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                lineparam.set(PCTRetResultMntSCHParams.LAST_UPDATE_DATE, line.getValue(KEY_HIDDEN_LAST_UPDATE_DATE));
                lineparam.set(PCTRetResultMntSCHParams.WORK_NO, viewState.getObject(ViewStateKeys.VS_WORK_NO));
                lineparam.set(PCTRetResultMntSCHParams.WORK_NO_LIST, viewState.getObject(ViewStateKeys.VS_WORK_NO_LIST));
                lineparam.set(PCTRetResultMntSCHParams.PLAN_UKEY, viewState.getObject(ViewStateKeys.VS_PLAN_UKEY));
                lineparam.set(PCTRetResultMntSCHParams.SETTING_UNIT_KEY, viewState.getObject(ViewStateKeys.VS_SETTING_UNIT_KEY));
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

                // reset editing row or highlighting error row.
                _lcm_lst_resultMntList.resetEditRow();
                _lcm_lst_resultMntList.resetHighlight();
                _lcm_lst_resultMntList.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_resultMntList.resetEditRow();
            _lcm_lst_resultMntList.resetHighlight();

            // set input parameters.
            PCTRetResultMntSCHParams inparam = new PCTRetResultMntSCHParams();
            inparam.set(PCTRetResultMntSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTRetResultMntSCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.VS_ITEM_CODE));
            inparam.set(PCTRetResultMntSCHParams.ORDER_NO, viewState.getObject(ViewStateKeys.VS_ORDER_NO));
            inparam.set(PCTRetResultMntSCHParams.JOB_STATUS, viewState.getObject(ViewStateKeys.VS_JOB_STATUS));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_resultMntList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_resultMntList.getNewLine();
                line.setValue(KEY_HIDDEN_JAN, outparam.get(PCTRetResultMntSCHParams.JAN));
                line.setValue(KEY_HIDDEN_ITF, outparam.get(PCTRetResultMntSCHParams.ITF));
                line.setValue(KEY_HIDDEN_BUNDLE_ITF, outparam.get(PCTRetResultMntSCHParams.BUNDLE_ITF));
                line.setValue(KEY_HIDDEN_USER_NAME, outparam.get(PCTRetResultMntSCHParams.USER_NAME));
                line.setValue(KEY_HIDDEN_RFT_NO, outparam.get(PCTRetResultMntSCHParams.RFT_NO));
                line.setValue(KEY_HIDDEN_CONSIGNOR_CODE, outparam.get(PCTRetResultMntSCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_HIDDEN_LAST_UPDATE_DATE, outparam.get(PCTRetResultMntSCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(PCTRetResultMntSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(PCTRetResultMntSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_PLAN_QTY, outparam.get(PCTRetResultMntSCHParams.PLAN_QTY));
                line.setValue(KEY_LST_RESULT_QTY, outparam.get(PCTRetResultMntSCHParams.RESULT_QTY));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(PCTRetResultMntSCHParams.LOT_QTY));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(PCTRetResultMntSCHParams.ZONE_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(PCTRetResultMntSCHParams.LOCATION_NO));
                viewState.setObject(ViewStateKeys.VS_WORK_NO_LIST, outparam.get(PCTRetResultMntSCHParams.WORK_NO_LIST));
                viewState.setObject(ViewStateKeys.VS_PLAN_UKEY, outparam.get(PCTRetResultMntSCHParams.PLAN_UKEY));
                viewState.setObject(ViewStateKeys.VS_SETTING_UNIT_KEY, outparam.get(PCTRetResultMntSCHParams.SETTING_UNIT_KEY));
                line.setValue(KEY_HIDDEN_AREA_NO, outparam.get(PCTRetResultMntSCHParams.AREA_NO));
                lst_resultMntList_SetLineToolTip(line);
                _lcm_lst_resultMntList.add(line);
            }

            // clear.
            btn_ModifySet.setEnabled(true);
            btn_ListClear.setEnabled(true);
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
    private void btn_ListClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_resultMntList.clear();
        btn_ModifySet.setEnabled(false);
        btn_ListClear.setEnabled(false);
        txt_PlanDay.setValue(null);
        txt_BatchNo.setValue(null);
        txt_BatchSeqNoPCT.setValue(null);
        txt_Area.setValue(null);
        txt_AreaName.setValue(null);
        txt_RegularCustomerCode.setValue(null);
        txt_RegularCustomerName.setValue(null);
        txt_CustomerCode.setValue(null);
        txt_CustomerName.setValue(null);
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
