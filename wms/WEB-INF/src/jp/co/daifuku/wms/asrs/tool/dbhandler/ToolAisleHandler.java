// $Id: ToolAisleHandler.java 5866 2009-11-14 09:04:48Z ota $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;
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

import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.wms.asrs.tool.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.tool.location.Aisle;
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

/**<jp>
 * <code>Aisle</code>クラスをデータベースから取得したり、データベースに保管する為に利用するクラスです。
 * 通常は、<code>Aisle</code>クラスの取得に関しては、<code>StationFactory</code>を利用してください。
 * <code>Aisle</code>クラスには<code>getHandler</code>メソッドが用意されていますので、
 * 対応するHandlerが必要な場合は、<code>getHandler</code>メソッドを利用して取得します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/20</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5866 $, $Date: 2009-11-14 18:04:48 +0900 (土, 14 11 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class us used to retrieve and store the <code>Aisle</code> class from/to database.
 * Normally, please use <code>StationFactory</code> concerning the retrieval of <code>Aisle</code> class.
 * For <code>Aisle</code> class, <code>getHandler</code> methods are prepared; if support of a Handler
 * is needed, retrieve one by using <code>getHandler</code> method.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/20</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5866 $, $Date: 2009-11-14 18:04:48 +0900 (土, 14 11 2009) $
 * @author  $Author: ota $
 </en>*/
public class ToolAisleHandler extends ToolStationHandler
{

    // Class fields --------------------------------------------------
    public static final String AISLE_HANDLE = "jp.co.daifuku.wms.base.dbhandler.AisleHandler";

