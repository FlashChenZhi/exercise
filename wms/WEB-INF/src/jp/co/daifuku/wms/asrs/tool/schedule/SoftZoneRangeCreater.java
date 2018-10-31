// $Id: SoftZoneRangeCreater.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.schedule;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.awt.Rectangle;
import java.io.IOException;
import java.sql.Connection;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.asrs.tool.common.LogHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolSoftZoneHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolSoftZonePriorityHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolSoftZonePrioritySearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolSoftZoneRangeHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolSoftZoneRangeSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolSoftZoneSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Shelf;
import jp.co.daifuku.wms.asrs.tool.location.SoftZonePriority;
import jp.co.daifuku.wms.asrs.tool.location.SoftZoneRange;

/**<jp>
 * ソフトゾーンの範囲のメンテナンス処理を行なうクラスです。
 * AbstractCreaterを継承し、ゾーンの設定処理に必要な処理を実装します。
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/11/21</TD><TD> K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class processes the zone maintenance.
 * It inherits the AbstractCreater, and implements the processes required for zone setting.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/11/21</TD><TD> K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class SoftZoneRangeCreater
        extends AbstractCreater
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * <CODE>ToolZoneSearchKey</CODE>インスタンス
     </jp>*/
    /**<en>
     * <CODE>ToolZoneSearchKey</CODE> instance
     </en>*/
    protected ToolSoftZoneRangeSearchKey wZoneKey = null;

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
        return ("$Revision: 4122 $,$Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 指定された位置のパラメータを削除します。<BR>
     * @param index 削除するパラメータ位置
     * @throws ScheduleException 指定された位置にパラメータが存在しない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete the parameter of the specified position. <BR>
     * @param index :position of the deleting parameter
     * @throws ScheduleException :Notifies if there are no parameters in specified position.
     </en>*/
    public void removeParameter(int index)
            throws ScheduleException
    {
        //<jp> メッセージの初期化</jp>
        //<en> Initialization of the message</en>
        setMessage("");
        try
        {
            wParamVec.remove(index);
            setMessage("6121003");
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new ScheduleException(e.getMessage());
        }
    }

    /**<jp>
     * 画面から印刷発行ボタンが押下された場合の処理を実装します。<BR>
     * @param conn データベースとのコネクションオブジェクト
     * @param locale オブジェクト
     * @param listParam スケジュールパラメータ
     * @return 印刷処理の結果
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Implement the process to run when the print-issue button was pressed on the display.<BR>
     * @param conn :Connection to connect with database
     * @param locale object
     * @param listParam :schedule parameter
     * @return :result of print job
     </en>*/
    public boolean print(Connection conn, Locale locale, Parameter listParam)
    {
        return true;
    }

    /**<jp>
     * 画面へ表示するデータを取得します。<BR>
     * @param <code>Locale</code> オブジェクト
     * @param searchParam 検索条件
     * @return スケジュールパラメータの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve data to isplay on the screen.<BR>
     * @param conn :Connection to connect with database
     * @param locale object
     * @param searchParam :search conditions
     * @return :the array of schedule parameter
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.<BR>
     </en>*/
    public Parameter[] query(Connection conn, Locale locale, Parameter searchParam)
            throws ReadWriteException,
                ScheduleException
    {
        SoftZoneRange[] array = getSoftZoneRangeArray(conn);
        //<jp>一時的にデータを格納するVector</jp>
        //<jp>ため打ちの最大件数を初期値としてセット</jp>
        //<en>Vector where the data will temporarily be stored</en>
        //<en>Set the max number of data as an initial value for entered data summary.</en>
        Vector vec = new Vector(100);
        //<jp>表示用のエンティティクラス</jp>
        //<en>Entity class for display</en>
        SoftZoneRangeParameter dispData = null;
        if (array.length > 0)
        {
            for (int i = 0; i < array.length; i++)
            {
                dispData = new SoftZoneRangeParameter();
                dispData.setZoneID(array[i].getSoftZoneID());
                dispData.setWareHouseStationNo(array[i].getWhStationNo());
                dispData.setWareHouseName(array[i].getWhStationNo());
                dispData.setStartBank(array[i].getStartBankNo());
                dispData.setStartBay(array[i].getStartBayNo());
                dispData.setStartLevel(array[i].getStartLevelNo());
                dispData.setEndBank(array[i].getEndBankNo());
                dispData.setEndBay(array[i].getEndBayNo());
                dispData.setEndLevel(array[i].getEndLevelNo());
                vec.addElement(dispData);
            }
            SoftZoneRangeParameter[] fmt = new SoftZoneRangeParameter[vec.size()];
            vec.toArray(fmt);
            return fmt;
        }
        return null;
    }

    /**<jp>
     * このクラスの初期化を行ないます。
     * @param conn データベースとのコネクションオブジェクト
     * @param kind 処理区分
     </jp>*/
    /**<en>
     * Initialize this class.
     * @param conn :connetion object with database
     * @param kind :process type
     </en>*/
    public SoftZoneRangeCreater(Connection conn, int kind)
    {
        super(conn, kind);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * パラメータの補完処理を行います。<BR>
     * ・倉庫名称が空白の場合は、名称をセットする。<BR>
     * ・修正、削除処理の場合、他の端末で更新されたかチェックするためZoneインスタンス
     * をパラメータに追加する。<BR>
     * 処理にに成功した場合は補完したパラメータ。失敗した場合はnullを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param param インスタンスをセットするパラメータ
     * @return 処理に成功した場合はパラメータ。失敗した場合はnullを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException 処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Conduct the complementarity process of parameter.<BR>
     *  -If the warehouse name is blank, set the name.<BR>
     *  -Append Zone instance to the parameter in order to check whether/not the data
     *   has been modified by other terminals.<BR>
     * It returns the complemented parameter if the process succeeded, or returns false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param param  :the parameter to set the instance
     * @return :returns the parameter if the process succeeded, ro returns null if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the process.
     </en>*/
    protected Parameter complementParameter(Parameter param)
            throws ReadWriteException,
                ScheduleException
    {
        SoftZoneRangeParameter mParameter = (SoftZoneRangeParameter)param;

        return mParameter;
    }

    /**<jp>
     * パラメータチェック処理を行ないます。パラメータ追加時、メンテナンス処理を実行する前に呼ばれます。
     * パラメータに異常があった場合、その詳細を<code>getMessage</code>で取得できます。
     * ＜ゾーン名称＞<BR>
     * ・禁止文字<BR>
     * ＜ゾーン範囲＞<BR>
     * ・開始棚ベイ＜＝終了棚ベイ<BR>
     * ・開始棚レベル＜＝終了棚レベル<BR>
     * ・ゾーン範囲が指定倉庫の範囲内にあること<BR>
     * @param conn データベースとのコネクションオブジェクト
     * @param param チェックするパラメータの内容
     * @return パラメータに異常が無い場合はtrue、異常がある場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Processes the paramter check. It will be called when adding the parameter, before the 
     * execution of maintenance process.
     * If there are any error with parameter, the reason can be obtained by <code>getMessage</code>.
     * <Zone name><BR>
     *  -Unacceptable letters and symbols<BR>
     * <Zone range><BR>
     *  -Startinf location bay <= ending location bay<BR>
     *  -Starting location level <= ending location level<BR>
     *  -Zone range should be contained within teh s@pecified warehouse range.<BR>
     * @param conn :Connection to connect with database
     * @param param : contents of the parameter to check
     * @return :returns true if there is no error with parameter, or returns false if there are any errors.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public boolean check(Connection conn, Parameter param)
            throws ReadWriteException,
                ScheduleException
    {
        //<jp>処理区分を取得</jp>
        //<en>Retrieve the process type.</en>
        int processingKind = getProcessingKind();
        SoftZoneRangeParameter mParameter = (SoftZoneRangeParameter)param;

        //<jp>登録処理の場合</jp>
        //<en>In case of registration,</en>
        if (processingKind == M_CREATE)
        {
            //<jp> WAREHOUSE表に登録されているかチェック</jp>
            //<en> Check whether/not the data is registered in WAREHOUSE table.</en>
            ToolWarehouseHandler whhandle = new ToolWarehouseHandler(conn);
            ToolWarehouseSearchKey whkey = new ToolWarehouseSearchKey();
            if (whhandle.count(whkey) <= 0)
            {
                //<jp> 倉庫情報がありません。倉庫設定画面で登録してください</jp>
                //<en> There is no information for the warehouse. Please register in screen for the wareshouse setting.</en>
                setMessage("6123100");
                return false;
            }

            //<jp> SOFTZONE表に登録されているかチェック（ソフトゾーンID）</jp>
            ToolSoftZoneSearchKey skey = new ToolSoftZoneSearchKey();
            if (getToolSoftZoneHandler(conn).count(skey) <= 0)
            {
                //<jp> ソフトゾーン情報がありません。ソフトゾーン設定画面で登録してください。</jp>
                setMessage("6123254");
                return false;
            }

            //<jp> SHELF表に登録されているかチェック</jp>
            //<en> CHeck whether/not it is registered in SHELF table.</en>
            ToolShelfHandler ahandle = new ToolShelfHandler(conn);
            ToolShelfSearchKey akey = new ToolShelfSearchKey();
            akey.setWarehouseStationNo(mParameter.getWareHouseStationNo());
            if (ahandle.count(akey) <= 0)
            {
                //<jp> 棚管理情報がありません。アイル設定画面で登録してください</jp>
                //<en> The location control information cannot be found. Please register in screen for aisle setting.</en>
                setMessage("6123113");
                return false;
            }

            //<jp>ゾーン範囲確認</jp>
            //<en>Check the zone range.</en>
            if (!checkZoneRange(conn, mParameter))
            {
                return false;
            }
            return true;
        }
        //<jp>処理区分が異常</jp>
        //<en>Error with the process type.</en>
        else
        {
            //<jp> 予期しない値がセットされました。{0} = {1}</jp>
            //<en> Unexpected value has been set.{0} = {1}</en>
            String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
            RmiMsgLogClient.write(msg, (String)this.getClass().getName());
            //<jp> 6127006 = 致命的なエラーが発生しました。ログを参照してください。</jp>
            //<en> 6127006 = Fatal error occurred. Please refer to the log.</en>
            throw new ScheduleException("6127006");
        }
    }

    /**<jp>
     * 整合性チェック処理を行ないます。eAWC環境設定ツールのジェネレート時に呼ばれます。
     * 異常があった場合、その詳細をファイルへ書き込みます。
     * @param conn データベースとのコネクションオブジェクト
     * @param logpath 異常が発生したときのログを書き込むファイルを置くためのパス
     * @param locale <code>Locale</code>オブジェクト
     * @return 異常が無い場合はtrue、一つでも異常がある場合はfalseを返します。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the inconsistency check. This will be called when generating the eAWC environment setting tool.
     * If any error takes place, the detail will be written in the file.
     * @param conn :Connection to connect with database
     * @param logpath :path to place the file in which the log will be written when error occurred.
     * @param locale <code>Locale</code> object
     * @return :true if there is no error, or false if there are any error.
     * @throws ScheduleException :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public boolean consistencyCheck(Connection conn, String logpath, Locale locale)
            throws ScheduleException
    {
        ToolCommonChecker check = new ToolCommonChecker(conn);
        //<jp>エラーが無い場合にtrueとなります。</jp>
        //<en>True if there is no error.</en>
        boolean errorFlag = true;
        String logfile = logpath + "/" + ToolParam.getParam("CONSTRAINT_CHECK_FILE");

        try
        {
            LogHandler loghandle = new LogHandler(logfile, locale);

            ToolSoftZoneSearchKey zoneKey = new ToolSoftZoneSearchKey();

            //<jp>SoftZoneが設定されていない場合</jp>
            //<en>If the SoftZone has not been set,</en>
            if (getToolSoftZoneHandler(conn).count(zoneKey) == 0)
            {
                return true;
            }

            SoftZoneRangeParameter zone = new SoftZoneRangeParameter();

            ToolSoftZoneRangeSearchKey zoneRangeKey = new ToolSoftZoneRangeSearchKey();
            zoneRangeKey.setWHStationNoOrder(1, true);
            SoftZoneRange[] znRangeArray = (SoftZoneRange[])getToolSoftZoneRangeHandler(conn).find(zoneRangeKey);

            //<jp>SoftZoneRangeが設定されていない場合</jp>
            //<en>If the SoftZoneRange has not been set,</en>
            if (znRangeArray.length == 0)
            {
                //<jp>6123264=ソフトゾーン範囲が設定されていません。</jp>
                //<en>6123264 = The soft zone range has not been set.</en>
                loghandle.write("SoftZoneRange", "SoftZoneRange Table", "6123264");
                //<jp>ソフトゾーンが設定されていない場合は、後のチェックを行わずに抜ける</jp>
                //<en>If the ard zone has not been set, discontinue the checks and exit.</en>
                return false;
            }

            //<jp>*** 倉庫ステーションNoのチェック(SoftZoneRange表) ***</jp>
            //<jp>SOFTZONERANGE表の倉庫ステーションNo.がWAREHOUSE表に存在するか確認</jp>
            String tempWarehouseStationNo = "";
            for (int i = 0; i < znRangeArray.length; i++)
            {
                // 倉庫が異なる場合にチェックを行う
                String warehouseStationNo = znRangeArray[i].getWhStationNo();
                if (!tempWarehouseStationNo.equals(warehouseStationNo))
                {
                    tempWarehouseStationNo = warehouseStationNo;
                    // 倉庫情報に存在するかチェック
                    if (!check.isExistAutoWarehouseStationNo(warehouseStationNo))
                    {
                        loghandle.write("SoftZoneRange", "Warehouse Table", check.getMessage());
                        errorFlag = false;
                    }
                }
            }

            ToolSoftZonePriorityHandler zonepHandler = new ToolSoftZonePriorityHandler(conn);
            ToolSoftZonePrioritySearchKey zonepKey = new ToolSoftZonePrioritySearchKey();
            SoftZonePriority[] znPriorityArray = (SoftZonePriority[])zonepHandler.find(zonepKey);

            tempWarehouseStationNo = "";
            for (int i = 0; i < znPriorityArray.length; i++)
            {
                // 倉庫が異なる場合にチェックを行う
                String warehouseStationNo = znPriorityArray[i].getWhStationNo();
                if (!tempWarehouseStationNo.equals(warehouseStationNo))
                {
                    tempWarehouseStationNo = warehouseStationNo;
                    // Shelf表のソフトゾーンチェック
                    if (!check.checkShelfSoftZone(warehouseStationNo))
                    {
                        loghandle.write("SoftZoneRange", "Shelf Table", check.getMessage());
                        errorFlag = false;
                    }
                }
            }

            //<jp>*** ゾーンIDのチェック(SoftZoneRange表) ***</jp>
            //<jp>SOFTZONERANGE表のゾーンIDがSOFTZONE表に存在するか確認</jp>
            for (int i = 0; i < znRangeArray.length; i++)
            {
                int zoneID = znRangeArray[i].getSoftZoneID();
                if (!check.isExistSoftZone(zoneID))
                {
                    loghandle.write("SoftZoneRange", "SoftZone Table", check.getMessage());
                    errorFlag = false;
                }
            }

            //<jp>*** ゾーンIDのチェック(SoftZoneRange表) ***</jp>
            //<jp>SOFTZONERANGE表のゾーンIDがSOFTZONEPRIORITY表に存在するか確認</jp>
            for (int i = 0; i < znRangeArray.length; i++)
            {
                String warehouseStationNo = znRangeArray[i].getWhStationNo();
                int zoneID = znRangeArray[i].getSoftZoneID();
                if (!check.isExistSoftZonePriority(warehouseStationNo, zoneID))
                {
                    loghandle.write("SoftZoneRange", "SoftZonePriority Table", check.getMessage());
                    errorFlag = false;
                }
            }

            //<jp>*** ゾーンIDのチェック(SoftZoneRange表) ***</jp>
            //<jp>SOFTZONERANGE表のゾーンIDがSHELF表に存在するか確認</jp>
            for (int i = 0; i < znRangeArray.length; i++)
            {
                int zoneID = znRangeArray[i].getSoftZoneID();
                if (!check.isExistShelf(zoneID, Shelf.SOFT))
                {
                    loghandle.write("SoftZoneRange", "Shelf Table", check.getMessage());
                    errorFlag = false;
                }
            }

            //<jp>*** 開始バンク・終了バンク、開始ベイ・終了ベイ、開始レベル・終了レベルのチェック(Zone表) ***</jp>
            //<jp> ① 開始バンク・終了バンク、開始ベイ・終了ベイ、開始レベル・終了レベルの値チェック（0以上か？）</jp>
            //<jp> ② 開始バンク・終了バンク、開始ベイ・終了ベイ、開始レベル・終了レベルの大小チェック</jp>
            //<en>*** Check for starting/ending banks, starting/ending bays and starting/ending levels (Zone table) ***</en>
            //<en> 1/ Check the values of starting/ending banks, starting/ending bays and starting/ending levels.</en>
            //<en>    The values must be 0 or greater.</en>
            //<en> 2/ Check the numeric numbers to see if that of starting point is aways smaller than ending point. </en>
            for (int i = 0; i < znRangeArray.length; i++)
            {
                int stbank = znRangeArray[i].getStartBankNo();
                int edbank = znRangeArray[i].getEndBankNo();
                int stbay = znRangeArray[i].getStartBayNo();
                int edbay = znRangeArray[i].getEndBayNo();
                int stlevel = znRangeArray[i].getStartLevelNo();
                int edlevel = znRangeArray[i].getEndLevelNo();

                if (!check.checkBank(stbank, edbank))
                {
                    loghandle.write("SoftZoneRange", "Zone Table", check.getMessage());
                    errorFlag = false;
                }
                if (!check.checkBay(stbay, edbay))
                {
                    loghandle.write("SoftZoneRange", "Zone Table", check.getMessage());
                    errorFlag = false;
                }
                if (!check.checkLevel(stlevel, edlevel))
                {
                    loghandle.write("SoftZoneRange", "Zone Table", check.getMessage());
                    errorFlag = false;
                }
            }

            //<jp>*** ゾーン設定範囲のチェック(Shelf表) ***</jp>
            //<jp>ZONE表の開始バンク・終了バンク、開始ベイ・終了ベイ、開始レベル・終了レベルの範囲チェック</jp>
            //<jp>SHELF表に存在するか確認</jp>
            //<en>*** Check the zone setting range. (Shelf table) ***</en>
            //<en>Check the range of starting/ending banks, starting/ending bays and starting/ending levels </en>
            //<en>in ZONE table. Check to see if they exist in Shelf table.</en>
            for (int i = 0; i < znRangeArray.length; i++)
            {
                //int ZoneID = znArray[i].getSoftZoneID();
                //<jp> 倉庫ステーションNo.</jp>
                //<en> warehouse station no.</en>
                zone.setWareHouseStationNo(znRangeArray[i].getWhStationNo());
                //<jp> 開始バンク</jp>
                //<en> starting bank</en>
                zone.setStartBank(znRangeArray[i].getStartBankNo());
                //<jp> 終了バンク</jp>
                //<en> ending bank</en>
                zone.setEndBank(znRangeArray[i].getEndBankNo());
                //<jp> 開始ベイ</jp>
                //<en> starting bay</en>
                zone.setStartBay(znRangeArray[i].getStartBayNo());
                //<jp> 終了ベイ</jp>
                //<en> ending bay</en>
                zone.setEndBay(znRangeArray[i].getEndBayNo());
                //<jp> 開始レベル</jp>
                //<en> starting level</en>
                zone.setStartLevel(znRangeArray[i].getStartLevelNo());
                //<jp> 終了レベル</jp>
                //<en> ending level</en>
                zone.setEndLevel(znRangeArray[i].getEndLevelNo());

                if (!checkZoneRange(conn, zone))
                {
                    loghandle.write("SoftZoneRange", "Shelf Table", this.getMessage());
                    errorFlag = false;
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());

        }
        return errorFlag;
    }

    /**<jp>
     * パラメータの重複チェック処理を行ないます。
     *＜登録処理の場合＞<BR>
     *・同一ゾーン範囲の設定が、ため打ちデータ内とZONE表に無いことを確認する。<BR>
     *＜修正処理の場合＞<BR>
     *・リストボックスより同一データが選択されていないことを確認する。<BR>
     *・同一ゾーン範囲の設定が、ため打ちデータ内とZONE表に無いことを確認する。<BR>
     *＜削除処理の場合＞<BR>
     *・リストボックスより同一データが選択されていないことを確認する。<BR>
     * パラメータ重複チェックを行い重複したデータが無い場合はtrue、
     * ある場合はfalseを返します。
     * パラメータ重複チェックに失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param conn データベースとのコネクションオブジェクト
     * @param param チェックするパラメータの内容
     * @return パラメータ重複チェックに成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータ重複チェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the parameter duplicate check.
     * < Registration ><BR>
     * -Check to see that the setting of the identical zone range does not exist in entered data summary or in ZONE table.<BR>
     * < Modification ><BR>
     * -Check to see that identical data has not been selected from the listbox.<BR>
     * -Check to see that the setting of the identical zone range does not exist in entered data summary or in ZONE table.<BR>
     * < Deletion ><BR>
     * -Check to see that identical data has not been selected from the listbox.<BR>
     * It checks the duplication of parameter, then returns true if there was no duplicated data or 
     * returns false if there were any duplication.
     * If parameter duplicate check failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn :Connection to connect with database
     * @param param :contents of the parameter to check
     * @return :returns true if the parameter duplicate check has succeeded, or returns false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public boolean duplicationCheck(Connection conn, Parameter param)
            throws ReadWriteException,
                ScheduleException
    {
        Parameter[] mArray = (Parameter[])getParameters();
        SoftZoneRangeParameter mParam = (SoftZoneRangeParameter)param;

        //<jp>同一データチェック</jp>
        //<en>Check the identical data.</en>
        if (isSameData(mParam, mArray))
        {
            return false;
        }

        //<jp>最小範囲チェック</jp>
        //<en>Check the min. range.</en>
        if (isZeroSameData(mParam))
        {
            return false;
        }

        //<jp>ゾーン範囲チェック</jp>
        //<en>Check the zone range.</en>
        if (!checkZoneRange(conn, mParam, mArray))
        {
            return false;
        }

        return true;
    }

    /**<jp>
     * メンテナンス処理を行います。
     * メンテナンス処理の種類は処理区分（getProcessingKind()メソッドより取得）により内部で判断する必要があります。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param conn データベースとのコネクションオブジェクト
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Conduct the maintenance process.
     * It is necessary that type of the maintentace should be determined internally according to
     * the process type (obtained by getProcessingKind() method.)
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn :Connection to connect with database
     * @return :returns true if the process succeeded, or false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    public boolean doStart(Connection conn)
            throws ReadWriteException,
                ScheduleException
    {
        //<jp>処理区分を取得</jp>
        //<en>Retrieve the process type.</en>
        int processingKind = getProcessingKind();

        //<jp>登録</jp>
        //<en>Registration</en>
        if (processingKind == M_CREATE)
        {
            if (!create(conn))
            {
                return false;
            }
            //<jp>6121004 = 編集しました</jp>
            //<en>6121004 = Edited the data.</en>
            setMessage("6121004");
            return true;
        }
        //<jp>処理区分が異常</jp>
        //<en>Error with the process type.</en>
        else
        {
            //<jp> 予期しない値がセットされました。{0} = {1}</jp>
            //<en> Unexpected value has been set.{0} = {1}</en>
            String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
            RmiMsgLogClient.write(msg, (String)this.getClass().getName());
            //<jp> 6127006 = 致命的なエラーが発生しました。ログを参照してください。</jp>
            //<en> 6127006 = Fatal error occurred. Please refer to the log.</en>
            throw new ScheduleException("6126499");
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * このクラスの初期化時に生成した<CODE>ZoneHandler</CODE>インスタンスを取得します。
     * @param conn データベースとのコネクションオブジェクト
     * @return <CODE>ZoneHandler</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>ZoneHandler</CODE> instance generated at the initialization of this class.
     * @param conn :Connection to connect with database
     * @return <CODE>ZoneHandler</CODE>
     </en>*/
    protected ToolSoftZoneRangeHandler getToolSoftZoneRangeHandler(Connection conn)
    {
        return new ToolSoftZoneRangeHandler(conn);
    }

    /**<jp>
     * このクラスの初期化時に生成した<CODE>ToolSoftZoneHandler</CODE>インスタンスを取得します。
     * @param conn データベースとのコネクションオブジェクト
     * @return <CODE>ToolSoftZoneHandler</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>ToolSoftZoneHandler</CODE> instance generated at the initialization of this class.
     * @param conn :Connection to connect with database
     * @return <CODE>ToolSoftZoneHandler</CODE>
     </en>*/
    protected ToolSoftZoneHandler getToolSoftZoneHandler(Connection conn)
    {
        return new ToolSoftZoneHandler(conn);
    }

    /**<jp>
     * メンテナンス修正処理を行います。
     * パラメータ配列のキーとなる項目を元に変更処理を行います。
     * この実装ではZONEINFOの修正は行いません。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     </jp>*/
    /**<en>
     * Process the maintenance modifications.
     * Modification will be made based on the key items of parameter array. 
     * Modification of ZONEINFO will not be done here.
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @return :returns true if the process succeeded, or false if it failed.
     </en>*/
    protected boolean modify()
    {
        return true;
    }

    /**<jp>
     * メンテナンス登録処理を行います。
     * 新規登録のゾーンID登録の場合はZONEINFO表とZONE表に
     * 追加し、追加登録の場合はZONE表にのみ登録を行います。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the maintenance registrations.
     * If registering a new zone ID, append the ID data in ZONEINFO table and ZONE table. 
     * And if it is an additional registration, register only in ZONE table.
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn :Connection to connect with database
     * @return :returns true if the process succeeded, or false if it failed.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    protected boolean create(Connection conn)
            throws ScheduleException
    {
        try
        {
            Parameter[] mArray = (Parameter[])getAllParameters();

            ToolShelfHandler shelfHandle = new ToolShelfHandler(conn);

            if (mArray.length > 0)
            {

                //<jp>表のデータを全部削除！</jp>
                //<en>Delete all data from the table.</en>
                isDropZoneRange(conn);
                isZeroUpdateShelf(conn);

                //<jp>***** SOFTZONERANGE表の更新処理 *****/</jp>
                //<en>***** Update of SOFTZONERANGE table *****/</en>
                SoftZoneRange zone = new SoftZoneRange();
                for (int i = 0; i < mArray.length; i++)
                {
                    SoftZoneRangeParameter castparam = (SoftZoneRangeParameter)mArray[i];

                    String tmpWHStno = castparam.getWareHouseStationNo();
                    int tmpzoneid = castparam.getZoneID();
                    int tmpfbank = castparam.getStartBank();
                    int tmpfbay = castparam.getStartBay();
                    int tmpflevel = castparam.getStartLevel();
                    int tmptbank = castparam.getEndBank();
                    int tmptbay = castparam.getEndBay();
                    int tmptlevel = castparam.getEndLevel();

                    //<jp>***** SHELF表の更新処理 *****/</jp>
                    //<en>***** Update of SHELF table *****/</en>
                    shelfHandle.modifySoftZoneId(tmpfbank, tmpfbay, tmpflevel, tmptbank, tmptbay, tmptlevel, tmpzoneid,
                            tmpWHStno);
                    //<jp>***** SHELF表の更新処理ここまで *****/</jp>
                    //<en>***** Update of SHELF table ends here. *****/</en>

                    //<jp>***** SOFTZONERANGE表の更新処理 *****/</jp>
                    //<en>***** Update of SOFTZONERANGE table *****/</en>
                    zone.setSoftZoneID(tmpzoneid);
                    zone.setWhStationNo(tmpWHStno);
                    zone.setStartBankNo(tmpfbank);
                    zone.setStartBayNo(tmpfbay);
                    zone.setStartLevelNo(tmpflevel);
                    zone.setEndBankNo(tmptbank);
                    zone.setEndBayNo(tmptbay);
                    zone.setEndLevelNo(tmptlevel);
                    getToolSoftZoneRangeHandler(conn).create(zone);
                    //<jp>***** SOFTZONERANGE表の更新処理ここまで *****/</jp>
                    //<en>***** Update of SOFTZONERANGE table ends here. *****/</en>
                }
                //<jp>***** SOFTZONERANGE表の更新処理ここまで *****/</jp>
                //<en>***** Update of SOFTZONERANGE table end here. *****/</en>
                return true;
            }
            //<jp>処理すべきデータが無い場合</jp>
            //<en>If there is no data to process,</en>
            else
            {
                //<jp>表のデータを全部削除！</jp>
                //<en>Delete all data from the table.</en>
                isDropZoneRange(conn);
                isZeroUpdateShelf(conn);
                return true;
            }
        }
        catch (DataExistsException e)
        {
            throw new ScheduleException(e.getMessage());
        }
        catch (ReadWriteException e)
        {
            throw new ScheduleException(e.getMessage());
        }
        catch (NotFoundException e)
        {
            throw new ScheduleException(e.getMessage());
        }
        catch (InvalidDefineException e)
        {
            throw new ScheduleException(e.getMessage());
        }
    }

    /**<jp>
     * メンテナンス削除処理を行います。
     * パラメータ配列のキーとなる項目を元に削除処理を行います。ZONE表のデータが全て削除されたときはそれに付随する
     * ZONEINFO表のデータも削除します。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     </jp>*/
    /**<en>
     * Process the maintenance deletions.
     * Deletion will be done based on the key items of parameter array. If all data is deleted
     * from ZONE table, the corresponding data in ZONEINFO table will be deleted as well.
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @return :returns true if the process succeeded, or false if it failed.
     </en>*/
    protected boolean delete()
    {
        return true;
    }

    // Private methods -----------------------------------------------
    /**<jp>
     * ゾーン設定における、指定棚範囲のチェックを行います。このメソッドでは<br>
     * 1. 開始ベイ   <=  終了ベイ<BR>
     * 2. 開始レベル <=  終了レベル<BR>
     * 3. 倉庫で定義されているベイ、レベルの範囲内であること<BR>
     * のチェックを行います。すべて正しい場合はTrueを返します。
     * @param conn データベースとのコネクションオブジェクト
     * @param param ZoneMaintenanceParameter
     * @return    チェックの結果を返します。すべて正しい場合はTrueを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check the specified location range that has been given in zone setting. 
     * In this method, the following are checked.<br>
     * 1: starting bay is equal to or smaller than ending bay <BR>
     * 2: starting level is equal to or smaller than ending level<BR>
     * Then return true if these are checked no problems.
     * @param conn :Connection to connect with database
     * @param param ZoneMaintenanceParameter
     * @return    :returns the check results. Return True if all are correct.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private boolean checkZoneRange(Connection conn, SoftZoneRangeParameter param)
            throws ReadWriteException
    {
        //<jp> 開始バンク</jp>
        //<en> starting bank</en>
        int stBank = param.getStartBank();
        //<jp> 開始ベイ</jp>
        //<en> starting bay</en>
        int stBay = param.getStartBay();
        //<jp> 開始レベル</jp>
        //<en> starting level</en>
        int stLevel = param.getStartLevel();
        //<jp> 終了バンク</jp>
        //<en> ending bank</en>
        int edBank = param.getEndBank();
        //<jp> 終了ベイ</jp>
        //<en> ending bay</en>
        int edBay = param.getEndBay();
        //<jp> 終了レベル</jp>
        //<en> ending level</en>
        int edLevel = param.getEndLevel();

        //<jp>開始棚バンク、ベイ、レベルは終了棚バンク、ベイ、レベル以下</jp>
        //<en>Ending bank, bay and level should be greater than the starting ones.</en>
        if (stBank <= edBank && stBay <= edBay && stLevel <= edLevel)
        {
            String whstno = param.getWareHouseStationNo();
            int zoneid = param.getZoneID();

            ToolShelfHandler toolshelfHandle = new ToolShelfHandler(conn);
            //<jp>intの配列で倉庫範囲を取得する。</jp>
            //<en>Retrieve the warehouse range in int array.</en>
            int[] range = toolshelfHandle.getShelfRange(whstno, stBank, edBank);
            //<jp>バンク数</jp>
            //<en>Number of banks</en>
            int maxBank = range[0];
            //<jp>ベイ数</jp>
            //<en>Number of bays</en>
            int maxBay = range[1];
            //<jp>レベル数</jp>
            //<en>Number of levels</en>
            int maxLevel = range[2];

            //<jp>指定された範囲が棚範囲内にあることを確認</jp>
            //<en>Check whether/not teh specified range is included in location range.</en>
            if (edBank > maxBank || edBay > maxBay || edLevel > maxLevel)
            {
                //<jp>6123092 = ゾーンID（{0}）は、バンク、ベイ、レベルの範囲が倉庫の範囲を超えています</jp>
                //<en>6123092 = The zone ID ({0}), with its bank/bay/level range, exceeded the range of </en>
                //<en>the warehouse size.</en>
                setMessage("6123092" + wDelim + zoneid);
                return false;
            }
            ToolShelfHandler toolshelfHdle = new ToolShelfHandler(conn);
            if (toolshelfHdle.count(stBank, stBay, stLevel, edBank, edBay, edLevel, whstno) <= 0)
            {
                //<jp>6123204 = 入力された範囲に棚情報が存在しません。</jp>
                //<en>6123204 = There is no location information in input range.</en>
                setMessage("6123204");
                return false;
            }
            return true;
        }
        else
        {
            //<jp>6123076 = 開始棚と終了棚の範囲が不正です。開始棚は終了棚以下の値を設定してください。</jp>
            //<en>6123076 = The range of strating location and ending location is invalid;</en>
            //<en>Please set the greater value for ending location than the starting location.</en>
            setMessage("6123076");
            return false;
        }
    }

    /**<jp>
     * ため打ちデータ内とZONE表に重複したゾーン範囲の有無を確認します。
     * すでに、重複したゾーン範囲で設定されたデータがある場合はfalseを返します。
     * @param conn データベースとのコネクションオブジェクト
     * @param param  今回追加するパラメータ
     * @param array  ため打ちデータ
     * @return    重複範囲で設定されたデータが存在する場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check whether/not there is the zone range overlapping the entered data summary and 
     * ZONE table.
     * Return false if data is found which has been set over duplicate zone range.
     * @param conn :Connection to connect with database
     * @param param :the parameter which will be appended in this process
     * @param array :entered data summary (pool)
     * @return      :returns false if there is data which has been set over duplicate zone range.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private boolean checkZoneRange(Connection conn, SoftZoneRangeParameter param, Parameter[] array)
            throws ReadWriteException
    {
        System.out.println(">> SoftZoneRangeCreater.java  checkZoneRange(Connection conn, ZoneParameter param, Parameter[] array)");

        int zoneID = param.getZoneID();
        String whStationNo = param.getWareHouseStationNo();

        //<jp> 開始バンク</jp>
        //<en> starting bank</en>
        int stBank = param.getStartBank() - 1;
        //<jp> 開始ベイ</jp>
        //<en> starting bay</en>
        int stBay = param.getStartBay() - 1;
        //<jp> 開始レベル</jp>
        //<en> starting level</en>
        int stLevel = param.getStartLevel() - 1;
        //<jp> 終了バンク</jp>
        //<en> ending bank</en>
        int edBank = param.getEndBank() - 1;
        //<jp> 終了ベイ</jp>
        //<en> ending bay</en>
        int edBay = param.getEndBay() - 1;
        //<jp> 終了レベル</jp>
        //<en> ending level</en>
        int edLevel = param.getEndLevel() - 1;

        int x;
        int y;
        int z;
        int x_min;
        int x_max;
        int y_min;
        int y_max;
        int z_min;
        int z_max;
        int zone_id;

        //<jp>*** ため打ちデータとのチェック ***</jp>
        //<jp>ため打ちデータが存在する場合</jp>
        //<en>*** Check with entered data summary. ***</en>
        //<en>If there is the entered data summary,</en>
        if (array.length > 0)
        {
            Rectangle r01 = new Rectangle(stBank, stLevel, edBank - stBank + 1, edLevel - stLevel + 1);
            Rectangle r02 = new Rectangle(stBay, stLevel, edBay - stBay + 1, edLevel - stLevel + 1);
            Rectangle r11;
            Rectangle r12;

            if (false)
            {
                ToolShelfHandler toolshelfHandle = new ToolShelfHandler(conn);
                //<jp>intの配列で倉庫範囲を取得する。</jp>
                //<en>Get the warehouse range using the int array.</en>
                int[] range = toolshelfHandle.getShelfRange(whStationNo, stBank, edBank);
                //<jp>バンク数</jp>
                //<en>Number of banks</en>
                int maxBank = range[0];
                //<jp>ベイ数</jp>
                //<en>Number of bays</en>
                int maxBay = range[1];
                //<jp>レベル数</jp>
                //<en>Number of levels</en>
                int maxLevel = range[2];

                int[][][] shf = new int[maxBank + 1][maxBay][maxLevel];
                for (x = 0; x < maxBank; ++x)
                {
                    for (y = 0; y < maxBay; ++y)
                    {
                        for (z = 0; z < maxLevel; ++z)
                        {
                            shf[x][y][z] = 0;
                        }
                    }
                }

                for (int i = 0; i < array.length; i++)
                {
                    SoftZoneRangeParameter castparam = (SoftZoneRangeParameter)array[i];
                    //<jp>*** 範囲の確認 ***</jp>
                    //<en>*** Check the range. ***</en>
                    if (whStationNo.equals(castparam.getWareHouseStationNo()))
                    {
                        x_min = castparam.getStartBank() - 1;
                        x_max = castparam.getEndBank() - 1;
                        y_min = castparam.getStartBay() - 1;
                        y_max = castparam.getEndBay() - 1;
                        z_min = castparam.getStartLevel() - 1;
                        z_max = castparam.getEndLevel() - 1;

                        for (x = x_min; x <= x_max; ++x)
                        {
                            for (y = y_min; y <= y_max; ++y)
                            {
                                for (z = z_min; z <= z_max; ++z)
                                {
                                    shf[x][y][z] = zone_id;
                                }
                            }
                        }
                    }
                }

                for (x = stBank; x <= edBank; ++x)
                {
                    for (y = stBay; y <= edBay; ++y)
                    {
                        for (z = stLevel; z <= edLevel; ++z)
                        {
                            if (shf[x][y][z] != 0)
                            {
                                //<jp>6123077 = ゾーン範囲が重複しているため設定できません</jp>
                                //<en>6123077 = Cannot set; the zone range overlaps.</en>
                                setMessage("6123077");
                                System.out.println("<< SoftZoneRangeCreater.java  checkZoneRange(Connection conn, ZoneParameter param, Parameter[] array)  !001");
                                return false;
                            }
                        }
                    }
                }
            }
            else
            {
                for (int i = 0; i < array.length; i++)
                {
                    SoftZoneRangeParameter castparam = (SoftZoneRangeParameter)array[i];
                    //<jp>*** 範囲の確認 ***</jp>
                    //<jp>同一の倉庫</jp>
                    //<en>*** Check the range. ***</en>
                    //<en>Identical warehouses</en>
                    if (whStationNo.equals(castparam.getWareHouseStationNo()))
                    {
                        x_min = castparam.getStartBank() - 1;
                        x_max = castparam.getEndBank() - 1;
                        y_min = castparam.getStartBay() - 1;
                        y_max = castparam.getEndBay() - 1;
                        z_min = castparam.getStartLevel() - 1;
                        z_max = castparam.getEndLevel() - 1;

                        r11 = new Rectangle(x_min, z_min, x_max - x_min + 1, z_max - z_min + 1);
                        r12 = new Rectangle(y_min, z_min, y_max - y_min + 1, z_max - z_min + 1);

                        if (r01.intersects(r11) && r02.intersects(r12))
                        {
                            //<jp>6123077 = ゾーン範囲が重複しているため設定できません</jp>
                            //<en>6123077 = Cannot set; the zone range overlaps.</en>
                            setMessage("6123077");
                            return false;
                        }
                    }
                }
            }

            if (false)
            {
                for (int i = 0; i < array.length; i++)
                {
                    SoftZoneRangeParameter castparam = (SoftZoneRangeParameter)array[i];
                    //<jp>*** 範囲の確認 ***</jp>
                    //<jp>同一の倉庫</jp>
                    //<en>*** Check the range.***</en>
                    //<en>Identical warehouses</en>
                    if (whStationNo.equals(castparam.getWareHouseStationNo()))
                    {
                        if (zoneID != castparam.getZoneID())
                        {
                            System.out.println("(SoftZoneRangeCreater.java):ZONE CHECK  zoneID=" + zoneID
                                    + ",castparam.getZoneID()=" + castparam.getZoneID());
                            System.out.println("(SoftZoneRangeCreater.java):BANK CHECK  stBank=" + stBank + ",edBank="
                                    + edBank + ",castparam.getStartBank()=" + castparam.getStartBank()
                                    + ",castparam.getEndBank()=" + castparam.getEndBank());
                            //<jp>バンク方向が範囲に含まれる</jp>
                            //<en>Direction of banks is included in the range.</en>
                            if ((stBank <= castparam.getStartBank() && edBank >= castparam.getStartBank())
                                    || (stBank <= castparam.getEndBank() && edBank >= castparam.getEndBank()))
                            {
                                System.out.println("(SoftZoneRangeCreater.java):BAY CHECK   stBay=" + stBay + ",edBay="
                                        + edBay + ",castparam.getStartBay()=" + castparam.getStartBay()
                                        + ",castparam.getEndBay()=" + castparam.getEndBay());
                                //<jp>ベイ方向が範囲に含まれる</jp>
                                //<en>Direction of bays is included in the range.</en>
                                if ((stBay <= castparam.getStartBay() && edBay >= castparam.getStartBay())
                                        || (stBay <= castparam.getEndBay() && edBay >= castparam.getEndBay()))
                                {
                                    System.out.println("(SoftZoneRangeCreater.java):LEVEL CHECK stLevel=" + stLevel
                                            + ",edLevel=" + edLevel + ",castparam.getStartLevel()="
                                            + castparam.getStartLevel() + ",castparam.getEndLevel()="
                                            + castparam.getEndLevel());
                                    //<jp>レベル方向が範囲に含まれる</jp>
                                    //<en>Direction of levels is included in the range.</en>
                                    if ((stLevel <= castparam.getStartLevel() && edLevel >= castparam.getStartLevel())
                                            || (stLevel <= castparam.getEndLevel() && edLevel >= castparam.getEndLevel()))
                                    {
                                        //<jp>6123077 = ゾーン範囲が重複しているため設定できません</jp>
                                        //<en>6123077 = Cannot set; the zone range overlaps.</en>
                                        setMessage("6123077");

                                        System.out.println("<< SoftZoneRangeCreater.java  checkZoneRange(Connection conn, ZoneParameter param, Parameter[] array)  !001");
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("<< SoftZoneRangeCreater.java  checkZoneRange(Connection conn, ZoneParameter param, Parameter[] array)  !002");
        return true;
    }

    /**<jp>
     * リストボックスより編集するデータを選択するときに、同一のデータが選択されたことを
     * 確認するためのチェックを実装します。ゾーンメンテでは、
     * 追加するパラメータのシリアルナンバーがため打ちデータ内に存在するかを確認します。
     * @param param 今回追加するパラメータ
     * @param array ため打ちデータ
     * @return    同一データが存在する場合Trueを返します。
     </jp>*/
    /**<en>
     * Implement the check in order to see that the identical data has been selected when chosing 
     * data from the list box to edit.
     * In zone maintenance process, it checks whether/not the serial no. of appending parameter
     * exists in the entered data summary.
     * @param param :the parameter which will be appended in this process
     * @param array :entered data summary (pool)
     * @return      :return true if identical data exists.
     </en>*/
    private boolean isSameData(SoftZoneRangeParameter param, Parameter[] array)
    {
        return false;
    }

    /**<jp>
     * Zoneインスタンスを取得します。
     * @return <code>Zone</code>オブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve the Zone isntance.
     * @param conn :Connection to connect with database
     * @return :the array of <code>Zone</code> object
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private SoftZoneRange[] getSoftZoneRangeArray(Connection conn)
            throws ReadWriteException
    {
        ToolSoftZoneRangeSearchKey softzoneKey = new ToolSoftZoneRangeSearchKey();
        softzoneKey.setWHStationNoOrder(1, true);
        softzoneKey.setSoftZoneIDOrder(2, true);
        //<jp>*** zoneインスタンスを取得 ***</jp>
        //<en>*** Retrieve the zone isntance ***</en>
        SoftZoneRange[] array = (SoftZoneRange[])getToolSoftZoneRangeHandler(conn).find(softzoneKey);
        return array;
    }

    /**<jp>
     * SOFTZONE表のデータを削除します。
     * @param conn データベースとのコネクションオブジェクト
     * @return   削除成功時はtrue、削除失敗時はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 削除データが存在しなかった場合、通知されます。
     </jp>*/
    /**<en>
     * Delete data from SOFTZONE table.
     * @param conn :Connection to connect with database
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException :
     </en>*/
    private void isDropZoneRange(Connection conn)
            throws ReadWriteException,
                NotFoundException
    {
        wZoneKey = new ToolSoftZoneRangeSearchKey();
        // 削除行が存在する場合のみdropメソッドを呼び出す
        if (getToolSoftZoneRangeHandler(conn).count(wZoneKey) > 0)
        {
            getToolSoftZoneRangeHandler(conn).drop(wZoneKey);
        }
    }

    /**<jp>
     * SHELF表のソフトゾーン項目をすべて'0'に更新します。
     * @param conn データベースとのコネクションオブジェクト
     * @return   更新成功時はtrue、更新失敗時はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFountdException 更新データが無かった場合に通知されます。
     * @throws InvalidDefineException 
     </jp>*/
    /**<en>
     * Update the all soft zone items in SHELF table to '0'.
     * @param conn :Connection to connect with database
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException :
     * @throws InvalidDefineException :
     </en>*/
    private void isZeroUpdateShelf(Connection conn)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        ToolShelfHandler toolshelfHandle = new ToolShelfHandler(conn);
        // 削除行が存在する場合のみdropメソッドを呼び出す
        ToolShelfSearchKey key = new ToolShelfSearchKey();
        if (toolshelfHandle.count(key) > 0)
        {
            //<jp> Shelf表のハードゾーン項目に0をセット</jp>
            //<en> Set 0 for hard zone items in Shelf table.</en>
            toolshelfHandle.modifySoftZoneId(0);
        }
    }

    /**<jp>
     * バンク・ベイ・レベルの入力値が0以上であるかのチェックを実装します。
     * @param param 今回追加するパラメータ
     * @return    0以下のデータが存在する場合Trueを返します。
     </jp>*/
    /**<en>
     * Implements the checks to see if the input value for bank/bay/level is 0 or greater.
     * @param param  :the parameter which will be appended in this process
     * @return       :return true if the data less than 0 is found.
     </en>*/
    private boolean isZeroSameData(SoftZoneRangeParameter param)
    {
        if (param.getStartBank() <= 0 || param.getStartBay() <= 0 || param.getStartLevel() <= 0
                || param.getEndBank() <= 0 || param.getEndBay() <= 0 || param.getEndLevel() <= 0)
        {
            //<jp> 6123071 = 範囲には１以上の値を指定してください</jp>
            //<en> 6123071 = Please specify values which is greater than 1 (inclusive) for the range.</en>
            setMessage("6123071");
            return true;
        }
        return false;
    }
}
//end of class


