// $Id: InventoryCompleteDASCH.java 6901 2010-01-25 11:22:02Z kumano $
package jp.co.daifuku.wms.inventorychk.dasch;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.inventorychk.dasch.InventoryCompleteDASCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.InventWorkInfo;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.GenericEntity;
import jp.co.daifuku.wms.handler.db.DefaultDDBFinder;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryInParameter;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryOutParameter;

/**
 * 棚卸結果確定に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 6901 $, $Date: 2010-01-25 20:22:02 +0900 (月, 25 1 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class InventoryCompleteDASCH
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
    public InventoryCompleteDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder.search(createSearchSql(p));

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
        Entity[] ents = _finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        int entQty = 0;
        int inventStockQty = 0;
        int resultQty = 0;
        int stockQty = 0;


        // 棚卸テーブルの在庫数取得用のフィールド
        FieldName inventStockQtyFld = new FieldName(InventWorkInfo.STORE_NAME, "INVENT_STOCK_QTY");
        // 在庫テーブルの在庫数取得用のフィールド
        FieldName stockQtyFld = new FieldName(Stock.STORE_NAME, "STOCK_QTY");

        while ((ents[0].getBigDecimal(InventWorkInfo.RESULT_STOCK_QTY, new BigDecimal(0)).intValue() == ents[0].getBigDecimal(
                stockQtyFld, new BigDecimal(0)).intValue()))
        {
            ents = _finder.getEntities(1);
        }

        for (Entity ent : ents)
        {
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());
            p.set(AREA_NO, ent.getValue(InventWorkInfo.AREA_NO));
            p.set(AREA_NAME, ent.getValue(Area.AREA_NAME, ""));
            p.set(LOCATION_NO, ent.getValue(InventWorkInfo.LOCATION_NO));
            p.set(ITEM_CODE, ent.getValue(InventWorkInfo.ITEM_CODE));
            p.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME, ""));
            p.set(LOT_NO, ent.getValue(InventWorkInfo.LOT_NO));
            entQty = ent.getBigDecimal(Item.ENTERING_QTY, new BigDecimal(0)).intValue();
            p.set(ENTERING_QTY, entQty);
            inventStockQty = ent.getBigDecimal(inventStockQtyFld, new BigDecimal(0)).intValue();
            p.set(BEFORE_STOCK_CASE_QTY, DisplayUtil.getCaseQty(inventStockQty, entQty));
            p.set(BEFORE_STOCK_PIECE_QTY, DisplayUtil.getPieceQty(inventStockQty, entQty));
            resultQty = ent.getBigDecimal(InventWorkInfo.RESULT_STOCK_QTY, new BigDecimal(0)).intValue();
            p.set(INVENTORY_STOCK_CASE_QTY, DisplayUtil.getCaseQty(resultQty, entQty));
            p.set(INVENTORY_STOCK_PIECE_QTY, DisplayUtil.getPieceQty(resultQty, entQty));
            stockQty = ent.getBigDecimal(stockQtyFld, new BigDecimal(0)).intValue();
            p.set(AFTER_STOCK_CASE_QTY, DisplayUtil.getCaseQty(stockQty, entQty));
            p.set(AFTER_STOCK_PIECE_QTY, DisplayUtil.getPieceQty(stockQty, entQty));
            int difQty = resultQty - stockQty;
            p.set(DIFFERENCE_CASE_QTY, DisplayUtil.getCaseQty(difQty, entQty));
            p.set(DIFFERENCE_PIECE_QTY, DisplayUtil.getPieceQty(difQty, entQty));

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
        InventWorkInfoFinder finder = new InventWorkInfoFinder(this.getConnection());

        //棚卸作業情報検索キーに検索キーをセットする
        Parameter[] outParam = createSearch(p);

        //差異リストカウント数
        int cnt = 0;

        // カーソルのオープン
        finder.open(true);
        //差異リスト件数取得
        int listCount = outParam.length;

        if (listCount > 0)
        {
            InventoryOutParameter[] param = (InventoryOutParameter[])outParam;

            for (int i = 0; i < outParam.length; i++)
            {
                if (!((param[i].getCaseQty() == param[i].getResultCaseQty()) && (param[i].getPieceQty() == param[i].getResultPieceQty())))
                {
                    cnt++;
                }
            }
        }
        _total = cnt;

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
        List<Params> params = new ArrayList<Params>();

        return params;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    /**
     * 検索条件のセットを行います。<BR>
     * @param p 検索条件を含むパラメータ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     * @return String 検索SQL
     */
    private String createSearchSql(Params p)
            throws CommonException
    {

        // データ取得SQL
        StringBuffer sql = null;

        sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("DNINVENTWORKINFO.JOB_NO JOB_NO");
        sql.append(",DNINVENTWORKINFO.AREA_NO AREA_NO");
        sql.append(",DNINVENTWORKINFO.LOCATION_NO LOCATION_NO");
        sql.append(",DNINVENTWORKINFO.ITEM_CODE ITEM_CODE");
        sql.append(",DNINVENTWORKINFO.LOT_NO LOT_NO");
        sql.append(",DNINVENTWORKINFO.STOCK_QTY INVENT_STOCK_QTY");
        sql.append(",DNINVENTWORKINFO.RESULT_STOCK_QTY RESULT_STOCK_QTY");
        sql.append(",DMITEM.ITEM_NAME ITEM_NAME");
        sql.append(",DMITEM.ENTERING_QTY ENTERING_QTY");
        sql.append(",DNSTOCK.STOCK_QTY STOCK_QTY ");
        sql.append(",DMAREA.AREA_NAME AREA_NAME ");
        sql.append("FROM");
        sql.append(" DNINVENTWORKINFO");
        sql.append(",DMITEM");
        sql.append(",DNSTOCK");
        sql.append(",DMAREA ");
        sql.append("WHERE");
        sql.append(" DNINVENTWORKINFO.CONSIGNOR_CODE = ");
        sql.append(DBFormat.format(p.getString(CONSIGNOR_CODE)));
        sql.append(" AND DNINVENTWORKINFO.STATUS_FLAG = ");
        sql.append(DBFormat.format(InventWorkInfo.STATUS_FLAG_INVENTORY_WORKING_COMPLETED));


        //条件指定により、検索条件を変更する
        if (p.getString(INVENTORY).equals(InventoryInParameter.COLLECT_STATUS_LISTNO))
        {
            //リスト作業NO指定
            if (!StringUtil.isBlank(p.getString(LIST_WORK_NO)))
            {
                sql.append(" AND DNINVENTWORKINFO.SETTING_UNIT_KEY = ");
                sql.append(DBFormat.format(p.getString(LIST_WORK_NO)));
            }
        }
        else
        {
            // エリアNo.
            sql.append(" AND DNINVENTWORKINFO.AREA_NO = ");
            sql.append(DBFormat.format(p.getString(AREA)));

            String[] loc = WmsFormatter.getFromTo(p.getString(LOCATION_FROM), p.getString(LOCATION_TO));
            // 開始棚
            if (!StringUtil.isBlank(loc[0]))
            {
                sql.append(" AND DNINVENTWORKINFO.LOCATION_NO >= ");
                sql.append(DBFormat.format(loc[0]));

            }
            // 終了棚
            if (!StringUtil.isBlank(loc[1]))
            {
                sql.append(" AND DNINVENTWORKINFO.LOCATION_NO <= ");
                sql.append(DBFormat.format(loc[1]));
            }
            // 商品コード
            if (!StringUtil.isBlank(p.getString(ITEM_CODE)))
            {
                sql.append(" AND DNINVENTWORKINFO.ITEM_CODE = ");
                sql.append(DBFormat.format(p.getString(ITEM_CODE)));
            }
        }

        // 結合条件
        sql.append(" AND DNINVENTWORKINFO.ITEM_CODE = DMITEM.ITEM_CODE(+)");
        sql.append("AND DNINVENTWORKINFO.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE(+)");
        sql.append("AND DNINVENTWORKINFO.AREA_NO = DNSTOCK.AREA_NO(+)");
        sql.append("AND DNINVENTWORKINFO.LOCATION_NO = DNSTOCK.LOCATION_NO(+)");
        sql.append("AND DNINVENTWORKINFO.CONSIGNOR_CODE = DNSTOCK.CONSIGNOR_CODE(+)");
        sql.append("AND DNINVENTWORKINFO.ITEM_CODE = DNSTOCK.ITEM_CODE(+)");
        sql.append("AND DNINVENTWORKINFO.AREA_NO = DMAREA.AREA_NO(+)");
        sql.append("AND NVL(DNINVENTWORKINFO.LOT_NO,\' \') = NVL(DNSTOCK.LOT_NO(+),\' \') ");
        // ソート順
        sql.append("ORDER BY");
        sql.append(" DNINVENTWORKINFO.AREA_NO ASC");
        sql.append(",DNINVENTWORKINFO.LOCATION_NO ASC");
        sql.append(",DNINVENTWORKINFO.ITEM_CODE ASC");
        sql.append(",DNINVENTWORKINFO.LOT_NO ASC");

        return String.valueOf(sql);
    }

    /**
     * 棚卸作業情報を取得するための検索キークラスのインスタンスを生成します。<BR>
     * <BR>
     * @param p 検索条件パラメータ
     * @return 棚卸作業メンテナンスを取得するための検索キークラスのインスタンス
     * @throws ReadWriteException データベースエラーが発生した場合にスローされます。
     */
    protected Parameter[] createSearch(Params p)
            throws ReadWriteException

    {
        //インスタンス変数の_ParameterをStorageInParameterにセットする
        InventWorkInfoSearchKey key = new InventWorkInfoSearchKey();

        //荷主コード
        key.setConsignorCode(p.getString(CONSIGNOR_CODE));

        // ダイレクトDBハンドラ
        DefaultDDBHandler ddbHandler = null;

        // カウントSQL
        StringBuffer countSql = null;

        countSql = new StringBuffer();
        countSql.append("SELECT ");
        countSql.append("COUNT(*) CNT ");
        countSql.append("FROM");
        countSql.append(" DNINVENTWORKINFO");
        countSql.append(",DMITEM");
        countSql.append(",DNSTOCK");
        countSql.append(",DMAREA ");
        countSql.append("WHERE");
        countSql.append(" DNINVENTWORKINFO.CONSIGNOR_CODE = ");
        countSql.append(DBFormat.format(p.getString(CONSIGNOR_CODE)));
        countSql.append(" AND DNINVENTWORKINFO.STATUS_FLAG = ");
        countSql.append(DBFormat.format(InventWorkInfo.STATUS_FLAG_INVENTORY_WORKING_COMPLETED));

        // データ取得SQL
        StringBuffer sql = null;

        sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("DNINVENTWORKINFO.JOB_NO JOB_NO");
        sql.append(",DNINVENTWORKINFO.AREA_NO AREA_NO");
        sql.append(",DNINVENTWORKINFO.LOCATION_NO LOCATION_NO");
        sql.append(",DNINVENTWORKINFO.ITEM_CODE ITEM_CODE");
        sql.append(",DNINVENTWORKINFO.LOT_NO LOT_NO");
        sql.append(",DNINVENTWORKINFO.STOCK_QTY INVENT_STOCK_QTY");
        sql.append(",DNINVENTWORKINFO.RESULT_STOCK_QTY RESULT_STOCK_QTY");
        sql.append(",DMITEM.ITEM_NAME ITEM_NAME");
        sql.append(",DMITEM.ENTERING_QTY ENTERING_QTY");
        sql.append(",DNSTOCK.STOCK_QTY STOCK_QTY ");
        sql.append(",DMAREA.AREA_NAME AREA_NAME ");
        sql.append("FROM");
        sql.append(" DNINVENTWORKINFO");
        sql.append(",DMITEM");
        sql.append(",DNSTOCK");
        sql.append(",DMAREA ");
        sql.append("WHERE");
        sql.append(" DNINVENTWORKINFO.CONSIGNOR_CODE = ");
        sql.append(DBFormat.format(p.getString(CONSIGNOR_CODE)));
        sql.append(" AND DNINVENTWORKINFO.STATUS_FLAG = ");
        sql.append(DBFormat.format(InventWorkInfo.STATUS_FLAG_INVENTORY_WORKING_COMPLETED));


        //条件指定により、検索条件を変更する
        if (p.getString(INVENTORY).equals(InventoryInParameter.COLLECT_STATUS_LISTNO))
        {
            //リスト作業NO指定
            if (!StringUtil.isBlank(p.getString(LIST_WORK_NO)))
            {
                // リスト作業No.
                countSql.append(" AND DNINVENTWORKINFO.SETTING_UNIT_KEY = ");
                countSql.append(DBFormat.format(p.getString(LIST_WORK_NO)));
                sql.append(" AND DNINVENTWORKINFO.SETTING_UNIT_KEY = ");
                sql.append(DBFormat.format(p.getString(LIST_WORK_NO)));
            }
        }
        else
        {
            // エリアNo.
            countSql.append(" AND DNINVENTWORKINFO.AREA_NO = ");
            countSql.append(DBFormat.format(p.getString(AREA)));
            sql.append(" AND DNINVENTWORKINFO.AREA_NO = ");
            sql.append(DBFormat.format(p.getString(AREA)));
            String[] loc = WmsFormatter.getFromTo(p.getString(LOCATION_FROM), p.getString(LOCATION_TO));

            // 開始棚
            if (!StringUtil.isBlank(loc[0]))
            {

                countSql.append(" AND DNINVENTWORKINFO.LOCATION_NO >= ");
                countSql.append(DBFormat.format(loc[0]));
                sql.append(" AND DNINVENTWORKINFO.LOCATION_NO >= ");
                sql.append(DBFormat.format(loc[0]));

            }
            // 終了棚
            if (!StringUtil.isBlank(loc[1]))
            {
                countSql.append(" AND DNINVENTWORKINFO.LOCATION_NO <= ");
                countSql.append(DBFormat.format(loc[1]));
                sql.append(" AND DNINVENTWORKINFO.LOCATION_NO <= ");
                sql.append(DBFormat.format(loc[1]));
            }
            // 商品コード
            if (!StringUtil.isBlank(p.getString(ITEM_CODE)))
            {
                countSql.append(" AND DNINVENTWORKINFO.ITEM_CODE = ");
                countSql.append(DBFormat.format(p.getString(ITEM_CODE)));
                sql.append(" AND DNINVENTWORKINFO.ITEM_CODE = ");
                sql.append(DBFormat.format(p.getString(ITEM_CODE)));
            }
        }

        // 結合条件
        countSql.append(" AND DNINVENTWORKINFO.ITEM_CODE = DMITEM.ITEM_CODE(+)");
        countSql.append("AND DNINVENTWORKINFO.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE(+)");
        countSql.append("AND DNINVENTWORKINFO.AREA_NO = DNSTOCK.AREA_NO(+)");
        countSql.append("AND DNINVENTWORKINFO.LOCATION_NO = DNSTOCK.LOCATION_NO(+)");
        countSql.append("AND DNINVENTWORKINFO.CONSIGNOR_CODE = DNSTOCK.CONSIGNOR_CODE(+)");
        countSql.append("AND DNINVENTWORKINFO.ITEM_CODE = DNSTOCK.ITEM_CODE(+)");
        countSql.append("AND DNINVENTWORKINFO.AREA_NO = DMAREA.AREA_NO(+)");
        countSql.append("AND NVL(DNINVENTWORKINFO.LOT_NO,\' \') = NVL(DNSTOCK.LOT_NO(+),\' \') ");

        sql.append(" AND DNINVENTWORKINFO.ITEM_CODE = DMITEM.ITEM_CODE(+)");
        sql.append("AND DNINVENTWORKINFO.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE(+)");
        sql.append("AND DNINVENTWORKINFO.AREA_NO = DNSTOCK.AREA_NO(+)");
        sql.append("AND DNINVENTWORKINFO.LOCATION_NO = DNSTOCK.LOCATION_NO(+)");
        sql.append("AND DNINVENTWORKINFO.CONSIGNOR_CODE = DNSTOCK.CONSIGNOR_CODE(+)");
        sql.append("AND DNINVENTWORKINFO.ITEM_CODE = DNSTOCK.ITEM_CODE(+)");
        sql.append("AND DNINVENTWORKINFO.AREA_NO = DMAREA.AREA_NO(+)");
        sql.append("AND NVL(DNINVENTWORKINFO.LOT_NO,\' \') = NVL(DNSTOCK.LOT_NO(+),\' \') ");
        // ソート順
        sql.append("ORDER BY");
        sql.append(" DNINVENTWORKINFO.AREA_NO ASC");
        sql.append(",DNINVENTWORKINFO.LOCATION_NO ASC");
        sql.append(",DNINVENTWORKINFO.ITEM_CODE ASC");
        sql.append(",DNINVENTWORKINFO.LOT_NO ASC");

        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
            // カウントSQLの実行
            ddbHandler.execute(String.valueOf(countSql));
            Entity[] countEntity = ddbHandler.getEntities(1, new InventWorkInfo());

            // 棚卸テーブルの在庫数取得用のフィールド
            FieldName count = new FieldName(InventWorkInfo.STORE_NAME, "CNT");

            ddbHandler.execute(String.valueOf(sql));
            Entity[] entity = ddbHandler.getEntities(countEntity[0].getBigDecimal(count).intValue(), new InventWorkInfo());

            InventoryOutParameter[] outParam = new InventoryOutParameter[entity.length];

            // 棚卸テーブルの在庫数取得用のフィールド
            FieldName inventStockQtyFld = new FieldName(InventWorkInfo.STORE_NAME, "INVENT_STOCK_QTY");
            // 在庫テーブルの在庫数取得用のフィールド
            FieldName stockQtyFld = new FieldName(Stock.STORE_NAME, "STOCK_QTY");

            for (int i = 0; i < entity.length; i++)
            {
                outParam[i] = new InventoryOutParameter();
                outParam[i].setJobNo(String.valueOf(entity[i].getValue(InventWorkInfo.JOB_NO)));
                outParam[i].setAreaNo(String.valueOf(entity[i].getValue(InventWorkInfo.AREA_NO)));
                outParam[i].setLocation(String.valueOf(entity[i].getValue(InventWorkInfo.LOCATION_NO)));
                outParam[i].setItemCode(String.valueOf(entity[i].getValue(InventWorkInfo.ITEM_CODE)));
                outParam[i].setItemName(String.valueOf(entity[i].getValue(Item.ITEM_NAME, "")));
                outParam[i].setLotNo(String.valueOf(entity[i].getValue(InventWorkInfo.LOT_NO, "")));
                outParam[i].setAreaName(String.valueOf(entity[i].getValue(Area.AREA_NAME, "")));
                int entQty = entity[i].getBigDecimal(Item.ENTERING_QTY, new BigDecimal(0)).intValue();
                outParam[i].setEnteringQty(entQty);
                // 在庫数
                int inventStockQty = entity[i].getBigDecimal(inventStockQtyFld, new BigDecimal(0)).intValue();
                outParam[i].setStockCaseQty(DisplayUtil.getCaseQty(inventStockQty, entQty));
                outParam[i].setStockPieceQty(DisplayUtil.getPieceQty(inventStockQty, entQty));
                // 棚卸数
                int resultQty = entity[i].getBigDecimal(InventWorkInfo.RESULT_STOCK_QTY, new BigDecimal(0)).intValue();
                outParam[i].setResultCaseQty(DisplayUtil.getCaseQty(resultQty, entQty));
                outParam[i].setResultPieceQty(DisplayUtil.getPieceQty(resultQty, entQty));
                // 現在庫数
                int stockQty = entity[i].getBigDecimal(stockQtyFld, new BigDecimal(0)).intValue();
                outParam[i].setCaseQty(DisplayUtil.getCaseQty(stockQty, entQty));
                outParam[i].setPieceQty(DisplayUtil.getPieceQty(stockQty, entQty));

            }
            return outParam;
        }
        finally
        {
            if (ddbHandler != null)
            {
                ddbHandler.close();
            }
        }
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
