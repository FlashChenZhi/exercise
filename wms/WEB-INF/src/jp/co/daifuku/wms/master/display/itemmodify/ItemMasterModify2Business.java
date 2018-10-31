// $Id: ItemMasterModify2Business.java 7604 2010-03-16 06:33:57Z okayama $
package jp.co.daifuku.wms.master.display.itemmodify;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.SoftZoneType;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsSoftZonePullDownModel;
import jp.co.daifuku.wms.master.schedule.ItemMasterModifySCH;
import jp.co.daifuku.wms.master.schedule.ItemMasterModifySCHParams;
import jp.co.daifuku.wms.master.schedule.ItemMasterRegistSCH;
import jp.co.daifuku.wms.master.schedule.ItemMasterRegistSCHParams;
import jp.co.daifuku.wms.master.schedule.MasterInParameter;

/**
 * 商品マスタ修正・削除（詳細情報）の画面処理を行います。
 *
 * @version $Revision: 7604 $, $Date: 2010-03-16 15:33:57 +0900 (火, 16 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
@SuppressWarnings("serial")
public class ItemMasterModify2Business
        extends ItemMasterModify2
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
    /** PullDownModel pul_SoftZone */
    private WmsSoftZonePullDownModel _pdm_pul_SoftZone;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public ItemMasterModify2Business()
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
        if (eventSource.startsWith("btn_Modify_Click"))
        {
            // process call.
            btn_Modify_Click_Process(eventSource);
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
    public void btn_Modify_Click(ActionEvent e)
            throws Exception
    {
    	// 引数追加
        // process call.
        btn_Modify_Click_Process(null);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Delete_Click(ActionEvent e)
            throws Exception
    {
    	// 引数追加
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
            // DFKLOOK:ここから修正
            //AS/RS導入されていない場合は無効
            if (hasAsrsPack())
            {
                // load pul_SoftZone.
                _pdm_pul_SoftZone.init(conn, SoftZoneType.MASTER);
                // 要素が存在しない場合は無効
                if (pul_SoftZone.getItems().size() <= 0)
                {
                    pul_SoftZone.setEnabled(false);
                }
            }
            else
            {
                pul_SoftZone.setEnabled(false);
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
    @SuppressWarnings("all")
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
        ItemMasterModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ItemMasterModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            ItemMasterModifySCHParams inparam = new ItemMasterModifySCHParams();
            inparam.set(ItemMasterModifySCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(ItemMasterModifySCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
            inparam.set(ItemMasterModifySCHParams.LAST_UPDATE_DATE, viewState.getObject(ViewStateKeys.LAST_UPDATE_DATE));

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_ItemName.setValue(outparam.get(ItemMasterModifySCHParams.ITEM_NAME));
                _pdm_pul_SoftZone.setSelectedValue(outparam.get(ItemMasterModifySCHParams.SOFT_ZONE_ID));
                txt_CaseEnteringQty.setValue(outparam.get(ItemMasterModifySCHParams.CASE_ENTERING_QTY));
                txt_JanCode.setValue(outparam.get(ItemMasterModifySCHParams.JAN_CODE));
                txt_CaseITF.setValue(outparam.get(ItemMasterModifySCHParams.CASE_ITF));
                txt_UpperStockQty.setValue(outparam.get(ItemMasterModifySCHParams.UPPER_STOCK_QTY));
                txt_LowerStockQty.setValue(outparam.get(ItemMasterModifySCHParams.LOWER_STOCK_QTY));
                txt_LastUpdateDate.setValue(outparam.get(ItemMasterModifySCHParams.LAST_UPDATE_DATE));
                txt_LastUsedDate.setValue(outparam.get(ItemMasterModifySCHParams.LAST_USED_DATE));
                lbl_JavaSetItemCode.setValue(outparam.get(ItemMasterModifySCHParams.ITEM_CODE));
                viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, outparam.get(ItemMasterModifySCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.VS_ITEM_NAME, outparam.get(ItemMasterModifySCHParams.ITEM_NAME));
                viewState.setObject(ViewStateKeys.VS_SOFT_ZONE, outparam.get(ItemMasterModifySCHParams.SOFT_ZONE_ID));
                viewState.setObject(ViewStateKeys.VS_CASE_ENTERING_QTY, outparam.get(ItemMasterModifySCHParams.CASE_ENTERING_QTY));
                viewState.setObject(ViewStateKeys.VS_JAN_CODE, outparam.get(ItemMasterModifySCHParams.JAN_CODE));
                viewState.setObject(ViewStateKeys.VS_CASE_ITF, outparam.get(ItemMasterModifySCHParams.CASE_ITF));
                viewState.setObject(ViewStateKeys.VS_UPPER_STOCK_QTY, outparam.get(ItemMasterModifySCHParams.UPPER_STOCK_QTY));
                viewState.setObject(ViewStateKeys.VS_LOWER_STOCK_QTY, outparam.get(ItemMasterModifySCHParams.LOWER_STOCK_QTY));
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
    private void btn_Back_Click_Process()
            throws Exception
    {
        try
        {
            // set ViewState parameters.
            viewState.setObject(ViewStateKeys.ITEM_CODE, lbl_JavaSetItemCode.getValue());

            // forward.
            forward("/master/itemmodify/ItemMasterModify.do");
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
    private void btn_Modify_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        txt_ItemName.validate(this, false);
        txt_CaseEnteringQty.validate(this, false);
        txt_JanCode.validate(this, false);
        txt_CaseITF.validate(this, false);
        txt_UpperStockQty.validate(this, false);
        txt_LowerStockQty.validate(this, false);

        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
            // 修正登録しますか?
            this.setConfirm("MSG-W0013", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Modify_Click");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ItemMasterModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ItemMasterModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            ItemMasterModifySCHParams inparam = new ItemMasterModifySCHParams();
            inparam.setProcessFlag(ProcessFlag.UPDATE);
            inparam.set(ItemMasterModifySCHParams.ITEM_NAME, txt_ItemName.getValue());
            inparam.set(ItemMasterModifySCHParams.SOFT_ZONE_ID, _pdm_pul_SoftZone.getSelectedValue());
            inparam.set(ItemMasterModifySCHParams.CASE_ENTERING_QTY, txt_CaseEnteringQty.getValue());
            inparam.set(ItemMasterModifySCHParams.JAN_CODE, txt_JanCode.getValue());
            inparam.set(ItemMasterModifySCHParams.CASE_ITF, txt_CaseITF.getValue());
            inparam.set(ItemMasterModifySCHParams.UPPER_STOCK_QTY, txt_UpperStockQty.getValue());
            inparam.set(ItemMasterModifySCHParams.LOWER_STOCK_QTY, txt_LowerStockQty.getValue());
            inparam.set(ItemMasterModifySCHParams.LAST_UPDATE_DATE, txt_LastUpdateDate.getValue());
            inparam.set(ItemMasterModifySCHParams.LAST_USED_DATE, txt_LastUsedDate.getValue());
            inparam.set(ItemMasterModifySCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(ItemMasterModifySCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
            inparam.set(ItemMasterModifySCHParams.LAST_UPDATE_DATE, viewState.getObject(ViewStateKeys.LAST_UPDATE_DATE));
            inparam.set(ItemMasterModifySCHParams.PROCESS_FLAG, MasterInParameter.PROCESS_FLAG_MODIFY);

            // SCH call.
            if (eventSource.equals("btn_Modify_Click") && !sch.check(inparam))
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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Modify_Click_SCH");
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

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(lbl_JavaSetItemCode.getStringValue(), "");
            part11List.add(txt_ItemName.getStringValue(), "");
            part11List.add(_pdm_pul_SoftZone.getSelectedStringValue(), "");
            part11List.add(txt_CaseEnteringQty.getStringValue(), "0");
            part11List.add(txt_JanCode.getStringValue(), "");
            part11List.add(txt_CaseITF.getStringValue(), "");
            part11List.add(txt_UpperStockQty.getStringValue(), "0");
            part11List.add(txt_LowerStockQty.getStringValue(), "0");
            part11List.add(txt_LastUpdateDate.getStringValue(), "");
            part11List.add(txt_LastUsedDate.getStringValue(), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_ITEM_NAME), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_SOFT_ZONE), "");
            //DFKLOOK:ここから修正
            DecimalFormat dft = new DecimalFormat("#,##0");
            part11List.add(dft.format(viewState.getObject(ViewStateKeys.VS_CASE_ENTERING_QTY)), "0");
            //DFKLOOK:ここまで修正
            part11List.add(viewState.getString(ViewStateKeys.VS_JAN_CODE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_CASE_ITF), "");
            //DFKLOOK:ここから修正
            part11List.add(dft.format(viewState.getObject(ViewStateKeys.VS_UPPER_STOCK_QTY)), "0");
            part11List.add(dft.format(viewState.getObject(ViewStateKeys.VS_LOWER_STOCK_QTY)), "0");
            //DFKLOOK:ここまで修正
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_MODIFY), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // set input parameters.
            inparam = new ItemMasterModifySCHParams();
            inparam.set(ItemMasterModifySCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(ItemMasterModifySCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
            inparam.set(ItemMasterModifySCHParams.LAST_UPDATE_DATE, viewState.getObject(ViewStateKeys.LAST_UPDATE_DATE));
            inparam.set(ItemMasterModifySCHParams.PROCESS_FLAG, MasterInParameter.PROCESS_FLAG_MODIFY);

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                txt_ItemName.setValue(outparam.get(ItemMasterModifySCHParams.ITEM_NAME));
                _pdm_pul_SoftZone.setSelectedValue(outparam.get(ItemMasterModifySCHParams.SOFT_ZONE_ID));
                txt_CaseEnteringQty.setValue(outparam.get(ItemMasterModifySCHParams.CASE_ENTERING_QTY));
                txt_JanCode.setValue(outparam.get(ItemMasterModifySCHParams.JAN_CODE));
                txt_CaseITF.setValue(outparam.get(ItemMasterModifySCHParams.CASE_ITF));
                txt_UpperStockQty.setValue(outparam.get(ItemMasterModifySCHParams.UPPER_STOCK_QTY));
                txt_LowerStockQty.setValue(outparam.get(ItemMasterModifySCHParams.LOWER_STOCK_QTY));
                txt_LastUpdateDate.setValue(outparam.get(ItemMasterModifySCHParams.LAST_UPDATE_DATE));
                txt_LastUsedDate.setValue(outparam.get(ItemMasterModifySCHParams.LAST_USED_DATE));
                viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, outparam.get(ItemMasterModifySCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.VS_ITEM_NAME, outparam.get(ItemMasterModifySCHParams.ITEM_NAME));
                viewState.setObject(ViewStateKeys.VS_SOFT_ZONE, outparam.get(ItemMasterModifySCHParams.SOFT_ZONE_ID));
                viewState.setObject(ViewStateKeys.VS_CASE_ENTERING_QTY, outparam.get(ItemMasterModifySCHParams.CASE_ENTERING_QTY));
                viewState.setObject(ViewStateKeys.VS_JAN_CODE, outparam.get(ItemMasterModifySCHParams.JAN_CODE));
                viewState.setObject(ViewStateKeys.VS_CASE_ITF, outparam.get(ItemMasterModifySCHParams.CASE_ITF));
                viewState.setObject(ViewStateKeys.VS_UPPER_STOCK_QTY, outparam.get(ItemMasterModifySCHParams.UPPER_STOCK_QTY));
                viewState.setObject(ViewStateKeys.VS_LOWER_STOCK_QTY, outparam.get(ItemMasterModifySCHParams.LOWER_STOCK_QTY));
            }

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
    private void btn_Delete_Click_Process(String eventSource)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ItemMasterModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ItemMasterModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            ItemMasterModifySCHParams inparam = new ItemMasterModifySCHParams();
            inparam.setProcessFlag(ProcessFlag.DELETE);
            inparam.set(ItemMasterModifySCHParams.ITEM_NAME, txt_ItemName.getValue());
            inparam.set(ItemMasterModifySCHParams.SOFT_ZONE_ID, _pdm_pul_SoftZone.getSelectedValue());
            inparam.set(ItemMasterModifySCHParams.CASE_ENTERING_QTY, txt_CaseEnteringQty.getValue());
            inparam.set(ItemMasterModifySCHParams.JAN_CODE, txt_JanCode.getValue());
            inparam.set(ItemMasterModifySCHParams.CASE_ITF, txt_CaseITF.getValue());
            inparam.set(ItemMasterModifySCHParams.UPPER_STOCK_QTY, txt_UpperStockQty.getValue());
            inparam.set(ItemMasterModifySCHParams.LOWER_STOCK_QTY, txt_LowerStockQty.getValue());
            inparam.set(ItemMasterModifySCHParams.LAST_UPDATE_DATE, txt_LastUpdateDate.getValue());
            inparam.set(ItemMasterModifySCHParams.LAST_USED_DATE, txt_LastUsedDate.getValue());
            inparam.set(ItemMasterModifySCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(ItemMasterModifySCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
            inparam.set(ItemMasterModifySCHParams.LAST_UPDATE_DATE, viewState.getObject(ViewStateKeys.LAST_UPDATE_DATE));

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

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(lbl_JavaSetItemCode.getStringValue(), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_ITEM_NAME), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_SOFT_ZONE), "");
            //DFKLOOK:ここから修正
            DecimalFormat dft = new DecimalFormat("#,##0");
            part11List.add(dft.format(viewState.getObject(ViewStateKeys.VS_CASE_ENTERING_QTY)), "0");
            //DFKLOOK:ここまで修正
            part11List.add(viewState.getString(ViewStateKeys.VS_JAN_CODE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_CASE_ITF), "");
            //DFKLOOK:ここから修正
            part11List.add(dft.format(viewState.getObject(ViewStateKeys.VS_UPPER_STOCK_QTY)), "0");
            part11List.add(dft.format(viewState.getObject(ViewStateKeys.VS_LOWER_STOCK_QTY)), "0");
            //DFKLOOK:ここまで修正
            part11List.add(txt_LastUpdateDate.getStringValue(), "");
            part11List.add(txt_LastUsedDate.getStringValue(), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_DELETE), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // clear.
            txt_ItemName.setValue(null);
            _pdm_pul_SoftZone.setSelectedValue(null);
            txt_CaseEnteringQty.setValue(null);
            txt_JanCode.setValue(null);
            txt_CaseITF.setValue(null);
            txt_UpperStockQty.setValue(null);
            txt_LowerStockQty.setValue(null);
            txt_LastUpdateDate.setValue(null);
            txt_LastUsedDate.setValue(null);
            btn_Clear.setEnabled(false);
            btn_Delete.setEnabled(false);
            btn_Modify.setEnabled(false);

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
        _pdm_pul_SoftZone.setSelectedValue(null);
        txt_CaseEnteringQty.setValue(null);
        txt_JanCode.setValue(null);
        txt_CaseITF.setValue(null);
        txt_UpperStockQty.setValue(null);
        txt_LowerStockQty.setValue(null);

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
    
    // DFKLOOK:ここから修正
    /**
     * AS/RSパッケージが導入されているかどうかをチェックします。
     *
     * @return boolean AS/RSパッケージが導入されているか否か
     * @throws Exception 全ての例外を報告します。
     */
    private boolean hasAsrsPack()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ItemMasterRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ItemMasterRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            ItemMasterRegistSCHParams inparam = new ItemMasterRegistSCHParams();

            // SCH call.
            Params outparam = sch.initFind(inparam);

            return outparam.getBoolean(ItemMasterRegistSCHParams.HAS_ASRS);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            return false;
        }
        finally
        {
            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }
    }
    // DFKLOOK:ここまで修正

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
