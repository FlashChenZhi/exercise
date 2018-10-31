// $Id: Dasch_ja.java 5085 2009-10-01 08:09:16Z okamura $
package jp.co.daifuku.wms.stock.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.stock.dasch.FaShelfInquiryDASCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.GenericEntity;
import jp.co.daifuku.wms.handler.db.DefaultDDBFinder;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 棚別在庫照会に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 5085 $, $Date:: 2009-10-01 17:09:16 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okamura $
 */
public class FaShelfInquiryDASCH
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
    private DefaultDDBFinder _finder = null;

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
    public FaShelfInquiryDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new DefaultDDBFinder(getConnection(), new GenericEntity());

        // Initialize record counts
        // データ件数初期化(Excel大量出力対応)
        _finder.open(isForwardOnly());

        // Create Search Key and search for Records
        _finder.search(String.valueOf(createSearchKey(p, true)));

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
        // 返却パラメータの生成
        Params p = new Params();
        // 1件分在庫情報を取得
        GenericEntity[] ents = (GenericEntity[])_finder.getEntities(1);
        for (GenericEntity ent : ents)
        {
            // 取得データのセット
            p.set(LOCATION_NO, getLocationNo(ent));
            p.set(LOCATION_STATUS, getLStatusName(ent));
            p.set(ITEM_CODE, ent.getValue(Stock.ITEM_CODE, ""));
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME, ""));
            p.set(LOT_NO, ent.getValue(Stock.LOT_NO, ""));
            p.set(LAST_PICKING_DATE, ent.getValue(Stock.RETRIEVAL_DAY, ""));
            p.set(STORAGE_DATETIME, ent.getDate(Stock.STORAGE_DATE));
            p.set(STOCK_QTY, ent.getBigDecimal(new FieldName("", "STOCK_QTY"), new BigDecimal(0)));

            // 帳票必須項目
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());
        }
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
        DefaultDDBHandler handler = null;
        try
        {
            // データ件数取得
            handler = new DefaultDDBHandler(getConnection());
            handler.execute(String.valueOf(createSearchKey(p, false)));
            Entity[] countEntity = handler.getEntities(1, new GenericEntity());
            int count = countEntity[0].getBigDecimal(new FieldName("", "COUNT"), new BigDecimal(0)).intValue();
            _total = count;
            return _total;
        }
        finally
        {
            if (handler != null)
            {
                handler.close();
            }
        }
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
        GenericEntity[] ents = (GenericEntity[])_finder.getEntities(start, start + cnt);
        for (GenericEntity ent : ents)
        {
            // 取得データのセット
            Params p = new Params();
            p.set(NO, String.valueOf(params.size() + 1 + start));
            p.set(LOCATION_NO, getLocationNo(ent));
            p.set(LOCATION_STATUS, getLStatusName(ent));
            p.set(ITEM_CODE, ent.getValue(Stock.ITEM_CODE, ""));
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME, ""));
            p.set(LOT_NO, ent.getValue(Stock.LOT_NO, ""));
            p.set(LAST_PICKING_DATE, ent.getValue(Stock.RETRIEVAL_DAY, ""));
            p.set(STORAGE_DATETIME, ent.getDate(Stock.STORAGE_DATE));
            p.set(STOCK_QTY, ent.getBigDecimal(new FieldName("", "STOCK_QTY"), new BigDecimal(0)));
            params.add(p);
        }
        return params;
    }

    /**
     * AS/RS棚情報.ステーションNo.を在庫情報の棚形式に変換して返却
     * 
     * @param ent 検索実行結果
     * @return 在庫情報の棚形式に変換した棚No.
     * @throws CommonException
     */
    protected String getLocationNo(GenericEntity ent)
            throws CommonException
    {
        // エリアマスタ情報コントローラの生成
        AreaController aCtrl = new AreaController(this.getConnection(), this.getClass());

        // 平置の場合
        if (StringUtil.isBlank(ent.getValue(Shelf.STATION_NO, "")))
        {
            // 在庫が存在しない棚の場合
            if (StringUtil.isBlank(ent.getValue(Stock.LOCATION_NO, "").toString()))
            {
                return ent.getValue(Locate.LOCATION_NO, "").toString();
            }
            else
            {
                return ent.getValue(Stock.LOCATION_NO, "").toString();
            }
        }
        else
        {
            // AS/RS棚情報.ステーションNo.を在庫情報の棚形式にて返却
            return aCtrl.toParamLocation(String.valueOf(ent.getValue(Shelf.STATION_NO, "")));
        }
    }

    /**
     * AS/RS棚情報、パレット情報から棚の状態を取得します。
     * 
     * @param stock
     * @return 棚状態
     */
    protected String getLStatusName(GenericEntity ent)
    {
        // 棚使用不可フラグ
        String prohibition = ent.getValue(Shelf.PROHIBITION_FLAG, "").toString();
        // AS/RS棚情報状態フラグ
        String shelfStatus = ent.getValue(new FieldName("", "SHELF_STATUS"), "").toString();
        // パレット情報状態フラグ
        String palletStatus = ent.getValue(new FieldName("", "PALLET_STATUS"), "").toString();
        // 空パレット状態フラグ
        String palletEmpty = ent.getValue(Pallet.EMPTY_FLAG, "").toString();
        // アクセス不可棚フラグ
        String accessNg = ent.getValue(Shelf.ACCESS_NG_FLAG, "").toString();
        // 平置棚状態フラグ
        String locateStatus = ent.getValue(Locate.STATUS_FLAG, "").toString();

        // アクセス不可棚
        if (Shelf.ACCESS_NG_FLAG_NG.equals(accessNg))
        {
            return DisplayText.getText("CHK-W0032");
        }
        // 禁止棚
        else if (Shelf.PROHIBITION_FLAG_NG.equals(prohibition))
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

        // 平置エリア かつ 実棚の場合
        if (SystemDefine.LOCATION_STATUS_FLAG_STORAGED.equals(locateStatus))
        {
            return DisplayText.getText("LBL-W0104");
        }
        // 平置エリア かつ 空棚の場合
        else if (SystemDefine.LOCATION_STATUS_FLAG_EMPTY.equals(locateStatus))
        {
            return DisplayText.getText("LBL-W0061");
        }
        else
        {
            return DisplayText.getText("LBL-W0104");
        }
    }

    /**
     * AS/RSエリア用のSQLを生成します。
     * 
     * @param param 画面のパラメータ
     * @param isSet 件数確認かどうか
     * @return 生成したSQL文字列
     * @throws CommonException
     */
    protected StringBuffer createAsrsArea(Params param, boolean isSet)
            throws CommonException
    {
        // SQL文字格納変数
        StringBuffer sbSql = new StringBuffer();

        // エリアマスタ情報コントローラの生成
        AreaController aCtrl = new AreaController(this.getConnection(), this.getClass());
        // 実棚チェック
        boolean checkStored = param.getBoolean(STORED_LOCATION);
        // 空棚チェック
        boolean checkEmpty = param.getBoolean(EMPTY_LOCATION);
        // 作業棚チェック
        boolean checkWork = param.getBoolean(WORK_LOCATION);
        // 禁止棚チェック
        boolean checkProhibited = param.getBoolean(PROHIBITED_LOCATION);
        // アクセス不可棚チェック
        boolean checkUnreachable = param.getBoolean(UNREACHABLE_LOCATION);
        // 棚の大小入替え
        String[] tmp = WmsFormatter.getFromTo(param.getString(FROM_LOCATION_NO), param.getString(TO_LOCATION_NO));
        // エリアNo.
        String areaNo = param.getString(AREA_NO);
        // 倉庫ステーションNo.
        String whStationNo = aCtrl.getWhStationNo(areaNo);;
        // 商品コード
        String itemCode = param.getString(ITEM_CODE);
        // 棚(From)
        String locationFrom = aCtrl.toAsrsLocation(areaNo, tmp[0]);
        // 棚(To)
        String locationto = aCtrl.toAsrsLocation(areaNo, tmp[1]);

        // 件数確認ではない場合
        if (isSet)
        {
            // 取得条件
            sbSql.append("SELECT");
            sbSql.append("    DMSHELF.BANK_NO");
            sbSql.append("    ,DMSHELF.BAY_NO");
            sbSql.append("    ,DMSHELF.LEVEL_NO");
            sbSql.append("    ,DMSHELF.STATUS_FLAG AS SHELF_STATUS");
            sbSql.append("    ,DMSHELF.PROHIBITION_FLAG");
            sbSql.append("    ,DMSHELF.STATION_NO");
            sbSql.append("    ,DMSHELF.ACCESS_NG_FLAG");
            sbSql.append("    ,DNPALLET.STATUS_FLAG AS PALLET_STATUS");
            sbSql.append("    ,DNPALLET.EMPTY_FLAG");
            sbSql.append("    ,DNSTOCK.LOCATION_NO");
            sbSql.append("    ,DNSTOCK.ITEM_CODE");
            sbSql.append("    ,DMITEM.ITEM_NAME");
            sbSql.append("    ,DNSTOCK.LOT_NO");
            sbSql.append("    ,DNSTOCK.RETRIEVAL_DAY");
            sbSql.append("    ,DNSTOCK.STORAGE_DATE");
            sbSql.append("    ,SUM(DNSTOCK.STOCK_QTY) AS STOCK_QTY");
        }
        else
        {
            // 取得条件
            sbSql.append("SELECT");
            sbSql.append("    SUM(COUNT(*)) AS COUNT");
        }

        // 取得元テーブル
        sbSql.append(" FROM");
        sbSql.append("    DMSHELF DMSHELF");
        sbSql.append("    ,DNPALLET DNPALLET");
        sbSql.append("    ,DNSTOCK DNSTOCK");
        sbSql.append("    ,DMITEM DMITEM");
        sbSql.append("    ,DMAREA DMAREA");

        // 検索条件
        sbSql.append(" WHERE");
        sbSql.append("    DMSHELF.WH_STATION_NO = " + DBFormat.format(whStationNo));
        // 商品コードが指定されている場合
        if (!StringUtil.isBlank(itemCode))
        {
            sbSql.append("    AND DNSTOCK.ITEM_CODE = " + DBFormat.format(itemCode));
        }
        // 棚(From)
        if (!StringUtil.isBlank(locationFrom))
        {
            sbSql.append("    AND DMSHELF.STATION_NO >= '" + locationFrom + "'");
        }
        // 棚(To)
        if (!StringUtil.isBlank(locationto))
        {
            sbSql.append("    AND DMSHELF.STATION_NO <= '" + locationto + "'");
        }
        // 実棚が指定されている場合
        if (checkStored)
        {
            // アクセス可、使用可、実棚、出庫予約棚ではない or 出庫中棚ではない、在庫情報あり
            sbSql.append("    AND (");
            sbSql.append("     (");
            sbSql.append("      DMSHELF.ACCESS_NG_FLAG = '" + SystemDefine.ACCESS_NG_FLAG_OK + "'");
            sbSql.append("      AND DMSHELF.PROHIBITION_FLAG = '" + SystemDefine.PROHIBITION_FLAG_OK + "'");
            sbSql.append("      AND DMSHELF.STATUS_FLAG = '" + SystemDefine.LOCATION_STATUS_FLAG_STORAGED + "'");
            sbSql.append("      AND (DNPALLET.STATUS_FLAG != '" + SystemDefine.PALLET_STATUS_RETRIEVAL_PLAN + "'");
            sbSql.append("           AND DNPALLET.STATUS_FLAG != '" + SystemDefine.PALLET_STATUS_RETRIEVAL + "'");
            sbSql.append("      )");
            sbSql.append("      AND DNSTOCK.STOCK_ID IS NOT NULL");
            sbSql.append("     )");

            // 実棚以降の状態が指定されていなければ括りを閉じる
            if (!checkEmpty && !checkWork && !checkProhibited && !checkUnreachable)
            {
                sbSql.append("    )");
            }
        }
        // 空棚が指定されている場合
        if (checkEmpty)
        {
            // 実棚も指定されている場合
            if (checkStored)
            {
                sbSql.append("    OR");
                sbSql.append("    (");
                // アクセス可、使用可、空棚(在庫情報なし)
                sbSql.append("     DMSHELF.ACCESS_NG_FLAG = '" + SystemDefine.ACCESS_NG_FLAG_OK + "'");
                sbSql.append("     AND DMSHELF.PROHIBITION_FLAG = '" + SystemDefine.PROHIBITION_FLAG_OK + "'");
                sbSql.append("     AND DMSHELF.STATUS_FLAG = '" + SystemDefine.LOCATION_STATUS_FLAG_EMPTY + "'");
                sbSql.append("     AND DNSTOCK.STOCK_ID IS NULL");
                sbSql.append("    )");
            }
            else
            {
                sbSql.append("    AND (");
                sbSql.append("     (");
                // アクセス可、使用可、空棚(在庫情報なし)
                sbSql.append("      DMSHELF.ACCESS_NG_FLAG = '" + SystemDefine.ACCESS_NG_FLAG_OK + "'");
                sbSql.append("      AND DMSHELF.PROHIBITION_FLAG = '" + SystemDefine.PROHIBITION_FLAG_OK + "'");
                sbSql.append("      AND DMSHELF.STATUS_FLAG = '" + SystemDefine.LOCATION_STATUS_FLAG_EMPTY + "'");
                sbSql.append("      AND DNSTOCK.STOCK_ID IS NULL");
                sbSql.append("     )");
            }

            // 空棚以降の状態が指定されていなければ括りを閉じる
            if (!checkWork && !checkProhibited && !checkUnreachable)
            {
                sbSql.append("    )");
            }
        }
        // 作業棚が指定されている場合
        if (checkWork)
        {
            // 実棚、または空棚も指定されている場合
            if (checkStored || checkEmpty)
            {
                sbSql.append("    OR");
                sbSql.append("    (");
                sbSql.append("     (");
                // アクセス可、使用可、予約棚
                sbSql.append("      DMSHELF.ACCESS_NG_FLAG = '" + SystemDefine.ACCESS_NG_FLAG_OK + "'");
                sbSql.append("      AND DMSHELF.PROHIBITION_FLAG = '" + SystemDefine.PROHIBITION_FLAG_OK + "'");
                sbSql.append("      AND DMSHELF.STATUS_FLAG = '" + SystemDefine.LOCATION_STATUS_FLAG_RESERVATION + "'");
                sbSql.append("     )");
                sbSql.append("     OR (");
                // アクセス可、使用可、実棚、出庫予約棚 or 出庫中棚
                sbSql.append("      DMSHELF.ACCESS_NG_FLAG = '" + SystemDefine.ACCESS_NG_FLAG_OK + "'");
                sbSql.append("      AND DMSHELF.PROHIBITION_FLAG = '" + SystemDefine.PROHIBITION_FLAG_OK + "'");
                sbSql.append("      AND DMSHELF.STATUS_FLAG = '" + SystemDefine.LOCATION_STATUS_FLAG_STORAGED + "'");
                sbSql.append("      AND (DNPALLET.STATUS_FLAG = '" + SystemDefine.PALLET_STATUS_RETRIEVAL_PLAN + "'");
                sbSql.append("          OR DNPALLET.STATUS_FLAG = '" + SystemDefine.PALLET_STATUS_RETRIEVAL + "'");
                sbSql.append("      )");
                sbSql.append("     )");
                sbSql.append("     OR (");
                // アクセス可、使用可、空棚(在庫情報あり)
                sbSql.append("      DMSHELF.ACCESS_NG_FLAG = '" + SystemDefine.ACCESS_NG_FLAG_OK + "'");
                sbSql.append("      AND DMSHELF.PROHIBITION_FLAG = '" + SystemDefine.PROHIBITION_FLAG_OK + "'");
                sbSql.append("      AND DMSHELF.STATUS_FLAG = '" + SystemDefine.LOCATION_STATUS_FLAG_EMPTY + "'");
                sbSql.append("      AND DNSTOCK.STOCK_ID IS NOT NULL");
                sbSql.append("     )");
                sbSql.append("    )");
            }
            else
            {
                sbSql.append("    AND (");
                sbSql.append("     (");
                sbSql.append("      (");
                // アクセス可、使用可、予約棚
                sbSql.append("       DMSHELF.ACCESS_NG_FLAG = '" + SystemDefine.ACCESS_NG_FLAG_OK + "'");
                sbSql.append("       AND DMSHELF.PROHIBITION_FLAG = '" + SystemDefine.PROHIBITION_FLAG_OK + "'");
                sbSql.append("       AND DMSHELF.STATUS_FLAG = '" + SystemDefine.LOCATION_STATUS_FLAG_RESERVATION + "'");
                sbSql.append("      )");
                sbSql.append("      OR (");
                // アクセス可、使用可、実棚、出庫予約棚 or 出庫中棚
                sbSql.append("       DMSHELF.ACCESS_NG_FLAG = '" + SystemDefine.ACCESS_NG_FLAG_OK + "'");
                sbSql.append("       AND DMSHELF.PROHIBITION_FLAG = '" + SystemDefine.PROHIBITION_FLAG_OK + "'");
                sbSql.append("       AND DMSHELF.STATUS_FLAG = '" + SystemDefine.LOCATION_STATUS_FLAG_STORAGED + "'");
                sbSql.append("       AND (DNPALLET.STATUS_FLAG = '" + SystemDefine.PALLET_STATUS_RETRIEVAL_PLAN + "'");
                sbSql.append("           OR DNPALLET.STATUS_FLAG = '" + SystemDefine.PALLET_STATUS_RETRIEVAL + "'");
                sbSql.append("       )");
                sbSql.append("      )");
                sbSql.append("      OR (");
                // アクセス可、使用可、空棚(在庫情報あり)
                sbSql.append("       DMSHELF.ACCESS_NG_FLAG = '" + SystemDefine.ACCESS_NG_FLAG_OK + "'");
                sbSql.append("       AND DMSHELF.PROHIBITION_FLAG = '" + SystemDefine.PROHIBITION_FLAG_OK + "'");
                sbSql.append("       AND DMSHELF.STATUS_FLAG = '" + SystemDefine.LOCATION_STATUS_FLAG_EMPTY + "'");
                sbSql.append("       AND DNSTOCK.STOCK_ID IS NOT NULL");
                sbSql.append("      )");
                sbSql.append("     )");
            }

            // 作業棚以降の状態が指定されていなければ括りを閉じる
            if (!checkProhibited && !checkUnreachable)
            {
                sbSql.append("    )");
            }
        }
        // 禁止棚が指定されている場合
        if (checkProhibited)
        {
            // 実棚、または空棚、または作業棚も指定されている場合
            if (checkStored || checkEmpty || checkWork)
            {
                sbSql.append("    OR");
                sbSql.append("    (");
                // アクセス可、使用不可
                sbSql.append("     DMSHELF.ACCESS_NG_FLAG = '" + SystemDefine.ACCESS_NG_FLAG_OK + "'");
                sbSql.append("     AND DMSHELF.PROHIBITION_FLAG = '" + SystemDefine.PROHIBITION_FLAG_NG + "'");
                sbSql.append("    )");
            }
            else
            {
                sbSql.append("    AND (");
                sbSql.append("     (");
                // アクセス可、使用不可
                sbSql.append("      DMSHELF.ACCESS_NG_FLAG = '" + SystemDefine.ACCESS_NG_FLAG_OK + "'");
                sbSql.append("      AND DMSHELF.PROHIBITION_FLAG = '" + SystemDefine.PROHIBITION_FLAG_NG + "'");
                sbSql.append("     )");
            }

            // 禁止棚以降の状態が指定されていなければ括りを閉じる
            if (!checkUnreachable)
            {
                sbSql.append("    )");
            }
        }
        // アクセス不可棚が指定されている場合
        if (checkUnreachable)
        {
            // 実棚、または空棚、または作業棚、または禁止棚も指定されている場合
            if (checkStored || checkEmpty || checkWork || checkProhibited)
            {
                sbSql.append("    OR");
                sbSql.append("     (");
                // アクセス不可
                sbSql.append("      DMSHELF.ACCESS_NG_FLAG = '" + SystemDefine.ACCESS_NG_FLAG_NG + "'");
                sbSql.append("     )");
                sbSql.append("    )");
            }
            else
            {
                sbSql.append("    AND (");
                sbSql.append("     (");
                // アクセス不可
                sbSql.append("      DMSHELF.ACCESS_NG_FLAG = '" + SystemDefine.ACCESS_NG_FLAG_NG + "'");
                sbSql.append("     )");
                sbSql.append("    )");
            }
        }
        sbSql.append("    AND DMSHELF.STATION_NO = DNPALLET.CURRENT_STATION_NO(+)");
        sbSql.append("    AND DNPALLET.PALLET_ID = DNSTOCK.PALLET_ID(+)");
        sbSql.append("    AND DNSTOCK.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE(+)");
        sbSql.append("    AND DNSTOCK.ITEM_CODE = DMITEM.ITEM_CODE(+)");
        sbSql.append("    AND DNSTOCK.AREA_NO = DMAREA.AREA_NO(+)");

        // 集約条件
        sbSql.append(" GROUP BY");
        sbSql.append("    DMSHELF.BANK_NO");
        sbSql.append("    ,DMSHELF.BAY_NO");
        sbSql.append("    ,DMSHELF.LEVEL_NO");
        sbSql.append("    ,DMSHELF.STATUS_FLAG");
        sbSql.append("    ,DMSHELF.PROHIBITION_FLAG");
        sbSql.append("    ,DMSHELF.STATION_NO");
        sbSql.append("    ,DMSHELF.ACCESS_NG_FLAG");
        sbSql.append("    ,DNPALLET.STATUS_FLAG");
        sbSql.append("    ,DNPALLET.EMPTY_FLAG");
        sbSql.append("    ,DNSTOCK.LOCATION_NO");
        sbSql.append("    ,DNSTOCK.ITEM_CODE");
        sbSql.append("    ,DMITEM.ITEM_NAME");
        sbSql.append("    ,DNSTOCK.LOT_NO");
        sbSql.append("    ,DNSTOCK.RETRIEVAL_DAY");
        sbSql.append("    ,DNSTOCK.STORAGE_DATE");

        // 件数確認ではない場合
        if (isSet)
        {
            // 並び順
            sbSql.append(" ORDER BY");
            sbSql.append("    DMSHELF.BANK_NO");
            sbSql.append("    ,DMSHELF.BAY_NO");
            sbSql.append("    ,DMSHELF.LEVEL_NO");
            sbSql.append("    ,DNSTOCK.ITEM_CODE");
            sbSql.append("    ,DNSTOCK.LOT_NO");
        }

        // 生成したSQLを返却
        return sbSql;
    }

    /**
     * フリー管理エリア用のSQLを生成します。
     * 
     * @param param 画面のパラメータ
     * @param isSet 件数確認かどうか
     * @return 生成したSQL文字列
     * @throws CommonException
     */
    protected StringBuffer createFreeArea(Params param, boolean isSet)
            throws CommonException
    {
        // SQL文字格納変数
        StringBuffer sbSql = new StringBuffer();

        // 棚の大小入替え
        String[] tmp = WmsFormatter.getFromTo(param.getString(FROM_LOCATION_NO), param.getString(TO_LOCATION_NO));
        // エリアNo.
        String areaNo = param.getString(AREA_NO);
        // 商品コード
        String itemCode = param.getString(ITEM_CODE);

        // 件数確認ではない場合
        if (isSet)
        {
            // 取得条件
            sbSql.append("SELECT");
            sbSql.append("    DNSTOCK.LOCATION_NO");
            sbSql.append("    ,DNSTOCK.ITEM_CODE");
            sbSql.append("    ,DMITEM.ITEM_NAME");
            sbSql.append("    ,DNSTOCK.LOT_NO");
            sbSql.append("    ,DNSTOCK.RETRIEVAL_DAY");
            sbSql.append("    ,DNSTOCK.STORAGE_DATE");
            sbSql.append("    ,SUM(DNSTOCK.STOCK_QTY) AS STOCK_QTY");
        }
        else
        {
            // 取得条件
            sbSql.append("SELECT");
            sbSql.append("    SUM(COUNT(*)) AS COUNT");
        }

        // 取得元テーブル
        sbSql.append(" FROM");
        sbSql.append("    DNSTOCK DNSTOCK");
        sbSql.append("    ,DMITEM DMITEM");
        sbSql.append("    ,DMAREA DMAREA");

        // 検索条件
        sbSql.append(" WHERE");
        sbSql.append("    DNSTOCK.AREA_NO = " + DBFormat.format(areaNo));
        // 商品コードが指定されている場合
        if (!StringUtil.isBlank(itemCode))
        {
            sbSql.append("    AND DNSTOCK.ITEM_CODE = " + DBFormat.format(itemCode));
        }
        // 棚(From)
        if (!StringUtil.isBlank(tmp[0]))
        {
            sbSql.append("    AND DNSTOCK.LOCATION_NO >= '" + tmp[0] + "'");
        }
        // 棚(To)
        if (!StringUtil.isBlank(tmp[1]))
        {
            sbSql.append("    AND DNSTOCK.LOCATION_NO <= '" + tmp[1] + "'");
        }
        sbSql.append("    AND DNSTOCK.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE(+)");
        sbSql.append("    AND DNSTOCK.ITEM_CODE = DMITEM.ITEM_CODE(+)");
        sbSql.append("    AND DNSTOCK.AREA_NO = DMAREA.AREA_NO(+)");

        // 集約条件
        sbSql.append(" GROUP BY");
        sbSql.append("    DNSTOCK.LOCATION_NO");
        sbSql.append("    ,DNSTOCK.ITEM_CODE");
        sbSql.append("    ,DMITEM.ITEM_NAME");
        sbSql.append("    ,DNSTOCK.LOT_NO");
        sbSql.append("    ,DNSTOCK.RETRIEVAL_DAY");
        sbSql.append("    ,DNSTOCK.STORAGE_DATE");

        // 件数確認ではない場合
        if (isSet)
        {
            // 並び順
            sbSql.append(" ORDER BY");
            sbSql.append("    DNSTOCK.LOCATION_NO");
            sbSql.append("    ,DNSTOCK.ITEM_CODE");
            sbSql.append("    ,DNSTOCK.LOT_NO");
        }
        // 生成したSQLを返却
        return sbSql;
    }

    /**
     * 棚管理エリア用のSQLを生成します。
     * 
     * @param param 画面のパラメータ
     * @param isSet 件数確認かどうか
     * @return 生成したSQL文字列
     * @throws CommonException
     */
    protected StringBuffer createLocateArea(Params param, boolean isSet)
            throws CommonException
    {
        // SQL文字格納変数
        StringBuffer sbSql = new StringBuffer();

        // 実棚チェック
        boolean checkStored = param.getBoolean(STORED_LOCATION);
        // 空棚チェック
        boolean checkEmpty = param.getBoolean(EMPTY_LOCATION);
        // 棚のFrom～Toを入替える
        String[] tmp = WmsFormatter.getFromTo(param.getString(FROM_LOCATION_NO), param.getString(TO_LOCATION_NO));
        // エリアNo.
        String areaNo = param.getString(AREA_NO);
        // 商品コード
        String itemCode = param.getString(ITEM_CODE);

        // 実棚が指定された場合
        if (checkStored)
        {
            // DNSTOCK(在庫情報)を主として実棚データを取得
            // 取得条件
            sbSql.append("SELECT");
            sbSql.append("    '" + SystemDefine.LOCATION_STATUS_FLAG_STORAGED + "' STATUS_FLAG");
            sbSql.append("    ,DNSTOCK.LOCATION_NO");
            sbSql.append("    ,DNSTOCK.ITEM_CODE");
            sbSql.append("    ,DMITEM.ITEM_NAME");
            sbSql.append("    ,DNSTOCK.LOT_NO");
            sbSql.append("    ,DNSTOCK.RETRIEVAL_DAY");
            sbSql.append("    ,DNSTOCK.STORAGE_DATE");
            sbSql.append("    ,SUM(DNSTOCK.STOCK_QTY) AS STOCK_QTY");

            // 取得元テーブル
            sbSql.append(" FROM");
            sbSql.append("    DNSTOCK DNSTOCK");
            sbSql.append("    ,DMITEM DMITEM");
            sbSql.append("    ,DMAREA DMAREA");

            // 検索条件
            sbSql.append(" WHERE");
            sbSql.append("    DNSTOCK.AREA_NO = " + DBFormat.format(areaNo));
            // 商品コードが指定されている場合
            if (!StringUtil.isBlank(itemCode))
            {
                sbSql.append("    AND DNSTOCK.ITEM_CODE = " + DBFormat.format(itemCode));
            }
            // 棚(From)
            if (!StringUtil.isBlank(tmp[0]))
            {
                sbSql.append("    AND DNSTOCK.LOCATION_NO >= '" + tmp[0] + "'");
            }
            // 棚(To)
            if (!StringUtil.isBlank(tmp[1]))
            {
                sbSql.append("    AND DNSTOCK.LOCATION_NO <= '" + tmp[1] + "'");
            }
            sbSql.append("    AND DNSTOCK.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE(+)");
            sbSql.append("    AND DNSTOCK.ITEM_CODE = DMITEM.ITEM_CODE(+)");
            sbSql.append("    AND DNSTOCK.AREA_NO = DMAREA.AREA_NO(+)");

            // 集約条件
            sbSql.append(" GROUP BY");
            sbSql.append("    DNSTOCK.LOCATION_NO");
            sbSql.append("    ,DNSTOCK.ITEM_CODE");
            sbSql.append("    ,DMITEM.ITEM_NAME");
            sbSql.append("    ,DNSTOCK.LOT_NO");
            sbSql.append("    ,DNSTOCK.RETRIEVAL_DAY");
            sbSql.append("    ,DNSTOCK.STORAGE_DATE");
        }

        // 実棚 かつ 空棚が両方指定された場合
        if (checkStored && checkEmpty)
        {
            sbSql.append(" UNION ALL ");
        }

        // 空棚が指定された場合
        if (checkEmpty)
        {
            // DMLOCATE(棚マスタ)を主として空棚データを取得
            // 取得条件
            sbSql.append("SELECT");
            sbSql.append("    '" + SystemDefine.LOCATION_STATUS_FLAG_EMPTY + "' STATUS_FLAG");
            sbSql.append("    ,DMLOCATE.LOCATION_NO");
            sbSql.append("    ,'' ITEM_CODE");
            sbSql.append("    ,'' ITEM_NAME");
            sbSql.append("    ,'' LOT_NO");
            sbSql.append("    ,'' RETRIEVAL_DAY");
            sbSql.append("    ,NULL STORAGE_DATE");
            sbSql.append("    ,0 STOCK_QTY");

            // 取得元テーブル
            sbSql.append(" FROM");
            sbSql.append("    DMLOCATE DMLOCATE");
            sbSql.append("    ,DNSTOCK DNSTOCK");

            // 検索条件
            sbSql.append(" WHERE");
            sbSql.append("    DMLOCATE.AREA_NO = " + DBFormat.format(areaNo));
            sbSql.append("    AND DMLOCATE.STATUS_FLAG = '" + SystemDefine.LOCATION_STATUS_FLAG_EMPTY + "'");
            // 商品コードが指定されている場合
            if (!StringUtil.isBlank(itemCode))
            {
                sbSql.append("    AND DNSTOCK.ITEM_CODE = " + DBFormat.format(itemCode));
            }
            // 棚(From)
            if (!StringUtil.isBlank(tmp[0]))
            {
                sbSql.append("    AND DMLOCATE.LOCATION_NO >= '" + tmp[0] + "'");
            }
            // 棚(To)
            if (!StringUtil.isBlank(tmp[1]))
            {
                sbSql.append("    AND DMLOCATE.LOCATION_NO <= '" + tmp[1] + "'");
            }
            sbSql.append("    AND DMLOCATE.AREA_NO = DNSTOCK.AREA_NO(+)");
            sbSql.append("    AND DMLOCATE.LOCATION_NO = DNSTOCK.LOCATION_NO(+)");
        }

        // 並び順
        sbSql.append(" ORDER BY");
        sbSql.append("    LOCATION_NO");
        sbSql.append("    ,ITEM_CODE");
        sbSql.append("    ,LOT_NO");

        // 生成したSQLを返却
        return sbSql;
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
    private StringBuffer createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        // エリア情報コントローラの生成
        AreaController aCtrl = new AreaController(getConnection(), getClass());

        // AS/RSエリアの場合
        if (SystemDefine.AREA_TYPE_ASRS.equals(aCtrl.getAreaType(param.getString(AREA_NO))))
        {
            return createAsrsArea(param, isSet);
        }
        // 平置エリアの場合
        else
        {
            // フリー管理の場合
            if (SystemDefine.LOCATION_TYPE_FREE.equals(aCtrl.getLocationType(param.getString(AREA_NO))))
            {
                return createFreeArea(param, isSet);
            }
            // 棚管理の場合
            else
            {
                // データ取得時の場合
                if (isSet)
                {
                    return createLocateArea(param, isSet);
                }
                else
                {
                    // 件数確認の場合
                    StringBuffer sbSql = new StringBuffer();
                    sbSql.append("SELECT COUNT(*) AS COUNT FROM ( ");
                    sbSql.append(createLocateArea(param, isSet));
                    sbSql.append(")");
                    return sbSql;
                }
            }
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
