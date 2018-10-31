// $Id: PCTItemRegistBusiness.java 7885 2010-04-27 11:44:45Z kumano $
package jp.co.daifuku.pcart.master.display.pctitemregist;

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
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.pcart.base.util.PCTLogWriter;
import jp.co.daifuku.pcart.master.listbox.pctitem.PCTLstItemParams;
import jp.co.daifuku.pcart.master.schedule.PCTItemRegistSCH;
import jp.co.daifuku.pcart.master.schedule.PCTItemRegistSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * 商品マスタ登録の画面処理を行います。
 *
 * @version $Revision: 7885 $, $Date:: 2010-04-27 20:44:45 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class PCTItemRegistBusiness
        extends PCTItemRegist
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
    public PCTItemRegistBusiness()
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
        if (eventSource.equals("btn_SearchItem_Click"))
        {
            // process call.
            btn_SearchItem_Click_DlgBack(dialogParams);
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
    public void btn_SearchItem_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchItem_Click_Process();
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
    // DFKLOOK:ここから修正
    /**
     * オペレーションログ情報の書込み処理を行います <BR>
     * @param   conn            データベースコネクション
     * @param   operationKind  操作区分
     * @throws Exception 全ての例外を報告します。
     */
    protected void log_write(Connection conn, int operationKind)
            throws Exception
    {
        // オペレーションログ出力
        List<String> itemLog = new ArrayList<String>();

        // 荷主コード
        itemLog.add(txt_ConsignorCode.getText());
        // 商品コード
        itemLog.add(txt_ItemCode.getText());
        // 商品名称
        itemLog.add(txt_ItemName.getText());
        // JANコード
        itemLog.add(txt_JanCode.getText());
        // ロット入数
        itemLog.add(txt_LotEnteringQty.getText());
        // ケースITF
        itemLog.add(txt_CaseITF.getText());
        // ボールITF
        itemLog.add(txt_BundleItf.getText());
        // 単重量
        itemLog.add(txt_UnitWeight.getText());
        // 重量誤差率
        itemLog.add(txt_WeightErrorRate.getText());
        // メッセージ
        itemLog.add(txt_Message.getText());


        // ログ出力
        PCTLogWriter opeLogWriter = new PCTLogWriter(conn);
        opeLogWriter.createOperationLog((DfkUserInfo)getUserInfo(), operationKind, itemLog);
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
        // DFKLOOK:ここから修正
        // clear.
        txt_ConsignorCode.setValue(null);
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        txt_JanCode.setValue(null);
        txt_LotEnteringQty.setValue(null);
        txt_CaseITF.setValue(null);
        txt_BundleItf.setValue(null);
        txt_UnitWeight.setValue(null);
        txt_WeightErrorRate.setValue(null);
        txt_MaxCheckUnitNumber.setReadOnly(true);
        txt_Message.setValue(null);
        txt_LocationNo.setValue(null);
        // DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTItemRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTItemRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTItemRegistSCHParams inparam = new PCTItemRegistSCHParams();

            // SCH call.
            Params outparam = sch.initFind(inparam);
            if (outparam != null)
            {
                txt_ConsignorCode.setValue(outparam.get(PCTItemRegistSCHParams.CONSIGNOR_CODE));
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
     *
     * @throws Exception
     */
    private void btn_SearchItem_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        PCTLstItemParams inparam = new PCTLstItemParams();
        inparam.set(PCTLstItemParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(PCTLstItemParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(PCTLstItemParams.SEARCHTABLE, InParameter.SEARCH_TABLE_MASTER);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchItem_Click");
        redirect("/pcart/master/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchItem_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        PCTLstItemParams outparam = new PCTLstItemParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(PCTLstItemParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(PCTLstItemParams.ITEM_NAME));
        txt_JanCode.setValue(outparam.get(PCTLstItemParams.JAN));
        txt_LotEnteringQty.setValue(outparam.get(PCTLstItemParams.LOT_QTY));
        txt_CaseITF.setValue(outparam.get(PCTLstItemParams.ITF));

        // set focus.
        setFocus(txt_ItemCode);

    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);
        txt_ItemCode.validate(this, true);
        txt_ItemName.validate(this, false);
        txt_JanCode.validate(this, false);
        txt_LotEnteringQty.validate(this, true);
        txt_CaseITF.validate(this, false);
        txt_BundleItf.validate(this, false);
        txt_UnitWeight.validate(this, false);
        txt_WeightErrorRate.validate(this, false);
        txt_MaxCheckUnitNumber.validate(this, false);
        txt_Message.validate(this, false);
        txt_LocationNo.validate(this, false);

        // DFKLOOK start
        if (StringUtil.isBlank(eventSource))
        {         
            // 登録しますか?
            this.setConfirm("MSG-W0012", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
            return;         
        }
        // DFKLOOK end
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTItemRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTItemRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTItemRegistSCHParams inparam = new PCTItemRegistSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(PCTItemRegistSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTItemRegistSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(PCTItemRegistSCHParams.ITEM_NAME, txt_ItemName.getValue());
            inparam.set(PCTItemRegistSCHParams.JAN, txt_JanCode.getValue());
            inparam.set(PCTItemRegistSCHParams.LOT_QTY, txt_LotEnteringQty.getValue());
            inparam.set(PCTItemRegistSCHParams.ITF, txt_CaseITF.getValue());
            inparam.set(PCTItemRegistSCHParams.BUNDLE_ITF, txt_BundleItf.getValue());
            inparam.set(PCTItemRegistSCHParams.SINGLE_WEIGHT, txt_UnitWeight.getValue());
            inparam.set(PCTItemRegistSCHParams.WEIGHT_DISTINCT_RATE, txt_WeightErrorRate.getValue());
            inparam.set(PCTItemRegistSCHParams.MAX_INSPECTION_UNIT_QTY, txt_MaxCheckUnitNumber.getValue());
            inparam.set(PCTItemRegistSCHParams.INFORMATION, txt_Message.getValue());
            inparam.set(PCTItemRegistSCHParams.LOCATION_NO, txt_LocationNo.getValue());

            // DFKLOOK start 不要。コメント化
            // SCH call.
//            if (confirm && !sch.check(inparam))
//            {
//                if (StringUtil.isBlank(sch.getDispMessage()))
//                {
//                    // show message.
//                    message.setMsgResourceKey(sch.getMessage());
//                    return;
//                }
//                else
//                {
//                    // show confirm message.
//                    this.setConfirm(sch.getDispMessage(), false, true);
//                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
//                    return;
//                }
//            }
            // DFKLOOK end

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // DFKLOOK:ここから修正            
            // オペレーションログ出力
            log_write(conn, EmConstants.OPELOG_CLASS_REGIST);
            // DFKLOOK:ここまで修正
            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            // set input parameters.
            inparam = new PCTItemRegistSCHParams();
            inparam.set(PCTItemRegistSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTItemRegistSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(PCTItemRegistSCHParams.LOT_QTY, txt_LotEnteringQty.getValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                txt_UnitWeight.setValue(outparam.get(PCTItemRegistSCHParams.SINGLE_WEIGHT));
                txt_WeightErrorRate.setValue(outparam.get(PCTItemRegistSCHParams.WEIGHT_DISTINCT_RATE));
                txt_MaxCheckUnitNumber.setValue(outparam.get(PCTItemRegistSCHParams.MAX_INSPECTION_UNIT_QTY));
            }
            // DFKLOOK:ここまで修正

            // set focus.
            setFocus(txt_ConsignorCode);
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
     * @throws Exception
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_ConsignorCode.setValue(null);
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        txt_JanCode.setValue(null);
        txt_LotEnteringQty.setValue(null);
        txt_CaseITF.setValue(null);
        txt_BundleItf.setValue(null);
        txt_UnitWeight.setValue(null);
        txt_WeightErrorRate.setValue(null);
        txt_MaxCheckUnitNumber.setValue(null);
        txt_Message.setValue(null);
        txt_LocationNo.setValue(null);

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
