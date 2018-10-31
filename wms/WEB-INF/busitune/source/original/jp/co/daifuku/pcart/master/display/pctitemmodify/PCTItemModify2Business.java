// $Id: PCTItemModify2Business.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.pcart.master.display.pctitemmodify;

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
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.pcart.master.display.pctitemmodify.PCTItemModify2;
import jp.co.daifuku.pcart.master.display.pctitemmodify.ViewStateKeys;
import jp.co.daifuku.pcart.master.schedule.PCTItemModify2SCH;
import jp.co.daifuku.pcart.master.schedule.PCTItemModify2SCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.master.schedule.MasterInParameter;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class PCTItemModify2Business
        extends PCTItemModify2
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

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTItemModify2Business()
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
        if (eventSource.equals("btn_ModifySet_Click"))
        {
            // process call.
            btn_ModifySet_Click_Process(false);
        }
        else if (eventSource.equals("btn_Delete_Click"))
        {
            // process call.
            btn_Delete_Click_Process(false);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Back_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Back_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ModifySet_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ModifySet_Click_Process(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Delete_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Delete_Click_Process(true);
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
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_ItemName);

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
        PCTItemModify2SCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTItemModify2SCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTItemModify2SCHParams inparam = new PCTItemModify2SCHParams();
            inparam.set(PCTItemModify2SCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTItemModify2SCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.VS_ITEM_CODE));
            inparam.set(PCTItemModify2SCHParams.LOT_QTY, viewState.getObject(ViewStateKeys.VS_LOT_QTY));

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                lbl_InConsignorCode.setValue(outparam.get(PCTItemModify2SCHParams.CONSIGNOR_CODE));
                lbl_InItemCode.setValue(outparam.get(PCTItemModify2SCHParams.ITEM_CODE));
                lbl_InLotEnteringQty.setValue(outparam.get(PCTItemModify2SCHParams.LOT_QTY));
                txt_ItemName.setValue(outparam.get(PCTItemModify2SCHParams.ITEM_NAME));
                txt_JanCode.setValue(outparam.get(PCTItemModify2SCHParams.JAN));
                txt_CaseITF.setValue(outparam.get(PCTItemModify2SCHParams.ITF));
                txt_BundleItf.setValue(outparam.get(PCTItemModify2SCHParams.BUNDLE_ITF));
                txt_UnitWeight.setValue(outparam.get(PCTItemModify2SCHParams.SINGLE_WEIGHT));
                txt_WeightErrorRate.setValue(outparam.get(PCTItemModify2SCHParams.WEIGHT_DISTINCT_RATE));
                txt_MaxCheckUnitNumber.setValue(outparam.get(PCTItemModify2SCHParams.MAX_INSPECTION_UNIT_QTY));
                txt_Message.setValue(outparam.get(PCTItemModify2SCHParams.INFORMATION));
                txt_LocationNo.setValue(outparam.get(PCTItemModify2SCHParams.LOCATION_NO));
                txt_ItemImageSet.setValue(outparam.get(PCTItemModify2SCHParams.ITEM_PICTURE_REGIST));
                viewState.setObject(ViewStateKeys.VS_LAST_UPDATE_DATE, outparam.get(PCTItemModify2SCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.VS_ITEM_NAME, outparam.get(PCTItemModify2SCHParams.ITEM_NAME));
                viewState.setObject(ViewStateKeys.VS_JAN, outparam.get(PCTItemModify2SCHParams.JAN));
                viewState.setObject(ViewStateKeys.VS_ITF, outparam.get(PCTItemModify2SCHParams.ITF));
                viewState.setObject(ViewStateKeys.VS_BUNDLE_ITF, outparam.get(PCTItemModify2SCHParams.BUNDLE_ITF));
                viewState.setObject(ViewStateKeys.VS_SINGLE_WEIGHT, outparam.get(PCTItemModify2SCHParams.SINGLE_WEIGHT));
                viewState.setObject(ViewStateKeys.VS_WEIGHT_DISTINCT_RATE, outparam.get(PCTItemModify2SCHParams.WEIGHT_DISTINCT_RATE));
                viewState.setObject(ViewStateKeys.VS_INFORMATION, outparam.get(PCTItemModify2SCHParams.INFORMATION));
                viewState.setObject(ViewStateKeys.VS_LOCATION_NO, outparam.get(PCTItemModify2SCHParams.LOCATION_NO));
            }

            // clear.
            txt_MaxCheckUnitNumber.setReadOnly(true);
            txt_ItemImageSet.setReadOnly(true);
            chk_Delete.setChecked(false);

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
    private void btn_Back_Click_Process()
            throws Exception
    {
        try
        {
            // set ViewState parameters.
            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, lbl_InConsignorCode.getValue());
            viewState.setObject(ViewStateKeys.VS_ITEM_CODE, lbl_InItemCode.getValue());
            viewState.setObject(ViewStateKeys.VS_LOT_QTY, lbl_InLotEnteringQty.getValue());

            // forward.
            forward("/pcart/master/pctitemmodify/PCTItemModify.do");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_ModifySet_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        txt_ItemName.validate(this, false);
        txt_JanCode.validate(this, false);
        txt_CaseITF.validate(this, false);
        txt_BundleItf.validate(this, false);
        txt_UnitWeight.validate(this, false);
        txt_WeightErrorRate.validate(this, false);
        txt_MaxCheckUnitNumber.validate(this, false);
        txt_Message.validate(this, false);
        txt_LocationNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTItemModify2SCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTItemModify2SCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTItemModify2SCHParams inparam = new PCTItemModify2SCHParams();
            inparam.setProcessFlag(ProcessFlag.UPDATE);
            inparam.set(PCTItemModify2SCHParams.ITEM_NAME, txt_ItemName.getValue());
            inparam.set(PCTItemModify2SCHParams.JAN, txt_JanCode.getValue());
            inparam.set(PCTItemModify2SCHParams.ITF, txt_CaseITF.getValue());
            inparam.set(PCTItemModify2SCHParams.BUNDLE_ITF, txt_BundleItf.getValue());
            inparam.set(PCTItemModify2SCHParams.SINGLE_WEIGHT, txt_UnitWeight.getValue());
            inparam.set(PCTItemModify2SCHParams.WEIGHT_DISTINCT_RATE, txt_WeightErrorRate.getValue());
            inparam.set(PCTItemModify2SCHParams.MAX_INSPECTION_UNIT_QTY, txt_MaxCheckUnitNumber.getValue());
            inparam.set(PCTItemModify2SCHParams.INFORMATION, txt_Message.getValue());
            inparam.set(PCTItemModify2SCHParams.LOCATION_NO, txt_LocationNo.getValue());
            inparam.set(PCTItemModify2SCHParams.DELETE_FLAG, chk_Delete.getValue());
            inparam.set(PCTItemModify2SCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTItemModify2SCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.VS_ITEM_CODE));
            inparam.set(PCTItemModify2SCHParams.LAST_UPDATE_DATE, viewState.getObject(ViewStateKeys.VS_LAST_UPDATE_DATE));
            inparam.set(PCTItemModify2SCHParams.LOT_QTY, viewState.getObject(ViewStateKeys.VS_LOT_QTY));
            inparam.set(PCTItemModify2SCHParams.PROCESS_FLAG, MasterInParameter.PROCESS_FLAG_MODIFY);

            // SCH call.
            if (confirm && !sch.check(inparam))
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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_ModifySet_Click");
                    return;
                }
            }

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // set input parameters.
            inparam = new PCTItemModify2SCHParams();
            inparam.set(PCTItemModify2SCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTItemModify2SCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.VS_ITEM_CODE));
            inparam.set(PCTItemModify2SCHParams.LOT_QTY, viewState.getObject(ViewStateKeys.VS_LOT_QTY));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                txt_ItemName.setValue(outparam.get(PCTItemModify2SCHParams.ITEM_NAME));
                txt_JanCode.setValue(outparam.get(PCTItemModify2SCHParams.JAN));
                txt_CaseITF.setValue(outparam.get(PCTItemModify2SCHParams.ITF));
                txt_BundleItf.setValue(outparam.get(PCTItemModify2SCHParams.BUNDLE_ITF));
                txt_UnitWeight.setValue(outparam.get(PCTItemModify2SCHParams.SINGLE_WEIGHT));
                txt_WeightErrorRate.setValue(outparam.get(PCTItemModify2SCHParams.WEIGHT_DISTINCT_RATE));
                txt_MaxCheckUnitNumber.setValue(outparam.get(PCTItemModify2SCHParams.MAX_INSPECTION_UNIT_QTY));
                txt_Message.setValue(outparam.get(PCTItemModify2SCHParams.INFORMATION));
                txt_LocationNo.setValue(outparam.get(PCTItemModify2SCHParams.LOCATION_NO));
                txt_ItemImageSet.setValue(outparam.get(PCTItemModify2SCHParams.ITEM_PICTURE_REGIST));
                viewState.setObject(ViewStateKeys.VS_LAST_UPDATE_DATE, outparam.get(PCTItemModify2SCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.VS_ITEM_NAME, outparam.get(PCTItemModify2SCHParams.ITEM_NAME));
                viewState.setObject(ViewStateKeys.VS_JAN, outparam.get(PCTItemModify2SCHParams.JAN));
                viewState.setObject(ViewStateKeys.VS_ITF, outparam.get(PCTItemModify2SCHParams.ITF));
                viewState.setObject(ViewStateKeys.VS_BUNDLE_ITF, outparam.get(PCTItemModify2SCHParams.BUNDLE_ITF));
                viewState.setObject(ViewStateKeys.VS_SINGLE_WEIGHT, outparam.get(PCTItemModify2SCHParams.SINGLE_WEIGHT));
                viewState.setObject(ViewStateKeys.VS_WEIGHT_DISTINCT_RATE, outparam.get(PCTItemModify2SCHParams.WEIGHT_DISTINCT_RATE));
                viewState.setObject(ViewStateKeys.VS_INFORMATION, outparam.get(PCTItemModify2SCHParams.INFORMATION));
                viewState.setObject(ViewStateKeys.VS_LOCATION_NO, outparam.get(PCTItemModify2SCHParams.LOCATION_NO));
            }

            // clear.
            chk_Delete.setChecked(false);

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
            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_Delete_Click_Process(boolean confirm)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTItemModify2SCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTItemModify2SCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTItemModify2SCHParams inparam = new PCTItemModify2SCHParams();
            inparam.setProcessFlag(ProcessFlag.DELETE);
            inparam.set(PCTItemModify2SCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTItemModify2SCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.VS_ITEM_CODE));
            inparam.set(PCTItemModify2SCHParams.LAST_UPDATE_DATE, viewState.getObject(ViewStateKeys.VS_LAST_UPDATE_DATE));
            inparam.set(PCTItemModify2SCHParams.LOT_QTY, viewState.getObject(ViewStateKeys.VS_LOT_QTY));
            inparam.set(PCTItemModify2SCHParams.PROCESS_FLAG, MasterInParameter.PROCESS_FLAG_DELETE);

            // SCH call.
            if (confirm && !sch.check(inparam))
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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Delete_Click");
                    return;
                }
            }

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // clear.
            txt_ItemName.setValue(null);
            txt_JanCode.setValue(null);
            txt_CaseITF.setValue(null);
            txt_BundleItf.setValue(null);
            txt_UnitWeight.setValue(null);
            txt_WeightErrorRate.setValue(null);
            txt_MaxCheckUnitNumber.setValue(null);
            txt_Message.setValue(null);
            txt_LocationNo.setValue(null);
            txt_ItemImageSet.setValue(null);
            chk_Delete.setChecked(false);
            txt_ItemName.setReadOnly(true);
            txt_JanCode.setReadOnly(true);
            txt_CaseITF.setReadOnly(true);
            txt_BundleItf.setReadOnly(true);
            txt_UnitWeight.setReadOnly(true);
            txt_WeightErrorRate.setReadOnly(true);
            txt_MaxCheckUnitNumber.setReadOnly(true);
            txt_Message.setReadOnly(true);
            txt_LocationNo.setReadOnly(true);
            chk_Delete.setEnabled(false);
            btn_ModifySet.setEnabled(false);
            btn_Delete.setEnabled(false);
            btn_Clear.setEnabled(false);

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
        txt_ItemName.setValue(null);
        txt_JanCode.setValue(null);
        txt_CaseITF.setValue(null);
        txt_BundleItf.setValue(null);
        txt_UnitWeight.setValue(null);
        txt_WeightErrorRate.setValue(null);
        txt_MaxCheckUnitNumber.setValue(null);
        txt_Message.setValue(null);
        txt_LocationNo.setValue(null);
        txt_ItemImageSet.setValue(null);
        chk_Delete.setChecked(false);

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
