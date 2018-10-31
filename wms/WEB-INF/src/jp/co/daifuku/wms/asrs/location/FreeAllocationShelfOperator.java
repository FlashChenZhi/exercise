//$Id: FreeAllocationShelfOperator.java 7770 2010-03-31 07:12:25Z ota $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.math.BigDecimal;
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
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfFinder;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WidthHandler;
import jp.co.daifuku.wms.base.dbhandler.WidthSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.Width;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.field.FieldName;

/**<jp>
 * フリーアロケーション倉庫の棚情報に対する操作を行う処理を集めたクラスです。<BR> 
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
public class FreeAllocationShelfOperator
        extends ShelfOperator
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 空棚検索パターン数<BR>
     </jp>*/
    public static final int SELECT_EPMTY_SHELF_PETTERN = 3;

    /**<jp>
     * 空棚検索パターン（同一荷幅）<BR>
     </jp>*/
    public static final int SELECT_EPMTY_SHELF_SAME_WIDTH = 0;

    /**<jp>
     * 空棚検索パターン（フリー荷幅）<BR>
     </jp>*/
    public static final int SELECT_EPMTY_SHELF_FREE_WIDTH = 1;

    /**<jp>
     * 空棚検索パターン（指定荷幅超過）<BR>
     </jp>*/
    public static final int SELECT_EPMTY_SHELF_OVER_WIDTH = 2;

    /**<jp>
     * 荷幅未指定<BR>
     </jp>*/
    public static final int NO_WIDTH = -1;


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
    public FreeAllocationShelfOperator()
    {
    }

    /**
     * 新しい<code>ShelfOperator</code>のインスタンスを作成します。
     * 引数で渡された、データベースコネクションよりShelfインスタンスを生成し保持します。
     * @param conn データベースコネクション
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     */
    public FreeAllocationShelfOperator(Connection conn) throws ReadWriteException
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
    public FreeAllocationShelfOperator(Connection conn, String stno) throws ReadWriteException
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
     * @param width 荷幅
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
     * @param width :width
     * @param exclusionBay 検索除外ベイ
     * @return : Shelf instance searched
     * @throws ReadWriteException :Notifies of the exception occured during the database processing.
     * @throws InvalidDefineException ShelfSelecotor :Notifies if interface-undefined direction of empty location 
     * search has been selected.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    // 2009/09/26 Y.Osawa UPD ST
    public Shelf findEmptyShelf(Aisle tAisle, Zone[] tZone, int width, int[] exclusionBay)
    // 2009/09/26 Y.Osawa UPD ED
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        // 2009/09/26 Y.Osawa UPD ST
        return findEmptyShelf(tAisle, tZone, false, null, width, true, exclusionBay);
        // 2009/09/26 Y.Osawa UPD ED
    }

    /**<jp>
     * 空棚検索を行います。
     * 指定されたアイル、ゾーンをもとにShelfテーブルを検索し、空棚となっているShelfインスタンスを一つ返します。
     * 空棚が見つからない場合、nullを返します。
     * このメソッドはShelfテーブルをトランザクションが完了するまでロックするので、呼び出し元は必ずトランザクションをcommitまたはrollbackしてください。
     * @param tAisle 空棚検索対象アイル
     * @param tZone  空棚検索対象ゾーン
     * @param blnCCarry 空棚検索条件処理判定 true:条件処理する false:条件処理しない
     * @param listSPossible 空棚数アイル一覧データ
     * @param width 荷幅
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
     * @param width :width
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
    public Shelf findEmptyShelf(Aisle tAisle, Zone[] tZone, Boolean blnCCarry, ArrayList listSPossible, int width,
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
            shelf = findEmptyShelf(tAisle, tZone[i], width, empLocDeterm, exclusionBay);
            // 2009/09/26 Y.Osawa UPD ED
            if (shelf != null)
            {
                break;
            }
        }

        return shelf;
    }

    /**<jp>
     * 荷幅フリーのベイに入庫する場合、荷幅、棚使用フラグを更新します。
     * ハンドラを使用してデータベースを更新します。
     * @param plt  搬送する<code>Pallet</code>インスタンス
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      このステーションがデータベースに見つからない場合に通知します。
     </jp>*/
    public void alterWidth(Pallet plt)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        ShelfSearchKey ssk = new ShelfSearchKey();
        ShelfAlterKey sak = new ShelfAlterKey();
        ShelfHandler shdl = new ShelfHandler(_conn);

        String whno = _shelf.getWhStationNo();
        int bank = _shelf.getBankNo();
        int bay = _shelf.getBayNo();
        int level = _shelf.getLevelNo();

        try
        {
            // 予約に更新する棚の荷幅がフリーの場合は荷幅、棚使用フラグ変更
            ssk.setStationNo(_shelf.getStationNo());
            ssk.setWidth(Shelf.WIDTH_FREE);
            if (shdl.count(ssk) > 0)
            {
                // 同一バンク、ベイ、レベルの荷幅変更
                sak.clear();
                sak.setWhStationNo(whno);
                sak.setBankNo(bank);
                sak.setBayNo(bay);
                sak.setLevelNo(level);
                sak.updateWidth(plt.getWidth());
                shdl.modify(sak);

                // 荷幅情報検索
                WidthSearchKey wsk = new WidthSearchKey();
                WidthHandler whdl = new WidthHandler(_conn);
                wsk.setWhStationNo(whno);
                wsk.setWidth(plt.getWidth());
                wsk.setStartBankNo(bank, "<=");
                wsk.setEndBankNo(bank, ">=");
                wsk.setStartBayNo(bay, "<=");
                wsk.setEndBayNo(bay, ">=");
                wsk.setStartLevelNo(level, "<=");
                wsk.setEndLevelNo(level, ">=");

                Width width = (Width)whdl.findPrimary(wsk);
                if (width == null)
                {
                    Object[] tObj = new Object[5];
                    tObj[0] = plt.getWidth();
                    tObj[1] = whno;
                    tObj[2] = bank;
                    tObj[2] = bay;
                    tObj[2] = level;
                    // 6026602=指定された荷幅が荷幅情報にありません。荷幅={0} 倉庫={1} バンクNo.={2} ベイNo.={3} レベルNo.={4}
                    RmiMsgLogClient.write(6026602, LogMessage.F_ERROR, this.getClass().getSimpleName(), tObj);
                    throw new InvalidDefineException(WmsMessageFormatter.format(6026602, tObj));
                }

                try
                {
                    // 同一バンク、ベイ、レベルの荷幅に対する最大格納数のみアドレスを使用可能とする
                    sak.clear();
                    sak.setWhStationNo(whno);
                    sak.setBankNo(bank);
                    sak.setBayNo(bay);
                    sak.setLevelNo(level);
                    sak.setAddressNo(width.getMaxStorage(), ">");
                    sak.updateLocationUseFlag(Shelf.LOCATION_USE_NG);
                    shdl.modify(sak);
                }
                catch (NotFoundException e)
                {
                    // 対象データがなくてもエラーとしない
                }
            }
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException();
        }
    }

    /**<jp>
     * 荷幅をフリーに更新する場合、荷幅、棚使用フラグを更新します。ハンドラを使用してデータベースを更新します。
     * @param plt  搬送する<code>Pallet</code>インスタンス
     * @param wh   入庫先の倉庫を示す<code>WareHouse</code>インスタンス
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      このステーションがデータベースに見つからない場合に通知します。
     </jp>*/
    public void alterFreeWidth()
            throws ReadWriteException,
                NotFoundException
    {
        ShelfSearchKey ssk = new ShelfSearchKey();
        ShelfAlterKey sak = new ShelfAlterKey();
        ShelfHandler shdl = new ShelfHandler(_conn);

        String whno = _shelf.getWhStationNo();
        int bank = _shelf.getBankNo();
        int bay = _shelf.getBayNo();
        int level = _shelf.getLevelNo();

        // 空棚に更新する棚以外にベイ内に在庫が存在しなければ荷幅、棚使用フラグ変更
        ssk.setWhStationNo(_shelf.getWhStationNo());
        ssk.setBankNo(_shelf.getBankNo());
        ssk.setBayNo(_shelf.getBayNo());
        ssk.setLevelNo(_shelf.getLevelNo());
        ssk.setStationNo(_shelf.getStationNo(), "!=");
        ssk.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY, "!=");
        if (shdl.count(ssk) == 0)
        {
            // 同一バンク、ベイ、レベルの荷幅、棚使用フラグ変更
            sak.clear();
            sak.setWhStationNo(whno);
            sak.setBankNo(bank);
            sak.setBayNo(bay);
            sak.setLevelNo(level);
            sak.updateWidth(Shelf.WIDTH_FREE);
            sak.updateLocationUseFlag(Shelf.LOCATION_USE_OK);
            shdl.modify(sak);
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 指定されたゾーンの空棚検索を行います。
     * 指定されたアイル、ゾーンをもとにShelfテーブルを検索し、空棚となっているShelfインスタンスを一つ返します。
     * 空棚が見つからない場合、nullを返します。
     * このメソッドはShelfテーブルをトランザクションが完了するまでロックするので、呼び出し元は必ずトランザクションをcommitまたはrollbackしてください。
     * @param tAisle 空棚検索対象アイル
     * @param tZone  空棚検索対象ゾーン
     * @param width 荷幅
     * @param empLocDeterm  空棚を決定し更新処理を行なう場合はtrue、チェックのみの場合はfalse
     * @param exclusionBay 検索除外ベイ
     * @return 検索したShelfインスタンス
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws InvalidDefineException ShelfSelecotorインターフェースに定義されていない空棚検索方向が指定された場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    // 2009/09/26 Y.Osawa UPD ST
    protected Shelf findEmptyShelf(Aisle tAisle, Zone tZone, int width, boolean empLocDeterm, int[] exclusionBay)
    // 2009/09/26 Y.Osawa UPD ED
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        Shelf shelf = null;

        // 指定された荷幅が0の場合、荷幅は検索条件としない
        if (0 == width)
        {
            // 荷幅未指定で検索
            // 2009/09/26 Y.Osawa UPD ST
            shelf = findEmptyWidthShelf(tAisle, tZone, NO_WIDTH, empLocDeterm, exclusionBay);
            // 2009/09/26 Y.Osawa UPD ED
        }
        else
        {
            // 荷幅パターン数分検索を行う
            for (int i = 0; i < SELECT_EPMTY_SHELF_PETTERN; i++)
            {
                // 同一荷幅検索
                if (SELECT_EPMTY_SHELF_SAME_WIDTH == i)
                {
                    shelf =
                            findEmptyWidthShelfStorageOrder(tAisle, tZone, width, SELECT_EPMTY_SHELF_SAME_WIDTH,
                                    // 2009/09/26 Y.Osawa UPD ST
                                    empLocDeterm, exclusionBay);
                                    // 2009/09/26 Y.Osawa UPD ED
                }
                // フリー荷幅検索
                else if (SELECT_EPMTY_SHELF_FREE_WIDTH == i)
                {
                    // 2009/09/26 Y.Osawa UPD ST
                    shelf = findEmptyWidthShelf(tAisle, tZone, Shelf.WIDTH_FREE, empLocDeterm, exclusionBay);
                    // 2009/09/26 Y.Osawa UPD ED
                }
                // 指定荷幅超過検索
                if (SELECT_EPMTY_SHELF_OVER_WIDTH == i)
                {
                    shelf =
                            findEmptyWidthShelfStorageOrder(tAisle, tZone, width, SELECT_EPMTY_SHELF_OVER_WIDTH,
                                    // 2009/09/26 Y.Osawa UPD ST
                                    empLocDeterm, exclusionBay);
                                    // 2009/09/26 Y.Osawa UPD ED
                }

                // 空棚が取得できた場合は終了
                if (shelf != null)
                {
                    break;
                }
            }
        }

        return shelf;
    }

    /**<jp>
     * 荷幅を指定して空棚検索を行います。
     * 指定されたアイル、ゾーンをもとにShelfテーブルを検索し、空棚となっているShelfインスタンスを一つ返します。
     * 空棚が見つからない場合、nullを返します。
     * このメソッドはShelfテーブルをトランザクションが完了するまでロックするので、呼び出し元は必ずトランザクションをcommitまたはrollbackしてください。
     * @param tAisle 空棚検索対象アイル
     * @param tZone  空棚検索対象ゾーン
     * @param width 荷幅
     * @param empLocDeterm  空棚を決定し更新処理を行なう場合はtrue、チェックのみの場合はfalse
     * @param exclusionBay 検索除外ベイ
     * @return 検索したShelfインスタンス
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws InvalidDefineException ShelfSelecotorインターフェースに定義されていない空棚検索方向が指定された場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    // 2009/09/26 Y.Osawa UPD ST
    protected Shelf findEmptyWidthShelf(Aisle tAisle, Zone tZone, int width, boolean empLocDeterm, int[] exclusionBay)
    // 2009/09/26 Y.Osawa UPD ED
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        ShelfSearchKey shelfKey = new ShelfSearchKey();
//        ShelfHandler shelfHdl = new ShelfHandler(_conn);
        ShelfFinder shelfFdr = new ShelfFinder(_conn);
        
        Shelf shelf = null;
        String strDirection = String.valueOf(tZone.getDirection());

        shelfKey.clear();
        shelfKey.setParentStationNo(tAisle.getStationNo());
        shelfKey.setHardZoneId(tZone.getHardZone().getHardZoneId());
        shelfKey.setSoftZoneId(tZone.getSoftZoneID());
        shelfKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
        shelfKey.setProhibitionFlag(Shelf.PROHIBITION_FLAG_OK);
        shelfKey.setAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);
        shelfKey.setLocationUseFlag(Shelf.LOCATION_USE_OK);
        if (width >= 0)
        {
            shelfKey.setWidth(width);
        }

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
        shelfKey.setAddressNoOrder(true);

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
            while(shelfFdr.hasNext())
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
        
        return shelf;
    }

    /**<jp>
     * 荷幅を指定してベイの収容数の降順に空棚検索を行います。検索区分によって同一荷幅、指定荷幅超過を選択できます
     * 指定されたアイル、ゾーンをもとにShelfテーブルを検索し、空棚となっているShelfインスタンスを一つ返します。
     * 空棚が見つからない場合、nullを返します。
     * このメソッドはShelfテーブルをトランザクションが完了するまでロックするので、呼び出し元は必ずトランザクションをcommitまたはrollbackしてください。
     * @param tAisle 空棚検索対象アイル
     * @param tZone  空棚検索対象ゾーン
     * @param width 荷幅
     * @param empLocDeterm  空棚を決定し更新処理を行なう場合はtrue、チェックのみの場合はfalse
     * @param exclusionBay 検索除外ベイ
     * @return 検索したShelfインスタンス
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws InvalidDefineException ShelfSelecotorインターフェースに定義されていない空棚検索方向が指定された場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    // 2009/09/26 Y.Osawa UPD ST
    protected Shelf findEmptyWidthShelfStorageOrder(Aisle tAisle, Zone tZone, int width, int selectFlg,
            boolean empLocDeterm, int[] exclusionBay)
    // 2009/09/26 Y.Osawa UPD ED
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        Shelf shelf = null;
        String strDirection = String.valueOf(tZone.getDirection());

        while (true)
        {
            // 条件に該当し、収容数の多い順に検索を行う
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT DMSHELF.* FROM DMSHELF,");
            sb.append("(SELECT WH_STATION_NO, BANK_NO, BAY_NO, LEVEL_NO, COUNT(*) CNT FROM DMSHELF ");
            sb.append("WHERE STATUS_FLAG != ");
            sb.append(DBFormat.format(Shelf.LOCATION_STATUS_FLAG_EMPTY));
            sb.append(" GROUP BY WH_STATION_NO, BANK_NO, BAY_NO, LEVEL_NO) A ");
            sb.append("WHERE PARENT_STATION_NO = ");
            sb.append(DBFormat.format(tAisle.getStationNo()));
            sb.append(" AND HARD_ZONE_ID = ");
            sb.append(tZone.getHardZone().getHardZoneId());
            sb.append(" AND SOFT_ZONE_ID = ");
            sb.append(tZone.getSoftZoneID());
            sb.append(" AND STATUS_FLAG = ");
            sb.append(DBFormat.format(Shelf.LOCATION_STATUS_FLAG_EMPTY));
            sb.append(" AND PROHIBITION_FLAG = ");
            sb.append(DBFormat.format(Shelf.PROHIBITION_FLAG_OK));
            sb.append(" AND ACCESS_NG_FLAG = ");
            sb.append(DBFormat.format(Shelf.ACCESS_NG_FLAG_OK));
            sb.append(" AND LOCATION_USE_FLAG = ");
            sb.append(DBFormat.format(Shelf.LOCATION_USE_OK));
            
            // 2009/09/26 Y.Osawa ADD ST
            // 検索除外ベイが指定されている時
            if(!ArrayUtil.isEmpty(exclusionBay))
            {
                for(int bay : exclusionBay)
                {
                    sb.append(" AND DMSHELF.BAY_NO != ");
                    sb.append(bay);
                }
            }
            // 2009/09/26 Y.Osawa ADD ED

            // 荷幅条件
            // 同一荷幅
            if (SELECT_EPMTY_SHELF_SAME_WIDTH == selectFlg)
            {
                sb.append(" AND WIDTH = ");
            }
            // 指定荷幅超過
            else if (SELECT_EPMTY_SHELF_OVER_WIDTH == selectFlg)
            {
                sb.append(" AND WIDTH > ");
            }
            sb.append(width);
            sb.append(" AND DMSHELF.WH_STATION_NO = A.WH_STATION_NO ");
            sb.append(" AND DMSHELF.BANK_NO = A.BANK_NO ");
            sb.append(" AND DMSHELF.BAY_NO = A.BAY_NO ");
            sb.append(" AND DMSHELF.LEVEL_NO = A.LEVEL_NO ");
            sb.append(" ORDER BY A.CNT DESC ");
            if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_HP.equals(strDirection))
            {
                // レベル方向検索(HP側から):HP手前
                sb.append(" , DMSHELF.BAY_NO, DMSHELF.LEVEL_NO, DMSHELF.BANK_NO ");
            }
            else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_HP.equals(strDirection))
            {
                // ベイ方向検索(HP側から):HP下段
                sb.append(" , DMSHELF.LEVEL_NO, DMSHELF.BAY_NO, DMSHELF.BANK_NO ");
            }
            else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_OP.equals(strDirection))
            {
                // レベル方向検索(OP側から):OP手前
                sb.append(" , DMSHELF.BAY_NO DESC, DMSHELF.LEVEL_NO, DMSHELF.BANK_NO ");
            }
            else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_OP.equals(strDirection))
            {
                // ベイ方向検索(OP側から):OP下段
                sb.append(" , DMSHELF.LEVEL_NO, DMSHELF.BAY_NO DESC, DMSHELF.BANK_NO ");
            }
            else
            {
                //<jp> 空棚検索方向が定義外の場合、例外を返す。</jp>
                //<en>undifined , throw exception!</en>
                Object[] tObj = new Object[3];
                tObj[0] = this.getClass().getName();
                tObj[1] = "Direction";
                tObj[2] = strDirection;
                String classname = (String)tObj[0];
                RmiMsgLogClient.write(6016061, LogMessage.F_ERROR, classname, tObj);
                throw (new InvalidDefineException(WmsMessageFormatter.format(6016061, tObj[0], tObj[1], tObj[2])));
            }
            sb.append(" , DMSHELF.ADDRESS_NO ");

            DefaultDDBHandler dbh = null;
            try
            {
                dbh = new DefaultDDBHandler(_conn);
                dbh.execute(sb.toString());
                Entity[] shelfArry = dbh.getEntities(1, new Shelf());

                if (shelfArry.length > 0)
                {
                    // 更新の場合はロックを行う
                    if (empLocDeterm)
                    {
                        ShelfSearchKey shelfKey = new ShelfSearchKey();
                        ShelfHandler shelfHdl = new ShelfHandler(_conn);
                        
                        shelfKey.clear();
                        shelfKey.setStationNo(String.valueOf(shelfArry[0].getValue(Shelf.STATION_NO)));
                        shelfKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
                        shelfKey.setProhibitionFlag(Shelf.PROHIBITION_FLAG_OK);
                        shelfKey.setAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);
                        shelfKey.setLocationUseFlag(Shelf.LOCATION_USE_OK);
                        shelfKey.setWidth(shelfArry[0].getBigDecimal(Shelf.WIDTH, new BigDecimal(0)).intValue());

                        // 条件に合う棚が存在しない場合（別プロセスに更新された場合）は再度空棚検索を行う
                        if (!shelfHdl.lock(shelfKey, ShelfHandler.WAIT_SEC_UNLIMITED))
                        {
                            continue;
                        }
                        else 
                        {
                            shelf = (Shelf)shelfArry[0];    
                        }
                    }
                }
                break;
            }
            finally
            {
                if (dbh != null)
                {
                    dbh.close();
                }
            }
        }

        return shelf;
    }

    /**<jp>
     * アイル内に荷幅以上、もしくはフリー荷幅の空棚が存在するかチェックを行います。
     * @param tAisle 空棚検索対象アイル
     * @param width 荷幅
     * @param exclusionBay 検索除外ベイ
     * @return 空棚が存在する場合はtrue, それ以外はfalseを返す
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws InvalidDefineException ShelfSelecotorインターフェースに定義されていない空棚検索方向が指定された場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    // 2009/09/26 Y.Osawa UPD ST
    protected boolean checkEmptyShelfInAisle(Aisle tAisle, int width, int[] exclusionBay)
    // 2009/09/26 Y.Osawa UPD ED
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        ShelfSearchKey shelfKey = new ShelfSearchKey();
        ShelfHandler shelfHdl = new ShelfHandler(_conn);

        shelfKey.clear();
        shelfKey.setParentStationNo(tAisle.getStationNo());
        shelfKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
        shelfKey.setProhibitionFlag(Shelf.PROHIBITION_FLAG_OK);
        shelfKey.setAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);
        shelfKey.setLocationUseFlag(Shelf.LOCATION_USE_OK);
        if (width > 0)
        {
            shelfKey.setWidth(Width.WIDTH_FREE, "=", "(", "", false);
            shelfKey.setWidth(width, ">=", "", ")", true);
        }

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

        if (shelfHdl.count(shelfKey) > 0)
        {
            return true;
        }

        return false;
    }

    /**
     * 棚が属する倉庫を参照し、フリーアロケーション運用の倉庫かどうかをチェックします。
     * 
     * @return フリーアロケーション運用の場合はtrue、そうでない場合はfalseを返します。
     * @throws ReadWriteException
     *          データベースに対する処理で発生した場合に通知します。
     */
    public boolean isFreeAllocationStation()
            throws ReadWriteException
    {
        try
        {
            WareHouseHandler whHandler = new WareHouseHandler(_conn);
            WareHouseSearchKey whKey = new WareHouseSearchKey();

            // ステーションを取得
            whKey.setCollect(new FieldName(WareHouse.STORE_NAME, FieldName.ALL_FIELDS));
            whKey.setJoin(WareHouse.STATION_NO, Shelf.WH_STATION_NO);
            whKey.setKey(Shelf.STATION_NO, _shelf.getStationNo());
            WareHouse wh = (WareHouse)whHandler.findPrimary(whKey);

            if (wh != null)
            {
                // フリーアロケーション運用倉庫かどうかチェックする
                if (WareHouse.FREE_ALLOCATION_ON.equals(wh.getFreeAllocationType()))
                {
                    // フリーアロケーション運用の場合は、trueを返す。
                    return true;
                }

                // 接続するアイルのダブルディープをチェックする。
                return false;
            }
            else
            {
                throw new ReadWriteException();
            }
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException(e);
        }
    }
}
//end of class

