// $Id: AisleParameter.java 5301 2009-10-28 05:36:02Z ota $
package jp.co.daifuku.wms.asrs.tool.schedule ;
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.Parameter;

/**<jp>
 * アイル設定で使用されるエンティティクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/20</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5301 $, $Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This is an entity class which will be used in aisle setting.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/20</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5301 $, $Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $
 * @author  $Author: ota $
 </en>*/
public class AisleParameter extends Parameter
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
    /**<en> Storage type </en>*/
    protected int wWarehouseNumber = 0;
    
    /**<jp> アイルステーションNo. </jp>*/
    /**<en> Aisle station no. </en>*/
    protected String wAisleStationNo = "";

    /**<jp> アイルNo. </jp>*/
    /**<en> Aisle no. </en>*/
    protected int wAisleNumber = 0;

    /**<jp> AGC No. </jp>*/
    /**<en> AGC No. </en>*/
    protected int wAGCNumber = 0;

    /**<jp> 開始バンク </jp>*/
    /**<en> Starting bank </en>*/
    protected int wSBank = 0;

    /**<jp> 終了バンク </jp>*/
    /**<en> Ending bank </en>*/
    protected int wEBank = 0;

    /**<jp> 開始ベイ </jp>*/
    /**<en> Starting bay </en>*/
    protected int wSBay = 0;

    /**<jp> 終了ベイ </jp>*/
    /**<en> Ending bay </en>*/
    protected int wEBay = 0;

    /**<jp> 開始レベル </jp>*/
    /**<en> Starting level </en>*/
    protected int wSLevel = 0;

    /**<jp> 終了レベル </jp>*/
    /**<en> Ending level </en>*/
    protected int wELevel = 0;

    /** 開始アイル位置 */
    protected int wSAislePosition = 0;

    /** 終了アイル位置 */
    protected int wEAislePosition = 0;
    
    /** 最大搬送可能数 */
    protected int wMaxCarry = 0;
    
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
        return ("$Revision: 5301 $,$Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * このコンストラクタを使用します。
     * @param conn <CODE>Connection</CODE>
     </jp>*/
    /**<en>
     * Utilize this constructor.
     * @param conn <CODE>Connection</CODE>
     </en>*/
    public AisleParameter()   
    {
    }

    // Public methods ------------------------------------------------
    /**<jp>
    * 製番フォルダのパスをセットします。
    </jp>*/
    /**<en>
    * Set the path of product number folder.
    </en>*/
    public void setFilePath(String filepath)
    {
        wFilePath = filepath;
    }
    
    /**<jp>
    * 製番フォルダのパスを返します。
    </jp>*/
    /**<en>
    * Return the path of product number folder.
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
     * アイルステーションNoを取得します。
     * @return wAisleStationNo
     </jp>*/
    /**<en>
     * Retrieve the aisle station no.
     * @return wAisleStationNo
     </en>*/
    public String getAisleStationNo()
    {
        return wAisleStationNo;
    }
    /**<jp>
     * アイルステーションNoをセットします。
     * @param AisleStationNo
     </jp>*/
    /**<en>
     * Set the aisle station no.
     * @param AisleStationNo
     </en>*/
    public void setAisleStationNo(String arg)
    {
        wAisleStationNo = arg;
    }



    /**<jp>
     * アイルNoを取得します。
     * @return wAisleNumber
     </jp>*/
    /**<en>
     * Retrieve the aisle no.
     * @return wAisleNumber
     </en>*/
    public int getAisleNumber()
    {
        return wAisleNumber;
    }
    /**<jp>
     * アイルNoをセットします。
     * @param AisleNumber
     </jp>*/
    /**<en>
     * Set the aisle no.
     * @param AisleNumber
     </en>*/
    public void setAisleNumber(int arg)
    {
        wAisleNumber = arg;
    }
    
    /**<jp>
     * AGC Noを取得します。
     * @return wAGCNumber
     </jp>*/
    /**<en>
     * Retrieve the AGC No.
     * @return wAGCNumber
     </en>*/
    public int getAGCNumber()
    {
        return wAGCNumber;
    }
    /**<jp>
     * AGC Noをセットします。
     * @param AGCNumber
     </jp>*/
    /**<en>
     * Set the AGC No.
     * @param AGCNumber
     </en>*/
    public void setAGCNumber(int arg)
    {
        wAGCNumber = arg;
    }
    /**<jp>
     * 開始バンクを取得します。
     * @return wSBank
     </jp>*/
    /**<en>
     * Retrieve the starting bank.
     * @return wSBank
     </en>*/
    public int getSBank()
    {
        return wSBank;
    }
    /**<jp>
     * 開始バンクをセットします。
     * @param 開始バンク
     </jp>*/
    /**<en>
     * Set the starting bank.
     * @param :starting bank
     </en>*/
    public void setSBank(int arg)
    {
        wSBank = arg;
    }
    /**<jp>
     * 終了バンクを取得します。
     * @return wEBank
     </jp>*/
    /**<en>
     * Retrieve the ending bank.
     * @return wEBank
     </en>*/
    public int getEBank()
    {
        return wEBank;
    }
    /**<jp>
     * 終了バンクをセットします。
     * @param 終了バンク
     </jp>*/
    /**<en>
     * Set the ending bank.
     * @param ending bank
     </en>*/
    public void setEBank(int arg)
    {
        wEBank = arg;
    }
    /**<jp>
     * 開始ベイを取得します。
     * @return wSBay
     </jp>*/
    /**<en>
     * Retrieve the starting bay.
     * @return wSBay
     </en>*/
    public int getSBay()
    {
        return wSBay;
    }
    /**<jp>
     * 開始ベイをセットします。
     * @param 開始ベイ
     </jp>*/
    /**<en>
     * Set the starting bay.
     * @param starting bay
     </en>*/
    public void setSBay(int arg)
    {
        wSBay = arg;
    }

    /**<jp>
     * 終了ベイを取得します。
     * @return wEBay
     </jp>*/
    /**<en>
     * Retrieve the ending bay.
     * @return wEBay
     </en>*/
    public int getEBay()
    {
        return wEBay;
    }
    /**<jp>
     * 終了ベイをセットします。
     * @param 終了ベイ
     </jp>*/
    /**<en>
     * Set the ending bay.
     * @param ending bay
     </en>*/
    public void setEBay(int arg)
    {
        wEBay = arg;
    }
    /**<jp>
     * 開始レベルを取得します。
     * @return wSLevel
     </jp>*/
    /**<en>
     * Retrieve hte starting level.
     * @return wSLevel
     </en>*/
    public int getSLevel()
    {
        return wSLevel;
    }
    /**<jp>
     * 開始レベルをセットします。
     * @param 開始レベル
     </jp>*/
    /**<en>
     * Set the starting level.
     * @param starting level
     </en>*/
    public void setSLevel(int arg)
    {
        wSLevel = arg;
    }
    
    /**<jp>
     * 終了レベルを取得します。
     * @return wELevel
     </jp>*/
    /**<en>
     * Retrieve the ending level.
     * @return wELevel
     </en>*/
    public int getELevel()
    {
        return wELevel;
    }
    /**<jp>
     * 終了レベルをセットします。
     * @param 終了レベル
     </jp>*/
    /**<en>
     * Set the ending level.
     * @param ending level
     </en>*/
    public void setELevel(int arg)
    {
        wELevel = arg;
    }
    
    /**
     * 開始アイル位置を取得します。
     * @return wSAislePosition
     */
    public int getSAislePosition()
    {
        return wSAislePosition;
    }
    /**
     * 開始アイル位置をセットします。
     * @param 開始バンク
     */
    public void setSAislePosition(int arg)
    {
        wSAislePosition = arg;
    }

    /**
     * 終了アイル位置を取得します。
     * @return wEAislePosition
     */
    public int getEAislePosition()
    {
        return wEAislePosition;
    }
    /**
     * 終了アイル位置をセットします。
     * @param 終了バンク
     */
    public void setEAislePosition(int arg)
    {
        wEAislePosition = arg;
    }

    /**
     * 最大搬送可能数を取得します。
     * @return wMaxCarry
     */
    public int getMaxCarry()
    {
        return wMaxCarry;
    }
    /**
     * 最大搬送可能数をセットします。
     * @param 最大搬送可能数
     */
    public void setMaxCarry(int arg)
    {
        wMaxCarry = arg;
    }
    
    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    
}
