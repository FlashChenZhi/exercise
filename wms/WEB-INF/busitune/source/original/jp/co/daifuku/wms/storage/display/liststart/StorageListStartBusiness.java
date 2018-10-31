// $Id: StorageListStartBusiness.java 7341 2010-03-04 02:20:10Z okayama $
package jp.co.daifuku.wms.storage.display.liststart;

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
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
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
import jp.co.daifuku.wms.storage.display.liststart.StorageListStart;
import jp.co.daifuku.wms.storage.display.liststart.ViewStateKeys;
import jp.co.daifuku.wms.storage.schedule.StorageListStartSCH;
import jp.co.daifuku.wms.storage.schedule.StorageListStartSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7341 $, $Date:: 2010-03-04 11:20:10 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class StorageListStartBusiness
        extends StorageListStart
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_StorageListStart(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_StorageListStart(LST_PLAN_DAY) */
    private static final ListCellKey KEY_LST_PLAN_DAY = new ListCellKey("LST_PLAN_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_StorageListStart(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_StorageListStart(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_StorageListStart(LST_DETAIL_COUNT) */
    private static final ListCellKey KEY_LST_DETAIL_COUNT = new ListCellKey("LST_DETAIL_COUNT", new NumberCellColumn(0), true, false);

    /** lst_StorageListStart keys */
    private static final ListCellKey[] LST_STORAGELISTSTART_KEYS = {
        KEY_LST_SELECT,
        KEY_LST_PLAN_DAY,
        KEY_LST_ITEM_CODE,
        KEY_LST_DETAIL_COUNT,
        KEY_LST_ITEM_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_StorageListStart */
    private ListCellModel _lcm_lst_StorageListStart;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public StorageListStartBusiness()
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
        Locale locale = httpRequest.getLocale();

        // initialize lst_StorageListStart.
        _lcm_lst_StorageListStart = new ListCellModel(lst_StorageListStart, LST_STORAGELISTSTART_KEYS, locale);
        _lcm_lst_StorageListStart.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_StorageListStart.setToolTipVisible(KEY_LST_PLAN_DAY, false);
        _lcm_lst_StorageListStart.setToolTipVisible(KEY_LST_ITEM_CODE, false);
        _lcm_lst_StorageListStart.setToolTipVisible(KEY_LST_ITEM_NAME, false);
        _lcm_lst_StorageListStart.setToolTipVisible(KEY_LST_DETAIL_COUNT, false);
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
    private void lst_StorageListStart_SetLineToolTip(ListCellLine line)
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
        setFocus(txt_StoragePlanDate);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_WorkStart.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        chk_IssueReport.setEnabled(false);
        _lcm_lst_StorageListStart.clear();
        chk_IssueReport.setChecked(true);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_StoragePlanDate.validate(this, false);
        txt_FromItemCode.validate(this, false);
        txt_ToItemCode.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StorageListStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StorageListStartSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            StorageListStartSCHParams inparam = new StorageListStartSCHParams();
            inparam.set(StorageListStartSCHParams.PLAN_DAY, txt_StoragePlanDate.getValue());
            inparam.set(StorageListStartSCHParams.ITEM_CODE, txt_FromItemCode.getValue());
            inparam.set(StorageListStartSCHParams.TO_ITEM_CODE, txt_ToItemCode.getValue());
            inparam.set(StorageListStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_StorageListStart.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StorageListStart.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(StorageListStartSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(StorageListStartSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(StorageListStartSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(StorageListStartSCHParams.DETAIL_COUNT));
                viewState.setObject(ViewStateKeys.PLAN_DAY, txt_StoragePlanDate.getValue());
                viewState.setObject(ViewStateKeys.ITEM_CODE, txt_FromItemCode.getValue());
                viewState.setObject(ViewStateKeys.TO_ITEM_CODE, txt_ToItemCode.getValue());
                txt_ToItemCode.setValue(outparam.get(StorageListStartSCHParams.SETTING_UKEY));
                lst_StorageListStart_SetLineToolTip(line);
                _lcm_lst_StorageListStart.add(line);
            }

            // clear.
            btn_WorkStart.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_AllClear.setEnabled(true);
            chk_IssueReport.setEnabled(true);
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
        txt_StoragePlanDate.setValue(null);
        txt_FromItemCode.setValue(null);
        txt_ToItemCode.setValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_WorkStart_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_StorageListStart.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_StorageListStart.get(i);
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
        StorageListStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StorageListStartSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_StorageListStart.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_StorageListStart.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                StorageListStartSCHParams lineparam = new StorageListStartSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(StorageListStartSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                lineparam.set(StorageListStartSCHParams.PLAN_DAY, line.getValue(KEY_LST_PLAN_DAY));
                lineparam.set(StorageListStartSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(StorageListStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(StorageListStartSCHParams.ISSUE_REPORT, chk_IssueReport.getValue());
                lineparam.set(StorageListStartSCHParams.FUNCTION_ID, "M_FUNCTIONID");
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
                _lcm_lst_StorageListStart.resetEditRow();
                _lcm_lst_StorageListStart.resetHighlight();
                _lcm_lst_StorageListStart.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_StorageListStart.size(); i++)
            {
                ListCellLine line = _lcm_lst_StorageListStart.get(i);
                lst_StorageListStart.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_PLAN_DAY), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");

                if (chk_IssueReport.getChecked())
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
            _lcm_lst_StorageListStart.resetEditRow();
            _lcm_lst_StorageListStart.resetHighlight();

            // set input parameters.
            StorageListStartSCHParams inparam = new StorageListStartSCHParams();
            inparam.set(StorageListStartSCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
            inparam.set(StorageListStartSCHParams.PLAN_DAY, viewState.getObject(ViewStateKeys.PLAN_DAY));
            inparam.set(StorageListStartSCHParams.TO_ITEM_CODE, viewState.getObject(ViewStateKeys.TO_ITEM_CODE));
            inparam.set(StorageListStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_StorageListStart.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StorageListStart.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(StorageListStartSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(StorageListStartSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(StorageListStartSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(StorageListStartSCHParams.DETAIL_COUNT));
                lst_StorageListStart_SetLineToolTip(line);
                _lcm_lst_StorageListStart.add(line);
            }

            // clear.
            btn_WorkStart.setEnabled(false);
            btn_AllCheck.setEnabled(false);
            btn_AllCheckClear.setEnabled(false);
            btn_AllClear.setEnabled(false);
            _lcm_lst_StorageListStart.clear();
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
        for (int i = 1; i <= _lcm_lst_StorageListStart.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_StorageListStart.get(i);
            lst_StorageListStart.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_StorageListStart_SetLineToolTip(clearLine);
            _lcm_lst_StorageListStart.set(i, clearLine);
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
        for (int i = 1; i <= _lcm_lst_StorageListStart.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_StorageListStart.get(i);
            lst_StorageListStart.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_StorageListStart_SetLineToolTip(clearLine);
            _lcm_lst_StorageListStart.set(i, clearLine);
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
        _lcm_lst_StorageListStart.clear();
        btn_WorkStart.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        chk_IssueReport.setEnabled(false);
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
