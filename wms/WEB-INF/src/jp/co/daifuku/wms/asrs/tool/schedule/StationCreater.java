// $Id: StationCreater.java 5355 2009-11-02 00:44:35Z ota $
package jp.co.daifuku.wms.asrs.tool.schedule;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.NotFoundException;
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
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationAlterKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;

/**<jp>
 * ステーション設定を行なうクラスです。
 * AbstractCreaterを継承し、ステーション設定に必要な処理を実装します。
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5355 $, $Date: 2009-11-02 09:44:35 +0900 (月, 02 11 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class operates the station settigs.
 * IT inherits the AbstractCreater and implements the processes required to set up stations.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5355 $, $Date: 2009-11-02 09:44:35 +0900 (月, 02 11 2009) $
 * @author  $Author: ota $
 </en>*/
public class StationCreater
        extends AbstractCreater
{
    // Class fields --------------------------------------------------
    // Class variables -----------------------------------------------
    /**<jp>
     * <CODE>ToolStationSearchKey</CODE>インスタンス
     </jp>*/
    /**<en>
     * <CODE>ToolStationSearchKey</CODE> instance
     </en>*/
    protected ToolStationSearchKey wStationKey = null;

    /**<jp>
     * <CODE>ToolStationAlterKey</CODE>インスタンス
     </jp>*/
    /**<en>
     * <CODE>ToolStationAlterKey</CODE> instance
     </en>*/
    protected ToolStationAlterKey wStationAKey = null;

    private boolean TRUE = true;

    private boolean FALSE = false;

    /**<jp> クラス名（入庫専用） </jp>*/
    /**<en> Class name (dedicated for storage)</en>*/
    public static String CLASS_STORAGE = "jp.co.daifuku.wms.asrs.location.StorageStationOperator";

    /**<jp> クラス名（出庫専用） </jp>*/
    /**<en> Class name (dedicated for retrieva)</en>*/
    public static String CLASS_RETRIEVAL = "jp.co.daifuku.wms.asrs.location.RetrievalStationOperator";

    /**<jp> クラス名（固定荷受台・自走台車） </jp>*/
    /**<en> Class name (P&D stand, powered cart)</en>*/
    public static String CLASS_INOUTSTATION = "jp.co.daifuku.wms.asrs.location.InOutStationOperator";

    /**<jp> クラス名（コの字入庫） </jp>*/
    /**<en> Class name  (U-shaped storage) </en>*/
    public static String CLASS_FREESTORAGE = "jp.co.daifuku.wms.asrs.location.FreeStorageStationOperator";

    /**<jp> クラス名（コの字出庫） </jp>*/
    /**<en> Class name  (U-shaped retrieval)</en>*/
    public static String CLASS_FREERETRIEVAL = "jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator";

    /**<jp> 種別（入庫専用） </jp>*/
    /**<en> Type(dedicated for storage ) </en>*/
    public static final int TYPE_STORAGE = 0;

    /**<jp> 種別（出庫専用） </jp>*/
    /**<en> Type(dedicated for retrieval ) </en>*/
    public static final int TYPE_RETRIEVAL = 1;

    /**<jp> 種別（固定荷受台・自走台車） </jp>*/
    /**<en> Type(P&D stand, powered cart)</en>*/
    public static final int TYPE_INOUTSTATION = 2;

    /**<jp> 種別（コの字入庫） </jp>*/
    /**<en> Type(U-shaped storage) </en>*/
    public static final int TYPE_FREESTORAGE = 3;

    /**<jp> 種別（コの字出庫） </jp>*/
    /**<en> Type(U-shaped retrieval) </en>*/
    public static final int TYPE_FREERETRIEVAL = 4;

    /**<jp> 荷姿 </jp>*/
    /**<en> Load size </en>*/
    private int LOADSIZE = 0;

    /**<jp> 製番フォルダ </jp>*/
    /**<en> PRoduct number folder </en>*/
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
        return ("$Revision: 5355 $,$Date: 2009-11-02 09:44:35 +0900 (月, 02 11 2009) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 指定された位置のパラメータを削除します。<BR>
     * @param conn データベース接続用 Connection
     * @param index 削除するパラメータ位置
     * @throws ScheduleException 指定された位置にパラメータが存在しない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete the parameter of the specified position. <BR>
     * @param conn Databse Connection Object
     * @param index :position of the deleting parameter
     * @throws ScheduleException :Notifies if there are no parameters in specified position.
     </en>*/
    public void removeParameter(Connection conn, int index)
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
     * 全てのパラメータを削除します。<BR>
     * @param conn データベース接続用 Connection
     * @throws ScheduleException 指定された位置にパラメータが存在しない場合に通知されます。
     </jp>*/
    /**<en>
     * Delete all parameters.<BR>
     * @param conn Databse Connection Object
     * @throws ScheduleException :Notifies if there are no parameters in specified position.
     </en>*/
    public void removeAllParameters(Connection conn)
            throws ScheduleException
    {
        //<jp> メッセージの初期化</jp>
        //<en> Initialization of the message</en>
        setMessage("");

        wParamVec.removeAllElements();
        //<jp>削除しました</jp>
        //<en>Deleted the data.</en>
        setMessage("6121003");
    }

    /**<jp>
     * このクラスの初期化を行ないます。初期化時に<CODE>ReStoringHandler</CODE>のインスタンス生成を行います。
     * @param conn データベースとのコネクションオブジェクト
     * @param kind 処理区分
     </jp>*/
    /**<en>
     * Initialize this class. Generate the instance of <CODE>ReStoringHandler</CODE> at the initialization.
     * @param conn :connection object with database
     * @param kind :process type
     </en>*/
    public StationCreater(Connection conn, int kind)
    {
        super(conn, kind);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 画面から印刷発行ボタンが押下された場合の処理を実装します。<BR>
     * @param conn データベース接続用 Connection
     * @param <code>Locale</code> オブジェクト
     * @param listParam スケジュールパラメータ
     * @return 印刷処理の結果
     </jp>*/
    /**<en>
     * Implement the process to run when the print-issue button was pressed on the display.<BR>
     * @param conn Databse Connection Object
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
     * @param conn データベース接続用 Connection
     * @param <code>Locale</code> オブジェクト
     * @param searchParam 検索条件
     * @return スケジュールパラメータの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve data to isplay on the screen.<BR>
     * @param conn Databse Connection Object
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
        Station[] array = getStationArray(conn);
        //<jp>一時的にデータを格納するVector</jp>
        //<jp>ため打ちの最大件数を初期値としてセット</jp>
        //<en>Vector where the data will temporarily be stored</en>
        //<en>Set the max number of data as an initial value for entered data summary.</en>
        Vector vec = new Vector(100);
        //<jp>表示用のエンティティクラス</jp>
        //<en>Entity class for display</en>
        StationParameter dispData = null;

        //<jp>他のメソッドではファイル名を外部から渡すことができないので</jp>
        //<jp>ここでセットする。</jp>
        //<en>File name should be set here, as it cannot be provided from external in other methods.</en>
        wFilePath = ((StationParameter)searchParam).getFilePath();

        if (array.length > 0)
        {
            for (int i = 0; i < array.length; i++)
            {
                //<jp>STATION表のステーションのみを表示します。</jp>
                //<jp>送信可能区分が1:送信可能、作業場種別が0:未指定</jp>
                //<en>Only display the stations of STATION table.</en>
                //<en>Division of sedable status - 1:sendable, workshop type - 0:unspecified</en>
                if (array[i].getSendable() == TRUE && array[i].getWorkPlaceType() == Station.NOT_WORKPLACE)
                {
                    dispData = new StationParameter();
                    //<jp>倉庫ステーションNo.</jp>
                    //<en>warehouse station no.</en>
                    dispData.setWareHouseStationNo(array[i].getWarehouseStationNo());
                    //<jp>ステーションNo.</jp>
                    //<en>station no.</en>
                    dispData.setNumber(array[i].getStationNo());
                    //<jp>ステーション名称</jp>
                    //<en>station name.</en>
                    dispData.setName(array[i].getStationName());
                    //<jp>種別</jp>
                    //<en>type</en>
                    if (array[i].getClassName().equals(CLASS_STORAGE))
                    {
                        //<jp>入庫専用</jp>
                        //<en>dedicated for storage</en>
                        dispData.setType(TYPE_STORAGE);
                    }
                    else if (array[i].getClassName().equals(CLASS_RETRIEVAL))
                    {
                        //<jp>出庫専用</jp>
                        //<en>dedicated for retrieval</en>
                        dispData.setType(TYPE_RETRIEVAL);
                    }
                    else if (array[i].getClassName().equals(CLASS_INOUTSTATION))
                    {
                        //<jp>固定荷受台・自走台車</jp>
                        //<en>P&D stand, powered cart</en>
                        dispData.setType(TYPE_INOUTSTATION);
                    }
                    else if (array[i].getClassName().equals(CLASS_FREESTORAGE))
                    {
                        //<jp>コの字（入庫側）</jp>
                        //<en>U-shaped (storage side)</en>
                        dispData.setType(TYPE_FREESTORAGE);
                    }
                    else if (array[i].getClassName().equals(CLASS_FREERETRIEVAL))
                    {
                        //<jp>コの字（出庫側）</jp>
                        //<en>U-shaped (retrieval side)</en>
                        dispData.setType(TYPE_FREERETRIEVAL);
                    }

                    //<jp>アイルステーションNo.</jp>
                    //<en>aisle station no.</en>
                    dispData.setAisleStationNo(array[i].getAisleStationNo());
                    //AGCNo.
                    dispData.setControllerNumber(array[i].getGroupController().getControllerNumber());
                    //<jp>設定区分</jp>
                    //<en>set type</en>
                    dispData.setSettingType(array[i].getSettingType());
                    if (array[i].getArrivalCheck() == Station.NOT_ARRIVALCHECK)
                    {
                        //<jp>到着報告（チェックなし）</jp>
                        //<en>arrival report (no check)</en>
                        dispData.setArrivalCheck(Station.NOT_ARRIVALCHECK);
                    }
                    else
                    {
                        //<jp>到着報告（チェックあり）</jp>
                        //<en>arrival report (to be checked)</en>
                        dispData.setArrivalCheck(Station.ARRIVALCHECK);
                    }
                    if (array[i].getLoadSize() == Station.NOT_LOADSIZECHECK)
                    {
                        //<jp>荷姿検知器（チェックなし）</jp>
                        //<en>load size check (no check)</en>
                        dispData.setLoadSizeCheck(Station.NOT_LOADSIZECHECK);
                    }
                    else
                    {
                        //<jp>荷姿検知器（チェックあり）</jp>
                        //<en>load size check (to be checked)</en>
                        dispData.setLoadSizeCheck(Station.LOADSIZECHECK);
                    }
                    //<jp>作業表示運用</jp>
                    //<en>on-line indication</en>
                    dispData.setOperationDisplay(array[i].getOperationDisplay());
                    if (array[i].getReStoringOperation() == Station.NOT_CREATE_RESTORING)
                    {
                        //<jp>再入庫作業（なし）</jp>
                        //<en>retrieval work (unavailable)</en>
                        dispData.setReStoringOperation(Station.NOT_CREATE_RESTORING);
                    }
                    else
                    {
                        //<jp>再入庫作業（あり）</jp>
                        //<en>retrieval work (available)</en>
                        dispData.setReStoringOperation(Station.CREATE_RESTORING);
                    }

                    //<jp>再入庫搬送指示</jp>
                    //<en>carry instruction for restorage</en>
                    dispData.setReStoringInstruction(array[i].getReStoringInstruction());
                    if (array[i].isRemove() == true)
                    {
                        //<jp>払出し区分（可）</jp>
                        //<en>removal (available)</en>
                        dispData.setRemove(Station.PAYOUT_OK);
                    }
                    else
                    {
                        //<jp>払出し区分（不可）</jp>
                        //<en>removal (unavailable)</en>
                        dispData.setRemove(Station.PAYOUT_NG);
                    }
                    //<jp>モード切替</jp>
                    //<en>mode switch</en>
                    dispData.setModeType(array[i].getModeType());
                    //<jp>搬送指示可能数</jp>
                    //<en>number of carry instruction sendable</en>
                    dispData.setMaxInstruction(array[i].getMaxInstruction());
                    //<jp>出庫指示可能数</jp>
                    //<en>number of retrieval instruction sendable</en>
                    dispData.setMaxPalletQuantity(array[i].getMaxPalletQty());

                    //<jp>親ステーションNo</jp>
                    //<en>parent station no.</en>
                    dispData.setParentNumber(array[i].getParentStationNo());

                    vec.addElement(dispData);
                }
            }
            StationParameter[] fmt = new StationParameter[vec.size()];
            vec.toArray(fmt);
            return fmt;
        }
        return null;
    }

    /**<jp>
     * パラメータチェック処理を行ないます。パラメータ追加時、メンテナンス処理を実行する前に呼ばれます。
     * パラメータに異常があった場合、その詳細を<code>getMessage</code>で取得できます。
     * ＜修正数＞<BR>
     * ・再入庫数以下でなくてはなりません
     * ・基準積み付け数以下でなくてはなりません
     * @param conn データベース接続用 Connection
     * @param param チェックするパラメータ
     * @return パラメータに異常が無い場合はtrue、異常がある場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Processes the parameter check. It will be called when adding the parameter, before the 
     * execution of maintenance process.
     * If there are any error with parameter, the reason can be obtained by <code>getMessage</code>.
     * <number for the modificaiton><BR>
     *  -should be less than the restorage load quantity.
     *  -should be less than standard load quantity to stack.
     * @param conn Databse Connection Object
     * @param param :parameter to check
     * @return :returns true if there is no error with parameter, or returns false if there are any errors.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
     </en>*/
    public boolean check(Connection conn, Parameter param)
            throws ReadWriteException,
                ScheduleException
    {
        ToolCommonChecker check = new ToolCommonChecker(conn);
        StationParameter mParameter = (StationParameter)param;
        //<jp>処理区分を取得</jp>
        //<en>Retrieve the process type.</en>
        int processingKind = getProcessingKind();
        //<jp>登録</jp>
        //<en>Registration</en>
        switch (processingKind)
        {
            case M_CREATE:
                //<jp> WAREHOUSE表に登録されているかチェック</jp>
                //<en> Check to see if the data is registered in WAREHOUSE table.</en>
                ToolWarehouseHandler whhandle = new ToolWarehouseHandler(conn);
                ToolWarehouseSearchKey whkey = new ToolWarehouseSearchKey();
                if (whhandle.count(whkey) <= 0)
                {
                    //<jp> 倉庫情報がありません。倉庫設定画面で登録してください</jp>
                    //<en> The information of the warehouse cannot be found. </en>
                    //<en> Please register in warehouse setting screen.</en>
                    setMessage("6123100");
                    return false;
                }

                //<jp> GROUPCONTROLLER表に登録されているかチェック</jp>
                //<en> Check to see if the data is registered in GROUPCONTROLLER table.</en>
                ToolGroupControllerHandler gchandle = new ToolGroupControllerHandler(conn);
                ToolGroupControllerSearchKey gckey = new ToolGroupControllerSearchKey();
                if (gchandle.count(gckey) <= 0)
                {
                    //<jp> グループコントローラ情報がありません。グループコントローラ設定画面で登録してください</jp>
                    //<en> The information of the group controller cannot be found. </en>
                    //<en> Please register in group controller setting screen.</en>
                    setMessage("6123078");
                    return false;
                }

                //<jp> AISLE表に登録されているかは、Beanの中でチェックしている</jp>
                //<en> It is checked in Bean whether/not the data is registered in AISLE table.</en>
                //<jp> 種別が固定荷受台は、モード切替を無しに指定することはできません。</jp>
                //<en> When P&D stand is the type, mode switch 'on' must be specified.</en>
                if (mParameter.getType() == TYPE_INOUTSTATION)
                {
                    if (mParameter.getModeType() == Station.NO_MODE_CHANGE)
                    {
                        //<jp> 固定荷受台・自走台車はモード切替を指定してください</jp>
                        //<en> Please designate the mode switch for P&D stand/powered cart operation.</en>
                        setMessage("6123210");
                        return false;
                    }
                }

                //<jp> 種別が入庫専用、固定荷受台、コの字（入庫側）は、必須入力です。</jp>
                //<en> Max. number of carry instruction sendable must be entered if the type is 'dedicated</en>
                //<en> for storage', 'P6D stand' or 'U-shaped retrieval'.</en>
                if (mParameter.getType() == TYPE_STORAGE || mParameter.getType() == TYPE_FREESTORAGE
                        || mParameter.getType() == TYPE_INOUTSTATION)
                {
                    //<jp> 搬送指示可能数入力チェック</jp>
                    //<en> Check the entered number of carry instruction sendable.</en>
                    if (mParameter.getMaxInstruction() <= 0)
                    {
                        //<jp> 搬送指示可能数には１以上の値を指定してください</jp>
                        //<en> Please specify 1 or greater value for the number of carry instruction sendable.</en>
                        setMessage("6123094");
                        return false;
                    }
                }
                //<jp> 出庫専用、コの字（出庫側）のとき、搬送指示可能数に０をセットする。</jp>
                //<en> Set 0 for the number of carry instruction sendable if dedicated for retrieval and U-shaped retrieval.</en>
                else
                {
                    mParameter.setMaxInstruction(0);
                }

                //<jp> 種別が出庫専用、固定荷受台、コの字（出庫側）は必須入力です。</jp>
                //<en> Max. number of retrieval instruction senable must be entered if the type is 'dedicated</en>
                //<en> for retrieval', 'P&D stand' or 'U-shaped retrieval'.</en>
                if (mParameter.getType() == TYPE_RETRIEVAL || mParameter.getType() == TYPE_FREERETRIEVAL
                        || mParameter.getType() == TYPE_INOUTSTATION)
                {
                    //<jp> 出庫指示可能数入力チェック</jp>
                    //<en> Check the entered number of retrieval instructions sendable.</en>
                    if (mParameter.getMaxPalletQuantity() <= 0)
                    {
                        //<jp> 出庫指示可能数には１以上の値を指定してください</jp>
                        //<en> Please specify 1 or greater value for the number of retrieval instruction sendable.</en>
                        setMessage("6123095");
                        return false;
                    }
                }
                //<jp> 入庫専用、コの字（入庫側）のとき、出庫指示可能数に０をセットする。</jp>
                //<en> Set 0 for the number of retrieval instructions sendable if dedicated for storage and U-shaped storage.</en>
                else
                {
                    mParameter.setMaxPalletQuantity(0);
                }

                //<jp>ステーション名称のチェック</jp>
                //<en>Check the name of the station.</en>
                if (!check.checkStationName(mParameter.getName()))
                {
                    //<jp>異常内容をセットする</jp>
                    //<en>Set the contents of the error.</en>
                    setMessage(check.getMessage());
                    return false;
                }

                // システム定義チェック
                if (WmsParam.ALL_STATION.equals(mParameter.getNumber()))
                {
                    // 6023222=入力された{0}はシステムで使用しているため登録できません。
                    setMessage(WmsMessageFormatter.format(6023222, DisplayText.getText("LBL-W0303")));
                    return false;
                }

                // 代表ステーションチェック
                if (!checkMainStation(conn, mParameter))
                {
                    return false;
                }

                break;
            default:
                //<jp> 予期しない値がセットされました。{0} = {1}</jp>
                //<en> Unexpected value has been set.{0} = {1}</en>
                String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
                RmiMsgLogClient.write(msg, (String)this.getClass().getName());
                //<jp> 6126499 = 致命的なエラーが発生しました。ログを参照してください。</jp>
                //<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
                throw new ScheduleException("6126499");
        }

        return true;
    }

    /**<jp>
     * 整合性チェック処理を行ないます。eAWC環境設定ツールのジェネレート時に呼ばれます。
     * 異常があった場合、その詳細をファイルへ書き込みます。
     * @param conn データベース接続用 Connection
     * @param logpath 異常が発生したときのログを書き込むファイルを置くためのパス
     * @param locale <code>Locale</code>オブジェクト
     * @return 異常が無い場合はtrue、一つでも異常がある場合はfalseを返します。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the inconsistency check. This will be called when generating the eAWC environment setting tool.
     * If any error takes place, the detail will be written in the file.
     * @param conn Databse Connection Object
     * @param logpath :path to place the file in which the log will be written when error occurred.
     * @param locale <code>Locale</code>object
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

            ToolStationSearchKey wstationKey = new ToolStationSearchKey();
            wstationKey.setSendable(Station.SENDABLE);
            wstationKey.setWorkPlaceType(Station.NOT_WORKPLACE);
            Station[] wArray = (Station[])getToolStationHandler(conn).find(wstationKey);

            //<jp>Stationが設定されていない場合</jp>
            //<en>If the Station has not been set,</en>
            if (wArray.length == 0)
            {
                //<jp>6123181 = ステーションが設定されていません</jp>
                //<en>6123181 = The station has not been set.</en>
                loghandle.write("Station", "Station Table", "6123181");
                //<jp>ステーションが設定されていない場合は、後のチェックを行わずに抜ける</jp>
                //<en>If the station has not been set, discontinue the checks and exit.</en>
                return false;
            }

            //<jp>*** グループコントローラNoのチェック(Aisle表) ***</jp>
            //<jp>STATION表のグループコントローラNoがグループコントローラ表に存在するか確認</jp>
            //<en>*** Check the group controller no. (Aisle table) ***</en>
            //<en>Check to see if the group controller no. in STATION table exists in group controller table.</en>
            for (int i = 0; i < wArray.length; i++)
            {
                int controller = wArray[i].getGroupController().getControllerNumber();
                if (controller != 0)
                {
                    if (!check.isExistControllerNo(controller))
                    {
                        loghandle.write("Station", "Station Table", check.getMessage());
                        errorFlag = false;
                    }
                }
            }

            //<jp>*** 倉庫ステーションNoのチェック(Warehouse表) ***</jp>
            //<jp>STATION表の倉庫ステーションNo.がWAREHOUSE表に存在するか確認</jp>
            //<en>*** Check the warehouse station no. (Warehouse table) ***</en>
            //<en>Check to see if the warehouse station no. in STATION table exists in WAREHOUSE table.</en>
            for (int i = 0; i < wArray.length; i++)
            {
                String warehouseStationNo = wArray[i].getWarehouseStationNo();
                if (wArray[i].getWorkPlaceType() != Station.WPTYPE_ALL)
                {
                    if (!check.isExistAutoWarehouseStationNo(warehouseStationNo))
                    {
                        loghandle.write("Station", "Station Table", check.getMessage());
                        errorFlag = false;
                    }
                }
            }

            //*** クローズ倉庫ステーションの払出し区分チェック(Warehouse表) ***
            //クローズ倉庫のステーションで払出し区分不可以外のものが存在するか確認
            ToolWarehouseSearchKey warehouseKey = new ToolWarehouseSearchKey();
            ToolWarehouseHandler warehousehandle = new ToolWarehouseHandler(conn);
            Warehouse[] warehouseArray = (Warehouse[])warehousehandle.find(warehouseKey);
            for (int i = 0; i < warehouseArray.length; i++)
            {
                if (warehouseArray[i].getEmploymentType() == Warehouse.CLOSE)
                {
                    for (int j = 0; j < wArray.length; j++)
                    {
                        if (wArray[j].getWarehouseStationNo().equals(warehouseArray[i].getWarehouseStationNo()))
                        {
                            if (wArray[j].isRemove() && wArray[j].getSendable())
                            {
                                //6123286=クローズ運用倉庫に属するステーション({0})の払出し区分は不可しか選択できません。
                                loghandle.write("Station", "Station Table", "6123286" + wDelim
                                        + wArray[j].getStationNo());
                                errorFlag = false;
                            }
                        }
                    }
                }
            }

            //<jp>*** アイルステーションNoのチェック(Shelf表) ***</jp>
            //<jp>STATION表のアイルステーションNoがアイル表に存在するか確認</jp>
            //<en>*** Check the aisle station no. (Shelf table) ***</en>
            //<en>Check to see if the aisle station no. in STATION table exists in aisle table.</en>
            for (int i = 0; i < wArray.length; i++)
            {
                String aisleStationNo = wArray[i].getAisleStationNo();
                if (aisleStationNo != null && aisleStationNo.length() > 0)
                {
                    if (!check.isExistAisleStationNo(aisleStationNo))
                    {
                        loghandle.write("Station", "Station Table", check.getMessage());
                        errorFlag = false;
                    }
                }
            }

            //<jp>*** ステーションNoのチェック(Station表) ***</jp>
            //<jp>STATION表のステーションNo.がSTATIONTYPE表に存在するか確認</jp>
            //<en>*** Check the station no. (Station table) ***</en>
            //<en>Check to see if the station no. in STATION table exists in STATIONTYPE table.</en>
            for (int i = 0; i < wArray.length; i++)
            {
                String stationNo = wArray[i].getStationNo();
                if (!check.isExistStationType(stationNo))
                {
                    loghandle.write("Station", "Station Table", check.getMessage());
                    errorFlag = false;
                }
            }
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
     * 同一品名コード、ロットナンバーのデータはため打ちデータに登録できません。
     * パラメータ重複チェックを行い重複したデータが無い場合はtrue、
     * ある場合はfalseを返します。
     * パラメータ重複チェックに失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param conn データベース接続用 Connection
     * @param param チェックするパラメータ
     * @return パラメータ重複チェックに成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException パラメータ重複チェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the parameter duplicate check.
     * Data of identical item code and lot no. cannot be registered in entered data summary.
     * It checks the duplication of parameter, then returns true if there was no duplicated data 
     * or returns false if there were any duplication.
     * If parameter duplicate check failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn Databse Connection Object
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
        StationParameter mParam = (StationParameter)param;
        //<jp>同一データチェック</jp>
        //<en>Check the identical data.</en>
        if (isSameData(mParam, mArray))
        {
            return false;
        }
        //<jp>ステーションNo.</jp>
        //<en>station no.</en>
        String stationNo = mParam.getNumber();

        //<jp> ステーション№チェック（STATION表で使用されているステーション№は使用できない）</jp>
        //<en> Check the station no. (any station no. in STATION table cannot be used)</en>
        if (isStationSameData(conn, mParam))
        {
            return false;
        }

        if (getToolStationHandler(conn).isStationType(stationNo, ToolStationHandler.STATION_HANDLE))
        {
            //<jp>6123122 すでに登録されています。同一ステーションNoを登録することはできません。</jp>
            //<en>6123122 The data is already registered. Cannot register the identical station no.</en>
            setMessage("6123122");
            return false;
        }

        //<jp>修正の時のみチェックする。</jp>
        //<en>Check only when modifing data.</en>
        if (getUpdatingFlag() != ScheduleInterface.NO_UPDATING)
        {
            //<jp>*** 修正の時、キー項目は変更不可とする ***</jp>
            //<en>*** Key items are not to be modifiable when modifing data. ***</en>
            //<jp>倉庫ステーションNo.</jp>
            //<en>warehouse station no.</en>
            String warehouseStNo = mParam.getWareHouseStationNo();
            //<jp>種別</jp>
            //<en>type</en>
            int stType = mParam.getType();
            //<jp>アイルステーションNo.</jp>
            //<en>aisle station no.</en>
            String aisleNo = mParam.getAisleStationNo();
            //AGCNo.
            int gcNo = mParam.getControllerNumber();

            //<jp>ため打ちの中のキー項目</jp>
            //<en>Key items for enterd data summary</en>
            String orgwarehouseStNo = "";
            String orgstationNo = "";
            int orgType = 0;
            String orgAisleNo = "";
            int orgGcNo = 0;

            Parameter[] mAllArray = (Parameter[])getAllParameters();
            for (int i = 0; i < mAllArray.length; i++)
            {
                StationParameter castparam = (StationParameter)mAllArray[i];
                //<jp>キー項目</jp>
                //<en>Key items</en>
                orgwarehouseStNo = castparam.getWareHouseStationNo();
                orgstationNo = castparam.getNumber();
                orgType = castparam.getType();
                orgAisleNo = castparam.getAisleStationNo();
                orgGcNo = castparam.getControllerNumber();

                //<jp>変更されていないのでOK</jp>
                //<en>Acceptable as the data is not modified.</en>
                if (warehouseStNo.equals(orgwarehouseStNo) && stationNo.equals(orgstationNo) && stType == orgType
                        && aisleNo.equals(orgAisleNo) && gcNo == orgGcNo)
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
     * @param conn データベース接続用 Connection
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
     * @param conn Databse Connection Object
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
            //<jp> 6126499 = 致命的なエラーが発生しました。ログを参照してください。</jp>
            //<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
            throw new ScheduleException("6126499");
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * このクラスの初期化時に生成した<CODE>ReStoringHandler</CODE>インスタンスを取得します。
     * @param conn データベース接続用 Connection
     * @return <CODE>ReStoringHandler</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>ReStoringHandler</CODE> instance generated at the initialization of this class.
     * @param conn Databse Connection Object
     * @return <CODE>ReStoringHandler</CODE>
     </en>*/
    protected ToolStationHandler getToolStationHandler(Connection conn)
    {
        return new ToolStationHandler(conn);
    }

    /**<jp>
     * このクラスの初期化時に生成した<CODE>FindUtil</CODE>インスタンスを取得します。
     * @param conn データベース接続用 Connection
     * @return <CODE>FindUtil</CODE>
     </jp>*/
    /**<en>
     * Retrieve the <CODE>FindUtil</CODE> instance generated at the initialization of this class.
     * @param conn Databse Connection Object
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
     * Conduct the complementarity process of parameter.<BR>
     * - Append ReStoring instance to the parameter in order to check whether/not the data 
     *   has been modified by other terminals.
     * It returns the complemented parameter if the process succeeded, or returns false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param param :parameter which is used for the complementarity process
     * @return :returns the parameter if the process succeeded, or returns null if it failed.
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
     * Process the maintenance modifications.
     * Modification will be made based on the key items of parameter array. 
     * Set the work no., item code and lot no. to the AlterKey as search conditions, and updates storage quantity.
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @return :returns true if the process succeeded, or false if it failed.
     </en>*/
    protected boolean modify()
    {
        return true;
    }

    /**<jp>
     * メンテナンス登録処理を行います。再入庫予定データの登録は行いません。
     * メンテナンス処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * 処理に失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param conn データベース接続用 Connection
     * @return 処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the maintenance registrations. The scheduled restorage data is not registered 
     * in this process.
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @param conn Databse Connection Object
     * @return :returns true if the process succeeded, or false if it failed.
     * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
     </en>*/
    protected boolean create(Connection conn)
            throws ScheduleException
    {
        try
        {
            Parameter[] mArray = (Parameter[])getAllParameters();
            StationParameter castparam = null;
            ToolStationHandler tsh = getToolStationHandler(conn);

            if (mArray.length > 0)
            {
                //<jp>表のデータを全部削除！</jp>
                //<en>Delete all data from the table.</en>
                isDropStation(conn);

                //<jp>***** STATION表の更新処理 *****/</jp>
                //<en>***** Update teh STATION table. *****/</en>
                Station station = new Station();
                for (int i = 0; i < mArray.length; i++)
                {
                    castparam = (StationParameter)mArray[i];
                    //<jp>倉庫ステーションNo.</jp>
                    //<en>warehouse station no.</en>
                    station.setWhStationNo(castparam.getWareHouseStationNo());
                    //<jp>ステーションNo.</jp>
                    //<en>station no.</en>
                    station.setStationNo(castparam.getNumber());
                    //<jp>ステーション名称</jp>
                    //<en>station name.</en>
                    station.setStationName(castparam.getName());
                    //<jp>種別、クラス名</jp>
                    //<en>Type, class name</en>
                    switch (castparam.getType())
                    {
                        case TYPE_STORAGE:
                            //<jp>入庫    </jp>
                            //<en>storage</en>
                            station.setStationType(Station.STATION_TYPE_IN);
                            //<jp>入庫専用</jp>
                            //<en>dedicated for storage</en>
                            station.setClassName(CLASS_STORAGE);
                            break;
                        case TYPE_RETRIEVAL:
                            //<jp>出庫</jp>
                            //<en>retrieval</en>
                            station.setStationType(Station.STATION_TYPE_OUT);
                            //<jp>出庫専用</jp>
                            //<en>dedicated for retrieval</en>
                            station.setClassName(CLASS_RETRIEVAL);
                            break;
                        case TYPE_INOUTSTATION:

                            //<jp>入出庫兼用</jp>
                            //<en>storage/retrieval available</en>
                            station.setStationType(Station.STATION_TYPE_INOUT);
                            //<jp>固定荷受台・自走台車</jp>
                            //<en>P&D stand, powered cart</en>
                            station.setClassName(CLASS_INOUTSTATION);
                            break;
                        case TYPE_FREESTORAGE:
                            //<jp>入庫</jp>
                            //<en>storage</en>
                            station.setStationType(Station.STATION_TYPE_IN);
                            //<jp>コの字（入庫側）</jp>
                            //<en>U-shaped (storage side)</en>
                            station.setClassName(CLASS_FREESTORAGE);
                            break;
                        case TYPE_FREERETRIEVAL:
                            //<jp>出庫</jp>
                            //<en>retrieval</en>
                            station.setStationType(Station.STATION_TYPE_OUT);
                            //<jp>コの字（出庫側）</jp>
                            //<en>U-shaped (retrieval side)</en>
                            station.setClassName(CLASS_FREERETRIEVAL);
                            break;
                        default:
                            break;
                    }

                    //<jp>アイルステーションNo.</jp>
                    //<en>aisle station no.</en>
                    station.setAisleStationNo(castparam.getAisleStationNo());

                    //AGCNo
                    GroupController gc = new GroupController(conn, castparam.getControllerNumber());
                    station.setGroupController(gc);

                    //<jp>設定区分</jp>
                    //<en>set type</en>
                    station.setSettingType(castparam.getSettingType());
                    if (castparam.getArrivalCheck() == Station.NOT_ARRIVALCHECK)
                    {
                        //<jp>到着報告（チェックなし）</jp>
                        //<en>arrival report(no check)</en>
                        station.setArrivalCheck(FALSE);
                    }
                    else
                    {
                        //<jp>到着報告（チェックあり）</jp>
                        //<en>arrival report(to be checked)</en>
                        station.setArrivalCheck(TRUE);
                    }
                    if (castparam.getLoadSizeCheck() == Station.NOT_LOADSIZECHECK)
                    {
                        //<jp>荷姿検知器（チェックなし）</jp>
                        //<en>load size check(no check)</en>
                        station.setLoadSize(FALSE);
                    }
                    else
                    {
                        //<jp>荷姿検知器（チェックあり）</jp>
                        //<en>load size check(to be checked)</en>
                        station.setLoadSize(TRUE);
                    }
                    //<jp>作業表示運用</jp>
                    //<en>On-line indication</en>
                    station.setOperationDisplay(castparam.getOperationDisplay());
                    if (castparam.getReStoringOperation() == Station.NOT_CREATE_RESTORING)
                    {
                        //<jp>再入庫作業（なし）</jp>
                        //<en>retrieval work(unavailable)</en>
                        station.setReStoringOperation(FALSE);
                    }
                    else
                    {
                        //<jp>再入庫作業（あり）</jp>
                        //<en>retrieval work(available)</en>
                        station.setReStoringOperation(TRUE);
                    }
                    //<jp>再入庫搬送指示</jp>
                    //<en>restorage carry instruction</en>
                    station.setReStoringInstruction(castparam.getReStoringInstruction());
                    //<jp>モード切替</jp>
                    //<en>mode switch</en>
                    station.setModeType(castparam.getModeType());
                    //<jp>搬送指示可能数</jp>
                    //<en>number of carry instruction sendable</en>
                    station.setMaxInstruction(castparam.getMaxInstruction());
                    //<jp>出庫指示可能数</jp>
                    //<en>number of retrieval instruction sendable</en>
                    station.setMaxPalletQty(castparam.getMaxPalletQuantity());
                    if (castparam.getRemove() == Station.PAYOUT_OK)
                    {
                        station.setRemove(true);
                    }
                    else
                    {
                        station.setRemove(false);
                    }
                    //<jp>送信可能区分（1:送信可能）</jp>
                    //<en>division of sendable status (1:sendable)</en>
                    station.setSendable(TRUE);
                    //<jp>状態（0:切離中）</jp>
                    //<en>status (0:off-line)</en>
                    station.setStatus(Station.STATION_NG);
                    //<jp>作業場種別（0:未指定）</jp>
                    //<en>workshop type (0:undefined)</en>
                    station.setWorkPlaceType(Station.NOT_WORKPLACE);
                    //<jp>中断中フラグ（0:使用可能）</jp>
                    //<en>suspention flag (0:unavailable)</en>
                    station.setSuspend(FALSE);
                    //<jp>在庫確認チェック（0:在庫確認未作業）</jp>
                    //<en>inventory check (0:unprocessed)</en>
                    station.setInventoryCheckFlag(Station.NOT_INVENTORYCHECK);
                    //<jp>現在作業モード（0:ニュートラルモード）</jp>
                    //<en>current work mode(0:neutral)</en>
                    station.setCurrentMode(Station.NEUTRAL);
                    //<jp>モード切替要求区分（モード切替要求なし）</jp>
                    //<en>mode switch type(no request for mode switch)</en>
                    station.setModeRequest(Station.NO_REQUEST);
                    //<jp>親ステーションNo.</jp>
                    //<en>parent station no.</en>
                    station.setParentStationNo(castparam.getParentNumber());

                    tsh.create(station);
                }
            }

            //<jp>処理すべきデータが無い場合</jp>
            //<en>If there is no data to process,</en>
            else
            {
                //<jp>表のデータを全部削除！</jp>
                //<en>Delete all data from the table.</en>
                isDropStation(conn);
            }
            return true;
        }
        catch (DataExistsException e)
        {
            throw new ScheduleException(e.getMessage());
        }
        catch (InvalidStatusException e)
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
     * Deletion will be done based on the key items of parameter array. 
     * Set the process type of selected item to delete to 'processed'. The actual deletion will
     * be done in daily transactions.
     * It returns true if the maintenance process succeeded, or false if it failed.
     * If the process failed, its reason can be obtained by <code>getMessage</code>.
     * @return :returns true if the process succeeded, or false if it failed.
     </en>*/
    protected boolean delete()
    {
        return true;
    }

    /**<jp>
     * 代表ステーションチェックを行います。
     * 既に代表ステーションに設定されているステーションを修正する場合、紐づく他のステーション
     * と異なる種別、アイル、AGC No.、再入庫搬送指示、払出し、モード切替は修正不可
     * @param conn データベースとのコネクションオブジェクト
     * @param param チェックするパラメータ
     * @return 修正可能な場合はtrue、不可能な場合はfalseを返します。
     </jp>
     * @throws ReadWriteException */
    protected boolean checkMainStation(Connection conn, StationParameter param)
            throws ReadWriteException
    {
        StationParameter mParameter = (StationParameter)param;

        ToolStationHandler shdle = new ToolStationHandler(conn);
        ToolStationSearchKey skey = new ToolStationSearchKey();

        // 代表ステーションかチェック
        skey.setStationNo(mParameter.getNumber());
        Station[] st = (Station[])shdle.findMainStation(skey);
        if (ArrayUtil.isEmpty(st))
        {
            // 代表ステーションでなければチェック不要
            return true;
        }

        // 代表ステーションの場合、紐づくステーションをチェックする
        ToolStationSearchKey childStKey = new ToolStationSearchKey();
        childStKey.setParentStationNo(st[0].getStationNo());
        Station[] childSt = (Station[])shdle.find(childStKey);
        if (ArrayUtil.isEmpty(st))
        {
            return true;
        }

        for (Station station : childSt)
        {
            // 自ステーションはチェック不要
            if (station.getStationNo().equals(mParameter.getNumber()))
            {
                continue;
            }

            //<jp>同一クラス名称のチェック</jp>
            //<en>Check the identical class name.</en>
            String className = null;
            switch (mParameter.getType())
            {
                //<jp>入庫    </jp>
                //<en>storage</en>
                case TYPE_STORAGE:
                    className = CLASS_STORAGE;
                    break;
                //<jp>出庫</jp>
                //<en>retrieval</en>
                case TYPE_RETRIEVAL:
                    className = CLASS_RETRIEVAL;
                    break;
                //<jp>入出庫兼用</jp>
                //<en>storage/retrieval available</en>
                case TYPE_INOUTSTATION:
                    className = CLASS_INOUTSTATION;
                    break;
                //<jp>コの字（入庫側）</jp>
                //<en>U-shaped (storage side)</en>
                case TYPE_FREESTORAGE:
                    className = CLASS_FREESTORAGE;
                    break;
                //<jp>コの字（出庫側）</jp>
                //<en>U-shaped (retrieval side)</en>
                case TYPE_FREERETRIEVAL:
                    className = CLASS_FREERETRIEVAL;
                    break;
                default:
                    return false;
            }
            if (!station.getClassName().equals(className))
            {
                //<jp>6123141 = 同一代表ステーション内にステーション種別の違うステーションは設定できません</jp>
                //<en>6123141 = Cannot set the stations of different station type in same main station.</en>
                setMessage("6123141");
                return false;
            }

            //<jp>同一アイルステーションNo.のチェック</jp>
            //<en>Check the identical aisle station no.</en>
            if (!station.getAisleStationNo().equals(mParameter.getAisleStationNo()))
            {
                //<jp>6123130 = 同一代表ステーション内にアイルステーションNo.の違うステーションは設定できません</jp>
                //<en>6123130 = Cannot set the stations of different aisle station no in same main station.</en>
                setMessage("6123130");
                return false;
            }

            //<jp>同一グループコントローラNo.のチェック</jp>
            //<en>Check the identical group controller no.</en>
            if (station.getGroupController().getControllerNumber() != mParameter.getControllerNumber())
            {
                //<jp>6123143 = 同一代表ステーション内にAGCNo.の違うステーションは設定できません</jp>
                //<en>6123143 = Cannot set the stations of different AGCNo in same main station.</en>
                setMessage("6123143");
                return false;
            }

            //<jp>同一再入庫搬送指示有無のチェック</jp>
            if (station.getReStoringInstruction() != mParameter.getReStoringInstruction())
            {
                //<jp>6123150=同一代表ステーション内に再入庫搬送指示の違うステーションは設定できません。</jp>
                //<en>6123150=Station from different restorage transfer command send existence can't be set in same main station.</en>
                setMessage("6123150");
                return false;
            }

            //<jp>同一払出し区分のチェック</jp>
            boolean remove = (mParameter.getRemove() == Station.PAYOUT_OK);
            if (station.isRemove() != remove)
            {
                //<jp>6123151=同一代表ステーション内に払出しの違うステーションは設定できません。</jp>
                //<en>6123151=Station from different remove type can't be set in same main station.</en>
                setMessage("6123151");
                return false;
            }
        }

        return true;
    }

    // Private methods -----------------------------------------------
    /**<jp>
     * リストボックスより編集するデータを選択するときに、同一のデータが選択されたことを
     * 確認するためのチェックを実装します。ステーション設定では、
     * 追加するパラメータの格納区分がため打ちデータ内に存在するかを確認します。
     * @param param 今回追加するパラメータ
     * @param array ため打ちデータ
     * @return    同一データが存在する場合Trueを返します。
     </jp>*/
    /**<en>
     * Implement the check in order to see that the identical data has been selected when chosing data
     * from the list box to edit.
     * In the station setting, it checks whether/not the storage type of appending parameter exists 
     * in the entered data summary.
     * @param param :the parameter which will be appended in this process
     * @param array :entered data summary (pool)
     * @return      :return true if identical data exists.
     </en>*/
    private boolean isSameData(StationParameter param, Parameter[] array)
    {
        //<jp>比較するキー</jp>
        //<en>Key to compare</en>
        //<jp>通常使用しない値</jp>
        //<en>Value which is unused in normal processes</en> 
        String newStationNo = "99999";
        //<jp>通常使用しない値</jp>
        //<en>Value which is unused in normal processes</en> 
        String orgStationNo = "99999";

        //<jp>ため打ちデータが存在する場合</jp>
        //<en>If there is the entered data summary,</en>
        if (array.length > 0)
        {
            //<jp>今回追加する格納区分で比較する</jp>
            //<en>Compare by the storage type appended in this process.</en>
            newStationNo = param.getNumber();

            for (int i = 0; i < array.length; i++)
            {
                StationParameter castparam = (StationParameter)array[i];
                //<jp>ため打ちデータのキー</jp>
                //<en>Key for the entered data summary</en>
                orgStationNo = castparam.getNumber();
                //<jp>同一ステーションNo.のチェック</jp>
                //<en>Check the identical station no.</en>
                if (newStationNo.equals(orgStationNo))
                {
                    //<jp>6123016 = 既に入力されています。同一ステーションNo.を入力することはできません</jp>
                    //<en>6123016 = The data is already entered. Cannot input the identical station no.</en>
                    setMessage("6123016");
                    return true;
                }
            }
        }

        return false;
    }

    /**<jp>
     * STATION表のステーションのみを削除します。（ダミーステーション、作業場、代表ステーションは削除しない）
     * @param conn データベース接続用 Connection
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 
     </jp>*/
    /**<en>
     * Delete only the stations from STATION table. (Dummy stations, workshops and main stations 
     * should not be deleted.)
     * @param conn Databse Connection Object
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws NotFoundException 
     </en>*/
    private void isDropStation(Connection conn)
            throws ReadWriteException,
                NotFoundException
    {
        wStationKey = new ToolStationSearchKey();
        //<jp>送信可能区分（1:送信可能）</jp>
        //<en>dicision of sendable status (1:sendable)</en>
        wStationKey.setSendable(Station.SENDABLE);
        //<jp>作業場種別（0:未指定）</jp>
        //<en>workshop type (0:unspecified)</en>
        wStationKey.setWorkPlaceType(Station.NOT_WORKPLACE);
        Station[] array = (Station[])getToolStationHandler(conn).find(wStationKey);

        if (array.length > 0)
        {
            getToolStationHandler(conn).drop(wStationKey);
        }
    }

    /**<jp>
     * ステーション№は他の画面でも使用されているため、STATION表よりステーション以外の
     * ステーション№を取得し、比較するためのチェックを実装します。
     * @param conn データベース接続用 Connection
     * @param param 今回追加するパラメータ
     * @return    同一データが存在する場合Trueを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * As the station no. is used in other screens, retrieve station no. other than stationss,
     * then implements checks for data comparison.
     * @param conn Databse Connection Object
     * @param param  :the parameter which will be appended in this process
     * @return       :return true if identical data exists.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private boolean isStationSameData(Connection conn, StationParameter param)
            throws ReadWriteException
    {
        wStationKey = new ToolStationSearchKey();
        wStationKey.setStationNo(param.getNumber());
        Station[] array = (Station[])getToolStationHandler(conn).find(wStationKey);

        for (int i = 0; i < array.length; i++)
        {
            //<jp> 作業種別が0以外、送信可能区分1以外で同一ステーションNo.のものがあるとNG</jp>
            //<en> Unacceptable if identical station no. of workshop type :anything other than 0 and </en>
            //<en> sendable status: other than 1 exist.</en>
            if (array[i].getWorkPlaceType() != Station.NOT_WORKPLACE || array[i].getSendable() != TRUE)
            {
                //<jp>6123016 = 既に入力されています。同一ステーションNo.を入力することはできません</jp>
                //<en>6123016 = The data is already entered. Cannot input the identical station no.</en>
                setMessage("6123016");
                return true;
            }
        }

        return false;
    }

    /**<jp>
     * Stationインスタンスを取得します。
     * @param conn データベース接続用 Connection
     * @return <code>Station</code>オブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve the Station instance.
     * @param conn Databse Connection Object
     * @return :the array of <code>Station</code> object
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    private Station[] getStationArray(Connection conn)
            throws ReadWriteException
    {
        ToolStationSearchKey stationKey = new ToolStationSearchKey();
        stationKey.setWareHouseStationNoOrder(1, true);
        stationKey.setStationNoOrder(2, true);

        //<jp>*** Stationインスタンスを取得 ***</jp>
        //<en>*** Retrieve the Station isntance ***</en>
        Station[] array = (Station[])getToolStationHandler(conn).find(stationKey);
        return array;
    }
}
//end of class

