// $Id: PCTItemDelete3Business.java 7162 2010-02-19 09:32:59Z shibamoto $
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
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.pcart.base.util.PCTFileFilter;
import jp.co.daifuku.pcart.base.util.PCTLogWriter;
import jp.co.daifuku.pcart.master.schedule.PCTItemDelete2SCHParams;
import jp.co.daifuku.pcart.master.schedule.PCTItemDelete3SCH;
import jp.co.daifuku.pcart.master.schedule.PCTMasterOutParameter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.SessionUtil;


/**
 * 商品マスタ削除(SAVE)の画面処理を行います。
 *
 * @version $Revision: 7162 $, $Date:: 2010-02-19 18:32:59 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class PCTItemDelete3Business
        extends PCTItemDelete3
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
     * 操作区分:SAVE
     */
    protected static final String OPELOG_SAVE = "1";

    // DFKLOOK:ここまで修正

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTItemDelete3Business()
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
    }

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
     * ファイル名を変更します
     * @throws Exception 
     */
    protected void fileRename()
            throws Exception
    {
        // パス
        File dir = new File(WmsParam.DMPDATA_FILE_PATH);
        // ファイル一覧
        File[] files = dir.listFiles(new PCTFileFilter());
        // ファイル名
        String fileName = WmsParam.DMPDATA_FILE_NAME.substring(0, WmsParam.DMPDATA_FILE_NAME.lastIndexOf('.'));
        // 拡張子
        String extension =
                WmsParam.DMPDATA_FILE_NAME.substring(WmsParam.DMPDATA_FILE_NAME.lastIndexOf('.') + 1).toUpperCase();

        for (int i = files.length - 1; i >= 0; i--)
        {
            int index = files[i].getName().lastIndexOf('.') - 1;
            try
            {
                int generation = Integer.parseInt(files[i].getName().substring(index, index + 1));

                // ファイル削除
                if (generation + 1 >= WmsParam.FILE_GENERATION)
                {
                    files[i].delete();
                    continue;
                }

                // 変更後ファイル
                File renameTo =
                        new File(WmsParam.DMPDATA_FILE_PATH + fileName + String.valueOf(generation + 1) + "."
                                + extension);
                // ファイル名変更
                files[i].renameTo(renameTo);
            }
            catch (NumberFormatException e)
            {
                // 変更後ファイル
                File renameTo = new File(WmsParam.DMPDATA_FILE_PATH + fileName + "1" + "." + extension);
                // ファイル名変更
                files[i].renameTo(renameTo);
            }
        }
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
    private void page_Load_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTItemDelete3SCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTItemDelete3SCH(conn, this.getClass(), locale, ui);

            // DFKLOOK:ここから修正
            // set input parameters.
            Parameter inparam = new Parameter();

            // SCH call.
            // 商品マスタ取込み状態をチェック
            PCTMasterOutParameter outParam = sch.initFind(inparam);

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

            // output display.
            lbl_In_DataFileFolder.setValue(WmsParam.DMPDATA_FILE_PATH);
            lbl_In_DataFileName.setValue(WmsParam.DMPDATA_FILE_NAME);
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
    private void btn_Start_Click_Process(String eventSource)
            throws Exception
    {
    	// DFKLOOK start
        if (StringUtil.isBlank(eventSource))
        {         
            // 開始しますか?
            this.setConfirm("MSG-W0031", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Start_Click");
            return;         
        }
        // DFKLOOK end
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;

        // DFKLOOK:ここから修正
        PCTItemDelete3SCH sch = null;
        // パラメータ
        Parameter param = new Parameter();

        // ファイルパス
        String filePath = WmsParam.DMPDATA_FILE_PATH;
        // 検索対象フォルダのFileインスタンスの生成
        File file = new File(filePath);
        // DFKLOOK:ここまで修正

        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTItemDelete3SCH(conn, this.getClass(), locale, ui);

            // DFKLOOK:ここから修正
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

            // ファイルが存在しない場合
            if (!file.exists())
            {
                file.mkdir();
            }
            else
            {
                // ファイルが存在する場合、リネーム
                fileRename();
            }

            PCTItemDelete2SCHParams inparam = new PCTItemDelete2SCHParams();
            inparam.set(PCTItemDelete2SCHParams.DATA_FILE_FOLDER, filePath);
            inparam.set(PCTItemDelete2SCHParams.DATA_FILE_NAME, WmsParam.DMPDATA_FILE_NAME);
            // DFKLOOK:ここまで修正

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }
            // DFKLOOK:ここから修正
            // オペレーションログ出力
            log_write(conn, EmConstants.OPELOG_CLASS_SETTING, OPELOG_SAVE);
            // DFKLOOK:ここまで修正

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

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
