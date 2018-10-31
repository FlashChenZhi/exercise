// $Id: Id31Process.java 8056 2013-05-24 10:17:47Z kishimoto $
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
import jp.co.daifuku.wms.asrs.communication.as21.As21Id31;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;

/**<jp>
 * 代替棚指示応答を処理するクラスです。応答区分を元に処理をおこないます。
 * 正常受付の場合、搬送先変更指示が正常に受け付けられたものとし、
 * 処理は行いません。
 * それ以外の報告の場合、CarryInformationの状態を異常に変更し、搬送データを無効にします。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/11/24</TD><TD>Y.Kawai</TD><TD>eWareNavi対応</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8056 $, $Date: 2013-05-24 19:17:47 +0900 (金, 24 5 2013) $
 * @author  $Author: kishimoto $
 </jp>*/
/**<en>
 * This class processes the response to instruction of alternative location. It processes based on the classificiaotn of reply.
 * When normally accepted, it regards that instruction of destination change has been accepted
 * normally and therefore there will be no process.
 * If any other reports accepted, it alters the status of CarryInformation and invalidates the Carry Data.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8056 $, $Date: 2013-05-24 19:17:47 +0900 (金, 24 5 2013) $
 * @author  $Author: kishimoto $
 </en>*/
public class Id31Process
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
        return ("$Revision: 8056 $,$Date: 2013-05-24 19:17:47 +0900 (金, 24 5 2013) $");
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
    public Id31Process()
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
    public Id31Process(String agcNo)
    {
        super(agcNo);
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 代替棚指示応答の処理を行います。
     * 受信した電文のMC Keyから、<code>CarryInformation</code>を検索し、
     * 異常応答の場合、搬送状態を 異常 に変更します。
     * ただし、トランザクションのコミット・ロールバックは行っていませんので、呼び出し元で行う必要があります。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Procesing the reply to the instruction for alternative location.
     * Based on the MC Key in communication message received, it searches <code>CarryInformation</code>.
     * If the reply was an error, alter the carry status to 'error'.
     * owever the call source needs to commit or rollback the transaction, as they are not done here.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    @SuppressWarnings("cast")
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        As21Id31 id31dt = new As21Id31(rdt);

        //<jp> 応答区分を取得</jp>
        //<en> Retrieves the reply classification.</en>
        String response = id31dt.getResponseClassification();

        CarryInfoHandler cih = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey cskey = new CarryInfoSearchKey();

        //<jp> 正常受付</jp>
        //<en> normaly received</en>
        if (As21Id31.CLASS_NORMAL_RECEIVE.equals(response))
        {
            // V3.5.3 正常時は、空出荷処理にて削除済みのため処理を終了
            return;
        }
        else
        {
            String mckey = id31dt.getMcKey();
            Object[] tObj = null;

            //<jp> 6024014=代替棚指示応答でNG応答を受信しました。応答区分={0} mckey={1}</jp>
            //<en> 6024014=Alternate location command was not accepted. Response category={0} mckey={1}</en>
            tObj = new Object[2];
            tObj[0] = response;
            tObj[1] = mckey;
            RmiMsgLogClient.write(6024014, LogMessage.F_ERROR, getClass().getName(), tObj);

            //<jp> MC keyを検索条件として設定</jp>
            //<en> Sets MC key as a search condition.</en>
            cskey.setCarryKey(mckey);

            //<jp> 該当のCarryInfoを取得</jp>
            //<en> Retrieves corresponding CarryInfo.</en>
            CarryInfo[] earr = (CarryInfo[])cih.find(cskey);

            //<jp> 該当データなし</jp>
            //<en> There is no corresponding data.</en>
            if (earr.length == 0)
            {
                // V3.5.3 空出荷処理にて削除済みのため処理を終了
                return;
            }

            if (earr[0] instanceof CarryInfo)
            {
                //<jp> 搬送状態を異常にする</jp>
                //<en> Alters the carry status to error.</en>
                CarryInfo ci = (CarryInfo)earr[0];
                CarryInfoAlterKey cakey = new CarryInfoAlterKey();
                cakey.updateCmdStatus(CarryInfo.CMD_STATUS_ERROR);
                cakey.setCarryKey(ci.getCarryKey());
                cakey.updateLastUpdatePname(getClassName());
                cih.modify(cakey);
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

