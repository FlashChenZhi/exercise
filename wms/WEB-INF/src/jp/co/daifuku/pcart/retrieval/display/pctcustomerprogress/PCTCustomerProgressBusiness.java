// $Id: PCTCustomerProgressBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.pcart.retrieval.display.pctcustomerprogress;

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
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.base.controller.PCTConsignorController;
import jp.co.daifuku.pcart.retrieval.listbox.regularCustomer.LstRegularCustomerParams;
import jp.co.daifuku.pcart.retrieval.schedule.PCTCustomerProgressSCH;
import jp.co.daifuku.pcart.retrieval.schedule.PCTCustomerProgressSCHParams;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetPlanWorkInquirySCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;

/**
 * 出荷先別作業進捗(グラフなし)の画面処理を行います。
 *
 * Designer : H.Okayama<BR>
 * Maker : H.Okayama<BR>
 * @version $Revision: 7996 $, $Date:: 2011-07-06 09:52:24 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class PCTCustomerProgressBusiness
        extends PCTCustomerProgress
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_PCTCustomerProgress(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE =
            new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME =
            new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress(LST_PROGRESS_RATE) */
    private static final ListCellKey KEY_LST_PROGRESS_RATE =
            new ListCellKey("LST_PROGRESS_RATE", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress(LST_ORDER_COUNT) */
    private static final ListCellKey KEY_LST_ORDER_COUNT =
            new ListCellKey("LST_ORDER_COUNT", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress(LST_BOX_COUNT) */
    private static final ListCellKey KEY_LST_BOX_COUNT =
            new ListCellKey("LST_BOX_COUNT", new NumberCellColumn(0), true, false);

    /** lst_PCTCustomerProgress(LST_LINE_COUNT) */
    private static final ListCellKey KEY_LST_LINE_COUNT =
            new ListCellKey("LST_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress(LST_LOT_COUNT) */
    private static final ListCellKey KEY_LST_LOT_COUNT =
            new ListCellKey("LST_LOT_COUNT", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress kyes */
    private static final ListCellKey[] LST_PCTCUSTOMERPROGRESS_KEYS = {
            KEY_LST_CUSTOMER_CODE,
            KEY_LST_CUSTOMER_NAME,
            KEY_LST_PROGRESS_RATE,
            KEY_LST_ORDER_COUNT,
            KEY_LST_BOX_COUNT,
            KEY_LST_LINE_COUNT,
            KEY_LST_LOT_COUNT,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_AreaNo */
    private WmsAreaPullDownModel _pdm_pul_AreaNo;

    /** RadioButtonGroupModel ProgressDisplay */
    private RadioButtonGroup _grp_ProgressDisplay;

    /** ListCellModel lst_PCTCustomerProgress */
    private ListCellModel _lcm_lst_PCTCustomerProgress;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTCustomerProgressBusiness()
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
        if (eventSource.equals("btn_P_Search_RegularCustomerCd_Click"))
        {
            // process call.
            btn_P_Search_RegularCustomerCd_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_ClientPull(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // 表示ボタン処理を行う
        btn_Display_Click(null);
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_P_Search_RegularCustomerCd_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_P_Search_RegularCustomerCd_Click_Process();
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void txt_ConsignorCode_EnterKey(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから修正
        // get locale.
        Connection conn = null;
        PCTConsignorController conController = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            conController = new PCTConsignorController(conn, this.getClass());
            txt_ConsignorName.setText(conController.getConsignorName(txt_ConsignorCode.getText(),
                    InParameter.SEARCH_TABLE_MASTER));

            setFocus(txt_RegularCustomerCode);
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
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Auto_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_Auto_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Manual_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_Manual_Click_Process();
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
    // DFKLOOK:ここから修正
    /**
     * 自動更新か手動更新かを切り替えます。
     * 
     * @throws Exception
     *             全ての例外を報告します。
     */
    protected void setRegularTransmission()
            throws Exception
    {
        // 定期送信フラグの切り替えを行います。
        if (rdo_Auto.getChecked())
        {
            setRegularTransmission(true);
        }
        else if (rdo_Manual.getChecked())
        {
            setRegularTransmission(false);
        }
    }

    // DFKLOOK:ここまで修正

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

        // initialize pul_AreaNo.
        _pdm_pul_AreaNo = new WmsAreaPullDownModel(pul_AreaNo, locale, ui);

        // initialize ProgressDisplay.
        _grp_ProgressDisplay = new RadioButtonGroup(new RadioButton[] {
                rdo_Auto,
                rdo_Manual
        }, locale);

        // initialize lst_PCTCustomerProgress.
        _lcm_lst_PCTCustomerProgress =
                new ListCellModel(lst_PCTCustomerProgress, LST_PCTCUSTOMERPROGRESS_KEYS, locale);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_CUSTOMER_CODE, true);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_PROGRESS_RATE, true);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_ORDER_COUNT, true);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_BOX_COUNT, true);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_LINE_COUNT, true);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_LOT_COUNT, true);

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

            // load pul_AreaNo.
            _pdm_pul_AreaNo.init(conn, AreaType.FLOOR, StationType.ALL, "", true);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTCustomerProgress_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_ConsignorCode);

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
        PCTCustomerProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerProgressSCHParams inparam = new PCTCustomerProgressSCHParams();

            // SCH call.
            // DFKLOOK:ここから修正
            // query() → initFind()
            Params outparam = sch.initFind(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            if (outparam != null)
            {
                txt_ConsignorCode.setValue(outparam.get(PCTRetPlanWorkInquirySCHParams.CONSIGNOR_CODE));
                txt_ConsignorName.setValue(outparam.get(PCTRetPlanWorkInquirySCHParams.CONSIGNOR_NAME));

                // clear.
                rdo_Auto.setChecked(true);

                // DFKLOOK:ここから修正
                // 定期送信フラグの切り替え
                setRegularTransmission();
                // DFKLOOK:ここまで修正
            }
            else
            {
                // clear.
                rdo_Manual.setChecked(true);

                // DFKLOOK:ここから修正
                // 定期送信フラグの切り替え
                setRegularTransmission();
                // DFKLOOK:ここまで修正
            }

            // 荷主コードがあればリストを表示する
            if (!StringUtil.isBlank(txt_ConsignorCode.getText()))
            {
                // パラメータのクリア
                inparam = new PCTCustomerProgressSCHParams();
                inparam.set(PCTCustomerProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());

                // SCH call.
                List<Params> outparams = sch.query(inparam);
                message.setMsgResourceKey(sch.getMessage());

                // DFKLOOK:ここから修正
                // データが無かった場合
                if (outparams.size() == 0)
                {
                    // (6003011)対象データはありませんでした。
                    message.setMsgResourceKey(WmsMessageFormatter.format(6003011));
                }
                // DFKLOOK:ここまで修正

                // output display.
                _lcm_lst_PCTCustomerProgress.clear();
                for (Params viewparam : outparams)
                {
                    ListCellLine line = _lcm_lst_PCTCustomerProgress.getNewLine();
                    line.setValue(KEY_LST_CUSTOMER_CODE, viewparam.get(PCTCustomerProgressSCHParams.CUSTOMER_CODE));
                    line.setValue(KEY_LST_CUSTOMER_NAME, viewparam.get(PCTCustomerProgressSCHParams.CUSTOMER_NAME));
                    line.setValue(KEY_LST_PROGRESS_RATE, viewparam.get(PCTCustomerProgressSCHParams.PROGRESS_RATE));
                    line.setValue(KEY_LST_ORDER_COUNT, viewparam.get(PCTCustomerProgressSCHParams.ORDER_COUNT));
                    line.setValue(KEY_LST_BOX_COUNT, viewparam.get(PCTCustomerProgressSCHParams.BOX_COUNT));
                    line.setValue(KEY_LST_LINE_COUNT, viewparam.get(PCTCustomerProgressSCHParams.LINE_COUNT));
                    line.setValue(KEY_LST_LOT_COUNT, viewparam.get(PCTCustomerProgressSCHParams.LOT_COUNT));
                    lst_PCTCustomerProgress_SetLineToolTip(line);
                    _lcm_lst_PCTCustomerProgress.add(line);
                }
            }
            // DFKLOOK:ここまで修正

            // clear.
            txt_RegularCustomerCode.setValue(null);
            txt_RegularCustomerName.setValue(null);
            _pdm_pul_AreaNo.setSelectedValue(null);
            txt_BatchNoPCT.setValue(null);
            txt_BatchSeqNo.setValue(null);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }

            // DFKLOOK:ここから修正
            // 表示ボタン(無効化)
            btn_Display.setEnabled(false);
            // クリアボタン(無効化)
            btn_Clear.setEnabled(false);
            // 定期送信フラグ(無効化)
            setRegularTransmission(false);
            // DFKLOOK:ここまで修正
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
    private void btn_P_Search_RegularCustomerCd_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstRegularCustomerParams inparam = new LstRegularCustomerParams();
        inparam.set(LstRegularCustomerParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstRegularCustomerParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
        inparam.set(LstRegularCustomerParams.SEARCHTABLE, InParameter.SEARCH_TABLE_PLAN);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_Search_RegularCustomerCd_Click");
        redirect("/pcart/retrieval/listbox/regularCustomer/LstRegularCustomer.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_P_Search_RegularCustomerCd_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstRegularCustomerParams outparam = new LstRegularCustomerParams(dialogParams);
        txt_RegularCustomerCode.setValue(outparam.get(LstRegularCustomerParams.REGULAR_CUSTOMER_CODE));
        txt_RegularCustomerName.setValue(outparam.get(LstRegularCustomerParams.REGULAR_CUSTOMER_NAME));

        // set focus.
        setFocus(txt_RegularCustomerCode);

    }

    /**
     *
     * @throws Exception
     */
    private void rdo_Auto_Click_Process()
            throws Exception
    {
        // DFKLOOK:ここから修正
        // clear.
        rdo_Auto.setChecked(true);

        // 定期送信フラグの切り替え
        setRegularTransmission();

        // input validation.
        txt_ConsignorCode.validate(this, true);
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void rdo_Manual_Click_Process()
            throws Exception
    {
        // DFKLOOK:ここから修正
        // clear.
        rdo_Manual.setChecked(true);

        // 定期送信フラグの切り替え
        setRegularTransmission();

        // input validation.
        txt_ConsignorCode.validate(this, true);
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);
        txt_RegularCustomerCode.validate(this, false);
        txt_BatchNoPCT.validate(this, false);
        txt_BatchSeqNo.validate(this, false);
        pul_AreaNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTCustomerProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerProgressSCHParams inparam = new PCTCustomerProgressSCHParams();
            inparam.set(PCTCustomerProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTCustomerProgressSCHParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
            inparam.set(PCTCustomerProgressSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            inparam.set(PCTCustomerProgressSCHParams.BATCH_NO, txt_BatchNoPCT.getValue());
            inparam.set(PCTCustomerProgressSCHParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            // データが無かった場合
            if (outparams.size() == 0)
            {
                // (6003011)対象データはありませんでした。
                message.setMsgResourceKey(WmsMessageFormatter.format(6003011));
            }
            // DFKLOOK:ここまで修正

            // output display.
            _lcm_lst_PCTCustomerProgress.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTCustomerProgress.getNewLine();
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(PCTCustomerProgressSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(PCTCustomerProgressSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_PROGRESS_RATE, outparam.get(PCTCustomerProgressSCHParams.PROGRESS_RATE));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(PCTCustomerProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT, outparam.get(PCTCustomerProgressSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(PCTCustomerProgressSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTCustomerProgressSCHParams.LOT_COUNT));
                lst_PCTCustomerProgress_SetLineToolTip(line);
                _lcm_lst_PCTCustomerProgress.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }
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
        txt_ConsignorCode.setValue(null);
        txt_ConsignorName.setValue(null);
        txt_RegularCustomerCode.setValue(null);
        txt_RegularCustomerName.setValue(null);
        _pdm_pul_AreaNo.setSelectedValue(null);
        txt_BatchNoPCT.setValue(null);
        txt_BatchSeqNo.setValue(null);
        rdo_Auto.setChecked(true);
        _lcm_lst_PCTCustomerProgress.clear();

        // DFKLOOK:ここから修正
        page_Load_Process();
        // DFKLOOK:ここまで修正
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
