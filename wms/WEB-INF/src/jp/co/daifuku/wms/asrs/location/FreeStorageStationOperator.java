// $Id: FreeStorageStationOperator.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Station;

/**<jp>
 * 入庫フリーステーション（コの字入庫側など）の動作を定義したステーションです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/02/17</TD><TD>INOUE</TD><TD>Station表のHeightに到着の荷姿から取得した値をセットするように変更</TD></TR>
 * <TR><TD>2005/11/01</TD><TD>Y.Okamura</TD><TD>eWareNavi対応</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * Defined in this class of station is the behaviour of storage free station
 * (storage side of U-shaped conveyor, etc.)
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/02/17</TD><TD>INOUE</TD><TD>Correction made so that load size info will be set from arrival data.</TD></TR>
 * <TR><TD>2005/11/01</TD><TD>Y.Okamura</TD><TD>For eWareNavi</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class FreeStorageStationOperator
        extends StationOperator
{
    // Class fields --------------------------------------------------
    /**<jp>
     * クラス名
     </jp>*/
    /**<en>
     * class name
     </en>*/
    private static final String CLASS_NAME = FreeStorageStationOperator.class.getSimpleName();

    // Class variables -----------------------------------------------
    /**<jp>
     * 対となるフリー出庫ステーションのステーション番号を保持します。
     </jp>*/
    /**<en>
     * Preserves the station no. of pairing free retrieval station.
     </en>*/
    private String _freeRetrievalStationNo = null;

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
     * @throws IOException            ファイルI/Oエラーが発生した場合に通知されます。
     * @see StationFactory
     </jp>*/
    /**<en>
     * Creates new instance of <code>Station</code>. If the instance which has stations
     * already defined is needed, please use <code>StationFactory</code> class.
     * @param  conn     :Connection with database
     * @param  snum     :own station no. preserved
     * @throws ReadWriteException     : Notifies if any trouble occured in data access. 
     * @throws IOException            : Notifies if file I/O error occured.
     * @see StationFactory
     </en>*/
    public FreeStorageStationOperator(Connection conn, String snum) throws ReadWriteException,
            IOException
    {
        super(conn, snum);
        //<jp> 自ステーションNoを元に、対となるフリー出庫ステーションを取得する。</jp>
        //<en> Retrieves the paring free retrieval station, according to the own station no.</en>
        _freeRetrievalStationNo = Route.getConnectorStation(snum);
    }

    /**
     * 新しい<code>FreeStorageStationOperator</code>のインスタンスを作成します。
     * 引数で渡された、Stationインスタンスを保持します。
     * @param conn データベースコネクション
     * @param st   ステーションインスタンス
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws IOException            ファイルI/Oエラーが発生した場合に通知されます。
     */
    public FreeStorageStationOperator(Connection conn, Station st) throws ReadWriteException,
            IOException
    {
        super(conn, st);
        //<jp> 自ステーションNoを元に、対となるフリー出庫ステーションを取得する。</jp>
        //<en> Retrieves the paring free retrieval station, according to the own station no.</en>
        _freeRetrievalStationNo = Route.getConnectorStation(st.getStationNo());
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 対となる出庫ステーションを取得します。
     * @return 対となる出庫ステーション
     </jp>*/
    /**<en>
     * Retrieves retrieval station, the other of ther pair.
     * @return retrieval station pairing
     </en>*/
    public String getFreeRetrievalStationNo()
    {
        return _freeRetrievalStationNo;
    }

    /**<jp>
     * 入庫フリーステーションにおける到着処理です。
     * 到着した搬送データの更新処理を行います。
     * 受取ったCarryInformationインスタンスは必ずダミー到着データになるので、
     * 出庫側フリーSTATIONよりCarryInformationインスタンスを取得します。
     * 出庫側フリーSTATIONにデータが存在しない場合、新規入庫データのダミー到着データとして保持します。
     * @param  ci 更新対象CarryInformation
     * @param  plt Palletインスタンス
     * @throws InvalidDefineException インスタンス内の情報に不整合がある場合に通知されます。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * This is the arrival process at the storage free station.
     * Update process of carry data arrived .
     * The CarryInformation instance received should always be thte dummy arrival data; 
     * Retrieves the CarryInformation isntance from the retrieval free station.
     * If there is no data in the retrieval free station, it preserve the data as a dummy arrival data of new storage.
     * @param ci  :CarryInformation to update
     * @param plt :Pallet instance
     * @param caller :called Class
     * @throws InvalidDefineException :Notifies if there are any data inconsistency in the instance. 
     * @throws ReadWriteException     :Notifies if any rtouble occured in data access.
     * @throws NotFoundException      :Notifies if there is no such data. 
     </en>*/
    public void arrival(CarryInfo ci, Pallet plt, Class caller)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        if (ci.getCarryKey().equals(WmsParam.DUMMY_MCKEY))
        {
            //<jp> 対となるフリー出庫ステーションNoをKeyに搬送データを一件取得</jp>
            //<en> Obtain 1 carry data using the free retrieval station no., the other of the pair, as the Key.</en>
            CarryInfo arrivalCi = getArrivalInfo(getFreeRetrievalStationNo(), plt);
            if (arrivalCi == null)
            {
                //<jp> 出庫側に到着データが存在しない場合、</jp>
                //<jp> 新規入庫用のダミー到着データと判断し、ダミー到着データを記録</jp>
                //<en> If there is no arrival data at retrieval side, it determinese the data to be the dummy arrival</en>
                //<en> data and record as a dummy arrival data.</en>
                //<en> If there is no arrival data at the retrieval side, it determines it should be the dummy arrival</en>
                //<en> data of new storage and records the dummy arrival data.</en>
                // または、作業表示運用ありのステーション定義の場合も、新規入庫と同じ処理を行います。
                registArrival(ci.getCarryKey(), plt);
            }
            else
            {
                CarryInfoAlterKey cakey = new CarryInfoAlterKey();
                cakey.setCarryKey(arrivalCi.getCarryKey());
                //<jp> 搬送状態を開始にする</jp>
                //<en> Modify the carry status to 'start'.</en>
                cakey.updateCmdStatus(CarryInfo.CMD_STATUS_START);
                //<jp> 搬送元ステーションNoをこのステーションにする。</jp>
                //<en> Selecting this station for sending station no.</en>
                cakey.updateSourceStationNo(getStationNo());
                cakey.updateLastUpdatePname(CLASS_NAME);

                CarryInfoHandler cinfoHandl = new CarryInfoHandler(getConnection());
                cinfoHandl.modify(cakey);

                //<jp> 出庫側に到着データが存在する場合、自ステーションの到着データとして記録する。</jp>
                //<en> If the arrival data exists at retrieval side, it records as an arrival data of own station.</en>
                //<en> If there is the arrival data at retrieval side, it records the data as the station's own arrival data.</en>
                registArrival(arrivalCi.getCarryKey(), plt);
            }
            //<jp> 搬送指示送信要求を行なう。</jp>
            //<en> Requests to submit the carry instruction.</en>
            carryRequest();
        }
        else
        {
            //<jp> ダミー到着以外は処理できないので例外を返す</jp>
            //<en> It returns exceptions for anything other than dummy arrival. (Only dummy arrival can be processed.)</en>
            Object[] tObj = new Object[2];
            tObj[0] = getStationNo();
            tObj[1] = ci.getCarryKey();
            //<jp> 6024025=ダミー到着以外の到着報告は無効です。StationNo={0} mckey={1}</jp>
            //<en> 6024025=Arrival report is invalid except for dummy arrival. ST No={0} mckey={1}</en>
            RmiMsgLogClient.write(6024025, LogMessage.F_NOTICE, "FreeStorageStationOperator", tObj);
            throw new InvalidDefineException(WmsMessageFormatter.format(6024025, tObj));
        }
    }

    /**<jp>
     * このステーションに対する作業表示および作業指示更新処理を行ないます。
     * コの字フリーステーション入庫側の作業指示更新処理は以下の作業を行ないます。
     *   1.搬送データの状態を開始に変更する。
     *   2.搬送データのCARRYKEYと一致する作業表示データ(<code>OperationDisplay</code>)を削除する。
     *   3.搬送指示送信要求を行う。
     * ステーションの作業表示属性が作業表示なしの場合にこのメソッドが呼び出された場合は、
     * InvalidDefineExceptionを通知します。
     * @param ci 対象CarryInformation
     * @param caller 呼び出し元クラス
     * @throws InvalidDefineException インスタンス内の情報に不整合がある場合に通知されます。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Processing the on-line indication update and job instruction update for this station.
     * Following procedures are taken to update the job instruction for storage free station.
     *   1.Modify the status of carry data to 'start'.
     *   2.Delete the on-line indication data(<code>OperationDisplay</code>) that matches the CARRYKEY in carry data.
     *   3.Then submits the request for carry instruction.
     * If this called to the station with attribute of no on-line indication, it notifies InvalidDefineException.
     * @param ci :objective CarryInformation
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
        CarryInfoHandler chandl = new CarryInfoHandler(getConnection());
        CarryInfoAlterKey cinfoAltkey = new CarryInfoAlterKey();
        OperationDisplayHandler ohandl = new OperationDisplayHandler(getConnection());
        OperationDisplaySearchKey odskey = new OperationDisplaySearchKey();

        //<jp> 本メソッドはステーションが作業指示有りの運用の場合のみ有効</jp>
        //<en> This method is only valid for stations which operates the on-line indication.</en>
        if (Station.OPERATION_DISPLAY_INSTRUCTION.equals(getOperationDisplay()))
        {
            //<jp> 入庫、直行の場合、搬送データを開始に変更し、作業表示データを削除する。</jp>
            //<en> If the transport section says 'storage' or 'direct travel', modify the carry data to 'start' and </en>
            //<en> delete on-line indication data.</en>
            if (CarryInfo.CARRY_FLAG_STORAGE.equals(ci.getCarryFlag())
                    || CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
            {
                //<jp> 搬送状態を開始に変更する</jp>
                //<en> alter the status of carry data to 'start'</en>
                cinfoAltkey.setCarryKey(ci.getCarryKey());
                cinfoAltkey.updateCmdStatus(CarryInfo.CMD_STATUS_START);
                cinfoAltkey.updateLastUpdatePname(CLASS_NAME);
                chandl.modify(cinfoAltkey);

                //<jp> 作業表示データの削除</jp>
                //<en> Deleting the on-line indicaiton data</en>
                odskey.setCarryKey(ci.getCarryKey());
                ohandl.drop(odskey);

                //<jp> 搬送指示送信要求を行う。</jp>
                //<en> Requests to submit the carry instruction.</en>
                carryRequest();
            }
            else
            {
                //<jp> FTTB ログメッセージ出力</jp>
                //<jp> 上記以外の状態は処理できない作業種別なので例外をスローする。</jp>
                //<en> Any status other than above are the work types unavailable for processing; </en>
                //<en> it therefore throws exception.</en>
                throw new InvalidDefineException("");
            }
        }
        else
        {
            //<jp> FTTB ログメッセージ出力</jp>
            //<jp> 作業表示無しのステーションに対してこのメソッドが呼ばれた場合はをスローする。</jp>
            //<en> Also throws exception if the station which does not operate the on-lin indication called this metho.d</en>
            throw new InvalidDefineException("");
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * 到着データを検索し取得します。指定されたステーションのステーションNoと搬送元ステーションNoが一致し
     * 到着日時が最も古いCarryInformationインスタンスを１件返します。
     * @param  stnum 検索対象ステーション番号(コの字出庫ステーションNo.)
     * @param  plt   パレット情報
     * @return 検索されたCarryInformationインスタンス
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     </jp>*/
    /**<en>
     * Search and get arrival data. Return just one instance which has the same sending station number as this
     * specified station number given, and which also contains the oldest arrival date of applicable data.
     * @param  stnum :station number to be searched
     * @param  plt   :pallet information
     * @return       :CarryInformation instance searched
     * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database.
     </en>*/
    private CarryInfo getArrivalInfo(String stnum, Pallet plt)
            throws ReadWriteException
    {

        CarryInfo[] cinfos = null;
        CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
        // 検索条件セット
        // 搬送元ステーション
        ciKey.setSourceStationNo(stnum);
        // 搬送状態：到着
        ciKey.setCmdStatus(CarryInfo.CMD_STATUS_ARRIVAL);

        // BCDATAがセットされていれば、PALLETIDを取得し検索条件に追加する。
        if (!StringUtil.isBlank(plt.getBcrData()))
        {
            Pallet[] pal = null;
            PalletSearchKey palKey = new PalletSearchKey();

            // PALLETテーブルの検索条件にBCDATAをセット
            palKey.setBcrData(plt.getBcrData());

            PalletHandler palh = new PalletHandler(getConnection());
            if (palh.count(palKey) > 0)
            {
                pal = (Pallet[])palh.find(palKey);
            }

            if (pal != null)
            {
                // PALLETIDが取得できれば、検索条件に追加する。
                // パレットID
                ciKey.setPalletId(pal[0].getPalletId());
            }
            else
            {
                // 検索結果が0件だった場合は、新規入庫として扱う
                return null;
            }
        }

        // 検索順セット
        // 到着日時
        ciKey.setArrivalDateOrder(true);

        CarryInfoHandler cih = new CarryInfoHandler(getConnection());
        if (cih.count(ciKey) > 0)
        {
            cinfos = (CarryInfo[])cih.find(ciKey);
        }

        // 到着データの場合一インスタンスで良いです。
        if (cinfos != null)
        {
            return cinfos[0];
        }
        else
        {
            return null;
        }
    }
}
//end of class

