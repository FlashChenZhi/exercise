// $Id: RetrievalStationOperator.java 7481 2010-03-09 02:26:20Z okayama $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.asrs.communication.as21.SystemTextTransmission;
import jp.co.daifuku.wms.asrs.control.CarryCompleteOperator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Station;

/**<jp>
 * 出庫専用ステーションの動作を定義したステーションです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/11/01</TD><TD>Y.Okamura</TD><TD>eWareNavi対応</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7481 $, $Date: 2010-03-09 11:26:20 +0900 (火, 09 3 2010) $
 * @author  $Author: okayama $
 </jp>*/
/**<en>
 * Defined in this clss of station is hte behaviour of retrieve dedicated station.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/11/01</TD><TD>Y.Okamura</TD><TD>For eWareNavi</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7481 $, $Date: 2010-03-09 11:26:20 +0900 (火, 09 3 2010) $
 * @author  $Author: okayama $
 </en>*/
public class RetrievalStationOperator
        extends StationOperator
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

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
        return ("$Revision: 7481 $,$Date: 2010-03-09 11:26:20 +0900 (火, 09 3 2010) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 新しい<code>Station</code>のインスタンスを作成します。既に定義されているステーションを
     * 持つインスタンスが必要な場合は、<code>StationFactory</code>クラスを利用してください。
     * @param conn データベースコネクション
     * @param snum 保持する自ステーション番号
     * @throws ReadWriteException     データベースに対する処理で発生した場合に通知します。
     * @see StationFactory
     </jp>*/
    /**<en>
     * Create a new instance of <code>Station</code>. If the instance which already has the 
     * defined stations is needed, please use <code>StationFactory</code> class.
     * @param  conn     :Connection with database
     * @param  snum     :own station no. preserved
     * @throws ReadWriteException     : Notifies if any trouble occured in data access. 
     * @see StationFactory
     </en>*/
    public RetrievalStationOperator(Connection conn, String snum) throws ReadWriteException
    {
        super(conn, snum);
    }

    /**
     * 新しい<code>RetrievalStationOperator</code>のインスタンスを作成します。
     * 引数で渡された、Stationインスタンスを保持します。
     * @param conn データベースコネクション
     * @param st   ステーションインスタンス
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     */
    public RetrievalStationOperator(Connection conn, Station st) throws ReadWriteException
    {
        super(conn, st);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 出庫専用ステーションにおける到着処理です。
     * 到着した搬送データの更新処理を行います。
     * 出庫専用ステーションなので、到着報告なしのステーションでこのメソッドが呼び出された場合は例外を返します。
     * @param  ci 更新対象CarryInformation
     * @param  plt Palletインスタンス
     * @throws InvalidDefineException 入庫、直行、ダミー到着データを受取った場合に通知されます。
     * @throws InvalidDefineException CarryInformationインスタンス内の搬送区分に不整合があった場合に通知されます。
     * @throws ReadWriteException     データベースに対する処理で発生した場合に通知されます。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * This is the arrival process at the retrieve dedicated station.
     * Update process of the carry data arrived.
     * The station is retrieve dedicated; if this method is called for the station which does not
     * operate the arrival reports, it returns exception.
     * @param ci  :CarryInformation to update
     * @param plt :Pallet instance
     * @param caller :called Class
     * @throws InvalidDefineException :Notifies if it received the data of storage, direct travel or dummy arival.
     * @throws InvalidDefineException :Notifies if there are any inconsistency with tranport section in 
     * CarryInformation instance.
     * @throws ReadWriteException     :Notifies if exception occured when processing for database.
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
            //<jp> 出庫専用ステーションでダミーパレットの到着は無効なので例外を返す。</jp>
            //<jp> 出庫ステーションでダミーパレットの到着は無効なので例外を返す。</jp>
            //<en> Arrival of dummy pallet at the retrieve dedicated station is invalid; returns exception.</en>
            //<en> Arrival of dummy pallet at retrieval station is invalid; returns exception.</en>
            //<jp> 6024022=出庫専用ステーションです。出庫以外の到着報告は無効です。StationNo={0} mckey={1}</jp>
            //<en> 6024022=Picking only station. Storage arrival report is invalid. ST No={0} mckey={1}</en>
            Object[] tObj = new Object[2];
            tObj[0] = getStationNo();
            tObj[1] = ci.getCarryKey();
            RmiMsgLogClient.write(6024022, LogMessage.F_NOTICE, "RetrievalStationOperator", tObj);
            throw new InvalidDefineException(WmsMessageFormatter.format(6024022, tObj));
        }

        //<jp> CarryInformationの搬送区分を元に処理を分岐</jp>
        //<en> Branches the process according to the transport section of CarryInformation.</en>

        //<jp> 入庫データの場合は例外を返す。</jp>
        //<en> If it received storage data, returns null.</en>
        if (CarryInfo.CARRY_FLAG_STORAGE.equals(ci.getCarryFlag()))
        {
            throw new InvalidDefineException("Storage Pallet is invalid arrival");
        }
        //<jp> 直行データの場合、ユニット出庫払出し処理を行う。</jp>
        //<en> If it received direct travel data, processes the unit retrieval transfer.</en>
        else if (CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
        {
            //<jp> 搬送データを削除（ユニット出庫払い出しと同じ扱い）</jp>
            //<en> Deletes the carry data (handling just as unit retrieval transfer).</en>
            CarryCompleteOperator carryOperate = new CarryCompleteOperator(getConnection(), getClass());
            // 再入庫データは作成しない
            carryOperate.unitRetrieval(ci, false);
        }
        //<jp> 出庫データの場合、出庫到着処理を呼び出す。</jp>
        //<en> If it received the retrieval data, call the retrieval arrival process.</en>
        else if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(ci.getCarryFlag()))
        {
            updateArrival(ci);
        }
        else
        {
            //<jp> 6024018=取得したインスタンス{0}の属性{1}の値が不正です。{1}={2}</jp>
            //<en> 6024018=Attribute {1} value of acquired instance {0} is invalid. {1}={2}</en>
            Object[] tObj = new Object[3];
            tObj[0] = "CarryInfomation";
            tObj[1] = "CarryKind";
            tObj[2] = new Integer(ci.getCarryFlag());
            RmiMsgLogClient.write(6024018, LogMessage.F_WARN, "RetrievalStationOperator", tObj);
        }
    }

    /**<jp>
     * 出庫データの出庫口到着処理です。
     * 出庫専用ステーションにおける搬送データの更新処理を行います。
     * 出庫専用ステーションなので出庫指示の内容に関わらず搬送データを削除します。
     * @param ci 更新対象CarryInformation
     * @throws InvalidDefineException 定義に不整合がある場合に通知されます。
     * @throws ReadWriteException     データベースに対する処理で発生した場合に通知されます。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Processing the retrieval data for the arrival at retrieval station.
     * It updates the carry data at retrieval dedicated station.
     * As it is the retrieve dedicated station, delete the carry data regardless of the contents of retrieval instruction.
     * @param ci :CarryInformation to be updated
     * @throws InvalidDefineException :Notifies if there are any inconsistency in definition.
     * @throws ReadWriteException     :Notifies if exception occured when processing for database.
     * @throws NotFoundException      :Notifies if there is no such data.
     * @throws ScheduleException 
     </en>*/
    public void updateArrival(CarryInfo ci)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException,
                ScheduleException
    {
        CarryCompleteOperator carryOperate = new CarryCompleteOperator(getConnection(), getCaller());

        if (getStation().isReStoringOperation())
        {
            //<jp> ユニット出庫在庫更新（再入庫予定データ作成）</jp>
            //<en> Updates the unit retrieval stocks (with creation of re-storage schedule data)</en>
            carryOperate.unitRetrieval(ci, true);
        }
        else
        {
            //<jp> ユニット出庫在庫更新（再入庫予定データ作成なし）</jp>
            //<en> Updates the unit retrieval stocks (with no creation of re-storage schedule data)</en>
            carryOperate.unitRetrieval(ci, false);
        }
    }

    /**<jp>
     * このステーションに対する作業表示および作業指示更新処理を行います。
     * 出庫専用ステーションの作業指示更新処理は以下の作業を行います。
     *   1.搬送データのCarryKeyと一致する作業表示データ(<code>OperationDisplay</code>)を削除する。
     *   2.MC作業完了報告を送信する。
     * 搬送データの搬送区分が出庫又は直行ではない場合および、このステーションの作業表示属性が作業表示なしの場合に
     * このメソッドが呼び出された場合は、InvalidDefineExceptionを通知します。
     * @param ci 更新対象CarryInformation
     * @param caller 呼び出し元クラス
     * @throws InvalidDefineException インスタンス内の情報に不整合がある場合に通知されます。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知されます。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Updating the on-line indication and job instructions for this station.
     * Following procedures are taken to update the job instruction for retrieval dedicated station.
     *   1.Delete data of on-line indication(<code>OperationDisplay</code>) which matches the CarryKey of the carry data.
     *   2.Submit the MC work completion report.
     * If this method called and if the transport section of this carry data was anything other than 'retrieval' or
     * 'direct travel', and if the station attribute designates no on-line indications, it notifies InvalidDefineException.
     * @param ci :CarryInformation to be updated
     * @param caller :called Class
     * @throws InvalidDefineException :Notifies if there are any data inconsistency in the instance. 
     * @throws ReadWriteException     :Notifies if any rtouble occured in data access.
     * @throws NotFoundException      :Notifies if there is no such data. 
     </en>*/
    public void operationDisplayUpdate(CarryInfo ci, Class caller)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        //<jp> 本メソッドはステーションが作業指示有りの運用の場合のみ有効</jp>
        //<en> This method is valid only for stations which operates the on-line indication.</en>
        if (Station.OPERATION_DISPLAY_INSTRUCTION.equals(getOperationDisplay()))
        {
            //<jp> 作業表示データを削除する。</jp>
            //<jp> また、MC作業完了報告を送信する。</jp>
            //<en> Deletes the data of on-line indication.</en>
            //<en> Also submit the MC work completion report.</en>
            if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(ci.getCarryFlag())
                    || CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
            {
                //<jp> 作業表示データの削除</jp>
                //<en> Deletes data of on-line indication.</en>
                OperationDisplayHandler ohandl = new OperationDisplayHandler(getConnection());
                OperationDisplaySearchKey skey = new OperationDisplaySearchKey();
                skey.setCarryKey(ci.getCarryKey());
                ohandl.drop(skey);

                try
                {
                    //<jp> MC作業完了報告を送信する。</jp>
                    //<en> Then submit the MC work completion report.</en>
                    SystemTextTransmission.id45send(ci, getConnection());
                }
                catch (Exception e)
                {
                    throw new ReadWriteException();
                }
            }
            else
            {
                //<jp> FTTB ログメッセージ出力</jp>
                //<jp> 出庫、直行以外の状態は処理できない作業種別なので例外をスローする。</jp>
                //<en> For any status other than retrieval and direct travel, it throws exceptions</en>
                //<en> as their work types are unavailable for processing.</en>
                throw new InvalidDefineException("");
            }
        }
        else
        {
            //<jp> FTTB ログメッセージ出力</jp>
            //<jp> 作業表示無しのステーションに対してこのメソッドが呼ばれた場合は例外をスローする。</jp>
            //<en> If htis method is called for the station where the on-line indicaiotns is not operated,</en>
            //<en> throw exception.</en>
            throw new InvalidDefineException("");
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

