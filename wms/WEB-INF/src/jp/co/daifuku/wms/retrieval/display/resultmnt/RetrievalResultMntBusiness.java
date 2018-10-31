// $Id: RetrievalResultMntBusiness.java 7597 2010-03-16 01:16:06Z ota $
package jp.co.daifuku.wms.retrieval.display.resultmnt;

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
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalResultMntSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalResultMntSCHParams;

/**
 * 出庫実績メンテナンスの画面処理を行います。
 *
 * @version $Revision: 7597 $, $Date: 2010-03-16 10:16:06 +0900 (火, 16 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class RetrievalResultMntBusiness
        extends RetrievalResultMnt
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

    /** lst_RetrievalResultMaintenance(HIDDEN_BATCH) */
    private static final ListCellKey KEY_HIDDEN_BATCH =
            new ListCellKey("HIDDEN_BATCH", new StringCellColumn(), false, false);

    /** lst_RetrievalResultMaintenance(HIDDEN_TICKET) */
    private static final ListCellKey KEY_HIDDEN_TICKET =
            new ListCellKey("HIDDEN_TICKET", new StringCellColumn(), false, false);

    /** lst_RetrievalResultMaintenance(HIDDEN_LINE) */
    private static final ListCellKey KEY_HIDDEN_LINE =
            new ListCellKey("HIDDEN_LINE", new StringCellColumn(), false, false);

    /** lst_RetrievalResultMaintenance(HIDDEN_BRANCH) */
    private static final ListCellKey KEY_HIDDEN_BRANCH =
            new ListCellKey("HIDDEN_BRANCH", new StringCellColumn(), false, false);

    /** lst_RetrievalResultMaintenance(HIDDEN_CUSTOMER) */
    private static final ListCellKey KEY_HIDDEN_CUSTOMER =
            new ListCellKey("HIDDEN_CUSTOMER", new StringCellColumn(), false, false);

    /** lst_RetrievalResultMaintenance(HIDDEN_CASE) */
    private static final ListCellKey KEY_HIDDEN_CASE =
            new ListCellKey("HIDDEN_CASE", new StringCellColumn(), false, false);

    /** lst_RetrievalResultMaintenance(HIDDEN_PIECE) */
    private static final ListCellKey KEY_HIDDEN_PIECE =
            new ListCellKey("HIDDEN_PIECE", new StringCellColumn(), false, false);

    /** lst_RetrievalResultMaintenance(HIDDEN_PLAN) */
    private static final ListCellKey KEY_HIDDEN_PLAN =
            new ListCellKey("HIDDEN_PLAN", new StringCellColumn(), false, false);

    /** lst_RetrievalResultMaintenance(HIDDEN_JOBNO) */
    private static final ListCellKey KEY_HIDDEN_JOBNO =
            new ListCellKey("HIDDEN_JOBNO", new StringCellColumn(), false, false);

    /** lst_RetrievalResultMaintenance(HIDDEN_PLANUKEY) */
    private static final ListCellKey KEY_HIDDEN_PLANUKEY =
            new ListCellKey("HIDDEN_PLANUKEY", new StringCellColumn(), false, false);

    /** lst_RetrievalResultMaintenance(HIDDEN_LASTUPDATE) */
    private static final ListCellKey KEY_HIDDEN_LASTUPDATE =
            new ListCellKey("HIDDEN_LASTUPDATE", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), false, false);

    /** lst_RetrievalResultMaintenance(HIDDEN_LOT) */
    private static final ListCellKey KEY_HIDDEN_LOT =
            new ListCellKey("HIDDEN_LOT", new StringCellColumn(), false, false);

    /** lst_RetrievalResultMaintenance(HIDDEN_CUSTOMER_NAME) */
    private static final ListCellKey KEY_HIDDEN_CUSTOMER_NAME =
            new ListCellKey("HIDDEN_CUSTOMER_NAME", new StringCellColumn(), false, false);

    /** lst_RetrievalResultMaintenance(HIDDEN_AREA_NAME) */
    private static final ListCellKey KEY_HIDDEN_AREA_NAME =
            new ListCellKey("HIDDEN_AREA_NAME", new StringCellColumn(), false, false);

    /** lst_RetrievalResultMaintenance(HIDDEN_REPORT_FLAG) */
    private static final ListCellKey KEY_HIDDEN_REPORT_FLAG =
            new ListCellKey("HIDDEN_REPORT_FLAG", new StringCellColumn(), false, false);

    /** lst_RetrievalResultMaintenance(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_RetrievalResultMaintenance(LST_PLAN_DAY) */
    private static final ListCellKey KEY_LST_PLAN_DAY =
            new ListCellKey("LST_PLAN_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_RetrievalResultMaintenance(LST_ORDER_NO) */
    private static final ListCellKey KEY_LST_ORDER_NO =
            new ListCellKey("LST_ORDER_NO", new StringCellColumn(), true, false);

    /** lst_RetrievalResultMaintenance(LST_RESULT_AREA_NO) */
    private static final ListCellKey KEY_LST_RESULT_AREA_NO =
            new ListCellKey("LST_RESULT_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_RetrievalResultMaintenance(LST_RESULT_LOCATION_NO) */
    private static final ListCellKey KEY_LST_RESULT_LOCATION_NO =
            new ListCellKey("LST_RESULT_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_RetrievalResultMaintenance(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE =
            new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_RetrievalResultMaintenance(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME =
            new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_RetrievalResultMaintenance(LST_RESULT_LOT_NO) */
    private static final ListCellKey KEY_LST_RESULT_LOT_NO =
            new ListCellKey("LST_RESULT_LOT_NO", new StringCellColumn(), true, true);

    /** lst_RetrievalResultMaintenance(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY =
            new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalResultMaintenance(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY =
            new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalResultMaintenance(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY =
            new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalResultMaintenance(LST_RESULT_CASE_QTY) */
    private static final ListCellKey KEY_LST_RESULT_CASE_QTY =
            new ListCellKey("LST_RESULT_CASE_QTY", new NumberCellColumn(0), true, true);

    /** lst_RetrievalResultMaintenance(LST_RESULT_PIECE_QTY) */
    private static final ListCellKey KEY_LST_RESULT_PIECE_QTY =
            new ListCellKey("LST_RESULT_PIECE_QTY", new NumberCellColumn(0), true, true);

    /** lst_RetrievalResultMaintenance(LST_REPORT_FLAG) */
    private static final ListCellKey KEY_LST_REPORT_FLAG =
            new ListCellKey("LST_REPORT_FLAG", new StringCellColumn(), true, false);

    /** lst_RetrievalResultMaintenance keys */
    private static final ListCellKey[] LST_RETRIEVALRESULTMAINTENANCE_KEYS = {
        KEY_HIDDEN_BATCH,
        KEY_HIDDEN_TICKET,
        KEY_HIDDEN_LINE,
        KEY_HIDDEN_BRANCH,
        KEY_HIDDEN_CUSTOMER,
        KEY_HIDDEN_CASE,
        KEY_HIDDEN_PIECE,
        KEY_HIDDEN_PLAN,
        KEY_HIDDEN_JOBNO,
        KEY_HIDDEN_PLANUKEY,
        KEY_HIDDEN_LASTUPDATE,
        KEY_HIDDEN_LOT,
        KEY_HIDDEN_CUSTOMER_NAME,
        KEY_HIDDEN_AREA_NAME,
        KEY_HIDDEN_REPORT_FLAG,
        KEY_LST_SELECT,
        KEY_LST_PLAN_DAY,
        KEY_LST_ORDER_NO,
        KEY_LST_RESULT_AREA_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_RESULT_LOT_NO,
        KEY_LST_ENTERING_QTY,
        KEY_LST_PLAN_CASE_QTY,
        KEY_LST_RESULT_CASE_QTY,
        KEY_LST_REPORT_FLAG,
        KEY_LST_RESULT_LOCATION_NO,
        KEY_LST_ITEM_NAME,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_RESULT_PIECE_QTY,
    };

    // DFKLOOK start
    // 選択チェックボックス(リストセル)
    private static final int LST_CHECK = 1;

    // DFKLOOK end

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_RetrievalArea */
    private WmsAreaPullDownModel _pdm_pul_RetrievalArea;

    /** ListCellModel lst_RetrievalResultMaintenance */
    private ListCellModel _lcm_lst_RetrievalResultMaintenance;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RetrievalResultMntBusiness()
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
        if (eventSource.startsWith("btn_ModifySet_Click"))
        {
            btn_ModifySet_Click_Process(eventSource);
        }
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
    public void btn_ModifySet_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ModifySet_Click_Process(null);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ListClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ListClear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_RetrievalResultMaintenance_Change(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから
        lst_RetrievalResultMaintenance.getMaxRows();

        for (int i = 1; i < lst_RetrievalResultMaintenance.getMaxRows(); i++)
        {
            lst_RetrievalResultMaintenance.setCurrentRow(i);
            if (lst_RetrievalResultMaintenance.getChecked(_lcm_lst_RetrievalResultMaintenance.getColumnIndex(KEY_LST_SELECT)))
            {
                if (WmsFormatter.getInt(lst_RetrievalResultMaintenance.getValue(_lcm_lst_RetrievalResultMaintenance.getColumnIndex(KEY_LST_ENTERING_QTY))) != 0)
                {
                    lst_RetrievalResultMaintenance.setCellReadOnly(
                            _lcm_lst_RetrievalResultMaintenance.getColumnIndex(KEY_LST_RESULT_CASE_QTY), false);
                }
                lst_RetrievalResultMaintenance.setCellReadOnly(
                        _lcm_lst_RetrievalResultMaintenance.getColumnIndex(KEY_LST_RESULT_PIECE_QTY), false);
                lst_RetrievalResultMaintenance.setCellReadOnly(
                        _lcm_lst_RetrievalResultMaintenance.getColumnIndex(KEY_LST_RESULT_LOT_NO), false);
            }
            else
            {
                lst_RetrievalResultMaintenance.setCellReadOnly(
                        _lcm_lst_RetrievalResultMaintenance.getColumnIndex(KEY_LST_RESULT_LOT_NO), true);
                lst_RetrievalResultMaintenance.setCellReadOnly(
                        _lcm_lst_RetrievalResultMaintenance.getColumnIndex(KEY_LST_RESULT_CASE_QTY), true);
                lst_RetrievalResultMaintenance.setCellReadOnly(
                        _lcm_lst_RetrievalResultMaintenance.getColumnIndex(KEY_LST_RESULT_PIECE_QTY), true);

            }
        }
        // DFKLOOK ここまで
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

    // DFKLOOK ここから
    /**
     * リストセルの入力情報が更新対象かどうかチェックします。<BR>
     * <BR>
     * 概要：リストセルのデータが更新対象の場合はTrueを、そうでない場合はfalseを返します。 <BR>
     * <BR>
     * @param line データ内容
     * @return チェック結果（更新対象の場合：True 更新対象でない場合：false）
     * @throws Exception 全ての例外を報告します。
     */
    protected boolean isChangeData(ListCellLine line)
            throws Exception
    {
        // 変更前出庫ケース数
        String orgCaseQty = line.getStringValue(KEY_HIDDEN_CASE);
        // 変更前出庫ピース数
        String orgPieceQty = line.getStringValue(KEY_HIDDEN_PIECE);
        // 変更前ロットNo.
        String orgLotNo = line.getStringValue(KEY_HIDDEN_LOT);

        // 入力テキストがひとつも変更されていない場合処理を行わない。
        // 出庫ケース数
        if (!(orgCaseQty).equals(line.getStringValue(KEY_LST_RESULT_CASE_QTY)))
        {
            return true;
        }
        // 出庫ピース数
        if (!(orgPieceQty).equals(line.getStringValue(KEY_LST_RESULT_PIECE_QTY)))
        {
            return true;
        }
        // ロットNo.
        if (!(orgLotNo).equals(line.getStringValue(KEY_LST_RESULT_LOT_NO)))
        {
            return true;
        }
        // 何も変更されていなかった場合
        return false;
    }

    // DFKLOOK ここまで

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

        // initialize pul_RetrievalArea.
        _pdm_pul_RetrievalArea = new WmsAreaPullDownModel(pul_RetrievalArea, locale, ui);

        // initialize lst_RetrievalResultMaintenance.
        _lcm_lst_RetrievalResultMaintenance =
                new ListCellModel(lst_RetrievalResultMaintenance, LST_RETRIEVALRESULTMAINTENANCE_KEYS, locale);
        _lcm_lst_RetrievalResultMaintenance.setToolTipVisible(KEY_LST_SELECT, true);
        _lcm_lst_RetrievalResultMaintenance.setToolTipVisible(KEY_LST_PLAN_DAY, true);
        _lcm_lst_RetrievalResultMaintenance.setToolTipVisible(KEY_LST_ORDER_NO, true);
        _lcm_lst_RetrievalResultMaintenance.setToolTipVisible(KEY_LST_RESULT_AREA_NO, true);
        _lcm_lst_RetrievalResultMaintenance.setToolTipVisible(KEY_LST_RESULT_LOCATION_NO, true);
        _lcm_lst_RetrievalResultMaintenance.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_RetrievalResultMaintenance.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_RetrievalResultMaintenance.setToolTipVisible(KEY_LST_RESULT_LOT_NO, false);
        _lcm_lst_RetrievalResultMaintenance.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_RetrievalResultMaintenance.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_RetrievalResultMaintenance.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_RetrievalResultMaintenance.setToolTipVisible(KEY_LST_RESULT_CASE_QTY, false);
        _lcm_lst_RetrievalResultMaintenance.setToolTipVisible(KEY_LST_RESULT_PIECE_QTY, false);
        _lcm_lst_RetrievalResultMaintenance.setToolTipVisible(KEY_LST_REPORT_FLAG, true);

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

            // load pul_RetrievalArea.
            _pdm_pul_RetrievalArea.init(conn, AreaType.NOT_MOVING, StationType.ALL, "", true);

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
    private void lst_RetrievalResultMaintenance_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        // DFKLOOK ここから
        line.addToolTip("LBL-W0028", KEY_HIDDEN_BATCH);
        line.addToolTip("LBL-W0145", KEY_HIDDEN_TICKET);
        line.addToolTip("LBL-W0066", KEY_HIDDEN_LINE);
        line.addToolTip("LBL-W0081", KEY_HIDDEN_BRANCH);
        line.addToolTip("LBL-W0114", KEY_HIDDEN_CUSTOMER);
        line.addToolTip("LBL-W0115", KEY_HIDDEN_CUSTOMER_NAME);
        line.addToolTip("LBL-W0117", KEY_HIDDEN_AREA_NAME);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-W0346", KEY_HIDDEN_CASE);
        line.addToolTip("LBL-W0347", KEY_HIDDEN_PIECE);
        // DFKLOOK ここまで

    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_RetrievalPlanDate);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        txt_RetrievalPlanDate.setValue(null);
        txt_BatchNo.setValue(null);
        txt_OrderNo.setValue(null);
        txt_CustomerCode.setValue(null);
        _pdm_pul_RetrievalArea.setSelectedValue(null);
        btn_ModifySet.setEnabled(false);
        btn_ListClear.setEnabled(false);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_RetrievalPlanDate.validate(this, false);
        txt_BatchNo.validate(this, false);
        txt_OrderNo.validate(this, false);
        txt_CustomerCode.validate(this, false);
        pul_RetrievalArea.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalResultMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalResultMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RetrievalResultMntSCHParams inparam = new RetrievalResultMntSCHParams();
            inparam.set(RetrievalResultMntSCHParams.RETRIEVAL_PLAN_DATE, txt_RetrievalPlanDate.getValue());
            inparam.set(RetrievalResultMntSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(RetrievalResultMntSCHParams.ORDER_NO, txt_OrderNo.getValue());
            inparam.set(RetrievalResultMntSCHParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
            inparam.set(RetrievalResultMntSCHParams.RETRIEVAL_AREA, _pdm_pul_RetrievalArea.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RetrievalResultMaintenance.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RetrievalResultMaintenance.getNewLine();
                line.setValue(KEY_LST_SELECT, outparam.get(RetrievalResultMntSCHParams.SELECT));
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(RetrievalResultMntSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(RetrievalResultMntSCHParams.ORDER_NO));
                line.setValue(KEY_LST_RESULT_AREA_NO, outparam.get(RetrievalResultMntSCHParams.RESULT_AREA_NO));
                line.setValue(KEY_LST_RESULT_LOCATION_NO, outparam.get(RetrievalResultMntSCHParams.RESULT_LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(RetrievalResultMntSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(RetrievalResultMntSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_RESULT_LOT_NO, outparam.get(RetrievalResultMntSCHParams.RESULT_LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(RetrievalResultMntSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(RetrievalResultMntSCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(RetrievalResultMntSCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_RESULT_CASE_QTY, outparam.get(RetrievalResultMntSCHParams.RESULT_CASE_QTY));
                line.setValue(KEY_LST_RESULT_PIECE_QTY, outparam.get(RetrievalResultMntSCHParams.RESULT_PIECE_QTY));
                line.setValue(KEY_LST_REPORT_FLAG, outparam.get(RetrievalResultMntSCHParams.REPORT_FLAG));
                viewState.setObject(ViewStateKeys.RETRIEVAL_PLAN_DATE, txt_RetrievalPlanDate.getValue());
                viewState.setObject(ViewStateKeys.BATCH_NO, txt_BatchNo.getValue());
                viewState.setObject(ViewStateKeys.ORDER_NO, txt_OrderNo.getValue());
                viewState.setObject(ViewStateKeys.CUSTOMER_CODE, txt_CustomerCode.getValue());
                viewState.setObject(ViewStateKeys.RETRIEVAL_AREA, _pdm_pul_RetrievalArea.getSelectedValue());
                line.setValue(KEY_HIDDEN_JOBNO, outparam.get(RetrievalResultMntSCHParams.JOB_NO));
                line.setValue(KEY_HIDDEN_PLANUKEY, outparam.get(RetrievalResultMntSCHParams.PLANUKEY));
                line.setValue(KEY_HIDDEN_LASTUPDATE, outparam.get(RetrievalResultMntSCHParams.LASTUPDATE));
                line.setValue(KEY_HIDDEN_PLAN, outparam.get(RetrievalResultMntSCHParams.PLAN));
                line.setValue(KEY_HIDDEN_BATCH, outparam.get(RetrievalResultMntSCHParams.BATCH_NO));
                line.setValue(KEY_HIDDEN_TICKET, outparam.get(RetrievalResultMntSCHParams.TICKET));
                line.setValue(KEY_HIDDEN_LINE, outparam.get(RetrievalResultMntSCHParams.LINE));
                line.setValue(KEY_HIDDEN_CUSTOMER, outparam.get(RetrievalResultMntSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_HIDDEN_LOT, outparam.get(RetrievalResultMntSCHParams.LOT));
                line.setValue(KEY_HIDDEN_CUSTOMER_NAME, outparam.get(RetrievalResultMntSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_HIDDEN_CASE, outparam.get(RetrievalResultMntSCHParams.CASE));
                line.setValue(KEY_HIDDEN_PIECE, outparam.get(RetrievalResultMntSCHParams.PIECE));
                line.setValue(KEY_HIDDEN_AREA_NAME, outparam.get(RetrievalResultMntSCHParams.AREA_NAME));
                line.setValue(KEY_HIDDEN_BRANCH, outparam.get(RetrievalResultMntSCHParams.BRANCH));
                line.setValue(KEY_HIDDEN_REPORT_FLAG, outparam.get(RetrievalResultMntSCHParams.HIDDEN_FLAG));
                lst_RetrievalResultMaintenance_SetLineToolTip(line);
                _lcm_lst_RetrievalResultMaintenance.add(line);
                // DFKLOOK ここから
                // 実績報告.未報告以外(報告済・搬送中)の行はチェック不能に
                if (!SystemDefine.REPORT_FLAG_NOT_REPORT.equals(outparam.get(RetrievalResultMntSCHParams.HIDDEN_FLAG)))
                {
                    lst_RetrievalResultMaintenance.setCellEnabled(1, false);
                }
                // DFKLOOK ここまで
            }

            // DFKLOOK ここから
            if (1 <= _lcm_lst_RetrievalResultMaintenance.size())
            {
                // clear.
                btn_ModifySet.setEnabled(true);
                btn_ListClear.setEnabled(true);
            }
            // DFKLOOK ここまで


            for (int i = 1; i <= _lcm_lst_RetrievalResultMaintenance.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_RetrievalResultMaintenance.get(i);
                lst_RetrievalResultMaintenance.setCurrentRow(i);
                lst_RetrievalResultMaintenance.setCellReadOnly(
                        _lcm_lst_RetrievalResultMaintenance.getColumnIndex(KEY_LST_RESULT_LOT_NO), true);
                lst_RetrievalResultMaintenance.setCellReadOnly(
                        _lcm_lst_RetrievalResultMaintenance.getColumnIndex(KEY_LST_RESULT_CASE_QTY), true);
                lst_RetrievalResultMaintenance.setCellReadOnly(
                        _lcm_lst_RetrievalResultMaintenance.getColumnIndex(KEY_LST_RESULT_PIECE_QTY), true);
                lst_RetrievalResultMaintenance_SetLineToolTip(clearLine);
                _lcm_lst_RetrievalResultMaintenance.set(i, clearLine);
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
     *
     * @throws Exception
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_RetrievalPlanDate.setValue(null);
        txt_BatchNo.setValue(null);
        txt_OrderNo.setValue(null);
        txt_CustomerCode.setValue(null);
        _pdm_pul_RetrievalArea.setSelectedValue(null);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_ModifySet_Click_Process(String eventSource)
            throws Exception
    {

        // DFKLOOK:ここから
        //データ選択有無チェック
        boolean checkDate = false;

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalResultMntSCH sch = null;

        // set input parameters.
        List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
        for (int i = 1; i <= _lcm_lst_RetrievalResultMaintenance.size(); i++)
        {
            // exclusion unmodified row.
            ListCellLine line = _lcm_lst_RetrievalResultMaintenance.get(i);

            // DFKLOOK ここから
            if (line.getStringValue(KEY_LST_SELECT).equals("true"))
            {
                // DFKLOOK ここまで
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }
                lst_RetrievalResultMaintenance.setCurrentRow(i);
                if (!lst_RetrievalResultMaintenance.getChecked(LST_CHECK))
                {
                    continue;
                }

                lst_RetrievalResultMaintenance.validate(line.getIndex(KEY_LST_RESULT_LOT_NO), false);

                // DFKLOOK ここから
                checkDate = true;
                //データが変更されている場合
                if (isChangeData(line))
                {
                    RetrievalResultMntSCHParams lineparam = new RetrievalResultMntSCHParams();
                    lineparam.setProcessFlag(ProcessFlag.UPDATE);
                    lineparam.setRowIndex(i);
                    lineparam.set(RetrievalResultMntSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                    lineparam.set(RetrievalResultMntSCHParams.PLAN_DAY, line.getValue(KEY_LST_PLAN_DAY));
                    lineparam.set(RetrievalResultMntSCHParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                    lineparam.set(RetrievalResultMntSCHParams.RESULT_AREA_NO, line.getValue(KEY_LST_RESULT_AREA_NO));
                    lineparam.set(RetrievalResultMntSCHParams.RESULT_LOCATION_NO,
                            line.getValue(KEY_LST_RESULT_LOCATION_NO));
                    lineparam.set(RetrievalResultMntSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                    lineparam.set(RetrievalResultMntSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                    lineparam.set(RetrievalResultMntSCHParams.RESULT_LOT_NO, line.getValue(KEY_LST_RESULT_LOT_NO));
                    lineparam.set(RetrievalResultMntSCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                    lineparam.set(RetrievalResultMntSCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                    lineparam.set(RetrievalResultMntSCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                    lineparam.set(RetrievalResultMntSCHParams.RESULT_CASE_QTY, line.getValue(KEY_LST_RESULT_CASE_QTY));
                    lineparam.set(RetrievalResultMntSCHParams.RESULT_PIECE_QTY, line.getValue(KEY_LST_RESULT_PIECE_QTY));
                    lineparam.set(RetrievalResultMntSCHParams.REPORT_FLAG, line.getValue(KEY_LST_REPORT_FLAG));
                    lineparam.set(RetrievalResultMntSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.BATCH_NO));
                    lineparam.set(RetrievalResultMntSCHParams.CUSTOMER_CODE,
                            viewState.getObject(ViewStateKeys.CUSTOMER_CODE));
                    lineparam.set(RetrievalResultMntSCHParams.ORDER_NO, viewState.getObject(ViewStateKeys.ORDER_NO));
                    lineparam.set(RetrievalResultMntSCHParams.RETRIEVAL_AREA,
                            viewState.getObject(ViewStateKeys.RETRIEVAL_AREA));
                    lineparam.set(RetrievalResultMntSCHParams.RETRIEVAL_PLAN_DATE,
                            viewState.getObject(ViewStateKeys.RETRIEVAL_PLAN_DATE));
                    lineparam.set(RetrievalResultMntSCHParams.BATCH_NO, line.getValue(KEY_HIDDEN_BATCH));
                    lineparam.set(RetrievalResultMntSCHParams.TICKET, line.getValue(KEY_HIDDEN_TICKET));
                    lineparam.set(RetrievalResultMntSCHParams.LINE, line.getValue(KEY_HIDDEN_LINE));
                    lineparam.set(RetrievalResultMntSCHParams.BRANCH, line.getValue(KEY_HIDDEN_BRANCH));
                    lineparam.set(RetrievalResultMntSCHParams.CUSTOMER_CODE, line.getValue(KEY_HIDDEN_CUSTOMER));
                    lineparam.set(RetrievalResultMntSCHParams.PLAN, line.getValue(KEY_HIDDEN_PLAN));
                    lineparam.set(RetrievalResultMntSCHParams.JOB_NO, line.getValue(KEY_HIDDEN_JOBNO));
                    lineparam.set(RetrievalResultMntSCHParams.PLANUKEY, line.getValue(KEY_HIDDEN_PLANUKEY));
                    lineparam.set(RetrievalResultMntSCHParams.LASTUPDATE, line.getValue(KEY_HIDDEN_LASTUPDATE));
                    lineparam.set(RetrievalResultMntSCHParams.CUSTOMER_NAME, line.getValue(KEY_HIDDEN_CUSTOMER_NAME));
                    lineparam.set(RetrievalResultMntSCHParams.AREA_NAME, line.getValue(KEY_HIDDEN_AREA_NAME));
                    inparamList.add(lineparam);
                }
                // DFKLOOK ここまで
            }
        }

        // DFKLOOK ここから
        if (!checkDate)
        {
            // 6003001=データを選択してください。
            message.setMsgResourceKey("6003001");
            return;
        }

        // 要素数が0なら"更新対象データがありません。"処理を終了
        if (inparamList.size() == 0)
        {
            //6003013="修正対象データがありませんでした"
            message.setMsgResourceKey("6003013");
            return;
        }
        // DFKLOOK ここまで

        ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
        inparamList.toArray(inparams);

        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalResultMntSCH(conn, this.getClass(), locale, ui);


            // DFKLOOK:ここから修正
            if (StringUtil.isBlank(eventSource))
            {
                if (!sch.check(inparams))
                {
                    message.setMsgResourceKey(sch.getMessage());

                    // reset editing row or highlighting error row.
                    _lcm_lst_RetrievalResultMaintenance.resetEditRow();
                    _lcm_lst_RetrievalResultMaintenance.resetHighlight();
                    _lcm_lst_RetrievalResultMaintenance.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                    return;
                }
                // 修正登録しますか?
                this.setConfirm("MSG-W0013", false, true);
                viewState.setString(_KEY_CONFIRMSOURCE, "btn_ModifySet_Click");
                return;
            }
            // DFKLOOK:ここまで修正

            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_RetrievalResultMaintenance.resetEditRow();
                _lcm_lst_RetrievalResultMaintenance.resetHighlight();
                _lcm_lst_RetrievalResultMaintenance.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_RetrievalResultMaintenance.size(); i++)
            {
                ListCellLine line = _lcm_lst_RetrievalResultMaintenance.get(i);
                lst_RetrievalResultMaintenance.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                lst_RetrievalResultMaintenance.setCurrentRow(i);
                if (!lst_RetrievalResultMaintenance.getChecked(LST_CHECK))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_PLAN_DAY), "");
                part11List.add(line.getViewString(KEY_LST_ORDER_NO), "");
                part11List.add(line.getViewString(KEY_LST_RESULT_AREA_NO), "");
                part11List.add(line.getViewString(KEY_LST_RESULT_LOCATION_NO), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_RESULT_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_RESULT_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_RESULT_PIECE_QTY), "");
                part11List.add(line.getViewString(KEY_HIDDEN_REPORT_FLAG), "");
                part11List.add(line.getStringValue(_lcm_lst_RetrievalResultMaintenance.getColumnKey(
                        _lcm_lst_RetrievalResultMaintenance.getColumnIndex(KEY_LST_RESULT_CASE_QTY)).getShadowKey()),
                        "");
                part11List.add(line.getStringValue(_lcm_lst_RetrievalResultMaintenance.getColumnKey(
                        _lcm_lst_RetrievalResultMaintenance.getColumnIndex(KEY_LST_RESULT_PIECE_QTY)).getShadowKey()),
                        "");
                part11List.add(line.getStringValue(_lcm_lst_RetrievalResultMaintenance.getColumnKey(
                        _lcm_lst_RetrievalResultMaintenance.getColumnIndex(KEY_LST_RESULT_LOT_NO)).getShadowKey()), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_MODIFY),
                        part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_RetrievalResultMaintenance.resetEditRow();
            _lcm_lst_RetrievalResultMaintenance.resetHighlight();

            // set input parameters.
            RetrievalResultMntSCHParams inparam = new RetrievalResultMntSCHParams();
            inparam.set(RetrievalResultMntSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.BATCH_NO));
            inparam.set(RetrievalResultMntSCHParams.CUSTOMER_CODE, viewState.getObject(ViewStateKeys.CUSTOMER_CODE));
            inparam.set(RetrievalResultMntSCHParams.ORDER_NO, viewState.getObject(ViewStateKeys.ORDER_NO));
            inparam.set(RetrievalResultMntSCHParams.RETRIEVAL_AREA, viewState.getObject(ViewStateKeys.RETRIEVAL_AREA));
            inparam.set(RetrievalResultMntSCHParams.RETRIEVAL_PLAN_DATE,
                    viewState.getObject(ViewStateKeys.RETRIEVAL_PLAN_DATE));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_RetrievalResultMaintenance.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RetrievalResultMaintenance.getNewLine();
                line.setValue(KEY_LST_SELECT, outparam.get(RetrievalResultMntSCHParams.SELECT));
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(RetrievalResultMntSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(RetrievalResultMntSCHParams.ORDER_NO));
                line.setValue(KEY_LST_RESULT_AREA_NO, outparam.get(RetrievalResultMntSCHParams.RESULT_AREA_NO));
                line.setValue(KEY_LST_RESULT_LOCATION_NO, outparam.get(RetrievalResultMntSCHParams.RESULT_LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(RetrievalResultMntSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(RetrievalResultMntSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_RESULT_LOT_NO, outparam.get(RetrievalResultMntSCHParams.RESULT_LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(RetrievalResultMntSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(RetrievalResultMntSCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(RetrievalResultMntSCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_RESULT_CASE_QTY, outparam.get(RetrievalResultMntSCHParams.RESULT_CASE_QTY));
                line.setValue(KEY_LST_RESULT_PIECE_QTY, outparam.get(RetrievalResultMntSCHParams.RESULT_PIECE_QTY));
                line.setValue(KEY_LST_REPORT_FLAG, outparam.get(RetrievalResultMntSCHParams.REPORT_FLAG));
                viewState.setObject(ViewStateKeys.RETRIEVAL_PLAN_DATE, txt_RetrievalPlanDate.getValue());
                viewState.setObject(ViewStateKeys.BATCH_NO, txt_BatchNo.getValue());
                viewState.setObject(ViewStateKeys.ORDER_NO, txt_OrderNo.getValue());
                viewState.setObject(ViewStateKeys.CUSTOMER_CODE, txt_CustomerCode.getValue());
                viewState.setObject(ViewStateKeys.RETRIEVAL_AREA, _pdm_pul_RetrievalArea.getSelectedValue());
                line.setValue(KEY_HIDDEN_JOBNO, outparam.get(RetrievalResultMntSCHParams.JOB_NO));
                line.setValue(KEY_HIDDEN_PLANUKEY, outparam.get(RetrievalResultMntSCHParams.PLANUKEY));
                line.setValue(KEY_HIDDEN_LASTUPDATE, outparam.get(RetrievalResultMntSCHParams.LASTUPDATE));
                line.setValue(KEY_HIDDEN_PLAN, outparam.get(RetrievalResultMntSCHParams.PLAN));
                line.setValue(KEY_HIDDEN_BATCH, outparam.get(RetrievalResultMntSCHParams.BATCH_NO));
                line.setValue(KEY_HIDDEN_TICKET, outparam.get(RetrievalResultMntSCHParams.TICKET));
                line.setValue(KEY_HIDDEN_LINE, outparam.get(RetrievalResultMntSCHParams.LINE));
                line.setValue(KEY_HIDDEN_CUSTOMER, outparam.get(RetrievalResultMntSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_HIDDEN_LOT, outparam.get(RetrievalResultMntSCHParams.LOT));
                line.setValue(KEY_HIDDEN_CUSTOMER_NAME, outparam.get(RetrievalResultMntSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_HIDDEN_CASE, outparam.get(RetrievalResultMntSCHParams.CASE));
                line.setValue(KEY_HIDDEN_PIECE, outparam.get(RetrievalResultMntSCHParams.PIECE));
                line.setValue(KEY_HIDDEN_AREA_NAME, outparam.get(RetrievalResultMntSCHParams.AREA_NAME));
                line.setValue(KEY_HIDDEN_BRANCH, outparam.get(RetrievalResultMntSCHParams.BRANCH));
                line.setValue(KEY_HIDDEN_REPORT_FLAG, outparam.get(RetrievalResultMntSCHParams.HIDDEN_FLAG));
                lst_RetrievalResultMaintenance_SetLineToolTip(line);
                _lcm_lst_RetrievalResultMaintenance.add(line);

                // DFKLOOK ここから
                //初期状態は入力不能
                lst_RetrievalResultMaintenance.setCellReadOnly(
                        _lcm_lst_RetrievalResultMaintenance.getColumnIndex(KEY_LST_RESULT_LOT_NO), true);
                lst_RetrievalResultMaintenance.setCellReadOnly(
                        _lcm_lst_RetrievalResultMaintenance.getColumnIndex(KEY_LST_RESULT_CASE_QTY), true);
                lst_RetrievalResultMaintenance.setCellReadOnly(
                        _lcm_lst_RetrievalResultMaintenance.getColumnIndex(KEY_LST_RESULT_PIECE_QTY), true);

                // 実績報告.未報告以外(報告済・予約)の行はチェック不能に
                if (!SystemDefine.REPORT_FLAG_NOT_REPORT.equals(outparam.get(RetrievalResultMntSCHParams.HIDDEN_FLAG)))
                {
                    lst_RetrievalResultMaintenance.setCellEnabled(1, false);
                }
                // DFKLOOK ここまで
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
    private void btn_ListClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_RetrievalResultMaintenance.clear();
        btn_ModifySet.setEnabled(false);
        btn_ListClear.setEnabled(false);

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
