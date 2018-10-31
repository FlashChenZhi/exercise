// $Id: Id33Process.java 8073 2013-12-03 06:56:52Z fukuwa $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id33;
import jp.co.daifuku.wms.asrs.communication.as21.ControlInfo;
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;
import jp.co.daifuku.wms.asrs.communication.as21.SystemTextTransmission;
import jp.co.daifuku.wms.asrs.control.CarryCompleteOperator.CARRY_COMPLETE;
import jp.co.daifuku.wms.asrs.location.CombineZoneSelector;
import jp.co.daifuku.wms.asrs.location.DepthShelfSelector;
import jp.co.daifuku.wms.asrs.location.FreeAllocationShelfOperator;
import jp.co.daifuku.wms.asrs.location.ShelfOperator;
import jp.co.daifuku.wms.asrs.location.ShelfSelector;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.ZoneSelector;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.AsPalletController;
import jp.co.daifuku.wms.base.controller.AsStockController;
import jp.co.daifuku.wms.base.controller.AsWorkInfoController;
import jp.co.daifuku.wms.base.controller.HostSendController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HardZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.HardZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InOutResultAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InOutResultHandler;
import jp.co.daifuku.wms.base.dbhandler.LoadSizeHandler;
import jp.co.daifuku.wms.base.dbhandler.LoadSizeSearchKey;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WidthHandler;
import jp.co.daifuku.wms.base.dbhandler.WidthSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.base.entity.InOutResult;
import jp.co.daifuku.wms.base.entity.LoadSize;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.SysDate;

/**<jp>
 * 作業完了報告を処理するクラスです。mckeyよりCarryInformationを生成し、
 * 搬送区分を元に到着処理を行います。<BR>
 * 搬送区分が入庫の場合、完了区分に従い入庫作業完了処理を行います。<BR>
 * 搬送区分が出庫の場合、完了区分に従い出庫完了処理を行います。 CarryInformationが空棚確認搬送データの場合、
 * 空棚確認用Palletの削除を行います。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/05/11</TD><TD>Inoue</TD><TD>他に引き当てられているパレットの入庫完了場合、RetrievalSenderをキックするように変更</TD></TR>
 * <TR><TD>2006/02/02</TD><TD>Y.Okamura</TD><TD>eWareNavi対応。二重格納、荷姿不一致時在庫の棚を変更。その実績を作成するよう変更</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8073 $, $Date: 2013-12-03 15:56:52 +0900 (火, 03 12 2013) $
 * @author  $Author: fukuwa $
 </jp>*/
/**<en>
 * This class processes the work completion report. It generates CarryInformation from mc key, then
 * processes the arrival according to the transport section.<BR>
 * When the transport section is storage, it processes the completion of storage work according to the classification of completion. <BR>
 * When the transport section is retrieval, it processes the completion of retrieval work according to the classification of completion.
 * If CarryInformation was the carry data of empty location check, it deletes the pallet for the empty location check.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/05/11</TD><TD>Inoue</TD><TD>In case of multi allocated pallet's storage completion,kick the retrieval sender</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8073 $, $Date: 2013-12-03 15:56:52 +0900 (火, 03 12 2013) $
 * @author  $Author: fukuwa $
 </en>*/
