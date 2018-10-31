// $Id: InventoryCompleteSCH.java 6901 2010-01-25 11:22:02Z kumano $
package jp.co.daifuku.wms.inventorychk.schedule;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.inventorychk.schedule.InventoryCompleteSCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoFinder;
import jp.co.daifuku.wms.base.entity.InventWorkInfo;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.inventorychk.operator.InventoryOperator;

/**
 * 棚卸結果確定のスケジュール処理を行います。
 * 
 * @version $Revision: 6901 $, $Date: 2010-01-25 20:22:02 +0900 (月, 25 1 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class InventoryCompleteSCH
        extends AbstractSCH
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

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public InventoryCompleteSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
        // ハンドラインスタンス生成
        AbstractDBFinder finder = null;
        try
        {
            finder = new InventWorkInfoFinder(getConnection());
            finder.open(true);
            // 検索処理実行
            // 取得件数に応じてメッセージを設定

            // 該当件数を取得
            int count = countDisp(p);

            // 該当件数を取得

            canLowerDisplay(count);

            return createSearchKey(p, count);
        }
        finally
        {
            // 検索で使用したFinderをcloseする
            closeFinder(finder);
        }
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }

        // オペレータパラメータ生成
        InventoryInParameter[] inParams = new InventoryInParameter[ps.length];
        for (int i = 0; i < inParams.length; i++)
        {
            inParams[i] = new InventoryInParameter(getWmsUserInfo());

            inParams[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            inParams[i].setItemCode(ps[i].getString(ITEM_CODE));
            inParams[i].setEnteringQty(ps[i].getInt(ENTERING_QTY));

            inParams[i].setJobNo(ps[i].getString(JOB_NO));
            // エリアNo
            String areaNo = ps[i].getString(AREA_NO);
            inParams[i].setAreaNo(areaNo);
            // 棚
            inParams[i].setLocationNo(ps[i].getString(LOCATION_NO));
            // 商品コード
            inParams[i].setItemCode(ps[i].getString(ITEM_CODE));
            // 商品名称
            inParams[i].setItemName(ps[i].getString(ITEM_NAME));
            // ロットNo
            inParams[i].setLotNo(ps[i].getString(LOT_NO));
            // ケース入数
            inParams[i].setEnteringQty(ps[i].getInt(ENTERING_QTY));

            // 現在庫ケース数
            inParams[i].setStockCaseQty(ps[i].getInt(CASE_QTY));
            // 現在庫ピース数
            inParams[i].setStockPieceQty(ps[i].getInt(PIECE_QTY));
            // 現在庫数
            inParams[i].setStockQty((ps[i].getInt(CASE_QTY) * ps[i].getInt(ENTERING_QTY)) + ps[i].getInt(PIECE_QTY));

            // 棚卸ケース数
            inParams[i].setResultCaseQty(ps[i].getInt(RESULT_CASE_QTY));
            // 棚卸ピース数
            inParams[i].setResultPieceQty(ps[i].getInt(RESULT_PIECE_QTY));

            // 棚卸実績数
            inParams[i].setInventoryInsResultQty((ps[i].getInt(RESULT_CASE_QTY) * ps[i].getInt(ENTERING_QTY))
                    + ps[i].getInt(RESULT_PIECE_QTY));

            // 行No.
            inParams[i].setRowNo(ps[i].getInt(ROW_NO));
        }

        try
        {

            if (complete(inParams))
            {
                return true;
            }
            return false;

        }
        catch (LockTimeOutException e)
        {
            // 他端末で処理中のため、処理を中断しました。
            setMessage("6023114");
            return false;
        }
        catch (NotFoundException e)
        {
            // 他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return false;
        }
        catch (DataExistsException e)
        {
            // 他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return false;
        }
        catch (OperatorException e)
        {
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode()))
            {
                // 6023015=No.{0}他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, e.getErrorLineNo()));
                setErrorRowIndex(e.getErrorLineNo());
                return false;
            }
            else if (OperatorException.ERR_SHORTAGE_ALLOCATION_QTY.equals(e.getErrorCode()))
            {
                // 6023204=No.{0} 棚卸数には在庫数と引当可能数の差を超える値を入力してください。
                setMessage(WmsMessageFormatter.format(6023204, e.getErrorLineNo()));
                setErrorRowIndex(e.getErrorLineNo());
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
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
     * 棚卸作業情報から表示件数を取得するための検索キークラスのインスタンスを生成します。<BR>
     * <BR>
     * @param inparam 入力パラメータ
     * @return 棚卸作業メンテナンスを取得するための検索キークラスのインスタンス
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected int countDisp(ScheduleParams inparam)
            throws CommonException

    {

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
        countSql.append(",DNSTOCK ");
        countSql.append("WHERE");
        countSql.append(" DNINVENTWORKINFO.CONSIGNOR_CODE = ");
        countSql.append(DBFormat.format(inparam.getString(CONSIGNOR_CODE)));
        countSql.append(" AND DNINVENTWORKINFO.STATUS_FLAG = ");
        countSql.append(DBFormat.format(InventWorkInfo.STATUS_FLAG_INVENTORY_WORKING_COMPLETED));

        //条件指定により、検索条件を変更する
        if (inparam.get(SEARCH_CRITERIA).equals(InventoryInParameter.COLLECT_STATUS_LISTNO))
        {
            //リスト作業NO指定
            if (!StringUtil.isBlank(inparam.getString(LIST_WORK_NO)))
            {
                // リスト作業No.
                countSql.append(" AND DNINVENTWORKINFO.SETTING_UNIT_KEY = ");
                countSql.append(DBFormat.format(inparam.getString(LIST_WORK_NO)));
            }
        }
        else
        {
            // エリアNo.
            countSql.append(" AND DNINVENTWORKINFO.AREA_NO = ");
            countSql.append(DBFormat.format(inparam.getString(AREA_NO)));

            String[] loc = WmsFormatter.getFromTo(inparam.getString(LOCATION_FROM), inparam.getString(LOCATION_TO));
            // 開始棚
            if (!StringUtil.isBlank(loc[0]))
            {

                countSql.append(" AND DNINVENTWORKINFO.LOCATION_NO >= ");
                countSql.append(DBFormat.format(loc[0]));

            }
            // 終了棚
            if (!StringUtil.isBlank(loc[1]))
            {

                countSql.append(" AND DNINVENTWORKINFO.LOCATION_NO <= ");
                countSql.append(DBFormat.format(loc[1]));

            }
            // 商品コード
            if (!StringUtil.isBlank(inparam.getString(ITEM_CODE)))
            {
                countSql.append(" AND DNINVENTWORKINFO.ITEM_CODE = ");
                countSql.append(DBFormat.format(inparam.getString(ITEM_CODE)));
            }


        }

        // 結合条件
        countSql.append(" AND DNINVENTWORKINFO.ITEM_CODE = DMITEM.ITEM_CODE(+)");
        countSql.append("AND DNINVENTWORKINFO.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE(+)");
        countSql.append("AND DNINVENTWORKINFO.AREA_NO = DNSTOCK.AREA_NO(+)");
        countSql.append("AND DNINVENTWORKINFO.LOCATION_NO = DNSTOCK.LOCATION_NO(+)");
        countSql.append("AND DNINVENTWORKINFO.CONSIGNOR_CODE = DNSTOCK.CONSIGNOR_CODE(+)");
        countSql.append("AND DNINVENTWORKINFO.ITEM_CODE = DNSTOCK.ITEM_CODE(+)");
        countSql.append("AND NVL(DNINVENTWORKINFO.LOT_NO,\' \') = NVL(DNSTOCK.LOT_NO(+),\' \') ");

        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
            // カウントSQLの実行
            ddbHandler.execute(String.valueOf(countSql));
            Entity[] countEntity = ddbHandler.getEntities(1, new InventWorkInfo());

            // 棚卸テーブルの在庫数取得用のフィールド
            FieldName count = new FieldName(InventWorkInfo.STORE_NAME, "CNT");

            return countEntity[0].getBigDecimal(count).intValue();
        }
        finally
        {
            if (ddbHandler != null)
            {
                ddbHandler.close();
            }
        }
    }


    /**
     * 棚卸作業情報を取得するための検索キークラスのインスタンスを生成します。<BR>
     * <BR>
     * @param inparam 入力パラメータ
     * @param cnt 該当データ件数
     * @return 棚卸作業メンテナンスを取得するための検索キークラスのインスタンス
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected List<Params> createSearchKey(ScheduleParams inparam, int cnt)
            throws CommonException

    {
        // ダイレクトDBハンドラ
        DefaultDDBHandler ddbHandler = null;

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
        sql.append("FROM");
        sql.append(" DNINVENTWORKINFO");
        sql.append(",DMITEM");
        sql.append(",DNSTOCK ");
        sql.append("WHERE");
        sql.append(" DNINVENTWORKINFO.CONSIGNOR_CODE = ");
        sql.append(DBFormat.format(inparam.getString(CONSIGNOR_CODE)));
        sql.append(" AND DNINVENTWORKINFO.STATUS_FLAG = ");
        sql.append(DBFormat.format(InventWorkInfo.STATUS_FLAG_INVENTORY_WORKING_COMPLETED));


        //条件指定により、検索条件を変更する
        if (inparam.get(SEARCH_CRITERIA).equals(InventoryInParameter.COLLECT_STATUS_LISTNO))
        {
            //リスト作業NO指定
            if (!StringUtil.isBlank(inparam.getString(LIST_WORK_NO)))
            {
                // リスト作業No.
                sql.append(" AND DNINVENTWORKINFO.SETTING_UNIT_KEY = ");
                sql.append(DBFormat.format(inparam.getString(LIST_WORK_NO)));
            }
        }
        else
        {
            // エリアNo.
            sql.append(" AND DNINVENTWORKINFO.AREA_NO = ");
            sql.append(DBFormat.format(inparam.getString(AREA_NO)));

            String[] loc = WmsFormatter.getFromTo(inparam.getString(LOCATION_FROM), inparam.getString(LOCATION_TO));

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
            if (!StringUtil.isBlank(inparam.getString(ITEM_CODE)))
            {
                sql.append(" AND DNINVENTWORKINFO.ITEM_CODE = ");
                sql.append(DBFormat.format(inparam.getString(ITEM_CODE)));
            }
        }

        sql.append(" AND DNINVENTWORKINFO.ITEM_CODE = DMITEM.ITEM_CODE(+)");
        sql.append("AND DNINVENTWORKINFO.CONSIGNOR_CODE = DMITEM.CONSIGNOR_CODE(+)");
        sql.append("AND DNINVENTWORKINFO.AREA_NO = DNSTOCK.AREA_NO(+)");
        sql.append("AND DNINVENTWORKINFO.LOCATION_NO = DNSTOCK.LOCATION_NO(+)");
        sql.append("AND DNINVENTWORKINFO.CONSIGNOR_CODE = DNSTOCK.CONSIGNOR_CODE(+)");
        sql.append("AND DNINVENTWORKINFO.ITEM_CODE = DNSTOCK.ITEM_CODE(+)");
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
            ddbHandler.execute(String.valueOf(sql));

            // 該当件数がリストセル最大表示件数を超えた場合
            int count = cnt;
            if (count > WmsParam.MAX_NUMBER_OF_DISP)
            {
                count = WmsParam.MAX_NUMBER_OF_DISP;
            }

            Entity[] entity = ddbHandler.getEntities(count, new InventWorkInfo());
            // 棚卸テーブルの在庫数取得用のフィールド
            FieldName inventStockQtyFld = new FieldName(InventWorkInfo.STORE_NAME, "INVENT_STOCK_QTY");
            // 在庫テーブルの在庫数取得用のフィールド
            FieldName stockQtyFld = new FieldName(Stock.STORE_NAME, "STOCK_QTY");

            List<Params> result = new ArrayList<Params>();

            for (int i = 0; i < count; i++)
            {
                Params param = new Params();

                param.set(JOB_NO, String.valueOf(entity[i].getValue(InventWorkInfo.JOB_NO)));
                param.set(AREA_NO, String.valueOf(entity[i].getValue(InventWorkInfo.AREA_NO)));
                param.set(LOCATION_NO, String.valueOf(entity[i].getValue(InventWorkInfo.LOCATION_NO)));
                param.set(ITEM_CODE, String.valueOf(entity[i].getValue(InventWorkInfo.ITEM_CODE, "")));
                param.set(ITEM_NAME, String.valueOf(entity[i].getValue(Item.ITEM_NAME, "")));
                param.set(LOT_NO, String.valueOf(entity[i].getValue(InventWorkInfo.LOT_NO, "")));
                int entQty = entity[i].getBigDecimal(Item.ENTERING_QTY, new BigDecimal(0)).intValue();
                param.set(ENTERING_QTY, entQty);
                // 在庫数
                int inventStockQty = entity[i].getBigDecimal(inventStockQtyFld, new BigDecimal(0)).intValue();
                param.set(STOCK_CASE_QTY, DisplayUtil.getCaseQty(inventStockQty, entQty));
                param.set(STOCK_PIECE_QTY, DisplayUtil.getPieceQty(inventStockQty, entQty));
                // 棚卸数
                int resultQty = entity[i].getBigDecimal(InventWorkInfo.RESULT_STOCK_QTY, new BigDecimal(0)).intValue();
                param.set(RESULT_CASE_QTY, DisplayUtil.getCaseQty(resultQty, entQty));
                param.set(RESULT_PIECE_QTY, DisplayUtil.getPieceQty(resultQty, entQty));
                // 現在庫数
                int stockQty = entity[i].getBigDecimal(stockQtyFld, new BigDecimal(0)).intValue();
                param.set(CASE_QTY, DisplayUtil.getCaseQty(stockQty, entQty));
                param.set(PIECE_QTY, DisplayUtil.getPieceQty(stockQty, entQty));

                result.add(param);
            }

            return result;
        }
        finally
        {
            if (ddbHandler != null)
            {
                ddbHandler.close();
            }
        }

    }


    /**
     * 棚卸結果確定処理を行います。
     * @param startparam 検索条件パラメータ
     * @return 処理正常の場合true、それ以外の場合falseを返す
     * @throws CommonException 何らかの例外が発生した場合に通知されます。
     */
    protected boolean complete(Parameter[] startparam)
            throws CommonException
    {
        InventoryInParameter[] param = (InventoryInParameter[])startparam;
        InventoryOperator operator = new InventoryOperator(this.getConnection(), this.getClass());

        if (param == null)
        {
            // 6003001=データを選択してください。
            setMessage("6003001");
            return false;
        }


        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }

        //棚卸結果確定処理
        operator.complete(param);

        // 6001015=確定しました。
        setMessage("6001015");
        return true;
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
