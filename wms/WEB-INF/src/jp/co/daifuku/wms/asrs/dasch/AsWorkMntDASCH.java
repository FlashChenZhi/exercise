// $Id: AsWorkMntDASCH.java 6901 2010-01-25 11:22:02Z kumano $
package jp.co.daifuku.wms.asrs.dasch;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.dasch.AsWorkMntDASCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.controller.StationController;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.GenericEntity;
import jp.co.daifuku.wms.handler.db.DefaultDDBFinder;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * AS/RS 作業メンテナンスに必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 6901 $, $Date: 2010-01-25 20:22:02 +0900 (月, 25 1 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class AsWorkMntDASCH
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
    private DefaultDDBFinder _finder = null;

    /**
     * 現在点
     */
    private int _current = -1;

    /**
     * レコード総数
     */
    private int _total = -1;

    /**
     * クラス変数
     */
    private String _processStatus;

    /**
     * 引当されている場合の表示文字
     */
    private static final String ALLOCATE_YES = "*";

    /**
     * 0の定数
     */
    private static final BigDecimal ZERO = new BigDecimal(0);

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
    public AsWorkMntDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new DefaultDDBFinder(getConnection(), new GenericEntity());
        // Initialize record counts
        _finder.open(isForwardOnly());
        // Create Search Key and search for Records
        _finder.search(getPrintSql(p, true));

        _processStatus = p.getString(ASRS_PROCESS);

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
        //throw new ScheduleException("This method is not implemented.");
        // get Next entity from finder class
        Entity[] ents = _finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object

        StationController stCtrl = new StationController(getConnection(), getClass());

        for (Entity ent : ents)
        {
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            if (StringUtil.isBlank(ent.getValue(CarryInfo.SOURCE_STATION_NO)))
            {
                p.set(SOURCE_STATION_NO, "");
            }
            else
            {
                p.set(SOURCE_STATION_NO, stCtrl.getDispStationNo((String)ent.getValue(CarryInfo.SOURCE_STATION_NO)));
            }
            if (StringUtil.isBlank(ent.getValue(CarryInfo.DEST_STATION_NO)))
            {
                p.set(DEST_STATION_NO, "");
            }
            else
            {
                p.set(DEST_STATION_NO, stCtrl.getDispStationNo((String)ent.getValue(CarryInfo.DEST_STATION_NO)));
            }
            p.set(JOB_NO, (String)ent.getValue(CarryInfo.WORK_NO));
            String workType = (String)ent.getValue(CarryInfo.WORK_TYPE);
            p.set(WORK_TYPE, DisplayResource.getWorkType(workType, getLocale()));
            String retrievalDetail = (String)ent.getValue(CarryInfo.RETRIEVAL_DETAIL);
            if (CarryInfo.RETRIEVAL_DETAIL_PICKING.equals(retrievalDetail)
                    || CarryInfo.RETRIEVAL_DETAIL_UNIT.equals(retrievalDetail))
            {
                p.set(RETRIEVAL_DETAIL, DisplayResource.getRetrievalDetail(retrievalDetail, getLocale()));
            }
            else
            {
                p.set(RETRIEVAL_DETAIL, DisplayResource.getRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_UNKNOWN,
                        getLocale()));
            }
            String cmdStatus = (String)ent.getValue(CarryInfo.CMD_STATUS);
            p.set(CMD_STATUS, (DisplayResource.getCmdStatus(cmdStatus, getLocale())));
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());
            p.set(CARRY_KEY, (String)ent.getValue(CarryInfo.CARRY_KEY));
            p.set(SCHEDULE_NO, (String)ent.getValue(CarryInfo.SCHEDULE_NO));
            p.set(MAINTENANCE_TYPE, DisplayResource.getAsrsProcessStatus(_processStatus, getLocale()));

            if (StringUtil.isBlank(((String)ent.getValue(WorkInfo.JOB_NO, ""))))
            {
                p.set(ALLOCATION_FLAG, "");
            }
            else
            {
                p.set(ALLOCATION_FLAG, ALLOCATE_YES);
            }
            p.set(ITEM_CODE, (String)ent.getValue(Stock.ITEM_CODE));
            p.set(ITEM_NAME, (String)ent.getValue(Item.ITEM_NAME, ""));
            int enteringQty = ent.getBigDecimal(Item.ENTERING_QTY).intValue();
            p.set(ENTERING_QTY, enteringQty);
            int stockQty = ent.getBigDecimal(Stock.STOCK_QTY).intValue();
            p.set(STOCK_CASE_QTY, DisplayUtil.getCaseQty(stockQty, enteringQty));
            p.set(STOCK_PIECE_QTY, DisplayUtil.getPieceQty(stockQty, enteringQty));
            int planQty = ent.getBigDecimal(WorkInfo.PLAN_QTY, ZERO).intValue();
            p.set(WORK_CASE_QTY, DisplayUtil.getCaseQty(planQty, enteringQty));
            p.set(WORK_PIECE_QTY, DisplayUtil.getPieceQty(planQty, enteringQty));
            p.set(STORAGE_DAY, ent.getDate(Stock.STORAGE_DATE));
            p.set(STORAGE_TIME, ent.getDate(Stock.STORAGE_DATE));
            p.set(LOT_NO, (String)ent.getValue(Stock.LOT_NO, ""));

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

    /**
     *
     * finder,Connection close
     */
    public void closeFinder()
    {
        if (_finder != null)
        {
            _finder.close();
        }

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
            // Create Finder Object
            handler = new DefaultDDBHandler(getConnection());
            // Initialize record counts

            // Create Search Key and search for Records
            handler.execute(getPrintSql(p, false));

            Entity[] entity = handler.getEntities(1, new CarryInfo());

            int cnt = entity[0].getBigDecimal(new FieldName("", "CNT")).intValue();

            _total = cnt;

            return _total;
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
        throw new RuntimeException("This method is not implemented.");
    }

    /**
     * 印刷データ取得用のSQLを取得します。<BR>
     * @param p 検索条件パラメータ
     * @param flg 件数検索条件フラグ
     * @return 印刷データ取得用のSQL
     */
    protected String getPrintSql(Params p, boolean flg)
    {
        StringBuffer sql = new StringBuffer();

        sql.append("SELECT ");
        if (flg)
        {
            sql.append("NVL(DNCARRYINFO.SOURCE_STATION_NO, DNCARRYINFO.AISLE_STATION_NO) AS SOURCE_STATION_NO, ");
            sql.append("NVL(DNCARRYINFO.DEST_STATION_NO, DNCARRYINFO.AISLE_STATION_NO) AS DEST_STATION_NO, ");
            sql.append("DNCARRYINFO.WORK_NO AS WORK_NO, ");
            sql.append("DNCARRYINFO.WORK_TYPE AS WORK_TYPE, ");
            sql.append("DNCARRYINFO.RETRIEVAL_DETAIL AS RETRIEVAL_DETAIL, ");
            sql.append("DNCARRYINFO.CMD_STATUS AS CMD_STATUS, ");
            sql.append("DNCARRYINFO.CARRY_KEY AS CARRY_KEY, ");
            sql.append("DNCARRYINFO.SCHEDULE_NO AS SCHEDULE_NO, ");
            sql.append("DNSTOCK.AREA_NO AS AREA_NO, ");
            sql.append("DNSTOCK.ITEM_CODE AS ITEM_CODE, ");
            sql.append("DNSTOCK.STOCK_QTY AS STOCK_QTY, ");
            sql.append("DNSTOCK.STORAGE_DATE AS STORAGE_DATE, ");
            sql.append("DNSTOCK.LOT_NO AS LOT_NO, ");
            sql.append("DNWORKINFO.PLAN_QTY AS PLAN_QTY, ");
            sql.append("DNWORKINFO.JOB_NO AS JOB_NO, ");
            sql.append("DMITEM.ITEM_NAME AS ITEM_NAME, ");
            sql.append("DMITEM.ENTERING_QTY AS ENTERING_QTY ");
        }
        else
        {
            sql.append(" COUNT (*) AS CNT ");
        }

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
        sql.append("ORDER BY ");
        sql.append("DNWORKINFO.CONSIGNOR_CODE ASC, ");
        sql.append("DNSTOCK.ITEM_CODE ASC, ");
        sql.append("DNSTOCK.STORAGE_DATE ASC, ");
        sql.append("DNSTOCK.LOT_NO ASC ");

        return String.valueOf(sql);
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 文字列をフォーマットします。テキストファイルに出力する際、文字列の両端に"をセットします。<BR>
     * ただし与えられた文字列がnull、0バイトの文字列だった場合、0バイトの文字列を返します。
     * @param  fmt フォーマットの対象となる文字列を指定します。
     * @return フォーマットされた文字列を返します。
     */
    protected String format(String fmt)
    {
        return (SimpleFormat.format(fmt));
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
