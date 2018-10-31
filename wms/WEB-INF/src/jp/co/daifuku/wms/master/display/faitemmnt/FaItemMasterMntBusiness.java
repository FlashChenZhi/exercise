// $Id: FaItemMasterMntBusiness.java 7537 2010-03-13 10:37:22Z shibamoto $
package jp.co.daifuku.wms.master.display.faitemmnt;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.List;
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
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.Constants;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsSoftZonePullDownModel;
import jp.co.daifuku.wms.master.display.faitemmnt.FaItemMasterMnt;
import jp.co.daifuku.wms.master.display.faitemmnt.ViewStateKeys;
import jp.co.daifuku.wms.master.schedule.FaItemMasterMntSCH;
import jp.co.daifuku.wms.master.schedule.FaItemMasterMntSCHParams;
import jp.co.daifuku.wms.master.schedule.MasterInParameter;
import jp.co.daifuku.wms.base.controller.PulldownController.SoftZoneType;

/**
 * 商品マスタメンテナンスのクラスです。
 *
 * @version $Revision: 7537 $, $Date:: 2010-03-13 19:37:22 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class FaItemMasterMntBusiness
        extends FaItemMasterMnt
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";
    
    // DFKLOOK:ここから修正    
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK:ここまで修正
    
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_SoftZone */
    private WmsSoftZonePullDownModel _pdm_pul_SoftZone;

    /** RadioButtonGroupModel UsageType */
    private RadioButtonGroup _grp_UsageType;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaItemMasterMntBusiness()
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
        if (eventSource.equals("btn_P_SearchItemCode_Click"))
        {
            // process call.
            btn_P_SearchItemCode_Click_DlgBack(dialogParams);
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
       // DFKLOOK ここから修正
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
       if (eventSource.startsWith("btn_Modify_Click"))
       {
    	   btn_Modify_Click_Process(eventSource);
       }
       else if (eventSource.startsWith("btn_Set2_Click"))
       {
           btn_Set2_Click_Process(eventSource);
       }
       // DFKLOOK ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_P_SearchItemCode_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_P_SearchItemCode_Click_Process();
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
    public void btn_Modify_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        // DFKLOOK ここから修正
        btn_Modify_Click_Process(null);
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Delet_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Delet_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Set2_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK start
        // process call.
        btn_Set2_Click_Process(null);
        // DFKLOOK end
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

        // initialize pul_SoftZone.
        _pdm_pul_SoftZone = new WmsSoftZonePullDownModel(pul_SoftZone, locale, ui);

        // initialize UsageType.
        _grp_UsageType = new RadioButtonGroup(new RadioButton[]{rdo_UsageType_generally, rdo_UsageType_temporarily}, locale);

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

            // load pul_SoftZone.
            _pdm_pul_SoftZone.init(conn, SoftZoneType.MASTER);

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
        setFocus(txt_ItemCode_U);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        txt_ItemCode_U.setValue(null);
        txt_ItemCode_U.setReadOnly(false);
        txt_ItemCode_D.setReadOnly(true);
        txt_ItemCode_D.setValue(null);
        txt_ItemName.setReadOnly(true);
        txt_ItemName.setValue(null);
        pul_SoftZone.setEnabled(false);
        rdo_UsageType_generally.setEnabled(false);
        rdo_UsageType_generally.setChecked(true);
        rdo_UsageType_temporarily.setEnabled(false);
        rdo_UsageType_temporarily.setChecked(false);
        btn_Set2.setEnabled(false);
        btn_Clear.setEnabled(false);
        btn_Set.setEnabled(true);
        btn_Modify.setEnabled(true);
        btn_Delet.setEnabled(true);

        // set focus.
        setFocus(txt_ItemCode_U);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_P_SearchItemCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode_U.getValue());
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_SearchItemCode_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_P_SearchItemCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_ItemCode_U.setValue(outparam.get(LstItemParams.ITEM_CODE));

        // set focus.
        setFocus(txt_ItemCode_U);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ItemCode_U.validate(this, true);
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaItemMasterMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaItemMasterMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaItemMasterMntSCHParams inparam = new FaItemMasterMntSCHParams();
            inparam.set(FaItemMasterMntSCHParams.ITEM_CODE, txt_ItemCode_U.getValue());
            inparam.set(FaItemMasterMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(FaItemMasterMntSCHParams.VS_PROCESS_KEY, MasterInParameter.PROCESS_FLAG_REGIST);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());
            // DFKLOOK ここから修正 
            if (!outparams.isEmpty()) 
            {
                return;
            }
            txt_ItemCode_D.setValue(txt_ItemCode_U.getValue());
            viewState.setObject(ViewStateKeys.VS_PROCESS_KEY, MasterInParameter.PROCESS_FLAG_REGIST);
            viewState.setObject(ViewStateKeys.VS_OPELOG_CLASS, EmConstants.OPELOG_CLASS_REGIST);
            // DFKLOOK ここまで修正
            // clear.
            txt_ItemCode_U.setReadOnly(true);
            btn_P_SearchItemCode.setEnabled(false);
            btn_Modify.setEnabled(false);
            btn_Delet.setEnabled(false);
            btn_Set.setEnabled(false);
            txt_ItemCode_D.setReadOnly(true);
            txt_ItemName.setReadOnly(false);
            txt_ItemName.setValue(null);
            pul_SoftZone.setEnabled(true);
            _pdm_pul_SoftZone.setSelectedValue(null);
            rdo_UsageType_generally.setChecked(true);
            rdo_UsageType_generally.setEnabled(true);
            rdo_UsageType_temporarily.setEnabled(true);
            btn_Set2.setEnabled(true);
            btn_Clear.setEnabled(true);

            // set focus.
            setFocus(txt_ItemName);

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
    // DFKLOOK ここから修正 
    private void btn_Modify_Click_Process(String eventSource)
            throws Exception
    // DFKLOOK ここまで修正
    {
        // input validation.
        txt_ItemCode_U.validate(this, true);
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaItemMasterMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaItemMasterMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaItemMasterMntSCHParams inparam = new FaItemMasterMntSCHParams();
            inparam.set(FaItemMasterMntSCHParams.ITEM_CODE, txt_ItemCode_U.getValue());
            inparam.set(FaItemMasterMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(FaItemMasterMntSCHParams.VS_PROCESS_KEY, MasterInParameter.PROCESS_FLAG_MODIFY);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());
            // DFKLOOK ここから修正 
            if (outparams.isEmpty())
            {
                return;
            }
            // 出庫予定情報データ存在・在庫情報データ存在をチェック
            if (StringUtil.isBlank(eventSource) && sch.check(inparam))
            {
                if (StringUtil.isBlank(sch.getDispMessage()))
                {
                    // 対象データが使用されていた場合messageを返却
                    message.setMsgResourceKey(sch.getMessage());
                    return;
                }
                else
                {
                    this.setConfirm(sch.getDispMessage(), false, true);
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Modify_Click");
                    return;
                }
            }
            // DFKLOOK ここまで修正
            // output display.
            for (Params outparam : outparams)
            {
                txt_ItemCode_D.setValue(outparam.get(FaItemMasterMntSCHParams.ITEM_CODE));
                txt_ItemName.setValue(outparam.get(FaItemMasterMntSCHParams.ITEM_NAME));
                _pdm_pul_SoftZone.setSelectedValue(outparam.get(FaItemMasterMntSCHParams.SOFT_ZONE));
                _grp_UsageType.setSelectedValue(outparam.get(FaItemMasterMntSCHParams.TEMPORARY_TYPE));
                viewState.setObject(ViewStateKeys.VS_PROCESS_KEY, MasterInParameter.PROCESS_FLAG_MODIFY);
                viewState.setObject(ViewStateKeys.VS_ITEM_NAME, outparam.get(FaItemMasterMntSCHParams.ITEM_NAME));
                viewState.setObject(ViewStateKeys.VS_SOFT_ZONE, outparam.get(FaItemMasterMntSCHParams.SOFT_ZONE));
                viewState.setObject(ViewStateKeys.VS_TEMPORARY_TYPE, outparam.get(FaItemMasterMntSCHParams.TEMPORARY_TYPE));
                viewState.setObject(ViewStateKeys.VS_OPELOG_CLASS, EmConstants.OPELOG_CLASS_MODIFY);
            }

            // clear.
            txt_ItemCode_U.setReadOnly(true);
            btn_P_SearchItemCode.setEnabled(false);
            btn_Set.setEnabled(false);
            btn_Modify.setEnabled(false);
            btn_Delet.setEnabled(false);
            txt_ItemCode_D.setReadOnly(true);
            txt_ItemName.setReadOnly(false);
            pul_SoftZone.setEnabled(true);
            rdo_UsageType_generally.setEnabled(true);
            rdo_UsageType_temporarily.setEnabled(true);
            btn_Set2.setEnabled(true);
            btn_Clear.setEnabled(true);

            // set focus.
            setFocus(txt_ItemName);

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
    private void btn_Delet_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ItemCode_U.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaItemMasterMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaItemMasterMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaItemMasterMntSCHParams inparam = new FaItemMasterMntSCHParams();
            inparam.set(FaItemMasterMntSCHParams.ITEM_CODE, txt_ItemCode_U.getValue());
            inparam.set(FaItemMasterMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(FaItemMasterMntSCHParams.VS_PROCESS_KEY, MasterInParameter.PROCESS_FLAG_DELETE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());
            // DFKLOOK ここから修正
            if (outparams.isEmpty())
            {
                return;
            }
            // 出庫予定情報データ存在・在庫情報データ存在をチェック
            if (sch.check(inparam))
            {
                // 対象データが使用されていた場合messageを返却
            	message.setMsgResourceKey(sch.getMessage());
                return;
            }
            // DFKLOOK ここまで修正
            // output display.
            for (Params outparam : outparams)
            {
                txt_ItemCode_D.setValue(outparam.get(FaItemMasterMntSCHParams.ITEM_CODE));
                txt_ItemName.setValue(outparam.get(FaItemMasterMntSCHParams.ITEM_NAME));
                _pdm_pul_SoftZone.setSelectedValue(outparam.get(FaItemMasterMntSCHParams.SOFT_ZONE));
                viewState.setObject(ViewStateKeys.VS_PROCESS_KEY, MasterInParameter.PROCESS_FLAG_DELETE);
                viewState.setObject(ViewStateKeys.VS_OPELOG_CLASS, EmConstants.OPELOG_CLASS_DELETE);
            }

            // clear.
            txt_ItemCode_U.setReadOnly(true);
            btn_Set.setEnabled(false);
            btn_Modify.setEnabled(false);
            btn_Delet.setEnabled(false);
            txt_ItemCode_D.setReadOnly(true);
            txt_ItemName.setReadOnly(true);
            pul_SoftZone.setEnabled(false);
            rdo_UsageType_generally.setEnabled(false);
            rdo_UsageType_temporarily.setEnabled(false);
            btn_Set2.setEnabled(true);
            btn_Clear.setEnabled(true);
            btn_P_SearchItemCode.setEnabled(false);

            // set focus.
            setFocus(btn_Set2);

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
    // DFKLOOK start
    private void btn_Set2_Click_Process(String eventSource)
            throws Exception
    // DFKLOOK end
    {
        // input validation.
        txt_ItemCode_D.validate(this, true);
        txt_ItemName.validate(this, false);
        pul_SoftZone.validate(this, true);
        rdo_UsageType_generally.validate(false);

        // DFKLOOK:ここから修正        
        if (StringUtil.isBlank(eventSource))
        {
        	setFocus(txt_ItemName);
			// show confirm message. 設定しますか？
			this.setConfirm("MSG-W9000", false, true);
			viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set2_Click");
			return;
        }
        // DFKLOOK:ここまで修正
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaItemMasterMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaItemMasterMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaItemMasterMntSCHParams inparam = new FaItemMasterMntSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(FaItemMasterMntSCHParams.ITEM_CODE, txt_ItemCode_D.getValue());
            inparam.set(FaItemMasterMntSCHParams.ITEM_NAME, txt_ItemName.getValue());
            inparam.set(FaItemMasterMntSCHParams.SOFT_ZONE, _pdm_pul_SoftZone.getSelectedValue());
            inparam.set(FaItemMasterMntSCHParams.TEMPORARY_TYPE, _grp_UsageType.getSelectedValue());
            inparam.set(FaItemMasterMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(FaItemMasterMntSCHParams.VS_PROCESS_KEY, viewState.getObject(ViewStateKeys.VS_PROCESS_KEY));

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
            part11List.add(txt_ItemCode_D.getStringValue(), "");
            part11List.add(txt_ItemName.getStringValue(), "");
            part11List.add(_pdm_pul_SoftZone.getSelectedStringValue(), "");
            part11List.add("0", "", rdo_UsageType_generally.getChecked());
            part11List.add("1", "", rdo_UsageType_temporarily.getChecked());

			// DFKLOOK start 修正の場合
            part11List.add(viewState.getString(ViewStateKeys.VS_ITEM_NAME), "", viewState
					.getString(ViewStateKeys.VS_PROCESS_KEY).equals(MasterInParameter.PROCESS_FLAG_MODIFY));
			part11List.add(viewState.getString(ViewStateKeys.VS_SOFT_ZONE), "", viewState
					.getString(ViewStateKeys.VS_PROCESS_KEY).equals(MasterInParameter.PROCESS_FLAG_MODIFY));
            part11List.add(viewState.getString(ViewStateKeys.VS_TEMPORARY_TYPE), "", viewState
					.getString(ViewStateKeys.VS_PROCESS_KEY).equals(MasterInParameter.PROCESS_FLAG_MODIFY));
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(viewState.getObject(ViewStateKeys.VS_OPELOG_CLASS)), part11List);
			// DFKLOOK end

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // clear.
            txt_ItemCode_U.setValue(null);
            txt_ItemCode_U.setReadOnly(false);
            btn_P_SearchItemCode.setEnabled(true);
            btn_Set.setEnabled(true);
            btn_Modify.setEnabled(true);
            btn_Delet.setEnabled(true);
            txt_ItemCode_D.setValue(null);
            txt_ItemCode_D.setReadOnly(true);
            txt_ItemName.setValue(null);
            txt_ItemName.setReadOnly(true);
            _pdm_pul_SoftZone.setSelectedValue(null);
            pul_SoftZone.setEnabled(false);
            rdo_UsageType_generally.setChecked(true);
            rdo_UsageType_generally.setEnabled(false);
            rdo_UsageType_temporarily.setChecked(false);
            rdo_UsageType_temporarily.setEnabled(false);
            btn_Set2.setEnabled(false);
            btn_Clear.setEnabled(false);

            // set focus.
            setFocus(txt_ItemCode_U);

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
        txt_ItemCode_D.setValue(null);
        txt_ItemCode_D.setReadOnly(true);
        txt_ItemName.setValue(null);
        txt_ItemName.setReadOnly(true);
        _pdm_pul_SoftZone.setSelectedValue(null);
        pul_SoftZone.setEnabled(false);
        rdo_UsageType_generally.setChecked(true);
        rdo_UsageType_generally.setEnabled(false);
        rdo_UsageType_temporarily.setChecked(false);
        rdo_UsageType_temporarily.setEnabled(false);
        btn_Set2.setEnabled(false);
        btn_Clear.setEnabled(false);
        btn_Set.setEnabled(true);
        btn_Modify.setEnabled(true);
        btn_Delet.setEnabled(true);
        btn_P_SearchItemCode.setEnabled(true);
        txt_ItemCode_U.setReadOnly(false);

        // set focus.
        setFocus(txt_ItemCode_U);

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
