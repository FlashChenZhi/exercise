// $Id: AsSimpleDirectTransferBusiness.java 7370 2010-03-05 04:20:30Z shibamoto $
package jp.co.daifuku.wms.asrs.display.simpledirecttransfer;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;
import jp.co.daifuku.wms.asrs.schedule.AsSimpleDirectTransferSCH;
import jp.co.daifuku.wms.asrs.schedule.AsSimpleDirectTransferSCHParams;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.Distribution;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkspacePullDownModel;

/**
 * AS/RS 簡易直行設定の画面処理を行います。
 *
 * @version $Revision: 7370 $, $Date:: 2010-03-05 13:20:30 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class AsSimpleDirectTransferBusiness
        extends AsSimpleDirectTransfer
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
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** PullDownModel pul_WorkPlace */
    private WmsWorkspacePullDownModel _pdm_pul_WorkPlace;

    /** PullDownModel pul_FromStation */
    private WmsStationPullDownModel _pdm_pul_FromStation;

    /** PullDownModel pul_ToStation */
    private WmsStationPullDownModel _pdm_pul_ToStation;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsSimpleDirectTransferBusiness()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面が初期表示されたときに呼ばれます。
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
    }

    /**
     * 各コントロールイベント呼び出し前に呼ばれます。
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
        if (eventSource.startsWith("btn_Set_Click"))
        {
            // process call.
            btn_Set_Click_Process(eventSource);
        }
        // DFKLOOK:ここまで修正
    }

    /** 
     * 作業場プルダウン変更時に呼ばれます。
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void pul_WorkPlace_Change(ActionEvent e) throws Exception
    {
        // DFKLOOK ここから
        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            
            String select_wp = pul_WorkPlace.getSelectedValue();
            
            if (!StringUtil.isBlank(select_wp))
            {
                // reload pul_FromStation.
                pul_FromStation.clearItem();
                _pdm_pul_FromStation.init(conn, StationType.DIRECT_TRANSFER_FROM, Distribution.UNUSE, Distribution.UNUSE, select_wp);
                
                // reload pul_ToStation.
                pul_ToStation.clearItem();
                _pdm_pul_ToStation.init(conn, StationType.DIRECT_TRANSFER_TO, Distribution.UNUSE);
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
        // DFKLOOK ここまで
    }

    /**
     * 設定ボタンが押下された時に呼ばれます。
     * 
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Set_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK start
        // process call.
        btn_Set_Click_Process(null);
        // DFKLOOK end
    }

    /**
     * メニューへ遷移します。
     * 
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
     * コンポーネントの初期化処理を行います。
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

        // initialize pul_WorkPlace.
        _pdm_pul_WorkPlace = new WmsWorkspacePullDownModel(pul_WorkPlace, locale, ui);

        // initialize pul_FromStation.
        _pdm_pul_FromStation = new WmsStationPullDownModel(pul_FromStation, locale, ui);

        // initialize pul_ToStation.
        _pdm_pul_ToStation = new WmsStationPullDownModel(pul_ToStation, locale, ui);
        _pdm_pul_ToStation.setParent(_pdm_pul_FromStation);

    }

    /**
     * プルダウンの初期化を行います。
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
            _pdm_pul_Area.init(conn, AreaType.ASRS, StationType.DIRECT_TRANSFER_FROM, "", false);

            
            // load pul_WorkPlace.
            _pdm_pul_WorkPlace.init(conn, StationType.DIRECT_TRANSFER_FROM);
            
            // DFKLOOK ここから
            // 先頭の作業場No.を取得するために0番目を選択する
            _pdm_pul_WorkPlace.setSelectedIndex(0);
            
            String select_wp = _pdm_pul_WorkPlace.getSelectedStringValue();
            if (!StringUtil.isBlank(select_wp))
            {
                // load pul_FromStation.
                _pdm_pul_FromStation.init(conn, StationType.DIRECT_TRANSFER_FROM, Distribution.UNUSE, Distribution.UNUSE, select_wp);
                
                // load pul_ToStation.
                _pdm_pul_ToStation.init(conn, StationType.DIRECT_TRANSFER_TO, Distribution.UNUSE);
            }
            // DFKLOOK ここまで

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
     * 初期化処理を行います。
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
     * 設定処理を行います。
     * 
     * @throws Exception
     */
    // DFKLOOK start
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    // DFKLOOK end
    {
        // input validation.
        pul_Area.validate(this, true);
        pul_FromStation.validate(this, true);
        pul_ToStation.validate(this, true);

        // DFKLOOK:ここから修正        
        if (StringUtil.isBlank(eventSource))
        {
            // show confirm message. 設定しますか？
            this.setConfirm("MSG-W9000", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
            return;
        }
        // DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsSimpleDirectTransferSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsSimpleDirectTransferSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsSimpleDirectTransferSCHParams inparam = new AsSimpleDirectTransferSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(AsSimpleDirectTransferSCHParams.AREA, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsSimpleDirectTransferSCHParams.SOURCE_STATION_NO, _pdm_pul_FromStation.getSelectedValue());
            inparam.set(AsSimpleDirectTransferSCHParams.DEST_STATION_NO, _pdm_pul_ToStation.getSelectedValue());
            inparam.set(AsSimpleDirectTransferSCHParams.CONSIGNOR_CODE, WmsParam.SIMPLEDIRECTTRANSFER_CONSIGNORCODE);
            inparam.set(AsSimpleDirectTransferSCHParams.ITEM_CODE, WmsParam.SIMPLEDIRECTTRANSFER_ITEMCODE);
            inparam.set(AsSimpleDirectTransferSCHParams.PLAN_QTY, 1);

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
            part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");
            part11List.add(_pdm_pul_WorkPlace.getSelectedStringValue(), "");
            part11List.add(_pdm_pul_FromStation.getSelectedStringValue(), "");
            part11List.add(_pdm_pul_ToStation.getSelectedStringValue(), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());
            
            // DFKLOOK ここから
            // 搬送指示送信を起動します。
            SendRequestor req = new SendRequestor();
            req.storage();
            // DFKLOOK ここまで

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
