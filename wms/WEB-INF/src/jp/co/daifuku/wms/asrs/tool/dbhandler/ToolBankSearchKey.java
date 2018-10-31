// $Id: ToolBankSearchKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * Bank表を検索するためのキーをセットするクラスです。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>P. Jain</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class sets the keys to search Bank table.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>P. Jain</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ToolBankSearchKey extends ToolSQLSearchKey
{
    // Class fields --------------------------------------------------
    //<jp> ここに検索条件又はソートされる可能性のあるカラムを定義します。</jp>
    //<en> Define here the column which may be used as a search condition or the which may be sorted.</en> 
    private static final String WHSTATIONNO = "WH_STATION_NO";
    private static final String AISLESTATIONNO = "AISLE_STATION_NO";
    private static final String BANKNO = "BANK_NO";

    // Class variables -----------------------------------------------
    private static final String[] Columns =
    {
        WHSTATIONNO,
        AISLESTATIONNO,
        BANKNO
    };

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
        return ("$Revision: 87 $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルトコンストラクタ
     </jp>*/
    /**<en>
     * Default constructor
     </en>*/
    public ToolBankSearchKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * WHSTATIONNOの検索値をセット
     * @param st WHSTATIONNO
     </jp>*/
    /**<en>
     * Set the search value of WHSTATIONNO.
     * @param st WHSTATIONNO
     </en>*/
    public void setWhStationNo(String st)
    {
        setValue(WHSTATIONNO, st);
    }

    /**<jp>
     * WHSTATIONNOを取得e
     * @return WHSTATIONNO
     </jp>*/
    /**<en>
     * Retrieve the WHSTATIONNO.
     * @return WHSTATIONNO
     </en>*/
    public String getWhStationNo()
    {
        return (String)getValue(WHSTATIONNO);
    }

    /**<jp>
     * WHSTATIONNOのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of WHSTATIONNO.
     </en>*/
    public void setWhStationNoOrder(int num, boolean bool)
    {
        setOrder(WHSTATIONNO, num, bool);
    }

    /**<jp>
     * WHSTATIONNOのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of WHSTATIONNO.
     </en>*/
    public int getWhStationNoOrder()
    {
        return (getOrder(WHSTATIONNO));
    }

    /**<jp>
     * AISLESTATIONNOの検索値をセット
     * @param st AISLESTATIONNO
     </jp>*/
    /**<en>
     * Set the search value of AISLESTATIONNO.
     * @param st AISLESTATIONNO
     </en>*/
    public void setAisleStationNo(String st)
    {
        setValue(AISLESTATIONNO, st);
    }

    /**<jp>
     * AISLESTATIONNOを取得e
     * @return AISLESTATIONNO
     </jp>*/
    /**<en>
     * Retrieve the AISLESTATIONNO.
     * @return AISLESTATIONNO
     </en>*/
    public String getAisleStationNo()
    {
        return (String)getValue(AISLESTATIONNO);
    }

    /**<jp>
     * AISLESTATIONNOのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of AISLESTATIONNO.
     </en>*/
    public void setAisleStationNoOrder(int num, boolean bool)
    {
        setOrder(AISLESTATIONNO, num, bool);
    }

    /**<jp>
     * AISLESTATIONNOのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of AISLESTATIONNO.
     </en>*/
    public int getAisleStationNoOrder()
    {
        return (getOrder(AISLESTATIONNO));
    }

    /**<jp>
     * NBANKのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of NBANK.
     </en>*/
    public void setBankNoOrder(int num, boolean bool)
    {
        setOrder(BANKNO, num, bool);
    }

    /**<jp>
     * NBANKのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of NBANK.
     </en>*/
    public int getBankNoOrder()
    {
        return (getOrder(BANKNO));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

