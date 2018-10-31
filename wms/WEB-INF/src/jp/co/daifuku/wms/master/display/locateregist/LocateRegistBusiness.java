// $Id: LocateRegistBusiness.java 7401 2010-03-05 12:08:12Z shibamoto $
package jp.co.daifuku.wms.master.display.locateregist;

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
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.LocationFormatException;
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
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.listbox.locate.LstLocateParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.master.schedule.LocateRegistSCH;
import jp.co.daifuku.wms.master.schedule.LocateRegistSCHParams;

/**
 * 棚マスタ登録の画面処理を行います。
 *
 * @version $Revision: 7401 $, $Date: 2010-03-05 21:08:12 +0900 (金, 05 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LocateRegistBusiness
        extends LocateRegist
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
    /** PullDownModel pul_AreaNo */
    private WmsAreaPullDownModel _pdm_pul_AreaNo;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LocateRegistBusiness()
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
        // DFKLOOK: ここから
        // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_AreaNo.getSelectedValue()));
        // DFKLOOK: ここまで 
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
      if (eventSource.startsWith("btn_Submit_Click"))
      {
          // process call.
    	  btn_Submit_Click_Process(eventSource);
      }
   }
   
   /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pul_AreaNo_Change(ActionEvent e)
            throws Exception
    {
		// DFKLOOK 棚の入力例を表示させます。
		lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_AreaNo.getSelectedValue()));
        //DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
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
    public void btn_Submit_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Submit_Click_Process(null);
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

        // initialize pul_AreaNo.
        _pdm_pul_AreaNo = new WmsAreaPullDownModel(pul_AreaNo, locale, ui);

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

            // load pul_AreaNo.
            _pdm_pul_AreaNo.init(conn, AreaType.FLOOR_LOCATE, StationType.ALL, "", false);

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
        setFocus(pul_AreaNo);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        _pdm_pul_AreaNo.setSelectedValue(null);
        txt_LocateNo.setValue(null);
        txt_AisleNo.setValue(null);

        // DFKLOOK: ここから
        // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_AreaNo.getSelectedValue()));
        // DFKLOOK: ここまで       
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Search_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstLocateParams inparam = new LstLocateParams();
        inparam.set(LstLocateParams.LOCATION_NO, txt_LocateNo.getValue());
        inparam.set(LstLocateParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_Search_Click");
        redirect("/base/listbox/locate/LstLocate.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_Search_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstLocateParams outparam = new LstLocateParams(dialogParams);
        txt_LocateNo.setValue(outparam.get(LstLocateParams.LOCATION_NO));
        
        // DFKLOOK: ここから
        // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_AreaNo.getSelectedValue()));
        // DFKLOOK: ここまで 
        
        // set focus.
        setFocus(txt_LocateNo);

    }

    /**
     *
     * @throws Exception
     */
    // DFKLOOK 引数追加
    private void btn_Submit_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        txt_LocateNo.validate(this, true);
        txt_AisleNo.validate(this, false);
        pul_AreaNo.validate(this, true);

    	// DFKLOOK start
        String loc = null;
        try
        {
        	// 棚のフォーマットチェック
        	loc = WmsFormatter.toParamLocation(txt_LocateNo.getStringValue(), lbl_LocationStyle.getStringValue());
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
        
        if(StringUtil.isBlank(eventSource))
        {
        	// 登録しますか?
            this.setConfirm("MSG-W0012", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_Submit_Click");
            return;
        }
    	// DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        LocateRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new LocateRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            LocateRegistSCHParams inparam = new LocateRegistSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(LocateRegistSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            // DFKLOOK start
            inparam.set(LocateRegistSCHParams.LOCATE_NO, loc);
            // DFKLOOK end
            inparam.set(LocateRegistSCHParams.AISLE_NO, txt_AisleNo.getValue());
            inparam.set(LocateRegistSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

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
            part11List.add(_pdm_pul_AreaNo.getSelectedStringValue(), "");
            part11List.add(txt_LocateNo.getStringValue(), "");
            part11List.add(txt_AisleNo.getStringValue(), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_REGIST), part11List);

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
        // clear.
        _pdm_pul_AreaNo.setSelectedValue(null);
        txt_LocateNo.setValue(null);
        txt_AisleNo.setValue(null);

        //DFKLOOK:ここから修正
        // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_AreaNo.getSelectedValue()));
        //DFKLOOK:ここまで修正
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
