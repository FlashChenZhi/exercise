// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.asrs.display.fainquiryretrieval;

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
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.util.Formatter;
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
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.dasch.FaInquiryRetrievalDASCH;
import jp.co.daifuku.wms.asrs.dasch.FaInquiryRetrievalDASCHParams;
import jp.co.daifuku.wms.asrs.display.fainquiryretrieval.FaInquiryRetrieval;
import jp.co.daifuku.wms.asrs.schedule.FaInquiryRetrievalSCH;
import jp.co.daifuku.wms.asrs.schedule.FaInquiryRetrievalSCHParams;
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
public class FaInquiryRetrievalBusiness
        extends FaInquiryRetrieval
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_FaInquiryRetrieval(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE = new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_FaInquiryRetrieval(HIDDEN_STORAGE_DATE) */
    private static final ListCellKey KEY_HIDDEN_STORAGE_DATE = new ListCellKey("HIDDEN_STORAGE_DATE", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), false, false);

    /** lst_FaInquiryRetrieval(HIDDEN_LAST_UPDATE_DATE) */
    private static final ListCellKey KEY_HIDDEN_LAST_UPDATE_DATE = new ListCellKey("HIDDEN_LAST_UPDATE_DATE", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMSS), false, false);

    /** lst_FaInquiryRetrieval(HIDDEN_STOCK_ID) */
    private static final ListCellKey KEY_HIDDEN_STOCK_ID = new ListCellKey("HIDDEN_STOCK_ID", new StringCellColumn(), false, false);

    /** lst_FaInquiryRetrieval(HIDDEN_PALLET_ID) */
    private static final ListCellKey KEY_HIDDEN_PALLET_ID = new ListCellKey("HIDDEN_PALLET_ID", new StringCellColumn(), false, false);

    /** lst_FaInquiryRetrieval(HIDDEN_AREA_TYPE) */
    private static final ListCellKey KEY_HIDDEN_AREA_TYPE = new ListCellKey("HIDDEN_AREA_TYPE", new StringCellColumn(), false, false);

    /** lst_FaInquiryRetrieval(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_FaInquiryRetrieval(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_FaInquiryRetrieval(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_FaInquiryRetrieval(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_FaInquiryRetrieval(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO = new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_FaInquiryRetrieval(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO = new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_FaInquiryRetrieval(LST_MIXED_LOAD) */
    private static final ListCellKey KEY_LST_MIXED_LOAD = new ListCellKey("LST_MIXED_LOAD", new StringCellColumn(), true, false);

    /** lst_FaInquiryRetrieval(LST_STOCK_QTY) */
    private static final ListCellKey KEY_LST_STOCK_QTY = new ListCellKey("LST_STOCK_QTY", new NumberCellColumn(0), true, false);

    /** lst_FaInquiryRetrieval(LST_PICKING_QTY) */
    private static final ListCellKey KEY_LST_PICKING_QTY = new ListCellKey("LST_PICKING_QTY", new NumberCellColumn(0), true, true);

    /** lst_FaInquiryRetrieval(LST_ALL_QTY) */
    private static final ListCellKey KEY_LST_ALL_QTY = new ListCellKey("LST_ALL_QTY", new CheckBoxColumn(), true, true);

    /** lst_FaInquiryRetrieval keys */
    private static final ListCellKey[] LST_FAINQUIRYRETRIEVAL_KEYS = {
        KEY_HIDDEN_CONSIGNOR_CODE,
        KEY_HIDDEN_STORAGE_DATE,
        KEY_HIDDEN_LAST_UPDATE_DATE,
        KEY_HIDDEN_STOCK_ID,
        KEY_HIDDEN_PALLET_ID,
        KEY_HIDDEN_AREA_TYPE,
        KEY_LST_SELECT,
        KEY_LST_ITEM_CODE,
        KEY_LST_ITEM_NAME,
        KEY_LST_LOT_NO,
        KEY_LST_AREA_NO,
        KEY_LST_LOCATION_NO,
        KEY_LST_MIXED_LOAD,
        KEY_LST_STOCK_QTY,
        KEY_LST_PICKING_QTY,
        KEY_LST_ALL_QTY,
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

    /** RadioButtonGroupModel SearchCondition */
    private RadioButtonGroup _grp_SearchCondition;

    /** PagerModel */
    private PagerModel _pager;

    /** PullDownModel pul_LPrioriryFlag */
    private DefaultPullDownModel _pdm_pul_LPrioriryFlag;

    /** ListCellModel lst_FaInquiryRetrieval */
    private ScrollListCellModel _lcm_lst_FaInquiryRetrieval;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaInquiryRetrievalBusiness()
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
        if (eventSource.equals("btn_PItemCodeSearch_Click"))
        {
            // process call.
            btn_PItemCodeSearch_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_PItemNameSearch_Click"))
        {
            // process call.
            btn_PItemNameSearch_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PItemCodeSearch_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PItemCodeSearch_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PItemNameSearch_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PItemNameSearch_Click_Process();
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
    public void btn_WorkStart_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_WorkStart_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ClearListInput_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ClearListInput_Click_Process();
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
    public void pager_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_Prev(ActionEvent e)
            throws Exception
    {
        _pager.previous();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_Next(ActionEvent e)
            throws Exception
    {
        _pager.next();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_Last(ActionEvent e)
            throws Exception
    {
        _pager.last();
        pager_SetPage();
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

        // initialize SearchCondition.
        _grp_SearchCondition = new RadioButtonGroup(new RadioButton[]{rdo_Search_ItemCode, rdo_Search_Location}, locale);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pager}, locale);

        // initialize pul_LPrioriryFlag.
        _pdm_pul_LPrioriryFlag = new DefaultPullDownModel(pul_LPrioriryFlag, locale, ui);

        // initialize lst_FaInquiryRetrieval.
        _lcm_lst_FaInquiryRetrieval = new ScrollListCellModel(lst_FaInquiryRetrieval, LST_FAINQUIRYRETRIEVAL_KEYS, locale);
        _lcm_lst_FaInquiryRetrieval.setToolTipVisible(KEY_LST_SELECT, true);
        _lcm_lst_FaInquiryRetrieval.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_FaInquiryRetrieval.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_FaInquiryRetrieval.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_FaInquiryRetrieval.setToolTipVisible(KEY_LST_AREA_NO, true);
        _lcm_lst_FaInquiryRetrieval.setToolTipVisible(KEY_LST_LOCATION_NO, true);
        _lcm_lst_FaInquiryRetrieval.setToolTipVisible(KEY_LST_MIXED_LOAD, true);
        _lcm_lst_FaInquiryRetrieval.setToolTipVisible(KEY_LST_STOCK_QTY, true);
        _lcm_lst_FaInquiryRetrieval.setToolTipVisible(KEY_LST_PICKING_QTY, false);
        _lcm_lst_FaInquiryRetrieval.setToolTipVisible(KEY_LST_ALL_QTY, true);
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
            _pdm_pul_WorkPlace.init(conn, StationType.FLOORANDRETRIEVAL);

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.FLOORANDRETRIEVAL, Distribution.UNUSE);

            // load pul_LPrioriryFlag.
            _pdm_pul_LPrioriryFlag.init(conn);
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
        // dispose DASCH.
        disposeDasch();
    }

    /**
     *
     * @throws Exception
     */
    private void disposeDasch()
            throws Exception
    {
        // disposing DASCH.
        DataAccessSCH dasch = (DataAccessSCH)session.getAttribute(_KEY_DASCH);
        if (dasch != null)
        {
            session.removeAttribute(_KEY_DASCH);
            dasch.close();
            DBUtil.close(dasch.getConnection());
        }
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_FaInquiryRetrieval_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("", KEY_HIDDEN_STORAGE_DATE);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
    }

    /**
     *
     * @throws Exception
     */
    private void pager_SetPage()
            throws Exception
    {
        // get event source.
        String eventSource = viewState.getString(_KEY_PAGERSOURCE);
        if (eventSource == null)
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_Display_Click"))
        {
            // process call.
            btn_Display_Click_SetList();
        }
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
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaInquiryRetrievalSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaInquiryRetrievalSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaInquiryRetrievalSCHParams inparam = new FaInquiryRetrievalSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // clear.
            pul_Area.setEnabled(true);
            pul_WorkPlace.setEnabled(true);
            pul_Station.setEnabled(true);
            btn_Display.setEnabled(true);
            btn_Clear.setEnabled(true);
            btn_WorkStart.setEnabled(false);
            btn_ClearListInput.setEnabled(false);
            btn_AllCheck.setEnabled(false);
            btn_AllCheckClear.setEnabled(false);
            btn_ListClear.setEnabled(false);
            chk_LWorkListPrint.setEnabled(false);
            pul_LPrioriryFlag.setEnabled(false);
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
    private void btn_PItemCodeSearch_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PItemCodeSearch_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PItemCodeSearch_Click_DlgBack(DialogParameters dialogParams)
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
    private void btn_PItemNameSearch_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemNameParams inparam = new LstItemNameParams();
        inparam.set(LstItemNameParams.ITEM_NAME, txt_ItemName.getValue());
        inparam.set(LstItemNameParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PItemNameSearch_Click");
        redirect("/base/listbox/itemname/LstItemName.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PItemNameSearch_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemNameParams outparam = new LstItemNameParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstItemNameParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemNameParams.ITEM_NAME));

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
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaInquiryRetrievalDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new FaInquiryRetrievalDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            FaInquiryRetrievalDASCHParams inparam = new FaInquiryRetrievalDASCHParams();
            inparam.set(FaInquiryRetrievalDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaInquiryRetrievalDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(FaInquiryRetrievalDASCHParams.SEARCH_CONDITION, _grp_SearchCondition.getSelectedValue());
            inparam.set(FaInquiryRetrievalDASCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(FaInquiryRetrievalDASCHParams.ITEM_NAME, txt_ItemName.getValue());
            inparam.set(FaInquiryRetrievalDASCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(FaInquiryRetrievalDASCHParams.FROM_RM_NO, txt_FromRMNo.getValue());
            inparam.set(FaInquiryRetrievalDASCHParams.TO_RM_NO, txt_ToRMNo.getValue());
            inparam.set(FaInquiryRetrievalDASCHParams.LOCATION_NO, txt_Location.getValue());
            inparam.set(FaInquiryRetrievalDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_FaInquiryRetrieval.clear();

            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }
            else if (count > _pager.getMax())
            {
                message.setMsgResourceKey("6001023\t" + Formatter.getNumFormat(count)
                        + "\t" + Formatter.getNumFormat(_pager.getMax()));
            }
            else
            {
                message.setMsgResourceKey("6001022\t" + Formatter.getNumFormat(count));
            }

            // DASCH call.
            dasch.search(inparam);

            // save session.
            session.setAttribute(_KEY_DASCH, dasch);
            isSuccess = true;

            // clear.
            btn_WorkStart.setEnabled(true);
            btn_ClearListInput.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_ListClear.setEnabled(true);
            chk_LWorkListPrint.setEnabled(true);
            pul_LPrioriryFlag.setEnabled(true);
            pul_Area.setEnabled(false);
            pul_WorkPlace.setEnabled(false);
            pul_Station.setEnabled(false);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaInquiryRetrieval.clear();
        }
        finally
        {
            if (isSuccess)
            {
                // set list.
                btn_Display_Click_SetList();
            }
            else
            {
                if (dasch != null)
                {
                    dasch.close();
                }
                DBUtil.close(conn);
            }
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_SetList()
            throws Exception
    {
        FaInquiryRetrievalDASCH dasch = null;
        try
        {
            // get session.
            dasch = (FaInquiryRetrievalDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_FaInquiryRetrieval.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_FaInquiryRetrieval.getNewLine();
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(FaInquiryRetrievalDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(FaInquiryRetrievalDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(FaInquiryRetrievalDASCHParams.LOT_NO));
                line.setValue(KEY_LST_AREA_NO, outparam.get(FaInquiryRetrievalDASCHParams.AREA_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(FaInquiryRetrievalDASCHParams.LOCATION_NO));
                line.setValue(KEY_LST_MIXED_LOAD, outparam.get(FaInquiryRetrievalDASCHParams.MIXED_LOAD));
                line.setValue(KEY_LST_STOCK_QTY, outparam.get(FaInquiryRetrievalDASCHParams.ALLOCATE_QTY));
                line.setValue(KEY_LST_PICKING_QTY, outparam.get(FaInquiryRetrievalDASCHParams.PICKING_QTY));
                line.setValue(KEY_HIDDEN_STORAGE_DATE, outparam.get(FaInquiryRetrievalDASCHParams.STORAGE_DATE));
                line.setValue(KEY_HIDDEN_PALLET_ID, outparam.get(FaInquiryRetrievalDASCHParams.PALLET_ID));
                line.setValue(KEY_HIDDEN_STOCK_ID, outparam.get(FaInquiryRetrievalDASCHParams.STOCK_ID));
                line.setValue(KEY_HIDDEN_LAST_UPDATE_DATE, outparam.get(FaInquiryRetrievalDASCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_HIDDEN_CONSIGNOR_CODE, outparam.get(FaInquiryRetrievalDASCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_HIDDEN_AREA_TYPE, outparam.get(FaInquiryRetrievalDASCHParams.AREA_TYPE));
                lst_FaInquiryRetrieval_SetLineToolTip(line);
                _lcm_lst_FaInquiryRetrieval.add(line);
            }

            // clear.
            btn_WorkStart.setEnabled(true);
            btn_ClearListInput.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_ListClear.setEnabled(true);
            chk_LWorkListPrint.setEnabled(true);
            pul_LPrioriryFlag.setEnabled(true);
            pul_Area.setEnabled(false);
            pul_WorkPlace.setEnabled(false);
            pul_Station.setEnabled(false);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaInquiryRetrieval.clear();
            disposeDasch();
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
        rdo_Search_ItemCode.setChecked(true);
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        txt_LotNo.setValue(null);
        txt_FromRMNo.setValue(null);
        txt_ToRMNo.setValue(null);
        txt_Location.setValue(null);
        txt_ItemCode.setReadOnly(false);
        txt_ItemName.setReadOnly(false);
        txt_LotNo.setReadOnly(false);
        txt_FromRMNo.setReadOnly(false);
        txt_ToRMNo.setReadOnly(false);
        txt_Location.setReadOnly(true);
        btn_PItemCodeSearch.setEnabled(true);
        btn_PItemNameSearch.setEnabled(true);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_WorkStart_Click_Process()
            throws Exception
    {
        // input validation.
        pul_LPrioriryFlag.validate(this, true);

        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_FaInquiryRetrieval.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_FaInquiryRetrieval.get(i);
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

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaInquiryRetrievalSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaInquiryRetrievalSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_FaInquiryRetrieval.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_FaInquiryRetrieval.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                FaInquiryRetrievalSCHParams lineparam = new FaInquiryRetrievalSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(FaInquiryRetrievalSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                lineparam.set(FaInquiryRetrievalSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(FaInquiryRetrievalSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(FaInquiryRetrievalSCHParams.LOT_NO, line.getValue(KEY_LST_LOT_NO));
                lineparam.set(FaInquiryRetrievalSCHParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
                lineparam.set(FaInquiryRetrievalSCHParams.MIXED_LOAD, line.getValue(KEY_LST_MIXED_LOAD));
                lineparam.set(FaInquiryRetrievalSCHParams.STOCK_QTY, line.getValue(KEY_LST_STOCK_QTY));
                lineparam.set(FaInquiryRetrievalSCHParams.ALLOCATE_QTY, line.getValue(KEY_LST_STOCK_QTY));
                lineparam.set(FaInquiryRetrievalSCHParams.PICKING_QTY, line.getValue(KEY_LST_PICKING_QTY));
                lineparam.set(FaInquiryRetrievalSCHParams.ALL_QTY, line.getValue(KEY_LST_ALL_QTY));
                lineparam.set(FaInquiryRetrievalSCHParams.LAST_UPDATE_DATE, line.getValue(KEY_HIDDEN_LAST_UPDATE_DATE));
                lineparam.set(FaInquiryRetrievalSCHParams.STOCK_ID, line.getValue(KEY_HIDDEN_STOCK_ID));
                lineparam.set(FaInquiryRetrievalSCHParams.PUL_AREA_NO, _pdm_pul_Area.getSelectedValue());
                lineparam.set(FaInquiryRetrievalSCHParams.AREA_NO, line.getValue(KEY_LST_AREA_NO));
                lineparam.set(FaInquiryRetrievalSCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
                lineparam.set(FaInquiryRetrievalSCHParams.FROM_RM_NO, txt_FromRMNo.getValue());
                lineparam.set(FaInquiryRetrievalSCHParams.TO_RM_NO, txt_ToRMNo.getValue());
                lineparam.set(FaInquiryRetrievalSCHParams.WORK_PLACE, _pdm_pul_WorkPlace.getSelectedValue());
                lineparam.set(FaInquiryRetrievalSCHParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                lineparam.set(FaInquiryRetrievalSCHParams.PALLET_ID, line.getValue(KEY_HIDDEN_PALLET_ID));
                lineparam.set(FaInquiryRetrievalSCHParams.STATUS_FLAG, _pdm_pul_LPrioriryFlag.getSelectedValue());
                lineparam.set(FaInquiryRetrievalSCHParams.WORK_LIST_PRINT_FLAG, chk_LWorkListPrint.getValue());
                lineparam.set(FaInquiryRetrievalSCHParams.FUNCTION_ID, "FUNCTION_ID");
                lineparam.set(FaInquiryRetrievalSCHParams.AREA_TYPE, line.getValue(KEY_HIDDEN_AREA_TYPE));
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
                _lcm_lst_FaInquiryRetrieval.resetEditRow();
                _lcm_lst_FaInquiryRetrieval.resetHighlight();
                _lcm_lst_FaInquiryRetrieval.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_FaInquiryRetrieval.size(); i++)
            {
                ListCellLine line = _lcm_lst_FaInquiryRetrieval.get(i);
                lst_FaInquiryRetrieval.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(_pdm_pul_WorkPlace.getSelectedStringValue(), "");
                part11List.add(_pdm_pul_Station.getSelectedStringValue(), "");

                if (chk_LWorkListPrint.getChecked())
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                }

                part11List.add(_pdm_pul_LPrioriryFlag.getSelectedStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_AREA_NO), "");
                part11List.add(line.getViewString(KEY_LST_LOCATION_NO), "");

                if (StringUtil.isBlank(line.getValue(KEY_LST_MIXED_LOAD)))
                {
                    part11List.add("0", "");
                }
                else
                {
                    part11List.add("1", "");
                }

                part11List.add(line.getViewString(KEY_LST_STOCK_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PICKING_QTY), "");

                if (lst_FaInquiryRetrieval.getChecked(_lcm_lst_FaInquiryRetrieval.getColumnIndex(KEY_LST_ALL_QTY)))
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                }

                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_FaInquiryRetrieval.resetEditRow();
            _lcm_lst_FaInquiryRetrieval.resetHighlight();

            // clear.
            _lcm_lst_FaInquiryRetrieval.clear();
            btn_WorkStart.setEnabled(false);
            btn_ClearListInput.setEnabled(false);
            btn_AllCheck.setEnabled(false);
            btn_AllCheckClear.setEnabled(false);
            btn_ListClear.setEnabled(false);
            chk_LWorkListPrint.setEnabled(false);
            pul_LPrioriryFlag.setEnabled(false);
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
    private void btn_ClearListInput_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_FaInquiryRetrieval.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_FaInquiryRetrieval.get(i);
            lst_FaInquiryRetrieval.setCurrentRow(i);
            clearLine.setValue(KEY_LST_PICKING_QTY, null);
            clearLine.setValue(KEY_LST_ALL_QTY, Boolean.FALSE);
            lst_FaInquiryRetrieval_SetLineToolTip(clearLine);
            _lcm_lst_FaInquiryRetrieval.set(i, clearLine);
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
        for (int i = 1; i <= _lcm_lst_FaInquiryRetrieval.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_FaInquiryRetrieval.get(i);
            lst_FaInquiryRetrieval.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_FaInquiryRetrieval_SetLineToolTip(clearLine);
            _lcm_lst_FaInquiryRetrieval.set(i, clearLine);
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
        for (int i = 1; i <= _lcm_lst_FaInquiryRetrieval.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_FaInquiryRetrieval.get(i);
            lst_FaInquiryRetrieval.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
            clearLine.setValue(KEY_LST_ALL_QTY, Boolean.FALSE);
            lst_FaInquiryRetrieval_SetLineToolTip(clearLine);
            _lcm_lst_FaInquiryRetrieval.set(i, clearLine);
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
        _lcm_lst_FaInquiryRetrieval.clear();
        _pager.clear();
        btn_WorkStart.setEnabled(false);
        btn_ClearListInput.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_ListClear.setEnabled(false);
        chk_LWorkListPrint.setEnabled(false);
        pul_LPrioriryFlag.setEnabled(false);
        pul_Area.setEnabled(true);
        pul_WorkPlace.setEnabled(true);
        pul_Station.setEnabled(true);
        rdo_Search_ItemCode.setEnabled(true);
        rdo_Search_Location.setEnabled(true);
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
