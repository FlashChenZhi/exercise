// $Id: Id26SubThread.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id26;
import jp.co.daifuku.wms.asrs.communication.as21.ControlInfo;
import jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.asrs.location.StationOperatorFactory;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.StationController;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LoadSizeHandler;
import jp.co.daifuku.wms.base.dbhandler.LoadSizeSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.WidthHandler;
import jp.co.daifuku.wms.base.dbhandler.WidthSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.LoadSize;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.handler.db.SQLErrors;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**<jp>
 * 到着報告を処理するクラスです。
 * mckeyよりCarryInformationを生成し、搬送区分を元に到着処理を行います。
 * mckeyがダミー到着を表す値の場合（通常は'99999999'）
 * ダミー到着処理を行います。
 * 実際の到着処理は到着報告テキスト内のステーションNoを元に生成された、Stationを継承したクラスによって行われます。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class processes the arrival reports. It generates the CarryInformation based on mckey,
 * then handles the arrival process according to the transport section.
 * If the mckey represents the value of dummy arrival (usually '99999999'), it processes the dummy arrival.
 * The actula arrival process will be handled by the class that inherited the Station generated based on the
 * station no. included in arrival report text.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class Id26SubThread
        extends IdProcess
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /** 管理対象のステーショングループNo. */
    private String _groupNo = Id26Process.DEFAULT_STATION_GROUP_NO;

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
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルトコンストラクタ
     * AGCNoにGroupController.DEFAULT_AGC_NUMBERをセット
     </jp>*/
    /**<en>
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     </en>*/
    public Id26SubThread()
    {
        super();
    }

    /**<jp>
     * 引数で渡されたAGCNo、ステーショングループNo.をセットし、このクラスの初期化を行います。
     * @param agcNo AGCNo
     * @param groupNo ステーショングループNo.
     </jp>*/
    public Id26SubThread(String agcNo, String groupNo)
    {
        super(agcNo);
        setGroupNo(groupNo);
    }

    // Public methods ------------------------------------------------
    /**
     * ステーショングループNo.を返します。
     * @return ステーショングループNo.を返します。
     */
    public String getGroupNo()
    {
        return _groupNo;
    }

    /**
     * ステーショングループNo.を設定します。
     * @param groupNo ステーショングループNo.
     */
    public void setGroupNo(String groupNo)
    {
        _groupNo = groupNo;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 到着報告の処理を行います。
     * 受信した電文のMC Keyから、<code>CarryInformation</code>を検索し、パレット到着後の処理を行います。
     * 受信データがダミー到着データの場合は、ダミー到着CarryInformationインスタンスの生成を行います。
     * 通常のMCKEYの場合、該当データの検索を行い、
     * その内容でCarryInformationインスタンスを生成し荷姿、現在位置を更新します。
     * 最後にStationクラスの到着処理を実行し、ステーションに応じた搬送データ更新処理を行います。
     * CarryInformationの搬送区分が出庫でかつ、搬送状態が指示済みまたは応答待ちであれば,
     * 出庫完了処理が飛ばされたと判断して完了処理を行います。
     * トランザクションのコミット・ロールバックは行っていませんので、呼び出し元で行う必要があります。
     * 但し、引当等の処理順上でデータベースのデッドロックが発生した場合は、この処理でロールバックし、
     * 到着処理をリトライします。
     *
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processing the arrival report.
     * Based on the MC Key of received communication message, it searches the <code>CarryInformation</code> and processes after pallet arrival.
     * If received the dummy arrival data, it will generate the CarryInformation instance for dummy arrival.
     * With regular MCKEY, it searches the corresponding data then generates the CarryInformation instance based on its content.
     * Then it updates the load size and current position accordingly.
     * Finally it executes the arrival process of the Station class abd updates the carry data according to the station.
     * If transport section of CarryInformation was retrieval with its status 'instructed' or 'wait for response',
     * it determines that retrieval completion process has been skipped, and carries out the completion process.
     * Call source needs to commit or rollback the transaction, as they are not done here.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    @SuppressWarnings("cast")
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        // データベースのデッドロックが発生した場合のリトライ回数と、デッドロック発生から
        // リトライまでスリープ時間を取得します。
        int retryMax = WmsParam.DEADLOCK_RETRY_MAX_COUNT;
        int sleepTime = WmsParam.DEADLOCK_RETRY_SLEEP_TIME;

        for (int retry = 0; retry <= retryMax; retry++)
        {
            try
            {
                processArival(rdt);
                break;
            }
            catch (Exception e)
            {
                // Exceptionがデータベース、ファイルI/O例外であるか確認
                if (e instanceof ReadWriteException)
                {
                    // データベース、ファイルI/O例外である場合は例外の原因を参照
                    if ((Exception)e.getCause() instanceof SQLException)
                    {
                        // 元々の例外の原因がSQLExceptionなので、ロードしたSQLエラークラスに発生した
                        // 例外のエラーコードを渡してWMS内部エラーコードに変換します。
                        SQLErrors errorsClass =
                                HandlerUtil.loadSQLErrors(HandlerUtil.loadStoreMetaData(Stock.STORE_NAME));
                        if (errorsClass.parseErrorCode((SQLException)e.getCause()) == SQLErrors.ERR_DEADLOCK)
                        {
                            // SQLExceptionのエラーコードがデッドロックだったので、一旦 rollbackします。
                            getConnection().rollback();

                            // 6024046=デッドロックが発生しました。 mckey={0}
                            Object[] tObj = new Object[1];
                            As21Id26 id26dt = new As21Id26(rdt);
                            tObj[0] = id26dt.getMcKey();
                            RmiMsgLogClient.write(6024046, LogMessage.F_ERROR, getClass().getName(), tObj);
                            if (retry < retryMax)
                            {
                                // リトライを行なう前に少しSleepします。
                                sleep(sleepTime);

                                // 6024047=mckey={0} の到着処理を再度実行します。
                                RmiMsgLogClient.write(6024047, LogMessage.F_ERROR, getClass().getName(), tObj);
                                continue;
                            }
                        }
                    }
                }

                // デッドロック以外のException、又は、デッドロックリトライのCount Up時は、そのままExceptionをthrowします。
                throw e;
            }
        }
    }

    /**<jp>
     * 到着報告の処理を行います。
     * 受信した電文のMC Keyから、<code>CarryInformation</code>を検索し、パレット到着後の処理を行います。
     * 受信データがダミー到着データの場合は、ダミー到着CarryInformationインスタンスの生成を行います。
     * 通常のMCKEYの場合、該当データの検索を行い、
     * その内容でCarryInformationインスタンスを生成し荷姿、現在位置を更新します。
     * 最後にStationクラスの到着処理を実行し、ステーションに応じた搬送データ更新処理を行います。
     * CarryInformationの搬送区分が出庫でかつ、搬送状態が指示済みまたは応答待ちであれば,
     * 出庫完了処理が飛ばされたと判断して完了処理を行います。
     * トランザクションのコミット・ロールバックは行っていませんので、呼び出し元で行う必要があります。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processing the arrival report.
     * Based on the MC Key of received communication message, it searches the <code>CarryInformation</code> and processes after pallet arrival.
     * If received the dummy arrival data, it will generate the CarryInformation instance for dummy arrival.
     * With regular MCKEY, it searches the corresponding data then generates the CarryInformation instance based on its content.
     * Then it updates the load size and current position accordingly.
     * Finally it executes the arrival process of the Station class abd updates the carry data according to the station.
     * If transport section of CarryInformation was retrieval with its status 'instructed' or 'wait for response',
     * it determines that retrieval completion process has been skipped, and carries out the completion process.
     * Call source needs to commit or rollback the transaction, as they are not done here.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    protected void processArival(byte[] rdt)
            throws Exception
    {
        //<jp> 更新対象CarryInformation</jp>
        //<en> CarryInformation to update</en>
        CarryInfo ci = null;

        Connection conn = getConnection();
        CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(conn, getClass());

        //<jp> 受信テキストを分解</jp>
        //<en> Decomposition of received texts</en>
        As21Id26 id26dt = new As21Id26(rdt);
        //<jp> 到着ステーションを作成</jp>
        //<en> Creating the arrival station</en>
        Station st = null;
        try
        {
            st = StationFactory.makeStation(conn, id26dt.getStationNo());
        }
        catch (NotFoundException e)
        {
            //<jp> 指定されたステーションが存在しない場合は</jp>
            //<jp> そのまま本ID処理を終了する。例外扱いにはしない</jp>
            //<en> If specified station does not exist, </en>
            //<en> terminates this ID processing jsut as it is. No handling for exception is conducted.</en>
            return;
        }

        //<jp> コの字ステーション出庫側の作業指示運用の場合は</jp>
        //<jp> 到着処理を行わない。</jp>
        //<en> The arrival process will not be conducted in case the on-line indication is operated on the retrieval side </en>
        //<en> of U-shaped station.</en>
        StationOperator sto = StationOperatorFactory.makeOperator(conn, st.getStationNo(), st.getClassName());
        if (sto instanceof FreeRetrievalStationOperator)
        {
            if (Station.OPERATION_DISPLAY_INSTRUCTION.equals(st.getOperationDisplay()))
            {
                return;
            }
        }

        // フリーアロケーション運用倉庫の場合、制御情報のチェックを行う（在荷OFFの場合はチェックしない）
        StationController stOpe = new StationController(conn, getClass());
        boolean isFreeAlloc = stOpe.isFreeAllocationStation(st.getStationNo());
        ControlInfo conInfo = new ControlInfo();
        if (isFreeAlloc && id26dt.getLoad())
        {
            conInfo = conInfo.convertControlInfo(id26dt.getControlInformation());
            if (null == conInfo)
            {
                Object[] tObj = new Object[2];
                tObj[0] = id26dt.getStationNo();
                tObj[1] = id26dt.getControlInformation();
                //<jp> 6026600=到着報告テキスト内の制御情報が不正です。StationNo={0} 制御情報={1}</jp>
                RmiMsgLogClient.write(6026600, LogMessage.F_ERROR, getClass().getName(), tObj);
                return;
            }

            // 荷姿チェック
            LoadSizeSearchKey lskey = new LoadSizeSearchKey();
            LoadSizeHandler lHandler = new LoadSizeHandler(getConnection());
            lskey.setLength(conInfo.getLength());
            lskey.setHeight(conInfo.getHeight());
            // 荷姿情報検索
            if (lHandler.count(lskey) == 0)
            {
                Object[] tObj = new Object[2];
                tObj[0] = id26dt.getStationNo();
                tObj[1] = id26dt.getControlInformation();
                // 6026603=到着報告テキスト内の制御情報が不正です。荷姿情報に存在しません。StationNo={0} 制御情報={1}
                RmiMsgLogClient.write(6026603, LogMessage.F_ERROR, getClass().getName(), tObj);
                return;
            }

            // 荷幅チェック
            WidthSearchKey wskey = new WidthSearchKey();
            WidthHandler wHandler = new WidthHandler(getConnection());
            wskey.setWidth(conInfo.getWidth());
            wskey.setWhStationNo(st.getWhStationNo());
            // 荷幅情報検索
            if (wHandler.count(wskey) == 0)
            {
                Object[] tObj = new Object[2];
                tObj[0] = id26dt.getStationNo();
                tObj[1] = id26dt.getControlInformation();
                // 6026604=到着報告テキスト内の制御情報が不正です。荷幅情報に存在しません。StationNo={0} 制御情報={1}
                RmiMsgLogClient.write(6026604, LogMessage.F_ERROR, getClass().getName(), tObj);
                return;
            }
        }

        //<jp> MCkeyをチェック</jp>
        //<en> Checks the MCkey.</en>
        String mckey = id26dt.getMcKey();
        if (WmsParam.DUMMY_MCKEY.equals(mckey))
        {
            //<jp> ダミー到着の場合、ダミーCarryInforationインスタンス生成</jp>
            //<en> In case of dummy arrival, it should create the instance of dummy CarryInforation.</en>
            Pallet dpl = new Pallet();

            dpl.setHeight(Integer.parseInt(id26dt.getDimensionInformation()));
            dpl.setBcrData(StringUtil.convValidStr(id26dt.getBcData()));
            ci = new CarryInfo();
            // 制御情報をセット
            dpl.setControlinfo(StringUtil.convValidStr(id26dt.getControlInformation()));
            ci.setCarryFlag(CarryInfo.CARRY_FLAG_STORAGE);
            ci.setCarryKey(mckey);
            ci.setPalletId(dpl.getPalletId());

            //<jp> ステーション別の到着処理を行う。</jp>
            //<en> Processes the arrival by each station.</en>
            sto.arrival(ci, dpl, this.getClass());
        }
        else
        {
            //<jp> MC keyを検索条件として設定し、CarryInforationインスタンス生成</jp>
            //<en> Sets the MC key as a search condition, generates the instance of CarryInforation. </en>
            CarryInfoHandler cih = new CarryInfoHandler(conn);
            CarryInfoSearchKey cskey = new CarryInfoSearchKey();
            cskey.setCarryKey(mckey);
            CarryInfo[] earr = (CarryInfo[])cih.findForUpdate(cskey);

            //<jp> 該当データなし</jp>
            //<en> There is no corresponding data.</en>
            if (earr.length == 0)
            {
                Object[] tObj = new Object[1];
                tObj[0] = mckey;
                //<jp> 6026038=指定されたmckeyの搬送データが存在しません。mckey={0}</jp>
                //<en> 6026038=Transfer data for the designated MCKey does not exist. mckey={0}</en>
                RmiMsgLogClient.write(6026038, LogMessage.F_ERROR, getClass().getName(), tObj);
                return;
            }

            if (earr[0] instanceof CarryInfo)
            {
                ci = (CarryInfo)earr[0];
            }
            else
            {
                Object[] tObj = new Object[1];
                tObj[0] = "CarryInformation";
                //<jp> 6006008={0}以外のオブジェクトが返されました。</jp>
                //<en> 6006008=Object other than {0} was returned.</en>
                RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, getClass().getName(), tObj);
                throw new InvalidProtocolException(WmsMessageFormatter.format(6006008, tObj));
            }

            //<jp> CarryInformationの搬送区分が出庫の場合搬送状態をチェック</jp>
            //<jp> 指示済みまたは応答待ちであれば</jp>
            //<jp> 出庫完了処理が飛ばされたと判断して完了処理を行う。</jp>
            //<en> Checks the carry status if the transport section of CarryInformation is 'retrieval'.</en>
            //<en> If the transport section was 'instructed' or 'wait for response', it determines that retrieval </en>
            //<en> completion process has been skipped and carries out the completion process.</en>
            if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(ci.getCarryFlag()))
            {
                if ((CarryInfo.CMD_STATUS_INSTRUCTION.equals(ci.getCmdStatus()))
                        || (CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(ci.getCmdStatus())))
                {
                    //<jp> 作業完了報告（出庫）テキストが飛んでいることをロギングする</jp>
                    //<en> Logging that work completion report (retrieval) text has been skipped.</en>
                    Object[] tObj = new Object[1];
                    tObj[0] = ci.getCarryKey();
                    //<jp> 6022022=出庫完了処理が行われていません。強制的に出庫完了処理行います。mckey={0}</jp>
                    //<en> 6022022=Picking completion is not processed. Forcing to complete picking. mckey={0}</en>
                    RmiMsgLogClient.write(6022022, LogMessage.F_ERROR, getClass().getName(), tObj);

                    Connection connect = getConnection();
                    try
                    {
                        //<jp> CarryInformationに対して、出庫完了処理を実行する。</jp>
                        //<en> Executes the retrieval completion process for this CarryInformation.</en>
                        Id33Process.normalRetrievalCompletion(connect, ci);

                        // WareNavi3.5.4
                        earr = (CarryInfo[])cih.find(cskey);
                        ci = (CarryInfo)earr[0];
                        // WareNavi3.5.4
                    }
                    // 例外発生時も処理を続行する
                    catch (Exception e)
                    {
                        connect.rollback();
                        // 別スレッドでID25処理が行われているかの確認
                        earr = (CarryInfo[])cih.find(cskey);

                        //<jp> 該当データなし</jp>
                        //<en> There is no corresponding data.</en>
                        if (earr.length == 0)
                        {
                            return;
                        }
                        Object[] tObject = {
                            earr[0].getCarryKey(),
                        };
                        // 搬送状態が応答待ち、指示済みの場合、引き続き到着処理を行えないと判断する
                        if ((CarryInfo.CMD_STATUS_INSTRUCTION.equals(earr[0].getCmdStatus()))
                                || (CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(earr[0].getCmdStatus())))
                        {
                            //<jp> 6024048=受信電文飛び越し処理でエラーが発生したため、処理を中断しました。mckey={0} StackTrace={1}
                            RmiMsgLogClient.write(new TraceHandler(6024048, e), getClass().getName(), tObject);
                            return;
                        }
                        //<jp> 6024045=受信電文飛び越し処理でエラーが発生しましたが、処理を続行します。mckey={0} StackTrace={1}</jp>
                        RmiMsgLogClient.write(new TraceHandler(6024045, e), getClass().getName(), tObject);

                    }
                }
            }

            // 2010/07/30 Y.Osawa UPD ST
            // ピッキング出庫かつコの字出庫コンベヤの場合はでも在荷OFFでも払出し処理を行わない
            boolean remove = true;
            if (sto instanceof FreeRetrievalStationOperator)
            {
                String retrDetail = ci.getRetrievalDetail();
                if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(retrDetail)
                        || CarryInfo.RETRIEVAL_DETAIL_PICKING.equals(retrDetail)
                        || CarryInfo.RETRIEVAL_DETAIL_ADD_STORING.equals(retrDetail))
                {
                    remove = false;
                }
            }

            //<jp> 受信テキストの在荷情報をチェックする、在荷Offの場合、ユニット払出し。</jp>
            //<en> Checks the load presence data in received text. If the load presence is OFF, unit trasfer to be conducted.</en>
//            if (!id26dt.getLoad())
            if (!id26dt.getLoad() && remove)
            // 2010/07/30 Y.Osawa UPD ED
            {
                if (st.isReStoringOperation())
                {
                    //<jp> ユニット出庫在庫更新（再入庫予定データ作成）</jp>
                    //<en> Updates the unit retrieval stocks (with creation of scheduled re-storage data)</en>
                    carryCompOpe.unitRetrieval(ci, true);
                }
                else
                {
                    //<jp> ユニット出庫在庫更新（再入庫予定データ作成なし）</jp>
                    //<en> Updates the unit retrieval stocks (no creation ofscheduled re-storage data)</en>
                    carryCompOpe.unitRetrieval(ci, false);
                }
            }
            else
            {
                //<jp> 今回搬送対象となるPalletの更新を行なう</jp>
                //<en> Updates information of Pallet to carry this time.</en>
                PalletHandler pHandle = new PalletHandler(conn);

                PalletAlterKey pAKey = new PalletAlterKey();
                pAKey.setPalletId(ci.getPalletId());

                //<jp> 到着ステーションが荷姿チェックありの場合、受信テキストの荷姿情報をPalletにセットする。</jp>
                //<en> If the arriving station operates the load size check, set Pallete with the load size information in teh received text.</en>
                if (st.isLoadSizeCheck())
                {
                    // フリーアロケーション倉庫
                    if (isFreeAlloc)
                    {
                        LoadSizeHandler lhandl = new LoadSizeHandler(getConnection());
                        LoadSizeSearchKey lkey = new LoadSizeSearchKey();

                        lkey.setLength(conInfo.getLength());
                        lkey.setHeight(conInfo.getHeight());
                        LoadSize[] loadsize = (LoadSize[])lhandl.find(lkey);

                        pAKey.updateHeight(loadsize[0].getLoadSize());
                        pAKey.updateWidth(conInfo.getWidth());
                        pAKey.updateControlinfo(id26dt.getControlInformation());
                    }
                    // フリーアロケーション倉庫以外
                    else
                    {
                        pAKey.updateHeight(Integer.parseInt(id26dt.getDimensionInformation()));
                    }
                }
                pAKey.updateBcrData(StringUtil.convValidStr(id26dt.getBcData()));
                pAKey.updateLastUpdatePname(getClass().getSimpleName());
                pHandle.modify(pAKey);

                //<jp> CarryInformationの到着ステーションと受信したステーションが異なる場合、ロギングメッセージ出力</jp>
                //<en> Output the log message if the arrival station of CarryInformation differs from the station that received data.</en>
                if (!ci.getDestStationNo().equals(st.getStationNo()))
                {
                    //<jp> 親ステーションが一致する場合、代表ステーションに対する指示なのでログは落とさない。</jp>
                    //<en> If parent station match, the instruction is given to main station; therefore no log to be recorded.</en>
                    if (!ci.getDestStationNo().equals(st.getParentStationNo()))
                    {
                        Object[] tObj = new Object[3];
                        tObj[0] = ci.getDestStationNo();
                        tObj[1] = st.getStationNo();
                        tObj[2] = mckey;
                        //<jp> 6022019=搬送データの搬送先ステーションと到着ステーションが違います。搬送先ステーション={0} 到着ステーション={1} mckey={2}</jp>
                        //<en> 6022019=Mismatch of receiving station in data and arrived station To ST={0} Arrival={1} mckey={2}</en>
                        RmiMsgLogClient.write(6022019, LogMessage.F_NOTICE, getClass().getName(), tObj);
                    }

                    //<jp> 搬送先ステーションを実際に到着したステーションに更新する。</jp>
                    //<jp>（直行データの起点・終点の判断に必要）</jp>
                    //<en> Update the data of receiving station to the actually arrived station. </en>
                    //<en> (in case of direct travel, it i necessary to determine the start position and end position)</en>
                    ci.setDestStationNo(st.getStationNo());
                }

                //<jp> ステーション別の到着処理を行う。</jp>
                //<en> Processes the arrival per station.</en>
                StationOperator sOpe = StationOperatorFactory.makeOperator(conn, st.getStationNo(), st.getClassName());
                sOpe.arrival(ci, new Pallet(), this.getClass());
            }
        }
    }

    // Private methods -----------------------------------------------

}
//end of class

