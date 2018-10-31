//$Id: ToolShelfHandler.java 7721 2010-03-24 08:19:38Z shibamoto $
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
import java.text.DecimalFormat;
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
import jp.co.daifuku.wms.asrs.tool.location.Bank;
import jp.co.daifuku.wms.asrs.tool.location.Shelf;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.Zone;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**<jp>
 * <code>Shelf</code>クラスをデータベースから取得したり、データベースに保管する為に利用するクラスです。
 * <code>Shelf</code>クラスの取得に関しては、<code>StationFactory</code>を利用してください。
 * <code>Shelf</code>クラスには<code>getHandler</code>メソッドが用意されていますので、
 * 対応するHandlerが必要な場合は、<code>getHandler</code>メソッドを利用して取得します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/09</TD><TD>okamura</TD><TD>ゾーンIDを取得するメソッド（findZones）を追加。<BR>
 * Tool画面のソフトゾーン問合せで使用しています。<BR>
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7721 $, $Date: 2010-03-24 17:19:38 +0900 (水, 24 3 2010) $
 * @author  $Author: shibamoto $
 </jp>*/
/**<en>
 * This class is used to retrieve/store the <code>Shelf</code> class from/to the database.
 * Please utilize <code>StationFactory</code> when retrieving the <code>Shelf</code> class.
 * As <code>getHandler</code> method has been prepared in the <code>Shelf</code> class,
 * if a support of the Handler is needed, please use <code>getHandler</code> method and obtain.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/09</TD><TD>okamura</TD><TD>Correction: a method to retrieve the zone ID (findZones) was added.<br>
 * This is used in soft zone inquiry of Tool screen.<br>
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7721 $, $Date: 2010-03-24 17:19:38 +0900 (水, 24 3 2010) $
 * @author  $Author: shibamoto $
 </en>*/
