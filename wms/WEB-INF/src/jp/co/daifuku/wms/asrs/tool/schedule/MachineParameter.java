// $Id: MachineParameter.java 7258 2010-02-26 05:59:51Z kanda $
package jp.co.daifuku.wms.asrs.tool.schedule ;
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.Parameter;

/**<jp>
 * 機種情報設定で使用されるエンティティクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7258 $, $Date: 2010-02-26 14:59:51 +0900 (金, 26 2 2010) $
 * @author  $Author: kanda $
 </jp>*/
/**<en>
 * This is an entity class which wil be used in the setting of machine information.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7258 $, $Date: 2010-02-26 14:59:51 +0900 (金, 26 2 2010) $
 * @author  $Author: kanda $
 </en>*/
public class MachineParameter extends Parameter
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    
    /** AGCNo */
    protected int wControllerNumber = 0;
    
    /**<jp> 機種コード </jp>*/
    /**<en> Machine code </en>*/
    protected int wMachineType = 0;

    /**<jp> 号機No </jp>*/
    /**<en> Machine no.</en>*/
    protected int wMachineNumber = 0;

    /**<jp> ステーションNo. </jp>*/
    /**<en> Station no. </en>*/
    protected String wStationNo = "";

    /**<jp> ステーション名称 </jp>*/
    /**<en> Station name </en>*/
    protected String wStationName = "";

    /**<jp> 機器名称 </jp>*/
    /**<en> Device name </en>*/
    protected String wDeviceName = "";
    
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
        return ("$Revision: 7258 $,$Date: 2010-02-26 14:59:51 +0900 (金, 26 2 2010) $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * このコンストラクタを使用します。
     * @param conn <CODE>Connection</CODE>
     </jp>*/
    /**<en>
     * This constructor will be used.
     * @param conn <CODE>Connection</CODE>
     </en>*/
    public MachineParameter()   
    {
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * AGCNo.を取得します。
     * @return wControllerNumber
     </jp>*/
    /**<en>
     * Retrieve the AGCNo.
     * @return wControllerNumber
     </en>*/
    public int getControllerNumber()
    {
        return wControllerNumber;
    }
    /**<jp>
     * AGCNo.をセットします。
     * @param AGCNo.
     </jp>*/
    /**<en>
     * Set the AGCNo.
     * @param AGCNo.
     </en>*/
    public void setControllerNumber(int arg)
    {
        wControllerNumber = arg;
    }

    /**<jp>
     * 機種コードを取得します。
     * @return wMachineType
     </jp>*/
    /**<en>
     * Retrieve the machine code.
     * @return wMachineType
     </en>*/
    public int getMachineType()
    {
        return wMachineType;
    }
    /**<jp>
     * 機種コードをセットします。
     * @param 機種コード
     </jp>*/
    /**<en>
     * Set the machine code.
     * @param machine code
     </en>*/
    public void setMachineType(int arg)
    {
        wMachineType = arg;
    }

    /**<jp>
     * 号機No.を取得します。
     * @return wMachineNumber
     </jp>*/
    /**<en>
     * Retrieve the machine no.
     * @return wMachineNumber
     </en>*/
    public int getMachineNumber()
    {
        return wMachineNumber;
    }
    /**<jp>
     * 号機Noをセットします。
     * @param 号機No
     </jp>*/
    /**<en>
     * Set the machine no.
     * @param machine no.
     </en>*/
    public void setMachineNumber(int arg)
    {
        wMachineNumber = arg;
    }

    /**<jp>
     * ステーションNoを取得します。
     * @return wStationNo
     </jp>*/
    /**<en>
     * Retrieve the station no.
     * @return wStationNo
     </en>*/
    public String getStationNo()
    {
        return wStationNo;
    }
    /**<jp>
     * ステーションNoをセットします。
     * @param StationNo
     </jp>*/
    /**<en>
     * Set the station no.
     * @param StationNo
     </en>*/
    public void setStationNo(String arg)
    {
        wStationNo = arg;
    }

    /**<jp>
     * ステーション名称を取得します。
     * @return wStationName
     </jp>*/
    /**<en>
     * Retrieve the station name.
     * @return wStationName
     </en>*/
    public String getStationName()
    {
        return wStationName;
    }
    /**<jp>
     * ステーション名称をセットします。
     * @param StationName
     </jp>*/
    /**<en>
     * Set the station name.
     * @param StationName
     </en>*/
    public void setStationName(String arg)
    {
        wStationName = arg;
    }

    /**<jp>
     * 機器名称を取得します。
     * @return wDeviceName
     </jp>*/
    /**<en>
     * Retrieve the Device name.
     * @return wDeviceName
     </en>*/
    public String getDeviceName()
    {
        return wDeviceName;
    }
    /**<jp>
     * 機器名称をセットします。
     * @param DeviceName
     </jp>*/
    /**<en>
     * Set the device name.
     * @param DeviceName
     </en>*/
    public void setDeviceName(String arg)
    {
        wDeviceName = arg;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    
}
//end of class
