// $Id: AsrsReplenishStartBusiness.java 7538 2010-03-13 11:10:44Z ota $
package jp.co.daifuku.wms.asrs.display.replenishstart;

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
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.schedule.AsrsReplenishStartSCH;
import jp.co.daifuku.wms.asrs.schedule.AsrsReplenishStartSCHParams;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;

/**
 * AS/RS 補充開始設定の画面処理を行います。
 *
 * @version $Revision: 7538 $, $Date: 2010-03-13 20:10:44 +0900 (土, 13 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class AsrsReplenishStartBusiness
        extends AsrsReplenishStart
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // DFKLOOK start
    /**
     * ダイアログ呼び出し元：設定ボタン
     */
    private static final String DIALOG_SET = "DIALOG_SET";
    
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK end

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ReplenishmentStart(LST_COLUMN_1) */
    private static final ListCellKey KEY_LST_COLUMN_1 = new ListCellKey("LST_COLUMN_1", new CheckBoxColumn(), true, true);

    /** lst_ReplenishmentStart(LST_LIST) */
    private static final ListCellKey KEY_LST_LIST = new ListCellKey("LST_LIST", new StringCellColumn(), true, false);

    /** lst_ReplenishmentStart(LST_FROM_AREA) */
    private static final ListCellKey KEY_LST_FROM_AREA = new ListCellKey("LST_FROM_AREA", new AreaCellColumn(), true, false);

    /** lst_ReplenishmentStart(LST_NOOF_RECORDS) */
    private static final ListCellKey KEY_LST_NOOF_RECORDS = new ListCellKey("LST_NOOF_RECORDS", new NumberCellColumn(0), true, false);

    /** lst_ReplenishmentStart keys */
    private static final ListCellKey[] LST_REPLENISHMENTSTART_KEYS = {
        KEY_LST_COLUMN_1,
        KEY_LST_LIST,
        KEY_LST_FROM_AREA,
        KEY_LST_NOOF_RECORDS,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** ListCellModel lst_ReplenishmentStart */
    private ListCellModel _lcm_lst_ReplenishmentStart;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsrsReplenishStartBusiness()
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
        // DFKLOOK start 追加
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
        if(eventSource.equals("btn_Start_Click_Check"))
        {
            viewState.setBoolean(DIALOG_SET, isExecute);
        }
        else if (!isExecute)
        {
            return;
        }

        // choose process.
        if (eventSource.startsWith("btn_Start_Click"))
        {
            btn_Start_Click_Process(eventSource);
        }
        // DFKLOOK end
        
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
        // DFKLOOK: ここから修正
        // process call.
        btn_Start_Click_Process(null);
        // DFKLOOK: ここまで修正
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

        // initialize pul_Area.
        _pdm_pul_Area = new WmsAreaPullDownModel(pul_Area, locale, ui);

        // initialize lst_ReplenishmentStart.
        _lcm_lst_ReplenishmentStart = new ListCellModel(lst_ReplenishmentStart, LST_REPLENISHMENTSTART_KEYS, locale);
        _lcm_lst_ReplenishmentStart.setToolTipVisible(KEY_LST_COLUMN_1, false);
        _lcm_lst_ReplenishmentStart.setToolTipVisible(KEY_LST_LIST, false);
        _lcm_lst_ReplenishmentStart.setToolTipVisible(KEY_LST_FROM_AREA, false);
        _lcm_lst_ReplenishmentStart.setToolTipVisible(KEY_LST_NOOF_RECORDS, false);

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
            _pdm_pul_Area.init(conn, AreaType.ASRS, StationType.RETRIEVAL, "", true);

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
    private void lst_ReplenishmentStart_SetLineToolTip(ListCellLine line)
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
        setFocus(txt_ListWorkNo);

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
        _lcm_lst_ReplenishmentStart.clear();

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ListWorkNo.validate(this, false);
        pul_Area.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsrsReplenishStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsrsReplenishStartSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsrsReplenishStartSCHParams inparam = new AsrsReplenishStartSCHParams();
            inparam.set(AsrsReplenishStartSCHParams.SETTING_UKEY, txt_ListWorkNo.getValue());
            inparam.set(AsrsReplenishStartSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsrsReplenishStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_ReplenishmentStart.clear();
            
            // DFKLOOK start
            if (outparams.size() == 0)
            {
                // clear.
                btn_Start.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_AllClear.setEnabled(false);
                return;
            }
            // DFKLOOK end
            
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ReplenishmentStart.getNewLine();
                line.setValue(KEY_LST_LIST, outparam.get(AsrsReplenishStartSCHParams.LST_SETTING_UKEY));
                line.setValue(KEY_LST_FROM_AREA, outparam.get(AsrsReplenishStartSCHParams.FROM_AREA));
                line.setValue(KEY_LST_NOOF_RECORDS, outparam.get(AsrsReplenishStartSCHParams.SUMMARY));
                viewState.setObject(ViewStateKeys.SETTING_UKEY, txt_ListWorkNo.getValue());
                viewState.setObject(ViewStateKeys.AREA_NO, _pdm_pul_Area.getSelectedValue());
                viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lst_ReplenishmentStart_SetLineToolTip(line);
                _lcm_lst_ReplenishmentStart.add(line);
            }

            // clear.
            btn_Start.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_AllClear.setEnabled(true);

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
        txt_ListWorkNo.setValue(null);
        _pdm_pul_Area.setSelectedValue(null);

    }

    /**
     *
     * @throws Exception
     */
    // DFKLOOK: ここから修正
    private void btn_Start_Click_Process(String eventSource)
            throws Exception
    {      
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsrsReplenishStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsrsReplenishStartSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ReplenishmentStart.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ReplenishmentStart.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                AsrsReplenishStartSCHParams lineparam = new AsrsReplenishStartSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(AsrsReplenishStartSCHParams.LST_SETTING_UKEY, line.getValue(KEY_LST_LIST));
                // DFKLOOK パラメータ修正
                lineparam.set(AsrsReplenishStartSCHParams.ERROR_ALLOC_CARRY, viewState.getBoolean(DIALOG_SET));

                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // DFKLOOK start
            if (ArrayUtil.isEmpty(inparams))
            {
                // データを選択してください
                message.setMsgResourceKey("6003001");
                return;
            }
            
            if (StringUtil.isBlank(eventSource))
            {
                // show confirm message.開始しますか？
                this.setConfirm("MSG-T0051", false, true);
                viewState.setString(_KEY_CONFIRMSOURCE, "btn_Start_Click");
                return;         
            }
            if (eventSource.equals("btn_Start_Click"))
            {
                // setConfirmを使用するよう変更
                // MSG-W0060=開始対象の搬送作業よりも、先に引当てられた未開始の搬送作業があった場合
                // 処理を中止しますか？中止する場合は「OK」、中止しない場合は「キャンセル」を選択してください。
                setConfirm(DisplayResource.format("MSG-W0060"), true, true);
                // 設定ボタンからダイアログ表示されたことを記憶する
                viewState.setString(_KEY_CONFIRMSOURCE, "btn_Start_Click_Check");
                return;
            }
            // DFKLOOK end


            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_ReplenishmentStart.resetEditRow();
                _lcm_lst_ReplenishmentStart.resetHighlight();
                _lcm_lst_ReplenishmentStart.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_ReplenishmentStart.size(); i++)
            {
                ListCellLine line = _lcm_lst_ReplenishmentStart.get(i);
                lst_ReplenishmentStart.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_LIST), "");
                part11List.add(line.getViewString(KEY_LST_FROM_AREA), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_ReplenishmentStart.resetEditRow();
            _lcm_lst_ReplenishmentStart.resetHighlight();

            // set input parameters.
            AsrsReplenishStartSCHParams inparam = new AsrsReplenishStartSCHParams();
            inparam.set(AsrsReplenishStartSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA_NO));
            inparam.set(AsrsReplenishStartSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(AsrsReplenishStartSCHParams.SETTING_UKEY, viewState.getObject(ViewStateKeys.SETTING_UKEY));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_ReplenishmentStart.clear();
            
            // DFKLOOK start
            if (outparams.size() != 0)
            {
                for (Params outparam : outparams)
                {
                    ListCellLine line = _lcm_lst_ReplenishmentStart.getNewLine();
                    line.setValue(KEY_LST_LIST, outparam.get(AsrsReplenishStartSCHParams.LST_SETTING_UKEY));
                    line.setValue(KEY_LST_FROM_AREA, outparam.get(AsrsReplenishStartSCHParams.FROM_AREA));
                    line.setValue(KEY_LST_NOOF_RECORDS, outparam.get(AsrsReplenishStartSCHParams.SUMMARY));
                    lst_ReplenishmentStart_SetLineToolTip(line);
                    _lcm_lst_ReplenishmentStart.add(line);
                }
                
                // clear.
                btn_Start.setEnabled(true);
                btn_AllCheck.setEnabled(true);
                btn_AllCheckClear.setEnabled(true);
                btn_AllClear.setEnabled(true);
            }
            else
            {
                btn_Start.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_AllClear.setEnabled(false);
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
        for (int i = 1; i <= _lcm_lst_ReplenishmentStart.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_ReplenishmentStart.get(i);
            lst_ReplenishmentStart.setCurrentRow(i);
            clearLine.setValue(KEY_LST_COLUMN_1, Boolean.TRUE);
            lst_ReplenishmentStart_SetLineToolTip(clearLine);
            _lcm_lst_ReplenishmentStart.set(i, clearLine);
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
        for (int i = 1; i <= _lcm_lst_ReplenishmentStart.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_ReplenishmentStart.get(i);
            lst_ReplenishmentStart.setCurrentRow(i);
            clearLine.setValue(KEY_LST_COLUMN_1, Boolean.FALSE);
            lst_ReplenishmentStart_SetLineToolTip(clearLine);
            _lcm_lst_ReplenishmentStart.set(i, clearLine);
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
        _lcm_lst_ReplenishmentStart.clear();
        btn_Start.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);

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