public class ToolShelfHandler
        extends ToolStationHandler
{

    // Class fields --------------------------------------------------
    private static final String SHELF_HANDLE = "jp.co.daifuku.wms.base.dbhandler.ShelfHandler";

    private static final String DOUBLEDEEPSHELF_HANDLE = "jp.co.daifuku.wms.asrs.dbhandler.DoubleDeepShelfHandler";

    // Class variables -----------------------------------------------
    private boolean wScreenFlag = false;

    /**<jp> テーブル名 </jp>*/
    /**<en> name of the table </en>*/
    private String wTableName = "TEMP_DMSHELF";

    /**<jp> TEMP_WAREHOUSEテーブル名 </jp>*/
    /**<en> name of the TEMP_WAREHOUSE table</en>*/
    protected final String wWarehouseTableName = "TEMP_DMWAREHOUSE";

    /**<jp> TEMP_STATIONTYPEテーブル名 </jp>*/
    /**<en> name of the TEMP_STATIONTYPE table</en>*/
    protected final String wStationTypeTableName = "TEMP_DMSTATIONTYPE";

    // Constructors --------------------------------------------------
    /**<jp>
     * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
     * @param conn データベース接続用 Connection
     </jp>*/
    /**<en>
     * Generate instance by specifying <code>Connection</code> to connect with database.
     * @param conn :Connection to connect with database
     </en>*/
    public ToolShelfHandler(Connection conn)
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
    public ToolShelfHandler(Connection conn, String tablename)
    {
        super(conn);
        wTableName = tablename;
    }

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Versin and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 7721 $,$Date: 2010-03-24 17:19:38 +0900 (水, 24 3 2010) $");
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * isScreenとは、Shelfの検索結果で画面に不要な項目はmakeShelf()でセットしないようにして、
     * レスポンスを早くするためのFlagです。
     * @return isScreen
     </jp>*/
    /**<en>
     * isScreen is a flag which enables the quicker responses in Shelf search results display 
     * by avoiding unrequried items to show by placing makeShelf().
     * @return isScreen
     </en>*/
    public boolean getisScreen()
    {
        return wScreenFlag;
    }

    /**<jp>
     * isScreenとは、Shelfの検索結果で画面に不要な項目はmakeShelf()でセットしないようにして、
     * レスポンスを早くするためのFlagです。
     * 参考：Shelf2000件データの検索で、makeShelf()内のParentStationを生成するmakeStation(wConn, psnum)を実行すると
     * 30秒～40秒かかります。
     * これをセットすることにより、約２～４秒で検索可能です。
     </jp>*/
    /**<en>
     * isScreen is a flag which enables the quicker responses in Shelf search results display 
     * by avoiding unrequried items to show by placing makeShelf().
     * F.Y.I. In case of searching 2000 shelves, if makeStation(wConn, psnum), which generates the 
     * ParentStation in makeShelf(), gets executed, it takes 30 - 40 seconds, while it only takes
     * 2 - 4 seconds if this flag is set.
     </en>*/
    public void setisScreen()
    {
        wScreenFlag = true;
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

            sqlstring =
                    "DELETE FROM " + wStationTypeTableName + " WHERE CLASS_NAME = " + "'" + SHELF_HANDLE + "'"
                            + " OR CLASS_NAME = " + "'" + DOUBLEDEEPSHELF_HANDLE + "'";
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
     * 棚を検索し、取得します。
     * @param key 検索のためのKey
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search and retrieve the location.
     * @param key :Key for search
     * @return    :the array of the created object
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public ToolEntity[] find(ToolSearchKey key)
            throws ReadWriteException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        Shelf[] fndStation = null;
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

            // make Shelf instances from resultset
            fndStation = makeShelf(rset);
        }
        catch (NotFoundException e)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en> This should not happen;</en>
            e.printStackTrace();
            throw (new ReadWriteException(e));
        }
        catch (DataExistsException ee)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en> This should not happen;</en>
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

        return (fndStation);
    }

    /**<jp>
     * アイルの一覧を返します。
     * <CODE>Statement</CODE>はwStatementを使用しカーソルオープンしています。<BR>
     * 一時的にwStatementを使用しているだけで、いずれこのメソッド内でカーソルを生成するように変更します。
     * @return AisleのString配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    public String[] findAisles(String whstno)
            throws ReadWriteException
    {
        Vector vec = new Vector();
        String[] aisles = null;
        Object[] fmtObj = new Object[1];
        ResultSet rset = null;

        String fmtSQL =
                "SELECT PARENT_STATION_NO FROM " + wTableName + " {0} GROUP BY PARENT_STATION_NO "
                        + " ORDER BY PARENT_STATION_NO ";

        try
        {
            if (whstno != null)
            {
                fmtObj[0] = " WHERE WH_STATION_NO = " + DBFormat.format(whstno) + " ";
            }

            String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
            rset = executeSQL(sqlstring, true); // private exec sql method

            while (rset.next())
            {
                vec.add(DBFormat.replace(rset.getString("PARENT_STATION_NO")));
            }

            aisles = new String[vec.size()];
            vec.copyInto(aisles);
        }
        catch (DataExistsException ee)
        {
            ee.printStackTrace();
            throw (new ReadWriteException(ee));
        }
        catch (NotFoundException e)
        {
            e.printStackTrace();
            throw (new ReadWriteException(e));
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

        return (aisles);
    }

    /**<jp>
     * 指定された倉庫におけるバンクの一覧を返します。
     * <CODE>Statement</CODE>はwStatementを使用しカーソルオープンしています。<BR>
     * 一時的にwStatementを使用しているだけで、いずれこのメソッド内でカーソルを生成するように変更します。
     * @param whstno   倉庫ステーションNo
     * @return 指定された条件のBankのint配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Return the list of bank of the specified warehouse.
     * <CODE>Statement</CODE> uses wStatement to open a cursor.<BR>
     * wStatement is used only as a temporary measure; a modification will be made at some stage
     * so taht a cursor will be generated within this method.
     * @param whstno   :warehouse station no.
     * @return :int array of the Bank that meets the specified conditions
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public int[] findBanks(String whstno)
            throws ReadWriteException
    {
        Vector vec = new Vector();
        int[] banks = null;
        ResultSet rset = null;

        String sqlstring =
                "SELECT BANK_NO FROM " + wTableName + " WHERE WH_STATION_NO = " + DBFormat.format(whstno) + " "
                        + " GROUP BY BANK_NO " + " ORDER BY BANK_NO ";

        try
        {
            rset = executeSQL(sqlstring, true); // private exec sql method

            while (rset.next())
            {
                vec.add(new Integer(rset.getInt("BANK_NO")));
            }

            banks = new int[vec.size()];
            for (int i = 0; i < banks.length; i++)
            {
                Integer bank = (Integer)vec.remove(0);
                banks[i] = bank.intValue();
            }
        }
        catch (DataExistsException ee)
        {
            ee.printStackTrace();
            throw (new ReadWriteException(ee));
        }
        catch (NotFoundException e)
        {
            e.printStackTrace();
            throw (new ReadWriteException(e));
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

        return (banks);
    }

    /**<jp>
     * 指定された倉庫におけるバンクごとのベイのMAX値の一覧を返します。<BR>
     * バンク1のベイが44 バンク2のベイが44 バンク3のベイが18の場合<BR>
     * Bays[0]=44, Bays[1]=44, Bays[2]=18 を返します。<BR>
     * <CODE>Statement</CODE>はwStatementを使用しカーソルオープンしています。<BR>
     * 一時的にwStatementを使用しているだけで、いずれこのメソッド内でカーソルを生成するように変更します。
     * @param whstno   倉庫ステーションNo
     * @return 指定された条件のBayのMAX値の集合
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Return a list of Bay Max value per bank of the specified warehouse.<BR>
     * Example case: bank 1 with 44 bays, bank 2 with 44 bays, bank 3 with 18 bays;<BR>
     * Returns Bays[0]=44, Bays[1]=44, Bays[2]=18.<BR>
     * <CODE>Statement</CODE> uses wStatement to open cursors.<BR>
     * wStatement is used only as a temporary measure; a modification will be made at some stage
     * so that the cursors will be generated within this method.
     * @param whstno   :warehouse station no.
     * @return :group of Bay Max value under specified conditions
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public int[] findBays(String whstno)
            throws ReadWriteException
    {
        Vector vec = new Vector();
        int[] bays = null;
        ResultSet rset = null;

        String sqlstring =
                "SELECT MAX(BAY_NO) BAY_NO FROM " + wTableName + " WHERE WH_STATION_NO = " + DBFormat.format(whstno)
                        + " " + " GROUP BY BANK_NO " + " ORDER BY BANK_NO ";

        try
        {
            rset = executeSQL(sqlstring, true); // private exec sql method

            while (rset.next())
            {
                vec.add(new Integer(rset.getInt("BAY_NO")));
            }

            bays = new int[vec.size()];
            for (int i = 0; i < bays.length; i++)
            {
                Integer bay = (Integer)vec.remove(0);
                bays[i] = bay.intValue();
            }
        }
        catch (DataExistsException ee)
        {
            ee.printStackTrace();
            throw (new ReadWriteException(ee));
        }
        catch (NotFoundException e)
        {
            e.printStackTrace();
            throw (new ReadWriteException(e));
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

        return (bays);
    }

    /**<jp>
     * 指定された倉庫におけるバンクごとのレベルのMAX値の一覧を返します。<BR>
     * バンク1のレベルが22 バンク2のレベルが22 バンク3のレベルが4の場合
     * Levels[0]=22, Levels[1]=22, Levels[2]=4 を返します。<BR>
     * <CODE>Statement</CODE>はwStatementを使用しカーソルオープンしています。<BR>
     * 一時的にwStatementを使用しているだけで、いずれこのメソッド内でカーソルを生成するように変更します。
     * @param whstno   倉庫ステーションNo
     * @return 指定された条件のLevelのMAX値の集合
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Return a list of level Max value per bank of the warehouse specified.<BR>
     * Example case: bank1 with 22 levels, bank2 with 22 levels, bank 3 with 4 levels;
     * Returns Levels[0]=22, Levels[1]=22, Levels[2]=4.<BR>
     * <CODE>Statement</CODE> uses wStatement to open cursors.<BR>
     * wStatement is used only as a temporary measure; a modification will be made at some stage
     * so that the cursors will be generated within this method.
     * @param whstno   :warehouse station no.
     * @return :group of Level Max value under specified conditions
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public int[] findLevels(String whstno)
            throws ReadWriteException
    {
        Vector vec = new Vector();
        int[] levels = null;
        ResultSet rset = null;

        String sqlstring =
                "SELECT MAX(LEVEL_NO) LEVEL_NO FROM " + wTableName + " WHERE WH_STATION_NO = "
                        + DBFormat.format(whstno) + " " + " GROUP BY BANK_NO " + " ORDER BY BANK_NO ";

        try
        {
            rset = executeSQL(sqlstring, true); // private exec sql method

            while (rset.next())
            {
                vec.add(new Integer(rset.getInt("LEVEL_NO")));
            }
            levels = new int[vec.size()];
            for (int i = 0; i < levels.length; i++)
            {
                Integer level = (Integer)vec.remove(0);
                levels[i] = level.intValue();
            }
        }
        catch (DataExistsException ee)
        {
            ee.printStackTrace();
            throw (new ReadWriteException(ee));
        }
        catch (NotFoundException e)
        {
            e.printStackTrace();
            throw (new ReadWriteException(e));
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
        return (levels);
    }

    /**<jp>
     * 指定された条件におけるソフトゾーンIDを返します。<BR>
     * <CODE>Statement</CODE>はwStatementを使用しカーソルオープンしています。<BR>
     * 一時的にwStatementを使用しているだけで、いずれこのメソッド内でカーソルを生成するように変更します。
     * @param key 検索のためのKey
     * @return 指定された条件のSOFTZONEID
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Return the soft zone ID acording to the specified consitions.<BR>
     * <CODE>Statement</CODE> uses wStatement to open cursors.<BR>
     * wStatement is used only as a temporary measure; a modification will be made at some stage
     * so that the cursors will be generated within this method.
     * @param key :Key for search
     * @return :SOFTZONEID which meet the specified conditions
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public int[] findZones(ToolSearchKey key)
            throws ReadWriteException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        Vector vec = new Vector();
        int[] softzoneid = null;
        Object[] fmtObj = new Object[2];

        // for database access
        ResultSet rset = null;

        String fmtSQL = "SELECT DISTINCT(SOFT_ZONE_ID) SOFT_ZONE_ID FROM " + wTableName + " {0} {1}";

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
            while (rset.next())
            {
                vec.add(new Integer(rset.getInt("SOFT_ZONE_ID")));
            }
            softzoneid = new int[vec.size()];
            for (int i = 0; i < softzoneid.length; i++)
            {
                Integer id = (Integer)vec.remove(0);
                softzoneid[i] = id.intValue();
            }

        }
        catch (DataExistsException ee)
        {
            ee.printStackTrace();
            throw (new ReadWriteException(ee));
        }
        catch (NotFoundException e)
        {
            e.printStackTrace();
            throw (new ReadWriteException(e));
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
        return (softzoneid);
    }

    /**<jp>
     * 指定された倉庫内のフリーアロケーション棚情報を返します。<BR>
     * 棚No.、バンク、ベイ、レベル、倉庫、ハードゾーンを返します。<BR>
     * 同一バンク、ベイ、レベルの場合、最小アドレスの棚を返します。<BR>
     * <CODE>Statement</CODE>はwStatementを使用しカーソルオープンしています。<BR>
     * 一時的にwStatementを使用しているだけで、いずれこのメソッド内でカーソルを生成するように変更します。
     * @param key 検索のためのKey
     * @return 指定された条件のSHELF
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    public Shelf[] findFreeAllocationMinShelf(ToolSearchKey key)
            throws ReadWriteException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        Vector tmpShelfVect = new Vector(); // temporary store for Station instances

        // for database access
        ResultSet rset = null;
        Object[] fmtObj = new Object[2];

        String fmtSQL =
                "SELECT MIN(STATION_NO) MIN_ST, BANK_NO, BAY_NO, LEVEL_NO, MIN(WH_STATION_NO) MIN_WH_ST, MIN(HARD_ZONE_ID) MIN_HZ_ID, MIN(SOFT_ZONE_ID) MIN_SZ_ID FROM "
                        + wTableName + " {0} GROUP BY BANK_NO, BAY_NO, LEVEL_NO {1}";

        try
        {
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
            rset = executeSQL(sqlstring, true); // private exec sql method

            // make Shelf instances from resultset
            while (rset.next())
            {
                Shelf tmpShelf = new Shelf(DBFormat.replace(rset.getString("MIN_ST")));
                tmpShelf.setBankNo(rset.getInt("BANK_NO"));
                // bay
                tmpShelf.setBayNo(rset.getInt("BAY_NO"));
                // level
                tmpShelf.setLevelNo(rset.getInt("LEVEL_NO"));
                // warehouse
                tmpShelf.setWhStationNo(DBFormat.replace(rset.getString("MIN_WH_ST")));
                // hardzone
                tmpShelf.setHardZoneId(rset.getInt("MIN_HZ_ID"));
                // softzone
                tmpShelf.setSoftZoneId(rset.getInt("MIN_SZ_ID"));

                tmpShelfVect.add(tmpShelf);
            }
        }
        catch (NotFoundException e)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en> This should not happen;</en>
            e.printStackTrace();
            throw (new ReadWriteException(e));
        }
        catch (DataExistsException ee)
        {
            //<jp> Findなので、起こらないはず</jp>
            //<en> This should not happen;</en>
            ee.printStackTrace();
            throw (new ReadWriteException(ee));
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

        Shelf[] fndStation = new Shelf[tmpShelfVect.size()];
        tmpShelfVect.copyInto(fndStation);

        return (fndStation);
    }

    /**<jp>
     * <CODE>ToolSearchKey</CODE>で与えられた条件を元に、該当するデータの件数を返します。
     * @param key 検索のためのKey
     * @return 検索結果の件数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Based on the conditions given by <CODE>ToolSearchKey</CODE>, it returns the number of 
     * corresponding data.
     * @param key :Key for search
     * @return :number of search results
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public int count(ToolSearchKey key)
            throws ReadWriteException
    {
        Statement stmt = null;
        ResultSet rset = null;
        int count = 0;
        Object[] fmtObj = new Object[1];

        try
        {
            String fmtSQL = "SELECT COUNT(*) COUNT FROM " + wTableName + " {0} ";

            if (key.ReferenceCondition() != null)
            {
                fmtObj[0] = " WHERE " + key.ReferenceCondition();
            }
            String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
            stmt = wConn.createStatement();
            rset = stmt.executeQuery(sqlstring);

            while (rset.next())
            {
                count = rset.getInt("count");
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }
        return count;
    }

    /**<jp>
     * <CODE>ToolSearchKey</CODE>で与えられた条件を元に、該当するデータの件数を返します。
     * @param fbank  開始バンク
     * @param fbay   開始ベイ
     * @param flevel 開始レベル
     * @param tbank  終了バンク
     * @param tbay   終了ベイ
     * @param tlevel 終了レベル
     * @param whstno 倉庫ステーションNo.
     * @return 検索結果の件数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Based on the conditions given by <CODE>ToolSearchKey</CODE>, it returns the number of 
     * corresponding data.
     * @param fbank  from bank
     * @param fbay   from bay
     * @param flevel from level
     * @param tbank  to bank
     * @param tbay   to bay
     * @param tlevel to level
     * @param whstno warehouse no
     * @return :number of search results
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public int count(int fbank, int fbay, int flevel, int tbank, int tbay, int tlevel, String whstno)
            throws ReadWriteException
    {
        Statement stmt = null;
        ResultSet rset = null;
        int count = 0;
        Object[] fmtObj = new Object[3];

        try
        {
            String fmtSQL =
                    "SELECT COUNT(*) COUNT FROM " + wTableName + " WHERE WH_STATION_NO = '" + whstno + "' {0} {1} {2} ";

            if (fbank > 0)
            {
                fmtObj[0] = " AND (BANK_NO >= " + fbank + " AND BANK_NO <= " + tbank + ")";
            }
            if (fbay > 0)
            {
                fmtObj[1] = " AND (BAY_NO >= " + fbay + " AND BAY_NO <= " + tbay + ")";
            }
            if (flevel > 0)
            {
                fmtObj[2] = " AND (LEVEL_NO >= " + flevel + " AND LEVEL_NO <= " + tlevel + ")";
            }

            String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
            stmt = wConn.createStatement();
            rset = stmt.executeQuery(sqlstring);

            while (rset.next())
            {
                count = rset.getInt("count");
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }
        return count;
    }

    /**<jp>
     * 指定された条件におけるフリーアロケーション棚の件数を返します。<BR>
     * <CODE>Statement</CODE>はwStatementを使用しカーソルオープンしています。<BR>
     * 一時的にwStatementを使用しているだけで、いずれこのメソッド内でカーソルを生成するように変更します。
     * @param key 検索のためのKey
     * @return 指定された条件のSHELF
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Return the shelf of free allocation acording to the specified consitions.<BR>
     * <CODE>Statement</CODE> uses wStatement to open cursors.<BR>
     * wStatement is used only as a temporary measure; a modification will be made at some stage
     * so that the cursors will be generated within this method.
     * @param key :Key for search
     * @return :SHELF which meet the specified conditions
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public int countFreeAllocationShelf(ToolSearchKey key)
            throws ReadWriteException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        Statement stmt = null;
        ResultSet rset = null;
        int count = 0;
        Object[] fmtObj = new Object[1];

        try
        {
            String fmtSQL =
                    "SELECT COUNT(*) COUNT FROM " + wTableName + ", " + wWarehouseTableName
                            + " WHERE TEMP_DMSHELF.WH_STATION_NO = TEMP_DMWAREHOUSE.STATION_NO"
                            + " AND TEMP_DMWAREHOUSE.FREE_ALLOCATION_TYPE = "
                            + DBFormat.format(SystemDefine.FREE_ALLOCATION_ON) + " {0} {1}";

            if (key.ReferenceCondition() != null)
            {
                fmtObj[0] = " AND " + key.ReferenceCondition();
            }

            String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
            stmt = wConn.createStatement();
            rset = stmt.executeQuery(sqlstring);

            while (rset.next())
            {
                count = rset.getInt("count");
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }
        return count;
    }

    /**<jp>
     * 倉庫指定された条件におけるフリーアロケーション棚の件数を返します。<BR>
     * <CODE>Statement</CODE>はwStatementを使用しカーソルオープンしています。<BR>
     * 一時的にwStatementを使用しているだけで、いずれこのメソッド内でカーソルを生成するように変更します。
     * @param key 検索のためのKey
     * @return 指定された条件のSHELF
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Return the shelf of free allocation acording to the specified consitions.<BR>
     * <CODE>Statement</CODE> uses wStatement to open cursors.<BR>
     * wStatement is used only as a temporary measure; a modification will be made at some stage
     * so that the cursors will be generated within this method.
     * @param key :Key for search
     * @return :SHELF which meet the specified conditions
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public int countNoWarehouseShelf(ToolSearchKey key)
            throws ReadWriteException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        Statement stmt = null;
        ResultSet rset = null;
        int count = 0;
        Object[] fmtObj = new Object[1];

        try
        {
            String fmtSQL =
                    "SELECT COUNT(*) COUNT FROM " + wTableName + ", " + wWarehouseTableName
                            + " WHERE TEMP_DMSHELF.WH_STATION_NO = TEMP_DMWAREHOUSE.STATION_NO"
                            + " AND TEMP_DMWAREHOUSE.FREE_ALLOCATION_TYPE = "
                            + DBFormat.format(SystemDefine.FREE_ALLOCATION_ON) + " {0}";

            if (key.ReferenceCondition() != null)
            {
                fmtObj[0] = " AND " + key.ReferenceCondition();
            }

            String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
            stmt = wConn.createStatement();
            rset = stmt.executeQuery(sqlstring);

            while (rset.next())
            {
                count = rset.getInt("count");
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }
        return count;
    }

    /**<jp>
     * ソフトゾーンが指定されている（0以外）の棚の件数を返します。<BR>
     * <CODE>Statement</CODE>はwStatementを使用しカーソルオープンしています。<BR>
     * 一時的にwStatementを使用しているだけで、いずれこのメソッド内でカーソルを生成するように変更します。
     * @param key 検索のためのKey
     * @return 指定された条件のSHELF
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    public int countNotZeroSoftZone(ToolSearchKey key)
            throws ReadWriteException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        Statement stmt = null;
        ResultSet rset = null;
        int count = 0;
        Object[] fmtObj = new Object[1];

        try
        {
            String fmtSQL = "SELECT COUNT(*) COUNT FROM " + wTableName + " WHERE SOFT_ZONE_ID != 0" + " {0}";

            if (key.ReferenceCondition() != null)
            {
                fmtObj[0] = " AND " + key.ReferenceCondition();
            }

            String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
            stmt = wConn.createStatement();
            rset = stmt.executeQuery(sqlstring);

            while (rset.next())
            {
                count = rset.getInt("count");
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }
        return count;
    }

    /**<jp>
     * 該当する棚の件数を返します。
     * バンク、ベイ、アドレスが同じ棚は1件としてカウントします。
     * @param fbank  開始バンク
     * @param fbay   開始ベイ
     * @param flevel 開始レベル
     * @param tbank  終了バンク
     * @param tbay   終了ベイ
     * @param tlevel 終了レベル
     * @param whstno 倉庫ステーションNo.
     * @return 検索結果の件数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    public int countLocation(int fbank, int fbay, int flevel, int tbank, int tbay, int tlevel, String whstno)
            throws ReadWriteException
    {
        Statement stmt = null;
        ResultSet rset = null;
        int count = 0;
        Object[] fmtObj = new Object[5];

        try
        {
            String fmtSQL =
                    "SELECT COUNT(*) FROM (SELECT COUNT(*) COUNT FROM " + wTableName + " WHERE WH_STATION_NO = "
                            + DBFormat.format(whstno) + " {0} {1} {2} {3} {4}";

            if (fbank > 0)
            {
                fmtObj[0] = " AND (BANK_NO >= " + fbank + " AND BANK_NO <= " + tbank + ")";
            }
            if (fbay > 0)
            {
                fmtObj[1] = " AND (BAY_NO >= " + fbay + " AND BAY_NO <= " + tbay + ")";
            }
            if (flevel > 0)
            {
                fmtObj[2] = " AND (LEVEL_NO >= " + flevel + " AND LEVEL_NO <= " + tlevel + ")";
            }
            fmtObj[3] = " GROUP BY BANK_NO, BAY_NO, LEVEL_NO) ";

            String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
            stmt = wConn.createStatement();
            rset = stmt.executeQuery(sqlstring);

            while (rset.next())
            {
                count = rset.getInt("count");
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
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }
        return count;
    }

    /**<jp>
     * データベースに新規棚情報を作成します。
     * @param tgt 作成する棚情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws DataExistsException 既に、同じ棚がデータベースに登録済みの場合に通知されます。
     </jp>*/
    /**<en>
     * Newly create the location information in database.
     * @param tgt :entity instance which preserves the location information to create
     * @throws ReadWriteException  :Notifies if error occured in connection with database.
     * @throws DataExistsException :Notifies if identical location is already registered in database.
     </en>*/
    public void create(ToolEntity tgt)
            throws ReadWriteException,
                DataExistsException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        String fmtSQL = "INSERT INTO " + wTableName + " (" + "  STATION_NO" // 0
                + ", BANK_NO" // 1
                + ", BAY_NO" // 2
                + ", LEVEL_NO" // 3
                + ", ADDRESS_NO" // 4
                + ", WH_STATION_NO" // 5
                + ", PROHIBITION_FLAG" // 6
                + ", STATUS_FLAG" // 7
                + ", HARD_ZONE_ID" // 8
                + ", SOFT_ZONE_ID" // 9
                + ", PARENT_STATION_NO" // 10
                + ", ACCESS_NG_FLAG" // 11
                + ", PRIORITY" // 12
                + ", PAIR_STATION_NO" // 13
                + ", SIDE" // 14
                + ", WIDTH" // 15
                + ", LOCATION_USE_FLAG" // 16
                + ") values (" + "{0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12},{13},{14},{15},{16}" + ")";
        String fmtSQL_StationType = "INSERT INTO " + wStationTypeTableName + " (" + "  STATION_NO" // 0
                + ",  CLASS_NAME" // 1
                + ") VALUES (" + "{0}, '" + SHELF_HANDLE + "'" + ")";


        Shelf tgtShelf;
        String sqlstring;
        //-------------------------------------------------
        // process
        //-------------------------------------------------
        if (tgt instanceof Shelf)
        {
            tgtShelf = (Shelf)tgt;
        }
        else
        {
            //<jp>致命的なエラーが発生しました。{0}</jp>
            //<en>Fatal error occurred.{0}</en>
            RmiMsgLogClient.write("6126499" + wDelim + "Illegal argument. Set Shelf Instance.",
                    this.getClass().getName());
            throw (new ReadWriteException());
        }

        try
        {
            // setting Station information to Object array
            Object[] fmtObj = setToArray(tgtShelf);
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
            //<en> This should not happen;</en>
            e.printStackTrace();
            throw (new ReadWriteException(e));
        }
    }

    /**<jp>
     * データベースに新規棚情報を作成します。
     * @param tgt 作成する棚情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws DataExistsException 既に、同じ棚がデータベースに登録済みの場合に通知されます。
     </jp>*/
    /**<en>
     * Newly create the location information in database.
     * @param tgt :entity instance which preserves the location information to create
     * @throws ReadWriteException  :Notifies if error occured in connection with database.
     * @throws DataExistsException :Notifies if identical location is already registered in database.
     </en>*/
    public void createDoubleDeep(ToolEntity tgt)
            throws ReadWriteException,
                DataExistsException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        String fmtSQL = "INSERT INTO " + wTableName + " (" + "  STATION_NO" // 0
                + ", BANK_NO" // 1
                + ", BAY_NO" // 2
                + ", LEVEL_NO" // 3
                + ", ADDRESS_NO" // 4
                + ", WH_STATION_NO" // 5
                + ", PROHIBITION_FLAG" // 6
                + ", STATUS_FLAG" // 7
                + ", HARD_ZONE_ID" // 8
                + ", SOFT_ZONE_ID" // 9
                + ", PARENT_STATION_NO" // 10
                + ", ACCESS_NG_FLAG" // 11
                + ", PRIORITY" // 12
                + ", PAIR_STATION_NO" // 13
                + ", SIDE" // 14
                + ", WIDTH" // 15
                + ", LOCATION_USE_FLAG" // 16
                + ") values (" + "{0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12},{13},{14},{15},{16}" + ")";
        String fmtSQL_StationType = "INSERT INTO " + wStationTypeTableName + " (" + "  STATION_NO" // 0
                + ",  CLASS_NAME" // 1
                + ") VALUES (" + "{0}, '" + DOUBLEDEEPSHELF_HANDLE + "'" + ")";


        Shelf tgtShelf;
        String sqlstring;
        //-------------------------------------------------
        // process
        //-------------------------------------------------
        if (tgt instanceof Shelf)
        {
            tgtShelf = (Shelf)tgt;
        }
        else
        {
            //<jp>致命的なエラーが発生しました。{0}</jp>
            //<en>Fatal error occurred.{0}</en>
            RmiMsgLogClient.write("6126499" + wDelim + "Illegal argument. Set Shelf Instance.",
                    this.getClass().getName());
            throw (new ReadWriteException());
        }

        try
        {
            // setting Station information to Object array
            Object[] fmtObj = setToArray(tgtShelf);
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
            //<en> This should not happen;</en>
            e.printStackTrace();
            throw (new ReadWriteException(e));
        }
    }

    /**<jp>
     * データベースの棚情報を変更します。
     * @param tgt 作成する棚情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 変更すべき棚が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the location information in database.
     * @param tgt :entity instance which preserves the location information to create
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if location to modify cannot be found.
     </en>*/
    public void modify(ToolEntity tgt)
            throws ReadWriteException,
                NotFoundException
    {
        //<jp> DBをアップデート</jp>
        //<jp>-------------------------------------------------</jp>
        //<en> UPdate DB.</en>
        //<en>-------------------------------------------------</en>
        // variable define
        //-------------------------------------------------
        String fmtSQL = "UPDATE shelf set" + ", BANK_NO = {1}" // 1
                + ", BAY_NO = {2}" // 2
                + ", LEVEL_NO = {3}" // 3
                + ", ADDRESS_NO = {4}" // 4
                + ", WH_STATION_NO = {5}" // 5
                + ", PROHIBITION_FLAG = {6}" // 6
                + ", STATUS_FLAG = {7}" // 7
                + ", HARD_ZONE_ID = {8}" // 8
                + ", SOFT_ZONE_ID = {9}" // 9
                + ", PARENT_STATION_NO = {10}" // 10
                + ", ACCESS_NG_FLAG = {11}" // 11
                + ", PRIORITY = {12}" // 12
                + ", PAIR_STATION_NO = {13}" // 13
                + ", SIDE = {14}" // 14
                + ", WIDTH = {15}" // 15
                + ", LOCATION_USE_FLAG = {16}" // 16
                + " WHERE STATION_NO = {0}"; // 0


        Shelf tgtShelf;
        String sqlstring;
        //-------------------------------------------------
        // process
        //-------------------------------------------------
        if (tgt instanceof Shelf)
        {
            tgtShelf = (Shelf)tgt;
        }
        else
        {
            //<jp>致命的なエラーが発生しました。{0}</jp>
            //<en>Fatal error occurred.{0}</en>
            RmiMsgLogClient.write("6126499" + wDelim + "Illegal argument. Set Shelf Instance.",
                    this.getClass().getName());
            throw (new ReadWriteException());
        }

        try
        {
            // setting Station information to Object array
            Object[] fmtObj = setToArray(tgtShelf);
            // create actual SQL
            sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
            // execute the sql
            executeSQL(sqlstring, false);
        }
        catch (DataExistsException ee)
        {
            //<jp> updateなので、起こらないはず</jp>
            //<en> This should not happen;</en>

            ee.printStackTrace();
            throw (new ReadWriteException(ee));
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
     * Modify the location information in database. The contents and conditions of the modification
     * will be obtained by AlterKey.
     * @param  key :AlterKey isntance which preserves the contents and conditions of modification 
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if data to modify cannot be found.
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
            //<en> Exception if update value has not been set;</en>
            Object[] tobj = {
                table
            };
            RmiMsgLogClient.write(6126005, LogMessage.F_ERROR, this.getClass().getName(), tobj);
            throw (new InvalidDefineException("6126005"));
        }
        fmtObj[1] = key.ModifyContents(table);

        if (key.ReferenceCondition(table) == null)
        {
            //<jp> 更新条件がセットされていない場合は例外</jp>
            //<en> Exception if update conditions have not been set;</en>
            Object[] tobj = {
                table
            };
            RmiMsgLogClient.write(6126006, LogMessage.F_ERROR, this.getClass().getName(), tobj);
            throw (new InvalidDefineException("6126006"));
        }
        fmtObj[2] = " WHERE " + key.ReferenceCondition(table);

        String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);

        try
        {
            executeSQL(sqlstring, false); // private exec sql method
        }
        catch (DataExistsException ee)
        {
            //<jp> updateなので、起こらないはず</jp>
            //<en> This should not happen;</en>

            ee.printStackTrace();
            throw (new ReadWriteException(ee));
        }
    }

    /**<jp>
     * zoneインスタンスが保持しているバンク、ベイ、レベルの範囲にある指定された値を更新します。
     * @param zone  zoneインスタンス
     * @param alkey 更新値を保持したkey
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 変更対象がデータベースに見つからない場合に通知されます。
     * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
     </jp>*/
    /**<en>
     * Update the specified values in the bank, bay and level range that zone instance preserves.
     * @param zone  :zone instance
     * @param alkey :key which preserve the update value
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if data to modify cannot be found in database..
     * @throws InvalidDefineException :Notifies if the contents of update has not been set.
     </en>*/
    public void modify(ToolEntity zone, ToolAlterKey alkey)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        Zone ins = (Zone)zone;
        String fmtSQL =
                " UPDATE {0} " + " SET {1} " + " WHERE BANK_NO  >= " + ins.getStartBankNo() + " AND BANK_NO  <= "
                        + ins.getEndBankNo() + " AND   BAY_NO   >= " + ins.getStartBayNo() + " AND BAY_NO   <= "
                        + ins.getEndBayNo() + " AND   LEVEL_NO >= " + ins.getStartLevelNo() + " AND LEVEL_NO <= "
                        + ins.getEndLevelNo() + " AND {2} ";

        Object[] fmtObj = new Object[3];
        fmtObj[0] = wTableName;
        if (alkey.ModifyContents(wTableName) == null)
        {
            //<jp> 更新値がセットされていない場合は例外</jp>
            //<en> Exception if update value has not been set;</en>
            Object[] tobj = {
                wTableName
            };
            RmiMsgLogClient.write(6126005, LogMessage.F_ERROR, this.getClass().getName(), tobj);
            throw (new InvalidDefineException("6126005"));
        }
        fmtObj[1] = alkey.ModifyContents(wTableName);
        if (alkey.ReferenceCondition(wTableName) == null)
        {
            //<jp> 更新条件がセットされていない場合は例外</jp>
            //<en> Exception if update conditiosn have not been set;</en>
            Object[] tobj = {
                wTableName
            };
            RmiMsgLogClient.write(6126006, LogMessage.F_ERROR, this.getClass().getName(), tobj);
            throw (new InvalidDefineException("6126006"));
        }
        fmtObj[2] = alkey.ReferenceCondition(wTableName);

        String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);

        try
        {
            executeSQL(sqlstring, false); // private exec sql method
        }
        catch (DataExistsException ee)
        {
            //<jp> updateなので、起こらないはず</jp>
            //<en> This should not happen;</en>
            ee.printStackTrace();
            throw (new ReadWriteException(ee));
        }
    }


    /**<jp>
     * データベースの棚情報を変更します。ハードゾーン項目を条件なしで更新します。
     * @param  zone 変更内容
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException  変更対象がデータベースに見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * MOdify the location information in database. It updates the hard zone items with no condition.
     * @param  zone :contents of modification
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if target data to modify cannot be found in database.
     </en>*/
    public void modifyHardZoneId(int zone)
            throws ReadWriteException,
                NotFoundException
    {
        String sqlstring = "UPDATE " + wTableName + " SET HARD_ZONE_ID = " + zone;
        try
        {
            executeSQL(sqlstring, false);
        }
        catch (DataExistsException ee)
        {
            //<jp> updateなので、起こらないはず</jp>
            //<en> This should not happen.</en>
            ee.printStackTrace();
            throw (new ReadWriteException(ee));
        }
    }

    /**<jp>
     * データベースの棚情報を変更します。ソフトゾーン項目を条件なしで更新します。
     * @param  zone 変更内容
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException  変更対象がデータベースに見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * MOdify the location information in database. It updates the soft zone items with no condition.
     * @param  zone :contents of modification
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if target data to modify cannot be found in database.
     </en>*/
    public void modifySoftZoneId(int zone)
            throws ReadWriteException,
                NotFoundException
    {
        String sqlstring = "UPDATE " + wTableName + " SET SOFT_ZONE_ID = " + zone;
        try
        {
            executeSQL(sqlstring, false);
        }
        catch (DataExistsException ee)
        {
            //<jp> updateなので、起こらないはず</jp>
            //<en> This should not happen.</en>
            ee.printStackTrace();
            throw (new ReadWriteException(ee));
        }
    }

    /**<jp>
     * データベースの棚情報を変更します。指定棚のアドレスをパラメータのアドレスで更新します。
     * @param  stno  ステーションNo.
     * @param  address  アドレス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException  変更対象がデータベースに見つからない場合に通知されます。
     </jp>*/
    public void modifyAddress(String stno, int address)
            throws ReadWriteException,
                NotFoundException
    {
        // アドレスを除いた棚のサイズ
        int locsize =
                WmsParam.WAREHOUSE_LENGTH + WmsParam.ASRS_BANK_LENGTH + WmsParam.ASRS_BAY_LENGTH
                        + WmsParam.ASRS_LEVEL_LENGTH;
        // アドレスのサイズ
        int addresssize = WmsParam.ASRS_SUBLOC_LENGTH;

        String addrdecimal = "";
        for (int i = 0; i < addresssize; i++)
        {
            addrdecimal = addrdecimal + "0";
        }
        DecimalFormat addrfmt = new DecimalFormat(addrdecimal);
        String updAddress = addrfmt.format(address);

        String fmtSQL =
                "UPDATE " + wTableName + " SET STATION_NO = SUBSTR(STATION_NO, 1, " + locsize + ") || "
                        + DBFormat.format(updAddress) + ", ADDRESS_NO = " + address + " WHERE STATION_NO = "
                        + DBFormat.format(stno);
        String fmtSQL_StationType =
                "UPDATE " + wStationTypeTableName + " SET STATION_NO = SUBSTR(STATION_NO, 1, " + locsize + ") || "
                        + DBFormat.format(updAddress) + " WHERE STATION_NO = " + DBFormat.format(stno);
        String sqlstring;

        try
        {
            //for stationtype table.
            sqlstring = SimpleFormat.format(fmtSQL_StationType, null);
            // execute the sql
            executeSQL(sqlstring, false);

            // create actual SQL
            sqlstring = SimpleFormat.format(fmtSQL, null);
            // execute the sql
            executeSQL(sqlstring, false);
        }
        catch (DataExistsException ee)
        {
            //<jp> updateなので、起こらないはず</jp>
            //<en> This should not happen.</en>
            ee.printStackTrace();
            throw (new ReadWriteException(ee));
        }
    }

    /**<jp>
     * データベースの棚情報を変更します。ハードゾーン項目を条件なしで更新します。
     * @param  fbank  開始バンク
     * @param  fbay   開始ベイ
     * @param  flevel 開始レベル
     * @param  tbank  終了バンク
     * @param  tbay   終了ベイ
     * @param  tlevel 終了レベル
     * @param  zoneid ゾーンID
     * @param  whstno 倉庫ステーションNo.
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException  変更対象がデータベースに見つからない場合に通知されます。
     * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the location information in database. Update the hard zone items with not conditions.
     * @param  fbank  from bank
     * @param  fbay   from bay
     * @param  flevel from level
     * @param  tbank  to bank
     * @param  tbay   to bay
     * @param  tlevel to level
     * @param  zoneid ゾーンID
     * @param  whstno warehouse no
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if target data to modify cannot be found in database.
     </en>*/
    public void modifyHardZoneId(int fbank, int fbay, int flevel, int tbank, int tbay, int tlevel, int zoneid,
            String whstno)
            throws ReadWriteException,
                NotFoundException
    {
        String sqlstring =
                "UPDATE " + wTableName + " SET HARD_ZONE_ID = " + zoneid + " WHERE (BANK_NO >= " + fbank
                        + " AND BAY_NO >= " + fbay + " AND LEVEL_NO >= " + flevel + " ) " + " AND   (BANK_NO <= "
                        + tbank + " AND BAY_NO <= " + tbay + " AND LEVEL_NO <= " + tlevel + " ) "
                        + " AND WH_STATION_NO = " + DBFormat.format(whstno) + " ";

        try
        {
            executeSQL(sqlstring, false);
        }
        catch (DataExistsException ee)
        {
            //<jp> updateなので、起こらないはず</jp>
            //<en> This should not happen.</en>
            ee.printStackTrace();
            throw (new ReadWriteException(ee));
        }
    }

    /**<jp>
     * データベースの棚情報を変更します。ソフトゾーン項目を条件なしで更新します。
     * @param  fbank  開始バンク
     * @param  fbay   開始ベイ
     * @param  flevel 開始レベル
     * @param  tbank  終了バンク
     * @param  tbay   終了ベイ
     * @param  tlevel 終了レベル
     * @param  zoneid ゾーンID
     * @param  whstno 倉庫ステーションNo.
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException  変更対象がデータベースに見つからない場合に通知されます。
     * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
     </jp>*/
    /**<en>
     * Modify the location information in database. Update the soft zone items with not conditions.
     * @param  fbank  from bank
     * @param  fbay   from bay
     * @param  flevel from level
     * @param  tbank  to bank
     * @param  tbay   to bay
     * @param  tlevel to level
     * @param  zoneid ゾーンID
     * @param  whstno warehouse no
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if target data to modify cannot be found in database.
     </en>*/
    public void modifySoftZoneId(int fbank, int fbay, int flevel, int tbank, int tbay, int tlevel, int zoneid,
            String whstno)
            throws ReadWriteException,
                NotFoundException
    {
        String sqlstring =
                "UPDATE " + wTableName + " SET SOFT_ZONE_ID = " + zoneid + " WHERE (BANK_NO >= " + fbank
                        + " AND BAY_NO >= " + fbay + " AND LEVEL_NO >= " + flevel + " ) " + " AND   (BANK_NO <= "
                        + tbank + " AND BAY_NO <= " + tbay + " AND LEVEL_NO <= " + tlevel + " ) "
                        + " AND WH_STATION_NO = " + DBFormat.format(whstno) + " ";

        try
        {
            executeSQL(sqlstring, false);
        }
        catch (DataExistsException ee)
        {
            //<jp> updateなので、起こらないはず</jp>
            //<en> This should not happen.</en>
            ee.printStackTrace();
            throw (new ReadWriteException(ee));
        }
    }

    /**<jp>
     * データベースから、パラメータで渡されたキーに合致する情報を削除します。
     * @param key 削除する情報のキー
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     * @throws NotFoundException 削除すべき情報が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete from database the information that match the key passed through parameter.
     * @param key :key for the information to delete
     * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database. 
     * @throws NotFoundException  :Notifies if data to delete cannot be found.
     </en>*/
    public void drop(ToolSearchKey key)
            throws NotFoundException,
                ReadWriteException
    {
        //<jp> DBからDelete</jp>
        //<en> Delete from DB.</en>
        ToolEntity[] tgts = find(key);
        for (int i = 0; i < tgts.length; i++)
        {
            drop(tgts[i]);
        }
    }

    /**<jp>
     * データベースから、パラメータで渡された棚の情報を削除します。
     * @param tgt 削除する棚情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 削除すべき棚が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete from database the location information passed through parameter.
     * @param tgt :entity instance which preserves the location information to delete
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if location to delete cannot be found.
     </en>*/
    public void drop(ToolEntity tgt)
            throws ReadWriteException,
                NotFoundException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        String fmtSQL = "DELETE FROM " + wTableName + " WHERE STATION_NO = {0}"; // 0

        String fmtSQL_StationType = "DELETE FROM " + wStationTypeTableName + " WHERE STATION_NO = {0}"; // 0
        Station tgtShelf;
        String sqlstring;
        //-------------------------------------------------
        // process
        //-------------------------------------------------
        if (tgt instanceof Shelf)
        {
            tgtShelf = (Shelf)tgt;
        }
        else
        {
            //<jp>致命的なエラーが発生しました。{0}</jp>
            //<en>Fatal error occurred.{0}</en>
            RmiMsgLogClient.write("6126499" + wDelim + "Illegal argument. Set Shelf Instance.",
                    this.getClass().getName());
            throw (new ReadWriteException());
        }

        try
        {
            // setting Station information to Object array
            Object[] fmtObj = setToArray(tgtShelf);
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
            //<en> This should not happen;</en>

            ee.printStackTrace();
            throw (new ReadWriteException(ee));
        }
    }

    /**<jp>
     * アドレスが0以外の棚を削除します。<BR>
     * <CODE>Statement</CODE>はwStatementを使用しカーソルオープンしています。<BR>
     * 一時的にwStatementを使用しているだけで、いずれこのメソッド内でカーソルを生成するように変更します。
     * @param key 検索のためのKey
     * @return 指定された条件のSHELF
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    public void dropNotAddressZero(ToolSearchKey key)
            throws ReadWriteException
    {
        //-------------------------------------------------
        // variable define
        //-------------------------------------------------
        Object[] fmtObj = new Object[1];
        String sqlstring;

        String fmtSQL = "DELETE FROM " + wTableName + " WHERE ADDRESS_NO != 0 {0}";

        String fmtSQL_StationType =
                "DELETE FROM " + wStationTypeTableName + " WHERE TEMP_DMSTATIONTYPE.STATION_NO IN ( "
                        + " SELECT TEMP_DMSHELF.STATION_NO FROM TEMP_DMSTATIONTYPE, TEMP_DMSHELF "
                        + " WHERE TEMP_DMSTATIONTYPE.STATION_NO = TEMP_DMSHELF.STATION_NO "
                        + " AND TEMP_DMSHELF.ADDRESS_NO != 0 {0})";


        if (key.ReferenceCondition() != null)
        {
            fmtObj[0] = " AND " + key.ReferenceCondition();
        }

        try
        {
            //for stationtype table.
            sqlstring = SimpleFormat.format(fmtSQL_StationType, fmtObj);
            // execute the sql
            executeSQL(sqlstring, false);

            // create actual SQL
            sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
            // execute the sql
            executeSQL(sqlstring, false);
        }
        catch (NotFoundException e)
        {
            //<jp> 削除データが存在しなくてもエラーとしない</jp>
        }
        catch (DataExistsException ee)
        {
            //<jp> deleteなので、起こらないはず</jp>
            //<en> This should not happen;</en>
            ee.printStackTrace();
            throw (new ReadWriteException(ee));
        }

        return;
    }

    /**<jp>
     * 指定した倉庫の最小バンク、最大バンクを配列にセットして返します。<BR>
     * 使用例
     * <pre>
     * ShelfHandler shelfHandle = new ShelfHandler(wConn);
     * int[] range = shelfHandle.getBankRange(wareHouseStationNo);
     * int minBank = range[0];
     * int maxBank = range[1];
     * </pre>
     * @param  whStno             倉庫ステーションNo.
     * @param  parentStationNo    親ステーションナンバー（アイルステーションNo）
     * @return range              最小バンク、最大バンクをint型配列で返す。
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Set the min. bank and the max. bank in the array, then return.<BR>
     * example:
     * <pre>
     * ShelfHandler shelfHandle = new ShelfHandler(wConn);
     * int[] range = shelfHandle.getBankRange(wareHouseStationNo);
     * int minBank = range[0];
     * int maxBank = range[1];
     * </pre>
     * @param  whStno             :warehouse station no.
     * @param  parentStationNo    :parent station no (aisle station no.)
     * @return range              :return the min. bank and the max. bank of specified warehouse 
     * in int type array.
     * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database. 
     </en>*/
    public int[] getBankRange(String whStno, String parentStationNo)
            throws ReadWriteException
    {
        int[] range = new int[2];
        Statement stmt = null;
        ResultSet rset = null;

        try
        {
            stmt = wConn.createStatement();
            String sqlString =
                    "SELECT MIN(BANK_NO) MINBANK,MAX(BANK_NO) MAXBANK FROM " + wTableName + " WHERE WH_STATION_NO = '"
                            + whStno + "'" + "and PARENT_STATION_NO = '" + parentStationNo + "'";
            rset = stmt.executeQuery(sqlString);
            while (rset.next())
            {
                range[0] = rset.getInt("MINBANK");
                range[1] = rset.getInt("MAXBANK");
            }
            return range;
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
                if (rset != null)
                {
                    rset.close();
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
    }

    /**<jp>
     * 指定した倉庫の最小ベイ、最大ベイを配列にセットして返します。<BR>
     * 使用例
     * <pre>
     * ShelfHandler shelfHandle = new ShelfHandler(wConn);
     * int[] range = shelfHandle.getBayRange(wareHouseStationNo);
     * int minBay = range[0];
     * int maxBay = range[1];
     * </pre>
     * @param  whStno             倉庫ステーションNo.
     * @param  parentStationNo    親ステーションナンバー（アイルステーションNo）
     * @return range              最小バンク、最大バンクをint型配列で返す。
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Set the min. bay and teh max. bay of hte specified warehouse in the array, then return. <BR>
     * example
     * <pre>
     * ShelfHandler shelfHandle = new ShelfHandler(wConn);
     * int[] range = shelfHandle.getBayRange(wareHouseStationNo);
     * int minBay = range[0];
     * int maxBay = range[1];
     * </pre>
     * @param  whStno             :warehouse station no.
     * @param  parentStationNo    :parent station no (aisle station no.)
     * @return range              :return the min. bank and the max. bank of specified warehouse 
     * in int type array.
     * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database. 
     </en>*/
    public int[] getBayRange(String whStno, String parentStationNo)
            throws ReadWriteException
    {
        int[] range = new int[2];
        Statement stmt = null;
        ResultSet rset = null;

        try
        {
            stmt = wConn.createStatement();
            String sqlString =
                    "SELECT MIN(BAY_NO) MINBAY,MAX(BAY_NO) MAXBAY FROM " + wTableName + " WHERE WH_STATION_NO = '"
                            + whStno + "'" + "and PARENT_STATION_NO = '" + parentStationNo + "'";

            rset = stmt.executeQuery(sqlString);
            System.out.println("SQL <" + sqlString + ">");
            while (rset.next())
            {
                range[0] = rset.getInt("MINBAY");
                range[1] = rset.getInt("MAXBAY");
                System.out.println("range[0] <" + range[0] + ">    range[1] <" + range[1] + ">");
            }
            return range;
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
                if (rset != null)
                {
                    rset.close();
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
    }

    /**<jp>
     * 指定した倉庫の最小レベル、最大レベルを配列にセットして返します。<BR>
     * 使用例
     * <pre>
     * ShelfHandler shelfHandle = new ShelfHandler(wConn);
     * int[] range = shelfHandle.getLevelRange(wareHouseStationNo);
     * int minLevel = range[0];
     * int maxLevel = range[1];
     * </pre>
     * @param  whStno             倉庫ステーションNo.
     * @param  parentStationNo    親ステーションナンバー（アイルステーションNo）
     * @return range              最小バンク、最大バンクをint型配列で返す。
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Set the min. level and the max. level of the specified warehouse in the array, then return.<BR>
     * example
     * <pre>
     * ShelfHandler shelfHandle = new ShelfHandler(wConn);
     * int[] range = shelfHandle.getLevelRange(wareHouseStationNo);
     * int minLevel = range[0];
     * int maxLevel = range[1];
     * </pre>
     * @param  whStno             :warehouse station no.
     * @param  parentStationNo    :parent station no (aisle station no.)
     * @return range              :return the min. bank and the max. bank of specified warehouse 
     * in int type array.
     * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database. 
     </en>*/
    public int[] getLevelRange(String whStno, String parentStationNo)
            throws ReadWriteException
    {
        int[] range = new int[2];
        Statement stmt = null;
        ResultSet rset = null;

        try
        {
            stmt = wConn.createStatement();
            String sqlString =
                    "SELECT MIN(LEVEL_NO) MINLEVEL,MAX(LEVEL_NO) MAXLEVEL FROM " + wTableName
                            + " WHERE WH_STATION_NO = '" + whStno + "'" + "and PARENT_STATION_NO = '" + parentStationNo
                            + "'";
            rset = stmt.executeQuery(sqlString);
            while (rset.next())
            {
                range[0] = rset.getInt("MINLEVEL");
                range[1] = rset.getInt("MAXLEVEL");
            }
            return range;
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
                if (rset != null)
                {
                    rset.close();
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
    }

    /**
     * 指定した倉庫の開始アイル位置、終了アイル位置を配列にセットして返します。<BR>
     * 使用例
     * <pre>
     * ShelfHandler shelfHandle = new ShelfHandler(wConn);
     * int[] range = shelfHandle.getLevelRange(wareHouseStationNo);
     * int minAislePosition = range[0];
     * int manAislePosition = range[1];
     * </pre>
     * @param  whStno             :warehouse station no.
     * @param  parentStationNo    :parent station no (aisle station no.)
     * @return range              :return the min. bank and the max. bank of specified warehouse 
     * in int type array.
     * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database. 
     */
    public int[] getAislePostion(String whStno, String parentStationNo)
            throws ReadWriteException
    {
        int[] range = new int[2];
        Statement stmt = null;
        ResultSet rset = null;

        try
        {
            stmt = wConn.createStatement();
            String sqlString =
                    "SELECT MIN(BANK_NO) MINLEVEL,MAX(BANK_NO) MAXLEVEL FROM " + wTableName
                            + " WHERE WH_STATION_NO = '" + whStno + "'" + " and PARENT_STATION_NO = '"
                            + parentStationNo + "'" + " and SIDE = '" + Bank.NEAR + "'";
            rset = stmt.executeQuery(sqlString);
            while (rset.next())
            {
                range[0] = rset.getInt("MINLEVEL");
                range[1] = rset.getInt("MAXLEVEL");
            }
            return range;
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
                if (rset != null)
                {
                    rset.close();
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
    }

    /**<jp>
     * 指定した倉庫,Bankの最小ベイ、最大ベイを配列にセットして返します。<BR>
     * 使用例
     * <pre>
     * ShelfHandler shelfHandle = new ShelfHandler(wConn);
     * int[] range = shelfHandle.getBayRange(wareHouseStationNo);
     * int minBay = range[0];
     * int maxBay = range[1];
     * </pre>
     * @param  whStno             倉庫ステーションNo.
     * @param  bank               バンクNo.
     * @return range              最小バンク、最大バンクをint型配列で返す。
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Set the min. bay and the max. bay of the specified warehouse and bank to the array then return.<BR>
     * example
     * <pre>
     * ShelfHandler shelfHandle = new ShelfHandler(wConn);
     * int[] range = shelfHandle.getBayRange(wareHouseStationNo);
     * int minBay = range[0];
     * int maxBay = range[1];
     * </pre>
     * @param  whStno             :warehouse station no.
     * @param  bank               :bank no
     * @return range              :return the min. bank and the max. bank of specified warehouse 
     * in int type array.
     * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database. 
     </en>*/
    public int[] getBayRange(String whStno, int bank)
            throws ReadWriteException
    {
        int[] range = new int[2];
        Statement stmt = null;
        ResultSet rset = null;

        try
        {
            stmt = wConn.createStatement();
            String sqlString =
                    "SELECT MIN(BAY_NO) MINBAY,MAX(BAY_NO) MAXBAY FROM " + wTableName + " WHERE WH_STATION_NO = '"
                            + whStno + "'" + "and BANK_NO = '" + String.valueOf(bank) + "'";

            rset = stmt.executeQuery(sqlString);
            while (rset.next())
            {
                range[0] = rset.getInt("MINBAY");
                range[1] = rset.getInt("MAXBAY");
            }
            return range;
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
                if (rset != null)
                {
                    rset.close();
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
    }

    /**<jp>
     * 指定した倉庫の最小レベル、最大レベルを配列にセットして返します。<BR>
     * 使用例
     * <pre>
     * ShelfHandler shelfHandle = new ShelfHandler(wConn);
     * int[] range = shelfHandle.getLevelRange(wareHouseStationNo);
     * int minLevel = range[0];
     * int maxLevel = range[1];
     * </pre>
     * @param  whStno             倉庫ステーションNo.
     * @param  bank               バンクNo.
     * @return range              最小バンク、最大バンクをint型配列で返す。
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Set the min.level and max. level of the specified warehouse to the array, then return.<BR>
     * example
     * <pre>
     * ShelfHandler shelfHandle = new ShelfHandler(wConn);
     * int[] range = shelfHandle.getLevelRange(wareHouseStationNo);
     * int minLevel = range[0];
     * int maxLevel = range[1];
     * </pre>
     * @param  whStno             :warehouse station no.
     * @param  bank               :bank no
     * @return range              :return the min. bank and the max. bank of specified warehouse 
     * in int type array.
     * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database. 
     </en>*/
    public int[] getLevelRange(String whStno, int bank)
            throws ReadWriteException
    {
        int[] range = new int[2];
        Statement stmt = null;
        ResultSet rset = null;

        try
        {
            stmt = wConn.createStatement();
            String sqlString =
                    "SELECT MIN(LEVEL_NO) MINLEVEL,MAX(LEVEL_NO) MAXLEVEL FROM " + wTableName
                            + " WHERE WH_STATION_NO = '" + whStno + "'" + "and BANK_NO = '" + String.valueOf(bank)
                            + "'";
            rset = stmt.executeQuery(sqlString);
            while (rset.next())
            {
                range[0] = rset.getInt("MINLEVEL");
                range[1] = rset.getInt("MAXLEVEL");
            }
            return range;
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
                if (rset != null)
                {
                    rset.close();
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
    }

    /**<jp>
     * 指定した倉庫の最大バンク、ベイ、レベルを配列にセットして返します。<BR>
     * 使用例
     * <pre>
     * ShelfHandler shelfHandle = new ShelfHandler(wConn);
     * int[] range = shelfHandle.getShelfRange(wareHouseStationNo);
     * int maxBank = range[0];
     * int maxBay = range[1];
     * int maxLevel = range[2];
     * </pre>
     * @param  whStno             倉庫ステーションNo.
     * @return range              最小バンク、最大バンクをint型配列で返す。
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Set the max. bank, bay and level of specified warehouse to the array then return.<BR>
     * example
     * <pre>
     * ShelfHandler shelfHandle = new ShelfHandler(wConn);
     * int[] range = shelfHandle.getShelfRange(wareHouseStationNo);
     * int maxBank = range[0];
     * int maxBay = range[1];
     * int maxLevel = range[2];
     * </pre>
     * @param  whStno             :warehouse station no.
     * @return range              :return the min. bank and the max. bank of specified warehouse 
     * in int type array.
     * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database. 
     </en>*/
    public int[] getShelfRange(String whStno)
            throws ReadWriteException
    {
        int[] range = new int[3];
        Statement stmt = null;
        ResultSet rset = null;

        try
        {
            stmt = wConn.createStatement();
            rset =
                    stmt.executeQuery("SELECT MAX(BANK_NO) MAXBANK,MAX(BAY_NO) MAXBAY, MAX(LEVEL_NO) MAXLEVEL FROM "
                            + wTableName + " WHERE WH_STATION_NO = '" + whStno + "'");
            while (rset.next())
            {
                range[0] = rset.getInt("MAXBANK");
                range[1] = rset.getInt("MAXBAY");
                range[2] = rset.getInt("MAXLEVEL");
            }
            return range;
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
                if (rset != null)
                {
                    rset.close();
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
    }

    /**<jp>
     * 指定した倉庫の最大バンク、ベイ、レベルを配列にセットして返します。<BR>
     * 使用例
     * <pre>
     * ShelfHandler shelfHandle = new ShelfHandler(wConn);
     * int[] range = shelfHandle.getShelfRange(wareHouseStationNo);
     * int maxBank = range[0];
     * int maxBay = range[1];
     * int maxLevel = range[2];
     * </pre>
     * @param  whStno             倉庫ステーションNo.
     * @param  fbank              開始バンクNo.
     * @param  tbank              終了バンクNo.
     * @return range              最小バンク、最大バンクをint型配列で返す。
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Set the max. bank, bay and level of specified warehouse to the array then return.<BR>
     * example
     * <pre>
     * ShelfHandler shelfHandle = new ShelfHandler(wConn);
     * int[] range = shelfHandle.getShelfRange(wareHouseStationNo);
     * int maxBank = range[0];
     * int maxBay = range[1];
     * int maxLevel = range[2];
     * </pre>
     * @param  whStno             :warehouse station no.
     * @param  fbank              :from bank no
     * @param  tbank              :to bank no
     * @return range              :return the min. bank and the max. bank of specified warehouse 
     * in int type array.
     * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database. 
     </en>*/
    public int[] getShelfRange(String whStno, int fbank, int tbank)
            throws ReadWriteException
    {
        int[] range = new int[3];
        Statement stmt = null;
        ResultSet rset = null;
        String sqlstring = "";

        try
        {
            sqlstring =
                    "SELECT MAX(BANK_NO) MAXBANK,MAX(BAY_NO) MAXBAY, MAX(LEVEL_NO) MAXLEVEL FROM " + wTableName
                            + " WHERE WH_STATION_NO = '" + whStno + "'" + " AND BANK_NO >= " + fbank
                            + " AND BANK_NO <= " + tbank + " ";

            stmt = wConn.createStatement();
            rset = stmt.executeQuery(sqlstring);
            while (rset.next())
            {
                range[0] = rset.getInt("MAXBANK");
                range[1] = rset.getInt("MAXBAY");
                range[2] = rset.getInt("MAXLEVEL");
            }
            return range;
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
                if (rset != null)
                {
                    rset.close();
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
    }

    /**<jp>
     * 指定した倉庫の最最小バンク、ベイ、レベルを配列にセットして返します。<BR>
     * 使用例
     * <pre>
     * ShelfHandler shelfHandle = new ShelfHandler(wConn);
     * int[] range = shelfHandle.getMinShelfRange(wareHouseStationNo);
     * int minBank = range[0];
     * int minBay = range[1];
     * int minLevel = range[2];
     * </pre>
     * @param  whStno             倉庫ステーションNo.
     * @return range              最小バンク、ベイ、レベルをint型配列で返す。
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     </jp>*/
    public int[] getMinShelfRange(String whStno)
            throws ReadWriteException
    {
        int[] range = new int[3];
        Statement stmt = null;
        ResultSet rset = null;
        String sqlstring = "";

        try
        {
            sqlstring = "SELECT MIN(BANK_NO) MINBANK,MIN(BAY_NO) MINBAY, MIN(LEVEL_NO) MINLEVEL FROM "
					+ wTableName + " WHERE WH_STATION_NO = '" + whStno + "'";

            stmt = wConn.createStatement();
            rset = stmt.executeQuery(sqlstring);
            while (rset.next())
            {
                range[0] = rset.getInt("MINBANK");
                range[1] = rset.getInt("MINBAY");
                range[2] = rset.getInt("MINLEVEL");
            }
            return range;
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
                if (rset != null)
                {
                    rset.close();
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
    }

    /**<jp>
     * 指定した倉庫の件数、最大アドレス、最小アドレスを配列にセットして返します。<BR>
     * 使用例
     * <pre>
     * ShelfHandler shelfHandle = new ShelfHandler(wConn);
     * int[] range = shelfHandle.getShelfWidthRange(wareHouseStationNo);
     * int count = range[0];
     * int maxAddress = range[1];
     * int minAddress = range[2];
     * </pre>
     * @param  fbank  開始バンク
     * @param  fbay   開始ベイ
     * @param  flevel 開始レベル
     * @param  tbank  終了バンク
     * @param  tbay   終了ベイ
     * @param  tlevel 終了レベル
     * @param  whstno 倉庫ステーションNo.
     * @return range  件数、最大アドレス、最小アドレスをint型配列で返す。
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Set the count, max. address, min. address of specified warehouse to the array then return.<BR>
     * example
     * <pre>
     * ShelfHandler shelfHandle = new ShelfHandler(wConn);
     * int[] range = shelfHandle.getShelfWidthRange(wareHouseStationNo);
     * int count = range[0];
     * int maxAddress = range[1];
     * int minAddress = range[2];
     * </pre>
     * @param  fbank  from bank
     * @param  fbay   from bay
     * @param  flevel from level
     * @param  tbank  to bank
     * @param  tbay   to bay
     * @param  tlevel to level
     * @param  whstno warehouse no
     * @return range              :return the count, max. address, min. address of specified warehouse 
     * in int type array.
     * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database. 
     </en>*/
    public int[] getShelfWidthRange(int fbank, int fbay, int flevel, int tbank, int tbay, int tlevel, String whstno)
            throws ReadWriteException
    {
        int[] range = new int[3];
        Statement stmt = null;
        ResultSet rset = null;
        String sqlstring = "";

        try
        {
            sqlstring =
                    "SELECT COUNT(ADDRESS_NO) CNTADDR, MAX(ADDRESS_NO) MAXADDR, MIN(ADDRESS_NO) MINADDR FROM "
                            + wTableName + " WHERE (BANK_NO >= " + fbank + " AND BAY_NO >= " + fbay
                            + " AND LEVEL_NO >= " + flevel + " ) " + " AND   (BANK_NO <= " + tbank + " AND BAY_NO <= "
                            + tbay + " AND LEVEL_NO <= " + tlevel + " ) " + " AND WH_STATION_NO = "
                            + DBFormat.format(whstno);

            stmt = wConn.createStatement();
            rset = stmt.executeQuery(sqlstring);
            while (rset.next())
            {
                range[0] = rset.getInt("CNTADDR");
                range[1] = rset.getInt("MAXADDR");
                range[2] = rset.getInt("MINADDR");
            }
            return range;
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
                if (rset != null)
                {
                    rset.close();
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
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * <code>Shelf</code>インスタンスから情報を取り出して、文字列(<code>String</code>)
     * としてObject配列にセットします。
     * INSERT,UPDATE の為に用意されています。
     * データベースへの保存時、nullがふさわしいものは、文字列 null がセットされます。
     * 文字列タイプ(VARCHAR)の項目は、'(シングル・クオート)を前後に追加します。
     * @param tgtShelf 情報を取得する<code>Shelf</code>インスタンス
     * @return Object配列。
     * <p>
     * 配列順は以下のようになります。<br>
     * <pre>
     * stationnumber    // 0
     * bank                // 1
     * bay                // 2
     * level            // 3
     * warehouse        // 4
     * status            // 5
     * presence            // 6
     * hardzone            // 7
     * softzone            // 8
     * parentstationid    // 9
     * accessngflag        // 10
     * </pre></p>
     </jp>*/
    /**<en>
     * Retrieve the information from the <code>Shelf</code> instane and set to the Object array
     * as a string (<code>String</code>) .
     * This is prepared for INSERT and UPDATE.
     * When storing in database and if appropiate, it will set string null in some cases.
     * For string type items(VARCHAR), enclose them with '(single quotations).
     * @param tgtShelf :<code>Shelf</code> instance to retrieve data
     * @return :Object array
     * <p>
     * The order of arrays should be as follows.<br>
     * <pre>
     * stationnumber    // 0
     * bank                // 1
     * bay                // 2
     * level            // 3
     * warehouse        // 4
     * status            // 5
     * presence            // 6
     * hardzone            // 7
     * softzone            // 8
     * parentstationid    // 9
     * accessngflag        // 10
     * </pre></p>
     </en>*/
    protected Object[] setToArray(Shelf tgtShelf)
    {
        Object[] fmtObj = new Object[17];
        // set parameters
        // station number
        fmtObj[0] = DBFormat.format(tgtShelf.getStationNo());
        // bank
        fmtObj[1] = String.valueOf(tgtShelf.getBankNo());
        // bay
        fmtObj[2] = String.valueOf(tgtShelf.getBayNo());
        // level
        fmtObj[3] = String.valueOf(tgtShelf.getLevelNo());
        // address
        fmtObj[4] = String.valueOf(tgtShelf.getAddressNo());
        // parentstationid
        fmtObj[5] = DBFormat.format(tgtShelf.getWarehouseStationNo());
        // status
        fmtObj[6] = String.valueOf(tgtShelf.getStatusFlag());
        // presence
        fmtObj[7] = String.valueOf(tgtShelf.getProhibitionFlag());
        // hardzone
        fmtObj[8] = String.valueOf(tgtShelf.getHardZoneId());
        // softzone
        fmtObj[9] = String.valueOf(tgtShelf.getSoftZoneId());
        // parentstationid
        fmtObj[10] = DBFormat.format(tgtShelf.getParentStationNo());
        // accsess Status
        if (tgtShelf.isAccessNgFlag())
        {
            fmtObj[11] = String.valueOf(Shelf.ACCESS_NG);
        }
        else
        {
            fmtObj[11] = String.valueOf(Shelf.ACCESS_OK);
        }
        // priority
        fmtObj[12] = String.valueOf(tgtShelf.getPriority());
        // pairstationnumber
        fmtObj[13] = DBFormat.format(tgtShelf.getPairStationNumber());
        // side
        fmtObj[14] = String.valueOf(tgtShelf.getSide());
        // width
        fmtObj[15] = String.valueOf(tgtShelf.getWidth());
        // location use flag
        fmtObj[16] = String.valueOf(tgtShelf.getLocationUseFlag());

        return (fmtObj);
    }

    /**<jp>
     * <code>ResultSet</code>から、各項目を取り出して、<code>Shelf</code>インスタンスを生成します。
     * @param rset SHELFテーブル検索の結果セット
     * @return 生成されたShelfインスタンスの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve each item from <code>ResultSet</code> and generate the <code>Shelf</code> instance.
     * @param rset :result set of SHELF table search
     * @return :the array of the generated Shelf instance
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    protected Shelf[] makeShelf(ResultSet rset)
            throws ReadWriteException
    {
        return makeShelf(rset, 0);
    }

    /**<jp>
     * <code>ResultSet</code>から<code>Shelf</code>インスタンスの配列を生成します。
     * @param rset SHELFテーブル検索の結果セット
     * @param maxcreate Shelfインスタンスを生成する個数。0の場合結果セットすべてのインスタンスを生成します。
     * @return 生成されたShelfインスタンスの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Generate the array of <code>Shelf</code> instance based on the <code>ResultSet</code>.
     * @param rset :result set of teh SHELF table search
     * @param maxcreate :number of Shelf instance to generate; if it is 0, it should generate 
     * instances of all result set.
     * @return :the array of the generated Shelf instance
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    protected Shelf[] makeShelf(ResultSet rset, int maxcreate)
            throws ReadWriteException
    {
        Vector tmpShelfVect = new Vector(); // temporary store for Station instances

        // data get from resultset and make new Station instance
        try
        {
            int count = 0;
            while (rset.next())
            {
                if ((maxcreate != 0) && (count > maxcreate))
                {
                    //<jp> インスタンス生成上限数を超えた場合、ループを抜ける。</jp>
                    //<en> Exits loop when exceeded the upper limit of instance generation.</en>
                    break;
                }
                //<jp> Shelfインスタンスを生成する。</jp>
                //<en> Generate the Shelf instance.</en>
                Shelf tmpShelf = new Shelf(DBFormat.replace(rset.getString("STATION_NO")));
                setShelf(rset, tmpShelf);
                tmpShelfVect.add(tmpShelf);

                // count up
                count++;
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
            RmiMsgLogClient.write(new TraceHandler(6126001, e), "ToolShelfHandler");
            throw (new ReadWriteException(e));
        }

        // move instance from vector to array of Station
        Shelf[] rstarr = new Shelf[tmpShelfVect.size()];
        tmpShelfVect.copyInto(rstarr);

        return (rstarr);
    }

    /**<jp>
     * <code>ResultSet</code>から、各項目を取り出して、<code>Shelf</code>インスタンスの各項目をセットします。
     * @param rset SHELFテーブル検索の結果セット
     * @param tmpShelf セット対象となるShelfインスタンス
     * @return 生成されたShelfインスタンス
     * @throws SQLException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidStatusException 設定内容が範囲外であった場合に通知します。
     </jp>*/
    /**<en>
     * Retrieve each item from <code>ResultSet</code> and set each item of <code>Shelf</code> instance.
     * @param rset :result set of the SHELF table search
     * @param tmpShelf :Shelf instance to set
     * @return :Shelf instance to generate
     * @throws SQLException :Notifies if error occured in connection with database.
     * @throws InvalidStatusException :Notifies if the set data is invalid. 
     </en>*/
    protected Shelf setShelf(ResultSet rset, Shelf tmpShelf)
            throws SQLException,
                InvalidStatusException
    {
        // bank
        tmpShelf.setBankNo(rset.getInt("BANK_NO"));
        // bay
        tmpShelf.setBayNo(rset.getInt("BAY_NO"));
        // level
        tmpShelf.setLevelNo(rset.getInt("LEVEL_NO"));
        // address
        tmpShelf.setAddressNo(rset.getInt("ADDRESS_NO"));
        // warehouse
        tmpShelf.setWhStationNo(DBFormat.replace(rset.getString("WH_STATION_NO")));
        // station status
        tmpShelf.setStatusFlag(rset.getInt("STATUS_FLAG"));
        // presence
        tmpShelf.setProhibitionFlag(rset.getInt("PROHIBITION_FLAG"));
        // hardzone
        tmpShelf.setHardZoneId(rset.getInt("HARD_ZONE_ID"));
        // softzone
        tmpShelf.setSoftZoneId(rset.getInt("SOFT_ZONE_ID"));
        // parent station
        tmpShelf.setParentStationNo(DBFormat.replace(rset.getString("PARENT_STATION_NO")));
        //<jp> aile station 将来的にはParentStationNoは廃止し、AISLESTATIONNOに置き換える。</jp>
        //<en> aile station :in future the use of ParentStationNo should be discontinued and </en>
        //<en>               be replaced by AISLESTATIONNO.</en>
        tmpShelf.setAisleStationNo(DBFormat.replace(rset.getString("PARENT_STATION_NO")));
        // accsess ng flag
        boolean acs = (rset.getInt("ACCESS_NG_FLAG") == Shelf.ACCESS_NG);
        tmpShelf.setAccessNgFlag(acs);
        // side
        tmpShelf.setSide(rset.getInt("SIDE"));
        // width
        tmpShelf.setWidth(rset.getInt("WIDTH"));
        // location use flag
        tmpShelf.setLocationUseFlag(rset.getInt("LOCATION_USE_FLAG"));
        // priority
        tmpShelf.setPriority(rset.getInt("PRIORITY"));
        // pairstationnumber
        tmpShelf.setPairStationNumber(DBFormat.replace(rset.getString("PAIR_STATION_NO")));
        // station handler
        tmpShelf.setHandler(this);

        return tmpShelf;
    }

    // Private methods -----------------------------------------------
}
//end of class

