// $Id: BatchStartCancelDASCH.java 4689 2009-07-16 00:23:45Z okayama $
package jp.co.daifuku.pcart.retrieval.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.retrieval.dasch.BatchStartCancelDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * バッチ開始/キャンセルに必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4689 $, $Date:: 2009-07-16 09:23:45 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class BatchStartCancelDASCH
        extends AbstractWmsDASCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /** カラム定義 商品画像(<code>ITEM_PICTURE</code>) */
    private static final FieldName ITEM_PICTURE = new FieldName(PCTItem.STORE_NAME, "ITEM_PICTURE");

    /** カラム定義 商品画像(あり・なし)(<code>PICTURE_FLAG</code>) */
    private static final FieldName PICTURE_FLAG = new FieldName(PCTItem.STORE_NAME, "PICTURE_FLAG");

    /** フィールド定義(<code>_PICTURE</code>) */
    private static final FieldName _PICTURE = new FieldName("", "NVL2((DMPCTITEM.ITEM_PICTURE ),'true','false' )");

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
    public BatchStartCancelDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new PCTRetPlanFinder(getConnection());

        // Initialize record counts
        // データ件数初期化(Excel大量出力対応)
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
        Params p = new Params();

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
        PCTRetPlanHandler handler = new PCTRetPlanHandler(getConnection());

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
        //throw new RuntimeException("This method is not implemented.");
        List<Params> params = new ArrayList<Params>();
        PCTRetPlan[] ents = (PCTRetPlan[])_finder.getEntities(start, start + cnt);

        AreaController areaCon = new AreaController(getConnection(), this.getClass());

        for (PCTRetPlan ent : ents)
        {
            Params p = new Params();
            p.set(BatchStartCancelDASCHParams.CONSIGNOR_CODE, ent.getValue(PCTItem.CONSIGNOR_CODE));
            p.set(BatchStartCancelDASCHParams.CONSIGNOR_NAME, ent.getValue(PCTItem.CONSIGNOR_NAME));
            p.set(BatchStartCancelDASCHParams.ITEM_CODE, ent.getValue(PCTItem.ITEM_CODE));
            p.set(BatchStartCancelDASCHParams.ITEM_NAME, ent.getValue(PCTItem.ITEM_NAME));
            p.set(BatchStartCancelDASCHParams.ENTERING_QTY, ent.getValue(PCTItem.LOT_ENTERING_QTY));
            p.set(BatchStartCancelDASCHParams.JAN, ent.getValue(PCTItem.JAN));
            p.set(BatchStartCancelDASCHParams.ITF, ent.getValue(PCTItem.ITF));

            String loc =
                    areaCon.toDispLocation((String)ent.getValue(PCTRetPlan.PLAN_AREA_NO),
                            (String)ent.getValue(PCTItem.LOCATION_NO_1));

            p.set(BatchStartCancelDASCHParams.LOCATION_NO, loc);
            p.set(BatchStartCancelDASCHParams.SINGLE_WEIGHT, ent.getValue(PCTItem.SINGLE_WEIGHT));
            p.set(BatchStartCancelDASCHParams.WEGHT_DISTINCT_RATE, ent.getValue(PCTItem.WEIGHT_DISTINCT_RATE));
            p.set(BatchStartCancelDASCHParams.MAX_INSPECTION_UNIT_QTY, ent.getValue(PCTItem.MAX_INSPECTION_UNIT_QTY));
            p.set(BatchStartCancelDASCHParams.LAST_UPDATE,
                    WmsFormatter.toParamDate((Date)ent.getValue(PCTItem.LAST_UPDATE_DATE)));
            p.set(BatchStartCancelDASCHParams.WORK_DAY,
                    WmsFormatter.toParamDate((Date)ent.getValue(PCTItem.LAST_USED_DATE)));

            p.set(BatchStartCancelDASCHParams.MESSAGE1, ent.getValue(PCTItem.INFORMATION));
            p.set(BatchStartCancelDASCHParams.CONSIGNOR_CODE, ent.getValue(PCTItem.CONSIGNOR_CODE));

            p.set(BatchStartCancelDASCHParams.ITEM_PICTURE_FLAG,
                    Boolean.valueOf(String.valueOf(ent.getValue(PICTURE_FLAG))));

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
    @SuppressWarnings("unchecked")
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        PCTRetPlanSearchKey key = new PCTRetPlanSearchKey();

        // バッチNo.
        List<String> batchList = new ArrayList<String>();

        List<String> list = (List<String>)param.get(BATCH_NO);

        batchList = list;

        String[] batchNos = new String[batchList.size()];
        for (int i = 0; i < batchList.size(); i++)
        {
            batchNos[i] = new String();
            batchNos[i] = (list.get(i));

        }
        key.setBatchNo(batchNos, true);

        // バッチSeqNo.
        List<String> batchSeqList = new ArrayList<String>();

        List<String> seqList = (List<String>)param.get(BATCH_SEQ_NO);

        batchSeqList = seqList;

        String[] batchSeqNos = new String[batchSeqList.size()];
        for (int i = 0; i < batchSeqList.size(); i++)
        {
            batchSeqNos[i] = new String();
            batchSeqNos[i] = (seqList.get(i));

        }
        key.setBatchSeqNo(batchSeqNos, true);

        // 単重量未登録商品
        key.setKey(PCTItem.SINGLE_WEIGHT, 0.0, "<=", "", "", true);

        // 結合条件
        // 荷主コード
        key.setJoin(PCTRetPlan.CONSIGNOR_CODE, PCTItem.CONSIGNOR_CODE);
        // 商品ｺｰﾄﾞ
        key.setJoin(PCTRetPlan.ITEM_CODE, PCTItem.ITEM_CODE);
        // ﾛｯﾄ入数
        key.setJoin(PCTRetPlan.LOT_ENTERING_QTY, PCTItem.LOT_ENTERING_QTY);

        // 集約条件
        // OrderByや、collect項目を記載する
        // 荷主コード
        key.setGroup(PCTItem.CONSIGNOR_CODE);
        // 荷主名称
        key.setGroup(PCTItem.CONSIGNOR_NAME);
        // 商品ｺｰﾄﾞ
        key.setGroup(PCTItem.ITEM_CODE);
        // 商品名称
        key.setGroup(PCTItem.ITEM_NAME);
        // ﾛｯﾄ入数
        key.setGroup(PCTItem.LOT_ENTERING_QTY);
        // JAN
        key.setGroup(PCTItem.JAN);
        // ITF
        key.setGroup(PCTItem.ITF);
        // ロケーションNo.
        key.setGroup(PCTItem.LOCATION_NO_1);
        // 単重量
        key.setGroup(PCTItem.SINGLE_WEIGHT);
        // 重量誤差率
        key.setGroup(PCTItem.WEIGHT_DISTINCT_RATE);
        // 最大検品可能数
        key.setGroup(PCTItem.MAX_INSPECTION_UNIT_QTY);
        // 最終更新日
        key.setGroup(PCTItem.LAST_UPDATE_DATE);
        // 最終使用日
        key.setGroup(PCTItem.LAST_USED_DATE);
        // 商品画像登録
        key.setGroup(_PICTURE);
        // メッセージ
        key.setGroup(PCTItem.INFORMATION);

        key.setGroup(PCTRetPlan.PLAN_AREA_NO);

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            // 荷主コード
            key.setCollect(PCTItem.CONSIGNOR_CODE);
            // 荷主名称
            key.setCollect(PCTItem.CONSIGNOR_NAME);
            // 商品ｺｰﾄﾞ
            key.setCollect(PCTItem.ITEM_CODE);
            // 商品名称
            key.setCollect(PCTItem.ITEM_NAME);
            // ﾛｯﾄ入数
            key.setCollect(PCTItem.LOT_ENTERING_QTY);
            // JAN
            key.setCollect(PCTItem.JAN);
            // ITF
            key.setCollect(PCTItem.ITF);
            // ロケーションNo.
            key.setCollect(PCTItem.LOCATION_NO_1);
            // 単重量
            key.setCollect(PCTItem.SINGLE_WEIGHT);
            // 重量誤差率
            key.setCollect(PCTItem.WEIGHT_DISTINCT_RATE);
            // 最大検品可能数
            key.setCollect(PCTItem.MAX_INSPECTION_UNIT_QTY);
            // 最終更新日
            key.setCollect(PCTItem.LAST_UPDATE_DATE);
            // 最終使用日
            key.setCollect(PCTItem.LAST_USED_DATE);
            // 商品画像登録
            key.setCollect(ITEM_PICTURE, "NVL2({0},'TRUE','FALSE')", PICTURE_FLAG);
            // メッセージ
            key.setCollect(PCTItem.INFORMATION);

            // エリアNo.
            key.setCollect(PCTRetPlan.PLAN_AREA_NO);

            // 荷主コード
            key.setOrder(PCTItem.CONSIGNOR_CODE, true);
            // 荷主名称
            key.setOrder(PCTItem.CONSIGNOR_NAME, true);
            // 商品ｺｰﾄﾞ
            key.setOrder(PCTItem.ITEM_CODE, true);
            // 商品名称
            key.setOrder(PCTItem.ITEM_NAME, true);
            // ﾛｯﾄ入数
            key.setOrder(PCTItem.LOT_ENTERING_QTY, true);

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
