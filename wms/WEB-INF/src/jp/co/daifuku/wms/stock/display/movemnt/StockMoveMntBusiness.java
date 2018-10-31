// $Id: StockMoveMntBusiness.java 7663 2010-03-17 11:36:27Z shibamoto $
package jp.co.daifuku.wms.stock.display.movemnt;

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
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.LocationFormatException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsChecker;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.stock.dasch.StockMoveMntDASCH;
import jp.co.daifuku.wms.stock.dasch.StockMoveMntDASCHParams;
import jp.co.daifuku.wms.stock.exporter.StockMoveWorkListParams;
import jp.co.daifuku.wms.stock.schedule.StockMoveMntSCH;
import jp.co.daifuku.wms.stock.schedule.StockMoveMntSCHParams;

/**
 * 在庫移動RFTメンテナンスの画面処理を行います。
 * 
 * @version $Revision: 7663 $, $Date: 2010-03-17 20:36:27 +0900 (水, 17 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class StockMoveMntBusiness
        extends StockMoveMnt
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

    /** lst_StockMoveMaintenance(HIDDEN_JOB_NO) */
    private static final ListCellKey KEY_HIDDEN_JOB_NO = new ListCellKey("HIDDEN_JOB_NO", new StringCellColumn(), false, false);

    /** lst_StockMoveMaintenance(HIDDEN_RETRIEVALDATE) */
    private static final ListCellKey KEY_HIDDEN_RETRIEVALDATE = new ListCellKey("HIDDEN_RETRIEVALDATE", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), false, false);

    /** lst_StockMoveMaintenance(HIDDEN_RFTNO) */
    private static final ListCellKey KEY_HIDDEN_RFTNO = new ListCellKey("HIDDEN_RFTNO", new StringCellColumn(), false, false);

    /** lst_StockMoveMaintenance(HIDDEN_USER_NAME) */
    private static final ListCellKey KEY_HIDDEN_USER_NAME = new ListCellKey("HIDDEN_USER_NAME", new StringCellColumn(), false, false);

    /** lst_StockMoveMaintenance(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new CheckBoxColumn(), true, true);

    /** lst_StockMoveMaintenance(LST_FROM_AREA) */
    private static final ListCellKey KEY_LST_FROM_AREA = new ListCellKey("LST_FROM_AREA", new AreaCellColumn(), true, false);

    /** lst_StockMoveMaintenance(LST_FROM_LOCATION) */
    private static final ListCellKey KEY_LST_FROM_LOCATION = new ListCellKey("LST_FROM_LOCATION", new LocationCellColumn(), true, false);

    /** lst_StockMoveMaintenance(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_StockMoveMaintenance(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_StockMoveMaintenance(LST_LOT) */
    private static final ListCellKey KEY_LST_LOT = new ListCellKey("LST_LOT", new StringCellColumn(), true, false);

    /** lst_StockMoveMaintenance(LST_CASE_PACK) */
    private static final ListCellKey KEY_LST_CASE_PACK = new ListCellKey("LST_CASE_PACK", new NumberCellColumn(0), true, false);

    /** lst_StockMoveMaintenance(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY = new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StockMoveMaintenance(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY = new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StockMoveMaintenance(LST_RELOCATION_CASE_QTY) */
    private static final ListCellKey KEY_LST_RELOCATION_CASE_QTY = new ListCellKey("LST_RELOCATION_CASE_QTY", new NumberCellColumn(0), true, true);

    /** lst_StockMoveMaintenance(LST_RELOCATION_PIECE_QTY) */
    private static final ListCellKey KEY_LST_RELOCATION_PIECE_QTY = new ListCellKey("LST_RELOCATION_PIECE_QTY", new NumberCellColumn(0), true, true);

    /** lst_StockMoveMaintenance(LST_TO_AREA) */
    private static final ListCellKey KEY_LST_TO_AREA = new ListCellKey("LST_TO_AREA", new AreaCellColumn(), true, true);

    /** lst_StockMoveMaintenance(LST_TO_LOCATION) */
    private static final ListCellKey KEY_LST_TO_LOCATION = new ListCellKey("LST_TO_LOCATION", new StringCellColumn(), true, true);

    /** lst_StockMoveMaintenance keys */
    private static final ListCellKey[] LST_STOCKMOVEMAINTENANCE_KEYS = {
        KEY_HIDDEN_JOB_NO,
        KEY_HIDDEN_RETRIEVALDATE,
        KEY_HIDDEN_RFTNO,
        KEY_HIDDEN_USER_NAME,
        KEY_LST_CANCEL,
        KEY_LST_FROM_AREA,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT,
        KEY_LST_CASE_PACK,
        KEY_LST_PLAN_CASE_QTY,
        KEY_LST_RELOCATION_CASE_QTY,
        KEY_LST_TO_AREA,
        KEY_LST_FROM_LOCATION,
        KEY_LST_ITEM_NAME,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_RELOCATION_PIECE_QTY,
        KEY_LST_TO_LOCATION,
    };

    // DFKLOOK ここから修正
    // 移動ケース数(リストセル)
    private static final int LST_MOVE_CASEQTY = 7;
    // DFKLOOK ここまで修正

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_FromMoveArea */
    private WmsAreaPullDownModel _pdm_pul_FromMoveArea;

    /** ListCellModel lst_StockMoveMaintenance */
    private ListCellModel _lcm_lst_StockMoveMaintenance;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public StockMoveMntBusiness()
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
        // DFKLOOK:ここから
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
        // DFKLOOK:ここまで
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pul_FromMoveArea_Change(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから　棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(
                pul_FromMoveArea.getSelectedValue()));
        
        if (WmsParam.ALL_AREA_NO.equals(pul_FromMoveArea.getSelectedValue()))
        {
            txt_Location.setText("");
            txt_Location.setReadOnly(true);
        }
        else 
        {
            txt_Location.setReadOnly(false);
        }
        // DFKLOOK:ここまで
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
    public void btn_Set_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから
        // process call.
        btn_Set_Click_Process(null);
        // DFKLOOK:ここまで
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllCheck_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllCheck_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllCheckClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllCheckClear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllClear_Click_Process();
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

        // initialize pul_FromMoveArea.
        _pdm_pul_FromMoveArea = new WmsAreaPullDownModel(pul_FromMoveArea, locale, ui);

        // initialize lst_StockMoveMaintenance.
        _lcm_lst_StockMoveMaintenance = new ListCellModel(lst_StockMoveMaintenance, LST_STOCKMOVEMAINTENANCE_KEYS, locale);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_CANCEL, true);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_FROM_AREA, true);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_FROM_LOCATION, true);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_LOT, true);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_CASE_PACK, true);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_RELOCATION_CASE_QTY, false);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_RELOCATION_PIECE_QTY, false);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_TO_AREA, false);
        _lcm_lst_StockMoveMaintenance.setToolTipVisible(KEY_LST_TO_LOCATION, false);

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

            // load pul_FromMoveArea.
            _pdm_pul_FromMoveArea.init(conn, AreaType.FLOOR_AND_TEMP_AND_RECEIVE,StationType.ALL, "", true);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_StockMoveMaintenance_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        // DFKLOOK ここから修正
        line.addToolTip("LBL-W0355", KEY_HIDDEN_RETRIEVALDATE);
        line.addToolTip("LBL-W0033", KEY_HIDDEN_USER_NAME);
        line.addToolTip("LBL-W0005", KEY_HIDDEN_RFTNO);
        // DFKLOOK ここまで修正
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
        setFocus(pul_FromMoveArea);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_Set.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        chk_LIssueReport.setChecked(true);
        chk_LIssueReport.setEnabled(false);
        txt_Location.setReadOnly(true);

        // DFKLOOK:ここから 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(
                pul_FromMoveArea.getSelectedValue()));
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StockMoveMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StockMoveMntSCH(conn, this.getClass(), locale, ui);
            
            StockMoveMntSCHParams inParam = new StockMoveMntSCHParams();
            inParam.set(StockMoveMntSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));

            // 画面定義テーブルをチェック
            Params outParam = sch.initFind(inParam);
            
            if (outParam != null)
            {
                String printflg = outParam.getString(StockMoveMntSCHParams.L_ISSUE_REPORT);
                if (SystemDefine.KEYDATA_OFF.equals(printflg))
                {
                    // 前回、チェックOFFだった場合は更新
                    chk_LIssueReport.setChecked(false);
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
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        pul_FromMoveArea.validate(this, true);
        txt_Location.validate(this, false);
        txt_ItemCode.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StockMoveMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StockMoveMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            StockMoveMntSCHParams inparam = new StockMoveMntSCHParams();
            inparam.set(StockMoveMntSCHParams.FROM_MOVE_AREA_NO, _pdm_pul_FromMoveArea.getSelectedValue());
            // DFKLOOK start
            if (!StringUtil.isBlank(lbl_LocationStyle.getValue()))
            {
                // 棚のフォーマットチェック
                String loc =
                        WmsFormatter.toParamLocation(
                                txt_Location.getStringValue(),
                                SuperLocationHolder.getInstance().getLocationFormat(pul_FromMoveArea.getSelectedValue()));
                inparam.set(StockMoveMntSCHParams.LOCATION, loc);
            }
            // DFKLOOK end
            inparam.set(StockMoveMntSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(StockMoveMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

			// DFKLOOK start
            if (outparams.size() == 0)
            {
                // DFKLOOK ここから修正
                btn_Set.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_AllClear.setEnabled(false);
                chk_LIssueReport.setEnabled(false);
                _lcm_lst_StockMoveMaintenance.clear();
                // DFKLOOK ここまで修正
                return;
            }
			// DFKLOOK end
            // output display.
            _lcm_lst_StockMoveMaintenance.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StockMoveMaintenance.getNewLine();
                line.setValue(KEY_LST_FROM_AREA, outparam.get(StockMoveMntSCHParams.RETRIEVAL_AREA_NO));
                line.setValue(KEY_LST_FROM_LOCATION, outparam.get(StockMoveMntSCHParams.RETRIEVAL_LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(StockMoveMntSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(StockMoveMntSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT, outparam.get(StockMoveMntSCHParams.LOT_NO));
                line.setValue(KEY_LST_CASE_PACK, outparam.get(StockMoveMntSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(StockMoveMntSCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(StockMoveMntSCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_RELOCATION_CASE_QTY, outparam.get(StockMoveMntSCHParams.MOVE_CASE_QTY));
                line.setValue(KEY_LST_RELOCATION_PIECE_QTY, outparam.get(StockMoveMntSCHParams.MOVE_PIECE_QTY));
                line.setValue(KEY_LST_TO_AREA, outparam.get(StockMoveMntSCHParams.MOVE_AREA_NO));
                line.setValue(KEY_LST_TO_LOCATION, outparam.get(StockMoveMntSCHParams.MOVE_LOCATION_NO));
                viewState.setObject(ViewStateKeys.FROM_MOVE_AREA, _pdm_pul_FromMoveArea.getSelectedValue());
                viewState.setObject(ViewStateKeys.LOCATION, txt_Location.getValue());
                viewState.setObject(ViewStateKeys.ITEM_CODE, txt_ItemCode.getValue());
                line.setValue(KEY_HIDDEN_JOB_NO, outparam.get(StockMoveMntSCHParams.JOB_NO));
                line.setValue(KEY_HIDDEN_RETRIEVALDATE, outparam.get(StockMoveMntSCHParams.RETRIEVALDATE));
                line.setValue(KEY_HIDDEN_RFTNO, outparam.get(StockMoveMntSCHParams.RFT_NO));
                line.setValue(KEY_HIDDEN_USER_NAME, outparam.get(StockMoveMntSCHParams.USER_NAME));
                lst_StockMoveMaintenance_SetLineToolTip(line);
                _lcm_lst_StockMoveMaintenance.add(line);
                // DFKLOOK ここから修正
                if (outparam.getInt(StockMoveMntSCHParams.ENTERING_QTY) == 0)
                {
                    lst_StockMoveMaintenance.setCurrentRow(_lcm_lst_StockMoveMaintenance.getListCell().getCurrentRow());
                    lst_StockMoveMaintenance.setCellReadOnly(LST_MOVE_CASEQTY, true);
                }
                // DFKLOOK ここまで修正
            }

            // clear.
            btn_Set.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_AllClear.setEnabled(true);
            chk_LIssueReport.setEnabled(true);
            chk_LIssueReport.setChecked(true);

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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        _pdm_pul_FromMoveArea.setSelectedValue(null);
        txt_Location.setValue(null);
        txt_ItemCode.setValue(null);

        // DFKLOOK:ここから 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(
                pul_FromMoveArea.getSelectedValue()));
        txt_Location.setReadOnly(true);
        // DFKLOOK:ここまで修正

    }

    /**
     *
     * @throws Exception
     */
        // DFKLOOK:ここから
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
        // DFKLOOK:ここまで
    {

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StockMoveMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StockMoveMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_StockMoveMaintenance.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_StockMoveMaintenance.get(i);

                // DFKLOOK ここから修正
                //キャンセルチェックがあるか、リストセルに入力がある場合パラメータにセットする
                if ((Boolean)line.getValue(KEY_LST_CANCEL)
                        || line.getStringValue(KEY_LST_RELOCATION_CASE_QTY).length() > 0
                        || line.getStringValue(KEY_LST_RELOCATION_PIECE_QTY).length() > 0
                        || line.getStringValue(KEY_LST_TO_AREA).length() > 0
                        || line.getStringValue(KEY_LST_TO_LOCATION).length() > 0)
                {
                    // DFKLOOK ここまで修正

                    StockMoveMntSCHParams lineparam = new StockMoveMntSCHParams();
                    lineparam.setProcessFlag(ProcessFlag.UPDATE);
                    lineparam.setRowIndex(i);
                    lineparam.set(StockMoveMntSCHParams.JOB_NO, line.getValue(KEY_HIDDEN_JOB_NO));
                    lineparam.set(StockMoveMntSCHParams.RETRIEVALDATE, line.getValue(KEY_HIDDEN_RETRIEVALDATE));
                    lineparam.set(StockMoveMntSCHParams.RFT_NO, line.getValue(KEY_HIDDEN_RFTNO));
                    lineparam.set(StockMoveMntSCHParams.USER_NAME, line.getValue(KEY_HIDDEN_USER_NAME));
                    lineparam.set(StockMoveMntSCHParams.CANCEL, line.getValue(KEY_LST_CANCEL));
                    lineparam.set(StockMoveMntSCHParams.RETRIEVAL_AREA_NO, line.getValue(KEY_LST_FROM_AREA));
                    lineparam.set(StockMoveMntSCHParams.RETRIEVAL_LOCATION_NO, line.getStringValue(KEY_LST_FROM_LOCATION));
                    lineparam.set(StockMoveMntSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                    lineparam.set(StockMoveMntSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                    lineparam.set(StockMoveMntSCHParams.LOT_NO, line.getValue(KEY_LST_LOT));
                    lineparam.set(StockMoveMntSCHParams.ENTERING_QTY, line.getValue(KEY_LST_CASE_PACK));
                    lineparam.set(StockMoveMntSCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                    lineparam.set(StockMoveMntSCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                    lineparam.set(StockMoveMntSCHParams.MOVE_CASE_QTY, line.getValue(KEY_LST_RELOCATION_CASE_QTY));
                    lineparam.set(StockMoveMntSCHParams.MOVE_PIECE_QTY, line.getValue(KEY_LST_RELOCATION_PIECE_QTY));
                    lineparam.set(StockMoveMntSCHParams.MOVE_AREA_NO, line.getValue(KEY_LST_TO_AREA));

                    //DFKLOOK:ここから修正
                    try
                    {
                        String loc =
                                WmsFormatter.toParamLocation(line.getStringValue(KEY_LST_TO_LOCATION),
                                        SuperLocationHolder.getInstance().getLocationFormat(
                                                line.getStringValue(KEY_LST_TO_AREA)));
                        lineparam.set(StockMoveMntSCHParams.MOVE_LOCATION_NO, loc);
                    }
                    // 棚フォーマットで投げられたExceptionをここでキャッチ
                    catch (LocationFormatException ex)
                    {
                        //6023317=No.{0} 棚のフォーマットが違います。
                        message.setMsgResourceKey(WmsMessageFormatter.format(6023317, i));
                        _lcm_lst_StockMoveMaintenance.resetHighlight();
                        _lcm_lst_StockMoveMaintenance.addHighlight(i, ControlColor.Warning);
                        return;
                    }
                    catch (ScheduleException ex)
                    {
                        //6023030=No.{0} 指定されたエリアは、存在しません。
                        message.setMsgResourceKey(WmsMessageFormatter.format(6023030, i));
                        _lcm_lst_StockMoveMaintenance.resetHighlight();
                        _lcm_lst_StockMoveMaintenance.addHighlight(i, ControlColor.Warning);
                        return;
                    }
                    //DFKLOOK:ここまで修正

                    lineparam.set(StockMoveMntSCHParams.L_ISSUE_REPORT, chk_LIssueReport.getValue());
                    lineparam.set(StockMoveMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

                    // DFKLOOK:ここから
                    lineparam.set(StockMoveMntSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));
                    // DFKLOOK:ここまで

                    inparamList.add(lineparam);

                    // DFKLOOK ここから修正
                    // 移動先エリアNo.入力チェック
                    if (line.getStringValue(KEY_LST_TO_AREA).length() > 0)
                    {
                        // 空白チェック
                        WmsChecker checker = new WmsChecker();
                        lst_StockMoveMaintenance.setCurrentRow(i + 1);
                        // エリアNo.(リストセル)
                        if (!checker.checkContainNgText((line.getStringValue(KEY_LST_TO_AREA)), i + 1, "LBL-W0199"))
                        {
                            message.setMsgResourceKey(checker.getMessage());
                            DisplayUtil.addHighlight(lst_StockMoveMaintenance, i, ControlColor.Yellow);
                            return;
                        }
                    }
                    //パラメータチェック
                    if (!sch.check(lineparam))
                    {
                        _lcm_lst_StockMoveMaintenance.resetHighlight();
                        DisplayUtil.addHighlight(lst_StockMoveMaintenance, lineparam.getRowIndex(),
                                ControlColor.Warning);
                        // メッセージのセット
                        message.setMsgResourceKey(sch.getMessage());
                        return;
                    }
                }
            }
            if (inparamList.size() == 0)
            {
                //6023059=更新対象データがありません。
                message.setMsgResourceKey("6023059");
                return;
            }
            
            // DFKLOOK:ここから修正
            //データ選択有無チェック
            boolean checkDate = false;

            if (StringUtil.isBlank(eventSource))
            {
                // 設定しますか?
                this.setConfirm("MSG-W9000", false, true);
                viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
                return;
            }
            // DFKLOOK:ここまで修正



            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_StockMoveMaintenance.resetEditRow();
                _lcm_lst_StockMoveMaintenance.resetHighlight();
                _lcm_lst_StockMoveMaintenance.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_StockMoveMaintenance.size(); i++)
            {
                ListCellLine line = _lcm_lst_StockMoveMaintenance.get(i);
                lst_StockMoveMaintenance.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_FROM_AREA), "");
                part11List.add(line.getViewString(KEY_LST_FROM_LOCATION), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT), "");
                part11List.add(line.getViewString(KEY_LST_CASE_PACK), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "");

                if (!lst_StockMoveMaintenance.getChecked(_lcm_lst_StockMoveMaintenance.getColumnIndex(KEY_LST_CANCEL)))
                {
                    part11List.add(line.getViewString(KEY_LST_RELOCATION_CASE_QTY), "");
                    part11List.add(line.getViewString(KEY_LST_RELOCATION_PIECE_QTY), "");
                    part11List.add(line.getViewString(KEY_LST_TO_AREA), "");
                    part11List.add(line.getViewString(KEY_LST_TO_LOCATION), "");
                    part11List.add(line.getViewString(KEY_HIDDEN_RETRIEVALDATE), "");
                    part11List.add(line.getViewString(KEY_HIDDEN_RFTNO), "");

                    //DFKLOOK:ここから修正
                    if (chk_LIssueReport.getChecked())
                    {
                        part11List.add("1", "");
                    }
                    else
                    {
                        part11List.add("0", "");
                    }
                    part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_MODIFY),
                            part11List);
                    //DFKLOOK:ここまで修正
                }
                else
                {
                    part11List.add(line.getViewString(KEY_HIDDEN_RETRIEVALDATE), "");
                    part11List.add(line.getViewString(KEY_HIDDEN_RFTNO), "");

                    //DFKLOOK:ここから修正
                    if (chk_LIssueReport.getChecked())
                    {
                        part11List.add("1", "");
                    }
                    else
                    {
                        part11List.add("0", "");
                    }
                    part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_CANCEL),
                            part11List);
                    //DFKLOOK:ここまで修正
                }
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK ここから
            if (chk_LIssueReport.getChecked())
            {

                // リストを発行する

                if (printMoveWorkList(locale, ui))
                {
                    message.setMsgResourceKey(sch.getMessage());
                }

            }
            // DFKLOOK ここまで

            // reset editing row.
            _lcm_lst_StockMoveMaintenance.resetEditRow();
            _lcm_lst_StockMoveMaintenance.resetHighlight();

            // set input parameters.
            StockMoveMntSCHParams inparam = new StockMoveMntSCHParams();
            inparam.set(StockMoveMntSCHParams.FROM_MOVE_AREA_NO, viewState.getObject(ViewStateKeys.FROM_MOVE_AREA));
            inparam.set(StockMoveMntSCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
            inparam.set(StockMoveMntSCHParams.LOCATION, viewState.getObject(ViewStateKeys.LOCATION));
            inparam.set(StockMoveMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_StockMoveMaintenance.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StockMoveMaintenance.getNewLine();
                line.setValue(KEY_HIDDEN_JOB_NO, outparam.get(StockMoveMntSCHParams.JOB_NO));
                line.setValue(KEY_HIDDEN_RETRIEVALDATE, outparam.get(StockMoveMntSCHParams.RETRIEVALDATE));
                line.setValue(KEY_HIDDEN_RFTNO, outparam.get(StockMoveMntSCHParams.RFT_NO));
                line.setValue(KEY_HIDDEN_USER_NAME, outparam.get(StockMoveMntSCHParams.USER_NAME));
                line.setValue(KEY_LST_FROM_AREA, outparam.get(StockMoveMntSCHParams.RETRIEVAL_AREA_NO));
                line.setValue(KEY_LST_FROM_LOCATION, outparam.get(StockMoveMntSCHParams.RETRIEVAL_LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(StockMoveMntSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(StockMoveMntSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT, outparam.get(StockMoveMntSCHParams.LOT_NO));
                line.setValue(KEY_LST_CASE_PACK, outparam.get(StockMoveMntSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(StockMoveMntSCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(StockMoveMntSCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_RELOCATION_CASE_QTY, outparam.get(StockMoveMntSCHParams.MOVE_CASE_QTY));
                line.setValue(KEY_LST_RELOCATION_PIECE_QTY, outparam.get(StockMoveMntSCHParams.MOVE_PIECE_QTY));
                line.setValue(KEY_LST_TO_AREA, outparam.get(StockMoveMntSCHParams.MOVE_AREA_NO));
                line.setValue(KEY_LST_TO_LOCATION, outparam.get(StockMoveMntSCHParams.MOVE_LOCATION_NO));
                lst_StockMoveMaintenance_SetLineToolTip(line);
                _lcm_lst_StockMoveMaintenance.add(line);
                // DFKLOOK ここから修正
                if (outparam.getInt(StockMoveMntSCHParams.ENTERING_QTY) == 0)
                {
                    lst_StockMoveMaintenance.setCurrentRow(_lcm_lst_StockMoveMaintenance.getListCell().getCurrentRow());
                    lst_StockMoveMaintenance.setCellReadOnly(LST_MOVE_CASEQTY, true);
                }
                // DFKLOOK ここまで修正
            }

            if (outparams.size() == 0)
            {
                // DFKLOOK ここから修正
                btn_Set.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_AllClear.setEnabled(false);
                chk_LIssueReport.setEnabled(false);
                _lcm_lst_StockMoveMaintenance.clear();
                // DFKLOOK ここまで修正
                return;
            }

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
    private void btn_AllCheck_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_StockMoveMaintenance.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_StockMoveMaintenance.get(i);
            lst_StockMoveMaintenance.setCurrentRow(i);
            clearLine.setValue(KEY_LST_CANCEL, Boolean.TRUE);
            lst_StockMoveMaintenance_SetLineToolTip(clearLine);
            _lcm_lst_StockMoveMaintenance.set(i, clearLine);
        }

    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllCheckClear_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_StockMoveMaintenance.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_StockMoveMaintenance.get(i);
            lst_StockMoveMaintenance.setCurrentRow(i);
            clearLine.setValue(KEY_LST_CANCEL, Boolean.FALSE);
            lst_StockMoveMaintenance_SetLineToolTip(clearLine);
            _lcm_lst_StockMoveMaintenance.set(i, clearLine);
        }

    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_StockMoveMaintenance.clear();
        btn_Set.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        chk_LIssueReport.setEnabled(false);

    }

    // DFKLOOK ここから修正
    /**
     * 在庫移動リストを発行します
     * 
     * @param locale ロケール
     * @param ui ユーザー情報
     * @throws ValidateException 文字不整合エラー発生時
     * @return boolean 正常終了:true 異常終了:false
     */
    private boolean printMoveWorkList(Locale locale, DfkUserInfo ui)
            throws ValidateException
    {
        Connection conn = null;
        StockMoveMntDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new StockMoveMntDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            List<String> listArry = new ArrayList<String>();

            for (int i = 1; i <= _lcm_lst_StockMoveMaintenance.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_StockMoveMaintenance.get(i);
                if ((Boolean)line.getValue(KEY_LST_CANCEL)
                        || line.getStringValue(KEY_LST_RELOCATION_CASE_QTY).length() > 0
                        || line.getStringValue(KEY_LST_RELOCATION_PIECE_QTY).length() > 0
                        || line.getStringValue(KEY_LST_TO_AREA).length() > 0
                        || line.getStringValue(KEY_LST_TO_LOCATION).length() > 0)
                {
                    String jobNo = line.getStringValue(KEY_HIDDEN_JOB_NO);

                    listArry.add(jobNo);
                }
            }
            String[] jobNos = new String[listArry.size()];
            listArry.toArray(jobNos);

            StockMoveMntDASCHParams inparam = new StockMoveMntDASCHParams();
            inparam.set(StockMoveMntDASCHParams.JOB_NO, jobNos);

            // check count.
            int count = dasch.count(inparam);
            if (count == 0)
            {
                return true;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            if (exporter == null)
            {
                ExporterFactory factory = new WmsExporterFactory(locale, ui);
                exporter = factory.newPrinterExporter("StockMoveWorkList", false);
                exporter.open();
            }

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                StockMoveWorkListParams expparam = new StockMoveWorkListParams();
                expparam.set(StockMoveWorkListParams.DFK_DS_NO, outparam.get(StockMoveMntDASCHParams.DFK_DS_NO));
                expparam.set(StockMoveWorkListParams.DFK_USER_ID, outparam.get(StockMoveMntDASCHParams.DFK_USER_ID));
                expparam.set(StockMoveWorkListParams.DFK_USER_NAME, outparam.get(StockMoveMntDASCHParams.DFK_USER_NAME));
                expparam.set(StockMoveWorkListParams.LISTNO, outparam.get(StockMoveMntDASCHParams.LISTNO));
                expparam.set(StockMoveWorkListParams.SYS_DAY, outparam.get(StockMoveMntDASCHParams.SYS_DAY));
                expparam.set(StockMoveWorkListParams.SYS_TIME, outparam.get(StockMoveMntDASCHParams.SYS_TIME));
                expparam.set(StockMoveWorkListParams.RETRIEVAL_AREA_NO,
                        outparam.get(StockMoveMntDASCHParams.RETRIEVAL_AREA_NO));
                expparam.set(StockMoveWorkListParams.RETRIEVAL_LOCATION_NO,
                        outparam.get(StockMoveMntDASCHParams.RETRIEVAL_LOCATION_NO));
                expparam.set(StockMoveWorkListParams.ITEM_CODE, outparam.get(StockMoveMntDASCHParams.ITEM_CODE));
                expparam.set(StockMoveWorkListParams.ITEM_NAME, outparam.get(StockMoveMntDASCHParams.ITEM_NAME));
                expparam.set(StockMoveWorkListParams.LOT_NO, outparam.get(StockMoveMntDASCHParams.LOT_NO));
                expparam.set(StockMoveWorkListParams.ENTERING_QTY, outparam.get(StockMoveMntDASCHParams.ENTERING_QTY));
                expparam.set(StockMoveWorkListParams.MOVEMENT_CASE_QTY,
                        outparam.get(StockMoveMntDASCHParams.MOVEMENT_CASE_QTY));
                expparam.set(StockMoveWorkListParams.MOVEMENT_PIECE_QTY,
                        outparam.get(StockMoveMntDASCHParams.MOVEMENT_PIECE_QTY));
                expparam.set(StockMoveWorkListParams.STORAGE_AREA_NO,
                        outparam.get(StockMoveMntDASCHParams.STORAGE_AREA_NO));
                expparam.set(StockMoveWorkListParams.STORAGE_LOCATION_NO,
                        outparam.get(StockMoveMntDASCHParams.STORAGE_LOCATION_NO));
                exporter.write(expparam);
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
            DBUtil.close(conn);
        }
        return true;
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
