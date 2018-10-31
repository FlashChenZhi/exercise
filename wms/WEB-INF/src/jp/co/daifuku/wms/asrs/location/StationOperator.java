// $Id: StationOperator.java 7970 2010-06-04 07:40:09Z kumano $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.ArrivalAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ArrivalHandler;
import jp.co.daifuku.wms.base.dbhandler.ArrivalSearchKey;
import jp.co.daifuku.wms.base.dbhandler.MachineHandler;
import jp.co.daifuku.wms.base.dbhandler.MachineSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.Arrival;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Machine;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.Entity;

/**<jp>
 * ステーション情報に対する操作を行う処理を集めたクラスです。<BR> 
 * ステーションのモード切替え、中断・再開などを行うメソッドを提供します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/22</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7970 $, $Date: 2010-06-04 16:40:09 +0900 (金, 04 6 2010) $
 * @author  $Author: kumano $
 </jp>*/
public class StationOperator
        extends Object
{
    // Class fields --------------------------------------------------
    /**
     * データベースコネクション
     */
    private Connection _conn = null;

    /**
     * Stationインスタンス
     */
    private Station _station = null;

    /**
     * 呼び出し元クラス名
     */
    private Class caller = null;

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 7970 $,$Date: 2010-06-04 16:40:09 +0900 (金, 04 6 2010) $");
    }

    // Constructors --------------------------------------------------
    /**
     * 新しい<code>StationOperator</code>のインスタンスを作成します。
     * 引数で渡された、ステーションNo.よりStationインスタンスを生成し保持します。
     * @param conn データベースコネクション
     * @param stno ステーションNo.
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     */
    public StationOperator(Connection conn, String stno)
            throws ReadWriteException
    {
        setConnection(conn);
        _station = getStation(conn, stno);
    }

    /**
     * 新しい<code>StationOperator</code>のインスタンスを作成します。
     * 引数で渡された、Stationインスタンスを保持します。
     * @param conn データベースコネクション
     * @param st   ステーションインスタンス
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     */
    public StationOperator(Connection conn, Station st)
            throws ReadWriteException
    {
        setConnection(conn);
        _station = st;
    }

    // Public methods ------------------------------------------------
    /**
     * データベースのコネクションを設定します。
     * @param conn データベースのコネクション
     */
    protected void setConnection(Connection conn)
    {
        _conn = conn;
    }

    /**
     * データベースのコネクションを返します。
     * @return データベースのコネクション。
     */
    protected Connection getConnection()
    {
        return _conn;
    }

    /**
     * 自ステーションインスタンスを返します。
     * @return ステーションインスタンス
     */
    public Station getStation()
    {
        return _station;
    }

    /**<jp>
     * ステーションの状態を使用可能または使用不可能に変更します。ハンドラを使用してデータベースを更新します。
     * 機器状態が故障中、切離中になっている機器の数のカウントを行い
     * 一件でもあれば、ステーションを使用不可に更新します。
     * 一件もなければ、ステーションを使用可能に更新します。
     * @throws InvalidStatusException テーブル更新の設定値に不整合があった場合に通知します。
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      このステーションがデータベースに見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Modifies the station status to 'available' or to 'unavailable'. Updates the database using handler.
     * It counts the number of machines on error or off-line status. 
     * If there is one or more machine error or off-line, it updates the station status 'unavailable'.
     * If there is no error or off-line machine at all, it updates the station status 'available'.
     * @throws InvalidStatusException :Notifies if there are any inconsistency in set values for table updates.
     * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
     * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
     * @throws NotFoundException      :Notifies if this station was not found in database.
     </en>*/
    public void updateStatus()
            throws InvalidStatusException,
                InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        //<jp> MachineHandlerを使用し、このステーションに対応する機器の機器情報を取得する。</jp>
        //<en> Gets machine information supporting this station by using MachineHandler.</en>
        MachineHandler mhandl = new MachineHandler(getConnection());
        MachineSearchKey key = new MachineSearchKey();
        key.setStationNo(_station.getStationNo());
        Machine[] mstat = (Machine[])mhandl.find(key);

        //<jp> 取得した機器情報群の状態を確認する。</jp>
        //<jp> 機器状態中に異常と切離しが含まれている場合、切離しを優先して記憶</jp>
        //<en> Checks the status of machine data group obtained.</en>
        //<en> If the error status or off-line status are included in the machine status, </en>
        //<en> it gives priority to off-line status when storing in memory.</en>
        int cnt = 0;
        String stat = Machine.MACHINE_STATE_ACTIVE;
        for (int i = 0; i < mstat.length; i++)
        {
            if (Machine.MACHINE_STATE_FAIL.equals(mstat[i].getStatusFlag()))
            {
                cnt++;
                if (!Machine.MACHINE_STATE_OFFLINE.equals(stat))
                {
                    stat = Machine.MACHINE_STATE_FAIL;
                }
            }
            else if (Machine.MACHINE_STATE_OFFLINE.equals(mstat[i].getStatusFlag()))
            {
                cnt++;
                stat = Machine.MACHINE_STATE_OFFLINE;
            }
        }

        //<jp> 異常または切離しのカウントが基準を超えた場合、このステーションの状態を更新する。</jp>
        //<jp> 基準以下の場合、現在のステーションの状態が正常以外であれば正常に変更する。</jp>
        //<en> If the counts of error or off-line exceeded the standard, it renews the status of this station.</en>
        //<en> If the counts remain standard or below, and if current status of the station is anything other than</en>
        //<en> normal, it should alter the status to 'normal'.</en>
        StationAlterKey altkey = new StationAlterKey();
        StationHandler handl = new StationHandler(getConnection());

        if (cnt > Station.STATION_NG_JUDGMENT)
        {
            if (Station.AISLE_STATUS_NORMAL.equals(_station.getStatus()))
            {
                if (Machine.MACHINE_STATE_FAIL.equals(stat))
                {
                    altkey.setStationNo(_station.getStationNo());
                    altkey.updateStatus(Station.STATION_STATUS_ERROR);
                    handl.modify(altkey);
                }
                else
                {
                    altkey.setStationNo(_station.getStationNo());
                    altkey.updateStatus(Station.STATION_STATUS_DISCONNECTED);
                    handl.modify(altkey);
                }
            }
        }
        else
        {
            if (!Station.AISLE_STATUS_NORMAL.equals(_station.getStatus()))
            {
                //<jp> ステーションの状態を使用可能に更新。</jp>
                //<en> Update the status of the station to 'available'.</en>
                altkey.setStationNo(_station.getStationNo());
                altkey.updateStatus(Station.AISLE_STATUS_NORMAL);
                handl.modify(altkey);
            }
        }
    }

    /**<jp>
     * ステーションの中断中フラグを変更します。ハンドラを使用してデータベースを更新します。
     * @param sus 中断中フラグ。
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      このステーションがデータベースに見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Alters the suspention flag. It updates the database using handler.
     * @param sus :suspention flag
     * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
     * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
     * @throws NotFoundException      :Notifies if this station was not found in database.
     </en>*/
    public void alterSuspend(boolean sus)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        StationAlterKey altkey = new StationAlterKey();
        altkey.setStationNo(_station.getStationNo());
        if (sus)
        {
            altkey.updateSuspend(Station.SUSPEND_ON);
        }
        else
        {
            altkey.updateSuspend(Station.SUSPEND_OFF);
        }
        StationHandler handl = new StationHandler(getConnection());
        handl.modify(altkey);
    }

    /**<jp>
     * ステーションの最終使用ステーションを変更します。ハンドラを使用してデータベースを更新します。
     * @param  lstno セットする最終使用ステーションNo.
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      このステーションがデータベースに見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Alters the end-use station. It updates the database using handler.
     * @param  lstno :final end-use station
     * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
     * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
     * @throws NotFoundException      :Notifies if this station was not found in database.
     </en>*/
    public void alterLastUsedStation(String lstno)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        StationAlterKey altkey = new StationAlterKey();
        altkey.setStationNo(_station.getStationNo());
        altkey.updateLastUsedStationNo(lstno);
        StationHandler handl = new StationHandler(getConnection());
        handl.modify(altkey);
    }

    /**<jp>
     * 搬送データのMCKEYをステーションに記録します。
     * このメソッドでデータベースの内容を確定します。
     * ＜理由＞
     *     ・通常このメソッドの後で搬送指示送信タスクに対して、搬送指示処理の要求を行ないます。
     *       その場合にCommitのタイミングによっては搬送指示送信が遅れることがあるためここでデータベースの内容を確定します。
     * @param  carrykey  セットする搬送キー
     * @param  plt Palletインスタンス
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      このステーションがデータベースに見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Records the MCKEY and the load size information of carry data in station.
     * Commit a database transaction in this method.
     * <Reason>
     * Normally after this method, the carry instruction processing is requested to send carry isntruction task.
     * In that case the transmission of carry isntruction may be get delayed depending on the timing of Commit.
     * Therefore the contents of database needs to be fixed at this point.
     * @param carrykey :CarryInformation to update
     * @param plt :Pallet instance
     * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
     * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
     * @throws NotFoundException      :Notifies if this station was not found in database.
     </en>
     * @throws DataExistsException */
    public void registArrival(String carrykey, Pallet plt)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        
        ArrivalHandler handl = new ArrivalHandler(getConnection());
        ArrivalSearchKey sKey = new ArrivalSearchKey();
        ArrivalAlterKey aKey = new ArrivalAlterKey();
        boolean dataexist = false;
        
        // 上書きの場合
        if (WmsParam.DUMMY_ARRIVED_BUFFERING_FLAG)
        {
            // 同一ステーションにダミー到着があがっているか確認
            sKey.setCarryKey(WmsParam.DUMMY_MCKEY);
            sKey.setStationNo(getStationNo());
            if (handl.count(sKey) > 0)
            {
            	dataexist = true;
                aKey.setStationNo(getStationNo());
                aKey.setCarryKey(WmsParam.DUMMY_MCKEY);
                aKey.updateBcrData(plt.getBcrData());
                aKey.updateControlinfo(plt.getControlinfo());

                aKey.updateSendFlag(SystemDefine.ARRIVAL_NOT_SEND);
                aKey.updateRegistPname(this.getClass().getSimpleName());
                aKey.updateLastUpdatePname(this.getClass().getSimpleName());
                aKey.updateArrivalDate(new Date());
                
                //<jp> 荷姿チェックのあるステーションの場合は、Palletが保持する荷姿をセットする。</jp>
                //<en> If the Arrival operates load size checking, set the load size the Pallet preserves.</en>
                if (getStation().isLoadSizeCheck())
                {
                    aKey.updateHeight(plt.getHeight());
                }
                
                handl.modify(aKey);
            }
        }
        // バッファリングの場合、又はデータが無かった場合
        if (!WmsParam.DUMMY_ARRIVED_BUFFERING_FLAG || !dataexist)
        {
            Arrival arri = new Arrival();
            arri.setStationNo(getStationNo());
            arri.setCarryKey(carrykey);
            arri.setBcrData(plt.getBcrData());
            arri.setControlinfo(plt.getControlinfo());
            arri.setSendFlag(SystemDefine.ARRIVAL_NOT_SEND);
            arri.setRegistPname(this.getClass().getSimpleName());
            arri.setLastUpdatePname(this.getClass().getSimpleName());

            //<jp> 荷姿チェックのあるステーションの場合は、Palletが保持する荷姿をセットする。</jp>
            //<en> If the Arrival operates load size checking, set the load size the Pallet preserves.</en>
            if (getStation().isLoadSizeCheck())
            {
                arri.setHeight(plt.getHeight());
            }


            try
            {
                handl.create(arri);
            }
            catch (DataExistsException e1)
            {
                RmiMsgLogClient.writeSQLTrace(e1, getClass().getName());
                throw new ReadWriteException(e1);
            }
        }

        //<jp> トランザクションを確定する。</jp>
        //<en>commit the transaction</en>
        try
        {
            getConnection().commit();
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
            throw new ReadWriteException(e);
        }
    }

    /**<jp>
     * ステーションのCARRYKEY、荷姿情報、BCデータをクリアします。
     * 搬送指示応答の受信、データキャンセルなどで到着情報が不要になった場合に使用されます。
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      削除すべきデータが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Clears the CARRYKEY, load size information and BC data in station.
     * This will be used when arrival information is unnecessary due to data cancel or receiving of 
     * reply to carry instructions, etc..
     * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
     * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
     * @throws NotFoundException      :Notifies if data to delete cannot be found.
     </en>*/
    public void dropArrival()
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        //        StationAlterKey altkey = new StationAlterKey();
        //        altkey.setStationNo(_station.getStationNo());
        //        altkey.updateCarryKey(null);
        //        altkey.updateBcrData(null);
        //        altkey.updateControlinfo(null);
        //        altkey.updateHeight(0);
        //        StationHandler handl = new StationHandler(getConnection());
        //        handl.modify(altkey);
    }

    // DFKLOOK:ここから修正(LOTTE対応)
    // ダミー到着クリア対応処理
    /**<jp>
     * ステーションのCARRYKEY、荷姿情報、BCデータをクリアします。
     * 搬送指示応答の受信、データキャンセルなどで到着情報が不要になった場合に使用されます。
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      削除すべきデータが見つからない場合に通知されます。
     </jp>*/
    public void dropArrivalWithCarryKey(String carrykey)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        ArrivalSearchKey altkey = new ArrivalSearchKey();
        altkey.setStationNo(_station.getStationNo());
        altkey.setCarryKey(carrykey);
        ArrivalHandler handl = new ArrivalHandler(getConnection());
        try
        {
            handl.drop(altkey);
        }
        catch (NotFoundException e)
        {
            // Catch NotFounf Exception 
            System.out.println("---- mckey " + carrykey + " is another arrival data.  stno = "
                    + _station.getStationNo() + "----");
        }
    }

    // DFKLOOK:ここまで修正(LOTTE対応)

    /**<jp>
     * 到着した搬送データの処理を行います。
     * このメソッドの実装は、必要に応じてサブクラスによって行われます。
     * @param ci  対象CarryInformation
     * @param plt 対象Pallet
     * @throws InvalidDefineException インスタンス内の情報に不整合がある場合に通知されます。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Processes the arrived carry data.
     * This method is implemented by sub class on demand.
     * @param ci :target CarryInformation
     * @param plt :target Pallet
     * @param caller :called Class
     * @throws InvalidDefineException :Notifies if there are any data inconcistency in instance.
     * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
     * @throws NotFoundException      :Notifies if there is no such data.
     * @throws ScheduleException
     </en>*/
    public void arrival(CarryInfo ci, Pallet plt, Class caller)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException,
                ScheduleException
    {
        //<jp> 不正な呼び出しです。このステーションでは到着処理は出来ません。</jp>
        //<en> This is not a proper call. The on-line indication cannot be updated in this station.</en>
        throw new InvalidDefineException("This Station is Not arrival operation");
    }

    /**<jp>
     * ステーションに対する作業表示および作業指示更新処理を行ないます。
     * このメソッドの実装は、必要に応じてサブクラスによって行われます。
     * <code>Station</code>クラスでこのメソッドを呼び出した場合、InvalidDefineExceptionを通知します。
     * @param ci 対象CarryInformation
     * @param caller 呼び出し元クラス
     * @throws InvalidDefineException インスタンス内の情報に不整合がある場合に通知されます。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Processes the on-line indications and its updates.
     * This method is implemented by sub class on demand.
     * If this method is called in <code>Station</code>, InvalidDefineException will be notified.
     * @param ci :target CarryInformation
     * @param caller :called Class
     * @throws InvalidDefineException :Notifies if there are any data inconcistency in instance.
     * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
     * @throws NotFoundException      :Notifies if there is no such data.
     * @throws ScheduleException
     </en>*/
    public void operationDisplayUpdate(CarryInfo ci, Class caller)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException,
                ScheduleException
    {
        //<jp> 不正な呼び出しです。このステーションでは作業指示更新処理は出来ません。</jp>
        //<en> This is not a proper call. The on-line indication cannot be updated in this station.</en>
        throw new InvalidDefineException("This Station is Not Operation Display");
    }

    /**<jp>
     * 搬送データの更新を行います。
     * このメソッドの実装は、必要に応じてサブクラスによって行われます。
     * @param ci 対象CarryInformation
     * @throws InvalidDefineException インスタンス内の情報に不整合がある場合に通知されます。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Updates the carry data.
     * This method is implemented by sub class on demand.
     * @param ci :target CarryInformation
     * @throws InvalidDefineException :Notifies if there are any data inconcistency in instance.
     * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
     * @throws NotFoundException      :Notifies if there is no such data.
     * @throws ScheduleException
     </en>*/
    public void updateArrival(CarryInfo ci)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException,
                ScheduleException
    {
        //<jp> 不正な呼び出しです。このステーションでは搬送データの更新は出来ません。</jp>
        //<en> This is not a proper call. The on-line indication cannot be updated in this station.</en>
        throw new InvalidDefineException("This Station is Not updateArrival operation");
    }

    /**<jp>
     * 搬送データの更新を行います。
     * このメソッドの実装は、必要に応じてサブクラスによって行われます。
     * @param ci 対象CarryInformation
     * @param caller 呼び出し元クラス
     * @throws InvalidDefineException インスタンス内の情報に不整合がある場合に通知されます。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Updates the carry data.
     * This method is implemented by sub class on demand.
     * @param ci :target CarryInformation
     * @param caller :called Class
     * @throws InvalidDefineException :Notifies if there are any data inconcistency in instance.
     * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
     * @throws NotFoundException      :Notifies if there is no such data.
     * @throws ScheduleException
     </en>*/
    public void updateArrival(CarryInfo ci, Class caller)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException,
                ScheduleException
    {
        //<jp> 不正な呼び出しです。このステーションでは搬送データの更新は出来ません。</jp>
        //<en> This is not a proper call. The on-line indication cannot be updated in this station.</en>
        throw new InvalidDefineException("This Station is Not updateArrival operation");
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**
     * 自ステーションNo.を返します。
     * @return ステーションNo.
     */
    protected String getStationNo()
    {
        return _station.getStationNo();
    }

    /**
     * 作業表示運用を返します。
     * @return 作業表示運用
     */
    protected String getOperationDisplay()
    {
        return _station.getOperationDisplay();
    }

    /**<jp>
     * 搬送指示送信タスクに対して、搬送指示処理の要求を行ないます。
     * このメソッドは、ステーションの到着処理後、搬送指示を送信する必要がある場合に使用します。
     * 通常、このクラスを継承したクラスが到着処理後に使用します。
     * 搬送指示送信か、自動モード切替搬送指示送信のどちらを呼び出すかは、このステーションの
     * モード切替種別によって決まります。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     </jp>*/
    /**<en>
     * Submits request for carry instruction processing to the transmit task of carry instruction.
     * This method will be used when the carry isntruction needs to be sent aftere the arrival process
     * at station.
     * Normally the class derived from this class will use this after the arrival processing.
     * Alternatives between carry isntruction and automatic mode switching carry isntruction for the
     * transmission will be selected according to the mode swtich type of this station.
     * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
     </en>*/
    protected void carryRequest()
            throws ReadWriteException
    {
        try
        {
            //<jp> 自動モード切替ステーションの場合、</jp>
            //<jp> 自動モード切替搬送指示をwait()から抜けさせるため、</jp>
            //<jp> メッセ－ジの送信を行なう。</jp>
            //<en> If it is the automatic mode switching station,</en>
            //<en> it submits the message in order to pulls the automatic mode switch </en>
            //<en> carry instruction out from wait().</en>
            if (Station.MODE_TYPE_AUTO_CHANGE.equals(_station.getModeType()))
            {
                if (WmsParam.MULTI_ASRSSERVER)
                {
                    //<jp> AutomaticModeChangeSenderに要求メッセージを送る。</jp>
                    //<en> Sends request message to AutomaticModeChangeSender.</en>
                    RmiSendClient rmiSndC =
                            new RmiSendClient(RmiSendClient.RMI_REG_SERVER + getStation().getControllerNo());
                    Object[] param = new Object[2];
                    param[0] = null;
                    rmiSndC.write("AutomaticModeChangeSender" + getStation().getControllerNo(), param);
                }
                else
                {
                    RmiSendClient rmiSndC = new RmiSendClient();
                    Object[] param = new Object[2];
                    param[0] = null;
                    rmiSndC.write("AutomaticModeChangeSender", param);
                }
            }
            else
            {
                if (WmsParam.MULTI_ASRSSERVER)
                {
                    //<jp> StorageSenderに要求メッセージを送る。</jp>
                    //<en> Sends request message to StorageSender.</en>
                    RmiSendClient rmiSndC =
                            new RmiSendClient(RmiSendClient.RMI_REG_SERVER + getStation().getControllerNo());
                    Object[] param = new Object[2];
                    param[0] = null;
                    rmiSndC.write("StorageSender" + getStation().getControllerNo(), param);
                }
                else
                {
                    RmiSendClient rmiSndC = new RmiSendClient();
                    Object[] param = new Object[2];
                    param[0] = null;
                    rmiSndC.write("StorageSender", param);
                }
            }
        }
        catch (Exception e)
        {
            //<jp> 呼出し元（arrivalメソッド)の構造を考慮し、</jp>
            //<jp> ここではReadWriteExceptionをthrowする。</jp>
            //<en> Taking the structure of the call source (arrival mthod) into account, </en>
            //<en> it throws ReadWriteException.</en>
            throw new ReadWriteException();
        }
    }

    // Private methods -----------------------------------------------
    /**
     * データベースからステーション情報を取得しStationインスタンスを生成します。
     * @param conn データベースのコネクション
     * @param stno ステーションのステーションNo.
     * @return ステーションインスタンス
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     */
    private Station getStation(Connection conn, String stno)
            throws ReadWriteException
    {
        StationSearchKey key = new StationSearchKey();
        key.setStationNo(stno);
        StationHandler wStationHandler = new StationHandler(conn);
        Entity[] ent = wStationHandler.find(key);

        return (Station)ent[0];
    }

    /**
     * callerを返します。
     * @return callerを返します。
     */
    public Class getCaller()
    {
        return caller;
    }

    /**
     * callerを設定します。
     * @param caller caller
     */
    public void setCaller(Class caller)
    {
        this.caller = caller;
    }
}
//end of class
