// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.asrs.display.faitemretrieval;

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
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
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
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.faitemretrieval.FaItemRetrieval;
import jp.co.daifuku.wms.asrs.schedule.FaItemRetrievalSCH;
import jp.co.daifuku.wms.asrs.schedule.FaItemRetrievalSCHParams;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.listbox.itemname.LstItemNameParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkspacePullDownModel;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class FaItemRetrievalBusiness
        extends FaItemRetrieval
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_FaItemRetrieval(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE = new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_FaItemRetrieval(HIDDEN_TYPE) */
    private static final ListCellKey KEY_HIDDEN_TYPE = new ListCellKey("HIDDEN_TYPE", new StringCellColumn(), false, false);

    /** lst_FaItemRetrieval(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY = new ListCellKey("LST_MODIFY", new StringCellColumn(), true, false);

    /** lst_FaItemRetrieval(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_FaItemRetrieval(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_FaItemRetrieval(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_FaItemRetrieval(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_FaItemRetrieval(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_FaItemRetrieval(LST_RETRIEVAL_RESULT_QTY) */
    private static final ListCellKey KEY_LST_RETRIEVAL_RESULT_QTY = new ListCellKey("LST_RETRIEVAL_RESULT_QTY", new NumberCellColumn(0), true, false);

    /** lst_FaItemRetrieval(LST_TYPE) */
    private static final ListCellKey KEY_LST_TYPE = new ListCellKey("LST_TYPE", new StringCellColumn(), true, false);

    /** lst_FaItemRetrieval keys */
    private static final ListCellKey[] LST_FAITEMRETRIEVAL_KEYS = {
        KEY_HIDDEN_CONSIGNOR_CODE,
        KEY_HIDDEN_TYPE,
        KEY_LST_MODIFY,
        KEY_LST_CANCEL,
        KEY_LST_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_ITEM_NAME,
        KEY_LST_LOT_NO,
        KEY_LST_RETRIEVAL_RESULT_QTY,
        KEY_LST_TYPE,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** PullDownModel pul_WorkPlace */
    private WmsWorkspacePullDownModel _pdm_pul_WorkPlace;

    /** PullDownModel pul_Station */
    private WmsStationPullDownModel _pdm_pul_Station;

    /** PullDownModel pul_PriorityFlag */
    private DefaultPullDownModel _pdm_pul_PriorityFlag;

    /** ListCellModel lst_FaItemRetrieval */
    private ScrollListCellModel _lcm_lst_FaItemRetrieval;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaItemRetrievalBusiness()
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
        else if (eventSource.equals("btn_SearchItemName_Click"))
        {
            // process call.
            btn_SearchItemName_Click_DlgBack(dialogParams);
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
        if (eventSource.equals("btn_Input_Click"))
        {
            // process call.
            btn_Input_Click_Process(false);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemCode_EnterKey(ActionEvent e)
            throws Exception
    {
        // process call.
        txt_ItemCode_EnterKey_Process();
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
    public void btn_SearchItemName_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchItemName_Click_Process();
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
    public void btn_Input_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Input_Click_Process(true);
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
    public void btn_Set_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Set_Click_Process();
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
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_FaItemRetrieval_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_FaItemRetrieval.getActiveCol();

        // choose process.
        if (_lcm_lst_FaItemRetrieval.getColumnIndex(KEY_LST_MODIFY) == activeCol)
        {
            // process call.
            lst_Modify_Click_Process();
        }
        else if (_lcm_lst_FaItemRetrieval.getColumnIndex(KEY_LST_CANCEL) == activeCol)
        {
            // process call.
            lst_Cancel_Click_Process();
        }
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

        // initialize pul_Area.
        _pdm_pul_Area = new WmsAreaPullDownModel(pul_Area, locale, ui);

        // initialize pul_WorkPlace.
        _pdm_pul_WorkPlace = new WmsWorkspacePullDownModel(pul_WorkPlace, locale, ui);
        _pdm_pul_WorkPlace.setParent(_pdm_pul_Area);

        // initialize pul_Station.
        _pdm_pul_Station = new WmsStationPullDownModel(pul_Station, locale, ui);
        _pdm_pul_Station.setParent(_pdm_pul_WorkPlace);

        // initialize pul_PriorityFlag.
        _pdm_pul_PriorityFlag = new DefaultPullDownModel(pul_PriorityFlag, locale, ui);

        // initialize lst_FaItemRetrieval.
        _lcm_lst_FaItemRetrieval = new ScrollListCellModel(lst_FaItemRetrieval, LST_FAITEMRETRIEVAL_KEYS, locale);
        _lcm_lst_FaItemRetrieval.setToolTipVisible(KEY_LST_MODIFY, false);
        _lcm_lst_FaItemRetrieval.setToolTipVisible(KEY_LST_CANCEL, false);
        _lcm_lst_FaItemRetrieval.setToolTipVisible(KEY_LST_NO, false);
        _lcm_lst_FaItemRetrieval.setToolTipVisible(KEY_LST_ITEM_CODE, false);
        _lcm_lst_FaItemRetrieval.setToolTipVisible(KEY_LST_ITEM_NAME, false);
        _lcm_lst_FaItemRetrieval.setToolTipVisible(KEY_LST_LOT_NO, false);
        _lcm_lst_FaItemRetrieval.setToolTipVisible(KEY_LST_RETRIEVAL_RESULT_QTY, false);
        _lcm_lst_FaItemRetrieval.setToolTipVisible(KEY_LST_TYPE, false);
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

            // load pul_Area.
            _pdm_pul_Area.init(conn, AreaType.NOT_MOVING, StationType.RETRIEVAL, "", true);

            // load pul_WorkPlace.
            _pdm_pul_WorkPlace.init(conn, StationType.ITEM_RETRIEVAL,"", Distribution.UNUSE, true);

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.ITEM_RETRIEVAL, Distribution.ALL, Distribution.ALL,"" ,true);

            // load pul_PriorityFlag.
            _pdm_pul_PriorityFlag.init(conn);
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
    private void lst_FaItemRetrieval_SetLineToolTip(ListCellLine line)
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
        setFocus(txt_ItemCode);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_Set.setEnabled(false);
        btn_AllClear.setEnabled(false);
        chk_WorkListPrint.setEnabled(false);
        chk_ShortageListPrint.setEnabled(false);
        txt_EnteredLines.setReadOnly(true);
        txt_EnterableLines.setReadOnly(true);
        txt_TotalStockQty.setReadOnly(true);
        pul_PriorityFlag.setEnabled(false);
    }

    /**
     *
     * @throws Exception
     */
    private void txt_ItemCode_EnterKey_Process()
            throws Exception
    {
        // input validation.
        txt_ItemCode.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaItemRetrievalSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaItemRetrievalSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaItemRetrievalSCHParams inparam = new FaItemRetrievalSCHParams();
            inparam.set(FaItemRetrievalSCHParams.ITEM_CODE, txt_ItemCode.getValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_ItemName.setValue(outparam.get(FaItemRetrievalSCHParams.ITEM_NAME));
            }
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
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchItemCode_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
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
    private void btn_SearchItemName_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemNameParams inparam = new LstItemNameParams();
        inparam.set(LstItemNameParams.ITEM_NAME, txt_ItemName.getValue());
        inparam.set(LstItemNameParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchItemName_Click");
        redirect("/base/listbox/itemname/LstItemName.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchItemName_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemNameParams outparam = new LstItemNameParams(dialogParams);
        txt_ItemName.setValue(outparam.get(LstItemNameParams.ITEM_NAME));
        txt_ItemCode.setValue(outparam.get(LstItemNameParams.ITEM_CODE));

        // set focus.
        setFocus(txt_ItemName);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        pul_Area.validate(this, true);
        pul_WorkPlace.validate(this, true);
        pul_Station.validate(this, true);
        txt_ItemCode.validate(this, true);
        txt_LotNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaItemRetrievalSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaItemRetrievalSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaItemRetrievalSCHParams inparam = new FaItemRetrievalSCHParams();
            inparam.set(FaItemRetrievalSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(FaItemRetrievalSCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(FaItemRetrievalSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaItemRetrievalSCHParams.WORK_PLACE, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(FaItemRetrievalSCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(FaItemRetrievalSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_TotalStockQty.setValue(outparam.get(FaItemRetrievalSCHParams.TOTAL_STOCK_QTY));
            }
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
     * @param confirm
     * @throws Exception
     */
    private void btn_Input_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        pul_Area.validate(this, true);
        pul_WorkPlace.validate(this, true);
        pul_Station.validate(this, true);
        txt_ItemCode.validate(this, true);
        txt_ItemName.validate(this, false);
        txt_LotNo.validate(this, false);
        txt_TotalStockQty.validate(this, false);
        txt_PickingQty.validate(this, true);
        pul_PriorityFlag.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaItemRetrievalSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaItemRetrievalSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaItemRetrievalSCHParams inparam = new FaItemRetrievalSCHParams();
            inparam.set(FaItemRetrievalSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaItemRetrievalSCHParams.WORK_PLACE, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(FaItemRetrievalSCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(FaItemRetrievalSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(FaItemRetrievalSCHParams.ITEM_NAME, txt_ItemName.getValue());
            inparam.set(FaItemRetrievalSCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(FaItemRetrievalSCHParams.TOTAL_STOCK_QTY, txt_TotalStockQty.getValue());
            inparam.set(FaItemRetrievalSCHParams.RETRIEVAL_QTY, txt_PickingQty.getValue());
            inparam.set(FaItemRetrievalSCHParams.PRIORITY_FLAG, _pdm_pul_PriorityFlag.getSelectedValue());
            inparam.set(FaItemRetrievalSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_FaItemRetrieval.size(); i++)
            {
                // exclusion editing row.
                if (_lcm_lst_FaItemRetrieval.getEditRow() == i)
                {
                    continue;
                }

                ListCellLine line = _lcm_lst_FaItemRetrieval.get(i);
                FaItemRetrievalSCHParams lineparam = new FaItemRetrievalSCHParams();
                lineparam.setProcessFlag(ProcessFlag.INPUT);
                lineparam.setRowIndex(i);
                lineparam.set(FaItemRetrievalSCHParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                lineparam.set(FaItemRetrievalSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(FaItemRetrievalSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(FaItemRetrievalSCHParams.LOT_NO, line.getValue(KEY_LST_LOT_NO));
                lineparam.set(FaItemRetrievalSCHParams.RETRIEVAL_RESULT_QTY, line.getValue(KEY_LST_RETRIEVAL_RESULT_QTY));
                lineparam.set(FaItemRetrievalSCHParams.TYPE, line.getValue(KEY_LST_TYPE));
                lineparam.set(FaItemRetrievalSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if (confirm && !sch.check(inparam, inparams))
            {
                if (StringUtil.isBlank(sch.getDispMessage()))
                {
                    // show message.
                    message.setMsgResourceKey(sch.getMessage());
                    return;
                }
                else
                {
                    // show confirm message.
                    this.setConfirm(sch.getDispMessage(), false, true);
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Input_Click");
                    return;
                }
            }

            message.setMsgResourceKey(sch.getMessage());

            // output display.
            int editRow = _lcm_lst_FaItemRetrieval.getEditRow();
            Boolean newline = ListCellModel.EDIT_ROW_NONE == editRow;
            ListCellLine line = newline ? _lcm_lst_FaItemRetrieval.getNewLine()
                                        : _lcm_lst_FaItemRetrieval.get(editRow);
            line.setValue(KEY_HIDDEN_CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            line.setValue(KEY_LST_ITEM_CODE, txt_ItemCode.getValue());
            line.setValue(KEY_LST_ITEM_NAME, txt_ItemName.getValue());
            line.setValue(KEY_LST_LOT_NO, txt_LotNo.getValue());
            line.setValue(KEY_LST_RETRIEVAL_RESULT_QTY, txt_PickingQty.getValue());
            line.setValue(KEY_LST_TYPE, _pdm_pul_PriorityFlag.getSelectedValue());
            line.setValue(KEY_HIDDEN_TYPE, _pdm_pul_PriorityFlag.getSelectedValue());

            // add new row or update editing row.
            lst_FaItemRetrieval_SetLineToolTip(line);
            if (newline)
            {
                _lcm_lst_FaItemRetrieval.add(line, true);
            }
            else
            {
                _lcm_lst_FaItemRetrieval.set(editRow, line);
            }

            // reset editing row.
            _lcm_lst_FaItemRetrieval.resetEditRow();
            _lcm_lst_FaItemRetrieval.resetHighlight();

            // clear.
            btn_Set.setEnabled(true);
            btn_AllClear.setEnabled(true);
            chk_WorkListPrint.setEnabled(true);
            chk_ShortageListPrint.setEnabled(true);
            pul_Area.setEnabled(false);
            pul_WorkPlace.setEnabled(false);
            pul_Station.setEnabled(false);
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
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        txt_LotNo.setValue(null);
        txt_TotalStockQty.setValue(null);
        txt_PickingQty.setValue(null);
        _pdm_pul_PriorityFlag.setSelectedValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaItemRetrievalSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaItemRetrievalSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_FaItemRetrieval.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_FaItemRetrieval.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                FaItemRetrievalSCHParams lineparam = new FaItemRetrievalSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(FaItemRetrievalSCHParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                lineparam.set(FaItemRetrievalSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(FaItemRetrievalSCHParams.LOT_NO, line.getValue(KEY_LST_LOT_NO));
                lineparam.set(FaItemRetrievalSCHParams.RETRIEVAL_RESULT_QTY, line.getValue(KEY_LST_RETRIEVAL_RESULT_QTY));
                lineparam.set(FaItemRetrievalSCHParams.TYPE, line.getValue(KEY_LST_TYPE));
                lineparam.set(FaItemRetrievalSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
                lineparam.set(FaItemRetrievalSCHParams.WORK_PLACE, _pdm_pul_WorkPlace.getSelectedValue());
                lineparam.set(FaItemRetrievalSCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
                lineparam.set(FaItemRetrievalSCHParams.WORK_LIST_PRINT, chk_WorkListPrint.getValue());
                lineparam.set(FaItemRetrievalSCHParams.SHORTAGE_LIST_PRINT, chk_ShortageListPrint.getValue());
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
                _lcm_lst_FaItemRetrieval.resetEditRow();
                _lcm_lst_FaItemRetrieval.resetHighlight();
                _lcm_lst_FaItemRetrieval.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_FaItemRetrieval.size(); i++)
            {
                ListCellLine line = _lcm_lst_FaItemRetrieval.get(i);
                lst_FaItemRetrieval.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");
                part11List.add(_pdm_pul_WorkPlace.getSelectedStringValue(), "");
                part11List.add(_pdm_pul_Station.getSelectedStringValue(), "");

                if (chk_WorkListPrint.getChecked())
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                }

                if (chk_ShortageListPrint.getChecked())
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                }

                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_RETRIEVAL_RESULT_QTY), "");
                part11List.add(line.getViewString(KEY_HIDDEN_TYPE), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_FaItemRetrieval.resetEditRow();
            _lcm_lst_FaItemRetrieval.resetHighlight();

            // clear.
            _lcm_lst_FaItemRetrieval.clear();
            btn_Set.setEnabled(false);
            btn_AllClear.setEnabled(false);
            chk_WorkListPrint.setEnabled(false);
            chk_ShortageListPrint.setEnabled(false);
            pul_Area.setEnabled(true);
            pul_WorkPlace.setEnabled(true);
            pul_Station.setEnabled(true);
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
    private void btn_AllClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_FaItemRetrieval.clear();
        btn_Set.setEnabled(false);
        btn_AllClear.setEnabled(false);
        chk_WorkListPrint.setEnabled(false);
        chk_ShortageListPrint.setEnabled(false);
        pul_Area.setEnabled(true);
        pul_WorkPlace.setEnabled(true);
        pul_Station.setEnabled(true);
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Modify_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_FaItemRetrieval.getActiveRow();
        lst_FaItemRetrieval.setCurrentRow(row);
        ListCellLine line = _lcm_lst_FaItemRetrieval.get(row);

        // output display.
        txt_ItemCode.setValue(line.getValue(KEY_LST_ITEM_CODE));
        txt_ItemName.setValue(line.getValue(KEY_LST_ITEM_NAME));
        txt_LotNo.setValue(line.getValue(KEY_LST_LOT_NO));
        txt_PickingQty.setValue(line.getValue(KEY_LST_RETRIEVAL_RESULT_QTY));
        _pdm_pul_PriorityFlag.setSelectedValue(line.getValue(KEY_LST_TYPE));

        // highlight active row.
        _lcm_lst_FaItemRetrieval.resetHighlight();
        _lcm_lst_FaItemRetrieval.addHighlight(row);
        _lcm_lst_FaItemRetrieval.setEditRow(row);
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_FaItemRetrieval.getActiveRow();
        lst_FaItemRetrieval.setCurrentRow(row);

        // reset editing row.
        lst_FaItemRetrieval.removeRow(row);
        _lcm_lst_FaItemRetrieval.resetEditRow();
        _lcm_lst_FaItemRetrieval.resetHighlight();
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
