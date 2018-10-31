// $Id: InventoryMovementBusiness.java 7845 2010-04-21 05:06:26Z shibamoto $
package jp.co.daifuku.wms.analysis.display.inventorymovement;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
import jp.co.daifuku.bluedog.model.PullDownModel;
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
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.analysis.display.inventorymovement.InventoryMovement;
import jp.co.daifuku.wms.analysis.display.inventorymovement.ViewStateKeys;
import jp.co.daifuku.wms.analysis.exporter.InventoryMovementParams;
import jp.co.daifuku.wms.analysis.schedule.InventoryMovementSCH;
import jp.co.daifuku.wms.analysis.schedule.InventoryMovementSCHParams;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7845 $, $Date:: 2010-04-21 14:06:26 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class InventoryMovementBusiness
        extends InventoryMovement
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
    /** PullDownModel pul_SearchCond */
    private DefaultPullDownModel _pdm_pul_SearchCond;

    /** RadioButtonGroupModel ANA_InventoryMovement */
    private RadioButtonGroup _grp_ANA_InventoryMovement;

    /** PullDownModel pul_TermYearMonth */
    private DefaultPullDownModel _pdm_pul_TermYearMonth;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public InventoryMovementBusiness()
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
        if (eventSource.equals("btn_ItemSearch_Click"))
        {
            // process call.
            btn_ItemSearch_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ItemSearch_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ItemSearch_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_View_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_View_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ExcelOutput_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ExcelOutput_Click_Process();
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

        // initialize pul_SearchCond.
        _pdm_pul_SearchCond = new DefaultPullDownModel(pul_SearchCond, locale, ui);

        // initialize ANA_InventoryMovement.
        _grp_ANA_InventoryMovement = new RadioButtonGroup(new RadioButton[]{rdo_UnitMonth, rdo_UnitDay}, locale);

        // initialize pul_TermYearMonth.
        _pdm_pul_TermYearMonth = new DefaultPullDownModel(pul_TermYearMonth, locale, ui);
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

            // load pul_SearchCond.
            _pdm_pul_SearchCond.init(conn);

            // load pul_TermYearMonth.
            _pdm_pul_TermYearMonth.init(conn);
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
        setFocus(txt_ItemCode);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        rdo_UnitMonth.setChecked(true);
        rdo_UnitDay.setChecked(false);
        pul_TermYearMonth.setEnabled(false);
        btn_ExcelOutput.setEnabled(false);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ItemSearch_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstItemParams.SEARCH_COND, _pdm_pul_SearchCond.getSelectedValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_ItemSearch_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_ItemSearch_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstItemParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemParams.ITEM_NAME));

        // set focus.
        setFocus(txt_ItemCode);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_View_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ItemCode.validate(this, true);
        pul_SearchCond.validate(this, false);
        txt_ItemName.validate(this, false);
        rdo_UnitMonth.validate(false);
        pul_TermYearMonth.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        InventoryMovementSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new InventoryMovementSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            InventoryMovementSCHParams inparam = new InventoryMovementSCHParams();
            inparam.set(InventoryMovementSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(InventoryMovementSCHParams.ITEM_NAME, txt_ItemName.getValue());
            inparam.set(InventoryMovementSCHParams.ANALYSIS_MODE, _grp_ANA_InventoryMovement.getSelectedValue());
            inparam.set(InventoryMovementSCHParams.ITEM_LIST_CONDITION, _pdm_pul_SearchCond.getSelectedValue());
            inparam.set(InventoryMovementSCHParams.ANALYSIS_START_DATE, "");
            inparam.set(InventoryMovementSCHParams.ANALYSIS_END_DATE, "");
            inparam.set(InventoryMovementSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                viewState.setObject(ViewStateKeys.VS_ITEM_CODE, txt_ItemCode.getValue());
                viewState.setObject(ViewStateKeys.VS_SEARCH_COND, _pdm_pul_SearchCond.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_INVENTRY_MOVEMENT, _grp_ANA_InventoryMovement.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_TEEM_YEAR_MONTH, _pdm_pul_TermYearMonth.getSelectedValue());
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
    private void btn_ExcelOutput_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newExcelExporter("InventoryMovement", getSession());
            File downloadFile = exporter.open();

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(viewState.getString(ViewStateKeys.VS_ITEM_CODE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_SEARCH_COND), "");
            part11List.add("0", "", rdo_UnitMonth.getChecked());

            if (rdo_UnitDay.getChecked())
            {
                part11List.add("1", "");
                part11List.add(viewState.getString(ViewStateKeys.VS_TEEM_YEAR_MONTH), "");
            }

            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_XLS), part11List);

            // commit.
            conn.commit();

            // redirect.
            download(downloadFile.getPath());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            if (exporter != null)
            {
                exporter.close();
            }
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
        txt_ItemCode.setValue(null);
        _pdm_pul_SearchCond.setSelectedValue(null);
        txt_ItemName.setValue(null);
        rdo_UnitMonth.setChecked(true);
        _pdm_pul_TermYearMonth.setSelectedValue(null);
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
