// $Id: AreaRegistBusiness.java 7401 2010-03-05 12:08:12Z shibamoto $
package jp.co.daifuku.wms.master.display.arearegist;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.Constants;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.listbox.area.LstAreaParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.master.schedule.AreaRegistSCH;
import jp.co.daifuku.wms.master.schedule.AreaRegistSCHParams;

/**
 * エリアマスタ登録の画面処理を行います。
 *
 * @version $Revision: 7401 $, $Date: 2010-03-05 21:08:12 +0900 (金, 05 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class AreaRegistBusiness
        extends AreaRegist
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    // DFKLOOK start
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /**
     * 棚の（ハイフンを抜いた）最大桁数です。
     */
    private static int freeAreaPermNumber = 8;
    // DFKLOOK end
    
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_AreaType */
    private DefaultPullDownModel _pdm_pul_AreaType;

    /** PullDownModel pul_LocationType */
    private DefaultPullDownModel _pdm_pul_LocationType;

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
    public AreaRegistBusiness()
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
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        // get event source.
        DialogParameters dialogParams = ((DialogEvent)e).getDialogParameters();
        String eventSource = dialogParams.getParameter(_KEY_POPUPSOURCE);
        if (StringUtil.isBlank(eventSource))
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_Search_Click"))
        {
            // process call.
            btn_Search_Click_DlgBack(dialogParams);
        }
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
		if(!isExecute)
		{
			return;
		}

		// choose process.
		if(eventSource.startsWith("btn_Set_Click"))
		{
			// process call.
			btn_Set_Click_Process(eventSource);
		}
		// DFKLOOK end
	}
   
    /**
	 * @param e
	 *        ActionEvent
	 * @throws Exception
	 */
    public void btn_Search_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Search_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pul_AreaType_Change(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // プルダウンの変更イベント追加
        // エリア種別が入荷の場合(SystemDefineのimportも必要)
        if (SystemDefine.AREA_TYPE_RECEIVING.equals(pul_AreaType.getSelectedValue()))
        {
        	// 棚フォーマットは選べないのでディフォルトを使う
        	txt_Location.setReadOnly(true);
        	txt_Location.setText(WmsParam.DEFAULT_LOCATE_STYLE);
        	
        	pul_ReceivingArea.setSelectedIndex(0);
            // 「入荷エリア」プルダウンを無効化
            pul_ReceivingArea.setEnabled(false);
            // 入荷エリア定義ありチェックを使用不可とする
            chk_ReceivingArea.setEnabled(false);
            // 入荷エリア定義ありチェックを外す
            chk_ReceivingArea.setChecked(false);
        }
        else
        {
        	// エリア種別が仮置エリアの場合
        	if(SystemDefine.AREA_TYPE_TEMPORARY.equals(pul_AreaType.getSelectedValue()))
        	{
	        	// 棚フォーマットは選べないのでディフォルトを使う
	        	txt_Location.setReadOnly(true);
	        	txt_Location.setText(WmsParam.DEFAULT_LOCATE_STYLE);
        	}
        	// エリア種別が平置エリアの場合
        	if(SystemDefine.AREA_TYPE_FLOOR.equals(pul_AreaType.getSelectedValue()))
        	{
        		//　棚フォーマットを有効化
        		txt_Location.setReadOnly(false);
        		txt_Location.setValue(null);
        	}

            // 入荷エリア定義ありチェックを使用不可とする
            if (pul_ReceivingArea.getItems().size() == 0)
            {
                chk_ReceivingArea.setEnabled(false);
            }
            else
            {
            	// 一番上の値を表示
            	pul_ReceivingArea.setSelectedIndex(0);
                chk_ReceivingArea.setEnabled(true);
            }
            // 「入荷エリア」プルダウンを無効化
            pul_ReceivingArea.setEnabled(false);
            // 入荷エリア定義ありチェックを外す
            chk_ReceivingArea.setChecked(false);

        }

        // 移動先仮置エリア定義ありチェックを使用可とする
        if (pul_TemporaryArea.getItems().size() == 0)
        {
            chk_MoveTemporaryStorage.setEnabled(false);
        }
        else
        {
        	// プルダウンを有効化する
        	pul_TemporaryArea.setSelectedIndex(0);
            chk_MoveTemporaryStorage.setEnabled(true);
        }
        // 「移動先仮置エリア」プルダウンを無効化       
        pul_TemporaryArea.setEnabled(false);
        // 移動先仮置エリア定義ありチェックを外す
        chk_MoveTemporaryStorage.setChecked(false);
        // DFKLOOK:ここまで修正
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
    public void btn_Set_Click(ActionEvent e)
            throws Exception
    {
        // process call.
    	// DFKLOOK 引数追加
        btn_Set_Click_Process(null);
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

        // initialize pul_AreaType.
        _pdm_pul_AreaType = new DefaultPullDownModel(pul_AreaType, locale, ui);

        // initialize pul_LocationType.
        _pdm_pul_LocationType = new DefaultPullDownModel(pul_LocationType, locale, ui);
        _pdm_pul_LocationType.setParent(_pdm_pul_AreaType);

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

        // DFKLOOK:ここから修正
        // プルダウン内容のクリア
        _pdm_pul_TemporaryArea.clear();
        _pdm_pul_ReceivingArea.clear();
        // DFKLOOK:ここまで修正

        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // load pul_AreaType.
            //DFKLOOK:ここから修正
            //_pdm_pul_AreaType.init(conn);

            // load pul_LocationType.
            //_pdm_pul_LocationType.init(conn);
            //DFKLOOK:ここまで修正

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
        setFocus(txt_Area);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        txt_Area.setValue(null);
        txt_AreaName.setValue(null);
        _pdm_pul_AreaType.setSelectedValue(null);
        _pdm_pul_LocationType.setSelectedValue(null);
        pul_TemporaryArea.setEnabled(false);
        chk_MoveTemporaryStorage.setChecked(false);
        _pdm_pul_VacantSearchType.setSelectedValue(null);
        pul_ReceivingArea.setEnabled(false);
        chk_ReceivingArea.setChecked(false);
        txt_Location.setValue(null);
        txt_Location.setReadOnly(false);

        // DFKLOOK:ここから修正
        // 移動先仮置エリアが存在しない場合
        if (pul_TemporaryArea.getItems().size() == 0)
        {
            // チェックボックスは無効にする
            chk_MoveTemporaryStorage.setEnabled(false);
        }

        // 入荷エリアが存在しない場合
        if (pul_ReceivingArea.getItems().size() == 0)
        {
            // チェックボックスは無効にする
            chk_ReceivingArea.setEnabled(false);
        }
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Search_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstAreaParams inparam = new LstAreaParams();
        inparam.set(LstAreaParams.AREA_NO, txt_Area.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_Search_Click");
        redirect("/base/listbox/area/LstArea.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_Search_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstAreaParams outparam = new LstAreaParams(dialogParams);
        txt_Area.setValue(outparam.get(LstAreaParams.AREA_NO));
        txt_AreaName.setValue(outparam.get(LstAreaParams.AREA_NAME));

        // set focus.
        setFocus(txt_Area);

    }

    /**
     *
     * @throws Exception
     */
    // DFKLOOK 引数追加
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        txt_Area.validate(this, true);
        txt_AreaName.validate(this, true);
        txt_Location.validate(this, true);

        // DFKLOOK:start
        // 空白チェック
        if (txt_Area.getText().indexOf(" ") != -1)
        {
        	// 6003101={0}として使用できない文字が含まれています。  LBL-W0228=エリア
            message.setMsgResourceKey(WmsMessageFormatter.format(6003101, DisplayText.getText("LBL-W0228")));
            return;
        }

        String locationStyle = txt_Location.getStringValue();

        // 最初か最後がハイフンの時
        if (locationStyle.charAt(0) == '-' || locationStyle.charAt(locationStyle.length() - 1) == '-')
        {
            // (6047031)棚フォーマットエラーです。
            message.setMsgResourceKey("6023697");
            return;
        }

        char c;
        int noOfHyphens = 0, noOfChars = 0;
        for (int i = 0; i < locationStyle.length(); i++)
        {
            // 指定以外の文字が入力された場合
            c = Character.toUpperCase(locationStyle.charAt(i));
            if (c != 'X' && c != '9' && c != '-')
            {
                // (6047031)棚フォーマットエラーです。
            	message.setMsgResourceKey("6023697");
                return;
            }

            // 配列のチェック項目は文字列の長さの一つ前までで完了する
            if (i < locationStyle.length() - 1)
            {
                // ハイフンが連続で入力された場合
                if (c == '-' && locationStyle.charAt(i + 1) == '-')
                {
                    // (6047031)棚フォーマットエラーです。
                	message.setMsgResourceKey("6023697");
                    return;
                }
                // ９とXがハイフンの区分内で混じった場合（i.e.　X9-9X9）
                if (c == '9' && Character.toUpperCase(locationStyle.charAt(i + 1)) == 'X')
                {
                    // (6047031)棚フォーマットエラーです。
                	message.setMsgResourceKey("6023697");
                    return;
                }
                if (c == 'X' && Character.toUpperCase(locationStyle.charAt(i + 1)) == '9')
                {
                    // (6047031)棚フォーマットエラーです。
                	message.setMsgResourceKey("6023697");
                    return;
                }
            }
            // 文字が入っているかチェック
            if (c == 'X')
            {
                noOfChars++;
            }
            // ハイフンが何個あるかチェック
            else if (c == '-')
            {
                noOfHyphens++;
            }
        }

        String loctype = _pdm_pul_LocationType.getSelectedValue().toString();
        // 棚マスタ管理の場合、数字とフォーマットに制限有（ハイフン2つに数字のみ）
        if (SystemDefine.LOCATION_TYPE_MASTER.equals(loctype)
                || SystemDefine.LOCATION_TYPE_FIXED.equals(loctype))
        {
            if (noOfHyphens < 2 || noOfChars > 0)
            {
                // (6023284)棚フォーマットエラーです。棚マスタ管理、または固定棚管理の場合、ハイフン2つ以上と数字のみになります。
            	message.setMsgResourceKey("6023284");
                return;
            }
        }
        
        // 棚のハイフン抜きの桁数チェックを行います。
        if(locationStyle.length() - noOfHyphens > freeAreaPermNumber)
        {
            //棚フォーマットエラーです。フリー管理棚の場合、入力可能桁数はハイフンを抜いて8桁以下です。
        	message.setMsgResourceKey("6023295");
            return;
        }

        if(StringUtil.isBlank(eventSource))
        {
        	// 登録しますか?
            this.setConfirm("MSG-W0012", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
            return;
        }
    	// DFKLOOK end

    	// get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AreaRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AreaRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AreaRegistSCHParams inparam = new AreaRegistSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(AreaRegistSCHParams.AREA_NO, txt_Area.getValue());
            inparam.set(AreaRegistSCHParams.AREA_NAME, txt_AreaName.getValue());
            inparam.set(AreaRegistSCHParams.AREA_TYPE, _pdm_pul_AreaType.getSelectedValue());
            inparam.set(AreaRegistSCHParams.LOCATION_TYPE, _pdm_pul_LocationType.getSelectedValue());
            inparam.set(AreaRegistSCHParams.TEMPORARY_AREA, _pdm_pul_TemporaryArea.getSelectedValue());
            inparam.set(AreaRegistSCHParams.VACANT_SEARCH_TYPE, _pdm_pul_VacantSearchType.getSelectedValue());
            inparam.set(AreaRegistSCHParams.RECEIVING_AREA, _pdm_pul_ReceivingArea.getSelectedValue());
            inparam.set(AreaRegistSCHParams.LOCATION_STYLE, txt_Location.getValue());
            inparam.set(AreaRegistSCHParams.MOVE_TEMPORARY_STORAGE, chk_MoveTemporaryStorage.getValue());
            inparam.set(AreaRegistSCHParams.CHK_RECEIVING_AREA, chk_ReceivingArea.getValue());

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
            part11List.add(txt_Area.getStringValue(), "");
            part11List.add(txt_AreaName.getStringValue(), "");
            part11List.add(_pdm_pul_AreaType.getSelectedStringValue(), "");
            part11List.add(_pdm_pul_LocationType.getSelectedStringValue(), "");
            part11List.add(txt_Location.getStringValue(), "");

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

            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_REGIST), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            // initialize pulldown models.
            initializePulldownModels();

            // 登録前の状態を復元
            // エリア種別
            pul_TemporaryArea.setSelectedItem(inparam.getString(AreaRegistSCHParams.AREA_TYPE));
            // 入荷エリア
            pul_ReceivingArea.setSelectedItem(inparam.getString(AreaRegistSCHParams.RECEIVING_AREA));

            // エリア種別が入荷の場合
            if (SystemDefine.AREA_TYPE_RECEIVING.equals(pul_AreaType.getSelectedValue()))
            {
                // 入荷エリア定義ありチェックを使用不可とする
                chk_ReceivingArea.setEnabled(false);
                // 入荷エリア定義ありチェックを外す
                chk_ReceivingArea.setChecked(false);
                // 「入荷エリア」プルダウンを無効化
                pul_ReceivingArea.setEnabled(false);
            }
            // 入荷エリア定義なしの場合
            else if (!chk_ReceivingArea.getChecked())
            {
                // 「入荷エリア」プルダウンを無効化
                pul_ReceivingArea.setEnabled(false);
            }

            // エリアマスタ再読込(棚フォーマット定義)
            SuperLocationHolder.getInstance().update();
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
        txt_Area.setValue(null);
        txt_AreaName.setValue(null);
        _pdm_pul_AreaType.setSelectedValue(null);
        _pdm_pul_LocationType.setSelectedValue(null);
        pul_TemporaryArea.setEnabled(false);
        chk_MoveTemporaryStorage.setChecked(false);
        _pdm_pul_VacantSearchType.setSelectedValue(null);
        pul_ReceivingArea.setEnabled(false);
        chk_ReceivingArea.setChecked(false);
        txt_Location.setValue(null);
        txt_Location.setReadOnly(false);

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