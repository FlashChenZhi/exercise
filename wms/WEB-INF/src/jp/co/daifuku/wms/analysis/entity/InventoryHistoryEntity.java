// $Id: InventoryHistoryEntity.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.analysis.entity;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;

import jp.co.daifuku.wms.base.entity.InventoryHist;
import jp.co.daifuku.wms.handler.field.FieldName;


/**
 * 在庫履歴テーブルのエンティティクラスです。<br>
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

public class InventoryHistoryEntity
        extends InventoryHist
        implements HistoryEntity, Comparable<InventoryHistoryEntity>
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;

    /** カラム定義 (STOCK_UBQTY) */
    public static final FieldName STOCK_UBQTY = new FieldName(InventoryHist.STORE_NAME, "STOCK_UBQTY");

    /** カラム定義 (STOCK_LBQTY) */
    public static final FieldName STOCK_LBQTY = new FieldName(InventoryHist.STORE_NAME, "STOCK_LBQTY");

    /** カラム定義 (ITEM_NAME) */
    public static final FieldName ITEM_NAME = new FieldName(InventoryHist.STORE_NAME, "ITEM_NAME");

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;
    /**
     * 更新時に利用されるキーフィールド名一覧
     */
    private static FieldName[] $updateKeyFieldList = {
        InventoryHist.CONSIGNOR_CODE,
        InventoryHist.ITEM_CODE,
        InventoryHist.LOT_NO
    };

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    private boolean[] _condition = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * カラム名リストを準備しインスタンスを生成します。
     */
    public InventoryHistoryEntity()
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
            InventoryHist.SUMMARY_DATE,
            InventoryHist.CONSIGNOR_CODE,
            InventoryHist.ITEM_CODE,
            InventoryHist.LOT_NO
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
            return $updateKeyFieldList;
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
            if ($updateKeyFieldList.length < count)
            {
                return $updateKeyFieldList;
            }
        }

        // 指定されているキーフィールド名をリストアップします。
        int ix = 0;
        FieldName[] updateKey = new FieldName[count];
        for (int i = 0; i < $updateKeyFieldList.length; i++)
        {
            if (_condition[i])
            {
                updateKey[ix] = $updateKeyFieldList[i];
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
            InventoryHist.STOCK_QTY,
            InventoryHist.STORAGE_QTY,
            InventoryHist.RETRIEVAL_QTY
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
        // 当日在庫数のみ上書き更新となります。
        boolean[] add = {
            false,
            true,
            true
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
        else if ("ITEM_NAME".equals(column))
        {
            setItemName((String)value);
        }
        else if ("LOT_NO".equals(column))
        {
            setLotNo((String)value);
        }
        else if ("CONSIGNOR_CODE".equals(column))
        {
            setConsignorCode((String)value);
        }
        else if ("SUMMARY_DATE".equals(column))
        {
            setSummaryDate((String)value);
        }
        else if ("STOCK_QTY".equals(column))
        {
            if (null == value)
            {
                setStockQty(0);
            }
            else
            {
                setStockQty(Integer.parseInt(String.valueOf(value)));
            }
        }
        else if ("STORAGE_QTY".equals(column))
        {
            if (null == value)
            {
                setStorageQty(0);
            }
            else
            {
                setStorageQty(Integer.parseInt(String.valueOf(value)));
            }
        }
        else if ("RETRIEVAL_QTY".equals(column))
        {
            if (null == value)
            {
                setRetrievalQty(0);
            }
            else
            {
                setRetrievalQty(Integer.parseInt(String.valueOf(value)));
            }
        }
        else if ("STOCK_UBQTY".equals(column))
        {
            if (null == value)
            {
                setStockUbqty(0);
            }
            else
            {
                setStockUbqty(Integer.parseInt(String.valueOf(value)));
            }
        }
        else if ("STOCK_LBQTY".equals(column))
        {
            if (null == value)
            {
                setStockLbqty(0);
            }
            else
            {
                setStockLbqty(Integer.parseInt(String.valueOf(value)));
            }
        }
    }

    /**
     * 在庫推移分析で直前の集計データを検索するために集計日でソートするようにします。
     * @param o 比較対象データ
     * @return 自集計日が大きい場合は正数を、同じ場合はゼロ、小さい場合は負数をそれぞれ返します。
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(InventoryHistoryEntity o)
    {
        String mySummaryDate = getSummaryDate();

        if (null == o)
        {
            if (null == mySummaryDate)
            {
                return 0;
            }
            return 1;
        }

        String targetSummaryDate = o.getSummaryDate();
        if (null == targetSummaryDate)
        {
            if (null == mySummaryDate)
            {
                return 0;
            }
            return 1;
        }
        if (null == mySummaryDate)
        {
            return -1;
        }
        return mySummaryDate.compareTo(targetSummaryDate);
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * STOCK_UBQTYに値をセットします。
     * @param value セットする値STOCK_UBQTY
     */
    public void setStockUbqty(int value)
    {
        setValue(STOCK_UBQTY, new BigDecimal(value), false);
    }

    /**
     * STOCK_UBQTYを取得します。
     * @return STOCK_UBQTY
     */
    public int getStockUbqty()
    {
        return getBigDecimal(STOCK_UBQTY).intValue();
    }

    /**
     * STOCK_LBQTYに値をセットします。
     * @param value セットする値STOCK_LBQTY
     */
    public void setStockLbqty(int value)
    {
        setValue(STOCK_LBQTY, new BigDecimal(value), false);
    }

    /**
     * STOCK_LBQTYを取得します。
     * @return STOCK_LBQTY
     */
    public int getStockLbqty()
    {
        return getBigDecimal(STOCK_LBQTY).intValue();
    }

    /**
     * ITEM_NAMEに値をセットします。
     * @param value セットする値ITEM_NAME
     */
    public void setItemName(String value)
    {
        setValue(ITEM_NAME, value, false);
    }

    /**
     * ITEM_NAMEを取得します。
     * @return ITEM_NAME
     */
    public String getItemName()
    {
        return String.valueOf(getValue(ITEM_NAME, ""));
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
        return "$Id: InventoryHistoryEntity.java 87 2008-10-04 03:07:38Z admin $";
    }
}
