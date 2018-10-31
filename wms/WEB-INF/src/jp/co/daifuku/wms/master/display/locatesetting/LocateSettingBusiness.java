// $Id: LocateSettingBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.master.display.locatesetting;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.Constants;
import jp.co.daifuku.common.LocationFormatException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;

import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.master.schedule.LocateSettingSCH;
import jp.co.daifuku.wms.master.schedule.LocateSettingSCHParams;
import jp.co.daifuku.wms.master.schedule.MasterInParameter;

/**
 * 棚マスタ一括設定の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class LocateSettingBusiness
        extends LocateSetting
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
    /** RadioButtonGroupModel rdo_group_LocationSetting */
    private RadioButtonGroup _grp_rdo_group_LocationSetting;

    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LocateSettingBusiness()
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
       //DFKLOOK:ここから修正
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
       if (eventSource.equals("btn_Set_Click"))
       {
           // process call.
           btn_Setting_Click_Process(eventSource);
       }
       //DFKLOOK:ここまで
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Regist_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_Regist_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Delete_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_Delete_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pul_Area_Change(ActionEvent e)
            throws Exception
    {
		// DFKLOOK ここから修正
		// 棚の入力例を表示させます。
		lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue()));
		// DFKLOOK ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Setting_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        //DFKLOOK:ここから修正
        btn_Setting_Click_Process(null);
        //DFKLOOK:ここまで修正
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

        // initialize rdo_group_LocationSetting.
        _grp_rdo_group_LocationSetting = new RadioButtonGroup(new RadioButton[]{rdo_Regist, rdo_Delete}, locale);

        // initialize pul_Area.
        _pdm_pul_Area = new WmsAreaPullDownModel(pul_Area, locale, ui);

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

            // load pul_Area.
            _pdm_pul_Area.init(conn, AreaType.FLOOR_LOCATE, StationType.ALL, "", false);

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
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // DFKLOOK ここから
        // 棚の入力例を表示させます。
        _pdm_pul_Area.setSelectedIndex(0);
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue()));
        // DFKLOOK ここまで

        // clear.
        rdo_Regist.setChecked(true);

    }

    /**
     *
     * @throws Exception
     */
    private void rdo_Regist_Click_Process()
            throws Exception
    {
        // clear.
        txt_AisleNo.setReadOnly(false);

    }

    /**
     *
     * @throws Exception
     */
    private void rdo_Delete_Click_Process()
            throws Exception
    {
        // clear.
        txt_AisleNo.setReadOnly(true);
        txt_AisleNo.setValue(null);

    }

    /**
     *
     * @throws Exception
     */
    //DFKLOOK:ここから修正
    private void btn_Setting_Click_Process(String eventSource)
    //DFKLOOK:ここまで修正
            throws Exception
    {
        // input validation.
        txt_StLocate.validate(this, true);
        txt_EdLocate.validate(this, true);
        pul_Area.validate(this, true);

        String sLoc = null;
	    String eLoc = null;

    	// DFKLOOK start
	    try
    	{
	        // 開始棚（棚のフォーマットチェック）
	        sLoc = WmsFormatter.toParamLocation(txt_StLocate.getStringValue(),
					lbl_LocationStyle.getStringValue());
	        // 終了棚（棚のフォーマットチェック）
	        eLoc = WmsFormatter.toParamLocation(txt_EdLocate.getStringValue(),
	        		lbl_LocationStyle.getStringValue());
    	}
        // 棚フォーマットで投げられたExceptionをここでキャッチ
	    catch (LocationFormatException ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            return;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            return;
        }

        String locs[] = WmsFormatter.getFromTo(sLoc, eLoc);

        // 棚No.を数字の配列に直す
        int[] fromLocate = getIntShelfFormatArray(locs[0], pul_Area.getSelectedValue());
        int[] toLocate = getIntShelfFormatArray(locs[1], pul_Area.getSelectedValue());

        // 棚No.範囲の大小チェックを行う
        for (int i = 0; i < fromLocate.length; i++)
        {
        	if (fromLocate[i] > toLocate[i])
        	{
        		//　検索範囲を確認してください。
        		message.setMsgResourceKey("6123020");
        		return;
        	}
        }

    	// 合計の棚数を計算
        int noOfShelves = 1;

        for(int i = 0, count = 1; i < fromLocate.length; i++)
        {
	      	count = toLocate[i] - fromLocate[i]+1;
	      	if(count == 0)
	      	{
	      		count = 1;
	      	}
	      	noOfShelves *= count;
	    }

	    // 棚マスタ一括設定上限チェック
	    if (WmsParam.LOCATION_MAX < noOfShelves)
	    {
	    	// 6023232=棚マスタ一括設定の上限数{0}以下の棚数で設定を行ってください。
			message.setMsgResourceKey(WmsMessageFormatter.format(6023232, WmsParam.LOCATION_MAX));
			return;
	    }

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
        LocateSettingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new LocateSettingSCH(conn, this.getClass(), locale, ui);

            // DFKLOOK start
            // 入力パラメータを生成
            LocateSettingSCHParams inparam = new LocateSettingSCHParams(ui);

            // エリア
            inparam.set(LocateSettingSCHParams.AREA, pul_Area.getSelectedValue());
            //DFKLOOK:ここから修正
            inparam.set(LocateSettingSCHParams.ST_LOCATE, locs[0]);
            inparam.set(LocateSettingSCHParams.ED_LOCATE, locs[1]);
            // アイルNo.
            inparam.set(LocateSettingSCHParams.AISLE_NO, WmsFormatter.getInt(txt_AisleNo.getText()));

            // 設定区分
            // 登録
            if (rdo_Regist.getChecked())
            {
                inparam.set(LocateSettingSCHParams.GROUP_LOCATION_SETTING,
                        MasterInParameter.PROCESS_FLAG_REGIST);
            }
            // 削除
            else if (rdo_Delete.getChecked())
            {
                inparam.set(LocateSettingSCHParams.GROUP_LOCATION_SETTING,
                        MasterInParameter.PROCESS_FLAG_DELETE);
            }

            // チェック処理
            if (!sch.check(inparam))
            {
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            //DFKLOOK:ここまで修正

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
            part11List.add(MasterInParameter.PROCESS_FLAG_REGIST, "", rdo_Regist.getChecked());
            part11List.add(MasterInParameter.PROCESS_FLAG_DELETE, "", rdo_Delete.getChecked());

            part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");
            part11List.add(txt_StLocate.getStringValue(), "");
            part11List.add(txt_EdLocate.getStringValue(), "");
            part11List.add(txt_AisleNo.getStringValue(), "");
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
    private void btn_Clear_Click_Process()
            throws Exception
    {

        // DFKLOOK ここから
        // 棚の入力例を表示させます。
        _pdm_pul_Area.setSelectedIndex(0);
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue()));
        // DFKLOOK ここまで

        // clear.
        rdo_Regist.setChecked(true);
        _pdm_pul_Area.setSelectedValue(null);
        txt_StLocate.setValue(null);
        txt_EdLocate.setValue(null);
        txt_AisleNo.setValue(null);
        // DFKLOOK ここから
        txt_AisleNo.setReadOnly(false);
        setFocus(rdo_Regist);
        // DFKLOOK ここまで
    }

    /**
     * 棚No.のInt配列取得処理<jp>
     * @param location 入力棚
     * @param areaNo 入力エリア
     * @return バンク･ベイ･レベルが格納されている配列を返す
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */

    private int[] getIntShelfFormatArray(String location, String areaNo)
    throws Exception
	{
    	// 棚フォーマットスタイルを取得
        //DFKLOOK 3.5 Start
        Connection conn = null;
        String style = null;
        try {
            conn = ConnectionManager.getRequestConnection(this);
            AreaController  areaController = new AreaController( conn, getClass());
            style = areaController.getLocationStyle(areaNo);
        }
        finally {
            DBUtil.close(conn);
        }
		//DFKLOOK 3.5 End

		// areaNoと同じ棚フォーマットで区切られた配列を用意する。
		String[] locationStyle = new String[style.split("\\W").length];
		int[] locationNo = new int[style.split("\\W").length];

		// areaNo.の棚フォーマットの列を入れる。
		locationStyle = style.split("\\W");


		for(int i = 0, size = 0; i < locationStyle.length ; i++ )
		{
			// 数字のみかチェック。そうでない場合はNumberFormatExceptionをスローする
			locationNo[i] = Integer.parseInt(locationStyle[i]);

			locationNo[i] = Integer.parseInt(location.substring(size, size + locationStyle[i].length()));
			size = size + locationStyle[i].length();
		}

		// バンク･ベイ･レベルが格納されている配列を返す
		return locationNo;
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
