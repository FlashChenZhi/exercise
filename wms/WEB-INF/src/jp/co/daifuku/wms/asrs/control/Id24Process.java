// $Id: Id24Process.java 4642 2009-07-10 01:16:36Z okamura $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id24;
import jp.co.daifuku.wms.asrs.control.CarryCompleteOperator.CARRY_COMPLETE;
import jp.co.daifuku.wms.asrs.dasch.AsWorkMntDASCHOperator;
import jp.co.daifuku.wms.asrs.schedule.AsrsInParameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalHandler;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Com_terminal;
import jp.co.daifuku.wms.base.entity.InOutResult;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.retrieval.allocate.ReleaseAllocateOperator;

/**<jp>
 * 搬送DataCancel応答を処理するクラスです。CANCEL結果を元に対象となるCarryInformationの更新を行います。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/01/09</TD><TD>MIYASHITA</TD><TD>応答が正常完了</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4642 $, $Date: 2009-07-10 10:16:36 +0900 (金, 10 7 2009) $
 * @author  $Author: okamura $
 </jp>*/
/**<en>
 * This class processes the response to CarryDataCancel. Based on the result of CANCEL, it updates the target CarryInformation.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/01/09</TD><TD>MIYASHITA</TD><TD>Response is completed successfully.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4642 $, $Date: 2009-07-10 10:16:36 +0900 (金, 10 7 2009) $
 * @author  $Author: okamura $
 </en>*/
