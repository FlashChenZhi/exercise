// $Id: WorkPlaceCreater.java 5355 2009-11-02 00:44:35Z ota $
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

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.tool.common.LogHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationAlterKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWorkPlaceHandler;
import jp.co.daifuku.wms.asrs.tool.location.Station;
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
 * This class is used when setting the stations.
 * It inherits the AbstractCreater, and implements processes requried for station setting.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5355 $, $Date: 2009-11-02 09:44:35 +0900 (月, 02 11 2009) $
 * @author  $Author: ota $
 </en>*/
public class WorkPlaceCreater
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

    /**<jp> 荷高 </jp>*/
    /**<en> Load height</en>*/
    private int HEIGHT = 0;

    /**<jp> 代表ステーション </jp>*/
    /**<en> Main station  </en>*/
    private int MAINSTATION = 1;

    /**<jp> 代表ステーション </jp>*/
    /**<en> Main station  </en>*/
    private int WORKPLACE = 0;

    /**<jp> 製番フォルダ </jp>*/
    /**<en> Pdoduct number holder </en>*/
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
     * @param conn : connetion object with database
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
     * Initialize this class. Generate the <CODE>ReStoringHandler</CODE> instance at the 
     * initialization.
     * @param conn : connetion object with database
     * @param kind : process type
     </en>*/
    public WorkPlaceCreater(Connection conn, int kind)
    {
        super(conn, kind);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 画面から印刷発行ボタンが押下された場合の処理を実装します。<BR>
     * @param <code>Locale</code> オブジェクト
     * @param listParam スケジュールパラメータ
     * @return 印刷処理の結果
     </jp>*/
    /**<en>
     * Implement the process to run when the print-issue button was pressed on the display.
     * @param locale object
     * @param listParam :schedule parameter
     * @return :result of print job
     </en>*/
    public boolean print(Locale locale, Parameter listParam)
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
        WorkPlaceParameter dispData = null;

        //<jp>検索条件の取得</jp>
        //<en>Retrieve the search conditions.</en>
        WorkPlaceParameter wp = (WorkPlaceParameter)searchParam;
        String whNo = wp.getWareHouseStationNo();
        String parentstNo = wp.getParentNumber();
        int mainST = wp.getMainStation();
        //<jp>他のメソッドではファイル名を外部から渡すことができないので</jp>
        //<jp>ここでセットする。</jp>
        //<en>File name should be set here, as it cannot be provided from external in other methods.</en>
        wFilePath = wp.getFilePath();

        if (array.length > 0)
        {
            for (int i = 0; i < array.length; i++)
            {
                //<jp>親ステーションNo、倉庫が同じデータを表示します。</jp>
                //<en>Display the data which has same parent station no. and the warehouse.</en>
                if (array[i].getParentStationNo().equals(parentstNo) && array[i].getWarehouseStationNo().equals(whNo))
                {
                    dispData = new WorkPlaceParameter();
                    //<jp>倉庫ステーションNo.</jp>
                    //<en>warehouse station no.</en>
                    dispData.setWareHouseStationNo(array[i].getWarehouseStationNo());
                    //<jp>作業場No</jp>
                    //<en>workshop no.</en>
                    dispData.setParentNumber(array[i].getParentStationNo());
                    //<jp>作業場種別</jp>
                    //<en>workshop type</en>
                    dispData.setWorkPlaceType(array[i].getWorkPlaceType());
                    //<jp>ステーションNo.</jp>
                    //<en>station no.</en>
                    dispData.setNumber(array[i].getStationNo());
                    //<jp>ステーション名称</jp>
                    //<en>station name.</en>
                    dispData.setName(array[i].getStationName());
                    //<jp>出庫指示可能数</jp>
                    //<en>number of retrieval insruction sendable</en>
                    dispData.setMaxPalletQuantity(array[i].getMaxPalletQty());
                    //<jp>搬送指示可能数</jp>
                    //<en>number of carry insruction sendable</en>
                    dispData.setMaxInstruction(array[i].getMaxInstruction());
                    //AGCNo.
                    dispData.setControllerNumber(array[i].getGroupController().getControllerNumber());
                    //<jp>種別</jp>
                    //<en>type</en>
                    dispData.setType(array[i].getStationType());
                    //<jp>アイルステーションNo.</jp>
                    //<en>aisle station no.</en>
                    dispData.setAisleNumber(array[i].getAisleStationNo());
                    //<jp>クラス名称</jp>
                    //<en>class name</en>
                    dispData.setClassName(array[i].getClassName());
                    //<jp>代表ステーション</jp>
                    //<en>main station</en>
                    if (mainST == MAINSTATION)
                    {
                        dispData.setMainStation(MAINSTATION);
                    }
                    //<jp>作業場</jp>
                    //<en>workshop</en>
                    else if (mainST == WORKPLACE)
                    {
                        dispData.setMainStation(WORKPLACE);
                    }
                    //<jp>再入庫搬送指示</jp>
                    //<en>restorage transfer command send existence</en>
                    dispData.setReStoringInstruction(array[i].getReStoringInstruction());
                    //<jp>払出し区分</jp>
                    //<en>remove type</en>
                    dispData.setRemove(array[i].isRemove());
                    //<jp>モード切替種別</jp>
                    //<en>mode change type</en>
                    dispData.setModeType(array[i].getModeType());


                    vec.addElement(dispData);
                }
            }
            WorkPlaceParameter[] fmt = new WorkPlaceParameter[vec.size()];
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
     * <number for modification><BR>
     *  -the number should be less than restorage quantity.
     *  -the number should be less than standard load quantity to stack.
     * @param conn Databse Connection Object
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
        WorkPlaceParameter mParameter = (WorkPlaceParameter)param;
        //<jp>処理区分を取得</jp>
        //<en>Retrieve the process type.</en>
        int processingKind = getProcessingKind();

        //<jp>登録</jp>
        //<en>Registration</en>
        switch (processingKind)
        {
            case M_CREATE:
                //<jp> 全取消の場合はここは通らない</jp>
                //<en> The process will not pass here if deleting all data.</en>
                if (!mParameter.getNumber().equals(""))
                {
                    //<jp> ステーションNo.／作業場No.がSTATION表に登録されているかチェック</jp>
                    //<en> Check if the station No. /workshop no. is registered in STATION table.</en>
                    ToolWorkPlaceHandler wphandle = new ToolWorkPlaceHandler(conn);
                    ToolStationSearchKey stkey = new ToolStationSearchKey();
                    stkey.setStationNo(mParameter.getNumber());
                    Station[] station = (Station[])wphandle.find(stkey);

                    Parameter[] Array = (Parameter[])getParameters();
                    //<jp>比較するキー</jp>
                    //<en>Key to compare</en>
                    //<jp>通常使用しない値</jp>
                    //<en>Value which is unused in normal processes</en> 
                    String newWarehouse = "99999";
                    //<jp>通常使用しない値</jp>
                    //<en>Value which is unused in normal processes</en> 
                    String orgWarehouse = "99999";
                    //<jp> ため打ちデータが存在する場合</jp>
                    //<en> Check the identical user name</en>
                    if (Array.length > 0)
                    {
                        //<jp>今回追加するステーションの格納区分で比較する</jp>
                        //<en>Compare with tje storage type of the station which is appended this time.</en>
                        newWarehouse = station[0].getWarehouseStationNo();
                        WorkPlaceParameter castparam = (WorkPlaceParameter)Array[0];
                        //<jp>ため打ちデータのキー</jp>
                        //<en>Key for the entered data summary</en>
                        orgWarehouse = castparam.getWareHouseStationNo();
                        //<jp>同一格納区分のチェック</jp>
                        //<en>Check the identical storage type</en>
                        if (!newWarehouse.equals(orgWarehouse))
                        {
                            //<jp>6123145 = 格納区分の違うステーションは設定できません</jp>
                            //<en>6123145 = Cannot set up the stations of different storage types.</en>
                            setMessage("6123145");
                            return false;
                        }
                    }

                    //<jp> ダミーステーションは選べません</jp>
                    //<en> Dummy station cannot be selected. </en>
                    if (station[0].getStationType() == Station.STATION_TYPE_OTHER)
                    {
                        //<jp> 6123139 = 作業場設定は、ダミーステーションを選択することができません</jp>
                        //<en> 6123139 = Dummy station cannot be selected in workshop setting process.</en>
                        setMessage("6123139");
                        return false;
                    }
                    //<jp>***代表ステーションの場合***</jp>
                    //<en>*** In case of main station, ***</en>
                    if (mParameter.getWorkPlaceType() == Station.MAIN_STATIONS)
                    {
                        Parameter[] mArray = (Parameter[])getParameters();
                        //<jp>比較するキー</jp>
                        //<en>Key to compare</en>
                        //<jp>通常使用しない値</jp>
                        //<en>Value which is unused in normal processes</en> 
                        String newAStationNo = "99999";
                        //<jp>通常使用しない値</jp>
                        //<en>Value which is unused in normal processes</en>
                        String orgAStationNo = "99999";
                        //<jp>通常使用しない値</jp>
                        //<en>Value which is unused in normal processes</en>
                        String newClassName = "99999";
                        //<jp>通常使用しない値</jp>
                        //<en>Value which is unused in normal processes</en>
                        String orgClassName = "99999";
                        //<jp>通常使用しない値</jp>
                        //<en>Value which is unused in normal processes</en>
                        String newControllerNumber = "99999";
                        //<jp>通常使用しない値</jp>
                        //<en>Value which is unused in normal processes</en>
                        String orgControllerNumber = "99999";
                        //<jp>通常使用しない値</jp>
                        //<en>Value which is unused in normal processes</en>
                        int orgRestoringInstruction = -1;
                        //<jp>通常使用しない値</jp>
                        //<en>Value which is unused in normal processes</en>
                        boolean orgRemove = false;
                        //<jp>通常使用しない値</jp>
                        //<en>Value which is unused in normal processes</en>
                        int orgModeType = -1;

                        //<jp> ため打ちデータが存在する場合</jp>
                        //<en> If there is the entered data summary,</en>
                        if (mArray.length > 0)
                        {
                            //<jp>今回追加するステーションとため打ちデータの項目で比較する</jp>
                            //<en>Compare with this station being added, and with items of entered data sumary.</en>
                            newAStationNo = station[0].getAisleStationNo();
                            newClassName = station[0].getClassName();
                            newControllerNumber =
                                    Integer.toString(station[0].getGroupController().getControllerNumber());
                            for (int i = 0; i < station.length; i++)
                            {
                                WorkPlaceParameter castparam = (WorkPlaceParameter)mArray[i];
                                //<jp>ため打ちデータのキー</jp>
                                //<en>Key for the entered data summary</en>
                                orgAStationNo = castparam.getAisleNumber();
                                orgClassName = castparam.getClassName();
                                orgControllerNumber = Integer.toString(castparam.getControllerNumber());
                                orgRestoringInstruction = castparam.getReStoringInstruction();
                                orgRemove = castparam.isRemove();
                                orgModeType = castparam.getModeType();
                                //<jp>同一クラス名称のチェック</jp>
                                //<en>Check the identical class name.</en>
                                if (!newClassName.equals(orgClassName))
                                {
                                    //<jp>6123141 = 同一代表ステーション内にステーション種別の違うステーションは設定できません</jp>
                                    //<en>6123141 = Cannot set the stations of different station type in same main station.</en>
                                    setMessage("6123141");
                                    return false;
                                }
                                //<jp>同一アイルステーションNo.のチェック</jp>
                                //<jp>アイルステーションNo.の違うステーションは同じ代表ステーションにできない</jp>
                                //<en>Check the identical aisle station no.</en>
                                //<en>Cannot set the station of different aisle station no. as the same main station.</en>
                                if (!newAStationNo.equals(orgAStationNo))
                                {
                                    //<jp>6123130 = 同一代表ステーション内にアイルステーションNo.の違うステーションは設定できません</jp>
                                    //<en>6123130 = Cannot set the stations of different aisle station no in same main station.</en>
                                    setMessage("6123130");
                                    return false;
                                }
                                //<jp>同一グループコントローラNo.のチェック</jp>
                                //<en>Check the identical group controller no.</en>
                                if (!newControllerNumber.equals(orgControllerNumber))
                                {
                                    //<jp>6123143 = 同一代表ステーション内にAGCNo.の違うステーションは設定できません</jp>
                                    //<en>6123143 = Cannot set the stations of different AGCNo in same main station.</en>
                                    setMessage("6123143");
                                    return false;
                                }
                                //<jp>同一再入庫搬送指示有無のチェック</jp>
                                if (station[0].getReStoringInstruction() != orgRestoringInstruction)
                                {
                                    //<jp>6123150=同一代表ステーション内に再入庫搬送指示の違うステーションは設定できません。</jp>
                                    //<en>6123150=Station from different restorage transfer command send existence can't be set in same main station.</en>
                                    setMessage("6123150");
                                    return false;
                                }
                                //<jp>同一払出し区分のチェック</jp>
                                if (station[0].isRemove() != orgRemove)
                                {
                                    //<jp>6123151=同一代表ステーション内に払出しの違うステーションは設定できません。</jp>
                                    //<en>6123151=Station from different remove type can't be set in same main station.</en>
                                    setMessage("6123151");
                                    return false;
                                }
                            }
                        }
                    }

                    //<jp>ステーションNoのチェック</jp>
                    //<en>Check thestation no.</en>
                    if (!check.checkStationNo(mParameter.getNumber()))
                    {
                        //<jp>異常内容をセットする</jp>
                        //<en>Set the contents of the error.</en>
                        setMessage(check.getMessage());
                        return false;
                    }

                    // システム定義チェック
                    if (WmsParam.AUTO_SELECT_STATION.equals(mParameter.getParentNumber())
                            || WmsParam.NOPARENT_STATION_WPNO.equals(mParameter.getParentNumber()))
                    {
                        // 6023222=入力された{0}はシステムで使用しているため登録できません。
                        setMessage(WmsMessageFormatter.format(6023222, DisplayText.getText("LBL-W9038")));
                        return false;
                    }

                }
                break;
            default:
                //<jp> 予期しない値がセットされました。{0} = {1}</jp>
                //<en> Unexpected value has been set.{0} = {1}</en>
                String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
                RmiMsgLogClient.write(msg, (String)this.getClass().getName());
                //<jp> 6126499 = 致命的なエラーが発生しました。ログを参照してください。</jp>
                //<en> 6126499 =  Fatal error occurred. Please refer to the log.</en>
                throw new ScheduleException("6126499");
        }

        return true;
    }

    /**<jp>
     * 整合性チェック処理を行ないます。eAWC環境設定ツールのジェネレート時に呼ばれます。
     * パラメータに異常があった場合、その詳細をfilenameで指定したファイルへ書き込みます。
     * @param conn データベース接続用 Connection
     * @param logpath 異常が発生したときのログを書き込むファイル名
     * @param locale <code>Locale</code>オブジェクト
     * @return 異常が無い場合はtrue、一つでも異常がある場合はfalseを返します。
     * @throws ScheduleException パラメータチェック中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the inconsistency check. This will be called when generating the eAWC environment setting tool.
     * If any error takes place, the detail will be written in the file which is specified by filename.
     * @param conn Databse Connection Object
     * @param logpath :name of the file the log will be written in when error occurred.
     * @param locale <code>Locale</code> object
     * @return :true if there is no error, or false if there are any error.
     * @throws ScheduleException :Notifies if unexpected error occurred during the parameter check.
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

            //<jp>*** グループコントローラNoのチェック(Aisle表) ***</jp>
            //<jp>STATION表のグループコントローラNoがグループコントローラ表に存在するか確認</jp>
            //<en>*** Check the group controller no. (Aisle table) ***</en>
            //<en>Check whether/not the group controller no. of STATION table exist in group controller table.</en>
            int[] workptype = {
                Station.STAND_ALONE_STATIONS,
                Station.AISLE_CONMECT_STATIONS,
                Station.MAIN_STATIONS
            };
            ToolStationSearchKey gstationKey = new ToolStationSearchKey();
            gstationKey.setWorkPlaceType(workptype);
            Station[] gArray = (Station[])getToolWorkPlaceHandler(conn).find(gstationKey);
            for (int i = 0; i < gArray.length; i++)
            {
                int controller = gArray[i].getGroupController().getControllerNumber();
                if (controller != 0)
                {
                    if (!check.isExistControllerNo(controller))
                    {
                        loghandle.write("WorkPlace", "Station Table", check.getMessage());
                        errorFlag = false;
                    }
                }
            }

            //<jp>*** 倉庫ステーションNoのチェック(Warehouse表) ***</jp>
            //<jp>STATION表の倉庫ステーションNo.がWAREHOUSE表に存在するか確認</jp>
            //<en>*** Check the warehouse station no. (Warehouse table) ***</en>
            //<en>Check if the warehouse station no. in STATION table exists in WAREHOUSE table.</en>
            for (int i = 0; i < gArray.length; i++)
            {
                String warehouseStationNo = gArray[i].getWarehouseStationNo();
                if (gArray[i].getWorkPlaceType() != Station.WPTYPE_ALL)
                {
                    if (!check.isExistAutoWarehouseStationNo(warehouseStationNo))
                    {
                        loghandle.write("WorkPlace", "Station Table", check.getMessage());
                        errorFlag = false;
                    }
                }
            }

            //<jp>*** アイルステーションNoのチェック(Shelf表) ***</jp>
            //<jp>STATION表のアイルステーションNoがアイル表に存在するか確認</jp>
            //<en>*** Check the aisle station no. (Shelf table) ***</en>
            //<en>Check if the aisle station no. of STATION table exist in aisle table.</en>
            for (int i = 0; i < gArray.length; i++)
            {
                String aisleStationNo = gArray[i].getAisleStationNo();
                if (aisleStationNo != null && aisleStationNo.length() > 0)
                {
                    if (!check.isExistAisleStationNo(aisleStationNo))
                    {
                        loghandle.write("WorkPlace", "Station Table", check.getMessage());
                        errorFlag = false;
                    }
                }
            }

            //<jp>*** ステーションNoのチェック(Station表) ***</jp>
            //<jp>STATION表のステーションNo.がSTATIONTYPE表に存在するか確認</jp>
            //<en>*** Check the station no. (Station table) ***</en>
            //<en>Check if station no. of STATION table exist in STATIONTYPE table.</en>
            for (int i = 0; i < gArray.length; i++)
            {
                String StationNo = gArray[i].getStationNo();
                if (!check.isExistStationType(StationNo))
                {
                    loghandle.write("WorkPlace", "Station Table", check.getMessage());
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
     * It checks the duplication of parameter, then returns true if there was no duplicated data or 
     * returns false if there were any duplication.
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
        WorkPlaceParameter mParam = (WorkPlaceParameter)param;

        //<jp>同一データチェック</jp>
        //<en>Check the identical data.</en>
        if (isSameData(mParam, mArray))
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
            //<en> 6126499 =  Fatal error occurred. Please refer to the log.</en>
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
    protected ToolWorkPlaceHandler getToolWorkPlaceHandler(Connection conn)
    {
        return new ToolWorkPlaceHandler(conn);
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
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException メンテナンス処理中に予期しないエラーが発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process the maintenance registrations. The scheduled restorage data is not registered in this process.
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
            WorkPlaceParameter headparam = null;
            WorkPlaceParameter castparam = null;
            WorkPlaceParameter workparam = null;
            WorkPlaceParameter wkparam = null;
            ToolWorkPlaceHandler tsh = getToolWorkPlaceHandler(conn);
            ToolStationAlterKey alterkey = null;

            if (mArray.length > 0)
            {
                headparam = (WorkPlaceParameter)mArray[0];

                ToolStationSearchKey wStation = new ToolStationSearchKey();
                wStation.setStationNo(headparam.getParentNumber());

                //<jp>***ステーションの更新***</jp>
                //<jp>入力された作業場が存在する場合</jp>
                //<en>*** Update of stations ***</en>
                //<en>If the entered workshop exists, </en>
                if (tsh.count(wStation) > 0)
                {
                    //<jp>入力した作業場がセットされているステーションの親ステーションNo.を空白にする</jp>
                    //<en>Enter blank in the parent station no. of the station which has been set</en>
                    //<en>with entered workshop.</en>
                    alterkey = new ToolStationAlterKey();
                    alterkey.setParentStationNo(headparam.getParentNumber());
                    alterkey.updateParentStationNo("");
                    try
                    {
                        tsh.modify(alterkey);
                    }
                    catch (NotFoundException e)
                    {
                    }
                }

                //<jp>ためうちデータがある場合</jp>
                //<en>If there are any entered data summary, </en>
                if (!headparam.getNumber().equals(""))
                {
                    for (int i = 0; i < mArray.length; i++)
                    {
                        castparam = (WorkPlaceParameter)mArray[i];
                        //<jp>ためうちデータにあるステーションの親ステーションに作業場をセットする</jp>
                        //<en>Set the workshop for the parent station of the station found in </en>
                        //<en>entered data summary.</en>
                        alterkey = new ToolStationAlterKey();
                        alterkey.setStationNo(castparam.getNumber());
                        alterkey.updateParentStationNo(castparam.getParentNumber());
                        tsh.modify(alterkey);
                    }

                    //<jp>***作業場／代表ステーションの作成と修正***</jp>
                    //<jp>***入力された作業場が存在しない場合***</jp>
                    //<jp>作業場データを新規に作成する</jp>
                    //<en>***Creation/modificaiton of the workshop/main station***</en>
                    //<en>***In case the entered workshop does not exist,***</en>
                    //<en>Newly create the workshop data.</en>
                    if (tsh.count(wStation) <= 0)
                    {
                        Station station = new Station();
                        //<jp>倉庫ステーションNo.</jp>
                        //<en>warehouse station no.</en>
                        station.setWhStationNo(castparam.getWareHouseStationNo());
                        //<jp>作業場No</jp>
                        //<en>workshop no.</en>
                        station.setStationNo(castparam.getParentNumber());
                        //<jp>作業場名前</jp>
                        //<en>workshop name</en>
                        station.setStationName(castparam.getParentName());

                        //<jp>作業場種別（作業場は1または2、代表ステーションは3）</jp>
                        //<jp>作業場</jp>
                        //<en>workshop type (set 1 or 2 for workshop, 3 for main station)</en>
                        //<en>workshop</en>
                        if (castparam.getMainStation() == WORKPLACE)
                        {
                            station.setWorkPlaceType(castparam.getWorkPlaceType());
                        }
                        //<jp>代表ステーション</jp>
                        //<en>main station</en>
                        else if (castparam.getMainStation() == MAINSTATION)
                        {
                            station.setWorkPlaceType(Station.MAIN_STATIONS);
                        }

                        //<jp>AGCNo.（作業場は0、代表ステーションはステーションのAGCNo.をセットする）</jp>
                        //<jp>作業場</jp>
                        //<en>AGCNo. (set 0 for workshop, or set AGCNo. of the station for main station)</en>
                        //<en>Workshop</en>
                        if (castparam.getMainStation() == WORKPLACE)
                        {
                            GroupController gc = new GroupController(conn, 0);
                            station.setGroupController(gc);
                        }
                        //<jp>代表ステーション</jp>
                        //<en>Main station</en>
                        else if (castparam.getMainStation() == MAINSTATION)
                        {
                            GroupController gc = new GroupController(conn, castparam.getControllerNumber());
                            station.setGroupController(gc);
                        }

                        //<jp>クラス名称（作業場は空白、代表ステーションはステーションのクラス名称をセットする）</jp>
                        //<jp>作業場</jp>
                        //<en>Class name (set blank for workshop, or set class name for main station)</en>
                        //<en>Workshop</en>
                        if (castparam.getMainStation() == WORKPLACE)
                        {
                            station.setClassName("");
                        }
                        //<jp>代表ステーション</jp>
                        //<en>Main station</en>
                        else if (castparam.getMainStation() == MAINSTATION)
                        {
                            station.setClassName(castparam.getClassName());
                        }

                        //<jp>アイルステーションNo.はステーションのアイルステーションをセットする</jp>
                        //<jp>違うアイルステーションNo.のステーション／作業場を同じ作業場にする場合、空白をセットする</jp>
                        //<en>For aisle station no., set the aisle station of the station.</en>
                        //<en>If setting the same workshop for station/workshop of different asile station no.,</en>
                        //<en> set blank.</en>
                        String aisle = castparam.getAisleNumber();
                        int fg = 0;
                        for (int i = 0; i < mArray.length; i++)
                        {
                            wkparam = (WorkPlaceParameter)mArray[i];
                            if (!wkparam.getAisleNumber().equals(aisle))
                            {
                                fg = 1;
                            }
                        }
                        if (fg == 1)
                        {
                            station.setAisleStationNo("");
                        }
                        else if (fg == 0)
                        {
                            station.setAisleStationNo(castparam.getAisleNumber());
                        }

                        //<jp>種別はステーションの種別から判断する（すべて入庫、すべて出庫、すべて入出庫兼用、種別が違う場合)</jp>
                        //<en>Determine the type according to the station type.(All storage, all retrieval,</en>
                        //<en>all storage/retrieval available,of different types)</en>
                        int type = castparam.getType();
                        int flg = 0;
                        for (int i = 0; i < mArray.length; i++)
                        {
                            workparam = (WorkPlaceParameter)mArray[i];
                            //<jp>種別が違う場合</jp>
                            //<en>If the type is different,</en>
                            if (type == Station.STATION_TYPE_IN || type == Station.STATION_TYPE_OUT)
                            {
                                if (type != workparam.getType())
                                {
                                    flg = 3;
                                }
                            }
                            //<jp>すべて入出庫兼用</jp>
                            //<en>All stations are available of storage/retrieval </en>
                            else if (type == Station.STATION_TYPE_INOUT)
                            {
                                flg = 3;
                            }
                        }
                        if (flg == 0)
                        {
                            //<jp>すべて入庫</jp>
                            //<en>All storage</en>
                            if (type == Station.STATION_TYPE_IN)
                            {
                                station.setStationType(Station.STATION_TYPE_IN);
                            }
                            //<jp>すべて出庫</jp>
                            //<en>All retrieval</en>
                            else if (type == Station.STATION_TYPE_OUT)
                            {
                                station.setStationType(Station.STATION_TYPE_OUT);
                            }
                        }
                        //<jp>すべて入出庫、種別が違う場合</jp>
                        //<en>All available of storage/retrieval, of different types</en>
                        else if (flg == 3)
                        {
                            station.setStationType(Station.STATION_TYPE_INOUT);
                        }

                        //<jp>設定区分（作業場は0をセット）</jp>
                        //<en>set type (set 0 for workshop)</en>
                        station.setSettingType(0);
                        //<jp>到着報告（0:チェックなし)</jp>
                        //<en>arrival report (0: no check)</en>
                        station.setArrivalCheck(FALSE);
                        //<jp>荷姿検知器（0:チェックなし）</jp>
                        //<en>load size check (0: no check)</en>
                        station.setLoadSize(FALSE);
                        //<jp>作業表示運用（0）</jp>
                        //<en>on-line indication(0)</en>
                        station.setOperationDisplay(0);
                        //<jp>再入庫作業（0:なし）</jp>
                        //<en>restorage  (0:Restorage work (0:no restorage))</en>
                        station.setReStoringOperation(FALSE);
                        //<jp>再入庫搬送指示（作業場は0:搬送指示不要、代表ステーションは紐づくステーションの再入庫搬送指示</jp>
                        //<jp>作業場</jp>
                        //<en>Workshop</en>
                        if (castparam.getMainStation() == WORKPLACE)
                        {
                            station.setReStoringInstruction(Station.AGC_STORAGE_SEND);
                        }
                        //<jp>代表ステーション</jp>
                        //<en>Main station</en>
                        else if (castparam.getMainStation() == MAINSTATION)
                        {
                            station.setReStoringInstruction(castparam.getReStoringInstruction());
                        }
                        //<jp>モード切替（0)</jp>
                        //<en>mode switch(0)</en>
                        station.setModeType(0);

                        //<jp>搬送指示可能数（作業場は0、代表ステーションは全配下ステーションの合計）</jp>
                        //<jp>作業場</jp>
                        //<en>Number of carry instruction sendable (0 for workshop, total of unde classes for main station)</en>
                        //<en>Workshop</en>
                        if (castparam.getMainStation() == WORKPLACE)
                        {
                            station.setMaxInstruction(0);
                        }
                        //<jp>代表ステーション</jp>
                        //<en>Main station</en>
                        else if (castparam.getMainStation() == MAINSTATION)
                        {
                            int isum = 0;
                            for (int i = 0; i < mArray.length; i++)
                            {
                                castparam = (WorkPlaceParameter)mArray[i];
                                isum += castparam.getMaxInstruction();
                            }
                            station.setMaxInstruction(isum);
                        }

                        //<jp>出庫指示可能数（作業場は0、代表ステーションは全配下ステーションの合計）</jp>
                        //<jp>作業場</jp>
                        //<en>Number of retrieval instruction sendable (0 for workshop, total of unde classes for main station)</en>
                        //<en>Workshop</en>
                        if (castparam.getMainStation() == WORKPLACE)
                        {
                            station.setMaxPalletQty(0);
                        }
                        //<jp>代表ステーション</jp>
                        //<en>Main station</en>
                        else if (castparam.getMainStation() == MAINSTATION)
                        {
                            int psum = 0;
                            for (int i = 0; i < mArray.length; i++)
                            {
                                castparam = (WorkPlaceParameter)mArray[i];
                                psum += castparam.getMaxPalletQuantity();
                            }
                            station.setMaxPalletQty(psum);
                        }

                        //<jp>送信可能区分（作業場は0:送信不可、代表ステーションは1:送信可）</jp>
                        //<jp>作業場</jp>
                        //<en>Division of sendable (0:unsendable for workshop, 1:sendable for main station)</en>
                        //<en>Workshop</en>
                        if (castparam.getMainStation() == WORKPLACE)
                        {
                            station.setSendable(FALSE);
                        }
                        //<jp>代表ステーション</jp>
                        //<en>Main station</en>
                        else if (castparam.getMainStation() == MAINSTATION)
                        {
                            station.setSendable(TRUE);
                        }

                        //<jp>状態（0:切離中）</jp>
                        //<en>status (0:off-line)</en>
                        station.setStatus(Station.STATION_NG);
                        //<jp>中断中フラグ（0:使用可能）</jp>
                        //<en>suspention flag (0:available)</en>
                        station.setSuspend(FALSE);
                        //<jp>払出し区分（作業場は0:払出し可、代表ステーションは紐づくステーションの払出し区分</jp>
                        //<jp>作業場</jp>
                        //<en>Workshop</en>
                        if (castparam.getMainStation() == WORKPLACE)
                        {
                            station.setRemove(TRUE);
                        }
                        //<jp>代表ステーション</jp>
                        //<en>Main station</en>
                        else if (castparam.getMainStation() == MAINSTATION)
                        {
                            station.setRemove(castparam.isRemove());
                        }
                        //<jp>在庫確認チェック（0:在庫確認未作業）</jp>
                        //<en>inventory check (0:unprocessed)</en>
                        station.setInventoryCheckFlag(Station.NOT_INVENTORYCHECK);
                        //<jp>現在作業モード（0:ニュートラルモード）</jp>
                        //<en>current work mode (0: neutral)</en>
                        station.setCurrentMode(Station.NEUTRAL);
                        //<jp>モード切替要求区分（モード切替要求なし）</jp>
                        //<en>mode switch request type(no request)</en>
                        station.setModeRequest(Station.NO_REQUEST);

                        getToolWorkPlaceHandler(conn).create(station);
                    }
                    //<jp>***入力された作業場が存在する場合***</jp>
                    //<en>***If the entered workshop exists,***</en>
                    else
                    {
                        //<jp>種別はステーションの種別から判断する（すべて入庫、すべて出庫、すべて入出庫兼用、種別が違う場合）</jp>
                        //<en>Type will be determined based on the station type. (all storage, all retrieval, all available</en>
                        //<en>for sotrage/retrieval, of different types)</en>
                        int stype = castparam.getType();
                        int flag = 0;
                        for (int i = 0; i < mArray.length; i++)
                        {
                            workparam = (WorkPlaceParameter)mArray[i];
                            //<jp>種別が違う場合</jp>
                            //<en>If the types are different,</en>
                            if (stype == Station.STATION_TYPE_IN || stype == Station.STATION_TYPE_OUT)
                            {
                                if (stype != workparam.getType())
                                {
                                    flag = 3;
                                }
                            }
                            //<jp>すべて入出庫兼用</jp>
                            //<en>all available of storage/retrieval</en>
                            else if (stype == Station.STATION_TYPE_INOUT)
                            {
                                flag = 3;
                            }
                        }
                        alterkey = new ToolStationAlterKey();
                        alterkey.setStationNo(headparam.getParentNumber());
                        if (flag == 0)
                        {
                            //<jp>すべてが入庫の場合</jp>
                            //<en>all storage case</en>
                            if (stype == Station.STATION_TYPE_IN)
                            {
                                alterkey.updateStationType(Station.STATION_TYPE_IN);
                            }
                            //<jp>すべてが出庫の場合</jp>
                            //<en>all retrieval case</en>
                            else if (stype == Station.STATION_TYPE_OUT)
                            {
                                alterkey.updateStationType(Station.STATION_TYPE_OUT);
                            }
                        }
                        //<jp>入庫と出庫の混在、入出庫兼用の場合</jp>
                        //<en>If both storage and retrieval are inxluded, if storage/retrieval available</en>
                        else if (flag == 3)
                        {
                            alterkey.updateStationType(Station.STATION_TYPE_INOUT);
                        }

                        //<jp>アイルステーションNo.はステーションのアイルステーションをセットする</jp>
                        //<jp>違うアイルステーションNo.のステーション／作業場を同じ作業場にする場合、空白をセットする</jp>
                        //<en>For aisle station no., set the aisle station of the station.</en>
                        //<en>If setting the same workshop for station/workshop of different asile station no.,</en>
                        //<en> set blank.</en>
                        String aisle = castparam.getAisleNumber();
                        int fg = 0;
                        for (int i = 0; i < mArray.length; i++)
                        {
                            wkparam = (WorkPlaceParameter)mArray[i];
                            if (!wkparam.getAisleNumber().equals(aisle))
                            {
                                fg = 1;
                            }
                        }
                        alterkey = new ToolStationAlterKey();
                        alterkey.setStationNo(castparam.getParentNumber());
                        if (fg == 1)
                        {
                            alterkey.updateAisleStationNo("");
                        }
                        else if (fg == 0)
                        {
                            alterkey.updateAisleStationNo(castparam.getAisleNumber());
                        }

                        //<jp>搬送指示可能数（作業場は0、代表ステーションは全配下ステーションの合計）</jp>
                        //<en>Number of carry instruction sendable (0 for workshop, total of unde classes for main station)</en>
                        alterkey = new ToolStationAlterKey();
                        alterkey.setStationNo(headparam.getParentNumber());
                        //<jp>作業場</jp>
                        //<en>Workshop</en>
                        if (castparam.getMainStation() == WORKPLACE)
                        {
                            alterkey.updateMaxInstruction(0);
                        }
                        //<jp>代表ステーション</jp>
                        //<en>Main station</en>
                        else if (castparam.getMainStation() == MAINSTATION)
                        {
                            int isum = 0;
                            for (int i = 0; i < mArray.length; i++)
                            {
                                castparam = (WorkPlaceParameter)mArray[i];
                                isum += castparam.getMaxInstruction();
                            }
                            alterkey.updateMaxInstruction(isum);
                        }

                        //<jp>出庫指示可能数（作業場は0、代表ステーションは全配下ステーションの合計）</jp>
                        //<jp>作業場</jp>
                        //<en>Number of retrieval instruction sendable (0 for workshop, total of unde classes for main station)</en>
                        //<en>Workshop</en>
                        if (castparam.getMainStation() == WORKPLACE)
                        {
                            alterkey.updateMaxPalletQuantity(0);
                        }
                        //<jp>代表ステーション</jp>
                        //<en>Main station</en>
                        else if (castparam.getMainStation() == MAINSTATION)
                        {
                            int psum = 0;
                            for (int i = 0; i < mArray.length; i++)
                            {
                                castparam = (WorkPlaceParameter)mArray[i];
                                psum += castparam.getMaxPalletQuantity();
                            }
                            alterkey.updateMaxPalletQuantity(psum);
                        }

                        //<jp>AGCNo.（作業場は0、代表ステーションはステーションのAGCNo.をセットする）</jp>
                        //<jp>作業場</jp>
                        //<en>AGCNo. (set 0 for workshop, or set AGCno. of the station for main station)</en>
                        //<en>workshop</en>
                        if (castparam.getMainStation() == WORKPLACE)
                        {
                            alterkey.updateControllerNo(0);
                        }
                        //<jp>代表ステーション</jp>
                        //<en>Main station</en>
                        else if (castparam.getMainStation() == MAINSTATION)
                        {
                            alterkey.updateControllerNo(castparam.getControllerNumber());
                        }

                        //<jp>クラス名称（作業場は空白、代表ステーションはステーションのクラス名称をセットする）</jp>
                        //<jp>作業場</jp>
                        //<en>Class name (set blank for workshop, or set class name of the station for main station)</en>
                        //<en>Workshop</en>
                        if (castparam.getMainStation() == WORKPLACE)
                        {
                            alterkey.updateClassName("");
                        }
                        //<jp>代表ステーション</jp>
                        //<en>Main station</en>
                        else if (castparam.getMainStation() == MAINSTATION)
                        {
                            alterkey.updateClassName(castparam.getClassName());
                        }

                        //<jp>再入庫搬送指示（作業場は0:搬送指示不要、代表ステーションは紐づくステーションの再入庫搬送指示</jp>
                        //<jp>作業場</jp>
                        //<en>Workshop</en>
                        if (castparam.getMainStation() == WORKPLACE)
                        {
                            alterkey.updateReStoringInstruction(Station.AGC_STORAGE_SEND);
                        }
                        //<jp>代表ステーション</jp>
                        //<en>Main station</en>
                        else if (castparam.getMainStation() == MAINSTATION)
                        {
                            alterkey.updateReStoringInstruction(castparam.getReStoringInstruction());
                        }

                        //<jp>払出し区分（作業場は0:払出し可、代表ステーションは紐づくステーションの払出し区分</jp>
                        //<jp>作業場</jp>
                        //<en>Workshop</en>
                        if (castparam.getMainStation() == WORKPLACE)
                        {
                            alterkey.updateRemove(Station.PAYOUT_OK);
                        }
                        //<jp>代表ステーション</jp>
                        //<en>Main station</en>
                        else if (castparam.getMainStation() == MAINSTATION)
                        {
                            int pay = castparam.isRemove() ? Station.PAYOUT_OK
                                                          : Station.PAYOUT_NG;
                            alterkey.updateRemove(pay);
                        }

                        // 更新
                        tsh.modify(alterkey);
                    }
                }
            }

            //<jp>***親ステーションにセットされていない作業場と代表ステーションを削除***</jp>
            //<jp>STATION表のデータを取得</jp>
            //<en>***Delete workshop and main station which are not set to parent stations.***</en>
            //<en>Retrieve data in STATION table.</en>
            ToolStationSearchKey wStation = new ToolStationSearchKey();
            Station[] array = (Station[])tsh.find(wStation);

            //<jp>作業場と代表ステーションのデータを取得</jp>
            //<en>Retrieve data of workshop and main station.</en>
            ToolStationSearchKey wStation2 = new ToolStationSearchKey();
            int[] temp_workplace = {
                Station.STAND_ALONE_STATIONS,
                Station.AISLE_CONMECT_STATIONS,
                Station.MAIN_STATIONS
            };
            wStation2.setWorkPlaceType(temp_workplace);
            Station[] array2 = (Station[])tsh.find(wStation2);

            if (array2.length > 0)
            {
                for (int i = 0; i < array2.length; i++)
                {
                    String wk2 = array2[i].getStationNo();
                    int flg = 0;
                    for (int j = 0; j < array.length; j++)
                    {
                        String wk = array[j].getParentStationNo();
                        if (wk2.equals(wk))
                        {
                            flg = 1;
                        }

                    }
                    if (flg == 0)
                    {
                        wStation2.setStationNo(wk2);
                        tsh.drop(wStation2);
                    }
                }
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
        catch (InvalidDefineException e)
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
     * Implement the check in order to see that the identical data has been selected when 
     * chosing data from the list box to edit.
     * In the setting of stations, it checks whether/not the storage type of appending parameter
     * exists in the entered data summary.
     * @param param  :the parameter which will be appended in this process
     * @param array  :entered data summary (pool)
     * @return       :return true if identical data exists.
     </en>*/
    private boolean isSameData(WorkPlaceParameter param, Parameter[] array)
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
                WorkPlaceParameter castparam = (WorkPlaceParameter)array[i];
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
        stationKey.setStationNoOrder(1, true);

        //<jp>*** Stationインスタンスを取得 ***</jp>
        //<en>*** Retrieve the Station instance.***</en>
        Station[] array = (Station[])getToolWorkPlaceHandler(conn).find(stationKey);
        return array;
    }
}
//end of class

