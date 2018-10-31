// $Id: Id28Process.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id28;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;

/**<jp>
 * 搬送先変更指示応答を処理するクラスです。
 * 応答区分を元に処理をおこないます。
 * 正常受付の場合、搬送先変更指示が正常に受け付けられたものとし、処理は行いません。
 * それ以外の報告の場合、CarryInformationの状態を異常に変更し、搬送データを無効にします。
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
 * This class processes the response to the instruction of destination change.
 * It handles the process based on the classsification of the response. 
 * If it is normaly received, it regards that the instruction of destination change has been
 * accepted normally, therefore it will not carry out any process.
 * For any other reports, it should alter tje status of CarryInformation to error and invalidates the Carry data.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class Id28Process
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
    public Id28Process()
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
    public Id28Process(String agcNo)
    {
        super(agcNo);
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 搬送先変更指示応答の処理を行います。
     * 受信した電文のMC Keyから、<code>CarryInformation</code>を検索し、異常応答の場合搬送状態を 異常 に変更します。
     * ただし、トランザクションのコミット・ロールバックは行っていませんので、呼び出し元で行う必要があります。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processing the response to instrruction of destination change.
     * Based on MC Key of received communiacation message, search <code>CarryInformation</code>
     * and alter the carry status 'error' in case the error response was received.
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
        As21Id28 id28dt = new As21Id28(rdt);
        CarryInfoHandler cih = new CarryInfoHandler(getConnection());

        //<jp> 応答区分を取得</jp>
        //<en> Obtains the response classificaiton.</en>
        String response = id28dt.getResponseClassification();

        //<jp> エラー報告、搬送状態を「異常」に変更</jp>
        //<en> Error report, alter hte carry status to 'error'.</en>
        if (!As21Id28.CLASS_NORMAL_RECEIVE.equals(response))
        {
            //<jp> MC keyを検索条件として設定</jp>
            //<en> Sets MC key as a search condition.</en>
            String mckey = id28dt.getMcKey();
            CarryInfoSearchKey cskey = new CarryInfoSearchKey();
            cskey.setCarryKey(mckey);

            //<jp> 該当のCarryInfoを取得</jp>
            //<en> Obtains the corrensponding CarryInfo.</en>
            CarryInfo[] earr = (CarryInfo[])cih.find(cskey);

            //<jp> 該当データなし</jp>
            //<en> There is no corresponding data.</en>
            if (earr.length == 0)
            {
                Object[] tObj = {
                    mckey,
                };
                //<jp> 6026038=指定されたmckeyの搬送データが存在しません。mckey={0}</jp>
                //<en> 6026038=Transfer data for the designated MCKey does not exist. mckey={0}</en>
                RmiMsgLogClient.write(6026038, LogMessage.F_ERROR, getClass().getName(), tObj);
                return;
            }

            if (earr[0] instanceof CarryInfo)
            {
                //<jp> 搬送先変更指示応答でNG応答を受信した場合、搬送状態を異常に更新する。</jp>
                //<en> In case the 'unacceptable' response to teh destination change instruction was </en>
                //<en> received, the carry status needs to be updated 'error'.</en>
                ci = (CarryInfo)earr[0];

                CarryInfoAlterKey cakey = new CarryInfoAlterKey();
                cakey.updateCmdStatus(CarryInfo.CMD_STATUS_ERROR);
                cakey.setCarryKey(ci.getCarryKey());
                cakey.updateLastUpdatePname(getClassName());
                cih.modify(cakey);

                Object[] tObj = {
                    response,
                    mckey,
                };
                //<jp> 6024013=搬送先変更指示応答でNG応答を受信しました。応答区分={0} mckey={1}</jp>
                //<en> 6024013=Carry destination change command was not accepted. Response category={0} mckey={1}</en>
                RmiMsgLogClient.write(6024013, LogMessage.F_WARN, getClass().getName(), tObj);
            }
            else
            {
                Object[] tObj = {
                    "CarryInformation",
                };
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

