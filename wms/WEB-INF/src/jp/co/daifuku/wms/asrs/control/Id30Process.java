// $Id: Id30Process.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id30;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.common.DsNumberDefine;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.AisleAlterKey;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.MachineAlterKey;
import jp.co.daifuku.wms.base.dbhandler.MachineHandler;
import jp.co.daifuku.wms.base.dbhandler.MachineSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Machine;
import jp.co.daifuku.wms.base.entity.Station;

/**<jp>
 * 機器状態報告を処理するクラスです。
 * テキスト内の機種Code、号機Noに一致するMACHINEの状態を受信内容で更新します。
 * 機器状態が変更された場合、その機器が所属するステーションの状態も使用可または使用不可に変更します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2006/02/02</TD><TD>Y.Okamura</TD><TD>eWareNavi対応</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </jp>*/
public class Id30Process
        extends IdProcess
{
    // Class variables -----------------------------------------------
    /**
     * オペレーションログ情報セット回数を定義
     * (COM_OPERATIONLOG ４０項目－１(AGC-NO)) / ４
     */
    private static final int LOOPMAX = 9;

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
    public Id30Process()
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
    public Id30Process(String agcNo)
    {
        super(agcNo);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * ステーションの状態を使用可能または使用不可能に変更します。ハンドラを使用してデータベースを更新します。
     * 機器状態が故障中、切離中になっている機器の数のカウントを行い
     * 一件でもあれば、ステーションを使用不可に更新します。
     * 一件もなければ、ステーションを使用可能に更新します。
     * @param  conn    データベースの接続コネクション
     * @param  st      ステーション
     * @throws InvalidStatusException テーブル更新の設定値に不整合があった場合に通知します。
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      このステーションがデータベースに見つからない場合に通知します。
     </jp>*/
    @SuppressWarnings("cast")
    public void updateStatus(Connection conn, Station st)
            throws InvalidStatusException,
                InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        //<jp> MachineHandlerを使用し、このステーションに対応する機器の機器情報を取得する。</jp>
        //<en> Gets machine information supporting this station by using MachineHandler.</en>
        MachineHandler mhandl = new MachineHandler(conn);
        MachineSearchKey key = new MachineSearchKey();
        key.setStationNo(st.getStationNo());
        Machine[] mstat = (Machine[])mhandl.find(key);

        //<jp> 取得した機器情報群の状態を確認する。</jp>
        //<jp> 機器状態中に異常と切離しが含まれている場合、切離しを優先して記憶</jp>
        //<en> Checks the status of machine data group obtained.</en>
        //<en> If the error status or off-line status are included in the machine status, </en>
        //<en> it gives priority to off-line status when storing in memory.</en>
        int ngCnt = 0;
        String as21Stat = Machine.MACHINE_STATE_ACTIVE;
        for (int i = 0; i < mstat.length; i++)
        {
            if (Machine.MACHINE_STATE_FAIL.equals(mstat[i].getStatusFlag()))
            {
                ngCnt++;
                if (!Machine.MACHINE_STATE_OFFLINE.equals(as21Stat))
                {
                    as21Stat = Machine.MACHINE_STATE_FAIL;
                }
            }
            else if (Machine.MACHINE_STATE_OFFLINE.equals(mstat[i].getStatusFlag()))
            {
                ngCnt++;
                as21Stat = Machine.MACHINE_STATE_OFFLINE;
            }
        }

        //<jp> 異常または切離しのカウントが基準を超えた場合、このステーションの状態を更新する。</jp>
        //<jp> 基準以下の場合、現在のステーションの状態が正常以外であれば正常に変更する。</jp>
        //<en> If the counts of error or off-line exceeded the standard, it renews the status of this station.</en>
        //<en> If the counts remain standard or below, and if current status of the station is anything other than</en>
        //<en> normal, it should alter the status to 'normal'.</en>
        String mcStat = null;
        String currentState = st.getStatus();
        if (ngCnt > Station.STATION_NG_JUDGMENT)
        {
            if (Machine.MACHINE_TYPE_STVS.equals(mstat[0].getMachineType())
                    || Machine.MACHINE_TYPE_STVL.equals(mstat[0].getMachineType()))
            {
                boolean updateFlag = true;
                for (Machine msts :mstat)
                {
                    if (!Machine.MACHINE_STATE_OFFLINE.equals(msts.getStatusFlag())){
                        updateFlag = false;
                    }
                }
                
                if (updateFlag)
                {
                    mcStat = Station.STATION_STATUS_DISCONNECTED;
                }
                else 
                {
                    mcStat = Station.STATION_STATUS_NORMAL;    
                }
            }
            else
            {
                if (Station.STATION_STATUS_NORMAL.equals(currentState))
                {
                    if (Machine.MACHINE_STATE_FAIL.equals(as21Stat))
                    {
                        mcStat = Station.STATION_STATUS_NORMAL;
                    }
                    else
                    {
                        mcStat = Station.STATION_STATUS_DISCONNECTED;
                    }
                }
                else if (Station.STATION_STATUS_DISCONNECTED.equals(currentState))
                {
                    if (Machine.MACHINE_STATE_FAIL.equals(as21Stat) || Machine.MACHINE_STATE_ACTIVE.equals(as21Stat))
                    {
                        mcStat = Station.STATION_STATUS_NORMAL;
                    }
                    else
                    {
                        mcStat = Station.STATION_STATUS_DISCONNECTED;
                    }
                }
                
            }
        }
        else
        {
            if (!Station.STATION_STATUS_NORMAL.equals(currentState))
            {
                //<jp> ステーションの状態を使用可能に更新。</jp>
                mcStat = Station.STATION_STATUS_NORMAL;
            }
        }

        // invalid status
        if (null == mcStat)
        {
            return;
        }

        if (st instanceof Aisle)
        {
            AisleHandler aisleh = new AisleHandler(conn);
            AisleAlterKey aisleAKey = new AisleAlterKey();
            aisleAKey.setStationNo(st.getStationNo());
            aisleAKey.updateStatus(mcStat);
            aisleh.modify(aisleAKey);
        }
        else if (st instanceof Station)
        {
            StationHandler sth = new StationHandler(conn);
            StationAlterKey stAKey = new StationAlterKey();
            stAKey.setStationNo(st.getStationNo());
            stAKey.updateStatus(mcStat);
            sth.modify(stAKey);
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 機器状態報告電文を処理します。
     * 受信した電文の機種Code,号機No.,AGCNo.から、<code>MACHINE</code>を検索し、
     * 該当する機器情報の更新を行ないます。機器が属するステーションの状態も機器情報の内容に応じて変更されます。
     * また、更新されたステーションに親ステーションが定義されている場合、ステーションの状態が変更された場合、
     * 同時に更新されます。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processing the communication message of machine status report.
     * Based on the model code, machine no. and AGCno. in received communication mesasage, it 
     * searches <code>MACHINE</code> then updates the corresponding machine information.
     * It also modifies the status of station this machine belongs to according to the contents of machine information.
     * Also if the updated station is defined with parent station, the parent station will be modified accordingly.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        //<jp> As21Id30のインスタンス生成</jp>
        //<en> Generates the instance of As21Id30.</en>
        As21Id30 id30dt = new As21Id30(rdt);

        MachineHandler as21MstatH = new MachineHandler(getConnection());
        MachineSearchKey machineKey = new MachineSearchKey();

        //<jp> Hashtable のインスタンス生成</jp>
        //<en> Generates the instance of Hashtable.</en>
        Set<String> updateStationNoSet = new HashSet<String>();
        Set<String> parentStationSet = new HashSet<String>();

        // ADD START 2010/09/13
        // 受信データを一度にロック
        machineKey.clear();
        for(int i = 0; i < id30dt.getNumberOfReports(); i++)
        {
            machineKey.setControllerNo(getAgcNo() ,"=","(","",true);
            machineKey.setMachineType(id30dt.getModelCode(i),"=","","",true);
            machineKey.setMachineNo(String.valueOf(Integer.valueOf(id30dt.getMachineNo(i))),"=","",")",false);
        }
        as21MstatH.findForUpdate(machineKey);
        // ADD END 2010/09/13

        //<jp> 受信テキストの報告データ数分処理</jp>
        //<en> Process as much report data in received text.</en>
        for (int i = 0; i < id30dt.getNumberOfReports(); i++)
        {
            String imodelCode = id30dt.getModelCode(i);
            String imachineNo = String.valueOf(Integer.valueOf(id30dt.getMachineNo(i)));

            //<jp> 検索項目（機種Code,号機No.,AGCNo.）をKEYに検索</jp>
            //<en> Search according to the search items (model code, machine no. and AGCno.) as a KEY.</en>
            machineKey.clear();
            machineKey.setMachineType(imodelCode);
            machineKey.setMachineNo(imachineNo);
            machineKey.setControllerNo(getAgcNo());
            Machine machine = (Machine)as21MstatH.findPrimary(machineKey);
            if (null == machine)
            {
                //<jp> 報告された機器情報が見つからなけばログに出力</jp>
                //<en> Output the log if reported machine information was not found.</en>
                Object[] tObj = {
                        imodelCode,
                        imachineNo,
                        getAgcNo(),
                };
                //<jp> 6024026=報告された機器の定義が機器情報内に見つかりません。種別={0} 号機No.={1} コントローラーNo.={2}</jp>
                //<en> 6024026=Reported equipment is not found in machine data. Type={0} Machine No.={1} SRC No.={2}</en>
                RmiMsgLogClient.write(6024026, LogMessage.F_WARN, getClass().getName(), tObj);
                continue;
            }

            //<jp> 機器情報の更新を行う。</jp>
            //<en> Updates the machine information.</en>
            MachineAlterKey machineAKey = new MachineAlterKey();
            machineAKey.setMachineType(machine.getMachineType());
            machineAKey.setMachineNo(machine.getMachineNo());
            machineAKey.setControllerNo(machine.getControllerNo());
            switch (id30dt.getCondition(i))
            {
                //<jp> 運転中</jp>
                //<en> operating</en>
                case As21Id30.STATE_ACTIVE:
                    machineAKey.updateStatusFlag(Machine.MACHINE_STATE_ACTIVE);
                    machineAKey.updateErrorCode(null);
                    as21MstatH.modify(machineAKey);
                    break;

                //<jp> 停止中</jp>
                //<en> Ustopped</en>
                case As21Id30.STATE_STOP:
                    machineAKey.updateStatusFlag(Machine.MACHINE_STATE_STOP);
                    machineAKey.updateErrorCode(null);
                    as21MstatH.modify(machineAKey);
                    break;

                //<jp> 故障中</jp>
                //<en> down</en>
                case As21Id30.STATE_FAIL:
                    machineAKey.updateStatusFlag(Machine.MACHINE_STATE_FAIL);
                    machineAKey.updateErrorCode(id30dt.getAbnormalCode(i));
                    as21MstatH.modify(machineAKey);
                    break;

                //<jp> 切り離し中</jp>
                //<en> separated</en>
                case As21Id30.STATE_OFFLINE:
                    machineAKey.updateStatusFlag(Machine.MACHINE_STATE_OFFLINE);
                    machineAKey.updateErrorCode(null);
                    as21MstatH.modify(machineAKey);
                    break;

                default:
                    // invalid condition.
            }

            //<jp> 更新されたSTATIONNUMBERを格納</jp>
            //<en> Stores the updated STATIONNUMBER.</en>
            updateStationNoSet.add(machine.getStationNo());
        }

        //<jp> 各STATIONNUMBERによりMACHINE表から同じSTATIONNUMBER機器の状態を見ます。</jp>
        //<en> Based on each STATIONNUMBER, looks into the machine status of same STATIONNUMBER using MACHINE table.</en>
        Iterator<String> e = updateStationNoSet.iterator();

        //<jp> HashtableからSTATIONNUMBERを取得し、機器状態を元にStationの状態更新を行います。</jp>
        //<en> Retrieves STATIONNUMBER from Hashtable, then updates the status of Stations based on machine status.</en>
        while (e.hasNext())
        {
            String stnum = e.next();

            //<jp> ステーションテーブルの更新</jp>
            //<jp> STATIONNUMBERを条件にステーションインスタンス生成し、</jp>
            //<jp> 機器情報を元に状態を更新する。</jp>
            //<en> Updates the station table.</en>
            //<en> Generates the station instance with STATIONNUMBER as condition, then updates the status</en>
            //<en> based on the machine information.</en>
            Station st = StationFactory.makeStation(getConnection(), stnum);
            updateStatus(getConnection(), st);

            //<jp> 親ステーション番号が入っている場合、</jp>
            //<jp> 後で親ステーションの状態を更新するためにHashTableに記録する。</jp>
            //<en> In case the parent station no. is included,</en>
            //<en> it records the no. in HashTable in order to update the status of parent station later.</en>
            if (!StringUtil.isBlank(st.getParentStationNo()))
            {
                parentStationSet.add(st.getParentStationNo());
            }
        }

        Iterator<String> e2 = parentStationSet.iterator();

        StationHandler sth = new StationHandler(getConnection());
        StationSearchKey stKey = new StationSearchKey();
        StationAlterKey stAKey = new StationAlterKey();
        while (e2.hasNext())
        {
            // 親ステーションに属する子ステーションが１つでも使用可能な場合、
            // 親ステーションを使用可能とする。
            // また、親ステーションに属する子ステーションが全て使用不可の場合、
            // 親ステーションを使用不可とする。
            // ただし、親ステーションが作業場である場合(sendable=false)は更新を行わない。
            Station mainSt = StationFactory.makeStation(getConnection(), e2.next());

            stKey.clear();
            stKey.setParentStationNo(mainSt.getStationNo());
            stKey.setStatus(Station.STATION_STATUS_NORMAL);
            // 子ステーションが１つでも使用可能な場合
            if (sth.count(stKey) > 0)
            {
                // 親ステーションが作業場でない場合
                if (mainSt.isSendable())
                {
                    if (Station.STATION_STATUS_DISCONNECTED.equals(mainSt.getStatus()))
                    {
                        stAKey.clear();
                        stAKey.setStationNo(mainSt.getStationNo());
                        stAKey.updateStatus(Station.STATION_STATUS_NORMAL);
                        sth.modify(stAKey);
                    }
                }
            }
            // 子ステーションが全て使用不可の場合
            else
            {
                // 親ステーションが作業場でない場合
                if (mainSt.isSendable())
                {
                    if (Station.STATION_STATUS_NORMAL.equals(mainSt.getStatus()))
                    {
                        stAKey.clear();
                        stAKey.setStationNo(mainSt.getStationNo());
                        stAKey.updateStatus(Station.STATION_STATUS_DISCONNECTED);
                        sth.modify(stAKey);
                    }
                }
            }
        }
        // オペレーションログ出力
        log_write(id30dt);
    }


    /**
     * オペレーションログ情報の書込み処理を行います <BR>
     * @param   id30dt    As21Id30
     * @throws Exception 全ての例外を報告します。
     */
    protected void log_write(As21Id30 id30dt)
            throws Exception
    {
        // オペレーションログ出力
        DfkUserInfo userInfo = new DfkUserInfo();
        // DS番号
        userInfo.setDsNumber(DsNumberDefine.DS_AGC_ID30);
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
        userInfo.setPageNameResourceKey(DsNumberDefine.PAGERESOUCE_AGC_ID30);

        List<String> itemLog = null;

        for (int i = 0; i < id30dt.getNumberOfReports(); i++)
        {

            if (i == 0 || i % LOOPMAX == 0)
            {
                if (i != 0)
                {
                    P11LogWriter opeLogWriter = new P11LogWriter(getConnection());
                    opeLogWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_CONTROL_MODULE, itemLog);
                }
                itemLog = new ArrayList<String>();
                // AGC-No
                itemLog.add(getAgcNo());
            }
            // 機種コード
            itemLog.add(id30dt.getModelCode(i));
            // 号機No
            itemLog.add(id30dt.getMachineNo(i));
            // 状態
            itemLog.add(String.valueOf(id30dt.getCondition(i)));
            // 異常コード
            itemLog.add(id30dt.getAbnormalCode(i));
        }

        // ログ出力
        if (itemLog != null)
        {
            P11LogWriter opeLogWriter = new P11LogWriter(getConnection());
            opeLogWriter.createOperationLog(userInfo, EmConstants.OPELOG_CLASS_CONTROL_MODULE, itemLog);
        }

    }
}
//end of class

