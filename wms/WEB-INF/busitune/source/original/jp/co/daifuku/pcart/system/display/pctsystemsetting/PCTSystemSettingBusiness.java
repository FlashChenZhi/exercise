// $Id: PCTSystemSettingBusiness.java 5909 2009-11-16 05:37:23Z kishimoto $
package jp.co.daifuku.pcart.system.display.pctsystemsetting;

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
import jp.co.daifuku.Constants;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.pcart.system.display.pctsystemsetting.PCTSystemSetting;
import jp.co.daifuku.pcart.system.schedule.PCTSystemSettingSCH;
import jp.co.daifuku.pcart.system.schedule.PCTSystemSettingSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5909 $, $Date:: 2009-11-16 14:37:23 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class PCTSystemSettingBusiness
        extends PCTSystemSetting
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
    /** RadioButtonGroupModel RankSetting */
    private RadioButtonGroup _grp_RankSetting;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTSystemSettingBusiness()
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
        if (eventSource.equals("btn_NoProcess_Click"))
        {
            // process call.
            btn_NoProcess_Click_Process(false);
        }
        else if (eventSource.equals("btn_Set_Click"))
        {
            // process call.
            btn_Set_Click_Process(false);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_NoProcess_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_NoProcess_Click_Process(true);
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
        btn_Set_Click_Process(true);
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
        Locale locale = httpRequest.getLocale();

        // initialize RankSetting.
        _grp_RankSetting = new RadioButtonGroup(new RadioButton[]{rdo_WorkRank_Manual, rdo_WorkRank_Auto}, locale);

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
        PCTSystemSettingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTSystemSettingSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTSystemSettingSCHParams inparam = new PCTSystemSettingSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                _grp_RankSetting.setSelectedValue(outparam.get(PCTSystemSettingSCHParams.RANK_SETTING_FLAG));
                txt_RankAStdNumber.setValue(outparam.get(PCTSystemSettingSCHParams.A_RANK_STANDARD_VALUE));
                txt_RankBStdNumber.setValue(outparam.get(PCTSystemSettingSCHParams.B_RANK_STANDARD_VALUE));
                txt_WeightErrorRateInit.setValue(outparam.get(PCTSystemSettingSCHParams.DEFULT_DISTINCT_RATE));
                txt_OriconMaxWeight.setValue(outparam.get(PCTSystemSettingSCHParams.ORICON_MAX_WEIGHT));
                txt_CenterName.setValue(outparam.get(PCTSystemSettingSCHParams.CENTER_NAME));
                txt_ItemDataload.setValue(outparam.get(PCTSystemSettingSCHParams.PCTMASTER_LOAD_FLAG));
            }

            // clear.
            txt_ItemDataload.setReadOnly(true);

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
    private void btn_NoProcess_Click_Process(boolean confirm)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTSystemSettingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTSystemSettingSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTSystemSettingSCHParams inparam = new PCTSystemSettingSCHParams();
            inparam.setProcessFlag(ProcessFlag.UPDATE);

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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_NoProcess_Click");
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

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // set input parameters.
            inparam = new PCTSystemSettingSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                txt_ItemDataload.setValue(outparam.get(PCTSystemSettingSCHParams.PCTMASTER_LOAD_FLAG));
            }

            // clear.
            btn_NoProcess.setEnabled(false);

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
    private void btn_Set_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        rdo_WorkRank_Manual.validate(false);
        txt_RankAStdNumber.validate(this, true);
        txt_RankBStdNumber.validate(this, true);
        txt_WeightErrorRateInit.validate(this, true);
        txt_OriconMaxWeight.validate(this, true);
        txt_CenterName.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTSystemSettingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTSystemSettingSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTSystemSettingSCHParams inparam = new PCTSystemSettingSCHParams();
            inparam.setProcessFlag(ProcessFlag.UPDATE);
            inparam.set(PCTSystemSettingSCHParams.RANK_SETTING_FLAG, _grp_RankSetting.getSelectedValue());
            inparam.set(PCTSystemSettingSCHParams.A_RANK_STANDARD_VALUE, txt_RankAStdNumber.getValue());
            inparam.set(PCTSystemSettingSCHParams.B_RANK_STANDARD_VALUE, txt_RankBStdNumber.getValue());
            inparam.set(PCTSystemSettingSCHParams.DEFULT_DISTINCT_RATE, txt_WeightErrorRateInit.getValue());
            inparam.set(PCTSystemSettingSCHParams.ORICON_MAX_WEIGHT, txt_OriconMaxWeight.getValue());
            inparam.set(PCTSystemSettingSCHParams.CENTER_NAME, txt_CenterName.getValue());

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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
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

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // set input parameters.
            inparam = new PCTSystemSettingSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                _grp_RankSetting.setSelectedValue(outparam.get(PCTSystemSettingSCHParams.RANK_SETTING_FLAG));
                txt_RankAStdNumber.setValue(outparam.get(PCTSystemSettingSCHParams.A_RANK_STANDARD_VALUE));
                txt_RankBStdNumber.setValue(outparam.get(PCTSystemSettingSCHParams.B_RANK_STANDARD_VALUE));
                txt_WeightErrorRateInit.setValue(outparam.get(PCTSystemSettingSCHParams.DEFULT_DISTINCT_RATE));
                txt_OriconMaxWeight.setValue(outparam.get(PCTSystemSettingSCHParams.ORICON_MAX_WEIGHT));
                txt_CenterName.setValue(outparam.get(PCTSystemSettingSCHParams.CENTER_NAME));
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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTSystemSettingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTSystemSettingSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTSystemSettingSCHParams inparam = new PCTSystemSettingSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                _grp_RankSetting.setSelectedValue(outparam.get(PCTSystemSettingSCHParams.RANK_SETTING_FLAG));
                txt_RankAStdNumber.setValue(outparam.get(PCTSystemSettingSCHParams.A_RANK_STANDARD_VALUE));
                txt_RankBStdNumber.setValue(outparam.get(PCTSystemSettingSCHParams.B_RANK_STANDARD_VALUE));
                txt_WeightErrorRateInit.setValue(outparam.get(PCTSystemSettingSCHParams.DEFULT_DISTINCT_RATE));
                txt_OriconMaxWeight.setValue(outparam.get(PCTSystemSettingSCHParams.ORICON_MAX_WEIGHT));
                txt_CenterName.setValue(outparam.get(PCTSystemSettingSCHParams.CENTER_NAME));
                txt_ItemDataload.setValue(outparam.get(PCTSystemSettingSCHParams.PCTMASTER_LOAD_FLAG));
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
