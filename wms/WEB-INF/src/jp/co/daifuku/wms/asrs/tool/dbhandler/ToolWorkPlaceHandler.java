//$Id: ToolWorkPlaceHandler.java 5355 2009-11-02 00:44:35Z ota $

package jp.co.daifuku.wms.asrs.tool.dbhandler;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.wms.asrs.tool.location.Station;

/**<jp>
 * <code>WorkPlace</code>クラスをデータベースから取得したり、データベースに保管する為に利用するクラスです。
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
 * This class is used to retrieve/store the <code>WorkPlace</code> class from/to database.
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
public class ToolWorkPlaceHandler
        extends ToolStationHandler
{

    // Class fields --------------------------------------------------

    private static final String WORKPLACE_HANDLE = "jp.co.daifuku.wms.asrs.dbhandler.ASWorkPlaceHandler";

    // Class variables -----------------------------------------------
    /**<jp> テーブル名 </jp>*/
    /**<en> name of the table </en>*/
    private String wTableName = "TEMP_DMSTATION";


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
    public ToolWorkPlaceHandler(Connection conn)
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
    public ToolWorkPlaceHandler(Connection conn, String tablename)
    {
        super(conn);
        wTableName = tablename;
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * データベースに新規情報を作成します。
     * @param tgt 作成する情報を持ったエンティティ・インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws DataExistsException 既に、同じ情報がデータベースに登録済みの場合に通知されます。
     </jp>*/
    /**<en>
     * Create new information in database.
     * @param tgt :entity instance which has the information to create
     * @throws ReadWriteException  :Notifies if error occured in connection with database.
     * @throws DataExistsException :Notifies if the same warehouse has been registered in database.
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
                + ") VALUES (" + "{0}, '" + WORKPLACE_HANDLE + "'" + ")";


        // for database access


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

            //for stationtype table.
            sqlstring = SimpleFormat.format(fmtSQL_StationType, fmtObj);
            // execute the sql
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
     * @throws NotFoundException  :Notifies if data of warehouse to modify cannot be found.
     </en>*/
    public void modify(ToolEntity tgt)
            throws ReadWriteException,
                NotFoundException
    {
        //<jp> DBをアップデート</jp>
        //<jp>-------------------------------------------------</jp>
        //<jp> variable define</jp>
        //<jp>-------------------------------------------------</jp>
        //<en> UPdate DB.</en>
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
                + ", PARENT_STATION_NO = {19} " // 29
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

        // for database access

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
     * @throws NotFoundException  :Notifies when data to modify cannot be found in database.
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
            //<en>6126005 = Cannot update database; the update value has not been set. TABLE={0}</en>
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
            //<en>6126006 = Cannot update database; the update conditions have not been set.  TABLE={0}</en>
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
     * Delete from database the warehouse information which has been passed through parameter.
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
        //<en> Delet from DB</en>
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
     * Delete from database the information that match the key which has been passed through parameter.
     * @param key :key of the data to delete
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException  :Notifies if data to delete cannot be found.
     </en>*/
    public void drop(ToolSearchKey key)
            throws ReadWriteException,
                NotFoundException
    {
        //<jp> DBからDelete</jp>
        //<en> Delete from DB.</en>
        ToolEntity[] tgts = find(key);
        for (int i = 0; i < tgts.length; i++)
        {
            drop(tgts[i]);
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

