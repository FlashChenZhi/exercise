// $Id: StorageStationOperator.java 87 2008-10-04 03:07:38Z admin $
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
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Station;

/**<jp>
 * 入庫専用ステーションの動作を定義したステーションです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/03/25</TD><TD>M.INOUE</TD><TD>作業指示で完了ボタンを押された場合、搬送状態を開始に変更していなかった不具合を修正</TD></TR>
 * <TR><TD>2005/11/01</TD><TD>Y.Okamura</TD><TD>eWareNavi対応</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * Defined in this class is the behaviour of storage dedicated station.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/03/25</TD><TD>M.INOUE</TD><TD>In case of putting on the complete button, Correction made so that carrying status will be 'start'.</TD></TR>
 * <TR><TD>2005/11/01</TD><TD>Y.Okamura</TD><TD>For eWareNavi</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class StorageStationOperator
        extends StationOperator
{
    // Class fields --------------------------------------------------
    /**<jp>
     * クラス名
     </jp>*/
    /**<en>
     * class name
     </en>*/
    private static final String CLASS_NAME = StorageStationOperator.class.getSimpleName();

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
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
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
     * Create new isntance of <code>Station</code>. If the instance which already has the defined
     * stations is needed, please used <code>StationFactory</code> class.
     * @param  conn     :Connection with database
     * @param  snum     :own station no. preserved
     * @throws ReadWriteException     : Notifies if any trouble occured in data access. 
     * @see StationFactory
     </en>*/
    public StorageStationOperator(Connection conn, String snum) throws ReadWriteException
    {
        super(conn, snum);
    }

    /**
     * 新しい<code>StorageStationOperator</code>のインスタンスを作成します。
     * 引数で渡された、Stationインスタンスを保持します。
     * @param conn データベースコネクション
     * @param st   ステーションインスタンス
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     */
    public StorageStationOperator(Connection conn, Station st) throws ReadWriteException
    {
        super(conn, st);
    }

    /**<jp>
     * 入庫専用ステーションにおける到着処理です。
     * 到着した搬送データの情報をステーションに記録します。
     * 入庫専用ステーションなので、到着報告なしのステーションでこのメソッドが呼び出された場合は例外を返します。
     * @param  ci 更新対象CarryInformation
     * @param  plt Palletインスタンス
     * @throws InvalidDefineException 本ステーションが到着報告無しの場合に通知されます。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知されます。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * This is the arrival process at the storage dedicated station.
     * It records the carry data arrived in station.
     * As the station is storage dedicated, It returns exception if this method is called to the station
     * where the arrival report is not operated.
     * @param ci  :CarryInformation to update
     * @param plt :Pallet instance
     * @param caller :called Class
     * @throws InvalidDefineException :Notifies if the station does not operate the arrival report.
     * @throws ReadWriteException     :Notifies if error occured in data access. 
     * @throws NotFoundException      :Notifies if such data cannot be found.
     </en>*/
    public void arrival(CarryInfo ci, Pallet plt, Class caller)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        //<jp> ステーションの到着チェック有無を確認する。</jp>
        //<en> Checks whether/not the station handles the arrival check.</en>
        if (getStation().isArrivalCheck())
        {
            //<jp> 到着報告有りのステーションであれば、到着データを記録する。</jp>
            //<en> If the station handles the arrival reports, it records the arrival data.</en>
            registArrival(ci.getCarryKey(), plt);

            //<jp> 搬送指示送信要求を行う。</jp>
            //<en> Then submits the request for carry instruction.</en>
            carryRequest();
        }
        else
        {
            //<jp> 到着報告無しのステーションの場合は例外を返す。</jp>
            //<en> If the station does not handle the arrival reports, it returns exception.</en>
            //<jp> 6026032=定義されていないステーション状態です。</jp>
            //<en> 6026032=The station status is not defined.</en>
            Object[] tObj = new Object[1];
            RmiMsgLogClient.write(6026032, LogMessage.F_ERROR, "StorageStation", tObj);
            throw new InvalidDefineException("6026032");
        }
    }

    /**<jp>
     * このステーションに対する作業表示および作業指示更新処理を行います。
     * 入庫専用ステーションの作業指示更新処理は以下の作業を行います。
     *   1.搬送データの状態を開始に変更する。
     *   2.搬送データのCarryKeyと一致する作業表示データ(<code>OperationDisplay</code>)を削除する。
     *   3.搬送指示送信要求を行う。
     * ステーションの作業表示属性が作業表示なしの場合にこのメソッドが呼び出された場合は、
     * InvalidDefineExceptionを通知します。
     * @param ci 対象CarryInformation
     * @param caller 呼び出し元クラス
     * @throws InvalidDefineException インスタンス内の情報に不整合がある場合に通知されます。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知されます。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Processing the on-line indication on screen and its updates for this station.
     * Following procedures are taken to update the job instruction for storage dedicated station.
     *   1.Alter the status of carry data to 'start'.
     *   2.Delete the data of on-line indication(<code>OperationDisplay</code>) that matches the CarryKey
     *     of the carry data.
     *   3.Then submits the request for carry instruction.
     * If this method is called to the station with attribute of no on-line indication, it notifies
     * InvalidDefineException.
     * @param ci :target CarryInformation
     * @param caller :called Class
     * @throws InvalidDefineException :Notifies if there are any data inconsistency in instance.
     * @throws ReadWriteException     :Notifies if error occured in data access.
     * @throws NotFoundException      :Notifies if there is no such data.
     </en>*/
    public void operationDisplayUpdate(CarryInfo ci, Class caller)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        CarryInfoHandler chandl = new CarryInfoHandler(getConnection());
        CarryInfoAlterKey altkey = new CarryInfoAlterKey();
        OperationDisplayHandler ohandl = new OperationDisplayHandler(getConnection());
        OperationDisplaySearchKey skey = new OperationDisplaySearchKey();

        //<jp> 本メソッドはステーションが作業指示有りの運用の場合のみ有効</jp>
        //<en> This method is valid only with stations that operate the on-line indiciaton screens.</en>
        if (Station.OPERATION_DISPLAY_INSTRUCTION.equals(getOperationDisplay()))
        {
            //<jp> 入庫の場合、搬送データを開始に変更し、作業表示データを削除する。</jp>
            //<en> If it is a storage, alter the status of carry data to 'start', then</en>
            //<en> delete the data of on-line indication.</en>
            if (CarryInfo.CARRY_FLAG_STORAGE.equals(ci.getCarryFlag())
                    || CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
            {
                //<jp> 搬送状態を開始に変更する</jp>
                //<en> alter the status of carry data to 'start'</en>
                altkey.setCarryKey(ci.getCarryKey());
                altkey.updateCmdStatus(CarryInfo.CMD_STATUS_START);
                altkey.updateLastUpdatePname(CLASS_NAME);
                chandl.modify(altkey);

                //<jp> 作業表示データの削除</jp>
                //<en> Deleting the on-line indicaiton data</en>
                skey.setCarryKey(ci.getCarryKey());
                ohandl.drop(skey);

                //<jp> 搬送指示送信要求を行う。</jp>
                //<en> Then submits the request for carry instruction.</en>
                carryRequest();
            }
            else
            {
                //<jp> FTTB ログメッセージ出力</jp>
                //<jp> 上記以外の状態は処理できない作業種別なので例外をスローする。</jp>
                //<en> Other status than above are of the work type unavailable for processing, therefore</en>
                //<en> it throws exceptions.</en>
                throw new InvalidDefineException("");
            }
        }
        else
        {
            //<jp> FTTB ログメッセージ出力</jp>
            //<jp> 作業表示無しのステーションに対してこのメソッドが呼ばれた場合はスローする。</jp>
            //<en> Throws exception if this method is called to the station where the on-line indicaiton is not </en>
            //<en> in operation.</en>
            throw new InvalidDefineException("");
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

