// $Id: AutomaticModeChangeSender.java 8062 2013-05-27 03:53:08Z kishimoto $
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
import java.util.Date;
import java.util.Hashtable;
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
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.rmi.RmiServAbstImpl;
import jp.co.daifuku.wms.asrs.control.DoubleDeepChecker;
import jp.co.daifuku.wms.asrs.entity.DoubleDeepShelf;
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
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ArrivalAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ArrivalHandler;
import jp.co.daifuku.wms.base.dbhandler.ArrivalSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
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
import jp.co.daifuku.wms.base.entity.Aisle;
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
 * 自動モード切替機能を持つ搬送指示、出庫指示送信処理を行うクラスです。<BR>
 * <code>CarryInformation</code>クラスよりAGCに送信すべき搬送情報を取得し、AGCに搬送指示を行います。<BR>
 * 搬送指示対象ステーションのモード切替が必要な場合、モード切替指示（ID=41）の送信を行ないます。<BR>
 * このクラスが送信対象とするステーションは自動モード切替機能をもつステーションのみとなります。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/19</TD><TD>mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/17</TD><TD>M.INOUE</TD><TD>modify a way to 'wait', from 'sleep()' to 'wait()'</TD></TR>
 * <TR><TD>2004/01/21</TD><TD>M.INOUE</TD><TD>ステーションに既に応答待ちのデータが存在する場合には<br>搬送指示を送信しないように変更</TD></TR>
 * <TR><TD>2004/02/16</TD><TD>K.MORI</TD><TD>空棚検索時に元棚を開放していたが、他のパレットが入庫されている場合は開放しないように変更</TD></TR>
 * <TR><TD>2004/02/19</TD><TD>K.MORI</TD><TD>お互いに入れ違いになる直行データを設定すると、モード切替指示を投げ続ける不具合を修正</TD></TR>
 * <TR><TD>2004/12/24</TD><TD>T.Hondo</TD><TD>ピッキング出庫の戻り入庫中に入庫設定をするとモードが入庫に切り替わる不具合を修正</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8062 $, $Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $
 * @author  $Author: kishimoto $
 </jp>*/
/**<en>
 * This class handles the process of sending carrying instructions and retrieval instructions and has
 * automatic mode change functions.
 * Retrieves data to send from CarryInformation to AGC, then gives carrying instruction to AGC.
 * When the mode change is necessary, it sends 'mode change request' (ID=41.
 * The target stations of this class are limited: stations with the automatic mode change fucntion.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/19</TD><TD>mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/17</TD><TD>M.INOUE</TD><TD>modify a way to 'wait', from 'sleep()' to 'wait()'</TD></TR>
 * <TR><TD>2004/01/21</TD><TD>M.INOUE</TD><TD>Instruction is not sent if data waiting for response still exist in the station.</TD></TR>
 * <TR><TD>2004/02/16</TD><TD>K.MORI</TD><TD>Correction is made so that the original location will not be open for empty location search any longer in case other pallets have been stored.</TD></TR>
 * <TR><TD>2004/02/19</TD><TD>K.MORI</TD><TD>Corrected the problem of throwing mode switch instrution repeatedly if 2 direct transfers that passes each other.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8062 $, $Date: 2013-05-27 12:53:08 +0900 (月, 27 5 2013) $
 * @author  $Author: kishimoto $
 </en>*/
