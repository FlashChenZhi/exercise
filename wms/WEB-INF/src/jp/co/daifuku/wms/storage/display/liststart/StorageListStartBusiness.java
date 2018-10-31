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
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.storage.dasch.StorageListStartDASCH;
import jp.co.daifuku.wms.storage.dasch.StorageListStartDASCHParams;
import jp.co.daifuku.wms.storage.exporter.StorageStartListParams;
import jp.co.daifuku.wms.storage.schedule.StorageListStartSCH;
import jp.co.daifuku.wms.storage.schedule.StorageListStartSCHParams;

/**
 * 入庫リスト作業開始の画面処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:46:39 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class StorageListStartBusiness
        extends StorageListStart
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";
    // DFKLOOK:ここから修正    
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK:ここまで修正
    
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
    public void page_ConfirmBack(ActionEvent e) 
    		throws Exception 
    {
        // DFKLOOK:ここから修正
		// get event source.
		String eventSource = viewState.getString(_KEY_CONFIRMSOURCE);
		if (eventSource == null) {
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
			btn_WorkStart_Click_Process(eventSource);
		}
        // DFKLOOK:ここまで修正
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
        // DFKLOOK:ここから修正    	
        // process call.
        btn_WorkStart_Click_Process(null);
        // DFKLOOK:ここまで修正
    	
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
        
        
        // DFKLOOK:ここから修正
        
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
            
            StorageListStartSCHParams inParam = new StorageListStartSCHParams(ui);
            inParam.set(StorageListStartSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));

            // 画面定義テーブルをチェック
            Params outParam = sch.initFind(inParam);
            
            if (outParam != null)
            {
                String printflg = outParam.getString(StorageListStartSCHParams.ISSUE_REPORT);
                if (SystemDefine.KEYDATA_OFF.equals(printflg))
                {
                    // 前回、チェックOFFだった場合は更新
                    chk_IssueReport.setChecked(false);
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
        //DFKLOOK:ここまで修正

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

            // DFKLOOK ここから修正
            if (outparams.size() == 0 || outparams == null)
            {
                // clear.
                btn_WorkStart.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_AllClear.setEnabled(false);
                chk_IssueReport.setEnabled(false);
                _lcm_lst_StorageListStart.clear();

                return;
            }
            // DFKLOOK ここまで修正

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
    // DFKLOOK:ここから修正    
    private void btn_WorkStart_Click_Process(String eventSource)
            throws Exception
    // DFKLOOK:ここまで修正
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

        // DFKLOOK:ここから修正
        if (StringUtil.isBlank(eventSource))
    	{
            // 開始しますか？
            this.setConfirm("MSG-W0031", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_WorkStart_Click");
            return;    		
    	}
        // DFKLOOK:ここまで修正
        
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
                // DFKLOOK ここから修正
                lineparam.set(StorageListStartSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));
                // DFKLOOK ここまで修正
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

            // DFKLOOK ここから修正
            // 帳票発行追加
            Boolean blnPrint = true;
            if (chk_IssueReport.getChecked())
            {
            	blnPrint = startPrint(conn, inparams);
            }
            // DFKLOOK ここまで修正

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

            // DFKLOOK ここから修正
            if (outparams.size() == 0 || outparams == null)
            {
                // DFKLOOK ここまで修正
                // clear.
                btn_WorkStart.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_AllClear.setEnabled(false);
                chk_IssueReport.setEnabled(false);
                _lcm_lst_StorageListStart.clear();

                // DFKLOOK ここから修正
                return;
            }

            if (blnPrint == true)
            {
                // 6021021 = 開始しました。
                message.setMsgResourceKey("6021021");
            }
            else
            {
                // 6007042 = 設定後、印刷に失敗しました。ログを参照してください。
                message.setMsgResourceKey("6007042");
            }
            // DFKLOOK ここまで修正

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

    // DFKLOOK ここから修正
    // 帳票発行メソッド追加
    /**
     * 入庫作業リストを発行します
     * 
     * @param conn
     * @param locale
     * @param ui
     * @param settingUkeys
     */
    private boolean startPrint(Connection conn, Params[] params)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();
        
        StorageListStartDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            dasch = new StorageListStartDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            for (Params param : params)
            {
                // set input parameters.
                StorageListStartDASCHParams inparam = new StorageListStartDASCHParams();
                inparam.set(StorageListStartDASCHParams.SETTING_UNIT_KEY,
                        param.get(StorageListStartSCHParams.SETTING_UKEY));
                
                // check count.
                int count = dasch.count(inparam);
                if (count == 0)
                {
                    message.setMsgResourceKey("6003011");
                    return false;
                }
    
                // DASCH call.
                dasch.search(inparam);
                
                if (exporter == null)
                {
                    // open exporter.
                    ExporterFactory factory = new WmsExporterFactory(locale, ui);
                    exporter = factory.newPrinterExporter("StorageStartList", false);
                    exporter.open();
                }
    
                // export.
                while (dasch.next())
                {
                    Params outparam = dasch.get();
                    StorageStartListParams expparam = new StorageStartListParams();
                    expparam.set(StorageStartListParams.DFK_DS_NO, outparam.get(StorageListStartDASCHParams.DFK_DS_NO));
                    expparam.set(StorageStartListParams.DFK_USER_ID, outparam.get(StorageListStartDASCHParams.DFK_USER_ID));
                    expparam.set(StorageStartListParams.DFK_USER_NAME, outparam.get(StorageListStartDASCHParams.DFK_USER_NAME));
                    expparam.set(StorageStartListParams.SYS_DAY, outparam.get(StorageListStartDASCHParams.SYS_DAY));
                    expparam.set(StorageStartListParams.SYS_TIME, outparam.get(StorageListStartDASCHParams.SYS_TIME));
                    expparam.set(StorageStartListParams.JOB_NO, outparam.get(StorageListStartDASCHParams.JOB_NO));
                    expparam.set(StorageStartListParams.PLAN_DAY, outparam.get(StorageListStartDASCHParams.PLAN_DAY));
                    expparam.set(StorageStartListParams.ITEM_CODE, outparam.get(StorageListStartDASCHParams.ITEM_CODE));
                    expparam.set(StorageStartListParams.ITEM_NAME, outparam.get(StorageListStartDASCHParams.ITEM_NAME));
                    expparam.set(StorageStartListParams.PLAN_AREA_NO, outparam.get(StorageListStartDASCHParams.PLAN_AREA_NO));
                    expparam.set(StorageStartListParams.PLAN_LOCATION_NO, outparam.get(StorageListStartDASCHParams.PLAN_LOCATION_NO));
                    expparam.set(StorageStartListParams.PLAN_LOT_NO, outparam.get(StorageListStartDASCHParams.PLAN_LOT_NO));
                    expparam.set(StorageStartListParams.ENTERING_QTY, outparam.get(StorageListStartDASCHParams.ENTERING_QTY));
                    expparam.set(StorageStartListParams.PLAN_CASE_QTY, outparam.get(StorageListStartDASCHParams.PLAN_CASE_QTY));
                    expparam.set(StorageStartListParams.PLAN_PIECE_QTY, outparam.get(StorageListStartDASCHParams.PLAN_PIECE_QTY));
                    if (!exporter.write(expparam))
                    {
                        break;
                    }
                }
            }

            // execute print.
            try
            {
                exporter.print();
                message.setMsgResourceKey("6001010");
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                message.setMsgResourceKey("6007034");
                return false;
            }

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
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
        }
    }

    // DFKLOOK ここまで修正

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
