// $Id: DeadStockInquiryDASCH.java 4967 2009-09-02 06:00:56Z shibamoto $
package jp.co.daifuku.wms.stock.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.stock.dasch.DeadStockInquiryDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 長期滞留品在庫照会に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 4967 $, $Date: 2009-09-02 15:00:56 +0900 (水, 02 9 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class DeadStockInquiryDASCH
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
    public DeadStockInquiryDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // get Next entity from finder class
        Stock[] stocks = (Stock[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (Stock stock : stocks)
        {
            // 取得データのセット
            // 最終出庫日
            p.set(RETRIEVAL_DAY, WmsFormatter.toDate(stock.getRetrievalDay()));
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
        // return Pram objstc
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
            // 最終出庫日
            p.set(RETRIEVAL_DAY, WmsFormatter.toDate(stock.getRetrievalDay()));
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
            throws CommonException
    {
        StockSearchKey key = new StockSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        // 入力された日に1日追加したDate型を作成
        String retrieval_day = WmsFormatter.toParamDate(param.getDate(LAST_RETRIEVAL_DATE));
        Calendar curDate = Calendar.getInstance();
        curDate.setTime(WmsFormatter.toDate(retrieval_day));

        // 次の式は、括弧が無いが、ORより、ANDの方が優先なので、とりあえず、問題ない。
        // 出庫最終日 < '入力値'  OR  出庫最終日 IS NULL  AND  入庫日時 < {(日付型)'入力値'}
        key.setRetrievalDay(retrieval_day, "<", "(", "", false);
        key.setRetrievalDay("");
        key.setStorageDate(curDate.getTime(), "<", "", ")", true);
        key.setStockQty(0, ">");
        if(!StringUtil.isBlank(param.getString(CONSIGNOR_CODE)))
        {
        	key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        }

        /* 結合条件の指定 */

        // 荷主コード
        key.setJoin(Stock.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        // 商品コード
        key.setJoin(Stock.ITEM_CODE, Item.ITEM_CODE);

        /* 取得項目の指定 */

        // 在庫ID 
        key.setStockIdCollect();
        // 最終出庫日
        key.setRetrievalDayCollect();
        // 商品コード
        key.setItemCodeCollect();
        // 商品名称
        key.setCollect(Item.ITEM_NAME);
        // ロットNo.
        key.setLotNoCollect();
        // エリア
        key.setAreaNoCollect();
        // 棚
        key.setLocationNoCollect();
        // ケース入数
        key.setCollect(Item.ENTERING_QTY);
        // 在庫数
        key.setStockQtyCollect();
        // JANコード
        key.setCollect(Item.JAN);
        // ケースITF
        key.setCollect(Item.ITF);

        /* ソート順の指定 */

        // 最終出庫日
        key.setRetrievalDayOrder(true);
        // 商品コード
        key.setItemCodeOrder(true);
        // ロットNo.
        key.setLotNoOrder(true);
        // エリア
        key.setAreaNoOrder(true);
        // 棚
        key.setLocationNoOrder(true);

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
