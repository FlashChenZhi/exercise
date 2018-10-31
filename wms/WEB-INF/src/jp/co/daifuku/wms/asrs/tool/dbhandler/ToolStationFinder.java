//$Id: ToolStationFinder.java 5355 2009-11-02 00:44:35Z ota $

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

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.wms.asrs.tool.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.tool.location.Station;

/**<jp>
 * データベースからStation表を検索し<CODE>ToolStationFinder</CODE>にマッピングするためのクラスです。
 * 本クラスは作業場一覧画面で使用されています。<BR>
 * 画面に検索結果を一覧表示する場合このクラスを使用します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5355 $, $Date: 2009-11-02 09:44:35 +0900 (月, 02 11 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class is used to search the Station table from database and to map into  
 * <CODE>ToolStationFinder</CODE>.
 * This class is used when displaying the list of workshop.<BR>
 * This class will be used when displaying the list of search results on the screen.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5355 $, $Date: 2009-11-02 09:44:35 +0900 (月, 02 11 2009) $
 * @author  $Author: ota $
 </en>*/
public class ToolStationFinder
        extends ToolStationHandler
        implements ToolDatabaseFinder
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp> テーブル名 </jp>*/
    /**<en> name of the table </en>*/

    private String wTableName = "TEMP_DMSTATION";

    /**<jp>
     * ステートメントを管理する変数。
     </jp>*/
    /**<en>
     * Variables which control the statements.
     </en>*/
    protected Statement wStatement = null;

    /**<jp>
     * 検索結果を保持する変数。
     </jp>*/
    /**<en>
     * Variables which preserve the search results
     </en>*/
    protected ResultSet wResultSet = null;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return :Version and the date
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
    public ToolStationFinder(Connection conn)
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
    public ToolStationFinder(Connection conn, String tablename)
    {
        super(conn);
        wTableName = tablename;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * ステートメントを生成し、カーソルをオープンします。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Generate a statement and open cursors.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public void open()
            throws ReadWriteException
    {
        try
        {
            wStatement = wConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured. {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message.</en>
            throw new ReadWriteException(e);
        }
    }

    /**<jp>
     * データベースの検索結果をエンティティ配列にして返します。
     * @param 検索結果の指定された開始位置
     * @param 検索結果の指定された終了位置
     * @return エンティティ配列
     * @throws ReadWriteException データベース接続で発生した例外をそのまま通知します。
     * @throws InvalidStatusException 指定された検索結果範囲に異常があった場合通知します。
     </jp>*/
    /**<en>
     * Return the result of database search in form of entity array.
     * @param :start position of the search result
     * @param :end position of the search result
     * @return :entity array
     * @throws ReadWriteException :Notifies the exception itself that occurred in connection with database.
     * @throws InvalidStatusException :Notifies if error was found in the scope of search specified.
     </en>*/
    public ToolEntity[] getEntitys(int start, int end)
            throws ReadWriteException,
                InvalidStatusException
    {
        Vector vec = new Vector();
        Station[] stationarray = null;
        Station tmpst = null;

        try
        {
            //<jp> 表示件数</jp>
            //<en> number of data to display</en>
            int count = end - start;

            if (wResultSet.absolute(start + 1))
            {
                for (int i = 0; i < count; i++)
                {
                    if (i > 0)
                    {
                        wResultSet.next();
                    }

                    //String clsname = DBFormat.replace(wResultSet.getString("CLASSNAME"));
                    tmpst = new Station(DBFormat.replace(wResultSet.getString("STATION_NO")));

                    // max pallet quantity</
                    tmpst.setMaxPalletQty(wResultSet.getInt("MAX_PALLET_QTY"));
                    //<jp> 最大搬送指示可能数</jp>
                    //<en> max number of carry instructions</en>
                    tmpst.setMaxInstruction(wResultSet.getInt("MAX_INSTRUCTION"));
                    // sendable
                    boolean snd = (wResultSet.getInt("SENDABLE") == Station.SENDABLE);
                    tmpst.setSendable(snd);
                    // station status
                    tmpst.setStatus(wResultSet.getInt("STATUS"));
                    // group controller
                    tmpst.setGroupController(GroupController.getInstance(wConn, wResultSet.getInt("CONTROLLER_NO")));
                    // station type
                    tmpst.setStationType(wResultSet.getInt("STATION_TYPE"));
                    // setting type
                    tmpst.setSettingType(wResultSet.getInt("SETTING_TYPE"));
                    // workplace type
                    tmpst.setWorkPlaceType(wResultSet.getInt("WORKPLACE_TYPE"));
                    // operationdisplay
                    tmpst.setOperationDisplay(wResultSet.getInt("OPERATION_DISPLAY"));
                    // station name
                    tmpst.setStationName(DBFormat.replace(wResultSet.getString("STATION_NAME")));
                    // suspend
                    boolean sus = (wResultSet.getInt("SUSPEND") == Station.SUSPEND);
                    tmpst.setSuspend(sus);
                    // arrivalCheck
                    boolean arr = (wResultSet.getInt("ARRIVAL") == Station.ARRIVALCHECK);
                    tmpst.setArrivalCheck(arr);
                    // loadSizeCheck
                    boolean load = (wResultSet.getInt("LOAD_SIZE") == Station.LOADSIZECHECK);
                    tmpst.setLoadSize(load);
                    //<jp> 払い出し区分</jp>
                    //<en> removal</en>
                    boolean pay = (wResultSet.getInt("REMOVE") == Station.PAYOUT_OK);
                    tmpst.setRemove(pay);
                    // inventory check Flag
                    tmpst.setInventoryCheckFlag(wResultSet.getInt("INVENTORY_CHECK_FLAG"));
                    // Restoring Operation
                    boolean res = (wResultSet.getInt("RESTORING_OPERATION") == Station.CREATE_RESTORING);
                    tmpst.setReStoringOperation(res);
                    // Restoring Instruction
                    tmpst.setReStoringInstruction(wResultSet.getInt("RESTORING_INSTRUCTION"));
                    // WareHouse
                    tmpst.setWhStationNo(DBFormat.replace(wResultSet.getString("WH_STATION_NO")));
                    // station parent station
                    tmpst.setParentStationNo(DBFormat.replace(wResultSet.getString("PARENT_STATION_NO")));
                    // ails station number
                    tmpst.setAisleStationNo(DBFormat.replace(wResultSet.getString("AISLE_STATION_NO")));
                    // next station number
                    tmpst.setNextStationNo(DBFormat.replace(wResultSet.getString("NEXT_STATION_NO")));
                    // last station number
                    tmpst.setLastUsedStationNo(DBFormat.replace(wResultSet.getString("LAST_USED_STATION_NO")));
                    // reject station number
                    tmpst.setRejectStationNo(DBFormat.replace(wResultSet.getString("REJECT_STATION_NO")));
                    // mode type
                    tmpst.setModeType(wResultSet.getInt("MODE_TYPE"));
                    // cyrrent mode
                    tmpst.setCurrentMode(wResultSet.getInt("CURRENT_MODE"));
                    // change mode request
                    tmpst.setModeRequest(wResultSet.getInt("MODE_REQUEST"));
                    // change mode request time 
                    Timestamp tims = wResultSet.getTimestamp("MODE_REQUEST_DATE");
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
                    tmpst.setClassName(DBFormat.replace(wResultSet.getString("CLASS_NAME")));

                    // station handler
                    tmpst.setHandler(this);

                    // append new Station instance to Vector
                    vec.addElement(tmpst);

                }

                stationarray = new Station[vec.size()];
                vec.copyInto(stationarray);
            }
            else
            {
                //<jp> 指定された行が正しくありません。</jp>
                //<en> Incorrect line has been selected.</en>
                RmiMsgLogClient.write(6126012, LogMessage.F_ERROR, this.getClass().getName(), null);
                throw new InvalidStatusException("6126012");
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured. {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message</en>
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
            tObj[0] = wTableName;
            tObj[1] = wSW.toString();
            RmiMsgLogClient.write(6126003, LogMessage.F_ERROR, this.getClass().getName(), tObj);
            throw (new ReadWriteException(e));
        }
        return stationarray;
    }

    /**<jp>
     * データベースを検索し、エンティティ配列を返します。
     * @return エンティティ配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search database and return the entity array.
     * @return :the entity array
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public ToolEntity[] next()
            throws ReadWriteException
    {
        ToolEntity[] toolentity = null;
        return toolentity;
    }

    /**<jp>
     * データベースを検索し、オブジェクトを返します。
     * @return エンティティ配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search database and return the object
     * @return :the entity array
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public ToolEntity[] back()
            throws ReadWriteException
    {
        ToolEntity[] toolentity = null;
        return toolentity;
    }

    /**<jp>
     * ステートメントをクローズします。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Close the statement.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public void close()
            throws ReadWriteException
    {
        try
        {
            if (wResultSet != null)
            {
                wResultSet.close();
                wResultSet = null;
            }
            if (wStatement != null)
            {
                wStatement.close();
                wStatement = null;
            }
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

    /**<jp>
     * ステーション管理テーブルを検索します。ステーションNoが指定されたときにこのメソッドを使用します。
     * このメソッドで取得されるデータは、ステーション種別が[3:入出庫兼用]でモード切替種別が
     * [0:モード切替なし 2:AGCモード切替]以外のデータを対象としています。
     * 使用される場合は、注意してご使用ください。
     * @param  stnumber  ステーションNo
     * @return 検索結果の件数
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Conduct search in station control table. This method will be used when the station no. is sprcified.
     * Target data of this method will be the data of station type [3:storage/retireval available] and of 
     * mode switch type anything other than [0:no mode switching, 2:AGC mode switch].
     * Please be certain of this condition when using thie method.
     * @param  stnumber  :station no.
     * @return :number of search result
     * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database. 
     </en>*/
    public int search(String stnumber)
            throws ReadWriteException
    {
        int count = 0;
        ResultSet countret = null;
        Object fmtObj[] = new Object[1];
        try
        {
            String fmtCountSQL =
                    "SELECT COUNT(1) COUNT FROM " + wTableName + " WHERE STATION_TYPE = 3 "
                            + " AND MODE_TYPE not in (0,2)  {0} ";

            String fmtSQL =
                    "SELECT * FROM " + wTableName + " WHERE STATION_TYPE = 3 " + " AND MODE_TYPE not in (0,2)  {0} ";

            if ((stnumber != null) && (!stnumber.equals("")))
            {
                fmtObj[0] = "AND STATION_NO = '" + stnumber + "'";
            }

            String sqlcountstring = SimpleFormat.format(fmtCountSQL, fmtObj);
            countret = wStatement.executeQuery(sqlcountstring);
            while (countret.next())
            {
                count = countret.getInt("COUNT");
            }
            //<jp>件数がMAXDISP以下の場合にのみ検索を実行します</jp>
            //<en>Carry out the search only when the number of data is lower than MAXDISP.</en>

            if (count <= MAXDISP)
            {
                String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
                wResultSet = wStatement.executeQuery(sqlstring);
            }
            else
            {
                wResultSet = null;
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured. {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message</en>
            throw (new ReadWriteException(e));
        }
        finally
        {
            try
            {
                if (countret != null)
                {
                    countret.close();
                    countret = null;
                }
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
     * 指定されたSerchKeyを元にステーション管理テーブルを検索します。
     * 使用される場合は、注意してご使用ください。
     * @param key   検索のためのKey
     * @return 検索結果の件数
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Search the station control table based on the specified SerchKey.
     * Please be careful when using this method.
     * @param key   :Key for the search
     * @return :number of search results
     * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database. 
     </en>*/
    public int search(ToolSearchKey key)
            throws ReadWriteException
    {
        int count = 0;
        ResultSet countret = null;
        Object fmtObj[] = new Object[2];
        try
        {
            String fmtCountSQL = "SELECT COUNT(1) COUNT FROM " + wTableName + "  {0} {1} ";

            String fmtSQL = "SELECT * FROM " + wTableName + "  {0} {1} ";

            if (key.ReferenceCondition() != null)
            {
                fmtObj[0] = "WHERE " + key.ReferenceCondition();
                if (key.SortCondition() != null)
                {
                    fmtObj[1] = "ORDER BY " + key.SortCondition();
                }
            }
            else if (key.SortCondition() != null)
            {
                fmtObj[0] = "ORDER BY " + key.SortCondition();
            }

            String sqlcountstring = SimpleFormat.format(fmtCountSQL, fmtObj);
            countret = wStatement.executeQuery(sqlcountstring);
            while (countret.next())
            {
                count = countret.getInt("COUNT");
            }
            //<jp>件数がMAXDISP以下の場合にのみ検索を実行します</jp>
            //<en>Carry out the search only when the number of data is lower than MAXDISP.</en>

            if (count <= MAXDISP)
            {
                String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
                wResultSet = wStatement.executeQuery(sqlstring);
            }
            else
            {
                wResultSet = null;
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured. {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message</en>
            throw (new ReadWriteException(e));
        }
        finally
        {
            try
            {
                if (countret != null)
                {
                    countret.close();
                    countret = null;
                }
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
     * 指定されたSerchKeyを元にステーション管理テーブルを検索します。
     * 使用される場合は、注意してご使用ください。
     * @param key   検索のためのKey
     * @param ptst  検索のためのKey
     * @param flg   検索のためのKey
     * @return 検索結果の件数
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Search the station control table based on the specified SerchKey.
     * Please be careful when using this method.
     * @param key   :Key for the search
     * @param ptst  :Key for the search
     * @param flg   :Key for the search
     * @return :number of search results
     * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database. 
     </en>*/
    public int search(ToolSearchKey key, String parentst, int flg)
            throws ReadWriteException
    {
        int count = 0;
        ResultSet countret = null;
        Object fmtObj[] = new Object[3];
        try
        {
            String fmtCountSQL = "SELECT COUNT(1) COUNT FROM " + wTableName + "  {0} {1} {2}";

            String fmtSQL = "SELECT * FROM " + wTableName + "  {0} {1} {2}";

            if (flg == 0)
            {
                fmtObj[0] =
                        " WHERE STATION_NO != '" + parentst
                                + "' AND (TRIM(AISLE_STATION_NO) IS NOT NULL OR WORKPLACE_TYPE ="
                                + Station.STAND_ALONE_STATIONS + ")";
            }
            else
            {
                fmtObj[0] = " WHERE STATION_NO != '" + parentst + "' AND TRIM(AISLE_STATION_NO) IS NULL ";
            }

            if (key.ReferenceCondition() != null)
            {
                fmtObj[1] = "AND " + key.ReferenceCondition();
                if (key.SortCondition() != null)
                {
                    fmtObj[2] = "ORDER BY " + key.SortCondition();
                }
            }
            else if (key.SortCondition() != null)
            {
                fmtObj[1] = "ORDER BY " + key.SortCondition();
            }

            String sqlcountstring = SimpleFormat.format(fmtCountSQL, fmtObj);
            System.out.println("SQL[" + sqlcountstring + "]");
            countret = wStatement.executeQuery(sqlcountstring);

            while (countret.next())
            {
                count = countret.getInt("COUNT");
            }
            //<jp>件数がMAXDISP以下の場合にのみ検索を実行します</jp>
            //<en>Carry out the search only when the number of data is lower than MAXDISP.</en>
            if (count <= MAXDISP)
            {
                String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
                wResultSet = wStatement.executeQuery(sqlstring);
            }
            else
            {
                wResultSet = null;
            }
        }
        catch (SQLException e)
        {
            //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
            //<en>6126001 = Database error occured. {0}</en>
            RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
            //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
            //<en>Here, the ReadWriteException will be thrown with an error message</en>
            throw (new ReadWriteException(e));
        }
        finally
        {
            try
            {
                if (countret != null)
                {
                    countret.close();
                    countret = null;
                }
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
    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
