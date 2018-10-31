// $Id: AisleCreater.java 5301 2009-10-28 05:36:02Z ota $
package jp.co.daifuku.wms.asrs.tool.schedule;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.sql.Connection;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ScheduleInterface;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.tool.common.LogHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolBankHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Aisle;
import jp.co.daifuku.wms.asrs.tool.location.Bank;
import jp.co.daifuku.wms.asrs.tool.location.Shelf;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;

/**<jp>
 * アイル設定を行なうクラスです。
 * AbstractCreaterを継承し、倉庫設定に必要な処理を実装します。
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/11</TD><TD>okamura</TD><TD>ソフトゾーンIDにセットする値の変更。<BR>
 * ソフトゾーンIDに"0"をセットしていた個所を、"-1"をセットするように変更しました。
 * <TR><TD></TD><TD></TD><TD>整合性チェックを追加。<BR>
 * ソフトゾーンIDが設定されていない棚があった場合は不可にするように変更しました。
 * </TD></TR>
 * <TR><TD>2003/12/16</TD><TD>okamura</TD><TD>変更、削除可能な条件を変更。<BR>
 * SHELFにゾーンIDがセットされていても変更、削除可能なように変更。
 * </TD></TR>
 * <TR><TD></TD><TD></TD><TD>不要なメソッド削除<BR>
 * 使われていないsetEndLocation、setStartLocationメソッド削除。<BR>
 * 不要なimportも削除
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5301 $, $Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class processes the setting of the aisle.
 * It inherits the AbstractCreater and implements rewuried process to set the warehouse.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/11</TD><TD>okamura</TD><TD>Changed the value to set as soft zone IDs.<BR>
 * Modified the former process of setting "0" to current "-1" as soft zone ID.
 * <TR><TD></TD><TD></TD><TD>Added the inconsistency checks.<BR>
 * If the soft zone ID has not been set to the location, the inconsistency check will 
 * result unacceptable due to this change.
 * </TD></TR>
 * <TR><TD>2003/12/16</TD><TD>okamura</TD><TD>Changed the modifiable/deletabl conditions.<BR>
 * Correted so that the conditions are modifiable/deletable even if the zone ID us set in SHELF.
 * </TD></TR>
 * <TR><TD></TD><TD></TD><TD>Deleted the nurequired method.<BR>
 * Deleted the setEndLocation and setStartLocation methods that are no longer in use.<BR>
 * Also deleted import that is not necessary.
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5301 $, $Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $
 * @author  $Author: ota $
 </en>*/
