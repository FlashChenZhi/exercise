// $Id: WorkPlace1Business.java 87 2008-10-04 03:07:38Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.workplace;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.AsrsToolListBoxDefine;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationTypeHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationTypeSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWorkPlaceHandler;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.StationType;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.wms.asrs.tool.schedule.Creater;
import jp.co.daifuku.wms.asrs.tool.schedule.CreaterFactory;
import jp.co.daifuku.wms.asrs.tool.schedule.ToolScheduleInterface;
import jp.co.daifuku.wms.asrs.tool.schedule.WorkPlaceParameter;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownData;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.workplacelist.WorkPlaceListBusiness;
import jp.co.daifuku.wms.base.common.ListBoxDefine;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;

/**
 * ステーション設定（作業場）1画面目の画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Nakazawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class WorkPlace1Business
        extends WorkPlace1
        implements WMSToolConstants
{
    // Class fields --------------------------------------------------
    /** 
     * 格納区分の値を保持するキーです
     */
    public static final String WAREHOUSENUMBER_KEY = "WAREHOUSENUMBER_KEY";

    /** 
     * 作業場No.の値を保持するキーです
     */
    public static final String PARENTSTATIONNO_KEY = "PARENTSTATIONNO_KEY";

    /** 
     * 作業場名称の値を保持するキーです
     */
    public static final String PARENTSTATIONNAME_KEY = "PARENTSTATIONNAME_KEY";

    /** 
     * 作業種別の値を保持するキーです
     */
    public static final String WORKPLACETYPE_KEY = "WORKPLACETYPE_KEY";

    /** 
     * 代表ステーションの値を保持するキーです
     */
    public static final String MAINSTATION_KEY = "MAINSTATION_KEY";

    /** 
     * 製番フォルダの値を保持するキーです
     */
    public static final String FILEPATH_KEY = "FILEPATH_KEY";

    /** 
     * クラス名（倉庫）
     */
    private static final String CLASS_WAREHOUSE = "jp.co.daifuku.wms.base.dbhandler.WareHouseHandler";

    /** 
     * クラス名（アイル）
     */
    private static final String CLASS_AISLE = "jp.co.daifuku.wms.base.dbhandler.AisleHandler";

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------
    /** <jp>
     * 各コントロールイベント呼び出し前に呼び出されます。
     * @param e ActionEvent
     * @throws Exception 例外が発生した場合に通知されます。
     </jp> */
    /** <en>
     * It is called before each control event call.
     * @param e ActionEvent
     * @throws Exception 
     </en> */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        if (pul_StoreAs.getEnabled())
        {
            setFocus(pul_StoreAs);
        }
        else
        {
            setFocus(txt_ParentStNumber);
        }
    }

    /** <jp>
     * 画面の初期化を行います。
     * @param e ActionEvent
     * @throws Exception 例外が発生した場合に通知されます。
     </jp> */
    /** <en>
     * This screen is initialized.
     * @param e ActionEvent
     * @throws Exception 
     </en> */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        String menuparam = this.getHttpRequest().getParameter(MENUPARAM);

        if (menuparam != null)
        {
            //<jp>パラメータを取得</jp><en> A parameter is acquired. </en>
            String title = CollectionUtils.getMenuParam(0, menuparam);
            String functionID = CollectionUtils.getMenuParam(1, menuparam);
            String menuID = CollectionUtils.getMenuParam(2, menuparam);
            //<jp>ViewStateへ保持する</jp><en> It holds to ViewState. </en>
            this.getViewState().setString(M_MENUID_KEY, menuID);
            //<jp>画面名称をセットする</jp><en> A screen name is set. </en>
            lbl_SettingName.setResourceKey(title);
            //<jp>ヘルプファイルへのパスをセットする</jp><en> The path to a help file is set. </en>
            btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()));

            //ViewStateへ保持する
            this.getViewState().setString(M_TITLE_KEY, title);
            this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
            this.getViewState().setString(M_MENUID_KEY, menuID);
        }
        else if (!StringUtil.isBlank(this.getViewState().getString(M_TITLE_KEY)))
        {
            // 画面名称をセットする
            lbl_SettingName.setResourceKey(this.getViewState().getString(M_TITLE_KEY));
        }

        Connection conn = null;
        try
        {
            Locale locale = this.getHttpRequest().getLocale();
            //クリア処理
            btn_Clear_Click(null);

            //<jp>製番が入力されているかチェックします。</jp>
            if (!checkProduct())
            {
                btn_Clear.setEnabled(false);
                btn_Next.setEnabled(false);
                btn_Search.setEnabled(false);

                message.setMsgResourceKey("6023089");
                return;
            }

            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);

            CreaterFactory factory = new CreaterFactory(conn, CreaterFactory.WORKPLACE, Creater.M_CREATE);

            //セッションに保持
            this.getSession().setAttribute("FACTORY", factory);
            //<jp> プルダウン表示</jp>
            //<en> Display the pull-down list.</en>
            ToolPulldownData pull = new ToolPulldownData(conn, locale, null);

            // プルダウン表示データ（格納区分）
            String[] whno = pull.getWarehousePulldownData(ToolPulldownData.WAREHOUSE_AUTO, "");

            //プルダウンデータをプルダウンへセット
            ToolPulldownHelper.setPullDown(pul_StoreAs, whno);

            //<jp>保存ファイル名をセットする</jp>
            //<en>Set the name of the file the data will be saved.</en>
            WorkPlaceParameter searchParam = new WorkPlaceParameter();
            searchParam.setFilePath((String)this.getSession().getAttribute("WorkFolder"));
            //製番フォルダの値を保持します
            getViewState().setString(FILEPATH_KEY, (String)this.getSession().getAttribute("WorkFolder"));

        }
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            try
            {
                //コネクションクローズ
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException ee)
            {
                message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ee, this));
            }
        }
    }

    /** <jp>
     * ポップアップウインドから、戻ってくるときにこのメソッドが
     * 呼ばれます。Pageに定義されているpage_DlgBackをオーバライドします。
     * @param e ActionEvent
     * @throws Exception 例外が発生した場合に通知されます。
     </jp> */
    /** <en>
     * When it is returned, this method is called from Popup window.
     * @param e ActionEvent
     * @throws Exception 
     </en> */
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        DialogParameters param = ((DialogEvent)e).getDialogParameters();
        String stationno = param.getParameter(WorkPlaceListBusiness.STATIONNO_KEY);
        //空ではないときに値をセットする
        if (!StringUtil.isBlank(stationno))
        {
            txt_ParentStNumber.setText(stationno);
            txt_ParentStationName.setText(param.getParameter(WorkPlaceListBusiness.STATIONNAME_KEY));
            int worktype = Integer.parseInt(param.getParameter(WorkPlaceListBusiness.WORKPLACETYPE_KEY));
            switch (worktype)
            {
                // 代表ステーション
                case Station.MAIN_STATIONS:
                    chk_MainStation.setChecked(true);
                    break;
                // アイル独立
                case Station.STAND_ALONE_STATIONS:
                    chk_MainStation.setChecked(false);
                    rdo_StandAloneType.setChecked(true);
                    rdo_AisleConnectedType.setChecked(false);
                    break;
                // アイル結合
                case Station.AISLE_CONMECT_STATIONS:
                    chk_MainStation.setChecked(false);
                    rdo_StandAloneType.setChecked(false);
                    rdo_AisleConnectedType.setChecked(true);
                    break;
                default:
                    break;
            }
            chk_MainStation_Change(null);

        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    /** <jp>
     * 製番入力済のチェックを行います。
     * @return 製番が入力されている場合は、true、入力されていない場合は、falseを返します。
     </jp> */
    private boolean checkProduct()
    {
        //<jp>製番が入力されているかチェックします。</jp>
        String wf = (String)this.getSession().getAttribute("WorkFolder");

        //<jp>製番が入力されていない場合、ボタンすべてを無効にします。</jp>
        boolean product = false;
        if (wf != null)
        {
            if (wf.trim().length() != 0)
            {
                product = true;
            }
        }

        return product;
    }

    /** 
     * 指定された倉庫ナンバーから倉庫ステーションナンバーを取得します。
     * @param conn データベース接続用 Connection
     * @param  whNo 倉庫ナンバー
     * @return 倉庫ステーションナンバー
     * @throws Exception 例外が発生した場合に通知されます。
     */
    private String getWHSTNumber(Connection conn, String whNo)
            throws Exception
    {
        ToolWarehouseSearchKey wareKey = new ToolWarehouseSearchKey();
        ToolWarehouseHandler warehd = new ToolWarehouseHandler(conn);
        wareKey.setWarehouseNo(Integer.parseInt(whNo));
        Warehouse[] house = (Warehouse[])warehd.find(wareKey);
        if (house.length <= 0)
        {
            return "";
        }
        return house[0].getStationNo();
    }

    // Event handler methods -----------------------------------------
    /**
     * メニューへボタンがクリックされたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
        forward(BusinessClassHelper.getSubMenuPath(getViewState().getString(M_MENUID_KEY)));
    }

    /** <jp>
     * 検索ボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when a reference button is pushed.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Search_Click(ActionEvent e)
            throws Exception
    {
        //作業場一覧画面へ遷移する
        ForwardParameters param = new ForwardParameters();
        param.setParameter(WorkPlaceListBusiness.STATIONNO_KEY, txt_ParentStNumber.getText());
        param.setParameter(WorkPlaceListBusiness.WHSTATIONNO_KEY, pul_StoreAs.getSelectedValue());
        //処理中画面->結果画面
        redirect(AsrsToolListBoxDefine.LST_TOOL_WORK_PLACE, param, ListBoxDefine.LST_PROGRESS);
    }

    /**
     * 次へボタンが押下されたとき呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     */
    public void btn_Next_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            //入力チェック
            txt_ParentStNumber.validate(this, true);
            
            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);
            //<jp> WAREHOUSE表に登録されているかチェック</jp>
            //<en> Check to see if the data is registered in WAREHOUSE table.</en>
            if (pul_StoreAs.getSelectedValue() == null)
            {
                //<jp> 倉庫情報がありません。倉庫設定画面で登録してください</jp>
                //<en> There is no information of the warheouse. Please register in warehouse setting screen.</en>
                message.setMsgResourceKey("6123100");
                return;
            }

            // システム定義チェック
            if (WmsParam.AUTO_SELECT_STATION.equals(txt_ParentStNumber.getText())
                    || WmsParam.NOPARENT_STATION_WPNO.equals(txt_ParentStNumber.getText()))
            {
                // 6023222=入力された{0}はシステムで使用しているため登録できません。
                message.setMsgResourceKey(WmsMessageFormatter.format(6023222, DisplayText.getText("LBL-W9038")));
                return;
            }
            
            //<jp> STATION表に登録されているかチェック</jp>
            //<en> Check to see if the data is registered in STATION table.</en>
            ToolStationSearchKey skey = new ToolStationSearchKey();
            ToolWorkPlaceHandler shdle = new ToolWorkPlaceHandler(conn);
            Station[] st = (Station[])shdle.find(skey);
            if (st.length <= 0)
            {
                //<jp> ステーション情報がありません。ステーション設定画面で登録してください</jp>
                //<en> There is no information of the station. Please register in station setting screen.</en>
                message.setMsgResourceKey("6123079");
                return;
            }

            //<jp>***作業場の場合***</jp>
            //<en>*** For workshop ***</en>
            if (!chk_MainStation.getChecked())
            {
                //<jp>設定できるステーション、作業場、代表ステーションが登録されているかチェック</jp>
                //<jp>ステーション種別で判断</jp>
                //<en>Check to see if there are any settable stations, workshops or main stations in registeration.</en>
                //<en>Determine by station type.</en>
                int[] temp_stationtype = {
                    Station.STATION_TYPE_IN,
                    Station.STATION_TYPE_OUT,
                    Station.STATION_TYPE_INOUT
                };
                ToolStationSearchKey wokey = new ToolStationSearchKey();
                ToolWorkPlaceHandler wohdle = new ToolWorkPlaceHandler(conn);
                wokey.setStationType(temp_stationtype);
                Station[] wo = (Station[])wohdle.find(wokey);
                if (wo.length <= 0)
                {
                    //<jp> ステーション／作業場情報がありません。ステーションはステーション設定画面で登録してください</jp>
                    //<en> There is no information of the station/warehouse.</en>
                    //<en> Please register the station in station setting screen.</en>
                    message.setMsgResourceKey("6123126");
                    return;
                }
            }
            //<jp>***代表ステーションの場合***</jp>
            //<en>*** For maiin stations ***</en>
            else
            {
                //<jp>設定できるステーション、代表ステーションが登録されているかチェック</jp>
                //<jp>送信可能区分で判断</jp>
                //<en>Check to see if there are any settable stations or main stations in registeration.</en>
                //<en>Determine by type of sendability.</en>
                ToolStationSearchKey wskey = new ToolStationSearchKey();
                ToolWorkPlaceHandler wshdle = new ToolWorkPlaceHandler(conn);
                wskey.setSendable(1);
                Station[] ws = (Station[])wshdle.find(wskey);
                if (ws.length <= 0)
                {
                    //<jp> ステーション情報がありません。ステーション設定画面で登録してください</jp>
                    //<en> There is no information of the station.</en>
                    //<en> Please register in station setting screen.</en>
                    message.setMsgResourceKey("6123079");
                    return;
                }
            }

            
            ToolStationSearchKey wstkey = new ToolStationSearchKey();
            ToolWorkPlaceHandler wsthdle = new ToolWorkPlaceHandler(conn);
            wstkey.setStationNo(txt_ParentStNumber.getText());
            Station[] wst = (Station[])wsthdle.find(wstkey);
            //<jp>***新規作業場の場合***</jp>
            //<en>*** For a new workshop ***</en>

            //<jp> 作業場名称必須入力</jp>
            //<en> Name of the workshop must be entered.</en>
            if (wst.length <= 0)
            {
                if (txt_ParentStationName.getText().trim().equals(""))
                {
                    //新規に登録する作業場は、作業場名称を入力してください。
                    message.setMsgResourceKey("6123108");
                    setFocus(txt_ParentStationName);
                    return;
                }
                txt_ParentStationName.validate(this, false);
            }

            //<jp>***入力された作業場がSTATION表に存在する場合***</jp>
            //<en>***If entered workshop is already registered in STATION table ***</en>
            if (wst.length > 0)
            {
                //<jp> 登録されている作業場／代表ステーションは格納区分を変更することができません</jp>
                //<en> Unable to modify the storage type of registered workshop/main station.</en>
                String whno = getWHSTNumber(conn, pul_StoreAs.getSelectedValue());
                if (!wst[0].getWarehouseStationNo().equals(whno))
                {
                    //<jp>6413170 = 入力された作業場は違う格納区分で設定されています</jp>
                    //<en>6413170 = Entered workshop is set with different storage type.</en>
                    message.setMsgResourceKey("6123170");
                    return;
                }
                //<jp>***作業場の場合***</jp>
                //<en>*** For workshop ***</en>

                if (!chk_MainStation.getChecked())
                {
                    //<jp> 登録されている作業場を代表ステーションに変更することができません</jp>
                    //<en> Unable to modify the registered workshop to the main station.</en>
                    if (wst[0].getWorkPlaceType() == 0 || wst[0].getWorkPlaceType() == 3)
                    {
                        //<jp>6413133 = 入力された作業場は、ステーションまたは代表ステーションとして設定されています</jp>
                        //<en>6413133 = Entered workshop is set as a station or a main station.</en>
                        message.setMsgResourceKey("6123133");
                        return;
                    }

                    //作業場種別
                    //アイル独立型
                    int workplacetype = 1;
                    RadioButton rdo = rdo_StandAloneType.getSelectedItem();
                    if (rdo.getId().equals("rdo_AisleConnectedType"))
                    {
                        //アイル結合型
                        workplacetype = 2;
                    }

                    //<jp> 登録されている作業場は作業場種別を変更することができません</jp>
                    //<en> Unable to modify the workshop type of registered workshop.</en>
                    if (wst[0].getWorkPlaceType() != workplacetype)
                    {
                        if (wst[0].getWorkPlaceType() == 1)
                        {
                            //<jp>6413132 = 入力された作業場は、アイル独立型作業場で設定されています</jp>
                            //<en>6413132 = Entered workshop is set as a stand alone type workshop.</en>
                            message.setMsgResourceKey("6123132");
                            return;
                        }
                        else if (wst[0].getWorkPlaceType() == 2)
                        {
                            //<jp>6413131 = 入力された作業場は、アイル結合型作業場で設定されています</jp>
                            //<en>6413131 = Entered workshop is set as an aisle connected type workshop.</en>
                            message.setMsgResourceKey("6123131");
                            return;
                        }
                    }
                }
                //<jp>***代表ステーションの場合***</jp>
                //<en>*** For the main station ***</en>
                else
                {
                    //<jp> 登録されている代表ステーションを作業場に変更することができません</jp>
                    //<en> Unable to modify the main station ion registration to a workshop.</en>
                    if (wst[0].getWorkPlaceType() != 3)
                    {
                        //<jp>6413136 = 入力された代表ステーションNo.は、ステーションまたは作業場として設定されています</jp>
                        //<en>6413136 = Entered main station no. is set as a station or a workshop.</en>
                        message.setMsgResourceKey("6123136");
                        return;
                    }
                }
            }
            //<jp>***入力された作業場がSTATIONTYPE表に存在する場合***</jp>
            //<en>*** If entered workshop is already registered in STATIONTYPE table ***</en>
            //<jp>倉庫ステーション、アイルステーションは選べません</jp>
            //<en>Unable to select the warehouse station or the aisle station.</en>
            String[] temp_class = {
                CLASS_WAREHOUSE,
                CLASS_AISLE
            };
            ToolStationTypeSearchKey tskey = new ToolStationTypeSearchKey();
            ToolStationTypeHandler thdle = new ToolStationTypeHandler(conn);
            tskey.setStationNo(txt_ParentStNumber.getText());
            tskey.setClassName(temp_class);
            StationType[] stp = (StationType[])thdle.find(tskey);
            if (stp.length > 0)
            {
                //<jp>6413149 入力された作業場No.は、倉庫ステーションまたはアイルステーションとして設定されています</jp>
                //<en>6413149 Entered workshop no. is registered as a warehouse station or an aisle station.</en>
                message.setMsgResourceKey("6123149");
                return;
            }

            //オープン用スケジューラ
            CreaterFactory factory = (CreaterFactory)this.getSession().getAttribute("FACTORY");
            WorkPlaceParameter searchParam = new WorkPlaceParameter();

            String whno = getWHSTNumber(conn, pul_StoreAs.getSelectedValue());

            //<jp>倉庫ステーションNo.</jp>
            //<en>warehouse station no.</en>
            searchParam.setWareHouseStationNo(whno);
            //<jp>作業場No.</jp>
            //<en>workshop no.</en>
            searchParam.setParentNumber(txt_ParentStNumber.getText());
            //<jp>代表ステーション</jp>
            //<en>main station</en>
            int mainstno = 0;
            if (chk_MainStation.getChecked())
            {
                mainstno = 1;
            }
            searchParam.setMainStation(mainstno);
            //<jp>保存ファイル名をセットする</jp>
            //<en>Set the name of the file the data will be saved.</en>
            searchParam.setFilePath(this.getViewState().getString(WorkPlace1Business.FILEPATH_KEY));

            WorkPlaceParameter[] array =
                    (WorkPlaceParameter[])factory.query(conn, this.getHttpRequest().getLocale(), searchParam);

            //<jp>データが取得できなかった場合</jp>
            //<en>In case the data could not be obtained,</en>
            if (array == null)
            {
                message.setMsgResourceKey(factory.getMessage());
                return;
            }
            else
            {
                for (int i = 0; i < array.length; i++)
                {
                    ((ToolScheduleInterface)factory).addInitialParameter(array[i]);
                }
            }

            //格納区分の値を保持します
            getViewState().setString(WAREHOUSENUMBER_KEY, pul_StoreAs.getSelectedValue());
            //作業場No.の値を保持します
            getViewState().setString(PARENTSTATIONNO_KEY, txt_ParentStNumber.getText());
            //作業場名称の値を保持します
            getViewState().setString(PARENTSTATIONNAME_KEY, txt_ParentStationName.getText());
            //作業場種別
            //アイル独立型
            int workplacetypenum = Station.STAND_ALONE_STATIONS;
            RadioButton rdo = rdo_StandAloneType.getSelectedItem();
            if (rdo.getId().equals("rdo_AisleConnectedType"))
            {
                //アイル結合型
                workplacetypenum = Station.AISLE_CONMECT_STATIONS;
            }
            //作業場種別の値を保持します
            getViewState().setString(WORKPLACETYPE_KEY, String.valueOf(workplacetypenum));
            //作業場名称の値を保持します
            getViewState().setString(MAINSTATION_KEY, String.valueOf(mainstno));

            //セッションに保持
            this.getSession().setAttribute("FACTORY", factory);
            forward("/asrs/tool/workplace/WorkPlace2.do");
        }
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            try
            {
                //コネクションクローズ
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException ee)
            {
                message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ee, this));
            }
        }
    }

    /**
     * クリアボタンが押下されたとき呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        pul_StoreAs.setSelectedIndex(0);
        txt_ParentStNumber.setText("");
        txt_ParentStationName.setText("");
        rdo_StandAloneType.setEnabled(true);
        rdo_AisleConnectedType.setEnabled(true);
        rdo_StandAloneType.setChecked(true);
        rdo_AisleConnectedType.setChecked(false);
        chk_MainStation.setChecked(false);
    }

    /** 
     * チェックボックスにチェックしたりチェックを取ったりした場合の処理を実装します
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     */
    public void chk_MainStation_Change(ActionEvent e)
            throws Exception
    {
        if (chk_MainStation.getChecked())
        {
            rdo_StandAloneType.setEnabled(false);
            rdo_AisleConnectedType.setEnabled(false);
        }
        else if (!chk_MainStation.getChecked())
        {
            rdo_StandAloneType.setEnabled(true);
            rdo_AisleConnectedType.setEnabled(true);
        }
    }
}
//end of class
