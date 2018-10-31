// $Id: BackUpBusiness.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.setting.system;


import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.util.BackupUtils;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;

/** <jp>
 * バックアップ作成の画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 </jp> */
/** <en>
 * This screen class is created by the screen generator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/1</TD><TD>T.Torigaki(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 </en> */
public class BackUpBusiness
        extends BackUp
        implements EmConstants
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /** 
     * 各コントロールイベント呼び出し前に呼び出されます。
     * @param e ActionEvent
     * @exception Exception
     */
    public void page_Initialize(ActionEvent e)
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

        setFocus(txt_FileBrows);
    }

    /**
     * 画面の初期化を行います。
     * @param e ActionEvent
     * @exception Exception
     */
    public void page_Load(ActionEvent e)
            throws Exception
    {
// 2008/12/25 K.Matsuda Start eWareNavi以外のときは赤*を非表示
        if(!EmConstants.PRODUCT_NAME_WMS.equals(EmProperties.getProperty(EmConstants.EMPARAMKEY_PRODUCT_NAME)))
        {
            setVisibleRequireMarks(false);
        }
// 2008/12/25 K.Matsuda End
        
        //	    //プロダクトタイプをViewStateに保存
        //	    this.getViewState().setString("PRODUCTTYPE",this.request.getParameter(PRODUCTTYPE));
        //	    //メニュータイプをViewStateに保存
        //	    this.getViewState().setString("MENUTYPE",this.request.getParameter(MENUTYPE));

        txt_FileBrows.setText(EmProperties.getProperty("Restore.Path"));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    
// 2008/12/25 K.Matsuda Start 赤*を非表示
    /**
     * 必須項目のマーク(*)を非表示にします
     */
    private void setVisibleRequireMarks(boolean visible)
    {
        // *を非表示にする
        lbl_Require.setVisible(visible);
    }
// 2008/12/25 K.Matsuda End
    
    // Event handler methods -----------------------------------------

    /** 
     * メニューへボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
        forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
    }

    /**
     * 開始ボタンが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Start_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            //保存先フォルダのパスを入力チェック
            txt_FileBrows.validate(this, true);

            String filePath = txt_FileBrows.getText();

            if (!filePath.endsWith("\\") && !filePath.endsWith("/"))
            {
                filePath = filePath + File.separator;
            }

            //Get table names for backup.
            List tabeles = EmProperties.getList(EmProperties.getProperty("Backup.Tables"));
            //コネクションを取得
            conn = EmConnectionHandler.getPageDbConnection(this);

            Iterator itr = tabeles.iterator();
            while (itr.hasNext())
            {
                String tableName = (String)itr.next();
                BackupUtils.mkFile(filePath, tableName, conn);
            }
            //メッセージをセット
            message.setMsgResourceKey("6401006");

            // write operation log
            //EManagerLogger.writeUserMainteLog(getUserInfo().getUserId(), "BackUp");

            //ユーザ情報の作成
            DfkUserInfo userInfo = (DfkUserInfo)getUserInfo();
            //項目リストの生成
            List list = new ArrayList();
            list.add(txt_FileBrows.getText());
            //オペレーションログ出力
            P11LogWriter logWriter = new P11LogWriter(conn);
            logWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_SETTING, list);
            //Commit
            conn.commit();
        }
        catch (FileNotFoundException fnfe)
        {
            //指定されたパスが見つかりません。
            message.setMsgResourceKey("6403038");
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }


    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void lbl_SettingName_Server(ActionEvent e)
            throws Exception
    {
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Help_Server(ActionEvent e)
            throws Exception
    {
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void tab_Click(ActionEvent e)
            throws Exception
    {
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_ToMenu_Server(ActionEvent e)
            throws Exception
    {
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void lbl_FolderBrows_Server(ActionEvent e)
            throws Exception
    {
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void txt_FileBrows_Server(ActionEvent e)
            throws Exception
    {
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void txt_FileBrows_AutoCompleteItemClick(ActionEvent e)
            throws Exception
    {
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void txt_FileBrows_AutoComplete(ActionEvent e)
            throws Exception
    {
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void txt_FileBrows_EnterKey(ActionEvent e)
            throws Exception
    {
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void txt_FileBrows_TabKey(ActionEvent e)
            throws Exception
    {
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void txt_FileBrows_InputComplete(ActionEvent e)
            throws Exception
    {
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Start_Server(ActionEvent e)
            throws Exception
    {
    }


}
//end of class
