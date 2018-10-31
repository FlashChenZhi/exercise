// $Id: Dasch_ja.java 5085 2009-10-01 08:09:16Z okamura $
package jp.co.daifuku.wms.stock.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.stock.dasch.FaStockMntDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.CarryInfoController;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.SoftZone;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 在庫メンテナンスに必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 5085 $, $Date:: 2009-10-01 17:09:16 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okamura $
 */
public class FaStockMntDASCH
        extends AbstractWmsDASCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // 出庫引当順(ロットNo.昇順)
    private static final int RETRIEVAL_ALLOCATE_PRIORITY_LOT = 1;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /**
     * DB Finder
     */
    private AbstractDBFinder _finder = null;

    /**
     * 現在点
     */
    private int _current = -1;

    /**
     * レコード総数
     */
    private int _total = -1;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでDASCHを作成します。
     * @param conn Database DBコネクション
     * @param parent Caller 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     */
    public FaStockMntDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * DBの検索を行います。
     * 帳票出力、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public void search(Params p)
            throws CommonException
    {
        // Create Finder Object
        _finder = new StockFinder(getConnection());

        // Initialize record counts
        // データ件数初期化(Excel大量出力対応)
        _finder.open(isForwardOnly());

        // Create Search Key and search for Records
        _finder.search(createSearchKey(p, true));

        _current = -1;
    }

    /**
     * 次のデータが存在するかどうかを判定します。<BR>
     * 帳票出力で使用します。
     * @return データが存在する場合はtrueを返します。
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean next()
            throws CommonException
    {
        _current++;
        return _total > _current;
    }

    /**
     * データを1件返します。<BR>
     * 帳票出力で使用します。
     * @return 取得データ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public Params get()
            throws CommonException
    {
        Stock[] stocks = (Stock[])_finder.getEntities(1);
        // パラメータ配列の生成
        Params p = new Params();

        // 取得した件数分、繰り返す
        for (Stock stock : stocks)
        {
            // エリア
            p.set(AREA_NO, stock.getAreaNo());
            // 棚
            p.set(LOCATION_NO, stock.getLocationNo());
            // 商品コード
            p.set(ITEM_CODE, stock.getItemCode());
            // 商品コード
            p.set(ITEM_NAME, stock.getValue(Item.ITEM_NAME, "").toString());
            // ロットNo.
            p.set(LOT_NO, stock.getLotNo());
            // 在庫数
            p.set(STOCK_QTY, stock.getStockQty());
            // 修正数(在庫数)
            p.set(MODIFIED_QTY, stock.getStockQty());
            // 引当可能数
            p.set(ALLOCATION_QTY, stock.getAllocationQty());
            // 最終出庫日
            p.set(LAST_RETRIEVAL_DATE, WmsFormatter.toDate(stock.getRetrievalDay()));
            // 入庫日
            p.set(STORAGE_DATE, stock.getStorageDate());
            // 入庫日時
            p.set(STORAGE_TIME, stock.getStorageDate());
            // ソフトゾーン名称
            p.set(SOFTZONE_NAME, stock.getValue(SoftZone.SOFT_ZONE_NAME, "").toString());
            // 在庫ID
            p.set(STOCK_ID, stock.getStockId());
            // 更新日時
            p.set(LAST_UPDATE_DATE, stock.getLastUpdateDate());
        }

        // 生成したパラメータを返却
        return p;
    }

    /**
     *
     * finder,Connection close
     */
    public void close()
    {
        if (_finder != null)
        {
            _finder.close();
        }
        super.close();
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
    /**
     * データ件数を返します。
     * 帳票発行、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @return データ件数
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected int actualCount(Params p)
            throws CommonException
    {
        StockHandler handler = new StockHandler(getConnection());

        // find count
        _total = handler.count(createSearchKey(p, false));

        return _total;
    }

    /**
     * 検索したデータのうち、start番目からcntで指定された件数のデータを返します。
     * 一覧表示で使用します。
     *
     * @param start 開始インデックス
     * @param cnt 件数
     * @return 対象データのリスト
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        // 返却用パラメータ配列を生成
        List<Params> params = new ArrayList<Params>();
        // 指定件数分在庫情報を取得
        Stock[] stocks = (Stock[])_finder.getEntities(start, start + cnt);

        // 取得した件数分繰り返し
        for (Stock stock : stocks)
        {
            // 取得データのセット
            Params p = new Params();
            // エリア
            p.set(AREA_NO, stock.getAreaNo());
            // 棚
            p.set(LOCATION_NO, stock.getLocationNo());
            // 商品コード
            p.set(ITEM_CODE, stock.getItemCode());
            // 商品コード
            p.set(ITEM_NAME, stock.getValue(Item.ITEM_NAME, "").toString());
            // ロットNo.
            p.set(LOT_NO, stock.getLotNo());
            // 在庫数
            p.set(STOCK_QTY, stock.getStockQty());
            // 修正数(在庫数)
            p.set(MODIFIED_QTY, stock.getStockQty());
            // 引当可能数
            p.set(ALLOCATION_QTY, stock.getAllocationQty());
            // 最終出庫日
            p.set(LAST_RETRIEVAL_DATE, WmsFormatter.toDate(stock.getRetrievalDay()));
            // 入庫日
            p.set(STORAGE_DATE, stock.getStorageDate());
            // 入庫日時
            p.set(STORAGE_TIME, stock.getStorageDate());
            // ソフトゾーンID
            p.set(SOFTZONE_ID, stock.getValue(SoftZone.SOFT_ZONE_ID, "").toString());
            // ソフトゾーン名称
            p.set(SOFTZONE_NAME, stock.getValue(SoftZone.SOFT_ZONE_NAME, "").toString());
            // 在庫ID
            p.set(STOCK_ID, stock.getStockId());
            // 更新日時
            p.set(LAST_UPDATE_DATE, stock.getLastUpdateDate());
            // ASRSフラグ
            p.set(ASRS_FLAG, checkArea(stock.getAreaNo()));

            // 生成したパラメータを配列に追加
            params.add(p);
        }

        // 生成したパラメータ配列を返却
        return params;
    }

    /**
     * 指定エリアのエリア種別がAS/RSかチェックを行います。
     *
     * @param areaNo 指定されたエリアNo.
     * @return チェック結果(true : AS/RS false : 平置き)
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkArea(String areaNo)
            throws CommonException
    {
        // 在庫情報ハンドラの生成
        AreaHandler areaHandler = new AreaHandler(getConnection());

        // 検索条件のセット
        AreaSearchKey areaKey = new AreaSearchKey();
        // エリアNo.
        areaKey.setAreaNo(areaNo);
        // エリア種別(AS/RS)
        areaKey.setAreaType(Area.AREA_TYPE_ASRS);

        // 指定エリアがAS/RSエリアの場合
        if (areaHandler.count(areaKey) > 0)
        {
            // AS/RSの場合はtrueを返却
            return true;
        }

        // 平置きの場合はfalseを返却
        return false;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        // 在庫情報検索キーの生成
        StockSearchKey key = new StockSearchKey();

        // 検索条件、集約条件をセットする
        //荷主コード
        if (!StringUtil.isBlank(param.getString(CONSIGNOR_CODE)))
        {
            key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        }
        // エリア
        key.setAreaNo(param.getString(AREA_NO));
        // 棚
        if (!StringUtil.isBlank(param.getString(LOCATION_NO)))
        {
            key.setLocationNo(param.getString(LOCATION_NO));
        }
        else
        {
            key.setLocationNo("", "!=");
        }
        // 商品コード
        if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
        {
            key.setItemCode(param.getString(ITEM_CODE));
        }

        // 結合条件
        // 荷主コード
        key.setJoin(Stock.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        // 商品コード
        key.setJoin(Stock.ITEM_CODE, Item.ITEM_CODE);
        // エリア
        key.setJoin(Stock.AREA_NO, Area.AREA_NO);

        // AS/RSの場合のみ条件に追加
        if (checkArea(param.getString(AREA_NO)))
        {
            // パレットID
            key.setJoin(Stock.PALLET_ID, "", Pallet.PALLET_ID, "(+)");
            // ステーションNo.
            key.setJoin(Pallet.CURRENT_STATION_NO, "", Shelf.STATION_NO, "(+)");
            // ソフトゾーンID
            key.setJoin(Shelf.SOFT_ZONE_ID, "", SoftZone.SOFT_ZONE_ID, "(+)");

            key.setKey(Pallet.STATUS_FLAG, SystemDefine.PALLET_STATUS_RETRIEVAL, "!=", "", "", true);

            // 既に空棚となっているデータは除外して検索します（副問合せ）
            // AS/RS搬送情報コントローラー
            CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
            // AS/RS搬送情報検索キー
            CarryInfoSearchKey carryKey = carryControl.getEmptyShelfPallet();
            key.setKey(Stock.PALLET_ID, carryKey, "!=", "", "", true);

            // 件数確認以外の場合
            if (isSet)
            {
                // ソフトゾーンID
                key.setCollect(SoftZone.SOFT_ZONE_ID);
                // ソフトゾーン名称
                key.setCollect(SoftZone.SOFT_ZONE_NAME);
            }
        }

        // 件数確認以外の場合
        if (isSet)
        {
            // 取得項目
            // エリアNo.
            key.setAreaNoCollect();
            // 棚
            key.setLocationNoCollect();
            // 商品コード
            key.setItemCodeCollect();
            // 商品名称
            key.setCollect(Item.ITEM_NAME);
            // ロットNo.
            key.setLotNoCollect();
            // 在庫数
            key.setStockQtyCollect();
            // 引当可能数
            key.setAllocationQtyCollect();
            // 最終出庫日
            key.setRetrievalDayCollect();
            // 入庫日時
            key.setStorageDateCollect();
            // 在庫ID
            key.setStockIdCollect();
            // 更新日時
            key.setLastUpdateDateCollect();

            // 並び順
            // 商品コード
            key.setItemCodeOrder(true);

            // 出庫引当順が[ロットNo.昇順]の場合のみ
            if (RETRIEVAL_ALLOCATE_PRIORITY_LOT == WmsParam.RETRIEVAL_ALLOCATE_PRIORITY)
            {
                // ロットNo.
                key.setLotNoOrder(true);
            }
            // 入庫日時
            key.setStorageDateOrder(true);
        }

        // 生成した検索キーを返却
        return key;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
