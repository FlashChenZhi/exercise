// $Id: Warehouse.java 5301 2009-10-28 05:36:02Z ota $
package jp.co.daifuku.wms.asrs.tool.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.location.StationFactory;

/**<jp>
 * 倉庫を管理するためのクラスです。
 * 倉庫には、自動倉庫や平置き倉庫などがあり、基本的には棚の集合になります。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/09</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5301 $, $Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class is used to control the warehouse.
 * There are automated warehouses and floor storage warehouses; this class handles 
 * these warehouses basically as groups of locations.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/09</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5301 $, $Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $
 * @author  $Author: ota $
 </en>*/
public class Warehouse
        extends Station
{
    // Class fields --------------------------------------------------

    /**<jp>
     * 倉庫の種別を表すフィールド（自動倉庫）
     </jp>*/
    /**<en>
     * Field of warehouse type (automated warehouse)
     </en>*/
    public static final int AUTOMATID_WAREHOUSE = 1;

    /**<jp>
     * 倉庫の種別を表すフィールド（平置倉庫）
     </jp>*/
    /**<en>
     * Field of warehouse type (floor storage warehouse)
     </en>*/
    public static final int CONVENTIONAL_WAREHOUSE = 2;

    /**<jp>
     * ゾーン取得種別を表すフィールド（ハードゾーン管理）
     </jp>*/
    /**<en>
     * Field of zone retrieval (hard zone operation)
     </en>*/
    public static final int HARD = 1;

    /**<jp>
     * ゾーン取得種別を表すフィールド（ソフトゾーン管理）
     </jp>*/
    /**<en>
     * Field of zone retrieval (soft zone operation)
     </en>*/
    public static final int SOFT = 2;

    /**<jp>
     * ゾーン取得種別を表すフィールド（ハードゾーン管理（ITEM表から取得））
     </jp>*/
    /**<en>
     * Field of zone retrieval (hard zone operation, retrieved from ITEM table)
     </en>*/
    public static final int HARD_ITEM = 3;

    /**<jp>
     * 自動倉庫運用種別を表すフィールド（オープン）
     </jp>*/
    /**<en>
     * Field of automated warehouse operation (open)
     </en>*/
    public static final int OPEN = 1;

    /**<jp>
     * 自動倉庫運用種別を表すフィールド（クローズ）
     </jp>*/
    /**<en>
     *  Field of automated warehouse operation (close)
     </en>*/
    public static final int CLOSE = 2;

    /**<jp>
     * フリーアロケーション運用を表すフィールド（フリーアロケーション運用なし）
     </jp>*/
    /**<en>
     * Field of automated warehouse operation (open)
     </en>*/
    public static final int FREE_ALLOCATION_OFF = 0;

    /**<jp>
     * フリーアロケーション運用を表すフィールド（フリーアロケーション運用あり）
     </jp>*/
    /**<en>
     *  Field of automated warehouse operation (close)
     </en>*/
    public static final int FREE_ALLOCATION_ON = 1;

    /**<jp>
     * 空棚検索優先区分空棚を表すフィールド（アイル優先）
     </jp>*/
    public static final int AISLE_PRIORITY = 1;

    /**<jp>
     * 空棚検索優先区分空棚を表すフィールド（ゾーン優先）
     </jp>*/
    public static final int ZONE_PRIORITY = 2;
    
    /**<jp>
     * アイル検索優先区分を表すフィールド（昇順）
     </jp>*/
    public static final int ASCENDING = 1;

    /**<jp>
     * アイル検索優先区分を表すフィールド（降順）
     </jp>*/
    public static final int DESCENDING = 2;
    
    // Class variables -----------------------------------------------
    /**<jp>
     * 倉庫番号（格納区分）を保持します。
     </jp>*/
    /**<en>
     * Preserve the warehouse number (storage type).
     </en>*/
    protected int wWarehouseNo;

    /**<jp>
     * 倉庫の種別を保持します。
     </jp>*/
    /**<en>
     * Preserve the warehouse type.
     </en>*/
    protected int wWarehouseType;

    /**<jp>
     * 倉庫の最大混載数を保持します。
     </jp>*/
    /**<en>
     * Preserve the max. mix-load quantity of the warehouse.
     </en>*/
    protected int wMaxMixedPallet;

    /**<jp>
     * 倉庫の名前を保持します。
     </jp>*/
    /**<en>
     * Preserve the warehouse name.
     </en>*/
    protected String wWarehouseName;

    /**<jp>
     * 自動倉庫運用種別を保持します。
     </jp>*/
    /**<en>
     * Preserve the operation type of automated warehouse.
     </en>*/
    protected int wEmploymentType = 0;

    /**<jp>
     * フリーアロケーション運用を保持します。
     </jp>*/
    /**<en>
     * Preserve the operation type of free allocation.
     </en>*/
    protected int wFreeAllocation = 0;

    /**<jp>
     * フリーアロケーション運用を保持します。
     </jp>*/
    /**<en>
     * Preserve the operation type of free allocation.
     </en>*/
    protected int wLocationSearchType = 0;
    
    /**<jp>
     * フリーアロケーション運用を保持します。
     </jp>*/
    /**<en>
     * Preserve the operation type of free allocation.
     </en>*/
    protected int wAisleSearchType = 0;
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
     * 新しい<CODE>Warehouse</CODE>を構築します。
     </jp>*/
    /**<en>
     * Construct new <CODE>Warehouse</CODE>.
     </en>*/
    public Warehouse()
    {
    }

    /**<jp>
     * 新しい<code>Warehouse</code>のインスタンスを作成します。既に定義されているステーションを
     * 持つインスタンスが必要な場合は、<code>StationFactory</code>メソッドを利用してください。
     * @param snum  保持するステーション番号
     * @see StationFactory
     </jp>*/
    /**<en>
     * Construct new isntance of <code>Warehouse</code>. Please use <code>StationFactory</code> 
     * class if the instance which already has the defined station is required.
     * @param snum  :station no. preserved
     * @see StationFactory
     </en>*/
    public Warehouse(String snum)
    {
        super(snum);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 倉庫の倉庫番号（格納区分）を設定します。
     * @param whnum 倉庫番号(ステーション番号ではありません)
     </jp>*/
    /**<en>
     * Set the warehouse no. (storage type) of the warehouse.
     * @param whnum :warehouse no.(not the station no.)
     </en>*/
    public void setWarehouseNo(int whnum)
    {
        wWarehouseNo = whnum;
    }

    /**<jp>
     * 倉庫の倉庫番号（格納区分）を取得します。
     * @return 倉庫番号(ステーション番号ではありません)
     </jp>*/
    /**<en>
     * Retrieve the warehouse number of the warehouse (storage type).
     * @return :warehouse no.(not the station no.)
     </en>*/
    public int getWarehouseNo()
    {
        return wWarehouseNo;
    }

    /**<jp>
     * 倉庫の種別を設定します。
     * @param type 倉庫種別
     * @throws InvalidStatusException typeの内容が範囲外であった場合に通知します。
     </jp>*/
    /**<en>
     * Set the warehouse type.
     * @param type :warehouse type
     * @throws InvalidStatusException :Notifies if contents of type is invalid.
     </en>*/
    public void setWarehouseType(int type)
            throws InvalidStatusException
    {
        //<jp> 倉庫種別のチェック</jp>
        //<en> Check the type of warehouse.</en>
        switch (type)
        {
            //<jp> 正しい倉庫種別の一覧</jp>
            //<en> List of correct type of warehouse</en>
            case AUTOMATID_WAREHOUSE:
            case CONVENTIONAL_WAREHOUSE:
                break;
            //<jp> 正しくない倉庫種別を設定しようとした場合は例外を発生させ、倉庫種別の変更はしない</jp>
            //<en> If incorrect type of warehouse were to set, it lets the exception occur and will not modify the type of warehouse.</en>
            default:
                //<jp> 6126009=定義されていない{0}を設定しようとしました。</jp>
                //<en> 6126009=Undefined {0} is set.</en>
                Object[] tObj = new Object[1];
                tObj[0] = "WAREHOUSETYPE";
                RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, "warehouse", tObj);
                throw (new InvalidStatusException("6126009" + wDelim + tObj[0]));
        }

        //<jp> 倉庫種別をセット</jp>
        //<en> Set the warehouse type.</en>
        wWarehouseType = type;
    }

    /**<jp>
     * 倉庫の種別を取得します。
     * @return 倉庫種別
     </jp>*/
    public int getWarehouseType()
    {
        return wWarehouseType;
    }

    /**<jp>
     * 倉庫の名称を取得します。
     * @return 倉庫の名前
     </jp>*/
    /**<en>
     * Retrieve the warehouse name.
     * @return warehouse name
     </en>*/
    public String getWarehouseName()
    {
        return wWarehouseName;
    }

    /**<jp>
     * 倉庫の倉庫の名称を設定します。
     * @param whnum 倉庫名称
     </jp>*/
    /**<en>
     * Set the warehouse name
     * @param name :warehouse nane
     </en>*/
    public void setWarehouseName(String name)
    {
        wWarehouseName = name;
    }

    /**<jp>
     * 倉庫の最大混載数を設定します。
     * @param maxnum 最大混載数
     </jp>*/
    /**<en>
     * Set the max mix-load quantity of the warehouse.
     * @param maxnum :the max mix-load quantity of the warehouse
     </en>*/
    public void setMaxMixedPallet(int maxnum)
    {
        wMaxMixedPallet = maxnum;
    }

    /**<jp>
     * 倉庫の最大混載数を取得します。
     * @return 最大混載数
     </jp>*/
    /**<en>
     * Retrieve the max mix-load quantity of the warehouse.
     * @return :the max mix-load quantity of the warehouse
     </en>*/
    public int getMaxMixedPallet()
    {
        return wMaxMixedPallet;
    }

    /**<jp>
     * 自動倉庫運用種別を設定します。
     * @param employ 自動倉庫運用種別（1:オープン 2:クローズ）
     * @throws InvalidStatusException employの内容が範囲外であった場合に通知します。
     </jp>*/
    /**<en>
     * Set the operatin type of automated warehouse.
     * @param employ :the operatin type of automated warehouse (1: opern, 2: close )
     * @throws InvalidStatusException :Notifies if contents of employ is invalid.
     </en>*/
    public void setEmploymentType(int employ)
            throws InvalidStatusException
    {
        switch (employ)
        {
            //<jp> 正しい自動倉庫運用種別の場合</jp>
            //<en> If the operatin type of automated warehouse is correct,</en>
            case OPEN:
            case CLOSE:
                break;

            //<jp> 正しくない自動倉庫運用種別を設定しようとした場合は例外を発生。自動倉庫運用種別の変更はしない。</jp>
            //<en> If incorrect operatin type of automated warehouse were to set, </en>
            //<en> it lets the exception occur and will not modify the operatin type of automated warehouse.</en>
            default:
                //<jp> 6126009=定義されていない{0}を設定しようとしました。</jp>
                //<en> 6126009=Undefined {0} is set.</en>
                Object[] tObj = new Object[1];
                tObj[0] = "WAREHOUSE.EMPLOYMENTTYPE";
                RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, this.getClass().getName(), tObj);
                throw (new InvalidStatusException("6126009" + wDelim + tObj[0]));
        }

        //<jp> 自動倉庫運用種別の変更</jp>
        //<en> Modify the operatin type of automated warehouse.</en>
        wEmploymentType = employ;
    }

    /**<jp>
     * 自動倉庫運用種別を取得します。
     * @return 自動倉庫運用種別
     </jp>*/
    /**<en>
     * Retrieve the operation type of the automated warehouse.
     * @return :operation type of the automated warehouse
     </en>*/
    public int getEmploymentType()
    {
        return wEmploymentType;
    }

    /**<jp>
     * フリーアロケーション運用を設定します。
     * @param freeallocation フリーアロケーション運用
     *                       （0:フリーアロケーション運用なし 1:フリーアロケーション運用あり）
     * @throws InvalidStatusException freeallocationの内容が範囲外であった場合に通知します。
     </jp>*/
    /**<en>
     * Set the operatin type of free allocation.
     * @param employ :the operatin type of free allocation 
     *                                      (0: Not free allocation, 1: free allocation )
     * @throws InvalidStatusException :Notifies if contents of freeallocation is invalid.
     </en>*/
    public void setFreeAllocationType(int freeallocation)
            throws InvalidStatusException
    {
        switch (freeallocation)
        {
            //<jp> 正しいフリーアロケーション運用の場合</jp>
            //<en> If the operatin type of automated warehouse is correct,</en>
            case FREE_ALLOCATION_OFF:
            case FREE_ALLOCATION_ON:
                break;

            //<jp> 正しくないフリーアロケーション運用を設定しようとした場合は例外を発生。フリーアロケーション運用の変更はしない。</jp>
            //<en> If incorrect operatin type of free allocation were to set, </en>
            //<en> it lets the exception occur and will not modify the operatin type of free allocation.</en>
            default:
                //<jp> 6126009=定義されていない{0}を設定しようとしました。</jp>
                //<en> 6126009=Undefined {0} is set.</en>
                Object[] tObj = new Object[1];
                tObj[0] = "WAREHOUSE.FREEALLOCATION";
                RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, this.getClass().getName(), tObj);
                throw (new InvalidStatusException("6126009" + wDelim + tObj[0]));
        }

        //<jp> 自動倉庫運用種別の変更</jp>
        //<en> Modify the operatin type of automated warehouse.</en>
        wFreeAllocation = freeallocation;
    }

    /**<jp>
     * 自動倉庫運用種別を取得します。
     * @return 自動倉庫運用種別
     </jp>*/
    /**<en>
     * Retrieve the operation type of the automated warehouse.
     * @return :operation type of the automated warehouse
     </en>*/
    public int getFreeAllocationType()
    {
        return wFreeAllocation;
    }

    /**<jp>
     * 空棚検索優先区分を設定します。
     * @param searchType 空棚検索優先区分（1:アイル優先 2:ゾーン優先）
     * @throws InvalidStatusException searchTypeの内容が範囲外であった場合に通知します。
     </jp>*/
    public void setLocationSearchType(int searchType)
            throws InvalidStatusException
    {
        switch (searchType)
        {
            //<jp> 正しい空棚検索優先区分の場合</jp>
            //<en> If the operatin type of automated warehouse is correct,</en>
            case AISLE_PRIORITY:
            case ZONE_PRIORITY:
                break;

            //<jp> 正しくない空棚検索優先区分を設定しようとした場合は例外を発生。空棚検索優先区分の変更はしない。</jp>
            default:
                //<jp> 6126009=定義されていない{0}を設定しようとしました。</jp>
                //<en> 6126009=Undefined {0} is set.</en>
                Object[] tObj = new Object[1];
                tObj[0] = "WAREHOUSE.EMPLOYMENTTYPE";
                RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, this.getClass().getName(), tObj);
                throw (new InvalidStatusException("6126009" + wDelim + tObj[0]));
        }

        //<jp> 空棚検索優先区分の変更</jp>
        wLocationSearchType = searchType;
    }

    /**<jp>
     * 空棚検索優先区分を取得します。
     * @return 空棚検索優先区分
     </jp>*/
    public int getLocationSearchType()
    {
        return wLocationSearchType;
    }
    
    /**<jp>
     * アイル検索優先区分を設定します。
     * @param searchType アイル検索優先区分（ 1：昇順　2：降順）
     * @throws InvalidStatusException searchTypeの内容が範囲外であった場合に通知します。
     </jp>*/
    public void setAisleSearchType(int searchType)
            throws InvalidStatusException
    {
        switch (searchType)
        {
            //<jp> 正しいアイル検索優先区分の場合</jp>
            case ASCENDING:
            case DESCENDING:
                break;

            //<jp> 正しくないアイル検索優先区分を設定しようとした場合は例外を発生。アイル検索優先区分の変更はしない。</jp>
            default:
                //<jp> 6126009=定義されていない{0}を設定しようとしました。</jp>
                Object[] tObj = new Object[1];
                tObj[0] = "WAREHOUSE.EMPLOYMENTTYPE";
                RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, this.getClass().getName(), tObj);
                throw (new InvalidStatusException("6126009" + wDelim + tObj[0]));
        }

        //<jp> アイル検索優先区分の変更</jp>
        wAisleSearchType = searchType;
    }

    /**<jp>
     * アイル検索優先区分を取得します。
     * @return アイル検索優先区分
     </jp>*/
    public int getAisleSearchType()
    {
        return wAisleSearchType;
    }
    
    /**<jp>
     * 文字列表現を返します。
     * @return    文字列表現
     </jp>*/
    /**<en>
     * Return the string representation.
     * @return    string representation
     </en>*/
    public String toString()
    {
        StringBuffer buf = new StringBuffer(100);
        try
        {
            buf.append("\nStation Number:" + wStationNo);
            buf.append("\nWarehouse Number:" + wWarehouseNo);
            buf.append("\nWarehouseType:" + wWarehouseType);
            buf.append("\nMaxMixedPallet:" + wMaxMixedPallet);
            buf.append("\nParent Station:" + wParentStationNo);
            buf.append("\nName:" + wWarehouseName);
            buf.append("\nLastUsedStation:" + wLastUsedStationNo);
            buf.append("\nEmploymentType:" + wEmploymentType);
        }
        catch (Exception e)
        {
        }

        return (buf.toString());
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

