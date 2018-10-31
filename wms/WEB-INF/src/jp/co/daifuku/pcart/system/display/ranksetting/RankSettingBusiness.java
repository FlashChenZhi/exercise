// $Id: RankSettingBusiness.java 7162 2010-02-19 09:32:59Z shibamoto $
package jp.co.daifuku.pcart.system.display.ranksetting;

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

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.pcart.system.schedule.RankSettingSCH;
import jp.co.daifuku.pcart.system.schedule.RankSettingSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;

/**
 * 作業基準値設定の画面処理を行います。
 *
 * @version $Revision: 7162 $, $Date:: 2010-02-19 18:32:59 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class RankSettingBusiness
        extends RankSetting
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

    /** lst_RankSet(HIDDEN_LAST_UP_DATE) */
    private static final ListCellKey KEY_HIDDEN_LAST_UP_DATE =
            new ListCellKey("HIDDEN_LAST_UP_DATE", new StringCellColumn(), false, false);

    /** lst_RankSet(LST_LEVEL) */
    private static final ListCellKey KEY_LST_LEVEL = new ListCellKey("LST_LEVEL", new StringCellColumn(), true, false);

    /** lst_RankSet(LST_LOT_COUNT) */
    private static final ListCellKey KEY_LST_LOT_COUNT =
            new ListCellKey("LST_LOT_COUNT", new StringCellColumn(), true, false);

    /** lst_RankSet(LST_ORDER_COUNT) */
    private static final ListCellKey KEY_LST_ORDER_COUNT =
            new ListCellKey("LST_ORDER_COUNT", new StringCellColumn(), true, false);

    /** lst_RankSet(LST_LINE_COUNT) */
    private static final ListCellKey KEY_LST_LINE_COUNT =
            new ListCellKey("LST_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_RankSet kyes */
    private static final ListCellKey[] LST_RANKSET_KEYS = {
            KEY_HIDDEN_LAST_UP_DATE,
            KEY_LST_LEVEL,
            KEY_LST_LOT_COUNT,
            KEY_LST_ORDER_COUNT,
            KEY_LST_LINE_COUNT,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** ListCellModel lst_RankSet */
    private ListCellModel _lcm_lst_RankSet;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RankSettingBusiness()
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
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
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
    public void btn_Display_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Display_Click_Process();
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
    public void btn_AutoCalculation_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AutoCalculation_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AutoCalculation_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ListClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ListClear_Click_Process();
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

        // initialize pul_Area.
        _pdm_pul_Area = new WmsAreaPullDownModel(pul_Area, locale, ui);

        // DFKLOOK:ここから修正
        // 一覧クリア時　MSG-W0015=全情報が取り消されます。よろしいですか？
        btn_ListClear.setBeforeConfirm("MSG-W0015");
        // DFKLOOK:ここまで修正

        // initialize lst_RankSet.
        _lcm_lst_RankSet = new ListCellModel(lst_RankSet, LST_RANKSET_KEYS, locale);
        _lcm_lst_RankSet.setToolTipVisible(KEY_LST_LEVEL, false);
        _lcm_lst_RankSet.setToolTipVisible(KEY_LST_LOT_COUNT, false);
        _lcm_lst_RankSet.setToolTipVisible(KEY_LST_ORDER_COUNT, false);
        _lcm_lst_RankSet.setToolTipVisible(KEY_LST_LINE_COUNT, false);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_RankSet_SetLineToolTip(ListCellLine line)
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
        setFocus(txt_ConsignorCode);

    }

    // DFKLOOK:ここから修正
    /**
     *
     * @throws Exception
     */
    private void rank_Disp()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RankSettingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RankSettingSCH(conn, this.getClass(), locale, ui);

            // ランク基準値表示
            // SCH call.
            Params pr = sch.rankDisp(new ScheduleParams());

            // output display.
            if (pr != null)
            {
                lbl_PercentRankA.setValue(pr.getString(RankSettingSCHParams.A_RANK_STANDARD_VALUE));
                lbl_PercentRankB.setValue(pr.getString(RankSettingSCHParams.B_RANK_STANDARD_VALUE));
            }


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
            DBUtil.close(conn);
        }

    }

    // DFKLOOK:ここまで修正

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        lbl_ConsignorCode.setValue(null);
        _pdm_pul_Area.setSelectedValue(null);
        btn_Set.setEnabled(false);
        btn_AutoCalculation.setEnabled(false);
        btn_ListClear.setEnabled(false);

        // DFKLOOK:ここから修正
        // 初期表示時、荷主検索を行う
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RankSettingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RankSettingSCH(conn, this.getClass(), locale, ui);

            // SCH call.
            Params p = sch.initFind(new ScheduleParams());

            // output display.
            if (p != null)
            {
                txt_ConsignorCode.setValue(p.get(RankSettingSCHParams.CONSIGNOR_CODE));
            }

            // ランク基準値の表示
            rank_Disp();
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
            DBUtil.close(conn);
        }
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {

        txt_LotPerH.setValue(null);
        txt_OrderPerH.setValue(null);
        txt_LinePerH.setValue(null);
        txt_ConsignorCode.validate(this, true);
        pul_Area.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RankSettingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RankSettingSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RankSettingSCHParams inparam = new RankSettingSCHParams();
            inparam.set(RankSettingSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(RankSettingSCHParams.AREA, _pdm_pul_Area.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            if (outparams.size() == 0)
            {
                // 荷主コードがマスタに登録されていない場合のみボタンを無効化する。 
                btn_Set.setEnabled(false);
                btn_AutoCalculation.setEnabled(false);
                btn_ListClear.setEnabled(false);

                // set focus.
                setFocus(txt_ConsignorCode);
            }
            else
            {
                // ボタンを有効化する。    
                btn_Set.setEnabled(true);
                btn_AutoCalculation.setEnabled(true);
                btn_ListClear.setEnabled(true);

                // set focus.
                setFocus(txt_LotPerH);
            }
            // DFKLOOK:ここまで修正

            // output display.
            _lcm_lst_RankSet.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RankSet.getNewLine();


                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                viewState.setObject(ViewStateKeys.VS_AREA, _pdm_pul_Area.getSelectedValue());

                // DFKLOOK:ここから修正
                // 対象データ無し
                if (StringUtil.isBlank(outparam.getString(RankSettingSCHParams.LAST_UP_DATE)))
                {
                    break;
                }
                // DFKLOOK:ここまで修正
                line.setValue(KEY_HIDDEN_LAST_UP_DATE, outparam.get(RankSettingSCHParams.LAST_UP_DATE));
                line.setValue(KEY_LST_LEVEL, outparam.get(RankSettingSCHParams.LEVEL));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(RankSettingSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(RankSettingSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(RankSettingSCHParams.LINE_COUNT));
                lst_RankSet_SetLineToolTip(line);
                _lcm_lst_RankSet.add(line);

            }

            // DFKLOOK:ここから修正
            // ランク基準値を表示
            rank_Disp();

            lbl_ConsignorCodeDisp.setValue(txt_ConsignorCode.getValue());
            lbl_AreaDisp.setText(pul_Area.getSelectedItem().getText());
            // DFKLOOK:ここまで修正
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
        txt_ConsignorCode.setValue(null);
        _pdm_pul_Area.setSelectedValue(null);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    {
        txt_LotPerH.validate(this, true);
        txt_OrderPerH.validate(this, true);
        txt_LinePerH.validate(this, true);

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
        RankSettingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RankSettingSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            RankSettingSCHParams lineparam = new RankSettingSCHParams();

            // DFKLOOK:ここから修正
            // 更新対象データ無しの場合、新規登録を行う
            if (_lcm_lst_RankSet.size() == 0)
            {
                lineparam.set(RankSettingSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
                lineparam.set(RankSettingSCHParams.AREA, viewState.getObject(ViewStateKeys.VS_AREA));
                lineparam.set(RankSettingSCHParams.S_LOT_COUNT, txt_LotPerH.getValue());
                lineparam.set(RankSettingSCHParams.S_LINE_COUNT, txt_LinePerH.getValue());
                lineparam.set(RankSettingSCHParams.S_ORDER_COUNT, txt_OrderPerH.getValue());
                inparamList.add(lineparam);
            }
            // DFKLOOK:ここまで修正

            for (int i = 1; i <= _lcm_lst_RankSet.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_RankSet.get(i);

                lineparam = new RankSettingSCHParams();

                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(RankSettingSCHParams.LAST_UP_DATE, line.getValue(KEY_HIDDEN_LAST_UP_DATE));
                lineparam.set(RankSettingSCHParams.LEVEL, line.getValue(KEY_LST_LEVEL));
                lineparam.set(RankSettingSCHParams.LOT_COUNT, line.getValue(KEY_LST_LOT_COUNT));
                lineparam.set(RankSettingSCHParams.ORDER_COUNT, line.getValue(KEY_LST_ORDER_COUNT));
                lineparam.set(RankSettingSCHParams.LINE_COUNT, line.getValue(KEY_LST_LINE_COUNT));
                lineparam.set(RankSettingSCHParams.S_LOT_COUNT, txt_LotPerH.getValue());
                lineparam.set(RankSettingSCHParams.S_LINE_COUNT, txt_LinePerH.getValue());
                lineparam.set(RankSettingSCHParams.S_ORDER_COUNT, txt_OrderPerH.getValue());
                lineparam.set(RankSettingSCHParams.AREA, viewState.getObject(ViewStateKeys.VS_AREA));
                lineparam.set(RankSettingSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highligiting error row.
                _lcm_lst_RankSet.resetEditRow();
                _lcm_lst_RankSet.resetHighlight();
                _lcm_lst_RankSet.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_RankSet.resetEditRow();
            _lcm_lst_RankSet.resetHighlight();

            // set input parameters.
            RankSettingSCHParams inparam = new RankSettingSCHParams();
            inparam.set(RankSettingSCHParams.AREA, viewState.getObject(ViewStateKeys.VS_AREA));
            inparam.set(RankSettingSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_RankSet.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RankSet.getNewLine();
                line.setValue(KEY_LST_LEVEL, outparam.get(RankSettingSCHParams.LEVEL));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(RankSettingSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(RankSettingSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(RankSettingSCHParams.LINE_COUNT));
                line.setValue(KEY_HIDDEN_LAST_UP_DATE, outparam.get(RankSettingSCHParams.LAST_UP_DATE));
                lst_RankSet_SetLineToolTip(line);
                _lcm_lst_RankSet.add(line);
            }

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
    private void btn_AutoCalculation_Click_Process()
            throws Exception
    {
        txt_LotPerH.setValue(null);
        txt_OrderPerH.setValue(null);
        txt_LinePerH.setValue(null);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RankSettingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RankSettingSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RankSettingSCHParams inparam = new RankSettingSCHParams();
            inparam.set(RankSettingSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(RankSettingSCHParams.AREA, _pdm_pul_Area.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.autoCalculation(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RankSet.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RankSet.getNewLine();

                // DFKLOOK:ここから修正
                line.setValue(KEY_HIDDEN_LAST_UP_DATE, outparam.get(RankSettingSCHParams.LAST_UP_DATE));
                line.setValue(KEY_LST_LEVEL, outparam.get(RankSettingSCHParams.LEVEL));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(RankSettingSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(RankSettingSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(RankSettingSCHParams.LINE_COUNT));


                lst_RankSet_SetLineToolTip(line);
                _lcm_lst_RankSet.add(line);
            }


            Params param = new Params();
            param = outparams.get(0);
            // 下段入力項目を自動表示する
            lbl_PercentRankA.setValue(param.getString(RankSettingSCHParams.A_RANK_STANDARD_VALUE));
            lbl_PercentRankB.setValue(param.getString(RankSettingSCHParams.B_RANK_STANDARD_VALUE));

            param = outparams.get(1);
            // ロット数/h
            txt_LotPerH.setValue(Double.valueOf(param.getString(RankSettingSCHParams.LOT_COUNT)));
            // オーダー数/h
            txt_OrderPerH.setValue(Double.valueOf(param.getString(RankSettingSCHParams.ORDER_COUNT)));
            // 行数/h
            txt_LinePerH.setValue(Double.valueOf(param.getString(RankSettingSCHParams.LINE_COUNT)));
            // DFKLOOK:ここまで修正

            // clear.
            btn_Set.setEnabled(true);
            btn_AutoCalculation.setEnabled(true);
            btn_ListClear.setEnabled(true);

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
            DBUtil.close(conn);
        }
    }


    /**
     *
     * @throws Exception
     */
    private void btn_ListClear_Click_Process()
            throws Exception
    {

        // clear.
        _lcm_lst_RankSet.clear();
        btn_Set.setEnabled(false);
        btn_AutoCalculation.setEnabled(false);
        btn_ListClear.setEnabled(false);
        txt_LotPerH.setValue(null);
        txt_OrderPerH.setValue(null);
        txt_LinePerH.setValue(null);
        lbl_AreaDisp.setValue(null);
        lbl_ConsignorCodeDisp.setValue(null);

        // DFKLOOK:ここから修正
        // ランク基準値を表示
        rank_Disp();
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
