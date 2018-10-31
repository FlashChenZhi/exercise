// $Id: LstReceivingPlanMntDASCH.java 4807 2009-08-10 06:30:29Z shibamoto $
package jp.co.daifuku.wms.receiving.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.receiving.dasch.LstReceivingPlanMntDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanSearchKey;
import jp.co.daifuku.wms.base.entity.ReceivingPlan;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 入荷予定検索に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4807 $, $Date: 2009-08-10 15:30:29 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstReceivingPlanMntDASCH
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
    public LstReceivingPlanMntDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new ReceivingPlanFinder(getConnection());

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
        ReceivingPlan[] ents = (ReceivingPlan[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (ReceivingPlan ent : ents)
        {
            p.set(PLAN_DATE, WmsFormatter.toDate(ent.getPlanDay()));
            p.set(SUPPLIER_CODE, ent.getSupplierCode());
            p.set(SLIP_NUMBER, ent.getReceiveTicketNo());

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
     * Returns number of available entities for given search conditions
     * 帳票発行、一覧表示で使用します。
     * @param p Params , search parameters
     * @return count , number of available entities
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected int actualCount(Params p)
            throws CommonException
    {
        ReceivingPlanHandler handler = new ReceivingPlanHandler(getConnection());

        // find count
        _total = handler.count(createSearchKey(p, false));

        return _total;
    }

    /**
     * Returns DB entities for given range
     * 一覧表示で使用します。
     *
     * @param start start
     * @param cnt end
     * @return List Returns the entities as List
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        //throw new RuntimeException("This method is not implemented.");
        List<Params> params = new ArrayList<Params>();

        ReceivingPlan[] ents = (ReceivingPlan[])_finder.getEntities(start, start + cnt);

        for (ReceivingPlan ent : ents)
        {
            Params p = new Params();
            p.set(COLUMN_1, String.valueOf(params.size() + 1 + start));
            p.set(PLAN_DATE, WmsFormatter.toDate(ent.getPlanDay()));
            p.set(SUPPLIER_CODE, ent.getSupplierCode());
            p.set(SUPPLIER_NAME, (String)ent.getValue(Supplier.SUPPLIER_NAME, ""));
            p.set(SLIP_NUMBER, ent.getReceiveTicketNo());
            p.set(TICKET, ent.getReceiveTicketNo());
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
        ReceivingPlanSearchKey rKey = new ReceivingPlanSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by

        // Plan Date
        if (!StringUtil.isBlank(param.getString(PLAN_DATE)))
        {
            rKey.setPlanDay(param.getString(PLAN_DATE));
        }

        // Supplier Code
        if (!StringUtil.isBlank(param.getString(SUPPLIER_CODE)))
        {
            rKey.setSupplierCode(param.getString(SUPPLIER_CODE));
        }
        // Slip No.
        if (!StringUtil.isBlank(param.getString(SLIP_NUMBER)))
        {
            rKey.setReceiveTicketNo(param.getString(SLIP_NUMBER));
        }
        // Consignor Code
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
            rKey.setStatusFlag(ReceivingPlan.STATUS_FLAG_DELETE, "!=");
        }
        // Grouping condition : 集約キーのセット
        // 予定日
        rKey.setPlanDayGroup();
        // Supplier Code
        rKey.setSupplierCodeGroup();
        // 伝票No
        rKey.setReceiveTicketNoGroup();
        
        // join
        rKey.setJoin(ReceivingPlan.CONSIGNOR_CODE, Supplier.CONSIGNOR_CODE);
        rKey.setJoin(ReceivingPlan.SUPPLIER_CODE, Supplier.SUPPLIER_CODE);


        if (isSet)
        {
            // OrderByや、collect項目を記載する
            // ソートキーのセット
            // 出庫予定日
            rKey.setPlanDayOrder(true);
            rKey.setSupplierCodeOrder(true);
            rKey.setReceiveTicketNoOrder(true);


            // 取得キーのセット
            rKey.setPlanDayCollect();
            rKey.setSupplierCodeCollect();
            rKey.setReceiveTicketNoCollect();
            rKey.setCollect(Supplier.SUPPLIER_NAME, "MAX", Supplier.SUPPLIER_NAME);

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
