// $Id: ToolCommonChecker.java 7721 2010-03-24 08:19:38Z shibamoto $
package jp.co.daifuku.wms.asrs.tool.schedule;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.IniFileOperator;
import jp.co.daifuku.wms.asrs.tool.common.ToolMenuText;
import jp.co.daifuku.wms.asrs.tool.common.ToolMenuTextHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAreaHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAreaSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolLoadSizeHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolLoadSizeSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolSoftZoneHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolSoftZonePriorityHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolSoftZonePrioritySearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolSoftZoneSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWidthHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWidthSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWmsAreaHandler;
import jp.co.daifuku.wms.asrs.tool.location.HardZone;
import jp.co.daifuku.wms.asrs.tool.location.Shelf;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**<jp>
 * メンテナンス処理の共通チェックを実装するクラスです。
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/16</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/17</TD><TD>okamura</TD><TD>change setMessage of isExistMenuId method</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7721 $, $Date: 2010-03-24 17:19:38 +0900 (水, 24 3 2010) $
 * @author  $Author: shibamoto $
 </jp>*/
/**<en>
 * This class implements the common checks in maintenance processing.
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/16</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/17</TD><TD>okamura</TD><TD>change setMessage of isExistMenuId method</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7721 $, $Date: 2010-03-24 17:19:38 +0900 (水, 24 3 2010) $
 * @author  $Author: shibamoto $
 </en>*/
