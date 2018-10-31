package jp.co.daifuku.wms.asrs.tool.toolmenu;


import java.util.StringTokenizer;

import jp.co.daifuku.bluedog.ui.control.LinkedPullDown;
import jp.co.daifuku.bluedog.ui.control.LinkedPullDownItem;
import jp.co.daifuku.bluedog.ui.control.PullDown;
import jp.co.daifuku.bluedog.ui.control.PullDownItem;

// $Id: ToolPulldownHelper.java 87 2008-10-04 03:07:38Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/** <jp>
 * ToolPulldownDataクラスで作成されたデータをBlueDOGのPullDownコントロール
 * へセットするためのメソッドを実装します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/29</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp> */
/** <en>
 *The method to set the data made in the ToolPulldownData class to the PullDown control 
 *of BlueDOG is mounted.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/29</TD><TD>Miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en> */
public class ToolPulldownHelper
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    // Class method --------------------------------------------------
    /** <jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /** <en>
     * Return version of this Class.
     * @return version of this Class and Updated date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
    }

    // Constructors --------------------------------------------------


    // Public methods ------------------------------------------------
    /** <jp>
     * PulldownDataで取得したデータをPullDownコントロールへセットするための
     * メソッドです。
     * @param pull PullDown control.
     * @param data  PulldownData
     </jp>*/
    /** <en>
     * It is the method to set the data acquired with PulldownData to the PullDown control.
     * @param pull PullDown control.
     * @param data  PulldownData
     </en>*/
    public static void setPullDown(PullDown pull, String[] data)
    {
        for (int i = 0; i < data.length; i++)
        {
            pull.addItem(getPullData(data[i]));
        }
    }
    
    /** <jp>
     * PulldownDataで取得したデータをLinkedPullDownコントロールへセットするための
     * メソッドです。
     * @param pull LinkedPullDown control.
     * @param data  PulldownData
     </jp>*/
    /** <en>
     * It is the method to set the data acquired with PulldownData to the LinkedPullDown control.
     * @param pull LinkedPullDown control.
     * @param data  PulldownData
     </en>*/
    public static void setLinkedPullDown(LinkedPullDown pull, String[] data)
    {
        for (int i = 0; i < data.length; i++)
        {
            pull.addItem(getLinkPullData(data[i]));
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    private static PullDownItem getPullData(String pullData)
    {
        PullDownItem item = new PullDownItem();
        StringTokenizer stk = new StringTokenizer(pullData, ",", false) ;
        int count = 0;
        while (stk.hasMoreTokens())
        {
            String value = (String)stk.nextToken().trim();
            if (count == 0)
            {
                item.setValue(value);
            }
            if (count == 1)
            {
                item.setText(value);
            }
            if (count == 2)
            {
            }
            if (count == 3)
            {
                if (value.equals("0"))
                {
                    item.setSelected(false);
                }
                else
                {
                    item.setSelected(true);
                }
            }
            count++;
        }
        return item;
    }    
    
    /**
     * 
     * @param pullData
     * @return
     */
    private static LinkedPullDownItem getLinkPullData(String pullData)
    {
        LinkedPullDownItem item = new LinkedPullDownItem();
        StringTokenizer stk = new StringTokenizer(pullData, ",", false) ;
        int count = 0;
        while (stk.hasMoreTokens())
        {
            String value = (String)stk.nextToken().trim();
            if (count == 0)
            {
                item.setValue(value);
            }
            if (count == 1)
            {
                item.setText(value);
            }
            if (count == 2)
            { 
                item.setForignKey(value);
            }
            if (count == 3)
            {
                if (value.equals("0"))
                {
                    item.setSelected(false);
                }
                else
                {
                    item.setSelected(true);
                }
            }
            count++;
        }
        
        return item;
    }
}
//end of class

