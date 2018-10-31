//$Id: ToolStationHandler.java 5355 2009-11-02 00:44:35Z ota $

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
import java.sql.Timestamp;
import java.util.Vector;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
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
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.tool.location.Aisle;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.base.common.WMSConstants;

/**<jp>
 * <code>Station</code>クラスをデータベースから取得したり、データベースに保管する為に利用するクラスです。
 * <code>Station</code>のサブクラスに関しては、それぞれにHandlerが必要となります。
 * <code>Station</code>およびそのサブクラスの取得に関しては、<code>StationFactory</code>を利用してください。
 * <code>Station</code>およびそのサブクラスには<code>getHandler</code>メソッドが用意されていますので、
 * 対応するHandlerが不明な場合は、<code>getHandler</code>メソッドを利用して取得します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/09</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5355 $, $Date: 2009-11-02 09:44:35 +0900 (月, 02 11 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class is used to retrieve/store the <code>Station</code> class from/to database.
 * As for the subclass of <code>Station</code>, Handler will be requried respectively.
 * Please use <code>StationFactory</code> to retrieve <code>Station</code> and the subclasses.
 * As <code>getHandler</code> method has been prepared for <code>Station</code> and the subclasses,
 * in case the Handler to support is unknown, retrieve one by using <code>getHandler</code> method.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/09</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5355 $, $Date: 2009-11-02 09:44:35 +0900 (月, 02 11 2009) $
 * @author  $Author: ota $
 </en>*/
