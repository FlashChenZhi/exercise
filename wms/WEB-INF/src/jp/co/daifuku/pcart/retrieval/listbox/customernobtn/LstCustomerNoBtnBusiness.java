// $Id: LstCustomerNoBtnBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.pcart.retrieval.listbox.customernobtn;

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
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstCustomerNoBtnDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstCustomerNoBtnDASCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * 出荷先一覧(登録用)の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date:: 2011-07-06 09:52:24 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class LstCustomerNoBtnBusiness
        extends LstCustomerNoBtn
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_SearchConditionOneColumn(LST_SEARCH_CONDITION_1) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_1 =
            new ListCellKey("LST_SEARCH_CONDITION_1", new StringCellColumn(), true, false);

    /** lst_SearchConditionOneColumn(LST_SEARCH_CONDITION_2) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_2 =
            new ListCellKey("LST_SEARCH_CONDITION_2", new StringCellColumn(), true, false);

    /** lst_SearchConditionOneColumn(LST_SEARCH_CONDITION_3) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_3 =
            new ListCellKey("LST_SEARCH_CONDITION_3", new StringCellColumn(), true, false);

    /** lst_SearchConditionOneColumn(LST_SEARCH_CONDITION_4) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_4 =
            new ListCellKey("LST_SEARCH_CONDITION_4", new StringCellColumn(), true, false);

    /** lst_CustomerListNoBtn(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE =
            new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_CustomerListNoBtn(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME =
            new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_SearchConditionOneColumn kyes */
    private static final ListCellKey[] LST_SEARCHCONDITIONONECOLUMN_KEYS = {
            KEY_LST_SEARCH_CONDITION_1,
            KEY_LST_SEARCH_CONDITION_2,
            KEY_LST_SEARCH_CONDITION_3,
            KEY_LST_SEARCH_CONDITION_4,
    };

    /** lst_CustomerListNoBtn kyes */
    private static final ListCellKey[] LST_CUSTOMERLISTNOBTN_KEYS = {
            KEY_LST_CUSTOMER_CODE,
            KEY_LST_CUSTOMER_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_SearchConditionOneColumn */
    private ListCellModel _lcm_lst_SearchConditionOneColumn;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_CustomerListNoBtn */
    private ListCellModel _lcm_lst_CustomerListNoBtn;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstCustomerNoBtnBusiness()
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
    public void pager_up_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        page_Load_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_up_Prev(ActionEvent e)
            throws Exception
    {
        _pager.previous();
        page_Load_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_up_Next(ActionEvent e)
            throws Exception
    {
        _pager.next();
        page_Load_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_up_Last(ActionEvent e)
            throws Exception
    {
        _pager.last();
        page_Load_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Close_Up_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_Up_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_down_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        page_Load_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_down_Prev(ActionEvent e)
            throws Exception
    {
        _pager.previous();
        page_Load_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_down_Next(ActionEvent e)
            throws Exception
    {
        _pager.next();
        page_Load_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_down_Last(ActionEvent e)
            throws Exception
    {
        _pager.last();
        page_Load_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Close_Down_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_Down_Click_Process();
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

        // initialize lst_SearchConditionOneColumn.
        _lcm_lst_SearchConditionOneColumn =
                new ListCellModel(lst_SearchConditionOneColumn, LST_SEARCHCONDITIONONECOLUMN_KEYS, locale);
        _lcm_lst_SearchConditionOneColumn.setToolTipVisible(KEY_LST_SEARCH_CONDITION_1, false);
        _lcm_lst_SearchConditionOneColumn.setToolTipVisible(KEY_LST_SEARCH_CONDITION_2, false);
        _lcm_lst_SearchConditionOneColumn.setToolTipVisible(KEY_LST_SEARCH_CONDITION_3, false);
        _lcm_lst_SearchConditionOneColumn.setToolTipVisible(KEY_LST_SEARCH_CONDITION_4, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[] {
                pager_up,
                pager_down
        }, locale);

        // initialize lst_CustomerListNoBtn.
        _lcm_lst_CustomerListNoBtn = new ListCellModel(lst_CustomerListNoBtn, LST_CUSTOMERLISTNOBTN_KEYS, locale);
        _lcm_lst_CustomerListNoBtn.setToolTipVisible(KEY_LST_CUSTOMER_CODE, true);
        _lcm_lst_CustomerListNoBtn.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);
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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_CustomerListNoBtn_SetLineToolTip(ListCellLine line)
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
        setFocus(pager_up);

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

        // dispose DASCH.
        disposeDasch();

        Connection conn = null;
        LstCustomerNoBtnDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstCustomerNoBtnDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstCustomerNoBtnDASCHParams inparam = new LstCustomerNoBtnDASCHParams();
            LstCustomerNoBtnParams requestParam = new LstCustomerNoBtnParams(request);
            inparam.set(LstCustomerNoBtnDASCHParams.CONSIGNOR_CODE,
                    requestParam.get(LstCustomerNoBtnParams.CONSIGNOR_CODE));
            inparam.set(LstCustomerNoBtnDASCHParams.CONSIGNOR_NAME,
                    requestParam.get(LstCustomerNoBtnParams.CONSIGNOR_NAME));
            inparam.set(LstCustomerNoBtnDASCHParams.ITEM_CODE, requestParam.get(LstCustomerNoBtnParams.ITEM_CODE));
            inparam.set(LstCustomerNoBtnDASCHParams.ITEM_NAME, requestParam.get(LstCustomerNoBtnParams.ITEM_NAME));
            inparam.set(LstCustomerNoBtnDASCHParams.ENTERING_QTY, requestParam.get(LstCustomerNoBtnParams.ENTERING_QTY));
            inparam.set(LstCustomerNoBtnDASCHParams.BATCH_NO, requestParam.get(LstCustomerNoBtnParams.BATCH_NO));
            inparam.set(LstCustomerNoBtnDASCHParams.BATCH_SEQ_NO, requestParam.get(LstCustomerNoBtnParams.BATCH_SEQ_NO));

            // get count.
            int count = dasch.count(inparam);
            _pager.setMax(count);
            _lcm_lst_CustomerListNoBtn.clear();

            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }
            else if (count > _pager.getMax())
            {
                message.setMsgResourceKey("6001023\t" + Formatter.getNumFormat(count) + "\t"
                        + Formatter.getNumFormat(_pager.getMax()));
            }
            else
            {
                message.setMsgResourceKey("6001022\t" + Formatter.getNumFormat(count));
            }

            // DASCH call.
            dasch.search(inparam);

            // DFKLOOK:ここから修正
            // 検索リストセルのセット
            lst_SearchCondition_Make(inparam);
            // DFKLOOK:ここまで修正

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_CustomerListNoBtn.getNewLine();
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(LstCustomerNoBtnDASCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(LstCustomerNoBtnDASCHParams.CUSTOMER_NAME));
                lst_CustomerListNoBtn_SetLineToolTip(line);
                _lcm_lst_CustomerListNoBtn.add(line);
            }

            // save session.
            session.setAttribute(_KEY_DASCH, dasch);
            isSuccess = true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_CustomerListNoBtn.clear();
        }
        finally
        {
            if (!isSuccess)
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
        LstCustomerNoBtnDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstCustomerNoBtnDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            _lcm_lst_CustomerListNoBtn.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_CustomerListNoBtn.getNewLine();
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(LstCustomerNoBtnDASCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(LstCustomerNoBtnDASCHParams.CUSTOMER_NAME));
                lst_CustomerListNoBtn_SetLineToolTip(line);
                _lcm_lst_CustomerListNoBtn.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_CustomerListNoBtn.clear();
            disposeDasch();
        }
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
     * @throws Exception
     */
    private void btn_Close_Up_Click_Process()
            throws Exception
    {
        parentRedirect(null);
        dispose();

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Close_Down_Click_Process()
            throws Exception
    {
        parentRedirect(null);
        dispose();

    }

    // DFKLOOK:ここから修正
    /**
     * 検索条件リストセルセット
     * 
     */
    protected void lst_SearchCondition_Make(Params inParam)
    {
        // 1行目
        ListCellLine cellLine = _lcm_lst_SearchConditionOneColumn.getNewLine();
        // 荷主コード
        cellLine.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-P0042"));
        cellLine.setValue(KEY_LST_SEARCH_CONDITION_2, inParam.get(LstCustomerNoBtnDASCHParams.CONSIGNOR_CODE));
        // 荷主名称
        cellLine.setValue(KEY_LST_SEARCH_CONDITION_3, DispResources.getText("LBL-P0016"));
        cellLine.setValue(KEY_LST_SEARCH_CONDITION_4, inParam.get(LstCustomerNoBtnDASCHParams.CONSIGNOR_NAME));
        _lcm_lst_SearchConditionOneColumn.add(cellLine);

        // 2行目
        cellLine = _lcm_lst_SearchConditionOneColumn.getNewLine();
        // 商品ｺｰﾄﾞ
        cellLine.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-W0128"));
        cellLine.setValue(KEY_LST_SEARCH_CONDITION_2, inParam.get(LstCustomerNoBtnDASCHParams.ITEM_CODE));
        // 商品名称
        cellLine.setValue(KEY_LST_SEARCH_CONDITION_3, DispResources.getText("LBL-W0130"));
        cellLine.setValue(KEY_LST_SEARCH_CONDITION_4, inParam.get(LstCustomerNoBtnDASCHParams.ITEM_NAME));
        _lcm_lst_SearchConditionOneColumn.add(cellLine);

        // 3行目
        cellLine = _lcm_lst_SearchConditionOneColumn.getNewLine();
        // ﾛｯﾄ入数
        cellLine.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-P0053"));
        cellLine.setValue(KEY_LST_SEARCH_CONDITION_2,
                Formatter.getNumFormat(inParam.getInt(LstCustomerNoBtnDASCHParams.ENTERING_QTY)));
        // 
        cellLine.setValue(KEY_LST_SEARCH_CONDITION_3, "");
        cellLine.setValue(KEY_LST_SEARCH_CONDITION_4, "");
        _lcm_lst_SearchConditionOneColumn.add(cellLine);
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
