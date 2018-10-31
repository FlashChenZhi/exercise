// $Id: Id32Process.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id32;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Pallet;

/**<jp>
 * 出庫指示応答を処理するクラスです。
 * 応答区分をもとに該当するCarryInformationの更新を行います。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2006/11/24</TD><TD>Y.Kawai</TD><TD>eWareNavi対応</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class processes the response to retrieval instruction. It updates teh corresponding CarryInformation
 * according to the classification of response. 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class Id32Process
        extends IdProcess
{
    /** <code>PROC_ID</code> */
    private static final String PROC_ID = "32";

    // Class fields --------------------------------------------------
    //<jp> 応答区分(正常)</jp>
    //<en> Classificaiotn of response (normal)</en>
    private static final int RESPONSE_NORMAL = 0;

    //<jp> 応答区分(多重設定)</jp>
    //<en> Classificaiotn of response (multiple set)</en>
    private static final int MULTI_SETTING = 3;

    //<jp> 応答区分(OFFLINE)</jp>
    //<en> Classificaiotn of response (off-line)</en>
    private static final int OFFLINE = 6;

    //<jp> 応答区分(BUFFER FULL)</jp>
    //<en> Classificaiotn of response (BUFFER FULL)</en>
    private static final int BUFFER_BULL = 11;

    //<jp> 応答区分(DATA ERROR)</jp>
    //<en> Classificaiotn of response (DATA ERROR)</en>
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
    public Id32Process()
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
    public Id32Process(String agcNo)
    {
        super(agcNo);
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 出庫指示応答の処理を行います。
     * 受信した電文のMC Keyから、<code>CarryInformation</code>を検索し、出庫指示応答の応答区分を
     * もとに搬送状態の更新処理を行ないます。
     * ただし、トランザクションのコミット・ロールバックは行っていませんので、呼び出し元で行う必要があります。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processing the response to retrieval instruction.
     * Search <code>CarryInformation</code> based on the MC Key of the received communication message,
     * then according to the classification of reply to the RetrievalInstruction, alter the carry status.
     * However the call source needs to commit or rollback the transaction, as they are not done here.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    @SuppressWarnings("cast")
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        CarryInfo ci;
        As21Id32 id32dt = new As21Id32(rdt);
        CarryInfoHandler cih = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey cskey = new CarryInfoSearchKey();
        CarryInfoAlterKey cakey = new CarryInfoAlterKey();

        String[] ckey = id32dt.getMcKey();
        int[] responce = id32dt.getResponseData();

        //<jp> 受信したテキストの件数分処理（標準では最大2件）</jp>
        //<en> Processes as much text data received (max. 2 in standard)</en>
        for (int i = 0; i < ckey.length; i++)
        {
            //<jp> MC keyを検索条件として設定</jp>
            //<en> Sets MC key as a search condition.</en>
            cskey.clear();
            cskey.setCarryKey(ckey[i]);

            //<jp> 該当のCarryInfoを取得</jp>
            //<en> Retrieves corresponding CarryInfo.</en>
            CarryInfo[] earr = (CarryInfo[])cih.find(cskey);

            //<jp> 該当データなし</jp>
            //<en> There is no correspondig data.</en>
            if (earr.length == 0)
            {
                //<jp> 6026038=指定されたmckeyの搬送データが存在しません。mckey={0}</jp>
                //<en> 6026038=Transfer data for the designated MCKey does not exist. mckey={0}</en>
                Object[] tObj = {
                    ckey[i],
                };
                RmiMsgLogClient.write(6026038, LogMessage.F_ERROR, getClass().getName(), tObj);
                continue;
            }

            if (earr[0] instanceof CarryInfo)
            {
                ci = (CarryInfo)earr[0];
                PalletAlterKey pakey = new PalletAlterKey();
                PalletHandler palletHandle = new PalletHandler(getConnection());

                //<jp> 応答区分を確認</jp>
                //<en> Checks the classification of response.</en>
                switch (responce[i])
                {
                    //<jp> 正常応答、搬送状態を「指示済み」に変更</jp>
                    //<en> Normal response and carry status to be altered to 'instruction given'.</en>
                    case RESPONSE_NORMAL:
                    {
                        //<jp> 搬送状態が「応答待ち」か確認</jp>
                        //<en> If carry status is 'wait to reply' or to be checked,</en>
                        if (CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(ci.getCmdStatus()))
                        {
                            cakey.clear();
                            cakey.updateCmdStatus(CarryInfo.CMD_STATUS_INSTRUCTION);
                            cakey.setCarryKey(ci.getCarryKey());
                            cakey.updateErrorCode(String.valueOf(RESPONSE_NORMAL));
                            cakey.updateLastUpdatePname(getClassName());
                            cih.modify(cakey);
                        }
                        else
                        {
                            //<jp> 搬送状態が応答待ちでない場合は、警告メッセージを出力する。</jp>
                            //<jp> 搬送データは処理しない</jp>
                            //<en> If carry status is anything other than 'wait for response', it output the </en>
                            //<en> warning message. There is no processing of carry data.</en>
                            //<jp> 6024015=搬送状態が応答待ちではない搬送データに対して出庫指示応答を受信しました。受信テキストは無視します。 mckey={0} （警告）</jp>
                            //<en> 6024015=Unexpected reply to picking instruction. No action for the received text. mckey={0}</en>
                            {
                                Object[] tObj = {
                                    id32dt.getMcKey(),
                                };
                                RmiMsgLogClient.write(6024015, LogMessage.F_WARN, getClass().getName(), tObj);
                            }
                        }
                        break;
                    }

                        //<jp> 多重設定、ログ出力のみ</jp>
                        //<en> Outputting log for Multiple set</en>
                    case MULTI_SETTING:
                    {
                        //<jp> 6024006=搬送指示応答で多重引当を受信しました。 mckey={0}</jp>
                        //<en> 6024006=Multiple allocation was received in response to transfer instruction. mckey={0}</en>
                        Object[] tObj = {
                            ckey[i],
                        };
                        RmiMsgLogClient.write(6024006, LogMessage.F_WARN, getClass().getName(), tObj);
                        break;
                    }

                        //<jp> オフライン中</jp>
                        //<en> Off-line</en>
                    case OFFLINE:
                    {
                        //<jp> 6024008=搬送指示応答でオフライン中を受信しました。 mckey={0}</jp>
                        //<en> 6024008=Offline was received in response to transfer instruction. mckey={0}</en>

                        Object[] tObj = {
                            ckey[i],
                        };
                        RmiMsgLogClient.write(6024008, LogMessage.F_WARN, getClass().getName(), tObj);

                        //<jp> 搬送状態を開始に戻す</jp>
                        //<en> Reset the carry status to 'start'.</en>
                        cakey.clear();
                        cakey.updateCmdStatus(CarryInfo.CMD_STATUS_START);
                        cakey.setCarryKey(ci.getCarryKey());
                        cakey.updateErrorCode(String.valueOf(OFFLINE));
                        cakey.updateLastUpdatePname(getClassName());
                        cih.modify(cakey);

                        //<jp> パレットの状態を出庫予約にする。</jp>
                        //<en> Set the status of pallet to 'reserved for retrieval'</en>
                        pakey.clear();
                        pakey.setPalletId(ci.getPalletId());
                        pakey.updateStatusFlag(Pallet.PALLET_STATUS_RETRIEVAL_PLAN);
                        pakey.updateLastUpdatePname(getClassName());
                        palletHandle.modify(pakey);

                        //<jp> GroupControlerの状態を「オフライン」に変更</jp>
                        //<en> Modifies the status of GroupControler to off-line.</en>
                        GroupController tgc = GroupController.getInstance(getConnection(), getAgcNo());
                        tgc.setStatus(GroupController.STATUS_OFFLINE);
                        break;
                    }
                        //<jp> バッファフル</jp>
                        //<en> Buffer full</en>
                    case BUFFER_BULL:
                    {
                        //<jp> 6024010=搬送指示応答でバッファフルを受信しました。 mckey={0}</jp>
                        //<en> 6024010=Buffer full was received in response to transfer instruction. mckey={0}</en>

                        Object[] tObj = {
                            ckey[i],
                        };
                        RmiMsgLogClient.write(6024010, LogMessage.F_WARN, getClass().getName(), tObj);

                        //<jp> 搬送状態を開始に戻す</jp>
                        //<en> Reset the carry status to 'start'.</en>
                        cakey.clear();
                        cakey.updateCmdStatus(CarryInfo.CMD_STATUS_START);
                        cakey.setCarryKey(ci.getCarryKey());
                        cakey.updateErrorCode(String.valueOf(BUFFER_BULL));
                        cakey.updateLastUpdatePname(getClassName());
                        cih.modify(cakey);

                        //<jp> パレットの状態を出庫予約にする。</jp>
                        //<en> Set the status of pallet to 'reserved for retrieval'</en>
                        pakey.clear();
                        pakey.updateStatusFlag(Pallet.PALLET_STATUS_RETRIEVAL_PLAN);
                        pakey.setPalletId(ci.getPalletId());
                        pakey.updateLastUpdatePname(getClassName());
                        palletHandle.modify(pakey);
                        break;
                    }
                        //<jp> データエラー</jp>
                        //<en> Data error</en>
                    case DATA_ERROR:
                    {
                        //<jp> 6024011=搬送指示応答でデータエラーを受信しました。 mckey={0}</jp>
                        //<en> 6024011=Data error was received in response to transfer instruction. mckey={0}</en>

                        Object[] tObj = {
                            ckey[i],
                        };
                        RmiMsgLogClient.write(6024011, LogMessage.F_WARN, getClass().getName(), tObj);

                        //<jp> 搬送状態を「異常」に変更</jp>
                        //<en> Modifies the carry status to 'error'.</en>
                        cakey.clear();
                        cakey.updateCmdStatus(CarryInfo.CMD_STATUS_ERROR);
                        cakey.setCarryKey(ci.getCarryKey());
                        cakey.updateErrorCode(String.valueOf(DATA_ERROR));
                        cakey.updateLastUpdatePname(getClassName());
                        cih.modify(cakey);

                        //<jp> 搬送先ステーションを中断中にする。</jp>
                        //<en> Alther the status of receiving station to 'suspended'.</en>
                        StationOperator sOpe = new StationOperator(getConnection(), ci.getDestStationNo());
                        sOpe.alterSuspend(true);
                        break;
                    }
                    default:
                    {
                        //<jp> 6026037=テキストID{0}の応答区分が不正です。受信応答区分={1}</jp>
                        //<en> 6026037=Response category for text ID {0} is invalid.  Receive response category={1}</en>
                        Object[] tObj = {
                            PROC_ID,
                            String.valueOf(responce[i]),
                        };
                        RmiMsgLogClient.write(6026037, LogMessage.F_WARN, getClass().getName(), tObj);
                        throw new InvalidDefineException(WmsMessageFormatter.format(6026037, tObj));
                    }
                }
            }
        }
    }
    // Private methods -----------------------------------------------

}
//end of class

