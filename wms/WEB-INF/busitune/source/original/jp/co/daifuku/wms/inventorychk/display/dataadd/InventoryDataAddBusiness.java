// $Id: InventoryDataAddBusiness.java 7449 2010-03-08 04:24:04Z okayama $
package jp.co.daifuku.wms.inventorychk.display.dataadd;

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
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.inventorychk.display.dataadd.InventoryDataAdd;
import jp.co.daifuku.wms.inventorychk.display.dataadd.InventoryDataAddParams;
import jp.co.daifuku.wms.inventorychk.display.dataadd.ViewStateKeys;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryDataAddSCH;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryDataAddSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7449 $, $Date:: 2010-03-08 13:24:04 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class InventoryDataAddBusiness
        extends InventoryDataAdd
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public InventoryDataAddBusiness()
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
    public void txt_ItemCode_EnterKey(ActionEvent e)
            throws Exception
    {
        // process call.
        txt_ItemCode_EnterKey_Process();
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
    public void btn_Close_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_Click_Process();
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
            _pdm_pul_Area.init(conn, AreaType.FLOOR_AND_TEMP_AND_RECEIVE, StationType.ALL, "", false);
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
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_ListWorkNo);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // output display.
        InventoryDataAddParams requestParam = new InventoryDataAddParams(request);
        txt_ListWorkNo.setValue(requestParam.get(InventoryDataAddParams.LIST_WORK_NO));
        _pdm_pul_Area.setSelectedValue(requestParam.get(InventoryDataAddParams.AREA_NO));
        viewState.setObject(ViewStateKeys.MASTER, requestParam.get(InventoryDataAddParams.MASTER));
        viewState.setObject(ViewStateKeys.CONDITION, requestParam.get(InventoryDataAddParams.CONDITION));
        viewState.setObject(ViewStateKeys.LIST_WORK_NO, requestParam.get(InventoryDataAddParams.LIST_WORK_NO));
        viewState.setObject(ViewStateKeys.AREA_NO, requestParam.get(InventoryDataAddParams.AREA_NO));
        viewState.setObject(ViewStateKeys.ITEM_CODE, requestParam.get(InventoryDataAddParams.ITEM_CODE));
        viewState.setObject(ViewStateKeys.LOCATION_FROM, requestParam.get(InventoryDataAddParams.LOCATION_FROM));
        viewState.setObject(ViewStateKeys.LOCATION_TO, requestParam.get(InventoryDataAddParams.LOCATION_TO));
    }

    /**
     *
     * @throws Exception
     */
    private void txt_ItemCode_EnterKey_Process()
            throws Exception
    {
        // input validation.
        txt_ItemCode.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        InventoryDataAddSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new InventoryDataAddSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            InventoryDataAddSCHParams inparam = new InventoryDataAddSCHParams();
            inparam.set(InventoryDataAddSCHParams.ITEM_CODE, txt_ItemCode.getValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_ItemName.setValue(outparam.get(InventoryDataAddSCHParams.ITEM_NAME));
                txt_EnteringQty.setValue(outparam.get(InventoryDataAddSCHParams.ENTERING_QTY));
                txt_ListWorkNo.setValue(outparam.get(InventoryDataAddSCHParams.LIST_WORK_NO));
                _pdm_pul_Area.setSelectedValue(outparam.get(InventoryDataAddSCHParams.AREA_NO));
                txt_Location.setValue(outparam.get(InventoryDataAddSCHParams.LOCATION_NO));
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
    private void btn_Set_Click_Process()
            throws Exception
    {
        // output parameter.
        InventoryDataAddParams outparam = new InventoryDataAddParams();
        outparam.set(InventoryDataAddParams.LIST_WORK_NO, txt_ListWorkNo.getValue());
        outparam.set(InventoryDataAddParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
        outparam.set(InventoryDataAddParams.LOCATION_NO, txt_Location.getValue());
        outparam.set(InventoryDataAddParams.ITEM_CODE, txt_ItemCode.getValue());
        outparam.set(InventoryDataAddParams.ITEM_NAME, txt_ItemName.getValue());
        outparam.set(InventoryDataAddParams.LOT_NO, txt_LotNo.getValue());
        outparam.set(InventoryDataAddParams.ENTERING_QTY, txt_EnteringQty.getValue());
        outparam.set(InventoryDataAddParams.RESULT_CASE_QTY, txt_StockCaseQty.getValue());
        outparam.set(InventoryDataAddParams.RESULT_PIECE_QTY, txt_StockPieceQty.getValue());
        outparam.set(InventoryDataAddParams.NEWDATE_FLAG, "1");
        outparam.set(InventoryDataAddParams.STOCK_CASE_QTY, 0);
        outparam.set(InventoryDataAddParams.STOCK_PIECE_QTY, 0);

        ForwardParameters forwardParam = outparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, viewState.getString(_KEY_POPUPSOURCE));
        parentRedirect(forwardParam);
        dispose();
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_ListWorkNo.setValue(null);
        _pdm_pul_Area.setSelectedValue(null);
        txt_Location.setValue(null);
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        txt_LotNo.setValue(null);
        txt_EnteringQty.setValue(null);
        txt_StockCaseQty.setValue(null);
        txt_StockPieceQty.setValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Close_Click_Process()
            throws Exception
    {
        parentRedirect(null);
        dispose();
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
