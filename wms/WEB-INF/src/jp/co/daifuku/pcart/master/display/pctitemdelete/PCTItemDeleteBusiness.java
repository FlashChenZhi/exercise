// $Id: PCTItemDeleteBusiness.java 4256 2009-05-12 04:47:22Z okayama $
package jp.co.daifuku.pcart.master.display.pctitemdelete;

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
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.pcart.base.util.PCTLogWriter;
import jp.co.daifuku.pcart.master.listbox.pctitem.PCTLstItemParams;
import jp.co.daifuku.pcart.master.schedule.PCTItemDeleteSCH;
import jp.co.daifuku.pcart.master.schedule.PCTItemDeleteSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * 商品マスタ削除の画面処理を行います。
 *
 * @version $Revision: 4256 $, $Date: 2009-05-12 13:47:22 +0900 (火, 12 5 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class PCTItemDeleteBusiness
        extends PCTItemDelete
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_PCTItemMasterDelete(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_PCTItemMasterDelete(LST_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_LST_CONSIGNOR_CODE =
            new ListCellKey("LST_CONSIGNOR_CODE", new StringCellColumn(), true, false);

    /** lst_PCTItemMasterDelete(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE =
            new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_PCTItemMasterDelete(LST_LOT_QTY) */
    private static final ListCellKey KEY_LST_LOT_QTY =
            new ListCellKey("LST_LOT_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTItemMasterDelete(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME =
            new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_PCTItemMasterDelete(LST_LAST_UPDATE_DATE) */
    private static final ListCellKey KEY_LST_LAST_UPDATE_DATE =
            new ListCellKey("LST_LAST_UPDATE_DATE", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_SEC), true, false);

    /** lst_PCTItemMasterDelete kyes */
    private static final ListCellKey[] LST_PCTITEMMASTERDELETE_KEYS = {
            KEY_LST_SELECT,
            KEY_LST_CONSIGNOR_CODE,
            KEY_LST_ITEM_CODE,
            KEY_LST_LOT_QTY,
            KEY_LST_ITEM_NAME,
            KEY_LST_LAST_UPDATE_DATE,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // DFKLOOK:ここから修正
    /**
     * 操作区分:削除
     */
    protected static final String OPELOG_DELETE = "3";

    // DFKLOOK:ここまで修正

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_PCTItemMasterDelete */
    private ListCellModel _lcm_lst_PCTItemMasterDelete;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTItemDeleteBusiness()
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
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        // get event source.
        DialogParameters dialogParams = ((DialogEvent)e).getDialogParameters();
        String eventSource = dialogParams.getParameter(_KEY_POPUPSOURCE);
        if (StringUtil.isBlank(eventSource))
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_SearchFromItemCode_Click"))
        {
            // process call.
            btn_SearchFromItemCode_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_SearchToItemCode_Click"))
        {
            // process call.
            btn_SearchToItemCode_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchFromItemCode_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchFromItemCode_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchToItemCode_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchToItemCode_Click_Process();
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
    public void btn_Save_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Save_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Load_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Load_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Delete_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Delete_Click_Process();
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
    public void btn_AllCheck_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllCheck_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllCheckClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllCheckClear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_PCTItemMasterDelete_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_PCTItemMasterDelete_ColumClick(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchConsignor_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchItemCode_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void chk_ItemCodeRange_Change(ActionEvent e)
            throws Exception
    {
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
     * オペレーションログ情報の書込み処理を行います <BR>
     * @param   conn            データベースコネクション
     * @param   operationKind  操作区分
     * @param   flag 区分
     * @throws Exception 全ての例外を報告します。
     */
    protected void log_write(Connection conn, int operationKind, String flag)
            throws Exception
    {
        for (int i = 1; i < _lcm_lst_PCTItemMasterDelete.size(); i++)
        {
            // exclusion unmodified row.
            ListCellLine line = _lcm_lst_PCTItemMasterDelete.get(i);

            if (!(line.isAppend() || line.isEdited()))
            {
                continue;
            }

            // オペレーションログ出力
            List<String> itemLog = new ArrayList<String>();

            // 操作区分
            itemLog.add(flag);

            // 荷主コード
            itemLog.add(line.getStringValue(KEY_LST_CONSIGNOR_CODE));
            // 商品コード
            itemLog.add(line.getStringValue(KEY_LST_ITEM_CODE));
            // ロット入数
            itemLog.add(line.getStringValue(KEY_LST_LOT_QTY));
            // 商品名称
            itemLog.add(line.getStringValue(KEY_LST_ITEM_NAME));
            // 最終更新日時
            itemLog.add(line.getStringValue(KEY_LST_LAST_UPDATE_DATE));

            // ログ出力
            PCTLogWriter opeLogWriter = new PCTLogWriter(conn);
            opeLogWriter.createOperationLog((DfkUserInfo)getUserInfo(), operationKind, itemLog);
        }
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

        // initialize lst_PCTItemMasterDelete.
        _lcm_lst_PCTItemMasterDelete = new ListCellModel(lst_PCTItemMasterDelete, LST_PCTITEMMASTERDELETE_KEYS, locale);
        _lcm_lst_PCTItemMasterDelete.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_PCTItemMasterDelete.setToolTipVisible(KEY_LST_CONSIGNOR_CODE, false);
        _lcm_lst_PCTItemMasterDelete.setToolTipVisible(KEY_LST_ITEM_CODE, false);
        _lcm_lst_PCTItemMasterDelete.setToolTipVisible(KEY_LST_LOT_QTY, false);
        _lcm_lst_PCTItemMasterDelete.setToolTipVisible(KEY_LST_ITEM_NAME, false);
        _lcm_lst_PCTItemMasterDelete.setToolTipVisible(KEY_LST_LAST_UPDATE_DATE, false);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTItemMasterDelete_SetLineToolTip(ListCellLine line)
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
        setFocus(txt_SearchDate);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_Delete.setEnabled(false);
        btn_ListClear.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        _lcm_lst_PCTItemMasterDelete.clear();

    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchFromItemCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        PCTLstItemParams inparam = new PCTLstItemParams();
        inparam.set(PCTLstItemParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(PCTLstItemParams.ITEM_CODE, txt_FromItemCode.getValue());
        inparam.set(PCTLstItemParams.LAST_UPDATE_DATE, txt_SearchDate.getValue());
        inparam.set(PCTLstItemParams.SEARCHTABLE, InParameter.SEARCH_TABLE_MASTER);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchFromItemCode_Click");
        redirect("/pcart/master/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchFromItemCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        PCTLstItemParams outparam = new PCTLstItemParams(dialogParams);
        txt_FromItemCode.setValue(outparam.get(PCTLstItemParams.ITEM_CODE));

        // set focus.
        setFocus(txt_FromItemCode);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchToItemCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        PCTLstItemParams inparam = new PCTLstItemParams();
        inparam.set(PCTLstItemParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(PCTLstItemParams.ITEM_CODE, txt_ToItemCode.getValue());
        inparam.set(PCTLstItemParams.LAST_UPDATE_DATE, txt_SearchDate.getValue());
        inparam.set(PCTLstItemParams.SEARCHTABLE, InParameter.SEARCH_TABLE_MASTER);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchToItemCode_Click");
        redirect("/pcart/master/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchToItemCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        PCTLstItemParams outparam = new PCTLstItemParams(dialogParams);
        txt_ToItemCode.setValue(outparam.get(PCTLstItemParams.ITEM_CODE));

        // set focus.
        setFocus(txt_ToItemCode);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_SearchDate.validate(this, true);
        txt_ConsignorCode.validate(this, false);
        txt_FromItemCode.validate(this, false);
        txt_ToItemCode.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTItemDeleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTItemDeleteSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTItemDeleteSCHParams inparam = new PCTItemDeleteSCHParams();
            inparam.set(PCTItemDeleteSCHParams.LAST_UPDATE_DATE, txt_SearchDate.getValue());
            inparam.set(PCTItemDeleteSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTItemDeleteSCHParams.FROM_ITEM_CODE, txt_FromItemCode.getValue());
            inparam.set(PCTItemDeleteSCHParams.TO_ITEM_CODE, txt_ToItemCode.getValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTItemMasterDelete.clear();

            // DFKLOOK:ここから修正
            if (outparams == null || outparams.size() <= 0)
            {
                btn_Delete.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_ListClear.setEnabled(false);
                return;
            }
            // DFKLOOK:ここまで修正

            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTItemMasterDelete.getNewLine();
                line.setValue(KEY_LST_CONSIGNOR_CODE, outparam.get(PCTItemDeleteSCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(PCTItemDeleteSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(PCTItemDeleteSCHParams.LOT_QTY));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(PCTItemDeleteSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LAST_UPDATE_DATE, outparam.get(PCTItemDeleteSCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.VS_LAST_UPDATE_DATE_DATE, txt_SearchDate.getValue());
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                viewState.setObject(ViewStateKeys.VS_FROM_ITEM_CODE, txt_FromItemCode.getValue());
                viewState.setObject(ViewStateKeys.VS_TO_ITEM_CODE, txt_ToItemCode.getValue());
                lst_PCTItemMasterDelete_SetLineToolTip(line);
                _lcm_lst_PCTItemMasterDelete.add(line);
            }

            // clear.
            btn_Delete.setEnabled(true);
            btn_ListClear.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);

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
        txt_SearchDate.setValue(null);
        txt_ConsignorCode.setValue(null);
        txt_FromItemCode.setValue(null);
        txt_ToItemCode.setValue(null);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Save_Click_Process()
            throws Exception
    {
        // forward.
        forward("/pcart/master/pctitemdelete/PCTItemDelete3.do");
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Load_Click_Process()
            throws Exception
    {
        // forward.
        forward("/pcart/master/pctitemdelete/PCTItemDelete2.do");
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Delete_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTItemDeleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTItemDeleteSCH(conn, this.getClass(), locale, ui);

            // DFKLOOK:ここから修正
            // input validation.
            int set_count = 0;
            for (int i = 1; i <= _lcm_lst_PCTItemMasterDelete.size(); i++)
            {
                ListCellLine checkline = _lcm_lst_PCTItemMasterDelete.get(i);

                if (checkline.isAppend() || checkline.isEdited())
                {
                    set_count += 1;
                }
            }
            if (set_count == 0)
            {
                // 6123103 = データを選択してください。
                message.setMsgResourceKey("6123103");
                return;
            }
            // DFKLOOK:ここまで修正

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_PCTItemMasterDelete.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_PCTItemMasterDelete.get(i);

                // DFKLOOK:ここから修正
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }
                // DFKLOOK:ここまで修正

                PCTItemDeleteSCHParams lineparam = new PCTItemDeleteSCHParams();
                lineparam.setProcessFlag(ProcessFlag.DELETE);
                lineparam.setRowIndex(i);
                lineparam.set(PCTItemDeleteSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                lineparam.set(PCTItemDeleteSCHParams.CONSIGNOR_CODE, line.getValue(KEY_LST_CONSIGNOR_CODE));
                lineparam.set(PCTItemDeleteSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(PCTItemDeleteSCHParams.LOT_QTY, line.getValue(KEY_LST_LOT_QTY));
                lineparam.set(PCTItemDeleteSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(PCTItemDeleteSCHParams.LAST_UPDATE_DATE, line.getValue(KEY_LST_LAST_UPDATE_DATE));
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

                // reset editing row or highligiting error row.
                _lcm_lst_PCTItemMasterDelete.resetEditRow();
                _lcm_lst_PCTItemMasterDelete.resetHighlight();
                _lcm_lst_PCTItemMasterDelete.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // DFKLOOK:ここから修正
            // オペレーションログ出力
            log_write(conn, EmConstants.OPELOG_CLASS_DELETE, OPELOG_DELETE);
            // DFKLOOK:ここまで修正

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_PCTItemMasterDelete.resetEditRow();
            _lcm_lst_PCTItemMasterDelete.resetHighlight();

            // DFKLOOK:ここから修正
            // set input parameters.
            PCTItemDeleteSCHParams inparam = new PCTItemDeleteSCHParams();
            inparam.set(PCTItemDeleteSCHParams.LAST_UPDATE_DATE,
                    viewState.getObject(ViewStateKeys.VS_LAST_UPDATE_DATE_DATE));
            inparam.set(PCTItemDeleteSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTItemDeleteSCHParams.FROM_ITEM_CODE, viewState.getObject(ViewStateKeys.VS_FROM_ITEM_CODE));
            inparam.set(PCTItemDeleteSCHParams.TO_ITEM_CODE, viewState.getObject(ViewStateKeys.VS_TO_ITEM_CODE));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            _lcm_lst_PCTItemMasterDelete.clear();

            if (outparams == null || outparams.size() <= 0)
            {
                // clear.
                btn_Delete.setEnabled(false);
                btn_ListClear.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
            }

            // output display.
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTItemMasterDelete.getNewLine();
                line.setValue(KEY_LST_CONSIGNOR_CODE, outparam.get(PCTItemDeleteSCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(PCTItemDeleteSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(PCTItemDeleteSCHParams.LOT_QTY));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(PCTItemDeleteSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LAST_UPDATE_DATE, outparam.get(PCTItemDeleteSCHParams.LAST_UPDATE_DATE));
                _lcm_lst_PCTItemMasterDelete.add(line);
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
     *
     * @throws Exception
     */
    private void btn_ListClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_PCTItemMasterDelete.clear();
        btn_Delete.setEnabled(false);
        btn_ListClear.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllCheck_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_PCTItemMasterDelete.size(); i++)
        {
            ListCellLine clearline = _lcm_lst_PCTItemMasterDelete.get(i);
            lst_PCTItemMasterDelete.setCurrentRow(i);
            clearline.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_PCTItemMasterDelete_SetLineToolTip(clearline);
            _lcm_lst_PCTItemMasterDelete.set(i, clearline);
        }

    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllCheckClear_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_PCTItemMasterDelete.size(); i++)
        {
            ListCellLine clearline = _lcm_lst_PCTItemMasterDelete.get(i);
            lst_PCTItemMasterDelete.setCurrentRow(i);
            clearline.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_PCTItemMasterDelete_SetLineToolTip(clearline);
            _lcm_lst_PCTItemMasterDelete.set(i, clearline);
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
