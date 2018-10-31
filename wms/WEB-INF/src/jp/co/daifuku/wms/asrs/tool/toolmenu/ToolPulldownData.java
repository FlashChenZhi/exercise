// $Id: ToolPulldownData.java 7468 2010-03-08 09:06:46Z shibamoto $
package jp.co.daifuku.wms.asrs.tool.toolmenu;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolBankHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolBankSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolHardZoneHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolHardZoneSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolSoftZoneHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolSoftZonePriorityHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolSoftZoneSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWidthHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWidthSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Bank;
import jp.co.daifuku.wms.asrs.tool.location.HardZone;
import jp.co.daifuku.wms.asrs.tool.location.SoftZone;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.wms.asrs.tool.location.Width;

/**<jp>
 * TOOL用プルダウンの表示に必要なデータを取得するためのメソッドを定義するクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/01/18</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7468 $, $Date: 2010-03-08 18:06:46 +0900 (月, 08 3 2010) $
 * @author  $Author: shibamoto $
 </jp>*/
/**<en>
 * This class defines methods used in order to retrieve data reqrueid when displaying 
 * the pull-down for TOOL.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/01/18</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7468 $, $Date: 2010-03-08 18:06:46 +0900 (月, 08 3 2010) $
 * @author  $Author: shibamoto $
 </en>*/
