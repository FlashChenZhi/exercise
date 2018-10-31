// $Id: AsStoringStatusBussiness.java 6927 2010-01-28 05:02:49Z okayama $
package jp.co.daifuku.wms.asrs.display.storingstatus;

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
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
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
import jp.co.daifuku.Constants;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.storingstatus.AsStoringStatus;
import jp.co.daifuku.wms.asrs.schedule.AsStoringStatusSCH;
import jp.co.daifuku.wms.asrs.schedule.AsStoringStatusSCHParams;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAislePullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 6927 $, $Date:: 2010-01-28 14:02:49 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class AsStoringStatusBussiness
        extends AsStoringStatus
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_AsStoringStatus(LST_RM_NO) */
    private static final ListCellKey KEY_LST_RM_NO = new ListCellKey("LST_RM_NO", new StringCellColumn(), true, false);

    /** lst_AsStoringStatus(LST_LOAD_SIZE) */
    private static final ListCellKey KEY_LST_LOAD_SIZE = new ListCellKey("LST_LOAD_SIZE", new StringCellColumn(), true, false);

    /** lst_AsStoringStatus(LST_SOFTZONE_NAME) */
    private static final ListCellKey KEY_LST_SOFTZONE_NAME = new ListCellKey("LST_SOFTZONE_NAME", new StringCellColumn(), true, false);

    /** lst_AsStoringStatus(LST_OCCUPIED) */
    private static final ListCellKey KEY_LST_OCCUPIED = new ListCellKey("LST_OCCUPIED", new NumberCellColumn(0), true, false);

    /** lst_AsStoringStatus(LST_EMPTY) */
    private static final ListCellKey KEY_LST_EMPTY = new ListCellKey("LST_EMPTY", new NumberCellColumn(0), true, false);

    /** lst_AsStoringStatus(LST_EMPTY_PALLET) */
    private static final ListCellKey KEY_LST_EMPTY_PALLET = new ListCellKey("LST_EMPTY_PALLET", new NumberCellColumn(0), true, false);

    /** lst_AsStoringStatus(LST_ERROR) */
    private static final ListCellKey KEY_LST_ERROR = new ListCellKey("LST_ERROR", new NumberCellColumn(0), true, false);

    /** lst_AsStoringStatus(LST_PROHIBITED) */
    private static final ListCellKey KEY_LST_PROHIBITED = new ListCellKey("LST_PROHIBITED", new NumberCellColumn(0), true, false);

    /** lst_AsStoringStatus(LST_UNREACHABLE) */
    private static final ListCellKey KEY_LST_UNREACHABLE = new ListCellKey("LST_UNREACHABLE", new NumberCellColumn(0), true, false);

    /** lst_AsStoringStatus(LST_TOTAL) */
    private static final ListCellKey KEY_LST_TOTAL = new ListCellKey("LST_TOTAL", new NumberCellColumn(0), true, false);

    /** lst_AsStoringStatus(LST_OCCUPANCY_RATE) */
    private static final ListCellKey KEY_LST_OCCUPANCY_RATE = new ListCellKey("LST_OCCUPANCY_RATE", new StringCellColumn(), true, false);

    /** lst_AsStoringStatus keys */
    private static final ListCellKey[] LST_ASSTORINGSTATUS_KEYS = {
        KEY_LST_RM_NO,
        KEY_LST_LOAD_SIZE,
        KEY_LST_SOFTZONE_NAME,
        KEY_LST_OCCUPIED,
        KEY_LST_EMPTY,
        KEY_LST_EMPTY_PALLET,
        KEY_LST_ERROR,
        KEY_LST_PROHIBITED,
        KEY_LST_UNREACHABLE,
        KEY_LST_TOTAL,
        KEY_LST_OCCUPANCY_RATE,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** PullDownModel pul_RMNo */
    private WmsAislePullDownModel _pdm_pul_RMNo;

    /** ListCellModel lst_AsStoringStatus */
    private ScrollListCellModel _lcm_lst_AsStoringStatus;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsStoringStatusBussiness()
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

        // initialize pul_RMNo.
        _pdm_pul_RMNo = new WmsAislePullDownModel(pul_RMNo, locale, ui);
        _pdm_pul_RMNo.setParent(_pdm_pul_Area);

        // initialize lst_AsStoringStatus.
        _lcm_lst_AsStoringStatus = new ScrollListCellModel(lst_AsStoringStatus, LST_ASSTORINGSTATUS_KEYS, locale);
        _lcm_lst_AsStoringStatus.setToolTipVisible(KEY_LST_RM_NO, false);
        _lcm_lst_AsStoringStatus.setToolTipVisible(KEY_LST_LOAD_SIZE, false);
        _lcm_lst_AsStoringStatus.setToolTipVisible(KEY_LST_SOFTZONE_NAME, false);
        _lcm_lst_AsStoringStatus.setToolTipVisible(KEY_LST_OCCUPIED, false);
        _lcm_lst_AsStoringStatus.setToolTipVisible(KEY_LST_EMPTY, false);
        _lcm_lst_AsStoringStatus.setToolTipVisible(KEY_LST_EMPTY_PALLET, false);
        _lcm_lst_AsStoringStatus.setToolTipVisible(KEY_LST_ERROR, false);
        _lcm_lst_AsStoringStatus.setToolTipVisible(KEY_LST_PROHIBITED, false);
        _lcm_lst_AsStoringStatus.setToolTipVisible(KEY_LST_UNREACHABLE, false);
        _lcm_lst_AsStoringStatus.setToolTipVisible(KEY_LST_TOTAL, false);
        _lcm_lst_AsStoringStatus.setToolTipVisible(KEY_LST_OCCUPANCY_RATE, false);

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
            _pdm_pul_Area.init(conn, AreaType.ASRS, StationType.ALL, "", false);

            // load pul_RMNo.
            _pdm_pul_RMNo.init(conn, "", true);

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
    private void lst_AsStoringStatus_SetLineToolTip(ListCellLine line)
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
        pul_RMNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsStoringStatusSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsStoringStatusSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsStoringStatusSCHParams inparam = new AsStoringStatusSCHParams();
            inparam.set(AsStoringStatusSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsStoringStatusSCHParams.RM_NO, _pdm_pul_RMNo.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_AsStoringStatus.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_AsStoringStatus.getNewLine();
                line.setValue(KEY_LST_RM_NO, outparam.get(AsStoringStatusSCHParams.RM_NO));
                line.setValue(KEY_LST_LOAD_SIZE, outparam.get(AsStoringStatusSCHParams.LOAD_SIZE));
                line.setValue(KEY_LST_SOFTZONE_NAME, outparam.get(AsStoringStatusSCHParams.SOFTZONE_NAME));
                line.setValue(KEY_LST_OCCUPIED, outparam.get(AsStoringStatusSCHParams.OCCUPIED));
                line.setValue(KEY_LST_EMPTY, outparam.get(AsStoringStatusSCHParams.EMPTY));
                line.setValue(KEY_LST_EMPTY_PALLET, outparam.get(AsStoringStatusSCHParams.EMPTY_PALLET));
                line.setValue(KEY_LST_ERROR, outparam.get(AsStoringStatusSCHParams.ERROR));
                line.setValue(KEY_LST_PROHIBITED, outparam.get(AsStoringStatusSCHParams.RESTRICTED));
                line.setValue(KEY_LST_UNREACHABLE, outparam.get(AsStoringStatusSCHParams.INACCESSIBLE));
                line.setValue(KEY_LST_TOTAL, outparam.get(AsStoringStatusSCHParams.TOTAL));
                line.setValue(KEY_LST_OCCUPANCY_RATE, outparam.get(AsStoringStatusSCHParams.OCCUPANCY_RATE));
                lst_AsStoringStatus_SetLineToolTip(line);
                _lcm_lst_AsStoringStatus.add(line);
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
