// $Id: ShippingHistoryEntity.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.analysis.entity;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;

import jp.co.daifuku.wms.base.entity.ShippingHist;
import jp.co.daifuku.wms.handler.field.FieldName;


/**
 * 出荷履歴テーブルのエンティティクラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2006/06/22</td><td nowrap>清水 正人</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  清水 正人
 * @author  Last commit: $Author: admin $
 */


public class ShippingHistoryEntity
        extends ShippingHist
        implements HistoryEntity
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * <code>serialVersionUID</code>のコメント
     */
    private static final long serialVersionUID = 1L;

    /** カラム定義 (出荷回数) */
    public static final FieldName SHIPPING_CNT = new FieldName(ShippingHist.STORE_NAME, "SHIPPING_CNT");

    /** カラム定義 (出荷先名称) */
    public static final FieldName CUSTOMER_NAME = new FieldName(ShippingHist.STORE_NAME, "CUSTOMER_NAME");

    /** カラム定義 (商品名称) */
    public static final FieldName ITEM_NAME = new FieldName(ShippingHist.STORE_NAME, "ITEM_NAME");

    /** カラム定義 (作業単位数) */
    public static final FieldName WORK_UNIT_QTY = new FieldName(ShippingHist.STORE_NAME, "WORK_UNIT_QTY");

    /** カラム定義 (ケース入数) */
    public static final FieldName ENTERING_QTY = new FieldName(ShippingHist.STORE_NAME, "ENTERING_QTY");

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
     * 更新時に利用されるキーフィールド名一覧
     */
    private final FieldName[] _updateKeyFieldList = {
        ShippingHist.CONSIGNOR_CODE,
        ShippingHist.CUSTOMER_CODE,
        ShippingHist.ITEM_CODE
    };

    private boolean[] _condition = null;

    private BigDecimal _shippingQty = new BigDecimal(0);

    private String _customerName = null;

    private String _itemName = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * カラム名リストを準備しインスタンスを生成します。
     */
    public ShippingHistoryEntity()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * キー項目となるフィールド名リストを取得します。<BR>
     * @return キーフィールド名リスト<BR>
     */
    public FieldName[] getKeyNames()
    {
        FieldName[] keyFieldList = {
            ShippingHist.SUMMARY_DATE,
            ShippingHist.CONSIGNOR_CODE,
            ShippingHist.CUSTOMER_CODE,
            ShippingHist.ITEM_CODE
        };
        return keyFieldList;
    }

    /**
     * 更新キー項目のフィールド名リストを取得します。<BR>
     * @return 更新キーフィールド名リスト<BR>
     */
    public FieldName[] getUpdateKeyNames()
    {
        // 有効キーフィールド数を取得します。
        int count = 0;
        if (null == _condition)
        {
            // 有効キーフィールドが指定されていなければ、全てのキーフィールドが
            // 指定されているものとします。
            return _updateKeyFieldList;
        }
        else
        {
            for (int i = 0; i < _condition.length; i++)
            {
                if (_condition[i])
                {
                    count++;
                }
            }
            // 定義されているキーフィールド数よりも多くの条件設定が行なわれて
            // いれば、全てのキーフィールドが指定されているものとします。
            if (_updateKeyFieldList.length < count)
            {
                return _updateKeyFieldList;
            }
        }

        // 指定されているキーフィールド名をリストアップします。
        int ix = 0;
        FieldName[] updateKey = new FieldName[count];
        for (int i = 0; i < _updateKeyFieldList.length; i++)
        {
            if (_condition[i])
            {
                updateKey[ix] = _updateKeyFieldList[i];
                ix++;
            }
        }

        return updateKey;
    }

    /**
     * 更新対象項目のフィールド名リストを取得します。<BR>
     * @return 更新対象フィールド名リスト<BR>
     */
    public FieldName[] getUpdateFieldNames()
    {
        FieldName[] updateFieldList = {
            ShippingHist.SHIPPING_QTY,
            ShippingHist.WORKING_QTY
        };
        return updateFieldList;
    }

    /**
     * <code>getUpdateFieldNames()</code>で取得した各フィールドについて
     * 加算更新を行なうかどうかを返します。<BR>
     * <code>true</code>が設定されていれば加算更新を行なうものとし、
     * <code>false</code>ならば、上書き更新となります。<BR>
     * @return 加算更新区分<BR>
     */
    public boolean[] getAddCondition()
    {
        // 当日出荷数は加算更新となります。
        boolean[] add = {
            true,
            false
        };
        return add;
    }

    /**
     * カラム名で指定された項目を設定します。<BR>
     * @param column カラム名<BR>
     * @param value 設定値<BR>
     */
    public void setValue(String column, Object value)
    {
        if ("ITEM_CODE".equals(column))
        {
            setItemCode((String)value);
        }
        else if ("CUSTOMER_CODE".equals(column))
        {
            setCustomerCode((String)value);
        }
        else if ("CONSIGNOR_CODE".equals(column))
        {
            setConsignorCode((String)value);
        }
        else if ("SUMMARY_DATE".equals(column))
        {
            setSummaryDate((String)value);
        }
        else if ("SHIPPING_QTY".equals(column))
        {
            BigDecimal qty;
            if (null == value)
            {
                qty = new BigDecimal(0);
            }
            else
            {
                qty = new BigDecimal(String.valueOf(value));
            }
            setBDShippingQty(qty);
        }
        else if ("WORKING_QTY".equals(column))
        {
            int qty;
            if (null == value)
            {
                qty = 0;
            }
            else
            {
                qty = new BigDecimal(String.valueOf(value)).intValue();
            }
            setWorkingQty(qty);
        }
        else if ("SHIPPING_CNT".equals(column))
        {
            if (null == value)
            {
                setShippingCnt(0);
            }
            else
            {
                setShippingCnt(Integer.parseInt(String.valueOf(value)));
            }
        }
        else if ("WORK_UNIT_QTY".equals(column))
        {
            if (null == value)
            {
                setWorkUnitQty(0);
            }
            else
            {
                setWorkUnitQty(Integer.parseInt(String.valueOf(value)));
            }
        }
        else if ("ENTERING_QTY".equals(column))
        {
            if (null == value)
            {
                setEnteringQty(0);
            }
            else
            {
                setEnteringQty(Integer.parseInt(String.valueOf(value)));
            }
        }
        else if ("ITEM_NAME".equals(column))
        {
            setItemName((String)value);
        }
        else if ("CUSTOMER_NAME".equals(column))
        {
            setCustomerName((String)value);
        }
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * CUSTOMER_NAMEに値をセットします。
     * @param value セットする値CUSTOMER_NAME
     */
    public void setCustomerName(String value)
    {
        _customerName = value;
    }

    /**
     * CUSTOMER_NAMEを取得します。
     * @return CUSTOMER_NAME
     */
    public String getCustomerName()
    {
        return _customerName;
    }

    /**
     * ITEM_NAMEに値をセットします。
     * @param value セットする値ITEM_NAME
     */
    public void setItemName(String value)
    {
        _itemName = value;
    }

    /**
     * ITEM_NAMEを取得します。
     * @return ITEM_NAME
     */
    public String getItemName()
    {
        return _itemName;
    }

    /**
     * SHIPPING_QTYに値をセットします。
     * @param value セットする値SHIPPING_QTY
     */
    public void setBDShippingQty(BigDecimal value)
    {
        _shippingQty = value;
    }

    /**
     * SHIPPING_QTYを取得します。
     * @return SHIPPING_QTY
     */
    public BigDecimal getBDShippingQty()
    {
        return _shippingQty;
    }

    /**
     * SHIPPING_CNTに値をセットします。
     * @param value セットする値SHIPPING_CNT
     */
    public void setShippingCnt(int value)
    {
        setValue(SHIPPING_CNT, new BigDecimal(value), false);
    }

    /**
     * SHIPPING_CNTを取得します。
     * @return SHIPPING_CNT
     */
    public int getShippingCnt()
    {
        return getBigDecimal(SHIPPING_CNT).intValue();
    }

    /**
     * WORK_UNIT_QTYに値をセットします。
     * @param value セットする値WORK_UNIT_QTY
     */
    public void setWorkUnitQty(int value)
    {
        setValue(WORK_UNIT_QTY, new BigDecimal(value), false);
    }

    /**
     * WORK_UNIT_QTYを取得します。
     * @return WORK_UNIT_QTY
     */
    public int getWorkUnitQty()
    {
        return getBigDecimal(WORK_UNIT_QTY).intValue();
    }

    /**
     * ENTERING_QTYに値をセットします。
     * @param value セットする値ENTERING_QTY
     */
    public void setEnteringQty(int value)
    {
        setValue(ENTERING_QTY, new BigDecimal(value), false);
    }

    /**
     * ENTERING_QTYを取得します。
     * @return ENTERING_QTY
     */
    public int getEnteringQty()
    {
        return getBigDecimal(ENTERING_QTY).intValue();
    }

    /**
     * 履歴テーブルを更新する際に取り出される検索条件の有効/無効を設定します。<BR>
     * @param enable <code>true</code>を指定したキーを有効とします。<BR>
     */
    public void setUpdateKeyCondition(boolean[] enable)
    {
        _condition = enable;
    }

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

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。<BR>
     * @return リビジョン文字列。<BR>
     */
    public static String getVersion()
    {
        return "$Id: ShippingHistoryEntity.java 87 2008-10-04 03:07:38Z admin $";
    }
}
