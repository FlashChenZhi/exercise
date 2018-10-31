// $Id: ToolAs21MachineSearchKey.java 7257 2010-02-26 05:59:12Z kanda $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * マシン表を検索するためのキーをセットするクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7257 $, $Date: 2010-02-26 14:59:12 +0900 (金, 26 2 2010) $
 * @author  $Author: kanda $
 </jp>*/
/**<en>
 * This class sets the keys when seaerching the machine table.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7257 $, $Date: 2010-02-26 14:59:12 +0900 (金, 26 2 2010) $
 * @author  $Author: kanda $
 </en>*/
public class ToolAs21MachineSearchKey extends ToolSQLSearchKey
{
    // Class fields --------------------------------------------------

    //<jp> ここに検索条件又はソートされる可能性のあるカラムを定義します。</jp>
    //<en> Define here the column which may be used as a search condition or the which may be sorted. </en>
    private static final String STATIONNO            = "STATION_NO";
    private static final String MACHINETYPE        = "MACHINE_TYPE";
    private static final String MACHINENO            = "MACHINE_NO";
    private static final String STATUSFLAG        = "STATUS_FLAG";
    private static final String CONTROLLERNO     = "CONTROLLER_NO";
    // DFKLOOK 20100222追加
    private static final String DEVICENAME  = "DEVICE_NAME";

    // Class variables -----------------------------------------------
    private static final String[] Columns =
    {
        STATIONNO,
        MACHINETYPE,
        MACHINENO,
        STATUSFLAG,
        CONTROLLERNO,
        // DFKLOOK 20100222追加
        DEVICENAME
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
        return ("$Revision: 7257 $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルトコンストラクタ
     </jp>*/
    /**<en>
     * Default constructor
     </en>*/
    public ToolAs21MachineSearchKey()
    {
        setColumns(Columns);
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * STATIONNOの検索値をセット
     * @param stno ステーションNo
     </jp>*/
    /**<en>
     * Set the search value of STATIONNO.
     * @param stno station number
     </en>*/
    public void setStationNo(String stno)
    {
        setValue(STATIONNO, stno);
    }

    /**<jp>
     * STATIONNOを取得
     * @return stno ステーションNo
     </jp>*/
    /**<en>
     * Retrieve the STATIONNO.
     * @return station number
     </en>*/
    public String getStationNo()
    {
        return (String)getValue(STATIONNO);
    }

    /**<jp>
     * STATIONNOのソート順セット
     * @param num ステーションNo
     * @param bool 昇順／降順
     </jp>*/
    /**<en>
     * Set the sort order of STATIONNO.
     * @param num station No
     * @param bool 
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

    /**<jp>
     * MACHINETYPEの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of MACHINETYPE.
     </en>*/
    public void setMachineType(int type)
    {
        setValue(MACHINETYPE, type);
    }

    /**<jp>
     * MACHINETYPEの検索値を取得
     </jp>*/
    /**<en>
     * Retrieve the search value of MACHINETYPE.
     </en>*/
    public int getMachineType()
    {
        Integer intobj = (Integer)getValue(MACHINETYPE);
        return (intobj.intValue());
    }

    /**<jp>
     * MACHINETYPEのソート順セット
     </jp>*/
    /**<en>
     * Set the search value of MACHINETYPE.
     </en>*/
    public void setMachineTypeOrder(int num, boolean bool)
    {
        setOrder(MACHINETYPE, num, bool);
    }

    /**<jp>
     * MACHINETYPEのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of MACHINETYPE.
     </en>*/
    public int getMachineTypeOrder()
    {
        return (getOrder(MACHINETYPE));
    }

    /**<jp>
     * MACHINENOの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of MACHINENO.
     </en>*/
    public void setMachineNo(int num)
    {
        setValue(MACHINENO, num);
    }

    /**<jp>
     * MACHINENOの検索値を取得
     </jp>*/
    /**<en>
     * Retrieve the search value of MACHINENO.
     </en>*/
    public int getMachineNo()
    {
        Integer intobj = (Integer)getValue(MACHINENO);
        return (intobj.intValue());
    }

    /**<jp>
     * MACHINENOのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of MACHINENO.
     </en>*/
    public void setMachineNoOrder(int num, boolean bool)
    {
        setOrder(MACHINENO, num, bool);
    }

    /**<jp>
     * MACHINENOのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of MACHINENO.
     </en>*/
    public int getMachineNoOrder()
    {
        return (getOrder(MACHINENO));
    }

    /**<jp>
     * STATUSFLAGの検索値をセット
     </jp>*/
    /**<en>
     * Set the search value of STATUSFLAG.
     </en>*/
    public void setStatusFlag(int num)
    {
        setValue(STATUSFLAG, num);
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
    
    // DFKLOOK 20100222追加　ここから
    /**<jp>
     * DEVICENAMEの検索値をセット
     * @param devName 機器名称
     </jp>*/
    /**<en>
     * Set the search value of Device Name.
     * @param devName
     </en>*/
    public void setDeviceName(String devName)
    {
        setValue(DEVICENAME, devName);
    }

    /**<jp>
     * DEVICENAMEを取得
     * @return devName 機器名称
     </jp>*/
    /**<en>
     * Retrieve the DEVICENAME.
     * @return device name
     </en>*/
    public String getDeviceName()
    {
        return (String)getValue(DEVICENAME);
    }
    // DFKLOOK ここまで

    /**<jp>
     * CONTROLLERNOのソート順セット
     </jp>*/
    /**<en>
     * Set the sort order of CONTROLLERNO.
     </en>*/
    public void setControllerNoOrder(int num, boolean bool)
    {
        setOrder(CONTROLLERNO, num, bool);
    }

    /**<jp>
     * CONTROLLERNOのソート順を取得
     </jp>*/
    /**<en>
     * Retrieve the sort order of CONTROLLERNO.
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

