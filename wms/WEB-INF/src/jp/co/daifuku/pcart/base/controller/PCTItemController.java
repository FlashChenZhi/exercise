// $Id: PCTItemController.java 3209 2009-03-02 06:34:19Z arai $
package jp.co.daifuku.pcart.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.base.entity.SystemDefine.MANAGEMENT_TYPE_SYSTEM;
import static jp.co.daifuku.wms.base.entity.SystemDefine.MANAGEMENT_TYPE_USER;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.pcart.base.util.BinaryDBUtil;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.AbstractController;
import jp.co.daifuku.wms.base.dbhandler.PCTItemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTItemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTItemSearchKey;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.exception.DuplicateOperatorException;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.rft.IdFileHandler;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * PCT商品マスタコントローラクラスです。
 *
 *
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  ss
 * @author  Last commit: $Author: arai $
 */


public class PCTItemController
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
    public PCTItemController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * PCT商品マスタレコードを検索してロックします。
     * @param consignorCode 荷主コード
     * @param itemCode 商品コード
     * @param lotEnteringQty ロット入数
     * @return 該当するPCT商品マスタデータ
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public PCTItem[] lock(String consignorCode, String itemCode, int lotEnteringQty)
            throws LockTimeOutException,
                ReadWriteException
    {
        PCTItemHandler ch = new PCTItemHandler(getConnection());
        PCTItemSearchKey key = new PCTItemSearchKey();
        key.setConsignorCode(consignorCode);
        key.setItemCode(itemCode);
        key.setLotEnteringQty(lotEnteringQty);

        PCTItem[] ents = (PCTItem[])super.retryLock(key, ch);
        if (ents == null)
        {
            // no such records found.
            return new PCTItem[0];
        }

        return ents;
    }

    /**
     * PCT商品マスタ情報を作成します。
     * 
     * @param src 未指定の項目はテーブルに保存されません。
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException すでにデータが登録済みの時スローされます。
     */
    public void insert(PCTItem src, WmsUserInfo ui)
            throws ReadWriteException,
                DataExistsException
    {
        String pname = getCallerName();
        src.setRegistPname(pname);
        src.setRegistDate(new SysDate());
        src.setLastUpdatePname(pname);

        PCTItemHandler ch = new PCTItemHandler(getConnection());
        ch.create(src);

        // FIXME add log info create
    }


    /**
     * PCT商品マスタ情報を更新します。
     * 
     * @param oldItem 更新元のPCT商品マスタ情報
     * @param newItem 更新データ
     * @param ui ユーザ情報
     * @throws ReadWriteException  データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のPCT商品マスタ情報が見つからなかったとき<br>
     * もしくは、すでに内容が変更されていたときスローされます。
     * @throws NoPrimaryException 対象PCT商品マスタ情報が複数存在するときスローされます。
     */
    public void update(PCTItem oldItem, PCTItem newItem, WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException
    {
        String pname = getCallerName();
        newItem.setLastUpdatePname(pname);

        PCTItemAlterKey akey = new PCTItemAlterKey();
        akey.setUpdateValues(newItem);

        // keys to search
        FieldName[] keyFields = {
            PCTItem.CONSIGNOR_CODE,
            PCTItem.ITEM_CODE,
            PCTItem.LOT_ENTERING_QTY,
        };

        PCTItemSearchKey key = new PCTItemSearchKey();
        key = (PCTItemSearchKey)createKey(oldItem, key, keyFields);
        akey.setKey(key);

        PCTItemHandler ch = new PCTItemHandler(getConnection());
        int numrec = ch.modify(akey);
        if (numrec > 1)
        {
            throw new NoPrimaryException();
        }

        // FIXME add log info create
    }


    /**
     * 対象PCT商品マスタ情報を削除します。
     * 
     * @param delItem 削除対象データのPCT商品マスタ情報
     * @param ui ユーザ情報
     * @throws NotFoundException 該当のPCT商品マスタ情報が見つからなかったとき<br>
     * もしくは、すでに内容が変更されていたときスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException 対象PCT商品マスタ情報が複数存在するときスローされます。
     */
    public void delete(PCTItem delItem, WmsUserInfo ui)
            throws NotFoundException,
                ReadWriteException,
                NoPrimaryException
    {
        PCTItemHandler ch = new PCTItemHandler(getConnection());
        PCTItemSearchKey key = new PCTItemSearchKey();

        // keys to search
        FieldName[] keyFields = {
            PCTItem.CONSIGNOR_CODE,
            PCTItem.ITEM_CODE,
            PCTItem.LOT_ENTERING_QTY,
        };

        key = (PCTItemSearchKey)createKey(delItem, key, keyFields);

        int numrec = ch.drop(key);
        if (numrec > 1)
        {
            throw new NoPrimaryException();
        }

        // FIXME add log info create 
    }

    /**
     * 登録されているPCT商品マスタ情報を更新します。<br>
     * まだ未登録の場合はPCT商品マスタ情報を追加します。<br>
     * 更新対象のPCT商品マスタ情報は、システム管理区分が通常のものだけになります。
     * 
     * @param src 更新するPCT商品マスタ情報
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws NoPrimaryException 対象PCT商品マスタ情報が複数存在するときスローされます。
     * @throws NotFoundException 対象PCT商品マスタ情報が他のアプリケーションから変更されたときスローされます。
     * @throws DataExistsException 対象PCT商品マスタ情報が他のアプリケーションから登録されたときスローされます。
     */
    public void autoCreate(PCTItem src, WmsUserInfo ui)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException,
                NotFoundException,
                DataExistsException
    {
        // read master with lock
        PCTItem[] ents = lock(src.getConsignorCode(), src.getItemCode(), src.getLotEnteringQty());
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

        PCTItem pctItem = ents[0];

        String mtype = pctItem.getManagementType();
        if (MANAGEMENT_TYPE_SYSTEM.equals(mtype))
        {
            // return if management type is SYSTEM
            return;
        }

        // update if exists
        update(pctItem, src, ui);
    }

    /**
     * スキャンコードからPCT商品マスタ情報を検索し、商品コードを返します。<br>
     * 検索対象は以下の通りです。<br>
     * 
     * 
     * 
     * <table border="1">
     * <tr bgcolor="#CCCCFF" class="TableHeadingColor">
     * <td>scanidxの値</td><td>検索対象</td>
     * </tr>
     * <!--
     * <tr><td>0</td><td>商品コード</td></tr>
     * -->
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
            PCTItem.ITEM_CODE, // NOT used
            PCTItem.JAN,
            PCTItem.ITF,
            PCTItem.BUNDLE_ITF,
        };

        PCTItemHandler handler = new PCTItemHandler(getConnection());
        PCTItemSearchKey key = new PCTItemSearchKey();

        key.setItemCodeCollect();

        key.setConsignorCode(consignorCode);
        key.setKey(keyFields[scanidx], scanCode);
        try
        {
            PCTItem ent = (PCTItem)handler.findPrimary(key);
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
     * 条件に該当する商品データのリストを作成します。複数件該当した場合に呼び出されるメソッドです。
     * 
     * @param consignorCode 荷主コード
     * @param scanCode      スキャンコード
     * @return  商品情報(商品コード、名称、入数 の文字列配列のリスト)
     * @throws ReadWriteException   データベースアクセスエラーが発生したときスローされます。
     */
    public List<String[]> getDupItems(String consignorCode, String scanCode)
            throws ReadWriteException
    {
        FieldName[] keyFields = {
            PCTItem.ITEM_CODE,
            PCTItem.JAN,
            PCTItem.ITF,
            PCTItem.BUNDLE_ITF,
        };

        List<String[]> dupList = new ArrayList<String[]>();
        PCTItemHandler handler = new PCTItemHandler(getConnection());
        PCTItemSearchKey key = new PCTItemSearchKey();

        for (int i = 0; i < MAX_SCAN_INDEX; i++)
        {
            key.clear();
            // 取得項目
            key.setItemCodeCollect();
            key.setItemNameCollect();
            key.setLotEnteringQtyCollect();
            // 条件
            key.setConsignorCode(consignorCode);
            key.setKey(keyFields[i], scanCode);
            // Order
            key.setConsignorCodeOrder(true);
            key.setItemCodeOrder(true);
            key.setLotEnteringQtyOrder(true);
            // 検索処理
            PCTItem[] pItems = (PCTItem[])handler.find(key);

            if (pItems != null && pItems.length > 0)
            {
                // データが取得できた場合、取得データを全てリストに保存
                for (PCTItem pItem : pItems)
                {
                    String[] lineData = new String[3];
                    lineData[0] = pItem.getItemCode();
                    lineData[1] = pItem.getItemName();
                    lineData[2] = String.valueOf(pItem.getLotEnteringQty());
                    dupList.add(lineData);
                }
                // データ取得できたので、DB問い合わせのループから抜ける。
                break;
            }
        }
        return dupList;
    }

    /**
     * 商品画像を取得します。<BR>
     * 
     * @param consignorCode 対象の荷主コード
     * @param itemCode      対象の商品コード
     * @param enteringQty   対象のロット入数
     * @param filePath      画像保存先
     * @return true 画像あり、false 画像なし
     * @throws ReadWriteException   データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException    検索の結果該当するデータが無い場合
     * @throws IOException          ファイルの作成や書き込みに失敗した場合
     */
    public boolean getItemPicture(String consignorCode, String itemCode, int enteringQty, String filePath)
            throws ReadWriteException,
                NotFoundException,
                IOException
    {
        // ディレクトリ存在チェックをして、無い場合は作成。
        IdFileHandler.checkDirectory(new File(filePath));
        
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ITEM_PICTURE FROM DMPCTITEM WHERE CONSIGNOR_CODE='");
        sql.append(consignorCode);
        sql.append("' AND ITEM_CODE='");
        sql.append(itemCode);
        sql.append("' AND LOT_ENTERING_QTY='");
        sql.append(enteringQty);
        sql.append("'");
        String sqlStr = String.valueOf(sql);
        long fileSize = BinaryDBUtil.getBinaryFromDB(getConnection(), sqlStr, filePath);
        if (0 < fileSize)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 商品画像を登録します。<BR>
     * 
     * @param consignorCode 対象の荷主コード
     * @param itemCode      対象の商品コード
     * @param enteringQty   対象のロット入数
     * @param filePath      登録元画像ファイル
     * @return true 登録成功、false 登録対象なし
     * @throws ReadWriteException       データベースとの接続で異常が発生した場合に通知されます。
     * @throws FileNotFoundException    登録元画像ファイルが見つからない場合に通知されます。
     */
    public boolean setItemPicture(String consignorCode, String itemCode, int enteringQty, String filePath)
            throws ReadWriteException,
                FileNotFoundException
    {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE DMPCTITEM SET LAST_UPDATE_DATE = SYSTIMESTAMP , LAST_UPDATE_PNAME = '");
        sql.append(getClass().getSimpleName());
        sql.append("' , ITEM_PICTURE = ? ");
        sql.append("WHERE CONSIGNOR_CODE='");
        sql.append(consignorCode);
        sql.append("' AND ITEM_CODE='");
        sql.append(itemCode);
        sql.append("' AND LOT_ENTERING_QTY='");
        sql.append(enteringQty);
        sql.append("'");
        String sqlStr = String.valueOf(sql);
        int lineNum = BinaryDBUtil.setBinaryToDB(getConnection(), sqlStr, filePath);
        if (0 < lineNum)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

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
        return "$Id: PCTItemController.java 3209 2009-03-02 06:34:19Z arai $";
    }
}
