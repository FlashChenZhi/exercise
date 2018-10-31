package jp.co.daifuku.wms.stock.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.stock.dasch.LstRepLocationDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
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
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 補充候補検索に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:43:43 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class LstRepLocationDASCH
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
    public LstRepLocationDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new StockFinder(getConnection());
        // Initialize record counts
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
        // TODO : Implement for export
        //throw new ScheduleException("This method is not implemented.");
        // get Next entity from finder class
        Stock[] ents = (Stock[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (Stock ent : ents)
        {
            // エリア
            p.set(AREA_NO, ent.getAreaNo());
            // 棚
            p.set(LOCATION_NO, ent.getLocationNo());
            // ロットNo
            p.set(LOT_NO, ent.getLotNo());
            // ケース入数
            p.set(ENTERING_QTY, ent.getBigDecimal(Item.ENTERING_QTY).intValue());
            // 在庫ケース数
            p.set(CASE_QTY, DisplayUtil.getCaseQty(ent.getStockQty(), ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            // 在庫ピース数
            p.set(PIECE_QTY,
                    DisplayUtil.getPieceQty(ent.getStockQty(), ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            // JANｺｰﾄﾞ
            p.set(JAN, String.valueOf(ent.getValue(Item.JAN, "")));
            // ケースITF
            p.set(ITF, String.valueOf(ent.getValue(Item.ITF, "")));
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
        // TODO : Implement for export or listcell
        //throw new ScheduleException("This method is not implemented.");
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
        // TODO : Implement for listcell
        //throw new RuntimeException("This method is not implemented.");
        List<Params> params = new ArrayList<Params>();
        Stock[] ents = (Stock[])_finder.getEntities(start, start + cnt);

        for (Stock ent : ents)
        {
            Params p = new Params();
            p.set(SELECT, String.valueOf(params.size() + 1 + start));
            // エリア
            p.set(AREA_NO, ent.getAreaNo());
            // 棚
            p.set(LOCATION_NO, ent.getLocationNo());
            // ロットNo
            p.set(LOT_NO, ent.getLotNo());
            // ケース入数
            p.set(ENTERING_QTY, ent.getBigDecimal(Item.ENTERING_QTY).intValue());
            // 在庫ケース数
            p.set(CASE_QTY, DisplayUtil.getCaseQty(ent.getStockQty(), ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            // 在庫ピース数
            p.set(PIECE_QTY,
                    DisplayUtil.getPieceQty(ent.getStockQty(), ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            // JANｺｰﾄﾞ
            p.set(JAN, String.valueOf(ent.getValue(Item.JAN, "")));
            // ケースITF
            p.set(ITF, String.valueOf(ent.getValue(Item.ITF, "")));

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
        // 商品コード
        if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
        {
            key.setItemCode(param.getString(ITEM_CODE));
        }
        // ロットNo.
        if (!StringUtil.isBlank(param.getString(LOT_NO)))
        {
            key.setLotNo(param.getString(LOT_NO));
        }
        // エリア
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            key.setAreaNo(param.getString(AREA_NO));
        }
        // 棚
        if (!StringUtil.isBlank(param.getString(LOCATION_NO)))
        {
            key.setLocationNo(param.getString(LOCATION_NO), ">=");
        }

        /* 結合条件の指定 */

        // 荷主コード
        key.setJoin(Stock.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        // 商品コード
        key.setJoin(Stock.ITEM_CODE, Item.ITEM_CODE);

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            // 表示順
            key.setAreaNoOrder(true);
            key.setLocationNoOrder(true);
            key.setLotNoOrder(true);

            // 取得項目
            // エリアNo.
            key.setAreaNoCollect();
            // 棚No.
            key.setLocationNoCollect();
            // ロットNo.
            key.setLotNoCollect();
            // DMITEM.ケース入数
            key.setCollect(Item.ENTERING_QTY);
            // 在庫数
            key.setStockQtyCollect();
            // DMITEM.JANコード
            key.setCollect(Item.JAN);
            // DMITEM.ケースITF
            key.setCollect(Item.ITF);
        }

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
