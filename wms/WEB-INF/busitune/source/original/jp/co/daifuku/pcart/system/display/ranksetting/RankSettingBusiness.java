// $Id: RankSettingBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
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
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.bluedog.webapp.ViewState;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.pcart.system.display.ranksetting.RankSetting;
import jp.co.daifuku.pcart.system.display.ranksetting.ViewStateKeys;
import jp.co.daifuku.pcart.system.schedule.RankSettingSCH;
import jp.co.daifuku.pcart.system.schedule.RankSettingSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class RankSettingBusiness
        extends RankSetting
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_RankSet(HIDDEN_LAST_UP_DATE) */
    private static final ListCellKey KEY_HIDDEN_LAST_UP_DATE = new ListCellKey("HIDDEN_LAST_UP_DATE", new StringCellColumn(), false, false);

    /** lst_RankSet(LST_LEVEL) */
    private static final ListCellKey KEY_LST_LEVEL = new ListCellKey("LST_LEVEL", new StringCellColumn(), true, false);

    /** lst_RankSet(LST_LOT_COUNT) */
    private static final ListCellKey KEY_LST_LOT_COUNT = new ListCellKey("LST_LOT_COUNT", new NumberCellColumn(0), true, false);

    /** lst_RankSet(LST_ORDER_COUNT) */
    private static final ListCellKey KEY_LST_ORDER_COUNT = new ListCellKey("LST_ORDER_COUNT", new NumberCellColumn(0), true, false);

    /** lst_RankSet(LST_LINE_COUNT) */
    private static final ListCellKey KEY_LST_LINE_COUNT = new ListCellKey("LST_LINE_COUNT", new NumberCellColumn(0), true, false);

    /** lst_RankSet keys */
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
    private DefaultPullDownModel _pdm_pul_Area;

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
        btn_Set_Click_Process();
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
        _pdm_pul_Area = new DefaultPullDownModel(pul_Area, locale, ui);

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
            _pdm_pul_Area.init(conn);
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

        Connection conn = null;
        RankSettingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RankSettingSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RankSettingSCHParams inparam = new RankSettingSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_ConsignorCode.setValue(outparam.get(RankSettingSCHParams.CONSIGNOR_CODE));
            }

            // clear.
            _pdm_pul_Area.setSelectedValue(null);
            txt_LotPerH.setValue(null);
            txt_OrderPerH.setValue(null);
            txt_LinePerH.setValue(null);
            lbl_PercentRankA.setValue(null);
            lbl_PercentRankB.setValue(null);
            lbl_ConsignorCodeDisp.setValue(null);
            lbl_AreaDisp.setValue(null);
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
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
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

            // output display.
            _lcm_lst_RankSet.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RankSet.getNewLine();
                line.setValue(KEY_HIDDEN_LAST_UP_DATE, outparam.get(RankSettingSCHParams.LAST_UP_DATE));
                line.setValue(KEY_LST_LEVEL, outparam.get(RankSettingSCHParams.LEVEL));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(RankSettingSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(RankSettingSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(RankSettingSCHParams.LINE_COUNT));
                viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                viewState.setObject(ViewStateKeys.AREA, _pdm_pul_Area.getSelectedValue());
                lbl_ConsignorCodeDisp.setValue(outparam.get(RankSettingSCHParams.CONSIGNOR_CODE_DISP));
                lbl_AreaDisp.setValue(outparam.get(RankSettingSCHParams.AREA_DISP));
                lbl_PercentRankA.setValue(outparam.get(RankSettingSCHParams.A_RANK_STANDARD_VALUE));
                lbl_PercentRankB.setValue(outparam.get(RankSettingSCHParams.B_RANK_STANDARD_VALUE));
                lst_RankSet_SetLineToolTip(line);
                _lcm_lst_RankSet.add(line);
            }

            // clear.
            btn_Set.setEnabled(true);
            btn_AutoCalculation.setEnabled(true);
            btn_ListClear.setEnabled(true);
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
    private void btn_Set_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_RankSet.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_RankSet.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003001");
            return;
        }

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
            for (int i = 1; i <= _lcm_lst_RankSet.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_RankSet.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                RankSettingSCHParams lineparam = new RankSettingSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(RankSettingSCHParams.LAST_UP_DATE, line.getValue(KEY_HIDDEN_LAST_UP_DATE));
                lineparam.set(RankSettingSCHParams.LEVEL, line.getValue(KEY_LST_LEVEL));
                lineparam.set(RankSettingSCHParams.LOT_COUNT, line.getValue(KEY_LST_LOT_COUNT));
                lineparam.set(RankSettingSCHParams.ORDER_COUNT, line.getValue(KEY_LST_ORDER_COUNT));
                lineparam.set(RankSettingSCHParams.LINE_COUNT, line.getValue(KEY_LST_LINE_COUNT));
                lineparam.set(RankSettingSCHParams.AREA, viewState.getObject(ViewStateKeys.AREA));
                lineparam.set(RankSettingSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
                lineparam.set(RankSettingSCHParams.S_LOT_COUNT, txt_LotPerH.getValue());
                lineparam.set(RankSettingSCHParams.S_ORDER_COUNT, txt_OrderPerH.getValue());
                lineparam.set(RankSettingSCHParams.S_LINE_COUNT, txt_LinePerH.getValue());
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

                // reset editing row or highlighting error row.
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
            inparam.set(RankSettingSCHParams.AREA, viewState.getObject(ViewStateKeys.AREA));
            inparam.set(RankSettingSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_RankSet.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RankSet.getNewLine();
                line.setValue(KEY_HIDDEN_LAST_UP_DATE, outparam.get(RankSettingSCHParams.LAST_UP_DATE));
                line.setValue(KEY_LST_LEVEL, outparam.get(RankSettingSCHParams.LEVEL));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(RankSettingSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(RankSettingSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(RankSettingSCHParams.LINE_COUNT));
                lst_RankSet_SetLineToolTip(line);
                _lcm_lst_RankSet.add(line);
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
    private void btn_AutoCalculation_Click_Process()
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

            // set input parameters.
            RankSettingSCHParams inparam = new RankSettingSCHParams();
            inparam.set(RankSettingSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(RankSettingSCHParams.AREA, _pdm_pul_Area.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RankSet.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RankSet.getNewLine();
                line.setValue(KEY_HIDDEN_LAST_UP_DATE, outparam.get(RankSettingSCHParams.LAST_UP_DATE));
                line.setValue(KEY_LST_LEVEL, outparam.get(RankSettingSCHParams.LEVEL));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(RankSettingSCHParams.S_LOT_COUNT));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(RankSettingSCHParams.S_ORDER_COUNT));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(RankSettingSCHParams.S_LINE_COUNT));
                lbl_PercentRankA.setValue(outparam.get(RankSettingSCHParams.A_RANK_STANDARD_VALUE));
                lbl_PercentRankB.setValue(outparam.get(RankSettingSCHParams.B_RANK_STANDARD_VALUE));
                lst_RankSet_SetLineToolTip(line);
                _lcm_lst_RankSet.add(line);
            }

            // clear.
            txt_LotPerH.setValue(null);
            txt_OrderPerH.setValue(null);
            txt_LinePerH.setValue(null);
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
