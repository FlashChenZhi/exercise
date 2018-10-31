// $Id: FaRetrievalRMCancelDASCH.java 6654 2010-01-07 01:11:02Z okamura $
package jp.co.daifuku.wms.asrs.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.asrs.dasch.FaRetrievalRMCancelDASCHParams.*;

import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.RetrievalCancelListFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalCancelListHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalCancelListSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalCancelList;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 出庫キャンセルリスト発行のためのデータを検索します。<BR>
 * 
 * @version $Revision: 6654 $, $Date:: 2010-01-07 10:11:02 +0900#$
 * @author  Y.Okamura
 * @author  Last commit: $Author: okamura $
 */
public class FaRetrievalRMCancelDASCH
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
    public FaRetrievalRMCancelDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new RetrievalCancelListFinder(getConnection());
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
        RetrievalCancelList[] ents = (RetrievalCancelList[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (RetrievalCancelList ent : ents)
        {
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DATE, getPrintDate());

            p.set(BATCH_NO, ent.getBatchNo());
            String ticketNo = ent.getShipTicketNo();
            p.set(TICKET_NO, ticketNo);
            // 伝票No.がnullの場合でも行No.にはゼロがセットされているので、伝票がnullの場合は空白をセットする
            if (StringUtil.isBlank(ticketNo))
            {
                p.set(LINE_NO, "");
            }
            else
            {
                p.set(LINE_NO, ent.getShipLineNo());
            }
            p.set(ITEM_CODE, ent.getItemCode());
            p.set(ITEM_NAME, ent.getItemName());
            p.set(LOT_NO, ent.getLotNo());
            p.set(PLAN_QTY, ent.getPlanQty());
            p.set(CANCEL_QTY, ent.getCancelQty());
            p.set(RESULT_QTY, ent.getResultQty());
            p.set(WORK_TYPE, DisplayResource.getJobType(ent.getJobType()));
            
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
//        if (_finder != null)
//        {
//            _finder.close();
//        }
//        super.close();
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
        RetrievalCancelListHandler handler = new RetrievalCancelListHandler(getConnection());

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
        RetrievalCancelListSearchKey key = new RetrievalCancelListSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        // group by
        key.setJobTypeGroup();
        key.setBatchNoGroup();
        key.setShipTicketNoGroup();
        key.setShipLineNoGroup();
        key.setItemCodeGroup();
        key.setLotNoGroup();
        
        
        if (isSet)
        {
            // collect
            key.setBatchNoCollect();
            key.setShipTicketNoCollect();
            key.setShipLineNoCollect();
            key.setItemCodeCollect();
            key.setItemNameCollect("max");
            key.setLotNoCollect();
            key.setPlanQtyCollect("max");
            key.setCancelQtyCollect("sum");
            key.setResultQtyCollect("max");
            key.setJobTypeCollect();
            
            // order by
            key.setBatchNoOrder(true);
            key.setShipTicketNoOrder(true);
            key.setShipLineNoOrder(true);
            key.setItemCodeOrder(true);
            key.setLotNoOrder(true);
            key.setJobTypeOrder(true);
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
