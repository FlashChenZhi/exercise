// $Id: RackToRackMoveShelfSelector.java 7950 2010-05-24 09:09:54Z ota $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.WareHouse;

/**<jp>
 * 棚間移動用の棚決定処理を行います。
 * <code>RackToRackMoveSelector</code>を継承しており、基本的な動作は<code>DepthShelfSelector</code>
 * と同じです。
 * 指定されたゾーン、空棚検索方向をもとに棚間移動のための空棚を検索します。
 * <code>RackToRackMoveSelector</code>は棚間移動の検索ゾーンを引数のPalletの荷高で検索を行うのに対し、
 * このクラスの棚間移動の検索ゾーンは引数のゾーンで検索を行ないます。Palletの荷高は使用しません。
 * 棚間移動で棚のハードゾーンと同一ゾーンに移動する時に用います。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/04/18</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7950 $, $Date: 2010-05-24 18:09:54 +0900 (月, 24 5 2010) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class handles the determination of location for location to location move.
 * Basically its behaviour is the same as <code>DepthShelfSelector</code>.
 * It searches the empty locations for the location to location move, according to the
 * specified zone and search direction of empty locations.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/04/18</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7950 $, $Date: 2010-05-24 18:09:54 +0900 (月, 24 5 2010) $
 * @author  $Author: ota $
 </en>*/
public class RackToRackMoveShelfSelector
        extends RackToRackMoveSelector
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

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
        return ("$Revision: 7950 $,$Date: 2010-05-24 18:09:54 +0900 (月, 24 5 2010) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * データベース接続用のConnectionインスタンスを引数としてインスタンスを生成します。
     * ランザクション制御は内部で行っていませんので、外部でCommitする必要があります。
     * @param conn  データベース接続用の<code>Connection</code>
     * @param zs    ゾーン検索用のZoneSelector
     </jp>*/
    /**<en>
     * Generates instances, using the Connection instance for database as parameter.
     * As transaction control is not internally conducted, it is necessary to commit transaction control externally.
     * @param conn  :<code>Connection</code> to connect with database
     * @param zs    :ZoneSelector for zone search
     </en>*/
    public RackToRackMoveShelfSelector(Connection conn, ZoneSelector zs)
    {
        super(conn, zs);
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * 指定されたパレットの現在位置、倉庫から棚間移動用の棚を検索し、返します。
     * @param plt        棚間移動の空棚検索対象パレット
     * @param wh         棚間移動の空棚検索対象倉庫インスタンス
     * @param aisleNo    棚間移動元棚のアイルNo
     * @param hardZoneId 棚間移動元棚のハードゾーンID
     * @param softZoneId 棚間移動元棚のソフトゾーンID
     * @param alternativeLoc 二重格納、荷姿不一致など代替棚検索時はtrue、新規検索時はfalse
     * @return 検索した棚間移動棚
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws ReadWriteException ルート定義ファイルの読込みに失敗した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Searches location from the specified warehouse and current position of pallet, then return.
     * @param plt        :pallet subject to empty search
     * @param wh         :instance of warehouse subject to empty search
     * @param aisleNo    :aisle no
     * @param hardZoneId : hard Zone Id
     * @param softZoneId : soft Zone Id
     * @param alternativeLoc 二重格納、荷姿不一致など代替棚検索時はtrue、新規検索時はfalse
     * @return :location to search
     * @throws ReadWriteException :Notifies if it occured in database processing.
     * @throws ReadWriteException :Notifies if loading of route definition file failed.
     * @throws InvalidDefineException :Notifies if there are inconsistencies in table data.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    public Shelf select(Pallet plt, WareHouse wh, String aisleNo, String hardZoneId, String softZoneId)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        // 2009/09/26 Y.Osawa UPD ST
        return select(plt, wh, aisleNo, hardZoneId, softZoneId, false);
        // 2009/09/26 Y.Osawa UPD ED
    }

    // 2009/09/26 Y.Osawa ADD ST
    /**<jp>
     * 指定されたパレットの現在位置、倉庫から棚間移動用の棚を検索し、返します。
     * @param plt        棚間移動の空棚検索対象パレット
     * @param wh         棚間移動の空棚検索対象倉庫インスタンス
     * @param aisleNo    棚間移動元棚のアイルNo
     * @param hardZoneId 棚間移動元棚のハードゾーンID
     * @param softZoneId 棚間移動元棚のソフトゾーンID
     * @param alternativeLoc 二重格納、荷姿不一致など代替棚検索時はtrue、新規検索時はfalse
     * @return 検索した棚間移動棚
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws ReadWriteException ルート定義ファイルの読込みに失敗した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Searches location from the specified warehouse and current position of pallet, then return.
     * @param plt        :pallet subject to empty search
     * @param wh         :instance of warehouse subject to empty search
     * @param aisleNo    :aisle no
     * @param hardZoneId : hard Zone Id
     * @param softZoneId : soft Zone Id
     * @param alternativeLoc 二重格納、荷姿不一致など代替棚検索時はtrue、新規検索時はfalse
     * @return :location to search
     * @throws ReadWriteException :Notifies if it occured in database processing.
     * @throws ReadWriteException :Notifies if loading of route definition file failed.
     * @throws InvalidDefineException :Notifies if there are inconsistencies in table data.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    public Shelf select(Pallet plt, WareHouse wh, String aisleNo, String hardZoneId, String softZoneId,
            boolean alternativeLoc)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        // 2010/05/11 Y.Osawa UPD ST
//      try
//      {
          Shelf tShelf;
          WareHouse tWH = (WareHouse)wh;

          // パレットおよび倉庫よりゾーン情報を取得
          Zone[] zone = _zoneSelector.selectZone(hardZoneId, softZoneId, tWH);

          // ゾーン検索で見つからなかった場合は棚なしで返す。
          if (zone == null)
          {
              return null;
          }

          // 検索する方は配列でゾーンがくると仮定しているため入庫可能全ゾーンを引数に渡して検索する
          tShelf = findShelf(tWH, zone, plt, aisleNo, hardZoneId, softZoneId, alternativeLoc);
          if (tShelf != null)
          {
              // 棚が見つかった場合、Shelfインスタンスを返す。
              return (tShelf);
          }
          else
          {
//              // 空棚なし時、通常検索を行う
//              return super.findShelf(wh, zone, plt, true, true, alternativeLoc);
              // 手前棚に棚間移動は行わない
              return null;
          }
//      }
//      catch (NotFoundException e)
//      {
//          throw new ReadWriteException();
//      }
      // 2010/05/11 Y.Osawa UPD ED
    }
    // 2009/09/26 Y.Osawa ADD ED

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

