// $Id: ItemWorkingInquiryDASCH.java 4926 2009-08-28 05:24:22Z shibamoto $
package jp.co.daifuku.wms.stock.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.stock.dasch.ItemWorkingInquiryDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.stock.schedule.StockInParameter;

/**
 * 商品別在庫照会に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 4926 $, $Date: 2009-08-28 14:24:22 +0900 (金, 28 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class ItemWorkingInquiryDASCH
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
    public ItemWorkingInquiryDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
            // 取得データのセット
            // 商品コード
            p.set(ITEM_CODE, stock.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, (String)stock.getValue(Item.ITEM_NAME, ""));
            // ロットNo.
            p.set(LOT_NO, stock.getLotNo());
            // エリアNo.
            p.set(AREA_NO, String.valueOf(stock.getValue(Stock.AREA_NO, "")));
            // 棚
            p.set(LOCATION_NO, stock.getLocationNo());
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

            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());

            break;
        }

        // 生成した配列を返却
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
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        List<Params> params = new ArrayList<Params>();
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
            // エリアNo.
            p.set(AREA_NO, String.valueOf(stock.getValue(Stock.AREA_NO, "")));
            // 棚
            p.set(LOCATION_NO, stock.getLocationNo());
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
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
    {
        StockSearchKey key = new StockSearchKey();

        // 検索条件、集約条件をセットする
        // エリアNo.
        String areano = param.getString(AREA_NO);
        if (!WmsParam.ALL_AREA_NO.equals(areano))
        {
            key.setAreaNo(areano);
        }

        // 商品コード
        String[] tmp = WmsFormatter.getFromTo(param.getString(ITEM_CODE_FROM), param.getString(ITEM_CODE_TO));
        String from = tmp[0];
        String to = tmp[1];

        // 商品コード(From)
        if (!StringUtil.isBlank(from))
        {
            key.setItemCode(from, ">=");
        }
        // 商品コード(To)
        if (!StringUtil.isBlank(to))
        {
            key.setItemCode(to, "<=");
        }

        //荷主コード
        if (!StringUtil.isBlank(param.getString(CONSIGNOR_CODE)))
        {
        	key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        }
        
        // 在庫数が１以上存在すること
        key.setStockQty(0, ">");

        // 荷主コード
        key.setJoin(Stock.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        // 商品コード
        key.setJoin(Stock.ITEM_CODE, Item.ITEM_CODE);
        // エリアNo.
        key.setJoin(Stock.AREA_NO, Area.AREA_NO);

        /* 集約条件の指定 */
        String groupcondition = param.getString(GROUP_CONDITION);

        if (StockInParameter.COLLECT_CONDITION_DETAIL.equals(groupcondition))
        {
            // 詳細表示の場合
            setCollectDetails(key);
        }
        else if (StockInParameter.COLLECT_CONDITION_ITEM.equals(groupcondition))
        {
            // 商品単位の場合
            setCollectGroup(key);
        }

        return key;
    }

    /**
     * 取得項目、ソート順をセット。<BR>
     * 集約条件：詳細表示の場合
     * @param skey サーチキー
     */
    protected void setCollectDetails(StockSearchKey skey)
    {
        /* 取得項目の指定 */
        skey.setItemCodeCollect(); // 商品コード
        skey.setCollect(Item.ITEM_NAME); // 商品名称(商品マスタより取得)
        skey.setAreaNoCollect(); // エリアNo.
        skey.setCollect(Area.AREA_NAME); // エリア名称
        skey.setLocationNoCollect(); // 棚No.
        skey.setLotNoCollect(); // ロットNo.
        skey.setCollect(Item.ENTERING_QTY); // ケース入数(商品マスタより取得)
        skey.setStockQtyCollect(); // 在庫数
        skey.setAllocationQtyCollect(); // 引当可能数
        skey.setStorageDateCollect(); // 入庫日時
        skey.setCollect(Item.JAN); // JANコード(商品マスタより取得)
        skey.setCollect(Item.ITF); // ケースITF(商品マスタより取得)

        /* ソート順：商品コード、ロットNo.、エリア、棚順 */
        skey.setItemCodeOrder(true);
        skey.setLotNoOrder(true);
        skey.setAreaNoOrder(true);
        skey.setLocationNoOrder(true);

    }

    /**
     * 取得項目、集約条件、ソート順をセット。<BR>
     * 集約条件：商品単位の場合
     * @param skey サーチキー
     */
    protected void setCollectGroup(StockSearchKey skey)
    {
        /* 取得項目の指定 */
        skey.setItemCodeCollect(); // 商品コード
        skey.setCollect(Item.ITEM_NAME); // 商品名称(商品マスタより取得)
        skey.setCollect(Item.ENTERING_QTY); // ケース入数(商品マスタより取得)
        skey.setCollect(Stock.STOCK_QTY, "SUM", null); // 在庫数
        skey.setCollect(Stock.ALLOCATION_QTY, "SUM", null); // 引当可能数
        skey.setCollect(Item.JAN); // JANコード(商品マスタより取得)
        skey.setCollect(Item.ITF); // ケースITF(商品マスタより取得)

        /* 集約条件 : 商品コードで集約 */
        skey.setItemCodeGroup(); // 商品コード
        skey.setGroup(Item.ITEM_NAME); // 商品名称
        skey.setGroup(Item.ENTERING_QTY); // ケース入数
        skey.setGroup(Item.JAN); // JANコード
        skey.setGroup(Item.ITF); // ケースITF

        /* ソート順 : 商品コード順 */
        skey.setItemCodeOrder(true);
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
