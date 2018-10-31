// $Id: FaRetrievalStartBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.retrieval.display.fastart;

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
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.Constants;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PrintHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkListHandler;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.retrieval.listbox.plandetail.FaLstRetrievalPlanDetailParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAllocPriorityPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsAllocPriorityPullDownModel.AllocateType;
import jp.co.daifuku.wms.retrieval.dasch.FaRetrievalStartDASCH;
import jp.co.daifuku.wms.retrieval.dasch.FaRetrievalStartDASCHParams;
import jp.co.daifuku.wms.retrieval.display.fastart.FaRetrievalStart;
import jp.co.daifuku.wms.retrieval.display.fastart.ViewStateKeys;
import jp.co.daifuku.wms.retrieval.schedule.FaRetrievalStartSCH;
import jp.co.daifuku.wms.retrieval.schedule.FaRetrievalStartSCHParams;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalInParameter;

/**
 * 出庫開始の画面設定を行います。
 *
 * @version $Revision: 7996 $, $Date:: 2011-07-06 09:52:24 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class FaRetrievalStartBusiness
        extends FaRetrievalStart
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // DFKLOOK start
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK end
    
    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_FaPlannedRetrieval(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE = new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_FaPlannedRetrieval(HIDDEN_PLAN_DAY) */
    private static final ListCellKey KEY_HIDDEN_PLAN_DAY = new ListCellKey("HIDDEN_PLAN_DAY", new StringCellColumn(), false, false);

    /** lst_FaPlannedRetrieval(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_FaPlannedRetrieval(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO = new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_FaPlannedRetrieval(LST_DETAIL) */
    private static final ListCellKey KEY_LST_DETAIL = new ListCellKey("LST_DETAIL", new StringCellColumn(), true, false);

    /** lst_FaPlannedRetrieval keys */
    private static final ListCellKey[] LST_FAPLANNEDRETRIEVAL_KEYS = {
        KEY_HIDDEN_CONSIGNOR_CODE,
        KEY_HIDDEN_PLAN_DAY,
        KEY_LST_SELECT,
        KEY_LST_BATCH_NO,
        KEY_LST_DETAIL,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_LAllocatedPattern */
    private WmsAllocPriorityPullDownModel _pdm_pul_LAllocatedPattern;

    /** RadioButtonGroupModel OrderCondition */
    private RadioButtonGroup _grp_OrderCondition;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_FaPlannedRetrieval */
    private ListCellModel _lcm_lst_FaPlannedRetrieval;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaRetrievalStartBusiness()
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
    	//DFKLOOK:ここから修正
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
            // process call.
            btn_Start_Click_Process(eventSource);
        }
    	//DFKLOOK:ここまで修正
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
    public void btn_Start_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        // DFKLOOK start
        //btn_Start_Click_Process();
        btn_Start_Click_Process(null);
        // DFKLOOK end
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
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_FaPlannedRetrieval_Click(ActionEvent e)
            throws Exception
    {
        //DFKLOOK:ここから修正
        // get event source column.
        int activeCol = lst_FaPlannedRetrieval.getActiveCol();
        // get active row.
        int row = lst_FaPlannedRetrieval.getActiveRow();
        ListCellLine line = _lcm_lst_FaPlannedRetrieval.get(row);
        
        // choose process.
        if (_lcm_lst_FaPlannedRetrieval.getColumnIndex(KEY_LST_DETAIL) == activeCol)
        {
            // dialog parameters set.
           FaLstRetrievalPlanDetailParams inparam = new FaLstRetrievalPlanDetailParams();
            inparam.set(FaLstRetrievalPlanDetailParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
            inparam.set(FaLstRetrievalPlanDetailParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // show dialog.
            ForwardParameters forwardParam = inparam.toForwardParameters();
            forwardParam.setParameter(_KEY_POPUPSOURCE, "lst_FaPlannedRetrieval_Click");
            redirect("/retrieval/listbox/plandetail/FaLstRetrievalPlanDetail.do", forwardParam, "/Progress.do");
        }
        //DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ListClear_Click(ActionEvent e)
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

        // initialize pul_LAllocatedPattern.
        _pdm_pul_LAllocatedPattern = new WmsAllocPriorityPullDownModel(pul_LAllocatedPattern, locale, ui);

        // initialize OrderCondition.
        _grp_OrderCondition = new RadioButtonGroup(new RadioButton[]{rdo_LCancelTicketNo, rdo_LPossibleWorkRetrieval}, locale);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pager}, locale);

        // initialize lst_FaPlannedRetrieval.
        _lcm_lst_FaPlannedRetrieval = new ListCellModel(lst_FaPlannedRetrieval, LST_FAPLANNEDRETRIEVAL_KEYS, locale);
        _lcm_lst_FaPlannedRetrieval.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_FaPlannedRetrieval.setToolTipVisible(KEY_LST_BATCH_NO, false);
        _lcm_lst_FaPlannedRetrieval.setToolTipVisible(KEY_LST_DETAIL, false);

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

            // load pul_LAllocatedPattern.
            _pdm_pul_LAllocatedPattern.init(conn, AllocateType.NORMAL);

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
    private void lst_FaPlannedRetrieval_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
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
        setFocus(txt_FromBatchNo);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_Start.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        pul_LAllocatedPattern.setEnabled(false);
        _pdm_pul_LAllocatedPattern.setSelectedValue(null);
        chk_LWorkListPrint.setEnabled(false);
        chk_LShortageListPrint.setEnabled(false);
        chk_LShortageListPrint.setChecked(false);
        rdo_LCancelTicketNo.setEnabled(false);
        rdo_LCancelTicketNo.setChecked(true);
        rdo_LPossibleWorkRetrieval.setEnabled(false);
        _lcm_lst_FaPlannedRetrieval.clear();
        
        // DFKLOOK start
        // デフォルトとして作業リスト発行にチェック
        chk_LWorkListPrint.setChecked(true);
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();
        
        Connection conn = null;
        FaRetrievalStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaRetrievalStartSCH(conn, this.getClass(), locale, ui);
            
            FaRetrievalStartSCHParams inparam = new FaRetrievalStartSCHParams();
            inparam.set(FaRetrievalStartSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));

            // 画面定義テーブルをチェック
            Params outParam = sch.initFind(inparam);
            
            if (outParam != null)
            {
                String printflg = outParam.getString(FaRetrievalStartSCHParams.WORK_LIST_PRINT_FLAG);
                if (SystemDefine.KEYDATA_OFF.equals(printflg))
                {
                    // 前回、チェックOFFだった場合は更新
                    chk_LWorkListPrint.setChecked(false);
                }
            }
            
            // ViewStateの初期化
            viewState.setObject(ViewStateKeys.FROM_BATCH_NO, null);
            viewState.setObject(ViewStateKeys.TO_BATCH_NO, null);
            viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, null);
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
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // DFKLOOK start
        // input validation.
        txt_FromBatchNo.validate(this, false);
        txt_ToBatchNo.validate(this, false);
        // DFKLOOK end
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaRetrievalStartDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new FaRetrievalStartDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            FaRetrievalStartDASCHParams inparam = new FaRetrievalStartDASCHParams();
            inparam.set(FaRetrievalStartDASCHParams.FROM_BATCH_NO, txt_FromBatchNo.getValue());
            inparam.set(FaRetrievalStartDASCHParams.TO_BATCH_NO, txt_ToBatchNo.getValue());
            inparam.set(FaRetrievalStartDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_FaPlannedRetrieval.clear();

            if (count == 0)
            {
                // DFKLOOK start
                // clear.
                btn_Start.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_AllClear.setEnabled(false);
                pul_LAllocatedPattern.setEnabled(false);
                chk_LWorkListPrint.setEnabled(false);
                chk_LShortageListPrint.setEnabled(false);
                rdo_LCancelTicketNo.setEnabled(false);
                rdo_LPossibleWorkRetrieval.setEnabled(false);
                // DFKLOOK end
                
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

            // clear.
            btn_Start.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_AllClear.setEnabled(true);
            pul_LAllocatedPattern.setEnabled(true);
            chk_LWorkListPrint.setEnabled(true);
            chk_LShortageListPrint.setEnabled(true);
            rdo_LCancelTicketNo.setEnabled(true);
            rdo_LPossibleWorkRetrieval.setEnabled(true);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaPlannedRetrieval.clear();
        }
        finally
        {
            if (isSuccess)
            {
                // set list.
                btn_Display_Click_SetList();

                // DFKLOOK start
                // 検索条件を保持する
                viewState.setObject(ViewStateKeys.FROM_BATCH_NO, txt_FromBatchNo.getValue());
                viewState.setObject(ViewStateKeys.TO_BATCH_NO, txt_ToBatchNo.getValue());
                viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                // DFKLOOK end
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
        FaRetrievalStartDASCH dasch = null;
        try
        {
            // get session.
            dasch = (FaRetrievalStartDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_FaPlannedRetrieval.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_FaPlannedRetrieval.getNewLine();
                line.setValue(KEY_LST_BATCH_NO, outparam.get(FaRetrievalStartDASCHParams.BATCH_NO));
                line.setValue(KEY_HIDDEN_CONSIGNOR_CODE, outparam.get(FaRetrievalStartDASCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_HIDDEN_PLAN_DAY, outparam.get(FaRetrievalStartDASCHParams.PLAN_DAY));
                lst_FaPlannedRetrieval_SetLineToolTip(line);
                _lcm_lst_FaPlannedRetrieval.add(line);
            }

            // clear.
            btn_Start.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_AllClear.setEnabled(true);
            pul_LAllocatedPattern.setEnabled(true);
            chk_LWorkListPrint.setEnabled(true);
            chk_LShortageListPrint.setEnabled(true);
            rdo_LCancelTicketNo.setEnabled(true);
            rdo_LPossibleWorkRetrieval.setEnabled(true);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaPlannedRetrieval.clear();
            disposeDasch();
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
        txt_FromBatchNo.setValue(null);
        txt_ToBatchNo.setValue(null);

    }

    /**
     *
     * @throws Exception
     */
    // DFKLOOK start 
    private void btn_Start_Click_Process(String eventSource)
            throws Exception
    // DFKLOOK end
    {
        // input validation.
        pul_LAllocatedPattern.validate(this, true);

        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_FaPlannedRetrieval.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_FaPlannedRetrieval.get(i);
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
        if (StringUtil.isBlank(eventSource))
        {
            // show confirm message. 開始しますか？
            this.setConfirm("MSG-W0031", true, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Start_Click");
            // 「処理中です」メッセージ表示
            message.setMsgResourceKey("6001017");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaRetrievalStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaRetrievalStartSCH(conn, this.getClass(), locale, ui);

            // DFKLOOK start
            // 引当パターンを設定
            FaRetrievalStartSCHParams checkParam = new FaRetrievalStartSCHParams();
            checkParam.set(FaRetrievalStartSCHParams.ALLOCATED_PATTERN, pul_LAllocatedPattern.getSelectedValue());
            
            if (eventSource.equals("btn_Start_Click") && !sch.check(checkParam))
            {
                if (!StringUtil.isBlank(sch.getDispMessage()))
                {
                    // show confirm message.
                    this.setConfirm(sch.getDispMessage(), false, true);
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Start_Click_SCH");
                    return;
                }

                // エラーメッセージを設定
                message.setMsgResourceKey(sch.getMessage());
                return;
            }
            // DFKLOOK end
            
            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_FaPlannedRetrieval.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_FaPlannedRetrieval.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                FaRetrievalStartSCHParams lineparam = new FaRetrievalStartSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(FaRetrievalStartSCHParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                lineparam.set(FaRetrievalStartSCHParams.PLAN_DAY, line.getValue(KEY_HIDDEN_PLAN_DAY));
                lineparam.set(FaRetrievalStartSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                lineparam.set(FaRetrievalStartSCHParams.ALLOCATED_PATTERN, _pdm_pul_LAllocatedPattern.getSelectedValue());
                lineparam.set(FaRetrievalStartSCHParams.WORK_LIST_PRINT_FLAG, chk_LWorkListPrint.getValue());
                lineparam.set(FaRetrievalStartSCHParams.SHORTAGE_LIST_PRINT_FLAG, chk_LShortageListPrint.getValue());
                lineparam.set(FaRetrievalStartSCHParams.SHORTAGE_WORK_FLAG, _grp_OrderCondition.getSelectedValue());
                // DFKLOOK start
                lineparam.set(FaRetrievalStartSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));
                // DFKLOOK end
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
                _lcm_lst_FaPlannedRetrieval.resetEditRow();
                _lcm_lst_FaPlannedRetrieval.resetHighlight();
                _lcm_lst_FaPlannedRetrieval.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_FaPlannedRetrieval.size(); i++)
            {
                ListCellLine line = _lcm_lst_FaPlannedRetrieval.get(i);
                lst_FaPlannedRetrieval.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(_pdm_pul_LAllocatedPattern.getSelectedStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_BATCH_NO), "");

                if (chk_LWorkListPrint.getChecked())
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                }

                if (chk_LShortageListPrint.getChecked())
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                }
                part11List.add(RetrievalInParameter.SHORTAGE_WORK_TICKET_CANCEL, "", rdo_LCancelTicketNo.getChecked());
                part11List.add(RetrievalInParameter.SHORTAGE_WORK_POSSIBLE_RETRIEVAL, "", rdo_LPossibleWorkRetrieval.getChecked());
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            // 統計情報の取得を行います。
            RetrievalPlanHandler planHandler = new RetrievalPlanHandler(conn);
            WorkInfoHandler workInfoHandler = new WorkInfoHandler(conn);
            StockHandler stockInfoHandler = new StockHandler(conn);
            ShortageInfoHandler shortageInfoHandler = new ShortageInfoHandler(conn);
            WorkListHandler workListHandler = new WorkListHandler(conn);
            PrintHistoryHandler printHistoryHandler = new PrintHistoryHandler(conn);
            planHandler.getStatics();
            workInfoHandler.getStatics();
            stockInfoHandler.getStatics();
            shortageInfoHandler.getStatics();
            workListHandler.getStatics();
            printHistoryHandler.getStatics();
            // DFKLOOK:ここまで修正
            
            // DFKLOOK start
            if (sch.isStartAsrsWork())
            {
                // 出庫指示送信へRMIメッセージを使用して出庫要求を行います。
                SendRequestor req = new SendRequestor();
                req.retrieval();
            }
            
            // 処理成功時には再表示処理を行う。
            redisplay(locale, ui);
            // DFKLOOK end

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
        for (int i = 1; i <= _lcm_lst_FaPlannedRetrieval.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_FaPlannedRetrieval.get(i);
            lst_FaPlannedRetrieval.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_FaPlannedRetrieval_SetLineToolTip(clearLine);
            _lcm_lst_FaPlannedRetrieval.set(i, clearLine);
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
        for (int i = 1; i <= _lcm_lst_FaPlannedRetrieval.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_FaPlannedRetrieval.get(i);
            lst_FaPlannedRetrieval.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_FaPlannedRetrieval_SetLineToolTip(clearLine);
            _lcm_lst_FaPlannedRetrieval.set(i, clearLine);
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
        btn_Start.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        rdo_LCancelTicketNo.setEnabled(false);
        rdo_LPossibleWorkRetrieval.setEnabled(false);
        chk_LWorkListPrint.setEnabled(false);
        chk_LShortageListPrint.setEnabled(false);
        pul_LAllocatedPattern.setEnabled(false);
        _pager.clear();
        _lcm_lst_FaPlannedRetrieval.clear();

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
    
    // DFKLOOK start
    /**
     * リストセルの再表示を行います。
     * 
     * @param locale ロケール
     * @param ui ユーザ情報
     */
    private void redisplay(Locale locale, DfkUserInfo ui)
            throws Exception
    {
        Connection conn = null;
        FaRetrievalStartDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new FaRetrievalStartDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            FaRetrievalStartDASCHParams inparam = new FaRetrievalStartDASCHParams();
            inparam.set(FaRetrievalStartDASCHParams.FROM_BATCH_NO, viewState.getObject(ViewStateKeys.FROM_BATCH_NO));
            inparam.set(FaRetrievalStartDASCHParams.TO_BATCH_NO, viewState.getObject(ViewStateKeys.TO_BATCH_NO));
            inparam.set(FaRetrievalStartDASCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));

            // get count.
            int count = dasch.count(inparam);
            // 再表示のためpagerのクリアはしません
            //_pager.clear();
            _pager.setMax(count);
            _lcm_lst_FaPlannedRetrieval.clear();

            if (count == 0)
            {
                // clear.
                btn_Start.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_AllClear.setEnabled(false);
                pul_LAllocatedPattern.setEnabled(false);
                chk_LWorkListPrint.setEnabled(false);
                chk_LShortageListPrint.setEnabled(false);
                rdo_LCancelTicketNo.setEnabled(false);
                rdo_LPossibleWorkRetrieval.setEnabled(false);
                
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // save session.
            session.setAttribute(_KEY_DASCH, dasch);
            isSuccess = true;

            // clear.
            btn_Start.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_AllClear.setEnabled(true);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaPlannedRetrieval.clear();
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
    // DFKLOOK end

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
