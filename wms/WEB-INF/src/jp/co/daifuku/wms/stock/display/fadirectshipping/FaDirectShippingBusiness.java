// $Id: Business_ja.java 109 2008-10-06 10:49:13Z nagao $
package jp.co.daifuku.wms.stock.display.fadirectshipping;

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
import jp.co.daifuku.bluedog.model.table.ListCellModel;
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
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.listbox.itemname.LstItemNameParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.stock.display.fadirectshipping.FaDirectShipping;
import jp.co.daifuku.wms.stock.schedule.FaDirectShippingSCH;
import jp.co.daifuku.wms.stock.schedule.FaDirectShippingSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: nagao $
 */
public class FaDirectShippingBusiness
        extends FaDirectShipping
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_FaNoPlanShipping(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY =
            new ListCellKey("LST_MODIFY", new StringCellColumn(), true, false);

    /** lst_FaNoPlanShipping(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL =
            new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_FaNoPlanShipping(LST_NO) */
    private static final ListCellKey KEY_LST_NO =
            new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_FaNoPlanShipping(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE =
            new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_FaNoPlanShipping(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME =
            new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_FaNoPlanShipping(LST_LOT) */
    private static final ListCellKey KEY_LST_LOT =
            new ListCellKey("LST_LOT", new StringCellColumn(), true, false);

    /** lst_FaNoPlanShipping(LST_AREA) */
    private static final ListCellKey KEY_LST_AREA =
            new ListCellKey("LST_AREA", new AreaCellColumn(), true, false);

    /** lst_FaNoPlanShipping(LST_WORK_QTY) */
    private static final ListCellKey KEY_LST_WORK_QTY =
            new ListCellKey("LST_WORK_QTY", new NumberCellColumn(0), true, false);

    /** lst_FaNoPlanShipping keys */
    private static final ListCellKey[] LST_FANOPLANSHIPPING_KEYS = {
        KEY_LST_MODIFY,
        KEY_LST_CANCEL,
        KEY_LST_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_ITEM_NAME,
        KEY_LST_LOT,
        KEY_LST_AREA,
        KEY_LST_WORK_QTY,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_ShippingArea */
    private WmsAreaPullDownModel _pdm_pul_ShippingArea;

    /** ListCellModel lst_FaNoPlanShipping */
    private ListCellModel _lcm_lst_FaNoPlanShipping;
    
    // DFKLOOK start
    /** 入力商品チェック済みフラグ */
    private boolean _isItemCheck = false;
    // DFKLOOK end

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaDirectShippingBusiness()
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
        else if (eventSource.equals("btn_ItemNameSearch_Click"))
        {
            // process call.
            btn_ItemNameSearch_Click_DlgBack(dialogParams);
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
        if (eventSource.startsWith("btn_Input_Click"))
        {
            // process call.
            btn_Input_Click_Process(eventSource);
        }
        // DFKLOOK start
        else if (eventSource.startsWith("btn_ShippingStart_Click"))
        {
            // process call.
            btn_ShippingStart_Click_Process(eventSource);
        }
        else if (eventSource.startsWith("checkItem"))
        {
            // process call.
            _isItemCheck = true;
            btn_Input_Click_Process(eventSource);
        }
        // DFKLOOK end
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemCode_EnterKey(ActionEvent e)
            throws Exception
    {
        // DFKLOOK start
        if (StringUtil.isBlank(txt_ItemCode.getValue()))
        {
            setFocus(txt_ItemName);
            return;
        }

        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            Item item = ItemController.getItemInfo(txt_ItemCode.getText(), WmsParam.DEFAULT_CONSIGNOR_CODE, conn);

            if (item == null)
            {
                // 6023021 = 商品コードがマスタに登録されていません。
                message.setMsgResourceKey("6023021");
                setFocus(txt_ItemCode);
                return;
            }

            // output display.
            txt_ItemName.setValue(item.getItemName());

            // set focus.
            setFocus(txt_LotNo);
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
        // DFKLOOK end

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
    public void btn_ItemNameSearch_Click(ActionEvent e)
            throws Exception
    {
                // process call.
                btn_ItemNameSearch_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Input_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK start
        // process call.
        btn_Input_Click_Process(null);
        // DFKLOOK end
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
        // DFKLOOK start
        // process call.
        btn_ShippingStart_Click_Process(null);
        // DFKLOOK end
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
    public void lst_FaNoPlanShipping_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_FaNoPlanShipping.getActiveCol();

        // choose process.
        if (_lcm_lst_FaNoPlanShipping.getColumnIndex(KEY_LST_MODIFY) == activeCol)
        {
            // process call.
            lst_Modify_Click_Process();
        }
        else if (_lcm_lst_FaNoPlanShipping.getColumnIndex(KEY_LST_CANCEL) == activeCol)
        {
            // process call.
            lst_Cancel_Click_Process();
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
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_ShippingArea.
        _pdm_pul_ShippingArea = new WmsAreaPullDownModel(pul_ShippingArea, locale, ui);

        // initialize lst_FaNoPlanShipping.
        _lcm_lst_FaNoPlanShipping = new ListCellModel(lst_FaNoPlanShipping, LST_FANOPLANSHIPPING_KEYS, locale);
        _lcm_lst_FaNoPlanShipping.setToolTipVisible(KEY_LST_MODIFY, false);
        _lcm_lst_FaNoPlanShipping.setToolTipVisible(KEY_LST_CANCEL, false);
        _lcm_lst_FaNoPlanShipping.setToolTipVisible(KEY_LST_NO, false);
        _lcm_lst_FaNoPlanShipping.setToolTipVisible(KEY_LST_ITEM_CODE, false);
        _lcm_lst_FaNoPlanShipping.setToolTipVisible(KEY_LST_ITEM_NAME, false);
        _lcm_lst_FaNoPlanShipping.setToolTipVisible(KEY_LST_LOT, false);
        _lcm_lst_FaNoPlanShipping.setToolTipVisible(KEY_LST_AREA, false);
        _lcm_lst_FaNoPlanShipping.setToolTipVisible(KEY_LST_WORK_QTY, false);

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
            _pdm_pul_ShippingArea.init(conn, AreaType.NOT_MOVING_TERM, StationType.ALL, "", false);

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
    private void lst_FaNoPlanShipping_SetLineToolTip(ListCellLine line)
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
        // clear.
        btn_ShippingStart.setEnabled(false);
        btn_ListClear.setEnabled(false);
        _lcm_lst_FaNoPlanShipping.clear();

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

        // set focus.
        setFocus(txt_ItemCode);

    }

    /**
    *
    * @throws Exception
    */
   private void btn_ItemNameSearch_Click_Process()
           throws Exception
   {
       // dialog parameters set.
       LstItemNameParams inparam = new LstItemNameParams();
        inparam.set(LstItemNameParams.ITEM_NAME, txt_ItemName.getValue());
        inparam.set(LstItemNameParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

       // show dialog.
       ForwardParameters forwardParam = inparam.toForwardParameters();
       forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_ItemNameSearch_Click");
       redirect("/base/listbox/itemname/LstItemName.do", forwardParam, "/Progress.do");
   }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_ItemNameSearch_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemNameParams outparam = new LstItemNameParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstItemNameParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemNameParams.ITEM_NAME));

        // set focus.
        setFocus(txt_ItemName);

    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    // DFKLOOK start
    private void btn_Input_Click_Process(String eventSource)
            throws Exception
    // DFKLOOK end
    {
        // input validation.
        txt_ItemCode.validate(this, true);
        txt_ItemName.validate(this, false);
        txt_LotNo.validate(this, false);
        pul_ShippingArea.validate(this, true);
        txt_StorageQty.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaDirectShippingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaDirectShippingSCH(conn, this.getClass(), locale, ui);

            // DFKLOOK start
            // 入力商品情報のチェック
            if ((StringUtil.isBlank(eventSource) || !eventSource.equals("btn_Input_Click_SCH")) && !checkItem(conn))
            {
                return;
            }
            // DFKLOOK end
            
            // set input parameters.
            FaDirectShippingSCHParams inparam = new FaDirectShippingSCHParams();
            inparam.set(FaDirectShippingSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(FaDirectShippingSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(FaDirectShippingSCHParams.ITEM_NAME, txt_ItemName.getValue());
            inparam.set(FaDirectShippingSCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(FaDirectShippingSCHParams.SHIPPING_AREA, _pdm_pul_ShippingArea.getSelectedValue());
            inparam.set(FaDirectShippingSCHParams.RESULT, txt_StorageQty.getValue());
            
            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_FaNoPlanShipping.size(); i++)
            {
                // exclusion editing row.
                if (_lcm_lst_FaNoPlanShipping.getEditRow() == i)
                {
                    continue;
                }

                ListCellLine line = _lcm_lst_FaNoPlanShipping.get(i);
                FaDirectShippingSCHParams lineparam = new FaDirectShippingSCHParams();
                lineparam.setProcessFlag(ProcessFlag.INPUT);
                lineparam.setRowIndex(i);
                lineparam.set(FaDirectShippingSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(FaDirectShippingSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(FaDirectShippingSCHParams.LOT_NO, line.getValue(KEY_LST_LOT));
                lineparam.set(FaDirectShippingSCHParams.SHIPPING_AREA, line.getValue(KEY_LST_AREA));
                lineparam.set(FaDirectShippingSCHParams.RESULT, line.getIndex(KEY_LST_WORK_QTY));
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if ((StringUtil.isBlank(eventSource) || eventSource.equals("checkItem")) && !sch.check(inparam, inparams))
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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Input_Click_SCH");
                    return;
                }
            }

            message.setMsgResourceKey(sch.getMessage());

            // output display.
            int editRow = _lcm_lst_FaNoPlanShipping.getEditRow();
            Boolean newline = ListCellModel.EDIT_ROW_NONE == editRow;
            ListCellLine line = newline ? _lcm_lst_FaNoPlanShipping.getNewLine()
                                        : _lcm_lst_FaNoPlanShipping.get(editRow);
            line.setValue(KEY_LST_NO, lst_FaNoPlanShipping.getCurrentRow() + 1);
            line.setValue(KEY_LST_ITEM_CODE, txt_ItemCode.getValue());
            line.setValue(KEY_LST_ITEM_NAME, inparam.getString(FaDirectShippingSCHParams.ITEM_NAME));
            line.setValue(KEY_LST_LOT, txt_LotNo.getValue());
            line.setValue(KEY_LST_AREA, _pdm_pul_ShippingArea.getSelectedValue());
            line.setValue(KEY_LST_WORK_QTY, txt_StorageQty.getValue());

            // add new row or update editing row.
            lst_FaNoPlanShipping_SetLineToolTip(line);
            if (newline)
            {
                _lcm_lst_FaNoPlanShipping.add(line, true);
            }
            else
            {
                _lcm_lst_FaNoPlanShipping.set(editRow, line);
            }

            // reset editing row.
            _lcm_lst_FaNoPlanShipping.resetEditRow();
            _lcm_lst_FaNoPlanShipping.resetHighlight();

            // clear.
            btn_ShippingStart.setEnabled(true);
            btn_ListClear.setEnabled(true);

            // set focus.
            setFocus(txt_ItemCode);

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
        txt_LotNo.setValue(null);
        txt_StorageQty.setValue(null);
        _pdm_pul_ShippingArea.setSelectedValue(null);
        txt_StorageQty.setValue(null);

    }

    /**
     *
     * @throws Exception
     */
    // DFKLOOK start
    private void btn_ShippingStart_Click_Process(String eventSource)
            throws Exception
    // DFKLOOK end
    {
        //DFKLOOK add start
        if (StringUtil.isBlank(eventSource))
        {
            // show confirm message. 設定しますか？
            this.setConfirm("MSG-W9000", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_ShippingStart_Click");
            return;
        }
        //DFKLOOK add end
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaDirectShippingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaDirectShippingSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_FaNoPlanShipping.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_FaNoPlanShipping.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }
                FaDirectShippingSCHParams lineparam = new FaDirectShippingSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(FaDirectShippingSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(FaDirectShippingSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(FaDirectShippingSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(FaDirectShippingSCHParams.LOT_NO, line.getValue(KEY_LST_LOT));
                lineparam.set(FaDirectShippingSCHParams.HARD_WARE_TYPE, "");
                lineparam.set(FaDirectShippingSCHParams.SHIPPING_AREA, line.getStringValue(KEY_LST_AREA));
                lineparam.set(FaDirectShippingSCHParams.RESULT, line.getValue(KEY_LST_WORK_QTY));
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
                _lcm_lst_FaNoPlanShipping.resetEditRow();
                _lcm_lst_FaNoPlanShipping.resetHighlight();
                _lcm_lst_FaNoPlanShipping.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_FaNoPlanShipping.size(); i++)
            {
                ListCellLine line = _lcm_lst_FaNoPlanShipping.get(i);
                lst_FaNoPlanShipping.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT), "");
                part11List.add(line.getViewString(KEY_LST_AREA), "");
                part11List.add(line.getViewString(KEY_LST_WORK_QTY), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_FaNoPlanShipping.resetEditRow();
            _lcm_lst_FaNoPlanShipping.resetHighlight();

            // clear.
            _lcm_lst_FaNoPlanShipping.clear();
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
        _lcm_lst_FaNoPlanShipping.clear();
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
        int row = lst_FaNoPlanShipping.getActiveRow();
        lst_FaNoPlanShipping.setCurrentRow(row);
        ListCellLine line = _lcm_lst_FaNoPlanShipping.get(row);

        // output display.
        txt_ItemCode.setValue(line.getValue(KEY_LST_ITEM_CODE));
        txt_ItemName.setValue(line.getValue(KEY_LST_ITEM_NAME));
        txt_LotNo.setValue(line.getValue(KEY_LST_LOT));

        // highlight active row.
        _lcm_lst_FaNoPlanShipping.resetHighlight();
        _lcm_lst_FaNoPlanShipping.addHighlight(row);
        _lcm_lst_FaNoPlanShipping.setEditRow(row);

    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_FaNoPlanShipping.getActiveRow();
        lst_FaNoPlanShipping.setCurrentRow(row);

        // reset editing row.
        lst_FaNoPlanShipping.removeRow(row);
        _lcm_lst_FaNoPlanShipping.resetHighlight();
        _lcm_lst_FaNoPlanShipping.resetEditRow();

        // DFKLOOK add start
        // reset clear and add buttons if there are no items in the list
        if (_lcm_lst_FaNoPlanShipping.size() == 0)
        {
            btn_ListClear_Click_Process();
        }
        else
        {
        	resetListNo(row);
        }
        // DFKLOOK add end
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

    // DFKLOOK start
    /**
     * リストセルのNo.を再セットします。
     *
     * @param row 取消行
     */
    private void resetListNo(int row)
    {
        for (int i = row; i <= _lcm_lst_FaNoPlanShipping.size(); i++)
        {
            ListCellLine line = _lcm_lst_FaNoPlanShipping.get(i);
            line.setValue(KEY_LST_NO, i);

            _lcm_lst_FaNoPlanShipping.set(i, line);
        }
    }
    
    /**
     * 入力されている商品情報のチェックを行います。<br>
     * 該当商品が存在しない場合は、エラーメッセージを表示します。<br>
     * 入力商品コードと名称が一致しない場合、確認ダイアログを表示します。<br>
     * 上記のダイアログで確認後は、商品名称を補完します。
     * 
     * @param conn データベースコネクション
     * @return チェック結果
     * @throws Exception 
     */
    private boolean checkItem(Connection conn)
            throws Exception
    {
        ItemController itemCon = new ItemController(conn, this.getClass());
        String consignor_code = WmsParam.DEFAULT_CONSIGNOR_CODE;
        String item_code = txt_ItemCode.getText();
        
        if (!_isItemCheck && !txt_ItemName.getText().equals(""))
        {
            if (itemCon.exists(item_code, consignor_code))
            {
                // 入力商品コードと商品名称が一致するかチェック
                String item_name = itemCon.getItemName(item_code, consignor_code);
                if (!item_name.equals(txt_ItemName.getText()))
                {
                    // 一致しない場合は、確認ダイアログを表示する
                    this.setConfirm("MSG-W9112", false, true);
                    viewState.setString(_KEY_CONFIRMSOURCE, "checkItem");
                    return false;
                }
            }
            else
            {
                // 6023021 = 商品コードがマスタに登録されていません。
                message.setMsgResourceKey("6023021");
                return false;
            }
        }
        else
        {
            // 商品情報の補完する
            Item item = ItemController.getItemInfo(item_code, consignor_code, conn);

            if (item == null)
            {
                // 6023021 = 商品コードがマスタに登録されていません。
                message.setMsgResourceKey("6023021");
                return false;
            }
            
            // output display.
            txt_ItemName.setValue(item.getItemName());
        }
        
        // フラグ初期化
        _isItemCheck = false;
        return true;
    }
    // DFKLOOK end

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
