// $Id: LstXDPlanMntDASCH.java 4805 2009-08-10 06:28:04Z shibamoto $
package jp.co.daifuku.wms.crossdock.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.crossdock.dasch.LstXDPlanMntDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanSearchKey;
import jp.co.daifuku.wms.base.entity.CrossDockPlan;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * TC予定検索に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4805 $, $Date: 2009-08-10 15:28:04 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstXDPlanMntDASCH
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
     * current now
     */
    private int _current = -1;

    /**
     * Total number of available records
     */
    private int _total = -1;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Constructor to create DASCH object
     * @param conn Database Connection
     * @param parent Caller Class
     * @param locale Browser Locale
     * @param ui DfkUserInfo
     */
    public LstXDPlanMntDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * Fetches data from Database for given search parametres.
     * @param p Params search parametres
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public void search(Params p)
            throws CommonException
    {
        // Create Finder Object
        _finder = new CrossDockPlanFinder(getConnection());
        // Initialize record counts
        _finder.open(isForwardOnly());
        // Create Search Key and search for Records
        _finder.search(createSearchKey(p, true));

        _current = -1;

        setMessage("6001013");
    }

    /**
     * Returns true if next DB entity is available
     * 帳票出力で使用します。
     * @return returns true if next DB entity is available. else false
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean next()
            throws CommonException
    {
        _current++;
        return _total > _current;
    }

    /**
     * Returns next DB entity
     * 帳票出力で使用します。
     * @return DB entity as Param object
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public Params get()
            throws CommonException
    {
        //throw new ScheduleException("This method is not implemented.");
        // get Next entity from finder class
        CrossDockPlan[] ents = (CrossDockPlan[])_finder.getEntities(1);
        Params p = new Params();

        // conver Entity to Param object
        for (CrossDockPlan ent : ents)
        {
            p.set(PLAN_DATE, WmsFormatter.toDate(ent.getPlanDay()));
            p.set(BATCH, ent.getBatchNo());
            p.set(SUPPLIER_CODE, ent.getSupplierCode());
            p.set(SUPPLIER_NAME, ent.getValue(Supplier.SUPPLIER_NAME));
            p.set(RECEIVING_TICKET, ent.getReceiveTicketNo());
            p.set(RECEIVING_TICKET_LINE, ent.getReceiveLineNo());
            p.set(ITEM_CODE, ent.getItemCode());
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME));
            p.set(LOT, ent.getValue(CrossDockPlan.PLAN_LOT_NO));
            break;
        }
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
     * Returns number of available entities for given search conditions
     * 帳票発行、一覧表示で使用します。
     * @param p Params , search parameters
     * @return count , number of available entities
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected int actualCount(Params p)
            throws CommonException
    {
        //throw new ScheduleException("This method is not implemented.");
        CrossDockPlanHandler handler = new CrossDockPlanHandler(getConnection());

        // find count
        _total = handler.count(createSearchKey(p, false));

        return _total;
    }

    /**
     * Returns DB entities for given range
     * 一覧表示で使用します。
     *
     * @param start 開始位置
     * @param cnt 取得件数
     * @return List, Returns the entities as List
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        //throw new RuntimeException("This method is not implemented.");
        List<Params> params = new ArrayList<Params>();

        CrossDockPlan[] ents = (CrossDockPlan[])_finder.getEntities(start, start + cnt);

        for (CrossDockPlan ent : ents)
        {
            Params p = new Params();
            p.set(COLUMN_1, String.valueOf(params.size() + 1 + start));
            p.set(PLAN_DATE, WmsFormatter.toDate(ent.getPlanDay()));
            p.set(BATCH, ent.getBatchNo());
            p.set(BATCH, ent.getBatchNo());
            p.set(SUPPLIER_CODE, ent.getSupplierCode());
            p.set(SUPPLIER_NAME, ent.getValue(Supplier.SUPPLIER_NAME));
            p.set(RECEIVING_TICKET, ent.getReceiveTicketNo());
            p.set(RECEIVING_TICKET_LINE, ent.getReceiveLineNo());
            p.set(ITEM_CODE, ent.getItemCode());
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME));
            p.set(LOT, ent.getValue(CrossDockPlan.PLAN_LOT_NO));
            p.set(CASE_PACK, ent.getValue(Item.ENTERING_QTY));
            p.set(JAN_CODE, ent.getValue(Item.JAN));
            p.set(CASE_ITF, ent.getValue(Item.ITF));

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
        CrossDockPlanSearchKey rKey = new CrossDockPlanSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        // Receiving Plan Date : 出庫予定日
        if (!StringUtil.isBlank(param.getString(PLAN_DATE)))
        {
            rKey.setPlanDay(param.getString(PLAN_DATE));
        }
        // Batch #
        if (!StringUtil.isBlank(param.getString(BATCH)))
        {
            rKey.setBatchNo(param.getString(BATCH));
        }
        // Supplier Code
        if (!StringUtil.isBlank(param.getString(SUPPLIER_CODE)))
        {
            rKey.setSupplierCode(param.getString(SUPPLIER_CODE));
        }
        // Receiving Ticket No : 伝票No.
        if (!StringUtil.isBlank(param.getString(RECEIVING_TICKET)))
        {
            rKey.setReceiveTicketNo(param.getString(RECEIVING_TICKET));
        }
        // Receiving Ticket Line No : 伝票 Line No.
        if (!StringUtil.isBlank(param.getString(RECEIVING_TICKET_LINE)))
        {
            rKey.setReceiveLineNo(param.getInt(RECEIVING_TICKET_LINE));
        }
        if (!StringUtil.isBlank(param.getString(LOT)))
        {
            rKey.setPlanLotNo(param.getString(LOT));
        }
        // 荷主コード
        rKey.setConsignorCode(param.getString(CONSIGNOR_CODE));
        // 状態フラグ
        if (!StringUtil.isBlank(param.getString(STATUS_FLAG)))
        {
            rKey.setStatusFlag(param.getString(STATUS_FLAG));
        }
        // 該当しない場合は削除以外
        else
        {
            // 状態フラグ(削除ではない)
            rKey.setStatusFlag(CrossDockPlan.STATUS_FLAG_DELETE, "!=");
        }
        
        // Join 
        rKey.setJoin(CrossDockPlan.CONSIGNOR_CODE, "", Item.CONSIGNOR_CODE, "(+)");
        rKey.setJoin(CrossDockPlan.ITEM_CODE, "", Item.ITEM_CODE, "(+)");
        rKey.setJoin(CrossDockPlan.CONSIGNOR_CODE, "", Supplier.CONSIGNOR_CODE, "(+)");
        rKey.setJoin(CrossDockPlan.SUPPLIER_CODE, "", Supplier.SUPPLIER_CODE, "(+)");

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            rKey.setPlanDayOrder(true);
            rKey.setBatchNoOrder(true);
            rKey.setSupplierCodeOrder(true);
            rKey.setReceiveTicketNoOrder(true);
            rKey.setReceiveLineNoOrder(true);
            rKey.setPlanLotNoOrder(true);

            // OrderByや、collect項目を記載する
            rKey.setPlanDayCollect();
            rKey.setBatchNoCollect();
            rKey.setSupplierCodeCollect();
            rKey.setCollect(Supplier.SUPPLIER_NAME);
            rKey.setReceiveTicketNoCollect();
            rKey.setReceiveLineNoCollect();
            rKey.setItemCodeCollect();
            rKey.setCollect(Item.ITEM_NAME);
            rKey.setPlanLotNoCollect();
            rKey.setCollect(Item.ENTERING_QTY);
            rKey.setCollect(Item.JAN);
            rKey.setCollect(Item.ITF);
        }
        return rKey;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * Returns current repository info for this class
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }
}
//end of class
