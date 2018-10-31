// $Id: RetrievalSender.java 8062 2013-05-27 03:53:08Z kishimoto $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.rmi.RmiServAbstImpl;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.StationController;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LoadSizeHandler;
import jp.co.daifuku.wms.base.dbhandler.LoadSizeSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.base.entity.LoadSize;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.util.CheckConnection;

/**<jp>
 * AS21通信プロトコルに対応した、出庫指示の送信を行なうクラスです。<BR>
 * 出庫指示送信対象となるCarryInformationの取得を行い、出庫条件を満たしていれば
 * 指示対象となるAGCに対して出庫指示を送信します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/17</TD><TD>M.INOUE</TD><TD>modify a way to 'wait', from 'sleep()' to 'wait()'</TD></TR>
 * <TR><TD>2004/03/18</TD><TD>M.INOUE</TD><TD>搬送指示可能数以上に搬送指示を送信していたところを修正</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8062 $, $Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $
 * @author  $Author: kishimoto $
 </jp>*/
/**<en>
 * This class sends the retrieval instruction based on the AS21 communication ptrotocol.<br>
 * It retrieves the CarryInformation, which is the sending object as retrieval instruction.
 * If the conditions for retrieval are met, the retrieval instruction will be sent to the target AGC.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/17</TD><TD>M.INOUE</TD><TD>modify a way to 'wait', from 'sleep()' to 'wait()'</TD></TR>
 * <TR><TD>2004/03/18</TD><TD>M.INOUE</TD><TD>Corrected the process of sending carry instructions over the possible quantity. </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8062 $, $Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $
 * @author  $Author: kishimoto $
 </en>*/
