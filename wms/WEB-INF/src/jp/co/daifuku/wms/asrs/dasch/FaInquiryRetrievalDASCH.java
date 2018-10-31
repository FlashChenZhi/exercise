// $Id: Dasch_ja.java 5085 2009-10-01 08:09:16Z okamura $
package jp.co.daifuku.wms.asrs.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.asrs.dasch.FaInquiryRetrievalDASCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.WorkPlace;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.GenericEntity;
import jp.co.daifuku.wms.handler.db.DefaultDDBFinder;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.field.FieldName;


/**
 * 問合せ出庫設定に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 5085 $, $Date:: 2009-11-09 17:09:16 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: nagao $
 */
public class FaInquiryRetrievalDASCH
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
    public FaInquiryRetrievalDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        GenericEntity[] ents = (GenericEntity[])_finder.getEntities(1);
        // パラメータ配列の生成
        Params p = new Params();
        // conver Entity to Param object
        for (GenericEntity ent : ents)
        {
            p.set(AREA_NO, ent.getValue(Stock.AREA_NO, ""));
            p.set(ITEM_CODE, ent.getValue(Stock.ITEM_CODE, ""));
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME, ""));
            p.set(LOCATION_NO, ent.getValue(Stock.LOCATION_NO, ""));

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
        DefaultDDBHandler handler = null;

        try
        {
            handler = new DefaultDDBHandler(getConnection());
            // find count
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

        // 取得した件数分、繰り返す
        for (GenericEntity ent : ents)
        {
            Params p = new Params();
            p.set(AREA_NO, ent.getValue(Stock.AREA_NO, ""));
            p.set(ITEM_CODE, ent.getValue(Stock.ITEM_CODE, ""));
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME));
            p.set(LOT_NO, ent.getValue(Stock.LOT_NO, ""));
            p.set(LOCATION_NO, ent.getValue(Stock.LOCATION_NO, ""));
            p.set(STORAGE_DATE, ent.getValue(Stock.STORAGE_DATE, ""));
            p.set(ALLOCATE_QTY, ent.getValue(Stock.ALLOCATION_QTY, ""));
            p.set(CONSIGNOR_CODE, ent.getValue(Stock.CONSIGNOR_CODE, ""));
            p.set(MIXED_LOAD, isMixed(ent) ? DisplayResource.getMixed(true)
                                          : "");
            p.set(PALLET_ID, ent.getValue(Stock.PALLET_ID, ""));
            p.set(LAST_UPDATE_DATE, ent.getDate(Stock.LAST_UPDATE_DATE));
            p.set(STOCK_ID, ent.getValue(Stock.STOCK_ID, ""));
            p.set(AREA_TYPE, ent.getValue(Area.AREA_TYPE, ""));
            params.add(p);
        }
        return params;
    }

    /**
     * パラメータによりデータ取得条件を生成する。
     *
     * @param param 検索条件を含むパラメータ
     * @return データ取得条件
     * @throws CommonException
     */
    protected StringBuffer createWhere(Params param)
            throws CommonException
    {
        // エリア情報コントローラ
        AreaController arc = new AreaController(getConnection(), getClass());

        // SQL文字格納変数
        StringBuffer sbSql = new StringBuffer();

        sbSql.append("    DNSTOCK.STOCK_QTY > 0");
        sbSql.append("    AND DNSTOCK.ALLOCATION_QTY > 0");
        sbSql.append("    AND DNSTOCK.CONSIGNOR_CODE = " + DBFormat.format(param.getString(CONSIGNOR_CODE)));
        // 商品コード
        if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
        {
            sbSql.append("    AND DNSTOCK.ITEM_CODE = " + DBFormat.format(param.getString(ITEM_CODE)));
        }
        // ロットNo.
        if (!StringUtil.isBlank(param.getString(LOT_NO)))
        {
            sbSql.append("    AND DNSTOCK.LOT_NO = " + DBFormat.format(param.getString(LOT_NO)));
        }
        // エリアNo.が全エリアではない場合
        if (!WmsParam.ALL_AREA_NO.equals(param.getString(AREA_NO)))
        {
            sbSql.append("    AND DNSTOCK.AREA_NO = " + DBFormat.format(param.getString(AREA_NO)));

            // エリアNo.がAS/RSエリアの場合
            if (SystemDefine.AREA_TYPE_ASRS.equals(arc.getAreaType(param.getString(AREA_NO))))
            {
                // 在庫情報.パレットIDがNULL
                sbSql.append("    AND DNSTOCK.PALLET_ID IS NOT NULL ");
            }
            else
            {
                // 在庫情報.パレットIDがNULL
                sbSql.append("    AND DNSTOCK.PALLET_ID IS NULL ");
            }
        }
        // 棚No.
        if (!StringUtil.isBlank(param.getString(LOCATION_NO)))
        {
            sbSql.append("    AND DNSTOCK.LOCATION_NO = " + DBFormat.format(param.getString(LOCATION_NO)));
        }
        // 禁止棚、アクセス不可棚
        sbSql.append("    AND ( (DMAREA.AREA_TYPE = " + DBFormat.format(SystemDefine.ACCESS_NG_FLAG_NG));
        sbSql.append(" AND DMSHELF.PROHIBITION_FLAG IS NOT NULL ) or (DMAREA.AREA_TYPE = "
                + DBFormat.format(SystemDefine.AREA_TYPE_FLOOR) + " and DMSHELF.PROHIBITION_FLAG IS NULL ))");
        sbSql.append("    AND ( (DMAREA.AREA_TYPE = " + DBFormat.format(SystemDefine.PROHIBITION_FLAG_NG));
        sbSql.append(" AND DMSHELF.ACCESS_NG_FLAG IS NOT NULL ) or (DMAREA.AREA_TYPE = "
                + DBFormat.format(SystemDefine.AREA_TYPE_FLOOR) + " and DMSHELF.ACCESS_NG_FLAG IS NULL ))");
        // 2012/04/04 ADD START
        sbSql.append("    AND ( (DMAREA.AREA_TYPE = " + DBFormat.format(SystemDefine.AREA_TYPE_ASRS));
        sbSql.append(" AND DNPALLET.STATUS_FLAG != " + DBFormat.format(SystemDefine.PALLET_STATUS_RETRIEVAL)
                + " ) or (DMAREA.AREA_TYPE = " + DBFormat.format(SystemDefine.AREA_TYPE_FLOOR)
                + " and DNPALLET.STATUS_FLAG IS NULL ))");
        // 2012/04/04 ADD END


        // 生成したWHERE句を返却
        return sbSql;
    }

    /**
     * 引数にしていされたステーションNo.からStationインスタンスを生成し返却します。
     *
     * @param p 検索条件を含むパラメータ
     * @return 生成されたStationインスタンス
     * @throws CommonException
     */
    protected Station getStation(Params p)
            throws CommonException
    {
        Station st = new Station();
        try
        {
            st = StationFactory.makeStation(getConnection(), p.getString(STATION_NO));
        }
        catch (NotFoundException e)
        {
            // ステーションが見つからなかった場合
            throw new InvalidDefineException();
        }
        return st;
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
    private StringBuffer createSearchKey(Params p, boolean isSet)
            throws CommonException
    {
        // エリア情報コントローラ
        AreaController arc = new AreaController(getConnection(), getClass());

        // SQL文字格納変数
        StringBuffer sbSql = new StringBuffer();

        // 件数確認ではない場合
        if (isSet)
        {
            // 取得項目
            sbSql.append("SELECT DISTINCT");
            sbSql.append("    DNSTOCK.STOCK_ID");
            sbSql.append("    ,DNSTOCK.AREA_NO");
            sbSql.append("    ,DNSTOCK.LOCATION_NO");
            sbSql.append("    ,DNSTOCK.CONSIGNOR_CODE");
            sbSql.append("    ,DNSTOCK.ITEM_CODE");
            sbSql.append("    ,DNSTOCK.LOT_NO");
            sbSql.append("    ,DNSTOCK.STORAGE_DATE");
            sbSql.append("    ,DNSTOCK.STOCK_QTY");
            sbSql.append("    ,DNSTOCK.ALLOCATION_QTY");
            sbSql.append("    ,DNSTOCK.PALLET_ID");
            sbSql.append("    ,DNSTOCK.LAST_UPDATE_DATE");
            sbSql.append("    ,DMITEM.ITEM_NAME");
            sbSql.append("    ,DMAREA.AREA_TYPE");
        }
        else
        {
            // 取得項目
            sbSql.append("SELECT DISTINCT");
            sbSql.append("    COUNT(DISTINCT DNSTOCK.STOCK_ID) AS COUNT");
        }

        // 取得元テーブル
        sbSql.append(" FROM");
        sbSql.append("    DNSTOCK DNSTOCK");
        sbSql.append("    INNER JOIN DMITEM DMITEM ON");
        sbSql.append("        DNSTOCK.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE");
        sbSql.append("        AND DNSTOCK.ITEM_CODE = DMITEM.ITEM_CODE");
        sbSql.append("    INNER JOIN DMAREA DMAREA ON");
        sbSql.append("        DNSTOCK.AREA_NO = DMAREA.AREA_NO");
        sbSql.append("    LEFT OUTER JOIN DNPALLET DNPALLET ON");
        sbSql.append("        DNSTOCK.PALLET_ID = DNPALLET.PALLET_ID");
        sbSql.append("    LEFT OUTER JOIN DMAISLE DMAISLE ON");
        sbSql.append("        DMAREA.WHSTATION_NO = DMAISLE.WH_STATION_NO");
        sbSql.append("    LEFT OUTER JOIN DMSHELF DMSHELF ON");
        sbSql.append("        DNPALLET.CURRENT_STATION_NO = DMSHELF.STATION_NO");
        sbSql.append("        AND DMAISLE.STATION_NO = DMSHELF.PARENT_STATION_NO");
        sbSql.append("        AND DMSHELF.ACCESS_NG_FLAG = " + DBFormat.format(SystemDefine.ACCESS_NG_FLAG_OK) + "");
        sbSql.append("        AND DMSHELF.PROHIBITION_FLAG = " + DBFormat.format(SystemDefine.PROHIBITION_FLAG_OK) + "");
        sbSql.append("        AND (DNPALLET.STATUS_FLAG = " + DBFormat.format(SystemDefine.PALLET_STATUS_REGULAR) + "");
        sbSql.append("        OR DNPALLET.STATUS_FLAG = " + DBFormat.format(SystemDefine.PALLET_STATUS_IRREGULAR) + ")");
        // 全ステーションが選択されていない場合
        StringBuffer sbSqlStation = new StringBuffer();
        if (!WmsParam.ALL_STATION.equals(p.getString(STATION_NO))
                && (WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)) || SystemDefine.AREA_TYPE_ASRS.equals(arc.getAreaType(p.getString(AREA_NO)))))
        {
            // Stationインスタンスの生成
            Station st = getStation(p);

            // 作業場の場合
            if (st instanceof WorkPlace)
            {
                WorkPlace wp = (WorkPlace)st;
                String[] stations = wp.getWPStations();

                // 結合倉庫が存在する場合
                if (hasAisleCombined(stations))
                {
                    sbSqlStation.append("        AND DMSHELF.WH_STATION_NO IN ( ");
                    sbSqlStation.append("            SELECT WH_STATION_NO FROM DMSTATION WHERE ");
                }
                else
                {
                    sbSqlStation.append("        AND DMSHELF.PARENT_STATION_NO IN ( ");
                    sbSqlStation.append("            SELECT AISLE_STATION_NO FROM DMSTATION WHERE ");
                }

                // ステーション配列分SQLを作成
                for (int i = 0; i < stations.length; i++)
                {
                    if (i > 0)
                    {
                        sbSqlStation.append(" OR ");
                    }
                    sbSqlStation.append(" STATION_NO = " + DBFormat.format(stations[i]));
                }
                sbSqlStation.append(" ) ");
            }
            else
            {
                // AS/RS棚情報(副テーブル)
                sbSql.append("    INNER JOIN DMSTATION DMSTATION ON");

                // アイル独立の場合
                if (StringUtil.isBlank(st.getAisleStationNo()))
                {
                    sbSql.append("        DMSHELF.WH_STATION_NO = DMSTATION.WH_STATION_NO");
                }
                // アイル結合の場合
                else
                {
                    sbSql.append("        DMSHELF.PARENT_STATION_NO = DMSTATION.AISLE_STATION_NO");
                }
                // AS/RSステーション情報.ステーションNo.と選択されたステーション
                sbSql.append("        AND DMSTATION.STATION_NO = " + DBFormat.format(p.getString(STATION_NO)));
            }
        }

        // 検索条件
        sbSql.append(" WHERE");
        sbSql.append(createWhere(p));
        sbSql.append(sbSqlStation);
        // RM No.が入力されている場合
        if (!StringUtil.isBlank(p.getString(FROM_RM_NO)) || !StringUtil.isBlank(p.getString(TO_RM_NO)))
        {
            String[] rm = WmsFormatter.getFromTo(p.getString(FROM_RM_NO), p.getString(TO_RM_NO));
            // RM No.(From)
            if (!StringUtil.isBlank(rm[0]))
            {
                sbSql.append("        AND DMAISLE.AISLE_NO >= " + DBFormat.format(rm[0]));
            }
            // RM No.(To)
            if (!StringUtil.isBlank(rm[1]))
            {
                sbSql.append("        AND DMAISLE.AISLE_NO <= " + DBFormat.format(rm[1]));
            }
        }

        // 件数確認ではない場合
        if (isSet)
        {
            // 並び順
            sbSql.append(" ORDER BY");
            sbSql.append("    DNSTOCK.ITEM_CODE");
            // ロットNo.昇順設定の場合
            if (WmsParam.RETRIEVAL_ALLOCATE_PRIORITY == 1)
            {
                sbSql.append("    ,DNSTOCK.LOT_NO");
            }
            sbSql.append("    ,DNSTOCK.AREA_NO");
            sbSql.append("    ,DNSTOCK.STORAGE_DATE");
        }
        // 生成したSQLを返却
        return sbSql;
    }

    /**
     * 引当てた在庫情報の内、混載の棚があるかチェックします。<BR>
     * @param stk 引当在庫情報のパラメータ
     * @param sh 参照データのカウントを取る
     * @return boolean 混載有時･･･true、混載無時･･･false
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private boolean isMixed(GenericEntity stk)
            throws CommonException
    {
        StockHandler sh = new StockHandler(getConnection());
        StockSearchKey sk = new StockSearchKey();

        sk.setAreaNo(stk.getValue(Stock.AREA_NO, "").toString());
        sk.setLocationNo(stk.getValue(Stock.LOCATION_NO, "").toString());
        sk.setPalletId(stk.getValue(Stock.PALLET_ID, "").toString());

        if (sh.count(sk) >= 2)
        {
            return true;
        }

        return false;
    }

    /**
     * 指定されたステーションに結合倉庫が存在するかを返します。
     *
     * @param wp ステーション
     * @throws ReadWriteException データベースエラーの場合にスローされます。
     */
    protected boolean hasAisleCombined(String[] stations)
            throws ReadWriteException
    {
        StationHandler sthandler = new StationHandler(getConnection());
        StationSearchKey stkey = new StationSearchKey();

        stkey.setStationNo(stations, true);
        stkey.setAisleStationNo("");

        if (sthandler.count(stkey) > 0)
        {
            return true;
        }
        return false;
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
