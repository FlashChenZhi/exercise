// $Id: HistoryMaker.java 4812 2009-08-10 11:05:22Z kumano $
package jp.co.daifuku.wms.analysis.operator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.analysis.dbhandler.InventoryHistFileHandler;
import jp.co.daifuku.wms.analysis.dbhandler.ShippingHistFileHandler;
import jp.co.daifuku.wms.analysis.entity.HistoryEntity;
import jp.co.daifuku.wms.analysis.entity.InventoryHistoryEntity;
import jp.co.daifuku.wms.analysis.entity.ShippingHistoryEntity;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.HostSendFinder;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHistoryFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHistorySearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingUnitAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingUnitHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingUnitSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.StockHistory;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.AbstractDBEntity;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;


/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 履歴テーブル生成処理。<br>
 * 日締め処理時にコールされ、在庫情報、実績情報から、当日在庫数、入出庫数、
 * 入出荷数などを在庫履歴テーブル、出荷履歴テーブルへ集計します。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2006/06/28</td><td nowrap>清水 正人</td><td>Class created.</td></tr>
 * <tr><td nowrap>2008/01/20</td><td nowrap>Shimizu(Softecs)</td><td>v3.2移行</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 4812 $, $Date: 2009-08-10 20:05:22 +0900 (月, 10 8 2009) $
 * @author  清水 正人
 * @author  Last commit: $Author: kumano $
 */


