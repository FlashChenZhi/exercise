// $Id: FaAsReStoringPlanModifyBusiness.java 7801 2010-04-07 06:54:37Z kishimoto $
package jp.co.daifuku.wms.asrs.display.farestoringplanmodify;

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
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.bluedog.webapp.ViewState;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.dasch.FaAsReStoringPlanModifyDASCH;
import jp.co.daifuku.wms.asrs.dasch.FaAsReStoringPlanModifyDASCHParams;
import jp.co.daifuku.wms.asrs.display.farestoringplanmodify.FaAsReStoringPlanModify;
import jp.co.daifuku.wms.asrs.display.farestoringplanmodify.ViewStateKeys;
import jp.co.daifuku.wms.asrs.schedule.FaAsReStoringPlanModifySCH;
import jp.co.daifuku.wms.asrs.schedule.FaAsReStoringPlanModifySCHParams;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7801 $, $Date:: 2010-04-07 15:54:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class FaAsReStoringPlanModifyBusiness
        extends FaAsReStoringPlanModify
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_FaAsReStoringPlanModify(HIDDEN_PLAN_UKEY) */
    private static final ListCellKey KEY_HIDDEN_PLAN_UKEY = new ListCellKey("HIDDEN_PLAN_UKEY", new StringCellColumn(), false, false);

    /** lst_FaAsReStoringPlanModify(HIDDEN_LAST_UPDATE_DATE) */
    private static final ListCellKey KEY_HIDDEN_LAST_UPDATE_DATE = new ListCellKey("HIDDEN_LAST_UPDATE_DATE", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMSS), false, false);

    /** lst_FaAsReStoringPlanModify(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY = new ListCellKey("LST_MODIFY", new CheckBoxColumn(), true, true);

    /** lst_FaAsReStoringPlanModify(LST_DELETE) */
    private static final ListCellKey KEY_LST_DELETE = new ListCellKey("LST_DELETE", new CheckBoxColumn(), true, true);

    /** lst_FaAsReStoringPlanModify(LST_STATION_NO) */
    private static final ListCellKey KEY_LST_STATION_NO = new ListCellKey("LST_STATION_NO", new StringCellColumn(), true, false);

    /** lst_FaAsReStoringPlanModify(LST_WORK_NO) */
    private static final ListCellKey KEY_LST_WORK_NO = new ListCellKey("LST_WORK_NO", new StringCellColumn(), true, false);

    /** lst_FaAsReStoringPlanModify(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_FaAsReStoringPlanModify(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_FaAsReStoringPlanModify(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_FaAsReStoringPlanModify(LST_PLAN_QTY) */
    private static final ListCellKey KEY_LST_PLAN_QTY = new ListCellKey("LST_PLAN_QTY", new NumberCellColumn(0), true, true);

    /** lst_FaAsReStoringPlanModify(LST_STORAGE_DAY) */
    private static final ListCellKey KEY_LST_STORAGE_DAY = new ListCellKey("LST_STORAGE_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, true);

    /** lst_FaAsReStoringPlanModify(LST_STORAGE_TIME) */
    private static final ListCellKey KEY_LST_STORAGE_TIME = new ListCellKey("LST_STORAGE_TIME", new DateCellColumn(null, TIME_FORMAT.HMS), true, true);

    /** lst_FaAsReStoringPlanModify(LST_REMOVE_DATE) */
    private static final ListCellKey KEY_LST_REMOVE_DATE = new ListCellKey("LST_REMOVE_DATE", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), true, false);

    /** lst_FaAsReStoringPlanModify keys */
    private static final ListCellKey[] LST_FAASRESTORINGPLANMODIFY_KEYS = {
        KEY_HIDDEN_PLAN_UKEY,
        KEY_HIDDEN_LAST_UPDATE_DATE,
        KEY_LST_MODIFY,
        KEY_LST_DELETE,
        KEY_LST_STATION_NO,
        KEY_LST_WORK_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_ITEM_NAME,
        KEY_LST_LOT_NO,
        KEY_LST_PLAN_QTY,
        KEY_LST_STORAGE_DAY,
        KEY_LST_STORAGE_TIME,
        KEY_LST_REMOVE_DATE,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Station */
    private WmsStationPullDownModel _pdm_pul_Station;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_FaAsReStoringPlanModify */
    private ScrollListCellModel _lcm_lst_FaAsReStoringPlanModify;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaAsReStoringPlanModifyBusiness()
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
        // process call.
        btn_Set_Click_Process();
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

        // initialize pul_Station.
        _pdm_pul_Station = new WmsStationPullDownModel(pul_Station, locale, ui);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pager}, locale);

        // initialize lst_FaAsReStoringPlanModify.
        _lcm_lst_FaAsReStoringPlanModify = new ScrollListCellModel(lst_FaAsReStoringPlanModify, LST_FAASRESTORINGPLANMODIFY_KEYS, locale);
        _lcm_lst_FaAsReStoringPlanModify.setToolTipVisible(KEY_LST_MODIFY, true);
        _lcm_lst_FaAsReStoringPlanModify.setToolTipVisible(KEY_LST_DELETE, true);
        _lcm_lst_FaAsReStoringPlanModify.setToolTipVisible(KEY_LST_STATION_NO, true);
        _lcm_lst_FaAsReStoringPlanModify.setToolTipVisible(KEY_LST_WORK_NO, true);
        _lcm_lst_FaAsReStoringPlanModify.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_FaAsReStoringPlanModify.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_FaAsReStoringPlanModify.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_FaAsReStoringPlanModify.setToolTipVisible(KEY_LST_PLAN_QTY, false);
        _lcm_lst_FaAsReStoringPlanModify.setToolTipVisible(KEY_LST_STORAGE_DAY, false);
        _lcm_lst_FaAsReStoringPlanModify.setToolTipVisible(KEY_LST_STORAGE_TIME, false);
        _lcm_lst_FaAsReStoringPlanModify.setToolTipVisible(KEY_LST_REMOVE_DATE, true);
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
            _pdm_pul_Station.init(conn, StationType.RESTORING_MNT, Distribution.ALL);
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
    private void lst_FaAsReStoringPlanModify_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
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
        else if (eventSource.equals("btn_Set_Click"))
        {
            // process call.
            btn_Set_Click_SetList();
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
        setFocus(pul_Station);
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
        btn_AllClear.setEnabled(false);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaAsReStoringPlanModifyDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new FaAsReStoringPlanModifyDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            FaAsReStoringPlanModifyDASCHParams inparam = new FaAsReStoringPlanModifyDASCHParams();
            inparam.set(FaAsReStoringPlanModifyDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(FaAsReStoringPlanModifyDASCHParams.WORK_NO, txt_WorkNo.getValue());

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_FaAsReStoringPlanModify.clear();

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

            // clear.
            btn_Set.setEnabled(true);
            btn_AllClear.setEnabled(true);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaAsReStoringPlanModify.clear();
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
        FaAsReStoringPlanModifyDASCH dasch = null;
        try
        {
            // get session.
            dasch = (FaAsReStoringPlanModifyDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_FaAsReStoringPlanModify.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_FaAsReStoringPlanModify.getNewLine();
                line.setValue(KEY_HIDDEN_PLAN_UKEY, outparam.get(FaAsReStoringPlanModifyDASCHParams.PLAN_UKEY));
                line.setValue(KEY_HIDDEN_LAST_UPDATE_DATE, outparam.get(FaAsReStoringPlanModifyDASCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_LST_STATION_NO, outparam.get(FaAsReStoringPlanModifyDASCHParams.STATION_NO));
                line.setValue(KEY_LST_WORK_NO, outparam.get(FaAsReStoringPlanModifyDASCHParams.WORK_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(FaAsReStoringPlanModifyDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(FaAsReStoringPlanModifyDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(FaAsReStoringPlanModifyDASCHParams.LOT_NO));
                line.setValue(KEY_LST_PLAN_QTY, outparam.get(FaAsReStoringPlanModifyDASCHParams.PLAN_QTY));
                line.setValue(KEY_LST_STORAGE_DAY, outparam.get(FaAsReStoringPlanModifyDASCHParams.STORAGE_DAY));
                line.setValue(KEY_LST_STORAGE_TIME, outparam.get(FaAsReStoringPlanModifyDASCHParams.STORAGE_TIME));
                line.setValue(KEY_LST_REMOVE_DATE, outparam.get(FaAsReStoringPlanModifyDASCHParams.REMOVE_DATE));
                viewState.setObject(ViewStateKeys.VS_STATION_NO, _pdm_pul_Station.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_WORK_NO, txt_WorkNo.getValue());
                lst_FaAsReStoringPlanModify_SetLineToolTip(line);
                _lcm_lst_FaAsReStoringPlanModify.add(line);
            }

            // clear.
            btn_Set.setEnabled(true);
            btn_AllClear.setEnabled(true);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaAsReStoringPlanModify.clear();
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
        _pdm_pul_Station.setSelectedValue(null);
        txt_WorkNo.setValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_FaAsReStoringPlanModify.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_FaAsReStoringPlanModify.get(i);
            if (!(checkline.isAppend() || checkline.isEdited()))
            {
                continue;
            }

            existEditedRow = true;
            lst_FaAsReStoringPlanModify.setCurrentRow(i);
            lst_FaAsReStoringPlanModify.validate(checkline.getIndex(KEY_LST_STORAGE_DAY), false);
            lst_FaAsReStoringPlanModify.validate(checkline.getIndex(KEY_LST_STORAGE_TIME), false);
            lst_FaAsReStoringPlanModify.validate(checkline.getIndex(KEY_LST_PLAN_QTY), false);
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003001");
            return;
        }

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaAsReStoringPlanModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaAsReStoringPlanModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_FaAsReStoringPlanModify.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_FaAsReStoringPlanModify.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                FaAsReStoringPlanModifySCHParams lineparam = new FaAsReStoringPlanModifySCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(FaAsReStoringPlanModifySCHParams.PLAN_UKEY, line.getValue(KEY_HIDDEN_PLAN_UKEY));
                lineparam.set(FaAsReStoringPlanModifySCHParams.LAST_UPDATE_DATE, line.getValue(KEY_HIDDEN_LAST_UPDATE_DATE));
                lineparam.set(FaAsReStoringPlanModifySCHParams.PLAN_QTY, line.getValue(KEY_LST_PLAN_QTY));
                lineparam.set(FaAsReStoringPlanModifySCHParams.STORAGE_DAY, line.getValue(KEY_LST_STORAGE_DAY));
                lineparam.set(FaAsReStoringPlanModifySCHParams.STORAGE_TIME, line.getValue(KEY_LST_STORAGE_TIME));
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
                _lcm_lst_FaAsReStoringPlanModify.resetEditRow();
                _lcm_lst_FaAsReStoringPlanModify.resetHighlight();
                _lcm_lst_FaAsReStoringPlanModify.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_FaAsReStoringPlanModify.size(); i++)
            {
                ListCellLine line = _lcm_lst_FaAsReStoringPlanModify.get(i);
                lst_FaAsReStoringPlanModify.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                if (lst_FaAsReStoringPlanModify.getChecked(_lcm_lst_FaAsReStoringPlanModify.getColumnIndex(KEY_LST_MODIFY)))
                {
                    part11List.add(line.getViewString(KEY_LST_STATION_NO), "");
                    part11List.add(line.getViewString(KEY_LST_WORK_NO), "");
                    part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                    part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                    part11List.add(line.getViewString(KEY_LST_PLAN_QTY), "0");
                    part11List.add(line.getViewString(KEY_LST_STORAGE_DAY), line.getViewString(KEY_LST_STORAGE_TIME), "");
                    part11List.add(line.getViewString(KEY_LST_REMOVE_DATE), "");
                    part11List.add(line.getViewString(KEY_LST_PLAN_QTY), "0");
                    part11List.add(line.getViewString(KEY_LST_STORAGE_DAY), line.getViewString(KEY_LST_STORAGE_TIME), "");
                }

                if (lst_FaAsReStoringPlanModify.getChecked(_lcm_lst_FaAsReStoringPlanModify.getColumnIndex(KEY_LST_DELETE)))
                {
                    part11List.add(line.getViewString(KEY_LST_STATION_NO), "");
                    part11List.add(line.getViewString(KEY_LST_WORK_NO), "");
                    part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                    part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                    part11List.add(line.getViewString(KEY_LST_PLAN_QTY), "0");
                    part11List.add(line.getViewString(KEY_LST_STORAGE_DAY), line.getViewString(KEY_LST_STORAGE_TIME), "");
                    part11List.add(line.getViewString(KEY_LST_REMOVE_DATE), "");
                }

                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_MODIFY), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_FaAsReStoringPlanModify.resetEditRow();
            _lcm_lst_FaAsReStoringPlanModify.resetHighlight();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            return;
        }
        finally
        {
            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }

        boolean isSuccess = false;
        FaAsReStoringPlanModifyDASCH dasch = null;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Set_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new FaAsReStoringPlanModifyDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            FaAsReStoringPlanModifyDASCHParams inparam = new FaAsReStoringPlanModifyDASCHParams();
            inparam.set(FaAsReStoringPlanModifyDASCHParams.STATION_NO, viewState.getObject(ViewStateKeys.VS_STATION_NO));
            inparam.set(FaAsReStoringPlanModifyDASCHParams.WORK_NO, viewState.getObject(ViewStateKeys.VS_WORK_NO));

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _lcm_lst_FaAsReStoringPlanModify.clear();

            if (count == 0)
            {
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // set list.
            session.setAttribute(_KEY_DASCH, dasch);
            _pager.setMax(count);
            btn_Set_Click_SetList();
            isSuccess = true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaAsReStoringPlanModify.clear();
        }
        finally
        {
            if (!isSuccess)
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
    private void btn_Set_Click_SetList()
            throws Exception
    {
        FaAsReStoringPlanModifyDASCH dasch = null;
        try
        {
            // get session.
            dasch = (FaAsReStoringPlanModifyDASCH)session.getAttribute(_KEY_DASCH);
            _lcm_lst_FaAsReStoringPlanModify.clear();

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_FaAsReStoringPlanModify.getNewLine();
                line.setValue(KEY_HIDDEN_PLAN_UKEY, outparam.get(FaAsReStoringPlanModifyDASCHParams.PLAN_UKEY));
                line.setValue(KEY_HIDDEN_LAST_UPDATE_DATE, outparam.get(FaAsReStoringPlanModifyDASCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_LST_STATION_NO, outparam.get(FaAsReStoringPlanModifyDASCHParams.STATION_NO));
                line.setValue(KEY_LST_WORK_NO, outparam.get(FaAsReStoringPlanModifyDASCHParams.WORK_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(FaAsReStoringPlanModifyDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(FaAsReStoringPlanModifyDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(FaAsReStoringPlanModifyDASCHParams.LOT_NO));
                line.setValue(KEY_LST_PLAN_QTY, outparam.get(FaAsReStoringPlanModifyDASCHParams.PLAN_QTY));
                line.setValue(KEY_LST_STORAGE_DAY, outparam.get(FaAsReStoringPlanModifyDASCHParams.STORAGE_DAY));
                line.setValue(KEY_LST_STORAGE_TIME, outparam.get(FaAsReStoringPlanModifyDASCHParams.STORAGE_TIME));
                line.setValue(KEY_LST_REMOVE_DATE, outparam.get(FaAsReStoringPlanModifyDASCHParams.REMOVE_DATE));
                lst_FaAsReStoringPlanModify_SetLineToolTip(line);
                _lcm_lst_FaAsReStoringPlanModify.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaAsReStoringPlanModify.clear();
            disposeDasch();
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
        _lcm_lst_FaAsReStoringPlanModify.clear();
        _pager.clear();
        btn_Set.setEnabled(false);
        btn_AllClear.setEnabled(false);
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
