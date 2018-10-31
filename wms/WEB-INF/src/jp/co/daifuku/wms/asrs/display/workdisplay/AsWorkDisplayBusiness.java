// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.asrs.display.workdisplay;

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
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;
import jp.co.daifuku.wms.asrs.schedule.AsWorkDisplaySCH;
import jp.co.daifuku.wms.asrs.schedule.AsWorkDisplaySCHParams;
import jp.co.daifuku.wms.asrs.schedule.AsrsInParameter;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.StationController;
import jp.co.daifuku.wms.base.controller.PulldownController.Distribution;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;


/**
 * AS/RS 作業表示の画面処理を行います。
 * 
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:08:43 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class AsWorkDisplayBusiness
        extends AsWorkDisplay
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
    
    /** lst_AsrsWorkDisplay(HIDDEN_TICKET_NO) */
    private static final ListCellKey KEY_HIDDEN_TICKET_NO = new ListCellKey("HIDDEN_TICKET_NO", new StringCellColumn(), false, false);

    /** lst_AsrsWorkDisplay(HIDDEN_LINE_NO) */
    private static final ListCellKey KEY_HIDDEN_LINE_NO = new ListCellKey("HIDDEN_LINE_NO", new NumberCellColumn(0), false, false);

    /** lst_AsrsWorkDisplay(HIDDEN_BRANCH_NO) */
    private static final ListCellKey KEY_HIDDEN_BRANCH_NO = new ListCellKey("HIDDEN_BRANCH_NO", new NumberCellColumn(0), false, false);

    /** lst_AsrsWorkDisplay(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_AsrsWorkDisplay(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_AsrsWorkDisplay(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_AsrsWorkDisplay(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_AsrsWorkDisplay(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_AsrsWorkDisplay(LST_STOCK_CASE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_CASE_QTY = new ListCellKey("LST_STOCK_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_AsrsWorkDisplay(LST_STOCK_PIECE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_PIECE_QTY = new ListCellKey("LST_STOCK_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_AsrsWorkDisplay(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY = new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_AsrsWorkDisplay(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY = new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_AsrsWorkDisplay(LST_ORDER_NO) */
    private static final ListCellKey KEY_LST_ORDER_NO = new ListCellKey("LST_ORDER_NO", new StringCellColumn(), true, false);

    /** lst_AsrsWorkDisplay(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE = new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_AsrsWorkDisplay(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME = new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_AsrsWorkDisplay keys */
    private static final ListCellKey[] LST_ASRSWORKDISPLAY_KEYS = {
        KEY_HIDDEN_TICKET_NO,
        KEY_HIDDEN_LINE_NO,
        KEY_HIDDEN_BRANCH_NO,
        KEY_LST_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT_NO,
        KEY_LST_ENTERING_QTY,
        KEY_LST_STOCK_CASE_QTY,
        KEY_LST_PLAN_CASE_QTY,
        KEY_LST_ORDER_NO,
        KEY_LST_CUSTOMER_CODE,
        KEY_LST_ITEM_NAME,
        KEY_LST_STOCK_PIECE_QTY,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_CUSTOMER_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** RadioButtonGroupModel ProgressDisplay */
    private RadioButtonGroup _grp_ProgressDisplay;

    /** PullDownModel pul_Station */
    private WmsStationPullDownModel _pdm_pul_Station;

    /** ListCellModel lst_AsrsWorkDisplay */
    private ListCellModel _lcm_lst_AsrsWorkDisplay;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsWorkDisplayBusiness()
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
    public void page_ClientPull(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから修正
        page_ClientPull_Process();
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        // DFKLOOK start
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
        if (eventSource.startsWith("btn_Complete_Click"))
        {
            btn_Complete_Click_Process(eventSource);
        }
        if (eventSource.startsWith("btn_PickComplete_Click"))
        {
            btn_PickComplete_Click_Process(eventSource);
        }
        // DFKLOOK end
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Auto_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから修正
        rdo_Auto.setChecked(true);
        setRegularTransmission();
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Manual_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから修正
        rdo_Manual.setChecked(true);
        setRegularTransmission();
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ReDisplayFunc_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ReDisplayFunc_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Complete_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Complete_Click_Process(null);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PickComplete_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PickComplete_Click_Process(null);
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

    // DFKLOOK ここから修正
    // 定期送信時のメソッド追加
    /**
     * 定期送信時に呼び出されます。
     * @throws Exception 全ての例外を報告します。
     */
    public void page_ClientPull_Process()
            throws Exception
    {
        pul_Station.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsWorkDisplaySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsWorkDisplaySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsWorkDisplaySCHParams inparam = new AsWorkDisplaySCHParams();
            inparam.set(AsWorkDisplaySCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            if (outparams.size() == 0 || outparams == null)
            {
                // clear.
                // clear.
                txt_WorkNo.setValue(null);
                txt_Location.setValue(null);
                txt_WorkKind.setValue(null);
                txt_InstructDetail.setValue(null);
                _lcm_lst_AsrsWorkDisplay.clear();
                //完了ボタンを押下不可にします。
                btn_Complete.setEnabled(false);

                //払出完了ボタンを押下不可にします。
                btn_PickComplete.setEnabled(false);
                return;
            }

            // 条件分岐追加
            Params param = outparams.get(0);
            txt_WorkNo.setValue(param.get(AsWorkDisplaySCHParams.WORK_NO));
            txt_WorkKind.setValue(param.get(AsWorkDisplaySCHParams.WORK_TYPE_NAME));
            txt_InstructDetail.setValue(param.get(AsWorkDisplaySCHParams.RETRIEVAL_DETAIL_NAME));

            if (!StringUtil.isBlank(param.getString(AsWorkDisplaySCHParams.LOCATION_NO)))
            {
                AreaController areaControl = new AreaController(conn, getClass());
                String location = areaControl.toParamLocation(param.getString(AsWorkDisplaySCHParams.LOCATION_NO));
                txt_Location.setText(areaControl.toDispLocation(param.getString(AsWorkDisplaySCHParams.AREA_NO),
                        location));
            }
            else
            {
                // 棚No.が空の場合、""をセット
                txt_Location.setText("");
            }

            // output display.
            _lcm_lst_AsrsWorkDisplay.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_AsrsWorkDisplay.getNewLine();
                viewState.setObject(ViewStateKeys.AREA_NO, outparam.get(AsWorkDisplaySCHParams.AREA_NO));
                viewState.setObject(ViewStateKeys.CARRY_FLAG, outparam.get(AsWorkDisplaySCHParams.CARRY_FLAG));
                viewState.setObject(ViewStateKeys.CARRY_KEY, outparam.get(AsWorkDisplaySCHParams.CARRY_KEY));
                viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE,
                        outparam.get(AsWorkDisplaySCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.RETRIEVAL_DETAIL,
                        outparam.get(AsWorkDisplaySCHParams.RETRIEVAL_DETAIL));
                viewState.setObject(ViewStateKeys.STATION_NO, outparam.get(AsWorkDisplaySCHParams.STATION_NO));
                viewState.setObject(ViewStateKeys.WAREHOUSE_NO, outparam.get(AsWorkDisplaySCHParams.WAREHOUSE_NO));
                viewState.setObject(ViewStateKeys.WORK_DISPLAY_OPERATE,
                        outparam.get(AsWorkDisplaySCHParams.WORK_DISPLAY_OPERATE));
                viewState.setObject(ViewStateKeys.WORK_TYPE, outparam.get(AsWorkDisplaySCHParams.WORK_TYPE));

                // 条件分岐追加
                if (SystemDefine.JOB_TYPE_STORAGE.equals(outparam.get(AsWorkDisplaySCHParams.JOB_TYPE))
                        || SystemDefine.JOB_TYPE_RETRIEVAL.equals(outparam.get(AsWorkDisplaySCHParams.JOB_TYPE)))
                {
                    line.setValue(KEY_HIDDEN_TICKET_NO, outparam.get(AsWorkDisplaySCHParams.TICKET_NO));
                    line.setValue(KEY_HIDDEN_LINE_NO, outparam.get(AsWorkDisplaySCHParams.LINE_NO));
                    line.setValue(KEY_HIDDEN_BRANCH_NO, outparam.get(AsWorkDisplaySCHParams.BRANCH_NO));
                }
                else
                {
                    line.setValue(KEY_HIDDEN_TICKET_NO, "");
                    line.setValue(KEY_HIDDEN_LINE_NO, "");
                    line.setValue(KEY_HIDDEN_BRANCH_NO, "");
                }

                line.setValue(KEY_LST_NO, outparam.get(AsWorkDisplaySCHParams.NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(AsWorkDisplaySCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(AsWorkDisplaySCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(AsWorkDisplaySCHParams.LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(AsWorkDisplaySCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(AsWorkDisplaySCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(AsWorkDisplaySCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(AsWorkDisplaySCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(AsWorkDisplaySCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(AsWorkDisplaySCHParams.ORDER_NO));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(AsWorkDisplaySCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(AsWorkDisplaySCHParams.CUSTOMER_NAME));
                lst_AsrsWorkDisplay_SetLineToolTip(line);
                _lcm_lst_AsrsWorkDisplay.add(line);
            }

            // ボタン制御追加
            // ステーションが作業表示あり、完了ボタンありの場合、ボタンを有効にする
            if (SystemDefine.OPERATION_DISPLAY_INSTRUCTION.equals(param.getString(AsWorkDisplaySCHParams.WORK_DISPLAY_OPERATE)))
            {
                // 搬送区分により、ボタンの使用可否を決定する
                // 搬送区分が入庫の場合、払出不要
                String carrykind = param.getString(AsWorkDisplaySCHParams.CARRY_FLAG);
                if (SystemDefine.CARRY_FLAG_STORAGE.equals(carrykind))
                {
                    //完了ボタンを押下可能にします。
                    btn_Complete.setEnabled(true);
                    //払出完了ボタンを押下不可にします。
                    btn_PickComplete.setEnabled(false);
                }
                // 搬送区分が出庫の場合、払出可能
                else if (SystemDefine.CARRY_FLAG_RETRIEVAL.equals(carrykind))
                {
                    //完了ボタンを押下可能にします。
                    btn_Complete.setEnabled(true);
                    // 払出し可能なステーションで、ユニット出庫以外の場合
                    // 払出し完了ボタンを押下可能とします。それ以外は、押下不可。
                    if (param.getBoolean(AsWorkDisplaySCHParams.CANREMOVE)
                            && !SystemDefine.RETRIEVAL_DETAIL_UNIT.equals(param.getString(AsWorkDisplaySCHParams.RETRIEVAL_DETAIL)))
                    {
                        btn_PickComplete.setEnabled(true);
                    }
                    else
                    {
                        btn_PickComplete.setEnabled(false);
                    }
                }
                // 搬送区分が直行の場合、
                else if (SystemDefine.CARRY_FLAG_DIRECT_TRAVEL.equals(carrykind))
                {
                    //完了ボタンを押下可能にします。
                    btn_Complete.setEnabled(true);
                    //払出完了ボタンを押下不可にします。
                    btn_PickComplete.setEnabled(false);
                }
            }
            else
            {

                //完了ボタンを押下不可にします。
                btn_Complete.setEnabled(false);

                //払出完了ボタンを押下不可にします。
                btn_PickComplete.setEnabled(false);
            }

            // clear.
            rdo_Auto.setChecked(true);
            txt_WorkNo.setReadOnly(true);
            txt_Location.setReadOnly(true);
            txt_WorkKind.setReadOnly(true);
            txt_InstructDetail.setReadOnly(true);

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

    // DFKLOOK ここまで修正

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
    // 定期送信フラグ切り替えメソッド追加
    /**
     * 自動更新か手動更新かを切り替えます。
     */
    protected void setRegularTransmission()
    {
        // 定期送信フラグの切り替えを行います。
        if (rdo_Auto.getChecked())
        {
            setRegularTransmission(true);
        }
        else if (rdo_Manual.getChecked())
        {
            setRegularTransmission(false);
        }
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

        // initialize ProgressDisplay.
        _grp_ProgressDisplay = new RadioButtonGroup(new RadioButton[]{rdo_Auto, rdo_Manual}, locale);

        // initialize pul_Station.
        _pdm_pul_Station = new WmsStationPullDownModel(pul_Station, locale, ui);

        // initialize lst_AsrsWorkDisplay.
        _lcm_lst_AsrsWorkDisplay = new ListCellModel(lst_AsrsWorkDisplay, LST_ASRSWORKDISPLAY_KEYS, locale);
        _lcm_lst_AsrsWorkDisplay.setToolTipVisible(KEY_LST_NO, true);
        _lcm_lst_AsrsWorkDisplay.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_AsrsWorkDisplay.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_AsrsWorkDisplay.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_AsrsWorkDisplay.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_AsrsWorkDisplay.setToolTipVisible(KEY_LST_STOCK_CASE_QTY, true);
        _lcm_lst_AsrsWorkDisplay.setToolTipVisible(KEY_LST_STOCK_PIECE_QTY, true);
        _lcm_lst_AsrsWorkDisplay.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_AsrsWorkDisplay.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_AsrsWorkDisplay.setToolTipVisible(KEY_LST_ORDER_NO, true);
        _lcm_lst_AsrsWorkDisplay.setToolTipVisible(KEY_LST_CUSTOMER_CODE, true);
        _lcm_lst_AsrsWorkDisplay.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);

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

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.OPERATION_DISPLAY, Distribution.UNUSE);

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
    private void lst_AsrsWorkDisplay_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);

        //DFKLOOK:ここから修正
        line.addToolTip("LBL-W0145", KEY_HIDDEN_TICKET_NO);
        line.addToolTip("LBL-W0066", KEY_HIDDEN_LINE_NO);
        line.addToolTip("LBL-W0081", KEY_HIDDEN_BRANCH_NO);
        //DFKLOOK:ここまで修正

        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME);
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
        if (rdo_Manual.getChecked())
        {
            setFocus(rdo_Manual);
        }
        else
        {
            setFocus(rdo_Auto);
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
    	//DFKLOOK start
        rdo_Auto.setChecked(true);
        //完了ボタンを押下不可にします。
        btn_Complete.setEnabled(false);
        //払出完了ボタンを押下不可にします。
        btn_PickComplete.setEnabled(false);
        //DFKLOOK end
    	
        // DFKLOOK ここから修正
        pul_Station.setSelectedIndex(0);
        // DFKLOOK ここまで修正
    	pul_Station.validate(this, true);
    	
    	// get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsWorkDisplaySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsWorkDisplaySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsWorkDisplaySCHParams inparam = new AsWorkDisplaySCHParams();
            inparam.set(AsWorkDisplaySCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK ここから修正
            txt_WorkNo.setReadOnly(true);
            txt_Location.setReadOnly(true);
            txt_WorkKind.setReadOnly(true);
            txt_InstructDetail.setReadOnly(true);
            if (outparams.size() == 0 || outparams == null)
            {
                return;
            }

            // 条件分岐追加
            Params param = outparams.get(0);
            txt_WorkNo.setValue(param.get(AsWorkDisplaySCHParams.WORK_NO));
            txt_WorkKind.setValue(param.get(AsWorkDisplaySCHParams.WORK_TYPE_NAME));
            txt_InstructDetail.setValue(param.get(AsWorkDisplaySCHParams.RETRIEVAL_DETAIL_NAME));


            if (!StringUtil.isBlank(param.getString(AsWorkDisplaySCHParams.LOCATION_NO)))
            {
                AreaController areaControl = new AreaController(conn, getClass());
                String location = areaControl.toParamLocation(param.getString(AsWorkDisplaySCHParams.LOCATION_NO));
                txt_Location.setText(areaControl.toDispLocation(param.getString(AsWorkDisplaySCHParams.AREA_NO),
                        location));
            }
            else
            {
                // 棚No.が空の場合、""をセット
                txt_Location.setText("");
            }

            // DFKLOOK ここまで修正

            // output display.
            _lcm_lst_AsrsWorkDisplay.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_AsrsWorkDisplay.getNewLine();
                viewState.setObject(ViewStateKeys.AREA_NO, outparam.get(AsWorkDisplaySCHParams.AREA_NO));
                viewState.setObject(ViewStateKeys.CARRY_FLAG, outparam.get(AsWorkDisplaySCHParams.CARRY_FLAG));
                viewState.setObject(ViewStateKeys.CARRY_KEY, outparam.get(AsWorkDisplaySCHParams.CARRY_KEY));
                viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, outparam.get(AsWorkDisplaySCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.RETRIEVAL_DETAIL, outparam.get(AsWorkDisplaySCHParams.RETRIEVAL_DETAIL));
                viewState.setObject(ViewStateKeys.STATION_NO, outparam.get(AsWorkDisplaySCHParams.STATION_NO));
                viewState.setObject(ViewStateKeys.WAREHOUSE_NO, outparam.get(AsWorkDisplaySCHParams.WAREHOUSE_NO));
                viewState.setObject(ViewStateKeys.WORK_DISPLAY_OPERATE, outparam.get(AsWorkDisplaySCHParams.WORK_DISPLAY_OPERATE));
                viewState.setObject(ViewStateKeys.WORK_TYPE, outparam.get(AsWorkDisplaySCHParams.WORK_TYPE));

                // DFKLOOK ここから修正
                // 条件分岐追加
                if (SystemDefine.JOB_TYPE_STORAGE.equals(outparam.get(AsWorkDisplaySCHParams.JOB_TYPE))
                        || SystemDefine.JOB_TYPE_RETRIEVAL.equals(outparam.get(AsWorkDisplaySCHParams.JOB_TYPE)))
                {
                    line.setValue(KEY_HIDDEN_TICKET_NO, outparam.get(AsWorkDisplaySCHParams.TICKET_NO));
                    line.setValue(KEY_HIDDEN_LINE_NO, outparam.get(AsWorkDisplaySCHParams.LINE_NO));
                    line.setValue(KEY_HIDDEN_BRANCH_NO, outparam.get(AsWorkDisplaySCHParams.BRANCH_NO));
                }
                else
                {
                    line.setValue(KEY_HIDDEN_TICKET_NO, "");
                    line.setValue(KEY_HIDDEN_LINE_NO, "");
                    line.setValue(KEY_HIDDEN_BRANCH_NO, "");
                }
                // DFKLOOK ここまで修正

                line.setValue(KEY_LST_NO, outparam.get(AsWorkDisplaySCHParams.NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(AsWorkDisplaySCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(AsWorkDisplaySCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(AsWorkDisplaySCHParams.LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(AsWorkDisplaySCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(AsWorkDisplaySCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(AsWorkDisplaySCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(AsWorkDisplaySCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(AsWorkDisplaySCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(AsWorkDisplaySCHParams.ORDER_NO));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(AsWorkDisplaySCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(AsWorkDisplaySCHParams.CUSTOMER_NAME));
                lst_AsrsWorkDisplay_SetLineToolTip(line);
                _lcm_lst_AsrsWorkDisplay.add(line);
            }

            // DFKLOOK ここから修正
            // ボタン制御追加
            // ステーションが作業表示あり、完了ボタンありの場合、ボタンを有効にする
            if (SystemDefine.OPERATION_DISPLAY_INSTRUCTION.equals(param.getString(AsWorkDisplaySCHParams.WORK_DISPLAY_OPERATE)))
            {
                // 搬送区分により、ボタンの使用可否を決定する
                // 搬送区分が入庫の場合、払出不要
                String carrykind = param.getString(AsWorkDisplaySCHParams.CARRY_FLAG);
                if (SystemDefine.CARRY_FLAG_STORAGE.equals(carrykind))
                {
                    //完了ボタンを押下可能にします。
                    btn_Complete.setEnabled(true);
                    //払出完了ボタンを押下不可にします。
                    btn_PickComplete.setEnabled(false);
                }
                // 搬送区分が出庫の場合、払出可能
                else if (SystemDefine.CARRY_FLAG_RETRIEVAL.equals(carrykind))
                {
                    //完了ボタンを押下可能にします。
                    btn_Complete.setEnabled(true);
                    // 払出し可能なステーションで、ユニット出庫以外の場合
                    // 払出し完了ボタンを押下可能とします。それ以外は、押下不可。
                    if (param.getBoolean(AsWorkDisplaySCHParams.CANREMOVE)
                            && !SystemDefine.RETRIEVAL_DETAIL_UNIT.equals(param.getString(AsWorkDisplaySCHParams.RETRIEVAL_DETAIL)))
                    {
                        btn_PickComplete.setEnabled(true);
                    }
                    else
                    {
                        btn_PickComplete.setEnabled(false);
                    }
                }
                // 搬送区分が直行の場合、
                else if (SystemDefine.CARRY_FLAG_DIRECT_TRAVEL.equals(carrykind))
                {
                    //完了ボタンを押下可能にします。
                    btn_Complete.setEnabled(true);
                    //払出完了ボタンを押下不可にします。
                    btn_PickComplete.setEnabled(false);
                }
            }
            else
            {

                //完了ボタンを押下不可にします。
                btn_Complete.setEnabled(false);

                //払出完了ボタンを押下不可にします。
                btn_PickComplete.setEnabled(false);
            }

            rdo_Auto.setChecked(true);
            // DFKLOOK ここまで修正


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
    private void btn_ReDisplayFunc_Click_Process()
            throws Exception
    {
        // input validation.
        pul_Station.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsWorkDisplaySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsWorkDisplaySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsWorkDisplaySCHParams inparam = new AsWorkDisplaySCHParams();
            inparam.set(AsWorkDisplaySCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(AsWorkDisplaySCHParams.JOB_TYPE, "");
            inparam.set(AsWorkDisplaySCHParams.CANREMOVE, "");

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK ここから修正
            // 該当件数が「0」の場合
            if (outparams.size() == 0 || outparams == null)
            {
                // clear.
                txt_WorkNo.setValue(null);
                txt_Location.setValue(null);
                txt_WorkKind.setValue(null);
                txt_InstructDetail.setValue(null);
                _lcm_lst_AsrsWorkDisplay.clear();
                //完了ボタンを押下不可にします。
                btn_Complete.setEnabled(false);
                //払出完了ボタンを押下不可にします。
                btn_PickComplete.setEnabled(false);
                return;
            }

            // 条件分岐追加
            Params param = outparams.get(0);
            txt_WorkNo.setValue(param.get(AsWorkDisplaySCHParams.WORK_NO));
            txt_WorkKind.setValue(param.get(AsWorkDisplaySCHParams.WORK_TYPE_NAME));
            txt_InstructDetail.setValue(param.get(AsWorkDisplaySCHParams.RETRIEVAL_DETAIL_NAME));

            if (!StringUtil.isBlank(param.getString(AsWorkDisplaySCHParams.LOCATION_NO)))
            {
                AreaController areaControl = new AreaController(conn, getClass());
                String location = areaControl.toParamLocation(param.getString(AsWorkDisplaySCHParams.LOCATION_NO));
                txt_Location.setText(areaControl.toDispLocation(param.getString(AsWorkDisplaySCHParams.AREA_NO),
                        location));
            }
            else
            {
                // 棚No.が空の場合、""をセット
                txt_Location.setText("");
            }

            // DFKLOOK ここまで修正

            // output display.
            _lcm_lst_AsrsWorkDisplay.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_AsrsWorkDisplay.getNewLine();
                viewState.setObject(ViewStateKeys.STATION_NO, outparam.get(AsWorkDisplaySCHParams.STATION_NO));
                viewState.setObject(ViewStateKeys.CARRY_KEY, outparam.get(AsWorkDisplaySCHParams.CARRY_KEY));
                viewState.setObject(ViewStateKeys.CARRY_FLAG, outparam.get(AsWorkDisplaySCHParams.CARRY_FLAG));
                viewState.setObject(ViewStateKeys.WORK_DISPLAY_OPERATE, outparam.get(AsWorkDisplaySCHParams.WORK_DISPLAY_OPERATE));
                viewState.setObject(ViewStateKeys.AREA_NO, outparam.get(AsWorkDisplaySCHParams.AREA_NO));
                viewState.setObject(ViewStateKeys.WAREHOUSE_NO, outparam.get(AsWorkDisplaySCHParams.WAREHOUSE_NO));
                viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, outparam.get(AsWorkDisplaySCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.WORK_TYPE, outparam.get(AsWorkDisplaySCHParams.WORK_TYPE));
                viewState.setObject(ViewStateKeys.RETRIEVAL_DETAIL, outparam.get(AsWorkDisplaySCHParams.RETRIEVAL_DETAIL));

                // DFKLOOK ここから修正
                // 条件分岐追加
                if (SystemDefine.JOB_TYPE_STORAGE.equals(outparam.get(AsWorkDisplaySCHParams.JOB_TYPE))
                        || SystemDefine.JOB_TYPE_RETRIEVAL.equals(outparam.get(AsWorkDisplaySCHParams.JOB_TYPE)))
                {
                    line.setValue(KEY_HIDDEN_TICKET_NO, outparam.get(AsWorkDisplaySCHParams.TICKET_NO));
                    line.setValue(KEY_HIDDEN_LINE_NO, outparam.get(AsWorkDisplaySCHParams.LINE_NO));
                    line.setValue(KEY_HIDDEN_BRANCH_NO, outparam.get(AsWorkDisplaySCHParams.BRANCH_NO));
                }
                else
                {
                    line.setValue(KEY_HIDDEN_TICKET_NO, "");
                    line.setValue(KEY_HIDDEN_LINE_NO, "");
                    line.setValue(KEY_HIDDEN_BRANCH_NO, "");
                }
                // DFKLOOK ここまで修正

                line.setValue(KEY_LST_NO, outparam.get(AsWorkDisplaySCHParams.NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(AsWorkDisplaySCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(AsWorkDisplaySCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(AsWorkDisplaySCHParams.LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(AsWorkDisplaySCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(AsWorkDisplaySCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(AsWorkDisplaySCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(AsWorkDisplaySCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(AsWorkDisplaySCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(AsWorkDisplaySCHParams.ORDER_NO));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(AsWorkDisplaySCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(AsWorkDisplaySCHParams.CUSTOMER_NAME));
                lst_AsrsWorkDisplay_SetLineToolTip(line);
                _lcm_lst_AsrsWorkDisplay.add(line);
            }

            // DFKLOOK ここから修正
            // ボタン制御追加
            // ステーションが作業表示あり、完了ボタンありの場合、ボタンを有効にする
            if (SystemDefine.OPERATION_DISPLAY_INSTRUCTION.equals(param.getString(AsWorkDisplaySCHParams.WORK_DISPLAY_OPERATE)))
            {
                // 搬送区分により、ボタンの使用可否を決定する
                // 搬送区分が入庫の場合、払出不要
                String carrykind = param.getString(AsWorkDisplaySCHParams.CARRY_FLAG);
                if (SystemDefine.CARRY_FLAG_STORAGE.equals(carrykind))
                {
                    //完了ボタンを押下可能にします。
                    btn_Complete.setEnabled(true);
                    //払出完了ボタンを押下不可にします。
                    btn_PickComplete.setEnabled(false);
                }
                // 搬送区分が出庫の場合、払出可能
                else if (SystemDefine.CARRY_FLAG_RETRIEVAL.equals(carrykind))
                {
                    //完了ボタンを押下可能にします。
                    btn_Complete.setEnabled(true);
                    // 払出し可能なステーションで、ユニット出庫以外の場合
                    // 払出し完了ボタンを押下可能とします。それ以外は、押下不可。
                    if (param.getBoolean(AsWorkDisplaySCHParams.CANREMOVE)
                            && !SystemDefine.RETRIEVAL_DETAIL_UNIT.equals(param.getString(AsWorkDisplaySCHParams.RETRIEVAL_DETAIL)))
                    {
                        btn_PickComplete.setEnabled(true);
                    }
                    else
                    {
                        btn_PickComplete.setEnabled(false);
                    }
                }
                // 搬送区分が直行の場合、
                else if (SystemDefine.CARRY_FLAG_DIRECT_TRAVEL.equals(carrykind))
                {
                    //完了ボタンを押下可能にします。
                    btn_Complete.setEnabled(true);
                    //払出完了ボタンを押下不可にします。
                    btn_PickComplete.setEnabled(false);
                }
            }
            else
            {

                //完了ボタンを押下不可にします。
                btn_Complete.setEnabled(false);

                //払出完了ボタンを押下不可にします。
                btn_PickComplete.setEnabled(false);
            }
            // DFKLOOK ここまで修正

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
    private void btn_Complete_Click_Process(String eventSource)
            throws Exception
    {
        // DFKLOOK:ここから修正        
        if (StringUtil.isBlank(eventSource))
        {         
            // 完了しますか?
            this.setConfirm("MSG-W0043", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Complete_Click");
            return;         
        }
        // DFKLOOK:ここまで修正  

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsWorkDisplaySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsWorkDisplaySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_AsrsWorkDisplay.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_AsrsWorkDisplay.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                AsWorkDisplaySCHParams lineparam = new AsWorkDisplaySCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(AsWorkDisplaySCHParams.TICKET_NO, line.getValue(KEY_HIDDEN_TICKET_NO));
                lineparam.set(AsWorkDisplaySCHParams.LINE_NO, line.getValue(KEY_HIDDEN_LINE_NO));
                lineparam.set(AsWorkDisplaySCHParams.BRANCH_NO, line.getValue(KEY_HIDDEN_BRANCH_NO));
                lineparam.set(AsWorkDisplaySCHParams.NO, line.getValue(KEY_LST_NO));
                lineparam.set(AsWorkDisplaySCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(AsWorkDisplaySCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(AsWorkDisplaySCHParams.LOT_NO, line.getValue(KEY_LST_LOT_NO));
                lineparam.set(AsWorkDisplaySCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(AsWorkDisplaySCHParams.STOCK_CASE_QTY, line.getValue(KEY_LST_STOCK_CASE_QTY));
                lineparam.set(AsWorkDisplaySCHParams.STOCK_PIECE_QTY, line.getValue(KEY_LST_STOCK_PIECE_QTY));
                lineparam.set(AsWorkDisplaySCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                lineparam.set(AsWorkDisplaySCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                lineparam.set(AsWorkDisplaySCHParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                lineparam.set(AsWorkDisplaySCHParams.CUSTOMER_CODE, line.getValue(KEY_LST_CUSTOMER_CODE));
                lineparam.set(AsWorkDisplaySCHParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                lineparam.set(AsWorkDisplaySCHParams.CARRY_FLAG, viewState.getObject(ViewStateKeys.CARRY_FLAG));
                lineparam.set(AsWorkDisplaySCHParams.CARRY_KEY, viewState.getObject(ViewStateKeys.CARRY_KEY));
                lineparam.set(AsWorkDisplaySCHParams.LAST_UPDATE_DATE, viewState.getObject(ViewStateKeys.LAST_UPDATE_DATE));
                lineparam.set(AsWorkDisplaySCHParams.RETRIEVAL_DETAIL, viewState.getObject(ViewStateKeys.RETRIEVAL_DETAIL));
                lineparam.set(AsWorkDisplaySCHParams.STATION_NO, viewState.getObject(ViewStateKeys.STATION_NO));
                lineparam.set(AsWorkDisplaySCHParams.WAREHOUSE_NO, viewState.getObject(ViewStateKeys.WAREHOUSE_NO));
                lineparam.set(AsWorkDisplaySCHParams.WORK_DISPLAY_OPERATE, viewState.getObject(ViewStateKeys.WORK_DISPLAY_OPERATE));
                lineparam.set(AsWorkDisplaySCHParams.WORK_TYPE, viewState.getObject(ViewStateKeys.WORK_TYPE));
                lineparam.set(AsWorkDisplaySCHParams.WORK_NO, txt_WorkNo.getValue());
                lineparam.set(AsWorkDisplaySCHParams.LOCATION_NO, txt_Location.getValue());
                lineparam.set(AsWorkDisplaySCHParams.PROCESS_STATUS, AsrsInParameter.PROCESS_STATUS_COMPLETE);
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_AsrsWorkDisplay.resetEditRow();
                _lcm_lst_AsrsWorkDisplay.resetHighlight();
                _lcm_lst_AsrsWorkDisplay.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_AsrsWorkDisplay.size(); i++)
            {
                ListCellLine line = _lcm_lst_AsrsWorkDisplay.get(i);
                lst_AsrsWorkDisplay.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(AsrsInParameter.PROCESS_STATUS_COMPLETE, "");
                part11List.add(viewState.getString(ViewStateKeys.STATION_NO), "");
                part11List.add(txt_WorkNo.getStringValue(), "");
                part11List.add(txt_Location.getStringValue(), "");
                part11List.add(viewState.getString(ViewStateKeys.WORK_TYPE), "");
                part11List.add(viewState.getString(ViewStateKeys.RETRIEVAL_DETAIL), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_STOCK_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_STOCK_PIECE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_ORDER_NO), "");
                part11List.add(line.getViewString(KEY_LST_CUSTOMER_CODE), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK ここから修正
            // 入庫指示送信を起動します。
            SendRequestor req = new SendRequestor();
            req.storage();
            // DFKLOOK ここまで修正
            
            // reset editing row.
            _lcm_lst_AsrsWorkDisplay.resetEditRow();
            _lcm_lst_AsrsWorkDisplay.resetHighlight();

            // set input parameters.
            AsWorkDisplaySCHParams inparam = new AsWorkDisplaySCHParams();
            inparam.set(AsWorkDisplaySCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // DFKLOOK ここから修正
            // 該当件数が「0」の場合
            if (outparams.size() == 0 || outparams == null)
            {

                // clear.
                txt_WorkNo.setValue(null);
                txt_Location.setValue(null);
                txt_WorkKind.setValue(null);
                txt_InstructDetail.setValue(null);
                _lcm_lst_AsrsWorkDisplay.clear();
                //完了ボタンを押下不可にします。
                btn_Complete.setEnabled(false);
                //払出完了ボタンを押下不可にします。
                btn_PickComplete.setEnabled(false);
                return;
            }

            // 条件分岐追加
            Params param = outparams.get(0);
            txt_WorkNo.setValue(param.get(AsWorkDisplaySCHParams.WORK_NO));
            txt_WorkKind.setValue(param.get(AsWorkDisplaySCHParams.WORK_TYPE_NAME));
            txt_InstructDetail.setValue(param.get(AsWorkDisplaySCHParams.RETRIEVAL_DETAIL_NAME));

            // 荷姿検知ありで入庫設定の場合、棚決定していないため、倉庫ステーションNo.をセット
            StationController stCtl = new StationController(conn, getClass());
            if (stCtl.isReStoringEmptyLocationSearch(param.getString(AsWorkDisplaySCHParams.STATION_NO))
                    && (SystemDefine.WORK_TYPE_NOPLAN_STORAGE.equals(param.getString(AsWorkDisplaySCHParams.WORK_TYPE)) || SystemDefine.WORK_TYPE_STORAGE.equals(param.getString(AsWorkDisplaySCHParams.WORK_TYPE))))
            {
                // 棚にはエリアを設定
                txt_Location.setValue(param.get(AsWorkDisplaySCHParams.AREA_NO));
            }
            // 荷姿検知なし、または入庫設定でない場合、フォーマットした棚No.をセット
            else
            {
                if (!StringUtil.isBlank(param.getString(AsWorkDisplaySCHParams.LOCATION_NO)))
                {
                    AreaController areaControl = new AreaController(conn, getClass());
                    String location = areaControl.toParamLocation(param.getString(AsWorkDisplaySCHParams.LOCATION_NO));
                    txt_Location.setText(areaControl.toDispLocation(param.getString(AsWorkDisplaySCHParams.AREA_NO),
                            location));
                }
                else
                {
                    // 棚No.が空の場合、""をセット
                    txt_Location.setText("");
                }
            }
            // DFKLOOK ここまで修正

            // output display.
            _lcm_lst_AsrsWorkDisplay.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_AsrsWorkDisplay.getNewLine();
                viewState.setObject(ViewStateKeys.AREA_NO, outparam.get(AsWorkDisplaySCHParams.AREA_NO));
                viewState.setObject(ViewStateKeys.CARRY_FLAG, outparam.get(AsWorkDisplaySCHParams.CARRY_FLAG));
                viewState.setObject(ViewStateKeys.CARRY_KEY, outparam.get(AsWorkDisplaySCHParams.CARRY_KEY));
                viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, outparam.get(AsWorkDisplaySCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.RETRIEVAL_DETAIL, outparam.get(AsWorkDisplaySCHParams.RETRIEVAL_DETAIL));
                viewState.setObject(ViewStateKeys.STATION_NO, outparam.get(AsWorkDisplaySCHParams.STATION_NO));
                viewState.setObject(ViewStateKeys.WAREHOUSE_NO, outparam.get(AsWorkDisplaySCHParams.WAREHOUSE_NO));
                viewState.setObject(ViewStateKeys.WORK_DISPLAY_OPERATE, outparam.get(AsWorkDisplaySCHParams.WORK_DISPLAY_OPERATE));
                viewState.setObject(ViewStateKeys.WORK_TYPE, outparam.get(AsWorkDisplaySCHParams.WORK_TYPE));

                // DFKLOOK ここから修正
                // 条件分岐追加
                if (SystemDefine.JOB_TYPE_STORAGE.equals(outparam.get(AsWorkDisplaySCHParams.JOB_TYPE))
                        || SystemDefine.JOB_TYPE_RETRIEVAL.equals(outparam.get(AsWorkDisplaySCHParams.JOB_TYPE)))
                {
                    line.setValue(KEY_HIDDEN_TICKET_NO, outparam.get(AsWorkDisplaySCHParams.TICKET_NO));
                    line.setValue(KEY_HIDDEN_LINE_NO, outparam.get(AsWorkDisplaySCHParams.LINE_NO));
                    line.setValue(KEY_HIDDEN_BRANCH_NO, outparam.get(AsWorkDisplaySCHParams.BRANCH_NO));
                }
                else
                {
                    line.setValue(KEY_HIDDEN_TICKET_NO, "");
                    line.setValue(KEY_HIDDEN_LINE_NO, "");
                    line.setValue(KEY_HIDDEN_BRANCH_NO, "");
                }
                // DFKLOOK ここまで修正

                line.setValue(KEY_LST_NO, outparam.get(AsWorkDisplaySCHParams.NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(AsWorkDisplaySCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(AsWorkDisplaySCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(AsWorkDisplaySCHParams.LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(AsWorkDisplaySCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(AsWorkDisplaySCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(AsWorkDisplaySCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(AsWorkDisplaySCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(AsWorkDisplaySCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(AsWorkDisplaySCHParams.ORDER_NO));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(AsWorkDisplaySCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(AsWorkDisplaySCHParams.CUSTOMER_NAME));
                lst_AsrsWorkDisplay_SetLineToolTip(line);
                _lcm_lst_AsrsWorkDisplay.add(line);
            }

            // DFKLOOK ここから修正
            // ボタン制御追加
            // ステーションが作業表示あり、完了ボタンありの場合、ボタンを有効にする
            if (SystemDefine.OPERATION_DISPLAY_INSTRUCTION.equals(param.getString(AsWorkDisplaySCHParams.WORK_DISPLAY_OPERATE)))
            {
                // 搬送区分により、ボタンの使用可否を決定する
                // 搬送区分が入庫の場合、払出不要
                String carrykind = param.getString(AsWorkDisplaySCHParams.CARRY_FLAG);
                if (SystemDefine.CARRY_FLAG_STORAGE.equals(carrykind))
                {
                    //完了ボタンを押下可能にします。
                    btn_Complete.setEnabled(true);
                    //払出完了ボタンを押下不可にします。
                    btn_PickComplete.setEnabled(false);
                }
                // 搬送区分が出庫の場合、払出可能
                else if (SystemDefine.CARRY_FLAG_RETRIEVAL.equals(carrykind))
                {
                    //完了ボタンを押下可能にします。
                    btn_Complete.setEnabled(true);
                    // 払出し可能なステーションで、ユニット出庫以外の場合
                    // 払出し完了ボタンを押下可能とします。それ以外は、押下不可。
                    if (param.getBoolean(AsWorkDisplaySCHParams.CANREMOVE)
                            && !SystemDefine.RETRIEVAL_DETAIL_UNIT.equals(param.getString(AsWorkDisplaySCHParams.RETRIEVAL_DETAIL)))
                    {
                        btn_PickComplete.setEnabled(true);
                    }
                    else
                    {
                        btn_PickComplete.setEnabled(false);
                    }
                }
                // 搬送区分が直行の場合、
                else if (SystemDefine.CARRY_FLAG_DIRECT_TRAVEL.equals(carrykind))
                {
                    //完了ボタンを押下可能にします。
                    btn_Complete.setEnabled(true);
                    //払出完了ボタンを押下不可にします。
                    btn_PickComplete.setEnabled(false);
                }
            }
            else
            {

                //完了ボタンを押下不可にします。
                btn_Complete.setEnabled(false);

                //払出完了ボタンを押下不可にします。
                btn_PickComplete.setEnabled(false);
            }
            // DFKLOOK ここまで修正
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
    private void btn_PickComplete_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_AsrsWorkDisplay.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_AsrsWorkDisplay.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003001");
            return;
        }

        // DFKLOOK:ここから修正        
        if (StringUtil.isBlank(eventSource))
        {         
            // 払出完了しますか?
            this.setConfirm("MSG-W0044", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_PickComplete_Click");
            return;         
        }
        // DFKLOOK:ここまで修正  

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsWorkDisplaySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsWorkDisplaySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_AsrsWorkDisplay.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_AsrsWorkDisplay.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                AsWorkDisplaySCHParams lineparam = new AsWorkDisplaySCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(AsWorkDisplaySCHParams.CARRY_FLAG, viewState.getObject(ViewStateKeys.CARRY_FLAG));
                lineparam.set(AsWorkDisplaySCHParams.CARRY_KEY, viewState.getObject(ViewStateKeys.CARRY_KEY));
                lineparam.set(AsWorkDisplaySCHParams.LAST_UPDATE_DATE, viewState.getObject(ViewStateKeys.LAST_UPDATE_DATE));
                lineparam.set(AsWorkDisplaySCHParams.RETRIEVAL_DETAIL, viewState.getObject(ViewStateKeys.RETRIEVAL_DETAIL));
                lineparam.set(AsWorkDisplaySCHParams.STATION_NO, viewState.getObject(ViewStateKeys.STATION_NO));
                lineparam.set(AsWorkDisplaySCHParams.WAREHOUSE_NO, viewState.getObject(ViewStateKeys.WAREHOUSE_NO));
                lineparam.set(AsWorkDisplaySCHParams.WORK_DISPLAY_OPERATE, viewState.getObject(ViewStateKeys.WORK_DISPLAY_OPERATE));
                lineparam.set(AsWorkDisplaySCHParams.WORK_TYPE, viewState.getObject(ViewStateKeys.WORK_TYPE));
                lineparam.set(AsWorkDisplaySCHParams.WORK_NO, txt_WorkNo.getValue());
                lineparam.set(AsWorkDisplaySCHParams.LOCATION_NO, txt_Location.getValue());
                lineparam.set(AsWorkDisplaySCHParams.TICKET_NO, line.getValue(KEY_HIDDEN_TICKET_NO));
                lineparam.set(AsWorkDisplaySCHParams.LINE_NO, line.getValue(KEY_HIDDEN_LINE_NO));
                lineparam.set(AsWorkDisplaySCHParams.BRANCH_NO, line.getValue(KEY_HIDDEN_BRANCH_NO));
                lineparam.set(AsWorkDisplaySCHParams.NO, line.getValue(KEY_LST_NO));
                lineparam.set(AsWorkDisplaySCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(AsWorkDisplaySCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(AsWorkDisplaySCHParams.LOT_NO, line.getValue(KEY_LST_LOT_NO));
                lineparam.set(AsWorkDisplaySCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(AsWorkDisplaySCHParams.STOCK_CASE_QTY, line.getValue(KEY_LST_STOCK_CASE_QTY));
                lineparam.set(AsWorkDisplaySCHParams.STOCK_PIECE_QTY, line.getValue(KEY_LST_STOCK_PIECE_QTY));
                lineparam.set(AsWorkDisplaySCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                lineparam.set(AsWorkDisplaySCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                lineparam.set(AsWorkDisplaySCHParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                lineparam.set(AsWorkDisplaySCHParams.CUSTOMER_CODE, line.getValue(KEY_LST_CUSTOMER_CODE));
                lineparam.set(AsWorkDisplaySCHParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                lineparam.set(AsWorkDisplaySCHParams.PROCESS_STATUS, AsrsInParameter.PROCESS_STATUS_COMPLETE_REMOVE);
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_AsrsWorkDisplay.resetEditRow();
                _lcm_lst_AsrsWorkDisplay.resetHighlight();
                _lcm_lst_AsrsWorkDisplay.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_AsrsWorkDisplay.size(); i++)
            {
                ListCellLine line = _lcm_lst_AsrsWorkDisplay.get(i);
                lst_AsrsWorkDisplay.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(AsrsInParameter.PROCESS_STATUS_COMPLETE_REMOVE, "");
                part11List.add(viewState.getString(ViewStateKeys.STATION_NO), "");
                part11List.add(txt_WorkNo.getStringValue(), "");
                part11List.add(txt_Location.getStringValue(), "");
                part11List.add(viewState.getString(ViewStateKeys.WORK_TYPE), "");
                part11List.add(viewState.getString(ViewStateKeys.RETRIEVAL_DETAIL), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_STOCK_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_STOCK_PIECE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_ORDER_NO), "");
                part11List.add(line.getViewString(KEY_LST_CUSTOMER_CODE), "");
                part11List.add(line.getViewString(KEY_LST_CUSTOMER_NAME), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_AsrsWorkDisplay.resetEditRow();
            _lcm_lst_AsrsWorkDisplay.resetHighlight();

            // set input parameters.
            AsWorkDisplaySCHParams inparam = new AsWorkDisplaySCHParams();
            inparam.set(AsWorkDisplaySCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // DFKLOOK ここから修正
            // 該当件数が「0」の場合
            if (outparams.size() == 0 || outparams == null)
            {
                // clear.
                txt_WorkNo.setValue(null);
                txt_Location.setValue(null);
                txt_WorkKind.setValue(null);
                txt_InstructDetail.setValue(null);
                _lcm_lst_AsrsWorkDisplay.clear();
                //完了ボタンを押下不可にします。
                btn_Complete.setEnabled(false);
                //払出完了ボタンを押下不可にします。
                btn_PickComplete.setEnabled(false);
                return;
            }

            // 条件分岐追加
            Params param = outparams.get(0);
            txt_WorkNo.setValue(param.get(AsWorkDisplaySCHParams.WORK_NO));
            txt_WorkKind.setValue(param.get(AsWorkDisplaySCHParams.WORK_TYPE_NAME));
            txt_InstructDetail.setValue(param.get(AsWorkDisplaySCHParams.RETRIEVAL_DETAIL_NAME));

            // 荷姿検知ありで入庫設定の場合、棚決定していないため、倉庫ステーションNo.をセット
            StationController stCtl = new StationController(conn, getClass());
            if (stCtl.isReStoringEmptyLocationSearch(param.getString(AsWorkDisplaySCHParams.STATION_NO))
                    && (SystemDefine.WORK_TYPE_NOPLAN_STORAGE.equals(param.getString(AsWorkDisplaySCHParams.WORK_TYPE)) || SystemDefine.WORK_TYPE_STORAGE.equals(param.getString(AsWorkDisplaySCHParams.WORK_TYPE))))
            {
                // 棚にはエリアを設定
                txt_Location.setValue(param.get(AsWorkDisplaySCHParams.AREA_NO));
            }
            // 荷姿検知なし、または入庫設定でない場合、フォーマットした棚No.をセット
            else
            {
                if (!StringUtil.isBlank(param.getString(AsWorkDisplaySCHParams.LOCATION_NO)))
                {
                    AreaController areaControl = new AreaController(conn, getClass());
                    String location = areaControl.toParamLocation(param.getString(AsWorkDisplaySCHParams.LOCATION_NO));
                    txt_Location.setText(areaControl.toDispLocation(param.getString(AsWorkDisplaySCHParams.AREA_NO),
                            location));
                }
                else
                {
                    // 棚No.が空の場合、""をセット
                    txt_Location.setText("");
                }
            }
            // DFKLOOK ここまで修正

            // output display.
            _lcm_lst_AsrsWorkDisplay.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_AsrsWorkDisplay.getNewLine();
                viewState.setObject(ViewStateKeys.AREA_NO, outparam.get(AsWorkDisplaySCHParams.AREA_NO));
                viewState.setObject(ViewStateKeys.CARRY_FLAG, outparam.get(AsWorkDisplaySCHParams.CARRY_FLAG));
                viewState.setObject(ViewStateKeys.CARRY_KEY, outparam.get(AsWorkDisplaySCHParams.CARRY_KEY));
                viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, outparam.get(AsWorkDisplaySCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.RETRIEVAL_DETAIL, outparam.get(AsWorkDisplaySCHParams.RETRIEVAL_DETAIL));
                viewState.setObject(ViewStateKeys.STATION_NO, outparam.get(AsWorkDisplaySCHParams.STATION_NO));
                viewState.setObject(ViewStateKeys.WAREHOUSE_NO, outparam.get(AsWorkDisplaySCHParams.WAREHOUSE_NO));
                viewState.setObject(ViewStateKeys.WORK_DISPLAY_OPERATE, outparam.get(AsWorkDisplaySCHParams.WORK_DISPLAY_OPERATE));
                viewState.setObject(ViewStateKeys.WORK_TYPE, outparam.get(AsWorkDisplaySCHParams.WORK_TYPE));

                // DFKLOOK ここから修正
                // 条件分岐追加
                if (SystemDefine.JOB_TYPE_STORAGE.equals(outparam.get(AsWorkDisplaySCHParams.JOB_TYPE))
                        || SystemDefine.JOB_TYPE_RETRIEVAL.equals(outparam.get(AsWorkDisplaySCHParams.JOB_TYPE)))
                {
                    line.setValue(KEY_HIDDEN_TICKET_NO, outparam.get(AsWorkDisplaySCHParams.TICKET_NO));
                    line.setValue(KEY_HIDDEN_LINE_NO, outparam.get(AsWorkDisplaySCHParams.LINE_NO));
                    line.setValue(KEY_HIDDEN_BRANCH_NO, outparam.get(AsWorkDisplaySCHParams.BRANCH_NO));
                }
                else
                {
                    line.setValue(KEY_HIDDEN_TICKET_NO, "");
                    line.setValue(KEY_HIDDEN_LINE_NO, "");
                    line.setValue(KEY_HIDDEN_BRANCH_NO, "");
                }
                // DFKLOOK ここまで修正

                line.setValue(KEY_LST_NO, outparam.get(AsWorkDisplaySCHParams.NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(AsWorkDisplaySCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(AsWorkDisplaySCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(AsWorkDisplaySCHParams.LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(AsWorkDisplaySCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(AsWorkDisplaySCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(AsWorkDisplaySCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(AsWorkDisplaySCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(AsWorkDisplaySCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(AsWorkDisplaySCHParams.ORDER_NO));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(AsWorkDisplaySCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(AsWorkDisplaySCHParams.CUSTOMER_NAME));
                lst_AsrsWorkDisplay_SetLineToolTip(line);
                _lcm_lst_AsrsWorkDisplay.add(line);
            }

            // DFKLOOK ここから修正
            // ボタン制御追加
            // ステーションが作業表示あり、完了ボタンありの場合、ボタンを有効にする
            if (SystemDefine.OPERATION_DISPLAY_INSTRUCTION.equals(param.getString(AsWorkDisplaySCHParams.WORK_DISPLAY_OPERATE)))
            {
                // 搬送区分により、ボタンの使用可否を決定する
                // 搬送区分が入庫の場合、払出不要
                String carrykind = param.getString(AsWorkDisplaySCHParams.CARRY_FLAG);
                if (SystemDefine.CARRY_FLAG_STORAGE.equals(carrykind))
                {
                    //完了ボタンを押下可能にします。
                    btn_Complete.setEnabled(true);
                    //払出完了ボタンを押下不可にします。
                    btn_PickComplete.setEnabled(false);
                }
                // 搬送区分が出庫の場合、払出可能
                else if (SystemDefine.CARRY_FLAG_RETRIEVAL.equals(carrykind))
                {
                    //完了ボタンを押下可能にします。
                    btn_Complete.setEnabled(true);
                    // 払出し可能なステーションで、ユニット出庫以外の場合
                    // 払出し完了ボタンを押下可能とします。それ以外は、押下不可。
                    if (param.getBoolean(AsWorkDisplaySCHParams.CANREMOVE)
                            && !SystemDefine.RETRIEVAL_DETAIL_UNIT.equals(param.getString(AsWorkDisplaySCHParams.RETRIEVAL_DETAIL)))
                    {
                        btn_PickComplete.setEnabled(true);
                    }
                    else
                    {
                        btn_PickComplete.setEnabled(false);
                    }
                }
                // 搬送区分が直行の場合、
                else if (SystemDefine.CARRY_FLAG_DIRECT_TRAVEL.equals(carrykind))
                {
                    //完了ボタンを押下可能にします。
                    btn_Complete.setEnabled(true);
                    //払出完了ボタンを押下不可にします。
                    btn_PickComplete.setEnabled(false);
                }
            }
            else
            {

                //完了ボタンを押下不可にします。
                btn_Complete.setEnabled(false);

                //払出完了ボタンを押下不可にします。
                btn_PickComplete.setEnabled(false);
            }
            // DFKLOOK ここまで修正

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
