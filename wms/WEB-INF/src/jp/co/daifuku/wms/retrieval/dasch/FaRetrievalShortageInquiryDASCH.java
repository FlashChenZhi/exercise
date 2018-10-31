// $Id: Dasch_ja.java 5085 2009-10-01 08:09:16Z okamura $
package jp.co.daifuku.wms.retrieval.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.retrieval.dasch.FaRetrievalShortageInquiryDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.ShortageInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.SearchKey;

/**
 * 欠品情報照会(FA)に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 5085 $, $Date:: 2009-10-01 17:09:16 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okamura $
 */
public class FaRetrievalShortageInquiryDASCH
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
    public FaRetrievalShortageInquiryDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new ShortageInfoFinder(getConnection());
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
    	ShortageInfo[] ents = (ShortageInfo[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (ShortageInfo ent : ents)
        {
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());
            p.set(SETTING_DAY, WmsFormatter.toDispDate(ent.getRetrievalStartDate(),getLocale()));
            p.set(SETTING_TIME, WmsFormatter.toDispTime(ent.getRetrievalStartDate(),getLocale()));

            p.set(BATCH_NO, ent.getBatchNo());
            p.set(TICKET_NO, ent.getShipTicketNo());
            if(StringUtil.isBlank(ent.getShipTicketNo()))
            {
            	p.set(LINE_NO, null);
            }
            else
            {
            	p.set(LINE_NO, ent.getShipLineNo());
            }
            p.set(ITEM_CODE, ent.getItemCode());
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME));
            p.set(LOT_NO, ent.getPlanLotNo());
            p.set(PLAN_QTY, ent.getThisTimePlanQty());
            p.set(SHORTAGE_QTY, ent.getShortageQty());
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
    	ShortageInfoHandler handler = new ShortageInfoHandler(getConnection());

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
        ShortageInfo[] ents = (ShortageInfo[])_finder.getEntities(start, start + cnt);

        for (ShortageInfo ent : ents)
        {
            Params p = new Params();
            p.set(BATCH_NO, ent.getBatchNo());
            p.set(TICKET_NO, ent.getShipTicketNo());
            if(StringUtil.isBlank(ent.getShipTicketNo()))
            {
            	p.set(LINE_NO, null);
            }
            else
            {
            	p.set(LINE_NO, ent.getShipLineNo());
            }
            p.set(ITEM_CODE, ent.getItemCode());
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME));
            p.set(PLAN_QTY, ent.getThisTimePlanQty());
            p.set(SHORTAGE_QTY, ent.getShortageQty());

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
    	ShortageInfoSearchKey searchKey = new ShortageInfoSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        // 出庫開始日時
        searchKey.setRetrievalStartDate(WmsFormatter.getFromSearchDate(param.getDate(SETTING_DAY), param.getDate(SETTING_TIME)));
        // バッチNo.
        if(!StringUtil.isBlank(param.getString(BATCH_NO)))
        {
        	searchKey.setBatchNo(param.getString(BATCH_NO));
        }
        // 伝票No.
        String[] ticketFromTo = WmsFormatter.getFromTo(param.getString(FROM_TICKET_NO), param.getString(TO_TICKET_NO));
        if(!StringUtil.isBlank(ticketFromTo[0]))
        {
        	searchKey.setShipTicketNo(ticketFromTo[0], ">=");
        }
        if(!StringUtil.isBlank(ticketFromTo[1]))
        {
        	searchKey.setShipTicketNo(ticketFromTo[1], "<=");
        }
        
        //商品マスタ
        // 荷主コード
        searchKey.setJoin(ShortageInfo.CONSIGNOR_CODE, "", Item.CONSIGNOR_CODE, "(+)");
        // 商品コード
        searchKey.setJoin(ShortageInfo.ITEM_CODE, "", Item.ITEM_CODE, "(+)");

        //出荷先マスタ
        // 荷主コード
        searchKey.setJoin(ShortageInfo.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
        // 出荷先コード
        searchKey.setJoin(ShortageInfo.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            searchKey.setBatchNoOrder(true);
            searchKey.setShipTicketNoOrder(true);
            searchKey.setShipLineNoOrder(true);
            searchKey.setItemCodeOrder(true);
            searchKey.setPlanLotNoOrder(true);

            searchKey.setBatchNoCollect();
            searchKey.setShipTicketNoCollect();
            searchKey.setShipLineNoCollect();
            searchKey.setItemCodeCollect();
            searchKey.setPlanLotNoCollect();
            searchKey.setThisTimePlanQtyCollect();
            searchKey.setShortageQtyCollect();
            searchKey.setRetrievalStartDateCollect();
            
            searchKey.setCollect(Item.ITEM_NAME);
        }

        return searchKey;
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
