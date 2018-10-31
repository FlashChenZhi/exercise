// $Id: ZoneMentLocationBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.pcart.master.display.zonementlocation;

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
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.pcart.master.schedule.ZoneMentLocationSCH;
import jp.co.daifuku.pcart.master.schedule.ZoneMentLocationSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.pcart.master.schedule.PCTMasterInParameter;

/**
 * ゾーンマスタメンテナンス(エリア・棚)の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date:: 2011-07-06 09:52:24 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class ZoneMentLocationBusiness
        extends ZoneMentLocation
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    // DFKLOOK:start
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK:end

    /** lst_ZoneMentenance(HIDDEN_FROM_LOCATION_NO) */
    private static final ListCellKey KEY_HIDDEN_FROM_LOCATION_NO =
            new ListCellKey("HIDDEN_FROM_LOCATION_NO", new StringCellColumn(), false, false);

    /** lst_ZoneMentenance(HIDDEN_TO_LOCATION_NO) */
    private static final ListCellKey KEY_HIDDEN_TO_LOCATION_NO =
            new ListCellKey("HIDDEN_TO_LOCATION_NO", new StringCellColumn(), false, false);

    /** lst_ZoneMentenance(HIDDEN_LAST_UPDATE_DATE) */
    private static final ListCellKey KEY_HIDDEN_LAST_UPDATE_DATE =
            new ListCellKey("HIDDEN_LAST_UPDATE_DATE", new StringCellColumn(), false, false);

    /** lst_ZoneMentenance(HIDDEN_NEW_DATA) */
    private static final ListCellKey KEY_HIDDEN_NEW_DATA =
            new ListCellKey("HIDDEN_NEW_DATA", new StringCellColumn(), false, false);

    /** lst_ZoneMentenance(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_ZoneMentenance(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL =
            new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_ZoneMentenance(LST_DELETE) */
    private static final ListCellKey KEY_LST_DELETE =
            new ListCellKey("LST_DELETE", new StringCellColumn(), true, false);

    /** lst_ZoneMentenance(LST_FROM_LOCATION_NO) */
    private static final ListCellKey KEY_LST_FROM_LOCATION_NO =
            new ListCellKey("LST_FROM_LOCATION_NO", new LocationCellColumn(), true, true);

    /** lst_ZoneMentenance(LST_TO_LOCATION_NO) */
    private static final ListCellKey KEY_LST_TO_LOCATION_NO =
            new ListCellKey("LST_TO_LOCATION_NO", new LocationCellColumn(), true, true);

    /** lst_ZoneMentenance kyes */
    private static final ListCellKey[] LST_ZONEMENTENANCE_KEYS = {
            KEY_HIDDEN_FROM_LOCATION_NO,
            KEY_HIDDEN_TO_LOCATION_NO,
            KEY_HIDDEN_LAST_UPDATE_DATE,
            KEY_HIDDEN_NEW_DATA,
            KEY_LST_SELECT,
            KEY_LST_CANCEL,
            KEY_LST_DELETE,
            KEY_LST_FROM_LOCATION_NO,
            KEY_LST_TO_LOCATION_NO,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // DFKLOOK:ここから修正
    /**
     * 検索結果表示用:選択チェックボックス(リストセル)
     */
    public static final int LIST_CHECKBOX = 1;

    /**
     * 検索結果表示用:取消ボタン(リストセル)
     */
    public static final int LIST_ERACE = 2;

    /**
     * 検索結果表示用:削除ボタン(リストセル)
     */
    public static final int LIST_DELETE = 3;

    /**
     * 検索結果表示用:開始棚No(リストセル)
     */
    public static final int LIST_START_LOCATIONNO = 4;

    /**
     * 検索結果表示用:終了棚No(リストセル)
     */
    public static final int LIST_END_LOCATIONNO = 5;

    // DFKLOOK:ここまで修正

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** ListCellModel lst_ZoneMentenance */
    private ListCellModel _lcm_lst_ZoneMentenance;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public ZoneMentLocationBusiness()
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

        // DFKLOOK:ここから修正
        // 項目の初期化
        clearInputArea();
        // DFKLOOK:ここまで修正
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

    // DFKLOOK start
    /**
    *
    * @param e ActionEvent
    * @throws Exception
    */   
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        // get event source.
        String eventSource = viewState.getString(_KEY_CONFIRMSOURCE);
        if (eventSource == null)
        {
            return;
        }

        // remove event source.
        viewState.remove(_KEY_CONFIRMSOURCE);

        // check result.
        boolean isExecute = new Boolean(String.valueOf(e.getEventArgs().get(0))).booleanValue();
        if (!isExecute)
        {
            return;
        }

        // choose process.
        if (eventSource.startsWith("btn_Set_Click"))
        {
            btn_Set_Click_Process(eventSource);
        }
    }
    // DFKLOOK end
    
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
    public void btn_Set_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Set_Click_Process(null);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Add_Click(ActionEvent e)
            throws Exception
    {
        btn_Add_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllDelete_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllDelete_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ListClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ListClear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ZoneMentenance_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_ZoneMentenance.getActiveCol();

        // choose process.
        if (_lcm_lst_ZoneMentenance.getColumnIndex(KEY_LST_CANCEL) == activeCol)
        {
            // process call.
            lst_Cancel_Click_Process();
        }
        else if (_lcm_lst_ZoneMentenance.getColumnIndex(KEY_LST_DELETE) == activeCol)
        {
            // process call.
            lst_Delete_Click_Process();
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ZoneMentenance_Change(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // 選択チェックボックスが押された行のデータを扱います
        lst_ZoneMentenance.setCurrentRow(lst_ZoneMentenance.getActiveRow());
        ListCellLine line = _lcm_lst_ZoneMentenance.get(lst_ZoneMentenance.getActiveRow());
        if (lst_ZoneMentenance.getChecked(LIST_CHECKBOX))
        {
            // 開始棚Noを入力可能状態にする。
            lst_ZoneMentenance.setCellReadOnly(LIST_START_LOCATIONNO, false);
            // 終了棚Noを入力可能状態にする。
            lst_ZoneMentenance.setCellReadOnly(LIST_END_LOCATIONNO, false);
            // 新規行チェック
            if (Boolean.valueOf(line.getViewString(KEY_HIDDEN_NEW_DATA)))
            {
                // 取消ボタンを押下可能状態にする。
                lst_ZoneMentenance.setCellEnabled(LIST_ERACE, true);
                // 削除ボタンを押下不可状態にする。
                lst_ZoneMentenance.setCellEnabled(LIST_DELETE, false);
            }
            else
            {
                // 取消ボタンを押下不可状態にする。
                lst_ZoneMentenance.setCellEnabled(LIST_ERACE, false);
                // 削除ボタンを押下可能状態にする。
                lst_ZoneMentenance.setCellEnabled(LIST_DELETE, true);
            }
        }
        else
        {
            // 開始棚Noを入力不可状態にする。
            lst_ZoneMentenance.setCellReadOnly(LIST_START_LOCATIONNO, true);
            // 終了棚Noを入力不可状態にする。
            lst_ZoneMentenance.setCellReadOnly(LIST_END_LOCATIONNO, true);
            // 取消ボタンを押下不可状態にする。
            lst_ZoneMentenance.setCellEnabled(LIST_ERACE, false);
            // 削除ボタンを押下不可状態にする。
            lst_ZoneMentenance.setCellEnabled(LIST_DELETE, false);
        }
        // DFKLOOK:ここまで修正
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
     * 開始棚No.、終了棚NO.の値が変更されているかどうかチェックします。<BR>
     * true:変更　false:未変更
     * @param int 選択されているリストセルNo.
     * @return boolean 変更されているかどうかを返します。
     * @throws Exception 全ての例外を報告します。
     */
    protected boolean isDataChange(int i)
            throws Exception
    {

        lst_ZoneMentenance.setCurrentRow(i);
        ListCellLine line = _lcm_lst_ZoneMentenance.get(i);
        // 開始棚No.
        if (!line.getStringValue(KEY_LST_FROM_LOCATION_NO).equals(line.getStringValue(KEY_HIDDEN_FROM_LOCATION_NO)))
        {
            return true;
        }
        // 終了棚No.
        if (!line.getStringValue(KEY_LST_TO_LOCATION_NO).equals(line.getStringValue(KEY_HIDDEN_TO_LOCATION_NO)))
        {
            return true;
        }

        return false;
    }

    // DFKLOOK:ここまで修正

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    // DFKLOOK:ここから修正
    /**
     * 入力エリアをクリアします。<BR>
     * <BR>
     * 概要：以下の入力エリアをクリアし、 ゾーンNo.にフォーカス設定します。
     * <UL>
     * <LI>エリア</LI>
     * <LI>ゾーンNo.</LI>
     * <LI>開始棚No.</LI>
     * <LI>終了棚No.</LI>
     * </UL>
     * @throws Exception 全ての例外を報告します。
     */
    private void clearInputArea()
            throws Exception
    {
        // 入力項目のクリア処理
        // 選択エリア
        pul_Area.setSelectedIndex(0);

        // 選択されたエリア名称をクリア
        lbl_AreaName.setText("");
        // リストセルをクリア
        lst_ZoneMentenance.clearRow();
        // リストセルエリアのボタンを変更
        setEnableListCellArea(false);
    }

    /**
     * リストセルエリアのボタンの有効／無効の設定を行います。<BR>
     * <BR>
     * 
     * @param bottonBool
     *            boolean型：追加ボタンの有効／無効を指定する
     */
    private void setEnableListCellArea(boolean bottonBool)
    {
        // 設定ボタン
        if (bottonBool)
        {
            btn_Set.setEnabled(true);
        }
        else
        {
            btn_Set.setEnabled(false);
        }
        // 一覧クリアボタン
        if (lst_ZoneMentenance.getMaxRows() <= 1)
        {
            btn_ListClear.setEnabled(false);
        }
        else
        {
            btn_ListClear.setEnabled(true);
        }
        // 全削除ボタン
        if (lst_ZoneMentenance.getMaxRows() <= 1)
        {
            btn_AllDelete.setEnabled(false);
        }
        else
        {
            btn_AllDelete.setEnabled(true);
        }
        // 追加ボタン
        if (bottonBool)
        {
            btn_Add.setEnabled(true);
        }
        else
        {
            btn_Add.setEnabled(false);
        }
    }

    // DFKLOOK:ここまで修正

    /**
     *
     * @throws Exception
     */
    private void initializeComponents()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_Area.
        _pdm_pul_Area = new WmsAreaPullDownModel(pul_Area, locale, ui);

        // initialize lst_ZoneMentenance.
        _lcm_lst_ZoneMentenance = new ListCellModel(lst_ZoneMentenance, LST_ZONEMENTENANCE_KEYS, locale);
        _lcm_lst_ZoneMentenance.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_ZoneMentenance.setToolTipVisible(KEY_LST_CANCEL, false);
        _lcm_lst_ZoneMentenance.setToolTipVisible(KEY_LST_DELETE, false);
        _lcm_lst_ZoneMentenance.setToolTipVisible(KEY_LST_FROM_LOCATION_NO, false);
        _lcm_lst_ZoneMentenance.setToolTipVisible(KEY_LST_TO_LOCATION_NO, false);

    }

    /**
     *
     * @throws Exception
     */
    private void initializePulldownModels()
            throws Exception
    {
        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // load pul_Area.
            _pdm_pul_Area.init(conn, AreaType.FLOOR, StationType.ALL, "", false);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_ZoneMentenance_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(pul_Area);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        pul_Area.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ZoneMentLocationSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ZoneMentLocationSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            ZoneMentLocationSCHParams inparam = new ZoneMentLocationSCHParams();
            inparam.set(ZoneMentLocationSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());

            // DFKLOOK:ここから修正
            viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_Area.getSelectedValue());
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            // output display.
            _lcm_lst_ZoneMentenance.clear();

            // DFKLOOK:ここから修正
            // データなし
            // 選択されたエリア名称を表示
            lbl_AreaName.setText(pul_Area.getSelectedItem().getText());
            if (outparams.isEmpty())
            {
                ListCellLine line = _lcm_lst_ZoneMentenance.getNewLine();
                line.setValue(KEY_HIDDEN_NEW_DATA, String.valueOf(true));
                line.setValue(KEY_HIDDEN_LAST_UPDATE_DATE, "");
                lst_ZoneMentenance_SetLineToolTip(line);
                _lcm_lst_ZoneMentenance.add(line);
                lst_ZoneMentenance.setCellReadOnly(LIST_START_LOCATIONNO, true);
                lst_ZoneMentenance.setCellReadOnly(LIST_END_LOCATIONNO, true);
                // 選択チェックボックス
                lst_ZoneMentenance.setChecked(LIST_CHECKBOX, false);
                // 取消ボタン
                lst_ZoneMentenance.setCellEnabled(LIST_ERACE, false);
                // 削除ボタン
                lst_ZoneMentenance.setCellEnabled(LIST_DELETE, false);
                // リストセルエリアのボタンを変更
                setEnableListCellArea(true);

                return;
            }

            message.setMsgResourceKey(sch.getMessage());
            // データあり
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ZoneMentenance.getNewLine();
                line.setValue(KEY_LST_FROM_LOCATION_NO, outparam.get(ZoneMentLocationSCHParams.FROM_LOCATION_NO));
                line.setValue(KEY_LST_TO_LOCATION_NO, outparam.get(ZoneMentLocationSCHParams.TO_LOCATION_NO));
                viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_Area.getSelectedValue());
                line.setValue(KEY_HIDDEN_LAST_UPDATE_DATE, outparam.get(ZoneMentLocationSCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_HIDDEN_FROM_LOCATION_NO, outparam.get(ZoneMentLocationSCHParams.FROM_LOCATION_NO));
                line.setValue(KEY_HIDDEN_TO_LOCATION_NO, outparam.get(ZoneMentLocationSCHParams.TO_LOCATION_NO));
                line.setValue(KEY_HIDDEN_NEW_DATA, "false");
                lst_ZoneMentenance_SetLineToolTip(line);
                _lcm_lst_ZoneMentenance.add(line);

                lst_ZoneMentenance.setCellReadOnly(LIST_START_LOCATIONNO, true);
                lst_ZoneMentenance.setCellReadOnly(LIST_END_LOCATIONNO, true);
                // 選択チェックボックス
                lst_ZoneMentenance.setChecked(LIST_CHECKBOX, false);
                // 取消ボタン
                lst_ZoneMentenance.setCellEnabled(LIST_ERACE, false);
                // 削除ボタン
                lst_ZoneMentenance.setCellEnabled(LIST_DELETE, false);
                // リストセルエリアのボタンを変更
                setEnableListCellArea(true);
            }
            // リストセルエリアのボタンを変更
            setEnableListCellArea(true);
            // DFKLOOK:ここまで修正
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }
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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        _pdm_pul_Area.setSelectedValue(null);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // reset editing row.
        _lcm_lst_ZoneMentenance.resetHighlight();

        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_ZoneMentenance.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_ZoneMentenance.get(i);

            // 選択されていないデータは対象外とする
            Boolean checkFlag = new Boolean(checkline.getStringValue(KEY_LST_SELECT));
            if (!checkFlag)
            {
                continue;
            }

            if (!(checkline.isAppend() || checkline.isEdited()))
            {
                continue;
            }

            existEditedRow = true;
            lst_ZoneMentenance.setCurrentRow(i);
            lst_ZoneMentenance.validate(checkline.getIndex(KEY_LST_FROM_LOCATION_NO), true);
            lst_ZoneMentenance.validate(checkline.getIndex(KEY_LST_TO_LOCATION_NO), true);
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003001");
            return;
        }

        if (lst_ZoneMentenance.getMaxRows() <= 1)
        {
            // 6003007=設定するデータを入力してください。
            message.setMsgResourceKey("6003007");
            return;
        }

        // HEADER部除く
        for (int lc = 1; lc < lst_ZoneMentenance.getMaxRows(); lc++)
        {
            lst_ZoneMentenance.setCurrentRow(lc);
            // 選択情報のみ、更新処理を行います。
            if (lst_ZoneMentenance.getChecked(LIST_CHECKBOX))
            {
                // 作業ゾーンNo,ゾーンNo.共に変更があるかどうか
                if (!isDataChange(lc))
                {
                    // 6403002=修正対象データがありませんでした。
                    message.setMsgResourceKey("6403002");
                    return;
                }
            }
        }
        
        if (StringUtil.isBlank(eventSource))
        {         
            // 設定しますか?
            this.setConfirm("MSG-W9000", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
            return;         
        }
        // DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ZoneMentLocationSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ZoneMentLocationSCH(conn, this.getClass(), locale, ui);

            int comparison = 0;
            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ZoneMentenance.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ZoneMentenance.get(i);

                // DFKLOOK:ここから修正
                // 選択されていないデータは対象外とする
                Boolean checkFlag = new Boolean(line.getStringValue(KEY_LST_SELECT));
                if (!checkFlag)
                {
                    continue;
                }
                // DFKLOOK:ここまで修正

                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                ZoneMentLocationSCHParams lineparam = new ZoneMentLocationSCHParams();

                // DFKLOOK:ここから修正
                if (!StringUtil.isBlank(line.getStringValue(KEY_LST_FROM_LOCATION_NO))
                        && !StringUtil.isBlank(line.getStringValue(KEY_LST_TO_LOCATION_NO)))
                {
                    comparison =
                            line.getStringValue(KEY_LST_FROM_LOCATION_NO).compareTo(
                                    line.getStringValue(KEY_LST_TO_LOCATION_NO));
                }

                if (0 < comparison)
                {
                    lineparam.set(ZoneMentLocationSCHParams.FROM_LOCATION_NO,
                            line.getStringValue(KEY_LST_TO_LOCATION_NO));
                    lineparam.set(ZoneMentLocationSCHParams.TO_LOCATION_NO,
                            line.getStringValue(KEY_LST_FROM_LOCATION_NO));
                }
                else
                {
                    lineparam.set(ZoneMentLocationSCHParams.FROM_LOCATION_NO,
                            line.getStringValue(KEY_LST_FROM_LOCATION_NO));
                    lineparam.set(ZoneMentLocationSCHParams.TO_LOCATION_NO, line.getStringValue(KEY_LST_TO_LOCATION_NO));
                }
                lineparam.set(ZoneMentLocationSCHParams.HIDDEN_FROM_LOCATION_NO,
                        line.getStringValue(KEY_HIDDEN_FROM_LOCATION_NO));
                lineparam.set(ZoneMentLocationSCHParams.HIDDEN_TO_LOCATION_NO,
                        line.getStringValue(KEY_HIDDEN_TO_LOCATION_NO));
                // DFKLOOK:ここまで修正

                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(ZoneMentLocationSCHParams.LAST_UPDATE_DATE, line.getValue(KEY_HIDDEN_LAST_UPDATE_DATE));
                lineparam.set(ZoneMentLocationSCHParams.NEW_DATA, line.getValue(KEY_HIDDEN_NEW_DATA));
                lineparam.set(ZoneMentLocationSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                lineparam.set(ZoneMentLocationSCHParams.PROCESS_FLAG, PCTMasterInParameter.PROCESS_FLAG_MODIFY);
                lineparam.set(ZoneMentLocationSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.VS_AREA_NO));
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // DFKLOOK:ここから修正
            // 更新可能チェック
            if (sch.check(inparams))
            {
                // SCH call.
                if (!sch.startSCH(inparams))
                {
                    // rollback.
                    conn.rollback();
                    message.setMsgResourceKey(sch.getMessage());

                    // reset editing row or highligiting error row.
                    _lcm_lst_ZoneMentenance.resetEditRow();
                    _lcm_lst_ZoneMentenance.resetHighlight();
                    _lcm_lst_ZoneMentenance.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                    return;
                }
            }
            else
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highligiting error row.
                _lcm_lst_ZoneMentenance.resetEditRow();
                _lcm_lst_ZoneMentenance.resetHighlight();
                _lcm_lst_ZoneMentenance.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }
            // DFKLOOK:ここまで修正

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_ZoneMentenance.resetEditRow();
            _lcm_lst_ZoneMentenance.resetHighlight();

            // set input parameters.
            ZoneMentLocationSCHParams inparam = new ZoneMentLocationSCHParams();
            inparam.set(ZoneMentLocationSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.VS_AREA_NO));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_ZoneMentenance.clear();

            // DFKLOOK:ここから修正
            // エラー判定
            if (outparams.isEmpty())
            {
                // エリア名のクリア
                lbl_AreaName.setText("");
                // リストセルエリアのボタンを変更
                setEnableListCellArea(true);

                return;
            }
            // DFKLOOK:ここまで修正

            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ZoneMentenance.getNewLine();
                line.setValue(KEY_HIDDEN_FROM_LOCATION_NO, outparam.get(ZoneMentLocationSCHParams.FROM_LOCATION_NO));
                line.setValue(KEY_HIDDEN_TO_LOCATION_NO, outparam.get(ZoneMentLocationSCHParams.TO_LOCATION_NO));
                line.setValue(KEY_HIDDEN_LAST_UPDATE_DATE, outparam.get(ZoneMentLocationSCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_HIDDEN_NEW_DATA, outparam.get(ZoneMentLocationSCHParams.NEW_DATA));
                line.setValue(KEY_LST_FROM_LOCATION_NO, outparam.get(ZoneMentLocationSCHParams.FROM_LOCATION_NO));
                line.setValue(KEY_LST_TO_LOCATION_NO, outparam.get(ZoneMentLocationSCHParams.TO_LOCATION_NO));
                lst_ZoneMentenance_SetLineToolTip(line);
                _lcm_lst_ZoneMentenance.add(line);
                lst_ZoneMentenance.setCellReadOnly(LIST_START_LOCATIONNO, true);
                lst_ZoneMentenance.setCellReadOnly(LIST_END_LOCATIONNO, true);
                // 取消ボタン
                lst_ZoneMentenance.setCellEnabled(LIST_ERACE, false);
                // 削除ボタン
                lst_ZoneMentenance.setCellEnabled(LIST_DELETE, false);
            }
            // DFKLOOK:ここから修正
            // リストセルエリアのボタンを変更
            setEnableListCellArea(true);
            // DFKLOOK:ここまで修正

        }
        // DFKLOOK:ここから修正
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            try
            {
                DBUtil.rollback(conn);
                DBUtil.close(conn);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
                if (sch != null && !StringUtil.isBlank(sch.getMessage()))
                {
                    message.setMsgResourceKey(sch.getMessage());
                }
            }
        }
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllDelete_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ZoneMentLocationSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ZoneMentLocationSCH(conn, this.getClass(), locale, ui);

            for (int i = 1; i <= _lcm_lst_ZoneMentenance.size(); i++)
            {
                lst_ZoneMentenance.setCurrentRow(i);
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ZoneMentenance.get(i);

                // DFKLOOK:ここから修正
                if (!Boolean.valueOf((line.getStringValue(KEY_HIDDEN_NEW_DATA))))
                {
                    ZoneMentLocationSCHParams lineparam = new ZoneMentLocationSCHParams();
                    lineparam.setProcessFlag(ProcessFlag.DELETE_ALL);
                    lineparam.setRowIndex(i);
                    lineparam.set(ZoneMentLocationSCHParams.HIDDEN_FROM_LOCATION_NO,
                            line.getValue(KEY_HIDDEN_FROM_LOCATION_NO));
                    lineparam.set(ZoneMentLocationSCHParams.HIDDEN_TO_LOCATION_NO,
                            line.getValue(KEY_HIDDEN_TO_LOCATION_NO));
                    lineparam.set(ZoneMentLocationSCHParams.LAST_UPDATE_DATE,
                            line.getValue(KEY_HIDDEN_LAST_UPDATE_DATE));
                    lineparam.set(ZoneMentLocationSCHParams.NEW_DATA, line.getValue(KEY_HIDDEN_NEW_DATA));
                    lineparam.set(ZoneMentLocationSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                    lineparam.set(ZoneMentLocationSCHParams.FROM_LOCATION_NO, line.getValue(KEY_LST_FROM_LOCATION_NO));
                    lineparam.set(ZoneMentLocationSCHParams.TO_LOCATION_NO, line.getValue(KEY_LST_TO_LOCATION_NO));
                    lineparam.set(ZoneMentLocationSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.VS_AREA_NO));
                    lineparam.set(ZoneMentLocationSCHParams.PROCESS_FLAG, PCTMasterInParameter.PROCESS_FLAG_DELETE);

                    // 削除可能チェック
                    if (sch.deleteCheck(lineparam))
                    {
                        // SCH call.
                        if (!sch.startSCH(lineparam))
                        {
                            // rollback.
                            conn.rollback();
                            message.setMsgResourceKey(sch.getMessage());
                            // reset editing row or highligiting error row.
                            _lcm_lst_ZoneMentenance.resetEditRow();
                            _lcm_lst_ZoneMentenance.resetHighlight();
                            _lcm_lst_ZoneMentenance.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                            return;
                        }
                    }
                    else
                    {
                        // rollback.
                        conn.rollback();
                        message.setMsgResourceKey(sch.getMessage());
                        // reset editing row or highligiting error row.
                        _lcm_lst_ZoneMentenance.resetEditRow();
                        _lcm_lst_ZoneMentenance.resetHighlight();
                        _lcm_lst_ZoneMentenance.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                        return;
                    }
                }
                // DFKLOOK:ここまで修正
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_ZoneMentenance.resetEditRow();
            _lcm_lst_ZoneMentenance.resetHighlight();

            // clear.
            _lcm_lst_ZoneMentenance.clear();
            btn_Set.setEnabled(false);
            btn_AllDelete.setEnabled(false);
            btn_ListClear.setEnabled(false);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }
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
    private void btn_ListClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_ZoneMentenance.clear();
        btn_Set.setEnabled(false);
        btn_Add.setEnabled(false);
        btn_AllDelete.setEnabled(false);
        btn_ListClear.setEnabled(false);

        // DFKLOOK:ここから修正
        lbl_AreaName.setText("");
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Add_Click_Process()
            throws Exception
    {
        // DFKLOOK:ここから修正
        // リストセル内に新規行且つ未入力行の有無をチェックします。
        // HEADER部除く
        for (int lc = 1; lc < lst_ZoneMentenance.getMaxRows(); lc++)
        {
            lst_ZoneMentenance.setCurrentRow(lc);
            ListCellLine line = _lcm_lst_ZoneMentenance.get(lc);
            // 新規行チェック
            if (Boolean.valueOf(line.getStringValue(KEY_HIDDEN_NEW_DATA)))
            {
                // 未入力行チェック
                if (StringUtil.isBlank(line.getStringValue(KEY_HIDDEN_FROM_LOCATION_NO))
                        && StringUtil.isBlank(line.getStringValue(KEY_HIDDEN_TO_LOCATION_NO)))
                {
                    return;
                }
            }
        }

        // ハイライト処理をクリア
        _lcm_lst_ZoneMentenance.resetHighlight();

        ListCellLine line = _lcm_lst_ZoneMentenance.getNewLine();
        // 新規フラグ：新規データ
        line.setValue(KEY_HIDDEN_NEW_DATA, String.valueOf(true));
        lst_ZoneMentenance_SetLineToolTip(line);
        _lcm_lst_ZoneMentenance.add(line);
        // 選択チェックボックス
        lst_ZoneMentenance.setChecked(LIST_CHECKBOX, false);
        // 取消ボタン
        lst_ZoneMentenance.setCellEnabled(LIST_ERACE, false);
        // 削除ボタン
        lst_ZoneMentenance.setCellEnabled(LIST_DELETE, false);
        // 作業ゾーンNo
        lst_ZoneMentenance.setValue(LIST_START_LOCATIONNO, "");
        lst_ZoneMentenance.setCellReadOnly(LIST_START_LOCATIONNO, true);
        // ゾーンNo
        lst_ZoneMentenance.setValue(LIST_END_LOCATIONNO, "");
        lst_ZoneMentenance.setCellReadOnly(LIST_END_LOCATIONNO, true);

        // リストセルエリアのボタンを変更
        setEnableListCellArea(true);
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_ZoneMentenance.getActiveRow();
        lst_ZoneMentenance.setCurrentRow(row);

        // reset editing row.
        lst_ZoneMentenance.removeRow(row);
        _lcm_lst_ZoneMentenance.resetEditRow();
        _lcm_lst_ZoneMentenance.resetHighlight();

        // DFKLOOK:ここから修正
        if (0 == _lcm_lst_ZoneMentenance.size())
        {
            btn_Set.setEnabled(false);
            btn_ListClear.setEnabled(false);
            btn_AllDelete.setEnabled(false);
        }
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Delete_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_ZoneMentenance.getActiveRow();
        lst_ZoneMentenance.setCurrentRow(row);
        ListCellLine line = _lcm_lst_ZoneMentenance.get(row);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ZoneMentLocationSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ZoneMentLocationSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            ZoneMentLocationSCHParams inparam = new ZoneMentLocationSCHParams();
            inparam.setProcessFlag(ProcessFlag.DELETE);
            inparam.setRowIndex(row);
            inparam.set(ZoneMentLocationSCHParams.HIDDEN_FROM_LOCATION_NO, line.getValue(KEY_HIDDEN_FROM_LOCATION_NO));
            inparam.set(ZoneMentLocationSCHParams.HIDDEN_TO_LOCATION_NO, line.getValue(KEY_HIDDEN_TO_LOCATION_NO));
            inparam.set(ZoneMentLocationSCHParams.LAST_UPDATE_DATE, line.getValue(KEY_HIDDEN_LAST_UPDATE_DATE));
            inparam.set(ZoneMentLocationSCHParams.NEW_DATA, line.getValue(KEY_HIDDEN_NEW_DATA));
            inparam.set(ZoneMentLocationSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
            inparam.set(ZoneMentLocationSCHParams.FROM_LOCATION_NO, line.getValue(KEY_LST_FROM_LOCATION_NO));
            inparam.set(ZoneMentLocationSCHParams.TO_LOCATION_NO, line.getValue(KEY_LST_TO_LOCATION_NO));
            inparam.set(ZoneMentLocationSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.VS_AREA_NO));
            inparam.set(ZoneMentLocationSCHParams.PROCESS_FLAG, PCTMasterInParameter.PROCESS_FLAG_DELETE);


            // DFKLOOK:ここから修正
            // 削除可能チェック
            if (sch.deleteCheck(inparam))
            {
                // SCH call.
                if (!sch.startSCH(inparam))
                {
                    // rollback.
                    conn.rollback();
                    message.setMsgResourceKey(sch.getMessage());

                    // reset editing row or highligiting error row.
                    _lcm_lst_ZoneMentenance.resetEditRow();
                    _lcm_lst_ZoneMentenance.resetHighlight();
                    _lcm_lst_ZoneMentenance.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                    return;
                }
            }
            else
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highligiting error row.
                _lcm_lst_ZoneMentenance.resetEditRow();
                _lcm_lst_ZoneMentenance.resetHighlight();
                _lcm_lst_ZoneMentenance.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }
            // DFKLOOK:ここまで修正

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            lst_ZoneMentenance.removeRow(row);
            _lcm_lst_ZoneMentenance.resetEditRow();
            _lcm_lst_ZoneMentenance.resetHighlight();

            // DFKLOOK:ここから修正
            if (0 == _lcm_lst_ZoneMentenance.size())
            {
                btn_Set.setEnabled(false);
                btn_ListClear.setEnabled(false);
                btn_AllDelete.setEnabled(false);
            }
            // DFKLOOK:ここまで修正
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }
        }
        finally
        {
            DBUtil.rollback(conn);
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