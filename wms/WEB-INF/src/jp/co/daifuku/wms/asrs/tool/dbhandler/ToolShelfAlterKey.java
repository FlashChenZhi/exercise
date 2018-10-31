// $Id: ToolShelfAlterKey.java 87 2008-10-04 03:07:38Z admin $

package jp.co.daifuku.wms.asrs.tool.dbhandler ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.location.Shelf;
/**<jp>
 * Shelfテーブルの更新を行うための情報を定義したクラスです。
 * ShelfHandlerでテーブル更新を行う場合に使用されます。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/11</TD><TD>okamura</TD><TD>更新条件設定メソッドを追加。<BR>
 * setAccessNgFlagメソッド、getAccessNgFlagメソッドを追加しました。
 * </TD></TR>
 * <TR><TD></TD><TD></TD><TD>更新値設定メソッドを修正。<BR>
 * updateAccessNgFlagメソッドのパラメータがtrueならばアクセス不可棚に、falseならばアクセス可棚に。
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class defines the information which is used in the update of Shelf table.
 * It will be used when updating data using ShelfHandler.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/11</TD><TD>okamura</TD><TD>added the setting method of update conditions.<BR>
 * setAccessNgFlag method and getAccessNgFlag method have been added.
 * </TD></TR>
 * <TR><TD></TD><TD></TD><TD>Modified the update value setting method.<BR>
 * If the parameter of updateAccessNgFlag method is true, set as inaccessible locations.
 * If this flag is false, set as acessible locations.
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ToolShelfAlterKey extends ToolSQLAlterKey
{
    // Class fields --------------------------------------------------

    //<jp> 検索条件および更新対象となる可能性のあるカラムを定義します。</jp>
    //<en> Define here the columns which could be search conditions or the target data of update.</en>
    private static final String STATIONNO               = "TEMP_DMSHELF.STATION_NO";
    private static final String BANKNO                  = "TEMP_DMSHELF.BANK_NO";
    private static final String BAYNO                   = "TEMP_DMSHELF.BAY_NO";
    private static final String LEVELNO                 = "TEMP_DMSHELF.LEVEL_NO";
    private static final String WHSTATIONNO             = "TEMP_DMSHELF.WH_STATION_NO";
    private static final String PROHIBITIONFLAG         = "TEMP_DMSHELF.PROHIBITION_FLAG";
    private static final String STATUSFLAG              = "TEMP_DMSHELF.STATUS_FLAG";
    private static final String HARDZONEID              = "TEMP_DMSHELF.HARD_ZONE_ID";
    private static final String SOFTZONEID              = "TEMP_DMSHELF.SOFT_ZONE_ID";
    private static final String PARENTSTATIONNO         = "TEMP_DMSHELF.PARENT_STATION_NO";
    private static final String ACCESSNGFLAG            = "TEMP_DMSHELF.ACCESS_NG_FLAG";
    private static final String PRIORITY                = "TEMP_DMSHELF.PRIORITY";
    private static final String PAIRSTATIONNUMBER       = "TEMP_DMSHELF.PAIR_STATION_NO";
    private static final String SIDE                    = "TEMP_DMSHELF.SIDE";

    // Class variables -----------------------------------------------

    //<jp> 宣言されたカラム名を定義した変数を配列にセットします。</jp>
    //<en> Set the variable defined with the declared column to the array.</en>
    private static final String[] Columns =
    {
        STATIONNO,
        BANKNO,
        BAYNO,
        LEVELNO,
        WHSTATIONNO,
        PROHIBITIONFLAG,
        STATUSFLAG,
        HARDZONEID,
        SOFTZONEID,
        PARENTSTATIONNO,
        ACCESSNGFLAG,
        PRIORITY,
        PAIRSTATIONNUMBER,
        SIDE
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
     * 宣言されたテーブルカラムの初期設定を行います。
     </jp>*/
    /**<en>
     * Initialize the declared table column.
     </en>*/
    public ToolShelfAlterKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------
    //<jp>======================<更新条件設定メソッド>====================</jp>
    //<en>==========<Method of update condition settings>================</en>

    /**<jp>
     * STATIONNOの検索値をセットします。
     * @param STATIONNOの更新値
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
     * STATIONNOを取得します。
     * @return STATIONNO
     </jp>*/
    /**<en>
     * Retrieve STATIONNO.
     * @return STATIONNO
     </en>*/
    public String getStationNo()
    {
        return (String)getValue(STATIONNO);
    }

    /**<jp>
     * BANKNOの検索値をセットします。
     * @param BANKNOの更新値
     </jp>*/
    /**<en>
     * Set the search value of BANKNO.
     * @param nbank :the search value of BANKNO
     </en>*/
    public void setBankNo(int nbank)
    {
        setValue(BANKNO, nbank);
    }

    /**<jp>
     * BANKNOを取得します。
     * @return BANKNO
     </jp>*/
    /**<en>
     * Retrieve BANKNO.
     * @return BANKNO
     </en>*/
    public String getBankNo()
    {
        return (String)getValue(BANKNO);
    }

    /**<jp>
     * BAYNOの検索値をセットします。
     * @param BAYNOの更新値
     </jp>*/
    /**<en>
     * Set the search value of BAYNO.
     * @param nbay :the search value of BAYNO
     </en>*/
    public void setBayNo(int nbay)
    {
        setValue(BAYNO, nbay);
    }

    /**<jp>
     * BAYNOを取得します。
     * @return BAYNO
     </jp>*/
    /**<en>
     * Retrieve BAYNO.
     * @return BAYNO
     </en>*/
    public String getBayNo()
    {
        return (String)getValue(BAYNO);
    }

    /**<jp>
     * LEVELNOの検索値をセットします。
     * @param LEVELNOの更新値
     </jp>*/
    /**<en>
     * Set the search value of LEVELNO.
     * @param nlevel :the search value of LEVELNO
     </en>*/
    public void setLevelNo(int nlevel)
    {
        setValue(LEVELNO, nlevel);
    }

    /**<jp>
     * LEVELNOを取得します。
     * @return LEVELNO
     </jp>*/
    /**<en>
     * Retrieve LEVELNO.
     * @return LEVELNO
     </en>*/
    public String getLevelNo()
    {
        return (String)getValue(LEVELNO);
    }

    /**<jp>
     * WHSTATIONNOの検索値をセットします。
     * @param WHSTATIONNOの更新値
     </jp>*/
    /**<en>
     * Set the search value of WHSTATIONNO.
     * @param whstnum :the search value of WHSTATIONNO
     </en>*/
    public void setWHStationNo(String whstnum)
    {
        setValue(WHSTATIONNO, whstnum);
    }

    /**<jp>
     * WHSTATIONNOを取得します。
     * @return WHSTATIONNO
     </jp>*/
    /**<en>
     * Retrieve WHSTATIONNO.
     * @return WHSTATIONNO
     </en>*/
    public String getWHStationNo()
    {
        return (String)getValue(WHSTATIONNO);
    }

    /**<jp>
     * PROHIBITIONFLAGの検索値をセットします。
     * @param PROHIBITIONFLAGの更新値
     </jp>*/
    /**<en>
     * Set the search value of PROHIBITIONFLAG.
     * @param flag :the search value of PROHIBITIONFLAG
     </en>*/
    public void setProhibitionFlag(String flag)
    {
        setValue(PROHIBITIONFLAG, flag);
    }

    /**<jp>
     * PROHIBITIONFLAGを取得します。
     * @return PROHIBITIONFLAG
     </jp>*/
    /**<en>
     * Retrieve PROHIBITIONFLAG.
     * @return PROHIBITIONFLAG
     </en>*/
    public String getProhibitionFlag()
    {
        return (String)getValue(PROHIBITIONFLAG);
    }

    /**<jp>
     * STATUSFLAGの検索値をセットします。
     * @param STATUSFLAGの更新値
     </jp>*/
    /**<en>
     * Set the search value of STATUSFLAG.
     * @param flag :the search value of STATUSFLAG
     </en>*/
    public void setStatusFlag(String flag)
    {
        setValue(STATUSFLAG, flag);
    }

    /**<jp>
     * STATUSFLAGを取得します。
     * @return STATUSFLAG
     </jp>*/
    /**<en>
     * Retrieve STATUSFLAG.
     * @return STATUSFLAG
     </en>*/
    public String getStatusFlag()
    {
        return (String)getValue(STATUSFLAG);
    }

    /**<jp>
     * HARDZONEIDの検索値をセットします。
     * @param HARDZONEIDの更新値
     </jp>*/
    /**<en>
     * Set the search value of HARDZONEID.
     * @param flag :the search value of HARDZONEID
     </en>*/
    public void setHardZoneId(String flag)
    {
        setValue(HARDZONEID, flag);
    }

    /**<jp>
     * HARDZONEIDを取得します。
     * @return HARDZONEID
     </jp>*/
    /**<en>
     * Retrieve HARDZONEID.
     * @return HARDZONEID
     </en>*/
    public String getHardZoneId()
    {
        return (String)getValue(HARDZONEID);
    }

    /**<jp>
     * SOFTZONEIDの検索値をセットします。
     * @param SOFTZONEIDの更新値
     </jp>*/
    /**<en>
     * Set the search value of SOFTZONEID.
     * @param flag :the search value of SOFTZONEID
     </en>*/
    public void setSoftZoneId(String flag)
    {
        setValue(SOFTZONEID, flag);
    }

    /**<jp>
     * SOFTZONEIDを取得します。
     * @return SOFTZONEID
     </jp>*/
    /**<en>
     * Retrieve SOFTZONEID.
     * @return SOFTZONEID
     </en>*/
    public String getSoftZoneId()
    {
        return (String)getValue(SOFTZONEID);
    }

    /**<jp>
     * PARENTSTATIONNOの検索値をセットします。
     * @param PARENTSTATIONNOの更新値
     </jp>*/
    /**<en>
     * Set the search value of PARENTSTATIONNO.
     * @param stnum :the search value of PARENTSTATIONNO
     </en>*/
    public void setParentStationNo(String stnum)
    {
        setValue(PARENTSTATIONNO, stnum);
    }

    /**<jp>
     * PARENTSTATIONNOを取得します。
     * @return PARENTSTATIONNO
     </jp>*/
    /**<en>
     * Retrieve PARENTSTATIONNO.
     * @return PARENTSTATIONNO
     </en>*/
    public String getParentStationNo()
    {
        return (String)getValue(PARENTSTATIONNO);
    }

    /**<jp>
     * ACCESSNGFLAGの検索値をセットします。
     * @param ACCESSNGFLAGの検索値
     </jp>*/
    /**<en>
     * Set the search value of ACCESSNGFLAG.
     * @param acc :the search value of ACCESSNGFLAG
     </en>*/
    public void setAccessNgFlag(int acc)
    {
        setValue(ACCESSNGFLAG, acc);
    }

    /**<jp>
     * ACCESSNGFLAGを取得します。
     * @return ACCESSNGFLAG
     </jp>*/
    /**<en>
     * Retrieve ACCESSNGFLAG.
     * @return ACCESSNGFLAG
     </en>*/
    public String getAccessNgFlag()
    {
        return (String)getValue(ACCESSNGFLAG);
    }

    /**<jp>
     * PAIRSTATIONNUMBERの検索値をセットします。
     * @param PAIRSTATIONNUMBERの更新値
     </jp>*/
    /**<en>
     * Set the search value of PAIRSTATIONNUMBER.
     * @param stnum :the search value of PAIRSTATIONNUMBER
     </en>*/
    public void setParentStationNumber(String stnum)
    {
        setValue(PAIRSTATIONNUMBER, stnum);
    }

    /**<jp>
     * PAIRSTATIONNUMBERを取得します。
     * @return PAIRSTATIONNUMBER
     </jp>*/
    /**<en>
     * Retrieve PAIRSTATIONNUMBER.
     * @return PAIRSTATIONNUMBER
     </en>*/
    public String getParentStationNumber()
    {
        return (String)getValue(PAIRSTATIONNUMBER);
    }

