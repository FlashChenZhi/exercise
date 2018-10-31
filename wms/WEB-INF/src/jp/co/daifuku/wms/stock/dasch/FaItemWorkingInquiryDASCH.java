// $Id: FaItemWorkingInquiryDASCH.java 5085 2009-10-01 08:09:16Z okamura $
package jp.co.daifuku.wms.stock.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.stock.dasch.FaItemWorkingInquiryDASCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 商品別在庫照会に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 5085 $, $Date:: 2009-10-01 17:09:16 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okamura $
 */
public class FaItemWorkingInquiryDASCH
        extends AbstractWmsDASCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // 総括(商品コード別集計)
    private static final String COLLECT_ITEM = "0";

    // 総括(商品コード、ロットNo.別集計)
    private static final String COLLECT_ITEM_AND_LOT = "1";

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
    public FaItemWorkingInquiryDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // 在庫情報エンティティ配列の生成
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
            // 入庫日時
            p.set(STORAGE_DATETIME, stock.getStorageDate());
            // エリア
            p.set(AREA_NO, String.valueOf(stock.getValue(Stock.AREA_NO, "")));
            // 棚
            p.set(LOCATION_NO, stock.getLocationNo());
            // 在庫数
            p.set(TOTAL_STOCK_QTY, stock.getBigDecimal(Stock.STOCK_QTY, new BigDecimal(0)));
            // 棚状態
            p.set(LOCATION_STATUS, getLStatusName(stock));

            // 帳票必須項目
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());
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
        // 在庫情報ハンドラの生成
        StockHandler handler = new StockHandler(getConnection());

        // find count
        _total = handler.count(createSearchKey(p, false));

        // データ件数を返却
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

        // 取得件数分繰り返す
        for (Stock stock : stocks)
        {
            // 取得データのセット
            Params p = new Params();
            // No
            p.set(NO, String.valueOf(params.size() + 1 + start));
            // 商品コード
            p.set(ITEM_CODE, stock.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, (String)stock.getValue(Item.ITEM_NAME, ""));
            // ロットNo.
            p.set(LOT_NO, stock.getLotNo());
            // 入庫日時
            p.set(STORAGE_DATETIME, stock.getStorageDate());
            // エリア
            p.set(AREA_NO, String.valueOf(stock.getValue(Stock.AREA_NO, "")));
            // 棚
            p.set(LOCATION_NO, stock.getLocationNo());
            // 在庫数
            p.set(TOTAL_STOCK_QTY, stock.getBigDecimal(Stock.STOCK_QTY, new BigDecimal(0)));
            // 棚状態
            p.set(LOCATION_STATUS, getLStatusName(stock));

            // 生成したパラメータを配列に追加
            params.add(p);
        }
        // 生成したパラメータ配列を返却
        return params;
    }

    /**
     * AS/RS棚情報、パレット情報から棚の状態を取得します。
     * 
     * @param stock
     * @return 棚状態
     */
    protected String getLStatusName(Stock stock)
    {
        // 棚使用不可フラグ
        String prohibition = stock.getValue(Shelf.PROHIBITION_FLAG, "").toString();
        // AS/RS棚情報状態フラグ
        String shelfStatus = stock.getValue(Shelf.STATUS_FLAG, "").toString();
        // パレット情報状態フラグ
        String palletStatus = stock.getValue(Pallet.STATUS_FLAG, "").toString();
        // 空パレット状態フラグ
        String palletEmpty = stock.getValue(Pallet.EMPTY_FLAG, "").toString();

        // 禁止棚
        if (Shelf.PROHIBITION_FLAG_NG.equals(prohibition))
        {
            return DisplayText.getText("LBL-W0059");
        }
        else
        {
            // 空棚
            if (Shelf.LOCATION_STATUS_FLAG_EMPTY.equals(shelfStatus))
            {
                // パレット状態が出庫中、または出庫予約の場合
                if (Pallet.PALLET_STATUS_RETRIEVAL.equals(palletStatus)
                        || Pallet.PALLET_STATUS_RETRIEVAL_PLAN.equals(palletStatus)
                        || Pallet.PALLET_STATUS_STORAGE_PLAN.equals(palletStatus))
                {
                    // 作業棚
                    return DisplayText.getText("LBL-W0237");
                }
                else
                {
                    // 空棚
                    return DisplayText.getText("LBL-W0061");
                }
            }
            // 実棚
            else if (Shelf.LOCATION_STATUS_FLAG_STORAGED.equals(shelfStatus))
            {
                // 実棚でかつ禁止棚のときは禁止棚として表示
                if (Shelf.PROHIBITION_FLAG_NG.equals(prohibition))
                {
                    return DisplayText.getText("LBL-W0059");
                }
                // 出庫中(作業棚として表示)
                else if (Pallet.PALLET_STATUS_RETRIEVAL.equals(palletStatus)
                        || Pallet.PALLET_STATUS_RETRIEVAL_PLAN.equals(palletStatus)
                        || Pallet.PALLET_STATUS_STORAGE_PLAN.equals(palletStatus))
                {
                    return DisplayText.getText("LBL-W0237");
                }
                else
                {
                    // 空パレット棚
                    if (Pallet.EMPTY_FLAG_EMPTY.equals(palletEmpty))
                    {
                        return DisplayText.getText("LBL-W0456");
                    }
                    else
                    {
                        // 異常棚
                        if (Pallet.PALLET_STATUS_IRREGULAR.equals(palletStatus))
                        {
                            return DisplayText.getText("LBL-W0036");
                        }
                        // 実棚
                        else
                        {
                            return DisplayText.getText("LBL-W0104");
                        }
                    }
                }
            }
            // 予約棚(作業棚)
            else if (Shelf.LOCATION_STATUS_FLAG_RESERVATION.equals(shelfStatus))
            {
                return DisplayText.getText("LBL-W0237");
            }
        }
        // 該当しない場合は平置きなので実棚を返却
        return DisplayText.getText("LBL-W0104");
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
        StockSearchKey key = new StockSearchKey();

        // 検索条件、集約条件をセットする
        // エリアNo.
        String areano = param.getString(AREA_NO);
        if (!WmsParam.ALL_AREA_NO.equals(areano))
        {
            key.setAreaNo(areano);
        }
        // 商品コード
        String[] tmp = WmsFormatter.getFromTo(param.getString(FROM_ITEM_CODE), param.getString(TO_ITEM_CODE));
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

        // 結合条件
        // 荷主コード
        key.setJoin(Stock.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        // 商品コード
        key.setJoin(Stock.ITEM_CODE, Item.ITEM_CODE);
        // エリアNo.
        key.setJoin(Stock.AREA_NO, Area.AREA_NO);

        // 集約条件を取得
        String searchCondition = param.getString(SEARCH_CONDITION);

        // 総括(商品コード別集計)が選択された場合
        if (COLLECT_ITEM.equals(searchCondition))
        {
            // 集約条件
            // 商品コード
            key.setItemCodeGroup();
            // 商品名称
            key.setGroup(Item.ITEM_NAME);

            if (isSet)
            {
                // 取得項目
                // 商品コード
                key.setItemCodeCollect();
                // 商品名称
                key.setCollect(Item.ITEM_NAME);
                // 総在庫数
                key.setCollect(Stock.STOCK_QTY, "SUM", null);

                // 並び順
                // 商品コード(昇順)
                key.setItemCodeOrder(true);
            }
        }
        // 総括(商品コード、ロットNo.別集計)が選択された場合
        else if (COLLECT_ITEM_AND_LOT.equals(searchCondition))
        {
            // 集約条件
            // 商品コード
            key.setItemCodeGroup();
            // 商品名称
            key.setGroup(Item.ITEM_NAME);
            // ロットNo.
            key.setLotNoGroup();

            if (isSet)
            {
                // 取得項目
                // 商品コード
                key.setItemCodeCollect();
                // 商品名称
                key.setCollect(Item.ITEM_NAME);
                // ロットNo.
                key.setLotNoCollect();
                // 総在庫数
                key.setCollect(Stock.STOCK_QTY, "SUM", null);

                // 並び順
                // 商品コード(昇順)
                key.setItemCodeOrder(true);
                // ロットNo.(昇順)
                key.setLotNoOrder(true);
            }
        }
        // 明細(商品コード別)の場合
        else
        {
            // 検索条件
            // AS/RSの場合のみ条件に追加
            if (checkArea(param.getString(AREA_NO)))
            {
                // アクセス不可棚フラグ(アクセス可)
                key.setKey(Shelf.ACCESS_NG_FLAG, Shelf.ACCESS_NG_FLAG_OK);
            }

            // 結合条件
            // パレットID
            key.setJoin(Stock.PALLET_ID, "", Pallet.PALLET_ID, "(+)");
            // ステーションNo.
            key.setJoin(Pallet.CURRENT_STATION_NO, "", Shelf.STATION_NO, "(+)");

            // 集約条件
            // 商品コード
            key.setItemCodeGroup();
            // 商品名称
            key.setGroup(Item.ITEM_NAME);
            // ロットNo.
            key.setLotNoGroup();
            // 入庫日時
            key.setStorageDateGroup();
            // エリア
            key.setAreaNoGroup();
            // 棚
            key.setLocationNoGroup();
            // 棚状態
            key.setGroup(Pallet.STATUS_FLAG);
            key.setGroup(Pallet.EMPTY_FLAG);
            key.setGroup(Shelf.STATUS_FLAG);
            key.setGroup(Shelf.PROHIBITION_FLAG);

            if (isSet)
            {
                // 取得項目
                // 商品コード
                key.setItemCodeCollect();
                // 商品名称
                key.setCollect(Item.ITEM_NAME);
                // ロットNo.
                key.setLotNoCollect();
                // 入庫日時
                key.setStorageDateCollect();
                // エリア
                key.setAreaNoCollect();
                // 棚
                key.setLocationNoCollect();
                // 棚状態
                key.setCollect(Pallet.STATUS_FLAG);
                key.setCollect(Pallet.EMPTY_FLAG);
                key.setCollect(Shelf.STATUS_FLAG);
                key.setCollect(Shelf.PROHIBITION_FLAG);
                // 総在庫数
                key.setCollect(Stock.STOCK_QTY, "SUM", null);

                // 並び順
                // 商品コード(昇順)
                key.setItemCodeOrder(true);
                // ロットNo.(昇順)
                key.setLotNoOrder(true);
                // 入庫日時(昇順)
                key.setStorageDateOrder(true);
            }
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
