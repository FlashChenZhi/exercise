//$Id: ShelfOperator.java 7770 2010-03-31 07:12:25Z ota $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.dbhandler.DoubleDeepShelfHandler;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfFinder;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.handler.Entity;

/**<jp>
 * 棚情報に対する操作を行う処理を集めたクラスです。<BR> 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7770 $, $Date: 2010-03-31 16:12:25 +0900 (水, 31 3 2010) $
 * @author  $Author: ota $
 </jp>*/
public class ShelfOperator
        extends Object
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * データベース接続用のConnectionインスタンス。
     * トランザクション管理は、このクラスの中では行わない。
     </jp>*/
    /**<en>
     * Connection instance to connect with database
     * Transaction control is not conducted in this class.
     </en>*/
    private Connection _conn;

    /**
     * Shelfインスタンス
     */
    private Shelf _shelf = null;

    // Constructors --------------------------------------------------
    /**
     * 新しい<code>ShelfOperator</code>のインスタンスを作成します。
     */
    public ShelfOperator()
    {
    }

    /**
     * 新しい<code>ShelfOperator</code>のインスタンスを作成します。
     * 引数で渡された、データベースコネクションよりShelfインスタンスを生成し保持します。
     * @param conn データベースコネクション
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     */
    public ShelfOperator(Connection conn) throws ReadWriteException
    {
        _conn = conn;
    }

    /**
     * 新しい<code>ShelfOperator</code>のインスタンスを作成します。
     * 引数で渡された、データベースコネクション、ステーションNo.よりShelfインスタンスを生成し保持します。
     * @param conn データベースコネクション
     * @param stno ステーションNo.
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     */
    public ShelfOperator(Connection conn, String stno) throws ReadWriteException
    {
        _conn = conn;
        _shelf = getStation(conn, stno);
    }

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
        return ("$Revision: 7770 $,$Date: 2010-03-31 16:12:25 +0900 (水, 31 3 2010) $");
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * 空棚検索を行います。
     * 指定されたアイル、ゾーンをもとにShelfテーブルを検索し、空棚となっているShelfインスタンスを一つ返します。
     * 空棚が見つからない場合、nullを返します。
     * このメソッドはShelfテーブルをトランザクションが完了するまでロックするので、呼び出し元は必ずトランザクションをcommitまたはrollbackしてください。
     * @param tAisle 空棚検索対象アイル
     * @param tZone  空棚検索対象ゾーン
     * @param blnCCarry 空棚検索条件処理判定 true:条件処理する false:条件処理しない
     * @param listSPossible 空棚数アイル一覧データ
     * @param empLocDeterm  空棚を決定し更新処理を行なう場合はtrue、チェックのみの場合はfalse
     * @param exclusionBay 検索除外ベイ
     * @return 検索したShelfインスタンス
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws InvalidDefineException ShelfSelecotorインターフェースに定義されていない空棚検索方向が指定された場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Search the empty location.
     * According to the specified aisle and zone, search in the Shlf table. Return one Shlf instance which is an
     * empty location.
     * Or return null if no empty location was found.
     * Please remember, the caller must commit or rollback the transaction, as this method locks the Shelf table
     * until the transaction should complete.
     * @param tAisle :aisle subject to empty location search
     * @param tZone  :zone subject to empty location search
     * @param blnCCarry :shelf check true or false
     * @param listSPossible :shelf possible location area
     * @param empLocDeterm     :true if a empty location has been selected as destination and renewing the shelf information, or false
     *                     if only the check will be done.
     * @param exclusionBay 検索除外ベイ
     * @return : Shelf instance searched
     * @throws ReadWriteException :Notifies of the exception occured during the database processing.
     * @throws InvalidDefineException ShelfSelecotor :Notifies if interface-undefined direction of empty location 
     * search has been selected.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    // 2009/09/26 Y.Osawa UPD ST
    public Shelf findEmptyShelf(Aisle tAisle, Zone[] tZone, Boolean blnCCarry, ArrayList listSPossible,
            boolean empLocDeterm, int[] exclusionBay)
    // 2009/09/26 Y.Osawa UPD ED
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        Shelf shelf = null;

        for (int i = 0; i < tZone.length; i++)
        {
            // 空棚検索条件処理フラグがtrueの場合、空棚検索する前に事前条件処理を行います
            // 以下のチェックにて対象アイルステーション・ゾーンで入庫可能数が1以上の場合
            // それ以降の空棚検索処理を行います
            if (blnCCarry == true)
            {
                // 空棚検索事前チェックを行う
                if (!checkEmptyShelf(tAisle, tZone[i], listSPossible))  
                {
                    continue;
                }
            }

            //　決定したゾーンで空棚検索を行う
            // 2009/09/26 Y.Osawa UPD ST
            shelf = findEmptyShelf(tAisle, tZone[i], empLocDeterm, exclusionBay);
            // 2009/09/26 Y.Osawa UPD ED
            if (shelf != null)
            {
                break;
            }
        }

        return shelf;
    }

    /**<jp>
     * 空棚検索を行います。
     * 指定されたアイル、ゾーンをもとにShelfテーブルを検索し、空棚となっているShelfインスタンスを一つ返します。
     * 空棚が見つからない場合、nullを返します。
     * このメソッドはShelfテーブルをトランザクションが完了するまでロックするので、呼び出し元は必ずトランザクションをcommitまたはrollbackしてください。
     * @param tAisle 空棚検索対象アイル
     * @param tZone  空棚検索対象ゾーン
     * @param exclusionBay 検索除外ベイ
     * @return 検索したShelfインスタンス
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws InvalidDefineException ShelfSelecotorインターフェースに定義されていない空棚検索方向が指定された場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Search the empty location.
     * According to the specified aisle and zone, search in the Shlf table. Return one Shlf instance which is an
     * empty location.
     * Or return null if no empty location was found.
     * Please remember, the caller must commit or rollback the transaction, as this method locks the Shelf table
     * until the transaction should complete.
     * @param tAisle :aisle subject to empty location search
     * @param tZone  :zone subject to empty location search
     * @param exclusionBay 検索除外ベイ
     * @return : Shelf instance searched
     * @throws ReadWriteException :Notifies of the exception occured during the database processing.
     * @throws InvalidDefineException ShelfSelecotor :Notifies if interface-undefined direction of empty location 
     * search has been selected.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    // 2009/09/26 Y.Osawa UPD ST
    public Shelf findEmptyShelf(Aisle tAisle, Zone[] tZone, int[] exclusionBay)
    // 2009/09/26 Y.Osawa UPD ED
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        // 2009/09/26 Y.Osawa UPD ST
        return findEmptyShelf(tAisle, tZone, false, null, true, exclusionBay);
        // 2009/09/26 Y.Osawa UPD ED
    }

    /**<jp>
     * 指定されたゾーンの空棚検索を行います。
     * 指定されたアイル、ゾーンをもとにShelfテーブルを検索し、空棚となっているShelfインスタンスを一つ返します。
     * 空棚が見つからない場合、nullを返します。
     * このメソッドはShelfテーブルをトランザクションが完了するまでロックするので、呼び出し元は必ずトランザクションをcommitまたはrollbackしてください。
     * @param tAisle 空棚検索対象アイル
     * @param tZone  空棚検索対象ゾーン
     * @param empLocDeterm  空棚を決定し更新処理を行なう場合はtrue、チェックのみの場合はfalse
     * @param exclusionBay 検索除外ベイ
     * @return 検索したShelfインスタンス
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws InvalidDefineException ShelfSelecotorインターフェースに定義されていない空棚検索方向が指定された場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    // 2009/09/26 Y.Osawa UPD ST
    protected Shelf findEmptyShelf(Aisle tAisle, Zone tZone, boolean empLocDeterm, int[] exclusionBay)
    // 2009/09/26 Y.Osawa UPD ED
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        ShelfSearchKey shelfKey = new ShelfSearchKey();
//        ShelfHandler shelfHdl = new ShelfHandler(_conn);
        
        ShelfFinder shelfFdr = new ShelfFinder(_conn);     
        
        Shelf shelf = null;
        String strDirection = "";

        shelfKey.clear();
        shelfKey.setParentStationNo(tAisle.getStationNo());
        shelfKey.setHardZoneId(tZone.getHardZone().getHardZoneId());
        shelfKey.setSoftZoneId(tZone.getSoftZoneID());
        shelfKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
        shelfKey.setProhibitionFlag(Shelf.PROHIBITION_FLAG_OK);
        shelfKey.setAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);

        // 2009/09/26 Y.Osawa ADD ST
        // 検索除外ベイが指定されている時
        if(!ArrayUtil.isEmpty(exclusionBay))
        {
            for(int bay : exclusionBay)
            {
                shelfKey.setBayNo(bay, "!=");
            }
        }
        // 2009/09/26 Y.Osawa ADD ED

        strDirection = String.valueOf(tZone.getDirection());
        if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_HP.equals(strDirection))
        {
            // レベル方向検索(HP側から):HP手前
            // LEVEL, BAY, BANK
            shelfKey.setBayNoOrder(true);
            shelfKey.setLevelNoOrder(true);
            shelfKey.setBankNoOrder(true);
        }
        else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_HP.equals(strDirection))
        {
            // ベイ方向検索(HP側から):HP下段
            // BAY, LEVEL, BANK
            shelfKey.setLevelNoOrder(true);
            shelfKey.setBayNoOrder(true);
            shelfKey.setBankNoOrder(true);
        }
        else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_OP.equals(strDirection))
        {
            // レベル方向検索(OP側から):OP手前
            // LEVEL, BAY DESC, BANK
            shelfKey.setBayNoOrder(false);
            shelfKey.setLevelNoOrder(true);
            shelfKey.setBankNoOrder(true);
        }
        else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_OP.equals(strDirection))
        {
            // ベイ方向検索(OP側から):OP下段
            // BAY DESC, LEVEL, BANK
            shelfKey.setLevelNoOrder(true);
            shelfKey.setBayNoOrder(false);
            shelfKey.setBankNoOrder(true);
        }
        else
        {
            //<jp> 空棚検索方向が定義外の場合、例外を返す。</jp>
            //<en>undifined , throw exception!</en>
            Object[] tObj = new Object[3];
            tObj[0] = this.getClass().getName();
            tObj[1] = "wDirection";
            tObj[2] = String.valueOf(tZone.getDirection());
            String classname = (String)tObj[0];
            //<jp> 6016061=範囲外の値を指定されました。セットできません。Class={0} Variable={1} Value={2}</jp>
            //<en> 6016061=Specified value is out of range. Cannot set. Class={0} Variable={1} Value={2}</en>
            RmiMsgLogClient.write(6016061, LogMessage.F_ERROR, classname, tObj);
            throw new InvalidDefineException(WmsMessageFormatter.format(6016061, tObj));
        }

        Shelf[] shelfArry = null;
        try
        {
            shelfFdr.open(true);
        
            // 更新の場合はロックを行う
            if (empLocDeterm)
            {
                shelfFdr.searchForUpdate(shelfKey, ShelfHandler.WAIT_SEC_UNLIMITED);
            }
            else
            {
                shelfFdr.search(shelfKey, ShelfHandler.WAIT_SEC_UNLIMITED);
            }
            while (shelfFdr.hasNext())
            {
                // 先頭の1件のみ必要なため1件のみ取得する。
                shelfArry = (Shelf[])shelfFdr.getEntities(1);
                shelf = shelfArry[0];
                break;
            }
        }
        finally
        {
            if (shelfFdr != null)
            {
                shelfFdr.close();
            }
        }
//        // 更新の場合はロックを行う
//        if (empLocDeterm)
//        {
//            shelfArry = (Shelf[])shelfHdl.findForUpdate(shelfKey, ShelfHandler.WAIT_SEC_UNLIMITED);
//        }
//        else
//        {
//            shelfArry = (Shelf[])shelfHdl.find(shelfKey, ShelfHandler.WAIT_SEC_UNLIMITED);
//        }
//
//        if (shelfArry.length > 0)
//        {
//            shelf = shelfArry[0];
//        }

        return shelf;
    }

    /**<jp>
     * 空棚検索する前の事前条件チェック処理を行います。
     * 対象アイルステーション・ゾーンで入庫可能数が存在するかどうかチェックします
     * @param tAisle 空棚検索対象アイル
     * @param tZone  空棚検索対象ゾーン
     * @param listSPossible 空棚数アイル一覧データ
     * @return チェックOKであればtrue, それ以外はfalseを返します。
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws InvalidDefineException ShelfSelecotorインターフェースに定義されていない空棚検索方向が指定された場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    protected boolean checkEmptyShelf(Aisle tAisle, Zone tZone, ArrayList listSPossible)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        // 空棚検索条件チェックOK/NGフラグ ture:OK/false:NG
        Boolean blnEmptShelfChkFlag = true;

        // 空棚数アイルデータ一覧をデータ分、検索します
        for (int intPCount = 0; intPCount < listSPossible.size(); intPCount++)
        {
            // 空棚数アイル一覧データを1行取得
            String strPossible = String.valueOf(listSPossible.get(intPCount));

            // ステーション情報の先頭位置を取得
            int intPoStationStart = strPossible.indexOf("{");
            intPoStationStart = intPoStationStart + 1;
            // ステーション情報の終了位置を取得
            int intPoStationEnd = strPossible.indexOf("}");
            // ソフトゾーン情報の先頭位置を取得
            int intPoSoftZoneStart = strPossible.indexOf("[");
            intPoSoftZoneStart = intPoSoftZoneStart + 1;
            // ソフトゾーン情報の終了位置を取得
            int intPoSoftZoneEnd = strPossible.indexOf("]");
            // ハードゾーン情報の先頭位置を取得
            int intPoHardZoneStart = strPossible.indexOf("<");
            intPoHardZoneStart = intPoHardZoneStart + 1;
            // ハードゾーン情報の終了位置を取得
            int intPoHardZoneEnd = strPossible.indexOf(">");
            // 入庫格納数情報の開始位置を取得
            int intPoQtyStart = strPossible.indexOf("(");
            intPoQtyStart = intPoQtyStart + 1;
            // 入庫格納数情報の終了位置を取得
            int intPoQtyEnd = strPossible.indexOf(")");

            // 搬送中データテーブルの「ステーション・ソフトゾーンNo・ハードゾーンNo」が一致する行を検索し
            // 搬送数分の減算を行います
            // ＜一致条件＞
            // 検索アイルステーションと一致
            // 且つ、検索ゾーンと一致
            if ((strPossible.substring(intPoStationStart, intPoStationEnd).indexOf(tAisle.getStationNo()) >= 0)
                    && (strPossible.substring(intPoSoftZoneStart, intPoSoftZoneEnd).indexOf(tZone.getSoftZoneID()) >= 0)
                    && (strPossible.substring(intPoHardZoneStart, intPoHardZoneEnd).indexOf(
                            tZone.getHardZone().getHardZoneId()) >= 0))
            {
                // 検索されたアイルステーション、ゾーンに一致する空棚数（入庫可能数）を取得する
                int intSubtractionQty = Integer.valueOf(strPossible.substring(intPoQtyStart, intPoQtyEnd));

                // 検索中に1回でも0件データが取得された場合は
                // 今回アイルステーション、ゾーンはNGとして事前チェックを終了し
                // 空棚検索も次ゾーン処理へ移行する
                if (intSubtractionQty <= 0)
                {
                    blnEmptShelfChkFlag = false;
                    break;
                }
            }
        }

        return blnEmptShelfChkFlag;
    }

    /**<jp>
     * 指定された倉庫Noと棚Noの範囲より空棚確認可能な空棚を検索します。
     * 読み込んだデータをロックするので、呼び出し元は必ずトランザクションの確定または取消しを行なってください。
     * @param whnum 倉庫ステーションNo
     * @param frloc 開始ロケーションNo
     * @param toloc 終了ロケーションNo
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Search the empty location available for empty location check within the specified range of warehouse numbers 
     * and location numbers.
     * Please remember, the caller must either confirm or cancel the transaction since the loaded will be locked.
     * @param whnum warehouse station no.
     * @param frloc :location no. starting from
     * @param toloc :location no. ending 
     * @return :object array created
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    public Entity[] allocateEmptyLocation(String whnum, String frloc, String toloc)
            throws ReadWriteException,
                LockTimeOutException
    {
        ShelfSearchKey shelfKey = new ShelfSearchKey();
        ShelfHandler shelfHdl = new ShelfHandler(_conn);

        shelfKey.setWhStationNo(whnum);
        if (StringUtil.isBlank(frloc) == false)
        {
            shelfKey.setStationNo(frloc, ">=");
        }
        if (StringUtil.isBlank(toloc) == false)
        {
            shelfKey.setStationNo(toloc, "<= ");
        }
        shelfKey.setAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);
        shelfKey.setProhibitionFlag(Shelf.PROHIBITION_FLAG_OK);
        shelfKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
        shelfKey.setBankNoOrder(true);
        shelfKey.setBayNoOrder(true);
        shelfKey.setLevelNoOrder(true);
        shelfKey.setAddressNoOrder(true);

        return shelfHdl.findForUpdate(shelfKey, ShelfHandler.WAIT_SEC_UNLIMITED);
    }

    /**<jp>
     * 指定された倉庫Noと棚Noの範囲およびアイルステーションNoより空棚確認可能な空棚を検索します。
     * 読み込んだデータをロックするので、呼び出し元は必ずトランザクションの確定または取消しを行なってください。
     * @param whnum 倉庫ステーションNo
     * @param frloc 開始ロケーションNo
     * @param toloc 終了ロケーションNo
     * @param aisle アイルステーションNo
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Search the empty location available for empty location check within the specified range of warehouse numbers 
     * and location numbers adn according to the aisle station no..
     * Please remember, the caller must either confirm or cancel the transaction since the loaded will be locked.
     * @param whnum :warehouse station no.
     * @param frloc :location no. starting from
     * @param toloc : location no. ending
     * @param aisle :aisle station no.
     * @return :object array created
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    public Entity[] allocateEmptyLocation(String whnum, String frloc, String toloc, String aisle)
            throws ReadWriteException,
                LockTimeOutException
    {
        ShelfSearchKey shelfKey = new ShelfSearchKey();
        ShelfHandler shelfHdl = new ShelfHandler(_conn);

        shelfKey.setWhStationNo(whnum);
        if (StringUtil.isBlank(frloc) == false)
        {
            shelfKey.setStationNo(frloc, ">=");
        }
        if (StringUtil.isBlank(toloc) == false)
        {
            shelfKey.setStationNo(toloc, "<= ");
        }
        shelfKey.setAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);
        shelfKey.setProhibitionFlag(Shelf.PROHIBITION_FLAG_OK);
        shelfKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
        shelfKey.setParentStationNo(aisle);
        shelfKey.setBankNoOrder(true);
        shelfKey.setBayNoOrder(true);
        shelfKey.setLevelNoOrder(true);
        shelfKey.setAddressNoOrder(true);

        return shelfHdl.findForUpdate(shelfKey, ShelfHandler.WAIT_SEC_UNLIMITED);
    }

    /**<jp>
     * 倉庫を指定してハードゾーンのZONEID一覧を返します。
     * <CODE>Statement</CODE>はwStatementを使用しカーソルオープンしています。<BR>
     * 一時的にwStatementを使用しているだけで、いずれこのメソッド内でカーソルを生成するように変更します。<BR>
     * @param whstno   倉庫ステーションNo
     * @return 指定された条件のゾーンIDのint配列
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Return a list of Zone IDs in hard zone by specifying warehouse.
     * <CODE>Statement</CODE> currently uses wStatement to open cursors.<BR>
     * wStatement is used as a temporary measure; in future this process will be changed so that cursors can
     * be created within this method.*
     * @param whstno   warehouse station no.
     * @return int array of Zone IDs under specified conditions
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     </en>*/
    public String[] getHardZoneIDArray(String whstno)
            throws ReadWriteException
    {
        ShelfSearchKey shelfKey = new ShelfSearchKey();
        ShelfHandler shelfHdl = new ShelfHandler(_conn);

        shelfKey.setHardZoneIdCollect();
        shelfKey.setWhStationNo(whstno);
        shelfKey.setHardZoneIdGroup();
        shelfKey.setHardZoneIdOrder(true);

        Shelf[] shelfArry = (Shelf[])shelfHdl.find(shelfKey);

        String[] zoneIDArray = new String[shelfArry.length];
        for (int i = 0; i < zoneIDArray.length; i++)
        {
            zoneIDArray[i] = shelfArry[i].getHardZoneId();
        }

        return (zoneIDArray);
    }

    /**<jp>
     * 棚の状態（棚の使用不可情報）を変更します。ハンドラを使用してデータベースを更新します。
     * @param sts 状態
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      このステーションがデータベースに見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Alter the shelf status(location availability). Update database by using handler.
     * @param sts :status
     * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
     * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
     * @throws NotFoundException      :Notifies if there is no such station in database. 
     </en>*/
    public void alterStatus(String sts)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        ShelfAlterKey sk = new ShelfAlterKey();
        ShelfHandler hdl = new ShelfHandler(_conn);

        sk.setStationNo(_shelf.getStationNo());
        sk.updateStatusFlag(sts);
        hdl.modify(sk);
    }

    /**<jp>
     * 棚の棚状態を変更します。ハンドラを使用してデータベースを更新します。
     * @param pre 棚状態
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      このステーションがデータベースに見つからない場合に通知します。
     </jp>*/
    /**<en>
     * Alter the shelf status. Update database by using handler.
     * @param pre :status of the shelf
     * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
     * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
     * @throws NotFoundException      :Notifies if there is no such station in database. 
     </en>*/
    public void alterPresence(String pre)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        ShelfAlterKey sk = new ShelfAlterKey();
        ShelfHandler hdl = new ShelfHandler(_conn);

        sk.setStationNo(_shelf.getStationNo());
        sk.updateStatusFlag(pre);
        hdl.modify(sk);
    }

    /**<jp>
     * 棚のアクセス不可棚フラグを変更します。ハンドラを使用してデータベースを更新します。
     * @param acc アクセス不可棚フラグ
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      このステーションがデータベースに見つからない場合に通知します。
     </jp>*/
    /**<en>
     *Alter the locaiton access flag of the shelf. Update database by using handler.
     * @param acc :inaccesible location flag
     * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
     * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
     * @throws NotFoundException      :Notifies if there is no such station in database. 
     </en>*/
    public void alterAccessNgFlag(String acc)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        ShelfAlterKey sk = new ShelfAlterKey();
        ShelfHandler hdl = new ShelfHandler(_conn);

        sk.setStationNo(_shelf.getStationNo());
        sk.updateAccessNgFlag(acc);
        hdl.modify(sk);
    }

    /**
     * ダブルディープのペア空棚数を取得します。
     * 引数で渡された倉庫の棚が属するゾーンに有るペアの空棚数を返す。
     * @param whNo 倉庫ステーションNo
     * @param shelfNo 棚No（ShelfのステーションNo）
     * @return ペアの空棚数
     * @throws InvalidDefineException 該当するゾーン情報が見つからない場合に通知されます。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      このステーションがデータベースに見つからない場合に通知します。
     */
    public int countPairEmptyShelf(String whNo, String shelfNo)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        try
        {
            // 倉庫ステーションNoから倉庫インスタンスを取得する。
            WareHouseHandler whHandle = new WareHouseHandler(_conn);
            WareHouseSearchKey whKey = new WareHouseSearchKey();
            whKey.setStationNo(whNo);
            WareHouse wh = (WareHouse)whHandle.findPrimary(whKey);

            // 倉庫ステーションNOと、棚NoからShelfインスタンスを取得する。
            ShelfSearchKey slfKey = new ShelfSearchKey();
            ShelfHandler slfHandle = new ShelfHandler(_conn);
            slfKey.setWhStationNo(whNo);
            slfKey.setStationNo(shelfNo);
            Shelf shelf = (Shelf)slfHandle.findPrimary(slfKey);

            // shelfの親ステーションNoからAisleインスタンスを取得する。
            AisleSearchKey aisleKey = new AisleSearchKey();
            AisleHandler aisleHandle = new AisleHandler(_conn);
            aisleKey.setStationNo(shelf.getParentStationNo());
            Aisle aisle = (Aisle)aisleHandle.findPrimary(aisleKey);

            // ソフトゾーン、ハードゾーンのZoneSelectorインスタンスを生成する。
            CombineZoneSelector zsel = new CombineZoneSelector(_conn);
            Zone[] zone = zsel.selectZone(shelf.getHardZoneId(), shelf.getSoftZoneId(), wh);
            if (zone == null)
            {
                // ゾーンが見つからない。
                throw new NotFoundException();
            }

            int count = 0;
            DoubleDeepShelfHandler ddHandle = new DoubleDeepShelfHandler(_conn);
            for (int i = 0; i < zone.length; i++)
            {
                // ゾーン毎に空のペア棚数をカウントする。
                count = count + ddHandle.countPairEmptyShelf(aisle, zone[i]);
            }

            return count;
        }
        catch (NoPrimaryException e)
        {
            throw new InvalidDefineException();
        }
    }

    /**
     * 荷姿不一致の棚間移動用の空棚検索を行い、検索した空棚を返します。
     * 引数のPalletの現在ステーションNoのShelfのハードゾーンIDから空棚を検索し、
     * 空棚無しならば、Palletの荷高で空棚検索します。
     * @param  plt 棚間移動対象Palletインスタンス
     * @param  wh  搬送先WareHouseインスタンス
     * @param  shelfStNo 棚No
     * @return 棚間移動先となる<code>Shelf</code>インスタンス
     * @throws ReadWriteException データベースの読書きで障害が発生した場合に通知されます。
     * @throws InvalidDefineException インスタンスの更新条件に不正があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     */
    public Shelf findRackToRackMoveLoadMisalignment(Pallet plt, WareHouse wh, String shelfStNo)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        // 2009/09/26 Y.Osawa UPD ST
        return findRackToRackMoveLoadMisalignment(plt, wh, shelfStNo, false);
        // 2009/09/26 Y.Osawa UPD ED
    }

    // 2009/09/26 Y.Osawa ADD ST
    /**
     * 荷姿不一致の棚間移動用の空棚検索を行い、検索した空棚を返します。
     * 引数のPalletの現在ステーションNoのShelfのハードゾーンIDから空棚を検索し、
     * 空棚無しならば、Palletの荷高で空棚検索します。
     * @param  plt 棚間移動対象Palletインスタンス
     * @param  wh  搬送先WareHouseインスタンス
     * @param  shelfStNo 棚No
     * @param  alternativeLoc 二重格納、荷姿不一致など代替棚検索時はtrue、新規検索時はfalse
     * @return 棚間移動先となる<code>Shelf</code>インスタンス
     * @throws ReadWriteException データベースの読書きで障害が発生した場合に通知されます。
     * @throws InvalidDefineException インスタンスの更新条件に不正があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     */
    public Shelf findRackToRackMoveLoadMisalignment(Pallet plt, WareHouse wh, String shelfStNo, boolean alternativeLoc)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        // ソフトゾーン、ハードゾーン併用検索
        ZoneSelector zn = new CombineZoneSelector(_conn);

        ShelfSelector[] selector = new ShelfSelector[1];
        selector[0] = new RackToRackMoveSelector(_conn, zn);
        return findRackToRackMoveExecute(plt, wh, shelfStNo, selector, alternativeLoc);
    }
    // 2009/09/26 Y.Osawa ADD ED

    /**
     * 棚間移動用の空棚検索を行い、検索した空棚を返します。
     * 引数のPalletの現在ステーションNoのShelfのハードゾーンIDから空棚を検索し、
     * 空棚無しならば、Palletの荷高で空棚検索します。
     * @param  plt 棚間移動対象Palletインスタンス
     * @param  wh  搬送先WareHouseインスタンス
     * @param  shelfStNo 棚No
     * @return 棚間移動先となる<code>Shelf</code>インスタンス
     * @throws ReadWriteException データベースの読書きで障害が発生した場合に通知されます。
     * @throws InvalidDefineException インスタンスの更新条件に不正があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     */
    public Shelf findRackToRackMove(Pallet plt, WareHouse wh, String shelfStNo)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        // 2009/09/26 Y.Osawa UPD ST
        return findRackToRackMove(plt, wh, shelfStNo, false);
        // 2009/09/26 Y.Osawa UPD ED
    }

    // 2009/09/26 Y.Osawa ADD ST
    /**
     * 棚間移動用の空棚検索を行い、検索した空棚を返します。
     * 引数のPalletの現在ステーションNoのShelfのハードゾーンIDから空棚を検索し、
     * 空棚無しならば、Palletの荷高で空棚検索します。
     * @param  plt 棚間移動対象Palletインスタンス
     * @param  wh  搬送先WareHouseインスタンス
     * @param  shelfStNo 棚No
     * @param  alternativeLoc 二重格納、荷姿不一致など代替棚検索時はtrue、新規検索時はfalse
     * @return 棚間移動先となる<code>Shelf</code>インスタンス
     * @throws ReadWriteException データベースの読書きで障害が発生した場合に通知されます。
     * @throws InvalidDefineException インスタンスの更新条件に不正があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     */
    public Shelf findRackToRackMove(Pallet plt, WareHouse wh, String shelfStNo, boolean alternativeLoc)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        // ソフトゾーン、ハードゾーン併用検索
        ZoneSelector zn = new CombineZoneSelector(_conn);

        ShelfSelector[] selector = null;

        Station st = null;
        try
        {
            // パレットの現在ステーションを取得
            st = StationFactory.makeStation(_conn, plt.getCurrentStationNo());
        }
        catch (NotFoundException e)
        {
            throw new ReadWriteException();
        }

        if (st instanceof Shelf)
        {
            // パレットの現在ステーションが棚の時は倉庫全体から移動先を探す
            // パレットの荷高に該当するゾーン
            selector = new ShelfSelector[1];
            selector[0] = new RackToRackMoveSelector(_conn, zn);
        }
        else
        {
            // パレットの現在ステーションがアイルの時はアイル内から移動先を探す
            // まず、棚のハードゾーンと一致する移動先、無ければパレットの荷高に該当するゾーン
            selector = new ShelfSelector[2];
            selector[0] = new RackToRackMoveShelfSelector(_conn, zn);
            selector[1] = new RackToRackMoveSelector(_conn, zn);
        }
        return findRackToRackMoveExecute(plt, wh, shelfStNo, selector, alternativeLoc);
    }
    // 2009/09/26 Y.Osawa ADD ED

    /**
     * 棚間移動用の空棚検索を行い、検索した空棚を返します。
     * 引数のPalletの現在ステーションNoのShelfのハードゾーンID、ソフトゾーンから空棚を検索し、
     * 空棚無しならば、Palletの荷高、ソフトゾーンで空棚検索します。
     * @param  plt 棚間移動対象Palletインスタンス
     * @param  wh  搬送先WareHouseインスタンス
     * @param  shelfStNo 棚No
     * @param  selector 棚間移動用空棚検索クラス
     * @param  alternativeLoc 二重格納、荷姿不一致など代替棚検索時はtrue、新規検索時はfalse
     * @return 棚間移動先となる<code>Shelf</code>インスタンス
     * @throws ReadWriteException データベースの読書きで障害が発生した場合に通知されます。
     * @throws InvalidDefineException インスタンスの更新条件に不正があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     */
    // 2009/09/26 Y.Osawa UPD ST
    public Shelf findRackToRackMoveExecute(Pallet plt, WareHouse wh, String shelfStNo, ShelfSelector[] selector, boolean alternativeLoc)
    // 2009/09/26 Y.Osawa UPD ED
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        ShelfHandler shelfHandler = new ShelfHandler(_conn);
        Shelf slf = null;
        try
        {
            // ShelfのハードゾーンIDを取得する。
            ShelfSearchKey shelfKey = new ShelfSearchKey();
            shelfKey.setStationNo(shelfStNo);
            slf = (Shelf)shelfHandler.findPrimary(shelfKey);
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException();
        }

        Shelf location = null;
        for (int i = 0; i < selector.length; i++)
        {
            // 空棚検索処理（棚間移動用空棚検索クラス）
            // 2009/09/26 Y.Osawa UPD ST
            location = selector[i].select(plt, wh, slf.getParentStationNo(), slf.getHardZoneId(), slf.getSoftZoneId(), alternativeLoc);
            // 2009/09/26 Y.Osawa UPD ED
            if (location != null)
            {
                // 空棚あり
                return location;
            }
        }

        // 空棚無し
        return null;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**
     * データベースからステーション情報を取得しStationインスタンスを生成します。
     * @param conn データベースのコネクション
     * @param stno ステーションのステーションNo.
     * @return ステーションインスタンス
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     */
    protected Shelf getStation(Connection conn, String stno)
            throws ReadWriteException
    {
        ShelfSearchKey key = new ShelfSearchKey();
        key.setStationNo(stno);
        ShelfHandler wShelfHandler = new ShelfHandler(conn);
        Entity[] ent = wShelfHandler.find(key);

        return (Shelf)ent[0];
    }
}
//end of class

