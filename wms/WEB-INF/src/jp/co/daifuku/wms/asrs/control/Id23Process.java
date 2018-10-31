// $Id: Id23Process.java 5427 2009-11-06 10:34:56Z okayama $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id23;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.MachineAlterKey;
import jp.co.daifuku.wms.base.dbhandler.MachineHandler;
import jp.co.daifuku.wms.base.dbhandler.MachineSearchKey;
import jp.co.daifuku.wms.base.entity.Machine;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**<jp>
 * 作業終了応答受信を処理するクラスです。受信した応答内容を元にGROUPCONTROLERの更新を行います。
 * GROUPCONTROLLERの状態がOFFLINEに変更された場合、GROUPCONTROLLER配下のMACHINESTATEの状態を未接続にします。
 * ただし切離中の機器はその状態を保持します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2006/02/02</TD><TD>Y.Okamura</TD><TD>eWareNavi対応</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5427 $, $Date: 2009-11-06 19:34:56 +0900 (金, 06 11 2009) $
 * @author  $Author: okayama $
 </jp>*/
/**<en>
 * This class processes the receiving of response to work completion. According to the contents of response received, it updates the GROUPCONTROLER.
 * When the status of GROUPCONTROLLER is altered to OFFLINE, it disconnects the status of MACHINESTATE under GROUPCONTROLLER.
 * Except any machines off-line remain the status.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 5427 $, $Date: 2009-11-06 19:34:56 +0900 (金, 06 11 2009) $
 * @author  $Author: okayama $
 </en>*/