public class Id33Process
        extends IdProcess
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    //<jp> 搬送区分(入庫)</jp>
    //<en> Transport section (storage)</en>
    private static final int TRANS_CLASS_IN = 1;

    //<jp> 搬送区分(出庫)</jp>
    //<en> Transport section (retrieval)</en>
    private static final int TRANS_CLASS_OUT = 2;

    //<jp> 搬送区分(棚間移動-出庫)</jp>
    //<en> Transport section (location to location move - retrieval)</en>
    private static final int TRANS_CLASS_MOVE_OUT = 4;

    //<jp> 搬送区分(棚間移動-入庫)</jp>
    //<en> Transport section (location to location move - storage)</en>
    private static final int TRANS_CLASS_MOVE_IN = 5;

    //<jp> 完了区分(正常)</jp>
    //<en> Classification of completion (normal)</en>
    private static final int COMP_CLASS_NORMAL = 0;

    //<jp> 完了区分(二重格納)</jp>
    //<en> Classification of completion (double occupation)</en>
    private static final int COMP_CLASS_DUP = 1;

    //<jp> 完了区分(空出荷)</jp>
    //<en>Classification of completion (empty retrieval)</en>
    private static final int COMP_CLASS_EMPTY = 2;

    //<jp> 完了区分(荷姿不一致)</jp>
    //<en> Classification of completion (load size unmatch)</en>
    private static final int COMP_CLASS_DIM = 3;

    //<jp> 完了区分(空棚確認-空棚)</jp>
    //<en> Classification of completion (empty location check - empty)</en>
    private static final int COMP_CLASS_COMPARE_EMPTY = 7;

    //<jp> 完了区分(空棚確認-実棚)</jp>
    //<en> Classification of completion (empty location check - loaded)</en>
    private static final int COMP_CLASS_COMPARE_FULL = 8;

    //<jp> 完了区分(キャンセル)</jp>
    //<en> Classification of completion (cancel)</en>
    private static final int COMP_CLASS_CANCEL = 9;

    private static final String CLASS_NAME = Id33Process.class.getSimpleName();

    private static final String LONG_CLASS_NAME = Id33Process.class.getName();

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    private AsWorkInfoController _workInfoCtlr;

    private HostSendController _hostSendCtlr;

    private AreaController _areaCtlr;

    private AsStockController _asStockCtlr;

    private WarenaviSystemController _warenaviSysCtlr;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**<jp>
     * デフォルトコンストラクタ
     * AGCNoにGroupController.DEFAULT_AGC_NUMBERをセット
     </jp>*/
    /**<en>
     * Default constructor
     * Set GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     </en>*/
    public Id33Process()
    {
        super();
    }

    /**<jp>
     * 引数で渡されたAGCNoをセットし、このクラスの初期化を行います。
     * @param agcNumber AGCNo
     </jp>*/
    /**<en>
     * Sets the AGCNo passed through parameter, then initialize this class.
     * @param agcNumber AGCNo
     </en>*/
    public Id33Process(String agcNumber)
    {
        super(agcNumber);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**<jp>
     * 出庫作業完了報告の正常処理を行います。CarryInforamtionの出庫指示詳細をもとにShelfの更新を行います。
     * このメソッドは、出庫作業完了報告テキスト受信時以外にも、到着報告処理時に対象のCarryInformationの
     * 状態が応答待ちになっていた場合のテキスト飛び越し対策として使用されます。
     * @param conn データベース接続用 Connection
     * @param  ci  更新対象CarryInformation
     * @throws InvalidDefineException 更新条件またはテーブルの構造が正しくなかった場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Normal process of retrieval completion report. It updates Shelf based on the retrieval instruction detail of CarryInforamtion.
     * Besides when receiving the retrieval completion report text, this method will be used as
     * a measure to skip texts when status of target CarryInformation was 'wait for response' during the arrival report process.
     * @param conn :Connection with database
     * @param  ci  :CarryInformation updated
     * @throws InvalidDefineException :Notifies if update conditions or table structure was not correct.
     * @throws ReadWriteException :Notifies if error occured when accessing database.
     * @throws ScheduleException
     </en>*/
    public static void normalRetrievalCompletion(Connection conn, CarryInfo ci)
            throws InvalidDefineException,
                ReadWriteException,
                ScheduleException
    {
        Shelf fromShelf = null;

        PalletSearchKey pSKey = new PalletSearchKey();
        PalletHandler pHandle = new PalletHandler(conn);

        try
        {
            pSKey.setPalletId(ci.getPalletId());
            Pallet[] pallet = (Pallet[])pHandle.find(pSKey);

            Station st = StationFactory.makeStation(conn, pallet[0].getCurrentStationNo());
            if (st instanceof Shelf)
            {
            	// 搬送区分が入庫の場合飛び越処理が行われたとみなし処理は終了
            	if (CarryInfo.CARRY_FLAG_STORAGE.equals(ci.getCarryFlag()))
            	{
            		return;
            	}

                ShelfAlterKey shelfAKey = new ShelfAlterKey();
                ShelfHandler shelfHandle = new ShelfHandler(conn);

                fromShelf = (Shelf)st;

                //<jp> ユニット出庫の場合、棚の在荷を解除する。</jp>
                //<en> Releases the load presene in location at unit retrieval.</en>
                if (CarryInfo.RETRIEVAL_DETAIL_UNIT.equals(ci.getRetrievalDetail()))
                {
                    if (!CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
                    {
                        FreeAllocationShelfOperator freeshelfop =
                                new FreeAllocationShelfOperator(conn, fromShelf.getStationNo());
                        // フリーアロケーション運用の場合、荷幅、棚使用フラグをフリーに更新
                        if (freeshelfop.isFreeAllocationStation())
                        {
                            freeshelfop.alterFreeWidth();
                        }

                        shelfAKey.clear();
                        shelfAKey.setStationNo(fromShelf.getStationNo());
                        shelfAKey.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
                        shelfHandle.modify(shelfAKey);
                    }
                    else
                    {
                        // 棚間移動出庫完了処理
                        CarryCompleteOperator cop = new CarryCompleteOperator(conn, Id33Process.class);
                        cop.retrievalRackToRackMove(ci);
                    }
                }
                //<jp> ピッキング出庫、在庫確認、積増入庫の場合、</jp>
                //<jp> 同一棚に再入庫の場合棚を予約にしておく。</jp>
                //<en> Reserves th location for pick retrieval, inventory check and replenishment and if</en>
                //<en> re-storing to the same location.</en>
                else if (CarryInfo.RETRIEVAL_DETAIL_PICKING.equals(ci.getRetrievalDetail())
                        || CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(ci.getRetrievalDetail())
                        || CarryInfo.RETRIEVAL_DETAIL_ADD_STORING.equals(ci.getRetrievalDetail()))
                {
                    Station aislleStation = StationFactory.makeStation(conn, fromShelf.getParentStationNo());
                    if (aislleStation instanceof Aisle)
                    {
                        Aisle aisle = (Aisle)aislleStation;
                        if (aisle.getDoubleDeepKind().equals(Aisle.DOUBLE_DEEP_KIND_DOUBLE))
                        {
                            //DoubleDeepで、在庫確認の場合。
                            if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(ci.getRetrievalDetail()))
                            {
                                if (ci.getRetrievalStationNo().equals(ci.getSourceStationNo()))
                                {
                                    // 出庫棚は棚状態を入庫予約にしておく
                                    Station retrievalStation =
                                            StationFactory.makeStation(conn, ci.getRetrievalStationNo());
                                    Shelf retrievalShelf = (Shelf)retrievalStation;
                                    shelfAKey.clear();
                                    shelfAKey.setStationNo(retrievalShelf.getStationNo());
                                    shelfAKey.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_RESERVATION);
                                    shelfHandle.modify(shelfAKey);
                                }
                                else
                                {
                                    // 出庫棚を空棚にする。
                                    shelfAKey.clear();
                                    shelfAKey.setStationNo(fromShelf.getStationNo());
                                    shelfAKey.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
                                    shelfHandle.modify(shelfAKey);
                                }
                            }
                            else
                            {
                                // 在庫確認以外の場合、ダブルディープは出庫棚を空棚にする。
                                shelfAKey.clear();
                                shelfAKey.setStationNo(fromShelf.getStationNo());
                                shelfAKey.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
                                shelfHandle.modify(shelfAKey);
                            }
                        }
                        else
                        {
                            // ダブルディープでない時。
                            // 同一棚に再入庫、又は在庫確認の場合、出庫元棚を入庫予約にする
                            if (CarryInfo.RESTORING_FLAG_SAME_LOC.equals(ci.getRestoringFlag())
                                    || CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(ci.getRetrievalDetail()))
                            {
                                shelfAKey.clear();
                                shelfAKey.setStationNo(fromShelf.getStationNo());
                                shelfAKey.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_RESERVATION);
                                shelfHandle.modify(shelfAKey);
                            }
                            // 同一棚に戻らない場合、出庫元棚を空棚にする
                            else
                            {
                                FreeAllocationShelfOperator freeshelfop =
                                        new FreeAllocationShelfOperator(conn, fromShelf.getStationNo());
                                // フリーアロケーション運用の場合、荷幅、棚使用フラグをフリーに更新
                                if (freeshelfop.isFreeAllocationStation())
                                {
                                    freeshelfop.alterFreeWidth();
                                }

                                shelfAKey.clear();
                                shelfAKey.setStationNo(fromShelf.getStationNo());
                                shelfAKey.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
                                shelfHandle.modify(shelfAKey);
                            }
                        }
                    }
                    else
                    {
                        // 6026035=取得したインスタンスが不正です。インスタンス={0}
                        Object[] tObj = {
                            (Object)aislleStation.getClass().getSimpleName()
                        };
                        RmiMsgLogClient.write(6026035, LogMessage.F_WARN, LONG_CLASS_NAME, tObj);
                    }
                }
                //<jp> 取得したインスタンスの出庫指示詳細が不正</jp>
                //<en> The retrieval instruction detail of instance obtained was invalid.</en>
                else
                {
                    //<jp> 6024018=取得したインスタンス{0}の属性{1}の値が不正です。{1}={2}</jp>
                    //<en> 6024018=Attribute {1} value of acquired instance {0} is invalid. {1}={2}</en>
                    Object[] tObj = {
                        "CarryInfomation",
                        "RetrievalDetail",
                        String.valueOf(ci.getRetrievalDetail()),
                    };
                    RmiMsgLogClient.write(6024018, LogMessage.F_WARN, LONG_CLASS_NAME, tObj);
                }

                CarryInfoAlterKey cakey = new CarryInfoAlterKey();
                CarryInfoHandler carryInfoHandle = new CarryInfoHandler(conn);

                //<jp> 搬送状態確認</jp>
                //<en> Checks the carry status.</en>

                //<jp> 応答待ち、指示済み</jp>
                //<en> Wait for response, insturction given</en>
                if (CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(ci.getCmdStatus())
                        || CarryInfo.CMD_STATUS_INSTRUCTION.equals(ci.getCmdStatus()))
                {
                    //<jp> 搬送状態フラグを完了に更新</jp>
                    //<en> Updates the carry status flag to 'complete'.</en>
                    cakey.updateCmdStatus(CarryInfo.CMD_STATUS_COMP_RETRIEVAL);
                    cakey.setCarryKey(ci.getCarryKey());
                    // WareNavi3.5.4
                    String[] cmdstatus = {
                            CarryInfo.CMD_STATUS_WAIT_RESPONSE,
                            CarryInfo.CMD_STATUS_INSTRUCTION,
                    };
                    cakey.setCmdStatus(cmdstatus, true);
                    cakey.updateLastUpdatePname(CLASS_NAME);
                    try
                    {
                        carryInfoHandle.modify(cakey);
                    }
                    catch (NotFoundException e)
                    {
                        CarryInfoSearchKey carrKey = new CarryInfoSearchKey();

                        carrKey.setCarryKey(ci.getCarryKey());
                        // 別スレッドで更新処理が行われているかの確認
                        CarryInfo[] carries = (CarryInfo[])carryInfoHandle.find(carrKey);

                        if (carries.length > 0)
                        {
                            String cmdStatus = carries[0].getCmdStatus();
                            // 搬送情報の搬送状態が応答待ち、指示済みでないの場合、と判断する。
                            if (!CarryInfo.CMD_STATUS_INSTRUCTION.equals(cmdStatus) &&
                                !CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(cmdStatus))
                            {
                                //<jp> 6024018=取得したインスタンス{0}の属性{1}の値が不正です。{1}={2}</jp>
                                //<en> 6024018=Attribute {1} value of acquired instance {0} is invalid. {1}={2}</en>
                                Object[] tObj = {
                                        "CarryInfomation",
                                        "cmd_status",
                                        cmdStatus,
                                };
                                RmiMsgLogClient.write(6024018, LogMessage.F_WARN, LONG_CLASS_NAME, tObj);
                            }
                        }

                        try
                        {
                            conn.rollback();
                        }
                        catch (SQLException se)
                        {
                            throw new ReadWriteException(se);
                        }
                        return;
                    }
                    cakey.clearKeys();
                    // WareNavi3.5.4

                    //<jp> 出庫ロケーションIDを更新</jp>
                    //<jp> ただし、在庫確認データの場合、在庫確認スケジュールで設定される値を使用するので</jp>
                    //<jp> 更新は行わない（棚間移動対策）</jp>
                    //<en> Updates the retrieval location ID.</en>
                    //<en> Except in case of inventory check data, the value set in stock check schedule will be used; </en>
                    //<en> therefore there is no updates. (measures for location to location moves)</en>
                    if (!CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(ci.getRetrievalDetail()))
                    {
                        cakey.updateRetrievalStationNo(fromShelf.getStationNo());
                        cakey.setCarryKey(ci.getCarryKey());
                        cakey.updateLastUpdatePname(CLASS_NAME);
                        carryInfoHandle.modify(cakey);
                    }
                    else
                    {
                        if (StringUtil.isBlank(ci.getRetrievalStationNo()))
                        {
                            //<jp> 在庫確認の場合は事前に出庫ロケーションNoが</jp>
                            //<jp> セットされている事が前提だが、セットされていない場合はセットする。</jp>
                            //<en> In case of inventory check, retrieval location no. is supposed to </en>
                            //<en> have been set; if not, set the location no. here.</en>
                            cakey.updateRetrievalStationNo(fromShelf.getStationNo());
                            cakey.setCarryKey(ci.getCarryKey());
                            cakey.updateLastUpdatePname(CLASS_NAME);
                            carryInfoHandle.modify(cakey);
                        }
                    }
                }
                //<jp> 取得したインスタンスの出庫指示詳細が不正</jp>
                //<en> The retrieval instruction detail of instance obtained was invalid.</en>
                else
                {
                    //<jp> 6024018=取得したインスタンス{0}の属性{1}の値が不正です。{1}={2}</jp>
                    //<en> 6024018=Attribute {1} value of acquired instance {0} is invalid. {1}={2}</en>
                    Object[] tObj = {
                        "CarryInfomation",
                        "CmdStatus",
                        String.valueOf(ci.getCmdStatus()),
                    };
                    RmiMsgLogClient.write(6024018, LogMessage.F_WARN, LONG_CLASS_NAME, tObj);
                }
            }
            else
            {
                //<jp> 生成されたインスタンスがShelfでなかった場合は例外を返す。</jp>
                //<en> Returns exception if the instance generated was not Shelf.</en>
                Object[] tObj = {
                    "Shelf",
                };
                //<jp> 6006008={0}以外のオブジェクトが返されました。</jp>
                //<en> 6006008=Object other than {0} was returned.</en>
                RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, LONG_CLASS_NAME, tObj);
                throw new InvalidDefineException(WmsMessageFormatter.format(6006008, tObj));
            }
        }
        catch (NotFoundException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * WorkInfoControllerを返します。
     *
     * @return WorkInfoController
     * @throws ReadWriteException データベース接続エラー
     */
    protected AsWorkInfoController getWorkInfoCtlr()
            throws ReadWriteException
    {
        if (null == _workInfoCtlr)
        {
            _workInfoCtlr = new AsWorkInfoController(getConnection(), getClass());
        }
        return _workInfoCtlr;
    }

    /**
     * HostSendControllerを返します。
     * @return HostSendController
     * @throws ReadWriteException データベース接続エラー
     */
    protected HostSendController getHostSendCtlr()
            throws ReadWriteException
    {
        if (null == _hostSendCtlr)
        {
            _hostSendCtlr = new HostSendController(getConnection(), getClass());
        }
        return _hostSendCtlr;
    }

    /**
     * AS/RS在庫コントローラを取得します。
     *
     * @return AsStockController
     * @throws ScheduleException 定義異常
     * @throws ReadWriteException データベース接続エラー
     */
    private AsStockController getAsStockCtlr()
            throws ReadWriteException,
                ScheduleException
    {
        if (null == _asStockCtlr)
        {
            _asStockCtlr = new AsStockController(getConnection(), getClass());
        }
        return _asStockCtlr;
    }

    /**
     * AreaControllerを返します。
     *
     * @return AreaController
     * @throws ReadWriteException データベース接続エラー
     */
    private AreaController getAreaCtlr()
            throws ReadWriteException
    {
        if (null == _areaCtlr)
        {
            _areaCtlr = new AreaController(getConnection(), getClass());
        }
        return _areaCtlr;
    }

    /**
     * WarenaviSystemControllerを返します。
     * @return WarenaviSystemControllerを返します。
     * @throws ScheduleException システム定義が異常
     * @throws ReadWriteException データベースアクセスエラー
     */
    private WarenaviSystemController getWarenaviSysCtlr()
            throws ReadWriteException,
                ScheduleException
    {
        if (null == _warenaviSysCtlr)
        {
            _warenaviSysCtlr = new WarenaviSystemController(getConnection(), getClass());
        }
        return _warenaviSysCtlr;
    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**<jp>
     * 作業完了電文を処理します。
     * 受信した電文のMC Keyから、<code>CarryInformation</code>を検索しそれぞれの処理を行います。
     * ただし、トランザクションのコミット・ロールバックは行っていませんので、
     * 呼び出し元で行う必要があります。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processes the communication message for work completion.
     * Based on the MC Key of received communication message, it searches <code>CarryInformation</code>
     * and processes respectively.
     * However the call source needs to commit or rollback the transaction, as they are not done here.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        try
        {
            As21Id33 id33dt = new As21Id33(rdt);

            //<jp> 搬送データ数分処理</jp>
            //<en> Processes as much carry data received.</en>
            for (int i = 0; i < id33dt.getMcKey().length; i++)
            {
                //<jp> 搬送区分ごとに処理を振り分け</jp>
                //<en> Allocates hte processes according to the transport section.</en>
                int[] transClass = id33dt.getTransportationClassification();
                switch (transClass[i])
                {
                    //<jp> 入庫又は棚間移動（入庫）</jp>
                    //<en> Storage or location to location move (storage)</en>
                    case TRANS_CLASS_IN:
                    case TRANS_CLASS_MOVE_IN:
                        storageCompletion(id33dt, i);
                        break;

                    //<jp> 出庫処理又は棚間移動（出庫）</jp>
                    //<en> Retrieval or location to location move (retrieval)</en>
                    case TRANS_CLASS_OUT:
                    case TRANS_CLASS_MOVE_OUT:
                        retrievalCompletion(id33dt, i);
                        break;

                    //<jp> 完了区分異常</jp>
                    //<en> COmpletion classification error</en>
                    default:
                        // 6024016=作業完了報告テキスト内の搬送区分が不正です。搬送区分={0} mckey={1}
                        Object[] tObj = new Object[2];
                        tObj[0] = String.valueOf(transClass[i]);
                        tObj[1] = id33dt.getMcKey()[i];
                        RmiMsgLogClient.write(6024016, LogMessage.F_WARN, getClass().getName(), tObj);
                        throw new InvalidProtocolException(WmsMessageFormatter.format(6024016, tObj));
                }
            }
        }
        catch (Exception e)
        {
            // コントローラクリア処理
            clearCtlr();

            throw e;
        }
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**<jp>
     * 入庫完了（棚間移動の入庫も含む）処理を行います。
     * id33dtインスタンスの完了区分をもとに処理を分岐します。
     * 1 正常完了
     *   ・入庫棚の予約を解除し、在荷をONにする。
     *   ・入庫ロケーションと搬送先ステーション（入庫予定のロケーション）を比較し、
     *     一致しない場合棚の予約をクリア。
     *   ・搬送データを削除する。
     *   ・パレットの現在位置を入庫ロケーションにする。
     *   ・パレットに対して他の引当が存在しない場合は引当を解除し実棚にする。
     *   ・引当が存在する場合は出庫予約にする。
     * 2 二重格納
     *   ・二重格納処理を行なう。
     * 3 荷姿不一致
     *   ・荷姿不一致処理を行なう。
     * @param  id33dt  作業完了報告テキストの内容
     * @param  num     作業完了報告テキスト詳細データ配列の要素番号
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processes the storage completion (including the storage of location to location moves).
     * Branches the process acording to the completion classificaiton of id33dt isntance.
     * 1. Normal end
     *    - Releases the reservation of storage shelf and switch the load presernce ON.
     *    - Compares the storage location and receiving station (To-store location), then clear the
     *      reservations of locations which do not match.
     *    - Deletes the carry data.
     *    - Specifies that current position of pallet as storage locations.
     *    - If there is no other allocation exist to the pallet, releases the allocation and it modifies
     *      to loaded location. If allocation exists, reserve it for retrieval.
     * 2 Double occupation
     *    - Processing the doube operation.
     * 3 Load size unmatch
     *    - Processing the load size unmatch
     * @param  id33dt  :text content of work completion report
     * @param  num     :element no. of data arrays for work completion report text detail
     * @throws  Exception  :in case any error occured
     </en>*/
    @SuppressWarnings("cast")
    private void storageCompletion(As21Id33 id33dt, int num)
            throws Exception
    {
        //<jp> 変数の定義 -------------------------------------</jp>
        //<en> Definition of variable-----------------------------------</en>
        CarryInfoHandler carrH = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey carrKey = new CarryInfoSearchKey();
        CarryInfoAlterKey carrAKey = new CarryInfoAlterKey();

        PalletSearchKey pltKey = new PalletSearchKey();
        PalletAlterKey pltAKey = new PalletAlterKey();
        PalletHandler pltH = new PalletHandler(getConnection());

        int[] compClass = id33dt.getCompletionClassification();
        CarryInfo ci;
        Object[] tObj = null;

        String compStorageLocation = id33dt.getLocationNo()[num];

        //<jp> 処理の開始 -------------------------------------</jp>
        //<jp> MC keyを検索条件として設定</jp>
        //<en> Start of process-------------------------------------</en>
        //<en> Sets MC key as a search condition.</en>
        carrKey.setCarryKey(id33dt.getMcKey()[num]);

        //<jp> 該当のCarryInfoを取得</jp>
        //<en> Retrieves corresponding CarryInfo.</en>
        CarryInfo[] carries = (CarryInfo[])carrH.find(carrKey);

        //<jp> 該当データなし</jp>
        //<en> There is no corresponding data.</en>
        if (carries.length == 0)
        {
            //<jp> 6026038=指定されたmckeyの搬送データが存在しません。mckey={0}</jp>
            //<en> 6026038=Transfer data for the designated MCKey does not exist. mckey={0}</en>
            tObj = new Object[1];
            tObj[0] = id33dt.getMcKey()[num];
            RmiMsgLogClient.write(6026038, LogMessage.F_ERROR, getClass().getName(), tObj);
            return;
        }

        if (carries[0] instanceof CarryInfo)
        {
            ci = (CarryInfo)carries[0];
        }
        else
        {
            tObj = new Object[1];
            tObj[0] = "CarryInformation";
            //<jp> 6006008={0}以外のオブジェクトが返されました。</jp>
            //<en> 6006008=Object other than {0} was returned.</en>
            RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, getClass().getName(), tObj);
            throw new InvalidProtocolException(WmsMessageFormatter.format(6006008, tObj));
        }

        //<jp> 行き先ステーションのインスタンス取得</jp>
        //<en> Retrieves the instance of destines station.</en>
        Station toStation = StationFactory.makeStation(getConnection(), compStorageLocation);
        Shelf toShelf = null;
        if (toStation instanceof Shelf)
        {
            toShelf = (Shelf)toStation;
        }
        else
        {
            tObj = new Object[1];
            tObj[0] = "Shelf";
            //<jp> 6006008={0}以外のオブジェクトが返されました。</jp>
            //<en> 6006008=Object other than {0} was returned.</en>
            RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, getClass().getName(), tObj);
            throw new InvalidProtocolException(WmsMessageFormatter.format(6006008, tObj));
        }

        String cmdStatus = ci.getCmdStatus();
        //<jp> CarryInforamtionの搬送状態が応答待ちの場合、テキストが飛び越されたと判断し、</jp>
        //<jp> 先に搬送指示応答の正常処理を行う。</jp>
        //<en> If carry status of CarryInforamtion is 'wait for resonse', it determines that texts has been skipped;</en>
        //<en> it firstly processes the response to carry instruction.</en>
        if (CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(cmdStatus))
        {
            if (!CarryInfo.WORK_TYPE_RACKMOVE_FROM.equals(ci.getWorkType()))
            {
                Connection conn = getConnection();
                //<jp> 搬送指示応答テキストが飛んでいることをロギングする</jp>
                //<en> Logging that response text to carry instruction has been skipped.</en>
                //<jp> 6022021=搬送指示応答処理が行われていません。強制的に搬送指示応答処理を行います。mckey={0}</jp>
                //<en> 6022021=No reply for transfer instruction. Forcing to complete picking. mckey={0}</en>
                tObj = new Object[1];
                tObj[0] = ci.getCarryKey();
                RmiMsgLogClient.write(6022021, LogMessage.F_ERROR, getClass().getName(), tObj);

                try
                {
                    //<jp> CarryInformationに対して、搬送指示応答処理を実行する。</jp>
                    //<en> Executes the reply to carry instruction for this CarryInformation.</en>
                    Id25Process.updateNormalResponce(conn, ci);

                    //<jp> すくい完了処理を行う</jp>
                    //<jp> 作業表示データのチェック、あれば削除</jp>
                    //<en> Processing the completion of pick up.</en>
                    //<en> Checks the on-line indication data, and delete if found any.</en>
                    OperationDisplayHandler odh = new OperationDisplayHandler(conn);
                    OperationDisplaySearchKey odkey = new OperationDisplaySearchKey();
                    odkey.setCarryKey(ci.getCarryKey());
                    if (odh.count(odkey) != 0)
                    {
                        odh.drop(odkey);
                    }
                }
                // 例外発生時も処理を続行する
                catch (Exception e)
                {
                    conn.rollback();
                    // 別スレッドでID25処理が行われているかの確認
                    carries = (CarryInfo[])carrH.find(carrKey);

                    //<jp> 該当データなし</jp>
                    //<en> There is no corresponding data.</en>
                    if (carries.length == 0)
                    {
                        return;
                    }
                    cmdStatus = carries[0].getCmdStatus();
                    Object[] tObject = {
                        carries[0].getCarryKey(),
                    };
                    // 搬送情報の搬送状態が応答待ちの場合、引き続き完了処理を行えないと判断する。
                    if (CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(cmdStatus))
                    {
                        //<jp> 6024048=受信電文飛び越し処理でエラーが発生したため、処理を中断しました。mckey={0} StackTrace={1}
                        RmiMsgLogClient.write(new TraceHandler(6024048, e), getClass().getName(), tObject);
                        return;
                    }
                    //<jp> 6024045=受信電文飛び越し処理でエラーが発生しましたが、処理を続行します。mckey={0} StackTrace={1}</jp>
                    RmiMsgLogClient.write(new TraceHandler(6024045, e), getClass().getName(), tObject);
                }
            }
            else
            {
                // 棚間移動出庫の応答待ち状態の時、搬送状態を「指示済み」に変更
                carrAKey.clear();
                carrAKey.updateCmdStatus(CarryInfo.CMD_STATUS_INSTRUCTION);
                carrAKey.setCarryKey(ci.getCarryKey());
                carrAKey.updateLastUpdatePname(getClassName());
                carrH.modify(carrAKey);

                // 搬送状態を更新したので、指示済みに変更する。
                cmdStatus = CarryInfo.CMD_STATUS_INSTRUCTION;
            }
        }

        //<jp> CarryInforamtionの搬送状態が指示済みか、</jp>
        //<jp> 到着の場合（出庫到着後の戻り入庫時はすくい完了を受信するまでは到着の状態）</jp>
        //<jp> すくい完了テキストが飛び越されたと判断し、先にすくい完了処理を行う。</jp>
        //<jp> 棚間移動で指示済みの場合は、出庫完了処理を行う。
        //<en> In case the instruction of carry status was given for CarryInforamtion,</en>
        //<en> it determines that</en>
        //<en> pick completion text was skipped and it processes the pick-up completi</en>
        if (CarryInfo.CMD_STATUS_INSTRUCTION.equals(cmdStatus) || CarryInfo.CMD_STATUS_ARRIVAL.equals(cmdStatus))
        {
            Connection conn = getConnection();
            try
            {
                if (!CarryInfo.WORK_TYPE_RACKMOVE_FROM.equals(ci.getWorkType()))
                {
                    //<jp> すくい完了処理を行う</jp>
                    //<jp> 作業表示データのチェック、あれば削除</jp>
                    //<en> Processing the pick-up completion.</en>
                    //<en> Checks the on-line indicationdata, the ndelete id any was found.</en>
                    OperationDisplayHandler odh = new OperationDisplayHandler(getConnection());
                    OperationDisplaySearchKey odkey = new OperationDisplaySearchKey();
                    odkey.setCarryKey(ci.getCarryKey());
                    if (odh.count(odkey) != 0)
                    {
                        odh.drop(odkey);
                    }
                }
                else
                {
                    // 6022022=出庫完了処理が行われていません。強制的に出庫完了処理行います。mckey={0}
                    tObj = new Object[1];
                    tObj[0] = ci.getCarryKey();
                    RmiMsgLogClient.write(6022022, LogMessage.F_ERROR, getClass().getName(), tObj);

                    // 棚間移動の出庫完了処理を行う。
                    normalRetrievalCompletion(conn, ci);

                    // 出庫完了処理を行い搬送情報を更新したので、搬送情報を読み直します。
                    carrKey.clear();
                    carrKey.setCarryKey(ci.getCarryKey());
                    ci = (CarryInfo)carrH.findPrimary(carrKey);
                }
            }
            // 例外発生時も処理を続行する
            catch (Exception e)
            {
                conn.rollback();
                // 別スレッドでID25処理が行われているか確認
                carries = (CarryInfo[])carrH.find(carrKey);

                //<jp> 該当データなし</jp>
                // 別スレッドでID25処理が行われているか確認
                if (carries.length == 0)
                {
                    return;
                }

                cmdStatus = carries[0].getCmdStatus();
                Object[] tObject = {
                    ci.getCarryKey(),
                };

                // 搬送状態が指示済み、到着の場合、引き続き完了処理を行えないと判断する
                if (CarryInfo.CMD_STATUS_INSTRUCTION.equals(cmdStatus) || CarryInfo.CMD_STATUS_ARRIVAL.equals(cmdStatus))
                {
                    //<jp> 6024048=受信電文飛び越し処理でエラーが発生したため、処理を中断しました。mckey={0} StackTrace={1}
                    RmiMsgLogClient.write(new TraceHandler(6024048, e), getClass().getName(), tObject);
                    return;
                }
                //<jp> 6024045=受信電文飛び越し処理でエラーが発生しましたが、処理を続行します。mckey={0} StackTrace={1}</jp>
                RmiMsgLogClient.write(new TraceHandler(6024045, e), getClass().getName(), tObject);
            }
        }

        //<jp> 実際の完了処理 ---------------------------------</jp>
        //<jp> 完了状態によって処理を振り分け</jp>
        //<en> Actual completion process ---------------------------------</en>
        //<en> Divides the processes according to the status of completions.</en>
        switch (compClass[num])
        {
            //<jp> 正常完了</jp>
            //<en> Normal end</en>
            case COMP_CLASS_NORMAL:

                // modify start 2010/07/16 作業完了時の在庫更新を修正
                // 在庫更新タイミングが搬送指示応答時の場合、在庫の更新を行う。
                if (WmsParam.STOCK_MODIFY_TIMING)
                {
                    CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection() ,getClass());
                    //<jp> 在庫更新を行う。</jp>
                    //<en> Updates the stocks.</en>
                    carryCompOpe.updateStock(ci);
                }

                String orgDestStNo = ci.getDestStationNo();
                // modify end 2010/07/16 作業完了時の在庫更新を修正

                //<jp> パレットを検索</jp>
                pltKey.clear();
                pltKey.setPalletId(ci.getPalletId());
                Pallet[] pallet = (Pallet[])pltH.findForUpdate(pltKey);
                Pallet pl;
                pl = pallet[0];

                ShelfAlterKey shelfAltKey = new ShelfAlterKey();
                ShelfHandler shelfHandle = new ShelfHandler(getConnection());

                //<jp> 入庫ロケーションと搬送先ステーションとの比較し、</jp>
                //<jp> 一致しない場合、棚の予約をクリア</jp>
                //<en> Compares the storage location with receiving station.</en>
                //<en> If htey do not match, clear the reservation of location.</en>
                if (!compStorageLocation.equals(orgDestStNo))
                {
                    // 6024035=入庫作業完了報告テキスト内のLocationNoが搬送予定先と異りました。棚を変更します。{0} ---> {1} mckey={2}
                    Object[] msg = new Object[3];
                    msg[0] = orgDestStNo;
                    msg[1] = compStorageLocation;
                    msg[2] = ci.getCarryKey();
                    RmiMsgLogClient.write(6024035, getClass().getName(), msg);

                    FreeAllocationShelfOperator fromfreeshelfop =
                            new FreeAllocationShelfOperator(getConnection(), orgDestStNo);
                    // フリーアロケーション運用の場合、搬送予定だった棚の荷幅、棚使用フラグをフリーに更新
                    if (fromfreeshelfop.isFreeAllocationStation())
                    {
                        fromfreeshelfop.alterFreeWidth();
                    }

                    //<jp> 搬送予定だった棚のステーションの予約を解除し、空棚にする</jp>
                    //<en> Releases the reservation of station which was to be carried, and  turn the load presence OFF.</en>
                    shelfAltKey.clear();
                    shelfAltKey.setStationNo(orgDestStNo);
                    shelfAltKey.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
                    shelfHandle.modify(shelfAltKey);

                    // フリーアロケーション運用の場合、入庫棚の荷幅、棚使用フラグを更新
                    FreeAllocationShelfOperator freeshelfop =
                            new FreeAllocationShelfOperator(getConnection(), compStorageLocation);
                    if (freeshelfop.isFreeAllocationStation())
                    {
                        freeshelfop.alterWidth(pl);
                    }
                }

//                // 在庫更新タイミングが搬送指示応答時の場合、在庫の更新を行う。
//                if (WmsParam.STOCK_MODIFY_TIMING)
//                {
//                    CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection() ,getClass());
//                    //<jp> 在庫更新を行う。</jp>
//                    //<en> Updates the stocks.</en>
//                    carryCompOpe.updateStock(ci);
//                }

                //<jp> 入庫棚の予約を解除し、実棚にする。</jp>
                //<en> Release the reservation of storage location, and turn the load presence ON.</en>
                shelfAltKey.clear();
                shelfAltKey.setStationNo(compStorageLocation);
                shelfAltKey.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_STORAGED);
                shelfHandle.modify(shelfAltKey);

                // 実績送信情報の報告フラグを未報告に更新
                HostSendController hsc = getHostSendCtlr();
                hsc.updateReportFlag(ci);

                // 搬送データを削除します
                carrKey.clear();
                carrKey.setCarryKey(ci.getCarryKey());
                carrH.drop(carrKey);

                // ダブルディープ対応
                // 棚間移動の完了の場合Palletの現在位置を移動先へ。
                if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
                {
                    // 棚間移動入庫の処理。
                    CarryCompleteOperator cop = new CarryCompleteOperator(getConnection(), getClass());
                    cop.storageRackToRackMove(ci, pl, id33dt.getLtoLocationNo()[num], compStorageLocation);

                    //棚間移動完了時にRetrievalSenderをキック
                    // 出庫指示送信へRMIメッセージを使用して出庫要求を行います。
                    SendRequestor req = new SendRequestor();
                    req.retrieval();
                }
                else
                {
                    // 予定棚と異なる棚に入庫完了した場合のみ、在庫の棚を更新し、実績を作成する
                    if (!compStorageLocation.equals(orgDestStNo))
                    {
                        // 在庫が移動したので実績を書き換え
                        updateLocation(ci, pl, orgDestStNo, compStorageLocation);
                    }
                    else
                    {
                        // 入庫完了したロケーションをパレットの現在位置にセット
                        pltAKey.clear();
                        pltAKey.setPalletId(pl.getPalletId());
                        pltAKey.updateCurrentStationNo(compStorageLocation);
                        pltAKey.updateLastUpdatePname(getClassName());
                        pltH.modify(pltAKey);
                    }
                    CarryInfoSearchKey key = new CarryInfoSearchKey();
                    key.setPalletId(pl.getPalletId());
                    CarryInfo[] multiCI = (CarryInfo[])carrH.find(key);

                    // NullPointerException対策
                    if (null == multiCI)
                    {
                        multiCI = new CarryInfo[0];
                    }

                    for (CarryInfo mCarry : multiCI)
                    {
                        pltKey.clear();

                        pltKey.setPalletId(mCarry.getPalletId());
                        Pallet[] mPallet = (Pallet[])pltH.find(pltKey);

                        carrAKey.clear();
                        carrAKey.setCarryKey(mCarry.getCarryKey());
                        carrAKey.updateSourceStationNo(mPallet[0].getCurrentStationNo());
                        //<jp> アイルステーションNoを更新する</jp>
                        //<en> Update with aisle station no.</en>
                        ShelfHandler wShelfHandler = new ShelfHandler(getConnection());
                        ShelfSearchKey wShelfKey = new ShelfSearchKey();
                        wShelfKey.clear();
                        wShelfKey.setStationNo(mPallet[0].getCurrentStationNo());

                        try
                        {
                            Shelf rShelf = (Shelf)wShelfHandler.findPrimary(wShelfKey);
                            if (rShelf == null)
                            {
                                carrAKey.updateAisleStationNo(null);
                            }
                            else
                            {
                                carrAKey.updateAisleStationNo(rShelf.getParentStationNo());
                            }
                        }
                        catch (NoPrimaryException e)
                        {
                            throw new ReadWriteException(e);
                        }

                        carrAKey.updateLastUpdatePname(getClassName());
                        carrH.modify(carrAKey);
                    }
                }

                // パレットの状態を更新する
                AsPalletController palCtl = new AsPalletController(getConnection(), getClass());
                if (palCtl.updatePalletStatusFlagStorage(pl.getPalletId()))
                {
                    // パレットの更新結果、他に引当が有る場合は
                    // 出庫指示送信へRMIメッセージを使用して出庫要求を行います。
                    SendRequestor req = new SendRequestor();
                    req.retrieval();
                }

                CarryCompleteOperator carryOperator = new CarryCompleteOperator(getConnection(), getClass());
                carryOperator.updateInventoryCheckInfo(ci);
                break;

            //<jp> 二重格納</jp>
            //<en> Double occupations</en>
            case COMP_CLASS_DUP:
                doubleStorage(ci, toShelf, id33dt, num);
                break;

            //<jp> 荷姿不一致</jp>
            //<en> Load size unmatch</en>
            case COMP_CLASS_DIM:
                loadMisalignment(ci, toShelf, id33dt, num);
                break;

            //<jp> 不正な完了区分</jp>
            //<en> Invalid classification of completion</en>
            default:
                //<jp> 搬送データを異常にする</jp>
                //<en> Alters the status of carry data to error.</en>
                carrAKey.clear();
                carrAKey.updateCmdStatus(CarryInfo.CMD_STATUS_ERROR);
                carrAKey.setCarryKey(ci.getCarryKey());
                carrAKey.updateLastUpdatePname(getClassName());
                carrH.modify(carrAKey);

                //<jp> メッセージロギング</jp>
                //<en> Logging of message</en>
                //<jp> 6024017=作業完了報告テキスト内の完了区分が不正です。完了区分={0} mckey={1}</jp>
                //<en> 6024017=Invalid completion category. (Work Completion Report text) Category={0} mckey={1}</en>
                tObj = new Object[2];
                tObj[0] = String.valueOf(compClass[num]);
                tObj[1] = id33dt.getMcKey()[num];
                RmiMsgLogClient.write(6024017, LogMessage.F_WARN, getClass().getName(), tObj);
        } // end of switch
    }

    /**<jp>
     * 出庫完了（棚間移動の出庫も含む）処理を行います。
     * @param  id33dt  作業完了報告テキストの内容
     * @param  num     作業完了報告テキスト詳細データ配列の要素番号
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Procesing the retrieval completion(including the retrievals for location to location moves).
     * @param  id33dt  :contents of work completion report text
     * @param  num     :element no. of data arrays for work completion report text detail
     * @throws  Exception  :in case any error occured
     </en>*/
    private void retrievalCompletion(As21Id33 id33dt, int num)
            throws Exception
    {
        //<jp> 変数の定義 -------------------------------------</jp>
        //<en> Definition of variable-------------------------------------</en>
        PalletSearchKey pSKey = new PalletSearchKey();
        PalletHandler pHandle = new PalletHandler(getConnection());

        CarryInfoHandler wCIHandler = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey wCIKey = new CarryInfoSearchKey();
        CarryInfoAlterKey cakey = new CarryInfoAlterKey();

        CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection(), getClass());

        int[] compClass = id33dt.getCompletionClassification();
        CarryInfo ci;
        Object[] tObj = null;

        //<jp> 処理の開始 -------------------------------------</jp>
        //<jp> MC keyを検索条件として設定</jp>
        //<en> Start of process -------------------------------------</en>
        //<en> Sets MC key as a search condition</en>
        wCIKey.setCarryKey(id33dt.getMcKey()[num]);

        //<jp> 該当のCarryInfoを取得</jp>
        //<en> Retrieves correspondng CarryInfo.</en>
        Entity[] earr = wCIHandler.findForUpdate(wCIKey);

        //<jp> 該当データなし</jp>
        //<en> There is no corresponding data.</en>
        if (earr.length == 0)
        {
            //<jp> 6026038=指定されたmckeyの搬送データが存在しません。mckey={0}</jp>
            //<en> 6026038=Transfer data for the designated MCKey does not exist. mckey={0}</en>
            tObj = new Object[1];
            tObj[0] = id33dt.getMcKey()[num];
            RmiMsgLogClient.write(6026038, LogMessage.F_ERROR, getClass().getName(), tObj);
            return;
        }

        if (earr[0] instanceof CarryInfo)
        {
            ci = (CarryInfo)earr[0];
        }
        else
        {
            tObj = new Object[1];
            tObj[0] = "CarryInformation";
            //<jp> 6006008={0}以外のオブジェクトが返されました。</jp>
            //<en> 6006008=Object other than {0} was returned.</en>
            RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, getClass().getName(), tObj);
            throw new InvalidProtocolException(WmsMessageFormatter.format(6006008, tObj));
        }

        //<jp> 実際の完了処理 ---------------------------------</jp>
        //<jp> 完了状態によって処理を振り分け</jp>
        //<en> Actual completion process ---------------------------------</en>
        //<en> Divides the process according to the status of completion.</en>
        switch (compClass[num])
        {
            case COMP_CLASS_COMPARE_FULL:
                //<jp> 実棚完了</jp>
                //<jp> メッセージを出力後、正常時と同じ処理を行う。</jp>
                //<en> Loaded location completion</en>
                //<en> After outputting the message, normal processing will be done.</en>
                pSKey.setPalletId(ci.getPalletId());
                Pallet[] pallet = (Pallet[])pHandle.find(pSKey);

                //<jp> 6020006=空棚確認で実棚の出庫が行われました。出庫ロケーションNo.={0}</jp>
                //<en> 6020006=An occupied location is retrieved in empty location check. Picking Location No.={0}</en>
                tObj = new Object[1];
                tObj[0] = pallet[0].getCurrentStationNo();
                RmiMsgLogClient.write(6020006, LogMessage.F_INFO, getClass().getName(), tObj);
                /* nobreak */

            case COMP_CLASS_NORMAL:
                //<jp> 正常完了</jp>
                //<en> Normal completion</en>
                normalRetrievalCompletion(getConnection(), ci);
                break;

            case COMP_CLASS_EMPTY:
                //<jp> 空出荷</jp>
                //<en> Empty retrieval</en>
                retrievalError(ci);
                break;

            case COMP_CLASS_COMPARE_EMPTY:
                //<jp> 空棚完了</jp>
                //<en> Empty location completion</en>
                emptyLocationCompletion(ci);
                break;

            case COMP_CLASS_CANCEL:
                //<jp> キャンセル</jp>
                //<en> Cancel</en>
                //<jp> 出庫データキャンセル処理</jp>
                //<en> Cancels the retrieval data </en>
                carryCompOpe.cancelRetrieval(ci);
                break;

            default:
                //<jp> 不正な完了区分</jp>
                //<en> Invalid classificaiotn of completion</en>
                //<jp> 搬送データを異常にする</jp>
                //<en> Alters the status of carry data to error.</en>
                cakey.updateCmdStatus(CarryInfo.CMD_STATUS_ERROR);
                cakey.setCarryKey(ci.getCarryKey());
                cakey.updateLastUpdatePname(getClassName());
                wCIHandler.modify(cakey);

                //<jp> メッセージロギング</jp>
                //<en> Logging of the message</en>
                //<jp> 6024017=作業完了報告テキスト内の完了区分が不正です。完了区分={0} mckey={1}</jp>
                //<en> 6024017=Invalid completion category. (Work Completion Report text) Category={0} mckey={1}</en>
                tObj = new Object[2];
                tObj[0] = String.valueOf(compClass[num]);
                tObj[1] = id33dt.getMcKey()[num];
                RmiMsgLogClient.write(6024017, LogMessage.F_WARN, getClass().getName(), tObj);
        } // end of switch
    }

    /**<jp>
     * 二重格納処理を行います。
     * @param  ci      搬送情報
     * @param  shelf   入庫ロケーションのShelf
     * @param  id33dt  作業完了報告テキストの内容
     * @param  num     作業完了報告テキスト詳細データ配列の要素番号
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processes the double occupations.
     * @param  ci     :carry information
     * @param  shelf  :Shelf of storage location
     * @param  id33dt :contents of work completion report text
     * @param  num    :element no. of data array for work completion report text detail
     * @throws  Exception  :in case any error occured
     </en>*/
    @SuppressWarnings("unused")
    private void doubleStorage(CarryInfo ci, Shelf shelf, As21Id33 id33dt, int num)
            throws Exception
    {
        //<jp> 変数の定義 -------------------------------------</jp>
        //<en> Definition of variable-------------------------------------</en>
        //<jp> 代替搬送先の有無を表すフラグ</jp>
        //<en> Flag which indicates whether/not there are any alternative destinations.</en>
        boolean canTransfer = false;

        // modify start 2010/07/16 作業完了時の在庫更新を修正
//        // 在庫更新タイミングが作業完了時の場合、在庫の更新を行う。
//        if (WmsParam.STOCK_MODIFY_TIMING)
//        {
//            updateStock(ci);
//        }
        // modify end 2010/07/16 作業完了時の在庫更新を修正

        PalletSearchKey pSKey = new PalletSearchKey();
        PalletHandler pHandle = new PalletHandler(getConnection());

        StockAlterKey sAKey = new StockAlterKey();
        StockHandler sHandle = new StockHandler(getConnection());

        WorkInfoHandler wiH = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey wiKey = new WorkInfoSearchKey();

        AsStockController asStockC = getAsStockCtlr();

        String altLocNo = null;

        //<jp> 処理の開始 -------------------------------------</jp>
        //<en> Start of the process -------------------------------------</en>

        pSKey.setPalletId(ci.getPalletId());
        Pallet[] pallet = (Pallet[])pHandle.find(pSKey);
        String orgLocation = pallet[0].getCurrentStationNo();

        String simpleName = getClassName();
        //<jp> 同一アイルより、代替棚の検索を行う。</jp>
        //<en> Search the alternative locations within the same aisle.</en>
        // 同一アイル間でのルートチェックを行うことになるので、
        // ここではアイルステーションNo.をセットします。
        Shelf altloc = getAlternativeLocation(ci, shelf.getParentStationNo());
        if (altloc == null)
        {
            if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
            {
                // 移動元の棚Noを取得
                altloc = getAlternativeLocation(ci, shelf.getStationNo());
            }
            else
            {
                // 搬送元ステーションNoを取得
                Station st = StationFactory.makeStation(getConnection(), ci.getSourceStationNo());
                //<jp> アイルステーション番号が定義されていない場合（アイル結合の場合）</jp>
                //<en> If the aisle station No. is not in defined (due to aisle connected station)</en>
                if (StringUtil.isBlank(st.getAisleStationNo()))
                {
                    //<jp> 同一アイル内に代替棚が見つからなかった場合、</jp>
                    //<jp> 倉庫全体より空棚検索を行う。</jp>
                    //<en> If there is no alternative location was found in the same aisle, conduct the empty</en>
                    //<en> location search all over the warehouse.</en>
                    altloc = getAlternativeLocation(ci, ci.getSourceStationNo());
                }
            }
        }

        CarryInfoAlterKey cakey = new CarryInfoAlterKey();
        CarryInfoHandler wCIHandler = new CarryInfoHandler(getConnection());
        if (altloc != null)
        {
            //<jp> 設備に送信する荷高情報を、棚情報およびハードゾーン情報より取得する。</jp>
            //<jp> 取得した値はパレットにセットする。</jp>
            //<en> Get the load size data from location data/hard zone data to send to equipment.</en>
            //<en> Then set the acquired value for pallet. </en>
            HardZoneHandler hhandl = new HardZoneHandler(getConnection());
            HardZoneSearchKey key = new HardZoneSearchKey();
            key.setHardZoneId(altloc.getHardZoneId());
            HardZone[] hzones = (HardZone[])hhandl.find(key);
            if (hzones.length == 0)
            {
                //<jp> ハードゾーン情報が見つからない場合は搬送データを異常にする。</jp>
                //<en> Set the status of carry data "error" if there is no hard zone data. </en>
                //<jp> 6026070=指定された棚に定義されているハードゾーンがゾーン定義にありません。棚No:{0} ハードゾーンID:{1}</jp>
                //<en> 6026070=Hard zone of the specified location is not found. LocationNo.:{0} Hard zone ID:{1}</en>
                Object[] tObj = new Object[2];
                tObj[0] = altloc.getStationNo();
                tObj[1] = altloc.getHardZoneId();
                RmiMsgLogClient.write(6026070, LogMessage.F_ERROR, getClass().getName(), tObj);

                //<jp> 代替棚・リジェクトステーション無し。搬送状態を異常とする。</jp>
                //<en> There is no alternative location or reject station. Change the carry status to "error".</en>
                cakey.clear();
                cakey.updateCmdStatus(CarryInfo.CMD_STATUS_ERROR);
                cakey.setCarryKey(ci.getCarryKey());

                cakey.updateLastUpdatePname(simpleName);
                wCIHandler.modify(cakey);

                // 在庫が移動したので実績を書き換え
                if (!CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
                {
                    // 棚間移動以外
                    updateLocation(ci, pallet[0], orgLocation, ci.getSourceStationNo());
                }
                else
                {
                    // 棚間移動はこの時点で在庫情報は元のまま、実績も作成して無いので、
                    // 棚間移動出来なかった場合、出庫棚に戻す
                    ShelfOperator sop = new ShelfOperator(getConnection(), ci.getRetrievalStationNo());
                    sop.alterPresence(Shelf.LOCATION_STATUS_FLAG_STORAGED);
                }

                canTransfer = false;
            }
            else
            {
                // フリーアロケーション倉庫の場合、搬送情報の制御情報を更新する
                if (WareHouse.FREE_ALLOCATION_ON.equals(getAreaCtlr().getFreeAllocationOfWarehouse(
                        pallet[0].getWhStationNo())))
                {
                    // 荷姿マスタから荷幅、荷長、荷高を取得する
                    LoadSizeSearchKey lskey = new LoadSizeSearchKey();
                    LoadSizeHandler lHandler = new LoadSizeHandler(getConnection());
                    lskey.clear();
                    lskey.setJoin(LoadSize.LOAD_SIZE, HardZone.HEIGHT);
                    lskey.setKey(HardZone.HARD_ZONE_ID, altloc.getHardZoneId());
                    // 荷姿マスタ情報検索
                    LoadSize loadsize = (LoadSize)lHandler.findPrimary(lskey);
                    // 制御情報作成
                    ControlInfo conInfo = new ControlInfo();
                    conInfo.setWidth(altloc.getWidth());
                    conInfo.setLength(loadsize.getLength());
                    conInfo.setHeight(loadsize.getHeight());
                    String controlInfo = conInfo.convertControlInfo(conInfo);
                    cakey.updateControlinfo(controlInfo);
                }

                //<jp> 検索で決定した搬送先ステーションNoで更新</jp>
                //<en> Update with destination station no. determined by the search.</en>
                cakey.setCarryKey(ci.getCarryKey());
                cakey.updateDestStationNo(altloc.getStationNo());

                //<jp> アイルステーションNoを更新する</jp>
                //<en> Update with aisle station no.</en>
                cakey.updateAisleStationNo(altloc.getParentStationNo());
                cakey.updateLastUpdatePname(simpleName);
                wCIHandler.modify(cakey);

                //<jp> パレットの現在位置を代替棚にする。</jp>
                //<en> Current position of pallet must be altered to the alternative location.</en>
                if (!CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
                {
                    // 棚間移動以外
                    updateLocation(ci, pallet[0], orgLocation, altloc.getStationNo());
                }
            }

            canTransfer = true;
        }
        else
        {
            //<jp> 代替棚・リジェクトステーション無し。搬送状態を異常とする。</jp>
            //<en> There is no alternative location or reject station. Change the carry status to "error".</en>
            cakey.clear();
            cakey.setCarryKey(ci.getCarryKey());
            cakey.updateCmdStatus(CarryInfo.CMD_STATUS_ERROR);
            cakey.updateLastUpdatePname(simpleName);
            wCIHandler.modify(cakey);

            // 在庫が移動したので実績を書き換え
            if (!CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
            {
                // 棚間移動以外
                updateLocation(ci, pallet[0], orgLocation, ci.getSourceStationNo());
            }
            else
            {
                // 棚間移動はこの時点で在庫情報は元のまま、実績も作成して無いので、
                // 棚間移動出来なかった場合、出庫棚に戻す
                ShelfOperator sop = new ShelfOperator(getConnection(), ci.getRetrievalStationNo());
                sop.alterPresence(Shelf.LOCATION_STATUS_FLAG_STORAGED);
            }

            canTransfer = false;
        }

        //<jp> 二重格納用のPallet,Stockテーブルの生成</jp>
        //<jp> ステーションNoをセット</jp>
        //<en> Generates the pallet and Stock tables for double occupations</en>
        //<en> Sets the station no.</en>
        // 在庫情報セット
        Stock stk = new Stock();
        stk.setAreaNo(getAreaCtlr().getAreaNoOfWarehouse(pallet[0].getWhStationNo()));
        stk.setConsignorCode(WmsParam.IRREGULAR_CONSIGNORCODE);
        stk.setItemCode(WmsParam.IRREGULAR_ITEMCODE);
        stk.setLocationNo(getAreaCtlr().toParamLocation(shelf.getStationNo()));
        stk.setStockQty(1);
        stk.setAllocationQty(1);
        stk.setStorageDay(getWarenaviSysCtlr().getWorkDay());
        stk.setStorageDate(new SysDate());

        // パレット情報セット
        Pallet dpl = new Pallet();
        dpl.setHeight(pallet[0].getHeight());
        dpl.setWidth(pallet[0].getWidth());
        dpl.setSoftZoneId(pallet[0].getSoftZoneId());
        dpl.setBcrData(pallet[0].getBcrData());
        dpl.setControlinfo(pallet[0].getControlinfo());
        dpl.setStatusFlag(SystemDefine.PALLET_STATUS_IRREGULAR);

        // 対象作業を取得します
        wiKey.setSystemConnKey(ci.getCarryKey());
        WorkInfo[] works = (WorkInfo[])wiH.find(wiKey);

        WorkInfo priWork = works[0]; // 代表作業として1件目を使用

        String userId = priWork.getUserId();
        String termNo = priWork.getTerminalNo();
        String jobType = priWork.getJobType();

        // ユーザ情報セット
        WmsUserInfo ui = WmsUserInfo.buildUserInfo(getConnection(), userId, SystemDefine.HARDWARE_TYPE_ASRS, termNo);

        // 在庫作成
        String stockId =
                asStockC.insert(stk, dpl, SystemDefine.INC_DEC_TYPE_STOCK_INCREMENT,
                        SystemDefine.JOB_TYPE_MAINTENANCE_PLUS, ui);


        // 最新搬送情報取得
        CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
        ciKey.setCarryKey(ci.getCarryKey());
        CarryInfo sendCarry = (CarryInfo)wCIHandler.findPrimary(ciKey);

        //<jp> 代替棚指示送信処理</jp>
        //<en> Submitting the isntruction for the alternative location.</en>
        SystemTextTransmission.id11send(sendCarry, SystemTextTransmission.CLASS_DOBULE_STRAGE, canTransfer,
                getConnection());

        // 代替棚がない場合は、搬送データ削除
        if (!canTransfer)
        {
            CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection(), getClass());
            // 2010/06/24 MODIFY START
            carryCompOpe.drop(ci, InOutResult.WORK_TYPE_CARRYINFODELETE, true, CARRY_COMPLETE.IRREGULAR);
            // 2010/06/24 MODIFY END
        }

        //<jp> 今回行った処理をロギング</jp>
        //<en> Logging of this process</en>
        String destStNo = (canTransfer) ? sendCarry.getDestStationNo()
                                       : SystemTextTransmission.getVoidLocationNo();
        //<jp> 6022029=二重格納が発生しました。搬送先を変更します。 {0} ---> {1} mckey={2}</jp>
        //<en> 6022029=A double occupancy was detected. Destination will be changed. {0} ---> {1} mckey={2}</en>
        Object[] tObj = {
            shelf.getStationNo(),
            destStNo,
            sendCarry.getCarryKey(),
        };
        RmiMsgLogClient.write(6022029, LogMessage.F_NOTICE, getClass().getName(), tObj);


        if (!canTransfer)
        {
            //<jp> 6022033=搬送データを削除しました。mckey={0}
            //<en> 6022033=Transfer data is deleted. mckey={0}
            tObj = new Object[1];
            tObj[0] = sendCarry.getCarryKey();
            RmiMsgLogClient.write(6022033, LogMessage.F_NOTICE, this.getClass().getName(), tObj);
        }

    }

    /**<jp>
     * 荷姿不一致処理を行います。
     * @param  ci      搬送情報
     * @param  shelf   入庫ロケーションのShelf
     * @param  id33dt  作業完了報告テキストの内容
     * @param  num     作業完了報告テキスト詳細データ配列の要素番号
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processing the load size unmatch.
     * @param  ci     :carry information
     * @param  shelf  :Shelf of storage location
     * @param  id33dt :contents of work completion report text
     * @param  num    :element no. of data array for work completion report text detail
     * @throws  Exception  :in case any error occured
     </en>*/
    private void loadMisalignment(CarryInfo ci, Shelf shelf, As21Id33 id33dt, int num)
            throws Exception
    {
        //<jp> 変数の定義 -------------------------------------</jp>
        //<en> Definition of variable-------------------------------------</en>
        //<jp> 代替搬送先の有無を表すフラグ</jp>
        //<en> Flag which indicates whether/not there are any alternative destinations.</en>
        boolean canTransfer = false;

        // modify start 2010/07/16 作業完了時の在庫更新を修正
//        // 在庫更新タイミングが作業完了時の場合、在庫の更新を行う。
//        if (WmsParam.STOCK_MODIFY_TIMING)
//        {
//            updateStock(ci);
//        }
        // modify end 2010/07/16 作業完了時の在庫更新を修正

        ShelfAlterKey sk = new ShelfAlterKey();
        ShelfHandler shelfHandle = new ShelfHandler(getConnection());

        CarryInfoAlterKey cakey = new CarryInfoAlterKey();
        CarryInfoHandler carryHandle = new CarryInfoHandler(getConnection());

        PalletSearchKey pSKey = new PalletSearchKey();
        PalletAlterKey pAKey = new PalletAlterKey();
        PalletHandler pHandle = new PalletHandler(getConnection());

        // 荷姿
        int height = 0;

        pSKey.setPalletId(ci.getPalletId());
        Pallet[] pallet = (Pallet[])pHandle.find(pSKey);
        String orgLocation = pallet[0].getCurrentStationNo();

        //<jp> 処理の開始 -------------------------------------</jp>
        //<en> Start of the process -------------------------------------</en>

        FreeAllocationShelfOperator freeshelfop =
                new FreeAllocationShelfOperator(getConnection(), shelf.getStationNo());
        // フリーアロケーション運用の場合、荷幅、棚使用フラグをフリーに更新
        if (freeshelfop.isFreeAllocationStation())
        {
            freeshelfop.alterFreeWidth();
        }

        //<jp> 今回入庫しようとした棚を空棚にする。</jp>
        //<en> Alters the status of this shelf, attempted to store, to empty.</en>
        sk.clear();
        sk.setStationNo(shelf.getStationNo());
        sk.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
        shelfHandle.modify(sk);

        // フリーアロケーション運用倉庫の場合
        boolean isFreeAlloc =
                WareHouse.FREE_ALLOCATION_ON.equals(getAreaCtlr().getFreeAllocationOfWarehouse(
                        pallet[0].getWhStationNo()));
        if (isFreeAlloc)
        {
            ControlInfo conInfo = new ControlInfo();
            conInfo = conInfo.convertControlInfo(id33dt.getControlInformation()[num]);
            if (null == conInfo)
            {
                Object[] tObj = new Object[2];
                tObj[0] = id33dt.getMcKey()[num];
                tObj[1] = id33dt.getControlInformation()[num];
                //<jp> 6026605=作業完了報告テキスト内の制御情報が不正です。mckey={0} 制御情報={1}</jp>
                RmiMsgLogClient.write(6026600, LogMessage.F_ERROR, getClass().getName(), tObj);
                return;
            }

            // 荷姿チェック
            LoadSizeSearchKey lskey = new LoadSizeSearchKey();
            LoadSizeHandler lHandler = new LoadSizeHandler(getConnection());
            lskey.setLength(conInfo.getLength());
            lskey.setHeight(conInfo.getHeight());
            // 荷姿情報検索
            LoadSize loadsize = (LoadSize)lHandler.findPrimary(lskey);
            if (loadsize == null)
            {
                Object[] tObj = new Object[2];
                tObj[0] = id33dt.getMcKey()[num];
                tObj[1] = id33dt.getControlInformation()[num];
                // 作業完了報告テキスト内の制御情報が不正です。荷姿情報に存在しません。mckey={0} 制御情報={1}
                RmiMsgLogClient.write(6026603, LogMessage.F_ERROR, getClass().getName(), tObj);
                return;
            }
            height = loadsize.getLoadSize();

            // 荷幅チェック
            WidthSearchKey wskey = new WidthSearchKey();
            WidthHandler wHandler = new WidthHandler(getConnection());
            wskey.setWidth(conInfo.getWidth());
            wskey.setWhStationNo(pallet[0].getWhStationNo());
            // 荷幅情報検索
            if (wHandler.count(wskey) == 0)
            {
                Object[] tObj = new Object[2];
                tObj[0] = id33dt.getMcKey()[num];
                tObj[1] = id33dt.getControlInformation()[num];
                // 作業完了報告テキスト内の制御情報が不正です。荷幅情報に存在しません。mckey={0} 制御情報={1}
                RmiMsgLogClient.write(6026604, LogMessage.F_ERROR, getClass().getName(), tObj);
                return;
            }

            //<jp> 作業完了報告テキストより荷姿情報を取得してセット</jp>
            //<en> Retrieves the load size information from the work completion report text and sets.</en>
            pallet[0].setWidth(conInfo.getWidth());
            pallet[0].setControlinfo(id33dt.getControlInformation()[num]);
        }
        // フリーアロケーション運用倉庫以外の場合
        else
        {
            //<jp> 作業完了報告テキストより荷姿情報を取得してセット</jp>
            //<en> Retrieves the load size information from the work completion report text and sets.</en>
            height = id33dt.getDimension()[num];
        }
        pallet[0].setHeight(height);

        //<jp> 同一アイルより、代替棚の検索を行う。</jp>
        //<jp> 指定された荷姿に一致する棚のみ検索対象とする。</jp>
        //<en> Search the alternative locations within the same aisle. Selects only locations that matche the </en>
        //<en> specified load size for the subject of search.</en>
        Shelf altloc = getloadMisalignmentAlternativeLocation(ci, pallet[0], shelf.getParentStationNo());
        if (altloc == null)
        {
            // 同一アイル内で検索した時に書き換えたCurrentStationNoに棚Noを再度セットする。
            pallet[0].setCurrentStationNo(shelf.getStationNo());

            if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
            {
                // 移動元の倉庫ステーションNoを取得
                altloc = getloadMisalignmentAlternativeLocation(ci, pallet[0], shelf.getStationNo());
            }
            else
            {
                Station st = StationFactory.makeStation(getConnection(), ci.getSourceStationNo());
                //<jp> アイルステーション番号が定義されていない場合（アイル結合の場合）</jp>
                //<en> If the aisle station No. is not in defined (due to aisle connected station)</en>
                if (StringUtil.isBlank(st.getAisleStationNo()))
                {
                    //<jp> 同一アイル内に代替棚が見つからなかった場合、</jp>
                    //<jp> 倉庫全体より空棚検索を行う。</jp>
                    //<en> If there is no alternative location was found in the same aisle, conduct the empty</en>
                    //<en> location search all over the warehouse.</en>
                    altloc = getloadMisalignmentAlternativeLocation(ci, pallet[0], ci.getSourceStationNo());
                }
            }
        }
        String simpleName = getClass().getSimpleName();
        if (altloc != null)
        {
            cakey.clear();
            cakey.setCarryKey(ci.getCarryKey());
            //<jp> 検索で決定した搬送先ステーションNoで更新</jp>
            //<en> Update with destination station no. determined by the search.</en>
            cakey.updateDestStationNo(altloc.getStationNo());
            //<jp> アイルステーションNoを更新する</jp>
            //<en> Update with aisle station no.</en>
            cakey.updateAisleStationNo(altloc.getParentStationNo());

            // フリーアロケーション倉庫の場合、搬送情報の制御情報を更新する
            if (isFreeAlloc)
            {
                // 荷姿マスタから荷幅、荷長、荷高を取得する
                LoadSizeSearchKey lskey = new LoadSizeSearchKey();
                LoadSizeHandler lHandler = new LoadSizeHandler(getConnection());
                lskey.clear();
                lskey.setJoin(LoadSize.LOAD_SIZE, HardZone.HEIGHT);
                lskey.setKey(HardZone.HARD_ZONE_ID, altloc.getHardZoneId());
                // 荷姿マスタ情報検索
                LoadSize loadsize = (LoadSize)lHandler.findPrimary(lskey);
                // 制御情報作成
                ControlInfo conInfo = new ControlInfo();
                conInfo.setWidth(altloc.getWidth());
                conInfo.setLength(loadsize.getLength());
                conInfo.setHeight(loadsize.getHeight());
                String controlInfo = conInfo.convertControlInfo(conInfo);
                cakey.updateControlinfo(controlInfo);

            }

            cakey.updateLastUpdatePname(simpleName);
            carryHandle.modify(cakey);

            if (!CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
            {
                // 棚間移動以外
                updateLocation(ci, pallet[0], orgLocation, altloc.getStationNo());
            }

            //<jp> パレットの荷高を更新する。</jp>
            //<en> Thenu update the load size.</en>
            pAKey.clear();
            pAKey.setPalletId(pallet[0].getPalletId());
            pAKey.updateHeight(height);
            pAKey.updateWidth(pallet[0].getWidth());
            pAKey.updateControlinfo(pallet[0].getControlinfo());
            pAKey.updateLastUpdatePname(simpleName);
            pHandle.modify(pAKey);

            canTransfer = true;
        }
        else
        {
            //<jp> 代替棚が見つからなければリジェクトステーションを探す</jp>
            //<en> If there is no alternative location is found, search the reject station.</en>
            Station rjst = getRejectStation(ci.getSourceStationNo());
            if (rjst != null)
            {
                //<jp> 検索で決定した搬送先ステーションNoで更新</jp>
                //<en> Update with destination station no. determined by the search.</en>
                cakey.clear();
                cakey.updateDestStationNo(rjst.getStationNo());
                cakey.updateControlinfo(id33dt.getControlInformation()[num]);
                cakey.setCarryKey(ci.getCarryKey());
                cakey.updateLastUpdatePname(simpleName);
                carryHandle.modify(cakey);

                // 在庫が移動したので実績を書き換え
                if (!CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
                {
                    // 棚間移動以外
                    updateLocation(ci, pallet[0], orgLocation, ci.getSourceStationNo());
                }
                else
                {
                    // 棚間移動はこの時点で在庫情報は元のまま、実績も作成して無いので、
                    // 棚間移動出来なかった場合、出庫棚に戻す
                    ShelfOperator sop = new ShelfOperator(getConnection(), ci.getRetrievalStationNo());
                    sop.alterPresence(Shelf.LOCATION_STATUS_FLAG_STORAGED);
                }

                canTransfer = true;
            }
            else
            {
                //<jp> 代替棚・リジェクトステーション無し。搬送状態を異常とする。</jp>
                //<en> There is no either alternative location or reject station. Sets the carry status error.</en>
                cakey.clear();
                cakey.updateCmdStatus(CarryInfo.CMD_STATUS_ERROR);
                cakey.setCarryKey(ci.getCarryKey());
                cakey.updateLastUpdatePname(simpleName);
                carryHandle.modify(cakey);

                // 在庫が移動したので実績を書き換え
                if (!CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
                {
                    // 棚間移動以外
                    updateLocation(ci, pallet[0], orgLocation, ci.getSourceStationNo());
                }
                else
                {
                    // 棚間移動はこの時点で在庫情報は元のまま、実績も作成して無いので、
                    // 棚間移動出来なかった場合、出庫棚に戻す
                    ShelfOperator sop = new ShelfOperator(getConnection(), ci.getRetrievalStationNo());
                    sop.alterPresence(Shelf.LOCATION_STATUS_FLAG_STORAGED);
                }

                canTransfer = false;
            }
        }

        CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
        ciKey.setCarryKey(ci.getCarryKey());
        CarryInfo sendCarry = (CarryInfo)carryHandle.findPrimary(ciKey);

        //<jp> 代替棚指示送信処理</jp>
        //<en> Submitting the instruction for alternative location</en>
        SystemTextTransmission.id11send(sendCarry, SystemTextTransmission.CLASS_LOAD_MISALIGNMENT, canTransfer,
                getConnection());

        // 代替棚がない場合は、搬送データ削除
        if (!canTransfer)
        {
            CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection(), getClass());
            carryCompOpe.drop(ci, InOutResult.WORK_TYPE_CARRYINFODELETE, true, CARRY_COMPLETE.ERROR_SHORTAGE);
        }

        //<jp> 今回行った処理をロギング</jp>
        //<en> Logging of this process</en>
        String destStNo = (canTransfer) ? sendCarry.getDestStationNo()
                                       : SystemTextTransmission.getVoidLocationNo();
        //<jp> 6022030=荷姿不一致が発生しました。搬送先を変更します。{0} ---> {1} mckey={2}</jp>
        //<en> 6022030=Load size mismatch occurred. Destination will be changed. {0} ---> {1} mckey={2}</en>
        Object[] tObj = {
            shelf.getStationNo(),
            destStNo,
            sendCarry.getCarryKey(),
        };
        RmiMsgLogClient.write(6022030, LogMessage.F_NOTICE, getClass().getName(), tObj);

        if (!canTransfer)
        {
            //<jp> 6022033=搬送データを削除しました。mckey={0}
            //<en> 6022033=Transfer data is deleted. mckey={0}
            tObj = new Object[1];
            tObj[0] = sendCarry.getCarryKey();
            RmiMsgLogClient.write(6022033, LogMessage.F_NOTICE, this.getClass().getName(), tObj);
        }

    }

    /**
     * 完了で棚替えが生じた場合、パレットに関連する作業と入出庫実績送信情報の
     * 実績棚No.、StockIDを書き換えます。<br>
     * 本メソッドは在庫の移動処理が終わってから、使用してください。
     *
     * @param ci 搬送情報
     * @param pl パレット情報
     * @param orgLocation 元棚No.(AsrsLocationフォーマット)
     * @param newLocation 新しい棚No.(AsrsLocationフォーマット)
     * @throws Exception 例外が発生した場合に通知されます。
     */
    private void updateLocation(CarryInfo ci, Pallet pl, String orgLocation, String newLocation)
            throws Exception
    {
        // 棚変更（パレット、在庫、マルチ引当分作業情報）
        CarryInfo newCarry = (CarryInfo)ci.clone();
        newCarry.setRetrievalStationNo(orgLocation);
        newCarry.setDestStationNo(newLocation);
        String[] jobType = {
            SystemDefine.JOB_TYPE_MAINTENANCE_MINUS,
            SystemDefine.JOB_TYPE_MAINTENANCE_PLUS
        };

        // modify start 2010/07/16 作業完了時の在庫更新を修正
        // 搬送指示応答で在庫更新の場合は実績棚を更新する
        if (!WmsParam.STOCK_MODIFY_TIMING)
        {
            getAsStockCtlr().updateAsrsLocation(newCarry, jobType, true);

            // 実績棚の更新（作業情報、実績送信情報）
            // 入庫の場合は作業、実績送信情報の実績棚を更新する
            if (CarryInfo.RETRIEVAL_DETAIL_ADD_STORING.equals(ci.getRetrievalDetail())
                    || CarryInfo.RETRIEVAL_DETAIL_UNKNOWN.equals(ci.getRetrievalDetail()))
            {
                updateResultLocation(ci.getCarryKey(), getAreaCtlr().toParamLocation(newLocation));
            }

            // 棚の更新（稼動実績情報）
            //<jp> 実績データのLocationNumberを変更する</jp>
            //<en> Modifies the LocationNumber in result data.</en>
            InOutResultAlterKey ikey = new InOutResultAlterKey();
            InOutResultHandler ioh = new InOutResultHandler(getConnection());
            ikey.setCarryKey(ci.getCarryKey());
            ikey.updateLocationNo(newLocation);
            ikey.updateLastUpdatePname(getClassName());
            ioh.modify(ikey);
        }
        else
        {
            // 作業完了で在庫更新の場合は、在庫履歴を作成しない
            getAsStockCtlr().updateAsrsLocation(newCarry, jobType, false);
        }
        // modify end 2010/07/16 作業完了時の在庫更新を修正
    }

    /**
     * 完了で棚替えが生じた場合、パレットに関連する作業と入出庫実績送信情報の
     * 実績棚No.を書き換えます。<br>
     * 本メソッドは在庫の移動処理が終わってから、使用してください。
     *
     * @param carryKey 代替棚指示が行われた搬送キー
     * @param newLocation 移動元の棚No.(ParamLocationフォーマット)
     * @throws Exception 例外が発生した場合に通知されます。
     */
    private void updateResultLocation(String carryKey, String newLocation)
            throws Exception
    {
        AsWorkInfoController wic = getWorkInfoCtlr();

        // 関連作業の取得
        WorkInfoHandler wiH = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey wiKey = new WorkInfoSearchKey();
        wiKey.setSystemConnKey(carryKey);

        WorkInfo[] works = (WorkInfo[])wiH.find(wiKey);

        // 作業の実績棚No.を更新
        wic.updateResultLocation(works, newLocation);

        // hostsendの実績棚No.を更新
        HostSendController hsc = getHostSendCtlr();
        hsc.updateResultLocation(works, newLocation);
    }

    /**
     * 荷姿不一致、二重格納時の在庫を減算
     *
     * @throws Exception 例外が発生した場合に通知されます。
     */
    private void updateStock(CarryInfo ci)
            throws Exception
    {
        // 関連作業の取得
        WorkInfoHandler wiH = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey wiKey = new WorkInfoSearchKey();
        wiKey.setSystemConnKey(ci.getCarryKey());

        WorkInfo[] works = (WorkInfo[])wiH.find(wiKey);

        StockHandler stH = new  StockHandler(getConnection());
        StockSearchKey stSeaKey = new  StockSearchKey();
        StockAlterKey stAltKey = new  StockAlterKey();

        for( WorkInfo work: works)
        {
            stSeaKey.clear();
            stSeaKey.setStockId(work.getStockId());
            if (stH.count(stSeaKey) > 0)
            {
                stAltKey.clear();
                stAltKey.setStockId(work.getStockId());
                stAltKey.updateStorageDate(null);
                stAltKey.updateNewestStorageDate(null);
                stAltKey.updateStorageDay("");
                stAltKey.updateStockQty(0);
                stAltKey.updateAllocationQty(0);
                stAltKey.updatePlanQty(work.getPlanQty());
                stH.modify(stAltKey);
            }
        }
    }

    /**<jp>
     * 空出荷処理を行います。
     * 指定された搬送データおよびパレットデータを削除します。
     * 実績の作成は行ないません。
     * @param ci 空出荷処理を行なう搬送情報
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processing the empty retrieval.
     * Deletes the specified carry data and the pallet data.
     * There will be not creation of result data.
     * @param ci :carry data to process the empty retrieval
     * @throws  Exception  :in case any error occured
     </en>*/
    private void retrievalError(CarryInfo ci)
            throws Exception
    {
        PalletSearchKey pSKey = new PalletSearchKey();
        PalletHandler pHandle = new PalletHandler(getConnection());

        pSKey.setPalletId(ci.getPalletId());
        Pallet[] pallet = (Pallet[])pHandle.find(pSKey);

        Shelf fromShelf = null;
        Station st = StationFactory.makeStation(getConnection(), pallet[0].getCurrentStationNo());

        CarryInfoAlterKey cakey = new CarryInfoAlterKey();
        CarryInfoHandler carryHandle = new CarryInfoHandler(getConnection());

        CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection(), getClass());

        if (st instanceof Shelf)
        {
            fromShelf = (Shelf)st;
            //<jp> 代替棚テキスト送信</jp>
            //<en> Submit the altenative location text.</en>
            SystemTextTransmission.id11send(ci, SystemTextTransmission.CLASS_RETRIEVAL_ERROR, false, getConnection());

            //<jp> 実績を落とすため</jp>
            //<en> In order to record the results, </en>
            cakey.updateRetrievalStationNo(fromShelf.getStationNo());
            cakey.setCarryKey(ci.getCarryKey());
            cakey.updateLastUpdatePname(getClassName());
            carryHandle.modify(cakey);

            //<jp> 今回行った処理をロギング</jp>
            //<en> Logging of this process</en>
            //<jp> 6022031=空出荷が発生しました。搬送データを削除します。出庫ロケーションNo.={0} mckey={1}</jp>
            //<en> 6022031=Empty picking occurred. Transfer data will be deleted. Picking location No.={0} mckey={1}</en>
            RmiMsgLogClient.write(
                    WmsMessageFormatter.format(6022031, pallet[0].getCurrentStationNo(), ci.getCarryKey()),
                    getClass().getName());

            //<jp> 搬送データを削除し、実績を作成する。</jp>
            //<en> Delete the carry data and create the result data.</en>
            carryCompOpe.drop(ci, InOutResult.WORK_TYPE_EMPTYRETRIEVAL, true, CARRY_COMPLETE.ERROR_SHORTAGE);
        }
        else
        {
            //<jp> 生成されたインスタンスがShelfでなかった場合は例外を返す。</jp>
            //<en> Returns exception if generated instance was not Shelf.</en>
            //<jp> 6006008={0}以外のオブジェクトが返されました。</jp>
            //<en> 6006008=Object other than {0} was returned.</en>
            RmiMsgLogClient.write(WmsMessageFormatter.format(6006008, "Shelf"), getClass().getName());
            throw new InvalidDefineException(WmsMessageFormatter.format(6006008, "Shelf"));
        }
    }

    /**<jp>
     * 空棚完了処理を行います。
     * @param  ci 搬送情報
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processing the completion of empty location.
     * @param  ci :carry information
     * @throws  Exception  :in case any error occured
     </en>*/
    private void emptyLocationCompletion(CarryInfo ci)
            throws Exception
    {
        CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection(), getClass());

        //<jp> 搬送データを削除</jp>
        //<en> Delete the carry data.</en>
        carryCompOpe.drop(ci, true);

        //<jp> 在庫確認データチェックを行い、</jp>
        //<jp> 在庫確認作業データが存在しない場合は在庫確認未作業にする。</jp>
        //<en> Check the inventory check data, and if there is no data of inventory check works,</en>
        //<en> set the status of inventory check 'unprocessed'.</en>
        carryCompOpe.emptyLocationCheckOff(ci);
    }

    /**<jp>
     * 二重格納時の代替棚検索を行ないます。
     * @param  ci     搬送先決定を行うCarryInformationインスタンス
     * @param  stNo   StationNo
     * @return 搬送先ステーションインスタンス。代替棚が検索された場合はShelfインスタンス。無い場合はnullを返します。
     * @throws Exception データアクセスで発生した場合に通知します。
     * @throws Exception データの内容に不整合があった場合に通知します。
     </jp>*/
    /**<en>
     * Search the alternative location for double occupation.
     * @param  ci   :CarryInformation instance to determine the destination
     * @param  stNo :StationNo
     * @return :instance of receiving station. Or Shelf instance if alternative location was searched. Returns null if there is none.
     * @throws Exception :Notifies if exception occurs in accessing data.
     * @throws Exception :Notifies if there are data inconsistency.
     </en>*/
    private Shelf getAlternativeLocation(CarryInfo ci, String stNo)
            throws Exception
    {
        PalletSearchKey pSKey = new PalletSearchKey();
        PalletHandler pHandle = new PalletHandler(getConnection());

        pSKey.setPalletId(ci.getPalletId());
        Pallet pallet = (Pallet)pHandle.findPrimary(pSKey);

        Station st = StationFactory.makeStation(getConnection(), pallet.getCurrentStationNo());

        WareHouseSearchKey whSkey = new WareHouseSearchKey();
        WareHouseHandler wWareHouseHandler = new WareHouseHandler(getConnection());
        whSkey.setStationNo(st.getWhStationNo());
        WareHouse wareHouse = (WareHouse)wWareHouseHandler.findPrimary(whSkey);

        // アイル、又は、搬送元No、棚No（棚間移動の倉庫内の移動先検索時）を現在ステーションNoにセットする。
        pallet.setCurrentStationNo(stNo);

        //棚間移動搬送データの空棚検索処理追加
        // ソフトゾーン、ハードゾーン併用検索
        ZoneSelector zn = new CombineZoneSelector(getConnection());
        // 空棚検索方法決定
        DepthShelfSelector dpt = new DepthShelfSelector(getConnection(), zn);
        Shelf location = null;
        if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
        {
            ShelfOperator cop = new ShelfOperator(getConnection());
            // 2009/09/26 Y.Osawa UPD ST
            location = cop.findRackToRackMove(pallet, wareHouse, st.getStationNo(), true);
            // 2009/09/26 Y.Osawa UPD ED
        }
        else
        {
            // 2009/09/26 Y.Osawa UPD ST
            location = dpt.select(pallet, wareHouse, true, true);
            // 2009/09/26 Y.Osawa UPD ED
        }

        if (location != null)
        {
            // フリーアロケーション運用の場合、荷幅、棚使用フラグを更新
            if (WareHouse.FREE_ALLOCATION_ON.equals(wareHouse.getFreeAllocationType()))
            {
                FreeAllocationShelfOperator freeshelfop =
                        new FreeAllocationShelfOperator(getConnection(), location.getStationNo());
                freeshelfop.alterWidth(pallet);
                // 棚の荷幅がフリーの場合、パレットの荷幅をセットする
                if (location.getWidth() == Shelf.WIDTH_FREE)
                {
                    location.setWidth(pallet.getWidth());
                }
            }

            //<jp> 検索した棚を入庫予約にする。</jp>
            //<en> Alter the status of searched location to 'reserved for storage'.</en>
            ShelfAlterKey shelfAkey = new ShelfAlterKey();
            ShelfHandler shelfHandle = new ShelfHandler(getConnection());
            shelfAkey.setStationNo(location.getStationNo());
            shelfAkey.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_RESERVATION);
            shelfHandle.modify(shelfAkey);

            return location;
        }
        else
        {
            return null;
        }
    }

    /**<jp>
     * 荷姿不一致時の代替棚検索を行ないます。
     * @param  ci   搬送先決定を行うCarryInformationインスタンス
     * @param  plt  搬送先決定を行うパレットインスタンス
     * @param  stNo 搬送元ステーションNo
     * @return 搬送先ステーションインスタンス。代替棚が検索された場合はShelfインスタンス。無い場合はnullを返します。
     * @throws Exception データアクセスで発生した場合に通知します。
     * @throws Exception データの内容に不整合があった場合に通知します。
     </jp>*/
    /**<en>
     * Search the alternative location for in case of load size unmatch.
     * @param  ci   :CarryInformation instance to determine the destination
     * @param  plt  :Pallet instance which determines the destinations.
     * @param  stNo :station no
     * @return :instance of receiving instance. Or Shelf instance if alternative location was searched. Returns null if there is none.
     * @throws Exception :Notifies if exception occurs in accessing data.
     * @throws Exception :Notifies if there are data inconsistency.
     </en>*/
    private Shelf getloadMisalignmentAlternativeLocation(CarryInfo ci, Pallet plt, String stNo)
            throws Exception
    {
        ShelfAlterKey sk = new ShelfAlterKey();
        ShelfHandler shelfHandle = new ShelfHandler(getConnection());

        WareHouseSearchKey key = new WareHouseSearchKey();
        WareHouseHandler wWareHouseHandler = new WareHouseHandler(getConnection());

        // ソフトゾーン、ハードゾーン併用検索
        ZoneSelector zn = new CombineZoneSelector(getConnection());
        // 空棚検索方法決定（空棚検索クラス）
        ShelfSelector dpt = new DepthShelfSelector(getConnection(), zn);

        Station st = StationFactory.makeStation(getConnection(), plt.getCurrentStationNo());

        key.setStationNo(st.getWhStationNo());
        WareHouse wareHouse = (WareHouse)wWareHouseHandler.findPrimary(key);

        // アイル、又は、倉庫ステーションNoを現在ステーションNoにセットする。
        plt.setCurrentStationNo(stNo);

        Shelf location = null;
        if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
        {
            // 荷姿不一致の空棚検索
            ShelfOperator cop = new ShelfOperator(getConnection());
            // 2009/09/26 Y.Osawa UPD ST
            location = cop.findRackToRackMoveLoadMisalignment(plt, wareHouse, st.getStationNo(), true);
            // 2009/09/26 Y.Osawa UPD ED
        }
        else
        {
            // 2009/09/26 Y.Osawa UPD ST
            location = dpt.select(plt, wareHouse, true, true);
            // 2009/09/26 Y.Osawa UPD ED
        }

        if (location != null)
        {
            // フリーアロケーション運用の場合、荷幅、棚使用フラグを更新
            if (WareHouse.FREE_ALLOCATION_ON.equals(wareHouse.getFreeAllocationType()))
            {
                FreeAllocationShelfOperator freeshelfop =
                        new FreeAllocationShelfOperator(getConnection(), location.getStationNo());
                freeshelfop.alterWidth(plt);
                // 棚の荷幅がフリーの場合、パレットの荷幅をセットする
                if (location.getWidth() == Shelf.WIDTH_FREE)
                {
                    location.setWidth(plt.getWidth());
                }
            }

            //<jp> 検索した棚を入庫予約にする。</jp>
            //<en> Setting the searched location as 'reserved for storage'.</en>
            sk.clear();
            sk.setStationNo(location.getStationNo());
            sk.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_RESERVATION);
            shelfHandle.modify(sk);

            return location;
        }
        else
        {
            return null;
        }
    }

    /**<jp>
     * 指定された搬送元ステーションNoより、リジェクトステーションを決定して返します。
     * リジェクトステーションが見つからない場合、nullを返します。
     * @param  stnum 搬送元ステーションNo
     * @return リジェクトステーションインスタンス。未定義の場合はnullを返します。
     * @throws Exception データアクセスで発生した場合に通知します。
     * @throws Exception データの内容に不整合があった場合に通知します。
     </jp>*/
    /**<en>
     * According to the specified sending station no., it designates hte reject station and returns.
     * If the reject station was not found, it returns null.
     * @param  stnum :sending station no.
     * @return :instance of reject instance. it returns null if undefined.
     * @throws Exception :Notifies if exception occurs in accessing data.
     * @throws Exception :Notifies if there are data inconsistency.
     </en>*/
    private Station getRejectStation(String stnum)
            throws Exception
    {
        Station st = StationFactory.makeStation(getConnection(), stnum);

        if (StringUtil.isBlank(st.getRejectStationNo()))
        {
            return null;
        }
        else
        {
            Station rejectSt = StationFactory.makeStation(getConnection(), st.getRejectStationNo());
            return rejectSt;
        }
    }

    /**<jp>
     * コントローラをクリアします。
     </jp>*/
    private void clearCtlr()
    {
        _workInfoCtlr = null;
        _hostSendCtlr = null;
        _areaCtlr = null;
        _asStockCtlr = null;
        _warenaviSysCtlr = null;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: Id33Process.java 8073 2013-12-03 06:56:52Z fukuwa $";
    }
}
