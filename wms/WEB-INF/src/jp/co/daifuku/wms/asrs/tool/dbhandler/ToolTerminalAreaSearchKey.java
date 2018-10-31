// $Id: ToolTerminalAreaSearchKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * ハンドラクラスを使用してTERMINALAREAテーブルを検索し、TERMINALAREAクラスのインスタンスを生成するために使用するキークラスです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/12/12</TD><TD>INOUE</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This key class is used to search TERMINALAREA table using handler class and to generate the 
 * instance of TERMINALAREA class.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/12/12</TD><TD>INOUE</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ToolTerminalAreaSearchKey extends ToolSQLSearchKey
{
    // Class fields --------------------------------------------------
    //<jp> ここに検索条又はソートされる可能性のあるカラムを定義します。</jp>
    //<en> Define here the column which may be used as a search condition or the which may be sorted. </en>
    private static final String STATIONNO  = "STATION_NO";
    private static final String AREAID     = "AREA_ID";
    private static final String TERMINALNO = "TERMINAL_NO";

    // Class variables -----------------------------------------------
    private static final String[] Columns =
    {
        STATIONNO,
        AREAID,
        TERMINALNO
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
     * カラム定義を設定します。
     </jp>*/
    /**<en>
     * Set the column definition.
     </en>*/
    public ToolTerminalAreaSearchKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * SERIALNUMBERの検索値をセットします。
     * @param snum 検索するシリアルNo
     </jp>*/
    /**<en>
     * Set the search value of SERIALNUMBER.
     * @param snum :serial no. to serach
     </en>*/
    /**<jp>
     * TERMINALNOの検索値をセットします。
     * @param terminalnum 検索対象となる端末No.
     </jp>*/
    /**<en>
     * Set the search value of TERMINALNO.
     * @param terminalnum :terminal no. to be searched
     </en>*/
    public void setTerminalNo(String terminalnum)
    {
        setValue(TERMINALNO, terminalnum);
    }

    /**<jp>
     * TERMINALNOの検索値を取得します。
     * @return 端末No.
     </jp>*/
    /**<en>
     * Retrieve the search value of TERMINALNO.
     * @return :terminal no.
     </en>*/
    public String getTerminalNo()
    {
        return (String)getValue(TERMINALNO);
    }

    /**<jp>
     * TERMINALNOのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of TERMINALNO.
     </en>*/
    public void setTerminalNoOrder(int num, boolean bool)
    {
        setOrder(TERMINALNO, num, bool);
    }

    /**<jp>
     * TERMINALNOのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of TERMINALNO.
     </en>*/
    public int getTerminalNoOrder()
    {
        return (getOrder(TERMINALNO));
    }
    
    /**<jp>
     * AREAIDの検索値をセットします。
     * @param areaid 検索対象となるエリアID
     </jp>*/
    /**<en>
     * Set the search value of AREAID.
     * @param areaid :area ID to be searched
     </en>*/
    public void setAreaId(int areaid)
    {
        setValue(AREAID, areaid);
    }

    /**<jp>
     * AREAIDの検索値を取得します。
     * @return エリアID
     </jp>*/
    /**<en>
     * Retrieve the search value of AREAID.
     * @return :area ID
     </en>*/
    public int getAreaId()
    {
        Integer intobj = (Integer)getValue(AREAID);
        return(intobj.intValue());
    }

    /**<jp>
     * AREAIDのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of AREAID.
     </en>*/
    public void setAreaIdOrder(int num, boolean bool)
    {
        setOrder(AREAID, num, bool);
    }

    /**<jp>
     * AREAIDのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of AREAID.
     </en>*/
    public int getAreaIdOrder()
    {
        return (getOrder(AREAID));
    }

    
    /**<jp>
     * STATIONNOの検索値をセットします。
     * @param num 検索対象となるSTATIONNO
     </jp>*/
    /**<en>
     * Set the search value of STATIONNO.
     * @param num :STATIONNO to be searched
     </en>*/
    public void setStationNo(String num)
    {
        setValue(STATIONNO, num);
    }

    /**<jp>
     * STATIONNOの検索値を取得します。
     * @return STATIONNO
     </jp>*/
    /**<en>
     * Retrieve the search value of STATIONNO.
     * @return STATIONNO
     </en>*/
    public String getStationNo()
    {
        return (String)getValue(STATIONNO);
    }

    /**<jp>
     * STATIONNOのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of STATIONNO.
     </en>*/
    public void setStationNoOrder(int num, boolean bool)
    {
        setOrder(STATIONNO, num, bool);
    }

    /**<jp>
     * STATIONNOのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of STATIONNO.
     </en>*/
    public int getStationNoOrder()
    {
        return (getOrder(STATIONNO));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

