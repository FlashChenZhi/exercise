// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.asrs.display.shelfmnt;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.LocationFormatException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.listbox.locationstatus.LstAsLocationStatusParams;
import jp.co.daifuku.wms.asrs.listbox.stockdetail.LstAsStockDetailParams;
import jp.co.daifuku.wms.asrs.listbox.stockdetailnbtn.LstAsStockDetailNoBtnParams;
import jp.co.daifuku.wms.asrs.schedule.AsShelfMntSCH;
import jp.co.daifuku.wms.asrs.schedule.AsShelfMntSCHParams;
import jp.co.daifuku.wms.asrs.schedule.AsrsInParameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;

/**
 * AS/RS 在庫情報変更の画面処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:07:46 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
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
        // DFKLOOK ここから
        else if (eventSource.startsWith("btn_Set_Click"))
        {
            // process call.
            btn_Set_Click_Process(eventSource);
        }
        // DFKLOOK ここまで
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pul_Area_Change(ActionEvent e)
            throws Exception
    {
        // DFKLOOK start
        // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue()));
        // DFKLOOK end
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
        // DFKLOOK ここから
        btn_Set_Click_Process(null);
        // DFKLOOK ここまで
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

		// DFKLOOK start
		_pdm_pul_Area.setSelectedValue(null);
	    // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue()));
        // DFKLOOK end
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
        // DFKLOOK start
        String style = SuperLocationHolder.getInstance().getLocationFormat(outparam.get(LstAsLocationStatusParams.AREA_NO));
        String loc = outparam.getString(LstAsLocationStatusParams.LOCATION_NO);
        txt_Location.setValue(WmsFormatter.toDispLocation(loc, style));
        // DFKLOOK end
        
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
        // DFKLOOK start
        String loc = WmsFormatter.toParamLocation(txt_Location.getStringValue(), lbl_LocationStyle.getStringValue());
        inparam.set(LstAsStockDetailNoBtnParams.LOCATION_NO, loc);
        // DFKLOOK end

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
            // DFKLOOK start
            String loc = WmsFormatter.toParamLocation(txt_Location.getStringValue(), lbl_LocationStyle.getStringValue());
            inparam.set(AsShelfMntSCHParams.LOCATION_NO, loc);
            // DFKLOOK end
            inparam.set(AsShelfMntSCHParams.PROCESSTYPE_KEY, AsrsInParameter.M_CREATE);
            // DFKLOOK ここから
            inparam.set(AsShelfMntSCHParams.SOFT_ZONE_CHECK, false);
            // DFKLOOK ここまで

            // DFKLOOK ここから修正
            // 棚状態をチェック
            if (sch.check(inparam))
            {
                // output display.
                // SCHを通らないため、Businessで棚編集
                lbl_InJavaSetLocation.setValue(WmsFormatter.toDispLocation(loc, lbl_LocationStyle.getStringValue()));
                lbl_InJavaSetLocationStatus.setText(DisplayText.getText(sch.getMessage()));
            // DFKLOOK ここまで修正
                
                viewState.setObject(ViewStateKeys.PROCESSTYPE_KEY, AsrsInParameter.M_CREATE);
                viewState.setObject(ViewStateKeys.STOCK_ID, "");

                // DFKLOOK ここから修正
                //マスタパッケージの有無をチェック
                Params outParam = sch.initFind(null);

                // マスタパッケージの有無を保持
                this.getViewState().setBoolean(ViewStateKeys.MASTER, outParam.getBoolean(AsShelfMntSCHParams.MASTER));
                // DFKLOOK ここまで修正
                
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

                // DFKLOOK ここから修正
                // 入庫日に作業日(システム日付)を初期表示します。
                txt_StorageDate.setDate(new Date());
                txt_StorageTime.setTime(new Date());
                
                // マスタパッケージありの場合は、入力不可項目を設定
                if (this.getViewState().getBoolean(ViewStateKeys.MASTER))
                {
                    txt_ItemName.setReadOnly(true);
                    txt_SoftZoneName.setReadOnly(true);
                    txt_EnteringQty.setReadOnly(true);
                    txt_JanCode.setReadOnly(true);
                    txt_CaseITF.setReadOnly(true);
                }
                else
                {
                    txt_ItemName.setReadOnly(false);
                    txt_SoftZoneName.setReadOnly(false);
                    txt_EnteringQty.setReadOnly(false);
                    txt_JanCode.setReadOnly(false);
                    txt_CaseITF.setReadOnly(false);
                }
                // DFKLOOK ここまで修正
                // set focus.
                setFocus(txt_ItemCode);
                // DFKLOOK ここから修正
            }
            else
            {
                message.setMsgResourceKey(sch.getMessage());
            }
            // DFKLOOK ここまで修正
        }
        //DFKLOOK:ここから修正
        // 棚フォーマットで投げられたExceptionをここでキャッチ
        catch (LocationFormatException ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        //DFKLOOK:ここまで修正
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
        // DFKLOOK start
        String loc = WmsFormatter.toParamLocation(txt_Location.getStringValue(), lbl_LocationStyle.getStringValue());
        inparam.set(LstAsStockDetailParams.LOCATION_NO, loc);
        // DFKLOOK end
        
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
        // DFKLOOK ここから修正
        // SCHのcheckの呼び出しとtry-catchの追加
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsShelfMntSCH sch = null;
        try
        {
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsShelfMntSCH(conn, this.getClass(), locale, ui);
            
            AsShelfMntSCHParams chkparam = new AsShelfMntSCHParams();
            chkparam.set(AsShelfMntSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            String loc = WmsFormatter.toParamLocation(txt_Location.getStringValue(), lbl_LocationStyle.getStringValue());
            chkparam.set(AsShelfMntSCHParams.LOCATION_NO, loc);
            chkparam.set(AsShelfMntSCHParams.PROCESSTYPE_KEY, AsrsInParameter.M_MODIFY);
            chkparam.set(AsShelfMntSCHParams.SOFT_ZONE_CHECK, false);
            
            //棚状態をチェック
            if (!sch.check(chkparam))
            {
                //初期状態に戻す
                //clearInputArea();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            lbl_InJavaSetLocation.setValue(WmsFormatter.toDispLocation(loc, lbl_LocationStyle.getStringValue()));
            lbl_InJavaSetLocationStatus.setText(DisplayText.getText(sch.getMessage()));

        // DFKLOOK ここまで修正
        
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

        // DFKLOOK ここから修正
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
            txt_StockCaseQty.setReadOnly(false);
            txt_StockPieceQty.setReadOnly(false);
            txt_StorageDate.setReadOnly(false);
            txt_StorageTime.setReadOnly(false);
            btn_Set.setEnabled(true);
            btn_Clear.setEnabled(true);
        }
        //DFKLOOK:ここから修正
        // 棚フォーマットで投げられたExceptionをここでキャッチ
        catch (LocationFormatException ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        //DFKLOOK:ここまで修正
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            try
            {
                //コネクションクローズ
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (Exception ex1)
            {
                message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex1, this));
            }
            
        }
        // DFKLOOK ここまで修正
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
        // DFKLOOK start
        String loc = WmsFormatter.toParamLocation(txt_Location.getStringValue(), lbl_LocationStyle.getStringValue());
        inparam.set(LstAsStockDetailParams.LOCATION_NO, loc);
        // DFKLOOK end

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
        // DFKLOOK ここから修正
        // SCHのcheckの呼び出し
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();
        
        Connection conn = null;
        AsShelfMntSCH sch = null;
        try
        {
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsShelfMntSCH(conn, this.getClass(), locale, ui);
            
            AsShelfMntSCHParams chkparam = new AsShelfMntSCHParams();
            chkparam.set(AsShelfMntSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            String loc = WmsFormatter.toParamLocation(txt_Location.getStringValue(), lbl_LocationStyle.getStringValue());
            chkparam.set(AsShelfMntSCHParams.LOCATION_NO, loc);
            chkparam.set(AsShelfMntSCHParams.PROCESSTYPE_KEY, AsrsInParameter.M_DELETE);
            chkparam.set(AsShelfMntSCHParams.SOFT_ZONE_CHECK, false);
            
            //棚状態をチェック
            if (!sch.check(chkparam))
            {
                //初期状態に戻す
                //clearInputArea();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }
            
            lbl_InJavaSetLocation.setValue(WmsFormatter.toDispLocation(loc, lbl_LocationStyle.getStringValue()));
            lbl_InJavaSetLocationStatus.setText(DisplayText.getText(sch.getMessage()));

            // DFKLOOK ここまで修正
        
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

            // DFKLOOK ここから修正
            // clear
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
            btn_Set.setEnabled(true);
            btn_Clear.setEnabled(true);
        }
        //DFKLOOK:ここから修正
        // 棚フォーマットで投げられたExceptionをここでキャッチ
        catch (LocationFormatException ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        //DFKLOOK:ここまで修正
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            try
            {
                // コネクションクローズ
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (Exception ex1)
            {
                message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex1, this));
            }
        }
        // DFKLOOK ここまで修正
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
    private void btn_Set_Click_Process(String eventSource)
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

        // 日付、日時どちらか空白の場合
        if (StringUtil.isBlank(txt_StorageDate.getValue()) ^ StringUtil.isBlank(txt_StorageTime.getValue()))
        {
            // 6022064=入庫日時を入力する場合、日付と時間の両方を入力してください。
            message.setMsgResourceKey("6022064");
            return;
        }

        // DFKLOOK:ここから修正
        if (StringUtil.isBlank(eventSource))
        {
            // 押されたボタンによってダイアログの表示メッセージを変える。
            String processKey = this.getViewState().getString(ViewStateKeys.PROCESSTYPE_KEY);
            if (AsrsInParameter.M_CREATE.equals(processKey))
            {
                // 登録しますか？
                this.setConfirm("MSG-T0056", false, true);
            }
            else if (AsrsInParameter.M_MODIFY.equals(processKey))
            {
                // 修正しますか？
                this.setConfirm("MSG-T0057", false, true);
            }
            else if (AsrsInParameter.M_DELETE.equals(processKey))
            {
                // 削除しますか？
                this.setConfirm("MSG-T0058", false, true);
            }
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
            return;
        }
        // DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsShelfMntSCH sch = null;
        
        //DFKLOOK:ここから修正
        // 現在日時を取得
        String day = DbDateUtil.getSystemDate();
        String time = DbDateUtil.getSystemDateTime();
        //DFKLOOK:ここまで修正
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsShelfMntSCH(conn, this.getClass(), locale, ui);

            // DFKLOOK ここから修正
            //商品コードよりマスタ検索(エラー時の表示用)
            if (this.getViewState().getBoolean(ViewStateKeys.MASTER))
            {
                AsShelfMntSCHParams queryparam = new AsShelfMntSCHParams();
                queryparam.set(AsShelfMntSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
                queryparam.set(AsShelfMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                queryparam.set(AsShelfMntSCHParams.ITEM_CODE, txt_ItemCode.getText());
                List<Params> outParams = sch.query(queryparam);

                if (outParams == null || outParams.size() > 0)
                {
                    for (Params outParam : outParams) 
                    {
                        txt_ItemName.setText(outParam.getString(AsShelfMntSCHParams.ITEM_NAME));
                        txt_SoftZoneName.setText(outParam.getString(AsShelfMntSCHParams.SOFT_ZONE_NAME));
                        txt_EnteringQty.setInt(outParam.getInt(AsShelfMntSCHParams.ENTERING_QTY));
                        txt_JanCode.setText(outParam.getString(AsShelfMntSCHParams.JAN));
                        txt_CaseITF.setText(outParam.getString(AsShelfMntSCHParams.ITF));
                    }
                }
                else
                {
                    //MSG="商品コードが登録されていません"
                    message.setMsgResourceKey("6023021");
                    return;
                }
            }
            // DFKLOOK ここまで修正
            
            // set input parameters.
            AsShelfMntSCHParams inparam = new AsShelfMntSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(AsShelfMntSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            // DFKLOOK start
            String loc = WmsFormatter.toParamLocation(txt_Location.getStringValue(), lbl_LocationStyle.getStringValue());
            inparam.set(AsShelfMntSCHParams.LOCATION_NO, loc);
            // DFKLOOK end
            inparam.set(AsShelfMntSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(AsShelfMntSCHParams.ITEM_NAME, txt_ItemName.getValue());
            inparam.set(AsShelfMntSCHParams.ENTERING_QTY, txt_EnteringQty.getValue());
            inparam.set(AsShelfMntSCHParams.JAN, txt_JanCode.getValue());
            inparam.set(AsShelfMntSCHParams.ITF, txt_CaseITF.getValue());
            inparam.set(AsShelfMntSCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(AsShelfMntSCHParams.STOCK_CASE_QTY, txt_StockCaseQty.getValue());
            inparam.set(AsShelfMntSCHParams.STOCK_PIECE_QTY, txt_StockPieceQty.getValue());
            //DFKLOOK:ここから修正
            // 入力されていない場合は現在日時を送る
            if (StringUtil.isBlank(txt_StorageDate.getValue()) && StringUtil.isBlank(txt_StorageTime.getValue()))
            {
                inparam.set(AsShelfMntSCHParams.STORAGE_DAY, WmsFormatter.toDate(day));
                inparam.set(AsShelfMntSCHParams.STORAGE_TIME, WmsFormatter.toDateTime(time));
            }
            else
            {
                inparam.set(AsShelfMntSCHParams.STORAGE_DAY, txt_StorageDate.getValue());
                inparam.set(AsShelfMntSCHParams.STORAGE_TIME, txt_StorageTime.getValue());
            }
            //DFKLOOK:ここまで修正
            inparam.set(AsShelfMntSCHParams.PROCESSTYPE_KEY, viewState.getObject(ViewStateKeys.PROCESSTYPE_KEY));
            inparam.set(AsShelfMntSCHParams.STOCK_ID, viewState.getObject(ViewStateKeys.STOCK_ID));
            inparam.set(AsShelfMntSCHParams.LAST_UPDATE_DATE, viewState.getObject(ViewStateKeys.LAST_UPDATE_DATE));
            inparam.set(AsShelfMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // DFKLOOK ここから
            inparam.set(AsShelfMntSCHParams.SOFT_ZONE_CHECK, true);

            if (eventSource.equals("btn_Set_Click") && !sch.check(inparam))
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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click_Check");
                    return;
                }
            }
            
            inparam.set(AsShelfMntSCHParams.SOFT_ZONE_CHECK, false);
            // DFKLOOK ここまで
            
            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                if (!txt_ItemCode.getReadOnly())
                {
                    setFocus(txt_ItemCode);
                }
                else if (!txt_StockCaseQty.getReadOnly())
                {
                    setFocus(txt_StockCaseQty);
                }
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");
            //DFKLOOK:ここから修正
            part11List.add(WmsFormatter.toDispLocation(loc, lbl_LocationStyle.getStringValue()), "");
            //DFKLOOK:ここまで修正
            part11List.add(txt_ItemCode.getStringValue(), "");
            part11List.add(txt_EnteringQty.getStringValue(), "0");
            part11List.add(txt_JanCode.getStringValue(), "");
            part11List.add(txt_CaseITF.getStringValue(), "");
            part11List.add(txt_LotNo.getStringValue(), "");
            part11List.add(txt_StockCaseQty.getStringValue(), "0");
            part11List.add(txt_StockPieceQty.getStringValue(), "0");
            //DFKLOOK:ここから修正
            // 入力されていない場合は現在日時を出力
            if (StringUtil.isBlank(txt_StorageDate.getValue()) && StringUtil.isBlank(txt_StorageTime.getValue()))
            {
                part11List.add(WmsFormatter.toDispDate(day, locale), WmsFormatter.toDispTime(WmsFormatter.toDateTime(time), locale), "");
            }
            else
            {
                part11List.add(txt_StorageDate.getStringValue(), txt_StorageTime.getStringValue(), "");
            }

            // 登録の場合
            if (AsrsInParameter.M_CREATE.equals(viewState.getObject(ViewStateKeys.PROCESSTYPE_KEY)))
            {
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_REGIST),
                        part11List);
            }
            // 修正の場合
            else if (AsrsInParameter.M_MODIFY.equals(viewState.getObject(ViewStateKeys.PROCESSTYPE_KEY)))
            {
                part11List.add(viewState.getObject(ViewStateKeys.VS_STOCK_CASE_QTY), "0");
                part11List.add(viewState.getObject(ViewStateKeys.VS_STOCK_PIECE_QTY), "0");
                String stDate = WmsFormatter.toDispDate(this.getViewState().getDate(ViewStateKeys.VS_STORAGE_DATE), locale);
                String stTime = WmsFormatter.toDispTime(this.getViewState().getDate(ViewStateKeys.VS_STORAGE_TIME), locale);
                part11List.add(stDate, stTime, "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_MODIFY),
                        part11List);
            }
            // 削除の場合
            else if (AsrsInParameter.M_DELETE.equals(viewState.getObject(ViewStateKeys.PROCESSTYPE_KEY)))
            {
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_DELETE),
                        part11List);
            }
            //DFKLOOK:ここまで修正

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
        //DFKLOOK:ここから修正
        // 棚フォーマットで投げられたExceptionをここでキャッチ
        catch (LocationFormatException ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        //DFKLOOK:ここまで修正
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
