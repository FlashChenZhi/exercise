// $Id: OperationLogListBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.pcart.system.listbox.operationlog;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.system.dasch.OperationLogListDASCH;
import jp.co.daifuku.pcart.system.dasch.OperationLogListDASCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * 画面操作履歴詳細の画面表示を行います。
 *
 * @version $Revision: 7996 $, $Date:: 2011-07-06 09:52:24 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
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
    private static final ListCellKey KEY_LST_CONDITIONS_1 =
            new ListCellKey("LST_CONDITIONS_1", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_CONDITIONS_2) */
    private static final ListCellKey KEY_LST_CONDITIONS_2 =
            new ListCellKey("LST_CONDITIONS_2", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_CONDITIONS_3) */
    private static final ListCellKey KEY_LST_CONDITIONS_3 =
            new ListCellKey("LST_CONDITIONS_3", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_CONDITIONS_4) */
    private static final ListCellKey KEY_LST_CONDITIONS_4 =
            new ListCellKey("LST_CONDITIONS_4", new StringCellColumn(), true, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_2) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_2 =
            new ListCellKey("HIDDEN_ITEM_DATA_2", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_3) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_3 =
            new ListCellKey("HIDDEN_ITEM_DATA_3", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_4) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_4 =
            new ListCellKey("HIDDEN_ITEM_DATA_4", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_5) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_5 =
            new ListCellKey("HIDDEN_ITEM_DATA_5", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_6) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_6 =
            new ListCellKey("HIDDEN_ITEM_DATA_6", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_7) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_7 =
            new ListCellKey("HIDDEN_ITEM_DATA_7", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_8) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_8 =
            new ListCellKey("HIDDEN_ITEM_DATA_8", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_9) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_9 =
            new ListCellKey("HIDDEN_ITEM_DATA_9", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_10) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_10 =
            new ListCellKey("HIDDEN_ITEM_DATA_10", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_11) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_11 =
            new ListCellKey("HIDDEN_ITEM_DATA_11", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_12) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_12 =
            new ListCellKey("HIDDEN_ITEM_DATA_12", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_13) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_13 =
            new ListCellKey("HIDDEN_ITEM_DATA_13", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_14) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_14 =
            new ListCellKey("HIDDEN_ITEM_DATA_14", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_15) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_15 =
            new ListCellKey("HIDDEN_ITEM_DATA_15", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_16) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_16 =
            new ListCellKey("HIDDEN_ITEM_DATA_16", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_17) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_17 =
            new ListCellKey("HIDDEN_ITEM_DATA_17", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_18) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_18 =
            new ListCellKey("HIDDEN_ITEM_DATA_18", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_19) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_19 =
            new ListCellKey("HIDDEN_ITEM_DATA_19", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_20) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_20 =
            new ListCellKey("HIDDEN_ITEM_DATA_20", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_21) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_21 =
            new ListCellKey("HIDDEN_ITEM_DATA_21", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_22) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_22 =
            new ListCellKey("HIDDEN_ITEM_DATA_22", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_23) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_23 =
            new ListCellKey("HIDDEN_ITEM_DATA_23", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_24) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_24 =
            new ListCellKey("HIDDEN_ITEM_DATA_24", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_25) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_25 =
            new ListCellKey("HIDDEN_ITEM_DATA_25", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_26) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_26 =
            new ListCellKey("HIDDEN_ITEM_DATA_26", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_27) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_27 =
            new ListCellKey("HIDDEN_ITEM_DATA_27", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_28) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_28 =
            new ListCellKey("HIDDEN_ITEM_DATA_28", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_29) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_29 =
            new ListCellKey("HIDDEN_ITEM_DATA_29", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_30) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_30 =
            new ListCellKey("HIDDEN_ITEM_DATA_30", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_31) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_31 =
            new ListCellKey("HIDDEN_ITEM_DATA_31", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_32) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_32 =
            new ListCellKey("HIDDEN_ITEM_DATA_32", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_33) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_33 =
            new ListCellKey("HIDDEN_ITEM_DATA_33", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_34) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_34 =
            new ListCellKey("HIDDEN_ITEM_DATA_34", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_35) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_35 =
            new ListCellKey("HIDDEN_ITEM_DATA_35", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_36) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_36 =
            new ListCellKey("HIDDEN_ITEM_DATA_36", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_37) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_37 =
            new ListCellKey("HIDDEN_ITEM_DATA_37", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_38) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_38 =
            new ListCellKey("HIDDEN_ITEM_DATA_38", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_39) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_39 =
            new ListCellKey("HIDDEN_ITEM_DATA_39", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(HIDDEN_ITEM_DATA_40) */
    private static final ListCellKey KEY_HIDDEN_ITEM_DATA_40 =
            new ListCellKey("HIDDEN_ITEM_DATA_40", new StringCellColumn(), false, false);

    /** lst_ScreenControlLogDetails(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_ScreenControlLogDetails(LST_ITEM_DATA_1) */
    private static final ListCellKey KEY_LST_ITEM_DATA_1 =
            new ListCellKey("LST_ITEM_DATA_1", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn kyes */
    private static final ListCellKey[] LST_SEARCHCONDITIONTWOCOLUMN_KEYS = {
            KEY_LST_CONDITIONS_1,
            KEY_LST_CONDITIONS_2,
            KEY_LST_CONDITIONS_3,
            KEY_LST_CONDITIONS_4,
    };

    /** lst_ScreenControlLogDetails kyes */
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
    private ListCellModel _lcm_lst_ScreenControlLogDetails;

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
        // initialize componenets.
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
    // DFKLOOK:ここから修正
    /**
     * 検索条件のリストセルを作成します。
     * 
     * @param param 検索条件
     * @throws Exception 全ての例外を報告します。
     */
    protected void lst_SearchCondition_Make(OperationLogListDASCHParams param, Connection conn, Locale locale)
            throws Exception
    {
        // 1行目の表示
        ListCellLine line = _lcm_lst_SearchConditionTwoColumn.getNewLine();
        // 出力日時
        line.setValue(KEY_LST_CONDITIONS_1, DispResources.getText("LBL-T0082"));
        line.setValue(KEY_LST_CONDITIONS_2, param.getString(OperationLogListDASCHParams.LOG_DAY));
        // 操作区分
        line.setValue(KEY_LST_CONDITIONS_3, DispResources.getText("LBL-W8016"));
        if (!StringUtil.isBlank(param.getString(OperationLogListDASCHParams.OPERATION_TYPE)))
        {
            line.setValue(KEY_LST_CONDITIONS_4, param.getString(OperationLogListDASCHParams.OPERATION_TYPE));
        }
        _lcm_lst_SearchConditionTwoColumn.add(line);

        // 2行目の表示
        line = _lcm_lst_SearchConditionTwoColumn.getNewLine();
        // ユーザID
        line.setValue(KEY_LST_CONDITIONS_1, DispResources.getText("LBL-P0193"));
        line.setValue(KEY_LST_CONDITIONS_2, param.getString(OperationLogListDASCHParams.USER_ID));
        // ユーザ名称
        line.setValue(KEY_LST_CONDITIONS_3, DispResources.getText("LBL-W0279"));
        line.setValue(KEY_LST_CONDITIONS_4, param.getString(OperationLogListDASCHParams.USER_NAME));
        _lcm_lst_SearchConditionTwoColumn.add(line);

        // 3行目の表示
        line = _lcm_lst_SearchConditionTwoColumn.getNewLine();
        // IPアドレス
        line.setValue(KEY_LST_CONDITIONS_1, DispResources.getText("LBL-W9905"));
        line.setValue(KEY_LST_CONDITIONS_2, param.getString(OperationLogListDASCHParams.IP_ADDRESS));
        // 端末名称
        line.setValue(KEY_LST_CONDITIONS_3, DispResources.getText("LBL-W8006"));
        line.setValue(KEY_LST_CONDITIONS_4, param.getString(OperationLogListDASCHParams.TERMINAL_NAME));
        _lcm_lst_SearchConditionTwoColumn.add(line);

        // 4行目の表示
        line = _lcm_lst_SearchConditionTwoColumn.getNewLine();
        // DS番号
        line.setValue(KEY_LST_CONDITIONS_1, DispResources.getText("LBL-W8003"));
        line.setValue(KEY_LST_CONDITIONS_2, param.getString(OperationLogListDASCHParams.DS_NO));
        // 画面名称
        line.setValue(KEY_LST_CONDITIONS_3, DispResources.getText("LBL-W8007"));
        line.setValue(KEY_LST_CONDITIONS_4, param.getString(OperationLogListDASCHParams.SCREEN_NAME));
        _lcm_lst_SearchConditionTwoColumn.add(line);
    }

    // DFKLOOK:ここまで修正
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
        _lcm_lst_SearchConditionTwoColumn =
                new ListCellModel(lst_SearchConditionTwoColumn, LST_SEARCHCONDITIONTWOCOLUMN_KEYS, locale);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_CONDITIONS_1, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_CONDITIONS_2, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_CONDITIONS_3, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_CONDITIONS_4, false);

        // initialize lst_ScreenControlLogDetails.
        _lcm_lst_ScreenControlLogDetails =
                new ListCellModel(lst_ScreenControlLogDetails, LST_SCREENCONTROLLOGDETAILS_KEYS, locale);
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
            inparam.set(OperationLogListDASCHParams.HIDDEN_LOG_DAY,
                    requestParam.get(OperationLogListParams.HIDDEN_LOG_DAY));
            inparam.set(OperationLogListDASCHParams.OPERATION_TYPE,
                    requestParam.get(OperationLogListParams.OPERATION_TYPE));
            inparam.set(OperationLogListDASCHParams.USER_ID, requestParam.get(OperationLogListParams.USER_ID));
            inparam.set(OperationLogListDASCHParams.USER_NAME, requestParam.get(OperationLogListParams.USER_NAME));
            inparam.set(OperationLogListDASCHParams.IP_ADDRESS, requestParam.get(OperationLogListParams.IP_ADDRESS));
            inparam.set(OperationLogListDASCHParams.TERMINAL_NAME,
                    requestParam.get(OperationLogListParams.TERMINAL_NAME));
            inparam.set(OperationLogListDASCHParams.DS_NO, requestParam.get(OperationLogListParams.DS_NO));
            inparam.set(OperationLogListDASCHParams.SCREEN_NAME, requestParam.get(OperationLogListParams.SCREEN_NAME));
            inparam.set(OperationLogListDASCHParams.PAGENAME_RESOURCE_KEY,
                    requestParam.get(OperationLogListParams.PAGENAME_RESOURCE_KEY));
            inparam.set(OperationLogListDASCHParams.HIDDEN_OPERATION_TYPE,
                    requestParam.get(OperationLogListParams.HIDDEN_OPERATION_TYPE));

            // DFKLOOK:ここから修正
            lst_SearchCondition_Make(inparam, conn, locale);
            // DFKLOOK:ここまで修正

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
                message.setMsgResourceKey("6001022\t" + count);
            }

            // DASCH call.
            dasch.search(inparam);

            // DFKLOOK:ここから修正
            Params outparam = (Params)dasch.get();
            // output display.
            while (dasch.next())
            {
                // 行１
                ListCellLine line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 1);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_1));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行２
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 2);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_2));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行３
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 3);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_3));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行４
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 4);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_4));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行５
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 5);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_5));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行６
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 6);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_6));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行７
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 7);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_7));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行８
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 8);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_8));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行９
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 9);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_9));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行１０
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 10);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_10));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行１１
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 11);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_11));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行１２
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 12);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_12));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行１３
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 13);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_13));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行１４
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 14);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_14));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行１５
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 15);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_15));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行１６
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 16);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_16));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行１７
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 17);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_17));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行１８
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 18);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_18));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行１９
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 19);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_19));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行２０
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 20);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_20));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行２１
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 21);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_21));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行２２
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 22);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_22));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行２３
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 23);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_23));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行２４
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 24);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_24));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行２５
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 25);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_25));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行２６
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 26);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_26));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行２７
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 27);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_27));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行２８
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 28);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_28));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行２９
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 29);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_29));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行３０
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 30);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_30));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行３１
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 31);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_31));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行３２
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 32);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_32));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行３３
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 33);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_33));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行３４
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 34);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_34));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行３５
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 35);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_35));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行３６
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 36);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_36));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行３７
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 37);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_37));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行３８
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 38);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_38));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行３９
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 39);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_39));
                _lcm_lst_ScreenControlLogDetails.add(line);
                // 行４０
                line = _lcm_lst_ScreenControlLogDetails.getNewLine();
                line.setValue(KEY_LST_NO, 40);
                line.setValue(KEY_LST_ITEM_DATA_1, outparam.get(OperationLogListDASCHParams.ITEM_DATA_40));
                _lcm_lst_ScreenControlLogDetails.add(line);

                lst_ScreenControlLogDetails_SetLineToolTip(line);
            }
            // DFKLOOK:ここまで修正

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
