package jp.co.daifuku.wms.asrs.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.dasch.LstAsLocationStockDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.CarryInfoController;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 棚別在庫一覧に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:02:41 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class LstAsLocationStockDASCH
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
    public LstAsLocationStockDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        AreaController area = new AreaController(getConnection(), this.getClass());
        // conver Entity to Param object
        for (Stock ent : ents)
        {
            p.set(AREA_NO, String.valueOf(ent.getValue(Area.AREA_NO, "")));
            String style = area.getLocationStyle(String.valueOf(ent.getValue(Area.AREA_NO, "")));
            p.set(LOCATION_NO, WmsFormatter.toDispLocation(String.valueOf(ent.getLocationNo()), style));
            p.set(ITEM_CODE, String.valueOf(ent.getItemCode()));
            p.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
            p.set(LOT_NO, String.valueOf(ent.getLotNo()));
            p.set(ENTERING_QTY, WmsFormatter.getInt(String.valueOf(ent.getValue(Item.ENTERING_QTY))));
            if (WmsFormatter.getInt(String.valueOf(ent.getValue(Item.ENTERING_QTY))) == 0)
            {
                p.set(STOCK_CASE_QTY, 0);
            }
            else
            {
                p.set(STOCK_CASE_QTY, WmsFormatter.getInt(String.valueOf(ent.getStockQty()))
                        / WmsFormatter.getInt(String.valueOf(ent.getValue(Item.ENTERING_QTY))));
            }

            if (WmsFormatter.getInt(String.valueOf(ent.getValue(Item.ENTERING_QTY))) == 0)
            {
                p.set(STOCK_PIECE_QTY, WmsFormatter.getInt(String.valueOf(ent.getStockQty())));
            }
            else
            {
                p.set(STOCK_PIECE_QTY, WmsFormatter.getInt(String.valueOf(ent.getStockQty()))
                        % WmsFormatter.getInt(String.valueOf(ent.getValue(Item.ENTERING_QTY))));
            }
            p.set(STORAGE_DATE, WmsFormatter.toDate(WmsFormatter.toParamDate(ent.getStorageDate())));
            p.set(STORAGE_TIME, WmsFormatter.toTime(WmsFormatter.toParamTime(ent.getStorageDate())));
            p.set(JAN, String.valueOf(ent.getValue(Item.JAN, "")));
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
        AreaController area = new AreaController(getConnection(), this.getClass());

        for (Stock ent : ents)
        {
            Params p = new Params();

            p.set(SELECT, String.valueOf(params.size() + 1 + start));
            p.set(AREA_NO, String.valueOf(ent.getValue(Area.AREA_NO, "")));
            String style = area.getLocationStyle(String.valueOf(ent.getValue(Area.AREA_NO, "")));
            p.set(LOCATION_NO, WmsFormatter.toDispLocation(String.valueOf(ent.getLocationNo()), style));
            p.set(ITEM_CODE, String.valueOf(ent.getItemCode()));
            p.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
            p.set(LOT_NO, String.valueOf(ent.getLotNo()));
            p.set(ENTERING_QTY, WmsFormatter.getInt(String.valueOf(ent.getValue(Item.ENTERING_QTY))));
            if (WmsFormatter.getInt(String.valueOf(ent.getValue(Item.ENTERING_QTY))) == 0)
            {
                p.set(STOCK_CASE_QTY, 0);
            }
            else
            {
                p.set(STOCK_CASE_QTY, WmsFormatter.getInt(String.valueOf(ent.getStockQty()))
                        / WmsFormatter.getInt(String.valueOf(ent.getValue(Item.ENTERING_QTY))));
            }

            if (WmsFormatter.getInt(String.valueOf(ent.getValue(Item.ENTERING_QTY))) == 0)
            {
                p.set(STOCK_PIECE_QTY, WmsFormatter.getInt(String.valueOf(ent.getStockQty())));
            }
            else
            {
                p.set(STOCK_PIECE_QTY, WmsFormatter.getInt(String.valueOf(ent.getStockQty()))
                        % WmsFormatter.getInt(String.valueOf(ent.getValue(Item.ENTERING_QTY))));
            }
            p.set(STORAGE_DATE, WmsFormatter.toDate(WmsFormatter.toParamDate(ent.getStorageDate())));
            p.set(STORAGE_TIME, WmsFormatter.toTime(WmsFormatter.toParamTime(ent.getStorageDate())));
            p.set(JAN, String.valueOf(ent.getValue(Item.JAN, "")));
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
        // 検索条件
        // エリアNo.
        key.setKey(Area.AREA_NO, param.getString(AREA_NO));

        // 商品コード
        if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
        {
            key.setItemCode(param.getString(ITEM_CODE));
        }
        // 荷主コード
        if (!StringUtil.isBlank(param.getString(CONSIGNOR_CODE)))
        {
            key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        }

        // 既に空棚となっているデータは除外して検索します（副問い合わせ）
        CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
        CarryInfoSearchKey carryKey = carryControl.getEmptyShelfPallet();
        key.setKey(Stock.PALLET_ID, carryKey, "!=", "", "", true);

        // 結合条件
        key.setJoin(Stock.AREA_NO, Area.AREA_NO);
        key.setJoin(Stock.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        key.setJoin(Stock.ITEM_CODE, Item.ITEM_CODE);

        key.setGroup(Area.AREA_NO);
        key.setGroup(Area.AREA_NAME);
        key.setLocationNoGroup();
        key.setItemCodeGroup();
        key.setGroup(Item.ITEM_NAME);
        key.setLotNoGroup();
        key.setGroup(Item.ENTERING_QTY);
        key.setStorageDateGroup();
        key.setGroup(Item.JAN);
        key.setGroup(Item.ITF);

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            // ソート順
            key.setLocationNoOrder(true);
            key.setItemCodeOrder(true);
            key.setLotNoOrder(true);

            // 取得項目
            key.setCollect(Area.AREA_NO);
            key.setCollect(Area.AREA_NAME);
            key.setLocationNoCollect();
            key.setItemCodeCollect();
            key.setCollect(Item.ITEM_NAME);
            key.setLotNoCollect();
            key.setCollect(Item.ENTERING_QTY);
            key.setStockQtyCollect("SUM");
            key.setStorageDateCollect();
            key.setCollect(Item.JAN);
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
