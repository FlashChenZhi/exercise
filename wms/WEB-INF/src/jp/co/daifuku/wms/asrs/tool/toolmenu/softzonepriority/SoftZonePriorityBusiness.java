// $Id: SoftZonePriorityBusiness.java 4289 2009-05-14 11:32:30Z okamura $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.softzonepriority;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ScheduleInterface;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.wms.asrs.tool.schedule.Creater;
import jp.co.daifuku.wms.asrs.tool.schedule.CreaterFactory;
import jp.co.daifuku.wms.asrs.tool.schedule.SoftZonePriorityParameter;
import jp.co.daifuku.wms.asrs.tool.schedule.ToolScheduleInterface;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownData;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownHelper;

/**
 * ソフトゾーン設定（優先順）の画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Nakazawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4289 $, $Date: 2009-05-14 20:32:30 +0900 (木, 14 5 2009) $
 * @author  $Author: okamura $
 */
public class SoftZonePriorityBusiness
        extends SoftZonePriority
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
        if (pul_StoreAs.getEnabled())
        {
            setFocus(pul_StoreAs);
        }
        else if (pul_ZoneId.getEnabled())
        {
            setFocus(pul_ZoneId);
        }
        else if (pul_PriorityZone.getEnabled())
        {
            setFocus(pul_ZoneId);
        }
        else
        {
            setFocus(txt_Priority);
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

            CreaterFactory factory = new CreaterFactory(conn, CreaterFactory.SOFTZONEPRIORITY, Creater.M_CREATE);
            //セッションに保持
            this.getSession().setAttribute("FACTORY", factory);

            //<jp> プルダウン表示</jp>
            //<en> Display the pull-down list.</en>
            ToolPulldownData pull = new ToolPulldownData(conn, locale, null);

            // プルダウン表示データ（格納区分）
            String[] whno =
                    pull.getWarehousePulldownData(ToolPulldownData.WAREHOUSE_AUTO, "", ToolPulldownData.ZONE_ALL);
            // プルダウン表示データ（ゾーンID）
            String[] zone = pull.getSoftZonePulldownData("");

            //プルダウンデータをプルダウンへセット
            ToolPulldownHelper.setPullDown(pul_StoreAs, whno);
            ToolPulldownHelper.setPullDown(pul_ZoneId, zone);
            ToolPulldownHelper.setPullDown(pul_PriorityZone, zone);

            SoftZonePriorityParameter[] array = (SoftZonePriorityParameter[])factory.query(conn, locale, null);
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
        String zoneid = "";
        String priorityzone = "";
        String priority = "";

        //行をすべて削除
        lst_SoftZonePriority.clearRow();

        //パラメータ取得
        Parameter[] paramarray = factory.getAllParameters();
        for (int i = 0; i < paramarray.length; i++)
        {
            SoftZonePriorityParameter param = (SoftZonePriorityParameter)paramarray[i];
            //リストへ追加するパラメータ
            warehousenumber = getWHNumber(conn, param.getWareHouseStationNo());
            zoneid = String.valueOf(param.getZoneID());
            priorityzone = String.valueOf(param.getPriorityZone());
            priority = String.valueOf(param.getPriority());

            //行追加
            //最終行を取得
            int count = lst_SoftZonePriority.getMaxRows();
            lst_SoftZonePriority.setCurrentRow(count);
            lst_SoftZonePriority.addRow();
            lst_SoftZonePriority.setValue(3, warehousenumber);
            lst_SoftZonePriority.setValue(4, zoneid);
            lst_SoftZonePriority.setValue(5, priorityzone);
            lst_SoftZonePriority.setValue(6, priority);
        }
        // 修正中の行をハイライト表示にする
        int modindex = factory.changeLineNo();
        if (modindex > -1)
        {
            lst_SoftZonePriority.setHighlight(modindex + 1);
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

    /** 
     * 指定された倉庫ステーションナンバーから倉庫ナンバーを取得します。
     * @param  conn コネクション
     * @param  whStNo 倉庫ステーションナンバー
     * @return 倉庫ナンバー
     * @throws Exception 例外が発生した場合に通知されます。
     */
    private String getWHNumber(Connection conn, String whStNo)
            throws Exception
    {
        ToolWarehouseSearchKey wareKey = new ToolWarehouseSearchKey();
        ToolWarehouseHandler warehd = new ToolWarehouseHandler(conn);
        wareKey.setWarehouseStationNo(whStNo);
        Warehouse[] house = (Warehouse[])warehd.find(wareKey);
        if (house.length <= 0)
        {
            return "*";
        }
        return String.valueOf(house[0].getWarehouseNo());
    }

    /** 
     * 指定された倉庫ナンバーから倉庫ステーションナンバーを取得します。
     * @param  conn コネクション
     * @param  whNo 倉庫ナンバー
     * @return 倉庫ステーションナンバー
     * @throws Exception 例外が発生した場合に通知されます。 
     */
    private String getWHSTNumber(Connection conn, String whNo)
            throws Exception
    {
        ToolWarehouseSearchKey wareKey = new ToolWarehouseSearchKey();
        ToolWarehouseHandler warehd = new ToolWarehouseHandler(conn);
        if (whNo == null)
        {
            return "9999";
        }
        wareKey.setWarehouseNo(Integer.parseInt(whNo));
        Warehouse[] house = (Warehouse[])warehd.find(wareKey);
        if (house.length <= 0)
        {
            return "9999";
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
            //プルダウンが正常に表示されていない場合
            if (pul_StoreAs.getSelectedValue() == null)
            {
                // 6123254=ソフトゾーン情報がありません。ソフトゾーン設定画面で登録してください。
                message.setMsgResourceKey("6123254");
                return;
            }
            //プルダウンが正常に表示されていない場合
            if (pul_ZoneId.getSelectedValue() == null)
            {
                // 6123254=ソフトゾーン情報がありません。ソフトゾーン設定画面で登録してください。
                message.setMsgResourceKey("6123254");
                return;
            }
            //プルダウンが正常に表示されていない場合
            if (pul_PriorityZone.getSelectedValue() == null)
            {
                // 6123254=ソフトゾーン情報がありません。ソフトゾーン設定画面で登録してください。
                message.setMsgResourceKey("6123254");
                return;
            }

            //入力チェック
            txt_Priority.validate(this, true);

            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);

            CreaterFactory factory = (CreaterFactory)this.getSession().getAttribute("FACTORY");

            //<jp> スケジュールパラメータに入力された値をセットする。</jp>
            //<en> Set the entered value in schedule parameter.</en>
            SoftZonePriorityParameter param = new SoftZonePriorityParameter();

            param.setWareHouseStationNo(getWHSTNumber(conn, pul_StoreAs.getSelectedValue()));
            param.setZoneID(Integer.parseInt(pul_ZoneId.getSelectedValue()));
            param.setPriorityZone(Integer.parseInt(pul_PriorityZone.getSelectedValue()));
            param.setPriority(Integer.parseInt(txt_Priority.getText()));

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
        pul_StoreAs.setSelectedIndex(0);
        pul_ZoneId.setSelectedIndex(0);
        pul_PriorityZone.setSelectedIndex(0);
        txt_Priority.setText("");
    }

    /** <jp>
     * 設定ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
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
     * @throws Exception 
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
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when it clicks on the list.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void lst_SoftZonePriority_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            CreaterFactory factory = (CreaterFactory)this.getSession().getAttribute("FACTORY");
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);
            //修正、削除された行
            int index = lst_SoftZonePriority.getActiveRow();
            //**** 修正 ****
            if (lst_SoftZonePriority.getActiveCol() == 1)
            {
                //クリア処理
                btn_Clear_Click(null);
                //現在の行をセット
                lst_SoftZonePriority.setCurrentRow(index);
                //黄色にハイライト表示させる
                lst_SoftZonePriority.setHighlight(index);
                //修正中のパラメータをfactoryに設定する
                factory.changeParameter(index - 1);
                //<jp>ファクトリから修正中のパラメータだけを取得します。</jp>
                //<en>Retrieve from factory only the parameters being modified.</en>
                SoftZonePriorityParameter param = (SoftZonePriorityParameter)factory.getUpdatingParameter();

                String whst = getWHNumber(conn, param.getWareHouseStationNo());
                pul_StoreAs.setSelectedItem(whst);
                pul_ZoneId.setSelectedItem(String.valueOf(param.getZoneID()));
                pul_PriorityZone.setSelectedItem(String.valueOf(param.getPriorityZone()));

                //パラメータに保持している値を画面にセットします。
                txt_Priority.setText(String.valueOf(param.getPriority()));
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
