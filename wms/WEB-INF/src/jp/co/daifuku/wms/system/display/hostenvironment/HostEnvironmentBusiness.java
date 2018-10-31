// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.system.display.hostenvironment;

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
import jp.co.daifuku.common.text.DisplayText;
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
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.system.schedule.HostEnvironmentSCH;
import jp.co.daifuku.wms.system.schedule.HostEnvironmentSCHParams;

/**
 *
 * ホスト環境設定のビジネスクラスです。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class HostEnvironmentBusiness
        extends HostEnvironment
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_HostEnvironment(HDN_DATA_NAME) */
    private static final ListCellKey KEY_HDN_DATA_NAME = new ListCellKey("HDN_DATA_NAME", new StringCellColumn(), false, false);

    // DFKLOOK start
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK end
    
    /** lst_HostEnvironment(HDN_DATA_TYPE) */
    private static final ListCellKey KEY_HDN_DATA_TYPE = new ListCellKey("HDN_DATA_TYPE", new StringCellColumn(), false, false);

    /** lst_HostEnvironment(LST_DATA_NAME) */
    private static final ListCellKey KEY_LST_DATA_NAME = new ListCellKey("LST_DATA_NAME", new StringCellColumn(), true, false);

    /** lst_HostEnvironment(LST_DATA_TYPE) */
    private static final ListCellKey KEY_LST_DATA_TYPE = new ListCellKey("LST_DATA_TYPE", new StringCellColumn(), true, false);

    /** lst_HostEnvironment(LST_DESTINATION_FOLDER) */
    private static final ListCellKey KEY_LST_DESTINATION_FOLDER = new ListCellKey("LST_DESTINATION_FOLDER", new StringCellColumn(), true, true);

    /** lst_HostEnvironment(LST_PREFIX_NAME) */
    private static final ListCellKey KEY_LST_PREFIX_NAME = new ListCellKey("LST_PREFIX_NAME", new StringCellColumn(), true, false);

    /** lst_HostEnvironment keys */
    private static final ListCellKey[] LST_HOSTENVIRONMENT_KEYS = {
        KEY_HDN_DATA_NAME,
        KEY_HDN_DATA_TYPE,
        KEY_LST_DATA_NAME,
        KEY_LST_DATA_TYPE,
        KEY_LST_DESTINATION_FOLDER,
        KEY_LST_PREFIX_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_HostEnvironment */
    private ListCellModel _lcm_lst_HostEnvironment;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public HostEnvironmentBusiness()
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

    // DFKLOOK start
	/**
	 * @param e
	 *        ActionEvent
	 * @throws Exception
	 */
	public void page_ConfirmBack(ActionEvent e) throws Exception
	{
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
		if(eventSource.startsWith("btn_Set2_Click"))
		{
			// process call.
			btn_Set2_Click_Process(eventSource);
		}
	}
	// DFKLOOK end

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Set2_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Set2_Click_Process(null);
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
    public void tab_Click(ActionEvent e)
            throws Exception
    {
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

        // initialize lst_HostEnvironment.
        _lcm_lst_HostEnvironment = new ListCellModel(lst_HostEnvironment, LST_HOSTENVIRONMENT_KEYS, locale);
        _lcm_lst_HostEnvironment.setToolTipVisible(KEY_LST_DATA_NAME, false);
        _lcm_lst_HostEnvironment.setToolTipVisible(KEY_LST_DATA_TYPE, false);
        _lcm_lst_HostEnvironment.setToolTipVisible(KEY_LST_DESTINATION_FOLDER, false);
        _lcm_lst_HostEnvironment.setToolTipVisible(KEY_LST_PREFIX_NAME, false);

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
    private void lst_HostEnvironment_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
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
        HostEnvironmentSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new HostEnvironmentSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            HostEnvironmentSCHParams inparam = new HostEnvironmentSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_HostEnvironment.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_HostEnvironment.getNewLine();
                line.setValue(KEY_HDN_DATA_NAME, outparam.get(HostEnvironmentSCHParams.HDN_DATA_NAME));
                line.setValue(KEY_HDN_DATA_TYPE, outparam.get(HostEnvironmentSCHParams.HDN_TYPE));
                line.setValue(KEY_LST_DATA_NAME, outparam.get(HostEnvironmentSCHParams.DATA_NAME));
                line.setValue(KEY_LST_DATA_TYPE, outparam.get(HostEnvironmentSCHParams.DATA_TYPE));
                line.setValue(KEY_LST_DESTINATION_FOLDER, outparam.get(HostEnvironmentSCHParams.DESTINATION_FOLDER));
                line.setValue(KEY_LST_PREFIX_NAME, outparam.get(HostEnvironmentSCHParams.PREFIX_NAME));
                lst_HostEnvironment_SetLineToolTip(line);
                _lcm_lst_HostEnvironment.add(line);
            }

            // set focus.
            setFocus(lst_HostEnvironment);

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
    private void btn_Set2_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        //DFKLOOK:ここから修正
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_HostEnvironment.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_HostEnvironment.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            // DFKLOOK start メッセージ変更
            message.setMsgResourceKey("6023059");
            // DFKLOOK end
            //message.setMsgResourceKey("6003001");
            return;
        }
        //DFKLOOK:ここまで修正
        
        // DFKLOOK start
    	if (StringUtil.isBlank(eventSource))
    	{
         	// 設定しますか?
            this.setConfirm("MSG-W9000", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set2_Click");
            return;
        }
    	// DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        HostEnvironmentSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new HostEnvironmentSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_HostEnvironment.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_HostEnvironment.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                HostEnvironmentSCHParams lineparam = new HostEnvironmentSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(HostEnvironmentSCHParams.HDN_TYPE, line.getValue(KEY_HDN_DATA_TYPE));
                lineparam.set(HostEnvironmentSCHParams.DESTINATION_FOLDER, line.getValue(KEY_LST_DESTINATION_FOLDER));
                lineparam.set(HostEnvironmentSCHParams.PREFIX_NAME, line.getValue(KEY_LST_PREFIX_NAME));
                lineparam.set(HostEnvironmentSCHParams.DATA_TYPE, line.getValue(KEY_LST_DATA_TYPE));
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
                _lcm_lst_HostEnvironment.resetEditRow();
                _lcm_lst_HostEnvironment.resetHighlight();
                _lcm_lst_HostEnvironment.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_HostEnvironment.size(); i++)
            {
                ListCellLine line = _lcm_lst_HostEnvironment.get(i);
                lst_HostEnvironment.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_HDN_DATA_TYPE), "");
                // DFKLOOK start
                part11List.add("1", "", line.getViewString(KEY_LST_DATA_TYPE).equals(
						DisplayText.getText("RDB-W0064")));
				part11List.add("0", "", line.getViewString(KEY_LST_DATA_TYPE).equals(
						DisplayText.getText("RDB-W0065")));
                // DFKLOOK end
                part11List.add(line.getViewString(KEY_LST_DESTINATION_FOLDER), "");
                part11List.add(line.getViewString(_lcm_lst_HostEnvironment.getColumnKey(_lcm_lst_HostEnvironment.
                		getColumnIndex(KEY_LST_DESTINATION_FOLDER)).getShadowKey()), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_HostEnvironment.resetEditRow();
            _lcm_lst_HostEnvironment.resetHighlight();

            // set input parameters.
            HostEnvironmentSCHParams inparam = new HostEnvironmentSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_HostEnvironment.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_HostEnvironment.getNewLine();
                line.setValue(KEY_HDN_DATA_TYPE, outparam.get(HostEnvironmentSCHParams.HDN_TYPE));
                line.setValue(KEY_LST_DATA_NAME, outparam.get(HostEnvironmentSCHParams.DATA_NAME));
                line.setValue(KEY_LST_DATA_TYPE, outparam.get(HostEnvironmentSCHParams.DATA_TYPE));
                line.setValue(KEY_LST_DESTINATION_FOLDER, outparam.get(HostEnvironmentSCHParams.DESTINATION_FOLDER));
                line.setValue(KEY_LST_PREFIX_NAME, outparam.get(HostEnvironmentSCHParams.PREFIX_NAME));
                lst_HostEnvironment_SetLineToolTip(line);
                _lcm_lst_HostEnvironment.add(line);
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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        HostEnvironmentSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new HostEnvironmentSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            HostEnvironmentSCHParams inparam = new HostEnvironmentSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_HostEnvironment.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_HostEnvironment.getNewLine();
                line.setValue(KEY_HDN_DATA_TYPE, outparam.get(HostEnvironmentSCHParams.HDN_TYPE));
                line.setValue(KEY_LST_DATA_NAME, outparam.get(HostEnvironmentSCHParams.DATA_NAME));
                line.setValue(KEY_LST_DATA_TYPE, outparam.get(HostEnvironmentSCHParams.DATA_TYPE));
                line.setValue(KEY_LST_DESTINATION_FOLDER, outparam.get(HostEnvironmentSCHParams.DESTINATION_FOLDER));
                line.setValue(KEY_LST_PREFIX_NAME, outparam.get(HostEnvironmentSCHParams.PREFIX_NAME));
                lst_HostEnvironment_SetLineToolTip(line);
                _lcm_lst_HostEnvironment.add(line);
            }

            // set focus.
            setFocus(lst_HostEnvironment);

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
