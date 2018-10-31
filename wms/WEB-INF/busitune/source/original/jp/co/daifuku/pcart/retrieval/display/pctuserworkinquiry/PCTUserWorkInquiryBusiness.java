// $Id: PCTUserWorkInquiryBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.pcart.retrieval.display.pctuserworkinquiry;

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
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
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
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.retrieval.display.pctuserworkinquiry.PCTUserWorkInquiry;
import jp.co.daifuku.pcart.retrieval.schedule.PCTUserWorkInquirySCH;
import jp.co.daifuku.pcart.retrieval.schedule.PCTUserWorkInquirySCHParams;
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
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class PCTUserWorkInquiryBusiness
        extends PCTUserWorkInquiry
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_UserWorkInquiry(LST_LINE_NO) */
    private static final ListCellKey KEY_LST_LINE_NO = new ListCellKey("LST_LINE_NO", new NumberCellColumn(0), true, false);

    /** lst_UserWorkInquiry(LST_USER_ID) */
    private static final ListCellKey KEY_LST_USER_ID = new ListCellKey("LST_USER_ID", new StringCellColumn(), true, false);

    /** lst_UserWorkInquiry(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME = new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_UserWorkInquiry(LST_PRODUCTION_RATE) */
    private static final ListCellKey KEY_LST_PRODUCTION_RATE = new ListCellKey("LST_PRODUCTION_RATE", new NumberCellColumn(0), true, false);

    /** lst_UserWorkInquiry(LST_LOT_COUNT) */
    private static final ListCellKey KEY_LST_LOT_COUNT = new ListCellKey("LST_LOT_COUNT", new NumberCellColumn(0), true, false);

    /** lst_UserWorkInquiry(LST_ORDER_QTY) */
    private static final ListCellKey KEY_LST_ORDER_QTY = new ListCellKey("LST_ORDER_QTY", new NumberCellColumn(0), true, false);

    /** lst_UserWorkInquiry(LST_LINE_COUNT) */
    private static final ListCellKey KEY_LST_LINE_COUNT = new ListCellKey("LST_LINE_COUNT", new NumberCellColumn(0), true, false);

    /** lst_UserWorkInquiry(REAL_TIME) */
    private static final ListCellKey KEY_REAL_TIME = new ListCellKey("REAL_TIME", new StringCellColumn(), true, false);

    /** lst_UserWorkInquiry keys */
    private static final ListCellKey[] LST_USERWORKINQUIRY_KEYS = {
        KEY_LST_LINE_NO,
        KEY_LST_USER_ID,
        KEY_LST_USER_NAME,
        KEY_LST_PRODUCTION_RATE,
        KEY_LST_LOT_COUNT,
        KEY_LST_ORDER_QTY,
        KEY_LST_LINE_COUNT,
        KEY_REAL_TIME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_UserWorkInquiry */
    private ScrollListCellModel _lcm_lst_UserWorkInquiry;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTUserWorkInquiryBusiness()
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

        // initialize lst_UserWorkInquiry.
        _lcm_lst_UserWorkInquiry = new ScrollListCellModel(lst_UserWorkInquiry, LST_USERWORKINQUIRY_KEYS, locale);
        _lcm_lst_UserWorkInquiry.setToolTipVisible(KEY_LST_LINE_NO, false);
        _lcm_lst_UserWorkInquiry.setToolTipVisible(KEY_LST_USER_ID, false);
        _lcm_lst_UserWorkInquiry.setToolTipVisible(KEY_LST_USER_NAME, false);
        _lcm_lst_UserWorkInquiry.setToolTipVisible(KEY_LST_PRODUCTION_RATE, false);
        _lcm_lst_UserWorkInquiry.setToolTipVisible(KEY_LST_LOT_COUNT, false);
        _lcm_lst_UserWorkInquiry.setToolTipVisible(KEY_LST_ORDER_QTY, false);
        _lcm_lst_UserWorkInquiry.setToolTipVisible(KEY_LST_LINE_COUNT, false);
        _lcm_lst_UserWorkInquiry.setToolTipVisible(KEY_REAL_TIME, false);
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
    private void lst_UserWorkInquiry_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
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
        PCTUserWorkInquirySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTUserWorkInquirySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTUserWorkInquirySCHParams inparam = new PCTUserWorkInquirySCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_UserWorkInquiry.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_UserWorkInquiry.getNewLine();
                line.setValue(KEY_LST_LINE_NO, outparam.get(PCTUserWorkInquirySCHParams.LINE_NO));
                line.setValue(KEY_LST_USER_ID, outparam.get(PCTUserWorkInquirySCHParams.USER_ID));
                line.setValue(KEY_LST_USER_NAME, outparam.get(PCTUserWorkInquirySCHParams.USER_NAME));
                line.setValue(KEY_LST_PRODUCTION_RATE, outparam.get(PCTUserWorkInquirySCHParams.PRODUCTION_RATE));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTUserWorkInquirySCHParams.LOT_COUNT));
                line.setValue(KEY_LST_ORDER_QTY, outparam.get(PCTUserWorkInquirySCHParams.ORDER_QTY));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(PCTUserWorkInquirySCHParams.LINE_COUNT));
                line.setValue(KEY_REAL_TIME, outparam.get(PCTUserWorkInquirySCHParams.REAL_TIME));
                lst_UserWorkInquiry_SetLineToolTip(line);
                _lcm_lst_UserWorkInquiry.add(line);
            }

            // clear.
            btn_PrevPage.setEnabled(false);
            btn_NextPage.setEnabled(false);
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
    private void btn_Display_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTUserWorkInquirySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTUserWorkInquirySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTUserWorkInquirySCHParams inparam = new PCTUserWorkInquirySCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_UserWorkInquiry.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_UserWorkInquiry.getNewLine();
                line.setValue(KEY_LST_LINE_NO, outparam.get(PCTUserWorkInquirySCHParams.LINE_NO));
                line.setValue(KEY_LST_USER_ID, outparam.get(PCTUserWorkInquirySCHParams.USER_ID));
                line.setValue(KEY_LST_USER_NAME, outparam.get(PCTUserWorkInquirySCHParams.USER_NAME));
                line.setValue(KEY_LST_PRODUCTION_RATE, outparam.get(PCTUserWorkInquirySCHParams.PRODUCTION_RATE));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTUserWorkInquirySCHParams.LOT_COUNT));
                line.setValue(KEY_LST_ORDER_QTY, outparam.get(PCTUserWorkInquirySCHParams.ORDER_QTY));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(PCTUserWorkInquirySCHParams.LINE_COUNT));
                line.setValue(KEY_REAL_TIME, outparam.get(PCTUserWorkInquirySCHParams.REAL_TIME));
                lst_UserWorkInquiry_SetLineToolTip(line);
                _lcm_lst_UserWorkInquiry.add(line);
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
