// $Id: StorageSender.java 8062 2013-05-27 03:53:08Z kishimoto $
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

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.rmi.RmiServAbstImpl;
import jp.co.daifuku.wms.asrs.location.FreeAllocationShelfOperator;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.AsStockController;
import jp.co.daifuku.wms.base.controller.AsWorkInfoController;
import jp.co.daifuku.wms.base.controller.CarryInfoController;
import jp.co.daifuku.wms.base.controller.HostSendController;
import jp.co.daifuku.wms.base.controller.StationController;
import jp.co.daifuku.wms.base.dbhandler.ArrivalAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ArrivalHandler;
import jp.co.daifuku.wms.base.dbhandler.ArrivalSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HardZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.LoadSizeHandler;
import jp.co.daifuku.wms.base.dbhandler.LoadSizeSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Arrival;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.base.entity.LoadSize;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.util.CheckConnection;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.SysDate;

/**<jp>
 * AS21通信プロトコルに対応した、搬送指示の送信を行なうクラスです。<BR>
 * 搬送指示送信対象となるCarryInformationの取得を行い、入庫搬送条件を満たしていれば
 * 指示対象となるAGCに対して搬送指示を送信します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/17</TD><TD>M.INOUE</TD><TD>modify a way to 'wait', from 'sleep()' to 'wait()'</TD></TR>
 * <TR><TD>2004/01/21</TD><TD>M.INOUE</TD><TD>ステーションに既に応答待ちのデータが存在する場合には<br>搬送指示を送信しないように変更</TD></TR>
 * <TR><TD>2004/02/17</TD><TD>M.INOUE</TD><TD>ステーションの状態（荷高、BCデータ）を更新するように変更</TD></TR>
 * <TR><TD>2004/05/07</TD><TD>M.INOUE</TD><TD>コの字フリーステーションの戻り入庫時の搬送指示の対象を開始状態のもののみとする</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8062 $, $Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $
 * @author  $Author: kishimoto $
 </jp>*/
/**<en>
 * This class handles the transmission of carry instruction.
 * It gives AGC the carry instruction by acquiring the data from CarryInformation  to send to AGC.
 * It is required that instance of <code>Station</code> the sender should be the station where the transmissions
 * can be received.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/17</TD><TD>M.INOUE</TD><TD>modify a way to 'wait', from 'sleep()' to 'wait()'</TD></TR>
 * <TR><TD>2004/01/21</TD><TD>M.INOUE</TD><TD>Correction is made for process of sending carry instruction:<BR>
 * In case there already exists the data in station that is waiting for response,<BR>
 * the carry instruction will not be sent.
 * </TD></TR>
 * <TR><TD>2004/02/17</TD><TD>M.INOUE</TD><TD>Corrected so that the status of the station (load size, BC data) will be updated.</TD></TR>
 * <TR><TD>2004/05/06</TD><TD>M.INOUE</TD><TD>In case of re-storage data of picking retrieval, there in a target data that carrying status is start</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8062 $, $Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $
 * @author  $Author: kishimoto $
 </en>*/
