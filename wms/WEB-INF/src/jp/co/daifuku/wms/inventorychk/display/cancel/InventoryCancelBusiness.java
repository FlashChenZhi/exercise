// $Id: InventoryCancelBusiness.java 7389 2010-03-05 09:29:24Z okayama $
package jp.co.daifuku.wms.inventorychk.display.cancel;

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
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryCancelSCH;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryCancelSCHParams;

/**
 * 棚卸キャンセルの画面処理を行います。
 * 
 * @version $Revision: 7389 $, $Date: 2010-03-05 18:29:24 +0900 (金, 05 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class InventoryCancelBusiness
        extends InventoryCancel
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";
    // DFKLOOK:ここから
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK:ここまで
    
    /** lst_InventoryCancelList(HIDDEN_SCHEDULE) */
    private static final ListCellKey KEY_HIDDEN_SCHEDULE = new ListCellKey("HIDDEN_SCHEDULE", new StringCellColumn(), false, false);

    /** lst_InventoryCancelList(HIDDEN_LASTUPDATE_DATE) */
    private static final ListCellKey KEY_HIDDEN_LASTUPDATE_DATE = new ListCellKey("HIDDEN_LASTUPDATE_DATE", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG, DateCellColumn.TIME_TYPE_SEC), false, false);

    /** lst_InventoryCancelList(LST_COLUMN_1) */
    private static final ListCellKey KEY_LST_COLUMN_1 = new ListCellKey("LST_COLUMN_1", new CheckBoxColumn(), true, true);

    /** lst_InventoryCancelList(LST_CYCLE_COUNT_START_DATETIME) */
    private static final ListCellKey KEY_LST_CYCLE_COUNT_START_DATETIME = new ListCellKey("LST_CYCLE_COUNT_START_DATETIME", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG, DateCellColumn.TIME_TYPE_SEC), true, false);

    /** lst_InventoryCancelList(LST_AREA) */
    private static final ListCellKey KEY_LST_AREA = new ListCellKey("LST_AREA", new AreaCellColumn(), true, false);

    /** lst_InventoryCancelList(LST_AREA_NAME) */
    private static final ListCellKey KEY_LST_AREA_NAME = new ListCellKey("LST_AREA_NAME", new StringCellColumn(), true, false);

    /** lst_InventoryCancelList(LST_START_LOCATION) */
    private static final ListCellKey KEY_LST_START_LOCATION = new ListCellKey("LST_START_LOCATION", new LocationCellColumn(), true, false);

    /** lst_InventoryCancelList(LST_END_LOCATION) */
    private static final ListCellKey KEY_LST_END_LOCATION = new ListCellKey("LST_END_LOCATION", new LocationCellColumn(), true, false);

    /** lst_InventoryCancelList kyes */
    private static final ListCellKey[] LST_INVENTORYCANCELLIST_KEYS = {
        KEY_HIDDEN_SCHEDULE,
        KEY_HIDDEN_LASTUPDATE_DATE,
        KEY_LST_COLUMN_1,
        KEY_LST_CYCLE_COUNT_START_DATETIME,
        KEY_LST_AREA,
        KEY_LST_AREA_NAME,
        KEY_LST_START_LOCATION,
        KEY_LST_END_LOCATION,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_InventoryCancelList */
    private ListCellModel _lcm_lst_InventoryCancelList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public InventoryCancelBusiness()
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
        // initialize componenets.
        initializeComponents();
        
        // DFKLOOK ここから修正
        setFocus(lst_InventoryCancelList);
        // DFKLOOK ここまで修正
    }
    
    /**
    *
    * @param e ActionEvent
    * @throws Exception
    */   
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから修正
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
        if (eventSource.startsWith("btn_Set_Click"))
        {
            btn_Set_Click_Process(eventSource);
        }
        // DFKLOOK ここまで修正
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
    public void btn_Set_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Set_Click_Process(null);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ReDisplayFunc_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ReDisplayFunc_Click_Process();
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

        // initialize lst_InventoryCancelList.
        _lcm_lst_InventoryCancelList = new ListCellModel(lst_InventoryCancelList, LST_INVENTORYCANCELLIST_KEYS, locale);
        _lcm_lst_InventoryCancelList.setToolTipVisible(KEY_LST_COLUMN_1, false);
        _lcm_lst_InventoryCancelList.setToolTipVisible(KEY_LST_CYCLE_COUNT_START_DATETIME, false);
        _lcm_lst_InventoryCancelList.setToolTipVisible(KEY_LST_AREA, false);
        _lcm_lst_InventoryCancelList.setToolTipVisible(KEY_LST_AREA_NAME, false);
        _lcm_lst_InventoryCancelList.setToolTipVisible(KEY_LST_START_LOCATION, false);
        _lcm_lst_InventoryCancelList.setToolTipVisible(KEY_LST_END_LOCATION, false);

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
    private void lst_InventoryCancelList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
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
        InventoryCancelSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new InventoryCancelSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            InventoryCancelSCHParams inparam = new InventoryCancelSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_InventoryCancelList.clear();
            
            // DFKLOOK ここから修正
            if (outparams.size() == 0)
            {
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_Set.setEnabled(false);
                return;
            }
            // DFKLOOK ここまで修正
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_InventoryCancelList.getNewLine();
                line.setValue(KEY_LST_CYCLE_COUNT_START_DATETIME, outparam.get(InventoryCancelSCHParams.REGIST_DATE));
                line.setValue(KEY_LST_AREA, outparam.get(InventoryCancelSCHParams.AREA_NO));
                line.setValue(KEY_LST_AREA_NAME, outparam.get(InventoryCancelSCHParams.AREA_NAME));
                line.setValue(KEY_LST_START_LOCATION, outparam.get(InventoryCancelSCHParams.FROM_LOCATION_NO));
                line.setValue(KEY_LST_END_LOCATION, outparam.get(InventoryCancelSCHParams.TO_LOCATION_NO));
                line.setValue(KEY_HIDDEN_SCHEDULE, outparam.get(InventoryCancelSCHParams.SCHEDULE));
                line.setValue(KEY_HIDDEN_LASTUPDATE_DATE, outparam.get(InventoryCancelSCHParams.LASTUPDATE));
                lst_InventoryCancelList_SetLineToolTip(line);
                _lcm_lst_InventoryCancelList.add(line);
            }
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_ReDisplayFunc.setEnabled(true);
            btn_Set.setEnabled(true);

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
    private void btn_AllCheck_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_InventoryCancelList.size(); i++)
        {
            ListCellLine clearline = _lcm_lst_InventoryCancelList.get(i);
            lst_InventoryCancelList.setCurrentRow(i);
            clearline.setValue(KEY_LST_COLUMN_1, Boolean.TRUE);
            lst_InventoryCancelList_SetLineToolTip(clearline);
            _lcm_lst_InventoryCancelList.set(i, clearline);
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
        for (int i = 1; i <= _lcm_lst_InventoryCancelList.size(); i++)
        {
            ListCellLine clearline = _lcm_lst_InventoryCancelList.get(i);
            lst_InventoryCancelList.setCurrentRow(i);
            clearline.setValue(KEY_LST_COLUMN_1, Boolean.FALSE);
            lst_InventoryCancelList_SetLineToolTip(clearline);
            _lcm_lst_InventoryCancelList.set(i, clearline);
        }

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_InventoryCancelList.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_InventoryCancelList.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            // DFKLOOK ここから修正
            // 6123103 = データを選択してください。
            message.setMsgResourceKey("6123103");
            // DFKLOOK ここまで修正 
            return;
        }

        // DFKLOOK:ここから修正        
        if (StringUtil.isBlank(eventSource))
        {
            // 設定しますか？
            this.setConfirm("MSG-W9000", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
            return;         
        }
        // DFKLOOK:ここまで修正
    
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        InventoryCancelSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new InventoryCancelSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_InventoryCancelList.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_InventoryCancelList.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                InventoryCancelSCHParams lineparam = new InventoryCancelSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(InventoryCancelSCHParams.SCHEDULE, line.getValue(KEY_HIDDEN_SCHEDULE));
                lineparam.set(InventoryCancelSCHParams.LASTUPDATE, line.getValue(KEY_HIDDEN_LASTUPDATE_DATE));
                lineparam.set(InventoryCancelSCHParams.COLUMN_1, line.getValue(KEY_LST_COLUMN_1));
                lineparam.set(InventoryCancelSCHParams.REGIST_DATE, line.getValue(KEY_LST_CYCLE_COUNT_START_DATETIME));
                lineparam.set(InventoryCancelSCHParams.AREA_NO, line.getValue(KEY_LST_AREA));
                lineparam.set(InventoryCancelSCHParams.AREA_NAME, line.getValue(KEY_LST_AREA_NAME));
                lineparam.set(InventoryCancelSCHParams.FROM_LOCATION_NO, line.getValue(KEY_LST_START_LOCATION));
                lineparam.set(InventoryCancelSCHParams.TO_LOCATION_NO, line.getValue(KEY_LST_END_LOCATION));
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

                // reset editing row or highligiting error row.
                _lcm_lst_InventoryCancelList.resetEditRow();
                _lcm_lst_InventoryCancelList.resetHighlight();
                _lcm_lst_InventoryCancelList.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_InventoryCancelList.size(); i++)
            {
                ListCellLine line = _lcm_lst_InventoryCancelList.get(i);
                lst_InventoryCancelList.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_CYCLE_COUNT_START_DATETIME), "");
                part11List.add(line.getViewString(KEY_LST_AREA), "");
                part11List.add(line.getViewString(KEY_LST_START_LOCATION), "");
                part11List.add(line.getViewString(KEY_LST_END_LOCATION), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_InventoryCancelList.resetEditRow();
            _lcm_lst_InventoryCancelList.resetHighlight();

            // set input parameters.
            InventoryCancelSCHParams inparam = new InventoryCancelSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_InventoryCancelList.clear();
            
            // DFKLOOK ここから修正
            if (outparams.size() == 0)
            {
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_Set.setEnabled(false);
                return;
            }
            // DFKLOOK ここまで修正
            
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_InventoryCancelList.getNewLine();
                line.setValue(KEY_HIDDEN_SCHEDULE, outparam.get(InventoryCancelSCHParams.SCHEDULE));
                line.setValue(KEY_HIDDEN_LASTUPDATE_DATE, outparam.get(InventoryCancelSCHParams.LASTUPDATE));
                line.setValue(KEY_LST_CYCLE_COUNT_START_DATETIME, outparam.get(InventoryCancelSCHParams.REGIST_DATE));
                line.setValue(KEY_LST_AREA, outparam.get(InventoryCancelSCHParams.AREA_NO));
                line.setValue(KEY_LST_AREA_NAME, outparam.get(InventoryCancelSCHParams.AREA_NAME));
                line.setValue(KEY_LST_START_LOCATION, outparam.get(InventoryCancelSCHParams.FROM_LOCATION_NO));
                line.setValue(KEY_LST_END_LOCATION, outparam.get(InventoryCancelSCHParams.TO_LOCATION_NO));
                lst_InventoryCancelList_SetLineToolTip(line);
                _lcm_lst_InventoryCancelList.add(line);
            }
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_ReDisplayFunc.setEnabled(true);
            btn_Set.setEnabled(true);

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
    private void btn_ReDisplayFunc_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        InventoryCancelSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new InventoryCancelSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            InventoryCancelSCHParams inparam = new InventoryCancelSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_InventoryCancelList.clear();
            // DFKLOOK ここから修正
            if (outparams.size() == 0)
            {
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_Set.setEnabled(false);
                return;
            }
            // DFKLOOK ここまで修正
            
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_InventoryCancelList.getNewLine();
                line.setValue(KEY_LST_CYCLE_COUNT_START_DATETIME, outparam.get(InventoryCancelSCHParams.REGIST_DATE));
                line.setValue(KEY_LST_AREA, outparam.get(InventoryCancelSCHParams.AREA_NO));
                line.setValue(KEY_LST_AREA_NAME, outparam.get(InventoryCancelSCHParams.AREA_NAME));
                line.setValue(KEY_LST_START_LOCATION, outparam.get(InventoryCancelSCHParams.FROM_LOCATION_NO));
                line.setValue(KEY_LST_END_LOCATION, outparam.get(InventoryCancelSCHParams.TO_LOCATION_NO));
                line.setValue(KEY_HIDDEN_SCHEDULE, outparam.get(InventoryCancelSCHParams.SCHEDULE));
                line.setValue(KEY_HIDDEN_LASTUPDATE_DATE, outparam.get(InventoryCancelSCHParams.LASTUPDATE));
                lst_InventoryCancelList_SetLineToolTip(line);
                _lcm_lst_InventoryCancelList.add(line);
            }

            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_ReDisplayFunc.setEnabled(true);
            btn_Set.setEnabled(true);
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
