// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.asrs.display.faworkdisplay;

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
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.Constants;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;
import jp.co.daifuku.wms.asrs.schedule.FaWorkDisplaySCH;
import jp.co.daifuku.wms.asrs.schedule.FaWorkDisplaySCHParams;
import jp.co.daifuku.wms.base.controller.PulldownController.Distribution;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;

/**
 * AS/RS 作業表示(FA)の画面処理を行います。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class FaWorkDisplayBusiness
        extends FaWorkDisplay
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";
    
    // DFKLOOK:ここから修正
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK:ここまで修正

	// DFKLOOK start
    /** key */
    private static final String _KEY_CURRENT = "_KEY_CURRENT";

    /** key */
    private static final String _KEY_COMPLATE = "_KEY_COMPLATE";
    
    /** key */
    private static final String _KEY_WORK_NO = "_KEY_WORK_NO";

    private static final int _NO_DATA = -1;

    private static final int _START_INDEX = 0;
    // DFKLOOK end
    
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Station */
    private WmsStationPullDownModel _pdm_pul_Station;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaWorkDisplayBusiness()
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
        if (eventSource.startsWith("btn_Complete_Click"))
        {
            // process call.
            btn_Complete_Click_Process(eventSource);
        }
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_ClientPull(ActionEvent e)
            throws Exception
    {
       // DFKLOOK ここから修正
	   btn_ReDisplay_Click_Process();
	   // DFKLOOK ここまで修正
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
    public void btn_Complete_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK start
        // process call.
        btn_Complete_Click_Process(null);
        // DFKLOOK end
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PrevWork_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PrevWork_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_NextWork_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_NextWork_Click_Process();
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

        // initialize pul_Station.
        _pdm_pul_Station = new WmsStationPullDownModel(pul_Station, locale, ui);

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

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.OPERATION_DISPLAY, Distribution.UNUSE);

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
        setFocus(pul_Station);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
    	// DFKLOOK start
    	// 初期化
    	viewState.setInt(_KEY_CURRENT, _NO_DATA);
    	viewState.setObject(_KEY_COMPLATE, null);
        viewState.setObject(_KEY_WORK_NO, null);
    	viewState.setObject(ViewStateKeys.VS_STATION_NO, null);
        _pdm_pul_Station.setSelectedIndex(0);
        pul_Station.validate();
    	viewState.setObject(ViewStateKeys.VS_STATION_NO, _pdm_pul_Station.getSelectedValue());
        // DFKLOOK end
        btn_Complete.setEnabled(false);
        btn_PrevWork.setEnabled(false);
        btn_NextWork.setEnabled(false);
        txt_RestWork.setReadOnly(true);
        txt_WorkNo.setReadOnly(true);
        txt_Location.setReadOnly(true);
        txt_CarryingFlag.setReadOnly(true);
        txt_InstructionDetail.setReadOnly(true);
        txt_ItemCode.setReadOnly(true);
        txt_ItemName.setReadOnly(true);
        txt_LotNo.setReadOnly(true);
        txt_StockQty.setReadOnly(true);
        txt_WorkQty.setReadOnly(true);
        txt_StorageDate.setReadOnly(true);
        txt_StorageTime.setReadOnly(true);
        txt_SlipNumber.setReadOnly(true);
        txt_BatchNo.setReadOnly(true);
        txt_LineNo.setReadOnly(true);
    	
    	// DFKLOOK start
        setDisplay(true);
        // DFKLOOK end
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ReDisplay_Click_Process()
            throws Exception
    {
        // input validation.
        pul_Station.validate(this,true);

        // DFKLOOK start
        // 前回表示したステーションと違うときは保持しているデータをクリア
        if(!_pdm_pul_Station.getSelectedValue().equals(viewState.getObject(ViewStateKeys.VS_STATION_NO)))
		{
        	viewState.setInt(_KEY_CURRENT, _NO_DATA);
        	viewState.setObject(_KEY_COMPLATE, null);
            viewState.setObject(_KEY_WORK_NO, null);
        	viewState.setObject(ViewStateKeys.VS_STATION_NO,_pdm_pul_Station.getSelectedValue());
		}

        setDisplay(true);
        // DFKLOOK end
    }

    /**
     *
     * @throws Exception
     */
    // DFKLOOK start
    private void btn_Complete_Click_Process(String eventSource)
            throws Exception
    {
    	// プルダウンは保持中のものに戻す。
    	_pdm_pul_Station.setSelectedValue(viewState.getObject(ViewStateKeys.VS_STATION_NO));

        if (StringUtil.isBlank(eventSource))
        {
            // show confirm message. 完了しますか？
            this.setConfirm("MSG-W0043", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Complete_Click");
            return;
        }
    	
		// 完了した作業のフラグを更新
        List<Boolean> compList = new ArrayList<Boolean>();
        compList = (ArrayList<Boolean>)viewState.getObject(_KEY_COMPLATE);
        compList.set(viewState.getInt(_KEY_CURRENT), true);

        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Connection conn = null;

        try
        {
	    	// get locale.
        	conn = ConnectionManager.getRequestConnection(this);
	        Locale locale = httpRequest.getLocale();
	
	        // 次の作業があれば_currentを更新して次の作業を表示
	    	if(getNextIndex() != _NO_DATA)
	    	{
            	// 操作ログを残す
            	operationLogWrite(conn, ui);
	            conn.commit();
            	
	    		viewState.setInt(_KEY_CURRENT, getNextIndex());
	    		setDisplay(false);
	    	}
	        // 次の作業が無く、前の作業があれば_currentを更新して前の作業を表示
	    	else if (getPrevIndex() != _NO_DATA)
	    	{
            	// 操作ログを残す
            	operationLogWrite(conn, ui);
	            conn.commit();
            	
	    		viewState.setInt(_KEY_CURRENT, getPrevIndex());
	    		setDisplay(false);
	    	}
	    	// 全部終わっている場合、完了処理
	    	else
	    	{
		        FaWorkDisplaySCH sch = null;
	
		        // open connection.
	            sch = new FaWorkDisplaySCH(conn, this.getClass(), locale, ui);
	
	            // set input parameters.
	            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
				
            	FaWorkDisplaySCHParams param = new FaWorkDisplaySCHParams();
	            param.set(FaWorkDisplaySCHParams.CARRY_KEY,viewState.getObject(ViewStateKeys.VS_CARRY_KEY));
	            param.set(FaWorkDisplaySCHParams.STATION_NO,viewState.getObject(ViewStateKeys.VS_STATION_NO));
	            inparamList.add(param);
	            // DFKLOOK end
	            
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
	
            	// 操作ログを残す
            	operationLogWrite(conn, ui);

	            // commit.
	            conn.commit();
	            
                // DFKLOOK start
                // 入庫指示送信を起動します。
                SendRequestor req = new SendRequestor();
                req.storage();
                
	            message.setMsgResourceKey(sch.getMessage());
	            // 初期化
	        	viewState.setInt(_KEY_CURRENT, _NO_DATA);
	        	viewState.setObject(_KEY_COMPLATE, null);
                viewState.setObject(_KEY_WORK_NO, null);

	            setDisplay(false);
	            // DFKLOOK end
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
    private void btn_PrevWork_Click_Process()
            throws Exception
    {
    	// DFKLOOK start
    	// 現在位置を前データに更新
    	viewState.setInt(_KEY_CURRENT, getPrevIndex());

    	setDisplay(true);
        // DFKLOOK end
    }

    /**
     *
     * @throws Exception
     */
    private void btn_NextWork_Click_Process()
            throws Exception
    {
    	// DFKLOOK start
    	// 現在位置を次データに更新
    	viewState.setInt(_KEY_CURRENT, getNextIndex());
    	
    	setDisplay(true);
    	// DFKLOOK end
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

    // DFKLOOK start メソッド追加
    /**
     * DB検索をして画面に値をセットします
     * 
     * @param dispMsg
     * @throws ValidateException 
     */
    private void setDisplay(boolean dispMsg) throws ValidateException
	{
        // 初期化
        btn_Complete.setEnabled(false);
        btn_NextWork.setEnabled(false);
        btn_PrevWork.setEnabled(false);
        txt_RestWork.setValue(null);
        txt_WorkNo.setValue(null);
        txt_Location.setValue(null);
        txt_CarryingFlag.setValue(null);
        txt_InstructionDetail.setValue(null);
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        txt_LotNo.setValue(null);
        txt_StockQty.setValue(null);
        txt_WorkQty.setValue(null);
        txt_StorageDate.setValue(null);
        txt_StorageTime.setValue(null);
        txt_SlipNumber.setValue(null);
        txt_BatchNo.setValue(null);
        txt_LineNo.setValue(null);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaWorkDisplaySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaWorkDisplaySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaWorkDisplaySCHParams inparam = new FaWorkDisplaySCHParams();
            inparam.set(FaWorkDisplaySCHParams.STATION_NO, viewState.getObject(ViewStateKeys.VS_STATION_NO));
            // 取得データ位置
            int current = viewState.getInt(_KEY_CURRENT);
            if(current == _NO_DATA)
            {
            	current++;
            }
            inparam.set(FaWorkDisplaySCHParams.CURRENT, current);
            inparam.set(FaWorkDisplaySCHParams.WORK_NO, viewState.getObject(_KEY_WORK_NO));

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            
            if(dispMsg)
            {
            	// 再表示・初期表示・前作業・次作業が押された場合
            	message.setMsgResourceKey(sch.getMessage());
            }

            if(outparams.size() != 0)
            {
	            // 必要な１件だけを取得
	            Params outparam  = outparams.get(0);
                // 取得したデータの作業No.が前回と同様かチェック
                String work_no = viewState.getString(_KEY_WORK_NO);
            	if(StringUtil.isBlank(work_no) || !outparam.get(FaWorkDisplaySCHParams.WORK_NO).equals(work_no))
            	{
            		// 初期位置をセット
            		viewState.setInt(_KEY_CURRENT, _START_INDEX);
    	            
            		// 完了フラグを保持するリストの作成
    	            List<Boolean> list = new ArrayList<Boolean>();
    	            for (int i = 0; i < outparam.getInt(FaWorkDisplaySCHParams.SIZE); i++)
    	            {
    	            	list.add(false);
    	            }
    	            viewState.setObject(_KEY_COMPLATE, list);
                    
                    viewState.setObject(_KEY_WORK_NO, outparam.get(FaWorkDisplaySCHParams.WORK_NO));
            	}

	            // output display.
	            txt_RestWork.setValue(getRestWorkQty());
	            txt_WorkNo.setValue(outparam.get(FaWorkDisplaySCHParams.WORK_NO));
	            txt_Location.setValue(outparam.get(FaWorkDisplaySCHParams.LOCATION_NO));
	            txt_CarryingFlag.setValue(outparam.get(FaWorkDisplaySCHParams.CARRY_FLAG_NAME));
	            txt_InstructionDetail.setValue(outparam.get(FaWorkDisplaySCHParams.RETRIEVAL_DETAIL_NAME));
	            txt_ItemCode.setValue(outparam.get(FaWorkDisplaySCHParams.ITEM_CODE));
	            txt_ItemName.setValue(outparam.get(FaWorkDisplaySCHParams.ITEM_NAME));
	            txt_LotNo.setValue(outparam.get(FaWorkDisplaySCHParams.LOT_NO));
	            txt_StockQty.setValue(outparam.get(FaWorkDisplaySCHParams.STOCK_QTY));
	            txt_WorkQty.setValue(outparam.get(FaWorkDisplaySCHParams.WORK_QTY));
	            txt_StorageDate.setValue(outparam.get(FaWorkDisplaySCHParams.STORAGE_DATE));
	            txt_StorageTime.setValue(outparam.get(FaWorkDisplaySCHParams.STORAGE_TIME));
	            txt_BatchNo.setValue(outparam.get(FaWorkDisplaySCHParams.BATCH_NO));
	            txt_SlipNumber.setValue(outparam.get(FaWorkDisplaySCHParams.TICKET_NO));
	            txt_LineNo.setValue(outparam.get(FaWorkDisplaySCHParams.LINE_NO));
	            viewState.setObject(ViewStateKeys.VS_CARRY_KEY, outparam.get(FaWorkDisplaySCHParams.CARRY_KEY));
	            viewState.setObject(ViewStateKeys.VS_LAST_UPDATE_DATE, outparam.get(FaWorkDisplaySCHParams.LAST_UPDATE_DATE));
	            viewState.setObject(ViewStateKeys.VS_RETRIEVAL_DETAIL, outparam.get(FaWorkDisplaySCHParams.RETRIEVAL_DETAIL));
	            viewState.setObject(ViewStateKeys.VS_STATION_NO, _pdm_pul_Station.getSelectedValue());
	            viewState.setObject(ViewStateKeys.VS_WAREHOUSE_NO, outparam.get(FaWorkDisplaySCHParams.WAREHOUSE_NO));
	            viewState.setObject(ViewStateKeys.VS_WORK_DISPLAY_OPERATE, outparam.get(FaWorkDisplaySCHParams.WORK_DISPLAY_OPERATE));
	            viewState.setObject(ViewStateKeys.VS_WORK_TYPE, outparam.get(FaWorkDisplaySCHParams.WORK_TYPE));
	            viewState.setObject(ViewStateKeys.VS_CARRY_FLAG, outparam.get(FaWorkDisplaySCHParams.CARRY_FLAG));
	            
	            // ボタンを表示
	            if(outparam.get(FaWorkDisplaySCHParams.WORK_DISPLAY_OPERATE).equals(SystemDefine.OPERATION_DISPLAY_INSTRUCTION))
	            {
		            btn_Complete.setEnabled(true);
	            }		
	        	btn_NextWork.setEnabled(getNextIndex() != _NO_DATA);
	        	btn_PrevWork.setEnabled(getPrevIndex() != _NO_DATA);
            }
            else
            {
                // 対象データがない場合はviewState初期化
                viewState.setInt(_KEY_CURRENT, _NO_DATA);
                viewState.setObject(_KEY_COMPLATE, null);
                viewState.setObject(_KEY_WORK_NO, null);
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
     * 前データがある場所を返します。無い場合は-1を返します。
     *
     * @return 前データがある場所
     */
    private int getPrevIndex()
	{
        if(0 < viewState.getInt(_KEY_CURRENT))
        {
            List<Boolean> list = (ArrayList<Boolean>)viewState.getObject(_KEY_COMPLATE);

             for (int i = viewState.getInt(_KEY_CURRENT) - 1; i > _NO_DATA; i--)
            {
            	if(!list.get(i))
        		{
                	// まだ完了していないデータが今の位置より前にあればその場所を返却
            		return i;
        		}
            }
        }
    	return _NO_DATA;
	}

    /**
     * 次データがある場所を返します。無い場合は-1を返します。
     *
     * @return 次データがある場所
     */
    private int getNextIndex()
	{
        List<Boolean> list = (ArrayList<Boolean>)viewState.getObject(_KEY_COMPLATE);

        for (int i = viewState.getInt(_KEY_CURRENT) + 1; i < list.size(); i++)
        {
        	if(!list.get(i))
    		{
            	// まだ完了していないデータが今の位置より後にあればその場所を返却
        		return i;
    		}
        }
    	return _NO_DATA;
	}

    /**
     * 残作業数を返します。
     * 
     * @return 残作業数
     */
    private int getRestWorkQty()
	{
        int qty = 0;
    	List<Boolean> list = (ArrayList<Boolean>)viewState.getObject(_KEY_COMPLATE);

        for (int i = 0; i < list.size(); i++)
        {
        	// まだ完了していない作業の場合
        	if(!list.get(i))
    		{
        		// 加算
        		qty++;
    		}
        }
    	return qty;
	}

    /**
	 * @throws Exception
	 */
	private void operationLogWrite(Connection conn, DfkUserInfo ui)
			throws Exception
	{
			// write part11 log.
			P11LogWriter part11Writer = new P11LogWriter(conn);
			Part11List part11List = new Part11List();
			part11List.add(txt_RestWork.getStringValue(), "");
			part11List.add(txt_WorkNo.getStringValue(), "");
			part11List.add(txt_Location.getStringValue(), "");
			part11List.add(viewState.getObject(ViewStateKeys.VS_CARRY_FLAG), "");
			part11List.add(viewState.getObject(ViewStateKeys.VS_RETRIEVAL_DETAIL), "");
			part11List.add(txt_ItemCode.getStringValue(), "");
			part11List.add(txt_LotNo.getStringValue(), "");
			part11List.add(txt_StockQty.getStringValue(), "");
			part11List.add(txt_WorkQty.getStringValue(), "");
			part11List.add(txt_StorageDate.getStringValue(), txt_StorageTime.getStringValue(), "");
			part11List.add(txt_BatchNo.getStringValue(), "");
			part11List.add(txt_SlipNumber.getStringValue(), "");
			part11List.add(txt_LineNo.getStringValue(), "");
			part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
	}

    // DFKLOOK end メソッド追加
    // ------------------------------------------------------------
    // utility methods
    // ------------------------------------------------------------
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
