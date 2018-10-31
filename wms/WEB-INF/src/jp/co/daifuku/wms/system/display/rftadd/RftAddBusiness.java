// $Id: RftAddBusiness.java 7520 2010-03-13 05:49:44Z okayama $
package jp.co.daifuku.wms.system.display.rftadd;

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
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
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
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsTerminalTypePullDownModel;
import jp.co.daifuku.wms.system.schedule.RftAddSCH;
import jp.co.daifuku.wms.system.schedule.RftAddSCHParams;

/**
 * RFT追加設定の画面処理を行います。
 *
 * @version $Revision: 7520 $, $Date: 2010-03-13 14:49:44 +0900 (土, 13 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
@SuppressWarnings("serial")
public class RftAddBusiness
        extends RftAdd
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_RftAssort */
    private WmsTerminalTypePullDownModel _pdm_pul_RftAssort;

    /** RadioButtonGroupModel language */
    private RadioButtonGroup _grp_language;

    /** PullDownModel pul_Model */
    private DefaultPullDownModel _pdm_pul_Model;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RftAddBusiness()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面が初期表示されたときに呼ばれます。
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

        // process call.
        page_Load_Process();
    }

    /**
     * 各コントロールイベント呼び出し前に呼ばれます。
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

    /**
     *
     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
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
        if (eventSource.equals("btn_Entry_Click"))
        {
            // process call.
            btn_Entry_Click_Process(false);
        }
        // DFKLOOK start
        if (eventSource.startsWith("btn_Set_Click"))
        {
            // process call.
            btn_Set_Click_Process(eventSource);
        }
        // DFKLOOK end
    }

    /**
     * 登録ボタンをクリックした時に呼ばれます。
     *
     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
     */
    public void btn_Entry_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Entry_Click_Process(true);
    }

    /**
     * 修正ボタンをクリックした時に呼ばれます。
     *
     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
     */
    public void btn_Modify_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Modify_Click_Process();
    }

    /**
     * 削除ボタンをクリックした時に呼ばれます。
     *
     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
     */
    public void btn_Delete_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Delete_Click_Process();
    }

    /**
     * 設定ボタンをクリックした時に呼ばれます。
     *
     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
     */
    public void btn_Set_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Set_Click_Process(null);
    }

    /**
     * クリアボタンをクリックした時に呼ばれます。
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
     * コンポーネントの初期化処理を行います。
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void initializeComponents()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_RftAssort.
        _pdm_pul_RftAssort = new WmsTerminalTypePullDownModel(pul_RftAssort, locale, ui);

        // initialize language.
        _grp_language = new RadioButtonGroup(new RadioButton[] {
                rdo_Japanese,
                rdo_English,
                rdo_Chinese
        }, locale);

        // initialize pul_Model.
        _pdm_pul_Model = new DefaultPullDownModel(pul_Model, locale, ui);

    }

    /**
     * プルダウンの初期化を行います。
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

            // load pul_RftAssort.
            _pdm_pul_RftAssort.init(conn, "");

            // load pul_Model.
            _pdm_pul_Model.init(conn);

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
        setFocus(txt_MachineNumber);

    }

    /**
     * 画面の初期化処理を行います。
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        txt_IpAddress.setReadOnly(true);
        rdo_Japanese.setChecked(true);
        rdo_English.setChecked(false);
        rdo_Chinese.setChecked(false);
        rdo_Japanese.setEnabled(false);
        rdo_English.setEnabled(false);
        rdo_Chinese.setEnabled(false);
        pul_Model.setEnabled(false);
        chk_WorkKind_TC_Receiving.setEnabled(false);
        chk_WorkKind_DC_Receiving.setEnabled(false);
        chk_WorkKind_Storage_Receiving.setEnabled(false);
        chk_WorkKind_Storage.setEnabled(false);
        chk_WorkKind_Order_Retrieval.setEnabled(false);
        chk_WorkKind_Sort.setEnabled(false);
        chk_WorkKind_Shipping_Pick.setEnabled(false);
        chk_WorkKind_Shipping_Loading.setEnabled(false);
        chk_WorkKind_NoPlanStorage.setEnabled(false);
        chk_WorkKind_NoPlanRetrieval.setEnabled(false);
        chk_WorkKind_Inventry.setEnabled(false);
        chk_WorkKind_RelocatinoRetriev.setEnabled(false);
        chk_WorkKind_RelocatinoStorage.setEnabled(false);
        btn_Set.setEnabled(false);
        btn_Clear.setEnabled(false);

    }

    /**
     * 登録処理を開始します。
     *
     * @param confirm
     * @throws Exception 全ての例外を報告します。
     */
    private void btn_Entry_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        txt_MachineNumber.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RftAddSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RftAddSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RftAddSCHParams inparam = new RftAddSCHParams();
            inparam.set(RftAddSCHParams.MACHINE_NUMBER, txt_MachineNumber.getValue());

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // DFKLOOK ここから修正
            // SCH call.
            /*if (confirm && !sch.check(inparam, inparams))
             {
             if (StringUtil.isBlank(sch.getDispMessage()))
             {
             // show message.
             message.setMsgResourceKey(sch.getMessage());
             return;
             }
             else
             {
             // show confirm message.
             this.setConfirm(sch.getDispMessage(), false, true);
             viewState.setString(_KEY_CONFIRMSOURCE, "btn_Entry_Click");
             return;
             }
             }*/

            if (!sch.check(inparam))
            {
                message.setMsgResourceKey(sch.getMessage());
                return;
            }
            // DFKLOOK ここまで修正
            // output display.
            viewState.setObject(ViewStateKeys.BTN_KEY, "CREATE");

            // DFKLOOK ここから修正
            if (SystemDefine.TERMINAL_TYPE_HT.equals(pul_RftAssort.getSelectedValue()))
            {
                // clear.
                txt_MachineNumber.setReadOnly(true);
                pul_RftAssort.setEnabled(false);
                btn_Entry.setEnabled(false);
                btn_Modify.setEnabled(false);
                btn_Delete.setEnabled(false);
                txt_IpAddress.setReadOnly(false);
                rdo_Japanese.setEnabled(true);
                rdo_English.setEnabled(true);
                rdo_Chinese.setEnabled(true);
                pul_Model.setEnabled(true);
                chk_WorkKind_TC_Receiving.setEnabled(true);
                chk_WorkKind_DC_Receiving.setEnabled(true);
                chk_WorkKind_Storage_Receiving.setEnabled(true);
                chk_WorkKind_Storage.setEnabled(true);
                chk_WorkKind_Order_Retrieval.setEnabled(true);
                chk_WorkKind_Sort.setEnabled(true);
                chk_WorkKind_Shipping_Pick.setEnabled(true);
                chk_WorkKind_Shipping_Loading.setEnabled(true);
                chk_WorkKind_NoPlanStorage.setEnabled(true);
                chk_WorkKind_NoPlanRetrieval.setEnabled(true);
                chk_WorkKind_Inventry.setEnabled(true);
                chk_WorkKind_RelocatinoRetriev.setEnabled(true);
                chk_WorkKind_RelocatinoStorage.setEnabled(true);
                btn_Set.setEnabled(true);
                btn_Clear.setEnabled(true);
            }
            // 
            else
            {
                txt_MachineNumber.setReadOnly(true);
                pul_RftAssort.setEnabled(false);
                btn_Entry.setEnabled(false);
                btn_Modify.setEnabled(false);
                btn_Delete.setEnabled(false);
                txt_IpAddress.setReadOnly(false);
                btn_Set.setEnabled(true);
                btn_Clear.setEnabled(true);
            }
            // DFKLOOK ここまで修正
            // set focus.
            setFocus(txt_IpAddress);

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
     * 修正処理を開始します。
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void btn_Modify_Click_Process()
            throws Exception
    {
        // input validation.
        txt_MachineNumber.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RftAddSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RftAddSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RftAddSCHParams inparam = new RftAddSCHParams();
            inparam.set(RftAddSCHParams.MACHINE_NUMBER, txt_MachineNumber.getValue());
            inparam.set(RftAddSCHParams.RFT_ASSORT, _pdm_pul_RftAssort.getSelectedValue());
            
            // DFKLOOK ここから修正
            if (!sch.checkrft(inparam))
            {
                message.setMsgResourceKey(sch.getMessage());
                return;
            }
            // DFKLOOK ここまで修正
            
            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // DFKLOOK ここから修正
            // 修正対象データなし
            if (outparams.isEmpty())
            {
                message.setMsgResourceKey(sch.getMessage());
                return;
            }
            // DFKLOOK ここまで修正

            // output display.
            for (Params outparam : outparams)
            {
                txt_IpAddress.setValue(outparam.get(RftAddSCHParams.IP_ADDRESS));
                _grp_language.setSelectedValue(outparam.get(RftAddSCHParams.LANGUAGE));
                _pdm_pul_Model.setSelectedValue(outparam.get(RftAddSCHParams.MODEL));
                chk_WorkKind_TC_Receiving.setValue(outparam.get(RftAddSCHParams.WORK_KIND_TC_RECEIVING));
                chk_WorkKind_DC_Receiving.setValue(outparam.get(RftAddSCHParams.WORK_KIND_DC_RECEIVING));
                chk_WorkKind_Storage_Receiving.setValue(outparam.get(RftAddSCHParams.WORK_KIND_STORAGE_RECEIVING));
                chk_WorkKind_Storage.setValue(outparam.get(RftAddSCHParams.WORK_KIND_STORAGE));
                chk_WorkKind_Order_Retrieval.setValue(outparam.get(RftAddSCHParams.WORK_KIND_ORDER_RETRIEVAL));
                chk_WorkKind_Sort.setValue(outparam.get(RftAddSCHParams.WORK_KIND_SORT));
                chk_WorkKind_Shipping_Pick.setValue(outparam.get(RftAddSCHParams.WORK_KIND_SHIPPING_PICK));
                chk_WorkKind_Shipping_Loading.setValue(outparam.get(RftAddSCHParams.WORK_KIND_SHIPPING_LOADING));
                chk_WorkKind_NoPlanStorage.setValue(outparam.get(RftAddSCHParams.WORK_KIND_NO_PLAN_STORAGE));
                chk_WorkKind_NoPlanRetrieval.setValue(outparam.get(RftAddSCHParams.WORK_KIND_NO_PLAN_RETRIEVAL));
                chk_WorkKind_Inventry.setValue(outparam.get(RftAddSCHParams.WORK_KIND_INVENTRY));
                chk_WorkKind_RelocatinoRetriev.setValue(outparam.get(RftAddSCHParams.WORK_KIND_RELOCATINO_RETRIEV));
                chk_WorkKind_RelocatinoStorage.setValue(outparam.get(RftAddSCHParams.WORK_KIND_RELOCATINO_STORAGE));
                viewState.setObject(ViewStateKeys.BTN_KEY, "MODIFY");
                viewState.setObject(ViewStateKeys.VS_RFT_ASSORT, outparam.get(RftAddSCHParams.RFT_ASSORT));
            }
            
            // DFKLOOK ここから修正
            if (SystemDefine.TERMINAL_TYPE_HT.equals(pul_RftAssort.getSelectedValue()))
            {
                // DFKLOOK ここまで修正
                // clear.
                txt_MachineNumber.setReadOnly(true);
                pul_RftAssort.setEnabled(false);
                btn_Entry.setEnabled(false);
                btn_Modify.setEnabled(false);
                btn_Delete.setEnabled(false);
                txt_IpAddress.setReadOnly(false);
                rdo_Japanese.setEnabled(true);
                rdo_English.setEnabled(true);
                rdo_Chinese.setEnabled(true);
                pul_Model.setEnabled(true);
                chk_WorkKind_TC_Receiving.setEnabled(true);
                chk_WorkKind_DC_Receiving.setEnabled(true);
                chk_WorkKind_Storage_Receiving.setEnabled(true);
                chk_WorkKind_Storage.setEnabled(true);
                chk_WorkKind_Order_Retrieval.setEnabled(true);
                chk_WorkKind_Sort.setEnabled(true);
                chk_WorkKind_Shipping_Pick.setEnabled(true);
                chk_WorkKind_Shipping_Loading.setEnabled(true);
                chk_WorkKind_NoPlanStorage.setEnabled(true);
                chk_WorkKind_NoPlanRetrieval.setEnabled(true);
                chk_WorkKind_Inventry.setEnabled(true);
                chk_WorkKind_RelocatinoRetriev.setEnabled(true);
                chk_WorkKind_RelocatinoStorage.setEnabled(true);
                btn_Set.setEnabled(true);
                btn_Clear.setEnabled(true);
            // DFKLOOK ここから修正
            }
            else
            {
                // clear.
                txt_MachineNumber.setReadOnly(true);
                pul_RftAssort.setEnabled(false);
                btn_Entry.setEnabled(false);
                btn_Modify.setEnabled(false);
                btn_Delete.setEnabled(false);
                txt_IpAddress.setReadOnly(false);
                btn_Set.setEnabled(true);
                btn_Clear.setEnabled(true);
            }
            // DFKLOOK ここまで修正
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
     * 削除処理を開始します。
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void btn_Delete_Click_Process()
            throws Exception
    {
        // input validation.
        txt_MachineNumber.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RftAddSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RftAddSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RftAddSCHParams inparam = new RftAddSCHParams();
            inparam.set(RftAddSCHParams.MACHINE_NUMBER, txt_MachineNumber.getValue());
            inparam.set(RftAddSCHParams.RFT_ASSORT, _pdm_pul_RftAssort.getSelectedValue());
            
            // DFKLOOK ここから修正
            if (!sch.checkrft(inparam))
            {
                message.setMsgResourceKey(sch.getMessage());
                return;
            }
            // DFKLOOK ここまで修正
            
            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // DFKLOOK ここから修正
            // 削除対象データなし
            if (outparams.isEmpty())
            {
                message.setMsgResourceKey(sch.getMessage());
                return;
            }
            // DFKLOOK ここまで修正

            // output display.
            for (Params outparam : outparams)
            {
                txt_IpAddress.setValue(outparam.get(RftAddSCHParams.IP_ADDRESS));
                _grp_language.setSelectedValue(outparam.get(RftAddSCHParams.LANGUAGE));
                _pdm_pul_Model.setSelectedValue(outparam.get(RftAddSCHParams.MODEL));
                chk_WorkKind_TC_Receiving.setValue(outparam.get(RftAddSCHParams.WORK_KIND_TC_RECEIVING));
                chk_WorkKind_DC_Receiving.setValue(outparam.get(RftAddSCHParams.WORK_KIND_DC_RECEIVING));
                chk_WorkKind_Storage_Receiving.setValue(outparam.get(RftAddSCHParams.WORK_KIND_STORAGE_RECEIVING));
                chk_WorkKind_Storage.setValue(outparam.get(RftAddSCHParams.WORK_KIND_STORAGE));
                chk_WorkKind_Order_Retrieval.setValue(outparam.get(RftAddSCHParams.WORK_KIND_ORDER_RETRIEVAL));
                chk_WorkKind_Sort.setValue(outparam.get(RftAddSCHParams.WORK_KIND_SORT));
                chk_WorkKind_Shipping_Pick.setValue(outparam.get(RftAddSCHParams.WORK_KIND_SHIPPING_PICK));
                chk_WorkKind_Shipping_Loading.setValue(outparam.get(RftAddSCHParams.WORK_KIND_SHIPPING_LOADING));
                chk_WorkKind_NoPlanStorage.setValue(outparam.get(RftAddSCHParams.WORK_KIND_NO_PLAN_STORAGE));
                chk_WorkKind_NoPlanRetrieval.setValue(outparam.get(RftAddSCHParams.WORK_KIND_NO_PLAN_RETRIEVAL));
                chk_WorkKind_Inventry.setValue(outparam.get(RftAddSCHParams.WORK_KIND_INVENTRY));
                chk_WorkKind_RelocatinoRetriev.setValue(outparam.get(RftAddSCHParams.WORK_KIND_RELOCATINO_RETRIEV));
                chk_WorkKind_RelocatinoStorage.setValue(outparam.get(RftAddSCHParams.WORK_KIND_RELOCATINO_STORAGE));
                viewState.setObject(ViewStateKeys.BTN_KEY, "DELETE");
                viewState.setObject(ViewStateKeys.VS_RFT_ASSORT, outparam.get(RftAddSCHParams.RFT_ASSORT));
            }

            // clear.
            txt_MachineNumber.setReadOnly(true);
            pul_RftAssort.setEnabled(false);
            btn_Entry.setEnabled(false);
            btn_Modify.setEnabled(false);
            btn_Delete.setEnabled(false);
            btn_Set.setEnabled(true);
            btn_Clear.setEnabled(true);

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
     * 設定処理を開始します。
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        txt_MachineNumber.validate(this, true);
        txt_IpAddress.validate(this, true);
        pul_RftAssort.validate(this, true);
        pul_Model.validate(this, true);

        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
        	// 設定しますか?
            this.setConfirm("MSG-W9000", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RftAddSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RftAddSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RftAddSCHParams inparam = new RftAddSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(RftAddSCHParams.MACHINE_NUMBER, txt_MachineNumber.getValue());
            inparam.set(RftAddSCHParams.RFT_ASSORT, _pdm_pul_RftAssort.getSelectedValue());
            inparam.set(RftAddSCHParams.IP_ADDRESS, txt_IpAddress.getValue());
            inparam.set(RftAddSCHParams.LANGUAGE, _grp_language.getSelectedValue());
            inparam.set(RftAddSCHParams.MODEL, _pdm_pul_Model.getSelectedValue());
            inparam.set(RftAddSCHParams.WORK_KIND_TC_RECEIVING, chk_WorkKind_TC_Receiving.getValue());
            inparam.set(RftAddSCHParams.WORK_KIND_DC_RECEIVING, chk_WorkKind_DC_Receiving.getValue());
            inparam.set(RftAddSCHParams.WORK_KIND_STORAGE_RECEIVING, chk_WorkKind_Storage_Receiving.getValue());
            inparam.set(RftAddSCHParams.WORK_KIND_STORAGE, chk_WorkKind_Storage.getValue());
            inparam.set(RftAddSCHParams.WORK_KIND_ORDER_RETRIEVAL, chk_WorkKind_Order_Retrieval.getValue());
            inparam.set(RftAddSCHParams.WORK_KIND_SORT, chk_WorkKind_Sort.getValue());
            inparam.set(RftAddSCHParams.WORK_KIND_SHIPPING_PICK, chk_WorkKind_Shipping_Pick.getValue());
            inparam.set(RftAddSCHParams.WORK_KIND_SHIPPING_LOADING, chk_WorkKind_Shipping_Loading.getValue());
            inparam.set(RftAddSCHParams.WORK_KIND_NO_PLAN_STORAGE, chk_WorkKind_NoPlanStorage.getValue());
            inparam.set(RftAddSCHParams.WORK_KIND_NO_PLAN_RETRIEVAL, chk_WorkKind_NoPlanRetrieval.getValue());
            inparam.set(RftAddSCHParams.WORK_KIND_INVENTRY, chk_WorkKind_Inventry.getValue());
            inparam.set(RftAddSCHParams.WORK_KIND_RELOCATINO_RETRIEV, chk_WorkKind_RelocatinoRetriev.getValue());
            inparam.set(RftAddSCHParams.WORK_KIND_RELOCATINO_STORAGE, chk_WorkKind_RelocatinoStorage.getValue());
            inparam.set(RftAddSCHParams.BTN_DISTINCTION, viewState.getObject(ViewStateKeys.BTN_KEY));
            inparam.set(RftAddSCHParams.TERMINALTYPE, viewState.getObject(ViewStateKeys.VS_RFT_ASSORT));

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
            part11List.add(txt_MachineNumber.getStringValue(), "");
            part11List.add(_pdm_pul_RftAssort.getSelectedStringValue(), "");
            part11List.add(txt_IpAddress.getStringValue(), "");
            part11List.add("00", "", rdo_Japanese.getChecked());
            part11List.add("01", "", rdo_English.getChecked());
            part11List.add("02", "", rdo_Chinese.getChecked());
            part11List.add(_pdm_pul_Model.getSelectedStringValue(), "");

            if (chk_WorkKind_TC_Receiving.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_WorkKind_DC_Receiving.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_WorkKind_Storage_Receiving.getChecked())
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

            if (chk_WorkKind_Order_Retrieval.getChecked())
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

            if (chk_WorkKind_Shipping_Pick.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            if (chk_WorkKind_Shipping_Loading.getChecked())
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

            if (chk_WorkKind_Inventry.getChecked())
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

            //DFKLOOK:ここから修正
            if ("CREATE".equals(viewState.getString(ViewStateKeys.BTN_KEY)))
            {
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_REGIST), part11List);
            }
            else if ("MODIFY".equals(viewState.getString(ViewStateKeys.BTN_KEY)))
            {
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_MODIFY), part11List);
            }
            else if ("DELETE".equals(viewState.getString(ViewStateKeys.BTN_KEY)))
            {
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_DELETE), part11List);
            }
            //DFKLOOK:ここまで修正

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // clear.
            txt_MachineNumber.setValue(null);
            txt_MachineNumber.setReadOnly(false);
            pul_RftAssort.setEnabled(true);
            btn_Entry.setEnabled(true);
            btn_Modify.setEnabled(true);
            btn_Delete.setEnabled(true);
            txt_IpAddress.setValue(null);
            txt_IpAddress.setReadOnly(true);
            rdo_Japanese.setEnabled(false);
            rdo_English.setEnabled(false);
            rdo_Chinese.setEnabled(false);
            pul_Model.setEnabled(false);
            chk_WorkKind_TC_Receiving.setEnabled(false);
            chk_WorkKind_DC_Receiving.setEnabled(false);
            chk_WorkKind_Storage_Receiving.setEnabled(false);
            chk_WorkKind_Storage.setEnabled(false);
            chk_WorkKind_Order_Retrieval.setEnabled(false);
            chk_WorkKind_Sort.setEnabled(false);
            chk_WorkKind_Shipping_Pick.setEnabled(false);
            chk_WorkKind_Shipping_Loading.setEnabled(false);
            chk_WorkKind_NoPlanStorage.setEnabled(false);
            chk_WorkKind_NoPlanRetrieval.setEnabled(false);
            chk_WorkKind_Inventry.setEnabled(false);
            chk_WorkKind_RelocatinoRetriev.setEnabled(false);
            chk_WorkKind_RelocatinoStorage.setEnabled(false);
            btn_Set.setEnabled(false);
            btn_Clear.setEnabled(false);

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
     * クリアボタンが押下された時の処理を行います。<BR>
     * 画面の初期設定を行います。
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_MachineNumber.setReadOnly(false);
        txt_MachineNumber.setValue(null);
        pul_RftAssort.setEnabled(true);
        btn_Entry.setEnabled(true);
        btn_Modify.setEnabled(true);
        btn_Delete.setEnabled(true);
        txt_IpAddress.setReadOnly(true);
        txt_IpAddress.setValue(null);
        rdo_Japanese.setChecked(true);
        rdo_Japanese.setEnabled(false);
        rdo_English.setEnabled(false);
        rdo_Chinese.setEnabled(false);
        pul_Model.setEnabled(false);
        chk_WorkKind_TC_Receiving.setEnabled(false);
        chk_WorkKind_DC_Receiving.setEnabled(false);
        chk_WorkKind_Storage_Receiving.setEnabled(false);
        chk_WorkKind_Storage.setEnabled(false);
        chk_WorkKind_Order_Retrieval.setEnabled(false);
        chk_WorkKind_Sort.setEnabled(false);
        chk_WorkKind_Shipping_Pick.setEnabled(false);
        chk_WorkKind_Shipping_Loading.setEnabled(false);
        chk_WorkKind_NoPlanStorage.setEnabled(false);
        chk_WorkKind_NoPlanRetrieval.setEnabled(false);
        chk_WorkKind_Inventry.setEnabled(false);
        chk_WorkKind_RelocatinoRetriev.setEnabled(false);
        chk_WorkKind_RelocatinoStorage.setEnabled(false);
        btn_Set.setEnabled(false);
        btn_Clear.setEnabled(false);

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
