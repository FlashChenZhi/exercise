// $Id: ReplenishListCompleteBusiness.java 7544 2010-03-15 00:25:30Z ota $
package jp.co.daifuku.wms.replenish.display.listcomplete;

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
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.util.ControlColor;
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
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.replenish.display.listcomplete.ReplenishListComplete;
import jp.co.daifuku.wms.replenish.display.listcomplete.ViewStateKeys;
import jp.co.daifuku.wms.replenish.schedule.ReplenishListCompleteSCH;
import jp.co.daifuku.wms.replenish.schedule.ReplenishListCompleteSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7544 $, $Date:: 2010-03-15 09:25:30 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class ReplenishListCompleteBusiness
        extends ReplenishListComplete
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ReplenishmentResultInput(LST_JOB_NO) */
    private static final ListCellKey KEY_LST_JOB_NO = new ListCellKey("LST_JOB_NO", new StringCellColumn(), false, false);

    /** lst_ReplenishmentResultInput(LST_WORK_TYPE) */
    private static final ListCellKey KEY_LST_WORK_TYPE = new ListCellKey("LST_WORK_TYPE", new StringCellColumn(), false, false);

    /** lst_ReplenishmentResultInput(LST_FROM_AREA) */
    private static final ListCellKey KEY_LST_FROM_AREA = new ListCellKey("LST_FROM_AREA", new AreaCellColumn(), true, false);

    /** lst_ReplenishmentResultInput(LST_FROM_LOCATION) */
    private static final ListCellKey KEY_LST_FROM_LOCATION = new ListCellKey("LST_FROM_LOCATION", new LocationCellColumn(), true, false);

    /** lst_ReplenishmentResultInput(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_ReplenishmentResultInput(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_ReplenishmentResultInput(LST_LOT) */
    private static final ListCellKey KEY_LST_LOT = new ListCellKey("LST_LOT", new StringCellColumn(), true, false);

    /** lst_ReplenishmentResultInput(LST_CASE_PACK) */
    private static final ListCellKey KEY_LST_CASE_PACK = new ListCellKey("LST_CASE_PACK", new NumberCellColumn(0), true, false);

    /** lst_ReplenishmentResultInput(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY = new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ReplenishmentResultInput(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY = new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ReplenishmentResultInput(LST_REPLENISHMENT_CASE_QTY) */
    private static final ListCellKey KEY_LST_REPLENISHMENT_CASE_QTY = new ListCellKey("LST_REPLENISHMENT_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ReplenishmentResultInput(LST_REPLENISHMENT_PIECE_QTY) */
    private static final ListCellKey KEY_LST_REPLENISHMENT_PIECE_QTY = new ListCellKey("LST_REPLENISHMENT_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ReplenishmentResultInput(LST_TO_AREA) */
    private static final ListCellKey KEY_LST_TO_AREA = new ListCellKey("LST_TO_AREA", new AreaCellColumn(), true, false);

    /** lst_ReplenishmentResultInput(LST_TO_LOCATION) */
    private static final ListCellKey KEY_LST_TO_LOCATION = new ListCellKey("LST_TO_LOCATION", new LocationCellColumn(), true, false);

    /** lst_ReplenishmentResultInput keys */
    private static final ListCellKey[] LST_REPLENISHMENTRESULTINPUT_KEYS = {
        KEY_LST_JOB_NO,
        KEY_LST_WORK_TYPE,
        KEY_LST_FROM_AREA,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT,
        KEY_LST_CASE_PACK,
        KEY_LST_PLAN_CASE_QTY,
        KEY_LST_REPLENISHMENT_CASE_QTY,
        KEY_LST_TO_AREA,
        KEY_LST_FROM_LOCATION,
        KEY_LST_ITEM_NAME,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_REPLENISHMENT_PIECE_QTY,
        KEY_LST_TO_LOCATION,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** RadioButtonGroupModel ReplenishmentWorkFlag */
    private RadioButtonGroup _grp_ReplenishmentWorkFlag;

    /** ListCellModel lst_ReplenishmentResultInput */
    private ListCellModel _lcm_lst_ReplenishmentResultInput;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public ReplenishListCompleteBusiness()
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
    public void btn_Complete_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Complete_Click_Process();
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
        Locale locale = httpRequest.getLocale();

        // initialize ReplenishmentWorkFlag.
        _grp_ReplenishmentWorkFlag = new RadioButtonGroup(new RadioButton[]{rdo_ReplenishmentWorkFlagPlan, rdo_ReplenishmentWorkFlagUgt}, locale);

        // initialize lst_ReplenishmentResultInput.
        _lcm_lst_ReplenishmentResultInput = new ListCellModel(lst_ReplenishmentResultInput, LST_REPLENISHMENTRESULTINPUT_KEYS, locale);
        _lcm_lst_ReplenishmentResultInput.setToolTipVisible(KEY_LST_FROM_AREA, true);
        _lcm_lst_ReplenishmentResultInput.setToolTipVisible(KEY_LST_FROM_LOCATION, true);
        _lcm_lst_ReplenishmentResultInput.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_ReplenishmentResultInput.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_ReplenishmentResultInput.setToolTipVisible(KEY_LST_LOT, true);
        _lcm_lst_ReplenishmentResultInput.setToolTipVisible(KEY_LST_CASE_PACK, true);
        _lcm_lst_ReplenishmentResultInput.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_ReplenishmentResultInput.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_ReplenishmentResultInput.setToolTipVisible(KEY_LST_REPLENISHMENT_CASE_QTY, true);
        _lcm_lst_ReplenishmentResultInput.setToolTipVisible(KEY_LST_REPLENISHMENT_PIECE_QTY, true);
        _lcm_lst_ReplenishmentResultInput.setToolTipVisible(KEY_LST_TO_AREA, true);
        _lcm_lst_ReplenishmentResultInput.setToolTipVisible(KEY_LST_TO_LOCATION, true);
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
    private void lst_ReplenishmentResultInput_SetLineToolTip(ListCellLine line)
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
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(rdo_ReplenishmentWorkFlagPlan);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        rdo_ReplenishmentWorkFlagPlan.setChecked(true);
        txt_LListWorkNo.setReadOnly(true);
        btn_Complete.setEnabled(false);
        btn_AllClear.setEnabled(false);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ListWorkNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ReplenishListCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ReplenishListCompleteSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            ReplenishListCompleteSCHParams inparam = new ReplenishListCompleteSCHParams();
            inparam.set(ReplenishListCompleteSCHParams.SELECT_JOB_TYPE, _grp_ReplenishmentWorkFlag.getSelectedValue());
            inparam.set(ReplenishListCompleteSCHParams.SETTING_UKEY, txt_ListWorkNo.getValue());
            inparam.set(ReplenishListCompleteSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_ReplenishmentResultInput.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ReplenishmentResultInput.getNewLine();
                line.setValue(KEY_LST_JOB_NO, outparam.get(ReplenishListCompleteSCHParams.JOB_NO));
                line.setValue(KEY_LST_FROM_AREA, outparam.get(ReplenishListCompleteSCHParams.FROM_AREA));
                line.setValue(KEY_LST_FROM_LOCATION, outparam.get(ReplenishListCompleteSCHParams.FROM_LOCATION));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(ReplenishListCompleteSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(ReplenishListCompleteSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT, outparam.get(ReplenishListCompleteSCHParams.LOT_NO));
                line.setValue(KEY_LST_CASE_PACK, outparam.get(ReplenishListCompleteSCHParams.CASE_PACK));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(ReplenishListCompleteSCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(ReplenishListCompleteSCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_REPLENISHMENT_CASE_QTY, outparam.get(ReplenishListCompleteSCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_REPLENISHMENT_PIECE_QTY, outparam.get(ReplenishListCompleteSCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_TO_AREA, outparam.get(ReplenishListCompleteSCHParams.TO_AREA));
                line.setValue(KEY_LST_TO_LOCATION, outparam.get(ReplenishListCompleteSCHParams.TO_LOCATION));
                viewState.setObject(ViewStateKeys.SELECT_JOB_TYPE, _grp_ReplenishmentWorkFlag.getSelectedValue());
                viewState.setObject(ViewStateKeys.SETTING_UKEY, txt_ListWorkNo.getValue());
                viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                txt_LRWorkFlag.setValue(_grp_ReplenishmentWorkFlag.getSelectedValue());
                txt_LListWorkNo.setValue(txt_ListWorkNo.getValue());
                line.setValue(KEY_LST_WORK_TYPE, _grp_ReplenishmentWorkFlag.getSelectedValue());
                lst_ReplenishmentResultInput_SetLineToolTip(line);
                _lcm_lst_ReplenishmentResultInput.add(line);
            }

            // clear.
            btn_Complete.setEnabled(true);
            btn_AllClear.setEnabled(true);
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
        rdo_ReplenishmentWorkFlagPlan.setChecked(true);
        txt_ListWorkNo.setValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Complete_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ReplenishListCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ReplenishListCompleteSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ReplenishmentResultInput.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ReplenishmentResultInput.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                ReplenishListCompleteSCHParams lineparam = new ReplenishListCompleteSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(ReplenishListCompleteSCHParams.JOB_NO, line.getValue(KEY_LST_JOB_NO));
                lineparam.set(ReplenishListCompleteSCHParams.L_SETTING_UKEY, txt_LListWorkNo.getValue());
                lineparam.set(ReplenishListCompleteSCHParams.REPLENISH_QTY, 0);
                lineparam.set(ReplenishListCompleteSCHParams.SELECT_JOB_TYPE, viewState.getObject(ViewStateKeys.SELECT_JOB_TYPE));
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
                _lcm_lst_ReplenishmentResultInput.resetEditRow();
                _lcm_lst_ReplenishmentResultInput.resetHighlight();
                _lcm_lst_ReplenishmentResultInput.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_ReplenishmentResultInput.size(); i++)
            {
                ListCellLine line = _lcm_lst_ReplenishmentResultInput.get(i);
                lst_ReplenishmentResultInput.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_WORK_TYPE), "");
                part11List.add(line.getViewString(KEY_LST_JOB_NO), "");
                part11List.add(line.getViewString(KEY_LST_FROM_AREA), "");
                part11List.add(line.getViewString(KEY_LST_FROM_LOCATION), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT), "");
                part11List.add(line.getViewString(KEY_LST_CASE_PACK), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_REPLENISHMENT_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_REPLENISHMENT_PIECE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_TO_AREA), "");
                part11List.add(line.getViewString(KEY_LST_TO_LOCATION), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_ReplenishmentResultInput.resetEditRow();
            _lcm_lst_ReplenishmentResultInput.resetHighlight();

            // set input parameters.
            ReplenishListCompleteSCHParams inparam = new ReplenishListCompleteSCHParams();
            inparam.set(ReplenishListCompleteSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(ReplenishListCompleteSCHParams.SELECT_JOB_TYPE, viewState.getObject(ViewStateKeys.SELECT_JOB_TYPE));
            inparam.set(ReplenishListCompleteSCHParams.SETTING_UKEY, viewState.getObject(ViewStateKeys.SETTING_UKEY));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_ReplenishmentResultInput.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ReplenishmentResultInput.getNewLine();
                line.setValue(KEY_LST_JOB_NO, outparam.get(ReplenishListCompleteSCHParams.JOB_NO));
                line.setValue(KEY_LST_FROM_AREA, outparam.get(ReplenishListCompleteSCHParams.FROM_AREA));
                line.setValue(KEY_LST_FROM_LOCATION, outparam.get(ReplenishListCompleteSCHParams.FROM_LOCATION));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(ReplenishListCompleteSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(ReplenishListCompleteSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT, outparam.get(ReplenishListCompleteSCHParams.LOT_NO));
                line.setValue(KEY_LST_CASE_PACK, outparam.get(ReplenishListCompleteSCHParams.CASE_PACK));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(ReplenishListCompleteSCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(ReplenishListCompleteSCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_REPLENISHMENT_CASE_QTY, outparam.get(ReplenishListCompleteSCHParams.REPLENISH_CASE_QTY));
                line.setValue(KEY_LST_REPLENISHMENT_PIECE_QTY, outparam.get(ReplenishListCompleteSCHParams.REPLENISH_PIECE_QTY));
                line.setValue(KEY_LST_TO_AREA, outparam.get(ReplenishListCompleteSCHParams.TO_AREA));
                line.setValue(KEY_LST_TO_LOCATION, outparam.get(ReplenishListCompleteSCHParams.TO_LOCATION));
                lst_ReplenishmentResultInput_SetLineToolTip(line);
                _lcm_lst_ReplenishmentResultInput.add(line);
            }

            // clear.
            btn_Complete.setEnabled(false);
            btn_AllClear.setEnabled(false);
            txt_LRWorkFlag.setValue(null);
            txt_LListWorkNo.setValue(null);
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
    private void btn_AllClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_ReplenishmentResultInput.clear();
        btn_Complete.setEnabled(false);
        btn_AllClear.setEnabled(false);
        txt_LRWorkFlag.setValue(null);
        txt_LListWorkNo.setValue(null);
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
