// $Id: IndividuallyHardZoneParameter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.schedule ;
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.Parameter;

/**<jp>
 * ハードゾーン設定（個別）で使用されるエンティティクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/28</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This is an entity class which is used in individual hard zone setting.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/28</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class IndividuallyHardZoneParameter extends Parameter
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    /**<jp>
    * 製番フォルダのパス
    </jp>*/
    /**<en>
    * Path of the product number folder
    </en>*/
    String wFilePath = "";
    
    /**<jp> 格納区分 </jp>*/
    /**<en> storage type </en>*/
    protected int wWarehouseNumber = 0;

    /**<jp> ゾーン番号 </jp>*/
    /**<en> zone no. </en>*/
    protected int wZoneID ;

    /**<jp> BANK </jp>*/
    /**<en> BANK </en>*/
    protected int wBank = 0;

    /**<jp> BAY </jp>*/
    /**<en> BAY </en>*/
    protected int wBay = 0;

    /**<jp> LEVEL </jp>*/
    /**<en> LEVEL </en>*/
    protected int wLevel = 0;

    // Class method --------------------------------------------------
     /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
     /**<en>
     * Return the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
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
    public IndividuallyHardZoneParameter()   
    {
    }

    // Public methods ------------------------------------------------
    /**<jp>
    * 製番フォルダのパスをセットします。
    </jp>*/
    /**<en>
    * Set the path of the product number folder.
    </en>*/
    public void setFilePath(String filepath)
    {
        wFilePath = filepath;
    }
    
    /**<jp>
    * 製番フォルダのパスを返します。
    </jp>*/
    /**<en>
    * Return the path of the product number folder.
    </en>*/
    public String getFilePath()
    {
        return wFilePath;
    }
    
    /**<jp>
     * 格納区分を取得します。
     * @return wWarehouseNumber
     </jp>*/
    /**<en>
     * Retrieve the storage type.
     * @return wWarehouseNumber
     </en>*/
    public int getWarehouseNumber()
    {
        return wWarehouseNumber;
    }
    /**<jp>
     * 格納区分をセットします。
     * @param WarehouseNumber
     </jp>*/
    /**<en>
     * Set the storage type.
     * @param WarehouseNumber
     </en>*/
    public void setWarehouseNumber(int arg)
    {
        wWarehouseNumber = arg;
    }

    /**<jp>
     * ゾーン番号を設定します。
     * @param zid   設定するゾーン番号
     </jp>*/
    /**<en>
     * Set teh zone no.
     * @param zid   :zone no. to set
     </en>*/
    public void setZoneID(int zid)
    {
        wZoneID = zid ;
    }

    /**<jp>
     * ゾーン番号を取得します。
     * @return    ゾーン番号
     </jp>*/
    /**<en>
     * Retrieve the zone no.
     * @return    zone no.
     </en>*/
    public int getZoneID()
    {
        return wZoneID ;
    }

    /**<jp>
     * BANKを取得します。
     * @return wBank
     </jp>*/
    /**<en>
     * Retrieve the BANK.
     * @return wBank
     </en>*/
    public int getBank()
    {
        return wBank;
    }
    /**<jp>
     * BANKをセットします。
     * @param Bank
     </jp>*/
    /**<en>
     * Set the BANK.
     * @param Bank
     </en>*/
    public void setBank(int arg)
    {
        wBank = arg;
    }

    /**<jp>
     * BAYを取得します。
     * @return wBay
     </jp>*/
    /**<en>
     * Retrieve the BAY.
     * @return wBay
     </en>*/
    public int getBay()
    {
        return wBay;
    }
    /**<jp>
     * BAYをセットします。
     * @param Bay
     </jp>*/
    /**<en>
     * Set the BAY.
     * @param Bay
     </en>*/
    public void setBay(int arg)
    {
        wBay = arg;
    }
    /**<jp>
     * Levelを取得します。
     * @return wLevel
     </jp>*/
    /**<en>
     * Retrieve the Level.
     * @return wLevel
     </en>*/
    public int getLevel()
    {
        return wLevel;
    }
    /**<jp>
     * Levelをセットします。
     * @param Level
     </jp>*/
    /**<en>
     * Set the Level.
     * @param Level
     </en>*/
    public void setLevel(int arg)
    {
        wLevel = arg;
    }
    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    
}
//end of class
