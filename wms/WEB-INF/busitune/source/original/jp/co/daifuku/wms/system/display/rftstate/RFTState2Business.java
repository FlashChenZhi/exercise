// $Id: RFTState2Business.java 7666 2010-03-17 13:20:58Z kishimoto $
package jp.co.daifuku.wms.system.display.rftstate;

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
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
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
import jp.co.daifuku.wms.system.display.rftstate.RFTState2;
import jp.co.daifuku.wms.system.display.rftstate.RFTState2Params;
import jp.co.daifuku.wms.system.display.rftstate.ViewStateKeys;
import jp.co.daifuku.wms.system.schedule.RFTStateSCH;
import jp.co.daifuku.wms.system.schedule.RFTStateSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7666 $, $Date:: 2010-03-17 22:20:58 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class RFTState2Business
        extends RFTState2
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
    /** RadioButtonGroupModel HalfWork */
    private RadioButtonGroup _grp_HalfWork;

    /** RadioButtonGroupModel Deficiency */
    private RadioButtonGroup _grp_Deficiency;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RFTState2Business()
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
        if (eventSource.equals("btn_Submit_Click"))
        {
            // process call.
            btn_Submit_Click_Process(false);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Submit_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Submit_Click_Process(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ReDisplay_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ReDisplay_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Close_U_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_U_Click_Process();
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

        // initialize HalfWork.
        _grp_HalfWork = new RadioButtonGroup(new RadioButton[]{rdo_HalfWork_Cancel, rdo_HalfWork_Commit}, locale);

        // initialize Deficiency.
        _grp_Deficiency = new RadioButtonGroup(new RadioButton[]{rdo_Deficiency_PartialDelivery, rdo_Deficiency_Shortage}, locale);
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
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(rdo_HalfWork_Cancel);
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
        RFTStateSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RFTStateSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RFTStateSCHParams inparam = new RFTStateSCHParams();
            RFTState2Params requestParam = new RFTState2Params(request);
            inparam.set(RFTStateSCHParams.RFT_NO, requestParam.get(RFTState2Params.RFT_NO));

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                lbl_JavaSetRftNo.setValue(outparam.get(RFTStateSCHParams.RFT_NO));
                lbl_JavaSetTerminalType.setValue(outparam.get(RFTStateSCHParams.TERMINAL_TYPE_NAME));
                lbl_JavaSetRftStatus.setValue(outparam.get(RFTStateSCHParams.STATUS_FLAG_NAME));
                lbl_JavaSetUserName.setValue(outparam.get(RFTStateSCHParams.USER_NAME));
                viewState.setObject(ViewStateKeys.VS_TERMINAL_TYPE, outparam.get(RFTStateSCHParams.TERMINAL_TYPE));
                viewState.setObject(ViewStateKeys.VS_USER_ID, outparam.get(RFTStateSCHParams.USER_ID));
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
     * @param confirm
     * @throws Exception
     */
    private void btn_Submit_Click_Process(boolean confirm)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RFTStateSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RFTStateSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RFTStateSCHParams inparam = new RFTStateSCHParams();
            inparam.setProcessFlag(ProcessFlag.UPDATE);
            inparam.set(RFTStateSCHParams.WORK_ON_THE_WAY, _grp_HalfWork.getSelectedValue());
            inparam.set(RFTStateSCHParams.LACK_FLAG, _grp_Deficiency.getSelectedValue());
            inparam.set(RFTStateSCHParams.RFT_NO, lbl_JavaSetRftNo.getValue());
            inparam.set(RFTStateSCHParams.USER_ID, viewState.getObject(ViewStateKeys.VS_USER_ID));

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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Submit_Click");
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
            part11List.add(lbl_JavaSetRftNo.getStringValue(), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_TERMINAL_TYPE), "");
            part11List.add(lbl_JavaSetRftStatus.getStringValue(), "");
            part11List.add(lbl_JavaSetUserName.getStringValue(), "");
            part11List.add("1", "", rdo_HalfWork_Cancel.getChecked());
            part11List.add("2", "", rdo_HalfWork_Commit.getChecked());
            part11List.add("2", "", rdo_Deficiency_PartialDelivery.getChecked());
            part11List.add("1", "", rdo_Deficiency_Shortage.getChecked());
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // set input parameters.
            inparam = new RFTStateSCHParams();
            inparam.set(RFTStateSCHParams.RFT_NO, lbl_JavaSetRftNo.getValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                lbl_JavaSetRftNo.setValue(outparam.get(RFTStateSCHParams.RFT_NO));
                lbl_JavaSetTerminalType.setValue(outparam.get(RFTStateSCHParams.TERMINAL_TYPE_NAME));
                lbl_JavaSetRftStatus.setValue(outparam.get(RFTStateSCHParams.STATUS_FLAG_NAME));
                lbl_JavaSetUserName.setValue(outparam.get(RFTStateSCHParams.USER_NAME));
                viewState.setObject(ViewStateKeys.VS_TERMINAL_TYPE, outparam.get(RFTStateSCHParams.TERMINAL_TYPE));
                viewState.setObject(ViewStateKeys.VS_USER_ID, outparam.get(RFTStateSCHParams.USER_ID));
            }

            // set focus.
            setFocus(rdo_HalfWork_Cancel);
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
    private void btn_ReDisplay_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RFTStateSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RFTStateSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RFTStateSCHParams inparam = new RFTStateSCHParams();
            inparam.set(RFTStateSCHParams.RFT_NO, lbl_JavaSetRftNo.getValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                lbl_JavaSetRftNo.setValue(outparam.get(RFTStateSCHParams.RFT_NO));
                lbl_JavaSetTerminalType.setValue(outparam.get(RFTStateSCHParams.TERMINAL_TYPE_NAME));
                lbl_JavaSetRftStatus.setValue(outparam.get(RFTStateSCHParams.STATUS_FLAG_NAME));
                lbl_JavaSetUserName.setValue(outparam.get(RFTStateSCHParams.USER_NAME));
                viewState.setObject(ViewStateKeys.VS_TERMINAL_TYPE, outparam.get(RFTStateSCHParams.TERMINAL_TYPE));
                viewState.setObject(ViewStateKeys.VS_USER_ID, outparam.get(RFTStateSCHParams.USER_ID));
            }

            // set focus.
            setFocus(rdo_HalfWork_Cancel);
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
    private void btn_Close_U_Click_Process()
            throws Exception
    {
        parentRedirect(null);
        dispose();
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
