// $Id: Id64Process.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id64;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.OperationDisplay;

/**<jp>
 * すくい完了報告を処理するクラスです。該当するCarryInformationの状態をすくい完了に更新します。
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
 * This class processes the pick up completion report. It updates hte status of corresponding CarryInformation to pick-up completion.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </en>*/
public class Id64Process
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
        return ("$Revision: 7996 $,$Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $");
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
    public Id64Process()
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
    public Id64Process(String agcNo)
    {
        super(agcNo);
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * すくい完了報告受信の処理を行います。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processes the receiving of pick-up completion report.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    @SuppressWarnings("cast")
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        CarryInfo ci;
        As21Id64 id64dt = new As21Id64(rdt);
        CarryInfoHandler cih = new CarryInfoHandler(getConnection());
        String[] ckey = id64dt.getVmcKey();

        //<jp> 受信したテキストの件数分処理（標準では最大2件）</jp>
        //<en> Processing as much data as text received (2 at max. in standard)</en>
        for (int i = 0; i < ckey.length; i++)
        {
            //<jp> MC keyを検索条件として設定</jp>
            //<en> Sets the MC key as a search condition.</en>
            CarryInfoSearchKey cikey = new CarryInfoSearchKey();
            cikey.setCarryKey(ckey[i]);

            //<jp> 該当のCarryInfoを取得</jp>
            //<en> Retrieves the corresponding CarryInfo.</en>
            //DFKLOOK:Start 3.5
            CarryInfo[] earr = (CarryInfo[])cih.findForUpdate(cikey);
            //DFKLOOK:End 3.5

            //<jp> 該当データなし</jp>
            //<en> There is no corresponding data.</en>
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
            }
            else
            {
                //<jp> 6006008={0}以外のオブジェクトが返されました。</jp>
                //<en> 6006008=Object other than {0} was returned.</en>
                Object[] tObj = {
                    "CarryInformation",
                };
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
                //<jp> 搬送指示応答テキストが飛んでいることをロギングする</jp>
                //<en> Logging that response text to carry instruction has been skipped.</en>
                //<jp> 6022021=搬送指示応答処理が行われていません。強制的に搬送指示応答処理を行います。mckey={0}</jp>
                //<en> 6022021=No reply for transfer instruction. Forcing to complete picking. mckey={0}</en>
                Object[] tObj = null;
                tObj = new Object[1];
                tObj[0] = ci.getCarryKey();
                RmiMsgLogClient.write(6022021, LogMessage.F_ERROR, getClass().getName(), tObj);
                Connection conn = getConnection();

                try
                {
                    //<jp> CarryInformationに対して、搬送指示応答処理を実行する。</jp>
                    //<en> Executes the reply to carry instruction for this CarryInformation.</en>
                    Id25Process.updateNormalResponce(conn, ci);
                }
                // 例外発生時も処理を続行する
                catch (Exception e)
                {
                    // 飛び越し処理に失敗した場合ロールバックする
                    conn.rollback();

                    // 別スレッドでID25処理が行われているか確認
                    earr = (CarryInfo[])cih.find(cikey);

                    if (earr.length == 0)
                    {
                        continue;
                    }
                    cmdStatus = earr[0].getCmdStatus();
                    Object[] tObject = {
                        earr[0].getCarryKey(),
                    };
                    // 搬送状態が指示済み以外の場合は救い完了処理を行えないと判断する。
                    if (!CarryInfo.CMD_STATUS_INSTRUCTION.equals(cmdStatus))
                    {
                        //<jp> 6024048=受信電文飛び越し処理でエラーが発生したため、処理を中断しました。mckey={0} StackTrace={1}
                        RmiMsgLogClient.write(new TraceHandler(6024048, e), getClass().getName(), tObject);
                        continue;
                    }
                    //<jp> 6024045=受信電文飛び越し処理でエラーが発生しましたが、処理を続行します。mckey={0} StackTrace={1}</jp>
                    RmiMsgLogClient.write(new TraceHandler(6024045, e), getClass().getName(), tObject);
                }
            }

            // 搬送情報をすくい完了に更新します
            CarryInfoAlterKey cakey = new CarryInfoAlterKey();
            cakey.updateCmdStatus(CarryInfo.CMD_STATUS_PICKUP);
            cakey.updateLastUpdatePname(Id64Process.class.getSimpleName());
            cakey.setCarryKey(ci.getCarryKey());
            cih.modify(cakey);

            //<jp> 作業表示データの有無を確認する。作業表示データがあれば削除する。</jp>
            //<en> Checks whether/not the on-line indicaiton is operated. Delete if there is the on-line indication data.</en>
            OperationDisplayHandler ohandl = new OperationDisplayHandler(getConnection());
            OperationDisplaySearchKey skey = new OperationDisplaySearchKey();
            skey.setCarryKey(ci.getCarryKey());
            OperationDisplay[] odisp = (OperationDisplay[])ohandl.find(skey);
            if (odisp.length > 0)
            {
                ohandl.drop(skey);
            }
        }
    }

    // Private methods -----------------------------------------------

}
//end of class
