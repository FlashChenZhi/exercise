// $Id: AreaModify2Business.java 7541 2010-03-13 12:08:20Z kishimoto $
package jp.co.daifuku.wms.master.display.areamodify;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
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
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.master.schedule.AreaModifySCH;
import jp.co.daifuku.wms.master.schedule.AreaModifySCHParams;
import jp.co.daifuku.wms.master.schedule.MasterInParameter;

/**
 * エリアマスタ修正・削除（詳細情報）の画面処理を行います。
 *
 * @version $Revision: 7541 $, $Date: 2010-03-13 21:08:20 +0900 (土, 13 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class AreaModify2Business
        extends AreaModify2
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_TemporaryArea */
    private WmsAreaPullDownModel _pdm_pul_TemporaryArea;

    /** PullDownModel pul_VacantSearchType */
    private DefaultPullDownModel _pdm_pul_VacantSearchType;

    /** PullDownModel pul_ReceivingArea */
    private WmsAreaPullDownModel _pdm_pul_ReceivingArea;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AreaModify2Business()
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
        if (eventSource.startsWith("btn_Modify_Click"))
        {
            // process call.
            btn_Modify_Click_Process(eventSource);
        }
        else if (eventSource.startsWith("btn_Delete_Click"))
        {
            // process call.
            btn_Delete_Click_Process(eventSource);
        }
    }

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
    public void chk_MoveTemporaryStorage_Change(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // チェックボックスの変更イベント追加
        // チェックオン状態の場合
        if (chk_MoveTemporaryStorage.getChecked())
        {
            // 「移動先仮置エリア」プルダウンを有効化
            pul_TemporaryArea.setEnabled(true);
        }

        // チェックオフ状態
        if (!chk_MoveTemporaryStorage.getChecked())
        {
            // 「移動先仮置エリア」プルダウンを無効化
            pul_TemporaryArea.setEnabled(false);
        }
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void chk_ReceivingArea_Change(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // チェックボックスの変更イベント追加
        // チェックオン状態の場合
        if (chk_ReceivingArea.getChecked())
        {
            // 「入荷エリア」プルダウンを有効化
            pul_ReceivingArea.setEnabled(true);
        }

        // チェックオフ状態
        if (!chk_ReceivingArea.getChecked())
        {
            // 「入荷エリア」プルダウンを無効化
            pul_ReceivingArea.setEnabled(false);
        }
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Modify_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Modify_Click_Process(null);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Delete_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Delete_Click_Process(null);
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
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_TemporaryArea.
        _pdm_pul_TemporaryArea = new WmsAreaPullDownModel(pul_TemporaryArea, locale, ui);

        // initialize pul_VacantSearchType.
        _pdm_pul_VacantSearchType = new DefaultPullDownModel(pul_VacantSearchType, locale, ui);

        // initialize pul_ReceivingArea.
        _pdm_pul_ReceivingArea = new WmsAreaPullDownModel(pul_ReceivingArea, locale, ui);

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

            // load pul_TemporaryArea.
            _pdm_pul_TemporaryArea.init(conn, AreaType.TEMP, StationType.ALL, "", false);

            // load pul_VacantSearchType.
            //DFKLOOK:ここから修正
            //_pdm_pul_VacantSearchType.init(conn);
            //DFKLOOK:ここまで修正

            // load pul_ReceivingArea.
            _pdm_pul_ReceivingArea.init(conn, AreaType.RECEIVE, StationType.ALL, "", false);

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
        setFocus(txt_AreaName);

    }

    /**
     *  DFKLOOK:ここから修正
     *  page_Load_Process()
     *  
     *  注意:「エリア種別」と「移動先仮置エリア／入荷エリア」の関係について
     *        平置エリア：　移動先仮置エリア有効、入荷エリア有効
     *        入荷エリア：　移動先仮置エリア有効、入荷エリア無効  
     *        仮置エリア：　移動先仮置エリア有効、入荷エリア有効
     *  DFKLOOK:ここまで修正
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
        AreaModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AreaModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AreaModifySCHParams inparam = new AreaModifySCHParams();
            inparam.set(AreaModifySCHParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA_NO));

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_AreaName.setValue(outparam.get(AreaModifySCHParams.AREA_NAME));
                _pdm_pul_TemporaryArea.setSelectedValue(outparam.get(AreaModifySCHParams.TEMPORARY_AREA));
                _pdm_pul_VacantSearchType.setSelectedValue(outparam.get(AreaModifySCHParams.VACANT_SEARCH_TYPE));
                _pdm_pul_ReceivingArea.setSelectedValue(outparam.get(AreaModifySCHParams.RECEIVING_AREA));
                lbl_InArea.setValue(outparam.get(AreaModifySCHParams.AREA_NO));
                viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, outparam.get(AreaModifySCHParams.LAST_UPDATE_DATE));
                lbl_InLocationStyle.setValue(outparam.get(AreaModifySCHParams.LOCATION_STYLE));
			
                // DFKLOOK:ここから修正
                // データベースの値ではなく名称を表示したいため手修正
                // エリア種別
                if (SystemDefine.AREA_TYPE_FLOOR.equals(outparam.get(AreaModifySCHParams.AREA_TYPE)))
                {
                    // 平置エリア
                    lbl_InAreaType.setText(DisplayText.getText("CMB-W0041"));
                }
                else if (SystemDefine.AREA_TYPE_TEMPORARY.equals(outparam.get(AreaModifySCHParams.AREA_TYPE)))
                {
                    // 仮置エリア
                    lbl_InAreaType.setText(DisplayText.getText("CMB-W0042"));
                }
                else if (SystemDefine.AREA_TYPE_RECEIVING.equals(outparam.get(AreaModifySCHParams.AREA_TYPE)))
                {
                    // 入荷エリア
                    lbl_InAreaType.setText(DisplayText.getText("CMB-W0052"));
                }           
                // ラベルの表示取得 
                // 棚管理方式
                if (SystemDefine.LOCATION_TYPE_FREE.equals(outparam.get(AreaModifySCHParams.LOCATION_TYPE)))
                {
                    // フリー管理
                    lbl_InLocationType.setText(DisplayText.getText("CMB-W0043"));
                }
                else if (SystemDefine.LOCATION_TYPE_MASTER.equals(outparam.get(AreaModifySCHParams.LOCATION_TYPE)))
                {
                    // 棚マスタ管理
                    lbl_InLocationType.setText(DisplayText.getText("CMB-W0044"));
                }
                else if (SystemDefine.LOCATION_TYPE_FIXED.equals(outparam.get(AreaModifySCHParams.LOCATION_TYPE)))
                {
                    // 固定棚管理
                    lbl_InLocationType.setText(DisplayText.getText("CMB-W0045"));
                }
                
                // プルダウンのロジックとデータ表示                
                // エリア種別が入荷の場合(SystemDefineのimportも必要)
                if (SystemDefine.AREA_TYPE_RECEIVING.equals(outparam.get(AreaModifySCHParams.AREA_TYPE)))
                {             	
                    // 「入荷エリア」プルダウンを無効化
                    pul_ReceivingArea.setEnabled(false);
                    // 入荷エリア定義ありチェックを使用不可とする
                    chk_ReceivingArea.setEnabled(false);
                    // 入荷エリア定義ありチェックを外す
                    chk_ReceivingArea.setChecked(false);
                }
                else
                {
                    // 入荷エリア定義ありチェックを使用可とする
                    if (pul_ReceivingArea.getItems().size() == 0)
                    {
                        // 「入荷エリア」プルダウンを無効化
                        pul_ReceivingArea.setEnabled(false);
                        // 入荷エリア定義ありチェックを使用不可とする
                        chk_ReceivingArea.setEnabled(false);
                        // 入荷エリア定義ありチェックを外す
                        chk_ReceivingArea.setChecked(false);
                    }
                    else
                    {
                        if(StringUtil.isBlank(outparam.getString(AreaModifySCHParams.RECEIVING_AREA)))
                        {
                        	pul_ReceivingArea.setSelectedIndex(0);
                            // 「入荷エリア」プルダウンを無効化       
                            pul_ReceivingArea.setEnabled(false);
                            // 入荷エリア定義ありチェックを外す
                            chk_ReceivingArea.setChecked(false);
                        }
                        else
                        {
                            // 「入荷エリア」プルダウンを無効化       
                            pul_ReceivingArea.setEnabled(true);
                            // 入荷エリア定義ありチェックを外す
                            chk_ReceivingArea.setChecked(true);
                        }
                    	// プルダウンチェックボックスを有効化する
                        chk_ReceivingArea.setEnabled(true);
                    }

                }
                // 移動先仮置エリア定義ありチェックを使用可とする
                if (pul_TemporaryArea.getItems().size() == 0)
                {
                    // 「移動先仮置きエリア」プルダウンを無効化
                	pul_TemporaryArea.setEnabled(false);
                    // 移動先仮置きエリア定義ありチェックを使用不可とする
                	chk_MoveTemporaryStorage.setEnabled(false);
                    // 移動先仮置きエリア定義ありチェックを外す
                	chk_MoveTemporaryStorage.setChecked(false);
                }
                else
                {
                    if(StringUtil.isBlank(outparam.getString(AreaModifySCHParams.TEMPORARY_AREA)))
                    {
                    	pul_TemporaryArea.setSelectedIndex(0);
                        // 「移動先仮置エリア」プルダウンを無効化       
                        pul_TemporaryArea.setEnabled(false);
                        // 移動先仮置エリア定義ありチェックを外す
                        chk_MoveTemporaryStorage.setChecked(false);
                    }
                    else
                    {
                        // 「移動先仮置エリア」プルダウンを有効化       
                        pul_TemporaryArea.setEnabled(true);
                        // 移動先仮置エリア定義ありチェックをつける
                        chk_MoveTemporaryStorage.setChecked(true);
                    }
                	// プルダウンチェックボックスを有効化する
                    chk_MoveTemporaryStorage.setEnabled(true);
                }
                // DFKLOOK:ここまで修正
                viewState.setObject(ViewStateKeys.VS_AREA_NAME, outparam.get(AreaModifySCHParams.AREA_NAME));
                viewState.setObject(ViewStateKeys.VS_TEMPORARY_AREA, outparam.get(AreaModifySCHParams.TEMPORARY_AREA));
                viewState.setObject(ViewStateKeys.VS_VACANT_SEARCH_TYPE, outparam.get(AreaModifySCHParams.VACANT_SEARCH_TYPE));
                viewState.setObject(ViewStateKeys.VS_RECEIVING_AREA, outparam.get(AreaModifySCHParams.RECEIVING_AREA));
                viewState.setObject(ViewStateKeys.VS_AREA_TYPE, outparam.get(AreaModifySCHParams.AREA_TYPE));
                viewState.setObject(ViewStateKeys.VS_LOCATION_TYPE, outparam.get(AreaModifySCHParams.LOCATION_TYPE));
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
    private void btn_Back_Click_Process()
            throws Exception
    {
        try
        {
            // forward.
            forward("/master/areamodify/AreaModify.do");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    // DFKLOOK 引数追加
    private void btn_Modify_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        txt_AreaName.validate(this, true);

        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
        	// 修正登録しますか?
            this.setConfirm("MSG-W0013", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_Modify_Click");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AreaModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AreaModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AreaModifySCHParams inparam = new AreaModifySCHParams();
            inparam.setProcessFlag(ProcessFlag.UPDATE);
            inparam.set(AreaModifySCHParams.AREA_NAME, txt_AreaName.getValue());
            inparam.set(AreaModifySCHParams.MOVE_TEMPORARY_STORAGE, chk_MoveTemporaryStorage.getValue());
            inparam.set(AreaModifySCHParams.VACANT_SEARCH_TYPE, _pdm_pul_VacantSearchType.getSelectedValue());

            // DFKLOOK:ここから修正
            if (chk_MoveTemporaryStorage.getChecked())
            {
            	// 選ばれた移動先仮置エリアが修正している仮置エリアと同じ場合
            	if( viewState.getObject(ViewStateKeys.AREA_NO).equals(_pdm_pul_TemporaryArea.getSelectedValue()) )
            	{
            		message.setMsgResourceKey("6023166");
            		return;
            	}
                inparam.set(AreaModifySCHParams.TEMPORARY_AREA, _pdm_pul_TemporaryArea.getSelectedValue());
            }
            if (chk_ReceivingArea.getChecked())
            {
                inparam.set(AreaModifySCHParams.RECEIVING_AREA, _pdm_pul_ReceivingArea.getSelectedValue());
            }
            // DFKLOOK:ここまで修正

            inparam.set(AreaModifySCHParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA_NO));
            inparam.set(AreaModifySCHParams.LAST_UPDATE_DATE, viewState.getObject(ViewStateKeys.LAST_UPDATE_DATE));
            inparam.set(AreaModifySCHParams.PROCESS_FLAG, MasterInParameter.PROCESS_FLAG_MODIFY);

            // DFKLOOK start
            // SCH call.
            if (eventSource.equals("btn_Modify_Click") && !sch.check(inparam))
            {
            	// DFKLOOK end
                if (StringUtil.isBlank(sch.getDispMessage()))
                {
                    // show message.
                    message.setMsgResourceKey(sch.getMessage());
                    return;
                }
                else
                {
                    // show confirm message.
                    this.setConfirm(sch.getDispMessage(), false, true);
                    // DFKLOOK start
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Modify_Click_SCH");
                    // DFKLOOK end
                    return;
                }
            }

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
            part11List.add(lbl_InArea.getStringValue(), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_AREA_TYPE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_LOCATION_TYPE), "");
            part11List.add(lbl_InLocationStyle.getStringValue(), "");
            part11List.add(txt_AreaName.getStringValue(), "");

            if (chk_MoveTemporaryStorage.getChecked())
            {
                part11List.add(_pdm_pul_TemporaryArea.getSelectedStringValue(), "");
                part11List.add("1", "");
            }
            else
            {
                part11List.add("", "");
                part11List.add("0", "");
            }

            part11List.add(_pdm_pul_VacantSearchType.getSelectedStringValue(), "");

            if (chk_ReceivingArea.getChecked())
            {
                part11List.add(_pdm_pul_ReceivingArea.getSelectedStringValue(), "");
                part11List.add("1", "");
            }
            else
            {
                part11List.add("", "");
                part11List.add("0", "");
            }

            part11List.add(viewState.getString(ViewStateKeys.VS_AREA_NAME), "");
            // DFKLOOK start
            part11List.add(viewState.getString(ViewStateKeys.VS_TEMPORARY_AREA), "");
            if (!StringUtil.isBlank(viewState.getString(ViewStateKeys.VS_TEMPORARY_AREA)))
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }
            part11List.add(viewState.getString(ViewStateKeys.VS_VACANT_SEARCH_TYPE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_RECEIVING_AREA), "");
            if (!StringUtil.isBlank(viewState.getString(ViewStateKeys.VS_RECEIVING_AREA)))
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }
            // DFKLOOK end
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_MODIFY), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // set input parameters.
            inparam = new AreaModifySCHParams();
            inparam.set(AreaModifySCHParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA_NO));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                txt_AreaName.setValue(outparam.get(AreaModifySCHParams.AREA_NAME));
                _pdm_pul_TemporaryArea.setSelectedValue(outparam.get(AreaModifySCHParams.TEMPORARY_AREA));
                _pdm_pul_VacantSearchType.setSelectedValue(outparam.get(AreaModifySCHParams.VACANT_SEARCH_TYPE));
                _pdm_pul_ReceivingArea.setSelectedValue(outparam.get(AreaModifySCHParams.RECEIVING_AREA));
                viewState.setObject(ViewStateKeys.AREA_NO, outparam.get(AreaModifySCHParams.AREA_NO));
                viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, outparam.get(AreaModifySCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.VS_AREA_NAME, outparam.get(AreaModifySCHParams.AREA_NAME));
                viewState.setObject(ViewStateKeys.VS_TEMPORARY_AREA, outparam.get(AreaModifySCHParams.TEMPORARY_AREA));
                viewState.setObject(ViewStateKeys.VS_VACANT_SEARCH_TYPE, outparam.get(AreaModifySCHParams.VACANT_SEARCH_TYPE));
                viewState.setObject(ViewStateKeys.VS_RECEIVING_AREA, outparam.get(AreaModifySCHParams.RECEIVING_AREA));

                // DFKLOOK:ここから修正
                // データベースの値ではなく名称を表示したいため手修正
                // エリア種別
                if (SystemDefine.AREA_TYPE_FLOOR.equals(outparam.get(AreaModifySCHParams.AREA_TYPE)))
                {
                    // 平置エリア
                    lbl_InAreaType.setText(DisplayText.getText("CMB-W0041"));
                }
                else if (SystemDefine.AREA_TYPE_TEMPORARY.equals(outparam.get(AreaModifySCHParams.AREA_TYPE)))
                {
                    // 仮置エリア
                    lbl_InAreaType.setText(DisplayText.getText("CMB-W0042"));
                }
                else if (SystemDefine.AREA_TYPE_RECEIVING.equals(outparam.get(AreaModifySCHParams.AREA_TYPE)))
                {
                    // 入荷エリア
                    lbl_InAreaType.setText(DisplayText.getText("CMB-W0052"));
                    // 入荷エリア使用不可
                    pul_ReceivingArea.setEnabled(false);
                    // 入荷エリア定義ありチェックを使用不可とする
                    chk_ReceivingArea.setEnabled(false);
                }

                // 棚管理方式
                if (SystemDefine.LOCATION_TYPE_FREE.equals(outparam.get(AreaModifySCHParams.LOCATION_TYPE)))
                {
                    // フリー管理
                    lbl_InLocationType.setText(DisplayText.getText("CMB-W0043"));
                }
                else if (SystemDefine.LOCATION_TYPE_MASTER.equals(outparam.get(AreaModifySCHParams.LOCATION_TYPE)))
                {
                    // 棚マスタ管理
                    lbl_InLocationType.setText(DisplayText.getText("CMB-W0044"));
                }
                else if (SystemDefine.LOCATION_TYPE_FIXED.equals(outparam.get(AreaModifySCHParams.LOCATION_TYPE)))
                {
                    // 固定棚管理
                    lbl_InLocationType.setText(DisplayText.getText("CMB-W0045"));
                }

                // 移動先仮置エリアデータが存在しない場合
                if (pul_TemporaryArea.getItems().size() == 0)
                {
                    // プルダウン無効
                    pul_TemporaryArea.setEnabled(false);
                    // チェックボックス無効
                    chk_MoveTemporaryStorage.setEnabled(false);
                }
                else
                {
                    // 移動先仮置エリアのチェックボックス
                    if (StringUtil.isBlank(outparam.getString(AreaModifySCHParams.TEMPORARY_AREA)))
                    {
                        // チェックなし
                        chk_MoveTemporaryStorage.setChecked(false);
                        // プルダウン無効
                        pul_TemporaryArea.setEnabled(false);
                    }
                    else
                    {
                        // チェックあり
                        chk_MoveTemporaryStorage.setChecked(true);
                        // プルダウン有効
                        pul_TemporaryArea.setEnabled(true);
                    }
                }

                // 入荷エリアデータが存在しない場合
                if (pul_ReceivingArea.getItems().size() == 0)
                {
                    // プルダウン無効
                    pul_ReceivingArea.setEnabled(false);
                    // チェックボックス無効
                    chk_ReceivingArea.setEnabled(false);
                }
                else
                {
                    // 入荷エリアのチェックボックス
                    if (StringUtil.isBlank(outparam.getString(AreaModifySCHParams.RECEIVING_AREA)))
                    {
                        // プルダウン無効
                        pul_ReceivingArea.setEnabled(false);
                        // チェックなし
                        chk_ReceivingArea.setChecked(false);
                    }
                    else
                    {
                        // プルダウン有効
                        pul_ReceivingArea.setEnabled(true);
                        // チェックボックス有効
                        chk_ReceivingArea.setEnabled(true);
                        // チェックあり
                        chk_ReceivingArea.setChecked(true);
                    }
                }
                // DFKLOOK:ここまで修正
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
     * @param confirm
     * @throws Exception
     */
    private void btn_Delete_Click_Process(String eventSource)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AreaModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AreaModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AreaModifySCHParams inparam = new AreaModifySCHParams();
            inparam.setProcessFlag(ProcessFlag.DELETE);
            inparam.set(AreaModifySCHParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA_NO));
            inparam.set(AreaModifySCHParams.LAST_UPDATE_DATE, viewState.getObject(ViewStateKeys.LAST_UPDATE_DATE));
            inparam.set(AreaModifySCHParams.PROCESS_FLAG, MasterInParameter.PROCESS_FLAG_DELETE);

            // DFKLOOK start
            // SCH call.
            if (StringUtil.isBlank(eventSource) && !sch.check(inparam))
            {
            	// DFKLOOK end
                if (StringUtil.isBlank(sch.getDispMessage()))
                {
                    // show message.
                    message.setMsgResourceKey(sch.getMessage());
                    return;
                }
                else
                {
                    // show confirm message.
                    this.setConfirm(sch.getDispMessage(), false, true);
                    // DFKLOOK start
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Delete_Click");
                    // DFKLOOK end
                    return;
                }
            }

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
            part11List.add(lbl_InArea.getStringValue(), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_AREA_TYPE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_LOCATION_TYPE), "");
            part11List.add(lbl_InLocationStyle.getStringValue(), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_AREA_NAME), "");

            // DFKLOOK start
            part11List.add(viewState.getString(ViewStateKeys.VS_TEMPORARY_AREA), "");
            if (!StringUtil.isBlank(viewState.getString(ViewStateKeys.VS_TEMPORARY_AREA)))
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }
            part11List.add(viewState.getString(ViewStateKeys.VS_VACANT_SEARCH_TYPE), "");
            part11List.add(viewState.getString(ViewStateKeys.VS_RECEIVING_AREA), "");
            if (!StringUtil.isBlank(viewState.getString(ViewStateKeys.VS_RECEIVING_AREA)))
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }
            // DFKLOOK end

            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_DELETE), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // clear.
            txt_AreaName.setValue(null);
            _pdm_pul_TemporaryArea.setSelectedValue(null);
            chk_MoveTemporaryStorage.setChecked(false);
            _pdm_pul_VacantSearchType.setSelectedValue(null);
            _pdm_pul_ReceivingArea.setSelectedValue(null);
            chk_ReceivingArea.setChecked(false);
            btn_Modify.setEnabled(false);
            btn_Delete.setEnabled(false);
            btn_Clear.setEnabled(false);

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
    	// DFKLOOK:ここから修正  
        page_Load_Process();
        // DFKLOOK:ここまで修正
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
