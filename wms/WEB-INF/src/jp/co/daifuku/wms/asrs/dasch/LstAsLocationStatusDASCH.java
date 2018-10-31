// $Id: LstAsLocationStatusDASCH.java 4804 2009-08-10 06:26:35Z shibamoto $
package jp.co.daifuku.wms.asrs.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.dasch.LstAsLocationStatusDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.asrs.schedule.AsrsInParameter;
import jp.co.daifuku.wms.base.dbhandler.ShelfFinder;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.AsrsStock;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 棚別状態一覧に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 4804 $, $Date: 2009-08-10 15:26:35 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstAsLocationStatusDASCH
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
    public LstAsLocationStatusDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // 状態の取得
        // 棚使用(空棚)
        boolean emptyLocationFlag = p.getBoolean(PROHIBITION_EMPTY);
        // 棚使用(実棚)
        boolean storedLocationFlag = p.getBoolean(PROHIBITION_STORAGED);
        // 棚使用(空PB棚)
        boolean emptyPBLocationFlag = p.getBoolean(PROHIBITION_EMPTY_PALLET);
        // 棚使用(異常棚)
        boolean errorLocationFlag = p.getBoolean(PROHIBITION_ERROR);

        // 状態の指定が無い場合は検索をせずに終了
        if (!emptyLocationFlag && !storedLocationFlag && !emptyPBLocationFlag && !errorLocationFlag)
        {
            return;
        }

        // AS/RS棚情報ファインダー
        _finder = new ShelfFinder(getConnection());
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
        // AS/RS棚情報エンティティ配列
        Shelf[] ents = (Shelf[])_finder.getEntities(1);
        // パラメータの生成
        Params p = new Params();

        // 件数分、繰り返す
        for (Shelf ent : ents)
        {
            // エリアNo.
            p.set(AREA_NO, ent.getValue(Area.AREA_NO, ""));
            // エリア名称
            p.set(AREA_NAME, ent.getValue(Area.AREA_NAME, ""));
            // ステーションNo.
            p.set(STATION_NO, ent.getStationNo());
            // アクセス不可棚フラグ
            p.set(ACCESS_NG_FLAG, ent.getAccessNgFlag());
            // 商品コード
            p.set(ITEM_CODE, ent.getValue(AsrsStock.ITEM_CODE, ""));
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(AsrsStock.ITEM_NAME, ""));
            // ロットNo.
            p.set(LOT_NO, ent.getValue(AsrsStock.LOT_NO, ""));

            // ケース入数、在庫ケース数、在庫ピース数の設定
            if (null != ent.getValue(AsrsStock.ENTERING_QTY))
            {
                // ケース入数
                p.set(ENTERING_QTY, WmsFormatter.getInt(String.valueOf(ent.getValue(AsrsStock.ENTERING_QTY))));

                if (WmsFormatter.getInt(String.valueOf(ent.getValue(AsrsStock.ENTERING_QTY))) == 0)
                {
                    // 在庫ケース数
                    p.set(STOCK_CASE_QTY, 0);
                    // 在庫ピース数
                    p.set(STOCK_PIECE_QTY, WmsFormatter.getInt(String.valueOf(ent.getValue(AsrsStock.STOCK_QTY))));
                }
                else
                {
                    // 在庫ケース数
                    p.set(STOCK_CASE_QTY, WmsFormatter.getInt(String.valueOf(ent.getValue(AsrsStock.STOCK_QTY)))
                            / WmsFormatter.getInt(String.valueOf(ent.getValue(AsrsStock.ENTERING_QTY))));
                    // 在庫ピース数
                    p.set(STOCK_PIECE_QTY, WmsFormatter.getInt(String.valueOf(ent.getValue(AsrsStock.STOCK_QTY)))
                            % WmsFormatter.getInt(String.valueOf(ent.getValue(AsrsStock.ENTERING_QTY))));
                }
            }
            // 入庫日
            p.set(STORAGE_DATE, ent.getDate(AsrsStock.STORAGE_DATE));
            // 入庫時刻
            p.set(STORAGE_TIME, ent.getDate(AsrsStock.STORAGE_DATE));
            // JANコード
            p.set(JAN, ent.getValue(AsrsStock.JAN, ""));
            // ケースITF
            p.set(ITF, ent.getValue(AsrsStock.ITF, ""));
            // 棚状態
            p.set(LOCATION_STATUS, setStatusFlag(ent));

            // 一件のみ取得のため、処理抜け
            break;
        }
        // 生成したパラメータ配列を返却
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
        // 状態の取得
        // 棚使用(空棚)
        boolean emptyLocationFlag = p.getBoolean(PROHIBITION_EMPTY);
        // 棚使用(実棚)
        boolean storedLocationFlag = p.getBoolean(PROHIBITION_STORAGED);
        // 棚使用(空PB棚)
        boolean emptyPBLocationFlag = p.getBoolean(PROHIBITION_EMPTY_PALLET);
        // 棚使用(異常棚)
        boolean errorLocationFlag = p.getBoolean(PROHIBITION_ERROR);

        // 状態の指定が無い場合は検索をせずに終了
        if (!emptyLocationFlag && !storedLocationFlag && !emptyPBLocationFlag && !errorLocationFlag)
        {
            return 0;
        }
        
        // AS/RS棚情報データベースハンドラ
        ShelfHandler handler = new ShelfHandler(getConnection());

        // 対象のデータ件数を取得
        _total = handler.count(createSearchKey(p, false));

        // 取得したデータ件数を返却
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
        // AS/RS棚情報エンティティ配列の生成
        Shelf[] ents = (Shelf[])_finder.getEntities(start, start + cnt);
        // 返却パラメータの生成
        List<Params> params = new ArrayList<Params>();

        // 件数分、繰り返す
        for (Shelf ent : ents)
        {
            // パラメータの生成
            Params p = new Params();

            // 選択ボタン
            p.set(SELECT, String.valueOf(params.size() + 1 + start));
            // エリアNo.
            p.set(AREA_NO, ent.getValue(Area.AREA_NO, ""));
            // エリア名称
            p.set(AREA_NAME, ent.getValue(Area.AREA_NAME, ""));
            // ステーションNo.
            p.set(STATION_NO, ent.getStationNo());
            // アクセス不可棚フラグ
            p.set(ACCESS_NG_FLAG, ent.getAccessNgFlag());
            // 商品コード
            p.set(ITEM_CODE, ent.getValue(AsrsStock.ITEM_CODE, ""));
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(AsrsStock.ITEM_NAME, ""));
            // ロットNo.
            p.set(LOT_NO, ent.getValue(AsrsStock.LOT_NO, ""));

            // ケース入数、在庫ケース数、在庫ピース数の設定
            if (null != ent.getValue(AsrsStock.ENTERING_QTY))
            {
                // ケース入数
                p.set(ENTERING_QTY, WmsFormatter.getInt(String.valueOf(ent.getValue(AsrsStock.ENTERING_QTY))));

                if (WmsFormatter.getInt(String.valueOf(ent.getValue(AsrsStock.ENTERING_QTY))) == 0)
                {
                    // 在庫ケース数
                    p.set(STOCK_CASE_QTY, 0);
                    // 在庫ピース数
                    p.set(STOCK_PIECE_QTY, WmsFormatter.getInt(String.valueOf(ent.getValue(AsrsStock.STOCK_QTY))));
                }
                else
                {
                    // 在庫ケース数
                    p.set(STOCK_CASE_QTY, WmsFormatter.getInt(String.valueOf(ent.getValue(AsrsStock.STOCK_QTY)))
                            / WmsFormatter.getInt(String.valueOf(ent.getValue(AsrsStock.ENTERING_QTY))));
                    // 在庫ピース数
                    p.set(STOCK_PIECE_QTY, WmsFormatter.getInt(String.valueOf(ent.getValue(AsrsStock.STOCK_QTY)))
                            % WmsFormatter.getInt(String.valueOf(ent.getValue(AsrsStock.ENTERING_QTY))));
                }
            }
            // 入庫日
            p.set(STORAGE_DATE, ent.getDate(AsrsStock.STORAGE_DATE));
            // 入庫時刻
            p.set(STORAGE_TIME, ent.getDate(AsrsStock.STORAGE_DATE));
            // JANコード
            p.set(JAN, ent.getValue(AsrsStock.JAN, ""));
            // ケースITF
            p.set(ITF, ent.getValue(AsrsStock.ITF, ""));
            // 棚状態
            p.set(LOCATION_STATUS, setStatusFlag(ent));

            // 設定したパラメータをパラメータ配列に格納
            params.add(p);
        }
        // 設定したパラメータ配列を返却
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
        // AS/RS棚情報検索キー
        ShelfSearchKey sKey = new ShelfSearchKey();

        // 結合条件
        // エリアマスタ.倉庫ステーションNo.(AS/RS棚情報.倉庫ステーションNo.)
        sKey.setJoin(Area.WHSTATION_NO, Shelf.WH_STATION_NO);
        // AS/RS棚情報.ステーションNo.(AS/RS在庫情報.現在ステーションNo.)[外部結合]
        sKey.setJoin(Shelf.STATION_NO, "", AsrsStock.CURRENT_STATION_NO, "(+)");

        // 検索キーの設定
        sKey.setKey(Area.AREA_NO, param.getString(AREA_NO));

        // 指定された状態をセット
        setSearchStatus(sKey, param);

        if (isSet)
        {
            // 取得キーの設定
            // エリアマスタ.エリアNo.
            sKey.setCollect(Area.AREA_NO);
            // エリアマスタ.エリア名称
            sKey.setCollect(Area.AREA_NAME);
            // AS/RS棚情報.ステーションNo.
            sKey.setStationNoCollect();
            // AS/RS棚情報.状態
            sKey.setProhibitionFlagCollect();
            // AS/RS棚情報.アクセス不可棚フラグ
            sKey.setAccessNgFlagCollect();
            // AS/RS棚情報.棚状態
            sKey.setStatusFlagCollect();
            // AS/RS在庫情報.ロケーションNo.
            sKey.setCollect(AsrsStock.LOCATION_NO);
            // AS/RS在庫情報.空パレット状態
            sKey.setCollect(AsrsStock.EMPTY_FLAG);
            // AS/RS在庫情報.パレット在庫状態
            sKey.setCollect(AsrsStock.PALLET_STATUS_FLAG);
            // AS/RS在庫情報.商品コード
            sKey.setCollect(AsrsStock.ITEM_CODE);
            // AS/RS在庫情報.商品名称
            sKey.setCollect(AsrsStock.ITEM_NAME);
            // AS/RS在庫情報.ロットNo.
            sKey.setCollect(AsrsStock.LOT_NO);
            // AS/RS在庫情報.ケース入数
            sKey.setCollect(AsrsStock.ENTERING_QTY);
            // AS/RS在庫情報.在庫数
            sKey.setCollect(AsrsStock.STOCK_QTY);
            // AS/RS在庫情報.入庫日時
            sKey.setCollect(AsrsStock.STORAGE_DATE);
            // AS/RS在庫情報.JANコード
            sKey.setCollect(AsrsStock.JAN);
            // AS/RS在庫情報.ケースITF
            sKey.setCollect(AsrsStock.ITF);

            // ソートキーの設定
            // AS/RS棚情報.ステーションNo.(昇順)
            sKey.setOrder(Shelf.STATION_NO, true);
            // AS/RS在庫情報.商品コード(昇順)
            sKey.setOrder(AsrsStock.ITEM_CODE, true);
            // AS/RS在庫情報.ロットNo.(昇順)
            sKey.setOrder(AsrsStock.LOT_NO, true);
        }
        // 生成した検索キーを返却
        return sKey;
    }

    /**
     * 指定された棚状態をsearchkeyにセットします。<BR>
     * @param sKey 条件をセットするサーチキー
     * @param inputParam 検索条件パラメータ
     */
    private void setSearchStatus(ShelfSearchKey sKey, Params param)
    {
        // 棚使用(空棚)にチェックがある場合
        if (param.getBoolean(PROHIBITION_EMPTY))
        {
            // 棚使用(空棚) + 棚使用(実棚) + 棚使用(空PB棚)
            if (param.getBoolean(PROHIBITION_STORAGED) && param.getBoolean(PROHIBITION_EMPTY_PALLET))
            {
                // AS/RS棚情報.棚状態(空棚)
                sKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY, "=", "(", "", false);
                // AS/RS棚情報.棚状態(実棚)
                sKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_STORAGED, "=", "((", "", false);
                // AS/RS在庫情報.空パレット状態(空パレット)
                sKey.setKey(AsrsStock.EMPTY_FLAG, AsrsStock.EMPTY_FLAG_EMPTY, "=", "", ")", true);
                // AS/RS在庫情報.パレット在庫状態(実棚)
                sKey.setKey(AsrsStock.PALLET_STATUS_FLAG, AsrsStock.PALLET_STATUS_REGULAR, "=", "(", "", true);
                // AS/RS在庫情報.在庫数
                sKey.setKey(AsrsStock.STOCK_QTY, "0", ">", "", ")))", true);
            }
            // 棚使用(空棚) + 棚使用(実棚)
            else if (param.getBoolean(PROHIBITION_STORAGED))
            {
                // AS/RS棚情報.棚状態(空棚)
                sKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY, "=", "(", "", false);
                // AS/RS棚情報.棚状態(実棚)
                sKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_STORAGED, "=", "(", "", true);
                // AS/RS在庫情報.パレット在庫状態(実棚)
                sKey.setKey(AsrsStock.PALLET_STATUS_FLAG, AsrsStock.PALLET_STATUS_REGULAR, "=", "", "", true);
                // AS/RS在庫情報.在庫数(0以上)
                sKey.setKey(AsrsStock.STOCK_QTY, "0", ">", "", "))", true);
            }
            // 棚使用(空棚) + 棚使用(空PB棚)
            else if (param.getBoolean(PROHIBITION_EMPTY_PALLET))
            {
                // AS/RS棚情報.棚状態(空棚)
                sKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY, "=", "(", "", false);
                // AS/RS棚情報.空パレット状態(空パレット)
                sKey.setKey(AsrsStock.EMPTY_FLAG, AsrsStock.EMPTY_FLAG_EMPTY, "=", "(", "", true);
                // AS/RS在庫情報.パレット在庫状態(実棚)
                sKey.setKey(AsrsStock.PALLET_STATUS_FLAG, AsrsStock.PALLET_STATUS_REGULAR, "=", "", "", true);
                // AS/RS在庫情報.在庫数(0以上)
                sKey.setKey(AsrsStock.STOCK_QTY, "0", ">", "", "))", true);
            }
            // 棚使用(空棚)のみ
            else
            {
                // AS/RS棚情報.棚状態(空棚)
                sKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
            }
        }
        // 棚使用(異常棚)にチェックがある場合
        else if (param.getBoolean(PROHIBITION_ERROR))
        {
            // 棚使用(異常棚) + 棚使用(実棚) + 棚使用(空PB棚)
            if (param.getBoolean(PROHIBITION_STORAGED) && param.getBoolean(PROHIBITION_EMPTY_PALLET))
            {
                // AS/RS棚情報.棚状態(実棚)
                sKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_STORAGED, "=", "((", "", false);
                // AS/RS在庫情報.パレット在庫状態(異常)
                sKey.setKey(AsrsStock.PALLET_STATUS_FLAG, SystemDefine.PALLET_STATUS_IRREGULAR, "=", "(", "", false);
                // AS/RS在庫情報.空パレット状態(空パレット状態)
                sKey.setKey(AsrsStock.EMPTY_FLAG, AsrsStock.EMPTY_FLAG_EMPTY, "=", "", ")", true);
                // AS/RS在庫情報.パレット在庫状態(実棚)
                sKey.setKey(AsrsStock.PALLET_STATUS_FLAG, AsrsStock.PALLET_STATUS_REGULAR, "=", "", "))", true);
                // AS/RS在庫情報.在庫数(0以上)
                sKey.setKey(AsrsStock.STOCK_QTY, "0", ">", "", "", true);
            }
            // 棚使用(異常棚) + 棚使用(実棚)
            else if (param.getBoolean(PROHIBITION_STORAGED))
            {
                // AS/RS棚情報.棚状態(実棚)
                sKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_STORAGED, "=", "(", "", true);
                // AS/RS在庫情報.パレット在庫状態(異常)
                sKey.setKey(AsrsStock.PALLET_STATUS_FLAG, SystemDefine.PALLET_STATUS_IRREGULAR, "=", "(", "", false);
                // AS/RS在庫情報.パレット在庫状態(実棚)
                sKey.setKey(AsrsStock.PALLET_STATUS_FLAG, AsrsStock.PALLET_STATUS_REGULAR, "=", "", "))", true);
                // AS/RS在庫情報.在庫数(0以上)
                sKey.setKey(AsrsStock.STOCK_QTY, "0", ">", "", "", true);
            }
            // 棚使用(異常棚) + 棚使用(空PB棚)
            else if (param.getBoolean(PROHIBITION_EMPTY_PALLET))
            {
                // AS/RS在庫情報.パレット在庫状態(異常棚)
                sKey.setKey(AsrsStock.PALLET_STATUS_FLAG, SystemDefine.PALLET_STATUS_IRREGULAR, "=", "(", "", false);
                // AS/RS在庫情報.空パレット状態(空パレット)
                sKey.setKey(AsrsStock.EMPTY_FLAG, AsrsStock.EMPTY_FLAG_EMPTY, "=", "(", "", true);
                // AS/RS在庫情報.パレット在庫状態(実棚)
                sKey.setKey(AsrsStock.PALLET_STATUS_FLAG, AsrsStock.PALLET_STATUS_REGULAR, "=", "", "))", true);
                // AS/RS在庫情報.在庫数(0以上)
                sKey.setKey(AsrsStock.STOCK_QTY, "0", ">", "", "", true);
            }
            // 棚使用(異常棚)のみ
            else
            {
                // AS/RS在庫情報.パレット在庫状態(異常)
                sKey.setKey(AsrsStock.PALLET_STATUS_FLAG, SystemDefine.PALLET_STATUS_IRREGULAR);
            }
        }
        // 空棚、異常棚にチェックが無い場合
        // 棚使用(実棚) + 棚使用(空PB棚)
        else if (param.getBoolean(PROHIBITION_STORAGED) && param.getBoolean(PROHIBITION_EMPTY_PALLET))
        {
            // AS/RS棚情報.棚状態(実棚)
            sKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_STORAGED, "=", "(", "", false);
            // AS/RS在庫情報.空パレット状態(空パレット)
            sKey.setKey(AsrsStock.EMPTY_FLAG, AsrsStock.EMPTY_FLAG_EMPTY, "=", "", ")", true);
            // AS/RS在庫情報.パレット在庫状態(実棚)
            sKey.setKey(AsrsStock.PALLET_STATUS_FLAG, AsrsStock.PALLET_STATUS_REGULAR, "=", "(", "", true);
            // AS/RS在庫情報.在庫数(0以上)
            sKey.setKey(AsrsStock.STOCK_QTY, "0", ">", "", ")", true);
        }
        // 棚使用(実棚)のみ
        else if (param.getBoolean(PROHIBITION_STORAGED))
        {
            // AS/RS棚情報.棚状態(実棚)
            sKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_STORAGED);
            // AS/RS在庫情報.パレット在庫状態(実棚)
            sKey.setKey(AsrsStock.PALLET_STATUS_FLAG, AsrsStock.PALLET_STATUS_REGULAR);
            // AS/RS在庫情報.空パレット状態(空パレット)
            sKey.setKey(AsrsStock.EMPTY_FLAG, AsrsStock.EMPTY_FLAG_EMPTY, "<>", "", "", true);
            // AS/RS在庫情報.在庫数(0以上)
            sKey.setKey(AsrsStock.STOCK_QTY, "0", ">", "", "", true);
        }
        // 棚使用(空PB棚)のみ
        else if (param.getBoolean(PROHIBITION_EMPTY_PALLET))
        {
            // AS/RS在庫情報.空パレット状態(空パレット)
            sKey.setKey(AsrsStock.EMPTY_FLAG, AsrsStock.EMPTY_FLAG_EMPTY, "=", "", "", true);
        }
    }

    /**
     * 棚状態を返します。
     * @param result エンティティクラス
     * @return 棚状態
     */
    private String setStatusFlag(Shelf result)
    {
        // アクセス不可棚フラグ(アクセス不可)
        if (Shelf.ACCESS_NG_FLAG_NG.equals(result.getAccessNgFlag()))
        {
            // 「アクセス不可棚」を返却
            return AsrsInParameter.STATUS_UNABLEACCESS;
        }
        // 状態(使用不可)
        else if (Shelf.PROHIBITION_FLAG_NG.equals(result.getProhibitionFlag()))
        {
            // 「禁止棚」を返却
            return AsrsInParameter.STATUS_UNAVAILABLE;
        }
        // パレット在庫状態(異常)
        else if (AsrsStock.PALLET_STATUS_IRREGULAR.equals(result.getValue(AsrsStock.PALLET_STATUS_FLAG)))
        {
            // 「異常棚」を返却
            return AsrsInParameter.STATUS_IRREGULAR;
        }
        // 棚状態(空棚)
        else if (Shelf.LOCATION_STATUS_FLAG_EMPTY.equals(result.getStatusFlag()))
        {
            // 「空棚」を返却
            return AsrsInParameter.STATUS_EMPTY;
        }
        // 棚状態(実棚)
        else if (Shelf.LOCATION_STATUS_FLAG_STORAGED.equals(result.getStatusFlag()))
        {
            // 空パレット状態(空パレット)
            if (AsrsStock.EMPTY_FLAG_EMPTY.equals(String.valueOf(result.getValue(AsrsStock.EMPTY_FLAG, ""))))
            {
                // 「空PB棚」を返却
                return AsrsInParameter.STATUS_EMPTYPALLET;
            }
            else
            {
                // 「実棚」を返却
                return AsrsInParameter.STATUS_STORAGED;
            }
        }
        else
        {
            // どれにも該当しない場合はnullを返却
            return null;
        }
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
