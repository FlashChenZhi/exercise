// $Id: InventoryMovementBusiness.java 7843 2010-04-21 04:49:28Z shibamoto $
package jp.co.daifuku.wms.analysis.display.inventorymovement;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.LineChartDataset;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.ui.control.VerticalBarChartDataset;
import jp.co.daifuku.bluedog.ui.control.VerticalBarLineChartBaseDataset;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.util.PulldownHelper;
import jp.co.daifuku.bluedog.util.chartutil.ChartColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.analysis.exporter.InventoryMovementParams;
import jp.co.daifuku.wms.analysis.listbox.item.LstAnalysisItemParams;
import jp.co.daifuku.wms.analysis.operator.InventoryHistSearchParameter;
import jp.co.daifuku.wms.analysis.schedule.AnalysisOutParameter;
import jp.co.daifuku.wms.analysis.schedule.InventoryMovementSCH;
import jp.co.daifuku.wms.analysis.schedule.InventoryMovementSCHParams;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * 在庫推移分析の画面処理を行います。
 *
 * @version $Revision: 7843 $, $Date: 2010-04-21 13:49:28 +0900 (水, 21 4 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
@SuppressWarnings("serial")
public class InventoryMovementBusiness
        extends InventoryMovement
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_SearchCond */
    private DefaultPullDownModel _pdm_pul_SearchCond;

    /** RadioButtonGroupModel ANA_InventoryMovement */
    @SuppressWarnings("all")
    private RadioButtonGroup _grp_ANA_InventoryMovement;

    /** PullDownModel pul_TermYearMonth */
    private DefaultPullDownModel _pdm_pul_TermYearMonth;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public InventoryMovementBusiness()
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
        if (eventSource.equals("btn_ItemSearch_Click"))
        {
            // process call.
            btn_ItemSearch_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ItemSearch_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ItemSearch_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_UnitMonth_Click(ActionEvent e)
            throws Exception
    {
        // カーソルを商品コードに移動します。
        setFocus(txt_ItemCode);
        pul_TermYearMonth.setEnabled(false);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_UnitDay_Click(ActionEvent e)
            throws Exception
    {
        // カーソルを商品コードに移動します。
        setFocus(txt_ItemCode);
        pul_TermYearMonth.setEnabled(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pul_TermYearMonth_Change(ActionEvent e)
            throws Exception
    {
        vbc_Chart.setVisible(false);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_View_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_View_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ExcelOutput_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ExcelOutput_Click_Process();
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
    public void vbc_Chart_Click(ActionEvent e)
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

        // initialize pul_SearchCond.
        _pdm_pul_SearchCond = new DefaultPullDownModel(pul_SearchCond, locale, ui);

        // initialize ANA_InventoryMovement.
        _grp_ANA_InventoryMovement = new RadioButtonGroup(new RadioButton[]{rdo_UnitMonth, rdo_UnitDay}, locale);

        // initialize pul_TermYearMonth.
        _pdm_pul_TermYearMonth = new DefaultPullDownModel(pul_TermYearMonth, locale, ui);

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

            // load pul_SearchCond.
            _pdm_pul_SearchCond.init(conn);

            // load pul_TermYearMonth.
            _pdm_pul_TermYearMonth.init(conn);

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
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        rdo_UnitMonth.setChecked(true);
        rdo_UnitDay.setChecked(false);
        pul_TermYearMonth.setEnabled(false);
        btn_ExcelOutput.setEnabled(false);

        // DFKLOOK ここから追加
        txt_ItemName.setReadOnly(true);

        // 対象年月プルダウンのセット
        setTermYearMonth();
        pul_TermYearMonth.setEnabled(false);
        vbc_Chart.setVisible(false);

        // XLSボタンを無効化します。
        btn_ExcelOutput.setEnabled(false);

        // 取り残されているオブジェクトを一旦取り除きます。
        clearAttribute();
        // DFKLOOK ここまで追加
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ItemSearch_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        // DFKLOOK ここから変更と追加
        // 商品検索は画面の「検索条件」によって対照テーブルが変わる
        String searchCond = (String)_pdm_pul_SearchCond.getSelectedValue();
        if (null != searchCond
                && (searchCond.equals(InventoryHistSearchParameter.SEARCH_TYPE_ITEM_OUTOFRANGE)
                        || searchCond.equals(InventoryHistSearchParameter.SEARCH_TYPE_ITEM_LIMITOVER) || searchCond.equals(InventoryHistSearchParameter.SEARCH_TYPE_ITEM_LIMITUNDER)))
        {
            // show dialog.
            LstAnalysisItemParams inparam = new LstAnalysisItemParams();
            inparam.set(LstAnalysisItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(LstAnalysisItemParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(LstAnalysisItemParams.SEARCH_COND, searchCond);
            ForwardParameters forwardParam = inparam.toForwardParameters();
            forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_ItemSearch_Click");
            redirect("/analysis/listbox/item/LstAnalysisItem.do", forwardParam, "/Progress.do");
        }
        else
        {
            // show dialog.
            LstItemParams inparam = new LstItemParams();
            inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode.getValue());
            ForwardParameters forwardParam = inparam.toForwardParameters();
            forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_ItemSearch_Click");
            redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
        }
        // DFKLOOK ここまで変更と追加
        
        //        inparam.set(LstItemParams.SEARCH_COND, _pdm_pul_SearchCond.getSelectedValue());

    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_ItemSearch_Click_DlgBack(DialogParameters dialogParams)
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
    private void btn_View_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ItemCode.validate(this, true);

        // DFKLOOK ここから追加
        // XLSボタンを無効化します。
        btn_ExcelOutput.setEnabled(false);
        // DFKLOOK ここまで追加

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        InventoryMovementSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new InventoryMovementSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            InventoryMovementSCHParams inparam = new InventoryMovementSCHParams();
            // DFKLOOK ここからコメント
            // inparam.set(InventoryMovementSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            // inparam.set(InventoryMovementSCHParams.ITEM_NAME, txt_ItemName.getValue());
            // inparam.set(InventoryMovementSCHParams.ANALYSIS_MODE, _grp_ANA_InventoryMovement.getSelectedValue());
            // inparam.set(InventoryMovementSCHParams.ITEM_LIST_CONDITION, _pdm_pul_SearchCond.getSelectedValue());
            // inparam.set(InventoryMovementSCHParams.ANALYSIS_START_DATE, "");
            // inparam.set(InventoryMovementSCHParams.ANALYSIS_END_DATE, "");
            // inparam.set(InventoryMovementSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            //
            // // SCH call.
            // List<Params> outparams = sch.query(inparam);
            // DFKLOOK ここまでコメント

            // DFKLOOK ここから追加
            // 荷主コード 
            inparam.set(InventoryMovementSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            // 商品コード 
            inparam.set(InventoryMovementSCHParams.ITEM_CODE, txt_ItemCode.getText());

            if (!sch.check(inparam))
            {
                // メッセージをセット
                message.setMsgResourceKey(sch.getMessage());
                vbc_Chart.setVisible(false);
                // 商品名称を再セットします。
                txt_ItemName.setText(sch.getItemName(WmsParam.DEFAULT_CONSIGNOR_CODE,
                                        txt_ItemCode.getText()));
                return;
            }
            // 商品名称の再表示処理
            txt_ItemName.setText(sch.getItemName(WmsParam.DEFAULT_CONSIGNOR_CODE,
                                    txt_ItemCode.getText()));

            // 在庫履歴データ検索＆グラフ描画
            AnalysisOutParameter imData = searchInventoryMovementData(conn);

            // 在庫履歴データセットをセッションにバインドします。
            // 取り残されているオブジェクトを一旦取り除きます。
            Params sRet = (Params)this.getSession().getAttribute("InventoryMovement");
            if (sRet != null)
            {
                this.getSession().removeAttribute("InventoryMovement");
            }
            // SessionにAnalysisOutParameterを保持します。
            this.getSession().setAttribute("InventoryMovement", imData);

            if (imData != null)
            {
                // 在庫履歴データをグラフ表示します。
                setChartData(imData);

                // XLSボタンを有効化します。
                btn_ExcelOutput.setEnabled(true);
                
                // 検索値を保持します。
                viewState.setObject(ViewStateKeys.VS_ITEM_CODE, txt_ItemCode.getValue());
                viewState.setObject(ViewStateKeys.VS_SEARCH_COND, _pdm_pul_SearchCond.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_INVENTRY_MOVEMENT, _grp_ANA_InventoryMovement.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_TEEM_YEAR_MONTH, _pdm_pul_TermYearMonth.getSelectedValue());
            }
            // DFKLOOK ここまで追加
            // DFKLOOK メッセージの取得をコメントにする
            // message.setMsgResourceKey(sch.getMessage());

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
    private void btn_ExcelOutput_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // DFKLOOK ここから追加
            // 保存されている在庫履歴データをセッションから取得する。
            AnalysisOutParameter param =
                (AnalysisOutParameter)this.getSession().getAttribute("InventoryMovement");
            // DFKLOOK ここまで追加

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newExcelExporter("InventoryMovement", getSession());
            File downloadFile = exporter.open();

            // DFKLOOK ここから追加
            // 月単位表示は、各月最終日の在庫数推移を表示することにする。
            // 月単位表示モードの判断は、検索開始年と終了年が違うことで判断する。
            // （検索単位を日単位で設定するように変更した為）
            String startYear = param.getAnalysisStartDate().substring(0, 4);
            String endYear = param.getAnalysisEndDate().substring(0, 4);
            boolean isUnitMonthMode = !startYear.equals(endYear);

            for (int i = 0; i < param.getSizeOfInventoryList(); i++)
            {
                InventoryMovementParams expparam = new InventoryMovementParams();
                expparam.set(InventoryMovementParams.ITEM_CODE, param.getItemCode());
                expparam.set(InventoryMovementParams.ITEM_NAME, param.getItemName());
                String str = param.getTimestampFromInventoryList(i);
                // 月単位は年月表示
                if (isUnitMonthMode)
                {
                    str = WmsFormatter.toParamYearMonth(WmsFormatter.toDate(str));
                }
                expparam.set(InventoryMovementParams.DATE, str);
                expparam.set(InventoryMovementParams.INVENTORY_QTY,
                                param.getInventoryQtyFromInventoryList(i));
                expparam.set(InventoryMovementParams.STORAGE_QTY, param.getStorageQtyFromInventoryList(i));
                expparam.set(InventoryMovementParams.RETRIEVAL_QTY, param.getRetrievalQtyFromInventoryList(i));
                expparam.set(InventoryMovementParams.UPPER_LIMIT_QTY, param.getUpperLimitQtyFromInventoryList(i));
                expparam.set(InventoryMovementParams.LOWER_LIMIT_QTY, param.getLowerLimitQtyFromInventoryList(i));
                if (!exporter.write(expparam))
                {
                    //DFKLOOK start
                    // XLS最大件数出力メッセージ対応
                    message.setMsgResourceKey("6001031\t"
                            + Formatter.getNumFormat(param.getSizeOfInventoryList()) + "\t"
                            + Formatter.getNumFormat(CommonParam.getIntParam("MAX_NUMBER_OF_XLS")
                                    - exporter.getHeaderRowsCount()));
                    //DFKLOOK end
                    break;
                }
            }
            // DFKLOOK ここまで追加
            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(viewState.getString(ViewStateKeys.VS_ITEM_CODE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_SEARCH_COND), "");
            if (viewState.getString(ViewStateKeys.VS_INVENTRY_MOVEMENT).equals(InventoryHistSearchParameter.SUMMARY_TYPE_MONTH))
            {
            	part11List.add(InventoryHistSearchParameter.SUMMARY_TYPE_MONTH, "");
            }
            else if (viewState.getString(ViewStateKeys.VS_INVENTRY_MOVEMENT).equals(InventoryHistSearchParameter.SUMMARY_TYPE_DAY))
            {
                part11List.add(InventoryHistSearchParameter.SUMMARY_TYPE_DAY, "");
                part11List.add(viewState.getString(ViewStateKeys.VS_TEEM_YEAR_MONTH), "");
            }
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_XLS), part11List);

            // commit.
            conn.commit();

            // redirect.
            download(downloadFile.getPath());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
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
        txt_ItemCode.setValue(null);
        _pdm_pul_SearchCond.setSelectedValue(null);
        txt_ItemName.setValue(null);
        rdo_UnitMonth.setChecked(true);
        _pdm_pul_TermYearMonth.setSelectedValue(null);

        // DFKLOOK ここから追加
        pul_SearchCond.setSelectedIndex(0); // 検索条件プルダウンの先頭データ
        rdo_UnitMonth.setChecked(true);
        rdo_UnitDay.setChecked(false);
        pul_TermYearMonth.setSelectedIndex(0); // 対象年月プルダウンの先頭データ
        pul_TermYearMonth.setEnabled(false);
        vbc_Chart.setVisible(false);

        // XLSボタンを無効化します。
        btn_ExcelOutput.setEnabled(false);

        // 取り残されているオブジェクトを一旦取り除きます。
        clearAttribute();
        // DFKLOOK ここまで追加
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

    // DFKLOOK ここから追加
    /** 
     * 対象年月プルダウンをセットする。
     * @throws Exception 全ての例外を報告します。
     */
    private void setTermYearMonth()
            throws Exception
    {
        Calendar curDate = Calendar.getInstance();

        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            WarenaviSystemController controller = new WarenaviSystemController(conn, getClass());
            // 作業日の前日取得
            Calendar cal = Calendar.getInstance();
            cal.setTime(WmsFormatter.toDate(controller.getWorkDay()));
            cal.add(Calendar.DATE, -1);
            String workDate = WmsFormatter.toParamDate(cal.getTime());

            curDate.setTime(WmsFormatter.toDate(workDate));

            if (workDate != null)
            {
                // 区切り文字
                String pddelim = ",";
                // 先頭表示
                String firstdisp_true = "1";
                // 先頭ではない
                String firstdisp_false = "0";
                Vector<String> array = new Vector<String>();

                // 作業日～1年間をセットします
                for (int i = 0; i < 13; i++)
                {
                    // 月の最初の日をセット
                    curDate.set(Calendar.DATE, 1);
                    Date date = curDate.getTime();

                    // 実際に使用する日付（yyyyMM01）
                    String value = WmsFormatter.toParamDate(date);
                    // 画面表示する日付（年+月：ロケールによって表示が異なります）
                    String disp = toDispYearMonth(date, this.getHttpRequest().getLocale());

                    // 実際に使用する日付と画面表示の日付をベクトルにセットする 
                    if (i == 0)
                    {
                        array.add(value + pddelim + disp + pddelim + "" + pddelim + firstdisp_true);
                    }
                    else
                    {
                        array.add(value + pddelim + disp + pddelim + "" + pddelim + firstdisp_false);
                    }

                    // 前の月を取得 
                    curDate.add(Calendar.MONTH, -1);
                }

                String[] a = new String[array.size()];
                array.toArray(a);
                PulldownHelper.setPullDown(pul_TermYearMonth, a);
                pul_TermYearMonth.setSelectedIndex(0);
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
    }

    /** 
     * 在庫推移データの検索を行います。
     * @param conn コネクション
     * @return 検索結果
     * @throws Exception 全ての例外を報告します。
     */
    private AnalysisOutParameter searchInventoryMovementData(Connection conn)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        InventoryMovementSCH sch = null;
        AnalysisOutParameter anaParam = null;
        try
        {
            sch = new InventoryMovementSCH(conn, this.getClass(), locale, ui);

            // スケジュール開始
            List<Params> lparams = sch.query(setParam(conn));
            if (lparams != null)
            {
                anaParam = (AnalysisOutParameter)lparams.get(0);
            }
            else
            {
                // 異常発生
                message.setMsgResourceKey(sch.getMessage());
                vbc_Chart.setVisible(false);
                return null;
            }

            // 対象データなし？
            if (anaParam.getSizeOfInventoryList() == 0)
            {
                message.setMsgResourceKey(sch.getMessage());
                vbc_Chart.setVisible(false);
                return null;
            }

            // 全レコードの在庫数、入出庫数がゼロだった場合は、対象データなしとする。
            boolean existData = false;
            for (int i = 0; i < anaParam.getSizeOfInventoryList(); i++)
            {
                if ((anaParam.getInventoryQtyFromInventoryList(i) > 0)
                        || (anaParam.getStorageQtyFromInventoryList(i) > 0)
                        || (anaParam.getRetrievalQtyFromInventoryList(i) > 0))
                {
                    existData = true;
                    break;
                }
            }
            if (!existData)
            {
                // 6003011=対象データはありませんでした。
                message.setMsgResourceKey("6003011");
                vbc_Chart.setVisible(false);
                return null;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }

        // 正常終了
        return anaParam;
    }

    /** 
     * グラフの描画を行います。
     * @param param 在庫推移データが格納されたパラメータ
     * @throws IOException ディスクへの書込みに失敗したときスローされます。
     */
    private void setChartData(AnalysisOutParameter param)
            throws IOException
    {
        // スケールMAX、MIN値の算出
        double inventoryMin = param.getMinInventoryQty();
        double inventoryMax = param.getMaxInventoryQty();
        double storageMin = param.getMinStorageQty();
        double storageMax = param.getMaxStorageQty();
        if (param.getSizeOfInventoryList() > 0)
        {
            // 在庫数、上限在庫数、下限在庫数
            double min = param.getMinLowerLimitQty();
            double max = param.getMaxUpperLimitQty();
            if (min >= 0 && inventoryMin > min)
            {
                inventoryMin = min;
            }
            if (max >= 0 && inventoryMax < max)
            {
                inventoryMax = max;
            }
            inventoryMin = inventoryMin * 0.8;
            if (inventoryMax > WmsParam.MAX_NUMBER_OF_DISP_GRAPH)
            {
                inventoryMax = WmsParam.MAX_NUMBER_OF_DISP_GRAPH;
            }
            else
            {
                inventoryMax = inventoryMax * 1.2;
            }

            // 入庫数、出庫数
            if (storageMin > param.getMinRetrievalQty())
            {
                storageMin = param.getMinRetrievalQty();
            }
            if (storageMax < param.getMaxRetrievalQty())
            {
                storageMax = param.getMaxRetrievalQty();
            }

            // 入出庫数の最大が0だった場合は、最大値を在庫数に合わす
            if (storageMax == 0)
            {
                storageMax = inventoryMax;
            }
            else
            {
                if (storageMax > WmsParam.MAX_NUMBER_OF_DISP_GRAPH)
                {
                    storageMax = WmsParam.MAX_NUMBER_OF_DISP_GRAPH;
                }
                else
                {
                    storageMax = storageMax * 1.2;
                }
            }
            storageMin = storageMin * 0.8;

            // 在庫数、上限在庫数、下限在庫数の最大が0だった場合は、最大値を入出庫数に合わす
            if (inventoryMax == 0)
            {
                inventoryMax = storageMax;
            }
        }

        // 在庫数
        VerticalBarLineChartBaseDataset dataset1 =
                new VerticalBarChartDataset(DispResources.getText("LBL-W0073"), ChartColor.ROYALBLUE, inventoryMin,
                        inventoryMax, true);
        // 入庫数
        VerticalBarLineChartBaseDataset dataset2 =
                new LineChartDataset(DispResources.getText("LBL-W0150"), ChartColor.AQUAMARINE, storageMin, storageMax,
                        true);
        // 出庫数
        VerticalBarLineChartBaseDataset dataset3 =
                new LineChartDataset(DispResources.getText("LBL-W0123"), ChartColor.GREENYELLOW, storageMin,
                        storageMax, true);
        // 上限在庫数
        VerticalBarLineChartBaseDataset dataset4 =
                new LineChartDataset(DispResources.getText("LBL-W0131"), ChartColor.BROWN, inventoryMin, inventoryMax,
                        false);
        // 下限在庫数
        VerticalBarLineChartBaseDataset dataset5 =
                new LineChartDataset(DispResources.getText("LBL-W0051"), ChartColor.BROWN, inventoryMin, inventoryMax,
                        false);

        // 月単位表示は、各月最終日の在庫数推移を表示することにする。
        // 月単位表示モードの判断は、検索開始年と終了年が違うことで判断する。
        // （検索単位を日単位で設定するように変更した為）
        String startYear = param.getAnalysisStartDate().substring(0, 4);
        String endYear = param.getAnalysisEndDate().substring(0, 4);
        boolean isUnitMonthMode = !startYear.equals(endYear);

        String timeStamp;
        for (int i = 0; i < param.getSizeOfInventoryList(); i++)
        {
            // 月単位は年月表示
            if (isUnitMonthMode)
            {
                timeStamp = toDispYearMonth(WmsFormatter.toDate(param.getTimestampFromInventoryList(i)),
                                this.getHttpRequest().getLocale());
            }
            // 日単位は年月日表示
            else
            {
                timeStamp = WmsFormatter.toDispDate(WmsFormatter.toDate(param.getTimestampFromInventoryList(i)),
                                this.getHttpRequest().getLocale());
            }

            // データをセットする(100000000を超える場合は、メモリーオーバーのために100000000をセット)
            if (param.getInventoryQtyFromInventoryList(i) > WmsParam.MAX_NUMBER_OF_DISP_GRAPH)
            {
                dataset1.addGraph(timeStamp, WmsParam.MAX_NUMBER_OF_DISP_GRAPH);
            }
            else
            {
                dataset1.addGraph(timeStamp, param.getInventoryQtyFromInventoryList(i));
            }

            if (param.getStorageQtyFromInventoryList(i) > WmsParam.MAX_NUMBER_OF_DISP_GRAPH)
            {
                dataset2.addGraph(timeStamp, WmsParam.MAX_NUMBER_OF_DISP_GRAPH);
            }
            else
            {
                dataset2.addGraph(timeStamp, param.getStorageQtyFromInventoryList(i));
            }

            if (param.getRetrievalQtyFromInventoryList(i) > WmsParam.MAX_NUMBER_OF_DISP_GRAPH)
            {
                dataset3.addGraph(timeStamp, WmsParam.MAX_NUMBER_OF_DISP_GRAPH);
            }
            else
            {
                dataset3.addGraph(timeStamp, param.getRetrievalQtyFromInventoryList(i));
            }

            if (param.getUpperLimitQtyFromInventoryList(i) > WmsParam.MAX_NUMBER_OF_DISP_GRAPH)
            {
                dataset4.addGraph(timeStamp, WmsParam.MAX_NUMBER_OF_DISP_GRAPH);
            }
            else
            {
                dataset4.addGraph(timeStamp, param.getUpperLimitQtyFromInventoryList(i));
            }

            if (param.getLowerLimitQtyFromInventoryList(i) > WmsParam.MAX_NUMBER_OF_DISP_GRAPH)
            {
                dataset5.addGraph(timeStamp, WmsParam.MAX_NUMBER_OF_DISP_GRAPH);
            }
            else
            {
                dataset5.addGraph(timeStamp, param.getLowerLimitQtyFromInventoryList(i));
            }
        }

        // チャートにデータセットを追加
        vbc_Chart.clearDataset();
        vbc_Chart.addDataset(dataset1);
        vbc_Chart.addDataset(dataset2);
        vbc_Chart.addDataset(dataset3);
        vbc_Chart.addDataset(dataset4);
        vbc_Chart.addDataset(dataset5);

        // チャート生成
        try
        {
            vbc_Chart.createChart(httpRequest);
            vbc_Chart.setVisible(true);

            // 6001013=表示しました。
            message.setMsgResourceKey("6001013");
        }
        catch (IllegalStateException e)
        {
            // 6006001 = 予期しないエラーが発生しました。{0}
            RmiMsgLogClient.write(6006001, LogMessage.F_ERROR, getClass().getName());
            throw e;
        }
        catch (IOException e)
        {
            // 6006001 = 予期しないエラーが発生しました。{0}
            RmiMsgLogClient.write(6006001, LogMessage.F_ERROR, getClass().getName());
            throw e;
        }
    }

    /**
     * 在庫推移データ検索用のパラメータをセットします。
     * @param conn コネクションオブジェクト
     * @return AnalysisInParameter
     * @throws Exception 全ての例外を報告します。
     */
    private InventoryMovementSCHParams setParam(Connection conn)
            throws Exception
    {
        InventoryMovementSCHParams param = new InventoryMovementSCHParams();
        Calendar cal = Calendar.getInstance();

        // 月単位
        if (rdo_UnitMonth.getChecked())
        {
            // 分析単位（日単位：各月の最終日の在庫数を表示する）
            param.set(InventoryMovementSCHParams.ANALYSIS_MODE, InventoryHistSearchParameter.SUMMARY_TYPE_DAY);

            // 作業日の前日取得
            WarenaviSystemController controller = new WarenaviSystemController(conn, getClass());
            cal.setTime(WmsFormatter.toDate(controller.getWorkDay()));
            cal.add(Calendar.DATE, -1);
            String workDate = WmsFormatter.toParamDate(cal.getTime());

            // 分析開始日（作業日の前年の月の最初の日）
            cal.add(Calendar.YEAR, -1);
            cal.set(Calendar.DATE, 1);
            param.set(InventoryMovementSCHParams.ANALYSIS_START_DATE, WmsFormatter.toParamDate(cal.getTime()));

            // 分析終了日（作業日の月の最後の日）
            cal.setTime(WmsFormatter.toDate(workDate));
            cal.add(Calendar.MONTH, 1);
            cal.set(Calendar.DATE, 0);
            param.set(InventoryMovementSCHParams.ANALYSIS_END_DATE, WmsFormatter.toParamDate(cal.getTime()));
        }
        // 日単位
        else
        {
            // 分析単位（日単位）
            param.set(InventoryMovementSCHParams.ANALYSIS_MODE, InventoryHistSearchParameter.SUMMARY_TYPE_DAY);

            // 分析開始日（対象期間の月の最初の日：プルダウンのvalue（実際に使用する日付））
            param.set(InventoryMovementSCHParams.ANALYSIS_START_DATE, pul_TermYearMonth.getSelectedValue());

            // 分析終了日（対象期間の月の最後の日）
            cal.setTime(WmsFormatter.toDate(pul_TermYearMonth.getSelectedValue()));
            cal.add(Calendar.MONTH, 1);
            cal.set(Calendar.DATE, 0);
            param.set(InventoryMovementSCHParams.ANALYSIS_END_DATE, WmsFormatter.toParamDate(cal.getTime()));
        }
        // 荷主コード
        param.set(InventoryMovementSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        // 商品コード
        param.set(InventoryMovementSCHParams.ITEM_CODE, txt_ItemCode.getText());
        // 商品コード検索条件
        param.set(InventoryMovementSCHParams.ITEM_LIST_CONDITION, pul_SearchCond.getSelectedValue());

        return param;
    }

    /**
     * セションにバインドされている在庫履歴データセットをクリアします。
     */
    private void clearAttribute()
    {
        // 在庫履歴データセットをセッションにバインドします。
        // 取り残されているオブジェクトを一旦取り除きます。
        Params sRet = (Params)this.getSession().getAttribute("InventoryMovement");
        if (sRet != null)
        {
            this.getSession().removeAttribute("InventoryMovement");
        }
        // SessionにInventoryMovementParameterを保持します。
        this.getSession().setAttribute("InventoryMovement", null);
    }

    /**
     * Date型からラベルへ表示する日付形式への変換を行います。<BR>
     * <BR>
     * 概要:Date型から、ラベルに表示するためLocaleに従った日付形式
     * (年+月:日本ならばyyyy/MM、英語圏ならMM/yyyy)への変換を行います。<BR>
     * 引数が空文字の時は空文字を返します。<BR>
     * <BR>
     * [パラメータ] *必須入力<BR>
     *   <DIR>
     *   日付(Date型)*<BR>
     *   Locale*<BR>
     *   </DIR>
     * <BR>
     * [返却データ]<BR>
     *   <DIR>
     *   日付(例:yyyy/MM(ja)、MM/yyyy(en))<BR>
     *   </DIR>
     * 
     * @param date Date型
     * @param locale 地域コードがセットされた<CODE>Locale</CODE>オブジェクト
     * @return ラベルへ表示する形式の日付 
     */
    public static synchronized String toDispYearMonth(Date date, Locale locale)
    {
        // 引数が空文字の時は空文字を返す
        if (StringUtil.isBlank(date))
        {
            return "";
        }

        // Localeに従ったString型の日付に変換
        SimpleDateFormat sdf = new SimpleDateFormat();
        // 日本
        if (locale.equals(Locale.JAPANESE))
        {
            sdf.applyLocalizedPattern("yyyy/MM");
        }
        // 言語圏が追加された場合はここにif文を追加
        // 英語圏
        else
        {
            sdf.applyLocalizedPattern("MM/yyyy");
        }

        String changeDate = sdf.format(date);

        return changeDate;
    }
    // DFKLOOK ここまで追加
    
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
