// $Id: AreaModify2Business.java 7541 2010-03-13 12:08:20Z kishimoto $
package jp.co.daifuku.wms.master.display.areamodify;

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
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
import jp.co.daifuku.bluedog.model.PullDownModel;
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
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.master.display.areamodify.AreaModify2;
import jp.co.daifuku.wms.master.display.areamodify.ViewStateKeys;
import jp.co.daifuku.wms.master.schedule.AreaModifySCH;
import jp.co.daifuku.wms.master.schedule.AreaModifySCHParams;
import jp.co.daifuku.wms.master.schedule.MasterInParameter;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7541 $, $Date:: 2010-03-13 21:08:20 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class AreaModify2Business
        extends AreaModify2
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_TemporaryArea */
    private WmsAreaPullDownModel _pdm_pul_TemporaryArea;

    /** PullDownModel pul_VacantSearchType */
    private DefaultPullDownModel _pdm_pul_VacantSearchType;

    /** PullDownModel pul_ReceivingArea */
    private WmsAreaPullDownModel _pdm_pul_ReceivingArea;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AreaModify2Business()
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
        if (eventSource.equals("btn_Modify_Click"))
        {
            // process call.
            btn_Modify_Click_Process(false);
        }
        else if (eventSource.equals("btn_Delete_Click"))
        {
            // process call.
            btn_Delete_Click_Process(false);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Back_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Back_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Modify_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Modify_Click_Process(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Delete_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Delete_Click_Process(true);
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

        // initialize pul_TemporaryArea.
        _pdm_pul_TemporaryArea = new WmsAreaPullDownModel(pul_TemporaryArea, locale, ui);

        // initialize pul_VacantSearchType.
        _pdm_pul_VacantSearchType = new DefaultPullDownModel(pul_VacantSearchType, locale, ui);

        // initialize pul_ReceivingArea.
        _pdm_pul_ReceivingArea = new WmsAreaPullDownModel(pul_ReceivingArea, locale, ui);
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

            // load pul_TemporaryArea.
            _pdm_pul_TemporaryArea.init(conn, AreaType.TEMP, StationType.ALL, "", false);

            // load pul_VacantSearchType.
            _pdm_pul_VacantSearchType.init(conn);

            // load pul_ReceivingArea.
            _pdm_pul_ReceivingArea.init(conn, AreaType.RECEIVE, StationType.ALL, "", false);
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
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_AreaName);
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
        AreaModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AreaModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AreaModifySCHParams inparam = new AreaModifySCHParams();
            inparam.set(AreaModifySCHParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA_NO));

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_AreaName.setValue(outparam.get(AreaModifySCHParams.AREA_NAME));
                _pdm_pul_TemporaryArea.setSelectedValue(outparam.get(AreaModifySCHParams.TEMPORARY_AREA));
                _pdm_pul_VacantSearchType.setSelectedValue(outparam.get(AreaModifySCHParams.VACANT_SEARCH_TYPE));
                _pdm_pul_ReceivingArea.setSelectedValue(outparam.get(AreaModifySCHParams.RECEIVING_AREA));
                lbl_InArea.setValue(outparam.get(AreaModifySCHParams.AREA_NO));
                viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, outparam.get(AreaModifySCHParams.LAST_UPDATE_DATE));
                lbl_InLocationStyle.setValue(outparam.get(AreaModifySCHParams.LOCATION_STYLE));
                lbl_InAreaType.setValue(outparam.get(AreaModifySCHParams.AREA_TYPE));
                lbl_InLocationType.setValue(outparam.get(AreaModifySCHParams.LOCATION_TYPE));
                viewState.setObject(ViewStateKeys.VS_AREA_NAME, outparam.get(AreaModifySCHParams.AREA_NAME));
                viewState.setObject(ViewStateKeys.VS_TEMPORARY_AREA, outparam.get(AreaModifySCHParams.TEMPORARY_AREA));
                viewState.setObject(ViewStateKeys.VS_VACANT_SEARCH_TYPE, outparam.get(AreaModifySCHParams.VACANT_SEARCH_TYPE));
                viewState.setObject(ViewStateKeys.VS_RECEIVING_AREA, outparam.get(AreaModifySCHParams.RECEIVING_AREA));
                viewState.setObject(ViewStateKeys.VS_AREA_TYPE, outparam.get(AreaModifySCHParams.AREA_TYPE));
                viewState.setObject(ViewStateKeys.VS_LOCATION_TYPE, outparam.get(AreaModifySCHParams.LOCATION_TYPE));
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
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Back_Click_Process()
            throws Exception
    {
        try
        {
            // forward.
            forward("/master/areamodify/AreaModify.do");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_Modify_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        txt_AreaName.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AreaModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AreaModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AreaModifySCHParams inparam = new AreaModifySCHParams();
            inparam.setProcessFlag(ProcessFlag.UPDATE);
            inparam.set(AreaModifySCHParams.AREA_NAME, txt_AreaName.getValue());
            inparam.set(AreaModifySCHParams.MOVE_TEMPORARY_STORAGE, chk_MoveTemporaryStorage.getValue());
            inparam.set(AreaModifySCHParams.VACANT_SEARCH_TYPE, _pdm_pul_VacantSearchType.getSelectedValue());
            inparam.set(AreaModifySCHParams.TEMPORARY_AREA, _pdm_pul_TemporaryArea.getSelectedValue());
            inparam.set(AreaModifySCHParams.RECEIVING_AREA, _pdm_pul_ReceivingArea.getSelectedValue());
            inparam.set(AreaModifySCHParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA_NO));
            inparam.set(AreaModifySCHParams.LAST_UPDATE_DATE, viewState.getObject(ViewStateKeys.LAST_UPDATE_DATE));
            inparam.set(AreaModifySCHParams.PROCESS_FLAG, MasterInParameter.PROCESS_FLAG_MODIFY);

            // SCH call.
            if (confirm && !sch.check(inparam))
            {
                if (StringUtil.isBlank(sch.getDispMessage()))
                {
                    // show message.
                    message.setMsgResourceKey(sch.getMessage());
                    return;
                }
                else
                {
                    // show confirm message.
                    this.setConfirm(sch.getDispMessage(), false, true);
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Modify_Click");
                    return;
                }
            }

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(lbl_InArea.getStringValue(), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_AREA_TYPE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_LOCATION_TYPE), "");
            part11List.add(lbl_InLocationStyle.getStringValue(), "");
            part11List.add(txt_AreaName.getStringValue(), "");

            if (chk_MoveTemporaryStorage.getChecked())
            {
                part11List.add(_pdm_pul_TemporaryArea.getSelectedStringValue(), "");
                part11List.add("1", "");
            }
            else
            {
                part11List.add("", "");
                part11List.add("0", "");
            }

            part11List.add(_pdm_pul_VacantSearchType.getSelectedStringValue(), "");

            if (chk_ReceivingArea.getChecked())
            {
                part11List.add(_pdm_pul_ReceivingArea.getSelectedStringValue(), "");
                part11List.add("1", "");
            }
            else
            {
                part11List.add("", "");
                part11List.add("0", "");
            }

            part11List.add(viewState.getString(ViewStateKeys.VS_AREA_NAME), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_TEMPORARY_AREA), "");
            part11List.add(chk_MoveTemporaryStorage.getStringValue(), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_VACANT_SEARCH_TYPE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_RECEIVING_AREA), "");
            part11List.add(chk_ReceivingArea.getStringValue(), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_MODIFY), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // set input parameters.
            inparam = new AreaModifySCHParams();
            inparam.set(AreaModifySCHParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA_NO));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                txt_AreaName.setValue(outparam.get(AreaModifySCHParams.AREA_NAME));
                _pdm_pul_TemporaryArea.setSelectedValue(outparam.get(AreaModifySCHParams.TEMPORARY_AREA));
                _pdm_pul_VacantSearchType.setSelectedValue(outparam.get(AreaModifySCHParams.VACANT_SEARCH_TYPE));
                _pdm_pul_ReceivingArea.setSelectedValue(outparam.get(AreaModifySCHParams.RECEIVING_AREA));
                viewState.setObject(ViewStateKeys.AREA_NO, outparam.get(AreaModifySCHParams.AREA_NO));
                viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, outparam.get(AreaModifySCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.VS_AREA_NAME, outparam.get(AreaModifySCHParams.AREA_NAME));
                viewState.setObject(ViewStateKeys.VS_TEMPORARY_AREA, outparam.get(AreaModifySCHParams.TEMPORARY_AREA));
                viewState.setObject(ViewStateKeys.VS_VACANT_SEARCH_TYPE, outparam.get(AreaModifySCHParams.VACANT_SEARCH_TYPE));
                viewState.setObject(ViewStateKeys.VS_RECEIVING_AREA, outparam.get(AreaModifySCHParams.RECEIVING_AREA));
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
     * @param confirm
     * @throws Exception
     */
    private void btn_Delete_Click_Process(boolean confirm)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AreaModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AreaModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AreaModifySCHParams inparam = new AreaModifySCHParams();
            inparam.setProcessFlag(ProcessFlag.DELETE);
            inparam.set(AreaModifySCHParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA_NO));
            inparam.set(AreaModifySCHParams.LAST_UPDATE_DATE, viewState.getObject(ViewStateKeys.LAST_UPDATE_DATE));
            inparam.set(AreaModifySCHParams.PROCESS_FLAG, MasterInParameter.PROCESS_FLAG_DELETE);

            // SCH call.
            if (confirm && !sch.check(inparam))
            {
                if (StringUtil.isBlank(sch.getDispMessage()))
                {
                    // show message.
                    message.setMsgResourceKey(sch.getMessage());
                    return;
                }
                else
                {
                    // show confirm message.
                    this.setConfirm(sch.getDispMessage(), false, true);
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Delete_Click");
                    return;
                }
            }

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(lbl_InArea.getStringValue(), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_AREA_TYPE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_LOCATION_TYPE), "");
            part11List.add(lbl_InLocationStyle.getStringValue(), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_AREA_NAME), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_TEMPORARY_AREA), "");

            if (chk_MoveTemporaryStorage.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            part11List.add(viewState.getString(ViewStateKeys.VS_VACANT_SEARCH_TYPE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_RECEIVING_AREA), "");

            if (chk_ReceivingArea.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_DELETE), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // clear.
            txt_AreaName.setValue(null);
            _pdm_pul_TemporaryArea.setSelectedValue(null);
            chk_MoveTemporaryStorage.setChecked(false);
            _pdm_pul_VacantSearchType.setSelectedValue(null);
            _pdm_pul_ReceivingArea.setSelectedValue(null);
            chk_ReceivingArea.setChecked(false);
            btn_Modify.setEnabled(false);
            btn_Delete.setEnabled(false);
            btn_Clear.setEnabled(false);
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
    private void btn_Clear_Click_Process()
            throws Exception
    {
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