public class HistoryMaker
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    // 集計タイプ（0:在庫履歴集計 1:出荷履歴集計）
    private static final int TYPE_INVENTORY = 0;

    // 集計データ格納位置
    private static final int ARG_STORAGE = 0; // 入庫数

    private static final int ARG_INSTOCK = 1; // 入荷数

    private static final int ARG_RETRIEVAL = 2; // 出庫数

    private static final int ARG_SHIPPING = 3; // 出荷数

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    private AbstractDBFinder _resultFinder = null;

    private InventoryHistFileHandler _inventoryHandler = null;

    private ShippingHistFileHandler _shippingHandler = null;

    /**
     * 集約キークラス。<br>
     * 内部に荷主コード、出荷先コード、商品コード、ロットNo.、作業日を保持し
     * メソッドequalsを備えます。
     */
    private class AggregateKey
    {
        //--------------------------------------------------------
        // properties (prefix 'p_')
        //--------------------------------------------------------
        // 荷主コード
        private String p_consignor = null;

        // 出荷先コード
        private String p_customer = null;

        //  商品コード
        private String p_itemcode = null;

        // ロットNo.
        private String p_lotno = null;

        // 作業日
        private String p_workdate = null;

        /**
         * デフォルトコンストラクタ。<br>
         * 必ず、荷主コード、商品コード、ロットNo.、作業日を
         * 設定して下さい。<br>
         */
        public AggregateKey()
        {
        }

        /**
         * 荷主コード、出荷先コード、商品コード、ロットNo.、作業日を
         * 指定して集約キークラスをインスタンス化します。
         * @param consignor 荷主コード
         * @param customer 出荷先コード
         * @param itemcode 商品コード
         * @param lotno ロットNo.
         * @param workdate 作業日
         */
        public AggregateKey(String consignor, String customer, String itemcode, String lotno, String workdate)
        {
            p_consignor = consignor; // 荷主コード
            p_customer = customer; // 出荷先コード
            p_itemcode = itemcode; // 商品コード
            p_lotno = lotno; // ロットNo.
            p_workdate = workdate; // 作業日
        }

        /**
         * 荷主コードを設定します。
         * @param consignor 荷主コード
         */
        public void setConsignor(String consignor)
        {
            p_consignor = consignor;
        }

        /**
         * 内部で保持している荷主コードを返します。
         * @return 荷主コード
         */
        public String getConsignor()
        {
            return p_consignor;
        }

        /**
         * 出荷先コードを設定します。
         * @param customer 出荷先コード
         */
        public void setCustomer(String customer)
        {
            p_customer = customer;
        }

        /**
         * 内部で保持している出荷先コードを返します。
         * @return 出荷先コード
         */
        public String getCustomer()
        {
            return p_customer;
        }

        /**
         * 商品コードを設定します。
         * @param itemcode 商品コード
         */
        public void setItemCode(String itemcode)
        {
            p_itemcode = itemcode;
        }

        /**
         * 内部で保持している商品コードを返します。
         * @return 商品コード
         */
        public String getItemCode()
        {
            return p_itemcode;
        }

        /**
         * ロットNo.を設定します。
         * @param lotno ロットNo.
         */
        public void setLotNo(String lotno)
        {
            p_lotno = lotno;
        }

        /**
         * 内部で保持しているロットNo.を返します。
         * @return ロットNo.
         */
        public String getLotNo()
        {
            return p_lotno;
        }

        /**
         * 作業日を設定します。
         * @param workdate 作業日
         */
        public void setWorkDate(String workdate)
        {
            p_workdate = workdate;
        }

        /**
         * 内部で保持している作業日を返します。
         * @return 作業日
         */
        public String getWorkDate()
        {
            return p_workdate;
        }

        /**
         * 荷主コード、出荷先コード、商品コード、ロットNo.、作業日を
         * 指定して集約キークラスに保持している値と比較します。
         * @param type 集計タイプ
         * @param consignor 荷主コード
         * @param customer 出荷先コード
         * @param itemcode 商品コード
         * @param lotno ロットNo.
         * @param workdate 作業日
         * @return true 等しい場合<br>
         * false 等しくない場合
         */
        public boolean equals(int type, String consignor, String customer, String itemcode, String lotno,
                String workdate)
        {
            // 在庫履歴集計
            if (TYPE_INVENTORY == type)
            {
                // 荷主コード
                if (!p_consignor.equals(consignor))
                {
                    return false;
                }
                // 商品コード
                if (!p_itemcode.equals(itemcode))
                {
                    return false;
                }
                // ロットNo.
                if (!p_lotno.equals(lotno))
                {
                    return false;
                }
                // 作業日
                if (!p_workdate.equals(workdate))
                {
                    return false;
                }
            }
            // 出荷履歴集計
            else
            {
                // 荷主コード
                if (!p_consignor.equals(consignor))
                {
                    return false;
                }
                // 出荷先コード
                if (!p_customer.equals(customer))
                {
                    return false;
                }
                // 商品コード
                if (!p_itemcode.equals(itemcode))
                {
                    return false;
                }
                // 作業日
                if (!p_workdate.equals(workdate))
                {
                    return false;
                }
            }
            return true;
        }

        /**
         * パラメータで指定されたより集約キークラスと、本クラスが
         * 保持している値と比較します。
         * @param type 集計タイプ
         * @param data 対象データ 
         * @return true 等しい場合<br>
         * false 等しくない場合
         */
        public boolean equals(int type, AggregateKey data)
        {
            return equals(type, data.getConsignor(), data.getCustomer(), data.getItemCode(), data.getLotNo(),
                    data.getWorkDate());
        }
    }

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 日締め処理時にコールされ、在庫情報、実績情報から、当日在庫数、入出庫数、
     * 入出荷数などを商品管理テーブル、在庫履歴テーブル、出荷履歴テーブルへ
     * 集計します。
     * @param conn データベースとのコネクションオブジェクト
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException スケジュール処理に失敗した場合に通知されます。
     */
    public void make(Connection conn)
            throws ReadWriteException,
                ScheduleException
    {
        // 作業日を取得します。
        String workDate = getWorkDate(conn);

        // 最終履歴データ生成日を取得します。
        Date lastDate = getHistoryMakedDate();

        // 履歴情報の集計を行います。
        summary(conn, lastDate, workDate);

        // 履歴情報の削除を行います。
        purge(conn, lastDate, workDate);

        // 最終履歴データ生成日を更新します。（更新前の作業日を設定）
        lastDate = getFinishDate(conn);
        if (!saveHistoryMakedDate(lastDate))
        {
            String message = WmsMessageFormatter.format(6006020, WmsParam.ANALYSIS_INI_PATH);
            RmiMsgLogClient.write(message, getClass().getName());
            throw new ScheduleException();
        }
    }

    /**
     * 在庫情報、実績送信情報から、当日在庫数、入出庫数、入出荷数など
     * を商品管理テーブル、履歴テーブルへ集計します。
     * @param conn データベースとのコネクションオブジェクト
     * @param lastDate 最終履歴生成日時
     * @param workDate 作業日
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException スケジュール処理に失敗した場合に通知されます。
     */
    public void summary(Connection conn, Date lastDate, String workDate)
            throws ReadWriteException,
                ScheduleException
    {
        // 在庫履歴と出荷履歴の集計を行います。
        for (int type = 0; type < 2; type++)
        {
            // 検索条件を設定します。
            if (null != _resultFinder)
            {
                _resultFinder.close();
            }
            // 実績処理件数カウンター
            int count = 0;
            // 在庫履歴
            if (TYPE_INVENTORY == type)
            {
                prepareInventoryFind(conn, lastDate);
            }
            // 出荷履歴
            else
            {
                prepareShippingFind(conn, lastDate);
            }

            // 検索した実績送信情報を１件づつ取り出して集計します。
            AggregateKey prevKey = new AggregateKey(); // 前回読み込んだ実績のキー情報
            AggregateKey currKey = null; // 今回読み込んだ実績のキー情報

            // 集計項目を初期化します。
            int[] resultQty = new int[4]; // 実績数集計用
            resultQty[ARG_INSTOCK] = 0; // 入荷数
            resultQty[ARG_STORAGE] = 0; // 入庫数
            resultQty[ARG_SHIPPING] = 0; // 出荷数
            resultQty[ARG_RETRIEVAL] = 0; // 出庫数

            while (_resultFinder.hasNext())
            {
                // 検索した実績情報を取り出します。
                AbstractDBEntity[] result = (AbstractDBEntity[])_resultFinder.getEntities(100);
                for (int i = 0; i < result.length; i++)
                {
                    currKey = null;
                    // 取得した実績情報より集約キーを生成します。
                    // 複数日分の実績を集約して扱うため、集計日はシステムの作業日とします。
                    // 在庫履歴
                    if (TYPE_INVENTORY == type)
                    {
                        StockHistory entity = (StockHistory)result[i];
                        currKey =
                                new AggregateKey(entity.getConsignorCode(), null, entity.getItemCode(),
                                        entity.getLotNo(), workDate);
                    }
                    // 出荷履歴
                    else
                    {
                        /* 仕様未確定のためコメント化しておきます。
                         if (WmsParam.SHIPPING_RESULT_MODE == AnalysisInParameter.SHIPPING_RESULT_MODE_1)
                         {
                         ShipHostSend entity = (ShipHostSend)result[i];
                         currKey =
                         new AggregateKey(entity.getConsignorCode(), entity.getCustomerCode(),
                         entity.getItemCode(), null, workDate);
                         }
                         else
                         {
                         HostSend entity = (HostSend)result[i];
                         currKey =
                         new AggregateKey(entity.getConsignorCode(), entity.getCustomerCode(),
                         entity.getItemCode(), null, workDate);
                         }
                         */
                        HostSend entity = (HostSend)result[i];
                        currKey =
                                new AggregateKey(entity.getConsignorCode(), entity.getCustomerCode(),
                                        entity.getItemCode(), null, workDate);
                    }

                    // 初回のみ前回キー情報へコピーします。
                    if (count == 0)
                    {
                        prevKey = currKey;
                    }
                    count++;

                    // 集約のブレーク条件をチェックします。
                    if (!prevKey.equals(type, currKey))
                    {
                        // 履歴情報をテーブルに書き込みます。
                        addHistory(conn, prevKey, resultQty, lastDate, workDate, type);

                        // ロットが違う同一商品も履歴生成するため、作業日か荷主コードか商品コードが変化したら
                        // 在庫数をまとめてセットします。
                        if (TYPE_INVENTORY == type)
                        {
                            if (!prevKey.getConsignor().equals(currKey.getConsignor())
                                    || !prevKey.getItemCode().equals(currKey.getItemCode())
                                    || !prevKey.getWorkDate().equals(currKey.getWorkDate()))
                            {
                                setStockQty(conn, prevKey, workDate);
                            }
                        }

                        // 集計項目を初期化します。
                        resultQty[ARG_INSTOCK] = 0;
                        resultQty[ARG_STORAGE] = 0;
                        resultQty[ARG_SHIPPING] = 0;
                        resultQty[ARG_RETRIEVAL] = 0;

                        // 前回情報へコピーします。
                        prevKey = currKey;
                    }

                    // 集計を行います。
                    if (TYPE_INVENTORY == type)
                    {
                        StockHistory entity = (StockHistory)result[i];
                        countData(type, entity.getIncDecType(), entity.getIncDecQty(), resultQty);
                    }
                    // 出荷履歴
                    else
                    {
                        /* 仕様未確定のためコメント化しておきます。
                         if (WmsParam.SHIPPING_RESULT_MODE == AnalysisInParameter.SHIPPING_RESULT_MODE_1)
                         {
                         ShipHostSend entity = (ShipHostSend)result[i];
                         countData(type, entity.getJobType(), entity.getResultQty(), resultQty);
                         }
                         else
                         {
                         HostSend entity = (HostSend)result[i];
                         countData(type, entity.getJobType(), entity.getResultQty(), resultQty);
                         }
                         */
                        HostSend entity = (HostSend)result[i];
                        countData(type, entity.getJobType(), entity.getResultQty(), resultQty);
                    }
                }

                // 最終データ時の処理
                if (!_resultFinder.hasNext())
                {
                    // 最終データを履歴情報をテーブルに書き込みます。
                    addHistory(conn, currKey, resultQty, lastDate, workDate, type);

                    // ロットが違う同一商品も履歴生成します。
                    if (TYPE_INVENTORY == type)
                    {
                        setStockQty(conn, currKey, workDate);
                    }

                    // バッチ終了処理を実施します。
                    finalizeBatch(type);
                }
            }
        }
    }

    /**
     * 一定期間が経過した履歴情報を商品管理テーブル、履歴テーブルから削除します。
     * @param conn データベースとのコネクションオブジェクト
     * @param lastDate 最終履歴生成日時
     * @param workDate 作業日
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public void purge(Connection conn, Date lastDate, String workDate)
            throws ReadWriteException
    {
        // 作業単位数マスタを削除します。
        try
        {
            // 履歴情報と同じ条件で作業単位数マスタを削除します。
            //String delDay = AbstractHistoryHandler.getDeleteDate(workDate);
            String delDay = getTerm(WmsFormatter.toDate(workDate));

            // 削除対象の情報を取得する条件設定を行います。
            WorkingUnitSearchKey searchKey = new WorkingUnitSearchKey();
            searchKey.setLastUseWorkDay(delDay, "<");

            // 取得した一定期間を経過した作業単位数マスタを削除します。
            WorkingUnitHandler handler = new WorkingUnitHandler(conn);
            if (handler.count(searchKey) > 0)
            {
                handler.drop(searchKey);
            }
        }
        catch (NotFoundException e)
        {
            // 削除ではNotFoundExceptionを無視します。
        }

        // 在庫履歴情報を削除します。
        InventoryHistFileHandler inventoryHandler = new InventoryHistFileHandler(conn);
        inventoryHandler.delete(workDate);

        // 出荷履歴情報を削除します。
        ShippingHistFileHandler shippingHandler = new ShippingHistFileHandler(conn);
        shippingHandler.delete(workDate);
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 在庫更新履歴情報を読み込むための検索条件を設定します。
     * @param conn データベースコネクション
     * @param lastDate 最終履歴データ生成日
     * @return 検索結果件数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    private int prepareInventoryFind(Connection conn, Date lastDate)
            throws ReadWriteException
    {
        // 検索条件を設定します。
        StockHistorySearchKey searchKey = new StockHistorySearchKey();

        // 登録日時
        searchKey.setRegistDate(lastDate, ">");

        // 分析区分
        searchKey.setAnalysisType(StockHistory.ANALYSIS_TYPE_ANALYSIS);

        // 荷主コード
        // 1番目の読み込みで、グルーピングに加えます。
        searchKey.setConsignorCodeCollect();
        searchKey.setConsignorCodeGroup();
        searchKey.setConsignorCodeOrder(true);

        // 商品コード
        // 2番目の読み込みで、グルーピングに加えます。
        searchKey.setItemCodeCollect();
        searchKey.setItemCodeGroup();
        searchKey.setItemCodeOrder(true);

        // ロットNo.
        // 3番目の読み込みで、グルーピングに加えます。
        searchKey.setLotNoCollect();
        searchKey.setLotNoGroup();
        searchKey.setLotNoOrder(true);

        // 増減区分
        // 4番目の読み込みで、グルーピングに加えます。
        searchKey.setIncDecTypeCollect();
        searchKey.setIncDecTypeGroup();
        searchKey.setIncDecTypeOrder(true);

        // 商品作業実績数
        searchKey.setIncDecQty(0, ">");
        searchKey.setIncDecQtyCollect("SUM");

        // 実績情報のファインダーを準備します。
        _resultFinder = new StockHistoryFinder(conn);
        _resultFinder.open(true);

        return _resultFinder.search(searchKey);
    }

    /**
     * 出荷実績送信情報を読み込むための検索条件を設定します。
     * @param conn データベースコネクション
     * @param lastDate 最終履歴データ生成日
     * @return 検索結果件数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    private int prepareShippingFind(Connection conn, Date lastDate)
            throws ReadWriteException
    {
        // 分析対象作業実績が出庫の時は入出庫実績送信情報より対象データを集計します。
        return prepareHostSendFind(conn, lastDate);
    }

    /**
     * 入出庫実績送信情報を読み込むための検索条件を設定します。
     * @param conn データベースコネクション
     * @param lastDate 最終履歴データ生成日
     * @return 検索結果件数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    private int prepareHostSendFind(Connection conn, Date lastDate)
            throws ReadWriteException
    {
        // 検索条件を設定します。
        HostSendSearchKey searchKey = new HostSendSearchKey();

        // 最終使用日時
        searchKey.setLastUpdateDate(lastDate, ">");

        // 作業区分：出庫
        searchKey.setJobType(HostSend.JOB_TYPE_RETRIEVAL);

        // 荷主コード
        // 1番目の読み込みで、グルーピングに加えます。
        searchKey.setConsignorCodeCollect();
        searchKey.setConsignorCodeGroup();
        searchKey.setConsignorCodeOrder(true);

        // 出荷先コード
        // 2番目の読み込みで、グルーピングに加えます。
        searchKey.setCustomerCodeCollect();
        searchKey.setCustomerCodeGroup();
        searchKey.setCustomerCodeOrder(true);

        // 商品コード
        // 3番目の読み込みで、グルーピングに加えます。
        searchKey.setItemCodeCollect();
        searchKey.setItemCodeGroup();
        searchKey.setItemCodeOrder(true);

        // 商品作業実績数
        searchKey.setResultQty(0, ">");
        searchKey.setResultQtyCollect("SUM");

        // 実績送信情報のファインダーを準備します。
        _resultFinder = new HostSendFinder(conn);
        _resultFinder.open(true);

        return _resultFinder.search(searchKey);
    }

    /**
     * 作業実績数の集計を行います。
     * @param type 集計タイプ
     * @param jobType 作業区分
     * @param result 作業実績数
     * @param data 集計数
     */
    private void countData(int type, String jobType, int result, int[] data)
    {
        // 在庫作業実績の集計
        if (TYPE_INVENTORY == type)
        {
            // 入庫
            if (StockHistory.INC_DEC_TYPE_STOCK_INCREMENT.equals(jobType))
            {
                data[ARG_STORAGE] += result; // 入庫数
            }

            // 出庫
            else
            {
                data[ARG_RETRIEVAL] += result; // 出庫数
            }
        }

        // 出荷作業実績の集計
        else
        {
            data[ARG_SHIPPING] += result; // 出荷数
        }
    }

    /**
     * パラメータで指定された集約キークラスと、集約データより、履歴情報を編集し、
     * データベースに書き込みます。<br>
     * ただし、集約データが全てゼロであれば、履歴情報をデータベースに書き込みません。
     * @param conn データベースコネクション
     * @param key 集約キー
     * @param data 集約データ
     * @param lastDate 最終履歴生成日時
     * @param workDate 作業日
     * @param type 集計タイプ 0:在庫履歴集計 1:出荷履歴集計
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException スケジュール処理に失敗した場合に通知されます。
     */
    private void addHistory(Connection conn, AggregateKey key, int[] data, Date lastDate, String workDate, int type)
            throws ReadWriteException,
                ScheduleException
    {
        // 集計タイプによって、履歴情報追加処理を振り分けます。
        if (TYPE_INVENTORY == type)
        {
            // 集約データが全てゼロなら履歴情報をデータベースに書き込みません。
            if (data[ARG_STORAGE] == 0 // 当日入庫数
                    && data[ARG_INSTOCK] == 0 // 当日入荷数
                    && data[ARG_RETRIEVAL] == 0 // 当日出庫数
                    && data[ARG_SHIPPING] == 0) // 当日出荷数
            {
                return;
            }

            // 在庫履歴情報を追加します。
            addInventoryHist(conn, key, data, workDate);
        }
        else
        {
            // 当日出荷数がゼロなら履歴情報をデータベースに書き込みません。
            if (data[ARG_SHIPPING] == 0) // 当日出荷数
            {
                return;
            }

            // 出荷履歴情報を追加します。
            addShippingHist(conn, key, data, lastDate, workDate);
        }
    }

    /**
     * パラメータで指定された集約キークラスと、集約データより、在庫履歴情報を編集し、
     * データベースに書き込みます。
     * @param conn データベースコネクション
     * @param key 集約キー
     * @param data 集約データ
     * @param workDate 作業日
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException スケジュール処理に失敗した場合に通知されます。
     */
    private void addInventoryHist(Connection conn, AggregateKey key, int[] data, String workDate)
            throws ReadWriteException,
                ScheduleException
    {
        // 履歴情報を編集します。
        InventoryHistoryEntity history = new InventoryHistoryEntity();
        // 荷主コード
        history.setConsignorCode(key.getConsignor());
        // 商品コード
        history.setItemCode(key.getItemCode());
        // ロットNO
        history.setLotNo(key.getLotNo());
        // 集計日
        history.setSummaryDate(workDate);
        // 当日在庫数（現在庫数から既に在庫履歴へ登録した在庫数を減算した値をセットします。）
        int stockQty = getStockQty(conn, history);
        int stockQtyHist = getStockQtyFromInventoryHist(conn, history);
        stockQty = stockQty - stockQtyHist;
        history.setStockQty(stockQty);
        // 当日入庫数
        history.setStorageQty(data[ARG_STORAGE]);
        // 当日入荷数(v3.2で削除されました)
        //history.setInstockQty(data[ARG_INSTOCK]);
        // 当日出庫数
        history.setRetrievalQty(data[ARG_RETRIEVAL]);
        // 当日出荷数(v3.2で削除されました)
        //history.setShippingQty(data[ARG_SHIPPING]);

        // 在庫履歴情報をテーブルに書き込みます。
        if (null == _inventoryHandler)
        {
            _inventoryHandler = new InventoryHistFileHandler(conn);
        }

        // ロット違いの同一商品のレコードも落とすことになったのでバッチ処理が使えなくなりました。
        //_inventoryHandler.addBatch(history) ;
        _inventoryHandler.write(history);

        // 作業単位数マスタの更新を行います。
        updateWorkingUnit(conn, history);
    }

    /**
     * 在庫履歴テーブル更新（在庫数のみ）
     * パラメータで指定された集約キークラスと、在庫数データより、在庫履歴情報を編集し、
     * データベースに書き込みます。
     * @param conn データベースコネクション
     * @param stock 在庫データ
     * @param data 在庫数データ
     * @param workDate 作業日(YYYYMMDD)
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    private void addStockQtyToInventoryHist(Connection conn, Stock stock, int data, String workDate)
            throws ReadWriteException
    {
        // 在庫履歴更新キー
        InventoryHistSearchParameter ihsParam = new InventoryHistSearchParameter();
        // 荷主コード
        ihsParam.setConsignorCode(stock.getConsignorCode());
        // 商品コード
        ihsParam.setItemCode(stock.getItemCode());
        // ロットNO
        if (!StringUtil.isBlank(stock.getLotNo()))
        {
            ihsParam.setLotNo(stock.getLotNo());
        }
        // 集計日
        ihsParam.setSummaryType(InventoryHistSearchParameter.SUMMARY_TYPE_DAY);
        ihsParam.setStartDate(workDate);
        ihsParam.setEndDate(workDate);

        // 在庫履歴情報ハンドラ
        if (null == _inventoryHandler)
        {
            _inventoryHandler = new InventoryHistFileHandler(conn);
        }

        // 該当する在庫履歴が存在すればスキップし、無ければ新規作成します。
        HistoryEntity[] history = _inventoryHandler.search(ihsParam);
        if (null != history && 0 < history.length)
        {
            // 何もしません
        }
        else
        {
            // 該当データなし．．．新規レコード追加
            InventoryHistoryEntity entity = new InventoryHistoryEntity();
            // 荷主コード
            entity.setConsignorCode(stock.getConsignorCode());
            // 商品コード
            entity.setItemCode(stock.getItemCode());
            // ロットNO
            entity.setLotNo(stock.getLotNo());
            // 集計日
            entity.setSummaryDate(workDate);
            // 当日在庫数
            entity.setStockQty(data);
            // その他数量項目はゼロ固定
            entity.setStorageQty(0);
            //entity.setInstockQty(0);
            entity.setRetrievalQty(0);
            //entity.setShippingQty(0);
            // 追加処理
            _inventoryHandler.write(entity);
        }
    }

    /**
     * 在庫数取得（在庫履歴テーブルより）
     * パラメータで指定された集約キークラスにて在庫履歴テーブルの在庫数を取得する。
     * @param conn データベースコネクション
     * @param history 在庫履歴データ
     * @return 在庫数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    private int getStockQtyFromInventoryHist(Connection conn, InventoryHistoryEntity history)
            throws ReadWriteException
    {
        int stockQtySum = 0; // 在庫数の合計

        // 在庫履歴更新キー
        InventoryHistSearchParameter ihsParam = new InventoryHistSearchParameter();
        // 荷主コード
        ihsParam.setConsignorCode(history.getConsignorCode());
        // 商品コード
        ihsParam.setItemCode(history.getItemCode());
        // ロットNO
        if (!StringUtil.isBlank(history.getLotNo()))
        {
            ihsParam.setLotNo(history.getLotNo());
        }
        // 集計日
        ihsParam.setSummaryType(InventoryHistSearchParameter.SUMMARY_TYPE_DAY);
        ihsParam.setStartDate(history.getSummaryDate());
        ihsParam.setEndDate(history.getSummaryDate());

        // 在庫履歴情報ハンドラ
        if (null == _inventoryHandler)
        {
            _inventoryHandler = new InventoryHistFileHandler(conn);
        }

        // 該当する在庫履歴が存在すれば当日在庫を更新し、無ければ新規作成します。
        HistoryEntity[] oldHistory = _inventoryHandler.search(ihsParam);
        if (null != oldHistory && 0 < oldHistory.length)
        {
            for (HistoryEntity histEntity : oldHistory)
            {
                InventoryHistoryEntity entity = (InventoryHistoryEntity)histEntity;
                stockQtySum += entity.getStockQty();
            }
        }
        return stockQtySum;
    }

    /**
     * パラメータで指定された集約キークラスと、集約データより、出荷履歴情報を編集し、
     * データベースに書き込みます。
     * @param conn データベースコネクション
     * @param key 集約キー
     * @param data 集約データ
     * @param lastDate 最終履歴生成日時
     * @param workDate 作業日
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    private void addShippingHist(Connection conn, AggregateKey key, int[] data, Date lastDate, String workDate)
            throws ReadWriteException
    {
        // 履歴情報を編集します。
        ShippingHistoryEntity history = new ShippingHistoryEntity();
        // 荷主コード
        history.setConsignorCode(key.getConsignor());
        // 出荷先コード
        history.setCustomerCode(key.getCustomer());
        // 商品コード
        history.setItemCode(key.getItemCode());
        // 集計日
        history.setSummaryDate(workDate);
        // 当日出荷数
        history.setShippingQty(data[ARG_SHIPPING]);
        // 伝票行数
        history.setWorkingQty(getShippingCnt(conn, lastDate, key.getConsignor(), key.getCustomer(), key.getItemCode()));

        // 出荷履歴情報をテーブルに書き込みます。
        if (null == _shippingHandler)
        {
            _shippingHandler = new ShippingHistFileHandler(conn);
        }

        // 追加時に重複データチェックを行うことにしたのでバッチ処理が使えなくなりました。
        //_shippingHandler.addBatch(history) ;
        _shippingHandler.write(history);
    }

    /**
     * 在庫履歴情報を書き込んだ商品について、作業単位数マスタの最終荷動き日を更新します。
     * @param conn データベースコネクション
     * @param history 在庫履歴情報エンティティ
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException スケジュール処理に失敗した場合に通知されます。
     */
    private void updateWorkingUnit(Connection conn, InventoryHistoryEntity history)
            throws ReadWriteException,
                ScheduleException
    {
        // 更新対象作業単位数マスタの検索キーを作成します。
        WorkingUnitSearchKey searchKey = new WorkingUnitSearchKey();
        searchKey.setConsignorCode(history.getConsignorCode());
        searchKey.setItemCode(history.getItemCode());

        // 更新対象作業単位数マスタが存在するかチェックします。
        try
        {
            WorkingUnitHandler handler = new WorkingUnitHandler(conn);
            int count = handler.count(searchKey);
            if (0 == count)
            {
                // 作業単位数マスタが存在しない場合は、何もしません。
                // 更新対象作業単位数マスタが存在しない場合は、新規に登録します。
            }
            else
            {
                // 更新対象商品管理テーブルが存在した場合は、最終荷動き日を更新します。
                WorkingUnitAlterKey alterKey = new WorkingUnitAlterKey();
                // 荷主コード
                alterKey.setConsignorCode(history.getConsignorCode());
                // 商品コード
                alterKey.setItemCode(history.getItemCode());
                // 最終荷動き日
                alterKey.updateLastUseWorkDay(getWorkDate(conn));
                // 最終更新処理名
                alterKey.updateLastUpdatePname(getClass().getSimpleName());
                handler.modify(alterKey);
            }
        }
        catch (NotFoundException e)
        {
            // 6003011=対象データはありませんでした。
            RmiMsgLogClient.write(new TraceHandler(6003011, e), getClass().getName());
            throw new ReadWriteException(e);
        }
    }

    /**
     * 在庫数更新（荷動き無し品のみ）
     * 荷主コード＆商品コードが同じで荷動きの無かった商品の在庫数を在庫履歴へ追加します。
     * @param conn データベースコネクション
     * @param key 在庫履歴更新キー情報
     * @param workDate 作業日
     * @return 在庫数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    private int setStockQty(Connection conn, AggregateKey key, String workDate)
            throws ReadWriteException
    {
        // 在庫情報テーブルの検索キーを作成します。
        StockSearchKey searchKey = new StockSearchKey();
        // 荷主コード
        searchKey.setConsignorCode(key.getConsignor());
        searchKey.setConsignorCodeCollect();
        // 商品コード
        searchKey.setItemCode(key.getItemCode());
        searchKey.setItemCodeCollect();
        // ロットNo
        searchKey.setLotNoCollect();
        // 在庫数の合計
        searchKey.setStockQtyCollect("SUM");
        // グループ指定
        searchKey.setConsignorCodeGroup();
        searchKey.setItemCodeGroup();
        searchKey.setLotNoGroup();
        // 設定した条件で在庫情報を読み込み、在庫数の合計を取得します。
        int stockQtySum = 0; // 在庫数の合計
        StockFinder finder = new StockFinder(conn);
        finder.open(true);
        int count = finder.search(searchKey);
        if (count > 0)
        {
            Stock[] stock = (Stock[])finder.getEntities(count);
            for (int i = 0; i < stock.length; i++)
            {
                // 在庫があれば在庫履歴を追加します。
                stockQtySum = stock[i].getStockQty();
                if (stockQtySum > 0)
                {
                    addStockQtyToInventoryHist(conn, stock[i], stockQtySum, workDate);
                }
            }
        }
        finder.close();
        return stockQtySum;
    }

    /**
     * 在庫履歴情報で指定された在庫数を在庫情報より読み取り通知します。
     * @param conn データベースコネクション
     * @param history 在庫履歴情報
     * @return 在庫数
     * @throws ReadWriteException データベースの接続でエラーを検出した場合に通知されます。
     */
    private int getStockQty(Connection conn, InventoryHistoryEntity history)
            throws ReadWriteException
    {
        // 在庫情報テーブルの検索キーを作成します。
        StockSearchKey searchKey = new StockSearchKey();
        // 荷主コード
        searchKey.setConsignorCode(history.getConsignorCode());
        // 商品コード
        searchKey.setItemCode(history.getItemCode());
        // ロットNo.
        searchKey.setLotNo(history.getLotNo());
        // 在庫数の合計
        searchKey.setStockQtyCollect("SUM");
        // 設定した条件で在庫情報を読み込み、在庫数の合計を取得します。
        int stockQtySum = 0; // 在庫数の合計
        StockFinder finder = new StockFinder(conn);
        finder.open(true);
        if (finder.search(searchKey) > 0)
        {
            Stock[] stock = (Stock[])finder.getEntities(1);
            stockQtySum += stock[0].getStockQty();
        }
        finder.close();
        return stockQtySum;
    }

    /**
     * 荷主コードと商品コードより該当する予定一意キーの数を取得します。
     * @param conn データベースコネクション
     * @param lastDate 最終履歴生成日時
     * @param consignorCode 荷主コード
     * @param customerCode 出荷先コード
     * @param itemCode 商品コード
     * @return 予定一意キー数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    private int getShippingCnt(Connection conn, Date lastDate, String consignorCode, String customerCode,
            String itemCode)
            throws ReadWriteException
    {
        // 分析対象作業実績が出庫の時は入出庫実績送信情報より対象データ件数を取得します。
        return getHostSendCnt(conn, lastDate, consignorCode, customerCode, itemCode);
    }

    /**
     * 入出庫送信履歴情報より該当する予定一意キーの数を取得します。
     * @param conn データベースコネクション
     * @param lastDate 最終履歴生成日時
     * @param consignorCode 荷主コード
     * @param customerCode 出荷先コード
     * @param itemCode 商品コード
     * @return 予定一意キー数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    private int getHostSendCnt(Connection conn, Date lastDate, String consignorCode, String customerCode,
            String itemCode)
            throws ReadWriteException
    {
        // 実績情報の検索キーを作成します。
        HostSendSearchKey searchKey = new HostSendSearchKey();

        // 最終履歴生成日時より後に発生したデータを検索します。
        searchKey.setLastUpdateDate(lastDate, ">");

        // 荷主コード
        searchKey.setConsignorCode(consignorCode);
        // 出荷先コード
        searchKey.setCustomerCode(customerCode);
        // 商品コード
        searchKey.setItemCode(itemCode);
        // 作業区分：出庫
        searchKey.setJobType(HostSend.JOB_TYPE_RETRIEVAL);

        // 荷主コード
        // 1番目の読み込みで、グルーピングに加えます。
        searchKey.setConsignorCodeCollect();
        searchKey.setConsignorCodeGroup();

        // 出荷先コード
        // 2番目の読み込みで、グルーピングに加えます。
        searchKey.setCustomerCodeCollect();
        searchKey.setCustomerCodeGroup();

        // 商品コード
        // 3番目の読み込みで、グルーピングに加えます。
        searchKey.setItemCodeCollect();
        searchKey.setItemCodeGroup();

        // 予定一意キー
        // 4番目の読み込みで、グルーピングに加えます。
        searchKey.setPlanUkeyCollect();
        searchKey.setPlanUkeyGroup();

        // 設定した条件で履歴情報を読み込み、対象レコード数を取得します。
        HostSendHandler handler = new HostSendHandler(conn);
        HostSend[] results = (HostSend[])handler.find(searchKey);
        int shippingCnt = results.length;
        return shippingCnt;
    }

    /**
     * バッチ処理の終了手続きを行います。
     * @param type 処理タイプ
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    private void finalizeBatch(int type)
            throws ReadWriteException
    {
        try
        {
            // 在庫履歴
            if (TYPE_INVENTORY == type)
            {
                if (null != _inventoryHandler)
                {
                    _inventoryHandler.finalizeBatch();
                }
            }
            // 出荷履歴
            else
            {
                if (null != _shippingHandler)
                {
                    _shippingHandler.finalizeBatch();
                }
            }
        }
        catch (SQLException e)
        {
            // 6127005=データベースエラーが発生しました。
            RmiMsgLogClient.write(new TraceHandler(6127005, e), getClass().getName());
            /*
             // 6006022=データベースエラーが発生しました。エラーコード = {0}
             RmiMsgLogClient.write(WmsMessageFormatter.format(6006022, Integer.valueOf(e.getErrorCode())), getClass().getName());
             */
            throw new ReadWriteException(e);
        }
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * 履歴データ生成最終日を取得します。<br>
     * 分析設定ファイルの読み込みに失敗した場合や、設定内容に異常を検出した場合は
     * デフォルトの日時を返します。
     * @return 履歴データ生成最終日
     */
    private Date getHistoryMakedDate()
    {
        // 分析設定ファイルハンドラを生成し、履歴データ生成最終日を読み込みます。
        AnalysisIniFileHandler iniP = new AnalysisIniFileHandler();
        iniP.load();
        return iniP.getHistoryMakedDate();
    }

    /**
     * 履歴データ生成最終日をセーブします。
     * @param date 履歴データ生成最終日
     * @return 保存結果
     */
    private boolean saveHistoryMakedDate(Date date)
    {
        // 分析設定ファイルハンドラー
        AnalysisIniFileHandler iniP = new AnalysisIniFileHandler();
        iniP.setHistoryMakedDate(date);
        return iniP.saveHistoryMakedDate();
    }

    /**
     * システム定義情報より作業日を取得します。
     * @param conn データベースコネクション
     * @return 作業日(YYYYMMDD)
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException スケジュール処理に失敗した場合に通知されます。
     */
    private String getWorkDate(Connection conn)
            throws ReadWriteException,
                ScheduleException
    {
        // システム定義情報を読み込みます。
        WarenaviSystemController controller = new WarenaviSystemController(conn, getClass());
        return controller.getWorkDay();
    }

    /**
     * 処理終了日時を取得します。
     * @param conn データベースコネクション
     * @return 現在日時
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    private Date getFinishDate(Connection conn)
            throws ReadWriteException
    {
        String sysDate = DbDateUtil.getSystemDateTimeStamp();
        return WmsFormatter.toDateTime(sysDate);
    }

    /**
     * パラメータで指定された日よりシステムで定められた一定期間前の日付を取得します。
     * @param date 指定日
     * @return 一定期間前の日付(YYYYMMDD)
     */
    private String getTerm(Date date)
    {
        //  定義されている保存期間を取得します。
        int month = WmsParam.KEEP_MONTHS;

        // 指定日を取得します。
        Calendar theCal = Calendar.getInstance();
        theCal.setTime(date);

        // 一定期間前の日付を算出します。
        theCal.add(Calendar.MONTH, -month);

        // 求めた日付より一定期間前の日付を編集します。
        // ここで編集する日付は1ヶ月単位での削除が目的のため、日を"99"としています。
        StringBuffer returnDate = new StringBuffer(WmsFormatter.toParamYearMonth(theCal.getTime()));
        returnDate.append("99");

        return String.valueOf(returnDate);
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列
     */
    public static String getVersion()
    {
        return "$Id: HistoryMaker.java 4812 2009-08-10 11:05:22Z kumano $";
    }
}
