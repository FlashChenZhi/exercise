// $Id: LstAsStockDetailDASCH.java 5092 2009-10-02 02:13:32Z kishimoto $
package jp.co.daifuku.wms.asrs.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.dasch.LstAsStockDetailDASCHParams.*;

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
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.SoftZone;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * AS/RS 棚明細一覧（ボタンあり）に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 5092 $, $Date: 2009-10-02 11:13:32 +0900 (金, 02 10 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class LstAsStockDetailDASCH
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
     * @param locale ロケール
     * @param ui ユーザ情報
     */
    public LstAsStockDetailDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // 在庫情報ファインダー
        _finder = new StockFinder(getConnection());
        _finder.open(isForwardOnly());

        // 生成した検索キーでDB検索
        _finder.search(createSearchKey(p, true));

        // 現在点を初期化
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
        // 次データへ移動
        _current++;

        // 全行数が現在行数より大きい場合はtrueを返却
        // 上記以外はfalseを返却
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
        // 在庫情報エンティティ
        Stock[] ents = (Stock[])_finder.getEntities(1);
        // パラメータ配列の生成
        Params p = new Params();

        // 件数分、繰り返す
        for (Stock ent : ents)
        {
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME, ""));
            // ロットNo.
            p.set(LOT_NO, ent.getLotNo());
            // ケース入数
            p.set(ENTERING_QTY, ent.getBigDecimal(Item.ENTERING_QTY).intValue());
            // 在庫数の取得
            int storageQty = ent.getStockQty();
            // 在庫ケース数
            p.set(STOCK_CASE_QTY, DisplayUtil.getCaseQty(storageQty, ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            // 在庫ピース数
            p.set(STOCK_PIECE_QTY, DisplayUtil.getPieceQty(storageQty, ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            // 入庫日
            p.set(STORAGE_DATE, ent.getStorageDate());
            // 入庫時刻
            p.set(STORAGE_TIME, ent.getStorageDate());
            // JANコード
            p.set(JAN, ent.getValue(Item.JAN, ""));
            // ケースITF
            p.set(ITF, ent.getValue(Item.ITF, ""));
            // 在庫ID
            p.set(STOCK_ID, ent.getStockId());
            // 最終更新日時
            p.set(LAST_UPDATE_DATE, ent.getLastUpdateDate());
            // ソフトゾーン名称
            p.set(SOFT_ZONE_NAME, ent.getValue(SoftZone.SOFT_ZONE_NAME, ""));

            // 一件のみ取得のため、処理抜け
            break;
        }
        // 生成したパラメータを返却
        return p;
    }

    /**
     * 生成したファインダーとコネクションの破棄処理<BR>
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
        // 在庫情報データベースハンドラ
        StockHandler handler = new StockHandler(getConnection());

        // 対象データの件数を取得
        _total = handler.count(createSearchKey(p, false));

        // 取得した件数を初期化
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
        // 在庫情報エンティティ配列
        Stock[] ents = (Stock[])_finder.getEntities(start, start + cnt);
        // 返却パラメータの生成
        List<Params> params = new ArrayList<Params>();

        // 件数分、繰り返す
        for (Stock ent : ents)
        {
            // パラメータの生成
            Params p = new Params();

            // 選択ボタン
            p.set(SELECT, String.valueOf(params.size() + 1 + start));
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME, ""));
            // ロットNo.
            p.set(LOT_NO, ent.getLotNo());
            // ケース入数
            p.set(ENTERING_QTY, ent.getBigDecimal(Item.ENTERING_QTY).intValue());
            // 在庫数の取得
            int storageQty = ent.getStockQty();
            // 在庫ケース数
            p.set(STOCK_CASE_QTY, DisplayUtil.getCaseQty(storageQty, ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            // 在庫ピース数
            p.set(STOCK_PIECE_QTY, DisplayUtil.getPieceQty(storageQty, ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            // 入庫日
            p.set(STORAGE_DATE, ent.getStorageDate());
            // 入庫時刻
            p.set(STORAGE_TIME, ent.getStorageDate());
            // JANコード
            p.set(JAN, ent.getValue(Item.JAN, ""));
            // ケースITF
            p.set(ITF, ent.getValue(Item.ITF, ""));
            // 在庫ID
            p.set(STOCK_ID, ent.getStockId());
            // 最終更新日時
            p.set(LAST_UPDATE_DATE, ent.getLastUpdateDate());
            // ソフトゾーン名称
            p.set(SOFT_ZONE_NAME, ent.getValue(SoftZone.SOFT_ZONE_NAME, ""));

            // 生成したパラメータをパラメータ配列に格納
            params.add(p);
        }
        // 生成したパラメータ配列を返却
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
        // 在庫情報検索キー
        StockSearchKey skey = new StockSearchKey();
        // AS/RSパレット情報検索キー
        PalletSearchKey pkey = new PalletSearchKey();

        // エリアマスタ情報コントローラー
        AreaController areacon = new AreaController(getConnection(), getClass());
        // 在庫情報形式の棚をAS/RS棚情報形式の棚に変換
        String location = areacon.toAsrsLocation(param.getString(AREA_NO), param.getString(LOCATION_NO));

        if (StringUtil.isBlank(param.getString(PALLET_ID)))
        {
            // 副問合せ用(AS/RSパレット情報)
            pkey.setCurrentStationNo(location);
            pkey.setPalletIdCollect();
            // 在庫情報.パレットID(AS/RSパレット情報のパレットIDと同一)
            skey.setKey(Stock.PALLET_ID, pkey);

            // 既に空棚となっているデータは除外して検索します（副問合せ）
            // AS/RS搬送情報コントローラー
            CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
            // AS/RS搬送情報検索キー
            CarryInfoSearchKey carryKey = carryControl.getEmptyShelfPallet();
            // 在庫情報.パレットID(AS/RS搬送情報のパレットIDと異なる)
            skey.setKey(Stock.PALLET_ID, carryKey, "!=", "", "", true);

            // エリアNo.
            if (!StringUtil.isBlank(param.getString(AREA_NO)))
            {
                // 在庫情報.エリアNo.(エリアNo.)
                skey.setAreaNo(param.getString(AREA_NO));
            }
        }
        else
        {
            // 在庫情報.パレットID(パレットID)
            skey.setPalletId(param.getString(PALLET_ID));
            // AS/RSパレット情報.パレットID(在庫情報.パレットID)
            skey.setJoin(Pallet.PALLET_ID, Stock.PALLET_ID);
        }

        // テーブル結合(名称、存在確認)
        // 在庫情報.商品コードと商品マスタ.商品コードが同一
        skey.setJoin(Stock.ITEM_CODE, Item.ITEM_CODE);
        // 在庫情報.荷主コードと商品マスタ.荷主コードが同一
        skey.setJoin(Stock.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        // 商品マスタ.ソフトゾーンIDとソフトゾーンマスタ.ソフトゾーンIDが同一
        skey.setJoin(Item.SOFT_ZONE_ID,"", SoftZone.SOFT_ZONE_ID,"(+)");

        // 件数確認ではない場合
        if (isSet)
        {
            // 取得キーの設定
            // 在庫情報.商品コード
            skey.setItemCodeCollect();
            // 商品マスタ.商品名称
            skey.setCollect(Item.ITEM_NAME);
            // 在庫情報.ロットNo.
            skey.setLotNoCollect();
            // ソフトゾーンマスタ.ソフトゾーン名称
            skey.setCollect(SoftZone.SOFT_ZONE_NAME);
            // 商品マスタ.ケース入数
            skey.setCollect(Item.ENTERING_QTY);
            // 在庫情報.在庫数
            skey.setStockQtyCollect();
            // 在庫情報.入庫日時
            skey.setStorageDateCollect();
            // 商品マスタ.JANコード
            skey.setCollect(Item.JAN);
            // 在庫情報.ケースITF
            skey.setCollect(Item.ITF);
            // 在庫情報.在庫ID
            skey.setStockIdCollect();
            // 在庫情報.最終更新日時
            skey.setLastUpdateDateCollect();

            // ソートキーの設定
            // 在庫情報.商品コード(昇順)
            skey.setItemCodeOrder(true);
            // 在庫情報.ロットNo.(昇順)
            skey.setLotNoOrder(true);
        }
        // 生成した検索キーを返却
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