public class ToolCommonChecker
        extends Object
{
    // Class fields --------------------------------------------------
    /**<jp>
     * デリミタ
     * Exception発生時、MessageDefのメッセージのパラメータの区切り文字です。
     </jp>*/
    /**<en>
     * Delimiter
     * This is the delimiter of the parameter for MessageDef when Exception occured.
     </en>*/
    public String wDelim = MessageResource.DELIM;

    // Class variables -----------------------------------------------

    /**<jp>
     * チェック処理実行時に発生した問題の詳細メッセージを格納する。
     </jp>*/
    /**<en>
     * Store the detail message for the problems that occurred during the check process.
     </en>*/
    protected String wMessage = "";

    /**<jp>
     * データベース接続用のコネクション
     </jp>*/
    /**<en>
     * Connection with database.
     </en>*/
    protected Connection wConn;


    private ToolGroupControllerHandler wGCHandler = null;

    private ToolWarehouseHandler wWarehouseHandler = null;

    private ToolAisleHandler wAisleHandler = null;

    private ToolStationHandler wStationHandler = null;

    private ToolShelfHandler wShelfHandler = null;

    private ToolWmsAreaHandler wWmsAreaHandler = null;

    private ToolAreaHandler wAreaHandler = null;

    private ToolLoadSizeHandler wLoadSizeHandler = null;

    private ToolWidthHandler wWidthHandler = null;

    private ToolSoftZoneHandler wSoftZoneHandler = null;

    private ToolSoftZonePriorityHandler wSoftZonePriorityHandler = null;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Return the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 7721 $,$Date: 2010-03-24 17:19:38 +0900 (水, 24 3 2010) $");
    }

    // Constructors --------------------------------------------------
    /**
     * コンストラクタ
     * @param conn データベース接続用 Connection
     */
    public ToolCommonChecker(Connection conn)
    {
        wConn = conn;
        wGCHandler = new ToolGroupControllerHandler(conn);
        wWarehouseHandler = new ToolWarehouseHandler(conn);
        wAisleHandler = new ToolAisleHandler(conn);
        wStationHandler = new ToolStationHandler(conn);
        wShelfHandler = new ToolShelfHandler(conn);
        wWmsAreaHandler = new ToolWmsAreaHandler(conn);
        wAreaHandler = new ToolAreaHandler(conn);
        wLoadSizeHandler = new ToolLoadSizeHandler(conn);
        wWidthHandler = new ToolWidthHandler(conn);
        wSoftZoneHandler = new ToolSoftZoneHandler(conn);
        wSoftZonePriorityHandler = new ToolSoftZonePriorityHandler(conn);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * チェックが保持するメッセージを返します。
     * チェックした内容がＮＧの場合に、その詳細を取得するために使用します。
     * @return メッセージの内容
     </jp>*/
    /**<en>
     * Return the message that check preserves.
     * This will be used to retrieve the detail in case the checked contents was unacceptable.
     * @return  :contents of the message
     </en>*/
    public String getMessage()
    {
        return wMessage;
    }


    /**<jp>
     * 引数で指定したステーションNoがルートテキストに存在するか
     * 確認します。
     * @param filepath テキストファイルのパス
     * @param stationNo ステーションNo
     * @return 存在する場合はtrueを返します。
     </jp>*/
    /**<en>
     * Check whether/not the station no., specified through parameter, exist in route text.
     * @param filepath file Path
     * @param stationNo Station Number
     * @return :return true if it exists.
     </en>*/
    public boolean isExistStationNo_RouteText(String filepath, String stationNo)
    {
        String defaultRouteText = ToolParam.getParam("DEFAULT_ROUTETEXT_PATH");
        File routepath = new File(defaultRouteText);

        IniFileOperator ifo = null;
        try
        {
            ifo = new IniFileOperator(filepath + "/" + routepath.getName(), RouteCreater.wSeparate);
        }
        catch (ReadWriteException e)
        {
            return false;
        }
        String[] fromStations = ifo.getKeys();
        String[] toStations = ifo.getValues();

        for (int i = 0; i < fromStations.length; i++)
        {
            if (fromStations[i].length() == stationNo.length() && fromStations[i].indexOf(stationNo) != -1)
            {
                return true;
            }
        }

        for (int i = 0; i < toStations.length; i++)
        {
            if (toStations[i].length() == stationNo.length() && toStations[i].indexOf(stationNo) != -1)
            {
                return true;
            }
        }
        return false;
    }

    /**<jp>
     * 引数で指定したグループコントローラNoがグループコントローラ表に存在するか
     * 確認します。
     * @param グループコントローラNo
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check whether/not the group controller no. specified through parameter exist in
     * the group controller table.
     * @param arg group controller No
     * @return :return true if it exists.
     * @throws ScheduleException 
     </en>*/
    public boolean isExistControllerNo(int arg)
            throws ScheduleException
    {
        try
        {
            ToolGroupControllerSearchKey key = new ToolGroupControllerSearchKey();
            key.setControllerNo(arg);
            if (getGCHandler().count(key) == 0)
            {
                //<jp>6123121=GROUPCONTROLLER表にCONTROLLERNO={0}は存在しません</jp>
                //<en>6123121=The CONTROLLERNO={0} does not exist in GROUPCONTROLLER table.</en>
                setMessage("6123121" + wDelim + Integer.toString(arg));
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        return true;
    }

    /**<jp>
     * 引数で指定した倉庫ステーションNoがWAREHOUSE表に存在するか
     * 確認します。
     * @param 倉庫ステーションNo
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check whether/not the warehouse station no., specified through parameter, exist
     * in WAREHOUSE table.
     * @param arg WareHouse Station No
     * @return :return true if data exists.
     * @throws ScheduleException 
     </en>*/
    public boolean isExistWarehouseStationNo(String arg)
            throws ScheduleException
    {
        try
        {
            ToolWarehouseSearchKey key = new ToolWarehouseSearchKey();
            key.setWarehouseStationNo(arg);
            if (getWarehouseHandler().count(key) == 0)
            {
                //<jp>6123138    WAREHOUSE表にSTATIONNO={0}は存在しません</jp>
                //<en>6123138    There is no STATIONNO={0} in WAREHOUSE table.</en>
                setMessage("6123138" + wDelim + arg);
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        return true;
    }

    /**
     * 引数で指定した倉庫ステーションNoが倉庫種別「1:自動倉庫」でWAREHOUSE表に存在するか
     * 確認します。
     * @param arg 倉庫ステーションNoが
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     */
    public boolean isExistAutoWarehouseStationNo(String arg)
            throws ScheduleException
    {
        try
        {
            ToolWarehouseSearchKey key = new ToolWarehouseSearchKey();
            key.setWarehouseStationNo(arg);
//            key.setWarehouseType(Warehouse.AUTOMATID_WAREHOUSE);
            if (getWarehouseHandler().count(key) == 0)
            {
                // 6123284    WAREHOUSE表に自動倉庫STATIONNO={0}は存在しません。
                setMessage("6123284" + wDelim + arg);
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        return true;
    }

    /**
     * 引数で指定した倉庫ステーションNoが倉庫種別「2:平置き倉庫」でWAREHOUSE表に存在するか
     * 確認します。
     * @param arg 倉庫ステーションNo
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     */
    public boolean isExistFloorWarehouseStationNo(String arg)
            throws ScheduleException
    {
        try
        {
            ToolWarehouseSearchKey key = new ToolWarehouseSearchKey();
            key.setWarehouseStationNo(arg);
//            key.setWarehouseType(Warehouse.CONVENTIONAL_WAREHOUSE);
            if (getWarehouseHandler().count(key) == 0)
            {
                //6123285    WAREHOUSE表に平置き倉庫STATIONNO={0}は存在しません。
                setMessage("6123285" + wDelim + arg);
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        return true;
    }

    /**
     * 引数で指定した倉庫ステーションNoが倉庫種別「1:自動倉庫」かつフリーアロケーション運用が
     * 「1:フリーアロケーション運用あり」でWAREHOUSE表に存在するか確認します。
     * @param arg 倉庫ステーションNo
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     */
    public boolean isExistFreeAllocationOnWarehouseStationNo(String arg)
            throws ScheduleException
    {
        try
        {
            ToolWarehouseSearchKey key = new ToolWarehouseSearchKey();
            key.setWarehouseStationNo(arg);
//            key.setWarehouseType(Warehouse.AUTOMATID_WAREHOUSE);
            key.setFreeAllocationType(Warehouse.FREE_ALLOCATION_ON);
            if (getWarehouseHandler().count(key) == 0)
            {
                // 6123287=WAREHOUSE表にフリーアロケーション運用の自動倉庫STATIONNUMBER={0}は存在しません。
                setMessage("6123287" + wDelim + arg);
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        return true;
    }

    /**
     * 引数で指定した倉庫No（格納区分）が倉庫種別「1:自動倉庫」かつフリーアロケーション運用が
     * 「1:フリーアロケーション運用あり」でWAREHOUSE表に存在するか確認します。
     * @param arg 倉庫ステーションNo
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     */
    public boolean isExistFreeAllocationOnWarehouseStationNo(int arg)
            throws ScheduleException
    {
        try
        {
            ToolWarehouseSearchKey key = new ToolWarehouseSearchKey();
            key.setWarehouseNo(arg);
//            key.setWarehouseType(Warehouse.AUTOMATID_WAREHOUSE);
            key.setFreeAllocationType(Warehouse.FREE_ALLOCATION_ON);
            if (getWarehouseHandler().count(key) == 0)
            {
                // 6123287=WAREHOUSE表にフリーアロケーション運用の自動倉庫STATIONNUMBER={0}は存在しません。
                setMessage("6123287" + wDelim + arg);
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        return true;
    }

    /**<jp>
     * 引数で指定したアイルステーションNoがAISLE表に存在するか
     * 確認します。
     * @param アイルステーションNo
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check whether/not the aisle station no. specified through parameter exist in AISLE table.
     * @param arg Aisle Station No
     * @return :return true if data exists.
     * @throws ScheduleException 
     </en>*/
    public boolean isExistAisleStationNo(String arg)
            throws ScheduleException
    {
        try
        {
            ToolAisleSearchKey key = new ToolAisleSearchKey();
            key.setStationNo(arg);
            if (getAisleHandler().count(key) == 0)
            {
                //<jp>6123128    AISLE表にSTATIONNO={0}は存在しません</jp>
                //<en>6123128    There is no STATIONNO={0} in AISLE table.</en>
                setMessage("6123128" + wDelim + arg);
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }

        return true;
    }

    /**<jp>
     * 引数で指定したステーションNoがSTATIONTYPE表に存在するか
     * 確認します。
     * @param arg ステーションNo
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check whether/not the station no, specified through parameter exist in STATIONTYPE table.
     * @param arg Station NO
     * @return :return true if data exists.
     * @throws ScheduleException 
     </en>*/
    public boolean isExistStationType(String arg)
            throws ScheduleException
    {
        try
        {
            if (!getStationHandler().isStationType(arg))
            {
                //<jp>6123134 = STATIONTYPE表にSTATIONNO={0}は存在しません</jp>
                //<en>6123134 = There is no STATIONNO={0} in STATIONTYPE table.</en>
                setMessage("6123134" + wDelim + arg);
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }

        return true;
    }

    /**<jp>
     * 引数で指定したステーションNoがSTATION表に存在するか確認します。
     * @param arg ステーションNo
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 
     </jp>*/
    /**<en>
     * Check whether/not the station no. specified through parameter exist in STATION tabl
     * @param arg Station No
     * @return :return true if data exists.
     * @throws ScheduleException 
     </en>*/
    public boolean isExistStationNo(String arg)
            throws ScheduleException
    {
        try
        {
            ToolStationSearchKey key = new ToolStationSearchKey();
            key.setStationNo(arg);
            if (getStationHandler().count(key) == 0)
            {
                //<jp>6123148 = STATION表にSTATIONNO={0}は存在しません</jp>
                //<en>6123148 = There is no STATIONNO={0} in STATION table.</en>
                setMessage("6123148" + wDelim + arg);
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        return true;
    }

    /**
     * 引数で指定したステーションNoがSTATION表に存在するか確認します。
     * 作業場種別は、未指定、代表ステーションのデータが対象となります。
     * @param arg ステーションNo
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     */
    public boolean isExistRoutStationNo(String arg)
            throws ScheduleException
    {
        try
        {
            int[] workptype = {
                    Station.NOT_WORKPLACE,
                    Station.MAIN_STATIONS
            };
            ToolStationSearchKey key = new ToolStationSearchKey();
            key.setStationNo(arg);
            key.setWorkPlaceType(workptype);
            if (getStationHandler().count(key) == 0)
            {
                //<jp>6123148 = STATION表にSTATIONNO={0}は存在しません</jp>
                //<en>6123148 = There is no STATIONNO={0} in STATION table.</en>
                setMessage("6123148" + wDelim + arg);
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        return true;
    }

    /**
     * 引数で指定したステーションNoがSTATION表に存在するか確認します。
     * 作業場種別は、未指定のデータが対象となります。
     * @param arg ステーションNo
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     */
    public boolean isExistMachineStationNo(String arg)
            throws ScheduleException
    {
        try
        {
            ToolStationSearchKey key = new ToolStationSearchKey();
            key.setStationNo(arg);
            key.setWorkPlaceType(Station.NOT_WORKPLACE);
            if (getStationHandler().count(key) == 0)
            {
                //<jp>6123148 = STATION表にSTATIONNO={0}は存在しません</jp>
                //<en>6123148 = There is no STATIONNO={0} in STATION table.</en>
                setMessage("6123148" + wDelim + arg);
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        return true;
    }

    /**
     * 引数で指定したステーションNoがSTATION表に存在するか確認します。
     * ステーション種別が「1:入庫、2:出庫、3:入出庫兼用」のデータが対象となります。
     * @param arg ステーションNo
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     */
    public boolean isExistTerminalAreaStationNo(String arg)
            throws ScheduleException
    {
        try
        {
            int[] stationtype = {
                    Station.STATION_TYPE_IN,
                    Station.STATION_TYPE_OUT,
                    Station.STATION_TYPE_INOUT
            };
            ToolStationSearchKey key = new ToolStationSearchKey();
            key.setStationNo(arg);
            key.setStationType(stationtype);
            if (getStationHandler().count(key) == 0)
            {
                //<jp>6123148 = STATION表にSTATIONNO={0}は存在しません</jp>
                //<en>6123148 = There is no STATIONNO={0} in STATION table.</en>
                setMessage("6123148" + wDelim + arg);
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        return true;
    }

    /**
     * 引数で指定したエリアがDMAREA表に仮置エリアで存在するか確認します。
     * エリア種別は、仮置エリアとなります。
     * @param areano エリアNo
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     */
    public boolean isExistTemporaryAreaNo(String areano)
            throws ScheduleException
    {
        try
        {
            ToolAreaSearchKey key = new ToolAreaSearchKey();
            key.setAreaType(SystemDefine.AREA_TYPE_TEMPORARY);
            key.setAreaNo(areano);
            if (getWmsAreaHandler().count(key) == 0)
            {
                //<jp>6123292={0}は仮置エリアではありません。</jp>
                setMessage("6123292" + wDelim + areano);
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        return true;
    }

    /**
     * 引数で指定したエリアがDMAREA表にAS/RS以外のエリア種別で存在するか確認します。
     * @param areano エリアNo
     * @return 存在する場合はtrue、存在しない場合は、falseを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     */
    public boolean isExistAreaNo(String areano)
            throws ScheduleException
    {
        try
        {
            ToolAreaSearchKey key = new ToolAreaSearchKey();
            String[] type = {
                    SystemDefine.AREA_TYPE_FLOOR,
                    SystemDefine.AREA_TYPE_MOVING,
                    SystemDefine.AREA_TYPE_TEMPORARY
            };
            key.setValue("AREA_TYPE", type);
            key.setAreaNo(areano);
            if (getWmsAreaHandler().count(key) == 0)
            {
                //<jp>6123291={0}はエリアマスタに存在しません。</jp>
                setMessage("6123291" + wDelim + areano);
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }

        //<jp>6123290=エリアNo {0}はエリアマスタに存在します。</jp>
        setMessage("6123290" + wDelim + areano);
        return true;
    }

    /**<jp>
     * 引数で指定したステーションNoがSTATION表,AISLE表のどちらかに存在するか確認します。
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 
     </jp>*/
    /**<en>
     * Check whether/not the station no. specified through parameter exist either in STATION table
     * or in AISLE table.
     * @param arg ステーションNo
     * @return :return true if data exists.
     * @throws ScheduleException 
     </en>*/
    public boolean isExistAllStationNo(String arg)
            throws ScheduleException
    {
        if (!isExistStationNo(arg))
        {
            if (!isExistAisleStationNo(arg))
            {
                //<jp>6123147 = STATION表,AISLE表のどちらにもSTATIONNO={0}は存在しません</jp>
                //<en>6123147 = There is no STATIONNO={0} either in STATION table or in AISLE table.</en>
                setMessage("6123147" + wDelim + arg);
                return false;
            }
        }

        return true;
    }

    /**
     * 引数で指定したステーションNoがSTATION表,AISLE表のどちらかに存在するか確認します。
     * 搬送ルート設定用です。
     * @param arg ステーションNo
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     */
    public boolean isExistAllRoutStationNo(String arg)
            throws ScheduleException
    {
        if (!isExistRoutStationNo(arg))
        {
            if (!isExistAisleStationNo(arg))
            {
                //<jp>6123147 = STATION表,AISLE表のどちらにもSTATIONNO={0}は存在しません</jp>
                //<en>6123147 = There is no STATIONNO={0} either in STATION table or in AISLE table.</en>
                setMessage("6123147" + wDelim + arg);
                return false;
            }
        }

        return true;
    }

    /**
     * 引数で指定したステーションNoがSTATION表,AISLE表のどちらかに存在するか確認します。
     * 機器情報設定用です。
     * @param arg ステーションNo
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     */
    public boolean isExistAllMachiniStationNo(String arg)
            throws ScheduleException
    {
        if (!isExistMachineStationNo(arg))
        {
            if (!isExistAisleStationNo(arg))
            {
                //<jp>6123147 = STATION表,AISLE表のどちらにもSTATIONNO={0}は存在しません</jp>
                //<en>6123147 = There is no STATIONNO={0} either in STATION table or in AISLE table.</en>
                setMessage("6123147" + wDelim + arg);
                return false;
            }
        }

        return true;
    }

    /**<jp>
     * 引数で指定したターミナルナンバーがTERMINALAREA表に存在するか
     * 確認します。
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 
     </jp>*/
    /**<en>
     * Check whether/not the terminal no. specified through parameter exist in TERMINALAREA table.
     * @return :return true if data exists.
     * @throws ScheduleException 
     </en>*/
    //    public boolean isExistTATerminalNumber(String arg) throws ScheduleException
    //    {
    //        try
    //        {
    //            ToolTerminalAreaSearchKey key = new ToolTerminalAreaSearchKey();
    //            key.setTerminalNumber(arg);
    //            if (getTerminalAreaHandler().count(key) == 0)
    //            {
    //                //<jp>6123230=TERMINALAREA表にTERMINALNUMBER={0}は存在しません</jp>
    //                //<en>6123230=There is no TERMINALNUMBER={0} in TERMINALAREA table.</en>
    //                setMessage("6123230" + wDelim + arg);
    //                
    //                return false;
    //            }
    //        }
    //        catch (ReadWriteException e)
    //        {
    //            e.printStackTrace();
    //            throw new ScheduleException(e.getMessage());
    //        }
    //        return true;
    //    }
    /**<jp>
     * 引数で指定したエリアIDがTERMINALAREA表に存在するか
     * 確認します。
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 
     </jp>*/
    /**<en>
     * Check whether/not the area ID specified through parameter exist in TERMINALAREA table.
     * @return :return true if data exists.
     * @throws ScheduleException 
     </en>*/
    //    public boolean isExistAreaId(int arg) throws ScheduleException
    //    {
    //        try
    //        {
    //            ToolTerminalAreaSearchKey key = new ToolTerminalAreaSearchKey();
    //            key.setAreaId(arg);
    //            if (getTerminalAreaHandler().count(key) == 0)
    //            {
    //                //<jp>6123231=TERMINALAREA表にAREAID={0}は存在しません</jp>
    //                //<en>6123231=There is no AREAID={0} in TERMINALAREA table.</en>
    //                setMessage("6123231" + wDelim + arg);
    //                
    //                return false;
    //            }
    //        }
    //        catch (ReadWriteException e)
    //        {
    //            e.printStackTrace();
    //            throw new ScheduleException(e.getMessage());
    //        }
    //        return true;
    //    }
    /**<jp>
     * 引数で指定したメニューIDがAWCMENU表に存在するか
     * 確認します。
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 
     </jp>*/
    /**<en>
     * Check whether/not the menu ID specified through parameter exist in AWCMENU table.
     * @param arg file Name
     * @param filename fuke name
     * @param locale locale
     * @return :return true if data exists.
     * @throws ScheduleException 
     </en>*/
    public boolean isExistMenuId(String arg, String filename, Locale locale)
            throws ScheduleException
    {
        try
        {
            String filepath = filename + "/MenuText_" + locale;
            ToolMenuTextHandler menutextHandle = new ToolMenuTextHandler(filepath);
            String[] category = menutextHandle.getCategorys();
            boolean flag = false;
            for (int i = 0; i < category.length; i++)
            {
                String[] key = menutextHandle.getKeys(category[i]);
                for (int j = 0; j < key.length; j++)
                {

                    ToolMenuText toolMenuText = menutextHandle.findMenuText(category[i], key[j]);
                    String menuid = toolMenuText.getKey();
                    if (!menuid.substring(4, 5).equals("0"))
                    {
                        if (arg.equals(menuid))
                        {
                            flag = true;
                        }
                    }
                }
            }
            if (flag == false)
            {
                //<jp>6123160 = AWCMENU表にMenu_TextにないメニューID（{0}）が存在します</jp>
                //<en>6123160=The menu ID {0} is not in awcmenu table exists in Menu_Text.</en>
                setMessage("6123160" + wDelim + arg);
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }

        return true;
    }

    /**<jp>
     * 引数で指定したゾーンIDがSHELF表に存在するか
     * 確認します。
     * @param arg ゾーンID
     * @param type ソフトゾーンIDかハードゾーンIDか
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 
     </jp>*/
    /**<en>
     * Check whether/not the zone ID specified through parameter exist in SHELF table.
     * @param arg  :zone ID
     * @param type :soft zone ID/hard zone ID
     * @return :return true if data exists.
     * @throws ScheduleException 
     </en>*/
    public boolean isExistShelf(int arg, int type)
            throws ScheduleException
    {
        try
        {
            ToolShelfSearchKey key = new ToolShelfSearchKey();
            if (type == Shelf.HARD)
            {
                key.setHardZoneId(arg);
            }
            else if (type == Shelf.SOFT)
            {
                key.setSoftZoneId(arg);
            }

            if (getShelfHandler().count(key) == 0)
            {
                if (type == Shelf.HARD)
                {
                    //<jp>6123243    SHELF表にHARDZONEID={0}は存在しません</jp>
                    //<en>6123243    There is no HARDZONEID={0} in SHELF table.</en>
                    setMessage("6123243" + wDelim + arg);
                    return false;
                }
                else if (type == Shelf.SOFT)
                {
                    //<jp>SHELF表にSOFTZONEID={0}は存在しません</jp>
                    //<en>There is no SOFTZONEID={0} in SHELF table.</en>
                    setMessage("6123268" + wDelim + arg);
                    return false;
                }
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        return true;
    }

    /**<jp>
     * 引数で指定したソフトゾーンIDがSOFTZONE表に存在するか
     * 確認します。
     * @param arg ソフトゾーンID
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check whether/not the soft zone id, specified through parameter exist in SOFTZONE table.
     * @param arg Soft zone id
     * @return :return true if data exists.
     * @throws ScheduleException 
     </en>*/
    public boolean isExistSoftZone(int arg)
            throws ScheduleException
    {
        try
        {
            ToolSoftZoneSearchKey key = new ToolSoftZoneSearchKey();
            key.setSoftZoneID(arg);
            if (getSoftZoneHandler().count(key) == 0)
            {
                //<jp>6123260=SOFTZONE表にSOFTZONEID={0}は存在しません。</jp>
                //<en>6123260=There is no SOFTZONEID={0} in SOFTZONE table.</en>
                setMessage("6123260" + wDelim + arg);
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }

        return true;
    }

    /**<jp>
     * 引数で指定したソフトゾーンIDがSOFTZONE表に存在するか
     * 確認します。
     * @param arg ソフトゾーンID
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check whether/not the soft zone id, specified through parameter exist in SOFTZONE table.
     * @param arg Soft zone id
     * @return :return true if data exists.
     * @throws ScheduleException 
     </en>*/
    public boolean isExistSoftZonePriority(String warehouseStationNo, int zoneId)
            throws ScheduleException
    {
        try
        {
            ToolSoftZonePrioritySearchKey key = new ToolSoftZonePrioritySearchKey();
            key.setWHStationNo(warehouseStationNo);
            key.setSoftZoneID(zoneId);
            if (getSoftZonePriorityHandler().count(key) == 0)
            {
                //<jp>6123237=SODTZONEPRIORIEY表にWAREHOUSENO={0},SOFTZONEID={1}は存在しません。</jp>
                setMessage("6123260" + wDelim + warehouseStationNo + wDelim + zoneId);
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }

        return true;
    }

    /**<jp>
     * 引数で指定した荷幅がWIDTH表に存在するか
     * 確認します。
     * @param arg 荷幅
     * @return 存在する場合はtrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check whether/not the soft zone id, specified through parameter exist in WIDTH table.
     * @param arg width
     * @return :return true if data exists.
     * @throws ScheduleException 
     </en>*/
    public boolean isExistWidth(int arg)
            throws ScheduleException
    {
        try
        {
            ToolWidthSearchKey key = new ToolWidthSearchKey();
            key.setWidth(arg);
            if (getWidthHandler().count(key) == 0)
            {
                //<jp>6123275=WIDTH表にWIDTH={0}は存在しません。</jp>
                //<en>6123260=There is no WIDTH={0} in WIDTH table.</en>
                setMessage("6123275" + wDelim + arg);
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }

        return true;
    }

    /**<jp>
     * AGCNoのチェックを行います。このメソッドでは<br>
     * ・ １以上の値であること<br>
     * のチェックを行います。
     * @param stArg  チェックを行うバンクを指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the AGCNo.  In this method, a check will be done for below;
     *  -the value should be 1 or greater.<br>
     * @param agc  :specifies the bank to check.
     * @return       :returns the check result. Return true if all are correct.
     </en>*/
    public boolean checkAgcNo(int agc)
    {
        if (agc < 1)
        {
            //<jp>6123214    AGCNo.には1以上の値を入力してください</jp>
            //<en>6123214    Please enter 1 or greater value for AGCNo.</en>
            setMessage("6123214");
            return false;
        }
        return true;
    }

    /**<jp>
     * アイルNoのチェックを行います。このメソッドでは<br>
     * ・ １以上の値であること<br>
     * のチェックを行います。
     * @param stArg  チェックを行うバンクを指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the aisle no. In this method, a check will be done for below;
     *  -the value should be 1 or greater.<br>
     * @param aisle  :specifies the bank to check.
     * @return       :returns the check result. Return true if all are correct.
     </en>*/
    public boolean checkAisleNo(int aisle)
    {
        if (aisle < 1)
        {
            //<jp>6123120    アイルNoには１以上の値を指定してください</jp>
            //<en>6123120    Please enter 1 or greater valud for the aisle no.</en>
            setMessage("6123120");
            return false;
        }
        return true;
    }

    /**<jp>
     * 最大混載数のチェックを行います。このメソッドでは<br>
     * ・ １以上の値であること<br>
     * のチェックを行います。
     * @param qty  チェックを行う最大混載数を指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the max. mix-load quantity. In this method, a check will be done for below;
     *  -the value should be 1 or greater.<br>
     * @param qty  :specify the max. mix-load quantity to be checked.
     * @return    :return hte checked results. It return true if all are correct.
     </en>*/
    public boolean checkMaxMixedQuantity(int qty)
    {
        if (qty < 1)
        {
            //<jp>6123207 = 最大混載数は1以上の値を入力してください</jp>
            //<en>6123207 = Please enter 1 or greater value for max. mix-load quantity.</en>
            setMessage("6123207");
            return false;
        }

        return true;
    }

    /**<jp>
     * 最大格納数のチェックを行います。このメソッドでは<br>
     * ・ １以上の値であること<br>
     * のチェックを行います。
     * @param qty  チェックを行う最大混載数を指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the max. storage quantity. In this method, a check will be done for below;
     *  -the value should be 1 or greater.<br>
     * @param qty  :specify the max. mix-load quantity to be checked.
     * @return    :return hte checked results. It return true if all are correct.
     </en>*/
    public boolean checkMaxStorage(int qty)
    {
        if (qty < 1)
        {
            //<jp>6123248 = 最大格納数は1以上の値を入力してください。</jp>
            //<en>6123248 = Please enter 1 or greater value for max. storage quantity.</en>
            setMessage("6123248");
            return false;
        }

        return true;
    }

    /**<jp>
     * 優先順位のチェックを行います。このメソッドでは<br>
     * ・ １以上の値であること<br>
     * のチェックを行います。
     * @param qty  チェックを行う優先順位を指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the max. storage quantity. In this method, a check will be done for below;
     *  -the value should be 1 or greater.<br>
     * @param qty  :specify the max. mix-load quantity to be checked.
     * @return    :return hte checked results. It return true if all are correct.
     </en>*/
    public boolean checkPriority(int qty)
    {
        if (qty < 1)
        {
            //<jp>6123253=優先順位は1以上の値を入力してください。</jp>
            //<en>6123253=Please enter 1 or greater value for priority.</en>
            setMessage("6123253");
            return false;
        }

        return true;
    }

    /**<jp>
     * バンク（範囲）のチェックを行います。このメソッドでは<br>
     * ・ 開始バンクは終了バンクよりも小さな値であること<br>
     * ・ １以上の値であること<br>
     * のチェックを行います。
     * @param startloc  チェックを行うバンクを指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the bank (range). In this method, the following will be checked.<br>
     *  -the start bank should be smaller than end bank in the value.<br>
     *  -the value should be 1 or greater.<br>
     * @param startloc  :specifies the bank to be checked.
     * @param endloc  last bank
     * @return       :returns the check result. Returns true if all are correct.
     </en>*/
    public boolean checkBank(int startloc, int endloc)
    {
        if (startloc < 1)
        {
            //<jp>6123071 = 範囲には１以上の値を指定してください</jp>
            //<en>6123071 = Please specify 1 or greater value for the range.</en>
            setMessage("6123071");
            return false;
        }
        if (endloc < 1)
        {
            //<jp>6123071 = 範囲には１以上の値を指定してください</jp>
            //<en>6123071 = Please specify 1 or greater value for the range.</en>
            setMessage("6123071");
            return false;
        }
        if (startloc > endloc)
        {
            //<jp>6123068 = 開始バンクは終了バンクよりも小さな値を指定してください</jp>
            //<en>6123068 = Please set the smaller value for start bank than end bank.</en>
            setMessage("6123068");
            return false;
        }

        return true;
    }

    /**<jp>
     * ベイ（範囲）のチェックを行います。このメソッドでは<br>
     * ・ 開始ベイは終了ベイよりも小さな値であること<br>
     * ・ １以上の値であること<br>
     * のチェックを行います。
     * @param startloc  チェックを行うベイを指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the bay (range). In this method, the following will be checked.<br>
     *  -the start bay should be smaller than end bay in the value.<br>
     *  -the value should be 1 or greater.<br>
     * @param startloc  :specifies the bay to be checked.
     * @param endloc end bay
     * @return       :returns the check result. Returns true if all are correct.
     </en>*/
    public boolean checkBay(int startloc, int endloc)
    {
        if (startloc < 1)
        {
            //<jp>6123071 = 範囲には１以上の値を指定してください</jp>
            //<en>6123071 = Please specify 1 or greater value for the range.</en>
            setMessage("6123071");
            return false;
        }
        if (endloc < 1)
        {
            //<jp>6123071 = 範囲には１以上の値を指定してください</jp>
            //<en>6123071 = Please specify 1 or greater value for the range.</en>
            setMessage("6123071");
            return false;
        }
        if (startloc > endloc)
        {
            //<jp>6123069 = 開始ベイは終了ベイよりも小さな値を指定してください</jp>
            //<en>6123069 = Please set the smaller value for start bay than the value of end bay.</en>
            setMessage("6123069");
            return false;
        }

        return true;
    }

    /**<jp>
     * レベル（範囲）のチェックを行います。このメソッドでは<br>
     * ・ 開始レベルは終了レベルよりも小さな値であること<br>
     * ・ １以上の値であること<br>
     * のチェックを行います。
     * @param startloc  チェックを行うレベルを指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the level (range). In this method, the following will be checked.<br>
     *  -the start level should be smaller than end level in the value.<br>
     *  -the value should be 1 or greater.<br>
     * @param startloc  :specifies the level to be checked.
     * @param endloc end level
     * @return       :returns the check result. Returns true if all are correct.
     </en>*/
    public boolean checkLevel(int startloc, int endloc)
    {
        if (startloc < 1)
        {
            //<jp>6123071 = 範囲には１以上の値を指定してください</jp>
            //<en>6123071 = Please specify 1 or greater value for the range.</en>
            setMessage("6123071");
            return false;
        }
        if (endloc < 1)
        {
            //<jp>6123071 = 範囲には１以上の値を指定してください</jp>
            //<en>6123071 = Please specify 1 or greater value for the range.</en>
            setMessage("6123071");
            return false;
        }
        if (startloc > endloc)
        {
            //<jp>6123070 = 開始レベルは終了レベルよりも小さな値を指定してください</jp>
            //<en>6123070 = Please set the smaller value for start level than the value of end level.</en>
            setMessage("6123070");
            return false;
        }

        return true;
    }

    /**<jp>
     * レベル（範囲）のチェックを行います。このメソッドでは<br>
     * ・ 開始レベルは終了レベルよりも小さな値であること<br>
     * ・ １以上の値であること<br>
     * のチェックを行います。
     * @param startloc  チェックを行うレベルを指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the level (range). In this method, the following will be checked.<br>
     *  -the start level should be smaller than end level in the value.<br>
     *  -the value should be 1 or greater.<br>
     * @param startloc  :specifies the level to be checked.
     * @param endloc end level
     * @return       :returns the check result. Returns true if all are correct.
     </en>*/
    public boolean checkAddress(int startloc, int endloc)
    {
        if (startloc < 1)
        {
            //<jp>6123071 = 範囲には１以上の値を指定してください</jp>
            //<en>6123071 = Please specify 1 or greater value for the range.</en>
            setMessage("6123071");
            return false;
        }
        if (endloc < 1)
        {
            //<jp>6123071 = 範囲には１以上の値を指定してください</jp>
            //<en>6123071 = Please specify 1 or greater value for the range.</en>
            setMessage("6123071");
            return false;
        }
        if (startloc > endloc)
        {
            //<jp>6123273=開始アドレスは終了アドレスよりも小さな値を指定してください</jp>
            //<en>6123273=Please set the smaller value for start address than the value of end address.</en>
            setMessage("6123273");
            return false;
        }

        return true;
    }

    /**
     * アイル位置（範囲）のチェックを行います。このメソッドでは<br>
     * ・ 開始アイル位置は終了アイル位置よりも小さな値であること<br>
     * ・ １以上の値であること<br>
     * のチェックを行います。
     * @param startpos  チェックを行うアイル位置を指定します。
     * @param endpos 終了アイル位置
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     */
    public boolean checkAislePosition(int startpos, int endpos)
    {
        if (startpos < 1)
        {
            //<jp>6123071 = 範囲には１以上の値を指定してください</jp>
            //<en>6123071 = Please specify 1 or greater value for the range.</en>
            setMessage("6123071");
            return false;
        }
        if (endpos < 1)
        {
            //<jp>6123071 = 範囲には１以上の値を指定してください</jp>
            //<en>6123071 = Please specify 1 or greater value for the range.</en>
            setMessage("6123071");
            return false;
        }
        if (startpos > endpos)
        {
            //6123280=開始アイル位置は終了アイル位置よりも小さな値を指定してください。
            setMessage("6123280");
            return false;
        }

        if (startpos != endpos - 1)
        {
            //6123281=アイル位置の入力が不正です。
            setMessage("6123281");
            return false;
        }

        return true;
    }

    /**<jp>
     * ステーションNoのチェックを行います。このメソッドでは<br>
     * ・ 禁止文字<br>
     * のチェックを行います。
     * @param stArg  チェックを行うステーションNoを指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the station no. In this method, a check will be done for below;<br>
     *  -unacceptable letters and symbols<br>
     * @param stArg  :specifies the station no. to be checked
     * @return       :returns the check result. Returns true if all are correct.
     </en>*/
    public boolean checkStationNo(String stArg)
    {
        if (isUndefinedChar(stArg))
        {
            //<jp>6123009 = ステーションNo.にシステムで使用できない文字が含まれています</jp>
            //<en>6123009 = The station no. contains the unacceptable letters in system.</en>
            setMessage("6123009");
            return false;
        }

        return true;
    }

    /**<jp>
     * ステーション名称のチェックを行います。このメソッドでは<br>
     * ・ 禁止文字<br>
     * のチェックを行います。
     * @param stArg  チェックを行うステーション名称を指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the station name. In this method, a check will be done for below;<br>
     *  -unacceptable letters and symbols<br>
     * @param stArg  :specifies the station name to be checked
     * @return       :returns the check result. Returns true if all are correct.
     </en>*/
    public boolean checkStationName(String stArg)
    {
        if (isUndefinedChar(stArg))
        {
            //<jp>6123167 = ステーション名称にシステムで使用できない文字が含まれています</jp>
            //<en>6123167 = The station nome contains the unacceptable letters in system.</en>
            setMessage("6123167");
            return false;
        }

        return true;
    }

    /**<jp>
     * 倉庫名称のチェックを行います。このメソッドでは<br>
     * ・ 禁止文字<br>
     * のチェックを行います。
     * @param stArg  チェックを行う倉庫名称を指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the warehouse name. In this method, a check will be done for below;<br>
     *  -unacceptable letters and symbols<br>
     * @param stArg  :specifies the warehouse name to be checked
     * @return       :returns the check result. Returns true if all are correct.
     </en>*/
    public boolean checkWarehouseName(String stArg)
    {
        if (isUndefinedChar(stArg))
        {
            //<jp>6123017 = 倉庫名称にシステムで使用できない文字が含まれています</jp>
            //<en>6123017 = The warehouse name contains the unacceptable letters in system.</en>
            setMessage("6123017");
            return false;
        }
        return true;
    }

    /**<jp>
     * ユーザー名のチェックを行います。このメソッドでは<br>
     * ・ 禁止文字<br>
     * のチェックを行います。
     * @param stArg  チェックを行うユーザー名を指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the user name. In this method, a check will be done for below;<br>
     *  -unacceptable letters and symbols<br>
     * @param stArg  :specifies the user name to be checked
     * @return       :returns the check result. Returns true if all are correct.
     </en>*/
    public boolean checkUserName(String stArg)
    {
        if (isUndefinedChar(stArg))
        {
            //<jp>6123022 = ユーザー名にシステムで使用できない文字が含まれています</jp>
            //<en>6123022 = The user name contains the unacceptable letters in system.</en>
            setMessage("6123022");
            return false;
        }

        return true;
    }

    /**<jp>
     * パスワードのチェックを行います。このメソッドでは<br>
     * ・ 禁止文字<br>
     * のチェックを行います。
     * @param stArg  チェックを行うパスワードを指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the password. In this method, a check will be done for below;<br>
     *  -unacceptable letters and symbols<br>
     * @param stArg  :specifies the password to be checked
     * @return       :returns the check result. Returns true if all are correct.
     </en>*/
    public boolean checkPassword(String stArg)
    {
        if (isUndefinedChar(stArg))
        {
            //<jp>6123023 = パスワードにシステムで使用できない文字が含まれています</jp>
            //<en>6123023 = The password contains the unacceptable letters in system.</en>
            setMessage("6123023");
            return false;
        }
        return true;
    }

    /**<jp>
     * 端末No.のチェックを行います。このメソッドでは<br>
     * ・ 禁止文字<br>
     * のチェックを行います。
     * @param stArg  チェックを行うパスワードを指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the terminal no. In this method, a check will be done for below;<br>
     *  -unacceptable letters and symbols<br>
     * @param stArg  :specifies the terminal no. to be checked
     * @return       :returns the check result. Returns true if all are correct.
     </en>*/
    public boolean checkTerminalNumber(String stArg)
    {
        if (isUndefinedChar(stArg))
        {
            //<jp>6123104 = 端末No.にシステムで使用できない文字が含まれています</jp>
            //<en>6123104 = The terminal no. contains the unacceptable letters in system.</en>
            setMessage("6123104");
            return false;
        }
        if (stArg.equals("0"))
        {
            //<jp>6123212 = 端末No.には1以上の値を入力してください</jp>
            //<en>6123212 = Please enter 1 or greater number for the terminal no.</en>
            setMessage("6123212");
            return false;
        }

        return true;
    }

    /**<jp>
     * 端末名のチェックを行います。このメソッドでは<br>
     * ・ 禁止文字<br>
     * のチェックを行います。
     * @param stArg  チェックを行うパスワードを指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the terminal name. In this method, a check will be done for below;<br>
     *  -unacceptable letters and symbols<br>
     * @param stArg  :specifies the terminal name to be checked
     * @return       :returns the check result. Returns true if all are correct.
     </en>*/
    public boolean checkTerminalName(String stArg)
    {
        if (isUndefinedChar(stArg))
        {
            //<jp>6123105 = 端末名にシステムで使用できない文字が含まれています</jp>
            //<en>6123105 = The terminal name contains the unacceptable letters in system.</en>
            setMessage("6123105");
            return false;
        }

        return true;
    }

    /**<jp>
     * ゾーン名称のチェックを行います。このメソッドでは<br>
     * ・ 禁止文字<br>
     * のチェックを行います。
     * @param stArg  チェックを行うゾーン名称を指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the zone name. In this method, a check will be done for below;<br>
     *  -unacceptable letters and symbols<br>
     * @param stArg  :specifies the zone name to be checked
     * @return       :returns the check result. Returns true if all are correct.
     </en>*/
    public boolean checkZoneName(String stArg)
    {
        if (isUndefinedChar(stArg))
        {
            //<jp>6123083 = ゾーン名称にシステムで使用できない文字が含まれています。</jp>
            //<en>6123083 = The zone name contains the unacceptable letters in system.</en>
            setMessage("6123083");
            return false;
        }

        return true;
    }

    /**<jp>
     * 荷姿名称のチェックを行います。このメソッドでは<br>
     * ・ 禁止文字<br>
     * のチェックを行います。
     * @param stArg  チェックを行う荷姿名称を指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the name of the load size. In this method, a check will be done for below;<br>
     *  -unacceptable letters and symbols<br>
     * @param stArg  :specifies the name of the load size to be checked
     * @return       :returns the check result. Returns true if all are correct.
     </en>*/
    public boolean checkLoadSizeName(String stArg)
    {
        if (isUndefinedChar(stArg))
        {
            //<jp>6123084 = 荷姿名称にシステムで使用できない文字が含まれています</jp>
            //<en>6123084 = The load size name contains the unacceptable letters in system.</en>
            setMessage("6123084");
            return false;
        }

        return true;
    }

    /**<jp>
     * 荷幅名称のチェックを行います。このメソッドでは<br>
     * ・ 禁止文字<br>
     * のチェックを行います。
     * @param stArg  チェックを行う荷幅名称を指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the name of the load width. In this method, a check will be done for below;<br>
     *  -unacceptable letters and symbols<br>
     * @param stArg  :specifies the name of the load width to be checked
     * @return       :returns the check result. Returns true if all are correct.
     </en>*/
    public boolean checkWidthName(String stArg)
    {
        if (isUndefinedChar(stArg))
        {
            //<jp>6123085 = 荷姿幅称にシステムで使用できない文字が含まれています</jp>
            //<en>6123085 = The load width name contains the unacceptable letters in system.</en>
            setMessage("6123085");
            return false;
        }

        return true;
    }

    /**<jp>
     * ホスト名称のチェックを行います。このメソッドでは<br>
     * ・ 禁止文字<br>
     * のチェックを行います。
     * @param stArg  チェックを行うホスト名称を指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the host name. In this method, a check will be done for below;<br>
     *  -unacceptable letters and symbols<br>
     * @param stArg  :specifies the host name to be checked
     * @return       :returns the check result. Returns true if all are correct.
     </en>*/
    public boolean checkHostName(String stArg)
    {
        if (isUndefinedChar(stArg))
        {
            //<jp>6123169 = ホスト名にシステムで使用できない文字が含まれています</jp>
            //<en>6123169 = The host name contains the unacceptable letters in system.</en>
            setMessage("6123169");
            return false;
        }
        return true;
    }

    /**<jp>
     * IPアドレスのチェックを行います。このメソッドでは<br>
     * ・ 禁止文字<br>
     * のチェックを行います。
     * @param stArg  チェックを行うIPアドレスを指定します。
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     </jp>*/
    /**<en>
     * Check the IP address. In this method, a check will be done for below;<br>
     *  -unacceptable letters and symbols<br>
     * @param stArg  :specifies the IP address to be checked
     * @return       :returns the check result. Returns true if all are correct.
     </en>*/
    public boolean checkIPAdress(String stArg)
    {
        if (isUndefinedChar(stArg))
        {
            //<jp>6123200 = IPアドレスにシステムで使用できない文字が含まれています</jp>
            //<en>6123200 = The IP address contains the unacceptable letters in system.</en>
            setMessage("6123200");
            return false;
        }
        return true;
    }

    /**<jp>
     * SHELFのハードゾーンのチェックを行います。このメソッドでは<br>
     * SHELF表に引数で渡された倉庫ステーションNo.でハードゾーンが0のSHELFが無いかチェックします。<br>
     * @param WHStno 倉庫ステーションNo.
     * @return    チェックの結果を返します。ハードゾーンが0のSHELFが無い場合はTrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     </jp>*/
    public boolean checkShelfHardZone(String WHStno)
            throws ScheduleException
    {
        try
        {
            ToolShelfSearchKey key = new ToolShelfSearchKey();
            key.setHardZoneId(HardZone.UN_SETTING);
            key.setWarehouseStationNo(WHStno);
            if (getShelfHandler().count(key) > 0)
            {
                //<jp>6123244=SHELF表のハードゾーンがすべて設定されていません。</jp>
                setMessage("6123244");
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }

        return true;
    }

    /**<jp>
     * SHELFのソフトゾーンのチェックを行います。このメソッドでは<br>
     * SHELF表にソフトゾーンが0のSHELFが無いかチェックします。<br>
     * @param arg 倉庫ステーションNo
     * @return    チェックの結果を返します。ソフトゾーンが0のSHELFが無い場合はTrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     </jp>*/
    public boolean checkShelfSoftZone(String warehouseStationNo)
            throws ScheduleException
    {
        try
        {
            ToolShelfSearchKey key = new ToolShelfSearchKey();
            key.setWarehouseStationNo(warehouseStationNo);
            key.setSoftZoneId(HardZone.UN_SETTING);
            if (getShelfHandler().count(key) > 0)
            {
                //<jp>6123265=SHELF表のソフトゾーンがすべて設定されていませ(WH_STATION_NO={0})。</jp>
                setMessage("6123265" + wDelim + warehouseStationNo);
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }

        return true;
    }

    /**<jp>
     * LOADSIZEのハードゾーンのチェックを行います。このメソッドでは<br>
     * HARDZONE表のフリーアロケーション運用の倉庫の荷姿が荷姿マスタ情報に登録されているかチェックします。<br>
     * @param arg 荷姿
     * @return    チェックの結果を返します。
     *             フリーアロケーション運用の倉庫の荷姿が荷姿マスタ情報に登録されている場合はTrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     </jp>*/
    public boolean checkLoadSizeHardZone(int arg)
            throws ScheduleException
    {
        try
        {
            ToolLoadSizeSearchKey key = new ToolLoadSizeSearchKey();

            // フリーアロケーション用ゾーンの荷姿を検索
            key.setLoadSize(arg);
            if (getLoadSizeHandler().count(key) == 0)
            {
                //<jp>6123269    LOADSIZE表にLOADSIZE={0}は存在しません。</jp>
                //<en>6123269    There is no LOADSIZE={0} in LOADSIZE table.</en>
                setMessage("6123269" + wDelim + arg);
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }

        return true;
    }

    /**<jp>
     * SHELFの荷幅のチェックを行います。このメソッドでは<br>
     * SHELF表にフリーアロケーション倉庫でアドレスが0のSHELFが無いかチェックします。<br>
     * @return    チェックの結果を返します。アドレスが0のSHELFが無い場合はTrueを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     </jp>*/
    public boolean checkShelfWidth()
            throws ScheduleException
    {
        try
        {
            ToolWarehouseSearchKey warehouseKey = new ToolWarehouseSearchKey();
            // フリーアロケーション用倉庫を検索
            warehouseKey.setFreeAllocationType(Warehouse.FREE_ALLOCATION_ON);
            Warehouse[] whArray = (Warehouse[])getWarehouseHandler().find(warehouseKey);

            for (int i = 0; i < whArray.length; i++)
            {
                // フリーアロケーション倉庫の最小棚（同一バンク、ベイ、レベルの場合は最小アドレスの棚）を検索する
                String whStNo = whArray[i].getStationNo();
                ToolShelfSearchKey shelfKey = new ToolShelfSearchKey();
                shelfKey.setWHStationNo(whStNo);
                Shelf[] shelfArray = (Shelf[])getShelfHandler().findFreeAllocationMinShelf(shelfKey);

                for (int j = 0; j < shelfArray.length; j++)
                {
                    // 荷幅マスタより最大の最大格納数を取得する
                    int maxStorage =
                            getWidthHandler().findMaxStorage(whStNo, shelfArray[j].getBankNo(),
                                    shelfArray[j].getBayNo(), shelfArray[j].getLevelNo());

                    // Shelfから該当棚の件数、最大アドレス、最小アドレスを取得する
                    int[] range =
                            getShelfHandler().getShelfWidthRange(shelfArray[j].getBankNo(), shelfArray[j].getBayNo(),
                                    shelfArray[j].getLevelNo(), shelfArray[j].getBankNo(), shelfArray[j].getBayNo(),
                                    shelfArray[j].getLevelNo(), whStNo);

                    // 以下の場合、整合性OK
                    // 1.件数が最大格納数である
                    // 2.最大アドレスが最大格納数である 
                    // 3.最小アドレスが1である 
                    if (range[0] != maxStorage || range[1] != maxStorage || range[2] != 1)
                    {
                        //<jp>6123250=SHELF表に荷幅分のアドレスが存在しません(WH_STATION_NO={0}, BANK_NO={1}, BAY_NO={2}, LEVEL_NO={3})。</jp>
                        setMessage("6123250" + wDelim + whStNo + wDelim + Integer.toString(shelfArray[j].getBankNo())
                                + wDelim + Integer.toString(shelfArray[j].getBayNo()) + wDelim
                                + Integer.toString(shelfArray[j].getLevelNo()));
                        return false;
                    }
                }
            }

            ToolShelfSearchKey key = new ToolShelfSearchKey();
            key.setAddressNo(0);
            if (getShelfHandler().countFreeAllocationShelf(key) > 0)
            {
                //<jp>6123249=SHELF表に荷幅が設定されていないフリーアロケーション棚が存在します。</jp>
                setMessage("6123249");
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }

        return true;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    /**<jp>
     * グループコントローラのハンドラークラスを返します。<br>
     * @return    グループコントローラのハンドラークラス<BR>
     </jp>*/
    protected ToolGroupControllerHandler getGCHandler()
    {
        return wGCHandler;
    }

    /**<jp>
     * WareHouseのハンドラークラスを返します。<br>
     * @return    WareHouseのハンドラークラス<BR>
     </jp>*/
    protected ToolWarehouseHandler getWarehouseHandler()
    {
        return wWarehouseHandler;
    }

    /**<jp>
     * アイルのハンドラークラスを返します。<br>
     * @return    アイルのハンドラークラス<BR>
     </jp>*/
    protected ToolAisleHandler getAisleHandler()
    {
        return wAisleHandler;
    }

    /**<jp>
     * ステーションのハンドラークラスを返します。<br>
     * @return    ステーションのハンドラークラス<BR>
     </jp>*/
    protected ToolStationHandler getStationHandler()
    {
        return wStationHandler;
    }

    /**<jp>
     * 棚のハンドラークラスを返します。<br>
     * @return    棚のハンドラークラス<BR>
     </jp>*/
    protected ToolShelfHandler getShelfHandler()
    {
        return wShelfHandler;
    }

    /**<jp>
     * エリア情報のハンドラークラスを返します。<br>
     * @return    エリアのハンドラークラス<BR>
     </jp>*/
    protected ToolWmsAreaHandler getWmsAreaHandler()
    {
        return wWmsAreaHandler;
    }

    /**<jp>
     * 一時エリア情報のハンドラークラスを返します。<br>
     * @return    エリアのハンドラークラス<BR>
     </jp>*/
    protected ToolAreaHandler getAreaHandler()
    {
        return wAreaHandler;
    }

    /**<jp>
     * 荷姿情報のハンドラークラスを返します。<br>
     * @return    荷姿のハンドラークラス<BR>
     </jp>*/
    protected ToolLoadSizeHandler getLoadSizeHandler()
    {
        return wLoadSizeHandler;
    }

    /**<jp>
     * 荷幅情報のハンドラークラスを返します。<br>
     * @return    荷幅のハンドラークラス<BR>
     </jp>*/
    protected ToolWidthHandler getWidthHandler()
    {
        return wWidthHandler;
    }

    /**<jp>
     * ソフトゾーン情報のハンドラークラスを返します。<br>
     * @return    ソフトゾーンのハンドラークラス<BR>
     </jp>*/
    protected ToolSoftZoneHandler getSoftZoneHandler()
    {
        return wSoftZoneHandler;
    }

    /**<jp>
     * ソフトゾーン優先順情報のハンドラークラスを返します。<br>
     * @return    ソフトゾーン優先順のハンドラークラス<BR>
     </jp>*/
    protected ToolSoftZonePriorityHandler getSoftZonePriorityHandler()
    {
        return wSoftZonePriorityHandler;
    }

    /**<jp>
     * チェックが保持するメッセージへセットします。
     * チェックした内容がＮＧの場合に、その詳細をセットします。
     * @param メッセージの内容
     </jp>*/
    /**<en>
     * Set to the message that check preserves.
     * In case the checked content was unacceptable, the detail will be set.
     * @param msg :contents of the message
     </en>*/
    protected void setMessage(String msg)
    {
        wMessage = msg;
    }

    /**<jp>
     * 禁止文字チェックを行います。
     * @param  param チェックの対象となるパラメータ
     * @return 禁止文字だった場合True。
     </jp>*/
    /**<en>
     * Check the unacceptable letters and symbols.
     * @param  param :parameter to be checked
     * @return :return true if unacceptable letters and symbols was included.
     </en>*/
    private boolean isUndefinedChar(String param)
    {
        //<jp>禁止文字のチェック</jp>
        //<en>Check for the unacceptable letters and symbols.</en>
        return (isPatternMatching(param));
    }

    /**<jp>
     * 指定された文字列内に、システムで定義された禁止文字が含まれているかどうか検証します。
     * 禁止文字の定義は、CommonParamにて指定します。
     * @param pattern 対象となる文字列を指定します。
     * @return 文字列中に禁止文字が含まれる場合はtrue, 禁止文字が含まれない場合はfalseを返します。
     </jp>*/
    /**<en>
     * Examine whether/not the unacceptable letters and symbols defined by system are contained
     * in specified string.
     * Definition of unacceptable letters and symbols can be specified by CommonParam.
     * @param pattern :specifies the target string.
     * @return :retutn true if the string contains unacceptable letters and symbols, or false if
     * unacceptable letters and symbols are not contained.
     </en>*/
    private static boolean isPatternMatching(String pattern)
    {
        return (isPatternMatching(pattern, ToolParam.getParam("NG_PARAMETER_TEXT")));
    }

    /**<jp>
     * 指定された文字列内に、システムで定義された禁止文字が含まれているかどうか検証します。
     * 禁止文字の定義は、CommonParamにて指定します。
     * @param pattern 対象となる文字列を指定します。
     * @param ngshars 禁止文字
     * @return 文字列中に禁止文字が含まれる場合はtrue, 禁止文字が含まれない場合はfalseを返します。
     </jp>*/
    /**<en>
     * Examine whether/not the system defined unacceptable letters and symbols are contained
     * in specified string.
     * Definition of unacceptable letters and symbols can be specified by CommonParam.
     * @param pattern :specifies the target string.
     * @param ngshars :unacceptable letters and symbols
     * @return :retutn true if the string contains unacceptable letters and symbols, or false if
     * unacceptable letters and symbols are not contained.
     </en>*/
    private static boolean isPatternMatching(String pattern, String ngshars)
    {
        if (pattern != null && !pattern.equals(""))
        {
            for (int i = 0; i < ngshars.length(); i++)
            {
                if (pattern.indexOf(ngshars.charAt(i)) > -1)
                {
                    return true;
                }
            }
        }
        return false;
    }

    // Private methods -----------------------------------------------

}
//end of class
