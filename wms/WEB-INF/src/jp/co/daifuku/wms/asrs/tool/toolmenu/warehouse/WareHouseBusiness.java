// $Id: WareHouseBusiness.java 5299 2009-10-28 05:34:56Z ota $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.warehouse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ScheduleInterface;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.wms.asrs.tool.schedule.Creater;
import jp.co.daifuku.wms.asrs.tool.schedule.CreaterFactory;
import jp.co.daifuku.wms.asrs.tool.schedule.ToolScheduleInterface;
import jp.co.daifuku.wms.asrs.tool.schedule.WarehouseParameter;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**
 * ロケーション設定(倉庫)の画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Nakazawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5299 $, $Date: 2009-10-28 14:34:56 +0900 (水, 28 10 2009) $
 * @author  $Author: ota $
 */
public class WareHouseBusiness
        extends WareHouse
        implements WMSToolConstants
{
    // Class fields --------------------------------------------------

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
        setFocus(txt_WareHouseNumber);
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
                btn_Add.setEnabled(false);
                btn_Clear.setEnabled(false);
                btn_Commit.setEnabled(false);
                btn_Cancel.setEnabled(false);

                message.setMsgResourceKey("6023089");
                return;
            }

            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);

            CreaterFactory factory = new CreaterFactory(conn, CreaterFactory.WAREHOUSE, Creater.M_CREATE);
            //セッションに保持
            this.getSession().setAttribute("FACTORY", factory);

            //<jp>保存ファイル名をセットする</jp>
            //<en>Set the name of the file the data will be saved.</en>
            WarehouseParameter searchParam = new WarehouseParameter();
            searchParam.setFilePath((String)this.getSession().getAttribute("WorkFolder"));

            WarehouseParameter[] array = (WarehouseParameter[])factory.query(conn, locale, searchParam);
            if (array != null)
            {
                for (int i = 0; i < array.length; i++)
                {
                    ((ToolScheduleInterface)factory).addInitialParameter(array[i]);
                }
            }
            //<jp> タメウチデータのセット</jp>
            //<en> Set the preset data.</en>
            setList(conn, factory);
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

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /** <jp>
     * ため打ちエリアに値をセットします。
     * @param conn Connection 
     * @param factory ScheduleInterface 
     * @throws Exception 
     </jp> */
    /** <en>
     * Set data to preset area.
     * @param conn Connection 
     * @param factory ScheduleInterface 
     * @throws Exception 
     </en> */
    private void setList(Connection conn, ScheduleInterface factory)
            throws Exception
    {
        String warehousenumber = "";
        String stationnumber = "";
        String warehousename = "";
        String employmenttype = "";
        String freeallocation = "";
        String locationsearch = "";
        String aislesearch = "";
        String maxmixedqty = "";
        String areano = "";
        String vacantsearchtype = "";
        String temporaryareatype = "";
        String temporaryarea = "";

        //行をすべて削除
        lst_WareHouse.clearRow();

        //パラメータ取得
        Parameter[] paramarray = factory.getAllParameters();
        for (int i = 0; i < paramarray.length; i++)
        {
            WarehouseParameter param = (WarehouseParameter)paramarray[i];
            //リストへ追加するパラメータ
            warehousenumber = String.valueOf(param.getWarehouseNumber());
            stationnumber = param.getStationNo();
            warehousename = param.getWarehouseName();
            //<jp> 自動倉庫運用種別</jp>
            //<en> division of automated warehouse operation</en>
            int employmenttypenum = param.getEmploymentType();
            if (employmenttypenum == 0)
            {
                employmenttypenum = Warehouse.OPEN;
            }
            employmenttype = DisplayText.getText("WAREHOUSE", "EMPLOYMENTTYPE", String.valueOf(employmenttypenum));

            freeallocation =
                    DisplayText.getText("WAREHOUSE", "FREEALLOCATION", String.valueOf(param.getFreeAllocationType()));

            //<jp> 空棚検索優先区分</jp>
            int locationsearchtypenum = param.getLocationSearchType();
            if (locationsearchtypenum == 1)
            {
                locationsearchtypenum = Warehouse.AISLE_PRIORITY;
            }
            else if (locationsearchtypenum == 2)
            {
                locationsearchtypenum = Warehouse.ZONE_PRIORITY;
            }
            
            locationsearch = DisplayText.getText("WAREHOUSE", "LOCATIONSEARCHTYPE", String.valueOf(locationsearchtypenum));
            
            //<jp> アイル検索優先区分</jp>
            int aislesearchtypenum = param.getLocationSearchType();
            if (aislesearchtypenum == 1)
            {
                aislesearchtypenum = Warehouse.ASCENDING;
            }
            else if (aislesearchtypenum == 2)
            {
                aislesearchtypenum = Warehouse.DESCENDING;
            }
            aislesearch = DisplayText.getText("WAREHOUSE", "AISLESEARCHTYPE", String.valueOf(aislesearchtypenum));
            
            
            maxmixedqty = String.valueOf(param.getMaxMixedQuantity());

            areano = param.getAreaNo();
            vacantsearchtype = DisplayText.getText("WAREHOUSE", "VACANTSEARCHTYPE", param.getVacantSearchType());
            temporaryareatype = DisplayText.getText("WAREHOUSE", "TEMPORARYAREATYPE", param.getTemporaryAreaType());
            temporaryarea = param.getTemporaryArea();

            //行追加
            //最終行を取得
            int count = lst_WareHouse.getMaxRows();
            lst_WareHouse.setCurrentRow(count);
            lst_WareHouse.addRow();
            lst_WareHouse.setValue(3, warehousenumber);
            lst_WareHouse.setValue(4, stationnumber);
            lst_WareHouse.setValue(5, employmenttype);
            lst_WareHouse.setValue(6, locationsearch);
            lst_WareHouse.setValue(7, maxmixedqty);
            lst_WareHouse.setValue(8, areano);
            lst_WareHouse.setValue(9, temporaryareatype);
            lst_WareHouse.setValue(10, warehousename);
            lst_WareHouse.setValue(11, freeallocation);
            lst_WareHouse.setValue(12, aislesearch);
            lst_WareHouse.setValue(13, vacantsearchtype);
            lst_WareHouse.setValue(14, temporaryarea);
        }
        // 修正中の行をハイライト表示にする
        int modindex = factory.changeLineNo();
        if (modindex > -1)
        {
            lst_WareHouse.setHighlight(modindex + 1);
        }
    }

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

    /** 
     * 自動倉庫ラジオボタンがクリックされたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     */
    public void rdo_AutomaticWareHouseSystem_Click(ActionEvent e)
            throws Exception
    {
        rdo_Open.setEnabled(true);
        rdo_Close.setEnabled(true);
        if (rdo_Open.getChecked())
        {
            rdo_Open.setChecked(true);
            rdo_Close.setChecked(false);
        }
        else if (rdo_Close.getChecked())
        {
            rdo_Open.setChecked(false);
            rdo_Close.setChecked(true);
        }
    }

    /** <jp>
     * 入力ボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when an Add button is pushed.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Add_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            //入力チェック
            txt_WareHouseNumber.validate(this, true);
            txt_StNumber.validate(this, true);
            txt_WareHouseName.validate(this, true);
            txt_MaxMixedQty.validate(this, true);
            txt_AreaNo.validate(this, true);

            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);

            CreaterFactory factory = (CreaterFactory)this.getSession().getAttribute("FACTORY");

            //<jp> スケジュールパラメータに入力された値をセットする。</jp>
            //<en> Set the entered value in schedule parameter.</en>
            WarehouseParameter param = new WarehouseParameter();

            param.setWarehouseNumber(Integer.parseInt(txt_WareHouseNumber.getText()));
            param.setStationNo(txt_StNumber.getText());
            param.setWarehouseName(txt_WareHouseName.getText());

            int employmenttype = Warehouse.OPEN;
            RadioButton rdoemploymenttype = rdo_Open.getSelectedItem();
            if (rdoemploymenttype.getId().equals("rdo_Close"))
            {
                employmenttype = Warehouse.CLOSE;
            }

            param.setEmploymentType(employmenttype);

            // フリーアロケーション運用
            // フリーアロケーション運用 : フリーアロケーション運用なし
            if (rdo_NotFreeAllocation.getChecked())
            {
                param.setFreeAllocationType(Warehouse.FREE_ALLOCATION_OFF);
            }
            // フリーアロケーション運用 : フリーアロケーション運用あり
            if (rdo_FreeAllocation.getChecked())
            {
                param.setFreeAllocationType(Warehouse.FREE_ALLOCATION_ON);
            }

            // 空棚検索優先区分
            // 空棚検索優先区分 : アイル優先
            if (rdo_AislePriority.getChecked())
            {
                param.setLocationSearchType(Warehouse.AISLE_PRIORITY);
            }
            // 空棚検索優区分 : ゾーン優先
            if (rdo_ZonePriority.getChecked())
            {
                param.setLocationSearchType(Warehouse.ZONE_PRIORITY);
            }
            
            // アイル検索優先区分
            // アイル検索優先区分 : 昇順
            if (rdo_Ascending.getChecked())
            {
                param.setAisleSearchType(Warehouse.ASCENDING);
            }
            // アイル検索優先区分 : 降順
            if (rdo_Descending.getChecked())
            {
                param.setAisleSearchType(Warehouse.DESCENDING);
            }
            
            param.setMaxMixedQuantity(txt_MaxMixedQty.getInt());
            param.setAreaNo(txt_AreaNo.getText());

            // 空棚検索方法
            // 空棚検索方向 : レベル方向検索(HP側から)
            if (rdo_VacantSearchTypeASRSLevelH.getChecked())
            {
                param.setVacantSearchType(SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_HP);
            }
            // 空棚検索方向 : レベル方向検索(OP側から)
            if (rdo_VacantSearchTypeAsrsLevelO.getChecked())
            {
                param.setVacantSearchType(SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_OP);
            }
            // 空棚検索方向 : ベイ方向検索(HP側から)
            if (rdo_VacantSearchTypeAsrsBayHP.getChecked())
            {
                param.setVacantSearchType(SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_HP);
            }
            // 空棚検索方向 : ベイ方向検索(OP側から)
            if (rdo_VacantSearchTypeAsrsBayOP.getChecked())
            {
                param.setVacantSearchType(SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_OP);
            }

            // 仮置在庫作成区分
            // 仮置在庫作成区分 : 作成しない
            if (rdo_NotCreate.getChecked())
            {
                param.setTemporaryAreaType(SystemDefine.TEMPORARY_AREA_TYPE_NONE);
            }
            // 仮置在庫作成区分 : 全ての在庫を作成する
            if (rdo_TemporaryArea.getChecked())
            {
                param.setTemporaryAreaType(SystemDefine.TEMPORARY_AREA_TYPE_ALL);
            }

            param.setTemporaryArea(txt_TemporaryArea.getText());

            if (factory.addParameter(conn, param))
            {
                //<jp> タメウチデータのセット</jp>
                //<en> Set the preset data.</en>
                setList(conn, factory);
            }
            //セッションに保持する
            this.getSession().setAttribute("FACTORY", factory);
            //<jp> メッセージをセット。</jp>
            //<en> Set the message.</en>
            message.setMsgResourceKey(factory.getMessage());
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
     * クリアボタンの処理を実装します。
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        txt_WareHouseNumber.setText("");
        txt_StNumber.setText("");
        txt_WareHouseName.setText("");
        rdo_Open.setChecked(true);
        rdo_Close.setChecked(false);
        txt_MaxMixedQty.setText("");
        rdo_Open.setEnabled(true);
        rdo_Close.setEnabled(true);
        rdo_NotFreeAllocation.setChecked(true);
        rdo_FreeAllocation.setChecked(false);

        rdo_AislePriority.setChecked(true);
        rdo_ZonePriority.setChecked(false);
        rdo_Ascending.setChecked(true);
        rdo_Descending.setChecked(false);
        
        txt_AreaNo.setText("");
        rdo_VacantSearchTypeASRSLevelH.setChecked(true);
        rdo_VacantSearchTypeAsrsLevelO.setChecked(false);
        rdo_VacantSearchTypeAsrsBayHP.setChecked(false);
        rdo_VacantSearchTypeAsrsBayOP.setChecked(false);
        rdo_NotCreate.setChecked(true);
        rdo_TemporaryArea.setChecked(false);
        txt_TemporaryArea.setText("");
    }

    /** <jp>
     * 設定ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     </jp> */
    /** <en>
     * It is called when a commit button is pushed.
     * @param e ActionEvent 
     * @throws Exception
     </en> */
    public void btn_Commit_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            CreaterFactory factory = (CreaterFactory)this.getSession().getAttribute("FACTORY");

            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);
            //<jp> スケジュールスタート</jp>
            //<en> Start the scheduling.</en>
            factory.startScheduler(conn);

            //rdo_Open.setChecked(true);
            //rdo_Close.setChecked(false);
            //<jp> メッセージをセット。</jp>
            //<en> Set the message.</en>
            message.setMsgResourceKey(factory.getMessage());
            //<jp> タメウチデータのセット</jp>
            //<en> Set the preset data.</en>
            setList(conn, factory);
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
     * 取り消しボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     </jp> */
    /** <en>
     * It is called when a cancel button is pushed.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Cancel_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            CreaterFactory factory = (CreaterFactory)this.getSession().getAttribute("FACTORY");
            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);
            //ためうち全削除
            factory.removeAllParameters(conn);
            //<jp> タメウチデータのセット</jp>
            //<en> Set the preset data.</en>
            setList(conn, factory);
            //<jp> メッセージをセット。</jp>
            //<en> Set the message.</en>
            message.setMsgResourceKey(factory.getMessage());
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
     * リストがクリックされたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     </jp> */
    /** <en>
     * It is called when it clicks on the list.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void lst_WareHouse_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            CreaterFactory factory = (CreaterFactory)this.getSession().getAttribute("FACTORY");
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);
            //修正、削除された行
            int index = lst_WareHouse.getActiveRow();
            //**** 修正 ****
            if (lst_WareHouse.getActiveCol() == 1)
            {
                //現在の行をセット
                lst_WareHouse.setCurrentRow(index);
                //黄色にハイライト表示させる
                lst_WareHouse.setHighlight(index);
                //修正中のパラメータをfactoryに設定する
                factory.changeParameter(index - 1);
                //<jp>ファクトリから修正中のパラメータだけを取得します。</jp>
                //<en>Retrieve from factory only the parameters being modified.</en>
                WarehouseParameter param = (WarehouseParameter)factory.getUpdatingParameter();
                //格納区分
                txt_WareHouseNumber.setText(String.valueOf(param.getWarehouseNumber()));
                //ステーションNo.
                txt_StNumber.setText(param.getStationNo());
                //倉庫名称
                txt_WareHouseName.setText(param.getWarehouseName());
                rdo_Open.setEnabled(true);
                rdo_Close.setEnabled(true);
                //自動倉庫運用種別
                if (param.getEmploymentType() == Warehouse.OPEN)
                {
                    rdo_Open.setChecked(true);
                    rdo_Close.setChecked(false);
                }
                else
                {
                    rdo_Open.setChecked(false);
                    rdo_Close.setChecked(true);
                }
                // フリーアロケーション運用 : フリーアロケーション運用なし
                if (param.getFreeAllocationType() == (Warehouse.FREE_ALLOCATION_OFF))
                {
                    rdo_NotFreeAllocation.setChecked(true);
                    rdo_FreeAllocation.setChecked(false);
                }
                // フリーアロケーション運用 : フリーアロケーション運用あり
                if (param.getFreeAllocationType() == (Warehouse.FREE_ALLOCATION_ON))
                {
                    rdo_NotFreeAllocation.setChecked(false);
                    rdo_FreeAllocation.setChecked(true);
                }
                
                // 空棚検索優先区分 : アイル優先
                if (param.getLocationSearchType() == (Warehouse.AISLE_PRIORITY))
                {
                    rdo_AislePriority.setChecked(true);
                    rdo_ZonePriority.setChecked(false);
                }
                // 空棚検索優先区分 : ゾーン優先
                if (param.getLocationSearchType() == (Warehouse.ZONE_PRIORITY))
                {
                    rdo_AislePriority.setChecked(false);
                    rdo_ZonePriority.setChecked(true);
                }
                
                // アイル検索優先区分 : 昇順
                if (param.getAisleSearchType() == (Warehouse.ASCENDING))
                {
                    rdo_Ascending.setChecked(true);
                    rdo_Descending.setChecked(false);
                }
                // アイル検索優先区分 : 降順
                if (param.getAisleSearchType() == (Warehouse.DESCENDING))
                {
                    rdo_Ascending.setChecked(false);
                    rdo_Descending.setChecked(true);
                }
                
                
                //最大混載数
                txt_MaxMixedQty.setText(Formatter.getNumFormat(param.getMaxMixedQuantity()));
                // エリアNo
                txt_AreaNo.setText(param.getAreaNo());
                // 空棚検索方法
                rdo_VacantSearchTypeASRSLevelH.setChecked(false);
                rdo_VacantSearchTypeAsrsLevelO.setChecked(false);
                rdo_VacantSearchTypeAsrsBayHP.setChecked(false);
                rdo_VacantSearchTypeAsrsBayOP.setChecked(false);
                // 空棚検索方向 : レベル方向検索(HP側から)
                if (param.getVacantSearchType().equals(SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_HP))
                {
                    rdo_VacantSearchTypeASRSLevelH.setChecked(true);
                }
                // 空棚検索方向 : レベル方向検索(OP側から)
                if (param.getVacantSearchType().equals(SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_OP))
                {
                    rdo_VacantSearchTypeAsrsLevelO.setChecked(true);
                }
                // 空棚検索方向 : ベイ方向検索(HP側から)
                if (param.getVacantSearchType().equals(SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_HP))
                {
                    rdo_VacantSearchTypeAsrsBayHP.setChecked(true);
                }
                // 空棚検索方向 : ベイ方向検索(OP側から)
                if (param.getVacantSearchType().equals(SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_OP))
                {
                    rdo_VacantSearchTypeAsrsBayOP.setChecked(true);
                }
                // 仮置在庫作成区分
                rdo_NotCreate.setChecked(false);
                rdo_TemporaryArea.setChecked(false);
                // 仮置在庫作成区分 : 作成しない
                if (param.getTemporaryAreaType().equals(SystemDefine.TEMPORARY_AREA_TYPE_NONE))
                {
                    rdo_NotCreate.setChecked(true);
                }
                // 仮置在庫作成区分 : 全ての在庫を作成する
                if (param.getTemporaryAreaType().equals(SystemDefine.TEMPORARY_AREA_TYPE_ALL))
                {
                    rdo_TemporaryArea.setChecked(true);
                }
                // 移動先仮置エリア
                txt_TemporaryArea.setText(param.getTemporaryArea());
            }
            //**** 削除 ****
            else
            {
                //ためうち１件削除
                factory.removeParameter(conn, index - 1);
                //<jp> タメウチデータのセット</jp>
                //<en> Set the preset data.</en>
                setList(conn, factory);
            }
            //<jp> メッセージをセット。</jp>
            //<en> Set the message.</en>
            message.setMsgResourceKey(factory.getMessage());
        }
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            try
            {
                //<jp>コネクションクローズ</jp>
                //<en> Close the connection.</en>
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


}
//end of class
