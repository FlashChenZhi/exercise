//$Id: ToolStationTypeHandler.java 87 2008-10-04 03:07:38Z admin $

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
import jp.co.daifuku.wms.asrs.tool.location.StationType;
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
 * <code>StationType</code>クラスをデータベースから取得したり、データベースに保管する為に利用するクラスです。
 * <code>StationType</code>のサブクラスに関しては、それぞれにHandlerが必要となります。
 * <code>StationType</code>およびそのサブクラスの取得に関しては、<code>StationFactory</code>を利用してください。
 * <code>StationType</code>およびそのサブクラスには<code>getHandler</code>メソッドが用意されていますので、
 * 対応するHandlerが不明な場合は、<code>getHandler</code>メソッドを利用して取得します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/09</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class is used to retrieve/store the <code>StationType</code> class from/to database.
 * As for the subclass of <code>StationType</code>, Handler will be requried respectively.
 * Please use <code>StationFactory</code> to retrieve <code>StationType</code> and the subclasses.
 * As <code>getHandler</code> method has been prepared for <code>StationType</code> and the subclasses,
 * in case the Handler to support is unknown, retrieve one by using <code>getHandler</code> mwthod.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/09</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ToolStationTypeHandler implements ToolDatabaseHandler
{

    // Class fields --------------------------------------------------
    
    // Class variables -----------------------------------------------
    /**<jp> テーブル名 </jp>*/
    /**<en> name of the table </en>*/
    private String wTableName = "TEMP_DMSTATIONTYPE";

    /**<jp>
     * データベース接続用のConnectionインスタンス。
     * トランザクション管理は、このクラスの中では行わない。
     </jp>*/
    /**<en>
     * Connection instance to connect with database
     * Transaction control is not conducted in this class.
     </en>*/
    protected Connection wConn ;

    /**<jp>
     * ステートメントを管理する変数。
     </jp>*/
    /**<en>
     * Variables which control the statements.
     </en>*/
    protected Statement wStatement = null ;

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
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
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
    public ToolStationTypeHandler(Connection conn)
    {
        setConnection(conn) ;
    }
    
    /**<jp>
     * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
     * @param conn データベース接続用 Connection
     * @param tablename テーブル名
     </jp>*/
    /**<en>
     * Generate instance by specifying <code>Connection</code> to connect with database.
     * @param conn :Connection connect with database
     * @param tablename :name of the table
     </en>*/
    public ToolStationTypeHandler(Connection conn, String tablename)
    {
        setConnection(conn) ;
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
        wConn = conn ;
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
        return (wConn) ;
    }

    /**<jp>
     * ステーションタイプを検索します。検索キーは、<code>StationSearchKey</code>である必要があります。
     * @param key 検索のためのKey
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search the station type.  <code>StationSearchKey</code> should be used for a search key.
     * @param key :Key for the search
     * @return :the array of the created object
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     </en>*/
    public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        StationType[] fndStationType = null ;
        Object[]  fmtObj = new Object[2] ;

        // for database access
        ResultSet rset = null ;

        String fmtSQL = "SELECT * FROM " + wTableName + " {0} {1}" ;

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

        try
        {
            rset = executeSQL(sqlstring, true) ;    // private exec sql method
            
            // make StationType instances from resultset
            // !!! makeStationType() is private method of this.
            fndStationType = makeStationType(rset) ;
        }
        catch (NotFoundException e)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en> This should not occur;</en>

            e.printStackTrace() ;
            throw (new ReadWriteException(e)) ;
        }
        catch (DataExistsException ee)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en> This should not occur;</en>

            ee.printStackTrace() ;
            throw (new ReadWriteException(ee)) ;
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
                
                closeStatement() ;
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

        return fndStationType;
    }

    /**<jp>
     * データベースから、パラメータに基づいて情報を検索し、結果の件数（StationTypeのデータ件数）を返します。
     * @param key 検索のためのKey
     * @return 検索結果の件数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search in database the information based on parameter and return the number of results
     * (number of StationType data).
     * @param key :Key for search
     * @return :number of search results
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

            String fmtSQL = "SELECT COUNT(1) COUNT FROM " + wTableName + " {0}" ;

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
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured. {0}</en>

            RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName()) ;
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>

            throw (new ReadWriteException(e)) ;
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

                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName()) ;
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>

                throw (new ReadWriteException(e)) ;
            }
        }

        return count ;
    }

    /**<jp>
     * データベースに新規情報を作成します。
     * @param tgt 作成する情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws DataExistsException 既に、同じ情報がデータベースに登録済みの場合に通知されます。
     </jp>*/
    /**<en>
     * Create the new information in database.
     * @param tgt :entity instance which has the information to create
     * @throws ReadWriteException  :Notifies if error occured in connection with database. 
     * @throws DataExistsException :Notifies if database already has the same data registered.
     </en>*/
    public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException
    {
    }

    /**<jp>
     * データベースの情報を変更します。
     * @param tgt 変更する情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 変更すべき情報が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the information in database.
     * @param tgt :entity instance which has the information to modify
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     * @throws NotFoundException  :Notifies if data to modify cannot be found.
     </en>*/
    public void modify(ToolEntity tgt) throws ReadWriteException, NotFoundException
    {
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
     * @param  key :ToolAlterKey instance which preserves the contents and conditions of the modification
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     * @throws NotFoundException  :Notifies when data to updata cannot be found in database.
     * @throws InvalidDefineException :Notifies if the contents of update has not been set.
     </en>*/
    public void modify(ToolAlterKey key) throws ReadWriteException, NotFoundException, InvalidDefineException
    {
    }

    /**<jp>
     * データベースから、パラメータで渡されたエンティティ・インスタンスの情報を削除します。
     * @param tgt 削除する情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 削除すべき情報が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete from database the information of entity instance which has been passed through parameter.
     * @param tgt :entity instance which has the informtion to delete
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     * @throws NotFoundException  :Notifies if data to delete cannot be found.
     </en>*/
    public void drop(ToolEntity tgt) throws ReadWriteException, NotFoundException
    {
    }

    /**<jp>
     * データベースから、パラメータで渡されたキーに合致する情報を削除します。
     * @param key 削除する情報のキー
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 削除すべき情報が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete from database the information of entity instance which has been passed through parameter.
     * @param key :key of the data to delete
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     * @throws NotFoundException  :Notifies if data to delete cannot be found.
     </en>*/
    public void drop(ToolSearchKey key) throws ReadWriteException, NotFoundException
    {
    }
    
    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * インスタンス変数であるwStatementをクローズします。
     * executeSQLメソッドで生成されたカーソルはこのメソッドを呼び出し、必ずクローズする必要があります。
     </jp>*/
    /**<en>
     * Close the wStatement which is the instance variable.
     * It is necessary that the cursor generated by executeSQL method should be closed.
     </en>*/
    protected void closeStatement()
    {
        try
        {
            if (wStatement != null)
            {
                wStatement.close() ;
            }
            wStatement = null ;
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured. {0}</en>

            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolStationTypeHandler") ;
        }
        catch (NullPointerException e)
        {
            //<jp>エラーログの出力処理も行う。</jp>
            //<en>Also carry out the output of error log.</en>

            Object[] tObj = new Object[1] ;
            if (wStatement != null)
            {
                tObj[0] = wStatement.toString() ;
            }
            else
            {
                tObj[0] = "null" ;
            }
            //<jp>6126007 = カーソルのクローズが出来ませんでした。Statement=[{0}]</jp>
            //<en>6126007 = Could not close the cursor. Statement=[{0}]</en>

            RmiMsgLogClient.write(6126007, LogMessage.F_ERROR, "ToolStationTypeHandler", tObj) ;
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
     * Receive and execute the SQL string.
     * @param sqlstr :SQL string to execute
     * @param query :true if it is a query
     * @return :<code>ResultSet</code> of the results, or null for all other cases
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     * @throws NotFoundException  :Notifies if result of the exection was 0.
     * @throws DataExistsException :If the unique restriction is broken at Insert.
     </en>*/
    protected ResultSet executeSQL(String sqlstr, boolean query) throws ReadWriteException, NotFoundException, DataExistsException
    {
        ResultSet rset = null ;
        try
        {
            //<jp> queryでfirst() で0行を見るためにはスクロールカーソルが必要</jp>
            //<en> A scroll cursor will be requried in order to view line 0 by first() of the query.</en>

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
                    //<jp>6123001 = 対象データはありませんでした</jp>
                    //<en>6123001 = No target data was found.</en>

                    throw (new NotFoundException("6123001")) ;
                }
                closeStatement() ;
            }
        }
        catch (SQLException e)
        {
            if (e.getErrorCode() == ToolParam.DATAEXISTS)
            {
                //<jp>6126008 = 既に同一データが存在するため、登録できません。</jp>
                //<en>6126008 = Cannot registrate; the identical data already exists.</en>

                RmiMsgLogClient.write(6126008, LogMessage.F_ERROR, "ToolStationTypeHandler", null);
                throw (new DataExistsException("6126008")) ;
            }
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured. {0}</en>

            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolStationTypeHandler") ;

            //<jp> データベースエラーが発生しました。</jp>
            //<en> Database error occured. </en>

            //String msg = "6126001" ; 
            throw (new ReadWriteException()) ;
        }
        return (rset) ;
    }

    /**<jp>
     * <code>ResultSet</code>から、各項目を取り出して、<code>StationType</code>インスタンスを生成します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve each item from <code>ResultSet</code> and generate the <code>StationType</code> instance.
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     </en>*/
    protected StationType[] makeStationType(ResultSet rset) throws ReadWriteException
    {
        Vector tmpStVect = new Vector(20) ;    // temporary store for StationType instances
        StationType tmpst = null;
        // data get from resultset and make new StationType instance
        try
        {
            while (rset.next())
            {

                tmpst = new StationType() ;
                //<jp> TEMP_STATION表のクラス名でインスタンスを生成しても意味無いのでコメントに</jp>
                //<en>No meaning of instantiation with the class name for TEMP_STATION table. See comment.</en>

                //<jp> ステーションNo.</jp>
                //<en> station no.</en>

                tmpst.setStationNo(DBFormat.replace(rset.getString("STATION_NO"))) ;
                //<jp> ハンドラクラス</jp>
                //<en> handler class</en>

                tmpst.setClassName(DBFormat.replace(rset.getString("CLASS_NAME"))) ;

                // station handler
                tmpst.setHandler(this) ;

                // append new Station instance to Vector
                tmpStVect.add(tmpst) ;
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured. {0}</en>

            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolStationTypeHandler") ;
            throw (new ReadWriteException(e)) ;
        }
        catch (Exception e)
        {
            if (e instanceof ReadWriteException)
            {
                throw (ReadWriteException)e;
            }
            e.printStackTrace(wPW) ;
            Object[] tObj = new Object[2] ;
            tObj[0] = "StationType" ;
            tObj[1] = wSW.toString() ;
            //<jp>6126003 = インスタンスの生成に失敗しました。クラス名={0} {1}</jp>
            //<en>6126003 = Failed to generate the instance. class name={0} {1}</en>

            RmiMsgLogClient.write(6126003, LogMessage.F_ERROR, "ToolStationTypeHandler", tObj);
            throw (new ReadWriteException(e)) ;
        }

        // move instance from vector to array of StationType
        StationType[] rstarr = new StationType[tmpStVect.size()] ;
        tmpStVect.copyInto(rstarr);

        return (rstarr) ;
    }

    // Private methods -----------------------------------------------
}
//end of class

