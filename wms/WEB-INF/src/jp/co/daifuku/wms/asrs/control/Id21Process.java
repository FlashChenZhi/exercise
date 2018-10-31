// $Id: Id21Process.java 8004 2011-08-30 00:51:30Z kishimoto $
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
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id21;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;

/**<jp>
 * 作業開始応答受信を処理するクラスです。受信した応答内容を元にGROUPCONTROLERの更新を行います。
 * システムリカバリ実施が報告された場合、
 * 内容をメッセージログに出力しますが、eWareNavi側でデータクリア処理等は行いません。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/11/24</TD><TD>Y.Kawai</TD><TD>eWareNavi対応</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8004 $, $Date: 2011-08-30 09:51:30 +0900 (火, 30 8 2011) $
 * @author  $Author: kishimoto $
 </jp>*/
public class Id21Process
        extends IdProcess
{
    //Class fields----------------------------------------------------

    // Class variables -----------------------------------------------

    /** <code>PROC_ID</code> */
    private static final String PROC_ID = "21";

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
        return ("$Revision: 8004 $,$Date: 2011-08-30 09:51:30 +0900 (火, 30 8 2011) $");
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
    public Id21Process()
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
    public Id21Process(String agcNumber)
    {
        super(agcNumber);
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 作業開始応答の処理を行います。
     * 受信した応答区分からそれぞれの処理を行います。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processes the response to the work start.
     * Based on the classificaiotn of response received, handles the respective operations.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        As21Id21 id21dt = new As21Id21(rdt);

        //<jp> 受信したAGCNoに該当する GroupControllerのインスタンスを生成する。</jp>
        //<en> Generating the instance of GroupController corresponding to the ACGCNo. received.</en>
        GroupController gct = GroupController.getInstance(getConnection(), getAgcNo());
        String str = id21dt.getResponseClassification();

        //<jp> 応答区分が正常な場合 GroupControler.Statusにオンラインをセットして、</jp>
        //<jp> ステーションの状態を初期化</jp>
        //<en> If the the classificaiotn of response is normal, sets the GroupControler.Status on-line,</en>
        //<en> then initializes the status of the station.</en>
        if (str.equals(As21Id21.NORMAL))
        {
            gct.setStatus(GroupController.STATUS_ONLINE);
            initialize();

            // update start 2011.08.29 GroupControllerに属する棚のアクセス不可棚状態をクリアする
            // AGCNo.から更新する範囲を取得します。
            AisleSearchKey askey = new AisleSearchKey();
            AisleHandler ahandler = new AisleHandler(getConnection());
            askey.setControllerNo(getAgcNo());
            Aisle[] aisles = (Aisle[])ahandler.find(askey);

            ShelfHandler shandler = new ShelfHandler(getConnection());
            ShelfAlterKey sAKey = new ShelfAlterKey();
            for (Aisle aisle : aisles)
            {
                sAKey.clear();
                sAKey.setParentStationNo(aisle.getStationNo());

                //<jp> Access不可解除</jp>
                //<en> Canceling the inaccessible status</en>
                sAKey.updateAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);

                shandler.modify(sAKey);
            }
            // update end 2011.08.29 GroupControllerに属する棚のアクセス不可棚状態をクリアする
        }
        //<jp> 応答区分がAGC状態異常な場合 メッセージロギング。</jp>
        //<en> If the the classificaiotn of response os AGC status error, message log should be output.</en>
        else if (str.equals(As21Id21.AGC_CONDITION_ERR))
        {
            Object[] tObj = new Object[1];
            tObj[0] = getAgcNo();
            //<jp> 6024001=AGC状態異常 AGCNo.={0}</jp>
            //<en> 6024001=SRC status error SRC No.={0}</en>
            RmiMsgLogClient.write(6024001, LogMessage.F_ERROR, getClass().getName(), tObj);
        }
        //<jp> 応答区分がDATA ERRORな場合 メッセージロギング。</jp>
        //<en> If the classificaiotn of response is DATA ERROR, message log should be output.</en>
        else if (str.equals(As21Id21.DATA_ERR))
        {
            //<jp> 6024002=DATA ERROR</jp>
            //<en> 6024002=DATA ERROR</en>
            RmiMsgLogClient.write(6024002, LogMessage.F_ERROR, getClass().getName(), null);
        }
        //<jp> 応答区分が不正</jp>
        //<en> The classificaiotn of response is invalid.</en>
        else
        {
            Object[] tObj = new Object[2];
            tObj[0] = PROC_ID;
            tObj[1] = str;
            //<jp> 6026037=テキストID{0}の応答区分が不正です。受信応答区分={1}</jp>
            //<en> 6026037=Response category for text ID {0} is invalid.  Receive response category={1}</en>
            RmiMsgLogClient.write(6026037, LogMessage.F_ERROR, getClass().getName(), tObj);
            throw new InvalidProtocolException(WmsMessageFormatter.format(6026037, tObj));
        }

        //<jp> SYSTEM RECOVERY実施の場合メッセージ出力</jp>
        //<en> If SYSTEM RECOVERY is operated, message log should be output.</en>
        if (id21dt.isSystemRecoveryReport())
        {
            Object[] tObj = new Object[1];
            tObj[0] = getAgcNo();
            //<jp> 6022014=AGCよりシステムリカバリ実施報告を受信しました。AGCNo.={0}</jp>
            //<en> 6022014=System recovery implementation report is received from SRC No. {0}.</en>
            RmiMsgLogClient.write(6022014, LogMessage.F_INFO, getClass().getName(), tObj);
        }
        // オペレーションログ出力
        log_write(id21dt);
    }

    // Private methods -----------------------------------------------
    /**<jp>
     * <code>GroupControler</code>に属するステーションの状態を初期化します。
     * @throws ReadWriteException      データベースに対する処理で発生した場合に通知します。
     * @throws InvalidDefineException  更新条件に不整合があった場合に通知されます。
     * @throws NotFoundException       対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Initializes the status of the station which belongs to <code>GroupControler</code>.
     * @throws ReadWriteException :Notifies if exception occured when processing for database.
     * @throws InvalidDefineException :Notifies if there are any inconsistency in condition of data update.
     * @throws NotFoundException      :Notifies if there is no such data.
     </en>*/
    public void initialize()
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException
    {
        //<jp> ステーションテーブルの更新</jp>
        //<jp> AGCNoを条件にステーションインスタンス生成し、機器情報を元に状態を更新する。</jp>
        //<en> Updating the station table.</en>
        //<en> With AGCNo as a condition, generates the station instance and update the status based on the machine information.</en>
        //<jp> SearchKeyインスタンス生成</jp>
        //<en> Generating instance of SearchKey.</en>
        StationSearchKey sk = new StationSearchKey();
        sk.setControllerNo(String.valueOf(getAgcNo()));

        //<jp> ハンドラインスタンス生成</jp>
        //<en> Generating handler instance.</en>
        StationHandler sh = new StationHandler(getConnection());
        Station[] st = (Station[])sh.find(sk);
        for (int i = 0; i < st.length; i++)
        {
            //<jp> "入出庫兼用"で、"AGCモード"でなければ初期化を行う</jp>
            //<en> If the station is 'storage/retrieval ready' and not on 'AGC mode', initialize.</en>
            if (Station.STATION_TYPE_INOUT.equals(st[i].getStationType())
                    && !Station.MODE_TYPE_AGC_CHANGE.equals(st[i].getModeType()))
            {
                //<jp> 現在のモード      ：ニュートラル</jp>
                //<jp> モード切替要求    ：モード切替要求無し</jp>
                //<jp> モード切替開始時間：null</jp>
                //<en> current mode              :nutral</en>
                //<en> request for mode switch   : no request for mode switch</en>
                //<en> start time of the switch  : null</en>
                StationAlterKey sak = new StationAlterKey();
                sak.setStationNo(st[i].getStationNo());
                sak.updateCurrentMode(Station.CURRENT_MODE_NEUTRAL);
                sak.updateModeRequest(Station.MODE_REQUEST_NONE);
                sak.updateModeRequestDate(null);
                sh.modify(sak);
            }
        }
    }

    /**
     * オペレーションログ情報の書込み処理を行います <BR>
     * @param   id21dt    As21Id21
     * @throws Exception 全ての例外を報告します。
     */
    protected void log_write(As21Id21 id21dt)
            throws Exception
    {
        // オペレーションログ出力
        DfkUserInfo userInfo = new DfkUserInfo();
        // DS番号
        userInfo.setDsNumber(id21dt.getDsNumber());
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
        userInfo.setPageNameResourceKey(id21dt.getResourceKey());

        List<String> itemLog = new ArrayList<String>();

        // AGC-No
        itemLog.add(getAgcNo());
        // 応答区分
        itemLog.add(id21dt.getResponseClassification());
        // エラー詳細
        itemLog.add(id21dt.getErrorDetails());
        // システムリカバリー報告
        itemLog.add(id21dt.getSystemRecoveryReport());

        // ログ出力
        P11LogWriter opeLogWriter = new P11LogWriter(getConnection());
        opeLogWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_CONTROL_MODULE, itemLog);

    }
}
//end of class