public class ToolPulldownData
        extends Object
{
    // Class fields --------------------------------------------------

    /**<jp>
     * 平置き倉庫を表示するプルダウンを示します。（倉庫種別は平置き倉庫）
     * <code>getWareHousePulldownData</code>メソッドの引数で指定します。
     </jp>*/
    /**<en>
     * Displays the pull-down which indicates the floor staorage warehouses. (warehouse type: floor storage)
     * Specified by the parameter of <code>getWareHousePulldownData</code> method.
     </en>*/
    public static final String WAREHOUSE_FLOOR = "1";

    /**<jp>
     * 自動倉庫を表示するプルダウンを示します。（倉庫種別は自動倉庫）
     * <code>getWareHousePulldownData</code>メソッドの引数で指定します。
     </jp>*/
    /**<en>
     * Displays the pull-down which indicates the automated warehouses. (warehouse type: autoamted warehosue)
     * Specified by the parameter of <code>getWareHousePulldownData</code> method.
     </en>*/
    public static final String WAREHOUSE_AUTO = "2";

    /**<jp>
     * 平置き倉庫、自動倉庫の両方を表示するプルダウンを示します。（倉庫種別は平置き倉庫、自動倉庫）
     * <code>getWareHousePulldownData</code>メソッドの引数で指定します。
     </jp>*/
    /**<en>
     * Displays the pull-down which indicates both floor storage warehouses and automated warehouses.
     * (warehouse type: floor storage, automated )
     * Specified by the parameter of <code>getWareHousePulldownData</code> method.
     </en>*/
    public static final String WAREHOUSE_ALL = "3";

    /**<jp>
     * ソフトゾーン優先順情報に存在する倉庫を表示するプルダウンを示します。
     * <code>getWareHousePulldownData</code>メソッドの引数で指定します。
     </jp>*/
    public static final String WAREHOUSE_SOFTZONEPRIORITY = "4";

    /**<jp>
     * ハードゾーンを表示するプルダウンを示します。（種別はハードゾーン）
     * <code>getZonePulldownData</code>メソッドの引数で指定します。
     </jp>*/
    /**<en>
     * Displays the pull-down which indicates hard zone. (type: hard zone)
     * Specified by the parameter of <code>getZonePulldownData</code> method.
     </en>*/
    public static final String ZONE_HARD = "30";

    /**<jp>
     * ソフトゾーンを表示するプルダウンを示します。（種別はソフトゾーン）
     * <code>getZonePulldownData</code>メソッドの引数で指定します。
     </jp>*/
    /**<en>
     * Displays the pull-down which indicates soft zone. (type: soft zone)
     * Specified by the parameter of <code>getZonePulldownData</code> method.
     </en>*/
    public static final String ZONE_SOFT = "31";

    /**<jp>
     * ハードゾーン、ソフトゾーンを表示するプルダウンを示します。（種別はハードゾーン、ソフトゾーン）
     * <code>getZonePulldownData</code>メソッドの引数で指定します。
     </jp>*/
    /**<en>
     * Displays the pull-down which indicates hard zone and soft zone. (type: hard zone, soft zone)
     * Specified by the parameter of <code>getZonePulldownData</code> method.
     </en>*/
    public static final String ZONE_ALL = "32";

    /**<jp>
     * 平置き倉庫を表示するプルダウンを示します。（倉庫種別は平置き倉庫）
     * <code>getWareHousePulldownData</code>メソッドの引数で指定します。
     </jp>*/
    /**<en>
     * Displays the pull-down which indicates the floor staorage warehouses. (warehouse type: floor storage)
     * Specified by the parameter of <code>getWareHousePulldownData</code> method.
     </en>*/
    public static final String FREE_ALLOCATION_OFF = "0";

    /**<jp>
     * 自動倉庫を表示するプルダウンを示します。（倉庫種別は自動倉庫）
     * <code>getWareHousePulldownData</code>メソッドの引数で指定します。
     </jp>*/
    /**<en>
     * Displays the pull-down which indicates the automated warehouses. (warehouse type: autoamted warehosue)
     * Specified by the parameter of <code>getWareHousePulldownData</code> method.
     </en>*/
    public static final String FREE_ALLOCATION_ON = "1";

    /**<jp>
     * 平置き倉庫、自動倉庫の両方を表示するプルダウンを示します。（倉庫種別は平置き倉庫、自動倉庫）
     * <code>getWareHousePulldownData</code>メソッドの引数で指定します。
     </jp>*/
    /**<en>
     * Displays the pull-down which indicates both floor storage warehouses and automated warehouses.
     * (warehouse type: floor storage, automated )
     * Specified by the parameter of <code>getWareHousePulldownData</code> method.
     </en>*/
    public static final String FREE_ALLOCATION_ALL = "2";

    /**<jp>
     * 「指定なし」を示すステーションナンバー
     * <code>getLinkedNoDummyStationPulldownData</code>メソッドの引数で指定します。
     </jp>*/
    /**<en>
     * Station no. which indicates 'unspecified'.
     * <Specified by the parameter of code>getLinkedNoDummyStationPulldownData</code> method.
     </en>*/
    public static final String NOSTATION = "888";


    // Class variables -----------------------------------------------

    /**<jp>
     * <CODE>Locale</CODE>オブジェクト
     </jp>*/
    /**<en>
     * <CODE>Locale</CODE> object
     </en>*/
    protected Locale wLocale = null;

    /**<jp>
     * <CODE>Connection</CODE>オブジェクト
     </jp>*/
    /**<en>
     * <CODE>Connection</CODE> object
     </en>*/
    protected Connection wConn = null;

    /**<jp>
     * 製番毎にテーブル名が違う時の対応用の<CODE>Hashtable</CODE>
     * キーとしてeAWCのテーブル名、値に製番用のテーブル名をセットします。
     </jp>*/
    /**<en>
     * Just in case the different table names are used in each projection,
     * set the eAWC table name as <CODE>Hashtable</CODE> key and set the table
     * name for corresponding projection as a value.
     </en>*/
    protected Hashtable wTableNames = null;

    /**<jp>
     * デリミタ
     </jp>*/
    /**<en>
     * Delimtier
     </en>*/
    protected String wDelim = MessageResource.DELIM;

    /**<jp>
     * 初期表示フラグ（初期表示する項目を表す）
     </jp>*/
    /**<en>
     * Initial indication flag (indicates items for initial display)
     </en>*/
    protected final String FIRSTDISP_TRUE = "1";

    /**<jp>
     * 初期表示フラグ（初期表示しない項目を表す）
     </jp>*/
    /**<en>
     * Initial indication flag (indicate items no to shown in initial display)
     </en>*/
    protected final String FIRSTDISP_FALSE = "0";


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
        return ("$Revision: 7468 $,$Date: 2010-03-08 18:06:46 +0900 (月, 08 3 2010) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * このコンストラクタが呼ばれたとき、ロケールと端末No.をインスタンス変数に
     * 保持し、プルダウンデータ保持用HashTableを初期化します。
     * @param conn <CODE>Connection</CODE>
     * @param locale <CODE>Locale</CODE>
     * @param tablenames 製番毎のテーブル名を保持した<code>Hashtable</code>
     </jp>*/
    /**<en>
     * When this constructor is called, preserve the locale and terminal no. as 
     * instance variable and initialize hte HashTable for preservation of pull-down data.
     * @param conn <CODE>Connection</CODE>
     * @param locale <CODE>Locale</CODE>
     * @param tablenames :<code>Hashtable</code> which preserve the table name per product no.
     </en>*/
    public ToolPulldownData(Connection conn, Locale locale, Hashtable tablenames)
    {
        wLocale = locale;
        wConn = conn;
        wTableNames = tablenames;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 格納区分プルダウンを表示するためのデータを<code>String</code>の配列にて返します。
     * 引数には初期表示の格納区分を与えます。
     * <BR>
     * ・平置き倉庫のみ表示（WAREHOUSE_FLOORを指定）<BR>
     * ・自動倉庫のみ表示（WAREHOUSE_AUTOを指定）<BR>
     * ・自動倉庫、平置き倉庫の両方を表示（WAREHOUSE_ALLを指定）<BR>
     * <BR>
     * Value+","+Name+","+ 親のVALUE +","+初期表示フラグ（"0" or "1"）というデータをArrayListに追加します。
     * 初期表示に"1"をセットしたデータがプルダウンの表示時に初期表示されます。
     * 
     * @param tablename テーブル名の一覧を持った<CODE>Hashtable</CODE>オブジェクト。
     * @param type プルダウンの種別を指定します。
     * @param selected  初期表示を行う倉庫ステーションナンバー。指定しない場合は""を入力してください。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException 定義情報に異常がある場合に通知されます。
     </jp>*/
    /**<en>
     * Return data, for the display of storage pull-down, in the array of <code>String</code>.
     * The storage type in initial display will be given for parameter.
     * <BR>
     *  -only display the floor storage warehouse. (specify WAREHOUSE_FLOOR.)<BR>
     *  -only display the automated warehouse. (specifiy WAREHOUSE_AUTO.)<BR>
     *  -only display both floor storage warehouse and automated warehouse. (specify WAREHOUSE_ALL).<BR>
     * <BR>
     * Add the data :"Value+","+Name+","+ parent VALUE +","+ initial display flag (0 or 1) to the ArrayList.
     * Those data set '1' at initial display will be initially indicated when displaying pull-down.
     * 
     * @param type      :specifies the pull-down type.
     * @param selected  :warehouse station no. which carries out the initial display. Please enter ""
     * if specifing no data. 
     * @return :return in the array of <code>String</code> which is needed to show pull-down image.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws InvalidDefineException :Notifies if error is found in definition of information.
     </en>*/
    public String[] getWarehousePulldownData(String type, String selected)
            throws ReadWriteException,
                InvalidDefineException
    {
        return getWarehousePulldownData(type, selected, ZONE_ALL, FREE_ALLOCATION_ALL);
    }

    /**<jp>
     * 格納区分プルダウンを表示するためのデータを<code>String</code>の配列にて返します。
     * 引数には初期表示の格納区分を与えます。
     * <BR>
     * ・平置き倉庫のみ表示（WAREHOUSE_FLOORを指定）<BR>
     * ・自動倉庫のみ表示（WAREHOUSE_AUTOを指定）<BR>
     * ・自動倉庫、平置き倉庫の両方を表示（WAREHOUSE_ALLを指定）<BR>
     * <BR>
     * Value+","+Name+","+ 親のVALUE +","+初期表示フラグ（"0" or "1"）というデータをArrayListに追加します。
     * 初期表示に"1"をセットしたデータがプルダウンの表示時に初期表示されます。
     * 
     * @param tablename テーブル名の一覧を持った<CODE>Hashtable</CODE>オブジェクト。
     * @param type プルダウンの種別を指定します。
     * @param selected  初期表示を行う倉庫ステーションナンバー。指定しない場合は""を入力してください。
     * @param zonetype  ゾーン種別を指定します。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException 定義情報に異常がある場合に通知されます。
     </jp>*/
    /**<en>
     * Return data, for the display of storage pull-down, in the array of <code>String</code>.
     * The storage type in initial display will be given for parameter.
     * <BR>
     *  -only display the floor storage warehouse. (specify WAREHOUSE_FLOOR.)<BR>
     *  -only display the automated warehouse. (specifiy WAREHOUSE_AUTO.)<BR>
     *  -only display both floor storage warehouse and automated warehouse. (specify WAREHOUSE_ALL).<BR>
     * <BR>
     * Add the data :"Value+","+Name+","+ parent VALUE +","+ initial display flag (0 or 1) to the ArrayList.
     * Those data set '1' at initial display will be initially indicated when displaying pull-down.
     * 
     * @param type      :specifies the pull-down type.
     * @param selected  :warehouse station no. which carries out the initial display. Please enter ""
     * if specifing no data. 
     * @param zonetype set up the zone type.
     * @return :return in the array of <code>String</code> which is needed to show pull-down image.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws InvalidDefineException :Notifies if error is found in definition of information.
     </en>*/
    public String[] getWarehousePulldownData(String type, String selected, String zonetype)
            throws ReadWriteException,
                InvalidDefineException
    {
        return getWarehousePulldownData(type, selected, zonetype, FREE_ALLOCATION_ALL);
    }

    /**<jp>
     * 格納区分プルダウンを表示するためのデータを<code>String</code>の配列にて返します。
     * 引数には初期表示の格納区分を与えます。
     * <BR>
     * ・平置き倉庫のみ表示（WAREHOUSE_FLOORを指定）<BR>
     * ・自動倉庫のみ表示（WAREHOUSE_AUTOを指定）<BR>
     * ・自動倉庫、平置き倉庫の両方を表示（WAREHOUSE_ALLを指定）<BR>
     * <BR>
     * Value+","+Name+","+ 親のVALUE +","+初期表示フラグ（"0" or "1"）というデータをArrayListに追加します。
     * 初期表示に"1"をセットしたデータがプルダウンの表示時に初期表示されます。
     * 
     * @param tablename テーブル名の一覧を持った<CODE>Hashtable</CODE>オブジェクト。
     * @param type      プルダウンの種別を指定します。
     * @param selected  初期表示を行う倉庫ステーションナンバー。指定しない場合は""を入力してください。
     * @param zonetype  ゾーン種別を指定します。
     * @param freealloctype フリーアロケーション運用を指定します。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException 定義情報に異常がある場合に通知されます。
     </jp>*/
    /**<en>
     * Return the data in <code>String</code> array to show the storage type for pull-down.
     * The storage type in initial display will be given for parameter.
     * <BR>
     *  -only display the floor storage warehouse. (specify WAREHOUSE_FLOOR.)<BR>
     *  -only display the automated warehouse. (specifiy WAREHOUSE_AUTO.)<BR>
     *  -only display both floor storage warehouse and automated warehouse. (specify WAREHOUSE_ALL).
     * <BR>
     * Add the data :"Value+","+Name+","+ parent VALUE +","+ initial display flag (0 or 1) to the ArrayList.
     * Those data set '1' at initial display will be initially indicated when displaying pull-down
     * 
     * @param type  : specify the pull-down typr:
     * @param selected :warehoudse station no. to initially indicates. Please enter "" if there is no specification.
     * @param zonetype set up the zone type.
     * @param freealloctype set up the free allocation type.
     * @return :return the data requried for pull-down images i nofrm of <code>String</code> array.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws InvalidDefineException :Notifies if error is found in definition of information.
     </en>*/
    public String[] getWarehousePulldownData(String type, String selected, String zonetype, String freealloctype)
            throws ReadWriteException,
                InvalidDefineException
    {
        ArrayList pulldownData = new ArrayList();
        //<jp>nullの場合は""をセットする。</jp>
        //<en>Set "" in case of null.</en>
        if (selected == null)
        {
            selected = "";
        }
        //<jp>指定された種別のステーションNo配列</jp>
        //<en>The array of the Station no. of specified type.</en>
        Warehouse[] warehouseArray = null;

        //<jp>平置き倉庫のプルダウンデータ</jp>
        //<en>Pull down data for floor storage.</en>
        if (type.equals(WAREHOUSE_FLOOR))
        {
            warehouseArray = getWarehouseArray(wConn, WAREHOUSE_FLOOR, zonetype, freealloctype);
        }
        //<jp>自動倉庫のプルダウンデータ</jp>
        //<en>Pull down data for automated warehouse</en>
        else if (type.equals(WAREHOUSE_AUTO))
        {
            warehouseArray = getWarehouseArray(wConn, WAREHOUSE_AUTO, zonetype, freealloctype);
        }
        //<jp>自動倉庫、平置き倉庫のプルダウンデータ</jp>
        //<en>Pull down data for autoamted warehouse and floor storage warehouse</en>
        else if (type.equals(WAREHOUSE_ALL))
        {
            warehouseArray = getWarehouseArray(wConn, WAREHOUSE_ALL, zonetype, freealloctype);
        }
        //<jp>ソフトゾーン情報のプルダウンデータ</jp>
        else if (type.equals(WAREHOUSE_SOFTZONEPRIORITY))
        {
            warehouseArray = getWarehouseInSoftZonePriorityArray(wConn);
        }
        else
        {
            throw new InvalidDefineException("Argument (Type) is wrong. ");
        }

        //<jp>**** 倉庫をArrayListに追加する ****</jp>
        //<en>**** Add wareahouses to the ArrayList. ****</en>
        for (int i = 0; i < warehouseArray.length; i++)
        {
            String warehouseNo = Integer.toString(warehouseArray[i].getWarehouseNo());
            //<jp>プルダウンに表示する名称は倉庫番号：倉庫名称</jp>
            //<en>Names to show in pull-down  warehouse no.:warehouse name</en>
            String warehouseName = warehouseNo + ":" + warehouseArray[i].getWarehouseName();
            //<jp>初期表示するデータ</jp>
            //<en>Data to show at initial display</en>
            if (warehouseNo.equals(selected))
            {
                pulldownData.add(warehouseNo + "," + warehouseName + "," + warehouseNo + "," + FIRSTDISP_TRUE);
            }
            //<jp>それ以外のデータ</jp>
            //<en>other data</en>
            else
            {
                pulldownData.add(warehouseNo + "," + warehouseName + "," + warehouseNo + "," + FIRSTDISP_FALSE);
            }
        }

        String[] str = new String[pulldownData.size()];
        pulldownData.toArray(str);
        return str;
    }

    /**<jp>
     * 倉庫ステーションのプルダウンを表示するためのデータを<code>String</code>の配列にて返します。
     * 引数には初期表示の倉庫ステーションを与えます。
     * プルダウンを作成するためのデータはすべてWAREHOUSE表より取得します。
     * 出力されるArrayListには<BR>
     * Value+","+Name+","+ 親のVALUE +","+初期表示フラグ（"0" or "1"）というデータをArrayListに追加します。<BR>
     * 出力されるデータは以下の様になります<BR>
     *        "1","1","","1"<BR>
     *        "2","2","","0"<BR>
     * 初期表示に"1"をセットしたデータがプルダウンの表示時に初期表示されます。
     * @param selected 初期表示したい倉庫ステーションNo。指定しない場合は""をセットします。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Return the data to show the pull-down of warehouse stations in the array of <code>String</code>.
     * Give warehouse station for initial display for parameter.
     * Retrieve all data recuired to create pull-down from the WAREHOUSE table.
     * For the ArrayList which is outputting, <BR>
     * the data: Value+","+Name+","+ parent VALUE +","+ initial display flag ("0" or "1") should be added to 
     * the ArrayList.<BR>
     * Outputting data will be as follows.<BR>
     *        "1","1","","1"<BR>
     *        "2","2","","0"<BR>
     * The data that "1" was set will be initially displayed at the pull-down indication.
     * @param selected :Set the warehouse station wanted to for display, or set "" if no data is specified.
     * @return :return in the array of <code>String</code> the required data for pull-down image.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public String[] getWarehouseStaionPulldownData(String selected)
            throws ReadWriteException
    {

        ArrayList pulldownData = new ArrayList(5);
        //<jp>nullの場合は""をセットする。</jp>
        //<en>Set "" in case of null.</en>
        if (selected == null)
        {
            selected = "";
        }

        //<jp>倉庫ステーションの配列の取得</jp>
        //<en>Retrieve the array of warehouse station.</en>
        Warehouse[] warehouseArray = getWarehouseArray(wConn);


        //<jp>**** 倉庫ステーションをArrayListに追加する ****</jp>
        //<en>**** Add the warehouse station to the ArrayList. ****</en>
        for (int i = 0; i < warehouseArray.length; i++)
        {
            //<jp>倉庫ステーションNo.</jp>
            //<en>warehouse station no.</en>
            String warehousestationNo = warehouseArray[i].getWarehouseStationNo();
            //<jp>プルダウンに表示する名称</jp>
            //<en>name to show in pull-down menu</en>
            String warehouseName = warehousestationNo + ":" + warehouseArray[i].getWarehouseName();

            //<jp>初期表示するデータ</jp>
            //<en>data to show as initial display</en>
            if (warehousestationNo.equals(selected))
            {
                pulldownData.add(warehousestationNo + "," + warehouseName + "," + warehousestationNo + ","
                        + FIRSTDISP_TRUE);
            }
            //<jp>それ以外のデータ</jp>
            //<en>other data</en>
            else
            {
                pulldownData.add(warehousestationNo + "," + warehouseName + "," + warehousestationNo + ","
                        + FIRSTDISP_FALSE);
            }
        }

        String[] str = new String[pulldownData.size()];
        pulldownData.toArray(str);
        return str;
    }


    /**<jp>
     * ステーションのプルダウンを表示するためのデータを<code>String</code>の配列にて返します。
     * プルダウンを作成するためのデータはすべてSTATION表より取得します。
     * @param worktype 0：すべてのステーション、１：作業場を含まない、２：作業場、代表ステーションを含まない
     * @param selected 初期表示したいステーションNo。指定しない場合は""をセットします。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Return data, for displayinh pull dowm of stations, in form of <code>String</code> array.
     * @param worktype 0:excluding workshop, 2: excluding workshop and main station
     * @param selected :station no. for initial display. Or set "" if nothing is specified.
     * @return :return the required data to create image of pull-down, in form of <code>String</code> array.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public String[] getStationPulldownData(int worktype, String selected)
            throws ReadWriteException
    {
        //<jp>nullの場合は""をセットする。</jp>
        //<en>Set "" in case of null.</en>
        if (selected == null)
        {
            selected = "";
        }

        ArrayList pulldownData = new ArrayList();

        //<jp>指定された送信区分のステーション配列</jp>
        //<en>Station arrya of specified sendability division.</en>
        Station[] stationArray = getStationArray(wConn, worktype);

        for (int i = 0; i < stationArray.length; i++)
        {
            String stationNo = stationArray[i].getStationNo();
            //<jp>プルダウンに表示する名称（STATION表に定義されている名称を表示する）</jp>
            //<jp>「ステーションナンバー：名称」</jp>
            //<en>Name to inidicate in pull-down (display names defined in STATION table.)</en>
            //<en> "station no. : name"</en>
            String stationName = stationNo + ":" + stationArray[i].getStationName();

            //<jp>初期表示するデータ</jp>
            //<en>data for initial display</en>
            if (stationNo.equals(selected))
            {
                pulldownData.add(stationNo + "," + stationName + "," + " " + "," + FIRSTDISP_TRUE);
            }
            //<jp>それ以外のデータ</jp>
            //<en>other data</en>
            else
            {
                pulldownData.add(stationNo + "," + stationName + "," + " " + "," + FIRSTDISP_FALSE);
            }
        }
        String[] str = new String[pulldownData.size()];
        pulldownData.toArray(str);

        return str;
    }

    /**<jp>
     * アイルのプルダウンを表示するためのデータを<code>String</code>の配列にて返します。
     * プルダウンを作成するためのデータはすべてAISLE表より取得します。
     * @param selected 初期表示したいアイルステーションNo。指定しない場合は""をセットします。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Arrange the data, which is to display in pull-down menu for aisles, in the array of 
     * <code>String</code>, then return.
     * All the data neede to create pull-down lists will be retrieved from AISLE table.
     * @param selected :aisle no. to show in initial display. Set "" if there is no specification.
     * @return :return data in <code>String</code> array which is required to create the image of pull-down menu.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public String[] getAislePulldownData(String selected)
            throws ReadWriteException
    {
        //<jp>nullの場合は""をセットする。</jp>
        //<en>Set "" in case of null.</en>
        if (selected == null)
        {
            selected = "";
        }

        ArrayList pulldownData = new ArrayList();

        //<jp>指定された送信区分のステーション配列</jp>
        //<en>Station array of specified sendability status</en>
        Station[] aisleArray = getAisleArray(wConn);

        for (int i = 0; i < aisleArray.length; i++)
        {
            String stationNo = aisleArray[i].getStationNo();
            String stationName = stationNo + ":" + "RM";

            //<jp>初期表示するデータ</jp>
            //<en>data for initial display</en>
            if (stationNo.equals(selected))
            {
                pulldownData.add(stationNo + "," + stationName + "," + " " + "," + FIRSTDISP_TRUE);
            }
            //<jp>それ以外のデータ</jp>
            //<en>other data</en>
            else
            {
                pulldownData.add(stationNo + "," + stationName + "," + " " + "," + FIRSTDISP_FALSE);
            }
        }
        String[] str = new String[pulldownData.size()];
        pulldownData.toArray(str);

        return str;
    }

    /**<jp>
     * ステーション+アイルのプルダウンを表示するためのデータを<code>String</code>の配列にて返します。
     * すべてのステーションを返します。
     * プルダウンを作成するためのデータはSTATION、AISLE表より取得します。
     * @param selected 初期表示したいステーションNo。指定しない場合は""をセットします。
     * @param type 0：すべてのステーション、１：作業場を含まない、２：作業場、代表ステーションを含まない
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Arrange the data, which is to display in pull-down menu for station + aisles, in the array of 
     * <code>String</code>, then return.
     * The data to create pull-down lists will be retrieved from STATION table and AISLE table.
     * @param selected :station no. to show in initial display. Set "" if there is no specification.
     * @param type - 0:all stations, 1: workshop excluded, 2: workshop and main station excluded
     * @return :return data in <code>String</code> array which is required to create the image of pull-down menu.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public String[] getAllStationPulldownData(String selected, int type)
            throws ReadWriteException
    {

        String[] stations = getStationPulldownData(type, selected);
        String[] aisles = getAislePulldownData(selected);

        String[] allstarray = new String[stations.length + aisles.length];
        System.arraycopy(stations, 0, allstarray, 0, stations.length);
        System.arraycopy(aisles, 0, allstarray, stations.length, aisles.length);

        return allstarray;
    }

    /**<jp>
     * グループコントローラのプルダウンを表示するためのデータを<code>String</code>の配列にて返します。
     * 引数には初期表示のグループコントローラを与えます。
     * プルダウンを作成するためのデータはすべてGROUPCONTROLLER表より取得します。
     * 出力されるArrayListには<BR>
     * Value+","+Name+","+ 親のVALUE +","+初期表示フラグ（"0" or "1"）というデータをArrayListに追加します。<BR>
     * 出力されるデータは以下の様になります<BR>
     *        "1","1","","1"<BR>
     *        "2","2","","0"<BR>
     * 初期表示に"1"をセットしたデータがプルダウンの表示時に初期表示されます。
     * @param selected  初期表示を行うグループコントローラのControllerNumberを指定します。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Arrange the data, which is to display in pull-down menu for group controller, in the array of 
     * <code>String</code>, then return.
     * Provide the parameter with the group controller for initial display.
     * All the data neede to create pull-down lists will be retrieved from GROUPCONTROLLER table.
     * To the ArrayList which will be output, add data created as follows.<BR>
     * Value+","+Name+","+ parent VALUE +","+initial display flag ("0" or "1")<BR>
     * Data to output will be as follows:<BR>
     *        "1","1","","1"<BR>
     *        "2","2","","0"<BR>
     * The data which is set with "1" in initial display will be shown when displaying the initial pull-down list.
     * @param selected :specifies the ControllerNumber of the group controller to show in initial display.
     * @return :return data in <code>String</code> array which is required to create the image of pull-down menu.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public String[] getGroupControllerPulldownData(String selected)
            throws ReadWriteException
    {

        ArrayList pulldownData = new ArrayList(5);
        //<jp>nullの場合は""をセットする。</jp>
        //<en>Set "" in case of null.</en>
        if (selected == null)
        {
            selected = "";
        }

        //<jp>グループコントローラの配列の取得</jp>
        //<en>Retrieve the array of the group controller.</en>
        GroupController[] gcArray = getGroupControllerArray(wConn);


        //<jp>**** グループコントローラをArrayListに追加する ****</jp>
        //<en>**** Add the group controller to the ArrayList. ****</en>
        for (int i = 0; i < gcArray.length; i++)
        {
            //Controller No.
            String controllerNo = Integer.toString(gcArray[i].getNumber());
            //<jp>プルダウンに表示する名称</jp>
            //<en>Names to show in pull-down list.</en>
            String gcName = controllerNo;

            //<jp>初期表示するデータ</jp>
            //<en>Data to show in initial display</en>
            if (controllerNo.equals(selected))
            {
                pulldownData.add(controllerNo + "," + gcName + "," + " " + "," + FIRSTDISP_TRUE);
            }
            //<jp>それ以外のデータ</jp>
            //<en>other data</en>
            else
            {
                pulldownData.add(controllerNo + "," + gcName + "," + " " + "," + FIRSTDISP_FALSE);
            }
        }

        String[] str = new String[pulldownData.size()];
        pulldownData.toArray(str);
        return str;
    }

    /**<jp>
     * 機種コードのプルダウンを表示するためのデータを<code>String</code>の配列にて返します。
     * 引数には初期表示の機種コードを与えます。
     * プルダウンを作成するためのデータはすべて固定で定義しています。
     * 出力されるArrayListには<BR>
     * Value+","+Name+","+ 親のVALUE +","+初期表示フラグ（"0" or "1"）というデータをArrayListに追加します。<BR>
     * 出力されるデータは以下の様になります<BR>
     *        "1","1","","1"<BR>
     *        "2","2","","0"<BR>
     * 初期表示に"1"をセットしたデータがプルダウンの表示時に初期表示されます。
     * @param selected  初期表示を行う機種コードを指定します。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     </jp>*/
    /**<en>
     * Arrange the data, which is to display in pull-down menu for machine code, in the array of 
     * <code>String</code>, then return.
     * All data used to create the pull-down lists are defined fixed.
     * To the ArrayList which will be output, add data created as follows.<BR>
     * Value+","+Name+","+ parent VALUE +","+ initial display flag("0" or "1")<BR>
     * Data to output will be as follows:<BR>
     *        "1","1","","1"<BR>
     *        "2","2","","0"<BR>
     * The data which is set with "1" in initial display will be shown when displaying the initial pull-down list.
     * @param selected :specifies the model code to show in initial display.
     * @return :return data in <code>String</code> array which is required to create the image of pull-down menu.
     </en>*/
    public String[] getMachineTypePulldownData(String selected)
    {

        ArrayList pulldownData = new ArrayList(5);
        //<jp>nullの場合は""をセットする。</jp>
        //<en>Set "" in case of null.</en>
        if (selected == null)
        {
            selected = "";
        }

        ToolFindUtil futil = new ToolFindUtil(wConn);

        String[] type = new String[18];

        type[0] = "11";
        type[1] = "15";
        type[2] = "16";
        type[3] = "17";
        type[4] = "18";
        type[5] = "21";
        type[6] = "22";
        type[7] = "28";
        type[8] = "31";
        type[9] = "35";
        type[10] = "44";
        type[11] = "45";
        type[12] = "54";
        type[13] = "55";
        type[14] = "61";
        type[15] = "64";
        type[16] = "65";
        type[17] = "66";

        for (int i = 0; i < type.length; i++)
        {
            //<jp>初期表示するデータ</jp>
            //<en>Data to show in initial display</en>
            if (type[i].equals(selected))
            {
                pulldownData.add(type[i] + "," + futil.getMachineTypeName(Integer.parseInt(type[i])) + "," + " " + ","
                        + FIRSTDISP_TRUE);
            }
            else
            {
                pulldownData.add(type[i] + "," + futil.getMachineTypeName(Integer.parseInt(type[i])) + "," + " " + ","
                        + FIRSTDISP_FALSE);
            }
        }

        String[] str = new String[pulldownData.size()];
        pulldownData.toArray(str);
        return str;
    }


    /**<jp>
     * 端末ナンバーの指定無しでゾーン名称プルダウンを表示するためのデータを<code>String</code>の配列にて返します。
     * 引数には<code>Connection</code>、プルダウンの種別（以下に示す種類のみ可能）、初期表示のZoneIDを与えます。
     * このメソッドでは倉庫を意識せずにHARDZONEに定義されている指定種別のゾーンを表示します。
     * ゾーンのプルダウンは毎回DBを検索します。プルダウンデータをHashTableに保持しません。
     * Value+","+Name+","+ 親のVALUE +","+初期表示フラグ（"0" or "1"）というデータをArrayListに追加します。
     * 親のVALUEにはZONEIDをそのままセットしています。
     * 初期表示に"1"をセットしたデータがプルダウンの表示時に初期表示されます。
     * @param selected  初期表示を行うゾーンID。指定しない場合は""を入力してください。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Arrange the data, which is to display in pull-down menu for zone name with no specific terminal no.,
     * in the array of <code>String</code>, then return.
     * Provide the parameter with <code>Connection</code>, pull-down type (only those shown below) and zone ID
     * for initial display.
     * In this method, it displays zone of specified type accrding to the HARDZONE definition with no regard of warehouse.
     * Pull-down of Zone requires DB search every time. It does not preserve the pull-down data in HashTable.
     * Data will be created as follows and added to ArrayLsit.
     * Value+","+Name+","+ parent VALUE +","+initial display flag ("0" or "1")
     * Set the ZONEID as it is for parent VALUE.
     * The data which is set with "1" in initial display will be shown when displaying the initial pull-down list.
     * @param selected :zone ID for initial display. Please set "" if there is no specification.
     * @return :return data in <code>String</code> array which is required to create the image of pull-down menu.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public String[] getHardZonePulldownData(String selected)
            throws ReadWriteException
    {
        //<jp>*** 初期処理 ***</jp>
        //<en>*** Initial processing***</en>
        //<jp>倉庫の配列を取得</jp>
        //<en>Retrieve the array of the warheouse.</en>
        //                Warehouse[] wWarehouseArray = getWareHouseArray(wConn, WAREHOUSE_ALL, ZONE_ALL);

        //<jp>nullの場合は""をセットする。</jp>
        //<en>Set "" in case of null.</en>
        if (selected == null)
        {
            selected = "";
        }

        ArrayList pulldownData = new ArrayList();
        //<jp>指定された種別のステーションNo配列</jp>
        //<en>array of station no. of specified type</en>
        HardZone[] zoneArray = null;

        zoneArray = getHardZoneArray(wConn);

        //<jp>**** ステーションをArrayListに追加する ****</jp>
        //<en>**** Add the station to the ArrayList. ****</en>
        for (int i = 0; i < zoneArray.length; i++)
        {
            int zoneID = zoneArray[i].getHardZoneID();
            //<jp>プルダウンに表示する名称は ハードゾーンID：ハードゾーン名称</jp>
            //<en>Names to show in pull-down list - hard zone ID: hard name</en>
            String zoneName = zoneID + ":" + zoneArray[i].getHardZoneName();

            //<jp>初期表示するデータ</jp>
            //<en>data for initial display</en>
            if (Integer.toString(zoneID).equals(selected))
            {
                pulldownData.add(zoneID + "," + zoneName + "," + zoneID + "," + FIRSTDISP_TRUE);
            }
            //<jp>それ以外のデータ</jp>
            //<en>other data</en>
            else
            {
                pulldownData.add(zoneID + "," + zoneName + "," + zoneID + "," + FIRSTDISP_FALSE);
            }
        }
        String[] str = new String[pulldownData.size()];
        pulldownData.toArray(str);
        return str;
    }

    /**<jp>
     * 端末ナンバーの指定無しでゾーン名称プルダウンを表示するためのデータを<code>String</code>の配列にて返します。
     * 引数には<code>Connection</code>、プルダウンの種別（以下に示す種類のみ可能）、初期表示のZoneIDを与えます。
     * このメソッドでは倉庫を意識せずにHARDZONEに定義されている指定種別のゾーンを表示します。
     * ゾーンのプルダウンは毎回DBを検索します。プルダウンデータをHashTableに保持しません。
     * Value+","+Name+","+ 親のVALUE +","+初期表示フラグ（"0" or "1"）というデータをArrayListに追加します。
     * 親のVALUEにはZONEIDをそのままセットしています。
     * 初期表示に"1"をセットしたデータがプルダウンの表示時に初期表示されます。
     * @param selected  初期表示を行うゾーンID。指定しない場合は""を入力してください。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Arrange the data, which is to display in pull-down menu for zone name with no specific terminal no.,
     * in the array of <code>String</code>, then return.
     * Provide the parameter with <code>Connection</code>, pull-down type (only those shown below) and zone ID
     * for initial display.
     * In this method, it displays zone of specified type accrding to the HARDZONE definition with no regard of warehouse.
     * Pull-down of Zone requires DB search every time. It does not preserve the pull-down data in HashTable.
     * Data will be created as follows and added to ArrayLsit.
     * Value+","+Name+","+ parent VALUE +","+initial display flag ("0" or "1")
     * Set the ZONEID as it is for parent VALUE.
     * The data which is set with "1" in initial display will be shown when displaying the initial pull-down list.
     * @param selected :zone ID for initial display. Please set "" if there is no specification.
     * @return :return data in <code>String</code> array which is required to create the image of pull-down menu.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public String[] getSoftZonePulldownData(String selected)
            throws ReadWriteException
    {
        //<jp>*** 初期処理 ***</jp>
        //<en>*** Initial processing***</en>
        //<jp>倉庫の配列を取得</jp>
        //<en>Retrieve the array of the warheouse.</en>
        //                Warehouse[] wWarehouseArray = getWareHouseArray(wConn, WAREHOUSE_ALL, ZONE_ALL);

        //<jp>nullの場合は""をセットする。</jp>
        //<en>Set "" in case of null.</en>
        if (selected == null)
        {
            selected = "";
        }

        ArrayList pulldownData = new ArrayList();
        //<jp>指定された種別のソフトゾーン配列</jp>
        //<en>array of station no. of specified type</en>
        SoftZone[] zoneArray = null;

        zoneArray = getSoftZoneArray(wConn);

        //<jp>****ソフトゾーンをArrayListに追加する ****</jp>
        //<en>**** Add the soft zone to the ArrayList. ****</en>
        for (int i = 0; i < zoneArray.length; i++)
        {
            int zoneID = zoneArray[i].getSoftZoneID();
            //<jp>プルダウンに表示する名称は ハードゾーンID：ハードゾーン名称</jp>
            //<en>Names to show in pull-down list - hard zone ID: hard name</en>
            String zoneName = zoneID + ":" + zoneArray[i].getSoftZoneName();

            //<jp>初期表示するデータ</jp>
            //<en>data for initial display</en>
            if (Integer.toString(zoneID).equals(selected))
            {
                pulldownData.add(zoneID + "," + zoneName + "," + zoneID + "," + FIRSTDISP_TRUE);
            }
            //<jp>それ以外のデータ</jp>
            //<en>other data</en>
            else
            {
                pulldownData.add(zoneID + "," + zoneName + "," + zoneID + "," + FIRSTDISP_FALSE);
            }
        }
        String[] str = new String[pulldownData.size()];
        pulldownData.toArray(str);
        return str;
    }

    /**<jp>
     * 端末ナンバーの指定無しでゾーン名称プルダウンを表示するためのデータを<code>String</code>の配列にて返します。
     * 引数には<code>Connection</code>、プルダウンの種別（以下に示す種類のみ可能）、初期表示のZoneIDを与えます。
     * このメソッドでは倉庫を意識せずにSOFTZONEに定義されている指定種別のゾーンを表示します。
     * ゾーンのプルダウンは毎回DBを検索します。プルダウンデータをHashTableに保持しません。
     * Value+","+Name+","+ 親のVALUE +","+初期表示フラグ（"0" or "1"）というデータをArrayListに追加します。
     * 親のVALUEにはZONEIDをそのままセットしています。
     * 初期表示に"1"をセットしたデータがプルダウンの表示時に初期表示されます。
     * @param selected  初期表示を行うゾーンID。指定しない場合は""を入力してください。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Arrange the data, which is to display in pull-down menu for zone name with no specific terminal no.,
     * in the array of <code>String</code>, then return.
     * Provide the parameter with <code>Connection</code>, pull-down type (only those shown below) and zone ID
     * for initial display.
     * In this method, it displays zone of specified type accrding to the SOFTZONE definition with no regard of warehouse.
     * Pull-down of Zone requires DB search every time. It does not preserve the pull-down data in HashTable.
     * Data will be created as follows and added to ArrayLsit.
     * Value+","+Name+","+ parent VALUE +","+initial display flag ("0" or "1")
     * Set the ZONEID as it is for parent VALUE.
     * The data which is set with "1" in initial display will be shown when displaying the initial pull-down list.
     * @param selected :zone ID for initial display. Please set "" if there is no specification.
     * @return :return data in <code>String</code> array which is required to create the image of pull-down menu.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public String[] getwhSoftZonePulldownData(String selected)
            throws ReadWriteException
    {
        //<jp>*** 初期処理 ***</jp>
        //<en>*** Initial processing***</en>

        //<jp>nullの場合は""をセットする。</jp>
        //<en>Set "" in case of null.</en>
        if (selected == null)
        {
            selected = "";
        }

        ArrayList pulldownData = new ArrayList();
        //<jp>指定された種別のステーションNo配列</jp>
        //<en>array of station no. of specified type</en>
        SoftZone[] zoneArray = null;

        ToolSoftZoneHandler zoneHandle = new ToolSoftZoneHandler(wConn);
        zoneArray = (SoftZone[])zoneHandle.findZoneJoinSofftZonePriority();

        //<jp>**** ステーションをArrayListに追加する ****</jp>
        //<en>**** Add the station to the ArrayList. ****</en>
        for (int i = 0; i < zoneArray.length; i++)
        {
            int zoneID = zoneArray[i].getSoftZoneID();
            //<jp>プルダウンに表示する名称は ソフトゾーンID：ソフトゾーン名称</jp>
            //<en>Names to show in pull-down list - soft zone ID: soft name</en>
            String zoneName = zoneID + ":" + zoneArray[i].getSoftZoneName();

            //<jp>倉庫ステーションNo</jp>
            //<en>warehouse station no.</en>
            String warehouseNo = null;
            ToolWarehouseHandler warehousehandle = new ToolWarehouseHandler(wConn);
            ToolWarehouseSearchKey warehousekey = new ToolWarehouseSearchKey();
            warehousekey.setWarehouseStationNo(zoneArray[i].getWhStationNo());
            Warehouse[] warehouseArray = (Warehouse[])warehousehandle.find(warehousekey);

            if (warehouseArray.length <= 0)
            {
                continue;
            }

            warehouseNo = "" + warehouseArray[0].getWarehouseNo();

            //<jp>初期表示するデータ</jp>
            //<en>data for initial display</en>
            if (Integer.toString(zoneID).equals(selected))
            {
                pulldownData.add(zoneID + "," + zoneName + "," + warehouseNo + "," + FIRSTDISP_TRUE);
            }
            //<jp>それ以外のデータ</jp>
            //<en>other data</en>
            else
            {
                pulldownData.add(zoneID + "," + zoneName + "," + warehouseNo + "," + FIRSTDISP_FALSE);
            }
        }
        String[] str = new String[pulldownData.size()];
        pulldownData.toArray(str);
        return str;
    }

    /**<jp>
     * 端末ナンバーの指定無しで荷幅プルダウンを表示するためのデータを<code>String</code>の配列にて返します。
     * 引数には<code>Connection</code>、プルダウンの種別（以下に示す種類のみ可能）、初期表示のWidthを与えます。
     * このメソッドでは倉庫を意識せずにWIDTHに定義されている指定種別のゾーンを表示します。
     * ゾーンのプルダウンは毎回DBを検索します。プルダウンデータをHashTableに保持しません。
     * Value+","+Name+","+ 親のVALUE +","+初期表示フラグ（"0" or "1"）というデータをArrayListに追加します。
     * 親のVALUEにはWIDTHをそのままセットしています。
     * 初期表示に"1"をセットしたデータがプルダウンの表示時に初期表示されます。
     * @param selected  初期表示を行う荷幅。指定しない場合は""を入力してください。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    public String[] getwhWidthPulldownData(String selected)
            throws ReadWriteException
    {
        //<jp>*** 初期処理 ***</jp>
        //<en>*** Initial processing***</en>

        //<jp>nullの場合は""をセットする。</jp>
        //<en>Set "" in case of null.</en>
        if (selected == null)
        {
            selected = "";
        }

        ArrayList pulldownData = new ArrayList();
        //<jp>指定された種別のステーションNo配列</jp>
        //<en>array of station no. of specified type</en>
        Width[] widthArray = null;

        widthArray = getWidthArray(wConn);

        //<jp>**** ステーションをArrayListに追加する ****</jp>
        //<en>**** Add the station to the ArrayList. ****</en>
        for (int i = 0; i < widthArray.length; i++)
        {
            int width = widthArray[i].getWidth();
            //<jp>プルダウンに表示する名称は ソフトゾーンID：ソフトゾーン名称</jp>
            //<en>Names to show in pull-down list - soft zone ID: soft name</en>
            String widthName = width + ":" + widthArray[i].getWidthName();

            //<jp>倉庫ステーションNo</jp>
            //<en>warehouse station no.</en>
            String warehouseNo = null;
            ToolWarehouseHandler warehousehandle = new ToolWarehouseHandler(wConn);
            ToolWarehouseSearchKey warehousekey = new ToolWarehouseSearchKey();
            warehousekey.setWarehouseStationNo(widthArray[i].getWhStationNo());
            Warehouse[] warehouseArray = (Warehouse[])warehousehandle.find(warehousekey);

            if (warehouseArray.length <= 0)
            {
                continue;
            }

            warehouseNo = "" + warehouseArray[0].getWarehouseNo();

            //<jp>初期表示するデータ</jp>
            //<en>data for initial display</en>
            if (Integer.toString(width).equals(selected))
            {
                pulldownData.add(width + "," + widthName + "," + warehouseNo + "," + FIRSTDISP_TRUE);
            }
            //<jp>それ以外のデータ</jp>
            //<en>other data</en>
            else
            {
                pulldownData.add(width + "," + widthName + "," + warehouseNo + "," + FIRSTDISP_FALSE);
            }
        }
        String[] str = new String[pulldownData.size()];
        pulldownData.toArray(str);
        return str;
    }

    /**<jp>
     * 端末ナンバーの指定無しでバンクのプルダウンを表示するためのデータを<code>String</code>の配列にて返します。
     * <font color=#FF0000>このメソッドは複数倉庫対応できていません。</font>
     * 引数には、<code>Connection</code>、初期表示のバンク、「全バンク」の表示有無flagを与えます。
     * プルダウンを作成するためのデータはすべてBANKSELECT表より取得します。
     * Value+","+Name+","+ 親のVALUE +","+初期表示フラグ（"0" or "1"）というデータをArrayListに追加します。<BR>
     * 複数倉庫の場合、出力されるデータは以下の様になります<BR>
     *        "9999","全BANK","9000","0"<BR>
     *        "1"   ,"BANK1" ,"9000","0"<BR>
     *        "2"   ,"BANK2" ,"9000","0"<BR>
     *        "9999","全BANK","9100","0"<BR>
     *        "1"   ,"BANK1" ,"9100","0"<BR>
     *         "2"   ,"BANK2" ,"9100","0"<BR>
     * この場合、どちらの倉庫のバンクを初期表示するかを決定するための情報としてparent（倉庫ステーションナンバー）を引数に持ちます。
     * 複数倉庫の場合、連動プルダウンとして使用するので、parentには倉庫プルダウンで選択された値を入力します。
     * また、倉庫が１つの場合には""をセットしてください。
     * 初期表示に"1"をセットしたデータがプルダウンの表示時に初期表示されます。
     * @param selected  初期表示を行うバンクNo.。指定しない場合は""を入力してください。
     * @param parent    複数倉庫の場合に、選択された倉庫ステーションナンバーを指定します。倉庫が１つの場合は""をセットします。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Arrange the data, which is to display in pull-down menu for bank with no specific terminal no.,
     * in the array of <code>String</code>, then return.
     * <font color=#FF0000> This method does not provide multiple warehouse support.</font>
     * Provide the parameter with <code>Connection</code>, bank and display flag (whether/not to show
     * all banks) for initial display.
     * All data will be retrieved from ABNKSELECT table, which is needed to create the pull-down lists.
     * Data: Value+","+Name+","+ parent VALUE +","+initial display flag("0" or "1") will be added to the
     * ArrayList.<BR>
     * If multiple warehouses are handled, data being output will be as below.<BR>
     *        "9999","all BANK","9000","0"<BR>
     *        "1"   ,"BANK1" ,"9000","0"<BR>
     *        "2"   ,"BANK2" ,"9000","0"<BR>
     *        "9999","all BANK","9100","0"<BR>
     *        "1"   ,"BANK1" ,"9100","0"<BR>
     *         "2"   ,"BANK2" ,"9100","0"<BR>
     * In this case the parent (warehouse station no.) will be preserved as parameter upon which to determine
     * which warehouse to show in initial display. 
     * If handling multiple warehouses, the pull-down menu should be linked and used; therefore the value selected
     * from warehosues in pull-down list should be entered for the parent.
     * Or if handling only one warheouse, please set "".
     * The data which is set with "1" in initial display will be shown when displaying the initial pull-down list.
     * @param selected :bank no. for initial display. Please set "" if there is no specification.
     * @param parent :specifies selected warehouse station no. whne handling multiple warehouses. Or set"" if 
     * only one warehouse is handled.
     * @return :return data in <code>String</code> array which is required to create the image of pull-down menu.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public String[] getBankPulldownData(String selected, String parent)
            throws ReadWriteException
    {
        //<jp>*** 初期処理 ***</jp>
        //<en>*** Initial processing***</en>
        //<jp>倉庫の配列を取得</jp>
        //<en>Retrieve the array of the warehouse.</en>
        //Warehouse[] wWarehouseArray = getWarehouseArray(wConn, WAREHOUSE_ALL, ZONE_ALL);

        //<jp>nullの場合は""をセットする。</jp>
        //<en>Set "" in case of null.</en>
        if (selected == null)
        {
            selected = "";
        }
        if (parent == null)
        {
            parent = "";
        }
        //<jp>*** 初期処理ここまで ***</jp>
        //<en>*** Initial processing ends here. ***</en>

        //<jp>*** プルダウンデータが検索済みかをチェックここまで ***</jp>
        //<en>*** Checking (whether/not the pull down data has been searched) ends here  ***</en>

        ArrayList pulldownData = new ArrayList();
        //<jp>バンク配列の取得</jp>
        //<en>Retrieve the array of the bank.</en>
        Bank[] bankArray = getBankArray(wConn);
        //<jp>「全BANK」を挿入するindexを記憶させます。</jp>
        //<en> Store the index to insert 'all BANK'.</en>
        Vector allItemIndexVec = new Vector();
        //<jp>「全BANK」を挿入したときの倉庫ステーションNoを記憶します。</jp>
        //<en>Store the warehouse station no. when 'all BANK' is inserted.</en>
        Vector wareHouseSTNoVec = new Vector();
        //<jp>全BANK挿入位置検索用</jp>
        //<en>To be used when searching the index for 'all BANK' insertion</en>
        String wareHouseStationNo_temp = "";

        //<jp>**** バンクをArrayListに追加する ****</jp>
        //<en>**** Add the bank to ArrayList. ****</en>
        for (int i = 0; i < bankArray.length; i++)
        {
            //<jp>バンクNo.</jp>
            //<en>Bank no.</en>
            String bankNo = Integer.toString(bankArray[i].getBankNo());

            //<jp>プルダウンに表示する名称（"BANK"+"bankNo" という表示にする）</jp>
            //<en>Names to show in pull-down list (display as "BANK"+"bankNo")</en>
            String bankName = "BANK" + bankNo;
            //<jp>倉庫ステーションNo</jp>
            //<en>warehouse station no.</en>
            String wareHouseStationNo = bankArray[i].getWhStationNo();
            //<jp>前回の倉庫ステーションNoと違う場合、そのindexを覚えておく</jp>
            //<en>In case the warehouse station no. differs from the previous no., store the index.</en>
            if (!wareHouseStationNo.equals(wareHouseStationNo_temp))
            {
                allItemIndexVec.add(new Integer(i));
                wareHouseSTNoVec.add((String)wareHouseStationNo);
                wareHouseStationNo_temp = wareHouseStationNo;
            }

            //<jp>初期表示するデータ</jp>
            //<en>data for initial display</en>
            if (bankNo.equals(selected))
            {
                pulldownData.add(bankNo + "," + bankName + "," + wareHouseStationNo + "," + FIRSTDISP_TRUE);
            }
            //<jp>それ以外のデータ</jp>
            //<en>other data</en>
            else
            {
                pulldownData.add(bankNo + "," + bankName + "," + wareHouseStationNo + "," + FIRSTDISP_FALSE);
            }
        }
        //<jp>**** プルダウンデータを保持する ****</jp>
        //<en>**** Preserve the pull-down data. ****</en>
        String[] str = new String[pulldownData.size()];
        pulldownData.toArray(str);
        return str;

    }

    /**<jp>
     * 端末ナンバーの指定無しでバンクのプルダウンを表示するためのデータを<code>String</code>の配列にて返します。
     * 引数には、<code>Connection</code>、初期表示のバンク、「全バンク」の表示有無flagを与えます。
     * プルダウンを作成するためのデータはすべてBANKSELECT表より取得します。
     * Value+","+Name+","+ 親のVALUE +","+初期表示フラグ（"0" or "1"）というデータをArrayListに追加します。<BR>
     * 複数倉庫の場合、出力されるデータは以下の様になります<BR>
     *         "バンク","名称",    "格納区分",    "0"
     *        "1"       ,"BANK1" ,    "1",            "0"<BR>
     *        "2"       ,"BANK2" ,    "2",            "0"<BR>
     * この場合、どちらの倉庫のバンクを初期表示するかを決定するための情報としてparent（倉庫ステーションナンバー）を引数に持ちます。
     * 複数倉庫の場合、連動プルダウンとして使用するので、parentには倉庫プルダウンで選択された値を入力します。
     * また、倉庫が１つの場合には""をセットしてください。
     * 初期表示に"1"をセットしたデータがプルダウンの表示時に初期表示されます。
     * @param selected  初期表示を行うバンクNo.。指定しない場合は""を入力してください。
     * @param whnumber  複数倉庫の場合に、選択された格納区分を指定します。倉庫が１つの場合は""をセットします。
     * @return <code>String</code>の配列にてプルダウンの描画に必要なデータを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Arrange the data, which is to display in pull-down menu for bank with no specific terminal no.,
     * in the array of <code>String</code>, then return.
     * Provide the parameter with <code>Connection</code>, bank and display flag (whether/not to show
     * all banks) for initial display.
     * All data will be retrieved from ABNKSELECT table, which is needed to create the pull-down lists.
     * Data: Value+","+Name+","+ parent VALUE +","+initial display flag("0" or "1") will be added to the
     * ArrayList.<BR>
     * If multiple warehouses are handled, data being output will be as below.<BR>
     *         "bank","name",    "storage type",    "0"
     *        "1"       ,"BANK1" ,    "1",            "0"<BR>
     *        "2"       ,"BANK2" ,    "2",            "0"<BR>
     * In this case the parent (warehouse station no.) will be preserved as parameter upon which to determine
     * which warehouse to show in initial display. 
     * If handling multiple warehouses, the pull-down menu should be linked and used; therefore the value selected
     * from warehosues in pull-down list should be entered for the parent.
     * Or if handling only one warheouse, please set "".
     * The data which is set with "1" in initial display will be shown when displaying the initial pull-down list.
     * @param selected :bank no. for initial display. Please set "" if there is no specification.
     * @param whnumber :specifies selected storage type if handling multiple warehouses. "" will be set if only
     * one warehouse is handled.
     * @return :return data in <code>String</code> array which is required to create the image of pull-down menu.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public String[] getwhBankPulldownData(String selected, int whnumber)
            throws ReadWriteException
    {
        //<jp>*** 初期処理 ***</jp>
        //<en>*** Initial processing***</en>
        //<jp>nullの場合は""をセットする。</jp>
        //<en>Set "" in case of null.</en>
        if (selected == null)
        {
            selected = "";
        }
        //<jp>*** 初期処理ここまで ***</jp>
        //<en>*** Initial processing ends here. ***</en>

        //<jp>*** プルダウンデータが検索済みかをチェックここまで ***</jp>
        //<en>*** Checking (whether/not the pull down data has been searched) ends here  ***</en>

        ArrayList pulldownData = new ArrayList();

        //<jp> 全バンク配列の取得</jp>
        //<en> Retrieve the array of all banks.</en>
        Bank[] bankArray = getBankArray(wConn);
        //<jp> 「BANK」を挿入するindexを記憶させます。</jp>
        //<en> Store the index to insert "BANKK".</en>
        Vector allItemIndexVec = new Vector();
        //<jp> 「BANK」を挿入したときの格納区分を記憶します。</jp>
        //<en> Store the storage type when "BANK" is inserted.</en>
        Vector wareHouseSTNoVec = new Vector();
        //<jp> 「BANK」挿入位置検索用</jp>
        //<en> To be used when searching the index of "BANK" insertion</en>
        String warehouseNo_temp = "";

        //<jp>**** バンクをArrayListに追加する ****</jp>
        //<en>**** Add banks to the ArrayList. ****</en>
        for (int i = 0; i < bankArray.length; i++)
        {
            //<jp>バンクNo.</jp>
            //<en>Bank no.</en>
            String bankNo = Integer.toString(bankArray[i].getBankNo());

            //<jp>プルダウンに表示する名称（"BANK"+"bankNo" という表示にする）</jp>
            //<en>Names to show in pull-down list (Display as "BANK"+"bankNo")</en>
            String bankName = "BANK" + bankNo;
            //<jp>倉庫ステーションNo(BANKSELECTより取得)から格納区分を取得する。</jp>
            //<en>Retrieve the storage type from the warehouse station no. (retrieved from BANKSELECT)</en>
            String warehouseNo = null;
            String whStationNo = bankArray[i].getWhStationNo();
            ToolWarehouseHandler warehousehandle = new ToolWarehouseHandler(wConn);
            ToolWarehouseSearchKey warehousekey = new ToolWarehouseSearchKey();
            warehousekey.setWarehouseStationNo(whStationNo);
            Warehouse[] warehouseArray = (Warehouse[])warehousehandle.find(warehousekey);

            if (warehouseArray.length <= 0)
            {
                continue;
            }

            warehouseNo = "" + warehouseArray[0].getWarehouseNo();
            //<jp>前回の格納区分と違う場合、そのindexを覚えておく</jp>
            //<en>If the storage type differs from the previous data, store the index.</en>
            if (!warehouseNo.equals(warehouseNo_temp))
            {
                allItemIndexVec.add(new Integer(i));
                wareHouseSTNoVec.add((String)warehouseNo);
                warehouseNo_temp = warehouseNo;
            }

            //<jp>初期表示するデータ</jp>
            //<en>data for initial display</en>
            if (bankNo.equals(selected))
            {
                pulldownData.add(bankNo + "," + bankName + "," + warehouseNo + "," + FIRSTDISP_TRUE);
            }
            //<jp>それ以外のデータ</jp>
            //<en>other data</en>
            else
            {
                pulldownData.add(bankNo + "," + bankName + "," + warehouseNo + "," + FIRSTDISP_FALSE);
            }
        }

        //<jp>**** プルダウンデータを保持する ****</jp>
        //<en>**** Preserve the pull-down data. ****</en>
        String[] str = new String[pulldownData.size()];
        pulldownData.toArray(str);

        return str;
    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------


    // Private methods -----------------------------------------------
    /**<jp>
     * 倉庫を検索し引数で指定した種類の倉庫の配列を返します。
     * @param conn <CODE>Connection</CODE>
     * @param 倉庫種別（WAREHOUSE_FLOOR：平置き倉庫 WAREHOUSE_AUTO：自動倉庫 WAREHOUSE_ALL：平置き、自動倉庫両方）
     * @param ゾーン種別（ZONE_HARD：ハードゾーン ZONE_SOFT：ソフトゾーン ZONE_ALL：ハード、ソフト両方）
     * @param フリーアロケーション運用（FREE_ALLOCATION_OFF：フリーアロケーション運用なし 
     *         FREE_ALLOCATION_ON：フリーアロケーション運用あり 
     *         FREE_ALLOCATION_ALL：フリーアロケーション運用なし、フリーアロケーション運用あり両方）
     * @return    倉庫配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException 定義情報に異常がある場合に通知されます。
     </jp>*/
    /**<en>
     * Search warehouses, then reurn the array of warehouse of the types that parameter specified.
     * @param conn <CODE>Connection</CODE>
     * @param type waraehouse type (WAREHOUSE_FLOOR:floot storage warehouse, WAREHOUSE_AUTO:automated warehouse, 
     * WAREHOUSE_ALL: both floor storage warehouse and automated warehouse)
     * @param zonetype :zone type (ZONE_HARD:hard zone, ZONE_SOFT: soft zone, ZONE_ALL:both hard and soft)
     * @param freealloctype :free alloctype type (FREE_ALLOCATION_OFF:free allocation off, 
     *         FREE_ALLOCATION_ON:free allocation on, 
     *         FREE_ALLOCATION_ALL:both free allocation off and free allocation on)
     * @return    :the array of warehouse 
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws InvalidDefineException :Notifies if error is found in definition of information.
     </en>*/
    private Warehouse[] getWarehouseArray(Connection conn, String type, String zonetype, String freealloctype)
            throws ReadWriteException,
                InvalidDefineException
    {
        ToolWarehouseSearchKey warehouseKey = new ToolWarehouseSearchKey();
        ToolWarehouseHandler warehouseHandle = new ToolWarehouseHandler(conn);
        //<jp>倉庫ステーションNoの昇順でソート</jp>
        //<en>Sort in asceding order of warehouse station no.</en>
        warehouseKey.setWarehouseStationNoOrder(2, true);
        warehouseKey.setWarehouseNoOrder(1, true);
        //<jp>平置き倉庫のプルダウンデータ</jp>
        //<en>Pull down data for floor storage.</en>
//        if (type.equals(WAREHOUSE_FLOOR))
//        {
//            warehouseKey.setWarehouseType(Warehouse.CONVENTIONAL_WAREHOUSE);
//        }
//        //<jp>自動倉庫のプルダウンデータ</jp>
//        //<en>Pull down data for automated warehouse</en>
//        else if (type.equals(WAREHOUSE_AUTO))
//        {
//            warehouseKey.setWarehouseType(Warehouse.AUTOMATID_WAREHOUSE);
//        }
//        //<jp>自動倉庫、平置き倉庫のプルダウンデータ</jp>
//        //<en>Pull down data for autoamted warehouse and floor storage warehouse</en>
//        else if (type.equals(WAREHOUSE_ALL))
//        {
//            //<jp>SearchKeyに何もセットしない</jp>
//            //<en>No setting here for SearchKey.</en>
//        }
//        else
//        {
//            throw new InvalidDefineException("Argument (Type) is wrong. ");
//        }
        //<jp>ハードゾーンのプルダウンデータ</jp>
        //<en>Pull-down data of hard zone</en>
        if (zonetype.equals(ZONE_HARD))
        {
            int[] znid = {
                    Warehouse.HARD,
                    Warehouse.HARD_ITEM
            };
            warehouseKey.setZoneType(znid);
        }
        //<jp>ソフトゾーンのプルダウンデータ</jp>
        //<en>Pull-down data of soft zone</en>
        else if (zonetype.equals(ZONE_SOFT))
        {
            warehouseKey.setZoneType(Warehouse.SOFT);
        }
        //<jp>ハードゾーン、ソフトゾーンのプルダウンデータ</jp>
        //<en>Pull-down data of hard zone and soft zone</en>
        else if (zonetype.equals(ZONE_ALL))
        {
            //<jp>SearchKeyに何もセットしない</jp>
            //<en>No setting here for SearchKey.</en>
        }
        else
        {
            throw new InvalidDefineException("Argument (ZoneType) is wrong. ");
        }

        //<jp>フリーアロケーション運用なしのプルダウンデータ</jp>
        //<en>Pull-down data of free allocation off</en>
        if (freealloctype.equals(FREE_ALLOCATION_OFF))
        {
            warehouseKey.setFreeAllocationType(Warehouse.FREE_ALLOCATION_OFF);
        }
        //<jp>フリーアロケーション運用ありのプルダウンデータ</jp>
        //<en>Pull-down data of soft free allocation on</en>
        else if (freealloctype.equals(FREE_ALLOCATION_ON))
        {
            warehouseKey.setFreeAllocationType(Warehouse.FREE_ALLOCATION_ON);
        }
        //<jp>フリーアロケーション運用なし、フリーアロケーション運用ありのプルダウンデータ</jp>
        //<en>Pull-down data of free allocation off and free allocation on</en>
        else if (freealloctype.equals(FREE_ALLOCATION_ALL))
        {
            //<jp>SearchKeyに何もセットしない</jp>
            //<en>No setting here for SearchKey.</en>
        }
        else
        {
            throw new InvalidDefineException("Argument (FreeAllocationType) is wrong. ");
        }

        return (Warehouse[])warehouseHandle.find(warehouseKey);
    }

    /**<jp>
     * 倉庫を検索し引数で指定した種類の倉庫の配列を返します。
     * @param conn <CODE>Connection</CODE>
     * @param 倉庫種別（WAREHOUSE_FLOOR：平置き倉庫 WAREHOUSE_AUTO：自動倉庫 WAREHOUSE_ALL：平置き、自動倉庫両方）
     * @param ゾーン種別（ZONE_HARD：ハードゾーン ZONE_SOFT：ソフトゾーン ZONE_ALL：ハード、ソフト両方）
     * @param フリーアロケーション運用（FREE_ALLOCATION_OFF：フリーアロケーション運用なし 
     *         FREE_ALLOCATION_ON：フリーアロケーション運用あり 
     *         FREE_ALLOCATION_ALL：フリーアロケーション運用なし、フリーアロケーション運用あり両方）
     * @return    倉庫配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException 定義情報に異常がある場合に通知されます。
     </jp>*/
    /**<en>
     * Search warehouses, then reurn the array of warehouse of the types that parameter specified.
     * @param conn <CODE>Connection</CODE>
     * @param type waraehouse type (WAREHOUSE_FLOOR:floot storage warehouse, WAREHOUSE_AUTO:automated warehouse, 
     * WAREHOUSE_ALL: both floor storage warehouse and automated warehouse)
     * @param zonetype :zone type (ZONE_HARD:hard zone, ZONE_SOFT: soft zone, ZONE_ALL:both hard and soft)
     * @param freealloctype :free alloctype type (FREE_ALLOCATION_OFF:free allocation off, 
     *         FREE_ALLOCATION_ON:free allocation on, 
     *         FREE_ALLOCATION_ALL:both free allocation off and free allocation on)
     * @return    :the array of warehouse 
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws InvalidDefineException :Notifies if error is found in definition of information.
     </en>*/
    private Warehouse[] getWarehouseInSoftZonePriorityArray(Connection conn)
            throws ReadWriteException,
                InvalidDefineException
    {
        ToolSoftZonePriorityHandler zoneHandle = new ToolSoftZonePriorityHandler(conn);
        String[] warehouses = (String[])zoneHandle.findWarehouses();

        Warehouse[] whArray = new Warehouse[warehouses.length];

        for (int i = 0; i < whArray.length; i++)
        {
            ToolWarehouseHandler warehousehandle = new ToolWarehouseHandler(conn);
            ToolWarehouseSearchKey warehousekey = new ToolWarehouseSearchKey();
            warehousekey.setWarehouseStationNo(warehouses[i]);
            Warehouse[] warehouseArray = (Warehouse[])warehousehandle.find(warehousekey);

            if (warehouseArray.length <= 0)
            {
                continue;
            }
            Warehouse wh = new Warehouse();

            wh.setWarehouseNo(warehouseArray[0].getWarehouseNo());
            wh.setWarehouseName(warehouseArray[0].getWarehouseName());
            whArray[i] = wh;
        }

        return (whArray);
    }

    /**<jp>
     * 全倉庫にある、ステーション配列を取得します。
     * 作業場を含むかどうかを条件にステーションの配列を取得します。
     * @param conn <CODE>Connection</CODE>
     * @param worktype 0：すべてのステーション、１：作業場を含まない、２：作業場、代表ステーションを含まない
     * @return 引数で指定した条件のステーション配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve the station array in all warehouses.
     * Retrieve the station array under condition whether/not to include workshops.
     * @param conn <CODE>Connection</CODE>
     * @param worktype -0: all stations, 1: workshop excluded, 2: workshos and main station excluded
     * @return :the station array that meets the paramter specified condition
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private Station[] getStationArray(Connection conn, int worktype)
            throws ReadWriteException
    {
        ToolStationHandler stationHandle = new ToolStationHandler(conn);
        ToolStationSearchKey stationKey = new ToolStationSearchKey();
        switch (worktype)
        {
            case 0:
                break;
            case 1:

                int[] types = {
                        Station.NOT_WORKPLACE,
                        Station.MAIN_STATIONS
                };
                stationKey.setWorkPlaceType(types);
                break;

            case 2:
                int type = Station.NOT_WORKPLACE;
                stationKey.setWorkPlaceType(type);
                break;

            default:
        }

        //<jp>ステーションNoの昇順でソート</jp>
        //<en>Sort in ascending order of station no.</en>
        stationKey.setStationNoOrder(1, true);
        Station[] allStationArray = (Station[])stationHandle.find(stationKey);

        return allStationArray;
    }

    /**<jp>
     * WAREHOUSE表を検索し倉庫ステーション配列を返します。
     * @param conn <CODE>Connection</CODE>
     * @return    倉庫ステーション配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search the WAREHOUSE table and return the array of warehouse station.
     * @param conn <CODE>Connection</CODE>
     * @return    :warehouse station array
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private Warehouse[] getWarehouseArray(Connection conn)
            throws ReadWriteException
    {
        ToolWarehouseSearchKey warehouseKey = new ToolWarehouseSearchKey();
        ToolWarehouseHandler warehouseHandle = new ToolWarehouseHandler(conn);
        warehouseKey.setWarehouseNoOrder(1, true);

        return (Warehouse[])warehouseHandle.find(warehouseKey);
    }

    /**<jp>
     * 全倉庫にある、アイル配列を取得します。
     * @param conn <CODE>Connection</CODE>
     * @return 引数で指定した条件のアイル配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve the array of aisles in all warehouses.
     * @param conn <CODE>Connection</CODE>
     * @return :the aisle array that meets the paramter specified condition
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private Station[] getAisleArray(Connection conn)
            throws ReadWriteException
    {
        ToolAisleHandler aisleHandle = new ToolAisleHandler(conn);
        ToolAisleSearchKey aisleKey = new ToolAisleSearchKey();

        //<jp>ステーションNoの昇順でソート</jp>
        //<en>/Sort in ascending order of station no.</en>
        aisleKey.setStationNoOrder(1, true);
        Station[] aisleArray = (Station[])aisleHandle.find(aisleKey);

        return aisleArray;
    }

    /**<jp>
     * GROUPCONTROLLER表を検索しグループコントローラ配列を返します。
     * @param conn <CODE>Connection</CODE>
     * @return    グループコントローラ配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search the GROUPCONTROLLER table and return the array of group controller.
     * @param conn <CODE>Connection</CODE>
     * @return    :the array of group controller
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private GroupController[] getGroupControllerArray(Connection conn)
            throws ReadWriteException
    {
        return GroupController.getInstances(conn);
    }

    /**<jp>
     * BANKSELECT表を検索し引数で指定した種類のBANK配列を返します。
     * @param conn <CODE>Connection</CODE>
     * @return    バンク配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search the BANKSELECT table and return the array of BANK of the type specified by parameter.
     * @param conn <CODE>Connection</CODE>
     * @return    :the bank array
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private Bank[] getBankArray(Connection conn)
            throws ReadWriteException
    {
        ToolBankSearchKey bankKey = new ToolBankSearchKey();
        ToolBankHandler bankHandle = new ToolBankHandler(conn);
        //<jp>倉庫ステーションNoの昇順でソート</jp>
        //<en>Sort in ascending order of warehouse station no.</en>
        bankKey.setWhStationNoOrder(1, true);

        //<jp>バンクNoの昇順でソート</jp>
        //<en>Sort in ascending order of bank no.</en>
        bankKey.setBankNoOrder(2, true);

        return (Bank[])bankHandle.find(bankKey);
    }

    /**<jp>
     * HARDZONEを検索し引数で指定した種類のHARDZONE配列を返します。
     * @param conn <CODE>Connection</CODE>
     * @return   HardZone配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search the HARDZONE table and return the array of HARDZONE of the type specified by parameter.
     * @param conn <CODE>Connection</CODE>
     * @return   :the HardZone array
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private HardZone[] getHardZoneArray(Connection conn)
            throws ReadWriteException
    {
        ToolHardZoneSearchKey zoneKey = new ToolHardZoneSearchKey();
        ToolHardZoneHandler zoneHandle = new ToolHardZoneHandler(conn);
        //<jp>ゾーンIDの昇順でソート</jp>
        //<en>Sort in ascending order of zone ID.</en>
        zoneKey.setHardZoneIDOrder(1, true);

        return (HardZone[])zoneHandle.find(zoneKey);
    }

    /**<jp>
     * SOFTZONEを検索し引数で指定した種類のSOFTZONE配列を返します。
     * @param conn <CODE>Connection</CODE>
     * @return   SoftZone配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search the SOFTZONE table and return the array of SOFTZONE of the type specified by parameter.
     * @param conn <CODE>Connection</CODE>
     * @return   :the SoftZone array
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private SoftZone[] getSoftZoneArray(Connection conn)
            throws ReadWriteException
    {
        ToolSoftZoneSearchKey zoneKey = new ToolSoftZoneSearchKey();
        ToolSoftZoneHandler zoneHandle = new ToolSoftZoneHandler(conn);
        //<jp>ゾーンIDの昇順でソート</jp>
        //<en>Sort in ascending order of zone ID.</en>
        zoneKey.setSoftZoneIDOrder(1, true);

        return (SoftZone[])zoneHandle.find(zoneKey);
    }

    /**<jp>
     * WIDTHを検索し引数で指定した種類のWIDTH配列を返します。
     * @param conn <CODE>Connection</CODE>
     * @return   Width配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Search the WIDTH table and return the array of WIDTH of the type specified by parameter.
     * @param conn <CODE>Connection</CODE>
     * @return   :the Width array
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private Width[] getWidthArray(Connection conn)
            throws ReadWriteException
    {
        ToolWidthSearchKey widthKey = new ToolWidthSearchKey();
        ToolWidthHandler widthHandle = new ToolWidthHandler(conn);
        //<jp>倉庫ステーションNoの昇順でソート</jp>
        //<en>Sort in ascending order of warehouse station no.</en>
        widthKey.setWHStationNoOrder(1, true);
        //<jp>荷幅の昇順でソート</jp>
        //<en>Sort in ascending order of width.</en>
        widthKey.setWidthIdOrder(2, true);

        return (Width[])widthHandle.find(widthKey);
    }
}
