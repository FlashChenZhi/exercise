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
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
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
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.retrieval.display.faliststart.FaRetrievalListStart;
import jp.co.daifuku.wms.retrieval.schedule.FaRetrievalListStartSCH;
import jp.co.daifuku.wms.retrieval.schedule.FaRetrievalListStartSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
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

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_FaRetListstart */
    private ScrollListCellModel _lcm_lst_FaRetListstart;

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
        // process call.
        btn_Start_Click_Process();
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
        _lcm_lst_FaRetListstart = new ScrollListCellModel(lst_FaRetListstart, LST_FARETLISTSTART_KEYS, locale);
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

            // clear.
            btn_Start.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_AllClear.setEnabled(true);
            chk_WorkListPrint.setEnabled(true);
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
    private void btn_Start_Click_Process()
            throws Exception
    {
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
