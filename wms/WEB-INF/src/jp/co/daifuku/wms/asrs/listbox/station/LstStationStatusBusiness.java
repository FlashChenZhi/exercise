// $Id: LstStationStatusBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.asrs.listbox.station;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.Constants;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.wms.asrs.dasch.LstStationStatusDASCH;
import jp.co.daifuku.wms.asrs.dasch.LstStationStatusDASCHParams;

/**
 * ステーション状態一覧の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class LstStationStatusBusiness
        extends LstStationStatus
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_DASCH_LIST = "_KEY_DASCH_LIST";

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_StationStatus(HIDDEN_STATION_NAME) */
    private static final ListCellKey KEY_HIDDEN_STATION_NAME =
            new ListCellKey("HIDDEN_STATION_NAME", new StringCellColumn(), false, false);

    /** lst_StationStatus(HIDDEN_MODE_TYPE) */
    private static final ListCellKey KEY_HIDDEN_MODE_TYPE =
            new ListCellKey("HIDDEN_MODE_TYPE", new StringCellColumn(), false, false);

    /** lst_StationStatus(HIDDEN_CURRENT_MODE) */
    private static final ListCellKey KEY_HIDDEN_CURRENT_MODE =
            new ListCellKey("HIDDEN_CURRENT_MODE", new StringCellColumn(), false, false);

    /** lst_StationStatus(HIDDEN_STATUS) */
    private static final ListCellKey KEY_HIDDEN_STATUS =
            new ListCellKey("HIDDEN_STATUS", new StringCellColumn(), false, false);

    /** lst_StationStatus(HIDDEN_SUSPEND) */
    private static final ListCellKey KEY_HIDDEN_SUSPEND =
            new ListCellKey("HIDDEN_SUSPEND", new StringCellColumn(), false, false);

    /** lst_StationStatus(HIDDEN_MODE_REQUEST) */
    private static final ListCellKey KEY_HIDDEN_MODE_REQUEST =
            new ListCellKey("HIDDEN_MODE_REQUEST", new StringCellColumn(), false, false);

    /** lst_StationStatus(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT =
            new ListCellKey("LST_SELECT", new StringCellColumn(), true, false);

    /** lst_StationStatus(LST_STATION_NO) */
    private static final ListCellKey KEY_LST_STATION_NO =
            new ListCellKey("LST_STATION_NO", new StringCellColumn(), true, false);

    /** lst_StationStatus(LST_MODE_TYPE) */
    private static final ListCellKey KEY_LST_MODE_TYPE =
            new ListCellKey("LST_MODE_TYPE", new StringCellColumn(), true, false);

    /** lst_StationStatus(LST_CURRENT_MODE) */
    private static final ListCellKey KEY_LST_CURRENT_MODE =
            new ListCellKey("LST_CURRENT_MODE", new StringCellColumn(), true, false);

    /** lst_StationStatus(LST_STATUS) */
    private static final ListCellKey KEY_LST_STATUS =
            new ListCellKey("LST_STATUS", new StringCellColumn(), true, false);

    /** lst_StationStatus(LST_SUSPEND) */
    private static final ListCellKey KEY_LST_SUSPEND =
            new ListCellKey("LST_SUSPEND", new StringCellColumn(), true, false);

    /** lst_StationStatus(LST_WORK_COUNT) */
    private static final ListCellKey KEY_LST_WORK_COUNT =
            new ListCellKey("LST_WORK_COUNT", new NumberCellColumn(0), true, false);

    /** lst_StationStatus(LST_MODE_REQUEST) */
    private static final ListCellKey KEY_LST_MODE_REQUEST =
            new ListCellKey("LST_MODE_REQUEST", new StringCellColumn(), true, false);

    /** lst_StationStatus kyes */
    private static final ListCellKey[] LST_STATIONSTATUS_KEYS = {
            KEY_HIDDEN_STATION_NAME,
            KEY_HIDDEN_MODE_TYPE,
            KEY_HIDDEN_CURRENT_MODE,
            KEY_HIDDEN_STATUS,
            KEY_HIDDEN_SUSPEND,
            KEY_HIDDEN_MODE_REQUEST,
            KEY_LST_SELECT,
            KEY_LST_STATION_NO,
            KEY_LST_MODE_TYPE,
            KEY_LST_CURRENT_MODE,
            KEY_LST_STATUS,
            KEY_LST_SUSPEND,
            KEY_LST_WORK_COUNT,
            KEY_LST_MODE_REQUEST,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_StationStatus */
    private ListCellModel _lcm_lst_StationStatus;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstStationStatusBusiness()
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
        // initialize componenets.
        initializeComponents();

        // process call.
        page_Initialize_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_U_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        page_Load_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_U_Prev(ActionEvent e)
            throws Exception
    {
        _pager.previous();
        page_Load_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_U_Next(ActionEvent e)
            throws Exception
    {
        _pager.next();
        page_Load_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_U_Last(ActionEvent e)
            throws Exception
    {
        _pager.last();
        page_Load_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Close_U_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_U_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_StationStatus_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_StationStatus.getActiveCol();

        // choose process.
        if (_lcm_lst_StationStatus.getColumnIndex(KEY_LST_SELECT) == activeCol)
        {
            // process call.
            lst_Select_Click_Process();
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_D_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        page_Load_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_D_Prev(ActionEvent e)
            throws Exception
    {
        _pager.previous();
        page_Load_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_D_Next(ActionEvent e)
            throws Exception
    {
        _pager.next();
        page_Load_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_D_Last(ActionEvent e)
            throws Exception
    {
        _pager.last();
        page_Load_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Close_D_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_D_Click_Process();
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
        Locale locale = httpRequest.getLocale();

        // initialize pager control.
        _pager = new PagerModel(new Pager[] {
                pgr_U,
                pgr_D
        }, locale);

        // initialize lst_StationStatus.
        _lcm_lst_StationStatus = new ListCellModel(lst_StationStatus, LST_STATIONSTATUS_KEYS, locale);
        _lcm_lst_StationStatus.setToolTipVisible(KEY_LST_SELECT, true);
        _lcm_lst_StationStatus.setToolTipVisible(KEY_LST_STATION_NO, true);
        _lcm_lst_StationStatus.setToolTipVisible(KEY_LST_MODE_TYPE, true);
        _lcm_lst_StationStatus.setToolTipVisible(KEY_LST_CURRENT_MODE, true);
        _lcm_lst_StationStatus.setToolTipVisible(KEY_LST_STATUS, true);
        _lcm_lst_StationStatus.setToolTipVisible(KEY_LST_SUSPEND, true);
        _lcm_lst_StationStatus.setToolTipVisible(KEY_LST_WORK_COUNT, true);
        _lcm_lst_StationStatus.setToolTipVisible(KEY_LST_MODE_REQUEST, true);

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
        // dispose DASCH.
        disposeDasch();
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_StationStatus_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        // DFKLOOK ここから
        line.addToolTip("LBL-W0023", KEY_HIDDEN_STATION_NAME);
        // DFKLOOK ここまで
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(pgr_U);

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

        // dispose DASCH.
        disposeDasch();

        Connection conn = null;
        LstStationStatusDASCH dasch = null;
        // DFKLOOK セッション保持フラグ追加
        boolean isSuccess = false;
        // DFKLOOK ここまで
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstStationStatusDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstStationStatusDASCHParams inparam = new LstStationStatusDASCHParams();
            LstStationStatusParams requestParam = new LstStationStatusParams(request);
            inparam.set(LstStationStatusDASCHParams.MACHINE_STATUS, requestParam.get(LstStationStatusParams.STATION_NO));
            inparam.set(LstStationStatusDASCHParams.MODE_TYPE, requestParam.get(LstStationStatusParams.MODE_TYPE));
            inparam.set(LstStationStatusDASCHParams.CURRENT_MODE, requestParam.get(LstStationStatusParams.CURRENT_MODE));
            inparam.set(LstStationStatusDASCHParams.STATUS, requestParam.get(LstStationStatusParams.STATUS));
            inparam.set(LstStationStatusDASCHParams.SUSPEND, requestParam.get(LstStationStatusParams.SUSPEND));
            inparam.set(LstStationStatusDASCHParams.WORK_COUNT, requestParam.get(LstStationStatusParams.WORK_COUNT));
            inparam.set(LstStationStatusDASCHParams.WORK_MODE_CHANGE,
                    requestParam.get(LstStationStatusParams.WORK_MODE_CHANGE));
            inparam.set(LstStationStatusDASCHParams.STATION, requestParam.get(LstStationStatusParams.STATION));

            // get count.
            int count = dasch.count(inparam);
            _pager.setMax(count);
            _lcm_lst_StationStatus.clear();
            int startIndex = _pager.getIndex() - 1;

            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }
            else if (count > _pager.getMax())
            {
                message.setMsgResourceKey("6001023\t" + WmsFormatter.getNumFormat(count)
                        + "\t" + WmsFormatter.getNumFormat(_pager.getMax()));
            }
            else
            {
                message.setMsgResourceKey("6001022\t" + WmsFormatter.getNumFormat(count));
            }
            // DASCH call.
            dasch.search(inparam);

            // output display.
            List<Params> outparams = dasch.get(startIndex, _pager.getLinesPerPage());
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StationStatus.getNewLine();
                line.setValue(KEY_LST_SELECT, outparam.get(LstStationStatusDASCHParams.SELECT));
                line.setValue(KEY_LST_STATION_NO, outparam.get(LstStationStatusDASCHParams.STATION_NO));
                line.setValue(KEY_LST_MODE_TYPE, outparam.get(LstStationStatusDASCHParams.MODE_TYPE));
                line.setValue(KEY_LST_CURRENT_MODE, outparam.get(LstStationStatusDASCHParams.CURRENT_MODE));
                line.setValue(KEY_LST_STATUS, outparam.get(LstStationStatusDASCHParams.STATUS));
                line.setValue(KEY_LST_SUSPEND, outparam.get(LstStationStatusDASCHParams.SUSPEND));
                line.setValue(KEY_LST_WORK_COUNT, outparam.get(LstStationStatusDASCHParams.WORK_COUNT));
                line.setValue(KEY_LST_MODE_REQUEST, outparam.get(LstStationStatusDASCHParams.MODE_REQUEST));
                line.setValue(KEY_HIDDEN_STATION_NAME, outparam.get(LstStationStatusDASCHParams.STATION));
                line.setValue(KEY_HIDDEN_MODE_TYPE, outparam.get(LstStationStatusDASCHParams.HIDDEN_MODE_TYPE));
                line.setValue(KEY_HIDDEN_CURRENT_MODE, outparam.get(LstStationStatusDASCHParams.HIDDEN_CURRENT_MODE));
                line.setValue(KEY_HIDDEN_STATUS, outparam.get(LstStationStatusDASCHParams.HIDDEN_STATUS));
                line.setValue(KEY_HIDDEN_SUSPEND, outparam.get(LstStationStatusDASCHParams.HIDDEN_SUSPEND));
                line.setValue(KEY_HIDDEN_MODE_REQUEST, outparam.get(LstStationStatusDASCHParams.HIDDEN_MODE_REQUEST));
                lst_StationStatus_SetLineToolTip(line);
                _lcm_lst_StationStatus.add(line);
            }

            // save session.
            session.setAttribute(_KEY_DASCH_LIST, dasch);
            // DFKLOOK フラグ更新
            isSuccess = true;
            // DFKLOOK ここまで
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_StationStatus.clear();
        }
        finally
        {
            // DFKLOOK finally追加
            if (!isSuccess)
            {
                if (dasch != null)
                {
                    dasch.close();
                }
                DBUtil.close(conn);
            }
            // DFKLOOK ここまで
        }
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_SetList()
            throws Exception
    {
        LstStationStatusDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstStationStatusDASCH)session.getAttribute(_KEY_DASCH_LIST);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_StationStatus.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StationStatus.getNewLine();
                line.setValue(KEY_LST_SELECT, outparam.get(LstStationStatusDASCHParams.SELECT));
                line.setValue(KEY_LST_STATION_NO, outparam.get(LstStationStatusDASCHParams.STATION_NO));
                line.setValue(KEY_LST_MODE_TYPE, outparam.get(LstStationStatusDASCHParams.MODE_TYPE));
                line.setValue(KEY_LST_CURRENT_MODE, outparam.get(LstStationStatusDASCHParams.CURRENT_MODE));
                line.setValue(KEY_LST_STATUS, outparam.get(LstStationStatusDASCHParams.STATUS));
                line.setValue(KEY_LST_SUSPEND, outparam.get(LstStationStatusDASCHParams.SUSPEND));
                line.setValue(KEY_LST_WORK_COUNT, outparam.get(LstStationStatusDASCHParams.WORK_COUNT));
                line.setValue(KEY_LST_MODE_REQUEST, outparam.get(LstStationStatusDASCHParams.MODE_REQUEST));
                line.setValue(KEY_HIDDEN_STATION_NAME, outparam.get(LstStationStatusDASCHParams.STATION));
                line.setValue(KEY_HIDDEN_MODE_TYPE, outparam.get(LstStationStatusDASCHParams.HIDDEN_MODE_TYPE));
                line.setValue(KEY_HIDDEN_CURRENT_MODE, outparam.get(LstStationStatusDASCHParams.HIDDEN_CURRENT_MODE));
                line.setValue(KEY_HIDDEN_STATUS, outparam.get(LstStationStatusDASCHParams.HIDDEN_STATUS));
                line.setValue(KEY_HIDDEN_SUSPEND, outparam.get(LstStationStatusDASCHParams.HIDDEN_SUSPEND));
                line.setValue(KEY_HIDDEN_MODE_REQUEST, outparam.get(LstStationStatusDASCHParams.HIDDEN_MODE_REQUEST));
                lst_StationStatus_SetLineToolTip(line);
                _lcm_lst_StationStatus.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_StationStatus.clear();
            disposeDasch();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void disposeDasch()
            throws Exception
    {
        // disposing DASCH.
        DataAccessSCH dasch = (DataAccessSCH)session.getAttribute(_KEY_DASCH_LIST);
        if (dasch != null)
        {
            session.removeAttribute(_KEY_DASCH_LIST);
            dasch.close();
            // DFKLOOK コネクションクローズ追加
            DBUtil.close(dasch.getConnection());
            // DFKLOOK ここまで
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Close_U_Click_Process()
            throws Exception
    {
        parentRedirect(null);
        dispose();

    }

    /**
     *
     * @throws Exception
     */
    private void lst_Select_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_StationStatus.getActiveRow();
        lst_StationStatus.setCurrentRow(row);
        ListCellLine line = _lcm_lst_StationStatus.get(row);

        // output parameter.
        LstStationStatusParams outparam = new LstStationStatusParams();
        outparam.set(LstStationStatusParams.STATION_NO, line.getValue(KEY_LST_STATION_NO));
        outparam.set(LstStationStatusParams.MODE_TYPE, line.getValue(KEY_LST_MODE_TYPE));
        outparam.set(LstStationStatusParams.CURRENT_MODE, line.getValue(KEY_LST_CURRENT_MODE));
        outparam.set(LstStationStatusParams.STATUS, line.getValue(KEY_LST_STATUS));
        outparam.set(LstStationStatusParams.SUSPEND, line.getValue(KEY_LST_SUSPEND));
        outparam.set(LstStationStatusParams.WORK_COUNT, line.getValue(KEY_LST_WORK_COUNT));
        outparam.set(LstStationStatusParams.MODE_REQUEST, line.getValue(KEY_LST_MODE_REQUEST));
        outparam.set(LstStationStatusParams.STATION, line.getValue(KEY_HIDDEN_STATION_NAME));
        outparam.set(LstStationStatusParams.HIDDEN_MODE_TYPE_SET, line.getValue(KEY_HIDDEN_MODE_TYPE));
        outparam.set(LstStationStatusParams.HIDDEN_CURRENT_MODE_SET, line.getValue(KEY_HIDDEN_CURRENT_MODE));
        outparam.set(LstStationStatusParams.HIDDEN_STATUS_SET, line.getValue(KEY_HIDDEN_STATUS));
        outparam.set(LstStationStatusParams.HIDDEN_SUSPEND_SET, line.getValue(KEY_HIDDEN_SUSPEND));
        outparam.set(LstStationStatusParams.HIDDEN_MODE_REQUEST_SET, line.getValue(KEY_HIDDEN_MODE_REQUEST));

        ForwardParameters forwardParam = outparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, viewState.getString(_KEY_POPUPSOURCE));
        parentRedirect(forwardParam);
        dispose();

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Close_D_Click_Process()
            throws Exception
    {
        parentRedirect(null);
        dispose();

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
