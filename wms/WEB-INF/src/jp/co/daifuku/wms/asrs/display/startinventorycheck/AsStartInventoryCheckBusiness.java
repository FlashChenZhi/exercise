// $Id: AsStartInventoryCheckBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.asrs.display.startinventorycheck;

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
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.LocationFormatException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;
import jp.co.daifuku.wms.asrs.dasch.AsStartInventoryCheckDASCH;
import jp.co.daifuku.wms.asrs.dasch.AsStartInventoryCheckDASCHParams;
import jp.co.daifuku.wms.asrs.schedule.AsStartInventoryCheckSCH;
import jp.co.daifuku.wms.asrs.schedule.AsStartInventoryCheckSCHParams;
import jp.co.daifuku.wms.base.common.LocationNumber;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.Distribution;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkspacePullDownModel;
import jp.co.daifuku.wms.asrs.exporter.InventoryCheckWorkListParams;

/**
 * AS/RS 在庫確認開始設定の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
@SuppressWarnings("serial")
public class AsStartInventoryCheckBusiness
        extends AsStartInventoryCheck
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

    /** PullDownModel pul_WorkPlace */
    private WmsWorkspacePullDownModel _pdm_pul_WorkPlace;

    /** PullDownModel pul_Station */
    private WmsStationPullDownModel _pdm_pul_Station;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsStartInventoryCheckBusiness()
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
        if (eventSource.startsWith("btn_Setting_Click"))
        {
            // process call.
            btn_Setting_Click_Process(eventSource);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pul_Area_Change(ActionEvent e)
            throws Exception
    {
        //DFKLOOK:ここから修正
        String loc = SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue());
        lbl_In_LocationStyle.setText(loc);
        //DFKLOOK:ここまで修正
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
    public void btn_Setting_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Setting_Click_Process(null);
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
    //DFKLOOK:ここから修正
    /**
     * 入力チェックを行うためのメソッドです。<BR>
     * 棚と商品の大小チェックを行います。
     * <BR>
     * @param conn コネクション
     * @return 入力チェックの結果(true：OK  false：NG)
     * @throws Exception 全ての例外を報告します。
     */
    protected boolean checkInputData(Connection conn)
            throws Exception
    {
        LocationNumber fromLoc = null;
        LocationNumber toLoc = null;
        AreaController areaCtlr = new AreaController(conn, this.getClass());
        String loc = SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue());

        try
        {
            // 棚範囲が両方指定されていれば大小チェックを行う
            if (!StringUtil.isBlank(txt_StartLocation.getText()) && !StringUtil.isBlank(txt_EndLocation.getText()))
            {
                fromLoc = new LocationNumber(areaCtlr.getLocationStyle(pul_Area.getSelectedValue()));
                fromLoc.parseParam(WmsFormatter.toParamLocation(txt_StartLocation.getStringValue(), loc));
                toLoc = new LocationNumber(areaCtlr.getLocationStyle(pul_Area.getSelectedValue()));
                toLoc.parseParam(WmsFormatter.toParamLocation(txt_EndLocation.getStringValue(), loc));


                String[] fromLocation = fromLoc.getLocation();
                String[] toLocation = toLoc.getLocation();

                int kBank = toLocation[LocationNumber.IDX_BANK].compareTo(fromLocation[LocationNumber.IDX_BANK]);
                int kBay = toLocation[LocationNumber.IDX_BAY].compareTo(fromLocation[LocationNumber.IDX_BAY]);
                int kLevel = toLocation[LocationNumber.IDX_LEVEL].compareTo(fromLocation[LocationNumber.IDX_LEVEL]);

                // バンク 開始 < 終了
                if (kBank > 0)
                {
                    // 開始 < 終了
                    fromLocation = fromLoc.getLocation();
                    toLocation = toLoc.getLocation();
                }
                // ベイ 開始 < 終了
                else if (kBay > 0)
                {
                    if (kBank != 0)
                    {
                        // (バンク 開始 > 終了) かつ (ベイ 開始 < 終了)
                        toLocation = fromLoc.getLocation();
                        fromLocation = toLoc.getLocation();
                    }
                    else
                    {
                        // (バンク 開始 == 終了) かつ (ベイ 開始 < 終了)
                        fromLocation = fromLoc.getLocation();
                        toLocation = toLoc.getLocation();
                    }
                }
                // レベル 開始 < 終了
                else if (kLevel > 0)
                {
                    if (kBank != 0)
                    {
                        // (バンク 開始 > 終了) かつ (レベル 開始 < 終了)
                        toLocation = fromLoc.getLocation();
                        fromLocation = toLoc.getLocation();
                    }
                    else
                    {
                        // (バンク 開始 == 終了) かつ (レベル 開始 < 終了)
                        fromLocation = fromLoc.getLocation();
                        toLocation = toLoc.getLocation();
                    }
                }
                else
                {
                    // バンク ベイ レベル (開始 > 終了) か (開始 == 終了)
                    toLocation = fromLoc.getLocation();
                    fromLocation = toLoc.getLocation();
                }

                if (Integer.parseInt(fromLocation[LocationNumber.IDX_BAY]) > Integer.parseInt(toLocation[LocationNumber.IDX_BAY]))
                {
                    // 6123069=開始ベイは終了ベイよりも小さな値を指定してください。
                    message.setMsgResourceKey("6123069");
                    return false;
                }
                if (Integer.parseInt(fromLocation[LocationNumber.IDX_LEVEL]) > Integer.parseInt(toLocation[LocationNumber.IDX_LEVEL]))
                {
                    // 6123070=開始レベルは終了レベルよりも小さな値を指定してください。
                    message.setMsgResourceKey("6123070");
                    return false;
                }
            }
        }
        catch (LocationFormatException ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            return false;
        }
        return true;
    }
    //DFKLOOK:ここまで修正

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

        // initialize pul_WorkPlace.
        _pdm_pul_WorkPlace = new WmsWorkspacePullDownModel(pul_WorkPlace, locale, ui);
        _pdm_pul_WorkPlace.setParent(_pdm_pul_Area);

        // initialize pul_Station.
        _pdm_pul_Station = new WmsStationPullDownModel(pul_Station, locale, ui);
        _pdm_pul_Station.setParent(_pdm_pul_WorkPlace);

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
            _pdm_pul_Area.init(conn, AreaType.ASRS, StationType.INVENTORY_CHECK, "", false);

            // load pul_WorkPlace.
            _pdm_pul_WorkPlace.init(conn, StationType.INVENTORY_CHECK);

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.INVENTORY_CHECK, Distribution.AUTO);

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
    @SuppressWarnings("all")
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
        setFocus(txt_StartLocation);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        chk_CommonUse.setChecked(true);
        _pdm_pul_Area.setSelectedValue(null);

        //DFKLOOK:ここから修正
        String loc = SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue());
        lbl_In_LocationStyle.setText(loc);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsStartInventoryCheckSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsStartInventoryCheckSCH(conn, this.getClass(), locale, ui);

            AsStartInventoryCheckSCHParams inParam = new AsStartInventoryCheckSCHParams(ui);
            inParam.set(AsStartInventoryCheckSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));

            // 画面定義テーブルをチェック
            Params outParam = sch.initFind(inParam);

            if (outParam != null)
            {
                String printflg = outParam.getString(AsStartInventoryCheckSCHParams.PRINT_FLAG);
                if (SystemDefine.KEYDATA_OFF.equals(printflg))
                {
                    // 前回、チェックOFFだった場合は更新
                    chk_CommonUse.setChecked(false);
                }
            }
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
        //DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchFromItemCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.ITEM_CODE, txt_StartItemCode.getValue());
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchFromItemCode_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchFromItemCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_StartItemCode.setValue(outparam.get(LstItemParams.ITEM_CODE));

        // set focus.
        setFocus(txt_StartItemCode);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchToItemCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.ITEM_CODE, txt_EndItemCode.getValue());
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchToItemCode_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchToItemCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_EndItemCode.setValue(outparam.get(LstItemParams.ITEM_CODE));

        // set focus.
        setFocus(txt_EndItemCode);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Setting_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        txt_StartItemCode.validate(this, false);
        txt_EndItemCode.validate(this, false);
        pul_Area.validate(this, true);
        pul_Station.validate(this, true);

        // DFKLOOK start
        String startLoc = null;
        String endLoc = null;
        String loc = null;
        try
        {
	        loc = lbl_In_LocationStyle.getStringValue();

	        startLoc = WmsFormatter.toParamLocation(txt_StartLocation.getStringValue(), loc);
	        endLoc = WmsFormatter.toParamLocation(txt_EndLocation.getStringValue(), loc);
        }
        // 棚フォーマットで投げられたExceptionをここでキャッチ
        catch (LocationFormatException ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            return;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            return;
        }

        if(StringUtil.isBlank(eventSource))
        {
        	// 設定しますか？
            this.setConfirm("MSG-W9000", true, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_Setting_Click");
            // 「処理中です」メッセージ表示
            message.setMsgResourceKey("6001017");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsStartInventoryCheckSCH sch = null;
        AsStartInventoryCheckDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsStartInventoryCheckSCH(conn, this.getClass(), locale, ui);
            dasch = new AsStartInventoryCheckDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // DFKLOOK ここから
            // 入力チェックを行います。
            if (!checkInputData(conn))
            {
                return;
            }
            // DFKLOOK ここまで

            // set input parameters.
            AsStartInventoryCheckSCHParams inparam = new AsStartInventoryCheckSCHParams(ui);
            inparam.set(AsStartInventoryCheckSCHParams.AREA, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsStartInventoryCheckSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(AsStartInventoryCheckSCHParams.START_ITEM_CODE, txt_StartItemCode.getValue());
            inparam.set(AsStartInventoryCheckSCHParams.END_ITEM_CODE, txt_EndItemCode.getValue());
            inparam.set(AsStartInventoryCheckSCHParams.COMMON_USE, chk_CommonUse.getValue());

            // DFKLOOK start
            inparam.set(AsStartInventoryCheckSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));
            // DFKLOOK end

            // DFKLOOK ここから
            // 棚大小入れ替え
            LocationNumber fromLoc = null;
            LocationNumber toLoc = null;
            AreaController areaCtlr = new AreaController(conn, this.getClass());
            // 入力確認
            if (!StringUtil.isBlank(txt_StartLocation.getText()) && !StringUtil.isBlank(txt_EndLocation.getText()))
            {
                fromLoc = new LocationNumber(areaCtlr.getLocationStyle(pul_Area.getSelectedValue()));
                fromLoc.parseParam(startLoc);
                toLoc = new LocationNumber(areaCtlr.getLocationStyle(pul_Area.getSelectedValue()));
                toLoc.parseParam(endLoc);

                String fromLocation[] = fromLoc.getLocation();
                String toLocation[] = toLoc.getLocation();

                if (Integer.parseInt(fromLocation[LocationNumber.IDX_BANK]) > Integer.parseInt(toLocation[LocationNumber.IDX_BANK]))
                {
                    inparam.set(AsStartInventoryCheckSCHParams.START_LOCATION, endLoc);
                    inparam.set(AsStartInventoryCheckSCHParams.END_LOCATION, startLoc);
                }
                else if (Integer.parseInt(fromLocation[LocationNumber.IDX_BAY]) > Integer.parseInt(toLocation[LocationNumber.IDX_BAY]))
                {
                    inparam.set(AsStartInventoryCheckSCHParams.START_LOCATION, endLoc);
                    inparam.set(AsStartInventoryCheckSCHParams.END_LOCATION, startLoc);
                }
                else if (Integer.parseInt(fromLocation[LocationNumber.IDX_LEVEL]) > Integer.parseInt(toLocation[LocationNumber.IDX_LEVEL]))
                {
                    inparam.set(AsStartInventoryCheckSCHParams.START_LOCATION, endLoc);
                    inparam.set(AsStartInventoryCheckSCHParams.END_LOCATION, startLoc);
                }
                else
                {
                    inparam.set(AsStartInventoryCheckSCHParams.START_LOCATION, startLoc);
                    inparam.set(AsStartInventoryCheckSCHParams.END_LOCATION, endLoc);
                }
            }
            else
            {
                inparam.set(AsStartInventoryCheckSCHParams.START_LOCATION, startLoc);
                inparam.set(AsStartInventoryCheckSCHParams.END_LOCATION, endLoc);
            }


            // ステーションNoセット
            if (WmsParam.AUTO_SELECT_STATION.equals(pul_Station.getSelectedValue()))
            {
                // ステーションが自動振分けの場合、作業場をセット
                inparam.set(AsStartInventoryCheckSCHParams.STATION, pul_WorkPlace.getSelectedValue());
            }
            else
            {
                // それ以外はステーションをセット
                inparam.set(AsStartInventoryCheckSCHParams.STATION, pul_Station.getSelectedValue());
            }
            // DFKLOOK ここまで

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
            part11List.add(_pdm_pul_WorkPlace.getSelectedStringValue(), "");
            //DFKLOOK:ここから修正
            if (_pdm_pul_WorkPlace.getSelectedStringValue().equals(_pdm_pul_Station.getSelectedStringValue()))
            {
                part11List.add(WmsParam.AUTO_SELECT_STATION, "");
            }
            else
            {
                part11List.add(_pdm_pul_Station.getSelectedStringValue(), "");
            }
            part11List.add(WmsFormatter.toDispLocation(startLoc, loc), "");
            part11List.add(WmsFormatter.toDispLocation(endLoc, loc), "");
            //DFKLOOK:ここまで修正
            part11List.add(txt_StartItemCode.getStringValue(), "");
            part11List.add(txt_EndItemCode.getStringValue(), "");

            if (chk_CommonUse.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK ここから
            // 出庫指示送信へRMIメッセージを使用して出庫要求を行います。
            SendRequestor req = new SendRequestor();
            req.retrieval();

            if (chk_CommonUse.getChecked())
            {
                Object settingUkeys = inparam.get(AsStartInventoryCheckDASCHParams.SETTING_UKEYS);


                AsStartInventoryCheckDASCHParams daschparam = new AsStartInventoryCheckDASCHParams();
                daschparam.set(AsStartInventoryCheckDASCHParams.SETTING_UKEYS, settingUkeys);

                // check count.
                int count = dasch.count(inparam);
                // DFKLOOK ここから
                if (count == 0)
                {
                    message.setMsgResourceKey("6007042");
                    return;
                }
                // DFKLOOK ここまで

                // DASCH call.
                dasch.search(inparam);

                // open exporter.
                ExporterFactory factory = new WmsExporterFactory(locale, ui);
                exporter = factory.newPrinterExporter("InventoryCheckWorkList", false);
                exporter.open();

                // export.
                while (dasch.next())
                {
                    Params outparam = dasch.get();
                    InventoryCheckWorkListParams expparam = new InventoryCheckWorkListParams();
                    expparam.set(InventoryCheckWorkListParams.DFK_DS_NO, outparam.get(AsStartInventoryCheckDASCHParams.DFK_DS_NO));
                    expparam.set(InventoryCheckWorkListParams.DFK_USER_ID, outparam.get(AsStartInventoryCheckDASCHParams.DFK_USER_ID));
                    expparam.set(InventoryCheckWorkListParams.DFK_USER_NAME, outparam.get(AsStartInventoryCheckDASCHParams.DFK_USER_NAME));
                    expparam.set(InventoryCheckWorkListParams.SYS_DAY, outparam.get(AsStartInventoryCheckDASCHParams.SYS_DAY));
                    expparam.set(InventoryCheckWorkListParams.SYS_TIME, outparam.get(AsStartInventoryCheckDASCHParams.SYS_TIME));
                    expparam.set(InventoryCheckWorkListParams.STATION_NO, outparam.get(AsStartInventoryCheckDASCHParams.STATION_NO));
                    expparam.set(InventoryCheckWorkListParams.STATION_NAME, outparam.get(AsStartInventoryCheckDASCHParams.STATION_NAME));
                    expparam.set(InventoryCheckWorkListParams.WORK_NO, outparam.get(AsStartInventoryCheckDASCHParams.JOB_NO));
                    expparam.set(InventoryCheckWorkListParams.LOCATION_NO, outparam.get(AsStartInventoryCheckDASCHParams.LOCATION_NO));
                    expparam.set(InventoryCheckWorkListParams.ITEM_CODE, outparam.get(AsStartInventoryCheckDASCHParams.ITEM_CODE));
                    expparam.set(InventoryCheckWorkListParams.ITEM_NAME, outparam.get(AsStartInventoryCheckDASCHParams.ITEM_NAME));
                    expparam.set(InventoryCheckWorkListParams.LOT_NO, outparam.get(AsStartInventoryCheckDASCHParams.LOT_NO));
                    expparam.set(InventoryCheckWorkListParams.STOCK_QTY, outparam.get(AsStartInventoryCheckDASCHParams.STOCK_PIECE_QTY));
                    expparam.set(InventoryCheckWorkListParams.AREA_NO, outparam.get(AsStartInventoryCheckDASCHParams.AREA_NO));
                    if (!exporter.write(expparam))
                    {
                        break;
                    }
                }

                try
                {
                    exporter.print();
                    message.setMsgResourceKey("6001006");
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    message.setMsgResourceKey("6007042");
                    return;
                }

            }
            // DFKLOOK ここまで
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
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
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
        _pdm_pul_Area.setSelectedValue(null);
        _pdm_pul_WorkPlace.setSelectedValue(null);
        _pdm_pul_Station.setSelectedValue(null);
        txt_StartLocation.setValue(null);
        txt_EndLocation.setValue(null);
        txt_StartItemCode.setValue(null);
        txt_EndItemCode.setValue(null);

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
