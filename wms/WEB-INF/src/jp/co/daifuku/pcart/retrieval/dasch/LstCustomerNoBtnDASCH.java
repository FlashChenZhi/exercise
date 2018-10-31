// $Id: LstCustomerNoBtnDASCH.java 4803 2009-08-10 06:24:09Z shibamoto $
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
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 出荷先一覧(登録用)に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4803 $, $Date:: 2009-08-10 15:24:09 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstCustomerNoBtnDASCH
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
    public LstCustomerNoBtnDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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

        for (PCTRetPlan ent : ents)
        {
            Params p = new Params();
            p.set(LstCustomerNoBtnDASCHParams.CUSTOMER_CODE, ent.getCustomerCode());
            p.set(LstCustomerNoBtnDASCHParams.CUSTOMER_NAME, ent.getCustomerName());

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

        // 検索条件、集約条件をセットする
        // where, group by
        // 荷主コード
        key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        // 商品ｺｰﾄﾞ
        key.setItemCode(param.getString(ITEM_CODE));
        // ﾛｯﾄ入数
        key.setLotEnteringQty(param.getInt(ENTERING_QTY));
        // バッチNo.(複数指定される場合があるためリスト形式で取得)
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
        // バッチSeqNo.(複数指定される場合があるためリスト形式で取得)
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

        // 出荷先コード
        key.setCustomerCodeGroup();
        // 出荷先名称
        key.setCustomerNameGroup();

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            // 出荷先コード
            key.setCustomerCodeCollect();
            // 出荷先名称
            key.setCustomerNameCollect();

            // 出荷先コード
            key.setCustomerCodeOrder(true);
            // 出荷先名称
            key.setCustomerNameOrder(true);


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
