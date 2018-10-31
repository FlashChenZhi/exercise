// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.stock.display.fastockmnt;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.lang.reflect.Field;
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
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SoftZone;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.listbox.itemname.LstItemNameParams;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsChecker;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.stock.schedule.FaStockMnt2SCH;
import jp.co.daifuku.wms.stock.schedule.FaStockMnt2SCHParams;

/**
 * 在庫メンテナンス(追加)の画面処理を行います。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class FaStockMnt2Business
        extends FaStockMnt2
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";
    
    // DFKLOOK:ここから修正
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK:ここまで修正

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
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
    public FaStockMnt2Business()
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
        if (eventSource.startsWith("btn_Set_Click"))
        {
            // process call.
            btn_Set_Click_Process(eventSource);
        }
        else if (eventSource.startsWith("checkItem"))
        {
            // process call.
            _isItemCheck = true;
            btn_Set_Click_Process(eventSource);
        }
        // DFKLOOK:ここまで修正
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
            txt_SoftZoneName.setValue(item.getValue(SoftZone.SOFT_ZONE_NAME));
            viewState.setString(ViewStateKeys.VS_SOFTZONE_ID, item.getValue(
					SoftZone.SOFT_ZONE_ID).toString());

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
    public void btn_Set_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        //DFKLOOK:ここから修正
        btn_Set_Click_Process(null);
        //DFKLOOK:ここまで修正
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
    public void btn_Back_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Back_Click_Process();
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
        // output display.
        //DFKLOOK:ここから修正
        Connection conn = null;
        String areaName = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            AreaController ac = new AreaController(conn, this.getClass());
            areaName = ac.getAreaName(viewState.getString(ViewStateKeys.VS_AREA_NO));
            
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
        lbl_In_Area.setValue(viewState.getObject(ViewStateKeys.VS_AREA_NO) + "：" + areaName);
        //DFKLOOK:ここまで修正
        
        //DFKLOOK:ここから修正
        String locFormat = SuperLocationHolder.getInstance().getLocationFormat(viewState.getString(ViewStateKeys.VS_AREA_NO));
        txt_Location.setValue(WmsFormatter.toDispLocation(viewState.getString(ViewStateKeys.VS_LOCATION_NO), locFormat));
        //DFKLOOK:ここまで修正

        // clear.
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        txt_LotNo.setValue(null);
        txt_SoftZoneName.setValue(null);
        txt_StockQty.setValue(null);
        lbl_In_DateFormat.setValue(null);
        txt_Location.setReadOnly(true);
        txt_SoftZoneName.setReadOnly(true);
        
        //DFKLOOK:ここから修正
        txt_StorageDate.setDate(DbDateUtil.getTimeStamp());
        txt_StorageTime.setTime(DbDateUtil.getTimeStamp());

        // 日付フォーマットを取得し入力形式にセット
        lbl_In_DateFormat.setText(DisplayText.getText("LBL-W1365"));
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
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode.getValue());

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
        txt_ItemCode.setValue(outparam.get(LstItemParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemParams.ITEM_NAME));
        txt_SoftZoneName.setValue(outparam.get(LstItemParams.SOFT_ZONE_NAME));

        // set focus.
        setFocus(txt_ItemCode);

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
        txt_ItemCode.setValue(outparam.get(LstItemNameParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemNameParams.ITEM_NAME));
        txt_SoftZoneName.setValue(outparam.get(LstItemNameParams.SOFT_ZONE_NAME));

        // set focus.
        setFocus(txt_ItemName);

    }

    /**
     *
     * @throws Exception
     */
    //DFKLOOK:ここから修正
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    //DFKLOOK:ここから修正
    {
        // input validation.
        txt_ItemCode.validate(this, true);
        txt_ItemName.validate(this, false);
        txt_LotNo.validate(this, false);
        txt_StockQty.validate(this, true);
        txt_StorageDate.validate(this, false);
        txt_StorageTime.validate(this, false);

        // DFKLOOK:ここから修正        
        if (StringUtil.isBlank(eventSource))
        {
            // show confirm message. 設定しますか？
            this.setConfirm("MSG-W9000", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
            return;
        }
        // DFKLOOK:ここまで修正
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaStockMnt2SCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaStockMnt2SCH(conn, this.getClass(), locale, ui);

            // DFKLOOK start
            // 入力商品情報のチェック
            if (!eventSource.equals("btn_Set_Click_SCH") && !checkItem(conn))
            {
                return;
            }
            // DFKLOOK end
            
            // set input parameters.
            FaStockMnt2SCHParams inparam = new FaStockMnt2SCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(FaStockMnt2SCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(FaStockMnt2SCHParams.AREA_NO, viewState.getString(ViewStateKeys.VS_AREA_NO));
            
            //DFKLOOK:ここから修正
            String locFormat = SuperLocationHolder.getInstance().getLocationFormat(viewState.getString(ViewStateKeys.VS_AREA_NO));
            inparam.set(FaStockMnt2SCHParams.LOCATION_NO, WmsFormatter.toParamLocation(txt_Location.getText(), locFormat));
            //DFKLOOK:ここまで修正
            
            inparam.set(FaStockMnt2SCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(FaStockMnt2SCHParams.ITEM_NAME, txt_ItemName.getValue());
            inparam.set(FaStockMnt2SCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(FaStockMnt2SCHParams.SOFT_ZONE_NAME, txt_SoftZoneName.getValue());
            inparam.set(FaStockMnt2SCHParams.STOCK_QTY, txt_StockQty.getValue());
            inparam.set(FaStockMnt2SCHParams.STORAGE_DATE, txt_StorageDate.getValue());
            inparam.set(FaStockMnt2SCHParams.STORAGE_TIME, txt_StorageTime.getValue());

            //DFKLOOK:ここから修正
            // 入力日付のチェックを行う
            WmsChecker chk = new WmsChecker();
            if (!chk.checkDate(txt_StorageDate.getDate(), txt_StorageTime.getTime()))
            {
                message.setMsgResourceKey(chk.getMessage());
                return;
            }
            //DFKLOOK:ここまで修正

            //DFKLOOK:ここから修正
            if ((eventSource.equals("btn_Set_Click") || eventSource.equals("checkItem")) && !sch.check(inparam))
            {
                if (StringUtil.isBlank(sch.getDispMessage()))
                {
                    // show message
                    message.setMsgResourceKey(sch.getMessage());
                    return;
                }
                else
                {
                    // show confirm message.
                    this.setConfirm(sch.getDispMessage(), false, true);
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click_SCH");
                    return;
                }
            }
            //DFKLOOK:ここまで修正
            
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
            part11List.add(viewState.getString(ViewStateKeys.VS_AREA_NO), "");
            part11List.add(txt_Location.getStringValue(), "");
            part11List.add(txt_ItemCode.getStringValue(), "");
            part11List.add(txt_LotNo.getStringValue(), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_SOFTZONE_ID), "");
            part11List.add(txt_StockQty.getStringValue(), "");
            part11List.add(txt_StorageDate.getStringValue(), txt_StorageTime.getStringValue(), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

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
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        txt_LotNo.setValue(null);
        txt_SoftZoneName.setValue(null);
        txt_StockQty.setValue(null);

        //DFKLOOK:ここから修正
        txt_StorageDate.setDate(DbDateUtil.getTimeStamp());
        txt_StorageTime.setTime(DbDateUtil.getTimeStamp());

        // 日付フォーマットを取得し入力形式にセット
        Field field = Constants.class.getField("DATE_FORMAT_" + httpRequest.getLocale().getLanguage());
        lbl_In_DateFormat.setText(((String[])(field.get(null)))[Constants.F_DATE_TIME]);
        //DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Back_Click_Process()
            throws Exception
    {
        // input validation.
        txt_Location.validate(this, false);
        txt_ItemCode.validate(this, false);
        txt_ItemName.validate(this, false);
        txt_LotNo.validate(this, false);
        txt_SoftZoneName.validate(this, false);
        txt_StockQty.validate(this, false);
        txt_StorageDate.validate(this, false);
        txt_StorageTime.validate(this, false);

        try
        {
            // forward.
            forward("/stock/fastockmnt/FaStockMnt.do");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
    }
    
    // DFKLOOK start
    /**
     * 入力されている商品情報のチェックを行います。<br>
     * 該当商品が存在しない場合は、エラーメッセージを表示します。<br>
     * 入力商品コードと名称が一致しない場合、確認ダイアログを表示します。<br>
     * 上記のダイアログで確認後は、商品名称とソフトゾーンを補完します。
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
                else
                {
                    // 一致した場合、ソフトゾーンのみ補完する
                    Item item = ItemController.getItemInfo(item_code, consignor_code, conn);
                    
                    if (item == null)
                    {
                        // 6023021 = 商品コードがマスタに登録されていません。
                        message.setMsgResourceKey("6023021");
                        return false;
                    }
                    
                    // output display.
                    txt_SoftZoneName.setValue(item.getValue(SoftZone.SOFT_ZONE_NAME));
                    viewState.setString(ViewStateKeys.VS_SOFTZONE_ID, item.getValue(
							SoftZone.SOFT_ZONE_ID).toString());
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
            txt_SoftZoneName.setValue(item.getValue(SoftZone.SOFT_ZONE_NAME));
        }
        
        // フラグ初期化
        _isItemCheck = false;
        return true;
    }
    // DFKLOOK end

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
