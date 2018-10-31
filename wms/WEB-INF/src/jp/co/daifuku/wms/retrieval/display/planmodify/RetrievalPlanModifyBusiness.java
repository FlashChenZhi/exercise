// $Id: RetrievalPlanModifyBusiness.java,v 1.2 2009/02/24 02:39:16 ose Exp $
package jp.co.daifuku.wms.retrieval.display.planmodify;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.retrieval.listbox.plan.LstRetrievalPlanMntParams;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalPlanModifySCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalPlanModifySCHParams;

/**
 * 出庫予定修正・削除（基本情報設定）の画面処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:39:16 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class RetrievalPlanModifyBusiness
        extends RetrievalPlanModify
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RetrievalPlanModifyBusiness()
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

        // process call.
        page_Initialize_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        // get event source.
        DialogParameters dialogParams = ((DialogEvent)e).getDialogParameters();
        String eventSource = dialogParams.getParameter(_KEY_POPUPSOURCE);
        if (StringUtil.isBlank(eventSource))
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_SearchRetrievalPlan_Click"))
        {
            // process call.
            btn_SearchRetrievalPlan_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchRetrievalPlan_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchRetrievalPlan_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Next_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Next_Click_Process();
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
        setFocus(txt_RetrievalPlanDate);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // output display.
        txt_RetrievalPlanDate.setValue(viewState.getObject(ViewStateKeys.PLAN_DAY));
        txt_SlipNumber.setValue(viewState.getObject(ViewStateKeys.SLIP_NUMBER));
        txt_LineNo.setValue(viewState.getObject(ViewStateKeys.LINE_NO));

        // DFKLOOK ここから修正
        savePackageFlags();
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchRetrievalPlan_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstRetrievalPlanMntParams inparam = new LstRetrievalPlanMntParams();
        inparam.set(LstRetrievalPlanMntParams.PLAN_DAY, txt_RetrievalPlanDate.getValue());
        inparam.set(LstRetrievalPlanMntParams.TICKET_NO, txt_SlipNumber.getValue());
        inparam.set(LstRetrievalPlanMntParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstRetrievalPlanMntParams.STATUS_FLAG, SystemDefine.STATUS_FLAG_UNSTART);

        // DFKLOOK ここから修正
        // 在庫ﾊﾟｯｹｰｼﾞ導入時は未処理、未導入時は処理済とする
        if (this.viewState.getBoolean(ViewStateKeys.STOCKPACK))
        {
            // 有りの場合
            inparam.set(LstRetrievalPlanMntParams.SCHEDULE_FLAG, SystemDefine.SCH_FLAG_NOT_SCHEDULE);
        }
        else
        {
            // 無しの場合
            inparam.set(LstRetrievalPlanMntParams.SCHEDULE_FLAG, SystemDefine.SCH_FLAG_SCHEDULE);
        }
        // DFKLOOK ここまで修正
        
        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchRetrievalPlan_Click");
        redirect("/retrieval/listbox/plan/LstRetrievalPlanMnt.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchRetrievalPlan_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstRetrievalPlanMntParams outparam = new LstRetrievalPlanMntParams(dialogParams);
        txt_RetrievalPlanDate.setValue(outparam.get(LstRetrievalPlanMntParams.PLAN_DAY));
        txt_SlipNumber.setValue(outparam.get(LstRetrievalPlanMntParams.TICKET_NO));

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Next_Click_Process()
            throws Exception
    {
        // input validation.
        txt_RetrievalPlanDate.validate(this, true);
        txt_SlipNumber.validate(this, true);
        txt_LineNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalPlanModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalPlanModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RetrievalPlanModifySCHParams inparam = new RetrievalPlanModifySCHParams();
            inparam.set(RetrievalPlanModifySCHParams.PLAN_DAY, txt_RetrievalPlanDate.getValue());
            inparam.set(RetrievalPlanModifySCHParams.SLIP_NUMBER, txt_SlipNumber.getValue());
            inparam.set(RetrievalPlanModifySCHParams.LINE_NO, txt_LineNo.getValue());
            inparam.set(RetrievalPlanModifySCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            if (!sch.nextCheck(inparam))
            {
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // set ViewState parameters.
            viewState.setObject(ViewStateKeys.PLAN_DAY, txt_RetrievalPlanDate.getValue());
            viewState.setObject(ViewStateKeys.SLIP_NUMBER, txt_SlipNumber.getValue());
            viewState.setObject(ViewStateKeys.LINE_NO, txt_LineNo.getValue());
            viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // forward.
            forward("/retrieval/planmodify/RetrievalPlanModify2.do");
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
        txt_RetrievalPlanDate.setValue(null);
        txt_SlipNumber.setValue(null);
        txt_LineNo.setValue(null);

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
    /**
     * パッケージフラグを取得して、ViewStateに保持します
     */
    private void savePackageFlags() throws Exception
    {
        // パッケージ導入有無をセット
        //WmsScheduler schedule = null;
        Connection conn = null;

        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();
        
        try
        {
            // コネクションを取得します
            conn = ConnectionManager.getRequestConnection(this);
            // スケジューラーをインスタンス化
            RetrievalPlanModifySCH schedule = new RetrievalPlanModifySCH(conn, this.getClass(), locale, ui);

            Params outparam = schedule.initFind(null);

            // マスタ管理導入フラグ
            this.viewState.setBoolean(ViewStateKeys.MASTER, outparam.getBoolean(RetrievalPlanModifySCHParams.MASTER));
            // 在庫パッケージ導入フラグ
            this.viewState.setBoolean(ViewStateKeys.STOCKPACK, outparam.getBoolean(RetrievalPlanModifySCHParams.STOCKPACK));
        }
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            try
            {
                // コネクションをクローズします
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (Exception ex)
            {
                message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
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
