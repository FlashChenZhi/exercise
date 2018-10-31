// $Id: StorageListCompleteBusiness.java 7821 2010-04-13 02:58:13Z shibamoto $
package jp.co.daifuku.wms.storage.display.listcomplete;

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
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.LocationFormatException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsChecker;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.storage.schedule.StorageListCompleteSCH;
import jp.co.daifuku.wms.storage.schedule.StorageListCompleteSCHParams;

/**
 * 入庫リスト作業結果入力の画面処理を行います。
 *
 * @version $Revision: 7821 $, $Date: 2010-04-13 11:58:13 +0900 (火, 13 4 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class StorageListCompleteBusiness
        extends StorageListComplete
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_StorageListResultInput(HIDDEN_PLAN_LOT_NO) */
    private static final ListCellKey KEY_HIDDEN_PLAN_LOT_NO = new ListCellKey("HIDDEN_PLAN_LOT_NO", new StringCellColumn(), false, false);

    /** lst_StorageListResultInput(HIDEEN_PLAN_AREA_NO) */
    private static final ListCellKey KEY_HIDEEN_PLAN_AREA_NO = new ListCellKey("HIDEEN_PLAN_AREA_NO", new StringCellColumn(), false, false);

    /** lst_StorageListResultInput(HIDDEN_PLAN_LOCATION_NO) */
    private static final ListCellKey KEY_HIDDEN_PLAN_LOCATION_NO = new ListCellKey("HIDDEN_PLAN_LOCATION_NO", new StringCellColumn(), false, false);

    /** lst_StorageListResultInput(HIDDEN_STORAGE_CASE_QTY) */
    private static final ListCellKey KEY_HIDDEN_STORAGE_CASE_QTY = new ListCellKey("HIDDEN_STORAGE_CASE_QTY", new NumberCellColumn(0), false, false);

    /** lst_StorageListResultInput(HIDDEN_STORAGE_PIECE_QTY) */
    private static final ListCellKey KEY_HIDDEN_STORAGE_PIECE_QTY = new ListCellKey("HIDDEN_STORAGE_PIECE_QTY", new NumberCellColumn(0), false, false);

    /** lst_StorageListResultInput(HIDDEN_COLLECT_JOBNO) */
    private static final ListCellKey KEY_HIDDEN_COLLECT_JOBNO = new ListCellKey("HIDDEN_COLLECT_JOBNO", new StringCellColumn(), false, false);

    /** lst_StorageListResultInput(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_StorageListResultInput(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_StorageListResultInput(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_StorageListResultInput(LST_PLAN_LOT_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOT_NO = new ListCellKey("LST_PLAN_LOT_NO", new StringCellColumn(), true, true);

    /** lst_StorageListResultInput(LST_PLAN_AREA_NO) */
    private static final ListCellKey KEY_LST_PLAN_AREA_NO = new ListCellKey("LST_PLAN_AREA_NO", new AreaCellColumn(), true, true);

    /** lst_StorageListResultInput(LST_PLAN_LOCATION_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOCATION_NO = new ListCellKey("LST_PLAN_LOCATION_NO", new StringCellColumn(), true, true);

    /** lst_StorageListResultInput(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_StorageListResultInput(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY = new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StorageListResultInput(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY = new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StorageListResultInput(LST_STORAGE_CASE_QTY) */
    private static final ListCellKey KEY_LST_STORAGE_CASE_QTY = new ListCellKey("LST_STORAGE_CASE_QTY", new NumberCellColumn(0), true, true);

    /** lst_StorageListResultInput(LST_STORAGE_PIECE_QTY) */
    private static final ListCellKey KEY_LST_STORAGE_PIECE_QTY = new ListCellKey("LST_STORAGE_PIECE_QTY", new NumberCellColumn(0), true, true);

    /** lst_StorageListResultInput(LST_SHORTAGE) */
    private static final ListCellKey KEY_LST_SHORTAGE = new ListCellKey("LST_SHORTAGE", new CheckBoxColumn(), true, true);

    /** lst_StorageListResultInput keys */
    private static final ListCellKey[] LST_STORAGELISTRESULTINPUT_KEYS = {
        KEY_HIDDEN_PLAN_LOT_NO,
        KEY_HIDEEN_PLAN_AREA_NO,
        KEY_HIDDEN_PLAN_LOCATION_NO,
        KEY_HIDDEN_STORAGE_CASE_QTY,
        KEY_HIDDEN_STORAGE_PIECE_QTY,
        KEY_HIDDEN_COLLECT_JOBNO,
        KEY_LST_SELECT,
        KEY_LST_ITEM_CODE,
        KEY_LST_PLAN_LOT_NO,
        KEY_LST_PLAN_AREA_NO,
        KEY_LST_PLAN_LOCATION_NO,
        KEY_LST_ENTERING_QTY,
        KEY_LST_PLAN_CASE_QTY,
        KEY_LST_STORAGE_CASE_QTY,
        KEY_LST_SHORTAGE,
        KEY_LST_ITEM_NAME,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_STORAGE_PIECE_QTY,
    };

    // DFKLOOK:ここから修正
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    // 選択チェックボックス(リストセル)
    private static final int LST_CHECK = 1;

    // ロットNo.(リストセル)
    private static final int LST_LOTNO = 3;

    // 入庫エリア(リストセル)
    private static final int LST_AREA_NO = 4;

    // 入庫棚(リストセル)
    private static final int LST_LOCATION = 5;

    // ケース入数(リストセル)
    private static final int LST_ENTERING_QTY = 6;

    // 入庫ケース数(リストセル)
    private static final int LST_RESULT_CASEQTY = 8;

    // 欠品(リストセル)
    private static final int LST_SHORTAGECHK = 9;

    // 入庫ピース数(リストセル)
    private static final int LST_RESULT_PIECEQTY = 12;

    // DFKLOOK:ここまで修正

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_StorageListResultInput */
    private ListCellModel _lcm_lst_StorageListResultInput;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public StorageListCompleteBusiness()
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
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
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
        if (eventSource.startsWith("btn_Complete_Click"))
        {
        	btn_Complete_Click_Process(eventSource);
        }
        // DFKLOOK：ここまで修正
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
    public void btn_Complete_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Complete_Click_Process(null);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ClearStorageQty_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ClearStorageQty_Click_Process();
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
    public void lst_StorageListResultInput_Change(ActionEvent e)
            throws Exception
    {
        // 現在行を取得
        int row = lst_StorageListResultInput.getActiveRow();

        // 作業行を選択します
        lst_StorageListResultInput.setCurrentRow(row);

        // 選択されている場合
        if (lst_StorageListResultInput.getChecked(LST_CHECK))
        {
            // ロットNo.(読取専用解除)
            lst_StorageListResultInput.setCellReadOnly(LST_LOTNO, false);
            // 入庫エリア(読取専用解除)
            lst_StorageListResultInput.setCellReadOnly(LST_AREA_NO, false);
            // 入庫棚(読取専用解除)
            lst_StorageListResultInput.setCellReadOnly(LST_LOCATION, false);

            // ケース入数が0では無い場合
            if (WmsFormatter.getInt(lst_StorageListResultInput.getValue(LST_ENTERING_QTY)) != 0)
            {
                // 入庫ケース数(読取専用解除)
                lst_StorageListResultInput.setCellReadOnly(LST_RESULT_CASEQTY, false);
            }
            // 入庫ピース数(読取専用解除)
            lst_StorageListResultInput.setCellReadOnly(LST_RESULT_PIECEQTY, false);
            // 欠品(読取専用解除)
            lst_StorageListResultInput.setCellEnabled(LST_SHORTAGECHK, true);
        }
        else
        {
            // ロットNo.(読取専用)
            lst_StorageListResultInput.setCellReadOnly(LST_LOTNO, true);
            // 入庫エリア(読取専用)
            lst_StorageListResultInput.setCellReadOnly(LST_AREA_NO, true);
            // 入庫棚(読取専用)
            lst_StorageListResultInput.setCellReadOnly(LST_LOCATION, true);
            // 入庫ケース数(読取専用)
            lst_StorageListResultInput.setCellReadOnly(LST_RESULT_CASEQTY, true);
            // 入庫ピース数(読取専用)
            lst_StorageListResultInput.setCellReadOnly(LST_RESULT_PIECEQTY, true);
            // 欠品(読取専用)
            lst_StorageListResultInput.setCellEnabled(LST_SHORTAGECHK, false);
        }
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

        // initialize lst_StorageListResultInput.
        _lcm_lst_StorageListResultInput = new ListCellModel(lst_StorageListResultInput, LST_STORAGELISTRESULTINPUT_KEYS, locale);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_SELECT, true);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_PLAN_LOT_NO, false);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_PLAN_AREA_NO, false);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_PLAN_LOCATION_NO, false);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_STORAGE_CASE_QTY, false);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_STORAGE_PIECE_QTY, false);
        _lcm_lst_StorageListResultInput.setToolTipVisible(KEY_LST_SHORTAGE, true);

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
    private void lst_StorageListResultInput_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        txt_StoragePlanDate.setValue(null);
        txt_ListWorkNo.setValue(null);
        chk_InitialInputStorageNumber.setChecked(false);
        btn_Complete.setEnabled(false);
        btn_ClearStorageQty.setEnabled(false);
        btn_ListClear.setEnabled(false);
        txt_LRStoragePlanDate.setReadOnly(true);
        txt_LRListWorkNo.setReadOnly(true);
        _lcm_lst_StorageListResultInput.clear();

    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_StoragePlanDate);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_StoragePlanDate.validate(this, true);
        txt_ListWorkNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StorageListCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StorageListCompleteSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            StorageListCompleteSCHParams inparam = new StorageListCompleteSCHParams();
            inparam.set(StorageListCompleteSCHParams.PLAN_DAY, txt_StoragePlanDate.getValue());
            inparam.set(StorageListCompleteSCHParams.SETTING_UNIT_KEY, txt_ListWorkNo.getValue());
            inparam.set(StorageListCompleteSCHParams.STORAGE_QTY_INPUT_NUMBER, chk_InitialInputStorageNumber.getValue());
            inparam.set(StorageListCompleteSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_StorageListResultInput.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StorageListResultInput.getNewLine();
                txt_LRStoragePlanDate.setValue(outparam.get(StorageListCompleteSCHParams.PLAN_DAY));
                txt_LRListWorkNo.setValue(outparam.get(StorageListCompleteSCHParams.SETTING_UNIT_KEY));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(StorageListCompleteSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(StorageListCompleteSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_PLAN_LOT_NO, outparam.get(StorageListCompleteSCHParams.PLAN_LOT_NO));
                line.setValue(KEY_LST_PLAN_AREA_NO, outparam.get(StorageListCompleteSCHParams.PLAN_AREA_NO));
                line.setValue(KEY_LST_PLAN_LOCATION_NO, outparam.get(StorageListCompleteSCHParams.PLAN_LOCATION_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(StorageListCompleteSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(StorageListCompleteSCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(StorageListCompleteSCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_STORAGE_CASE_QTY, outparam.get(StorageListCompleteSCHParams.STORAGE_CASE_QTY));
                line.setValue(KEY_LST_STORAGE_PIECE_QTY, outparam.get(StorageListCompleteSCHParams.STORAGE_PIECE_QTY));
                line.setValue(KEY_HIDDEN_PLAN_LOT_NO, outparam.get(StorageListCompleteSCHParams.PLAN_LOT_NO));
                line.setValue(KEY_HIDEEN_PLAN_AREA_NO, outparam.get(StorageListCompleteSCHParams.PLAN_AREA_NO));
                line.setValue(KEY_HIDDEN_PLAN_LOCATION_NO, outparam.get(StorageListCompleteSCHParams.PLAN_LOCATION_NO));
                line.setValue(KEY_HIDDEN_STORAGE_CASE_QTY, outparam.get(StorageListCompleteSCHParams.STORAGE_CASE_QTY));
                line.setValue(KEY_HIDDEN_STORAGE_PIECE_QTY, outparam.get(StorageListCompleteSCHParams.STORAGE_PIECE_QTY));
                line.setValue(KEY_HIDDEN_COLLECT_JOBNO, outparam.get(StorageListCompleteSCHParams.COLLECT_JOBNO));
                lst_StorageListResultInput_SetLineToolTip(line);
                _lcm_lst_StorageListResultInput.add(line);

                // DFKLOOK:ここから修正
                // 入力エリアを読取専用にする
                // ロットNo.
                _lcm_lst_StorageListResultInput.getListCell().setCellReadOnly(LST_LOTNO, true);
                // 入庫エリア
                _lcm_lst_StorageListResultInput.getListCell().setCellReadOnly(LST_AREA_NO, true);
                // 入庫棚
                _lcm_lst_StorageListResultInput.getListCell().setCellReadOnly(LST_LOCATION, true);
                // 入庫ケース数
                _lcm_lst_StorageListResultInput.getListCell().setCellReadOnly(LST_RESULT_CASEQTY, true);
                // 入庫ピース数
                _lcm_lst_StorageListResultInput.getListCell().setCellReadOnly(LST_RESULT_PIECEQTY, true);
                // 欠品
                _lcm_lst_StorageListResultInput.getListCell().setCellEnabled(LST_SHORTAGECHK, false);
                // DFKLOOK:ここまで修正
            }

            // DFKLOOK:ここから修正
            // データが存在しなかった場合の対応
            if (outparams.size() != 0)
            {
                // clear.
                btn_Complete.setEnabled(true);
                btn_ClearStorageQty.setEnabled(true);
                btn_ListClear.setEnabled(true);
            }
            else
            {
                // clear.
                btn_Complete.setEnabled(false);
                btn_ClearStorageQty.setEnabled(false);
                btn_ListClear.setEnabled(false);
                txt_LRListWorkNo.setValue(null);
                txt_LRStoragePlanDate.setValue(null);
            }
            // DFKLOOK:ここまで修正
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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_StoragePlanDate.setValue(null);
        txt_ListWorkNo.setValue(null);
        chk_InitialInputStorageNumber.setChecked(false);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Complete_Click_Process(String eventSource)
            throws Exception
    {
    	// DFKLOOK start
        if (StringUtil.isBlank(eventSource))
        {
            // ハイライトのリセット
            _lcm_lst_StorageListResultInput.resetEditRow();
            _lcm_lst_StorageListResultInput.resetHighlight();
            
            // チェッカークラス生成
            WmsChecker checker = new WmsChecker();

            boolean existEditedRow = false;

            for (int rowNo = 1; rowNo <= _lcm_lst_StorageListResultInput.size(); rowNo++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_StorageListResultInput.get(rowNo);

                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                lst_StorageListResultInput.setCurrentRow(rowNo);

                if (!lst_StorageListResultInput.getChecked(LST_CHECK))
                {
                    continue;
                }

                existEditedRow = true;

                lst_StorageListResultInput.validate(line.getIndex(KEY_LST_PLAN_LOT_NO), false);
                lst_StorageListResultInput.validate(line.getIndex(KEY_LST_PLAN_AREA_NO), false);
                lst_StorageListResultInput.validate(line.getIndex(KEY_LST_PLAN_LOCATION_NO), false);

                // 欠品チェックがあるかどうかのフラグ
                boolean shortageCheck = lst_StorageListResultInput.getChecked(LST_SHORTAGECHK);

                String caseQty = lst_StorageListResultInput.getValue(LST_RESULT_CASEQTY);
                // ケース数が0、もしくは空かどうかのフラグ
                boolean caseZero = StringUtil.isBlank(caseQty) || caseQty.equals("0");

                String pieceQty = lst_StorageListResultInput.getValue(LST_RESULT_PIECEQTY);
                // ピース数が0、もしくは空かどうかのフラグ
                boolean pieceZero = StringUtil.isBlank(pieceQty) || pieceQty.equals("0");

                // 欠品チェックがあり・入庫数が0の場合、エリア、棚チェックは行われない
                if (!(shortageCheck && caseZero && pieceZero))
                {
                    // 入庫エリアチェック
                    if (StringUtil.isBlank(lst_StorageListResultInput.getValue(LST_AREA_NO)))
                    {
                        // (6023127)No.{0}入庫エリアを入力してください。
                        message.setMsgResourceKey(WmsMessageFormatter.format(6023127, rowNo));
                        _lcm_lst_StorageListResultInput.addHighlight(rowNo, ControlColor.Warning);
                        return;
                    }

                    // 入庫棚チェック
                    if (StringUtil.isBlank(lst_StorageListResultInput.getValue(LST_LOCATION)))
                    {
                        // (6023128)No.{0}入庫棚を入力してください。
                        message.setMsgResourceKey(WmsMessageFormatter.format(6023128, rowNo));
                        _lcm_lst_StorageListResultInput.addHighlight(rowNo, ControlColor.Warning);
                        return;
                    }
                    
                    try
                    {
                        // 棚のフォーマットチェック
                        WmsFormatter.toParamLocation(lst_StorageListResultInput.getValue(LST_LOCATION),
                                SuperLocationHolder.getInstance().getLocationFormat(
                                        lst_StorageListResultInput.getValue(LST_AREA_NO)));
                    }
                    // 棚フォーマットで投げられたExceptionをここでキャッチ
                    catch (LocationFormatException ex)
                    {
                        //6023317=No.{0} 棚のフォーマットが違います。
                        message.setMsgResourceKey(WmsMessageFormatter.format(6023317, rowNo));
                        _lcm_lst_StorageListResultInput.addHighlight(rowNo, ControlColor.Warning);
                        return;
                    }
                    catch (ScheduleException ex)
                    {
                        //6023030=No.{0} 指定されたエリアは、存在しません。
                        message.setMsgResourceKey(WmsMessageFormatter.format(6023030, rowNo));
                        _lcm_lst_StorageListResultInput.addHighlight(rowNo, ControlColor.Warning);
                        return;
                    }
                }

                // 欠品チェックが入っていない場合のみ処理を行う
                if (!shortageCheck)
                {
                    // ケース数、ピース数が入力されていない場合
                    if (StringUtil.isBlank(caseQty) && StringUtil.isBlank(pieceQty))
                    {
                        // (6023205)No.{0} 入庫ケース数または入庫ピース数を入力してください。
                        message.setMsgResourceKey(WmsMessageFormatter.format(6023205, rowNo));
                        _lcm_lst_StorageListResultInput.addHighlight(rowNo, ControlColor.Warning);
                        return;
                    }
                }

                // エリアNo.
                if (!checker.checkContainNgText(lst_StorageListResultInput.getValue(LST_AREA_NO), rowNo, "LBL-W0198"))
                {
                    // 返却メッセージの設定
                    message.setMsgResourceKey(checker.getMessage());
                    _lcm_lst_StorageListResultInput.addHighlight(rowNo, ControlColor.Warning);
                    return;
                }

                // 欠品チェックがなく(分納)、ケース数とピース数が0の場合
                if (!shortageCheck && caseZero && pieceZero)
                {
                    // (6023046)No.{0} ケース数またはピース数には1以上の値を入力してください。
                    message.setMsgResourceKey(WmsMessageFormatter.format(6023046, rowNo));
                    _lcm_lst_StorageListResultInput.addHighlight(rowNo, ControlColor.Warning);
                    return;
                }

                // パラメータのケース入数が0の場合、ケース数は0が登録されているか
                if (Integer.parseInt(line.getStringValue(KEY_LST_ENTERING_QTY)) == 0 && !caseZero)
                {
                    // (6023017)ケース入数が0の場合、予定ケース数は入力できません。
                    message.setMsgResourceKey(WmsMessageFormatter.format(6023017, rowNo));
                    _lcm_lst_StorageListResultInput.addHighlight(rowNo, ControlColor.Warning);
                    return;
                }

                // 予定数
                int planQty =
                        Integer.parseInt(line.getNumberValue(KEY_LST_ENTERING_QTY).toString())
                                * Integer.parseInt(line.getNumberValue(KEY_LST_PLAN_CASE_QTY).toString())
                                + Integer.parseInt(line.getNumberValue(KEY_LST_PLAN_PIECE_QTY).toString());
                // 実績数
                int resultQty =
                        Integer.parseInt(line.getNumberValue(KEY_LST_ENTERING_QTY).toString())
                                * Integer.parseInt(line.getNumberValue(KEY_LST_STORAGE_CASE_QTY).toString())
                                + Integer.parseInt(line.getNumberValue(KEY_LST_STORAGE_PIECE_QTY).toString());
                // 予定数より実績数が多い場合
                if (planQty < resultQty)
                {
                    // (6023034)No.{0} 入庫数には入庫予定数以下の値を入力してください。
                    message.setMsgResourceKey(WmsMessageFormatter.format(6023034, rowNo));
                    _lcm_lst_StorageListResultInput.addHighlight(rowNo, ControlColor.Warning);
                    return;
                }

                // 完了数に作業予定数以上の値を入力した場合、欠品は指定できません。
                if (shortageCheck && (planQty <= resultQty))
                {
                    // 6023250=No.{0} 完了数に作業予定数以上の値を入力した場合、欠品は指定できません。
                    message.setMsgResourceKey(WmsMessageFormatter.format(6023250, rowNo));
                    _lcm_lst_StorageListResultInput.addHighlight(rowNo, ControlColor.Warning);
                    return;
                }
            }

            if (!existEditedRow)
            {
                // (6003001)データを選択してください。
                message.setMsgResourceKey("6003001");
                return;
            }

            // 完了しますか?
            this.setConfirm("MSG-W0043", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Complete_Click");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StorageListCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StorageListCompleteSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_StorageListResultInput.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_StorageListResultInput.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                lst_StorageListResultInput.setCurrentRow(i);
                if (!lst_StorageListResultInput.getChecked(LST_CHECK))
                {
                    continue;
                }
                
                StorageListCompleteSCHParams lineparam = new StorageListCompleteSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(StorageListCompleteSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(StorageListCompleteSCHParams.SETTING_UNIT_KEY, txt_LRListWorkNo.getValue());
                lineparam.set(StorageListCompleteSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(StorageListCompleteSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(StorageListCompleteSCHParams.PLAN_LOT_NO, line.getValue(KEY_LST_PLAN_LOT_NO));
                lineparam.set(StorageListCompleteSCHParams.PLAN_AREA_NO, line.getValue(KEY_LST_PLAN_AREA_NO));
                //DFKLOOK:ここから修正
                String style =
                        SuperLocationHolder.getInstance().getLocationFormat(line.getStringValue(KEY_LST_PLAN_AREA_NO));
                String loc = WmsFormatter.toParamLocation(line.getStringValue(KEY_LST_PLAN_LOCATION_NO), style);
                lineparam.set(StorageListCompleteSCHParams.PLAN_LOCATION_NO, loc);
                //DFKLOOK:ここまで修正
                lineparam.set(StorageListCompleteSCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(StorageListCompleteSCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                lineparam.set(StorageListCompleteSCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                lineparam.set(StorageListCompleteSCHParams.STORAGE_CASE_QTY, line.getValue(KEY_LST_STORAGE_CASE_QTY));
                lineparam.set(StorageListCompleteSCHParams.STORAGE_PIECE_QTY, line.getValue(KEY_LST_STORAGE_PIECE_QTY));
                lineparam.set(StorageListCompleteSCHParams.SHORTAGE, line.getValue(KEY_LST_SHORTAGE));
                lineparam.set(StorageListCompleteSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                lineparam.set(StorageListCompleteSCHParams.PLAN_DAY, txt_LRStoragePlanDate.getValue());
                lineparam.set(StorageListCompleteSCHParams.COLLECT_JOBNO, line.getValue(KEY_HIDDEN_COLLECT_JOBNO));
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // ダイアログ以外からの処理の場合
            // reset editing row.
            _lcm_lst_StorageListResultInput.resetEditRow();
            _lcm_lst_StorageListResultInput.resetHighlight();

            // DFKLOOK:ここから修正
            if (eventSource.equals("btn_Complete_Click"))
            {
                // リスト件数分繰り返す
                for (ScheduleParams p : inparams)
                {
                    // リスト入力チェックを行う
                    if (!sch.check(p, p.getRowIndex()))
                    {
                        // ハイライト
                        _lcm_lst_StorageListResultInput.addHighlight(p.getRowIndex(), ControlColor.Warning);

                        // メッセージのセット
                        message.setMsgResourceKey(sch.getMessage());
                        return;
                    }

                    // 欠品 かつ ケース0、ピース0以外の場合のみ棚チェックを行う
                    if (!(p.getBoolean(StorageListCompleteSCHParams.SHORTAGE)
                            && p.getInt(StorageListCompleteSCHParams.STORAGE_CASE_QTY) == 0 && p.getInt(StorageListCompleteSCHParams.STORAGE_PIECE_QTY) == 0))
                    {
                        if (!sch.checkLocation(p, p.getRowIndex()))
                        {
                            // ハイライト
                            _lcm_lst_StorageListResultInput.addHighlight(p.getRowIndex(), ControlColor.Warning);

                            // 画面表示するメッセージが存在する場合
                            if (StringUtil.isBlank(sch.getDispMessage()))
                            {
                                // メッセージのセット
                                message.setMsgResourceKey(sch.getMessage());
                                return;
                            }
                            else
                            {
                                // 確認ダイアログの表示
                                this.setConfirm(sch.getDispMessage(), false, true);
                                viewState.setString(_KEY_CONFIRMSOURCE, "btn_Complete_Click_SCH");
                                return;
                            }
                        }
                    }
                }
            }
            // DFKLOOK:ここまで修正

            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_StorageListResultInput.resetEditRow();
                _lcm_lst_StorageListResultInput.resetHighlight();
                _lcm_lst_StorageListResultInput.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_StorageListResultInput.size(); i++)
            {
                ListCellLine line = _lcm_lst_StorageListResultInput.get(i);
                lst_StorageListResultInput.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }
                
                lst_StorageListResultInput.setCurrentRow(i);

                if (!lst_StorageListResultInput.getChecked(LST_CHECK))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                // DFKLOOK start
                part11List.add(txt_LRStoragePlanDate.getStringValue(), "");
                part11List.add(txt_LRListWorkNo.getStringValue(), "");
                // DFKLOOK end
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_AREA_NO), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_LOCATION_NO), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_STORAGE_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_STORAGE_PIECE_QTY), "0");

                //DFKLOOK:ここから修正
                // 予定数
                int planQty =
                        Integer.parseInt(line.getNumberValue(KEY_LST_ENTERING_QTY).toString())
                                * Integer.parseInt(line.getNumberValue(KEY_LST_PLAN_CASE_QTY).toString())
                                + Integer.parseInt(line.getNumberValue(KEY_LST_PLAN_PIECE_QTY).toString());
                // 実績数
                int resultQty =
                        Integer.parseInt(line.getNumberValue(KEY_LST_ENTERING_QTY).toString())
                                * Integer.parseInt(line.getNumberValue(KEY_LST_STORAGE_CASE_QTY).toString())
                                + Integer.parseInt(line.getNumberValue(KEY_LST_STORAGE_PIECE_QTY).toString());
                // 欠品チェックが入っている かつ 予定数と欠品数が同一
                if (planQty == resultQty
                        || lst_StorageListResultInput.getChecked(_lcm_lst_StorageListResultInput.getColumnIndex(KEY_LST_SHORTAGE)))
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("2", "");
                }
                //DFKLOOK:ここまで修正

                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_StorageListResultInput.resetEditRow();
            _lcm_lst_StorageListResultInput.resetHighlight();

            // set input parameters.
            StorageListCompleteSCHParams inparam = new StorageListCompleteSCHParams();
            inparam.set(StorageListCompleteSCHParams.PLAN_DAY, txt_LRStoragePlanDate.getValue());
            inparam.set(StorageListCompleteSCHParams.SETTING_UNIT_KEY, txt_LRListWorkNo.getValue());
            inparam.set(StorageListCompleteSCHParams.STORAGE_QTY_INPUT_NUMBER, chk_InitialInputStorageNumber.getValue());
            inparam.set(StorageListCompleteSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_StorageListResultInput.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StorageListResultInput.getNewLine();
                txt_LRStoragePlanDate.setValue(outparam.get(StorageListCompleteSCHParams.PLAN_DAY));
                txt_LRListWorkNo.setValue(outparam.get(StorageListCompleteSCHParams.SETTING_UNIT_KEY));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(StorageListCompleteSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(StorageListCompleteSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_PLAN_LOT_NO, outparam.get(StorageListCompleteSCHParams.PLAN_LOT_NO));
                line.setValue(KEY_LST_PLAN_AREA_NO, outparam.get(StorageListCompleteSCHParams.PLAN_AREA_NO));
                line.setValue(KEY_LST_PLAN_LOCATION_NO, outparam.get(StorageListCompleteSCHParams.PLAN_LOCATION_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(StorageListCompleteSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(StorageListCompleteSCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(StorageListCompleteSCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_STORAGE_CASE_QTY, outparam.get(StorageListCompleteSCHParams.STORAGE_CASE_QTY));
                line.setValue(KEY_LST_STORAGE_PIECE_QTY, outparam.get(StorageListCompleteSCHParams.STORAGE_PIECE_QTY));
                line.setValue(KEY_HIDDEN_PLAN_LOT_NO, outparam.get(StorageListCompleteSCHParams.PLAN_LOT_NO));
                line.setValue(KEY_HIDEEN_PLAN_AREA_NO, outparam.get(StorageListCompleteSCHParams.PLAN_AREA_NO));
                line.setValue(KEY_HIDDEN_PLAN_LOCATION_NO, outparam.get(StorageListCompleteSCHParams.PLAN_LOCATION_NO));
                line.setValue(KEY_HIDDEN_STORAGE_CASE_QTY, outparam.get(StorageListCompleteSCHParams.STORAGE_CASE_QTY));
                line.setValue(KEY_HIDDEN_STORAGE_PIECE_QTY, outparam.get(StorageListCompleteSCHParams.STORAGE_PIECE_QTY));
                line.setValue(KEY_HIDDEN_COLLECT_JOBNO, outparam.get(StorageListCompleteSCHParams.COLLECT_JOBNO));
                lst_StorageListResultInput_SetLineToolTip(line);
                _lcm_lst_StorageListResultInput.add(line);

                // DFKLOOK:ここから修正
                // 入力エリアを読取専用にする
                // ロットNo.
                _lcm_lst_StorageListResultInput.getListCell().setCellReadOnly(LST_LOTNO, true);
                // 入庫エリア
                _lcm_lst_StorageListResultInput.getListCell().setCellReadOnly(LST_AREA_NO, true);
                // 入庫棚
                _lcm_lst_StorageListResultInput.getListCell().setCellReadOnly(LST_LOCATION, true);
                // 入庫ケース数
                _lcm_lst_StorageListResultInput.getListCell().setCellReadOnly(LST_RESULT_CASEQTY, true);
                // 入庫ピース数
                _lcm_lst_StorageListResultInput.getListCell().setCellReadOnly(LST_RESULT_PIECEQTY, true);
                // 欠品
                _lcm_lst_StorageListResultInput.getListCell().setCellEnabled(LST_SHORTAGECHK, false);
                // DFKLOOK:ここまで修正
            }

            // DFKLOOK:ここから修正
            // データが存在しなかった場合の対応
            if (outparams.size() == 0)
            {
                // clear.
                btn_Complete.setEnabled(false);
                btn_ClearStorageQty.setEnabled(false);
                btn_ListClear.setEnabled(false);
                txt_LRListWorkNo.setValue(null);
                txt_LRStoragePlanDate.setValue(null);
            }
            // DFKLOOK:ここまで修正
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
    private void btn_ClearStorageQty_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_StorageListResultInput.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_StorageListResultInput.get(i);
            lst_StorageListResultInput.setCurrentRow(i);
            // DFKLOOK:ここから修正
            clearLine.setValue(KEY_LST_STORAGE_CASE_QTY, 0);
            clearLine.setValue(KEY_LST_STORAGE_PIECE_QTY, 0);
            // DFKLOOK:ここまで修正
            clearLine.setValue(KEY_LST_SHORTAGE, Boolean.FALSE);
            lst_StorageListResultInput_SetLineToolTip(clearLine);
            _lcm_lst_StorageListResultInput.set(i, clearLine);
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
        _lcm_lst_StorageListResultInput.clear();
        btn_Complete.setEnabled(false);
        btn_ClearStorageQty.setEnabled(false);
        btn_ListClear.setEnabled(false);
        txt_LRStoragePlanDate.setValue(null);
        txt_LRListWorkNo.setValue(null);

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
