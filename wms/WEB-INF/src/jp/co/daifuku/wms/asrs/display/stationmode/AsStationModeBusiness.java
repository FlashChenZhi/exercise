// $Id: AsStationModeBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.asrs.display.stationmode;

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
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
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
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.stationmode.AsStationMode;
import jp.co.daifuku.wms.asrs.schedule.AsStationModeSCH;
import jp.co.daifuku.wms.asrs.schedule.AsStationModeSCHParams;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * AS/RSステーションモード設定の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date:: 2011-07-06 09:52:24 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class AsStationModeBusiness
        extends AsStationMode
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
    
    /** lst_AsStationMode(HIDDEN_STATION_NAME) */
    private static final ListCellKey KEY_HIDDEN_STATION_NAME = new ListCellKey("HIDDEN_STATION_NAME", new StringCellColumn(), false, false);

    /** lst_AsStationMode(HIDDEN_STATION_TYPE) */
    private static final ListCellKey KEY_HIDDEN_STATION_TYPE = new ListCellKey("HIDDEN_STATION_TYPE", new StringCellColumn(), false, false);

    /** lst_AsStationMode(HIDDEN_MODE_TYPE) */
    private static final ListCellKey KEY_HIDDEN_MODE_TYPE = new ListCellKey("HIDDEN_MODE_TYPE", new StringCellColumn(), false, false);

    /** lst_AsStationMode(HIDDEN_BEFORE_CURRENT_MODE) */
    private static final ListCellKey KEY_HIDDEN_BEFORE_CURRENT_MODE = new ListCellKey("HIDDEN_BEFORE_CURRENT_MODE", new StringCellColumn(), false, false);

    /** lst_AsStationMode(HIDDEN_MACHINE_STATUS) */
    private static final ListCellKey KEY_HIDDEN_MACHINE_STATUS = new ListCellKey("HIDDEN_MACHINE_STATUS", new StringCellColumn(), false, false);

    /** lst_AsStationMode(HIDDEN_SUSPEND) */
    private static final ListCellKey KEY_HIDDEN_SUSPEND = new ListCellKey("HIDDEN_SUSPEND", new StringCellColumn(), false, false);

    /** lst_AsStationMode(LST_STATION_NO) */
    private static final ListCellKey KEY_LST_STATION_NO = new ListCellKey("LST_STATION_NO", new StringCellColumn(), true, false);

    /** lst_AsStationMode(LST_MODE_TYPE) */
    private static final ListCellKey KEY_LST_MODE_TYPE = new ListCellKey("LST_MODE_TYPE", new StringCellColumn(), true, false);

    /** lst_AsStationMode(LST_BEFORE_CURRENT_MODE) */
    private static final ListCellKey KEY_LST_BEFORE_CURRENT_MODE = new ListCellKey("LST_BEFORE_CURRENT_MODE", new StringCellColumn(), true, false);

    /** lst_AsStationMode(LST_AFTER_CURRENT_MODE) */
    private static final ListCellKey KEY_LST_AFTER_CURRENT_MODE = new ListCellKey("LST_AFTER_CURRENT_MODE", new StringCellColumn(), true, true);

    /** lst_AsStationMode(LST_MACHINE_STATUS) */
    private static final ListCellKey KEY_LST_MACHINE_STATUS = new ListCellKey("LST_MACHINE_STATUS", new StringCellColumn(), true, false);

    /** lst_AsStationMode(LST_SUSPEND) */
    private static final ListCellKey KEY_LST_SUSPEND = new ListCellKey("LST_SUSPEND", new StringCellColumn(), true, false);

    /** lst_AsStationMode(LST_WORK_COUNT) */
    private static final ListCellKey KEY_LST_WORK_COUNT = new ListCellKey("LST_WORK_COUNT", new StringCellColumn(), true, false);

    /** lst_AsStationMode(LST_SETTING_RESULT) */
    private static final ListCellKey KEY_LST_SETTING_RESULT = new ListCellKey("LST_SETTING_RESULT", new StringCellColumn(), true, false);

    /** lst_AsStationMode keys */
    private static final ListCellKey[] LST_ASSTATIONMODE_KEYS = {
        KEY_HIDDEN_STATION_NAME,
        KEY_HIDDEN_STATION_TYPE,
        KEY_HIDDEN_MODE_TYPE,
        KEY_HIDDEN_BEFORE_CURRENT_MODE,
        KEY_HIDDEN_MACHINE_STATUS,
        KEY_HIDDEN_SUSPEND,
        KEY_LST_STATION_NO,
        KEY_LST_MODE_TYPE,
        KEY_LST_BEFORE_CURRENT_MODE,
        KEY_LST_AFTER_CURRENT_MODE,
        KEY_LST_MACHINE_STATUS,
        KEY_LST_SUSPEND,
        KEY_LST_WORK_COUNT,
        KEY_LST_SETTING_RESULT,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    
    // DFKLOOK start
    // チェックボックス (ListCell)
    // 作業モード
    private static final int LST_AFTER_CURRENT_MODE = 4;
    // DFKLOOK end
    
    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_AsStationMode */
    private ListCellModel _lcm_lst_AsStationMode;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsStationModeBusiness()
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
    }

	/**
	 * @param e
	 *        ActionEvent
	 * @throws Exception
	 */
	public void page_ConfirmBack(ActionEvent e)
			throws Exception
	{
	    // DFKLOOK start
		// get event source.
		String eventSource = viewState.getString(_KEY_CONFIRMSOURCE);
		if(eventSource == null)
		{
			return;
		}

		// remove event source.
		viewState.remove(_KEY_CONFIRMSOURCE);

		// check result.
		boolean isExecute = new Boolean(String.valueOf(e.getEventArgs().get(0))).booleanValue();
		if(!isExecute)
		{
			return;
		}

		// choose process.
		if(eventSource.startsWith("btn_Set_Click"))
		{
			// process call.
			btn_Set_Click_Process(eventSource);
		}
		// DFKLOOK end
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
    public void btn_ReDisplayFunc_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ReDisplayFunc_Click_Process();
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
        Locale locale = httpRequest.getLocale();

        // initialize lst_AsStationMode.
        _lcm_lst_AsStationMode = new ListCellModel(lst_AsStationMode, LST_ASSTATIONMODE_KEYS, locale);
        _lcm_lst_AsStationMode.setToolTipVisible(KEY_LST_STATION_NO, true);
        _lcm_lst_AsStationMode.setToolTipVisible(KEY_LST_MODE_TYPE, true);
        _lcm_lst_AsStationMode.setToolTipVisible(KEY_LST_BEFORE_CURRENT_MODE, true);
        _lcm_lst_AsStationMode.setToolTipVisible(KEY_LST_AFTER_CURRENT_MODE, true);
        _lcm_lst_AsStationMode.setToolTipVisible(KEY_LST_MACHINE_STATUS, true);
        _lcm_lst_AsStationMode.setToolTipVisible(KEY_LST_SUSPEND, true);
        _lcm_lst_AsStationMode.setToolTipVisible(KEY_LST_WORK_COUNT, true);
        _lcm_lst_AsStationMode.setToolTipVisible(KEY_LST_SETTING_RESULT, true);

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
    private void lst_AsStationMode_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        // DFKLOOK start
        line.addToolTip("LBL-W0304", KEY_HIDDEN_STATION_NAME);
        line.addToolTip("LBL-W1433", KEY_HIDDEN_STATION_TYPE);
        // DFKLOOK end
        line.addToolTip("LBL-W1409", KEY_LST_SETTING_RESULT);
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
        AsStationModeSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsStationModeSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsStationModeSCHParams inparam = new AsStationModeSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_AsStationMode.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_AsStationMode.getNewLine();
                line.setValue(KEY_LST_STATION_NO, outparam.get(AsStationModeSCHParams.STATION_NO));
                line.setValue(KEY_LST_MODE_TYPE, outparam.get(AsStationModeSCHParams.MODE_TYPE));
                line.setValue(KEY_LST_BEFORE_CURRENT_MODE, outparam.get(AsStationModeSCHParams.BEFORE_CURRENT_MODE));
                line.setValue(KEY_LST_AFTER_CURRENT_MODE, outparam.get(AsStationModeSCHParams.AFTER_CURRENT_MODE));
                line.setValue(KEY_LST_MACHINE_STATUS, outparam.get(AsStationModeSCHParams.MACHINE_STATUS));
                line.setValue(KEY_LST_SUSPEND, outparam.get(AsStationModeSCHParams.SUSPEND));
                line.setValue(KEY_LST_WORK_COUNT, outparam.get(AsStationModeSCHParams.WORK_COUNT));
                line.setValue(KEY_LST_SETTING_RESULT, outparam.get(AsStationModeSCHParams.SETTING_RESULT));
                line.setValue(KEY_HIDDEN_STATION_NAME, outparam.get(AsStationModeSCHParams.HIDDEN_STATION_NAME));
                line.setValue(KEY_HIDDEN_STATION_TYPE, outparam.get(AsStationModeSCHParams.HIDDEN_STATION_TYPE));
                line.setValue(KEY_HIDDEN_MODE_TYPE, outparam.get(AsStationModeSCHParams.HIDDEN_MODE_TYPE));
                line.setValue(KEY_HIDDEN_BEFORE_CURRENT_MODE, outparam.get(AsStationModeSCHParams.HIDDEN_BEFORE_CURRENT_MODE));
                line.setValue(KEY_HIDDEN_MACHINE_STATUS, outparam.get(AsStationModeSCHParams.HIDDEN_MACHINE_STATUS));
                line.setValue(KEY_HIDDEN_SUSPEND, outparam.get(AsStationModeSCHParams.HIDDEN_SUSPEND));
                lst_AsStationMode_SetLineToolTip(line);
                _lcm_lst_AsStationMode.add(line);
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
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_AsStationMode.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_AsStationMode.get(i);
            if (!(checkline.isAppend() || checkline.isEdited()))
            {
                continue;
            }
            if (Station.MODE_REQUEST_NONE.equals((checkline.getStringValue(KEY_LST_AFTER_CURRENT_MODE))))
            {
                continue;
            }
            existEditedRow = true;
        }
        if (!existEditedRow)
        {
            // DFKLOOK start
            // 作業モードが選択されていません。
            message.setMsgResourceKey("6023445");
            // DFKLOOK end
            return;
        }

        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
        	// 設定しますか？
            this.setConfirm("MSG-W9000", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsStationModeSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsStationModeSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_AsStationMode.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_AsStationMode.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                AsStationModeSCHParams lineparam = new AsStationModeSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(AsStationModeSCHParams.HIDDEN_STATION_TYPE, line.getValue(KEY_HIDDEN_STATION_TYPE));
                lineparam.set(AsStationModeSCHParams.HIDDEN_STATION_NAME, line.getValue(KEY_HIDDEN_STATION_NAME));
                lineparam.set(AsStationModeSCHParams.HIDDEN_MODE_TYPE, line.getValue(KEY_HIDDEN_MODE_TYPE));
                lineparam.set(AsStationModeSCHParams.HIDDEN_BEFORE_CURRENT_MODE, line.getValue(KEY_HIDDEN_BEFORE_CURRENT_MODE));
                lineparam.set(AsStationModeSCHParams.STATION_NO, line.getValue(KEY_LST_STATION_NO));
                lineparam.set(AsStationModeSCHParams.MODE_TYPE, line.getValue(KEY_LST_MODE_TYPE));
                lineparam.set(AsStationModeSCHParams.BEFORE_CURRENT_MODE, line.getValue(KEY_LST_BEFORE_CURRENT_MODE));
                lineparam.set(AsStationModeSCHParams.AFTER_CURRENT_MODE, line.getValue(KEY_LST_AFTER_CURRENT_MODE));
                lineparam.set(AsStationModeSCHParams.MACHINE_STATUS, line.getValue(KEY_LST_MACHINE_STATUS));
                lineparam.set(AsStationModeSCHParams.SUSPEND, line.getValue(KEY_LST_SUSPEND));
                lineparam.set(AsStationModeSCHParams.WORK_COUNT, line.getValue(KEY_LST_WORK_COUNT));
                lineparam.set(AsStationModeSCHParams.SETTING_RESULT, line.getValue(KEY_LST_SETTING_RESULT));
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);
            // DFKLOOK start
            // プルダウンのチェックを行います。
            // プルダウンが何も切替えられていない時は、「作業モードが選択されていません。」
            // のメッセージを表示します。
            if(!sch.check(inparams))
            {
                message.setMsgResourceKey("6023445");
                return;
            }
            // DFKLOOK end
            // SCH call.
            List<Params> outparams = sch.startSCHgetParams(inparams);

            if (outparams == null)
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_AsStationMode.resetEditRow();
                _lcm_lst_AsStationMode.resetHighlight();
                _lcm_lst_AsStationMode.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_AsStationMode.size(); i++)
            {
                ListCellLine line = _lcm_lst_AsStationMode.get(i);
                lst_AsStationMode.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }
                if (Station.MODE_REQUEST_NONE.equals((line.getStringValue(KEY_LST_AFTER_CURRENT_MODE))))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_STATION_NO), "");
                part11List.add(line.getViewString(KEY_HIDDEN_MODE_TYPE), "");
                part11List.add(line.getViewString(KEY_HIDDEN_BEFORE_CURRENT_MODE), "");
                part11List.add(line.getViewString(KEY_LST_AFTER_CURRENT_MODE), "");
                part11List.add(line.getViewString(KEY_HIDDEN_MACHINE_STATUS), "");
                part11List.add(line.getViewString(KEY_HIDDEN_SUSPEND), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_AsStationMode.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_AsStationMode.getNewLine();
                line.setValue(KEY_HIDDEN_STATION_NAME, outparam.get(AsStationModeSCHParams.HIDDEN_STATION_NAME));
                line.setValue(KEY_HIDDEN_STATION_TYPE, outparam.get(AsStationModeSCHParams.HIDDEN_STATION_TYPE));
                line.setValue(KEY_HIDDEN_MODE_TYPE, outparam.get(AsStationModeSCHParams.HIDDEN_MODE_TYPE));
                line.setValue(KEY_HIDDEN_BEFORE_CURRENT_MODE, outparam.get(AsStationModeSCHParams.HIDDEN_BEFORE_CURRENT_MODE));
                line.setValue(KEY_LST_AFTER_CURRENT_MODE, outparam.get(AsStationModeSCHParams.AFTER_CURRENT_MODE));
                line.setValue(KEY_LST_STATION_NO, outparam.get(AsStationModeSCHParams.STATION_NO));
                line.setValue(KEY_LST_MODE_TYPE, outparam.get(AsStationModeSCHParams.MODE_TYPE));
                line.setValue(KEY_LST_BEFORE_CURRENT_MODE, outparam.get(AsStationModeSCHParams.BEFORE_CURRENT_MODE));
                line.setValue(KEY_LST_MACHINE_STATUS, outparam.get(AsStationModeSCHParams.MACHINE_STATUS));
                line.setValue(KEY_LST_SUSPEND, outparam.get(AsStationModeSCHParams.SUSPEND));
                line.setValue(KEY_LST_WORK_COUNT, outparam.get(AsStationModeSCHParams.WORK_COUNT));
                line.setValue(KEY_LST_SETTING_RESULT, outparam.get(AsStationModeSCHParams.SETTING_RESULT));
                lst_AsStationMode_SetLineToolTip(line);
                _lcm_lst_AsStationMode.add(line);
            }

            // reset editing row.
            _lcm_lst_AsStationMode.resetEditRow();
            _lcm_lst_AsStationMode.resetHighlight();

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
    private void btn_ReDisplayFunc_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsStationModeSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsStationModeSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsStationModeSCHParams inparam = new AsStationModeSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_AsStationMode.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_AsStationMode.getNewLine();
                line.setValue(KEY_HIDDEN_STATION_NAME, outparam.get(AsStationModeSCHParams.HIDDEN_STATION_NAME));
                line.setValue(KEY_HIDDEN_STATION_TYPE, outparam.get(AsStationModeSCHParams.HIDDEN_STATION_TYPE));
                line.setValue(KEY_HIDDEN_MODE_TYPE, outparam.get(AsStationModeSCHParams.HIDDEN_MODE_TYPE));
                line.setValue(KEY_HIDDEN_BEFORE_CURRENT_MODE, outparam.get(AsStationModeSCHParams.HIDDEN_BEFORE_CURRENT_MODE));
                line.setValue(KEY_LST_STATION_NO, outparam.get(AsStationModeSCHParams.STATION_NO));
                line.setValue(KEY_LST_MODE_TYPE, outparam.get(AsStationModeSCHParams.MODE_TYPE));
                line.setValue(KEY_LST_BEFORE_CURRENT_MODE, outparam.get(AsStationModeSCHParams.BEFORE_CURRENT_MODE));
                line.setValue(KEY_LST_AFTER_CURRENT_MODE, outparam.get(AsStationModeSCHParams.AFTER_CURRENT_MODE));
                line.setValue(KEY_LST_MACHINE_STATUS, outparam.get(AsStationModeSCHParams.MACHINE_STATUS));
                line.setValue(KEY_LST_SUSPEND, outparam.get(AsStationModeSCHParams.SUSPEND));
                line.setValue(KEY_LST_WORK_COUNT, outparam.get(AsStationModeSCHParams.WORK_COUNT));
                line.setValue(KEY_LST_SETTING_RESULT, outparam.get(AsStationModeSCHParams.SETTING_RESULT));
                line.setValue(KEY_HIDDEN_MACHINE_STATUS, outparam.get(AsStationModeSCHParams.HIDDEN_MACHINE_STATUS));
                line.setValue(KEY_HIDDEN_SUSPEND, outparam.get(AsStationModeSCHParams.HIDDEN_SUSPEND));
                lst_AsStationMode_SetLineToolTip(line);
                _lcm_lst_AsStationMode.add(line);
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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_AsStationMode.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_AsStationMode.get(i);
            lst_AsStationMode.setCurrentRow(i);
            clearLine.setValue(KEY_LST_AFTER_CURRENT_MODE, null);
            lst_AsStationMode_SetLineToolTip(clearLine);
            _lcm_lst_AsStationMode.set(i, clearLine);
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
