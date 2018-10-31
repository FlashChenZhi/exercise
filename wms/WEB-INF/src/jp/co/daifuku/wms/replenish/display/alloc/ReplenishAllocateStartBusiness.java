package jp.co.daifuku.wms.replenish.display.alloc;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.Constants;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.LocationFormatException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
//DFKLOOK 3.5 ADD START
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
// DFKLOOK 3.5 ADD END
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.uimodel.WmsAllocPriorityPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsAllocPriorityPullDownModel.AllocateType;
import jp.co.daifuku.wms.replenish.dasch.AsReplenishWorkDASCH;
import jp.co.daifuku.wms.replenish.dasch.AsReplenishWorkDASCHParams;
import jp.co.daifuku.wms.replenish.dasch.ReplenishShortageDASCH;
import jp.co.daifuku.wms.replenish.dasch.ReplenishShortageDASCHParams;
import jp.co.daifuku.wms.replenish.dasch.ReplenishWorkDASCH;
import jp.co.daifuku.wms.replenish.dasch.ReplenishWorkDASCHParams;
import jp.co.daifuku.wms.replenish.display.alloc.ReplenishAllocateStart;
import jp.co.daifuku.wms.replenish.exporter.ReplenishmentListParams;
import jp.co.daifuku.wms.replenish.exporter.AsrsReplenishmentListParams;
import jp.co.daifuku.wms.replenish.exporter.ReplenishShortageListParams;
import jp.co.daifuku.wms.replenish.schedule.ReplenishAllocateStartSCH;
import jp.co.daifuku.wms.replenish.schedule.ReplenishAllocateStartSCHParams;

