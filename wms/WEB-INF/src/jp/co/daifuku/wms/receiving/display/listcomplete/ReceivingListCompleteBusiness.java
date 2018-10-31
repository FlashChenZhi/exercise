// $Id: ReceivingListCompleteBusiness.java 7513 2010-03-13 04:10:51Z okayama $
package jp.co.daifuku.wms.receiving.display.listcomplete;

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
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.receiving.schedule.ReceivingListCompleteSCH;
import jp.co.daifuku.wms.receiving.schedule.ReceivingListCompleteSCHParams;

/**
 * 入荷確定の画面処理を行います。
 * 
 * @version $Revision: 7513 $, $Date: 2010-03-13 13:10:51 +0900 (土, 13 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
@SuppressWarnings("serial")
public class ReceivingListCompleteBusiness
        extends ReceivingListComplete
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";
    // DFKLOOK ここから
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK ここまで
    
    /** lst_ReceivingListResultInput(LST_HIDDEN_JOBNO) */
    private static final ListCellKey KEY_LST_HIDDEN_JOBNO = new ListCellKey("LST_HIDDEN_JOBNO", new StringCellColumn(), false, false);

    /** lst_ReceivingListResultInput(LST_HIDDEN_PLANUKEY) */
    private static final ListCellKey KEY_LST_HIDDEN_PLANUKEY = new ListCellKey("LST_HIDDEN_PLANUKEY", new StringCellColumn(), false, false);

    /** lst_ReceivingListResultInput(LST_COLUMN_1) */
    private static final ListCellKey KEY_LST_COLUMN_1 = new ListCellKey("LST_COLUMN_1", new CheckBoxColumn(), true, true);

    /** lst_ReceivingListResultInput(LST_SUPPLIER_CODE) */
    private static final ListCellKey KEY_LST_SUPPLIER_CODE = new ListCellKey("LST_SUPPLIER_CODE", new StringCellColumn(), true, false);

    /** lst_ReceivingListResultInput(LST_SUPPLIER_NAME) */
    private static final ListCellKey KEY_LST_SUPPLIER_NAME = new ListCellKey("LST_SUPPLIER_NAME", new StringCellColumn(), true, false);

    /** lst_ReceivingListResultInput(LST_TICKET) */
    private static final ListCellKey KEY_LST_TICKET = new ListCellKey("LST_TICKET", new StringCellColumn(), true, false);

    /** lst_ReceivingListResultInput(LST_LINE) */
    private static final ListCellKey KEY_LST_LINE = new ListCellKey("LST_LINE", new StringCellColumn(), true, false);

    /** lst_ReceivingListResultInput(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_ReceivingListResultInput(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_ReceivingListResultInput(LST_LOT) */
    private static final ListCellKey KEY_LST_LOT = new ListCellKey("LST_LOT", new StringCellColumn(), true, true);

    /** lst_ReceivingListResultInput(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_ReceivingListResultInput(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY = new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ReceivingListResultInput(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY = new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ReceivingListResultInput(LST_RECEIVING_CASE_QTY) */
    private static final ListCellKey KEY_LST_RECEIVING_CASE_QTY = new ListCellKey("LST_RECEIVING_CASE_QTY", new NumberCellColumn(0), true, true);

    /** lst_ReceivingListResultInput(LST_RECEIVING_PIECE_QTY) */
    private static final ListCellKey KEY_LST_RECEIVING_PIECE_QTY = new ListCellKey("LST_RECEIVING_PIECE_QTY", new NumberCellColumn(0), true, true);

    /** lst_ReceivingListResultInput(LST_SHORTAGE) */
    private static final ListCellKey KEY_LST_SHORTAGE = new ListCellKey("LST_SHORTAGE", new CheckBoxColumn(), true, true);

    /** lst_ReceivingListResultInput(LST_RECEIVING_AREA) */
    private static final ListCellKey KEY_LST_RECEIVING_AREA = new ListCellKey("LST_RECEIVING_AREA", new StringCellColumn(), true, true);

    /** lst_ReceivingListResultInput keys */
    private static final ListCellKey[] LST_RECEIVINGLISTRESULTINPUT_KEYS = {
        KEY_LST_HIDDEN_JOBNO,
        KEY_LST_HIDDEN_PLANUKEY,
        KEY_LST_COLUMN_1,
        KEY_LST_SUPPLIER_CODE,
        KEY_LST_TICKET,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT,
        KEY_LST_ENTERING_QTY,
        KEY_LST_PLAN_CASE_QTY,
        KEY_LST_RECEIVING_CASE_QTY,
        KEY_LST_SHORTAGE,
        KEY_LST_RECEIVING_AREA,
        KEY_LST_SUPPLIER_NAME,
        KEY_LST_LINE,
        KEY_LST_ITEM_NAME,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_RECEIVING_PIECE_QTY,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // DFKLOOK ここから
    // チェックボックス (ListCell)
    private static final int LST_CHECK = 1;

    // ロットNo. (ListCell)
    private static final int LST_LOTNO = 5;

    // 入荷ケース数 (ListCell) 
    private static final int LST_RESULT_CASEQTY = 8;

    // 欠品フラグ (ListCell)
    private static final int LST_SHORTAGECHK = 9;

    // 入荷エリア (ListCell)
    private static final int LST_AREA_NO = 10;

    // 入荷ピース数 (ListCell)
    private static final int LST_RESULT_PIECEQTY = 15;
    // DFKLOOK ここまで

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_ReceivingListResultInput */
    private ListCellModel _lcm_lst_ReceivingListResultInput;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public ReceivingListCompleteBusiness()
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
        // DFKLOOK ここから
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
        // DFKLOOK ここまで
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
        // DFKLOOK ここから
        // process call.
        btn_Complete_Click_Process(null);
        // DFKLOOK ここまで
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
    /**
     * ダイアログ表示前のチェックを行います。
     * 
     * @param line リストセル行情報
     * @param lineNo リストセル行No.
     * @return 正常:true、異常:false
     */
    protected boolean beforeDialogCheck(ListCellLine line, int lineNo)
    {
        // リストのハイライトをクリア
        _lcm_lst_ReceivingListResultInput.resetHighlight();

        // ケース入数
        int eQty = line.getNumberValue(KEY_LST_ENTERING_QTY).intValue();
        // 入荷ケース数
        int casePack = line.getNumberValue(KEY_LST_RECEIVING_CASE_QTY).intValue();
        // 入荷ピース数
        int piecePack = line.getNumberValue(KEY_LST_RECEIVING_PIECE_QTY).intValue();
        // 入荷数
        int allQty = (eQty * casePack) + piecePack;
        // 予定ケース数
        int planCaseQty = line.getNumberValue(KEY_LST_PLAN_CASE_QTY).intValue();
        // 予定ピース数
        int planPieceQty = line.getNumberValue(KEY_LST_PLAN_PIECE_QTY).intValue();
        // 予定数
        int planQty = (eQty * planCaseQty) + planPieceQty;
        // 欠品チェック
        boolean sChecked =
                lst_ReceivingListResultInput.getChecked(_lcm_lst_ReceivingListResultInput.getColumnIndex(KEY_LST_SHORTAGE));

        // 入荷エリアが入力されていない場合
        if (StringUtil.isBlank(line.getStringValue(KEY_LST_RECEIVING_AREA)))
        {
            // 欠品 かつ ケース0、ピース0以外の場合のみ
            if (!(sChecked && (casePack == 0 && piecePack == 0)))
            {
                // 6023413=No.{0} 入荷エリアを入力してください。
                message.setMsgResourceKey(WmsMessageFormatter.format(6023413, lineNo));
                DisplayUtil.addHighlight(lst_ReceivingListResultInput, lineNo, ControlColor.Warning);
                return false;
            }
        }

        // 入荷ケース数と入荷ピース数のチェックは欠品チェック以外
        if (!sChecked)
        {
            if (StringUtil.isBlank(line.getStringValue(KEY_LST_RECEIVING_CASE_QTY))
                    && StringUtil.isBlank(line.getStringValue(KEY_LST_RECEIVING_PIECE_QTY)))
            {
                // 6023409=No.{0} 入荷ケース数または入荷ピース数を入力してください。
                message.setMsgResourceKey(WmsMessageFormatter.format(6023409, lineNo));
                DisplayUtil.addHighlight(lst_ReceivingListResultInput, lineNo, ControlColor.Warning);
                return false;
            }

            // ケース数、またはピース数がともに0の場合
            if (casePack == 0 && piecePack == 0)
            {
                // 6023046 = No.{0} ケース数またはピース数には1以上の値を入力してください。
                message.setMsgResourceKey(WmsMessageFormatter.format(6023046, lineNo));
                DisplayUtil.addHighlight(lst_ReceivingListResultInput, lineNo, ControlColor.Warning);
                return false;
            }
        }

        // ケース入数が0で予定ケース数が入力されている場合
        if (eQty == 0 && casePack != 0)
        {
            // 6023017 = ケース入数が0の場合、予定ケース数は入力できません
            message.setMsgResourceKey(WmsMessageFormatter.format(6023017, lineNo));
            DisplayUtil.addHighlight(lst_ReceivingListResultInput, lineNo, ControlColor.Warning);
            return false;
        }

        // 入荷数には作業上限数{1}以下の値を入力してください。
        if (allQty > WmsParam.MAX_TOTAL_QTY)
        {
            // 6023411 = No.{0} 入荷数には作業上限数{1}以下の値を入力してください。
            message.setMsgResourceKey(WmsMessageFormatter.format(6023411, lineNo,
                    WmsFormatter.getNumFormat(WmsParam.MAX_TOTAL_QTY)));
            DisplayUtil.addHighlight(lst_ReceivingListResultInput, lineNo, ControlColor.Warning);
            return false;
        }

        // 完了数に作業予定数以上の値を入力した場合、欠品は指定できません。
        if (sChecked && (planQty <= allQty))
        {
            // 6023250=No.{0} 完了数に作業予定数以上の値を入力した場合、欠品は指定できません。
            message.setMsgResourceKey(WmsMessageFormatter.format(6023250, lineNo));
            DisplayUtil.addHighlight(lst_ReceivingListResultInput, lineNo, ControlColor.Warning);
            return false;
        }

        // 問題ない場合
        return true;
    }

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

        // initialize lst_ReceivingListResultInput.
        _lcm_lst_ReceivingListResultInput = new ListCellModel(lst_ReceivingListResultInput, LST_RECEIVINGLISTRESULTINPUT_KEYS, locale);
        _lcm_lst_ReceivingListResultInput.setToolTipVisible(KEY_LST_COLUMN_1, true);
        _lcm_lst_ReceivingListResultInput.setToolTipVisible(KEY_LST_SUPPLIER_CODE, true);
        _lcm_lst_ReceivingListResultInput.setToolTipVisible(KEY_LST_SUPPLIER_NAME, true);
        _lcm_lst_ReceivingListResultInput.setToolTipVisible(KEY_LST_TICKET, true);
        _lcm_lst_ReceivingListResultInput.setToolTipVisible(KEY_LST_LINE, true);
        _lcm_lst_ReceivingListResultInput.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_ReceivingListResultInput.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_ReceivingListResultInput.setToolTipVisible(KEY_LST_LOT, false);
        _lcm_lst_ReceivingListResultInput.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_ReceivingListResultInput.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_ReceivingListResultInput.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_ReceivingListResultInput.setToolTipVisible(KEY_LST_RECEIVING_CASE_QTY, false);
        _lcm_lst_ReceivingListResultInput.setToolTipVisible(KEY_LST_RECEIVING_PIECE_QTY, false);
        _lcm_lst_ReceivingListResultInput.setToolTipVisible(KEY_LST_SHORTAGE, true);
        _lcm_lst_ReceivingListResultInput.setToolTipVisible(KEY_LST_RECEIVING_AREA, false);
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
    @SuppressWarnings("all")
    private void dispose()
            throws Exception
    {
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_ReceivingListResultInput_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0099", KEY_LST_SUPPLIER_NAME);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_ReceivingPlanDate);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        chk_InitialInputReceivingNumber.setChecked(false);
        btn_Complete.setEnabled(false);
        btn_ListClear.setEnabled(false);

        // DFKLOOK ここから
        txt_LRReceivingPlanDate.setReadOnly(true);
        // DFKLOOK ここまで
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ReceivingPlanDate.validate(this, true);
        txt_SupplierCode.validate(this, false);
        txt_ItemCode.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ReceivingListCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ReceivingListCompleteSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            ReceivingListCompleteSCHParams inparam = new ReceivingListCompleteSCHParams();
            inparam.set(ReceivingListCompleteSCHParams.RECEIVING_PLAN_DATE, txt_ReceivingPlanDate.getValue());
            inparam.set(ReceivingListCompleteSCHParams.SUPPLIER_CODE, txt_SupplierCode.getValue());
            inparam.set(ReceivingListCompleteSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(ReceivingListCompleteSCHParams.INITIAL_INPUT_RECEIVING_NUMBER, chk_InitialInputReceivingNumber.getValue());
            inparam.set(ReceivingListCompleteSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK ここから
            // clear.
            if (outparams.size() == 0)
            {
                _lcm_lst_ReceivingListResultInput.clear();
                txt_LRReceivingPlanDate.setText("");
                btn_Complete.setEnabled(false);
                btn_ListClear.setEnabled(false);

                return;
            }

            // 入力された予定日を下部の表示領域に表示する。
            txt_LRReceivingPlanDate.setDate(txt_ReceivingPlanDate.getDate());
            // DFKLOOK ここまで

            // output display.
            _lcm_lst_ReceivingListResultInput.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ReceivingListResultInput.getNewLine();
                line.setValue(KEY_LST_SUPPLIER_CODE, outparam.get(ReceivingListCompleteSCHParams.SUPPLIER_CODE));
                line.setValue(KEY_LST_SUPPLIER_NAME, outparam.get(ReceivingListCompleteSCHParams.SUPPLIER_NAME));
                line.setValue(KEY_LST_TICKET, outparam.get(ReceivingListCompleteSCHParams.TICKET));
                line.setValue(KEY_LST_LINE, outparam.get(ReceivingListCompleteSCHParams.LINE));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(ReceivingListCompleteSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(ReceivingListCompleteSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT, outparam.get(ReceivingListCompleteSCHParams.LOT));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(ReceivingListCompleteSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(ReceivingListCompleteSCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(ReceivingListCompleteSCHParams.PLAN_PIECE_QTY));
                // DFKLOOK ここから
                if (chk_InitialInputReceivingNumber.getChecked())
                {
                    line.setValue(KEY_LST_RECEIVING_CASE_QTY,
                            outparam.get(ReceivingListCompleteSCHParams.RECEIVING_CASE_QTY));
                    line.setValue(KEY_LST_RECEIVING_PIECE_QTY,
                            outparam.get(ReceivingListCompleteSCHParams.RECEIVING_PIECE_QTY));
                }
                // DFKLOOK ここまで
                line.setValue(KEY_LST_RECEIVING_AREA, outparam.get(ReceivingListCompleteSCHParams.RECEIVING_AREA));
                viewState.setObject(ViewStateKeys.RECEIVING_PLAN_DATE, txt_ReceivingPlanDate.getValue());
                viewState.setObject(ViewStateKeys.SUPPLIER_CODE, txt_SupplierCode.getValue());
                viewState.setObject(ViewStateKeys.ITEM_CODE, txt_ItemCode.getValue());
                viewState.setObject(ViewStateKeys.INITIAL_INPUT_RECEIVING_NUMBER, chk_InitialInputReceivingNumber.getValue());
                line.setValue(KEY_LST_HIDDEN_JOBNO, outparam.get(ReceivingListCompleteSCHParams.HIDDEN_JOBNO));
                line.setValue(KEY_LST_HIDDEN_PLANUKEY, outparam.get(ReceivingListCompleteSCHParams.HIDDEN_PLANUKEY));
                lst_ReceivingListResultInput_SetLineToolTip(line);
                _lcm_lst_ReceivingListResultInput.add(line);

                // DFKLOOK ここから
                // Sets ListCell read-only
                lst_ReceivingListResultInput.setCellReadOnly(LST_RESULT_CASEQTY, true);
                lst_ReceivingListResultInput.setCellReadOnly(LST_RESULT_PIECEQTY, true);
                lst_ReceivingListResultInput.setCellEnabled(LST_SHORTAGECHK, false);
                lst_ReceivingListResultInput.setCellReadOnly(LST_LOTNO, true);
                lst_ReceivingListResultInput.setCellReadOnly(LST_AREA_NO, true);
                // DFKLOOK ここまで
            }

            // clear.
            btn_Complete.setEnabled(true);
            btn_ListClear.setEnabled(true);

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
        txt_ReceivingPlanDate.setValue(null);
        txt_SupplierCode.setValue(null);
        txt_ItemCode.setValue(null);
        chk_InitialInputReceivingNumber.setChecked(false);

    }

    /**
     *
     * @throws Exception
     */
    // DFKLOOK ここから
    private void btn_Complete_Click_Process(String eventSource)
            throws Exception
    // DFKLOOK ここまで
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_ReceivingListResultInput.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_ReceivingListResultInput.get(i);
            if (!(checkline.isAppend() || checkline.isEdited()))
            {
                continue;
            }

            lst_ReceivingListResultInput.setCurrentRow(i);

            // DFKLOOK ここから
            // チェックが入っているものが対象
            if (!lst_ReceivingListResultInput.getChecked(LST_CHECK))
            {
                continue;
            }
            // DFKLOOK ここまで
            existEditedRow = true;

            lst_ReceivingListResultInput.validate(checkline.getIndex(KEY_LST_LOT), false);
            lst_ReceivingListResultInput.validate(checkline.getIndex(KEY_LST_RECEIVING_CASE_QTY), false);
            lst_ReceivingListResultInput.validate(checkline.getIndex(KEY_LST_RECEIVING_PIECE_QTY), false);

            // DFKLOOK ここから
            // 入力チェックを行う
            if (!beforeDialogCheck(checkline, i))
            {
                return;
            }
            // DFKLOOK ここまで
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003001");
            return;
        }
        
        // DFKLOOK ここから
        if (StringUtil.isBlank(eventSource))
        {
            // show confirm message.完了しますか？
            this.setConfirm("MSG-W0043", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Complete_Click");
            return;
        }
        // DFKLOOK ここまで
        

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ReceivingListCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ReceivingListCompleteSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ReceivingListResultInput.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ReceivingListResultInput.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                // DFKLOOK ここから
                lst_ReceivingListResultInput.setCurrentRow(i);
                if (!lst_ReceivingListResultInput.getChecked(LST_CHECK))
                {
                    continue;
                }
                // DFKLOOK ここまで

                ReceivingListCompleteSCHParams lineparam = new ReceivingListCompleteSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(ReceivingListCompleteSCHParams.COLUMN_1, line.getValue(KEY_LST_COLUMN_1));
                lineparam.set(ReceivingListCompleteSCHParams.SUPPLIER_CODE, line.getValue(KEY_LST_SUPPLIER_CODE));
                lineparam.set(ReceivingListCompleteSCHParams.SUPPLIER_NAME, line.getValue(KEY_LST_SUPPLIER_NAME));
                lineparam.set(ReceivingListCompleteSCHParams.TICKET, line.getValue(KEY_LST_TICKET));
                lineparam.set(ReceivingListCompleteSCHParams.LINE, line.getValue(KEY_LST_LINE));
                lineparam.set(ReceivingListCompleteSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(ReceivingListCompleteSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(ReceivingListCompleteSCHParams.LOT, line.getValue(KEY_LST_LOT));
                lineparam.set(ReceivingListCompleteSCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(ReceivingListCompleteSCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                lineparam.set(ReceivingListCompleteSCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                lineparam.set(ReceivingListCompleteSCHParams.RECEIVING_CASE_QTY, line.getValue(KEY_LST_RECEIVING_CASE_QTY));
                lineparam.set(ReceivingListCompleteSCHParams.RECEIVING_PIECE_QTY, line.getValue(KEY_LST_RECEIVING_PIECE_QTY));
                lineparam.set(ReceivingListCompleteSCHParams.SHORTAGE, line.getValue(KEY_LST_SHORTAGE));
                lineparam.set(ReceivingListCompleteSCHParams.RECEIVING_AREA, line.getValue(KEY_LST_RECEIVING_AREA));
                lineparam.set(ReceivingListCompleteSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(ReceivingListCompleteSCHParams.HIDDEN_JOBNO, line.getValue(KEY_LST_HIDDEN_JOBNO));
                lineparam.set(ReceivingListCompleteSCHParams.HIDDEN_PLANUKEY, line.getValue(KEY_LST_HIDDEN_PLANUKEY));
                lineparam.set(ReceivingListCompleteSCHParams.RECEIVING_PLAN_DATE, viewState.getObject(ViewStateKeys.RECEIVING_PLAN_DATE));

                // DFKLOOK ここから
                if (!sch.check(lineparam))
                {
                    DisplayUtil.addHighlight(lst_ReceivingListResultInput, i, ControlColor.Warning);
                    // Sets a message
                    message.setMsgResourceKey(sch.getMessage());
                    return;
                }
                // DFKLOOK ここまで

                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_ReceivingListResultInput.resetEditRow();
                _lcm_lst_ReceivingListResultInput.resetHighlight();
                _lcm_lst_ReceivingListResultInput.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_ReceivingListResultInput.size(); i++)
            {
                ListCellLine line = _lcm_lst_ReceivingListResultInput.get(i);
                lst_ReceivingListResultInput.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }
                // DFKLOOK ここから
                // チェックが入っている(確定されたデータのみ対象とします。)
                if (!lst_ReceivingListResultInput.getChecked(LST_CHECK))
                {
                    continue;
                }
                // DFKLOOK ここまで

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(txt_LRReceivingPlanDate.getStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_SUPPLIER_CODE), "");
                part11List.add(line.getViewString(KEY_LST_TICKET), "");
                part11List.add(line.getViewString(KEY_LST_LINE), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_RECEIVING_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_RECEIVING_PIECE_QTY), "0");

                if (lst_ReceivingListResultInput.getChecked(_lcm_lst_ReceivingListResultInput.getColumnIndex(KEY_LST_SHORTAGE)))
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                }
                //DFKLOOK:ここから修正
                part11List.add(line.getViewString(KEY_LST_RECEIVING_AREA), "");
                //DFKLOOK:ここまで修正

                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_ReceivingListResultInput.resetEditRow();
            _lcm_lst_ReceivingListResultInput.resetHighlight();

            // set input parameters.
            ReceivingListCompleteSCHParams inparam = new ReceivingListCompleteSCHParams();
            inparam.set(ReceivingListCompleteSCHParams.INITIAL_INPUT_RECEIVING_NUMBER, viewState.getObject(ViewStateKeys.INITIAL_INPUT_RECEIVING_NUMBER));
            inparam.set(ReceivingListCompleteSCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
            inparam.set(ReceivingListCompleteSCHParams.RECEIVING_PLAN_DATE, viewState.getObject(ViewStateKeys.RECEIVING_PLAN_DATE));
            inparam.set(ReceivingListCompleteSCHParams.SUPPLIER_CODE, viewState.getObject(ViewStateKeys.SUPPLIER_CODE));
            inparam.set(ReceivingListCompleteSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_ReceivingListResultInput.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ReceivingListResultInput.getNewLine();
                line.setValue(KEY_LST_SUPPLIER_CODE, outparam.get(ReceivingListCompleteSCHParams.SUPPLIER_CODE));
                line.setValue(KEY_LST_SUPPLIER_NAME, outparam.get(ReceivingListCompleteSCHParams.SUPPLIER_NAME));
                line.setValue(KEY_LST_TICKET, outparam.get(ReceivingListCompleteSCHParams.TICKET));
                line.setValue(KEY_LST_LINE, outparam.get(ReceivingListCompleteSCHParams.LINE));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(ReceivingListCompleteSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(ReceivingListCompleteSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT, outparam.get(ReceivingListCompleteSCHParams.LOT));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(ReceivingListCompleteSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(ReceivingListCompleteSCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(ReceivingListCompleteSCHParams.PLAN_PIECE_QTY));
                // DFKLOOK ここから
                if (chk_InitialInputReceivingNumber.getChecked())
                {
                    line.setValue(KEY_LST_RECEIVING_CASE_QTY,
                            outparam.get(ReceivingListCompleteSCHParams.RECEIVING_CASE_QTY));
                    line.setValue(KEY_LST_RECEIVING_PIECE_QTY,
                            outparam.get(ReceivingListCompleteSCHParams.RECEIVING_PIECE_QTY));
                }
                // DFKLOOK ここまで
                line.setValue(KEY_LST_RECEIVING_AREA, outparam.get(ReceivingListCompleteSCHParams.RECEIVING_AREA));
                line.setValue(KEY_LST_HIDDEN_JOBNO, outparam.get(ReceivingListCompleteSCHParams.HIDDEN_JOBNO));
                line.setValue(KEY_LST_HIDDEN_PLANUKEY, outparam.get(ReceivingListCompleteSCHParams.HIDDEN_PLANUKEY));
                lst_ReceivingListResultInput_SetLineToolTip(line);
                _lcm_lst_ReceivingListResultInput.add(line);

                // DFKLOOK ここから
                // Sets ListCell read-only
                lst_ReceivingListResultInput.setCellReadOnly(LST_RESULT_CASEQTY, true);
                lst_ReceivingListResultInput.setCellReadOnly(LST_RESULT_PIECEQTY, true);
                lst_ReceivingListResultInput.setCellEnabled(LST_SHORTAGECHK, false);
                lst_ReceivingListResultInput.setCellReadOnly(LST_LOTNO, true);
                lst_ReceivingListResultInput.setCellReadOnly(LST_AREA_NO, true);
                // DFKLOOK ここまで
            }

            // DFKLOOK ここから
            if (outparams.size() == 0)
            {
                // clear.
                btn_Complete.setEnabled(false);
                btn_ListClear.setEnabled(false);
                txt_LRReceivingPlanDate.setValue(null);
            }

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
    private void btn_ListClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_ReceivingListResultInput.clear();
        btn_Complete.setEnabled(false);
        btn_ListClear.setEnabled(false);
        txt_LRReceivingPlanDate.setValue(null);

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

    // DFKLOOK ここから
    /** 
     * Process when a checkBox in ListCell area is modified
     * @param e ActionEvent 
     */
    public void lst_ReceivingListResultInput_Change(ActionEvent e)
    {

        int row = lst_ReceivingListResultInput.getActiveRow();
        // Selects an operation row
        lst_ReceivingListResultInput.setCurrentRow(row);
        if (lst_ReceivingListResultInput.getChecked(LST_CHECK))
        {
            lst_ReceivingListResultInput.setCellReadOnly(LST_AREA_NO, false);
            if (_lcm_lst_ReceivingListResultInput.get(row).getNumberValue(KEY_LST_ENTERING_QTY).intValue() != 0)
            {
                lst_ReceivingListResultInput.setCellReadOnly(LST_RESULT_CASEQTY, false);
            }
            lst_ReceivingListResultInput.setCellReadOnly(LST_RESULT_PIECEQTY, false);
            lst_ReceivingListResultInput.setCellEnabled(LST_SHORTAGECHK, true);
            lst_ReceivingListResultInput.setCellReadOnly(LST_LOTNO, false);
        }
        else
        {
            lst_ReceivingListResultInput.setCellReadOnly(LST_AREA_NO, true);
            lst_ReceivingListResultInput.setCellReadOnly(LST_RESULT_CASEQTY, true);
            lst_ReceivingListResultInput.setCellReadOnly(LST_RESULT_PIECEQTY, true);
            lst_ReceivingListResultInput.setCellEnabled(LST_SHORTAGECHK, false);
            lst_ReceivingListResultInput.setCellReadOnly(LST_LOTNO, true);
        }
    }

    // DFKLOOK ここまで

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
