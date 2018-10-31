package jp.co.daifuku.wms.asrs.display.noplanaddstorage;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.LocationFormatException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.listbox.locationstock.LstAsLocationStockParams;
import jp.co.daifuku.wms.asrs.listbox.stockdetailnbtn.LstAsStockDetailNoBtnParams;
import jp.co.daifuku.wms.asrs.schedule.AsNoPlanAddStorageSCH;
import jp.co.daifuku.wms.asrs.schedule.AsNoPlanAddStorageSCHParams;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;

/**
 * AS/RS 予定外入庫設定(積増)（棚選択）の画面処理を行います。
 * 
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:06:28 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class AsNoPlanAddStorageBusiness
        extends AsNoPlanAddStorage
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
    public AsNoPlanAddStorageBusiness()
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
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        // get event source.
        DialogParameters dialogParams = ((DialogEvent)e).getDialogParameters();
        String eventSource = dialogParams.getParameter(_KEY_POPUPSOURCE);
        if (StringUtil.isBlank(eventSource))
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_LocationSearch_Click"))
        {
            // process call.
            btn_LocationSearch_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_LocationDetail_Click"))
        {
            // process call.
            btn_LocationDetail_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_LocationSearch_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_LocationSearch_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pul_Area_Change(ActionEvent e)
            throws Exception
    {
        // DFKLOOK start
        // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue()));
        // DFKLOOK end
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_LocationDetail_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_LocationDetail_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Next_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Next_Click_Process();
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
            _pdm_pul_Area.init(conn, AreaType.ASRS, StationType.ADD_STORAGE, "", false);

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
        setFocus(txt_ItemCode);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // DFKLOOK start
        _pdm_pul_Area.setSelectedValue(null);
        // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue()));

        // output display.
        if (!StringUtil.isBlank(txt_Location.getText()))
        {
            txt_Location.setValue(WmsFormatter.toDispLocation(viewState.getString(ViewStateKeys.LOCATION_NO),
                    lbl_LocationStyle.getStringValue()));
        }
        // DFKLOOK end
    }

    /**
     *
     * @throws Exception
     */
    private void btn_LocationSearch_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstAsLocationStockParams inparam = new LstAsLocationStockParams();
        inparam.set(LstAsLocationStockParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstAsLocationStockParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstAsLocationStockParams.AREA_NO, _pdm_pul_Area.getSelectedValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_LocationSearch_Click");
        redirect("/asrs/listbox/locationstock/LstAsLocationStock.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_LocationSearch_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstAsLocationStockParams outparam = new LstAsLocationStockParams(dialogParams);
        // DFKLOOK start
        txt_Location.setValue(WmsFormatter.toDispLocation
        		(outparam.getString(LstAsLocationStockParams.LOCATION_NO),	lbl_LocationStyle.getStringValue()));
        // DFKLOOK end
        // set focus.
        setFocus(txt_Location);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_LocationDetail_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstAsStockDetailNoBtnParams inparam = new LstAsStockDetailNoBtnParams();
        // DFKLOOK start
        String loc = WmsFormatter.toParamLocation(txt_Location.getStringValue(), lbl_LocationStyle.getStringValue());
        inparam.set(LstAsStockDetailNoBtnParams.LOCATION_NO, loc);
        // DFKLOOK end
        inparam.set(LstAsStockDetailNoBtnParams.AREA_NO, _pdm_pul_Area.getSelectedValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_LocationDetail_Click");
        redirect("/asrs/listbox/stockdetailnbtn/LstAsStockDetailNoBtn.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_LocationDetail_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // set focus.
        setFocus(txt_Location);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Next_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ItemCode.validate(this, false);
        pul_Area.validate(this, true);
        txt_Location.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsNoPlanAddStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsNoPlanAddStorageSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsNoPlanAddStorageSCHParams inparam = new AsNoPlanAddStorageSCHParams();
            inparam.set(AsNoPlanAddStorageSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(AsNoPlanAddStorageSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            // DFKLOOK start
            String loc = WmsFormatter.toParamLocation(txt_Location.getStringValue(), lbl_LocationStyle.getStringValue());
            inparam.set(AsNoPlanAddStorageSCHParams.LOCATION_NO, loc);
            // DFKLOOK end

            //DFKLOOK 開始
            //            // SCH call.
            //            if (!sch.nextCheck(inparam))
            //            {
            //                message.setMsgResourceKey(sch.getMessage());
            //                return;
            //            }
            //            
            //            // set ViewState parameters.
            //            viewState.setObject(ViewStateKeys.ITEM_CODE, txt_ItemCode.getValue());
            //            viewState.setObject(ViewStateKeys.AREA, _pdm_pul_Area.getSelectedValue());
            //            viewState.setObject(ViewStateKeys.LOCATION, txt_Location.getValue());
            //            viewState.setObject(ViewStateKeys.AREA_NAME, "");
            //            viewState.setObject(ViewStateKeys.PALLET_ID, "");

            //            // forward.
            //            forward("/asrs/noplanaddstorage/AsNoPlanAddStorage2.do");

            Params outParam = sch.initFind(inparam);
            if (outParam != null)
            {
                // set ViewState parameters.
                viewState.setObject(ViewStateKeys.ITEM_CODE, txt_ItemCode.getValue());
                viewState.setObject(ViewStateKeys.AREA_NO, _pdm_pul_Area.getSelectedValue());
                viewState.setObject(ViewStateKeys.LOCATION_NO, loc);
                viewState.setObject(ViewStateKeys.AREA_NAME, outParam.getString(AsNoPlanAddStorageSCHParams.AREA_NAME));
                viewState.setObject(ViewStateKeys.PALLET_ID, outParam.getString(AsNoPlanAddStorageSCHParams.PALLET_ID));

                // forward.
                forward("/asrs/noplanaddstorage/AsNoPlanAddStorage2.do");
            }
            else
            {
                message.setMsgResourceKey(sch.getMessage());
            }
            //DFKLOOK 終了
        }
        //DFKLOOK:ここから修正
        // 棚フォーマットで投げられたExceptionをここでキャッチ
        catch (LocationFormatException ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        //DFKLOOK:ここまで修正
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
        txt_ItemCode.setValue(null);
        _pdm_pul_Area.setSelectedValue(null);
        txt_Location.setValue(null);

        // DFKLOOK start
        // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue()));
        // DFKLOOK end
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
