// $Id: TcReceivingShortageMntBusiness.java,v 1.2 2009/02/24 02:35:33 ose Exp $
package jp.co.daifuku.wms.receiving.display.tcshortagemnt;

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
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
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
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.receiving.schedule.TcReceivingInParameter;
import jp.co.daifuku.wms.receiving.schedule.TcReceivingShortageMntSCH;
import jp.co.daifuku.wms.receiving.schedule.TcReceivingShortageMntSCHParams;

/**
 * TC入荷欠品メンテナンスの画面処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:35:33 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class TcReceivingShortageMntBusiness
        extends TcReceivingShortageMnt
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    // DFKLOOK start
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK end
    
    /** lst_ReceivingShortageMnt(LST_HIDDEN_JOB_NO) */
    private static final ListCellKey KEY_LST_HIDDEN_JOB_NO = new ListCellKey("LST_HIDDEN_JOB_NO", new StringCellColumn(), false, false);

    /** lst_ReceivingShortageMnt(LST_HIDDEN_SETTING_UKEY) */
    private static final ListCellKey KEY_LST_HIDDEN_SETTING_UKEY = new ListCellKey("LST_HIDDEN_SETTING_UKEY", new StringCellColumn(), false, false);

    /** lst_ReceivingShortageMnt(LST_HIDDEN_CROSS_DOCK_UKEY) */
    private static final ListCellKey KEY_LST_HIDDEN_CROSS_DOCK_UKEY = new ListCellKey("LST_HIDDEN_CROSS_DOCK_UKEY", new StringCellColumn(), false, false);

    /** lst_ReceivingShortageMnt(LST_HIDDEN_PLAN_UKEY) */
    private static final ListCellKey KEY_LST_HIDDEN_PLAN_UKEY = new ListCellKey("LST_HIDDEN_PLAN_UKEY", new StringCellColumn(), false, false);

    /** lst_ReceivingShortageMnt(LST_HIDDEN_LAST_UPDATEDATE_KEY) */
    private static final ListCellKey KEY_LST_HIDDEN_LAST_UPDATEDATE_KEY = new ListCellKey("LST_HIDDEN_LAST_UPDATEDATE_KEY", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG, DateCellColumn.TIME_TYPE_SEC), false, false);

    /** lst_ReceivingShortageMnt(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_ReceivingShortageMnt(LST_PLAN_DAY) */
    private static final ListCellKey KEY_LST_PLAN_DAY = new ListCellKey("LST_PLAN_DAY", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG, DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_ReceivingShortageMnt(LST_SUPPLIER_CODE) */
    private static final ListCellKey KEY_LST_SUPPLIER_CODE = new ListCellKey("LST_SUPPLIER_CODE", new StringCellColumn(), true, false);

    /** lst_ReceivingShortageMnt(LST_SUPPLIER_NAME) */
    private static final ListCellKey KEY_LST_SUPPLIER_NAME = new ListCellKey("LST_SUPPLIER_NAME", new StringCellColumn(), true, false);

    /** lst_ReceivingShortageMnt(LST_RECEIVE_TICKET_NO) */
    private static final ListCellKey KEY_LST_RECEIVE_TICKET_NO = new ListCellKey("LST_RECEIVE_TICKET_NO", new StringCellColumn(), true, false);

    /** lst_ReceivingShortageMnt(LST_RECEIVE_LINE_NO) */
    private static final ListCellKey KEY_LST_RECEIVE_LINE_NO = new ListCellKey("LST_RECEIVE_LINE_NO", new NumberCellColumn(0), true, false);

    /** lst_ReceivingShortageMnt(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_ReceivingShortageMnt(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_ReceivingShortageMnt(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_ReceivingShortageMnt(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_ReceivingShortageMnt(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY = new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ReceivingShortageMnt(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY = new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ReceivingShortageMnt(LST_RESULT_CASE_QTY) */
    private static final ListCellKey KEY_LST_RESULT_CASE_QTY = new ListCellKey("LST_RESULT_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ReceivingShortageMnt(LST_RESULT_PIECE_QTY) */
    private static final ListCellKey KEY_LST_RESULT_PIECE_QTY = new ListCellKey("LST_RESULT_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ReceivingShortageMnt(LST_SHORTAGE_CASE_QTY) */
    private static final ListCellKey KEY_LST_SHORTAGE_CASE_QTY = new ListCellKey("LST_SHORTAGE_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ReceivingShortageMnt(LST_SHORTAGE_PIECE_QTY) */
    private static final ListCellKey KEY_LST_SHORTAGE_PIECE_QTY = new ListCellKey("LST_SHORTAGE_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ReceivingShortageMnt kyes */
    private static final ListCellKey[] LST_RECEIVINGSHORTAGEMNT_KEYS = {
        KEY_LST_HIDDEN_JOB_NO,
        KEY_LST_HIDDEN_SETTING_UKEY,
        KEY_LST_HIDDEN_CROSS_DOCK_UKEY,
        KEY_LST_HIDDEN_PLAN_UKEY,
        KEY_LST_HIDDEN_LAST_UPDATEDATE_KEY,
        KEY_LST_SELECT,
        KEY_LST_PLAN_DAY,
        KEY_LST_SUPPLIER_CODE,
        KEY_LST_RECEIVE_TICKET_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT_NO,
        KEY_LST_ENTERING_QTY,
        KEY_LST_PLAN_CASE_QTY,
        KEY_LST_RESULT_CASE_QTY,
        KEY_LST_SHORTAGE_CASE_QTY,
        KEY_LST_SUPPLIER_NAME,
        KEY_LST_RECEIVE_LINE_NO,
        KEY_LST_ITEM_NAME,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_RESULT_PIECE_QTY,
        KEY_LST_SHORTAGE_PIECE_QTY,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_ReceivingShortageMnt */
    private ListCellModel _lcm_lst_ReceivingShortageMnt;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public TcReceivingShortageMntBusiness()
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
        // DFKLOOK start
		// get event source.
		String eventSource = viewState.getString(_KEY_CONFIRMSOURCE);
		if(eventSource == null)
		{
			return;
		}

		// remove event source.
		viewState.remove(_KEY_CONFIRMSOURCE);

		// check result.
		boolean isExecute = new Boolean(String.valueOf(e.getEventArgs().get(0))).booleanValue();
		if(!isExecute)
		{
			return;
		}

		// choose process.
		if(eventSource.startsWith("btn_ShortageDecision_Click"))
		{
			// process call.
			btn_ShortageDecision_Click_Process(eventSource);
		}
		else if(eventSource.startsWith("btn_ShortageCancel_Click"))
		{
			// process call.
			btn_ShortageCancel_Click_Process(eventSource);
		}
        // DFKLOOK end
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
    public void btn_ShortageDecision_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ShortageDecision_Click_Process(null);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ShortageCancel_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ShortageCancel_Click_Process(null);
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
    public void btn_ListClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ListClear_Click_Process();
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
        Locale locale = httpRequest.getLocale();

        // initialize lst_ReceivingShortageMnt.
        _lcm_lst_ReceivingShortageMnt = new ListCellModel(lst_ReceivingShortageMnt, LST_RECEIVINGSHORTAGEMNT_KEYS, locale);
        _lcm_lst_ReceivingShortageMnt.setToolTipVisible(KEY_LST_SELECT, true);
        _lcm_lst_ReceivingShortageMnt.setToolTipVisible(KEY_LST_PLAN_DAY, true);
        _lcm_lst_ReceivingShortageMnt.setToolTipVisible(KEY_LST_SUPPLIER_CODE, true);
        _lcm_lst_ReceivingShortageMnt.setToolTipVisible(KEY_LST_SUPPLIER_NAME, true);
        _lcm_lst_ReceivingShortageMnt.setToolTipVisible(KEY_LST_RECEIVE_TICKET_NO, true);
        _lcm_lst_ReceivingShortageMnt.setToolTipVisible(KEY_LST_RECEIVE_LINE_NO, true);
        _lcm_lst_ReceivingShortageMnt.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_ReceivingShortageMnt.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_ReceivingShortageMnt.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_ReceivingShortageMnt.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_ReceivingShortageMnt.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_ReceivingShortageMnt.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_ReceivingShortageMnt.setToolTipVisible(KEY_LST_RESULT_CASE_QTY, true);
        _lcm_lst_ReceivingShortageMnt.setToolTipVisible(KEY_LST_RESULT_PIECE_QTY, true);
        _lcm_lst_ReceivingShortageMnt.setToolTipVisible(KEY_LST_SHORTAGE_CASE_QTY, true);
        _lcm_lst_ReceivingShortageMnt.setToolTipVisible(KEY_LST_SHORTAGE_PIECE_QTY, true);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_ReceivingShortageMnt_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0099", KEY_LST_SUPPLIER_NAME);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-W0537", KEY_LST_RESULT_CASE_QTY);
        line.addToolTip("LBL-W0538", KEY_LST_RESULT_PIECE_QTY);
        line.addToolTip("LBL-W0063", KEY_LST_SHORTAGE_CASE_QTY);
        line.addToolTip("LBL-W0064", KEY_LST_SHORTAGE_PIECE_QTY);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_ReceivingPlanDate);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_ShortageDecision.setEnabled(false);
        btn_ShortageCancel.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
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
        txt_ReceivingPlanDate.validate(this, false);
        txt_SupplierCode.validate(this, false);
        txt_SlipNumber.validate(this, false);
        txt_ItemCode.validate(this, false);
        txt_LotNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        TcReceivingShortageMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new TcReceivingShortageMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            // DFKLOOK コンストラクタにUserInfoをセット
            TcReceivingShortageMntSCHParams inparam = new TcReceivingShortageMntSCHParams(ui);
            // DFKLOOK ここまで修正
            inparam.set(TcReceivingShortageMntSCHParams.PLAN_DAY, txt_ReceivingPlanDate.getValue());
            inparam.set(TcReceivingShortageMntSCHParams.SUPPLIER_CODE, txt_SupplierCode.getValue());
            inparam.set(TcReceivingShortageMntSCHParams.TICKET_NO, txt_SlipNumber.getValue());
            inparam.set(TcReceivingShortageMntSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(TcReceivingShortageMntSCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(TcReceivingShortageMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_ReceivingShortageMnt.clear();
            
            // DFKLOOK ここから修正
            if(outparams.size() <= 0) 
            {
                btn_ShortageDecision.setEnabled(false);
                btn_ShortageCancel.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_ListClear.setEnabled(false);
                
                return;
            }
            
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ReceivingShortageMnt.getNewLine();
                line.setValue(KEY_LST_HIDDEN_JOB_NO, outparam.get(TcReceivingShortageMntSCHParams.HIDDEN_JOB_NO));
                line.setValue(KEY_LST_HIDDEN_SETTING_UKEY, outparam.get(TcReceivingShortageMntSCHParams.HIDDEN_SETTING_UKEY));
                line.setValue(KEY_LST_HIDDEN_CROSS_DOCK_UKEY, outparam.get(TcReceivingShortageMntSCHParams.HIDDEN_CROSS_DOCK_UKEY));
                line.setValue(KEY_LST_HIDDEN_PLAN_UKEY, outparam.get(TcReceivingShortageMntSCHParams.HIDDEN_PLAN_UKEY));
                line.setValue(KEY_LST_HIDDEN_LAST_UPDATEDATE_KEY, outparam.get(TcReceivingShortageMntSCHParams.HIDDEN_LAST_UPDATEDATE_KEY));
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(TcReceivingShortageMntSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_SUPPLIER_CODE, outparam.get(TcReceivingShortageMntSCHParams.SUPPLIER_CODE));
                line.setValue(KEY_LST_SUPPLIER_NAME, outparam.get(TcReceivingShortageMntSCHParams.SUPPLIER_NAME));
                line.setValue(KEY_LST_RECEIVE_TICKET_NO, outparam.get(TcReceivingShortageMntSCHParams.RECEIVE_TICKET_NO));
                line.setValue(KEY_LST_RECEIVE_LINE_NO, outparam.get(TcReceivingShortageMntSCHParams.RECEIVE_LINE_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(TcReceivingShortageMntSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(TcReceivingShortageMntSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(TcReceivingShortageMntSCHParams.LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(TcReceivingShortageMntSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(TcReceivingShortageMntSCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(TcReceivingShortageMntSCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_RESULT_CASE_QTY, outparam.get(TcReceivingShortageMntSCHParams.RESULT_CASE_QTY));
                line.setValue(KEY_LST_RESULT_PIECE_QTY, outparam.get(TcReceivingShortageMntSCHParams.RESULT_PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_CASE_QTY, outparam.get(TcReceivingShortageMntSCHParams.SHORTAGE_CASE_QTY));
                line.setValue(KEY_LST_SHORTAGE_PIECE_QTY, outparam.get(TcReceivingShortageMntSCHParams.SHORTAGE_PIECE_QTY));
                viewState.setObject(ViewStateKeys.PLAN_DAY, txt_ReceivingPlanDate.getValue());
                viewState.setObject(ViewStateKeys.SUPPLIER_CODE, txt_SupplierCode.getValue());
                viewState.setObject(ViewStateKeys.TICKET_NO, txt_SlipNumber.getValue());
                viewState.setObject(ViewStateKeys.ITEM_CODE, txt_ItemCode.getValue());
                viewState.setObject(ViewStateKeys.LOT_NO, txt_LotNo.getValue());
                lst_ReceivingShortageMnt_SetLineToolTip(line);
                _lcm_lst_ReceivingShortageMnt.add(line);
            }

            // clear.
            btn_ShortageDecision.setEnabled(true);
            btn_ShortageCancel.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_ListClear.setEnabled(true);

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
        txt_ReceivingPlanDate.setValue(null);
        txt_SupplierCode.setValue(null);
        txt_SlipNumber.setValue(null);
        txt_ItemCode.setValue(null);
        txt_LotNo.setValue(null);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_ShortageDecision_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_ReceivingShortageMnt.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_ReceivingShortageMnt.get(i);
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

        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
        	// 確定しますか？
            this.setConfirm("MSG-W0047", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_ShortageDecision_Click");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        TcReceivingShortageMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new TcReceivingShortageMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ReceivingShortageMnt.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ReceivingShortageMnt.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                // DFKLOOK コンストラクタにUserInfoをセット
                TcReceivingShortageMntSCHParams lineparam = new TcReceivingShortageMntSCHParams(ui);
                // DFKLOOK ここまで修正
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(TcReceivingShortageMntSCHParams.HIDDEN_JOB_NO, line.getValue(KEY_LST_HIDDEN_JOB_NO));
                lineparam.set(TcReceivingShortageMntSCHParams.HIDDEN_SETTING_UKEY, line.getValue(KEY_LST_HIDDEN_SETTING_UKEY));
                lineparam.set(TcReceivingShortageMntSCHParams.HIDDEN_CROSS_DOCK_UKEY, line.getValue(KEY_LST_HIDDEN_CROSS_DOCK_UKEY));
                lineparam.set(TcReceivingShortageMntSCHParams.HIDDEN_PLAN_UKEY, line.getValue(KEY_LST_HIDDEN_PLAN_UKEY));
                lineparam.set(TcReceivingShortageMntSCHParams.HIDDEN_LAST_UPDATEDATE_KEY, line.getValue(KEY_LST_HIDDEN_LAST_UPDATEDATE_KEY));
                lineparam.set(TcReceivingShortageMntSCHParams.PROCESS_FLAG, TcReceivingInParameter.PROCESS_FLAG_SHORTAGE_DECISION);
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

                // reset editing row or highligiting error row.
                _lcm_lst_ReceivingShortageMnt.resetEditRow();
                _lcm_lst_ReceivingShortageMnt.resetHighlight();
                _lcm_lst_ReceivingShortageMnt.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_ReceivingShortageMnt.size(); i++)
            {
                ListCellLine line = _lcm_lst_ReceivingShortageMnt.get(i);
                lst_ReceivingShortageMnt.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(TcReceivingInParameter.PROCESS_FLAG_SHORTAGE_DECISION, "");
                part11List.add(line.getViewString(KEY_LST_PLAN_DAY), "");
                part11List.add(line.getViewString(KEY_LST_SUPPLIER_CODE), "");
                part11List.add(line.getViewString(KEY_LST_RECEIVE_TICKET_NO), "");
                part11List.add(line.getViewString(KEY_LST_RECEIVE_LINE_NO), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_RESULT_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_RESULT_PIECE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_SHORTAGE_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_SHORTAGE_PIECE_QTY), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_ReceivingShortageMnt.resetEditRow();
            _lcm_lst_ReceivingShortageMnt.resetHighlight();

            // set input parameters.
            // DFKLOOK コンストラクタにUserInfoをセット
            TcReceivingShortageMntSCHParams inparam = new TcReceivingShortageMntSCHParams(ui);
            // DFKLOOK ここまで修正
            inparam.set(TcReceivingShortageMntSCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
            inparam.set(TcReceivingShortageMntSCHParams.LOT_NO, viewState.getObject(ViewStateKeys.LOT_NO));
            inparam.set(TcReceivingShortageMntSCHParams.PLAN_DAY, viewState.getObject(ViewStateKeys.PLAN_DAY));
            inparam.set(TcReceivingShortageMntSCHParams.TICKET_NO, viewState.getObject(ViewStateKeys.TICKET_NO));
            inparam.set(TcReceivingShortageMntSCHParams.SUPPLIER_CODE, viewState.getObject(ViewStateKeys.SUPPLIER_CODE));
            inparam.set(TcReceivingShortageMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_ReceivingShortageMnt.clear();
            
            // DFKLOOK ここから修正
            if(outparams.size() <= 0) 
            {
                // 下部ボタン無効化
                btn_ShortageDecision.setEnabled(false);
                btn_ShortageCancel.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_ListClear.setEnabled(false);
                
                return;
            }
            // DFKLOOK ここまで修正
            
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ReceivingShortageMnt.getNewLine();
                line.setValue(KEY_LST_HIDDEN_JOB_NO, outparam.get(TcReceivingShortageMntSCHParams.HIDDEN_JOB_NO));
                line.setValue(KEY_LST_HIDDEN_SETTING_UKEY, outparam.get(TcReceivingShortageMntSCHParams.HIDDEN_SETTING_UKEY));
                line.setValue(KEY_LST_HIDDEN_CROSS_DOCK_UKEY, outparam.get(TcReceivingShortageMntSCHParams.HIDDEN_CROSS_DOCK_UKEY));
                line.setValue(KEY_LST_HIDDEN_PLAN_UKEY, outparam.get(TcReceivingShortageMntSCHParams.HIDDEN_PLAN_UKEY));
                line.setValue(KEY_LST_HIDDEN_LAST_UPDATEDATE_KEY, outparam.get(TcReceivingShortageMntSCHParams.HIDDEN_LAST_UPDATEDATE_KEY));
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(TcReceivingShortageMntSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_SUPPLIER_CODE, outparam.get(TcReceivingShortageMntSCHParams.SUPPLIER_CODE));
                line.setValue(KEY_LST_SUPPLIER_NAME, outparam.get(TcReceivingShortageMntSCHParams.SUPPLIER_NAME));
                line.setValue(KEY_LST_RECEIVE_TICKET_NO, outparam.get(TcReceivingShortageMntSCHParams.RECEIVE_TICKET_NO));
                line.setValue(KEY_LST_RECEIVE_LINE_NO, outparam.get(TcReceivingShortageMntSCHParams.RECEIVE_LINE_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(TcReceivingShortageMntSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(TcReceivingShortageMntSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(TcReceivingShortageMntSCHParams.LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(TcReceivingShortageMntSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(TcReceivingShortageMntSCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(TcReceivingShortageMntSCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_RESULT_CASE_QTY, outparam.get(TcReceivingShortageMntSCHParams.RESULT_CASE_QTY));
                line.setValue(KEY_LST_RESULT_PIECE_QTY, outparam.get(TcReceivingShortageMntSCHParams.RESULT_PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_CASE_QTY, outparam.get(TcReceivingShortageMntSCHParams.SHORTAGE_CASE_QTY));
                line.setValue(KEY_LST_SHORTAGE_PIECE_QTY, outparam.get(TcReceivingShortageMntSCHParams.SHORTAGE_PIECE_QTY));
                lst_ReceivingShortageMnt_SetLineToolTip(line);
                _lcm_lst_ReceivingShortageMnt.add(line);
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
    private void btn_ShortageCancel_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_ReceivingShortageMnt.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_ReceivingShortageMnt.get(i);
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

        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
        	// 欠品のキャンセル処理を実行しますか？
            this.setConfirm("MSG-W0057", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_ShortageCancel_Click");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        TcReceivingShortageMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new TcReceivingShortageMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ReceivingShortageMnt.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ReceivingShortageMnt.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                // DFKLOOK コンストラクタにUserInfoをセット
                TcReceivingShortageMntSCHParams lineparam = new TcReceivingShortageMntSCHParams(ui);
                // DFKLOOK ここまで修正
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(TcReceivingShortageMntSCHParams.HIDDEN_JOB_NO, line.getValue(KEY_LST_HIDDEN_JOB_NO));
                lineparam.set(TcReceivingShortageMntSCHParams.HIDDEN_SETTING_UKEY, line.getValue(KEY_LST_HIDDEN_SETTING_UKEY));
                lineparam.set(TcReceivingShortageMntSCHParams.HIDDEN_CROSS_DOCK_UKEY, line.getValue(KEY_LST_HIDDEN_CROSS_DOCK_UKEY));
                lineparam.set(TcReceivingShortageMntSCHParams.HIDDEN_PLAN_UKEY, line.getValue(KEY_LST_HIDDEN_PLAN_UKEY));
                lineparam.set(TcReceivingShortageMntSCHParams.HIDDEN_LAST_UPDATEDATE_KEY, line.getValue(KEY_LST_HIDDEN_LAST_UPDATEDATE_KEY));
                lineparam.set(TcReceivingShortageMntSCHParams.PROCESS_FLAG, TcReceivingInParameter.PROCESS_FLAG_SHORTAGE_CANCEL);
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

                // reset editing row or highligiting error row.
                _lcm_lst_ReceivingShortageMnt.resetEditRow();
                _lcm_lst_ReceivingShortageMnt.resetHighlight();
                _lcm_lst_ReceivingShortageMnt.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_ReceivingShortageMnt.size(); i++)
            {
                ListCellLine line = _lcm_lst_ReceivingShortageMnt.get(i);
                lst_ReceivingShortageMnt.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(TcReceivingInParameter.PROCESS_FLAG_SHORTAGE_CANCEL, "");
                part11List.add(line.getViewString(KEY_LST_PLAN_DAY), "");
                part11List.add(line.getViewString(KEY_LST_SUPPLIER_CODE), "");
                part11List.add(line.getViewString(KEY_LST_RECEIVE_TICKET_NO), "");
                part11List.add(line.getViewString(KEY_LST_RECEIVE_LINE_NO), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_RESULT_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_RESULT_PIECE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_SHORTAGE_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_SHORTAGE_PIECE_QTY), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_ReceivingShortageMnt.resetEditRow();
            _lcm_lst_ReceivingShortageMnt.resetHighlight();

            // set input parameters.
            // DFKLOOK コンストラクタにUserInfoをセット
            TcReceivingShortageMntSCHParams inparam = new TcReceivingShortageMntSCHParams(ui);
            // DFKLOOK ここまで修正
            inparam.set(TcReceivingShortageMntSCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
            inparam.set(TcReceivingShortageMntSCHParams.LOT_NO, viewState.getObject(ViewStateKeys.LOT_NO));
            inparam.set(TcReceivingShortageMntSCHParams.PLAN_DAY, viewState.getObject(ViewStateKeys.PLAN_DAY));
            inparam.set(TcReceivingShortageMntSCHParams.TICKET_NO, viewState.getObject(ViewStateKeys.TICKET_NO));
            inparam.set(TcReceivingShortageMntSCHParams.SUPPLIER_CODE, viewState.getObject(ViewStateKeys.SUPPLIER_CODE));
            inparam.set(TcReceivingShortageMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_ReceivingShortageMnt.clear();
            
            // DFKLOOK ここから修正
            if(outparams.size() <= 0) 
            {
                // 下部ボタン無効化
                btn_ShortageDecision.setEnabled(false);
                btn_ShortageCancel.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_ListClear.setEnabled(false);
                
                return;
            }
            // DFKLOOK ここまで修正
            
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ReceivingShortageMnt.getNewLine();
                line.setValue(KEY_LST_HIDDEN_JOB_NO, outparam.get(TcReceivingShortageMntSCHParams.HIDDEN_JOB_NO));
                line.setValue(KEY_LST_HIDDEN_SETTING_UKEY, outparam.get(TcReceivingShortageMntSCHParams.HIDDEN_SETTING_UKEY));
                line.setValue(KEY_LST_HIDDEN_CROSS_DOCK_UKEY, outparam.get(TcReceivingShortageMntSCHParams.HIDDEN_CROSS_DOCK_UKEY));
                line.setValue(KEY_LST_HIDDEN_PLAN_UKEY, outparam.get(TcReceivingShortageMntSCHParams.HIDDEN_PLAN_UKEY));
                line.setValue(KEY_LST_HIDDEN_LAST_UPDATEDATE_KEY, outparam.get(TcReceivingShortageMntSCHParams.HIDDEN_LAST_UPDATEDATE_KEY));
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(TcReceivingShortageMntSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_SUPPLIER_CODE, outparam.get(TcReceivingShortageMntSCHParams.SUPPLIER_CODE));
                line.setValue(KEY_LST_SUPPLIER_NAME, outparam.get(TcReceivingShortageMntSCHParams.SUPPLIER_NAME));
                line.setValue(KEY_LST_RECEIVE_TICKET_NO, outparam.get(TcReceivingShortageMntSCHParams.RECEIVE_TICKET_NO));
                line.setValue(KEY_LST_RECEIVE_LINE_NO, outparam.get(TcReceivingShortageMntSCHParams.RECEIVE_LINE_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(TcReceivingShortageMntSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(TcReceivingShortageMntSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(TcReceivingShortageMntSCHParams.LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(TcReceivingShortageMntSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(TcReceivingShortageMntSCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(TcReceivingShortageMntSCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_RESULT_CASE_QTY, outparam.get(TcReceivingShortageMntSCHParams.RESULT_CASE_QTY));
                line.setValue(KEY_LST_RESULT_PIECE_QTY, outparam.get(TcReceivingShortageMntSCHParams.RESULT_PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_CASE_QTY, outparam.get(TcReceivingShortageMntSCHParams.SHORTAGE_CASE_QTY));
                line.setValue(KEY_LST_SHORTAGE_PIECE_QTY, outparam.get(TcReceivingShortageMntSCHParams.SHORTAGE_PIECE_QTY));
                lst_ReceivingShortageMnt_SetLineToolTip(line);
                _lcm_lst_ReceivingShortageMnt.add(line);
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
        for (int i = 1; i <= _lcm_lst_ReceivingShortageMnt.size(); i++)
        {
            ListCellLine clearline = _lcm_lst_ReceivingShortageMnt.get(i);
            lst_ReceivingShortageMnt.setCurrentRow(i);
            clearline.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_ReceivingShortageMnt_SetLineToolTip(clearline);
            _lcm_lst_ReceivingShortageMnt.set(i, clearline);
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
        for (int i = 1; i <= _lcm_lst_ReceivingShortageMnt.size(); i++)
        {
            ListCellLine clearline = _lcm_lst_ReceivingShortageMnt.get(i);
            lst_ReceivingShortageMnt.setCurrentRow(i);
            clearline.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_ReceivingShortageMnt_SetLineToolTip(clearline);
            _lcm_lst_ReceivingShortageMnt.set(i, clearline);
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
        _lcm_lst_ReceivingShortageMnt.clear();
        btn_ShortageDecision.setEnabled(false);
        btn_ShortageCancel.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
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
