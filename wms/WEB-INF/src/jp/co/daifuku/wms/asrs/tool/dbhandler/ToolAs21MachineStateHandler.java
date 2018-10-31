// $Id: ToolAs21MachineStateHandler.java 7257 2010-02-26 05:59:12Z kanda $

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
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.communication.as21.As21MachineState;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;

/**<jp>
 * 環境設定ツール用の<code>As21MachineState</code>クラスをデータベースから取得したり、データベースに保管する為に利用するクラスです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7257 $, $Date: 2010-02-26 14:59:12 +0900 (金, 26 2 2010) $
 * @author  $Author: kanda $
 </jp>*/
/**<en>
 * This class is used to retrieve/store the <code>As21MachineState</code> class  
 * of environment setting tool from/to database.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7257 $, $Date: 2010-02-26 14:59:12 +0900 (金, 26 2 2010) $
 * @author  $Author: kanda $
 </en>*/
public class ToolAs21MachineStateHandler implements ToolDatabaseHandler
{

    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    Connection wConn ;
    Statement wStatement = null ;

    /**<jp> テーブル名 </jp>*/
    /**<en> name of the table </en>*/
    private String wTableName = "TEMP_DMMACHINE";

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------
    /**<jp>
     * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
     * @param conn データベース接続用 Connection
     </jp>*/
    /**<en>
     * Generate instance by specifying <code>Connection</code> to connect with database.
     * @param conn : to connect with database
     </en>*/
    public ToolAs21MachineStateHandler(Connection conn)
    {
        setConnection(conn) ;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * データベース接続用の<code>Connection</code>を設定します。
     * @param conn データベース接続用 Connection
     </jp>*/
    /**<en>
     * Set the <code>Connection</code> to connect with database.
     * @param conn :Connection to connect with database
     </en>*/
    public void setConnection(Connection conn)
    {
        wConn = conn ;
    }
    /**<jp>
     * データベース接続用の<code>Connection</code>を取得します。
     * @return データベース接続用 Connection
     </jp>*/
    /**<en>
     * Retrieve the <code>Connection</code> to connect with database.
     * @return :Connection to connect with database
     </en>*/
    public Connection getConnection()
    {
        return (wConn) ;
    }

    /**<jp>
     * テーブルのデータを全て削除します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Delete all data from the table.
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
     * 機器状態を検索し、取得します。
     * @param key 検索のためのKey。 
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search and retrieve the machine status.
     * @param key :Key for search
     * @return    :the array of the created object
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     </en>*/
    public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException 
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        ResultSet rset      = null;
        As21MachineState[] fndMS = null ;

        // for error message
        Object[] fmtObj = new Object[2] ;


        String fmtSQL = "SELECT * FROM " + wTableName + " {0} {1} ";

         try
         {
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

            String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
            
            // TODO debug
            System.out.println(sqlstring);

            // execute the sql (super)
            rset = executeSQL(sqlstring, true) ;

            // make As21MachineState instances from resultset
            // !!! makeAs21MachineState() is private method of this.
            fndMS = makeAs21MachineState(rset) ;
            
            return (fndMS) ;
        }
        catch (DataExistsException ee)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en> This should not happen;</en>
            ee.printStackTrace() ;
            throw (new ReadWriteException(ee)) ;
        }
        catch (NotFoundException e)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en> This should not happen;</en>
            e.printStackTrace() ;
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
     * 検索条件に一致する機器状態の件数を取得します。
     * @param key 検索のためのKey
     * @return 検索結果の件数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve the number of machine status data that match the search conditions.
     * @param key :Key for search
     * @return    :number of search results
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     </en>*/
    public int count(ToolSearchKey key) throws ReadWriteException
    {
        Statement stmt      = null;
        ResultSet rset      = null;
        int       count     = 0;
        Object[] fmtObj = new Object[1] ;

         try
         {
            stmt = wConn.createStatement();

            String fmtSQL = "SELECT COUNT(1) COUNT FROM " + wTableName + " {0} " ; 

            if (key.ReferenceCondition() != null)
            {
                fmtObj[0] = "WHERE " + key.ReferenceCondition();
            }
            String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
            rset = stmt.executeQuery(sqlstring) ;

            while (rset.next())
            {
                count = rset.getInt("count");
            }
            
            return count ;
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured. {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolAs21MachineStateHandler") ;
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException()) ;
            
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
                //<en>6126001 = Database error occured. {0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolAs21MachineStateHandler") ;
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException()) ;
            }
        }
    }

    /**<jp>
     * データベースに新規機器情報を作成します。
     * @param tgt 作成する機器情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws DataExistsException 既に、同じ機器状態がデータベースに登録済みの場合に通知されます。
     </jp>*/
    /**<en>
     * Newly create the new machine information in database.
     * @param tgt  :entity instance which preserves the machine information to create.
     * @throws ReadWriteException  :Notifies if error occured in connection with database. 
     * @throws DataExistsException :Notifies if the same machine status is already registered in database.
     </en>*/
    public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException
    {

        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        String fmtSQL = "INSERT INTO " + wTableName + " ("
                        // 0
                        + " STATION_NO"
                        // 1
                        + ", MACHINE_TYPE" 
                        // 2
                        + ", MACHINE_NO"
                        // 3
                        + ", STATUS_FLAG"
                        // 4
                        + ", ERROR_CODE"
                        // 5
                        + ", DEVICE_NAME"
                        // 6
                        + ", CONTROLLER_NO"
                        + ") VALUES ("
                        + "{0},{1},{2},{3},{4},{5},{6}"
                        + ")" ;

        As21MachineState tgtMS = null ;

        //-------------------------------------------------
        // process
        //-------------------------------------------------
        if (tgt instanceof As21MachineState)
        {
            tgtMS = (As21MachineState)tgt ;
        }
        else
        {
            RmiMsgLogClient.write(6126016, LogMessage.F_ERROR, "ToolAs21MachineStateHandler", null);
            throw (new ReadWriteException()) ;
        }
        Object [] fmtObj = setToMachine(tgtMS);
        // create actual sql
        String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;

        try
        {
            // execute the sql (super)
            executeSQL(sqlstring, false) ;
        }
        catch (NotFoundException e)
        {
            //<jp> Insertなので、起こらないはず</jp>
            //<en> This should not happen;</en>
            e.printStackTrace() ;
            throw (new ReadWriteException(e)) ;
        }
        catch (DataExistsException e)
        {
            //<jp> マシン登録時に失敗しました、データがすでに存在してます。</jp>
            //<en> Failed to register the machine. The data already exists.</en>
            e.printStackTrace() ;
            throw (new ReadWriteException(e)) ;
        }
    }

    /**<jp>
     * データベースの機器状態情報を変更します。
     * @param tgt 作成する機器状態情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 変更すべき機器が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the infornmation of machine status in database.
     * @param tgt :entity instance which preserves the machine information to create.
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     * @throws NotFoundException  :Notifies if the machine to modify cannot be found.
     </en>*/
    public void modify(ToolEntity tgt) throws ReadWriteException, NotFoundException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        String fmtSQL = "UPDATE " + wTableName + "SET "
                        + " STATION_NO = {0}"
                        + ", STATUS_FLAG        = {3}"
                        + ", ERROR_CODE    = {4}"
                        + " WHERE"
                        + " MACHINE_TYPE = {1} AND MACHINE_NO = {2} AND CONTROLLER_NO = {5}" ;

        As21MachineState tgtMS = null ;
        //-------------------------------------------------
        // process
        //-------------------------------------------------
        if (tgt instanceof As21MachineState)
        {
            tgtMS = (As21MachineState)tgt ;
        }
        else
        {
            RmiMsgLogClient.write(6126004, LogMessage.F_ERROR, "ToolAs21MachineStateHandler", null);
            throw (new ReadWriteException()) ;
        }
        Object [] fmtObj = setToMachine(tgtMS);
        // create actual sql
        String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;

        try
        {
            // execute the sql (super)
            executeSQL(sqlstring, false) ;
        }
        catch (DataExistsException e)
        {
            //<jp> Updateなので、起こらないはず</jp>
            //<en> This should not happen;</en>
            e.printStackTrace() ;
            throw (new ReadWriteException(e)) ;
        }
        catch (NotFoundException e)
        {
            //<jp> Insertなので、起こらないはず</jp>
            //<en> This should not happen;</en>
            e.printStackTrace() ;
            //<jp> 該当のマシンが存在しません。</jp>
            //<en> There is no corresponding machine.</en>
            throw new ReadWriteException() ;
        }
    }

    /**<jp>
     * データベースの機器状態情報を変更します。変更内容および変更条件はToolAlterKeyより獲得します。
     * @param  key 変更内容および変更条件を保持しているToolAlterKeyインスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException  変更対象がデータベースに見つからない場合に通知されます。
     * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
     * @throws InvalidDefineException 更新条件がセットされていない場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the information of machine status in database. The contents and conditions of modification 
     * will be retrieved by ToolAlterKey.
     * @param  key :ToolAlterKey instance which preservese the contents and conditions of modification
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     * @throws NotFoundException  :Notifies if target data of modification cannot be found in database.
     * @throws InvalidDefineException :Notifies if the contents of update has not been set.
     * @throws InvalidDefineException :Notifies if the conditions of update have not been set.
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
            //<en> Exception if update value has not been set;</en>
            Object[] tobj = {table};
            RmiMsgLogClient.write(6126006, LogMessage.F_ERROR, "ToolAs21MachineStateHandler", tobj);
            throw (new InvalidDefineException("6126006"));
        }
        fmtObj[1] = key.ModifyContents(table);

        if (key.ReferenceCondition(table) == null)
        {
            //<jp> 更新条件がセットされていない場合は例外</jp>
            //<en> Exception if update conditions have not been set;</en>
            Object[] tobj = {table};
            RmiMsgLogClient.write(6126005, LogMessage.F_ERROR, "ToolAs21MachineStateHandler", tobj);
            throw (new InvalidDefineException("6126005"));
        }
        fmtObj[2] = "WHERE " + key.ReferenceCondition(table);

        String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;

        try
        {
            // private exec sql method
            executeSQL(sqlstring, false) ;
        }
        catch (DataExistsException ee)
        {
            //<jp> updateなので、起こらないはず</jp>
            //<en> This should not happen;</en>
            ee.printStackTrace() ;
            throw (new ReadWriteException(ee)) ;
        }
        catch (NotFoundException e)
        {
            //<jp> Insertなので、起こらないはず</jp>
            //<en> This should not happen;</en>
            e.printStackTrace() ;
            //<jp> 該当のマシンが存在しません。</jp>
            //<en> There is no corresponding machine.</en>
            throw new ReadWriteException() ;
        }
    }

    /**<jp>
     * データベースから、パラメータで渡された機器状態の情報を削除します。
     * @param tgt 削除する機器状態情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 削除すべき機器状態が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete from database the information of machine status passed through parameter.
     * @param tgt :entity instance which has the machine status information to delete
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     * @throws NotFoundException  :Notifies if the machine status to delete cannot be found.
     </en>*/
    public void drop(ToolEntity tgt) throws ReadWriteException, NotFoundException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        String fmtSQL = "DELETE FROM " + wTableName
                        + " WHERE"
                        + " MACHINE_TYPE = {1} AND MACHINE_NO = {2} AND CONTROLLER_NO = {5}" ;

        As21MachineState tgtMS = null ;
        //-------------------------------------------------
        // process
        //-------------------------------------------------
        if (tgt instanceof As21MachineState)
        {
            tgtMS = (As21MachineState)tgt ;
        }
        else
        {
            //<jp> As21MachineStateのインスタンスではありません。</jp>
            //<en> This is not the instance of As21MachineState.</en>
            RmiMsgLogClient.write(6126016, LogMessage.F_ERROR, "ToolAs21MachineStateHandler", null);
            throw (new ReadWriteException()) ;
        }
        Object [] fmtObj = setToMachine(tgtMS);
        // create actual sql
        String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;

        try
        {
            // execute the sql (super)
            executeSQL(sqlstring, false) ;
        }
        catch (NotFoundException e)
        {
            //<jp> Deleteなので、起こらないはず</jp>
            //<en> This should not happen;</en>
            e.printStackTrace() ;
            //<jp> 該当のマシンが存在しません。</jp>
            //<en> There is no corresponding mathine.</en>
            throw new ReadWriteException() ;
        }
        catch (DataExistsException e)
        {
            //<jp> Deleteなので、起こらないはず</jp>
            //<en> This should not happen;</en>
            e.printStackTrace() ;
            //<jp> マシンがすでに存在するため処理できませんでした。</jp>
            //<en> Could not process; the machine already exists.</en>
            throw new ReadWriteException() ;
        }
    }
    /**<jp>
     * データベースから、パラメータで渡された機器状態の情報を削除します。
     * @param key 削除する機器状態情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 削除すべき機器状態が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete from database the information of machine status passed through parameter.
     * @param key :entity instance which has the machine status information to delete
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     * @throws NotFoundException  :Notifies if the machine status to delete cannot be found.
     </en>*/
    public void drop(ToolSearchKey key) throws ReadWriteException, NotFoundException
    {
        ToolEntity[] tgt = find(key) ;
        for (int i = 0; i < tgt.length; i++)
        {
            drop(tgt[i]) ;
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    protected void closeStatement(Statement stmt)
    {
        try
        {
            stmt.close() ;
        }
        catch (Exception e)
        {
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
     * Recieve and execute the SQL string.
     * @param sqlstr :SQL string to execute
     * @param query  :true if it is the query
     * @return :returns <code>ResultSet</code> of results, or returns null for all other cases
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     * @throws NotFoundException  :Notifies if the execution resulted no data (0).
     * @throws DataExistsException :Notifies if the unique restriction is broken at Insert.
     </en>*/
    protected ResultSet executeSQL(String sqlstr, boolean query) throws ReadWriteException, NotFoundException, DataExistsException
    {
        ResultSet rset = null ;
        try
        {
            //<jp> queryでfirst() で0行を見るためにはスクロールカーソルが必要</jp>
            //<en> A scroll cursor is required in order to see the line 0 by first() of query.</en>
            wStatement = wConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
                                        , ResultSet.CONCUR_READ_ONLY
                                    ) ;
            if (query)
            {
                // SELECT
                rset = wStatement.executeQuery(sqlstr);
            }
            else
            {
                // INSERT,UPDATE,DELETE
                int rrows = wStatement.executeUpdate(sqlstr);
                if (rrows == 0)
                {
                    RmiMsgLogClient.write(6126018, LogMessage.F_ERROR, "ToolAs21MachineStateHandler", null);
                    throw (new NotFoundException("6123001")) ;
                }
                wStatement.close();
            }
        }
        catch (SQLException e)
        {
            // Logging
            e.printStackTrace();
            
            if (e.getErrorCode() == ToolParam.DATAEXISTS)
            {
                //<jp> マシンがすでに存在するため処理できませんでした。</jp>
                //<en> Could not process; the machine already exists.</en>
                RmiMsgLogClient.write(6126017, LogMessage.F_ERROR, "ToolAs21MachineStateHandler", null);
                throw (new DataExistsException("6126017")) ;
            }
            else
            {
                //<jp> To get the message number </jp>
                //<en> To get the message number </en>
                RmiMsgLogClient.write(6126018, LogMessage.F_ERROR, "ToolAs21MachineStateHandler", null);
                throw (new ReadWriteException()) ;
            }
        }
        
        return rset ;
    }

    // Private methods -----------------------------------------------
    private Object[] setToMachine(As21MachineState mac)
    {
        Vector vec = new Vector();

        vec.addElement(DBFormat.format(mac.getStationNo()));
        vec.addElement(new Integer(mac.getType()));
        vec.addElement(new Integer(mac.getNumber()));
        vec.addElement(new Integer(mac.getState()));
        vec.addElement(DBFormat.format(mac.getErrorCode()));
        
        // DFKLOOK 20100222追加　
        vec.addElement(DBFormat.format(mac.getDeviceName()));
        
        vec.addElement(new Integer(mac.getControllerNumber()));

        Object[] obj = new Object[vec.size()];
        vec.copyInto(obj);
        return obj;
    }

    /**<jp>
     * <code>ResultSet</code>から、各項目を取り出して、<code>As21MachineState</code>インスタンスを生成します。
     * @param rset Result set
     * @throws ReadWriteException データベース接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve each item from <code>ResultSet</code> and generate the <code>As21MachineState</code>
     * instance .
     * @param rset Result set
     * @return As21MachineState Array
     * @throws ReadWriteException :Notifies if an error occurred in conneciton with database.
     </en>*/
    private As21MachineState[] makeAs21MachineState(ResultSet rset) throws ReadWriteException
    {
        // temporary store for As21MachineState instances
        Vector tmpMSVect = new Vector(10) ;
        As21MachineState tmpMS = null ;
        // data get from resultset and make new As21MachineState instance
        try
        {
            while (rset.next())
            {
                // append new As21MachineState instance to Vector
                tmpMS = new As21MachineState(0, 0) ;
                tmpMS.setStationNo(DBFormat.replace(rset.getString("STATION_NO"))) ;
                tmpMS.setType(rset.getInt("MACHINE_TYPE")) ;
                tmpMS.setNumber(rset.getInt("MACHINE_NO")) ;
                tmpMS.setState(rset.getInt("STATUS_FLAG")) ;
                tmpMS.setErrorCode(DBFormat.replace(rset.getString("ERROR_CODE"))) ;
                tmpMS.setControllerNumber(rset.getInt("CONTROLLER_NO")) ;
                
                // DFKLOOK 20100222追加
                tmpMS.setDeviceName(DBFormat.replace(rset.getString("DEVICE_NAME"))) ;
                
                tmpMS.setHandler(this) ;
                tmpMSVect.add(tmpMS) ;
            }
            
            As21MachineState[] array = new As21MachineState[tmpMSVect.size()];
            tmpMSVect.copyInto(array);
            
            return array ;
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured. {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "As21MachineStateHandler") ;
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException());
        }
    }
}
//end of class