public class RetrievalSender
        extends RmiServAbstImpl
        implements java.lang.Runnable
{
    // Class fields --------------------------------------------------
    /**<jp>
     * リモートオブジェクトにバインドするオブジェクト名
     </jp>*/
    /**<en>
     * Object name to bind to the remote object
     </en>*/
    public static final String OBJECT_NAME = "RetrievalSender";

    private static final String DOUBLE_ZERO = "00";

    // Class variables -----------------------------------------------
    /**<jp>
     * データベース接続用のコネクション
     </jp>*/
    /**<en>
     * Connection for database use
     </en>*/
    private Connection _conn = null;

    /**<jp>
     * 出庫指示テキスト内に一度にセットする件数。
     </jp>*/
    /**<en>
     * number of instructions to set in each retrieval instruction text
     </en>*/
    private static final int SEND_MAX = 2;

    /**<jp>
     * 出庫指示テキスト内に一度に指示出来る件数。
     </jp>*/
    /**<en>
     * number of instructions to set in each retrieval instruction text
     </en>*/
    private static final int INSTRUCTION_MAX = 2;

    /**<jp>
     * 搬送データ操作用ハンドラクラス
     </jp>*/
    /**<en>
     * Handler class to manipulate the carrying data
     </en>*/
    private CarryInfoHandler _carryInfoHandler = null;

    /**<jp>
     * Stationデータ操作用ハンドラクラス
     </jp>*/
    /**<en>
     * Handler class to manipulate the Station data
     </en>*/
    private StationHandler _stationHandler = null;

    /**<jp>
     * 搬送ルート制御クラス
     </jp>*/
    /**<en>
     * control class of transport route
     </en>*/
    private RouteController _routeController = null;

    /**<jp>
     * 搬送データはあるが送信可能な状態ではない場合のSleep Time
     </jp>*/
    /**<en>
     * Sleep time when ther is carrying data but not able to send
     </en>*/
    private int _existSleepTime = 1000;

    /**<jp>
     * このフラグは、RetrievalSenderクラスを終了するかどうか判断します。
     * ExitFlagがtrueになった場合、run()メソッド内の無限ループから抜けます。
     * このフラグを更新するためには、stop()メソッドを使用します。
     </jp>*/
    /**<en>
     * This flag is to determine whether/not to terminate the RetrievalSender class.
     * When ExitFlag changed to true, it pulls out of the infinite loop in run() method.
     * For the updating of this flag, stop() method should be used.
     </en>*/
    private boolean _exitFlag = false;

    /**<jp>
     * このフラグは、RetrievalSenderが、wait状態になるための判断に使用します。
     * _requestフラグは、外部からの出庫指示要求に応じて状態を変更します。
     * _requestがtrueの場合、出庫指示処理はwait状態にはなりません。
     </jp>*/
    /**<en>
     * This flag is to determine whether/not RetrievalSender should be in wait state.
     * _request flag alters its status according to the retrieval requests from the external.
     * When _request is true, the processing of the retrieval instruction will not change to wait state.
     </en>*/
    private boolean _request = false;

    // DFKLOOK ここから追加(DB再接続フラグ)

    /**<jp>
     * このフラグは、オブジェクト内で取得したDBコネクションが保持されているかの判断に使用します。
     * 使用する場合は、CheckConnectionクラスのcheckメソッドを呼出てください。
     * コネクションを失っている場合trueがセットされます。
     </jp>*/
    /**<en>
     * This flag is obtained in the DB object used to determine whether the connection isu maintained.
     * If you are using, please call the check methods of the CheckConnection class.
     * If object have lost connection is set to 'true'.
     </en>*/
    protected boolean _dbConCheckFlag = false;

    // DFKLOOK ここまで

    private int _instructionMax = 0;

//    /**<jp>
//     * 多重起動フラグ
//     </jp>*/
//    /**<en>
//     * start-up overlaps
//     </en>*/
//    private boolean _overlapsFlag = false;

    // 2010/05/11 Y.Osawa ADD ST
    /**<jp>
     * 出庫指示可能数（ステーションの最大出庫指示可能数からそのステーションへの搬送数を引いた数）
     </jp>*/
    private int _palletMaxQty = 0;

    // 2010/05/11 Y.Osawa ADD ED

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
        return ("$Revision: 8062 $,$Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $");
    }

    // Constructors --------------------------------------------------
    /**
     * AGC 番号を保持する変数。
     */
    private String _agcNo;

    /**<jp>
     * 新しい<code>RetrievalSender</code>のインスタンスを作成しデータベースとのコネクションを取得します。
     * コネクションはAS/RSシステムパラメータから取得します。
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     </jp>*/
    /**<en>
     * Create new instance of <code>RetrievalSender</code>.
     * The connection will be obtained  from parameter of AS/RS system out of resource.
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     * @throws RemoteException  Exception related to communication generated while executing remote method call
     </en>*/
    public RetrievalSender()
            throws ReadWriteException,
                RemoteException
    {
        //<jp> リソースファイルからSleep Timeを取り込む</jp>
        //<en> Load hte Sleep Time from the resource file</en>
        _existSleepTime = WmsParam.INSTRUCT_CONTROL_SLEEP_SEC_DATA_EXIST;

        // 出庫指示テキスト内に一度に指示出来る件数をセットします。
        setInstructionMax(INSTRUCTION_MAX);

        /* ADD Start 2009.12.08 */
        //データベースコネクションチェックフラグセット
        if ("1".equals(WmsParam.WMS_DB_CONNECT_CHECK))
        {
            _dbConCheckFlag = true;
        }
        /* ADD End 2009.12.08 */

        try
        {
            //<jp> DataBaseへのコネクションを獲得する。ユーザ名等はリソースファイルより獲得。</jp>
            //<en> Establish the connection with DataBase. Users name is obtained from the resource file.</en>
            _conn = WmsParam.getConnection();
            restRequest();
            initHandler();
//            /* ADD Start 2006.10.10 E.Takeda */
//            Runtime.getRuntime().addShutdownHook(new Thread()
//            {
//                public void run()
//                {
//                    // 終了処理
//                    try
//                    {
//                        if (_overlapsFlag)
//                        {
//                            // 多重起動の場合は何もしない
//                            return;
//                        }
//
//                        _exitFlag = true;
//                        closeConnection();
//
//                        // unbind
//                        RmiUnbinder unbinder = new RmiUnbinder();
//                        if (unbinder.isbind(OBJECT_NAME))
//                        {
//                            unbinder.unbind(OBJECT_NAME);
//                        }
//                    }
//                    catch (Exception e)
//                    {
//
//                    }
//                }
//            });
//            /* ADD End 2006.10.10 */

        }
        catch (SQLException e)
        {
            Object[] tObj = {
                new Integer(e.getErrorCode()),
            };
            //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
            //<en> 6007030=Database error occured. error code={0}</en>
            RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
            throw new ReadWriteException(e);
        }
    }

    /**
     * 新しい<code>RetrievalSender</code>のインスタンスを作成しデータベースとのコネクションを取得します。
     * コネクションはAS/RSシステムパラメータから取得します。
     * @param agcNumber
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     */
    public RetrievalSender(String agcNumber)
            throws ReadWriteException,
                RemoteException
    {
        //<jp> リソースファイルからSleep Timeを取り込む</jp>
        //<en> Load hte Sleep Time from the resource file</en>
        _existSleepTime = WmsParam.INSTRUCT_CONTROL_SLEEP_SEC_DATA_EXIST;

        // 出庫指示テキスト内に一度に指示出来る件数をセットします。
        setInstructionMax(INSTRUCTION_MAX);

        /* ADD Start 2009.12.08 */
        //データベースコネクションチェックフラグセット
        if ("1".equals(WmsParam.WMS_DB_CONNECT_CHECK))
        {
            _dbConCheckFlag = true;
        }
        /* ADD End 2009.12.08 */

        try
        {
            _agcNo = agcNumber;
            //<jp> DataBaseへのコネクションを獲得する。ユーザ名等はリソースファイルより獲得。</jp>
            //<en> Establish the connection with DataBase. Users name is obtained from the resource file.</en>
            _conn = WmsParam.getConnection();
            restRequest();
            initHandler();
//            /* ADD Start 2006.10.10 E.Takeda */
//            Runtime.getRuntime().addShutdownHook(new Thread()
//            {
//                public void run()
//                {
//                    // 終了処理
//                    try
//                    {
//                        if (_overlapsFlag)
//                        {
//                            // 多重起動の場合は何もしない
//                            return;
//                        }
//
//                        _exitFlag = true;
//                        closeConnection();
//
//                        // unbind
//                        RmiUnbinder unbinder = new RmiUnbinder();
//                        if (unbinder.isbind(OBJECT_NAME + _agcNo))
//                        {
//                            unbinder.unbind(OBJECT_NAME + _agcNo);
//                        }
//                    }
//                    catch (Exception e)
//                    {
//
//                    }
//                }
//            });
//            /* ADD End 2006.10.10 */

        }
        catch (SQLException e)
        {
            Object[] tObj = {
                new Integer(e.getErrorCode()),
            };
            //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
            //<en> 6007030=Database error occured. error code={0}</en>
            RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
            throw new ReadWriteException(e);
        }
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * このクラスで使用する各ハンドラインスタンスの生成を行います。
     </jp>*/
    /**<en>
     * Generates the each handeler instance to be used by this class.
     </en>*/
    protected void initHandler()
    {
        //<jp> ハンドラインスタンス生成</jp>
        //<en> Generate the handler instance</en>
        _carryInfoHandler = new CarryInfoHandler(_conn);
        _stationHandler = new StationHandler(_conn);
        //<jp> ルートコントローラーインスタンス生成</jp>
        //<jp> 毎回ルートチェックを実施する。</jp>
        //<en> Generate the route controller instance</en>
        //<en> Conduct the route checking at every cycle.</en>
        _routeController = new RouteController(_conn, true);
    }

    /**<jp>
     * <code>CarryInformation</code>の読込みを行い、出庫指示可能であれば設備コントローラに出庫指示を送信します。
     * <code>CarryInformation</code>の読込みは一定の間隔で行われます。搬送先が作業場などの送信不可ステーションの場合、ステーション決定を行います。
     </jp>*/
    /**<en>
     * Read <code>CarryInformation</code>, and send th retrieval instruction to the facility controller(AGC)
     * if the retrieval is feasible.
     * Readidng of <code>CarryInformation</code> is done at a certain interval. If the transmission of instruction
     * is not available, e.g. in case of carrying to the workshops etc., the station will be designated.
     </en>*/
    public void run()
    {
        try
        {
            if (WmsParam.MULTI_ASRSSERVER)
            {
                //<jp> 出庫指示をRMI Serverへ登録</jp>
                //<en> Refgister the retrieval instruction to RMI Server</en>
                this.bind("//" + RmiSendClient.RMI_REG_SERVER + _agcNo + "/" + OBJECT_NAME + _agcNo);
            }
            else
            {
                //<jp> 出庫指示をRMI Serverへ登録</jp>
                //<en> Refgister the retrieval instruction to RMI Server</en>
                this.bind("//" + RmiSendClient.RMI_REG_SERVER + "/" + OBJECT_NAME);
            }

            //<jp> 6020016=出庫指示送信処理を起動します。</jp>
            //<en> Start up the transmission of the retrieval instruction</en>
            RmiMsgLogClient.write(6020016, this.getClass().getName());

            //<jp> コネクションチェックメソッド</jp>
            // <en> Connection Check Method</en>
            CheckConnection chk = new CheckConnection();

            //<jp> ずっと動き続けること。</jp>
            //<en> Repeat the following</en>
            while (_exitFlag == false)
            {
                try
                {
                    //<jp> DBに再接続</jp>
                    //<en> Reestablishes the connection with DB</en>
                    if (true == chk.check(_conn, _dbConCheckFlag))
                    {
                        _conn = WmsParam.getConnection();
                        initHandler();
                    }

                    while (_exitFlag == false)
                    {
                        waitRequest();
                        if (_exitFlag)
                        {
                            break;
                        }
                        //<jp> 搬送データを取りに行く。</jp>
                        //<en> Go fetch the carrying data</en>
                        control();
                    }
                }
                catch (NotBoundException e)
                {
                    //<jp> 6024031=AGCとの接続が確立されていません。搬送要求テキストを送信できませんでした。</jp>
                    //<en> 6024031=Cannot send the transfer request text since SRC is not connected. SRC No.={0}</en>
                    RmiMsgLogClient.write(6024031, LogMessage.F_WARN, this.getClass().getName());
                }
                catch (Exception e)
                {
                    //<jp> 6026045=出庫指示タスクで異常が発生しました。例外：{0}</jp>
                    //<en> 6026045=Error occurred in picking instruction task. Exception:{0}</en>
                    RmiMsgLogClient.write(new TraceHandler(6026045, e), this.getClass().getName());
                }
                finally
                {
                    closeConnection();
                    if (!_exitFlag)
                    {
                        Thread.sleep(3000);
                    }
                }
            }
        }
        catch (Exception e)
        {
            //<jp> 6026045=出庫指示タスクで異常が発生しました。例外：{0}</jp>
            //<en> 6026045=Error occurred in picking instruction task. Exception:{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6026045, e), this.getClass().getName());
        }
        finally
        {
            System.out.println("RetrievalSender:::::finally!!");
            //<jp> 6020017=出庫指示送信処理を停止します。</jp>
            //<en> 6020017=Terminating picking instruction sending process.</en>
            RmiMsgLogClient.write(6020017, this.getClass().getName());

            //<jp> RMI Serverから登録を削除</jp>
            //<en> Delete the registration from RMI Server</en>
            this.unbind();
            closeConnection();
        }
    }

    /**
     * @throws InterruptedException スレッドの割り込み時に通知されます。
     */
    private void waitRequest()
            throws InterruptedException
    {
        synchronized (this)
        {
            System.out.println("RetrievalSender ::: WAIT!!!");
            //<jp> 要求が行なわれていない場合、wait状態にする。</jp>
            //<en> If request has not been made, move to the wait state.</en>
            if (!isRequest())
            {
                this.wait();
            }
            //<jp> 要求フラグをリセットする。</jp>
            //<en> Reset the requesting flag.</en>
            restRequest();
            System.out.println("RetrievalSender ::: Wake UP UP UP!!!");
        }
    }

    /**<jp>
     * Wait中の出庫指示を起します。
     </jp>*/
    /**<en>
     * Activate the retrieval instruction in wait state.
     </en>*/
    public void wakeup()
    {
        synchronized (this)
        {
            setRequest();
            this.notify();
        }
    }

    /**<jp>
     * この出庫指示送信処理に対して、即搬送データの確認をするように要求を行ないます。
     * RMI Server経由でキックする場合は、こちらのメソッドを使ってください。
     </jp>*/
    /**<en>
     * To this retrieval instruction, make a request to immediately check the carrying data.
     * Please use this method if kicking via RMI Server.
     </en>
     * @param params not used
     */
    public void write(Object[] params)
    {
        synchronized (this)
        {
            //<jp> スレッドがwait状態ではない可能性があるので</jp>
            //<jp> 要求フラグをセットしておく</jp>
            //<en> As there is a possibility that thread is not in wait state,</en>
            //<en> set the requesting flag beforehand.</en>
            setRequest();
            //<jp> wait解除</jp>
            //<en> release from waiting</en>
            this.notify();
        }
    }

    /**<jp>
     * 処理を終了します。
     * 外部よりこのメソッドが呼び出された場合、動作中のこのインスタンスを終了します。
     </jp>*/
    /**<en>
     * Terminate the process.
     * The process terminate when this method is called externally.
     </en>*/
    public void stop()
    {
        synchronized (this)
        {
            //<jp> スレッドのループが終了するように、フラグを更新する。</jp>
            //<en> Update hte flag so that loop of this thread should terminate.</en>
            _exitFlag = true;
            //<jp> このスレッドの待ち状態を解除する。</jp>
            //<en> Release the wait state of this thread.</en>
            this.notify();
        }
    }

    /**<jp>
     * 出庫指示処理を行います。
     * 出庫指示要求状態のCarryInformationを読込み、搬送可能であれば設備コントローラーに搬送指示を送信します。
     * @throws Exception 出庫指示処理の続行が不可能な場合に通知されます。
     </jp>*/
    /**<en>
     * Procesing the retrieval instruction.
     * Read the CarryInformation requesting for the retrieval instruction, then if the carrying is feasible,
     * @throws Exception : Notifies if retrieval instruction cannot be continued any longer.
     </en>*/
    public void control()
            throws Exception
    {
        //<jp> ステーション毎の出庫指示データを探しに行ってデータが無かった回数</jp>
        //<en> Number of attempts of searching data of retrieval instruction of each station and no data was found</en>
        int countRetrievaDataExist = 0;
        //<jp> ステーション数</jp>
        //<en> Number of stations</en>
        int countStations = 1;

        //<jp> ステーション毎の出庫指示データを探しに行って</jp>
        //<jp> データが無かった回数とステーション数を比べて全てのステーションで</jp>
        //<jp> データが無くなったらcontrol()を抜けてSleepに入る。</jp>
        //<en> Number of times that went searching the data of retrieval instructions for each station (and could not</en>
        //<en> find data) to be compared to the number of stations; if no data is left at all stations, it goes out</en>
        //<en> the control() and get into Sleep state.</en>
        while (countStations > countRetrievaDataExist)
        {
            //<jp> 入出庫兼用＋出庫ステーション一覧を取得する。</jp>
            //<en> Get a list of station operating both storage and the retrieval + station operating retrieval only.</en>
            StationHandler stationHandler = new StationHandler(_conn);
            StationSearchKey stationKey = new StationSearchKey();
            // 検索キーセット
            stationKey.clear();
            if (WmsParam.MULTI_ASRSSERVER)
            {
                stationKey.setControllerNo(_agcNo);
            }
            stationKey.setSendable(Station.SENDABLE_TRUE);
            stationKey.setModeType(Station.MODE_TYPE_AUTO_CHANGE, "!=");
            stationKey.setStationType(Station.STATION_TYPE_OUT, "=", "(", "", false);
            stationKey.setStationType(Station.STATION_TYPE_INOUT, "=", "", ")", true);
            stationKey.setStationNoOrder(true);
            stationKey.setKey(jp.co.daifuku.wms.base.entity.GroupController.STATUS_FLAG, SystemDefine.GC_STATUS_ONLINE);
            stationKey.setJoin(Station.CONTROLLER_NO, jp.co.daifuku.wms.base.entity.GroupController.CONTROLLER_NO);

            // 検索
            Station[] stations = (Station[])stationHandler.find(stationKey);
            countStations = stations.length;

            // ステーションごとに出庫指示要求データを取得
            // CarryInfo検索用
            CarryInfoSearchKey carryKey = new CarryInfoSearchKey();

            for (int i = 0; i < countStations; i++)
            {
                // 副問い合わせ用キー
                // 同一パレットを対象としている搬送データのパレットを取得する
                // 開始以上の出庫の搬送データが1件以上あるか、同一パレットに対する入庫、直行のデータが存在する場合、
                // その搬送データは出庫指示送信不可能なデータとしてCarryInformationインスタンスの生成は行わない
                CarryInfoSearchKey carrySubKey = new CarryInfoSearchKey();
                carrySubKey.setPalletIdCollect();
                carrySubKey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL, "=", "(((", "", false);
                carrySubKey.setCarryFlag(CarryInfo.CARRY_FLAG_RACK_TO_RACK, "=", "", ")", true);
                carrySubKey.setCmdStatus(CarryInfo.CMD_STATUS_START, ">", "(", "))", false);
                carrySubKey.setCarryFlag(CarryInfo.CARRY_FLAG_STORAGE, "=", "", "", false);
                carrySubKey.setCarryFlag(CarryInfo.CARRY_FLAG_DIRECT_TRAVEL, "=", "", ")", true);
                carrySubKey.setPalletIdGroup();

                //<jp> ステーションごとに出庫指示要求データを取得</jp>
                //<en> Get data of retrieval instruciton request from each station</en>
                // 検索キーセット
                carryKey.clear();
                carryKey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL);
                carryKey.setCmdStatus(CarryInfo.CMD_STATUS_START);
                carryKey.setDestStationNo((String)stations[i].getStationNo());
                carryKey.setKey(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
                carryKey.setKey(Pallet.STATUS_FLAG, Pallet.PALLET_STATUS_RETRIEVAL_PLAN);
                carryKey.setKey(CarryInfo.PALLET_ID, carrySubKey, "!=", "", "", true);
                carryKey.setPriorityOrder(true);
                carryKey.setRegistDateOrder(true);
                carryKey.setCarryKeyOrder(true);

                // CarryInfo検索
                CarryInfo[] resultCarryArray = (CarryInfo[])_carryInfoHandler.find(carryKey);
                if (!ArrayUtil.isEmpty(resultCarryArray))
                {
                    // 出庫指示の送信を行う。
                    if (!sendCarry(resultCarryArray, (Station)stations[i]))
                    {
                        // ダブルディープ対応
                        // 棚間移動データが作成されていた場合は搬送指示を送信する。
                        // DoubleDeepRetrievalSender、AutomaticChangeSender両方から呼び出される可能性があるため
                        // 最初に呼んだ側がそのデータをロックし同一の棚間指示を送信しないようにする
                        CarryInfo[] carryMove = getRackMoveInfoForUpdate();
                        if (carryMove.length > 0)
                        {
                            // 出庫指示の送信を行う。
                            sendCarry(carryMove, null);
                        }
                        else
                        {
                            // 搬送データが無い時でもパレットと搬送情報をロックしてるので開放する。
                            _conn.rollback();
                        }
                    }
                    countRetrievaDataExist = 0;
                }
                else
                {
                    // ステーション毎の出庫指示データを探しに行ってデータが無かった回数をカウントする
                    countRetrievaDataExist = countRetrievaDataExist + 1;
                }
            }

            //<jp> スレッド終了</jp>
            //<en> Terminating thread</en>
            if (_exitFlag == true)
            {
                break;
            }

            //<jp> 搬送データはあるが送信可能な状態ではない場合は、少しSleep</jp>
            //<en> If there is carrying data but NOT able to send, let Sleep for a while.</en>
            synchronized (this)
            {
                this.wait(_existSleepTime);
            }
        }
    }

    // Accessor methods -----------------------------------------------
    /**<jp>
     * データベース接続用の<code>Connection</code>を取得します。
     * @return 現在保持している<code>Connection</code>
     </jp>*/
    /**<en>
     * Retrieve <code>Connection</code> to connect with database.
     * @return :<code>Connection</code> currently preserved
     </en>*/
    protected Connection getConnection()
    {
        return _conn;
    }

    /**<jp>
     * データベース接続用の<code>Connection</code>を設定します。
     * @param connection 設定するConnection
     </jp>*/
    /**<en>
     * Set <code>Connection</code> to connect with database
     * @param connection :Connection to set
     </en>*/
    protected void setConnection(Connection connection)
    {
        try
        {
            //<jp> コネクションチェックメソッド</jp>
            // <en> Connection Check Method</en>
            CheckConnection chk = new CheckConnection();

            //<jp> DBに再接続</jp>
            //<en> Reestablishes the connection with DB</en>
            if (true == chk.check(connection, _dbConCheckFlag))
            {
                throw new RuntimeException("Connection is null or closed!");
            }
            if (connection == null || connection.isClosed())
            {
                throw new RuntimeException("Connection is null or closed!");
            }
            _conn = connection;
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Can not access to database!");
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 指定されたCarryInformationインスタンス配列の条件チェックを行い、グループコントローラーに対して出庫指示を行います。
     * @param  chkArray    出庫または棚間移動搬送データの配列
     * @param  destSt      搬送先ステーション
     * @return true:text送信あり, false:text送信無し
     * @throws Exception   データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check the conditions of the specified intance array of CarryInformation, then give retrieval instruction
     * to the group controller.
     * @param  chkArray    array of retrirval data/location to location move
     * @param  destSt      receiving station
     * @return true:send text, false:not send text
     * @throws Exception   Notifies if trouble occurs in reading/writing in database.
     </en>*/
    protected boolean sendCarry(CarryInfo[] chkArray, Station destSt)
            throws Exception
    {
        CarryInfo[] sendArray = new CarryInfo[2];
        CarryInfo[] cInfoArray = null;

        try
        {
            //<jp> 取得したCarryInformationの配列に対して搬送先決定と条件チェックを行う。</jp>
            //<en> Designate the receiving station for acquired array of CarryInformation, then check the conditions.</en>
            cInfoArray = getSendCarryArray(chkArray, destSt);
            //<jp> 送信前にトランザクションを確定させる。作業場情報が更新されている可能性があるので</jp>
            //<jp> 搬送データが無くてもcommitを行う。</jp>
            //<en> Fix the transaction before transmission. As the workshop information may have been reniewed, ensure</en>
            //<en> to commit even if there is no carrying data.</en>
            _conn.commit();
            if (cInfoArray.length > 0)
            {
                //<jp> もらった出庫指示分、送信を繰り返す。</jp>
                //<jp> CarryInfomationから送信用電文を作成する。</jp>
                //<jp> 出庫指示は2件セット可能</jp>
                //<en> Repeat as many transmissions as the retrieve instructions provided.</en>
                //<en> Create the cmmunication messages to send from CarryInfomation.</en>
                //<en> Retrieve instructions can be put together in set of 2.</en>
                sendArray[0] = cInfoArray[0];
                if (cInfoArray.length >= SEND_MAX)
                {
                    // ダブルディープ対応
                    // 棚間移動時はdestStはNULLのため1件のみ送信
                    if (destSt != null)
                    {
                        System.out.println("Work no. of RetrievalSender ::: 2 cInfoArray[0] = "
                                + cInfoArray[0].getWorkNo());

                        // CarryInfo検索用
                        CarryInfoSearchKey carryKey = new CarryInfoSearchKey();

                        // 2010/05/11 Y.Osawa DEL ST
                        //                        // 検索キーセット
                        //                        carryKey.clear();
                        //                        String[] cmd = {
                        //                            CarryInfo.CMD_STATUS_INSTRUCTION,
                        //                            CarryInfo.CMD_STATUS_WAIT_RESPONSE,
                        //                            CarryInfo.CMD_STATUS_COMP_RETRIEVAL,
                        //                            CarryInfo.CMD_STATUS_ARRIVAL
                        //                        };
                        //                        carryKey.setCmdStatus(cmd, true);
                        //                        carryKey.setDestStationNo(destSt.getStationNo());
                        // 2010/05/11 Y.Osawa DEL ST
                        // 2010/05/11 Y.Osawa UPD ST
                        // 代表ステーションの時は紐づくステーションが使用可能かチェックする
                        // 使用可能なステーションの出庫指示可能数の合計が代表ステーションの出庫指示可能数より
                        // 少ない場合は使用可能なステーションの出庫指示可能数の合計まで指示を送信する
                        int maxQty = 0;
                        // 代表ステーション
                        if (Station.WORKPLACE_TYPE_MAIN_STATION.equals(destSt.getWorkplaceType()))
                        {
                            maxQty = getMaxPalletQtyOfMainSt(cInfoArray[0], destSt);
                        }
                        // 通常ステーション
                        else
                        {
                            maxQty = destSt.getMaxPalletQty();
                        }

                        // if (_carryInfoHandler.count(carryKey) + 1 >= destSt.getMaxPalletQty())
                        int qty = getRetrievalCount(destSt);
                        if (qty + 1 >= maxQty)
                        // 2010/05/11 Y.Osawa UPD ED
                        {
                            System.out.println("2nd data transmission will overrun the max. number of carry instruction allowable.");
                            sendArray[1] = null;
                        }
                        else
                        {
                            // 2010/05/11 Y.Osawa UPD ST
                            //                            AisleHandler asiHand = new AisleHandler(_conn);
                            //                            AisleSearchKey asiKey = new AisleSearchKey();
                            //
                            //                            asiKey.setStationNo(cInfoArray[0].getAisleStationNo());
                            //                            if (_carryInfoHandler.count(carryKey) + 1 >= asiHand.count(asiKey))
                            //                            {
                            //                                System.out.println("2nd data transmission will overrun the max. number of carry instruction allowable.");
                            //                                sendArray[1] = null;
                            //                            }
                            //                            else
                            //                            {
                            //                                sendArray[1] = cInfoArray[1];
                            //                                System.out.println("Work no. of RetrievalSender ::: 2 cInfoArray[1] = "
                            //                                        + cInfoArray[1].getWorkNo());
                            //                            }

                            if (StringUtil.isBlank(destSt.getAisleStationNo()))
                            {
                                // アイルごとに出庫搬送指示数を取得する

                                // 2010/05/11 Y.Osawa ADD ST
                                String[] cmd = {
                                    CarryInfo.CMD_STATUS_INSTRUCTION,
                                    CarryInfo.CMD_STATUS_WAIT_RESPONSE,
                                    CarryInfo.CMD_STATUS_COMP_RETRIEVAL,
                                    CarryInfo.CMD_STATUS_ARRIVAL
                                };
                                // 2010/05/11 Y.Osawa ADD ED
                                // 検索キーセット
                                carryKey.clear();
                                carryKey.setPalletIdCollect();
                                carryKey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL);
                                carryKey.setCmdStatus(cmd, true);
                                carryKey.setAisleStationNo(cInfoArray[0].getAisleStationNo());

                                int carryCount = getCarryInfoHandler().count(carryKey);

                                AisleSearchKey aisleKey = new AisleSearchKey();
                                AisleHandler aisleHand = new AisleHandler(_conn);
                                aisleKey.setStationNo(cInfoArray[0].getAisleStationNo());

                                Aisle aisle = (Aisle)aisleHand.findPrimary(aisleKey);
                                if (carryCount + 1 >= aisle.getMaxCarry())
                                {
                                    System.out.println("2nd data transmission will overrun the max. number of carry instruction allowable.");
                                    sendArray[1] = null;
                                }
                                else
                                {
                                    sendArray[1] = cInfoArray[1];
                                    System.out.println("Work no. of RetrievalSender ::: 2 cInfoArray[1] = "
                                            + cInfoArray[1].getWorkNo());
                                }
                            }
                            else
                            {
                                sendArray[1] = cInfoArray[1];
                                System.out.println("Work no. of RetrievalSender ::: 2 cInfoArray[1] = "
                                        + cInfoArray[1].getWorkNo());
                            }
                            // 2010/05/11 Y.Osawa UPD ED
                        }
                    }
                    else
                    {
                        sendArray[1] = null;
                    }
                }
                else
                {
                    sendArray[1] = null;
                    System.out.println("Work no. of RetrievalSender ::: 2 cInfoArray[0] = " + cInfoArray[0].getWorkNo());
                }

                //<jp> 出庫指示テキストの送信を行うため、StationからGroupControllerインスタンスを取得する。</jp>
                //<jp> 棚間移動の場合などは搬送先Stationがセットされないので、Palletから搬送先Stationを得る。</jp>
                //<en> Get the instance of GroupController from Station in order to send the text of retrieveal instruction.</en>
                //<en> In case of location to location moves, receiving station should be got from Pallet since the receiving</en>
                //<en> station has not been set for those moves.</en>

                Station sdestSt = destSt;
                if (sdestSt != null)
                {
                    sendText(sendArray, GroupController.getInstance(_conn, sdestSt.getControllerNo()));
                }
                else
                {
                    Station station = StationFactory.makeStation(getConnection(), sendArray[0].getAisleStationNo());
                    sendText(sendArray, GroupController.getInstance(_conn, station.getControllerNo()));
                }

                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e)
        {
            //<jp> commit時の例外</jp>
            //<en> Exception at the committment</en>
            Object[] tObj = {
                String.valueOf(e.getErrorCode()),
            };
            //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
            //<en> 6007030=Database error occured. error code={0}</en>
            RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
            throw new ReadWriteException(e);
        }
    }

    // 2010/05/11 Y.Osawa ADD ST
    /**
     * 代表ステーション用最大出庫指示可能数取得処理
     * @param  cInfo           搬送対象CarryInformation
     * @param  mainSt          代表ステーション
     * @return 代表ステーションに紐づく各ステーションが使用可能かチェックし、使用可能なステーションの最大出庫指示件数の
     *         合計を取得する。
     *         代表ステーションの最大出庫指示可能数より使用可能な各ステーションの最大出庫指示可能数の合計の方が大きければ
     *         代表ステーションの最大出庫指示可能数を返す。それ以外であれば使用可能なステーションの最大出庫指示件数の合計を返す。
     * @throws Exception 例外が発生した場合に通知されます。
     */
    protected int getMaxPalletQtyOfMainSt(CarryInfo cInfo, Station mainSt)
            throws Exception
    {
        // Pallet検索用
        PalletHandler palletHandelr = new PalletHandler(getConnection());
        PalletSearchKey palletKey = new PalletSearchKey();

        // 代表ステーションに紐づくステーションNo.を取得する
        StationSearchKey sskey = new StationSearchKey();
        StationHandler sth = new StationHandler(getConnection());
        sskey.setParentStationNo(mainSt.getStationNo());
        sskey.setStationNoOrder(true);
        Station[] stations = (Station[])sth.find(sskey);

        //<jp> 搬送先決定および搬送ルートチェック用パレット取得</jp>
        // 検索キーセット
        palletKey.clear();
        palletKey.setPalletId(cInfo.getPalletId());

        // 検索
        Pallet pallet = (Pallet)palletHandelr.findPrimary(palletKey);

        int maxQty = 0;

        RouteController rc = new RouteController(getConnection());

        boolean inventoryflag = false;
        if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals((cInfo.getRetrievalDetail())))
        {
            inventoryflag = true;
        }

        // 代表ステーションに紐づくステーションのチェックを行う
        for (Station destSt : stations)
        {
            //<jp> 搬送先決定および搬送ルートチェック</jp>
            //<en> Determination of the destination, transport route checking</en>
            if (!rc.retrievalDetermin(pallet, destSt, false, false, false, inventoryflag, false, false))
            {
                //<jp> 搬送ルートが無い</jp>
                //<en> Transport route cannot be found</en>
                continue;
            }

            //<jp> 搬送先ステーションの条件チェック</jp>
            //<en> Check the condition with the receiving station</en>
            if (!destRightStation(cInfo, destSt))
            {
                //<jp> 搬送先条件NG</jp>
                //<en> if conditions are not met at the receiving station</en>
                continue;
            }
            // 出庫指示可能数（ステーションの最大出庫指示可能数からそのステーションへの搬送数を引いた数）を取得します。
            maxQty = maxQty + getPalletMaxQty();
        }

        // 代表ステーションの最大出庫指示可能数より使用可能な各ステーションの最大出庫指示可能数の合計の方が大きければ
        // 代表ステーションの最大出庫指示可能数を返す
        if (mainSt.getMaxPalletQty() < maxQty)
        {
            return mainSt.getMaxPalletQty();
        }
        else
        {
            return maxQty;
        }
    }

    /**
     * 指定ステーションの出庫指示数取得処理
     * @param  destSt          指定ステーション
     * @return 指定ステーションに対する出庫指示数を返します。
     *         出庫指示から戻り入庫掬い完了までを出庫指示件数とします。但し、ユニット出庫は出庫完了以降のものを除きます。
     * @throws ReadWriteException DBアクセス時に例外が発生した場合に通知されます。
     */
    protected int getRetrievalCount(Station destSt)
            throws ReadWriteException
    {
        // CarryInfo検索用
        CarryInfoSearchKey carryKey = new CarryInfoSearchKey();

        // ダブルディープかチェックする
        StationController stControll = new StationController(getConnection(), getClass());
        boolean isDouble = stControll.isDoubleDeepConnectStation(destSt);

        // 検索キーセット
        carryKey.clear();

        // ダブルディープかつ入出庫兼用ステーションの場合は戻り入庫のすくい完了までを出庫指示件数としてカウントする
        if (isDouble && Station.STATION_TYPE_INOUT.equals(destSt.getStationType()))
        {
            carryKey.setCmdStatus(CarryInfo.CMD_STATUS_INSTRUCTION, "=", "((", "", false);
            carryKey.setCmdStatus(CarryInfo.CMD_STATUS_WAIT_RESPONSE, "=", "", "", false);
            carryKey.setCmdStatus(CarryInfo.CMD_STATUS_COMP_RETRIEVAL, "=", "((", "", false);
            carryKey.setCmdStatus(CarryInfo.CMD_STATUS_ARRIVAL, "=", "", ")", true);
            carryKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK, "=", "(", "", false);
            carryKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_PICKING, "=", "", "", false);
            carryKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING, "=", "", ")))", true);
            carryKey.setDestStationNo(destSt.getStationNo(), "=", "", ")", false);

            carryKey.setCmdStatus(CarryInfo.CMD_STATUS_INSTRUCTION, "=", "((", "", false);
            carryKey.setCmdStatus(CarryInfo.CMD_STATUS_WAIT_RESPONSE, "=", "", "", false);
            carryKey.setCmdStatus(CarryInfo.CMD_STATUS_ARRIVAL, "=", "", ")", true);
            carryKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK, "=", "(", "", false);
            carryKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING, "=", "", "", false);
            carryKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_PICKING, "=", "", ")", true);
            carryKey.setSourceStationNo(destSt.getStationNo(), "=", "", ")", false);
        }
        else
        {
            String[] cmd = {
                CarryInfo.CMD_STATUS_INSTRUCTION,
                CarryInfo.CMD_STATUS_WAIT_RESPONSE,
                CarryInfo.CMD_STATUS_COMP_RETRIEVAL,
                CarryInfo.CMD_STATUS_ARRIVAL
            };
            carryKey.setCmdStatus(cmd, true);
            carryKey.setDestStationNo(destSt.getStationNo());
        }

        int carryCount = _carryInfoHandler.count(carryKey);

        return carryCount;
    }
    // 2010/05/11 Y.Osawa ADD ED

    /**
     * 以下の条件で搬送情報を取得します。
     * <ol>
     * 取得項目
     * <li>搬送Key
     * <li>パレットID
     * </ol>
     * <ul>
     * 条件
     * <li>搬送区分 = CARRY_FLAG_RACK_TO_RACK
     * <li>搬送状態 = CMD_STATUS_START
     * <li>PalletID = Pallet.PalletID
     * </ul>
     * <ol>
     * ソート順
     * <li>搬送情報作成日時 昇順
     * </ol>
     *
     * @return 搬送情報
     * @throws LockTimeOutException ロックに失敗した場合に通知されます。
     * @throws ReadWriteException データベースに対する処理で発生した場合に通知します。
     */
    protected CarryInfo[] getRackMoveInfoForUpdate()
            throws ReadWriteException,
                LockTimeOutException
    {
        /*
         SELECT CARRYINFO.CARRYKEY, CARRYINFO.PALLETID
         FROM CARRYINFO, PALLET
         WHERE
         CARRYINFO.PALLETID = PALLET.PALLETID
         AND CARRYINFO.CARRYKIND = SystemDefine.CARRY_FLAG_RACK_TO_RACK
         AND CARRYINFO.CMDSTATUS = SystemDefine.CMD_STATUS_START
         ORDER BY CARRYINFO.CREATEDATE
         FOR UPDATE
         */

        CarryInfoHandler ciH = _carryInfoHandler;
        CarryInfoSearchKey ciKey = new CarryInfoSearchKey();

        // set pick up columns
        ciKey.setCarryKeyCollect();
        ciKey.setPalletIdCollect();
        // Addtional 2007/08/31
        ciKey.setWorkTypeCollect();
        ciKey.setGroupNoCollect();
        ciKey.setCmdStatusCollect();
        ciKey.setPriorityCollect();
        ciKey.setRestoringFlagCollect();
        ciKey.setCarryFlagCollect();
        ciKey.setRetrievalStationNoCollect();
        ciKey.setRetrievalDetailCollect();
        ciKey.setWorkNoCollect();
        ciKey.setSourceStationNoCollect();
        ciKey.setDestStationNoCollect();
        ciKey.setArrivalDateCollect();
        ciKey.setControlinfoCollect();
        ciKey.setCancelRequestCollect();
        ciKey.setCancelRequestDateCollect();
        ciKey.setScheduleNoCollect();
        ciKey.setAisleStationNoCollect();
        ciKey.setEndStationNoCollect();
        ciKey.setErrorCodeCollect();

        // set keys
        ciKey.setCarryFlag(SystemDefine.CARRY_FLAG_RACK_TO_RACK);
        ciKey.setCmdStatus(SystemDefine.CMD_STATUS_START);

        // set join
        ciKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);

        // set sort column
        ciKey.setRegistDateOrder(true);

        CarryInfo[] moveCarry = null;
        try
        {
            moveCarry = (CarryInfo[])ciH.findForUpdate(ciKey);
        }
        catch (LockTimeOutException e)
        {
            moveCarry = new CarryInfo[0];
        }

        return moveCarry;
    }

    /**<jp>
     * 受け取ったCarryInformationインスタンスの配列と搬送先ステーションに対して、搬送条件チェック処理を行ないます。
     * 搬送先が作業場などの送信可能ステーションではない場合は、搬送先決定処理を行い、CarryInformationの搬送先ステーションNoを
     * 決定したステーションに置き換えます。呼び出し元には搬送可能なCarryInformationの配列を返します。
     * 全てのCarryInformationが搬送出来ない場合、要素数0の配列を返します。
     * CarryInformation内のデータに不整合があった場合などは、該当のCarryInformationの状態を異常に変更します。
     * @param  carray      出庫要求CarryIformationの配列
     * @param  destSt      搬送先ステーション
     * @return             搬送可能なCarryInformationの一覧を返します。
     * @throws Exception   データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Process of checking the carrying conditions for the array of acquired instance of CarryInformation and for the
     * receiving station.
     * If data transmissions is not available at the destination of the load, e.g. workshop, etc. where there is no such
     * on-line environment, the receiving station must be newly designated in process. The no. of newly designated
     * receiving station of the CarryInformation should replace the former destination.
     * Then return the caller the array of feasible CarryInformation
     * If the carrying is NOT possible with all CarryInformation, return the array of element number0.
     * In case there is any inconsistency within CarryInformation data, change the state of applicable CarryInformation
     * to error.
     * @param  carry       Array of CarryIformation with retrieval requests
     * @param  destSt      receiving station
     * @return             Return the list of feasible CarryInformations
     * @throws Exception   Notifies if trouble occurs in reading/writing in database.
     </en>*/
    protected CarryInfo[] getSendCarryArray(CarryInfo[] carry, Station destSt)
            throws Exception
    {
        // Pallet検索用
        PalletHandler palletHandelr = new PalletHandler(_conn);
        PalletSearchKey palletKey = new PalletSearchKey();
        Pallet pallet = null;

        List<CarryInfo> carryList = new ArrayList<CarryInfo>();

        for (int i = 0; i < carry.length; i++)
        {
            //<jp> 今回出庫しようとする搬送データがユニット出庫の場合に限り</jp>
            //<jp> 同一パレットに対するCarryInformationが存在するかチェックする。</jp>
            //<jp> 搬送状態が開始でかつ、出庫指示詳細がピッキングまたは、在庫確認または、積増入庫のCarryInformationが存在する場合</jp>
            //<jp> 先にユニット出庫指示を行なうと、到着報告で払い出されてしまうため、出庫指示の対象としない。</jp>
            //<en> Only if the carry data of this retrieval specifies unit retrieval, check to see if the CarryInformation for</en>
            //<en> the same pallet ID exists. </en>
            //<en> If there is CarryInformation that carry status is "Start" and retrieval instruction detail is either</en>
            //<en> picking, inventory check or replenishment</en>
            //<en> If uniti retrieval is instructed first, stocks will be removed at arrival report. Therefore they will</en>
            //<en> not be included in target of retrieval instruction.</en>
            if (!CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(carry[i].getCarryFlag()))
            {
                if (CarryInfo.RETRIEVAL_DETAIL_UNIT.equals(carry[i].getRetrievalDetail()))
                {
                    CarryInfoSearchKey key = new CarryInfoSearchKey();
                    //<jp> 同一パレットIDで搬送状態が開始となっている搬送データでかつ、</jp>
                    //<jp> 出庫指示詳細が在庫確認、ピッキング、積増入庫のデータを探す</jp>
                    //<en> earch the carry data of same pallet IDs that the carry status is "Start" and its retrieval instruciton detail</en>
                    //<en> is either"Inventory check","Picking" or "Replenishment". </en>
                    key.setPalletId(carry[i].getPalletId());
                    key.setCmdStatus(CarryInfo.CMD_STATUS_START);
                    String[] details = {
                        CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK,
                        CarryInfo.RETRIEVAL_DETAIL_PICKING,
                        CarryInfo.RETRIEVAL_DETAIL_ADD_STORING,
                    };
                    key.setRetrievalDetail(details, true);

                    if (_carryInfoHandler.count(key) > 0)
                    {
                        //<jp> CarryInformationが存在する場合、この搬送データは今回の出庫指示対象としない。</jp>
                        //<en> In case the CarryInformation exists, the carry data will not be included in target of this retrieval instruction.</en>
                        continue;
                    }
                }
                // パレットに紐づく自分より小さい搬送キーの開始データを検索
                CarryInfoSearchKey key = new CarryInfoSearchKey();
                key.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL);
                key.setCmdStatus(CarryInfo.CMD_STATUS_START);
                key.setPalletId(carry[i].getPalletId());
                key.setCarryKey(carry[i].getCarryKey(), "<");
                if (_carryInfoHandler.count(key) > 0)
                {
                    // 存在した場合、AutomaticModeChangeSender出庫対象の搬送データで処理中の場合を考慮し、今回の出庫指示対象としない
                    continue;
                }
            }

            //<jp> 出庫先決定処理</jp>
            //<en> Determination of the destination</en>
            if (!destDetermin(carry[i], destSt))
            {
                //<jp> 同一ステーションに対する出庫指示送信可能チェックなので、</jp>
                //<jp> NGの時点で終了する。</jp>
                //<en> It terminates as any inadequacy found; this is to check the conditions for sending retrieval instruction</en>
                //<en> to the same station.</en>
                break;
            }

            if (!StringUtil.isBlank(destSt))
            {
                if (StringUtil.isBlank(destSt.getAisleStationNo()))
                {
                    // アイルごとに出庫搬送指示数を取得する
                    // CarryInfo検索用
                    CarryInfoSearchKey carryKey = new CarryInfoSearchKey();

                    // 検索キーセット
                    carryKey.clear();
                    carryKey.setPalletIdCollect();
                    // 2010/05/11 Y.Osawa ADD ST
                    carryKey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL);
                    // 2010/05/11 Y.Osawa ADD ED
                    String[] cmd = {
                        CarryInfo.CMD_STATUS_INSTRUCTION,
                        CarryInfo.CMD_STATUS_WAIT_RESPONSE,
                        CarryInfo.CMD_STATUS_COMP_RETRIEVAL,
                        CarryInfo.CMD_STATUS_ARRIVAL
                    };
                    carryKey.setCmdStatus(cmd, true);
                    // 2010/05/11 Y.Osawa DEL ST
                    //                    carryKey.setDestStationNo(destSt.getStationNo());
                    // 2010/05/11 Y.Osawa DEL ED
                    carryKey.setAisleStationNo(carry[i].getAisleStationNo());

                    int carryCount = _carryInfoHandler.count(carryKey);

                    AisleSearchKey aisleKey = new AisleSearchKey();
                    AisleHandler aisleHand = new AisleHandler(_conn);
                    aisleKey.setStationNo(carry[i].getAisleStationNo());

                    Aisle aisle = (Aisle)aisleHand.findPrimary(aisleKey);
                    if (aisle.getMaxCarry() <= carryCount)
                    {
                        continue;
                    }
                }
            }
            else
            {
                Station aisle = StationFactory.makeStation(_conn, carry[i].getAisleStationNo());
                // アイルステーションが使用可能でシステム状態がオンラインの棚間移動のみ送信
                if (!Station.STATION_STATUS_NORMAL.equals(aisle.getStatus()))
                {
                    break;
                }
            }
            // 引当られた棚がアクセス不可棚になっていた場合
            ShelfSearchKey shs = new ShelfSearchKey();
            ShelfHandler shsH = new ShelfHandler(_conn);

            shs.clear();
            shs.setStationNo(carry[i].getRetrievalStationNo());
            shs.setAccessNgFlag(SystemDefine.ACCESS_NG_FLAG_NG);

            if (shsH.count(shs) > 0)
            {
                // 今回の出庫指示対象としない。
                continue;
            }

            try
            {
                //<jp> 引き当てられたパレットの位置が棚ではないとき。</jp>
                //<en> If allocated pallet was not on teh shelf,</en>

                // 検索キーセット
                palletKey.clear();
                palletKey.setPalletId(carry[i].getPalletId());

                // 検索
                pallet = (Pallet)palletHandelr.findPrimary(palletKey);

                if (!(StationFactory.makeStation(_conn, pallet.getCurrentStationNo()) instanceof Shelf))
                {
                    System.out.println("Located other place than shelves.");
                    continue;
                }
            }
            catch (InvalidDefineException e)
            {
                carryFailure(carry[i]);
                continue;
            }
            catch (NotFoundException e)
            {
                carryFailure(carry[i]);
                continue;
            }

            carryList.add(carry[i]);
            if ((carryList.size() >= SEND_MAX) || (carryList.size() >= getInstructionMax()))
            {
                System.out.println("getInstructionMax() <" + getInstructionMax() + ">");
                break;
            }
        }

        //<jp> 条件をパスしたCarryInformationだけを返す為に、その分の領域を確保。</jp>
        //<en> In order to only return CarryInformation which met the conditions, secure the area of that portion.</en>
        CarryInfo[] rCarryinfoArray = (CarryInfo[])carryList.toArray(new CarryInfo[0]);

        return rCarryinfoArray;
    }

    /**<jp>
     * 出庫指示の条件チェックを行います。現在指示済みの搬送データ件数と搬送可能件数との比較を行い、搬送可能件数以下ならばtrue
     * 搬送可能件数を超えていればfalseを返します。
     * @param  cInfo           搬送対象CarryInformation
     * @param  destSt          搬送先ステーション
     * @return                 搬送可能ならばtrue、搬送できない場合はfalseを返します。
     * @throws Exception       データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check the conditions of retireval instructions. Compare the number of carry data which instructions already
     * released with the MAX. number of carry feasible; if the number of the former has not reached the latter,
     * it returns 'true'.
     * It returns 'false' if the former exceeded the MAX. number of carrys feasible.
     * @param  cInfo           CarryInformation
     * @param  destSt          receiving station
     * @return                 return 'true' if the carry is feasible; 'false' if it is not.
     * @throws Exception       Notifies if trouble occurs in reading/writing in database.
     </en>*/
    protected boolean destDetermin(CarryInfo cInfo, Station destSt)
            throws Exception
    {
        try
        {
            //<jp> 搬送先決定および搬送ルートチェック</jp>
            //<en> Determination of the destination, transport route checking</en>
            // Pallet検索用
            PalletHandler palletHandelr = new PalletHandler(_conn);
            PalletSearchKey palletKey = new PalletSearchKey();
            Pallet pallet = null;

            // 検索キーセット
            palletKey.clear();
            palletKey.setPalletId(cInfo.getPalletId());

            // 検索
            pallet = (Pallet)palletHandelr.findPrimary(palletKey);

            // 2010/05/11 Y.Osawa ADD ST
            // 代表ステーションかつ在庫確認時は在庫確認中チェックを行わない
            boolean routeOK = false;
            if (Station.WORKPLACE_TYPE_MAIN_STATION.equals(destSt.getWorkplaceType())
                    && CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(cInfo.getRetrievalDetail()))
            {
                routeOK = _routeController.retrievalDetermin(pallet, destSt, true, false, false, true);
            }
            else
            {
                routeOK = _routeController.retrievalDetermin(pallet, destSt);
            }
            // 2010/05/11 Y.Osawa ADD ED

            // 2010/05/11 Y.Osawa UPD ST
            //if (!_routeController.retrievalDetermin(pallet, destSt))
            if (!routeOK)
            // 2010/05/11 Y.Osawa UPD ST
            {
                //<jp> 搬送ルートが無い</jp>
                //<en> Transport route cannot be found</en>
                return false;
            }

            //<jp> 搬送先ステーションの条件チェック</jp>
            //<en> Check the condition with the receiving station</en>
            if (!destRightStation(cInfo, destSt))
            {
                //<jp> 搬送先条件NG</jp>
                //<en> if conditions are not met at the receiving station</en>
                return false;
            }

            // フリーアロケーション倉庫の場合、搬送情報の制御情報を更新する
            AreaController areaCtlr = new AreaController(getConnection(), getClass());
            if (WareHouse.FREE_ALLOCATION_ON.equals(areaCtlr.getFreeAllocationOfWarehouse(pallet.getWhStationNo())))
            {
                // 棚情報、荷姿マスタから荷幅、荷長、荷高を取得する
                ShelfSearchKey sskey = new ShelfSearchKey();
                ShelfHandler sHandler = new ShelfHandler(getConnection());
                sskey.clear();
                sskey.setStationNo(pallet.getCurrentStationNo());
                // AS/RS棚情報検索
                Shelf shelf = (Shelf)sHandler.findPrimary(sskey);

                LoadSizeSearchKey lskey = new LoadSizeSearchKey();
                LoadSizeHandler lHandler = new LoadSizeHandler(getConnection());
                lskey.clear();
                lskey.setJoin(LoadSize.LOAD_SIZE, HardZone.HEIGHT);
                lskey.setKey(HardZone.HARD_ZONE_ID, shelf.getHardZoneId());
                // 荷姿マスタ情報検索
                LoadSize loadsize = (LoadSize)lHandler.findPrimary(lskey);
                if (loadsize == null)
                {
                    // 6026601=指定されたハードゾーンの荷高が荷姿マスタの荷姿にありません。ハードゾーンID:{0}
                    Object[] tObj = new Object[1];
                    tObj[0] = shelf.getHardZoneId();
                    RmiMsgLogClient.write(6022036, this.getClass().getSimpleName(), tObj);
                    return false;
                }

                // 制御情報作成
                ControlInfo conInfo = new ControlInfo();
                conInfo.setWidth(shelf.getWidth());
                conInfo.setLength(loadsize.getLength());
                conInfo.setHeight(loadsize.getHeight());
                String controlInfo = conInfo.convertControlInfo(conInfo);

                // CarryInfo更新用
                CarryInfoAlterKey carryAlterKey = new CarryInfoAlterKey();
                // 検索キーセット
                carryAlterKey.clear();
                carryAlterKey.setCarryKey(cInfo.getCarryKey());
                carryAlterKey.updateControlinfo(controlInfo);
                carryAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                // 更新
                getCarryInfoHandler().modify(carryAlterKey);

                // Entityも更新する
                cInfo.setControlinfo(controlInfo);
            }

            return true;
        }
        //<jp> 指定されたステーションNoが不正な場合に通知されます。</jp>
        //<en> Notifies if specified station no. is invalid.</en>
        catch (InvalidDefineException e)
        {
            carryFailure(cInfo);
            return false;
        }
    }

    /**<jp>
     * 搬送先ステーションの状態をチェックします。
     * 搬送先Stationが所属する設備コントローラー(AGC)がオンライン
     * 搬送先Stationが作業モード切替要求中ではない
     * 作業モード管理を行う場合、入出庫兼用ステーションなら搬送先Stationの作業モードが出庫
     * 搬送先Stationが中断中ではない
     * 出庫指示可能件数については搬送先ステーションに関連するCarryInformationが規定数を越えていないかをCheckする。
     * 該当ステーションが搬送元になっているCarryInformationについては考慮しない。
     * @param  cInfo       搬送情報(CarryInformation)
     * @param  destSt      搬送先ステーション
     * @return             搬送可能と確認できた場合はtrue、ダメな場合がfalse
     * @throws Exception   データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check th status of the receiving station.
     * Facility controller(AGC) , that the receiving station belongs to, has connection on-line.
     * The receiving station is not requesting for the switch of the work mode.
     * In case the work mode is in control: If the receiving station handles both storage/retrieval,
     * the work mode of the receiving station is 'retrieval'
     * Operation of receiving station is not suspended.
     * As for the feasible number of retrieval instructions, check to see if CarryInformation relevant to this.
     * receiving station exceeded the volume regulated.
     * No consideration is necessary if this station was the sending station of CarryInformation.
     * @param  cInfo       CarryInformation
     * @param  destSt      receiving station
     * @return             returns 'true' if the carry is feasible or 'false' if not.
     * @throws Exception   Notifies if trouble occurs in reading/writing in database.
     </en>*/
    protected boolean destRightStation(CarryInfo cInfo, Station destSt)
            throws Exception
    {
        //<jp> 搬送先ステーションが属するグループコントローラーがオンライン以外の場合は搬送不可</jp>
        //<en> If the group controller that the receiving station belongs to has no connection on-line, the carry </en>
        //<en> cannot carried out.</en>
        GroupController groupControll = GroupController.getInstance(_conn, destSt.getControllerNo());
        if (groupControll.getStatus() != GroupController.STATUS_ONLINE)
        {
            return false;
        }

        //<jp> 搬送先ステーションの作業モード確認</jp>
        //<jp> 搬送先Stationが入出庫兼用ならばモードの確認が必要。入庫専用ステーションかモード管理無しならばモードは確認しない。</jp>
        //<en> Check the work mode of the receiving station.</en>
        //<en> If this receiving station handles both storage and retrieval, its work mode needs to be checked.</en>
        //<en> If this station only handles storage, or if no work mode control is done, checking is unnecessary. </en>
        if (Station.STATION_TYPE_INOUT.equals(destSt.getStationType())
                && !Station.MODE_TYPE_NONE.equals(destSt.getModeType()))
        {
            //<jp> 搬送先ステーションが以下のいずれかの状態であれば、即出庫は行われないと判断する。</jp>
            //<jp> 作業モード切替要求が入庫または出庫モード切替要求中</jp>
            //<jp> 作業モードがニュートラルまたは入庫モード</jp>
            //<en> If condition of this receiving station applies to any of the following, determines that immediate </en>
            //<en> retrieval will not be made.</en>
            //<en> Requesting for the work mode to switch to 'storage' or 'retrieval'</en>
            //<en> Work mode is in 'neutral' or 'storage'</en>
            if ((!Station.MODE_REQUEST_NONE.equals(destSt.getModeRequest()))
                    || (Station.CURRENT_MODE_NEUTRAL.equals(destSt.getCurrentMode()))
                    || (Station.CURRENT_MODE_STORAGE.equals(destSt.getCurrentMode())))
            {
                return false;
            }
        }

        //<jp> 搬送先ステーションの中断中フラグを確認。</jp>
        //<en> Check the suspention flag of the receiving station.</en>
        if (Station.SUSPEND_ON.equals(destSt.getSuspend()))
        {
            //<jp> 中断中ならば即出庫は行われないと判断する。</jp>
            //<en> If the operation is suspended, it determines that immediate retrieval will not be made.</en>
            System.out.println("In RetrievalSender destRightStation, if the suspention flag of receiving station states 'invalid': StationNo.= "
                    + destSt.getStationNo());
            return false;
        }
        System.out.println("In RetrievalSender destRightStation, if the suspention flag of receiving station states 'valid': StationNo.= "
                + destSt.getStationNo());

        //<jp> 出庫指示可能件数のチェック(搬送先ステーションに関連するCarryInformationが規定数を越えていないか)</jp>
        //<en> Check the MAX. number of retrieval instructions acceptable ( whether/not CarryInformation relevant to the receiving</en>
        //<en> station exceeded the regulated volume)</en>

        // 2010/05/11 Y.Osawa DEL ST
        //        // ステーションごとに出庫指示要求データを取得
        //        // CarryInfo検索用
        //        CarryInfoSearchKey carryKey = new CarryInfoSearchKey();
        //
        //        // 検索キーセット
        //        carryKey.clear();
        //        carryKey.setPalletIdCollect();
        //        String[] cmd = {
        //            CarryInfo.CMD_STATUS_INSTRUCTION,
        //            CarryInfo.CMD_STATUS_WAIT_RESPONSE,
        //            CarryInfo.CMD_STATUS_COMP_RETRIEVAL,
        //            CarryInfo.CMD_STATUS_ARRIVAL
        //        };
        //        carryKey.setCmdStatus(cmd, true);
        //        carryKey.setDestStationNo(destSt.getStationNo());
        //
        //        int carryCount = _carryInfoHandler.count(carryKey);
        // 2010/05/11 Y.Osawa DEL ED

        // 2010/05/11 Y.Osawa UPD ST
        // 代表ステーションの時は紐づくステーションが使用可能かチェックする
        // 使用可能なステーションの出庫指示可能数の合計が代表ステーションの出庫指示可能数より
        // 少ない場合は使用可能なステーションの出庫指示可能数の合計まで指示を送信する
        int maxQty = 0;
        // 代表ステーション
        if (Station.WORKPLACE_TYPE_MAIN_STATION.equals(destSt.getWorkplaceType()))
        {
            maxQty = getMaxPalletQtyOfMainSt(cInfo, destSt);
        }
        // 通常ステーション
        else
        {
            maxQty = destSt.getMaxPalletQty();
        }

        // if (destSt.getMaxPalletQty() <= carryCount)
        int carryCount = getRetrievalCount(destSt);
        if (maxQty <= carryCount)
        // 2010/05/11 Y.Osawa UPD ED
        {
            //<jp> 出庫指示可能数をオーバーしている。</jp>
            //<en> The number of retrieval instruction exceeded the available number set by the regulation</en>
            System.out.println("RetrievalSender ::: Exceeding the MAX. number of retrieval instructions  ");
            return false;
        }

        // 2010/05/11 Y.Osawa ADD ST
        // 出庫指示可能数（ステーションの最大出庫指示可能数からそのステーションへの搬送数を引いた数）をセットします。
        setPalletMaxQty(destSt.getMaxPalletQty() - carryCount);
        // 2010/05/11 Y.Osawa ADD ED

        return true;
    }

    /**<jp>
     * 出庫指示テキストの編集を行い、出庫指示を送信します。
     * 出庫指示テキストの送信はRMIを経由して行われます。正常に行われた場合はCarryInformationの搬送状態を応答待ちに変更します。
     * @param  cInfoArray      搬送対象となるCarryInformation配列
     * @param  gc              搬送指示テキスト送信対象となるGroupControllerインスタンス
     * @throws Exception       データベースの読書きで障害が発生した場合に通知されます。
     * @throws Exception       送信タスクに対するテキスト送信で障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Edit the text of retrieval instructions and send it.
     * The transmission of retrieval instruction text will sent via RMI. Normally, it alters the carry state of this
     * CarryInformation to 'wait for reply'.
     * @param  cInfoArray      Array of CarryInformation
     * @param  gc              instance of GroupController which the carry instructions text sends to
     * @throws Exception       Notifies if trouble occurs in reading/writing in database.
     * @throws Exception       It will be notified if trouble occurred in sending text to send task.
     </en>*/
    protected void sendText(CarryInfo[] cInfoArray, GroupController gc)
            throws Exception
    {
        Object[] param = new Object[2];

        DecimalFormat fmt = new DecimalFormat(DOUBLE_ZERO);

        try
        {
            //<jp> 出庫指示送信テキストの編集</jp>
            //<en> Edit the sending text of the retrieval instruction</en>
            As21Id12 id12 = new As21Id12(cInfoArray, _conn);
            String sendMsg = id12.getSendMessage();

            //<jp> RMIを利用してAs21Senderのwriteメソッドをコール</jp>
            //<en> Call the write method of AS21Sender using RMI</en>
            RmiSendClient rmiSndC = null;
            if (WmsParam.MULTI_ASRSSERVER)
            {
                rmiSndC = new RmiSendClient(RmiSendClient.RMI_REG_SERVER + gc.getNo());
            }
            else
            {
                rmiSndC = new RmiSendClient();
            }

            param[0] = sendMsg;

            //<jp> 出庫指示可能なCarryInformationの状態を応答待ちに更新し、</jp>
            //<jp> パレットの状態を出庫中にする。</jp>
            //<en> Update the status of CarryInstruction that is possible for retrieval instruction to "wait for response",</en>
            //<en> then change the pallet status to "retrieval in progress".</en>
            // CarryInfo更新用
            CarryInfoHandler carryInfoHandler = new CarryInfoHandler(_conn);
            CarryInfoAlterKey carryAlterKey = new CarryInfoAlterKey();

            // Pallet更新用
            PalletHandler palletHandelr = new PalletHandler(_conn);
            PalletAlterKey palletAlterKey = new PalletAlterKey();

            for (int i = 0; i < cInfoArray.length; i++)
            {
                if (cInfoArray[i] != null)
                {
                    //<jp> 出庫指示可能なCarryInformationの場合、応答待ちに更新</jp>
                    //<en> Update the status of CarryInstruction if it is possible for retrieval instruction</en>
                    // 検索キーセット
                    carryAlterKey.clear();
                    carryAlterKey.setCarryKey(cInfoArray[i].getCarryKey());
                    carryAlterKey.updateCmdStatus(CarryInfo.CMD_STATUS_WAIT_RESPONSE);
                    carryAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                    // 更新
                    carryInfoHandler.modify(carryAlterKey);

                    //<jp> パレットを出庫中にする。</jp>
                    //<en> Set the pallet status to "retrieval in progress".</en>
                    // 検索キーセット
                    palletAlterKey.clear();
                    palletAlterKey.setPalletId(cInfoArray[i].getPalletId());
                    palletAlterKey.updateStatusFlag(Pallet.PALLET_STATUS_RETRIEVAL);
                    palletAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                    // 更新
                    palletHandelr.modify(palletAlterKey);
                }
            }
            //<jp> テキスト送信前にトランザクションを確定させる。</jp>
            //<en>Commit the transaction before sending the text.</en>
            _conn.commit();

            //<jp> 送信すべきAGCを決定</jp>
            //<en> Select AGC to send </en>
            rmiSndC.write("AGC" + fmt.format(Integer.valueOf(gc.getNo())), param);
            rmiSndC = null;
        }
        // 2010/07/30 Y.Osawa ADD ST
        catch (NotBoundException e)
        {
            for (int i = 0; i < cInfoArray.length; i++)
            {
                if(null == cInfoArray[i])
                {
                    break;
                }
                //<jp> 6026615=AGCとの接続が確立されていません。搬送要求テキストを送信できませんでした。mckey={0}</jp>
                //<en> 6026615=Cannot send the transfer request text since SRC is not connected. mckey={0}</en>
                Object[] objAgc = new Object[1];
                objAgc[0] = cInfoArray[i].getCarryKey();
                RmiMsgLogClient.write(6026615, LogMessage.F_ERROR, this.getClass().getName(), objAgc);

//                carryFailure(cInfoArray[i]);
                carryRetry(cInfoArray[i]);
            }
        }
        catch (IOException e)
        {
            for (int i = 0; i < cInfoArray.length; i++)
            {
                if(null == cInfoArray[i])
                {
                    break;
                }
                //<jp> 通信上にて電文送出できず</jp>
                //<jp> 6026616=テキストを送信できませんでした。mckey={0}</jp>
                //<en> Communication message could not be sent out.</en>
                //<en> 6026616=Cannot send the text. mckey={0}</en>
                Object[] objAgc = new Object[1];
                objAgc[0] = cInfoArray[i].getCarryKey();
                RmiMsgLogClient.write(new TraceHandler(6026616, e), this.getClass().getName(), objAgc);

//                carryFailure(cInfoArray[i]);
                carryRetry(cInfoArray[i]);
            }
        }
        // 2010/07/30 Y.Osawa ADD ED
        //<jp> データ更新内容に誤りがあった場合に発生する。</jp>
        //<en> Occurs if there are any contents error in updated data.</en>
        catch (InvalidStatusException e)
        {
            for (int i = 0; i < cInfoArray.length; i++)
            {
                carryFailure(cInfoArray[i]);
            }
        }
        catch (InvalidProtocolException e)
        {
            //<jp> 6026045=出庫指示タスクで異常が発生しました。例外：{0}</jp>
            //<en> 6026045=Error occurred in picking instruction task. Exception:{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6026045, e), this.getClass().getName());

            for (int i = 0; i < cInfoArray.length; i++)
            {
                carryFailure(cInfoArray[i]);
            }
        }
        //<jp> トランザクション確定時に障害があった場合に発生する。</jp>
        //<en> Occurs if trouble occured when fixing the transaction.</en>
        catch (SQLException e)
        {
            Object[] tObj = {
                String.valueOf(e.getErrorCode()),
            };
            //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
            //<en> 6007030=Database error occured. error code={0}</en>
            RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
            throw new ReadWriteException(e);
        }
    }

    /**<jp>
     * 指定されたCarryInforamtionインスタンスの搬送状態を異常に変更します。
     * 異常に変更されたCarryInformationは以後搬送対象となりません。
     * また、更新内容を反映させるために、このメソッド内でトランザクションを確定します。
     * @param  failureTarget   搬送対象CarryInformationインスタンス
     * @throws Exception       データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Alters the carry state of specified instance of CarryInforamtion to 'error'.
     * Once the state changes to error, this CarryInformation will not be used for carry any longer.
     * Also in order to mirror the updates, fix the transaction within this method.
     * @param  failureTarget    Instance of CarryInformation for the carry
     * @throws Exception        : Notifies if trouble occured in reading/writing in database.
     </en>*/
    protected void carryFailure(CarryInfo failureTarget)
            throws Exception
    {
        try
        {
            if (failureTarget != null)
            {
                //<jp> 現在までの更新内容をrollback。</jp>
                //<en> Rolls back the contents of update made up to this moment.</en>
                _conn.rollback();
                //<jp> 状態を異常に更新</jp>
                //<en> Update the status to error</en>

                // CarryInfo更新用
                CarryInfoHandler carryInfoHandler = new CarryInfoHandler(_conn);
                CarryInfoAlterKey carryAlterKey = new CarryInfoAlterKey();
                // 検索キーセット
                carryAlterKey.clear();
                carryAlterKey.setCarryKey(failureTarget.getCarryKey());
                carryAlterKey.updateCmdStatus(CarryInfo.CMD_STATUS_ERROR);
                carryAlterKey.updateLastUpdatePname(getClass().getSimpleName());

                // 更新
                carryInfoHandler.modify(carryAlterKey);

                //<jp> トランザクションを確定する。</jp>
                //<en> Fix the transaction</en>
                _conn.commit();
            }
        }
        catch (SQLException e)
        {
            Object[] tObj = {
                String.valueOf(e.getErrorCode()),
            };
            //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
            //<en> 6007030=Database error occured. error code={0}</en>
            RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
            throw new ReadWriteException(e);
        }
    }

    /**<jp>
     * 指定されたCarryInforamtionインスタンスの搬送状態を未指示に変更します。
     * 未指示に変更されたCarryInformationは再度搬送対象となします。
     * 指示送信時に受信側が確立されていない場合に再度指示を出すため未指示に戻します。
     * @param  failureTarget   搬送対象CarryInformationインスタンス
     * @throws Exception       データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Alters the carry state of specified instance of CarryInforamtion to 'error'.
     * Once the state changes to error, this CarryInformation will not be used for carry any longer.
     * Also in order to mirror the updates, fix the transaction within this method.
     * @param  failureTarget    Instance of CarryInformation for the carry
     * @throws Exception        : Notifies if trouble occured in reading/writing in database.
     </en>*/
    protected void carryRetry(CarryInfo retryTarget)
            throws Exception
    {
        try
        {
            if (retryTarget != null)
            {
                //<jp> 現在までの更新内容をrollback。</jp>
                //<en> Rolls back the contents of update made up to this moment.</en>
                _conn.rollback();
                //<jp> 状態を開始に更新</jp>
                //<en> Update the status to error</en>

                // CarryInfo更新用
                CarryInfoHandler carryInfoHandler = new CarryInfoHandler(_conn);
                CarryInfoAlterKey carryAlterKey = new CarryInfoAlterKey();
                // 検索キーセット
                carryAlterKey.clear();
                carryAlterKey.setCarryKey(retryTarget.getCarryKey());
                carryAlterKey.updateCmdStatus(CarryInfo.CMD_STATUS_START);
                carryAlterKey.updateLastUpdatePname(getClass().getSimpleName());

                // 更新
                carryInfoHandler.modify(carryAlterKey);

                PalletAlterKey pAkey = new PalletAlterKey();
                PalletHandler pHandler = new PalletHandler(_conn);
                pAkey.clear();
                pAkey.setPalletId(retryTarget.getPalletId());
                pAkey.updateStatusFlag(Pallet.PALLET_STATUS_RETRIEVAL_PLAN);
                pAkey.updateLastUpdatePname(getClass().getSimpleName());
                pHandler.modify(pAkey);


                //<jp> トランザクションを確定する。</jp>
                //<en> Fix the transaction</en>
                _conn.commit();
            }
        }
        catch (SQLException e)
        {
            Object[] tObj = {
                String.valueOf(e.getErrorCode()),
            };
            //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
            //<en> 6007030=Database error occured. error code={0}</en>
            RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
            throw new ReadWriteException(e);
        }
    }


    /**<jp>
     * 要求フラグを要求中にします。
     * 要求フラグが要求中の場合、出庫指示処理はwait状態にはなりません。
     </jp>*/
    /**<en>
     * Alter the request flag to 'requesting'.
     * If the flag says 'requesting', the retrieval instruction will not shift to wait state.
     </en>*/
    protected void setRequest()
    {
        _request = true;
    }

    /**<jp>
     * 要求フラグを未要求にします。
     * 要求フラグが未要求の場合、出庫指示処理はwait状態となります。
     </jp>*/
    /**<en>
     * Change the reqest flag to 'no request'.
     * If the flag states 'no request', retrieval instruciton should shift to wait state.
     </en>*/
    protected void restRequest()
    {
        _request = false;
    }

    /**<jp>
     * 要求フラグの現在の状態を返します。
     * 要求フラグが要求中の場合はtrue、そうでなければfalseを返します。
     * @return 要求フラグが要求中の場合はtrue、そうでなければfalse
     </jp>*/
    /**<en>
     * Return the current status of the request flag.
     * If this flag is 'requesting', it returns 'true' or 'false' if not.
     * @return if request flag is 'requesting', 'true' or 'flkse' if not
     </en>*/
    protected boolean isRequest()
    {
        return _request;
    }

    /* ADD Start 2006.10.10 E.Takeda */
    /**
     * このクラスで保持しているコネクションをクローズします。
     * 2006.10.03
     * @author E.Takeda
     */
    private void closeConnection()
    {
        try
        {
            // 2009/07/28 K.Mori ADD
            //<jp> データベースのコネクションが有効であればロールバックを行なう。</jp>
            //<en> Rolls back if the connection with database is valid.</en>
            if (_conn != null)
            {
                /* ADD Start 2006.10.23 E.Takeda */
                _conn.rollback();
                _conn.close();
            }
            // 2009/07/28 ADD END
        }
        catch (SQLException e)
        {
            // do nothing.
        }

        _conn = null;
//        //<jp> 6020017=出庫指示送信処理を停止します。</jp>
//        //<en> 6020017=Terminating picking instruction sending process.</en>
//        RmiMsgLogClient.write(6020017, OBJECT_NAME);
    }

    /* ADD End 2006.10.10 */

    /**
     * carryInfoHandlerを返します。
     * @return carryInfoHandlerを返します。
     */
    protected CarryInfoHandler getCarryInfoHandler()
    {
        return _carryInfoHandler;
    }

    /**
     * routeControllerを返します。
     * @return routeControllerを返します。
     */
    protected RouteController getRouteController()
    {
        return _routeController;
    }

    /**
     * stationHandlerを返します。
     * @return stationHandlerを返します。
     */
    protected StationHandler getStationHandler()
    {
        return _stationHandler;
    }

    /**
     * 出庫指示テキスト内に一度に指示出来る件数をセットします。
     * @param i 一度に指示出来る件数
     */
    protected void setInstructionMax(int i)
    {
        _instructionMax = i;
    }

    /**
     * 出庫指示テキスト内に一度に指示出来る件数を返します。
     * @return _instructionMaxを返します。
     */
    protected int getInstructionMax()
    {
        return _instructionMax;
    }

    // 2010/05/11 Y.Osawa ADD ST
    /**
     * 出庫指示可能数（ステーションの最大出庫指示可能数からそのステーションへの搬送数を引いた数）をセットします。
     * @param i 出庫指示可能数
     */
    protected void setPalletMaxQty(int i)
    {
        _palletMaxQty = i;
    }

    /**
     * 出庫指示可能数（ステーションの最大出庫指示可能数からそのステーションへの搬送数を引いた数）を返します。
     * @return _palletMaxQtyを返します。
     */
    protected int getPalletMaxQty()
    {
        return _palletMaxQty;
    }
    // 2010/05/11 Y.Osawa ADD ED

    // Private methods -----------------------------------------------
}
//end of class
