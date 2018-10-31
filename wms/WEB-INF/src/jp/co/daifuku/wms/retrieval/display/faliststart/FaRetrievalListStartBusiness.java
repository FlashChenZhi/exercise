// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.retrieval.display.faliststart;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.Constants;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.retrieval.dasch.FaRetrievalListStartDASCH;
import jp.co.daifuku.wms.retrieval.dasch.FaRetrievalListStartDASCHParams;
import jp.co.daifuku.wms.retrieval.exporter.RetrievalWorkListParams;
import jp.co.daifuku.wms.retrieval.schedule.FaRetrievalListStartSCH;
import jp.co.daifuku.wms.retrieval.schedule.FaRetrievalListStartSCHParams;
import jp.co.daifuku.wms.stock.schedule.FaNoPlanStorageSCH;
import jp.co.daifuku.wms.stock.schedule.FaNoPlanStorageSCHParams;



/**
 * 出庫リスト作業開始(FA)の画面処理を行います。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class FaRetrievalListStartBusiness
        extends FaRetrievalListStart
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

    /** lst_FaRetListstart(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_FaRetListstart(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO = new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_FaRetListstart(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO = new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_FaRetListstart(LST_AREA_NAME) */
    private static final ListCellKey KEY_LST_AREA_NAME = new ListCellKey("LST_AREA_NAME", new StringCellColumn(), true, false);

    /** lst_FaRetListstart(LST_NO_OF_RECORDS) */
    private static final ListCellKey KEY_LST_NO_OF_RECORDS = new ListCellKey("LST_NO_OF_RECORDS", new NumberCellColumn(0), true, false);

    /** lst_FaRetListstart keys */
    private static final ListCellKey[] LST_FARETLISTSTART_KEYS = {
        KEY_LST_SELECT,
        KEY_LST_BATCH_NO,
        KEY_LST_AREA_NO,
        KEY_LST_AREA_NAME,
        KEY_LST_NO_OF_RECORDS,
    };

    //DFKLOOK start
    /** FROM_BATCH_NO */
    public static final String FROM_BATCH_NO = "FROM_BATCH_NO";
    /** TO_BATCH_NO */
    public static final String TO_BATCH_NO = "TO_BATCH_NO";
    //DFKLOOK end
    
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_FaRetListstart */
    private ListCellModel _lcm_lst_FaRetListstart;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaRetrievalListStartBusiness()
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
       if (eventSource.startsWith("btn_Start_Click"))
       {
           btn_Start_Click_Process(eventSource);
       }
       // DFKLOOK ここまで修正
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
    public void btn_Start_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK start
        // process call.
        btn_Start_Click_Process(null);
        //DFKLOOK end
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
    public void btn_AllClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllClear_Click_Process();
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

        // initialize lst_FaRetListstart.
        _lcm_lst_FaRetListstart = new ListCellModel(lst_FaRetListstart, LST_FARETLISTSTART_KEYS, locale);
        _lcm_lst_FaRetListstart.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_FaRetListstart.setToolTipVisible(KEY_LST_BATCH_NO, false);
        _lcm_lst_FaRetListstart.setToolTipVisible(KEY_LST_AREA_NO, false);
        _lcm_lst_FaRetListstart.setToolTipVisible(KEY_LST_AREA_NAME, false);
        _lcm_lst_FaRetListstart.setToolTipVisible(KEY_LST_NO_OF_RECORDS, false);

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
    private void lst_FaRetListstart_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_FromBatchNo);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_Start.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        chk_WorkListPrint.setEnabled(false);
        
        // DFKLOOK start 作業リストチェックボックスのチェック処理
        // デフォルトとして作業リスト発行にチェック
        chk_WorkListPrint.setChecked(true);
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();
        
        Connection conn = null;
        FaNoPlanStorageSCH sch = null;
        
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // 画面定義テーブルをチェック
            sch = new FaNoPlanStorageSCH(conn, this.getClass(), locale, ui);
            
            FaNoPlanStorageSCHParams inparam = new FaNoPlanStorageSCHParams();
            inparam.set(FaNoPlanStorageSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));
            
            Params outParam = sch.initFind(inparam);
            
            if (outParam != null)
            {
                String printflg = outParam.getString(FaNoPlanStorageSCHParams.PRINT_FLAG);
                if (SystemDefine.KEYDATA_OFF.equals(printflg))
                {
                    // 前回、チェックOFFだった場合は更新
                    chk_WorkListPrint.setChecked(false);
                }
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
        // DFKLOOK end
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_FromBatchNo.validate(this, false);
        txt_ToBatchNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaRetrievalListStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaRetrievalListStartSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaRetrievalListStartSCHParams inparam = new FaRetrievalListStartSCHParams();
            inparam.set(FaRetrievalListStartSCHParams.FROM_BATCH_NO, txt_FromBatchNo.getValue());
            inparam.set(FaRetrievalListStartSCHParams.TO_BATCH_NO, txt_ToBatchNo.getValue());
            inparam.set(FaRetrievalListStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_FaRetListstart.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_FaRetListstart.getNewLine();
                line.setValue(KEY_LST_BATCH_NO, outparam.get(FaRetrievalListStartSCHParams.BATCH_NO));
                line.setValue(KEY_LST_AREA_NO, outparam.get(FaRetrievalListStartSCHParams.AREA_NO));
                line.setValue(KEY_LST_AREA_NAME, outparam.get(FaRetrievalListStartSCHParams.AREA_NAME));
                line.setValue(KEY_LST_NO_OF_RECORDS, outparam.get(FaRetrievalListStartSCHParams.NO_OF_RECORDS));
                lst_FaRetListstart_SetLineToolTip(line);
                _lcm_lst_FaRetListstart.add(line);
            }

            // DFKLOOK start
            boolean hasData = !outparams.isEmpty();
            
            // viewStateに検索する値を保持
            if(hasData)
            {
	            viewState.setObject(FROM_BATCH_NO, txt_FromBatchNo.getValue());
	            viewState.setObject(TO_BATCH_NO, txt_ToBatchNo.getValue());
            }
            
            // clear.
            btn_Start.setEnabled(hasData);
            btn_AllCheck.setEnabled(hasData);
            btn_AllCheckClear.setEnabled(hasData);
            btn_AllClear.setEnabled(hasData);
            chk_WorkListPrint.setEnabled(hasData);
            // DFKLOOK end

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
        txt_FromBatchNo.setValue(null);
        txt_ToBatchNo.setValue(null);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Start_Click_Process(String eventSource)
            throws Exception
    {
        // DFKLOOK start
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_FaRetListstart.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_FaRetListstart.get(i);
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

        if (StringUtil.isBlank(eventSource))
        {
            // show confirm message. 開始しますか？
            this.setConfirm("MSG-W0031", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Start_Click");
            return;
        }
        // DFKLOOK end
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaRetrievalListStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaRetrievalListStartSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_FaRetListstart.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_FaRetListstart.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                FaRetrievalListStartSCHParams lineparam = new FaRetrievalListStartSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(FaRetrievalListStartSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                lineparam.set(FaRetrievalListStartSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                lineparam.set(FaRetrievalListStartSCHParams.AREA_NO, line.getValue(KEY_LST_AREA_NO));
                lineparam.set(FaRetrievalListStartSCHParams.AREA_NAME, line.getValue(KEY_LST_AREA_NAME));
                lineparam.set(FaRetrievalListStartSCHParams.NO_OF_RECORDS, line.getValue(KEY_LST_NO_OF_RECORDS));
                lineparam.set(FaRetrievalListStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                // DFKLOOK start
                lineparam.set(FaRetrievalListStartSCHParams.PRINT_FLAG, chk_WorkListPrint.getValue());
                lineparam.set(FaRetrievalListStartSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));
                // DFKLOOK end
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // DFKLOOK start
            // 帳票発行のため startSCH -> startSCHgetParams に変更
            if(inparams.length == 0)
            {
                message.setMsgResourceKey("6003001");
                return;
            }
            // SCH call.
            List<Params> schparams = sch.startSCHgetParams(inparams);
            if (schparams == null)
            // DFKLOOK end
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_FaRetListstart.resetEditRow();
                _lcm_lst_FaRetListstart.resetHighlight();
                _lcm_lst_FaRetListstart.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_FaRetListstart.size(); i++)
            {
                ListCellLine line = _lcm_lst_FaRetListstart.get(i);
                lst_FaRetListstart.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                if (chk_WorkListPrint.getChecked())
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                }

                if (lst_FaRetListstart.getChecked(_lcm_lst_FaRetListstart.getColumnIndex(KEY_LST_SELECT)))
                {
                    part11List.add(line.getViewString(KEY_LST_BATCH_NO), "");
                    part11List.add(line.getViewString(KEY_LST_AREA_NO), "");
                }

                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK start 
            // 帳票発行処理
            Boolean blnPrint = true;
            if (chk_WorkListPrint.getChecked())
            {
                // process call.
                blnPrint = startPrint(schparams);
            }

            // set input parameters.
            FaRetrievalListStartSCHParams queryparam = new FaRetrievalListStartSCHParams();
            queryparam.set(FaRetrievalListStartSCHParams.FROM_BATCH_NO, viewState.getObject(FROM_BATCH_NO));
            queryparam.set(FaRetrievalListStartSCHParams.TO_BATCH_NO, viewState.getObject(TO_BATCH_NO));
            queryparam.set(FaRetrievalListStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(queryparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_FaRetListstart.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_FaRetListstart.getNewLine();
                line.setValue(KEY_LST_BATCH_NO, outparam.get(FaRetrievalListStartSCHParams.BATCH_NO));
                line.setValue(KEY_LST_AREA_NO, outparam.get(FaRetrievalListStartSCHParams.AREA_NO));
                line.setValue(KEY_LST_AREA_NAME, outparam.get(FaRetrievalListStartSCHParams.AREA_NAME));
                line.setValue(KEY_LST_NO_OF_RECORDS, outparam.get(FaRetrievalListStartSCHParams.NO_OF_RECORDS));
                lst_FaRetListstart_SetLineToolTip(line);
                _lcm_lst_FaRetListstart.add(line);
            }

            if (blnPrint == true)
            {
                // 6021021 = 開始しました。
                message.setMsgResourceKey("6021021");
            }
            else
            {
                // 6007042 = 設定後、印刷に失敗しました。ログを参照してください。
                message.setMsgResourceKey("6007042");
            }

            if (outparams.size() == 0)
            {
                // reset editing row.
                _lcm_lst_FaRetListstart.resetEditRow();
                _lcm_lst_FaRetListstart.resetHighlight();

                // clear.
                _lcm_lst_FaRetListstart.clear();
                btn_Start.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_AllClear.setEnabled(false);
                chk_WorkListPrint.setEnabled(false);
            }
            // DFKLOOK end
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
        for (int i = 1; i <= _lcm_lst_FaRetListstart.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_FaRetListstart.get(i);
            lst_FaRetListstart.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_FaRetListstart_SetLineToolTip(clearLine);
            _lcm_lst_FaRetListstart.set(i, clearLine);
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
        for (int i = 1; i <= _lcm_lst_FaRetListstart.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_FaRetListstart.get(i);
            lst_FaRetListstart.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_FaRetListstart_SetLineToolTip(clearLine);
            _lcm_lst_FaRetListstart.set(i, clearLine);
        }

    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_FaRetListstart.clear();
        btn_Start.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        chk_WorkListPrint.setEnabled(false);

    }

    // DFKLOOK start
    /**
     * 印刷処理を行います。
     * @throws Exception
     */
    private boolean startPrint(List<Params> schparams)
            throws Exception
    {

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;

        PrintExporter exporter = null;
        FaRetrievalListStartDASCH dasch = null;

        try
        {
            // set input parameters.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new FaRetrievalListStartDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // 設定単位キーの取得
            Iterator<Params> it = schparams.iterator();
            while (it.hasNext())
            {
                // スケジュールから受け取ったパラメータを編集
                FaRetrievalListStartDASCHParams printparam = new FaRetrievalListStartDASCHParams();
                Params pNextparam = (Params)it.next();
                printparam.set(FaRetrievalListStartDASCHParams.SETTING_UNIT_KEY,
                        pNextparam.get(FaRetrievalListStartSCHParams.SETTING_UNIT_KEY));

                // check count.
                int count = dasch.count(printparam);
                if (count == 0)
                {
                    message.setMsgResourceKey("6003011");
                    return false;
                }

                // DASCH call.
                dasch.search(printparam);

                // open exporter.
                if (exporter == null)
                {
                    ExporterFactory factory = new WmsExporterFactory(locale, ui);
                    exporter = factory.newPrinterExporter("RetrievalWorkList", false);
                    exporter.open();
                }

                // export.
                while (dasch.next())
                {
                    Params outparam = dasch.get();
                    RetrievalWorkListParams expparam = new RetrievalWorkListParams();
                    expparam.set(RetrievalWorkListParams.DFK_DS_NO, outparam.get(FaRetrievalListStartDASCHParams.DFK_DS_NO));
                    expparam.set(RetrievalWorkListParams.DFK_USER_ID, outparam.get(FaRetrievalListStartDASCHParams.DFK_USER_ID));
                    expparam.set(RetrievalWorkListParams.DFK_USER_NAME, outparam.get(FaRetrievalListStartDASCHParams.DFK_USER_NAME));
                    expparam.set(RetrievalWorkListParams.SYS_DAY, outparam.get(FaRetrievalListStartDASCHParams.SYS_DAY));
                    expparam.set(RetrievalWorkListParams.SYS_TIME, outparam.get(FaRetrievalListStartDASCHParams.SYS_TIME));
                    expparam.set(RetrievalWorkListParams.LINE_NO, outparam.get(FaRetrievalListStartDASCHParams.LINE_NO));
                    expparam.set(RetrievalWorkListParams.AREA_NO, outparam.get(FaRetrievalListStartDASCHParams.AREA_NO));
                    expparam.set(RetrievalWorkListParams.LOCATION_NO, outparam.get(FaRetrievalListStartDASCHParams.LOCATION_NO));
                    expparam.set(RetrievalWorkListParams.ITEM_CODE, outparam.get(FaRetrievalListStartDASCHParams.ITEM_CODE));
                    expparam.set(RetrievalWorkListParams.ITEM_NAME, outparam.get(FaRetrievalListStartDASCHParams.ITEM_NAME));
                    expparam.set(RetrievalWorkListParams.LOT_NO, outparam.get(FaRetrievalListStartDASCHParams.LOT_NO));
                    expparam.set(RetrievalWorkListParams.WORK_QTY, outparam.get(FaRetrievalListStartDASCHParams.WORK_QTY));
                    expparam.set(RetrievalWorkListParams.STOCK_QTY, outparam.get(FaRetrievalListStartDASCHParams.STOCK_QTY));
                    expparam.set(RetrievalWorkListParams.BATCH_NO, outparam.get(FaRetrievalListStartDASCHParams.BATCH_NO));
                    expparam.set(RetrievalWorkListParams.TICKET_NO, outparam.get(FaRetrievalListStartDASCHParams.TICKET_NO));
                    expparam.set(RetrievalWorkListParams.LIST_NO, outparam.get(FaRetrievalListStartDASCHParams.LIST_NO));

                   if (!exporter.write(expparam))
                    {
                        break;
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
                return false;
            }

            return true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            return false;
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
            DBUtil.close(conn);
        }
    }
    // DFKLOOK end

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
