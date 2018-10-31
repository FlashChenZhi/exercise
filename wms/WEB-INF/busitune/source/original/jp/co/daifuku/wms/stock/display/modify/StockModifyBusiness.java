// $Id: StockModifyBusiness.java 7837 2010-04-21 02:13:53Z kishimoto $
package jp.co.daifuku.wms.stock.display.modify;

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
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
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
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.stock.display.modify.StockModify;
import jp.co.daifuku.wms.stock.display.modify.ViewStateKeys;
import jp.co.daifuku.wms.stock.schedule.StockModifySCH;
import jp.co.daifuku.wms.stock.schedule.StockModifySCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7837 $, $Date:: 2010-04-21 11:13:53 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class StockModifyBusiness
        extends StockModify
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_StockInfo(HIDDEN_STOCKID) */
    private static final ListCellKey KEY_HIDDEN_STOCKID = new ListCellKey("HIDDEN_STOCKID", new StringCellColumn(), false, false);

    /** lst_StockInfo(HIDDEN_LASTUPDATEDATE) */
    private static final ListCellKey KEY_HIDDEN_LASTUPDATEDATE = new ListCellKey("HIDDEN_LASTUPDATEDATE", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMSS), false, false);

    /** lst_StockInfo(HIDDEN_STORAGE_DAY) */
    private static final ListCellKey KEY_HIDDEN_STORAGE_DAY = new ListCellKey("HIDDEN_STORAGE_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), false, false);

    /** lst_StockInfo(HIDDEN_STORAGE_TIME) */
    private static final ListCellKey KEY_HIDDEN_STORAGE_TIME = new ListCellKey("HIDDEN_STORAGE_TIME", new DateCellColumn(null, TIME_FORMAT.HMS), false, false);

    /** lst_StockInfo(HIDDEN_RETRIEVAL_DAY) */
    private static final ListCellKey KEY_HIDDEN_RETRIEVAL_DAY = new ListCellKey("HIDDEN_RETRIEVAL_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), false, false);

    /** lst_StockInfo(LST_DELETE) */
    private static final ListCellKey KEY_LST_DELETE = new ListCellKey("LST_DELETE", new StringCellColumn(), true, false);

    /** lst_StockInfo(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO = new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_StockInfo(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO = new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_StockInfo(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_StockInfo(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_StockInfo(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_StockInfo(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_StockInfo(LST_STOCK_CASE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_CASE_QTY = new ListCellKey("LST_STOCK_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StockInfo(LST_STOCK_PIECE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_PIECE_QTY = new ListCellKey("LST_STOCK_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StockInfo(LST_ALLOC_CASE_QTY) */
    private static final ListCellKey KEY_LST_ALLOC_CASE_QTY = new ListCellKey("LST_ALLOC_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StockInfo(LST_ALLOC_PIECE_QTY) */
    private static final ListCellKey KEY_LST_ALLOC_PIECE_QTY = new ListCellKey("LST_ALLOC_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StockInfo(LST_MODIFY_CASE_QTY) */
    private static final ListCellKey KEY_LST_MODIFY_CASE_QTY = new ListCellKey("LST_MODIFY_CASE_QTY", new NumberCellColumn(0), true, true);

    /** lst_StockInfo(LST_MODIFY_PIECE_QTY) */
    private static final ListCellKey KEY_LST_MODIFY_PIECE_QTY = new ListCellKey("LST_MODIFY_PIECE_QTY", new NumberCellColumn(0), true, true);

    /** lst_StockInfo(LST_RETRIEVAL_DAY) */
    private static final ListCellKey KEY_LST_RETRIEVAL_DAY = new ListCellKey("LST_RETRIEVAL_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, true);

    /** lst_StockInfo(LST_STORAGE_DAY) */
    private static final ListCellKey KEY_LST_STORAGE_DAY = new ListCellKey("LST_STORAGE_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, true);

    /** lst_StockInfo(LST_STORAGE_TIME) */
    private static final ListCellKey KEY_LST_STORAGE_TIME = new ListCellKey("LST_STORAGE_TIME", new DateCellColumn(null, TIME_FORMAT.HMS), true, true);

    /** lst_StockInfo(LST_JAN) */
    private static final ListCellKey KEY_LST_JAN = new ListCellKey("LST_JAN", new StringCellColumn(), true, false);

    /** lst_StockInfo(LST_ITF) */
    private static final ListCellKey KEY_LST_ITF = new ListCellKey("LST_ITF", new StringCellColumn(), true, false);

    /** lst_StockInfo keys */
    private static final ListCellKey[] LST_STOCKINFO_KEYS = {
        KEY_HIDDEN_STOCKID,
        KEY_HIDDEN_LASTUPDATEDATE,
        KEY_HIDDEN_STORAGE_DAY,
        KEY_HIDDEN_STORAGE_TIME,
        KEY_HIDDEN_RETRIEVAL_DAY,
        KEY_LST_DELETE,
        KEY_LST_AREA_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT_NO,
        KEY_LST_ENTERING_QTY,
        KEY_LST_STOCK_CASE_QTY,
        KEY_LST_ALLOC_CASE_QTY,
        KEY_LST_MODIFY_CASE_QTY,
        KEY_LST_RETRIEVAL_DAY,
        KEY_LST_STORAGE_DAY,
        KEY_LST_JAN,
        KEY_LST_LOCATION_NO,
        KEY_LST_ITEM_NAME,
        KEY_LST_STOCK_PIECE_QTY,
        KEY_LST_ALLOC_PIECE_QTY,
        KEY_LST_MODIFY_PIECE_QTY,
        KEY_LST_STORAGE_TIME,
        KEY_LST_ITF,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** ListCellModel lst_StockInfo */
    private ListCellModel _lcm_lst_StockInfo;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public StockModifyBusiness()
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
    public void btn_ModifySubmit_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ModifySubmit_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_StockInfo_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_StockInfo.getActiveCol();

        // choose process.
        if (_lcm_lst_StockInfo.getColumnIndex(KEY_LST_DELETE) == activeCol)
        {
            // process call.
            lst_Delete_Click_Process();
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

        // initialize lst_StockInfo.
        _lcm_lst_StockInfo = new ListCellModel(lst_StockInfo, LST_STOCKINFO_KEYS, locale);
        _lcm_lst_StockInfo.setToolTipVisible(KEY_LST_DELETE, true);
        _lcm_lst_StockInfo.setToolTipVisible(KEY_LST_AREA_NO, true);
        _lcm_lst_StockInfo.setToolTipVisible(KEY_LST_LOCATION_NO, true);
        _lcm_lst_StockInfo.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_StockInfo.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_StockInfo.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_StockInfo.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_StockInfo.setToolTipVisible(KEY_LST_STOCK_CASE_QTY, true);
        _lcm_lst_StockInfo.setToolTipVisible(KEY_LST_STOCK_PIECE_QTY, true);
        _lcm_lst_StockInfo.setToolTipVisible(KEY_LST_ALLOC_CASE_QTY, true);
        _lcm_lst_StockInfo.setToolTipVisible(KEY_LST_ALLOC_PIECE_QTY, true);
        _lcm_lst_StockInfo.setToolTipVisible(KEY_LST_MODIFY_CASE_QTY, false);
        _lcm_lst_StockInfo.setToolTipVisible(KEY_LST_MODIFY_PIECE_QTY, false);
        _lcm_lst_StockInfo.setToolTipVisible(KEY_LST_RETRIEVAL_DAY, false);
        _lcm_lst_StockInfo.setToolTipVisible(KEY_LST_STORAGE_DAY, false);
        _lcm_lst_StockInfo.setToolTipVisible(KEY_LST_STORAGE_TIME, false);
        _lcm_lst_StockInfo.setToolTipVisible(KEY_LST_JAN, true);
        _lcm_lst_StockInfo.setToolTipVisible(KEY_LST_ITF, true);
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
            _pdm_pul_Area.init(conn, AreaType.FLOOR_AND_TEMP_AND_RECEIVE,StationType.ALL, "", true);
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
    private void lst_StockInfo_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-W0002", KEY_LST_JAN);
        line.addToolTip("LBL-W0017", KEY_LST_ITF);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(pul_Area);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_ModifySubmit.setEnabled(false);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_Location.validate(this, false);
        txt_ItemCode.validate(this, false);
        pul_Area.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StockModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StockModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            StockModifySCHParams inparam = new StockModifySCHParams();
            inparam.set(StockModifySCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(StockModifySCHParams.LOCATION_NO, txt_Location.getValue());
            inparam.set(StockModifySCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(StockModifySCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_StockInfo.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StockInfo.getNewLine();
                line.setValue(KEY_HIDDEN_STOCKID, outparam.get(StockModifySCHParams.STOCKID));
                line.setValue(KEY_HIDDEN_LASTUPDATEDATE, outparam.get(StockModifySCHParams.LASTUPDATEDATE));
                line.setValue(KEY_LST_AREA_NO, outparam.get(StockModifySCHParams.AREA_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(StockModifySCHParams.LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(StockModifySCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(StockModifySCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(StockModifySCHParams.LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(StockModifySCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(StockModifySCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(StockModifySCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_ALLOC_CASE_QTY, outparam.get(StockModifySCHParams.ALLOC_CASE_QTY));
                line.setValue(KEY_LST_ALLOC_PIECE_QTY, outparam.get(StockModifySCHParams.ALLOC_PIECE_QTY));
                line.setValue(KEY_LST_MODIFY_CASE_QTY, outparam.get(StockModifySCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_MODIFY_PIECE_QTY, outparam.get(StockModifySCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_RETRIEVAL_DAY, outparam.get(StockModifySCHParams.RETRIEVAL_DAY));
                line.setValue(KEY_LST_STORAGE_DAY, outparam.get(StockModifySCHParams.STORAGE_DAY));
                line.setValue(KEY_LST_STORAGE_TIME, outparam.get(StockModifySCHParams.STORAGE_TIME));
                line.setValue(KEY_LST_JAN, outparam.get(StockModifySCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(StockModifySCHParams.ITF));
                viewState.setObject(ViewStateKeys.AREA_NO, _pdm_pul_Area.getSelectedValue());
                viewState.setObject(ViewStateKeys.LOCATION_NO, txt_Location.getValue());
                viewState.setObject(ViewStateKeys.ITEM_CODE, txt_ItemCode.getValue());
                line.setValue(KEY_HIDDEN_STORAGE_DAY, outparam.get(StockModifySCHParams.STORAGE_DAY));
                line.setValue(KEY_HIDDEN_STORAGE_TIME, outparam.get(StockModifySCHParams.STORAGE_TIME));
                line.setValue(KEY_HIDDEN_RETRIEVAL_DAY, outparam.get(StockModifySCHParams.RETRIEVAL_DAY));
                lst_StockInfo_SetLineToolTip(line);
                _lcm_lst_StockInfo.add(line);
            }

            // clear.
            btn_ModifySubmit.setEnabled(true);
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
        _pdm_pul_Area.setSelectedValue(null);
        txt_Location.setValue(null);
        txt_ItemCode.setValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ModifySubmit_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_StockInfo.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_StockInfo.get(i);
            if (!(checkline.isAppend() || checkline.isEdited()))
            {
                continue;
            }

            existEditedRow = true;
            lst_StockInfo.setCurrentRow(i);
            lst_StockInfo.validate(checkline.getIndex(KEY_LST_MODIFY_CASE_QTY), false);
            lst_StockInfo.validate(checkline.getIndex(KEY_LST_MODIFY_PIECE_QTY), false);
            lst_StockInfo.validate(checkline.getIndex(KEY_LST_RETRIEVAL_DAY), false);
            lst_StockInfo.validate(checkline.getIndex(KEY_LST_STORAGE_DAY), false);
            lst_StockInfo.validate(checkline.getIndex(KEY_LST_STORAGE_TIME), false);
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
        StockModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StockModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_StockInfo.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_StockInfo.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                StockModifySCHParams lineparam = new StockModifySCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(StockModifySCHParams.STOCKID, line.getValue(KEY_HIDDEN_STOCKID));
                lineparam.set(StockModifySCHParams.LASTUPDATEDATE, line.getValue(KEY_HIDDEN_LASTUPDATEDATE));
                lineparam.set(StockModifySCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(StockModifySCHParams.STOCK_CASE_QTY, line.getValue(KEY_LST_STOCK_CASE_QTY));
                lineparam.set(StockModifySCHParams.STOCK_PIECE_QTY, line.getValue(KEY_LST_STOCK_PIECE_QTY));
                lineparam.set(StockModifySCHParams.ALLOC_CASE_QTY, line.getValue(KEY_LST_ALLOC_CASE_QTY));
                lineparam.set(StockModifySCHParams.ALLOC_PIECE_QTY, line.getValue(KEY_LST_ALLOC_PIECE_QTY));
                lineparam.set(StockModifySCHParams.MODIFY_CASE_QTY, line.getValue(KEY_LST_MODIFY_CASE_QTY));
                lineparam.set(StockModifySCHParams.MODIFY_PIECE_QTY, line.getValue(KEY_LST_MODIFY_PIECE_QTY));
                lineparam.set(StockModifySCHParams.RETRIEVAL_DAY, line.getValue(KEY_LST_RETRIEVAL_DAY));
                lineparam.set(StockModifySCHParams.STORAGE_DAY, line.getValue(KEY_LST_STORAGE_DAY));
                lineparam.set(StockModifySCHParams.STORAGE_TIME, line.getValue(KEY_LST_STORAGE_TIME));
                lineparam.set(StockModifySCHParams.JAN, line.getValue(KEY_LST_JAN));
                lineparam.set(StockModifySCHParams.ITF, line.getValue(KEY_LST_ITF));
                lineparam.set(StockModifySCHParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA_NO));
                lineparam.set(StockModifySCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
                lineparam.set(StockModifySCHParams.LOCATION_NO, viewState.getObject(ViewStateKeys.LOCATION_NO));
                lineparam.set(StockModifySCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
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
                _lcm_lst_StockInfo.resetEditRow();
                _lcm_lst_StockInfo.resetHighlight();
                _lcm_lst_StockInfo.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_StockInfo.size(); i++)
            {
                ListCellLine line = _lcm_lst_StockInfo.get(i);
                lst_StockInfo.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_AREA_NO), "");
                part11List.add(line.getViewString(KEY_LST_LOCATION_NO), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_STOCK_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_STOCK_PIECE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_ALLOC_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_ALLOC_PIECE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_MODIFY_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_MODIFY_PIECE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_RETRIEVAL_DAY), "");
                part11List.add(line.getViewString(KEY_LST_STORAGE_DAY), line.getViewString(KEY_LST_STORAGE_TIME), "");
                part11List.add(line.getViewString(KEY_LST_JAN), "");
                part11List.add(line.getViewString(KEY_LST_ITF), "");
                part11List.add(line.getViewString(KEY_HIDDEN_RETRIEVAL_DAY), "");
                part11List.add(line.getViewString(KEY_HIDDEN_STORAGE_DAY), line.getViewString(KEY_HIDDEN_STORAGE_TIME), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_MODIFY), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_StockInfo.resetEditRow();
            _lcm_lst_StockInfo.resetHighlight();

            // set input parameters.
            StockModifySCHParams inparam = new StockModifySCHParams();
            inparam.set(StockModifySCHParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA_NO));
            inparam.set(StockModifySCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
            inparam.set(StockModifySCHParams.LOCATION_NO, viewState.getObject(ViewStateKeys.LOCATION_NO));
            inparam.set(StockModifySCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_StockInfo.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StockInfo.getNewLine();
                line.setValue(KEY_HIDDEN_STOCKID, outparam.get(StockModifySCHParams.STOCKID));
                line.setValue(KEY_HIDDEN_LASTUPDATEDATE, outparam.get(StockModifySCHParams.LASTUPDATEDATE));
                line.setValue(KEY_LST_AREA_NO, outparam.get(StockModifySCHParams.AREA_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(StockModifySCHParams.LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(StockModifySCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(StockModifySCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(StockModifySCHParams.LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(StockModifySCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(StockModifySCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(StockModifySCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_ALLOC_CASE_QTY, outparam.get(StockModifySCHParams.ALLOC_CASE_QTY));
                line.setValue(KEY_LST_ALLOC_PIECE_QTY, outparam.get(StockModifySCHParams.ALLOC_PIECE_QTY));
                line.setValue(KEY_LST_MODIFY_CASE_QTY, outparam.get(StockModifySCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_MODIFY_PIECE_QTY, outparam.get(StockModifySCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_RETRIEVAL_DAY, outparam.get(StockModifySCHParams.RETRIEVAL_DAY));
                line.setValue(KEY_LST_STORAGE_DAY, outparam.get(StockModifySCHParams.STORAGE_DAY));
                line.setValue(KEY_LST_STORAGE_TIME, outparam.get(StockModifySCHParams.STORAGE_TIME));
                line.setValue(KEY_LST_JAN, outparam.get(StockModifySCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(StockModifySCHParams.ITF));
                line.setValue(KEY_HIDDEN_STORAGE_DAY, outparam.get(StockModifySCHParams.STORAGE_DAY));
                line.setValue(KEY_HIDDEN_STORAGE_TIME, outparam.get(StockModifySCHParams.STORAGE_TIME));
                line.setValue(KEY_HIDDEN_RETRIEVAL_DAY, outparam.get(StockModifySCHParams.RETRIEVAL_DAY));
                lst_StockInfo_SetLineToolTip(line);
                _lcm_lst_StockInfo.add(line);
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
    private void lst_Delete_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_StockInfo.getActiveRow();
        lst_StockInfo.setCurrentRow(row);
        ListCellLine line = _lcm_lst_StockInfo.get(row);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StockModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StockModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            StockModifySCHParams inparam = new StockModifySCHParams();
            inparam.setProcessFlag(ProcessFlag.DELETE);
            inparam.setRowIndex(row);
            inparam.set(StockModifySCHParams.STOCKID, line.getValue(KEY_HIDDEN_STOCKID));
            inparam.set(StockModifySCHParams.LASTUPDATEDATE, line.getValue(KEY_HIDDEN_LASTUPDATEDATE));
            inparam.set(StockModifySCHParams.AREA_NO, line.getValue(KEY_LST_AREA_NO));
            inparam.set(StockModifySCHParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
            inparam.set(StockModifySCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            if (!sch.check(inparam))
            {
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_StockInfo.resetEditRow();
                _lcm_lst_StockInfo.resetHighlight();
                _lcm_lst_StockInfo.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(line.getViewString(KEY_LST_AREA_NO), "");
            part11List.add(line.getViewString(KEY_LST_LOCATION_NO), "");
            part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
            part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
            part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "0");
            part11List.add(line.getViewString(KEY_LST_STOCK_CASE_QTY), "0");
            part11List.add(line.getViewString(KEY_LST_STOCK_PIECE_QTY), "0");
            part11List.add(line.getViewString(KEY_LST_ALLOC_CASE_QTY), "0");
            part11List.add(line.getViewString(KEY_LST_ALLOC_PIECE_QTY), "0");
            part11List.add(line.getViewString(KEY_HIDDEN_RETRIEVAL_DAY), "");
            part11List.add(line.getViewString(KEY_HIDDEN_STORAGE_DAY), line.getViewString(KEY_HIDDEN_STORAGE_TIME), "");
            part11List.add(line.getViewString(KEY_LST_JAN), "");
            part11List.add(line.getViewString(KEY_LST_ITF), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_DELETE), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            lst_StockInfo.removeRow(row);
            _lcm_lst_StockInfo.resetEditRow();
            _lcm_lst_StockInfo.resetHighlight();
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