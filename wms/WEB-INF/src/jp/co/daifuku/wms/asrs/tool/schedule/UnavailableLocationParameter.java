// $Id: UnavailableLocationParameter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.schedule ;
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.Parameter;

/**<jp>
 * 使用不可棚設定で使用されるエンティティクラスです。
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
 * This is an entity class which will be used when setting the unavailable locations.
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
public class UnavailableLocationParameter extends Parameter
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
    * 使用不可棚を保存するテキストのパス
    </jp>*/
    /**<en>
    * Path of the text which saves the unavailable locations
    </en>*/
    String wFileName = "";

    /**<jp> 格納区分 </jp>*/
    /**<en> Storage type </en>*/
    protected int wWarehouseNumber = 0;

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
     * Returns the version of this class.
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
    public UnavailableLocationParameter()   
    {
    }

    // Public methods ------------------------------------------------
    /**<jp>
    * 使用不可棚を保存するテキストのパスをセットします。
    </jp>*/
    /**<en>
    * Set the path of the text which saves the unavailable locations.
    </en>*/
    public void setFileName(String filename)
    {
        wFileName = filename;
    }
    
    /**<jp>
    * 使用不可棚を保存するテキストのパスを返します。
    </jp>*/
    /**<en>
    * Return the path of the text which saves the unavailable locations.
    </en>*/
    public String getFileName()
    {
        return wFileName;
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
     * @param arg WarehouseNumber
     </en>*/
    public void setWarehouseNumber(int arg)
    {
        wWarehouseNumber = arg;
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
     * @param arg Bank
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
     * @param arg Bay
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
     * @param arg Level
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
