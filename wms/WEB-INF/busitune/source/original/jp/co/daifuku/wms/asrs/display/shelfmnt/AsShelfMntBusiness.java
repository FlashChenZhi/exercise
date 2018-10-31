// $Id: AsShelfMntBusiness.java 7992 2011-02-07 03:50:35Z kanda $
package jp.co.daifuku.wms.asrs.display.shelfmnt;

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
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.bluedog.webapp.ViewState;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.shelfmnt.AsShelfMnt;
import jp.co.daifuku.wms.asrs.display.shelfmnt.ViewStateKeys;
import jp.co.daifuku.wms.asrs.listbox.locationstatus.LstAsLocationStatusParams;
import jp.co.daifuku.wms.asrs.listbox.stockdetail.LstAsStockDetailParams;
import jp.co.daifuku.wms.asrs.listbox.stockdetailnbtn.LstAsStockDetailNoBtnParams;
import jp.co.daifuku.wms.asrs.schedule.AsShelfMntSCH;
import jp.co.daifuku.wms.asrs.schedule.AsShelfMntSCHParams;
import jp.co.daifuku.wms.asrs.schedule.AsrsInParameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7992 $, $Date:: 2011-02-07 12:50:35 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kanda $
 */
public class AsShelfMntBusiness
        extends AsShelfMnt
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsShelfMntBusiness()
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
        if (eventSource.equals("btn_PInquiry_Click"))
        {
            // process call.
            btn_PInquiry_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_PLocationDetail_Click"))
        {
            // process call.
            btn_PLocationDetail_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_PModify_Click"))
        {
            // process call.
            btn_PModify_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_PDelete_Click"))
        {
            // process call.
            btn_PDelete_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_PSearchItemCode_Click"))
        {
            // process call.
            btn_PSearchItemCode_Click_DlgBack(dialogParams);
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
        if (eventSource.equals("btn_Submit_Click"))
        {
            // process call.
            btn_Submit_Click_Process(false);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PInquiry_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PInquiry_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PLocationDetail_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PLocationDetail_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Submit_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Submit_Click_Process(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PModify_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PModify_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PDelete_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PDelete_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PSearchItemCode_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PSearchItemCode_Click_Process();
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
        btn_Set_Click_Process();
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

        // initialize pul_Area.
        _pdm_pul_Area = new WmsAreaPullDownModel(pul_Area, locale, ui);
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
            _pdm_pul_Area.init(conn, AreaType.ASRS, StationType.ALL, "", false);
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
    private void dispose()
            throws Exception
    {
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
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        chk_EmptyLocation.setChecked(true);
        txt_ItemCode.setReadOnly(true);
        btn_PSearchItemCode.setEnabled(false);
        txt_ItemName.setReadOnly(true);
        txt_EnteringQty.setReadOnly(true);
        txt_JanCode.setReadOnly(true);
        txt_CaseITF.setReadOnly(true);
        txt_LotNo.setReadOnly(true);
        txt_StockCaseQty.setReadOnly(true);
        txt_StockPieceQty.setReadOnly(true);
        txt_StorageDate.setReadOnly(true);
        txt_StorageTime.setReadOnly(true);
        btn_Set.setEnabled(false);
        btn_Clear.setEnabled(false);
        txt_SoftZoneName.setReadOnly(true);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_PInquiry_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstAsLocationStatusParams inparam = new LstAsLocationStatusParams();
        inparam.set(LstAsLocationStatusParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
        inparam.set(LstAsLocationStatusParams.PROHIBITION_EMPTY, chk_EmptyLocation.getValue());
        inparam.set(LstAsLocationStatusParams.PROHIBITION_EMPTY_PALLET, chk_EmptyPBLocation.getValue());
        inparam.set(LstAsLocationStatusParams.PROHIBITION_STORAGED, chk_StoredLocation.getValue());
        inparam.set(LstAsLocationStatusParams.PROHIBITION_ERROR, chk_StoredLocation.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PInquiry_Click");
        redirect("/asrs/listbox/locationstatus/LstAsLocationStatus.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PInquiry_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstAsLocationStatusParams outparam = new LstAsLocationStatusParams(dialogParams);
        txt_Location.setValue(outparam.get(LstAsLocationStatusParams.LOCATION_NO));

        // set focus.
        setFocus(txt_Location);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_PLocationDetail_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstAsStockDetailNoBtnParams inparam = new LstAsStockDetailNoBtnParams();
        inparam.set(LstAsStockDetailNoBtnParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
        inparam.set(LstAsStockDetailNoBtnParams.LOCATION_NO, txt_Location.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PLocationDetail_Click");
        redirect("/asrs/listbox/stockdetailnbtn/LstAsStockDetailNoBtn.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PLocationDetail_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // set focus.
        setFocus(txt_Location);
    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_Submit_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        txt_Location.validate(this, true);
        pul_Area.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsShelfMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsShelfMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsShelfMntSCHParams inparam = new AsShelfMntSCHParams();
            inparam.set(AsShelfMntSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsShelfMntSCHParams.LOCATION_NO, txt_Location.getValue());
            inparam.set(AsShelfMntSCHParams.PROCESSTYPE_KEY, AsrsInParameter.M_CREATE);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();

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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Submit_Click");
                    return;
                }
            }

            message.setMsgResourceKey(sch.getMessage());

            // output display.
            lbl_InJavaSetLocation.setValue(txt_Location.getValue());
            viewState.setObject(ViewStateKeys.PROCESSTYPE_KEY, AsrsInParameter.M_CREATE);
            viewState.setObject(ViewStateKeys.STOCK_ID, "");

            // clear.
            pul_Area.setEnabled(false);
            chk_EmptyLocation.setEnabled(false);
            chk_EmptyPBLocation.setEnabled(false);
            chk_StoredLocation.setEnabled(false);
            btn_PInquiry.setEnabled(false);
            txt_Location.setReadOnly(true);
            btn_PLocationDetail.setEnabled(false);
            btn_Submit.setEnabled(false);
            btn_PModify.setEnabled(false);
            btn_PDelete.setEnabled(false);
            txt_ItemCode.setReadOnly(false);
            btn_PSearchItemCode.setEnabled(true);
            txt_LotNo.setReadOnly(false);
            txt_StockCaseQty.setReadOnly(false);
            txt_StockPieceQty.setReadOnly(false);
            txt_StorageDate.setReadOnly(false);
            txt_StorageTime.setReadOnly(false);
            btn_Set.setEnabled(true);
            btn_Clear.setEnabled(true);

            // set focus.
            setFocus(txt_ItemCode);
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
    private void btn_PModify_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstAsStockDetailParams inparam = new LstAsStockDetailParams();
        inparam.set(LstAsStockDetailParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
        inparam.set(LstAsStockDetailParams.LOCATION_NO, txt_Location.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PModify_Click");
        redirect("/asrs/listbox/stockdetail/LstAsStockDetail.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PModify_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstAsStockDetailParams outparam = new LstAsStockDetailParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstAsStockDetailParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstAsStockDetailParams.ITEM_NAME));
        txt_SoftZoneName.setValue(outparam.get(LstAsStockDetailParams.SOFT_ZONE_NAME));
        txt_EnteringQty.setValue(outparam.get(LstAsStockDetailParams.ENTERING_QTY));
        txt_JanCode.setValue(outparam.get(LstAsStockDetailParams.JAN));
        txt_CaseITF.setValue(outparam.get(LstAsStockDetailParams.ITF));
        txt_LotNo.setValue(outparam.get(LstAsStockDetailParams.LOT_NO));
        txt_StockCaseQty.setValue(outparam.get(LstAsStockDetailParams.STOCK_CASE_QTY));
        txt_StockPieceQty.setValue(outparam.get(LstAsStockDetailParams.STOCK_PIECE_QTY));
        txt_StorageDate.setValue(outparam.get(LstAsStockDetailParams.STORAGE_DATE));
        txt_StorageTime.setValue(outparam.get(LstAsStockDetailParams.STORAGE_TIME));
        viewState.setObject(ViewStateKeys.STOCK_ID, outparam.get(LstAsStockDetailParams.STOCK_ID));
        viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, outparam.get(LstAsStockDetailParams.LAST_UPDATE_DATE));
        viewState.setObject(ViewStateKeys.PROCESSTYPE_KEY, AsrsInParameter.M_MODIFY);
        viewState.setObject(ViewStateKeys.VS_STOCK_CASE_QTY, outparam.get(LstAsStockDetailParams.STOCK_CASE_QTY));
        viewState.setObject(ViewStateKeys.VS_STOCK_PIECE_QTY, outparam.get(LstAsStockDetailParams.STOCK_PIECE_QTY));
        viewState.setObject(ViewStateKeys.VS_STORAGE_DATE, outparam.get(LstAsStockDetailParams.STORAGE_DATE));
        viewState.setObject(ViewStateKeys.VS_STORAGE_TIME, outparam.get(LstAsStockDetailParams.STORAGE_TIME));

        // set focus.
        setFocus(txt_StockCaseQty);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_PDelete_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstAsStockDetailParams inparam = new LstAsStockDetailParams();
        inparam.set(LstAsStockDetailParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
        inparam.set(LstAsStockDetailParams.LOCATION_NO, txt_Location.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PDelete_Click");
        redirect("/asrs/listbox/stockdetail/LstAsStockDetail.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PDelete_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstAsStockDetailParams outparam = new LstAsStockDetailParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstAsStockDetailParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstAsStockDetailParams.ITEM_NAME));
        txt_SoftZoneName.setValue(outparam.get(LstAsStockDetailParams.SOFT_ZONE_NAME));
        txt_EnteringQty.setValue(outparam.get(LstAsStockDetailParams.ENTERING_QTY));
        txt_JanCode.setValue(outparam.get(LstAsStockDetailParams.JAN));
        txt_CaseITF.setValue(outparam.get(LstAsStockDetailParams.ITF));
        txt_LotNo.setValue(outparam.get(LstAsStockDetailParams.LOT_NO));
        txt_StockCaseQty.setValue(outparam.get(LstAsStockDetailParams.STOCK_CASE_QTY));
        txt_StockPieceQty.setValue(outparam.get(LstAsStockDetailParams.STOCK_PIECE_QTY));
        txt_StorageDate.setValue(outparam.get(LstAsStockDetailParams.STORAGE_DATE));
        txt_StorageTime.setValue(outparam.get(LstAsStockDetailParams.STORAGE_TIME));
        viewState.setObject(ViewStateKeys.STOCK_ID, outparam.get(LstAsStockDetailParams.STOCK_ID));
        viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, outparam.get(LstAsStockDetailParams.LAST_UPDATE_DATE));
        viewState.setObject(ViewStateKeys.PROCESSTYPE_KEY, AsrsInParameter.M_DELETE);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_PSearchItemCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PSearchItemCode_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PSearchItemCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstItemParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemParams.ITEM_NAME));
        txt_SoftZoneName.setValue(outparam.get(LstItemParams.SOFT_ZONE_NAME));
        txt_EnteringQty.setValue(outparam.get(LstItemParams.ENTERING_QTY));
        txt_JanCode.setValue(outparam.get(LstItemParams.JAN));
        txt_CaseITF.setValue(outparam.get(LstItemParams.ITF));

        // set focus.
        setFocus(txt_ItemCode);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process()
            throws Exception
    {
        // input validation.
        txt_Location.validate(this, false);
        txt_ItemCode.validate(this, true);
        txt_ItemName.validate(this, false);
        txt_EnteringQty.validate(this, false);
        txt_JanCode.validate(this, false);
        txt_CaseITF.validate(this, false);
        txt_LotNo.validate(this, false);
        txt_StockCaseQty.validate(this, false);
        txt_StockPieceQty.validate(this, false);
        txt_StorageDate.validate(this, false);
        txt_StorageTime.validate(this, false);
        pul_Area.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsShelfMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsShelfMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsShelfMntSCHParams inparam = new AsShelfMntSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(AsShelfMntSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsShelfMntSCHParams.LOCATION_NO, txt_Location.getValue());
            inparam.set(AsShelfMntSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(AsShelfMntSCHParams.ITEM_NAME, txt_ItemName.getValue());
            inparam.set(AsShelfMntSCHParams.ENTERING_QTY, txt_EnteringQty.getValue());
            inparam.set(AsShelfMntSCHParams.JAN, txt_JanCode.getValue());
            inparam.set(AsShelfMntSCHParams.ITF, txt_CaseITF.getValue());
            inparam.set(AsShelfMntSCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(AsShelfMntSCHParams.STOCK_CASE_QTY, txt_StockCaseQty.getValue());
            inparam.set(AsShelfMntSCHParams.STOCK_PIECE_QTY, txt_StockPieceQty.getValue());
            inparam.set(AsShelfMntSCHParams.STORAGE_DAY, txt_StorageDate.getValue());
            inparam.set(AsShelfMntSCHParams.STORAGE_TIME, txt_StorageTime.getValue());
            inparam.set(AsShelfMntSCHParams.PROCESSTYPE_KEY, viewState.getObject(ViewStateKeys.PROCESSTYPE_KEY));
            inparam.set(AsShelfMntSCHParams.STOCK_ID, viewState.getObject(ViewStateKeys.STOCK_ID));
            inparam.set(AsShelfMntSCHParams.LAST_UPDATE_DATE, viewState.getObject(ViewStateKeys.LAST_UPDATE_DATE));
            inparam.set(AsShelfMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");
            part11List.add(txt_Location.getStringValue(), "");
            part11List.add(txt_ItemCode.getStringValue(), "");
            part11List.add(txt_EnteringQty.getStringValue(), "0");
            part11List.add(txt_JanCode.getStringValue(), "");
            part11List.add(txt_CaseITF.getStringValue(), "");
            part11List.add(txt_LotNo.getStringValue(), "");
            part11List.add(txt_StockCaseQty.getStringValue(), "0");
            part11List.add(txt_StockPieceQty.getStringValue(), "0");
            part11List.add(txt_StorageDate.getStringValue(), txt_StorageTime.getStringValue(), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_STOCK_CASE_QTY), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_STOCK_PIECE_QTY), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_STORAGE_DATE), viewState.getString(ViewStateKeys.VS_STORAGE_TIME), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_REGIST), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // clear.
            lbl_InJavaSetLocation.setValue(null);
            lbl_InJavaSetLocationStatus.setValue(null);
            txt_ItemCode.setReadOnly(true);
            txt_ItemCode.setValue(null);
            btn_PSearchItemCode.setEnabled(false);
            txt_ItemName.setReadOnly(true);
            txt_ItemName.setValue(null);
            txt_SoftZoneName.setReadOnly(true);
            txt_SoftZoneName.setValue(null);
            txt_EnteringQty.setReadOnly(true);
            txt_EnteringQty.setValue(null);
            txt_JanCode.setReadOnly(true);
            txt_JanCode.setValue(null);
            txt_CaseITF.setReadOnly(true);
            txt_CaseITF.setValue(null);
            txt_LotNo.setReadOnly(true);
            txt_LotNo.setValue(null);
            txt_StockCaseQty.setReadOnly(true);
            txt_StockCaseQty.setValue(null);
            txt_StockPieceQty.setReadOnly(true);
            txt_StockPieceQty.setValue(null);
            txt_StorageDate.setReadOnly(true);
            txt_StorageDate.setValue(null);
            txt_StorageTime.setReadOnly(true);
            txt_StorageTime.setValue(null);
            btn_Set.setEnabled(false);
            btn_Clear.setEnabled(false);
            pul_Area.setEnabled(true);
            chk_EmptyLocation.setEnabled(true);
            chk_EmptyPBLocation.setEnabled(true);
            chk_StoredLocation.setEnabled(true);
            btn_PInquiry.setEnabled(true);
            txt_Location.setReadOnly(false);
            btn_PLocationDetail.setEnabled(true);
            btn_Submit.setEnabled(true);
            btn_PModify.setEnabled(true);
            btn_PDelete.setEnabled(true);
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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        pul_Area.setEnabled(true);
        chk_EmptyLocation.setEnabled(true);
        chk_EmptyPBLocation.setEnabled(true);
        chk_StoredLocation.setEnabled(true);
        btn_PInquiry.setEnabled(true);
        txt_Location.setReadOnly(false);
        btn_PLocationDetail.setEnabled(true);
        btn_Submit.setEnabled(true);
        btn_PModify.setEnabled(true);
        btn_PDelete.setEnabled(true);
        lbl_InJavaSetLocation.setValue(null);
        lbl_InJavaSetLocationStatus.setValue(null);
        txt_ItemCode.setReadOnly(true);
        txt_ItemCode.setValue(null);
        btn_PSearchItemCode.setEnabled(false);
        txt_ItemName.setReadOnly(true);
        txt_ItemName.setValue(null);
        txt_SoftZoneName.setReadOnly(true);
        txt_SoftZoneName.setValue(null);
        txt_EnteringQty.setReadOnly(true);
        txt_EnteringQty.setValue(null);
        txt_JanCode.setReadOnly(true);
        txt_JanCode.setValue(null);
        txt_CaseITF.setReadOnly(true);
        txt_CaseITF.setValue(null);
        txt_LotNo.setReadOnly(true);
        txt_LotNo.setValue(null);
        txt_StockCaseQty.setReadOnly(true);
        txt_StockCaseQty.setValue(null);
        txt_StockPieceQty.setReadOnly(true);
        txt_StockPieceQty.setValue(null);
        txt_StorageDate.setReadOnly(true);
        txt_StorageDate.setValue(null);
        txt_StorageTime.setReadOnly(true);
        txt_StorageTime.setValue(null);
        btn_Set.setEnabled(false);
        btn_Clear.setEnabled(false);
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
