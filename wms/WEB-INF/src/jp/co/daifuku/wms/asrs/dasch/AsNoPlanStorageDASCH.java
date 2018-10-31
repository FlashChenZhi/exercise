package jp.co.daifuku.wms.asrs.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.dasch.AsNoPlanStorageDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.GenericEntity;
import jp.co.daifuku.wms.handler.db.DefaultDDBFinder;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * AS/RS 予定外入庫設定に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:02:35 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class AsNoPlanStorageDASCH
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
    public AsNoPlanStorageDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // TODO : Implement for export
        //throw new ScheduleException("This method is not implemented.");
        // get Next entity from finder class
        GenericEntity[] ents = (GenericEntity[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (GenericEntity ent : ents)
        {
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());

            p.set(AREA_NO, ent.getValue(WorkInfo.PLAN_AREA_NO, ""));
            p.set(AREA_NAME, ent.getValue(Area.AREA_NAME, ""));
            p.set(STATION_NO, ent.getValue("STATION_NO"));
            p.set(STATION_NAME, ent.getValue(Station.STATION_NAME, ""));
            p.set(JOB_NO, ent.getValue(CarryInfo.WORK_NO));
            p.set(LOCATION_NO, ent.getValue(WorkInfo.PLAN_LOCATION_NO, ""));
            p.set(ITEM_CODE, ent.getValue(WorkInfo.ITEM_CODE, ""));
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME, ""));
            p.set(LOT_NO, ent.getValue(WorkInfo.PLAN_LOT_NO, ""));
            p.set(ENTERING_QTY, ent.getBigDecimal(Item.ENTERING_QTY).intValue());
            p.set(STORAGE_CASE_QTY, DisplayUtil.getCaseQty(ent.getBigDecimal(WorkInfo.PLAN_QTY).intValue(),
                    ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            p.set(STORAGE_PIECE_QTY, DisplayUtil.getPieceQty(ent.getBigDecimal(WorkInfo.PLAN_QTY).intValue(),
                    ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            p.set(JAN, ent.getValue(Item.JAN, ""));
            p.set(ITF, ent.getValue(Item.ITF, ""));
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
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        // TODO : Implement for listcell
        //throw new RuntimeException("This method is not implemented.");
        List<Params> params = new ArrayList<Params>();
        GenericEntity[] ents = (GenericEntity[])_finder.getEntities(start, start + cnt);

        for (GenericEntity ent : ents)
        {
            Params p = new Params();

            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());

            p.set(AREA_NO, ent.getValue(WorkInfo.PLAN_AREA_NO, ""));
            p.set(AREA_NAME, ent.getValue(Area.AREA_NAME, ""));
            p.set(STATION_NO, ent.getValue("STATION_NO"));
            p.set(STATION_NAME, ent.getValue(Station.STATION_NAME, ""));
            p.set(JOB_NO, ent.getValue(CarryInfo.WORK_NO));
            p.set(LOCATION_NO, ent.getValue(WorkInfo.PLAN_LOCATION_NO, ""));
            p.set(ITEM_CODE, ent.getValue(WorkInfo.ITEM_CODE, ""));
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME, ""));
            p.set(LOT_NO, ent.getValue(WorkInfo.PLAN_LOT_NO, ""));
            p.set(ENTERING_QTY, ent.getBigDecimal(Item.ENTERING_QTY).intValue());
            p.set(STORAGE_CASE_QTY, DisplayUtil.getCaseQty(ent.getBigDecimal(WorkInfo.PLAN_QTY).intValue(),
                    ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            p.set(STORAGE_PIECE_QTY, DisplayUtil.getPieceQty(ent.getBigDecimal(WorkInfo.PLAN_QTY).intValue(),
                    ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            p.set(JAN, ent.getValue(Item.JAN, ""));
            p.set(ITF, ent.getValue(Item.ITF, ""));

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
    private StringBuffer createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        // カウントSQL
        StringBuffer countSql = null;

        if (SystemDefine.WORK_TYPE_ADD_STORAGE.equals(param.getString(WORK_TYPE)))
        {
            countSql = setAddNoPlanStorageSql(param);
        }
        else if (SystemDefine.WORK_TYPE_NOPLAN_STORAGE.equals(param.getString(WORK_TYPE)))
        {
            countSql = setNoPlanStorageSql(param);
        }
        else
        {
            countSql = setSql(param);
        }

        return countSql;
    }

    /**
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     */
    private StringBuffer createSearchSql(Params param, boolean isSet)
            throws CommonException
    {
        // カウントSQL
        StringBuffer countSql = null;

        if (SystemDefine.WORK_TYPE_ADD_STORAGE.equals(param.getString(WORK_TYPE)))
        {
            countSql = addNoPlanStorageCount(param);
        }
        else if (SystemDefine.WORK_TYPE_NOPLAN_STORAGE.equals(param.getString(WORK_TYPE)))
        {
            countSql = noPlanStorageCount(param);
        }
        else
        {
            countSql = allCount(param);
        }

        return countSql;
    }

    /**
     * AS/RS 予定外入庫(積み増し)作業情報を取得するための検索キーをセットするメソッドです。<BR>
     * <BR>
     * @return countSql AS/RS 予定外入庫(積み増し)作業情報を取得するための検索キークラスのインスタンス
     * @throws ReadWriteException データベース処理でエラー発生した場合にthrowします。
     */
    protected StringBuffer setAddNoPlanStorageSql(Params param)
            throws ReadWriteException
    {
        // StringBuffer情報
        StringBuffer countSql = new StringBuffer();

        // 取得項目
        countSql.append("SELECT");
        countSql.append(" DNWORKINFO.COLLECT_JOB_NO AS COLLECT_JOB_NO");
        countSql.append(" ,DNWORKINFO.PLAN_AREA_NO AS PLAN_AREA_NO");
        countSql.append(" ,DNWORKINFO.PLAN_LOCATION_NO AS PLAN_LOCATION_NO");
        countSql.append(" ,DNWORKINFO.ITEM_CODE AS ITEM_CODE");
        countSql.append(" ,DNWORKINFO.PLAN_LOT_NO AS PLAN_LOT_NO");
        countSql.append(" ,DNWORKINFO.PLAN_DAY AS PLAN_DAY");
        countSql.append(" ,SUM(DNWORKINFO.PLAN_QTY) AS PLAN_QTY");
        countSql.append(" ,DNCARRYINFO.WORK_NO AS WORK_NO");
        countSql.append(" ,DMAREA.AREA_NAME AS AREA_NAME");
        countSql.append(" ,DNCARRYINFO.DEST_STATION_NO AS STATION_NO");
        countSql.append(" ,DMSTATION.STATION_NAME AS STATION_NAME");
        countSql.append(" ,DMITEM.ITEM_NAME AS ITEM_NAME");
        countSql.append(" ,DMITEM.ENTERING_QTY AS ENTERING_QTY");
        countSql.append(" ,DMITEM.JAN AS JAN");
        countSql.append(" ,DMITEM.ITF AS ITF ");

        // 取得元テーブル
        countSql.append("FROM");
        countSql.append(" DNWORKINFO");
        countSql.append(" ,DNCARRYINFO");
        countSql.append(" ,DMITEM");
        countSql.append(" ,DMAREA");
        countSql.append(" ,DMSTATION ");

        // 取得条件
        countSql.append("WHERE");
        // 作業区分
        countSql.append(" DNWORKINFO.JOB_TYPE = ");
        countSql.append(DBFormat.format(SystemDefine.JOB_TYPE_NOPLAN_STORAGE));
        // ハードウェア区分 : AS/RS
        countSql.append(" AND DNWORKINFO.HARDWARE_TYPE = ");
        countSql.append(DBFormat.format(SystemDefine.HARDWARE_TYPE_ASRS));
        // 状態フラグ
        countSql.append(" AND DNWORKINFO.STATUS_FLAG = ");
        countSql.append(DBFormat.format(SystemDefine.STATUS_FLAG_NOWWORKING));
        // 設定単位キー
        if (!StringUtil.isBlank(param.getString(SETTING_UNITKEY)))
        {
            countSql.append(" AND DNWORKINFO.SETTING_UNIT_KEY = ");
            countSql.append(DBFormat.format(param.getString(SETTING_UNITKEY)));
        }
        // 積み増し入庫で、搬送区分が出庫のもの
        countSql.append(" AND ((DNCARRYINFO.RETRIEVAL_DETAIL = ");
        countSql.append(DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING));
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ");
        countSql.append(DBFormat.format(CarryInfo.CARRY_FLAG_RETRIEVAL));
        countSql.append(" AND DNCARRYINFO.DEST_STATION_NO = ");
        countSql.append(DBFormat.format(param.getString(RETRIEVAL_STATION_NO)));
        countSql.append(")");
        // 積み増し入庫で、搬送区分が入庫のもの
        // 作業表示運用ありの場合は、搬送元に入庫ステーションがセットされますが
        // 作業表示運用なしの場合は、タイミングによって、
        // 入庫側ステーションがセットされている状態と、出庫側ステーションがセットされている状態があります。
        countSql.append(" OR (DNCARRYINFO.RETRIEVAL_DETAIL = ");
        countSql.append(DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING));
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ");
        countSql.append(DBFormat.format(CarryInfo.CARRY_FLAG_STORAGE));
        countSql.append(" AND (DNCARRYINFO.SOURCE_STATION_NO = ");
        countSql.append(DBFormat.format(param.getString(RETRIEVAL_STATION_NO)));
        countSql.append(" OR DNCARRYINFO.SOURCE_STATION_NO = ");
        countSql.append(DBFormat.format(param.getString(STORAGE_STATION_NO)));
        countSql.append(")))");

        // 結合条件
        countSql.append(" AND DNWORKINFO.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE(+)");
        countSql.append(" AND DNWORKINFO.ITEM_CODE = DMITEM.ITEM_CODE(+)");
        countSql.append(" AND DNWORKINFO.PLAN_AREA_NO = DMAREA.AREA_NO(+)");
        countSql.append(" AND DNWORKINFO.SYSTEM_CONN_KEY = DNCARRYINFO.CARRY_KEY(+)");
        countSql.append(" AND DNCARRYINFO.DEST_STATION_NO = DMSTATION.STATION_NO(+) ");

        // 集約条件
        countSql.append("GROUP BY");
        countSql.append(" DNWORKINFO.COLLECT_JOB_NO");
        countSql.append(" ,DNWORKINFO.PLAN_AREA_NO");
        countSql.append(" ,DNWORKINFO.PLAN_LOCATION_NO");
        countSql.append(" ,DNWORKINFO.ITEM_CODE");
        countSql.append(" ,DNWORKINFO.PLAN_LOT_NO");
        countSql.append(" ,DNWORKINFO.PLAN_DAY");
        countSql.append(" ,DNCARRYINFO.WORK_NO");
        countSql.append(" ,DMAREA.AREA_NAME");
        countSql.append(" ,DNCARRYINFO.DEST_STATION_NO");
        countSql.append(" ,DMSTATION.STATION_NAME");
        countSql.append(" ,DMITEM.ITEM_NAME");
        countSql.append(" ,DMITEM.ENTERING_QTY");
        countSql.append(" ,DMITEM.JAN");
        countSql.append(" ,DMITEM.ITF ");

        // 表示順
        countSql.append("ORDER BY");
        countSql.append(" STATION_NO");
        countSql.append(" ,WORK_NO");
        countSql.append(" ,ITEM_CODE");
        countSql.append(" ,PLAN_LOT_NO");

        return countSql;
    }

    /**
     * AS/RS 予定外入庫作業情報を取得するための検索キーをセットするメソッドです。<BR>
     * <BR>
     * @return countSql AS/RS 予定外入庫作業情報を取得するための検索キークラスのインスタンス
     * @throws ReadWriteException データベース処理でエラー発生した場合にthrowします。
     */
    protected StringBuffer setNoPlanStorageSql(Params param)
            throws ReadWriteException
    {
        // StringBuffer情報
        StringBuffer countSql = new StringBuffer();

        // 取得項目
        countSql.append("SELECT");
        countSql.append(" DNWORKINFO.COLLECT_JOB_NO AS COLLECT_JOB_NO");
        countSql.append(" ,DNWORKINFO.PLAN_AREA_NO AS PLAN_AREA_NO");
        countSql.append(" ,DNWORKINFO.PLAN_LOCATION_NO AS PLAN_LOCATION_NO");
        countSql.append(" ,DNWORKINFO.ITEM_CODE AS ITEM_CODE");
        countSql.append(" ,DNWORKINFO.PLAN_LOT_NO AS PLAN_LOT_NO");
        countSql.append(" ,DNWORKINFO.PLAN_DAY AS PLAN_DAY");
        countSql.append(" ,SUM(DNWORKINFO.PLAN_QTY) AS PLAN_QTY");
        countSql.append(" ,DNCARRYINFO.WORK_NO AS WORK_NO");
        countSql.append(" ,DMAREA.AREA_NAME AS AREA_NAME");
        countSql.append(" ,DNCARRYINFO.SOURCE_STATION_NO AS STATION_NO");
        countSql.append(" ,DMSTATION.STATION_NAME AS STATION_NAME");
        countSql.append(" ,DMITEM.ITEM_NAME AS ITEM_NAME");
        countSql.append(" ,DMITEM.ENTERING_QTY AS ENTERING_QTY");
        countSql.append(" ,DMITEM.JAN AS JAN");
        countSql.append(" ,DMITEM.ITF AS ITF ");

        // 取得元テーブル
        countSql.append("FROM");
        countSql.append(" DNWORKINFO");
        countSql.append(" ,DNCARRYINFO");
        countSql.append(" ,DMITEM");
        countSql.append(" ,DMAREA");
        countSql.append(" ,DMSTATION ");

        // 取得条件
        countSql.append("WHERE");
        // 作業区分
        countSql.append(" DNWORKINFO.JOB_TYPE = ");
        countSql.append(DBFormat.format(SystemDefine.JOB_TYPE_NOPLAN_STORAGE));
        // ハードウェア区分 : AS/RS
        countSql.append(" AND DNWORKINFO.HARDWARE_TYPE = ");
        countSql.append(DBFormat.format(SystemDefine.HARDWARE_TYPE_ASRS));
        // 状態フラグ 
        countSql.append(" AND DNWORKINFO.STATUS_FLAG = ");
        countSql.append(DBFormat.format(SystemDefine.STATUS_FLAG_NOWWORKING));
        // 設定単位キー
        if (!StringUtil.isBlank(param.getString(SETTING_UNITKEY)))
        {
            countSql.append(" AND DNWORKINFO.SETTING_UNIT_KEY = ");
            countSql.append(DBFormat.format(param.getString(SETTING_UNITKEY)));
        }

        // 結合条件
        countSql.append(" AND DNWORKINFO.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE(+)");
        countSql.append(" AND DNWORKINFO.ITEM_CODE = DMITEM.ITEM_CODE(+)");
        countSql.append(" AND DNWORKINFO.PLAN_AREA_NO = DMAREA.AREA_NO(+)");
        countSql.append(" AND DNWORKINFO.SYSTEM_CONN_KEY = DNCARRYINFO.CARRY_KEY(+)");
        countSql.append(" AND DNCARRYINFO.SOURCE_STATION_NO = DMSTATION.STATION_NO(+) ");

        // 集約条件
        countSql.append("GROUP BY");
        countSql.append(" DNWORKINFO.COLLECT_JOB_NO");
        countSql.append(" ,DNWORKINFO.PLAN_AREA_NO");
        countSql.append(" ,DNWORKINFO.PLAN_LOCATION_NO");
        countSql.append(" ,DNWORKINFO.ITEM_CODE");
        countSql.append(" ,DNWORKINFO.PLAN_LOT_NO");
        countSql.append(" ,DNWORKINFO.PLAN_DAY");
        countSql.append(" ,DNCARRYINFO.WORK_NO");
        countSql.append(" ,DMAREA.AREA_NAME");
        countSql.append(" ,DNCARRYINFO.SOURCE_STATION_NO");
        countSql.append(" ,DMSTATION.STATION_NAME");
        countSql.append(" ,DMITEM.ITEM_NAME");
        countSql.append(" ,DMITEM.ENTERING_QTY");
        countSql.append(" ,DMITEM.JAN");
        countSql.append(" ,DMITEM.ITF ");

        // 表示順
        countSql.append("ORDER BY");
        countSql.append(" STATION_NO");
        countSql.append(" ,WORK_NO");
        countSql.append(" ,ITEM_CODE");
        countSql.append(" ,PLAN_LOT_NO");

        return countSql;
    }


    /**
     * AS/RS 予定外入庫、予定外入庫(積み増し)作業情報を取得するための検索キーをセットするメソッドです。<BR>
     * <BR>
     * @return countSql AS/RS 予定外入庫、予定外入庫(積み増し)作業情報を取得するための検索キークラスのインスタンス
     * @throws ReadWriteException データベース処理でエラー発生した場合にthrowします。
     */
    protected StringBuffer setSql(Params param)
            throws ReadWriteException
    {
        // StringBuffer情報
        StringBuffer countSql = new StringBuffer();

        // 取得項目
        countSql.append("SELECT");
        countSql.append(" COLLECT_JOB_NO");
        countSql.append(" ,PLAN_AREA_NO");
        countSql.append(" ,PLAN_LOCATION_NO");
        countSql.append(" ,ITEM_CODE");
        countSql.append(" ,PLAN_LOT_NO");
        countSql.append(" ,PLAN_DAY");
        countSql.append(" ,PLAN_QTY");
        countSql.append(" ,SETTING_UNIT_KEY");
        countSql.append(" ,WORK_NO");
        countSql.append(" ,AREA_NAME");
        countSql.append(" ,STATION_NO");
        countSql.append(" ,STATION_NAME");
        countSql.append(" ,ITEM_NAME");
        countSql.append(" ,ENTERING_QTY");
        countSql.append(" ,JAN");
        countSql.append(" ,ITF ");

        // 取得元テーブル
        countSql.append(" FROM");

        // 取得項目
        countSql.append(" (SELECT");
        countSql.append(" DNWORKINFO.COLLECT_JOB_NO AS COLLECT_JOB_NO");
        countSql.append(" ,DNWORKINFO.PLAN_AREA_NO AS PLAN_AREA_NO");
        countSql.append(" ,DNWORKINFO.PLAN_LOCATION_NO AS PLAN_LOCATION_NO");
        countSql.append(" ,DNWORKINFO.ITEM_CODE AS ITEM_CODE");
        countSql.append(" ,DNWORKINFO.PLAN_LOT_NO AS PLAN_LOT_NO");
        countSql.append(" ,DNWORKINFO.PLAN_DAY AS PLAN_DAY");
        countSql.append(" ,SUM(DNWORKINFO.PLAN_QTY) AS PLAN_QTY");
        countSql.append(" ,DNWORKINFO.SETTING_UNIT_KEY AS SETTING_UNIT_KEY");
        countSql.append(" ,DNCARRYINFO.WORK_NO AS WORK_NO");
        countSql.append(" ,DMAREA.AREA_NAME AS AREA_NAME");
        countSql.append(" ,DNCARRYINFO.SOURCE_STATION_NO AS STATION_NO");
        countSql.append(" ,DMSTATION.STATION_NAME AS STATION_NAME");
        countSql.append(" ,DMITEM.ITEM_NAME AS ITEM_NAME");
        countSql.append(" ,DMITEM.ENTERING_QTY AS ENTERING_QTY");
        countSql.append(" ,DMITEM.JAN AS JAN");
        countSql.append(" ,DMITEM.ITF AS ITF");
        // 取得元テーブル
        countSql.append(" FROM");
        countSql.append(" DNWORKINFO");
        countSql.append(" ,DNCARRYINFO");
        countSql.append(" ,DMITEM");
        countSql.append(" ,DMAREA");
        countSql.append(" ,DMSTATION");
        // 取得条件
        countSql.append(" WHERE");
        // 作業区分
        countSql.append(" DNWORKINFO.JOB_TYPE = ");
        countSql.append(DBFormat.format(SystemDefine.JOB_TYPE_NOPLAN_STORAGE));
        // ハードウェア区分 : AS/RS
        countSql.append(" AND DNWORKINFO.HARDWARE_TYPE = ");
        countSql.append(DBFormat.format(SystemDefine.HARDWARE_TYPE_ASRS));
        // 状態フラグ
        countSql.append(" AND DNWORKINFO.STATUS_FLAG = ");
        countSql.append(DBFormat.format(SystemDefine.STATUS_FLAG_NOWWORKING));
        // 設定単位キー
        if (!StringUtil.isBlank(param.getString(SETTING_UNITKEY)))
        {
            countSql.append(" AND DNWORKINFO.SETTING_UNIT_KEY = ");
            countSql.append(DBFormat.format(param.getString(SETTING_UNITKEY)));
        }
        // エリアNo
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            countSql.append(" AND DNWORKINFO.PLAN_AREA_NO = ");
            countSql.append(DBFormat.format(param.getString(AREA_NO)));
        }
        // 開始検索日時
        if (!StringUtil.isBlank(param.getString(SEARCH_DATE)))
        {
            countSql.append(" AND DNCARRYINFO.REGIST_DATE >= ");
            countSql.append(DBFormat.format(param.getDate(SEARCH_DATE)));
        }
        // 終了検索日時
        if (!StringUtil.isBlank(param.getString(TO_SEARCH_DATE)))
        {
            // 終了検索日時に1秒追加用
            Calendar curDate = Calendar.getInstance();
            curDate.setTime(param.getDate(TO_SEARCH_DATE));
            curDate.add(Calendar.SECOND, +1);

            countSql.append(" AND DNCARRYINFO.REGIST_DATE < ");
            countSql.append(DBFormat.format(curDate.getTime()));
        }
        // 新規入庫で、搬送区分が入庫のもの（予定外入庫設定より入庫）
        countSql.append(" AND ((DNCARRYINFO.RETRIEVAL_DETAIL = ");
        countSql.append(DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_UNKNOWN));
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ");
        countSql.append(DBFormat.format(CarryInfo.CARRY_FLAG_STORAGE));
        countSql.append(" AND DNCARRYINFO.SOURCE_STATION_NO = ");
        countSql.append(DBFormat.format(param.getString(STORAGE_STATION_NO)));
        countSql.append(")");

        // 積み増し入庫で、搬送区分が入庫のもの
        // 作業表示運用ありの場合は、搬送元に入庫ステーションがセットされますが
        // 作業表示運用なしの場合は、タイミングによって、
        // 入庫側ステーションがセットされている状態と、出庫がわステーションがセットされている状態があります。
        countSql.append(" OR (DNCARRYINFO.RETRIEVAL_DETAIL = ");
        countSql.append(DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING));
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ");
        countSql.append(DBFormat.format(CarryInfo.CARRY_FLAG_STORAGE));
        countSql.append(" AND (DNCARRYINFO.SOURCE_STATION_NO = ");
        countSql.append(DBFormat.format(param.getString(RETRIEVAL_STATION_NO)));
        countSql.append(" OR DNCARRYINFO.SOURCE_STATION_NO = ");
        countSql.append(DBFormat.format(param.getString(STORAGE_STATION_NO)));
        countSql.append(")))");

        // 結合条件
        countSql.append(" AND DNWORKINFO.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE(+)");
        countSql.append(" AND DNWORKINFO.ITEM_CODE = DMITEM.ITEM_CODE(+)");
        countSql.append(" AND DNWORKINFO.PLAN_AREA_NO = DMAREA.AREA_NO(+)");
        countSql.append(" AND DNWORKINFO.SYSTEM_CONN_KEY = DNCARRYINFO.CARRY_KEY(+)");
        countSql.append(" AND DNCARRYINFO.SOURCE_STATION_NO = DMSTATION.STATION_NO(+) ");

        // 集約条件
        countSql.append("GROUP BY");
        countSql.append(" DNWORKINFO.COLLECT_JOB_NO");
        countSql.append(" ,DNWORKINFO.PLAN_AREA_NO");
        countSql.append(" ,DNWORKINFO.PLAN_LOCATION_NO");
        countSql.append(" ,DNWORKINFO.ITEM_CODE");
        countSql.append(" ,DNWORKINFO.PLAN_LOT_NO");
        countSql.append(" ,DNWORKINFO.PLAN_DAY");
        countSql.append(" ,DNWORKINFO.SETTING_UNIT_KEY");
        countSql.append(" ,DNCARRYINFO.WORK_NO");
        countSql.append(" ,DMAREA.AREA_NAME");
        countSql.append(" ,DNCARRYINFO.SOURCE_STATION_NO");
        countSql.append(" ,DMSTATION.STATION_NAME");
        countSql.append(" ,DMITEM.ITEM_NAME");
        countSql.append(" ,DMITEM.ENTERING_QTY");
        countSql.append(" ,DMITEM.JAN");
        countSql.append(" ,DMITEM.ITF ");

        countSql.append("UNION ");

        countSql.append("SELECT");
        countSql.append(" DNWORKINFO.COLLECT_JOB_NO AS COLLECT_JOB_NO");
        countSql.append(" ,DNWORKINFO.PLAN_AREA_NO AS PLAN_AREA_NO");
        countSql.append(" ,DNWORKINFO.PLAN_LOCATION_NO AS PLAN_LOCATION_NO");
        countSql.append(" ,DNWORKINFO.ITEM_CODE AS ITEM_CODE");
        countSql.append(" ,DNWORKINFO.PLAN_LOT_NO AS PLAN_LOT_NO");
        countSql.append(" ,DNWORKINFO.PLAN_DAY AS PLAN_DAY");
        countSql.append(" ,SUM(DNWORKINFO.PLAN_QTY) AS PLAN_QTY");
        countSql.append(" ,DNWORKINFO.SETTING_UNIT_KEY AS SETTING_UNIT_KEY");
        countSql.append(" ,DNCARRYINFO.WORK_NO AS WORK_NO");
        countSql.append(" ,DMAREA.AREA_NAME AS AREA_NAME");
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

        // 作業区分
        countSql.append(" DNWORKINFO.JOB_TYPE = ");
        countSql.append(DBFormat.format(SystemDefine.JOB_TYPE_NOPLAN_STORAGE));
        // ハードウェア区分 : AS/RS
        countSql.append(" AND DNWORKINFO.HARDWARE_TYPE = ");
        countSql.append(DBFormat.format(SystemDefine.HARDWARE_TYPE_ASRS));
        // 状態フラグ
        countSql.append(" AND DNWORKINFO.STATUS_FLAG = ");
        countSql.append(DBFormat.format(SystemDefine.STATUS_FLAG_NOWWORKING));
        // 設定単位キー
        if (!StringUtil.isBlank(param.getString(SETTING_UNITKEY)))
        {
            countSql.append(" AND DNWORKINFO.SETTING_UNIT_KEY = ");
            countSql.append(DBFormat.format(param.getString(SETTING_UNITKEY)));
        }
        // エリアNo
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            countSql.append(" AND DNWORKINFO.PLAN_AREA_NO = ");
            countSql.append(DBFormat.format(param.getString(AREA_NO)));
        }
        // 開始検索日時
        if (!StringUtil.isBlank(param.getString(SEARCH_DATE)))
        {
            countSql.append(" AND DNCARRYINFO.REGIST_DATE >= ");
            countSql.append(DBFormat.format(param.getDate(SEARCH_DATE)));
        }
        // 終了検索日時
        if (!StringUtil.isBlank(param.getString(TO_SEARCH_DATE)))
        {
            // 終了検索日時に1秒追加用
            Calendar curDate = Calendar.getInstance();
            curDate.setTime(param.getDate(TO_SEARCH_DATE));
            curDate.add(Calendar.SECOND, +1);

            countSql.append(" AND DNCARRYINFO.REGIST_DATE < ");
            countSql.append(DBFormat.format(curDate.getTime()));
        }

        // 積み増し入庫で、搬送区分が出庫のもの
        countSql.append(" AND ((DNCARRYINFO.RETRIEVAL_DETAIL = ");
        countSql.append(DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING));
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ");
        countSql.append(DBFormat.format(CarryInfo.CARRY_FLAG_RETRIEVAL));
        countSql.append(" AND DNCARRYINFO.DEST_STATION_NO = ");
        countSql.append(DBFormat.format(param.getString(RETRIEVAL_STATION_NO)));

        // 積み増し入庫で、搬送区分が入庫のもの
        // 作業表示運用ありの場合は、搬送元に入庫ステーションがセットされますが
        // 作業表示運用なしの場合は、タイミングによって、
        // 入庫側ステーションがセットされている状態と、出庫がわステーションがセットされている状態があります。
        countSql.append(" OR (DNCARRYINFO.RETRIEVAL_DETAIL = ");
        countSql.append(DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING));
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ");
        countSql.append(DBFormat.format(CarryInfo.CARRY_FLAG_STORAGE));
        countSql.append(" AND (DNCARRYINFO.SOURCE_STATION_NO = ");
        countSql.append(DBFormat.format(param.getString(RETRIEVAL_STATION_NO)));
        countSql.append(" OR DNCARRYINFO.SOURCE_STATION_NO = ");
        countSql.append(DBFormat.format(param.getString(STORAGE_STATION_NO)));
        countSql.append(")))");

        // 結合条件
        countSql.append(" AND DNWORKINFO.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE(+)");
        countSql.append(" AND DNWORKINFO.ITEM_CODE = DMITEM.ITEM_CODE(+)");
        countSql.append(" AND DNWORKINFO.PLAN_AREA_NO = DMAREA.AREA_NO(+)");
        countSql.append(" AND DNWORKINFO.SYSTEM_CONN_KEY = DNCARRYINFO.CARRY_KEY(+)");
        countSql.append(" AND DNCARRYINFO.DEST_STATION_NO = DMSTATION.STATION_NO(+) ");

        // 集約条件
        countSql.append("GROUP BY");
        countSql.append(" DNWORKINFO.COLLECT_JOB_NO");
        countSql.append(" ,DNWORKINFO.PLAN_AREA_NO");
        countSql.append(" ,DNWORKINFO.PLAN_LOCATION_NO");
        countSql.append(" ,DNWORKINFO.ITEM_CODE");
        countSql.append(" ,DNWORKINFO.PLAN_LOT_NO");
        countSql.append(" ,DNWORKINFO.PLAN_DAY");
        countSql.append(" ,DNWORKINFO.SETTING_UNIT_KEY");
        countSql.append(" ,DNCARRYINFO.WORK_NO");
        countSql.append(" ,DMAREA.AREA_NAME");
        countSql.append(" ,DNCARRYINFO.DEST_STATION_NO");
        countSql.append(" ,DMSTATION.STATION_NAME");
        countSql.append(" ,DMITEM.ITEM_NAME");
        countSql.append(" ,DMITEM.ENTERING_QTY");
        countSql.append(" ,DMITEM.JAN");
        countSql.append(" ,DMITEM.ITF");
        countSql.append(") ");

        // 表示順
        countSql.append("ORDER BY");
        countSql.append(" STATION_NO");
        countSql.append(" ,WORK_NO");
        countSql.append(" ,ITEM_CODE");
        countSql.append(" ,PLAN_LOT_NO");

        return countSql;
    }

    /**
     * AS/RS 予定外入庫の印刷件数を取得します。<BR>
     * コンストラクタで指定されたパラメータをもとに、入出庫作業情報を検索し<BR>
     * 印刷対象データ件数を返します。
     * @return 印刷対象データ件数
     * @throws ReadWriteException データベース処理でエラー発生した場合にthrowします。
     * @see jp.co.daifuku.wms.base.report.AbstractCSVWriter#count()
     */
    protected StringBuffer noPlanStorageCount(Params param)
            throws ReadWriteException
    {

        // カウントSQL
        StringBuffer countSql = new StringBuffer();

        // 取得項目
        countSql.append("SELECT");
        countSql.append(" COUNT(*) as CNT ");

        // 取得元テーブル
        countSql.append("FROM");
        countSql.append(" DNWORKINFO");
        countSql.append(" ,DNCARRYINFO");
        countSql.append(" ,DMITEM");
        countSql.append(" ,DMAREA");
        countSql.append(" ,DMSTATION ");

        // 取得条件
        countSql.append("WHERE");
        // 作業区分
        countSql.append(" DNWORKINFO.JOB_TYPE = ");
        countSql.append(DBFormat.format(SystemDefine.JOB_TYPE_NOPLAN_STORAGE));
        // ハードウェア区分 : AS/RS
        countSql.append(" AND DNWORKINFO.HARDWARE_TYPE = ");
        countSql.append(DBFormat.format(SystemDefine.HARDWARE_TYPE_ASRS));
        // 状態フラグ
        countSql.append(" AND DNWORKINFO.STATUS_FLAG = ");
        countSql.append(DBFormat.format(SystemDefine.STATUS_FLAG_NOWWORKING));
        // 設定単位キー
        if (!StringUtil.isBlank(param.getString(SETTING_UNITKEY)))
        {
            countSql.append(" AND DNWORKINFO.SETTING_UNIT_KEY = ");
            countSql.append(DBFormat.format(param.getString(SETTING_UNITKEY)));
        }

        // 結合条件
        countSql.append(" AND DNWORKINFO.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE(+)");
        countSql.append(" AND DNWORKINFO.ITEM_CODE = DMITEM.ITEM_CODE(+)");
        countSql.append(" AND DNWORKINFO.PLAN_AREA_NO = DMAREA.AREA_NO(+)");
        countSql.append(" AND DNWORKINFO.SYSTEM_CONN_KEY = DNCARRYINFO.CARRY_KEY(+)");
        countSql.append(" AND DNCARRYINFO.DEST_STATION_NO = DMSTATION.STATION_NO(+)");

        return countSql;
    }

    /**
     * AS/RS 予定外入庫(積み増し)の印刷件数を取得します。<BR>
     * コンストラクタで指定されたパラメータをもとに、入出庫作業情報を検索し<BR>
     * 印刷対象データ件数を返します。
     * @return 印刷対象データ件数
     * @throws ReadWriteException データベース処理でエラー発生した場合にthrowします。
     * @see jp.co.daifuku.wms.base.report.AbstractCSVWriter#count()
     */
    protected StringBuffer addNoPlanStorageCount(Params param)
            throws ReadWriteException
    {

        // カウントSQL
        StringBuffer countSql = new StringBuffer();

        // 取得項目
        countSql.append("SELECT");
        countSql.append(" COUNT(*) as CNT ");

        // 取得元テーブル
        countSql.append("FROM");
        countSql.append(" DNWORKINFO");
        countSql.append(" ,DNCARRYINFO");
        countSql.append(" ,DMITEM");
        countSql.append(" ,DMAREA");
        countSql.append(" ,DMSTATION ");

        // 取得条件
        countSql.append("WHERE");
        // 作業区分
        countSql.append(" DNWORKINFO.JOB_TYPE = ");
        countSql.append(DBFormat.format(SystemDefine.JOB_TYPE_NOPLAN_STORAGE));
        // ハードウェア区分 : AS/RS
        countSql.append(" AND DNWORKINFO.HARDWARE_TYPE = ");
        countSql.append(DBFormat.format(SystemDefine.HARDWARE_TYPE_ASRS));
        // 状態フラグ
        countSql.append(" AND DNWORKINFO.STATUS_FLAG = ");
        countSql.append(DBFormat.format(SystemDefine.STATUS_FLAG_NOWWORKING));
        // 設定単位キー
        if (!StringUtil.isBlank(param.getString(SETTING_UNITKEY)))
        {
            countSql.append(" AND DNWORKINFO.SETTING_UNIT_KEY = ");
            countSql.append(DBFormat.format(param.getString(SETTING_UNITKEY)));
        }
        // 積み増し入庫で、搬送区分が出庫のもの
        countSql.append(" AND ((DNCARRYINFO.RETRIEVAL_DETAIL = ");
        countSql.append(DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING));
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ");
        countSql.append(DBFormat.format(CarryInfo.CARRY_FLAG_RETRIEVAL));
        countSql.append(" AND DNCARRYINFO.DEST_STATION_NO = ");
        countSql.append(DBFormat.format(param.getString(RETRIEVAL_STATION_NO)));
        countSql.append(")");
        // 積み増し入庫で、搬送区分が入庫のもの
        // 作業表示運用ありの場合は、搬送元に入庫ステーションがセットされますが
        // 作業表示運用なしの場合は、タイミングによって、
        // 入庫側ステーションがセットされている状態と、出庫がわステーションがセットされている状態があります。
        countSql.append(" OR (DNCARRYINFO.RETRIEVAL_DETAIL = ");
        countSql.append(DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING));
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ");
        countSql.append(DBFormat.format(CarryInfo.CARRY_FLAG_STORAGE));
        countSql.append(" AND (DNCARRYINFO.SOURCE_STATION_NO = ");
        countSql.append(DBFormat.format(param.getString(RETRIEVAL_STATION_NO)));
        countSql.append(" OR DNCARRYINFO.SOURCE_STATION_NO = ");
        countSql.append(DBFormat.format(param.getString(STORAGE_STATION_NO)));
        countSql.append(")))");

        // 結合条件
        countSql.append(" AND DNWORKINFO.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE(+)");
        countSql.append(" AND DNWORKINFO.ITEM_CODE = DMITEM.ITEM_CODE(+)");
        countSql.append(" AND DNWORKINFO.PLAN_AREA_NO = DMAREA.AREA_NO(+)");
        countSql.append(" AND DNWORKINFO.SYSTEM_CONN_KEY = DNCARRYINFO.CARRY_KEY(+)");
        countSql.append(" AND DNCARRYINFO.SOURCE_STATION_NO = DMSTATION.STATION_NO(+)");

        return countSql;
    }

    /**
     * AS/RS 予定外入庫、AS/RS 予定外入庫(積み増し)の印刷件数を取得します。<BR>
     * コンストラクタで指定されたパラメータをもとに、入出庫作業情報を検索し<BR>
     * 印刷対象データ件数を返します。
     * @return 印刷対象データ件数
     * @throws ReadWriteException データベース処理でエラー発生した場合にthrowします。
     * @see jp.co.daifuku.wms.base.report.AbstractCSVWriter#count()
     */
    protected StringBuffer allCount(Params param)
            throws ReadWriteException
    {

        // カウントSQL
        StringBuffer countSql = new StringBuffer();

        countSql.append("SELECT");
        countSql.append(" SUM(CNT) AS CNT ");
        countSql.append("FROM");
        countSql.append(" (SELECT");
        countSql.append(" COUNT(*) as CNT");
        countSql.append(" FROM");
        countSql.append(" DNWORKINFO");
        countSql.append(" ,DNCARRYINFO");
        countSql.append(" ,DMITEM");
        countSql.append(" ,DMAREA");
        countSql.append(" ,DMSTATION");
        countSql.append(" WHERE");

        // 作業区分
        countSql.append(" DNWORKINFO.JOB_TYPE = ");
        countSql.append(DBFormat.format(SystemDefine.JOB_TYPE_NOPLAN_STORAGE));
        // ハードウェア区分 : AS/RS
        countSql.append(" AND DNWORKINFO.HARDWARE_TYPE = ");
        countSql.append(DBFormat.format(SystemDefine.HARDWARE_TYPE_ASRS));
        // 状態フラグ
        countSql.append(" AND DNWORKINFO.STATUS_FLAG = ");
        countSql.append(DBFormat.format(SystemDefine.STATUS_FLAG_NOWWORKING));

        // 設定単位キー
        if (!StringUtil.isBlank(param.getString(SETTING_UNITKEY)))
        {
            countSql.append(" AND DNWORKINFO.SETTING_UNIT_KEY = ");
            countSql.append(DBFormat.format(param.getString(SETTING_UNITKEY)));
        }
        // エリアNo
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            countSql.append(" AND DNWORKINFO.PLAN_AREA_NO = ");
            countSql.append(DBFormat.format(param.getString(AREA_NO)));
        }
        // 開始検索日時
        if (!StringUtil.isBlank(param.getString(SEARCH_DATE)))
        {
            countSql.append(" AND DNCARRYINFO.REGIST_DATE >= ");
            countSql.append(DBFormat.format(param.getDate(SEARCH_DATE)));
        }
        // 終了検索日時
        if (!StringUtil.isBlank(param.getString(TO_SEARCH_DATE)))
        {
            // 終了検索日時に1秒追加用
            Calendar curDate = Calendar.getInstance();
            curDate.setTime(param.getDate(SETTING_UNITKEY));
            curDate.add(Calendar.SECOND, +1);

            countSql.append(" AND DNCARRYINFO.REGIST_DATE < ");
            countSql.append(DBFormat.format(curDate.getTime()));
        }

        // 新規入庫で、搬送区分が入庫のもの（予定外入庫設定より入庫）
        countSql.append(" AND ((DNCARRYINFO.RETRIEVAL_DETAIL = ");
        countSql.append(DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_UNKNOWN));
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ");
        countSql.append(DBFormat.format(CarryInfo.CARRY_FLAG_STORAGE));
        countSql.append(" AND DNCARRYINFO.SOURCE_STATION_NO = ");
        countSql.append(DBFormat.format(param.getString(STORAGE_STATION_NO)));
        countSql.append(")");

        // 積み増し入庫で、搬送区分が入庫のもの
        // 作業表示運用ありの場合は、搬送元に入庫ステーションがセットされますが
        // 作業表示運用なしの場合は、タイミングによって、
        // 入庫側ステーションがセットされている状態と、出庫がわステーションがセットされている状態があります。
        countSql.append(" OR (DNCARRYINFO.RETRIEVAL_DETAIL = ");
        countSql.append(DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING));
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ");
        countSql.append(DBFormat.format(CarryInfo.CARRY_FLAG_STORAGE));
        countSql.append(" AND (DNCARRYINFO.SOURCE_STATION_NO = ");
        countSql.append(DBFormat.format(param.getString(RETRIEVAL_STATION_NO)));
        countSql.append(" OR DNCARRYINFO.SOURCE_STATION_NO = ");
        countSql.append(DBFormat.format(param.getString(STORAGE_STATION_NO)));
        countSql.append(")))");

        // 結合条件
        countSql.append(" AND DNWORKINFO.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE(+)");
        countSql.append(" AND DNWORKINFO.ITEM_CODE = DMITEM.ITEM_CODE(+)");
        countSql.append(" AND DNWORKINFO.PLAN_AREA_NO = DMAREA.AREA_NO(+)");
        countSql.append(" AND DNWORKINFO.SYSTEM_CONN_KEY = DNCARRYINFO.CARRY_KEY(+)");
        countSql.append(" AND DNCARRYINFO.SOURCE_STATION_NO = DMSTATION.STATION_NO(+)");

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

        // 作業区分
        countSql.append(" DNWORKINFO.JOB_TYPE = ");
        countSql.append(DBFormat.format(SystemDefine.JOB_TYPE_NOPLAN_STORAGE));
        // ハードウェア区分 : AS/RS
        countSql.append(" AND DNWORKINFO.HARDWARE_TYPE = ");
        countSql.append(DBFormat.format(SystemDefine.HARDWARE_TYPE_ASRS));
        // 状態フラグ
        countSql.append(" AND DNWORKINFO.STATUS_FLAG = ");
        countSql.append(DBFormat.format(SystemDefine.STATUS_FLAG_NOWWORKING));

        // 設定単位キー
        if (!StringUtil.isBlank(param.getString(SETTING_UNITKEY)))
        {
            countSql.append(" AND DNWORKINFO.SETTING_UNIT_KEY = ");
            countSql.append(DBFormat.format(param.getString(SETTING_UNITKEY)));
        }
        // エリアNo
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            countSql.append(" AND DNWORKINFO.PLAN_AREA_NO = ");
            countSql.append(DBFormat.format(param.getString(AREA_NO)));
        }
        // 開始検索日時
        if (!StringUtil.isBlank(param.getString(SEARCH_DATE)))
        {
            countSql.append(" AND DNCARRYINFO.REGIST_DATE >= ");
            countSql.append(DBFormat.format(param.getDate(SEARCH_DATE)));
        }
        // 終了検索日時
        if (!StringUtil.isBlank(param.getString(TO_SEARCH_DATE)))
        {
            // 終了検索日時に1秒追加用
            Calendar curDate = Calendar.getInstance();
            curDate.setTime(param.getDate(TO_SEARCH_DATE));
            curDate.add(Calendar.SECOND, +1);

            countSql.append(" AND DNCARRYINFO.REGIST_DATE < ");
            countSql.append(DBFormat.format(curDate.getTime()));
        }

        // 積み増し入庫で、搬送区分が出庫のもの
        countSql.append(" AND ((DNCARRYINFO.RETRIEVAL_DETAIL = ");
        countSql.append(DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING));
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ");
        countSql.append(DBFormat.format(CarryInfo.CARRY_FLAG_RETRIEVAL));
        countSql.append(" AND DNCARRYINFO.DEST_STATION_NO = ");
        countSql.append(DBFormat.format(param.getString(RETRIEVAL_STATION_NO)));
        countSql.append(")");

        // 積み増し入庫で、搬送区分が入庫のもの
        // 作業表示運用ありの場合は、搬送元に入庫ステーションがセットされますが
        // 作業表示運用なしの場合は、タイミングによって、
        // 入庫側ステーションがセットされている状態と、出庫がわステーションがセットされている状態があります。
        countSql.append(" OR (DNCARRYINFO.RETRIEVAL_DETAIL = ");
        countSql.append(DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING));
        countSql.append(" AND DNCARRYINFO.CARRY_FLAG = ");
        countSql.append(DBFormat.format(CarryInfo.CARRY_FLAG_STORAGE));
        countSql.append(" AND (DNCARRYINFO.SOURCE_STATION_NO = ");
        countSql.append(DBFormat.format(param.getString(RETRIEVAL_STATION_NO)));
        countSql.append(" OR DNCARRYINFO.SOURCE_STATION_NO = ");
        countSql.append(DBFormat.format(param.getString(STORAGE_STATION_NO)));
        countSql.append(")))");

        // 結合条件
        countSql.append(" AND DNWORKINFO.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE(+)");
        countSql.append(" AND DNWORKINFO.ITEM_CODE = DMITEM.ITEM_CODE(+)");
        countSql.append(" AND DNWORKINFO.PLAN_AREA_NO = DMAREA.AREA_NO(+)");
        countSql.append(" AND DNWORKINFO.SYSTEM_CONN_KEY = DNCARRYINFO.CARRY_KEY(+)");
        countSql.append(" AND DNCARRYINFO.DEST_STATION_NO = DMSTATION.STATION_NO(+)");
        countSql.append(")");

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
