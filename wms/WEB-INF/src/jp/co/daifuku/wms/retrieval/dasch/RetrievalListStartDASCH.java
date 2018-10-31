// $Id: RetrievalListStartDASCH.java 4808 2009-08-10 06:32:12Z shibamoto $
package jp.co.daifuku.wms.retrieval.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.retrieval.dasch.RetrievalListStartDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 出庫リスト作業開始に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 4808 $, $Date: 2009-08-10 15:32:12 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class RetrievalListStartDASCH
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
    public RetrievalListStartDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
            p.set(JOB_NO, ent.getSettingUnitKey());
            p.set(PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            p.set(BATCH_NO, ent.getBatchNo());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());
            p.set(ORDER_NO, ent.getOrderNo());
            p.set(CUSTOMER_CODE, ent.getCustomerCode());
            p.set(CUSTOMER_NAME, ent.getValue(Customer.CUSTOMER_NAME));
            p.set(AREA_NO, ent.getPlanAreaNo());
            p.set(AREA_NAME, ent.getValue(Area.AREA_NAME));
            p.set(PLAN_LOCATION_NO, ent.getPlanLocationNo());
            p.set(ITEM_CODE, ent.getItemCode());
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME));
            p.set(PLAN_LOT_NO, ent.getPlanLotNo());
            p.set(ENTERING_QTY, ent.getValue(Item.ENTERING_QTY));
            p.set(PLAN_CASE_QTY,
                    DisplayUtil.getCaseQty(ent.getPlanQty(), ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            p.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(),
                    ent.getBigDecimal(Item.ENTERING_QTY).intValue()));

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
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        List<Params> params = new ArrayList<Params>();
        WorkInfo[] ents = (WorkInfo[])_finder.getEntities(start, start + cnt);

        for (WorkInfo ent : ents)
        {
            Params p = new Params();

            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(JOB_NO, ent.getSettingUnitKey());
            p.set(PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            p.set(BATCH_NO, ent.getBatchNo());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());
            p.set(ORDER_NO, ent.getOrderNo());
            p.set(CUSTOMER_CODE, ent.getCustomerCode());
            p.set(CUSTOMER_NAME, ent.getValue(Customer.CUSTOMER_NAME));
            p.set(AREA_NO, ent.getPlanAreaNo());
            p.set(AREA_NAME, ent.getValue(Area.AREA_NAME));
            p.set(PLAN_LOCATION_NO, ent.getPlanLocationNo());
            p.set(ITEM_CODE, ent.getItemCode());
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME));
            p.set(PLAN_LOT_NO, ent.getPlanLotNo());
            p.set(ENTERING_QTY, ent.getValue(Item.ENTERING_QTY));
            p.set(PLAN_CASE_QTY,
                    DisplayUtil.getCaseQty(ent.getPlanQty(), ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            p.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(),
                    ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            
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
        // 設定単位キー
        key.setSettingUnitKey(param.getString(SETTING_UNIT_KEY));
        // 荷主コード
        key.setJoin(WorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        key.setJoin(WorkInfo.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
        // 商品コード
        key.setJoin(WorkInfo.ITEM_CODE, Item.ITEM_CODE);
        // 出荷先コード
        key.setJoin(WorkInfo.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");
        // エリアNo.
        key.setJoin(WorkInfo.PLAN_AREA_NO, Area.AREA_NO);
        
        // グループ条件
        key.setSettingUnitKeyGroup();
        key.setCollectJobNoGroup();
        key.setPlanDayGroup();
        key.setBatchNoGroup();
        key.setOrderNoGroup();
        key.setCustomerCodeGroup();
        key.setPlanAreaNoGroup();
        key.setPlanLocationNoGroup();
        key.setItemCodeGroup();
        key.setPlanLotNoGroup();
        key.setGroup(Customer.CUSTOMER_NAME);
        key.setGroup(Area.AREA_NAME);
        key.setGroup(Item.ITEM_NAME);
        key.setGroup(Item.ENTERING_QTY);
        
        if (isSet)
        {
            // OrderByや、collect項目を記載する
            key.setSettingUnitKeyOrder(true);
            key.setPlanDayOrder(true);
            key.setBatchNoOrder(true);
            key.setOrderNoOrder(true);
            key.setCustomerCodeOrder(true);
            key.setPlanAreaNoOrder(true);
            key.setPlanLocationNoOrder(true);
            key.setItemCodeOrder(true);
            key.setPlanLotNoOrder(true);
            
            // 取得条件
            key.setSettingUnitKeyCollect();
            key.setCollectJobNoCollect();
            key.setPlanDayCollect();
            key.setBatchNoCollect();
            key.setOrderNoCollect();
            key.setCustomerCodeCollect();
            key.setPlanAreaNoCollect();
            key.setPlanLocationNoCollect();
            key.setItemCodeCollect();
            key.setPlanLotNoCollect();
            key.setPlanQtyCollect("SUM");
            key.setCollect(Customer.CUSTOMER_NAME);
            key.setCollect(Area.AREA_NAME);
            key.setCollect(Item.ITEM_NAME);
            key.setCollect(Item.ENTERING_QTY);
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
