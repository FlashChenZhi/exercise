//$Id: ASShelfHandler.java 4303 2009-05-15 08:22:56Z ota $
package jp.co.daifuku.wms.asrs.dbhandler;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.db.SQLErrors;

/**<jp>
 * <code>Shelf</code>クラスをデータベースから取得したり、データベースに保管する為に利用するクラスです。
 * <code>Shelf</code>クラスの取得に関しては、<code>StationFactory</code>を利用してください。
 * <code>Shelf</code>クラスには<code>getHandler</code>メソッドが用意されていますので、
 * 対応するHandlerが必要な場合は、<code>getHandler</code>メソッドを利用して取得します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4303 $, $Date: 2009-05-15 17:22:56 +0900 (金, 15 5 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class is used to obtain <code>Shelf</code> class from, or to store one in database.
 * Concerning the acquisition of <code>Shelf</code> class, please use <code>StationFactory</code>.
 * As <code>getHandler</code> method is prepared in <code>Shelf</code>class, 
 * please use <code>getHandler</code> method if any support of Handler is necessary.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4303 $, $Date: 2009-05-15 17:22:56 +0900 (金, 15 5 2009) $
 * @author  $Author: ota $
 </en>*/
public class ASShelfHandler
        extends ShelfHandler
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * データベース接続用のConnectionインスタンス。
     * トランザクション管理は、このクラスの中では行わない。
     </jp>*/
    /**<en>
     * Connection instance to connect with database
     * Transaction control is not conducted in this class.
     </en>*/
    private Connection _conn;

    /**<jp>
     * ステートメントを管理する変数。
     </jp>*/
    /**<en>
     * Variable which controls the statement
     </en>*/
    private Statement _statement = null;

    // Constructors --------------------------------------------------
    /**<jp>
     * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
     * @param conn データベース接続用 Connection
     </jp>*/
    /**<en>
     * Generate instance by specifying <code>Connection</code> to connect with database.
     * @param conn :Connection to connect with database
     </en>*/
    public ASShelfHandler(Connection conn)
    {
        super(conn);
        setConnection(conn);
    }

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
        return ("$Revision: 4303 $,$Date: 2009-05-15 17:22:56 +0900 (金, 15 5 2009) $");
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * 空パレット検索を行います。
     * 指定されたアイル、ゾーンをもとにShelfテーブルを検索し、空パレットとなっているShelfインスタンスを一つ返します。
     * 空パレットが見つからない場合、nullを返します。
     * このメソッドはShelfテーブルをトランザクションが完了するまでロックするので、呼び出し元は必ずトランザクションをcommitまたはrollbackしてください。
     * @param tAise 空棚検索対象アイル
     * @param tZone 空棚検索対象ゾーン
     * @return 検索したShelfインスタンス
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws InvalidDefineException ShelfSelecotorインターフェースに定義されていない空棚検索方向が指定された場合に通知されます。
     </jp>*/
    /**<en>
     * Search the empty location.
     * Search in the Shlf table according to the specified aisle and zone, then return one instance of Shelf
     * which is an empty pallet.
     * Or it returns null if empty pallet was not found.
     * Please remember, the caller must either commit of rollback the transaction as this method locks the Shelf table
     * until thd transaction should complete.
     * @param tAisle :aisle subject to empty location search
     * @param tZone :zone subject to empty location search
     * @return :Shelf instance searched
     * @throws ReadWriteException :Notifies of the exception occured during the database processing.
     * @throws InvalidDefineException ShelfSelecotor: Notifies if the selected direction of empty location search is not
     * defined with interface.
     </en>*/
    public Shelf findEmptyPallet(Aisle tAisle, Zone[] tZone)
            throws ReadWriteException,
                InvalidDefineException
    {
        ResultSet rset = null;
        Shelf[] fndStation = null;
        String sqlstring = null;

        try
        {
            String fmtSQL =
                    "SELECT * FROM DMSHELF, DNPALLET WHERE" + " DMSHELF.STATION_NO = DNPALLET.CURRENT_STATION_NO "
                            + " AND DNPALLET.STATUS_FLAG = " + DBFormat.format(SystemDefine.PALLET_STATUS_REGULAR)
                            + " AND DNPALLET.ALLOCATION_FLAG = "
                            + DBFormat.format(SystemDefine.ALLOCATION_FLAG_NOT_ALLOCATED)
                            + " AND DNPALLET.EMPTY_FLAG = " + DBFormat.format(SystemDefine.EMPTY_FLAG_EMPTY)
                            + " AND DMSHELF.PARENT_STATION_NO = {0}" + " AND DMSHELF.HARD_ZONE_ID = {1} "
                            + " AND DMSHELF.SOFT_ZONE_ID = {2} " + " AND DMSHELF.STATUS_FLAG = "
                            + DBFormat.format(SystemDefine.LOCATION_STATUS_FLAG_STORAGED)
                            + " AND DMSHELF.PROHIBITION_FLAG   = " + DBFormat.format(SystemDefine.PROHIBITION_FLAG_OK)
                            + " AND DMSHELF.ACCESS_NG_FLAG = " + DBFormat.format(SystemDefine.ACCESS_NG_FLAG_OK)
                            + " ORDER BY {3} FOR UPDATE";

            Object[] fmtObj = new Object[4];

            fmtObj[0] = DBFormat.format(tAisle.getStationNo());

            for (int i = 0; i < tZone.length; i++)
            {
                fmtObj[1] = DBFormat.format(tZone[i].getHardZone().getHardZoneId());
                fmtObj[2] = DBFormat.format(tZone[i].getSoftZoneID());
                String strDirection = String.valueOf(tZone[i].getDirection());
                if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_HP.equals(strDirection))
                {
                    // レベル方向検索(HP側から):HP手前
                    fmtObj[3] = "BAY_NO, LEVEL_NO, BANK_NO";
                }
                else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_HP.equals(strDirection))
                {
                    // ベイ方向検索(HP側から):HP下段
                    fmtObj[3] = "LEVEL_NO, BAY_NO, BANK_NO";
                }
                else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_OP.equals(strDirection))
                {
                    // レベル方向検索(OP側から):OP手前
                    fmtObj[3] = "BAY_NO DESC, LEVEL_NO, BANK_NO";
                }
                else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_OP.equals(strDirection))
                {
                    // ベイ方向検索(OP側から):OP下段
                    fmtObj[3] = "LEVEL_NO, BAY_NO DESC, BANK_NO";
                }
                else
                {
                    //<jp> 空棚検索方向が定義外の場合、例外を返す。</jp>
                    //<en>undifined , throw exception!</en>
                    Object[] tObj = new Object[3];
                    tObj[0] = this.getClass().getName();
                    tObj[1] = "Direction";
                    tObj[2] = strDirection;
                    String classname = (String)tObj[0];
                    RmiMsgLogClient.write(6016061, LogMessage.F_ERROR, classname, tObj);
                    throw (new InvalidDefineException(WmsMessageFormatter.format(6016061, tObj[0], tObj[1], tObj[2])));
                }

                sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
                //<jp> queryを実行し、結果セットの先頭データよりShelfインスタンスを生成する。</jp>
                //<en> Run the query, then generate the Shle instance from the leading data of result set.</en>
                rset = executeSQL(sqlstring, true);
                //<jp> Shelfインスタンスは一つだけ生成する。</jp>
                //<en> Generates only one Shelf instance.</en>
                fndStation = makeShelf(rset, 1);
                rset.close();
                rset = null;
                closeStatement();

                if (fndStation.length > 0)
                {
                    return fndStation[0];
                }
            }
        }
        catch (NotFoundException e)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en> This will not happen, however in case it did;</en>
            e.printStackTrace();
            throw new ReadWriteException();
        }
        catch (DataExistsException e)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en> This will not happen, however in case it did;</en>
            e.printStackTrace();
            throw new ReadWriteException();
        }
        catch (SQLException e)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en> This will not happen, however in case it did;</en>
            e.printStackTrace();
            throw new ReadWriteException();
        }
        finally
        {
            try
            {
                if (rset != null)
                {
                    rset.close();
                    rset = null;
                }

                closeStatement();
            }
            catch (SQLException e)
            {
                //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
                //<en>6126001 = Database error occured. {0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw new ReadWriteException(e);
            }
        }

        return null;
    }

    /**<jp>
     * 各アイルステーション・ソフトゾーン・ハードゾーン毎に空棚数（入庫可能数）を取得する。
     * @param parentStationNo アイルステーションNo
     * @param listSPossible 空棚数アイル一覧データ
     * @return ArrayList 入庫可能数格納テーブル
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws ReadWriteException ルート定義ファイルの読込みに失敗した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    public ArrayList getVacantCount(String parentStationNo, ArrayList listSPossible)
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException,
                LockTimeOutException
    {
        ResultSet rset = null;
        String sqlstring = null;

        try
        {
            String fmtSQL =
                    "SELECT A.PARENT_STATION_NO PARENT_STATION_NO, A.SOFT_ZONE_ID SOFT_ZONE_ID, A.HARD_ZONE_ID HARD_ZONE_ID, "
                            + " DECODE (B.COUNT, NULL, 0, B.COUNT) VACANT_COUNT " + " FROM "
                            + "    (SELECT PARENT_STATION_NO, SOFT_ZONE_ID, HARD_ZONE_ID " + "FROM DMSHELF "
                            + "     WHERE PARENT_STATION_NO = " + DBFormat.format(parentStationNo)
                            + "     GROUP BY PARENT_STATION_NO, SOFT_ZONE_ID, HARD_ZONE_ID " + ") A, "
                            + "    (SELECT SOFT_ZONE_ID, HARD_ZONE_ID, COUNT(*) COUNT " + " FROM DMSHELF "
                            + "     WHERE PARENT_STATION_NO = " + DBFormat.format(parentStationNo)
                            + " AND STATUS_FLAG = " + DBFormat.format(SystemDefine.LOCATION_STATUS_FLAG_EMPTY)
                            + " AND PROHIBITION_FLAG = " + DBFormat.format(SystemDefine.PROHIBITION_FLAG_OK)
                            + " AND ACCESS_NG_FLAG = " + DBFormat.format(SystemDefine.ACCESS_NG_FLAG_OK)
                            + " AND LOCATION_USE_FLAG = " + DBFormat.format(SystemDefine.LOCATION_USE_OK)  
                            + " GROUP BY SOFT_ZONE_ID, HARD_ZONE_ID " + ") B "
                            + " WHERE A.SOFT_ZONE_ID = B.SOFT_ZONE_ID(+) AND A.HARD_ZONE_ID = B.HARD_ZONE_ID(+) "
                            + " ORDER BY PARENT_STATION_NO, SOFT_ZONE_ID, HARD_ZONE_ID ";

            Object[] fmtObj = new Object[1];
            sqlstring = SimpleFormat.format(fmtSQL, fmtObj);

            //queryを実行し、結果セットの先頭データより結果表のインスタンスを生成する。
            rset = executeSQL(sqlstring, true);

            while (rset.next())
            {
                int simpQty = rset.getInt("VACANT_COUNT");

                listSPossible.add("{" + rset.getString("PARENT_STATION_NO") + "}" + "," + "["
                        + rset.getString("SOFT_ZONE_ID") + "]" + "," + "<" + rset.getString("HARD_ZONE_ID") + ">" + ","
                        + "(" + simpQty + ")");
            }
        }
        catch (SQLException e)
        {
            //Findなので、起こらないはず
            e.printStackTrace();
            throw new ReadWriteException();
        }
        catch (NotFoundException e)
        {
            //Findなので、起こらないはず
            e.printStackTrace();
            throw new ReadWriteException();
        }
        catch (DataExistsException e)
        {
            //Findなので、起こらないはず
            e.printStackTrace();
            throw new ReadWriteException();
        }
        finally
        {
            try
            {
                if (rset != null)
                {
                    rset.close();
                    rset = null;
                }

                closeStatement();
            }
            catch (SQLException e)
            {
                //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
                //<en>6126001 = Database error occured. {0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }

        //<jp> 取得した空棚数の情報を格納します。</jp>
        return listSPossible;
    }

    /**<jp>
     * 各アイルステーション・ソフトゾーン・ハードゾーン毎に空棚数（入庫可能数）を取得する。
     * 荷幅フリーか指定された荷幅以上で検索。
     * @param parentStationNo アイルステーションNo
     * @param listSPossible 空棚数アイル一覧データ
     * @return ArrayList 入庫可能数格納テーブル
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws ReadWriteException ルート定義ファイルの読込みに失敗した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    public ArrayList getVacantCount(String parentStationNo, int width, ArrayList listSPossible)
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException,
                LockTimeOutException
    {
        ResultSet rset = null;
        String sqlstring = null;

        try
        {
            String fmtSQL =
                    "SELECT A.PARENT_STATION_NO PARENT_STATION_NO, A.SOFT_ZONE_ID SOFT_ZONE_ID, A.HARD_ZONE_ID HARD_ZONE_ID, "
                            + " DECODE (B.COUNT, NULL, 0, B.COUNT) VACANT_COUNT " + " FROM "
                            + "    (SELECT PARENT_STATION_NO, SOFT_ZONE_ID, HARD_ZONE_ID " + "FROM DMSHELF "
                            + "     WHERE PARENT_STATION_NO = " + DBFormat.format(parentStationNo)
                            + "     GROUP BY PARENT_STATION_NO, SOFT_ZONE_ID, HARD_ZONE_ID " + ") A, "
                            + "    (SELECT SOFT_ZONE_ID, HARD_ZONE_ID, SUM(COUNT) COUNT " + " FROM "
                            + "        (SELECT MAX(SOFT_ZONE_ID) SOFT_ZONE_ID, MAX(HARD_ZONE_ID) HARD_ZONE_ID, "
                            + "        DECODE(DMSHELF.WIDTH, 0, MAX(DMWIDTH.MAX_STORAGE), COUNT(*)) COUNT "
                            + " FROM DMSHELF, DMWIDTH " + "        WHERE PARENT_STATION_NO = "
                            + DBFormat.format(parentStationNo) + "        AND STATUS_FLAG = "
                            + DBFormat.format(SystemDefine.LOCATION_STATUS_FLAG_EMPTY)
                            + "        AND PROHIBITION_FLAG = " + DBFormat.format(SystemDefine.PROHIBITION_FLAG_OK)
                            + "        AND ACCESS_NG_FLAG = " + DBFormat.format(SystemDefine.ACCESS_NG_FLAG_OK)
                            + "        AND LOCATION_USE_FLAG = " + DBFormat.format(SystemDefine.LOCATION_USE_OK)
                            + "        {0} " + "        AND DMSHELF.WH_STATION_NO = DMWIDTH.WH_STATION_NO "
                            + "        AND DMSHELF.BANK_NO >= DMWIDTH.START_BANK_NO "
                            + "        AND DMSHELF.BANK_NO <= DMWIDTH.END_BANK_NO "
                            + "        AND DMSHELF.BAY_NO >= DMWIDTH.START_BAY_NO "
                            + "        AND DMSHELF.BAY_NO <= DMWIDTH.END_BAY_NO "
                            + "        AND DMSHELF.LEVEL_NO >= DMWIDTH.START_LEVEL_NO "
                            + "        AND DMSHELF.LEVEL_NO <= DMWIDTH.END_LEVEL_NO "
                            + "        GROUP BY BANK_NO, BAY_NO, LEVEL_NO, DMSHELF.WIDTH) "
                            + "    GROUP BY SOFT_ZONE_ID, HARD_ZONE_ID " + ") B "
                            + " WHERE A.SOFT_ZONE_ID = B.SOFT_ZONE_ID(+) AND A.HARD_ZONE_ID = B.HARD_ZONE_ID(+) "
                            + " ORDER BY PARENT_STATION_NO, SOFT_ZONE_ID, HARD_ZONE_ID ";

            Object[] fmtObj = new Object[1];

            if (width != 0)
            {
                fmtObj[0] =
                        "        AND ((DMSHELF.WIDTH = " + SystemDefine.WIDTH_FREE + " AND DMWIDTH.WIDTH = " + width
                        + ") " + "        OR  (DMSHELF.WIDTH >= " + width
                        + " AND DMSHELF.WIDTH = DMWIDTH.WIDTH)) ";
            }

            sqlstring = SimpleFormat.format(fmtSQL, fmtObj);

            //            SELECT
            //            A.PARENT_STATION_NO PARENT_STATION_NO
            //            ,A.SOFT_ZONE_ID SOFT_ZONE_ID
            //            ,A.HARD_ZONE_ID HARD_ZONE_ID
            //            ,DECODE(B. COUNT
            //                ,NULL, 0
            //                ,B. COUNT
            //            ) VACANT_COUNT
            //        FROM
            //            (
            //                SELECT
            //                        PARENT_STATION_NO
            //                        ,SOFT_ZONE_ID
            //                        ,HARD_ZONE_ID
            //                    FROM
            //                        DMSHELF
            //                    WHERE
            //                        PARENT_STATION_NO = '9101'
            //                    GROUP BY
            //                        PARENT_STATION_NO
            //                        ,SOFT_ZONE_ID
            //                        ,HARD_ZONE_ID
            //            ) A
            //            ,(
            //                SELECT
            //                        SOFT_ZONE_ID
            //                        ,HARD_ZONE_ID
            //                        ,SUM(COUNT) COUNT
            //                    FROM
            //                        (
            //                            SELECT
            //                                    MAX(SOFT_ZONE_ID) SOFT_ZONE_ID
            //                                    ,MAX(HARD_ZONE_ID) HARD_ZONE_ID
            //                                    ,DECODE(DMSHELF.WIDTH
            //                                        ,0, MAX(DMWIDTH.MAX_STORAGE)
            //                                        ,COUNT(*)
            //                                    ) COUNT
            //                                FROM
            //                                    DMSHELF
            //                                    ,DMWIDTH
            //                                WHERE
            //                                    PARENT_STATION_NO = '9101'
            //                                    AND STATUS_FLAG = '0'
            //                                    AND PROHIBITION_FLAG = '0'
            //                                    AND ACCESS_NG_FLAG = '0'
            //                                    AND LOCATION_USE_FLAG = '0'
            //                                    AND (
            //                                        (
            //                                            DMSHELF.WIDTH = 0
            //                                            AND DMWIDTH.WIDTH = 5
            //                                        )
            //                                        OR (
            //                                            DMSHELF.WIDTH >= 5
            //                                            AND DMSHELF.WIDTH = DMWIDTH.WIDTH
            //                                        )
            //                                    )
            //                                    AND DMSHELF.WH_STATION_NO = DMWIDTH.WH_STATION_NO
            //                                    AND DMSHELF.BANK_NO >= DMWIDTH.START_BANK_NO
            //                                    AND DMSHELF.BANK_NO <= DMWIDTH.END_BANK_NO
            //                                    AND DMSHELF.BAY_NO >= DMWIDTH.START_BAY_NO
            //                                    AND DMSHELF.BAY_NO <= DMWIDTH.END_BAY_NO
            //                                    AND DMSHELF.LEVEL_NO >= DMWIDTH.START_LEVEL_NO
            //                                    AND DMSHELF.LEVEL_NO <= DMWIDTH.END_LEVEL_NO
            //                                GROUP BY
            //                                    BANK_NO
            //                                    ,BAY_NO
            //                                    ,LEVEL_NO
            //                                    ,DMSHELF.WIDTH
            //                        )
            //                    GROUP BY
            //                        SOFT_ZONE_ID
            //                        ,HARD_ZONE_ID
            //            ) B
            //        WHERE
            //            A.SOFT_ZONE_ID = B.SOFT_ZONE_ID(+)
            //            AND A.HARD_ZONE_ID = B.HARD_ZONE_ID(+)
            //        ORDER BY
            //            PARENT_STATION_NO
            //            ,SOFT_ZONE_ID
            //            ,HARD_ZONE_ID

            //queryを実行し、結果セットの先頭データより結果表のインスタンスを生成する。
            rset = executeSQL(sqlstring, true);

            while (rset.next())
            {
                int simpQty = rset.getInt("VACANT_COUNT");

                listSPossible.add("{" + rset.getString("PARENT_STATION_NO") + "}" + "," + "["
                        + rset.getString("SOFT_ZONE_ID") + "]" + "," + "<" + rset.getString("HARD_ZONE_ID") + ">" + ","
                        + "(" + simpQty + ")");
            }
        }
        catch (SQLException e)
        {
            //Findなので、起こらないはず
            e.printStackTrace();
            throw new ReadWriteException();
        }
        catch (NotFoundException e)
        {
            //Findなので、起こらないはず
            e.printStackTrace();
            throw new ReadWriteException();
        }
        catch (DataExistsException e)
        {
            //Findなので、起こらないはず
            e.printStackTrace();
            throw new ReadWriteException();
        }
        finally
        {
            try
            {
                if (rset != null)
                {
                    rset.close();
                    rset = null;
                }

                closeStatement();
            }
            catch (SQLException e)
            {
                //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
                //<en>6126001 = Database error occured. {0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }

        //<jp> 取得した空棚数の情報を格納します。</jp>
        return listSPossible;
    }

    // Accessor methods -----------------------------------------------
    /**<jp>
     * データベース接続用の<code>Connection</code>を取得します。
     * @return 現在保持している<code>Connection</code>
     </jp>*/
    /**<en>
     * Retrieve <code>Connection</code> to connect with database.
     * @return :<code>Connection</code> currently preserved
     </en>*/
    public Connection getConnection()
    {
        return _conn;
    }

    /**<jp>
     * データベース接続用の<code>Connection</code>を設定します。
     * @param connection 設定するConnection
     </jp>*/
    /**<en>
     * Set <code>Connection</code> to connect with database
     * @param connection :Connection to set
     </en>*/
    public void setConnection(Connection connection)
    {
        try
        {
            if (connection == null || connection.isClosed())
            {
                throw new RuntimeException("Connection is null or closed!");
            }
            _conn = connection;
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Can not access to database!");
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * <code>ResultSet</code>から<code>Shelf</code>インスタンスの配列を生成します。
     * @param rset SHELFテーブル検索の結果セット
     * @param maxcreate Shelfインスタンスを生成する個数。0の場合結果セットすべてのインスタンスを生成します。
     * @return 生成されたShelfインスタンスの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Generate an array of <code>Shelf</code> instance from <code>ResultSet</code>.
     * @param rset :result set from SHELF table search
     * @param maxcreate : number of Shelf instance to generate. If it is 0, generate instances of 
     * all result set.
     * @return :Shelf instance array generated
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     </en>*/
    protected Shelf[] makeShelf(ResultSet rset, int maxcreate)
            throws ReadWriteException
    {
        //<jp> Shelfインスタンスの一時領域</jp>
        //<en> temporary store for Shelf instances</en>
        List<Shelf> tempShelfList = new ArrayList<Shelf>();

        //<jp> 検索結果からデータを取得し、Shelfインスタンスを新規作成する。</jp>
        //<en> data get from resultset and make new Shelf instance</en>
        try
        {
            int count = 0;
            while (rset.next())
            {
                if ((maxcreate != 0) && (count > maxcreate))
                {
                    //<jp> インスタンス生成上限数を超えた場合、ループを抜ける。</jp>
                    //<en> Gets out the loop when the cycle exceeded the ceiling number of of instance generation.</en>
                    break;
                }
                //<jp> Shelfインスタンスを生成する。</jp>
                //<en> Generates Shelf instance.</en>
                Shelf tmpShelf = new Shelf();
                setShelf(rset, tmpShelf);
                tempShelfList.add(tmpShelf);

                //<jp> カウントアップ</jp>
                //<en> count up.</en>
                count++;
            }
        }
        catch (SQLException e)
        {
            //<jp>6006002 = データベースエラーが発生しました。{0}</jp>
            //<en>6006002 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6006002, e), "ASShelfHandler");
            throw new ReadWriteException(e);
        }

        //<jp> 一時領域からShelf配列へインスタンスを移動する。</jp>
        //<en> move instance from List to array of Shelf.</en>
        Shelf[] rstarr = (Shelf[])tempShelfList.toArray(new Shelf[0]);

        return (rstarr);
    }

    /**<jp>
     * <code>ResultSet</code>から、各項目を取り出して、<code>Shelf</code>インスタンスの各項目をセットします。
     * @param rset SHELFテーブル検索の結果セット
     * @param tmpShelf セット対象となるShelfインスタンス
     * @return 生成されたShelfインスタンス
     * @throws SQLException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Get each item from <code>ResultSet</code>, and set each item for <code>Shelf</code> instance.
     * @param rset :result set from SHELF table search
     * @param tmpShelf :Shelf instance to be set 
     * @return :Shelf instance generated
     * @throws SQLException :Notifies if error occured in connection with database. 
     </en>*/
    protected Shelf setShelf(ResultSet rset, Shelf tmpShelf)
            throws SQLException
    {
        //<jp> ステーションNo.</jp>
        //<en> station number</en>
        tmpShelf.setStationNo(rset.getString("STATION_NO"));
        //<jp> バンク</jp>
        //<en> bank</en>
        tmpShelf.setBankNo(rset.getInt("BANK_NO"));
        //<jp> ベイ</jp>
        //<en> bay</en>
        tmpShelf.setBayNo(rset.getInt("BAY_NO"));
        //<jp> レベル</jp>
        //<en> level</en>
        tmpShelf.setLevelNo(rset.getInt("LEVEL_NO"));
        //<jp> 倉庫ステーションNo.</jp>
        //<en> warehouse</en>
        tmpShelf.setWhStationNo(DBFormat.replace(rset.getString("WH_STATION_NO")));
        //<jp> 棚の使用不可情報</jp>
        //<en> location availability</en>
        tmpShelf.setProhibitionFlag(rset.getString("PROHIBITION_FLAG"));
        //<jp> 棚状態</jp>
        //<en> presence</en>
        tmpShelf.setStatusFlag(rset.getString("STATUS_FLAG"));
        //<jp> ハードゾーン</jp>
        //<en> hardzone</en>
        tmpShelf.setHardZoneId(rset.getString("HARD_ZONE_ID"));
        //<jp> ソフトゾーン</jp>
        //<en> softzone</en>
        tmpShelf.setSoftZoneId(rset.getString("SOFT_ZONE_ID"));
        //<jp> 親ステーションNo.</jp>
        //<en> parent station</en>
        tmpShelf.setParentStationNo(DBFormat.replace(rset.getString("PARENT_STATION_NO")));
        //<jp> アイルステーションNo.</jp>
        //<en> aisle station</en>
        tmpShelf.setAisleStationNo(DBFormat.replace(rset.getString("PARENT_STATION_NO")));
        //<jp> アクセス不可棚フラグ</jp>
        //<en> accsess ng flag</en>
        tmpShelf.setAccessNgFlag(rset.getString("ACCESS_NG_FLAG"));
        //<jp> 空棚検索順</jp>
        //<en> priority</en>
        tmpShelf.setPriority(rset.getInt("PRIORITY"));
        //<jp> ペアステーションNo.</jp>
        //<en> pair station number</en>
        tmpShelf.setPairStationNo(DBFormat.replace(rset.getString("PAIR_STATION_NO")));
        //<jp> 手前、奥棚区分</jp>
        //<en> side</en>
        tmpShelf.setSide(rset.getString("SIDE"));

        return tmpShelf;
    }

    /**<jp>
     * インスタンス変数であるwStatementをクローズします。
     * executeSQLメソッドで生成されたカーソルはこのメソッドを呼び出し、必ずクローズする必要があります。
     </jp>*/
    /**<en>
     * Close the wStatement, which is the instance variable.
     * The cursor generated by executeSQL method must call this method to close.
     </en>*/
    protected void closeStatement()
    {
        try
        {
            if (_statement != null)
            {
                _statement.close();
            }
            _statement = null;
        }
        catch (SQLException e)
        {
            //<jp>6006002 = データベースエラーが発生しました。{0}</jp>
            //<en>6006002 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6006002, e), "ASShelfHandler");
        }
        catch (NullPointerException e)
        {
            //<jp> エラーログの出力処理も行う。</jp>
            //<en> Also perform the outputting og error log.</en>
            Object[] tObj = new Object[1];
            if (_statement != null)
            {
                tObj[0] = String.valueOf(_statement);
            }
            else
            {
                tObj[0] = "null";
            }
            RmiMsgLogClient.write(6016066, LogMessage.F_ERROR, "ASShelfHandler", tObj);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**<jp>
     * SQL文を受け取って、実行します。
     * @param sqlstr 実行するSQL文
     * @param query 問い合わせの場合は true
     * @return 結果の<code>ResultSet</code> それ以外はnull
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 実行結果が0件の場合に通知されます。
     * @throws DataExistsException Insert時に、ユニーク制約に違反した場合に通知されます。
     </jp>*/
    /**<en>
     * Accept the SQL statement and execute.
     * @param sqlstr :SQL statement to execute
     * @param query : true if it is query
     * @return <code>ResultSet</code> of the result.  null for anything else
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     * @throws NotFoundException  :Notifies if executed result was 0.
     * @throws DataExistsException :Notifies if it broke the uniqye restriction at Insert.
     </en>*/
    protected ResultSet executeSQL(String sqlstr, boolean query)
            throws ReadWriteException,
                NotFoundException,
                DataExistsException
    {
        ResultSet rset = null;
        DEBUG.MSG(DEBUG.HANDLER, sqlstr);
        try
        {
            //<jp> queryでfirst() で0行を見るためにはスクロールカーソルが必要</jp>
            //<en> Scroll cursor is required in order to view line 0 by first() in query.</en>
            _statement = getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if (query)
            {
                // SELECT
                rset = _statement.executeQuery(sqlstr);
            }
            else
            {
                int rrows = _statement.executeUpdate(sqlstr);
                //<jp> 更新行がなかった場合カーソルをCloseしていなかったところを修正</jp>
                //<en> When there is no updating line, the place which was not closing cursor is corrected</en>
                closeStatement();
                if (rrows == 0)
                {
                    throw (new NotFoundException("6003018"));
                }
            }
        }
        catch (SQLException e)
        {
            if (e.getErrorCode() == SQLErrors.ERR_DUPLICATED)
            {
                // 6026034=すでに同一データが存在するため、登録できません。
                RmiMsgLogClient.write(6026034, LogMessage.F_ERROR, "ASShelfHandler", null);
                throw (new DataExistsException("6026034"));
            }
            //<jp>6006002 = データベースエラーが発生しました。{0}</jp>
            //<en>6006002 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6006002, e), "ASShelfHandler");
            //<jp> データベースエラーが発生しました。</jp>
            //<en> Database error occured.</en>
            throw new ReadWriteException(e);
        }

        return (rset);
    }
}
//end of class

