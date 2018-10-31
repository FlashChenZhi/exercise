package jp.co.daifuku.wms.asrs.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.dasch.LstAsStoragePlanDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * AS/RS 入庫予定検索に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:02:44 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class LstAsStoragePlanDASCH
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
    public LstAsStoragePlanDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // TODO : Implement for export
        //throw new ScheduleException("This method is not implemented.");
        // get Next entity from finder class
        WorkInfo[] ents = (WorkInfo[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (WorkInfo ent : ents)
        {
            // 入庫予定日
            p.set(PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
            // ケース入数
            p.set(ENTERING_QTY, ent.getBigDecimal(Item.ENTERING_QTY).intValue());
            // 残数
            p.set(CASE_QTY, DisplayUtil.getCaseQty(ent.getPlanQty(), ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            p.set(PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(), ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            // ロットNo.
            p.set(PLAN_LOT_NO, ent.getPlanLotNo());
            // 入庫エリア
            p.set(PLAN_AREA_NO, ent.getPlanAreaNo());
            // 入庫棚
            p.set(PLAN_LOCATION_NO, ent.getPlanLocationNo());

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
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        // TODO : Implement for listcell
        //throw new RuntimeException("This method is not implemented.");
        List<Params> params = new ArrayList<Params>();
        WorkInfo[] ents = (WorkInfo[])_finder.getEntities(start, start + cnt);

        for (WorkInfo ent : ents)
        {
            Params p = new Params();
            // 行No.
            p.set(SELECT, String.valueOf(params.size() + 1 + start));
            // 入庫予定日
            p.set(PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            p.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
            // ケース入数
            p.set(ENTERING_QTY, ent.getBigDecimal(Item.ENTERING_QTY).intValue());
            // 残数
            p.set(CASE_QTY, DisplayUtil.getCaseQty(ent.getPlanQty(), ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            p.set(PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(), ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            // ロットNo.
            p.set(PLAN_LOT_NO, ent.getPlanLotNo());
            // 入庫エリア
            p.set(PLAN_AREA_NO, ent.getPlanAreaNo());
            // 入庫棚
            p.set(PLAN_LOCATION_NO, ent.getPlanLocationNo());

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
        WorkInfoSearchKey key = new WorkInfoSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        // 荷主コード
        key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        // 入庫予定日
        if (!StringUtil.isBlank(param.getString(PLAN_DAY)))
        {
            key.setPlanDay(WmsFormatter.toParamDate(param.getDate(PLAN_DAY)));
        }
        // 商品コード
        if (!StringUtil.isBlank(param.getString(ITEM_CODE)))
        {
            key.setItemCode(param.getString(ITEM_CODE));
        }
        // ロットNo.
        if (!StringUtil.isBlank(param.getString(PLAN_LOT_NO)))
        {
            key.setPlanLotNo(param.getString(PLAN_LOT_NO));
        }
        // 状態フラグ(未作業)
        key.setStatusFlag(WorkInfo.STATUS_FLAG_UNSTART);
        // 作業区分
        key.setJobType(WorkInfo.JOB_TYPE_STORAGE);

        /* 結合条件の指定 */
        // 商品コード
        key.setJoin(WorkInfo.ITEM_CODE, Item.ITEM_CODE);
        key.setJoin(WorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);

        key.setPlanDayGroup();
        key.setItemCodeGroup();
        key.setGroup(Item.ITEM_NAME);
        key.setPlanLotNoGroup();
        key.setGroup(Item.ENTERING_QTY);
        key.setPlanAreaNoGroup();
        key.setPlanLocationNoGroup();

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            // 入庫予定日
            key.setPlanDayCollect();
            key.setPlanDayOrder(true);
            // 商品コード
            key.setItemCodeCollect();
            key.setItemCodeOrder(true);
            // 商品名称
            key.setCollect(Item.ITEM_NAME);
            // 予定ロットNo.
            key.setPlanLotNoCollect();
            key.setPlanLotNoOrder(true);
            // ケース入数
            key.setCollect(Item.ENTERING_QTY);
            // 予定数
            key.setPlanQtyCollect("SUM");
            // 予定エリア
            key.setPlanAreaNoCollect();
            key.setPlanAreaNoOrder(true);
            // 予定棚
            key.setPlanLocationNoCollect();
            key.setPlanLocationNoOrder(true);
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
