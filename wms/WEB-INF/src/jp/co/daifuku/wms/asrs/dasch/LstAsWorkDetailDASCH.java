// $Id: LstAsWorkDetailDASCH.java 4804 2009-08-10 06:26:35Z shibamoto $
package jp.co.daifuku.wms.asrs.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.dasch.LstAsWorkDetailDASCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.GenericEntity;
import jp.co.daifuku.wms.handler.db.DefaultDDBFinder;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 作業明細一覧に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4804 $, $Date: 2009-08-10 15:26:35 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstAsWorkDetailDASCH
        extends AbstractWmsDASCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    private static final String ALLOCATE_YES = "*";

    // public static final int FIELD_VALUE = 1 ;
    private static final BigDecimal ZERO = new BigDecimal(0);

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /**
     * DB Finder
     */
    private DefaultDDBFinder _finder = null;

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
    public LstAsWorkDetailDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new DefaultDDBFinder(getConnection(), new GenericEntity());
        _finder.open(isForwardOnly());

        StringBuffer sql = new StringBuffer();

        sql.append("SELECT ");
        sql.append("DNSTOCK.ITEM_CODE AS ITEM_CODE, ");
        sql.append("DMITEM.ITEM_NAME AS ITEM_NAME, ");
        sql.append("DMITEM.ENTERING_QTY AS ENTERING_QTY, ");
        sql.append("DNSTOCK.STOCK_QTY AS STOCK_QTY, ");
        sql.append("DNWORKINFO.PLAN_QTY AS PLAN_QTY, ");
        sql.append("DNWORKINFO.JOB_NO AS JOB_NO, ");
        sql.append("DNSTOCK.STORAGE_DATE AS STORAGE_DATE, ");
        sql.append("DNSTOCK.LOT_NO AS LOT_NO ");
        sql.append("FROM ");
        sql.append("DNCARRYINFO, ");
        sql.append("DNSTOCK, ");
        sql.append("DMITEM, ");
        sql.append("(");
        sql.append("SELECT ");
        sql.append("DNWORKINFO.STOCK_ID, ");
        sql.append("DNWORKINFO.CONSIGNOR_CODE, ");
        sql.append("DNWORKINFO.PLAN_QTY, ");
        sql.append("DNWORKINFO.JOB_NO ");
        sql.append("FROM ");
        sql.append("DNWORKINFO ");
        sql.append("WHERE ");
        sql.append("DNWORKINFO.SYSTEM_CONN_KEY = ");
        sql.append(DBFormat.format(p.getString(CARRY_KEY)));
        sql.append(" AND DNWORKINFO.STATUS_FLAG != ");
        sql.append(DBFormat.format(WorkInfo.STATUS_FLAG_DELETE));
        sql.append(") DNWORKINFO ");
        sql.append("WHERE ");
        sql.append("DNCARRYINFO.CARRY_KEY = ");
        sql.append(DBFormat.format(p.getString(CARRY_KEY)));
        sql.append(" AND DNCARRYINFO.PALLET_ID = DNSTOCK.PALLET_ID ");
        sql.append("AND DNSTOCK.STOCK_ID = DNWORKINFO.STOCK_ID(+) ");
        sql.append("AND DNSTOCK.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE ");
        sql.append("AND DNSTOCK.ITEM_CODE = DMITEM.ITEM_CODE ");
        sql.append("ORDER BY ");
        sql.append("DNWORKINFO.CONSIGNOR_CODE ASC, ");
        sql.append("DNSTOCK.ITEM_CODE ASC, ");
        sql.append("DNSTOCK.STORAGE_DATE ASC, ");
        sql.append("DNSTOCK.LOT_NO ASC ");

        _finder.search(String.valueOf(sql));
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
        DefaultDDBHandler handler = null;
        try
        {
            handler = new DefaultDDBHandler(getConnection());

            StringBuffer sql = new StringBuffer();

            sql.append("SELECT ");
            sql.append("COUNT(*) AS CNT ");
            sql.append("FROM ");
            sql.append("DNCARRYINFO, ");
            sql.append("DNSTOCK, ");
            sql.append("DMITEM, ");
            sql.append("(");
            sql.append("SELECT ");
            sql.append("DNWORKINFO.STOCK_ID ");
            sql.append("FROM ");
            sql.append("DNWORKINFO ");
            sql.append("WHERE ");
            sql.append("DNWORKINFO.SYSTEM_CONN_KEY = ");
            sql.append(DBFormat.format(p.getString(CARRY_KEY)));
            sql.append(" AND DNWORKINFO.STATUS_FLAG != ");
            sql.append(DBFormat.format(WorkInfo.STATUS_FLAG_COMPLETION));
            sql.append(" AND DNWORKINFO.STATUS_FLAG != ");
            sql.append(DBFormat.format(WorkInfo.STATUS_FLAG_DELETE));
            sql.append(") DNWORKINFO ");
            sql.append("WHERE ");
            sql.append("DNCARRYINFO.CARRY_KEY = ");
            sql.append(DBFormat.format(p.getString(CARRY_KEY)));
            sql.append(" AND DNCARRYINFO.PALLET_ID = DNSTOCK.PALLET_ID ");
            sql.append("AND DNSTOCK.STOCK_ID = DNWORKINFO.STOCK_ID(+) ");
            sql.append("AND DNSTOCK.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE ");
            sql.append("AND DNSTOCK.ITEM_CODE = DMITEM.ITEM_CODE ");

            handler.execute(String.valueOf(sql));
            Entity[] entities = handler.getEntities(1, new GenericEntity());
            int cnt = entities[0].getBigDecimal(new FieldName("", "CNT")).intValue();

            return cnt;
        }
        finally
        {
            if (handler != null)
            {
                handler.close();
            }
        }
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
        //throw new RuntimeException("This method is not implemented.");
        List<Params> params = new ArrayList<Params>();
        GenericEntity[] ents = (GenericEntity[])_finder.getEntities(start, start + cnt);
        for (GenericEntity ent : ents)
        {
            Params p = new Params();
            // 引当
            if (StringUtil.isBlank(String.valueOf(ent.getValue(WorkInfo.JOB_NO, ""))))
            {
                p.set(ALLOC, "");
            }
            else
            {
                p.set(ALLOC, ALLOCATE_YES);
            }
            // 商品コード
            p.set(ITEM_CODE, ent.getValue(Stock.ITEM_CODE, ""));
            // 商品名称
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME, ""));
            // ケース入数
            int entering = ((BigDecimal)ent.getValue(Item.ENTERING_QTY)).intValue();
            p.set(ENTERING_QTY, entering);
            // 在庫数
            int stockQty = ((BigDecimal)ent.getValue(Stock.STOCK_QTY)).intValue();
            // 在庫ケース数
            p.set(STOCK_CASE_QTY, DisplayUtil.getCaseQty(stockQty, entering));
            // 在庫ピース数
            p.set(STOCK_PIECE_QTY, DisplayUtil.getPieceQty(stockQty, entering));
            // 予定数
            int planQty = ((BigDecimal)ent.getValue(WorkInfo.PLAN_QTY, ZERO)).intValue();
            // 予定ケース数
            p.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(planQty, entering));
            // 予定ピース数
            p.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(planQty, entering));
            // 入庫日時
            p.set(STORAGE_DATE, ent.getValue(Stock.STORAGE_DATE));
            // ロットNo.
            p.set(LOT_NO, (ent.getValue(Stock.LOT_NO, "")));

            params.add(p);
        }
        return params;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

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
