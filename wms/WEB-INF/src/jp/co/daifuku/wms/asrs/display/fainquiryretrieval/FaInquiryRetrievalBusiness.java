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
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
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
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.LocationFormatException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.Constants;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;
import jp.co.daifuku.wms.asrs.dasch.FaInquiryRetrievalDASCH;
import jp.co.daifuku.wms.asrs.dasch.FaInquiryRetrievalDASCHParams;
import jp.co.daifuku.wms.asrs.schedule.AsrsInParameter;
import jp.co.daifuku.wms.asrs.schedule.FaInquiryRetrievalSCH;
import jp.co.daifuku.wms.asrs.schedule.FaInquiryRetrievalSCHParams;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.Distribution;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.listbox.itemname.LstItemNameParams;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkspacePullDownModel;

/**
 * 問合せ出庫の画面処理を行います。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: nagao $
 */
public class FaInquiryRetrievalBusiness
        extends FaInquiryRetrieval
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // DFKLOOK start
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK end
    
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
    private ListCellModel _lcm_lst_FaInquiryRetrieval;
    
    // DFKLOOK start
    /** 入力商品チェック済みフラグ */
    private boolean _isItemCheck = false;
    // DFKLOOK end

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
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        // DFKLOOK start
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
        if (eventSource.startsWith("btn_WorkStart_Click"))
        {
            // process call.
            btn_WorkStart_Click_Process(eventSource);
        }
        else if (eventSource.startsWith("checkItem"))
        {
            // process call.
            _isItemCheck = true;
            btn_Display_Click_Process(eventSource);
        }
        // DFKLOOK end
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pul_Area_Change(ActionEvent e)
            throws Exception
    {
    	// DFKLOOK start
        Connection conn = null;
        try
        {
            // 棚の入力例を表示させます。
            lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue()));

            // set focus
            if (rdo_Search_ItemCode.getChecked())
            {
                setFocus(txt_ItemCode);
            }
            else
            {
                setFocus(txt_Location);
            }
            
            conn = ConnectionManager.getRequestConnection(this);
            // エリアプルダウンのエリアの種別にあわせて開始・終了RM-No.テキストを制御します。
            AreaController arc = new AreaController(conn, getClass());
            boolean typeAsrs = false;
            if (WmsParam.ALL_AREA_NO.equals(_pdm_pul_Area.getSelectedStringValue()))
            {
                rdo_Search_ItemCode_Click(e);
                rdo_Search_Location.setEnabled(false);
                typeAsrs = false;
            }
            else
            {
                rdo_Search_Location.setEnabled(true);
                typeAsrs = arc.getAreaType(_pdm_pul_Area.getSelectedStringValue()).equals(SystemDefine.AREA_TYPE_ASRS);
            }
            if (!typeAsrs)
            {
                txt_FromRMNo.setValue(null);
                txt_ToRMNo.setValue(null);
            }
            txt_FromRMNo.setReadOnly(!typeAsrs);
            txt_ToRMNo.setReadOnly(!typeAsrs);
        }
        finally
        {
            DBUtil.close(conn);
        }
    	// DFKLOOK end
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Search_ItemCode_Click(ActionEvent e)
            throws Exception
    {
    	//DFKLOOK:ここから修正
    	rdo_Search_ItemCode.setChecked(true);

    	// 入力不可エリア設定
    	txt_Location.setText("");
    	txt_Location.setReadOnly(true);

    	// 入力可能エリア設定
    	txt_ItemCode.setReadOnly(false);
    	txt_ItemName.setReadOnly(false);
    	txt_LotNo.setReadOnly(false);

        // エリアプルダウンのエリアの種別にあわせて開始・終了RM-No.テキストを制御します。
        AreaController arc = new AreaController(ConnectionManager.getRequestConnection(this), getClass());
        boolean typeAsrs = false;
        if (WmsParam.ALL_AREA_NO.equals(_pdm_pul_Area.getSelectedStringValue()))
        {
            rdo_Search_Location.setEnabled(false);
            typeAsrs = false;
        }
        else
        {
            rdo_Search_Location.setEnabled(true);
            typeAsrs = arc.getAreaType(_pdm_pul_Area.getSelectedStringValue()).equals(SystemDefine.AREA_TYPE_ASRS);
        }
        txt_FromRMNo.setReadOnly(!typeAsrs);
        txt_ToRMNo.setReadOnly(!typeAsrs);
    	btn_PItemCodeSearch.setEnabled(true);
    	btn_PItemNameSearch.setEnabled(true);

    	// set focus
    	setFocus(txt_ItemCode);
    	//DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Search_Location_Click(ActionEvent e)
            throws Exception
    {
    	//DFKLOOK:ここから修正
    	rdo_Search_Location.setChecked(true);

    	// 入力不可エリア設定
    	txt_ItemCode.setText("");
    	txt_ItemName.setText("");
    	txt_LotNo.setText("");
    	txt_FromRMNo.setText("");
    	txt_ToRMNo.setText("");
    	txt_ItemCode.setReadOnly(true);
    	txt_ItemName.setReadOnly(true);
    	txt_LotNo.setReadOnly(true);
    	txt_FromRMNo.setReadOnly(true);
    	txt_ToRMNo.setReadOnly(true);
    	btn_PItemCodeSearch.setEnabled(false);
    	btn_PItemNameSearch.setEnabled(false);

    	// 入力可能エリア設定
    	txt_Location.setReadOnly(false);

    	// set focus
    	setFocus(txt_Location);
    	//DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemCode_EnterKey(ActionEvent e)
            throws Exception
    {
        // DFKLOOK start
        if (StringUtil.isBlank(txt_ItemCode.getValue()))
        {
            setFocus(txt_ItemName);
            return;
        }

        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            Item item = ItemController.getItemInfo(txt_ItemCode.getText(), WmsParam.DEFAULT_CONSIGNOR_CODE, conn);

            if (item == null)
            {
                // 6023021 = 商品コードがマスタに登録されていません。
                message.setMsgResourceKey("6023021");
                setFocus(txt_ItemCode);
                return;
            }

            // output display.
            txt_ItemName.setValue(item.getItemName());

            // set focus.
            setFocus(txt_LotNo);
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
        // DFKLOOK end
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
        //DFKLOOK:ここから修正
        btn_Display_Click_Process(null);
        //DFKLOOK:ここまで修正
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
        // DFKLOOK start
        // process call.
        btn_WorkStart_Click_Process(null);
        // DFKLOOK end
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
        _lcm_lst_FaInquiryRetrieval = new ListCellModel(lst_FaInquiryRetrieval, LST_FAINQUIRYRETRIEVAL_KEYS, locale);
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

            //DFKLOOK:ここから修正
            // load pul_Area.
            _pdm_pul_Area.init(conn, AreaType.NOT_MOVING_TERM, StationType.RETRIEVAL, "", true);

            // load pul_WorkPlace.
            _pdm_pul_WorkPlace.init(conn, StationType.FLOORANDRETRIEVAL, "", Distribution.UNUSE, true);

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.FLOORANDRETRIEVAL, Distribution.ALL, Distribution.ALL, "", true);
            //DFKLOOK:ここまで修正

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
        // DFKLOOK ここから
        line.addToolTip("LBL-W0487", KEY_HIDDEN_STORAGE_DATE);
        // DFKLOOK ここまで
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

            // DFKLOOK ここから
            // 検索条件：商品コード
            txt_Location.setReadOnly(true);

            // 棚の入力例を表示させます。
            _pdm_pul_Area.setSelectedIndex(0);
            lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue()));

            // エリアプルダウンのエリアの種別にあわせて開始・終了RM-No.テキストを制御します。
            AreaController arc = new AreaController(conn, getClass());
            boolean typeAsrs = false;
            if (WmsParam.ALL_AREA_NO.equals(_pdm_pul_Area.getSelectedStringValue()))
            {
                rdo_Search_Location.setEnabled(false);
                typeAsrs = false;
            }
            else
            {
                rdo_Search_Location.setEnabled(true);
                typeAsrs = arc.getAreaType(_pdm_pul_Area.getSelectedStringValue()).equals(SystemDefine.AREA_TYPE_ASRS);
            }
            txt_FromRMNo.setReadOnly(!typeAsrs);
            txt_ToRMNo.setReadOnly(!typeAsrs);

            setFocus(txt_ItemCode);

            // set FUNCTION_ID
            try
            {

                FaInquiryRetrievalSCHParams inparam = new FaInquiryRetrievalSCHParams();
                inparam.set(FaInquiryRetrievalSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));

                Params outParam = sch.initFind(inparam);

                if (outParam != null)
                {
                	String printflg = outParam.getString(FaInquiryRetrievalSCHParams.WORK_LIST_PRINT_FLAG);
                    if (SystemDefine.KEYDATA_OFF.equals(printflg))
                    {
                        // 前回、チェックOFFだった場合は更新
                        chk_LWorkListPrint.setChecked(false);
                    }
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
            // DFKLOOK ここまで

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
    //DFKLOOK:ここから修正
    private void btn_Display_Click_Process(String eventSource)
    //DFKLOOK:ここまで修正
            throws Exception
    {
    	// DFKLOOK:ここから修正
        // input validation.
        pul_Area.validate(this, true);
        pul_WorkPlace.validate(this, true);
        pul_Station.validate(this, false);

        if (rdo_Search_ItemCode.getChecked())
        {
        	txt_ItemCode.validate(this, true);
        }
        else
        {
        	txt_ItemCode.validate(this, false);
        }
        
        if (rdo_Search_Location.getChecked())
        {
        	txt_Location.validate(this, true);
        }
        else
        {
        	txt_Location.validate(this, false);
        }
        txt_ItemName.validate(this, false);
        txt_LotNo.validate(this, false);
        txt_FromRMNo.validate(this, false);
        txt_ToRMNo.validate(this, false);
    	// DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaInquiryRetrievalDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // DFKLOOK start
            if (rdo_Search_ItemCode.getChecked())
            {
                // 商品コード指定なら、入力商品情報をチェック
                if (!checkItem())
                {
                    return;
                }
            }
            // DFKLOOK end
            
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
            
            //DFKLOOK:ここから修正
            if (rdo_Search_ItemCode.getChecked())
            {
            	inparam.set(FaInquiryRetrievalDASCHParams.ITEM_CODE, txt_ItemCode.getValue());
            	inparam.set(FaInquiryRetrievalDASCHParams.ITEM_NAME, txt_ItemName.getValue());
            	inparam.set(FaInquiryRetrievalDASCHParams.LOT_NO, txt_LotNo.getValue());
            	inparam.set(FaInquiryRetrievalDASCHParams.FROM_RM_NO, txt_FromRMNo.getValue());
            	inparam.set(FaInquiryRetrievalDASCHParams.TO_RM_NO, txt_ToRMNo.getValue());
            }
            else if (rdo_Search_Location.getChecked() && !StringUtil.isBlank(lbl_LocationStyle.getValue()))
            {
                String loc = WmsFormatter.toParamLocation(txt_Location.getStringValue(), lbl_LocationStyle.getStringValue());
                inparam.set(FaInquiryRetrievalDASCHParams.LOCATION_NO, loc);
            }
            //DFKLOOK:ここまで修正
            
            inparam.set(FaInquiryRetrievalDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_FaInquiryRetrieval.clear();

            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                
                // DFKLOOK 対象データ無しのためうちクリア
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
                // DFKLOOK ここまで
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

            // DFKLOOK set viewstate
            viewState.setObject(ViewStateKeys.AREA_NO, pul_Area.getSelectedValue());
            viewState.setObject(ViewStateKeys.WORK_PLACE, pul_WorkPlace.getSelectedValue());
            viewState.setObject(ViewStateKeys.STATION_NO, pul_Station.getSelectedValue());
            viewState.setObject(ViewStateKeys.ITEM_CODE, txt_ItemCode.getText());
            viewState.setObject(ViewStateKeys.ITEM_NAME, txt_ItemName.getText());
            viewState.setObject(ViewStateKeys.LOT_NO, txt_LotNo.getText());
            viewState.setObject(ViewStateKeys.FROM_RM_NO, txt_FromRMNo.getText());
            viewState.setObject(ViewStateKeys.TO_RM_NO, txt_ToRMNo.getText());
            viewState.setObject(ViewStateKeys.LOCATION_NO, txt_Location.getText());
            // DFKLOOK ここまで

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
        //DFKLOOK:ここから修正
        // 棚フォーマットで投げられたExceptionをここでキャッチ
        catch (LocationFormatException ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        //DFKLOOK:ここまで修正
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

	// DFKLOOK for list refresh
	/**
	*
	* @throws Exception
	*/
	private void refreshList()
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
	        inparam.set(FaInquiryRetrievalDASCHParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA_NO));
	        inparam.set(FaInquiryRetrievalDASCHParams.STATION_NO, viewState.getObject(ViewStateKeys.STATION_NO));
	        inparam.set(FaInquiryRetrievalDASCHParams.SEARCH_CONDITION, _grp_SearchCondition.getSelectedValue());
	        inparam.set(FaInquiryRetrievalDASCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
	        inparam.set(FaInquiryRetrievalDASCHParams.ITEM_NAME, viewState.getObject(ViewStateKeys.ITEM_NAME));
	        inparam.set(FaInquiryRetrievalDASCHParams.LOT_NO, viewState.getObject(ViewStateKeys.LOT_NO));
	        inparam.set(FaInquiryRetrievalDASCHParams.FROM_RM_NO, viewState.getObject(ViewStateKeys.FROM_RM_NO));
	        inparam.set(FaInquiryRetrievalDASCHParams.TO_RM_NO, viewState.getObject(ViewStateKeys.TO_RM_NO));
	        inparam.set(FaInquiryRetrievalDASCHParams.LOCATION_NO, viewState.getObject(ViewStateKeys.LOCATION_NO));
	        if (!StringUtil.isBlank(lbl_LocationStyle.getValue()))
	        {
		          String loc = WmsFormatter.toParamLocation(txt_Location.getStringValue(), lbl_LocationStyle.getStringValue());
		          inparam.set(FaInquiryRetrievalDASCHParams.LOCATION_NO, loc);
	        }
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
        // 棚フォーマットで投げられたExceptionをここでキャッチ
        catch (LocationFormatException ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaInquiryRetrieval.clear();
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
	// DFKLOOK ここまで

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
        
        //DFKLOOK:ここから修正
        Connection conn = null;
        try
        {
            conn = ConnectionManager.getRequestConnection(this);

            // エリアプルダウンのエリアの種別にあわせて開始・終了RM-No.テキストを制御します。
            AreaController arc = new AreaController(conn, getClass());
            boolean typeAsrs = false;
            if (WmsParam.ALL_AREA_NO.equals(_pdm_pul_Area.getSelectedStringValue()))
            {
                rdo_Search_Location.setEnabled(false);
                typeAsrs = false;
            }
            else
            {
                rdo_Search_Location.setEnabled(true);
                typeAsrs = arc.getAreaType(_pdm_pul_Area.getSelectedStringValue()).equals(SystemDefine.AREA_TYPE_ASRS);
            }
            txt_FromRMNo.setReadOnly(!typeAsrs);
            txt_ToRMNo.setReadOnly(!typeAsrs);
        }
        finally
        {
            DBUtil.close(conn);
        }
        //DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    // DFKLOOK start
    private void btn_WorkStart_Click_Process(String eventSource)
            throws Exception
    // DFKLOOK end
    {
        // input validation.
        pul_LPrioriryFlag.validate(this, true);

        // DFKLOOK start
        // reset editing row.
        _lcm_lst_FaInquiryRetrieval.resetEditRow();
        _lcm_lst_FaInquiryRetrieval.resetHighlight();
        // DFKLOOK end
        
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_FaInquiryRetrieval.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_FaInquiryRetrieval.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                //break;
            }

            // DFKLOOK start
            boolean isSelect = Boolean.valueOf(checkline.getStringValue(KEY_LST_SELECT)).booleanValue();
            boolean isAll = Boolean.valueOf(checkline.getStringValue(KEY_LST_ALL_QTY)).booleanValue();
            int stockQty = checkline.getNumberValue(KEY_LST_STOCK_QTY).intValue();
            int pickQty = checkline.getNumberValue(KEY_LST_PICKING_QTY).intValue();
            
            // 出庫数または全数の入力ありかつ選択なし
            if ((!StringUtil.isBlank(checkline.getValue(KEY_LST_PICKING_QTY)) || isAll) && !isSelect)
            {
                // No.{0} 出庫数を入力、または全数チェックする場合は、選択列をチェックしてください。
                message.setMsgResourceKey(WmsMessageFormatter.format(6023148, i));
                DisplayUtil.addHighlight(lst_FaInquiryRetrieval, i, ControlColor.Warning);
                return;
            }
            
            // 出庫数と全数の入力なしかつ選択あり
            if ((StringUtil.isBlank(checkline.getValue(KEY_LST_PICKING_QTY)) && !isAll) && isSelect)
            {
                if (SystemDefine.AREA_TYPE_ASRS.equals(checkline.getStringValue(KEY_HIDDEN_AREA_TYPE)))
                {
                    // 6023312=No.{0} 出庫数には0以上の値を入力してください。
                    message.setMsgResourceKey(WmsMessageFormatter.format(6023312, i));
                    DisplayUtil.addHighlight(lst_FaInquiryRetrieval, i, ControlColor.Warning);
                    return;
                }
                else
                {
                    // 6023281=No.{0} 出庫数には1以上の値を入力してください。
                    message.setMsgResourceKey(WmsMessageFormatter.format(6023281, i));
                    DisplayUtil.addHighlight(lst_FaInquiryRetrieval, i, ControlColor.Warning);
                    return;
                }
            }
            
            // 全数チェックありかつ出庫数入力あり
            if (isAll && !StringUtil.isBlank(checkline.getValue(KEY_LST_PICKING_QTY)))
            {
                // No.{0} 全数を選択する場合は、出庫数は入力できません。
                message.setMsgResourceKey(WmsMessageFormatter.format(6023145, i));
                DisplayUtil.addHighlight(lst_FaInquiryRetrieval, i, ControlColor.Warning);
                return;
            }
            
            // 在庫数のチェック
            if (stockQty == 0)
            {
                // 6023297=No.{0} 在庫数が0の場合は、作業できません。
                message.setMsgResourceKey(WmsMessageFormatter.format(6023297, i));
                DisplayUtil.addHighlight(lst_FaInquiryRetrieval, i, ControlColor.Warning);
                return;
            }
            
            // 出庫数の入力チェック
            if (!StringUtil.isBlank(checkline.getValue(KEY_LST_PICKING_QTY)))
            {
                if (SystemDefine.AREA_TYPE_ASRS.equals(checkline.getStringValue(KEY_HIDDEN_AREA_TYPE)))
                {
                    if (pickQty < 0)
                    {
                        // 6023312=No.{0} 出庫数には0以上の値を入力してください。
                        message.setMsgResourceKey(WmsMessageFormatter.format(6023312, i));
                        DisplayUtil.addHighlight(lst_FaInquiryRetrieval, i, ControlColor.Warning);
                        return;
                    }
                }
                else
                {
                    if (pickQty == 0)
                    {
                        // 6023289=平置きエリアからは0数出庫は出来ません。
                        message.setMsgResourceKey(WmsMessageFormatter.format(6023289));
                        DisplayUtil.addHighlight(lst_FaInquiryRetrieval, i, ControlColor.Warning);
                        return;
                    }
                    
                    if (pickQty < 1)
                    {
                        // 6023281=No.{0} 出庫数には1以上の値を入力してください。
                        message.setMsgResourceKey(WmsMessageFormatter.format(6023281, i));
                        DisplayUtil.addHighlight(lst_FaInquiryRetrieval, i, ControlColor.Warning);
                        return;
                    }
                }
                
                if (pickQty > WmsParam.MAX_TOTAL_QTY)
                {
                    // 6023207=No.{0} 出庫数には作業上限数{1}以下の値を入力してください。
                    message.setMsgResourceKey(WmsMessageFormatter.format(6023207, i,
                            WmsFormatter.getNumFormat(WmsParam.MAX_TOTAL_QTY)));
                    DisplayUtil.addHighlight(lst_FaInquiryRetrieval, i, ControlColor.Warning);
                    return;
                }
                
                if (pickQty > stockQty)
                {
                    // 6023296=No.{0} 出庫数には在庫数以下の値を入力してください。
                    message.setMsgResourceKey(WmsMessageFormatter.format(6023296, i));
                    DisplayUtil.addHighlight(lst_FaInquiryRetrieval, i, ControlColor.Warning);
                    return;
                }
            }
            // DFKLOOK end
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003001");
            return;
        }
        
        // DFKLOOK:ここから修正
        if (StringUtil.isBlank(eventSource))
        {
            // show confirm message. 設定しますか？
            this.setConfirm("MSG-W9000", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_WorkStart_Click");
            return;
        }
        // DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        //DFKLOOK:ここから修正
        boolean asrsAreaFlag = false;
        //DFKLOOK:ここまで修正
        
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

                // DFKLOOK get viewstate
                viewState.getObject(ViewStateKeys.AREA_NO);
                viewState.getObject(ViewStateKeys.STATION_NO);
                viewState.getObject(ViewStateKeys.ITEM_CODE);
                viewState.getObject(ViewStateKeys.ITEM_NAME);
                viewState.getObject(ViewStateKeys.LOCATION_NO);
                viewState.getObject(ViewStateKeys.FROM_RM_NO);
                viewState.getObject(ViewStateKeys.TO_RM_NO);
                viewState.getObject(ViewStateKeys.LOT_NO);
                // DFKLOOK ここまで
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
                //DFKLOOK:ここから修正
                // 選択されていても全数チェック無し、出庫数未入力のデータもスキップ
                if (!Boolean.valueOf(line.getStringValue(KEY_LST_ALL_QTY)).booleanValue() && StringUtil.isBlank(line.getValue(KEY_LST_PICKING_QTY)))
                {
                	lineparam.set(FaInquiryRetrievalSCHParams.PICKING_QTY, -1);
                }
                else
                {
                    lineparam.set(FaInquiryRetrievalSCHParams.PICKING_QTY, line.getValue(KEY_LST_PICKING_QTY));
                }
                //DFKLOOK:ここまで修正
                lineparam.set(FaInquiryRetrievalSCHParams.ALL_QTY, line.getValue(KEY_LST_ALL_QTY));
                lineparam.set(FaInquiryRetrievalSCHParams.LAST_UPDATE_DATE, line.getValue(KEY_HIDDEN_LAST_UPDATE_DATE));
                lineparam.set(FaInquiryRetrievalSCHParams.STOCK_ID, line.getValue(KEY_HIDDEN_STOCK_ID));
                lineparam.set(FaInquiryRetrievalSCHParams.PUL_AREA_NO, _pdm_pul_Area.getSelectedValue());
                lineparam.set(FaInquiryRetrievalSCHParams.AREA_NO, line.getValue(KEY_LST_AREA_NO));
                //DFKLOOK:ここから修正
                if (SystemDefine.AREA_TYPE_ASRS.equals(line.getStringValue(KEY_HIDDEN_AREA_TYPE)))
                {
                    asrsAreaFlag = true;
                }
                //DFKLOOK:ここまで修正
                lineparam.set(FaInquiryRetrievalSCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
                lineparam.set(FaInquiryRetrievalSCHParams.FROM_RM_NO, txt_FromRMNo.getValue());
                lineparam.set(FaInquiryRetrievalSCHParams.TO_RM_NO, txt_ToRMNo.getValue());
                lineparam.set(FaInquiryRetrievalSCHParams.WORK_PLACE, _pdm_pul_WorkPlace.getSelectedValue());
                lineparam.set(FaInquiryRetrievalSCHParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                lineparam.set(FaInquiryRetrievalSCHParams.PALLET_ID, line.getValue(KEY_HIDDEN_PALLET_ID));
                lineparam.set(FaInquiryRetrievalSCHParams.STATUS_FLAG, _pdm_pul_LPrioriryFlag.getSelectedValue());
                // DFKLOOK start
                if (chk_LWorkListPrint.getChecked())
                {
                	lineparam.set(FaInquiryRetrievalSCHParams.WORK_LIST_PRINT_FLAG, true);
                }
                // DFKLOOK end
                lineparam.set(FaInquiryRetrievalSCHParams.AREA_TYPE, line.getValue(KEY_HIDDEN_AREA_TYPE));
                inparamList.add(lineparam);
                // DFKLOOK start
                lineparam.set(FaInquiryRetrievalSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));
                // DFKLOOK end
            }
            // DFKLOOK ここから
            if (inparamList.isEmpty())
            {
                message.setMsgResourceKey("6003001");
                return;
            }
            // DFKLOOK ここまで

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
                // DFKLOOK star
                if (_pdm_pul_WorkPlace.getSelectedStringValue().startsWith(AsrsInParameter.SELECT_STATION_NONE))
                {
                	// 作業場・ステーションは空白で登録
	                part11List.add("", "");
	                part11List.add("", "");
                }
                else
                {
	                // 作業場はテキストを:で分割を行いステーションNo.を取得する。
	                part11List.add(pul_WorkPlace.getItem(_pdm_pul_WorkPlace.getSelectedIndex())
							.getText().split(":")[0], "");
					part11List.add(_pdm_pul_Station.getSelectedStringValue(), "");
                }
                // DFKLOOK end

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
            // DFKLOOK list refresh
            refreshList();

            // 搬送要求
            if (asrsAreaFlag)
            {
                // 出庫指示送信へRMIメッセージを使用して出庫要求を行います。
                SendRequestor req = new SendRequestor();
                req.retrieval();
            }
            // DFKLOOK ここまで
            
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_FaInquiryRetrieval.resetEditRow();
            _lcm_lst_FaInquiryRetrieval.resetHighlight();

            //DFKLOOK:ここから修正
            if (lst_FaInquiryRetrieval.getMaxRows() <= 1)
            {
                // process call.
                btn_ListClear_Click_Process();
            }
            //DFKLOOK:ここまで修正

        }
        //DFKLOOK:ここから修正
        // 棚フォーマットで投げられたExceptionをここでキャッチ
        catch (LocationFormatException ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        //DFKLOOK:ここまで修正
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
            // DFKLOOK reset list
            clearLine.setValue(KEY_LST_PICKING_QTY, Boolean.FALSE);
            if (lst_FaInquiryRetrieval.getChecked(_lcm_lst_FaInquiryRetrieval.getColumnIndex(KEY_LST_SELECT)))
            {
            	lst_FaInquiryRetrieval.setCellReadOnly(_lcm_lst_FaInquiryRetrieval.getColumnIndex(KEY_LST_PICKING_QTY), false);
            }
            clearLine.setValue(KEY_LST_ALL_QTY, Boolean.FALSE);
            // DFKLOOK ここまで
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
        
        //DFKLOOK:ここから修正
        Connection conn = null;
        try
        {
            conn = ConnectionManager.getRequestConnection(this);

            // エリアプルダウンのエリアの種別にあわせて開始・終了RM-No.テキストを制御します。
            AreaController arc = new AreaController(conn, getClass());
            boolean typeAsrs = false;
            if (WmsParam.ALL_AREA_NO.equals(_pdm_pul_Area.getSelectedStringValue()))
            {
                rdo_Search_Location.setEnabled(false);
                typeAsrs = false;
            }
            else
            {
                rdo_Search_Location.setEnabled(true);
                typeAsrs = arc.getAreaType(_pdm_pul_Area.getSelectedStringValue()).equals(SystemDefine.AREA_TYPE_ASRS);
                if (typeAsrs && rdo_Search_Location.getChecked())
                {
                    typeAsrs = false;
                }
            }
            txt_FromRMNo.setReadOnly(!typeAsrs);
            txt_ToRMNo.setReadOnly(!typeAsrs);
        }
        finally
        {
            DBUtil.close(conn);
        }
        //DFKLOOK:ここまで修正

        setFocus(txt_ItemCode);
    }
    
    // DFKLOOK start
    /**
     * 入力されている商品情報のチェックを行います。<br>
     * 該当商品が存在しない場合は、エラーメッセージを表示します。<br>
     * 入力商品コードと名称が一致しない場合、確認ダイアログを表示します。<br>
     * 上記のダイアログで確認後は、商品名称とソフトゾーンを補完します。
     * 
     * @return チェック結果
     * @throws Exception 
     */
    private boolean checkItem()
            throws Exception
    {
        Connection conn = null;
        
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            
            ItemController itemCon = new ItemController(conn, this.getClass());
            String consignor_code = WmsParam.DEFAULT_CONSIGNOR_CODE;
            String item_code = txt_ItemCode.getText();
            
            if (!_isItemCheck && !txt_ItemName.getText().equals(""))
            {
                if (itemCon.exists(item_code, consignor_code))
                {
                    // 入力商品コードと商品名称が一致するかチェック
                    String item_name = itemCon.getItemName(item_code, consignor_code);
                    if (!item_name.equals(txt_ItemName.getText()))
                    {
                        // 一致しない場合は、確認ダイアログを表示する
                        this.setConfirm("MSG-W9112", false, true);
                        viewState.setString(_KEY_CONFIRMSOURCE, "checkItem");
                        return false;
                    }
                }
                else
                {
                    // 6023021 = 商品コードがマスタに登録されていません。
                    message.setMsgResourceKey("6023021");
                    return false;
                }
            }
            else
            {
                // 商品情報の補完する
                Item item = ItemController.getItemInfo(item_code, consignor_code, conn);
    
                if (item == null)
                {
                    // 6023021 = 商品コードがマスタに登録されていません。
                    message.setMsgResourceKey("6023021");
                    return false;
                }
                
                // output display.
                txt_ItemName.setValue(item.getItemName());
            }

            // フラグ初期化
            _isItemCheck = false;
            return true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            return false;
        }
        finally
        {
            DBUtil.close(conn);
        }
    }
    // DFKLOOK end

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
