// $Id: ShippingHistSearchResult.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.analysis.operator;

import jp.co.daifuku.wms.analysis.entity.HistoryEntity;
import jp.co.daifuku.wms.analysis.entity.ShippingHistoryEntity;
import jp.co.daifuku.wms.analysis.schedule.AnalysisInParameter;
import jp.co.daifuku.wms.base.common.WmsParam;


/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 出荷履歴データ検索結果クラスです。<br>
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


public class ShippingHistSearchResult
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
    private class ShippingHistResult
    {
        /**
         * 出荷先コード
         */
        private String _customerCode = null;

        /**
         * 出荷先名称
         */
        private String _customerName = null;

        /**
         * 商品コード
         */
        private String _itemCode = null;

        /**
         * 商品名称
         */
        private String _itemName = null;

        /**
         * 出荷数
         */
        private long _shippingQty = 0;

        /**
         * 作業数
         */
        private long _workQty = 0;

        /**
         * 出荷回数
         */
        private long _shippingCnt = 0;

        /**
         * 作業単位数
         */
        private long _workUnitQty = 0;

        /**
         * customerCodeを返します。
         * @return customerCodeを返します。
         */
        public String getCustomerCode()
        {
            return _customerCode;
        }

        /**
         * customerCodeを設定します。
         * @param customerCode customerCode
         */
        public void setCustomerCode(String customerCode)
        {
            _customerCode = customerCode;
        }

        /**
         * customerNameを返します。
         * @return customerNameを返します。
         */
        public String getCustomerName()
        {
            return _customerName;
        }

        /**
         * customerNameを設定します。
         * @param customerName customerName
         */
        public void setCustomerName(String customerName)
        {
            _customerName = customerName;
        }

        /**
         * itemCodeを返します。
         * @return itemCodeを返します。
         */
        public String getItemCode()
        {
            return _itemCode;
        }

        /**
         * itemCodeを設定します。
         * @param itemCode itemCode
         */
        public void setItemCode(String itemCode)
        {
            _itemCode = itemCode;
        }

        /**
         * itemNameを返します。
         * @return itemNameを返します。
         */
        public String getItemName()
        {
            return _itemName;
        }

        /**
         * itemNameを設定します。
         * @param itemName itemName
         */
        public void setItemName(String itemName)
        {
            _itemName = itemName;
        }

        /**
         * shippingCntを返します。
         * @return shippingCntを返します。
         */
        public long getShippingCnt()
        {
            return _shippingCnt;
        }

        /**
         * shippingCntを設定します。
         * @param shippingCnt shippingCnt
         */
        public void setShippingCnt(long shippingCnt)
        {
            _shippingCnt = shippingCnt;
        }

        /**
         * shippingQtyを返します。
         * @return shippingQtyを返します。
         */
        public long getShippingQty()
        {
            return _shippingQty;
        }

        /**
         * shippingQtyを設定します。
         * @param shippingQty shippingQty
         */
        public void setShippingQty(long shippingQty)
        {
            _shippingQty = shippingQty;
        }

        /**
         * workQtyを返します。
         * @return workQtyを返します。
         */
        public long getWorkQty()
        {
            return _workQty;
        }

        /**
         * workQtyを設定します。
         * @param workQty workQty
         */
        public void setWorkQty(long workQty)
        {
            _workQty = workQty;
        }

        /**
         * workUnitQtyを返します。
         * @return workUnitQtyを返します。
         */
        public long getWorkUnitQty()
        {
            return _workUnitQty;
        }

        /**
         * workUnitQtyを設定します。
         * @param workUnitQty workUnitQty
         */
        public void setWorkUnitQty(long workUnitQty)
        {
            _workUnitQty = workUnitQty;
        }
    }

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ
     */
    public ShippingHistSearchResult()
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
        // 履歴データが1件も無ければ、何もしません。
        if (null == data || 0 == data.length)
        {
            return;
        }

        // 履歴データを検索結果オブジェクトとして格納します。
        for (int i = 0; i < data.length; i++)
        {
            ShippingHistoryEntity entity = (ShippingHistoryEntity)data[i];
            ShippingHistResult result = new ShippingHistResult();
            result.setCustomerCode(entity.getCustomerCode());
            result.setItemCode(entity.getItemCode());
            result.setShippingQty(entity.getBDShippingQty().longValue());
            result.setCustomerName(entity.getCustomerName());
            result.setItemName(entity.getItemName());
            result.setShippingCnt(entity.getShippingCnt());

            // 作業数を算出します。
            int unitQty = entity.getWorkUnitQty();
            if (0 == unitQty)
            {
                // 作業単位数の設定が行なわれていなければ、デフォルトの作業単位数を取得します。
                if (WmsParam.WORK_UNIT_TYPE == AnalysisInParameter.WORK_UNIT_TYPE_CASE)
                {
                    // デフォルトの作業単位数がケース入数の場合
                    unitQty = entity.getEnteringQty();
                    if (0 == unitQty)
                    {
                        // ケース入数が未設定の場合は１とします。
                        unitQty = 1;
                    }
                }
                else
                {
                    unitQty = 1;
                }
            }

            result.setWorkUnitQty(unitQty);
            double workQty = (double)result.getShippingQty() / (double)unitQty;
            result.setWorkQty((long)Math.ceil(workQty));

            addResult(result);
        }
    }

    /**
     * パラメータで指定された検索結果オブジェクトより出荷先コードを取得します。<BR>
     * @param data 検索結果オブジェクト<BR>
     * @return 出荷先コード<BR>
     */
    public String getCustomerCode(Object data)
    {
        ShippingHistResult result = (ShippingHistResult)data;
        return result.getCustomerCode();
    }

    /**
     * パラメータで指定された検索結果オブジェクトより出荷先名称を取得します。<BR>
     * @param data 検索結果オブジェクト<BR>
     * @return 出荷先名称<BR>
     */
    public String getCustomerName(Object data)
    {
        ShippingHistResult result = (ShippingHistResult)data;
        return result.getCustomerName();
    }

    /**
     * パラメータで指定された検索結果オブジェクトより商品コードを取得します。<BR>
     * @param data 検索結果オブジェクト<BR>
     * @return 商品コード<BR>
     */
    public String getItemCode(Object data)
    {
        ShippingHistResult result = (ShippingHistResult)data;
        return result.getItemCode();
    }

    /**
     * パラメータで指定された検索結果オブジェクトより商品名称を取得します。<BR>
     * @param data 検索結果オブジェクト<BR>
     * @return 商品名称<BR>
     */
    public String getItemName(Object data)
    {
        ShippingHistResult result = (ShippingHistResult)data;
        return result.getItemName();
    }

    /**
     * パラメータで指定された検索結果オブジェクトより出荷数を取得します。<BR>
     * @param data 検索結果オブジェクト<BR>
     * @return 出荷数<BR>
     */
    public long getShippingQty(Object data)
    {
        ShippingHistResult result = (ShippingHistResult)data;
        return result.getShippingQty();
    }

    /**
     * パラメータで指定された検索結果オブジェクトより作業数を取得します。<BR>
     * @param data 検索結果オブジェクト<BR>
     * @return 作業数<BR>
     */
    public long getWorkQty(Object data)
    {
        ShippingHistResult result = (ShippingHistResult)data;
        return result.getWorkQty();
    }

    /**
     * パラメータで指定された検索結果オブジェクトより出荷回数を取得します。<BR>
     * @param data 検索結果オブジェクト<BR>
     * @return 出荷回数<BR>
     */
    public long getShippingCnt(Object data)
    {
        ShippingHistResult result = (ShippingHistResult)data;
        return result.getShippingCnt();
    }

    /**
     * パラメータで指定された検索結果オブジェクトより作業単位数を取得します。<BR>
     * @param data 検索結果オブジェクト<BR>
     * @return 作業単位数<BR>
     */
    public long getWorkUnitQty(Object data)
    {
        ShippingHistResult result = (ShippingHistResult)data;
        return result.getWorkUnitQty();
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
     * 検索結果オブジェクトを生成します。<BR>
     * @return 検索結果オブジェクト<BR>
     */
    @Override
    protected Object createResult()
    {
        return new ShippingHistResult();
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。<BR>
     * @return リビジョン文字列。<BR>
     */
    public static String getVersion()
    {
        return "$Id: ShippingHistSearchResult.java 87 2008-10-04 03:07:38Z admin $";
    }
}
