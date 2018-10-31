// $Id: ZoneMentenanceBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.pcart.master.display.zonementenance;

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
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.bluedog.webapp.ViewState;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.pcart.master.display.zonementenance.ViewStateKeys;
import jp.co.daifuku.pcart.master.display.zonementenance.ZoneMentenance;
import jp.co.daifuku.pcart.master.schedule.ZoneMentenanceSCH;
import jp.co.daifuku.pcart.master.schedule.ZoneMentenanceSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.master.schedule.MasterInParameter;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class ZoneMentenanceBusiness
        extends ZoneMentenance
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ZoneMentenance(HIDDEN_WORK_ZONE_NO) */
    private static final ListCellKey KEY_HIDDEN_WORK_ZONE_NO = new ListCellKey("HIDDEN_WORK_ZONE_NO", new StringCellColumn(), false, false);

    /** lst_ZoneMentenance(HIDDEN_ZONE_NO) */
    private static final ListCellKey KEY_HIDDEN_ZONE_NO = new ListCellKey("HIDDEN_ZONE_NO", new StringCellColumn(), false, false);

    /** lst_ZoneMentenance(HIDDEN_LAST_UPDATE_DATE) */
    private static final ListCellKey KEY_HIDDEN_LAST_UPDATE_DATE = new ListCellKey("HIDDEN_LAST_UPDATE_DATE", new StringCellColumn(), false, false);

    /** lst_ZoneMentenance(HIDDEN_NEW_DATA) */
    private static final ListCellKey KEY_HIDDEN_NEW_DATA = new ListCellKey("HIDDEN_NEW_DATA", new StringCellColumn(), false, false);

    /** lst_ZoneMentenance(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_ZoneMentenance(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_ZoneMentenance(LST_DELETE) */
    private static final ListCellKey KEY_LST_DELETE = new ListCellKey("LST_DELETE", new StringCellColumn(), true, false);

    /** lst_ZoneMentenance(LST_WORK_ZONE_NO) */
    private static final ListCellKey KEY_LST_WORK_ZONE_NO = new ListCellKey("LST_WORK_ZONE_NO", new StringCellColumn(), true, true);

    /** lst_ZoneMentenance(LST_ZONE_NO) */
    private static final ListCellKey KEY_LST_ZONE_NO = new ListCellKey("LST_ZONE_NO", new StringCellColumn(), true, true);

    /** lst_ZoneMentenance keys */
    private static final ListCellKey[] LST_ZONEMENTENANCE_KEYS = {
        KEY_HIDDEN_WORK_ZONE_NO,
        KEY_HIDDEN_ZONE_NO,
        KEY_HIDDEN_LAST_UPDATE_DATE,
        KEY_HIDDEN_NEW_DATA,
        KEY_LST_SELECT,
        KEY_LST_CANCEL,
        KEY_LST_DELETE,
        KEY_LST_WORK_ZONE_NO,
        KEY_LST_ZONE_NO,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** ListCellModel lst_ZoneMentenance */
    private ScrollListCellModel _lcm_lst_ZoneMentenance;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public ZoneMentenanceBusiness()
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
    public void btn_AllDelete_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllDelete_Click_Process();
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
    public void lst_ZoneMentenance_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_ZoneMentenance.getActiveCol();

        // choose process.
        if (_lcm_lst_ZoneMentenance.getColumnIndex(KEY_LST_CANCEL) == activeCol)
        {
            // process call.
            lst_Cancel_Click_Process();
        }
        else if (_lcm_lst_ZoneMentenance.getColumnIndex(KEY_LST_DELETE) == activeCol)
        {
            // process call.
            lst_Delete_Click_Process();
        }
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

        // initialize lst_ZoneMentenance.
        _lcm_lst_ZoneMentenance = new ScrollListCellModel(lst_ZoneMentenance, LST_ZONEMENTENANCE_KEYS, locale);
        _lcm_lst_ZoneMentenance.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_ZoneMentenance.setToolTipVisible(KEY_LST_CANCEL, false);
        _lcm_lst_ZoneMentenance.setToolTipVisible(KEY_LST_DELETE, false);
        _lcm_lst_ZoneMentenance.setToolTipVisible(KEY_LST_WORK_ZONE_NO, false);
        _lcm_lst_ZoneMentenance.setToolTipVisible(KEY_LST_ZONE_NO, false);
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
            _pdm_pul_Area.init(conn, AreaType.FLOOR, StationType.ALL, "", false);
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
    private void lst_ZoneMentenance_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
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
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        pul_Area.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ZoneMentenanceSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ZoneMentenanceSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            ZoneMentenanceSCHParams inparam = new ZoneMentenanceSCHParams();
            inparam.set(ZoneMentenanceSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_ZoneMentenance.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ZoneMentenance.getNewLine();
                line.setValue(KEY_LST_WORK_ZONE_NO, outparam.get(ZoneMentenanceSCHParams.WORK_ZONE_NO));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(ZoneMentenanceSCHParams.ZONE_NO));
                line.setValue(KEY_HIDDEN_NEW_DATA, "false");
                line.setValue(KEY_HIDDEN_LAST_UPDATE_DATE, outparam.get(ZoneMentenanceSCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_HIDDEN_WORK_ZONE_NO, outparam.get(ZoneMentenanceSCHParams.WORK_ZONE_NO));
                line.setValue(KEY_HIDDEN_ZONE_NO, outparam.get(ZoneMentenanceSCHParams.ZONE_NO));
                viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_Area.getSelectedValue());
                lst_ZoneMentenance_SetLineToolTip(line);
                _lcm_lst_ZoneMentenance.add(line);
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
        _pdm_pul_Area.setSelectedValue(null);
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
        for (int i = 1; i <= _lcm_lst_ZoneMentenance.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_ZoneMentenance.get(i);
            if (!(checkline.isAppend() || checkline.isEdited()))
            {
                continue;
            }

            existEditedRow = true;
            lst_ZoneMentenance.setCurrentRow(i);
            lst_ZoneMentenance.validate(checkline.getIndex(KEY_LST_WORK_ZONE_NO), false);
            lst_ZoneMentenance.validate(checkline.getIndex(KEY_LST_ZONE_NO), false);
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
        ZoneMentenanceSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ZoneMentenanceSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ZoneMentenance.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ZoneMentenance.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                ZoneMentenanceSCHParams lineparam = new ZoneMentenanceSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(ZoneMentenanceSCHParams.HIDDEN_WORK_ZONE_NO, line.getValue(KEY_HIDDEN_WORK_ZONE_NO));
                lineparam.set(ZoneMentenanceSCHParams.HIDDEN_ZONE_NO, line.getValue(KEY_HIDDEN_ZONE_NO));
                lineparam.set(ZoneMentenanceSCHParams.LAST_UPDATE_DATE, line.getValue(KEY_HIDDEN_LAST_UPDATE_DATE));
                lineparam.set(ZoneMentenanceSCHParams.NEW_DATA, line.getValue(KEY_HIDDEN_NEW_DATA));
                lineparam.set(ZoneMentenanceSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                lineparam.set(ZoneMentenanceSCHParams.WORK_ZONE_NO, line.getValue(KEY_LST_WORK_ZONE_NO));
                lineparam.set(ZoneMentenanceSCHParams.ZONE_NO, line.getValue(KEY_LST_ZONE_NO));
                lineparam.set(ZoneMentenanceSCHParams.PROCESS_FLAG, MasterInParameter.PROCESS_FLAG_MODIFY);
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
                _lcm_lst_ZoneMentenance.resetEditRow();
                _lcm_lst_ZoneMentenance.resetHighlight();
                _lcm_lst_ZoneMentenance.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_ZoneMentenance.resetEditRow();
            _lcm_lst_ZoneMentenance.resetHighlight();

            // set input parameters.
            ZoneMentenanceSCHParams inparam = new ZoneMentenanceSCHParams();
            inparam.set(ZoneMentenanceSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.VS_AREA_NO));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_ZoneMentenance.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ZoneMentenance.getNewLine();
                line.setValue(KEY_LST_WORK_ZONE_NO, outparam.get(ZoneMentenanceSCHParams.WORK_ZONE_NO));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(ZoneMentenanceSCHParams.ZONE_NO));
                line.setValue(KEY_HIDDEN_WORK_ZONE_NO, outparam.get(ZoneMentenanceSCHParams.WORK_ZONE_NO));
                line.setValue(KEY_HIDDEN_ZONE_NO, outparam.get(ZoneMentenanceSCHParams.ZONE_NO));
                line.setValue(KEY_HIDDEN_LAST_UPDATE_DATE, outparam.get(ZoneMentenanceSCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_HIDDEN_NEW_DATA, outparam.get(ZoneMentenanceSCHParams.NEW_DATA));
                lst_ZoneMentenance_SetLineToolTip(line);
                _lcm_lst_ZoneMentenance.add(line);
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
    private void btn_AllDelete_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ZoneMentenanceSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ZoneMentenanceSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ZoneMentenance.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ZoneMentenance.get(i);
                ZoneMentenanceSCHParams lineparam = new ZoneMentenanceSCHParams();
                lineparam.setProcessFlag(ProcessFlag.DELETE_ALL);
                lineparam.setRowIndex(i);
                lineparam.set(ZoneMentenanceSCHParams.HIDDEN_WORK_ZONE_NO, line.getValue(KEY_HIDDEN_WORK_ZONE_NO));
                lineparam.set(ZoneMentenanceSCHParams.HIDDEN_ZONE_NO, line.getValue(KEY_HIDDEN_ZONE_NO));
                lineparam.set(ZoneMentenanceSCHParams.LAST_UPDATE_DATE, line.getValue(KEY_HIDDEN_LAST_UPDATE_DATE));
                lineparam.set(ZoneMentenanceSCHParams.NEW_DATA, line.getValue(KEY_HIDDEN_NEW_DATA));
                lineparam.set(ZoneMentenanceSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                lineparam.set(ZoneMentenanceSCHParams.WORK_ZONE_NO, line.getValue(KEY_LST_WORK_ZONE_NO));
                lineparam.set(ZoneMentenanceSCHParams.ZONE_NO, line.getValue(KEY_LST_ZONE_NO));
                lineparam.set(ZoneMentenanceSCHParams.PROCESS_FLAG, MasterInParameter.PROCESS_FLAG_DELETE);
                lineparam.set(ZoneMentenanceSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.VS_AREA_NO));
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
                _lcm_lst_ZoneMentenance.resetEditRow();
                _lcm_lst_ZoneMentenance.resetHighlight();
                _lcm_lst_ZoneMentenance.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_ZoneMentenance.resetEditRow();
            _lcm_lst_ZoneMentenance.resetHighlight();

            // clear.
            _lcm_lst_ZoneMentenance.clear();
            btn_Set.setEnabled(false);
            btn_ListClear.setEnabled(false);
            btn_AllDelete.setEnabled(false);
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
        _lcm_lst_ZoneMentenance.clear();
        btn_Set.setEnabled(false);
        btn_ListClear.setEnabled(false);
        btn_AllDelete.setEnabled(false);
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_ZoneMentenance.getActiveRow();
        lst_ZoneMentenance.setCurrentRow(row);

        // reset editing row.
        lst_ZoneMentenance.removeRow(row);
        _lcm_lst_ZoneMentenance.resetEditRow();
        _lcm_lst_ZoneMentenance.resetHighlight();
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Delete_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_ZoneMentenance.getActiveRow();
        lst_ZoneMentenance.setCurrentRow(row);
        ListCellLine line = _lcm_lst_ZoneMentenance.get(row);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ZoneMentenanceSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ZoneMentenanceSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            ZoneMentenanceSCHParams inparam = new ZoneMentenanceSCHParams();
            inparam.setProcessFlag(ProcessFlag.DELETE);
            inparam.setRowIndex(row);
            inparam.set(ZoneMentenanceSCHParams.LAST_UPDATE_DATE, line.getValue(KEY_HIDDEN_LAST_UPDATE_DATE));
            inparam.set(ZoneMentenanceSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
            inparam.set(ZoneMentenanceSCHParams.HIDDEN_WORK_ZONE_NO, line.getValue(KEY_LST_WORK_ZONE_NO));
            inparam.set(ZoneMentenanceSCHParams.HIDDEN_ZONE_NO, line.getValue(KEY_LST_ZONE_NO));
            inparam.set(ZoneMentenanceSCHParams.PROCESS_FLAG, MasterInParameter.PROCESS_FLAG_DELETE);
            inparam.set(ZoneMentenanceSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.VS_AREA_NO));

            // SCH call.
            if (!sch.check(inparam))
            {
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_ZoneMentenance.resetEditRow();
                _lcm_lst_ZoneMentenance.resetHighlight();
                _lcm_lst_ZoneMentenance.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            lst_ZoneMentenance.removeRow(row);
            _lcm_lst_ZoneMentenance.resetEditRow();
            _lcm_lst_ZoneMentenance.resetHighlight();
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
