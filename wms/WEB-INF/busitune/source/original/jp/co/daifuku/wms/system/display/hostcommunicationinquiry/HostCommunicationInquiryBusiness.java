// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.system.display.hostcommunicationinquiry;

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
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
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
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsDataTypePullDownModel;
import jp.co.daifuku.wms.system.display.hostcommunicationinquiry.HostCommunicationInquiry;
import jp.co.daifuku.wms.system.schedule.HostCommunicationInquirySCH;
import jp.co.daifuku.wms.system.schedule.HostCommunicationInquirySCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class HostCommunicationInquiryBusiness
        extends HostCommunicationInquiry
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_HostCommunicationInquiry(HIDDEN_START_DATE) */
    private static final ListCellKey KEY_HIDDEN_START_DATE = new ListCellKey("HIDDEN_START_DATE", new DateCellColumn(null, TIME_FORMAT.HMS), false, false);

    /** lst_HostCommunicationInquiry(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_HostCommunicationInquiry(LST_START_DATE) */
    private static final ListCellKey KEY_LST_START_DATE = new ListCellKey("LST_START_DATE", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), true, false);

    /** lst_HostCommunicationInquiry(LST_FILE_NAME) */
    private static final ListCellKey KEY_LST_FILE_NAME = new ListCellKey("LST_FILE_NAME", new StringCellColumn(), true, false);

    /** lst_HostCommunicationInquiry(LST_STATUS) */
    private static final ListCellKey KEY_LST_STATUS = new ListCellKey("LST_STATUS", new StringCellColumn(), true, false);

    /** lst_HostCommunicationInquiry(LST_DETAILS) */
    private static final ListCellKey KEY_LST_DETAILS = new ListCellKey("LST_DETAILS", new StringCellColumn(), true, false);

    /** lst_HostCommunicationInquiry keys */
    private static final ListCellKey[] LST_HOSTCOMMUNICATIONINQUIRY_KEYS = {
        KEY_HIDDEN_START_DATE,
        KEY_LST_NO,
        KEY_LST_START_DATE,
        KEY_LST_FILE_NAME,
        KEY_LST_STATUS,
        KEY_LST_DETAILS,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** RadioButtonGroupModel CommunicationType */
    private RadioButtonGroup _grp_CommunicationType;

    /** PullDownModel pul_CommunicationData */
    private WmsDataTypePullDownModel _pdm_pul_CommunicationData;

    /** ListCellModel lst_HostCommunicationInquiry */
    private ScrollListCellModel _lcm_lst_HostCommunicationInquiry;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public HostCommunicationInquiryBusiness()
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
    public void rdo_Receive_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_Receive_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Send_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_Send_Click_Process();
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

        // initialize CommunicationType.
        _grp_CommunicationType = new RadioButtonGroup(new RadioButton[]{rdo_Receive, rdo_Send}, locale);

        // initialize pul_CommunicationData.
        _pdm_pul_CommunicationData = new WmsDataTypePullDownModel(pul_CommunicationData, locale, ui);

        // initialize lst_HostCommunicationInquiry.
        _lcm_lst_HostCommunicationInquiry = new ScrollListCellModel(lst_HostCommunicationInquiry, LST_HOSTCOMMUNICATIONINQUIRY_KEYS, locale);
        _lcm_lst_HostCommunicationInquiry.setToolTipVisible(KEY_LST_NO, false);
        _lcm_lst_HostCommunicationInquiry.setToolTipVisible(KEY_LST_START_DATE, false);
        _lcm_lst_HostCommunicationInquiry.setToolTipVisible(KEY_LST_FILE_NAME, false);
        _lcm_lst_HostCommunicationInquiry.setToolTipVisible(KEY_LST_STATUS, false);
        _lcm_lst_HostCommunicationInquiry.setToolTipVisible(KEY_LST_DETAILS, false);
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

            // load pul_CommunicationData.
            _pdm_pul_CommunicationData.init(conn, DataType.RECEIVE);
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
    private void lst_HostCommunicationInquiry_SetLineToolTip(ListCellLine line)
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
        setFocus(pul_CommunicationData);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        rdo_Receive.setChecked(true);
    }

    /**
     *
     * @throws Exception
     */
    private void rdo_Receive_Click_Process()
            throws Exception
    {
        // clear.
        _pdm_pul_CommunicationData.setSelectedValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void rdo_Send_Click_Process()
            throws Exception
    {
        // clear.
        _pdm_pul_CommunicationData.setSelectedValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_FromSearchDate.validate(this, false);
        txt_FromSearchTime.validate(this, false);
        txt_SearchDate.validate(this, false);
        txt_ToSearchTime.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        HostCommunicationInquirySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new HostCommunicationInquirySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            HostCommunicationInquirySCHParams inparam = new HostCommunicationInquirySCHParams();
            inparam.set(HostCommunicationInquirySCHParams.COMMUNICATION_DATA, _pdm_pul_CommunicationData.getSelectedValue());
            inparam.set(HostCommunicationInquirySCHParams.SEARCH_DATE_FROM, txt_FromSearchDate.getValue());
            inparam.set(HostCommunicationInquirySCHParams.SEARCH_TIME_FROM, txt_FromSearchTime.getValue());
            inparam.set(HostCommunicationInquirySCHParams.SEARCH_DATE_TO, txt_SearchDate.getValue());
            inparam.set(HostCommunicationInquirySCHParams.SEARCH_TIME_TO, txt_ToSearchTime.getValue());
            inparam.set(HostCommunicationInquirySCHParams.COMMUNICATION_TYPE, _grp_CommunicationType.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_HostCommunicationInquiry.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_HostCommunicationInquiry.getNewLine();
                line.setValue(KEY_LST_NO, outparam.get(HostCommunicationInquirySCHParams.NO));
                line.setValue(KEY_LST_START_DATE, outparam.get(HostCommunicationInquirySCHParams.START_DATE));
                line.setValue(KEY_LST_FILE_NAME, outparam.get(HostCommunicationInquirySCHParams.FILE_NAME));
                line.setValue(KEY_LST_STATUS, outparam.get(HostCommunicationInquirySCHParams.STATUS));
                line.setValue(KEY_HIDDEN_START_DATE, outparam.get(HostCommunicationInquirySCHParams.HID_START_DATE));
                lst_HostCommunicationInquiry_SetLineToolTip(line);
                _lcm_lst_HostCommunicationInquiry.add(line);
            }

            // clear.
            for (int i = 1; i <= _lcm_lst_HostCommunicationInquiry.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_HostCommunicationInquiry.get(i);
                lst_HostCommunicationInquiry.setCurrentRow(i);
                lst_HostCommunicationInquiry.setCellEnabled(_lcm_lst_HostCommunicationInquiry.getColumnIndex(KEY_LST_DETAILS), false);
                lst_HostCommunicationInquiry_SetLineToolTip(clearLine);
                _lcm_lst_HostCommunicationInquiry.set(i, clearLine);
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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        rdo_Receive.setChecked(true);
        _pdm_pul_CommunicationData.setSelectedValue(null);
        txt_FromSearchDate.setValue(null);
        txt_FromSearchTime.setValue(null);
        txt_SearchDate.setValue(null);
        txt_ToSearchTime.setValue(null);

        // set focus.
        setFocus(rdo_Receive);
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
