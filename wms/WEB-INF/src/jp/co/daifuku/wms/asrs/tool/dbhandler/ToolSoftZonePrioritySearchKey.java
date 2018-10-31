// $Id: ToolSoftZonePrioritySearchKey.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.dbhandler;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * ハンドラクラスを使用してTEMP_SOFTZONEPRIORITYテーブルを検索し、TEMP_SOFTZONEPRIORITYクラスのインスタンスを生成するために使用するキークラスです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This is a key class which is used to search TEMP_SOFTZONEPRIORITY table by using handler class
 * and generate the instance of TEMP_SOFTZONEPRIORITY class.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class ToolSoftZonePrioritySearchKey
        extends ToolSQLSearchKey
{
    // Class fields --------------------------------------------------
    //<jp> ここに検索条件又はソートされる可能性のあるカラムを定義します。</jp>
    //<en> Define here the column which may be used as a search condition or the which may be sorted. </en>
    private static final String WHSTATIONNO = "WH_STATION_NO";

    private static final String SOFTZONEID = "SOFT_ZONE_ID";

    private static final String PRIORITYSOFTZONEID = "PRIORITY_SOFT_ZONE";

    private static final String PRIORITY = "PRIORITY";

    // Class variables -----------------------------------------------
    private static final String[] Columns = {
            WHSTATIONNO,
            SOFTZONEID,
            PRIORITYSOFTZONEID,
            PRIORITY,
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
        return ("$Revision: 4122 $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * カラム定義を設定します。
     </jp>*/
    /**<en>
     * Define the column definition.
     </en>*/
    public ToolSoftZonePrioritySearchKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * WHSTATIONNOの検索値をセットします。
     * @param stnum 検索する倉庫を識別するステーションNo
     </jp>*/
    /**<en>
     * Set the search value of WHSTATIONNO.
     * @param stnum :station no. to identify the warehouse to search
     </en>*/
    public void setWHStationNo(String stnum)
    {
        setValue(WHSTATIONNO, stnum);
    }

    /**<jp>
     * WHSTATIONNOを検索値を取得します。
     * @return 倉庫を識別するステーションNo
     </jp>*/
    /**<en>
     * Retrieve the search value of WHSTATIONNO.
     * @return :station no. to identify the warehouse
     </en>*/
    public String getWHStationNo()
    {
        return (String)getValue(WHSTATIONNO);
    }

    /**<jp>
     * WHSTATIONNOのソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of WHSTATIONNO.
     * @param num   :prioriry in sort order
     * @param bool  true: in ascending order, false: in descending order
     </en>*/
    public void setWHStationNoOrder(int num, boolean bool)
    {
        setOrder(WHSTATIONNO, num, bool);
    }

    /**<jp>
     * WHSTATIONNOのソート順を取得
     * @return 倉庫を識別するステーションNo順
     </jp>*/
    /**<en>
     * Set the search value of WHSTATIONNO.
     * @return :the order of station no. to identify the station no.
     </en>*/
    public int getWHStationNoOrder()
    {
        return (getOrder(WHSTATIONNO));
    }

    /**<jp>
     * ソフトゾーンIDの検索値をセットします。
     * @param szid ソフトゾーンIDの検索値
     </jp>*/
    /**<en>
     * Set the search value of soft zone ID.
     * @param szid :the search value of soft zone ID
     </en>*/
    public void setSoftZoneID(int szid)
    {
        setValue(SOFTZONEID, szid);
    }

    /**<jp>
     * ソフトゾーンIDを検索値を取得します。
     * @return ソフトゾーンID
     </jp>*/
    /**<en>
     * Retrieve the search value of soft zone ID.
     * @return :soft zone
     </en>*/
    public int getSoftZoneID()
    {
        Integer intobj = (Integer)getValue(SOFTZONEID);
        return (intobj.intValue());
    }

    /**<jp>
     * ソフトゾーンIDのソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of soft zone ID.
     * @param num  :prioriry in sort order
     * @param bool true: in ascending order, false: in descending order
     </en>*/
    public void setSoftZoneIDOrder(int num, boolean bool)
    {
        setOrder(SOFTZONEID, num, bool);
    }

    /**<jp>
     * ソフトゾーンIDのソート順を取得
     * @return ソフトゾーンID順
     </jp>*/
    /**<en>
     * Retrieve the sort order of soft zone ID.
     * @return :the order of soft zone ID
     </en>*/
    public int getSoftZoneIDOrder()
    {
        return (getOrder(SOFTZONEID));
    }

    /**<jp>
     * 優先ソフトゾーンの検索値をセットします。
     * @param pszid 優先ソフトゾーンの検索値
     </jp>*/
    /**<en>
     * Set the search value of priority soft zone ID.
     * @param pszid :the search value of priority soft zone ID
     </en>*/
    public void setPrioritySoftZone(int pszid)
    {
        setValue(PRIORITYSOFTZONEID, pszid);
    }

    /**<jp>
     * 優先ソフトゾーンを検索値を取得します。
     * @return 優先ソフトゾーン
     </jp>*/
    /**<en>
     * Retrieve the search value of priority soft zone ID.
     * @return :priority soft zone
     </en>*/
    public int getPrioritySoftZone()
    {
        Integer intobj = (Integer)getValue(PRIORITYSOFTZONEID);
        return (intobj.intValue());
    }

    /**<jp>
     * 優先ソフトゾーンのソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of priority soft zone ID.
     * @param num  :prioriry in sort order
     * @param bool true: in ascending order, false: in descending order
     </en>*/
    public void setPrioritySoftZoneOrder(int num, boolean bool)
    {
        setOrder(PRIORITYSOFTZONEID, num, bool);
    }

    /**<jp>
     * 優先ソフトゾーンのソート順を取得
     * @return 優先ソフトゾーン順
     </jp>*/
    /**<en>
     * Retrieve the sort order of priority soft zone ID.
     * @return :the order of priority soft zone ID
     </en>*/
    public int getPrioritySoftZoneOrder()
    {
        return (getOrder(PRIORITYSOFTZONEID));
    }

    /**<jp>
     * 優先順位の検索値をセットします。
     * @param pri 優先順位の検索値
     </jp>*/
    /**<en>
     * Set the search value of priority.
     * @param pri :the search value of priority
     </en>*/
    public void setPriority(int pri)
    {
        setValue(PRIORITY, pri);
    }

    /**<jp>
     * 優先順位を検索値を取得します。
     * @return 優先順位
     </jp>*/
    /**<en>
     * Retrieve the search value of priority.
     * @return :priority
     </en>*/
    public int getPriority()
    {
        Integer intobj = (Integer)getValue(PRIORITY);
        return (intobj.intValue());
    }

    /**<jp>
     * 優先順位のソート順セット
     * @param num ソートの優先順
     * @param bool 昇順の時はtrue,降順の時はfalse
     </jp>*/
    /**<en>
     * Set the sort order of priority.
     * @param num :prioriry in sort order
     * @param bool :true: in ascending order, false: in descending order
     </en>*/
    public void setPriorityOrder(int num, boolean bool)
    {
        setOrder(PRIORITY, num, bool);
    }

    /**<jp>
     * 優先順位のソート順を取得
     * @return 優先順位順
     </jp>*/
    /**<en>
     * Retrieve the sort order of priority.
     * @return :the order of priority
     </en>*/
    public int getPriorityOrder()
    {
        return (getOrder(PRIORITY));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

