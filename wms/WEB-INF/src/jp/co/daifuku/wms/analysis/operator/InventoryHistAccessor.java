// $Id: InventoryHistAccessor.java 4812 2009-08-10 11:05:22Z kumano $
package jp.co.daifuku.wms.analysis.operator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.analysis.dbhandler.AbstractHistoryHandler;
import jp.co.daifuku.wms.analysis.dbhandler.InventoryHistFileHandler;
import jp.co.daifuku.wms.analysis.entity.HistoryEntity;
import jp.co.daifuku.wms.analysis.entity.InventoryHistoryEntity;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ItemFinder;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHistoryFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHistorySearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.StockHistory;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 在庫履歴データ検索クラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2006/06/27</td><td nowrap>Softecs</td><td>Class created.</td></tr>
 * <tr><td nowrap>2008/01/20</td><td nowrap>Shimizu(Softecs)</td><td>v3.2移行</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 4812 $, $Date: 2009-08-10 20:05:22 +0900 (月, 10 8 2009) $
 * @author  Softecs
 * @author  Last commit: $Author: kumano $
 */


public class InventoryHistAccessor
        extends AbstractHistoryAccessor
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

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

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションを指定してインスタンス化します。<BR>
     * @param conn データベースコネクション<BR>
     */
    public InventoryHistAccessor(Connection conn)
    {
        super(conn);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 履歴データ検索（在庫推移）。<BR>
     * パラメータで指定された検索条件をもとに履歴データを検索し、検索結果を返します。<BR>
     * @param param 検索条件<BR>
     * @return 検索結果<BR>
     * @throws ReadWriteException データベースの接続でエラーを検出した場合に通知されます。
     * @throws  ScheduleException 予期しないエラーを検出した場合に通知されます。
     */
    @Override
    public HistorySearchResult search(HistorySearchParamater param)
            throws ReadWriteException,
                ScheduleException
    {
        // 検索結果オブジェクトを生成し、指定された検索条件を設定します。
        InventoryHistSearchResult result = new InventoryHistSearchResult();
        result.setSearchParamater(param);

        // 引き続き、現在庫数を取得し、検索結果オブジェクトに設定します。
        InventoryHistSearchParameter sparam = (InventoryHistSearchParameter)param;
        int stockQty = getStockQty(sparam);
        result.setStockQty(stockQty);

        // 商品マスタから上下限数を取得
        int[] upperlowerQty = getUpperLowerQty(sparam);
        result.setUpperQty(upperlowerQty[0]);
        result.setLowerQty(upperlowerQty[1]);

        result.setWorkDate(getWorkDate());

        // 検索条件により、履歴データを検索します。
        InventoryHistFileHandler handler = new InventoryHistFileHandler(getConnection());
        HistoryEntity[] histories = handler.search(param);

        // 検索した履歴データを検索結果に設定します。
        result.setResult(histories);

        return result;
    }

    /**
     * 履歴データ検索（商品一覧）。<BR>
     * パラメータで指定された検索条件をもとに履歴データを検索し、検索結果を返します。<BR>
     * @param param 検索条件<BR>
     * @return 検索結果<BR>
     * @throws ReadWriteException データベースの接続でエラーを検出した場合に通知されます。
     */
    public ResultSet getItemList(InventoryHistSearchParameter param)
            throws ReadWriteException
    {
        // 検索条件により、履歴データを検索します。
        InventoryHistFileHandler handler = new InventoryHistFileHandler(getConnection());

        return handler.searchItemList(param);
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
    /**
     * 検索結果オブジェクトを生成し返します。<BR>
     * @return 検索結果オブジェクト<BR>
     */
    @Override
    protected HistorySearchResult getHistorySearchResult()
    {
        return new InventoryHistSearchResult();
    }

    /**
     * 履歴データのハンドラを取得します。<BR>
     * @param conn データベースコネクションオブジェクト<BR>
     * @return 履歴データハンドラ<BR>
     */
    @Override
    protected AbstractHistoryHandler getHistoryHandler(Connection conn)
    {
        InventoryHistFileHandler handler = new InventoryHistFileHandler(conn);
        return handler;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 検索条件を指定して、該当する在庫情報より現在庫数を算出します。<BR>
     * @param param 検索条件<BR>
     * @return 現在庫数<BR>
     * @throws ReadWriteException データベースの接続でエラーを検出した場合に通知されます。
     * @throws  ScheduleException 予期しないエラーを検出した場合に通知されます。
     */
    private int getStockQty(InventoryHistSearchParameter param)
            throws ReadWriteException,
                ScheduleException
    {
        // 直近の履歴情報より現在庫数を算出します。
        int histStockQty = getHistStockQty(param);
        if (0 <= histStockQty)
        {
            return histStockQty;
        }

        // 直近の履歴情報が存在しない場合は、在庫情報より在庫数を求めます。
        // 在庫情報の検索キーを作成します。
        StockSearchKey searchKey = new StockSearchKey();
        // 荷主コード
        if (!StringUtil.isBlank(param.getConsignorCode()))
        {
            searchKey.setConsignorCode(param.getConsignorCode());
        }
        // 商品コード
        if (!StringUtil.isBlank(param.getItemCode()))
        {
            searchKey.setItemCode(param.getItemCode());
        }
        // ロットNo.
        if (!StringUtil.isBlank(param.getLotNo()))
        {
            searchKey.setLotNo(param.getLotNo());
        }

        // 取得項目：在庫数
        searchKey.setStockQtyCollect("SUM");

        // 設定した条件で在庫情報を読み込み、在庫数の合計を取得します。
        StockFinder finder = new StockFinder(getConnection());

        finder.open(true);
        int count = finder.search(searchKey);
        // 在庫数の合計
        long stockQtySum = 0;

        if (count > 0)
        {
            Stock[] stock = (Stock[])finder.getEntities(1);
            stockQtySum = stock[0].getStockQty();
        }
        finder.close();

        // 最終履歴データ生成日を取得します。
        AnalysisIniFileHandler iniP = new AnalysisIniFileHandler();
        if (!iniP.load())
        {
            // 読み込み失敗
            throw new ScheduleException();
        }
        Date lastDate = iniP.getHistoryMakedDate();

        // 日次更新前の履歴に作成されていない在庫を実績送信情報より取得し、現在庫から除きます。
        stockQtySum += (getMinusResultQty(param, lastDate) - getPlusResultQty(param, lastDate));
        if (stockQtySum < 0)
        {
            stockQtySum = 0;
        }

        return (int)stockQtySum;
    }

    /**
     * 検索条件を指定して、該当する商品マスタより上下限在庫数を取得します。<BR>
     * @param param 検索条件<BR>
     * @return 上限在庫数、下限在庫数<BR>
     * @throws ReadWriteException データベースの接続でエラーを検出した場合に通知されます。
     */
    private int[] getUpperLowerQty(InventoryHistSearchParameter param)
            throws ReadWriteException
    {
        // 在庫情報の検索キーを作成します。
        ItemSearchKey searchKey = new ItemSearchKey();

        // 荷主コード
        searchKey.setConsignorCode(param.getConsignorCode());
        // 商品コード
        searchKey.setItemCode(param.getItemCode());

        // 取得条件：上限在庫数
        searchKey.setUpperQtyCollect("");
        searchKey.setLowerQtyCollect("");

        // 設定した条件で在庫情報を読み込み、在庫数の合計を取得します。
        ItemFinder finder = new ItemFinder(getConnection());

        finder.open(true);
        int count = finder.search(searchKey);
        int[] qty = {
            0,
            0
        };
        if (count > 0)
        {
            Item[] item = (Item[])finder.getEntities(1);
            qty[0] = item[0].getUpperQty();
            qty[1] = item[0].getLowerQty();
        }
        finder.close();

        return qty;
    }

    /**
     * 指定された検索条件より、直近の履歴情報より在庫数を算出します。<BR>
     * 該当する履歴情報が存在しなかった場合は、-1を返します。<BR>
     * @param param 検索条件<BR>
     * @return 在庫数<BR>
     * @throws ReadWriteException データベースの接続でエラーを検出した場合に通知されます。
     * @throws  ScheduleException 予期しないエラーを検出した場合に通知されます。
     */
    private int getHistStockQty(InventoryHistSearchParameter param)
            throws ReadWriteException,
                ScheduleException
    {
        // 履歴情報の検索キーを作成します。
        // 検索日付の範囲は、検索開始日より
        // 作業日までとし、最新の履歴情報より在庫数を算出します。
        InventoryHistSearchParameter sparam = new InventoryHistSearchParameter();
        sparam.setSummaryType(param.getSummaryType());
        sparam.setStartDate(param.getStartDate());
        sparam.setEndDate(getWorkDate());

        // 荷主コード
        String strParam = param.getConsignorCode();
        if (strParam != null)
        {
            sparam.setConsignorCode(strParam);
        }
        // 商品コード
        strParam = param.getItemCode();
        if (strParam != null)
        {
            sparam.setItemCode(strParam);
        }
        // ロットNo
        strParam = param.getLotNo();
        if (strParam != null)
        {
            sparam.setLotNo(strParam);
        }

        // 検索条件により、履歴データを検索します。
        InventoryHistFileHandler handler = new InventoryHistFileHandler(getConnection());
        HistoryEntity[] histories = handler.search(sparam);
        if (null == histories || 0 == histories.length)
        {
            return -1;
        }

        // 検索した履歴データより直近の履歴を取得します。
        InventoryHistoryEntity entity = searchHistory(param.getStartDate(), histories);
        if (null == entity)
        {
            return -1;
        }

        int stockQty = entity.getStockQty();
        if (stockQty < 0)
        {
            stockQty = 0;
        }
        int storageQty = entity.getStorageQty();
        if (storageQty < 0)
        {
            storageQty = 0;
        }
        int retrievalQty = entity.getRetrievalQty();
        if (retrievalQty < 0)
        {
            retrievalQty = 0;
        }
        int returnQty = stockQty + retrievalQty - storageQty;
        if (returnQty < 0)
        {
            returnQty = 0;
        }
        return returnQty;
    }

    /**
     * 指定された検索条件より、実績送信情報の実績数を算出します。<BR>
     * 在庫増の実績数を返します。<BR>
     * @param param 検索条件<BR>
     * @param lastDate 履歴データ生成最終日<BR>
     * @return 実績数<BR>
     * @throws ReadWriteException データベースの接続でエラーを検出した場合に通知されます。
     */
    private long getPlusResultQty(InventoryHistSearchParameter param, Date lastDate)
            throws ReadWriteException
    {
        // 実績送信情報の検索キーを作成します。
        StockHistorySearchKey searchKey = new StockHistorySearchKey();
        // 荷主コード
        if (!StringUtil.isBlank(param.getConsignorCode()))
        {
            searchKey.setConsignorCode(param.getConsignorCode());
        }
        // 商品コード
        if (!StringUtil.isBlank(param.getItemCode()))
        {
            searchKey.setItemCode(param.getItemCode());
        }
        // ロットNo.
        if (!StringUtil.isBlank(param.getLotNo()))
        {
            searchKey.setLotNo(param.getLotNo());
        }

        // 増減区分：在庫加算（入庫）
        searchKey.setIncDecType(StockHistory.INC_DEC_TYPE_STOCK_INCREMENT);
        // 分析区分
        searchKey.setAnalysisType(StockHistory.ANALYSIS_TYPE_ANALYSIS);

        // 指定された最終履歴データ生成日より後に発生したデータを検索する
        searchKey.setRegistDate(lastDate, ">");

        // 取得項目：在庫増減数
        searchKey.setIncDecQtyCollect("SUM");

        // 設定した条件で在庫情報を読み込み、在庫数の合計を取得します。
        StockHistoryFinder finder = new StockHistoryFinder(getConnection());

        finder.open(true);
        int count = finder.search(searchKey);
        // 実績数の合計
        long resultQtySum = 0;
        if (0 < count)
        {
            StockHistory[] hostsend = (StockHistory[])finder.getEntities(1);
            resultQtySum = hostsend[0].getIncDecQty();
        }
        finder.close();

        return resultQtySum;
    }

    /**
     * 指定された検索条件より、実績送信情報の実績数を算出します。<BR>
     * 在庫減の実績数を返します。<BR>
     * @param param 検索条件<BR>
     * @param lastDate 履歴データ生成最終日<BR>
     * @return 実績数<BR>
     * @throws ReadWriteException データベースの接続でエラーを検出した場合に通知されます。
     */
    private long getMinusResultQty(InventoryHistSearchParameter param, Date lastDate)
            throws ReadWriteException
    {
        // 実績送信情報の検索キーを作成します。
        StockHistorySearchKey searchKey = new StockHistorySearchKey();
        // 荷主コード
        if (!StringUtil.isBlank(param.getConsignorCode()))
        {
            searchKey.setConsignorCode(param.getConsignorCode());
        }
        // 商品コード
        if (!StringUtil.isBlank(param.getItemCode()))
        {
            searchKey.setItemCode(param.getItemCode());
        }
        // ロットNo.
        if (!StringUtil.isBlank(param.getLotNo()))
        {
            searchKey.setLotNo(param.getLotNo());
        }

        // 増減区分：在庫減算（出庫）
        searchKey.setIncDecType(StockHistory.INC_DEC_TYPE_STOCK_DECREMENT);
        // 分析区分
        searchKey.setAnalysisType(StockHistory.ANALYSIS_TYPE_ANALYSIS);

        // 指定された最終履歴データ生成日より後に発生したデータを検索する
        searchKey.setRegistDate(lastDate, ">");

        // 取得項目：在庫増減数
        searchKey.setIncDecQtyCollect("SUM");

        // 設定した条件で在庫情報を読み込み、在庫数の合計を取得します。
        StockHistoryFinder finder = new StockHistoryFinder(getConnection());

        finder.open(true);
        int count = finder.search(searchKey);
        // 実績数の合計
        long resultQtySum = 0;
        if (0 < count)
        {
            StockHistory[] hostsend = (StockHistory[])finder.getEntities(1);
            resultQtySum = hostsend[0].getIncDecQty();
        }
        finder.close();

        return resultQtySum;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * システム定義情報より作業日を取得します。<BR>
     * @return 作業日(YYYYMMDD)<BR>
     * @throws ReadWriteException データベースの接続でエラーを検出した場合に通知されます。
     * @throws  ScheduleException 予期しないエラーを検出した場合に通知されます。
     */
    private String getWorkDate()
            throws ReadWriteException,
                ScheduleException
    {
        // システム定義情報を読み込みます。
        WarenaviSystemController controller = new WarenaviSystemController(getConnection(), getClass());
        String workDate = controller.getWorkDay();
        return workDate;
    }

    /**
     *  パラメータで指定された日付のデータを検索結果エンティティの中から検索します。<br>
     *  該当するデータが見つからない場合には、直後の検索結果エンティティを返します。<br>
     *  検索日付よりも古いデータしか含まれていなかった場合は、その中で一番新しいデータを
     *  検索結果として返します。
     *  @param date 日付(YYYYMMDD)
     *  @param histories 検索結果エンティティ
     *  @return 該当検索結果エンティティ
     */
    public static InventoryHistoryEntity searchHistory(String date, HistoryEntity[] histories)
    {
        // 在庫履歴データエンティティの配列に変換し、日付の昇順にソートします。
        int size = histories.length;
        InventoryHistoryEntity[] inventoryHistArr = new InventoryHistoryEntity[size];
        for (int i = 0; i < size; i++)
        {
            inventoryHistArr[i] = (InventoryHistoryEntity)histories[i];
        }
        Arrays.sort(inventoryHistArr);

        //  指定された日付と一致するデータを検索します。
        for (InventoryHistoryEntity entity : inventoryHistArr)
        {
            //  指定日付と一致する検索結果エンティティがあれば、このエンティティを返します。
            if (entity.getSummaryDate().equals(date))
            {
                return entity;
            }
            //  指定日付よりも新しい(大きい)日付であれば、このエンティティを返します。
            if (0 < entity.getSummaryDate().compareTo(date))
            {
                return entity;
            }
        }
        //  検索日付よりも古いデータしかなかった場合は、一番新しいデータを返します。
        return inventoryHistArr[size - 1];
    }

    /**
     * このクラスのリビジョンを返します。<BR>
     * @return リビジョン文字列。<BR>
     */
    public static String getVersion()
    {
        return "$Id: InventoryHistAccessor.java 4812 2009-08-10 11:05:22Z kumano $";
    }
}
