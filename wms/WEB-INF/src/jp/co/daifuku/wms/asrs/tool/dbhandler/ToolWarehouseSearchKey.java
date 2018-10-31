// $Id: ToolWarehouseSearchKey.java 5301 2009-10-28 05:36:02Z ota $
package jp.co.daifuku.wms.asrs.tool.dbhandler;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * Warehouse表を検索するためのキーをセットするクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5301 $, $Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class is used to set keys for Warehouse table search.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5301 $, $Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $
 * @author  $Author: ota $
 </en>*/
public class ToolWarehouseSearchKey
        extends ToolSQLSearchKey
{
    // Class fields --------------------------------------------------
    //<jp> ここに検索条件又はソートされる可能性のあるカラムを定義します。</jp>
    //<en> Define here the column which may be used as a search condition or the which may be sorted.</en>
    private static final String WAREHOUSENO = "WAREHOUSE_NO";

    private static final String WAREHOUSETYPE = "WAREHOUSE_TYPE";

    private static final String STATIONNO = "STATION_NO";

    private static final String ZONETYPE = "ZONE_TYPE";

    private static final String FREEALLOCATION = "FREE_ALLOCATION_TYPE";

    // Class variables -----------------------------------------------
    private static final String[] Columns = {
            WAREHOUSENO,
            WAREHOUSETYPE,
            STATIONNO,
            ZONETYPE,
            FREEALLOCATION
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
        return ("$Revision: 5301 $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルトコンストラクタ
     </jp>*/
    /**<en>
     * Default constructor
     </en>*/
    public ToolWarehouseSearchKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * WAREHOUSENOの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of WAREHOUSENO.
     </en>*/
    public void setWarehouseNo(int whnum)
    {
        setValue(WAREHOUSENO, whnum);
    }

    /**<jp>
     * WAREHOUSENOを取得
     </jp>*/
    /**<en>
     * Retrieve the WAREHOUSENO.
     </en>*/
    public String getWarehouseNo()
    {
        return (String)getValue(WAREHOUSENO);
    }

    /**<jp>
     * WAREHOUSENOのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of WAREHOUSENO.
     </en>*/
    public void setWarehouseNoOrder(int num, boolean bool)
    {
        setOrder(WAREHOUSENO, num, bool);
    }

    /**<jp>
     * WAREHOUSENOのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of WAREHOUSENO.
     </en>*/
    public int getWarehouseNoOrder()
    {
        return (getOrder(WAREHOUSENO));
    }

//    /**<jp>
//     * WAREHOUSETYPEの検索値をセット
//     </jp>*/
//    /**<en>
//     * Set the search value of WAREHOUSETYPE.
//     </en>*/
//    public void setWarehouseType(int arg)
//    {
//        setValue(WAREHOUSETYPE, arg);
//    }
//
//    /**<jp>
//     * WAREHOUSETYPEの検索値を取得
//     </jp>*/
//    /**<en>
//     * Retrieve the search value of WAREHOUSETYPE.
//     </en>*/
//    public int getWarehouseType()
//    {
//        Integer intobj = (Integer)getValue(WAREHOUSETYPE);
//        return (intobj.intValue());
//    }
//
//    /**<jp>
//     * WAREHOUSETYPEのソート順セット
//     </jp>*/
//    /**<en>
//     * Set the sort order of WAREHOUSETYPE.
//     </en>*/
//    public void setWarehouseTypeOrder(int num, boolean bool)
//    {
//        setOrder(WAREHOUSETYPE, num, bool);
//    }
//
//    /**<jp>
//     * WAREHOUSETYPEのソート順を取得
//     </jp>*/
//    /**<en>
//     * Retrieve the sort order of WAREHOUSETYPE.
//     </en>*/
//    public int getWarehouseTypeOrder()
//    {
//        return (getOrder(WAREHOUSETYPE));
//    }

    /**<jp>
     * STATIONNO（倉庫ステーションNo）の検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of STATIONNO (warehouse station no.).
     </en>*/
    public void setWarehouseStationNo(String whnum)
    {
        setValue(STATIONNO, whnum);
    }

    /**<jp>
     * STATIONNO（倉庫ステーションNo）を取得
     </jp>*/
    /**<en>
     * Retrieve the STATIONNO(warehouse station no.).
     </en>*/
    public String getWarehouseStationNo()
    {
        return (String)getValue(STATIONNO);
    }

    /**<jp>
     * STATIONNO（倉庫ステーションNo）のソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of STATIONNO(warehouse station no.).
     </en>*/
    public void setWarehouseStationNoOrder(int num, boolean bool)
    {
        setOrder(STATIONNO, num, bool);
    }

    /**<jp>
     * STATIONNO（倉庫ステーションNo）のソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of STATIONNO(warehouse station no.).
     </en>*/
    public int getWarehouseStationNoOrder()
    {
        return (getOrder(STATIONNO));
    }

    /**<jp>
     * ZONETYPEの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of ZONETYPE.
     </en>*/
    public void setZoneType(int arg)
    {
        setValue(ZONETYPE, arg);
    }

    /**<jp>
     * ZONETYPEの検索値を取得
     </jp>*/
    /**<en>
     * Retrieve the search value of ZONETYPE.
     </en>*/
    public int getZoneType()
    {
        Integer intobj = (Integer)getValue(ZONETYPE);
        return (intobj.intValue());
    }

    /**<jp>
     * ZONETYPEの検索値をセット
     * 配列で渡されたZONETYPE同士はORで結合されます。
     </jp>*/
    /**<en>
     * Set the search value of ZONETYPE.
     * Each aone type passed through parameter will be connected by placing OR in between.
     </en>*/
    public void setZoneType(int[] arg)
    {
        setValue(ZONETYPE, arg);
    }


    /**<jp>
     * ZONETYPEのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of ZONETYPE.
     </en>*/
    public void setZoneTypeOrder(int num, boolean bool)
    {
        setOrder(ZONETYPE, num, bool);
    }

    /**<jp>
     * ZONETYPEのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of ZONETYPE.
     </en>*/
    public int getZoneTypeOrder()
    {
        return (getOrder(ZONETYPE));
    }

    /**<jp>
     * FREEALLOCATIONの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of FREEALLOCATION.
     </en>*/
    public void setFreeAllocationType(int arg)
    {
        setValue(FREEALLOCATION, arg);
    }

    /**<jp>
     * FREEALLOCATIONの検索値を取得
     </jp>*/
    /**<en>
     * Retrieve the search value of FREEALLOCATION.
     </en>*/
    public int getFreeAllocationType()
    {
        Integer intobj = (Integer)getValue(FREEALLOCATION);
        return (intobj.intValue());
    }

    /**<jp>
     * FREEALLOCATIONの検索値をセット
     * 配列で渡されたZONETYPE同士はORで結合されます。
     </jp>*/
    /**<en>
     * Set the search value of FREEALLOCATION.
     * Each aone type passed through parameter will be connected by placing OR in between.
     </en>*/
    public void setFreeAllocationType(int[] arg)
    {
        setValue(FREEALLOCATION, arg);
    }


    /**<jp>
     * FREEALLOCATIONのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of FREEALLOCATION.
     </en>*/
    public void setFreeAllocationTypeOrder(int num, boolean bool)
    {
        setOrder(FREEALLOCATION, num, bool);
    }

    /**<jp>
     * FREEALLOCATIONのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of FREEALLOCATION.
     </en>*/
    public int getFreeAllocationTypeOrder()
    {
        return (getOrder(FREEALLOCATION));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

