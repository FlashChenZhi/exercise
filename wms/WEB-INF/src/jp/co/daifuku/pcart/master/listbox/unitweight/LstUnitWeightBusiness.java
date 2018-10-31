// $Id: LstUnitWeightBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.pcart.master.listbox.unitweight;

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
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.pcart.master.dasch.LstUnitWeightDASCH;
import jp.co.daifuku.pcart.master.dasch.LstUnitWeightDASCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * 単重量一覧の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class LstUnitWeightBusiness
        extends LstUnitWeight
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_SearchConditionTwoColumn(LST_SEARCH_CONDITION_1) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_1 =
            new ListCellKey("LST_SEARCH_CONDITION_1", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_SEARCH_CONDITION_2) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_2 =
            new ListCellKey("LST_SEARCH_CONDITION_2", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_SEARCH_CONDITION_3) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_3 =
            new ListCellKey("LST_SEARCH_CONDITION_3", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_SEARCH_CONDITION_4) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_4 =
            new ListCellKey("LST_SEARCH_CONDITION_4", new StringCellColumn(), true, false);

    /** lst_UnitWeight(LST_SINGLE_WEIGHT) */
    private static final ListCellKey KEY_LST_SINGLE_WEIGHT =
            new ListCellKey("LST_SINGLE_WEIGHT", new NumberCellColumn(0), true, false);

    /** lst_UnitWeight(LST_DISTINCT_RATE) */
    private static final ListCellKey KEY_LST_WEIGHT_DISTINCT_RATE =
            new ListCellKey("LST_DISTINCT_RATE", new NumberCellColumn(0), true, false);

    /** lst_UnitWeight(LST_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_LST_CONSIGNOR_CODE =
            new ListCellKey("LST_CONSIGNOR_CODE", new StringCellColumn(), true, false);

    /** lst_UnitWeight(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE =
            new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_UnitWeight(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME =
            new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_UnitWeight(LST_LOT_QTY) */
    private static final ListCellKey KEY_LST_LOT_QTY =
            new ListCellKey("LST_LOT_QTY", new NumberCellColumn(0), true, false);

    /** lst_SearchConditionTwoColumn kyes */
    private static final ListCellKey[] LST_SEARCHCONDITIONTWOCOLUMN_KEYS = {
            KEY_LST_SEARCH_CONDITION_1,
            KEY_LST_SEARCH_CONDITION_2,
            KEY_LST_SEARCH_CONDITION_3,
            KEY_LST_SEARCH_CONDITION_4,
    };

    /** lst_UnitWeight kyes */
    private static final ListCellKey[] LST_UNITWEIGHT_KEYS = {
            KEY_LST_SINGLE_WEIGHT,
            KEY_LST_WEIGHT_DISTINCT_RATE,
            KEY_LST_CONSIGNOR_CODE,
            KEY_LST_ITEM_CODE,
            KEY_LST_ITEM_NAME,
            KEY_LST_LOT_QTY,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_SearchConditionTwoColumn */
    private ListCellModel _lcm_lst_SearchConditionTwoColumn;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_UnitWeight */
    private ListCellModel _lcm_lst_UnitWeight;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstUnitWeightBusiness()
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

    // DFKLOOK:ここから修正
    /**
     * 検索条件のリストセルを作成します。
     * 
     * @param param 検索条件
     * @param conn コネクション
     * @throws Exception 全ての例外を報告します。
     */
    protected void lst_SearchCondition_Make(LstUnitWeightDASCHParams param)
            throws Exception
    {

        ListCellLine line = _lcm_lst_SearchConditionTwoColumn.getNewLine();
        // 下限重量
        line.setValue(KEY_LST_SEARCH_CONDITION_1, DisplayText.getText("LBL-P0188"));
        // 上限重量
        line.setValue(KEY_LST_SEARCH_CONDITION_3, DisplayText.getText("LBL-P0189"));

        if (!StringUtil.isBlank(param.getString(LstUnitWeightDASCHParams.FROM_SINGLE_WEIGHT))
                && !StringUtil.isBlank(param.getString(LstUnitWeightDASCHParams.TO_SINGLE_WEIGHT)))
        {
            int comparison =
                    param.getString(LstUnitWeightDASCHParams.FROM_SINGLE_WEIGHT).compareTo(
                            param.getString(LstUnitWeightDASCHParams.TO_SINGLE_WEIGHT));
            if (0 < comparison)
            {
                line.setValue(
                        KEY_LST_SEARCH_CONDITION_2,
                        WmsFormatter.getNumFormat(Double.parseDouble(param.getString(LstUnitWeightDASCHParams.TO_SINGLE_WEIGHT))));
                line.setValue(
                        KEY_LST_SEARCH_CONDITION_4,
                        WmsFormatter.getNumFormat(Double.parseDouble(param.getString(LstUnitWeightDASCHParams.FROM_SINGLE_WEIGHT))));
            }
            else
            {
                line.setValue(
                        KEY_LST_SEARCH_CONDITION_2,
                        WmsFormatter.getNumFormat(Double.parseDouble(param.getString(LstUnitWeightDASCHParams.FROM_SINGLE_WEIGHT))));
                line.setValue(
                        KEY_LST_SEARCH_CONDITION_4,
                        WmsFormatter.getNumFormat(Double.parseDouble(param.getString(LstUnitWeightDASCHParams.TO_SINGLE_WEIGHT))));
            }
        }
        else
        {
            if (!StringUtil.isBlank(param.getString(LstUnitWeightDASCHParams.FROM_SINGLE_WEIGHT)))
            {
                line.setValue(
                        KEY_LST_SEARCH_CONDITION_2,
                        WmsFormatter.getNumFormat(Double.parseDouble(param.getString(LstUnitWeightDASCHParams.FROM_SINGLE_WEIGHT))));
            }
            else if (!StringUtil.isBlank(param.getString(LstUnitWeightDASCHParams.TO_SINGLE_WEIGHT)))
            {
                line.setValue(
                        KEY_LST_SEARCH_CONDITION_4,
                        WmsFormatter.getNumFormat(Double.parseDouble(param.getString(LstUnitWeightDASCHParams.TO_SINGLE_WEIGHT))));
            }
        }
        _lcm_lst_SearchConditionTwoColumn.add(line);
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
        Locale locale = httpRequest.getLocale();

        // initialize lst_SearchConditionTwoColumn.
        _lcm_lst_SearchConditionTwoColumn =
                new ListCellModel(lst_SearchConditionTwoColumn, LST_SEARCHCONDITIONTWOCOLUMN_KEYS, locale);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_SEARCH_CONDITION_1, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_SEARCH_CONDITION_2, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_SEARCH_CONDITION_3, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_SEARCH_CONDITION_4, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[] {
                pgr_U,
                pgr_D
        }, locale);

        // initialize lst_UnitWeight.
        _lcm_lst_UnitWeight = new ListCellModel(lst_UnitWeight, LST_UNITWEIGHT_KEYS, locale);
        _lcm_lst_UnitWeight.setToolTipVisible(KEY_LST_SINGLE_WEIGHT, false);
        _lcm_lst_UnitWeight.setToolTipVisible(KEY_LST_WEIGHT_DISTINCT_RATE, false);
        _lcm_lst_UnitWeight.setToolTipVisible(KEY_LST_CONSIGNOR_CODE, false);
        _lcm_lst_UnitWeight.setToolTipVisible(KEY_LST_ITEM_CODE, false);
        _lcm_lst_UnitWeight.setToolTipVisible(KEY_LST_ITEM_NAME, false);
        _lcm_lst_UnitWeight.setToolTipVisible(KEY_LST_LOT_QTY, false);

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
    private void lst_UnitWeight_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
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
        LstUnitWeightDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstUnitWeightDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstUnitWeightDASCHParams inparam = new LstUnitWeightDASCHParams();
            LstUnitWeightParams requestParam = new LstUnitWeightParams(request);
            inparam.set(LstUnitWeightDASCHParams.FROM_SINGLE_WEIGHT,
                    requestParam.get(LstUnitWeightParams.FROM_SINGLE_WEIGHT));
            inparam.set(LstUnitWeightDASCHParams.TO_SINGLE_WEIGHT,
                    requestParam.get(LstUnitWeightParams.TO_SINGLE_WEIGHT));

            // DFKLOOK:ここから修正
            // 検索条件のリストセルを作成します。
            lst_SearchCondition_Make(inparam);

            // DFKLOOK:ここまで修正

            // get count.
            int count = dasch.count(inparam);
            _pager.setMax(count);
            _lcm_lst_UnitWeight.clear();

            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }
            else if (count > _pager.getMax())
            {
                message.setMsgResourceKey("6001023\t" + Formatter.getNumFormat(count) + "\t"
                        + Formatter.getNumFormat(_pager.getMax()));
            }
            else
            {
                message.setMsgResourceKey("6001022\t" + Formatter.getNumFormat(count));
            }

            // DASCH call.
            dasch.search(inparam);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_UnitWeight.getNewLine();
                line.setValue(KEY_LST_SINGLE_WEIGHT, outparam.get(LstUnitWeightDASCHParams.SINGLE_WEIGHT));
                line.setValue(KEY_LST_WEIGHT_DISTINCT_RATE, outparam.get(LstUnitWeightDASCHParams.WEIGHT_DISTINCT_RATE));
                line.setValue(KEY_LST_CONSIGNOR_CODE, outparam.get(LstUnitWeightDASCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstUnitWeightDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstUnitWeightDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(LstUnitWeightDASCHParams.LOT_QTY));
                lst_UnitWeight_SetLineToolTip(line);
                _lcm_lst_UnitWeight.add(line);
            }

            // save session.
            session.setAttribute(_KEY_DASCH, dasch);
            isSuccess = true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_UnitWeight.clear();
            if (dasch != null)
            {
                dasch.close();
            }
            DBUtil.close(conn);
        }
        finally
        {
            if (!isSuccess)
            {
                if (dasch != null)
                {
                    dasch.close();
                }
                DBUtil.close(conn);
            }
        }
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_SetList()
            throws Exception
    {
        LstUnitWeightDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstUnitWeightDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            _lcm_lst_UnitWeight.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_UnitWeight.getNewLine();
                line.setValue(KEY_LST_SINGLE_WEIGHT, outparam.get(LstUnitWeightDASCHParams.SINGLE_WEIGHT));
                line.setValue(KEY_LST_WEIGHT_DISTINCT_RATE, outparam.get(LstUnitWeightDASCHParams.WEIGHT_DISTINCT_RATE));
                line.setValue(KEY_LST_CONSIGNOR_CODE, outparam.get(LstUnitWeightDASCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstUnitWeightDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstUnitWeightDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(LstUnitWeightDASCHParams.LOT_QTY));
                lst_UnitWeight_SetLineToolTip(line);
                _lcm_lst_UnitWeight.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_UnitWeight.clear();
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
        DataAccessSCH dasch = (DataAccessSCH)session.getAttribute(_KEY_DASCH);
        if (dasch != null)
        {
            session.removeAttribute(_KEY_DASCH);
            dasch.close();
            DBUtil.close(dasch.getConnection());
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
