// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.retrieval.display.falistcomplete;

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
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
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
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsChecker;
import jp.co.daifuku.wms.retrieval.schedule.FaRetrievalListCompleteSCH;
import jp.co.daifuku.wms.retrieval.schedule.FaRetrievalListCompleteSCHParams;

/**
 * 出庫リスト作業結果入力の画面処理を行います。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class FaRetrievalListCompleteBusiness
        extends FaRetrievalListComplete
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

    /** lst_FaRetListComplatte(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE = new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_FaRetListComplatte(HIDDEN_COLLECT_JOBNO) */
    private static final ListCellKey KEY_HIDDEN_COLLECT_JOBNO = new ListCellKey("HIDDEN_COLLECT_JOBNO", new StringCellColumn(), false, false);

    /** lst_FaRetListComplatte(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO = new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_FaRetListComplatte(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO = new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_FaRetListComplatte(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_FaRetListComplatte(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_FaRetListComplatte(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, true);

    /** lst_FaRetListComplatte(LST_PLAN_QTY) */
    private static final ListCellKey KEY_LST_PLAN_QTY = new ListCellKey("LST_PLAN_QTY", new NumberCellColumn(0), true, false);

    /** lst_FaRetListComplatte(LST_PICKING_QTY) */
    private static final ListCellKey KEY_LST_PICKING_QTY = new ListCellKey("LST_PICKING_QTY", new NumberCellColumn(0), true, true);

    /** lst_FaRetListComplatte(LST_SHORTAGE_FLAG) */
    private static final ListCellKey KEY_LST_SHORTAGE_FLAG = new ListCellKey("LST_SHORTAGE_FLAG", new CheckBoxColumn(), true, true);

    /** lst_FaRetListComplatte keys */
    private static final ListCellKey[] LST_FARETLISTCOMPLATTE_KEYS = {
        KEY_HIDDEN_CONSIGNOR_CODE,
        KEY_HIDDEN_COLLECT_JOBNO,
        KEY_LST_AREA_NO,
        KEY_LST_LOCATION_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_ITEM_NAME,
        KEY_LST_LOT_NO,
        KEY_LST_PLAN_QTY,
        KEY_LST_PICKING_QTY,
        KEY_LST_SHORTAGE_FLAG,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_FaRetListComplatte */
    private ListCellModel _lcm_lst_FaRetListComplatte;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaRetrievalListCompleteBusiness()
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
       if (eventSource.startsWith("btn_Complete_Click"))
       {
           btn_Complete_Click_Process(eventSource);
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
    public void btn_Complete_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK start
        // process call.
        btn_Complete_Click_Process(null);
        // DFKLOOK end
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ClearListInput_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ClearListInput_Click_Process();
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

        // initialize lst_FaRetListComplatte.
        _lcm_lst_FaRetListComplatte = new ListCellModel(lst_FaRetListComplatte, LST_FARETLISTCOMPLATTE_KEYS, locale);
        _lcm_lst_FaRetListComplatte.setToolTipVisible(KEY_LST_AREA_NO, true);
        _lcm_lst_FaRetListComplatte.setToolTipVisible(KEY_LST_LOCATION_NO, true);
        _lcm_lst_FaRetListComplatte.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_FaRetListComplatte.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_FaRetListComplatte.setToolTipVisible(KEY_LST_LOT_NO, false);
        _lcm_lst_FaRetListComplatte.setToolTipVisible(KEY_LST_PLAN_QTY, true);
        _lcm_lst_FaRetListComplatte.setToolTipVisible(KEY_LST_PICKING_QTY, false);
        _lcm_lst_FaRetListComplatte.setToolTipVisible(KEY_LST_SHORTAGE_FLAG, true);

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
    private void lst_FaRetListComplatte_SetLineToolTip(ListCellLine line)
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
        setFocus(txt_SearchListWorkNo);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        txt_ListWorkNo.setReadOnly(true);
        txt_BatchNo.setReadOnly(true);
        btn_Complete.setEnabled(false);
        btn_ClearListInput.setEnabled(false);
        btn_AllClear.setEnabled(false);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_SearchListWorkNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaRetrievalListCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaRetrievalListCompleteSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaRetrievalListCompleteSCHParams inparam = new FaRetrievalListCompleteSCHParams();
            inparam.set(FaRetrievalListCompleteSCHParams.SEARCH_LIST_WORK_NO, txt_SearchListWorkNo.getValue());
            inparam.set(FaRetrievalListCompleteSCHParams.INITIAL_INPUT, chk_InitialInput.getValue());
            inparam.set(FaRetrievalListCompleteSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK start
            // 表示データが無かったときの処理
            if(outparams.size() == 0)
            {
            	return;
            }
            // DFKLOOK end
            
            // output display.
            _lcm_lst_FaRetListComplatte.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_FaRetListComplatte.getNewLine();
                txt_ListWorkNo.setValue(txt_SearchListWorkNo.getValue());
                txt_BatchNo.setValue(outparam.get(FaRetrievalListCompleteSCHParams.BATCH_NO));
                line.setValue(KEY_LST_AREA_NO, outparam.get(FaRetrievalListCompleteSCHParams.AREA_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(FaRetrievalListCompleteSCHParams.LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(FaRetrievalListCompleteSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(FaRetrievalListCompleteSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(FaRetrievalListCompleteSCHParams.LOT_NO));
                line.setValue(KEY_LST_PLAN_QTY, outparam.get(FaRetrievalListCompleteSCHParams.PLAN_QTY));
                line.setValue(KEY_LST_PICKING_QTY, outparam.get(FaRetrievalListCompleteSCHParams.PICKING_QTY));
                line.setValue(KEY_HIDDEN_CONSIGNOR_CODE, outparam.get(FaRetrievalListCompleteSCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_HIDDEN_COLLECT_JOBNO, outparam.get(FaRetrievalListCompleteSCHParams.COLLECT_JOBNO));
                lst_FaRetListComplatte_SetLineToolTip(line);
                _lcm_lst_FaRetListComplatte.add(line);
            }

            // clear.
            btn_Complete.setEnabled(true);
            btn_ClearListInput.setEnabled(true);
            btn_AllClear.setEnabled(true);

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
        txt_SearchListWorkNo.setValue(null);
        chk_InitialInput.setChecked(false);

    }

    /**
     *
     * @throws Exception
     */
    // DFKLOOK start
    private void btn_Complete_Click_Process(String eventSource)
            throws Exception
    // DFKLOOK end 
    {
        // DFKLOOK start
        // reset editing row.
        _lcm_lst_FaRetListComplatte.resetEditRow();
        _lcm_lst_FaRetListComplatte.resetHighlight();
        // DFKLOOK end
        
        // input validation.
        for (int i = 1; i <= _lcm_lst_FaRetListComplatte.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_FaRetListComplatte.get(i);
//            if (!(checkline.isAppend() || checkline.isEdited()))
//            {
//                continue;
//            }

            lst_FaRetListComplatte.setCurrentRow(i);
            lst_FaRetListComplatte.validate(checkline.getIndex(KEY_LST_LOT_NO), false);
            lst_FaRetListComplatte.validate(checkline.getIndex(KEY_LST_PICKING_QTY), false);
            
            // DFKLOOK start
            if (!Boolean.valueOf(checkline.getStringValue(KEY_LST_SHORTAGE_FLAG)).booleanValue())
            {
                // 出庫数の入力チェック
                if (checkline.getNumberValue(KEY_LST_PICKING_QTY).intValue() == 0)
                {
                    // 6023281=No.{0} 出庫数には1以上の値を入力してください。
                    message.setMsgResourceKey(WmsMessageFormatter.format(6023281, i));
                    DisplayUtil.addHighlight(lst_FaRetListComplatte, i, ControlColor.Warning);
                    return;
                }
            }
            
            // 出庫数の入力チェック
            if (checkline.getNumberValue(KEY_LST_PLAN_QTY).intValue() < checkline.getNumberValue(KEY_LST_PICKING_QTY).intValue())
            {
                // 6023034=No.{0} 出庫数には出庫予定数以下の値を入力してください。
                message.setMsgResourceKey(WmsMessageFormatter.format(6023192, i));
                DisplayUtil.addHighlight(lst_FaRetListComplatte, i, ControlColor.Warning);
                return;
            }
            
            // 欠品チェック
            if ((checkline.getNumberValue(KEY_LST_PICKING_QTY).intValue() < checkline.getNumberValue(KEY_LST_PLAN_QTY).intValue())
                    && !Boolean.valueOf(checkline.getStringValue(KEY_LST_SHORTAGE_FLAG)).booleanValue())
            {
                // 6023047=No.{0} 出庫数が予定数未満です。欠品を選択してください。
                message.setMsgResourceKey(WmsMessageFormatter.format(6023047, i));
                DisplayUtil.addHighlight(lst_FaRetListComplatte, i, ControlColor.Warning);
                return;
            }
            if (Boolean.valueOf(checkline.getStringValue(KEY_LST_SHORTAGE_FLAG)).booleanValue()
                    && checkline.getNumberValue(KEY_LST_PLAN_QTY).intValue() <= checkline.getNumberValue(
                            KEY_LST_PICKING_QTY).intValue())
            {
                // 6023250=No.{0} 完了数に作業予定数以上の値を入力した場合、欠品は指定できません。
                message.setMsgResourceKey(WmsMessageFormatter.format(6023250, i));
                DisplayUtil.addHighlight(lst_FaRetListComplatte, i, ControlColor.Warning);
                return;
            }
            // DFKLOOK end
        }

        // DFKLOOK start
        if (StringUtil.isBlank(eventSource))
        {
            // show confirm message. 完了しますか？
            this.setConfirm("MSG-W0043", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Complete_Click");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaRetrievalListCompleteSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaRetrievalListCompleteSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_FaRetListComplatte.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_FaRetListComplatte.get(i);
                
                // DFKLOOK start 全てのデータが対象なのでコメント化
                //if (!(line.isAppend() || line.isEdited()))
                //{
                //    continue;
                //}
                // DFKLOOK end

                FaRetrievalListCompleteSCHParams lineparam = new FaRetrievalListCompleteSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(FaRetrievalListCompleteSCHParams.AREA_NO, line.getValue(KEY_LST_AREA_NO));
                lineparam.set(FaRetrievalListCompleteSCHParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
                lineparam.set(FaRetrievalListCompleteSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(FaRetrievalListCompleteSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(FaRetrievalListCompleteSCHParams.LOT_NO, line.getValue(KEY_LST_LOT_NO));
                lineparam.set(FaRetrievalListCompleteSCHParams.PLAN_QTY, line.getValue(KEY_LST_PLAN_QTY));
                lineparam.set(FaRetrievalListCompleteSCHParams.PICKING_QTY, line.getValue(KEY_LST_PICKING_QTY));
                lineparam.set(FaRetrievalListCompleteSCHParams.SHORTAGE_FLAG, line.getValue(KEY_LST_SHORTAGE_FLAG));
                lineparam.set(FaRetrievalListCompleteSCHParams.LIST_WORK_NO, txt_ListWorkNo.getValue());
                lineparam.set(FaRetrievalListCompleteSCHParams.BATCH_NO, txt_BatchNo.getValue());
                lineparam.set(FaRetrievalListCompleteSCHParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                lineparam.set(FaRetrievalListCompleteSCHParams.COLLECT_JOBNO, line.getValue(KEY_HIDDEN_COLLECT_JOBNO));
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // DFKLOOK start
            // ハイライトのリセット
            _lcm_lst_FaRetListComplatte.resetEditRow();
            _lcm_lst_FaRetListComplatte.resetHighlight();

            // リスト件数分繰り返す
            for (ScheduleParams p : inparams)
            {
                // リスト入力チェックを行う
                if (!sch.check(p, p.getRowIndex()))
                {
                    // ハイライト
                    _lcm_lst_FaRetListComplatte.addHighlight(p.getRowIndex(), ControlColor.Warning);

                    // メッセージのセット
                    message.setMsgResourceKey(sch.getMessage());
                    return;
                }

            }
            // DFKLOOK end
            
            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_FaRetListComplatte.resetEditRow();
                _lcm_lst_FaRetListComplatte.resetHighlight();
                _lcm_lst_FaRetListComplatte.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_FaRetListComplatte.size(); i++)
            {
                ListCellLine line = _lcm_lst_FaRetListComplatte.get(i);
                lst_FaRetListComplatte.setCurrentRow(i);

                // DFKLOOK start
                // exclusion unmodified row.
                //if (!(line.isAppend() || line.isEdited()))
                //{
                //    continue;
                //}
                // DFKLOOK end

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(txt_ListWorkNo.getStringValue(), "");
                part11List.add(txt_BatchNo.getStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_AREA_NO), "");
                part11List.add(line.getViewString(KEY_LST_LOCATION_NO), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PICKING_QTY), "");

                if (lst_FaRetListComplatte.getChecked(_lcm_lst_FaRetListComplatte.getColumnIndex(KEY_LST_SHORTAGE_FLAG)))
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                }

                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_FaRetListComplatte.resetEditRow();
            _lcm_lst_FaRetListComplatte.resetHighlight();

            // clear.
            _lcm_lst_FaRetListComplatte.clear();
            btn_Complete.setEnabled(false);
            btn_ClearListInput.setEnabled(false);
            btn_AllClear.setEnabled(false);
            txt_ListWorkNo.setValue(null);
            txt_BatchNo.setValue(null);

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
    private void btn_ClearListInput_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_FaRetListComplatte.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_FaRetListComplatte.get(i);
            lst_FaRetListComplatte.setCurrentRow(i);
            clearLine.setValue(KEY_LST_PICKING_QTY, null);
            clearLine.setValue(KEY_LST_SHORTAGE_FLAG, Boolean.FALSE);
            lst_FaRetListComplatte_SetLineToolTip(clearLine);
            _lcm_lst_FaRetListComplatte.set(i, clearLine);
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
        _lcm_lst_FaRetListComplatte.clear();
        btn_Complete.setEnabled(false);
        btn_ClearListInput.setEnabled(false);
        btn_AllClear.setEnabled(false);
        txt_ListWorkNo.setValue(null);
        txt_BatchNo.setValue(null);

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
