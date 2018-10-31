// $Id: ShelfSelector.java 5977 2009-11-17 09:02:10Z ota $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.WareHouse;

/**<jp>
 * 棚検索を行うためのインターフェースです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5977 $, $Date: 2009-11-17 18:02:10 +0900 (火, 17 11 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This is an interface for the location search.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5977 $, $Date: 2009-11-17 18:02:10 +0900 (火, 17 11 2009) $
 * @author  $Author: ota $
 </en>*/
public interface ShelfSelector
{
    // Class fields --------------------------------------------------
    // Class variables -----------------------------------------------
    // Public methods ------------------------------------------------
    /**<jp>
     * 指定された倉庫および搬送元の情報を持つパレットより、空棚を検索します。
     * @param plt 空棚検索対象パレット
     * @param wh  空棚検索対象倉庫
     * @return 検索された空棚
     * @throws ReadWriteException データI/O処理で発生した場合に通知されます。
     * @throws InvalidDefineException 定義情報に不整合があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Searches the empty location based on the pallet which preserves information of 
     * specified warehouse and sending station.
     * @param plt :pallet subject to empty location search
     * @param wh  :warehouse subject to empty location search
     * @return    :empty location searched
     * @throws ReadWriteException :Notifies if exception occured in data I/O process.
     * @throws InvalidDefineException :Notifies if there are any inconsistency in definition.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    public Shelf select(Pallet plt, WareHouse wh)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException;

    /**<jp>
     * 指定されたパレットの現在位置、倉庫から棚を検索し、返します。
     * @param plt        空棚検索対象パレット
     * @param wh         空棚検索対象倉庫インスタンス
     * @param aisleNo    アイルNo
     * @param hardZoneId ハードゾーンID
     * @param softZoneId ソフトゾーンID
     * @return 検索棚
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws ReadWriteException ルート定義ファイルの読込みに失敗した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    public Shelf select(Pallet plt, WareHouse wh, String aisleNo, String hardZoneId, String softZoneId)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException;

    // 2009/09/26 Y.Osawa ADD ST
    /**<jp>
     * 指定されたパレットの現在位置、倉庫から棚を検索し、返します。
     * @param plt        空棚検索対象パレット
     * @param wh         空棚検索対象倉庫インスタンス
     * @param aisleNo    アイルNo
     * @param hardZoneId ハードゾーンID
     * @param softZoneId ソフトゾーンID
     * @param alternativeLoc 二重格納、荷姿不一致など代替棚検索時はtrue、新規検索時はfalse
     * @return 検索棚
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws ReadWriteException ルート定義ファイルの読込みに失敗した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    public Shelf select(Pallet plt, WareHouse wh, String aisleNo, String hardZoneId, String softZoneId, boolean alternativeLoc)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException;

    /**<jp>
     * 指定された倉庫および搬送元の情報を持つパレットより、空棚を検索します。
     * @param plt 空棚検索対象パレット
     * @param wh  空棚検索対象倉庫
     * @param empLocDeterm  空棚を決定し更新処理を行なう場合はtrue、チェックのみの場合はfalse
     * @param alternativeLoc 二重格納、荷姿不一致など代替棚検索時はtrue、新規検索時はfalse
     * @return 検索された空棚
     * @throws ReadWriteException データI/O処理で発生した場合に通知されます。
     * @throws InvalidDefineException 定義情報に不整合があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    public Shelf select(Pallet plt, WareHouse wh, boolean empLocDeterm, boolean alternativeLoc)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException;

    // 2009/09/26 Y.Osawa ADD ED

    /**<jp>
     * 指定された倉庫および搬送元の情報を持つパレットより、空棚を検索します。
     * @param plt 空棚検索対象パレット
     * @param wh  空棚検索対象倉庫
     * @param empLocDeterm  空棚を決定し更新処理を行なう場合はtrue、チェックのみの場合はfalse
     * @return 検索された空棚
     * @throws ReadWriteException データI/O処理で発生した場合に通知されます。
     * @throws InvalidDefineException 定義情報に不整合があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Searches the empty location based on the pallet which preserves information of 
     * specified warehouse and sending station.
     * @param plt :pallet subject to empty location search
     * @param wh  :warehouse subject to empty location search
     * @param empLocDeterm     :true if a empty location has been selected as destination and renewing the shelf information, or false
     *                     if only the check will be done.
     * @return    :empty location searched
     * @throws ReadWriteException :Notifies if exception occured in data I/O process.
     * @throws InvalidDefineException :Notifies if there are any inconsistency in definition.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    public Shelf select(Pallet plt, WareHouse wh, boolean empLocDeterm)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException;

    /**<jp>
     * ゾーン検索の為に利用する、<code>ZoneSelector</code>インスタンスを設定します。
     * @param zs 棚検索時に利用する<code>ZoneSelector</code>
     </jp>*/
    /**<en>
     * Sets <code>ZoneSelector</code> instance for use in zone search.
     * @param zs :<code>ZoneSelector</code> used in location search
     </en>*/
    public void setZoneSelector(ZoneSelector zs);

    /**<jp>
     * ゾーン検索の為に利用する、<code>ZoneSelector</code>インスタンスを取得します。
     * @return 設定されている<code>ZoneSelector</code>
     </jp>*/
    /**<en>
     * Retrieves <code>ZoneSelector</code> instance for use in zone search.
     * @return :<code>ZoneSelector</code> set
     </en>*/
    public ZoneSelector getZoneSelector();

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of interface

