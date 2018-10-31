// $Id: DirectShippingBusiness.java 7390 2010-03-05 09:30:24Z okayama $
package jp.co.daifuku.wms.stock.display.directshipping;

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
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.stock.schedule.DirectShippingSCH;
import jp.co.daifuku.wms.stock.schedule.DirectShippingSCHParams;

/**
 * 入庫即出庫の画面処理を行います。
 *
 * @version $Revision: 7390 $, $Date: 2010-03-05 18:30:24 +0900 (金, 05 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class DirectShippingBusiness
        extends DirectShipping
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_NoPlanShipping(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY =
            new ListCellKey("LST_MODIFY", new StringCellColumn(), true, false);

    /** lst_NoPlanShipping(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL =
            new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_NoPlanShipping(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE =
            new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_NoPlanShipping(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME =
            new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_NoPlanShipping(LST_SHIPPING_AREA) */
    private static final ListCellKey KEY_LST_SHIPPING_AREA =
            new ListCellKey("LST_SHIPPING_AREA", new AreaCellColumn(), true, false);

    /** lst_NoPlanShipping(LST_LOT) */
    private static final ListCellKey KEY_LST_LOT = new ListCellKey("LST_LOT", new StringCellColumn(), true, false);

    /** lst_NoPlanShipping(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY =
            new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_NoPlanShipping(LST_SHIPPING_CASE_QTY) */
    private static final ListCellKey KEY_LST_SHIPPING_CASE_QTY =
            new ListCellKey("LST_SHIPPING_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_NoPlanShipping(LST_SHIPPING_PIECE_QTY) */
    private static final ListCellKey KEY_LST_SHIPPING_PIECE_QTY =
            new ListCellKey("LST_SHIPPING_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_NoPlanShipping(LST_UPC_CODE) */
    private static final ListCellKey KEY_LST_UPC_CODE =
            new ListCellKey("LST_UPC_CODE", new StringCellColumn(), true, false);

    /** lst_NoPlanShipping(LST_ITF) */
    private static final ListCellKey KEY_LST_ITF = new ListCellKey("LST_ITF", new StringCellColumn(), true, false);

    /** lst_NoPlanShipping keys */
    private static final ListCellKey[] LST_NOPLANSHIPPING_KEYS = {
            KEY_LST_MODIFY,
            KEY_LST_CANCEL,
            KEY_LST_ITEM_CODE,
            KEY_LST_SHIPPING_AREA,
            KEY_LST_LOT,
            KEY_LST_ENTERING_QTY,
            KEY_LST_SHIPPING_CASE_QTY,
            KEY_LST_UPC_CODE,
            KEY_LST_ITEM_NAME,
            KEY_LST_SHIPPING_PIECE_QTY,
            KEY_LST_ITF,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_ShippingArea */
    private WmsAreaPullDownModel _pdm_pul_ShippingArea;

    /** ListCellModel lst_NoPlanShipping */
    private ListCellModel _lcm_lst_NoPlanShipping;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public DirectShippingBusiness()
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
        if (eventSource.equals("btn_ItemCodeSearch_Click"))
        {
            // process call.
            btn_ItemCodeSearch_Click_DlgBack(dialogParams);
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
        if (eventSource.equals("btn_Input_Click"))
        {
            // process call.
            btn_Input_Click_Process(false);
        }
        // DFKLOOK add start
        else if (eventSource.equals("btn_ShippingStart_Click"))
        {
            btn_ShippingStart_Click_Process(eventSource);
        }
        // DFKLOOK add end
    }


    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ItemCodeSearch_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ItemCodeSearch_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Input_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Input_Click_Process(true);
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
    public void btn_ShippingStart_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ShippingStart_Click_Process(null);
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
    public void lst_NoPlanShipping_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_NoPlanShipping.getActiveCol();

        // choose process.
        if (_lcm_lst_NoPlanShipping.getColumnIndex(KEY_LST_MODIFY) == activeCol)
        {
            // process call.
            lst_Modify_Click_Process();
        }
        else if (_lcm_lst_NoPlanShipping.getColumnIndex(KEY_LST_CANCEL) == activeCol)
        {
            // process call.
            lst_Cancel_Click_Process();
        }
    }


    /**
     * Menu button click event handling.

     * @param e ActionEvent, event information
     * @throws Exception
     */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
        // Delete Connection From Session
        SessionUtil.deleteSession(getSession());
        // Forward to Submenu screen
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
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_ShippingArea.
        _pdm_pul_ShippingArea = new WmsAreaPullDownModel(pul_ShippingArea, locale, ui);

        // initialize lst_NoPlanShipping.
        _lcm_lst_NoPlanShipping = new ListCellModel(lst_NoPlanShipping, LST_NOPLANSHIPPING_KEYS, locale);
        _lcm_lst_NoPlanShipping.setToolTipVisible(KEY_LST_MODIFY, true);
        _lcm_lst_NoPlanShipping.setToolTipVisible(KEY_LST_CANCEL, true);
        _lcm_lst_NoPlanShipping.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_NoPlanShipping.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_NoPlanShipping.setToolTipVisible(KEY_LST_SHIPPING_AREA, true);
        _lcm_lst_NoPlanShipping.setToolTipVisible(KEY_LST_LOT, true);
        _lcm_lst_NoPlanShipping.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_NoPlanShipping.setToolTipVisible(KEY_LST_SHIPPING_CASE_QTY, true);
        _lcm_lst_NoPlanShipping.setToolTipVisible(KEY_LST_SHIPPING_PIECE_QTY, true);
        _lcm_lst_NoPlanShipping.setToolTipVisible(KEY_LST_UPC_CODE, true);
        _lcm_lst_NoPlanShipping.setToolTipVisible(KEY_LST_ITF, true);

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

            // load pul_ShippingArea.
            _pdm_pul_ShippingArea.init(conn, AreaType.FLOOR_AND_TEMP_AND_RECEIVE, StationType.ALL, "", false);

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
    private void lst_NoPlanShipping_SetLineToolTip(ListCellLine line)
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
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_ItemCode);

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
        DirectShippingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new DirectShippingSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            DirectShippingSCHParams inparam = new DirectShippingSCHParams();

            // DFKLOOK update start
            // SCH call.
            Params outparam = sch.initFind(inparam);

            // output display.
            viewState.setBoolean(ViewStateKeys.MASTER, outparam.getBoolean(DirectShippingSCHParams.MASTER_FLAG));

            // マスタパッケージありの場合は、入力不可項目を設定
            if (getViewState().getBoolean(ViewStateKeys.MASTER))
            {
                txt_ItemName.setReadOnly(true);
                txt_EnteringQty.setReadOnly(true);
                txt_JanCode.setReadOnly(true);
                txt_CaseITF.setReadOnly(true);
            }

            // DFKLOOK update end

            // clear.
            btn_Input.setEnabled(true);
            btn_Clear.setEnabled(true);
            btn_ShippingStart.setEnabled(false);
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
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ItemCodeSearch_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_ItemCodeSearch_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_ItemCodeSearch_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstItemParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemParams.ITEM_NAME));
        txt_EnteringQty.setValue(outparam.get(LstItemParams.ENTERING_QTY));
        txt_JanCode.setValue(outparam.get(LstItemParams.JAN));
        txt_CaseITF.setValue(outparam.get(LstItemParams.ITF));

        // set focus.
        setFocus(txt_ItemCode);

    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_Input_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        txt_ItemCode.validate(this, true);
        txt_ItemName.validate(this, false);
        txt_LotNo.validate(this, false);
        txt_EnteringQty.validate(this, false);
        txt_ShippingCaseQty.validate(this, false);
        txt_ShippingPieceQty.validate(this, false);
        txt_JanCode.validate(this, false);
        txt_CaseITF.validate(this, false);
        pul_ShippingArea.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        DirectShippingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new DirectShippingSCH(conn, this.getClass(), locale, ui);

            // DFKLOOK add start
            // マスタありの場合、補完する
            if (this.getViewState().getBoolean(ViewStateKeys.MASTER))
            {
                DirectShippingSCHParams masterParam = new DirectShippingSCHParams();

                // 荷主コード
                masterParam.set(DirectShippingSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                // 商品コード
                masterParam.set(DirectShippingSCHParams.ITEM_CODE, txt_ItemCode.getText());

                List<Params> outParam = sch.query(masterParam);

                if (outParam.size() > 0)
                {
                    // 商品名称
                    txt_ItemName.setText(outParam.get(0).getString(DirectShippingSCHParams.ITEM_NAME));
                    // ケース入数
                    txt_EnteringQty.setInt(outParam.get(0).getInt(DirectShippingSCHParams.ENTERING_QTY));
                    // JANコード
                    txt_JanCode.setText(outParam.get(0).getString(DirectShippingSCHParams.UPC_CODE));
                    // ケースITF
                    txt_CaseITF.setText(outParam.get(0).getString(DirectShippingSCHParams.CASE_ITF));
                }
            }

            // if pieceQty or caseQty is empty, put a 0 in the field.
            int caseQty = txt_ShippingCaseQty.getInt();
            int pieceQty = txt_ShippingPieceQty.getInt();
            if (pieceQty == 0)
            {
                txt_ShippingPieceQty.setText("" + pieceQty);
            }
            if (caseQty == 0)
            {
                txt_ShippingCaseQty.setText("" + caseQty);
            }
            // DFKLOOK add end
            // set input parameters.
            DirectShippingSCHParams inparam = new DirectShippingSCHParams();
            inparam.set(DirectShippingSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(DirectShippingSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(DirectShippingSCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(DirectShippingSCHParams.SHIPPING_AREA, _pdm_pul_ShippingArea.getSelectedValue());
            inparam.set(DirectShippingSCHParams.ENTERING_QTY, txt_EnteringQty.getValue());
            inparam.set(DirectShippingSCHParams.SHIPPING_CASE_QTY, txt_ShippingCaseQty.getValue());
            inparam.set(DirectShippingSCHParams.SHIPPING_PIECE_QTY, txt_ShippingPieceQty.getValue());

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_NoPlanShipping.size(); i++)
            {
                // exclusion editing row.
                if (_lcm_lst_NoPlanShipping.getEditRow() == i)
                {
                    continue;
                }

                ListCellLine line = _lcm_lst_NoPlanShipping.get(i);
                DirectShippingSCHParams lineparam = new DirectShippingSCHParams();
                lineparam.setProcessFlag(ProcessFlag.INPUT);
                lineparam.setRowIndex(i);
                lineparam.set(DirectShippingSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(DirectShippingSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(DirectShippingSCHParams.LOT_NO, line.getValue(KEY_LST_LOT));
                lineparam.set(DirectShippingSCHParams.SHIPPING_AREA, line.getValue(KEY_LST_SHIPPING_AREA));
                lineparam.set(DirectShippingSCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(DirectShippingSCHParams.SHIPPING_CASE_QTY, line.getValue(KEY_LST_SHIPPING_CASE_QTY));
                lineparam.set(DirectShippingSCHParams.SHIPPING_PIECE_QTY, line.getValue(KEY_LST_SHIPPING_PIECE_QTY));
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if (confirm && !sch.check(inparam, inparams))
            {
                if (StringUtil.isBlank(sch.getDispMessage()))
                {
                    // show message.
                    message.setMsgResourceKey(sch.getMessage());
                    return;
                }
                else
                {
                    // show confirm message.
                    this.setConfirm(sch.getDispMessage(), false, true);
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Input_Click");
                    return;
                }
            }

            message.setMsgResourceKey(sch.getMessage());

            // output display.
            int editRow = _lcm_lst_NoPlanShipping.getEditRow();
            Boolean newline = ListCellModel.EDIT_ROW_NONE == editRow;
            ListCellLine line = newline ? _lcm_lst_NoPlanShipping.getNewLine()
                                       : _lcm_lst_NoPlanShipping.get(editRow);
            line.setValue(KEY_LST_ITEM_CODE, txt_ItemCode.getValue());
            line.setValue(KEY_LST_ITEM_NAME, txt_ItemName.getValue());
            line.setValue(KEY_LST_LOT, txt_LotNo.getValue());
            line.setValue(KEY_LST_SHIPPING_AREA, _pdm_pul_ShippingArea.getSelectedValue());
            line.setValue(KEY_LST_ENTERING_QTY, txt_EnteringQty.getValue());
            line.setValue(KEY_LST_SHIPPING_CASE_QTY, txt_ShippingCaseQty.getValue());
            line.setValue(KEY_LST_SHIPPING_PIECE_QTY, txt_ShippingPieceQty.getValue());
            line.setValue(KEY_LST_UPC_CODE, txt_JanCode.getValue());
            line.setValue(KEY_LST_ITF, txt_CaseITF.getValue());

            // add new row or update editing row.
            lst_NoPlanShipping_SetLineToolTip(line);
            if (newline)
            {
                _lcm_lst_NoPlanShipping.add(line, true);
            }
            else
            {
                _lcm_lst_NoPlanShipping.set(editRow, line);
            }

            // reset editing row.
            _lcm_lst_NoPlanShipping.resetEditRow();
            _lcm_lst_NoPlanShipping.resetHighlight();

            // clear.
            btn_ShippingStart.setEnabled(true);
            btn_ListClear.setEnabled(true);

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
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        txt_EnteringQty.setValue(null);
        txt_JanCode.setValue(null);
        txt_CaseITF.setValue(null);
        txt_LotNo.setValue(null);
        _pdm_pul_ShippingArea.setSelectedValue(null);
        txt_ShippingCaseQty.setValue(null);
        txt_ShippingPieceQty.setValue(null);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_ShippingStart_Click_Process(String eventSource)
            throws Exception
    {
        //DFKLOOK add start glm???
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_NoPlanShipping.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_NoPlanShipping.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003001");
            return;
        }

        if (StringUtil.isBlank(eventSource))
        {
            // show confirm message = 開始しますか？
            this.setConfirm("MSG-T0056", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_ShippingStart_Click");
            return;         
        }
        
        //DFKLOOK add end
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        DirectShippingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new DirectShippingSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_NoPlanShipping.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_NoPlanShipping.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                DirectShippingSCHParams lineparam = new DirectShippingSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(DirectShippingSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(DirectShippingSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(DirectShippingSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(DirectShippingSCHParams.LOT_NO, line.getValue(KEY_LST_LOT));
                lineparam.set(DirectShippingSCHParams.SHIPPING_AREA, line.getValue(KEY_LST_SHIPPING_AREA));
                //DFKLOOK add start
                lineparam.set(DirectShippingSCHParams.SHIPPING_LOCATION, WmsParam.DEFAULT_LOCATION_NO);//audit trail points to 99999999
                //DFKLOOK add end
                lineparam.set(DirectShippingSCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(DirectShippingSCHParams.SHIPPING_CASE_QTY, line.getValue(KEY_LST_SHIPPING_CASE_QTY));
                lineparam.set(DirectShippingSCHParams.SHIPPING_PIECE_QTY, line.getValue(KEY_LST_SHIPPING_PIECE_QTY));
                lineparam.set(DirectShippingSCHParams.UPC_CODE, line.getValue(KEY_LST_UPC_CODE));
                lineparam.set(DirectShippingSCHParams.CASE_ITF, line.getValue(KEY_LST_ITF));
                lineparam.set(DirectShippingSCHParams.HARD_WARE_TYPE, SystemDefine.HARDWARE_TYPE_LIST);
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
                _lcm_lst_NoPlanShipping.resetEditRow();
                _lcm_lst_NoPlanShipping.resetHighlight();
                _lcm_lst_NoPlanShipping.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_NoPlanShipping.size(); i++)
            {
                ListCellLine line = _lcm_lst_NoPlanShipping.get(i);
                lst_NoPlanShipping.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_SHIPPING_AREA), "");
                part11List.add(line.getViewString(KEY_LST_LOT), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "");
                part11List.add(line.getViewString(KEY_LST_SHIPPING_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_SHIPPING_PIECE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_UPC_CODE), "");
                part11List.add(line.getViewString(KEY_LST_ITF), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_NoPlanShipping.resetEditRow();
            _lcm_lst_NoPlanShipping.resetHighlight();

            // clear.
            _lcm_lst_NoPlanShipping.clear();
            btn_ShippingStart.setEnabled(false);
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
        _lcm_lst_NoPlanShipping.clear();
        btn_ShippingStart.setEnabled(false);
        btn_ListClear.setEnabled(false);

    }

    /**
     *
     * @throws Exception
     */
    private void lst_Modify_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_NoPlanShipping.getActiveRow();
        lst_NoPlanShipping.setCurrentRow(row);
        ListCellLine line = _lcm_lst_NoPlanShipping.get(row);

        // output display.
        txt_ItemCode.setValue(line.getValue(KEY_LST_ITEM_CODE));
        txt_ItemName.setValue(line.getValue(KEY_LST_ITEM_NAME));
        txt_LotNo.setValue(line.getValue(KEY_LST_LOT));
        _pdm_pul_ShippingArea.setSelectedValue(line.getValue(KEY_LST_SHIPPING_AREA));
        txt_EnteringQty.setValue(line.getValue(KEY_LST_ENTERING_QTY));
        txt_ShippingCaseQty.setValue(line.getValue(KEY_LST_SHIPPING_CASE_QTY));
        txt_ShippingPieceQty.setValue(line.getValue(KEY_LST_SHIPPING_PIECE_QTY));
        txt_JanCode.setValue(line.getValue(KEY_LST_UPC_CODE));
        txt_CaseITF.setValue(line.getValue(KEY_LST_ITF));

        // highligit active row.
        _lcm_lst_NoPlanShipping.resetHighlight();
        _lcm_lst_NoPlanShipping.addHighlight(row);
        _lcm_lst_NoPlanShipping.setEditRow(row);

    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_NoPlanShipping.getActiveRow();
        lst_NoPlanShipping.setCurrentRow(row);

        // reset editing row.
        lst_NoPlanShipping.removeRow(row);
        _lcm_lst_NoPlanShipping.resetEditRow();
        _lcm_lst_NoPlanShipping.resetHighlight();

        // DFKLOOK add start
        // reset clear and add buttons if there are no items in the list
        if (_lcm_lst_NoPlanShipping.size() == 0)
        {
            btn_ListClear_Click_Process();
        }
        // DFKLOOK add end
    }

    /**
     * Displays Screen title.
     *
     * @throws Exception
     */
    private void setTitle()
            throws Exception
    {
        // get Menu parameters from httpRequest
        String menuparam = this.getHttpRequest().getParameter(Constants.MENUPARAM);
        if (menuparam != null)
        {
            String title = CollectionUtils.getMenuParam(0, menuparam);
            String functionID = CollectionUtils.getMenuParam(1, menuparam);
            String menuID = CollectionUtils.getMenuParam(2, menuparam);

            // update Title information to view state to display when screen is refreshed
            viewState.setString(Constants.M_TITLE_KEY, title);
            viewState.setString(Constants.M_FUNCTIONID_KEY, functionID);
            viewState.setString(Constants.M_MENUID_KEY, menuID);

            lbl_SettingName.setResourceKey(title);
        }
        else if (this.getTitleResourceKey() != null && !this.getTitleResourceKey().equals(""))
        {
            // If there is no title , get the title key from ResourceKey (page.xml)
            // this is used for POPUP title keys
            lbl_SettingName.setResourceKey(this.getTitleResourceKey());
        }
        else if (viewState.getString(Constants.M_TITLE_KEY) != null)
        {
            // Set screen title
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
