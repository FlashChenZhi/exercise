// $Id: AsStorageListDASCH.java 4804 2009-08-10 06:26:35Z shibamoto $
package jp.co.daifuku.wms.asrs.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.dasch.AsStorageListDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * AS/RS 入庫作業リスト発行に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4804 $, $Date:: 2009-08-10 15:26:35 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class AsStorageListDASCH
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
    public AsStorageListDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new WorkInfoFinder(getConnection());
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
        // get Next entity from finder class
        WorkInfo[] ents = (WorkInfo[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (WorkInfo ent : ents)
        {
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            // エリア
            p.set(AREA_NO, ent.getPlanAreaNo());
            // エリア名称
            p.set(AREA_NAME, String.valueOf(ent.getValue(Area.AREA_NAME, "")));
            // 発行日
            p.set(SYS_DAY, getPrintDate());
            // 発行時刻
            p.set(SYS_TIME, getPrintDate());
            // ステーション
            p.set(STATION_NO, String.valueOf(ent.getValue(CarryInfo.SOURCE_STATION_NO, "")));
            // ステーション名称
            p.set(STATION_NAME, String.valueOf(ent.getValue(Station.STATION_NAME, "")));
            // 作業No
            p.set(WORK_NO, String.valueOf(ent.getValue(CarryInfo.WORK_NO, "")));
            // 入庫棚
            p.set(STORAGE_LOCATION_NO, ent.getPlanLocationNo());
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
            // ロットNo.
            p.set(PLAN_LOT_NO, ent.getPlanLotNo());
            // 入庫予定日
            p.set(STORAGE_PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            // ケース入数
            int enteringQty = ent.getBigDecimal(Item.ENTERING_QTY).intValue();
            p.set(ENTERING_QTY, enteringQty);
            // 予定ケース数
            p.set(STORAGE_CASE_QTY, DisplayUtil.getCaseQty(ent.getPlanQty(), enteringQty));
            // 予定ピース数
            p.set(STORAGE_PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(), enteringQty));
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
        WorkInfoHandler handler = new WorkInfoHandler(getConnection());

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
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        List<Params> params = new ArrayList<Params>();
        WorkInfo[] ents = (WorkInfo[])_finder.getEntities(start, start + cnt);

        for (WorkInfo ent : ents)
        {
            Params p = new Params();

            // 取得データのセット
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            // エリア
            p.set(AREA_NO, ent.getPlanAreaNo());
            // エリア名称
            p.set(AREA_NAME, String.valueOf(ent.getValue(Area.AREA_NAME, "")));
            // 発行日
            p.set(SYS_DAY, getPrintDate());
            // 発行時刻
            p.set(SYS_TIME, getPrintDate());
            // ステーション
            p.set(STATION_NO, String.valueOf(ent.getValue(CarryInfo.SOURCE_STATION_NO, "")));
            // ステーション名称
            p.set(STATION_NAME, String.valueOf(ent.getValue(Station.STATION_NAME, "")));
            // 作業No
            p.set(WORK_NO, String.valueOf(ent.getValue(CarryInfo.WORK_NO, "")));
            // 入庫棚
            p.set(STORAGE_LOCATION_NO, ent.getPlanLocationNo());
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
            // ロットNo.
            p.set(PLAN_LOT_NO, ent.getPlanLotNo());
            // 入庫予定日
            p.set(STORAGE_PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            // ケース入数
            int enteringQty = ent.getBigDecimal(Item.ENTERING_QTY).intValue();
            p.set(ENTERING_QTY, enteringQty);
            // 予定ケース数
            p.set(STORAGE_CASE_QTY, DisplayUtil.getCaseQty(ent.getPlanQty(), enteringQty));
            // 予定ピース数
            p.set(STORAGE_PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(), enteringQty));
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
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        WorkInfoSearchKey key = new WorkInfoSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by

        // 検索日時
        Date[] tmp =
                WmsFormatter.getFromTo(param.getDate(FROM_SEARCH_DATE), param.getDate(FROM_SEARCH_TIME),
                        param.getDate(TO_SEARCH_DATE), param.getDate(TO_SEARCH_TIME));
        Date from = tmp[0];
        Date to = tmp[1];
        // 開始検索日時
        if (!StringUtil.isBlank(from))
        {
            key.setKey(CarryInfo.REGIST_DATE, from, ">=", "", "", true);
        }
        // 終了検索日時
        if (!StringUtil.isBlank(to))
        {
            key.setKey(CarryInfo.REGIST_DATE, to, "<", "", "", true);
        }
        // ステーションNo.
        if (!StringUtil.isBlank(param.getString(STATION_NO)))
        {
            if (!WmsParam.AUTO_SELECT_STATION.equals(param.getString(STATION_NO)))
            {
                key.setKey(CarryInfo.SOURCE_STATION_NO, param.getString(STATION_NO));
            }
            else
            {
                key.setKey(Station.PARENT_STATION_NO, param.getString(WORK_PLACE_NO));
            }
        }
        // エリアNo.
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            key.setPlanAreaNo(param.getString(AREA_NO));
        }
        // 作業区分
        if (!StringUtil.isBlank(param.getString(STORAGE_WORK_KIND)))
        {
            key.setJobType(param.getString(STORAGE_WORK_KIND));
        }
        // 状態フラグ
        key.setStatusFlag(WorkInfo.STATUS_FLAG_NOWWORKING);
        // ハードウェア区分
        key.setHardwareType(SystemDefine.HARDWARE_TYPE_ASRS);

        // 結合条件
        key.setJoin(WorkInfo.SYSTEM_CONN_KEY, CarryInfo.CARRY_KEY);
        key.setJoin(CarryInfo.SOURCE_STATION_NO, Station.STATION_NO);
        key.setJoin(WorkInfo.PLAN_AREA_NO, Area.AREA_NO);
        key.setJoin(WorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        key.setJoin(WorkInfo.ITEM_CODE, Item.ITEM_CODE);

        // 集約条件
        key.setGroup(CarryInfo.WORK_NO);
        key.setPlanAreaNoGroup();
        key.setGroup(Area.AREA_NAME);
        key.setGroup(CarryInfo.SOURCE_STATION_NO);
        key.setGroup(Station.STATION_NAME);
        key.setPlanLocationNoGroup();
        key.setItemCodeGroup();
        key.setGroup(Item.ITEM_NAME);
        key.setPlanLotNoGroup();
        key.setPlanDayGroup();
        key.setGroup(Item.ENTERING_QTY);
        key.setGroup(Item.JAN);
        key.setGroup(Item.ITF);

        if (isSet)
        {
            // OrderByや、collect項目を記載する

            // ソート順
            // 作業No.
            key.setOrder(CarryInfo.WORK_NO, true);
            // 商品コード
            key.setItemCodeOrder(true);
            // 予定ロットNo.
            key.setPlanLotNoOrder(true);

            // 取得条件
            key.setPlanAreaNoCollect();
            key.setCollect(Area.AREA_NAME);
            key.setCollect(CarryInfo.SOURCE_STATION_NO);
            key.setCollect(Station.STATION_NAME);
            key.setPlanLocationNoCollect();
            key.setItemCodeCollect();
            key.setCollect(Item.ITEM_NAME);
            key.setPlanLotNoCollect();
            key.setPlanDayCollect();
            key.setCollect(Item.ENTERING_QTY);
            key.setPlanQtyCollect("SUM");
            key.setCollect(Item.JAN);
            key.setCollect(Item.ITF);
            key.setCollect(CarryInfo.WORK_NO);
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
