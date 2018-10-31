// $Id: HardZoneBusiness.java 4289 2009-05-14 11:32:30Z okamura $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.hardzone;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ScheduleInterface;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.wms.asrs.tool.schedule.Creater;
import jp.co.daifuku.wms.asrs.tool.schedule.CreaterFactory;
import jp.co.daifuku.wms.asrs.tool.schedule.HardZoneParameter;
import jp.co.daifuku.wms.asrs.tool.schedule.ToolScheduleInterface;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownData;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolTipHelper;

/**
 * ハードゾーン設定（ハードゾーン範囲）の画面クラスです。
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
public class HardZoneBusiness
        extends HardZone
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
        else
        {
            setFocus(txt_HardZoneId);
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

            CreaterFactory factory = new CreaterFactory(conn, CreaterFactory.HARDZONE, Creater.M_CREATE);
            //セッションに保持
            this.getSession().setAttribute("FACTORY", factory);

            //<jp> プルダウン表示</jp>
            //<en> Display the pull-down list.</en>
            ToolPulldownData pull = new ToolPulldownData(conn, locale, null);

            // プルダウン表示データ（格納区分）
            String[] whno =
                    pull.getWarehousePulldownData(ToolPulldownData.WAREHOUSE_AUTO, "", ToolPulldownData.ZONE_ALL);

            //プルダウンデータをプルダウンへセット
            ToolPulldownHelper.setPullDown(pul_StoreAs, whno);

            HardZoneParameter[] array = (HardZoneParameter[])factory.query(conn, locale, null);
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
        String loadsize = "";
        String loadsizename = "";
        String priority = "";
        String bank = "";
        String bay = "";
        String level = "";
        String prioritychar = "";
        String fbank = "";
        String fbay = "";
        String flevel = "";
        String tbank = "";
        String tbay = "";
        String tlevel = "";

        ///TOOL TIPの銘板
        String title_loadsizename = DisplayText.getText("LBL-W9007");
        String title_priority = DisplayText.getText("LBL-W9054");

        //行をすべて削除
        lst_HardZone.clearRow();

        //パラメータ取得
        Parameter[] paramarray = factory.getAllParameters();
        for (int i = 0; i < paramarray.length; i++)
        {
            HardZoneParameter param = (HardZoneParameter)paramarray[i];
            //リストへ追加するパラメータ
            warehousenumber = getWHNumber(conn, param.getWareHouseStationNo());
            zoneid = String.valueOf(param.getZoneID());
            loadsize = String.valueOf(param.getHeight());
            loadsizename = param.getZoneName();
            fbank = String.valueOf(param.getStartBank());
            fbay = String.valueOf(param.getStartBay());
            flevel = String.valueOf(param.getStartLevel());
            tbank = String.valueOf(param.getEndBank());
            tbay = String.valueOf(param.getEndBay());
            tlevel = String.valueOf(param.getEndLevel());
            bank = fbank + " - " + tbank;
            bay = fbay + " - " + tbay;
            level = flevel + " - " + tlevel;
            priority = param.getPriority();
            if (priority != null)
            {
                priority = priority.trim();
                String castpriority = "";
                for (int count = 0; count < priority.length(); count++)
                {
                    if (count != 0)
                    {
                        castpriority = castpriority + "-";
                    }
                    castpriority = castpriority + priority.substring(count, count + 1);
                }
                prioritychar = castpriority;
            }

            //行追加
            //最終行を取得
            int count = lst_HardZone.getMaxRows();
            lst_HardZone.setCurrentRow(count);
            lst_HardZone.addRow();
            lst_HardZone.setValue(0, prioritychar);
            lst_HardZone.setValue(3, warehousenumber);
            lst_HardZone.setValue(4, zoneid);
            lst_HardZone.setValue(5, loadsize);
            lst_HardZone.setValue(6, loadsizename);
            lst_HardZone.setValue(7, bank);
            lst_HardZone.setValue(8, bay);
            lst_HardZone.setValue(9, level);
            lst_HardZone.setValue(10, prioritychar);

            //TOOL TIPに表示する文字列を作成
            ToolTipHelper toolTip = new ToolTipHelper();
            toolTip.add(title_loadsizename, loadsizename);
            toolTip.add(title_priority, prioritychar);


            //TOOL TIPをセットする    
            lst_HardZone.setToolTip(count, toolTip.getText());

        }
        // 修正中の行をハイライト表示にする
        int modindex = factory.changeLineNo();
        if (modindex > -1)
        {
            lst_HardZone.setHighlight(modindex + 1);
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
                //6123100 = 倉庫情報がありません。倉庫設定画面で登録してください
                message.setMsgResourceKey("6123100");
                return;
            }
            //入力チェック
            txt_HardZoneId.validate(this, true);
            txt_LoadSize.validate(this, true);
            txt_ZoneName.validate(this, true);
            txt_FBank.validate(this, true);
            txt_TBank.validate(this, true);
            txt_FBay.validate(this, true);
            txt_TBay.validate(this, true);
            txt_FLevel.validate(this, true);
            txt_TLevel.validate(this, true);
            txt_ZonePriority2.validate(this, false);
            txt_ZonePriority3.validate(this, false);
            txt_ZonePriority4.validate(this, false);
            txt_ZonePriority5.validate(this, false);
            txt_ZonePriority6.validate(this, false);
            txt_ZonePriority7.validate(this, false);
            txt_ZonePriority8.validate(this, false);
            txt_ZonePriority9.validate(this, false);
            txt_ZonePriority10.validate(this, false);

            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);

            CreaterFactory factory = (CreaterFactory)this.getSession().getAttribute("FACTORY");

            //<jp> スケジュールパラメータに入力された値をセットする。</jp>
            //<en> Set the entered value in schedule parameter.</en>
            HardZoneParameter param = new HardZoneParameter();

            param.setZoneID(Integer.parseInt(txt_HardZoneId.getText()));
            param.setHeight(Integer.parseInt(txt_LoadSize.getText()));
            param.setZoneName(txt_ZoneName.getText());
            param.setWareHouseStationNo(getWHSTNumber(conn, pul_StoreAs.getSelectedValue()));
            param.setStartBank(Integer.parseInt(txt_FBank.getText()));
            param.setStartBay(Integer.parseInt(txt_FBay.getText()));
            param.setStartLevel(Integer.parseInt(txt_FLevel.getText()));
            param.setEndBank(Integer.parseInt(txt_TBank.getText()));
            param.setEndBay(Integer.parseInt(txt_TBay.getText()));
            param.setEndLevel(Integer.parseInt(txt_TLevel.getText()));

            //<jp> 優先順位登録 (間にSPACEがあっても詰めて登録する)</jp>
            //<en> Set teh priority. (Any blanks should be ommitted by closing up )</en>
            String priority = txt_HardZoneId.getText();
            priority += txt_ZonePriority2.getText().trim();
            priority += txt_ZonePriority3.getText().trim();
            priority += txt_ZonePriority4.getText().trim();
            priority += txt_ZonePriority5.getText().trim();
            priority += txt_ZonePriority6.getText().trim();
            priority += txt_ZonePriority7.getText().trim();
            priority += txt_ZonePriority8.getText().trim();
            priority += txt_ZonePriority9.getText().trim();
            priority += txt_ZonePriority10.getText().trim();
            param.setPriority(priority);

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
        txt_HardZoneId.setText("");
        txt_LoadSize.setText("");
        txt_ZoneName.setText("");
        txt_FBank.setText("");
        txt_TBank.setText("");
        txt_FBay.setText("");
        txt_TBay.setText("");
        txt_FLevel.setText("");
        txt_TLevel.setText("");
        txt_ZonePriority2.setText("");
        txt_ZonePriority3.setText("");
        txt_ZonePriority4.setText("");
        txt_ZonePriority5.setText("");
        txt_ZonePriority6.setText("");
        txt_ZonePriority7.setText("");
        txt_ZonePriority8.setText("");
        txt_ZonePriority9.setText("");
        txt_ZonePriority10.setText("");
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
    public void lst_HardZone_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            CreaterFactory factory = (CreaterFactory)this.getSession().getAttribute("FACTORY");
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);
            //修正、削除された行
            int index = lst_HardZone.getActiveRow();
            //**** 修正 ****
            if (lst_HardZone.getActiveCol() == 1)
            {
                //クリア処理
                btn_Clear_Click(null);
                //現在の行をセット
                lst_HardZone.setCurrentRow(index);
                //黄色にハイライト表示させる
                lst_HardZone.setHighlight(index);
                //修正中のパラメータをfactoryに設定する
                factory.changeParameter(index - 1);
                //<jp>ファクトリから修正中のパラメータだけを取得します。</jp>
                //<en>Retrieve from factory only the parameters being modified.</en>
                HardZoneParameter param = (HardZoneParameter)factory.getUpdatingParameter();

                //格納区分
                String whst = getWHNumber(conn, param.getWareHouseStationNo());
                pul_StoreAs.setSelectedItem(whst);

                //パラメータに保持している値を画面にセットします。
                txt_HardZoneId.setText(String.valueOf(param.getZoneID()));
                txt_LoadSize.setText(String.valueOf(param.getHeight()));
                txt_ZoneName.setText(param.getZoneName());
                txt_FBank.setText(String.valueOf(param.getStartBank()));
                txt_FBay.setText(String.valueOf(param.getStartBay()));
                txt_FLevel.setText(String.valueOf(param.getStartLevel()));
                txt_TBank.setText(String.valueOf(param.getEndBank()));
                txt_TBay.setText(String.valueOf(param.getEndBay()));
                txt_TLevel.setText(String.valueOf(param.getEndLevel()));

                //優先順位をセットします。
                String priority = param.getPriority();
                if (priority != null)
                {
                    priority = priority.trim();
                    String castpriority = "";
                    for (int l = 0; l < priority.length(); l++)
                    {
                        if (l != 0)
                        {
                            castpriority = priority.substring(l, l + 1);
                            switch (l + 1)
                            {
                                case 2:
                                    txt_ZonePriority2.setText(castpriority);
                                    break;
                                case 3:
                                    txt_ZonePriority3.setText(castpriority);
                                    break;
                                case 4:
                                    txt_ZonePriority4.setText(castpriority);
                                    break;
                                case 5:
                                    txt_ZonePriority5.setText(castpriority);
                                    break;
                                case 6:
                                    txt_ZonePriority6.setText(castpriority);
                                    break;
                                case 7:
                                    txt_ZonePriority7.setText(castpriority);
                                    break;
                                case 8:
                                    txt_ZonePriority8.setText(castpriority);
                                    break;
                                case 9:
                                    txt_ZonePriority9.setText(castpriority);
                                    break;
                                case 10:
                                    txt_ZonePriority10.setText(castpriority);
                                    break;
                                default:
                            }
                        }
                    }
                }
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
