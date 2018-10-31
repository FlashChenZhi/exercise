// $Id: RFTWorkingModeSettingBusiness.java 7414 2010-03-06 05:36:39Z okayama $
package jp.co.daifuku.wms.system.display.rftworkingmodesetting;

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
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.TerminalType;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsRftNoPullDownModel;
import jp.co.daifuku.wms.system.schedule.RFTWorkingModeSettingSCH;
import jp.co.daifuku.wms.system.schedule.RFTWorkingModeSettingSCHParams;

/**
 * RFTモード切替設定の画面処理を行います。
 *
 * @version $Revision: 7414 $, $Date: 2010-03-06 14:36:39 +0900 (土, 06 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class RFTWorkingModeSettingBusiness
        extends RFTWorkingModeSetting
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    // DFKLOOK start
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK end
    
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_RFTNo */
    private WmsRftNoPullDownModel _pdm_pul_RFTNo;

    /** RadioButtonGroupModel ITFtoJAN */
    private RadioButtonGroup _grp_ITFtoJAN;

    /** RadioButtonGroupModel CasePieseMode */
    private RadioButtonGroup _grp_CasePieseMode;

    /** RadioButtonGroupModel InspectionMode */
    private RadioButtonGroup _grp_InspectionMode;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RFTWorkingModeSettingBusiness()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     *
     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
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
     * @throws Exception 全ての例外を報告します。
     */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        // initialize componenets.
        initializeComponents();

        // process call.
        page_Initialize_Process();
    }

    // DFKLOOK start
    /**
	 * @param e
	 *        ActionEvent
	 * @throws Exception
	 */
	public void page_ConfirmBack(ActionEvent e) throws Exception
	{
		// get event source.
		String eventSource = viewState.getString(_KEY_CONFIRMSOURCE);
		if(eventSource == null)
		{
			return;
		}

		// remove event source.
		viewState.remove(_KEY_CONFIRMSOURCE);

		// check result.
		boolean isExecute = new Boolean(String.valueOf(e.getEventArgs().get(0))).booleanValue();
		if(!isExecute)
		{
			return;
		}

		// choose process.
		if(eventSource.startsWith("btn_Set2_Click"))
		{
			// process call.
			btn_Set2_Click_Process(eventSource);
		}
	}
	// DFKLOOK end

    /**
	 * @param e
	 *        ActionEvent
	 * @throws Exception
	 *         全ての例外を報告します。
	 */
    public void btn_AllCheck_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllCheck_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
     */
    public void btn_AllCheckClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllCheckClear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
     */
    public void btn_Set2_Click(ActionEvent e)
            throws Exception
    {
    	// 引数追加
        // process call.
        btn_Set2_Click_Process(null);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
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
     * @throws Exception 全ての例外を報告します。
     */
    private void initializeComponents()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_RFTNo.
        _pdm_pul_RFTNo = new WmsRftNoPullDownModel(pul_RFTNo, locale, ui);

        // initialize ITFtoJAN.
        _grp_ITFtoJAN = new RadioButtonGroup(new RadioButton[] {
                rdo_ITFtoJAN_ON,
                rdo_ITFtoJAN_OFF
        }, locale);

        // initialize CasePieseMode.
        _grp_CasePieseMode = new RadioButtonGroup(new RadioButton[] {
                rdo_CasePieseMode_Case,
                rdo_CasePieseMode_Piese
        }, locale);

        // initialize InspectionMode.
        _grp_InspectionMode = new RadioButtonGroup(new RadioButton[] {
                rdo_InspectionMode_ON,
                rdo_InspectionMode_OFF
        }, locale);

    }

    /**
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void initializePulldownModels()
            throws Exception
    {
        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // load pul_RFTNo.
            _pdm_pul_RFTNo.init(conn, "", TerminalType.HT, true);

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
     * @throws Exception 全ての例外を報告します。
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(pul_RFTNo);

    }

    /**
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void btn_AllCheck_Click_Process()
            throws Exception
    {
        // all checked.
        chk_WorkKind_NoPlanRetrieval.setChecked(true);
        chk_WorkKind_NoPlanStorage.setChecked(true);
        chk_WorkKind_RelocatinoRetriev.setChecked(true);
        chk_WorkKind_Inventry.setChecked(true);
        chk_WorkKind_RelocatinoStorage.setChecked(true);
        chk_WorkKind_Receiving.setChecked(true);
        chk_WorkKind_Storage.setChecked(true);
        chk_WorkKind_Retrieval.setChecked(true);
        chk_WorkKind_Sort.setChecked(true);
        chk_WorkKind_Shipping.setChecked(true);

    }

    /**
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void btn_AllCheckClear_Click_Process()
            throws Exception
    {
        // all unchecked.
        chk_WorkKind_Receiving.setChecked(false);
        chk_WorkKind_Storage.setChecked(false);
        chk_WorkKind_Retrieval.setChecked(false);
        chk_WorkKind_Sort.setChecked(false);
        chk_WorkKind_Shipping.setChecked(false);
        chk_WorkKind_RelocatinoStorage.setChecked(false);
        chk_WorkKind_RelocatinoRetriev.setChecked(false);
        chk_WorkKind_Inventry.setChecked(false);
        chk_WorkKind_NoPlanStorage.setChecked(false);
        chk_WorkKind_NoPlanRetrieval.setChecked(false);

    }

    /**
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void btn_Set2_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, false);
        pul_RFTNo.validate(this, true);

        // 少なくとも１つ以上のチェックがあるか。
        if (!isChecked())
        {
            // 6403047=チェックボックスにチェックをつけてください。
            message.setMsgResourceKey("6403047");
            return;
        }

        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
        	// 設定しますか?
            this.setConfirm("MSG-W9000", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set2_Click");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RFTWorkingModeSettingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RFTWorkingModeSettingSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RFTWorkingModeSettingSCHParams inparam = new RFTWorkingModeSettingSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(RFTWorkingModeSettingSCHParams.RFT_NO, _pdm_pul_RFTNo.getSelectedValue());
            inparam.set(RFTWorkingModeSettingSCHParams.WORK_KIND_RECEIVING, chk_WorkKind_Receiving.getValue());
            inparam.set(RFTWorkingModeSettingSCHParams.WORK_KIND_STORAGE, chk_WorkKind_Storage.getValue());
            inparam.set(RFTWorkingModeSettingSCHParams.WORK_KIND_RETRIEVAL, chk_WorkKind_Retrieval.getValue());
            inparam.set(RFTWorkingModeSettingSCHParams.WORK_KIND_SORT, chk_WorkKind_Sort.getValue());
            inparam.set(RFTWorkingModeSettingSCHParams.WORK_KIND_SHIPPING, chk_WorkKind_Shipping.getValue());
            inparam.set(RFTWorkingModeSettingSCHParams.WORK_KIND_RELOCATINO_STORAGE,
                    chk_WorkKind_RelocatinoStorage.getValue());
            inparam.set(RFTWorkingModeSettingSCHParams.WORK_KIND_RELOCATINO_RETRIEV,
                    chk_WorkKind_RelocatinoRetriev.getValue());
            inparam.set(RFTWorkingModeSettingSCHParams.WORK_KIND_INVENTRY, chk_WorkKind_Inventry.getValue());
            inparam.set(RFTWorkingModeSettingSCHParams.WORK_KIND_NO_PLAN_STORAGE, chk_WorkKind_NoPlanStorage.getValue());
            inparam.set(RFTWorkingModeSettingSCHParams.WORK_KIND_NO_PLAN_RETRIEVAL,
                    chk_WorkKind_NoPlanRetrieval.getValue());
            inparam.set(RFTWorkingModeSettingSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(RFTWorkingModeSettingSCHParams.ITF_TO_JAN, _grp_ITFtoJAN.getSelectedValue());
            inparam.set(RFTWorkingModeSettingSCHParams.CASE_PIESE_MODE, _grp_CasePieseMode.getSelectedValue());
            inparam.set(RFTWorkingModeSettingSCHParams.INSPECTION_MODE, _grp_InspectionMode.getSelectedValue());

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
            part11List.add(_pdm_pul_RFTNo.getSelectedStringValue(), "");

            if (chk_WorkKind_Receiving.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_WorkKind_Storage.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_WorkKind_Retrieval.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_WorkKind_Sort.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_WorkKind_Shipping.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_WorkKind_RelocatinoRetriev.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_WorkKind_RelocatinoStorage.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_WorkKind_Inventry.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_WorkKind_NoPlanStorage.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_WorkKind_NoPlanRetrieval.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }
            part11List.add(txt_ConsignorCode.getStringValue(), "");
            part11List.add("1", "", rdo_ITFtoJAN_ON.getChecked());
            part11List.add("0", "", rdo_ITFtoJAN_OFF.getChecked());
            part11List.add("1", "", rdo_CasePieseMode_Case.getChecked());
            part11List.add("2", "", rdo_CasePieseMode_Piese.getChecked());
            part11List.add("1", "", rdo_InspectionMode_ON.getChecked());
            part11List.add("0", "", rdo_InspectionMode_OFF.getChecked());
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

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
            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_ConsignorCode.setValue(null);
        rdo_ITFtoJAN_OFF.setChecked(true);
        rdo_CasePieseMode_Piese.setChecked(true);
        rdo_InspectionMode_OFF.setChecked(true);

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

    /**
     * チェックボックスに１つ以上チェックが入っているか、判別します。
     * 
     * @return boolean １つでもチェックがあれば、true。なければ、false。
     */
    private boolean isChecked()
    {
        if (chk_WorkKind_Receiving.getChecked() || chk_WorkKind_Storage.getChecked()
                || chk_WorkKind_Retrieval.getChecked() || chk_WorkKind_Sort.getChecked()
                || chk_WorkKind_Shipping.getChecked() || chk_WorkKind_RelocatinoStorage.getChecked()
                || chk_WorkKind_RelocatinoRetriev.getChecked() || chk_WorkKind_Inventry.getChecked()
                || chk_WorkKind_NoPlanStorage.getChecked() || chk_WorkKind_NoPlanRetrieval.getChecked())
        {
            return true;
        }

        return false;
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
