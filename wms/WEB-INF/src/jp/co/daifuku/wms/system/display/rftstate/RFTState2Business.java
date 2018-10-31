// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.system.display.rftstate;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
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
import jp.co.daifuku.wms.base.controller.RftController;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.system.schedule.RFTStateSCH;
import jp.co.daifuku.wms.system.schedule.RFTStateSCHParams;
import jp.co.daifuku.wms.system.schedule.SystemInParameter;

/**
 * RFT作業状態メンテナンスの画面処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:59:02 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
@SuppressWarnings("serial")
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
        if (eventSource.startsWith("btn_Submit_Click"))
        {
            // 引数修正
            // process call.
            btn_Submit_Click_Process(eventSource);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_HalfWork_Cancel_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_HalfWork_Commit_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_DeficiencyQty_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Deficiency_PartialDelivery_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Deficiency_Shortage_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Submit_Click(ActionEvent e)
            throws Exception
    {
    	// DFKLOOK 引数追加
        // process call.
        btn_Submit_Click_Process(null);
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
    // DFKLOOK ここから修正
    /**
     * 設定処理後の画面状態を設定します。
     * @param jobType 作業区分
     * @param rftNo   端末No.
     * @param pConn  コネクション
     */
    protected void setInputAreaView(String jobType, String rftNo, Connection pConn)
    {
        // ラジオボタンの設定を行う
        // 不足数量分(分納)
        rdo_Deficiency_PartialDelivery.setChecked(false);
        rdo_Deficiency_PartialDelivery.setEnabled(false);
        // 不足数量分(欠品)
        rdo_Deficiency_Shortage.setChecked(false);
        rdo_Deficiency_Shortage.setEnabled(false);
        // 途中作業(キャンセル)
        rdo_HalfWork_Cancel.setChecked(true);
        rdo_HalfWork_Cancel.setEnabled(true);
        // 途中作業(確定)
        rdo_HalfWork_Commit.setChecked(false);
        rdo_HalfWork_Commit.setEnabled(false);

        // 作業区分が[未開始]以外の場合
        if (!SystemDefine.JOB_TYPE_UNSTART.equals(jobType))
        {
            // RFT情報コントローラ生成
            RftController rftController = new RftController(pConn, getClass());
            try
            {
                if (rftController.isWorkingData(rftNo))
                {
                    // 途中作業(確定)
                    rdo_HalfWork_Commit.setChecked(true);
                    rdo_HalfWork_Commit.setEnabled(true);

                    setFocus(rdo_HalfWork_Commit);

                    // 作業区分が「出庫」の場合
                    if (SystemDefine.JOB_TYPE_RETRIEVAL.equals(jobType))
                    {
                        // 不足数量分(分納)
                        rdo_Deficiency_PartialDelivery.setEnabled(false);
                        // 不足数量分(欠品)
                        rdo_Deficiency_Shortage.setEnabled(true);
                        rdo_Deficiency_Shortage.setChecked(true);
                    }
                    // 作業区分が「出荷」の場合
                    else if (SystemDefine.JOB_TYPE_SHIPPING.equals(jobType))
                    {
                        // 不足数量分(分納)
                        rdo_Deficiency_PartialDelivery.setEnabled(true);
                        rdo_Deficiency_PartialDelivery.setChecked(true);
                        // 不足数量分(欠品)
                        rdo_Deficiency_Shortage.setEnabled(true);
                    }
                }
            }
            catch (Exception e)
            {
                // 途中作業(確定)
                rdo_HalfWork_Commit.setChecked(false);
            }
        }
    }
    // DFKLOOK ここまで修正

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
        // DFKLOOK ここから修正
        if (rdo_HalfWork_Commit.getChecked())
        {
            setFocus(rdo_HalfWork_Commit);
        }
        else
        {
            setFocus(rdo_HalfWork_Cancel);
        }
        // DFKLOOK ここまで修正

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

                // DFKLOOK ここから修正
                setInputAreaView(String.valueOf(outparam.get(RFTStateSCHParams.STATUS_FLAG)),
                        String.valueOf(outparam.get(RFTStateSCHParams.RFT_NO)), conn);
                // DFKLOOK ここまで修正
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
    private void btn_Submit_Click_Process(String eventSource)
            throws Exception
    {
        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
            // 設定しますか?
            this.setConfirm("MSG-W9000", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Submit_Click");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RFTStateSCH sch = null;

        // DFKLOOK ここから修正
        // 途中作業ラジオボタン選択
        int flg_halfwayRadioButton = 0;
        // DFKLOOK ここまで修正

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

            // DFKLOOK ここから修正
            // 完了区分
            if (rdo_HalfWork_Cancel.getChecked())
            {
                // キャンセル
                inparam.set(RFTStateSCHParams.WORK_ON_THE_WAY, SystemInParameter.RFT_WORK_CANSEL);
            }
            else
            {
                // 確定
                inparam.set(RFTStateSCHParams.WORK_ON_THE_WAY, SystemInParameter.RFT_WORK_COMFIRM);
                flg_halfwayRadioButton = 1;
            }

            // 不足数量分
            if (rdo_Deficiency_PartialDelivery.getChecked())
            {
                // 分納
                inparam.set(RFTStateSCHParams.LACK_FLAG, SystemInParameter.COMPLETION_FLAG_REMNANT);
            }
            else if (rdo_Deficiency_Shortage.getChecked())
            {
                // 欠品
                inparam.set(RFTStateSCHParams.LACK_FLAG, SystemInParameter.COMPLETION_FLAG_DECISION);
            }
            // DFKLOOK ここまで修正

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // DFKLOOK ここから修正
                if (flg_halfwayRadioButton == 1)
                {
                    // 確定にフォーカスセット
                    setFocus(rdo_HalfWork_Commit);
                }
                else
                {
                    // キャンセルにフォーカスセット
                    setFocus(rdo_HalfWork_Cancel);
                }
                // DFKLOOK ここまで修正

                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(lbl_JavaSetRftNo.getStringValue(), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_TERMINAL_TYPE), "");
            //DFKLOOK:ここから修正
            part11List.add(lbl_JavaSetRftStatus.getText(), "");
            part11List.add(lbl_JavaSetUserName.getText(), "");
            //DFKLOOK:ここまで修正
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

                // DFKLOOK ここから修正
                setInputAreaView(String.valueOf(outparam.get(RFTStateSCHParams.STATUS_FLAG)),
                        String.valueOf(outparam.get(RFTStateSCHParams.RFT_NO)), conn);
                // DFKLOOK ここまで修正

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

                // DFKLOOK ここから修正
                setInputAreaView(String.valueOf(outparam.get(RFTStateSCHParams.STATUS_FLAG)),
                        String.valueOf(outparam.get(RFTStateSCHParams.RFT_NO)), conn);
                // DFKLOOK ここまで修正
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
