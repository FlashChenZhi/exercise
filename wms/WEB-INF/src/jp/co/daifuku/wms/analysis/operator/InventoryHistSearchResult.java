// $Id: InventoryHistSearchResult.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.analysis.operator;

import java.util.Calendar;

import jp.co.daifuku.wms.analysis.entity.HistoryEntity;
import jp.co.daifuku.wms.analysis.entity.InventoryHistoryEntity;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 在庫履歴データ検索結果クラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2006/06/24</td><td nowrap>清水 正人</td><td>Class created.</td></tr>
 * <tr><td nowrap>2008/01/20</td><td nowrap>Shimizu(Softecs)</td><td>v3.2移行</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  清水 正人
 * @author  Last commit: $Author: admin $
 */


public class InventoryHistSearchResult
        extends AbstractHistorySearchResult
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
    /**
     * 検索結果オブジェクト
     */
    private class InventoryHistResult
    {
        /**
         * タイムスタンプ
         */
        private String _summaryDate = null;

        /**
         * 荷主コード
         */
        private String _consignorCode = null;

        /**
         * 商品コード
         */
        private String _itemCode = null;

        /**
         * 上限在庫数
         */
        private int _ubQty = 0;

        /**
         * 下限在庫数
         */
        private int _lbQty = 0;

        /**
         * 在庫数
         */
        private int _stockQty1 = 0;

        /**
         * 入庫数
         */
        private int _storageQty = 0;

        /**
         * 入荷数
         */
        private int _instockQty = 0;

        /**
         * 出庫数
         */
        private int _retrievalQty = 0;

        /**
         * 出荷数
         */
        private int _shippingQty = 0;

        /**
         * _consignorCodeを返します。
         * @return _consignorCodeを返します。
         */
        public String getConsignorCode()
        {
            return _consignorCode;
        }

        /**
         * _consignorCodeを設定します。
         * @param code _consignorCode
         */
        public void setConsignorCode(String code)
        {
            _consignorCode = code;
        }

        /**
         * _instockQtyを返します。
         * @return _instockQtyを返します。
         */
        public int getInstockQty()
        {
            return (0 > _instockQty) ? 0
                                    : _instockQty;
        }

        /**
         * _instockQtyを設定します。
         * @param qty _instockQty
         */
        public void setInstockQty(int qty)
        {
            _instockQty = qty;
        }

        /**
         * _itemCodeを返します。
         * @return _itemCodeを返します。
         */
        public String getItemCode()
        {
            return _itemCode;
        }

        /**
         * _itemCodeを設定します。
         * @param code _itemCode
         */
        public void setItemCode(String code)
        {
            _itemCode = code;
        }

        /**
         * _lbQtyを返します。
         * @return _lbQtyを返します。
         */
        public int getLbQty()
        {
            return (0 > _lbQty) ? 0
                               : _lbQty;
        }

        /**
         * _lbQtyを設定します。
         * @param qty _lbQty
         */
        public void setLbQty(int qty)
        {
            _lbQty = qty;
        }

        /**
         * _retrievalQtyを返します。
         * @return _retrievalQtyを返します。
         */
        public int getRetrievalQty()
        {
            return (0 > _retrievalQty) ? 0
                                      : _retrievalQty;
        }

        /**
         * _retrievalQtyを設定します。
         * @param qty _retrievalQty
         */
        public void setRetrievalQty(int qty)
        {
            _retrievalQty = qty;
        }

        /**
         * _shippingQtyを返します。
         * @return _shippingQtyを返します。
         */
        public int getShippingQty()
        {
            return (0 > _shippingQty) ? 0
                                     : _shippingQty;
        }

        /**
         * _shippingQtyを設定します。
         * @param qty _shippingQty
         */
        public void setShippingQty(int qty)
        {
            _shippingQty = qty;
        }

        /**
         * _stockQtyを返します。
         * @return _stockQtyを返します。
         */
        public int getStockQty()
        {
            return (0 > _stockQty1) ? 0
                                   : _stockQty1;
        }

        /**
         * _stockQtyを設定します。
         * @param qty _stockQty
         */
        public void setStockQty(int qty)
        {
            _stockQty1 = qty;
        }

        /**
         * _storageQtyを返します。
         * @return _storageQtyを返します。
         */
        public int getStorageQty()
        {
            return (0 > _storageQty) ? 0
                                    : _storageQty;
        }

        /**
         * _storageQtyを設定します。
         * @param qty _storageQty
         */
        public void setStorageQty(int qty)
        {
            _storageQty = qty;
        }

        /**
         * _summaryDateを返します。
         * @return _summaryDateを返します。
         */
        public String getSummaryDate()
        {
            return _summaryDate;
        }

        /**
         * _summaryDateを設定します。
         * @param date _summaryDate
         */
        public void setSummaryDate(String date)
        {
            _summaryDate = date;
        }

        /**
         * _ubQtyを返します。
         * @return _ubQtyを返します。
         */
        public int getUbQty()
        {
            return (0 > _ubQty) ? 0
                               : _ubQty;
        }

        /**
         * _ubQtyを設定します。
         * @param qty _ubQty
         */
        public void setUbQty(int qty)
        {
            _ubQty = qty;
        }
    }

    /**
     * 現在庫数
     */
    private int _stockQty = 0;

    /**
     * 上限在庫数
     */
    private int _upperQty = 0;

    /**
     * 下限在庫数
     */
    private int _lowerQty = 0;

    /**
     * 作業日
     */
    private String _workDate = "";

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ
     */
    public InventoryHistSearchResult()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 検索条件によって取り出された履歴データを設定します。<BR>
     * @param data 履歴データ<BR>
     */
    public void setResult(HistoryEntity[] data)
    {
        // 検索結果オブジェクトを生成します。
        int count = createHistResult();

        // 検索結果が1件もない場合
        if (null == data || 0 == data.length)
        {
            for (int i = 0; i < count; i++)
            {
                InventoryHistResult result = (InventoryHistResult)getResult(i);

                // 現在の作業日より未来でなければ現在庫数と上下限在庫数をセットし、それ以外は在庫数0をセット。
                if (0 <= result._summaryDate.compareTo(_workDate))
                {
                    result._stockQty1 = 0;
                    result._ubQty = _upperQty;
                    result._lbQty = _lowerQty;
                }
                else
                {
                    result._stockQty1 = _stockQty;
                    result._ubQty = _upperQty;
                    result._lbQty = _lowerQty;
                }
            }
            return;
        }

        //  検索結果を集計日の昇順にソートします。
        /* searchHistoryメソッドを使用しないように変更(パフォーマンスは？)
         InventoryHistoryEntity[] inventoryArr = new InventoryHistoryEntity[data.length];
         for (int i = 0; i < data.length; i++)
         {
         inventoryArr[i] = (InventoryHistoryEntity)data[i];
         }
         Arrays.sort(inventoryArr);
         */

        // 検索結果を検索結果オブジェクトに格納します。
        for (int i = 0; i < count; i++)
        {
            // 履歴データ格納位置を検索し、各値を設定します。
            InventoryHistResult result = (InventoryHistResult)getResult(i);
            String date = result._summaryDate;
            //int ix = searchHistory(date, inventoryArr) ;
            InventoryHistoryEntity entity = InventoryHistAccessor.searchHistory(date, data);

            // 現在の作業日と比較し、作業日以降のデータの場合は在庫数0と上下限在庫数をセット
            if (0 <= date.compareTo(_workDate))
            {
                result._stockQty1 = 0;
                result._ubQty = _upperQty;
                result._lbQty = _lowerQty;
            }
            else
            {
                // 格納位置が見当たらなかった場合は、現在庫数と上下限在庫数を設定します。
                //if (0 > ix)
                if (null == entity)
                {
                    result._stockQty1 = _stockQty;
                    result._ubQty = _upperQty;
                    result._lbQty = _lowerQty;
                }
                else
                {
                    //if (date.equals(inventoryArr[ix].getSummaryDate()))
                    if (date.equals(entity.getSummaryDate()))
                    {
                        /*
                         result.p_ubQty = inventoryArr[ix].getStockUbqty() ;
                         result.p_lbQty = inventoryArr[ix].getStockLbqty() ;
                         result.p_stockQty = inventoryArr[ix].getStockQty() ;
                         result.p_storageQty = inventoryArr[ix].getStorageQty() ;
                         result.p_instockQty = 0;  // v3.2では集計しません。
                         result.p_retrievalQty = inventoryArr[ix].getRetrievalQty() ;
                         result.p_shippingQty = 0; // v3.2では集計しません。
                         */
                        result._ubQty = entity.getStockUbqty();
                        result._lbQty = entity.getStockLbqty();
                        result._stockQty1 = entity.getStockQty();
                        result._storageQty = entity.getStorageQty();
                        result._instockQty = 0; // v3.2では集計しません。
                        result._retrievalQty = entity.getRetrievalQty();
                        result._shippingQty = 0; // v3.2では集計しません。
                    }
                    else
                    {
                        /*
                         result.p_ubQty = inventoryArr[ix].getStockUbqty() ;
                         result.p_lbQty = inventoryArr[ix].getStockLbqty() ;
                         result.p_stockQty = inventoryArr[ix].getStockQty() + inventoryArr[ix].getRetrievalQty() - inventoryArr[ix].getStorageQty() ;
                         */
                        result._ubQty = entity.getStockUbqty();
                        result._lbQty = entity.getStockLbqty();
                        if (0 > date.compareTo(entity.getSummaryDate()))
                        {
                            // 過去のデータより捏造する場合は、在庫数を補正します。
                            result._stockQty1 =
                                    entity.getStockQty() + entity.getRetrievalQty() - entity.getStorageQty();
                        }
                        else
                        {
                            result._stockQty1 = entity.getStockQty();
                        }
                        result._storageQty = 0;
                        result._instockQty = 0;
                        result._retrievalQty = 0;
                        result._shippingQty = 0;
                    }
                }
            }
        }
    }

    /**
     * 検索結果設定（商品一覧）。<BR>
     * 検索条件によって取り出された履歴データを設定します。<BR>
     * @param data 履歴データ<BR>
     */
    public void setItemList(HistoryEntity[] data)
    {
        // 検索結果を検索結果オブジェクトに格納します。
        for (int i = 0; i < data.length; i++)
        {
            // 履歴データ格納位置を検索し、各値を設定します。
            // 検索結果オブジェクトを生成して、タイムスタンプを設定します。
            InventoryHistResult result = (InventoryHistResult)createResult();
            InventoryHistoryEntity history = (InventoryHistoryEntity)data[i];
            result._consignorCode = history.getConsignorCode();
            result._itemCode = history.getItemCode();
            addResult(result);
        }
    }

    /**
     * パラメータで指定された検索結果オブジェクトよりタイムスタンプを取得します。<BR>
     * @param data 検索結果オブジェクト<BR>
     * @return タイムスタンプ<BR>
     */
    public String getTimeStamp(Object data)
    {
        InventoryHistResult result = (InventoryHistResult)data;
        return result.getSummaryDate();
    }

    /**
     * パラメータで指定された検索結果オブジェクトより荷主コードを取得します。<BR>
     * @param data 検索結果オブジェクト<BR>
     * @return 荷主コード<BR>
     */
    public String getConsignorCode(Object data)
    {
        InventoryHistResult result = (InventoryHistResult)data;
        return result.getConsignorCode();
    }

    /**
     * パラメータで指定された検索結果オブジェクトより商品コードを取得します。<BR>
     * @param data 検索結果オブジェクト<BR>
     * @return 商品コード<BR>
     */
    public String getItemCode(Object data)
    {
        InventoryHistResult result = (InventoryHistResult)data;
        return result.getItemCode();
    }

    /**
     * パラメータで指定された検索結果オブジェクトより上限在庫数を取得します。<BR>
     * @param data 検索結果オブジェクト<BR>
     * @return 上限在庫数<BR>
     */
    public int getStockUbQty(Object data)
    {
        InventoryHistResult result = (InventoryHistResult)data;
        return result.getUbQty();
    }

    /**
     * パラメータで指定された検索結果オブジェクトより下限在庫数を取得します。<BR>
     * @param data 検索結果オブジェクト<BR>
     * @return 下限在庫数<BR>
     */
    public int getStockLbQty(Object data)
    {
        InventoryHistResult result = (InventoryHistResult)data;
        return result.getLbQty();
    }

    /**
     * パラメータで指定された検索結果オブジェクトより在庫数を取得します。<BR>
     * @param data 検索結果オブジェクト<BR>
     * @return 在庫数<BR>
     */
    public int getStockQty(Object data)
    {
        InventoryHistResult result = (InventoryHistResult)data;
        return result.getStockQty();
    }

    /**
     * パラメータで指定された検索結果オブジェクトより入庫数を取得します。<BR>
     * @param data 検索結果オブジェクト<BR>
     * @return 入庫数<BR>
     */
    public int getStorageQty(Object data)
    {
        InventoryHistResult result = (InventoryHistResult)data;
        return result.getStorageQty();
    }

    /**
     * パラメータで指定された検索結果オブジェクトより入荷数を取得します。<BR>
     * @param data 検索結果オブジェクト<BR>
     * @return 入荷数<BR>
     */
    public int getInstockQty(Object data)
    {
        InventoryHistResult result = (InventoryHistResult)data;
        return result.getInstockQty();
    }

    /**
     * パラメータで指定された検索結果オブジェクトより出庫数を取得します。<BR>
     * @param data 検索結果オブジェクト<BR>
     * @return 出庫数<BR>
     */
    public int getRetrievalQty(Object data)
    {
        InventoryHistResult result = (InventoryHistResult)data;
        return result.getRetrievalQty();
    }

    /**
     * パラメータで指定された検索結果オブジェクトより出荷数を取得します。<BR>
     * @param data 検索結果オブジェクト<BR>
     * @return 出荷数<BR>
     */
    public int getShippingQty(Object data)
    {
        InventoryHistResult result = (InventoryHistResult)data;
        return result.getShippingQty();
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 現在庫数を設定します。<BR>
     * @param qty 現在庫数<BR>
     */
    public void setStockQty(int qty)
    {
        _stockQty = qty;
    }

    /**
     * 現在庫数を取得します。<BR>
     * @return 現在庫数<BR>
     */
    public long getStockQty()
    {
        return _stockQty;
    }

    /**
     * 上限在庫数を設定します。<BR>
     * @param qty 上限在庫数<BR>
     */
    public void setUpperQty(int qty)
    {
        _upperQty = qty;
    }

    /**
     * 上限在庫数を取得します。<BR>
     * @return 上限在庫数<BR>
     */
    public long getUpperQty()
    {
        return _upperQty;
    }

    /**
     * 下限在庫数を設定します。<BR>
     * @param qty 下限在庫数<BR>
     */
    public void setLowerQty(int qty)
    {
        _lowerQty = qty;
    }

    /**
     * 下限在庫数を取得します。<BR>
     * @return 下限在庫数<BR>
     */
    public long getLowerQty()
    {
        return _lowerQty;
    }

    /**
     * 作業日を設定します。<BR>
     * @param date 作業日<BR>
     */
    public void setWorkDate(String date)
    {
        _workDate = date;
    }

    /**
     * 作業日を取得します。<BR>
     * @return 作業日<BR>
     */
    public String getWorkDate()
    {
        return _workDate;
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 検索結果オブジェクトを生成します。<BR>
     * @return 検索結果オブジェクト<BR>
     */
    @Override
    protected Object createResult()
    {
        return new InventoryHistResult();
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 検索条件より、検索結果を格納する結果オブジェクトを生成します。<BR>
     * @return 生成結果オブジェクト数<BR>
     */
    private int createHistResult()
    {
        // 検索条件より、集計単位、開始日付、終了日付を取得します。
        InventoryHistSearchParameter param = (InventoryHistSearchParameter)getSearchParamater();
        String type = param.getSummaryType();
        String date;
        String endDate;

        // 集計単位が「日」であれば、タイムスタンプは"YYYYMMDD"とします。
        if (!type.equals(InventoryHistSearchParameter.SUMMARY_TYPE_MONTH))
        {
            date = param.getStartDate();
            endDate = param.getEndDate();
        }

        // 集計単位が「月」であれば、タイムスタンプは"YYYYMM"とします。
        else
        {
            date = param.getStartDate().substring(0, 6);
            endDate = param.getEndDate().substring(0, 6);
        }

        // 開始日付～終了日付までの検索結果オブジェクトを生成します。
        Calendar cal = Calendar.getInstance();
        while (true)
        {
            // 検索結果オブジェクトを生成して、タイムスタンプを設定します。
            InventoryHistResult result = (InventoryHistResult)createResult();
            result._summaryDate = date;
            addResult(result);

            // 設定したタイムスタンプが終了日付と一致していれば
            // 検索結果オブジェクトの生成は終了します。
            if (endDate.equals(date))
            {
                break;
            }

            // 次のタイムスタンプを算出します。
            if (InventoryHistSearchParameter.SUMMARY_TYPE_MONTH != type)
            {
                int year = Integer.parseInt(date.substring(0, 4));
                int month = Integer.parseInt(date.substring(4, 6)) - 1;
                int day = Integer.parseInt(date.substring(6, 8));
                cal.set(year, month, day);
                cal.add(Calendar.DATE, 1);
                date = WmsFormatter.toParamDate(cal.getTime());
            }
            else
            {
                int year = Integer.parseInt(date.substring(0, 4));
                int month = Integer.parseInt(date.substring(4, 6)) - 1;
                cal.set(year, month, 1);
                cal.add(Calendar.MONTH, 1);
                // DB内部での日付表現は Locale.JAPANESE を使用しています。
                date = WmsFormatter.toParamYearMonth(cal.getTime());
            }
        }

        // 生成した検索結果オブジェクトの数を通知します。
        return count();
    }

    /**
     * パラメータで指定された日付のデータを検索結果エンティティの中から検索します。<br>
     * 該当するデータが見つからない場合には、直前の検索結果エンティティの位置を返します。<br>
     * いずれの検索結果エンティティも見当たらない場合には、-1を返します。
     * @param date 日付(YYYYMMDD)
     * @param histories 検索結果エンティティ
     * @return 該当検索結果エンティティの位置
     */
    /* InventoryHistAccessorのメソッドを使用するように変更
     * ただし、パフォーマンスのことを考えると、このメソッドを使用した方が良いだろう…
     private int searchHistory(String date, InventoryHistoryEntity[] histories)
     {
     // 指定された日付と一致するデータを検索します。
     for (int i = 0; i < histories.length; i++)
     {
     //  指定日付と一致する検索結果エンティティがあれば、この位置を返します。
     if (histories[i].getSummaryDate().equals(date))
     {
     return i;
     }
     //  指定日付よりも新しい(大きい)日付であれば、この位置を返します。
     if (0 < histories[i].getSummaryDate().compareTo(date))
     {
     return i;
     }
     }
     // いずれの検索結果エンティティも存在しなかった場合は-1を返します。
     return -1;
     }
     */

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。<BR>
     * @return リビジョン文字列。<BR>
     */
    public static String getVersion()
    {
        return "$Id: InventoryHistSearchResult.java 87 2008-10-04 03:07:38Z admin $";
    }
}
