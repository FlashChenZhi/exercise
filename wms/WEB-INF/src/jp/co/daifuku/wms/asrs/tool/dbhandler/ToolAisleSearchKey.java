// $Id: ToolAisleSearchKey.java 5866 2009-11-14 09:04:48Z ota $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * ハンドラクラスを使用してAISLEテーブルを検索し、Aisleクラスのインスタンスを生成するために使用するキークラスです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5866 $, $Date: 2009-11-14 18:04:48 +0900 (土, 14 11 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This key class is used when searching the AISLE table using the handler and when generating
 * the instance of Aisle class.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5866 $, $Date: 2009-11-14 18:04:48 +0900 (土, 14 11 2009) $
 * @author  $Author: ota $
 </en>*/
public class ToolAisleSearchKey extends ToolSQLSearchKey
{
    // Class fields --------------------------------------------------
    //<jp>ここに検索条件又はソートされる可能性のあるカラムを定義します。</jp>
    //<en> Define here the column which may be used as a search condition or the which may be sorted.</en>
    private static final String STATIONNO    = "STATION_NO";
    private static final String WHSTATIONNO  = "WH_STATION_NO";
    private static final String AISLENO      = "AISLE_NO";
    private static final String CONTROLLERNO = "CONTROLLER_NO";
    private static final String MAXCARRY = "MAX_CARRY";

    // Class variables -----------------------------------------------
    private static final String[] Columns =
    {
        STATIONNO,
        WHSTATIONNO,
        AISLENO,
        CONTROLLERNO,
        MAXCARRY
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
        return ("$Revision: 5866 $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * カラム定義を設定します。
     </jp>*/
    /**<en>
     * Set the column definition.
     </en>*/
    public ToolAisleSearchKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * STATIONNOの検索値をセット
     * @param stnum STATIONNOの検索値
     </jp>*/
    /**<en>
     * Set the search value of STATIONNO.
     * @param stnum :the search value of STATIONNO
     </en>*/
    public void setStationNo(String stnum)
    {
        setValue(STATIONNO, stnum);
    }

    /**<jp>
     * STATIONNOを取得
     * @return STATIONNO
     </jp>*/
    /**<en>
     * Retrieve the STATIONNO.
     * @return STATIONNO
     </en>*/
    public String getStationNo()
    {
        return (String)getValue(STATIONNO);
    }

    /**<jp>
     * STATIONNOのソート順セット
     * @param num ソートの優先順位
     * @param bool trueを指定すると昇順
     </jp>*/ 
    /**<en>
     * Set the sort order of STATIONNO.
     * @param num  :priority in sort order
     * @param bool :ascending order if specified true
     </en>*/ 
    public void setStationNoOrder(int num, boolean bool)
    {
        setOrder(STATIONNO, num, bool);
    }

    /**<jp>
     * STATIONNOのソート順を取得
     * @return STATIONNOのソート順
     </jp>*/
    /**<en>
     * Retrieve the sort order of STATIONNO
     * @return :the sort order of STATIONNO
     </en>*/
    public int getStationNoOrder()
    {
        return (getOrder(STATIONNO));
    }

    /**<jp>
     *WHSTATIONNOの検索値をセット
     * @param WHSTNo WHSTATIONNOの検索値
     </jp>*/ 
    /**<en>
     * Set the search value of WHSTATIONNO.
     * @param wWHSTNo :the search value of WHSTATIONNO
     </en>*/ 
    public void setWhStationNo(String wWHSTNo)
    {
        setValue(WHSTATIONNO, wWHSTNo);
    }

    /**<jp>
     * WHSTATIONNOの検索値を取得
     * @return WHSTATIONNO
     </jp>*/
    /**<en>
     * Retrieve the search value of WHSTATIONNO.
     * @return WHSTATIONNO
     </en>*/
    public String getWhStationNo()
    {
        return (String)getValue(WHSTATIONNO);
    }
    /**<jp>
     * WHSTATIONNOの検索順をセット
     * @param num ソートの優先順位
     * @param bool trueを指定すると昇順
     </jp>*/ 
    /**<en>
     * Set the search value of WHSTATIONNO.
     * @param num :priority in sort order
     * @param bool :ascending order if specified true
     </en>*/ 
    public void setWhStationNoOrder(int num, boolean bool)
    {
        setOrder(WHSTATIONNO, num, bool);
    }

    /**<jp>
     * AISLENOの検索値をセット
     * @param wAlNo AISLENOの検索値
     </jp>*/ 
    /**<en>
     * Set the search value of AISLENO.
     * @param wAlNo :the search value of AISLENO
     </en>*/ 
    public void setAisleNo(String wAlNo)
    {
        setValue(AISLENO, wAlNo);
    }

    /**<jp>
     * AISLENOの検索値を取得
     * @return AISLENO
     </jp>*/
    /**<en>
     * Retrieve the search value of AISLENO.
     * @return AISLENO
     </en>*/
    public String getAisleNo()
    {
        return (String)getValue(AISLENO);
    }

    /**<jp>
     * WHSTATIONNOの検索順を取得
     * @return WHSTATIONNOの検索順
     </jp>*/
    /**<en>
     * Retrieve the search value of WHSTATIONNO.
     * @return :the search value of WHSTATIONNO
     </en>*/
    public int getWhStationNoOrder()
    {
        return (getOrder(WHSTATIONNO));


    }
    /**<jp>
     *AISLENOのソート順セット
     * @param num ソートの優先順位
     * @param bool trueを指定すると昇順
     </jp>*/ 
    /**<en>
     * Set the sort order of AISLENO.
     * @param num  :priority in sort order
     * @param bool :ascending order if specified true
     </en>*/ 
    public void setAisleNoOrder(int num, boolean bool)
    {
        setOrder(AISLENO, num, bool);
    }

    /**<jp>
     * AISLENOのソート順を取得
     * @return AISLENOのソート順
     </jp>*/
    /**<en>
     * Retrieve the sort order of AISLENO.
     * @return :the sort order of AISLENO
     </en>*/
    public int getAisleNoOrder()
    {
        return (getOrder(AISLENO));
    }

    /**<jp>
     * CONTROLLERNOの検索値をセット
     * @param num Controller No
     </jp>*/
    /**<en>
     * Set the search value of CONTROLLERNO.
     * @param num Controller No
     </en>*/
    public void setControllerNo(int num)
    {
        setValue(CONTROLLERNO, num);
    }

    /**<jp>
     * CONTROLLERNOの検索値を取得
     * @return Controller No
     </jp>*/
    /**<en>
     * Retrieve the search value of CONTROLLERNO.
     * @return Controller No
     </en>*/
    public int getControllerNo()
    {
        Integer intobj = (Integer)getValue(CONTROLLERNO);
        return (intobj.intValue());
    }
    /**<jp>
     *CONTROLLERNOのソート順セット
     * @param num ソートの優先順位
     * @param bool trueを指定すると昇順
     </jp>*/ 
    /**<en>
     * Set the sort order of CONTROLLERNO.
     * @param num  :priority in sort order
     * @param bool :ascending order if specified true
     </en>*/ 
    public void setControllerNoOrder(int num, boolean bool)
    {
        setOrder(CONTROLLERNO, num, bool);
    }

    /**<jp>
     * CONTROLLERNOのソート順を取得
     * @return CONTROLLERNOのソート順
     </jp>*/
    /**<en>
     * Retrieve the sort order of CONTROLLERNO.
     * @return :the sort order of CONTROLLERNO
     </en>*/
    public int getControllerNoOrder()
    {
        return (getOrder(CONTROLLERNO));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

