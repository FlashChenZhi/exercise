// $Id: RFTStateInquiryBusiness.java 3990 2009-04-07 06:26:00Z okayama $
package jp.co.daifuku.pcart.system.display.rftstateinquiry;

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
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.retrieval.listbox.pctretrftnoworkinquiry.LstPCTRetRftNoWorkInquiryParams;
import jp.co.daifuku.pcart.system.schedule.RFTStateInquirySCH;
import jp.co.daifuku.pcart.system.schedule.RFTStateInquirySCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * Pカート作業状態表示の画面処理を行います。
 *
 * @version $Revision: 3990 $, $Date:: 2009-04-07 15:26:00 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class RFTStateInquiryBusiness
        extends RFTStateInquiry
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_RFTState(LST_DETAILS) */
    private static final ListCellKey KEY_LST_DETAILS =
            new ListCellKey("LST_DETAILS", new StringCellColumn(), true, false);

    /** lst_RFTState(LST_RFT_NO) */
    private static final ListCellKey KEY_LST_RFT_NO =
            new ListCellKey("LST_RFT_NO", new StringCellColumn(), true, false);

    /** lst_RFTState(LST_TERMINAL_FLAG) */
    private static final ListCellKey KEY_LST_TERMINAL_FLAG =
            new ListCellKey("LST_TERMINAL_FLAG", new StringCellColumn(), true, false);

    /** lst_RFTState(LST_STATUS) */
    private static final ListCellKey KEY_LST_STATUS =
            new ListCellKey("LST_STATUS", new StringCellColumn(), true, false);

    /** lst_RFTState(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME =
            new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_RFTState(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO =
            new ListCellKey("LST_AREA_NO", new StringCellColumn(), true, false);

    /** lst_RFTState kyes */
    private static final ListCellKey[] LST_RFTSTATE_KEYS = {
            KEY_LST_DETAILS,
            KEY_LST_RFT_NO,
            KEY_LST_TERMINAL_FLAG,
            KEY_LST_STATUS,
            KEY_LST_USER_NAME,
            KEY_LST_AREA_NO,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** RadioButtonGroupModel rdo_CollectCondition */
    private RadioButtonGroup _grp_rdo_CollectCondition;

    /** ListCellModel lst_RFTState */
    private ListCellModel _lcm_lst_RFTState;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RFTStateInquiryBusiness()
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
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_DisplayOder_Area_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_DisplayOder_Area_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_DisplayOder_RFTNo_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_DisplayOder_RFTNo_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_RFTState_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_RFTState.getActiveCol();

        // choose process.
        if (_lcm_lst_RFTState.getColumnIndex(KEY_LST_DETAILS) == activeCol)
        {
            // process call.
            lst_Details_Click_Process();
        }
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

        // initialize rdo_CollectCondition.
        _grp_rdo_CollectCondition = new RadioButtonGroup(new RadioButton[] {
                rdo_DisplayOder_Area,
                rdo_DisplayOder_RFTNo
        }, locale);

        // initialize lst_RFTState.
        _lcm_lst_RFTState = new ListCellModel(lst_RFTState, LST_RFTSTATE_KEYS, locale);
        _lcm_lst_RFTState.setToolTipVisible(KEY_LST_DETAILS, true);
        _lcm_lst_RFTState.setToolTipVisible(KEY_LST_RFT_NO, true);
        _lcm_lst_RFTState.setToolTipVisible(KEY_LST_TERMINAL_FLAG, true);
        _lcm_lst_RFTState.setToolTipVisible(KEY_LST_STATUS, true);
        _lcm_lst_RFTState.setToolTipVisible(KEY_LST_USER_NAME, true);
        _lcm_lst_RFTState.setToolTipVisible(KEY_LST_AREA_NO, true);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_RFTState_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-P0158", KEY_LST_USER_NAME);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(rdo_DisplayOder_Area);

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
        RFTStateInquirySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RFTStateInquirySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RFTStateInquirySCHParams inparam = new RFTStateInquirySCHParams();

            // DFKLOOK:ここから修正
            // エリアNo.(表示順) 
            inparam.set(RFTStateInquirySCHParams.COLLECT_CONDITION, rdo_DisplayOder_Area.getValue());
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RFTState.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RFTState.getNewLine();
                line.setValue(KEY_LST_RFT_NO, outparam.get(RFTStateInquirySCHParams.RFT_NO));
                line.setValue(KEY_LST_TERMINAL_FLAG, outparam.get(RFTStateInquirySCHParams.TERMINAL_FLAG));
                line.setValue(KEY_LST_STATUS, outparam.get(RFTStateInquirySCHParams.STATUS));
                line.setValue(KEY_LST_USER_NAME, outparam.get(RFTStateInquirySCHParams.USER_NAME));
                line.setValue(KEY_LST_AREA_NO, outparam.get(RFTStateInquirySCHParams.AREA_NO));
                lst_RFTState_SetLineToolTip(line);
                _lcm_lst_RFTState.add(line);
            }

            // clear.
            rdo_DisplayOder_Area.setChecked(true);

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
    private void rdo_DisplayOder_Area_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RFTStateInquirySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RFTStateInquirySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RFTStateInquirySCHParams inparam = new RFTStateInquirySCHParams();

            // DFKLOOK:ここから修正
            // エリアNo.(表示順)
            inparam.set(RFTStateInquirySCHParams.COLLECT_CONDITION, rdo_DisplayOder_Area.getValue());
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RFTState.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RFTState.getNewLine();
                line.setValue(KEY_LST_RFT_NO, outparam.get(RFTStateInquirySCHParams.RFT_NO));
                line.setValue(KEY_LST_TERMINAL_FLAG, outparam.get(RFTStateInquirySCHParams.TERMINAL_FLAG));
                line.setValue(KEY_LST_STATUS, outparam.get(RFTStateInquirySCHParams.STATUS));
                line.setValue(KEY_LST_USER_NAME, outparam.get(RFTStateInquirySCHParams.USER_NAME));
                line.setValue(KEY_LST_AREA_NO, outparam.get(RFTStateInquirySCHParams.AREA_NO));
                lst_RFTState_SetLineToolTip(line);
                _lcm_lst_RFTState.add(line);
            }

            // clear.
            rdo_DisplayOder_Area.setChecked(true);

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
    private void rdo_DisplayOder_RFTNo_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RFTStateInquirySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RFTStateInquirySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RFTStateInquirySCHParams inparam = new RFTStateInquirySCHParams();

            // DFKLOOK:ここから修正
            // 号機No.(表示順)
            inparam.set(RFTStateInquirySCHParams.COLLECT_CONDITION, rdo_DisplayOder_RFTNo.getValue());
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RFTState.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RFTState.getNewLine();
                line.setValue(KEY_LST_RFT_NO, outparam.get(RFTStateInquirySCHParams.RFT_NO));
                line.setValue(KEY_LST_TERMINAL_FLAG, outparam.get(RFTStateInquirySCHParams.TERMINAL_FLAG));
                line.setValue(KEY_LST_STATUS, outparam.get(RFTStateInquirySCHParams.STATUS));
                line.setValue(KEY_LST_USER_NAME, outparam.get(RFTStateInquirySCHParams.USER_NAME));
                line.setValue(KEY_LST_AREA_NO, outparam.get(RFTStateInquirySCHParams.AREA_NO));
                lst_RFTState_SetLineToolTip(line);
                _lcm_lst_RFTState.add(line);
            }

            // clear.
            rdo_DisplayOder_RFTNo.setChecked(true);

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
    private void lst_Details_Click_Process()
            throws Exception
    {

        // DFKLOOK:ここから修正
        int row = lst_RFTState.getActiveRow();
        ListCellLine line = _lcm_lst_RFTState.get(row);

        // dialog parameters set.
        LstPCTRetRftNoWorkInquiryParams inparam = new LstPCTRetRftNoWorkInquiryParams();
        inparam.set(LstPCTRetRftNoWorkInquiryParams.PCART_RFT_NO, line.getValue(KEY_LST_RFT_NO));
        // DFKLOOK:ここまで修正

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_Display_Click");
        redirect("/pcart/retrieval/listbox/pctretrftnoworkinquiry/LstPCTRetRftNoWorkInquiry.do", forwardParam,
                "/Progress.do");
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ReDisplay_Click_Process()
            throws Exception
    {
        // input validation.
        rdo_DisplayOder_Area.validate(false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RFTStateInquirySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RFTStateInquirySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RFTStateInquirySCHParams inparam = new RFTStateInquirySCHParams();
            inparam.set(RFTStateInquirySCHParams.COLLECT_CONDITION, _grp_rdo_CollectCondition.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RFTState.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RFTState.getNewLine();
                line.setValue(KEY_LST_RFT_NO, outparam.get(RFTStateInquirySCHParams.RFT_NO));
                line.setValue(KEY_LST_TERMINAL_FLAG, outparam.get(RFTStateInquirySCHParams.TERMINAL_FLAG));
                line.setValue(KEY_LST_STATUS, outparam.get(RFTStateInquirySCHParams.STATUS));
                line.setValue(KEY_LST_USER_NAME, outparam.get(RFTStateInquirySCHParams.USER_NAME));
                line.setValue(KEY_LST_AREA_NO, outparam.get(RFTStateInquirySCHParams.AREA_NO));
                viewState.setObject(ViewStateKeys.VS_COLLECT_CONDITION, _grp_rdo_CollectCondition.getSelectedValue());
                lst_RFTState_SetLineToolTip(line);
                _lcm_lst_RFTState.add(line);
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