public class AisleCreater
        extends AbstractCreater
{
    // Class fields --------------------------------------------------
    // Class variables -----------------------------------------------
    /**<jp>
     * 製番フォルダ
     </jp>*/
    /**<en>
     * Product no. folder
     </en>*/
    public String wFilePath = "";

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
        return ("$Revision: 5301 $,$Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 指定された位置のパラメータを削除します。<BR>
     * @param index 削除するパラメータ位置
     * @throws ScheduleException 指定された位置にパラメータが存在しない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete the parameter of the specified position. <BR>
     * @param conn Connection Object
     * @param index :position of the parameter to delete
     * @throws ScheduleException :Notifies if there are no parameters in specified position.
     </en>*/
    public void removeParameter(Connection conn, int index)
            throws ScheduleException
    {
        //<jp> メッセージの初期化</jp>
        //<en> Initialization of the messages</en>
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
     * 全てのパラメータを削除します。<BR>
     * @throws ScheduleException 指定された位置にパラメータが存在しない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete all parameters.<BR>
     * @param conn Connection Object
     * @throws ScheduleException :Notifies if there are no parameters in specified position.
     </en>*/
    public void removeAllParameters(Connection conn)
            throws ScheduleException
    {
        //<jp> メッセージの初期化</jp>
        //<en> Initialization of the messages</en>
        setMessage("");

        wParamVec.removeAllElements();
        //<jp>削除しました</jp>
        //<en>Deleted.</en>
        setMessage("6121003");
    }

    /**<jp>
     * このクラスの初期化を行ないます。初期化時に<CODE>ReStoringHandler</CODE>のインスタンス生成を行います。
     * @param conn データベースとのコネクションオブジェクト
     * @param kind 処理区分
     </jp>*/
    /**<en>
     * Initialize this class. Generate the <CODE>ReStoringHandler</CODE> instance at the initialization.
     * @param conn :connection object with database
     * @param kind :process type
     </en>*/
    public AisleCreater(Connection conn, int kind)
    {
        super(conn, kind);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 画面から印刷発行ボタンが押下された場合の処理を実装します。<BR>
     * @param <code>Locale</code> オブジェクト
     * @param listParam スケジュールパラメータ
     * @return 印刷処理の結果
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Implements the process to run when the print-issue button was pressed on the display.<BR>
     * @param locale object
     * @param listParam :schedule parameters
     * @return :result print job
     </en>*/
    public boolean print(Locale locale, Parameter listParam)
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
     * Retrieve the data to display on the screen.<BR>
     * @param conn Connection Object
     * @param locale object
     * @param searchParam :sesarch conditions
     * @return :the array of the schedule parameters
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
     </en>*/
    public Parameter[] query(Connection conn, Locale locale, Parameter searchParam)
            throws ReadWriteException,
                ScheduleException
    {
        Aisle[] array = getAisleArray(conn);
        //<jp>一時的にデータを格納するVector</jp>
        //<en>:Vector where the data will temporarily be stored</en>
        //<jp>ため打ちの最大件数を初期値としてセット</jp>
        //<en>Set the max. data to enter in entered data summary as initial value.</en>
        Vector vec = new Vector(100);
        //<jp>表示用のエンティティクラス</jp>
        //<en>entity class to display</en>
        AisleParameter dispData = null;
        //<jp>範囲</jp>
        //<en>range</en>
        int[] range = new int[2];
        //<jp>他のメソッドではファイル名を外部から渡すことができないので</jp>
        //<jp>ここでセットする。</jp>
        //<en>Set the file name here as the file names cannot be passed from external.</en>
        wFilePath = ((AisleParameter)searchParam).getFilePath();

        if (array.length > 0)
        {
            for (int i = 0; i < array.length; i++)
            {
                dispData = new AisleParameter();
                String warehouseStationNo = array[i].getWarehouseStationNo();
                int warehouseNo = getFindUtil(conn).getWarehouseNumber(warehouseStationNo);
                dispData.setWarehouseNumber(warehouseNo);
                dispData.setAisleStationNo(array[i].getAisleStationNo());
                dispData.setAisleNumber(array[i].getAisleNo());

                GroupController gc = array[i].getGroupController();
                dispData.setAGCNumber(gc.getNumber());
                //<jp>Bank範囲</jp>
                //<en>Bank range</en>
                range = getToolShelfHandler(conn).getBankRange(warehouseStationNo, array[i].getAisleStationNo());
                dispData.setSBank(range[0]);
                dispData.setEBank(range[1]);

                //<jp>BAY範囲</jp>
                //<en>BAY range</en>
                range = getToolShelfHandler(conn).getBayRange(warehouseStationNo, array[i].getAisleStationNo());
                dispData.setSBay(range[0]);
                dispData.setEBay(range[1]);

                //<jp>LEVEL範囲</jp>
                //<en>LEVEL range</en>
                range = getToolShelfHandler(conn).getLevelRange(warehouseStationNo, array[i].getAisleStationNo());
                dispData.setSLevel(range[0]);
                dispData.setELevel(range[1]);

                //アイル位置
                if (array[i].getDoubleDeepKind() == Aisle.DOUBLE_DEEP)
                {
                    range = getToolShelfHandler(conn).getAislePostion(warehouseStationNo, array[i].getAisleStationNo());
                    dispData.setSAislePosition(range[0]);
                    dispData.setEAislePosition(range[1]);
                }
                dispData.setMaxCarry(array[i].getMaxCarry());
                
                vec.addElement(dispData);
            }
            AisleParameter[] fmt = new AisleParameter[vec.size()];

            vec.toArray(fmt);
            return fmt;
        }

        return null;
    }

    /**<jp>
     * パラメータチェック処理を行ないます。パラメータ追加時、メンテナンス処理を実行する前に呼ばれます。
     * パラメータに異常があった場合、その詳細を<code>getMessage</code>で取得できます。
     * @param param チェックするパラメータ
     * @return パラメータに異常が無い場合はtrue、異常がある場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Processes the paramter check. It will be called when adding the parameter, before the execution 
     * of maintenance process.
     * If parameter check failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn Connection Object
     * @param param :parameter to check
     * @return :returns true if there is no error with parameter, or returns false if there are any errors.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
     </en>*/
    public boolean check(Connection conn, Parameter param)
            throws ReadWriteException,
                ScheduleException
    {
        ToolCommonChecker check = new ToolCommonChecker(conn);
        AisleParameter mParameter = (AisleParameter)param;
        //<jp>処理区分を取得</jp>
        //<en>Retrieve the process type.</en>
        int processingKind = getProcessingKind();
        //<jp>登録</jp>
        //<en>Registeration</en>
        if (processingKind == M_CREATE)
        {
            //<jp> 倉庫表に登録されているかチェック</jp>
            //<en> Check to see if the data is registered in the warehouse table.</en>
            ToolWarehouseHandler warehousehandle = new ToolWarehouseHandler(conn);
            ToolWarehouseSearchKey warehosuekey = new ToolWarehouseSearchKey();

            if (warehousehandle.count(warehosuekey) <= 0)
            {
                //<jp> 6123117 = 倉庫管理情報がありません。倉庫設定で登録してください</jp>
                //<en> 6123117 = There is no warehous control data. Please register in the setting </en>
                //<en> display of warehouses.</en>
                setMessage("6123117");
                return false;
            }

            // システム定義チェック
            if (WmsParam.ALL_STATION.equals(mParameter.getAisleStationNo()))
            {
                // 6023222=入力された{0}はシステムで使用しているため登録できません。
                setMessage(WmsMessageFormatter.format(6023222, DisplayText.getText("LBL-W0303")));
                return false;
            }

            //<jp>アイルNoのチェック</jp>
            //<en>Check the aisle no.</en>
            if (!check.checkAisleNo(mParameter.getAisleNumber()))
            {
                //<jp>異常内容をセットする</jp>
                //<en>Set the contents of error.</en>
                setMessage(check.getMessage());
                return false;
            }

            //<jp> GROUPCONTROLLER表に登録されているかチェック</jp>
            //<en> Check to see of the data is registered in GROUPCONTROLLER table.</en>
            ToolGroupControllerHandler gchandle = new ToolGroupControllerHandler(conn);
            ToolGroupControllerSearchKey gckey = new ToolGroupControllerSearchKey();

            if (gchandle.count(gckey) <= 0)
            {
                //<jp> グループコントローラ情報がありません。グループコントローラ設定画面で登録してください</jp>
                //<en> There is no group controller data. Please register in the setting diplay of </en>
                //<en> group controller.</en>
                setMessage("6123078");
                return false;
            }

            //<jp>バンクのチェック</jp>
            //<en>Check the bank.</en>
            if (!check.checkBank(mParameter.getSBank(), mParameter.getEBank()))
            {
                //<jp>異常内容をセットする</jp>
                //<en>Set the contents of error.</en>
                setMessage(check.getMessage());
                return false;
            }
            //<jp>ベイのチェック</jp>
            //<en>Check the bay.</en>
            if (!check.checkBay(mParameter.getSBay(), mParameter.getEBay()))
            {
                //<jp>異常内容をセットする</jp>
                //<en>Set the contents of error.</en>
                setMessage(check.getMessage());
                return false;
            }
            //<jp>レベルのチェック</jp>
            //<en>Check the level.</en>
            if (!check.checkLevel(mParameter.getSLevel(), mParameter.getELevel()))
            {
                //<jp>異常内容をセットする</jp>
                //<en>Set the contents of error.</en>
                setMessage(check.getMessage());
                return false;
            }

            //バンク範囲のチェック
            if (mParameter.getEBank() - mParameter.getSBank() > 3)
            {
                //6123279=バンク範囲には4バンク以上設定することはできません。
                setMessage("6123279");
                return false;
            }

            //ダブルディープの場合
            if (isDoubleDeep(mParameter.getSBank(), mParameter.getEBank()))
            {
                //<jp>アイル位置のチェック</jp>
                //<en>Check the aisle position.</en>
                if (!check.checkAislePosition(mParameter.getSAislePosition(), mParameter.getEAislePosition()))
                {
                    //<jp>異常内容をセットする</jp>
                    //<en>Set the contents of error.</en>
                    setMessage(check.getMessage());
                    return false;
                }

                //アイル位置がバンク範囲内に設定されているかをチェック
                if (mParameter.getSAislePosition() < mParameter.getSBank()
                        || mParameter.getSAislePosition() > mParameter.getEBank())
                {
                    //6123281=アイル位置の入力が不正です。
                    setMessage("6123281");
                    return false;
                }
                if (mParameter.getEAislePosition() < mParameter.getSBank()
                        || mParameter.getEAislePosition() > mParameter.getEBank())
                {
                    //6123281=アイル位置の入力が不正です。
                    setMessage("6123281");
                    return false;
                }

                if (mParameter.getSAislePosition() - mParameter.getSBank() > 1)
                {
                    //6123281=アイル位置の入力が不正です。
                    setMessage("6123281");
                    return false;
                }

                if (mParameter.getEBank() - mParameter.getEAislePosition() > 1)
                {
                    //6123281=アイル位置の入力が不正です。
                    setMessage("6123281");
                    return false;
                }
            }

            return true;
        }
        //<jp>処理区分が異常</jp>
        //<en>Error in process type.</en>
        else
        {
            //<jp> 予期しない値がセットされました。{0} = {1}</jp>
            //<en> Unexpected value has been set.{0} = {1}</en>
            String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
            RmiMsgLogClient.write(msg, (String)this.getClass().getName());
            //<jp> 6126499 = 致命的なエラーが発生しました。ログを参照してください。</jp>
            //<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
            throw new ScheduleException("6126499");
        }
    }

    /**<jp>
     * 整合性チェック処理を行ないます。eAWC環境設定ツールのジェネレート時に呼ばれます。
     * 異常があった場合、その詳細をファイルへ書き込みます。
     * @param logpath 異常が発生したときのログを書き込むファイルを置くためのパス
     * @param locale <code>Locale</code>オブジェクト
     * @return 異常が無い場合はtrue、一つでも異常がある場合はfalseを返します。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the inconsistency check. This will be called when generating the setting tool for eAWC.
     * If there are any errors, the detail will be written into the file.
     * @param logpath :path to place the file to erite the log when error occurred.
     * @param locale <code>Locale</code>object
     * @return :returns true if there are no errors, or false if there is 1 or more errors.
     * @throws ScheduleException :Notifies if unexpected error occurred during the parameter check.
     </en>*/
    public boolean consistencyCheck(Connection conn, String logpath, Locale locale)
            throws ScheduleException
    {
        ToolCommonChecker check = new ToolCommonChecker(conn);
        //<jp>エラーが無い場合にtrueとなります。</jp>
        //<en>True will be given if there is no error.</en>
        boolean errorFlag = true;
        String logfile = logpath + "/" + ToolParam.getParam("CONSTRAINT_CHECK_FILE");

        try
        {
            LogHandler loghandle = new LogHandler(logfile, locale);

            ToolAisleSearchKey aisleKey = new ToolAisleSearchKey();
            Aisle[] aisleArray = (Aisle[])getToolAisleHandler(conn).find(aisleKey);
            GroupController controller = null;

            //<jp>Aisleが設定されていない場合</jp>
            //<en>In case the Aisle has not been set:</en>
            if (aisleArray.length == 0)
            {
                //<jp>6123179 = アイルが設定されていません</jp>
                //<en>6123179 = The aisle has not been set.</en>
                loghandle.write("Aisle", "Aisle Table", "6123179");
                //<jp>アイルが設定されていない場合は、後のチェックを行わずに抜ける</jp>
                //<en>If the aisle has not been set, it discontinue the check and exits the process.</en>                
                return false;
            }

            for (int i = 0; i < aisleArray.length; i++)
            {
                //<jp>*** グループコントローラNoのチェック(Aisle表) ***</jp>
                //<jp>AISLE表のグループコントローラNoがグループコントローラ表に存在するか確認</jp>
                //<en>*** Check for the group controller no. (in Aisle table) ***</en>
                //<en>Check to see if the group controller no. in the AISLE table exists in </en>
                //<en>the group controller table.</en>
                controller = aisleArray[i].getGroupController();
                if (!check.isExistControllerNo(controller.getNumber()))
                {
                    loghandle.write("Aisle", "Aisle Table", check.getMessage());
                    errorFlag = false;
                }

                //<jp>*** 倉庫ステーションNoのチェック(Aisle表) ***</jp>
                //<jp>AISLE表の倉庫ステーションNoがWAREHOUSE表とSTATIONTYPE表に存在するか確認</jp>
                //<en>*** Check for warehouse station no. (in Aisle table) ***</en>
                //<en>Check to see if the warehouse station no. in the AISLE table exists in </en>
                //<en>the WAREHOUSE table and the STATIONTYPE table.</en>
                String warehouseStationNo = aisleArray[i].getWarehouseStationNo();
                if (!check.isExistAutoWarehouseStationNo(warehouseStationNo))
                {
                    loghandle.write("Aisle", "Aisle Table", check.getMessage());
                    errorFlag = false;
                }

                if (!check.isExistStationType(warehouseStationNo))
                {
                    loghandle.write("Aisle", "Aisle Table", check.getMessage());
                    errorFlag = false;
                }

            }

            //<jp>*** Shelf表チェック ***</jp>
            // アイル毎にチェックを行う
            String[] shelfAilesArray = (String[])getToolShelfHandler(conn).findAisles(null);
            for (int i = 0; i < shelfAilesArray.length; i++)
            {
                ToolShelfSearchKey shelfKey = new ToolShelfSearchKey();
                shelfKey.setParentStationNo(shelfAilesArray[i]);
                Shelf[] shelfArray = (Shelf[])getToolShelfHandler(conn).find(shelfKey);
                String Last_warehouseStationNo = "";
                String Last_parentStationNo = "";
                for (int j = 0; j < shelfArray.length; j++)
                {
                    //<jp>*** 倉庫ステーションNoのチェック(Shelf表) ***</jp>
                    //<jp>SHELF表の倉庫ステーションNoがWAREHOUSE表に存在するか確認</jp>
                    //<en>Check to see if the warehouse station no. in the SHELF table exists in </en>
                    //<en>the WAREHOUSE table.</en>
                    String warehouseStationNo = shelfArray[j].getWarehouseStationNo();
                    if (!Last_warehouseStationNo.equals(warehouseStationNo))
                    {
                        if (!check.isExistWarehouseStationNo(warehouseStationNo))
                        {
                            loghandle.write("Aisle", "Shelf Table", check.getMessage());
                            errorFlag = false;
                        }
                        if (!check.isExistStationType(warehouseStationNo))
                        {
                            loghandle.write("Aisle", "Shelf Table warehouse", check.getMessage());
                            errorFlag = false;
                        }
                        Last_warehouseStationNo = warehouseStationNo;
                    }
                    //<jp>*** アイルステーションNoのチェック(Shelf表) ***</jp>
                    //<en>*** Check for the aisle station no. (in Shelf table) ***</en>
                    //<jp>Shelf表の親ステーションNoがアイル表に存在するか確認</jp>
                    //<en>Check to see if the parent station no. in the Shelf table exists in </en>
                    //<en>the Aisle table.</en>
                    String parentStationNo = shelfArray[j].getParentStationNo();
                    if (!Last_parentStationNo.equals(parentStationNo))
                    {
                        if (!check.isExistAisleStationNo(parentStationNo))
                        {
                            loghandle.write("Aisle", "Shelf Table", check.getMessage());
                            errorFlag = false;
                        }
                        if (!check.isExistStationType(parentStationNo))
                        {
                            loghandle.write("Aisle", "Shelf Table", check.getMessage());
                            errorFlag = false;
                        }
                        Last_parentStationNo = parentStationNo;
                    }
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
     * 同一アイルステーションNoと同一アイルNo.のデータはため打ちデータに登録できません。
     * パラメータ重複チェックを行い重複したデータが無い場合はtrue、
     * ある場合はfalseを返します。
     * パラメータ重複チェックに失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param param チェックするパラメータ
     * @return パラメータ重複チェックに成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータ重複チェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Processes the parameter duplicate check.
     * Data of identicla aisle station no. and aisle no. cannot be registered in entered data summary.
     * It checks the duplication of parameter, then returns true if there was no duplicated data or 
     * returns false if there were any duplication.
     * If parameter duplicate check failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn Connection Object
     * @param param :parameter to check
     * @return :returns true if the parameter duplicate check has succeeded, or returns false if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public boolean duplicationCheck(Connection conn, Parameter param)
            throws ReadWriteException,
                ScheduleException
    {

        Parameter[] mArray = (Parameter[])getParameters();
        AisleParameter mParam = (AisleParameter)param;
        //<jp>同一データチェック</jp>
        //<en>Identical data check</en>
        if (isSameData(mParam, mArray))
        {
            return false;
        }
        String stationNo = mParam.getAisleStationNo();

        if (getToolAisleHandler(conn).isStationType(stationNo, ToolAisleHandler.AISLE_HANDLE))
        {
            //<jp>6123122 すでに登録されています。同一ステーションNoを登録することはできません。</jp>
            //<en>6123122 The data is already registered. Cannot register teh identical station no.</en>
            setMessage("6123122");
            return false;
        }

        //<jp>修正の時のみチェックする。</jp>
        //<en>Check only when modifing the data.</en>
        if (getUpdatingFlag() != ScheduleInterface.NO_UPDATING)
        {
            //<jp>*** 修正の時、キー項目は変更不可とする ***</jp>
            //<en>*** In the modification process, key items are regarded fixed and not able to change. ***</en>
            int warehouseNo = mParam.getWarehouseNumber();
            int aisleNo = mParam.getAisleNumber();
            int gcNo = mParam.getAGCNumber();

            //<jp>ため打ちの中のキー項目</jp>
            //<en>Key items in the entered data summary</en>
            int orgWarehouseNo = 0;
            String orgAisleStationNo = "";
            int orgAisleNo = 0;
            int orgGcNo = 0;


            Parameter[] mAllArray = (Parameter[])getAllParameters();
            for (int i = 0; i < mAllArray.length; i++)
            {
                AisleParameter castparam = (AisleParameter)mAllArray[i];
                //<jp>キー項目</jp>
                //<en>Key items</en>
                orgWarehouseNo = castparam.getWarehouseNumber();
                orgAisleStationNo = castparam.getAisleStationNo();
                orgAisleNo = castparam.getAisleNumber();
                orgGcNo = castparam.getAGCNumber();
                //<jp>変更されていないのでOK</jp>
                //<en>Accetable as they are not modified.</en>
                if (warehouseNo == orgWarehouseNo && stationNo.equals(orgAisleStationNo) && aisleNo == orgAisleNo
                        && gcNo == orgGcNo)
                {
                    return true;
                }
            }

            return true;
        }
        return true;
    }

    /**<jp>
     * メンテナンス処理を行います。
     * メンテナンス処理の種類は処理区分（getProcessingKind()メソッドより取得）により内部で判断する必要があります。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Conducts the maintenance process.
     * It is necessary that type of the maintentace should be determined internally according to
     * the process type (obtained by getProcessingKind() method.)
     * Return true if maintenance process succeeded, or false if failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn Connection Object
     * @return :true if maintenance process succeeded, or false if failed.
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
        //<en>Registeration</en>
        if (processingKind == M_CREATE)
        {
            if (!create(conn))
            {
                return false;
            }
            //<jp>6121004 = 編集しました</jp>
            //<en>6121004 = Teh data is edited.</en>
            setMessage("6121004");
            return true;
        }
        //<jp>処理区分が異常</jp>
        //<en>Error is given as the process type.</en>
        else
        {
            //<jp> 予期しない値がセットされました。{0} = {1}</jp>
            //<en> Unexpected value has been set.{0} = {1}</en>
            String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
            RmiMsgLogClient.write(msg, (String)this.getClass().getName());
            //<jp> 6126499 = 致命的なエラーが発生しました。ログを参照してください。</jp>
            //<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
            throw new ScheduleException("6126499");
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * このクラスの初期化時に生成した<CODE>ToolShelfHandler</CODE>インスタンスを取得します。
     * @return <CODE>ToolShelfHandler</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>ToolShelfHandler</CODE> instance which has been generated 
     * at the initialization of this class.
     * @param conn Connection Object
     * @return :CODE>ToolShelfHandler</CODE>
     </en>*/
    protected ToolShelfHandler getToolShelfHandler(Connection conn)
    {
        return new ToolShelfHandler(conn);
    }

    /**<jp>
     * このクラスの初期化時に生成した<CODE>ToolAisleHandler</CODE>インスタンスを取得します。
     * @return <CODE>ToolAisleHandler</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>ToolAisleHandler</CODE> instance which has been generated 
     * at the initialization of this class.
     * @param conn Connection Object
     * @return <CODE>ToolAisleHandler</CODE>
     </en>*/
    protected ToolAisleHandler getToolAisleHandler(Connection conn)
    {
        return new ToolAisleHandler(conn);
    }

    /**<jp>
     * このクラスの初期化時に生成した<CODE>FindUtil</CODE>インスタンスを取得します。
     * @return <CODE>FindUtil</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>FindUtil</CODE> instance which has been generated 
     * at the initialization of this class.
     * @param conn Connection Object
     * @return <CODE>FindUtil</CODE>
     </en>*/
    protected ToolFindUtil getFindUtil(Connection conn)
    {
        return new ToolFindUtil(conn);
    }

    /**<jp>
     * パラメータの補完処理を行います。<BR>
     * ・他の端末で更新されたかチェックするためReStoringインスタンス
     * をパラメータに追加する
     * 処理にに成功した場合は補完したパラメータ。失敗した場合はnullを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param param 補完処理を行うためのパラメータ
     * @return 処理に成功した場合はパラメータ。失敗した場合はnullを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException 処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Conducts the complementarity process of parameter.<BR>
     * - Add ReStoring instance to the parameter in ordder to check whether/not the data 
     *   has been modified by other terminals.
     * It returns the complemented parameter if the process succeeded, or returns false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param param : parameter which is used for the complementarity process
     * @return :returns the parameter if the process succeeded, ro returns null if it failed.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the process.
     </en>*/
    protected Parameter complementParameter(Parameter param)
            throws ReadWriteException,
                ScheduleException
    {
        return param;
    }

    /**<jp>
     * メンテナンス修正処理を行います。
     * パラメータ配列のキーとなる項目を元に変更処理を行います。
     * AlterKeyに検索条件として作業No、品名コード、ロットNoをセットして入庫数を更新します。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     </jp>*/
    /**<en>
     * Processes the maintenance modifications.
     * Modification will be made based on the key items of parameter array. 
     * Sets the work no., item code and lot no. to the AlterKey as update values.
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @return :true if the process succeeded, or false if it failed.
     </en>*/
    protected boolean modify()
    {
        return true;
    }

    /**<jp>
     * メンテナンス登録処理を行います。再入庫予定データの登録は行いません。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Processe the maintenance registrations. The rscheduled restorage data will not be registered.
     * Return true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn Connection Object
     * @return :true if the process succeeded, or false if it failed.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    protected boolean create(Connection conn)
            throws ScheduleException
    {
        try
        {
            Parameter[] mArray = (Parameter[])getAllParameters();
            if (mArray.length > 0)
            {
                //<jp>Aisle表を更新</jp>
                //<en>Update the Aisle table.</en>
                updateAilseTable(conn, mArray);
                //<jp>Shelf表を更新</jp>
                //<en>Update the Shelf table.</en>
                updateShelfTable(conn, mArray);
                //<jp>BankSelect表を更新</jp>
                //<en>Update the BankSelect table.</en>
                updateBankSelectTable(conn, mArray);
                return true;
            }
            //<jp>処理すべきデータが無い場合</jp>
            //<en>If there is no data to process,</en>
            else
            {
                //<jp>表のデータを全部削除！</jp>
                //<en>Delete all data from the table.</en>
                getToolAisleHandler(conn).truncate();
                //<jp>Shelf表のデータを全部削除！</jp>
                //<en>Delete all data in Shelf table.</en>
                getToolShelfHandler(conn).truncate();

                ToolBankHandler bankHandler = new ToolBankHandler(conn);
                bankHandler.truncate();

                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
    }

    /**<jp>
     * メンテナンス削除処理を行います。
     * パラメータ配列のキーとなる項目を元に削除処理を行います。
     * 削除が選択された項目の処理区分を「処理済み」にセットします。実際の削除は
     * 日締め処理にて行われます。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     </jp>*/
    /**<en>
     * Process the maintenance deletions.
     * Deletion will be done based on teh key items of parameter array. 
     * Set the process type of selected item to delete to 'processed'. The acrual deletion will be
     * done in daily transactions.
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @return :returns true if the process succeeded, or returns false if it failed. 
     </en>*/
    protected boolean delete()
    {
        return true;
    }

    // Private methods -----------------------------------------------
    /**<jp>
     * AISLEテーブルの更新を行います。
     * @param array  ため打ちデータ
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Update the AISLE table.
     * @param conn Connection Object
     * @param mArray :entered data summary
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    private void updateAilseTable(Connection conn, Parameter[] mArray)
            throws ReadWriteException,
                ScheduleException
    {
        try
        {
            AisleParameter castparam = null;
            //<jp>Aisle表のデータを全部削除！</jp>
            //<en>Delete all data in Aisle table.</en>
            getToolAisleHandler(conn).truncate();

            for (int i = 0; i < mArray.length; i++)
            {
                castparam = (AisleParameter)mArray[i];

                //<jp>アイルステーションNo</jp>
                //<en>Aisle station no.</en>
                Aisle aisle = new Aisle(castparam.getAisleStationNo());
                //<jp>アイルNo</jp>
                //<en>Aisle no.</en>
                aisle.setAisleNo(castparam.getAisleNumber());
                //<jp>倉庫ステーションNo</jp>
                //<en>Warehouse station no.</en>
                String whstno = getFindUtil(conn).getWarehouseStationNumber(castparam.getWarehouseNumber());
                aisle.setWhStationNo(whstno);
                //<jp>AGCNo</jp>
                //<en>AGCNo</en>
                GroupController gc = new GroupController(conn, castparam.getAGCNumber());
                aisle.setGroupController(gc);

                //<jp>ダブルディープ区分</jp>
                //<en>Double deep type </en>
                int doubledeepKind = Aisle.SINGLE_DEEP;

                if (isDoubleDeep(castparam.getSBank(), castparam.getEBank()))
                {
                    doubledeepKind = Aisle.DOUBLE_DEEP;
                }
                aisle.setDoubleDeepKind(doubledeepKind);
                //<jp>状態(使用不可)</jp>
                //<en>Status (unavailable)</en>
                aisle.setStatus(Station.STATION_NG);
                //<jp>在庫確認フラグ（未作業）</jp>
                //<en>inventory checking flag (unprocessed)</en>
                aisle.setInventoryCheckFlag(Station.NOT_INVENTORYCHECK);

                aisle.setMaxCarry(castparam.getMaxCarry());
                
                //<jp>作成</jp>
                //<en>Create</en>
                getToolAisleHandler(conn).create(aisle);
            }
        }
        catch (InvalidStatusException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        catch (DataExistsException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
    }

    /**<jp>
     * SHELFテーブルの更新を行います。
     * @param array  ため打ちデータ
     </jp>*/
    /**<en>
     * Update the SHELF table.
     * @param conn Connection Object
     * @param mArray :entered data summary
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    private void updateShelfTable(Connection conn, Parameter[] mArray)
            throws ReadWriteException,
                ScheduleException
    {
        try
        {
            AisleParameter castparam = null;
            //<jp>Shelf表のデータを全部削除！</jp>
            //<en>Delete all data in Shelf table.</en>
            getToolShelfHandler(conn).truncate();

            //<jp>ため打ちループ</jp>
            //<en>Loop for pooling entered data</en>
            for (int i = 0; i < mArray.length; i++)
            {
                castparam = (AisleParameter)mArray[i];

                int[] bankArray = getLocationArray(castparam.getSBank(), castparam.getEBank());
                int[] bayArray = getLocationArray(castparam.getSBay(), castparam.getEBay());
                int[] levelArray = getLocationArray(castparam.getSLevel(), castparam.getELevel());

                for (int j = 0; j < bankArray.length; j++)
                {
                    int priority = 0;
                    int side = Bank.NEAR;
                    int pairBank = 0;
                    boolean doubledeepKind = isDoubleDeep(castparam.getSBank(), castparam.getEBank());
                    //ダブルディープの場合
                    if (doubledeepKind)
                    {
                        //優先順取得
                        priority =
                                getPriority(bankArray[j], castparam.getSBank(), castparam.getEBank(),
                                        castparam.getSAislePosition(), castparam.getEAislePosition());

                        //棚の位置
                        if (bankArray[j] != castparam.getSAislePosition()
                                && bankArray[j] != castparam.getEAislePosition())
                        {
                            //奥
                            side = Bank.FAR;
                        }

                        //ペア棚のバンクを取得
                        pairBank =
                                getPairBank(bankArray[j], castparam.getSBank(), castparam.getEBank(),
                                        castparam.getSAislePosition(), castparam.getEAislePosition());
                    }

                    for (int k = 0; k < bayArray.length; k++)
                    {
                        for (int l = 0; l < levelArray.length; l++)
                        {
                            int warehouseNo = castparam.getWarehouseNumber();
                            String locationNo = Shelf.getNumber(warehouseNo, bankArray[j], bayArray[k], levelArray[l]);

                            Shelf shelf = new Shelf(locationNo);
                            shelf.setBankNo(bankArray[j]);
                            shelf.setBayNo(bayArray[k]);
                            shelf.setLevelNo(levelArray[l]);
                            shelf.setAddressNo(0);
                            //<jp>倉庫ステーションNo</jp>
                            //<en>Warehouse station no.</en>
                            String whstno = getFindUtil(conn).getWarehouseStationNumber(castparam.getWarehouseNumber());
                            shelf.setWhStationNo(whstno);
                            //<jp>状態(使用可)</jp>
                            //<en>Status (available)</en>
                            shelf.setStatus(Station.STATION_OK);
                            //<jp>在荷</jp>
                            //<en>Load presence</en>
                            shelf.setStatusFlag(Shelf.PRESENCE_EMPTY);
                            //<jp>Hard Zone</jp>
                            //<en>Hard Zone</en>
                            shelf.setHardZoneId(0);
                            //<jp>Soft Zone</jp>
                            //<en>Soft Zone</en>
                            shelf.setSoftZoneId(Shelf.UN_SETTING);
                            //<jp>親ステーション</jp>
                            //<en>Parent station</en>
                            shelf.setParentStationNo(castparam.getAisleStationNo());
                            //<jp>アクセスNGフラグ</jp>
                            //<en>Inaccesible flag</en>
                            shelf.setAccessNgFlag(false);
                            //<jp>荷幅</jp>
                            //<en>Load width</en>
                            shelf.setWidth(0);
                            //<jp>棚使用フラグ</jp>
                            //<en>Location use flag</en>
                            shelf.setLocationUseFlag(Shelf.LOCATION_USE_FLAG_OK);

                            // ダブルディープの場合
                            if (doubledeepKind)
                            {
                                //優先順位
                                shelf.setPriority(priority);
                                //棚の位置
                                shelf.setSide(side);
                                //ペアステーションNo
                                if (pairBank != 0)
                                {
                                    shelf.setPairStationNumber(Shelf.getNumber(warehouseNo, pairBank, bayArray[k],
                                            levelArray[l]));

                                    //<jp>作成</jp>
                                    //<en>Create</en>
                                    getToolShelfHandler(conn).createDoubleDeep(shelf);
                                }
                                else
                                {
                                    //<jp>作成</jp>
                                    //<en>Create</en>
                                    getToolShelfHandler(conn).create(shelf);
                                }
                            }
                            // シングルディープの場合
                            else
                            {
                                //棚の位置(手前)
                                shelf.setSide(Bank.NEAR);

                                //<jp>作成</jp>
                                //<en>Create</en>
                                getToolShelfHandler(conn).create(shelf);
                            }
                        }
                    }
                }
            }
        }
        catch (InvalidStatusException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
        catch (DataExistsException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
    }


    /**<jp>
     * BANKSELECTテーブルの更新を行います。
     * @param array  ため打ちデータ
     </jp>*/
    /**<en>
     * Update the BANKSELECT table.
     * @param conn Connection Object
     * @param mArray :entered data summary
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    private void updateBankSelectTable(Connection conn, Parameter[] mArray)
            throws ReadWriteException,
                ScheduleException
    {

        try
        {
            AisleParameter castparam = null;
            ToolBankHandler bankHandler = new ToolBankHandler(conn);

            //<jp>BANKSELECT表のデータを全部削除！</jp>
            //<en>Delete all data in BANKSELECT table.</en>
            bankHandler.truncate();

            //<jp>ため打ちループ</jp>
            //<en>Loop for pooling entered data</en>
            for (int i = 0; i < mArray.length; i++)
            {
                castparam = (AisleParameter)mArray[i];

                int[] bankArray = getLocationArray(castparam.getSBank(), castparam.getEBank());

                boolean doubledeepKind = isDoubleDeep(castparam.getSBank(), castparam.getEBank());

                for (int j = 0; j < bankArray.length; j++)
                {
                    String warehouseStationNo =
                            getFindUtil(conn).getWarehouseStationNumber(castparam.getWarehouseNumber());

                    Bank bank = new Bank();

                    bank.setWhStationNo(warehouseStationNo);
                    bank.setAisleStationNo(castparam.getAisleStationNo());
                    bank.setBankNo(bankArray[j]);
                    bank.setPairBank(0);

                    //ダブルディープの場合
                    if (doubledeepKind)
                    {
                        if (bankArray[j] == castparam.getSAislePosition()
                                || bankArray[j] == castparam.getEAislePosition())
                        {
                            //手前
                            bank.setSide(Bank.NEAR);
                        }
                        else
                        {
                            //奥
                            bank.setSide(Bank.FAR);
                        }
                        // PairBankを取得し登録する
                        bank.setPairBank(getPairBank(bankArray[j], castparam.getSBank(), castparam.getEBank(),
                                castparam.getSAislePosition(), castparam.getEAislePosition()));
                    }
                    //シングルディープの場合
                    else
                    {
                        //手前
                        bank.setSide(Bank.NEAR);
                    }

                    //<jp>作成</jp>
                    //<en>Create</en>
                    bankHandler.create(bank);
                }
            }
        }
        catch (DataExistsException e)
        {
            e.printStackTrace();
            throw new ScheduleException(e.getMessage());
        }
    }

    /**<jp>
     * 同一格納区分で、バンク範囲の重なりを確認します。重なっている場合は
     * falseを返します。
     * @param bank 比較するバンクのint配列
     * @param bankOrg 比較するバンクのint配列
     * @param array ため打ちデータ
     * @return    範囲が重なっている場合はfalseを返します。
     </jp>*/
    /**<en>
     * Check the overlapped bank range of the identical storage type. 
     * Return false if the range overlapped.
     * @param bank    :int array of the bank to compare
     * @param bankOrg :int array of the bank to compare
     * @return    :return false if the data range overlapped.
     </en>*/
    private boolean checkBankRange(int[] bank, int[] bankOrg)
    {
        for (int i = 0; i < bank.length; i++)
        {
            for (int j = 0; j < bankOrg.length; j++)
            {
                if (bank[i] == bankOrg[j])
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**<jp>
     * 倉庫設定では、
     * 追加するパラメータの格納区分がため打ちデータ内に存在するかを確認します。
     * また同一格納区分でバンクの範囲が重なった場合はfalseを返します。
     * 同一格納区分で同一アイルナンバーを割り当てることはできません。
     * システム同一アイルステーションＮｏを割り当てることはできません。
     * @param param  今回追加するパラメータ
     * @param array  ため打ちデータ
     * @return    同一データが存在する場合Trueを返します。
     </jp>*/
    /**<en>
     * At the warehouse seetting:
     * Check whether/not the storage type of adding parameter exists in entered data summary.
     * Return false if the bank range overlapped within the identical storage type.
     * Identical aisle no. cannot be assigned when storage type is the same.
     * Identical aisle station no. of system cannot be assigned.
     * @param param :the parameter being added in this process
     * @param array :entered data summary
     * @return      :returns true if identical data exists.
     </en>*/
    private boolean isSameData(AisleParameter param, Parameter[] array)
    {
        //<jp>比較するキー</jp>
        //<en>Keys to compare;</en>
        //<jp>通常使用しない値</jp>
        //<en>the value normally unused</en>
        int newWarehouseNo = 99999;
        //<jp>通常使用しない値</jp>
        //<en>the value normally unused</en>
        int orgWarehouseNo = 99999;
        String newAisleStationNo = "";
        String orgAisleStationNo = "";
        //<jp>通常使用しない値</jp>
        //<en>the value normally unused</en>
        int newAisleNo = 99999;
        //<jp>通常使用しない値</jp>
        //<en>the value normally unused</en>
        int orgAisleNo = 99999;

        int newSBank = 0;
        int newEBank = 0;
        int orgSBank = 0;
        int orgEBank = 0;

        //<jp>ため打ちデータが存在する場合</jp>
        //<en>If there are any entered data summary,</en>
        if (array.length > 0)
        {
            newWarehouseNo = param.getWarehouseNumber();
            newAisleStationNo = param.getAisleStationNo();
            newAisleNo = param.getAisleNumber();

            newSBank = param.getSBank();
            newEBank = param.getEBank();

            //<jp>修正中パラメータのindexを取得</jp>
            //<en>Retrieve the index of the updating parmaeter.</en>

            for (int i = 0; i < array.length; i++)
            {
                AisleParameter castparam = (AisleParameter)array[i];
                //<jp>ため打ちデータのキー</jp>
                //<en>Key for entered data summary</en>
                orgWarehouseNo = castparam.getWarehouseNumber();
                orgAisleStationNo = castparam.getAisleStationNo();
                orgAisleNo = castparam.getAisleNumber();
                orgSBank = castparam.getSBank();
                orgEBank = castparam.getEBank();

                //<jp>同一アイルステーションNoの確認</jp>
                //<en>Check the identical aisle station no.</en>
                if (newAisleStationNo.equals(orgAisleStationNo))
                {
                    //<jp>6123016 = 既に入力されています。同一ステーションNo.を入力することはできません。</jp>
                    //<en>6123016 = The data is already entered. Cannot enter identical station no.</en>
                    setMessage("6123016");
                    return true;
                }

                //<jp>同一格納区分の場合</jp>
                //<en>If the storage type is identical,</en>
                if (newWarehouseNo == orgWarehouseNo)
                {
                    //<jp>同一アイルNoの確認</jp>
                    //<en>Check the identical aisle no.</en>
                    if (newAisleNo == orgAisleNo)
                    {
                        //<jp>6123053 = 既に入力されています。同一アイルNo.を入力することはできません。</jp>
                        //<en>6123053 = The data is already entered. Cannot enter identical station no.</en>
                        setMessage("6123053");
                        return true;
                    }
                    //<jp>バンク範囲の重なりを確認</jp>
                    //<en>Check the overlap of bank range.</en>
                    int[] bankArray = getLocationArray(newSBank, newEBank);
                    int[] bankArrayOrg = getLocationArray(orgSBank, orgEBank);
                    if (!checkBankRange(bankArray, bankArrayOrg))
                    {
                        //<jp>6123099=同一格納区分で重なったバンク範囲を入力することはできません。</jp>
                        //<en>6123099=Cannot enter the overlapped bank range of identical storage type.</en>
                        setMessage("6123099");
                        return true;
                    }
                }
            }
        }

        return false;
    }


    /**<jp>
     * 開始ロケーションと終了ロケーションからint型の配列を作成します。
     * 
     * @param start 開始ロケーション
     * @param end 終了ロケーション
     * @return    int型の配列
     </jp>*/
    /**<en>
     * Create the array of int style based on the starting location and the ending location.
     * 
     * @param start :starting location
     * @param end   :ending location
     * @return      :int style array
     </en>*/
    private int[] getLocationArray(int start, int end)
    {
        int count = Math.abs(end - start) + 1;

        int[] retArray = new int[count];
        for (int i = 0; i < count; i++)
        {
            retArray[i] = start + i;
        }
        return retArray;
    }

    /**<jp>
     * Aisleインスタンスを取得します。
     * @return <code>Aisle</code>オブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retireve the Aisle instance.
     * @param conn Connection Object
     * @return <code>Aisle</code> object array
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private Aisle[] getAisleArray(Connection conn)
            throws ReadWriteException
    {
        ToolAisleSearchKey aisleKey = new ToolAisleSearchKey();
        //<jp>*** Aisleインスタンスを取得 ***</jp>
        //<en>*** Retrieve the Aisle instance ***</en>
        aisleKey.setWhStationNoOrder(1, true);
        aisleKey.setAisleNoOrder(2, true);
        Aisle[] array = (Aisle[])getToolAisleHandler(conn).find(aisleKey);
        return array;
    }

    /**
     * 指定されたバンク範囲がダブルディープかどうか判断します。
     * @param startBank 開始バンクを指定します。
     * @param endBank   終了バンクを指定します。
     * @return ダブルディープ設定の場合はtrueを返します。
     */
    private boolean isDoubleDeep(int startBank, int endBank)
    {
        if (endBank - startBank > 1)
        {
            return true;
        }

        return false;
    }

    /**
     * 指定されたバンクの優先順を取得します。
     * @param bank 優先順取得対象バンク
     * @param startBank  開始バンクを指定します。
     * @param endBank    終了バンクを指定します。
     * @param startAisle 開始アイル位置を指定します。
     * @param endAisle   終了アイル位置を指定します。
     * @return 優先順位
     */
    private int getPriority(int bank, int startBank, int endBank, int startAisle, int endAisle)
    {
        int priority = 0;

        if (bank == startBank)
        {
            priority = 1;
        }
        else if (bank == startAisle)
        {
            priority = 2;
        }
        else if (bank == endAisle)
        {
            priority = endBank - startBank + 1;
        }
        else if (bank == endBank)
        {
            priority = endBank - startBank;
        }

        return priority;
    }

    /**
     * 指定されたバンクのペア棚となるバンクを取得します。
     * 指定されたバンクがシングルディープの場合、0を返します。 
     * @param bank バンク
     * @param startBank  開始バンクを指定します。
     * @param endBank    終了バンクを指定します。
     * @param startAisle 開始アイル位置を指定します。
     * @param endAisle   終了アイル位置を指定します。
     * @return ペア棚のバンク
     */
    private int getPairBank(int bank, int startBank, int endBank, int startAisle, int endAisle)
    {
        if (bank == startBank)
        {
            if (bank != startAisle)
            {
                return bank + 1;
            }
        }
        else if (bank == startAisle)
        {
            return bank - 1;
        }
        else if (bank == endAisle)
        {
            if (bank != endBank)
            {
                return bank + 1;
            }
        }
        else if (bank == endBank)
        {
            return bank - 1;
        }

        return 0;
    }
}
//end of class

