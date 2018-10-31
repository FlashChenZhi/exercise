// $Id: StockMoveMntBusiness.java 7663 2010-03-17 11:36:27Z shibamoto $
package jp.co.daifuku.wms.stock.display.movemnt;

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
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
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
import jp.co.daifuku.wms.stock.display.movemnt.StockMoveMnt;
import jp.co.daifuku.wms.stock.display.movemnt.ViewStateKeys;
import jp.co.daifuku.wms.stock.schedule.StockMoveMntSCH;
import jp.co.daifuku.wms.stock.schedule.StockMoveMntSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7663 $, $Date:: 2010-03-17 20:36:27 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class StockMoveMntBusiness
        extends StockMoveMnt
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_StockMoveMaintenance(HIDDEN_JOB_NO) */
    private static final ListCellKey KEY_HIDDEN_JOB_NO = new ListCellKey("HIDDEN_JOB_NO", new StringCellColumn(), false, false);

    /** lst_StockMoveMaintenance(HIDDEN_RETRIEVALDATE) */
    private static final ListCellKey KEY_HIDDEN_RETRIEVALDATE = new ListCellKey("HIDDEN_RETRIEVALDATE", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), false, false);

    /** lst_StockMoveMaintenance(HIDDEN_RFTNO) */
    private static final ListCellKey KEY_HIDDEN_RFTNO = new ListCellKey("HIDDEN_RFTNO", new StringCellColumn(), false, false);

    /** lst_StockMoveMaintenance(HIDDEN_USER_NAME) */
    private static final ListCellKey KEY_HIDDEN_USER_NAME = new ListCellKey("HIDDEN_USER_NAME", new StringCellColumn(), false, false);

    /** lst_StockMoveMaintenance(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new CheckBoxColumn(), true, true);

    /** lst_StockMoveMaintenance(LST_FROM_AREA) */
    private static final ListCellKey KEY_LST_FROM_AREA = new ListCellKey("LST_FROM_AREA", new AreaCellColumn(), true, false);

    /** lst_StockMoveMaintenance(LST_FROM_LOCATION) */
    private static final ListCellKey KEY_LST_FROM_LOCATION = new ListCellKey("LST_FROM_LOCATION", new LocationCellColumn(), true, false);

    /** lst_StockMoveMaintenance(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_StockMoveMaintenance(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_StockMoveMaintenance(LST_LOT) */
    private static final ListCellKey KEY_LST_LOT = new ListCellKey("LST_LOT", new StringCellColumn(), true, false);

    /** lst_StockMoveMaintenance(LST_CASE_PACK) */
    private static final ListCellKey KEY_LST_CASE_PACK = new ListCellKey("LST_CASE_PACK", new NumberCellColumn(0), true, false);

    /** lst_StockMoveMaintenance(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY = new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StockMoveMaintenance(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY = new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StockMoveMaintenance(LST_RELOCATION_CASE_QTY) */
    private static final ListCellKey KEY_LST_RELOCATION_CASE_QTY = new ListCellKey("LST_RELOCATION_CASE_QTY", new NumberCellColumn(0), true, true);

    /** lst_StockMoveMaintenance(LST_RELOCATION_PIECE_QTY) */
    private static final ListCellKey KEY_LST_RELOCATION_PIECE_QTY = new ListCellKey("LST_RELOCATION_PIECE_QTY", new NumberCellColumn(0), true, true);

    /** lst_StockMoveMaintenance(LST_TO_AREA) */
    private static final ListCellKey KEY_LST_TO_AREA = new ListCellKey("LST_TO_AREA", new AreaCellColumn(), true, true);

    /** lst_StockMoveMaintenance(LST_TO_LOCATION) */
    private static final ListCellKey KEY_LST_TO_LOCATION = new ListCellKey("LST_TO_LOCATION", new StringCellColumn(), true, true);

    /** lst_StockMoveMaintenance keys */
    private static final ListCellKey[] LST_STOCKMOVEMAINTENANCE_KEYS = {
        KEY_HIDDEN_JOB_NO,
        KEY_HIDDEN_RETRIEVALDATE,
        KEY_HIDDEN_RFTNO,
        KEY_HIDDEN_USER_NAME,
        KEY_LST_CANCEL,
        KEY_LST_FROM_AREA,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT,
        KEY_LST_CASE_PACK,
        KEY_LST_PLAN_CASE_QTY,
        KEY_LST_RELOCATION_CASE_QTY,
        KEY_LST_TO_AREA,
        KEY_LST_FROM_LOCATION,
        KEY_LST_ITEM_NAME,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_RELOCATION_PIECE_QTY,
        KEY_LST_TO_LOCATION,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_FromMoveArea */
    private WmsAreaPullDownModel _pdm_pul_FromMoveArea;

    /** ListCellModel lst_StockMoveMaintenance */
    private ListCellModel _lcm_lst_StockMoveMaintenance;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public StockMoveMntBusiness()
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
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_FromMoveArea.
        _pdm_pul_FromMoveArea = new WmsAreaPullDownModel(pul_FromMoveArea, locale, ui);

        // initialize lst_StockMoveMaintenance.
        _lcm_lst_StockMoveMaintenance = new ListCellModel(lst_StockMoveMaintenance, LST_STOCKMOVEMAINTENANCE_KEYS, locale);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_CANCEL, true);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_FROM_AREA, true);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_FROM_LOCATION, true);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_LOT, true);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_CASE_PACK, true);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_RELOCATION_CASE_QTY, false);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_RELOCATION_PIECE_QTY, false);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_TO_AREA, false);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_TO_LOCATION, false);
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

            // load pul_FromMoveArea.
            _pdm_pul_FromMoveArea.init(conn, AreaType.FLOOR_AND_TEMP_AND_RECEIVE,StationType.ALL, "", true);
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
    private void lst_StockMoveMaintenance_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("", KEY_HIDDEN_RETRIEVALDATE);
        line.addToolTip("", KEY_HIDDEN_RFTNO);
        line.addToolTip("", KEY_HIDDEN_USER_NAME);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(pul_FromMoveArea);
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
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        chk_LIssueReport.setChecked(true);
        chk_LIssueReport.setEnabled(false);
        txt_Location.setReadOnly(true);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        pul_FromMoveArea.validate(this, true);
        txt_Location.validate(this, false);
        txt_ItemCode.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StockMoveMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StockMoveMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            StockMoveMntSCHParams inparam = new StockMoveMntSCHParams();
            inparam.set(StockMoveMntSCHParams.FROM_MOVE_AREA_NO, _pdm_pul_FromMoveArea.getSelectedValue());
            inparam.set(StockMoveMntSCHParams.LOCATION, txt_Location.getValue());
            inparam.set(StockMoveMntSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(StockMoveMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_StockMoveMaintenance.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StockMoveMaintenance.getNewLine();
                line.setValue(KEY_LST_FROM_AREA, outparam.get(StockMoveMntSCHParams.RETRIEVAL_AREA_NO));
                line.setValue(KEY_LST_FROM_LOCATION, outparam.get(StockMoveMntSCHParams.RETRIEVAL_LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(StockMoveMntSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(StockMoveMntSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT, outparam.get(StockMoveMntSCHParams.LOT_NO));
                line.setValue(KEY_LST_CASE_PACK, outparam.get(StockMoveMntSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(StockMoveMntSCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(StockMoveMntSCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_RELOCATION_CASE_QTY, outparam.get(StockMoveMntSCHParams.MOVE_CASE_QTY));
                line.setValue(KEY_LST_RELOCATION_PIECE_QTY, outparam.get(StockMoveMntSCHParams.MOVE_PIECE_QTY));
                line.setValue(KEY_LST_TO_AREA, outparam.get(StockMoveMntSCHParams.MOVE_AREA_NO));
                line.setValue(KEY_LST_TO_LOCATION, outparam.get(StockMoveMntSCHParams.MOVE_LOCATION_NO));
                viewState.setObject(ViewStateKeys.FROM_MOVE_AREA, _pdm_pul_FromMoveArea.getSelectedValue());
                viewState.setObject(ViewStateKeys.LOCATION, txt_Location.getValue());
                viewState.setObject(ViewStateKeys.ITEM_CODE, txt_ItemCode.getValue());
                line.setValue(KEY_HIDDEN_JOB_NO, outparam.get(StockMoveMntSCHParams.JOB_NO));
                line.setValue(KEY_HIDDEN_RETRIEVALDATE, outparam.get(StockMoveMntSCHParams.RETRIEVALDATE));
                line.setValue(KEY_HIDDEN_RFTNO, outparam.get(StockMoveMntSCHParams.RFT_NO));
                line.setValue(KEY_HIDDEN_USER_NAME, outparam.get(StockMoveMntSCHParams.USER_NAME));
                lst_StockMoveMaintenance_SetLineToolTip(line);
                _lcm_lst_StockMoveMaintenance.add(line);
            }

            // clear.
            btn_Set.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_AllClear.setEnabled(true);
            chk_LIssueReport.setEnabled(true);
            chk_LIssueReport.setChecked(true);
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
        _pdm_pul_FromMoveArea.setSelectedValue(null);
        txt_Location.setValue(null);
        txt_ItemCode.setValue(null);
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
        StockMoveMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StockMoveMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_StockMoveMaintenance.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_StockMoveMaintenance.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                StockMoveMntSCHParams lineparam = new StockMoveMntSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(StockMoveMntSCHParams.JOB_NO, line.getValue(KEY_HIDDEN_JOB_NO));
                lineparam.set(StockMoveMntSCHParams.RETRIEVALDATE, line.getValue(KEY_HIDDEN_RETRIEVALDATE));
                lineparam.set(StockMoveMntSCHParams.RFT_NO, line.getValue(KEY_HIDDEN_RFTNO));
                lineparam.set(StockMoveMntSCHParams.USER_NAME, line.getValue(KEY_HIDDEN_USER_NAME));
                lineparam.set(StockMoveMntSCHParams.CANCEL, line.getValue(KEY_LST_CANCEL));
                lineparam.set(StockMoveMntSCHParams.RETRIEVAL_AREA_NO, line.getValue(KEY_LST_FROM_AREA));
                lineparam.set(StockMoveMntSCHParams.RETRIEVAL_LOCATION_NO, line.getValue(KEY_LST_FROM_LOCATION));
                lineparam.set(StockMoveMntSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(StockMoveMntSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(StockMoveMntSCHParams.LOT_NO, line.getValue(KEY_LST_LOT));
                lineparam.set(StockMoveMntSCHParams.ENTERING_QTY, line.getValue(KEY_LST_CASE_PACK));
                lineparam.set(StockMoveMntSCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                lineparam.set(StockMoveMntSCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                lineparam.set(StockMoveMntSCHParams.MOVE_CASE_QTY, line.getValue(KEY_LST_RELOCATION_CASE_QTY));
                lineparam.set(StockMoveMntSCHParams.MOVE_PIECE_QTY, line.getValue(KEY_LST_RELOCATION_PIECE_QTY));
                lineparam.set(StockMoveMntSCHParams.MOVE_AREA_NO, line.getValue(KEY_LST_TO_AREA));
                lineparam.set(StockMoveMntSCHParams.MOVE_LOCATION_NO, line.getValue(KEY_LST_TO_LOCATION));
                lineparam.set(StockMoveMntSCHParams.L_ISSUE_REPORT, chk_LIssueReport.getValue());
                lineparam.set(StockMoveMntSCHParams.FUNCTION_ID, "M_FUCNTIONID");
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
                _lcm_lst_StockMoveMaintenance.resetEditRow();
                _lcm_lst_StockMoveMaintenance.resetHighlight();
                _lcm_lst_StockMoveMaintenance.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_StockMoveMaintenance.size(); i++)
            {
                ListCellLine line = _lcm_lst_StockMoveMaintenance.get(i);
                lst_StockMoveMaintenance.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_FROM_AREA), "");
                part11List.add(line.getViewString(KEY_LST_FROM_LOCATION), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT), "");
                part11List.add(line.getViewString(KEY_LST_CASE_PACK), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "");

                if (!lst_StockMoveMaintenance.getChecked(_lcm_lst_StockMoveMaintenance.getColumnIndex(KEY_LST_CANCEL)))
                {
                    part11List.add(line.getViewString(KEY_LST_RELOCATION_CASE_QTY), "");
                    part11List.add(line.getViewString(KEY_LST_RELOCATION_PIECE_QTY), "");
                    part11List.add(line.getViewString(KEY_LST_TO_AREA), "");
                    part11List.add(line.getViewString(KEY_LST_TO_LOCATION), "");
                    part11List.add(line.getViewString(KEY_HIDDEN_RETRIEVALDATE), "");
                    part11List.add(line.getViewString(KEY_HIDDEN_RFTNO), "");
                }
                else
                {
                    part11List.add(line.getViewString(KEY_HIDDEN_RETRIEVALDATE), "");
                    part11List.add(line.getViewString(KEY_HIDDEN_RFTNO), "");
                }

                if (chk_LIssueReport.getChecked())
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                }

                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_MODIFY), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_StockMoveMaintenance.resetEditRow();
            _lcm_lst_StockMoveMaintenance.resetHighlight();

            // set input parameters.
            StockMoveMntSCHParams inparam = new StockMoveMntSCHParams();
            inparam.set(StockMoveMntSCHParams.FROM_MOVE_AREA_NO, viewState.getObject(ViewStateKeys.FROM_MOVE_AREA));
            inparam.set(StockMoveMntSCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
            inparam.set(StockMoveMntSCHParams.LOCATION, viewState.getObject(ViewStateKeys.LOCATION));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_StockMoveMaintenance.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StockMoveMaintenance.getNewLine();
                line.setValue(KEY_HIDDEN_JOB_NO, outparam.get(StockMoveMntSCHParams.JOB_NO));
                line.setValue(KEY_HIDDEN_RETRIEVALDATE, outparam.get(StockMoveMntSCHParams.RETRIEVALDATE));
                line.setValue(KEY_HIDDEN_RFTNO, outparam.get(StockMoveMntSCHParams.RFTNO));
                line.setValue(KEY_HIDDEN_USER_NAME, outparam.get(StockMoveMntSCHParams.USER_NAME));
                line.setValue(KEY_LST_FROM_AREA, outparam.get(StockMoveMntSCHParams.RETRIEVAL_AREA_NO));
                line.setValue(KEY_LST_FROM_LOCATION, outparam.get(StockMoveMntSCHParams.RETRIEVAL_LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(StockMoveMntSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(StockMoveMntSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT, outparam.get(StockMoveMntSCHParams.LOT_NO));
                line.setValue(KEY_LST_CASE_PACK, outparam.get(StockMoveMntSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(StockMoveMntSCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(StockMoveMntSCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_RELOCATION_CASE_QTY, outparam.get(StockMoveMntSCHParams.MOVE_CASE_QTY));
                line.setValue(KEY_LST_RELOCATION_PIECE_QTY, outparam.get(StockMoveMntSCHParams.MOVE_PIECE_QTY));
                line.setValue(KEY_LST_TO_AREA, outparam.get(StockMoveMntSCHParams.MOVE_AREA_NO));
                line.setValue(KEY_LST_TO_LOCATION, outparam.get(StockMoveMntSCHParams.MOVE_LOCATION_NO));
                lst_StockMoveMaintenance_SetLineToolTip(line);
                _lcm_lst_StockMoveMaintenance.add(line);
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
        for (int i = 1; i <= _lcm_lst_StockMoveMaintenance.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_StockMoveMaintenance.get(i);
            lst_StockMoveMaintenance.setCurrentRow(i);
            clearLine.setValue(KEY_LST_CANCEL, Boolean.TRUE);
            lst_StockMoveMaintenance_SetLineToolTip(clearLine);
            _lcm_lst_StockMoveMaintenance.set(i, clearLine);
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
        for (int i = 1; i <= _lcm_lst_StockMoveMaintenance.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_StockMoveMaintenance.get(i);
            lst_StockMoveMaintenance.setCurrentRow(i);
            clearLine.setValue(KEY_LST_CANCEL, Boolean.FALSE);
            lst_StockMoveMaintenance_SetLineToolTip(clearLine);
            _lcm_lst_StockMoveMaintenance.set(i, clearLine);
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
        _lcm_lst_StockMoveMaintenance.clear();
        btn_Set.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        chk_LIssueReport.setEnabled(false);
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
