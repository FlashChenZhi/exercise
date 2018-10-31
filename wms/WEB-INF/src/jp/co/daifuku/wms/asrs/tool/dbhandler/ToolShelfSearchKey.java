// $Id: ToolShelfSearchKey.java 4122 2009-04-10 10:58:38Z ota $

package jp.co.daifuku.wms.asrs.tool.dbhandler;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
/**<jp>
 * Shelf表を検索するためのキーをセットするクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/20</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class sets the keys when searching the Shelf table.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/20</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class ToolShelfSearchKey
        extends ToolSQLSearchKey
{
    // Class fields --------------------------------------------------
    //<jp> ここに検索条件又はソートされる可能性のあるカラムを定義します。</jp>
    //<en> Define here the column which may be used as a search condition or the which may be sorted.</en>
    private static final String STATIONNO = "STATION_NO";

    private static final String BANKNO = "BANK_NO";

    private static final String BAYNO = "BAY_NO";

    private static final String LEVELNO = "LEVEL_NO";

    private static final String ADDRESSNO = "ADDRESS_NO";

    private static final String WHSTATIONNO = "WH_STATION_NO";

    private static final String PROHIBITIONFLAG = "PROHIBITION_FLAG";

    private static final String STATUSFLAG = "STATUS_FLAG";

    private static final String HARDZONEID = "HARD_ZONE_ID";

    private static final String SOFTZONEID = "SOFT_ZONE_ID";

    private static final String PARENTSTATIONNO = "PARENT_STATION_NO";

    private static final String ACCESSNGFLAG = "ACCESS_NG_FLAG";

    private static final String WIDTH = "WIDTH";

    private static final String LOCATIONUSEFLAG = "LOCATION_USE_FLAG";


    // Class variables -----------------------------------------------
    private static final String[] Columns = {
            STATIONNO,
            BANKNO,
            BAYNO,
            LEVELNO,
            ADDRESSNO,
            WHSTATIONNO,
            PROHIBITIONFLAG,
            STATUSFLAG,
            HARDZONEID,
            SOFTZONEID,
            PARENTSTATIONNO,
            ACCESSNGFLAG,
            WIDTH,
            LOCATIONUSEFLAG
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
     * デフォルトコンストラクタ
     </jp>*/
    /**<en>
     * Default constructor
     </en>*/
    public ToolShelfSearchKey()
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
     * Retrieve STATIONNO.
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
     * Set the sort order of STATIONNO.
     </en>*/
    public int getStationNoOrder()
    {
        return (getOrder(STATIONNO));
    }

    /**<jp>
     * STATUSFLAGの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of PRESENCE.
     </en>*/
    public void setStatusFlag(int type)
    {
        setValue(STATUSFLAG, type);
    }

    /**<jp>
     * STATUSFLAGの検索値を取得
     </jp>*/
    /**<en>
     * Retrieve the search value of STATUSFLAG.
     </en>*/
    public int getStatusFlag()
    {
        Integer intobj = (Integer)getValue(STATUSFLAG);
        return (intobj.intValue());
    }

    /**<jp>
     * PROHIBITIONFLAGの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of STATUS.
     </en>*/
    public void setProhibitionFlag(int hzone)
    {
        setValue(PROHIBITIONFLAG, hzone);
    }

    /**<jp>
     * PROHIBITIONFLAGを取得
     </jp>*/
    /**<en>
     * Retrieve STATUS.
     </en>*/
    public int getProhibitionFlag()
    {
        Integer intobj = (Integer)getValue(PROHIBITIONFLAG);
        return (intobj.intValue());
    }

    /**<jp>
     * HARDZONEIDの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of HARDZONEID.
     </en>*/
    public void setHardZoneId(int hzone)
    {
        setValue(HARDZONEID, hzone);
    }

    /**<jp>
     * HARDZONEIDを取得
     </jp>*/
    /**<en>
     * Retrieve HARDZONEID.
     </en>*/
    public int getHardZoneId()
    {
        Integer intobj = (Integer)getValue(HARDZONEID);
        return (intobj.intValue());
    }

    /**<jp>
     * HARDZONEIDのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of HARDZONEID.
     </en>*/
    public void setHardZoneIdOrder(int num, boolean bool)
    {
        setOrder(HARDZONEID, num, bool);
    }

    /**<jp>
     * HARDZONEIDのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of HARDZONEID.
     </en>*/
    public int getHardZoneIdOrder()
    {
        return (getOrder(HARDZONEID));
    }

    /**<jp>
     * SOFTZONEIDの検索値をセット
     * @param szone SOFTZONEIDの検索値
     </jp>*/
    /**<en>
     * Set the search value of SOFTZONEID.
     * @param szone :search value of SOFTZONEID
     </en>*/
    public void setSoftZoneId(int szone)
    {
        setValue(SOFTZONEID, szone);
    }

    /**<jp>
     * SOFTZONEIDを取得
     * @return SOFTZONEID
     </jp>*/
    /**<en>
     * RetrieveSOFTZONEID.
     * @return SOFTZONEID
     </en>*/
    public int getSoftZoneId()
    {
        Integer intobj = (Integer)getValue(SOFTZONEID);
        return (intobj.intValue());
    }

    /**<jp>
     * SOFTZONEIDのソート順セット
     * @param num 検索の優先順位
     * @param bool 昇順で検索するときtrue、降順で検索するときfalse
     </jp>*/
    /**<en>
     * Set the sort order of SOFTZONEID.
     * @param num  :priority in search
     * @param bool :true if searching in ascending order, or false if in descending order
     </en>*/
    public void setSoftZoneIdOrder(int num, boolean bool)
    {
        setOrder(SOFTZONEID, num, bool);
    }

    /**<jp>
     * SOFTZONEIDのソート順を取得
     * @return SOFTZONEID順
     </jp>*/
    /**<en>
     * Retrieve the sort order of SOFTZONEID.
     * @return :order of SOFTZONEID
     </en>*/
    public int getSoftZoneIdOrder()
    {
        return (getOrder(SOFTZONEID));
    }

    /**<jp>
     * BANKNOの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of BANKNO.
     </en>*/
    public void setBankNo(int nbank)
    {
        setValue(BANKNO, nbank);
    }

    /**<jp>
     * BANKNOを取得
     </jp>*/
    /**<en>
     * Retrieve BANKNO.
     </en>*/
    public int getBankNo()
    {
        Integer intobj = (Integer)getValue(BANKNO);
        return (intobj.intValue());
    }

    /**<jp>
     * BANKNOのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of BANKNO.
     </en>*/
    public void setBankNoOrder(int num, boolean bool)
    {
        setOrder(BANKNO, num, bool);
    }

    /**<jp>
     * BANKNOのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of BANKNO.
     </en>*/
    public int getBankNoOrder()
    {
        return (getOrder(BANKNO));
    }

    /**<jp>
     * BAYNOの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of BAYNO.
     </en>*/
    public void setBayNo(int nbay)
    {
        setValue(BAYNO, nbay);
    }

    /**<jp>
     * BAYNOを取得
     </jp>*/
    /**<en>
     * Retrieve BAYNO.
     </en>*/
    public int getBayNo()
    {
        Integer intobj = (Integer)getValue(BAYNO);
        return (intobj.intValue());
    }

    /**<jp>
     * BAYNOのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of BAYNO.
     </en>*/
    public void setBayNoOrder(int num, boolean bool)
    {
        setOrder(BAYNO, num, bool);
    }

    /**<jp>
     * BAYNOのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of BAYNO.
     </en>*/
    public int getBayNoOrder()
    {
        return (getOrder(BAYNO));
    }

    /**<jp>
     * LEVELNOの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of LEVELNO.
     </en>*/
    public void setLevelNo(int nlevel)
    {
        setValue(LEVELNO, nlevel);
    }

    /**<jp>
     * LEVELNOを取得
     </jp>*/
    /**<en>
     * Retrieve LEVELNO.
     </en>*/
    public int getLevelNo()
    {
        Integer intobj = (Integer)getValue(LEVELNO);
        return (intobj.intValue());
    }

    /**<jp>
     * LEVELNOのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of LEVELNO.
     </en>*/
    public void setLevelNoOrder(int num, boolean bool)
    {
        setOrder(LEVELNO, num, bool);
    }

    /**<jp>
     * LEVELNOのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of LEVELNO.
     </en>*/
    public int getLevelNoOrder()
    {
        return (getOrder(LEVELNO));
    }

    /**<jp>
     * ADDRESSNOの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of ADDRESSNO.
     </en>*/
    public void setAddressNo(int naddr)
    {
        setValue(ADDRESSNO, naddr);
    }

    /**<jp>
     * ADDRESSNOを取得
     </jp>*/
    /**<en>
     * Retrieve ADDRESSNO.
     </en>*/
    public int getAddressNo()
    {
        Integer intobj = (Integer)getValue(ADDRESSNO);
        return (intobj.intValue());
    }

    /**<jp>
     * ADDRESSNOのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of ADDRESSNO.
     </en>*/
    public void setAddressNoOrder(int num, boolean bool)
    {
        setOrder(ADDRESSNO, num, bool);
    }

    /**<jp>
     * ADDRESSNOのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of ADDRESSNO.
     </en>*/
    public int getAddressNoOrder()
    {
        return (getOrder(ADDRESSNO));
    }

    /**<jp>
     * WHSTATIONNOの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of WHSTATIONNO.
     </en>*/
    public void setWHStationNo(String stnum)
    {
        setValue(WHSTATIONNO, stnum);
    }

    /**<jp>
     * WHSTATIONNOを取得
     </jp>*/
    /**<en>
     * Retrieve WHSTATIONNO.
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
    public void setWarehouseStationNo(String stnum)
    {
        setValue(WHSTATIONNO, stnum);
    }

    /**<jp>
     * WHSTATIONNOを取得
     </jp>*/
    /**<en>
     * Retrieve WHSTATIONNO.
     </en>*/
    public String getWarehouseStationNo()
    {
        return (String)getValue(WHSTATIONNO);
    }

    /**<jp>
     * WHSTATIONNOのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of WHSTATIONNO.
     </en>*/
    public void setWHStationNoOrder(int num, boolean bool)
    {
        setOrder(WHSTATIONNO, num, bool);
    }

    /**<jp>
     * WHSTATIONNOのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of WHSTATIONNO.
     </en>*/
    public int getWHStationNoOrder()
    {
        return (getOrder(WHSTATIONNO));
    }

    /**<jp>
     * PARENTSTATIONNOの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of PARENTSTATIONNO.
     </en>*/
    public void setParentStationNo(String pnum)
    {
        setValue(PARENTSTATIONNO, pnum);
    }

    /**<jp>
     * PARENTSTATIONNOの検索値を取得します。
     * @return 親ステーションNo
     </jp>*/
    /**<en>
     * Retrieve the search value of PARENTSTATIONNO.
     * @return :parent station no.
     </en>*/
    public String getParentStationNo()
    {
        return (String)getValue(PARENTSTATIONNO);
    }

    /**<jp>
     * ACCESSNGFLAGの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of ACCESSNGFLAG.
     </en>*/
    public void setAccessNgFlag(int acc)
    {
        setValue(ACCESSNGFLAG, acc);
    }

    /**<jp>
     * ACCESSNGFLAGの検索値を取得
     </jp>*/
    /**<en>
     * Retrieve the search value of ACCESSNGFLAG.
     </en>*/
    public int getAccessNgFlag()
    {
        Integer intobj = (Integer)getValue(ACCESSNGFLAG);
        return (intobj.intValue());
    }

    /**<jp>
     * WIDTHの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of WIDTH.
     </en>*/
    public void setWidth(int nlevel)
    {
        setValue(WIDTH, nlevel);
    }

    /**<jp>
     * WIDTHを取得
     </jp>*/
    /**<en>
     * Retrieve WIDTH.
     </en>*/
    public int getWidth()
    {
        Integer intobj = (Integer)getValue(WIDTH);
        return (intobj.intValue());
    }

    /**<jp>
     * WIDTHのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of WIDTH.
     </en>*/
    public void setWidthOrder(int num, boolean bool)
    {
        setOrder(WIDTH, num, bool);
    }

    /**<jp>
     * WIDTHのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of WIDTH.
     </en>*/
    public int getWidthOrder()
    {
        return (getOrder(WIDTH));
    }

    /**<jp>
     * LOCATIONUSEFLAGの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of LOCATIONUSEFLAG.
     </en>*/
    public void setLocationUseFlag(int nlevel)
    {
        setValue(LOCATIONUSEFLAG, nlevel);
    }

    /**<jp>
     * LOCATIONUSEFLAGを取得
     </jp>*/
    /**<en>
     * Retrieve LOCATIONUSEFLAG.
     </en>*/
    public int getLocationUseFlag()
    {
        Integer intobj = (Integer)getValue(LOCATIONUSEFLAG);
        return (intobj.intValue());
    }

    /**<jp>
     * LOCATIONUSEFLAGのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of LOCATIONUSEFLAG.
     </en>*/
    public void setLocationUseFlagOrder(int num, boolean bool)
    {
        setOrder(LOCATIONUSEFLAG, num, bool);
    }

    /**<jp>
     * LOCATIONUSEFLAGのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of LOCATIONUSEFLAG.
     </en>*/
    public int getLocationUseFlagOrder()
    {
        return (getOrder(LOCATIONUSEFLAG));
    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

