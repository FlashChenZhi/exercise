// $Id: AsAllocationClearBusiness.java 7404 2010-03-05 13:25:51Z shibamoto $
package jp.co.daifuku.wms.asrs.display.allocationclear;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.schedule.AsAllocationClearSCH;
import jp.co.daifuku.wms.asrs.schedule.AsAllocationClearSCHParams;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * 搬送データクリアの画面処理を行います。
 *
 * @version $Revision: 7404 $, $Date:: 2010-03-05 22:25:51 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class AsAllocationClearBusiness
        extends AsAllocationClear
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

    // DFKLOOK start
    /**
     * ダイアログ：警告
     */
    private static final String DIALOG_WARNING = "DIALOG_WARNING";

    /**
     * ダイアログ：開始
     */
    private static final String DIALOG_START = "DIALOG_START";
    // DFKLOOK end

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsAllocationClearBusiness()
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
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        // DFKLOOK start
        try
        {
            // ダイアログで「はい」が押された場合true
            // ダイアログで「いいえ」が押された場合false
            boolean isExecute = new Boolean(String.valueOf(e.getEventArgs().get(0))).booleanValue();

            // 「はい」が押された場合、搬送データクリアを開始する
            if (isExecute)
            {
                // どのダイアログからの戻りかをチェックする
                if (this.getViewState().getBoolean(DIALOG_WARNING))
                {
                    this.getViewState().setBoolean(DIALOG_WARNING, false);
                    // 「処理中です」メッセージ表示
                    message.setMsgResourceKey("6001017");

                    // 警告のみの場合ダイアログを表示する。
                    // MSG-W0061=管理者メンテナンス処理画面ですが、よろしいですか？
                    setConfirm(DisplayResource.format("MSG-W0063"), true, true);
                    // 開始からダイアログ表示されたことを記憶する        
                    this.getViewState().setBoolean(DIALOG_START, true);

                    return;
                }
                if (this.getViewState().getBoolean(DIALOG_START))
                {
                    this.getViewState().setBoolean(DIALOG_START, false);
                    // 「処理中です」メッセージ表示
                    message.setMsgResourceKey("6001017");
                    startAllocationClear();
                    return;
                }

                else
                {
                    return;
                }
            }
            // 「いいえ」を押された場合、処理を終了する
            else
            {
                this.getViewState().setBoolean(DIALOG_START, false);
                this.getViewState().setBoolean(DIALOG_WARNING, false);
                return;
            }
        }
        catch (Exception ex)
        {
            this.getViewState().setBoolean(DIALOG_START, false);
            this.getViewState().setBoolean(DIALOG_WARNING, false);
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        // DFKLOOK end
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
     * @throws Exception
     */
    private void btn_Start_Click_Process()
            throws Exception
    {
        // DFKLOOK start
        // 警告のみの場合ダイアログを表示する。
        // MSG-W0061=管理者メンテナンス処理画面ですが、よろしいですか？
        setConfirm(DisplayResource.format("MSG-W0062"), true, true);
        // 開始からダイアログ表示されたことを記憶する        
        this.getViewState().setBoolean(DIALOG_WARNING, true);
        // 「処理中です」メッセージ表示
        message.setMsgResourceKey("6001017");
        // DFKLOOK end
    }

    // DFKLOOK start
    /**
     * 搬送データクリアを開始します。
     * @throws Exception 
     *
     */
    protected void startAllocationClear()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsAllocationClearSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsAllocationClearSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsAllocationClearSCHParams inparam = new AsAllocationClearSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

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