public class Id23Process
        extends IdProcess
{
    //Class fields----------------------------------------------------

    // Class variables -----------------------------------------------

    /** <code>PROC_ID</code> */
    private static final String PROC_ID = "23";

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 5427 $,$Date: 2009-11-06 19:34:56 +0900 (金, 06 11 2009) $");
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
    public Id23Process()
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
    public Id23Process(String agcNumber)
    {
        super(agcNumber);
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 作業終了応答の処理を行います。
     * 受信した応答区分からそれぞれの処理を行います。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processes the response to work completion.
     * Based on the classificaiotn of response received, handles the respective operations.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        As21Id23 id23dt = new As21Id23(rdt);

        //<jp> 受信したAGCNoをもとにGroupControllerインスタンスを生成する。</jp>
        //<en> Based on the AGCNo. received, generate the instance of GroupController.</en>
        GroupController gct = GroupController.getInstance(getConnection(), getAgcNo());
        String str = id23dt.getResponseClassification();

        //<jp> 正常終了</jp>
        //<en> Normal end</en>
        if (str.equals(As21Id23.NORMAL_END))
        {
            //<jp> 応答区分が正常な場合 GroupController.Statusにオフラインをセット</jp>
            //<en> If hte classificaiton of response is normal, set the GroupController.Status off-line.</en>
            gct.setStatus(GroupController.STATUS_OFFLINE);
            
            //<jp> 応答区分が正常な場合 GroupController.Recv_Dateに現在日付をセット</jp>
            gct.setRecvDate(String.valueOf(getAgcNo()));

            //<jp> 機器テーブルの更新</jp>
            //<jp> AGCNoから機器情報の一覧を生成する。</jp>
            //<en> Updating the machine table</en>
            //<en> Generatest the list of machine information based on the AGCNo.</en>
            MachineSearchKey mk = new MachineSearchKey();
            MachineHandler mh = new MachineHandler(getConnection());

            mk.setControllerNo(String.valueOf(getAgcNo()));
            Machine[] machines = (Machine[])mh.find(mk);
            for (int i = 0; i < machines.length; i++)
            {
                Machine machine = machines[i];
                // 機器状態が切離し以外の場合、statusを未接続（状態不明）にする。
                String statusFlag = machine.getStatusFlag();
                boolean offLine = SystemDefine.MACHINE_STATE_OFFLINE.equals(statusFlag);
                if (!offLine)
                {
                    MachineAlterKey mak = new MachineAlterKey();
                    mak.setMachineType(machine.getMachineType());
                    mak.setMachineNo(machine.getMachineNo());
                    mak.setControllerNo(machine.getControllerNo());
                    mak.updateStatusFlag(null);
                    mak.updateErrorCode(null);
                    mh.modify(mak);
                }
            }

            // ステーションとアイルの状態を更新する
            Id30Process id30 = new Id30Process(getAgcNo());
            for (int i = 0; i < machines.length; i++)
            {
                Station st = StationFactory.makeStation(getConnection(), machines[i].getStationNo());
                id30.updateStatus(getConnection(), st);
            }
        }
        //<jp> 終了不可</jp>
        //<en> Not able to end</en>
        else if (str.equals(As21Id23.END_IMPOSS))
        {
            //<jp> GroupController.Statusにオンラインをセット</jp>
            //<en> Sets GroupController.Status on-line.</en>
            gct.setStatus(GroupController.STATUS_ONLINE);
            Object[] tObj = new Object[2];
            tObj[0] = id23dt.getModelCode();
            tObj[1] = id23dt.getMachineNo();
            //<jp> 6024028=作業終了応答で終了不可を受取りました。作業終了はできません。機種Code={0} 号機No.={1}</jp>
            //<en> 6024028=Unable to terminate was received in response to End Work. Machine type Code={0} Machine No.={1}.</en>
            RmiMsgLogClient.write(6024028, LogMessage.F_WARN, getClass().getName(), tObj);
        }
        //<jp> データエラー</jp>
        //<en> Data error</en>
        else if (str.equals(As21Id23.DATA_ERR))
        {
            //<jp> GroupController.Statusにオンラインをセット</jp>
            //<en> Sets GroupController.Status on-line</en>
            gct.setStatus(GroupController.STATUS_ONLINE);
            Object[] tObj = new Object[2];
            tObj[0] = id23dt.getModelCode();
            tObj[1] = id23dt.getMachineNo();
            //<jp> 6024029=作業終了応答でデータエラーを受取りました。作業終了はできません。機種Code={0} 号機No.={1}</jp>
            //<en> 6024029=Data error was received in response to End Work. Machine type Code={0} Machine No.={1}</en>
            RmiMsgLogClient.write(6024029, LogMessage.F_WARN, getClass().getName(), tObj);
        }
        //<jp> 応答区分が不正</jp>
        //<en> Classificaiton of replay is invalid.</en>
        else
        {
            //<jp> GroupController.Statusにオンラインをセット</jp>
            //<en> Sets GroupController.Status on-line</en>
            gct.setStatus(GroupController.STATUS_ONLINE);
            Object[] tObj = new Object[2];
            tObj[0] = PROC_ID;
            tObj[1] = str;
            //<jp> 6026037=テキストID{0}の応答区分が不正です。受信応答区分={1}</jp>
            //<en> 6026037=Response category for text ID {0} is invalid.  Receive response category={1}</en>
            RmiMsgLogClient.write(6026037, LogMessage.F_ERROR, getClass().getName(), tObj);
        }
        // オペレーションログ出力
        log_write(id23dt);
    }

    // Private methods -----------------------------------------------
    /**
     * オペレーションログ情報の書込み処理を行います <BR>
     * @param   id23dt    As21Id23
     * @throws Exception 全ての例外を報告します。
     */
    protected void log_write(As21Id23 id23dt)
            throws Exception
    {
        // オペレーションログ出力
        DfkUserInfo userInfo = new DfkUserInfo();
        // DS番号
        userInfo.setDsNumber(id23dt.getDsNumber());
        // ユーザID
        userInfo.setUserId(WmsParam.SYS_USER_ID);
        // ユーザ名称
        userInfo.setUserName(WmsParam.SYS_USER_NAME);
        // 端末No
        userInfo.setTerminalNumber(WmsParam.SYS_TERMINAL_NO);
        // 端末名称
        userInfo.setTerminalName(WmsParam.SYS_TERMINAL_NAME);
        // 端末IPアドレス
        userInfo.setTerminalAddress(WmsParam.SYS_IP_ADDRESS);
        // リソース番号
        userInfo.setPageNameResourceKey(id23dt.getResourceKey());
        
        List<String> itemLog = new ArrayList<String>();

        // AGC-No
        itemLog.add(getAgcNo());
        // 応答区分
        itemLog.add(id23dt.getResponseClassification());
        // 機種コード
        itemLog.add(id23dt.getModelCode());
        // 号機No
        itemLog.add(id23dt.getMachineNo());
        
        // ログ出力
        P11LogWriter opeLogWriter = new P11LogWriter(getConnection());
        opeLogWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_CONTROL_MODULE, itemLog);

    }
}
//end of class