    // Class variables -----------------------------------------------
    /**<jp> テーブル名 </jp>*/
    /**<en> name of the table </en>*/
    private String wTableName = "TEMP_DMAISLE";

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
        return ("$Revision: 5866 $,$Date: 2009-11-14 18:04:48 +0900 (土, 14 11 2009) $") ;
    }


    // Constructors --------------------------------------------------
    /**<jp>
     * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
     * @param conn データベース接続用 Connection
     </jp>*/
    /**<en>
     * Generate the instance by sprcifing <code>Connection</code> to connect with database.
     * @param conn :Connection to connect with database
     </en>*/
    public ToolAisleHandler(Connection conn)
    {
        super(conn) ;
    }
    /**<jp>
     * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
     * @param conn データベース接続用 Connection
     * @param tablename テーブル名
     </jp>*/
    /**<en>
     * Generate instance by specifying <code>Connection</code> to connect with database.
     * @param conn : Connection to connect with database
     * @param tablename :table name
     </en>*/
    public ToolAisleHandler(Connection conn, String tablename)
    {
        super(conn) ;
        wTableName = tablename;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * テーブルのデータを全て削除します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Delete all table data.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public void truncate() throws ReadWriteException
    {
        Statement stmt = null;
        try
        {
            stmt = wConn.createStatement();
            String sqlstring = "TRUNCATE TABLE " + wTableName ;
            // execute the sql
            stmt.executeQuery(sqlstring) ;
            
            sqlstring = "DELETE FROM " + wStationTypeTableName
                        + " WHERE CLASS_NAME = " + "'" + AISLE_HANDLE + "'" ;
            // execute the sql
            stmt.executeQuery(sqlstring) ;
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured. {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName()) ;
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException()) ;
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
                //<en>6126001 = Database error occured. {0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName()) ;
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException()) ;
            }
        }
    }
    /**<jp>
     * アイルを検索し、取得します。
     * @param key 検索のためのKey。 
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search and retrieve the aisle.
     * @param key :Key for search 
     * @return :the array of the object created
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        Aisle[] fnd = null ;    // for return variable
        Object[]  fmtObj = new Object[2] ;

        // for database access
        ResultSet rset = null ;

        String fmtSQL = "SELECT * FROM " + wTableName + " {0} {1}" ;

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

        String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;

        try
        {
            rset = executeSQL(sqlstring, true) ;    // private exec sql method
            
            fnd = makeAisle(rset) ;
            
            return (fnd) ;
        }
        catch (NotFoundException e)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en> This will not occur;</en>
            throw (new ReadWriteException(e)) ;
        }
        catch (DataExistsException e)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en> This will not occur;</en>
            throw (new ReadWriteException(e)) ;
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
            }
            catch (SQLException e)
            {
                //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
                //<en>6126001 = Database error occured. {0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName()) ;
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException()) ;
            }
        }
    }

    /**<jp>
     * <code>Aisle</code>の数を数えます。
     * @param key 検索のためのKey
     * @return 検索結果の件数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Count the number of the <code>Aisle</code>.
     * @param key :Key for search
     * @return :number of search result
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public int count(ToolSearchKey key) throws ReadWriteException
    {
        //<jp> findで、オブジェクトを作って、その数を返す。</jp>
        //<jp>!!! パフォーマンス上、よろしくないので、場合によっては作り直す。</jp>
        //<en> Create the object by using find, then return the number.</en>
        //<en>!!! This movement is not desirable on performance basis. The process should be rebuilt depending on circumstances.</en>
        int tcount = 0 ;
        try
        {
            ToolEntity[] ent = find(key) ;
            tcount = ent.length ;
        }
        catch (Exception e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured. {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "AisleHandler") ;
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException(e)) ;
        }
        return (tcount) ;
    }

    /**<jp>
     * データベースに新規アイル情報を作成します。
     * @param tgt 作成するアイル情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws DataExistsException 既に、同じ倉庫がデータベースに登録済みの場合に通知されます。
     </jp>*/
    /**<en>
     * Create the new aisle information in database.
     * @param tgt :entity instance which has the aisle information creating.
     * @throws ReadWriteException  :Notifies if error occured in connection with database.
     * @throws DataExistsException :Notifies if the same warehouse is already registered in database.
     </en>*/
    public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        String fmtSQL = "INSERT INTO " + wTableName + " ("
                        + "  STATION_NO"                  // 0
                        + ", WH_STATION_NO"               // 1
                        + ", AISLE_NO"                    // 2
                        + ", CONTROLLER_NO"               // 3
                        + ", DOUBLE_DEEP_KIND"            // 4
                        + ", STATUS"                      // 5
                        + ", INVENTORY_CHECK_FLAG"        // 6
                        + ", MAX_CARRY"                   // 7
                        + ") values ("
                        + "{0},{1},{2},{3},{4},{5},{6},{7}"
                        + ")" ;
        String fmtSQL_StationType = "INSERT INTO " + wStationTypeTableName + " ("
                        + "  STATION_NO"             // 0
                        + ",  CLASS_NAME"            // 1
                        + ") VALUES ("
                        + "{0}, '" + AISLE_HANDLE + "'" 
                        + ")" ;

        Aisle tgtAisle ;
        String sqlstring ;
        //-------------------------------------------------
        // process
        //-------------------------------------------------
        if (tgt instanceof Aisle)
        {
            tgtAisle = (Aisle)tgt ;
        }
        else
        {
            //<jp>致命的なエラーが発生しました。{0}</jp>
            //<en>Fatal error occurred.{0}</en>
            RmiMsgLogClient.write("6126499" + wDelim + "Illegal argument. Set Aisle Instance.", this.getClass().getName());
            throw (new ReadWriteException()) ;
        }
        try
        {
            // setting Station information to Object array
            Object [] fmtObj = setToArray(tgtAisle) ;
            // create actual SQL
            sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
            // execute the sql
            executeSQL(sqlstring, false) ;
            
            //for stationtype table
            sqlstring = SimpleFormat.format(fmtSQL_StationType, fmtObj) ;
            executeSQL(sqlstring, false) ;
        }
        catch (NotFoundException e)
        {
            //<jp> Insertなので、起こらないはず</jp>
            //<en> This should not happen;</en>
            e.printStackTrace() ;
            throw (new ReadWriteException(e)) ;
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
     * @param tgt :entity instance which has the warehouse insformation to create
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if warehouse to modify cannot be found.
     </en>*/
    public void modify(ToolEntity tgt) throws ReadWriteException, NotFoundException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        String fmtSQL = "UPDATE " + wTableName + " SET "
                        + ", WH_STATION_NO          = {1}"
                        + ", AISLE_NO               = {2}"
                        + ", CONTROLLER_NO          = {3}"
                        + ", DOUBLE_DEEP_KIND       = {4}"
                        + ", STATUS                 = {5}"
                        + ", INVENTORY_CHECK_FLAG   = {6}"
                        + " WHERE "
                        + " STATION_NO = {0}" ;

        Aisle tgtAisle ;
        String sqlstring ;
        //-------------------------------------------------
        // process
        //-------------------------------------------------
        if (tgt instanceof Aisle)
        {
            tgtAisle = (Aisle)tgt ;
        }
        else
        {
            //<jp>致命的なエラーが発生しました。{0}</jp>
            //<en>Fatal error occurred.{0}</en>
            RmiMsgLogClient.write("6126499" + wDelim + "Illegal argument. Set Aisle Instance.", this.getClass().getName());
            throw (new ReadWriteException()) ;
        }
        try
        {
            // setting Station information to Object array
            Object [] fmtObj = setToArray(tgtAisle) ;
            // create actual SQL
            sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
            // execute the sql
            executeSQL(sqlstring, false) ;
        }
        catch (DataExistsException ee)
        {
            //<jp> Insertなので、起こらないはず</jp>
            //<en> This should not occur;</en>
            ee.printStackTrace() ;
            throw (new ReadWriteException(ee)) ;
        }
    }

    /**<jp>
     * データベースの棚情報を変更します。変更内容および変更条件はAlterKeyより獲得します。
     * @param  key 変更内容および変更条件を保持しているAlterKeyインスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException  変更対象がデータベースに見つからない場合に通知されます。
     * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the location data in database. The contents and conditions of the modification
     * will be retrieved by AlterKey.
     * @param  key ::AlterKey isntance which preserves the contents and conditions of modification 
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if the target data to mofdify cannot be found in database.
     * @throws InvalidDefineException :Notifies if the contents of update has not been set.
     </en>*/
    public void modify(ToolAlterKey key) throws ReadWriteException, NotFoundException, InvalidDefineException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        Object[]  fmtObj     = new Object[3] ;
        String    table      = wTableName; 

        String fmtSQL = " UPDATE {0} SET {1} {2}";

        fmtObj[0] = table;

        if (key.ModifyContents(table) == null)
        {
            //<jp> 更新値がセットされていない場合は例外</jp>
            //<en> Exception if the conditions of update has not been set.</en>
            Object[] tobj = {table};
            RmiMsgLogClient.write(6126005, LogMessage.F_ERROR, this.getClass().getName(), tobj);
            throw (new InvalidDefineException("6126005"));
        }
        fmtObj[1] = key.ModifyContents(table);

        if (key.ReferenceCondition(table) == null)
        {
            //<jp> 更新条件がセットされていない場合は例外</jp>
            //<en> Exception if the conditions of update has not been set.</en>
            Object[] tobj = {table};
            RmiMsgLogClient.write(6126006, LogMessage.F_ERROR, this.getClass().getName(), tobj);
            throw (new InvalidDefineException("6126006"));
        }
        fmtObj[2] = "WHERE " + key.ReferenceCondition(table);

        String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
        try
        {
            executeSQL(sqlstring, false) ;    // private exec sql method
        }
        catch (DataExistsException ee)
        {
            //<jp> updateなので、起こらないはず</jp>
            //<en> Tis should not happen;</en>
            ee.printStackTrace() ;
            throw (new ReadWriteException(ee)) ;
        }
    }


    /**<jp>
     * データベースから、パラメータで渡された倉庫の情報を削除します。
     * @param tgt 削除する倉庫情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 削除すべき倉庫が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete from database the warehouse information passed through parameter.
     * @param tgt :entity instance which preserves the warehouse information to delete
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if the warehouse information to delete cannot be found.
     </en>*/
    public void drop(ToolEntity tgt) throws ReadWriteException, NotFoundException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        String fmtSQL = "DELETE FROM " + wTableName
                        + " WHERE"
                        + " STATION_NO = {0}" ;
        String fmtSQL_StationType = "DELETE FROM " + wStationTypeTableName +
                        " WHERE STATION_NO = {0}" ;            // 0

        Aisle tgtAisle ;
        String sqlstring ;
        //-------------------------------------------------
        // process
        //-------------------------------------------------
        if (tgt instanceof Aisle)
        {
            tgtAisle = (Aisle)tgt ;
        }
        else
        {
            //<jp>致命的なエラーが発生しました。{0}</jp>
            //<en>Fatal error occurred.{0}</en>
            RmiMsgLogClient.write("6126499" + wDelim + "Illegal argument. Set Aisle Instance.", this.getClass().getName());
            throw (new ReadWriteException()) ;
        }
        try
        {
            // setting Station information to Object array
            Object [] fmtObj = setToArray(tgtAisle) ;
            // create actual SQL
            sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
            // execute the sql
            executeSQL(sqlstring, false) ;
            
            //for stationtype table.
            sqlstring = SimpleFormat.format(fmtSQL_StationType, fmtObj) ;
            // execute the sql
            executeSQL(sqlstring, false) ;
        }
        catch (DataExistsException e)
        {
            //<jp> Insertなので、起こらないはず</jp>
            //<en> This should not occur;</en>
            e.printStackTrace() ;
            throw (new ReadWriteException(e)) ;
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * <code>Aisle</code>インスタンスから情報を取り出して、文字列(<code>String</code>)
     * としてObject配列にセットします。
     * INSERT,UPDATE の為に用意されています。
     * データベースへの保存時、nullがふさわしいものは、文字列 null がセットされます。
     * 文字列タイプ(VARCHAR)の項目は、'(シングル・クオート)を前後に追加します。
     * @param tgt 情報を取得する<code>Aisle</code>インスタンス
     * @return Object配列。
     * <p>
     * 配列順は以下のようになります。<br>
     * <pre>
     * STATION_NO            // 0
     * WH_STATION_NO         // 1
     * AISLE_NO              // 2
     * CONTROLLER_NO         // 3
     * DOUBLE_DEEP_KIND      // 4
     * STATUS                // 5
     * INVENTORY_CHECK_FLAG  // 6
     * </pre></p>
     </jp>*/
    /**<en>
     * Retrieve the information from <code>Aisle</code> isntance, then set to Object array
     * as a string (<code>String</code>).
     * This is prepared for INSERT and UPDATE.
     * When storing data in database and if null is appropriate, the string null will be set.
     * The string type items (VARCHAR) should be snclosed in '(single quotations).
     * @param tgt :<code>Aisle</code> instance to retrieve the information
     * @return Object array
     * <p>
     * The order of array should be as follows.<br>
     * <pre>
     * STATION_NO            // 0
     * WH_STATION_NO         // 1
     * AISLE_NO              // 2
     * CONTROLLER_NO         // 3
     * DOUBLE_DEEP_KIND      // 4
     * STATUS                // 5
     * INVENTORY_CHECK_FLAG  // 6
     * </pre></p>
     </en>*/
    protected Object[] setToArray(Aisle tgt)
    {

        Object[] fmtObj = new Object[8] ;
        // set parameters
        fmtObj[0] = DBFormat.format(tgt.getStationNo()) ;
        fmtObj[1] = DBFormat.format(tgt.getWarehouseStationNo()) ;
        fmtObj[2] = String.valueOf(tgt.getAisleNo()) ;
        if (tgt.getGroupController() == null)
        {
            fmtObj[3] = null;
        }
        else
        {
            fmtObj[3] = String.valueOf(tgt.getGroupController().getNumber()) ;
        }
        fmtObj[4] = String.valueOf(tgt.getDoubleDeepKind()) ;
        fmtObj[5] = String.valueOf(tgt.getStatus()) ;
        fmtObj[6] = String.valueOf(tgt.getInventoryCheckFlag()) ;
        fmtObj[7] = String.valueOf(tgt.getMaxCarry()) ;

        return (fmtObj) ;
    }

    // Private methods -----------------------------------------------
    /**<jp>
     * <code>ResultSet</code>から、各項目を取り出して、<code>Aisle</code>インスタンスを生成します。
     * @return Aisle Entity
     * @throws ReadWriteException データベース接続で異常が発生した場合に通知します。
     </jp>*/
    /**<en>
     * Retrieve each item from <code>ResultSet</code> and generate isntances of <code>Aisle</code>.
     * @param rset Result Set
     * @return Aisle Entity
     * @throws ReadWriteException :Notifies if error occurred in connection with database.
     </en>*/
    private Aisle[] makeAisle(ResultSet rset) throws ReadWriteException
    {
        Vector tmpVect = new Vector(10) ;    // temporary store for Aisle instances

        // data get from resultset and make new Aisle instance
        try
        {
            while (rset.next())
            {
                Aisle tmpa = new Aisle(DBFormat.replace(rset.getString("STATION_NO"))) ;
                tmpa.setWhStationNo(DBFormat.replace(rset.getString("WH_STATION_NO"))) ;
                tmpa.setAisleNo(rset.getInt("AISLE_NO"));
                tmpa.setGroupController(GroupController.getInstance(wConn, rset.getInt("CONTROLLER_NO"))) ;
                tmpa.setDoubleDeepKind(rset.getInt("DOUBLE_DEEP_KIND"));
                tmpa.setStatus(rset.getInt("STATUS"));
                tmpa.setInventoryCheckFlag(rset.getInt("INVENTORY_CHECK_FLAG"));
                tmpa.setMaxCarry(rset.getInt("MAX_CARRY"));
                tmpa.setHandler(this) ;
                // append new Aisle instance to Vector
                tmpVect.add(tmpa) ;
            }
            
            // move instance from vector to array of Aisle
            Aisle[] aisles = new Aisle[tmpVect.size()] ;
            tmpVect.copyInto(aisles);

            return (aisles) ;
        }
        catch (InvalidStatusException e)
        {
            throw new ReadWriteException(e);
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured. {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "AisleHandler") ;
            throw (new ReadWriteException()) ;
        }
    }
}
//end of class

