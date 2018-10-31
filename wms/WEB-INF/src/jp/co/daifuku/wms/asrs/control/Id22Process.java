// $Id: Id22Process.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id22;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.communication.as21.SystemTextTransmission;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.Station;

/**<jp>
 * 日付・時刻要求受信を処理するクラスです。対象となるAGCに日付・時刻データを送信します。
 * 作業開始が報告された場合、該当のGROUPCONTROLLERをオンラインに更新します。
 * システムリカバリ実施が報告された場合、
 * 内容をメッセージログに出力しますが、eWareNavi側でデータクリア処理等は行いません。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/11/24</TD><TD>Y.Kawai</TD><TD>eWareNavi対応</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class processes the receiving of date and time requests. It submits the data of date and time to the 
 * target AGC. When work start is reported, it updates the corresponding GROUPCONTROLLER on-line.
 * If the operation of system recovery is reported, it outputs the contents to message logs, however,
 * there will be no processing on eWareNavi side such as data clearing, etc.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class Id22Process
        extends IdProcess
{
    //Class fields----------------------------------------------------

    // Class variables -----------------------------------------------

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
    public Id22Process()
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
    public Id22Process(String agcNumber)
    {
        super(agcNumber);
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 日付・時刻要求の処理を行います。
     * 対象となるAGCに日付・時刻データを送信し、受信した区分からそれぞれの処理を行います。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processes the date and time requests.
     * It submits the data of date and time to the target AGC and based on the classificaiotn of response received, handles the respective operations.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        As21Id22 id22dt = new As21Id22(rdt);

        //<jp> 受信したAGCNoに該当する GroupControllerのインスタンスを生成する。</jp>
        //<en> Generating the instance of GroupController corresponds to the received AGCNo.</en>
        GroupController gct = GroupController.getInstance(getConnection(), getAgcNo());
        int req = id22dt.getRequestClassification();

        //<jp> 要求区分が作業開始の場合 GroupControler.Statusにオンラインをセットして、</jp>
        //<jp> ステーションの状態を初期化</jp>
        //<en> If the the request classification is work start, sets the GroupControler.Status on-line,</en>
        //<en> then initializes the status of the station.</en>
        if (req == As21Id22.W_START)
        {
            gct.setStatus(GroupController.STATUS_ONLINE);
            initialize();
        }

        //<jp> 日付・時刻Data送信</jp>
        //<en> Transmission of date and time data.</en>
        SystemTextTransmission.id02send(getAgcNo());

        //<jp> 要求区分が作業開始かつSYSTEM RECOVERY実施の場合メッセージ出力</jp>
        //<en> If the the request classification is work start and SYSTEM RECOVERY is operated, output the message log.</en>
        if (req == As21Id22.W_START && id22dt.isSystemRecoveryReports())
        {
            Object[] tObj = new Object[1];
            tObj[0] = getAgcNo();
            //<jp> 6022014=AGCよりシステムリカバリ実施報告を受信しました。AGCNo.={0}</jp>
            //<en> 6022014=System recovery implementation report is received from SRC No. {0}.</en>
            RmiMsgLogClient.write(6022014, LogMessage.F_INFO, getClass().getName(), tObj);
        }
    }

    // Private methods -----------------------------------------------
    /**<jp>
     * <code>GroupControler</code>に属するステーションの状態を初期化します。
     * @throws ReadWriteException      データベースに対する処理で発生した場合に通知します。
     * @throws InvalidDefineException  更新条件に不整合があった場合に通知されます。
     * @throws NotFoundException       更新すべきデータが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Initializes the status of the station which belongs to <code>GroupControler</code>.
     * @throws ReadWriteException :Notifies if exception occured when processing for database.
     * @throws InvalidDefineException :Notifies if there are any inconsistency in condition of data update.
     * @throws NotFoundException  :Notifies if data to update cannot be found.
     </en>*/
    public void initialize()
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException
    {
        //<jp> ステーションテーブルの更新</jp>
        //<jp> AGCNoを条件にステーションインスタンス生成し、機器情報を元に状態を更新する。</jp>
        //<en> Updating the station table</en>
        //<en> With AGCNo as a condition, generates a station instatnce. Then, update status based on the machine information.</en>
        //<jp> SearchKeyインスタンス生成</jp>
        //<en> Generating the instance of SearchKey</en>
        StationSearchKey sk = new StationSearchKey();
        sk.setControllerNo(String.valueOf(getAgcNo()));

        //<jp> ハンドラインスタンス生成</jp>
        //<en> Generating the handler instance</en>
        StationHandler sh = new StationHandler(getConnection());
        Station[] st = (Station[])sh.find(sk);
        for (int i = 0; i < st.length; i++)
        {
            //<jp> "入出庫兼用"で、"AGCモード"でなければ初期化を行う</jp>
            //<en> Initialize if 'storage/retrieval ready' and not on 'AGC mode'.</en>
            if (Station.STATION_TYPE_INOUT.equals(st[i].getStationType())
                    && !Station.MODE_TYPE_AGC_CHANGE.equals(st[i].getModeType()))
            {
                //<jp> 現在のモード      ：ニュートラル</jp>
                //<jp> モード切替要求    ：モード切替要求無し</jp>
                //<jp> モード切替開始時間：null</jp>
                //<en> current mode              :nutral</en>
                //<en> mode switch request       :no request for mode switch</en>
                //<en> start time for mode switch:null</en>
                StationAlterKey sak = new StationAlterKey();
                sak.setStationNo(st[i].getStationNo());
                sak.updateCurrentMode(Station.CURRENT_MODE_NEUTRAL);
                sak.updateModeRequest(Station.MODE_REQUEST_NONE);
                sak.updateModeRequestDate(null);
                sh.modify(sak);
            }
        }
    }
}
//end of class
