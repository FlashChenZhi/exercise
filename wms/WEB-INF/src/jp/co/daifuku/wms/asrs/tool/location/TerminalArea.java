// $Id: TerminalArea.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.location ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
/**<jp>
 * 端末がどのステーション（作業場）を持っているかを保持するクラスです。
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/12/12</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class preserves information of teminals and the respective stations (workshops) preserved by them.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/12/12</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class TerminalArea extends ToolEntity
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * ステーションNo
     </jp>*/
    /**<en>
     * station no.
     </en>*/
    protected String wStationNo;

    /**<jp>
     * エリアID
     </jp>*/
    /**<en>
     * area ID
     </en>*/
    protected int wAreaId;

    /**<jp>
     * 端末No
     </jp>*/
    /**<en>
     * terminal ID
     </en>*/
    protected String wTerminalNo;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 新しい<CODE>TerminalArea</CODE>を構築します。
     </jp>*/
    /**<en>
     * Construct new <CODE>TerminalArea</CODE>.
     </en>*/
    public TerminalArea()
    {
    }

    /**<jp>
     * 新しい<CODE>TerminalArea</CODE>を構築します。
     * @param stationnumber   ステーションNo.
     * @param areaid          エリアID
     * @param terminal        端末No
     </jp>*/
    /**<en>
     * Construct new <CODE>TerminalArea</CODE>.
     * @param stationnumber   :station no.
     * @param areaid          :area ID
     * @param terminal        :terminal ID
     </en>*/
    public TerminalArea(String  stationnumber, 
                        int        areaid,
                        String    terminal
                  )
    {
        //<jp> インスタンス変数にセット</jp>
        //<en> Set as an instance variable.</en>
        setStationNo(stationnumber) ;
        setAreaId(areaid);
        setTerminalNo(terminal) ;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * ステーションNoに値をセットします。
     * @param stno セットするステーションNo
     </jp>*/
    /**<en>
     * Set a value of station no.
     * @param stno :station no. to set
     </en>*/
    public void setStationNo(String stno)
    {
        wStationNo = stno ;
    }

    /**<jp>
     * ステーションNoを取得します。
     * @return ステーションNo
     </jp>*/
    /**<en>
     * Retrieve the station no.
     * @return station no.
     </en>*/
    public String getStationNo()
    {
        return wStationNo ;
    }

    /**<jp>
     * エリアIDに値をセットします。
     * @param areaid エリアID
     </jp>*/
    /**<en>
     * Set a value of area ID.
     * @param areaid :area ID to set
     </en>*/
    public void setAreaId(int areaid)
    {
        wAreaId = areaid ;
    }

    /**<jp>
     * エリアIDを取得します。
     * @return エリアID
     </jp>*/
    /**<en>
     * Retrieve the area ID.
     * @return area ID
     </en>*/
    public int getAreaId()
    {
        return wAreaId ;
    }

    /**<jp>
     * 端末Noに値をセットします。
     * @param terminal セットする端末No
     </jp>*/
    /**<en>
     * Set the value of terminal ID.
     * @param terminal :terminal ID to set
     </en>*/
    public void setTerminalNo(String terminal) 
    {
        wTerminalNo = terminal ;
    }

    /**<jp>
     * 端末Noを取得します。
     * @return 端末No
     </jp>*/
    /**<en>
     * Retrieve the terminal ID.
     * @return terminal ID
     </en>*/
    public String getTerminalNo()
    {
        return wTerminalNo ;
    }
    
    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class
