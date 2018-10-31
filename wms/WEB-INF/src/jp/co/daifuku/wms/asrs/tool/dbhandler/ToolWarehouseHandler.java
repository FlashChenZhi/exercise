// $Id: ToolWarehouseHandler.java 5301 2009-10-28 05:36:02Z ota $
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
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;

/**<jp>
 * <code>Warehouse</code>クラスをデータベースから取得したり、データベースに保管する為に利用するクラスです。
 * 通常は、<code>Warehouse</code>クラスの取得に関しては、<code>StationFactory</code>を利用してください。
 * <code>Warehouse</code>クラスには<code>getHandler</code>メソッドが用意されていますので、
 * 対応するHandlerが必要な場合は、<code>getHandler</code>メソッドを利用して取得します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5301 $, $Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class is used to retrieve/store the <code>Warehouse</code> class from/to database.
 * Please use <code>StationFactory</code> in normal process to retrieve <code>Warehouse</code> class.
 * As <code>getHandler</code> method has been prepared for <code>Warehouse</code> class,
 * in case the Handler to support is unknown, retrieve one by using <code>getHandler</code> method.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5301 $, $Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $
 * @author  $Author: ota $
 </en>*/
public class ToolWarehouseHandler
        extends ToolStationHandler
{

    // Class fields --------------------------------------------------
    public static final String WAREHOUSE_HANDLE = "jp.co.daifuku.wms.base.dbhandler.WareHouseHandler";

    // Class variables -----------------------------------------------

    /**<jp> テーブル名 </jp>*/
    /**<en> name of the table </en>*/
    private String wTableName = "TEMP_DMWAREHOUSE";

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
     * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
     * @param conn データベース接続用 Connection
     </jp>*/
    /**<en>
     * Generate instance by specifying <code>Connection</code> to connect with database.
     * @param conn :Connection to connect with database
     </en>*/
    public ToolWarehouseHandler(Connection conn)
    {
        super(conn);
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
    public ToolWarehouseHandler(Connection conn, String tablename)
    {
        super(conn);
        wTableName = tablename;
    }

    // Public methods ------------------------------------------------


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

            sqlstring = "DELETE FROM " + wStationTypeTableName + " WHERE CLASS_NAME = " + "'" + WAREHOUSE_HANDLE + "'";
            // execute the sql
            stmt.executeQuery(sqlstring);
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "WarehouseHandler");
                //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
                //<en>6126001 = Database error occured. {0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }
    }

    /**<jp>
     * 倉庫を検索し、取得します。
     * @param key 検索のためのKey。
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search and retrieve the warehouse.
     * @param key :Key for the search
     * @return :the array of the created object
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public ToolEntity[] find(ToolSearchKey key)
            throws ReadWriteException
    {

        //-------------------------------------------------
        // variable define
        //-------------------------------------------------

        Warehouse[] fndWH = null; // for return variable
        Object[] fmtObj = new Object[2];

        // for database access
        ResultSet rset = null;

        String fmtSQL = "SELECT * FROM " + wTableName + " {0} {1}";

        if (key.ReferenceCondition() != null)
        {
            if (key.SortCondition() != null)
            {
                fmtObj[0] = " WHERE " + key.ReferenceCondition();
                fmtObj[1] = " ORDER BY " + key.SortCondition();
            }
            else
            {
                fmtObj[0] = " WHERE " + key.ReferenceCondition();
            }
        }
        else if (key.SortCondition() != null)
        {
            fmtObj[0] = " ORDER BY " + key.SortCondition();
        }

        String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);

        try
        {
            rset = executeSQL(sqlstring, true); // private exec sql method

            // make Warehouse instances from resultset
            // !!! makeWarehouse() is private method of this.
            fndWH = makeWarehouse(rset);
        }
        catch (NotFoundException e)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en>  This should not occur;</en>
            e.printStackTrace();
            throw (new ReadWriteException(e));
        }
        catch (DataExistsException ee)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en>  This should not occur;</en>
            ee.printStackTrace();
            throw (new ReadWriteException(ee));
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

        return (fndWH);
    }

    /**<jp>
     * <code>Warehouse</code>の数を数えます。
     * @param key 検索のためのKey
     * @return 検索結果の件数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Count the number of <code>Warehouse</code>.
     * @param key :Key for the search
     * @return    :number of search results
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public int count(ToolSearchKey key)
            throws ReadWriteException
    {
        //<jp> findで、オブジェクトを作って、その数を返す。</jp>
        //<jp> !!! パフォーマンス上、よろしくないので、場合によっては作り直す。</jp>
        //<en>Create the object using find, then return the number.</en>
        //<en>Attention! This is not a preferable process on a perfoemace basis.</en>
        //<en>This may have to be rebuilt on demand. </en>
        int tcount = 0;
        try
        {
            ToolEntity[] whs = find(key);
            tcount = whs.length;
        }
        catch (ReadWriteException e)
        {
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>

            throw (new ReadWriteException(e));
        }
        catch (Exception e)
        {
            //<jp>6126001 =データベースエラーが発生しました。{0}</jp>
            //<en>6126001 =Database error occured. {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "WarehouseHandler");
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException(e));
        }

        return (tcount);
    }

    /**<jp>
     * データベースに新規倉庫情報を作成します。
     * @param tgt 作成する倉庫情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws DataExistsException 既に、同じ倉庫がデータベースに登録済みの場合に通知されます。
     </jp>*/
    /**<en>
     * Create new warehouse information in database.
     * @param tgt :entity instance which has the warehouse information to create
     * @throws ReadWriteException  :Notifies if error occured in connection with database.
     * @throws DataExistsException :Notifies if the same warehouse has been registered in database.
     </en>*/
    public void create(ToolEntity tgt)
            throws ReadWriteException,
                DataExistsException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------

        String fmtSQL = "INSERT INTO " + wTableName + " (" + "  STATION_NO" // 0
                + ", WAREHOUSE_NO" // 1
                + ", MAX_MIXEDPALLET" // 2
                + ", WAREHOUSE_NAME" // 3
                + ", LAST_USED_STATION_NO" // 4
                + ", EMPLOYMENT_TYPE" // 5
                + ", FREE_ALLOCATION_TYPE" // 6
                + ", LOCATION_SEARCH_TYPE" // 7
                + ", AISLE_SEARCH_TYPE" // 8
                + ") values (" + "{0},{1},{2},{3},{4},{5},{6},{7},{8}" + ")";
        String fmtSQL_StationType = "INSERT INTO " + wStationTypeTableName + " (" + "  STATION_NO" // 0
                + ",  CLASS_NAME" // 1
                + ") VALUES (" + "{0}, '" + WAREHOUSE_HANDLE + "'" + ")";

        Warehouse tgtWH;
        String sqlstring;
        //-------------------------------------------------
        // process
        //-------------------------------------------------
        if (tgt instanceof Warehouse)
        {
            tgtWH = (Warehouse)tgt;
        }
        else
        {
            //<jp>致命的なエラーが発生しました。{0}</jp>
            //<en>Fatal error has occurred.{0}</en>
            RmiMsgLogClient.write("6126499" + wDelim + "Illegal argument. Set Warehouse Instance.",
                    this.getClass().getName());
            throw (new ReadWriteException());
        }
        try
        {
            // setting Station information to Object array
            Object[] fmtObj = setToArray(tgtWH);
            // create actual SQL
            sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
            // execute the sql
            executeSQL(sqlstring, false);

            //for stationtype table
            sqlstring = SimpleFormat.format(fmtSQL_StationType, fmtObj);
            executeSQL(sqlstring, false);
        }
        catch (NotFoundException e)
        {
            //<jp> Insertなので、起こらないはず</jp>
            //<en> This should not occur;</en>
            e.printStackTrace();
            throw (new ReadWriteException(e));
        }
    }

    /**<jp>
     * データベースの倉庫情報を変更します。
     * @param tgt 作成する倉庫情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 変更すべき倉庫が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the warehouse information in database.
     * @param tgt :entity instance which has the warehouse information to modify
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if data of warehouse to modify cannot be found.
     </en>*/
    public void modify(ToolEntity tgt)
            throws ReadWriteException,
                NotFoundException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        String fmtSQL =
                "UPDATE " + wTableName + " SET " + "  WAREHOUSE_NO = {1}" + ", MAX_MIXEDPALLET = {2}"
                        + ", WAREHOUSE_NAME = {3}" + ", LAST_USED_STATION_NO = {4}" + ", EMPLOYMENT_TYPE = {5}"
                        + ", FREE_ALLOCATION_TYPE = {6}" + ", LOCATION_SEARCH_TYPE = {7}" + ", AISLE_SEARCH_TYPE = {8}"
                        + "  WHERE STATION_NO = {0}";

        Warehouse tgtWH;
        String sqlstring;
        //-------------------------------------------------
        // process
        //-------------------------------------------------
        if (tgt instanceof Warehouse)
        {
            tgtWH = (Warehouse)tgt;
        }
        else
        {
            //<jp>致命的なエラーが発生しました。{0}</jp>
            //<en>Fatal error has occurred.{0}</en>
            RmiMsgLogClient.write("6126499" + wDelim + "Illegal argument. Set Warehouse Instance.",
                    this.getClass().getName());
            throw (new ReadWriteException());
        }
        try
        {
            // setting Station information to Object array
            Object[] fmtObj = setToArray(tgtWH);
            // create actual SQL
            sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
            // execute the sql
            executeSQL(sqlstring, false);
        }
        catch (DataExistsException e)
        {
            //<jp> Insertなので、起こらないはず</jp>
            //<en> This should not occur;</en>
            e.printStackTrace();
            throw (new ReadWriteException(e));
        }
    }

    /**<jp>
     * データベースの棚情報を変更します。変更内容および変更条件はToolAlterKeyより獲得します。
     * @param  key 変更内容および変更条件を保持しているToolAlterKeyインスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException  変更対象がデータベースに見つからない場合に通知されます。
     * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the shelf information in database. The contents and conditions of the modificaiton will be 
     * obtained by ToolAlterKey.
     * @param  key :ToolAlterKey instance preserved by contents and conditions of the modification
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies when data to updata cannot be found in database.
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

        String fmtSQL = " UPDATE {0} SET {1} {2}";

        fmtObj[0] = table;

        if (key.ModifyContents(table) == null)
        {
            //<jp> 更新値がセットされていない場合は例外</jp>
            //<en> Exception if the update values have not been set.</en>
            Object[] tobj = {
                table
            };
            RmiMsgLogClient.write(6126005, LogMessage.F_ERROR, "WarehouseHandler", tobj);
            throw (new InvalidDefineException("6126005"));
        }
        fmtObj[1] = key.ModifyContents(table);

        if (key.ReferenceCondition(table) == null)
        {
            //<jp> 更新条件がセットされていない場合は例外</jp>
            //<en> Exception if the update conditions have not been set.</en>
            Object[] tobj = {
                table
            };
            RmiMsgLogClient.write(6126006, LogMessage.F_ERROR, "WarehouseHandler", tobj);
            throw (new InvalidDefineException("6126006"));
        }
        fmtObj[2] = "WHERE " + key.ReferenceCondition(table);

        String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);

        try
        {
            executeSQL(sqlstring, false); // private exec sql method
        }
        catch (DataExistsException ee)
        {
            //<jp> updateなので、起こらないは</jp>
            //<en> This should not occur;</en>
            ee.printStackTrace();
            throw (new InvalidDefineException(ee.getMessage()));
        }
    }

    /**<jp>
     * データベースから、パラメータで渡された倉庫の情報を削除します。
     * @param tgt 削除する倉庫情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 削除すべき倉庫が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete from database the warehouse information of entity instance which has been passed through parameter.
     * @param tgt :entity instance which has the warehouse informtion to delete
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if warehouse data to delete cannot be found.
     </en>*/
    public void drop(ToolEntity tgt)
            throws ReadWriteException,
                NotFoundException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        String fmtSQL = "DELETE FROM " + wTableName + " WHERE" + " STATION_NO = {0}";

        String fmtSQL_StationType = "DELETE FROM " + wStationTypeTableName + " WHERE STATION_NO = {0}"; // 0


        Warehouse tgtWH;
        String sqlstring;
        //-------------------------------------------------
        // process
        //-------------------------------------------------
        if (tgt instanceof Warehouse)
        {
            tgtWH = (Warehouse)tgt;
        }
        else
        {
            //<jp>致命的なエラーが発生しました。{0}</jp>
            //<en>Fatal error has occurred.{0}</en>
            RmiMsgLogClient.write("6126499" + wDelim + "Illegal argument. Set Warehouse Instance.",
                    this.getClass().getName());
            throw (new ReadWriteException());
        }
        try
        {
            // setting Station information to Object array
            Object[] fmtObj = setToArray(tgtWH);
            // create actual SQL
            sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
            // execute the sql
            executeSQL(sqlstring, false);

            //for stationtype table.
            sqlstring = SimpleFormat.format(fmtSQL_StationType, fmtObj);
            // execute the sql
            executeSQL(sqlstring, false);
        }
        catch (DataExistsException e)
        {
            //<jp> Insertなので、起こらないはず</jp>
            //<en> This should not occur;</en>
            e.printStackTrace();
            throw (new ReadWriteException(e));
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * <code>Warehouse</code>インスタンスから情報を取り出して、文字列(<code>String</code>)
     * としてObject配列にセットします。
     * INSERT,UPDATE の為に用意されています。
     * データベースへの保存時、nullがふさわしいものは、文字列 null がセットされます。
     * 文字列タイプ(VARCHAR)の項目は、'(シングル・クオート)を前後に追加します。
     * @param tgtWh 情報を取得する<code>Shelf</code>インスタンス
     * @return Object配列。
     * <p>
     * 配列順は以下のようになります。<br>
     * <pre>
     * STATIONNO         // 0
     * WAREHOUSENO       // 1
     * WAREHOUSETYPE         // 2
     * MAXMIXEDPALLET       // 3
     * WAREHOUSENAME         // 4
     * LASTUSEDSTATIONNO // 5
     * EMPLOYMENTTYPE         // 6
     * </pre></p>
     </jp>*/
    /**<en>
     * Retrieve information from the <code>Warehouse</code> instance and set to the Object array
     * as a string.(<code>String</code>).
     * This is prepared for INSERT and UPDATE.
     * When storing in database and if appropiate, it will set string null in some cases.
     * Enclose the string type items (VARCHAR) in ' single quotations.
     * @param tgtWh :<code>Shelf</code> instance to retrieve the information
     * @return :the Object array
     * <p>
     * The order of the arrays whould be as follows.<br>
     * <pre>
     * STATIONNO         // 0
     * WAREHOUSENO       // 1
     * WAREHOUSETYPE         // 2
     * MAXMIXEDPALLET       // 3
     * WAREHOUSENAME         // 4
     * LASTUSEDSTATIONNO // 5
     * EMPLOYMENTTYPE         // 6
     * </pre></p>
     </en>*/
    protected Object[] setToArray(Warehouse tgtWH)
    {
        Object[] fmtObj = new Object[9];

        // set parameters
        fmtObj[0] = DBFormat.format(tgtWH.getStationNo());
        fmtObj[1] = Integer.toString(tgtWH.getWarehouseNo());
        //        fmtObj[2] = Integer.toString(tgtWH.getWarehouseType());
        fmtObj[2] = Integer.toString(tgtWH.getMaxMixedPallet());
        fmtObj[3] = DBFormat.format(tgtWH.getWarehouseName());
        fmtObj[4] = DBFormat.format(tgtWH.getLastUsedStationNo());
        fmtObj[5] = Integer.toString(tgtWH.getEmploymentType());
        fmtObj[6] = Integer.toString(tgtWH.getFreeAllocationType());
        fmtObj[7] = Integer.toString(tgtWH.getLocationSearchType());
        fmtObj[8] = Integer.toString(tgtWH.getAisleSearchType());
        return (fmtObj);
    }

    // Private methods -----------------------------------------------
    /**<jp>
     * <code>ResultSet</code>から、各項目を取り出して、<code>Warehouse</code>インスタンスを生成します。
     * @throws ReadWriteException データベース接続で異常が発生した場合に通知します。
     </jp>*/
    /**<en>
     * Retrieve each item from <code>ResultSet</code> and generate the <code>Warehouse</code> instance.
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     </en>*/
    private Warehouse[] makeWarehouse(ResultSet rset)
            throws ReadWriteException
    {
        Vector tmpwhVect = new Vector(10); // temporary store for Warehouse instances

        // data get from resultset and make new Warehouse instance
        try
        {
            while (rset.next())
            {
                Warehouse tmpwh = new Warehouse(DBFormat.replace(rset.getString("STATION_NO")));
                tmpwh.setStationNo(DBFormat.replace(rset.getString("STATION_NO")));
                tmpwh.setWarehouseNo(rset.getInt("WAREHOUSE_NO"));
//                tmpwh.setWarehouseType(rset.getInt("WAREHOUSE_TYPE"));
                tmpwh.setMaxMixedPallet(rset.getInt("MAX_MIXEDPALLET"));
                tmpwh.setWarehouseName(DBFormat.replace(rset.getString("WAREHOUSE_NAME")));
                tmpwh.setLastUsedStationNo(DBFormat.replace(rset.getString("LAST_USED_STATION_NO")));
                tmpwh.setEmploymentType(rset.getInt("EMPLOYMENT_TYPE"));
                tmpwh.setFreeAllocationType(rset.getInt("FREE_ALLOCATION_TYPE"));
                tmpwh.setLocationSearchType(rset.getInt("LOCATION_SEARCH_TYPE"));
                tmpwh.setAisleSearchType(rset.getInt("AISLE_SEARCH_TYPE"));
                tmpwh.setHandler(this);

                // append new Warehouse instance to Vector
                tmpwhVect.add(tmpwh);
            }
        }
        catch (InvalidStatusException e)
        {
            e.printStackTrace();
            throw new ReadWriteException(e);
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured. {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "WarehouseHandler");
            throw (new ReadWriteException());
        }

        // move instance from vector to array of Warehouse
        Warehouse[] rwharr = new Warehouse[tmpwhVect.size()];
        tmpwhVect.copyInto(rwharr);

        return (rwharr);
    }
}
//end of class

