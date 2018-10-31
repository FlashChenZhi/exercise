// $Id: Id25Process.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id25;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.ArrivalAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ArrivalHandler;
import jp.co.daifuku.wms.base.dbhandler.ArrivalSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Arrival;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**<jp>
 * 搬送指示応答を処理するクラスです。
 * 応答区分をもとに該当するCarryInformationの更新を行います。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </jp>*/
/**<en>
 * This class processes the response to Carry Instruction. According to the classification of response,
 * it updates the corresponding CarryInformation.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </en>*/
public class Id25Process
        extends IdProcess
{
    /** <code>PROC_ID</code> */
    private static final String PROC_ID = "25";

    // Class fields --------------------------------------------------
    //<jp> 応答区分(正常)</jp>
    //<en> classificaiton of response (normal)</en>
    private static final int RESPONSE_NORMAL = 0;

    //<jp> 応答区分(在荷エラー)</jp>
    //<en> classificaiton of response (load presence error)</en>
    private static final int LOAD_ERR = 1;

    //<jp> 応答区分(多重設定)</jp>
    //<en> classificaiton of response (multiple set)</en>
    private static final int MULTI_SETTING = 3;

    //<jp> 応答区分(故障)</jp>
    //<en> classificaiton of response (breakdown)</en>
    private static final int FAILURE = 4;

    //<jp> 応答区分(設備切離し)</jp>
    //<en> classificaiton of response (equipment off-line)</en>
    private static final int EQUIP_OFFLINE = 5;

    //<jp> 応答区分(OFFLINE)</jp>
    //<en> classificaiton of response(OFFLINE)</en>
    private static final int OFFLINE = 6;

    //<jp> 応答区分(条件ERROR)</jp>
    //<en> classificaiton of response (condition error)</en>
    private static final int CONDITION_ERROR = 7;

    //<jp> 応答区分(BUFFER FULL)</jp>
    //<en> classificaiton of response(BUFFER FULL)</en>
    private static final int BUFFER_BULL = 11;

    //<jp> 応答区分(DATA ERROR)</jp>
    //<en> classificaiton of response(DATA ERROR)</en>
    private static final int DATA_ERROR = 99;

    // Class variables -----------------------------------------------

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
        return ("$Revision: 7996 $,$Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $");
    }

    /**<jp>
     * 搬送指示応答の正常処理を行います。在庫更新および搬送元ステーションの到着データをクリアします。
     * このメソッドは、搬送指示テキスト受信時以外に、入庫作業完了報告テキスト受信時に対象のCarryInformationの
     * 状態が応答待ちになっていた場合にも、テキスト飛び越し対策として使用されます。
     * @param  conn  データベースの接続コネクション
     * @param  ci    更新対象CarryInformation
     * @throws InvalidDefineException クラス定義が、正しくなかった場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Performs the normal process of Reply to Carry Instruction. It clears the stock update data and the arrival data of sdnding station.
     * Besides receiving the Carry Instructions, this method will be used as a measure when the texts was skipped
     * and receiving the storage completion report text while target CarryInformation is in 'watit for response',
     * @param  conn : Connection
     * @param  ci   : CarryInformation to update
     * @throws InvalidDefineException :Notifies when definition of the class was incorrect.
     * @throws ReadWriteException  :Notifies if error occured when accessing database.
     * @throws ScheduleException
     </en>*/
    public static void updateNormalResponce(Connection conn, CarryInfo ci)
            throws InvalidDefineException,
                ReadWriteException,
                ScheduleException
    {
        try
        {
            CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(conn, Id25Process.class);
            StationOperator sOpe = new StationOperator(conn, ci.getSourceStationNo());

            // 在庫更新タイミングが搬送指示応答時の場合、在庫の更新を行う。(直行の場合は必ず指示応答時)
            if (!WmsParam.STOCK_MODIFY_TIMING || SystemDefine.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
            {
                //<jp> 在庫更新を行う。</jp>
                //<en> Updates the stocks.</en>
                carryCompOpe.updateStock(ci);
            }
            //<jp> 搬送状態を「指示済み」に変更</jp>
            //<en> Alters the carry status to 'instructed'</en>
            CarryInfoAlterKey cakey = new CarryInfoAlterKey();
            CarryInfoHandler carryInfoHandle = new CarryInfoHandler(conn);
            cakey.setCarryKey(ci.getCarryKey());
            cakey.updateCmdStatus(CarryInfo.CMD_STATUS_INSTRUCTION);
            cakey.updateErrorCode(String.valueOf(RESPONSE_NORMAL));
            cakey.updateLastUpdatePname(Id25Process.class.getSimpleName());
            carryInfoHandle.modify(cakey);

            //<jp> 搬送元ステーションが到着報告有りの場合、到着情報をクリアする。</jp>
            //<en> If the sending station operates the arrival reports, it clears the arrival information.</en>
            if (sOpe.getStation().isArrivalCheck())
            {
//                // DFKLOOK:ここから修正(LOTTE対応)
//                sOpe.dropArrival();

                // 2009/06/19 ステーションの到着データは、mckeyが一致する場合のみクリアする
                // mckeyが一致しない場合、既に次のパレットが到着しているという事なので
                // 到着情報をクリアすると搬送指示が行なわれなくなる。

                // DFKLOOK 3.4
                ArrivalHandler arrivalHan = new ArrivalHandler(conn);
                ArrivalSearchKey arrKey = new ArrivalSearchKey();
                arrKey.setCarryKey(ci.getCarryKey());
                arrKey.setStationNo(sOpe.getStation().getStationNo());
                arrKey.setSendFlag(Arrival.ARRIVAL_SENDED);
                if (arrivalHan.count(arrKey) > 0)
                {
                    Arrival arrival[] = (Arrival[])arrivalHan.find(arrKey);
                    for (Arrival arrivals :arrival)
                    {
                        arrivalHan.drop(arrivals);
                    }
                }
                // DFKLOOK 3.4
//                if (ci.getCarryKey().equals(sOpe.getStation().getCarryKey()))
//                {
//                    sOpe.dropArrivalWithCarryKey(ci.getCarryKey());
//                    System.out.println("-----Id25 carrykey clear start----------");
//                    System.out.println("DNCARRYINFO=" + ci.getCarryKey());
//                    System.out.println("DMSTATION  =" + ci.getCarryKey());
//                    System.out.println("-----Id25 carrykey clear end----------");
//                }
//
//
//
//                // DFKLOOK:ここまで修正(LOTTE対応)
            }
        }
        catch (NotFoundException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }
        catch (InvalidStatusException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }
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
    public Id25Process()
    {
        super();
    }

    /**<jp>
     * 引数で渡されたAGCNoをセットし、このクラスの初期化を行います。
     * @param agcNumber AGCNo
     </jp>*/
    /**<en>
     * Sets the AGCNo passed through parameter, then initialize this class.
     * @param agcNo AGCNo
     </en>*/
    public Id25Process(String agcNo)
    {
        super(agcNo);
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 搬送指示応答の処理を行います。
     * 受信した電文のMC Keyから、<code>CarryInformation</code>を検索し、搬送指示応答の応答区分を
     * もとに搬送状態の更新処理を行ないます。
     * ただし、トランザクションのコミット・ロールバックは行っていませんので、
     * 呼び出し元で行う必要があります。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processes the response to Carry Instruction.
     * Search <code>CarryInformation</code> based on the MC Key of the received communication message,
     * then according to the classification of reply to the CarryInstruction, alter the carry status.
     * However, the commitment and rollback of transaction must be done by call resource
     * as they are not deone.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        As21Id25 id25dt = new As21Id25(rdt);
        CarryInfoHandler cih = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey cskey = new CarryInfoSearchKey();
        CarryInfoAlterKey cakey = new CarryInfoAlterKey();

        Object[] tObj = null;

        //<jp> MC keyを検索条件として設定</jp>
        //<en> Sets the MC key as a search condition.</en>
        cskey.setCarryKey(id25dt.getMcKey());
        //<jp> 該当のCarryInfoを取得</jp>
        //<en>Obtains corresponding CarryInfo.</en>
        //DFKLOOK:Start 3.5
        CarryInfo[] carryInfo = (CarryInfo[])cih.findForUpdate(cskey);
        //DFKLOOK:End 3.5

        //<jp> 該当データなし</jp>
        //<en> There is no corresponding data.</en>
        if (carryInfo.length == 0)
        {
            tObj = new Object[1];
            tObj[0] = id25dt.getMcKey();
            //<jp> 6026038=指定されたmckeyの搬送データが存在しません。mckey={0}</jp>
            //<en> 6026038=Transfer data for the designated MCKey does not exist. mckey={0}</en>
            RmiMsgLogClient.write(6026038, LogMessage.F_ERROR, getClass().getName(), tObj);
            return;
        }

        CarryInfo ci = carryInfo[0];

        //DFKLOOK:Start 3.5
        String cmdStatus = ci.getCmdStatus();
        //CarryInforamtionの搬送状態が'応答待ち'でなければ処理しない
        if ( !CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(cmdStatus))
        {
            return;
        }
        //DFKLOOK:End 3.5

        StationOperator sOpe = new StationOperator(getConnection(), ci.getSourceStationNo());
        //<jp> 応答区分を確認</jp>
        //<en> Checks the classification of response.</en>
        switch (id25dt.getResponseClassification())
        {
            //<jp> 正常応答</jp>
            //<en> Normal response</en>
            case RESPONSE_NORMAL:
                if (CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(ci.getCmdStatus()))
                {
                    //<jp> 正常応答時の搬送データ更新処理を呼び出す。</jp>
                    //<en> Calls the process of carry data update at the normal response.</en>
                    updateNormalResponce(getConnection(), ci);
                }
                else
                {
                    tObj = new Object[1];
                    tObj[0] = id25dt.getMcKey();
                    //<jp> 6024004=搬送状態が応答待ちではない搬送データに対して搬送指示応答を受信しました。受信テキストは無視します。 mckey={0}</jp>
                    //<en> 6024004=Unexpected reply to carry instruction. No action for the received text. mckey={0}</en>
                    RmiMsgLogClient.write(6024004, LogMessage.F_WARN, getClass().getName(), tObj);
                }
                break;

            //<jp> 応答区分(在荷エラー)</jp>
            //<en> Reply classification (load presence error)</en>
            case LOAD_ERR:
                tObj = new Object[1];
                tObj[0] = id25dt.getMcKey();
                //<jp> 6024005=搬送搬送指示応答で在荷エラーを受信しました。 mckey={0}</jp>
                //<en> 6024005=Load presence error was received in response to transfer instruction. mckey={0}</en>
                RmiMsgLogClient.write(6024005, LogMessage.F_WARN, getClass().getName(), tObj);
                //<jp> 搬送状態を異常に更新する。</jp>
                //<en> Update the carry status to 'error'.</en>
                cakey.clear();
                cakey.setCarryKey(ci.getCarryKey());
                cakey.updateCmdStatus(CarryInfo.CMD_STATUS_ERROR);
                cakey.updateErrorCode(String.valueOf(LOAD_ERR));
                cakey.updateLastUpdatePname(getClassName());
                cih.modify(cakey);
                break;

            //<jp> 多重引当</jp>
            //<en> Multiple set</en>
            case MULTI_SETTING:
                tObj = new Object[1];
                tObj[0] = id25dt.getMcKey();
                //<jp> 6024006=搬送指示応答で多重引当を受信しました。 mckey={0}</jp>
                //<en> 6024006=Multiple allocation was received in response to transfer instruction. mckey={0}</en>
                RmiMsgLogClient.write(6024006, LogMessage.F_WARN, getClass().getName(), tObj);
                break;

            //<jp> 故障</jp>
            //<en> Breakdown</en>
            case FAILURE:
                tObj = new Object[1];
                tObj[0] = id25dt.getMcKey();
                //<jp> 6024012=搬送指示応答で設備故障を受信しました。 搬送Key={0}</jp>
                //<en> 6024012=Equipment error was received in response to transfer instruction. Transkey={0}</en>
                RmiMsgLogClient.write(6024012, LogMessage.F_WARN, getClass().getName(), tObj);

                //<jp> 搬送状態を開始に戻す</jp>
                //<en> Reset the carry status to 'start'.</en>
                cakey.clear();
                cakey.setCarryKey(ci.getCarryKey());
                cakey.updateCmdStatus(CarryInfo.CMD_STATUS_START);
                cakey.updateErrorCode(String.valueOf(FAILURE));
                cakey.updateLastUpdatePname(getClassName());
                cih.modify(cakey);

                //DFKUPDATE 3.5
                if (sOpe.getStation().isArrivalCheck())
                {
                    // 到着情報を未指示状態に戻す
                    cancelArrival(ci.getCarryKey(), ci.getSourceStationNo());
                }
                //DFKUPDATE 3.5

                break;

            //<jp>  設備切離し</jp>
            //<en>  Equipment off-line</en>
            case EQUIP_OFFLINE:
                tObj = new Object[1];
                tObj[0] = id25dt.getMcKey();
                //<jp> 6024007=搬送指示応答で設備切離しを受信しました。 mckey={0}</jp>
                //<en> 6024007=Equipment offline was received in response to transfer instruction. mckey={0}</en>
                RmiMsgLogClient.write(6024007, LogMessage.F_WARN, getClass().getName(), tObj);

                //<jp> 搬送状態を開始に戻す</jp>
                //<en> Reset the carry status to 'start'.</en>
                cakey.clear();
                cakey.setCarryKey(ci.getCarryKey());
                cakey.updateCmdStatus(CarryInfo.CMD_STATUS_START);
                cakey.updateErrorCode(String.valueOf(EQUIP_OFFLINE));
                cakey.updateLastUpdatePname(getClassName());
                cih.modify(cakey);

                //DFKUPDATE 3.5
                if (sOpe.getStation().isArrivalCheck())
                {
                    // 到着情報を未指示状態に戻す
                    cancelArrival(ci.getCarryKey(), ci.getSourceStationNo());
                }
                //DFKUPDATE 3.5

                break;

            //<jp> オフライン中</jp>
            //<en> Off-line</en>
            case OFFLINE:
                tObj = new Object[1];
                tObj[0] = id25dt.getMcKey();
                //<jp> 6024008=搬送指示応答でオフライン中を受信しました。 mckey={0}</jp>
                //<en> 6024008=Offline was received in response to transfer instruction. mckey={0}</en>
                RmiMsgLogClient.write(6024008, LogMessage.F_WARN, getClass().getName(), tObj);

                //<jp> 搬送状態を開始に戻す</jp>
                //<en> Reset the carry status to 'start'.</en>
                cakey.clear();
                cakey.setCarryKey(ci.getCarryKey());
                cakey.updateCmdStatus(CarryInfo.CMD_STATUS_START);
                cakey.updateErrorCode(String.valueOf(OFFLINE));
                cakey.updateLastUpdatePname(getClassName());
                cih.modify(cakey);

                //DFKUPDATE 3.5
                if (sOpe.getStation().isArrivalCheck())
                {
                    // 到着情報を未指示状態に戻す
                    cancelArrival(ci.getCarryKey(), ci.getSourceStationNo());
                }
                //DFKUPDATE 3.5

                //<jp> グループコントローラの状態をオフラインに変更する。</jp>
                //<en> Alter the state of group controller to off-line.</en>
                GroupController gc = GroupController.getInstance(getConnection(), getAgcNo());
                gc.setStatus(GroupController.STATUS_OFFLINE);
                break;

            //<jp> 条件ERROR</jp>
            //<en> Condition error</en>
            case CONDITION_ERROR:
                tObj = new Object[1];
                tObj[0] = id25dt.getMcKey();
                //<jp> 6024009=搬送指示応答で条件エラーを受信しました。 mckey={0}</jp>
                //<en> 6024009=Condition error was received in response to transfer instruction. mckey={0}</en>
                RmiMsgLogClient.write(6024009, LogMessage.F_WARN, getClass().getName(), tObj);

                //<jp> 搬送状態を開始に戻す</jp>
                //<en> Reset the carry status to 'start'.</en>
                cakey.clear();
                cakey.setCarryKey(ci.getCarryKey());
                cakey.updateCmdStatus(CarryInfo.CMD_STATUS_START);
                cakey.updateErrorCode(String.valueOf(CONDITION_ERROR));
                cakey.updateLastUpdatePname(getClassName());
                cih.modify(cakey);

                //DFKUPDATE 3.5
                if (sOpe.getStation().isArrivalCheck())
                {
                    // 到着情報を未指示状態に戻す
                    cancelArrival(ci.getCarryKey(), ci.getSourceStationNo());
                }
                //DFKUPDATE 3.5

                //<jp> 搬送元ステーションを中断中にする。</jp>
                //<en> Alter the status of sending station to 'suspended'.</en>
                sOpe.alterSuspend(true);
                break;

            //<jp> BUFFER FULL</jp>
            //<en> BUFFER FULL</en>
            case BUFFER_BULL:
                tObj = new Object[1];
                tObj[0] = id25dt.getMcKey();
                //<jp> 6024010=搬送指示応答でバッファフルを受信しました。 mckey={0}</jp>
                //<en> 6024010=Buffer full was received in response to transfer instruction. mckey={0}</en>
                RmiMsgLogClient.write(6024010, LogMessage.F_WARN, getClass().getName(), tObj);

                //<jp> 搬送状態を開始に戻す</jp>
                //<en> Reset the carry status to 'start'.</en>
                cakey.clear();
                cakey.setCarryKey(ci.getCarryKey());
                cakey.updateCmdStatus(CarryInfo.CMD_STATUS_START);
                cakey.updateErrorCode(String.valueOf(BUFFER_BULL));
                cakey.updateLastUpdatePname(getClassName());
                cih.modify(cakey);

                //DFKUPDATE 3.5
                if (sOpe.getStation().isArrivalCheck())
                {
                    // 到着情報を未指示状態に戻す
                    cancelArrival(ci.getCarryKey(), ci.getSourceStationNo());
                }
                //DFKUPDATE 3.5

                break;

            // DATA ERROR
            case DATA_ERROR:
                tObj = new Object[1];
                tObj[0] = id25dt.getMcKey();
                //<jp> 6024011=搬送指示応答でデータエラーを受信しました。 mckey={0}</jp>
                //<en> 6024011=Data error was received in response to transfer instruction. mckey={0}</en>
                RmiMsgLogClient.write(6024011, LogMessage.F_WARN, getClass().getName(), tObj);

                //<jp> 搬送状態を異常に変更</jp>
                //<en> Reset the carry status to 'start'.</en>
                cakey.clear();
                cakey.setCarryKey(ci.getCarryKey());
                cakey.updateCmdStatus(CarryInfo.CMD_STATUS_ERROR);
                cakey.updateErrorCode(String.valueOf(DATA_ERROR));
                cakey.updateLastUpdatePname(getClassName());
                cih.modify(cakey);

                //<jp> 搬送元ステーションを中断中にする。</jp>
                //<en> Alter the status of sending station to 'suspended'.</en>
                sOpe.alterSuspend(true);

                //<jp> 搬送元ステーションが到着報告有りの場合、到着情報をクリアする。</jp>
                //<en> Clear the arrival data if the sending station operates the arrival reports.</en>
                if (sOpe.getStation().isArrivalCheck())
                {
//                    // DFKLOOK:ここから修正(LOTTE対応)
//                    sOpe.dropArrival();
//
//                    // 2009/06/19 ステーションの到着データは、mckeyが一致する場合のみクリアする
//                    // mckeyが一致しない場合、既に次のパレットが到着しているという事なので
//                    // 到着情報をクリアすると搬送指示が行なわれなくなる。
//                    if (ci.getCarryKey().equals(sOpe.getStation().getCarryKey()))
//                    {
//                        sOpe.dropArrivalWithCarryKey(ci.getCarryKey());
//                    }
//                    // DFKLOOK:ここまで修正(LOTTE対応)
                    // DFKLOOK 3.4
                    ArrivalHandler arrivalHand = new ArrivalHandler(getConnection());
                    ArrivalSearchKey arrivalSkey = new ArrivalSearchKey();
                    // 検索キーセット
                    arrivalSkey.setCarryKey(ci.getCarryKey());
                    arrivalSkey.setStationNo(sOpe.getStation().getStationNo());
                    // 削除
                    arrivalHand.drop(arrivalSkey);

                    // DFKLOOK 3.4
                }
                break;

            //<jp> 未定義の応答区分</jp>
            //<en> Undefined response classification </en>
            default:
                tObj = new Object[2];
                tObj[0] = PROC_ID;
                tObj[1] = String.valueOf(id25dt.getResponseClassification());
                //<jp> 6026037=テキストID{0}の応答区分が不正です。受信応答区分={1}</jp>
                //<en> 6026037=Response category for text ID {0} is invalid.  Receive response category={1}</en>
                RmiMsgLogClient.write(6026037, LogMessage.F_WARN, getClass().getName(), tObj);
                throw new InvalidDefineException(WmsMessageFormatter.format(6026037, tObj));
        }
    }

    // Private methods -----------------------------------------------

    /**
     * 到着情報を未指示状態に戻す
     * @param carrykey 搬送キー
     * @param statioNo ステーションNo
     * @throws ReadWriteException
     * @throws NotFoundException
     */
    public void cancelArrival(String carrykey, String stationNo)
            throws ReadWriteException,
                NotFoundException
    {
        ArrivalHandler arrivalHandler = new ArrivalHandler(getConnection());
        ArrivalAlterKey arrivalAlterKey = new ArrivalAlterKey();

        arrivalAlterKey.setStationNo(stationNo);
        arrivalAlterKey.setCarryKey(carrykey);
        arrivalAlterKey.updateSendFlag(Arrival.ARRIVAL_NOT_SEND);
        arrivalHandler.modify(arrivalAlterKey);
    }
}
//end of class

