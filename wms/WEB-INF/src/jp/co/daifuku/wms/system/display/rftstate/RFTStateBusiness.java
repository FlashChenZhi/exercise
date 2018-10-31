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
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.system.schedule.RFTStateSCH;
import jp.co.daifuku.wms.system.schedule.RFTStateSCHParams;

/**
 * RFT作業状態設定の画面処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:59:03 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class RFTStateBusiness
        extends RFTState
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_RFTState(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT =
            new ListCellKey("LST_SELECT", new StringCellColumn(), true, false);

    /** lst_RFTState(LST_RFT_NO) */
    private static final ListCellKey KEY_LST_RFT_NO =
            new ListCellKey("LST_RFT_NO", new StringCellColumn(), true, false);

    /** lst_RFTState(LST_TERMINAL_TYPE) */
    private static final ListCellKey KEY_LST_TERMINAL_TYPE =
            new ListCellKey("LST_TERMINAL_TYPE", new StringCellColumn(), true, false);

    /** lst_RFTState(LST_STATUS_FLAG) */
    private static final ListCellKey KEY_LST_STATUS_FLAG =
            new ListCellKey("LST_STATUS_FLAG", new StringCellColumn(), true, false);

    /** lst_RFTState(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME =
            new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_RFTState kyes */
    private static final ListCellKey[] LST_RFTSTATE_KEYS = {
            KEY_LST_SELECT,
            KEY_LST_RFT_NO,
            KEY_LST_TERMINAL_TYPE,
            KEY_LST_STATUS_FLAG,
            KEY_LST_USER_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_RFTState */
    private ListCellModel _lcm_lst_RFTState;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RFTStateBusiness()
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
    }

    // DFKLOOK:ここから修正
    /**
    *
    * @param e ActionEvent
    * @throws Exception
    */
   public void page_DlgBack(ActionEvent e)
           throws Exception
   {
       btn_ReDisplay_Click_Process();
   }
   // DFKLOOK:ここまで修正
   
    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_SettingName_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ToMenu_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_RFTState_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから修正
        lst_RFTState_Click_Process();
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_RFTState_EnterKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_RFTState_TabKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_RFTState_InputComplete(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_RFTState_ColumClick(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_RFTState_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_RFTState_Change(ActionEvent e)
            throws Exception
    {
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
    public void btn_ReDisplay_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void tab_Maintenance_Click(ActionEvent e)
            throws Exception
    {
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

        // initialize lst_RFTState.
        _lcm_lst_RFTState = new ListCellModel(lst_RFTState, LST_RFTSTATE_KEYS, locale);
        // DFKLOOK
        _lcm_lst_RFTState.setToolTipVisible(KEY_LST_SELECT, true);
        _lcm_lst_RFTState.setToolTipVisible(KEY_LST_RFT_NO, true);
        _lcm_lst_RFTState.setToolTipVisible(KEY_LST_TERMINAL_TYPE, true);
        _lcm_lst_RFTState.setToolTipVisible(KEY_LST_STATUS_FLAG, true);
        _lcm_lst_RFTState.setToolTipVisible(KEY_LST_USER_NAME, true);
        // DFKLOOK
        
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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_RFTState_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        // DFKLOOK
        line.addToolTip("LBL-W0033", KEY_LST_USER_NAME);
        // DFKLOOK
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

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RFTState.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RFTState.getNewLine();
                line.setValue(KEY_LST_SELECT, outparam.get(RFTStateSCHParams.SELECT));
                line.setValue(KEY_LST_RFT_NO, outparam.get(RFTStateSCHParams.RFT_NO));
                line.setValue(KEY_LST_TERMINAL_TYPE, outparam.get(RFTStateSCHParams.TERMINAL_TYPE));
                line.setValue(KEY_LST_STATUS_FLAG, outparam.get(RFTStateSCHParams.STATUS_FLAG));
                line.setValue(KEY_LST_USER_NAME, outparam.get(RFTStateSCHParams.USER_NAME));
                line.setValue(KEY_LST_TERMINAL_TYPE, outparam.get(RFTStateSCHParams.TERMINAL_TYPE_NAME));
                line.setValue(KEY_LST_STATUS_FLAG, outparam.get(RFTStateSCHParams.STATUS_FLAG_NAME));
                lst_RFTState_SetLineToolTip(line);
                _lcm_lst_RFTState.add(line);
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

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RFTState.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RFTState.getNewLine();
                line.setValue(KEY_LST_SELECT, outparam.get(RFTStateSCHParams.SELECT));
                line.setValue(KEY_LST_RFT_NO, outparam.get(RFTStateSCHParams.RFT_NO));
                line.setValue(KEY_LST_TERMINAL_TYPE, outparam.get(RFTStateSCHParams.TERMINAL_TYPE_NAME));
                line.setValue(KEY_LST_STATUS_FLAG, outparam.get(RFTStateSCHParams.STATUS_FLAG_NAME));
                line.setValue(KEY_LST_USER_NAME, outparam.get(RFTStateSCHParams.USER_NAME));
                lst_RFTState_SetLineToolTip(line);
                _lcm_lst_RFTState.add(line);
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

    // DFKLOOK ここから修正
    /**
     *
     * @throws Exception
     */
    private void lst_RFTState_Click_Process()
            throws Exception
    {

        // get active row.
        int row = lst_RFTState.getActiveRow();
        lst_RFTState.setCurrentRow(row);
        ListCellLine line = _lcm_lst_RFTState.get(row);

        // dialog parameters set.
        RFTState2Params inparam = new RFTState2Params();
        inparam.set(RFTState2Params.RFT_NO, line.getValue(KEY_LST_RFT_NO));

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "lst_RFTState_ColumClick");
        redirect("/system/rftstate/RFTState2.do", forwardParam, "/Progress.do");
    }

    // DFKLOOK:ここまで修正
    
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