public class Id24Process
        extends IdProcess
{
    // Class fields --------------------------------------------------

    /**
     * オペレーションロギング用DS番号
     */
    private static final String DS_NUMBER = "000305";

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
        return ("$Revision: 4642 $,$Date: 2009-07-10 10:16:36 +0900 (金, 10 7 2009) $");
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
    public Id24Process()
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
    public Id24Process(String agcNumber)
    {
        super(agcNumber);
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 搬送DataCancel応答の処理を行います。
     * 受信した電文のMC Keyから、<code>CarryInformation</code>を検索し、搬送DataCancel応答の応答区分と
     * <code>CarryInformation</code>のキャンセル要求区分をもとにキャンセル処理を行ないます。
     * 1.応答区分が正常または該当搬送データ無しの場合
     *   CarryInformationのキャンセル要求区分をもとに処理を分岐する。
     *   ・Data Cancel
     *     搬送Dataを未指示の状態に戻す。
     *   ・引当解除
     *     搬送Dataを削除処理を行なう。
     *   ・トラッキング削除
     *     搬送Dataと在庫Dataを削除する。
     * 2.応答区分が搬送開始済みのため、Cancel不可の場合
     *   注意メッセージをログに出力し、何も処理を行なわない。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processing the reply to Carry Data Cancel.
     * Search <code>CarryInformation</code> based on the MC Key of the received communication message,
     * then according to the classification of reply to CarryDataCancel and the cancel request classsification
     * of <code>CarryInformation</code>, process the cancelation.
     * 1. In case the classification of reply is normal, or if there was no corrensponding carry data,
     *    It branches the process according to the classification of cancel request for CarryInformation.
     *    - Data Cancel
     *      Reset the status of Carry Data to 'unprocessed'.
     *    - Allocation release
     *      Process the deletion of CarryData.
     *    - Deletion of track data
     *      Delete the Carry Data and the stock data.
     * 2. Not able to Cancel due to the classification 'Carry started'.
     *    Output the caution message, nothing else will be done.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        Object[] tObj = new Object[1];
        CarryInfoHandler cih = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey cskey = new CarryInfoSearchKey();
        CarryInfoAlterKey cakey = new CarryInfoAlterKey();

        CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection(), getClass());

        //<jp> 受信テキスト分解</jp>
        //<en> Decomposition of the received text.</en>
        As21Id24 id24dt = new As21Id24(rdt);

        // 正常完了又は該当搬送データ無しの場合、削除処理を行う
        if (As21Id24.RESULT_COMP_NORMAL.equals(id24dt.getCancellationResults())
                || As21Id24.RESULT_NOT_DATA.equals(id24dt.getCancellationResults()))
        {
            tObj[0] = id24dt.getMcKey();
            if (As21Id24.RESULT_COMP_NORMAL.equals(id24dt.getCancellationResults()))
            {
                //<jp> 6022015=受信テキストCancel結果->正常完了。mckey={0}</jp>
                //<en> 6022015=Result of received text cancel request -> Normal end. mckey={0}</en>
                RmiMsgLogClient.write(6022015, LogMessage.F_NOTICE, getClass().getName(), tObj);
            }
            else
            {
                //<jp> 6022017=受信テキストCancel結果->該当Data無し。mckey={0}</jp>
                //<en> 6022017=Result of received text cancel request -> There is no data. mckey={0}</en>
                RmiMsgLogClient.write(6022017, LogMessage.F_NOTICE, getClass().getName(), tObj);
            }

            //<jp> MC keyを検索条件として設定し、CarryInformationの検索を行なう。</jp>
            //<en> By setting MC key as a search condition, searches the CarryInformation.</en>
            cskey.setCarryKey(id24dt.getMcKey());
            //<jp> 該当のCarryInfoを取得</jp>
            //<en> Obtains corresponding CarryInfo.</en>
            Entity[] earr = cih.find(cskey);
            //<jp> 該当データなし</jp>
            //<en> There is no corresponding data.</en>
            if (earr.length == 0)
            {
                tObj = new Object[1];
                tObj[0] = id24dt.getMcKey();
                //<jp> 6026038=指定されたmckeyの搬送データが存在しません。mckey={0}</jp>
                //<en> 6026038=Transfer data for the designated MCKey does not exist. mckey={0}</en>
                RmiMsgLogClient.write(6026038, LogMessage.F_ERROR, getClass().getName(), tObj);
                //<jp> 該当データ無しの場合、例外扱いにせず、そのまま本ID処理を終了する。</jp>
                //<en> If there is no corresponding data, no handling of exception is done but terminates this ID processing jsut as it is. </en>
                return;
            }

            if (earr[0] instanceof CarryInfo)
            {
                //<jp> 搬送情報を格納</jp>
                //<en> Stores the carry information.</en>
                CarryInfo ci = (CarryInfo)earr[0];

                // PART11用 DfkUserInfoを作成する
                DfkUserInfo userInfo = new DfkUserInfo();
                //DS番号
                userInfo.setDsNumber(DS_NUMBER);
                // ユーザID
                userInfo.setUserId(WmsParam.SYS_USER_ID);
                // ユーザ名称
                userInfo.setUserName(WmsParam.SYS_USER_NAME);
                // 端末No
                userInfo.setTerminalNumber(ci.getMaintenanceTerminal());
                // 端末名称
                userInfo.setTerminalName(WmsParam.SYS_TERMINAL_NAME);
                // 端末IPアドレス
                userInfo.setTerminalAddress(WmsParam.SYS_IP_ADDRESS);
                // プリンタ名を取得
                Com_terminalHandler termHand = new Com_terminalHandler(getConnection());
                Com_terminalSearchKey termSkey = new Com_terminalSearchKey();
                termSkey.setTerminalnumber(ci.getMaintenanceTerminal());
                Com_terminal comEnty = (Com_terminal)termHand.findPrimary(termSkey);

                userInfo.setTerminalPrinterName(comEnty.getPrintername());

                AsrsInParameter param = new AsrsInParameter(new WmsUserInfo(userInfo));
                param.setCarryKey(ci.getCarryKey());
                param.setTerminalNo(ci.getMaintenanceTerminal());


                //<jp> キャンセル要求区分</jp>
                //<en> cancel request classsification</en>
                //<jp> 未指示に戻す</jp>
                //<en> Cancel instructions</en>
                if (CarryInfo.CANCEL_REQUEST_CANCEL.equals(ci.getCancelRequest()))
                {
                    param.setProcessStatus(AsrsInParameter.PROCESS_STATUS_UNINSTRUCT);
                }
                //<jp> 引当を取り消す</jp>
                //<en> Cancel allocate</en>
                else if (CarryInfo.CANCEL_REQUEST_RELEASE.equals(ci.getCancelRequest()))
                {
                    param.setProcessStatus(AsrsInParameter.PROCESS_STATUS_CANCEL_ALLOCATE);

                }
                //<jp> トラッキング削除</jp>
                //<en> Delete track data</en>
                else if (CarryInfo.CANCEL_REQUEST_DROP.equals(ci.getCancelRequest()))
                {
                    param.setProcessStatus(AsrsInParameter.PROCESS_STATUS_TRACKING_DELETE);

                }
                // メンテナンスリストを発行する
                AsWorkMntDASCHOperator asDaschOpe = new AsWorkMntDASCHOperator(this.getClass());
                asDaschOpe.print(Locale.getDefault(), userInfo, param, getConnection());

                //<jp> 搬送区分を確認</jp>
                //<en> Checks the carry classification.</en>

                //<jp> 入庫、直行</jp>
                //<en> Storage, direct travel</en>
                if (CarryInfo.CARRY_FLAG_STORAGE.equals(ci.getCarryFlag())
                        || CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
                {
                    //<jp> キャンセル要求区分により処理を判断</jp>
                    //<en> Determines the process according to the classification of cancel request.</en>
                    if (CarryInfo.CANCEL_REQUEST_CANCEL.equals(ci.getCancelRequest()))
                    {
                        //<jp> 入庫データキャンセル処理</jp>
                        //<en> Canceling the storage data</en>
                        carryCompOpe.cancelStorage(ci);
                        //<jp> キャンセル要求フラグを未要求にする。</jp>
                        //<en> Switching the cancel request flag to 'not requested'.</en>
                        cakey.updateCancelRequest(CarryInfo.CANCEL_REQUEST_UNDEMAND);
                        cakey.updateCancelRequestDate(ci.getCancelRequestDate());
                        cakey.setCarryKey(ci.getCarryKey());
                        cakey.updateLastUpdatePname(getClassName());
                        cih.modify(cakey);
                    }
                    else
                    {
                        //<jp> 削除</jp>
                        //<en> Deletes</en>

                        //<jp> 引当、開始、応答待ちは実績を作成しない</jp>
                        //<en> Allocated, start, wait for reply</en>
                        if (CarryInfo.CMD_STATUS_ALLOCATION.equals(ci.getCmdStatus())
                                || CarryInfo.CMD_STATUS_START.equals(ci.getCmdStatus())
                                || CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(ci.getCmdStatus()))
                        {
                            carryCompOpe.drop(ci, false);
                        }
                        //<jp> その他は実績ありで、完了する
                        //<en> With any other cases, all carry works should be deleted. (with data)</en>
                        else
                        {
                            carryCompOpe.drop(ci, true);
                        }

                    }
                }
                //<jp> 出庫、棚間移動</jp>
                //<en> Retrieval, location to location move</en>
                else if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(ci.getCarryFlag())
                        || CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
                {
                    //<jp> キャンセル要求区分により処理を判断</jp>
                    //<en> Determines the process according to the classification of cancel request.</en>
                    if (CarryInfo.CANCEL_REQUEST_CANCEL.equals(ci.getCancelRequest()))
                    {
                        //<jp> 出庫データキャンセル処理</jp>
                        //<en> Canceling the retrieval data</en>
                        carryCompOpe.cancelRetrieval(ci);
                        //<jp> キャンセル要求フラグを未要求にする。</jp>
                        //<en> Switching the cancel request flag to 'not requested'.</en>
                        cakey.updateCancelRequest(CarryInfo.CANCEL_REQUEST_UNDEMAND);
                        cakey.updateCancelRequestDate(ci.getCancelRequestDate());
                        cakey.setCarryKey(ci.getCarryKey());
                        cakey.updateLastUpdatePname(getClassName());
                        cih.modify(cakey);
                    }
                    else if (CarryInfo.CANCEL_REQUEST_RELEASE.equals(ci.getCancelRequest()))
                    {
                        //<jp> 引当解除</jp>
                        //<en> Release of allocation</en>

                        //<jp> 引当、開始、応答待ち、指示済みの場合に引当解除を行う</jp>
                        //<en> Allocated, start, wait for reply, instruction provided</en>
                        if (CarryInfo.CMD_STATUS_ALLOCATION.equals(ci.getCmdStatus())
                                || CarryInfo.CMD_STATUS_START.equals(ci.getCmdStatus())
                                || CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(ci.getCmdStatus())
                                || CarryInfo.CMD_STATUS_INSTRUCTION.equals(ci.getCmdStatus()))
                        {
                            // DFKLOOK:ここから修正
                            // 引当解除
                            //ReleaseAllocateOperator releaseOpe =
                            //        new ReleaseAllocateOperator(getConnection(), getClass());
                            //releaseOpe.allocateClearOfCarryKey(ci);

                            // '09/06/17 積み増し入庫データが作業メンテナンスで「引当取り消し」した時の対応
                            if (CarryInfo.RETRIEVAL_DETAIL_ADD_STORING.equals(ci.getRetrievalDetail()))
                            {
                                // 積み増しの引当解除設定時は、積み増し設定した予定在庫が実在庫に
                                // なってないので実績を作成しないを指定する。
                                carryCompOpe.drop(ci, false);
                            }
                            else
                            {
                                // 引当解除
                                ReleaseAllocateOperator releaseOpe =
                                        new ReleaseAllocateOperator(getConnection(), getClass());
                                releaseOpe.allocateClearOfCarryKey(ci);
                            }
                            // DFKLOOK:ここまで修正
                        }
                        else
                        {
                            tObj[0] = new Integer(ci.getCmdStatus());
                            //<jp> 6026068=不正な搬送状態のため引当て解除できません。搬送状態:{0}</jp>
                            //<en> 6026068=Invalid transfer status. Unable to clear the allocation. Transfer status={0}</en>
                            RmiMsgLogClient.write(6026068, LogMessage.F_WARN, getClass().getName(), tObj);
                        }
                    }
                    else
                    {
                        if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(ci.getCarryFlag()))
                        {
                            //<jp> 削除</jp>
                            //<en> Deletes</en>
                            carryCompOpe.drop(ci, InOutResult.WORK_TYPE_CARRYINFODELETE, true, CARRY_COMPLETE.NORMAL);
                        }
                        else
                        {
                            carryCompOpe.drop(ci, true);
                        }
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
                tObj[0] = "CarryInformation";
                //<jp> 6006008={0}以外のオブジェクトが返されました。</jp>
                //<en> 6006008=Object other than {0} was returned.</en>
                RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, getClass().getName(), tObj);
                throw new InvalidProtocolException(WmsMessageFormatter.format(6006008, tObj));
            }
        }
        //<jp> 該当データは既に搬送開始済みのため、Cancel不可</jp>
        //<en> Not able to Cancel as the Carry of corresponding data already started.</en>
        else if (As21Id24.RESULT_NOT_CANCEL.equals(id24dt.getCancellationResults()))
        {
            tObj[0] = id24dt.getMcKey();
            //<jp> 6022016=受信テキストCancel結果->該当Dataはすでに搬送済みのため、Cancel不可。mckey={0}</jp>
            //<en> 6022016=Result of received text cancel request -> Unable to cancel. Data has been transferred. mckey={0}</en>
            RmiMsgLogClient.write(6022016, LogMessage.F_NOTICE, getClass().getName(), tObj);
        }
        //<jp> Cancel結果不明</jp>
        //<en> Reuslt of Cancel unknown.</en>
        else
        {
            tObj[0] = id24dt.getCancellationResults();
            //<jp> 6022018=受信テキストCancel結果が不正です。キャンセル結果={0}</jp>
            //<en> 6022018=Result of received text cancel request is invalid. Cancel result={0}</en>
            RmiMsgLogClient.write(6022018, LogMessage.F_NOTICE, getClass().getName(), tObj);
        }
    }

    // Private methods -----------------------------------------------

}
//end of class

