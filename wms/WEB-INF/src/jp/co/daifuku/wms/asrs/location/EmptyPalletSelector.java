// $Id: EmptyPalletSelector.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.WareHouse;

/**<jp>
 * 空パレットを検索します。
 * クローズ運用時の空パレット検索に使用します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class conducts search for empty pallets.
 * This is used in closed operataion when searching for empty pallets.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class EmptyPalletSelector
{
    // Class fields --------------------------------------------------
    /**<jp>
     * データベース接続用のコネクション
     </jp>*/
    /**<en>
     * Connection with database
     </en>*/
    private Connection _conn;

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
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * データベース接続用のConnectionインスタンスを引数としてインスタンスを生成します。
     * ランザクション制御は内部で行っていませんので、外部でCommitする必要があります。
     * @param conn  データベース接続用の<code>Connection</code>
     </jp>*/
    /**<en>
     * Generates instances, using the Connection instance for database as parameter.
     * As transaction control is not internally conducted, it is necessary to commit transaction control externally.
     * @param conn  :<code>Connection</code> to connect with database
     </en>*/
    public EmptyPalletSelector(Connection conn)
    {
        _conn = conn;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 指定された入庫ステーションから空棚検索可能なアイルの一覧を取得し、そのアイルから空棚を取得します。
     * 空棚が見つからない場合、nullを返します。
     * @param wh         空パレット棚検索対象倉庫インスタンス
     * @param targetZone 空パレット棚検索対象ゾーンの配列
     * @param st         入庫ステーション
     * @return 検索棚
     * @throws ReadWriteException データベースへのアクセスで異常が発生した場合に通知されます。
     * @throws ReadWriteException ルート定義ファイルの読込みに失敗した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieves a list of aisle searchable for empty locations from the specified storing station.
     * Gets the empty location from those aisles.
     * If no empty location was found, it returns null.
     * @param wh         :warehouse instance subject to the empty pallet search
     * @param targetZone :zone array subject to the empty pallet search
     * @param st         :storing station
     * @return :searched location
     * @throws ReadWriteException :Notifies if error occured when accessing database.
     * @throws ReadWriteException :Notifies if it failed loading the route definition file.
     * @throws InvalidDefineException :Notifies if there are any data inconsistency in table.
     </en>*/
    public Shelf findShelf(WareHouse wh, Zone[] targetZone, Station st)
            throws ReadWriteException,
                InvalidDefineException
    {
        try
        {
            //<jp> 入庫ステーションより空棚検索可能なアイルの一覧を取得</jp>
            //<en> Retrieves from the storing station a list of aisle searchable for empty location.</en>
            AisleSelector asel = new AisleSelector(getConnection(), wh, st);

            //<jp> アイルの優先順に空棚検索を行う</jp>
            //<en> Searchese the empty location according to the prioritized aisles.</en>
            while (asel.next())
            {
                Shelf tShelf = asel.findPallet(targetZone);

                if (tShelf != null)
                {
                    //<jp> 棚確定、今回検索したアイルステーションNoを次回空棚検索では検索順の最後にする。</jp>
                    //<en> The aisle station no. used and/or fixed in latest search should be put at the bottom of search list in </en>
                    //<en> next search.</en>
                    asel.determin();
                    return (tShelf);
                }
            }

            //<jp> 空棚無しの場合はnullを返す</jp>
            //<en> It returns null if there is no empty locations.</en>
            return (null);
        }
        catch (NotFoundException e)
        {
            throw new ReadWriteException();
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**
     * コネクションオブジェクトを返します。
     * 
     * @return コネクションオブジェクト
     */
    protected Connection getConnection()
    {
        return _conn;
    }

    // Private methods -----------------------------------------------

}
//end of class