public class AutomaticModeChangeSender
        extends RmiServAbstImpl
        implements java.lang.Runnable
{
    // Class fields --------------------------------------------------
    /**<jp>
     * リモートオブジェクトにバインドするオブジェクト名
     </jp>*/
    /**<en>
     * Object which binds to the remote object.
     </en>*/
    public static final String OBJECT_NAME = "AutomaticModeChangeSender";

    // Class variables -----------------------------------------------
    /**<jp>
     * データベース接続用のコネクション
     </jp>*/
    /**<en>
     * Connection to establish with database
     </en>*/
    private Connection _conn = null;

    /**<jp>
     * 搬送データ操作用ハンドラクラス
     </jp>*/
    /**<en>
     * Handler class for the carrying data manipulation
     </en>*/
    private CarryInfoHandler _carryInfoHandler = null;

    /**<jp>
     * 搬送ルート制御クラス
     </jp>*/
    /**<en>
     * Class to control class convey route
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
     * ステーションコントローラ
     </jp>*/
    /**<en>
     * station control class
     </en>*/
    private StationController _stationCtlr;

    /**<jp>
     * ストックコントローラ
     </jp>*/
    /**<en>
     * stock control class
     </en>*/
    private AsStockController _stockCtlr;

    /**<jp>
     * 搬送データはあるが送信可能な状態ではない場合のSleep Time
     </jp>*/
    /**<en>
     * Sleep Time required when there is convey data but NOT able to send
     </en>*/
    private int _existSleepTime = 1000;

    /**<jp>
     * このフラグは、AutomaticModeChangeSenderクラスを終了するかどうか判断します。
     * ExitFlagがtrueになった場合、run()メソッド内の無限ループから抜けます。
     * このフラグを更新するためには、stop()メソッドを使用します。
     </jp>*/
    /**<en>
     * This flag determines whether/not to terminate AutomaticModeChangeSender class.
     * When ExitFlag is 'true', it pulls out of infinite loop of the method: run().
     * In order to update this flag, the method: stop() should be used.
     </en>*/
    private boolean _exitFlag = false;

    /**<jp>
     * このフラグは、AutomaticModeChangeSenderが、wait状態になるための判断に使用します。
     * wRequestフラグは、外部からの搬送指示要求に応じて状態を変更します。
     * wRequestがtrueの場合、この処理はwait状態にはなりません。
     </jp>*/
    /**<en>
     * This flag is used to determine whethter/not AutomaticModeChangeSender should be in wait state.
     * The flag wRequest changes the state according to the carrying instruction request comes from outside.
     * This process is not and will not be in wait state as long as wRequest is 'true'.
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

    // DFKLOOK:ここから修正(LOTTE対応)
    /**<jp>
     * Stationデータ操作用ハンドラクラス
     </jp>*/
    /**<en>
     * Handlerclass fot hte manipulation of station data
     </en>*/
    private StationHandler _stationHandler = null;

    // DFKLOOK:ここまで修正(LOTTE対応)

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
     * 新しい<code>AutomaticModeChangeSender</code>のインスタンスを作成しデータベースとのコネクションを取得します。
     * コネクションはAS/RSシステムパラメータから取得します。
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     </jp>*/
    /**<en>
     * Create new instance of <code>AutomaticModeChangeSender</code>.
     * The connection will be obtained  from parameter of AS/RS system out of resource.
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     * @throws RemoteException  Exception related to communication generated while executing remote method call
     </en>*/
    public AutomaticModeChangeSender()
            throws ReadWriteException,
                RemoteException
    {
        try
        {
            //<jp> DataBaseへのコネクションを獲得する。ユーザ名等はリソースファイルより獲得。</jp>
            //<en> Establishes the connection with DataBase. Users name and other information </en>
            //<en> is obtained from the resource file.</en>
            _conn = WmsParam.getConnection();
            restRequest();
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
            Object[] tObj = new Object[1];
            tObj[0] = new Integer(e.getErrorCode());
            //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
            //<en> 6007030=Database error occured. error code={0}</en>
            RmiMsgLogClient.write(new TraceHandler(6007030, e), getClass().getName(), tObj);
            throw new ReadWriteException(e);
        }
    }

    /**
     * 新しい<code>AutomaticModeChangeSender</code>のインスタンスを作成しデータベースとのコネクションを取得します。
     * コネクションはAS/RSシステムパラメータから取得します。
     * @param agcNumber agcNumber
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     */
    public AutomaticModeChangeSender(String agcNumber)
            throws ReadWriteException,
                RemoteException
    {
        try
        {
            _agcNo = agcNumber;
            //<jp> DataBaseへのコネクションを獲得する。ユーザ名等はリソースファイルより獲得。</jp>
            //<en> Establishes the connection with DataBase. Users name and other information </en>
            //<en> is obtained from the resource file.</en>
            _conn = WmsParam.getConnection();
            restRequest();
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
            Object[] tObj = new Object[1];
            tObj[0] = new Integer(e.getErrorCode());
            //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
            //<en> 6007030=Database error occured. error code={0}</en>
            RmiMsgLogClient.write(new TraceHandler(6007030, e), getClass().getName(), tObj);
            throw new ReadWriteException(e);
        }
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * このクラスで使用する各ハンドラインスタンスの生成を行います。
     </jp>*/
    /**<en>
     * Generates handler instances respectively to be used in this class.
     </en>*/
    protected void initHandler()
    {
        //<jp> ハンドラインスタンス生成</jp>
        //<en> Generation of handler instance</en>
        _carryInfoHandler = new CarryInfoHandler(_conn);
        //<jp> ルートコントローラーインスタンス生成</jp>
        //<jp> 毎回ルートチェックを実施する。</jp>
        //<en> Generation of route controller instance</en>
        //<en> Checks routes each time.</en>
        _routeController = new RouteController(_conn, true);

        // DFKLOOK:ここから修正(LOTTE対応)
        _stationHandler = new StationHandler(_conn);
        // DFKLOOK:ここまで修正(LOTTE対応)

        //<jp> エリア コントローラをクリア</jp>
        _areaCtlr = null;
        //<jp> ステーション コントローラをクリア</jp>
        _stationCtlr = null;
        // <jp> ストック コントローラをクリア</jp>
        _stockCtlr = null;
    }

    /**<jp>
     * <code>CarryInformation</code>の読込みを行い、搬送指示可能であれば設備コントローラに搬送指示を送信します。
     * <code>CarryInformation</code>の読込みは一定の間隔で行われます。空棚検索が必要な場合、入庫棚決定を行います。
     </jp>*/
    /**<en>
     * Reads <code>CarryInformation</code>, then if it is capable of giving carryring instruction,
     * it sends the carying instruction to the facility controller(AGC) if possible.
     * Reading of <code>CarryInformation</code> is carried at certain intarvals. If the empty location
     * search is needed, it designates the storing location.
     </en>*/
    public void run()
    {
        try
        {
            //<jp> リソースファイルからSleep Timeを取り込む</jp>
            //<en> Loads Sleep Time from the resource file.</en>
            _existSleepTime = WmsParam.INSTRUCT_CONTROL_SLEEP_SEC_DATA_EXIST;

            if (WmsParam.MULTI_ASRSSERVER)
            {
                //<jp> このクラスをRMI Serverへ登録</jp>
                //<en> Registers this class in RMI Server.</en>
                this.bind("//" + RmiSendClient.RMI_REG_SERVER + _agcNo + "/" + OBJECT_NAME + _agcNo);
            }
            else
            {
                //<jp> このクラスをRMI Serverへ登録</jp>
                //<en> Registers this class in RMI Server.</en>
                this.bind("//" + RmiSendClient.RMI_REG_SERVER + "/" + OBJECT_NAME);
            }

            //<jp> 6020018=自動モード切替搬送指示送信処理を起動します。</jp>
            //<en> 6020018=Starting automatic mode change sender.</en>
            RmiMsgLogClient.write(6020018, this.getClass().getName());

            //<jp> ずっと動き続けること。</jp>
            //<en> Keep operating the following.</en>
            while (_exitFlag == false)
            {
                try
                {
                    //<jp> DBに再接続</jp>
                    //<en> Reestablishes the connection with DB</en>
                    CheckConnection chk = new CheckConnection();
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
                                System.out.println("AutomaticModeChangeSender ::: WAIT!!!");
                                //<jp> 要求が行なわれていない場合、wait状態にする。</jp>
                                //<en> If no request is made, puts on wait state.</en>
                                if (isRequest() == false)
                                {
                                    this.wait();
                                }
                                //<jp> 要求フラグをリセットする。</jp>
                                //<en> Resets the request flag.</en>
                                restRequest();
                                System.out.println("AutomaticModeChangeSender ::: Wake UP UP UP!!!");
                                if (_exitFlag)
                                {
                                    break;
                                }
                            }
                            catch (Exception e)
                            {
                                //<jp> エラーをログファイルに落とす</jp>
                                //<en>Records errors in log file.</en>
                                //<jp> 6026043=搬送指示タスクで異常が発生しました。例外：{0}</jp>
                                //<en> 6026043=Error occurred in transfer instruction task. Exception:{0}</en>
                                RmiMsgLogClient.write(new TraceHandler(6026043, e), this.getClass().getName());
                            }
                        }

                        //<jp> 搬送データを取りに行く。</jp>
                        //<en> Fetch the carrying data.</en>
                        control();
                    }
                }
                catch (NotBoundException e)
                {
                    //<jp> テキスト送信タスクに搬送要求を送信できませんでした。</jp>
                    //<en> Carry request could not be sent to the send text task.</en>
                    //<jp> 6024031=AGCとの接続が確立されていません。搬送要求テキストを送信できませんでした。</jp>
                    //<en> 6024031=Cannot send the transfer request text since SRC is not connected. SRC No.={0}</en>
                    RmiMsgLogClient.write(6024031, LogMessage.F_WARN, this.getClass().getName());
                }
                catch (Exception e)
                {
                    //<jp> エラーをログファイルに落とす</jp>
                    //<en>Records errors in log file.</en>
                    //<jp> 6026043=搬送指示タスクで異常が発生しました。例外：{0}</jp>
                    //<en> 6026043=Error occurred in transfer instruction task. Exception:{0}</en>
                    RmiMsgLogClient.write(new TraceHandler(6026043, e), this.getClass().getName());
                }
                finally
                {
                    try
                    {
                        //<jp> データベースのコネクションが有効であればロールバックを行なう。</jp>
                        //<en> Rolls back if the connection with database is valid.</en>
                        if (_conn != null)
                        {
                            _conn.rollback();
                        }
                    }
                    catch (SQLException e)
                    {
                        //<jp> エラーをログファイルに落とす</jp>
                        //<en>Records errors in log file.</en>
                        //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
                        //<en> 6007030=Database error occured. error code={0}</en>
                        RmiMsgLogClient.write(new TraceHandler(6007030, e), this.getClass().getName());
                        _conn = null;
                    }
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
            System.out.println("AutomaticModeChangeSender ::: finally");
            //<jp> 6020019=自動モード切替搬送指示送信処理を停止します。</jp>
            //<en> 6020019=Terminating automatic mode change sender.</en>
            RmiMsgLogClient.write(6020019, this.getClass().getName());
            try
            {
                //<jp> RMI Serverから登録を削除</jp>
                //<en> Deletes the registration from the RMI Server.</en>
                this.unbind();
                //<jp> データベースのコネクションが有効であればロールバックを行なう。</jp>
                //<en> Rolls back if the connection with database is valid.</en>
                if (_conn != null)
                {
                    _conn.rollback();
                    _conn.close();
                }
            }
            catch (SQLException e)
            {
                Object[] tObj = new Object[1];
                tObj[0] = String.valueOf(e.getErrorCode());
                //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
                //<en> 6007030=Database error occured. error code={0}</en>
                RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, this.getClass().getName(), tObj);
            }
        }
    }

    /**<jp>
     * Wait中の本搬送指示を起します。
     </jp>*/
    /**<en>
     * Activates hte carrying instruction on Wait state.
     </en>*/
    public synchronized void wakeup()
    {
        this.notify();
    }

    /**<jp>
     * この自動モード切替搬送指示送信処理に対して、即搬送データの確認をするように要求を行ないます。
     * RMI Server経由でキックする場合は、こちらのメソッドを使ってください。
     * @param params
     </jp>*/
    /**<en>
     * Requests for the immediate confirmation of carrying data to this process of sending carrying instruction
     * with automatic mode switch function.
     * If requesting via RMI Server, please use this method.
     * @param params
     </en>*/
    public synchronized void write(Object[] params)
    {
        //<jp> 要求フラグをセットしておく</jp>
        //<en> Sets the request flag.</en>
        setRequest();
        //<jp> wait解除</jp>
        //<en> Cancels the wait state.</en>
        this.notify();
    }

    /**<jp>
     * 処理を終了します。
     * 外部よりこのメソッドを呼び出された場合、処理を終了します。
     </jp>*/
    /**<en>
     * Terminates the process.
     * It teminates the process if this method is called externally.
     </en>*/
    public synchronized void stop()
    {
        //<jp> スレッドのループが終了するように、フラグを更新する。</jp>
        //<en> Updates the flag so that the looping of this thread should discontine.</en>
        _exitFlag = true;
        //<jp> このスレッドの待ち状態を解除する。</jp>
        //<en> Cancels the wait state of this thread.</en>
        this.notify();
    }

    /**<jp>
     * 搬送指示処理を行います。
     * 搬送指示要求状態のCarryInformationを読込み、搬送可能であれば設備コントローラーに搬送指示を送信します。
     * @throws Exception 搬送指示処理の続行が不可能な場合に通知されます。
     </jp>*/
    /**<en>
     * Processes the carrying instruction.
     * Reads the CarryInformation which is requesting the carrying instruction; if it is capable of carrying,
     * is sends the carrying instruciton to the facility controller(AGC).
     * @throws Exception :Notifies if the processing of carrying instruction cannot be continued.
     </en>*/
    public void control()
            throws Exception
    {
        try
        {
            //<jp> ステーション毎の搬送指示データを探しに行ってデータが無かった回数</jp>
            //<en> Number of attempts the data was not found of carrying instruction per station</en>
            int counStorageDataExist = 0;
            //<jp> ステーション数</jp>
            //<en> Number of stations</en>
            int countStations = 1;

            //Station検索用
            StationHandler stationHandler = new StationHandler(_conn);
            StationSearchKey key = new StationSearchKey();

            //<jp> ステーション毎の搬送指示データを探しに行って</jp>
            //<jp> データが無かった回数とステーション数を比べて全てのステーションで</jp>
            //<jp> データが無くなったらcontrol()を抜けてSleepに入る。</jp>
            //<en> In attempt of searching data of carrying instruction per station,</en>
            //<en> and in comparing the number of times the data was not found and the number of stations,</en>
            //<en> if no data was found at all station, get through control() and enter Sleep state.</en>
            while (countStations > counStorageDataExist)
            {
                //<jp> 自動モード切替を行なうステーション一覧を取得する。</jp>
                //<en> Gets a list of stations with automatic mode switch.</en>
                key.clear();
                if (WmsParam.MULTI_ASRSSERVER)
                {
                    key.setControllerNo(_agcNo);
                }
                key.setSendable(Station.SENDABLE_TRUE);
                key.setModeType(Station.MODE_TYPE_AUTO_CHANGE);
                key.setKey(jp.co.daifuku.wms.base.entity.GroupController.STATUS_FLAG, SystemDefine.GC_STATUS_ONLINE);
                key.setJoin(Station.CONTROLLER_NO, jp.co.daifuku.wms.base.entity.GroupController.CONTROLLER_NO);

                Station[] sts = (Station[])stationHandler.find(key);

                countStations = sts.length;

                //<jp> 入庫可能なステーションの一覧分 ループする。</jp>
                //<jp> 通常は入庫データを優先して読み込むが、</jp>
                //<jp> 出庫モード切替または出庫指示を送信した場合は出庫データを優先して読み込む</jp>
                //<en> Loops until the end of station list that is available for storage.</en>
                //<en> Normally it gives priority of reading storage data, however,</en>
                //<en> in case the work mode has been switched to the retrieval mode or in case the retrieval instruction</en>
                //<en> has been sent, retrieval data will be read with priority.</en>
                for (int i = 0; i < countStations; i++)
                {
                    //<jp> 搬送データを取得し、搬送（出庫）指示または作業モード切替処理を行なう</jp>
                    //<en> Gets carrying data, then either gives carry (retrieval) instruction or switches the work mode.</en>
                    boolean sendflag = checkAndSend(sts[i]);

                    if (sendflag)
                    {
                        //<jp> 搬送データが存在時の処理を実施した場合は送信結果の有無にかかわらず</jp>
                        //<jp> 棚決定処理、モード切替等で取得したロック資源を解放するためトランザクションをcommit。</jp>
                        //<en> If carrying data existed and the process is carried out accordingly, regardless of the </en>
                        //<en> results of sending process, commit transaction in order to release locked resource</en>
                        //<en> which were obtained from location designation or mode switching.</en>
                        _conn.commit();
                        counStorageDataExist = 0;
                    }
                    else
                    {
                        //<jp> 入庫、出庫ともに搬送データが存在しない場合</jp>
                        //<jp> 検索済みステーションのカウントを増やす</jp>
                        //<en> If there is no carrying data of either storage not retrieval,</en>
                        //<en> increase the counts of sesarched stations.</en>
                        counStorageDataExist = counStorageDataExist + 1;
                    }
                }

                //<jp> スレッド終了</jp>
                //<en> End of thread</en>
                if (_exitFlag == true)
                {
                    break;
                }

                //<jp> 搬送データはあるが送信可能な状態ではない場合は、少しSleep</jp>
                //<en> If the carrying data does exist but NOT able to send, let Sleep for a moment.</en>
                synchronized (this)
                {
                    this.wait(_existSleepTime);
                }
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
     * @throws ReadWriteException データベースに対する処理で発生した場合に通知します。
     </jp>*/
    /**<en>
     * Set <code>Connection</code> to connect with database
     * @param connection :Connection to set
     * @throws ReadWriteException :Notifies if exception occured when processing for database.
     </en>*/
    protected void setConnection(Connection connection)
            throws ReadWriteException
    {
        try
        {
            if (connection == null || connection.isClosed())
            {
                throw new RuntimeException("Connection is null or closed!");
            }
            _conn = connection;
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
            throw new ReadWriteException(e);
        }
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
     * StationControllerを返します。
     *
     * @return StationController
     * @throws ReadWriteException データベース接続エラー
     */
    protected StationController getStationCtlr()
            throws ReadWriteException
    {
        if (null == _stationCtlr)
        {
            _stationCtlr = new StationController(getConnection(), getClass());
        }
        return _stationCtlr;
    }

    /**
     * StockControllerを返します。
     *
     * @return StockController
     * @throws ReadWriteException データベース接続エラー
     * @throws ScheduleException スケジュールエラー
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

    // DFKLOOK:ここから修正(LOTTE対応)
    /**
     * stationHandlerを返します。
     * @return stationHandlerを返します。
     */
    protected StationHandler getStationHandler()
    {
        return _stationHandler;
    }

    // DFKLOOK:ここまで修正(LOTTE対応)

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 指定された<code>Station</code>オブジェクトに対する搬送データが存在するか検索を行い、
     * 存在する場合は作業モードの切替要求または搬送（出庫）指示を行ないます。
     * 作業モード切替要求か、搬送（出庫）指示が送信された場合はtrueを返します。
     * いずれのテキストも送信されなかった場合はfalseを返します。
     * @param  st 搬送の有無を確認する搬送元ステーション
     * @return 作業モード切替要求か、搬送（出庫）指示が送信された場合はtrue、何もなければfalseを返します。
     * @throws Exception データベースの読書きで障害が発生した場合に通知されます。
     * @throws Exception モード切替テキストの送信で障害が発生した場合に通知されます。
     * @throws Exception ステーションのモードチェックで例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Conducts search for the carrying data which coresponds to specified <code>Station</code> object.
     * If the data is found, it either requests the work mode switch or gives carrying instrucion(retrieval).
     * Returns 'true' if the request of work mode switch or carrying instrucion(retrieval) is sent.
     * Returns 'false' neither text was sent.
     * @param  st  :Sending station with which to confirm the type of carrying
     * @return : 'true' if work mode change is requested or retrieval instruction is sent; 'false' if neither was sent.
     * @throws Exception : Notifies if trouble occured in reading/writing database.
     * @throws Exception : Notifies if trouble occured in sending text concerning mode switching.
     * @throws Exception : Notifies if exceptions occurred during the mode checking of the station.
     </en>*/
    protected boolean checkAndSend(Station st)
            throws Exception
    {
        // 副問い合わせ用キー（出庫用）
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

        // 主問い合わせ用キー
        CarryInfoSearchKey cskey = new CarryInfoSearchKey();
        cskey.setKey(CarryInfo.PALLET_ID, Pallet.PALLET_ID);

        // 入庫
        // ダブルディープ、又は、ステーションの搬送指示有無によりSQLが異なる
        cskey.setCarryFlag(CarryInfo.CARRY_FLAG_STORAGE, "=", "(((", "", false);
        cskey.setCarryFlag(CarryInfo.CARRY_FLAG_DIRECT_TRAVEL, "=", "", ")", true);
        if (getStationCtlr().isReStoringEmptyLocationSearch(st.getStationNo())
                || Station.RESTORING_INSTRUCTION_AWC_STORAGE_SEND.equals(st.getRestoringInstruction()))
        {
            cskey.setCmdStatus(CarryInfo.CMD_STATUS_ALLOCATION, "=", "(", "", false);
            cskey.setCmdStatus(CarryInfo.CMD_STATUS_START, "=", "", "", false);
            cskey.setCmdStatus(CarryInfo.CMD_STATUS_ARRIVAL, "=", "", ")", true);
        }
        else
        {
            cskey.setCmdStatus(CarryInfo.CMD_STATUS_ALLOCATION, "=", "(", "", false);
            cskey.setCmdStatus(CarryInfo.CMD_STATUS_START, "=", "", ")", true);
        }
        cskey.setSourceStationNo((String)st.getStationNo(), "=", "", ")", false);
        // 出庫
        cskey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL, "=", "(", "", true);
        cskey.setCmdStatus(CarryInfo.CMD_STATUS_START);
        cskey.setDestStationNo((String)st.getStationNo());
        cskey.setKey(Pallet.STATUS_FLAG, Pallet.PALLET_STATUS_RETRIEVAL_PLAN);
        cskey.setKey(CarryInfo.PALLET_ID, carrySubKey, "!=", "", "))", true);

        // ソート順
        cskey.setPriorityOrder(true);
        cskey.setRegistDateOrder(true);
        cskey.setCarryKeyOrder(true);

        // DFKLOOK:ここから修正
        // バーコード存在フラグ
        String existBcr = null;

        // バーコード情報セット
        ArrivalHandler arrHand = new ArrivalHandler(_conn);
        ArrivalSearchKey arrKey = new ArrivalSearchKey();
        arrKey.setStationNo(st.getStationNo());
        arrKey.setSendFlag(SystemDefine.ARRIVAL_NOT_SEND);
        arrKey.setArrivalDateOrder(true);

        if (arrHand.count(arrKey) > 0)
        {
            Arrival[] arrival = (Arrival[])arrHand.find(arrKey);

            if (!StringUtil.isBlank(arrival[0].getBcrData()))
            {
                PalletHandler palletHandelr = new PalletHandler(_conn);
                PalletSearchKey sKey = new PalletSearchKey();
                sKey.setKey(Pallet.BCR_DATA, arrival[0].getBcrData().trim(), "=", "(", "", false);
                sKey.setKey(Pallet.BCR_DATA, arrival[0].getBcrData(), "=", "", ")", true);
                if (palletHandelr.count(sKey) > 0)
                {
                    cskey.setKey(Pallet.BCR_DATA, arrival[0].getBcrData().trim(), "=", "(", "", false);
                    cskey.setKey(Pallet.BCR_DATA, arrival[0].getBcrData(), "=", "", ")", true);

                    // バーコードが存在した為、フラグを成立させる
                    existBcr = arrival[0].getBcrData();
                }
            }
        }

        // DFKLOOK:ここから修正

        // 検索
        CarryInfo[] carryArray = (CarryInfo[])_carryInfoHandler.find(cskey);
        if (ArrayUtil.isEmpty(carryArray))
        {
            // DFKLOOK:ここから修正
            // ダミー到着でバーコードが指定されたが
            // 搬送データが存在しなかった場合
            if (!StringUtil.isBlank(existBcr))
            {
                // 6026609=パレットのバーコード情報、または容器No.が重複している可能性があります。StationNo={0} バーコード情報={1}
                Object[] tobj = new Object[2];
                tobj[0] = st.getStationNo();
                tobj[1] = existBcr;
                RmiMsgLogClient.write(6026609, LogMessage.F_ERROR, this.getClass().getName(), tobj);
            }
            // DFKLOOK:ここまで修正

            return false;
        }

        for (CarryInfo carry : carryArray)
        {
            //<jp> 搬送区分に応じて処理を振り分ける。</jp>
            //<en> Allocates the process in accordance with transport sections.</en>
            if (CarryInfo.CARRY_FLAG_STORAGE.equals(carry.getCarryFlag())
                    || CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(carry.getCarryFlag()))
            {
                //<jp> 入庫作業モード切替または搬送指示送信</jp>
                //<en> Switching the mode to storage, or sending carrying instruction</en>
                if (storageCheckAndSend(st, carry))
                {
                    break;
                }
                else
                {
                    continue;
                }

            }
            else if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(carry.getCarryFlag()))
            {

                // 引当られた棚がアクセス不可棚になっていた場合
                ShelfSearchKey shs = new ShelfSearchKey();
                ShelfHandler shsH = new ShelfHandler(_conn);

                shs.clear();
                shs.setStationNo(carry.getRetrievalStationNo());
                shs.setAccessNgFlag(SystemDefine.ACCESS_NG_FLAG_NG);

                if (shsH.count(shs) > 0)
                {
                    // 今回の出庫指示対象としない。
                    continue;
                }

                //<jp> 出庫作業モード切替または出庫指示送信</jp>
                //<en> Switching the mode to retrieval, or sending retrieval instruction</en>
                retrievalCheckAndSend(st, carryArray);
                break;
            }
        }


        return true;
    }

    /**<jp>
     * 指定された<code>Station</code>、<code>CarryInformation</code>に対する、
     * 入庫作業モードの切替要求または搬送指示を行ないます。
     * 搬送指示の送信が出来ない状態（出庫モード、モード切替要求中など）であれば、
     * 作業モード切替指示（ID=42）を送信します。
     * 搬送指示送信可能な状態の場合、他の入庫条件チェックに通れば搬送指示の送信を行ないます。
     * @param  st 搬送の有無を確認する搬送元ステーション
     * @param  storageInfo 搬送対象となる<code>CarryInformation</code>インスタンス
     * @throws Exception データベースの読書きで障害が発生した場合に通知されます。
     * @throws Exception モード切替テキストの送信で障害が発生した場合に通知されます。
     * @throws Exception ステーションのモードチェックで例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Requests to switch the storage mode, or gives carry instruction according to specified <code>Station</code>
     * or <code>CarryInformation</code>
     * If it is not in the state of sending carrying instructtion, e.g., on retirval mode or requesting the mode switch,
     * sends instruction to change work mode(ID=42).
     * If it is capable of sending carrying instruction, it does send the instruction after passing the storing condition checks.
     * @param  st Sending station with which to confirm the type of carry.
     * @param  storageInfo :Instance of <code>CarryInformation</code>, to carry
     * @throws Exception :Notifies if trouble occured in reading/writing database.
     * @throws Exception :Notifies if trouble occured in sending text concerning mode switching.

     * @throws Exception :Notifies if exceptions occurred during the mode checking of the station.
     </en>*/
    protected boolean storageCheckAndSend(Station st, CarryInfo storageInfo)
            throws Exception
    {
        //<jp> 搬送指示の送信可・不可を示すフラグ</jp>
        //<en> Flag to indicate whether/not carrying instruction data is to be sent.</en>
        boolean carrySend = false;

        //<jp> 応答待ちのデータがステーションに存在した場合は指示を送信しない。</jp>
        //<en>Instruction is not sent if data waiting for response still exist in the station.</en>
        // CarryInfo検索用
        CarryInfoSearchKey cskey = new CarryInfoSearchKey();

        cskey.clear();
        cskey.setSourceStationNo(st.getStationNo());
        cskey.setCmdStatus(CarryInfo.CMD_STATUS_WAIT_RESPONSE, "=", "(", "", false);
        cskey.setCmdStatus(CarryInfo.CMD_STATUS_ERROR, "=", "", ")", true);
        if (_carryInfoHandler.count(cskey) > 0)
        {
            return false;
        }

        // Stationの数が多い場合、現在のステーションの状態と一致せず
        // １回のダミー到着に対し、搬送指示を２回投げることがあるため
        // Stationの状態を再取得するよう修正
        st = StationFactory.makeStation(_conn, st.getStationNo());

        //<jp> 搬送区分によって確認する項目が異なるので分岐。</jp>
        //<en> Branch: check items differs accoding to their own tansport section.</en>
        if (CarryInfo.CARRY_FLAG_STORAGE.equals(storageInfo.getCarryFlag()))
        {
            //<jp> 入庫の場合、戻り入庫データかどうか確認</jp>
            //<en> In case of sotrage, confirms if it is the data of returning storate.</en>
            if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(storageInfo.getRetrievalDetail())
                    || CarryInfo.RETRIEVAL_DETAIL_UNIT.equals(storageInfo.getRetrievalDetail())
                    || CarryInfo.RETRIEVAL_DETAIL_PICKING.equals(storageInfo.getRetrievalDetail())
                    || CarryInfo.RETRIEVAL_DETAIL_ADD_STORING.equals(storageInfo.getRetrievalDetail()))
            {
                //<jp> 搬送元ステーションの条件チェック</jp>
                //<en>Check the condition of sending station.</en>
                if (soueceRightStation(storageInfo, st))
                {
                    carrySend = true;
                }
            }
            else
            {
                //<jp> このステーションに対して直行搬送作業が存在し、かつそのデータの搬送順が今回の直行より先ならば</jp>
                //<jp> モード切替と搬送は行なわず、搬送不可と判断する。</jp>
                //<en>If direct transfer work exists with this station and if creation date/time of the data is older, </en>
                //<en>the mode is not switched,the carry is not conducted and the carry is determined impossible.</en>
                //CarryInfo検索用
                CarryInfoSearchKey carryKey = new CarryInfoSearchKey();

                carryKey.clear();
                carryKey.setCarryFlag(CarryInfo.CARRY_FLAG_DIRECT_TRAVEL);
                carryKey.setDestStationNo(st.getStationNo());
                carryKey.setCmdStatus(CarryInfo.CMD_STATUS_START);
                CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
                if (!carryControl.checkCarryPriority(storageInfo, carryKey))
                {
                    //<jp> このステーションが搬送先の直行データと今回の直行データの搬送キーを比較する。</jp>
                    //<jp> 読込んだデータの搬送キーが古ければ、今回モード切替は行なわない。</jp>
                    //<en>Compare creation date of the direct transfer data that designates this station as a destination with that of this direct transfer data. </en>
                    //<en>If the creation date of loaded data is older, the mode will not be switched this time.</en>

                    System.out.println("Skip the process as there is the direct transfer data for this station. StNo:::"
                            + st.getStationNo());
                    return false;

                }

                //<jp> 搬送元ステーションの条件チェック</jp>
                //<en>Check the condition of sending station.</en>
                if (soueceRightStation(storageInfo, st))
                {
                    //<jp> 新規入庫データの場合、入庫モードのチェックまたは切り替え</jp>
                    //<en> If it is a new storage data, check or switch mode for 'storage'.</en>
                	Date requestDate = new Date();
                    if (storageCheck(st , requestDate))
                    {
                        carrySend = true;
                    }
                    else
                    {
                        //Station検索用
                        StationHandler stationHandler = new StationHandler(_conn);
                        StationSearchKey key = new StationSearchKey();
                        key.setStationNo(st.getStationNo());
                        key.setModeRequestDate(requestDate);

                        if (stationHandler.count(key) > 0)
                        {
                        	return true;
                        }
                    }
                }
            }
        }
        else
        {
            //<jp> このステーションに対して直行搬送作業が存在し、かつそのデータの搬送順が今回の直行より先ならば</jp>
            //<jp> モード切替と搬送は行なわず、搬送不可と判断する。</jp>
            //<en>If direct transfer work exists with this station and if creation date/time of the data is older, </en>
            //<en>the mode is not switched,the carry is not conducted and the carry is determined impossible.</en>
            //CarryInfo検索用
            CarryInfoSearchKey carryKey = new CarryInfoSearchKey();

            carryKey.clear();
            carryKey.setCarryFlag(CarryInfo.CARRY_FLAG_DIRECT_TRAVEL);
            carryKey.setDestStationNo(st.getStationNo());
            carryKey.setCmdStatus(CarryInfo.CMD_STATUS_START);
            CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
            if (!carryControl.checkCarryPriority(storageInfo, carryKey))
            {
                // このステーションを搬送先とした、先に設定した搬送データが有るので、今回は搬送不可とする。
                System.out.println("Skip the process as there is the direct transfer data for this station. StNo:::"
                        + st.getStationNo());
                return false;

            }

            //<jp> 直行の場合搬送元、搬送先共にチェックする。</jp>
            //<jp> 搬送元ステーションの条件チェック</jp>
            //<en>Check both carry source and destination in case of direct transfer. </en>
            //<en>Check the condition of sending station.</en>
            if (soueceRightStation(storageInfo, st))
            {
                //<jp> 直行データの場合、搬送元、搬送先のモードを同時に切替える。</jp>
                //<en>Switch the mode of carry source adn destination concurrently if it is the direct transfer data.</en>
                Station dest = StationFactory.makeStation(_conn, storageInfo.getDestStationNo());
                if (storageCheckDirectTrancefar(st, dest, storageInfo))
                {
                    carrySend = true;
                }
            }
        }

        //<jp> 搬送指示可の状態であれば、搬送指示送信チェックおよび送信処理を行なう。</jp>
        //<en> If carrying instruction can be sent, checks the data sent for carrying instustion then sending of process.</en>
        if (carrySend
                && !(SystemDefine.CMD_STATUS_ALLOCATION.equals(storageInfo.getCmdStatus())
                        && SystemDefine.OPERATION_DISPLAY_INSTRUCTION.equals(st.getOperationDisplay())))
        {
            System.out.println("STATION = " + st.getStationNo() + " STORAGE SEND OK");
            //<jp> 搬送指示送信処理</jp>
            //<en> Proces sending of carrying instruction</en>
            storageSend(storageInfo, st);
            return true;
        }
        return false;
    }

    /**<jp>
     * 指定された<code>Station</code>オブジェクトに対する出庫データが存在するか検索を行い、
     * 存在する場合は出庫作業モードの切替要求または出庫指示を行ないます。
     * 出庫指示不可の状態（入庫モード、モード切替要求中など）であれば、作業モード切替指示（ID=42）を送信します。
     * 出庫指示送信可能な状態の場合、他の出庫条件チェックに通れば出庫指示の送信を行ないます。
     * 出庫作業モード切替要求か、出庫指示が送信された場合はtrueを返します。
     * テキストが送信されなかった場合はfalseを返します。
     * @param  st            搬送の有無を確認する搬送元ステーション
     * @param  retrievalInfo 出庫搬送情報
     * @return 出庫指示対象のデータが一件でも存在する場合はtrue、一件もなければfalseを返します。
     * @throws Exception データベースの読書きで障害が発生した場合に通知されます。
     * @throws Exception モード切替テキストの送信で障害が発生した場合に通知されます。
     * @throws Exception ステーションのモード更新に失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Conduct search of retrieval data which corresponds to the specified <code>Station</code>object.
     * If the data is found, either request for switching retrieval work mode is made or releases retrieval instruction.
     * If the retrieval instruction cannot be sent(storage mode or requesting to switch mode), it sends the request for
     * work mode change(ID=42).
     * If the retrieval instruction can be sent, it sends the instruction after passing all other condition checks for retrieval.
     * Returns 'true' if either the request of retrieval work mode switch is made or retrieval instruction is sent.
     * Returns 'false' if no text was sent.
     * @param  st : Sending station with which to confirm the type of carry
     * @param  retrievalInfo Retrieval carry information
     * @throws Exception :Notifies if trouble occured in reading/writing database.
     * @throws Exception :Notifies if trouble occured in sending text concerning mode switching.
     * @throws Exception :Notifies if updating of station mode failed.
     </en>*/
    protected void retrievalCheckAndSend(Station st, CarryInfo[] retrievalInfo)
            throws Exception
    {
        // CarryInfo検索用
        CarryInfoSearchKey carryKey = new CarryInfoSearchKey();
        CarryInfo[] carryArray = null;
        CarryInfo retrievalCarry = null;

        // Pallet検索用
        PalletSearchKey pSkey = new PalletSearchKey();
        PalletHandler pHandler = new PalletHandler(_conn);
        // WorkInfo検索用
        WorkInfoHandler workInfoHandelr = new WorkInfoHandler(_conn);
        WorkInfoSearchKey workInfoKey = new WorkInfoSearchKey();

        // パレットの現在ステーション
        Station currStation = null;

        // Pallet、WorkInfoに紐付くCarryInfoを格納するための変数
        List<CarryInfo> carryList = new ArrayList<CarryInfo>();

        for (CarryInfo carry : retrievalInfo)
        {
            if (!CarryInfo.CARRY_FLAG_RETRIEVAL.equals(carry.getCarryFlag()))
            {
                continue;
            }

            // 引当られた棚がアクセス不可棚になっていた場合
            ShelfSearchKey shs = new ShelfSearchKey();
            ShelfHandler shsH = new ShelfHandler(_conn);

            shs.clear();
            shs.setStationNo(carry.getRetrievalStationNo());
            shs.setAccessNgFlag(SystemDefine.ACCESS_NG_FLAG_NG);

            if (shsH.count(shs) > 0)
            {
                // 出庫指示対象としない。
                continue;
            }

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
            if (!CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(carry.getCarryFlag()))
            {
                if (CarryInfo.RETRIEVAL_DETAIL_UNIT.equals(carry.getRetrievalDetail()))
                {
                    CarryInfoSearchKey key = new CarryInfoSearchKey();
                    //<jp> 同一パレットIDで搬送状態が開始となっている搬送データでかつ、</jp>
                    //<jp> 出庫指示詳細が在庫確認、ピッキング、積増入庫のデータを探す</jp>
                    //<en> earch the carry data of same pallet IDs that the carry status is "Start" and its retrieval instruciton detail</en>
                    //<en> is either"Inventory check","Picking" or "Replenishment". </en>
                    key.setPalletId(carry.getPalletId());
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
                key.setPalletId(carry.getPalletId());
                key.setCarryKey(carry.getCarryKey(), "<");
                if (_carryInfoHandler.count(key) > 0)
                {
                    // 存在した場合、RetreivalSender出庫対象の搬送データで処理中の場合を考慮し、今回の出庫指示対象としない
                    continue;
                }
            }

            //<jp> 引き当てられたパレットの位置が棚ではないとき。</jp>
            //<en> If allocated pallet was not on teh shelf,</en>
            pSkey.setPalletId(carry.getPalletId());
            Pallet pallet = (Pallet)pHandler.findPrimary(pSkey);

            currStation = StationFactory.makeStation(_conn, pallet.getCurrentStationNo());
            if (!(currStation instanceof Shelf))
            {
                System.out.println("Located other place than shelves.");
                continue;
            }

            retrievalCarry = carry;
            break;
        }

        // 今回出庫できる搬送データが存在しない
        if (retrievalCarry == null)
        {
            return;
        }

        //<jp> 搬送先ステーションの条件チェック</jp>
        //<en> Check the condition of receiving station.</en>
        if (destRightStation(st, retrievalCarry))
        {
            if (StringUtil.isBlank(st.getAisleStationNo()))
            {
                // アイルごとに出庫搬送指示数を取得する

                // 検索キーセット
                carryKey.clear();
                carryKey.setPalletIdCollect();
                String[] cmd = {
                    CarryInfo.CMD_STATUS_INSTRUCTION,
                    CarryInfo.CMD_STATUS_WAIT_RESPONSE,
                    CarryInfo.CMD_STATUS_COMP_RETRIEVAL,
                    CarryInfo.CMD_STATUS_ARRIVAL
                };
                // 2010/05/11 Y.Osawa ADD ST
                carryKey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL);
                // 2010/05/11 Y.Osawa ADD ED
                carryKey.setCmdStatus(cmd, true);
                // 2010/05/11 Y.Osawa DEL ST
                //                carryKey.setDestStationNo(st.getStationNo());
                // 2010/05/11 Y.Osawa DEL ED
                carryKey.setAisleStationNo(retrievalCarry.getAisleStationNo());

                int carryCount = _carryInfoHandler.count(carryKey);

                AisleSearchKey aisleKey = new AisleSearchKey();
                AisleHandler aisleHand = new AisleHandler(_conn);
                aisleKey.setStationNo(retrievalCarry.getAisleStationNo());

                Aisle aisle = (Aisle)aisleHand.findPrimary(aisleKey);
                if (aisle.getMaxCarry() <= carryCount)
                {
                    return;
                }
            }

            //<jp> 出庫モードのチェックまたは切り替え</jp>
            //<en> In case of retrieval data, checks or switches retrieval mode</en>
            if (retrievalCheck(st))
            {
                // ダブルディープ対応
                DoubleDeepChecker ddChecker = new DoubleDeepChecker(_conn);
                Shelf currShelf = (Shelf)currStation;
                if (ddChecker.retrievalCheck(retrievalCarry, currShelf))
                {
                    System.out.println("STATION = " + st.getStationNo() + " RETRIVAL SEND OK");
                    //<jp> 出庫指示送信</jp>
                    //<en>Send retrieval instruction</en>
                    retrievalSend(retrievalCarry, st);
                }
                else
                {
                    System.out.println("Shipment directions couldn't be sent. " + st.getStationNo());
                    carryKey.clear();
                    carryKey.setCarryKeyCollect();
                    carryKey.setPalletIdCollect();
                    carryKey.setCarryFlag(CarryInfo.CARRY_FLAG_RACK_TO_RACK);
                    carryKey.setCmdStatus(CarryInfo.CMD_STATUS_START);
                    carryKey.setRegistDateOrder(true);

                    // CarryInfo検索
                    try
                    {
                        carryArray = (CarryInfo[])_carryInfoHandler.findForUpdate(carryKey);
                    }
                    catch (LockTimeOutException e)
                    {
                        return;
                    }

                    // CarrtInfo格納用
                    carryList.clear();

                    for (int j = 0; j < carryArray.length; j++)
                    {
                        // CarryInfoに紐付くPalletを検索
                        pSkey.clear();
                        pSkey.setPalletId(carryArray[j].getPalletId());
                        pSkey.setStatusFlag(Pallet.PALLET_STATUS_RETRIEVAL_PLAN);

                        //  CarryInfoに紐付くWorkInfoを検索
                        workInfoKey.clear();
                        workInfoKey.setSystemConnKey(carryArray[j].getCarryKey());

                        if (pHandler.count(pSkey) > 0 && workInfoHandelr.count(workInfoKey) > 0)
                        {
                            // Pallet、WorkInfoに紐付いたCarryInfoを格納
                            carryList.add(carryArray[j]);
                        }
                    }

                    CarryInfo[] resultCarryArray = (CarryInfo[])carryList.toArray(new CarryInfo[0]);

                    List<CarryInfo> sendCarryList = new ArrayList<CarryInfo>();
                    Hashtable<Integer, Integer> keepPalletId = new Hashtable<Integer, Integer>();

                    for (int j = 0; j < carryList.size(); j++)
                    {
                        //<jp> 同じPalletIdを持つCarryInformationインスタンスを複数生成しない</jp>
                        //<en> Not generate more than just one CarryInformation instance which has the same pallet ID.</en>
                        Integer pid = new Integer(resultCarryArray[j].getPalletId());
                        if (keepPalletId.containsKey(pid))
                        {
                            continue;
                        }
                        else
                        {
                            carryKey.clear();
                            carryKey.setCarryKey(((CarryInfo)carryList.get(j)).getCarryKey());
                            CarryInfo[] carry = (CarryInfo[])_carryInfoHandler.find(carryKey);
                            if (carry.length > 0)
                            {
                                sendCarryList.add(carry[0]);
                            }
                        }
                        keepPalletId.put(pid, pid);
                    }

                    CarryInfo[] rackToRackCarryArray = new CarryInfo[sendCarryList.size()];

                    if (rackToRackCarryArray != null && rackToRackCarryArray.length > 0)
                    {
                        for (int i = 0; i < rackToRackCarryArray.length; i++)
                        {
                            rackToRackCarryArray[i] = (CarryInfo)sendCarryList.get(i);

                            Station aisle =
                                    StationFactory.makeStation(_conn, rackToRackCarryArray[i].getAisleStationNo());
                            GroupController groupControll = GroupController.getInstance(_conn, aisle.getControllerNo());

                            // アイルステーションが使用可能でシステム状態がオンラインの棚間移動のみ送信
                            if (Station.STATION_STATUS_NORMAL.equals(aisle.getStatus()))
                            {
                                if (groupControll.getStatus() == GroupController.STATUS_ONLINE)
                                {
                                    CarryInfo[] sendArray = new CarryInfo[1];
                                    sendArray[0] = rackToRackCarryArray[i];
                                    System.out.println("Movement directions are sent !!!!!!!!!!!");
                                    sendRetrievalText(sendArray, groupControll);
                                }
                                else
                                {
                                    System.out.println("The system state can't be sent for Off-line.");
                                }
                            }
                            else
                            {
                                System.out.println("Movement directions can't be sent because the state of the aisle can't be used.");
                            }
                        }
                    }
                }
            }
        }
    }

    /**<jp>
     * 指定された<code>Station</code>オブジェクトの作業モードの状態が搬送指示可能な状態かどうか検証します。
     * 搬送指示不可の状態（出庫モード、切替要求中など）であれば、作業モード切替指示（ID=42）を送信します。
     * 搬送指示可能な状態であればtrueを返します。
     * @param  st 搬送の有無を確認する搬送元ステーション
     * @return 搬送指示可能な作業モードの状態であればtrue、そうでなければfalseを返します。
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
     * @return Returns 'true' if the work mode accepts the carrying instruction, or 'false' if not.
     *         Returns 'false' also if mode switching is requested.
     * @throws Exception :Notifies if trouble occured in reading/writing database.
     * @throws Exception :Notifies if trouble occured in sending text concerning mode switching.
     * @throws Exception :Notifies if updating of station mode failed.
     </en>*/
    protected boolean storageCheck(Station st , Date requestDate)
            throws Exception
    {
        // Station検索用
        StationHandler sh = new StationHandler(_conn);
        StationAlterKey sk = new StationAlterKey();

        if (Station.CURRENT_MODE_STORAGE.equals(st.getCurrentMode()))
        {
            //<jp> 入庫モードでかつモード切替要求が未要求であれば送信可能</jp>
            //<en> If the work is on storage mode and if mode switching has not been requested, instruction can be sent.</en>
            if (Station.MODE_REQUEST_NONE.equals(st.getModeRequest()))
            {
                return true;
            }
            else
            {
                //<jp> モード切替要求中であれば、何もしない。</jp>
                //<en> If the mode switching is being requested, nothing will be done.</en>
                return false;
            }
        }
        else if (Station.CURRENT_MODE_RETRIEVAL.equals(st.getCurrentMode()))
        {
            if (Station.MODE_REQUEST_NONE.equals(st.getModeRequest()))
            {
                //<jp> 出庫モードでかつモード切替要求が未要求であれば、</jp>
                //<jp> 出庫指示送信済みデータと入庫指示送信済みデータをカウントし、一件もなければモード切替要求を行なう。</jp>
                //<en> If the work is on retrieval mode and if mode switching has not been requested, counts the data of </en>
                //<en> retrieval instructions already sent and counts the data of storage instructions already sent.  </en>
                //<en> If there is none at all, requests for mode switching.</en>
                int cnt =
                        _carryInfoHandler.count(createRetrievCountKey(st))
                                + _carryInfoHandler.count(createStorageCountKey(st));
                if (cnt == 0)
                {
                    //<jp> 入庫モードに切替え、データベースを更新する。</jp>
                    //<en> Switch to storage mode, then updates database.</en>
                    sk.clear();
                    sk.setStationNo(st.getStationNo());
                    sk.updateModeRequest(Station.MODE_REQUEST_STORAGE);
                    sk.updateModeRequestDate(new SysDate());
                    sh.modify(sk);

                    //<jp> テキスト送信前にトランザクションをCommit</jp>
                    //<en>Commit the transaction before sending the text.</en>
                    _conn.commit();

                    //<jp> 入庫モード切替指示を送信する。</jp>
                    //<en> Send instruction to switch on storage mode.</en>
                    SystemTextTransmission.id42send(st, String.valueOf(As21Id42.CLASS_STORAGE_EMG), _conn);
                }

                //<jp> モード切替は行なうが、搬送指示可能な状態ではないのでfalseを返す。</jp>
                //<en> It switches the mode, but returns 'false' as carryind instruction cannot be accepted.</en>
                return false;
            }
            else
            {
                //<jp> モード切替要求中であれば、何もしない。</jp>
                //<en> If the request of switching mode is being made, nothing will be done.</en>
                return false;
            }
        }
        else if (Station.CURRENT_MODE_NEUTRAL.equals(st.getCurrentMode()))
        {
            if (Station.MODE_REQUEST_NONE.equals(st.getModeRequest()))
            {
                //<jp> ニュートラルモードでかつモード切替要求が未要求であれば、</jp>
                //<jp> 出庫指示送信済みデータをカウントし、一件もなければモード切替要求を行なう。</jp>
                //<en> If the work is in neutral mode and the request of switching mode has not been made, counts the </en>
                //<en> data of retrieval instruction already sent, then requests for the mode switch if there is none.</en>
                int cnt = _carryInfoHandler.count(createRetrievCountKey(st));
                if (cnt == 0)
                {
                    //<jp> 入庫モードに切替え、データベースを更新する。</jp>
                    //<en> Switches to storage mode, then updates database.</en>
                    sk.clear();
                    sk.setStationNo(st.getStationNo());
                    sk.updateModeRequest(Station.MODE_REQUEST_STORAGE);
                    sk.updateModeRequestDate(requestDate);
                    sh.modify(sk);

                    //<jp> テキスト送信前にトランザクションをCommit</jp>
                    //<en>Commit the transaction before sending the text.</en>
                    _conn.commit();

                    //<jp> 入庫モード切替指示を送信する。</jp>
                    //<en> Sends the mode switch instruction to storage,</en>
                    SystemTextTransmission.id42send(st, String.valueOf(As21Id42.CLASS_STORAGE_EMG), _conn);
                }

                //<jp> モード切替は行なうが、搬送指示可能な状態ではないのでfalseを返す。</jp>
                //<en> Mode will be switched; but it returns 'false' as carrying instruction cannot be sent.</en>
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

    /**<jp>
     * 指定された<code>Station</code>オブジェクトの作業モードの状態が搬送指示可能な状態かどうか検証します。
     * 本メソッドは直行用のモードチェック＆モード切替処理です。
     * 搬送指示不可の状態（出庫モード、切替要求中など）でなければ、作業モード切替指示（ID=42）を送信します。
     * また、搬送先ステーションが自動モード切替ステーションであれば、そちらのモード切替指示の送信も行ないます。
     * モードの状態が正常で、搬送指示可能な状態であればtrueを返します。
     * モード切替を行なったか、モード切替不可の状態であればfalseを返します。
     * @param  st   搬送の有無を確認する搬送元ステーション
     * @param  dest 搬送の有無を確認する搬送先ステーション
     * @param  storageInfo 搬送対象となる<code>CarryInformation</code>インスタンス
     * @return 搬送指示可能な作業モードの状態であればtrue、そうでなければfalseを返します。
     *         モード切替要求を行なった場合もfalseを返します。
     * @throws Exception データベースの読書きで障害が発生した場合に通知されます。
     * @throws Exception モード切替テキストの送信で障害が発生した場合に通知されます。
     * @throws Exception ステーションのモード更新に失敗した場合に通知されます。
     </jp>*/
    /**<en>
     * Examine wheather the work mode of specified <code>Station</code> object is available state for carry
     * instruction or not.This method checks the mode for direct transfer and switches mode.
     * Unless the work mode is unavailable state of carry instruction such as retrieval mode or requesting for
     * mode switch, switch mode instruction (ID=42) will be sent.
     * And if the receiving station is operated by automatic mode switching function, switch mode instruction
     * will be sent to the receiving station also.
     * If the work mode is in normal status and if available for carry instruction, it returns true.
     * If mode has been swithced or if the station is unable to switch modes, it returns false.
     * @param  st    Sending station that should be checked if the carry exists or not
     * @param  dest  Receiving station that should be checked if the carry exists or not
     * @param  storageInfo :Instance of <code>CarryInformation</code>, to carry
     * @return It returns true if the work mode is available state for carry instructioin and returns false if not.
     *          It returns false also if the mode switch has been requested.
     * @throws Exception   Notify if any trouble occurred in writing/reading of database.
     * @throws Exception   Notify if any trouble occurred in sending text for mode switch.
     * @throws Exception   Notify if it failed to update the mode of the station.
     </en>*/
    protected boolean storageCheckDirectTrancefar(Station st, Station dest, CarryInfo storageInfo)
            throws Exception
    {
        GroupController groupControll = GroupController.getInstance(_conn, dest.getControllerNo());

        // CarryInfo検索用
        CarryInfoSearchKey carryKey = new CarryInfoSearchKey();

        // Station変更用
        StationHandler sh = new StationHandler(_conn);
        StationAlterKey sk = new StationAlterKey();

        if (Station.CURRENT_MODE_STORAGE.equals(st.getCurrentMode()))
        {
            // 検索キーセット
            carryKey.clear();
            String[] cmd = {
                CarryInfo.CMD_STATUS_INSTRUCTION,
                CarryInfo.CMD_STATUS_WAIT_RESPONSE,
                CarryInfo.CMD_STATUS_COMP_RETRIEVAL,
                CarryInfo.CMD_STATUS_ARRIVAL
            };
            carryKey.setCmdStatus(cmd, true);
            carryKey.setDestStationNo(dest.getStationNo());

            //<jp> 搬送元ステーションが入庫モードでかつモード切替要求が未要求であれば送信可能</jp>
            //<en>Sendable if sending station is on storage mode and if mode switching is unrequested.</en>
            if (Station.MODE_REQUEST_NONE.equals(st.getModeRequest()))
            {
                //<jp> 搬送元が送信可能状態であれば、搬送先ステーションの状態をチェックする。</jp>
                //<jp> 搬送先も自動モード切替ステーションであれば、モード切替指示の送信を行なう。</jp>
                //<jp> 搬送先ステーションが属するグループコントローラーがオンライン以外の場合は搬送不可</jp>
                //<en> If the sending station is sendable, check the status of receiving station.</en>
                //<en> If receiving station also operates the automatic mode switching, send the mode switching instruction.</en>
                //<en> Carrying is not available if the group controller that receiving station belongs to is not on-line.</en>
                if (groupControll.getStatus() != GroupController.STATUS_ONLINE)
                {
                    return false;
                }

                //<jp> 搬送先ステーションの中断中フラグを確認。</jp>
                //<en> Checks the interrupt flag of the receiving station.</en>
                if (Station.SUSPEND_ON.equals(dest.getSuspend()))
                {
                    //<jp> 中断中ならば即出庫は行われないと判断する。</jp>
                    //<en> If it has been interrupted,ite determinates that immediate retrieval will not be carried out.</en>
                    return false;
                }

                // 2010/05/11 Y.Osawa UPD ST
                // 代表ステーションの時は紐づくステーションが使用可能かチェックする
                // 使用可能なステーションの出庫指示可能数の合計が代表ステーションの出庫指示可能数より
                // 少ない場合は使用可能なステーションの出庫指示可能数の合計まで指示を送信する
                int maxQty = 0;
                // 代表ステーション
                if (Station.WORKPLACE_TYPE_MAIN_STATION.equals(dest.getWorkplaceType()))
                {
                    maxQty = getMaxPalletQtyOfMainSt(storageInfo, st, dest);
                }
                // 通常ステーション
                else
                {
                    maxQty = dest.getMaxPalletQty();
                }

                //<jp> 出庫指示可能件数のチェック(搬送先ステーションに関連するCarryInformationが規定数を越えていないか)</jp>
                //<en> Checks the available number of retrieval instrucitons (whether/not the carry information related to </en>
                //<en> the receiving station exceeded the regulation.)</en>
                //                if (dest.getMaxPalletQty() <= _carryInfoHandler.count(carryKey))
                if (maxQty <= _carryInfoHandler.count(carryKey))
                // 2010/05/11 Y.Osawa UPD ED
                {
                    //<jp> 出庫指示可能数をオーバーしている。</jp>
                    //<en> Exceeded the available number of retrieval instruction.</en>
                    return false;
                }

                //<jp> 搬送先ステーションのモード切替種別をもとに、搬送先のモードチェック処理を分岐</jp>
                //<en> Branch the mode check processing of receiving station according to the mode switch type of receiving station. </en>
                if (Station.MODE_TYPE_AUTO_CHANGE.equals(dest.getModeType()))
                {
                    //<jp> 搬送先ステーションに搬送作業が存在し、かつそのデータの搬送順が今回の直行より先ならば</jp>
                    //<jp> モード切替と搬送は行なわず、搬送不可と判断する。</jp>
                    carryKey.clear();
                    String[] cflg = {
                        CarryInfo.CARRY_FLAG_STORAGE,
                        CarryInfo.CARRY_FLAG_DIRECT_TRAVEL
                    };
                    carryKey.setCarryFlag(cflg, true);
                    carryKey.setSourceStationNo(dest.getStationNo());
                    carryKey.setCmdStatus(CarryInfo.CMD_STATUS_START);
                    CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
                    if (!carryControl.checkCarryPriority(storageInfo, carryKey))
                    {
                        System.out.println("Skip the process as there is the direct transfer data for this station. StNo:::"
                                + st.getStationNo());
                        return false;
                    }

                    //<jp> 自動モード切替ステーションであれば、モード切替チェック＆送信</jp>
                    //<en>Check the mode switch and send if the station operates the automatic mode switching.</en>
                    if (retrievalCheck(dest))
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
                else
                {
                    //<jp> 搬送先ステーションの作業モード確認</jp>
                    //<jp> 搬送先Stationが入出庫兼用ならばモードの確認が必要。入庫専用ステーションかモード管理無しならばモードは確認しない。</jp>
                    //<en> Checks the work mode of the receiving station.</en>
                    //<en> The work mode needs to be checked if receiving station is used for both storage and retrieval.</en>
                    //<en> If it is used for storage only, or if no mode control is conducted,no checking is necessary.</en>
                    if (Station.STATION_TYPE_INOUT.equals(dest.getStationType())
                            && !Station.MODE_TYPE_NONE.equals(dest.getModeType()))
                    {
                        //<jp> 搬送先ステーションが以下のいずれかの状態であれば、即出庫は行われないと判断する。</jp>
                        //<jp> 作業モード切替要求が入庫または出庫モード切替要求中</jp>
                        //<jp> 作業モードがニュートラルまたは入庫モード</jp>
                        //<en> If the status of receiving station falls on one of the following, it determines that no immdediate</en>
                        //<en> retrieval will be carried out.</en>
                        //<en> Requesting to switch the work mode of storage/retrieval</en>
                        //<en> THe work mode is neutral or storage.</en>
                        if ((!Station.MODE_REQUEST_NONE.equals(dest.getModeRequest()))
                                || (Station.CURRENT_MODE_NEUTRAL.equals(dest.getCurrentMode()))
                                || (Station.CURRENT_MODE_STORAGE.equals(dest.getCurrentMode())))
                        {
                            return false;
                        }
                    }
                    return true;
                }
            }
            else
            {
                //<jp> モード切替要求中であれば、何もしない。</jp>
                //<en> If the mode switching is being requested, nothing will be done.</en>
                return false;
            }
        }
        else if (st.getCurrentMode().equals(Station.CURRENT_MODE_RETRIEVAL))
        {
            if (st.getModeRequest().equals(Station.MODE_REQUEST_NONE))
            {
                // 検索キーセット
                carryKey.clear();
                String[] cmd = {
                    CarryInfo.CMD_STATUS_INSTRUCTION,
                    CarryInfo.CMD_STATUS_WAIT_RESPONSE,
                    CarryInfo.CMD_STATUS_COMP_RETRIEVAL,
                    CarryInfo.CMD_STATUS_ARRIVAL
                };
                carryKey.setCmdStatus(cmd, true);
                carryKey.setDestStationNo(st.getStationNo());
                //<jp> 出庫モードでかつモード切替要求が未要求であれば、</jp>
                //<jp> 出庫指示送信済みデータをカウントし、一件もなければモード切替要求を行なう。</jp>
                //<en> If the work is on retrieval mode and if mode switching has not been requested, counts the data of </en>
                //<en> retrieval instructions already sent. If there is none at all, requests for mode switching.</en>
                int cnt = _carryInfoHandler.count(carryKey);
                if (cnt == 0)
                {
                    //<jp> 入庫モードに切替え、データベースを更新する。</jp>
                    //<en> Switches to storage mode, then updates database.</en>
                    sk.clear();
                    sk.setStationNo(st.getStationNo());
                    sk.updateModeRequest(Station.MODE_REQUEST_STORAGE);
                    sk.updateModeRequestDate(new SysDate());
                    sh.modify(sk);
                    //st.requestStorageMode();

                    //<jp> テキスト送信前にトランザクションをCommit</jp>
                    //<en>Commit the transaction before sending the text.</en>
                    _conn.commit();

                    //<jp> 入庫モード切替指示を送信する。</jp>
                    //<en> Send instruction to switch on storage mode.</en>
                    SystemTextTransmission.id42send(st, String.valueOf(As21Id42.CLASS_STORAGE_EMG), _conn);
                }

                //<jp> モード切替は行なうが、搬送指示可能な状態ではないのでfalseを返す。</jp>
                //<en> It switches the mode, but returns 'false' as carryind instruction cannot be accepted.</en>
                return false;
            }
            else
            {
                //<jp> モード切替要求中であれば、何もしない。</jp>
                //<en> If the mode switching is being requested, nothing will be done.</en>
                return false;
            }
        }
        else if (st.getCurrentMode().equals(Station.CURRENT_MODE_NEUTRAL))
        {
            if (st.getModeRequest().equals(Station.MODE_REQUEST_NONE))
            {
                // 検索キーセット
                carryKey.clear();
                String[] cmd = {
                    CarryInfo.CMD_STATUS_INSTRUCTION,
                    CarryInfo.CMD_STATUS_WAIT_RESPONSE,
                    CarryInfo.CMD_STATUS_COMP_RETRIEVAL,
                    CarryInfo.CMD_STATUS_ARRIVAL
                };
                carryKey.setCmdStatus(cmd, true);
                carryKey.setDestStationNo(st.getStationNo());

                //<jp> ニュートラルモードでかつモード切替要求が未要求であれば、</jp>
                //<jp> 出庫指示送信済みデータをカウントし、一件もなければモード切替要求を行なう。</jp>
                //<en> If the work is in neutral mode and the request of switching mode has not been made, counts the </en>
                //<en> data of retrieval instruction already sent, then requests for the mode switch if there is none.</en>
                int cnt = _carryInfoHandler.count(carryKey);
                if (cnt == 0)
                {
                    //<jp> 入庫モードに切替え、データベースを更新する。</jp>
                    //<en> Switches to storage mode, then updates database.</en>
                    sk.clear();
                    sk.setStationNo(st.getStationNo());
                    sk.updateModeRequest(Station.MODE_REQUEST_STORAGE);
                    sk.updateModeRequestDate(new SysDate());
                    sh.modify(sk);

                    //<jp> テキスト送信前にトランザクションをCommit</jp>
                    //<en>Commit the transaction before sending the text.</en>
                    _conn.commit();

                    //<jp> 入庫モード切替指示を送信する。</jp>
                    //<en> Send instruction to switch on storage mode.</en>
                    SystemTextTransmission.id42send(st, String.valueOf(As21Id42.CLASS_STORAGE_EMG), _conn);
                }

                //<jp> モード切替は行なうが、搬送指示可能な状態ではないのでfalseを返す。</jp>
                //<en> It switches the mode, but returns 'false' as carryind instruction cannot be accepted.</en>
                return false;
            }
            else
            {
                //<jp> モード切替要求中であれば、何もしない。</jp>
                //<en> If the mode switching is being requested, nothing will be done.</en>
                return false;
            }
        }
        else
        {
            throw new InvalidDefineException("Invalid Mode");
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

    /**<jp>
     * 指定された<code>CarryInformation</code>に対する搬送指示送信処理を行ないます。
     * 搬送データおよびステーションの状態をチェックし、搬送可能であればID=05を送信します。
     * @param ci       搬送対象となる搬送データを現す<code>CarryInformation</code>オブジェクト
     * @param sourceSt 搬送の有無を確認する搬送元ステーション
     * @throws Exception データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Sends the carry instruction for the specified <code>CarryInformation</code>.
     * Checks the status of carrying data and stations; if carrying can be done, ID-05 should be sent.
     * @param ci           Object<code>CarryInformation</code> which indicates the carrying data.
     * @param sourceSt     Sending station with which to confirm the type of carry
     * @throws Exception   Notifies if trouble occured in reading/writing database.
     </en>*/
    protected void storageSend(CarryInfo ci, Station sourceSt)
            throws Exception
    {
        GroupController groupControll = GroupController.getInstance(_conn, sourceSt.getControllerNo());

        CarryInfo sendcarry = null;

        //<jp> ステーションに到着している搬送データを優先的に送信する必要があるため</jp>
        //<jp> 到着データの確認を行なう。</jp>
        //<en> Checks the arrived data in need of sending the carrying data with priority.</en>
        if (sourceSt.getArrival().equals(Station.ARRIVAL_ON))
        {
            sendcarry = getTopStorageInfo(ci, sourceSt);
        }
        else
        {
            sendcarry = ci;
        }

        if (sendcarry != null)
        {
            System.out.println("SEND CarryKey = " + ci.getCarryKey());
            //<jp> 搬送先決定処理および搬送ルートチェック</jp>
            //<en> Designates the receiving station and checks the tranport route.</en>
            if (destStorageDetermin(ci, sourceSt))
            {
                System.out.println("destStorageDetermin OK key = " + ci.getCarryKey());

                // 到着報告ありのステーションの場合
                if (SystemDefine.ARRIVAL_ON.equals(sourceSt.getArrival()))
                {
                    // DFKLOOK ADD start 3.4
                    ArrivalHandler arrivalHandler = new ArrivalHandler(getConnection());
                    ArrivalAlterKey arrivalAlterKey = new ArrivalAlterKey();
                    ArrivalSearchKey skey = new ArrivalSearchKey();
                    skey.setStationNo(sourceSt.getStationNo());
                    skey.setSendFlag(Arrival.ARRIVAL_NOT_SEND);
                    skey.setRegistDateOrder(true);

                    Arrival[] arrival = (Arrival[])arrivalHandler.find(skey);

                    arrivalAlterKey.setRegistDate(arrival[0].getRegistDate());
                    arrivalAlterKey.setStationNo(sourceSt.getStationNo());
                    arrivalAlterKey.updateCarryKey(ci.getCarryKey());
                    arrivalAlterKey.updateSendFlag(Arrival.ARRIVAL_SENDED);
                    arrivalHandler.modify(arrivalAlterKey);
                    // DFKLOOK ADD end   3.4
                }

                //<jp> 搬送指示テキストの送信を行う。</jp>
                //<en> Sends the carrying instruciton text.</en>
                sendStorageText(ci, groupControll ,sourceSt);


            }
        }
    }

    /**<jp>
     * 指定されたステーションに存在する搬送データの読み込みを行います。
     * １．ステーションに到着情報が記録されていない場合、nullを返します。
     * ２．ステーションにダミー到着が記録されている場合、
     *     受け取った搬送データにステーションに記録されている荷姿、バーコード情報をセットしたものを返します。
     * ３．ステーションに通常の搬送Keyが記録されている場合、その搬送Keyで搬送データの取得を行ない、
     *     ステーションに記録されている荷姿、バーコード情報をセットしたものを返します。
     * 荷姿、バーコード情報の更新に失敗した場合、読み込んだCarryInformationは異常に変更し、nullを返します。
     * @param  ci 搬送予定の<code>CarryInformation</code>オブジェクト
     * @param  st 到着情報を確認する搬送元ステーション
     * @return 到着情報の参照の結果、優先搬送対象となった<code>CarryInformation</code>オブジェクト
     * @throws Exception データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Reads the carrying data existing at specified station.
     * 1. If no arrival data is recorded in the station, returns null.
     * 2. If dummy arrivalg data is recorded in the station,
     *    it returns received carrying data along with the load size and BC data recorded at the station.
     * 3. If the regular carry key is recorded at the station, carrying data will be obtained by that carry key,
     *    then returns it along with the load size and BC data recorded at the station.
     * If updating of load size and BC data failed, loaded data of CarryInformation changes error and returns null.
     * @param  ci : object <code>CarryInformation</code> to carry
     * @param  st : sending station to check the arrival data.
     * @return : object<code>CarryInformation</code> carried with priority as a result of refering to arrival data.
     * @throws Exception Notifies if trouble occured in reading/writing database.
     </en>*/
    protected CarryInfo getTopStorageInfo(CarryInfo ci, Station st)
            throws Exception
    {
        PalletAlterKey pAkey = new PalletAlterKey();
        PalletHandler pHandler = new PalletHandler(_conn);

        ArrivalSearchKey skey = new ArrivalSearchKey();
        ArrivalHandler aHand = new ArrivalHandler(_conn);
        skey.setStationNo(st.getStationNo());
        skey.setSendFlag(Arrival.ARRIVAL_NOT_SEND);
        skey.setRegistDateOrder(true);
        if (aHand.count(skey) == 0)
        {
            return null;
        }
        Arrival[] arrival = (Arrival[])aHand.find(skey);

        String carryKey = arrival[0].getCarryKey();

        System.out.println("Arrival check ");
        if (StringUtil.isBlank(carryKey))
        {
            System.out.println("NOT Arrival ");
            //<jp> 到着データが無い場合はnullにする。</jp>
            //<en> Null if there is no arrival data.</en>
            return null;
        }
        else if (carryKey.equals(WmsParam.DUMMY_MCKEY))
        {
            //<jp> Stationにダミー到着が来ている。</jp>
            //<en> Dammy of arrival data is sent to the station.</en>
            try
            {
                //<jp> Stationに保持しているBC Data、荷高情報を今回送信対象となるPalletにセット</jp>
                //<en> BC data and the load size that preserved by the station should be set together with this pallet sending.</en>
                if (!StringUtil.isBlank(arrival[0].getBcrData()))
                {
                    if (!(arrival[0].getBcrData().equals("")))
                    {
                        pAkey.clear();
                        pAkey.setPalletId(ci.getPalletId());
                        pAkey.updateBcrData(arrival[0].getBcrData());
                        pAkey.updateLastUpdatePname(getClass().getSimpleName());
                        pHandler.modify(pAkey);
                    }
                }

                // フリーアロケーション運用かつ制御情報を保持している場合、荷幅、荷高をセットする
                if (getStationCtlr().isFreeAllocationStation(st.getStationNo())
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
                    pAkey.clear();
                    pAkey.setPalletId(ci.getPalletId());
                    pAkey.updateHeight(loadsize[0].getLoadSize());
                    pAkey.updateWidth(conInfo.getWidth());
                    pAkey.updateControlinfo(arrival[0].getControlinfo());
                    pAkey.updateLastUpdatePname(getClass().getSimpleName());
                    // 更新
                    pHandler.modify(pAkey);
                }
                // フリーアロケーション運用以外かつ荷高が0より大きい場合、荷高をセットする
                else if (arrival[0].getHeight() > 0)
                {
                    pAkey.clear();
                    pAkey.setPalletId(ci.getPalletId());
                    pAkey.updateHeight(arrival[0].getHeight());
                    pAkey.updateLastUpdatePname(getClass().getSimpleName());
                    pHandler.modify(pAkey);
                }
            }
            catch (NotFoundException e)
            {
                carryFailure(ci);
            }
            return ci;
        }
        else
        {
            //<jp> StationにあるMcKeyを基にCarryInformationテーブルから条件に会ったものを１つ獲得する。</jp>
            //<en> Based on the Mc Key of the station, obtains 1 key out of CarryInformation table which would meet the conditions.</en>
            CarryInfoSearchKey key = new CarryInfoSearchKey();
            key.setCarryKey(arrival[0].getCarryKey());
            CarryInfo[] arrivalCarry = (CarryInfo[])_carryInfoHandler.find(key);

            //<jp> 該当データなし</jp>
            //<en> No such data is found.</en>
            if (arrivalCarry.length == 0)
            {
                //<jp> 警告メッセージを出力する。</jp>
                //<en> Outputs a warning message.</en>
                //<jp> 6026066=指定されたmckeyの搬送データ[McKey={0}]が存在しないのでStation[StationNo.={1}]から削除しました。</jp>
                //<en> 6026066=No transfer data [MCKey={0}] for the specified MCKey. Deleted from the station [ST No.={1}].</en>
                Object[] tobj = new Object[2];
                tobj[0] = arrival[0].getCarryKey();
                tobj[1] = arrival[0].getStationNo();
                RmiMsgLogClient.write(6026066, LogMessage.F_ERROR, this.getClass().getName(), tobj);

                //<jp> 指定されたmckeyの搬送データがないなら、消してしまう。</jp>
                //<en> Deletes if there is no carrying data of specified Mc Key.</en>
                try
                {
                    // Arrival削除
                    ArrivalHandler arrivalHand = new ArrivalHandler(_conn);
                    ArrivalSearchKey arrivalSkey = new ArrivalSearchKey();
                    // 検索キーセット
                    arrivalSkey.setCarryKey(arrival[0].getCarryKey());
                    arrivalSkey.setStationNo(arrival[0].getStationNo());
                    // 削除
                    arrivalHand.drop(arrivalSkey);
                }

                catch (NotFoundException e)
                {
                    //<jp> 削除すべきデータが見つからない場合</jp>
                    //<en> If no data is found to delete</en>
                    Object[] tObj = new Object[1];
                    tObj[0] = Station.STORE_NAME;
                    //<jp> 6006006=削除対象データがありません。テーブル名:{0}</jp>
                    //<en> 6006006=There is no data to delete. Table Name: {0}</en>
                    RmiMsgLogClient.write(new TraceHandler(6006006, e), getClass().getName(), tObj);
                }
                return ci;
            }
            else
            {
                //<jp> 到着情報として記録されている搬送Keyより取得したCarryInformationを返す。</jp>
                //<en> Returns CarryInformation which has been obtained from the carry key recorded as arrival data.</en>
                try
                {
                    //<jp> Stationに保持しているBC Data、荷高情報を今回送信対象となるPalletにセット</jp>
                    //<en> Set the BC data and load size preserved at the station along with this pallet sending.</en>
                    if (!StringUtil.isBlank(arrival[0].getBcrData()))
                    {
                        if (!(arrival[0].getBcrData().equals("")))
                        {
                            pAkey.clear();
                            pAkey.setPalletId(arrivalCarry[0].getPalletId());
                            pAkey.updateBcrData(arrival[0].getBcrData());
                            pAkey.updateLastUpdatePname(getClass().getSimpleName());
                            pHandler.modify(pAkey);
                        }
                    }
                    // フリーアロケーション運用かつ制御情報を保持している場合、荷幅、荷高をセットする
                    if (getStationCtlr().isFreeAllocationStation(st.getStationNo())
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
                        pAkey.clear();
                        pAkey.setPalletId(ci.getPalletId());
                        pAkey.updateHeight(loadsize[0].getLoadSize());
                        pAkey.updateWidth(conInfo.getWidth());
                        pAkey.updateControlinfo(arrival[0].getControlinfo());
                        pAkey.updateLastUpdatePname(getClass().getSimpleName());
                        // 更新
                        pHandler.modify(pAkey);
                    }
                    // フリーアロケーション運用以外かつ荷高が0より大きい場合、荷高をセットする
                    else if (arrival[0].getHeight() > 0)
                    {
                        pAkey.clear();
                        pAkey.setPalletId(arrivalCarry[0].getPalletId());
                        pAkey.updateHeight(arrival[0].getHeight());
                        pAkey.updateLastUpdatePname(getClass().getSimpleName());
                        pHandler.modify(pAkey);
                    }
                }
                catch (NotFoundException e)
                {
                    carryFailure(arrivalCarry[0]);
                }

                //<jp> CarryInformationを新たに取得したデータと置換え</jp>
                //<en> Replaces the CarryInformation with newly obtained data</en>
                return arrivalCarry[0];
            }
        }
    }

    /**<jp>
     * 搬送元ステーションの状態をチェックします。
     * 搬送元Stationが所属する設備コントローラー(AGC)が正常
     * 搬送指示可能件数については搬送元ステーションに関連するCarryInformationが規定数を越えていないかをCheckする。
     * 該当ステーションが搬送先になっているCarryInformationについては考慮しない。
     * 搬送元Stationが中断中ではない
     * @param  cInfo       搬送情報(CarryInformation)
     * @param  sourceSt    搬送元ステーション
     * @return             搬送可能と確認できた場合はtrue、ダメな場合がfalse
     * @throws Exception   データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Checks the status of sending station.
     * Facility controller (AGC) that sending station belongs to is normal.
     * As for the available number of carrying instructions, need to check whether/not the CarryInformation to this
     * sending station has exceeded the regulated volume.
     * No condsideration for CarryInformation is necessary if applicable station is the receiver.
     * Sending station is not interrupted.
     * @param  cInfo       Carry Information
     * @param  sourceSt    sending station
     * @return             'true' if it is verified that carrying can be carried out, 'false' if not.
     * @throws Exception   Notifies if trouble occured in reading/writing database.
     </en>*/
    protected boolean soueceRightStation(CarryInfo cInfo, Station sourceSt)
            throws Exception
    {
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

        GroupController groupControll = GroupController.getInstance(_conn, sourceSt.getControllerNo());

        //<jp> 搬送元Stationを確認。</jp>
        //<jp> 搬送元StationをコントロールするAGCが正常か？</jp>
        //<en> Checks the sending station.</en>
        //<en> Is AGC, which controls the sending station, is in normal status?</en>
        if (groupControll.getStatus() != GroupController.STATUS_ONLINE)
        {
            return false;
        }

        //<jp> 今回の搬送データの状態が到着の場合、搬送指示済件数に自身が含まれるので</jp>
        //<jp> 結果から-1して比較する。</jp>
        //<en> If the status of this carrying data 'arrival', it has been included in the number of carrying instructions;</en>
        //<en> subtract 1 from the results then compare:</en>
        if (cInfo.getCmdStatus().equals(CarryInfo.CMD_STATUS_ARRIVAL))
        {
            //<jp> 搬送元ステーションに関連するCarryInformationが規定数を越えていないか</jp>
            //<en> whether/not the number of CarryInformation related to this sending station exceeded the regulated volume?</en>
            if (sourceSt.getMaxInstruction() <= _carryInfoHandler.count(carryKey) - 1)
            {
                //<jp> 搬送指示可能数オーバー。</jp>
                //<en> Exceeded the available number of carying instructions</en>
                return false;
            }
        }
        else
        {
            //<jp> 搬送元ステーションに関連するCarryInformationが規定数を越えていないか</jp>
            //<en> whether/not the number of CarryInformation related to this sending station exceeded the regulated volume?</en>
            if (sourceSt.getMaxInstruction() <= _carryInfoHandler.count(carryKey))
            {
                //<jp> 搬送指示可能数オーバー。</jp>
                //<en> Exceeded the available number of carying instructions</en>
                return false;
            }
        }

        //<jp> 搬送元Stationが中断中になってはいないか？</jp>
        //<en> whether/not the operation of sending station interrupted?</en>
        if (sourceSt.getSuspend().equals(Station.SUSPEND_ON))
        {
            return false;
        }

        return true;
    }

    /**<jp>
     * 搬送ルートのチェックを行います。搬送先が倉庫、作業場など送信可能ステーションではない場合
     * 搬送先決定処理を行い、cInfoの搬送先を更新します。
     * 搬送可能なルートが正常の場合はtrue、搬送先が見つからないか搬送ルートNGの場合はfalseを返します。
     * @param  cInfo       搬送情報(CarryInformation)
     * @param  sourceSt    搬送元ステーション
     * @return             搬送可能なルートが正常の場合は搬送先Stationインスタンス。搬送先が見つからないか搬送ルートNGの場合はnull
     * @throws Exception   データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Checks the tranport route. If the destination of this carrying is warehouse or workshop and no data can be sent,
     * it designates the receiving location and updates the destination of cInfo.
     * If the route to the acvailable carrying is normal, it returns 'true'. If no receiving location is found, it returns
     * 'false'.
     * @param  cInfo       Carry Information
     * @param  sourceSt    sending stastion
     * @return             Instance of sending station if the route of available carrying is normal; null if no sending
     * @throws Exception   Notifies if trouble occured in reading/writing database.
     </en>*/
    protected boolean destStorageDetermin(CarryInfo cInfo, Station sourceSt)
            throws Exception
    {
        try
        {
            // Pallet検索用
            PalletSearchKey pSkey = new PalletSearchKey();
            PalletHandler pHandler = new PalletHandler(_conn);

            pSkey.setPalletId(cInfo.getPalletId());
            Pallet pallet = (Pallet)pHandler.findPrimary(pSkey);

            Station destSt = StationFactory.makeStation(_conn, cInfo.getDestStationNo());
            if (destSt instanceof Shelf)
            {
                //<jp> 搬送先が棚の場合でも、搬送元ステーションが荷姿チェックありでかつ、搬送データが在庫確認以外の場合</jp>
                //<jp> 再度棚決定が必要なため、搬送先を倉庫に変更する。</jp>
                //<en> If the destination is a location, if sending station conducts load size checking and if the carrying data</en>
                //<en> was anything but the inventory checking;</en>
                //<en> Reallocation of the location is necessary. Changes the destination to the warehouse.</en>
                if (getStationCtlr().isReStoringEmptyLocationSearch(sourceSt.getStationNo())
                        && (!CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(cInfo.getRetrievalDetail())))
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
                    // 2010/05/11 Y.Osawa DEL ST
                    // pkey.setKey(Pallet.PALLET_ID, cskey, "!=", "", "", true);
                    // 2010/05/11 Y.Osawa DEL ST
                    if (pHandler.count(pkey) == 0)
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
                }
            }

            // 2010/05/11 Y.Osawa ADD ST
            // 搬送先が代表ステーションの場合（直行）はルートチェックを行わない（別のロジックで紐づくステーションのチェックを行う）
            if (Station.WORKPLACE_TYPE_MAIN_STATION.equals(destSt.getWorkplaceType()))
            {
                System.out.println("Designation of destination and tranport route check in DoubleDeepStorageSender destDetermin  ST No. = "
                        + destSt.getStationNo());
                //<jp> 搬送指示可能</jp>
                //<en> Able to send carry instruction</en>
                return true;
            }
            // 2010/05/11 Y.Osawa ADD ED

            //<jp> 搬送先決定処理および搬送ルートチェック。</jp>
            //<jp> ルートチェックのため一時的に搬送元ステーションを置換え</jp>
            //<en> Designates the receiving location and checks the tranport route.</en>
            //<en> FOr the route checking, replace teh sending station temporarily.</en>
            pallet.setCurrentStationNo(sourceSt.getStationNo());
            System.out.println("cInfo.getPallet() = " + pallet.getCurrentStationNo());
            System.out.println("destSt = " + destSt.getStationNo());
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
                        System.out.println("DEST CHECK FALSE");
                        return false;
                    }
                }

                // ダブルディープ対応
                if (_routeController.getDestStation() instanceof DoubleDeepShelf)
                {
                    Shelf destShelf = (Shelf)_routeController.getDestStation();
                    DoubleDeepChecker ddChecker = new DoubleDeepChecker(_conn);
                    if (!ddChecker.storageCheck(cInfo, destShelf))
                    {
                        return false;
                    }
                }

                //<jp> 搬送先をRouteControllerクラスが決定した搬送先に置き換える。</jp>
                //<en> Replaces the destination to the one that RouteController class designated.</en>
                if (!destSt.getStationNo().equals(_routeController.getDestStation().getStationNo()))
                {
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
                System.out.println("DEST CHECK FALSE");
                return false;
            }

            // フリーアロケーション倉庫の場合、搬送情報の制御情報を更新する
            if (WareHouse.FREE_ALLOCATION_ON.equals(getAreaCtlr().getFreeAllocationOfWarehouse(
                    _routeController.getDestStation().getWhStationNo())))
            {
                // 棚情報、荷姿マスタから荷幅、荷長、荷高を取得する
                ShelfSearchKey sskey = new ShelfSearchKey();
                ShelfHandler sHandler = new ShelfHandler(getConnection());
                sskey.clear();
                sskey.setStationNo(_routeController.getDestStation().getStationNo());
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
        //<en> Occurs if there is any error with conditions of updating data.</en>
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
        //<en> Available for carrying instruction</en>
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
                updateResultLocation(cInfo.getCarryKey(), getAreaCtlr().toParamLocation(
                        routeCtlr.getDestStation().getStationNo()));
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
     * Edits the text of carring instruction and sends it.
     * Text of carrying instruction is sent via RMI. If normally done, it changes the state of carrying to
     * 'waiting for a response'.
     * @param  cInfo       Instance of CarryInformation to carry
     * @param  gc          Instance of GroupController, to where the carrying instruction text is sent
     * @throws Exception   Notifies if trouble occured in reading/writing database.
     * @throws Exception   Notifies if trouble occured in editing the messages.
     </en>*/
    protected void sendStorageText(CarryInfo cInfo, GroupController gc , Station st)
            throws Exception
    {
        Object[] param = new Object[2];
        DecimalFormat fmt = new DecimalFormat("00");

        try
        {
            // CarryInfo検索用
            CarryInfoHandler cHandler = new CarryInfoHandler(_conn);
            CarryInfoAlterKey cAkey = new CarryInfoAlterKey();

            //<jp> 搬送データが送信可能な場合はテキスト送信を行い、搬送状態を応答待ちに変更する。</jp>
            //<jp> CarryInfomationから送信用電文を作成する。</jp>
            //<en> If it is possible to send the carry data, send the text then change the carry state to 'waiting for reply'.</en>
            //<en> Creating the communciation message to send from CarryInfomation</en>
            As21Id05 id05 = new As21Id05(cInfo, _conn);
            String sendMsg = id05.getSendMessage();
            System.out.println("AutomaticModeChangeSender SEND TEXT = " + sendMsg);

            //<jp> RMIを利用してAs21Senderのwriteメソッドをコール</jp>
            //<en> Calls Write method of As21Sender using RMI.</en>
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
            //<en> Updates the CarryInformation, to which the carrying instruction sent, to 'waiting for a response'.</en>
            cAkey.clear();
            cAkey.setCarryKey(cInfo.getCarryKey());
            cAkey.updateCmdStatus(CarryInfo.CMD_STATUS_WAIT_RESPONSE);
            cAkey.updateLastUpdatePname(getClass().getSimpleName());
            cHandler.modify(cAkey);

            //<jp> テキスト送信前にトランザクションを確定させる。</jp>
            //<en>Commit the transaction before sending the text.</en>
            _conn.commit();

            //<jp> 搬送指示を送信する。</jp>
            //<en> Send the carry instruction.</en>
            rmiSndC.write("AGC" + fmt.format(Integer.valueOf(gc.getNo())), param);
            rmiSndC = null;
            param[0] = null;
        }
        //<jp> トランザクション確定時に障害があった場合に発生する。</jp>
        //<en> Occurs if there are any trouble during the transaztion fix.</en>
        catch (SQLException e)
        {
            Object[] tObj = new Object[1];
            tObj[0] = new Integer(e.getErrorCode());
            //<jp> 6007030=データベースエラーが発生しました。エラーコード={0}</jp>
            //<en> 6007030=Database error occured. error code={0}</en>
            RmiMsgLogClient.write(new TraceHandler(6007030, e), getClass().getName(), tObj);
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
        //<en> Occurs if there are any error during the text editing.</en>
        catch (InvalidProtocolException e)
        {
            //<jp> 6026042=搬送情報取得時に障害発生。例外：{0}</jp>
            //<en> 6026042=Trouble occurred in obtaining transfer data. Exception:{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6026042, e), this.getClass().getName());
            carryFailure(cInfo);
        }
        //<jp> データ更新内容に誤りがあった場合に発生する。</jp>
        //<en> Occurs if there were any contents error in data updating.</en>
        catch (InvalidStatusException e)
        {
            //<jp> 6026042=搬送情報取得時に障害発生。例外：{0}</jp>
            //<en> 6026042=Trouble occurred in obtaining transfer data. Exception:{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6026042, e), this.getClass().getName());
            carryFailure(cInfo);
        }
        //<jp> データ更新内容に誤りがあった場合に発生する。</jp>
        //<en> Occurs if there were any contents error in data updating.</en>
        catch (InvalidDefineException e)
        {
            //<jp> 6026042=搬送情報取得時に障害発生。例外：{0}</jp>
            //<en> 6026042=Trouble occurred in obtaining transfer data. Exception:{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6026042, e), this.getClass().getName());
            carryFailure(cInfo);
        }
        //<jp> データ更新内容に誤りがあった場合に発生する。</jp>
        //<en> Occurs if there are any error in data updating.</en>
        catch (IOException e)
        {
            // 2010/07/30 Y.Osawa UPD ST
            //          //<jp> 通信上にて電文送出できず</jp>
            //          //<jp> 6026058=テキストを送信できませんでした。{0}</jp>
            //          //<en> Communication message could not be sent out.</en>
            //          //<en> 6026058=Cannot send the text. {0}</en>
            //          RmiMsgLogClient.write(new TraceHandler(6026058, e), this.getClass().getName());
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
     * 指定された<code>CarryInformation</code>に対する出庫指示送信処理を行ないます。
     * 搬送データおよびステーションの状態をチェックし、搬送可能であればID=12を送信します。
     * @param ci       搬送対象となる搬送データを現す<code>CarryInformation</code>オブジェクト
     * @param destSt   搬送先ステーション
     * @throws Exception データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Prcesses sending of retrieval instruction to the specified <code>CarryInformation</code>.
     * Checks the carrying information and the status of the station; if all available for the carrying instruction,
     * ID=12 will be sent out.
     * @param ci           Object<code>CarryInformation</code> which indicates the carrying data.
     * @param destSt     Receiving station
     * @throws Exception   Notifies if trouble occured in reading/writing database.
     </en>*/
    protected void retrievalSend(CarryInfo ci, Station destSt)
            throws Exception
    {
        GroupController groupControll = GroupController.getInstance(_conn, destSt.getControllerNo());

        //<jp> 搬送先決定処理および搬送ルートチェック</jp>
        //<en> Designates the destination of this carry, then checks the tranport route.</en>
        if (destRetrievalDetermin(ci, destSt))
        {
            CarryInfo[] carray = new CarryInfo[1];
            carray[0] = ci;
            //<jp> 出庫指示テキストの送信を行なう。</jp>
            //<en> Sends the text of retrieval instruction.</en>
            sendRetrievalText(carray, groupControll);
        }
    }

    /**<jp>
     * 出庫指示の条件チェックを行います。現在指示済みの搬送データ件数と搬送可能件数との比較を行い、搬送可能件数以下ならばtrue
     * 搬送可能件数を超えていればfalseを返します。
     * @param  cInfo           搬送対象CarryInformationインスタンス
     * @param  destSt          搬送先ステーション
     * @return                 搬送可能ならばtrue、搬送できない場合はfalseを返します。
     * @throws Exception       データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Checks the conditions of retrieval instruction. Compares the number of carrying data currently sompleted with
     * the availble number of carrying. If actual number of data is below the available number of data, returns 'true'.
     * If it exceeded, returns 'false'.
     * @param  cInfo           CarryInformation
     * @param  destSt          receiving station
     * @return                 returns 'true' if carrying can be carried out, 'false' if not.
     * @throws Exception       Notifies if trouble occured in reading/writing database.
     </en>*/
    protected boolean destRetrievalDetermin(CarryInfo cInfo, Station destSt)
            throws Exception
    {
        try
        {
            // CarryInfo検索用
            CarryInfoHandler cHandler = new CarryInfoHandler(_conn);
            CarryInfoAlterKey cAkey = new CarryInfoAlterKey();

            // Pallet検索用
            PalletSearchKey pSkey = new PalletSearchKey();
            PalletHandler pHandler = new PalletHandler(_conn);

            pSkey.clear();
            pSkey.setPalletId(cInfo.getPalletId());
            Pallet pallet = (Pallet)pHandler.findPrimary(pSkey);

            //<jp> 今回出庫しようとする搬送データがユニット出庫の場合に限り</jp>
            //<jp> 同一パレットに対するCarryInformationが存在するかチェックする。</jp>
            //<jp> 搬送状態が開始でかつ、出庫指示詳細がピッキングまたは、在庫確認または、積増入庫のCarryInformationが存在する場合</jp>
            //<jp> 先にユニット出庫指示を行なうと、到着報告で払い出されてしまうため、出庫指示の対象としない。</jp>
            //<en> Only if the carry data of this retrieval is for unit retrieval, check to see if there is CarryInformation for</en>
            //<en> the same pallet. </en>
            //<en> If a unit retrieval is instructed under follwing conditions, stocks will be removed at the arrival report.</en>
            //<en> Therefore, they are not regarded as a target of retrieval instruction.</en>
            //<en> If carry status is "Start" and if retrieval instruction detail is either "Picking", "Inventory check" or "Replenishment".</en>
            if (CarryInfo.RETRIEVAL_DETAIL_UNIT.equals(cInfo.getRetrievalDetail()))
            {
                CarryInfoSearchKey key = new CarryInfoSearchKey();
                //<jp> 同一パレットIDで搬送状態が開始となっている搬送データでかつ、</jp>
                //<jp> 出庫指示詳細が在庫確認、ピッキング、積増入庫のデータを探す</jp>
                //<en> Search the carry data of same pallet IDs that the carry status is "Start" and its retrieval instruciton detail</en>
                //<en>is either"Inventory check","Picking" or "Replenishment". </en>
                key.clear();
                key.setPalletId(pallet.getPalletId());
                key.setCmdStatus(CarryInfo.CMD_STATUS_START);
                String[] details = new String[3];
                details[0] = CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK;
                details[1] = CarryInfo.RETRIEVAL_DETAIL_PICKING;
                details[2] = CarryInfo.RETRIEVAL_DETAIL_ADD_STORING;
                key.setRetrievalDetail(details, true);
                CarryInfo[] cntinfo = (CarryInfo[])_carryInfoHandler.find(key);
                if (cntinfo.length > 0)
                {
                    //<jp> CarryInformationが存在する場合、この搬送データは今回の出庫指示対象としない。</jp>
                    //<en> In case the CarryInformation exists, the carry data will not be included in target of this retrieval instruction.</en>
                    return false;
                }
            }

            //<jp> 搬送ルートチェック</jp>
            //<en> Check the carry route</en>
            if (_routeController.retrievalDetermin(pallet, destSt))
            {
                //<jp> 搬送先をRouteControllerクラスが決定した搬送先に置き換える。</jp>
                //<en> Replaces the destination to the one that RouteController class designated.</en>
                if (!destSt.getStationNo().equals(_routeController.getDestStation().getStationNo()))
                {
                    Station st = _routeController.getDestStation();
                    try
                    {
                        //<jp> 決定した搬送先StationNumberをCarryInformationにセット</jp>
                        //<en> Set the StationNumber of determined receiving station in CarryInformation.</en>
                        cAkey.clear();
                        cAkey.setCarryKey(cInfo.getCarryKey());
                        cAkey.updateDestStationNo(st.getStationNo());
                        cAkey.updateLastUpdatePname(getClass().getSimpleName());
                        cHandler.modify(cAkey);

                    }
                    catch (NotFoundException e)
                    {
                        carryFailure(cInfo);
                        return false;
                    }
                }

                // フリーアロケーション倉庫の場合、搬送情報の制御情報を更新する
                if (WareHouse.FREE_ALLOCATION_ON.equals(getAreaCtlr().getFreeAllocationOfWarehouse(
                        pallet.getWhStationNo())))
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
            else
            {
                //<jp> 搬送ルートチェックＮＧ</jp>
                //<en> If carry route was checked and found unacceptable </en>
                return false;
            }
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
     * 搬送先Stationが中断中ではない
     * 出庫指示可能件数については搬送先ステーションに関連するCarryInformationが規定数を越えていないかをCheckする。
     * 該当ステーションが搬送元になっているCarryInformationについては考慮しない。
     * @param  destSt          搬送先ステーション
     * @return                 搬送可能と確認できた場合はtrue、ダメな場合がfalse
     * @throws Exception       データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Checks the status of receiving station.
     * Facility controller(AGC) , that the receiving station belongs to, has connection on-line.
     * Operation of the receiving station has not been interrupted.
     * As for the available number of retrieval instructions, it checks whether/not the carry information related to
     * the receiving station exceeded the regulation.
     * No consideration is necessary for carry information that applicable station is the sending side.
     * @param  destSt          receiving station
     * @return                 'true' if the carrying can be carried out, or 'false' if not.
     * @throws Exception       Notifies if trouble occured in reading/writing database.
     </en>*/
    protected boolean destRightStation(Station destSt, CarryInfo cInfo)
            throws Exception
    {
        // 2010/05/11 Y.Osawa DEL ST
        // CarryInfo検索用
        //        CarryInfoSearchKey carryKey = new CarryInfoSearchKey();
        //
        //        // 検索キーセット
        //        carryKey.clear();
        //        String[] cmd = {
        //            CarryInfo.CMD_STATUS_INSTRUCTION,
        //            CarryInfo.CMD_STATUS_WAIT_RESPONSE,
        //            CarryInfo.CMD_STATUS_COMP_RETRIEVAL,
        //            CarryInfo.CMD_STATUS_ARRIVAL
        //        };
        //        carryKey.setCmdStatus(cmd, true);
        //        carryKey.setDestStationNo(destSt.getStationNo());
        // 2010/05/11 Y.Osawa DEL ED

        GroupController groupControll = GroupController.getInstance(_conn, destSt.getControllerNo());
        //<jp> 搬送先ステーションが属するグループコントローラーがオンライン以外の場合は搬送不可</jp>
        //<en> Carrying is not available if the group controller that receiving station belongs to is not on-line.</en>
        if (groupControll.getStatus() != GroupController.STATUS_ONLINE)
        {
            return false;
        }

        //<jp> 搬送先ステーションの中断中フラグを確認。</jp>
        //<en> Checks the interrupt flag of the receiving station.</en>
        if (destSt.getSuspend().equals(Station.SUSPEND_ON))
        {
            //<jp> 中断中ならば即出庫は行われないと判断する。</jp>
            //<en> If it has been interrupted,ite determinates that immediate retrieval will not be carried out.</en>
            return false;
        }

        // 2010/05/11 Y.Osawa ADD ST
        // 代表ステーションの時は紐づくステーションが使用可能かチェックする
        // 使用可能なステーションの出庫指示可能数の合計が代表ステーションの出庫指示可能数より
        // 少ない場合は使用可能なステーションの出庫指示可能数の合計まで指示を送信する
        int maxQty = 0;
        // 代表ステーション
        if (Station.WORKPLACE_TYPE_MAIN_STATION.equals(destSt.getWorkplaceType()))
        {
            maxQty = getMaxPalletQtyOfMainSt(cInfo, null, destSt);
        }
        // 通常ステーション
        else
        {
            maxQty = destSt.getMaxPalletQty();
        }
        // 2010/05/11 Y.Osawa ADD ED

        //<jp> 出庫指示可能件数のチェック(搬送先ステーションに関連するCarryInformationが規定数を越えていないか)</jp>
        //<en> Checks the available number of retrieval instrucitons (whether/not the carry information related to </en>
        //<en> the receiving station exceeded the regulation.)</en>
        // 2010/05/11 Y.Osawa UPD ST
        //int carryCount = _carryInfoHandler.count(carryKey);
        //if (destSt.getMaxPalletQty() <= carryCount)
        int carryCount = getRetrievalCount(destSt);
        if (maxQty <= carryCount)
        // 2010/05/11 Y.Osawa UPD ED
        {
            //<jp> 出庫指示可能数をオーバーしている。</jp>
            //<en> Exceeded the available number of retrieval instruction.</en>
            return false;
        }
        //
        //        carryKey.clear();
        //        carryKey.setCarryKey(cInfo.getCarryKey());
        //        carryKey.setJoin(CarryInfo.SOURCE_STATION_NO, Shelf.STATION_NO);
        //        carryKey.setJoin(Shelf.PARENT_STATION_NO, Aisle.STATION_NO);
        //        carryKey.setCollect(Aisle.MAX_CARRY);
        //        CarryInfo ci = (CarryInfo)_carryInfoHandler.findPrimary(carryKey);
        //        if (ci.getBigDecimal(Aisle.MAX_CARRY).intValue() <= carryCount)
        //        {
        //            //<jp> 出庫指示可能数をオーバーしている。</jp>
        //            //<en> The number of retrieval instruction exceeded the available number set by the regulation</en>
        //            System.out.println("RetrievalSender ::: Exceeding the MAX. number of retrieval instructions  ");
        //            return false;
        //        }

        // 2010/05/11 Y.Osawa ADD ST
        // 出庫指示可能数（ステーションの最大出庫指示可能数からそのステーションへの搬送数を引いた数）をセットします。
        setPalletMaxQty(destSt.getMaxPalletQty() - carryCount);
        // 2010/05/11 Y.Osawa ADD ED

        return true;
    }

    // 2010/05/11 Y.Osawa ADD ST
    /**
     * 代表ステーション用最大出庫指示可能数取得処理
     * @param  cInfo           搬送対象CarryInformation
     * @param  sourceSt        搬送元ステーション（直行時のみ必要）
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

        RouteController rc = new RouteController(getConnection());

        int maxQty = 0;

        // 代表ステーションに紐づくステーションのチェックを行う
        for (Station destSt : stations)
        {
            //<jp> 搬送先決定および搬送ルートチェック</jp>
            //<en> Determination of the destination, transport route checking</en>
            // 出庫
            if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(cInfo.getRetrievalDetail()))
            {
                // 搬送ルートチェック
                if (!rc.retrievalDetermin(pallet, destSt, false, false, false, false, false, false))
                {
                    //<jp> 搬送ルートが無い</jp>
                    //<en> Transport route cannot be found</en>
                    continue;
                }
            }
            // 直行
            else
            {
                // 直行時は搬送先のモードチェックを行う
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
                        continue;
                    }
                }

                // 搬送ルートチェック
                if (!rc.storageDetermin(pallet, sourceSt, destSt))
                {
                    //<jp> 搬送ルートが無い</jp>
                    //<en> Transport route cannot be found</en>
                    continue;
                }
            }

            //<jp> 搬送先ステーションの条件チェック</jp>
            //<en> Check the condition with the receiving station</en>
            if (!destRightStation(destSt, cInfo))
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

    /**<jp>
     * 出庫指示テキストの編集を行い、出庫指示を送信します。
     * 出庫指示テキストの送信はRMIを経由して行われます。正常に行われた場合はCarryInformationの搬送状態を応答待ちに変更します。
     * @param  cInfoArray      搬送対象となるCarryInformation配列
     * @param  gc              出庫指示テキスト送信対象となるGroupControllerインスタンス
     * @throws Exception       データベースの読書きで障害が発生した場合に通知されます。
     * @throws Exception       送信タスクに対するテキスト送信で障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Edits the text of retrieval instruction and sends it.
     * Text of retrieval instruction is sent via RMI. If correctly done, it changes the state of carrying to
     * 'waiting for a response'.
     * @param  cInfoArray      Array of CarryInformation to carry
     * @param  gc              Instance of GroupController, to where the retrieval instruction text is sent
     * @throws Exception       Notifies if trouble occured in reading/writing database.
     * @throws Exception       Notifies if trouble occured in editing the messages.
     </en>*/
    protected void sendRetrievalText(CarryInfo[] cInfoArray, GroupController gc)
            throws Exception
    {
        Object[] param = new Object[2];
        DecimalFormat fmt = new DecimalFormat("00");

        try
        {
            // CarryInfo検索用
            CarryInfoHandler cHandler = new CarryInfoHandler(_conn);
            CarryInfoAlterKey cAkey = new CarryInfoAlterKey();

            // Pallet検索用
            PalletSearchKey pSkey = new PalletSearchKey();
            PalletAlterKey pAkey = new PalletAlterKey();
            PalletHandler pHandler = new PalletHandler(_conn);

            //<jp> 出庫指示送信テキストの編集</jp>
            //<en> Edits the text of retrieval instruction to send </en>
            As21Id12 id12 = new As21Id12(cInfoArray, _conn);
            String sendMsg = id12.getSendMessage();
            System.out.println("AutomaticModeChangeSender SEND TEXT = " + sendMsg);

            RmiSendClient rmiSndC = null;
            if (WmsParam.MULTI_ASRSSERVER)
            {
                //<jp> RMIを利用してAs21Senderのwriteメソッドをコール</jp>
                //<en> Calls Write method of As21Sender, using RMI</en>
                rmiSndC = new RmiSendClient(RmiSendClient.RMI_REG_SERVER + Integer.parseInt(gc.getNo()));
            }
            else
            {
                rmiSndC = new RmiSendClient();
            }

            param[0] = sendMsg;

            //<jp> 出庫指示可能なCarryInformationの状態を応答待ちに更新し、</jp>
            //<jp> パレットの状態を出庫中にする。</jp>
            //<en> If the CarryInformation filled requirements for the retrieval instrucitons, updates the </en>
            //<en> state to 'waiting for response'.</en>
            //<en> Change the status of pallet to 'being retrieved'</en>
            for (int i = 0; i < cInfoArray.length; i++)
            {
                cAkey.clear();
                cAkey.setCarryKey(cInfoArray[i].getCarryKey());
                cAkey.updateCmdStatus(CarryInfo.CMD_STATUS_WAIT_RESPONSE);
                cAkey.updateLastUpdatePname(getClass().getSimpleName());
                cHandler.modify(cAkey);

                pSkey.clear();
                pSkey.setPalletId(cInfoArray[i].getPalletId());
                Pallet pallet = (Pallet)pHandler.findPrimary(pSkey);

                pAkey.clear();
                pAkey.setPalletId(pallet.getPalletId());
                pAkey.updateStatusFlag(Pallet.PALLET_STATUS_RETRIEVAL);
                pAkey.updateLastUpdatePname(getClass().getSimpleName());
                pHandler.modify(pAkey);
            }
            //<jp> テキスト送信前にトランザクションを確定させる。</jp>
            //<en>Commit the transaction before sending the text.</en>
            _conn.commit();

            //<jp> 出庫指示を送信する。</jp>
            //<en> Send the retrieval instruction.</en>
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
                carryRetry(cInfoArray[i]);
//                carryFailure(cInfoArray[i]);
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
                carryRetry(cInfoArray[i]);
//                carryFailure(cInfoArray[i]);
            }
        }
        // 2010/07/30 Y.Osawa ADD ED
        //<jp> データ更新内容に誤りがあった場合に発生する。</jp>
        //<en> Occurs if there was any error while updating data.</en>
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
        //<en> Occurs if there has been any troubles at transaction fix.</en>
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
     * 指定されたCarryInforamtionインスタンスの搬送状態を異常に変更します。
     * 異常に変更されたCarryInformationは以後搬送対象となりません。
     * また、更新内容を反映させるために、このメソッド内でトランザクションを確定します。
     * @param  failureTarget   搬送対象CarryInformationインスタンス
     * @throws Exception       データベースの読書きで障害が発生した場合に通知されます。
     * @throws Exception       インスタンスの更新内容に不正があった場合に通知されます。
     </jp>*/
    /**<en>
     * Changes the carrying state of instance CarryInforamtion specified to 'error'.
     * Once the state is changed to error, this CarryInformation will not be the object of carrying.
     * Also, in order to reflect the contents updated, the transaction is fixed in this method.
     * @param  failureTarget   Instance of CarryInformation
     * @throws Exception       Notifies if trouble occured in reading/writing database.
     * @throws Exception       Notifies if there was irregularity in updates of instances.
     </en>*/
    protected void carryFailure(CarryInfo failureTarget)
            throws Exception
    {
        try
        {
            if (failureTarget != null)
            {
                //<jp> 現在までの更新内容をrollback。</jp>
                //<en> Rolls back the contents updated until present.</en>
                _conn.rollback();

                //CarryInfo更新用
                CarryInfoHandler cHandler = new CarryInfoHandler(_conn);
                CarryInfoAlterKey cAkey = new CarryInfoAlterKey();

                //<jp> 状態を異常に更新</jp>
                //<en> Updates the status to 'error'</en>
                cAkey.clear();
                cAkey.setCarryKey(failureTarget.getCarryKey());
                cAkey.updateCmdStatus(CarryInfo.CMD_STATUS_ERROR);
                cAkey.updateLastUpdatePname(getClass().getSimpleName());
                cHandler.modify(cAkey);

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

                //CarryInfo更新用
                CarryInfoHandler cih = new CarryInfoHandler(_conn);
                CarryInfoAlterKey cakey = new CarryInfoAlterKey();

                //<jp> 状態を開始に更新</jp>
                //<en> Updates the status to 'error'</en>
                cakey.clear();
                cakey.setCarryKey(retryTarget.getCarryKey());
                cakey.updateCmdStatus(CarryInfo.CMD_STATUS_START);
                cakey.updateLastUpdatePname(getClass().getSimpleName());

                cih.modify(cakey);

                if (SystemDefine.CARRY_FLAG_RETRIEVAL.equals(retryTarget.getCarryFlag())
                        || SystemDefine.CARRY_FLAG_RACK_TO_RACK.equals(retryTarget.getCarryFlag()))
                {
                    // 出庫、棚間移動の場合、パレット状態の更新
                    PalletAlterKey pAkey = new PalletAlterKey();
                    PalletHandler pHandler = new PalletHandler(_conn);
                    pAkey.clear();
                    pAkey.setPalletId(retryTarget.getPalletId());
                    pAkey.updateStatusFlag(Pallet.PALLET_STATUS_RETRIEVAL_PLAN);
                    pAkey.updateLastUpdatePname(getClass().getSimpleName());
                    pHandler.modify(pAkey);
                }
                else if (SystemDefine.CARRY_FLAG_STORAGE.equals(retryTarget.getCarryFlag())
                        || SystemDefine.CARRY_FLAG_DIRECT_TRAVEL.equals(retryTarget.getCarryFlag()))
                {
                    // 入庫、直行の場合、到着送信状態の更新
                    StationOperator sOpe = new StationOperator(getConnection(), retryTarget.getSourceStationNo());
                    if (sOpe.getStation().isArrivalCheck())
                    {
                        // 到着情報を未指示状態に戻す
                        ArrivalHandler arrivalHandler = new ArrivalHandler(getConnection());
                        ArrivalAlterKey arrivalAlterKey = new ArrivalAlterKey();

                        arrivalAlterKey.setStationNo(retryTarget.getSourceStationNo());
                        arrivalAlterKey.setCarryKey(retryTarget.getCarryKey());
                        arrivalAlterKey.updateSendFlag(Arrival.ARRIVAL_NOT_SEND);
                        arrivalHandler.modify(arrivalAlterKey);
                    }
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
     * 要求フラグを要求中にします。
     * 要求フラグが要求中の場合、自動モード切替搬送指示処理はwait状態にはなりません。
     </jp>*/
    /**<en>
     * Turns the request flag to 'requesting'.
     * If the request flag is 'requesting', the process of carrying instruction with automatic mode switch will
     * not be in wait state.
     </en>*/
    protected void setRequest()
    {
        _request = true;
    }

    /**<jp>
     * 要求フラグを未要求にします。
     * 要求フラグが未要求の場合、自動モード切替搬送指示処理はwait状態となります。
     </jp>*/
    /**<en>
     * Change the reqest flag to 'no request'.
     * If the flag states 'no request', the process of carrying instruction with automatic mode switchn should shift to wait state.
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
     * Returns the current state of request flag.
     * If the flag is 'requesting' it returns 'true'. or 'false' if not.
     * @return If the flag is 'requesting' it returns 'true'. or 'false' if not.
     </en>*/
    protected boolean isRequest()
    {
        return _request;
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

    /**
     * データベースから、出庫指示可能数を求めるために、搬送状態が「応答待ち」or「指示済み」or
     * 「完了」or「到着」の条件で検索し、結果の件数（CarryInfoのデータ件数）を返します。
     * @param  st 搬送先ステーション
     * @return 搬送情報検索キー
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     */
    private CarryInfoSearchKey createRetrievCountKey(Station st)
            throws ReadWriteException
    {
        // CarryInfo検索用
        CarryInfoSearchKey carryKey = new CarryInfoSearchKey();

        carryKey.clear();
        String[] cmd = {
            CarryInfo.CMD_STATUS_INSTRUCTION,
            CarryInfo.CMD_STATUS_WAIT_RESPONSE,
            CarryInfo.CMD_STATUS_COMP_RETRIEVAL,
            CarryInfo.CMD_STATUS_ARRIVAL
        };
        carryKey.setCmdStatus(cmd, true);
        carryKey.setDestStationNo(st.getStationNo());

        return carryKey;
    }

    /**
     * データベースから、搬送指示可能数を求めるために、搬送状態が「応答待ち」or「指示済み」or
     * 「完了」or「到着」の条件で検索する搬送情報検索キーを返します。
     * @param  st 搬送元ステーション
     * @return 搬送情報検索キー
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     */
    private CarryInfoSearchKey createStorageCountKey(Station st)
            throws ReadWriteException
    {
        // CarryInfo検索用
        CarryInfoSearchKey carryKey = new CarryInfoSearchKey();

        carryKey.clear();
        String[] cmd = {
            CarryInfo.CMD_STATUS_INSTRUCTION,
            CarryInfo.CMD_STATUS_WAIT_RESPONSE,
            CarryInfo.CMD_STATUS_COMP_RETRIEVAL,
            CarryInfo.CMD_STATUS_ARRIVAL
        };
        carryKey.setCmdStatus(cmd, true);
        carryKey.setSourceStationNo(st.getStationNo());

        return carryKey;
    }

    /* ADD Start 2006.10.10 E.Takeda */
    /**
     * このクラスで保持しているコネクションをクローズします。 2006.10.03
     *
     * @author E.Takeda
     *
     */
    private void closeConnection()
    {
        try
        {
            /* ADD Start 2006.10.10 E.Takeda */
            _conn.rollback();
            _conn.close();

        }
        catch (SQLException e)
        {
            // 何もしない
        }
        // <jp> 6020019=自動モード切替搬送指示送信処理を停止します。</jp>
        // <en> 6020019=Terminating automatic mode change sender.</en>
        RmiMsgLogClient.write(6020019, OBJECT_NAME);

    }
    /* ADD End 2006.10.10 */
}
//end of class

