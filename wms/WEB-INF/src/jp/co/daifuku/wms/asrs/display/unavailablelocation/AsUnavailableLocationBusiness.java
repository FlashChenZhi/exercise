// $Id: AsUnavailableLocationBusiness.java 7404 2010-03-05 13:25:51Z shibamoto $
package jp.co.daifuku.wms.asrs.display.unavailablelocation;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.LocationFormatException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.Constants;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.listbox.fastockdetailnbtn.FaLstAsStockDetailNoBtnParams;
import jp.co.daifuku.wms.asrs.listbox.stockdetail.LstAsStockDetailParams;
import jp.co.daifuku.wms.asrs.schedule.AsUnavailableLocationSCH;
import jp.co.daifuku.wms.asrs.schedule.AsUnavailableLocationSCHParams;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;

/**
 * AS/RS禁止棚設定の画面処理を行います。
 *
 * @version $Revision: 7404 $, $Date:: 2010-03-05 22:25:51 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class AsUnavailableLocationBusiness
        extends AsUnavailableLocation
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    // DFKLOOK start
    /** key */
    private static final String _KEY_PULFORM = "_KEY_PULFORM";
    // DFKLOOK end

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area_Event */
    private WmsAreaPullDownModel _pdm_pul_Area_Event;

    /** RadioButtonGroupModel ASRSLocationStatus */
    private RadioButtonGroup _grp_ASRSLocationStatus;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsUnavailableLocationBusiness()
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
        if (eventSource.equals("btn_LocationDetail_Click"))
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
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
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
        if (eventSource.startsWith("btn_Set_Click"))
        {
            // process call.
            btn_Set_Click_Process(eventSource);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_Terminate(ActionEvent e)
            throws Exception
    {
        //DFKLOOK start
        this.getSession().setAttribute(_KEY_PULFORM, pul_Area_Event.getSelectedValue());
        // DFKLOOK end
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pul_Area_Event_Change(ActionEvent e)
            throws Exception
    {
        // DFKLOOK start
        lbl_In_JavaSet.setValue(SuperLocationHolder.getInstance().getLocationFormat(
                _pdm_pul_Area_Event.getSelectedValue()));
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
    public void btn_Set_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Set_Click_Process(null);
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

        // initialize pul_Area_Event.
        _pdm_pul_Area_Event = new WmsAreaPullDownModel(pul_Area_Event, locale, ui);

        // initialize ASRSLocationStatus.
        _grp_ASRSLocationStatus = new RadioButtonGroup(new RadioButton[]{rdo_ASRSLocationStatus_Unavail, rdo_ASRSLocationStatus_Availab}, locale);

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

            // load pul_Area_Event.
            _pdm_pul_Area_Event.init(conn, AreaType.ASRS, StationType.ALL, "", false);

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
        setFocus(pul_Area_Event);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        rdo_ASRSLocationStatus_Unavail.setChecked(true);

        // DFKLOOK start
        _pdm_pul_Area_Event.setSelectedIndex(0);

        lbl_In_JavaSet.setValue(SuperLocationHolder.getInstance().getLocationFormat(
                _pdm_pul_Area_Event.getSelectedValue()));
        // DFKLOOK end

    }

    /**
     *
     * @throws Exception
     */
    private void btn_LocationDetail_Click_Process()
            throws Exception
    {
        
        // DFKLOOK start
        
        pul_Area_Event.validate(this, true);
        txt_Location.validate(this, true);
        rdo_ASRSLocationStatus_Unavail.validate(false);

        Connection conn = null;
        try
        {
            conn = ConnectionManager.getRequestConnection(this);
            WarenaviSystemController sysController = new WarenaviSystemController(conn, getClass());

            if (sysController.isFaDaEnabled())
            {
                // dialog parameters set.
                FaLstAsStockDetailNoBtnParams inparam = new FaLstAsStockDetailNoBtnParams();
                // DFKLOOK start
                String loStyle =
                        SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area_Event.getSelectedValue());
                String loc = WmsFormatter.toParamLocation(txt_Location.getText(), loStyle);
                
                System.out.println(loc);
                inparam.set(FaLstAsStockDetailNoBtnParams.LOCATION_NO, loc);
                inparam.set(FaLstAsStockDetailNoBtnParams.AREA_NO, _pdm_pul_Area_Event.getSelectedValue());
                // DFKLOOK
                inparam.set(FaLstAsStockDetailNoBtnParams.PALLET_ID, null);
                // ここまで

                // show dialog.
                ForwardParameters forwardParam = inparam.toForwardParameters();
                forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_LocationDetail_Click");

                redirect("/asrs/listbox/fastockdetailnbtn/FaLstAsStockDetailNoBtn.do", forwardParam, "/Progress.do");
            }
            else if (!sysController.isFaDaEnabled())
            {
                // dialog parameters set.
                LstAsStockDetailParams inparam = new LstAsStockDetailParams();
                // DFKLOOK start
                String loStyle =
                        SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area_Event.getSelectedValue());
                String loc = WmsFormatter.toParamLocation(txt_Location.getText(), loStyle);
                inparam.set(LstAsStockDetailParams.LOCATION_NO, loc);
                inparam.set(LstAsStockDetailParams.AREA_NO, _pdm_pul_Area_Event.getSelectedValue());
                // DFKLOOK
                inparam.set(LstAsStockDetailParams.PALLET_ID, null);
                // ここまで

                // show dialog.
                ForwardParameters forwardParam = inparam.toForwardParameters();
                forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_LocationDetail_Click");
                
                // TODO 画面遷移先を修正する必要あり
                redirect("/asrs/listbox/stockdetailnbtn/LstAsStockDetailNoBtn.do", forwardParam, "/Progress.do");
            }
        }
        // 棚フォーマットで投げられたExceptionをここでキャッチ
        catch (LocationFormatException ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
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
     * @param dialogParams DialogParameters
     */
    private void btn_LocationDetail_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // DFKLOOK start
        // output display.
        Connection conn = null;
        try
        {
            conn = ConnectionManager.getRequestConnection(this);
            WarenaviSystemController sysController = new WarenaviSystemController(conn, getClass());
            
            if(!sysController.isFaDaEnabled())
            {
                LstAsStockDetailParams outparam = new LstAsStockDetailParams(dialogParams);
                txt_Location.setValue(outparam.get(LstAsStockDetailParams.LOCATION_NO));
            }   
            
            // set focus.
            setFocus(txt_Location);
        }
        finally
        {
            DBUtil.close(conn);
        }
        // DFKLOOK end
    }

    /**
     *
     * @param eventSource
     * @throws Exception
     */
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        txt_Location.validate(this, true);
        rdo_ASRSLocationStatus_Unavail.validate(false);
        pul_Area_Event.validate(this, true);

        // DFKLOOK start
        String loc = null;
        try
        {
	        loc = WmsFormatter.toParamLocation(txt_Location.getText(), lbl_In_JavaSet.getStringValue());
        }
        // 棚フォーマットで投げられたExceptionをここでキャッチ
        catch (LocationFormatException ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            return;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            return;
        }
        
        if(StringUtil.isBlank(eventSource))
        {
        	// 設定しますか？
            this.setConfirm("MSG-W9000", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsUnavailableLocationSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsUnavailableLocationSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsUnavailableLocationSCHParams inparam = new AsUnavailableLocationSCHParams(ui);
            inparam.setProcessFlag(ProcessFlag.UPDATE);
            inparam.set(AsUnavailableLocationSCHParams.AREA, _pdm_pul_Area_Event.getSelectedValue());
            // DFKLOOK start
            //inparam.set(AsUnavailableLocationSCHParams.LOCATION, txt_Location.getValue());
            inparam.set(AsUnavailableLocationSCHParams.LOCATION, loc);
            // DFKLOOK end

            //棚状態 DFKLOOK
            if (rdo_ASRSLocationStatus_Unavail.getChecked())
            {
                //使用不可                
                inparam.set(AsUnavailableLocationSCHParams.ASRS_LOCATION_STATUS, SystemDefine.PROHIBITION_FLAG_NG);
            }
            else
            {
                //使用可
                inparam.set(AsUnavailableLocationSCHParams.ASRS_LOCATION_STATUS, SystemDefine.PROHIBITION_FLAG_OK);
            }
            // ここまで

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(_pdm_pul_Area_Event.getSelectedStringValue(), "");
            part11List.add(txt_Location.getStringValue(), "");
            part11List.add(SystemDefine.PROHIBITION_FLAG_NG, "", rdo_ASRSLocationStatus_Unavail.getChecked());
            part11List.add(SystemDefine.PROHIBITION_FLAG_OK, "", rdo_ASRSLocationStatus_Availab.getChecked());
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        _pdm_pul_Area_Event.setSelectedValue(null);
        txt_Location.setValue(null);
        rdo_ASRSLocationStatus_Unavail.setChecked(true);

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
