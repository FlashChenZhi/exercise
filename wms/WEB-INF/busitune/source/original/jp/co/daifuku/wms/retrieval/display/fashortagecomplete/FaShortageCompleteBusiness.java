// $Id: FaShortageCompleteBusiness.java 7385 2010-03-05 09:08:52Z shibamoto $
package jp.co.daifuku.wms.retrieval.display.fashortagecomplete;

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
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.retrieval.display.fashortagecomplete.FaShortageComplete;
import jp.co.daifuku.wms.retrieval.display.fashortagecomplete.ViewStateKeys;
import jp.co.daifuku.wms.retrieval.schedule.FaShortageCompleteSCH;
import jp.co.daifuku.wms.retrieval.schedule.FaShortageCompleteSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7385 $, $Date:: 2010-03-05 18:08:52 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class FaShortageCompleteBusiness
        extends FaShortageComplete
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_FaShortageComplete(HIDDEN_ITEM_NAME) */
    private static final ListCellKey KEY_HIDDEN_ITEM_NAME = new ListCellKey("HIDDEN_ITEM_NAME", new StringCellColumn(), false, false);

    /** lst_FaShortageComplete(HIDDEN_PLAN_UNIT_KEY) */
    private static final ListCellKey KEY_HIDDEN_PLAN_UNIT_KEY = new ListCellKey("HIDDEN_PLAN_UNIT_KEY", new StringCellColumn(), false, false);

    /** lst_FaShortageComplete(HIDDEN_BATCH_NO) */
    private static final ListCellKey KEY_HIDDEN_BATCH_NO = new ListCellKey("HIDDEN_BATCH_NO", new StringCellColumn(), false, false);

    /** lst_FaShortageComplete(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_FaShortageComplete(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO = new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_FaShortageComplete(LST_TICKET_NO) */
    private static final ListCellKey KEY_LST_TICKET_NO = new ListCellKey("LST_TICKET_NO", new StringCellColumn(), true, false);

    /** lst_FaShortageComplete(LST_LINE_NO) */
    private static final ListCellKey KEY_LST_LINE_NO = new ListCellKey("LST_LINE_NO", new NumberCellColumn(0), true, false);

    /** lst_FaShortageComplete(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_FaShortageComplete(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_FaShortageComplete(LST_PLAN_QTY) */
    private static final ListCellKey KEY_LST_PLAN_QTY = new ListCellKey("LST_PLAN_QTY", new NumberCellColumn(0), true, false);

    /** lst_FaShortageComplete(LST_SHORTAGE_QTY) */
    private static final ListCellKey KEY_LST_SHORTAGE_QTY = new ListCellKey("LST_SHORTAGE_QTY", new NumberCellColumn(0), true, false);

    /** lst_FaShortageComplete keys */
    private static final ListCellKey[] LST_FASHORTAGECOMPLETE_KEYS = {
        KEY_HIDDEN_ITEM_NAME,
        KEY_HIDDEN_PLAN_UNIT_KEY,
        KEY_HIDDEN_BATCH_NO,
        KEY_LST_SELECT,
        KEY_LST_BATCH_NO,
        KEY_LST_TICKET_NO,
        KEY_LST_LINE_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT_NO,
        KEY_LST_PLAN_QTY,
        KEY_LST_SHORTAGE_QTY,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_FaShortageComplete */
    private ScrollListCellModel _lcm_lst_FaShortageComplete;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaShortageCompleteBusiness()
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

        // initialize lst_FaShortageComplete.
        _lcm_lst_FaShortageComplete = new ScrollListCellModel(lst_FaShortageComplete, LST_FASHORTAGECOMPLETE_KEYS, locale);
        _lcm_lst_FaShortageComplete.setToolTipVisible(KEY_LST_SELECT, true);
        _lcm_lst_FaShortageComplete.setToolTipVisible(KEY_LST_BATCH_NO, true);
        _lcm_lst_FaShortageComplete.setToolTipVisible(KEY_LST_TICKET_NO, true);
        _lcm_lst_FaShortageComplete.setToolTipVisible(KEY_LST_LINE_NO, true);
        _lcm_lst_FaShortageComplete.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_FaShortageComplete.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_FaShortageComplete.setToolTipVisible(KEY_LST_PLAN_QTY, true);
        _lcm_lst_FaShortageComplete.setToolTipVisible(KEY_LST_SHORTAGE_QTY, true);
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
    private void lst_FaShortageComplete_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("", KEY_HIDDEN_ITEM_NAME);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_FromSlipNumber);
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
        _lcm_lst_FaShortageComplete.clear();
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_FromSlipNumber.validate(this, false);
        txt_ToSlipNumber.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaShortageCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaShortageCompleteSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaShortageCompleteSCHParams inparam = new FaShortageCompleteSCHParams();
            inparam.set(FaShortageCompleteSCHParams.FROM_TICKET_NO, txt_FromSlipNumber.getValue());
            inparam.set(FaShortageCompleteSCHParams.TO_TICKET_NO, txt_ToSlipNumber.getValue());
            inparam.set(FaShortageCompleteSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_FaShortageComplete.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_FaShortageComplete.getNewLine();
                line.setValue(KEY_HIDDEN_PLAN_UNIT_KEY, outparam.get(FaShortageCompleteSCHParams.PLAN_UNIT_KEY));
                line.setValue(KEY_HIDDEN_BATCH_NO, outparam.get(FaShortageCompleteSCHParams.BATCH_NO));
                line.setValue(KEY_HIDDEN_ITEM_NAME, outparam.get(FaShortageCompleteSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_TICKET_NO, outparam.get(FaShortageCompleteSCHParams.TICKET_NO));
                line.setValue(KEY_LST_LINE_NO, outparam.get(FaShortageCompleteSCHParams.LINE_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(FaShortageCompleteSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_LOT_NO, outparam.get(FaShortageCompleteSCHParams.LOT_NO));
                line.setValue(KEY_LST_PLAN_QTY, outparam.get(FaShortageCompleteSCHParams.PLAN_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY, outparam.get(FaShortageCompleteSCHParams.SHORTAGE_QTY));
                viewState.setObject(ViewStateKeys.FROM_TICKET_NO, txt_FromSlipNumber.getValue());
                viewState.setObject(ViewStateKeys.TO_TICKET_NO, txt_ToSlipNumber.getValue());
                viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lst_FaShortageComplete_SetLineToolTip(line);
                _lcm_lst_FaShortageComplete.add(line);
            }

            // clear.
            btn_Set.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
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
        txt_FromSlipNumber.setValue(null);
        txt_ToSlipNumber.setValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_FaShortageComplete.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_FaShortageComplete.get(i);
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
        FaShortageCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaShortageCompleteSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_FaShortageComplete.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_FaShortageComplete.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                FaShortageCompleteSCHParams lineparam = new FaShortageCompleteSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(FaShortageCompleteSCHParams.PLAN_UNIT_KEY, line.getValue(KEY_HIDDEN_PLAN_UNIT_KEY));
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
                _lcm_lst_FaShortageComplete.resetEditRow();
                _lcm_lst_FaShortageComplete.resetHighlight();
                _lcm_lst_FaShortageComplete.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_FaShortageComplete.size(); i++)
            {
                ListCellLine line = _lcm_lst_FaShortageComplete.get(i);
                lst_FaShortageComplete.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_BATCH_NO), "");
                part11List.add(line.getViewString(KEY_LST_TICKET_NO), "");
                part11List.add(line.getViewString(KEY_LST_LINE_NO), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_QTY), "");
                part11List.add(line.getViewString(KEY_LST_SHORTAGE_QTY), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_FaShortageComplete.resetEditRow();
            _lcm_lst_FaShortageComplete.resetHighlight();

            // set input parameters.
            FaShortageCompleteSCHParams inparam = new FaShortageCompleteSCHParams();
            inparam.set(FaShortageCompleteSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(FaShortageCompleteSCHParams.FROM_TICKET_NO, viewState.getObject(ViewStateKeys.FROM_TICKET_NO));
            inparam.set(FaShortageCompleteSCHParams.TO_TICKET_NO, viewState.getObject(ViewStateKeys.TO_TICKET_NO));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_FaShortageComplete.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_FaShortageComplete.getNewLine();
                line.setValue(KEY_HIDDEN_PLAN_UNIT_KEY, outparam.get(FaShortageCompleteSCHParams.PLAN_UNIT_KEY));
                line.setValue(KEY_HIDDEN_BATCH_NO, outparam.get(FaShortageCompleteSCHParams.BATCH_NO));
                line.setValue(KEY_HIDDEN_ITEM_NAME, outparam.get(FaShortageCompleteSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_TICKET_NO, outparam.get(FaShortageCompleteSCHParams.TICKET_NO));
                line.setValue(KEY_LST_LINE_NO, outparam.get(FaShortageCompleteSCHParams.LINE_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(FaShortageCompleteSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_LOT_NO, outparam.get(FaShortageCompleteSCHParams.LOT_NO));
                line.setValue(KEY_LST_PLAN_QTY, outparam.get(FaShortageCompleteSCHParams.PLAN_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY, outparam.get(FaShortageCompleteSCHParams.SHORTAGE_QTY));
                lst_FaShortageComplete_SetLineToolTip(line);
                _lcm_lst_FaShortageComplete.add(line);
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
        for (int i = 1; i <= _lcm_lst_FaShortageComplete.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_FaShortageComplete.get(i);
            lst_FaShortageComplete.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_FaShortageComplete_SetLineToolTip(clearLine);
            _lcm_lst_FaShortageComplete.set(i, clearLine);
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
        for (int i = 1; i <= _lcm_lst_FaShortageComplete.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_FaShortageComplete.get(i);
            lst_FaShortageComplete.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_FaShortageComplete_SetLineToolTip(clearLine);
            _lcm_lst_FaShortageComplete.set(i, clearLine);
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
