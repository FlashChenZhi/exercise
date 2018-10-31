// $Id: AsNoPlanStorageListDASCH.java 7406 2010-03-06 02:44:11Z okayama $
package jp.co.daifuku.wms.asrs.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.asrs.dasch.AsNoPlanStorageListDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.asrs.location.FreeStorageStationOperator;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.asrs.location.StationOperatorFactory;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.GenericEntity;
import jp.co.daifuku.wms.handler.db.DefaultDDBFinder;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * AS/RS 予定外入庫リスト発行に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 7406 $, $Date:: 2010-03-06 11:44:11 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class AsNoPlanStorageListDASCH
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
     * <code>BasicDatabaseFinder</code>参照を保持します。
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
    public AsNoPlanStorageListDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 入力されたパラメータをもとにSQL文を発行します。
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
        // get Next entity from finder class
        GenericEntity[] ents = (GenericEntity[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (GenericEntity ent : ents)
        {
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            // エリア
            p.set(AREA_NO, ent.getValue(WorkInfo.PLAN_AREA_NO));
            // エリア名称
            p.set(AREA_NAME, String.valueOf(ent.getValue(Area.AREA_NAME, "")));
            // 発行日
            p.set(SYS_DAY, getPrintDate());
            // 発行時刻
            p.set(SYS_TIME, getPrintDate());
            // ステーション
            p.set(STATION_NO, String.valueOf(ent.getValue("STATION_NO", "")));
            // ステーション名称
            p.set(STATION_NAME, String.valueOf(ent.getValue(Station.STATION_NAME, "")));
            // 作業No
            p.set(WORK_NO, String.valueOf(ent.getValue(CarryInfo.WORK_NO, "")));
            // 入庫棚
            p.set(STORAGE_LOCATION_NO, ent.getValue(WorkInfo.PLAN_LOCATION_NO));
            // 商品コード
            p.set(ITEM_CODE, ent.getValue(WorkInfo.ITEM_CODE));
            // 商品名称
            p.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
            // ロットNo.
            p.set(PLAN_LOT_NO, ent.getValue(WorkInfo.PLAN_LOT_NO));
            // ケース入数
            int enteringQty = ent.getBigDecimal(Item.ENTERING_QTY).intValue();
            p.set(ENTERING_QTY, enteringQty);
            // 予定ケース数
            p.set(STORAGE_CASE_QTY,
                    DisplayUtil.getCaseQty(ent.getBigDecimal(WorkInfo.PLAN_QTY).intValue(), enteringQty));
            // 予定ピース数
            p.set(STORAGE_PIECE_QTY, DisplayUtil.getPieceQty(ent.getBigDecimal(WorkInfo.PLAN_QTY).intValue(),
                    enteringQty));
            // JAN
            p.set(JAN, String.valueOf(ent.getValue(Item.JAN, "")));
            // ITF
            p.set(ITF, String.valueOf(ent.getValue(Item.ITF, "")));
            break;
        }
        // return Pram objstc
        return p;
    }

    /**
     * Finderのクローズを行います。
     * @throws ReadWriteException データベースエラー
     */
    public void closeFinder()
            throws ReadWriteException
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
        station(p);

        DefaultDDBHandler handler = null;

        try
        {
            handler = new DefaultDDBHandler(getConnection());
            // find count
            // カウントSQLの実行
            handler.execute(String.valueOf(createSearchSql(p, false)));
            Entity[] countEntity = handler.getEntities(1, new WorkInfo());
            int cnt = countEntity[0].getBigDecimal(new FieldName("", "CNT")).intValue();
            _total = cnt;

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
        List<Params> params = new ArrayList<Params>();
        GenericEntity[] ents = (GenericEntity[])_finder.getEntities(start, start + cnt);

        for (GenericEntity ent : ents)
        {
            Params p = new Params();

            // 取得データのセット
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            // エリア
            p.set(AREA_NO, ent.getValue(WorkInfo.PLAN_AREA_NO));
            // エリア名称
            p.set(AREA_NAME, String.valueOf(ent.getValue(Area.AREA_NAME, "")));
            // 発行日
            p.set(SYS_DAY, getPrintDate());
            // 発行時刻
            p.set(SYS_TIME, getPrintDate());
            // ステーション
            p.set(STATION_NO, String.valueOf(ent.getValue("STATION_NO", "")));
            // ステーション名称
            p.set(STATION_NAME, String.valueOf(ent.getValue(Station.STATION_NAME, "")));
            // 作業No
            p.set(WORK_NO, String.valueOf(ent.getValue(CarryInfo.WORK_NO, "")));
            // 入庫棚
            p.set(STORAGE_LOCATION_NO, ent.getValue(WorkInfo.PLAN_LOCATION_NO));
            // 商品コード
            p.set(ITEM_CODE, ent.getValue(WorkInfo.ITEM_CODE));
            // 商品名称
            p.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
            // ロットNo.
            p.set(PLAN_LOT_NO, ent.getValue(WorkInfo.PLAN_LOT_NO));
            // ケース入数
            int enteringQty = ent.getBigDecimal(Item.ENTERING_QTY).intValue();
            p.set(ENTERING_QTY, enteringQty);
            // 予定ケース数
            p.set(STORAGE_CASE_QTY,
                    DisplayUtil.getCaseQty(ent.getBigDecimal(WorkInfo.PLAN_QTY).intValue(), enteringQty));
            // 予定ピース数
            p.set(STORAGE_PIECE_QTY, DisplayUtil.getPieceQty(ent.getBigDecimal(WorkInfo.PLAN_QTY).intValue(),
                    enteringQty));
            // JAN
            p.set(JAN, String.valueOf(ent.getValue(Item.JAN, "")));
            // ITF
            p.set(ITF, String.valueOf(ent.getValue(Item.ITF, "")));

            params.add(p);
        }
        return params;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    /**
     * @param p 検索条件を含むパラメータ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private void station(Params p)
            throws CommonException
    {
        String storageStNo = p.getString(STATION_NO);
        String retrievalStNo = null;

        // 画面で指定されたステーション
        Station dispSt = null;
        // 画面で指定されたステーションのオペレータクラス
        StationOperator stOpe = null;
        // 画面入力よりステーションを作成する
        dispSt = StationFactory.makeStation(getConnection(), storageStNo);
        // オペレータクラスを作成する 
        stOpe = StationOperatorFactory.makeOperator(getConnection(), dispSt.getStationNo(), dispSt.getClassName());

        // コの字ステーションの場合、出庫側ステーションを取得する
        if (stOpe instanceof FreeStorageStationOperator)
        {
            FreeStorageStationOperator castStOpe = (FreeStorageStationOperator)stOpe;
            retrievalStNo = castStOpe.getFreeRetrievalStationNo();
        }
        // コの字ステーション以外の場合（入出庫兼用）、
        // 画面で指定されたステーションを出庫ステーションにセットする
        else
        {
            retrievalStNo = stOpe.getStation().getStationNo();
        }

        p.set(RETRIEVALSTATION_NO, retrievalStNo);
        p.set(STORAGESTATION_NO, storageStNo);
    }

    /**
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return countSql SQL
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private StringBuffer createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        // カウントSQL
        StringBuffer countSql = null;

        countSql = searchNoPlanStorage(param);

        return countSql;
    }

    /**
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return countSql SQL
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private StringBuffer createSearchSql(Params param, boolean isSet)
            throws CommonException
    {
        // カウントSQL
        StringBuffer countSql = null;

        countSql = allcount(param);

        return countSql;
    }

    /**
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @return countSql SQL
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private StringBuffer searchNoPlanStorage(Params param)
            throws CommonException
    {
        // カウントSQL
        StringBuffer countSql = null;

        countSql = new StringBuffer();
        countSql.append("SELECT");
        countSql.append(" COLLECT_JOB_NO");
        countSql.append(" ,PLAN_AREA_NO");
        countSql.append(" ,PLAN_LOCATION_NO");
        countSql.append(" ,ITEM_CODE");
        countSql.append(" ,PLAN_LOT_NO");
        countSql.append(" ,PLAN_DAY");
        countSql.append(" ,PLAN_QTY");
        countSql.append(" ,WORK_NO");
        countSql.append(" ,AREA_NAME");
        countSql.append(" ,STATION_NO");
        countSql.append(" ,STATION_NAME");
        countSql.append(" ,ITEM_NAME");
        countSql.append(" ,ENTERING_QTY");
        countSql.append(" ,JAN");
        countSql.append(" ,ITF");
        countSql.append(" FROM");

        countSql.append(" (SELECT ");
        countSql.append(" DNWORKINFO.COLLECT_JOB_NO AS COLLECT_JOB_NO");
        countSql.append(" ,DNWORKINFO.PLAN_AREA_NO AS PLAN_AREA_NO");
        countSql.append(" ,DNWORKINFO.PLAN_LOCATION_NO AS PLAN_LOCATION_NO");
        countSql.append(" ,DNWORKINFO.ITEM_CODE AS ITEM_CODE");
        countSql.append(" ,DNWORKINFO.PLAN_LOT_NO AS PLAN_LOT_NO");
        countSql.append(" ,DNWORKINFO.PLAN_DAY AS PLAN_DAY");
        countSql.append(" ,SUM(DNWORKINFO.PLAN_QTY) AS PLAN_QTY");
        countSql.append(" ,DNCarryInfo.WORK_NO AS WORK_NO");
        countSql.append(" ,DMArea.AREA_NAME AS AREA_NAME");
        countSql.append(" ,DNCarryInfo.SOURCE_STATION_NO AS STATION_NO");
        countSql.append(" ,DMStation.STATION_NAME AS STATION_NAME");
        countSql.append(" ,DMItem.ITEM_NAME AS ITEM_NAME");
        countSql.append(" ,DMItem.ENTERING_QTY AS ENTERING_QTY");
        countSql.append(" ,DMItem.JAN AS JAN");
        countSql.append(" ,DMItem.ITF AS ITF");
        countSql.append(" FROM");
        countSql.append(" DNWORKINFO");
        countSql.append(" ,DNCARRYINFO");
        countSql.append(" ,DMITEM");
        countSql.append(" ,DMAREA");
        countSql.append(" ,DMSTATION");
        countSql.append(" WHERE");

        // エリアNo
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            countSql.append(" DNWORKINFO.PLAN_AREA_NO = ").append(DBFormat.format(param.getString(AREA_NO)));
        }
        // 検索日時
        Date[] tmp =
                WmsFormatter.getFromTo(param.getDate(FROM_SEARCH_DATE), param.getDate(FROM_SEARCH_TIME),
                        param.getDate(TO_SEARCH_DATE), param.getDate(TO_SEARCH_TIME));
        Date from = tmp[0];
        Date to = tmp[1];
        // 開始検索日時
        if (!StringUtil.isBlank(from))
        {
            countSql.append(" AND DNCARRYINFO.REGIST_DATE >= ").append(DBFormat.format(from));
        }
        // 終了検索日時
        if (!StringUtil.isBlank(to))
        {
            // 終了検索日時に1秒追加用
            Calendar curDate = Calendar.getInstance();
            curDate.setTime(to);
            curDate.add(Calendar.SECOND, +1);
            countSql.append(" AND DNCARRYINFO.REGIST_DATE < ").append(DBFormat.format(curDate.getTime()));
        }

        // 新規入庫で、搬送区分が入庫のもの（予定外入庫設定より入庫）
        countSql.append(" AND ((DNCARRYINFO.RETRIEVAL_DETAIL = ").append(
                DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_UNKNOWN));
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ").append(DBFormat.format(CarryInfo.CARRY_FLAG_STORAGE));
        countSql.append(" AND DNCARRYINFO.SOURCE_STATION_NO = ").append(
                DBFormat.format(param.getString(STORAGESTATION_NO))).append(")");

        // 積み増し入庫で、搬送区分が入庫のもの
        // 作業表示運用ありの場合は、搬送元に入庫ステーションがセットされますが
        // 作業表示運用なしの場合は、タイミングによって、
        // 入庫側ステーションがセットされている状態と、出庫がわステーションがセットされている状態があります。
        countSql.append(" OR (DNCARRYINFO.RETRIEVAL_DETAIL = ").append(
                DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING));
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ").append(DBFormat.format(CarryInfo.CARRY_FLAG_STORAGE));
        countSql.append(" AND (DNCARRYINFO.SOURCE_STATION_NO = ").append(
                DBFormat.format(param.getString(RETRIEVALSTATION_NO))).append("");
        countSql.append(" OR DNCARRYINFO.SOURCE_STATION_NO = ").append(
                DBFormat.format(param.getString(STORAGESTATION_NO))).append(")))");

        // 作業区分
        countSql.append(" AND DNWORKINFO.JOB_TYPE = ").append(DBFormat.format(SystemDefine.JOB_TYPE_NOPLAN_STORAGE));
        // ハードウェア区分 : AS/RS
        countSql.append(" AND DNWORKINFO.HARDWARE_TYPE = ").append(DBFormat.format(SystemDefine.HARDWARE_TYPE_ASRS));
        // 状態フラグ
        countSql.append(" AND DNWORKINFO.STATUS_FLAG = ").append(DBFormat.format(SystemDefine.STATUS_FLAG_NOWWORKING));

        // 結合条件
        countSql.append(" AND DNWORKINFO.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE( + )");
        countSql.append(" AND DNWORKINFO.ITEM_CODE = DMITEM.ITEM_CODE( + )");
        countSql.append(" AND DNWORKINFO.PLAN_AREA_NO = DMAREA.AREA_NO( + )");
        countSql.append(" AND DNWORKINFO.SYSTEM_CONN_KEY = DNCARRYINFO.CARRY_KEY( + )");
        countSql.append(" AND DNCARRYINFO.SOURCE_STATION_NO = DMSTATION.STATION_NO( + )");

        // 集約条件
        countSql.append(" GROUP BY");
        countSql.append(" DNWORKINFO.COLLECT_JOB_NO");
        countSql.append(" ,DNWORKINFO.PLAN_AREA_NO");
        countSql.append(" ,DNWORKINFO.PLAN_LOCATION_NO");
        countSql.append(" ,DNWORKINFO.ITEM_CODE");
        countSql.append(" ,DNWORKINFO.PLAN_LOT_NO");
        countSql.append(" ,DNWORKINFO.PLAN_DAY");
        countSql.append(" ,DNCARRYINFO.WORK_NO");
        countSql.append(" ,DMArea.AREA_NAME");
        countSql.append(" ,DNCARRYINFO.SOURCE_STATION_NO");
        countSql.append(" ,DMStation.STATION_NAME");
        countSql.append(" ,DMITEM.ITEM_NAME");
        countSql.append(" ,DMITEM.ENTERING_QTY");
        countSql.append(" ,DMITEM.JAN");
        countSql.append(" ,DMITEM.ITF");

        countSql.append(" UNION");

        countSql.append(" SELECT");
        countSql.append(" DNWORKINFO.COLLECT_JOB_NO AS COLLECT_JOB_NO");
        countSql.append(" ,DNWORKINFO.PLAN_AREA_NO AS PLAN_AREA_NO");
        countSql.append(" ,DNWORKINFO.PLAN_LOCATION_NO AS PLAN_LOCATION_NO");
        countSql.append(" ,DNWORKINFO.ITEM_CODE AS ITEM_CODE");
        countSql.append(" ,DNWORKINFO.PLAN_LOT_NO AS PLAN_LOT_NO");
        countSql.append(" ,DNWORKINFO.PLAN_DAY AS PLAN_DAY");
        countSql.append(" ,SUM(DNWORKINFO.PLAN_QTY) AS PLAN_QTY");
        countSql.append(" ,DNCARRYINFO.WORK_NO AS WORK_NO");
        countSql.append(" ,DMArea.AREA_NAME AS AREA_NAME");
        countSql.append(" ,DNCARRYINFO.DEST_STATION_NO AS STATION_NO");
        countSql.append(" ,DMSTATION.STATION_NAME AS STATION_NAME");
        countSql.append(" ,DMITEM.ITEM_NAME AS ITEM_NAME");
        countSql.append(" ,DMITEM.ENTERING_QTY AS ENTERING_QTY");
        countSql.append(" ,DMITEM.JAN AS JAN");
        countSql.append(" ,DMITEM.ITF AS ITF");
        countSql.append(" FROM");
        countSql.append(" DNWORKINFO");
        countSql.append(" ,DNCARRYINFO");
        countSql.append(" ,DMITEM");
        countSql.append(" ,DMAREA");
        countSql.append(" ,DMSTATION");
        countSql.append(" WHERE");

        // エリアNo
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            countSql.append(" DNWORKINFO.PLAN_AREA_NO = ").append(DBFormat.format(param.getString(AREA_NO)));
        }
        // 開始検索日時
        if (!StringUtil.isBlank(from))
        {
            countSql.append(" AND DNCARRYINFO.REGIST_DATE >= ").append(DBFormat.format(from));
        }
        // 終了検索日時
        if (!StringUtil.isBlank(to))
        {
            // 終了検索日時に1秒追加用
            Calendar curDate = Calendar.getInstance();
            curDate.setTime(to);
            curDate.add(Calendar.SECOND, +1);
            countSql.append(" AND DNCARRYINFO.REGIST_DATE < ").append(DBFormat.format(curDate.getTime()));
        }

        // 積み増し入庫で、搬送区分が出庫のもの
        countSql.append(" AND ((DNCARRYINFO.RETRIEVAL_DETAIL = ").append(
                DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING));
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ").append(DBFormat.format(CarryInfo.CARRY_FLAG_RETRIEVAL));
        countSql.append(" AND DNCARRYINFO.DEST_STATION_NO = ").append(
                DBFormat.format(param.getString(RETRIEVALSTATION_NO))).append(")");

        // 積み増し入庫で、搬送区分が入庫のもの
        // 作業表示運用ありの場合は、搬送元に入庫ステーションがセットされますが
        // 作業表示運用なしの場合は、タイミングによって、
        // 入庫側ステーションがセットされている状態と、出庫がわステーションがセットされている状態があります。
        countSql.append(" OR (DNCARRYINFO.RETRIEVAL_DETAIL = ").append(
                DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING));
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ").append(DBFormat.format(CarryInfo.CARRY_FLAG_STORAGE));
        countSql.append(" AND (DNCARRYINFO.SOURCE_STATION_NO = ").append(
                DBFormat.format(param.getString(RETRIEVALSTATION_NO)));
        countSql.append(" OR DNCARRYINFO.SOURCE_STATION_NO = ").append(
                DBFormat.format(param.getString(STORAGESTATION_NO))).append(")))");

        // 作業区分
        countSql.append(" AND DNWORKINFO.JOB_TYPE = ").append(DBFormat.format(SystemDefine.JOB_TYPE_NOPLAN_STORAGE));
        // ハードウェア区分 : AS/RS
        countSql.append(" AND DNWORKINFO.HARDWARE_TYPE = ").append(DBFormat.format(SystemDefine.HARDWARE_TYPE_ASRS));
        // 状態フラグ
        countSql.append(" AND DNWORKINFO.STATUS_FLAG = ").append(DBFormat.format(SystemDefine.STATUS_FLAG_NOWWORKING));

        // 結合条件
        countSql.append(" AND DNWORKINFO.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE( + )");
        countSql.append(" AND DNWORKINFO.ITEM_CODE = DMITEM.ITEM_CODE( + )");
        countSql.append(" AND DNWORKINFO.PLAN_AREA_NO = DMAREA.AREA_NO( + )");
        countSql.append(" AND DNWORKINFO.SYSTEM_CONN_KEY = DNCARRYINFO.CARRY_KEY( + )");
        countSql.append(" AND DNCARRYINFO.DEST_STATION_NO = DMSTATION.STATION_NO( + )");

        // 集約条件
        countSql.append(" GROUP BY");
        countSql.append(" DNWORKINFO.COLLECT_JOB_NO");
        countSql.append(" ,DNWORKINFO.PLAN_AREA_NO");
        countSql.append(" ,DNWORKINFO.PLAN_LOCATION_NO");
        countSql.append(" ,DNWORKINFO.ITEM_CODE");
        countSql.append(" ,DNWORKINFO.PLAN_LOT_NO");
        countSql.append(" ,DNWORKINFO.PLAN_DAY");
        countSql.append(" ,DNCarryInfo.WORK_NO");
        countSql.append(" ,DMAREA.AREA_NAME");
        countSql.append(" ,DNCarryInfo.DEST_STATION_NO");
        countSql.append(" ,DMSTATION.STATION_NAME");
        countSql.append(" ,DMITEM.ITEM_NAME");
        countSql.append(" ,DMITEM.ENTERING_QTY");
        countSql.append(" ,DMITEM.JAN");
        countSql.append(" ,DMITEM.ITF");
        countSql.append(" ) ");

        // 表示順
        countSql.append(" ORDER BY");
        countSql.append(" WORK_NO");
        countSql.append(" ,ITEM_CODE");
        countSql.append(" ,PLAN_LOT_NO");

        return countSql;
    }

    /**
     * データ件数を取得します。<BR>
     * @param param 検索条件を保持したAsrsInParameterオブジェクト
     * @return cnt データ件数
     * @throws CommonException データベースエラーが発生した場合にスローされます。
     */
    protected StringBuffer allcount(Params param)
            throws CommonException
    {
        // カウントSQL
        StringBuffer countSql = null;

        countSql = new StringBuffer();
        countSql.append("SELECT");
        countSql.append(" SUM(CNT) AS CNT");
        countSql.append(" FROM");
        countSql.append(" (SELECT");
        countSql.append(" COUNT(*) as CNT");
        countSql.append(" FROM");
        countSql.append(" DNWORKINFO");
        countSql.append(" ,DNCARRYINFO");
        countSql.append(" ,DMITEM");
        countSql.append(" ,DMAREA");
        countSql.append(" ,DMSTATION");
        countSql.append(" WHERE");

        // エリアNo
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            countSql.append(" DNWORKINFO.PLAN_AREA_NO = ").append(DBFormat.format(param.getString(AREA_NO)));
        }
        // 検索日時
        Date[] tmp =
                WmsFormatter.getFromTo(param.getDate(FROM_SEARCH_DATE), param.getDate(FROM_SEARCH_TIME),
                        param.getDate(TO_SEARCH_DATE), param.getDate(TO_SEARCH_TIME));
        Date from = tmp[0];
        Date to = tmp[1];
        // 開始検索日時
        if (!StringUtil.isBlank(from))
        {
            countSql.append(" AND DNCARRYINFO.REGIST_DATE >= ").append(DBFormat.format(from));
        }
        // 終了検索日時
        if (!StringUtil.isBlank(to))
        {
            // 終了検索日時に1秒追加用
            Calendar curDate = Calendar.getInstance();
            curDate.setTime(to);
            curDate.add(Calendar.SECOND, +1);
            countSql.append(" AND DNCARRYINFO.REGIST_DATE < ").append(DBFormat.format(curDate.getTime()));
        }

        // 新規入庫で、搬送区分が入庫のもの（予定外入庫設定より入庫）
        countSql.append(" AND ((DNCARRYINFO.RETRIEVAL_DETAIL = ").append(
                DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_UNKNOWN));
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ").append(DBFormat.format(CarryInfo.CARRY_FLAG_STORAGE));
        countSql.append(" AND DNCARRYINFO.SOURCE_STATION_NO = ").append(
                DBFormat.format(param.getString(STORAGESTATION_NO))).append(")");

        // 積み増し入庫で、搬送区分が入庫のもの
        // 作業表示運用ありの場合は、搬送元に入庫ステーションがセットされますが
        // 作業表示運用なしの場合は、タイミングによって、
        // 入庫側ステーションがセットされている状態と、出庫がわステーションがセットされている状態があります。
        countSql.append(" OR (DNCARRYINFO.RETRIEVAL_DETAIL = '").append(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING).append(
                "'");
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ").append(DBFormat.format(CarryInfo.CARRY_FLAG_STORAGE));
        countSql.append(" AND (DNCARRYINFO.SOURCE_STATION_NO = ").append(
                DBFormat.format(param.getString(RETRIEVALSTATION_NO)));
        countSql.append(" OR DNCARRYINFO.SOURCE_STATION_NO = ").append(
                DBFormat.format(param.getString(STORAGESTATION_NO))).append(")))");

        // 作業区分
        countSql.append(" AND DNWORKINFO.JOB_TYPE = ").append(DBFormat.format(SystemDefine.JOB_TYPE_NOPLAN_STORAGE));
        // ハードウェア区分 : AS/RS
        countSql.append(" AND DNWORKINFO.HARDWARE_TYPE = ").append(DBFormat.format(SystemDefine.HARDWARE_TYPE_ASRS));
        // 状態フラグ
        countSql.append(" AND DNWORKINFO.STATUS_FLAG = ").append(DBFormat.format(SystemDefine.STATUS_FLAG_NOWWORKING));

        // 結合条件
        countSql.append(" AND DNWORKINFO.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE( + )");
        countSql.append(" AND DNWORKINFO.ITEM_CODE = DMITEM.ITEM_CODE( + )");
        countSql.append(" AND DNWORKINFO.PLAN_AREA_NO = DMAREA.AREA_NO( + )");
        countSql.append(" AND DNWORKINFO.SYSTEM_CONN_KEY = DNCARRYINFO.CARRY_KEY( + )");
        countSql.append(" AND DNCARRYINFO.SOURCE_STATION_NO = DMSTATION.STATION_NO( + )").append(")");

        countSql.append(" UNION ALL");

        countSql.append(" SELECT");
        countSql.append(" COUNT(*) as CNT");
        countSql.append(" FROM");
        countSql.append(" DNWORKINFO");
        countSql.append(" ,DNCARRYINFO");
        countSql.append(" ,DMITEM");
        countSql.append(" ,DMAREA");
        countSql.append(" ,DMSTATION");
        countSql.append(" WHERE");

        // エリアNo
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            countSql.append(" DNWORKINFO.PLAN_AREA_NO = ").append(DBFormat.format(param.getString(AREA_NO)));
        }
        // 開始検索日時
        if (!StringUtil.isBlank(from))
        {
            countSql.append(" AND DNCARRYINFO.REGIST_DATE >= ").append(DBFormat.format(from));
        }
        // 終了検索日時
        if (!StringUtil.isBlank(to))
        {
            // 終了検索日時に1秒追加用
            Calendar curDate = Calendar.getInstance();
            curDate.setTime(to);
            curDate.add(Calendar.SECOND, +1);
            countSql.append(" AND DNCARRYINFO.REGIST_DATE < ").append(DBFormat.format(curDate.getTime()));
        }

        // 積み増し入庫で、搬送区分が出庫のもの
        countSql.append(" AND ((DNCARRYINFO.RETRIEVAL_DETAIL = ").append(
                DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING));
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ").append(DBFormat.format(CarryInfo.CARRY_FLAG_RETRIEVAL));
        countSql.append(" AND DNCARRYINFO.DEST_STATION_NO = ").append(
                DBFormat.format(param.getString(RETRIEVALSTATION_NO))).append(")");

        // 積み増し入庫で、搬送区分が入庫のもの
        // 作業表示運用ありの場合は、搬送元に入庫ステーションがセットされますが
        // 作業表示運用なしの場合は、タイミングによって、
        // 入庫側ステーションがセットされている状態と、出庫がわステーションがセットされている状態があります。
        countSql.append(" OR (DNCARRYINFO.RETRIEVAL_DETAIL = ").append(
                DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING));
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ").append(DBFormat.format(CarryInfo.CARRY_FLAG_STORAGE));
        countSql.append(" AND (DNCARRYINFO.SOURCE_STATION_NO = ").append(
                DBFormat.format(param.getString(RETRIEVALSTATION_NO)));
        countSql.append(" OR DNCARRYINFO.SOURCE_STATION_NO = ").append(
                DBFormat.format(param.getString(STORAGESTATION_NO))).append(")))");

        // 作業区分
        countSql.append(" AND DNWORKINFO.JOB_TYPE = ").append(DBFormat.format(SystemDefine.JOB_TYPE_NOPLAN_STORAGE));
        // ハードウェア区分 : AS/RS
        countSql.append(" AND DNWORKINFO.HARDWARE_TYPE = ").append(DBFormat.format(SystemDefine.HARDWARE_TYPE_ASRS));
        // 状態フラグ
        countSql.append(" AND DNWORKINFO.STATUS_FLAG = ").append(DBFormat.format(SystemDefine.STATUS_FLAG_NOWWORKING));

        // 結合条件
        countSql.append(" AND DNWORKINFO.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE( + )");
        countSql.append(" AND DNWORKINFO.ITEM_CODE = DMITEM.ITEM_CODE( + )");
        countSql.append(" AND DNWORKINFO.PLAN_AREA_NO = DMAREA.AREA_NO( + )");
        countSql.append(" AND DNWORKINFO.SYSTEM_CONN_KEY = DNCARRYINFO.CARRY_KEY( + )");
        countSql.append(" AND DNCARRYINFO.DEST_STATION_NO = DMSTATION.STATION_NO( + )");

        return countSql;
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