public class ToolStationHandler
        implements ToolDatabaseHandler
{

    // Class fields --------------------------------------------------

    public static final String STATION_HANDLE = "jp.co.daifuku.wms.base.dbhandler.StationHandler";


    // Class variables -----------------------------------------------
    /**<jp> テーブル名 </jp>*/
    /**<en> name of the table </en>*/
    private String wTableName = "TEMP_DMSTATION";

    /**<jp> STATIONTYPEテーブル名 </jp>*/
    /**<en> name of the STATIONTYPE table</en>*/
    protected final String wStationTypeTableName = wTableName + "TYPE";

    /**<jp>
     * データベース接続用のConnectionインスタンス。
     * トランザクション管理は、このクラスの中では行わない。
     </jp>*/
    /**<en>
     * Connection instance to connect with database
     * Transaction control is not conducted in this class.
     </en>*/
    protected Connection wConn;

    /**<jp>
     * ステートメントを管理する変数。
     </jp>*/
    /**<en>
     * Variables which control the statements.
     </en>*/
    protected Statement wStatement = null;

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
        return ("$Revision: 5355 $,$Date: 2009-11-02 09:44:35 +0900 (月, 02 11 2009) $");
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
    public ToolStationHandler(Connection conn)
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
     * @param conn :Connection connect with database
     * @param tablename :name of the table
     </en>*/
    public ToolStationHandler(Connection conn, String tablename)
    {
        setConnection(conn);
        wTableName = tablename;
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * STATIONTYPE表に引数で指定されているステーションNoが存在するか確認します。
     * @param stationNo ステーションNo
     * @return 存在する場合はtrue。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check whether/not the station no. which has been specified by paramter in the 
     * STATIONTYPE table exists.
     * @param stationNo :station no.
     * @return:true if the no. exists.
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     </en>*/
    public boolean isStationType(String stationNo)
            throws ReadWriteException
    {
        Statement stmt = null;
        ResultSet rset = null;
        int count = 0;

        try
        {
            stmt = wConn.createStatement();
            String sqlstring =
                    "SELECT COUNT(1) COUNT FROM " + wStationTypeTableName + " WHERE STATION_NO = '" + stationNo + "'";

            rset = stmt.executeQuery(sqlstring);
            while (rset.next())
            {
                count = rset.getInt("COUNT");
            }
            if (count == 0)
            {
                return false;
            }
            return true;
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.  {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
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
                //<en>6126001 = Database error occured.  {0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());

            }
        }
    }

    /**<jp>
     * STATIONTYPE表に引数で指定されているステーションNoが存在するか確認します。
     * このメソッドでは、指定されたハンドラクラス以外のなかで、指定ステーションNoが
     * 存在するのかを確認します。
     * @param stationNo ステーションNo
     * @param handlerClass ステーションインスタンスを生成するために使用するハンドらクラス
     * @return 同一データが存在するかしないか（true:存在する false:存在しない）
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check whether/not the station no. which has been specified by paramter in the 
     * STATION_TYPE table exists.
     * In this method, it checks whether/not the specified station no. exists in any handler classes
     * other than the specicified handler class.
     * @param stationNo :station no.
     * @param handlerClass :the handelr class which is used to generate the sation instance.
     * @return :whether/not the identical data exist (true: identical data exists, false: does not exist)
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     </en>*/
    public boolean isStationType(String stationNo, String handlerClass)
            throws ReadWriteException
    {
        Statement stmt = null;
        ResultSet rset = null;
        int count = 0;

        try
        {
            stmt = wConn.createStatement();
            String sqlstring =
                    "SELECT COUNT(1) COUNT FROM " + wStationTypeTableName + " WHERE STATION_NO = '" + stationNo + "'"
                            + " AND " + " CLASS_NAME != '" + handlerClass + "'";

            rset = stmt.executeQuery(sqlstring);
            while (rset.next())
            {
                count = rset.getInt("COUNT");
            }
            if (count == 0)
            {
                return false;
            }
            return true;
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.  {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
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
                //<en>6126001 = Database error occured.  {0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());

            }
        }
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
            //<en>6126001 = Database error occured.  {0}</en>
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
                //<en>6126001 = Database error occured.  {0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }

    }

    /**<jp>
     * データベース接続用の<code>Connection</code>を設定します。
     * @param conn 設定するConnection
     </jp>*/
    /**<en>
     * Set the <code>Connection</code> to connect with database.
     * @param conn :Connection to connect
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
     * ステーションを検索します。検索キーは、<code>StationSearchKey</code>である必要があります。
     * @param key 検索のためのKey
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search the station.  <code>StationSearchKey</code> should be used for a search key.
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
        Station[] fndStation = null;
        Object[] fmtObj = new Object[2];

        // for database access
        ResultSet rset = null;

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
            rset = executeSQL(sqlstring, true); // private exec sql method

            // make Station instances from resultset
            // !!! makeStation() is private method of this.
            fndStation = makeStation(rset);
        }
        catch (NotFoundException e)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en> This should not occur;</en>
            e.printStackTrace();
            throw (new ReadWriteException(e));
        }
        catch (DataExistsException ee)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en> This should not occur;</en>
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

        return fndStation;
    }

    /**<jp>
     * 代表ステーションを検索します。検索キーは、<code>StationSearchKey</code>である必要があります。
     * @param key 検索のためのKey
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search the station.  <code>StationSearchKey</code> should be used for a search key.
     * @param key :Key for the search
     * @return :the array of the created object
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     </en>*/
    public ToolEntity[] findMainStation(ToolSearchKey key)
            throws ReadWriteException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        Station[] fndStation = null;
        Object[] fmtObj = new Object[2];

        // for database access
        ResultSet rset = null;

        String fmtSQL =
                "SELECT * FROM " + wTableName + " WHERE STATION_NO IN " + "(SELECT PARENT_STATION_NO FROM "
                        + wTableName + " {0}) AND WORKPLACE_TYPE  = '3' {1}";

        //        if (key.ReferenceCondition() != null)
        //        {
        //            if (key.SortCondition() != null)
        //            {
        //                fmtObj[0] = "WHERE " + key.ReferenceCondition();
        //                fmtObj[1] = "ORDER BY " + key.SortCondition();
        //            }
        //            else
        //            {
        //                fmtObj[0] = "WHERE " + key.ReferenceCondition();
        //            }
        //        }
        //        else if (key.SortCondition() != null)
        //        {
        //            fmtObj[0] = "ORDER BY " + key.SortCondition();
        //        }

        if (key.ReferenceCondition() != null)
        {
            fmtObj[0] = "WHERE " + key.ReferenceCondition();
        }
        if (key.SortCondition() != null)
        {
            fmtObj[1] = "ORDER BY " + key.SortCondition();
        }

        String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);

        try
        {
            rset = executeSQL(sqlstring, true); // private exec sql method

            // make Station instances from resultset
            // !!! makeStation() is private method of this.
            fndStation = makeStation(rset);
        }
        catch (NotFoundException e)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en> This should not occur;</en>
            e.printStackTrace();
            throw (new ReadWriteException(e));
        }
        catch (DataExistsException ee)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en> This should not occur;</en>
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

        return fndStation;
    }

    /**<jp>
     * エリア内の全ての作業場を含む入出庫ステーションを返します。
     * eWareNaviAS/RS設定ツールの端末エリア情報の作成時に使用します。
     * @return ステーション一覧
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    public Station[] getStationInArea()
            throws ReadWriteException
    {
        ResultSet rset = null;
        Statement stmt = null;
        Station[] stnArray = null;

        try
        {
            stmt = wConn.createStatement();
            String sqlstring =
                    "SELECT TEMP_DMSTATION.* " + "FROM TEMP_DMSTATION, TEMP_DMWAREHOUSE "
                            + "WHERE TEMP_DMSTATION.WH_STATION_NO = TEMP_DMWAREHOUSE.STATION_NO "
                            + "AND TEMP_DMSTATION.STATION_TYPE IN ('1', '2', '3') " + "AND WORKPLACE_TYPE = '0'";

            rset = stmt.executeQuery(sqlstring);

            if (rset != null)
            {
                // make Station instances from resultset
                stnArray = makeStation(rset);
            }

        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.  {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolStationHandler");
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException(e));
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
                //<jp>6126001 =データベースエラーが発生しました。{0}</jp>
                //<en>6126001 =Database error occured.  {0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException(e));
            }
        }
        return stnArray;
    }

    /**<jp>
     * システム内の全ての端末定義を返します。
     * TEAMINAL表の検索を行いますが、便宜上このハンドラで検索します。
     * eWareNaviAS/RS設定ツールの端末エリア情報の作成時に使用します。
     * @return ステーション一覧
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    public String[] getTerminalNumbers()
            throws ReadWriteException
    {
        ResultSet rset = null;
        Statement stmt = null;
        String[] starray = null;

        Connection conn = null;
        try
        {
            //            stmt = wConn.createStatement();
            // WMSTOOLユーザーのデータではなく、WMSユーザーが持つユーザー定義情報を取得する。
            conn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_NAME);
            stmt = conn.createStatement();
            String sqlstring = "SELECT TERMINALNUMBER FROM COM_TERMINAL ";
            rset = stmt.executeQuery(sqlstring);

            Vector vec = new Vector(20);
            while (rset.next())
            {
                vec.addElement(rset.getString("TERMINALNUMBER"));
            }
            starray = new String[vec.size()];
            vec.copyInto(starray);

        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.  {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolStationHandler");
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException(e));
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
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException e)
            {
                //<jp>6126001 =データベースエラーが発生しました。{0}</jp>
                //<en>6126001 =Database error occured.  {0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException(e));
            }
        }
        return starray;
    }


    /**<jp>
     * データベースから、パラメータに基づいて情報を検索し、結果の件数（Stationのデータ件数）を返します。
     * @param key 検索のためのKey
     * @return 検索結果の件数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search information from database based on parameters and return number of results 
     * (number of station data).
     * @param key :Key for search
     * @return :number of search results
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     </en>*/
    public int count(ToolSearchKey key)
            throws ReadWriteException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        Object[] fmtObj = new Object[1];
        int count = 0;

        // for database access
        ResultSet rset = null;

        String fmtSQL = "SELECT count(1) count FROM " + wTableName + " {0} ";

        if (key.ReferenceCondition() != null)
        {
            fmtObj[0] = "WHERE " + key.ReferenceCondition();
        }

        String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
        try
        {
            rset = executeSQL(sqlstring, true); // private exec sql method
            if (rset != null)
            {
                while (rset.next())
                {
                    count = rset.getInt("COUNT");
                }
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 =データベースエラーが発生しました。{0}</jp>
            //<en>6126001 =Database error occured.  {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw (new ReadWriteException(e));
        }
        catch (NotFoundException e)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en> This should not occur;</en>
            e.printStackTrace();
            throw (new ReadWriteException(e));
        }
        catch (DataExistsException ee)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en> This should not occur;</en>
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

        return count;
    }

    /**<jp>
     * データベースに新規情報を作成します。
     * @param tgt 作成する情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws DataExistsException 既に、同じ情報がデータベースに登録済みの場合に通知されます。
     </jp>*/
    /**<en>
     * Create teh new information in database.
     * @param tgt :entity instance which has the information to create
     * @throws ReadWriteException  :Notifies if error occured in connection with database. 
     * @throws DataExistsException :Notifies if the same information has been registered in database.
     </en>*/
    public void create(ToolEntity tgt)
            throws ReadWriteException,
                DataExistsException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        String fmtSQL =
                "INSERT INTO "
                        + wTableName
                        + " ("
                        + "  STATION_NO" // 0
                        + ", MAX_PALLET_QTY" // 1
                        + ", MAX_INSTRUCTION" // 2
                        + ", SENDABLE" // 3
                        + ", STATUS" // 4
                        + ", CONTROLLER_NO" // 5
                        + ", STATION_TYPE" // 6
                        + ", SETTING_TYPE" // 7
                        + ", WORKPLACE_TYPE" // 8
                        + ", OPERATION_DISPLAY" // 9
                        + ", STATION_NAME" // 10
                        + ", SUSPEND" // 11
                        + ", ARRIVAL" // 12
                        + ", LOAD_SIZE" // 13
                        + ", REMOVE" // 14
                        + ", INVENTORY_CHECK_FLAG" // 15
                        + ", RESTORING_OPERATION" // 16
                        + ", RESTORING_INSTRUCTION" // 17
                        + ", WH_STATION_NO" // 18
                        + ", PARENT_STATION_NO" // 19
                        + ", AISLE_STATION_NO" // 20
                        + ", NEXT_STATION_NO" // 21
                        + ", LAST_USED_STATION_NO" // 22
                        + ", REJECT_STATION_NO" // 23
                        + ", MODE_TYPE" // 24
                        + ", CURRENT_MODE" // 25
                        + ", MODE_REQUEST" // 26
                        + ", MODE_REQUEST_DATE" // 27
                        + ", CLASS_NAME" // 28
                        + ") VALUES ("
                        + "{0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12},{13},{14},{15},{16},{17},{18},{19},{20},{21},{22},{23},{24},{25},{26},{27},{28}"
                        + ")";

        String fmtSQL_StationType = "INSERT INTO " + wStationTypeTableName + " (" + "  STATION_NO" // 0
                + ",  CLASS_NAME" // 1
                + ") VALUES (" + "{0}, '" + STATION_HANDLE + "'" + ")";

        Station tgtSt;
        String sqlstring;
        //-------------------------------------------------
        // process
        //-------------------------------------------------
        if (tgt instanceof Station)
        {
            tgtSt = (Station)tgt;
        }
        else
        {
            //<jp>致命的なエラーが発生しました。{0}</jp>
            //<en>Fatal error has occurred.{0}</en>
            RmiMsgLogClient.write("6126499" + wDelim + "Illegal argument. Set Station Instance.",
                    this.getClass().getName());
            throw (new ReadWriteException());
        }

        try
        {
            // setting Station information to Object array
            Object[] fmtObj = setToArray(tgtSt);
            // create actual SQL
            sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
            // execute the sql
            executeSQL(sqlstring, false);

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
    public void modify(ToolEntity tgt)
            throws ReadWriteException,
                NotFoundException
    {
        //<jp> DBをアップデート</jp>
        //<jp>-------------------------------------------------</jp>
        //<jp> variable define</jp>
        //<jp>-------------------------------------------------</jp>
        //<en> Update DB.</en>
        //<en>-------------------------------------------------</en>
        //<en> variable define</en>
        //<en>-------------------------------------------------</en>
        String fmtSQL = "UPDATE " + wTableName + " set" + "  MAX_PALLET_QTY = {1} " // 1
                + ", MAX_INSTRUCTION = {2} " // 2
                + ", SENDABLE = {3} " // 3
                + ", STATUS = {4} " // 4
                + ", CONTROLLER_NO = {5} " // 5
                + ", STATION_TYPE = {6} " // 6
                + ", SETTING_TYPE = {7} " // 7
                + ", WORKPLACE_TYPE = {8} " // 8
                + ", OPERATION_DISPLAY = {9} " // 9
                + ", STATION_NAME = {10} " // 10
                + ", SUSPEND = {11} " // 11
                + ", ARRIVAL = {12} " // 12
                + ", LOAD_SIZE = {13} " // 13
                + ", REMOVE = {14} " // 14
                + ", INVENTORY_CHECK_FLAG = {15} " // 15
                + ", RESTORING_OPERATION = {16} " // 16
                + ", RESTORING_INSTRUCTION = {17} " // 17
                + ", WH_STATION_NO = {18} " // 18
                + ", PARENT_STATION_NO = {19} " // 19
                + ", AISLE_STATION_NO = {20} " // 20
                + ", NEXT_STATION_NO = {21} " // 21
                + ", LAST_USED_STATION_NO = {22} " // 22
                + ", REJECT_STATION_NO = {23} " // 23
                + ", MODE_TYPE = {24} " // 24
                + ", CURRENT_MODE = {25} " // 25
                + ", MODE_REQUEST = {26} " // 26
                + ", MODE_REQUEST_DATE = {27} " // 27
                + ", CARRY_KEY = {28} " // 28
                + ", HEIGHT = {29} " // 29
                + ", BCR_DATA = {30} " // 30
                + ", CONTROLINFO = {31} " // 31
                + ", CLASS_NAME = {32} " // 32
                + " WHERE STATION_NO = {0}"; // 0

        Station tgtSt;
        String sqlstring;
        //-------------------------------------------------
        // process
        //-------------------------------------------------
        if (tgt instanceof Station)
        {
            tgtSt = (Station)tgt;
        }
        else
        {
            //<jp>致命的なエラーが発生しました。{0}</jp>
            //<en>Fatal error has occurred.{0}</en>
            RmiMsgLogClient.write("6126499" + wDelim + "Illegal argument. Set Station Instance.", "ToolStationHandler");
            throw (new ReadWriteException());
        }

        try
        {
            // setting Station information to Object array
            Object[] fmtObj = setToArray(tgtSt);
            // create actual SQL
            sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
            // execute the sql
            executeSQL(sqlstring, false);
        }
        catch (DataExistsException ee)
        {
            //<jp> updateなので、起こらないはず</jp>
            //<en> This should not occur;</en>
            ee.printStackTrace();
            throw (new ReadWriteException(ee));
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
            //<jp>6126005 = 更新値がセットされていないため、データベースを更新できません。 TABLE={0}</jp>
            //<en>6126005 = Cannot update the database as the update value has not been set. TABLE={0}</en>
            RmiMsgLogClient.write(6126005, LogMessage.F_ERROR, "ToolStationHandler", tobj);
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
            //<jp>6126006 = 更新条件がセットされていないため、データベースを更新できません。 TABLE={0}</jp>
            //<en>6126006 = Cannot update the database as the update condiitons have not been set. TABLE={0}</en>
            RmiMsgLogClient.write(6126006, LogMessage.F_ERROR, "ToolStationHandler", tobj);
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
            //<jp> updateなので、起こらないはず</jp>
            //<en> This should not occur;</en>
            ee.printStackTrace();
            throw (new ReadWriteException(ee));
        }
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
    public void drop(ToolEntity tgt)
            throws ReadWriteException,
                NotFoundException
    {
        //<jp> DBからDelete</jp>
        //<jp>-------------------------------------------------</jp>
        //<jp> variable define</jp>
        //<jp>-------------------------------------------------</jp>
        //<en> Delete from DB</en>
        //<en>-------------------------------------------------</en>
        //<en> variable define</en>
        //<en>-------------------------------------------------</en>
        String fmtSQL = "DELETE FROM " + wTableName + " WHERE STATION_NO = {0}"; // 0

        String fmtSQL_StationType = "DELETE FROM " + wStationTypeTableName + " WHERE STATION_NO = {0}"; // 0


        Station tgtSt;
        String sqlstring;
        //-------------------------------------------------
        // process
        //-------------------------------------------------
        if (tgt instanceof Station)
        {
            tgtSt = (Station)tgt;
        }
        else
        {
            //<jp>致命的なエラーが発生しました。{0}</jp>
            //<en>Fatal error has occurred.{0}</en>
            RmiMsgLogClient.write("6126499" + wDelim + "Illegal argument. Set Station Instance.", "ToolStationHandler");
            throw (new ReadWriteException());
        }

        try
        {
            // setting Station information to Object array
            Object[] fmtObj = setToArray(tgtSt);
            // create actual SQL
            sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
            // execute the sql
            executeSQL(sqlstring, false);

            //for stationtype table.
            sqlstring = SimpleFormat.format(fmtSQL_StationType, fmtObj);
            // execute the sql
            executeSQL(sqlstring, false);

        }
        catch (DataExistsException ee)
        {
            //<jp> deleteなので、起こらないはず</jp>
            //<en> This should not occur;</en>
            ee.printStackTrace();
            throw (new ReadWriteException(ee));
        }
    }

    /**<jp>
     * データベースから、パラメータで渡されたキーに合致する情報を削除します。
     * @param key 削除する情報のキー
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 削除すべき情報が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete from database the information which match the key passed through parameter.
     * @param key :key for the to-delete information
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     * @throws NotFoundException  :Notifies if data to delete cannot be found.
     </en>*/
    public void drop(ToolSearchKey key)
            throws ReadWriteException,
                NotFoundException
    {
        //<jp> DBからDelete</jp>
        //<en> Delete from DB</en>
        ToolEntity[] tgts = find(key);
        for (int i = 0; i < tgts.length; i++)
        {
            drop(tgts[i]);
        }
    }

    /**
     * ダブルディープのアイルに属するSTATIONの荷姿チェックを荷姿チェックありに更新します。<BR>
     * @param tgt 変更する情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public void modifyLoadSizeCheck()
            throws ReadWriteException
    {
        //<jp> DBをアップデート</jp>
        //<jp>-------------------------------------------------</jp>
        //<jp> variable define</jp>
        //<jp>-------------------------------------------------</jp>
        //<en> Update DB.</en>
        //<en>-------------------------------------------------</en>
        //<en> variable define</en>
        //<en>-------------------------------------------------</en>
        String sqlstring =
                "UPDATE " + wTableName + " SET LOAD_SIZE = " + DBFormat.format(Integer.toString(Station.LOADSIZECHECK))
                        + " WHERE EXISTS (SELECT 1 FROM TEMP_DMAISLE "
                        + "                        WHERE AISLE_STATION_NO = TEMP_DMAISLE.STATION_NO"
                        + "                          AND TEMP_DMAISLE.DOUBLE_DEEP_KIND = "
                        + DBFormat.format(Integer.toString(Aisle.DOUBLE_DEEP)) + "               )";
        try
        {
            // execute the sql
            executeSQL(sqlstring, false);
        }
        catch (DataExistsException ee)
        {
            //<jp> updateなので、起こらないはず</jp>
            //<en> This should not occur;</en>
            ee.printStackTrace();
            throw (new ReadWriteException(ee));
        }
        catch (NotFoundException e)
        {
            // エラーにはしない
        }
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
                wStatement.close();
            }
            wStatement = null;
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.  {0}</en>

            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolStationHandler");
        }
        catch (NullPointerException e)
        {
            //<jp>エラーログの出力処理も行う。</jp>
            //<en>Also carry out the output of error log.</en>
            Object[] tObj = new Object[1];
            if (wStatement != null)
            {
                tObj[0] = wStatement.toString();
            }
            else
            {
                tObj[0] = "null";
            }
            //<jp>6126007 = カーソルのクローズが出来ませんでした。Statement=[{0}]</jp>
            //<en>6126007 = Could not close the cursor. Statement=[{0}]</en>
            RmiMsgLogClient.write(6126007, LogMessage.F_ERROR, "ToolStationHandler", tObj);
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
     * @param query  :true if it is a query
     * @return :<code>ResultSet</code> of the results, or null for all other cases
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     * @throws NotFoundException  :Notifies if result of the exection was 0.
     * @throws DataExistsException :If the unique restriction is broken at Insert.
     </en>*/
    protected ResultSet executeSQL(String sqlstr, boolean query)
            throws ReadWriteException,
                NotFoundException,
                DataExistsException
    {
        ResultSet rset = null;
        try
        {
            //<jp> queryでfirst() で0行を見るためにはスクロールカーソルが必要</jp>
            //<en> A scroll cursor will be requried in order to view line 0 by first() of the query.</en>
            wStatement = wConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
                    throw (new NotFoundException("6123001"));
                }
                closeStatement();
            }
        }
        catch (SQLException e)
        {
            if (e.getErrorCode() == ToolParam.DATAEXISTS)
            {
                //<jp>6126008 = 既に同一データが存在するため、登録できません。</jp>
                //<en>6126008 = Cannot registrate; the identical data already exists.</en>
                RmiMsgLogClient.write(6126008, LogMessage.F_ERROR, "ToolStationHandler", null);
                throw (new DataExistsException("6126008"));
            }
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.  {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolStationHandler");

            //<jp> データベースエラーが発生しました。</jp>
            //<en> Database error occured.  </en>
            //String msg = "6126001" ; 
            throw (new ReadWriteException(e));
        }
        return (rset);
    }

    /**<jp>
     * <code>Station</code>インスタンスから情報を取り出して、Object配列にセットします。
     * INSERT,UPDATE の為に用意されています。
     * データベースへの保存時、nullがふさわしいものは、文字列 null がセットされます。
     * 文字列タイプの項目は、'(シングル・クオート)を前後に追加します。
     * @param tgtSt 情報を取得する<code>Station</code>インスタンス
     * @return Object配列。
     * <p>
     * 配列順は以下のようになります。<br>
     * <pre>
     * stationnumber            // 0
     * maxpalletquantity        // 1
     * maxinstruction            // 2
     * sendable                    // 3
     * status                    // 4
     * controllernumber         // 5
     * stationtype                // 6
     * settingtype                // 7
     * workplacetype            // 8
     * operationdisplay            // 9
     * stationname                // 10
     * suspend                    // 11
     * arrivalcheck                // 12
     * loadsize            // 13
     * remove                    // 14
     * inventorycheckflag        // 15
     * restoringoperation        // 16
     * restoringinstruction        // 17
     * whstationnumber            // 18
     * parentstationnumber        // 19
     * ailestationnumber        // 20
     * nextstationnumber        // 21
     * lastusedstationnumber    // 22
     * rejectstationnumber        // 23
     * modetype                    // 24
     * currentmode                // 25
     * moderequest                // 26
     * moderequesttime            // 27
     * classname                // 28
     * </pre></p>
     </jp>*/
    /**<en>
     * Retireve information from the <code>Station</code> instance and set in Object array.
     * This is prepared for INSERT and UPDATE.
     * When storing in database and if appropiate, it will set string null in some cases.
     * Enclose the string type items in ' single quotations.
     * @param tgtSt :<code>Station</code> instance to retrieve data
     * @return :Object array
     * <p>
     * The order of the arrays whould be as follows.<br>
     * <pre>
     * stationnumber            // 0
     * maxpalletquantity        // 1
     * maxinstruction            // 2
     * sendable                    // 3
     * status                    // 4
     * controllernumber         // 5
     * stationtype                // 6
     * settingtype                // 7
     * workplacetype            // 8
     * operationdisplay            // 9
     * stationname                // 10
     * suspend                    // 11
     * arrivalcheck                // 12
     * loadsize            // 13
     * remove                    // 14
     * inventorycheckflag        // 15
     * restoringoperation        // 16
     * restoringinstruction        // 17
     * whstationnumber            // 18
     * parentstationnumber        // 19
     * ailestationnumber        // 20
     * nextstationnumber        // 21
     * lastusedstationnumber    // 22
     * rejectstationnumber        // 23
     * modetype                    // 24
     * currentmode                // 25
     * moderequest                // 26
     * moderequesttime            // 27
     * classname                // 28
     * </pre></p>
     </en>*/
    protected Object[] setToArray(Station tgtSt)
    {
        Object[] fmtObj = new Object[29];
        // set parameters
        // station number
        fmtObj[0] = DBFormat.format(tgtSt.getStationNo());
        // max pallet quantity
        fmtObj[1] = Integer.toString(tgtSt.getMaxPalletQty());
        //<jp> 最大搬送指示可能数</jp>
        //<en> max number of carry instruction sendable</en>
        fmtObj[2] = Integer.toString(tgtSt.getMaxInstruction());
        // sendable
        if (tgtSt.isSendable())
        {
            fmtObj[3] = Integer.toString(Station.SENDABLE);
        }
        else
        {
            fmtObj[3] = Integer.toString(Station.NOT_SENDABLE);
        }
        // status
        fmtObj[4] = Integer.toString(tgtSt.getStatus());
        // group controller id
        if (tgtSt.getGroupController() == null)
        {
            fmtObj[5] = "0";
        }
        else
        {
            fmtObj[5] = Integer.toString(tgtSt.getGroupController().getNumber());
        }
        // station type
        fmtObj[6] = Integer.toString(tgtSt.getStationType());
        // setting type (for in-station)
        fmtObj[7] = Integer.toString(tgtSt.getSettingType());
        // workplace type
        fmtObj[8] = Integer.toString(tgtSt.getWorkPlaceType());
        // operationdisplay
        fmtObj[9] = Integer.toString(tgtSt.getOperationDisplay());
        // station name
        fmtObj[10] = DBFormat.format(tgtSt.getStationName());
        // suspend
        if (tgtSt.isSuspend())
        {
            fmtObj[11] = Integer.toString(Station.SUSPEND);
        }
        else
        {
            fmtObj[11] = Integer.toString(Station.NOT_SUSPEND);
        }
        // arrivalcheck
        if (tgtSt.isArrivalCheck())
        {
            fmtObj[12] = Integer.toString(Station.ARRIVALCHECK);
        }
        else
        {
            fmtObj[12] = Integer.toString(Station.NOT_ARRIVALCHECK);
        }
        // loadsizecheck
        if (tgtSt.isLoadSizeCheck())
        {
            fmtObj[13] = Integer.toString(Station.LOADSIZECHECK);
        }
        else
        {
            fmtObj[13] = Integer.toString(Station.NOT_LOADSIZECHECK);
        }
        // removecheck
        if (tgtSt.isRemove())
        {
            fmtObj[14] = Integer.toString(Station.PAYOUT_OK);
        }
        else
        {
            fmtObj[14] = Integer.toString(Station.PAYOUT_NG);
        }
        // inventorycheckflag
        fmtObj[15] = Integer.toString(tgtSt.getInventoryCheckFlag());
        // restoringoperation
        if (tgtSt.isReStoringOperation())
        {
            fmtObj[16] = Integer.toString(Station.CREATE_RESTORING);
        }
        else
        {
            fmtObj[16] = Integer.toString(Station.NOT_CREATE_RESTORING);
        }
        // restoringinstruction
        fmtObj[17] = Integer.toString(tgtSt.getReStoringInstruction());
        // werehouse station number
        fmtObj[18] = DBFormat.format(tgtSt.getWarehouseStationNo());
        // parent station number
        fmtObj[19] = DBFormat.format(tgtSt.getParentStationNo());
        // aile station number
        fmtObj[20] = DBFormat.format(tgtSt.getAisleStationNo());
        // next station number
        fmtObj[21] = DBFormat.format(tgtSt.getNextStationNo());
        // lastused station number
        fmtObj[22] = DBFormat.format(tgtSt.getLastUsedStationNo());
        // reject station number
        fmtObj[23] = DBFormat.format(tgtSt.getRejectStationNo());
        // mode type
        fmtObj[24] = Integer.toString(tgtSt.getModeType());
        // current mode
        fmtObj[25] = Integer.toString(tgtSt.getCurrentMode());
        // mode request
        fmtObj[26] = Integer.toString(tgtSt.getModeRequest());
        // mode request time
        fmtObj[27] = DBFormat.format(tgtSt.getModeRequestDate());
        // class name
        fmtObj[28] = DBFormat.format(tgtSt.getClassName());

        return (fmtObj);
    }

    /**<jp>
     * <code>ResultSet</code>から、各項目を取り出して、<code>Station</code>インスタンスを生成します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve each item from <code>ResultSet</code> and generate the <code>Station</code> instance.
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     </en>*/
    protected Station[] makeStation(ResultSet rset)
            throws ReadWriteException
    {
        Vector tmpStVect = new Vector(20); // temporary store for Station instances
        Station tmpst = null;
        // data get from resultset and make new Station instance
        try
        {
            while (rset.next())
            {

                tmpst = new Station(DBFormat.replace(rset.getString("STATION_NO")));

                // max pallet quantity
                tmpst.setMaxPalletQty(rset.getInt("MAX_PALLET_QTY"));
                //<jp> 最大搬送指示可能数</jp>
                //<en> max number of carry instrucitons sendable</en>
                tmpst.setMaxInstruction(rset.getInt("MAX_INSTRUCTION"));
                // sendable
                boolean snd = (rset.getInt("SENDABLE") == Station.SENDABLE);
                tmpst.setSendable(snd);
                // station status
                tmpst.setStatus(rset.getInt("STATUS"));
                // group controller
                tmpst.setGroupController(GroupController.getInstance(wConn, rset.getInt("CONTROLLER_NO")));
                // station type
                tmpst.setStationType(rset.getInt("STATION_TYPE"));
                // setting type
                tmpst.setSettingType(rset.getInt("SETTING_TYPE"));
                // workplace type
                tmpst.setWorkPlaceType(rset.getInt("WORKPLACE_TYPE"));
                // operationdisplay
                tmpst.setOperationDisplay(rset.getInt("OPERATION_DISPLAY"));
                // station name
                tmpst.setStationName(DBFormat.replace(rset.getString("STATION_NAME")));
                // suspend
                boolean sus = (rset.getInt("SUSPEND") == Station.SUSPEND);
                tmpst.setSuspend(sus);
                // arrivalCheck
                boolean arr = (rset.getInt("ARRIVAL") == Station.ARRIVALCHECK);
                tmpst.setArrivalCheck(arr);
                // loadSizeCheck
                boolean load = (rset.getInt("LOAD_SIZE") == Station.LOADSIZECHECK);
                tmpst.setLoadSize(load);
                //<jp> 払い出し区分</jp>
                //<en> removal</en>
                boolean pay = (rset.getInt("REMOVE") == Station.PAYOUT_OK);
                tmpst.setRemove(pay);
                // inventory check Flag
                tmpst.setInventoryCheckFlag(rset.getInt("INVENTORY_CHECK_FLAG"));
                // Restoring Operation
                boolean res = (rset.getInt("RESTORING_OPERATION") == Station.CREATE_RESTORING);
                tmpst.setReStoringOperation(res);
                // Restoring Instruction
                tmpst.setReStoringInstruction(rset.getInt("RESTORING_INSTRUCTION"));
                // WareHouse
                tmpst.setWhStationNo(DBFormat.replace(rset.getString("WH_STATION_NO")));
                // station parent station
                tmpst.setParentStationNo(DBFormat.replace(rset.getString("PARENT_STATION_NO")));
                // ails station number
                tmpst.setAisleStationNo(DBFormat.replace(rset.getString("AISLE_STATION_NO")));
                // next station number
                tmpst.setNextStationNo(DBFormat.replace(rset.getString("NEXT_STATION_NO")));
                // last station number
                tmpst.setLastUsedStationNo(DBFormat.replace(rset.getString("LAST_USED_STATION_NO")));
                // reject station number
                tmpst.setRejectStationNo(DBFormat.replace(rset.getString("REJECT_STATION_NO")));
                // mode type
                tmpst.setModeType(rset.getInt("MODE_TYPE"));
                // cyrrent mode
                tmpst.setCurrentMode(rset.getInt("CURRENT_MODE"));
                // change mode request
                tmpst.setModeRequest(rset.getInt("MODE_REQUEST"));
                // change mode request time 
                Timestamp tims = rset.getTimestamp("MODE_REQUEST_DATE");
                java.util.Date mdate = null;
                if (tims != null)
                {
                    mdate = new java.util.Date(tims.getTime());
                }
                else
                {
                    mdate = null;
                }
                tmpst.setModeRequestDate(mdate);
                // class name
                tmpst.setClassName(DBFormat.replace(rset.getString("CLASS_NAME")));

                // station handler
                tmpst.setHandler(this);

                // append new Station instance to Vector
                tmpStVect.add(tmpst);
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured.  {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolStationHandler");
            throw (new ReadWriteException(e));
        }
        catch (Exception e)
        {
            if (e instanceof ReadWriteException)
            {
                throw (ReadWriteException)e;
            }
            e.printStackTrace(wPW);
            Object[] tObj = new Object[2];
            tObj[0] = "Station";
            tObj[1] = wSW.toString();
            //<jp>6126003 = インスタンスの生成に失敗しました。クラス名={0} {1}</jp>
            //<en>6126003 = Failed to generate the instance. class name={0} {1}</en>
            RmiMsgLogClient.write(6126003, LogMessage.F_ERROR, "ToolStationHandler", tObj);
            throw (new ReadWriteException(e));
        }

        // move instance from vector to array of Station
        Station[] rstarr = new Station[tmpStVect.size()];
        tmpStVect.copyInto(rstarr);

        return (rstarr);
    }

    // Private methods -----------------------------------------------
}
//end of class