/**
 * 計画補充開始設定の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class ReplenishAllocateStartBusiness
        extends ReplenishAllocateStart
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
    /** PullDownModel pul_ToReplenishmentArea */
    private WmsAreaPullDownModel _pdm_pul_ToReplenishmentArea;

    /** PullDownModel pul_AllocatedPattern */
    private WmsAllocPriorityPullDownModel _pdm_pul_AllocatedPattern;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public ReplenishAllocateStartBusiness()
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
       if (eventSource.startsWith("btn_Start_Click"))
       {
           btn_Start_Click_Process(eventSource);
       }
       // DFKLOOK:ここまで修正
   }

   /**
    *
    * @param e ActionEvent
    * @throws Exception
    */
    public void pul_ToReplenishmentArea_Change(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // 棚の入力例を表示させます。
        if (!WmsParam.ALL_AREA_NO.equals(_pdm_pul_ToReplenishmentArea.getSelectedValue()))
        {
            lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(
                    _pdm_pul_ToReplenishmentArea.getSelectedValue()));
            txt_FromLocation.setReadOnly(false);
            txt_ToLocation.setReadOnly(false);
        }
        else
        {
            lbl_LocationStyle.setValue("");
            txt_FromLocation.setValue(null);
            txt_FromLocation.setReadOnly(true);
            txt_ToLocation.setValue(null);
            txt_ToLocation.setReadOnly(true);
        }
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Start_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Start_Click_Process(null);
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

        // initialize pul_ToReplenishmentArea.
        _pdm_pul_ToReplenishmentArea = new WmsAreaPullDownModel(pul_ToReplenishmentArea, locale, ui);

        // initialize pul_AllocatedPattern.
        _pdm_pul_AllocatedPattern = new WmsAllocPriorityPullDownModel(pul_AllocatedPattern, locale, ui);

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

            // load pul_ToReplenishmentArea.
            //DFKLOOK 3.5 Start
            _pdm_pul_ToReplenishmentArea.init(conn, AreaType.FLOOR_FIXEDLOCATE, StationType.ALL, "", false);
            //DFKLOOK 3.5 End

            // load pul_AllocatedPattern.
            _pdm_pul_AllocatedPattern.init(conn, AllocateType.REPLENISHMENT);

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
        setFocus(pul_ToReplenishmentArea);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        chk_IssueReport.setChecked(true);

        // DFKLOOK 3.5 UPD START
        _pdm_pul_ToReplenishmentArea.setSelectedValue(null);
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(
                _pdm_pul_ToReplenishmentArea.getSelectedValue()));
        // DFKLOOK 3.5 UPD END

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ReplenishAllocateStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ReplenishAllocateStartSCH(conn, this.getClass(), locale, ui);

            ReplenishAllocateStartSCHParams inParam = new ReplenishAllocateStartSCHParams();
            inParam.set(ReplenishAllocateStartSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));

            // 画面定義テーブルをチェック
            Params outParam = sch.initFind(inParam);

            if (outParam != null)
            {
                String printflg = outParam.getString(ReplenishAllocateStartSCHParams.ISSUE_REPORT);
                if (SystemDefine.KEYDATA_OFF.equals(printflg))
                {
                    // 前回、チェックOFFだった場合は更新
                    chk_IssueReport.setChecked(false);
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
        // DFKLOOK:ここまで修正

    }

    /**
     *
     * @throws Exception
     */
    // DFKLOOK:ここから修正
    private void btn_Start_Click_Process(String eventSource)
            throws Exception
    // DFKLOOK:ここまで修正
    {
        // input validation.
        pul_ToReplenishmentArea.validate(this, true);
        txt_FromLocation.validate(this, false);
        txt_ToLocation.validate(this, false);
        txt_ItemCode.validate(this, false);
        txt_ReplenishmentRate.validate(this, true);
        pul_AllocatedPattern.validate(this, true);



        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ReplenishAllocateStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ReplenishAllocateStartSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            ReplenishAllocateStartSCHParams inparam = new ReplenishAllocateStartSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(ReplenishAllocateStartSCHParams.TO_REPLENISHMENT_AREA,
                    _pdm_pul_ToReplenishmentArea.getSelectedValue());
            // DFKLOOK start
            if (StringUtil.isBlank(eventSource))
            {
                if (!StringUtil.isBlank(lbl_LocationStyle.getValue()))
                {
                    // 棚のフォーマットチェック
                    String loc =
                            WmsFormatter.toParamLocation(txt_FromLocation.getStringValue(),
                                    SuperLocationHolder.getInstance().getLocationFormat(
                                            _pdm_pul_ToReplenishmentArea.getSelectedValue()));
                    inparam.set(ReplenishAllocateStartSCHParams.FROM_LOCATION, loc);
                    loc =
                            WmsFormatter.toParamLocation(txt_ToLocation.getStringValue(),
                                    SuperLocationHolder.getInstance().getLocationFormat(
                                            _pdm_pul_ToReplenishmentArea.getSelectedValue()));
                    inparam.set(ReplenishAllocateStartSCHParams.TO_LOCATION, loc);
                }
                else
                {
                    // 棚のフォーマットチェック
                    inparam.set(ReplenishAllocateStartSCHParams.FROM_LOCATION, txt_FromLocation.getStringValue());
                    inparam.set(ReplenishAllocateStartSCHParams.TO_LOCATION, txt_ToLocation.getStringValue());
                }

                // show confirm message. 開始しますか？
                this.setConfirm("MSG-W0031", true, true);
                viewState.setString(_KEY_CONFIRMSOURCE, "btn_Start_Click");
                // 「処理中です」メッセージ表示
                message.setMsgResourceKey("6001017");
                return;
            }

            if (!StringUtil.isBlank(lbl_LocationStyle.getValue()))
            {
                // 棚のフォーマットチェック
                String loc =
                        WmsFormatter.toParamLocation(txt_FromLocation.getStringValue(),
                                SuperLocationHolder.getInstance().getLocationFormat(
                                        _pdm_pul_ToReplenishmentArea.getSelectedValue()));
                inparam.set(ReplenishAllocateStartSCHParams.FROM_LOCATION, loc);
                loc =
                        WmsFormatter.toParamLocation(txt_ToLocation.getStringValue(),
                                SuperLocationHolder.getInstance().getLocationFormat(
                                        _pdm_pul_ToReplenishmentArea.getSelectedValue()));
                inparam.set(ReplenishAllocateStartSCHParams.TO_LOCATION, loc);
            }
            else
            {
                // 棚のフォーマットチェック
                inparam.set(ReplenishAllocateStartSCHParams.FROM_LOCATION, txt_FromLocation.getStringValue());
                inparam.set(ReplenishAllocateStartSCHParams.TO_LOCATION, txt_ToLocation.getStringValue());
            }
            // DFKLOOK end

            inparam.set(ReplenishAllocateStartSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(ReplenishAllocateStartSCHParams.RATE, txt_ReplenishmentRate.getValue());
            inparam.set(ReplenishAllocateStartSCHParams.ALLOC_PATTERN, _pdm_pul_AllocatedPattern.getSelectedValue());
            inparam.set(ReplenishAllocateStartSCHParams.ISSUE_REPORT, chk_IssueReport.getValue());
            inparam.set(ReplenishAllocateStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            // DFKLOOK start
            inparam.set(ReplenishAllocateStartSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));
            // DFKLOOK end

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
            part11List.add(_pdm_pul_ToReplenishmentArea.getSelectedStringValue(), "");
            part11List.add(txt_FromLocation.getStringValue(), "");
            part11List.add(txt_ToLocation.getStringValue(), "");
            part11List.add(txt_ItemCode.getStringValue(), "");
            part11List.add(txt_ReplenishmentRate.getStringValue(), "");
            part11List.add(_pdm_pul_AllocatedPattern.getSelectedStringValue(), "");

            if (chk_IssueReport.getChecked())
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
            if (chk_IssueReport.getChecked())
            {
                Object settingUkeys = inparam.get(ReplenishAllocateStartSCHParams.SETTING_UKEYS);
                Object asSettingUkeys = inparam.get(ReplenishAllocateStartSCHParams.AS_SETTING_UKEYS);
                Object shortageKey = inparam.get(ReplenishAllocateStartSCHParams.SHORTAGE_KEY);

                // メソッドgetSettingUnitKeys がnullでなければ帳票発行を行う
                if (!ArrayUtil.isEmpty(settingUkeys))
                {
                    printReplenishmentList(locale, ui, settingUkeys);
                }

                // メソッドgetAsrsSettingUnitKeys がnullでなければ帳票発行を行う
                if (!ArrayUtil.isEmpty(asSettingUkeys))
                {
                    printAsrsReplenishmentList(locale, ui, asSettingUkeys);
                }

                // 補充欠品リストを発行する
                printReplenishShortageList(locale, ui, shortageKey);

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

    // DFKLOOK ここから
    /**
     * 補充作業リストを発行します
     *
     * @param locale
     * @param ui
     * @param settingUkeys
     * @throws ValidateException
     */
    private void printReplenishmentList(Locale locale, DfkUserInfo ui, Object settingUkeys)
            throws Exception
    {
        boolean hasError = false;

        Connection conn = null;
        ReplenishWorkDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new ReplenishWorkDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            ReplenishWorkDASCHParams inparam = new ReplenishWorkDASCHParams();
            inparam.set(ReplenishWorkDASCHParams.SETTING_UKEYS, settingUkeys);

            // check count.
            int count = dasch.count(inparam);
            if (count == 0)
            {
                hasError = true;
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("ReplenishmentList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ReplenishmentListParams expparam = new ReplenishmentListParams();
                expparam.set(ReplenishmentListParams.DFK_DS_NO, outparam.get(ReplenishWorkDASCHParams.DFK_DS_NO));
                expparam.set(ReplenishmentListParams.DFK_USER_ID, outparam.get(ReplenishWorkDASCHParams.DFK_USER_ID));
                expparam.set(ReplenishmentListParams.DFK_USER_NAME,
                        outparam.get(ReplenishWorkDASCHParams.DFK_USER_NAME));
                expparam.set(ReplenishmentListParams.SYS_DAY, outparam.getDate(ReplenishWorkDASCHParams.SYS_DAY));
                expparam.set(ReplenishmentListParams.SYS_TIME, outparam.getDate(ReplenishWorkDASCHParams.SYS_TIME));
                expparam.set(ReplenishmentListParams.JOB_NO, outparam.get(ReplenishWorkDASCHParams.JOB_NO));
                expparam.set(ReplenishmentListParams.REP_RETRIEVAL_AREA_NO,
                        outparam.get(ReplenishWorkDASCHParams.REP_RETRIEVAL_AREA_NO));
                expparam.set(ReplenishmentListParams.REP_RETRIEVAL_AREA_NAME,
                        outparam.get(ReplenishWorkDASCHParams.REP_RETRIEVAL_AREA_NAME));
                expparam.set(ReplenishmentListParams.JOB_TYPE, outparam.get(ReplenishWorkDASCHParams.JOB_TYPE));
                expparam.set(ReplenishmentListParams.REP_RETRIEVAL_LOCATION_NO,
                        outparam.get(ReplenishWorkDASCHParams.REP_RETRIEVAL_LOCATION_NO));
                expparam.set(ReplenishmentListParams.ITEM_CODE, outparam.get(ReplenishWorkDASCHParams.ITEM_CODE));
                expparam.set(ReplenishmentListParams.ITEM_NAME, outparam.get(ReplenishWorkDASCHParams.ITEM_NAME));
                expparam.set(ReplenishmentListParams.LOT_NO, outparam.get(ReplenishWorkDASCHParams.LOT_NO));
                expparam.set(ReplenishmentListParams.ENTERING_QTY, outparam.get(ReplenishWorkDASCHParams.ENTERING_QTY));
                expparam.set(ReplenishmentListParams.PLAN_CASE_QTY,
                        outparam.get(ReplenishWorkDASCHParams.PLAN_CASE_QTY));
                expparam.set(ReplenishmentListParams.PLAN_PIECE_QTY,
                        outparam.get(ReplenishWorkDASCHParams.PLAN_PIECE_QTY));
                expparam.set(ReplenishmentListParams.REP_STORAGE_AREA_NO,
                        outparam.get(ReplenishWorkDASCHParams.REP_STORAGE_AREA_NO));
                expparam.set(ReplenishmentListParams.REP_LOCATION_NO,
                        outparam.get(ReplenishWorkDASCHParams.REP_LOCATION_NO));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }
            // execute print.
            try
            {
                exporter.print();
            }
            catch (Exception ex)
            {
                hasError = true;
                ex.printStackTrace();
                return;
            }

        }
        catch (Exception ex)
        {
            hasError = true;
            ex.printStackTrace();
            ExceptionHandler.getDisplayMessage(ex, this);
            return;
        }
        finally
        {
            if (hasError)
            {
                // 6007042=設定後、印刷に失敗しました。ログを参照してください。
                message.setMsgResourceKey(WmsMessageFormatter.format(6007042));
            }

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

    /**
     * AS/RS補充作業リストを発行します
     *
     * @param locale
     * @param ui
     * @param settingUkeys
     * @throws ValidateException
     */
    private void printAsrsReplenishmentList(Locale locale, DfkUserInfo ui, Object asSettingUkeys)
            throws Exception
    {
        boolean hasError = false;

        Connection conn = null;
        AsReplenishWorkDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new AsReplenishWorkDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            AsReplenishWorkDASCHParams inparam = new AsReplenishWorkDASCHParams();
            inparam.set(AsReplenishWorkDASCHParams.AS_SETTING_UKEYS, asSettingUkeys);

            // check count.
            int count = dasch.count(inparam);
            if (count == 0)
            {
                hasError = true;
                return;

            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("AsrsReplenishmentList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                AsrsReplenishmentListParams expparam = new AsrsReplenishmentListParams();
                expparam.set(AsrsReplenishmentListParams.DFK_DS_NO, outparam.get(AsReplenishWorkDASCHParams.DFK_DS_NO));
                expparam.set(AsrsReplenishmentListParams.DFK_USER_ID,
                        outparam.get(AsReplenishWorkDASCHParams.DFK_USER_ID));
                expparam.set(AsrsReplenishmentListParams.DFK_USER_NAME,
                        outparam.get(AsReplenishWorkDASCHParams.DFK_USER_NAME));
                expparam.set(AsrsReplenishmentListParams.SYS_DAY, outparam.get(AsReplenishWorkDASCHParams.SYS_DAY));
                expparam.set(AsrsReplenishmentListParams.SYS_TIME, outparam.get(AsReplenishWorkDASCHParams.SYS_TIME));
                expparam.set(AsrsReplenishmentListParams.SERCH_JOB_NO, outparam.get(AsReplenishWorkDASCHParams.LIST_NO));
                expparam.set(AsrsReplenishmentListParams.STATION_NO,
                        outparam.get(AsReplenishWorkDASCHParams.STATION_NO));
                expparam.set(AsrsReplenishmentListParams.REP_RETRIEVAL_AREA_NO,
                        outparam.get(AsReplenishWorkDASCHParams.RETRIEVAL_AREA_NO));
                expparam.set(AsrsReplenishmentListParams.REP_RETRIEVAL_AREA_NAME,
                        outparam.get(AsReplenishWorkDASCHParams.RETRIEVAL_AREA_NAME));
                expparam.set(AsrsReplenishmentListParams.JOB_TYPE, outparam.get(AsReplenishWorkDASCHParams.JOB_TYPE));
                expparam.set(AsrsReplenishmentListParams.JOB_NO, outparam.get(AsReplenishWorkDASCHParams.JOB_NO));
                expparam.set(AsrsReplenishmentListParams.REP_RETRIEVAL_LOCATION_NO,
                        outparam.get(AsReplenishWorkDASCHParams.RETRIEVAL_LOCATION_NO));
                expparam.set(AsrsReplenishmentListParams.ITEM_CODE, outparam.get(AsReplenishWorkDASCHParams.ITEM_CODE));
                expparam.set(AsrsReplenishmentListParams.ITEM_NAME, outparam.get(AsReplenishWorkDASCHParams.ITEM_NAME));
                expparam.set(AsrsReplenishmentListParams.LOT_NO, outparam.get(AsReplenishWorkDASCHParams.LOT_NO));
                expparam.set(AsrsReplenishmentListParams.ENTERING_QTY,
                        outparam.get(AsReplenishWorkDASCHParams.ENTERING_QTY));
                expparam.set(AsrsReplenishmentListParams.PLAN_CASE_QTY,
                        outparam.get(AsReplenishWorkDASCHParams.PLAN_CASE_QTY));
                expparam.set(AsrsReplenishmentListParams.PLAN_PIECE_QTY,
                        outparam.get(AsReplenishWorkDASCHParams.PLAN_PIECE_QTY));
                expparam.set(AsrsReplenishmentListParams.REP_STORAGE_AREA_NO,
                        outparam.get(AsReplenishWorkDASCHParams.STORAGE_AREA_NO));
                expparam.set(AsrsReplenishmentListParams.REP_LOCATION_NO,
                        outparam.get(AsReplenishWorkDASCHParams.STORAGE_LOCATION_NO));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }
            // execute print.
            try
            {
                exporter.print();
            }
            catch (Exception ex)
            {
                hasError = true;
                ex.printStackTrace();
                return;
            }

        }
        catch (Exception ex)
        {
            hasError = true;
            ex.printStackTrace();
            ExceptionHandler.getDisplayMessage(ex, this);
            return;

        }
        finally
        {
            if (hasError)
            {
                // 6007042=設定後、印刷に失敗しました。ログを参照してください。
                message.setMsgResourceKey(WmsMessageFormatter.format(6007042));
            }

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


    /**
     * 計画補充欠品リストを発行します
     *
     * @param locale
     * @param ui
     * @param settingUkeys
     * @throws ValidateException
     */
    private void printReplenishShortageList(Locale locale, DfkUserInfo ui, Object shortageKey)
            throws Exception
    {
        boolean hasError = false;

        Connection conn = null;
        ReplenishShortageDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new ReplenishShortageDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            ReplenishShortageDASCHParams inparam = new ReplenishShortageDASCHParams();
            inparam.set(ReplenishShortageDASCHParams.SHORTAGE_KEY, shortageKey);

            // check count.
            int count = dasch.count(inparam);
            if (count == 0)
            {
                // 欠品リストの場合は、対象データなしでもエラーではない
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("ReplenishShortageList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ReplenishShortageListParams expparam = new ReplenishShortageListParams();
                expparam.set(ReplenishShortageListParams.ALLOCATE_NO,
                        outparam.get(ReplenishShortageDASCHParams.ALLOCATE_NO));
                expparam.set(ReplenishShortageListParams.AREA_NO, outparam.get(ReplenishShortageDASCHParams.AREA_NO));
                expparam.set(ReplenishShortageListParams.DFK_DS_NO,
                        outparam.get(ReplenishShortageDASCHParams.DFK_DS_NO));
                expparam.set(ReplenishShortageListParams.DFK_USER_ID,
                        outparam.get(ReplenishShortageDASCHParams.DFK_USER_ID));
                expparam.set(ReplenishShortageListParams.DFK_USER_NAME,
                        outparam.get(ReplenishShortageDASCHParams.DFK_USER_NAME));
                expparam.set(ReplenishShortageListParams.ENTERING_QTY,
                        outparam.get(ReplenishShortageDASCHParams.ENTERING_QTY));
                expparam.set(ReplenishShortageListParams.ITEM_CODE,
                        outparam.get(ReplenishShortageDASCHParams.ITEM_CODE));
                expparam.set(ReplenishShortageListParams.ITEM_NAME,
                        outparam.get(ReplenishShortageDASCHParams.ITEM_NAME));
                expparam.set(ReplenishShortageListParams.LOCATION_NO,
                        outparam.get(ReplenishShortageDASCHParams.LOCATION_NO));
                expparam.set(ReplenishShortageListParams.PLAN_CASE_QTY,
                        outparam.get(ReplenishShortageDASCHParams.PLAN_CASE_QTY));
                expparam.set(ReplenishShortageListParams.PLAN_PIECE_QTY,
                        outparam.get(ReplenishShortageDASCHParams.PLAN_PIECE_QTY));
                expparam.set(ReplenishShortageListParams.RATE, outparam.get(ReplenishShortageDASCHParams.RATE));
                expparam.set(ReplenishShortageListParams.REPLENISH_QTY,
                        outparam.get(ReplenishShortageDASCHParams.REPLENISH_QTY));
                expparam.set(ReplenishShortageListParams.SHORT_CASE_QTY,
                        outparam.get(ReplenishShortageDASCHParams.SHORT_CASE_QTY));
                expparam.set(ReplenishShortageListParams.SHORT_PIECE_QTY,
                        outparam.get(ReplenishShortageDASCHParams.SHORT_PIECE_QTY));
                expparam.set(ReplenishShortageListParams.SYS_DAY, outparam.get(ReplenishShortageDASCHParams.SYS_DAY));
                expparam.set(ReplenishShortageListParams.SYS_TIME, outparam.get(ReplenishShortageDASCHParams.SYS_TIME));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }
            // execute print.
            try
            {
                exporter.print();
            }
            catch (Exception ex)
            {
                hasError = true;
                ex.printStackTrace();
                return;
            }

        }
        catch (Exception ex)
        {
            hasError = true;
            ex.printStackTrace();
            ExceptionHandler.getDisplayMessage(ex, this);
            return;

        }
        finally
        {
            if (hasError)
            {
                // 6007042=設定後、印刷に失敗しました。ログを参照してください。
                message.setMsgResourceKey(WmsMessageFormatter.format(6007042));
            }

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

    // DFKLOOK ここまで

    /**
     *
     * @throws Exception
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        _pdm_pul_ToReplenishmentArea.setSelectedValue(null);
        txt_FromLocation.setValue(null);
        txt_ToLocation.setValue(null);
        txt_ItemCode.setValue(null);
        txt_ReplenishmentRate.setValue(null);
        _pdm_pul_AllocatedPattern.setSelectedValue(null);
        chk_IssueReport.setChecked(true);
        txt_FromLocation.setReadOnly(true);
        txt_ToLocation.setReadOnly(true);

        // DFKLOOK:ここから修正
        // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(
                _pdm_pul_ToReplenishmentArea.getSelectedValue()));
        // DFKLOOK:ここまで修正

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
