// $Id: AsEndInventoryCheckBusiness.java 7404 2010-03-05 13:25:51Z shibamoto $
package jp.co.daifuku.wms.asrs.display.endinventorycheck;

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
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
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
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.endinventorycheck.AsEndInventoryCheck;
import jp.co.daifuku.wms.asrs.schedule.AsEndInventoryCheckSCH;
import jp.co.daifuku.wms.asrs.schedule.AsEndInventoryCheckSCHParams;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7404 $, $Date:: 2010-03-05 22:25:51 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class AsEndInventoryCheckBusiness
        extends AsEndInventoryCheck
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_EndInventoryCheck(HDN_STATION_NO) */
    private static final ListCellKey KEY_HDN_STATION_NO = new ListCellKey("HDN_STATION_NO", new StringCellColumn(), false, false);

    /** lst_EndInventoryCheck(HDN_BATCH_NO) */
    private static final ListCellKey KEY_HDN_BATCH_NO = new ListCellKey("HDN_BATCH_NO", new StringCellColumn(), false, false);

    /** lst_EndInventoryCheck(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_EndInventoryCheck(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_EndInventoryCheck(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO = new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_EndInventoryCheck(LST_STATION_NAME) */
    private static final ListCellKey KEY_LST_STATION_NAME = new ListCellKey("LST_STATION_NAME", new StringCellColumn(), true, false);

    /** lst_EndInventoryCheck(LST_FROM_LOCATION_NO) */
    private static final ListCellKey KEY_LST_FROM_LOCATION_NO = new ListCellKey("LST_FROM_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_EndInventoryCheck(LST_TO_LOCATION_NO) */
    private static final ListCellKey KEY_LST_TO_LOCATION_NO = new ListCellKey("LST_TO_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_EndInventoryCheck(LST_FROM_ITEM_CODE) */
    private static final ListCellKey KEY_LST_FROM_ITEM_CODE = new ListCellKey("LST_FROM_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_EndInventoryCheck(LST_TO_ITEM_CODE) */
    private static final ListCellKey KEY_LST_TO_ITEM_CODE = new ListCellKey("LST_TO_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_EndInventoryCheck keys */
    private static final ListCellKey[] LST_ENDINVENTORYCHECK_KEYS = {
        KEY_HDN_STATION_NO,
        KEY_HDN_BATCH_NO,
        KEY_LST_SELECT,
        KEY_LST_NO,
        KEY_LST_AREA_NO,
        KEY_LST_STATION_NAME,
        KEY_LST_FROM_LOCATION_NO,
        KEY_LST_TO_LOCATION_NO,
        KEY_LST_FROM_ITEM_CODE,
        KEY_LST_TO_ITEM_CODE,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_EndInventoryCheck */
    private ScrollListCellModel _lcm_lst_EndInventoryCheck;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsEndInventoryCheckBusiness()
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
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllCheck_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllCheck_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllCheckClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllCheckClear_Click_Process();
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
        btn_Setting_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ReDisplay_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ReDisplay_Click_Process();
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

        // initialize lst_EndInventoryCheck.
        _lcm_lst_EndInventoryCheck = new ScrollListCellModel(lst_EndInventoryCheck, LST_ENDINVENTORYCHECK_KEYS, locale);
        _lcm_lst_EndInventoryCheck.setToolTipVisible(KEY_LST_SELECT, true);
        _lcm_lst_EndInventoryCheck.setToolTipVisible(KEY_LST_NO, true);
        _lcm_lst_EndInventoryCheck.setToolTipVisible(KEY_LST_AREA_NO, true);
        _lcm_lst_EndInventoryCheck.setToolTipVisible(KEY_LST_STATION_NAME, true);
        _lcm_lst_EndInventoryCheck.setToolTipVisible(KEY_LST_FROM_LOCATION_NO, true);
        _lcm_lst_EndInventoryCheck.setToolTipVisible(KEY_LST_TO_LOCATION_NO, true);
        _lcm_lst_EndInventoryCheck.setToolTipVisible(KEY_LST_FROM_ITEM_CODE, true);
        _lcm_lst_EndInventoryCheck.setToolTipVisible(KEY_LST_TO_ITEM_CODE, true);
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
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_EndInventoryCheck_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0085", KEY_LST_STATION_NAME);
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
        AsEndInventoryCheckSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsEndInventoryCheckSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsEndInventoryCheckSCHParams inparam = new AsEndInventoryCheckSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_EndInventoryCheck.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_EndInventoryCheck.getNewLine();
                line.setValue(KEY_HDN_STATION_NO, outparam.get(AsEndInventoryCheckSCHParams.STATION_NO));
                line.setValue(KEY_HDN_BATCH_NO, outparam.get(AsEndInventoryCheckSCHParams.BATCH_NO));
                line.setValue(KEY_LST_NO, outparam.get(AsEndInventoryCheckSCHParams.NO));
                line.setValue(KEY_LST_AREA_NO, outparam.get(AsEndInventoryCheckSCHParams.AREA_NO));
                line.setValue(KEY_LST_STATION_NAME, outparam.get(AsEndInventoryCheckSCHParams.STATION_NAME));
                line.setValue(KEY_LST_FROM_LOCATION_NO, outparam.get(AsEndInventoryCheckSCHParams.FROM_LOCATION_NO));
                line.setValue(KEY_LST_TO_LOCATION_NO, outparam.get(AsEndInventoryCheckSCHParams.TO_LOCATION_NO));
                line.setValue(KEY_LST_FROM_ITEM_CODE, outparam.get(AsEndInventoryCheckSCHParams.FROM_ITEM_CODE));
                line.setValue(KEY_LST_TO_ITEM_CODE, outparam.get(AsEndInventoryCheckSCHParams.TO_ITEM_CODE));
                lst_EndInventoryCheck_SetLineToolTip(line);
                _lcm_lst_EndInventoryCheck.add(line);
            }

            // clear.
            for (int i = 1; i <= _lcm_lst_EndInventoryCheck.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_EndInventoryCheck.get(i);
                lst_EndInventoryCheck.setCurrentRow(i);
                clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
                lst_EndInventoryCheck_SetLineToolTip(clearLine);
                _lcm_lst_EndInventoryCheck.set(i, clearLine);
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
    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllCheck_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_EndInventoryCheck.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_EndInventoryCheck.get(i);
            lst_EndInventoryCheck.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_EndInventoryCheck_SetLineToolTip(clearLine);
            _lcm_lst_EndInventoryCheck.set(i, clearLine);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllCheckClear_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_EndInventoryCheck.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_EndInventoryCheck.get(i);
            lst_EndInventoryCheck.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_EndInventoryCheck_SetLineToolTip(clearLine);
            _lcm_lst_EndInventoryCheck.set(i, clearLine);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Setting_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_EndInventoryCheck.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_EndInventoryCheck.get(i);
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
        AsEndInventoryCheckSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsEndInventoryCheckSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_EndInventoryCheck.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_EndInventoryCheck.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                AsEndInventoryCheckSCHParams lineparam = new AsEndInventoryCheckSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(AsEndInventoryCheckSCHParams.STATION_NO, line.getValue(KEY_HDN_STATION_NO));
                lineparam.set(AsEndInventoryCheckSCHParams.BATCH_NO, line.getValue(KEY_HDN_BATCH_NO));
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
                _lcm_lst_EndInventoryCheck.resetEditRow();
                _lcm_lst_EndInventoryCheck.resetHighlight();
                _lcm_lst_EndInventoryCheck.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_EndInventoryCheck.size(); i++)
            {
                ListCellLine line = _lcm_lst_EndInventoryCheck.get(i);
                lst_EndInventoryCheck.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_AREA_NO), "");
                part11List.add(line.getViewString(KEY_HDN_STATION_NO), "");
                part11List.add(line.getViewString(KEY_LST_FROM_LOCATION_NO), "");
                part11List.add(line.getViewString(KEY_LST_TO_LOCATION_NO), "");
                part11List.add(line.getViewString(KEY_LST_FROM_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_TO_ITEM_CODE), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_EndInventoryCheck.resetEditRow();
            _lcm_lst_EndInventoryCheck.resetHighlight();

            // set input parameters.
            AsEndInventoryCheckSCHParams inparam = new AsEndInventoryCheckSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_EndInventoryCheck.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_EndInventoryCheck.getNewLine();
                line.setValue(KEY_HDN_STATION_NO, outparam.get(AsEndInventoryCheckSCHParams.STATION_NO));
                line.setValue(KEY_HDN_BATCH_NO, outparam.get(AsEndInventoryCheckSCHParams.BATCH_NO));
                line.setValue(KEY_LST_NO, outparam.get(AsEndInventoryCheckSCHParams.NO));
                line.setValue(KEY_LST_AREA_NO, outparam.get(AsEndInventoryCheckSCHParams.AREA_NO));
                line.setValue(KEY_LST_STATION_NAME, outparam.get(AsEndInventoryCheckSCHParams.STATION_NAME));
                line.setValue(KEY_LST_FROM_LOCATION_NO, outparam.get(AsEndInventoryCheckSCHParams.FROM_LOCATION_NO));
                line.setValue(KEY_LST_TO_LOCATION_NO, outparam.get(AsEndInventoryCheckSCHParams.TO_LOCATION_NO));
                line.setValue(KEY_LST_FROM_ITEM_CODE, outparam.get(AsEndInventoryCheckSCHParams.FROM_ITEM_CODE));
                line.setValue(KEY_LST_TO_ITEM_CODE, outparam.get(AsEndInventoryCheckSCHParams.TO_ITEM_CODE));
                lst_EndInventoryCheck_SetLineToolTip(line);
                _lcm_lst_EndInventoryCheck.add(line);
            }

            // clear.
            for (int i = 1; i <= _lcm_lst_EndInventoryCheck.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_EndInventoryCheck.get(i);
                lst_EndInventoryCheck.setCurrentRow(i);
                clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
                lst_EndInventoryCheck_SetLineToolTip(clearLine);
                _lcm_lst_EndInventoryCheck.set(i, clearLine);
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
    private void btn_ReDisplay_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsEndInventoryCheckSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsEndInventoryCheckSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsEndInventoryCheckSCHParams inparam = new AsEndInventoryCheckSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_EndInventoryCheck.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_EndInventoryCheck.getNewLine();
                line.setValue(KEY_HDN_STATION_NO, outparam.get(AsEndInventoryCheckSCHParams.STATION_NO));
                line.setValue(KEY_HDN_BATCH_NO, outparam.get(AsEndInventoryCheckSCHParams.BATCH_NO));
                line.setValue(KEY_LST_NO, outparam.get(AsEndInventoryCheckSCHParams.NO));
                line.setValue(KEY_LST_AREA_NO, outparam.get(AsEndInventoryCheckSCHParams.AREA_NO));
                line.setValue(KEY_LST_STATION_NAME, outparam.get(AsEndInventoryCheckSCHParams.STATION_NAME));
                line.setValue(KEY_LST_FROM_LOCATION_NO, outparam.get(AsEndInventoryCheckSCHParams.FROM_LOCATION_NO));
                line.setValue(KEY_LST_TO_LOCATION_NO, outparam.get(AsEndInventoryCheckSCHParams.TO_LOCATION_NO));
                line.setValue(KEY_LST_FROM_ITEM_CODE, outparam.get(AsEndInventoryCheckSCHParams.FROM_ITEM_CODE));
                line.setValue(KEY_LST_TO_ITEM_CODE, outparam.get(AsEndInventoryCheckSCHParams.TO_ITEM_CODE));
                lst_EndInventoryCheck_SetLineToolTip(line);
                _lcm_lst_EndInventoryCheck.add(line);
            }

            // clear.
            for (int i = 1; i <= _lcm_lst_EndInventoryCheck.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_EndInventoryCheck.get(i);
                lst_EndInventoryCheck.setCurrentRow(i);
                clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
                lst_EndInventoryCheck_SetLineToolTip(clearLine);
                _lcm_lst_EndInventoryCheck.set(i, clearLine);
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
