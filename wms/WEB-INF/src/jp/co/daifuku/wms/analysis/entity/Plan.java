//$Id: Plan.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.analysis.entity;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;

/**
 * Planのエンティティクラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2006/11/06</td><td nowrap>Y.Okamura</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class Plan
        extends AbstractEntity
{
    /** <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;

    // Class variables -----------------------------------------------
    /** テーブル名定義 */
    public static final String STORE_NAME = "PLAN";

    /** カラム定義 (PLAN_DATE) */
    public static final FieldName PLANDATE = new FieldName(STORE_NAME, "PLAN_DAY");

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods -----------------------------------------------
    /**
     * 作業日に値をセットします。
     * @param arg セットする作業日
     */
    public void setPlanDate(String arg)
    {
        setValue(PLANDATE, arg);
    }

    /**
     * 作業日を取得します。
     * @return 作業日
     */
    public String getPlanDate()
    {
        return String.valueOf(getValue(Plan.PLANDATE));
    }

    /**
     * テーブル名を返します。（使用していません）
     * 
     * @return テーブル名
     */
    public String getTablename()
    {
        return "";
    }

    /**
     * {@inheritDoc}
     */
    public StoreMetaData getStoreMetaData()
    {
        // 何もしません。
        return null;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
