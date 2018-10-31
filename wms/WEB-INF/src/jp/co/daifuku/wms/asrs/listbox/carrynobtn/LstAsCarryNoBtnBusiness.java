// $Id: LstAsCarryNoBtnBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.asrs.listbox.carrynobtn;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.Constants;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.dasch.LstAsCarryNoBtnDASCH;
import jp.co.daifuku.wms.asrs.dasch.LstAsCarryNoBtnDASCHParams;

/**
 * 残作業一覧の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class LstAsCarryNoBtnBusiness
        extends LstAsCarryNoBtn
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_DASCH_LIST = "_KEY_DASCH_LIST";

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_RestWorkNoButtonList(HDN_SOURCE_STATION_NAME) */
    private static final ListCellKey KEY_HDN_SOURCE_STATION_NAME = new ListCellKey("HDN_SOURCE_STATION_NAME", new StringCellColumn(), false, false);

    /** lst_RestWorkNoButtonList(HDN_DEST_STATION_NAME) */
    private static final ListCellKey KEY_HDN_DEST_STATION_NAME = new ListCellKey("HDN_DEST_STATION_NAME", new StringCellColumn(), false, false);

    /** lst_RestWorkNoButtonList(HDN_WORK_TYPE) */
    private static final ListCellKey KEY_HDN_WORK_TYPE = new ListCellKey("HDN_WORK_TYPE", new StringCellColumn(), false, false);

    /** lst_RestWorkNoButtonList(HDN_RETRIEVAL_DETAIL) */
    private static final ListCellKey KEY_HDN_RETRIEVAL_DETAIL = new ListCellKey("HDN_RETRIEVAL_DETAIL", new StringCellColumn(), false, false);

    /** lst_RestWorkNoButtonList(HDN_SOURCE_STATION_NO) */
    private static final ListCellKey KEY_HDN_SOURCE_STATION_NO = new ListCellKey("HDN_SOURCE_STATION_NO", new StringCellColumn(), false, false);

    /** lst_RestWorkNoButtonList(HDN_DEST_STATION_NO) */
    private static final ListCellKey KEY_HDN_DEST_STATION_NO = new ListCellKey("HDN_DEST_STATION_NO", new StringCellColumn(), false, false);

    /** lst_RestWorkNoButtonList(HDN_AREA_NO) */
    private static final ListCellKey KEY_HDN_AREA_NO = new ListCellKey("HDN_AREA_NO", new StringCellColumn(), false, false);

    /** lst_RestWorkNoButtonList(HIDDEN_SCHEDULE_NO) */
    private static final ListCellKey KEY_HIDDEN_SCHEDULE_NO = new ListCellKey("HIDDEN_SCHEDULE_NO", new StringCellColumn(), false, false);

    /** lst_RestWorkNoButtonList(HIDDEN_LOCATION_NO) */
    private static final ListCellKey KEY_HIDDEN_LOCATION_NO = new ListCellKey("HIDDEN_LOCATION_NO", new StringCellColumn(), false, false);

    /** lst_RestWorkNoButtonList(HIDDEN_TO_LOCATION_NO) */
    private static final ListCellKey KEY_HIDDEN_TO_LOCATION_NO = new ListCellKey("HIDDEN_TO_LOCATION_NO", new StringCellColumn(), false, false);

    /** lst_RestWorkNoButtonList(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_RestWorkNoButtonList(LST_PRIORITY) */
    private static final ListCellKey KEY_LST_PRIORITY = new ListCellKey("LST_PRIORITY", new StringCellColumn(), true, false);

    /** lst_RestWorkNoButtonList(LST_CARRY_KEY) */
    private static final ListCellKey KEY_LST_CARRY_KEY = new ListCellKey("LST_CARRY_KEY", new StringCellColumn(), true, false);

    /** lst_RestWorkNoButtonList(LST_STATION_NO) */
    private static final ListCellKey KEY_LST_STATION_NO = new ListCellKey("LST_STATION_NO", new StringCellColumn(), true, false);

    /** lst_RestWorkNoButtonList(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO = new ListCellKey("LST_LOCATION_NO", new StringCellColumn(), true, false);

    /** lst_RestWorkNoButtonList(LST_SOURCE_DEST) */
    private static final ListCellKey KEY_LST_SOURCE_DEST = new ListCellKey("LST_SOURCE_DEST", new StringCellColumn(), true, false);

    /** lst_RestWorkNoButtonList(LST_CARRY_FLAG) */
    private static final ListCellKey KEY_LST_CARRY_FLAG = new ListCellKey("LST_CARRY_FLAG", new StringCellColumn(), true, false);

    /** lst_RestWorkNoButtonList(LST_CMD_STATUS) */
    private static final ListCellKey KEY_LST_CMD_STATUS = new ListCellKey("LST_CMD_STATUS", new StringCellColumn(), true, false);

    /** lst_RestWorkNoButtonList(LST_WORK_NO) */
    private static final ListCellKey KEY_LST_WORK_NO = new ListCellKey("LST_WORK_NO", new StringCellColumn(), true, false);

    /** lst_RestWorkNoButtonList keys */
    private static final ListCellKey[] LST_RESTWORKNOBUTTONLIST_KEYS = {
        KEY_HDN_SOURCE_STATION_NAME,
        KEY_HDN_DEST_STATION_NAME,
        KEY_HDN_WORK_TYPE,
        KEY_HDN_RETRIEVAL_DETAIL,
        KEY_HDN_SOURCE_STATION_NO,
        KEY_HDN_DEST_STATION_NO,
        KEY_HDN_AREA_NO,
        KEY_HIDDEN_SCHEDULE_NO,
        KEY_HIDDEN_LOCATION_NO,
        KEY_HIDDEN_TO_LOCATION_NO,
        KEY_LST_NO,
        KEY_LST_PRIORITY,
        KEY_LST_CARRY_KEY,
        KEY_LST_STATION_NO,
        KEY_LST_LOCATION_NO,
        KEY_LST_SOURCE_DEST,
        KEY_LST_CARRY_FLAG,
        KEY_LST_CMD_STATUS,
        KEY_LST_WORK_NO,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_RestWorkNoButtonList */
    private ListCellModel _lcm_lst_RestWorkNoButtonList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstAsCarryNoBtnBusiness()
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
    public void btn_Close_D_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_D_Click_Process();
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
        _pager = new PagerModel(new Pager[]{pgr_U}, locale);

        // initialize lst_RestWorkNoButtonList.
        _lcm_lst_RestWorkNoButtonList = new ListCellModel(lst_RestWorkNoButtonList, LST_RESTWORKNOBUTTONLIST_KEYS, locale);
        _lcm_lst_RestWorkNoButtonList.setToolTipVisible(KEY_LST_NO, true);
        _lcm_lst_RestWorkNoButtonList.setToolTipVisible(KEY_LST_PRIORITY, true);
        _lcm_lst_RestWorkNoButtonList.setToolTipVisible(KEY_LST_CARRY_KEY, true);
        _lcm_lst_RestWorkNoButtonList.setToolTipVisible(KEY_LST_STATION_NO, true);
        _lcm_lst_RestWorkNoButtonList.setToolTipVisible(KEY_LST_LOCATION_NO, true);
        _lcm_lst_RestWorkNoButtonList.setToolTipVisible(KEY_LST_SOURCE_DEST, true);
        _lcm_lst_RestWorkNoButtonList.setToolTipVisible(KEY_LST_CARRY_FLAG, true);
        _lcm_lst_RestWorkNoButtonList.setToolTipVisible(KEY_LST_CMD_STATUS, true);
        _lcm_lst_RestWorkNoButtonList.setToolTipVisible(KEY_LST_WORK_NO, true);

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
        DataAccessSCH dasch = (DataAccessSCH)session.getAttribute(_KEY_DASCH_LIST);
        if (dasch != null)
        {
            session.removeAttribute(_KEY_DASCH_LIST);
            dasch.close();
            DBUtil.close(dasch.getConnection());
        }
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_RestWorkNoButtonList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        //DFKLOOK:ここから修正
        line.addToolTip("LBL-W1393", KEY_LST_CARRY_KEY);
        line.addToolTip("LBL-W0132", KEY_LST_CMD_STATUS);
        line.addToolTip("LBL-W0362", KEY_LST_STATION_NO);
        line.addToolTip("LBL-W0044", KEY_HIDDEN_TO_LOCATION_NO);
        line.addToolTip("LBL-W0276", KEY_HDN_RETRIEVAL_DETAIL);
        line.addToolTip("LBL-W0079", KEY_HDN_WORK_TYPE);
        line.addToolTip("LBL-W1392", KEY_HIDDEN_SCHEDULE_NO);
        //DFKLOOK:ここまで修正
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
        if (eventSource.equals("page_Load"))
        {
            // process call.
            page_Load_SetList();
        }
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
        LstAsCarryNoBtnDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "page_Load");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstAsCarryNoBtnDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstAsCarryNoBtnDASCHParams inparam = new LstAsCarryNoBtnDASCHParams();

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_RestWorkNoButtonList.clear();

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
            session.setAttribute(_KEY_DASCH_LIST, dasch);
            isSuccess = true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_RestWorkNoButtonList.clear();
        }
        finally
        {
            if (isSuccess)
            {
                // set list.
                page_Load_SetList();
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
    private void page_Load_SetList()
            throws Exception
    {
        LstAsCarryNoBtnDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstAsCarryNoBtnDASCH)session.getAttribute(_KEY_DASCH_LIST);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_RestWorkNoButtonList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RestWorkNoButtonList.getNewLine();
                line.setValue(KEY_LST_NO, outparam.get(LstAsCarryNoBtnDASCHParams.NO));
                line.setValue(KEY_LST_PRIORITY, outparam.get(LstAsCarryNoBtnDASCHParams.PRIORITY_NAME));
                line.setValue(KEY_LST_CARRY_KEY, outparam.get(LstAsCarryNoBtnDASCHParams.CARRY_KEY));
                line.setValue(KEY_LST_STATION_NO, outparam.get(LstAsCarryNoBtnDASCHParams.STATION_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(LstAsCarryNoBtnDASCHParams.LOCATION_NO));
                line.setValue(KEY_LST_SOURCE_DEST, outparam.get(LstAsCarryNoBtnDASCHParams.SOURCE_DEST));
                line.setValue(KEY_LST_CARRY_FLAG, outparam.get(LstAsCarryNoBtnDASCHParams.CARRY_FLAG_NAME));
                line.setValue(KEY_LST_CMD_STATUS, outparam.get(LstAsCarryNoBtnDASCHParams.CMD_STATUS_NAME));
                line.setValue(KEY_LST_WORK_NO, outparam.get(LstAsCarryNoBtnDASCHParams.WORK_NO));
                line.setValue(KEY_HDN_SOURCE_STATION_NAME, outparam.get(LstAsCarryNoBtnDASCHParams.SOURCE_STATION_NAME));
                line.setValue(KEY_HDN_DEST_STATION_NAME, outparam.get(LstAsCarryNoBtnDASCHParams.DEST_STATION_NAME));
                line.setValue(KEY_HDN_WORK_TYPE, outparam.get(LstAsCarryNoBtnDASCHParams.WORK_TYPE_NAME));
                line.setValue(KEY_HDN_RETRIEVAL_DETAIL, outparam.get(LstAsCarryNoBtnDASCHParams.RETRIEVAL_DETAIL_NAME));
                line.setValue(KEY_HDN_SOURCE_STATION_NO, outparam.get(LstAsCarryNoBtnDASCHParams.SOURCE_STATION_NO));
                line.setValue(KEY_HDN_DEST_STATION_NO, outparam.get(LstAsCarryNoBtnDASCHParams.DEST_STATION_NO));
                line.setValue(KEY_HDN_AREA_NO, outparam.get(LstAsCarryNoBtnDASCHParams.AREA_NO));
                line.setValue(KEY_HIDDEN_SCHEDULE_NO, outparam.get(LstAsCarryNoBtnDASCHParams.SCHEDULE_NO));
                line.setValue(KEY_HIDDEN_LOCATION_NO, outparam.get(LstAsCarryNoBtnDASCHParams.LOCATION_NO));
                line.setValue(KEY_HIDDEN_TO_LOCATION_NO, outparam.get(LstAsCarryNoBtnDASCHParams.TO_LOCATION_NO));
                lst_RestWorkNoButtonList_SetLineToolTip(line);
                _lcm_lst_RestWorkNoButtonList.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_RestWorkNoButtonList.clear();
            disposeDasch();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Close_D_Click_Process()
            throws Exception
    {
        parentRedirect(null);
        dispose();

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

            lbl_ListName.setResourceKey(title);
        }
        else if (this.getTitleResourceKey() != null && !this.getTitleResourceKey().equals(""))
        {
            // リストボックスの場合はpage.xmlから取得する
            lbl_ListName.setResourceKey(this.getTitleResourceKey());
        }
        else if (viewState.getString(Constants.M_TITLE_KEY) != null)
        {
            // 2画面遷移から戻ってきた場合はViewStateから取得する
            lbl_ListName.setResourceKey(viewState.getString(Constants.M_TITLE_KEY));
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
