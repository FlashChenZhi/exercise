// $Id: WorkingTimeSimuBusiness.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.analysis.display.workingtimesimu;

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
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.Constants;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;

import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;

import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.analysis.schedule.AnalysisOutParameter;
import jp.co.daifuku.wms.analysis.schedule.WorkingTimeSimuSCH;
import jp.co.daifuku.wms.analysis.schedule.WorkingTimeSimuSCHParams;

/**
 * 作業時間予測（予測処理）の画面処理を行います。
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class WorkingTimeSimuBusiness
        extends WorkingTimeSimu
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
    /** RadioButtonGroupModel SearchDate */
    private RadioButtonGroup _grp_SearchDate;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public WorkingTimeSimuBusiness()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面が読み込まれたときに呼ばれます。
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
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
     * 各コントロールイベント呼び出し前に呼び出されます。
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
     */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        // initialize componenets.
        initializeComponents();

        // process call.
        page_Initialize_Process();
    }

    /**
     * 検索対象 全て のラジオボタンが押された時に呼び出されます。
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
     */
    public void rdo_SearchPlanDateAll_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_SearchPlanDateAll_Click_Process();
    }

    /**
     *検索対象 指定予定日 のラジオボタンが押された時に呼び出されます。
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
     */
    public void rdo_SearchPlanDateInput_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_SearchPlanDateInput_Click_Process();
    }

    /**
     * 表示ボタンが押された時に呼び出されます。
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
     */
    public void btn_Select_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Select_Click_Process();
    }

    /**
     * 予測実行ボタンが押された時に呼び出されます。
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
     */
    public void btn_Simulate_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Simulate_Click_Process();
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
     * @throws Exception All the exceptions are reported.
     */
    private void initializeComponents()
            throws Exception
    {
        // get locale.
        Locale locale = httpRequest.getLocale();

        // initialize SearchDate.
        _grp_SearchDate = new RadioButtonGroup(new RadioButton[]{rdo_SearchPlanDateAll, rdo_SearchPlanDateInput}, locale);

    }

    /**
     *
     * @throws Exception All the exceptions are reported.
     */
    private void initializePulldownModels()
            throws Exception
    {
    }

    /**
     *
     * @throws Exception All the exceptions are reported.
     */
    private void dispose()
            throws Exception
    {
    }

    /**
     * カーソルを移動します。
     * @throws Exception All the exceptions are reported.
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        // DFKLOOK setFocusをコメントにする
        // setFocus(rdo_SearchPlanDateAll);
        // DFKLOOK １行追加
        setFocus();

    }

    /**
     * 画面の初期表示を行います。
     * @throws Exception All the exceptions are reported.
     */
    private void page_Load_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        WorkingTimeSimuSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new WorkingTimeSimuSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            // DFKLOOK パラメータのセットをコメント化
            // WorkingTimeSimuSCHParams inparam = new WorkingTimeSimuSCHParams(ui);

            // SCH call.
            // DFKLOOK query()の呼び出しをコメント化
            // List<Params> outparams = sch.query(inparam);
            // message.setMsgResourceKey(sch.getMessage());
            // DFKLOOK ここから追加
            // 分析用設定ファイルから作業者数と、作業開始時刻の前回設定値を取得
            Params outparam = sch.initFind(new ScheduleParams());
            if (outparam != null)
            {
                WarenaviSystemController wmsCtl = new WarenaviSystemController(conn, getClass());

                // 入庫作業
                if (!wmsCtl.hasStoragePack())
                {
                    txt_StorageWorkerNum.setReadOnly(true);
                    txt_StorageWorkStartTime.setReadOnly(true);
                    txt_StorageWorkingTime.setReadOnly(true);
                    txt_StorageWorkEndTime.setReadOnly(true);
                    txt_StorageItemQtyInp.setReadOnly(true);
                    txt_StoragePieceQtyInp.setReadOnly(true);
                }
                else
                {
                    txt_StorageWorkerNum.setText((String)outparam.get(WorkingTimeSimuSCHParams.STORAGE_WORKER_NUM));
                    txt_StorageWorkStartTime.setText((String)outparam.get(WorkingTimeSimuSCHParams.STORAGE_WORK_START_TIME));
                }

                // 出庫作業
                if (!wmsCtl.hasRetrievalPack())
                {
                    txt_RetrievalWorkerNum.setReadOnly(true);
                    txt_RetrievalWorkStartTime.setReadOnly(true);
                    txt_RetrievalWorkingTime.setReadOnly(true);
                    txt_RetrievalWorkEndTime.setReadOnly(true);
                    txt_RetrievalItemQtyInp.setReadOnly(true);
                    txt_RetrievalPieceQtyInp.setReadOnly(true);
                }
                else
                {
                    txt_RetrievalWorkerNum.setText(outparam.getString(WorkingTimeSimuSCHParams.RETRIEVAL_WORKER_NUM));
                    txt_RetrievalWorkStartTime.setText(outparam.getString(WorkingTimeSimuSCHParams.RETRIEVAL_WORK_START_TIME));
                }
            }

            setVisible();
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
     * 検索対象 全て のラジオボタンが押された時の処理です。
     * @throws Exception All the exceptions are reported.
     */
    private void rdo_SearchPlanDateAll_Click_Process()
            throws Exception
    {
        // clear.
        rdo_SearchPlanDateAll.setChecked(true);
        rdo_SearchPlanDateInput.setChecked(false);
        // DFKLOOK ここから追加
        setFocus();
        setVisible();
        // DFKLOOK ここまで追加

    }

    /**
     * 検索対象 指定予定日 のラジオボタンが押された時の処理です。
     * @throws Exception All the exceptions are reported.
     */
    private void rdo_SearchPlanDateInput_Click_Process()
            throws Exception
    {
        // clear.
        rdo_SearchPlanDateAll.setChecked(false);
        rdo_SearchPlanDateInput.setChecked(true);
        // DFKLOOK ここから追加
        setFocus();
        setVisible();
        // DFKLOOK ここまで追加

    }

    /**
     * 表示ボタンが押された時の処理です。
     * @throws Exception All the exceptions are reported.
     */
    private void btn_Select_Click_Process()
            throws Exception
    {
        // DFKLOOK ここから追加
        if (rdo_SearchPlanDateInput.getChecked())
        {
            // DFKLOOK ここまで追加
            // input validation.
            txt_WorkPlanDate.validate(this, true);
        // DFKLOOK 閉じカッコ１行を追加
        }

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        WorkingTimeSimuSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new WorkingTimeSimuSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            WorkingTimeSimuSCHParams inparam = new WorkingTimeSimuSCHParams(ui);
            // DFKLOOK ここから追加
            if (rdo_SearchPlanDateInput.getChecked())
            {
                if (!StringUtil.isBlank(txt_WorkPlanDate.getDate()))
                {
                    // DFKLOOK ここまで追加
                    inparam.set(WorkingTimeSimuSCHParams.WORK_PLAN_DATE, txt_WorkPlanDate.getValue());
                    // DFKLOOK 閉じカッコを追加
                }
                inparam.set(WorkingTimeSimuSCHParams.BEFORE_PLAN_DATE, chk_BeforePlanDate.getValue());
                inparam.set(WorkingTimeSimuSCHParams.AFTER_PLAN_DATE, chk_AfterPlanDate.getValue());
                // DFKLOOK ここから追加
            }
            else
            {
                inparam.set(WorkingTimeSimuSCHParams.WORK_PLAN_DATE, null);
                inparam.set(WorkingTimeSimuSCHParams.BEFORE_PLAN_DATE, false);
                inparam.set(WorkingTimeSimuSCHParams.AFTER_PLAN_DATE, false);
            }
            // DFKLOOK ここまで追加

            // SCH call.
            // DFKLOOK 作業時間予測処理（予定データより商品数、ピース数を取得する）
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK ここから追加
            AnalysisOutParameter anaParam = null;
            if (outparams != null)
            {
                anaParam = (AnalysisOutParameter)outparams.get(0);
            }
            else
            {
                message.setMsgResourceKey(sch.getMessage());
                return;
            }
            
            // 予定商品数・総ピース数と変更後商品数・総ピース数を表示する
            if (!StringUtil.isBlank(txt_StorageWorkerNum.getText()))
            {
                txt_StorageItemQtyPlan.setText(String.valueOf(new Long(anaParam.getStorageItemQty())));
                txt_StorageItemQtyInp.setText(txt_StorageItemQtyPlan.getText());
                txt_StoragePieceQtyPlan.setText(String.valueOf(new Long(anaParam.getStoragePieceQty())));
                txt_StoragePieceQtyInp.setText(txt_StoragePieceQtyPlan.getText());
                txt_StorageWorkingTime.setText("");
                txt_StorageWorkEndTime.setText("");
            }
            if (!StringUtil.isBlank(txt_RetrievalWorkerNum.getText()))
            {
                txt_RetrievalItemQtyPlan.setText(String.valueOf(new Long(anaParam.getRetrievalItemQty())));
                txt_RetrievalItemQtyInp.setText(txt_RetrievalItemQtyPlan.getText());
                txt_RetrievalPieceQtyPlan.setText(String.valueOf(new Long(anaParam.getRetrievalPieceQty())));
                txt_RetrievalPieceQtyInp.setText(txt_RetrievalPieceQtyPlan.getText());
                txt_RetrievalWorkingTime.setText("");
                txt_RetrievalWorkEndTime.setText("");
            }

            // メッセージをセットします。
            String msgNo = sch.getMessage();
            message.setMsgResourceKey(msgNo);

            // 6001013=表示しました。
            if ("6001013".equals(msgNo))
            {
                // 正常にデータを表示した場合は、入庫作業者数にフォーカスを移します。
                setFocus(txt_StorageWorkerNum);
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
     * 予測実行ボタンが押された時の処理です。
     * @throws Exception All the exceptions are reported.
     */
    private void btn_Simulate_Click_Process()
            throws Exception
    {
        // input validation.
        // DFKLOOK ここから追加
        // 入力フィールドの状態を確認
        if (!txt_StorageWorkerNum.getReadOnly())
        {
            // DFKLOOK ここまで追加
            txt_StorageWorkerNum.validate(this, true);
            txt_StorageWorkStartTime.validate(this, true);
        // DFKLOOK ここから追加
        }
        if (!txt_RetrievalWorkerNum.getReadOnly())
        {
            // ここまで追加
            txt_RetrievalWorkerNum.validate(this, true);
            txt_RetrievalWorkStartTime.validate(this, true);
        // DFKLOOK 閉じカッコを追加
        }

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        WorkingTimeSimuSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new WorkingTimeSimuSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();

            // DFKLOOK ここから追加
            WorkingTimeSimuSCHParams inparam = new WorkingTimeSimuSCHParams(ui);
            // 作業者数と作業開始時刻、変更後商品数、ピース数をパラメータへセットする
            inparam.set(WorkingTimeSimuSCHParams.INSTOCK_WORKER_NUM, "1");
            inparam.set(WorkingTimeSimuSCHParams.INSTOCK_WK_START_TIME, "00:00");
            inparam.set(WorkingTimeSimuSCHParams.INSTOCK_ITEM_QTY, 1);
            inparam.set(WorkingTimeSimuSCHParams.INSTOCK_PIECE_QTY, 1);

            if (!StringUtil.isBlank(txt_StorageWorkerNum.getText()))
            {
                inparam.set(WorkingTimeSimuSCHParams.STORAGE_WORKER_NUM, txt_StorageWorkerNum.getText());
                inparam.set(WorkingTimeSimuSCHParams.STORAGE_WORK_START_TIME, txt_StorageWorkStartTime.getText());
                inparam.set(WorkingTimeSimuSCHParams.STORAGE_ITEM_QTY_INP, txt_StorageItemQtyInp.getLong());
                inparam.set(WorkingTimeSimuSCHParams.STORAGE_PIECE_QTY_INP, txt_StoragePieceQtyInp.getLong());
            }

            if (!StringUtil.isBlank(txt_RetrievalWorkerNum.getText()))
            {
                inparam.set(WorkingTimeSimuSCHParams.RETRIEVAL_WORKER_NUM, txt_RetrievalWorkerNum.getText());
                inparam.set(WorkingTimeSimuSCHParams.RETRIEVAL_WORK_START_TIME, txt_RetrievalWorkStartTime.getText());
                inparam.set(WorkingTimeSimuSCHParams.RETRIEVAL_ITEM_QTY_INP, txt_RetrievalItemQtyInp.getLong());
                inparam.set(WorkingTimeSimuSCHParams.RETRIEVAL_PIECE_QTY_INP, txt_RetrievalPieceQtyInp.getLong());
            }

            inparam.set(WorkingTimeSimuSCHParams.SORTING_WORKER_NUM, "1");
            inparam.set(WorkingTimeSimuSCHParams.SORTING_WK_START_TIME, "00:00");
            inparam.set(WorkingTimeSimuSCHParams.SORTING_ITEM_QTY, 1);
            inparam.set(WorkingTimeSimuSCHParams.SORTING_PIECE_QTY, 1);

            inparam.set(WorkingTimeSimuSCHParams.SHIPPING_WORKER_NUM, "1");
            inparam.set(WorkingTimeSimuSCHParams.SHIPPING_WK_START_TIME, "00:00");
            inparam.set(WorkingTimeSimuSCHParams.SHIPPING_ITEM_QTY, 1);
            inparam.set(WorkingTimeSimuSCHParams.SHIPPING_PIECE_QTY, 1);

            // ファイル書き込みを行います。
            if (!sch.startSCH(inparam))
            {
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // 予測結果表示を一旦クリアします。
            txt_StorageWorkingTime.setText("");
            txt_StorageWorkEndTime.setText("");
            txt_RetrievalWorkingTime.setText("");
            txt_RetrievalWorkEndTime.setText("");
            // DFKLOOK ここまで追加

            inparamList.add(inparam);
            // DFKLOOK 作業時間予測処理
            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            List<Params> outparams = sch.startSCHgetParams(inparams);

            if (outparams == null)
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                setFocus(txt_StorageWorkerNum);

                return;
            }

            // DFKLOOK ここから追加
            AnalysisOutParameter anaParam = (AnalysisOutParameter)outparams.get(0);
            // 作業所要時間と作業終了時刻を表示する
            if (!StringUtil.isBlank(txt_StorageWorkerNum.getText()))
            {
                txt_StorageWorkingTime.setText(anaParam.getStorageWorkingTime());
                txt_StorageWorkEndTime.setText(anaParam.getStorageEndTime());
            }
            if (!StringUtil.isBlank(txt_RetrievalWorkerNum.getText()))
            {
                txt_RetrievalWorkingTime.setText(anaParam.getRetrievalWorkingTime());
                txt_RetrievalWorkEndTime.setText(anaParam.getRetrievalEndTime());
            }
            // DFKLOOK ここまで追加

            // DFKLOK DBの更新は無いのでコメントにする
            // // commit.
            // conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK ここからコメント
            // 上の行ですでに表示処理をしているのでここはコメントにする
            // output display.
            // for (Params outparam : outparams)
            // {
            //     txt_StorageWorkingTime.setValue(outparam.get(WorkingTimeSimuSCHParams.STORAGE_WORKING_TIME));
            //     txt_StorageWorkEndTime.setValue(outparam.get(WorkingTimeSimuSCHParams.STORAGE_WORK_END_TIME));
            //     txt_RetrievalWorkingTime.setValue(outparam.get(WorkingTimeSimuSCHParams.RETRIEVAL_WORKING_TIME));
            //     txt_RetrievalWorkEndTime.setValue(outparam.get(WorkingTimeSimuSCHParams.RETRIEVAL_WORK_END_TIME));
            // }
            // DFKLOOK ここまでコメント

            // set focus.
            setFocus(txt_StorageWorkerNum);

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

    /**
     * フォーカスのセットを行います。<BR>
     * 検索対象ラジオボタンの「全て」がチェックされている場合は「全て」に
     * 「指定予定日」がチェックされている場合は「指定予定日」にフォーカスをセットします。
     *
     */
    protected void setFocus()
    {
        if (rdo_SearchPlanDateAll.getChecked())
        {
            setFocus(rdo_SearchPlanDateAll);
        }
        else
        {
            setFocus(txt_WorkPlanDate);
        }
    }

    /**
     * 入力エリアの有効・無効を切り替えます。<BR>
     * 検索対象ラジオボタンの「全て」がチェックされている場合は
     * 入力エリアを使用不可に<BR>
     * 「指定予定日」がチェックされている場合は
     * 入力エリアを使用可能に
     * 変更します。
     *
     */
    protected void setVisible()
    {
        if (rdo_SearchPlanDateAll.getChecked())
        {
            // テキストエリアとチェックボックスをクリアします。
            txt_WorkPlanDate.setText("");
            chk_BeforePlanDate.setChecked(false);
            chk_AfterPlanDate.setChecked(false);

            txt_WorkPlanDate.setReadOnly(true);
            chk_BeforePlanDate.setEnabled(false);
            chk_AfterPlanDate.setEnabled(false);
        }
        else
        {
            txt_WorkPlanDate.setReadOnly(false);
            chk_BeforePlanDate.setEnabled(true);
            chk_AfterPlanDate.setEnabled(true);
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
