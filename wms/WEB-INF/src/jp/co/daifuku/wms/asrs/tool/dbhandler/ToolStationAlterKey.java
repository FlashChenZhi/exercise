// $Id: ToolStationAlterKey.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.dbhandler;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Date;

/**<jp>
 * Stationテーブルの更新を行うための情報を定義したクラスです。
 * StationHandlerでテーブル更新を行う場合に使用されます。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * Defined in this class is the informtion to update the definitionStation table.
 * It will be used when updating tables by StationHandler.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class ToolStationAlterKey
        extends ToolSQLAlterKey
{
    // Class fields --------------------------------------------------

    //<jp> 検索条件および更新対象となる可能性のあるカラムを定義します。</jp>
    //<en> Define here the columns which could be search conditions or the target data of update. </en>
    private static final String STATIONNO = "TEMP_DMSTATION.STATION_NO";

    private static final String STATUS = "TEMP_DMSTATION.STATUS";

    private static final String CARRYKEY = "TEMP_DMSTATION.CARRY_KEY";

    private static final String LOADSIZE = "TEMP_DMSTATION.LOAD_SIZE";

    private static final String BCRDATA = "TEMP_DMSTATION.BCR_DATA";

    private static final String CONTROLINFO = "TEMP_DMSTATION.CONTROLINFO";

    private static final String LASTUSEDSTATIONNO = "TEMP_DMSTATION.LAST_USED_STATION_NO";

    private static final String SUSPEND = "TEMP_DMSTATION.SUSPEND";

    private static final String CURRENTMODE = "TEMP_DMSTATION.CURRENT_MODE";

    private static final String MODERQUEST = "TEMP_DMSTATION.MODE_REQUEST";

    private static final String MODERQUESTDATE = "TEMP_DMSTATION.MODE_REQUEST_DATE";

    private static final String INVENTORYCHECKFLAG = "TEMP_DMSTATION.INVENTORY_CHECK_FLAG";

    private static final String MODETYPE = "TEMP_DMSTATION.MODE_TYPE";

    private static final String PARENTSTATIONNO = "TEMP_DMSTATION.PARENT_STATION_NO";

    private static final String AISLESTATIONNO = "TEMP_DMSTATION.AISLE_STATION_NO";

    private static final String STATIONTYPE = "TEMP_DMSTATION.STATION_TYPE";

    private static final String STATIONNAME = "TEMP_DMSTATION.STATION_NAME";

    private static final String MAXINSTRUCTION = "TEMP_DMSTATION.MAX_INSTRUCTION";

    private static final String MAXPALLETQTY = "TEMP_DMSTATION.MAX_PALLET_QTY";

    private static final String CONTROLLERNO = "TEMP_DMSTATION.CONTROLLER_NO";

    private static final String CLASSNAME = "TEMP_DMSTATION.CLASS_NAME";

    private static final String RESTORINGINSTRUCTION = "TEMP_DMSTATION.RESTORING_INSTRUCTION";

    private static final String REMOVE = "TEMP_DMSTATION.REMOVE";

    // Class variables -----------------------------------------------

    //<jp> 宣言されたカラム名を定義した変数を配列にセットします。</jp>
    //<en> Set the vaeiables, defined with the declared column name, in the array.</en>
    private static final String[] Columns = {
            STATIONNO,
            STATUS,
            CARRYKEY,
            LOADSIZE,
            BCRDATA,
            CONTROLINFO,
            LASTUSEDSTATIONNO,
            SUSPEND,
            CURRENTMODE,
            MODERQUEST,
            MODERQUESTDATE,
            INVENTORYCHECKFLAG,
            MODETYPE,
            PARENTSTATIONNO,
            AISLESTATIONNO,
            STATIONTYPE,
            STATIONNAME,
            MAXINSTRUCTION,
            MAXPALLETQTY,
            CONTROLLERNO,
            CLASSNAME,
            RESTORINGINSTRUCTION,
            REMOVE
    };

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Version and the dat
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 4122 $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 宣言されたテーブルカラムの初期設定を行います。
     </jp>*/
    /**<en>
     * Conduct the initial setting of the table column declared.
     </en>*/
    public ToolStationAlterKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------
    //<jp>======================<更新条件設定メソッド>====================</jp>
    //<en>=============<Method of update condition settings>=============</en>

    /**<jp>
     * STATIONNOの検索値をセットします。
     * @param 検索ステーションNo
     </jp>*/
    /**<en>
     * Set the search value of STATIONNO.
     * @param :station no. to be searched
     </en>*/
    public void setStationNo(String stnum)
    {
        setValue(STATIONNO, stnum);
    }

    /**<jp>
     * STATIONNOをの検索値を取得します。
     * @return 検索ステーションNo
     </jp>*/
    /**<en>
     * Retrieve the STATIONNO.
     * @return :station no. to be searched
     </en>*/
    public String getStationNo()
    {
        return (String)getValue(STATIONNO);
    }

    /**<jp>
     * PARENTSTATIONNOの検索値をセットします。
     * @param 検索親ステーションNo
     </jp>*/
    /**<en>
     * Set the search value of PARENTSTATIONNO.
     * @param :parent station no. to be searched
     </en>*/
    public void setParentStationNo(String stnum)
    {
        setValue(PARENTSTATIONNO, stnum);
    }

    /**<jp>
     * PARENTSTATIONNOの検索値を取得します。
     * @return 検索親ステーションNo
     </jp>*/
    /**<en>
     * Retrieve the search value of PARENTSTATIONNO.
     * @return :parent station no. to be searched
     </en>*/
    public String getParentStationNo()
    {
        return (String)getValue(PARENTSTATIONNO);
    }

    /**<jp>
     * AISLESTATIONNOの検索値をセットします。
     * @param 検索アイルステーションNo
     </jp>*/
    /**<en>
     * Set the search value of AISLESTATIONNO.
     * @param :aisle station no. to be searched
     </en>*/
    public void setAisleStationNo(String al)
    {
        setValue(AISLESTATIONNO, al);
    }

    /**<jp>
     * AISLESTATIONNOの検索値を取得します。
     * @return 検索アイルステーションNo
     </jp>*/
    /**<en>
     * Retrieve the search value of AISLESTATIONNO.
     * @return :aisle station no. to be searched
     </en>*/
    public String getAisleStationNo()
    {
        return (String)getValue(AISLESTATIONNO);
    }

    /**<jp>
     * STATIONNAMEの検索値をセットします。
     * @param 検索ステーション名称
     </jp>*/
    /**<en>
     * Set the search value of STATIONNAME.
     * @param :station name to be searched
     </en>*/
    public void setStationName(String sn)
    {
        setValue(STATIONNAME, sn);
    }

    /**<jp>
     * STATIONNAMEの検索値を取得します。
     * @return 検索ステーション名称
     </jp>*/
    /**<en>
     * Retrieve the search value of STATIONNAME.
     * @return :station name to be searched
     </en>*/
    public String getStationName()
    {
        return (String)getValue(STATIONNAME);
    }

    /**<jp>
     * STATUSの更新値をセットします。
     * @param STATUSの検索値
     </jp>*/
    /**<en>
     * Set the update value of STATUS.
     * @param :update value of STATUS
     </en>*/
    public void updateStatus(int num)
    {
        setUpdValue(STATUS, num);
    }

    /**<jp>
     * CARRYKEYの更新値をセットします。
     * @param CARRYKEYの検索値
     </jp>*/
    /**<en>
     * Set the update value of CARRYKEY.
     * @param :update value of CARRYKEY
     </en>*/
    public void updateCarryKey(String ckey)
    {
        setUpdValue(CARRYKEY, ckey);
    }

    /**<jp>
     * LOADSIZEの更新値をセットします。
     * @param LOADSIZEの検索値
     </jp>*/
    /**<en>
     * Set the update value of LOADSIZE.
     * @param :update value of LOADSIZE
     </en>*/
    public void updateLoadSize(int num)
    {
        setUpdValue(LOADSIZE, num);
    }

    /**<jp>
     * BCRDATAの更新値をセットします。
     * @param BCRDATAの検索値
     </jp>*/
    /**<en>
     * Set the update value of BCRDATA.
     * @param :update value of BCRDATA
     </en>*/
    public void updateBCData(String bcd)
    {
        setUpdValue(BCRDATA, bcd);
    }

    /**<jp>
     * CONTROLINFOの更新値をセットします。
     * @param CONTROLINFOの検索値
     </jp>*/
    /**<en>
     * Set the update value of CONTROLINFO.
     * @param :update value of CONTROLINFO
     </en>*/
    public void updateControlInfo(String ctrlinfo)
    {
        setUpdValue(CONTROLINFO, ctrlinfo);
    }

    /**<jp>
     * LASTUSEDSTATIONNOの更新値をセットします。
     * @param LASTUSEDSTATIONNOの検索値
     </jp>*/
    /**<en>
     * Set the update value of LASTUSEDSTATIONNO.
     * @param :update value of LASTUSEDSTATIONNO
     </en>*/
    public void updateLastUsedStationNo(String lst)
    {
        setUpdValue(LASTUSEDSTATIONNO, lst);
    }

    /**<jp>
     * SUSPENDの更新値をセットします。
     * @param SUSPENDの更新値
     </jp>*/
    /**<en>
     * Set the update value of SUSPEND.
     * @param :update value of SUSPEND
     </en>*/
    public void updateSuspend(int sus)
    {
        setUpdValue(SUSPEND, sus);
    }

    /**<jp>
     * INVENTORYCHECKFLAGの更新値をセットします。
     * @param INVENTORYCHECKFLAGの更新値
     </jp>*/
    /**<en>
     * Set the update value of INVENTORYCHECKFLAG.
     * @param :update value of INVENTORYCHECKFLAG
     </en>*/
    public void updateInventoryCheck(int flg)
    {
        setUpdValue(INVENTORYCHECKFLAG, flg);
    }

    /**<jp>
     * CURRENTMODEの更新値をセットします。
     * @param CURRENTMODEの検索値
     </jp>*/
    /**<en>
     * Set the update value of CURRENTMODE.
     * @param :update value of CURRENTMODE
     </en>*/
    public void updateCurrentMode(int mode)
    {
        setUpdValue(CURRENTMODE, mode);
    }

    /**<jp>
     * MODERQUESTの更新値をセットします。
     * @param MODERQUESTの検索値
     </jp>*/
    /**<en>
     * Set the update value of MODERQUEST.
     * @param :update value of MODERQUEST
     </en>*/
    public void updateModeChangeRequest(int req)
    {
        setUpdValue(MODERQUEST, req);
    }

    /**<jp>
     * MODERQUESTDATEの更新値をセットします。
     * @param MODERQUESTDATEの検索値
     </jp>*/
    /**<en>
     * Set the update value of MODERQUESTDATE.
     * @param :update value of MODERQUESTDATE
     </en>*/
    public void updateModeChangeRequestTime(Date dt)
    {
        setUpdValue(MODERQUESTDATE, dt);
    }

    /**<jp>
     * MODETYPEの更新値をセットします。
     * @param MODETYPEの更新値
     </jp>*/
    /**<en>
     * Set the update value of MODETYPE.
     * @param :update value of MODETYPE
     </en>*/
    public void updateModeType(int mtype)
    {
        setUpdValue(MODETYPE, mtype);
    }

    /**<jp>
     * PARENTSTATIONNOの更新値をセットします。
     * @param PARENTSTATIONNOの更新値
     </jp>*/
    /**<en>
     * Set the update value of PARENTSTATIONNO.
     * @param :update value of PARENTSTATIONNO
     </en>*/
    public void updateParentStationNo(String psn)
    {
        setUpdValue(PARENTSTATIONNO, psn);
    }

    /**<jp>
     * AISLESTATIONNOの更新値をセットします。
     * @param AISLESTATIONNOの更新値
     </jp>*/
    /**<en>
     * Set the update value of AISLESTATIONNO.
     * @param :update value of AISLESTATIONNO
     </en>*/
    public void updateAisleStationNo(String asn)
    {
        setUpdValue(AISLESTATIONNO, asn);
    }

    /**<jp>
     * STATIONTYPEの更新値をセットします。
     * @param STATIONTYPEの検索値
     </jp>*/
    /**<en>
     * Set the update value of STATIONTYPE.
     * @param :update value of STATIONTYPE
     </en>*/
    public void updateStationType(int stt)
    {
        setUpdValue(STATIONTYPE, stt);
    }

    /**<jp>
     * STATIONNAMEの更新値をセットします。
     * @param STATIONNAMEの検索値
     </jp>*/
    /**<en>
     * Set the update value of STATIONNAME.
     * @param :update value of STATIONNAME
     </en>*/
    public void updateStationName(String sn)
    {
        setUpdValue(STATIONNAME, sn);
    }

    /**<jp>
     * MAXINSTRUCTIONの更新値をセットします。
     * @param MAXINSTRUCTIONの検索値
     </jp>*/
    /**<en>
     * Set the update value of MAXINSTRUCTION.
     * @param :update value of MAXINSTRUCTION
     </en>*/
    public void updateMaxInstruction(int mi)
    {
        setUpdValue(MAXINSTRUCTION, mi);
    }

    /**<jp>
     * MAXPALLETQTYの更新値をセットします。
     * @param MAXPALLETQTYの検索値
     </jp>*/
    /**<en>
     * Set the update value of MAXPALLETQTY.
     * @param :update value of MAXPALLETQTY
     </en>*/
    public void updateMaxPalletQuantity(int mp)
    {
        setUpdValue(MAXPALLETQTY, mp);
    }

    /**<jp>
     * CONTROLLERNOの更新値をセットします。
     * @param CONTROLLERNOの検索値
     </jp>*/
    /**<en>
     * Set the update value of CONTROLLERNO.
     * @param :update value of CONTROLLERNO
     </en>*/
    public void updateControllerNo(int crn)
    {
        setUpdValue(CONTROLLERNO, crn);
    }

    /**<jp>
     * CLASSNAMEの更新値をセットします。
     * @param CLASSNAMEの検索値
     </jp>*/
    /**<en>
     * Set the update value of CLASSNAME.
     * @param :update value of CLASSNAME
     </en>*/
    public void updateClassName(String cln)
    {
        setUpdValue(CLASSNAME, cln);
    }

    /**<jp>
     * RESTORINGINSTRUCTIONの更新値をセットします。
     * @param resi RESTORINGINSTRUCTIONの検索値
     </jp>*/
    /**<en>
     * Set the update value of RESTORINGINSTRUCTION.
     * @param resi :update value of RESTORINGINSTRUCTION
     </en>*/
    public void updateReStoringInstruction(int resi)
    {
        setUpdValue(RESTORINGINSTRUCTION, resi);
    }

    /**<jp>
     * REMOVEの更新値をセットします。
     * @param rem REMOVEの検索値
     </jp>*/
    /**<en>
     * Set the update value of REMOVE.
     * @param rem :update value of REMOVE
     </en>*/
    public void updateRemove(int rem)
    {
        setUpdValue(REMOVE, rem);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------

}
//end of class

