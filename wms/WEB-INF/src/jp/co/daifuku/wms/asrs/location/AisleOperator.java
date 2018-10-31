// $Id: AisleOperator.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.AisleAlterKey;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.BankSelectHandler;
import jp.co.daifuku.wms.base.dbhandler.BankSelectSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.BankSelect;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.handler.Entity;

/**<jp>
 * アイル情報に対する操作を行う処理を集めたクラスです。<BR> 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/22</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
public class AisleOperator
        extends Object
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    /**<jp>
     * データベース接続用のコネクション
     </jp>*/
    /**<en>
     * Connection to connect with database
     </en>*/
    private Connection _conn = null;

    /**
     * Aisleインスタンス
     */
    private Aisle _aisle = null;

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    /**<jp>
     * 指定された棚Noの範囲内に含まれるアイル内ステーションNoの一覧を返します。
     * @param  conn     データベース接続用 Connection
     * @param  frShfNum 先頭棚No
     * @param  toShfNum 最終棚No
     * @return アイルステーションNoの一覧
     * @throws ReadWriteException データベースへのアクセスで異常が発生した場合に通知します。
     * @throws IllegalArgumentException 指定された棚Noが定義されていない場合に通知します。
     * @throws IllegalArgumentException 指定された二つの棚Noを管理する倉庫が異なる場合に通知します。
     * @throws IllegalArgumentException 指定されたバンクの範囲が不正な場合に通知します。
     </jp>*/
    /**<en>
     * Return the list of station no. of Aisles included in location numbers of specified range.
     * @param  conn     :Connection with database
     * @param  frShfNum :leading location no.
     * @param  toShfNum :last location no.
     * @return :list of aisle station no.
     * @throws ReadWriteException :Notifies if error occured when accessing database.
     * @throws IllegalArgumentException :Notifies if specified location no. was undefined.
     * @throws IllegalArgumentException :Notifies if warehouse which controls 2 location no. specified is different.
     * @throws IllegalArgumentException :Notifies if invalid bank range has been specified.
     </en>*/
    public static String[] getAisleStationNos(Connection conn, String frShfNum, String toShfNum)
            throws ReadWriteException,
                IllegalArgumentException
    {
        int startBank = 0;
        int endBank = 0;
        String whnum = null;

        //<jp> 開始棚Noより終了棚Noが小さい場合は例外を返す。</jp>
        //<en> Returns exception if leading location no. is lower than the last location no.</en>
        if (frShfNum.compareTo(toShfNum) > 0)
        {
            //<jp> 6022020=指定された棚No.の範囲に誤りがあります。</jp>
            //<en> 6022020=Error with the specified range of location.</en>
            RmiMsgLogClient.write(6022020, LogMessage.F_NOTICE, "AisleOperator", null);
            throw new IllegalArgumentException("6022020");
        }

        try
        {
            //<jp> 開始棚NoよりバンクNo取得</jp>
            //<en> Getting bank no. based on the leafing location no.</en>
            Station fst = StationFactory.makeStation(conn, frShfNum);
            if (fst instanceof Shelf)
            {
                Shelf frshf = (Shelf)fst;
                startBank = frshf.getBankNo();
                whnum = frshf.getWhStationNo();
            }
            else
            {
                //<jp> 棚以外のステーションNoが指定された場合は例外を投げる</jp>
                //<en> Throws exception if station no. other than locations is specified.</en>
                //<jp> 6026047=指定された棚No.の情報は存在しません。棚No.={0}</jp>
                //<en> 6026047=Data of specified location no. does not exist. Location No.={0}</en>
                Object[] tObj = new Object[1];
                tObj[0] = frShfNum;
                RmiMsgLogClient.write(6026047, LogMessage.F_ERROR, "AisleOperator", tObj);
                throw new IllegalArgumentException(WmsMessageFormatter.format(6026047, tObj));
            }

            //<jp> 終了棚NoよりバンクNo取得</jp>
            //<en> Getting bank no. based on the last locatio no.</en>
            Station tst = StationFactory.makeStation(conn, toShfNum);
            if (tst instanceof Shelf)
            {
                Shelf toshf = (Shelf)tst;
                endBank = toshf.getBankNo();
                if (!whnum.equals(toshf.getWhStationNo()))
                {
                    //<jp> 同一倉庫内の棚範囲でない場合は例外を返す。</jp>
                    //<en> Throws exception if the range of locations surpassed  one warehouse.</en>
                    //<jp> 6022020=指定された棚No.の範囲に誤りがあります。</jp>
                    //<en> 6022020=Error with the specified range of location.</en>
                    RmiMsgLogClient.write(6022020, LogMessage.F_NOTICE, "AisleOperator", null);
                    throw new IllegalArgumentException("6022020");
                }
            }
            else
            {
                //<jp> 棚以外のステーションNoが指定された場合は例外を投げる</jp>
                //<en> Throws exception if station no. other than locations is specified.</en>
                //<jp> 6026047=指定された棚No.の情報は存在しません。棚No.={0}</jp>
                //<en> 6026047=Data of specified location no. does not exist. Location No.={0}</en>
                Object[] tObj = new Object[1];
                tObj[0] = frShfNum;
                RmiMsgLogClient.write(6026047, LogMessage.F_ERROR, "AisleOperator", tObj);
                throw new IllegalArgumentException(WmsMessageFormatter.format(6026047, tObj));
            }
        }
        catch (NotFoundException e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
        catch (InvalidDefineException e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }

        return getAisleStationNos(conn, whnum, startBank, endBank);
    }

    /**<jp>
     * 指定された倉庫、開始バンク、終了バンクの範囲内に含まれるアイルステーションNoの一覧を返します。
     * @param conn   データベース接続用 Connection
     * @param whnum  倉庫No
     * @param frBank 開始バンクNo
     * @param toBank 終了バンクNo
     * @return アイルステーションNoの一覧
     * @throws ReadWriteException データベースへのアクセスで異常が発生した場合に通知します。
     * @throws IllegalArgumentException 指定された倉庫Noと棚Noの範囲で定義が存在しない場合に通知します。
     </jp>*/
    /**<en>
     * Returns a list of aisle station no. included in the range of warehouse set, between leading bank and the last bank.
     * @param conn   :Connection to connect with database
     * @param whStno  :warehouse station no.
     * @param frBank :leading bank no.
     * @param toBank :last bank no.
     * @return :list of aisle station no.
     * @throws ReadWriteException :Notifies if error occured when accessing database.
     * @throws IllegalArgumentException :Notifies if there is no definition in the range of specified warehouse no. and 
     * the location no.
     </en>*/
    public static String[] getAisleStationNos(Connection conn, String whStno, int frBank, int toBank)
            throws ReadWriteException,
                IllegalArgumentException
    {
        String[] strAisleStation = null;

        BankSelectSearchKey bselKey = new BankSelectSearchKey();

        bselKey.setAisleStationNoCollect("DISTINCT");
        bselKey.setWhStationNo(whStno);
        bselKey.setBankNo(frBank, ">=", "(", "", true);
        bselKey.setBankNo(toBank, "<=", "", ")", true);
        bselKey.setAisleStationNoOrder(true);

        BankSelectHandler bselh = new BankSelectHandler(conn);
        BankSelect[] bselArry = (BankSelect[])bselh.find(bselKey);
        if (bselArry != null)
        {
            List<String> aisleList = new ArrayList<String>();
            for (int i = 0; i < bselArry.length; i++)
            {
                aisleList.add(bselArry[i].getAisleStationNo());
            }
            strAisleStation = (String[])aisleList.toArray(new String[0]);
        }
        else
        {
            //<jp> 6026048=アイルの情報が存在しません。 倉庫={0} 開始バンクNo.={0} 終了バンクNo.={1}</jp>
            //<en> 6026048=Aisle data does not exist. Warehouse={0} Start Bank No.={0} End Bank No.={1}</en>
            Object[] tObj = new Object[3];
            tObj[0] = whStno;
            tObj[1] = new Integer(frBank);
            tObj[2] = new Integer(toBank);
            RmiMsgLogClient.write(6026048, LogMessage.F_ERROR, "AisleSelector", tObj);
            throw new IllegalArgumentException(WmsMessageFormatter.format(6026048, tObj));
        }

        return (strAisleStation);
    }

    /**<jp>
     * 指定された倉庫の範囲内に含まれるアイルステーションNoの一覧を返します。
     * @param conn データベース接続用 Connection
     * @param wstnum アイルステーションNo一覧を取得する倉庫ステーションNo
     * @return アイルステーションNoの一覧
     * @throws ReadWriteException データベースへのアクセスで異常が発生した場合に通知します。
     * @throws IllegalArgumentException wstnumに該当するアイルステーションが存在しない場合に通知します。
     </jp>*/
    /**<en>
     * Returns a list of aisle station no. included in the range of warehouse set.
     * specified.
     * @param conn :Connection with database
     * @param whnum :warehouse station no. to retrieve the aisle station no. from
     * @return :list of aisle station no.
     * @throws ReadWriteException :Notifies if error occured when accessing database.
     * @throws IllegalArgumentException :Notifies if no aisle station of such wstnum does not exist.
     </en>*/
    public static String[] getAisleStationNos(Connection conn, String whnum)
            throws ReadWriteException,
                IllegalArgumentException
    {
        String[] strAisleStation = null;

        BankSelectSearchKey bselKey = new BankSelectSearchKey();

        bselKey.setAisleStationNoCollect("DISTINCT");
        bselKey.setWhStationNo(whnum);
        bselKey.setAisleStationNoOrder(true);

        BankSelectHandler bselh = new BankSelectHandler(conn);
        BankSelect[] bselArry = (BankSelect[])bselh.find(bselKey);
        if (bselArry != null)
        {
            List<String> aisleList = new ArrayList<String>();
            for (int i = 0; i < bselArry.length; i++)
            {
                aisleList.add(bselArry[i].getAisleStationNo());
            }

            strAisleStation = (String[])aisleList.toArray(new String[0]);
        }

        return (strAisleStation);
    }

    /**<jp>
     * 指定されたステーションNoがアイルステーションNoとして存在するかチェックします。
     * @param conn データベース接続用 Connection
     * @param stnum ステーションNo
     * @return アイルステーションである場合はtrue、アイルステーションとして存在しない場合はfalse
     * @throws ReadWriteException データベースへのアクセスで異常が発生した場合に通知します。
     * @throws NotFoundException 指定されたステーションNoがアイルステーションでない場合に通知します。
     </jp>*/
    /**<en>
     * Check to see if that station no. specified exists as an aisle station no.
     * @param conn Connection with database
     * @param stnum station no.
     * @return true if it does, false if it does not exist.
     * @throws ReadWriteException :Notifies if error occured when accessing database.
     * @throws NotFoundException :Notifies if the number other than the aisle station no. is specified.
     </en>*/
    public static boolean isAisleStation(Connection conn, String stnum)
            throws ReadWriteException,
                NotFoundException
    {
        //<jp> 指定されたステーションNoでインスタンスを生成する。</jp>
        //<jp> 生成されたステーションがアイルの場合はtrueを返す。</jp>
        //<en> Generates instance according to the specified station no.</en>
        //<en> It returns true if the generated station is the aisle.</en>
        Station st = null;
        try
        {
            st = StationFactory.makeStation(conn, stnum);
        }
        catch (NotFoundException e)
        {
            //<jp> 6026046=指定されたステーションはアイルではありません。 StationNo={0}</jp>
            //<en> 6026046=The specified station is not an aisle. ST No={0}</en>
            Object[] tObj = new Object[1];
            tObj[0] = stnum;
            throw new NotFoundException(WmsMessageFormatter.format(6026046, tObj));
        }
        catch (InvalidDefineException e)
        {
            //<jp> 6026046=指定されたステーションはアイルではありません。 StationNo={0}</jp>
            //<en> 6026046=The specified station is not an aisle. ST No={0}</en>
            Object[] tObj = new Object[1];
            tObj[0] = stnum;
            throw new NotFoundException(WmsMessageFormatter.format(6026046, tObj));
        }

        if (st instanceof Aisle)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // Constructors --------------------------------------------------
    /**
     * 新しい<code>AisleOperator</code>のインスタンスを作成します。
     */
    public AisleOperator()
    {
    }

    /**
     * 新しい<code>AisleOperator</code>のインスタンスを作成します。
     * 引数で渡された、アイルNo.よりaisleインスタンスを生成し保持します。
     * @param conn データベースコネクション
     * @param aisleNo アイルNo.
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     */
    public AisleOperator(Connection conn, String aisleNo)
            throws ReadWriteException
    {
        _conn = conn;
        _aisle = getStation(conn, aisleNo);
    }

    /**
     * データベースからステーション情報を取得しAisleインスタンスを生成します。
     * @param conn データベースのコネクション
     * @param aisleNo アイルNo.
     * @return アイルインスタンス
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     */
    private Aisle getStation(Connection conn, String aisleNo)
            throws ReadWriteException
    {
        AisleSearchKey key = new AisleSearchKey();
        key.setStationNo(aisleNo);
        AisleHandler wAisleHandler = new AisleHandler(conn);
        Entity[] ent = wAisleHandler.find(key);

        return (Aisle)ent[0];
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * 入力されたステーションから搬送ルートにあるアイルが在庫確認中かどうかを判断します。<BR>
     * 入庫時の確認には入庫ステーション、出庫時の確認には出庫ステーション、直行時の確認には
     * 搬送元、 搬送先についてこのメソッドで在庫確認中かどうかを確認してください。<BR>
     * 作業場が指定された場合は、その作業場に属するすべてのステーション
     * をチェックし、一つでも在庫確認中の場合はtrueを返します。
     * <BR>＜処理内容＞<BR>
     * １．Station.getAisleStationNumber()メソッドでAISLESTATIONNUMBERを取得する。<BR>
     * ２．１つでもINVENTORYCHECKFLAGがINVENTORYCHECKの場合は在庫確認中。<BR>
     * @param conn データベース接続用 Connection
     * @param stno 確認を行うステーションNo.
     * @return True：在庫確認中  False：在庫確認中では無い
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     * @throws InvalidDefineException  クラス定義が、正しくなかった場合に通知されます。
     * @throws NotFoundException   該当のクラスが見つからなかった場合に通知されます。
     </jp>*/
    /**<en>
     * Based on the entered station no., it determines whether/not the aisles on the carry route are 
     * in process of invneotry check.<BR>
     * Please confirm about the inventory check, by this method, with storage station if storing, with retrieval 
     * station if retrieving and with both sending  and receiving stations if direct traveing.<BR>
     * In case the workshop has been specified, check all stations that belong to the specified workshop then
     * return true if one or more station in inventory checking process is found.
     * Create a station instance based on the station no., and pass to the check method for empty location.
     * <BR><Process detail><BR>
     * 1.  Get AISLESTATIONNUMBER according to the method: Station.getAisleStationNumber().<BR>
     * 2. If one or more INVENTORYCHECKFLAG is found INVENTORYCHECK, the inventory check is in progress.<BR>
     * @param conn Connection with database
     * @param stno station no. to check.
     * @return True:inventory check is in progress, False:inventory check is not done
     * @throws ReadWriteException  :Notifies of exception as it is that occured in accessing database.
     * @throws InvalidDefineException  :Notifies if definition of the class was incorrect.
     * @throws NotFoundException       :Notifies if corresponding class was not found.
     </en>*/
    public boolean isInventoryCheck(Connection conn, String stno)
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException
    {
        String[] aisleStNo = getAisleStationNo(conn, stno);

        //<jp>取得したアイルで在庫確認中のものを探す</jp>
        //<en>Searches through obtained aisle for the ones in process of inventory checks.</en>
        for (int i = 0; i < aisleStNo.length; i++)
        {
            //<jp>アイルステーションのインスタンスを作成する</jp>
            //<en>Creates an instance of aisle station.</en>
            Station aisleSt = StationFactory.makeStation(conn, aisleStNo[i]);

            //<jp>在庫確認中の場合</jp>
            //<en>If inventory check is in progress,</en>
            if (Station.INVENTORY_CHECK_FLAG_WORKING.equals(aisleSt.getInventoryCheckFlag()))
            {
                //<jp> 在庫確認中なので true で返す</jp>
                //<en> inventory check in progress</en>
                return true;
            }
        }
        return false;
    }

    /**<jp>
     * 入力されたステーションから搬送ルートにあるアイルが空棚確認中かどうかを判断します。<BR>
     * 入庫時の確認には搬送元ステーション、出庫時の確認には搬送先ステーション、直行時の確認には
     * 搬送元、 搬送先について空棚確認中かどうかを確認してください。<BR>
     * 作業場が指定された場合は、その作業場に属するすべてのステーション
     * をチェックし、一つでも空棚確認中の場合はtrueを返します。
     * <BR>＜処理内容＞<BR>
     * １．指定されたステーションからアイルステーションNoを取得する。<BR>
     * ２．１つでもINVENTORYCHECKFLAGがEMPTY_LOCATION_CHECKの場合は空棚確認中。<BR>
     * @param conn データベース接続用 Connection
     * @param stno 確認を行うステーションNo.
     * @return true：空棚確認中  false：空棚確認中では無い
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     * @throws InvalidDefineException  クラス定義が、正しくなかった場合に通知されます。
     * @throws NotFoundException   該当のクラスが見つからなかった場合に通知されます。
     </jp>*/
    /**<en>
     * Based on the entered station no., it determines whether/not the aisles on the carry route are
     * in process of empty location check.<BR>
     * Please confirm about the empty location checkes with sending station if storing, with receiving station
     * if retrieving and with both sending and receiving stations if direct traveing.<BR>
     * In case the workshop has been specified, check all stations that belong to the specified workshop then
     * return true if one or more station in empty locations checking process is found.
     * <BR><Process detail><BR>
     * 1.  Get aisle station no. from specified station.<BR>
     * 2.  If one or more INVENTORYCHECKFLAG is found EMPTY_LOCATION_CHECK, the empty location check is<BR>
     *     in progress.<BR>
     * @param conn Connection with database
     * @param stno station no. to check.
     * @return true :empty location check is in progress,  false: empty location check is not done.
     * @throws ReadWriteException :Notifies of exception as it is that occured in accessing database.
     * @throws InvalidDefineException  :Notifies if definition of the class was incorrect.
     * @throws NotFoundException       :Notifies if corresponding class was not found.
     </en>*/
    public boolean isEmptyLocationCheck(Connection conn, String stno)
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException
    {
        String[] aisleStNo = getAisleStationNo(conn, stno);

        //<jp>取得したアイルで空棚確認中のものを探す</jp>
        //<en>Searches through obtained aisle for the ones in process of empty location checks.</en>
        for (int i = 0; i < aisleStNo.length; i++)
        {
            //<jp>アイルステーションのインスタンスを作成する</jp>
            //<en>Creates an instance of aisle station.</en>
            Station aisleSt = StationFactory.makeStation(conn, aisleStNo[i]);

            //<jp>空棚確認中の場合</jp>
            //<en>If empty location check is in progress,</en>
            if (Station.INVENTORY_CHECK_FLAG_WORKING_EMPTY.equals(aisleSt.getInventoryCheckFlag()))
            {
                //<jp> 空棚確認中なので true で返す</jp>
                //<en> Sets the message classification for empty location check in progress.</en>
                return true;
            }
        }
        return false;
    }

    /**<jp>
     * 入力されたステーションからルートのあるアイルステーションを配列にして返します。<BR>
     * 作業場が指定された場合は、その作業場に属するすべてのステーション
     * をチェックし、ルートのあるアイルステーションを返します。<BR>
     * <BR>＜処理内容＞<BR>
     * １．搬送元（先）ステーションナンバーからステーションインスタンスを作成する。<BR><BR>
     * ２．Station.getAisleStationNo()メソッドでAISLESTATIONNOを取得する。<BR><BR>
     * ３-A．AISLESTATIONNOが定義されている場合（アイル独立の場合）<BR>
     *  ・AISLESTATIONNOからアイルステーションインスタンスを作成する。<BR>
     *  ・取得したAISLESTATIONNOを配列に追加する。<BR><BR>
     * ３-B．AISLESTATIONNOが定義されて無い場合（アイル結合の場合）<BR>
     *  ・Station.getWareHouseStationNo()でWHSTATIONNOを取得する。<BR>
     *  ・AisleOperator.getAisleStationNos(Connection conn, String whnum)<BR>
     *  メソッドにより、その倉庫に所属するAISLESTATIONNOを取得。<BR>
     *  ・AISLESTATIONNOからアイルステーションインスタンスを作成する。<BR>
     *  ・取得したAISLESTATIONNOを配列に追加する。<BR>
     * @param conn データベース接続用 Connection
     * @param stno 確認を行うステーションNo.
     * @return 入力されたステーションに属するアイルステーションナンバーの配列。
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     * @throws InvalidDefineException  クラス定義が、正しくなかった場合に通知されます。
     * @throws NotFoundException   該当のクラスが見つからなかった場合に通知されます。
     </jp>*/
    /**<en>
     * Return the aisle station in form of array that has routes to connect with entered station.<BR>
     * If any workplace is specified, check all the stations that belong to that workplace then return
     * the conected aisle station by routes.<BR>
     * <BR><detail of process><BR>
     * 1. Create the station instance based on the sending/receiving station No.<BR><BR>
     * 2. Get the AISLESTATIONNO using Station.getAisleStationNo() method.<BR><BR>
     * 3-A. If AISLESTATIONNO is defined (stand alone system),<BR>
     *  - Create the aisle station instance based on the AISLESTATIONNO.<BR>
     *  - Add the acquired AISLESTATIONNO to the array.<BR><BR>
     * 3-B  If AISLESTATIONNO is not defined (connected aisle system),<BR>
     *  - Get WHSTATIONNO using Station.getAisleStationNo().<BR>
     *  - Get the AISLESTATIONNO that belog to that warehouse by using 
     *     AisleSelector.getAisleStationNos(Connection conn, String whnum) method.<BR>
     *  - Create the aisle station instance based on the AISLESTATIONNO.<BR>
     *  - Add the acquired AISLESTATIONNO to the array.<BR>
     * @param conn Connection with database
     * @param stno station no. to check.
     * @return :array of aisle station No. that belong to the entered station
     * @throws ReadWriteException :Notify the exception as it is that occurred in connection with database.
     * @throws InvalidDefineException  :Notify if definition of the class is incorrect.
     * @throws NotFoundException   :Notify if corresponding class cannot be found.
     </en>*/
    public String[] getAisleStationNo(Connection conn, String stno)
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException
    {
        //<jp>ステーションインスタンスを作成する</jp>
        //<en>Create the station instance.</en>
        Station st = StationFactory.makeStation(conn, stno);

        String aisleStNo = null;
        List<String> aisleList = new ArrayList<String>();
        Hashtable hst = new Hashtable();
        String[] aisleNumArray = null;
        try
        {
            //<jp>作業場の場合</jp>
            //<en>For workplace</en>
            if (st instanceof WorkPlace)
            {
                //<jp>WorkPlaceへキャスト</jp>
                //<en>Cast to WorkPlace</en>
                WorkPlace wPlace = (WorkPlace)st;

                //<jp>作業上に属するステーションを取得</jp>
                //<en>Get the stations that belong to the workplace.</en>
                String[] stNumbers = wPlace.getWPStations();

                //<jp> そのステーションが属するアイルステーション番号を取得</jp>
                //<en> Get the aisle station No. that the station belongs to.</en>
                for (int i = 0; i < stNumbers.length; i++)
                {
                    String[] aisle = getAisleStationNo(conn, stNumbers[i]);
                    for (int j = 0; j < aisle.length; j++)
                    {
                        //<jp> 取得したアイルNo.を配列に追加していく</jp>
                        //<en> Add the acquired aisle No. to the array. (repeat as many as existing data)</en>
                        if (!hst.containsKey(aisle[j]))
                        {
                            aisleList.add(aisle[j]);
                            hst.put(aisle[j], "");
                        }
                    }
                }
            }
            //<jp> 作業場以外の場合</jp>
            //<en> Other than workplace</en>
            else
            {
                //<jp> そのステーションが属するアイルステーション番号を取得</jp>
                //<en> Get the aisle station No. that the station belongs to.</en>
                aisleStNo = st.getAisleStationNo();

                //<jp> アイルステーション番号が定義されていない場合（アイル結合の場合）</jp>
                //<en> If the aisle station No. is not in defined (due to aisle connected station)</en>
                if (StringUtil.isBlank(aisleStNo))
                {
                    //<jp> 倉庫番号を取得する</jp>
                    //<en> Get the warehouse No.</en>
                    String whStNo = st.getWhStationNo();

                    //<jp> 倉庫ステーションNo.から所属するアイルステーションNo.の一覧を生成</jp>
                    //<en> Generate the list of aisle station No. of warehouse using the warehouse No.</en>
                    String[] aisleStNumbers = getAisleStationNos(conn, whStNo);

                    //<jp> 取得したアイルNo.を配列に追加していく</jp>
                    //<en> Add each of retrieved aisle No. to the array.</en>
                    for (int i = 0; i < aisleStNumbers.length; i++)
                    {
                        if (!hst.containsKey(aisleStNumbers[i]))
                        {
                            aisleList.add(aisleStNumbers[i]);
                            hst.put(aisleStNumbers[i], "");
                        }
                    }
                }
                //<jp> アイルステーション番号が定義されている場合（アイル独立の場合）</jp>
                //<en> If the aisle station No. is defined (stand alone station)</en>
                else
                {
                    //<jp> 取得したアイルNo.を配列に追加していく</jp>
                    //<en> Add each of retrieved aisle No. to the array.</en>
                    if (!hst.containsKey(aisleStNo))
                    {
                        aisleList.add(aisleStNo);
                        hst.put(aisleStNo, "");
                    }
                }
            }
        }
        finally
        {
            aisleNumArray = (String[])aisleList.toArray(new String[0]);
        }
        return aisleNumArray;
    }

    /**<jp>
     * アイルの状態を変更します。ハンドラを使用してデータベースを更新します。
     * @param  sts 変更する状態
     * @throws InvalidStatusException stsの内容が範囲外であった場合に通知します。
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データベースへのアクセスで異常が発生した場合に通知します。
     * @throws NotFoundException      このステーションがデータベースに見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Modifies the status of aisle. It updates the database using handler.
     * @param  sts :status to be modified
     * @throws InvalidStatusException :Notifies if the contents of sts is outside the scope.
     * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
     * @throws ReadWriteException     :Notifies if error occured when accessing database.
     * @throws NotFoundException      :Notifies if this station was not found in database.
     </en>*/
    public void alterStatus(String sts)
            throws InvalidStatusException,
                InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        AisleAlterKey ak = new AisleAlterKey();
        ak.setStationNo(_aisle.getStationNo());
        ak.updateStatus(sts);
        AisleHandler ah = new AisleHandler(_conn);
        ah.modify(ak);
    }

    /**<jp>
     * ステーションの在庫確認状態を変更します。ハンドラを使用してデータベースを更新します。
     * @param  flg 在庫確認フラグ
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データベースへのアクセスで異常が発生した場合に通知します。
     * @throws NotFoundException      このステーションがデータベースに見つからない場合に通知します。
     * @throws InvalidStatusException flgの内容が範囲外であった場合に通知します。
     </jp>*/
    /**<en>
     * Modifies teh state of inventory check of the station. It updates database using handler.
     * @param  flg :flag for inventory check
     * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
     * @throws ReadWriteException     :Notifies if error occured when accessing database.
     * @throws NotFoundException      :Notifies if this station was not found in database.
     * @throws InvalidStatusException :Notifies if contents of flg is outside the scope.
     </en>*/
    public void alterInventoryCheckFlag(String flg)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException,
                InvalidStatusException
    {
        AisleAlterKey ak = new AisleAlterKey();
        ak.setStationNo(_aisle.getStationNo());
        ak.updateInventoryCheckFlag(flg);
        AisleHandler ah = new AisleHandler(_conn);
        ah.modify(ak);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

