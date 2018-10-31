// $Id: PCTItemDelete2Business.java 7162 2010-02-19 09:32:59Z shibamoto $
package jp.co.daifuku.pcart.master.display.pctitemdelete;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.PulldownHelper;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.pcart.base.util.PCTFileFilter;
import jp.co.daifuku.pcart.base.util.PCTLogWriter;
import jp.co.daifuku.pcart.master.schedule.PCTItemDelete2SCH;
import jp.co.daifuku.pcart.master.schedule.PCTItemDelete2SCHParams;
import jp.co.daifuku.pcart.master.schedule.PCTMasterOutParameter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * 商品マスタ削除(LOAD)の画面処理を行います。
 *
 * @version $Revision: 7162 $, $Date:: 2010-02-19 18:32:59 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class PCTItemDelete2Business
        extends PCTItemDelete2
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    // DFKLOOK:start
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK:end
    
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // DFKLOOK:ここから修正
    /**
     * PCT商品マスタ取込みフラグ
     */
    private static String $loadFlag;

    /**
     * 操作区分:LOAD
     */
    protected static final String OPELOG_LOAD = "2";

    // DFKLOOK:ここまで修正

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_DataFile */
    private DefaultPullDownModel _pdm_pul_DataFile;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTItemDelete2Business()
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
        // initialize componenets.
        initializeComponents();

        // process call.
        page_Initialize_Process();
    }

    // DFKLOOK:ここから修正
    /**
     * 定期送信時に呼び出されます。
     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
     */
    public void page_ClientPull(ActionEvent e)
            throws Exception
    {
        String flg = $loadFlag;
        page_Load_Process();

        if (StringUtil.isBlank($loadFlag))
        {
            if (SystemDefine.PCTMASTER_LOAD_FLAG_SAVE.equals(flg))
            {
                // 6001025=セーブ処理が完了しました。
                message.setMsgResourceKey(WmsMessageFormatter.format(6001025));
            }
            else if (SystemDefine.PCTMASTER_LOAD_FLAG_LOAD.equals(flg))
            {
                // 6001026=ロード処理が完了しました。
                message.setMsgResourceKey(WmsMessageFormatter.format(6001026));
            }
        }
    }
    // DFKLOOK:ここまで修正

    // DFKLOOK start
    /**
    *
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
        if (eventSource.startsWith("btn_Start_Click"))
        {
            btn_Start_Click_Process(eventSource);
        }
    }
    // DFKLOOK end
    
    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Back_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Back_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pul_DataFile_Change(ActionEvent e)
            throws Exception
    {
        // process call.
        pul_DataFile_Change_Process();
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
    // DFKLOOK:ここから修正
    /**
     * データファイルプルダウンで選択されているファイルが存在するか確認します。
     * @return 存在するとき:true
     */
    protected boolean hasFile()
    {
        // ファイルのパス
        File file = new File(lbl_In_DataFileFolder.getText() + pul_DataFile.getSelectedValue());
        return file.exists();
    }

    /**
     * データファイルプルダウンに表示するファイル一覧を取得します。
     * @return ファイル一覧
     */
    protected String[] getFileList()
    {
        File dir = new File(WmsParam.DMPDATA_FILE_PATH);
        File[] files = dir.listFiles(new PCTFileFilter());
        String select = "1";
        String pdDelim = ",";
        Vector<String> array = new Vector<String>();

        if (files != null)
        {
            for (int i = 0; i < files.length; i++)
            {
                array.add(i, files[i].getName() + pdDelim + files[i].getName() + pdDelim + "" + pdDelim + select);
                select = "0";
            }
        }

        String[] file = new String[array.size()];
        array.toArray(file);
        return file;
    }

    /**
     * 指定されたファイルの最終更新日を取得します。
     * @param fileName ファイル名
     * @return 最終更新日
     */
    protected Date getLastModified(String fileName)
    {
        File dir = new File(WmsParam.DMPDATA_FILE_PATH);
        File[] files = dir.listFiles(new PCTFileFilter());
        long lastModified = 0;

        for (int i = 0; i < files.length; i++)
        {
            if (fileName.equals(files[i].getName()))
            {
                lastModified = files[i].lastModified();
            }
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(lastModified));
        return cal.getTime();
    }

    /**
     * オペレーションログ情報の書込み処理を行います <BR>
     * @param   conn            データベースコネクション
     * @param   operationKind  操作区分
     * @param   flag 区分
     * @throws Exception 全ての例外を報告します。
     */
    protected void log_write(Connection conn, int operationKind, String flag)
            throws Exception
    {
        // オペレーションログ出力
        List<String> itemLog = new ArrayList<String>();

        // 操作区分
        itemLog.add(flag);

        // ログ出力
        PCTLogWriter opeLogWriter = new PCTLogWriter(conn);
        opeLogWriter.createOperationLog((DfkUserInfo)getUserInfo(), operationKind, itemLog);
    }

    // DFKLOOK:ここまで修正
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
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_DataFile.
        _pdm_pul_DataFile = new DefaultPullDownModel(pul_DataFile, locale, ui);

    }

    /**
     *
     * @throws Exception
     */
    private void initializePulldownModels()
            throws Exception
    {
        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // load pul_DataFile.
            _pdm_pul_DataFile.init(conn);

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
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(pul_DataFile);

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
        PCTItemDelete2SCH sch = null;
        // パラメータ
        Parameter param = new Parameter();
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTItemDelete2SCH(conn, this.getClass(), locale, ui);

            // DFKLOOK:ここから修正
            // 商品マスタ取込み状態を取得
            PCTMasterOutParameter outParam = sch.initFind(param);

            if (!StringUtil.isBlank(sch.getMessage()))
            {
                // メッセージセット
                message.setMsgResourceKey(sch.getMessage());
                // 開始ボタン無効化
                btn_Start.setEnabled(false);
                // 監視
                setRegularTransmission(true);
                // フラグ保持
                $loadFlag = outParam.getPctMasterLoadFlag();
            }
            else
            {
                // メッセージセット
                message.setMsgResourceKey("");
                // 開始ボタン有効化
                btn_Start.setEnabled(true);
                // 監視停止
                setRegularTransmission(false);
                // フラグクリア
                $loadFlag = "";
            }

            // ファイル名プルダウン
            pul_DataFile.clearItem();
            PulldownHelper.setPullDown(pul_DataFile, getFileList());

            // パス名
            lbl_In_DataFileFolder.setText(WmsParam.DMPDATA_FILE_PATH);

            pul_DataFile.setSelectedIndex(0);

            if (StringUtil.isBlank(pul_DataFile.getSelectedValue()))
            {
                // 6003009=ファイルが見つかりませんでした。{0}
                message.setMsgResourceKey(WmsMessageFormatter.format(6003009, WmsParam.DMPDATA_FILE_NAME));
                // 開始ボタン無効化
                btn_Start.setEnabled(false);
                return;
            }
            // データファイル最終更新日
            String date =
                    WmsFormatter.toDispDate(getLastModified(pul_DataFile.getSelectedValue()),
                            this.getHttpRequest().getLocale());
            txt_R_FileCreationDate.setText(date);
            // DFKLOOK:ここまで修正
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }
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
    private void btn_Back_Click_Process()
            throws Exception
    {
        // forward.
        forward("/pcart/master/pctitemdelete/PCTItemDelete.do");
    }

    /**
     *
     * @throws Exception
     */
    private void pul_DataFile_Change_Process()
            throws Exception
    {
        // DFKLOOK:ここから修正
        // ファイル存在チェック
        if (!hasFile())
        {
            // 最終更新日テキストボックスのクリア
            txt_R_FileCreationDate.setText(null);
            // 6003009=ファイルが見つかりませんでした。{0}
            message.setMsgResourceKey(WmsMessageFormatter.format(6003009, pul_DataFile.getSelectedValue()));
            return;
        }
        // データファイル最終更新日
        String date =
                WmsFormatter.toDispDate(getLastModified(pul_DataFile.getSelectedValue()),
                        this.getHttpRequest().getLocale());
        txt_R_FileCreationDate.setText(date);
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Start_Click_Process(String eventSource)
            throws Exception
    {
		pul_DataFile.validate(this, true);
		
        // DFKLOOK:ここから修正
        // ファイル存在チェック
        if (!hasFile())
        {
            // 最終更新日テキストボックスのクリア
            txt_R_FileCreationDate.setText(null);
            // 6003009=ファイルが見つかりませんでした。{0}
            message.setMsgResourceKey(WmsMessageFormatter.format(6003009, pul_DataFile.getSelectedValue()));
            return;
        }

        if (StringUtil.isBlank(eventSource))
        {         
            // 開始しますか?
            this.setConfirm("MSG-W0031", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Start_Click");
            return;         
        }
        // DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTItemDelete2SCH sch = null;

        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTItemDelete2SCH(conn, this.getClass(), locale, ui);

            // DFKLOOK:ここから修正
            // パラメータ
            Parameter param = new Parameter();

            // 商品マスタ取込み状態をチェック
            PCTMasterOutParameter outParam = (PCTMasterOutParameter)sch.initFind(param);
            if (!StringUtil.isBlank(sch.getMessage()))
            {
                // メッセージセット
                message.setMsgResourceKey(sch.getMessage());
                // 開始ボタン無効化
                btn_Start.setEnabled(false);
                // 監視
                setRegularTransmission(true);
                // フラグ保持
                $loadFlag = outParam.getPctMasterLoadFlag();
                return;
            }

            // ロード処理実行前チェック
            if (!sch.check(param))
            {
                // メッセージセット
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // set input parameters.
            PCTItemDelete2SCHParams inparam = new PCTItemDelete2SCHParams();
            //            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(PCTItemDelete2SCHParams.DATA_FILE_FOLDER, WmsParam.DMPDATA_FILE_PATH);
            inparam.set(PCTItemDelete2SCHParams.DATA_FILE_NAME, _pdm_pul_DataFile.getSelectedValue());

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // オペレーションログ出力
            log_write(conn, EmConstants.OPELOG_CLASS_SETTING, OPELOG_LOAD);
            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());
            // DFKLOOK:ここまで修正
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }
        }
        finally
        {
            DBUtil.rollback(conn);
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
