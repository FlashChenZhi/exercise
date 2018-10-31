// $Id: RetrievalOrderAllocClearBusiness.java 7446 2010-03-08 04:20:52Z okayama $
package jp.co.daifuku.wms.retrieval.display.allocclear;

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
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
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
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.retrieval.dasch.RetrievalOrderAllocClearDASCH;
import jp.co.daifuku.wms.retrieval.dasch.RetrievalOrderAllocClearDASCHParams;
import jp.co.daifuku.wms.retrieval.display.allocclear.RetrievalOrderAllocClear;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOrderAllocClearSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOrderAllocClearSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7446 $, $Date:: 2010-03-08 13:20:52 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class RetrievalOrderAllocClearBusiness
        extends RetrievalOrderAllocClear
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

    /** lst_RetrievalAllocateWorkList(LST_BATCH) */
    private static final ListCellKey KEY_LST_BATCH = new ListCellKey("LST_BATCH", new StringCellColumn(), true, false);

    /** lst_RetrievalAllocateWorkList(LST_ORDER) */
    private static final ListCellKey KEY_LST_ORDER = new ListCellKey("LST_ORDER", new StringCellColumn(), true, false);

    /** lst_RetrievalAllocateWorkList(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE = new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_RetrievalAllocateWorkList(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME = new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_RetrievalAllocateWorkList keys */
    private static final ListCellKey[] LST_RETRIEVALALLOCATEWORKLIST_KEYS = {
        KEY_LST_BATCH,
        KEY_LST_ORDER,
        KEY_LST_CUSTOMER_CODE,
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

    /** ListCellModel lst_RetrievalAllocateWorkList */
    private ListCellModel _lcm_lst_RetrievalAllocateWorkList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RetrievalOrderAllocClearBusiness()
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
    public void btn_Set_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Set_Click_Process();
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

        // initialize lst_RetrievalAllocateWorkList.
        _lcm_lst_RetrievalAllocateWorkList = new ListCellModel(lst_RetrievalAllocateWorkList, LST_RETRIEVALALLOCATEWORKLIST_KEYS, locale);
        _lcm_lst_RetrievalAllocateWorkList.setToolTipVisible(KEY_LST_BATCH, true);
        _lcm_lst_RetrievalAllocateWorkList.setToolTipVisible(KEY_LST_ORDER, true);
        _lcm_lst_RetrievalAllocateWorkList.setToolTipVisible(KEY_LST_CUSTOMER_CODE, true);
        _lcm_lst_RetrievalAllocateWorkList.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);
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
    private void lst_RetrievalAllocateWorkList_SetLineToolTip(ListCellLine line)
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
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        _pager.clear();
        _pager.clear();
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
        RetrievalOrderAllocClearDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new RetrievalOrderAllocClearDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            RetrievalOrderAllocClearDASCHParams inparam = new RetrievalOrderAllocClearDASCHParams();
            inparam.set(RetrievalOrderAllocClearDASCHParams.PLANDAY, txt_PlanDate.getValue());
            inparam.set(RetrievalOrderAllocClearDASCHParams.BATCHNO, txt_BatchNo.getValue());
            inparam.set(RetrievalOrderAllocClearDASCHParams.FROMORDER, txt_OrderNoFrom.getValue());
            inparam.set(RetrievalOrderAllocClearDASCHParams.TOORDER, txt_OrderNoTo.getValue());
            inparam.set(RetrievalOrderAllocClearDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_RetrievalAllocateWorkList.clear();

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
            _lcm_lst_RetrievalAllocateWorkList.clear();
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
        RetrievalOrderAllocClearDASCH dasch = null;
        try
        {
            // get session.
            dasch = (RetrievalOrderAllocClearDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_RetrievalAllocateWorkList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RetrievalAllocateWorkList.getNewLine();
                line.setValue(KEY_LST_BATCH, outparam.get(RetrievalOrderAllocClearDASCHParams.BATCH));
                line.setValue(KEY_LST_ORDER, outparam.get(RetrievalOrderAllocClearDASCHParams.ORDER));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(RetrievalOrderAllocClearDASCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(RetrievalOrderAllocClearDASCHParams.CUSTOMER_NAME));
                lst_RetrievalAllocateWorkList_SetLineToolTip(line);
                _lcm_lst_RetrievalAllocateWorkList.add(line);
            }

            // set focus.
            setFocus(txt_PlanDate);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_RetrievalAllocateWorkList.clear();
            disposeDasch();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process()
            throws Exception
    {
        // input validation.
        txt_PlanDate.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_OrderNoFrom.validate(this, false);
        txt_OrderNoTo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalOrderAllocClearSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalOrderAllocClearSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RetrievalOrderAllocClearSCHParams inparam = new RetrievalOrderAllocClearSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(RetrievalOrderAllocClearSCHParams.PLAN_DATE, txt_PlanDate.getValue());
            inparam.set(RetrievalOrderAllocClearSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(RetrievalOrderAllocClearSCHParams.ORDER_NO_FROM, txt_OrderNoFrom.getValue());
            inparam.set(RetrievalOrderAllocClearSCHParams.ORDER_NO_TO, txt_OrderNoTo.getValue());
            inparam.set(RetrievalOrderAllocClearSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

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
            part11List.add(txt_OrderNoFrom.getStringValue(), "");
            part11List.add(txt_OrderNoTo.getStringValue(), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // clear.
            _lcm_lst_RetrievalAllocateWorkList.clear();

            // set focus.
            setFocus(txt_PlanDate);
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
        txt_OrderNoFrom.setValue(null);
        txt_OrderNoTo.setValue(null);
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
