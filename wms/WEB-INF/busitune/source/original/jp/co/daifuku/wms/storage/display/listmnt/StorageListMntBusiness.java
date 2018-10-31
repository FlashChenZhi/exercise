// $Id: StorageListMntBusiness.java 7547 2010-03-15 00:26:14Z ota $
package jp.co.daifuku.wms.storage.display.listmnt;

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
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
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
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.storage.dasch.StorageListStartDASCH;
import jp.co.daifuku.wms.storage.dasch.StorageListStartDASCHParams;
import jp.co.daifuku.wms.storage.display.listmnt.StorageListMnt;
import jp.co.daifuku.wms.storage.display.listmnt.ViewStateKeys;
import jp.co.daifuku.wms.storage.exporter.StorageStartListParams;
import jp.co.daifuku.wms.storage.schedule.StorageListMntSCH;
import jp.co.daifuku.wms.storage.schedule.StorageListMntSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7547 $, $Date:: 2010-03-15 09:26:14 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class StorageListMntBusiness
        extends StorageListMnt
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_StorageListMaintenance(LST_COLUMN_1) */
    private static final ListCellKey KEY_LST_COLUMN_1 = new ListCellKey("LST_COLUMN_1", new CheckBoxColumn(), true, true);

    /** lst_StorageListMaintenance(LST_LIST) */
    private static final ListCellKey KEY_LST_LIST = new ListCellKey("LST_LIST", new StringCellColumn(), true, false);

    /** lst_StorageListMaintenance(LST_PLAN_DATE) */
    private static final ListCellKey KEY_LST_PLAN_DATE = new ListCellKey("LST_PLAN_DATE", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_StorageListMaintenance(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_StorageListMaintenance(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_StorageListMaintenance keys */
    private static final ListCellKey[] LST_STORAGELISTMAINTENANCE_KEYS = {
        KEY_LST_COLUMN_1,
        KEY_LST_LIST,
        KEY_LST_PLAN_DATE,
        KEY_LST_ITEM_CODE,
        KEY_LST_ITEM_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_StorageListMaintenance */
    private ListCellModel _lcm_lst_StorageListMaintenance;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public StorageListMntBusiness()
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
    public void btn_Display_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Display_Click_Process();
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
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_RePrint_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_RePrint_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_WorkCancel_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_WorkCancel_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
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
     * @throws Exception
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
     * @throws Exception
     */
    public void btn_ListClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ListClear_Click_Process();
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
        Locale locale = httpRequest.getLocale();

        // initialize lst_StorageListMaintenance.
        _lcm_lst_StorageListMaintenance = new ListCellModel(lst_StorageListMaintenance, LST_STORAGELISTMAINTENANCE_KEYS, locale);
        _lcm_lst_StorageListMaintenance.setToolTipVisible(KEY_LST_COLUMN_1, true);
        _lcm_lst_StorageListMaintenance.setToolTipVisible(KEY_LST_LIST, true);
        _lcm_lst_StorageListMaintenance.setToolTipVisible(KEY_LST_PLAN_DATE, true);
        _lcm_lst_StorageListMaintenance.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_StorageListMaintenance.setToolTipVisible(KEY_LST_ITEM_NAME, true);
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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_StorageListMaintenance_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_StoragePlanDate);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_RePrint.setEnabled(false);
        btn_WorkCancel.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_ListClear.setEnabled(false);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_StoragePlanDate.validate(this, false);
        txt_ItemCodeFrom.validate(this, false);
        txt_ItemCodeTo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StorageListMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StorageListMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            StorageListMntSCHParams inparam = new StorageListMntSCHParams();
            inparam.set(StorageListMntSCHParams.PLAN_DAY, txt_StoragePlanDate.getValue());
            inparam.set(StorageListMntSCHParams.FROM_ITEM_CODE, txt_ItemCodeFrom.getValue());
            inparam.set(StorageListMntSCHParams.TO_ITEM_CODE, txt_ItemCodeTo.getValue());
            inparam.set(StorageListMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_StorageListMaintenance.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StorageListMaintenance.getNewLine();
                line.setValue(KEY_LST_LIST, outparam.get(StorageListMntSCHParams.SETTING_UNIT_KEY));
                line.setValue(KEY_LST_PLAN_DATE, outparam.get(StorageListMntSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(StorageListMntSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(StorageListMntSCHParams.ITEM_NAME));
                viewState.setObject(ViewStateKeys.PLAN_DAY, txt_StoragePlanDate.getValue());
                viewState.setObject(ViewStateKeys.FROM_ITEM_CODE, txt_ItemCodeFrom.getValue());
                viewState.setObject(ViewStateKeys.TO_ITEM_CODE, txt_ItemCodeTo.getValue());
                lst_StorageListMaintenance_SetLineToolTip(line);
                _lcm_lst_StorageListMaintenance.add(line);
            }

            // clear.
            btn_RePrint.setEnabled(true);
            btn_WorkCancel.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_ListClear.setEnabled(true);
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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_StoragePlanDate.setValue(null);
        txt_ItemCodeFrom.setValue(null);
        txt_ItemCodeTo.setValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_RePrint_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_StorageListMaintenance.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_StorageListMaintenance.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003001");
            return;
        }

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StorageListStartDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new StorageListStartDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            main: for (int i = 1; i <= _lcm_lst_StorageListMaintenance.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_StorageListMaintenance.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                // set input parameters.
                StorageListStartDASCHParams inparam = new StorageListStartDASCHParams();
                inparam.set(StorageListStartDASCHParams.SETTING_UNIT_KEY, line.getValue(KEY_LST_LIST));
                inparam.set(StorageListStartDASCHParams.PLAN_DAY, line.getValue(KEY_LST_PLAN_DATE));
                inparam.set(StorageListStartDASCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                inparam.set(StorageListStartDASCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));

                // check count.
                int count = dasch.count(inparam);
                if (count == 0)
                {
                    message.setMsgResourceKey("6003011");
                    return;
                }

                // DASCH call.
                dasch.search(inparam);

                // open exporter.
                if (exporter == null)
                {
                    ExporterFactory factory = new WmsExporterFactory(locale, ui);
                    exporter = factory.newPrinterExporter("StorageStartList", false);
                    exporter.open();
                }

                // export.
                while (dasch.next())
                {
                    Params outparam = dasch.get();
                    StorageStartListParams expparam = new StorageStartListParams();
                    expparam.set(StorageStartListParams.DFK_DS_NO, outparam.get(StorageListStartDASCHParams.DFK_DS_NO));
                    expparam.set(StorageStartListParams.DFK_USER_ID, outparam.get(StorageListStartDASCHParams.DFK_USER_ID));
                    expparam.set(StorageStartListParams.DFK_USER_NAME, outparam.get(StorageListStartDASCHParams.DFK_USER_NAME));
                    expparam.set(StorageStartListParams.SYS_DAY, outparam.get(StorageListStartDASCHParams.SYS_DAY));
                    expparam.set(StorageStartListParams.SYS_TIME, outparam.get(StorageListStartDASCHParams.SYS_TIME));
                    expparam.set(StorageStartListParams.JOB_NO, outparam.get(StorageListStartDASCHParams.JOB_NO));
                    expparam.set(StorageStartListParams.PLAN_DAY, outparam.get(StorageListStartDASCHParams.PLAN_DAY));
                    expparam.set(StorageStartListParams.ITEM_CODE, outparam.get(StorageListStartDASCHParams.ITEM_CODE));
                    expparam.set(StorageStartListParams.ITEM_NAME, outparam.get(StorageListStartDASCHParams.ITEM_NAME));
                    expparam.set(StorageStartListParams.PLAN_AREA_NO, outparam.get(StorageListStartDASCHParams.PLAN_AREA_NO));
                    expparam.set(StorageStartListParams.PLAN_LOCATION_NO, outparam.get(StorageListStartDASCHParams.PLAN_LOCATION_NO));
                    expparam.set(StorageStartListParams.PLAN_LOT_NO, outparam.get(StorageListStartDASCHParams.PLAN_LOT_NO));
                    expparam.set(StorageStartListParams.ENTERING_QTY, outparam.get(StorageListStartDASCHParams.ENTERING_QTY));
                    expparam.set(StorageStartListParams.PLAN_CASE_QTY, outparam.get(StorageListStartDASCHParams.PLAN_CASE_QTY));
                    expparam.set(StorageStartListParams.PLAN_PIECE_QTY, outparam.get(StorageListStartDASCHParams.PLAN_PIECE_QTY));
                    if (!exporter.write(expparam))
                    {
                        break main;
                    }
                }
            }

            // execute print.
            try
            {
                exporter.print();
                message.setMsgResourceKey("6001010");
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                message.setMsgResourceKey("6007034");
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_StorageListMaintenance.size(); i++)
            {
                ListCellLine line = _lcm_lst_StorageListMaintenance.get(i);
                lst_StorageListMaintenance.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_LIST), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_DATE), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PRINT), part11List);
            }
            // commit.
            conn.commit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_WorkCancel_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_StorageListMaintenance.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_StorageListMaintenance.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003001");
            return;
        }

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StorageListMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StorageListMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_StorageListMaintenance.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_StorageListMaintenance.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                StorageListMntSCHParams lineparam = new StorageListMntSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(StorageListMntSCHParams.SETTING_UNIT_KEY, line.getValue(KEY_LST_LIST));
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_StorageListMaintenance.resetEditRow();
                _lcm_lst_StorageListMaintenance.resetHighlight();
                _lcm_lst_StorageListMaintenance.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_StorageListMaintenance.size(); i++)
            {
                ListCellLine line = _lcm_lst_StorageListMaintenance.get(i);
                lst_StorageListMaintenance.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_LIST), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_DATE), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_StorageListMaintenance.resetEditRow();
            _lcm_lst_StorageListMaintenance.resetHighlight();

            // set input parameters.
            StorageListMntSCHParams inparam = new StorageListMntSCHParams();
            inparam.set(StorageListMntSCHParams.FROM_ITEM_CODE, viewState.getObject(ViewStateKeys.FROM_ITEM_CODE));
            inparam.set(StorageListMntSCHParams.PLAN_DAY, viewState.getObject(ViewStateKeys.PLAN_DAY));
            inparam.set(StorageListMntSCHParams.TO_ITEM_CODE, viewState.getObject(ViewStateKeys.TO_ITEM_CODE));
            inparam.set(StorageListMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_StorageListMaintenance.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StorageListMaintenance.getNewLine();
                line.setValue(KEY_LST_LIST, outparam.get(StorageListMntSCHParams.SETTING_UNIT_KEY));
                line.setValue(KEY_LST_PLAN_DATE, outparam.get(StorageListMntSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(StorageListMntSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(StorageListMntSCHParams.ITEM_NAME));
                lst_StorageListMaintenance_SetLineToolTip(line);
                _lcm_lst_StorageListMaintenance.add(line);
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
     * @throws Exception
     */
    private void btn_AllCheck_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_StorageListMaintenance.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_StorageListMaintenance.get(i);
            lst_StorageListMaintenance.setCurrentRow(i);
            clearLine.setValue(KEY_LST_COLUMN_1, Boolean.TRUE);
            lst_StorageListMaintenance_SetLineToolTip(clearLine);
            _lcm_lst_StorageListMaintenance.set(i, clearLine);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllCheckClear_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_StorageListMaintenance.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_StorageListMaintenance.get(i);
            lst_StorageListMaintenance.setCurrentRow(i);
            clearLine.setValue(KEY_LST_COLUMN_1, Boolean.FALSE);
            lst_StorageListMaintenance_SetLineToolTip(clearLine);
            _lcm_lst_StorageListMaintenance.set(i, clearLine);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ListClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_StorageListMaintenance.clear();
        btn_RePrint.setEnabled(false);
        btn_WorkCancel.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_ListClear.setEnabled(false);
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
