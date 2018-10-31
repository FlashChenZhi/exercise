// $Id: ToolWmsAreaHandler.java 87 2008-10-04 03:07:38Z admin $
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
import jp.co.daifuku.wms.asrs.tool.location.Area;
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
 * エリア情報(Area)を操作するためのクラスです。
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
public class ToolWmsAreaHandler implements ToolDatabaseHandler
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp> テーブル名 </jp>*/
    private String wTableName = "DMAREA";
    /**<jp>
     * データベース接続用のConnectionインスタンス。
     * トランザクション管理は、このクラスの中では行わない。
     </jp>*/
    protected Connection wConn ;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
     * @param conn データベース接続用 Connection
     </jp>*/
    public ToolWmsAreaHandler(Connection conn)
    {
        setConnection(conn) ;
    }
    /**<jp>
     * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
     * @param conn データベース接続用 Connection
     * @param tablename テーブル名
     </jp>*/
    public ToolWmsAreaHandler(Connection conn, String tablename)
    {
        setConnection(conn) ;
        wTableName = tablename;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * データベース接続用の<code>Connection</code>を設定します。
     * @param conn 設定するConnection
     </jp>*/
    public void setConnection(Connection conn)
    {
        wConn = conn ;
    }

    /**<jp>
     * データベース接続用の<code>Connection</code>を取得します。
     * @return 現在保持している<code>Connection</code>
     </jp>*/
    public Connection getConnection()
    {
        return (wConn) ;
    }

    /**
     * テーブル名に設定します
     * 
     * @param tablename テーブル名
     */
    public void setTableName(String tablename)
    {
        wTableName = tablename;
    }

    /**
     * 指定されたAreaがデータベースのAreaテーブルに存在するかチェックを行います。<br>
     * @param conn データベース接続用 Connection
     * @param area 検索対象 Area
     * 
     * @return Area情報の有無
     * 
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     * 
     */
    public boolean isAreaTable(Connection conn, Area area)
        throws ReadWriteException
    {
        Statement stmt = null;
        ResultSet rset = null;
        Object[] fmtObj = new Object[1];
        int count = 0;
        String fmtSQL = "SELECT COUNT(*) COUNT FROM " + wTableName + " WHERE AREA_NO = {0}";

        fmtObj[0] = DBFormat.format(area.getAreaNo());
        String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);

        try
        {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sqlstring);
            while (rset.next())
            {
                count = rset.getInt("COUNT");
            }
        }
        catch (SQLException e)
        {
            //6006002 = データベースエラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006002, e), "ToolAreaHandler");
            //ここで、ReadWriteExceptionをthrowする。
            throw (new ReadWriteException(e));
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
                if (stmt != null)
                {
                    stmt.close();
                    stmt = null;
                }
            }
            catch (SQLException e)
            {
                //6006002 = データベースエラーが発生しました。{0}
                RmiMsgLogClient.write(new TraceHandler(6006002, e), "ToolAreaHandler");
                //ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。

                throw (new ReadWriteException(e));
            }
        }
        // データが存在する場合はtrue、存在しない場合はfalseを返す
        if (count == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**<jp>
     * テーブルのデータを全て削除します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
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
            RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName()) ;
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName()) ;
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                throw (new ReadWriteException()) ;
            }
        }

    }

    /**<jp>
     * Areaを検索し、取得します。
     * @param key 検索のためのKey
     * @return 検索結果を元に生成されたAreaインスタンスの配列。
     * @throws ReadWriteException 保管機構からの読み込みに失敗した場合に通知されます。
     </jp>*/
    public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException
    {
        Statement stmt = null;
        ResultSet rset = null;
        Object[]  fmtObj = new Object[2] ;
        Area[] bankArray = null;

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
            bankArray = convertArea(rset) ;
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolAreaHandler") ;
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolAreaHandler") ;
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
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
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolAreaHandler") ;
            //<jp>ここで、ReadWriteExceptionをthrowする。</jp>
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolAreaHandler") ;
                //<jp>ここで、ReadWriteExceptionをthrowする。</jp>
                throw new ReadWriteException() ;
            }
        }

        return count ;
    }

    /**<jp>
     * データベースに新規Area情報を作成します。
     * 登録の必要はないので、実装されていません。
     * @param tgt 作成するArea情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException 保管機構からの読み込みに失敗した場合に通知されます。
     * @throws DataExistsException 既に、同じAreaがデータベースに登録済みの場合に通知されます。
     </jp>*/
    public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------

        String fmtSQL = "INSERT INTO " + wTableName + " ("
                        + "  MANAGEMENT_TYPE"           // 0
                        + ", AREA_NO"                   // 1
                        + ", AREA_NAME"                 // 2
                        + ", AREA_TYPE"                 // 3
                        + ", LOCATION_TYPE"             // 4
                        + ", LOCATION_STYLE"            // 5
                        + ", TEMPORARY_AREA_TYPE"       // 6
                        + ", TEMPORARY_AREA"            // 7
                        + ", VACANT_SEARCH_TYPE"        // 8
                        + ", WHSTATION_NO"              // 9
                        + ", REGIST_DATE"               // 10
                        + ", REGIST_PNAME"              // 11
                        + ", LAST_UPDATE_DATE"          // 12
                        + ", LAST_UPDATE_PNAME"         // 13
                        + ") values ("
                        + "{0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12},{13}"
                        + ")" ;

        Statement stmt = null;

        try
        {
            stmt = wConn.createStatement();

            String sqlstring = null;
            Object [] arearObj = setToArea((Area)tgt) ;
            
            //<jp> DMAREA表に存在する場合</jp>
            //<en> If the data exists in DMAREA table.</en>
            if (isAreaTable(wConn, (Area)tgt)) 
            {
                //<jp> すでに存在する場合は登録不可</jp>
                //<en> Registration is not possible if the data already exists.</en>
                RmiMsgLogClient.write(6126008, LogMessage.F_ERROR, "ToolAreaHandler", null);
                throw (new DataExistsException("6126008"));
            }
            else
            {
                sqlstring = SimpleFormat.format(fmtSQL, arearObj) ;
                stmt.executeUpdate(sqlstring) ;
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolAreaHandler") ;
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolAreaHandler") ;
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException(e)) ;
            }
        }
    }
    

    /**<jp>
     * データベースにある情報を、引数で渡されたエンティティ情報に変更します。
     * 登録の必要はないので、実装されていません。
     * @param tgt 変更する情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 変更すべき情報が見つからない場合に通知されます。
     </jp>*/
    public void modify(ToolEntity tgt) throws ReadWriteException, NotFoundException
    {
    }
    /**<jp>
     * データベースの情報を変更します。変更内容および変更条件はToolSearchKeyより獲得します。
     * 登録の必要はないので、実装されていません。
     * @param key 変更する情報を持ったAlterKeyインスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 変更すべき情報が見つからない場合に通知されます。
     * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
     </jp>*/
    public void modify(ToolAlterKey key) throws ReadWriteException, NotFoundException, InvalidDefineException
    {
    }


    /**<jp>
     * データベースから、パラメータで渡されたエンティティ・インスタンスの情報を削除します。
     * 登録の必要はないので、実装されていません。
     * @param tgt 削除する情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 削除すべき情報が見つからない場合に通知されます。
     </jp>*/
    public void drop(ToolEntity tgt) throws ReadWriteException, NotFoundException
    {
        Statement stmt = null;

        try
        {
            //<jp>tgtをAreaクラスにキャスト</jp>
            //<en>Cast the tgt to Area class.</en>
            Area area = (Area)tgt;
            stmt = wConn.createStatement();

            String sqlarea = "DELETE FROM " + wTableName + " WHERE AREA_NO = {0}";

            String sqlstring = null;

            Object [] areaObj = setToArea(area) ;

            sqlstring = SimpleFormat.format(sqlarea, areaObj) ;
            int count = stmt.executeUpdate(sqlstring) ;
            if (count == 0)
            {
                Object[] tObj = new Object[2] ;
                tObj[0] = wTableName;
                RmiMsgLogClient.write(6126015, LogMessage.F_ERROR, "ToolAreaHandler", tObj);
                throw (new NotFoundException("6126015" + wDelim + tObj[0]));
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolAreaHandler") ;
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolAreaHandler") ;
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException(e)) ;
            }
        }
    }

    /**<jp>
     * データベースから、パラメータで渡されたキーに合致する情報を削除します。
     * 登録の必要はないので、実装されていません。
     * @param key 削除する情報のキー
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 削除すべき情報が見つからない場合に通知されます。
     </jp>*/
    public void drop(ToolSearchKey key) throws ReadWriteException, NotFoundException
    {
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    /**<jp>
     * 結果セットをマッピング
     * @param rset <CODE>ResultSet</CODE> 検索結果
     * @return エリアインスタンス
      * @throws ReadWriteException データベース接続で発生した例外をそのまま通知します。
     </jp>*/
    protected Area[] convertArea(ResultSet rset) throws ReadWriteException
    {
        Vector vec = new Vector();
        Area[] array = null;

        try
        {
            while (rset.next())
            {
                vec.addElement (new Area(
                                           DBFormat.replace(rset.getString("management_type")),
                                           DBFormat.replace(rset.getString("area_no")),
                                           DBFormat.replace(rset.getString("area_name")),
                                           DBFormat.replace(rset.getString("area_type")),
                                           DBFormat.replace(rset.getString("location_type")),
                                           DBFormat.replace(rset.getString("location_style")),
                                           DBFormat.replace(rset.getString("temporary_area_type")),
                                           DBFormat.replace(rset.getString("temporary_area")),
                                           DBFormat.replace(rset.getString("vacant_search_type")),
                                           DBFormat.replace(rset.getString("whstation_no")),
                                           rset.getTimestamp("REGIST_DATE"),
                                           DBFormat.replace(rset.getString("REGIST_PNAME")),
                                           rset.getTimestamp("LAST_UPDATE_DATE"),
                                           DBFormat.replace(rset.getString("LAST_UPDATE_PNAME"))
                                      )
                                );
            }

            array = new Area[vec.size()];
            vec.copyInto(array);
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolAreaHandler") ;
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            throw (new ReadWriteException()) ;
        }
        return array;
    }

    /**<jp>
     * 取得したAreaインスタンスの内容を元に、AREA表に対するDML文実行用の文字配列を生成します。
     * @param  area 編集対象のAreaインスタンス
     * @return SQL実行用の文字配列
     </jp>*/
    protected Object[] setToArea(Area area)
    {
        Vector vec = new Vector();

        vec.addElement(DBFormat.format(area.getManagementType()));
        vec.addElement(DBFormat.format(area.getAreaNo()));
        vec.addElement(DBFormat.format(area.getAreaName()));
        vec.addElement(DBFormat.format(area.getAreaTypre()));
        vec.addElement(DBFormat.format(area.getLocationType()));
        vec.addElement(DBFormat.format(area.getLocationStyle()));
        vec.addElement(DBFormat.format(area.getTemporaryAreaType()));
        vec.addElement(DBFormat.format(area.getTemporaryArea()));
        vec.addElement(DBFormat.format(area.getVacantSearchType()));
        vec.addElement(DBFormat.format(area.getWhstationNo()));

        vec.addElement(DBFormat.format(area.getRegistDate()));
        vec.addElement(DBFormat.format(area.getRegistPname()));
        vec.addElement(DBFormat.format(area.getLastUpdateDate()));
        vec.addElement(DBFormat.format(area.getLastUpdatePname()));

        Object[] obj = new Object[vec.size()];
        vec.copyInto(obj);
        return obj;
    }

    // Private methods -----------------------------------------------
}
//end of class
