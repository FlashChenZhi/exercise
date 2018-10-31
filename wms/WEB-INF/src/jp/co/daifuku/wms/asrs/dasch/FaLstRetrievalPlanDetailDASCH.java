// $Id: Dasch_ja.java 5085 2009-10-01 08:09:16Z okamura $
package jp.co.daifuku.wms.asrs.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.asrs.dasch.FaLstRetrievalPlanDetailDASCHParams.*;
import static jp.co.daifuku.wms.base.dasch.LstItemDASCHParams.SELECT;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.SearchKey;

/**
 * BusiTuneで生成されたDASCHクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5085 $, $Date:: 2009-10-01 17:09:16 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okamura $
 */
public class FaLstRetrievalPlanDetailDASCH
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
    public FaLstRetrievalPlanDetailDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new RetrievalPlanFinder(getConnection());
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
        // DFKLOOK このメソッドは未使用
        throw new ScheduleException("This method is not implemented.");
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
        RetrievalPlanHandler handler = new RetrievalPlanHandler(getConnection());

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
        // TODO : Implement for listcell
        //throw new RuntimeException("This method is not implemented.");
        List<Params> params = new ArrayList<Params>();
        RetrievalPlan[] ents = (RetrievalPlan[])_finder.getEntities(start, start + cnt);

        for (RetrievalPlan ent : ents)
        {
            Params p = new Params();
            p.set(BATCH_NO, ent.getBatchNo());
            p.set(CONSIGNOR_CODE, ent.getConsignorCode());

            // 行番号
            p.set(SELECT, String.valueOf(params.size() + 1 + start));
            // 出荷伝票No.
            p.set(SHIP_TICKET_NO, ent.getShipTicketNo());
            // 出荷伝票行No.
            p.set(SHIP_TICKET_LINE, ent.getShipLineNo());
            // 商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            // ロットNo.
            p.set(LOT_NO, ent.getPlanLotNo());
            // 出庫予定数
            p.set(PLAN_QTY, ent.getPlanQty());
            // 出庫予定日
            p.set(PLAN_DATE, ent.getPlanDay());
            // 作業状態
            p.set(STATUS_FLAG, ent.getStatusFlag());
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME));
            
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
        RetrievalPlanSearchKey key = new RetrievalPlanSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        key.setJoin(RetrievalPlan.ITEM_CODE, Item.ITEM_CODE);
    	key.setBatchNo(param.getString(BATCH_NO));
    	key.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
    	key.setCancelFlag(SystemDefine.CANCEL_FLAG_HOST_CANCEL, "!=");

        if (isSet)
        {
            // OrderByや、collect項目を記載する        	
        	// select
        	// 出荷伝票No.
        	key.setShipTicketNoCollect();
        	// 出荷伝票行No.
        	key.setShipLineNoCollect();
        	// 商品コード
        	key.setItemCodeCollect();
        	// ロットNo.
        	key.setPlanLotNoCollect();
        	// 出庫予定数
        	key.setPlanQtyCollect();
        	// 出庫予定日
        	key.setPlanDayCollect();
        	// 作業状態
        	key.setStatusFlagCollect();
        	// 商品名称
        	key.setCollect(Item.ITEM_NAME);
        	
        	// order
        	// 出荷伝票No.
        	key.setShipTicketNoOrder(true);
        	// 出荷伝票行No.
        	key.setShipLineNoOrder(true);
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
