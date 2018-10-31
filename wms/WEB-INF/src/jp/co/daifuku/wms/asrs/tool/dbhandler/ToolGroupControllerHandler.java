// $Id: ToolGroupControllerHandler.java 87 2008-10-04 03:07:38Z admin $
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
import jp.co.daifuku.wms.asrs.tool.location.GroupControllerInformation;
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
 * GroupControllerの集合を操作するためのクラスです。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>P. Jain</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class operates the GroupControllergroup.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>P. Jain</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ToolGroupControllerHandler implements ToolDatabaseHandler
{
    // Class fields --------------------------------------------------

    public static final String GROUPCONTROLLER_HANDLE = "jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler";

    // Class variables -----------------------------------------------
    /**<jp> テーブル名 </jp>*/
    /**<en> name of the table </en>*/
    private String wTableName = "TEMP_DMGROUPCONTROLLER";
    
    /**<jp>
     * データベース接続用のConnectionインスタンス。
     * トランザクション管理は、このクラスの中では行わない。
     </jp>*/
    /**<en>
     * Connection instance to connect with database
     * Transaction control is not conducted in this class.
     </en>*/
    protected Connection wConn ;

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
    public ToolGroupControllerHandler(Connection conn)
    {
        setConnection(conn) ;
    }
    /**<jp>
     * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
     * @param conn データベース接続用 Connection
     * @param tableName テーブル名
     </jp>*/
    /**<en>
     * Generate instance by specifying <code>Connection</code> to connect with database.
     * @param conn :Connection to connect with database
     * @param tableName :name of the table
     </en>*/
    public ToolGroupControllerHandler(Connection conn, String tableName)
    {
        this(conn);
        wTableName = tableName;
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
            //<en>6126001 = Database error occured.{0}</en>
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
                //<en>6126001 = Database error occured.{0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName()) ;
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException()) ;
            }
        }

    }
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
     * GroupControllerを検索し、取得します。
     * @param key 検索のためのKey
     * @return 検索結果を元に生成されたGroupControllerインスタンスの配列。
     * @throws ReadWriteException 保管機構からの読み込みに失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Search and retireve the GroupController.
     * @param key :Key for search
     * @return :the array of GroupController instance generated based on the search result
     * @throws ReadWriteException :Notifies if it failed to load from the storage system. 
     </en>*/
    public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException
    {
        Statement stmt            = null;
        ResultSet rset            = null;
        GroupControllerInformation[]    groupcontrollerArray = null;
        Object[] fmtObj = new Object[2] ;

         try
         {
            stmt = wConn.createStatement();

            String fmtSQL = "SELECT * FROM " + wTableName + "{0} {1}";

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
            rset = stmt.executeQuery(sqlstring) ;

            groupcontrollerArray = convertGroupController(rset);

        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolGroupControllerHandler") ;
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
                //<en>6126001 = Database error occured.{0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolGroupControllerHandler") ;
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException(e)) ;
            }
        }
        return groupcontrollerArray;
    }

    /**<jp>
     * 指定されたKeyの条件に一致するデータの数を返します。
     * @param key 検索のためのKey
     * @return 検索結果の件数
     * @throws ReadWriteException 保管機構からの読み込みに失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Return the number of data that meets the conditions of specifeid key.
     * @param key :Key for search
     * @return :number of search results
     * @throws ReadWriteException :Notifies if it failed to load from the storage system. 
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
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolGroupControllerHandler") ;
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
                //<en>6126001 = Database error occured.{0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolGroupControllerHandler") ;
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException(e)) ;
            }
        }

        return count ;
    }

    /**<jp>
     * データベースに新規GroupController情報を作成します。
     * @param tgt 作成するGroupController情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException 保管機構からの読み込みに失敗した場合に通知されます。
     * @throws DataExistsException 既に、同じGroupControllerNumberがデータベースに登録済みの場合に通知されます。
     </jp>*/
    /**<en>
     * Newly create the GroupController information in database.
     * @param tgt :entity instance which preserves the GroupController information to create
     * @throws ReadWriteException  :Notifies if it failed to load from the storage system. 
     * @throws DataExistsException :Notifies if the same GroupControllerNumber is already registered
     * in database.
     </en>*/
    public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException
    {
        Statement stmt  = null;
        
        try
        {
            stmt = wConn.createStatement();
            GroupControllerInformation groupcontroller = (GroupControllerInformation)tgt;
            String sql = " INSERT INTO " + wTableName + " ("
                            + "CONTROLLER_NO"
                            + ", STATUS_FLAG"
                            + ", IPADDRESS"
                            + ", PORT"
                            + ") values ("
                            + "{0}, {1}, {2}, {3}"
                            + ")" ;
                                    
            String sqlstring = null;
            Object [] groupcontrollerObj = setToGroupController(groupcontroller) ;
            
            //<jp> GROUPCONTROLLER表に存在する場合</jp>
            //<en> If the data exists in GROUPCONTROLLER table.</en>
            if (isGroupControllerTable(wConn, groupcontroller)) 
            {
                //<jp> すでに存在する場合は登録不可</jp>
                //<en> Registration is not possible if the data already exists.</en>
                RmiMsgLogClient.write(6126008, LogMessage.F_ERROR, "ToolGroupControllerHandler", null);
                throw (new DataExistsException("6126008"));
            }
            else
            {
                sqlstring = SimpleFormat.format(sql, groupcontrollerObj) ;
                stmt.executeUpdate(sqlstring) ;
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolGroupControllerHandler") ;
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException(e)) ;
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolGroupControllerHandler") ;
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException(e)) ;
            }
        }
    }

    /**<jp>
     * データベースのGroupController情報を変更します。
     * @param tgt 作成するGroupController情報を持ったエンティティ・インスタンス
     * @throws NotFoundException 更新した結果、情報が見つからない場合に通知されます。
     * @throws ReadWriteException 保管機構からの読み込みに失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the GroupController information in database.
     * @param tgt :entity instance which preserves the GroupController information to create
     * @throws NotFoundException  :Notifies if information cannot be found as a result of update.
     * @throws ReadWriteException :Notifies if it failed to load from the storage system. 
     </en>*/
    public void modify(ToolEntity tgt) throws NotFoundException, ReadWriteException
    {

    }

    /**<jp>
     * データベースのGroupController情報を変更します。変更内容および変更条件はToolAlterKeyより獲得します。
     * @param  key 変更内容および変更条件を保持しているToolAlterKeyインスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 変更すべき情報が見つからない場合に通知されます。
     * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the GroupController information in database. The contents and conditions of the modification
     * will be obtained by ToolAlterKey.
     * @param  key :AlterKey isntance which preserves the contents and conditions of modification 
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if data to modify cannot be found.
     * @throws InvalidDefineException :Notifies if the contents of update has not been set.
     </en>*/
    public void modify(ToolAlterKey key) throws ReadWriteException, NotFoundException, InvalidDefineException
    {

    }

    /**<jp>
     * データベースから、パラメータで渡されたGroupControllerの情報を削除します。
     * @param tgt 削除するGroupController情報を持ったエンティティ・インスタンス
     * @throws NotFoundException 検索した結果、情報が見つからない場合に通知されます。
     * @throws ReadWriteException 保管機構からの読み込みに失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Delete from database the GroupController information passed through parameter.
     * @param tgt :entity instance which preserves the GroupController information to delete
     * @throws NotFoundException  :Notifies if data cannot be found as a result of search.
     * @throws ReadWriteException :Notifies if it failed to load from the storage system. 
     </en>*/
    public void drop(ToolEntity tgt) throws NotFoundException, ReadWriteException
    {
        Statement   stmt         = null;
        

        try
        {
            //<jp>tgtをCustomerクラスにキャスト</jp>
            //<en>Cast the tgt to Customer class.</en>
            GroupControllerInformation    groupcontroller = (GroupControllerInformation)tgt;
            stmt                 = wConn.createStatement();

            String sqlgroupcontroller = "DELETE FROM " + wTableName + " "
                                           + " WHERE CONTROLLER_NO = {0} ";

            String sqlstring = null;

            Object [] groupcontrollernumObj = setToGroupController(groupcontroller) ;

            sqlstring = SimpleFormat.format(sqlgroupcontroller, groupcontrollernumObj) ;
            int count = stmt.executeUpdate(sqlstring) ;
            if (count == 0)
            {
                Object[] tObj = new Object[2] ;
                tObj[0] = "CUSTOMER";
                RmiMsgLogClient.write(6126015, LogMessage.F_ERROR, "ToolGroupControllerHandler", tObj);
                throw (new NotFoundException("6126015" + wDelim + tObj[0]));
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolGroupControllerHandler") ;
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException(e)) ;
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolGroupControllerHandler") ;
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException(e)) ;
            }
        }

    }

    /**<jp>
     * データベースから、パラメータで渡されたキーに合致する情報を削除します。
     * @param key 削除する情報のキー
      * @throws NotFoundException 検索した結果、情報が見つからない場合に通知されます。
     * @throws ReadWriteException 保管機構からの読み込みに失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Delete from database the information that match the key passed through parameter.
     * @param key :key for the information to delete
      * @throws NotFoundException  :Notifies if information cannot be found as a result of search.
     * @throws ReadWriteException :Notifies if it failed to load from the storage system. 
     </en>*/
    public void drop(ToolSearchKey key) throws NotFoundException, ReadWriteException
    {
        //<jp> DBからDelete</jp>
        //<en> Delete from DB.</en>
        ToolEntity[] tgts = find(key) ;
        for (int i = 0 ; i < tgts.length ; i++)
        {
            drop(tgts[i]) ;
        }
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    /**<jp>
     * 結果セットをマッピング
     * @param rset <CODE>ResultSet</CODE> 検索結果
      * @throws ReadWriteException データベース接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Mapping of result set.
     * @param rset <CODE>ResultSet</CODE> :search result
      * @throws ReadWriteException :Notifies the exception itself that occurred in connection with database.
     </en>*/
    protected GroupControllerInformation[] convertGroupController(ResultSet rset) throws ReadWriteException
    {
        Vector vec = new Vector();
        GroupControllerInformation[] groupcontrollerArray = null;

        try
        {
            while (rset.next())
            {
                vec.addElement (new GroupControllerInformation(
                                           rset.getInt("controller_no"),
                                           rset.getInt("status_flag"),
                                           DBFormat.replace(rset.getString("ipaddress")),
                                           rset.getInt("port")
                                          )
                                );
            }

            groupcontrollerArray = new GroupControllerInformation[vec.size()];
            vec.copyInto(groupcontrollerArray);
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolGroupControllerHandler") ;
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException()) ;
        }
        return groupcontrollerArray;
    }

    // Private methods -----------------------------------------------
    /**<jp>
     * 取得したcustインスタンスの内容を元に、CUSTOMER表に対するDML文実行用の文字配列を生成します。
     * @param  cust 編集対象のCustomerインスタンス
     * @return SQL実行用の文字配列
     </jp>*/
    /**<en>
     * Based on the contents of retrieved cust instance, generate the string array to 
     * execute the DML string in CUSTOMER table.
     * @param  cust :Customer isntance to edit
     * @return :string array for SQL execution
     </en>*/
    private Object[] setToGroupController(GroupControllerInformation groupcontroller)
    {
        Vector vec = new Vector();

        vec.addElement(Integer.toString(groupcontroller.getControllerNo()));
        vec.addElement(Integer.toString(groupcontroller.getStatusFlag()));
        vec.addElement(DBFormat.format(groupcontroller.getIPAddress()));
        vec.addElement(Integer.toString(groupcontroller.getPort()));

        Object[] obj = new Object[vec.size()];
        vec.copyInto(obj);
        return obj;
    }
    /**<jp>
     * 指定されたgroupcontrollernumがデータベースのGROUPCONTROLLERテーブルに存在するかチェックを行います
     * @param conn データベース接続用 Connection
     * @param groupcontrollernum  検索対象CUSTOMER
     * @return     GROUPCONTROLLERテーブルの有無
     </jp>*/
    /**<en>
     * Check whether/not the specified groupcontrollernum exists in GROUPCONTROLLER table of database.
     * @param conn :Connection to connect with database
     * @param groupcontroller :CUSTOMER to be searched
     * @return     :whether/not the GROUPCONTROLLER table exists
     </en>*/
    private boolean isGroupControllerTable(Connection conn, GroupControllerInformation groupcontroller) throws ReadWriteException
    {
        Statement stmt = null;
        ResultSet rset = null;
        int count = 0;
        try
        {
            stmt = conn.createStatement();
            rset = stmt.executeQuery("SELECT COUNT(*) COUNT FROM " + wTableName + " WHERE CONTROLLER_NO = "
                                            + groupcontroller.getControllerNo() + "");
            while (rset.next())
            {
                count = rset.getInt("COUNT");
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>

            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolGroupControllerHandler") ;
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            //<jp>6126013 = {0}の検索に失敗しました。ログを参照して下さい。</jp>
            //<en>6126013 = Failed to search {0}. Please refer to the log.</en>

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
                //<en>6126001 = Database error occured.{0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolGroupControllerHandler") ;
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                //<jp>6126013 = {0}の検索に失敗しました。ログを参照して下さい。</jp>
                //<en>6126013 = Failed to search {0}. Please refer to the log.</en>
                throw (new ReadWriteException()) ;
            }
        }
        if (count == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
//end of class

