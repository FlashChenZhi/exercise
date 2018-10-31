// $Id: MaxWorkCountSettingBusiness.java 7162 2010-02-19 09:32:59Z shibamoto $
package jp.co.daifuku.pcart.master.display.maxworkcountsetting;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.pcart.master.schedule.MaxWorkCountSettingSCH;
import jp.co.daifuku.pcart.master.schedule.MaxWorkCountSettingSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;

/**
 * 最大作業数設定の画面処理を行います。
 *
 * @version $Revision: 7162 $, $Date: 2010-02-19 18:32:59 +0900 (金, 19 2 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class MaxWorkCountSettingBusiness
        extends MaxWorkCountSetting
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

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public MaxWorkCountSettingBusiness()
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

        // DFKLOOK:ここから修正
        getDisplay();
        // DFKLOOK:ここまで修正
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
        if (eventSource.startsWith("btn_Set_Click"))
        {
            btn_Set_Click_Process(eventSource);
        }
    }
    // DFKLOOK end
    
    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pul_Area_Change(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // スケジュール
        MaxWorkCountSettingSCH schedule = null;
        // コネクション
        Connection conn = null;

        try
        {
            // コネクション
            conn = ConnectionManager.getRequestConnection(this);
            // スケジュール
            schedule = new MaxWorkCountSettingSCH(conn, this.getClass(), locale, ui);
            // パラメータ
            MaxWorkCountSettingSCHParams inParam = new MaxWorkCountSettingSCHParams();
            // エリア
            inParam.set(MaxWorkCountSettingSCHParams.AREA, pul_Area.getSelectedValue());

            // PCTシステム情報読込み            
            List<Params> outparams = schedule.query(inParam);

            // output display.
            for (Params outparam : outparams)
            {
                txt_MaxCount.setValue(outparam.get(MaxWorkCountSettingSCHParams.MAX_COUNT));
                viewState.setObject(ViewStateKeys.VS_LAST_UPDATE_DATE,
                        outparam.get(MaxWorkCountSettingSCHParams.LAST_UPDATE_DATE));
            }
            // 検索結果がない場合
            if (outparams.isEmpty())
            {
                // 最大作業数
                txt_MaxCount.setInt(0);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            try
            {
                // コネクションのクローズ
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            }
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
    // DFKLOOK:ここから修正
    /**
     * 画面表示処理を行います。<BR>
     * <BR>
     * 概要：画面表示処理を行います。<BR>
     * 
     * @throws Exception 全ての例外を報告します。
     */
    protected void getDisplay()
            throws Exception
    {

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        MaxWorkCountSettingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new MaxWorkCountSettingSCH(conn, this.getClass(), locale, ui);
            // プルダウン初期設定       
            pul_Area.setSelectedIndex(0);
            // エリア情報がない場合
            if (pul_Area.getSelectedValue() == null)
            {
                // 設定ボタン
                btn_Set.setEnabled(false);
                // 最大作業数
                txt_MaxCount.setText("");

                // 6023139=エリアがマスタに登録されていません。。
                message.setMsgResourceKey("6023139");
            }
            else
            {
                // 設定ボタン
                btn_Set.setEnabled(true);
            }


            // set input parameters.
            MaxWorkCountSettingSCHParams inparam = new MaxWorkCountSettingSCHParams();
            inparam.set(MaxWorkCountSettingSCHParams.AREA, _pdm_pul_Area.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            for (Params outparam : outparams)
            {
                txt_MaxCount.setValue(outparam.get(MaxWorkCountSettingSCHParams.MAX_COUNT));
                viewState.setObject(ViewStateKeys.VS_LAST_UPDATE_DATE,
                        outparam.get(MaxWorkCountSettingSCHParams.LAST_UPDATE_DATE));
            }
            // 検索結果がない場合
            if (outparams.isEmpty())
            {
                // 最大作業数
                txt_MaxCount.setInt(0);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            try
            {
                // コネクションのクローズ
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            }
        }
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
            _pdm_pul_Area.init(conn, AreaType.FLOOR, StationType.ALL, "", false);


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
        setFocus(pul_Area);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        txt_MaxCount.validate(this, false);
        pul_Area.validate(this, true);

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
        MaxWorkCountSettingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new MaxWorkCountSettingSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            MaxWorkCountSettingSCHParams inparam = new MaxWorkCountSettingSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(MaxWorkCountSettingSCHParams.AREA, _pdm_pul_Area.getSelectedValue());
            inparam.set(MaxWorkCountSettingSCHParams.MAX_COUNT, txt_MaxCount.getValue());
            inparam.set(MaxWorkCountSettingSCHParams.LAST_UPDATE_DATE,
                    viewState.getObject(ViewStateKeys.VS_LAST_UPDATE_DATE));

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // commit.
            conn.commit();
            // DFKLOOK:ここから修正
            // 画面再表示
            getDisplay();
            // DFKLOOK:ここまで修正
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
     *
     * @throws Exception
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        // DFKLOOK:ここから修正
        // プルダウン初期設定       
        pul_Area.setSelectedIndex(0);
        // 画面表示
        getDisplay();
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
