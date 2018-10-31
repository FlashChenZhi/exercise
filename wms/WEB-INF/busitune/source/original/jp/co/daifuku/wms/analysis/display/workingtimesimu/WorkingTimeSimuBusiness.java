// $Id: WorkingTimeSimuBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.wms.analysis.display.workingtimesimu;

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
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.analysis.display.workingtimesimu.WorkingTimeSimu;
import jp.co.daifuku.wms.analysis.schedule.WorkingTimeSimuSCH;
import jp.co.daifuku.wms.analysis.schedule.WorkingTimeSimuSCHParams;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class WorkingTimeSimuBusiness
        extends WorkingTimeSimu
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
    /** RadioButtonGroupModel SearchDate */
    private RadioButtonGroup _grp_SearchDate;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public WorkingTimeSimuBusiness()
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
    public void rdo_SearchPlanDateAll_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_SearchPlanDateAll_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_SearchPlanDateInput_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_SearchPlanDateInput_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Select_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Select_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Simulate_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Simulate_Click_Process();
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

        // initialize SearchDate.
        _grp_SearchDate = new RadioButtonGroup(new RadioButton[]{rdo_SearchPlanDateAll, rdo_SearchPlanDateInput}, locale);

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
        setFocus(rdo_SearchPlanDateAll);

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
        WorkingTimeSimuSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new WorkingTimeSimuSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            WorkingTimeSimuSCHParams inparam = new WorkingTimeSimuSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_StorageWorkerNum.setValue(outparam.get(WorkingTimeSimuSCHParams.STORAGE_WORKER_NUM));
                txt_StorageWorkStartTime.setValue(outparam.get(WorkingTimeSimuSCHParams.STORAGE_WORK_START_TIME));
                txt_RetrievalWorkerNum.setValue(outparam.get(WorkingTimeSimuSCHParams.RETRIEVAL_WORKER_NUM));
                txt_RetrievalWorkStartTime.setValue(outparam.get(WorkingTimeSimuSCHParams.RETRIEVAL_WORK_START_TIME));
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
    private void rdo_SearchPlanDateAll_Click_Process()
            throws Exception
    {
        // clear.
        rdo_SearchPlanDateAll.setChecked(true);
        rdo_SearchPlanDateInput.setChecked(false);

    }

    /**
     *
     * @throws Exception
     */
    private void rdo_SearchPlanDateInput_Click_Process()
            throws Exception
    {
        // clear.
        rdo_SearchPlanDateAll.setChecked(false);
        rdo_SearchPlanDateInput.setChecked(true);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Select_Click_Process()
            throws Exception
    {
        // input validation.
        txt_WorkPlanDate.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        WorkingTimeSimuSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new WorkingTimeSimuSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            WorkingTimeSimuSCHParams inparam = new WorkingTimeSimuSCHParams();
            inparam.set(WorkingTimeSimuSCHParams.WORK_PLAN_DATE, txt_WorkPlanDate.getValue());
            inparam.set(WorkingTimeSimuSCHParams.BEFORE_PLAN_DATE, chk_BeforePlanDate.getValue());
            inparam.set(WorkingTimeSimuSCHParams.AFTER_PLAN_DATE, chk_AfterPlanDate.getValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_StorageItemQtyPlan.setValue(outparam.get(WorkingTimeSimuSCHParams.STORAGE_ITEM_QTY_PLAN));
                txt_StorageItemQtyInp.setValue(outparam.get(WorkingTimeSimuSCHParams.STORAGE_ITEM_QTY_INP));
                txt_StoragePieceQtyPlan.setValue(outparam.get(WorkingTimeSimuSCHParams.STORAGE_PIECE_QTY_PLAN));
                txt_StoragePieceQtyInp.setValue(outparam.get(WorkingTimeSimuSCHParams.STORAGE_PIECE_QTY_INP));
                txt_RetrievalItemQtyPlan.setValue(outparam.get(WorkingTimeSimuSCHParams.RETRIEVAL_ITEM_QTY_PLAN));
                txt_RetrievalItemQtyInp.setValue(outparam.get(WorkingTimeSimuSCHParams.RETRIEVAL_ITEM_QTY_INP));
                txt_RetrievalPieceQtyPlan.setValue(outparam.get(WorkingTimeSimuSCHParams.RETRIEVAL_PIECE_QTY_PLAN));
                txt_RetrievalPieceQtyInp.setValue(outparam.get(WorkingTimeSimuSCHParams.RETRIEVAL_PIECE_QTY_INP));
            }

            // clear.
            txt_StorageWorkingTime.setValue(null);
            txt_StorageWorkEndTime.setValue(null);
            txt_RetrievalWorkingTime.setValue(null);
            txt_RetrievalWorkEndTime.setValue(null);

            // set focus.
            setFocus(txt_StorageWorkerNum);

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
    private void btn_Simulate_Click_Process()
            throws Exception
    {
        // input validation.
        txt_StorageWorkerNum.validate(this, true);
        txt_StorageWorkStartTime.validate(this, true);
        txt_RetrievalWorkerNum.validate(this, true);
        txt_RetrievalWorkStartTime.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        WorkingTimeSimuSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new WorkingTimeSimuSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            List<Params> outparams = sch.startSCHgetParams(inparams);

            if (outparams == null)
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_StorageWorkingTime.setValue(outparam.get(WorkingTimeSimuSCHParams.STORAGE_WORKING_TIME));
                txt_StorageWorkEndTime.setValue(outparam.get(WorkingTimeSimuSCHParams.STORAGE_WORK_END_TIME));
                txt_RetrievalWorkingTime.setValue(outparam.get(WorkingTimeSimuSCHParams.RETRIEVAL_WORKING_TIME));
                txt_RetrievalWorkEndTime.setValue(outparam.get(WorkingTimeSimuSCHParams.RETRIEVAL_WORK_END_TIME));
            }

            // set focus.
            setFocus(txt_StorageWorkerNum);

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
