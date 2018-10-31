// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.asrs.display.faworkdisplay;

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
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.faworkdisplay.FaWorkDisplay;
import jp.co.daifuku.wms.asrs.display.faworkdisplay.ViewStateKeys;
import jp.co.daifuku.wms.asrs.schedule.FaWorkDisplaySCH;
import jp.co.daifuku.wms.asrs.schedule.FaWorkDisplaySCHParams;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class FaWorkDisplayBusiness
        extends FaWorkDisplay
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
    /** PullDownModel pul_Station */
    private WmsStationPullDownModel _pdm_pul_Station;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaWorkDisplayBusiness()
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
    public void btn_ReDisplay_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ReDisplay_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Complete_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Complete_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PrevWork_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PrevWork_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_NextWork_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_NextWork_Click_Process();
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

        // initialize pul_Station.
        _pdm_pul_Station = new WmsStationPullDownModel(pul_Station, locale, ui);
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

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.OPERATION_DISPLAY, Distribution.UNUSE);
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
        setFocus(pul_Station);
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
        FaWorkDisplaySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaWorkDisplaySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaWorkDisplaySCHParams inparam = new FaWorkDisplaySCHParams();
            inparam.set(FaWorkDisplaySCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_RestWork.setValue(outparam.get(FaWorkDisplaySCHParams.REST_WORK));
                txt_WorkNo.setValue(outparam.get(FaWorkDisplaySCHParams.WORK_NO));
                txt_Location.setValue(outparam.get(FaWorkDisplaySCHParams.LOCATION_NO));
                txt_CarryingFlag.setValue(outparam.get(FaWorkDisplaySCHParams.CARRY_FLAG));
                txt_InstructionDetail.setValue(outparam.get(FaWorkDisplaySCHParams.RETRIEVAL_DETAIL_NAME));
                txt_ItemCode.setValue(outparam.get(FaWorkDisplaySCHParams.ITEM_CODE));
                txt_ItemName.setValue(outparam.get(FaWorkDisplaySCHParams.ITEM_NAME));
                txt_LotNo.setValue(outparam.get(FaWorkDisplaySCHParams.LOT_NO));
                txt_StockQty.setValue(outparam.get(FaWorkDisplaySCHParams.STOCK_QTY));
                txt_WorkQty.setValue(outparam.get(FaWorkDisplaySCHParams.WORK_QTY));
                txt_StorageDate.setValue(outparam.get(FaWorkDisplaySCHParams.STORAGE_DATE));
                txt_StorageTime.setValue(outparam.get(FaWorkDisplaySCHParams.STORAGE_TIME));
                txt_BatchNo.setValue(outparam.get(FaWorkDisplaySCHParams.BATCH_NO));
                txt_SlipNumber.setValue(outparam.get(FaWorkDisplaySCHParams.TICKET_NO));
                txt_LineNo.setValue(outparam.get(FaWorkDisplaySCHParams.LINE_NO));
                viewState.setObject(ViewStateKeys.CARRY_KEY, outparam.get(FaWorkDisplaySCHParams.CARRY_KEY));
                viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, outparam.get(FaWorkDisplaySCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.RETRIEVAL_DETAIL, outparam.get(FaWorkDisplaySCHParams.RETRIEVAL_DETAIL));
                viewState.setObject(ViewStateKeys.WAREHOUSE_NO, outparam.get(FaWorkDisplaySCHParams.WAREHOUSE_NO));
                viewState.setObject(ViewStateKeys.WORK_DISPLAY_OPERATE, outparam.get(FaWorkDisplaySCHParams.WORK_DISPLAY_OPERATE));
                viewState.setObject(ViewStateKeys.WORK_TYPE, outparam.get(FaWorkDisplaySCHParams.WORK_TYPE));
            }

            // clear.
            btn_Complete.setEnabled(false);
            btn_PrevWork.setEnabled(false);
            btn_NextWork.setEnabled(false);
            txt_RestWork.setReadOnly(true);
            txt_WorkNo.setReadOnly(true);
            txt_Location.setReadOnly(true);
            txt_CarryingFlag.setReadOnly(true);
            txt_InstructionDetail.setReadOnly(true);
            txt_ItemCode.setReadOnly(true);
            txt_ItemName.setReadOnly(true);
            txt_LotNo.setReadOnly(true);
            txt_StockQty.setReadOnly(true);
            txt_WorkQty.setReadOnly(true);
            txt_StorageDate.setReadOnly(true);
            txt_StorageTime.setReadOnly(true);
            txt_SlipNumber.setReadOnly(true);
            txt_BatchNo.setReadOnly(true);
            txt_LineNo.setReadOnly(true);
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
    private void btn_ReDisplay_Click_Process()
            throws Exception
    {
        // input validation.
        pul_Station.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaWorkDisplaySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaWorkDisplaySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaWorkDisplaySCHParams inparam = new FaWorkDisplaySCHParams();
            inparam.set(FaWorkDisplaySCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_RestWork.setValue(outparam.get(FaWorkDisplaySCHParams.REST_WORK));
                txt_WorkNo.setValue(outparam.get(FaWorkDisplaySCHParams.WORK_NO));
                txt_Location.setValue(outparam.get(FaWorkDisplaySCHParams.LOCATION_NO));
                txt_CarryingFlag.setValue(outparam.get(FaWorkDisplaySCHParams.CARRY_FLAG));
                txt_InstructionDetail.setValue(outparam.get(FaWorkDisplaySCHParams.RETRIEVAL_DETAIL_NAME));
                txt_ItemCode.setValue(outparam.get(FaWorkDisplaySCHParams.ITEM_CODE));
                txt_ItemName.setValue(outparam.get(FaWorkDisplaySCHParams.ITEM_NAME));
                txt_LotNo.setValue(outparam.get(FaWorkDisplaySCHParams.LOT_NO));
                txt_StockQty.setValue(outparam.get(FaWorkDisplaySCHParams.STOCK_QTY));
                txt_WorkQty.setValue(outparam.get(FaWorkDisplaySCHParams.WORK_QTY));
                txt_StorageDate.setValue(outparam.get(FaWorkDisplaySCHParams.STORAGE_DATE));
                txt_StorageTime.setValue(outparam.get(FaWorkDisplaySCHParams.STORAGE_TIME));
                txt_BatchNo.setValue(outparam.get(FaWorkDisplaySCHParams.BATCH_NO));
                txt_SlipNumber.setValue(outparam.get(FaWorkDisplaySCHParams.TICKET_NO));
                txt_LineNo.setValue(outparam.get(FaWorkDisplaySCHParams.LINE_NO));
                viewState.setObject(ViewStateKeys.STATION_NO, _pdm_pul_Station.getSelectedValue());
                viewState.setObject(ViewStateKeys.CARRY_KEY, outparam.get(FaWorkDisplaySCHParams.CARRY_KEY));
                viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, outparam.get(FaWorkDisplaySCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.RETRIEVAL_DETAIL, outparam.get(FaWorkDisplaySCHParams.RETRIEVAL_DETAIL));
                viewState.setObject(ViewStateKeys.WAREHOUSE_NO, outparam.get(FaWorkDisplaySCHParams.WAREHOUSE_NO));
                viewState.setObject(ViewStateKeys.WORK_DISPLAY_OPERATE, outparam.get(FaWorkDisplaySCHParams.WORK_DISPLAY_OPERATE));
                viewState.setObject(ViewStateKeys.WORK_TYPE, outparam.get(FaWorkDisplaySCHParams.WORK_TYPE));
            }

            // clear.
            btn_Complete.setEnabled(true);
            btn_PrevWork.setEnabled(true);
            btn_NextWork.setEnabled(true);
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
    private void btn_Complete_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaWorkDisplaySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaWorkDisplaySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(txt_RestWork.getStringValue(), "");
            part11List.add(txt_WorkNo.getStringValue(), "");
            part11List.add(txt_Location.getStringValue(), "");
            part11List.add(txt_CarryingFlag.getStringValue(), "");
            part11List.add(txt_InstructionDetail.getStringValue(), "");
            part11List.add(txt_ItemCode.getStringValue(), "");
            part11List.add(txt_LotNo.getStringValue(), "");
            part11List.add(txt_StockQty.getStringValue(), "");
            part11List.add(txt_WorkQty.getStringValue(), "");
            part11List.add(txt_StorageDate.getStringValue(), txt_StorageTime.getStringValue(), "");
            part11List.add(txt_BatchNo.getStringValue(), "");
            part11List.add(txt_SlipNumber.getStringValue(), "");
            part11List.add(txt_LineNo.getStringValue(), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // set input parameters.
            FaWorkDisplaySCHParams inparam = new FaWorkDisplaySCHParams();
            inparam.set(FaWorkDisplaySCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                txt_RestWork.setValue(outparam.get(FaWorkDisplaySCHParams.REST_WORK));
                txt_WorkNo.setValue(outparam.get(FaWorkDisplaySCHParams.WORK_NO));
                txt_Location.setValue(outparam.get(FaWorkDisplaySCHParams.LOCATION_NO));
                txt_CarryingFlag.setValue(outparam.get(FaWorkDisplaySCHParams.WORK_TYPE));
                txt_InstructionDetail.setValue(outparam.get(FaWorkDisplaySCHParams.RETRIEVAL_DETAIL_NAME));
                txt_ItemCode.setValue(outparam.get(FaWorkDisplaySCHParams.ITEM_CODE));
                txt_ItemName.setValue(outparam.get(FaWorkDisplaySCHParams.ITEM_NAME));
                txt_LotNo.setValue(outparam.get(FaWorkDisplaySCHParams.LOT_NO));
                txt_StockQty.setValue(outparam.get(FaWorkDisplaySCHParams.STOCK_QTY));
                txt_WorkQty.setValue(outparam.get(FaWorkDisplaySCHParams.WORK_QTY));
                txt_StorageDate.setValue(outparam.get(FaWorkDisplaySCHParams.STORAGE_DATE));
                txt_StorageTime.setValue(outparam.get(FaWorkDisplaySCHParams.STORAGE_TIME));
                txt_BatchNo.setValue(outparam.get(FaWorkDisplaySCHParams.BATCH_NO));
                txt_SlipNumber.setValue(outparam.get(FaWorkDisplaySCHParams.TICKET_NO));
                txt_LineNo.setValue(outparam.get(FaWorkDisplaySCHParams.LINE_NO));
                viewState.setObject(ViewStateKeys.STATION_NO, _pdm_pul_Station.getSelectedValue());
                viewState.setObject(ViewStateKeys.CARRY_KEY, outparam.get(FaWorkDisplaySCHParams.CARRY_KEY));
                viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, outparam.get(FaWorkDisplaySCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.RETRIEVAL_DETAIL, outparam.get(FaWorkDisplaySCHParams.RETRIEVAL_DETAIL));
                viewState.setObject(ViewStateKeys.WAREHOUSE_NO, outparam.get(FaWorkDisplaySCHParams.WAREHOUSE_NO));
                viewState.setObject(ViewStateKeys.WORK_DISPLAY_OPERATE, outparam.get(FaWorkDisplaySCHParams.WORK_DISPLAY_OPERATE));
            }

            // clear.
            btn_Complete.setEnabled(false);
            btn_PrevWork.setEnabled(false);
            btn_NextWork.setEnabled(false);
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
    private void btn_PrevWork_Click_Process()
            throws Exception
    {
        // input validation.
        pul_Station.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaWorkDisplaySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaWorkDisplaySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaWorkDisplaySCHParams inparam = new FaWorkDisplaySCHParams();
            inparam.set(FaWorkDisplaySCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_RestWork.setValue(outparam.get(FaWorkDisplaySCHParams.REST_WORK));
                txt_WorkNo.setValue(outparam.get(FaWorkDisplaySCHParams.WORK_NO));
                txt_Location.setValue(outparam.get(FaWorkDisplaySCHParams.LOCATION_NO));
                txt_CarryingFlag.setValue(outparam.get(FaWorkDisplaySCHParams.CARRY_FLAG));
                txt_InstructionDetail.setValue(outparam.get(FaWorkDisplaySCHParams.RETRIEVAL_DETAIL_NAME));
                txt_ItemCode.setValue(outparam.get(FaWorkDisplaySCHParams.ITEM_CODE));
                txt_ItemName.setValue(outparam.get(FaWorkDisplaySCHParams.ITEM_NAME));
                txt_LotNo.setValue(outparam.get(FaWorkDisplaySCHParams.LOT_NO));
                txt_StockQty.setValue(outparam.get(FaWorkDisplaySCHParams.STOCK_QTY));
                txt_WorkQty.setValue(outparam.get(FaWorkDisplaySCHParams.WORK_QTY));
                txt_StorageDate.setValue(outparam.get(FaWorkDisplaySCHParams.STORAGE_DATE));
                txt_StorageTime.setValue(outparam.get(FaWorkDisplaySCHParams.STORAGE_TIME));
                txt_BatchNo.setValue(outparam.get(FaWorkDisplaySCHParams.BATCH_NO));
                txt_SlipNumber.setValue(outparam.get(FaWorkDisplaySCHParams.TICKET_NO));
                txt_LineNo.setValue(outparam.get(FaWorkDisplaySCHParams.LINE_NO));
                viewState.setObject(ViewStateKeys.STATION_NO, _pdm_pul_Station.getSelectedValue());
                viewState.setObject(ViewStateKeys.CARRY_KEY, outparam.get(FaWorkDisplaySCHParams.CARRY_KEY));
                viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, outparam.get(FaWorkDisplaySCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.RETRIEVAL_DETAIL, outparam.get(FaWorkDisplaySCHParams.RETRIEVAL_DETAIL));
                viewState.setObject(ViewStateKeys.WAREHOUSE_NO, outparam.get(FaWorkDisplaySCHParams.WAREHOUSE_NO));
                viewState.setObject(ViewStateKeys.WORK_DISPLAY_OPERATE, outparam.get(FaWorkDisplaySCHParams.WORK_DISPLAY_OPERATE));
                viewState.setObject(ViewStateKeys.WORK_TYPE, outparam.get(FaWorkDisplaySCHParams.WORK_TYPE));
            }

            // clear.
            btn_PrevWork.setEnabled(false);
            btn_NextWork.setEnabled(true);
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
    private void btn_NextWork_Click_Process()
            throws Exception
    {
        // input validation.
        pul_Station.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaWorkDisplaySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaWorkDisplaySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaWorkDisplaySCHParams inparam = new FaWorkDisplaySCHParams();
            inparam.set(FaWorkDisplaySCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                _pdm_pul_Station.setSelectedValue(outparam.get(FaWorkDisplaySCHParams.STATION_NO));
                txt_RestWork.setValue(outparam.get(FaWorkDisplaySCHParams.REST_WORK));
                txt_WorkNo.setValue(outparam.get(FaWorkDisplaySCHParams.WORK_NO));
                txt_Location.setValue(outparam.get(FaWorkDisplaySCHParams.LOCATION_NO));
                txt_CarryingFlag.setValue(outparam.get(FaWorkDisplaySCHParams.CARRY_FLAG));
                txt_InstructionDetail.setValue(outparam.get(FaWorkDisplaySCHParams.RETRIEVAL_DETAIL_NAME));
                txt_ItemCode.setValue(outparam.get(FaWorkDisplaySCHParams.ITEM_CODE));
                txt_ItemName.setValue(outparam.get(FaWorkDisplaySCHParams.ITEM_NAME));
                txt_LotNo.setValue(outparam.get(FaWorkDisplaySCHParams.LOT_NO));
                txt_StockQty.setValue(outparam.get(FaWorkDisplaySCHParams.STOCK_QTY));
                txt_WorkQty.setValue(outparam.get(FaWorkDisplaySCHParams.WORK_QTY));
                txt_StorageDate.setValue(outparam.get(FaWorkDisplaySCHParams.STORAGE_DATE));
                txt_StorageTime.setValue(outparam.get(FaWorkDisplaySCHParams.STORAGE_TIME));
                txt_BatchNo.setValue(outparam.get(FaWorkDisplaySCHParams.BATCH_NO));
                txt_SlipNumber.setValue(outparam.get(FaWorkDisplaySCHParams.TICKET_NO));
                txt_LineNo.setValue(outparam.get(FaWorkDisplaySCHParams.LINE_NO));
                viewState.setObject(ViewStateKeys.STATION_NO, _pdm_pul_Station.getSelectedValue());
                viewState.setObject(ViewStateKeys.CARRY_KEY, outparam.get(FaWorkDisplaySCHParams.CARRY_KEY));
                viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, outparam.get(FaWorkDisplaySCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.RETRIEVAL_DETAIL, outparam.get(FaWorkDisplaySCHParams.RETRIEVAL_DETAIL));
                viewState.setObject(ViewStateKeys.WAREHOUSE_NO, outparam.get(FaWorkDisplaySCHParams.WAREHOUSE_NO));
                viewState.setObject(ViewStateKeys.WORK_DISPLAY_OPERATE, outparam.get(FaWorkDisplaySCHParams.WORK_DISPLAY_OPERATE));
                viewState.setObject(ViewStateKeys.WORK_TYPE, outparam.get(FaWorkDisplaySCHParams.WORK_TYPE));
            }

            // clear.
            btn_PrevWork.setEnabled(true);
            btn_NextWork.setEnabled(false);
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
