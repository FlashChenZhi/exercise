// $Id: Id35Process.java 5301 2009-10-28 05:36:02Z ota $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id35;
import jp.co.daifuku.wms.asrs.control.CarryCompleteOperator.CARRY_COMPLETE;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.ArrivalHandler;
import jp.co.daifuku.wms.base.dbhandler.ArrivalSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Arrival;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.InOutResult;
import jp.co.daifuku.wms.base.entity.Station;

/**<jp>
 * 搬送データ削除報告を処理するクラスです。指定されたmckeyに該当するCarryInformationを削除します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/11/24</TD><TD>Y.Kawai</TD><TD>eWareNavi対応</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5301 $, $Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class processes the carry data deletion report. It deletes the CarryInformation corresponding to the specified mckey.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5301 $, $Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $
 * @author  $Author: ota $
 </en>*/
public class Id35Process
        extends IdProcess
{
    // Class fields --------------------------------------------------

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
        return ("$Revision: 5301 $,$Date: 2009-10-28 14:36:02 +0900 (水, 28 10 2009) $");
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
    public Id35Process()
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
    public Id35Process(String agcNo)
    {
        super(agcNo);
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 搬送データ削除報告の処理を行います。
     * 受信した搬送Keyがダミー到着データの場合、ステーションのダミー到着データをクリアします。
     * 通常の搬送Keyであれば、該当するCarryInfomationを元に、搬送データ、在庫データを削除します。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processes the carry data deletion report.
     * If the Carry Key received was for dummy arrival data, clear the dummy arrival data in the station.
     * If it is a normal Carry Key, delete the carry data and stock data according to the corresponding CarryInfomation.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    @SuppressWarnings("cast")
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        As21Id35 id35dt = new As21Id35(rdt);

        CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection(), getClass());

        //<jp> 6022032=搬送Data削除報告を受信しました。mckey={0}</jp>
        //<en> 6022032=Transfer data deletion report is received. mckey={0}</en>
        Object[] tObj = new Object[1];
        tObj[0] = id35dt.getMcKey();
        RmiMsgLogClient.write(6022032, LogMessage.F_NOTICE, getClass().getName(), tObj);

        //<jp> MCkeyをチェック</jp>
        //<en> Checks the MCkey.</en>
        if (WmsParam.DUMMY_MCKEY.equals(id35dt.getMcKey()))
        {
            //<jp> ダミー到着の場合、搬送先ステーションの到着データをクリアする。</jp>
            //<en> If it was a dummy arrival, clear the arrival data of receiving station.</en>
            StationOperator sOpe = new StationOperator(getConnection(), id35dt.getReceivingStationNo());
            //            sOpe.dropArrival();
            // DFKLOOK ADD start 3.4
            // Arrival削除
            ArrivalHandler arrivalHand = new ArrivalHandler(getConnection());
            ArrivalSearchKey arrivalSkey = new ArrivalSearchKey();
            // 検索キーセット
            arrivalSkey.setCarryKey(WmsParam.DUMMY_MCKEY);
            arrivalSkey.setStationNo(sOpe.getStation().getStationNo());
            arrivalSkey.setArrivalDateOrder(true);
            if (arrivalHand.count(arrivalSkey) > 0)
            {
                Arrival[] arrival = (Arrival[])arrivalHand.find(arrivalSkey);
                // 削除
                arrivalHand.drop(arrival[0]);
            }
            // DFKLOOK ADD end 3.4


        }
        else
        {
            //<jp>MCKEYをもとにCarryInfo検索</jp>
            //<en>Searches the CarryInfo based on MCKEY.</en>
            CarryInfoSearchKey cskey = new CarryInfoSearchKey();
            cskey.setCarryKey(id35dt.getMcKey());
            CarryInfoHandler cih = new CarryInfoHandler(getConnection());
            CarryInfo[] earr = (CarryInfo[])cih.find(cskey);
            //<jp> 該当データなし</jp>
            //<en> There is no corresponding data.</en>
            if (earr.length == 0)
            {
                //<jp> 6026038=指定されたmckeyの搬送データが存在しません。mckey={0}</jp>
                //<en> 6026038=Transfer data for the designated MCKey does not exist. mckey={0}</en>
                tObj = new Object[1];
                tObj[0] = id35dt.getMcKey();
                RmiMsgLogClient.write(6026038, LogMessage.F_ERROR, getClass().getName(), tObj);
                return;
            }

            if (earr[0] instanceof CarryInfo)
            {
                CarryInfo ci = (CarryInfo)earr[0];
                if (CarryInfo.CMD_STATUS_ARRIVAL.equals(ci.getCmdStatus()))
                {
                    Station curStation = StationFactory.makeStation(getConnection(), ci.getSourceStationNo());

                    if (curStation.isArrivalCheck())
                    {
                        //<jp> 到着報告有りのステーションに到着しているパレットであれば、</jp>
                        //<jp> 到着データをクリアする。</jp>
                        //<en> If the pallet arrived at the station where arrival reports is operated,</en>
                        //<en> the arrival data should be cleared.</en>
//                        StationOperator sOpe = new StationOperator(getConnection(), ci.getSourceStationNo());
//                        sOpe.dropArrival();
                        
                        // DFKLOOK ADD start 3.4
                        // Arrival削除
                        ArrivalHandler arrivalHand = new ArrivalHandler(getConnection());
                        ArrivalSearchKey arrivalSkey = new ArrivalSearchKey();
                        // 検索キーセット
                        arrivalSkey.setCarryKey(ci.getCarryKey());
                        arrivalSkey.setStationNo(ci.getSourceStationNo());
                        arrivalSkey.setArrivalDateOrder(true);
                        if (arrivalHand.count(arrivalSkey) > 0)
                        {
                            Arrival[] arrival = (Arrival[])arrivalHand.find(arrivalSkey);
                            // 削除
                            arrivalHand.drop(arrival[0]);
                        }
                        // DFKLOOK ADD end 3.4
                    }
                }

                //<jp> 入庫、直行</jp>
                //<en> Storage, direct travel</en>
                if (CarryInfo.CARRY_FLAG_STORAGE.equals(ci.getCarryFlag())
                        || CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
                {
                    //<jp> 削除</jp>
                    //<en> Deletes</en>

                    //<jp> 引当、開始、応答待ちは実績を作成しない</jp>
                    //<en> Allocated, start, wait for reply</en>
                    if (CarryInfo.CMD_STATUS_ALLOCATION.equals(ci.getCmdStatus())
                            || CarryInfo.CMD_STATUS_START.equals(ci.getCmdStatus())
                            || CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(ci.getCmdStatus()))
                    {
                        //<jp> 削除</jp>
                        //<en> Deletes</en>
                        // 6022033=搬送データを削除しました。mckey={0}
                        carryCompOpe.drop(ci, false);
                        tObj = new Object[1];
                        tObj[0] = id35dt.getMcKey();
                        RmiMsgLogClient.write(6022033, LogMessage.F_NOTICE, getClass().getName(), tObj);
                    }
                    //<jp> その他は実績ありで、完了する
                    //<en> With any other cases, all carry works should be deleted. (with data)</en>
                    else
                    {
                        //<jp> 削除</jp>
                        //<en> Deletes</en>
                        // 6022033=搬送データを削除しました。mckey={0}
                        carryCompOpe.drop(ci, true);
                        tObj = new Object[1];
                        tObj[0] = id35dt.getMcKey();
                        RmiMsgLogClient.write(6022033, LogMessage.F_NOTICE, getClass().getName(), tObj);
                    }
                }
                //<jp> 出庫、棚間移動</jp>
                //<en> Retrieval, location to location move</en>
                else if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(ci.getCarryFlag())
                        || CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
                {
                    //<jp> 出庫</jp>
                    //<en> Retrieval</en>
                    if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(ci.getCarryFlag()))
                    {
                        //<jp> 削除</jp>
                        //<en> Deletes</en>
                        // 6022033=搬送データを削除しました。mckey={0}
                        carryCompOpe.drop(ci, InOutResult.WORK_TYPE_CARRYINFODELETE, true, CARRY_COMPLETE.NORMAL);
                        tObj = new Object[1];
                        tObj[0] = id35dt.getMcKey();
                        RmiMsgLogClient.write(6022033, LogMessage.F_NOTICE, getClass().getName(), tObj);
                    }
                    else
                    {
                        //<jp> 削除</jp>
                        //<en> Deletes</en>
                        // 6022033=搬送データを削除しました。mckey={0}
                        carryCompOpe.drop(ci, true);
                        tObj = new Object[1];
                        tObj[0] = id35dt.getMcKey();
                        RmiMsgLogClient.write(6022033, LogMessage.F_NOTICE, getClass().getName(), tObj);
                    }
                }
                //<jp> 不正な搬送区分</jp>
                //<en> Invalid carry classification</en>
                else
                {
                    tObj = new Object[3];
                    tObj[0] = "CarryInformation";
                    tObj[1] = "CarryKind";
                    tObj[2] = ci.getCarryFlag();
                    //<jp> 6024018=取得したインスタンス{0}の属性{1}の値が不正です。{1}={2}</jp>
                    //<en> 6024018=Attribute {1} value of acquired instance {0} is invalid. {1}={2}</en>
                    RmiMsgLogClient.write(6024018, LogMessage.F_WARN, getClass().getName(), tObj);
                    throw new InvalidStatusException(WmsMessageFormatter.format(6024018, tObj));
                }
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
        }
    }

    // Private methods -----------------------------------------------
}
//end of class

