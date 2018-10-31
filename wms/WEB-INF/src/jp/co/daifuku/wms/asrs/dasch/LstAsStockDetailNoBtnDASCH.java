// $Id: LstAsStockDetailNoBtnDASCH.java 5600 2009-11-10 06:44:33Z yamashita $
package jp.co.daifuku.wms.asrs.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.CarryInfoController;

import static jp.co.daifuku.wms.asrs.dasch.LstAsStockDetailNoBtnDASCHParams.*;

/**
 * AS/RS 棚明細一覧（ボタンなし）に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 5600 $, $Date: 2009-11-10 15:44:33 +0900 (火, 10 11 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: yamashita $
 */
public class LstAsStockDetailNoBtnDASCH
        extends AbstractWmsDASCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

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
     * @param locale ロケーる
     * @param ui ユーザ情報
     */
    public LstAsStockDetailNoBtnDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // TODO : Implement for export
        // Create Finder Object
        // DFKLOOK StockFinderを設定
        _finder = new StockFinder(getConnection());
        // Initialize record counts
        _finder.open(isForwardOnly());
        // Create Search Key and search for Records
        _finder.search(createSearchKey(p, true));

        _current = -1;

        setMessage("6001013");
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
        // TODO : Implement for export
        // DFKLOOK このメソッドは未使用
        throw new ScheduleException("This method is not implemented.");
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
        // TODO : Implement for export or listcell
        // DFKLOOK StockHandlerを設定
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
        // TODO : Implement for listcell
        List<Params> params = new ArrayList<Params>();
        // DFKLOOK ここから変更
        // Stockエンティティに商品情報を取得
        Stock[] stocks = (Stock[])_finder.getEntities(start, start + cnt);

        for (Stock stock : stocks)
        {
            Params p = new Params();
            // 商品コード
            p.set(ITEM_CODE, stock.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, (String)stock.getValue(Item.ITEM_NAME, ""));
            // ロットNo.
            p.set(LOT_NO, stock.getLotNo());
            // ケース入数
            p.set(ENTERING_QTY, stock.getBigDecimal(Item.ENTERING_QTY));

            int entering_qty = Integer.parseInt(String.valueOf(stock.getValue(Item.ENTERING_QTY)));

            // 在庫数
            p.set(STOCK_QTY, stock.getStockQty());
            //在庫ケース数(在庫数/ケース入数)
            p.set(STOCK_CASE_QTY,
                    DisplayUtil.getCaseQty(stock.getBigDecimal(Stock.STOCK_QTY).longValue(), entering_qty));
            //在庫ピース数(在庫数%ケース入数)
            p.set(STOCK_PIECE_QTY, DisplayUtil.getPieceQty(stock.getBigDecimal(Stock.STOCK_QTY).longValue(),
                    entering_qty));
            // 入庫日
            p.set(STORAGE_DATE, stock.getStorageDate());
            // 入庫時刻
            p.set(STORAGE_TIME, stock.getStorageDate());
            //JANコード
            p.set(JAN, (String)stock.getValue(Item.JAN, ""));
            //ケースITF
            p.set(ITF, (String)stock.getValue(Item.ITF, ""));
            // DFKLOOK ここまで変更

            params.add(p);
        }
        return params;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        // DFKLOOK ここから変更
        // 検索条件、集約条件をセットする
        // where, group by

        // エリアコントローラー
        AreaController areacon = new AreaController(getConnection(), getClass());

        String location = areacon.toAsrsLocation(param.getString(AREA_NO), param.getString(LOCATION_NO));

        StockSearchKey skey = new StockSearchKey();
        PalletSearchKey pkey = new PalletSearchKey();

        if (StringUtil.isBlank(param.getString(PALLET_ID)))
        {
            // 副問合せ用
            pkey.setCurrentStationNo(location);
            pkey.setPalletIdCollect();

            skey.setKey(Stock.PALLET_ID, pkey);

            // 既に空棚となっているデータは除外して検索します（副問い合わせ）
            CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
            CarryInfoSearchKey carryKey = carryControl.getEmptyShelfPallet();
            skey.setKey(Stock.PALLET_ID, carryKey, "!=", "", "", true);

            // エリア
            if (!StringUtil.isBlank(param.getString(AREA_NO)))
            {
                skey.setAreaNo(param.getString(AREA_NO));
            }
        }
        else
        {
            skey.setPalletId(param.getString(PALLET_ID));
            skey.setJoin(Pallet.PALLET_ID, Stock.PALLET_ID);
        }

        // 結合
        skey.setJoin(Stock.ITEM_CODE, Item.ITEM_CODE);
        skey.setJoin(Stock.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);

        if (isSet)
        {
            // 商品ｺｰﾄﾞ、ﾛｯﾄNo.の昇順
            skey.setItemCodeOrder(true);
            skey.setLotNoOrder(true);

            // 取得項目
            // 商品コード
            skey.setItemCodeCollect();
            // 商品名称
            skey.setCollect(Item.ITEM_NAME);
            // ロットNo.
            skey.setLotNoCollect();
            // ケース入数
            skey.setCollect(Item.ENTERING_QTY);
            // 在庫数
            skey.setStockQtyCollect();
            // 入庫日時
            skey.setStorageDateCollect();
            // JANコード
            skey.setCollect(Item.JAN);
            // ケースITF
            skey.setCollect(Item.ITF);
            // 在庫ID
            skey.setStockIdCollect();
            // 最終更新日時
            skey.setLastUpdateDateCollect();
        }
        // DFKLOOK ここまで変更

        return skey;
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
