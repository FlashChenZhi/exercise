// $Id: OperationLogListBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.pcart.system.listbox.operationlog;

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
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
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
import jp.co.daifuku.pcart.system.dasch.OperationLogListDASCH;
import jp.co.daifuku.pcart.system.dasch.OperationLogListDASCHParams;
import jp.co.daifuku.pcart.system.listbox.operationlog.OperationLogList;
import jp.co.daifuku.pcart.system.listbox.operationlog.OperationLogListParams;
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
public class OperationLogListBusiness
        extends OperationLogList
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_SearchConditionTwoColumn(LST_CONDITIONS_1) */
    private static final ListCellKey KEY_LST_CONDITIONS_1 = new ListCellKey("LST_CONDITIONS_1", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_CONDITIONS_2) */
    private static final ListCellKey KEY_LST_CONDITIONS_2 = new ListCellKey("LST_CONDITIONS_2", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_CONDITIONS_3) */
    private static final ListCellKey KEY_LST_CONDITIONS_3 = new ListCellKey("LST_CONDITIONS_3", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_CONDITIONS_4) */
    private static final ListCellKey KEY_LST_CONDITIONS_4 = new ListCellKey("LST_CONDITIONS_4", new StringCellColumn(), true, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_2) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_2 = new ListCellKey("HIDDEN_ITEM_DATA_2", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_3) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_3 = new ListCellKey("HIDDEN_ITEM_DATA_3", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_4) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_4 = new ListCellKey("HIDDEN_ITEM_DATA_4", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_5) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_5 = new ListCellKey("HIDDEN_ITEM_DATA_5", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_6) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_6 = new ListCellKey("HIDDEN_ITEM_DATA_6", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_7) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_7 = new ListCellKey("HIDDEN_ITEM_DATA_7", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_8) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_8 = new ListCellKey("HIDDEN_ITEM_DATA_8", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_9) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_9 = new ListCellKey("HIDDEN_ITEM_DATA_9", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_10) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_10 = new ListCellKey("HIDDEN_ITEM_DATA_10", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_11) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_11 = new ListCellKey("HIDDEN_ITEM_DATA_11", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_12) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_12 = new ListCellKey("HIDDEN_ITEM_DATA_12", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_13) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_13 = new ListCellKey("HIDDEN_ITEM_DATA_13", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_14) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_14 = new ListCellKey("HIDDEN_ITEM_DATA_14", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_15) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_15 = new ListCellKey("HIDDEN_ITEM_DATA_15", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_16) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_16 = new ListCellKey("HIDDEN_ITEM_DATA_16", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_17) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_17 = new ListCellKey("HIDDEN_ITEM_DATA_17", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_18) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_18 = new ListCellKey("HIDDEN_ITEM_DATA_18", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_19) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_19 = new ListCellKey("HIDDEN_ITEM_DATA_19", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_20) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_20 = new ListCellKey("HIDDEN_ITEM_DATA_20", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_21) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_21 = new ListCellKey("HIDDEN_ITEM_DATA_21", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_22) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_22 = new ListCellKey("HIDDEN_ITEM_DATA_22", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_23) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_23 = new ListCellKey("HIDDEN_ITEM_DATA_23", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_24) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_24 = new ListCellKey("HIDDEN_ITEM_DATA_24", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_25) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_25 = new ListCellKey("HIDDEN_ITEM_DATA_25", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_26) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_26 = new ListCellKey("HIDDEN_ITEM_DATA_26", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_27) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_27 = new ListCellKey("HIDDEN_ITEM_DATA_27", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_28) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_28 = new ListCellKey("HIDDEN_ITEM_DATA_28", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_29) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_29 = new ListCellKey("HIDDEN_ITEM_DATA_29", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_30) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_30 = new ListCellKey("HIDDEN_ITEM_DATA_30", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_31) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_31 = new ListCellKey("HIDDEN_ITEM_DATA_31", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_32) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_32 = new ListCellKey("HIDDEN_ITEM_DATA_32", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_33) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_33 = new ListCellKey("HIDDEN_ITEM_DATA_33", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_34) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_34 = new ListCellKey("HIDDEN_ITEM_DATA_34", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_35) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_35 = new ListCellKey("HIDDEN_ITEM_DATA_35", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_36) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_36 = new ListCellKey("HIDDEN_ITEM_DATA_36", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_37) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_37 = new ListCellKey("HIDDEN_ITEM_DATA_37", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_38) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_38 = new ListCellKey("HIDDEN_ITEM_DATA_38", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_39) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_39 = new ListCellKey("HIDDEN_ITEM_DATA_39", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_40) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_40 = new ListCellKey("HIDDEN_ITEM_DATA_40", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_ScreenControlLogDetails(LST_ITEM_DATA_1) */
    private static final ListCellKey KEY_LST_ITEM_DATA_1 = new ListCellKey("LST_ITEM_DATA_1", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn keys */
    private static final ListCellKey[] LST_SEARCHCONDITIONTWOCOLUMN_KEYS = {
        KEY_LST_CONDITIONS_1,
        KEY_LST_CONDITIONS_2,
        KEY_LST_CONDITIONS_3,
        KEY_LST_CONDITIONS_4,
    };

    /** lst_ScreenControlLogDetails keys */
    private static final ListCellKey[] LST_SCREENCONTROLLOGDETAILS_KEYS = {
        KEY_HIDDEN_ITEM_DATA_2,
        KEY_HIDDEN_ITEM_DATA_3,
        KEY_HIDDEN_ITEM_DATA_4,
        KEY_HIDDEN_ITEM_DATA_5,
        KEY_HIDDEN_ITEM_DATA_6,
        KEY_HIDDEN_ITEM_DATA_7,
        KEY_HIDDEN_ITEM_DATA_8,
        KEY_HIDDEN_ITEM_DATA_9,
        KEY_HIDDEN_ITEM_DATA_10,
        KEY_HIDDEN_ITEM_DATA_11,
        KEY_HIDDEN_ITEM_DATA_12,
        KEY_HIDDEN_ITEM_DATA_13,
        KEY_HIDDEN_ITEM_DATA_14,
        KEY_HIDDEN_ITEM_DATA_15,
        KEY_HIDDEN_ITEM_DATA_16,
        KEY_HIDDEN_ITEM_DATA_17,
        KEY_HIDDEN_ITEM_DATA_18,
        KEY_HIDDEN_ITEM_DATA_19,
        KEY_HIDDEN_ITEM_DATA_20,
        KEY_HIDDEN_ITEM_DATA_21,
        KEY_HIDDEN_ITEM_DATA_22,
        KEY_HIDDEN_ITEM_DATA_23,
        KEY_HIDDEN_ITEM_DATA_24,
        KEY_HIDDEN_ITEM_DATA_25,
        KEY_HIDDEN_ITEM_DATA_26,
        KEY_HIDDEN_ITEM_DATA_27,
        KEY_HIDDEN_ITEM_DATA_28,
        KEY_HIDDEN_ITEM_DATA_29,
        KEY_HIDDEN_ITEM_DATA_30,
        KEY_HIDDEN_ITEM_DATA_31,
        KEY_HIDDEN_ITEM_DATA_32,
        KEY_HIDDEN_ITEM_DATA_33,
        KEY_HIDDEN_ITEM_DATA_34,
        KEY_HIDDEN_ITEM_DATA_35,
        KEY_HIDDEN_ITEM_DATA_36,
        KEY_HIDDEN_ITEM_DATA_37,
        KEY_HIDDEN_ITEM_DATA_38,
        KEY_HIDDEN_ITEM_DATA_39,
        KEY_HIDDEN_ITEM_DATA_40,
        KEY_LST_NO,
        KEY_LST_ITEM_DATA_1,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_SearchConditionTwoColumn */
    private ListCellModel _lcm_lst_SearchConditionTwoColumn;

    /** ListCellModel lst_ScreenControlLogDetails */
    private ScrollListCellModel _lcm_lst_ScreenControlLogDetails;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public OperationLogListBusiness()
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

        // initialize lst_SearchConditionTwoColumn.
        _lcm_lst_SearchConditionTwoColumn = new ListCellModel(lst_SearchConditionTwoColumn, LST_SEARCHCONDITIONTWOCOLUMN_KEYS, locale);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_CONDITIONS_1, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_CONDITIONS_2, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_CONDITIONS_3, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_CONDITIONS_4, false);

        // initialize lst_ScreenControlLogDetails.
        _lcm_lst_ScreenControlLogDetails = new ScrollListCellModel(lst_ScreenControlLogDetails, LST_SCREENCONTROLLOGDETAILS_KEYS, locale);
        _lcm_lst_ScreenControlLogDetails.setToolTipVisible(KEY_LST_NO, false);
        _lcm_lst_ScreenControlLogDetails.setToolTipVisible(KEY_LST_ITEM_DATA_1, false);

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
    private void lst_SearchConditionTwoColumn_SetLineToolTip(ListCellLine line)
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
    private void lst_ScreenControlLogDetails_SetLineToolTip(ListCellLine line)
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
        OperationLogListDASCH dasch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new OperationLogListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            OperationLogListDASCHParams inparam = new OperationLogListDASCHParams();
            OperationLogListParams requestParam = new OperationLogListParams(request);
            inparam.set(OperationLogListDASCHParams.LOG_DAY, requestParam.get(OperationLogListParams.LOG_DAY));
            inparam.set(OperationLogListDASCHParams.LOG_TIME, requestParam.get(OperationLogListParams.LOG_TIME));
            inparam.set(OperationLogListDASCHParams.OPERATION_TYPE, requestParam.get(OperationLogListParams.OPERATION_TYPE));
            inparam.set(OperationLogListDASCHParams.USER_ID, requestParam.get(OperationLogListParams.USER_ID));
            inparam.set(OperationLogListDASCHParams.USER_NAME, requestParam.get(OperationLogListParams.USER_NAME));
            inparam.set(OperationLogListDASCHParams.IP_ADDRESS, requestParam.get(OperationLogListParams.IP_ADDRESS));
            inparam.set(OperationLogListDASCHParams.TERMINAL_NAME, requestParam.get(OperationLogListParams.TERMINAL_NAME));
            inparam.set(OperationLogListDASCHParams.DS_NO, requestParam.get(OperationLogListParams.DS_NO));
            inparam.set(OperationLogListDASCHParams.SCREEN_NAME, requestParam.get(OperationLogListParams.SCREEN_NAME));
            inparam.set(OperationLogListDASCHParams.PAGENAME_RESOURCE_KEY, requestParam.get(OperationLogListParams.PAGENAME_RESOURCE_KEY));
            inparam.set(OperationLogListDASCHParams.HIDDEN_OPERATION_TYPE, requestParam.get(OperationLogListParams.HIDDEN_OPERATION_TYPE));
            inparam.set(OperationLogListDASCHParams.HIDDEN_LOG_DAY, requestParam.get(OperationLogListParams.HIDDEN_LOG_DAY));

            // get count.
            int count = dasch.count(inparam);
            _lcm_lst_ScreenControlLogDetails.clear();

            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }
            else
            {
                message.setMsgResourceKey("6001022\t" + Formatter.getNumFormat(count));
            }

            // DASCH call.
            dasch.search(inparam);

            // output display.
            while (dasch.next())
            {
                OperationLogListDASCHParams outparam = (OperationLogListDASCHParams)dasch.get();
                ListCellLine line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_1));
                line.setValue(KEY_HIDDEN_ITEM_DATA_2, outparam.get(OperationLogListDASCHParams.ITEM_DATA_2));
                line.setValue(KEY_HIDDEN_ITEM_DATA_3, outparam.get(OperationLogListDASCHParams.ITEM_DATA_3));
                line.setValue(KEY_HIDDEN_ITEM_DATA_4, outparam.get(OperationLogListDASCHParams.ITEM_DATA_4));
                line.setValue(KEY_HIDDEN_ITEM_DATA_5, outparam.get(OperationLogListDASCHParams.ITEM_DATA_5));
                line.setValue(KEY_HIDDEN_ITEM_DATA_6, outparam.get(OperationLogListDASCHParams.ITEM_DATA_6));
                line.setValue(KEY_HIDDEN_ITEM_DATA_7, outparam.get(OperationLogListDASCHParams.ITEM_DATA_7));
                line.setValue(KEY_HIDDEN_ITEM_DATA_8, outparam.get(OperationLogListDASCHParams.ITEM_DATA_8));
                line.setValue(KEY_HIDDEN_ITEM_DATA_9, outparam.get(OperationLogListDASCHParams.ITEM_DATA_9));
                line.setValue(KEY_HIDDEN_ITEM_DATA_10, outparam.get(OperationLogListDASCHParams.ITEM_DATA_10));
                line.setValue(KEY_HIDDEN_ITEM_DATA_11, outparam.get(OperationLogListDASCHParams.ITEM_DATA_11));
                line.setValue(KEY_HIDDEN_ITEM_DATA_12, outparam.get(OperationLogListDASCHParams.ITEM_DATA_12));
                line.setValue(KEY_HIDDEN_ITEM_DATA_13, outparam.get(OperationLogListDASCHParams.ITEM_DATA_13));
                line.setValue(KEY_HIDDEN_ITEM_DATA_14, outparam.get(OperationLogListDASCHParams.ITEM_DATA_14));
                line.setValue(KEY_HIDDEN_ITEM_DATA_15, outparam.get(OperationLogListDASCHParams.ITEM_DATA_15));
                line.setValue(KEY_HIDDEN_ITEM_DATA_16, outparam.get(OperationLogListDASCHParams.ITEM_DATA_16));
                line.setValue(KEY_HIDDEN_ITEM_DATA_17, outparam.get(OperationLogListDASCHParams.ITEM_DATA_17));
                line.setValue(KEY_HIDDEN_ITEM_DATA_18, outparam.get(OperationLogListDASCHParams.ITEM_DATA_18));
                line.setValue(KEY_HIDDEN_ITEM_DATA_19, outparam.get(OperationLogListDASCHParams.ITEM_DATA_19));
                line.setValue(KEY_HIDDEN_ITEM_DATA_20, outparam.get(OperationLogListDASCHParams.ITEM_DATA_20));
                line.setValue(KEY_HIDDEN_ITEM_DATA_21, outparam.get(OperationLogListDASCHParams.ITEM_DATA_21));
                line.setValue(KEY_HIDDEN_ITEM_DATA_22, outparam.get(OperationLogListDASCHParams.ITEM_DATA_22));
                line.setValue(KEY_HIDDEN_ITEM_DATA_23, outparam.get(OperationLogListDASCHParams.ITEM_DATA_23));
                line.setValue(KEY_HIDDEN_ITEM_DATA_24, outparam.get(OperationLogListDASCHParams.ITEM_DATA_24));
                line.setValue(KEY_HIDDEN_ITEM_DATA_25, outparam.get(OperationLogListDASCHParams.ITEM_DATA_25));
                line.setValue(KEY_HIDDEN_ITEM_DATA_26, outparam.get(OperationLogListDASCHParams.ITEM_DATA_26));
                line.setValue(KEY_HIDDEN_ITEM_DATA_27, outparam.get(OperationLogListDASCHParams.ITEM_DATA_27));
                line.setValue(KEY_HIDDEN_ITEM_DATA_28, outparam.get(OperationLogListDASCHParams.ITEM_DATA_28));
                line.setValue(KEY_HIDDEN_ITEM_DATA_29, outparam.get(OperationLogListDASCHParams.ITEM_DATA_29));
                line.setValue(KEY_HIDDEN_ITEM_DATA_30, outparam.get(OperationLogListDASCHParams.ITEM_DATA_30));
                line.setValue(KEY_HIDDEN_ITEM_DATA_31, outparam.get(OperationLogListDASCHParams.ITEM_DATA_31));
                line.setValue(KEY_HIDDEN_ITEM_DATA_32, outparam.get(OperationLogListDASCHParams.ITEM_DATA_32));
                line.setValue(KEY_HIDDEN_ITEM_DATA_33, outparam.get(OperationLogListDASCHParams.ITEM_DATA_33));
                line.setValue(KEY_HIDDEN_ITEM_DATA_34, outparam.get(OperationLogListDASCHParams.ITEM_DATA_34));
                line.setValue(KEY_HIDDEN_ITEM_DATA_35, outparam.get(OperationLogListDASCHParams.ITEM_DATA_35));
                line.setValue(KEY_HIDDEN_ITEM_DATA_36, outparam.get(OperationLogListDASCHParams.ITEM_DATA_36));
                line.setValue(KEY_HIDDEN_ITEM_DATA_37, outparam.get(OperationLogListDASCHParams.ITEM_DATA_37));
                line.setValue(KEY_HIDDEN_ITEM_DATA_38, outparam.get(OperationLogListDASCHParams.ITEM_DATA_38));
                line.setValue(KEY_HIDDEN_ITEM_DATA_39, outparam.get(OperationLogListDASCHParams.ITEM_DATA_39));
                line.setValue(KEY_HIDDEN_ITEM_DATA_40, outparam.get(OperationLogListDASCHParams.ITEM_DATA_40));
                lst_ScreenControlLogDetails_SetLineToolTip(line);
                _lcm_lst_ScreenControlLogDetails.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _lcm_lst_ScreenControlLogDetails.clear();
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            DBUtil.close(conn);
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
