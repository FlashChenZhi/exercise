// $Id: ProductionListBusiness.java 834 2008-10-28 06:41:39Z okamura $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.toolmenu.listbox.productionlist;
import java.io.File;
import java.io.FilenameFilter;

import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.tool.WMSToolConstants;
import jp.co.daifuku.wms.asrs.tool.common.ExceptionHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;

/**
 * 製番フォルダ画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/01</TD><TD>kaneko</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 834 $, $Date: 2008-10-28 15:41:39 +0900 (火, 28 10 2008) $
 * @author  $Author: okamura $
 */
public class ProductionListBusiness extends ProductionList implements WMSToolConstants
{
    // Class fields --------------------------------------------------
    /** 
     * 製番No.の受け渡しに使用するキーです
     */
    public static final String PRODUCTIONNO_KEY = "PRODUCTIONNO_KEY";

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

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
    public void page_Load(ActionEvent e) throws Exception
    {
        // 画面名をセットする
        lbl_ListName.setText(DisplayText.getText("TLE-W9003"));

        //<jp> プルダウン表示用データ</jp>
        //<en> Data for pull-down list</en>
        String[] listdata = null;
        try
        {
            //<jp> 製番ファイルフォルダのパスをリソースから取得します。</jp>
            //<en> Get the path of project no. file from the resource.</en>
            String currentFolder = ToolParam.getParam("EAWC_ITEM_ROUTE_PATH");
            //<jp> ファイルリスト取得</jp>
            //<en> Get the file list.</en>
            File file = new File(currentFolder);
            String[] list = null;
            //<jp> 指定されたパスが読み込み可能なら、サブディレクトリ検索</jp>
            //<en> If the specified path is readable, search the sub directory.</en>
            if (file.canRead())
            {               
                list =  file.list(new FilenameFilter()
                {
                    public boolean accept(File dir, String name)
                    {
                        //<jp> ディレクトリのみ取得</jp>
                        //<en> Get the directory only.</en>
                        return ((new File(dir , name)).isDirectory());
                    }
                });
            }
            if (list != null)
            {
                //<jp> 指定されたフォルダのサブフォルダを設定</jp>
                //<en> Set the sub folder of the specified folder.</en>
                listdata = new String[ list.length];
                //<jp>  ファイルを配列に追加</jp>
                //<en>  Add the file to the array.</en>
                int cnt;
                for (cnt = 0 ; cnt < list.length ; cnt++)
                {
                    listdata[ cnt ] = list[ cnt ];
                }
            }
            //最初のページを表示
            setList(listdata);
        }
        catch (Exception ex)
        {
            lbl_InMsg.setText(MessageResources.getText(ExceptionHandler.getDisplayMessage(ex, this)));
        }
    }

    /**
     * @param e ActionEvent
     * @throws Exception 
     */
    public void page_LoginCheck(ActionEvent e) throws Exception
    {
        //ログイン前なのでこのメソッドは必要
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /** <jp>
     * リストセルに値をセットします。
     * @param listbox  ToolSessionZoneRet
     * @param actionName String
     * @throws Exception 
     </jp> */
    /** <en>
     * Set data to listcell.
     * @param listbox  ToolSessionZoneRet
     * @param actionName String
     * @throws Exception      
     </en> */
    protected void setList(String listdata[]) throws Exception
    {
        int len = 0;
        if (listdata != null)
        {
            len = listdata.length;
        }

        if (len > 0)
        {
            //行をすべて削除
            lst_ProductionNumberList.clearRow();
            
            String dirname = "";

            for (int i = 0; i < listdata.length; i++) 
            {
                //<jp>取得した情報をリモートデータに設定する</jp>
                //<en>Set the retrieved data in remote data.</en>
                dirname = listdata[i];
                //行追加
                //最終行を取得
                int count = lst_ProductionNumberList.getMaxRows();
                lst_ProductionNumberList.setCurrentRow(count);
                lst_ProductionNumberList.addRow();
                lst_ProductionNumberList.setValue(1, Integer.toString(count));
                lst_ProductionNumberList.setValue(2, dirname);
            }
        }
        else
        {
            //ヘッダーを隠します
            lst_ProductionNumberList.setVisible(false);
            //MSG-W9016 = 対象データはありませんでした
            lbl_InMsg.setResourceKey("MSG-W9016");
        }
    }

    // Event handler methods -----------------------------------------
    /** <jp>
     * 閉じるボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when a close button is pushed.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Close_U_Click(ActionEvent e) throws Exception
    {
        //呼び出し元の画面へ遷移します
        parentRedirect(null);
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
    public void lst_ProductionNumberList_Click(ActionEvent e) throws Exception
    {
        //現在の行をセット
        lst_ProductionNumberList.setCurrentRow(lst_ProductionNumberList.getActiveRow());

        //呼び出し元の画面へ渡すパラメータ作成
        ForwardParameters param = new ForwardParameters();
        param.setParameter(PRODUCTIONNO_KEY, lst_ProductionNumberList.getValue(2));
        
        //終了処理
        btn_Close_U_Click(null);
        
        //呼び出し元の画面へ遷移します
        parentRedirect(param);
    }

    /** <jp>
     * 閉じるボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * It is called when a close button is pushed.
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Close_D_Click(ActionEvent e) throws Exception
    {
        btn_Close_U_Click(e);
    }
}
//end of class
