// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.retrieval.display.faretrievallist;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.Constants;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.Distribution;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsChecker;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkspacePullDownModel;
import jp.co.daifuku.wms.retrieval.dasch.FaRetrievalListDASCH;
import jp.co.daifuku.wms.retrieval.dasch.FaRetrievalListDASCHParams;
import jp.co.daifuku.wms.retrieval.exporter.AsRetrievalWorkListParams;
import jp.co.daifuku.wms.retrieval.exporter.InventoryCheckWorkListParams;
import jp.co.daifuku.wms.retrieval.exporter.RetrievalWorkListParams;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalInParameter;

/**
 * 出庫作業リストの画面処理を行います。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
@SuppressWarnings("serial")
public class FaRetrievalListBusiness
        extends FaRetrievalList
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // DFKLOOK START
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK END
    
    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_FaRetrievalWorkList(HIDDEN_SETTING_UNIT_KEY) */
    private static final ListCellKey KEY_HIDDEN_SETTING_UNIT_KEY = new ListCellKey("HIDDEN_SETTING_UNIT_KEY", new StringCellColumn(), false, false);

    /** lst_FaRetrievalWorkList(HIDDEN_WORK_TYPE) */
    private static final ListCellKey KEY_HIDDEN_WORK_TYPE = new ListCellKey("HIDDEN_WORK_TYPE", new StringCellColumn(), false, false);

    /** lst_FaRetrievalWorkList(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_FaRetrievalWorkList(LST_STATION_NO) */
    private static final ListCellKey KEY_LST_STATION_NO = new ListCellKey("LST_STATION_NO", new StringCellColumn(), true, false);

    /** lst_FaRetrievalWorkList(LST_WORK_TYPE_NAME) */
    private static final ListCellKey KEY_LST_WORK_TYPE_NAME = new ListCellKey("LST_WORK_TYPE_NAME", new StringCellColumn(), true, false);

    /** lst_FaRetrievalWorkList(LST_SETTING_DATE) */
    private static final ListCellKey KEY_LST_SETTING_DATE = new ListCellKey("LST_SETTING_DATE", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), true, false);

    /** lst_FaRetrievalWorkList keys */
    private static final ListCellKey[] LST_FARETRIEVALWORKLIST_KEYS = {
        KEY_HIDDEN_SETTING_UNIT_KEY,
        KEY_HIDDEN_WORK_TYPE,
        KEY_LST_SELECT,
        KEY_LST_STATION_NO,
        KEY_LST_WORK_TYPE_NAME,
        KEY_LST_SETTING_DATE,
    };

    //DFKLOOK:ここから修正
    // ステーションNo.
    private static final int LST_STATION_NO = 2;
    //DFKLOOK:ここまで修正
    
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

    /** PullDownModel pul_Station */
    private WmsStationPullDownModel _pdm_pul_Station;

    /** PullDownModel pul_FFaRetrievalWorkKind */
    private DefaultPullDownModel _pdm_pul_FFaRetrievalWorkKind;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_FaRetrievalWorkList */
    private ListCellModel _lcm_lst_FaRetrievalWorkList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaRetrievalListBusiness()
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
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        // DFKLOOK START
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
        if (eventSource.equals("btn_Print_Click"))
        {
            // process call.
            btn_Print_Click_Process(false);
        }
        // DFKLOOK END
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
    public void btn_Print_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        // DFKLOOK START
        btn_Print_Click_Process(true);
        // DFKLOOK END
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Preview_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Preview_Click_Process();
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
    public void btn_AllClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllClear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_Prev(ActionEvent e)
            throws Exception
    {
        _pager.previous();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_Next(ActionEvent e)
            throws Exception
    {
        _pager.next();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_Last(ActionEvent e)
            throws Exception
    {
        _pager.last();
        pager_SetPage();
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
    //DFKLOOK:ここから修正
    /**
     * リスト表示項目を切り替えます。
     * @throws Exception 全ての例外を報告します。
     */
    protected void changeList() 
			throws Exception
    {
    	// ﾌﾟﾙﾀﾞｳﾝで選択されているエリアが平置かASRSか
    	AreaController acon = new AreaController(ConnectionManager.getRequestConnection(this), getClass());

    	if (!acon.getAreaType(pul_Area.getSelectedValue()).equals(Area.AREA_TYPE_ASRS))
    	{
    		// 平置の場合
    		_lcm_lst_FaRetrievalWorkList.getListCell().setColumnHidden(LST_STATION_NO, true);
    	}
    	else
    	{
    		// ASRSの場合
    		_lcm_lst_FaRetrievalWorkList.getListCell().setColumnHidden(LST_STATION_NO, false);
    	}
    }
    
    /**
     * リストセルより出庫作業リストのキーとなるパラメータのみを作成し、返します。<br>
     * 1件も該当しない場合は、nullを返します。
     * 
     * @param worktype リストのタイプ(<code>RetrievalInParameter</code>より指定)
     */
    protected FaRetrievalListDASCHParams getPrintListParam(String worktype)
    {
    	List<String> DestStList = new ArrayList<String>();
        
        List<String> ukeyList = new ArrayList<String>();
        for (int i = 1; i <= _lcm_lst_FaRetrievalWorkList.size(); i++)
        {
            // exclusion unmodified row.
            ListCellLine line = _lcm_lst_FaRetrievalWorkList.get(i);
            if (!(line.isAppend() || line.isEdited()))
            {
                continue;
            }
            
            // 設定単位キーをセットする
            if (worktype.equals(line.getStringValue(KEY_HIDDEN_WORK_TYPE)))
            {
                // 選択された設定単位キーを追加
                ukeyList.add(line.getStringValue(KEY_HIDDEN_SETTING_UNIT_KEY));
                // 選択されたｽﾃｰｼｮﾝNo.を追加
                DestStList.add(line.getStringValue(KEY_LST_STATION_NO));
            }
        }
        
        if (ukeyList.isEmpty())
        {
            // 空ならnullを返す
            return null;
        }
        
        FaRetrievalListDASCHParams retparam = new FaRetrievalListDASCHParams();
        retparam.set(FaRetrievalListDASCHParams.SETTING_UNIT_KEY, ukeyList);
        
        retparam.set(FaRetrievalListDASCHParams.STATION_NO, DestStList);
        
        if (pul_FFaRetrievalWorkKind.getItem(1).getValue().equals(worktype))
        {
            retparam.set(FaRetrievalListDASCHParams.WORK_TYPE, RetrievalInParameter.SEARCH_ASRS_RETRIEVAL_LIST);
        }
        else if (pul_FFaRetrievalWorkKind.getItem(2).getValue().equals(worktype))
        {
            retparam.set(FaRetrievalListDASCHParams.WORK_TYPE, RetrievalInParameter.SEARCH_FLOOR_RETRIEVAL_LIST);
        }
        else 
        {
            retparam.set(FaRetrievalListDASCHParams.WORK_TYPE, RetrievalInParameter.SEARCH_INVENTORY_CHECK_LIST);
            
        }

        return retparam;
    }
    //DFKLOOK:ここまで修正

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

        // initialize pul_WorkPlace.
        _pdm_pul_WorkPlace = new WmsWorkspacePullDownModel(pul_WorkPlace, locale, ui);
        _pdm_pul_WorkPlace.setParent(_pdm_pul_Area);

        // initialize pul_Station.
        _pdm_pul_Station = new WmsStationPullDownModel(pul_Station, locale, ui);
        _pdm_pul_Station.setParent(_pdm_pul_WorkPlace);

        // initialize pul_FFaRetrievalWorkKind.
        _pdm_pul_FFaRetrievalWorkKind = new DefaultPullDownModel(pul_FFaRetrievalWorkKind, locale, ui);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pager}, locale);

        // initialize lst_FaRetrievalWorkList.
        _lcm_lst_FaRetrievalWorkList = new ListCellModel(lst_FaRetrievalWorkList, LST_FARETRIEVALWORKLIST_KEYS, locale);
        _lcm_lst_FaRetrievalWorkList.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_FaRetrievalWorkList.setToolTipVisible(KEY_LST_STATION_NO, false);
        _lcm_lst_FaRetrievalWorkList.setToolTipVisible(KEY_LST_WORK_TYPE_NAME, false);
        _lcm_lst_FaRetrievalWorkList.setToolTipVisible(KEY_LST_SETTING_DATE, false);

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
            //DFKLOOK:ここから修正
            _pdm_pul_Area.init(conn, AreaType.NOT_MOVING_TERM, StationType.RETRIEVAL, "", false);
            //DFKLOOK:ここまで修正

            // load pul_WorkPlace.
            _pdm_pul_WorkPlace.init(conn, StationType.FLOORANDRETRIEVAL);

            // load pul_Station.
            //DFKLOOK:ここから修正
            _pdm_pul_Station.init(conn, StationType.FLOORANDRETRIEVAL, Distribution.ALL);
            //DFKLOOK:ここまで修正

            // load pul_FFaRetrievalWorkKind.
            _pdm_pul_FFaRetrievalWorkKind.init(conn);

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
    @SuppressWarnings("all")
    private void dispose()
            throws Exception
    {
        // dispose DASCH.
        disposeDasch();
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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_FaRetrievalWorkList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
    }

    /**
     *
     * @throws Exception
     */
    private void pager_SetPage()
            throws Exception
    {
        // get event source.
        String eventSource = viewState.getString(_KEY_PAGERSOURCE);
        if (eventSource == null)
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_Display_Click"))
        {
            // process call.
            btn_Display_Click_SetList();
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
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_Print.setEnabled(false);
        btn_Preview.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        
        // DFKLOOK START
        lbl_InputStyle.setValue(DisplayText.getText("LBL-W1365"));
        // DFKLOOK END
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // DFKLOOK START
        pul_Area.validate(this, true);
        pul_WorkPlace.validate(this, true);
        pul_Station.validate(this, true);
        // DFKLOOK END
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaRetrievalListDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // DFKLOOK START
            WmsChecker chk = new WmsChecker();

            if (!chk.checkDate(txt_FromSearchDate.getDate(), txt_FromSearchTime.getTime()))
            {
                message.setMsgResourceKey(chk.getMessage());
                return;
            }
            if (!chk.checkDate(txt_ToSearchDate.getDate(), txt_ToSearchTime.getTime()))
            {
                message.setMsgResourceKey(chk.getMessage());
                return;
            }
            // DFKLOOK END
            
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new FaRetrievalListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            FaRetrievalListDASCHParams inparam = new FaRetrievalListDASCHParams();
            inparam.set(FaRetrievalListDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaRetrievalListDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(FaRetrievalListDASCHParams.FROM_SEARCH_DAY, txt_FromSearchDate.getValue());
            inparam.set(FaRetrievalListDASCHParams.FROM_SEARCH_TIME, txt_FromSearchTime.getValue());
            inparam.set(FaRetrievalListDASCHParams.TO_SEARCH_DAY, txt_ToSearchDate.getValue());
            inparam.set(FaRetrievalListDASCHParams.TO_SEARCH_TIME, txt_ToSearchTime.getValue());
            inparam.set(FaRetrievalListDASCHParams.WORK_TYPE, _pdm_pul_FFaRetrievalWorkKind.getSelectedValue());

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_FaRetrievalWorkList.clear();

            if (count == 0)
            {
                // DFKLOOK start
                btn_Print.setEnabled(false);
                btn_Preview.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_AllClear.setEnabled(false);
                // DFKLOOK end
                message.setMsgResourceKey("6003011");
                return;
            }
            else if (count > _pager.getMax())
            {
                message.setMsgResourceKey("6001023\t" + Formatter.getNumFormat(count)
                        + "\t" + Formatter.getNumFormat(_pager.getMax()));
            }
            else
            {
                message.setMsgResourceKey("6001022\t" + Formatter.getNumFormat(count));
            }

            // DASCH call.
            dasch.search(inparam);

            // save session.
            session.setAttribute(_KEY_DASCH, dasch);
            isSuccess = true;

            // clear.
            btn_Print.setEnabled(true);
            btn_Preview.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_AllClear.setEnabled(true);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaRetrievalWorkList.clear();
        }
        finally
        {
            if (isSuccess)
            {
                // set list.
                btn_Display_Click_SetList();
            }
            else
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
    private void btn_Display_Click_SetList()
            throws Exception
    {
        FaRetrievalListDASCH dasch = null;
        try
        {
            // get session.
            dasch = (FaRetrievalListDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_FaRetrievalWorkList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_FaRetrievalWorkList.getNewLine();
                line.setValue(KEY_HIDDEN_SETTING_UNIT_KEY, outparam.get(FaRetrievalListDASCHParams.SETTING_UNIT_KEY));
                line.setValue(KEY_HIDDEN_WORK_TYPE, outparam.get(FaRetrievalListDASCHParams.WORK_TYPE));
                //DFKLOOK:ここから修正
                line.setValue(KEY_LST_STATION_NO, outparam.get(FaRetrievalListDASCHParams.STATION_NO));
                line.setValue(KEY_LST_WORK_TYPE_NAME, outparam.get(FaRetrievalListDASCHParams.WORK_TYPE_NAME));
                line.setValue(KEY_LST_SETTING_DATE, outparam.get(FaRetrievalListDASCHParams.SETTING_DATE));
                //DFKLOOK:ここまで修正
                viewState.setObject(ViewStateKeys.FROM_SEARCH_DAY, txt_FromSearchDate.getValue());
                viewState.setObject(ViewStateKeys.FROM_SEARCH_TIME, txt_FromSearchTime.getValue());
                viewState.setObject(ViewStateKeys.TO_SEARCH_DAY, txt_ToSearchDate.getValue());
                viewState.setObject(ViewStateKeys.TO_SEARCH_TIME, txt_ToSearchTime.getValue());
                lst_FaRetrievalWorkList_SetLineToolTip(line);
                _lcm_lst_FaRetrievalWorkList.add(line);
            }
            // DFKLOOK start
            // リストの表示内容の設定
            changeList();
            // DFKLOOK end

            // clear.
            btn_Print.setEnabled(true);
            btn_Preview.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_AllClear.setEnabled(true);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaRetrievalWorkList.clear();
            disposeDasch();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
    	//DFKLOOK:ここから修正
        // input validation.
        pul_Area.validate(this, true);
        pul_WorkPlace.validate(this, true);
        pul_Station.validate(this, true);
        //DFKLOOK:ここまで修正
        
        // clear.
        _pdm_pul_Area.setSelectedValue(null);
        _pdm_pul_WorkPlace.setSelectedValue(null);
        txt_FromSearchDate.setValue(null);
        txt_FromSearchTime.setValue(null);
        txt_ToSearchDate.setValue(null);
        txt_ToSearchTime.setValue(null);
        _pdm_pul_FFaRetrievalWorkKind.setSelectedValue(null);

    }

    /**
     *
     * @throws Exception
     */
    //DFKLOOK:ここから修正
    private void btn_Print_Click_Process(boolean confirm)
    //DFKLOOK:ここまで修正
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_FaRetrievalWorkList.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_FaRetrievalWorkList.get(i);
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
        FaRetrievalListDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new FaRetrievalListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // DFKLOOK START
            List<String> ukeyList = new ArrayList<String>();
            List<String> stList = new ArrayList<String>();
            for (int i = 1; i <= _lcm_lst_FaRetrievalWorkList.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_FaRetrievalWorkList.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                // 選択された設定単位キーを追加
                ukeyList.add(line.getStringValue(KEY_HIDDEN_SETTING_UNIT_KEY));
                // 選択されたステーションを追加
                stList.add(line.getStringValue(KEY_LST_STATION_NO));
            }
            
            // 設定単位キーのみセットし、全体の発行件数をチェックするようにする
            FaRetrievalListDASCHParams inparam = new FaRetrievalListDASCHParams();
            inparam.set(FaRetrievalListDASCHParams.SETTING_UNIT_KEY, ukeyList);
            if (stList.size() > 1)
            {
                inparam.set(FaRetrievalListDASCHParams.STATION_NO, stList);
            }
            
            
            // check count.
            int count = dasch.count(inparam);
            if (confirm && count > 0)
            {
                // show confirm message.
                this.setConfirm("MSG-W0018\t" + Formatter.getNumFormat(count), false, true);
                viewState.setString(_KEY_CONFIRMSOURCE, "btn_Print_Click");
                return;
            }
            else if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }


            // AS/RS出庫作業リスト用のパラメータを作成
            FaRetrievalListDASCHParams printAsParam = new FaRetrievalListDASCHParams();
            printAsParam = getPrintListParam(RetrievalInParameter.SEARCH_ASRS_RETRIEVAL_LIST);
            
            // パラメータがnullの場合は印刷処理を行わない。
            if (printAsParam != null && dasch.count(printAsParam) > 0)
            {
                // DASCH call.
                dasch.search(printAsParam);

                // open exporter.
                ExporterFactory factory = new WmsExporterFactory(locale, ui);
                exporter = factory.newPrinterExporter("AsRetrievalWorkList", false);
                exporter.open();
    
                // export.
                while (dasch.next())
                {
                    Params outparam = dasch.get();
                    AsRetrievalWorkListParams expparam = new AsRetrievalWorkListParams();
                    expparam.set(AsRetrievalWorkListParams.DFK_DS_NO, outparam.get(FaRetrievalListDASCHParams.DFK_DS_NO));
                    expparam.set(AsRetrievalWorkListParams.DFK_USER_ID, outparam.get(FaRetrievalListDASCHParams.DFK_USER_ID));
                    expparam.set(AsRetrievalWorkListParams.DFK_USER_NAME, outparam.get(FaRetrievalListDASCHParams.DFK_USER_NAME));
                    expparam.set(AsRetrievalWorkListParams.SYS_DAY, outparam.get(FaRetrievalListDASCHParams.SYS_DAY));
                    expparam.set(AsRetrievalWorkListParams.SYS_TIME, outparam.get(FaRetrievalListDASCHParams.SYS_TIME));
                    expparam.set(AsRetrievalWorkListParams.STATION_NO, outparam.get(FaRetrievalListDASCHParams.STATION_NO));
                    expparam.set(AsRetrievalWorkListParams.STATION_NAME, outparam.get(FaRetrievalListDASCHParams.STATION_NAME));
                    expparam.set(AsRetrievalWorkListParams.FROM_SEARCH_DAY, viewState.getObject(ViewStateKeys.FROM_SEARCH_DAY));
                    expparam.set(AsRetrievalWorkListParams.FROM_SEARCH_TIME, viewState.getObject(ViewStateKeys.FROM_SEARCH_TIME));
                    expparam.set(AsRetrievalWorkListParams.TO_SEARCH_DAY, viewState.getObject(ViewStateKeys.TO_SEARCH_DAY));
                    expparam.set(AsRetrievalWorkListParams.TO_SEARCH_TIME, viewState.getObject(ViewStateKeys.TO_SEARCH_TIME));
                    expparam.set(AsRetrievalWorkListParams.WORK_NO, outparam.get(FaRetrievalListDASCHParams.WORK_NO));
                    expparam.set(AsRetrievalWorkListParams.LOCATION_NO, outparam.get(FaRetrievalListDASCHParams.LOCATION_NO));
                    expparam.set(AsRetrievalWorkListParams.ITEM_CODE, outparam.get(FaRetrievalListDASCHParams.ITEM_CODE));
                    expparam.set(AsRetrievalWorkListParams.ITEM_NAME, outparam.get(FaRetrievalListDASCHParams.ITEM_NAME));
                    expparam.set(AsRetrievalWorkListParams.LOT_NO, outparam.get(FaRetrievalListDASCHParams.LOT_NO));
                    expparam.set(AsRetrievalWorkListParams.RETRIEVAL_COMMAND_DETAIL, outparam.get(FaRetrievalListDASCHParams.RETRIEVAL_COMMAND_DETAIL));
                    expparam.set(AsRetrievalWorkListParams.PRIORITY_FLAG, outparam.get(FaRetrievalListDASCHParams.PRIORITY_FLAG));
                    expparam.set(AsRetrievalWorkListParams.WORK_QTY, outparam.get(FaRetrievalListDASCHParams.WORK_QTY));
                    expparam.set(AsRetrievalWorkListParams.STOCK_QTY, outparam.get(FaRetrievalListDASCHParams.STOCK_QTY));
                    expparam.set(AsRetrievalWorkListParams.BATCH_NO, outparam.get(FaRetrievalListDASCHParams.BATCH_NO));
                    expparam.set(AsRetrievalWorkListParams.TICKET_NO, outparam.get(FaRetrievalListDASCHParams.TICKET_NO));
                    //DFKLOOK:ここから修正
                    expparam.set(AsRetrievalWorkListParams.LINE_NO, StringUtil.isBlank(outparam.get(FaRetrievalListDASCHParams.TICKET_NO)) ? null : outparam.get(FaRetrievalListDASCHParams.LINE_NO));
                    expparam.set(AsRetrievalWorkListParams.AREA_NO, outparam.get(FaRetrievalListDASCHParams.AREA_NO));
                    //DFKLOOK:ここまで修正
                    if (!exporter.write(expparam))
                    {
                        break;
                    }
                }
    
                // execute print.
                try
                {
                    exporter.print();
                    message.setMsgResourceKey("6001010");
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    message.setMsgResourceKey("6007034");
                    return;
                }
            }
            
            // 平置入庫作業リスト用のパラメータを作成
            FaRetrievalListDASCHParams printFloorParam = new FaRetrievalListDASCHParams();
            printFloorParam = getPrintListParam(RetrievalInParameter.SEARCH_FLOOR_RETRIEVAL_LIST);
            
            // パラメータがnullの場合は印刷処理を行わない。
            if (printFloorParam != null && dasch.count(printFloorParam) > 0)
            {
                // DASCH call.
                dasch.search(printFloorParam);

                // open exporter.
                ExporterFactory factory = new WmsExporterFactory(locale, ui);
                exporter = factory.newPrinterExporter("RetrievalWorkList", false);
                exporter.open();
    
                // export.
                while (dasch.next())
                {

                   Params outparam = dasch.get();
                   RetrievalWorkListParams expparam = new RetrievalWorkListParams();
                   expparam.set(RetrievalWorkListParams.DFK_DS_NO, outparam.get(FaRetrievalListDASCHParams.DFK_DS_NO));
                   expparam.set(RetrievalWorkListParams.DFK_USER_ID, outparam.get(FaRetrievalListDASCHParams.DFK_USER_ID));
                   expparam.set(RetrievalWorkListParams.DFK_USER_NAME, outparam.get(FaRetrievalListDASCHParams.DFK_USER_NAME));
                   expparam.set(RetrievalWorkListParams.SYS_DAY, outparam.get(FaRetrievalListDASCHParams.SYS_DAY));
                   expparam.set(RetrievalWorkListParams.SYS_TIME, outparam.get(FaRetrievalListDASCHParams.SYS_TIME));
                   expparam.set(RetrievalWorkListParams.LIST_NO, outparam.get(FaRetrievalListDASCHParams.SETTING_UNIT_KEY));
                   expparam.set(RetrievalWorkListParams.FROM_SEARCH_DAY, viewState.getObject(ViewStateKeys.FROM_SEARCH_DAY));
                   expparam.set(RetrievalWorkListParams.FROM_SEARCH_TIME, viewState.getObject(ViewStateKeys.FROM_SEARCH_TIME));
                   expparam.set(RetrievalWorkListParams.TO_SEARCH_DAY, viewState.getObject(ViewStateKeys.TO_SEARCH_DAY));
                   expparam.set(RetrievalWorkListParams.TO_SEARCH_TIME, viewState.getObject(ViewStateKeys.TO_SEARCH_TIME));
                   expparam.set(RetrievalWorkListParams.AREA_NO, outparam.get(FaRetrievalListDASCHParams.AREA_NO));
                   expparam.set(RetrievalWorkListParams.LOCATION_NO, outparam.get(FaRetrievalListDASCHParams.LOCATION_NO));
                   expparam.set(RetrievalWorkListParams.ITEM_CODE, outparam.get(FaRetrievalListDASCHParams.ITEM_CODE));
                   expparam.set(RetrievalWorkListParams.ITEM_NAME, outparam.get(FaRetrievalListDASCHParams.ITEM_NAME));
                   expparam.set(RetrievalWorkListParams.LOT_NO, outparam.get(FaRetrievalListDASCHParams.LOT_NO));
                   expparam.set(RetrievalWorkListParams.RETRIEVAL_COMMAND_DETAIL, outparam.get(FaRetrievalListDASCHParams.RETRIEVAL_COMMAND_DETAIL));
                   expparam.set(RetrievalWorkListParams.PRIORITY_FLAG, outparam.get(FaRetrievalListDASCHParams.PRIORITY_FLAG));
                   expparam.set(RetrievalWorkListParams.WORK_QTY, outparam.get(FaRetrievalListDASCHParams.WORK_QTY));
                   expparam.set(RetrievalWorkListParams.STOCK_QTY, outparam.get(FaRetrievalListDASCHParams.STOCK_QTY));
                   expparam.set(RetrievalWorkListParams.BATCH_NO, outparam.get(FaRetrievalListDASCHParams.BATCH_NO));
                   expparam.set(RetrievalWorkListParams.TICKET_NO, outparam.get(FaRetrievalListDASCHParams.TICKET_NO));
                   expparam.set(RetrievalWorkListParams.LINE_NO, StringUtil.isBlank(outparam.get(FaRetrievalListDASCHParams.TICKET_NO)) ? null :outparam.get(FaRetrievalListDASCHParams.LINE_NO));
                    if (!exporter.write(expparam))
                    {
                        break;
                    }
                }
    
                // execute print.
                try
                {
                    exporter.print();
                    message.setMsgResourceKey("6001010");
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    message.setMsgResourceKey("6007034");
                    return;
                }
            }
            
            // 在庫確認用リスト用のパラメータを作成
            FaRetrievalListDASCHParams printInventryParam = new FaRetrievalListDASCHParams();
            printInventryParam = getPrintListParam(RetrievalInParameter.SEARCH_INVENTORY_CHECK_LIST);
            
            // パラメータがnullの場合は印刷処理を行わない。
            if (printInventryParam != null && dasch.count(printInventryParam) > 0)
            {
                // DASCH call.
                dasch.search(printInventryParam);

                // open exporter.
                ExporterFactory factory = new WmsExporterFactory(locale, ui);
                exporter = factory.newPrinterExporter("InventoryCheckWorkList", false);
                exporter.open();
    
                // export.
                while (dasch.next())
                {
                    Params outparam = dasch.get();
                    InventoryCheckWorkListParams expparam = new InventoryCheckWorkListParams();
                    expparam.set(InventoryCheckWorkListParams.DFK_DS_NO, outparam.get(FaRetrievalListDASCHParams.DFK_DS_NO));
                    expparam.set(InventoryCheckWorkListParams.DFK_USER_ID, outparam.get(FaRetrievalListDASCHParams.DFK_USER_ID));
                    expparam.set(InventoryCheckWorkListParams.DFK_USER_NAME, outparam.get(FaRetrievalListDASCHParams.DFK_USER_NAME));
                    expparam.set(InventoryCheckWorkListParams.SYS_DAY, outparam.get(FaRetrievalListDASCHParams.SYS_DAY));
                    expparam.set(InventoryCheckWorkListParams.SYS_TIME, outparam.get(FaRetrievalListDASCHParams.SYS_TIME));
                    expparam.set(InventoryCheckWorkListParams.STATION_NO, outparam.get(FaRetrievalListDASCHParams.STATION_NO));
                    expparam.set(InventoryCheckWorkListParams.STATION_NAME, outparam.get(FaRetrievalListDASCHParams.STATION_NAME));
                    expparam.set(InventoryCheckWorkListParams.FROM_SEARCH_DAY, viewState.getObject(ViewStateKeys.FROM_SEARCH_DAY));
                    expparam.set(InventoryCheckWorkListParams.FROM_SEARCH_TIME, viewState.getObject(ViewStateKeys.FROM_SEARCH_TIME));
                    expparam.set(InventoryCheckWorkListParams.TO_SEARCH_DAY, viewState.getObject(ViewStateKeys.TO_SEARCH_DAY));
                    expparam.set(InventoryCheckWorkListParams.TO_SEARCH_TIME, viewState.getObject(ViewStateKeys.TO_SEARCH_TIME));
                    expparam.set(InventoryCheckWorkListParams.WORK_NO, outparam.get(FaRetrievalListDASCHParams.WORK_NO));
                    expparam.set(InventoryCheckWorkListParams.LOCATION_NO, outparam.get(FaRetrievalListDASCHParams.LOCATION_NO));
                    expparam.set(InventoryCheckWorkListParams.ITEM_CODE, outparam.get(FaRetrievalListDASCHParams.ITEM_CODE));
                    expparam.set(InventoryCheckWorkListParams.ITEM_NAME, outparam.get(FaRetrievalListDASCHParams.ITEM_NAME));
                    expparam.set(InventoryCheckWorkListParams.LOT_NO, outparam.get(FaRetrievalListDASCHParams.LOT_NO));
                    expparam.set(InventoryCheckWorkListParams.STOCK_QTY, outparam.get(FaRetrievalListDASCHParams.STOCK_QTY));
                    expparam.set(InventoryCheckWorkListParams.AREA_NO, outparam.get(FaRetrievalListDASCHParams.AREA_NO));
                    if (!exporter.write(expparam))
                    {
                        break;
                    }
                }
    
                // execute print.
                try
                {
                    exporter.print();
                    message.setMsgResourceKey("6001010");
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    message.setMsgResourceKey("6007034");
                    return;
                }
            }
            
            // write part11 log.
            for (int i = 1; i <= _lcm_lst_FaRetrievalWorkList.size(); i++)
            {
                ListCellLine line = _lcm_lst_FaRetrievalWorkList.get(i);
                lst_FaRetrievalWorkList.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_STATION_NO), "");
                part11List.add(line.getViewString(KEY_HIDDEN_WORK_TYPE), "");
                part11List.add(line.getViewString(KEY_LST_SETTING_DATE), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PRINT), part11List);
            }
            // commit.
            conn.commit();

            // DFKLOOK END

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Preview_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_FaRetrievalWorkList.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_FaRetrievalWorkList.get(i);
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
        FaRetrievalListDASCH dasch = null;
        PrintExporter exporter = null;
        File downloadFile = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new FaRetrievalListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            //DFKLOOK:ここから修正
            List<String> ukeyList = new ArrayList<String>();
            List<String> stList = new ArrayList<String>();
            for (int i = 1; i <= _lcm_lst_FaRetrievalWorkList.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_FaRetrievalWorkList.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                // 選択された設定単位キーを追加
                ukeyList.add(line.getStringValue(KEY_HIDDEN_SETTING_UNIT_KEY));
                // 選択されたステーションを追加
                stList.add(line.getStringValue(KEY_LST_STATION_NO));
            }
            
            // 設定単位キーのみセットし、全体の発行件数をチェックするようにする
            FaRetrievalListDASCHParams inparam = new FaRetrievalListDASCHParams();
            inparam.set(FaRetrievalListDASCHParams.SETTING_UNIT_KEY, ukeyList);
            if (stList.size() > 1)
            {
                inparam.set(FaRetrievalListDASCHParams.STATION_NO, stList);
            }

            // check count.
            int count = dasch.count(inparam);
            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }

            // AS/RS出庫作業リスト用のパラメータを作成
            FaRetrievalListDASCHParams printAsParam = new FaRetrievalListDASCHParams();
            printAsParam = getPrintListParam(RetrievalInParameter.SEARCH_ASRS_RETRIEVAL_LIST);
            
            // パラメータがnullの場合は印刷処理を行わない。
            if (printAsParam != null && dasch.count(printAsParam) > 0)
            {
                // DASCH call.
                dasch.search(printAsParam);

                // open exporter.
                ExporterFactory factory = new WmsExporterFactory(locale, ui);
                exporter = factory.newPVExporter("AsRetrievalWorkList", getSession());
                exporter.open();
    
                // export.
                while (dasch.next())
                {
                    Params outparam = dasch.get();
                    AsRetrievalWorkListParams expparam = new AsRetrievalWorkListParams();
                    expparam.set(AsRetrievalWorkListParams.DFK_DS_NO, outparam.get(FaRetrievalListDASCHParams.DFK_DS_NO));
                    expparam.set(AsRetrievalWorkListParams.DFK_USER_ID, outparam.get(FaRetrievalListDASCHParams.DFK_USER_ID));
                    expparam.set(AsRetrievalWorkListParams.DFK_USER_NAME, outparam.get(FaRetrievalListDASCHParams.DFK_USER_NAME));
                    expparam.set(AsRetrievalWorkListParams.SYS_DAY, outparam.get(FaRetrievalListDASCHParams.SYS_DAY));
                    expparam.set(AsRetrievalWorkListParams.SYS_TIME, outparam.get(FaRetrievalListDASCHParams.SYS_TIME));
                    expparam.set(AsRetrievalWorkListParams.STATION_NO, outparam.get(FaRetrievalListDASCHParams.STATION_NO));
                    expparam.set(AsRetrievalWorkListParams.STATION_NAME, outparam.get(FaRetrievalListDASCHParams.STATION_NAME));
                    expparam.set(AsRetrievalWorkListParams.FROM_SEARCH_DAY, viewState.getObject(ViewStateKeys.FROM_SEARCH_DAY));
                    expparam.set(AsRetrievalWorkListParams.FROM_SEARCH_TIME, viewState.getObject(ViewStateKeys.FROM_SEARCH_TIME));
                    expparam.set(AsRetrievalWorkListParams.TO_SEARCH_DAY, viewState.getObject(ViewStateKeys.TO_SEARCH_DAY));
                    expparam.set(AsRetrievalWorkListParams.TO_SEARCH_TIME, viewState.getObject(ViewStateKeys.TO_SEARCH_TIME));
                    expparam.set(AsRetrievalWorkListParams.WORK_NO, outparam.get(FaRetrievalListDASCHParams.WORK_NO));
                    expparam.set(AsRetrievalWorkListParams.LOCATION_NO, outparam.get(FaRetrievalListDASCHParams.LOCATION_NO));
                    expparam.set(AsRetrievalWorkListParams.ITEM_CODE, outparam.get(FaRetrievalListDASCHParams.ITEM_CODE));
                    expparam.set(AsRetrievalWorkListParams.ITEM_NAME, outparam.get(FaRetrievalListDASCHParams.ITEM_NAME));
                    expparam.set(AsRetrievalWorkListParams.LOT_NO, outparam.get(FaRetrievalListDASCHParams.LOT_NO));
                    expparam.set(AsRetrievalWorkListParams.RETRIEVAL_COMMAND_DETAIL, outparam.get(FaRetrievalListDASCHParams.RETRIEVAL_COMMAND_DETAIL));
                    expparam.set(AsRetrievalWorkListParams.PRIORITY_FLAG, outparam.get(FaRetrievalListDASCHParams.PRIORITY_FLAG));
                    expparam.set(AsRetrievalWorkListParams.WORK_QTY, outparam.get(FaRetrievalListDASCHParams.WORK_QTY));
                    expparam.set(AsRetrievalWorkListParams.STOCK_QTY, outparam.get(FaRetrievalListDASCHParams.STOCK_QTY));
                    expparam.set(AsRetrievalWorkListParams.BATCH_NO, outparam.get(FaRetrievalListDASCHParams.BATCH_NO));
                    expparam.set(AsRetrievalWorkListParams.TICKET_NO, outparam.get(FaRetrievalListDASCHParams.TICKET_NO));
                    expparam.set(AsRetrievalWorkListParams.LINE_NO, StringUtil.isBlank(outparam.get(FaRetrievalListDASCHParams.TICKET_NO)) ? null :outparam.get(FaRetrievalListDASCHParams.LINE_NO));
                    expparam.set(AsRetrievalWorkListParams.AREA_NO, outparam.get(FaRetrievalListDASCHParams.AREA_NO));
                    if (!exporter.write(expparam))
                    {
                        break;
                    }
                }
    
                // execute print.
                try
                {
                    downloadFile = exporter.print();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    message.setMsgResourceKey("6007034");
                    return;
                }

                // redirect.
                previewPDF(downloadFile.getPath());
            }
            
            // 平置入庫作業リスト用のパラメータを作成
            FaRetrievalListDASCHParams printFloorParam = new FaRetrievalListDASCHParams();
            printFloorParam = getPrintListParam(RetrievalInParameter.SEARCH_FLOOR_RETRIEVAL_LIST);
            
            // パラメータがnullの場合は印刷処理を行わない。
            if (printFloorParam != null && dasch.count(printFloorParam) > 0)
            {
                // DASCH call.
                dasch.search(printFloorParam);

                // open exporter.
                ExporterFactory factory = new WmsExporterFactory(locale, ui);
                exporter = factory.newPVExporter("RetrievalWorkList", getSession());
                exporter.open();
    
                // export.
                while (dasch.next())
                {

                   Params outparam = dasch.get();
                   RetrievalWorkListParams expparam = new RetrievalWorkListParams();
                   expparam.set(RetrievalWorkListParams.DFK_DS_NO, outparam.get(FaRetrievalListDASCHParams.DFK_DS_NO));
                   expparam.set(RetrievalWorkListParams.DFK_USER_ID, outparam.get(FaRetrievalListDASCHParams.DFK_USER_ID));
                   expparam.set(RetrievalWorkListParams.DFK_USER_NAME, outparam.get(FaRetrievalListDASCHParams.DFK_USER_NAME));
                   expparam.set(RetrievalWorkListParams.SYS_DAY, outparam.get(FaRetrievalListDASCHParams.SYS_DAY));
                   expparam.set(RetrievalWorkListParams.SYS_TIME, outparam.get(FaRetrievalListDASCHParams.SYS_TIME));
                   expparam.set(RetrievalWorkListParams.LIST_NO, outparam.get(FaRetrievalListDASCHParams.SETTING_UNIT_KEY));
                   expparam.set(RetrievalWorkListParams.FROM_SEARCH_DAY, viewState.getObject(ViewStateKeys.FROM_SEARCH_DAY));
                   expparam.set(RetrievalWorkListParams.FROM_SEARCH_TIME, viewState.getObject(ViewStateKeys.FROM_SEARCH_TIME));
                   expparam.set(RetrievalWorkListParams.TO_SEARCH_DAY, viewState.getObject(ViewStateKeys.TO_SEARCH_DAY));
                   expparam.set(RetrievalWorkListParams.TO_SEARCH_TIME, viewState.getObject(ViewStateKeys.TO_SEARCH_TIME));
                   expparam.set(RetrievalWorkListParams.AREA_NO, outparam.get(FaRetrievalListDASCHParams.AREA_NO));
                   expparam.set(RetrievalWorkListParams.LOCATION_NO, outparam.get(FaRetrievalListDASCHParams.LOCATION_NO));
                   expparam.set(RetrievalWorkListParams.ITEM_CODE, outparam.get(FaRetrievalListDASCHParams.ITEM_CODE));
                   expparam.set(RetrievalWorkListParams.ITEM_NAME, outparam.get(FaRetrievalListDASCHParams.ITEM_NAME));
                   expparam.set(RetrievalWorkListParams.LOT_NO, outparam.get(FaRetrievalListDASCHParams.LOT_NO));
                   expparam.set(RetrievalWorkListParams.RETRIEVAL_COMMAND_DETAIL, outparam.get(FaRetrievalListDASCHParams.RETRIEVAL_COMMAND_DETAIL));
                   expparam.set(RetrievalWorkListParams.PRIORITY_FLAG, outparam.get(FaRetrievalListDASCHParams.PRIORITY_FLAG));
                   expparam.set(RetrievalWorkListParams.WORK_QTY, outparam.get(FaRetrievalListDASCHParams.WORK_QTY));
                   expparam.set(RetrievalWorkListParams.STOCK_QTY, outparam.get(FaRetrievalListDASCHParams.STOCK_QTY));
                   expparam.set(RetrievalWorkListParams.BATCH_NO, outparam.get(FaRetrievalListDASCHParams.BATCH_NO));
                   expparam.set(RetrievalWorkListParams.TICKET_NO, outparam.get(FaRetrievalListDASCHParams.TICKET_NO));
                   expparam.set(RetrievalWorkListParams.LINE_NO, StringUtil.isBlank(outparam.get(FaRetrievalListDASCHParams.TICKET_NO)) ? null:outparam.get(FaRetrievalListDASCHParams.LINE_NO));
                    if (!exporter.write(expparam))
                    {
                        break;
                    }
                }
    
                // execute print.
                try
                {
                    downloadFile = exporter.print();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    message.setMsgResourceKey("6007034");
                    return;
                }

                // redirect.
                previewPDF(downloadFile.getPath());
            }
            
            // 在庫確認用リスト用のパラメータを作成
            FaRetrievalListDASCHParams printInventryParam = new FaRetrievalListDASCHParams();
            printInventryParam = getPrintListParam(RetrievalInParameter.SEARCH_INVENTORY_CHECK_LIST);
            
            // パラメータがnullの場合は印刷処理を行わない。
            if (printInventryParam != null && dasch.count(printInventryParam) > 0)
            {
                // DASCH call.
                dasch.search(printInventryParam);

                // open exporter.
                ExporterFactory factory = new WmsExporterFactory(locale, ui);
                exporter = factory.newPVExporter("InventoryCheckWorkList", getSession());
                exporter.open();
    
                // export.
                while (dasch.next())
                {
                    Params outparam = dasch.get();
                    InventoryCheckWorkListParams expparam = new InventoryCheckWorkListParams();
                    expparam.set(InventoryCheckWorkListParams.DFK_DS_NO, outparam.get(FaRetrievalListDASCHParams.DFK_DS_NO));
                    expparam.set(InventoryCheckWorkListParams.DFK_USER_ID, outparam.get(FaRetrievalListDASCHParams.DFK_USER_ID));
                    expparam.set(InventoryCheckWorkListParams.DFK_USER_NAME, outparam.get(FaRetrievalListDASCHParams.DFK_USER_NAME));
                    expparam.set(InventoryCheckWorkListParams.SYS_DAY, outparam.get(FaRetrievalListDASCHParams.SYS_DAY));
                    expparam.set(InventoryCheckWorkListParams.SYS_TIME, outparam.get(FaRetrievalListDASCHParams.SYS_TIME));
                    expparam.set(InventoryCheckWorkListParams.STATION_NO, outparam.get(FaRetrievalListDASCHParams.STATION_NO));
                    expparam.set(InventoryCheckWorkListParams.STATION_NAME, outparam.get(FaRetrievalListDASCHParams.STATION_NAME));
                    expparam.set(InventoryCheckWorkListParams.FROM_SEARCH_DAY, viewState.getObject(ViewStateKeys.FROM_SEARCH_DAY));
                    expparam.set(InventoryCheckWorkListParams.FROM_SEARCH_TIME, viewState.getObject(ViewStateKeys.FROM_SEARCH_TIME));
                    expparam.set(InventoryCheckWorkListParams.TO_SEARCH_DAY, viewState.getObject(ViewStateKeys.TO_SEARCH_DAY));
                    expparam.set(InventoryCheckWorkListParams.TO_SEARCH_TIME, viewState.getObject(ViewStateKeys.TO_SEARCH_TIME));
                    expparam.set(InventoryCheckWorkListParams.WORK_NO, outparam.get(FaRetrievalListDASCHParams.WORK_NO));
                    expparam.set(InventoryCheckWorkListParams.LOCATION_NO, outparam.get(FaRetrievalListDASCHParams.LOCATION_NO));
                    expparam.set(InventoryCheckWorkListParams.ITEM_CODE, outparam.get(FaRetrievalListDASCHParams.ITEM_CODE));
                    expparam.set(InventoryCheckWorkListParams.ITEM_NAME, outparam.get(FaRetrievalListDASCHParams.ITEM_NAME));
                    expparam.set(InventoryCheckWorkListParams.LOT_NO, outparam.get(FaRetrievalListDASCHParams.LOT_NO));
                    expparam.set(InventoryCheckWorkListParams.STOCK_QTY, outparam.get(FaRetrievalListDASCHParams.STOCK_QTY));
                    expparam.set(InventoryCheckWorkListParams.AREA_NO, outparam.get(FaRetrievalListDASCHParams.AREA_NO));
                    if (!exporter.write(expparam))
                    {
                        break;
                    }
                }
    
	            // execute print.
	            try
	            {
	                downloadFile = exporter.print();
	            }
	            catch (Exception ex)
	            {
	                ex.printStackTrace();
	                message.setMsgResourceKey("6007034");
	                return;
	            }

                // redirect.
                previewPDF(downloadFile.getPath());
            //DFKLOOK:ここまで修正
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_FaRetrievalWorkList.size(); i++)
            {
                ListCellLine line = _lcm_lst_FaRetrievalWorkList.get(i);
                lst_FaRetrievalWorkList.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_STATION_NO), "");
                part11List.add(line.getViewString(KEY_HIDDEN_WORK_TYPE), "");
                part11List.add(line.getViewString(KEY_LST_SETTING_DATE), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PREVIEW), part11List);
            }
            // commit.
            conn.commit();

            // DFKLOOK start
            setFocus(null);
            // DFKLOOK end
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
            DBUtil.rollback(conn);
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
        for (int i = 1; i <= _lcm_lst_FaRetrievalWorkList.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_FaRetrievalWorkList.get(i);
            lst_FaRetrievalWorkList.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_FaRetrievalWorkList_SetLineToolTip(clearLine);
            _lcm_lst_FaRetrievalWorkList.set(i, clearLine);
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
        for (int i = 1; i <= _lcm_lst_FaRetrievalWorkList.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_FaRetrievalWorkList.get(i);
            lst_FaRetrievalWorkList.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_FaRetrievalWorkList_SetLineToolTip(clearLine);
            _lcm_lst_FaRetrievalWorkList.set(i, clearLine);
        }

    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_FaRetrievalWorkList.clear();
        _pager.clear();
        btn_Print.setEnabled(false);
        btn_Preview.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);

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
