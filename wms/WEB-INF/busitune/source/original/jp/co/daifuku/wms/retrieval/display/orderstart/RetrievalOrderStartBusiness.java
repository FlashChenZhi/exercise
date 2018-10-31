// $Id: RetrievalOrderStartBusiness.java 7446 2010-03-08 04:20:52Z okayama $
package jp.co.daifuku.wms.retrieval.display.orderstart;

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
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAllocPriorityPullDownModel;
import jp.co.daifuku.wms.retrieval.display.orderstart.RetrievalOrderStart;
import jp.co.daifuku.wms.retrieval.display.orderstart.ViewStateKeys;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOrderStartSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOrderStartSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7446 $, $Date:: 2010-03-08 13:20:52 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class RetrievalOrderStartBusiness
        extends RetrievalOrderStart
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_RetrievalStart(LST_COLUMN_1) */
    private static final ListCellKey KEY_LST_COLUMN_1 = new ListCellKey("LST_COLUMN_1", new CheckBoxColumn(), true, true);

    /** lst_RetrievalStart(LST_BATCH) */
    private static final ListCellKey KEY_LST_BATCH = new ListCellKey("LST_BATCH", new StringCellColumn(), true, false);

    /** lst_RetrievalStart(LST_ORDER) */
    private static final ListCellKey KEY_LST_ORDER = new ListCellKey("LST_ORDER", new StringCellColumn(), true, false);

    /** lst_RetrievalStart(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE = new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_RetrievalStart(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME = new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_RetrievalStart(LST_NOOF_RECORDS) */
    private static final ListCellKey KEY_LST_NOOF_RECORDS = new ListCellKey("LST_NOOF_RECORDS", new NumberCellColumn(0), true, false);

    /** lst_RetrievalStart(LST_ALLOCATION_RESULT) */
    private static final ListCellKey KEY_LST_ALLOCATION_RESULT = new ListCellKey("LST_ALLOCATION_RESULT", new StringCellColumn(), true, false);

    /** lst_RetrievalStart keys */
    private static final ListCellKey[] LST_RETRIEVALSTART_KEYS = {
        KEY_LST_COLUMN_1,
        KEY_LST_BATCH,
        KEY_LST_ORDER,
        KEY_LST_CUSTOMER_CODE,
        KEY_LST_NOOF_RECORDS,
        KEY_LST_ALLOCATION_RESULT,
        KEY_LST_CUSTOMER_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_LAllocatedPattern */
    private WmsAllocPriorityPullDownModel _pdm_pul_LAllocatedPattern;

    /** ListCellModel lst_RetrievalStart */
    private ListCellModel _lcm_lst_RetrievalStart;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RetrievalOrderStartBusiness()
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
    public void btn_Start_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Start_Click_Process();
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

        // initialize pul_LAllocatedPattern.
        _pdm_pul_LAllocatedPattern = new WmsAllocPriorityPullDownModel(pul_LAllocatedPattern, locale, ui);

        // initialize lst_RetrievalStart.
        _lcm_lst_RetrievalStart = new ListCellModel(lst_RetrievalStart, LST_RETRIEVALSTART_KEYS, locale);
        _lcm_lst_RetrievalStart.setToolTipVisible(KEY_LST_COLUMN_1, false);
        _lcm_lst_RetrievalStart.setToolTipVisible(KEY_LST_BATCH, false);
        _lcm_lst_RetrievalStart.setToolTipVisible(KEY_LST_ORDER, false);
        _lcm_lst_RetrievalStart.setToolTipVisible(KEY_LST_CUSTOMER_CODE, false);
        _lcm_lst_RetrievalStart.setToolTipVisible(KEY_LST_CUSTOMER_NAME, false);
        _lcm_lst_RetrievalStart.setToolTipVisible(KEY_LST_NOOF_RECORDS, false);
        _lcm_lst_RetrievalStart.setToolTipVisible(KEY_LST_ALLOCATION_RESULT, false);
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

            // load pul_LAllocatedPattern.
            _pdm_pul_LAllocatedPattern.init(conn, AllocateType.NORMAL);
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
    private void lst_RetrievalStart_SetLineToolTip(ListCellLine line)
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
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_Start.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        txt_LRetrievalPlanDate.setReadOnly(true);
        pul_LAllocatedPattern.setEnabled(false);
        _lcm_lst_RetrievalStart.clear();
        chk_LNoReplenishmentShortage.setEnabled(false);

        // set focus.
        setFocus(txt_PlanDate);
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
        txt_OrderNoFrom.validate(this, false);
        txt_OrderNoTo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalOrderStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalOrderStartSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RetrievalOrderStartSCHParams inparam = new RetrievalOrderStartSCHParams();
            inparam.set(RetrievalOrderStartSCHParams.RETRIEVAL_PLAN_DATE, txt_PlanDate.getValue());
            inparam.set(RetrievalOrderStartSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(RetrievalOrderStartSCHParams.ORDER_NO_FROM, txt_OrderNoFrom.getValue());
            inparam.set(RetrievalOrderStartSCHParams.ORDER_NO_TO, txt_OrderNoTo.getValue());
            inparam.set(RetrievalOrderStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RetrievalStart.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RetrievalStart.getNewLine();
                line.setValue(KEY_LST_BATCH, outparam.get(RetrievalOrderStartSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER, outparam.get(RetrievalOrderStartSCHParams.ORDER_NO));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(RetrievalOrderStartSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(RetrievalOrderStartSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_NOOF_RECORDS, outparam.get(RetrievalOrderStartSCHParams.DETAIL_COUNT));
                line.setValue(KEY_LST_ALLOCATION_RESULT, outparam.get(RetrievalOrderStartSCHParams.ALLOCATION_RESULT));
                viewState.setObject(ViewStateKeys.RETRIEVAL_PLAN_DATE, txt_PlanDate.getValue());
                viewState.setObject(ViewStateKeys.BATCH_NO, txt_BatchNo.getValue());
                viewState.setObject(ViewStateKeys.ORDER_NO_FROM, txt_OrderNoFrom.getValue());
                viewState.setObject(ViewStateKeys.ORDER_NO_TO, txt_OrderNoTo.getValue());
                txt_LRetrievalPlanDate.setValue(txt_PlanDate.getValue());
                lst_RetrievalStart_SetLineToolTip(line);
                _lcm_lst_RetrievalStart.add(line);
            }

            // clear.
            btn_Start.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_AllClear.setEnabled(true);
            pul_LAllocatedPattern.setEnabled(true);
            chk_LNoReplenishmentShortage.setEnabled(true);
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
        txt_OrderNoFrom.setValue(null);
        txt_OrderNoTo.setValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Start_Click_Process()
            throws Exception
    {
        // input validation.
        pul_LAllocatedPattern.validate(this, true);

        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_RetrievalStart.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_RetrievalStart.get(i);
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
        RetrievalOrderStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalOrderStartSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_RetrievalStart.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_RetrievalStart.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                RetrievalOrderStartSCHParams lineparam = new RetrievalOrderStartSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(RetrievalOrderStartSCHParams.COLUMN_1, line.getValue(KEY_LST_COLUMN_1));
                lineparam.set(RetrievalOrderStartSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH));
                lineparam.set(RetrievalOrderStartSCHParams.ORDER_NO, line.getValue(KEY_LST_ORDER));
                lineparam.set(RetrievalOrderStartSCHParams.CUSTOMER_CODE, line.getValue(KEY_LST_CUSTOMER_CODE));
                lineparam.set(RetrievalOrderStartSCHParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                lineparam.set(RetrievalOrderStartSCHParams.DETAIL_COUNT, line.getValue(KEY_LST_NOOF_RECORDS));
                lineparam.set(RetrievalOrderStartSCHParams.ALLOCATION_RESULT, line.getValue(KEY_LST_ALLOCATION_RESULT));
                lineparam.set(RetrievalOrderStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(RetrievalOrderStartSCHParams.RETRIEVAL_PLAN_DATE, txt_LRetrievalPlanDate.getValue());
                lineparam.set(RetrievalOrderStartSCHParams.ALLOCATED_PATTERN, _pdm_pul_LAllocatedPattern.getSelectedValue());
                lineparam.set(RetrievalOrderStartSCHParams.SHORTAGE_COMPLETION_FLAG, chk_LNoReplenishmentShortage.getValue());
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
                _lcm_lst_RetrievalStart.resetEditRow();
                _lcm_lst_RetrievalStart.resetHighlight();
                _lcm_lst_RetrievalStart.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_RetrievalStart.size(); i++)
            {
                ListCellLine line = _lcm_lst_RetrievalStart.get(i);
                lst_RetrievalStart.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(txt_LRetrievalPlanDate.getStringValue(), "");
                part11List.add(_pdm_pul_LAllocatedPattern.getSelectedStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_BATCH), "");
                part11List.add(line.getViewString(KEY_LST_ORDER), "");
                part11List.add(line.getViewString(KEY_LST_CUSTOMER_CODE), "");
                part11List.add(line.getViewString(KEY_LST_ALLOCATION_RESULT), "");
                part11List.add(chk_LNoReplenishmentShortage.getStringValue(), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_RetrievalStart.resetEditRow();
            _lcm_lst_RetrievalStart.resetHighlight();

            // clear.
            _lcm_lst_RetrievalStart.clear();
            btn_Start.setEnabled(false);
            btn_AllCheck.setEnabled(false);
            btn_AllCheckClear.setEnabled(false);
            btn_AllClear.setEnabled(false);
            pul_LAllocatedPattern.setEnabled(false);
            chk_LNoReplenishmentShortage.setEnabled(false);
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
        for (int i = 1; i <= _lcm_lst_RetrievalStart.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_RetrievalStart.get(i);
            lst_RetrievalStart.setCurrentRow(i);
            clearLine.setValue(KEY_LST_COLUMN_1, Boolean.TRUE);
            lst_RetrievalStart_SetLineToolTip(clearLine);
            _lcm_lst_RetrievalStart.set(i, clearLine);
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
        for (int i = 1; i <= _lcm_lst_RetrievalStart.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_RetrievalStart.get(i);
            lst_RetrievalStart.setCurrentRow(i);
            clearLine.setValue(KEY_LST_COLUMN_1, Boolean.FALSE);
            lst_RetrievalStart_SetLineToolTip(clearLine);
            _lcm_lst_RetrievalStart.set(i, clearLine);
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
        _lcm_lst_RetrievalStart.clear();
        btn_Start.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        txt_LRetrievalPlanDate.setValue(null);
        pul_LAllocatedPattern.setEnabled(false);
        chk_LNoReplenishmentShortage.setEnabled(false);

        // set focus.
        setFocus(txt_PlanDate);
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
