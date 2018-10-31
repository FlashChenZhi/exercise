// $Id: InventoryDataAddBusiness.java 7538 2010-03-13 11:10:44Z ota $
package jp.co.daifuku.wms.inventorychk.display.dataadd;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;
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
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryDataAddSCH;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryDataAddSCHParams;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryInParameter;

/**
 * 新規棚卸データ追加の画面処理を行います。
 * 
 * @version $Revision: 7538 $, $Date: 2010-03-13 20:10:44 +0900 (土, 13 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class InventoryDataAddBusiness
        extends InventoryDataAdd
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    // DFKLOOK ここから修正
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    // DFKLOOK ここまで修正   
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
    public InventoryDataAddBusiness()
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
    	//DFKLOOK:ここから修正
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
        //DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから修正
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
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pul_Area_Change(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemCode_EnterKey(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから修正
        if (!viewState.getBooleanObject(ViewStateKeys.MASTER))
        {
            return;
        }
        setFocus(txt_LotNo);
        // DFKLOOK ここまで修正
        // process call.
        txt_ItemCode_EnterKey_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemCode_TabKey(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから修正
        if (!viewState.getBooleanObject(ViewStateKeys.MASTER))
        {
            setFocus(txt_ItemName);
            return;
        }
        setFocus(txt_LotNo);
        // DFKLOOK ここまで修正
        // process call.
        txt_ItemCode_EnterKey_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Set_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから修正
        // process call.
        btn_Set_Click_Process(null);
        // DFKLOOK ここまで修正
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
    public void btn_Close_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_Click_Process();
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
    // DFKLOOK ここから修正
    /**
     * 画面入力データのチェックを行います<BR>
     * 入力データが正常であれば、trueを返します。入力データが不正であれば、falseを返します。<BR>
     * @return boolean
     * @throws Exception 全ての例外を報告します
     */
    protected boolean checkInputData()
            throws Exception
    {

        // 入力値チェック
        txt_ListWorkNo.validate(this, false);
        pul_Area.validate(this, true);
        txt_Location.validate(this, true);
        txt_ItemCode.validate(this, true);
        txt_LotNo.validate(this, false);
        txt_EnteringQty.validate(this, false);
        txt_StockCaseQty.validate(this, false);
        txt_StockPieceQty.validate(this, false);

        return true;
    }

    /**
     * 画面入力の商品コードより商品マスタを取得します。<BR>
     * @throws Exception 全ての例外を報告します。
     * @return 商品マスタ。該当データがない場合はnull
     */
    protected Params searchItemMaster()
            throws Exception
    {
        // input validation.
        txt_ItemCode.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        InventoryDataAddSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new InventoryDataAddSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            InventoryDataAddSCHParams inparam = new InventoryDataAddSCHParams();
            inparam.set(InventoryDataAddSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(InventoryDataAddSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                return outparam;
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
        return null;
    }

    /**
     * 画面入力データのチェックを行います<BR>
     * 入力データが正常であれば、trueを返します。入力データが不正であれば、falseを返します。<BR>
     * @return boolean
     * @throws Exception 全ての例外を報告します
     */
    protected boolean check(String Location, String style)
            throws Exception
    {

        //ケース入数が0の場合
        if (txt_EnteringQty.getInt() == 0 && txt_StockCaseQty.getInt() != 0)
        {
            //ケース入数が0の場合、棚卸ケース数は入力できません。
            message.setMsgResourceKey("6023117");
            return false;
        }
        // 結果在庫数が在庫上限数以上の場合
        long qty = (txt_EnteringQty.getLong() * txt_StockCaseQty.getLong()) + txt_StockPieceQty.getLong();
        if (qty > WmsParam.MAX_STOCK_QTY)
        {
            // 6023186=棚卸数には在庫上限数{0}以下の値を入力してしてください。
            message.setMsgResourceKey(WmsMessageFormatter.format(6023186,
                    WmsFormatter.getNumFormat(WmsParam.MAX_STOCK_QTY)));
            return false;
        }

        String condition = this.viewState.getString(ViewStateKeys.CONDITION);
        // 条件指定方法：棚範囲指定の場合
        if (!InventoryInParameter.COLLECT_STATUS_LISTNO.equals(condition))
        {
            String locFrom = WmsFormatter.toParamLocation(this.viewState.getString(ViewStateKeys.LOCATION_FROM), style);
            String locTo = WmsFormatter.toParamLocation(this.viewState.getString(ViewStateKeys.LOCATION_TO), style);
            // 棚No.チェック
            String[] loc = WmsFormatter.getFromTo(locFrom, locTo);
            if (!StringUtil.isBlank(loc[0]))
            {
                if (Integer.valueOf(loc[0]).intValue() > Integer.valueOf(Location).intValue())
                {
                    // 棚卸範囲外の棚は入力できません。
                    message.setMsgResourceKey("6023201");
                    return false;
                }
            }
            if (!StringUtil.isBlank(loc[1]))
            {
                if (Integer.valueOf(loc[1]).intValue() < Integer.valueOf(Location).intValue())
                {
                    // 棚卸範囲外の棚は入力できません。
                    message.setMsgResourceKey("6023201");
                    return false;
                }
            }
        }
        return true;
    }

    // DFKLOOK ここまで修正

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
            _pdm_pul_Area.init(conn, AreaType.FLOOR_AND_TEMP_AND_RECEIVE, StationType.ALL, "", false);

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
        // DFKLOOK ここから修正
        if (!txt_ListWorkNo.getReadOnly())
        {
            setFocus(txt_ListWorkNo);
        }
        else
        {
            setFocus(txt_Location);
        }
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // output display.
        InventoryDataAddParams requestParam = new InventoryDataAddParams(request);
        _pdm_pul_Area.setSelectedValue(requestParam.get(InventoryDataAddParams.AREA_NO));
        viewState.setObject(ViewStateKeys.MASTER, requestParam.get(InventoryDataAddParams.MASTER));
        viewState.setObject(ViewStateKeys.CONDITION, requestParam.get(InventoryDataAddParams.CONDITION));
        viewState.setObject(ViewStateKeys.LIST_WORK_NO, requestParam.get(InventoryDataAddParams.LIST_WORK_NO));
        viewState.setObject(ViewStateKeys.AREA_NO, requestParam.get(InventoryDataAddParams.AREA_NO));
        viewState.setObject(ViewStateKeys.ITEM_CODE, requestParam.get(InventoryDataAddParams.ITEM_CODE));
        viewState.setObject(ViewStateKeys.LOCATION_FROM, requestParam.get(InventoryDataAddParams.LOCATION_FROM));
        viewState.setObject(ViewStateKeys.LOCATION_TO, requestParam.get(InventoryDataAddParams.LOCATION_TO));
        // DFKLOOK ここから修正
        String condition = this.viewState.getString(ViewStateKeys.CONDITION);
        String lstWorkNo = this.viewState.getString(ViewStateKeys.LIST_WORK_NO);
        String areaNo = this.viewState.getString(ViewStateKeys.AREA_NO);
        String itemCode = this.viewState.getString(ViewStateKeys.ITEM_CODE);

        if (requestParam.getBoolean(InventoryDataAddParams.MASTER))
        {
            txt_ItemName.setReadOnly(true);
            txt_EnteringQty.setReadOnly(true);
        }

        // 条件指定方法：リスト作業No.指定の場合
        if (InventoryInParameter.COLLECT_STATUS_LISTNO.equals(condition))
        {
            txt_ListWorkNo.setText(lstWorkNo);
            txt_ListWorkNo.setReadOnly(true);

            // 呼び出し画面よりエリア情報がセットされている場合
            if (!StringUtil.isBlank(areaNo))
            {
                pul_Area.setSelectedItem(areaNo);
                pul_Area.setEnabled(false);
                setFocus(txt_Location);
            }
            else
            {

                setFocus(pul_Area);
            }
        }
        // 条件指定方法：棚範囲指定の場合
        else
        {
            pul_Area.setSelectedItem(areaNo);
            pul_Area.setEnabled(false);
            if (!StringUtil.isBlank(itemCode))
            {
                txt_ItemCode.setText(itemCode);
                txt_ItemCode_EnterKey_Process();
                txt_ItemCode.setReadOnly(true);
            }
        }

        // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue()));


        // DFKLOOK ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void txt_ItemCode_EnterKey_Process()
            throws Exception
    {
        // DFKLOOK ここから修正
        //商品情報取得
        Params outparam = searchItemMaster();
        if (outparam != null)
        {
            txt_EnteringQty.setValue(outparam.get(InventoryDataAddSCHParams.ENTERING_QTY));
            txt_ItemName.setValue(outparam.get(InventoryDataAddSCHParams.ITEM_NAME));
        }
        else
        {
            txt_ItemName.setText("");
            txt_EnteringQty.setText("");
        }
        setFocus(txt_LotNo);
        // DFKLOOK ここまで修正
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

        // set focus.
        setFocus(txt_ItemCode);

    }

    /**
     *
     * @throws Exception
     */
    // DFKLOOK ここから修正
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    {
    	// 入力チェック
        if (!checkInputData())
        {
            // 不正があれば、処理を終了する。
            return;
        }

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        try
        {
            if (StringUtil.isBlank(eventSource))
            {
                String acstyle = SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue());
                WmsFormatter.toParamLocation(txt_Location.getStringValue(), acstyle);
                // show confirm message. 設定しますか？
                this.setConfirm("MSG-W9000", false, false);
                viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
                return;
            }

            conn = ConnectionManager.getConnection();

            // マスタパッケージありの場合は、商品情報取得
            if (viewState.getBooleanObject(ViewStateKeys.MASTER))
            {
                Params param = searchItemMaster();

                if (param != null)
                {
                    txt_EnteringQty.setValue(param.get(InventoryDataAddSCHParams.ENTERING_QTY));
                    txt_ItemName.setValue(param.get(InventoryDataAddSCHParams.ITEM_NAME));
                }
                else
                {
                    txt_ItemName.setText("");
                    txt_EnteringQty.setText("");
                    //商品コードがマスタに登録されていません。
                    message.setMsgResourceKey("6023021");
                    return;
                }
            }
            else
            {
                txt_EnteringQty.validate(this, false);
                txt_ItemName.validate(this, false);
            }

            InventoryDataAddSCH schedule = new InventoryDataAddSCH(conn, this.getClass(), locale, ui);
            InventoryDataAddSCHParams inParam = new InventoryDataAddSCHParams();
            
            // DFKLOOK start 3.4.1
            // 棚のフォーマットチェック
            String style = SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue());
            String loc = WmsFormatter.toParamLocation(txt_Location.getStringValue(), style);
            inParam.set(InventoryDataAddSCHParams.LOCATION_NO, loc);
            // DFKLOOK end  3.4.1

            // 入力チェック
            if (!check(loc, style))
            {
                // 不正があれば、処理を終了する。

                String condition = this.viewState.getString(ViewStateKeys.CONDITION);
                // 条件指定方法が「リスト作業No.」の場合
                if (InventoryInParameter.COLLECT_STATUS_LISTNO.equals(condition))
                {
                    setFocus(txt_Location);
                }
                // 上記以外
                else
                {
                    setFocus(txt_ListWorkNo);
                }
                return;
            }

            inParam.set(InventoryDataAddSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inParam.set(InventoryDataAddSCHParams.LIST_WORK_NO, txt_ListWorkNo.getText());
            inParam.set(InventoryDataAddSCHParams.AREA_NO, pul_Area.getSelectedValue());

            // 呼び出し画面よりエリアがしていない場合
            if (pul_Area.getEnabled())
            {
                inParam.set(InventoryDataAddSCHParams.WORKINFO_CHECK, true);
            }
            else
            {
                inParam.set(InventoryDataAddSCHParams.WORKINFO_CHECK, false);
            }

            //入力値チェック
            if (!schedule.check(inParam))
            {
                message.setMsgResourceKey(schedule.getMessage());
                return;
            }

            // output parameter.
            InventoryDataAddParams outparam = new InventoryDataAddParams();
            outparam.set(InventoryDataAddParams.LIST_WORK_NO, txt_ListWorkNo.getValue());
            outparam.set(InventoryDataAddParams.AREA_NO, _pdm_pul_Area.getSelectedValue());

            //DFKLOOK:ここから修正
            outparam.set(InventoryDataAddParams.LOCATION_NO, loc);
            //DFKLOOK:ここまで修正

            outparam.set(InventoryDataAddParams.ITEM_CODE, txt_ItemCode.getValue());
            outparam.set(InventoryDataAddParams.ITEM_NAME, txt_ItemName.getValue());
            outparam.set(InventoryDataAddParams.LOT_NO, txt_LotNo.getValue());
            outparam.set(InventoryDataAddParams.ENTERING_QTY, txt_EnteringQty.getValue());
            outparam.set(InventoryDataAddParams.RESULT_CASE_QTY, txt_StockCaseQty.getInt());
            outparam.set(InventoryDataAddParams.RESULT_PIECE_QTY, txt_StockPieceQty.getInt());
            outparam.set(InventoryDataAddParams.STOCK_CASE_QTY, 0);
            outparam.set(InventoryDataAddParams.STOCK_PIECE_QTY, 0);
            // DFKLOOK ここから修正
            outparam.set(InventoryDataAddParams.NEWDATE_FLAG, true);
            // DFKLOOK ここまで修正

            ForwardParameters forwardParam = outparam.toForwardParameters();
            forwardParam.setParameter(_KEY_POPUPSOURCE, viewState.getString(_KEY_POPUPSOURCE));
            parentRedirect(forwardParam);
            dispose();

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
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException es)
            {
                message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(es, this));
            }
        }

        // DFKLOOK ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_ListWorkNo.setText("");
        pul_Area.setSelectedIndex(0);
        txt_Location.setText("");
        txt_ItemCode.setText("");
        txt_ItemName.setText("");
        txt_LotNo.setText("");
        txt_EnteringQty.setText("");
        txt_StockCaseQty.setText("");
        txt_StockPieceQty.setText("");

        // DFKLOOK ここから修正
        String condition = this.viewState.getString(ViewStateKeys.CONDITION);
        String lstWorkNo = this.viewState.getString(ViewStateKeys.LIST_WORK_NO);
        String areaNo = this.viewState.getString(ViewStateKeys.AREA_NO);
        String itemCode = this.viewState.getString(ViewStateKeys.ITEM_CODE);
        // 条件指定方法：リスト作業No.指定の場合
        if (InventoryInParameter.COLLECT_STATUS_LISTNO.equals(condition))
        {
            txt_ListWorkNo.setText(lstWorkNo);
            txt_ListWorkNo.setReadOnly(true);
            pul_Area.setSelectedItem(areaNo);
            pul_Area.setEnabled(false);
            setFocus(txt_Location);
        }
        // 条件指定方法：棚範囲指定の場合
        else
        {
            pul_Area.setSelectedItem(areaNo);
            pul_Area.setEnabled(false);
            if (!StringUtil.isBlank(itemCode))
            {
                txt_ItemCode.setText(itemCode);
                txt_ItemCode_EnterKey_Process();
                txt_ItemCode.setReadOnly(true);
            }
        }

        // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(pul_Area.getSelectedValue()));

        // DFKLOOK ここまで修正

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
