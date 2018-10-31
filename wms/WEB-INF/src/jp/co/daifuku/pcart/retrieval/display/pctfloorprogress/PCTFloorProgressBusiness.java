// $Id: PCTFloorProgressBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.pcart.retrieval.display.pctfloorprogress;

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
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.base.controller.PCTConsignorController;
import jp.co.daifuku.pcart.retrieval.schedule.PCTAreaProgressGraphSCHParams;
import jp.co.daifuku.pcart.retrieval.schedule.PCTFloorProgressSCH;
import jp.co.daifuku.pcart.retrieval.schedule.PCTFloorProgressSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * エリア別作業進捗(グラフなし)の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date:: 2011-07-06 09:52:24 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class PCTFloorProgressBusiness
        extends PCTFloorProgress
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_PCTAreaProgress(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO =
            new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_PCTAreaProgress(LST_ZONE_NO) */
    private static final ListCellKey KEY_LST_ZONE_NO =
            new ListCellKey("LST_ZONE_NO", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress(LST_PROGRESS_RATE) */
    private static final ListCellKey KEY_LST_PROGRESS_RATE =
            new ListCellKey("LST_PROGRESS_RATE", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress(LST_LINE_COUNT) */
    private static final ListCellKey KEY_LST_LINE_COUNT =
            new ListCellKey("LST_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress(LST_LOT_COUNT) */
    private static final ListCellKey KEY_LST_LOT_COUNT =
            new ListCellKey("LST_LOT_COUNT", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress kyes */
    private static final ListCellKey[] LST_PCTAREAPROGRESS_KEYS = {
            KEY_LST_AREA_NO,
            KEY_LST_ZONE_NO,
            KEY_LST_PROGRESS_RATE,
            KEY_LST_LINE_COUNT,
            KEY_LST_LOT_COUNT,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** RadioButtonGroupModel ProgressDisplay */
    private RadioButtonGroup _grp_ProgressDisplay;

    /** ListCellModel lst_PCTAreaProgress */
    private ListCellModel _lcm_lst_PCTAreaProgress;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTFloorProgressBusiness()
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
    public void page_ClientPull(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから修正
        // input validation.
        txt_ConsignorCode.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_BatchSeqNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTFloorProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTFloorProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTFloorProgressSCHParams inparam = new PCTFloorProgressSCHParams();
            inparam.set(PCTFloorProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTFloorProgressSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(PCTFloorProgressSCHParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK ここから修正
            if (outparams == null || outparams.size() == 0)
            {
                // 表示データを削除
                // output display.
                _lcm_lst_PCTAreaProgress.clear();

                // 6003011=対象データはありませんでした。
                message.setMsgResourceKey("6003011");
                return;
            }
            // DFKLOOK ここまで修正

            // output display.
            _lcm_lst_PCTAreaProgress.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTAreaProgress.getNewLine();
                line.setValue(KEY_LST_AREA_NO, outparam.get(PCTFloorProgressSCHParams.AREA_NO));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(PCTFloorProgressSCHParams.ZONE_NO));
                line.setValue(KEY_LST_PROGRESS_RATE, outparam.get(PCTFloorProgressSCHParams.PROGRESS_RATE));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(PCTFloorProgressSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTFloorProgressSCHParams.LOT_COUNT));
                lst_PCTAreaProgress_SetLineToolTip(line);
                _lcm_lst_PCTAreaProgress.add(line);
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
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ConsignorCode_EnterKey(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから修正
        // get locale.
        Connection conn = null;
        PCTConsignorController conController = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            conController = new PCTConsignorController(conn, this.getClass());
            txt_ConsignorName.setText(conController.getConsignorName(txt_ConsignorCode.getText(),
                    InParameter.SEARCH_TABLE_MASTER));

            setRegularTransmission();

            setFocus(txt_BatchNo);
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
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Auto_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから修正
        // clear.
        rdo_Auto.setChecked(true);

        // 定期送信フラグの入れ替え
        setRegularTransmission();

        // 必須入力チェック
        txt_ConsignorCode.validate(this, true);
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Manual_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから修正
        rdo_Manual.setChecked(true);

        // 定期送信フラグの入れ替え
        setRegularTransmission();

        // 必須入力チェック
        txt_ConsignorCode.validate(this, true);
        // DFKLOOK ここまで修正
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
    // DFKLOOK ここから修正
    /**
     * 自動更新か手動更新かを切り替えます。
     * @throws Exception 全ての例外を報告します。
     */
    protected void setRegularTransmission()
            throws Exception

    {
        // 定期送信フラグの切り替えを行います。
        if (rdo_Auto.getChecked())
        {
            setRegularTransmission(true);
        }
        else if (rdo_Manual.getChecked())
        {
            setRegularTransmission(false);
        }
    }

    // DFKLOOK ここまで修正

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

        // initialize ProgressDisplay.
        _grp_ProgressDisplay = new RadioButtonGroup(new RadioButton[] {
                rdo_Auto,
                rdo_Manual
        }, locale);

        // initialize lst_PCTAreaProgress.
        _lcm_lst_PCTAreaProgress = new ListCellModel(lst_PCTAreaProgress, LST_PCTAREAPROGRESS_KEYS, locale);
        _lcm_lst_PCTAreaProgress.setToolTipVisible(KEY_LST_AREA_NO, false);
        _lcm_lst_PCTAreaProgress.setToolTipVisible(KEY_LST_ZONE_NO, false);
        _lcm_lst_PCTAreaProgress.setToolTipVisible(KEY_LST_PROGRESS_RATE, false);
        _lcm_lst_PCTAreaProgress.setToolTipVisible(KEY_LST_LINE_COUNT, false);
        _lcm_lst_PCTAreaProgress.setToolTipVisible(KEY_LST_LOT_COUNT, false);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTAreaProgress_SetLineToolTip(ListCellLine line)
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
        PCTFloorProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTFloorProgressSCH(conn, this.getClass(), locale, ui);

            // DFKLOOK ここから修正          
            // set input parameters.
            PCTFloorProgressSCHParams inparam = new PCTFloorProgressSCHParams();

            // SCH call.
            Params initOutparam = sch.initFind(inparam);

            if (initOutparam == null)
            {
                _lcm_lst_PCTAreaProgress.clear();
                rdo_Manual.setChecked(true);
                setRegularTransmission();
            }
            else
            {
                txt_ConsignorCode.setValue(initOutparam.get(PCTAreaProgressGraphSCHParams.CONSIGNOR_CODE));
                txt_ConsignorName.setValue(initOutparam.get(PCTAreaProgressGraphSCHParams.CONSIGNOR_NAME));

                // 荷主コードがあれば、リストセルを初期表示する
                if (!StringUtil.isBlank(txt_ConsignorCode.getText()))
                {
                    // DFKLOOK ここまで修正

                    // set input parameters.
                    inparam = new PCTFloorProgressSCHParams();
                    inparam.set(PCTFloorProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                    inparam.set(PCTFloorProgressSCHParams.BATCH_NO, txt_BatchNo.getValue());
                    inparam.set(PCTFloorProgressSCHParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());

                    // SCH call.
                    List<Params> outparams = sch.query(inparam);
                    message.setMsgResourceKey(sch.getMessage());

                    // DFKLOOK ここから修正
                    if (outparams == null || outparams.size() == 0)
                    {
                        // 表示データを削除
                        _lcm_lst_PCTAreaProgress.clear();

                        // 6003011=対象データはありませんでした。
                        message.setMsgResourceKey("6003011");
                        return;
                    }
                    // DFKLOOK ここまで修正

                    // output display.
                    _lcm_lst_PCTAreaProgress.clear();
                    for (Params outparam : outparams)
                    {
                        ListCellLine line = _lcm_lst_PCTAreaProgress.getNewLine();
                        line.setValue(KEY_LST_AREA_NO, outparam.get(PCTFloorProgressSCHParams.AREA_NO));
                        line.setValue(KEY_LST_ZONE_NO, outparam.get(PCTFloorProgressSCHParams.ZONE_NO));
                        line.setValue(KEY_LST_PROGRESS_RATE, outparam.get(PCTFloorProgressSCHParams.PROGRESS_RATE));
                        line.setValue(KEY_LST_LINE_COUNT, outparam.get(PCTFloorProgressSCHParams.LINE_COUNT));
                        line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTFloorProgressSCHParams.LOT_COUNT));
                        // DFKLOOK ここから修正
                        //                txt_ConsignorName.setValue(outparam.get(PCTFloorProgressSCHParams.CONSIGNOR_NAME));
                        // DFKLOOK ここまで修正
                        lst_PCTAreaProgress_SetLineToolTip(line);
                        _lcm_lst_PCTAreaProgress.add(line);
                    }
                    // DFKLOOK ここから修正
                }
                // clear.
                rdo_Auto.setChecked(true);
            }
            // DFKLOOK ここまで修正

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
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_BatchSeqNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTFloorProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTFloorProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTFloorProgressSCHParams inparam = new PCTFloorProgressSCHParams();
            inparam.set(PCTFloorProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTFloorProgressSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(PCTFloorProgressSCHParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK ここから修正
            if (outparams == null || outparams.size() == 0)
            {
                // 表示データを削除
                // output display.
                _lcm_lst_PCTAreaProgress.clear();

                // 6003011=対象データはありませんでした。
                message.setMsgResourceKey("6003011");
                return;
            }
            // DFKLOOK ここまで修正

            // output display.
            _lcm_lst_PCTAreaProgress.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTAreaProgress.getNewLine();
                line.setValue(KEY_LST_AREA_NO, outparam.get(PCTFloorProgressSCHParams.AREA_NO));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(PCTFloorProgressSCHParams.ZONE_NO));
                line.setValue(KEY_LST_PROGRESS_RATE, outparam.get(PCTFloorProgressSCHParams.PROGRESS_RATE));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(PCTFloorProgressSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTFloorProgressSCHParams.LOT_COUNT));
                lst_PCTAreaProgress_SetLineToolTip(line);
                _lcm_lst_PCTAreaProgress.add(line);
            }

            // DFKLOOK ここから修正
            setRegularTransmission();
            // DFKLOOK ここまで修正

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
        txt_ConsignorName.setValue(null);
        txt_BatchNo.setValue(null);
        txt_BatchSeqNo.setValue(null);
        rdo_Auto.setChecked(true);

        // DFKLOOK ここから修正
        page_Load_Process();
        // DFKLOOK ここまで修正

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
