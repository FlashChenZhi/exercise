// $Id: DailyUpdateBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.system.display.dailyupdate;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.emanager.util.P11LogController;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.launcher.LauncherManager;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.system.schedule.DailyUpdateSCH;
import jp.co.daifuku.wms.system.schedule.DailyUpdateSCHParams;
import jp.co.daifuku.wms.system.schedule.SystemInParameter;
import jp.co.daifuku.wms.system.schedule.SystemOutParameter;

import org.apache.commons.io.FileSystemUtils;

/**
 * 日次更新の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class DailyUpdateBusiness
        extends DailyUpdate
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_DailyUpdate(LST_STATUS) */
    private static final ListCellKey KEY_LST_STATUS = new ListCellKey("LST_STATUS", new StringCellColumn(), true, false);

    /** lst_DailyUpdate(LST_REASON) */
    private static final ListCellKey KEY_LST_REASON = new ListCellKey("LST_REASON", new StringCellColumn(), true, false);

    /** lst_DailyUpdate(LST_NG_HAPPENED_POINT) */
    private static final ListCellKey KEY_LST_NG_HAPPENED_POINT = new ListCellKey("LST_NG_HAPPENED_POINT", new StringCellColumn(), true, false);

    /** lst_DailyUpdate keys */
    private static final ListCellKey[] LST_DAILYUPDATE_KEYS = {
        KEY_LST_STATUS,
        KEY_LST_REASON,
        KEY_LST_NG_HAPPENED_POINT,
    };

    // DFKLOOK:ここから修正
    /**
     * OK,NG理由
     */
    private static final String UPDATE_OK = "0";

    /**
     * OK,NG理由
     */
    private static final String UPDATE_OK_CONTAIN_WARNING = "1";

    /**
     * OK,NG理由
     */
    private static final String UPDATE_NG = "2";

    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK:ここまで修正

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** RadioButtonGroupModel NoWorkInfomation */
    private RadioButtonGroup _grp_NoWorkInfomation;

    /** ListCellModel lst_DailyUpdate */
    private ListCellModel _lcm_lst_DailyUpdate;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public DailyUpdateBusiness()
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
        // DFKLOOK:ここから修正
        try
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
    		if(eventSource.equals("btn_Start_Click"))
    		{
    			// process call.
    			btn_Start_Click_Process(eventSource);
    		}
    		else if(eventSource.equals("DIALOG_START"))
    		{
                // 「処理中です」メッセージ表示
                message.setMsgResourceKey("6001017");
    			// process call.
                checkDVD();
    		}
    		else if(eventSource.equals("DIALOG_DVD"))
    		{
                // 「処理中です」メッセージ表示
                message.setMsgResourceKey("6001017");
    			// process call.
                startDailyUpdate();
    		}
    		else if(eventSource.equals("DIALOG_COPY"))
    		{
                // 「処理中です」メッセージ表示
                message.setMsgResourceKey("6001017");
    			// process call.
                dvdCopy();
    		}
        }
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_DvdCopy_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_DvdCopy_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void chk_BackupAndShutdown_Change(ActionEvent e)
            throws Exception
    {
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
        btn_Start_Click_Process(null);
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
    public void btn_ReDisplay_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ReDisplay_Click_Process();
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
    //DFKLOOK:ここから修正
    /**
     * 作業日とシステム日付の関連性により作業日の横に補足を追加します。
     *
     * @throws Exception
     */
    protected void setWorkDayMessage()
        throws Exception
    {
        // 作業日 > システム日付の場合は"作業日は既に更新されています。"を表示
        // 作業日 + 1日 < システム日付の場合は"作業日を現在の日付に更新します。"を表示
        // 上記以外の場合は何も表示しない
        Date systemDate = WmsFormatter.toDate(DbDateUtil.getSystemDate());
        Date ewNWorkDate = WmsFormatter.toDate(getWareNaviWorkDay());
        if (!StringUtil.isBlank(systemDate) && !StringUtil.isBlank(ewNWorkDate))
        {
            // 作業日+1日した日付を取得
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(ewNWorkDate);

            // 作業日 > システム日付の場合
            if (ewNWorkDate.compareTo(systemDate) > 0)
            {
                lbl_In_WorkDay.setText(DisplayText.getText("LBL-W0604"));
            }
            // 作業日 + 1日 < システム日付の場合
            else if (systemDate.compareTo(cal.getTime()) > 0)
            {
                lbl_In_WorkDay.setText(DisplayText.getText("LBL-W0605"));
            }
            // 上記以外の場合
            else
            {
                lbl_In_WorkDay.setText("");
            }
        }
    }
    //DFKLOOK:ここまで修正

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

        // initialize NoWorkInfomation.
        _grp_NoWorkInfomation = new RadioButtonGroup(new RadioButton[]{rdo_NoWorkInfomation_Delete, rdo_NoWorkInfomation_CarryOver}, locale);

        // initialize lst_DailyUpdate.
        _lcm_lst_DailyUpdate = new ListCellModel(lst_DailyUpdate, LST_DAILYUPDATE_KEYS, locale);
        _lcm_lst_DailyUpdate.setToolTipVisible(KEY_LST_STATUS, false);
        _lcm_lst_DailyUpdate.setToolTipVisible(KEY_LST_REASON, false);
        _lcm_lst_DailyUpdate.setToolTipVisible(KEY_LST_NG_HAPPENED_POINT, false);

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
    private void lst_DailyUpdate_SetLineToolTip(ListCellLine line)
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
        setFocus(txt_InWorkDate);

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
        DailyUpdateSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new DailyUpdateSCH(conn, this.getClass(), locale, ui);

            // DFKLOOK:ここから修正
            // 初期値
            rdo_NoWorkInfomation_Delete.setChecked(true);
            setFocus(rdo_NoWorkInfomation_Delete);
            chk_BackupAndShutdown.setChecked(true);

            // 入力エリアの初期化
            initFind();
            // DFKLOOK:ここまで修正

            // set input parameters.
            DailyUpdateSCHParams inparam = new DailyUpdateSCHParams();
            inparam.set(DailyUpdateSCHParams.WORK_DATE, txt_InWorkDate.getValue());
            inparam.set(DailyUpdateSCHParams.NO_WORK_INFOMATION, _grp_NoWorkInfomation.getSelectedValue());

            // DFKLOOK:ここから修正
            // 日次更新可能かチェック
            checkStatus(sch, inparam);
            // DFKLOOK:ここまで修正
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
    private void btn_DvdCopy_Click_Process()
            throws Exception
    {
        // DFKLOOK:ここから修正
        // 「処理中です」メッセージ表示
        message.setMsgResourceKey("6001017");

        try
        {
            String bacuDrive = EmProperties.getProperty("PART11LOG_EXTERNAL_DISK_PATH");
            File file_bacuDrive = new File(bacuDrive);
            if(file_bacuDrive.getUsableSpace() == 0 )
            {
                // メディア存在チェック
                // ※空き領域が0MBの場合、メディアが存在しないのか、容量がないのかが不明である。
            	//   "FileSystemUtils"を使用することで、ファイル/フォルダがなければIOExceptionとなるため、
            	//   その場合はメディアが存在しないと判断する。
            	FileSystemUtils.freeSpaceKb(bacuDrive);
            }
            // MSG-W0055=バックアップメディアの空き領域：{0}です。
            setConfirm(DisplayResource.format("MSG-W0055",
                    WmsFormatter.getNumFormat((file_bacuDrive.getUsableSpace() / 1024 ) / 1024)), true, true);

            // DVDからダイアログ表示されたことを記憶する
    		viewState.setString(_KEY_CONFIRMSOURCE, "DIALOG_COPY");
        }
        catch (IOException ex)
        {
            // MSG-W0056=バックアップメディアをドライブにセットしてください。
            setConfirm(DisplayResource.format("MSG-W0056"), true, true);
            // DVDからダイアログ表示されたことを記憶する
    		viewState.setString(_KEY_CONFIRMSOURCE, "DIALOG_COPY");
        }
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Start_Click_Process(String eventSource)
            throws Exception
    {
        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
        	// 開始しますか？
            this.setConfirm("MSG-W0031", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_Start_Click");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        DailyUpdateSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new DailyUpdateSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            DailyUpdateSCHParams inparam = new DailyUpdateSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(DailyUpdateSCHParams.WORK_DATE, txt_InWorkDate.getValue());
            inparam.set(DailyUpdateSCHParams.NO_WORK_INFOMATION, _grp_NoWorkInfomation.getSelectedValue());

            // DFKLOOK:ここから修正
            WarenaviSystemController sysCon = new WarenaviSystemController(conn, getClass());
            if (!sysCon.getWorkDay().equals(WmsFormatter.toParamDate(inparam.getDate(DailyUpdateSCHParams.WORK_DATE))))
            {
                // 6023194=作業日が変更されています。
                message.setMsgResourceKey("6023194");
                return;
            }

            // 日次更新可能かチェックを行う
            String status = checkStatus(sch, inparam);

            if (UPDATE_OK.equals(status))
            {
                // 「処理中です」メッセージ表示
                message.setMsgResourceKey("6001017");
                checkDVD();
                return;
            }
            else if (UPDATE_OK_CONTAIN_WARNING.equals(status))
            {
                // 警告のみの場合ダイアログを表示する。
                // MSG-W0031=警告が発生していますが、日次処理を実行してよろしいですか？
                setConfirm(DisplayResource.format("MSG-W0042"), true, true);
                // 開始からダイアログ表示されたことを記憶する
        		viewState.setString(_KEY_CONFIRMSOURCE, "DIALOG_START");
                // 「処理中です」メッセージ表示
                message.setMsgResourceKey("6001017");
            }
            else
            {
                // 特に何もしない
            }
            // DFKLOOK:ここまで修正

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
        // clear.
        // DFKLOOK:ここから修正
        // 初期値
        rdo_NoWorkInfomation_Delete.setChecked(true);
        setFocus(rdo_NoWorkInfomation_Delete);
        chk_BackupAndShutdown.setChecked(true);

        // 入力エリアの初期化
        initFind();
        // DFKLOOK:ここまで修正
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
        DailyUpdateSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new DailyUpdateSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            DailyUpdateSCHParams inparam = new DailyUpdateSCHParams();
            inparam.set(DailyUpdateSCHParams.WORK_DATE, txt_InWorkDate.getValue());
            inparam.set(DailyUpdateSCHParams.NO_WORK_INFOMATION, _grp_NoWorkInfomation.getSelectedValue());

            // DFKLOOK:ここから修正
            // 日次更新可能かチェック
            checkStatus(sch, inparam);

            // ラジオボタン：削除選択時
            if (rdo_NoWorkInfomation_Delete.getChecked())
            {
                // フォーカスセット
                setFocus(rdo_NoWorkInfomation_Delete);
            }
            // ラジオボタン：持ち越し選択時
            else
            {
                // フォーカスセット
                setFocus(rdo_NoWorkInfomation_CarryOver);
            }

            // 初期表示処理
            initFind();
            // DFKLOOK:ここまで修正
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

    // DFKLOOK:ここから修正
    /**
     * 初期表示の設定を行います。<BR>
     *
     * @param schedule 日次更新スケジューラ
     * @throws Exception 全ての例外を報告します。<BR>
     */
    private void initFind()
            throws Exception
    {
        // 作業日(読取専用)
        txt_InWorkDate.setReadOnly(true);

        // 前回実施日・時間(読取専用)
        txt_ExecutionDate.setReadOnly(true);
        txt_ExecutionTime.setReadOnly(true);

        // システム定義の作業を取得
        if (!StringUtil.isBlank(getWareNaviWorkDay()))
        {
            txt_InWorkDate.setDate(WmsFormatter.toDate(getWareNaviWorkDay()));
        }
        else
        {
            txt_InWorkDate.setText("");
        }

        // 前回実施日を取得
        if (!StringUtil.isBlank(getWareNaviDailyUpdateDate()))
        {
        	txt_ExecutionDate.setDate(getWareNaviDailyUpdateDate());
        	txt_ExecutionTime.setTime(getWareNaviDailyUpdateDate());
        }
        else
        {
        	txt_ExecutionDate.setText("");
        	txt_ExecutionTime.setText("");
        }

        // DVD出力ボタン表示制御
        if (isCopyFailedLastTime())
        {
            btn_DvdCopy.setVisible(true);
        }
        else
        {
            btn_DvdCopy.setVisible(false);
        }

        // 作業日メッセージを表示
        setWorkDayMessage();
    }

    // DFKLOOK:ここまで修正

    // DFKLOOK:ここから修正
    /**
     * システム定義情報の作業を取得します。<BR>
     * <BR>
     * @throws Exception 全ての例外を報告します。
     */
    private String getWareNaviWorkDay()
            throws Exception
    {
        // システム定義情報の生成
        WarenaviSystemController sysCon = null;
        // コネクションの生成
        Connection conn = null;

        try
        {
            // コネクションの取得
            conn = ConnectionManager.getRequestConnection(this);
            // システム定義情報生成
            sysCon = new WarenaviSystemController(conn, getClass());
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
        // 取得した作業日の取得
        return sysCon.getWorkDay();
    }

    // DFKLOOK:ここまで修正

    // DFKLOOK:ここから修正
    /**
     * システム定義情報の日次更新日を取得します。<BR>
     * <BR>
     * @throws Exception 全ての例外を報告します。
     */
    private Date getWareNaviDailyUpdateDate()
            throws Exception
    {
        // システム定義情報の生成
        WarenaviSystemController sysCon = null;
        // コネクションの生成
        Connection conn = null;

        try
        {
            // コネクションの取得
            conn = ConnectionManager.getRequestConnection(this);
            // システム定義情報生成
            sysCon = new WarenaviSystemController(conn, getClass());
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
        // 取得した作業日の取得
        return sysCon.getDailyUpdateDate();
    }

    // DFKLOOK:ここまで修正

    // DFKLOOK:ここから修正
    /**
     * 前回の処理でDVDへコピー失敗したファイルが残っているかどうかを判定します。
     *
     * @return 前回の処理でDVDへのコピーが失敗していた場合はtrue、それ以外はfalse
     */
    private boolean isCopyFailedLastTime()
            throws Exception
    {
        // コネクションの生成
        Connection conn = null;

        try
        {
            // コネクションの取得
            conn = ConnectionManager.getRequestConnection(this);
            // P11コントローラー生成
            P11LogController p11 = new P11LogController(conn);
            return p11.isCopyFailedLastTime();
        }
        catch (Exception ex)
        {
            // 異常の場合はfalseを返却
            return false;
        }
        finally
        {
            DBUtil.close(conn);
        }
    }

    // DFKLOOK:ここまで修正

    // DFKLOOK:ここから修正
    /**
     * 日次更新可能かチェックを行う。<BR>
     * <BR>
     * @param sch スケジューラ
     * @param param 画面入力値
     * @return 日次更新可能か否か
     * @throws Exception 全ての例外を通知します
     */
    private String checkStatus(DailyUpdateSCH sch, DailyUpdateSCHParams param)
            throws Exception
    {
        // 日次更新可能かチェックを行う
        List<Params> outParams = sch.query(param);
        message.setMsgResourceKey(sch.getMessage());

        // 返却パラメータがない場合、NGはなしなのでOKを返す
        if (outParams == null || outParams.size() == 0)
        {
            resetList();
            return UPDATE_OK;
        }

        // 返却パラメータがあった場合、エラーか警告のみか判別
        boolean hasWarningFlag = false;
        boolean hasNGFlag = false;

        for (Params outParam : outParams)
        {
            if (SystemOutParameter.DAILYUPDATE_STATUS_NG.equals(outParam.get(DailyUpdateSCHParams.STATUS)))
            {
                hasNGFlag = true;
            }
            else if (SystemOutParameter.DAILYUPDATE_STATUS_WARNING.equals(outParam.get(DailyUpdateSCHParams.STATUS)))
            {
                hasWarningFlag = true;
            }
        }

        // エラー内容にNG、警告が含まれない場合はOK
        if (!hasWarningFlag && !hasNGFlag)
        {
            resetList();
            return UPDATE_OK;
        }
        // NGは含まれないが、警告は含まれる場合は警告ありOK
        else if (hasWarningFlag && !hasNGFlag)
        {
            // リストセルエリアにNG理由をセット
            setList(outParams);
            return UPDATE_OK_CONTAIN_WARNING;
        }
        // NGが含まれる場合
        else
        {
            // リストセルエリアにNG理由をセット
            setList(outParams);
            return UPDATE_NG;
        }
    }

    // DFKLOOK:ここまで修正

    // DFKLOOK:ここから修正
    /**
     * リストセルを初期化する<BR>
     */
    private void resetList()
    {
        // リストセルクリア
        lst_DailyUpdate.clearRow();
    }

    // DFKLOOK:ここまで修正

    // DFKLOOK:ここから修正
    /**
     * リストセルにセットする。<BR>
     * <BR>
     * @param outParam List<Params>
     * @throws Exception 全ての例外を通知します
     */
    private void setList(List<Params> param)
            throws Exception
    {
        try
        {
            // リストのクリア処理
            _lcm_lst_DailyUpdate.clear();

            // 取得データの設定
            for (Params params : param)
            {
                // 行データの生成
                ListCellLine line = _lcm_lst_DailyUpdate.getNewLine();

                // 状態
                line.setValue(KEY_LST_STATUS,
                        DisplayResource.getDailyUpdateStatus(params.getString(DailyUpdateSCHParams.STATUS)));
                // NG理由
                line.setValue(KEY_LST_REASON, DisplayResource.getReason(params.getString(DailyUpdateSCHParams.REASON)));
                // 発生箇所
                line.setValue(KEY_LST_NG_HAPPENED_POINT,
                        DisplayResource.getNGHappenedPoint(params.getString(DailyUpdateSCHParams.NG_HAPPENED_POINT)));
                // ツールチップの設定
                lst_DailyUpdate_SetLineToolTip(line);

                // 行データの追加
                _lcm_lst_DailyUpdate.add(line);
            }

            // 再表示ボタンの有効化
            btn_ReDisplay.setEnabled(true);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
    }

    // DFKLOOK:ここまで修正

    // DFKLOOK:ここから修正
    /**
     * Part11ログを書き込むためのメディアのチェックを行います。<br>
     * @throws Exception 全ての例外を報告します。
     */
    protected void checkDVD()
            throws Exception
    {
        Connection conn = null;

        try
        {
            // コネクションの取得
            conn = ConnectionManager.getRequestConnection(this);

            P11LogController p11log = new P11LogController(conn);
            if (p11log.hasBackupData())
            {
                String bacuDrive = EmProperties.getProperty("PART11LOG_EXTERNAL_DISK_PATH");
                File file_bacuDrive = new File(bacuDrive);
                if(file_bacuDrive.getUsableSpace() == 0 )
                {
                    // メディア存在チェック
                    // ※空き領域が0MBの場合、メディアが存在しないのか、容量がないのかが不明である。
                	//   "FileSystemUtils"を使用することで、ファイル/フォルダがなければIOExceptionとなるため、
                	//   その場合はメディアが存在しないと判断する。
                	FileSystemUtils.freeSpaceKb(bacuDrive);
                }
                // MSG-W0055=バックアップメディアの空き領域：{0}です。
                setConfirm(DisplayResource.format("MSG-W0055",
                        WmsFormatter.getNumFormat((file_bacuDrive.getUsableSpace() / 1024 ) / 1024)), true, true);

                // DVDからダイアログ表示されたことを記憶する
        		viewState.setString(_KEY_CONFIRMSOURCE, "DIALOG_DVD");

            }
            else
            {
                // DVDコピー処理不要な場合は処理を開始する
                startDailyUpdate();
            }
        }
        catch (IOException e)
        {
            // MSG-W0056=バックアップメディアをドライブにセットしてください。
            setConfirm(DisplayResource.format("MSG-W0056"), true, true);
            // DVDからダイアログ表示されたことを記憶する
    		viewState.setString(_KEY_CONFIRMSOURCE, "DIALOG_DVD");
        }
        catch (Exception e)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(e, this));
        }
        finally
        {

            if (conn != null)
            {
                try
                {
                    conn.rollback();
                    conn.close();
                }
                catch (Exception ex2)
                {
                    ex2.printStackTrace();
                    message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex2, this));
                }
            }
        }
    }

    // DFKLOOK:ここまで修正

    // DFKLOOK:ここから修正
    /**
     * 日次更新処理を行います。<BR>
     *
     * @throws Exception 全ての例外を報告します。
     */
    protected void startDailyUpdate()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // 日次更新処理を開始する。
        Connection conn = null;

        DailyUpdateSCH sch = null;

        try
        {
            // コネクションの取得
            conn = ConnectionManager.getRequestConnection(this);

            // 入力パラメータを生成する
            DailyUpdateSCHParams inparam = new DailyUpdateSCHParams();
            inparam.set(DailyUpdateSCHParams.WORK_DATE, WmsFormatter.toParamDate(txt_InWorkDate.getDate()));

            // ラジオボタン：削除選択時
            if (rdo_NoWorkInfomation_Delete.getChecked())
            {
                inparam.set(DailyUpdateSCHParams.NO_WORK_INFOMATION, SystemInParameter.UNSTART_DELETE);
            }
            // ラジオボタン：持ち越し選択時
            else
            {
                inparam.set(DailyUpdateSCHParams.NO_WORK_INFOMATION, SystemInParameter.UNSTART_HOLD);
            }

            // スケジューラを生成
            sch = new DailyUpdateSCH(conn, this.getClass(), locale, ui);

            // 日次更新スケジュールを実行する
            if (sch.startSCH(inparam))
            {
                // オペレーションログ出力
                log_write(conn, EmConstants.OPELOG_CLASS_SETTING);

                conn.commit();
                message.setMsgResourceKey(sch.getMessage());

                initFind();

                // データバックアップ・シャットダウンを行う
                if(chk_BackupAndShutdown.getChecked())
                {
                    // Launcherへキック
                    jp.co.daifuku.launcher.Constants.ReturnType sts = jp.co.daifuku.launcher.Constants.ReturnType.Success;
                    LauncherManager.initialize("localhost", 10001);
                    // 通常終了・シャットダウン
                    sts = LauncherManager.executeCommand("SHUTDOWN_NORMAL", jp.co.daifuku.launcher.Constants.OSShutdownType.Shutdown);
                    if(sts.equals(jp.co.daifuku.launcher.Constants.ReturnType.CommandNotFound))
                    {
                        // ログ出力：指定された終了コマンドが存在しません。
                        RmiMsgLogClient.write(6026610, LogMessage.F_ERROR, "DailyUpdate");
                        // 予期しないエラーが発生しました。ログを参照してください。
                        message.setMsgResourceKey("6027009");
                    }
                    else if(sts.equals(jp.co.daifuku.launcher.Constants.ReturnType.InvalidOperation))
                    {
                        // ログ出力：ランチャーアプリケーションが操作中です。
                        RmiMsgLogClient.write(6026611, LogMessage.F_ERROR, "DailyUpdate");
                        // 予期しないエラーが発生しました。ログを参照してください。
                        message.setMsgResourceKey("6027009");
                    }
                }
            }
            else
            {
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
            }

            // ラジオボタン：削除選択時
            if (rdo_NoWorkInfomation_Delete.getChecked())
            {
                // フォーカスセット
                setFocus(rdo_NoWorkInfomation_Delete);
            }
            // ラジオボタン：持ち越し選択時
            else
            {
                // フォーカスセット
                setFocus(rdo_NoWorkInfomation_CarryOver);
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

    // DFKLOOK:ここまで修正

    // DFKLOOK:ここから修正
    /**
     * P11ログのDVDへのコピー処理を実施します<BR>
     * 日次処理にてコピーに失敗した場合のみ使用されます。<BR>
     *
     * @throws Exception 全ての例外を報告します。<BR>
     */
    protected void dvdCopy()
            throws Exception
    {
        try
        {
            // メディアがセットされていることを確認
            String bacuDrive = EmProperties.getProperty("PART11LOG_EXTERNAL_DISK_PATH");
            File file_bacuDrive = new File(bacuDrive);
            if(file_bacuDrive.getUsableSpace() == 0 )
            {
                // メディア存在チェック
                // ※空き領域が0MBの場合、メディアが存在しないのか、容量がないのかが不明である。
            	//   "FileSystemUtils"を使用することで、ファイル/フォルダがなければIOExceptionとなるため、
            	//   その場合はメディアが存在しないと判断する。
            	FileSystemUtils.freeSpaceKb(bacuDrive);
            }

        }
        catch (IOException ex)
        {
            // 6023241=バックアップメディアがドライブにセットされていません。
            message.setMsgResourceKey("6023241");
            return;
        }

        Connection conn = null;

        try
        {
            // コネクションの取得
            conn = ConnectionManager.getRequestConnection(this);

            P11LogController p11 = new P11LogController(conn);

            p11.copyToDvD();

            // DVDへのコピー処理失敗の場合
            if (p11.isFailedCopyToBackupMedia())
            {
                if (p11.isFailedCopyToBackupMedia())
                {
                    // 6007045=DVDへのコピーに失敗しました。ファイル名={0}
                    message.setMsgResourceKey(WmsMessageFormatter.format(6007044, p11.getCopyFailedFileName()));
                }
            }
            else
            {
                // 6001014=完了しました。
                message.setMsgResourceKey("6001014");
                btn_DvdCopy.setEnabled(false);
            }
        }
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            DBUtil.close(conn);
        }
    }

    // DFKLOOK:ここまで修正

    // DFKLOOK:ここから修正
    /**
     * オペレーションログ情報の書込み処理を行います <BR>
     * @param   conn            データベースコネクション
     * @param   operationKind  操作区分
     * @throws Exception 全ての例外を報告します。
     */
    protected void log_write(Connection conn, int operationKind)
            throws Exception
    {
        // write part11 log.
        P11LogWriter part11Writer = new P11LogWriter(conn);
        Part11List part11List = new Part11List();
        part11List.add(txt_InWorkDate.getStringValue(), "");
        part11List.add(txt_ExecutionDate.getStringValue(), txt_ExecutionTime.getStringValue(), "");
        part11List.add(SystemInParameter.UNSTART_DELETE, "", rdo_NoWorkInfomation_Delete.getChecked());
        part11List.add(SystemInParameter.UNSTART_HOLD, "", rdo_NoWorkInfomation_CarryOver.getChecked());
        part11List.add("1", "", chk_BackupAndShutdown.getChecked());
        part11List.add("0", "", !chk_BackupAndShutdown.getChecked());
        part11Writer.createOperationLog((DfkUserInfo)getUserInfo(), ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
    }

    // DFKLOOK:ここまで修正

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
