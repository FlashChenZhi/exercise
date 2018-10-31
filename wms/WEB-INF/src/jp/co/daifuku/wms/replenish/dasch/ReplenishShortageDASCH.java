package jp.co.daifuku.wms.replenish.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

import jp.co.daifuku.wms.base.dbhandler.ReplenishShortageFinder;
import jp.co.daifuku.wms.base.dbhandler.ReplenishShortageHandler;
import jp.co.daifuku.wms.base.dbhandler.ReplenishShortageSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.ReplenishShortage;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import static jp.co.daifuku.wms.replenish.dasch.ReplenishShortageDASCHParams.*;

/**
 * 計画補充欠品リスト発行に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4807 $, $Date: 2009-08-10 15:30:29 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class ReplenishShortageDASCH
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
    public ReplenishShortageDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new ReplenishShortageFinder(getConnection());
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
        ReplenishShortage[] ents = (ReplenishShortage[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (ReplenishShortage ent : ents)
        {
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());

            //引当パターンNo.
            p.set(ALLOCATE_NO, ent.getAllocateNo());
            // 補充率
            p.set(RATE, ent.getRate());
            //商品コード
            p.set(ITEM_CODE, ent.getItemCode());
            //商品名称
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME, ""));
            // 補充先エリア
            p.set(AREA_NO, ent.getAreaNo());
            // 補充先棚
            p.set(LOCATION_NO, ent.getLocationNo());
            // 補充点
            p.set(REPLENISH_QTY, ent.getReplenishmentQty());
            // ケース入数 
            int enteringQty = ent.getBigDecimal(Item.ENTERING_QTY).intValue();
            p.set(ENTERING_QTY, enteringQty);
            // 予定ケース数
            p.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(ent.getPlanQty(), enteringQty));
            // 予定ピース数
            p.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(), enteringQty));
            // 欠品ケース数
            p.set(SHORT_CASE_QTY, DisplayUtil.getCaseQty(ent.getShortageQty(), enteringQty));
            // 欠品ピース数
            p.set(SHORT_PIECE_QTY, DisplayUtil.getPieceQty(ent.getShortageQty(), enteringQty));

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
        ReplenishShortageHandler handler = new ReplenishShortageHandler(getConnection());

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
        throw new RuntimeException("This method is not implemented.");
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet trueの場合にのみOrderBy、collect条件を含む
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
    {
        ReplenishShortageSearchKey key = new ReplenishShortageSearchKey();

        // set where
        // 補充開始単位キー
        if (!StringUtil.isBlank(param.getString(SHORTAGE_KEY)))
        {
            key.setStartUnitKey(param.getString(SHORTAGE_KEY));
        }

        // 荷主コード
        key.setJoin(ReplenishShortage.CONSIGNOR_CODE, "", Item.CONSIGNOR_CODE, "(+)");
        // 商品コード
        key.setJoin(ReplenishShortage.ITEM_CODE, "", Item.ITEM_CODE, "(+)");

        if (isSet)
        {
            // set order by
            key.setAllocateNoOrder(true);
            key.setItemCodeOrder(true);
            key.setAreaNoOrder(true);
            key.setLocationNoOrder(true);

            // set collect
            key.setCollect(Item.ITEM_NAME);
            key.setCollect(Item.ENTERING_QTY);
            key.setCollect(new FieldName(ReplenishShortage.STORE_NAME, FieldName.ALL_FIELDS));
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