public class StorageSender
        extends RmiServAbstImpl
        implements java.lang.Runnable
{
    // Class fields --------------------------------------------------
    /**<jp>
     * リモートオブジェクトにバインドするオブジェクト名
     </jp>*/
    /**<en>
     * Object to bind to the remote object
     </en>*/
    public static final String OBJECT_NAME = "StorageSender";

    // Class variables -----------------------------------------------
    /**<jp>
     * データベース接続用のコネクション
     </jp>*/
    /**<en>
     * Connection for database use
     </en>*/
    private Connection _conn = null;

    /**<jp>
     * 搬送データ操作用ハンドラクラス
     </jp>*/
    /**<en>
     * Handlerclass for the manipulation of carry data
     </en>*/
    private CarryInfoHandler _carryInfoHandler = null;

    /**<jp>
     * Stationデータ操作用ハンドラクラス
     </jp>*/
    /**<en>
     * Handlerclass fot hte manipulation of station data
     </en>*/
    private StationHandler _stationHandler = null;

    /**<jp>
     * 搬送ルート制御クラス
     </jp>*/
    /**<en>
     * Transport route control class
     </en>*/
    private RouteController _routeController = null;

    /**<jp>
     * エリアコントローラ
     </jp>*/
    /**<en>
     * area control class
     </en>*/
    private AreaController _areaCtlr;

    /**<jp>
     * ストックコントローラ
     </jp>*/
    /**<en>
     * stock control class
     </en>*/
    private AsStockController _stockCtlr;

    /**<jp>
     * ステーションコントローラ
     </jp>*/
    /**<en>
     * station control class
     </en>*/
    private StationController _stationCtlr;

    /**<jp>
     * ハードゾーン検索用ハンドラクラス
     </jp>*/
    /**<en>
     * Transport route control class
     </en>*/
    private HardZoneHandler _hardZoneHandler = null;

    /**<jp>
     * 搬送データはあるが送信可能な状態ではない場合のSleep Time
     </jp>*/
    /**<en>
     * Sleep Time needed in case there is carry data but NOT able to send
     </en>*/
    private int _existSleepTime = 1000;

    /**<jp>
     * このフラグは、StorageSenderクラスを終了するかどうか判断します。
     * ExitFlagがtrueになった場合、run()メソッド内の無限ループから抜けます。
     * このフラグを更新するためには、stop()メソッドを使用します。
     </jp>*/
    /**<en>
     * This flag determines whether/not to terminate the StorageSender class.
     * If ExitFlag changes to true, it pulls out of the infenite loop in run() method.
     * In order to update this flag, stop() method should be used.
     </en>*/
    private boolean _exitFlag = false;

    /**<jp>
     * このフラグは、StorageSenderが、wait状態になるための判断に使用します。
     * wRequestフラグは、外部からの搬送指示要求に応じて状態を変更します。
     * wRequestがtrueの場合、搬送指示処理はwait状態にはなりません。
     </jp>*/
    /**<en>
     * This flag is used to determine whether/not StorageSender should shift to wait state.
     * wRequest flag alters its status depending on the carry instruction request from external.
     * If wRequest is true, carry instruction sill not shift to wait state.
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
    private boolean _dbConCheckFlag = false;

    // DFKLOOK ここまで

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
     * Retruns the version of this class.
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
     * 新しい<code>StorageSender</code>のインスタンスを作成しデータベースとのコネクションを取得します。
     * コネクションはAS/RSシステムパラメータから取得します。
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     </jp>*/
    /**<en>
     * Create new instance of <code>StorageSender</code>.
     * The connection will be obtained  from parameter of AS/RS system out of resource.
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     * @throws RemoteException  Exception related to communication generated while executing remote method call
     </en>*/
    public StorageSender() throws ReadWriteException,
            RemoteException
    {
        //<jp> リソースファイルからSleep Timeを取り込む</jp>
        //<en> Load the Sleep Time from the resource file.</en>
        _existSleepTime = WmsParam.INSTRUCT_CONTROL_SLEEP_SEC_DATA_EXIST;

        try
        {
            //<jp> DataBaseへのコネクションを獲得する。ユーザ名等はリソースファイルより獲得。</jp>
            //<en> Obtain the connection with DataBase. Information such as user's name, etc. wil be</en>
            //<en> gained from the resource file.</en>
            _conn = WmsParam.getConnection();

            setRequest(false);

            initHandler();

            /* ADD Start 2009.12.08 */
            //データベースコネクションチェックフラグセット
            if ("1".equals(WmsParam.WMS_DB_CONNECT_CHECK))
            {
                _dbConCheckFlag = true;
            }
            /* ADD End 2009.12.08 */

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
            logSQLException(e);
            throw new ReadWriteException(e);
        }
    }

    /**<jp>
     * 新しい<code>StorageSender</code>のインスタンスを作成しデータベースとのコネクションを取得します。
     * コネクションはAS/RSシステムパラメータから取得します。
     * @param agcNumber agcNumber
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     </jp>*/
    public StorageSender(String agcNumber) throws ReadWriteException,
            RemoteException
    {
        //<jp> リソースファイルからSleep Timeを取り込む</jp>
        //<en> Load the Sleep Time from the resource file.</en>
        _existSleepTime = WmsParam.INSTRUCT_CONTROL_SLEEP_SEC_DATA_EXIST;

        try
        {
            _agcNo = agcNumber;

            //<jp> DataBaseへのコネクションを獲得する。ユーザ名等はリソースファイルより獲得。</jp>
            //<en> Obtain the connection with DataBase. Information such as user's name, etc. wil be</en>
            //<en> gained from the resource file.</en>
            _conn = WmsParam.getConnection();

            setRequest(false);

            initHandler();

            /* ADD Start 2009.12.08 */
            //データベースコネクションチェックフラグセット
            if ("1".equals(WmsParam.WMS_DB_CONNECT_CHECK))
            {
                _dbConCheckFlag = true;
            }
            /* ADD End 2009.12.08 */

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
            logSQLException(e);
            throw new ReadWriteException(e);
        }
    }


    // Public methods ------------------------------------------------
    /**<jp>
     * このクラスで使用する各ハンドラインスタンスの生成を行います。
     </jp>*/
    /**<en>
     * Generate the each handler instance whickeih will be used in this class.
     </en>*/
    protected void initHandler()
    {
        //<jp> ハンドラインスタンス生成</jp>
        //<en> Generation of handler instance</en>
        _carryInfoHandler = new CarryInfoHandler(_conn);
        _stationHandler = new StationHandler(_conn);
        _hardZoneHandler = new HardZoneHandler(_conn);
        //<jp> ルートコントローラーインスタンス生成</jp>
        //<jp> 毎回ルートチェックを実施する。</jp>
        //<en> Generation of the route controller instance</en>
        //<en> Conduct the route check at each cycle.</en>
        _routeController = new RouteController(_conn, true);
        //<jp> エリア コントローラをクリア</jp>
        _areaCtlr = null;
        // ストックコントローラーをクリア
        _stockCtlr = null;
        // ステーションコントローラーをクリア
        _stationCtlr = null;
    }

    /**<jp>
     * <code>CarryInformation</code>の読込みを行い、搬送指示可能であれば設備コントローラに搬送指示を送信します。
     * <code>CarryInformation</code>の読込みは一定の間隔で行われます。空棚検索が必要な場合、入庫棚決定を行います。
     </jp>*/
    /**<en>
     * Read <code>CarryInformation</code>, abd if the carry instruction is feasible, send the carry instruction
     * to the facility controller(AGC).
     * Reading of <code>CarryInformation</code> is done at a certain interval. If the empty location search is
     * required, conduct the storing location designation.
     </en>*/
    public void run()
    {
        try
        {
            if (WmsParam.MULTI_ASRSSERVER)
            {
                //<jp> 搬送指示をRMI Serverへ登録</jp>
                //<en> Register the carry instruction to the RMI Server</en>
                this.bind("//" + RmiSendClient.RMI_REG_SERVER + _agcNo + "/" + OBJECT_NAME + _agcNo);
            }
            else
            {
                //<jp> 搬送指示をRMI Serverへ登録</jp>
                //<en> Register the carry instruction to the RMI Server</en>
                this.bind("//" + RmiSendClient.RMI_REG_SERVER + "/" + OBJECT_NAME);

            }

            //<jp> 6020014=搬送指示送信処理を起動します。</jp>
            //<en> 6020014=Starting transfer instruction sending process.</en>
            RmiMsgLogClient.write(6020014, this.getClass().getName());

            //<jp> コネクションチェックメソッド</jp>
            // <en> Connection Check Method</en>
            CheckConnection chk = new CheckConnection();

            //<jp> ずっと動き続けること。</jp>
            //<en> Keep repeating the following process.</en>
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
                        synchronized (this)
                        {
                            try
                            {
                                System.out.println("StorageSender ::: WAIT!!!");
                                //<jp> 要求が行なわれていない場合、wait状態にする。</jp>
                                //<en> If there is no request, shift to the wait state.</en>
                                if (hasRequest() == false)
                                {
                                    this.wait();
                                }
                                //<jp> 要求フラグをリセットする。</jp>
                                //<en> Reset the requesting flag.</en>
                                setRequest(false);
                                System.out.println("StorageSender ::: Wake UP UP UP!!!");
                                if (_exitFlag)
                                {
                                    break;
                                }
                            }
                            catch (Exception e)
                            {
                                //<jp> 6026043=搬送指示タスクで異常が発生しました。例外：{0}</jp>
                                //<en> 6026043=Error occurred in transfer instruction task. Exception:{0}</en>
                                RmiMsgLogClient.write(new TraceHandler(6026043, e), this.getClass().getName());
                            }
                        }
                        //<jp> 搬送データを取りに行く。</jp>
                        //<en> Go fetch the carry data.</en>
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
                    //<jp> 6026043=搬送指示タスクで異常が発生しました。例外：{0}</jp>
                    //<en> 6026043=Error occurred in transfer instruction task. Exception:{0}</en>
                    RmiMsgLogClient.write(new TraceHandler(6026043, e), this.getClass().getName());
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
            //<jp> 6026043=搬送指示タスクで異常が発生しました。例外：{0}</jp>
            //<en> 6026043=Error occurred in transfer instruction task. Exception:{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6026043, e), this.getClass().getName());
        }
        finally
        {
            System.out.println("StorageSender:::::finally!!");
            //<jp> 6020015=搬送指示送信処理を停止します。</jp>
            //<en> 6020015=Terminating transfer instruction sending process.</en>
            RmiMsgLogClient.write(6020015, this.getClass().getName());

            //<jp> RMI Serverから登録を削除</jp>
            //<en> Delete the registration from RMI Server</en>
            this.unbind();

            closeConnection();
        }
    }

    /**<jp>
     * Wait中の本搬送指示を起します。
     </jp>*/
    /**<en>
     * Activate the carry instruction in wait state.
     </en>*/
    public synchronized void wakeup()
    {
        setRequest(true);
        this.notify();
    }

    /**<jp>
     * この搬送指示送信処理に対して、即搬送データの確認をするように要求を行ないます。
     * RMI Server経由でキックする場合は、こちらのメソッドを使ってください。
     </jp>*/
    /**<en>
     * For this transmission of carry instruction, submit request to immediately check its carry data.
     * If requesting via RMI Server, please use this method.
     </en>
     * @param params not used
     */
    public synchronized void write(Object[] params)
    {
        //<jp> 要求フラグをセットしておく</jp>
        //<en> Preset the request flag.</en>
        setRequest(true);
        //<jp> wait解除</jp>
        //<en> release from wait state</en>
        this.notify();
    }

    /**<jp>
     * 処理を終了します。
     * 外部よりこのメソッドを呼び出された場合、処理を終了します。
     </jp>*/
    /**<en>
     * Terminate this process.
     * It terminates if external calls for this method.
     </en>*/
    public synchronized void stop()
    {
        //<jp> スレッドのループが終了するように、フラグを更新する。</jp>
        //<en> Reniew the flag status so that the loop of this thread would terminate.</en>
        _exitFlag = true;
        //<jp> このスレッドの待ち状態を解除する。</jp>
        //<en> Release the wait state of this thread.</en>
        this.notify();
    }

    /**<jp>
     * 搬送指示処理を行います。
     * 搬送指示要求状態のCarryInformationを読込み、搬送可能であれば設備コントローラーに搬送指示を送信します。
     * @throws Exception 搬送指示処理の続行が不可能な場合に通知されます。
     </jp>*/
    /**<en>
     * Processing the carry instruction.
     * Load the CarryInformation requesting for carry instruction; if carry can be carried out, send the
     * carry instruction of facility controller (AGC)
     * @throws Exception : Notifies if carry instruction cannot be continued any longer.
     </en>*/
    public void control()
            throws Exception
    {
        CarryInfo cInfo = null;
        try
        {
            //<jp> ステーション毎の搬送指示データを探しに行ってデータが無かった回数</jp>
            //<en> Number of times no data was found in attempt of searching carry instruction of each station</en>
            int counStorageDataExist = 0;
            //<jp> ステーション数</jp>
            //<en> Number of stations</en>
            int countStations = 1;

            //<jp> ステーション毎の搬送指示データを探しに行って</jp>
            //<jp> データが無かった回数とステーション数を比べて全てのステーションで</jp>
            //<jp> データが無くなったらcontrol()を抜けてSleepに入る。</jp>
            //<en> In search for the carry instruction data of each station;</en>
            //<en> and in comparing numbers of times that data was not found and the number of stations,</en>
            //<en> if no data is founde at all stations, get through control() and shift to Sleep.</en>
            // Station検索用
            StationHandler stationHandler = new StationHandler(_conn);
            StationSearchKey stationKey = new StationSearchKey();

            while (countStations > counStorageDataExist)
            {
                //<jp> 入出庫兼用＋入庫ステーション一覧を取得する。</jp>
                //<en> Get list of stations handling both storage/retrieval + storage only station</en>
                // 検索キーセット
                stationKey.clear();
                if (WmsParam.MULTI_ASRSSERVER)
                {
                    stationKey.setControllerNo(_agcNo);
                }
                stationKey.setSendable(Station.SENDABLE_TRUE);
                stationKey.setModeType(Station.MODE_TYPE_AUTO_CHANGE, "!=");
                stationKey.setStationType(Station.STATION_TYPE_IN, "=", "(", "", false);
                stationKey.setStationType(Station.STATION_TYPE_INOUT, "=", "", ")", true);
                stationKey.setKey(jp.co.daifuku.wms.base.entity.GroupController.STATUS_FLAG,
                        SystemDefine.GC_STATUS_ONLINE);
                stationKey.setJoin(Station.CONTROLLER_NO, jp.co.daifuku.wms.base.entity.GroupController.CONTROLLER_NO);
                stationKey.setStationNoOrder(true);
                // 検索
                Station[] stations = (Station[])stationHandler.find(stationKey);

                countStations = stations.length;
                //<jp> 入庫可能なステーションの一覧分 ループする。</jp>
                //<en> Loop to the end of the list of stations available of storing</en>
                for (int i = 0; i < countStations; i++)
                {
                    Station sourceSt = stations[i];
                    //<jp> 搬送開始状態の搬送データを読込み</jp>
                    //<en> Read the cary data in start state</en>
                    cInfo = getCarryInfo(sourceSt);
                    if (cInfo == null)
                    {
                        System.out.println("No data::::::::::::" + sourceSt.getStationNo() + ";;;" + "");
                        counStorageDataExist = counStorageDataExist + 1;
                        //<jp> 棚決定処理等で取得したロック資源を解放するためにトランザクションを確定させる。</jp>
                        //<en> Fix the transaction in order to release the locked resource obtained from</en>
                        //<en> determination of the location, etc.</en>
                        _conn.commit();
                        //<jp> 該当搬送データなし</jp>
                        //<en> No suche carry data is found.</en>
                        continue;
                    }
                    //<jp> 搬送データが存在する場合、条件をチェックし搬送指示テキストを作成</jp>
                    //<jp> 搬送元Stationの状態をチェック</jp>
                    //<en> If the carry data exists, check the conditions and create the carry instruction.</en>
                    //<en> Check the state of sending station.</en>
                    if (soueceRightStation(cInfo, sourceSt))
                    {
                        //<jp> 搬送先決定処理および搬送ルートチェック</jp>
                        //<en> Determination of the receiving location and carry route check</en>
                        if (destDetermin(cInfo, sourceSt))
                        {
                            // 2009.09.04 Add Start by tani
                            // 該当到着情報の送信Flagを送信済みに更新
                            // 該当ステーションが到着ありの場合のみ更新
                            if (SystemDefine.ARRIVAL_ON.equals(sourceSt.getArrival()))
                            {
                                ArrivalHandler arrivalHandler = new ArrivalHandler(getConnection());
                                ArrivalAlterKey arrivalAlterKey = new ArrivalAlterKey();
                                ArrivalSearchKey skey = new ArrivalSearchKey();
                                skey.setStationNo(sourceSt.getStationNo());
                                skey.setSendFlag(Arrival.ARRIVAL_NOT_SEND);
                                skey.setRegistDateOrder(true);

                                Arrival[] arrival = (Arrival[])arrivalHandler.find(skey);

                                arrivalAlterKey.setRegistDate(arrival[0].getRegistDate());
                                arrivalAlterKey.setStationNo(sourceSt.getStationNo());
                                arrivalAlterKey.updateCarryKey(cInfo.getCarryKey());
                                arrivalAlterKey.updateSendFlag(Arrival.ARRIVAL_SENDED);
                                arrivalHandler.modify(arrivalAlterKey);
                            }
                            // 2009.09.04 Add End by tani

                            //<jp> 搬送指示テキストの送信を行う。</jp>
                            //<en> Send the text of carry instruction.</en>
                            sendText(cInfo, GroupController.getInstance(_conn, sourceSt.getControllerNo()) ,sourceSt);

                        }
                    }
                    //<jp> 搬送データが存在時の処理を実施した場合は送信結果の有無にかかわらず</jp>
                    //<jp> 棚決定処理等で取得したロック資源を解放するためにトランザクションを確定させる。</jp>
                    //<en> If the process is operated for existing carry data, regardless of the results of sending/receiving,</en>
                    //<en> Fix the transaction in order to release the locked resource ontained by the determination of location.</en>
                    _conn.commit();
                    counStorageDataExist = 0;
                }

                //<jp> スレッド終了</jp>
                //<en> Terminating the thread</en>
                if (_exitFlag == true)
                {
                    break;
                }

                //<jp> 搬送データはあるが送信可能な状態ではない場合は、少しSleep</jp>
                //<en> If there are carry data but NOT able to send, let sleep for a while.</en>
                synchronized (this)
                {
                    this.wait(_existSleepTime);
                }
            }
        }
        catch (SQLException e)
        {
            logSQLException(e);
            throw new ReadWriteException(e);
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

    /**
     * AreaControllerを返します。
     *
     * @return AreaController
     * @throws ReadWriteException データベース接続エラー
     */
    protected AreaController getAreaCtlr()
            throws ReadWriteException
    {
        if (null == _areaCtlr)
        {
            _areaCtlr = new AreaController(getConnection(), getClass());
        }
        return _areaCtlr;
    }

    /**
     * AsStockControllerを返します。
     *
     * @return AsStockController
     * @throws ReadWriteException データベース接続エラー
     * @throws ScheduleException スケジュール
     */
    protected AsStockController getStockCtlr()
            throws ReadWriteException,
                ScheduleException
    {
        if (null == _stockCtlr)
        {
            _stockCtlr = new AsStockController(getConnection(), getClass());
        }
        return _stockCtlr;
    }

    /**
     * StationControllerを返します。
     *
     * @return StationController
     * @throws ReadWriteException データベース接続エラー
     * @throws ScheduleException スケジュール
     */
    protected StationController getStationCtlr()
            throws ReadWriteException,
                ScheduleException
    {
        if (null == _stationCtlr)
        {
            _stationCtlr = new StationController(getConnection(), getClass());
        }
        return _stationCtlr;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    /**<jp>
     * 指定されたステーションに存在する搬送データの読み込みを行います。搬送情報が見つからなかった場合、nullが戻ります。
     * 到着報告有りのステーションの場合、ステーションに記録された搬送key（mckey）を元にCarryInformationの読込みを行ないます。
     * ダミー到着の場合、ダミー到着が存在する場合のみ、そのステーションが搬送元となるCarryInformationを探し、搬送作成日が最も古いものを搬送指示対象とします。
     * ステーションの到着情報にBCDATAおよび荷高が記録されている場合、その内容をCarryInformationが参照するPalletのBCDATAおよび荷高にセットします。
     * Palletインスタンスの更新に失敗した場合、読み込んだCarryInformationは異常に変更し、nullを返します。
     * 到着報告無しのステーションの場合、指定されたステーションが搬送元となるCarryInformationを読み込みます。
     * @param  chStation   搬送の有無を確認する搬送元ステーション
     * @return             搬送情報(CarryInformation)
     * @throws Exception   データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Read the carry data existing in specified station. If no carry data is found, it returns null.
     * If the station has arrival report, read the CarryInformation according to the carry key (mckey) recorded in station.
     * If it is dummy arrival, search the CarryInformation designating this station to be the sending station, then select one
     * with the oldest date of carry creation.
     * If BC data and the load size are recorded in the station'S arrival data, set these data as the BC data and load size
     * of pallet that CarryInformation will refer to.
     * If failed to renew the Pallet instance, change the CarryInformation already read to 'error' and return null.
     * If there is no arrival report at the station, load the CarryInformation designating this specified station to be the
     * sender.
     * @param  chStation   Sending station with which to confirm the carry type
     * @return             CarryInformation
     * @throws Exception   Notifies if trouble occurs in reading/writing in database.
     </en>*/
    protected CarryInfo getCarryInfo(Station chStation)
            throws Exception
    {
        CarryInfoSearchKey cskey = new CarryInfoSearchKey();

        // Pallet検索・更新用
        PalletHandler palletHandelr = new PalletHandler(_conn);
        PalletAlterKey palletAlterKey = new PalletAlterKey();

        //<jp> 応答待ちのデータがステーションに存在した場合は指示を送信しない。</jp>
        //<en> If there is a data waiting for reponse in station, the instruction will not be sent.</en>
        // 検索キーセット
        cskey.clear();
        cskey.setSourceStationNo(chStation.getStationNo());
        cskey.setCmdStatus(CarryInfo.CMD_STATUS_WAIT_RESPONSE, "=", "(", "", false);
        cskey.setCmdStatus(CarryInfo.CMD_STATUS_ERROR, "=", "", ")", true);
        if (_carryInfoHandler.count(cskey) > 0)
        {
            return null;
        }

        // Stationの数が多い場合、現在のステーションの状態と一致せず
        // 1回のダミー到着に対し、搬送指示を2回投げることがあるため
        // Stationの状態を再取得するよう修正
        chStation = StationFactory.makeStation(_conn, chStation.getStationNo());

        //<jp> 到着報告があるステーションなのかをチェック</jp>
        //<en> Check whether/not the station has the arrival report.</en>
        if (Station.ARRIVAL_ON.equals(chStation.getArrival()))
        {

            ArrivalSearchKey skey = new ArrivalSearchKey();
            ArrivalHandler aHand = new ArrivalHandler(_conn);
            skey.setStationNo(chStation.getStationNo());
            skey.setSendFlag(Arrival.ARRIVAL_NOT_SEND);
            skey.setRegistDateOrder(true);
            if (aHand.count(skey) == 0)
            {
                return null;
            }
            Arrival[] arrival = (Arrival[])aHand.find(skey);

            //<jp> 搬送Keyが在れば、それを基にCarryInrormationを獲得する。</jp>
            //<en> If the carry key is found, get hte CarryInformation based on that.</en>
            String carryKey = arrival[0].getCarryKey();
            //<jp> Stationにある搬送Keyを基にCarryInformationテーブルから条件に会ったものを１つ獲得する。</jp>
            //<jp> Stationに到着が来ていないものとして何もしない。</jp>
            //<en> Based on the carry key at the station, get one out of CarryInformation table that meets the conditions.</en>
            //<en> Take no action as no arrival is reported to the station.</en>
            if (StringUtil.isBlank(arrival))
            {
                return null;
            }
            //<jp> Stationにダミー到着が来ている。</jp>
            //<en> Station received the dummy arrival.</en>
            else if (carryKey.equals(WmsParam.DUMMY_MCKEY))
            {
                // 搬送指示有無によりSQLが異なる
                if (Station.RESTORING_INSTRUCTION_AWC_STORAGE_SEND.equals(chStation.getRestoringInstruction()))
                {
                    // 検索キーセット
                    cskey.clear();
                    cskey.setCarryFlag(CarryInfo.CARRY_FLAG_STORAGE, "=", "(", "", false);
                    cskey.setCarryFlag(CarryInfo.CARRY_FLAG_DIRECT_TRAVEL, "=", "", ")", true);
                    cskey.setCmdStatus(CarryInfo.CMD_STATUS_START, "=", "(", "", false);
                    cskey.setCmdStatus(CarryInfo.CMD_STATUS_ARRIVAL, "=", "", ")", true);
                    cskey.setSourceStationNo(chStation.getStationNo());
                    cskey.setPriorityOrder(true);
                    cskey.setRegistDateOrder(true);
                    cskey.setCarryKeyOrder(true);
                }
                else
                {
                    // 検索キーセット
                    cskey.clear();
                    cskey.setCarryFlag(CarryInfo.CARRY_FLAG_STORAGE, "=", "(", "", false);
                    cskey.setCarryFlag(CarryInfo.CARRY_FLAG_DIRECT_TRAVEL, "=", "", ")", true);
                    cskey.setCmdStatus(CarryInfo.CMD_STATUS_START);
                    cskey.setSourceStationNo(chStation.getStationNo());
                    cskey.setPriorityOrder(true);
                    cskey.setRegistDateOrder(true);
                    cskey.setCarryKeyOrder(true);
                }

                // DFKLOOK:ここから修正
                // バーコード存在フラグ
                boolean existBcrFlag = false;

                // バーコード情報セット
                if (!StringUtil.isBlank(arrival[0].getBcrData()))
                {
                    PalletSearchKey sKey = new PalletSearchKey();
                    sKey.setKey(Pallet.BCR_DATA, arrival[0].getBcrData().trim(), "=", "(", "", false);
                    sKey.setKey(Pallet.BCR_DATA, arrival[0].getBcrData(), "=", "", ")", true);
                    if (palletHandelr.count(sKey) > 0)
                    {
                        cskey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
                        cskey.setKey(Pallet.BCR_DATA, arrival[0].getBcrData().trim(), "=", "(", "", false);
                        cskey.setKey(Pallet.BCR_DATA, arrival[0].getBcrData(), "=", "", ")", true);

                        // バーコードが存在した為、フラグを成立させる
                        existBcrFlag = true;
                    }
                }

                //                // バーコード情報セット
                //                if (!StringUtil.isBlank(chStation.getBcrData()))
                //                {
                //                    PalletSearchKey sKey = new PalletSearchKey();
                //                    sKey.setKey(Pallet.BCR_DATA, chStation.getBcrData().trim(), "=", "(", "", false);
                //                    sKey.setKey(Pallet.BCR_DATA, chStation.getBcrData(), "=", "", ")", true);
                //                    if (palletHandelr.count(sKey) > 0)
                //                    {
                //                        cskey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
                //                        cskey.setKey(Pallet.BCR_DATA, chStation.getBcrData().trim(), "=", "(", "", false);
                //                        cskey.setKey(Pallet.BCR_DATA, chStation.getBcrData(), "=", "", ")", true);
                //
                //                        // バーコードが存在した為、フラグを成立させる
                //                        existBcrFlag = true;
                //                    }
                //                }
                // DFKLOOK:ここから修正

                CarryInfo[] carry = (CarryInfo[])_carryInfoHandler.find(cskey);
                // 該当データなし
                if (ArrayUtil.isEmpty(carry))
                {
                    // DFKLOOK:ここから修正
                    // ダミー到着でバーコードが指定されたが
                    // 搬送データが存在しなかった場合
                    if (existBcrFlag)
                    {
                        // 6026609=パレットのバーコード情報、または容器No.が重複している可能性があります。StationNo={0} バーコード情報={1}
                        Object[] tobj = new Object[2];
                        tobj[0] = arrival[0].getStationNo();
                        tobj[1] = arrival[0].getBcrData();
                        RmiMsgLogClient.write(6026609, LogMessage.F_ERROR, this.getClass().getName(), tobj);
                    }
                    // DFKLOOK:ここまで修正

                    return null;
                }
                // 該当データが存在する場合1件目を使用します
                else
                {
                    try
                    {
                        //<jp> Stationに保持しているBC Data、荷高情報をPalletにセット</jp>
                        //<en> Set the BC data and the load sizewhich are  preserved in Station to the pallet.</en>
                        if (!StringUtil.isBlank(arrival[0].getBcrData()))
                        {
                            if (!(arrival[0].getBcrData().equals("")))
                            {
                                // 検索キーセット
                                palletAlterKey.clear();
                                palletAlterKey.setPalletId(carry[0].getPalletId());
                                palletAlterKey.updateBcrData(arrival[0].getBcrData());
                                palletAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                                // 更新
                                palletHandelr.modify(palletAlterKey);
                            }
                        }

                        // フリーアロケーション運用かつ制御情報を保持している場合、荷幅、荷高をセットする
                        if (getStationCtlr().isFreeAllocationStation(arrival[0].getStationNo())
                                && !StringUtil.isBlank(arrival[0].getControlinfo()))
                        {
                            LoadSizeHandler lhandl = new LoadSizeHandler(getConnection());
                            LoadSizeSearchKey lkey = new LoadSizeSearchKey();

                            // 制御情報より荷姿検索
                            ControlInfo conInfo = new ControlInfo();
                            conInfo = conInfo.convertControlInfo(arrival[0].getControlinfo());

                            lkey.setLength(conInfo.getLength());
                            lkey.setHeight(conInfo.getHeight());
                            LoadSize[] loadsize = (LoadSize[])lhandl.find(lkey);

                            // 検索キーセット
                            palletAlterKey.clear();
                            palletAlterKey.setPalletId(carry[0].getPalletId());
                            palletAlterKey.updateHeight(loadsize[0].getLoadSize());
                            palletAlterKey.updateWidth(conInfo.getWidth());
                            palletAlterKey.updateControlinfo(arrival[0].getControlinfo());
                            palletAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                            // 更新
                            palletHandelr.modify(palletAlterKey);
                        }
                        // フリーアロケーション運用以外かつ荷高が0より大きい場合、荷高をセットする
                        else if (arrival[0].getHeight() > 0)
                        {
                            // 検索キーセット
                            palletAlterKey.clear();
                            palletAlterKey.setPalletId(carry[0].getPalletId());
                            palletAlterKey.updateHeight(arrival[0].getHeight());
                            palletAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                            // 更新
                            palletHandelr.modify(palletAlterKey);
                        }
                        return carry[0];
                    }
                    catch (NotFoundException e)
                    {
                        //<jp> 更新すべきデータが見つからない場合</jp>
                        //<en> If the data to delete cannot be found</en>
                        Object[] tObj = new Object[1];
                        tObj[0] = carry[0].getPalletId();
                        //<jp> 6026067=指定されたパレットIDのパレット情報が存在しません。PalletID={0}</jp>
                        //<en> 6026067=Pallet data for the designated Pallet ID does not exist. Pallet ID={0}</en>
                        RmiMsgLogClient.write(new TraceHandler(6026067, e), getClass().getName(), tObj);
                        carryFailure(carry[0]);
                        return null;
                    }
                }
            }
            //<jp> StationにあるMcKeyを基にCarryInformationテーブルから条件に会ったものを１つ獲得する。</jp>
            //<en> Based on the McKey at Station, get one out of CarryInformation table that meets the conditions.</en>
            else
            {
                // 搬送指示有無によりSQLが異なる
                if (Station.RESTORING_INSTRUCTION_AWC_STORAGE_SEND.equals(chStation.getRestoringInstruction()))
                {
                    //<jp> 搬送状態が開始または到着のものを搬送指示送信対象とする</jp>
                    //<en> There is a target data that CarryInformation status is CMD_STATUS_START or CMD_STATUS_ARRIVAL</en>
                    cskey.clear();
                    cskey.setCmdStatus(CarryInfo.CMD_STATUS_START, "=", "(", "", false);
                    cskey.setCmdStatus(CarryInfo.CMD_STATUS_ARRIVAL, "=", "", ")", true);
                }
                else
                {
                    //<jp> 搬送状態が開始のもののみ搬送指示送信対象とする</jp>
                    //<en> There is a target data that CarryInformation status is only CMD_STATUS_START</en>
                    cskey.clear();
                    cskey.setCmdStatus(CarryInfo.CMD_STATUS_START);
                }
                cskey.setCarryKey(carryKey);
                //<jp> 該当のCarryInfoを取得</jp>
                //<en> Getting the applicable CarryInfo</en>
                CarryInfo[] carry = (CarryInfo[])_carryInfoHandler.find(cskey);

                //<jp> 該当データなし</jp>
                //<en> No such data is found.</en>
                if (ArrayUtil.isEmpty(carry))
                {
                    //<jp> 警告メッセージを出力する。</jp>
                    //<jp> 6026066=指定されたmckeyの搬送データ[McKey={0}]が存在しないのでStation[StationNo.={1}]から削除しました。</jp>
                    //<en> Outputting the warning message</en>
                    //<en> 6026066=No transfer data [MCKey={0}] for the specified MCKey. Deleted from the station [ST No.={1}].</en>
                    Object[] tobj = new Object[2];
                    tobj[0] = carryKey;
                    tobj[1] = arrival[0].getStationNo();
                    RmiMsgLogClient.write(6026066, LogMessage.F_ERROR, this.getClass().getName(), tobj);
                    //<jp> 指定されたmckeyの搬送データがないなら、消してしまう。</jp>
                    //<en> If specified carry data of specified mc key cannot be found, delete.</en>
                    try
                    {
                        System.out.println("delete if no carry data of specified mckey is found.St No. = "
                                + arrival[0].getStationNo());

                        // DFKLOOK DELETE start 3.4
                        //                        // Station更新用
                        //                        StationHandler stationHandelr = new StationHandler(_conn);
                        //                        StationAlterKey stationAlterKey = new StationAlterKey();
                        //
                        //                        // 検索キーセット
                        //                        stationAlterKey.clear();
                        //                        stationAlterKey.setStationNo(arrival[0].getStationNo());
                        //                        stationAlterKey.updateCarryKey(null);
                        //                        stationAlterKey.updateBcrData(null);
                        //                        stationAlterKey.updateHeight(0);
                        //
                        //                        // 更新
                        //                        stationHandelr.modify(stationAlterKey);
                        // DFKLOOK DELETE end 3.4

                        // DFKLOOK ADD start 3.4
                        // Arrival削除
                        ArrivalHandler arrivalHand = new ArrivalHandler(_conn);
                        ArrivalSearchKey arrivalSkey = new ArrivalSearchKey();
                        // 検索キーセット
                        arrivalSkey.setCarryKey(arrival[0].getCarryKey());
                        arrivalSkey.setStationNo(arrival[0].getStationNo());
                        // 削除
                        arrivalHand.drop(arrivalSkey);
                        // DFKLOOK ADD end 3.4
                    }
                    catch (NotFoundException e)
                    {
                        //<jp> 削除すべきデータが見つからない場合</jp>
                        //<en> If the data to delete cannot be found</en>
                        Object[] tObj = new Object[1];
                        tObj[0] = Station.STORE_NAME;
                        //<jp> 6006006=削除対象データがありません。テーブル名:{0}</jp>
                        //<en> 6006006=There is no data to delete. Table Name: {0}</en>
                        RmiMsgLogClient.write(new TraceHandler(6006006, e), getClass().getName(), tObj);
                    }
                    return null;
                }

                if (carry[0] instanceof CarryInfo)
                {
                    //<jp> 到着情報として記録されている搬送Keyより取得したCarryInformationを返す。</jp>
                    //<en> Return the CarryInformation acquired by the carry key that is recorded as arrival data. </en>
                    try
                    {
                        //<jp> Stationに保持しているBC Data、荷高情報を今回送信対象となるPalletにセット</jp>
                        //<en> Set the BC data and load size data preserved by the station to the target Pallet of sending data.  </en>
                        if (!StringUtil.isBlank(arrival[0].getBcrData()))
                        {
                            if (!(arrival[0].getBcrData().equals("")))
                            {
                                // 検索キーセット
                                palletAlterKey.clear();
                                palletAlterKey.setPalletId(carry[0].getPalletId());
                                palletAlterKey.updateBcrData(arrival[0].getBcrData());
                                palletAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                                // 更新
                                palletHandelr.modify(palletAlterKey);
                            }
                        }
                        // フリーアロケーション運用かつ制御情報を保持している場合、荷幅、荷高をセットする
                        if (getStationCtlr().isFreeAllocationStation(arrival[0].getStationNo())
                                && !StringUtil.isBlank(arrival[0].getControlinfo()))
                        {
                            LoadSizeHandler lhandl = new LoadSizeHandler(getConnection());
                            LoadSizeSearchKey lkey = new LoadSizeSearchKey();

                            // 制御情報より荷姿検索
                            ControlInfo conInfo = new ControlInfo();
                            conInfo = conInfo.convertControlInfo(arrival[0].getControlinfo());

                            lkey.setLength(conInfo.getLength());
                            lkey.setHeight(conInfo.getHeight());
                            LoadSize[] loadsize = (LoadSize[])lhandl.find(lkey);

                            // 検索キーセット
                            palletAlterKey.clear();
                            palletAlterKey.setPalletId(carry[0].getPalletId());
                            palletAlterKey.updateHeight(loadsize[0].getLoadSize());
                            palletAlterKey.updateWidth(conInfo.getWidth());
                            palletAlterKey.updateControlinfo(arrival[0].getControlinfo());
                            palletAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                            // 更新
                            palletHandelr.modify(palletAlterKey);
                        }
                        // フリーアロケーション運用以外かつ荷高が0より大きい場合、荷高をセットする
                        else if (arrival[0].getHeight() > 0)
                        {
                            // 検索キーセット
                            palletAlterKey.clear();
                            palletAlterKey.setPalletId(carry[0].getPalletId());
                            palletAlterKey.updateHeight(arrival[0].getHeight());
                            palletAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                            // 更新
                            palletHandelr.modify(palletAlterKey);
                        }
                        return carry[0];
                    }
                    catch (NotFoundException e)
                    {
                        //<jp> 更新すべきデータが見つからない場合</jp>
                        //<en> If the data to delete cannot be found</en>
                        Object[] tObj = new Object[1];
                        tObj[0] = carry[0].getPalletId();
                        //<jp> 6026067=指定されたパレットIDのパレット情報が存在しません。PalletID={0}</jp>
                        //<en> 6026067=Pallet data for the designated Pallet ID does not exist. Pallet ID={0}</en>
                        RmiMsgLogClient.write(new TraceHandler(6026067, e), getClass().getName(), tObj);
                        carryFailure(carry[0]);
                        return null;
                    }
                }
                else
                {
                    return null;
                }
            }
        }
        else
        {
            // 搬送指示有無によりSQLが異なる
            if (chStation.getRestoringInstruction().equals(Station.RESTORING_INSTRUCTION_AWC_STORAGE_SEND))
            {
                // 検索キーセット
                cskey.clear();
                cskey.setCarryFlag(CarryInfo.CARRY_FLAG_STORAGE, "=", "(", "", false);
                cskey.setCarryFlag(CarryInfo.CARRY_FLAG_DIRECT_TRAVEL, "=", "", ")", true);
                cskey.setCmdStatus(CarryInfo.CMD_STATUS_START, "=", "(", "", false);
                cskey.setCmdStatus(CarryInfo.CMD_STATUS_ARRIVAL, "=", "", ")", true);
                cskey.setSourceStationNo(chStation.getStationNo());
                cskey.setPriorityOrder(true);
                cskey.setRegistDateOrder(true);
                cskey.setCarryKeyOrder(true);
            }
            else
            {
                // 検索キーセット
                cskey.clear();
                cskey.setCarryFlag(CarryInfo.CARRY_FLAG_STORAGE, "=", "(", "", false);
                cskey.setCarryFlag(CarryInfo.CARRY_FLAG_DIRECT_TRAVEL, "=", "", ")", true);
                cskey.setCmdStatus(CarryInfo.CMD_STATUS_START);
                cskey.setSourceStationNo(chStation.getStationNo());
                cskey.setPriorityOrder(true);
                cskey.setRegistDateOrder(true);
                cskey.setCarryKeyOrder(true);
            }

            CarryInfo[] carryArray = (CarryInfo[])_carryInfoHandler.find(cskey);

            // 該当データあり
            if (!ArrayUtil.isEmpty(carryArray))
            {
                return carryArray[0];
            }

            return null;
        }
    }

    /**<jp>
     * 搬送元ステーションの状態をチェックします。
     * 搬送元Stationが所属する設備コントローラー(AGC)が正常
     * 搬送元Stationが作業モード切替要求中ではない
     * 作業モード管理を行う場合、入出庫兼用ステーションなら搬送元Stationの作業モードが入庫
     *   ただし、出庫指示詳細が未設定の場合(新規入庫)はStationの作業モードはチェックしない。
     * 搬送指示可能件数については搬送元ステーションに関連するCarryInformationが規定数を越えていないかをCheckする。
     * 該当ステーションが搬送先になっているCarryInformationについては考慮しない。
     * 搬送元Stationが中断中ではない
     * @param  cInfo       搬送情報(CarryInformation)
     * @param  sourceSt    搬送元ステーション
     * @return             搬送可能と確認できた場合はtrue、ダメな場合がfalse
     * @throws Exception   データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check the status of the sending station.
     * Facility controller (AGC) ,that sending station belongs to, is normal.
     * Sending station is not requesting to switch the work mode.
     * In case the work mode is in control: If the sending station handles both storage/retrieval,
     * the work mode of the sending station is 'storage'
     *   Except if the detail of retrieval instruction is unset(new storage), the work mode of the Station won't have to be checked.
     * As for MAX. number of carry instructions, check to see if CarryInformation, relevant to this sending station has not
     * exceeded.
     * No consideration is requried if the applicable station is the receiver in CarryInformation
     * Operation of the sending station is not suspended.
     * @param  cInfo       CarryInformation
     * @param  sourceSt    Sending station
     * @return             Return true if it's available mad 'false' if not.
     * @throws Exception   Notifies if trouble occurs in reading/writing in database.
     </en>*/
    protected boolean soueceRightStation(CarryInfo cInfo, Station sourceSt)
            throws Exception
    {
        //<jp> 搬送元Stationを確認。</jp>
        //<jp> 搬送元StationをコントロールするAGCが正常か？</jp>
        //<en> Check the sending station.</en>
        //<en> Is AGC normal, which should control the sending station?</en>
        GroupController groupControll = GroupController.getInstance(_conn, sourceSt.getControllerNo());

        System.out.println("StorageSender GroupController Is AGC controles over sending statio = "
                + groupControll.getStatus());
        if (groupControll.getStatus() != GroupController.STATUS_ONLINE)
        {
            return false;
        }

        //<jp> 出庫指示詳細が未設定の場合は新規入庫なので</jp>
        //<jp> 搬送元ステーションの作業モードチェックを行う</jp>
        //<en> If the detail of retrieval instruction is unset, it means that is a new storage.</en>
        //<en> Check the work mode of the receiving </en>
        if (CarryInfo.RETRIEVAL_DETAIL_UNKNOWN.equals(cInfo.getRetrievalDetail()))
        {
            //<jp> 搬送元Stationが入出庫兼用ならばモードの確認が必要。入庫専用ステーションかモード管理無しならばモードは確認しない。</jp>
            //<en> If the sending station handles both storage/retrieval, the mode needs to be checked. If the station only</en>
            //<en> handles storage, or if the station has no control over work mode, checking of mode is not necessary.</en>
            if (Station.STATION_TYPE_INOUT.equals(sourceSt.getStationType())
                    && !Station.MODE_TYPE_NONE.equals(sourceSt.getModeType()))
            {
                //<jp> 搬送元Stationに作業モード切替要求が来てはいないか？</jp>
                //<jp> 搬送元Stationの作業モードが入庫か？(つまりニュートラルや出庫モードはNG)</jp>
                //<en> Whether/not the sending station received the request of work mode switching.</en>
                //<en> Is the work mode of this sending station 'storage'? (neutral or retrirval are NOT acceptable)</en>
                System.out.println("StorageSender Station Is work mode of the sending station 'storage'? = "
                        + sourceSt.getCurrentMode() + "this station number = " + sourceSt.getStationNo());
                System.out.println("StorageSender Whether/not this sending station received the request of work mode switching = "
                        + sourceSt.getModeRequest());
                if ((!Station.MODE_REQUEST_NONE.equals(sourceSt.getModeRequest()))
                        || (Station.CURRENT_MODE_NEUTRAL.equals(sourceSt.getCurrentMode()))
                        || (Station.CURRENT_MODE_RETRIEVAL.equals(sourceSt.getCurrentMode())))
                {
                    return false;
                }
            }
        }

        //<jp> 今回の搬送データの状態が到着の場合、搬送指示済件数に自身が含まれるので</jp>
        //<jp> 結果から-1して比較する。</jp>
        //<en> If the status of this carru data 'arrival', this data is already included in the number of carrying instruction given;</en>
        //<en> therefore subtract 1 from the result and compare.</en>

        // CarryInfo検索用
        CarryInfoSearchKey carryKey = new CarryInfoSearchKey();

        // 検索キーセット
        carryKey.clear();
        carryKey.setPalletIdCollect();
        String[] cmd = {
                CarryInfo.CMD_STATUS_INSTRUCTION,
                CarryInfo.CMD_STATUS_WAIT_RESPONSE,
                CarryInfo.CMD_STATUS_COMP_RETRIEVAL,
                CarryInfo.CMD_STATUS_ARRIVAL
        };
        carryKey.setCmdStatus(cmd, true);
        carryKey.setSourceStationNo(sourceSt.getStationNo());

        if (cInfo.getCmdStatus().equals(CarryInfo.CMD_STATUS_ARRIVAL))
        {
            //<jp> 搬送元ステーションに関連するCarryInformationが規定数を越えていないか</jp>
            //<en> Whether/not the CarryInformation relevant to the sending station exceeded the regulated volume.</en>
            if (sourceSt.getMaxInstruction() <= _carryInfoHandler.count(carryKey) - 1)
            {
                //<jp> 搬送指示可能数オーバー。</jp>
                //<en> Exceeding the MAX. number of carry information</en>
                return false;
            }
        }
        else
        {
            // 2010/05/11 Y.Osawa UPD ST
            //            System.out.println("MAX. numbers of pallet of StorageSender Station?= " + sourceSt.getMaxPalletQty());
            System.out.println("MAX. numbers of pallet of StorageSender Station?= " + sourceSt.getMaxInstruction());
            // 2010/05/11 Y.Osawa UPD ED
            System.out.println("Current pallet numbers of StorageSender Station?= " + _carryInfoHandler.count(carryKey));
            //<jp> 搬送元ステーションに関連するCarryInformationが規定数を越えていないか</jp>
            //<en> Whether/not the CarryInformation relevant to the sending station exceeded the regulated volume.</en>
            if (sourceSt.getMaxInstruction() <= _carryInfoHandler.count(carryKey))
            {
                //<jp> 搬送指示可能数オーバー。</jp>
                //<en> Exceeding the MAX. number of carry information</en>
                System.out.println("StorageSender exceeding the MAX. number of carry information");
                return false;
            }
        }

        // 直行の場合搬送先Stationの確認
        if (CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(cInfo.getCarryFlag()))
        {
            // 2010/05/11 Y.Osawa UPD ST
            //            // 検索キーセット
            //            carryKey.clear();
            //            carryKey.setPalletIdCollect();
            //            String[] destCmd = {
            //                    CarryInfo.CMD_STATUS_INSTRUCTION,
            //                    CarryInfo.CMD_STATUS_WAIT_RESPONSE,
            //                    CarryInfo.CMD_STATUS_COMP_RETRIEVAL,
            //                    CarryInfo.CMD_STATUS_ARRIVAL
            //            };
            //            carryKey.setCmdStatus(destCmd, true);
            //            carryKey.setDestStationNo(cInfo.getDestStationNo());
            //
            //            // 搬送先ステーションに関する情報
            //            StationSearchKey stKey = new StationSearchKey();
            //            stKey.setStationNo(cInfo.getDestStationNo());
            //            Station destSt = (Station)_stationHandler.findPrimary(stKey);
            //
            //            // 出庫指示可能数
            //            if (destSt.getMaxPalletQty() <= _carryInfoHandler.count(carryKey))
            //            {
            //                //<jp> 出庫指示可能数をオーバーしている。</jp>
            //                //<en> The number of retrieval instruction exceeded the available number set by the regulation</en>
            //                System.out.println("StorageSender ::: Exceeding the MAX. number of retrieval instructions  ");
            //                return false;
            //            }
            //
            //            //<jp> 搬送先ステーションの中断中フラグを確認。</jp>
            //            //<en> Checks the interrupt flag of the receiving station.</en>
            //            if (Station.SUSPEND_ON.equals(destSt.getSuspend()))
            //            {
            //                //<jp> 中断中ならば即出庫は行われないと判断する。</jp>
            //                //<en> If it has been interrupted,ite determinates that immediate retrieval will not be carried out.</en>
            //                return false;
            //            }
            //
            //            //<jp> 搬送先ステーションのモード切替種別をもとに、搬送先のモードチェック処理を分岐</jp>
            //            //<en> Branch the mode check processing of receiving station according to the mode switch type of receiving station. </en>
            //            if (Station.MODE_TYPE_AUTO_CHANGE.equals(destSt.getModeType()))
            //            {
            //                //<jp> 搬送先ステーションに搬送作業が存在し、かつそのデータの搬送順が今回の直行より先ならば</jp>
            //                //<jp> モード切替と搬送は行なわず、搬送不可と判断する。</jp>
            //                carryKey.clear();
            //                String[] cflg = {
            //                        CarryInfo.CARRY_FLAG_STORAGE,
            //                        CarryInfo.CARRY_FLAG_DIRECT_TRAVEL
            //                };
            //                carryKey.setCarryFlag(cflg, true);
            //                carryKey.setSourceStationNo(destSt.getStationNo());
            //                carryKey.setCmdStatus(CarryInfo.CMD_STATUS_START);
            //                CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
            //                if (!carryControl.checkCarryPriority(cInfo, carryKey))
            //                {
            //                    System.out.println("Skip the process as there is the direct transfer data for this station. StNo:::"
            //                            + sourceSt.getStationNo());
            //                    return false;
            //                }
            //
            //                //<jp> 自動モード切替ステーションであれば、モード切替チェック＆送信</jp>
            //                //<en>Check the mode switch and send if the station operates the automatic mode switching.</en>
            //                if (retrievalCheck(destSt))
            //                {
            //                    //<jp> 搬送先ステーション条件ＯＫ</jp>
            //                    //<en>Condition of receiving station is checked and found accpeptable</en>
            //                    return true;
            //                }
            //                else
            //                {
            //                    //<jp> 搬送先ステーションモード切替指示送信or切替不可</jp>
            //                    //<en>Wheather the receiving station mode is "send switching instruction" or "unable to switch"</en>
            //                    return false;
            //                }
            //            }
            // 搬送先ステーションに関する情報
            StationSearchKey stKey = new StationSearchKey();
            stKey.setStationNo(cInfo.getDestStationNo());
            Station destSt = (Station)_stationHandler.findPrimary(stKey);
            // 搬送先ステーションチェック
            if (!destRightStation(cInfo, sourceSt, destSt))
            {
                return false;
            }
            // 2010/05/11 Y.Osawa UPD ST
        }

        //<jp> 搬送元Stationが中断中になってはいないか？</jp>
        //<en> Whether/not the operation of sending station is on suspention.</en>
        System.out.println("StorageSender Whether/not the operation of sending station is on suspention. = "
                + sourceSt.getSuspend());
        if (Station.SUSPEND_ON.equals(sourceSt.getSuspend()))
        {
            return false;
        }
        return true;
    }

    // 2010/05/11 Y.Osawa ADD ST
    /**<jp>
     * 搬送先ステーションの状態をチェックします。直行時に使用します。
     * 搬送先Stationが作業モード切替要求中ではない
     * 作業モード管理を行う場合、入出庫兼用ステーションなら搬送先Stationの作業モードが出庫
     * 搬送先Stationが中断中ではない
     * 出庫指示可能件数については搬送先ステーションに関連するCarryInformationが規定数を越えていないかをCheckする。
     * 該当ステーションが搬送元になっているCarryInformationについては考慮しない。
     * @param  cInfo       搬送情報(CarryInformation)
     * @param  sourceSt    搬送元ステーション
     * @param  destSt      搬送先ステーション
     * @return             搬送可能と確認できた場合はtrue、ダメな場合がfalse
     * @throws Exception   データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    protected boolean destRightStation(CarryInfo cInfo, Station sourceSt, Station destSt)
            throws Exception
    {
        // CarryInfo検索用
        CarryInfoSearchKey carryKey = new CarryInfoSearchKey();
        // 検索キーセット
        carryKey.clear();
        carryKey.setPalletIdCollect();
        String[] destCmd = {
                CarryInfo.CMD_STATUS_INSTRUCTION,
                CarryInfo.CMD_STATUS_WAIT_RESPONSE,
                CarryInfo.CMD_STATUS_COMP_RETRIEVAL,
                CarryInfo.CMD_STATUS_ARRIVAL
        };
        carryKey.setCmdStatus(destCmd, true);
        carryKey.setDestStationNo(destSt.getStationNo());

        // 出庫指示可能数
        // 代表ステーションの時は紐づくステーションが使用可能かチェックする
        // 使用可能なステーションの出庫指示可能数の合計が代表ステーションの出庫指示可能数より
        // 少ない場合は使用可能なステーションの出庫指示可能数の合計まで指示を送信する
        int maxQty = 0;
        // 代表ステーション
        if (Station.WORKPLACE_TYPE_MAIN_STATION.equals(destSt.getWorkplaceType()))
        {
            maxQty = getMaxPalletQtyOfMainSt(cInfo, sourceSt, destSt);
        }
        // 通常ステーション
        else
        {
            maxQty = destSt.getMaxPalletQty();
        }

        int carryCount = getCarryInfoHandler().count(carryKey);

        if (maxQty <= carryCount)
        {
            //<jp> 出庫指示可能数をオーバーしている。</jp>
            //<en> The number of retrieval instruction exceeded the available number set by the regulation</en>
            System.out.println("StorageSender ::: Exceeding the MAX. number of retrieval instructions  ");
            return false;
        }

        //<jp> 搬送先ステーションの中断中フラグを確認。</jp>
        //<en> Checks the interrupt flag of the receiving station.</en>
        if (Station.SUSPEND_ON.equals(destSt.getSuspend()))
        {
            //<jp> 中断中ならば即出庫は行われないと判断する。</jp>
            //<en> If it has been interrupted,ite determinates that immediate retrieval will not be carried out.</en>
            return false;
        }

        //<jp> 搬送先ステーションのモード切替種別をもとに、搬送先のモードチェック処理を分岐</jp>
        //<en> Branch the mode check processing of receiving station according to the mode switch type of receiving station. </en>
        if (Station.MODE_TYPE_AUTO_CHANGE.equals(destSt.getModeType()))
        {
            //<jp> 搬送先ステーションに搬送作業が存在し、かつそのデータの搬送順が今回の直行より先ならば</jp>
            //<jp> モード切替と搬送は行なわず、搬送不可と判断する。</jp>
            carryKey.clear();
            String[] cflg = {
                    CarryInfo.CARRY_FLAG_STORAGE,
                    CarryInfo.CARRY_FLAG_DIRECT_TRAVEL
            };
            carryKey.setCarryFlag(cflg, true);
            carryKey.setSourceStationNo(destSt.getStationNo());
            carryKey.setCmdStatus(CarryInfo.CMD_STATUS_START);
            CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
            if (!carryControl.checkCarryPriority(cInfo, carryKey))
            {
                System.out.println("Skip the process as there is the direct transfer data for this station. StNo:::"
                        + sourceSt.getStationNo());
                return false;
            }

            //<jp> 自動モード切替ステーションであれば、モード切替チェック＆送信</jp>
            //<en>Check the mode switch and send if the station operates the automatic mode switching.</en>
            if (retrievalCheck(destSt))
            {
                //<jp> 搬送先ステーション条件ＯＫ</jp>
                //<en>Condition of receiving station is checked and found accpeptable</en>
                return true;
            }
            else
            {
                //<jp> 搬送先ステーションモード切替指示送信or切替不可</jp>
                //<en>Wheather the receiving station mode is "send switching instruction" or "unable to switch"</en>
                return false;
            }
        }
        // 2010/05/11 Y.Osawa ADD ST
        // 自動モード切替ではない場合、搬送先ステーションのモードをチェックする
        else
        {
            //<jp> 搬送先ステーションの作業モード確認</jp>
            //<jp> 搬送先Stationが入出庫兼用ならばモードの確認が必要。入庫専用ステーションかモード管理無しならばモードは確認しない。</jp>
            //<en> Checks the work mode of the receiving station.</en>
            //<en> The work mode needs to be checked if receiving station is used for both storage and retrieval.</en>
            //<en> If it is used for storage only, or if no mode control is conducted,no checking is necessary.</en>
            if (Station.STATION_TYPE_INOUT.equals(destSt.getStationType())
                    && !Station.MODE_TYPE_NONE.equals(destSt.getModeType()))
            {
                //<jp> 搬送先ステーションが以下のいずれかの状態であれば、即出庫は行われないと判断する。</jp>
                //<jp> 作業モード切替要求が入庫または出庫モード切替要求中</jp>
                //<jp> 作業モードがニュートラルまたは入庫モード</jp>
                //<en> If the status of receiving station falls on one of the following, it determines that no immdediate</en>
                //<en> retrieval will be carried out.</en>
                //<en> Requesting to switch the work mode of storage/retrieval</en>
                //<en> THe work mode is neutral or storage.</en>
                if ((!Station.MODE_REQUEST_NONE.equals(destSt.getModeRequest()))
                        || (Station.CURRENT_MODE_NEUTRAL.equals(destSt.getCurrentMode()))
                        || (Station.CURRENT_MODE_STORAGE.equals(destSt.getCurrentMode())))
                {
                    return false;
                }
            }
        }

        // 出庫指示可能数（ステーションの最大出庫指示可能数からそのステーションへの搬送数を引いた数）をセットします。
        setPalletMaxQty(destSt.getMaxPalletQty() - carryCount);
        // 2010/05/11 Y.Osawa ADD ED

        return true;
    }

    /**
     * 代表ステーション用最大出庫指示可能数取得処理
     * @param  cInfo           搬送対象CarryInformation
     * @param  sourceSt        搬送元ステーション
     * @param  mainSt          搬送先代表ステーション
     * @return 代表ステーションに紐づく各ステーションが使用可能かチェックし、使用可能なステーションの最大出庫指示件数の
     *         合計を取得する。
     *         代表ステーションの最大出庫指示可能数より使用可能な各ステーションの最大出庫指示可能数の合計の方が大きければ
     *         代表ステーションの最大出庫指示可能数を返す。それ以外であれば使用可能なステーションの最大出庫指示件数の合計を返す。
     * @throws Exception 例外が発生した場合に通知されます。
     */
    protected int getMaxPalletQtyOfMainSt(CarryInfo cInfo, Station sourceSt, Station mainSt)
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

        // 代表ステーションに紐づくステーションのチェックを行う
        for (Station destSt : stations)
        {
            //<jp> 搬送先決定および搬送ルートチェック</jp>
            //<en> Determination of the destination, transport route checking</en>
            if (!rc.storageDetermin(pallet, sourceSt, destSt))
            {
                //<jp> 搬送ルートが無い</jp>
                //<en> Transport route cannot be found</en>
                continue;
            }

            //<jp> 搬送先ステーションの条件チェック</jp>
            //<en> Check the condition with the receiving station</en>
            if (!destRightStation(cInfo, sourceSt, destSt))
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

    // 2010/05/11 Y.Osawa ADD ED

    /**<jp>
     * 搬送ルートのチェックを行います。搬送先が倉庫、作業場など送信可能ステーションではない場合
     * 搬送先決定処理を行い、cInfoの搬送先を更新します。
     * @param  cInfo       搬送情報(CarryInformation)
     * @param  sourceSt    搬送元ステーション
     * @return             搬送可能なルートが正常の場合はtrue、搬送先が見つからないか搬送ルートNGの場合はfalse
     * @throws Exception   データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check the transport route. In case the receiver is the station that cannot receive transmission, e.g. warehouse or
     * workshop and has no on-line environment, determination of destination must be made and the destination of cInfo must
     * be renewed.
     * @param  cInfo       CarryInformation
     * @param  sourceSt    Sending station
     * @return             If the route is normal and acceptablle of the carry, it returns 'true'. If no destination is found,
     *                      or if the the selected transport route is not acceptable, it returns 'false'.
     * @throws Exception   Notifies if trouble occurs in reading/writing in database.
     </en>*/
    protected boolean destDetermin(CarryInfo cInfo, Station sourceSt)
            throws Exception
    {
        try
        {
            // Pallet検索・更新用
            PalletHandler palletHandelr = new PalletHandler(_conn);
            PalletSearchKey palletKey = new PalletSearchKey();

            Station destSt = StationFactory.makeStation(_conn, cInfo.getDestStationNo());
            System.out.println("No. of receiving station in StorageSender destDetermin = " + cInfo.getDestStationNo());
            if (destSt instanceof Shelf)
            {
                //<jp> 搬送先が棚の場合でも、搬送元ステーションが荷姿チェックありでかつ、搬送データが在庫確認以外の場合</jp>
                //<jp> 再度棚決定が必要なため、搬送先を倉庫に変更する。</jp>
                //<en> Though in case the carry is destined to the shelf, if sending station conducts the load size checking</en>
                //<en> and if the carry data is other than inventory checking:</en>
                //<en> Once again the determination of the location is necessary. change the destination to the warehouse.</en>
                if (Station.LOAD_SIZE_ON.equals(sourceSt.getLoadSize())
                        && !CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(cInfo.getRetrievalDetail()))
                {
                    //<jp> 元々の搬送先を空棚にする前に、他のパレットがその棚に入庫されていないかどうか </jp>
                    //<jp> 確認する処理を追加 </jp>
                    //<en> Add a confirmation process wheather other pallets have been stored in that location or not before </en>
                    //<en> emptying the original receiving location. </en>

                    //<jp> 倉庫インスタンス作成</jp>
                    WareHouse wh = (WareHouse)StationFactory.makeStation(_conn, destSt.getWhStationNo());

                    // 副問い合わせ用キー
                    // 同一棚に再入庫しないステーションへ出庫したピッキング出庫または積増入庫で、
                    // 出庫した棚が空棚（出庫完了または到着または開始（搬送区分が入庫））のパレット、
                    // またはユニット出庫の出庫完了状態のパレットは除外して検索する
                    CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
                    CarryInfoSearchKey cskey = carryControl.getEmptyShelfPallet();

                    ShelfSearchKey sSkey = new ShelfSearchKey();
                    ShelfHandler shelfHandler = new ShelfHandler(getConnection());

                    // 棚間移動で引き当てた棚を更新してしまう可能性があるのでロックを行う
                    sSkey.setStationNo(destSt.getStationNo());
                    shelfHandler.lock(sSkey);

                    // 主問い合わせ用キー
                    PalletSearchKey pkey = new PalletSearchKey();
                    pkey.setCurrentStationNo(destSt.getStationNo(), "=", "((", "", true);
                    pkey.setKey(Pallet.PALLET_ID, cskey, "!=", "", ")", false);
                    pkey.setKey(CarryInfo.DEST_STATION_NO, destSt.getStationNo(), "=", "(", "", true);
                    pkey.setKey(CarryInfo.CARRY_FLAG, CarryInfo.CARRY_FLAG_RACK_TO_RACK);
                    pkey.setKey(CarryInfo.PALLET_ID, Pallet.PALLET_ID, "=", "", "))", true);
                    pkey.setPalletId(cInfo.getPalletId(), "!=");
                    if (palletHandelr.count(pkey) == 0)
                    {
                        Shelf shf = (Shelf)destSt;

                        // フリーアロケーション運用の場合、荷幅、棚使用フラグをフリーに更新
                        if (WareHouse.FREE_ALLOCATION_ON.equals(wh.getFreeAllocationType()))
                        {
                            FreeAllocationShelfOperator freeshelfop =
                                    new FreeAllocationShelfOperator(getConnection(), shf.getStationNo());
                            freeshelfop.alterFreeWidth();
                        }

                        //<jp> 元々入庫予定の搬送先の棚の在荷をOFF（空棚）にする。</jp>
                        //<en> Change the load presence of the destined location to 'OFF, empty' initially planned for this storage.</en>
                        ShelfAlterKey sAkey = new ShelfAlterKey();
                        ShelfHandler sHandler = new ShelfHandler(_conn);
                        sAkey.clear();
                        sAkey.setStationNo(shf.getStationNo());
                        sAkey.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
                        sHandler.modify(sAkey);
                    }

                    //<jp> 搬送先を倉庫に変更</jp>
                    //<en> Change the destination to the warehouse.</en>
                    destSt = wh;
                    System.out.println("StorageSender The station No. that empty location to be searched once again. = "
                            + destSt);
                }
            }

            // 2010/05/11 Y.Osawa ADD ST
            // 搬送先が代表ステーションの場合（直行）はルートチェックを行わない（別のロジックで紐づくステーションのチェックを行う）
            if (Station.WORKPLACE_TYPE_MAIN_STATION.equals(destSt.getWorkplaceType()))
            {
                System.out.println("Designation of destination and tranport route check in StorageSender destDetermin  ST No. = "
                        + destSt.getStationNo());
                //<jp> 搬送指示可能</jp>
                //<en> Able to send carry instruction</en>
                return true;
            }
            // 2010/05/11 Y.Osawa ADD ED

            //<jp> 搬送先決定処理および搬送ルートチェック。</jp>
            //<jp> ルートチェックのため一時的に搬送元ステーションを置換え</jp>
            //<en> Determination of the destination and check the transport route</en>
            //<en> Temporarily replace the sending station for route checking.</en>

            // 検索キーセット
            palletKey.clear();
            palletKey.setPalletId(cInfo.getPalletId());
            // 検索
            Pallet pallet = (Pallet)palletHandelr.findPrimary(palletKey);

            // 置き換え
            pallet.setCurrentStationNo(sourceSt.getStationNo());

            if (_routeController.storageDetermin(pallet, sourceSt, destSt))
            {
                // リジェクトSTの場合は空棚なしとする
                if (!(_routeController.getDestStation() instanceof Shelf))
                {
                    if ((destSt instanceof Shelf) || (destSt instanceof WareHouse))
                    {
                        // 6022036=空棚がありません。搬送元ステーション={0}
                        Object[] tObj = new Object[1];
                        tObj[0] = sourceSt.getStationNo();
                        RmiMsgLogClient.write(6022036, this.getClass().getSimpleName(), tObj);
                        System.out.println("StorageSender Check for determination of receiving station/carry route determined unacceptable by destDetermin.");
                        return false;
                    }
                }

                System.out.println("Designation of destination and tranport route check in StorageSender destDetermin  ST No. = "
                        + destSt.getStationNo());
                System.out.println("replace hte destination with the one RouteController has selected in StorageSender destDetermin ST No. = "
                        + (_routeController.getDestStation().getStationNo()));
                //<jp> 搬送先をRouteControllerクラスが決定した搬送先に置き換える。</jp>
                //<en> Replace the destination with the new oner that RouteController has selected.</en>
                if (!destSt.getStationNo().equals(_routeController.getDestStation().getStationNo()))
                {
                    //<jp> 搬送先StationNumber、アイルStaitonNumberをCarryInformationにセット</jp>
                    //<en> Set the receiving StationNumber and aisle StaitonNumber in CarryInformation.</en>
                    System.out.println("In StorageSender destDetermin, set receiving StationNumber and aisle StationNumber in CarryInformation");

                    // 各テーブルを搬送先に更新する
                    updateDestSt(cInfo, _routeController);
                }
            }
            else
            {
                if (_routeController.getRouteStatus() == RouteController.LOCATION_EMPTY)
                {
                    // 6022036=空棚がありません。搬送元ステーション={0}
                    Object[] tObj = new Object[1];
                    tObj[0] = sourceSt.getStationNo();
                    RmiMsgLogClient.write(6022036, this.getClass().getSimpleName(), tObj);
                }
                else
                {
                    // 6022035=搬送可能な搬送先が決定できませんでした。搬送元ステーション={0} ルートチェック結果={1}
                    Object[] tObj = new Object[2];
                    tObj[0] = sourceSt.getStationNo();
                    tObj[1] = _routeController.getRouteStatus();
                    RmiMsgLogClient.write(6022035, this.getClass().getSimpleName(), tObj);
                }
                System.out.println("StorageSender Check for determination of receiving station/carry route determined unacceptable by destDetermin.");
                return false;
            }

            // フリーアロケーション倉庫の場合、搬送情報の制御情報を更新する
            if (WareHouse.FREE_ALLOCATION_ON.equals(getAreaCtlr().getFreeAllocationOfWarehouse(
                    getRouteController().getDestStation().getWhStationNo())))
            {
                // 棚情報、荷姿マスタから荷幅、荷長、荷高を取得する
                ShelfSearchKey sskey = new ShelfSearchKey();
                ShelfHandler sHandler = new ShelfHandler(getConnection());
                sskey.clear();
                sskey.setStationNo(getRouteController().getDestStation().getStationNo());
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
        }
        //<jp> データ更新条件に誤りがあった場合に発生する。</jp>
        //<en> Occurs if there is any error in updated data.</en>
        catch (InvalidDefineException e)
        {
            carryFailure(cInfo);
            return false;
        }
        //<jp> StationFactory.makeStationで発生</jp>
        //<en> Occurs in StationFactory.makeStation</en>
        catch (NotFoundException e)
        {
            carryFailure(cInfo);
            return false;
        }

        //<jp> 搬送指示可能</jp>
        //<en> Able to send carry instruction</en>
        return true;
    }

    /**<jp>
     * 各テーブルを搬送先に更新します。
     * @param  cInfo       搬送情報(CarryInformation)
     * @param  routeCtlr   ルートコントローラ
     * @throws Exception   全ての例外が通知されます。
     </jp>*/
    protected void updateDestSt(CarryInfo cInfo, RouteController routeCtlr)
            throws Exception
    {
        // CarryInfo更新用
        CarryInfoAlterKey carryAlterKey = new CarryInfoAlterKey();
        // 検索キーセット
        carryAlterKey.clear();
        carryAlterKey.setCarryKey(cInfo.getCarryKey());
        carryAlterKey.updateDestStationNo(routeCtlr.getDestStation().getStationNo());
        carryAlterKey.updateAisleStationNo(routeCtlr.getDestStation().getAisleStationNo());
        carryAlterKey.updateLastUpdatePname(getClass().getSimpleName());
        // 更新
        getCarryInfoHandler().modify(carryAlterKey);
        //搬送指示送信時に更新された内容を反映させるため（セットし直さなければ倉庫Noのままになるため）
        cInfo.setDestStationNo(routeCtlr.getDestStation().getStationNo());

        boolean history = false;
        if (!cInfo.getRetrievalStationNo().equals(cInfo.getDestStationNo())
                && !StringUtil.isBlank(cInfo.getRetrievalStationNo()))
        {
            // 戻り入庫が棚違いの場合は在庫更新履歴を登録する
            history = true;

            // 積増入庫の場合は作業、実績送信情報の実績棚を更新する
            if (CarryInfo.RETRIEVAL_DETAIL_ADD_STORING.equals(cInfo.getRetrievalDetail()))
            {
                updateResultLocation(cInfo.getCarryKey(),
                        getAreaCtlr().toParamLocation(routeCtlr.getDestStation().getStationNo()));
            }
        }
        // 棚変更（パレット、在庫、マルチ引当分作業情報）
        getStockCtlr().updateAsrsLocation(cInfo, null, history);
    }

    /**<jp>
     * 搬送指示テキストの編集を行い、搬送指示を送信します。
     * 搬送指示テキストの送信はRMIを経由して行われます。正常に行われた場合はCarryInformationの搬送状態を応答待ちに変更します。
     * @param  cInfo           搬送対象CarryInformationインスタンス
     * @param  gc              搬送指示テキスト送信対象となるGroupControllerインスタンス
     * @throws Exception       データベースの読書きで障害が発生した場合に通知されます。
     * @throws Exception       送信タスクに対するテキスト送信で障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Edit the text of carry instruction and send.
     * The transmission of carry instruction text will be sent via RMI. If it is normally done, alter the carry state
     * to 'waiting for reply'.
     * @param  cInfo           instance of CarryInformation
     * @param  gc              instatnce of GroupController to which the carry instruction text is sent to
     * @throws Exception       Notifies if trouble occurs in reading/writing in database.
     * @throws Exception       Notifies if there are any errors with the updated instance.
     </en>*/
    protected void sendText(CarryInfo cInfo, GroupController gc , Station st)
            throws Exception
    {
        Object[] param = new Object[2];
        DecimalFormat fmt = new DecimalFormat("00");

        try
        {
            //<jp> 搬送データが送信可能な場合はテキスト送信を行い、搬送状態を応答待ちに変更する。</jp>
            //<jp> CarryInfomationから送信用電文を作成する。</jp>
            //<en> If it is possible to send the carry data, send the text then change the carry state to 'waiting for reply'.</en>
            //<en> Creating the communciation message to send from CarryInfomation</en>
            As21Id05 id05 = new As21Id05(cInfo, _conn);
            String sendMsg = id05.getSendMessage();
            System.out.println("StorageSender Communication message length of carry instruction = " + sendMsg.length());
            System.out.println("StorageSender Communication message of carry instruction = " + sendMsg);
            //<jp> RMIを利用してAs21Senderのwriteメソッドをコール</jp>
            //<en> Call the write method of As21Sender using RMI.</en>
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

            //<jp> 搬送指示を送ったCarryInformationを応答待ちに更新</jp>
            //<en> Renew the status of CarryInformation, which the instruciton has just been sent, to 'waiting for reply'.</en>
            // CarryInfo更新用
            CarryInfoHandler carryInfoHandler = new CarryInfoHandler(_conn);
            CarryInfoAlterKey carryAlterKey = new CarryInfoAlterKey();
            // 検索キーセット
            carryAlterKey.clear();
            carryAlterKey.setCarryKey(cInfo.getCarryKey());
            carryAlterKey.updateCmdStatus(CarryInfo.CMD_STATUS_WAIT_RESPONSE);
            carryAlterKey.updateLastUpdatePname(getClass().getSimpleName());

            // 更新
            carryInfoHandler.modify(carryAlterKey);

            //<jp> テキスト送信前にトランザクションを確定させる。</jp>
            //<en>Commit the transaction before sending the text.</en>
            _conn.commit();

            rmiSndC.write("AGC" + fmt.format(Integer.valueOf(gc.getNo())), param);
            rmiSndC = null;
            param[0] = null;
        }
        //<jp> トランザクション確定時に障害があった場合に発生する。</jp>
        //<en> Occurs if trouble occurs when fixing the transaction.</en>
        catch (SQLException e)
        {
            logSQLException(e);
            throw new ReadWriteException(e);
        }
        // 2010/07/30 Y.Osawa ADD ST
        catch (NotBoundException e)
        {
            //<jp> 6026615=AGCとの接続が確立されていません。搬送要求テキストを送信できませんでした。mckey={0}</jp>
            //<en> 6026615=Cannot send the transfer request text since SRC is not connected. mckey={0}</en>
            Object[] objAgc = new Object[1];
            objAgc[0] = cInfo.getCarryKey();
            RmiMsgLogClient.write(6026615, LogMessage.F_ERROR, this.getClass().getName(), objAgc);
            carryRetry(cInfo);
//            carryFailure(cInfo);
        }
        // 2010/07/30 Y.Osawa ADD ED
        //<jp> テキスト編集時に誤りがあった場合に発生する。</jp>
        //<en> Occurs if there are any error when editing text.</en>
        catch (InvalidProtocolException e)
        {
            //<jp> 6026042=搬送情報取得時に障害発生。例外：{0}</jp>
            //<en> 6026042=Trouble occurred in obtaining transfer data. Exception:{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6026042, e), this.getClass().getName());
            carryFailure(cInfo);
        }
        //<jp> データ更新内容に誤りがあった場合に発生する。</jp>
        //<en> Occurs if there are any error in updated data.</en>
        catch (InvalidStatusException e)
        {
            //<jp> 6026042=搬送情報取得時に障害発生。例外：{0}</jp>
            //<en> 6026042=Trouble occurred in obtaining transfer data. Exception:{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6026042, e), this.getClass().getName());
            carryFailure(cInfo);
        }
        //<jp> データ更新内容に誤りがあった場合に発生する。</jp>
        //<en> Occurs if there are any error in updated data.</en>
        catch (InvalidDefineException e)
        {
            //<jp> 6026042=搬送情報取得時に障害発生。例外：{0}</jp>
            //<en> 6026042=Trouble occurred in obtaining transfer data. Exception:{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6026042, e), this.getClass().getName());
            carryFailure(cInfo);
        }
        //<jp> データ更新内容に誤りがあった場合に発生する。</jp>
        //<en> Occurs if there are any error in updated data.</en>
        catch (IOException e)
        {
            // 2010/07/30 Y.Osawa UPD ST
            //            //<jp> 通信上にて電文送出できず</jp>
            //            //<jp> 6026058=テキストを送信できませんでした。{0}</jp>
            //            //<en> Communication message could not be sent out.</en>
            //            //<en> 6026058=Cannot send the text. {0}</en>
            //            RmiMsgLogClient.write(new TraceHandler(6026058, e), this.getClass().getName());
            //<jp> 通信上にて電文送出できず</jp>
            //<jp> 6026616=テキストを送信できませんでした。mckey={0}</jp>
            //<en> Communication message could not be sent out.</en>
            //<en> 6026616=Cannot send the text. mckey={0}</en>
            Object[] objAgc = new Object[1];
            objAgc[0] = cInfo.getCarryKey();
            RmiMsgLogClient.write(new TraceHandler(6026616, e), this.getClass().getName(), objAgc);
            // 2010/07/30 Y.Osawa UPD ED
            carryRetry(cInfo);
//            carryFailure(cInfo);
        }
    }

    /**<jp>
     * 指定されたCarryInforamtionインスタンスの搬送状態を異常に変更します。
     * 異常に変更されたCarryInformationは以後搬送対象となりません。
     * また、更新内容を反映させるために、このメソッド内でトランザクションを確定します。
     * @param  failureTarget   搬送対象CarryInformationインスタンス
     * @throws Exception       データベースの読書きで障害が発生した場合に通知されます。
     * @throws Exception       インスタンスの更新内容に不正があった場合に通知されます。
     </jp>*/
    /**<en>
     * Alter the carry status of the specified CarryInforamtion instance to 'error'.
     * Once changed to error, this CarryInformation will be used for carry any longer.
     * Also in order to mirror the updated contents, fix the transaction within this method.
     * @param  failureTarget   instance of CarryInformation for this carry
     * @throws Exception       Notifies if trouble occurs in reading/writing in database.
     * @throws Exception       Notifies if there are any errors with the updated instance.
     </en>*/
    protected void carryFailure(CarryInfo failureTarget)
            throws Exception
    {
        try
        {
            if (failureTarget != null)
            {
                //<jp> 現在までの更新内容をrollback。</jp>
                //<en> Rollback the updates made up to this moment.</en>
                _conn.rollback();
                //<jp> 状態を異常に更新</jp>
                //<en> Renew the status to 'error'.</en>
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
                //<en> Fix the transaction.</en>
                _conn.commit();
            }
        }
        catch (SQLException e)
        {
            logSQLException(e);
            throw new ReadWriteException(e);
        }
    }

    /**<jp>
     * 指定されたCarryInforamtionインスタンスの搬送状態を未指示に変更します。
     * 未指示に変更されたCarryInformationは再度搬送対象となします。
     * 指示送信時に受信側が確立されていない場合に再度指示を出すため未指示に戻します。
     * また、更新内容を反映させるために、このメソッド内でトランザクションを確定します。
     * @param  retryTarget   搬送対象CarryInformationインスタンス
     * @throws Exception       データベースの読書きで障害が発生した場合に通知されます。
     * @throws Exception       インスタンスの更新内容に不正があった場合に通知されます。
     </jp>*/
    /**<en>
     * Changes the carrying state of instance CarryInforamtion specified to 'error'.
     * Once the state is changed to error, this CarryInformation will not be the object of carrying.
     * Also, in order to reflect the contents updated, the transaction is fixed in this method.
     * @param  retryTarget   Instance of CarryInformation
     * @throws Exception       Notifies if trouble occured in reading/writing database.
     * @throws Exception       Notifies if there was irregularity in updates of instances.
     </en>*/
    protected void carryRetry(CarryInfo retryTarget)
            throws Exception
    {
        try
        {
            if (retryTarget != null)
            {
                //<jp> 現在までの更新内容をrollback。</jp>
                //<en> Rolls back the contents updated until present.</en>
                _conn.rollback();

                String carryKey = retryTarget.getCarryKey();
                String sourceStationNo = retryTarget.getSourceStationNo();

                CarryInfoHandler cih = new CarryInfoHandler(_conn);
                CarryInfoAlterKey cakey = new CarryInfoAlterKey();

                //<jp> 搬送状態を開始に戻す</jp>
                //<en> Reset the carry status to 'start'.</en>
                cakey.clear();
                cakey.setCarryKey(carryKey);
                cakey.updateCmdStatus(CarryInfo.CMD_STATUS_START);
                cakey.updateLastUpdatePname(getClass().getSimpleName());
                cih.modify(cakey);

                StationOperator sOpe = new StationOperator(getConnection(), sourceStationNo);
                if (sOpe.getStation().isArrivalCheck())
                {
                    // 到着情報を未指示状態に戻す
                    ArrivalHandler arrivalHandler = new ArrivalHandler(getConnection());
                    ArrivalAlterKey arrivalAlterKey = new ArrivalAlterKey();

                    arrivalAlterKey.setStationNo(sourceStationNo);
                    arrivalAlterKey.setCarryKey(carryKey);
                    arrivalAlterKey.updateSendFlag(Arrival.ARRIVAL_NOT_SEND);
                    arrivalHandler.modify(arrivalAlterKey);
                }

                //<jp> トランザクションを確定する。</jp>
                //<en> Fix the transaction.</en>
                _conn.commit();
            }
        }
        catch (SQLException e)
        {
            Object[] tObj = new Object[1];
            tObj[0] = new Integer(e.getErrorCode());
            //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
            //<en> 6007030=Database error occured. error code={0}</en>
            RmiMsgLogClient.write(new TraceHandler(6007030, e), getClass().getName(), tObj);
            throw new ReadWriteException(e);
        }
    }

    /**<jp>
     * 指定された<code>Station</code>オブジェクトの作業モードの状態が搬送指示可能な状態かどうか検証します。
     * 出庫指示不可の状態（入庫モード、切替要求中など）であれば、作業モード切替指示（ID=42）を送信します。
     * 出庫指示可能な状態であればtrueを返します。
     * @param  st 搬送の有無を確認する搬送元ステーション
     * @return 出庫指示可能な作業モードの状態であればtrue、そうでなければfalseを返します。
     *         モード切替要求を行なった場合もfalseを返します。
     * @throws Exception データベースの読書きで障害が発生した場合に通知されます。
     * @throws Exception モード切替テキストの送信で障害が発生した場合に通知されます。
     * @throws Exception ステーションのモード更新に失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Examines whether/not the work mode of specified object <code>Station</code> allows the carrying instruction.
     * If carrying instruction cannot be sent (due to retrieval mode or requesting to switch mode), it sends the work mode
     * change request (ID=42).
     * Returns 'true' if carrying instruction can be sent.
     * @param  st Sending station with which to confirm the type of carry
     * @return Returns 'true' if the work mode accepts the retrieval instruction, , or 'false' if not.
     *         Returns 'false' also if mode switching is requested.
     * @throws Exception :Notifies if trouble occured in reading/writing database.
     * @throws Exception :Notifies if updating of station mode failed.
     </en>*/
    protected boolean retrievalCheck(Station st)
            throws Exception
    {
        // CarryInfo検索用
        CarryInfoSearchKey carryKey = new CarryInfoSearchKey();

        // Station変更用
        StationHandler sh = new StationHandler(_conn);
        StationAlterKey sk = new StationAlterKey();

        // 検索キーセット
        carryKey.clear();
        carryKey.setPalletIdCollect();
        String[] cmd = {
                CarryInfo.CMD_STATUS_INSTRUCTION,
                CarryInfo.CMD_STATUS_WAIT_RESPONSE,
                CarryInfo.CMD_STATUS_COMP_RETRIEVAL,
                CarryInfo.CMD_STATUS_ARRIVAL
        };
        carryKey.setCmdStatus(cmd, true);
        carryKey.setSourceStationNo(st.getStationNo());

        if (st.getCurrentMode().equals(Station.CURRENT_MODE_STORAGE))
        {
            if (st.getModeRequest().equals(Station.MODE_REQUEST_NONE))
            {
                //<jp> 入庫モードでかつモード切替要求が未要求であれば、</jp>
                //<jp> 搬送指示送信済みデータをカウントし、一件もなければモード切替要求を行なう。</jp>
                //<en> If the work is on storage mode and the request of switching mode has not been made, </en>
                //<en> counts the carrying instruction data already sent; or send request if there are none at all.</en>
                int cnt = _carryInfoHandler.count(carryKey);
                if (cnt == 0)
                {
                    //<jp> 出庫モードに切替え、データベースを更新する。</jp>
                    //<en> Switch the work mode to 'retrieval'; then update the database.</en>
                    sk.clear();
                    sk.setStationNo(st.getStationNo());
                    sk.updateModeRequest(Station.MODE_REQUEST_RETRIEVAL);
                    sk.updateModeRequestDate(new SysDate());
                    sh.modify(sk);

                    //<jp> テキスト送信前にトランザクションをCommit</jp>
                    //<en>Commit the transaction before sending the text.</en>
                    _conn.commit();

                    //<jp> 出庫モード切替指示を送信する。</jp>
                    //<en> Sends the work mode switch request (ID=42).</en>
                    SystemTextTransmission.id42send(st, WmsFormatter.getNumFormat(As21Id42.CLASS_RETRIEVAL_EMG), _conn);
                }

                //<jp> モード切替は行なうが、出庫指示可能な状態ではないのでfalseを返す。</jp>
                //<en> Mode will be switched, but it returns 'false' as the retrieval instruction cannot be accepted.</en>
                return false;
            }
            else
            {
                //<jp> モード切替要求中であれば、何もしない。</jp>
                //<en> If the request of mode switching is being made, nothing will be done.</en>
                return false;
            }
        }
        else if (st.getCurrentMode().equals(Station.CURRENT_MODE_RETRIEVAL))
        {
            //<jp> 出庫モードでかつモード切替要求が未要求であれば送信可能</jp>
            //<en> If the work mode is on 'retrieval' and the request of mode switch has not been made,</en>
            //<en> the instruction can be sent.</en>
            if (st.getModeRequest().equals(Station.MODE_REQUEST_NONE))
            {
                return true;
            }
            else
            {
                //<jp> モード切替要求中であれば、何もしない。</jp>
                //<en> If the request of mode switching is being made, nothing will be done.</en>
                return false;
            }
        }
        else if (st.getCurrentMode().equals(Station.CURRENT_MODE_NEUTRAL))
        {
            if (st.getModeRequest().equals(Station.MODE_REQUEST_NONE))
            {
                //<jp> ニュートラルモードでかつモード切替要求が未要求であれば、</jp>
                //<jp> 搬送指示送信済みデータをカウントし、一件もなければモード切替要求を行なう。</jp>
                //<en> If the work mode is neutral and the request of switching mode has not been made, counts the </en>
                //<en> data of carrying instruction already sent, then requests for the mode switch if there is none.</en>
                int cnt = _carryInfoHandler.count(carryKey);
                if (cnt == 0)
                {
                    //<jp> 出庫モードに切替え、データベースを更新する。</jp>
                    //<en> Switch to 'retrieval' mode then update the database.</en>
                    sk.clear();
                    sk.setStationNo(st.getStationNo());
                    sk.updateModeRequest(Station.MODE_REQUEST_RETRIEVAL);
                    sk.updateModeRequestDate(new SysDate());
                    sh.modify(sk);

                    //<jp> テキスト送信前にトランザクションをCommit</jp>
                    //<en>Commit the transaction before sending the text.</en>
                    _conn.commit();

                    //<jp> 出庫モード切替指示を送信する。</jp>
                    //<en> Sends mode switch request for 'retrieval'.</en>
                    SystemTextTransmission.id42send(st, WmsFormatter.getNumFormat(As21Id42.CLASS_RETRIEVAL_EMG), _conn);
                }

                //<jp> モード切替は行なうが、出庫指示可能な状態ではないのでfalseを返す。</jp>
                //<en> It switches the mode, but returns 'false' since retrieval instruction cannot be sent.</en>
                return false;
            }
            else
            {
                //<jp> モード切替要求中であれば、何もしない。</jp>
                //<en> If the request of mode switching is being made, nothing will be done.</en>
                return false;
            }
        }
        else
        {
            throw new InvalidDefineException("Invalid Mode");
        }
    }

    /**
     * 棚替えが生じた場合、パレットに関連する作業と入出庫実績送信情報の
     * 実績棚No.を書き換えます。<br>
     *
     * @param carryKey 棚替えが行われた搬送キー
     * @param newLocation 移動先の棚No.(ParamLocationフォーマット)
     * @throws Exception 例外が発生した場合に通知されます。
     */
    protected void updateResultLocation(String carryKey, String newLocation)
            throws Exception
    {
        AsWorkInfoController wic = new AsWorkInfoController(getConnection(), getClass());

        // 関連作業の取得
        WorkInfoHandler wiH = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey wiKey = new WorkInfoSearchKey();
        wiKey.setSystemConnKey(carryKey);

        WorkInfo[] works = (WorkInfo[])wiH.find(wiKey);

        // 作業の実績棚No.を更新
        wic.updateResultLocation(works, newLocation);

        // hostsendの実績棚No.を更新
        HostSendController hsc = new HostSendController(getConnection(), getClass());
        hsc.updateResultLocation(works, newLocation);
    }

    /**<jp>
     * 要求フラグを要求中にします。
     * 要求フラグが要求中の場合、搬送指示処理はwait状態にはなりません。
     * @param requested
     </jp>*/
    /**<en>
     * Change the reqest flag to 'requesting'.
     * If the flag states 'requesting', carry instruciton sill not be in wait state.
     * @param requested
     </en>*/
    protected void setRequest(boolean requested)
    {
        _request = requested;
    }

    /**<jp>
     * 要求フラグの現在の状態を返します。
     * 要求フラグが要求中の場合はtrue、そうでなければfalseを返します。
     * @return 要求フラグが要求中の場合はtrue、そうでなければfalse
     </jp>*/
    /**<en>
     * Returns the current status of the request flag.
     * It returns 'true' if the flag is requesting and 'false' if it is not.
     * @return 'true' if the flag is requesting and 'false' if it is not
     </en>*/
    protected boolean hasRequest()
    {
        return _request;
    }

    /**
     * carryInfoHandlerを返します。
     * @return carryInfoHandlerを返します。
     */
    protected CarryInfoHandler getCarryInfoHandler()
    {
        return _carryInfoHandler;
    }

    /**
     * hardZoneHandlerを返します。
     * @return hardZoneHandlerを返します。
     */
    protected HardZoneHandler getHardZoneHandler()
    {
        return _hardZoneHandler;
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
     * SQLExceptionをロギングします。
     * @param e Exception
     */
    protected void logSQLException(SQLException e)
    {
        RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
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

    /* ADD Start 2006.10.10 E.Takeda */
    /**
     * このクラスで保持しているコネクションをクローズします。
     * 2006.10.03
     * @author E.Takeda
     *
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
            // do nothing
        }

        _conn = null;

//        //<jp> 6020015=搬送指示送信処理を停止します。</jp>
//        //<en> 6020015=Terminating transfer instruction sending process.</en>
//        RmiMsgLogClient.write(6020015, OBJECT_NAME);
    }
    /* ADD End 2006.10.10 */
    // Private methods -----------------------------------------------
}
//end of class
