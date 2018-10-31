// $Id: StockReportEnvironmentBusiness.java 7414 2010-03-06 05:36:39Z okayama $
package jp.co.daifuku.wms.system.display.stockreportenvironment;

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
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.system.schedule.StockReportEnvironmentSCH;
import jp.co.daifuku.wms.system.schedule.StockReportEnvironmentSCHParams;

/**
 * 在庫情報報告環境設定の画面処理を行います。
 *
 * @version $Revision: 7414 $, $Date: 2010-03-06 14:36:39 +0900 (土, 06 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class StockReportEnvironmentBusiness
        extends StockReportEnvironment
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
    
    //------------------------------------------------------------
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
    public StockReportEnvironmentBusiness()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面が初期表示されたときに呼ばれます。
     * 
     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
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
     * 各コントロールイベント呼び出し前に呼ばれます。
     * 
     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
     */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        // initialize componenets.
        initializeComponents();

        // process call.
        page_Initialize_Process();
    }

    // DFKLOOK start
    /**
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
        if (eventSource.startsWith("btn_Set_Click"))
        {
            // process call.
            btn_Set_Click_Process(eventSource);
        }
    }
	// DFKLOOK end

	/**
     * 設定ボタンが押下された時に呼ばれます。
     * 
     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
     */
    public void btn_Set_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Set_Click_Process(null);
    }

    /**
     * クリアボタンが押下された時に呼ばれます。
     * 
     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Clear_Click_Process();
    }

    /**
     * メニューへ遷移します。
     * 
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
     * コンポーネントの初期化処理を行います。
     * 
     * @throws Exception 全ての例外を報告します。
     */
    private void initializeComponents()
            throws Exception
    {
    }

    /**
     * プルダウンの初期化を行います。
     * 
     * @throws Exception 全ての例外を報告します。
     */
    private void initializePulldownModels()
            throws Exception
    {
    }

    /**
     * 後処理を行います。
     * 
     * @throws Exception 全ての例外を報告します。
     */
    private void dispose()
            throws Exception
    {
    }

    /**
     * ツールチップのセットを行います。
     * 
     * @param line ListCellLine
     * @throws Exception 全ての例外を報告します。
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_StockDataFolder);

    }

    /**
     * 画面の初期化処理を行います。
     * 
     * @throws Exception 全ての例外を報告します。
     */
    private void page_Load_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StockReportEnvironmentSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StockReportEnvironmentSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            StockReportEnvironmentSCHParams inparam = new StockReportEnvironmentSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_StockDataFolder.setValue(outparam.get(StockReportEnvironmentSCHParams.STOCK_DATA_FOLDER));
                txt_StockDataPrefix.setValue(outparam.get(StockReportEnvironmentSCHParams.STOCK_DATA_PREFIX));
                viewState.setObject(ViewStateKeys.VS_DATA_FOLDER, outparam.get(StockReportEnvironmentSCHParams.STOCK_DATA_FOLDER));
            }

            // clear.
            txt_StockDataPrefix.setReadOnly(true);

            // set focus.
            setFocus(txt_StockDataFolder);

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
     * 設定処理を行います。
     * 
     * @throws Exception 全ての例外を報告します。
     */
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    {
        // DFKLOOK 入力チェック追加
        if (this.viewState.getString(ViewStateKeys.VS_DATA_FOLDER).equals(txt_StockDataFolder.getText()))
        {
            setFocus(txt_StockDataFolder);

            // 6023059=更新対象データがありません。
            message.setMsgResourceKey("6023059");
            return;
        }
        // DFKLOOK ここまで
        
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
        StockReportEnvironmentSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StockReportEnvironmentSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            
            // DFKLOOK パラメータセット
            StockReportEnvironmentSCHParams param = new StockReportEnvironmentSCHParams();
            param.set(StockReportEnvironmentSCHParams.STOCK_DATA_FOLDER, txt_StockDataFolder.getValue());
            param.set(StockReportEnvironmentSCHParams.STOCK_DATA_PREFIX, txt_StockDataPrefix.getValue());
            inparamList.add(param);
            // DFKLOOK ここまで
            
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
            part11List.add("21", "");
            part11List.add(txt_StockDataFolder.getStringValue(), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_DATA_FOLDER), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // set input parameters.
            StockReportEnvironmentSCHParams inparam = new StockReportEnvironmentSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                txt_StockDataFolder.setValue(outparam.get(StockReportEnvironmentSCHParams.STOCK_DATA_FOLDER));
                txt_StockDataPrefix.setValue(outparam.get(StockReportEnvironmentSCHParams.STOCK_DATA_PREFIX));
                viewState.setObject(ViewStateKeys.VS_DATA_FOLDER, outparam.get(StockReportEnvironmentSCHParams.STOCK_DATA_FOLDER));
            }

            // clear.
            txt_StockDataPrefix.setReadOnly(true);

            // set focus.
            setFocus(txt_StockDataFolder);

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
     * クリア処理を行います。
     * 
     * @throws Exception 全ての例外を報告します。
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StockReportEnvironmentSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new StockReportEnvironmentSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            StockReportEnvironmentSCHParams inparam = new StockReportEnvironmentSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_StockDataFolder.setValue(outparam.get(StockReportEnvironmentSCHParams.STOCK_DATA_FOLDER));
                txt_StockDataPrefix.setValue(outparam.get(StockReportEnvironmentSCHParams.STOCK_DATA_PREFIX));
                viewState.setObject(ViewStateKeys.VS_DATA_FOLDER, outparam.get(StockReportEnvironmentSCHParams.STOCK_DATA_FOLDER));
            }

            // clear.
            txt_StockDataPrefix.setReadOnly(true);

            // set focus.
            setFocus(txt_StockDataFolder);

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
