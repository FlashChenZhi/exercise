// $Id: MachineBusiness.java 7259 2010-02-26 06:02:47Z kanda $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.machine;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.schedule.Creater;
import jp.co.daifuku.wms.asrs.tool.schedule.CreaterFactory;
import jp.co.daifuku.wms.asrs.tool.schedule.MachineParameter;
import jp.co.daifuku.wms.asrs.tool.schedule.ToolScheduleInterface;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownData;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownHelper;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolTipHelper;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ScheduleInterface;
import jp.co.daifuku.common.text.DisplayText;

/**
 * 機器情報設定画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>kaneko</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7259 $, $Date: 2010-02-26 15:02:47 +0900 (金, 26 2 2010) $
 * @author  $Author: kanda $
 */
public class MachineBusiness
        extends Machine
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
        if (pul_AGCNo.getEnabled())
        {
            setFocus(pul_AGCNo);
        }
        else if (pul_MachineTypeCode.getEnabled())
        {
            setFocus(pul_MachineTypeCode);
        }
        else
        {
            setFocus(txt_MachineNumber);
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
                //pul_AGCNo.setEnabled(false);
                //pul_MachineTypeCode.setEnabled(false);
                //pul_StationNumber.setEnabled(false);
                btn_Add.setEnabled(false);
                btn_Clear.setEnabled(false);
                btn_Commit.setEnabled(false);
                btn_Cancel.setEnabled(false);

                message.setMsgResourceKey("6023089");
                return;
            }

            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);

            CreaterFactory factory = new CreaterFactory(conn, CreaterFactory.MACHINE, Creater.M_CREATE);
            //セッションに保持
            this.getSession().setAttribute("FACTORY", factory);

            ToolPulldownData pull = new ToolPulldownData(conn, locale, null);
            // プルダウン表示データ（AGCNo）
            String[] agcno = pull.getGroupControllerPulldownData("");
            // プルダウン表示データ（機種コード）
            String[] machinetype = pull.getMachineTypePulldownData("");
            // プルダウン表示データ（ステーションNo）
            String[] stno = pull.getAllStationPulldownData("", 2);
            //プルダウンデータをプルダウンへセット
            ToolPulldownHelper.setPullDown(pul_AGCNo, agcno);
            ToolPulldownHelper.setPullDown(pul_MachineTypeCode, machinetype);
            ToolPulldownHelper.setPullDown(pul_StationNumber, stno);

            MachineParameter[] array = (MachineParameter[])factory.query(conn, locale, null);

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
        String agcno = "";
        String machinetypename = "";
        String machineno = "";
        String stno = "";
        String stname = "";
        // DFKLOOK 20100222追加
        String machinename="";

        ///TOOL TIPの銘板
        String title_STName = DisplayText.getText("LBL-W9015");

        ToolFindUtil findutil = new ToolFindUtil(conn);

        //行をすべて削除
        lst_MachineStatus.clearRow();

        //パラメータ取得
        Parameter[] paramarray = factory.getAllParameters();
        for (int i = 0; i < paramarray.length; i++)
        {
            MachineParameter param = (MachineParameter)paramarray[i];
            //リストへ追加するパラメータ
            agcno = String.valueOf(param.getControllerNumber());
            machinetypename = findutil.getMachineTypeName(param.getMachineType());
            machineno = String.valueOf(param.getMachineNumber());
            stno = param.getStationNo();
            stname = findutil.getStationName(param.getStationNo());
            // DFKLOOK 20100222追加
            machinename = param.getDeviceName();
            
            //行追加
            //最終行を取得
            int count = lst_MachineStatus.getMaxRows();
            lst_MachineStatus.setCurrentRow(count);
            lst_MachineStatus.addRow();
            lst_MachineStatus.setValue(3, agcno);
            lst_MachineStatus.setValue(4, machinetypename);
            lst_MachineStatus.setValue(5, machineno);
            lst_MachineStatus.setValue(6, stno);
            lst_MachineStatus.setValue(7, stname);
            // DFKLOOK 20100222追加
            lst_MachineStatus.setValue(8, machinename);

            
            ToolTipHelper toolTip = new ToolTipHelper();
            toolTip.add(title_STName, stname);

            //TOOL TIPをセットする    
            lst_MachineStatus.setToolTip(count, toolTip.getText());
        }

        // 修正中の行をハイライト表示にする
        int modindex = factory.changeLineNo();
        if (modindex > -1)
        {
            lst_MachineStatus.setHighlight(modindex + 1);
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
            txt_MachineNumber.validate(this, true);

            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);

            CreaterFactory factory = (CreaterFactory)this.getSession().getAttribute("FACTORY");

            //<jp> スケジュールパラメータに入力された値をセットする。</jp>
            //<en> Set the entered value in schedule parameter.</en>
            MachineParameter param = new MachineParameter();
            int agcno = 0;

            if (pul_AGCNo.getSelectedValue() != null)
            {
                //<jp> プルダウンにデータがある場合</jp>
                //<en> If data is found in pull-down,</en>
                agcno = Integer.parseInt(pul_AGCNo.getSelectedValue());
            }

            param.setControllerNumber(agcno);
            param.setMachineType(Integer.parseInt(pul_MachineTypeCode.getSelectedValue()));
            param.setMachineNumber(Integer.parseInt(txt_MachineNumber.getText()));
            param.setStationNo(pul_StationNumber.getSelectedValue());
            param.setStationName(pul_StationNumber.getSelectedValue());
            //DFKLOOK 20100222追加
            param.setDeviceName(txt_MachineName.getText());

            if (factory.addParameter(conn, param))
            {
                //<jp> タメウチデータのセット</jp>
                //<en> Set the preset data.</en>
                setList(conn, factory);
                // 2007/07/10 操作性向上のため、クリア処理を削除
                // 表示エリアのクリア
                //btn_Clear_Click(null);
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

    /** <jp>
     * クリアボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when a clear button is pushed.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        pul_AGCNo.setSelectedIndex(0);
        pul_MachineTypeCode.setSelectedIndex(0);
        txt_MachineNumber.setText("");
        pul_StationNumber.setSelectedIndex(0);
		// DFKLOOK 20100222追加
        txt_MachineName.setText("");
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
            ex.printStackTrace();
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
    public void lst_MachineStatus_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            CreaterFactory factory = (CreaterFactory)this.getSession().getAttribute("FACTORY");
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);
            //修正、削除された行
            int index = lst_MachineStatus.getActiveRow();
            //**** 修正 ****
            if (lst_MachineStatus.getActiveCol() == 1)
            {
                //現在の行をセット
                lst_MachineStatus.setCurrentRow(index);
                //黄色にハイライト表示させる
                lst_MachineStatus.setHighlight(index);
                //修正中のパラメータをfactoryに設定する
                factory.changeParameter(index - 1);
                //<jp>ファクトリから修正中のパラメータだけを取得します。</jp>
                //<en>Retrieve from factory only the parameters being modified.</en>
                MachineParameter param = (MachineParameter)factory.getUpdatingParameter();
                //AGCNo
                pul_AGCNo.setSelectedItem(String.valueOf(param.getControllerNumber()));
                //機種コード
                pul_MachineTypeCode.setSelectedItem(String.valueOf(param.getMachineType()));
                //号機No
                txt_MachineNumber.setText(String.valueOf(param.getMachineNumber()));
                //ステーションNo
                pul_StationNumber.setSelectedItem(param.getStationNo());
                // DFKLOOK 20100222追加
                // 機器名称
                txt_MachineName.setText(param.getDeviceName());
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
