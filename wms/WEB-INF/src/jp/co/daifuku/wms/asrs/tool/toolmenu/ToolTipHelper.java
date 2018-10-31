// $Id: ToolTipHelper.java 87 2008-10-04 03:07:38Z admin $

package jp.co.daifuku.wms.asrs.tool.toolmenu ;

import java.util.ArrayList;
import java.util.Iterator;

import jp.co.daifuku.Constants;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/** <jp>
 * ToolTipへセットする文字列を作成するためのクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/29</TD><TD>Kaneko</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp> */
/** <en>
 * It is the class to make a string to set on ToolTip.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/29</TD><TD>Kaneko</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en> */
public class ToolTipHelper implements Constants
{
    // Class fields --------------------------------------------------


    // Class variables -----------------------------------------------
    private ArrayList wList = new ArrayList();

    // Class method --------------------------------------------------
    /** <jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /** <en>
     * Comment for field
     * @return version of this Class and Updated date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
    }
    // Constructors --------------------------------------------------
    /** <jp>
     * コンストラクタ
     </jp>*/
    /** <en>
     * Constructor
     </en>*/
    public ToolTipHelper()
    {
    }
    // Public methods ------------------------------------------------
    /** <jp>
     * ToolTipに値を追加します。
     * @param title Title
     * @param value ToolTipの値
     </jp>*/
    /** <en>
     * Add data for ToolTip.
     * @param title Title
     * @param value Value of the ToolTip.
     </en>*/
    public void add(String title, String value)
    {
        wList.add(new ToolTipData(title, value));
    }

    /** <jp>
     * ToolTipに値を追加します。
     * @param title Title
     * @param value ToolTipの値(int型)
     </jp>*/
    /** <en>
     * Add data for ToolTip.
     * @param title Title
     * @param value Value of the ToolTip(int type).
     </en>*/
    public void add(String title, int value)
    {
        add(title, Integer.toString(value));
    }

    
    /** <jp>
     * ToolTipにセットする文字列を取得します。
     * @return 文字列
     </jp>*/
    /** <en>
     * Return a string to set on ToolTip.
     * @return string
     </en>*/
    public String getText()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(LINE_FEED);

        Iterator itr = wList.iterator();
        while (itr.hasNext())
        {
            ToolTipData data = (ToolTipData)itr.next();
            sb.append(data.wTitle).append(TOOLTIP_DELIM).append(data.wValue).append(LINE_FEED);
        }
        return sb.toString();
    }
    
    //InnerClass for data of tool tip.
    class ToolTipData
    {
        public String wTitle = "";
        public String wValue = "";

        public ToolTipData(String title, String value)
        {
            wTitle = title;
            wValue = value;
        }
    }
}