/*  2003/12/11  INSERT  okamura START  */
/*  2003/12/11  INSERT  okamura END  */
    //<jp>======================<更新値設定メソッド>======================</jp>
    //<en>========<Method of update valud settings>=============</en>
    /**<jp>
     * STATUSFLAGの更新値をセットします。
     * @param STATUSFLAGの更新値
     </jp>*/
    /**<en>
     * Set the update value of STATUSFLAG.
     * @param sts :the update value of STATUSFLAG
     </en>*/
    public void updateStatusFalg(int sts)
    {
        setUpdValue(STATUSFLAG, sts);
    }

    /**<jp>
     * ACCESSNGFLAGの更新値をセットします。
     * @param ACCESSNGFLAGの更新値
     </jp>*/
    /**<en>
     * Set the update value of ACCESSNGFLAG.
     * @param acc :the update value of ACCESSNGFLAG
     </en>*/
    public void updateAccessNgFlag(boolean acc)
    {
        if (acc)
        {
/*  2003/12/11  MODIRY  okamura START  */
            setUpdValue(ACCESSNGFLAG, Shelf.ACCESS_NG) ;
        }
        else
        {
            setUpdValue(ACCESSNGFLAG, Shelf.ACCESS_OK) ;
/*  2003/12/11  MODIFY  okamura END  */
        }
    }

    /**<jp>
     * HARDZONEの更新値をセットします。
     * @param HARDZONEの更新値
     </jp>*/
    /**<en>
     * Set the update value of HARDZONE.
     * @param hzone :the update value of HARDZONE
     </en>*/
    public void updateHardZone(int hzone)
    {
        setUpdValue(HARDZONEID, hzone);
    }
    
    /**<jp>
     * SOFTZONEIDの更新値をセットします。
     * @param szone SOFTZONEIDの更新値
     </jp>*/
    /**<en>
     * Set the update value of SOFTZONE.
     * @param szone :the update value of SOFTZONE
     </en>*/
    public void updateSoftZone(int szone)
    {
        setUpdValue(SOFTZONEID, szone);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

