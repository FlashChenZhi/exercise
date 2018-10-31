// $Id: FaAsReStoringDeleteDASCH.java 7376 2010-03-05 07:37:40Z kishimoto $
package jp.co.daifuku.wms.system.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.system.dasch.FaAsReStoringDeleteDASCHParams.*;

import java.sql.Connection;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.ReStoringPlan;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.SearchKey;

/**
 * BusiTuneで生成されたDASCHクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7376 $, $Date:: 2010-03-05 16:37:40 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class FaAsReStoringDeleteDASCH
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
    public FaAsReStoringDeleteDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new ReStoringPlanFinder(getConnection());
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
        ReStoringPlan[] ents = (ReStoringPlan[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (ReStoringPlan ent : ents)
        {
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());

            p.set(STATION_NO, ent.getStationNo());
            p.set(STATION_NAME, ent.getValue(Station.STATION_NAME));
            p.set(WORK_NO, ent.getWorkNo());
            p.set(ITEM_CODE, ent.getItemCode());
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME));
            p.set(LOT_NO, ent.getPlanLotNo());
            p.set(STORAGE_DATE, ent.getStorageDate());
            p.set(PLAN_QTY, ent.getPlanQty());
            p.set(REMOVE_DATE, ent.getRemoveDate());
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
        ReStoringPlanHandler handler = new ReStoringPlanHandler(getConnection());

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
        throw new RuntimeException("This method is not implemented.");
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
        ReStoringPlanSearchKey key = new ReStoringPlanSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        // 状態フラグ(未作業)
        key.setStatusFlag(ReStoringPlan.STATUS_FLAG_UNSTART);
        
        // 予定日(保持期間超過)
        key.setPlanDay(param.getString(DELETE_DAY), "<=");
        
        key.setJoin(ReStoringPlan.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        key.setJoin(ReStoringPlan.ITEM_CODE, Item.ITEM_CODE);
        key.setJoin(ReStoringPlan.STATION_NO, Station.STATION_NO);
        
        if (isSet)
        {
            // OrderByや、collect項目を記載する
            key.setStationNoCollect();
            key.setCollect(Station.STATION_NAME);
            key.setWorkNoCollect();
            key.setItemCodeCollect();
            key.setCollect(Item.ITEM_NAME);
            key.setPlanLotNoCollect();
            key.setStorageDateCollect();
            key.setPlanQtyCollect();
            key.setRemoveDateCollect();
            
            key.setStationNoOrder(true);
            key.setWorkNoOrder(true);
            key.setItemCodeOrder(true);
            key.setPlanLotNoOrder(true);
            key.setStorageDateOrder(false);
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