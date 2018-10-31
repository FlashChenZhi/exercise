// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.stock.display.fashelfinquiry;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.LocationFormatException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.listbox.itemname.LstItemNameParams;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.stock.dasch.FaShelfInquiryDASCH;
import jp.co.daifuku.wms.stock.dasch.FaShelfInquiryDASCHParams;
import jp.co.daifuku.wms.stock.exporter.StockByLocationListParams;

/**
 * 棚別在庫照会の画面処理を行います。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class FaShelfInquiryBusiness
        extends FaShelfInquiry
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_FaShelfInquiry(HIDDEN_ITEM_NAME) */
    private static final ListCellKey KEY_HIDDEN_ITEM_NAME = new ListCellKey("HIDDEN_ITEM_NAME", new StringCellColumn(), false, false);

    /** lst_FaShelfInquiry(HIDDEN_LOCATION_STATUS) */
    private static final ListCellKey KEY_HIDDEN_LOCATION_STATUS = new ListCellKey("HIDDEN_LOCATION_STATUS", new StringCellColumn(), false, false);

    /** lst_FaShelfInquiry(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_FaShelfInquiry(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO = new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_FaShelfInquiry(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_FaShelfInquiry(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_FaShelfInquiry(LST_LAST_PICKING_DATE) */
    private static final ListCellKey KEY_LST_LAST_PICKING_DATE = new ListCellKey("LST_LAST_PICKING_DATE", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_FaShelfInquiry(LST_STORAGE_DATETIME) */
    private static final ListCellKey KEY_LST_STORAGE_DATETIME = new ListCellKey("LST_STORAGE_DATETIME", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), true, false);

    /** lst_FaShelfInquiry(LST_STOCK_QTY) */
    private static final ListCellKey KEY_LST_STOCK_QTY = new ListCellKey("LST_STOCK_QTY", new NumberCellColumn(0), true, false);

    /** lst_FaShelfInquiry keys */
    private static final ListCellKey[] LST_FASHELFINQUIRY_KEYS = {
        KEY_HIDDEN_ITEM_NAME,
        KEY_HIDDEN_LOCATION_STATUS,
        KEY_LST_NO,
        KEY_LST_LOCATION_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT_NO,
        KEY_LST_LAST_PICKING_DATE,
        KEY_LST_STORAGE_DATETIME,
        KEY_LST_STOCK_QTY,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_FaShelfInquiry */
    private ListCellModel _lcm_lst_FaShelfInquiry;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaShelfInquiryBusiness()
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
        if (eventSource.equals("btn_SearchItemCode_Click"))
        {
            // process call.
            btn_SearchItemCode_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_SearchItemName_Click"))
        {
            // process call.
            btn_SearchItemName_Click_DlgBack(dialogParams);
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
        if (eventSource.equals("btn_Print_Click"))
        {
            // process call.
            btn_Print_Click_Process(false);
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
        chengeArea();
        //DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemCode_EntEvent_EnterKey(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        if (StringUtil.isBlank(txt_ItemCode_EntEvent.getValue()))
        {
            setFocus(txt_ItemName);
            return;
        }

        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            Item item =
                    ItemController.getItemInfo(txt_ItemCode_EntEvent.getText(), WmsParam.DEFAULT_CONSIGNOR_CODE, conn);

            if (item == null)
            {
                // 6023021 = 商品コードがマスタに登録されていません。
                message.setMsgResourceKey("6023021");
                setFocus(txt_ItemCode_EntEvent);
                return;
            }

            // output display.
            txt_ItemName.setValue(item.getItemName());

            // set focus.
            setFocus(pul_Area);
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
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchItemCode_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchItemCode_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchItemName_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchItemName_Click_Process();
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
    public void btn_Print_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Print_Click_Process(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Preview_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Preview_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_XLS_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_XLS_Click_Process();
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
    public void pager_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_Prev(ActionEvent e)
            throws Exception
    {
        _pager.previous();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_Next(ActionEvent e)
            throws Exception
    {
        _pager.next();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_Last(ActionEvent e)
            throws Exception
    {
        _pager.last();
        pager_SetPage();
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
     * 画面でチェックされた棚状態を取得
     * 
     * @return String 棚状態
     */
    protected String getLocationStatus()
    {
        // 前項目にチェックが付与されていたかどうか判断
        boolean checked = false;
        // 生成文字列を格納
        String locationStatus = "";

        // 実棚(チェック)
        if (chk_StoredLocation.getChecked())
        {
            checked = true;
            locationStatus = DisplayText.getText("LBL-W0104");
        }

        // 空棚(チェック)
        if (chk_EmptyLocation.getChecked())
        {
            // チェックが付与されている場合は複合文字を追加
            if (checked)
            {
                locationStatus = locationStatus + ",";
            }
            locationStatus = locationStatus + DisplayText.getText("LBL-W0061");
            checked = true;
        }

        // 作業棚(チェック)
        if (chk_WorkLocation.getChecked())
        {
            if (checked)
            {
                locationStatus = locationStatus + ",";
            }
            locationStatus = locationStatus + DisplayText.getText("LBL-W0237");
            checked = true;
        }

        // 禁止棚(チェック)
        if (chk_ProhibitedLocation.getChecked())
        {
            if (checked)
            {
                locationStatus = locationStatus + ",";
            }
            locationStatus = locationStatus + DisplayText.getText("LBL-W0059");
            checked = true;
        }

        // アクセス不可棚(チェック)
        if (chk_UnreachableLocation.getChecked())
        {
            if (checked)
            {
                locationStatus = locationStatus + ",";
            }
            locationStatus = locationStatus + DisplayText.getText("CHK-W0032");
            checked = true;
        }

        // 生成した文字列を返却
        return locationStatus;
    }

    /**
     * ロード時、エリアチェック時のイベント
     * 
     * @throws Exception
     */
    protected void chengeArea()
            throws Exception
    {
        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            AreaController aCtrl = new AreaController(conn, this.getClass());

            // AS/RSエリアの場合
            if (SystemDefine.AREA_TYPE_ASRS.equals(aCtrl.getAreaType(_pdm_pul_Area.getSelectedStringValue())))
            {
                // 棚状態を使用可能にします。
                chk_StoredLocation.setEnabled(true);
                chk_EmptyLocation.setEnabled(true);
                chk_ProhibitedLocation.setEnabled(true);
                chk_UnreachableLocation.setEnabled(true);
                chk_WorkLocation.setEnabled(true);

                // 実棚のみにチェックします。
                chk_StoredLocation.setChecked(true);
                chk_EmptyLocation.setChecked(false);
                chk_ProhibitedLocation.setChecked(false);
                chk_UnreachableLocation.setChecked(false);
                chk_WorkLocation.setChecked(false);
            }
            // 平置エリアの場合
            else
            {
                // フリー管理エリアの場合
                if (SystemDefine.LOCATION_TYPE_FREE.equals(aCtrl.getLocationType(_pdm_pul_Area.getSelectedStringValue())))
                {
                    // 実棚以外を使用不可にします。
                    chk_EmptyLocation.setEnabled(false);
                    chk_ProhibitedLocation.setEnabled(false);
                    chk_UnreachableLocation.setEnabled(false);
                    chk_WorkLocation.setEnabled(false);

                    // 実棚のみにチェックします。
                    chk_StoredLocation.setChecked(true);
                    chk_EmptyLocation.setChecked(false);
                    chk_ProhibitedLocation.setChecked(false);
                    chk_UnreachableLocation.setChecked(false);
                    chk_WorkLocation.setChecked(false);
                }
                // 棚管理エリアの場合
                else
                {
                    // 実棚・空棚以外を使用不可にします。
                    chk_EmptyLocation.setEnabled(true);
                    chk_ProhibitedLocation.setEnabled(false);
                    chk_UnreachableLocation.setEnabled(false);
                    chk_WorkLocation.setEnabled(false);

                    // 実棚のみにチェックします。
                    chk_StoredLocation.setChecked(true);
                    chk_EmptyLocation.setChecked(false);
                    chk_ProhibitedLocation.setChecked(false);
                    chk_UnreachableLocation.setChecked(false);
                    chk_WorkLocation.setChecked(false);
                }
            }

            // 棚フォーマット設定
            String loc = SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue());
            lbl_In_LocationStyle.setText(loc);
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

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pager}, locale);

        // initialize lst_FaShelfInquiry.
        _lcm_lst_FaShelfInquiry = new ListCellModel(lst_FaShelfInquiry, LST_FASHELFINQUIRY_KEYS, locale);
        _lcm_lst_FaShelfInquiry.setToolTipVisible(KEY_LST_NO, true);
        _lcm_lst_FaShelfInquiry.setToolTipVisible(KEY_LST_LOCATION_NO, true);
        _lcm_lst_FaShelfInquiry.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_FaShelfInquiry.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_FaShelfInquiry.setToolTipVisible(KEY_LST_LAST_PICKING_DATE, true);
        _lcm_lst_FaShelfInquiry.setToolTipVisible(KEY_LST_STORAGE_DATETIME, true);
        _lcm_lst_FaShelfInquiry.setToolTipVisible(KEY_LST_STOCK_QTY, true);

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

            //DFKLOOK:ここから修正
            // load pul_Area.
            _pdm_pul_Area.init(conn, AreaType.ALL, StationType.STORAGE, "", false);
            //DFKLOOK:ここまで修正

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
        // dispose DASCH.
        disposeDasch();
    }

    /**
     *
     * @throws Exception
     */
    private void disposeDasch()
            throws Exception
    {
        // disposing DASCH.
        DataAccessSCH dasch = (DataAccessSCH)session.getAttribute(_KEY_DASCH);
        if (dasch != null)
        {
            session.removeAttribute(_KEY_DASCH);
            dasch.close();
            DBUtil.close(dasch.getConnection());
        }
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_FaShelfInquiry_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);

        //DFKLOOK:ここから修正
        line.addToolTip("LBL-W0130", KEY_HIDDEN_ITEM_NAME);
        //DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void pager_SetPage()
            throws Exception
    {
        // get event source.
        String eventSource = viewState.getString(_KEY_PAGERSOURCE);
        if (eventSource == null)
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_Display_Click"))
        {
            // process call.
            btn_Display_Click_SetList();
        }
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
        _pdm_pul_Area.setSelectedValue(null);
        chk_StoredLocation.setChecked(true);
        chk_EmptyLocation.setChecked(false);
        chk_WorkLocation.setChecked(false);
        chk_ProhibitedLocation.setChecked(false);
        chk_UnreachableLocation.setChecked(false);
        txt_FromLocation.setValue(null);
        txt_ToLocation.setValue(null);
        lbl_In_LocationStyle.setValue(null);
        txt_ItemCode_EntEvent.setValue(null);
        txt_ItemName.setValue(null);
        _lcm_lst_FaShelfInquiry.clear();

        //DFKLOOK:ここから修正
        chengeArea();
        //DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchItemCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode_EntEvent.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchItemCode_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchItemCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_ItemCode_EntEvent.setValue(outparam.get(LstItemParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemParams.ITEM_NAME));

        // set focus.
        setFocus(txt_ItemCode_EntEvent);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchItemName_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemNameParams inparam = new LstItemNameParams();
        inparam.set(LstItemNameParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstItemNameParams.ITEM_NAME, txt_ItemName.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchItemName_Click");
        redirect("/base/listbox/itemname/LstItemName.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchItemName_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemNameParams outparam = new LstItemNameParams(dialogParams);
        txt_ItemCode_EntEvent.setValue(outparam.get(LstItemNameParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemNameParams.ITEM_NAME));

        // set focus.
        setFocus(txt_ItemName);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        //DFKLOOK:ここから修正
        if (!chk_StoredLocation.getChecked() && !chk_EmptyLocation.getChecked() && !chk_WorkLocation.getChecked()
                && !chk_ProhibitedLocation.getChecked() && !chk_UnreachableLocation.getChecked())
        {
            //6023274=棚状態は一つ以上の項目を選択してください。
            message.setMsgResourceKey("6023274");
            setFocus(chk_StoredLocation);
            return;
        }

        // 入力チェック
        if (!StringUtil.isBlank(txt_ItemCode_EntEvent.getText()))
        {
            txt_ItemCode_EntEvent.validate(this, false);
        }
        if (!StringUtil.isBlank(txt_ItemName.getText()))
        {
            txt_ItemName.validate(this, false);
        }
        //DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaShelfInquiryDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new FaShelfInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            FaShelfInquiryDASCHParams inparam = new FaShelfInquiryDASCHParams();
            inparam.set(FaShelfInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(FaShelfInquiryDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaShelfInquiryDASCHParams.STORED_LOCATION, chk_StoredLocation.getValue());
            inparam.set(FaShelfInquiryDASCHParams.EMPTY_LOCATION, chk_EmptyLocation.getValue());
            inparam.set(FaShelfInquiryDASCHParams.WORK_LOCATION, chk_WorkLocation.getValue());
            inparam.set(FaShelfInquiryDASCHParams.PROHIBITED_LOCATION, chk_ProhibitedLocation.getValue());
            inparam.set(FaShelfInquiryDASCHParams.UNREACHABLE_LOCATION, chk_UnreachableLocation.getValue());

            //DFKLOOK:ここから修正
            //エリアから棚のフォーマットを取得しフォーマットを外してSCHに送る
            String locFormat = SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue());
            inparam.set(FaShelfInquiryDASCHParams.FROM_LOCATION_NO, WmsFormatter.toParamLocation(
                    txt_FromLocation.getText(), locFormat));
            inparam.set(FaShelfInquiryDASCHParams.TO_LOCATION_NO, WmsFormatter.toParamLocation(
                    txt_ToLocation.getText(), locFormat));
            //DFKLOOK:ここまで修正

            inparam.set(FaShelfInquiryDASCHParams.ITEM_CODE, txt_ItemCode_EntEvent.getValue());
            inparam.set(FaShelfInquiryDASCHParams.ITEM_NAME, txt_ItemName.getValue());

            // get count.
            int count = dasch.count(inparam);

            //DFKLOOK:ここから修正
            _pager.clear();
            //DFKLOOK:ここまで修正

            _pager.setMax(count);
            _lcm_lst_FaShelfInquiry.clear();

            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }
            else if (count > _pager.getMax())
            {
                message.setMsgResourceKey("6001023\t" + Formatter.getNumFormat(count)
                        + "\t" + Formatter.getNumFormat(_pager.getMax()));
            }
            else
            {
                message.setMsgResourceKey("6001022\t" + Formatter.getNumFormat(count));
            }

            // DASCH call.
            dasch.search(inparam);

            // save session.
            session.setAttribute(_KEY_DASCH, dasch);
            isSuccess = true;

        }
        //DFKLOOK:ここから修正
        // 棚フォーマットで投げられたExceptionをここでキャッチ
        catch (LocationFormatException ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaShelfInquiry.clear();
        }
        //DFKLOOK:ここまで修正
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaShelfInquiry.clear();
        }
        finally
        {
            if (isSuccess)
            {
                // set list.
                btn_Display_Click_SetList();
            }
            else
            {
                if (dasch != null)
                {
                    dasch.close();
                }
                DBUtil.close(conn);
            }
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_SetList()
            throws Exception
    {
        //DFKLOOK:ここから修正
        Connection conn = null;
        //DFKLOOK:ここまで修正

        FaShelfInquiryDASCH dasch = null;
        try
        {
            //DFKLOOK:ここから修正
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            //DFKLOOK:ここまで修正

            // get session.
            dasch = (FaShelfInquiryDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_FaShelfInquiry.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_FaShelfInquiry.getNewLine();
                line.setValue(KEY_HIDDEN_ITEM_NAME, outparam.get(FaShelfInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_NO, outparam.get(FaShelfInquiryDASCHParams.NO));

                //DFKLOOK:ここから修正
                AreaController aCtrl = new AreaController(conn, getClass());
                String location =
                        aCtrl.toDispLocation(_pdm_pul_Area.getSelectedStringValue(),
                                outparam.getString(FaShelfInquiryDASCHParams.LOCATION_NO))
                                + "：" + outparam.get(FaShelfInquiryDASCHParams.LOCATION_STATUS);
                line.setValue(KEY_LST_LOCATION_NO, location);
                //DFKLOOK:ここまで修正

                line.setValue(KEY_LST_ITEM_CODE, outparam.get(FaShelfInquiryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_LOT_NO, outparam.get(FaShelfInquiryDASCHParams.LOT_NO));

                //DFKLOOK:ここから修正
                line.setValue(KEY_LST_LAST_PICKING_DATE, WmsFormatter.toDispDate(
                        outparam.getString(FaShelfInquiryDASCHParams.LAST_PICKING_DATE), httpRequest.getLocale()));
                //DFKLOOK:ここまで修正

                line.setValue(KEY_LST_STORAGE_DATETIME, outparam.get(FaShelfInquiryDASCHParams.STORAGE_DATETIME));
                line.setValue(KEY_LST_STOCK_QTY, outparam.get(FaShelfInquiryDASCHParams.STOCK_QTY));
                line.setValue(KEY_HIDDEN_LOCATION_STATUS, outparam.get(FaShelfInquiryDASCHParams.LOCATION_STATUS));
                lst_FaShelfInquiry_SetLineToolTip(line);
                _lcm_lst_FaShelfInquiry.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaShelfInquiry.clear();
            disposeDasch();
        }
        //DFKLOOK:ここから修正
        finally
        {
            DBUtil.close(conn);
        }
        //DFKLOOK:ここから修正
    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_Print_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        txt_FromLocation.validate(this, false);
        txt_ToLocation.validate(this, false);
        txt_ItemCode_EntEvent.validate(this, false);
        txt_ItemName.validate(this, false);

        //DFKLOOK:ここから修正
        if (!chk_StoredLocation.getChecked() && !chk_EmptyLocation.getChecked() && !chk_WorkLocation.getChecked()
                && !chk_ProhibitedLocation.getChecked() && !chk_UnreachableLocation.getChecked())
        {
            //6023274=棚状態は一つ以上の項目を選択してください。
            message.setMsgResourceKey("6023274");
            setFocus(chk_StoredLocation);
            return;
        }
        //DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaShelfInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new FaShelfInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaShelfInquiryDASCHParams inparam = new FaShelfInquiryDASCHParams();
            inparam.set(FaShelfInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(FaShelfInquiryDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaShelfInquiryDASCHParams.STORED_LOCATION, chk_StoredLocation.getValue());
            inparam.set(FaShelfInquiryDASCHParams.EMPTY_LOCATION, chk_EmptyLocation.getValue());
            inparam.set(FaShelfInquiryDASCHParams.WORK_LOCATION, chk_WorkLocation.getValue());
            inparam.set(FaShelfInquiryDASCHParams.PROHIBITED_LOCATION, chk_ProhibitedLocation.getValue());
            inparam.set(FaShelfInquiryDASCHParams.UNREACHABLE_LOCATION, chk_UnreachableLocation.getValue());

            //DFKLOOK:ここから修正
            //エリアから棚のフォーマットを取得しフォーマットを外してSCHに送る
            String locFormat = SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue());
            inparam.set(FaShelfInquiryDASCHParams.FROM_LOCATION_NO, WmsFormatter.toParamLocation(
                    txt_FromLocation.getText(), locFormat));
            inparam.set(FaShelfInquiryDASCHParams.TO_LOCATION_NO, WmsFormatter.toParamLocation(
                    txt_ToLocation.getText(), locFormat));
            //DFKLOOK:ここまで修正

            inparam.set(FaShelfInquiryDASCHParams.ITEM_CODE, txt_ItemCode_EntEvent.getValue());
            inparam.set(FaShelfInquiryDASCHParams.ITEM_NAME, txt_ItemName.getValue());

            // check count.
            int count = dasch.count(inparam);
            if (confirm && count > 0)
            {
                // show confirm message.
                this.setConfirm("MSG-W0018\t" + Formatter.getNumFormat(count), false, true);
                viewState.setString(_KEY_CONFIRMSOURCE, "btn_Print_Click");
                return;
            }
            else if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("StockByLocationList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                StockByLocationListParams expparam = new StockByLocationListParams();
                expparam.set(StockByLocationListParams.DFK_DS_NO, outparam.get(FaShelfInquiryDASCHParams.DFK_DS_NO));
                expparam.set(StockByLocationListParams.DFK_USER_ID, outparam.get(FaShelfInquiryDASCHParams.DFK_USER_ID));
                expparam.set(StockByLocationListParams.DFK_USER_NAME, outparam.get(FaShelfInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(StockByLocationListParams.SYS_DAY, outparam.get(FaShelfInquiryDASCHParams.SYS_DAY));
                expparam.set(StockByLocationListParams.SYS_TIME, outparam.get(FaShelfInquiryDASCHParams.SYS_TIME));
                expparam.set(StockByLocationListParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
                expparam.set(StockByLocationListParams.SEARCH_ITEM_CODE, outparam.get(FaShelfInquiryDASCHParams.ITEM_CODE));

                //DFKLOOK:ここから修正
                expparam.set(StockByLocationListParams.SEARCH_LOCATION_STATUS, getLocationStatus());
                expparam.set(StockByLocationListParams.FROM_LOCATION_NO, WmsFormatter.toParamLocation(
                        txt_FromLocation.getText(), locFormat));
                expparam.set(StockByLocationListParams.TO_LOCATION_NO, WmsFormatter.toParamLocation(
                        txt_ToLocation.getText(), locFormat));
                //DFKLOOK:ここまで修正

                expparam.set(StockByLocationListParams.LOCATION_NO, outparam.get(FaShelfInquiryDASCHParams.LOCATION_NO));
                expparam.set(StockByLocationListParams.LOCATION_STATUS, outparam.get(FaShelfInquiryDASCHParams.LOCATION_STATUS));
                expparam.set(StockByLocationListParams.ITEM_CODE, outparam.get(FaShelfInquiryDASCHParams.ITEM_CODE));
                expparam.set(StockByLocationListParams.ITEM_NAME, outparam.get(FaShelfInquiryDASCHParams.ITEM_NAME));
                expparam.set(StockByLocationListParams.LOT_NO, outparam.get(FaShelfInquiryDASCHParams.LOT_NO));
                expparam.set(StockByLocationListParams.STORAGE_DATE, outparam.get(FaShelfInquiryDASCHParams.STORAGE_DATETIME));
                expparam.set(StockByLocationListParams.STOCK_QTY, outparam.get(FaShelfInquiryDASCHParams.STOCK_QTY));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // execute print.
            try
            {
                exporter.print();
                message.setMsgResourceKey("6001010");
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                message.setMsgResourceKey("6007034");
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");

            if (chk_StoredLocation.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_EmptyLocation.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_WorkLocation.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_ProhibitedLocation.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_UnreachableLocation.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            part11List.add(txt_FromLocation.getStringValue(), "");
            part11List.add(txt_ToLocation.getStringValue(), "");
            part11List.add(txt_ItemCode_EntEvent.getStringValue(), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PRINT), part11List);
            // commit.
            conn.commit();

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
    private void btn_Preview_Click_Process()
            throws Exception
    {
        // input validation.
        txt_FromLocation.validate(this, false);
        txt_ToLocation.validate(this, false);
        txt_ItemCode_EntEvent.validate(this, false);
        txt_ItemName.validate(this, false);

        //DFKLOOK:ここから修正
        if (!chk_StoredLocation.getChecked() && !chk_EmptyLocation.getChecked() && !chk_WorkLocation.getChecked()
                && !chk_ProhibitedLocation.getChecked() && !chk_UnreachableLocation.getChecked())
        {
            //6023274=棚状態は一つ以上の項目を選択してください。
            message.setMsgResourceKey("6023274");
            setFocus(chk_StoredLocation);
            return;
        }
        //DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaShelfInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new FaShelfInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaShelfInquiryDASCHParams inparam = new FaShelfInquiryDASCHParams();
            inparam.set(FaShelfInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(FaShelfInquiryDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaShelfInquiryDASCHParams.STORED_LOCATION, chk_StoredLocation.getValue());
            inparam.set(FaShelfInquiryDASCHParams.EMPTY_LOCATION, chk_EmptyLocation.getValue());
            inparam.set(FaShelfInquiryDASCHParams.WORK_LOCATION, chk_WorkLocation.getValue());
            inparam.set(FaShelfInquiryDASCHParams.PROHIBITED_LOCATION, chk_ProhibitedLocation.getValue());
            inparam.set(FaShelfInquiryDASCHParams.UNREACHABLE_LOCATION, chk_UnreachableLocation.getValue());

            //DFKLOOK:ここから修正
            //エリアから棚のフォーマットを取得しフォーマットを外してSCHに送る
            String locFormat = SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue());
            inparam.set(FaShelfInquiryDASCHParams.FROM_LOCATION_NO, WmsFormatter.toParamLocation(
                    txt_FromLocation.getText(), locFormat));
            inparam.set(FaShelfInquiryDASCHParams.TO_LOCATION_NO, WmsFormatter.toParamLocation(
                    txt_ToLocation.getText(), locFormat));
            //DFKLOOK:ここまで修正

            inparam.set(FaShelfInquiryDASCHParams.ITEM_CODE, txt_ItemCode_EntEvent.getValue());
            inparam.set(FaShelfInquiryDASCHParams.ITEM_NAME, txt_ItemName.getValue());

            // check count.
            int count = dasch.count(inparam);
            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPVExporter("StockByLocationList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                StockByLocationListParams expparam = new StockByLocationListParams();
                expparam.set(StockByLocationListParams.DFK_DS_NO, outparam.get(FaShelfInquiryDASCHParams.DFK_DS_NO));
                expparam.set(StockByLocationListParams.DFK_USER_ID, outparam.get(FaShelfInquiryDASCHParams.DFK_USER_ID));
                expparam.set(StockByLocationListParams.DFK_USER_NAME, outparam.get(FaShelfInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(StockByLocationListParams.SYS_DAY, outparam.get(FaShelfInquiryDASCHParams.SYS_DAY));
                expparam.set(StockByLocationListParams.SYS_TIME, outparam.get(FaShelfInquiryDASCHParams.SYS_TIME));
                expparam.set(StockByLocationListParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
                expparam.set(StockByLocationListParams.SEARCH_ITEM_CODE, outparam.get(FaShelfInquiryDASCHParams.ITEM_CODE));

                //DFKLOOK:ここから修正
                expparam.set(StockByLocationListParams.SEARCH_LOCATION_STATUS, getLocationStatus());
                expparam.set(StockByLocationListParams.FROM_LOCATION_NO, WmsFormatter.toParamLocation(
                        txt_FromLocation.getText(), locFormat));
                expparam.set(StockByLocationListParams.TO_LOCATION_NO, WmsFormatter.toParamLocation(
                        txt_ToLocation.getText(), locFormat));
                //DFKLOOK:ここまで修正

                expparam.set(StockByLocationListParams.LOCATION_NO, outparam.get(FaShelfInquiryDASCHParams.LOCATION_NO));
                expparam.set(StockByLocationListParams.LOCATION_STATUS, outparam.get(FaShelfInquiryDASCHParams.LOCATION_STATUS));
                expparam.set(StockByLocationListParams.ITEM_CODE, outparam.get(FaShelfInquiryDASCHParams.ITEM_CODE));
                expparam.set(StockByLocationListParams.ITEM_NAME, outparam.get(FaShelfInquiryDASCHParams.ITEM_NAME));
                expparam.set(StockByLocationListParams.LOT_NO, outparam.get(FaShelfInquiryDASCHParams.LOT_NO));
                expparam.set(StockByLocationListParams.STORAGE_DATE, outparam.get(FaShelfInquiryDASCHParams.STORAGE_DATETIME));
                expparam.set(StockByLocationListParams.STOCK_QTY, outparam.get(FaShelfInquiryDASCHParams.STOCK_QTY));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // execute print.
            try
            {
                downloadFile = exporter.print();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                message.setMsgResourceKey("6007034");
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");

            if (chk_StoredLocation.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_EmptyLocation.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_WorkLocation.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_ProhibitedLocation.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_UnreachableLocation.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            part11List.add(txt_FromLocation.getStringValue(), "");
            part11List.add(txt_ToLocation.getStringValue(), "");
            part11List.add(txt_ItemCode_EntEvent.getStringValue(), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PREVIEW), part11List);
            // commit.
            conn.commit();

            // redirect.
            previewPDF(downloadFile.getPath());

            // DFKLOOK start
            setFocus(null);
            // DFKLOOK end
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
    private void btn_XLS_Click_Process()
            throws Exception
    {
        // input validation.
        txt_FromLocation.validate(this, false);
        txt_ToLocation.validate(this, false);
        txt_ItemCode_EntEvent.validate(this, false);
        txt_ItemName.validate(this, false);

        //DFKLOOK:ここから修正
        if (!chk_StoredLocation.getChecked() && !chk_EmptyLocation.getChecked() && !chk_WorkLocation.getChecked()
                && !chk_ProhibitedLocation.getChecked() && !chk_UnreachableLocation.getChecked())
        {
            //6023274=棚状態は一つ以上の項目を選択してください。
            message.setMsgResourceKey("6023274");
            setFocus(chk_StoredLocation);
            return;
        }
        //DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaShelfInquiryDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new FaShelfInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaShelfInquiryDASCHParams inparam = new FaShelfInquiryDASCHParams();
            inparam.set(FaShelfInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(FaShelfInquiryDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaShelfInquiryDASCHParams.STORED_LOCATION, chk_StoredLocation.getValue());
            inparam.set(FaShelfInquiryDASCHParams.EMPTY_LOCATION, chk_EmptyLocation.getValue());
            inparam.set(FaShelfInquiryDASCHParams.WORK_LOCATION, chk_WorkLocation.getValue());
            inparam.set(FaShelfInquiryDASCHParams.PROHIBITED_LOCATION, chk_ProhibitedLocation.getValue());
            inparam.set(FaShelfInquiryDASCHParams.UNREACHABLE_LOCATION, chk_UnreachableLocation.getValue());

            //DFKLOOK:ここから修正
            //エリアから棚のフォーマットを取得しフォーマットを外してSCHに送る
            String locFormat = SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue());
            inparam.set(FaShelfInquiryDASCHParams.FROM_LOCATION_NO, WmsFormatter.toParamLocation(
                    txt_FromLocation.getText(), locFormat));
            inparam.set(FaShelfInquiryDASCHParams.TO_LOCATION_NO, WmsFormatter.toParamLocation(
                    txt_ToLocation.getText(), locFormat));
            //DFKLOOK:ここまで修正

            inparam.set(FaShelfInquiryDASCHParams.ITEM_CODE, txt_ItemCode_EntEvent.getValue());
            inparam.set(FaShelfInquiryDASCHParams.ITEM_NAME, txt_ItemName.getValue());

            // check count.
            int count = dasch.count(inparam);
            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newExcelExporter("StockByLocationList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                StockByLocationListParams expparam = new StockByLocationListParams();
                expparam.set(StockByLocationListParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
                expparam.set(StockByLocationListParams.LOCATION_NO, outparam.get(FaShelfInquiryDASCHParams.LOCATION_NO));

                //DFKLOOK:ここから修正
                expparam.set(StockByLocationListParams.LOCATION_STATUS, getLocationStatus());
                //DFKLOOK:ここまで修正

                expparam.set(StockByLocationListParams.ITEM_CODE, outparam.get(FaShelfInquiryDASCHParams.ITEM_CODE));
                expparam.set(StockByLocationListParams.ITEM_NAME, outparam.get(FaShelfInquiryDASCHParams.ITEM_NAME));
                expparam.set(StockByLocationListParams.LOT_NO, outparam.get(FaShelfInquiryDASCHParams.LOT_NO));
                expparam.set(StockByLocationListParams.STORAGE_DATE, outparam.get(FaShelfInquiryDASCHParams.STORAGE_DATETIME));
                expparam.set(StockByLocationListParams.STOCK_QTY, outparam.get(FaShelfInquiryDASCHParams.STOCK_QTY));
                if (!exporter.write(expparam))
                {
                    //DFKLOOK start
                    // XLS最大件数出力メッセージ対応
                    message.setMsgResourceKey("6001031\t"
                            + Formatter.getNumFormat(count) + "\t"
                            + Formatter.getNumFormat(CommonParam.getIntParam("MAX_NUMBER_OF_XLS")
                                    - exporter.getHeaderRowsCount()));
                    //DFKLOOK end
                    break;
                }
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");

            if (chk_StoredLocation.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_EmptyLocation.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_WorkLocation.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_ProhibitedLocation.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_UnreachableLocation.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            part11List.add(txt_FromLocation.getStringValue(), "");
            part11List.add(txt_ToLocation.getStringValue(), "");
            part11List.add(txt_ItemCode_EntEvent.getStringValue(), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_XLS), part11List);
            // commit.
            conn.commit();

            // redirect.
            download(downloadFile.getPath());

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
        chk_StoredLocation.setChecked(true);
        chk_EmptyLocation.setChecked(false);
        chk_WorkLocation.setChecked(false);
        chk_ProhibitedLocation.setChecked(false);
        chk_UnreachableLocation.setChecked(false);
        txt_FromLocation.setValue(null);
        txt_ToLocation.setValue(null);
        txt_ItemCode_EntEvent.setValue(null);
        txt_ItemName.setValue(null);

        //DFKLOOK:ここから修正
        chengeArea();
        //DFKLOOK:ここまで修正
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
