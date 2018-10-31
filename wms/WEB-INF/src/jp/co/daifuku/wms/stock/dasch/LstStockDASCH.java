// $Id: LstStockDASCH.java 4808 2009-08-10 06:32:12Z shibamoto $
package jp.co.daifuku.wms.stock.dasch;

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
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.util.DisplayUtil;

import static jp.co.daifuku.wms.stock.dasch.LstStockDASCHParams.*;

/**
 * 棚明細一覧に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4808 $, $Date: 2009-08-10 15:32:12 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstStockDASCH
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
    public LstStockDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
            //在庫ケース数(在庫数/ケース入数)
            p.set(STOCK_CASE_QTY,
                    DisplayUtil.getCaseQty(stock.getBigDecimal(Stock.STOCK_QTY).longValue(), entering_qty));
            //在庫ピース数(在庫数%ケース入数)
            p.set(STOCK_PIECE_QTY, DisplayUtil.getPieceQty(stock.getBigDecimal(Stock.STOCK_QTY).longValue(),
                    entering_qty));
            //引当可能ケース数(引当可能数/ケース入数)
            p.set(ALLOC_CASE_QTY, DisplayUtil.getCaseQty(stock.getBigDecimal(Stock.ALLOCATION_QTY).longValue(),
                    entering_qty));
            //引当可能ピース数(引当可能数%ケース入数)
            p.set(ALLOC_PIECE_QTY, DisplayUtil.getPieceQty(stock.getBigDecimal(Stock.ALLOCATION_QTY).longValue(),
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
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
    {
        // DFKLOOK ここから変更
        // 検索条件、集約条件をセットする
        // where, group by

        StockSearchKey key = new StockSearchKey();

        /* 検索条件セット */
        // エリアNo.
        String areano = param.getString(AREA_NO);
        if (!StringUtil.isBlank(areano))
        {
            key.setAreaNo(areano);
        }
        // 棚No.
        String locno = param.getString(LOCATION_NO);
        if (!StringUtil.isBlank(locno))
        {
            key.setLocationNo(locno);
        }
        // 在庫数：１以上
        key.setStockQty(1, ">=");

        /* 結合条件の指定(商品マスター) */
        // 荷主コード
        key.setJoin(Stock.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        // 商品コード
        key.setJoin(Stock.ITEM_CODE, Item.ITEM_CODE);

        // 集約条件

        if (isSet)
        {
            // ソート順：商品コード、ロットNo.順
            key.setItemCodeOrder(true);
            key.setLotNoOrder(true);

            // 取得項目
            // 商品コード
            key.setItemCodeCollect();
            // 商品名称(商品マスタより取得)
            key.setCollect(Item.ITEM_NAME);
            // ロットNo.
            key.setLotNoCollect();
            // ケース入数(商品マスタより取得)
            key.setCollect(Item.ENTERING_QTY);
            // 在庫数
            key.setStockQtyCollect();
            // 引当可能数
            key.setAllocationQtyCollect();
            // 入庫日時
            key.setStorageDateCollect();
            // JANコード(商品マスタより取得)
            key.setCollect(Item.JAN);
            // ケースITF(商品マスタより取得)
            key.setCollect(Item.ITF);
        }
        // DFKLOOK ここまで変更

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
