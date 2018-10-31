// $Id: PCTUndoneWorkBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.pcart.retrieval.display.pctundonework;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.base.controller.PCTConsignorController;
import jp.co.daifuku.pcart.retrieval.schedule.PCTUndoneWorkSCH;
import jp.co.daifuku.pcart.retrieval.schedule.PCTUndoneWorkSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;

/**
 * 残作業一覧の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date:: 2011-07-06 09:52:24 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class PCTUndoneWorkBusiness
        extends PCTUndoneWork
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_UndoneWorkList(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO =
            new ListCellKey("LST_AREA_NO", new StringCellColumn(), true, false);

    /** lst_UndoneWorkList(LST_START_WORK_ZONE) */
    private static final ListCellKey KEY_LST_START_WORK_ZONE =
            new ListCellKey("LST_START_WORK_ZONE", new StringCellColumn(), true, false);

    /** lst_UndoneWorkList(LST_PROGRESS_RATE) */
    private static final ListCellKey KEY_LST_PROGRESS_RATE =
            new ListCellKey("LST_PROGRESS_RATE", new StringCellColumn(), true, false);

    /** lst_UndoneWorkList(LST_PLAN_ORDER_COUNT) */
    private static final ListCellKey KEY_LST_PLAN_ORDER_COUNT =
            new ListCellKey("LST_PLAN_ORDER_COUNT", new NumberCellColumn(0), true, false);

    /** lst_UndoneWorkList(LST_COMPLETE_ORDER_COUNT) */
    private static final ListCellKey KEY_LST_COMPLETE_ORDER_COUNT =
            new ListCellKey("LST_COMPLETE_ORDER_COUNT", new NumberCellColumn(0), true, false);

    /** lst_UndoneWorkList(LST_SURPLUS_ORDER_COUNT) */
    private static final ListCellKey KEY_LST_SURPLUS_ORDER_COUNT =
            new ListCellKey("LST_SURPLUS_ORDER_COUNT", new NumberCellColumn(0), true, false);

    /** lst_UndoneWorkList kyes */
    private static final ListCellKey[] LST_UNDONEWORKLIST_KEYS = {
            KEY_LST_AREA_NO,
            KEY_LST_START_WORK_ZONE,
            KEY_LST_PROGRESS_RATE,
            KEY_LST_PLAN_ORDER_COUNT,
            KEY_LST_COMPLETE_ORDER_COUNT,
            KEY_LST_SURPLUS_ORDER_COUNT,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** RadioButtonGroupModel ProgressDisplay */
    private RadioButtonGroup _grp_ProgressDisplay;

    /** ListCellModel lst_UndoneWorkList */
    private ListCellModel _lcm_lst_UndoneWorkList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTUndoneWorkBusiness()
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
    public void page_ClientPull(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // input validation.
        txt_ConsignorCode.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTUndoneWorkSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTUndoneWorkSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTUndoneWorkSCHParams inparam = new PCTUndoneWorkSCHParams();
            inparam.set(PCTUndoneWorkSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTUndoneWorkSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_UndoneWorkList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_UndoneWorkList.getNewLine();
                line.setValue(KEY_LST_AREA_NO, outparam.get(PCTUndoneWorkSCHParams.AREA_NO));
                line.setValue(KEY_LST_START_WORK_ZONE, outparam.get(PCTUndoneWorkSCHParams.START_WORK_ZONE));

                // 「%」を付与
                line.setValue(KEY_LST_PROGRESS_RATE,
                        String.valueOf(WmsFormatter.toProductionRate(outparam.getBigDecimal(
                                (PCTUndoneWorkSCHParams.PROGRESS_RATE)).doubleValue())));

                line.setValue(KEY_LST_PLAN_ORDER_COUNT, outparam.get(PCTUndoneWorkSCHParams.PLAN_ORDER_COUNT));
                line.setValue(KEY_LST_COMPLETE_ORDER_COUNT, outparam.get(PCTUndoneWorkSCHParams.COMPLETE_ORDER_COUNT));
                line.setValue(KEY_LST_SURPLUS_ORDER_COUNT, outparam.get(PCTUndoneWorkSCHParams.SURPLUS_ORDER_COUNT));
                lst_UndoneWorkList_SetLineToolTip(line);
                _lcm_lst_UndoneWorkList.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }
        }
        finally
        {
            DBUtil.close(conn);
        }
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ConsignorCode_EnterKey(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // get locale.
        Connection conn = null;
        PCTConsignorController conController = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            conController = new PCTConsignorController(conn, this.getClass());
            txt_ConsignorName.setText(conController.getConsignorName(txt_ConsignorCode.getText(),
                    InParameter.SEARCH_TABLE_MASTER));

            setRegularTransmission();

            setFocus(pul_Area);
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
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Auto_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        rdo_Auto.setChecked(true);
        setRegularTransmission();

        // 必須入力チェック
        txt_ConsignorCode.validate(this, true);
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Manual_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        rdo_Manual.setChecked(true);
        setRegularTransmission();

        // 必須入力チェック
        txt_ConsignorCode.validate(this, true);
        // DFKLOOK:ここまで修正
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
    // DFKLOOK:ここから修正
    /**
     * 自動更新か手動更新かを切り替えます。
     * 
     * @throws Exception
     *             全ての例外を報告します。
     */
    protected void setRegularTransmission()
            throws Exception
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

    // DFKLOOK:ここまで修正

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

        // initialize ProgressDisplay.
        _grp_ProgressDisplay = new RadioButtonGroup(new RadioButton[] {
                rdo_Auto,
                rdo_Manual
        }, locale);

        // initialize lst_UndoneWorkList.
        _lcm_lst_UndoneWorkList = new ListCellModel(lst_UndoneWorkList, LST_UNDONEWORKLIST_KEYS, locale);
        _lcm_lst_UndoneWorkList.setToolTipVisible(KEY_LST_AREA_NO, false);
        _lcm_lst_UndoneWorkList.setToolTipVisible(KEY_LST_START_WORK_ZONE, false);
        _lcm_lst_UndoneWorkList.setToolTipVisible(KEY_LST_PROGRESS_RATE, false);
        _lcm_lst_UndoneWorkList.setToolTipVisible(KEY_LST_PLAN_ORDER_COUNT, false);
        _lcm_lst_UndoneWorkList.setToolTipVisible(KEY_LST_COMPLETE_ORDER_COUNT, false);
        _lcm_lst_UndoneWorkList.setToolTipVisible(KEY_LST_SURPLUS_ORDER_COUNT, false);

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
            _pdm_pul_Area.init(conn, AreaType.FLOOR, StationType.ALL, "", true);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_UndoneWorkList_SetLineToolTip(ListCellLine line)
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
        PCTUndoneWorkSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTUndoneWorkSCH(conn, this.getClass(), locale, ui);

            // DFKLOOK:ここから修正          
            // set input parameters.
            PCTUndoneWorkSCHParams inparam = new PCTUndoneWorkSCHParams();
            inparam.set(PCTUndoneWorkSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());

            // SCH call.
            Params initOutparam = sch.initFind(inparam);

            if (initOutparam == null)
            {
                rdo_Manual.setChecked(true);
                setRegularTransmission();
            }
            else
            {
                txt_ConsignorCode.setValue(initOutparam.get(PCTUndoneWorkSCHParams.CONSIGNOR_CODE));
                txt_ConsignorName.setValue(initOutparam.get(PCTUndoneWorkSCHParams.CONSIGNOR_NAME));

                rdo_Auto.setChecked(true);

                // 荷主コードがあれば、リストセルを初期表示する
                if (!StringUtil.isBlank(txt_ConsignorCode.getText()))
                {
                    // set input parameters.
                    inparam = new PCTUndoneWorkSCHParams();
                    //DFKLOOK:ここまで修正

                    inparam.set(PCTUndoneWorkSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                    inparam.set(PCTUndoneWorkSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());

                    // SCH call.
                    List<Params> outparams = sch.query(inparam);
                    message.setMsgResourceKey(sch.getMessage());

                    // output display.
                    _lcm_lst_UndoneWorkList.clear();
                    for (Params outparam : outparams)
                    {
                        ListCellLine line = _lcm_lst_UndoneWorkList.getNewLine();
                        line.setValue(KEY_LST_AREA_NO, outparam.get(PCTUndoneWorkSCHParams.AREA_NO));
                        line.setValue(KEY_LST_START_WORK_ZONE, outparam.get(PCTUndoneWorkSCHParams.START_WORK_ZONE));

                        // DFKLOOK:ここから修正
                        // 「%」を付与
                        line.setValue(KEY_LST_PROGRESS_RATE,
                                String.valueOf(WmsFormatter.toProductionRate(outparam.getBigDecimal(
                                        (PCTUndoneWorkSCHParams.PROGRESS_RATE)).doubleValue())));
                        // DFKLOOK:ここまで修正

                        line.setValue(KEY_LST_PLAN_ORDER_COUNT, outparam.get(PCTUndoneWorkSCHParams.PLAN_ORDER_COUNT));
                        line.setValue(KEY_LST_COMPLETE_ORDER_COUNT,
                                outparam.get(PCTUndoneWorkSCHParams.COMPLETE_ORDER_COUNT));
                        line.setValue(KEY_LST_SURPLUS_ORDER_COUNT,
                                outparam.get(PCTUndoneWorkSCHParams.SURPLUS_ORDER_COUNT));
                        lst_UndoneWorkList_SetLineToolTip(line);
                        _lcm_lst_UndoneWorkList.add(line);
                    }
                    // DFKLOOK:ここから修正
                }
                // clear.
                rdo_Auto.setChecked(true);

                setRegularTransmission();
            }
            // DFKLOOK:ここまで修正
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }
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
        pul_Area.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTUndoneWorkSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTUndoneWorkSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTUndoneWorkSCHParams inparam = new PCTUndoneWorkSCHParams();
            inparam.set(PCTUndoneWorkSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTUndoneWorkSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_UndoneWorkList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_UndoneWorkList.getNewLine();
                line.setValue(KEY_LST_AREA_NO, outparam.get(PCTUndoneWorkSCHParams.AREA_NO));
                line.setValue(KEY_LST_START_WORK_ZONE, outparam.get(PCTUndoneWorkSCHParams.START_WORK_ZONE));

                // DFKLOOK:ここから修正
                // 「%」を付与
                line.setValue(KEY_LST_PROGRESS_RATE,
                        String.valueOf(WmsFormatter.toProductionRate(outparam.getBigDecimal(
                                (PCTUndoneWorkSCHParams.PROGRESS_RATE)).doubleValue())));
                // DFKLOOK:ここまで修正

                line.setValue(KEY_LST_PLAN_ORDER_COUNT, outparam.get(PCTUndoneWorkSCHParams.PLAN_ORDER_COUNT));
                line.setValue(KEY_LST_COMPLETE_ORDER_COUNT, outparam.get(PCTUndoneWorkSCHParams.COMPLETE_ORDER_COUNT));
                line.setValue(KEY_LST_SURPLUS_ORDER_COUNT, outparam.get(PCTUndoneWorkSCHParams.SURPLUS_ORDER_COUNT));
                lst_UndoneWorkList_SetLineToolTip(line);
                _lcm_lst_UndoneWorkList.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }
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
