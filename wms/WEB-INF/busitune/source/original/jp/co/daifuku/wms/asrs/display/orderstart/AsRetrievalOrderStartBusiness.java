// $Id: AsRetrievalOrderStartBusiness.java 7543 2010-03-15 00:25:17Z ota $
package jp.co.daifuku.wms.asrs.display.orderstart;

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
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
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
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.orderstart.AsRetrievalOrderStart;
import jp.co.daifuku.wms.asrs.display.orderstart.ViewStateKeys;
import jp.co.daifuku.wms.asrs.schedule.AsRetrievalOrderStartSCH;
import jp.co.daifuku.wms.asrs.schedule.AsRetrievalOrderStartSCHParams;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkspacePullDownModel;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7543 $, $Date:: 2010-03-15 09:25:17 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class AsRetrievalOrderStartBusiness
        extends AsRetrievalOrderStart
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ASRSRetrievalStart(HIDDEN_LINE_COUNT) */
    private static final ListCellKey KEY_HIDDEN_LINE_COUNT = new ListCellKey("HIDDEN_LINE_COUNT", new StringCellColumn(), false, false);

    /** lst_ASRSRetrievalStart(HIDDEN_SETTING_UKEYS) */
    private static final ListCellKey KEY_HIDDEN_SETTING_UKEYS = new ListCellKey("HIDDEN_SETTING_UKEYS", new StringCellColumn(), false, false);

    /** lst_ASRSRetrievalStart(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_ASRSRetrievalStart(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO = new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalStart(LST_ORDER_NO) */
    private static final ListCellKey KEY_LST_ORDER_NO = new ListCellKey("LST_ORDER_NO", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalStart(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE = new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalStart(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME = new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalStart(LST_STATION_NO) */
    private static final ListCellKey KEY_LST_STATION_NO = new ListCellKey("LST_STATION_NO", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalStart(LST_STATION_NAME) */
    private static final ListCellKey KEY_LST_STATION_NAME = new ListCellKey("LST_STATION_NAME", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalStart(LST_DETAIL_COUNT) */
    private static final ListCellKey KEY_LST_DETAIL_COUNT = new ListCellKey("LST_DETAIL_COUNT", new NumberCellColumn(0), true, false);

    /** lst_ASRSRetrievalStart keys */
    private static final ListCellKey[] LST_ASRSRETRIEVALSTART_KEYS = {
        KEY_HIDDEN_LINE_COUNT,
        KEY_HIDDEN_SETTING_UKEYS,
        KEY_LST_SELECT,
        KEY_LST_BATCH_NO,
        KEY_LST_ORDER_NO,
        KEY_LST_CUSTOMER_CODE,
        KEY_LST_STATION_NO,
        KEY_LST_DETAIL_COUNT,
        KEY_LST_CUSTOMER_NAME,
        KEY_LST_STATION_NAME,
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

    /** ListCellModel lst_ASRSRetrievalStart */
    private ListCellModel _lcm_lst_ASRSRetrievalStart;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsRetrievalOrderStartBusiness()
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
    public void btn_RetrievalStart_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_RetrievalStart_Click_Process();
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

        // initialize lst_ASRSRetrievalStart.
        _lcm_lst_ASRSRetrievalStart = new ListCellModel(lst_ASRSRetrievalStart, LST_ASRSRETRIEVALSTART_KEYS, locale);
        _lcm_lst_ASRSRetrievalStart.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_ASRSRetrievalStart.setToolTipVisible(KEY_LST_BATCH_NO, false);
        _lcm_lst_ASRSRetrievalStart.setToolTipVisible(KEY_LST_ORDER_NO, false);
        _lcm_lst_ASRSRetrievalStart.setToolTipVisible(KEY_LST_CUSTOMER_CODE, false);
        _lcm_lst_ASRSRetrievalStart.setToolTipVisible(KEY_LST_CUSTOMER_NAME, false);
        _lcm_lst_ASRSRetrievalStart.setToolTipVisible(KEY_LST_STATION_NO, false);
        _lcm_lst_ASRSRetrievalStart.setToolTipVisible(KEY_LST_STATION_NAME, false);
        _lcm_lst_ASRSRetrievalStart.setToolTipVisible(KEY_LST_DETAIL_COUNT, false);
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
            _pdm_pul_Area.init(conn, AreaType.ASRS, StationType.RETRIEVAL, "", false);

            // load pul_WorkPlace.
            _pdm_pul_WorkPlace.init(conn, StationType.RETRIEVAL);

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.RETRIEVAL, Distribution.ALL, Distribution.ALL);
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
    private void lst_ASRSRetrievalStart_SetLineToolTip(ListCellLine line)
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
        btn_RetrievalStart.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_ListClear.setEnabled(false);
        chk_LIssueReport.setEnabled(false);
        chk_LIssueReport.setChecked(true);
        txt_LRRetrievalPlanDate.setReadOnly(true);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_RetrievalPlanDate.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_OrderNoFrom.validate(this, false);
        txt_OrderNoTo.validate(this, false);
        pul_Area.validate(this, true);
        pul_WorkPlace.validate(this, true);
        pul_Station.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsRetrievalOrderStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsRetrievalOrderStartSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsRetrievalOrderStartSCHParams inparam = new AsRetrievalOrderStartSCHParams();
            inparam.set(AsRetrievalOrderStartSCHParams.RETRIEVAL_PLAN_DATE, txt_RetrievalPlanDate.getValue());
            inparam.set(AsRetrievalOrderStartSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(AsRetrievalOrderStartSCHParams.ORDER_NO_FROM, txt_OrderNoFrom.getValue());
            inparam.set(AsRetrievalOrderStartSCHParams.ORDER_NO_TO, txt_OrderNoTo.getValue());
            inparam.set(AsRetrievalOrderStartSCHParams.AREA, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsRetrievalOrderStartSCHParams.WORK_PLACE, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(AsRetrievalOrderStartSCHParams.STATION, _pdm_pul_Station.getSelectedValue());
            inparam.set(AsRetrievalOrderStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_ASRSRetrievalStart.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ASRSRetrievalStart.getNewLine();
                line.setValue(KEY_LST_BATCH_NO, outparam.get(AsRetrievalOrderStartSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(AsRetrievalOrderStartSCHParams.ORDER_NO));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(AsRetrievalOrderStartSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(AsRetrievalOrderStartSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_STATION_NO, outparam.get(AsRetrievalOrderStartSCHParams.STATION_NO));
                line.setValue(KEY_LST_STATION_NAME, outparam.get(AsRetrievalOrderStartSCHParams.STATION_NAME));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(AsRetrievalOrderStartSCHParams.DETAIL_COUNT));
                viewState.setObject(ViewStateKeys.RETRIEVAL_PLAN_DATE, txt_RetrievalPlanDate.getValue());
                viewState.setObject(ViewStateKeys.BATCH_NO, txt_BatchNo.getValue());
                viewState.setObject(ViewStateKeys.ORDER_NO_FROM, txt_OrderNoFrom.getValue());
                viewState.setObject(ViewStateKeys.ORDER_NO_TO, txt_OrderNoTo.getValue());
                viewState.setObject(ViewStateKeys.AREA, _pdm_pul_Area.getSelectedValue());
                viewState.setObject(ViewStateKeys.WORK_PLACE, _pdm_pul_WorkPlace.getSelectedValue());
                viewState.setObject(ViewStateKeys.STATION, _pdm_pul_Station.getSelectedValue());
                line.setValue(KEY_HIDDEN_LINE_COUNT, outparam.get(AsRetrievalOrderStartSCHParams.LINE_COUNT));
                lst_ASRSRetrievalStart_SetLineToolTip(line);
                _lcm_lst_ASRSRetrievalStart.add(line);
            }

            // clear.
            btn_RetrievalStart.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_ListClear.setEnabled(true);
            chk_LIssueReport.setEnabled(true);
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
        txt_OrderNoFrom.setValue(null);
        txt_OrderNoTo.setValue(null);
        _pdm_pul_Area.setSelectedValue(null);
        _pdm_pul_WorkPlace.setSelectedValue(null);
        _pdm_pul_Station.setSelectedValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_RetrievalStart_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsRetrievalOrderStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsRetrievalOrderStartSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ASRSRetrievalStart.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ASRSRetrievalStart.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                AsRetrievalOrderStartSCHParams lineparam = new AsRetrievalOrderStartSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(AsRetrievalOrderStartSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                lineparam.set(AsRetrievalOrderStartSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                lineparam.set(AsRetrievalOrderStartSCHParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                lineparam.set(AsRetrievalOrderStartSCHParams.CUSTOMER_CODE, line.getValue(KEY_LST_CUSTOMER_CODE));
                lineparam.set(AsRetrievalOrderStartSCHParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                lineparam.set(AsRetrievalOrderStartSCHParams.STATION_NO, line.getValue(KEY_LST_STATION_NO));
                lineparam.set(AsRetrievalOrderStartSCHParams.STATION_NAME, line.getValue(KEY_LST_STATION_NAME));
                lineparam.set(AsRetrievalOrderStartSCHParams.DETAIL_COUNT, line.getValue(KEY_LST_DETAIL_COUNT));
                lineparam.set(AsRetrievalOrderStartSCHParams.AREA, viewState.getObject(ViewStateKeys.AREA));
                lineparam.set(AsRetrievalOrderStartSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.BATCH_NO));
                lineparam.set(AsRetrievalOrderStartSCHParams.ORDER_NO_FROM, viewState.getObject(ViewStateKeys.ORDER_NO_FROM));
                lineparam.set(AsRetrievalOrderStartSCHParams.ORDER_NO_TO, viewState.getObject(ViewStateKeys.ORDER_NO_TO));
                lineparam.set(AsRetrievalOrderStartSCHParams.RETRIEVAL_PLAN_DATE, viewState.getObject(ViewStateKeys.RETRIEVAL_PLAN_DATE));
                lineparam.set(AsRetrievalOrderStartSCHParams.STATION, viewState.getObject(ViewStateKeys.STATION));
                lineparam.set(AsRetrievalOrderStartSCHParams.WORK_PLACE, viewState.getObject(ViewStateKeys.WORK_PLACE));
                lineparam.set(AsRetrievalOrderStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(AsRetrievalOrderStartSCHParams.PRINT_FLAG, chk_LIssueReport.getValue());
                lineparam.set(AsRetrievalOrderStartSCHParams.FUNCTION_ID, "M_FUNCTIONID");
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
                _lcm_lst_ASRSRetrievalStart.resetEditRow();
                _lcm_lst_ASRSRetrievalStart.resetHighlight();
                _lcm_lst_ASRSRetrievalStart.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_ASRSRetrievalStart.size(); i++)
            {
                ListCellLine line = _lcm_lst_ASRSRetrievalStart.get(i);
                lst_ASRSRetrievalStart.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(viewState.getString(ViewStateKeys.AREA), "");
                part11List.add(line.getViewString(KEY_LST_STATION_NO), "");
                part11List.add(txt_LRRetrievalPlanDate.getStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_BATCH_NO), "");
                part11List.add(line.getViewString(KEY_LST_ORDER_NO), "");
                part11List.add(line.getViewString(KEY_LST_CUSTOMER_CODE), "");

                if (chk_LIssueReport.getChecked())
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
            _lcm_lst_ASRSRetrievalStart.resetEditRow();
            _lcm_lst_ASRSRetrievalStart.resetHighlight();

            // clear.
            _lcm_lst_ASRSRetrievalStart.clear();
            btn_RetrievalStart.setEnabled(false);
            btn_AllCheck.setEnabled(false);
            btn_AllCheckClear.setEnabled(false);
            btn_ListClear.setEnabled(false);
            chk_LIssueReport.setEnabled(false);
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
        for (int i = 1; i <= _lcm_lst_ASRSRetrievalStart.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_ASRSRetrievalStart.get(i);
            lst_ASRSRetrievalStart.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_ASRSRetrievalStart_SetLineToolTip(clearLine);
            _lcm_lst_ASRSRetrievalStart.set(i, clearLine);
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
        for (int i = 1; i <= _lcm_lst_ASRSRetrievalStart.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_ASRSRetrievalStart.get(i);
            lst_ASRSRetrievalStart.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_ASRSRetrievalStart_SetLineToolTip(clearLine);
            _lcm_lst_ASRSRetrievalStart.set(i, clearLine);
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
        _lcm_lst_ASRSRetrievalStart.clear();
        btn_RetrievalStart.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_ListClear.setEnabled(false);
        chk_LIssueReport.setEnabled(false);
        txt_LRRetrievalPlanDate.setValue(null);
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
