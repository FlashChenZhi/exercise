// $Id: WorkingTimeConfigBusiness.java 7417 2010-03-06 05:42:21Z okayama $
package jp.co.daifuku.wms.analysis.display.workingtimeconfig;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.analysis.operator.AnalysisIniFileHandler;
import jp.co.daifuku.wms.analysis.schedule.AnalysisOutParameter;
import jp.co.daifuku.wms.analysis.schedule.WorkingTimeConfigSCH;
import jp.co.daifuku.wms.analysis.schedule.WorkingTimeConfigSCHParams;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * 作業時間予測（条件入力）の画面処理を行います。
 *
 * @version $Revision: 7417 $, $Date: 2010-03-06 14:42:21 +0900 (土, 06 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class WorkingTimeConfigBusiness
        extends WorkingTimeConfig
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
    public WorkingTimeConfigBusiness()
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
        // DFKLOOK start
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
            btn_Setting_Click_Process(eventSource);
        }
        // DFKLOOK end
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_StorageWkTimeResult_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_StorageWkTimeResult_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_RetrievalWkTimeResult_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_RetrievalWkTimeResult_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Setting_Click(ActionEvent e)
            throws Exception
    {
    	// DFKLOOK 引数追加
        // process call.
        btn_Setting_Click_Process(null);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Restore_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Restore_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Reflesh_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Reflesh_Click_Process();
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
     * 引数で指定されたDouble値を、指定小数点より後ろは四捨五入し、
     * 文字列として返却します。
     * 
     * @param db 指定値
     * @param scale 有効小数点
     * @return 四捨五入後の文字列
     */
    protected String setScale(double db, int scale)
    {
        return String.valueOf((new BigDecimal(db)).setScale(scale, BigDecimal.ROUND_HALF_UP));
    }

    /** 
     * 商品毎作業時間、数量毎作業時間を設定ファイルよりセットします。<BR>
     * パッケージの導入状態によりコントロールのReadOnly化も行います。
     * @param conn コネクション
     * @param prev 前回値ロード
     * @return 正常に設定ファイルよりセットできた場合は<code>true</code>を返します。
     * @throws Exception 全ての例外を報告します。
     */
    private boolean loadAnalysisIni(Connection conn, boolean prev)
            throws Exception
    {
        // 分析設定ファイルハンドラーを生成します。
        AnalysisIniFileHandler iniP = new AnalysisIniFileHandler();
        // ファイルの読み込みを行います。
        if (!iniP.load())
        {
            // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
            message.setMsgResourceKey("6007031");
            return false;
        }

        try
        {
            WarenaviSystemController controller = new WarenaviSystemController(conn, getClass());

            // 入庫作業
            if (!controller.hasStoragePack())
            {
                txt_StorageSecPerItem.setReadOnly(true);
                txt_StorageSecPerPiece.setReadOnly(true);
                btn_StorageWkTimeResult.setEnabled(false);

                txt_StorageSecPerItem.setText("");
                txt_StorageSecPerPiece.setText("");
            }
            else
            {
                txt_StorageSecPerItem.setReadOnly(false);
                txt_StorageSecPerPiece.setReadOnly(false);
                btn_StorageWkTimeResult.setEnabled(true);

                if (prev)
                {
                    txt_StorageSecPerItem.setText(iniP.getStorageSecPerItemPrev());
                    txt_StorageSecPerPiece.setText(iniP.getStorageSecPerPiecePrev());
                }
                else
                {
                    txt_StorageSecPerItem.setText(iniP.getStorageSecPerItem());
                    txt_StorageSecPerPiece.setText(iniP.getStorageSecPerPiece());
                }
            }

            // 出庫作業
            if (!controller.hasRetrievalPack())
            {
                txt_RetrievalSecPerItem.setReadOnly(true);
                txt_RetrievalSecPerPiece.setReadOnly(true);
                btn_RetrievalWkTimeResult.setEnabled(false);

                txt_RetrievalSecPerItem.setText("");
                txt_RetrievalSecPerPiece.setText("");
            }
            else
            {
                txt_RetrievalSecPerItem.setReadOnly(false);
                txt_RetrievalSecPerPiece.setReadOnly(false);
                btn_RetrievalWkTimeResult.setEnabled(true);

                if (prev)
                {
                    txt_RetrievalSecPerItem.setText(iniP.getRetrievalSecPerItemPrev());
                    txt_RetrievalSecPerPiece.setText(iniP.getRetrievalSecPerPiecePrev());
                }
                else
                {
                    txt_RetrievalSecPerItem.setText(iniP.getRetrievalSecPerItem());
                    txt_RetrievalSecPerPiece.setText(iniP.getRetrievalSecPerPiece());
                }
            }

            if (prev)
            {
                // 6021027=前回値を復元しました。
                message.setMsgResourceKey("6021027");
            }
            return true;
        }
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        return false;
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
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_StorageSecPerItem);

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
        WorkingTimeConfigSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new WorkingTimeConfigSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            WorkingTimeConfigSCHParams inparam = new WorkingTimeConfigSCHParams();

            // DFKLOOK ここから削除
            // スケジュールの呼び出しと表示データのセットはiniFind()の結果で行なうので、BusiTuneで生成した
            // この処理はコメントにする。
            //inparam.set(WorkingTimeConfigSCHParams.KIND_WORK, "");
            //
            // SCH call.
            //List<Params> outparams = sch.query(inparam);
            //message.setMsgResourceKey(sch.getMessage());
            //
            // output display.
            //for (Params outparam : outparams)
            //{
            //    txt_StorageSecPerItem.setValue(outparam.get(WorkingTimeConfigSCHParams.STORAGE_SEC_PER_ITEM));
            //    txt_StorageSecPerPiece.setValue(outparam.get(WorkingTimeConfigSCHParams.STORAGE_SEC_PER_PIECE));
            //    txt_StorageAveTimeItem.setValue(outparam.get(WorkingTimeConfigSCHParams.STORAGE_AVE_TIME_ITEM));
            //    txt_StorageAvePiece.setValue(outparam.get(WorkingTimeConfigSCHParams.STORAGE_AVE_PIECE));
            //    txt_StorageAveTimePiece.setValue(outparam.get(WorkingTimeConfigSCHParams.STORAGE_AVE_TIME_PIECE));
            //    txt_RetrievalSecPerItem.setValue(outparam.get(WorkingTimeConfigSCHParams.RETRIEVAL_SEC_PER_ITEM));
            //    txt_RetrievalSecPerPiece.setValue(outparam.get(WorkingTimeConfigSCHParams.RETRIEVAL_SEC_PER_PIECE));
            //    txt_RetrievalAveTimeItem.setValue(outparam.get(WorkingTimeConfigSCHParams.RETRIEVAL_AVE_TIME_ITEM));
            //    txt_RetrievalAvePiece.setValue(outparam.get(WorkingTimeConfigSCHParams.RETRIEVAL_AVE_PIECE));
            //    txt_RetrievalAveTimePiece.setValue(outparam.get(WorkingTimeConfigSCHParams.RETRIEVAL_AVE_TIME_PIECE));
            //}
            // DFKLOOK ここまで削除

            // clear.
            txt_StorageAveTimeItem.setReadOnly(true);
            txt_StorageAvePiece.setReadOnly(true);
            txt_StorageAveTimePiece.setReadOnly(true);
            txt_RetrievalAveTimeItem.setReadOnly(true);
            txt_RetrievalAvePiece.setReadOnly(true);
            txt_RetrievalAveTimePiece.setReadOnly(true);

            // DFKLOOK ここから追加
            // 設定値の読み込み
            if (loadAnalysisIni(conn, false))
            {
                // 商品毎平均時間、商品毎平均ピース数、ピース毎平均時間
                // 表示データが存在した場合のみメッセージを表示します。
                // 各表示テキストボックスの有効桁数
                int sclSecItem = 2;
                int sclSecPiece = 2;
                int sclPiecePerItem = 0;

                // 入庫の実績グラフが表示されていれば、インストールされているので初期表示値を取得
                boolean editData = false;
                txt_StorageAveTimeItem.setText("");
                txt_StorageAveTimePiece.setText("");
                txt_StorageAvePiece.setText("");
                if (btn_StorageWkTimeResult.getEnabled() == true)
                {
                    // 入庫用
                    inparam.set(WorkingTimeConfigSCHParams.KIND_WORK, DispResources.getText("LBL-W1222"));
                    AnalysisOutParameter anaParam = (AnalysisOutParameter)sch.initFind(inparam);

                    if (anaParam != null)
                    {
                        // 初期表示値が取得できた場合
                        txt_StorageAveTimeItem.setText(setScale(anaParam.getAveSecPerItem(), sclSecItem));
                        txt_StorageAveTimePiece.setText(setScale(anaParam.getAveSecPerPiece(), sclSecPiece));
                        txt_StorageAvePiece.setText(setScale(anaParam.getAvePiecesPerItem(), sclPiecePerItem));
                        editData = true;
                    }
                }

                // 出庫の実績グラフが表示されていれば、インストールされているので初期表示値を取得
                txt_RetrievalAveTimeItem.setText("");
                txt_RetrievalAveTimePiece.setText("");
                txt_RetrievalAvePiece.setText("");
                if (btn_RetrievalWkTimeResult.getEnabled() == true)
                {
                    // 出庫用
                    inparam.set(WorkingTimeConfigSCHParams.KIND_WORK, DispResources.getText("LBL-W1223"));
                    AnalysisOutParameter anaParam = (AnalysisOutParameter)sch.initFind(inparam);

                    if (anaParam != null)
                    {
                        // 初期表示値が取得できた場合
                        txt_RetrievalAveTimeItem.setText(setScale(anaParam.getAveSecPerItem(), sclSecItem));
                        txt_RetrievalAveTimePiece.setText(setScale(anaParam.getAveSecPerPiece(), sclSecPiece));
                        txt_RetrievalAvePiece.setText(setScale(anaParam.getAvePiecesPerItem(), sclPiecePerItem));
                        editData = true;
                    }
                }

                // 商品毎平均時間、商品毎平均ピース数、ピース毎平均時間
                // 表示データが存在した場合のみメッセージを表示します。
                if (editData)
                {
                    // 6001013=表示しました。
                    message.setMsgResourceKey("6001013");
                }
                else
                {
                    // 6003011=対象データはありませんでした。
                    message.setMsgResourceKey("6003011");
                }
            }
            // DFKLOOK ここまで追加
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
    private void btn_StorageWkTimeResult_Click_Process()
            throws Exception
    {
        try
        {
            // set ViewState parameters.
            // DFKLOOK 引数を変更
            viewState.setObject(ViewStateKeys.KIND_OF_WORK, DispResources.getText("LBL-W1222"));
            viewState.setObject(ViewStateKeys.STORAGE_SEC_PER_ITEM, txt_StorageSecPerItem.getValue());
            viewState.setObject(ViewStateKeys.STORAGE_SEC_PER_PIECE, txt_StorageSecPerPiece.getValue());

            // forward.
            forward("/analysis/workingtimeconfig/WorkingTimeConfig2.do");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_RetrievalWkTimeResult_Click_Process()
            throws Exception
    {
        try
        {
            // set ViewState parameters.
            // DFKLOOK 引数を変更
            viewState.setObject(ViewStateKeys.KIND_OF_WORK, DispResources.getText("LBL-W1223"));
            // DFKLOOK ここから削除
            // viewState.setObject(ViewStateKeys.RETRIEVAL_SEC_PER_ITEM, txt_RetrievalSecPerItem.getValue());
            // viewState.setObject(ViewStateKeys.RETRIEVAL_SEC_PER_PIECE, txt_RetrievalSecPerPiece.getValue());
            // DFKLOOK ここまで削除
            viewState.setObject(ViewStateKeys.STORAGE_SEC_PER_ITEM, txt_RetrievalSecPerItem.getValue());
            viewState.setObject(ViewStateKeys.STORAGE_SEC_PER_PIECE, txt_RetrievalSecPerPiece.getValue());

            // forward.
            forward("/analysis/workingtimeconfig/WorkingTimeConfig2.do");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
    }

    /**
     *
     * @throws Exception
     */
    // DFKLOOK 引数追加
    private void btn_Setting_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        // DFKLOOK 入庫パッケージ導入チェックを追加
        // 入力テキストボックスがRead Only ならば入庫パッケージは導入されていない
        if (!txt_StorageSecPerItem.getReadOnly())
        {
            txt_StorageSecPerItem.validate(this, true);
            txt_StorageSecPerPiece.validate(this, true);
        }
        // DFKLOOK 出庫パッケージ導入チェックを追加
        // 入力テキストボックスがRead Only ならば出庫パッケージは導入されていない
        if (!txt_RetrievalSecPerItem.getReadOnly())
        {
            txt_RetrievalSecPerItem.validate(this, true);
            txt_RetrievalSecPerPiece.validate(this, true);
        }

        // DFKLOOK start
        if (StringUtil.isBlank(eventSource))
        {
            // 設定しますか?
            this.setConfirm("MSG-W9000", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        WorkingTimeConfigSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new WorkingTimeConfigSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            WorkingTimeConfigSCHParams inparam = new WorkingTimeConfigSCHParams();

            // DFKLOOK ここからコメント
            // inparam.setProcessFlag(ProcessFlag.REGIST);
            // inparam.set(WorkingTimeConfigSCHParams.STORAGE_SEC_PER_ITEM, txt_StorageSecPerItem.getValue());
            // inparam.set(WorkingTimeConfigSCHParams.STORAGE_SEC_PER_PIECE, txt_StorageSecPerPiece.getValue());
            // inparam.set(WorkingTimeConfigSCHParams.RETRIEVAL_SEC_PER_ITEM, txt_RetrievalSecPerItem.getValue());
            // inparam.set(WorkingTimeConfigSCHParams.RETRIEVAL_SEC_PER_PIECE, txt_RetrievalSecPerPiece.getValue());
            // DFKLOOK ここまでコメント

            // DFKLOOK ここから追加
            // 導入パッケージによってパラメータを設定する。
            WarenaviSystemController controller = new WarenaviSystemController(conn, getClass());
            if (controller.hasStoragePack())
            {
                // 入庫作業商品毎時間
                inparam.set(WorkingTimeConfigSCHParams.STORAGE_SEC_PER_ITEM, txt_StorageSecPerItem.getValue());
                // 入庫ピース毎時間
                inparam.set(WorkingTimeConfigSCHParams.STORAGE_SEC_PER_PIECE, txt_StorageSecPerPiece.getValue());
            }
            if (controller.hasRetrievalPack())
            {
                // 出庫作業商品毎時間
                inparam.set(WorkingTimeConfigSCHParams.RETRIEVAL_SEC_PER_ITEM, txt_RetrievalSecPerItem.getValue());
                // 出庫ピース毎時間
                inparam.set(WorkingTimeConfigSCHParams.RETRIEVAL_SEC_PER_PIECE, txt_RetrievalSecPerPiece.getValue());
            }
            // DFKLOOK ここまで追加

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(txt_StorageSecPerItem.getStringValue(), "0");
            part11List.add(txt_StorageSecPerPiece.getStringValue(), "0");
            part11List.add(txt_StorageAveTimeItem.getStringValue(), "0");
            part11List.add(txt_StorageAvePiece.getStringValue(), "0");
            part11List.add(txt_StorageAveTimePiece.getStringValue(), "0");
            part11List.add(txt_RetrievalSecPerItem.getStringValue(), "0");
            part11List.add(txt_RetrievalSecPerPiece.getStringValue(), "0");
            part11List.add(txt_RetrievalAveTimeItem.getStringValue(), "0");
            part11List.add(txt_RetrievalAvePiece.getStringValue(), "0");
            part11List.add(txt_RetrievalAveTimePiece.getStringValue(), "0");
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

    /**
     *
     * @throws Exception
     */
    private void btn_Restore_Click_Process()
            throws Exception
    {
        // DFKLOOK ここから追加
        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            // 前回設定値の読み込みを行います。
            loadAnalysisIni(conn, true);
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
        // DFKLOOK ここまで追加
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Reflesh_Click_Process()
            throws Exception
    {
        // DFKLOOK 画面を再表示します。
        page_Load_Process();
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
