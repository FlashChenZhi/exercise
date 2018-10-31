// $Id: WarehouseParameter.java 5301 2009-10-28 05:36:02Z ota $
package jp.co.daifuku.wms.asrs.tool.schedule;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.Parameter;

/**<jp>
 * 倉庫設定で使用されるエンティティクラスです。
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
 * This entity class is used in warehouse settings.
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
public class WarehouseParameter
        extends Parameter
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * 製番フォルダのパス
     </jp>*/
    /**<en>
     * Path of product number folder
     </en>*/
    String wFilePath = "";

    /**<jp> 格納区分 </jp>*/
    /**<en> Storage type </en>*/
    protected int wWarehouseNumber = 0;

    /**<jp> ステーションNo. </jp>*/
    /**<en> Station no. </en>*/
    protected String wStationNo = "";

    /**<jp> 倉庫名称 </jp>*/
    /**<en> Warehouse name</en>*/
    protected String wWarehouseName = "";

    /**<jp> ゾーン種別 </jp>*/
    /**<en> Zone type </en>*/
    protected int wZoneType = 0;

    /**<jp> 最大混載数 </jp>*/
    /**<en> Max. mix-load quantity </en>*/
    protected int wMaxMixedQuantity = 0;

    /**<jp> 自動倉庫運用種別 </jp>*/
    /**<en> Operation type of automated warehouse </en>*/
    protected int wEploymentType = 0;

    /**<jp> フリーアロケーション運用 </jp>*/
    /**<en> Operation type of free allocation </en>*/
    protected int wFreeAllocation = 0;

    /**<jp> 空棚検索優先区分 </jp>*/
    protected int wLocationSearchType = 0;

    /**<jp> アイル検索優先区分 </jp>*/
    protected int wAisleSearchType = 0;
    
    /**<jp> エリアNo </jp>*/
    protected String wAreaNo = "";

    /**<jp> 空棚検索方法 </jp>*/
    protected String wVacantSearchType = "";

    /**<jp> 仮置在庫作成区分 </jp>*/
    protected String wTemporaryAreaType = "";

    /**<jp> 移動先仮置エリア </jp>*/
    protected String wTemporaryArea = "";


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
        return ("$Revision: 5301 $,$Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * このコンストラクタを使用します。
     </jp>*/
    /**<en>
     * This constructor will be used.
     </en>*/
    public WarehouseParameter()
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
     * 倉庫名称を取得します。
     * @return wWarehouseName
     </jp>*/
    /**<en>
     * Retrieve the warehouse name.
     * @return wWarehouseName
     </en>*/
    public String getWarehouseName()
    {
        return wWarehouseName;
    }

    /**<jp>
     * 倉庫名称をセットします。
     * @param WarehouseName
     </jp>*/
    /**<en>
     * Set the warehouse name.
     * @param WarehouseName
     </en>*/
    public void setWarehouseName(String arg)
    {
        wWarehouseName = arg;
    }

    /**<jp>
     * ゾーン種別を取得します。
     * @return wZoneType
     </jp>*/
    /**<en>
     * Retrieve the zone type.
     * @return wZoneType
     </en>*/
    public int getZoneType()
    {
        return wZoneType;
    }

    /**<jp>
     * ゾーン種別をセットします。
     * @param ZoneType
     </jp>*/
    /**<en>
     * Set the zone type.
     * @param ZoneType
     </en>*/
    public void setZoneType(int arg)
    {
        wZoneType = arg;
    }

    /**<jp>
     * 最大混載数を取得します。
     * @return wMaxMixedQuantity
     </jp>*/
    /**<en>
     * Retrieve the max. mix-load quantity.
     * @return wMaxMixedQuantity
     </en>*/
    public int getMaxMixedQuantity()
    {
        return wMaxMixedQuantity;
    }

    /**<jp>
     * 最大混載数をセットします。
     * @param 最大混載数
     </jp>*/
    /**<en>
     *Set the max. mix-load quantity.
     * @param max. mix-load quantity
     </en>*/
    public void setMaxMixedQuantity(int arg)
    {
        wMaxMixedQuantity = arg;
    }

    /**<jp>
     * 自動倉庫運用種別を取得します。
     * @return wAutoWarehouseType
     </jp>*/
    /**<en>
     * Retrieve the operation type of automated warehouse.
     * @return wAutoWarehouseType
     </en>*/
    public int getEmploymentType()
    {
        return wEploymentType;
    }

    /**<jp>
     * 自動倉庫運用種別をセットします。
     * @param arg 自動倉庫運用種別
     </jp>*/
    /**<en>
     * Set the operation type of automated warehouse.
     * @param arg operation type of automated warehouse
     </en>*/
    public void setEmploymentType(int arg)
    {
        wEploymentType = arg;
    }

    /**<jp>
     * フリーアロケーション運用を取得します。
     * @return wFreeAllocation
     </jp>*/
    /**<en>
     * Retrieve the operation type of free allocation.
     * @return wFreeAllocation
     </en>*/
    public int getFreeAllocationType()
    {
        return wFreeAllocation;
    }

    /**<jp>
     * フリーアロケーション運用をセットします。
     * @param arg フリーアロケーション運用
     </jp>*/
    /**<en>
     * Set the operation type of free allocation.
     * @param arg operation type of free allocation
     </en>*/
    public void setFreeAllocationType(int arg)
    {
        wFreeAllocation = arg;
    }

    /**<jp>
     * 空棚検索優先区分を取得します。
     * @return wFreeAllocation
     </jp>*/
    public int getLocationSearchType()
    {
        return wLocationSearchType;
    }

    /**<jp>
     * 空棚検索優先区分をセットします。
     * @param arg 空棚検索優先区分
     </jp>*/
    public void setLocationSearchType(int arg)
    {
        wLocationSearchType = arg;
    }
    
    /**<jp>
     * アイル検索優先区分を取得します。
     * @return wFreeAllocation
     </jp>*/
    public int getAisleSearchType()
    {
        return wAisleSearchType;
    }

    /**<jp>
     * アイル検索優先区分をセットします。
     * @param arg アイル検索優先区分
     </jp>*/
    public void setAisleSearchType(int arg)
    {
        wAisleSearchType = arg;
    }
    
    /**<jp>
     * エリアNoを取得します。
     * @return wAreaNo
     </jp>*/
    public String getAreaNo()
    {
        return wAreaNo;
    }

    /**<jp>
     * エリアNoをセットします。
     * @param arg エリアNo
     </jp>*/
    public void setAreaNo(String arg)
    {
        wAreaNo = arg;
    }

    /**<jp>
     * 空棚検索方法を取得します。
     * @return wVacantSearchType
     </jp>*/
    public String getVacantSearchType()
    {
        return wVacantSearchType;
    }

    /**<jp>
     * 空棚検索方法をセットします。
     * @param arg 空棚検索方法
     </jp>*/
    public void setVacantSearchType(String arg)
    {
        wVacantSearchType = arg;
    }

    /**<jp>
     * 仮置在庫作成区分を取得します。
     * @return wTemporaryAreaType
     </jp>*/
    public String getTemporaryAreaType()
    {
        return wTemporaryAreaType;
    }

    /**<jp>
     * 仮置在庫作成区分をセットします。
     * @param arg 仮置在庫作成区分
     </jp>*/
    public void setTemporaryAreaType(String arg)
    {
        wTemporaryAreaType = arg;
    }

    /**<jp>
     * 移動先仮置エリアを取得します。
     * @return wTemporaryArea
     </jp>*/
    public String getTemporaryArea()
    {
        return wTemporaryArea;
    }

    /**<jp>
     * 移動先仮置エリアをセットします。
     * @param arg 移動先仮置エリア
     </jp>*/
    public void setTemporaryArea(String arg)
    {
        wTemporaryArea = arg;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------


}
//end of class
