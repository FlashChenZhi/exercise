// $Id: LstUserIdBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.pcart.retrieval.listbox.userid;

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
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
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
import jp.co.daifuku.Constants;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstUserIdDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstUserIdDASCHParams;
import jp.co.daifuku.pcart.retrieval.listbox.userid.LstUserId;
import jp.co.daifuku.pcart.retrieval.listbox.userid.LstUserIdParams;
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
public class LstUserIdBusiness
        extends LstUserId
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

    /** lst_PCTUserIdList(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new StringCellColumn(), true, false);

    /** lst_PCTUserIdList(LST_USER_ID) */
    private static final ListCellKey KEY_LST_USER_ID = new ListCellKey("LST_USER_ID", new StringCellColumn(), true, false);

    /** lst_PCTUserIdList(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME = new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_PCTUserIdList keys */
    private static final ListCellKey[] LST_PCTUSERIDLIST_KEYS = {
        KEY_LST_SELECT,
        KEY_LST_USER_ID,
        KEY_LST_USER_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_PCTUserIdList */
    private ScrollListCellModel _lcm_lst_PCTUserIdList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstUserIdBusiness()
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
    public void lst_PCTUserIdList_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_PCTUserIdList.getActiveCol();

        // choose process.
        if (_lcm_lst_PCTUserIdList.getColumnIndex(KEY_LST_SELECT) == activeCol)
        {
            // process call.
            lst_Select_Click_Process();
        }
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
        _pager = new PagerModel(new Pager[]{pager}, locale);

        // initialize lst_PCTUserIdList.
        _lcm_lst_PCTUserIdList = new ScrollListCellModel(lst_PCTUserIdList, LST_PCTUSERIDLIST_KEYS, locale);
        _lcm_lst_PCTUserIdList.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_PCTUserIdList.setToolTipVisible(KEY_LST_USER_ID, false);
        _lcm_lst_PCTUserIdList.setToolTipVisible(KEY_LST_USER_NAME, false);

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
    private void lst_PCTUserIdList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
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
        // set focus.
        setFocus(pager);

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
        LstUserIdDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "page_Load");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstUserIdDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstUserIdDASCHParams inparam = new LstUserIdDASCHParams();
            LstUserIdParams requestParam = new LstUserIdParams(request);
            inparam.set(LstUserIdDASCHParams.USER_ID, requestParam.get(LstUserIdParams.USER_ID));
            inparam.set(LstUserIdDASCHParams.SEARCHTABLE, requestParam.get(LstUserIdParams.SEARCHTABLE));
            inparam.set(LstUserIdDASCHParams.CONSIGNOR_CODE, requestParam.get(LstUserIdParams.CONSIGNOR_CODE));
            inparam.set(LstUserIdDASCHParams.WORK_DAY, requestParam.get(LstUserIdParams.WORK_DAY));
            inparam.set(LstUserIdDASCHParams.BATCH_NO, requestParam.get(LstUserIdParams.BATCH_NO));
            inparam.set(LstUserIdDASCHParams.BATCH_SEQ_NO, requestParam.get(LstUserIdParams.BATCH_SEQ_NO));
            inparam.set(LstUserIdDASCHParams.AREA_NO, requestParam.get(LstUserIdParams.AREA_NO));
            inparam.set(LstUserIdDASCHParams.PLAN_DAY, requestParam.get(LstUserIdParams.PLAN_DAY));
            inparam.set(LstUserIdDASCHParams.REGULAR_CUSTOMER_CODE, requestParam.get(LstUserIdParams.REGULAR_CUSTOMER_CODE));
            inparam.set(LstUserIdDASCHParams.CUSTOMER_CODE, requestParam.get(LstUserIdParams.CUSTOMER_CODE));
            inparam.set(LstUserIdDASCHParams.ORDER_NO, requestParam.get(LstUserIdParams.ORDER_NO));
            inparam.set(LstUserIdDASCHParams.ITEM_CODE, requestParam.get(LstUserIdParams.ITEM_CODE));
            inparam.set(LstUserIdDASCHParams.JOB_STATUS, requestParam.get(LstUserIdParams.JOB_STATUS));

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_PCTUserIdList.clear();

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
            _lcm_lst_PCTUserIdList.clear();
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
        LstUserIdDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstUserIdDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_PCTUserIdList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTUserIdList.getNewLine();
                line.setValue(KEY_LST_SELECT, outparam.get(LstUserIdDASCHParams.SELECT));
                line.setValue(KEY_LST_USER_ID, outparam.get(LstUserIdDASCHParams.USER_ID));
                line.setValue(KEY_LST_USER_NAME, outparam.get(LstUserIdDASCHParams.USER_NAME));
                lst_PCTUserIdList_SetLineToolTip(line);
                _lcm_lst_PCTUserIdList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_PCTUserIdList.clear();
            disposeDasch();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Select_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_PCTUserIdList.getActiveRow();
        lst_PCTUserIdList.setCurrentRow(row);
        ListCellLine line = _lcm_lst_PCTUserIdList.get(row);

        // output parameter.
        LstUserIdParams outparam = new LstUserIdParams();
        outparam.set(LstUserIdParams.USER_ID, line.getValue(KEY_LST_USER_ID));
        outparam.set(LstUserIdParams.USER_NAME, line.getValue(KEY_LST_USER_NAME));

        ForwardParameters forwardParam = outparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, viewState.getString(_KEY_POPUPSOURCE));
        parentRedirect(forwardParam);
        dispose();

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
