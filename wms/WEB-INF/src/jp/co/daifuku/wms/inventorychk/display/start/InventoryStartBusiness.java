// $Id: InventoryStartBusiness.java 7857 2010-04-22 06:15:21Z shibamoto $
package jp.co.daifuku.wms.inventorychk.display.start;

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
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.LocationFormatException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.inventorychk.dasch.InventoryCheckDASCH;
import jp.co.daifuku.wms.inventorychk.dasch.InventoryCheckDASCHParams;
import jp.co.daifuku.wms.inventorychk.exporter.InventoryCheckListParams;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryStartSCH;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryStartSCHParams;

/**
 * 棚卸開始の画面処理を行います。
 * 
 * @version $Revision: 7857 $, $Date: 2010-04-22 15:15:21 +0900 (木, 22 4 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class InventoryStartBusiness
        extends InventoryStart
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    // DFKLOOK:ここから
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    // DFKLOOK:ここまで

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
    public InventoryStartBusiness()
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
        // DFKLOOK ここから
        // 棚の入力例を表示させます。
        lbl_InLocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(pul_Area.getSelectedValue()));
        //DFKLOOK:ここまで修正
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
        btn_Set_Click_Process(null);
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
        chk_InventoryIssue.setChecked(true);

        // DFKLOOK ここから
        // 生成したプルダウンの初期値を取得。
        _pdm_pul_Area.setSelectedValue(0);
        // 棚の入力例を表示させます。
        lbl_InLocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(
                _pdm_pul_Area.getSelectedValue()));
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        InventoryStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new InventoryStartSCH(conn, this.getClass(), locale, ui);
            
            // チェックボックスのチェック状態を取得
            InventoryStartSCHParams inParam = new InventoryStartSCHParams();
            inParam.set(InventoryStartSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));

            // 画面定義テーブルをチェック
            Params outParam = sch.initFind(inParam);
            
            if (outParam != null)
            {
                String printflg = outParam.getString(InventoryStartSCHParams.INVENTORY_ISSUE);
                if (SystemDefine.KEYDATA_OFF.equals(printflg))
                {
                    // 前回、チェックOFFだった場合は更新
                    chk_InventoryIssue.setChecked(false);
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
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        pul_Area.validate(this, true);
        txt_LocationFrom.validate(this, false);
        txt_LocationTo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        InventoryStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new InventoryStartSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            InventoryStartSCHParams inparam = new InventoryStartSCHParams();
            
            // DFKLOOK:ここから修正        
            if (StringUtil.isBlank(eventSource))
            {
                // DFKLOOK start
                if (!StringUtil.isBlank(lbl_InLocationStyle.getValue()))
                {
                    // 棚のフォーマットチェック
                    String loc =
                            WmsFormatter.toParamLocation(txt_LocationFrom.getStringValue(),
                                    SuperLocationHolder.getInstance().getLocationFormat(pul_Area.getSelectedValue()));
                    inparam.set(InventoryStartSCHParams.LOCATION_FROM, loc);
                    loc =
                            WmsFormatter.toParamLocation(txt_LocationTo.getStringValue(),
                                    SuperLocationHolder.getInstance().getLocationFormat(pul_Area.getSelectedValue()));
                    inparam.set(InventoryStartSCHParams.LOCATION_TO, loc);
                }
                // DFKLOOK end
                // 設定しますか？
                this.setConfirm("MSG-W9000", false, true);
                viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
                return;
            }
            // DFKLOOK:ここまで修正

            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(InventoryStartSCHParams.AREA, _pdm_pul_Area.getSelectedValue());
            // DFKLOOK start
            if (!StringUtil.isBlank(lbl_InLocationStyle.getValue()))
            {
                // 棚のフォーマットチェック
                String loc =
                        WmsFormatter.toParamLocation(txt_LocationFrom.getStringValue(),
                                SuperLocationHolder.getInstance().getLocationFormat(pul_Area.getSelectedValue()));
                inparam.set(InventoryStartSCHParams.LOCATION_FROM, loc);
                loc =
                        WmsFormatter.toParamLocation(txt_LocationTo.getStringValue(),
                                SuperLocationHolder.getInstance().getLocationFormat(pul_Area.getSelectedValue()));
                inparam.set(InventoryStartSCHParams.LOCATION_TO, loc);
            }
            // DFKLOOK end
            inparam.set(InventoryStartSCHParams.INVENTORY_ISSUE, chk_InventoryIssue.getValue());
            inparam.set(InventoryStartSCHParams.INVENTORY_STOCK_REPORT, chk_InventoryStockReport.getValue());
            inparam.set(InventoryStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            // DFKLOOK:ここから
            inparam.set(InventoryStartSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));
            // DFKLOOK:ここまで
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

            //DFKLOOK:ここから修正
            String style = SuperLocationHolder.getInstance().getLocationFormat(pul_Area.getSelectedValue());
            String loc = WmsFormatter.toParamLocation(txt_LocationFrom.getStringValue(), style);
            part11List.add(WmsFormatter.toDispLocation(loc, style), "");
            loc = WmsFormatter.toParamLocation(txt_LocationTo.getStringValue(), style);
            part11List.add(WmsFormatter.toDispLocation(loc, style), "");
            //DFKLOOK:ここまで修正

            if (chk_InventoryIssue.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_InventoryStockReport.getChecked())
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
            if (chk_InventoryIssue.getChecked())
            {
                // リストを発行する
                if (printList(locale, ui))
                {
                    message.setMsgResourceKey(sch.getMessage());
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
        txt_LocationFrom.setValue(null);
        txt_LocationTo.setValue(null);
        chk_InventoryStockReport.setChecked(false);

        // DFKLOOK ここから
        // 棚の入力例を表示させます。
        lbl_InLocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(pul_Area.getSelectedValue()));
        //DFKLOOK:ここまで修正
    }

    // DFKLOOK ここから修正
    /**
     * 棚卸リストを発行します
     * 
     * @param locale ロケール
     * @param ui ユーザー情報
     * @throws ValidateException  文字整合エラー
     * @return boolean 正常終了:true 異常終了:false
     */
    private boolean printList(Locale locale, DfkUserInfo ui)
            throws ValidateException
    {
        Connection conn = null;
        InventoryCheckDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new InventoryCheckDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            InventoryCheckDASCHParams inparam = new InventoryCheckDASCHParams();

            inparam.set(InventoryCheckDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(InventoryCheckDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());

            //DFKLOOK:ここから修正
            String loc =
                    WmsFormatter.toParamLocation(txt_LocationFrom.getStringValue(),
                            SuperLocationHolder.getInstance().getLocationFormat(pul_Area.getSelectedValue()));
            inparam.set(InventoryCheckDASCHParams.LOCATION_FROM, loc);
            loc =
                    WmsFormatter.toParamLocation(txt_LocationTo.getStringValue(),
                            SuperLocationHolder.getInstance().getLocationFormat(pul_Area.getSelectedValue()));
            inparam.set(InventoryCheckDASCHParams.LOCATION_TO, loc);
            //DFKLOOK:ここまで修正

            inparam.set(InventoryCheckDASCHParams.INVENTORY_STOCK_QTY_REPORT, chk_InventoryStockReport.getValue());

            // check count.
            int count = dasch.count(inparam);
            if (count == 0)
            {
                return true;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("InventoryCheckList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                InventoryCheckListParams expparam = new InventoryCheckListParams();
                expparam.set(InventoryCheckListParams.DFK_DS_NO, outparam.get(InventoryCheckDASCHParams.DFK_DS_NO));
                expparam.set(InventoryCheckListParams.DFK_USER_ID, outparam.get(InventoryCheckDASCHParams.DFK_USER_ID));
                expparam.set(InventoryCheckListParams.DFK_USER_NAME, outparam.get(InventoryCheckDASCHParams.DFK_USER_NAME));
                expparam.set(InventoryCheckListParams.SYS_DAY, outparam.get(InventoryCheckDASCHParams.SYS_DAY));
                expparam.set(InventoryCheckListParams.SYS_TIME, outparam.get(InventoryCheckDASCHParams.SYS_TIME));
                expparam.set(InventoryCheckListParams.JOB_NO, outparam.get(InventoryCheckDASCHParams.JOB_NO));
                expparam.set(InventoryCheckListParams.AREA_NO, outparam.get(InventoryCheckDASCHParams.AREA_NO));
                expparam.set(InventoryCheckListParams.AREA_NAME, outparam.get(InventoryCheckDASCHParams.AREA_NAME));
                expparam.set(InventoryCheckListParams.LOCATION_NO, outparam.get(InventoryCheckDASCHParams.LOCATION_NO));
                expparam.set(InventoryCheckListParams.ITEM_CODE, outparam.get(InventoryCheckDASCHParams.ITEM_CODE));
                expparam.set(InventoryCheckListParams.ITEM_NAME, outparam.get(InventoryCheckDASCHParams.ITEM_NAME));
                expparam.set(InventoryCheckListParams.LOT_NO, outparam.get(InventoryCheckDASCHParams.LOT_NO));


                if (StringUtil.isBlank(outparam.getString(InventoryCheckDASCHParams.ITEM_CODE)))
                {
                    expparam.set(InventoryCheckListParams.ENTERING_QTY, "");
                    expparam.set(InventoryCheckListParams.STOCK_CASE_QTY, "");
                    expparam.set(InventoryCheckListParams.STOCK_PIECE_QTY, "");
                }
                else
                {
                    expparam.set(InventoryCheckListParams.ENTERING_QTY,
                            outparam.get(InventoryCheckDASCHParams.ENTERING_QTY));
                    if (chk_InventoryStockReport.getChecked())
                    {
                        expparam.set(InventoryCheckListParams.STOCK_CASE_QTY,
                                outparam.get(InventoryCheckDASCHParams.STOCK_CASE_QTY));
                        expparam.set(InventoryCheckListParams.STOCK_PIECE_QTY,
                                outparam.get(InventoryCheckDASCHParams.STOCK_PIECE_QTY));
                    }
                    else
                    {
                        expparam.set(InventoryCheckListParams.STOCK_CASE_QTY, "");
                        expparam.set(InventoryCheckListParams.STOCK_PIECE_QTY, "");
                    }
                }

                if (!exporter.write(expparam))
                {
                    break;
                }
            }
            // execute print.
            try
            {
                exporter.print();
                return true;
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                message.setMsgResourceKey("6007034");
                return false;
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            return false;

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
        }
    }

    // DFKLOOK ここまで修正

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
