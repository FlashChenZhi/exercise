// $Id: AsOperationCycleBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.wms.asrs.display.operationcycle;

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
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
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
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.operationcycle.AsOperationCycle;
import jp.co.daifuku.wms.asrs.schedule.AsOperationCycleSCH;
import jp.co.daifuku.wms.asrs.schedule.AsOperationCycleSCHParams;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class AsOperationCycleBusiness
        extends AsOperationCycle
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ASRSOperationResult(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO = new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_ASRSOperationResult(LST_AISLE_NO) */
    private static final ListCellKey KEY_LST_AISLE_NO = new ListCellKey("LST_AISLE_NO", new StringCellColumn(), true, false);

    /** lst_ASRSOperationResult(LST_TOTAL_COUNT) */
    private static final ListCellKey KEY_LST_TOTAL_COUNT = new ListCellKey("LST_TOTAL_COUNT", new NumberCellColumn(0), true, false);

    /** lst_ASRSOperationResult(LST_STORAGE_COUNT) */
    private static final ListCellKey KEY_LST_STORAGE_COUNT = new ListCellKey("LST_STORAGE_COUNT", new NumberCellColumn(0), true, false);

    /** lst_ASRSOperationResult(LST_RETRIEVAL_COUNT) */
    private static final ListCellKey KEY_LST_RETRIEVAL_COUNT = new ListCellKey("LST_RETRIEVAL_COUNT", new NumberCellColumn(0), true, false);

    /** lst_ASRSOperationResult keys */
    private static final ListCellKey[] LST_ASRSOPERATIONRESULT_KEYS = {
        KEY_LST_AREA_NO,
        KEY_LST_AISLE_NO,
        KEY_LST_TOTAL_COUNT,
        KEY_LST_STORAGE_COUNT,
        KEY_LST_RETRIEVAL_COUNT,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** ListCellModel lst_ASRSOperationResult */
    private ScrollListCellModel _lcm_lst_ASRSOperationResult;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsOperationCycleBusiness()
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

        // initialize lst_ASRSOperationResult.
        _lcm_lst_ASRSOperationResult = new ScrollListCellModel(lst_ASRSOperationResult, LST_ASRSOPERATIONRESULT_KEYS, locale);
        _lcm_lst_ASRSOperationResult.setToolTipVisible(KEY_LST_AREA_NO, false);
        _lcm_lst_ASRSOperationResult.setToolTipVisible(KEY_LST_AISLE_NO, false);
        _lcm_lst_ASRSOperationResult.setToolTipVisible(KEY_LST_TOTAL_COUNT, false);
        _lcm_lst_ASRSOperationResult.setToolTipVisible(KEY_LST_STORAGE_COUNT, false);
        _lcm_lst_ASRSOperationResult.setToolTipVisible(KEY_LST_RETRIEVAL_COUNT, false);
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
            _pdm_pul_Area.init(conn, AreaType.ASRS, StationType.ALL, "", true);
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
    private void lst_ASRSOperationResult_SetLineToolTip(ListCellLine line)
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
        txt_SearchDate.validate(this, false);
        txt_SearchTime.validate(this, false);
        txt_ToSearchDate.validate(this, false);
        txt_ToSearchTime.validate(this, false);
        pul_Area.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsOperationCycleSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsOperationCycleSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsOperationCycleSCHParams inparam = new AsOperationCycleSCHParams();
            inparam.set(AsOperationCycleSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsOperationCycleSCHParams.FROM_SEARCH_DATE, txt_SearchDate.getValue());
            inparam.set(AsOperationCycleSCHParams.FROM_SEARCH_TIME, txt_SearchTime.getValue());
            inparam.set(AsOperationCycleSCHParams.TO_SEARCH_DATE, txt_ToSearchDate.getValue());
            inparam.set(AsOperationCycleSCHParams.TO_SEARCH_TIME, txt_ToSearchTime.getValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_ASRSOperationResult.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ASRSOperationResult.getNewLine();
                txt_InSearchDate.setValue(outparam.get(AsOperationCycleSCHParams.IN_SEARCH_DATE));
                txt_InToSearchDate.setValue(outparam.get(AsOperationCycleSCHParams.IN_TO_SEARCH_DATE));
                line.setValue(KEY_LST_AREA_NO, outparam.get(AsOperationCycleSCHParams.AREA_NO));
                line.setValue(KEY_LST_AISLE_NO, outparam.get(AsOperationCycleSCHParams.AISLE_NO));
                line.setValue(KEY_LST_TOTAL_COUNT, outparam.get(AsOperationCycleSCHParams.TOTAL_COUNT));
                line.setValue(KEY_LST_STORAGE_COUNT, outparam.get(AsOperationCycleSCHParams.STORAGE_COUNT));
                line.setValue(KEY_LST_RETRIEVAL_COUNT, outparam.get(AsOperationCycleSCHParams.RETRIEVAL_COUNT));
                lst_ASRSOperationResult_SetLineToolTip(line);
                _lcm_lst_ASRSOperationResult.add(line);
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
        txt_SearchDate.setValue(null);
        txt_SearchTime.setValue(null);
        txt_ToSearchDate.setValue(null);
        txt_ToSearchTime.setValue(null);
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
