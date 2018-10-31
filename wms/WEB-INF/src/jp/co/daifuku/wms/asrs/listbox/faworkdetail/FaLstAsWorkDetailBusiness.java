// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.asrs.listbox.faworkdetail;

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
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.Constants;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.dasch.FaLstAsWorkDetailDASCH;
import jp.co.daifuku.wms.asrs.dasch.FaLstAsWorkDetailDASCHParams;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * 作業明細の画面処理を行います。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class FaLstAsWorkDetailBusiness
        extends FaLstAsWorkDetail
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

    /** lst_SearchCondition(LST_SEARCH_CONDITION) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION = new ListCellKey("LST_SEARCH_CONDITION", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_COLUMN_2) */
    private static final ListCellKey KEY_LST_COLUMN_2 = new ListCellKey("LST_COLUMN_2", new StringCellColumn(), true, false);

    /** lst_WorkInquiryList(HIDDEN_ITEM_NAME) */
    private static final ListCellKey KEY_HIDDEN_ITEM_NAME = new ListCellKey("HIDDEN_ITEM_NAME", new StringCellColumn(), false, false);

    /** lst_WorkInquiryList(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_WorkInquiryList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_WorkInquiryList(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_WorkInquiryList(LST_STORAGE_DATE) */
    private static final ListCellKey KEY_LST_STORAGE_DATE = new ListCellKey("LST_STORAGE_DATE", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), true, false);

    /** lst_WorkInquiryList(LST_STOCK_QTY) */
    private static final ListCellKey KEY_LST_STOCK_QTY = new ListCellKey("LST_STOCK_QTY", new NumberCellColumn(0), true, false);

    /** lst_WorkInquiryList(LST_REMAINING_STOCK_QTY) */
    private static final ListCellKey KEY_LST_REMAINING_STOCK_QTY = new ListCellKey("LST_REMAINING_STOCK_QTY", new NumberCellColumn(0), true, false);

    /** lst_WorkInquiryList(LST_WORK_QTY) */
    private static final ListCellKey KEY_LST_WORK_QTY = new ListCellKey("LST_WORK_QTY", new NumberCellColumn(0), true, false);

    /** lst_WorkInquiryList(LST_ALLOCATED) */
    private static final ListCellKey KEY_LST_ALLOCATED = new ListCellKey("LST_ALLOCATED", new StringCellColumn(), true, false);

    /** lst_SearchCondition keys */
    private static final ListCellKey[] LST_SEARCHCONDITION_KEYS = {
        KEY_LST_SEARCH_CONDITION,
        KEY_LST_COLUMN_2,
    };

    /** lst_WorkInquiryList keys */
    private static final ListCellKey[] LST_WORKINQUIRYLIST_KEYS = {
        KEY_HIDDEN_ITEM_NAME,
        KEY_LST_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT_NO,
        KEY_LST_STORAGE_DATE,
        KEY_LST_STOCK_QTY,
        KEY_LST_REMAINING_STOCK_QTY,
        KEY_LST_WORK_QTY,
        KEY_LST_ALLOCATED,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_SearchCondition */
    private ListCellModel _lcm_lst_SearchCondition;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_WorkInquiryList */
    private ListCellModel _lcm_lst_WorkInquiryList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaLstAsWorkDetailBusiness()
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
    public void pager_First(ActionEvent e)
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
    public void pager_Prev(ActionEvent e)
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
    public void pager_Next(ActionEvent e)
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
    public void pager_Last(ActionEvent e)
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
    public void btn_Close_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_Click_Process();
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
    //DFKLOOK:ここから修正
    /**
     * <code>outParam</code>配列の値をリストセルエリアに設定します。<BR>
     * <BR>
     * 概要:<BR>
     * <DIR>
     *     1.リストセルエリアのクリア<BR>
     *     2.配列データをリストセルエリアへ設定<BR>
     *     <BR>
     *     項目名[初期値]<BR>
     *     <DIR>
     *         選択[チェックなし]<BR>
     *     </DIR>
     * </DIR>
     * @param inParam 入庫出力パラメータクラス
     */
    protected void lst_SearchCondition_Make(FaLstAsWorkDetailDASCHParams inParam)
    {
        try
        {
            // リストセルエリアをクリアする
            lst_SearchCondition.clearRow();

            // 行の追加
            lst_SearchCondition.addRow();
            lst_SearchCondition.setCurrentRow(1);

            // 作業No.
            lst_SearchCondition.setValue(1, DisplayText.getText("LBL-W0074"));
            lst_SearchCondition.setValue(2, inParam.getString(FaLstAsWorkDetailDASCHParams.WORK_NO));
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }

    }
    //DFKLOOK:ここまで修正
    
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

        // initialize lst_SearchCondition.
        _lcm_lst_SearchCondition = new ListCellModel(lst_SearchCondition, LST_SEARCHCONDITION_KEYS, locale);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_SEARCH_CONDITION, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_COLUMN_2, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pager}, locale);

        // initialize lst_WorkInquiryList.
        _lcm_lst_WorkInquiryList = new ListCellModel(lst_WorkInquiryList, LST_WORKINQUIRYLIST_KEYS, locale);
        _lcm_lst_WorkInquiryList.setToolTipVisible(KEY_LST_NO, true);
        _lcm_lst_WorkInquiryList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_WorkInquiryList.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_WorkInquiryList.setToolTipVisible(KEY_LST_STORAGE_DATE, true);
        _lcm_lst_WorkInquiryList.setToolTipVisible(KEY_LST_STOCK_QTY, true);
        _lcm_lst_WorkInquiryList.setToolTipVisible(KEY_LST_REMAINING_STOCK_QTY, true);
        _lcm_lst_WorkInquiryList.setToolTipVisible(KEY_LST_WORK_QTY, true);
        _lcm_lst_WorkInquiryList.setToolTipVisible(KEY_LST_ALLOCATED, true);

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
    private void lst_SearchCondition_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_WorkInquiryList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        //DFKLOOK:ここから修正
        line.addToolTip("LBL-W0130", KEY_HIDDEN_ITEM_NAME);
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
    private void page_Initialize_Process()
            throws Exception
    {
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
        FaLstAsWorkDetailDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "page_Load");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new FaLstAsWorkDetailDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            FaLstAsWorkDetailDASCHParams inparam = new FaLstAsWorkDetailDASCHParams();
            FaLstAsWorkDetailParams requestParam = new FaLstAsWorkDetailParams(request);
            inparam.set(FaLstAsWorkDetailDASCHParams.WORK_NO, requestParam.get(FaLstAsWorkDetailParams.WORK_NO));
            inparam.set(FaLstAsWorkDetailDASCHParams.CARRY_KEY, requestParam.get(FaLstAsWorkDetailParams.CARRY_KEY));

            //DFKLOOK:ここから修正
            // 検索条件セット
            lst_SearchCondition_Make(inparam);
            //DFKLOOK:ここまで修正
            
            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_WorkInquiryList.clear();

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
            _lcm_lst_WorkInquiryList.clear();
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
        FaLstAsWorkDetailDASCH dasch = null;
        try
        {
            // get session.
            dasch = (FaLstAsWorkDetailDASCH)session.getAttribute(_KEY_DASCH_LIST);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_WorkInquiryList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_WorkInquiryList.getNewLine();
                line.setValue(KEY_HIDDEN_ITEM_NAME, outparam.get(FaLstAsWorkDetailDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_NO, outparam.get(FaLstAsWorkDetailDASCHParams.NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(FaLstAsWorkDetailDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_LOT_NO, outparam.get(FaLstAsWorkDetailDASCHParams.LOT_NO));
                line.setValue(KEY_LST_STORAGE_DATE, outparam.get(FaLstAsWorkDetailDASCHParams.STORAGE_DATE));
                line.setValue(KEY_LST_STOCK_QTY, outparam.get(FaLstAsWorkDetailDASCHParams.STOCK_QTY));
                line.setValue(KEY_LST_REMAINING_STOCK_QTY, outparam.get(FaLstAsWorkDetailDASCHParams.REMAINING_STOCK_QTY));
                line.setValue(KEY_LST_WORK_QTY, outparam.get(FaLstAsWorkDetailDASCHParams.WORK_QTY));
                line.setValue(KEY_LST_ALLOCATED, outparam.get(FaLstAsWorkDetailDASCHParams.ALLOCATED));
                lst_WorkInquiryList_SetLineToolTip(line);
                _lcm_lst_WorkInquiryList.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_WorkInquiryList.clear();
            disposeDasch();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Close_Click_Process()
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
