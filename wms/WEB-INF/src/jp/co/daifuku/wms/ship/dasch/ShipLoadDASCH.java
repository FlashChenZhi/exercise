package jp.co.daifuku.wms.ship.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.ship.dasch.ShipLoadDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.ShipWorkInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 出荷積込に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:42:13 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class ShipLoadDASCH
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
    public ShipLoadDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new ShipWorkInfoFinder(getConnection());
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
        ShipWorkInfo[] ents = (ShipWorkInfo[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (ShipWorkInfo ent : ents)
        {
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());

            p.set(PLAN_DATE, WmsFormatter.toDate(ent.getPlanDay()));
            p.set(CUSTOMER_CODE, ent.getCustomerCode());
            p.set(CUSTOMER_NAME, ent.getValue(Customer.CUSTOMER_NAME));
            p.set(BATCH_NO, ent.getBatchNo());
            p.set(ROUTE, ent.getValue(Customer.ROUTE));
            p.set(BERTH_NO, ent.getResultBerth());

            p.set(POSTAL_CODE, ent.getValue(Customer.POSTAL_CODE));
            p.set(PREFECTURE, ent.getValue(Customer.PREFECTURE));
            p.set(ADDRESS, ent.getValue(Customer.ADDRESS1));
            p.set(ADDRESS2, ent.getValue(Customer.ADDRESS2));
            p.set(TELEPHONE, ent.getValue(Customer.TELEPHONE));

            p.set(TICKET_NO, ent.getShipTicketNo());
            p.set(LINE_NO, ent.getShipLineNo());
            p.set(ITEM_CODE, ent.getItemCode());
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME));
            if (SystemDefine.STATUS_FLAG_COMPLETION.equals(ent.getWorkStatusFlag()))
            {
                p.set(LOT_NO, ent.getResultLotNo());
                p.set(ENTERING_QTY, ent.getBigDecimal(Item.ENTERING_QTY).intValue());
                p.set(SHIPPING_CASE_QTY, DisplayUtil.getCaseQty(ent.getResultQty(),
                        ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
                p.set(SHIPPING_PIECE_QTY, DisplayUtil.getPieceQty(ent.getResultQty(), ent.getBigDecimal(
                        Item.ENTERING_QTY).intValue()));
            }
            else
            {
                p.set(LOT_NO, ent.getPlanLotNo());
                p.set(ENTERING_QTY, ent.getBigDecimal(Item.ENTERING_QTY).intValue());
                p.set(SHIPPING_CASE_QTY, DisplayUtil.getCaseQty(ent.getPlanQty(),
                        ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
                p.set(SHIPPING_PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(),
                        ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            }
            p.set(INSPECTION, inspection(ent.getWorkStatusFlag()));
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
        ShipWorkInfoHandler handler = new ShipWorkInfoHandler(getConnection());

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
        ShipWorkInfo[] ents = (ShipWorkInfo[])_finder.getEntities(start, start + cnt);

        for (ShipWorkInfo ent : ents)
        {
            Params p = new Params();
            p.set(PLAN_DATE, WmsFormatter.toDate(ent.getPlanDay()));
            p.set(CUSTOMER_CODE, ent.getCustomerCode());
            p.set(CUSTOMER_NAME, ent.getValue(Customer.CUSTOMER_NAME));
            p.set(BATCH_NO, ent.getBatchNo());
            p.set(ROUTE, ent.getValue(Customer.ROUTE));
            p.set(BERTH_NO, ent.getResultBerth());

            p.set(POSTAL_CODE, ent.getValue(Customer.POSTAL_CODE));
            p.set(PREFECTURE, ent.getValue(Customer.PREFECTURE));
            p.set(ADDRESS, ent.getValue(Customer.ADDRESS1));
            p.set(ADDRESS2, ent.getValue(Customer.ADDRESS2));
            p.set(TELEPHONE, ent.getValue(Customer.TELEPHONE));

            p.set(TICKET_NO, ent.getShipTicketNo());
            p.set(LINE_NO, ent.getShipLineNo());
            p.set(ITEM_CODE, ent.getItemCode());
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME));
            if (SystemDefine.STATUS_FLAG_COMPLETION.equals(ent.getWorkStatusFlag()))
            {
                p.set(LOT_NO, ent.getResultLotNo());
                p.set(ENTERING_QTY, ent.getBigDecimal(Item.ENTERING_QTY).intValue());
                p.set(SHIPPING_CASE_QTY, DisplayUtil.getCaseQty(ent.getResultQty(),
                        ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
                p.set(SHIPPING_PIECE_QTY, DisplayUtil.getPieceQty(ent.getResultQty(), ent.getBigDecimal(
                        Item.ENTERING_QTY).intValue()));
            }
            else
            {
                p.set(LOT_NO, ent.getPlanLotNo());
                p.set(ENTERING_QTY, ent.getBigDecimal(Item.ENTERING_QTY).intValue());
                p.set(SHIPPING_CASE_QTY, DisplayUtil.getCaseQty(ent.getPlanQty(),
                        ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
                p.set(SHIPPING_PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(),
                        ent.getBigDecimal(Item.ENTERING_QTY).intValue()));
            }
            p.set(INSPECTION, inspection(ent.getWorkStatusFlag()));
            params.add(p);
        }
        return params;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     *
     */
    private String inspection(String statusFlag)
            throws CommonException
    {
        String statusFlagName = null;

        if (SystemDefine.STATUS_FLAG_COMPLETION.equals(statusFlag))
        {
            statusFlagName = DisplayText.getText("LBL-W0465");
        }
        else
        {
            statusFlagName = DisplayText.getText("LBL-W0464");
        }

        return statusFlagName;
    }

    /**
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        ShipWorkInfoSearchKey key = new ShipWorkInfoSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        key.setSettingUnitKey(param.getString(SETTING_UNIT_KEY));
        // DFKLOOK 3.5 ADD START
        key.setJoin(ShipWorkInfo.CONSIGNOR_CODE, Customer.CONSIGNOR_CODE);
        // DFKLOOK 3.5 ADD END
        key.setJoin(ShipWorkInfo.CUSTOMER_CODE, Customer.CUSTOMER_CODE);
        // DFKLOOK 3.5 ADD START
        key.setJoin(ShipWorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        // DFKLOOK 3.5 ADD END
        key.setJoin(ShipWorkInfo.ITEM_CODE, Item.ITEM_CODE);

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            key.setPlanDayCollect();
            key.setCustomerCodeCollect();
            key.setCollect(Customer.CARRIER_NAME);
            key.setBatchNoCollect();
            key.setCollect(Customer.ROUTE);
            key.setResultBerthCollect();
            key.setCollect(Customer.POSTAL_CODE);
            key.setCollect(Customer.PREFECTURE);
            key.setCollect(Customer.ADDRESS1);
            key.setCollect(Customer.ADDRESS2);
            key.setCollect(Customer.TELEPHONE);
            key.setShipTicketNoCollect();
            key.setShipLineNoCollect();
            key.setItemCodeCollect();
            key.setCollect(Item.ITEM_NAME);
            key.setResultLotNoCollect();
            key.setPlanLotNoCollect();
            key.setCollect(Item.ENTERING_QTY);
            key.setResultQtyCollect();
            key.setPlanQtyCollect();
            key.setWorkStatusFlagCollect();

            key.setCustomerCodeOrder(true);
            key.setShipTicketNoOrder(true);
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
