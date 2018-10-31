// $Id: HardZoneListBusiness.java 6964 2010-02-05 01:34:40Z okayama $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.hardzonelist;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownData;
import jp.co.daifuku.wms.asrs.tool.toolmenu.ToolPulldownHelper;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.ui.web.BusinessClassHelper;

/**
 * ハードゾーン問合せ画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 6964 $, $Date: 2010-02-05 10:34:40 +0900 (金, 05 2 2010) $
 * @author  $Author: okayama $
 */
public class HardZoneListBusiness
        extends HardZoneList
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
        else if (pul_Bank.getEnabled())
        {
            setFocus(pul_Bank);
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
            //<jp>製番が入力されているかチェックします。</jp>
            if (!checkProduct())
            {
                btn_Query.setEnabled(false);

                message.setMsgResourceKey("6023089");
                return;
            }

            Locale locale = this.getHttpRequest().getLocale();
            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);

            ToolPulldownData pull = new ToolPulldownData(conn, locale, null);

            //<jp> プルダウンデータのセット。</jp>
            //<en> Set the pull-down data.</en>
            String[] whno =
                    pull.getWarehousePulldownData(ToolPulldownData.WAREHOUSE_AUTO, "", ToolPulldownData.ZONE_ALL);

            if (whno.length > 0)
            {
                String[] bank = pull.getwhBankPulldownData("", 0);

                //プルダウンデータをプルダウンへセット
                ToolPulldownHelper.setPullDown(pul_StoreAs, whno);
                ToolPulldownHelper.setLinkedPullDown(pul_Bank, bank);
                //子プルダウンを登録
                pul_StoreAs.addChild(pul_Bank);
            }

            // 2010/02/05 H.Okayama 使用不可棚判断処理で使用するため追加
            pul_StoreAs.setSelectedIndex(0);
            this.getSession().setAttribute("wSelectedWareHouseStationNumber", pul_StoreAs.getSelectedValue());
            // 2010/02/05 H.Okayama 使用不可棚判断処理で使用するため追加
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
     * 問合せボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。 
     */
    public void btn_Query_Click(ActionEvent e)
            throws Exception
    {
        if (pul_StoreAs.getSelectedValue() == null)
        {
            //<jp>6123100 = 倉庫情報がありません。倉庫設定画面で登録してください</jp>
            //<en>6123100 = The warehouse information does not exist. Please register in warehouse setting screen.</en>
            message.setMsgResourceKey("6123100");
            return;
        }
        if (pul_Bank.getSelectedValue() == null)
        {
            //<jp>6123113 = 棚管理情報がありません。アイル設定画面で登録してください</jp>
            //<en>6123113 = The location control information does not exist. Please register in aisle setting screen.</en>
            message.setMsgResourceKey("6123113");
            return;
        }

        this.getSession().setAttribute("wSelectedBank", pul_Bank.getSelectedValue());
        this.getSession().setAttribute("wSelectedWareHouseStationNumber", pul_StoreAs.getSelectedValue());
    }
}
//end of class
