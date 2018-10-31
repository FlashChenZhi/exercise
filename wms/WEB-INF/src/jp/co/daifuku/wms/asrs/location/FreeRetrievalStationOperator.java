// $Id: FreeRetrievalStationOperator.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.asrs.control.CarryCompleteOperator;
import jp.co.daifuku.wms.asrs.control.Id33Process;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.handler.db.SysDate;

/**<jp>
 * 出庫フリーステーション（コの字出庫側など）の動作を定義したステーションです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/11/01</TD><TD>Y.Okamura</TD><TD>eWareNavi対応</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </jp>*/
/**<en>
 * Defined in this class of station is the behaviour of retrieval free station (retrieval side of 
 * U-shaped conveyor, etc.).
 * Defined in this class is the behaviour of 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/11/01</TD><TD>Y.Okamura</TD><TD>For eWareNavi</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </en>*/
public class FreeRetrievalStationOperator
        extends StationOperator
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * 対となる入庫ステーションのステーション番号を保持します。
     </jp>*/
    /**<en>
     * Preserves the station no. of pairing storage station.
     </en>*/
    private String _freeStorageStationNo = null;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Return the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 7996 $,$Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 新しい<code>Station</code>のインスタンスを作成します。既に定義されているステーションを
     * 持つインスタンスが必要な場合は、<code>StationFactory</code>クラスを利用してください。
     * @param conn データベースコネクション
     * @param snum 保持する自ステーション番号
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @see StationFactory
     </jp>*/
    /**<en>
     * Create a new instance of <code>Station</code>. If the instance which already has
     * defined stations is necessary, please use <code>StationFactory</code> class.
     * @param  conn     :Connection with database
     * @param  snum     :own station no. preserved
     * @throws ReadWriteException     : Notifies if any trouble occured in data access. 
     * @see StationFactory
     </en>*/
    public FreeRetrievalStationOperator(Connection conn, String snum) throws ReadWriteException
    {
        super(conn, snum);
        //<jp> 自ステーションNoを元に、対となるフリー入庫ステーションを取得する。</jp>
        //<en> Retrieves the free storage station pairing to the own sation no.</en>
        _freeStorageStationNo = Route.getConnectorStationTo(snum);
    }

    /**
     * 新しい<code>FreeRetrievalStationOperator</code>のインスタンスを作成します。
     * 引数で渡された、Stationインスタンスを保持します。
     * @param conn データベースコネクション
     * @param st   ステーションインスタンス
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     */
    public FreeRetrievalStationOperator(Connection conn, Station st) throws ReadWriteException
    {
        super(conn, st);
        //<jp> 自ステーションNoを元に、対となるフリー入庫ステーションを取得する。</jp>
        //<en> Retrieves the free storage station pairing to the own sation no.</en>
        _freeStorageStationNo = Route.getConnectorStationTo(st.getStationNo());
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 対となる入庫ステーションを取得します。
     * @return 対となる入庫ステーション
     </jp>*/
    /**<en>
     * Retrieves the storage station, the other of the pair.
     * @return :the storage station of the pair
     </en>*/
    public String getFreeStorageStationNo()
    {
        return _freeStorageStationNo;
    }

    /**<jp>
     * 出庫フリーステーションにおける到着処理です。
     * 到着した搬送データの更新処理を行います。
     * 受取った搬送データの搬送区分に基づいてデ－タ更新を行います。
     * @param  ci 更新対象CarryInformation
     * @param  plt Palletインスタンス
     * @throws InvalidDefineException インスタンス内の情報に不整合がある場合に通知されます。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * This is the arrival process at the retrieval free station.
     * Update process of the carry data arrived.
     * Data will be updated according to the transport section of the received carry data.
     * @param ci :CarryInformation to update
     * @param plt :Pallet instance
     * @param caller :called Class
     * @throws InvalidDefineException :Notifies if there are any data inconsistency in instance.
     * @throws ReadWriteException     :Notifies if trouble occured in data access.
     * @throws NotFoundException      :Notifies if there is no such data.
     * @throws ScheduleException
     </en>*/
    public void arrival(CarryInfo ci, Pallet plt, Class caller)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException,
                ScheduleException
    {
        setCaller(caller);

        if (ci.getCarryKey().equals(WmsParam.DUMMY_MCKEY))
        {
            //<jp> 出庫ステーションでダミーパレットの到着は無効なので例外を返す。</jp>
            //<en> Arrival of dummy pallets at retrieval station is invalid; it should return exception.</en>
            //<jp> 6024022=出庫専用ステーションです。出庫以外の到着報告は無効です。StationNo={0} mckey={1}</jp>
            //<en> 6024022=Picking only station. Storage arrival report is invalid. ST No={0} mckey={1}</en>
            Object[] tObj = new Object[2];
            tObj[0] = getStationNo();
            tObj[1] = ci.getCarryKey();
            RmiMsgLogClient.write(6024022, LogMessage.F_NOTICE, "FreeRetrievalStationOperator", tObj);
            throw new InvalidDefineException(WmsMessageFormatter.format(6024022, tObj));
        }
        else
        {
            //<jp> CarryInformationの搬送区分を元に処理を分岐</jp>
            //<en> Branching the process according to the transport sections in CarryInformation.</en>

            //<jp> 直行の場合、搬送先に到着したと判断し、搬送データを削除する。</jp>
            //<en> Direct travel: it determines the pallet already was arrived at destination and deletes the carry data.</en>
            if (CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
            {
                //<jp> 搬送データを削除（ユニット出庫払い出しと同じ扱い）</jp>
                //<en> Deletes the carry data (handled just as unit retrieval transfer)</en>
                CarryCompleteOperator carryOperate = new CarryCompleteOperator(getConnection(), getClass());

                // 再入庫予定データは作成しない
                carryOperate.unitRetrieval(ci, false);
            }
            //<jp> 出庫の場合、出庫到着処理を呼び出す。</jp>
            //<en> Invokes the process of arrival of the retrieval.</en>
            else if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(ci.getCarryFlag()))
            {
                updateArrival(ci, caller);
            }
            // それ以外の区分の場合はログを出力する
            else
            {
                //<jp> 6024018=取得したインスタンス{0}の属性{1}の値が不正です。{1}={2}</jp>
                //<en> 6024018=Attribute {1} value of acquired instance {0} is invalid. {1}={2}</en>
                Object[] tObj = new Object[3];
                tObj[0] = "CarryInfomation";
                tObj[1] = "CarryKind";
                tObj[2] = new Integer(ci.getCarryFlag());
                RmiMsgLogClient.write(6024018, LogMessage.F_WARN, "FreeRetrievalStationOperator", tObj);
            }
        }
    }

    /**<jp>
     * 出庫データの出庫口到着処理です。
     * 出庫フリーステーションにおける搬送データの更新処理を行います。
     * ユニット出庫の場合、搬送データ、在庫データを削除します。
     * ピッキング出庫の場合、搬送データのCARRYKEYを到着データに記録します。
     * @param ci 更新対象CarryInformationインスタンス
     * @param caller 呼び出し元クラス
     * @throws InvalidDefineException 定義に不整合がある場合に通知されます。
     * @throws ReadWriteException     データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Processing the retireval data for arrival at restrieval station.
     * It updates the carry data at retrieval free station.
     * If it is the unit retrieval, deletes carry data and stock data.
     * If it is a pick retrieval, records the CARRYKEY of that carry data into arrival data.
     * @param ci :CarryInformation instance to be uodated
     * @param caller :called Class
     * @throws InvalidDefineException :Notifies if there are any inconsistency in definition.
     * @throws ReadWriteException     :Notifies if exception occured when processing for database. 
     * @throws NotFoundException      :Notifies if there is no such data.
     </en>*/
    public void updateArrival(CarryInfo ci, Class caller)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException,
                ScheduleException
    {
        try
        {
            //<jp> 作業表示無しか、作業表示のみの運用の場合、到着処理にて在庫更新処理を行なう。</jp>
            //<jp> 作業指示運用の場合は搬送データ更新処理は作業指示画面より呼び出される。</jp>
            //<en> In case of operation without on-line indication, or in case only the on-line indication is operated,</en>
            //<en> the stock will be update at arrival process.</en>
            //<en> In case the on-line indication is operated, the update processing of carry data will be called by</en>
            //<en> the screen of on-line indication.</en>
            //<en> If it is for job instruction, the updates of carry data will be called by job instruction display.</en>
            if ((Station.OPERATION_DISPLAY_NONE.equals(getOperationDisplay()))
                    || (Station.OPERATION_DISPLAY_DISP_ONLY.equals(getOperationDisplay())))
            {
                //<jp> 到着更新処理を行う。</jp>
                //<en> Processes the arrival updates.</en>
                processArrival(ci, caller);
            }
        }
        catch (InvalidStatusException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }
    }

    /**<jp>
     * 強制完了処理です。
     * 出庫フリーステーションにおける搬送データの更新処理を行います。
     * 通常は作業指示運用の場合到着報告は無視されますが、強制完了の場合、更新処理を行ないます。
     * @param ci 更新対象CarryInformationインスタンス
     * @param caller 呼び出し元クラス
     * @throws InvalidDefineException 定義に不整合がある場合に通知されます。
     * @throws ReadWriteException     データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Processing the retireval data for arrival at restrieval station.
     * It updates the carry data at retrieval free station.
     * If it is the unit retrieval, deletes carry data and stock data.
     * If it is a pick retrieval, records the CARRYKEY of that carry data into arrival data.
     * @param ci :CarryInformation instance to be uodated
     * @param caller :called Class
     * @throws InvalidDefineException :Notifies if there are any inconsistency in definition.
     * @throws ReadWriteException     :Notifies if exception occured when processing for database. 
     * @throws NotFoundException      :Notifies if there is no such data.
     * @throws ScheduleException
     </en>*/
    public void completeByForce(CarryInfo ci, Class caller)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException,
                ScheduleException
    {
        try
        {
            //<jp> 作業指示運用に関わらず、到着処理を行なう。</jp>
            //<jp> 到着更新処理を行う。</jp>
            //<en> Processes the arrival updates.</en>
            processArrival(ci, caller);
        }
        catch (InvalidStatusException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }
    }

    /**<jp>
     * コのステーションに対する作業指示更新処理を行ないます。
     * コの字フリーステーション出庫側の作業指示更新処理は以下の作業を行ないます。
     *   1.搬送データのCARRYKEYと一致する作業表示データ(<code>OperationDisplay</code>)を削除する。
     * 搬送データの搬送区分が出庫又は直行ではない場合および、このステーションの作業表示属性が作業表示なしの場合に
     * このメソッドが呼び出された場合は、InvalidDefineExceptionを通知します。
     * @param ci 対象CarryInformationインスタンス
     * @param caller 呼び出し元クラス
     * @throws InvalidDefineException インスタンス内の情報に不整合がある場合に通知されます。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     * @throws ScheduleException
     </jp>*/
    public void operationDisplayUpdate(CarryInfo ci, Class caller)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException,
                ScheduleException
    {
        try
        {
            OperationDisplayHandler ohandl = new OperationDisplayHandler(getConnection());
            OperationDisplaySearchKey skey = new OperationDisplaySearchKey();

            //<jp> 本メソッドはステーションが作業指示有りの運用の場合のみ有効</jp>
            //<en> This method is only valid for stations which handles operations with on-line indications.</en>
            if (Station.OPERATION_DISPLAY_INSTRUCTION.equals(getOperationDisplay()))
            {
                // 直行または出庫の場合のみ有効
                if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(ci.getCarryFlag())
                        || CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
                {
                    //<jp>   到着更新処理を行う。</jp>
                    //<en>   Arrival update processing.</en>
                    processArrival(ci, caller);

                    //<jp> ユニット出庫時にはprocessArrivalメソッド内で到着処理を行っているので処理を終了</jp>
                    //<en> Terminates the process as tge arraival process is done in processArrival method at</en>
                    //<en> unit retrieval.</en>
                    if (CarryInfo.RETRIEVAL_DETAIL_UNIT.equals(ci.getRetrievalDetail())
                            || CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
                    {
                        return;
                    }

                    //<jp> 作業表示データの削除</jp>
                    //<en> Deleting the on-line indicaiton data</en>
                    skey.setCarryKey(ci.getCarryKey());
                    ohandl.drop(skey);
                }
                else
                {
                    //<jp> FTTB ログメッセージ出力</jp>
                    //<jp> 出庫、直行以外の状態は処理できない作業種別なので例外をスローする。</jp>
                    //<en> Any status other than 'retrieval' and 'direct travel' are the work type unavailable for </en>
                    //<en> processing; it therefore throws exception.</en>
                    throw new InvalidDefineException("");
                }
            }
            else
            {
                //<jp> FTTB ログメッセージ出力</jp>
                //<jp> 作業表示無しのステーションに対してこのメソッドが呼ばれた場合はをスローする。</jp>
                //<en> It also throws exception when this method is called for station which has no on-line indication.</en>
                throw new InvalidDefineException("");
            }
        }
        catch (InvalidStatusException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * 到着時の搬送データ、在庫の更新処理を行います。
     * @param ci 対象CarryInformationインスタンス
     * @param caller 呼び出し元クラス
     * @throws InvalidDefineException インスタンス内の情報に不整合がある場合に通知されます。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     * @throws InvalidStatusException 範囲外の状態をセットした場合に通知します。
     </jp>*/
    /**<en>
     * Update processing of carry data at arrival and the stock data.
     * @param ci :objective CarryInformation instance
     * @param caller :called Class
     * @throws InvalidDefineException :Notifies if there are any data inconsistency in instance.
     * @throws ReadWriteException     :Notifies if any trouble occured in data access.
     * @throws NotFoundException      :Notifies if there is no such data.
     * @throws InvalidStatusException :Notifies if invalid status has been set.
     * @throws ScheduleException
     </en>*/
    private void processArrival(CarryInfo ci, Class caller)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException,
                InvalidStatusException,
                ScheduleException
    {
        setCaller(caller);
        //<jp> 状態が指示済みまたは応答待ちであれば、出庫完了処理が飛ばされたと判断して完了処理を行う。</jp>
        //<en> in case of INSTRUCTION or RESPONSE_WAIT, process retrieval completion</en>
        if ((ci.getCmdStatus().equals(CarryInfo.CMD_STATUS_INSTRUCTION))
                || (ci.getCmdStatus().equals(CarryInfo.CMD_STATUS_WAIT_RESPONSE)))
        {
            if (!CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
            {
                //<jp> 作業完了報告（出庫）テキストが飛んでいることをロギングする</jp>
                //<en> Logging catch work complition report</en>
                //<jp> 6022022=出庫完了処理が行われていません。強制的に出庫完了処理行います。mckey={0}</jp>
                //<en> 6022022=Picking completion is not processed. Forcing to complete picking. mckey={0}</en>
                Object[] tObj = new Object[1];
                tObj[0] = ci.getCarryKey();
                RmiMsgLogClient.write(6022022, LogMessage.F_WARN, this.getClass().getName(), tObj);
                Connection conn = getConnection();
                try
                {
                    //<jp> CarryInformationに対して、出庫完了処理を実行する。</jp>
                    //<en> process retrieval complition for  CarryInformation</en>
                    Id33Process.normalRetrievalCompletion(conn, ci);
                }
                // 例外発生時も処理を続行する
                catch (Exception e)
                {
                    try
                    {
                        conn.rollback();
                    }
                    catch (SQLException e1)
                    {
                        // 6026054=コントロール系処理がデータベース操作中にエラーが発生しました。StackTrace={0}
                        RmiMsgLogClient.write(new TraceHandler(6026054, e), getClass().getName());

                        throw new ReadWriteException(e);
                    }
                    
                    CarryInfoHandler cih = new CarryInfoHandler(getConnection());
                    CarryInfoSearchKey cikey = new CarryInfoSearchKey();
                    cikey.setCarryKey(ci.getCarryKey());
                    
                    // 別スレッドでID25処理が行われているか確認
                    CarryInfo[] cis = (CarryInfo[])cih.find(cikey);
                    
                    if (cis.length == 0)
                    {
                        return;
                    }
                    Object[] tObject = {
                        cis[0].getCarryKey(),
                    };
                    // 搬送状態が応答待ち、指示済みの場合、引き続き到着を処理を行えないと判断する
                    if ((cis[0].getCmdStatus().equals(CarryInfo.CMD_STATUS_INSTRUCTION))
                            || (cis[0].getCmdStatus().equals(CarryInfo.CMD_STATUS_WAIT_RESPONSE)))
                    {
                        //<jp> 6024048=受信電文飛び越し処理でエラーが発生したため、処理を中断しました。mckey={0} StackTrace={1}
                        RmiMsgLogClient.write(new TraceHandler(6024048, e), getClass().getName(), tObject);
                        return;
                    }
                    //<jp> 6024045=受信電文飛び越し処理でエラーが発生しましたが、処理を続行します。mckey={0} StackTrace={1}</jp>
                    RmiMsgLogClient.write(new TraceHandler(6024045, e), getClass().getName(), tObject);
                }
            }
        }

        CarryCompleteOperator carryOperate = new CarryCompleteOperator(getConnection(), caller);
        
        // 直行の到着時の処理の場合、ユニット出庫の処理を行う
        if (CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
        {
            // 再入庫データは作成しない
            carryOperate.unitRetrieval(ci, false);
            return;
        }

        //<jp> 出庫指示詳細を元に処理の分岐を行う。</jp>
        //<en> Branching the process according to the retrieval instruction detail.</en>

        //<jp> ユニット出庫</jp>
        //<en> Unit retrieval</en>
        if (CarryInfo.RETRIEVAL_DETAIL_UNIT.equals(ci.getRetrievalDetail()))
        {
            if (getStation().isReStoringOperation())
            {
                //<jp> ユニット出庫在庫更新（再入庫予定データ作成）</jp>
                //<en> Updates the unit retrieval stocks (with creation of scheduled re-storage data)</en>
                carryOperate.unitRetrieval(ci, true);
            }
            else
            {
                //<jp> ユニット出庫在庫更新（再入庫予定データ作成なし）</jp>
                //<en> Updates the unit retrieval stocks (no creation ofscheduled re-storage data)</en>
                carryOperate.unitRetrieval(ci, false);
            }
        }
        //<jp> ピッキング出庫、積増入庫、在庫確認</jp>
        //<en> Pick retrieval, replenishment storage and inventory checks</en>
        else if (CarryInfo.RETRIEVAL_DETAIL_PICKING.equals(ci.getRetrievalDetail())
                || CarryInfo.RETRIEVAL_DETAIL_ADD_STORING.equals(ci.getRetrievalDetail())
                || CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(ci.getRetrievalDetail()))
        {
            //<jp> 在庫数の更新と在庫確認中フラグの更新</jp>
            //<en> Update the stock quantity and switch the inventory checking flag.</en>
            carryOperate.updateStock(ci);

            // 到着データを入庫用の搬送データに更新する
            CarryInfoHandler cih = new CarryInfoHandler(getConnection());
            CarryInfoAlterKey cakey = new CarryInfoAlterKey();
            cakey.setCarryKey(ci.getCarryKey());
            cakey.updateSourceStationNo(_freeStorageStationNo);
            cakey.updateCmdStatus(CarryInfo.CMD_STATUS_START);
            if (CarryInfo.RESTORING_FLAG_SAME_LOC.equals(ci.getRestoringFlag())
                    || CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(ci.getRetrievalDetail()))
            {
                // 同一棚戻りまたは出庫指示詳細が在庫確認の場合同一棚に戻るため、
                //<jp> 搬送先ステーションNoを出庫ステーションNo（棚No）にする。</jp>
                //<en> Selecting retrieval station no.(location no.) for receiving station no.</en>
                cakey.updateDestStationNo(ci.getRetrievalStationNo());

                //<jp> 在庫確認</jp>
                //<en> inventory checking</en>
                if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(ci.getRetrievalDetail()))
                {
                    StockHandler stkdl = new StockHandler(getConnection());
                    StockAlterKey stkAltKey = new StockAlterKey();
                    AreaController getAreaCtlr = new AreaController(getConnection(), getClass());

                    stkAltKey.setPalletId(ci.getPalletId());
                    stkAltKey.updateLocationNo(getAreaCtlr.toParamLocation(ci.getRetrievalStationNo()));
                    stkdl.modify(stkAltKey);

                    PalletHandler plth = new PalletHandler(getConnection());
                    PalletAlterKey pltAKey = new PalletAlterKey();
                    pltAKey.setPalletId(ci.getPalletId());
                    pltAKey.updateCurrentStationNo(ci.getRetrievalStationNo());
                    plth.modify(pltAKey);
                }
            }
            else
            {
                //<jp> 搬送先ステーションNoを倉庫ステーションNoにする。</jp>
                //<en> Selecting retrieval station no.(location no.) for receiving station no.</en>
                cakey.updateDestStationNo(getStation().getWhStationNo());
            }
            cakey.updateCarryFlag(CarryInfo.CARRY_FLAG_STORAGE);
            cakey.updateArrivalDate(new SysDate());
            cakey.updateLastUpdatePname(getClass().getSimpleName());
            cih.modify(cakey);

            //<jp> パレットを入庫中にする。</jp>
            //<en> Switch the pallet to 'storing'.</en>
            PalletHandler pltHandler = new PalletHandler(getConnection());
            PalletAlterKey pltAKey = new PalletAlterKey();
            pltAKey.setPalletId(ci.getPalletId());
            pltAKey.updateStatusFlag(Pallet.PALLET_STATUS_STORAGE_PLAN);
            pltAKey.updateLastUpdatePname(getClass().getSimpleName());
            pltHandler.modify(pltAKey);

            // 在庫確認、空棚確認に関係するテーブルをチェック・更新します。
            carryOperate.updateInventoryCheckInfo(ci);
        }
        else
        {
            //<jp> 6024018=取得したインスタンス{0}の属性{1}の値が不正です。{1}={2}</jp>
            //<en> 6024018=Attribute {1} value of acquired instance {0} is invalid. {1}={2}</en>
            Object[] tObj = new Object[3];
            tObj[0] = "CarryInfomation";
            tObj[1] = "RetrievalDetail";
            tObj[2] = new Integer(ci.getRetrievalDetail());
            RmiMsgLogClient.write(6024018, LogMessage.F_WARN, "FreeRetrievalStationOperator", tObj);
        }

    }
}
//end of class
