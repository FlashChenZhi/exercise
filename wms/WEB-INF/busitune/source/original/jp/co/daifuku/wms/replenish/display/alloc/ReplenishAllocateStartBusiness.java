// $Id: ReplenishAllocateStartBusiness.java 7544 2010-03-15 00:25:30Z ota $
package jp.co.daifuku.wms.replenish.display.alloc;

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
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAllocPriorityPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.replenish.display.alloc.ReplenishAllocateStart;
import jp.co.daifuku.wms.replenish.schedule.ReplenishAllocateStartSCH;
import jp.co.daifuku.wms.replenish.schedule.ReplenishAllocateStartSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7544 $, $Date:: 2010-03-15 09:25:30 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class ReplenishAllocateStartBusiness
        extends ReplenishAllocateStart
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
    /** PullDownModel pul_ToReplenishmentArea */
    private WmsAreaPullDownModel _pdm_pul_ToReplenishmentArea;

    /** PullDownModel pul_AllocatedPattern */
    private WmsAllocPriorityPullDownModel _pdm_pul_AllocatedPattern;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public ReplenishAllocateStartBusiness()
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
    public void btn_Start_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Start_Click_Process();
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

        // initialize pul_ToReplenishmentArea.
        _pdm_pul_ToReplenishmentArea = new WmsAreaPullDownModel(pul_ToReplenishmentArea, locale, ui);

        // initialize pul_AllocatedPattern.
        _pdm_pul_AllocatedPattern = new WmsAllocPriorityPullDownModel(pul_AllocatedPattern, locale, ui);
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

            // load pul_ToReplenishmentArea.
            _pdm_pul_ToReplenishmentArea.init(conn, AreaType.FLOOR_FIXEDLOCATE);

            // load pul_AllocatedPattern.
            _pdm_pul_AllocatedPattern.init(conn, AllocateType.REPLENISHMENT);
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
        setFocus(pul_ToReplenishmentArea);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        chk_IssueReport.setChecked(true);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Start_Click_Process()
            throws Exception
    {
        // input validation.
        txt_FromLocation.validate(this, false);
        txt_ToLocation.validate(this, false);
        txt_ItemCode.validate(this, false);
        txt_ReplenishmentRate.validate(this, true);
        pul_ToReplenishmentArea.validate(this, true);
        pul_AllocatedPattern.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ReplenishAllocateStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ReplenishAllocateStartSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            ReplenishAllocateStartSCHParams inparam = new ReplenishAllocateStartSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(ReplenishAllocateStartSCHParams.TO_REPLENISHMENT_AREA, _pdm_pul_ToReplenishmentArea.getSelectedValue());
            inparam.set(ReplenishAllocateStartSCHParams.FROM_LOCATION, txt_FromLocation.getValue());
            inparam.set(ReplenishAllocateStartSCHParams.TO_LOCATION, txt_ToLocation.getValue());
            inparam.set(ReplenishAllocateStartSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(ReplenishAllocateStartSCHParams.RATE, txt_ReplenishmentRate.getValue());
            inparam.set(ReplenishAllocateStartSCHParams.ALLOC_PATTERN, _pdm_pul_AllocatedPattern.getSelectedValue());
            inparam.set(ReplenishAllocateStartSCHParams.ISSUE_REPORT, chk_IssueReport.getValue());
            inparam.set(ReplenishAllocateStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(ReplenishAllocateStartSCHParams.FUNCTION_ID, "M_FUNCTIONID");

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
            part11List.add(_pdm_pul_ToReplenishmentArea.getSelectedStringValue(), "");
            part11List.add(txt_FromLocation.getStringValue(), "");
            part11List.add(txt_ToLocation.getStringValue(), "");
            part11List.add(txt_ItemCode.getStringValue(), "");
            part11List.add(txt_ReplenishmentRate.getStringValue(), "");
            part11List.add(_pdm_pul_AllocatedPattern.getSelectedStringValue(), "");

            if (chk_IssueReport.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());
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
        // clear.
        _pdm_pul_ToReplenishmentArea.setSelectedValue(null);
        txt_FromLocation.setValue(null);
        txt_ToLocation.setValue(null);
        txt_ItemCode.setValue(null);
        txt_ReplenishmentRate.setValue(null);
        _pdm_pul_AllocatedPattern.setSelectedValue(null);
        chk_IssueReport.setChecked(true);
        txt_FromLocation.setReadOnly(true);
        txt_ToLocation.setReadOnly(true);
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
