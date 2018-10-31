// $Id: ToolTerminalAreaHandler.java 87 2008-10-04 03:07:38Z admin $
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
import jp.co.daifuku.wms.asrs.tool.location.TerminalArea;
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
 * 端末エリア情報の集合を操作するためのクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/02/11</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class is used to operate the group of terminal area information.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/02/11</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class ToolTerminalAreaHandler implements ToolDatabaseHandler
{

    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp> テーブル名 </jp>*/
    /**<en> name of the table </en>*/
    private String wTableName = "TEMP_DMTERMINALAREA";

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
    public ToolTerminalAreaHandler(Connection conn)
    {
        setConnection(conn) ;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * テーブルのデータを全て削除します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Delete all data from the table.
     * @throws ReadWriteException Notifies if error occured in connection with database.
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
     * TerminalAreaを検索し、取得します。
     * @param key 検索のためのKey
     * @return 検索結果を元に生成されたTerminalAreaインスタンスの配列。
     * @throws ReadWriteException 保管機構からの読み込みに失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Search and retrienve the TerminalArea.
     * @param key :Key for teh search
     * @return :the array of the TerminalArea generated based on the search results
     * @throws ReadWriteException :Notifies if it failed to load from the storage system.
     </en>*/
    public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException
    {
        Statement stmt            = null;
        ResultSet rset            = null;
        TerminalArea[] terminalareaArray  = null;
        Object[] fmtObj = new Object[2] ;
         try
         {
            stmt = wConn.createStatement();

            String fmtSQL = "SELECT * FROM  " + wTableName + " {0} {1}";

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
            rset = stmt.executeQuery(sqlstring) ;

            terminalareaArray = convertTerminalArea(rset);
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "TerminalAreaHandler") ;
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "TerminalAreaHandler") ;
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException(e)) ;
            }
        }
        return terminalareaArray;
    }

    /**<jp>
     * 処理は実装されていません。
     * @param key 検索のためのKey
     * @return 検索結果の件数
     * @throws ReadWriteException 保管機構からの読み込みに失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * THe process is not implemented.
     * @param key :Key for search
     * @return :number of search results
     * @throws ReadWriteException :Notifies if it failed to load from the storage system.
     </en>*/
    public int count(ToolSearchKey key) throws ReadWriteException
    {
        return 0;
    }

    /**<jp>
     * データベースに新規TerminalArea情報を作成します。
     * @param tgt 作成するTerminalArea情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException 保管機構からの読み込みに失敗した場合に通知されます。
     * @throws DataExistsException 既に、同じCustomerがデータベースに登録済みの場合に通知されます。
     </jp>*/
    /**<en>
     * Create the new TerminalArea information in database.
     * @param tgt :entity instance which has the TerminalArea information to create
     * @throws ReadWriteException  :Notifies if it failed to load from the storage system.
     * @throws DataExistsException :Notifies if identical Customer was already registered in database.
     </en>*/
    public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException
    {
        Statement stmt  = null;
        
        try
        {
            stmt                 = wConn.createStatement();
            TerminalArea    terminalarea      = (TerminalArea)tgt;

            String sql =     " INSERT INTO  " + wTableName + " ("
                                + " STATION_NO,"
                                + " AREA_ID,"
                                + " TERMINAL_NO"
                                + "  ) VALUES ( {0}, {1}, {2} ) ";

            Object [] terminalareaObj = setToTerminalArea(terminalarea) ;

            String sqlstring = SimpleFormat.format(sql, terminalareaObj) ;
            stmt.executeUpdate(sqlstring) ;
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "TerminalAreaHandler") ;
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "TerminalAreaHandler") ;
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException(e)) ;
            }
        }
    }

    /**<jp>
     * データベースのTerminalArea情報を変更します。
     * @param tgt 作成するTerminalArea情報を持ったエンティティ・インスタンス
     * @throws NotFoundException 更新した結果、情報が見つからない場合に通知されます。
     * @throws ReadWriteException 保管機構からの読み込みに失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the TerminalArea information in database.
     * @param tgt :entity instance which has the TerminalArea information to modify
     * @throws NotFoundException  :Notifies if data cannot be found as a result of update.
     * @throws ReadWriteException :Notifies if it failed to load from the storage system.
     </en>*/
    public void modify(ToolEntity tgt) throws NotFoundException, ReadWriteException
    {
        Statement stmt        = null;
        
        try
        {
            //<jp>tgtをTerminalAreaクラスにキャスト</jp>
            //<en>Cast the tgt in TerminalArea class.</en>
            TerminalArea        terminalarea  = (TerminalArea)tgt;
    
            stmt = wConn.createStatement();

            String sql =     " UPDATE  " + wTableName + " SET"
                                + " STATION_NO = {0},"
                                + " AREA_ID = {1},"
                                + " TERMINAL_NO = {2}"
                                + " WHERE STATION_NO = {0} ";

            Object [] terminalareaObj = setToTerminalArea(terminalarea) ;

            String sqlstring = SimpleFormat.format(sql, terminalareaObj) ;
            int count = stmt.executeUpdate(sqlstring) ;
            if (count == 0)
            {
                Object[] tObj = new Object[1] ;
                tObj[0] = "TERMINALAREA";
                RmiMsgLogClient.write(6126014, LogMessage.F_ERROR, "TerminalAreaHandler", tObj);
                throw new NotFoundException("6126014" + wDelim + tObj[0]);
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "TerminalAreaHandler") ;
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "TerminalAreaHandler") ;
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException(e)) ;
            }
        }
    }

    /**<jp>
     * データベースのTerminalArea情報を変更します。TerminalArea表に対する更新はmodify(ToolEntity)を使用してください。
     * 本メソッドは実装されていません。
     * @param  key 変更内容および変更条件を保持しているToolAlterKeyインスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 変更すべき情報が見つからない場合に通知されます。
     * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the TerminalArea in database. Please use modify(ToolEntity) when updating
     * TerminalArea table.
     * This method is not implemented.
     * @param  key :ToolAlterKey instance which preserves the contents and conditions of the modification
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if the data to modify cannot be found.
     * @throws InvalidDefineException :Notifies if the contents of update has not been set.
     </en>*/
    public void modify(ToolAlterKey key) throws ReadWriteException, NotFoundException, InvalidDefineException
    {
    }

    /**<jp>
     * データベースから、パラメータで渡されたTerminalAreaの情報を削除します。
     * @param tgt 削除するTerminalArea情報を持ったエンティティ・インスタンス
     * @throws NotFoundException 検索した結果、情報が見つからない場合に通知されます。
     * @throws ReadWriteException 保管機構からの読み込みに失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Delete the TerminalArea data passed through parameter from database.
     * @param tgt :entity instance which has the TerminalArea informtion to delete
     * @throws NotFoundException  :Notifies if data cannot be found as a search result.
     * @throws ReadWriteException :Notifies if it failed to load from the storage system.
     </en>*/
    public void drop(ToolEntity tgt) throws NotFoundException, ReadWriteException
    {
        Statement   stmt         = null;
        
        try
        {
            //<jp>tgtをTerminalAreaクラスにキャスト</jp>
            //<en>Cast tgt to TerminalArea class.</en>
            TerminalArea        terminal = (TerminalArea)tgt;
            stmt                 = wConn.createStatement();

            String sql = "DELETE FROM  " + wTableName + " "
                            + " WHERE STATION_NO = {0} AND"
                                        +  " AREA_ID = {1} AND"
                                 + " TERMINAL_NO = {2} ";

            Object [] terminalObj = setToTerminalArea(terminal) ;

            String sqlstring = SimpleFormat.format(sql, terminalObj) ;

            int count = stmt.executeUpdate(sqlstring) ;
            if (count == 0)
            {
                Object[] tObj = new Object[1] ;
                tObj[0] = "TERMINALAREA";
                RmiMsgLogClient.write(6126015, LogMessage.F_ERROR, "TerminalAreaHandler", tObj);
                throw new NotFoundException("6126015" + wDelim + tObj[0]);
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "TerminalAreaHandler") ;
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "TerminalAreaHandler") ;
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
     * Delete from database the information that match the key which was passed through parameter.
     * @param key :key for the data to delete
     * @throws NotFoundException  :Notifies if the data cannot be found as a search result.
     * @throws ReadWriteException :Notifies if it failed to load from the storage system.
     </en>*/
    public void drop(ToolSearchKey key) throws ReadWriteException
    {
        Statement stmt            = null;
        Object[] fmtObj = new Object[2] ;
        try
        {
            stmt = wConn.createStatement();

            String fmtSQL = "DELETE FROM  " + wTableName + " {0}";

            if (key.ReferenceCondition() != null)
            {
                fmtObj[0] = "WHERE " + key.ReferenceCondition();
            }
            String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
            System.out.println(sqlstring);
            stmt.executeUpdate(sqlstring) ;

        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "TerminalAreaHandler") ;
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "TerminalAreaHandler") ;
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException(e)) ;
            }
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 結果セットをマッピング
     * @param rset <CODE>ResultSet</CODE> 検索結果
      * @throws ReadWriteException データベース接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Mapping of the result set.
     * @param rset <CODE>ResultSet</CODE> search result
      * @throws ReadWriteException :Notifies the exception itself that occurred in connection with database.
     </en>*/
    protected TerminalArea[] convertTerminalArea(ResultSet rset) throws ReadWriteException
    {
        Vector vec = new Vector();
        TerminalArea[] terminalAreaArray = null;
        try 
        {
            while (rset.next())
            {
                vec.addElement (new TerminalArea(
                                           DBFormat.replace(rset.getString("STATION_NO")),
                                           rset.getInt("AREA_ID"),
                                           DBFormat.replace(rset.getString("TERMINAL_NO"))
                                          ));
            }
            terminalAreaArray = new TerminalArea[vec.size()];
            vec.copyInto(terminalAreaArray);
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "TerminalAreaHandler") ;
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException(e)) ;
        }
        return terminalAreaArray;
    }

    // Private methods -----------------------------------------------
    /**<jp>
     * 取得したTerminalAreaインスタンスの内容を元に、TERMINALAREA表に対するDML文実行用の文字配列を生成します。
     * @param  TerminalArea 編集対象のTerminalAreaインスタンス
     * @return SQL実行用の文字配列
     </jp>*/
    /**<en>
     * Based on the contents of TerminalArea isntance retrieved, generate the string array with which 
     * to run DML string in TERMINALAREA table.
     * @param  TerminalArea :TerminalArea isntance to edit
     * @return :string array to run SQL
     </en>*/
    private Object[] setToTerminalArea(TerminalArea ta)
    {
        Vector vec = new Vector();

        vec.addElement(DBFormat.format(ta.getStationNo()));
        vec.addElement(DBFormat.format(Integer.toString(ta.getAreaId())));
        vec.addElement(DBFormat.format(ta.getTerminalNo()));


        Object[] obj = new Object[vec.size()];
        vec.copyInto(obj);
        return obj;
    }
}
//end of class

