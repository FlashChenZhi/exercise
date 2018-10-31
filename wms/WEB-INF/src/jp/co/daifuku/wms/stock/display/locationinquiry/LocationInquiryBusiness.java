// $Id: LocationInquiryBusiness.java 4766 2009-07-27 06:53:05Z shibamoto $
package jp.co.daifuku.wms.stock.display.locationinquiry;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.Constants;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;

import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController.BankType;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsBankPullDownModel;
import jp.co.daifuku.wms.stock.schedule.LocationInquirySCH;
import jp.co.daifuku.wms.stock.schedule.LocationInquirySCHParams;
import jp.co.daifuku.wms.stock.schedule.LocationLevelView;

/**
 * 棚状態照会の画面処理を行います。
 *
 * @version $Revision: 4766 $, $Date: 2009-07-27 15:53:05 +0900 (月, 27 7 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LocationInquiryBusiness
        extends LocationInquiry
{
    // DFKLOOK ここから追加
    /**
     * Session用のキー：選択エリア
     */
    public static final String SELECTED_AREA = "SELECTED_AREA";

    /**
     * Session用のキー：選択バンク
     */
    public static final String SELECTED_BANK = "SELECTED_BANK";

    /**
     * Session用のキー：検索結果
     */
    public static final String RESULT = "RESULT";
    // DFKLOOK ここまで追加

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
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** PullDownModel pul_Bank */
    private WmsBankPullDownModel _pdm_pul_Bank;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LocationInquiryBusiness()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     *
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
     *
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
     *
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
     */
    public void btn_DisplaySync_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_DisplaySync_Click_Process();
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
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_Area.
        _pdm_pul_Area = new WmsAreaPullDownModel(pul_Area, locale, ui);

        // initialize pul_Bank.
        _pdm_pul_Bank = new WmsBankPullDownModel(pul_Bank, locale, ui);
        _pdm_pul_Bank.setParent(_pdm_pul_Area);

    }

    /**
     *
     * @throws Exception All the exceptions are reported.
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
            // DFKLOOK プルダウンの引数を設定
//            _pdm_pul_Area.init(conn, AreaType.FLOOR_FIXEDLOCATE, StationType.ALL, false, false);
//            _pdm_pul_Area.init(conn, AreaType.FLOOR_LOCATE, StationType.ALL, false, false);
            _pdm_pul_Area.init(conn, AreaType.FLOOR_LOCATE, StationType.ALL, "", false);

            // load pul_Bank.
            // DFKLOOK プルダウンの引数を設定
            _pdm_pul_Bank.init(conn, BankType.FLOOR, "");

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
     * @throws Exception All the exceptions are reported.
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(pul_Area);

    }

    /**
     * 画面の初期表示を行います。
     * @throws Exception All the exceptions are reported.
     */
     private void page_Load_Process()
            throws Exception
     {
         // clear.
         // DFKLOOK ここから追加
         // View Data をクリアする。
         this.getSession().setAttribute(SELECTED_AREA, null);
         this.getSession().setAttribute(SELECTED_BANK, null);
         this.getSession().setAttribute(RESULT, null);
     }

    /**
     *
     * @throws Exception All the exceptions are reported.
     */
    private void btn_DisplaySync_Click_Process()
            throws Exception
    {
        // input validation.
        pul_Area.validate(this, true);
        pul_Bank.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        LocationInquirySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new LocationInquirySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            LocationInquirySCHParams inparam = new LocationInquirySCHParams(ui);
            inparam.set(LocationInquirySCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(LocationInquirySCHParams.BANK_NO, _pdm_pul_Bank.getSelectedValue());

            // DFKLOOK ここから追加
            // View Data 取得
            LocationLevelView[] levelViews = sch.getLevelViewData(inparam);

            this.getSession().setAttribute(SELECTED_AREA, _pdm_pul_Area.getSelectedValue());
            this.getSession().setAttribute(SELECTED_BANK, _pdm_pul_Bank.getSelectedValue());
            this.getSession().setAttribute(RESULT, levelViews);
            // DFKLOOK ここまで追加

            // SCH call.
            // DFKLOOK queryをコメントにする
            // List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

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
