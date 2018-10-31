// $Id: ToolBankHandler.java 87 2008-10-04 03:07:38Z admin $
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
import jp.co.daifuku.wms.asrs.tool.location.Bank;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;

/**<jp>
 * バンク情報(Bank)の集合を操作するためのクラスです。
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
 * This class operates the Bank information (Bank) group.
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
public class ToolBankHandler implements ToolDatabaseHandler
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp> テーブル名 </jp>*/
    /**<en> name of the table </en>*/
    private String wTableName = "TEMP_DMBANKSELECT";
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
    public ToolBankHandler(Connection conn)
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
     * @param conn    :Connection to connect with database
     * @param tablename :name of the table
     </en>*/
    public ToolBankHandler(Connection conn, String tablename)
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
     * Bankを検索し、取得します。
     * @param key 検索のためのKey
     * @return 検索結果を元に生成されたBankインスタンスの配列。
     * @throws ReadWriteException 保管機構からの読み込みに失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Search and retrieve Bank.
     * @param key :Key for search
     * @return :the array of Bank instance generated based on the search result
     * @throws ReadWriteException :Notifies if it failed to loadi from the storate system.
     </en>*/
    public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException
    {
        Statement stmt = null;
        ResultSet rset = null;
        Object[]  fmtObj = new Object[2] ;
        Bank[] bankArray = null;

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
            stmt = wConn.createStatement();
            rset = stmt.executeQuery(sqlstring) ;
            bankArray = convertBank(rset) ;
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolZoneHandler") ;
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolZoneHandler") ;
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }
        return bankArray;
    }

    /**<jp>
     * 指定されたKeyの条件に一致するデータの数を返します。
     * @param key 検索のためのKey
     * @return 検索結果の件数
     * @throws ReadWriteException 保管機構からの読み込みに失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Return the number of data that meets the conditions of specified Key.
     * @param key :Key for search
     * @return    :number of search result
     * @throws ReadWriteException :Notifies if it failed to loadi from the storate system.
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
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolZoneHandler") ;
            //<jp>ここで、ReadWriteExceptionをthrowする。</jp>
            //<en>Throw the ReadWriteException here.</en>
            throw new ReadWriteException() ;
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolZoneHandler") ;
                //<jp>ここで、ReadWriteExceptionをthrowする。</jp>
                //<en>Throw the ReadWriteException here.</en>
                throw new ReadWriteException() ;
            }
        }

        return count ;
    }

    /**<jp>
     * データベースに新規Bank情報を作成します。
     * 登録の必要はないので、実装されていません。
     * @param tgt 作成するBank情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException 保管機構からの読み込みに失敗した場合に通知されます。
     * @throws DataExistsException 既に、同じBankがデータベースに登録済みの場合に通知されます。
     </jp>*/
    /**<en>
     * Newly create the Bank information in database.
     * This has not been implemented due to no need of registration.
     * @param tgt :entity instance which preserves the Bank information to create.
     * @throws ReadWriteException  :Notifies if it failed to load from the storage system.
     * @throws DataExistsException :Notifies if the same Bank is already registered in database.
     </en>*/
    public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException
    {
        Statement stmt  = null;
        
        try
        {
            stmt = wConn.createStatement();
            Bank bank = (Bank)tgt;

            String sql =     " INSERT INTO  " + wTableName + " ("
                                + " WH_STATION_NO,"
                                + " AISLE_STATION_NO,"
                                + " BANK_NO,"
                                + " PAIR_BANK,"
                                + " SIDE "
                                + "  ) VALUES ( {0}, {1}, {2}, {3}, {4} ) ";

            Object [] bankObj = setToBank(bank) ;

            String sqlstring = SimpleFormat.format(sql, bankObj) ;
            stmt.executeUpdate(sqlstring) ;
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
                throw (new ReadWriteException()) ;
            }
        }
    }
    

    /**<jp>
     * データベースにある情報を、引数で渡されたエンティティ情報に変更します。
     * @param tgt 変更する情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 変更すべき情報が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the information in database to the entity information passed through parameter.
     * @param tgt :entity instance which preserves information to modify
     * @throws ReadWriteException :Notifies if error occured in connection with database.  
     * @throws NotFoundException  :Notifies if data to modify cannot be found.
     </en>*/
    public void modify(ToolEntity tgt) throws ReadWriteException, NotFoundException
    {
    }
    /**<jp>
     * データベースの情報を変更します。変更内容および変更条件はToolSearchKeyより獲得します。
     * @param key 変更する情報を持ったAlterKeyインスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 変更すべき情報が見つからない場合に通知されます。
     * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the information in database. The contens and conditions of the modification
     * will be obtained by ToolSearchKey.
     * @param key :AlterKey instance which preserves the information to modify
     * @throws ReadWriteException :Notifies if error occured in connection with database.  
     * @throws NotFoundException  :Notifies if data to modify cannot be found.
     * @throws InvalidDefineException :Notifies if contents of update has not been set.
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
     * Delete from database the information of entity instance passed through parameter.
     * @param tgt :entity instance which preserves the information to delete
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
     * Delete from database the information that match the key passed through parameter.
     * @param key :key for the information to delete
     * @throws ReadWriteException :Notifies if error occured in connection with database.  
     * @throws NotFoundException  :Notifies if data to delete cannot be found.
     </en>*/
    public void drop(ToolSearchKey key) throws ReadWriteException, NotFoundException
    {
    }



    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------


    /**<jp>
     * 結果セットをマッピング
     * @param rset <CODE>ResultSet</CODE> 検索結果
      * @throws ReadWriteException データベース接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Mapping of result set:
     * @param rset <CODE>ResultSet</CODE> :result of search
      * @throws ReadWriteException :Notifies the exception itself that occurred in connection with database.
     </en>*/
    protected Bank[] convertBank(ResultSet rset) throws ReadWriteException
    {
        Vector vec = new Vector();
        Bank[] bankArray = null;

        try
        {
            while (rset.next())
            {
                vec.addElement (new Bank(
                                           DBFormat.replace(rset.getString("WH_STATION_NO")),
                                           DBFormat.replace(rset.getString("AISLE_STATION_NO")),
                                           rset.getInt("BANK_NO"),
                                           rset.getInt("PAIR_BANK"),
                                           rset.getInt("SIDE")
                                          )
                                );
            }

            bankArray = new Bank[vec.size()];
            vec.copyInto(bankArray);
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "BankHandler") ;
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException()) ;
        }
        return bankArray;
    }

    // Private methods -----------------------------------------------
    /**<jp>
     * 取得したcustインスタンスの内容を元に、CUSTOMER表に対するDML文実行用の文字配列を生成します。
     * @param  cust 編集対象のCustomerインスタンス
     * @return SQL実行用の文字配列
     </jp>*/
    /**<en>
     * Based on the contents of retrieved cust instance, it generates the string array for 
     * DML string to execute in CUSTOMER table.
     * @param  cust :Customer instance to edit
     * @return :string array to execute SQL
     </en>*/
    private Object[] setToBank(Bank bank)
    {
        Vector vec = new Vector();

        vec.addElement(DBFormat.format(bank.getWhStationNo()));
        vec.addElement(DBFormat.format(bank.getAisleStationNo()));
        vec.addElement(new Integer(bank.getBankNo()));
        vec.addElement(new Integer(bank.getPairBank()));
        vec.addElement(new Integer(bank.getSide()));

        Object[] obj = new Object[vec.size()];
        vec.copyInto(obj);
        return obj;
    }
}
//end of class

