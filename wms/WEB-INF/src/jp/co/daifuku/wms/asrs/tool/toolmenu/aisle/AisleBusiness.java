// $Id: AisleBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.aisle;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.schedule.AisleParameter;
import jp.co.daifuku.wms.asrs.tool.schedule.Creater;
import jp.co.daifuku.wms.asrs.tool.schedule.CreaterFactory;
import jp.co.daifuku.wms.asrs.tool.schedule.ToolScheduleInterface;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownData;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownHelper;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ScheduleInterface;

/**
 * アイル設定画面クラスです。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 */
public class AisleBusiness
        extends Aisle
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
     * @throws Exception
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
            setFocus(txt_StNumber);
        }
    }

    /** <jp>
     * 画面の初期化を行います。
     * @param e ActionEvent
     * @throws Exception
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
                //pul_StoreAs.setEnabled(false);
                //pul_AGCNo.setEnabled(false);
                btn_Add.setEnabled(false);
                btn_Clear.setEnabled(false);
                btn_Commit.setEnabled(false);
                btn_Cancel.setEnabled(false);

                message.setMsgResourceKey("6023089");
                return;
            }

            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);

            ScheduleInterface factory = new CreaterFactory(conn, CreaterFactory.AISLE, Creater.M_CREATE);
            //セッションに保持
            this.getSession().setAttribute("FACTORY", factory);

            ToolPulldownData pull = new ToolPulldownData(conn, locale, null);

            //<jp> プルダウンデータのセット。</jp>
            //<en> Set the pull-down data.</en>
            String[] whno = pull.getWarehousePulldownData(ToolPulldownData.WAREHOUSE_AUTO, "");
            String[] agcno = pull.getGroupControllerPulldownData("");

            //プルダウンデータをプルダウンへセット
            ToolPulldownHelper.setPullDown(pul_StoreAs, whno);
            ToolPulldownHelper.setPullDown(pul_AGCNo, agcno);

            //<jp>保存ファイル名をセットする</jp>
            //<en>Set the name of saving file.</en>
            AisleParameter searchParam = new AisleParameter();
            searchParam.setFilePath((String)this.getSession().getAttribute("WorkFolder"));

            AisleParameter[] array = (AisleParameter[])factory.query(conn, locale, searchParam);
            if (array != null)
            {
                for (int i = 0; i < array.length; i++)
                {
                    ((ToolScheduleInterface)factory).addInitialParameter(array[i]);
                }
            }

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
        String whno = "";
        String stno = "";
        String aisleno = "";
        String agcno = "";
        String bank = "";
        String bay = "";
        String level = "";
        String aisleposition = "";
        String maxcarry = "";
        
        //行をすべて削除
        lst_Aisle.clearRow();

        //パラメータ取得
        Parameter[] paramarray = factory.getAllParameters();
        for (int i = 0; i < paramarray.length; i++)
        {
            AisleParameter param = (AisleParameter)paramarray[i];
            //リストへ追加するパラメータ
            if (param.getWarehouseNumber() == 9999)
            {
                whno = "*";
            }
            else
            {
                whno = String.valueOf(param.getWarehouseNumber());
            }
            stno = param.getAisleStationNo();
            aisleno = String.valueOf(param.getAisleNumber());
            if (param.getAGCNumber() == 9999)
            {
                agcno = "*";
            }
            else
            {
                agcno = String.valueOf(param.getAGCNumber());
            }
            bank = String.valueOf(param.getSBank()) + " - " + String.valueOf(param.getEBank());
            bay = String.valueOf(param.getSBay()) + " - " + String.valueOf(param.getEBay());
            level = String.valueOf(param.getSLevel()) + " - " + String.valueOf(param.getELevel());
            if (param.getSAislePosition() == 0)
            {
                aisleposition = "-";
            }
            else
            {
                aisleposition =
                        String.valueOf(param.getSAislePosition()) + " - " + String.valueOf(param.getEAislePosition());
            }
            maxcarry = String.valueOf(param.getMaxCarry());
            
            //行追加
            //最終行を取得
            int count = lst_Aisle.getMaxRows();
            lst_Aisle.setCurrentRow(count);
            lst_Aisle.addRow();
            lst_Aisle.setValue(3, whno);
            lst_Aisle.setValue(4, stno);
            lst_Aisle.setValue(5, aisleno);
            lst_Aisle.setValue(6, agcno);
            lst_Aisle.setValue(7, bank);
            lst_Aisle.setValue(8, bay);
            lst_Aisle.setValue(9, level);
            lst_Aisle.setValue(10, aisleposition);
            lst_Aisle.setValue(11, maxcarry);
        }

        // 修正中の行をハイライト表示にする
        int modindex = factory.changeLineNo();
        if (modindex > -1)
        {
            lst_Aisle.setHighlight(modindex + 1);
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
     * @throws Exception 例外発生時に返ります。
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
            //<jp>プルダウンが正常に表示されていない場合</jp>
            //<en>In case the pull-down is not correctly displayed,</en>
            if (pul_StoreAs.getSelectedValue() == null)
            {
                //<jp>6123117 = 倉庫管理情報がありません。倉庫設定で登録してください</jp>
                //<en>6123117 = There is no warehouse control information. </en>
                //<en>Please register in warehouse setting screen.</en>
                message.setMsgResourceKey("6123117");
                return;
            }
            if (pul_AGCNo.getSelectedValue() == null)
            {
                //<jp>6123078 = グループコントローラ情報がありません。グループコントローラ設定画面で登録してください</jp>
                //<en>6123078 = There is no group controller information. </en>
                //<en>Please register in group controller setting screen.</en>
                message.setMsgResourceKey("6123078");
                return;
            }

            //入力チェック
            txt_StNumber.validate(this, true);
            txt_AisleNumber.validate(this, true);
            txt_FBank.validate(this, true);
            txt_TBank.validate(this, true);
            txt_FBay.validate(this, true);
            txt_TBay.validate(this, true);
            txt_FLevel.validate(this, true);
            txt_TLevel.validate(this, true);
            txt_MaxCarry.validate(this, true);

            // アイルNo.チェック
            if (Integer.parseInt(txt_AisleNumber.getText()) < 1)
            {
                // 6123120 = アイルNoには１以上の値を指定してください。
                message.setMsgResourceKey("6123120");
                return;
            }

            int sbank = Integer.parseInt(txt_FBank.getText());
            int ebank = Integer.parseInt(txt_TBank.getText());
            //ダブルディープの場合はアイル位置が入力されているかチェックする
            if (ebank - sbank > 1)
            {
                txt_FAislePosition.validate(this, true);
                txt_TAislePosition.validate(this, true);
            }
            
            // 最大搬送可能数チェック
            if (Integer.parseInt(txt_MaxCarry.getText()) < 1)
            {
                // 6123124 = 最大搬送可能数には１以上の値を指定してください。
                message.setMsgResourceKey("6123124");
                return;                
            }
            
            // アイルステーションNo.のチェック
            if (!txt_StNumber.getText().substring(0, 1).equals("9"))
            {
                // 6123125=アイルステーションNo.の先頭には9しか使用できません。
                message.setMsgResourceKey("6123125");
                return;     
            }

            //パラメータへセット
            AisleParameter param = new AisleParameter();
            param.setWarehouseNumber(Integer.parseInt(pul_StoreAs.getSelectedValue()));
            param.setAisleStationNo(txt_StNumber.getText());
            param.setAisleNumber(txt_AisleNumber.getInt());
            param.setAGCNumber(Integer.parseInt(pul_AGCNo.getSelectedValue()));
            param.setSBank(sbank);
            param.setEBank(ebank);
            param.setSBay(Integer.parseInt(txt_FBay.getText()));
            param.setEBay(Integer.parseInt(txt_TBay.getText()));
            param.setSLevel(Integer.parseInt(txt_FLevel.getText()));
            param.setELevel(Integer.parseInt(txt_TLevel.getText()));
            param.setMaxCarry(Integer.parseInt(txt_MaxCarry.getText()));
            if (ebank - sbank > 1)
            {
                param.setSAislePosition(Integer.parseInt(txt_FAislePosition.getText()));
                param.setEAislePosition(Integer.parseInt(txt_TAislePosition.getText()));
            }

            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);

            //<jp>スケジューラオブジェクトを取得する。</jp>
            //<en>Retrieve the scheduler object.</en>
            ScheduleInterface factory = (ScheduleInterface)this.getSession().getAttribute("FACTORY");

            //<jp>ためうち処理を行う。</jp>
            //<jp>ためうち成功時は入力エリアをクリアにする。</jp>
            //<en>Process the pooled entered data.</en>
            //<en>If all data has been entered in pooling area successfully, clear the input area.</en>
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
        pul_StoreAs.setSelectedIndex(0);
        txt_StNumber.setText("");
        txt_AisleNumber.setText("");
        pul_AGCNo.setSelectedIndex(0);
        txt_FBank.setText("");
        txt_TBank.setText("");
        txt_FBay.setText("");
        txt_TBay.setText("");
        txt_FLevel.setText("");
        txt_TLevel.setText("");
        txt_FAislePosition.setText("");
        txt_TAislePosition.setText("");
        txt_MaxCarry.setText("");
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
            ScheduleInterface factory = (ScheduleInterface)this.getSession().getAttribute("FACTORY");
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

    /**
     * ため打ちクリアボタンが押下されたときに呼ばれます
     * @param e ActionEvent
     * @throws Exception 例外発生時に返ります。
     */
    public void btn_Cancel_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            ScheduleInterface factory = (ScheduleInterface)this.getSession().getAttribute("FACTORY");
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
    public void lst_Aisle_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            ScheduleInterface factory = (ScheduleInterface)this.getSession().getAttribute("FACTORY");
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);
            //修正、削除された行
            int index = lst_Aisle.getActiveRow();
            //**** 修正 ****
            if (lst_Aisle.getActiveCol() == 1)
            {
                //現在の行をセット
                lst_Aisle.setCurrentRow(index);
                //黄色にハイライト表示させる
                lst_Aisle.setHighlight(index);
                //修正中のパラメータをfactoryに設定する
                factory.changeParameter(index - 1);

                //<jp>ファクトリから修正中のパラメータだけを取得します。</jp>
                //<en>Retrieve from factory only the parameters being modified.</en>
                AisleParameter param = (AisleParameter)factory.getUpdatingParameter();

                //格納区分
                pul_StoreAs.setSelectedItem(String.valueOf(param.getWarehouseNumber()));
                //ステーションNo
                txt_StNumber.setText(param.getAisleStationNo());
                //アイルNo
                txt_AisleNumber.setInt(param.getAisleNumber());
                //AGCNo
                pul_AGCNo.setSelectedItem(String.valueOf(param.getAGCNumber()));
                //バンク(開始)
                txt_FBank.setText(String.valueOf(param.getSBank()));
                //バンク(終了)
                txt_TBank.setText(String.valueOf(param.getEBank()));
                //ベイ(開始)
                txt_FBay.setText(String.valueOf(param.getSBay()));
                //ベイ(終了)
                txt_TBay.setText(String.valueOf(param.getEBay()));
                //レベル(開始)
                txt_FLevel.setText(String.valueOf(param.getSLevel()));
                //レベル(終了)
                txt_TLevel.setText(String.valueOf(param.getELevel()));
                //アイル位置(開始)
                int sAislePos = param.getSAislePosition();
                if (sAislePos == 0)
                {
                    txt_FAislePosition.setText("");
                }
                else
                {
                    txt_FAislePosition.setText(String.valueOf(sAislePos));
                }
                //アイル位置(終了)
                int eAislePos = param.getEAislePosition();
                if (eAislePos == 0)
                {
                    txt_TAislePosition.setText("");
                }
                else
                {
                    txt_TAislePosition.setText(String.valueOf(eAislePos));
                }
                // 最大搬送可能数
                txt_MaxCarry.setText(String.valueOf(param.getMaxCarry()));
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
