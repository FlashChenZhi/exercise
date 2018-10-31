// $Id: ProductNumberBusiness.java 87 2008-10-04 03:07:38Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.productnumber;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.AsrsToolListBoxDefine;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.toolmenu.dataoperate.DataOperator;
import jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.productionlist.ProductionListBusiness;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.base.common.ListBoxDefine;

/**
 * ツールが生成した画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class ProductNumberBusiness extends ProductNumber implements WMSToolConstants
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /**
     * 画面の初期化を行います。
     * @param e ActionEvent
     * @throws Exception 例外発生時に通知されます。
     */
    public void page_Load(ActionEvent e) throws Exception
    {
        String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
        
        if (menuparam != null)
        {
            //<jp>パラメータを取得</jp><en> A parameter is acquired. </en>
            String title      = CollectionUtils.getMenuParam(0, menuparam);
            String functionID = CollectionUtils.getMenuParam(1, menuparam);
            String menuID     = CollectionUtils.getMenuParam(2, menuparam);
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
            //クリア処理
            btn_Clear_Click(null);

            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);
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
     * 各コントロールイベント呼び出し前に呼び出されます。<BR>
     * @param e ActionEvent
     * @throws Exception 例外発生時に通知されます。
     */
    public void page_Initialize(ActionEvent e) throws Exception
    {
        setFocus(txt_ProductionNumber);
    }

    /** <jp>
     * ポップアップウインドから、戻ってくるときにこのメソッドが
     * 呼ばれます。Pageに定義されているpage_DlgBackをオーバライドします。
     * @param e ActionEvent
     * @throws Exception
     </jp> */
    /** <en>
     * When it is returned, this method is called from Popup window.
     * @param e ActionEvent
     * @throws Exception
     </en> */
    public void page_DlgBack(ActionEvent e) throws Exception
    {
        DialogParameters param = ((DialogEvent) e).getDialogParameters();
        String procno = param.getParameter(ProductionListBusiness.PRODUCTIONNO_KEY);
        //空ではないときに値をセットする
        if (!StringUtil.isBlank(procno))
        {
            txt_ProductionNumber.setText(procno);
        }
    }   

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Event handler methods -----------------------------------------

    /** 
     * メニューへボタンがクリックされたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     */
    public void btn_ToMenu_Click(ActionEvent e) throws Exception
    {
        forward(BusinessClassHelper.getSubMenuPath(getViewState().getString(M_MENUID_KEY)));
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
    public void btn_Clear_Click(ActionEvent e) throws Exception
    {
        txt_ProductionNumber.setText("");
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 例外発生時に通知されます。
     */
    public void txt_ProductionNumber_EnterKey(ActionEvent e) throws Exception
    {
        btn_Commit_Click(null);
    }

    /** 
     * 製番一覧へボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 例外が発生した場合に通知されます。
     */
    public void btn_ProductionNumber_Click(ActionEvent e) throws Exception
    {
        //製番一覧画面へ検索条件をセットする
        ForwardParameters param = new ForwardParameters();
        
        //処理中画面->結果画面
        redirect(AsrsToolListBoxDefine.LST_TOOL_PRODUCT, param, ListBoxDefine.LST_PROGRESS);
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 例外発生時に通知されます。
     */
    public void btn_Commit_Click(ActionEvent e) throws Exception
    {
        Connection conn = null;
        try
        {
            txt_ProductionNumber.validate(this, true);

            // 製番禁止文字チェックを行います
            ArrayList chklist = new ArrayList();
            chklist.add("\\");
            chklist.add("/");
            chklist.add(":");
            chklist.add("*");
            chklist.add("?");
            chklist.add("\"");
            chklist.add("<");
            chklist.add(">");
            chklist.add("|");
            
            String prdNumber = txt_ProductionNumber.getText();  
            for (int i = 0; i < prdNumber.length(); i++)
            {
                if (chklist.contains(prdNumber.substring(i, i + 1)))
                {
                    // 6003101={0}として使用できない文字が含まれています
                    message.setMsgResourceKey("6003101" + MSG_DELIM + DisplayText.getText("LBL-W9069"));                           
                    return;
                }
            }
                
            //コネクション取得
            conn = ConnectionManager.getRequestConnection(DATASOURCE_NAME, this);

            //
            session.setMaxInactiveInterval(-1);

            DataOperator dataope = new DataOperator(conn);
            //<jp> ログイン処理を行う。</jp>
            //<en> Login will be processed.</en>
            if (!dataope.login(getHttpRequest(), prdNumber))
            {
                conn.rollback();

                //<jp> メッセージをセット。</jp>
                //<en> Set the message.</en>
                message.setMsgResourceKey(dataope.getMessage());
            }
            else
            {
                conn.commit();

                //<jp> メッセージをセット。</jp>
                //<en> Set the message.</en>
                message.setMsgResourceKey("6121004");
            }

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

}
//end of class
