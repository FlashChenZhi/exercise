// $Id: AsChangeStationPopupBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.asrs.display.changestationpopup;

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
import jp.co.daifuku.wms.asrs.schedule.AsChangeStationPopupSCH;
import jp.co.daifuku.wms.asrs.schedule.AsChangeStationPopupSCHParams;
import jp.co.daifuku.wms.base.common.DsNumberDefine;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * AS/RS ステーション状態変更設定の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class AsChangeStationPopupBusiness
        extends AsChangeStationPopup
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ChangeStationStatus(HDN_MODE_TYPE) */
    private static final ListCellKey KEY_HDN_MODE_TYPE = new ListCellKey("HDN_MODE_TYPE", new StringCellColumn(), false, false);

    /** lst_ChangeStationStatus(HDN_CURRENT_MODE) */
    private static final ListCellKey KEY_HDN_CURRENT_MODE = new ListCellKey("HDN_CURRENT_MODE", new StringCellColumn(), false, false);

    /** lst_ChangeStationStatus(HDN_STATUS) */
    private static final ListCellKey KEY_HDN_STATUS = new ListCellKey("HDN_STATUS", new StringCellColumn(), false, false);

    // DFKLOOK start
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK end
    
    /** lst_ChangeStationStatus(HDN_SUSPEND) */
    private static final ListCellKey KEY_HDN_SUSPEND = new ListCellKey("HDN_SUSPEND", new StringCellColumn(), false, false);

    /** lst_ChangeStationStatus(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_ChangeStationStatus(LST_STATION_NO) */
    private static final ListCellKey KEY_LST_STATION_NO = new ListCellKey("LST_STATION_NO", new StringCellColumn(), true, false);

    /** lst_ChangeStationStatus(LST_STATION_NAME) */
    private static final ListCellKey KEY_LST_STATION_NAME = new ListCellKey("LST_STATION_NAME", new StringCellColumn(), true, false);

    /** lst_ChangeStationStatus(LST_MODE_TYPE) */
    private static final ListCellKey KEY_LST_MODE_TYPE = new ListCellKey("LST_MODE_TYPE", new StringCellColumn(), true, false);

    /** lst_ChangeStationStatus(LST_CURRENT_MODE) */
    private static final ListCellKey KEY_LST_CURRENT_MODE = new ListCellKey("LST_CURRENT_MODE", new StringCellColumn(), true, false);

    /** lst_ChangeStationStatus(LST_STATUS) */
    private static final ListCellKey KEY_LST_STATUS = new ListCellKey("LST_STATUS", new StringCellColumn(), true, false);

    /** lst_ChangeStationStatus(LST_SUSPEND) */
    private static final ListCellKey KEY_LST_SUSPEND = new ListCellKey("LST_SUSPEND", new StringCellColumn(), true, false);

    /** lst_ChangeStationStatus(LST_WORK_COUNT) */
    private static final ListCellKey KEY_LST_WORK_COUNT = new ListCellKey("LST_WORK_COUNT", new NumberCellColumn(0), true, false);

    /** lst_ChangeStationStatus keys */
    private static final ListCellKey[] LST_CHANGESTATIONSTATUS_KEYS = {
        KEY_HDN_MODE_TYPE,
        KEY_HDN_CURRENT_MODE,
        KEY_HDN_STATUS,
        KEY_HDN_SUSPEND,
        KEY_LST_SELECT,
        KEY_LST_STATION_NO,
        KEY_LST_STATION_NAME,
        KEY_LST_MODE_TYPE,
        KEY_LST_CURRENT_MODE,
        KEY_LST_STATUS,
        KEY_LST_SUSPEND,
        KEY_LST_WORK_COUNT,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_ChangeStationStatus */
    private ListCellModel _lcm_lst_ChangeStationStatus;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsChangeStationPopupBusiness()
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
	public void page_ConfirmBack(ActionEvent e) throws Exception
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
		if(eventSource.startsWith("btn_Operation_Click"))
		{
			// process call.
			btn_Operation_Click_Process(eventSource);
		}
		if(eventSource.startsWith("btn_Suspension_Click"))
		{
			// process call.
			btn_Suspension_Click_Process(eventSource);
		}
		// DFKLOOK end
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
    public void btn_Operation_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Operation_Click_Process(null);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Suspension_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Suspension_Click_Process(null);
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
    public void btn_Close_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_Click_Process();
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

        // initialize lst_ChangeStationStatus.
        _lcm_lst_ChangeStationStatus = new ListCellModel(lst_ChangeStationStatus, LST_CHANGESTATIONSTATUS_KEYS, locale);
        _lcm_lst_ChangeStationStatus.setToolTipVisible(KEY_LST_SELECT, true);
        _lcm_lst_ChangeStationStatus.setToolTipVisible(KEY_LST_STATION_NO, true);
        _lcm_lst_ChangeStationStatus.setToolTipVisible(KEY_LST_STATION_NAME, true);
        _lcm_lst_ChangeStationStatus.setToolTipVisible(KEY_LST_MODE_TYPE, true);
        _lcm_lst_ChangeStationStatus.setToolTipVisible(KEY_LST_CURRENT_MODE, true);
        _lcm_lst_ChangeStationStatus.setToolTipVisible(KEY_LST_STATUS, true);
        _lcm_lst_ChangeStationStatus.setToolTipVisible(KEY_LST_SUSPEND, true);
        _lcm_lst_ChangeStationStatus.setToolTipVisible(KEY_LST_WORK_COUNT, true);

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
    private void lst_ChangeStationStatus_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0023", KEY_LST_STATION_NAME);
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
        AsChangeStationPopupSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsChangeStationPopupSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsChangeStationPopupSCHParams inparam = new AsChangeStationPopupSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_ChangeStationStatus.clear();
            // DFKLOOK 件数チェックを追加
            if (outparams.size() <= 0)
            {
                buttonEnabled(false);
                return;
            }
            else
            {
                buttonEnabled(true);
            }
            // DFKLOOK ここまで
            
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ChangeStationStatus.getNewLine();
                line.setValue(KEY_LST_STATION_NO, outparam.get(AsChangeStationPopupSCHParams.STATION_NO));
                line.setValue(KEY_LST_STATION_NAME, outparam.get(AsChangeStationPopupSCHParams.STATION_NAME));
                line.setValue(KEY_LST_MODE_TYPE, outparam.get(AsChangeStationPopupSCHParams.MODE_TYPE_NAME));
                line.setValue(KEY_LST_CURRENT_MODE, outparam.get(AsChangeStationPopupSCHParams.CURRENT_MODE_NAME));
                line.setValue(KEY_LST_STATUS, outparam.get(AsChangeStationPopupSCHParams.STATUS_NAME));
                line.setValue(KEY_LST_SUSPEND, outparam.get(AsChangeStationPopupSCHParams.SUSPEND_NAME));
                line.setValue(KEY_LST_WORK_COUNT, outparam.get(AsChangeStationPopupSCHParams.WORK_COUNT));
                line.setValue(KEY_HDN_MODE_TYPE, outparam.get(AsChangeStationPopupSCHParams.MODE_TYPE));
                line.setValue(KEY_HDN_CURRENT_MODE, outparam.get(AsChangeStationPopupSCHParams.CURRENT_MODE));
                line.setValue(KEY_HDN_STATUS, outparam.get(AsChangeStationPopupSCHParams.STATUS));
                line.setValue(KEY_HDN_SUSPEND, outparam.get(AsChangeStationPopupSCHParams.SUSPEND));
                lst_ChangeStationStatus_SetLineToolTip(line);
                _lcm_lst_ChangeStationStatus.add(line);
            }

            // clear.
            for (int i = 1; i <= _lcm_lst_ChangeStationStatus.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_ChangeStationStatus.get(i);
                lst_ChangeStationStatus.setCurrentRow(i);
                clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
                lst_ChangeStationStatus_SetLineToolTip(clearLine);
                _lcm_lst_ChangeStationStatus.set(i, clearLine);
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
    private void btn_AllCheck_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_ChangeStationStatus.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_ChangeStationStatus.get(i);
            lst_ChangeStationStatus.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_ChangeStationStatus_SetLineToolTip(clearLine);
            _lcm_lst_ChangeStationStatus.set(i, clearLine);
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
        for (int i = 1; i <= _lcm_lst_ChangeStationStatus.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_ChangeStationStatus.get(i);
            lst_ChangeStationStatus.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_ChangeStationStatus_SetLineToolTip(clearLine);
            _lcm_lst_ChangeStationStatus.set(i, clearLine);
        }

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Operation_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_ChangeStationStatus.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_ChangeStationStatus.get(i);
            // DFKLOOK 条件に作業状態が中断中のみを追加
            if (checkline.isAppend() || checkline.isEdited()
                    && SystemDefine.SUSPEND_ON.equals(checkline.getValue(KEY_HDN_SUSPEND)))
            // DFKLOOK ここまで
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            // DFKLOOK メッセージセット変更
            // 更新対象データがありません。
            message.setMsgResourceKey("6023059");
            // DFKLOOK ここまで
            return;
        }

        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
        	// 設定しますか？
            this.setConfirm("MSG-W9000", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_Operation_Click");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsChangeStationPopupSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsChangeStationPopupSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ChangeStationStatus.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ChangeStationStatus.get(i);
                // DFKLOOK 条件に作業状態が中断中のみを追加
                if (!(line.isAppend() || line.isEdited()
                        && SystemDefine.SUSPEND_ON.equals(line.getValue(KEY_HDN_SUSPEND))))
                // DFKLOOK ここまで
                {
                    continue;
                }

                AsChangeStationPopupSCHParams lineparam = new AsChangeStationPopupSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(AsChangeStationPopupSCHParams.SUSPEND, line.getValue(KEY_HDN_SUSPEND));
                lineparam.set(AsChangeStationPopupSCHParams.STATION_NO, line.getValue(KEY_LST_STATION_NO));
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
                _lcm_lst_ChangeStationStatus.resetEditRow();
                _lcm_lst_ChangeStationStatus.resetHighlight();
                _lcm_lst_ChangeStationStatus.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_ChangeStationStatus.size(); i++)
            {
                ListCellLine line = _lcm_lst_ChangeStationStatus.get(i);
                lst_ChangeStationStatus.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                // 設定区分 0:運転
                part11List.add(SystemDefine.SUSPEND_OFF, "");
                part11List.add(line.getViewString(KEY_LST_STATION_NO), "");
                part11List.add(line.getViewString(KEY_HDN_MODE_TYPE), "");
                part11List.add(line.getViewString(KEY_HDN_CURRENT_MODE), "");
                part11List.add(line.getViewString(KEY_HDN_STATUS), "");
                part11List.add(line.getViewString(KEY_HDN_SUSPEND), "");
                // DFKLOOK start
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING),
                		DsNumberDefine.DS_STN_CHANGE, DsNumberDefine.PAGERESOUCE_STN_CHANGE, part11List);
                // DFKLOOK end
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_ChangeStationStatus.resetEditRow();
            _lcm_lst_ChangeStationStatus.resetHighlight();

            // set input parameters.
            AsChangeStationPopupSCHParams inparam = new AsChangeStationPopupSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_ChangeStationStatus.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ChangeStationStatus.getNewLine();
                line.setValue(KEY_LST_STATION_NO, outparam.get(AsChangeStationPopupSCHParams.STATION_NO));
                line.setValue(KEY_LST_STATION_NAME, outparam.get(AsChangeStationPopupSCHParams.STATION_NAME));
                line.setValue(KEY_LST_MODE_TYPE, outparam.get(AsChangeStationPopupSCHParams.MODE_TYPE_NAME));
                line.setValue(KEY_LST_CURRENT_MODE, outparam.get(AsChangeStationPopupSCHParams.CURRENT_MODE_NAME));
                line.setValue(KEY_LST_STATUS, outparam.get(AsChangeStationPopupSCHParams.STATUS_NAME));
                line.setValue(KEY_LST_SUSPEND, outparam.get(AsChangeStationPopupSCHParams.SUSPEND_NAME));
                line.setValue(KEY_LST_WORK_COUNT, outparam.get(AsChangeStationPopupSCHParams.WORK_COUNT));
                line.setValue(KEY_HDN_MODE_TYPE, outparam.get(AsChangeStationPopupSCHParams.MODE_TYPE));
                line.setValue(KEY_HDN_CURRENT_MODE, outparam.get(AsChangeStationPopupSCHParams.CURRENT_MODE));
                line.setValue(KEY_HDN_STATUS, outparam.get(AsChangeStationPopupSCHParams.STATUS));
                line.setValue(KEY_HDN_SUSPEND, outparam.get(AsChangeStationPopupSCHParams.SUSPEND));
                lst_ChangeStationStatus_SetLineToolTip(line);
                _lcm_lst_ChangeStationStatus.add(line);
            }

            // clear.
            for (int i = 1; i <= _lcm_lst_ChangeStationStatus.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_ChangeStationStatus.get(i);
                lst_ChangeStationStatus.setCurrentRow(i);
                clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
                lst_ChangeStationStatus_SetLineToolTip(clearLine);
                _lcm_lst_ChangeStationStatus.set(i, clearLine);
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
    private void btn_Suspension_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_ChangeStationStatus.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_ChangeStationStatus.get(i);
            // DFKLOOK 条件に作業状態が使用可能のみを追加
            if (checkline.isAppend() || checkline.isEdited()
                    && SystemDefine.SUSPEND_OFF.equals(checkline.getValue(KEY_HDN_SUSPEND)))
            // DFKLOOK ここまで
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            // DFKLOOK メッセージセット変更
            // 更新対象データがありません。
            message.setMsgResourceKey("6023059");
            // DFKLOOK ここまで
            return;
        }

        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
        	// 設定しますか？
            this.setConfirm("MSG-W9000", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_Suspension_Click");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsChangeStationPopupSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsChangeStationPopupSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ChangeStationStatus.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ChangeStationStatus.get(i);
                // DFKLOOK 条件に作業状態が使用可能のみを追加
                if (!(line.isAppend() || line.isEdited()
                        && SystemDefine.SUSPEND_OFF.equals(line.getValue(KEY_HDN_SUSPEND))))
                // DFKLOOK ここまで
                {
                    continue;
                }

                AsChangeStationPopupSCHParams lineparam = new AsChangeStationPopupSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(AsChangeStationPopupSCHParams.SUSPEND, line.getValue(KEY_HDN_SUSPEND));
                lineparam.set(AsChangeStationPopupSCHParams.STATION_NO, line.getValue(KEY_LST_STATION_NO));
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
                _lcm_lst_ChangeStationStatus.resetEditRow();
                _lcm_lst_ChangeStationStatus.resetHighlight();
                _lcm_lst_ChangeStationStatus.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_ChangeStationStatus.size(); i++)
            {
                ListCellLine line = _lcm_lst_ChangeStationStatus.get(i);
                lst_ChangeStationStatus.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                // 設定区分 1:中断
                part11List.add(SystemDefine.SUSPEND_ON, "");
                part11List.add(line.getViewString(KEY_LST_STATION_NO), "");
                part11List.add(line.getViewString(KEY_HDN_MODE_TYPE), "");
                part11List.add(line.getViewString(KEY_HDN_CURRENT_MODE), "");
                part11List.add(line.getViewString(KEY_HDN_STATUS), "");
                part11List.add(line.getViewString(KEY_HDN_SUSPEND), "");
                // DFKLOOK start
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING),
						DsNumberDefine.DS_STN_CHANGE, DsNumberDefine.PAGERESOUCE_STN_CHANGE, part11List);
                // DFKLOOK end
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_ChangeStationStatus.resetEditRow();
            _lcm_lst_ChangeStationStatus.resetHighlight();

            // set input parameters.
            AsChangeStationPopupSCHParams inparam = new AsChangeStationPopupSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_ChangeStationStatus.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ChangeStationStatus.getNewLine();
                line.setValue(KEY_LST_STATION_NO, outparam.get(AsChangeStationPopupSCHParams.STATION_NO));
                line.setValue(KEY_LST_STATION_NAME, outparam.get(AsChangeStationPopupSCHParams.STATION_NAME));
                line.setValue(KEY_LST_MODE_TYPE, outparam.get(AsChangeStationPopupSCHParams.MODE_TYPE_NAME));
                line.setValue(KEY_LST_CURRENT_MODE, outparam.get(AsChangeStationPopupSCHParams.CURRENT_MODE_NAME));
                line.setValue(KEY_LST_STATUS, outparam.get(AsChangeStationPopupSCHParams.STATUS_NAME));
                line.setValue(KEY_LST_SUSPEND, outparam.get(AsChangeStationPopupSCHParams.SUSPEND_NAME));
                line.setValue(KEY_LST_WORK_COUNT, outparam.get(AsChangeStationPopupSCHParams.WORK_COUNT));
                line.setValue(KEY_HDN_MODE_TYPE, outparam.get(AsChangeStationPopupSCHParams.MODE_TYPE));
                line.setValue(KEY_HDN_CURRENT_MODE, outparam.get(AsChangeStationPopupSCHParams.CURRENT_MODE));
                line.setValue(KEY_HDN_STATUS, outparam.get(AsChangeStationPopupSCHParams.STATUS));
                line.setValue(KEY_HDN_SUSPEND, outparam.get(AsChangeStationPopupSCHParams.SUSPEND));
                lst_ChangeStationStatus_SetLineToolTip(line);
                _lcm_lst_ChangeStationStatus.add(line);
            }

            // clear.
            for (int i = 1; i <= _lcm_lst_ChangeStationStatus.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_ChangeStationStatus.get(i);
                lst_ChangeStationStatus.setCurrentRow(i);
                clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
                lst_ChangeStationStatus_SetLineToolTip(clearLine);
                _lcm_lst_ChangeStationStatus.set(i, clearLine);
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
    private void btn_ReDisplay_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsChangeStationPopupSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsChangeStationPopupSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsChangeStationPopupSCHParams inparam = new AsChangeStationPopupSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_ChangeStationStatus.clear();
            // DFKLOOK 件数チェックを追加
            if (outparams.size() <= 0)
            {
                buttonEnabled(false);
                return;
            }
            else
            {
                buttonEnabled(true);
            }
            // DFKLOOK ここまで
            
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ChangeStationStatus.getNewLine();
                line.setValue(KEY_LST_STATION_NO, outparam.get(AsChangeStationPopupSCHParams.STATION_NO));
                line.setValue(KEY_LST_STATION_NAME, outparam.get(AsChangeStationPopupSCHParams.STATION_NAME));
                line.setValue(KEY_LST_MODE_TYPE, outparam.get(AsChangeStationPopupSCHParams.MODE_TYPE_NAME));
                line.setValue(KEY_LST_CURRENT_MODE, outparam.get(AsChangeStationPopupSCHParams.CURRENT_MODE_NAME));
                line.setValue(KEY_LST_STATUS, outparam.get(AsChangeStationPopupSCHParams.STATUS_NAME));
                line.setValue(KEY_LST_SUSPEND, outparam.get(AsChangeStationPopupSCHParams.SUSPEND_NAME));
                line.setValue(KEY_LST_WORK_COUNT, outparam.get(AsChangeStationPopupSCHParams.WORK_COUNT));
                line.setValue(KEY_HDN_MODE_TYPE, outparam.get(AsChangeStationPopupSCHParams.MODE_TYPE));
                line.setValue(KEY_HDN_CURRENT_MODE, outparam.get(AsChangeStationPopupSCHParams.CURRENT_MODE));
                line.setValue(KEY_HDN_STATUS, outparam.get(AsChangeStationPopupSCHParams.STATUS));
                line.setValue(KEY_HDN_SUSPEND, outparam.get(AsChangeStationPopupSCHParams.SUSPEND));
                lst_ChangeStationStatus_SetLineToolTip(line);
                _lcm_lst_ChangeStationStatus.add(line);
            }

            // clear.
            for (int i = 1; i <= _lcm_lst_ChangeStationStatus.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_ChangeStationStatus.get(i);
                lst_ChangeStationStatus.setCurrentRow(i);
                clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
                lst_ChangeStationStatus_SetLineToolTip(clearLine);
                _lcm_lst_ChangeStationStatus.set(i, clearLine);
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
    private void btn_Close_Click_Process()
            throws Exception
    {
        parentRedirect(null);
        dispose();

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
    
    //DFKLOOK:ここから修正
    /**
     * ボタンの使用可/使用不可を設定します。
     *
     * @param arg 
     */
    private void buttonEnabled(boolean arg)
    {
        btn_AllCheck.setEnabled(arg);
        btn_AllCheckClear.setEnabled(arg);
        btn_Operation.setEnabled(arg);
        btn_Suspension.setEnabled(arg);
    }
    //DFKLOOK:ここまで修正

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
