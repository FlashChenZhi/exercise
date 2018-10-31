// $Id: PCTAreaProgressGraphBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.pcart.retrieval.display.pctareaprogressgraph;

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
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.retrieval.display.pctareaprogressgraph.PCTAreaProgressGraph;
import jp.co.daifuku.pcart.retrieval.display.pctareaprogressgraph.ViewStateKeys;
import jp.co.daifuku.pcart.retrieval.schedule.PCTAreaProgressGraphSCH;
import jp.co.daifuku.pcart.retrieval.schedule.PCTAreaProgressGraphSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class PCTAreaProgressGraphBusiness
        extends PCTAreaProgressGraph
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_PCTAreaProgress1(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO = new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_PCTAreaProgress1(LST_ZONE_NO) */
    private static final ListCellKey KEY_LST_ZONE_NO = new ListCellKey("LST_ZONE_NO", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress1(LST_LINE_COUNT) */
    private static final ListCellKey KEY_LST_LINE_COUNT = new ListCellKey("LST_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress1(LST_LOT_COUNT) */
    private static final ListCellKey KEY_LST_LOT_COUNT = new ListCellKey("LST_LOT_COUNT", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress2(LST_AREA_NO_2) */
    private static final ListCellKey KEY_LST_AREA_NO_2 = new ListCellKey("LST_AREA_NO_2", new AreaCellColumn(), true, false);

    /** lst_PCTAreaProgress2(LST_ZONE_NO_2) */
    private static final ListCellKey KEY_LST_ZONE_NO_2 = new ListCellKey("LST_ZONE_NO_2", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress2(LST_LINE_COUNT_2) */
    private static final ListCellKey KEY_LST_LINE_COUNT_2 = new ListCellKey("LST_LINE_COUNT_2", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress2(LST_LOT_COUNT_2) */
    private static final ListCellKey KEY_LST_LOT_COUNT_2 = new ListCellKey("LST_LOT_COUNT_2", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress3(LST_AREA_NO_3) */
    private static final ListCellKey KEY_LST_AREA_NO_3 = new ListCellKey("LST_AREA_NO_3", new AreaCellColumn(), true, false);

    /** lst_PCTAreaProgress3(LST_ZONE_NO_3) */
    private static final ListCellKey KEY_LST_ZONE_NO_3 = new ListCellKey("LST_ZONE_NO_3", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress3(LST_LINE_COUNT_3) */
    private static final ListCellKey KEY_LST_LINE_COUNT_3 = new ListCellKey("LST_LINE_COUNT_3", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress3(LST_LOT_COUNT_3) */
    private static final ListCellKey KEY_LST_LOT_COUNT_3 = new ListCellKey("LST_LOT_COUNT_3", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress4(LST_AREA_NO_4) */
    private static final ListCellKey KEY_LST_AREA_NO_4 = new ListCellKey("LST_AREA_NO_4", new AreaCellColumn(), true, false);

    /** lst_PCTAreaProgress4(LST_ZONE_NO_4) */
    private static final ListCellKey KEY_LST_ZONE_NO_4 = new ListCellKey("LST_ZONE_NO_4", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress4(LST_LINE_COUNT_4) */
    private static final ListCellKey KEY_LST_LINE_COUNT_4 = new ListCellKey("LST_LINE_COUNT_4", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress4(LST_LOT_COUNT_4) */
    private static final ListCellKey KEY_LST_LOT_COUNT_4 = new ListCellKey("LST_LOT_COUNT_4", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress1 keys */
    private static final ListCellKey[] LST_PCTAREAPROGRESS1_KEYS = {
        KEY_LST_AREA_NO,
        KEY_LST_ZONE_NO,
        KEY_LST_LINE_COUNT,
        KEY_LST_LOT_COUNT,
    };

    /** lst_PCTAreaProgress2 keys */
    private static final ListCellKey[] LST_PCTAREAPROGRESS2_KEYS = {
        KEY_LST_AREA_NO_2,
        KEY_LST_ZONE_NO_2,
        KEY_LST_LINE_COUNT_2,
        KEY_LST_LOT_COUNT_2,
    };

    /** lst_PCTAreaProgress3 keys */
    private static final ListCellKey[] LST_PCTAREAPROGRESS3_KEYS = {
        KEY_LST_AREA_NO_3,
        KEY_LST_ZONE_NO_3,
        KEY_LST_LINE_COUNT_3,
        KEY_LST_LOT_COUNT_3,
    };

    /** lst_PCTAreaProgress4 keys */
    private static final ListCellKey[] LST_PCTAREAPROGRESS4_KEYS = {
        KEY_LST_AREA_NO_4,
        KEY_LST_ZONE_NO_4,
        KEY_LST_LINE_COUNT_4,
        KEY_LST_LOT_COUNT_4,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** RadioButtonGroupModel ProgressDisplay */
    private RadioButtonGroup _grp_ProgressDisplay;

    /** ListCellModel lst_PCTAreaProgress1 */
    private ListCellModel _lcm_lst_PCTAreaProgress1;

    /** ListCellModel lst_PCTAreaProgress2 */
    private ListCellModel _lcm_lst_PCTAreaProgress2;

    /** ListCellModel lst_PCTAreaProgress3 */
    private ListCellModel _lcm_lst_PCTAreaProgress3;

    /** ListCellModel lst_PCTAreaProgress4 */
    private ListCellModel _lcm_lst_PCTAreaProgress4;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTAreaProgressGraphBusiness()
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
    public void btn_PrevPage_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PrevPage_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_NextPage_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_NextPage_Click_Process();
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

        // initialize ProgressDisplay.
        _grp_ProgressDisplay = new RadioButtonGroup(new RadioButton[]{rdo_Auto, rdo_Manual}, locale);

        // initialize lst_PCTAreaProgress1.
        _lcm_lst_PCTAreaProgress1 = new ListCellModel(lst_PCTAreaProgress1, LST_PCTAREAPROGRESS1_KEYS, locale);
        _lcm_lst_PCTAreaProgress1.setToolTipVisible(KEY_LST_AREA_NO, true);
        _lcm_lst_PCTAreaProgress1.setToolTipVisible(KEY_LST_ZONE_NO, true);
        _lcm_lst_PCTAreaProgress1.setToolTipVisible(KEY_LST_LINE_COUNT, true);
        _lcm_lst_PCTAreaProgress1.setToolTipVisible(KEY_LST_LOT_COUNT, true);

        // initialize lst_PCTAreaProgress2.
        _lcm_lst_PCTAreaProgress2 = new ListCellModel(lst_PCTAreaProgress2, LST_PCTAREAPROGRESS2_KEYS, locale);
        _lcm_lst_PCTAreaProgress2.setToolTipVisible(KEY_LST_AREA_NO_2, true);
        _lcm_lst_PCTAreaProgress2.setToolTipVisible(KEY_LST_ZONE_NO_2, true);
        _lcm_lst_PCTAreaProgress2.setToolTipVisible(KEY_LST_LINE_COUNT_2, true);
        _lcm_lst_PCTAreaProgress2.setToolTipVisible(KEY_LST_LOT_COUNT_2, true);

        // initialize lst_PCTAreaProgress3.
        _lcm_lst_PCTAreaProgress3 = new ListCellModel(lst_PCTAreaProgress3, LST_PCTAREAPROGRESS3_KEYS, locale);
        _lcm_lst_PCTAreaProgress3.setToolTipVisible(KEY_LST_AREA_NO_3, true);
        _lcm_lst_PCTAreaProgress3.setToolTipVisible(KEY_LST_ZONE_NO_3, true);
        _lcm_lst_PCTAreaProgress3.setToolTipVisible(KEY_LST_LINE_COUNT_3, true);
        _lcm_lst_PCTAreaProgress3.setToolTipVisible(KEY_LST_LOT_COUNT_3, true);

        // initialize lst_PCTAreaProgress4.
        _lcm_lst_PCTAreaProgress4 = new ListCellModel(lst_PCTAreaProgress4, LST_PCTAREAPROGRESS4_KEYS, locale);
        _lcm_lst_PCTAreaProgress4.setToolTipVisible(KEY_LST_AREA_NO_4, true);
        _lcm_lst_PCTAreaProgress4.setToolTipVisible(KEY_LST_ZONE_NO_4, true);
        _lcm_lst_PCTAreaProgress4.setToolTipVisible(KEY_LST_LINE_COUNT_4, true);
        _lcm_lst_PCTAreaProgress4.setToolTipVisible(KEY_LST_LOT_COUNT_4, true);
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
    private void lst_PCTAreaProgress1_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-P0043", KEY_LST_AREA_NO);
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTAreaProgress2_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-P0043", KEY_LST_AREA_NO_2);
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTAreaProgress3_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-P0043", KEY_LST_AREA_NO_3);
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTAreaProgress4_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-P0043", KEY_LST_AREA_NO_4);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_ConsignorCode);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAreaProgressGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAreaProgressGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAreaProgressGraphSCHParams inparam = new PCTAreaProgressGraphSCHParams();
            inparam.set(PCTAreaProgressGraphSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTAreaProgressGraphSCHParams.CONSIGNOR_NAME, txt_ConsignorName.getValue());
            inparam.set(PCTAreaProgressGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTAreaProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTAreaProgress1.getNewLine();
                line.setValue(KEY_LST_AREA_NO, outparam.get(PCTAreaProgressGraphSCHParams.AREA_NO));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(PCTAreaProgressGraphSCHParams.ZONE_NO));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(PCTAreaProgressGraphSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTAreaProgressGraphSCHParams.LOT_COUNT));
                txt_ProgressRate1.setValue(outparam.get(PCTAreaProgressGraphSCHParams.PROGRESS_RATE));
                lst_PCTAreaProgress1_SetLineToolTip(line);
                _lcm_lst_PCTAreaProgress1.add(line);
            }

            // clear.
            rdo_Auto.setChecked(true);
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
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_BatchSeqNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAreaProgressGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAreaProgressGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAreaProgressGraphSCHParams inparam = new PCTAreaProgressGraphSCHParams();
            inparam.set(PCTAreaProgressGraphSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTAreaProgressGraphSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(PCTAreaProgressGraphSCHParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
            inparam.set(PCTAreaProgressGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);
            inparam.set(PCTAreaProgressGraphSCHParams.BUTTON_CONTROL_FLAG, "");

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTAreaProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTAreaProgress1.getNewLine();
                line.setValue(KEY_LST_AREA_NO, outparam.get(PCTAreaProgressGraphSCHParams.AREA_NO));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(PCTAreaProgressGraphSCHParams.ZONE_NO));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(PCTAreaProgressGraphSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTAreaProgressGraphSCHParams.LOT_COUNT));
                txt_ProgressRate1.setValue(outparam.get(PCTAreaProgressGraphSCHParams.PROGRESS_RATE));
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                viewState.setObject(ViewStateKeys.VS_BATCH_NO, txt_BatchNo.getValue());
                viewState.setObject(ViewStateKeys.VS_BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
                lst_PCTAreaProgress1_SetLineToolTip(line);
                _lcm_lst_PCTAreaProgress1.add(line);
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
    private void btn_PrevPage_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_BatchSeqNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAreaProgressGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAreaProgressGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAreaProgressGraphSCHParams inparam = new PCTAreaProgressGraphSCHParams();
            inparam.set(PCTAreaProgressGraphSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTAreaProgressGraphSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.VS_BATCH_NO));
            inparam.set(PCTAreaProgressGraphSCHParams.BATCH_SEQ_NO, viewState.getObject(ViewStateKeys.VS_BATCH_SEQ_NO));
            inparam.set(PCTAreaProgressGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_PREVIOUS_PAGE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTAreaProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTAreaProgress1.getNewLine();
                line.setValue(KEY_LST_AREA_NO, outparam.get(PCTAreaProgressGraphSCHParams.AREA_NO));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(PCTAreaProgressGraphSCHParams.ZONE_NO));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(PCTAreaProgressGraphSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTAreaProgressGraphSCHParams.LOT_COUNT));
                txt_ProgressRate1.setValue(outparam.get(PCTAreaProgressGraphSCHParams.PROGRESS_RATE));
                lst_PCTAreaProgress1_SetLineToolTip(line);
                _lcm_lst_PCTAreaProgress1.add(line);
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
    private void btn_NextPage_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_BatchSeqNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAreaProgressGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAreaProgressGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAreaProgressGraphSCHParams inparam = new PCTAreaProgressGraphSCHParams();
            inparam.set(PCTAreaProgressGraphSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTAreaProgressGraphSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.VS_BATCH_NO));
            inparam.set(PCTAreaProgressGraphSCHParams.BATCH_SEQ_NO, viewState.getObject(ViewStateKeys.VS_BATCH_SEQ_NO));
            inparam.set(PCTAreaProgressGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_NEXT_PAGE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_PCTAreaProgress1.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTAreaProgress1.getNewLine();
                line.setValue(KEY_LST_AREA_NO, outparam.get(PCTAreaProgressGraphSCHParams.AREA_NO));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(PCTAreaProgressGraphSCHParams.ZONE_NO));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(PCTAreaProgressGraphSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTAreaProgressGraphSCHParams.LOT_COUNT));
                txt_ProgressRate1.setValue(outparam.get(PCTAreaProgressGraphSCHParams.PROGRESS_RATE));
                lst_PCTAreaProgress1_SetLineToolTip(line);
                _lcm_lst_PCTAreaProgress1.add(line);
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
