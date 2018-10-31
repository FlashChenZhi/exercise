// $Id: PCTItemModify2Business.java 7162 2010-02-19 09:32:59Z shibamoto $
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

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.pcart.base.util.PCTLogWriter;
import jp.co.daifuku.pcart.master.schedule.PCTItemModify2SCH;
import jp.co.daifuku.pcart.master.schedule.PCTItemModify2SCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.pcart.master.schedule.PCTMasterInParameter;

/**
 * 商品マスタ修正・削除の画面処理を行います。
 *
 * @version $Revision: 7162 $, $Date:: 2010-02-19 18:32:59 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
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

    // DFKLOOK:ここから修正
    /**
     * 画像削除区分 しない
     */
    private static final String DELETE_FALSE = "0";

    /**
     * 画像削除区分 する
     */
    private static final String DELETE_TRUE = "1";

    // DFKLOOK:ここまで修正

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
        if (eventSource.startsWith("btn_ModifySet_Click"))
        {
            // process call.
            btn_ModifySet_Click_Process(eventSource);
        }
        else if (eventSource.startsWith("btn_Delete_Click"))
        {
            // process call.
            btn_Delete_Click_Process(eventSource);
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
        btn_ModifySet_Click_Process(null);
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
        btn_Delete_Click_Process(null);
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
        itemLog.add(lbl_InConsignorCode.getText());
        // 商品コード
        itemLog.add(lbl_InItemCode.getText());
        // ロット入数
        itemLog.add(lbl_InLotEnteringQty.getText());
        // 商品名称
        itemLog.add(txt_ItemName.getText());
        // JANコード
        itemLog.add(txt_JanCode.getText());
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
        // ロケーションNo.
        itemLog.add(txt_LocationNo.getText());
        // 商品画像削除区分
        if (chk_Delete.getChecked())
        {
            itemLog.add(DELETE_TRUE);
        }
        else
        {
            itemLog.add(DELETE_FALSE);
        }

        if (operationKind == EmConstants.OPELOG_CLASS_MODIFY)
        {
            // 商品名称
            itemLog.add(viewState.getString(ViewStateKeys.VS_ITEM_NAME));
            // JANコード
            itemLog.add(viewState.getString(ViewStateKeys.VS_JAN));
            // ケースITF
            itemLog.add(viewState.getString(ViewStateKeys.VS_ITF));
            // ボールITF
            itemLog.add(viewState.getString(ViewStateKeys.VS_BUNDLE_ITF));
            // 単重量
            itemLog.add(String.valueOf(viewState.getObject(ViewStateKeys.VS_SINGLE_WEIGHT)));
            // 重量誤差率
            itemLog.add(String.valueOf(viewState.getObject(ViewStateKeys.VS_WEIGHT_DISTINCT_RATE)));
            // メッセージ
            itemLog.add(viewState.getString(ViewStateKeys.VS_INFORMATION));
            // ロケーションNo.
            itemLog.add(WmsFormatter.toDispLocation(viewState.getString(ViewStateKeys.VS_LOCATION_NO),
                    WmsParam.DEFAULT_LOCATE_STYLE));
        }

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
                // DFKLOOK:ここから修正
                lbl_InLotEnteringQty.setValue(String.valueOf(outparam.get(PCTItemModify2SCHParams.LOT_QTY)));
                // DFKLOOK:ここまで修正
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
                viewState.setObject(ViewStateKeys.VS_LAST_UPDATE_DATE,
                        outparam.get(PCTItemModify2SCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.VS_ITEM_NAME, outparam.get(PCTItemModify2SCHParams.ITEM_NAME));
                viewState.setObject(ViewStateKeys.VS_JAN, outparam.get(PCTItemModify2SCHParams.JAN));
                viewState.setObject(ViewStateKeys.VS_ITF, outparam.get(PCTItemModify2SCHParams.ITF));
                viewState.setObject(ViewStateKeys.VS_BUNDLE_ITF, outparam.get(PCTItemModify2SCHParams.BUNDLE_ITF));
                viewState.setObject(ViewStateKeys.VS_SINGLE_WEIGHT, outparam.get(PCTItemModify2SCHParams.SINGLE_WEIGHT));
                viewState.setObject(ViewStateKeys.VS_WEIGHT_DISTINCT_RATE,
                        outparam.get(PCTItemModify2SCHParams.WEIGHT_DISTINCT_RATE));
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
    private void btn_Back_Click_Process()
            throws Exception
    {
        // set ViewState parameters.
        viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, lbl_InConsignorCode.getValue());
        viewState.setObject(ViewStateKeys.VS_ITEM_CODE, lbl_InItemCode.getValue());
        // DFKLOOK:ここから修正
        viewState.setObject(ViewStateKeys.VS_LOT_QTY,
                WmsFormatter.getInt(String.valueOf(lbl_InLotEnteringQty.getValue())));
        // DFKLOOK:ここまで修正

        // forward.
        forward("/pcart/master/pctitemmodify/PCTItemModify.do");
    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_ModifySet_Click_Process(String eventSource)
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

        // DFKLOOK start
        if (StringUtil.isBlank(eventSource))
        {         
            // 修正登録しますか?
            this.setConfirm("MSG-W0013", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_ModifySet_Click");
            return;         
        }
        // DFKLOOK end
        
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
            inparam.set(PCTItemModify2SCHParams.LAST_UPDATE_DATE,
                    viewState.getObject(ViewStateKeys.VS_LAST_UPDATE_DATE));
            inparam.set(PCTItemModify2SCHParams.LOT_QTY, viewState.getObject(ViewStateKeys.VS_LOT_QTY));
            inparam.set(PCTItemModify2SCHParams.PROCESS_FLAG, PCTMasterInParameter.PROCESS_FLAG_MODIFY);

            // SCH call.
            if (eventSource.equals("btn_ModifySet_Click") && !sch.check(inparam))
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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_ModifySet_Click_SCH");
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

            // DFKLOOK:ここから修正            
            // オペレーションログ出力
            log_write(conn, EmConstants.OPELOG_CLASS_MODIFY);
            // DFKLOOK:ここまで修正

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
                viewState.setObject(ViewStateKeys.VS_LAST_UPDATE_DATE,
                        outparam.get(PCTItemModify2SCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.VS_ITEM_NAME, outparam.get(PCTItemModify2SCHParams.ITEM_NAME));
                viewState.setObject(ViewStateKeys.VS_JAN, outparam.get(PCTItemModify2SCHParams.JAN));
                viewState.setObject(ViewStateKeys.VS_ITF, outparam.get(PCTItemModify2SCHParams.ITF));
                viewState.setObject(ViewStateKeys.VS_BUNDLE_ITF, outparam.get(PCTItemModify2SCHParams.BUNDLE_ITF));
                viewState.setObject(ViewStateKeys.VS_SINGLE_WEIGHT, outparam.get(PCTItemModify2SCHParams.SINGLE_WEIGHT));
                viewState.setObject(ViewStateKeys.VS_WEIGHT_DISTINCT_RATE,
                        outparam.get(PCTItemModify2SCHParams.WEIGHT_DISTINCT_RATE));
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
     * @param confirm
     * @throws Exception
     */
    private void btn_Delete_Click_Process(String eventSource)
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
            inparam.set(PCTItemModify2SCHParams.LAST_UPDATE_DATE,
                    viewState.getObject(ViewStateKeys.VS_LAST_UPDATE_DATE));
            inparam.set(PCTItemModify2SCHParams.LOT_QTY, viewState.getObject(ViewStateKeys.VS_LOT_QTY));
            inparam.set(PCTItemModify2SCHParams.PROCESS_FLAG, PCTMasterInParameter.PROCESS_FLAG_DELETE);

            // SCH call.
            if (StringUtil.isBlank(eventSource) && !sch.check(inparam))
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

            // DFKLOOK:ここから修正            
            // オペレーションログ出力
            log_write(conn, EmConstants.OPELOG_CLASS_MODIFY);
            // DFKLOOK:ここまで修正

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
