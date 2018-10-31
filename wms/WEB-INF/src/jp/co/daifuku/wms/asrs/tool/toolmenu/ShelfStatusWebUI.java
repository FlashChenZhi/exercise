// $Id: ShelfStatusWebUI.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.toolmenu;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Shelf;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;

/**<jp>
 * メンテナンスの棚状態を表示するためのユーティリティクラスです。
 * 棚状態特有の検索条件を指定した検索メソッドを実装します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/05/21</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This is a utility class which is used when indicating the location status in maintenance.
 * It implements the search method which is specified with specific search conditions for 
 * location status.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/05/21</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class ShelfStatusWebUI
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

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
        return ("$Revision: 4122 $,$Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $");
    }

    /**<jp>
     * 棚をPresenceをキーに検索します。<BR>
     * 空き棚検索にはPresenceにShelf.PRESENCE_EMPTYをセットして下さい。<BR>
     * 実棚検索にはPresenceにShelf.PRESENCE_STORAGEDをセットして下さい。<BR>
     * 予約棚検索にはPresenceにShelf.PRESENCE_RESERVATIONをセットして下さい。<BR>
     * 全棚の場合はPresenceに99をセットして下さい。
     * @param     conn     <code>Connection</code>
     * @param     whstnum  倉庫StationNo
     * @param     wh       <code>WareHouse</code>インスタンス
     * @param     Presence 棚状態
     * @return Shelf[]   棚の検索結果
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     * @throws NotFoundException 検索した結果、情報が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Search locations by using Presence as a key.<BR>
     * For empty location search, please set Shelf.PRESENCE_EMPTY to Presence.<BR>
     * For loaded location search, please set Shelf.PRESENCE_STORAGED for Presence.<BR>
     * For reserved location search, please set Shelf.PRESENCE_RESERVATION to Presence.<BR>
     * For all location search, please set 99 to Presence.
     * @param     conn     <code>Connection</code>
     * @param     whstnum  warehouse StationNo
     * @param     wh       <code>WareHouse</code> instance
     * @param     presence location status
     * @return Shelf[]   result of location search
     * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database.
     </en>*/
    public static Shelf[] findShelf(Connection conn, String whstnum, Warehouse wh, int presence)
            throws ReadWriteException
    {
        return findShelf(conn, whstnum, wh, presence, -1);
    }

    /**<jp>
     * 指定したバンクをPresenceを条件に検索します。<BR>
     * 空き棚検索にはPresenceにShelf.PRESENCE_EMPTYをセットして下さい。<BR>
     * 実棚検索にはPresenceにShelf.PRESENCE_STORAGEDをセットして下さい。<BR>
     * 予約棚検索にはPresenceにShelf.PRESENCE_RESERVATIONをセットして下さい。<BR>
     * 全棚の場合はPresenceに99をセットして下さい。
     * @param     conn     <code>Connection</code>
     * @param     whstnum  倉庫StationNo
     * @param     wh       <code>Warehouse</code>インスタンス
     * @param     Presence 棚状態
     * @param     nbank    指定バンク
     * @return ShelfStatusView[]   棚の検索結果
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     * @throws NotFoundException 検索した結果、情報が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Search the specified bank with Presence as condition.<BR>
     * For empty location search, please set Shelf.PRESENCE_EMPTY to Presence.<BR>
     * For loaded location search, please set Shelf.PRESENCE_STORAGED for Presence.<BR>
     * For reserved location search, please set Shelf.PRESENCE_RESERVATION to Presence.<BR>
     * For all location search, please set 99 to Presence.
     * @param     conn     <code>Connection</code>
     * @param     whstnum  warehouse StationNo
     * @param     wh       <code>Warehouse</code> instance
     * @param     Presence location status
     * @param     nbank    specified bank
     * @return ShelfStatusView[]   :result of location search
     * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database.
     * @throws NotFoundException  :Notifies if data cannot be found as a result of search.
     </en>*/
    public static Shelf[] findShelf(Connection conn, String whstnum, Warehouse wh, int presence, int nbank)
            throws ReadWriteException
    {
        //<jp> "SELECT * FROM SHELF WHERE PRESENCE = presence ORDER BY NBANK ASC, NLEVEL DESC, NBAY ASC"</jp>
        //<jp> ORDER BY はnBank、nBayを昇順、nLevelを降順にする（空き棚イメージを表示する時使用するため）</jp>
        //<jp> 例 イメージ図 Bank1 (1行目にLevelのMaxを表示)</jp>
        //<en> "SELECT * FROM SHELF WHERE PRESENCE = presence ORDER BY NBANK ASC, NLEVEL DESC, NBAY ASC"</en>
        //<en> For ORDER BY, arrange the nBank and nBay in ascending order and nLevel in descending order. </en>
        //<en> (to be used when displaying the image of empty locations)</en>
        //<en> Example: image of Bank1 (display the Mad Lavel in header line.)</en>
        //     1-4 2-4 3-4 4-4
        //     1-3 2-3 3-3 4-3
        //     1-2 2-2 3-2 4-2
        //     1-1 2-1 3-1 4-1
        ToolShelfHandler shHandle = new ToolShelfHandler(conn);
        ToolShelfSearchKey shkey = new ToolShelfSearchKey();
        shkey.setWarehouseStationNo(whstnum);

        //<jp> Presenceセット</jp>
        //<en> Set the Presence.</en>
        if (presence != 99)
        {
            shkey.setStatusFlag(presence);
        }

        //<jp> Bankセット</jp>
        //<en> Set the Bank.</en>
        if (nbank != -1)
        {
            shkey.setBankNo(nbank);
        }

        //<jp> NBANKのソート順セット</jp>
        //<en> Set the sort order of NBANK.</en>
        shkey.setBankNoOrder(1, true);
        //<jp> NLEVELのソート順セット</jp>
        //<en> Set the sort order of NLEVEL.</en>
        shkey.setLevelNoOrder(2, false);
        //<jp> NBAYのソート順セット</jp>
        //<en> Set the sort order of NBAY.</en>
        shkey.setBayNoOrder(3, true);

        Shelf[] sharray = (Shelf[])shHandle.findFreeAllocationMinShelf(shkey);
        return sharray;
    }

    /**<jp>
     * 実際のBankがBank配列の中の何番目にあるかを返します。
     * プルダウンではvalueとして実際のBankを返しますが、棚状態
     * の表示ではBank配列の何番目かを必要とするためこのメソッドを
     * 作成しました。見つからない場合は"0"を返します。
     * @param orgNo 実際のBank
     * @param bankArray BANKの配列
     * @return Bank配列の中の順番
     </jp>*/
    /**<en>
     * Return where the actual Bank is in Bank array.
     * This method has been created since in display of location status it requires where in Bank array 
     * the value is given while with Pull-down menu, it returns actual Bank as a value.
     * It returns "0" if the valud cannot be found.
     * @param orgNo :actual Bank
     * @param bankArray bank array
     * @return      :position in Bank array
     </en>*/
    public static String getIndexOfBankArray(String orgNo, int[] bankArray)
    {
        for (int i = 0; i < bankArray.length; i++)
        {
            if (orgNo.equals(Integer.toString(bankArray[i])))
            {
                return Integer.toString(i);
            }
        }
        return "0";
    }
    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
