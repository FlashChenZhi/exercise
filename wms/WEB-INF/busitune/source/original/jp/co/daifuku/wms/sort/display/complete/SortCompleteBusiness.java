// $Id: SortCompleteBusiness.java 7444 2010-03-08 04:19:13Z okayama $
package jp.co.daifuku.wms.sort.display.complete;

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
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.util.Formatter;
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
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.sort.dasch.SortCompleteDASCH;
import jp.co.daifuku.wms.sort.dasch.SortCompleteDASCHParams;
import jp.co.daifuku.wms.sort.display.complete.SortComplete;
import jp.co.daifuku.wms.sort.schedule.SortCompleteSCH;
import jp.co.daifuku.wms.sort.schedule.SortCompleteSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7444 $, $Date:: 2010-03-08 13:19:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class SortCompleteBusiness
        extends SortComplete
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_SortWorkList(HIDDEN_HARD_WERE_TYPE) */
    private static final ListCellKey KEY_HIDDEN_HARD_WERE_TYPE = new ListCellKey("HIDDEN_HARD_WERE_TYPE", new StringCellColumn(), false, false);

    /** lst_SortWorkList(HIDDEN_USER_NAME) */
    private static final ListCellKey KEY_HIDDEN_USER_NAME = new ListCellKey("HIDDEN_USER_NAME", new StringCellColumn(), false, false);

    /** lst_SortWorkList(HIDDEN_TERMINAL_NO) */
    private static final ListCellKey KEY_HIDDEN_TERMINAL_NO = new ListCellKey("HIDDEN_TERMINAL_NO", new StringCellColumn(), false, false);

    /** lst_SortWorkList(LST_PLAN_DAY) */
    private static final ListCellKey KEY_LST_PLAN_DAY = new ListCellKey("LST_PLAN_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_SortWorkList(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO = new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_SortWorkList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_SortWorkList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_SortWorkList(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_SortWorkList(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_SortWorkList(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY = new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_SortWorkList(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY = new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_SortWorkList(LST_JAN) */
    private static final ListCellKey KEY_LST_JAN = new ListCellKey("LST_JAN", new StringCellColumn(), true, false);

    /** lst_SortWorkList(LST_ITF) */
    private static final ListCellKey KEY_LST_ITF = new ListCellKey("LST_ITF", new StringCellColumn(), true, false);

    /** lst_SortWorkList(LST_SORT_PLACE) */
    private static final ListCellKey KEY_LST_SORT_PLACE = new ListCellKey("LST_SORT_PLACE", new StringCellColumn(), true, false);

    /** lst_SortWorkList(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE = new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_SortWorkList(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME = new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_SortWorkList(LST_STATUS_FLAG) */
    private static final ListCellKey KEY_LST_STATUS_FLAG = new ListCellKey("LST_STATUS_FLAG", new StringCellColumn(), true, false);

    /** lst_SortWorkList keys */
    private static final ListCellKey[] LST_SORTWORKLIST_KEYS = {
        KEY_HIDDEN_HARD_WERE_TYPE,
        KEY_HIDDEN_USER_NAME,
        KEY_HIDDEN_TERMINAL_NO,
        KEY_LST_PLAN_DAY,
        KEY_LST_BATCH_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT_NO,
        KEY_LST_ENTERING_QTY,
        KEY_LST_PLAN_CASE_QTY,
        KEY_LST_JAN,
        KEY_LST_SORT_PLACE,
        KEY_LST_CUSTOMER_CODE,
        KEY_LST_STATUS_FLAG,
        KEY_LST_ITEM_NAME,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_ITF,
        KEY_LST_CUSTOMER_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_SortWorkList */
    private ListCellModel _lcm_lst_SortWorkList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public SortCompleteBusiness()
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
    public void btn_Decision_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Decision_Click_Process();
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
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_U_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_U_Prev(ActionEvent e)
            throws Exception
    {
        _pager.previous();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_U_Next(ActionEvent e)
            throws Exception
    {
        _pager.next();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_U_Last(ActionEvent e)
            throws Exception
    {
        _pager.last();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_D_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_D_Prev(ActionEvent e)
            throws Exception
    {
        _pager.previous();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_D_Next(ActionEvent e)
            throws Exception
    {
        _pager.next();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_D_Last(ActionEvent e)
            throws Exception
    {
        _pager.last();
        pager_SetPage();
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

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale);

        // initialize lst_SortWorkList.
        _lcm_lst_SortWorkList = new ListCellModel(lst_SortWorkList, LST_SORTWORKLIST_KEYS, locale);
        _lcm_lst_SortWorkList.setToolTipVisible(KEY_LST_PLAN_DAY, true);
        _lcm_lst_SortWorkList.setToolTipVisible(KEY_LST_BATCH_NO, true);
        _lcm_lst_SortWorkList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_SortWorkList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_SortWorkList.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_SortWorkList.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_SortWorkList.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_SortWorkList.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_SortWorkList.setToolTipVisible(KEY_LST_JAN, true);
        _lcm_lst_SortWorkList.setToolTipVisible(KEY_LST_ITF, true);
        _lcm_lst_SortWorkList.setToolTipVisible(KEY_LST_SORT_PLACE, true);
        _lcm_lst_SortWorkList.setToolTipVisible(KEY_LST_CUSTOMER_CODE, true);
        _lcm_lst_SortWorkList.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);
        _lcm_lst_SortWorkList.setToolTipVisible(KEY_LST_STATUS_FLAG, true);
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
        // dispose DASCH.
        disposeDasch();
    }

    /**
     *
     * @throws Exception
     */
    private void disposeDasch()
            throws Exception
    {
        // disposing DASCH.
        DataAccessSCH dasch = (DataAccessSCH)session.getAttribute(_KEY_DASCH);
        if (dasch != null)
        {
            session.removeAttribute(_KEY_DASCH);
            dasch.close();
            DBUtil.close(dasch.getConnection());
        }
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_SortWorkList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("", KEY_HIDDEN_HARD_WERE_TYPE);
        line.addToolTip("", KEY_HIDDEN_USER_NAME);
        line.addToolTip("", KEY_HIDDEN_TERMINAL_NO);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-W0114", KEY_LST_CUSTOMER_CODE);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME);
        line.addToolTip("LBL-W0086", KEY_LST_STATUS_FLAG);
    }

    /**
     *
     * @throws Exception
     */
    private void pager_SetPage()
            throws Exception
    {
        // get event source.
        String eventSource = viewState.getString(_KEY_PAGERSOURCE);
        if (eventSource == null)
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_Display_Click"))
        {
            // process call.
            btn_Display_Click_SetList();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_PlanDate);
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
        SortCompleteDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new SortCompleteDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            SortCompleteDASCHParams inparam = new SortCompleteDASCHParams();
            inparam.set(SortCompleteDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(SortCompleteDASCHParams.PLAN_DAY, txt_PlanDate.getValue());
            inparam.set(SortCompleteDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(SortCompleteDASCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(SortCompleteDASCHParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
            inparam.set(SortCompleteDASCHParams.STATUS_FLAG, SystemDefine.STATUS_FLAG_UNSTART);

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_SortWorkList.clear();

            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }
            else if (count > _pager.getMax())
            {
                message.setMsgResourceKey("6001023\t" + Formatter.getNumFormat(count)
                        + "\t" + Formatter.getNumFormat(_pager.getMax()));
            }
            else
            {
                message.setMsgResourceKey("6001022\t" + Formatter.getNumFormat(count));
            }

            // DASCH call.
            dasch.search(inparam);

            // save session.
            session.setAttribute(_KEY_DASCH, dasch);
            isSuccess = true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_SortWorkList.clear();
        }
        finally
        {
            if (isSuccess)
            {
                // set list.
                btn_Display_Click_SetList();
            }
            else
            {
                if (dasch != null)
                {
                    dasch.close();
                }
                DBUtil.close(conn);
            }
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_SetList()
            throws Exception
    {
        SortCompleteDASCH dasch = null;
        try
        {
            // get session.
            dasch = (SortCompleteDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_SortWorkList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_SortWorkList.getNewLine();
                line.setValue(KEY_HIDDEN_HARD_WERE_TYPE, outparam.get(SortCompleteDASCHParams.HARD_WARE_TYPE));
                line.setValue(KEY_HIDDEN_USER_NAME, outparam.get(SortCompleteDASCHParams.USER_NAME));
                line.setValue(KEY_HIDDEN_TERMINAL_NO, outparam.get(SortCompleteDASCHParams.TERMINAL_NO));
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(SortCompleteDASCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(SortCompleteDASCHParams.BATCH_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(SortCompleteDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(SortCompleteDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(SortCompleteDASCHParams.LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(SortCompleteDASCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(SortCompleteDASCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(SortCompleteDASCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_JAN, outparam.get(SortCompleteDASCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(SortCompleteDASCHParams.ITF));
                line.setValue(KEY_LST_SORT_PLACE, outparam.get(SortCompleteDASCHParams.SORT_PLACE));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(SortCompleteDASCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(SortCompleteDASCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_STATUS_FLAG, outparam.get(SortCompleteDASCHParams.STATUS_FLAG));
                line.setValue(KEY_HIDDEN_HARD_WERE_TYPE, outparam.get(SortCompleteDASCHParams.HARD_WARE_TYPE_NAME));
                line.setValue(KEY_LST_STATUS_FLAG, outparam.get(SortCompleteDASCHParams.STATUS_FLAG_NAME));
                lst_SortWorkList_SetLineToolTip(line);
                _lcm_lst_SortWorkList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_SortWorkList.clear();
            disposeDasch();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Decision_Click_Process()
            throws Exception
    {
        // input validation.
        txt_PlanDate.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_ItemCode.validate(this, false);
        txt_CustomerCode.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        SortCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new SortCompleteSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            SortCompleteSCHParams inparam = new SortCompleteSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(SortCompleteSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(SortCompleteSCHParams.PLAN_DAY, txt_PlanDate.getValue());
            inparam.set(SortCompleteSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(SortCompleteSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(SortCompleteSCHParams.CUSTOMER_CODE, txt_CustomerCode.getValue());

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
            part11List.add(txt_PlanDate.getStringValue(), "");
            part11List.add(txt_BatchNo.getStringValue(), "");
            part11List.add(txt_ItemCode.getStringValue(), "");
            part11List.add(txt_CustomerCode.getStringValue(), "");
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
        txt_PlanDate.setValue(null);
        txt_BatchNo.setValue(null);
        txt_ItemCode.setValue(null);
        txt_CustomerCode.setValue(null);
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
