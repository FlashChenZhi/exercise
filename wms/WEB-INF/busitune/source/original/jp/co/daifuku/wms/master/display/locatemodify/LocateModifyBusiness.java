// $Id: LocateModifyBusiness.java 7401 2010-03-05 12:08:12Z shibamoto $
package jp.co.daifuku.wms.master.display.locatemodify;

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
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
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
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.master.display.locatemodify.LocateModify;
import jp.co.daifuku.wms.master.display.locatemodify.ViewStateKeys;
import jp.co.daifuku.wms.master.schedule.LocateModifySCH;
import jp.co.daifuku.wms.master.schedule.LocateModifySCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7401 $, $Date:: 2010-03-05 21:08:12 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LocateModifyBusiness
        extends LocateModify
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_LocateListMaintenance(HDNIDX_UPDAY) */
    private static final ListCellKey KEY_HDNIDX_UPDAY = new ListCellKey("HDNIDX_UPDAY", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), false, false);

    /** lst_LocateListMaintenance(HDNIDX_AREA_NO) */
    private static final ListCellKey KEY_HDNIDX_AREA_NO = new ListCellKey("HDNIDX_AREA_NO", new AreaCellColumn(), false, false);

    /** lst_LocateListMaintenance(LST_DELETE) */
    private static final ListCellKey KEY_LST_DELETE = new ListCellKey("LST_DELETE", new StringCellColumn(), true, false);

    /** lst_LocateListMaintenance(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO = new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_LocateListMaintenance(LST_AISLE_NO) */
    private static final ListCellKey KEY_LST_AISLE_NO = new ListCellKey("LST_AISLE_NO", new NumberCellColumn(0), true, true);

    /** lst_LocateListMaintenance keys */
    private static final ListCellKey[] LST_LOCATELISTMAINTENANCE_KEYS = {
        KEY_HDNIDX_UPDAY,
        KEY_HDNIDX_AREA_NO,
        KEY_LST_DELETE,
        KEY_LST_LOCATION_NO,
        KEY_LST_AISLE_NO,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** ListCellModel lst_LocateListMaintenance */
    private ScrollListCellModel _lcm_lst_LocateListMaintenance;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LocateModifyBusiness()
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
    public void btn_ModifySet_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ModifySet_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_LocateListMaintenance_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_LocateListMaintenance.getActiveCol();

        // choose process.
        if (_lcm_lst_LocateListMaintenance.getColumnIndex(KEY_LST_DELETE) == activeCol)
        {
            // process call.
            lst_Delete_Click_Process();
        }
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

        // initialize lst_LocateListMaintenance.
        _lcm_lst_LocateListMaintenance = new ScrollListCellModel(lst_LocateListMaintenance, LST_LOCATELISTMAINTENANCE_KEYS, locale);
        _lcm_lst_LocateListMaintenance.setToolTipVisible(KEY_LST_DELETE, false);
        _lcm_lst_LocateListMaintenance.setToolTipVisible(KEY_LST_LOCATION_NO, false);
        _lcm_lst_LocateListMaintenance.setToolTipVisible(KEY_LST_AISLE_NO, false);
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
            _pdm_pul_Area.init(conn, AreaType.FLOOR_LOCATE, StationType.ALL, "", false);
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
    private void lst_LocateListMaintenance_SetLineToolTip(ListCellLine line)
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
        setFocus(pul_Area);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_ModifySet.setEnabled(false);
        txt_Area.setReadOnly(true);
        txt_AreaName.setReadOnly(true);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        pul_Area.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        LocateModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new LocateModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            LocateModifySCHParams inparam = new LocateModifySCHParams();
            inparam.set(LocateModifySCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(LocateModifySCHParams.FROM_LOCATE_NO, txt_FromLocate.getValue());
            inparam.set(LocateModifySCHParams.TO_LOCATE_NO, txt_ToLocate.getValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_LocateListMaintenance.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_LocateListMaintenance.getNewLine();
                line.setValue(KEY_HDNIDX_UPDAY, outparam.get(LocateModifySCHParams.UPDAY));
                line.setValue(KEY_HDNIDX_AREA_NO, outparam.get(LocateModifySCHParams.AREA_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(LocateModifySCHParams.LOCATION_NO));
                line.setValue(KEY_LST_AISLE_NO, outparam.get(LocateModifySCHParams.AISLE_NO));
                txt_Area.setValue(outparam.get(LocateModifySCHParams.AREA_NO));
                txt_AreaName.setValue(outparam.get(LocateModifySCHParams.AREA_NAME));
                lst_LocateListMaintenance_SetLineToolTip(line);
                _lcm_lst_LocateListMaintenance.add(line);
            }

            // clear.
            btn_ModifySet.setEnabled(true);
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
        _pdm_pul_Area.setSelectedValue(null);
        txt_FromLocate.setValue(null);
        txt_ToLocate.setValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ModifySet_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_LocateListMaintenance.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_LocateListMaintenance.get(i);
            if (!(checkline.isAppend() || checkline.isEdited()))
            {
                continue;
            }

            existEditedRow = true;
            lst_LocateListMaintenance.setCurrentRow(i);
            lst_LocateListMaintenance.validate(checkline.getIndex(KEY_LST_AISLE_NO), false);
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
        LocateModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new LocateModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_LocateListMaintenance.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_LocateListMaintenance.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                LocateModifySCHParams lineparam = new LocateModifySCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(LocateModifySCHParams.UPDAY, line.getValue(KEY_HDNIDX_UPDAY));
                lineparam.set(LocateModifySCHParams.AREA_NO, txt_Area.getValue());
                lineparam.set(LocateModifySCHParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
                lineparam.set(LocateModifySCHParams.AISLE_NO, line.getValue(KEY_LST_AISLE_NO));
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
                _lcm_lst_LocateListMaintenance.resetEditRow();
                _lcm_lst_LocateListMaintenance.resetHighlight();
                _lcm_lst_LocateListMaintenance.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_LocateListMaintenance.size(); i++)
            {
                ListCellLine line = _lcm_lst_LocateListMaintenance.get(i);
                lst_LocateListMaintenance.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(txt_Area.getStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_LOCATION_NO), "");
                part11List.add(line.getViewString(KEY_LST_AISLE_NO), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_MODIFY), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_LocateListMaintenance.resetEditRow();
            _lcm_lst_LocateListMaintenance.resetHighlight();

            // set input parameters.
            LocateModifySCHParams inparam = new LocateModifySCHParams();
            inparam.set(LocateModifySCHParams.AREA_NO, txt_Area.getValue());
            inparam.set(LocateModifySCHParams.FROM_LOCATE_NO, viewState.getObject(ViewStateKeys.FROM_LOCATE_NO));
            inparam.set(LocateModifySCHParams.TO_LOCATE_NO, viewState.getObject(ViewStateKeys.TO_LOCATE_NO));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_LocateListMaintenance.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_LocateListMaintenance.getNewLine();
                txt_Area.setValue(outparam.get(LocateModifySCHParams.AREA_NO));
                txt_AreaName.setValue(outparam.get(LocateModifySCHParams.AREA_NAME));
                line.setValue(KEY_HDNIDX_UPDAY, outparam.get(LocateModifySCHParams.UPDAY));
                line.setValue(KEY_HDNIDX_AREA_NO, outparam.get(LocateModifySCHParams.AREA_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(LocateModifySCHParams.LOCATION_NO));
                line.setValue(KEY_LST_AISLE_NO, outparam.get(LocateModifySCHParams.AISLE_NO));
                lst_LocateListMaintenance_SetLineToolTip(line);
                _lcm_lst_LocateListMaintenance.add(line);
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
    private void lst_Delete_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_LocateListMaintenance.getActiveRow();
        lst_LocateListMaintenance.setCurrentRow(row);
        ListCellLine line = _lcm_lst_LocateListMaintenance.get(row);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        LocateModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new LocateModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            LocateModifySCHParams inparam = new LocateModifySCHParams();
            inparam.setProcessFlag(ProcessFlag.DELETE);
            inparam.setRowIndex(row);
            inparam.set(LocateModifySCHParams.AREA_NO, txt_Area.getValue());
            inparam.set(LocateModifySCHParams.UPDAY, line.getValue(KEY_HDNIDX_UPDAY));
            inparam.set(LocateModifySCHParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
            inparam.set(LocateModifySCHParams.AISLE_NO, line.getValue(KEY_LST_AISLE_NO));

            // SCH call.
            if (!sch.check(inparam))
            {
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_LocateListMaintenance.resetEditRow();
                _lcm_lst_LocateListMaintenance.resetHighlight();
                _lcm_lst_LocateListMaintenance.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(txt_Area.getStringValue(), "");
            part11List.add(line.getViewString(KEY_LST_LOCATION_NO), "");
            part11List.add(line.getViewString(KEY_LST_AISLE_NO), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_DELETE), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            lst_LocateListMaintenance.removeRow(row);
            _lcm_lst_LocateListMaintenance.resetEditRow();
            _lcm_lst_LocateListMaintenance.resetHighlight();
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
