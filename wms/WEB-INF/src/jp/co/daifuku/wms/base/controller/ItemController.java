// $Id: ItemController.java 7591 2010-03-15 13:51:03Z kishimoto $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.base.entity.SystemDefine.MANAGEMENT_TYPE_SYSTEM;
import static jp.co.daifuku.wms.base.entity.SystemDefine.MANAGEMENT_TYPE_USER;

import java.sql.Connection;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.emanager.util.P11Config;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.dbhandler.ItemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.ItemHistory;
import jp.co.daifuku.wms.base.entity.SoftZone;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.DuplicateOperatorException;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;


/**
 * 商品マスタコントローラクラスです。
 *
 *
 * @version $Revision: 7591 $, $Date: 2010-03-15 22:51:03 +0900 (月, 15 3 2010) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */


public class ItemController
        extends AbstractController
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** スキャンコードを検索するタイプ数 (1～3) */
    public static final int MAX_SCAN_INDEX = 4;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public ItemController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 商品マスタ情報を検索してロックします。
     * 
     * @param consignorCode 荷主コード
     * @param itemCode 商品コード
     * @return 該当する商品マスタデータ
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public Item[] lock(String consignorCode, String itemCode)
            throws LockTimeOutException,
                ReadWriteException
    {
        ItemHandler ch = new ItemHandler(getConnection());
        ItemSearchKey key = new ItemSearchKey();
        key.setConsignorCode(consignorCode);
        key.setItemCode(itemCode);

        Item[] ents = (Item[])super.retryLock(key, ch);
        if (ents == null)
        {
            // no such records found.
            return new Item[0];
        }
        return ents;
    }

    /**
     * 商品マスタ情報を作成します。
     * 
     * @param src 未指定の項目はテーブルに保存されません。
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException すでにデータが登録済みの時スローされます。
     */
    public void insert(Item src, WmsUserInfo ui)
            throws ReadWriteException,
                DataExistsException
    {
        String pname = getCallerName();
        src.setRegistPname(pname);
        src.setRegistDate(new SysDate());
        src.setLastUpdatePname(pname);

        ItemHandler ch = new ItemHandler(getConnection());
        ch.create(src);

        // FIXME add log info create
    }


    /**
     * 商品マスタ情報を更新します。
     * 
     * @param oldItem 更新元の商品マスタ情報
     * @param newItem 更新データ
     * @param ui ユーザ情報
     * @throws ReadWriteException  データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException すでにデータが登録済みの時スローされます。
     * @throws NotFoundException 該当の商品マスタ情報が見つからなかったとき<br>
     * もしくは、すでに内容が変更されていたときスローされます。
     * @throws NoPrimaryException 対象商品マスタ情報が複数存在するときスローされます。
     */
    public void update(Item oldItem, Item newItem, WmsUserInfo ui)
            throws ReadWriteException,
                DataExistsException,
                NotFoundException,
                NoPrimaryException
    {
        String pname = getCallerName();
        newItem.setLastUpdatePname(pname);

        ItemAlterKey akey = new ItemAlterKey();
        akey.setUpdateValues(newItem);

        // keys to search
        FieldName[] keyFields = {
            Item.CONSIGNOR_CODE,
            Item.ITEM_CODE,
        };

        ItemSearchKey key = new ItemSearchKey();
        key = (ItemSearchKey)createKey(oldItem, key, keyFields);
        akey.setKey(key);

        ItemHandler ch = new ItemHandler(getConnection());
        int numrec = ch.modify(akey);
        if (numrec > 1)
        {
            throw new NoPrimaryException();
        }

        // FIXME add log info create
    }


    /**
     * 対象商品マスタ情報を削除します。
     * 
     * @param delItem 削除対象データの商品マスタ情報
     * @param ui ユーザ情報
     * @throws NotFoundException 該当の商品マスタ情報が見つからなかったとき<br>
     * もしくは、すでに内容が変更されていたときスローされます。
     * @throws DataExistsException すでにデータが登録済みの時スローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException 対象商品マスタ情報が複数存在するときスローされます。
     */
    public void delete(Item delItem, WmsUserInfo ui)
            throws NotFoundException,
                DataExistsException,
                ReadWriteException,
                NoPrimaryException
    {
        ItemHandler ch = new ItemHandler(getConnection());
        ItemSearchKey key = new ItemSearchKey();

        // keys to search
        FieldName[] keyFields = {
            Item.CONSIGNOR_CODE,
            Item.ITEM_CODE,
        };

        key = (ItemSearchKey)createKey(delItem, key, keyFields);

        int numrec = ch.drop(key);
        if (numrec > 1)
        {
            throw new NoPrimaryException();
        }

        // FIXME add log info create 
    }

    /**
     * 登録されている商品マスタ情報を更新します。<br>
     * まだ未登録の場合は商品マスタ情報を追加します。<br>
     * 更新対象の商品マスタ情報は、システム管理区分が通常のものだけになります。
     * 
     * @param src 更新する商品マスタ情報
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws NoPrimaryException 対象商品マスタ情報が複数存在するときスローされます。
     * @throws NotFoundException 対象商品マスタ情報が他のアプリケーションから変更されたときスローされます。
     * @throws DataExistsException 対象商品マスタ情報が他のアプリケーションから登録されたときスローされます。
     */
    public void autoCreate(Item src, WmsUserInfo ui)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException,
                NotFoundException,
                DataExistsException
    {
        // read master with lock
        Item[] ents = lock(src.getConsignorCode(), src.getItemCode());
        if (ArrayUtil.length(ents) > 1)
        {
            throw new NoPrimaryException();
        }

        if (ArrayUtil.isEmpty(ents))
        {
            // insert if no such Item
            src.setManagementType(MANAGEMENT_TYPE_USER);
            insert(src, ui);
            return;
        }

        Item item = ents[0];

        String mtype = item.getManagementType();
        if (MANAGEMENT_TYPE_SYSTEM.equals(mtype))
        {
            // return if management type is SYSTEM
            return;
        }

        // update if exists
        update(item, src, ui);
    }

    /**
     * スキャンコードから商品マスタ情報を検索し、商品コードを返します。<br>
     * 検索対象は以下の通りです。<br>
     * 
     * <table border="1">
     * <tr bgcolor="#CCCCFF" class="TableHeadingColor">
     * <td>scanidxの値</td><td>検索対象</td>
     * </tr>
     * <tr><td>1</td><td>JANコード</td></tr>
     * <tr><td>2</td><td>ケースITF</td></tr>
     * <tr><td>3</td><td>ボールITF</td></tr>
     * </table>
     * 
     * @param consignorCode 対象の荷主コード
     * @param scanCode スキャンコード
     * @param scanidx 検索対象の指定 (1...MAX_SCAN_INDEX)
     * @return 商品コード
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DuplicateOperatorException 商品コードが一意に特定できない場合にスローされます。
     */
    public String getItemCode(String consignorCode, String scanCode, int scanidx)
            throws ReadWriteException,
                DuplicateOperatorException
    {
        FieldName[] keyFields = {
            Item.ITEM_CODE, // NOT used
            Item.JAN,
            Item.ITF,
            Item.BUNDLE_ITF,
        };

        ItemHandler handler = new ItemHandler(getConnection());
        ItemSearchKey key = new ItemSearchKey();

        key.setItemCodeCollect();

        key.setConsignorCode(consignorCode);
        key.setKey(keyFields[scanidx], scanCode);
        try
        {
            Item ent = (Item)handler.findPrimary(key);
            if (null == ent)
            {
                return "";
            }
            return ent.getItemCode();
        }
        catch (NoPrimaryException e)
        {
            throw new DuplicateOperatorException(OperatorException.ERR_ITEM_DUPLICATED);
        }
    }

    /**
     * 商品名称を取得します。<br>
     * 該当商品が存在しなかった場合は0バイトの文字列を返します。
     * 
     * @param itemCode 対象の商品コード
     * @param consignorCode 対象の荷主コード
     * @return 商品名称
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public String getItemName(String itemCode, String consignorCode)
            throws ReadWriteException
    {
        // 商品名称取得
        ItemHandler itemHandler = new ItemHandler(getConnection());
        ItemSearchKey iKey = new ItemSearchKey();

        // 取得条件
        iKey.setItemCode(itemCode);
        iKey.setConsignorCode(consignorCode);
        iKey.setItemNameCollect();

        try
        {
            Item item = (Item)itemHandler.findPrimary(iKey);
            if (item == null)
            {
                return "";
            }
            return item.getItemName();
        }
        catch (NoPrimaryException e)
        {
            return "";
        }
    }

    /**
     * 商品マスタ更新履歴情報登録処理を行います。
     * 
     * @param item          対象マスタ情報
     * @param operationKind 操作区分
     * @param ui            ユーザ情報
     * @throws ReadWriteException データベースエラー又は、マスタ情報がnullのとき
     * @throws DataExistsException 商品マスタ更新履歴情報登録済み
     */
    public void insertHistory(Item item, String operationKind, DfkUserInfo ui)
            throws ReadWriteException,
                DataExistsException
    {
        // PART11 Packageなし又は、改廃履歴不要指定時、処理を行わず復帰します。
        if (!P11Config.isPart11Log() || !P11Config.isMasterLog())
        {
            return;
        }

        if (null == item)
        {
            throw new ReadWriteException();
        }

        // create entity for insert
        ItemHistoryHandler histch = new ItemHistoryHandler(getConnection());
        ItemHistory hist = new ItemHistory();

        // ユーザID
        hist.setUserId(ui.getUserId());
        // ユーザ名称
        hist.setUserName(ui.getUserName());
        // 端末No
        hist.setTerminalNo(ui.getTerminalNumber());
        // 端末名称
        hist.setTerminalName(ui.getTerminalName());
        // 端末IPアドレス
        hist.setIpAddress(ui.getTerminalAddress());
        // DS番号
        hist.setDsNo(ui.getDsNumber());
        // リソース番号
        hist.setPagenameResourcekey(ui.getPageNameResourceKey());

        // 更新区分
        hist.setUpdateKind(operationKind);
        // システム管理区分
        hist.setManagementType(item.getManagementType());
        // 荷主コード
        hist.setConsignorCode(item.getConsignorCode());
        // 商品コード
        hist.setItemCode(item.getItemCode());

        if (SystemDefine.UPDATE_KIND_REGIST.equals(operationKind))
        {
            // 修正後商品名称
            hist.setUpdateItemName(item.getItemName());
            // 修正後ソフトゾーン
            hist.setUpdateSoftZoneId(item.getSoftZoneId());
            // 修正後JANコード
            hist.setUpdateJan(item.getJan());
            // 修正後ケースITF
            hist.setUpdateItf(item.getItf());
            // 修正後ケース入数
            hist.setUpdateEnteringQty(item.getEnteringQty());
            // 修正後上限在庫数
            hist.setUpdateUpperQty(item.getUpperQty());
            // 修正後下限在庫数
            hist.setUpdateLowerQty(item.getLowerQty());
            // 修正後把持区分
            hist.setUpdateHoldType(item.getHoldType());
            // 修正後一時商品区分
            hist.setUpdateTemporaryType(item.getTemporaryType());
        }
        else
        {
            // 商品名称
            hist.setItemName(item.getItemName());
            // ソフトゾーン
            hist.setSoftZoneId(item.getSoftZoneId());
            // JANコード
            hist.setJan(item.getJan());
            // ケースITF
            hist.setItf(item.getItf());
            // ボールITF
            hist.setBundleItf(item.getBundleItf());
            // ケース入数
            hist.setEnteringQty(item.getEnteringQty());
            // ボール入数
            hist.setBundleEnteringQty(item.getBundleEnteringQty());
            // 上限在庫数
            hist.setUpperQty(item.getUpperQty());
            // 下限在庫数
            hist.setLowerQty(item.getLowerQty());
            // 把持区分
            hist.setHoldType(item.getHoldType());
            // 一時商品区分
            hist.setTemporaryType(item.getTemporaryType());
        }


        histch.create(hist);
    }

    /**
     * 商品マスタ更新履歴情報登録処理を行います。
     * 
     * @param oldItem   修正前マスタ情報
     * @param newItem   修正後マスタ情報
     * @param operationKind 操作区分
     * @param ui            ユーザ情報
     * @throws ReadWriteException データベースエラー又は、マスタ情報がnullのとき
     * @throws DataExistsException 商品マスタ更新履歴情報登録済み
     */
    public void insertHistory(Item oldItem, Item newItem, String operationKind, DfkUserInfo ui)
            throws ReadWriteException,
                DataExistsException
    {
        // PART11 Packageなし又は、改廃履歴不要指定時、処理を行わず復帰します。
        if (!P11Config.isPart11Log() || !P11Config.isMasterLog())
        {
            return;
        }

        if (null == oldItem)
        {
            throw new ReadWriteException();
        }
        if (null == newItem)
        {
            throw new ReadWriteException();
        }

        // create entity for insert
        ItemHistoryHandler histch = new ItemHistoryHandler(getConnection());
        ItemHistory hist = new ItemHistory();

        // ユーザID
        hist.setUserId(ui.getUserId());
        // ユーザ名称
        hist.setUserName(ui.getUserName());
        // 端末No
        hist.setTerminalNo(ui.getTerminalNumber());
        // 端末名称
        hist.setTerminalName(ui.getTerminalName());
        // 端末IPアドレス
        hist.setIpAddress(ui.getTerminalAddress());
        // DS番号
        hist.setDsNo(ui.getDsNumber());
        // リソース番号
        hist.setPagenameResourcekey(ui.getPageNameResourceKey());

        // 更新区分
        hist.setUpdateKind(operationKind);
        // システム管理区分
        hist.setManagementType(oldItem.getManagementType());
        // 荷主コード
        hist.setConsignorCode(oldItem.getConsignorCode());
        // 商品コード
        hist.setItemCode(oldItem.getItemCode());
        // 商品名称
        hist.setItemName(oldItem.getItemName());
        // ソフトゾーン
        hist.setSoftZoneId(oldItem.getSoftZoneId());
        // JANコード
        hist.setJan(oldItem.getJan());
        // ケースITF
        hist.setItf(oldItem.getItf());
        // ボールITF
        hist.setBundleItf(oldItem.getBundleItf());
        // ケース入数
        hist.setEnteringQty(oldItem.getEnteringQty());
        // ボール入数
        hist.setBundleEnteringQty(oldItem.getBundleEnteringQty());
        // 上限在庫数
        hist.setUpperQty(oldItem.getUpperQty());
        // 下限在庫数
        hist.setLowerQty(oldItem.getLowerQty());
        // 把持区分
        hist.setHoldType(oldItem.getHoldType());
        // 一時商品区分
        hist.setTemporaryType(oldItem.getTemporaryType());
        // 修正後商品名称
        hist.setUpdateItemName(newItem.getItemName());
        // 修正後ソフトゾーン
        hist.setUpdateSoftZoneId(newItem.getSoftZoneId());
        // 修正後JANコード
        hist.setUpdateJan(newItem.getJan());
        // 修正後ケースITF
        hist.setUpdateItf(newItem.getItf());
        // 修正後ケース入数
        hist.setUpdateEnteringQty(newItem.getEnteringQty());
        // 修正後上限在庫数
        hist.setUpdateUpperQty(newItem.getUpperQty());
        // 修正後下限在庫数
        hist.setUpdateLowerQty(newItem.getLowerQty());
        // 修正後把持区分
        hist.setUpdateHoldType(newItem.getHoldType());
        // 修正後一時商品区分
        hist.setUpdateTemporaryType(newItem.getTemporaryType());

        histch.create(hist);
    }

    /**
     * 商品コードがマスタに存在するかどうかチェックして返します。
     * 
     * @param itemcode 商品コード
     * @param cons 荷主コード
     * @return 存在する場合は true
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean exists(String itemcode, String cons)
            throws ReadWriteException
    {
        ItemHandler ch = new ItemHandler(getConnection());
        ItemSearchKey key = new ItemSearchKey();
        key.setConsignorCode(cons);
        key.setItemCode(itemcode);

        int cnt = ch.count(key);

        return (0 < cnt);
    }

    
    //////////////////////////////////////////////////////////////////////////
    // DAC added from here
    //////////////////////////////////////////////////////////////////////////
    /**
     * 引数を検索キーに商品マスタ情報を検索し、商品情報を取得します。<br>
     * 検索対象が見つからなかった場合、nullを返します。
     * 
     * @param itemCode 商品コード
     * @param consignorCode 荷主コード
     * @param conn データベースコネクション
     * @return 商品情報 (見つからなかった場合は null )
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public static Item getItemInfo(String itemCode, String consignorCode, Connection conn)
            throws ReadWriteException
    {
        // 商品名称取得
//        ItemHandler itemHandler = new ItemHandler(getConnection());
        ItemHandler itemHandler = new ItemHandler(conn);
        ItemSearchKey iKey = new ItemSearchKey();

        // 取得条件
        iKey.setItemCode(itemCode);
        iKey.setConsignorCode(consignorCode);
        iKey.setItemNameCollect();
        iKey.setItfCollect();
        iKey.setJanCollect();
        iKey.setBundleItfCollect();
        iKey.setEnteringQtyCollect();
        iKey.setBundleEnteringQtyCollect();
        iKey.setSoftZoneIdCollect();
        iKey.setCollect(SoftZone.SOFT_ZONE_NAME);
        iKey.setJoin(Item.SOFT_ZONE_ID, "", SoftZone.SOFT_ZONE_ID, "(+)");

        try
        {
            Item item = (Item)itemHandler.findPrimary(iKey);
            if (item == null)
            {
                return null;
            }
            return item;
        }
        catch (NoPrimaryException e)
        {
            return null;
        }
    }
    //////////////////////////////////////////////////////////////////////////
    // DAC added to here
    //////////////////////////////////////////////////////////////////////////

    
    
    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: ItemController.java 7591 2010-03-15 13:51:03Z kishimoto $";
    }
}
