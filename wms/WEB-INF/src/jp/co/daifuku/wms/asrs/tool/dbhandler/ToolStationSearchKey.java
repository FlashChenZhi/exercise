// $Id: ToolStationSearchKey.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.communication.as21.GroupController;
/**<jp>
 * ステーション番号もしくは、ステーションIDをキーとして、ステーション検索を行う場合に使われる、キー指定のためのクラス。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class specifies the keys for station search by using station no. or the station ID
 * as keys.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ToolStationSearchKey extends ToolSQLSearchKey
{
    // Class fields --------------------------------------------------
    //<jp> ここに検索条件又はソートされる可能性のあるカラムを定義します。</jp>
    //<en> Define here the column which may be used as a search condition or the which may be sorted.</en>
    private static final String STATIONNO           = "STATION_NO";
    private static final String STATIONTYPE         = "STATION_TYPE";
    private static final String CONTROLLERNO        = "CONTROLLER_NO";
    private static final String WHSTATIONNO         = "WH_STATION_NO";
    private static final String SENDABLE            = "SENDABLE";
    private static final String PARENTSTATIONNO     = "PARENT_STATION_NO";
    private static final String STATUS              = "STATUS";
    private static final String AISLESTATIONNO      = "AISLE_STATION_NO";
    private static final String MODETYPE            = "MODE_TYPE";
    private static final String WORKPLACETYPE       = "WORKPLACE_TYPE";

    // Class variables -----------------------------------------------
    private static final String[] Columns =
    {
        STATIONNO,
        STATIONTYPE,
        CONTROLLERNO,
        WHSTATIONNO,
        SENDABLE,
        PARENTSTATIONNO,
        STATUS,
        AISLESTATIONNO,
        MODETYPE,
        WORKPLACETYPE
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
    public ToolStationSearchKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * STATIONNOの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of STATIONNO.
     </en>*/
    public void setStationNo(String stnum)
    {
        setValue(STATIONNO, stnum);
    }

    /**<jp>
     * STATIONNOを取得
     </jp>*/
    /**<en>
     * Retrieve the STATIONNO.
     </en>*/
    public String getStationNo()
    {
        return (String)getValue(STATIONNO);
    }

    /**<jp>
     * STATIONNOの検索値をセット
     * 配列で渡されたSTATIONNO同士はORで結合されます。
     </jp>*/
    /**<en>
     * Set the search value of STATIONNO.
     * Connect each station no. passed through parameter by placing OR in between.
     </en>*/
    public void setStationNo(String[] stnums)
    {
        setValue(STATIONNO, stnums);
    }

    /**<jp>
     * STATIONNOのソート順セット
     </jp>*/
    /**<en>
     * Set the order of sorting STATIONNO.
     </en>*/
    public void setStationNoOrder(int num, boolean bool)
    {
        setOrder(STATIONNO, num, bool);
    }

    /**<jp>
     * STATIONNOのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the order of sorting STATIONNO.
     </en>*/
    public int getStationNoOrder()
    {
        return (getOrder(STATIONNO));
    }

    /**<jp>
     * STATIONTYPEの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of STATIONTYPE.
     </en>*/
    public void setStationType(int type)
    {
        setValue(STATIONTYPE, type);
    }

    /**<jp>
     * STATIONTYPEの検索値をセット
     * 配列で渡されたSTATIONTYPE同士はORで結合されます。
     </jp>*/
    /**<en>
     * Set the search value of STATIONTYPE.
     * Connect each station type passed through parameter by placing OR in between.
     </en>*/
    public void setStationType(int[] types)
    {
        setValue(STATIONTYPE, types);
    }


    /**<jp>
     * STATIONTYPEの検索値を取得
     </jp>*/
    /**<en>
     * Retrieve the search value of STATIONTYPE.
     </en>*/
    public int getStationType()
    {
        Integer intobj = (Integer)getValue(STATIONTYPE);
        return (intobj.intValue());
    }

    /**<jp>
     * CONTROLLERNOの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of CONTROLLERNO.
     </en>*/
    public void setController(GroupController gcon)
    {
        setControllerNo(gcon.getNumber());
    }

    /**<jp>
     * CONTROLLERNOの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of CONTROLLERNO.
     </en>*/
    public void setControllerNo(int num)
    {
        setValue(CONTROLLERNO, num);
    }

    /**<jp>
     * CONTROLLERNOの検索値を取得
     </jp>*/
    /**<en>
     * Retrieve the search value of CONTROLLERNO.
     </en>*/
    public int getControllerNo()
    {
        Integer intobj = (Integer)getValue(CONTROLLERNO);
        return (intobj.intValue());
    }

    /**<jp>
     * WHSTATIONNOの検索値をセット
     * @deprecated setWareHouseStationNoを使用してください。
     </jp>*/
    /**<en>
     * Set the search value of WHSTATIONNO.
     * @deprecated :please use setWareHouseStationNo.
     </en>*/
    public void setWHStationNo(String WHSTNumber)
    {
        setValue(WHSTATIONNO, WHSTNumber);
    }

    /**<jp>
     * WHSTATIONNOの検索値を取得
     * @deprecated getWareHouseStationNoを使用してください。
     </jp>*/
    /**<en>
     * Retrieve the search value of WHSTATIONNO.
     * @deprecated :please use getWareHouseStationNo.
     </en>*/
    public String getWHStationNo()
    {
        return (String)getValue(WHSTATIONNO);
    }

    /**<jp>
     * WHSTATIONNOの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of WHSTATIONNO.
     </en>*/
    public void setWareHouseStationNo(String WHSTNumber)
    {
        setValue(WHSTATIONNO, WHSTNumber);
    }

    /**<jp>
     * WHSTATIONNOの検索値を取得
     </jp>*/
    /**<en>
     * Retrieve the search value of WHSTATIONNO.
     </en>*/
    public String getWareHouseStationNo()
    {
        return (String)getValue(WHSTATIONNO);
    }

    /**<jp>
     * WHSTATIONNOのソート順セット
     </jp>*/
    /**<en>
     * Set the order of sorting WHSTATIONNO.
     </en>*/
    public void setWareHouseStationNoOrder(int num, boolean bool)
    {
        setOrder(WHSTATIONNO, num, bool);
    }

    /**<jp>
     * WHSTATIONNOのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the order of sorting WHSTATIONNO.
     </en>*/
    public int getWareHouseStationNoOrder()
    {
        return (getOrder(WHSTATIONNO));
    }

    /**<jp>
     * SENDABLEの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of SENDABLE.
     </en>*/
    public void setSendable(int sendable)
    {
        setValue(SENDABLE, sendable);
    }

    /**<jp>
     * SENDABLEの検索値を取得
     </jp>*/
    /**<en>
     * Retrieve the search value of SENDABLE.
     </en>*/
    public int getSendable()
    {
        Integer intobj = (Integer)getValue(SENDABLE);
        return (intobj.intValue());
    }

    /**<jp>
     * PARENTSTATIONNOの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of PARENTSTATIONNO.
     </en>*/
    public void setParentStationNo(String pstnum)
    {
        setValue(PARENTSTATIONNO, pstnum);
    }

    /**<jp>
     * PARENTSTATIONNOの検索値を取得
     </jp>*/
    /**<en>
     * Retrieve the search value of PARENTSTATIONNO.
     </en>*/
    public String getParentStationNo()
    {
        return (String)getValue(PARENTSTATIONNO);
    }

    /**<jp>
     * STATUSの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of STATUS.
     </en>*/
    public void setStatus(int stat)
    {
        setValue(STATUS, stat);
    }

    /**<jp>
     * STATUSの検索値を取得
     </jp>*/
    /**<en>
     * Retrieve the search value of STATUS.
     </en>*/
    public int getStatus()
    {
        Integer intobj = (Integer)getValue(STATUS);
        return (intobj.intValue());
    }

    /**<jp>
     * AISLESTATIONNOの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of AISLESTATIONNO.
     </en>*/
    public void setAisleStationNo(String aile)
    {
        setValue(AISLESTATIONNO, aile);
    }

    /**<jp>
     * AISLESTATIONNOの検索値を取得
     </jp>*/
    /**<en>
     * Retrieve the search value of AISLESTATIONNO.
     </en>*/
    public String getAisleStationNo()
    {
        return (String)getValue(AISLESTATIONNO);
    }

    /**<jp>
     * AISLESTATIONNOのソート順セット
     </jp>*/
    /**<en>
     * Set the order of sorting AISLESTATIONNO.
     </en>*/
    public void setAisleStationNoOrder(int num, boolean bool)
    {
        setOrder(AISLESTATIONNO, num, bool);
    }

    /**<jp>
     * AISLESTATIONNOのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the order of sorting AISLESTATIONNO.
     </en>*/
    public int getAisleStationNoOrder()
    {
        return (getOrder(AISLESTATIONNO));
    }

    /**<jp>
     * MODETYPEの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of MODETYPE.
     </en>*/
    public void setModeType(int type)
    {
        setValue(MODETYPE, type);
    }

    /**<jp>
     * MODETYPEの検索値を取得
     </jp>*/
    /**<en>
     * Set the search value of MODETYPE.
     </en>*/
    public int getModeType()
    {
        Integer intobj = (Integer)getValue(MODETYPE);
        return (intobj.intValue());
    }

    /**<jp>
     * MODETYPEの検索値をセット
     * 配列で渡されたSTATIONNO同士はORで結合されます。
     </jp>*/
    /**<en>
     * Set the search value of MODETYPE.
     * Connect each mode type passed through parameter by placing OR in between.
     </en>*/
    public void setModeType(int[] type)
    {
        setValue(MODETYPE, type);
    }

    /**<jp>
     * WORKPLACETYPEの検索値をセットします。
     * @param type 検索対象となるゾーン種別
     </jp>*/
    /**<en>
     * Set the search value of WORKPLACETYPE.
     * @param type :zone type to be searched
     </en>*/
    public void setWorkPlaceType(int type)
    {
        setValue(WORKPLACETYPE, type);
    }

    /**<jp>
     * WORKPLACETYPEの検索値を取得します。
     * @return ゾーン種別
     </jp>*/
    /**<en>
     * Retrieve the search value of WORKPLACETYPE.
     * @return :zone type
     </en>*/
    public int getWorkPlaceType()
    {
        Integer intobj = (Integer)getValue(WORKPLACETYPE);
        return (intobj.intValue());
    }

    /**<jp>
     * WORKPLACETYPEの検索値をセットします。
     * 配列で渡されたSTATIONTYPE同士はORで結合されます。
     * @param type 検索対象となるゾーン種別
     </jp>*/
    /**<en>
     * Set the search value of WORKPLACETYPE.
     * Connect each station type passed through parameter by placing OR in between.
     * @param type :zone type to be searched
     </en>*/
    public void setWorkPlaceType(int[] type)
    {
        setValue(WORKPLACETYPE, type);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

