// $Id: ToolHardZoneHandler.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.dbhandler;

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
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.wms.asrs.tool.location.HardZone;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**<jp>
 * <code>Zone</code>クラスをデータベースから取得したり、データベースに保管する為に利用するクラスです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class is used to retrieve/store the <code>Zone</code> class to/from database.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/

public class ToolHardZoneHandler
        implements ToolDatabaseHandler
{

    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    /**<jp> テーブル名 </jp>*/
    /**<en> name of the table </en>*/
    private String wTableName = "TEMP_DMHARDZONE";

    /**<jp> TEMP_WAREHOUSEテーブル名 </jp>*/
    /**<en> name of the TEMP_WAREHOUSE table</en>*/
    protected final String wWarehouseTableName = "TEMP_DMWAREHOUSE";

    /**<jp>
     * データベース接続用のConnectionインスタンス。
     * トランザクション管理は、このクラスの中では行わない。
     </jp>*/
    /**<en>
     * Connection instance to connect with database
     * Transaction control is not conducted in this class.
     </en>*/
    protected Connection wConn;

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

    // Constructors --------------------------------------------------
    /**<jp>
     * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
     * @param conn データベース接続用 Connection
     </jp>*/
    /**<en>
     * Generate instance by specifying <code>Connection</code> to connect with database.
     * @param conn :Connection to connect with database
     </en>*/
    public ToolHardZoneHandler(Connection conn)
    {
        setConnection(conn);
    }

    /**<jp>
     * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
     * @param conn データベース接続用 Connection
     * @param tablename テーブル名
     </jp>*/
    /**<en>
     * Generate instance by specifying <code>Connection</code> to connect with database.
     * @param conn :Connection to connect with database
     * @param tablename :name of the table
     </en>*/
    public ToolHardZoneHandler(Connection conn, String tablename)
    {
        setConnection(conn);
        wTableName = tablename;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * データベース接続用の<code>Connection</code>を設定します。
     * @param conn 設定するConnection
     </jp>*/
    /**<en>
     * Set the <code>Connection</code> to connect with database.
     * @param conn :Connection to set
     </en>*/
    public void setConnection(Connection conn)
    {
        wConn = conn;
    }

    /**<jp>
     * データベース接続用の<code>Connection</code>を取得します。
     * @return 現在保持している<code>Connection</code>
     </jp>*/
    /**<en>
     * Retrieve the <code>Connection</code> to connect with database.
     * @return :<code>Connection</code> currently preserved
     </en>*/
    public Connection getConnection()
    {
        return (wConn);
    }

    /**<jp>
     * テーブルのデータを全て削除します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Delete all data from the table.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public void truncate()
            throws ReadWriteException
    {
        Statement stmt = null;
        try
        {
            stmt = wConn.createStatement();
            String sqlstring = "TRUNCATE TABLE " + wTableName;
            // execute the sql
            stmt.executeQuery(sqlstring);
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException());
        }
        finally
        {
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (SQLException e)
            {
                //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
                //<en>6126001 = Database error occured.{0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }

    }

    /**<jp>
     * TEMP_HARDZONEを検索し、取得します。
     * @param key 検索のためのKey。 ToolHardZoneSearchKeyである必要があります。
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search and retrieve the TEMP_HARDZONE.
     * @param key :Key for search. ToolHardZoneSearchKey has to be used as a key.
     * @return :the array of the created object
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public ToolEntity[] find(ToolSearchKey key)
            throws ReadWriteException
    {
        Statement stmt = null;
        ResultSet rset = null;
        Object[] fmtObj = new Object[2];
        HardZone[] zoneArray = null;

        String fmtSQL = "SELECT * FROM " + wTableName + " {0} {1}";

        if (key.ReferenceCondition() != null)
        {
            if (key.SortCondition() != null)
            {
                fmtObj[0] = "WHERE " + key.ReferenceCondition();
                fmtObj[1] = "ORDER BY " + key.SortCondition();
            }
            else
            {
                fmtObj[0] = "WHERE " + key.ReferenceCondition();
            }
        }
        else if (key.SortCondition() != null)
        {
            fmtObj[0] = "ORDER BY " + key.SortCondition();
        }
        String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);

        try
        {
            stmt = wConn.createStatement();
            rset = stmt.executeQuery(sqlstring);
            zoneArray = makeZone(rset);
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolZoneHandler");
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException());
        }
        finally
        {
            try
            {
                if (rset != null)
                {
                    rset.close();
                }
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (SQLException e)
            {
                //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
                //<en>6126001 = Database error occured.{0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolZoneHandler");
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }
        return zoneArray;
    }

    /**<jp>
     * フリーアロケーション運用のゾーンに指定されている荷高の検索を行います。
     * @return 検索したHardZoneインスタンス
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws InvalidDefineException ShelfSelecotorインターフェースに定義されていない空棚検索方向が指定された場合に通知されます。
     </jp>*/
    public int[] findFreeAllocationHeight()
            throws ReadWriteException,
                InvalidDefineException
    {
        Statement stmt = null;
        ResultSet rset = null;
        Vector vec = new Vector();
        int[] loadsizes = null;

        String sqlstring =
                "SELECT DISTINCT(TEMP_DMHARDZONE.HEIGHT)" + " FROM " + wTableName + ", " + wWarehouseTableName
                        + " WHERE TEMP_DMHARDZONE.WH_STATION_NO = TEMP_DMWAREHOUSE.STATION_NO"
                        + " AND TEMP_DMWAREHOUSE.FREE_ALLOCATION_TYPE = "
                        + DBFormat.format(SystemDefine.FREE_ALLOCATION_ON) + " ORDER BY TEMP_DMHARDZONE.HEIGHT";

        try
        {
            stmt = wConn.createStatement();
            rset = stmt.executeQuery(sqlstring);
            while (rset.next())
            {
                vec.add(new Integer(rset.getInt("HEIGHT")));
            }

            loadsizes = new int[vec.size()];
            for (int i = 0; i < loadsizes.length; i++)
            {
                Integer loadsize = (Integer)vec.remove(0);
                loadsizes[i] = loadsize.intValue();
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolZoneHandler");
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException());
        }
        finally
        {
            try
            {
                if (rset != null)
                {
                    rset.close();
                }
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (SQLException e)
            {
                //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
                //<en>6126001 = Database error occured.{0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolZoneHandler");
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }

        return loadsizes;
    }

    /**<jp>
     * <code>Zone</code>の数を数えます。
     * @param key 検索のためのKey
     * @return 検索結果の件数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Count the number of <code>Zone</code>.
     * @param key :Key for search. 
     * @return :number of search results
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public int count(ToolSearchKey key)
            throws ReadWriteException
    {
        Statement stmt = null;
        ResultSet rset = null;
        int count = 0;
        Object[] fmtObj = new Object[1];

        try
        {
            stmt = wConn.createStatement();

            String fmtSQL = "SELECT COUNT(1) COUNT FROM " + wTableName + " {0}";

            if (key.ReferenceCondition() != null)
            {
                fmtObj[0] = "WHERE " + key.ReferenceCondition();
            }
            String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
            rset = stmt.executeQuery(sqlstring);

            while (rset.next())
            {
                count = rset.getInt("count");
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolZoneHandler");
            //<jp>ここで、ReadWriteExceptionをthrowする。</jp>
            //<en>Throw the ReadWriteException here.</en>
            throw new ReadWriteException();
        }
        finally
        {
            try
            {
                if (rset != null)
                {
                    rset.close();
                }
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (SQLException e)
            {
                //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
                //<en>6126001 = Database error occured.{0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolZoneHandler");
                //<jp>ここで、ReadWriteExceptionをthrowする。</jp>
                //<en>Throw the ReadWriteException here.</en>
                throw new ReadWriteException();
            }
        }
        return count;
    }

    /**<jp>
     * データベースに新たなゾーン情報を作成します。
     * @param tgt 作成するゾーン情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws DataExistsException 倉庫とゾーン番号が一致するゾーン情報がデータベースに登録されている場合に通知されます。
     </jp>*/
    /**<en>
     * Newly create hte zone information in database.
     * @param tgt :entity instance which preserves the zone information to create
     * @throws ReadWriteException  :Notifies if error occured in connection with database.
     * @throws DataExistsException Notifies if the zone information registered match the zone no.
     * of warehousees.
     </en>*/
    public void create(ToolEntity tgt)
            throws ReadWriteException,
                DataExistsException
    {
        Statement stmt = null;

        String sql = "INSERT INTO " + wTableName + " (" + "HARD_ZONE_ID" // 0
                + ", HARD_ZONE_NAME" // 1
                + ", WH_STATION_NO" // 2
                + ", HEIGHT" // 3
                + ", PRIORITY" // 4
                + ", START_BANK_NO" // 5
                + ", START_BAY_NO" // 6
                + ", START_LEVEL_NO" // 7
                + ", END_BANK_NO" // 8
                + ", END_BAY_NO" // 9
                + ", END_LEVEL_NO" // 10
                + ") values (" + "{0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10}" + ")";

        try
        {
            //<jp> FTTB 同一データチェックを行なうこと。</jp>
            //<en>FTTB Data duplication check to be done.</en>

            HardZone ins = (HardZone)tgt;
            stmt = wConn.createStatement();

            Object[] obj = setToZone(ins);
            String sqlstring = SimpleFormat.format(sql, obj);
            stmt.executeUpdate(sqlstring);
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolZoneHandler");
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException());
        }
        finally
        {
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (SQLException e)
            {
                //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
                //<en>6126001 = Database error occured.{0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolZoneHandler");
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }
    }

    /**<jp>
     * データベースのZONE情報を変更します。
     * @param  key 変更内容および変更条件を保持しているAlterKeyインスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 変更すべき情報が見つからない場合に通知されます。
     * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the zoner information in database. 
     * @param  key :AlterKey isntance which preserves the contents and conditions of modification 
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if data to modify cannot be found.
     * @throws InvalidDefineException :Notifies if the contents of update has not been set.
     </en>*/
    public void modify(ToolAlterKey key)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------

        Object[] fmtObj = new Object[3];
        String table = wTableName;
        Statement stmt = null;

        String fmtSQL = " UPDATE {0} SET {1} {2}";

        fmtObj[0] = table;

        if (key.ModifyContents(table) == null)
        {
            //<jp> 更新値がセットされていない場合は例外</jp>
            //<en> Exception if update value has not been set.</en>
            Object[] tobj = {
                table
            };
            RmiMsgLogClient.write(6126005, LogMessage.F_ERROR, "ToolZoneHandler", tobj);
            throw (new InvalidDefineException("6126005"));
        }
        fmtObj[1] = key.ModifyContents(table);

        if (key.ReferenceCondition(table) == null)
        {
            //<jp> 更新条件がセットされていない場合は例外</jp>
            //<en> Exception if update conditions have not been set.</en>
            Object[] tobj = {
                table
            };
            RmiMsgLogClient.write(6126006, LogMessage.F_ERROR, "ToolZoneHandler", tobj);
            throw (new InvalidDefineException("6126006"));
        }
        fmtObj[2] = "WHERE " + key.ReferenceCondition(table);

        String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
        try
        {
            stmt = wConn.createStatement();
            int count = stmt.executeUpdate(sqlstring); // private exec sql method
            if (count == 0)
            {
                RmiMsgLogClient.write("6126014" + wDelim + wTableName, "ToolZoneHandler");
                throw new NotFoundException("6126014" + wDelim + wTableName);
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>

            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolZoneHandler");
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>

            throw (new ReadWriteException());
        }
        finally
        {
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (SQLException e)
            {
                //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
                //<en>6126001 = Database error occured.{0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolZoneHandler");
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }
    }

    /**<jp>
     * データベースのZONE情報を変更します。SERIALNUMBERをキーにしてZONE情報を変更します。
     * @param tgt 作成するZone情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     * @throws NotFoundException 変更すべきZoneが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the zone information in database. Modify the zone information using the 
     * SERIALNUMBER as a key.
     * @param tgt :entity instance which preserves the zone information to create
     * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database.  
     * @throws NotFoundException  :Notifies if the zone to modify cannot be found.
     </en>*/
    public void modify(ToolEntity tgt)
            throws ReadWriteException,
                NotFoundException
    {
        Statement stmt = null;

        String sql = " UPDATE " + wTableName + " SET" + " HARD_ZONE_ID = {0}," // 0
                + " HARD_ZONE_NAME = {1}," // 1
                + " WH_STATION_NO = {2}," // 2
                + " HEIGHT = {3}," // 3
                + " PRIORITY = {4} " // 4
                + " START_BANK_NO = {5}," // 5
                + " START_BAY_NO = {6}," // 6
                + " START_LEVEL_NO = {7}," // 7
                + " END_BANK_NO = {8}, " // 8
                + " END_BAY_NO = {9}, " // 9
                + " END_LEVEL_NO = {10}, " // 10
                + " WHERE SERIALNUMBER = {11}"; // 11
        try
        {
            HardZone ins = (HardZone)tgt;
            stmt = wConn.createStatement();
            Object[] obj = setToZone(ins);
            String sqlstring = SimpleFormat.format(sql, obj);
            int count = stmt.executeUpdate(sqlstring);
            if (count == 0)
            {
                RmiMsgLogClient.write("6126014" + wDelim + wTableName, "ToolZoneHandler");
                throw new NotFoundException("6126014" + wDelim + wTableName);
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>

            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolZoneHandler");
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException());
        }
        finally
        {
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (SQLException e)
            {
                //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
                //<en>6126001 = Database error occured.{0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolZoneHandler");
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }
    }

    /**<jp>
     * データベースから、パラメータで渡されたZONEの情報を削除します。
     * このメソッドではSERIALNUMBERをキーにして削除します。
     * @param tgt 削除するZone情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     * @throws NotFoundException 削除すべきZoneが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete from database the zone information passed through parameter.
     * In this method, data will be deleted by using SERIALNUMBER as a key.
     * @param tgt :entity instance which preserves the zone information to delete
     * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database.  
     * @throws NotFoundException  :Notifies if zone to delete cannot be found.
     </en>*/
    public void drop(ToolEntity tgt)
            throws NotFoundException,
                ReadWriteException
    {
        Statement stmt = null;

        String sql = "DELETE FROM " + wTableName + " ";
        try
        {
            HardZone ins = (HardZone)tgt;
            stmt = wConn.createStatement();
            Object[] obj = setToZone(ins);
            String sqlstring = SimpleFormat.format(sql, obj);
            int count = stmt.executeUpdate(sqlstring);
            if (count == 0)
            {
                RmiMsgLogClient.write("6126015" + wDelim + wTableName, "ToolZoneHandler");
                throw new NotFoundException("6126015" + wDelim + wTableName);
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolZoneHandler");
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException(e));
        }
        finally
        {
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (SQLException e)
            {
                //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
                //<en>6126001 = Database error occured.{0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolZoneHandler");
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException(e));
            }
        }
    }

    /**<jp>
     * データベースから、パラメータで渡されたキーに合致する情報を削除します。
     * @param key 削除する情報のキー
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     * @throws NotFoundException 削除すべき情報が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete from database the information that match the key passed through parameter.
     * @param key :key for the information to delete
     * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database.  
     * @throws NotFoundException  :Notifies if information cannot be found as a result of search.
     </en>*/
    public void drop(ToolSearchKey key)
            throws NotFoundException,
                ReadWriteException
    {
        //<jp> DBからDelete</jp>
        //<en> Delete from DB.</en>
        ToolEntity[] tgts = find(key);

        if (tgts.length > 0)
        {
            drop(tgts[0]);
        }
    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * <code>Zone</code>インスタンスから情報を取り出して、文字列(<code>String</code>)
     * としてObject配列にセットします。
     * INSERT,UPDATE の為に用意されています。
     * データベースへの保存時、nullがふさわしいものは、文字列 null がセットされます。
     * 文字列タイプ(VARCHAR)の項目は、'(シングル・クオート)を前後に追加します。
     * @param tgt 情報を取得する<code>Zone</code>インスタンス
     * @return Object配列。
     * <p>
     * 配列順は以下のようになります。<br>
     * <pre>
     * HARDZONEID       // 0
     * HARDZONENAME     // 1
     * WHSTATIONNO      // 2
     * HEIGHT           // 3
     * PRIORITY         // 4
     * STARTBANKNO      // 5
     * STARTBAYNO       // 6
     * STARTLEVELNO     // 7
     * ENDBANKNO        // 8
     * ENDBAYNO         // 9
     * ENDLEVELNO       // 10
     * </pre></p>
     </jp>*/
    /**<en>
     * Retrieve the information from <code>Zone</code> instance and set to Object array
     * as string (<code>String</code>) instance.
     * This is prepared fpr INSERT and UPDATE.
     * When saving data in database. it sets the string null if appropriate.
     * String type items (VARCHAR) shuold be enclosed in ' (single quotations).
     * @param tgt :<code>Zone</code> instance to retrieve the information
     * @return :Object array
     * <p>
     * The order of arrays should be as follows.<br>
     * <pre>
     * HARDZONEID       // 0
     * HARDZONENAME     // 1
     * WHSTATIONNO      // 2
     * HEIGHT           // 3
     * PRIORITY         // 4
     * STARTBANKNO      // 5
     * STARTBAYNO       // 6
     * STARTLEVELNO     // 7
     * ENDBANKNO        // 8
     * ENDBAYNO         // 9
     * ENDLEVELNO       // 10
     * </pre></p>
     </en>*/
    protected Object[] setToZone(HardZone tgt)
    {

        Object[] fmtObj = new Object[11];

        // set parameters
        fmtObj[0] = Integer.toString(tgt.getHardZoneID());
        fmtObj[1] = DBFormat.format(tgt.getHardZoneName());
        fmtObj[2] = DBFormat.format(tgt.getWhStationNo());
        fmtObj[3] = Integer.toString(tgt.getHeight());
        fmtObj[4] = DBFormat.format(tgt.getPriority());
        fmtObj[5] = Integer.toString(tgt.getStartBankNo());
        fmtObj[6] = Integer.toString(tgt.getStartBayNo());
        fmtObj[7] = Integer.toString(tgt.getStartLevelNo());
        fmtObj[8] = Integer.toString(tgt.getEndBankNo());
        fmtObj[9] = Integer.toString(tgt.getEndBayNo());
        fmtObj[10] = Integer.toString(tgt.getEndLevelNo());

        return (fmtObj);
    }

    // Private methods -----------------------------------------------
    /**<jp>
     * <code>ResultSet</code>から、各項目を取り出して、<code>Zone</code>インスタンスを生成します。
     * @throws ReadWriteException データベース接続で異常が発生した場合に通知します。
     </jp>*/
    /**<en>
     * Retrieve each item from <code>ResultSet</code> and generate the <code>Zone</code> instance.
     * @throws ReadWriteException :Notifies if error occurred in connection with database.
     </en>*/
    private HardZone[] makeZone(ResultSet rset)
            throws ReadWriteException
    {
        Vector tmpVect = new Vector(10); // temporary store for Aisle instances
        // data get from resultset and make new Aisle instance
        try
        {
            while (rset.next())
            {
                HardZone tmpa = new HardZone();
                tmpa.setHardZoneID(rset.getInt("HARD_ZONE_ID"));
                tmpa.setHardZoneName(DBFormat.replace(rset.getString("HARD_ZONE_NAME")));
                tmpa.setWhStationNo(DBFormat.replace(rset.getString("WH_STATION_NO")));
                tmpa.setHeight(rset.getInt("HEIGHT"));
                tmpa.setPriority(DBFormat.replace(rset.getString("PRIORITY")));
                tmpa.setStartBankNo(rset.getInt("START_BANK_NO"));
                tmpa.setStartBayNo(rset.getInt("START_BAY_NO"));
                tmpa.setStartLevelNo(rset.getInt("START_LEVEL_NO"));
                tmpa.setEndBankNo(rset.getInt("END_BANK_NO"));
                tmpa.setEndBayNo(rset.getInt("END_BAY_NO"));
                tmpa.setEndLevelNo(rset.getInt("END_LEVEL_NO"));
                tmpa.setHandler(this);
                // append new Aisle instance to Vector
                tmpVect.add(tmpa);
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolZoneHandler");
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException(e));
        }

        // move instance from vector to array of Aisle
        HardZone[] zns = new HardZone[tmpVect.size()];
        tmpVect.copyInto(zns);

        return zns;
    }
}
//end of class

