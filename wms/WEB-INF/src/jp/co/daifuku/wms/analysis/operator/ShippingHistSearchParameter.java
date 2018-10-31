// $Id: ShippingHistSearchParameter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.analysis.operator;

import java.text.DecimalFormat;

import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.wms.base.entity.ShippingHist;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 出荷履歴情報を検索する条件を指定するクラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2006/06/28</td><td nowrap>Softecs</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Softecs
 * @author  Last commit: $Author: admin $
 */


public class ShippingHistSearchParameter
        implements HistorySearchParamater
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
    private String p_consignorCode = null;

    private String p_customerCode = null;

    private String p_startDate = null;

    private String p_endDate = null;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 検索対象となる履歴テーブルの名称を取得します。<BR>
     * @return 検索対象履歴テーブル名<BR>
     */
    public String[] getTargetTables()
    {
        // 末尾の数値を整形するフォーマッタを準備します。
        DecimalFormat decFormat = new DecimalFormat("00");

        // 検索開始日付、検索終了日付より検索対象となるテーブルを決定します。
        int startYear = Integer.parseInt(getStartDate().substring(0, 4));
        int endYear = Integer.parseInt(getEndDate().substring(0, 4));
        int startMon = Integer.parseInt(getStartDate().substring(4, 6));
        int endMon = Integer.parseInt(getEndDate().substring(4, 6));

        // 開始分析対象期間>終了分析対象期間のときは、検索対象テーブルを返しません。
        if (startYear == endYear && startMon > endMon)
        {
            String[] tableNames = new String[0];
            return tableNames;
        }

        // 下記いずれかの場合は12テーブルが対象
        // 検索年の差が2年以上
        // 検索年の差が1年で検索開始月-1が検索終了月以上
        int count = 0;
        if ((endYear - startYear >= 2) || ((endYear - startYear == 1) && (startMon - 1 <= endMon)))
        {
            count = 12;
        }
        // 検索年の差が1年で12ヶ月以内の場合
        else if (endYear - startYear == 1)
        {
            count = (13 - startMon) + endMon;
        }
        // 検索年が同一年の場合
        else
        {
            count = endMon - startMon + 1;
        }

        // 検索対象テーブル名を編集します。
        String[] tableNames = new String[count];
        for (int i = 0; i < count; i++)
        {
            StringBuffer tableName = new StringBuffer(ShippingHist.STORE_NAME);
            tableName.append(decFormat.format(startMon));
            tableNames[i] = String.valueOf(tableName);
            startMon++;
            if (12 < startMon)
            {
                startMon -= 12;
            }
        }

        return tableNames;
    }

    /**
     * 履歴情報を検索する検索条件をSQL文字列として取り出します。<BR>
     * @param tableName テーブル名<BR>
     * @return 検索条件SQL文字列<BR>
     */
    public String getSearchSQL(String tableName)
    {
        StringBuffer searchSQL = new StringBuffer("SELECT ");

        searchSQL.append(tableName);
        searchSQL.append(".ITEM_CODE AS ITEM_CODE,");

        searchSQL.append("SUM(");
        searchSQL.append(tableName);
        searchSQL.append(".WORKING_QTY) AS WORKING_QTY,");

        searchSQL.append("SUM(");
        searchSQL.append(tableName);
        searchSQL.append(".SHIPPING_QTY) AS SHIPPING_QTY,");

        searchSQL.append("COUNT(*) AS SHIPPING_CNT,");
        /*
         searchSQL.append("COUNT(DISTINCT ");
         searchSQL.append(tableName);
         searchSQL.append(".CUSTOMER_CODE) AS SHIPPING_CNT,");
         */

        searchSQL.append("DMWORKINGUNIT.WORK_UNIT_QTY AS WORK_UNIT_QTY,");
        searchSQL.append("DMITEM.ENTERING_QTY AS ENTERING_QTY,");

        searchSQL.append("DMITEM.ITEM_NAME AS ITEM_NAME");

        searchSQL.append(" FROM ");
        searchSQL.append(tableName);

        searchSQL.append(", DMWORKINGUNIT");
        searchSQL.append(", DMITEM");
        searchSQL.append(" WHERE ");

        // 荷主コードの指定が行われていれば、条件の設定を行います。
        String consignorCode = getConsignorCode();
        if (null != consignorCode && !consignorCode.equals(""))
        {
            searchSQL.append(tableName);
            searchSQL.append(".CONSIGNOR_CODE=");
            searchSQL.append(DBFormat.format(consignorCode));
            searchSQL.append(" AND ");
        }

        // 出荷先コードの指定が行われていれば、条件の設定を行います。
        String customerCode = getCustomerCode();
        if (null != customerCode && !customerCode.equals(""))
        {
            searchSQL.append(tableName);
            searchSQL.append(".CUSTOMER_CODE=");
            searchSQL.append(DBFormat.format(customerCode));
            searchSQL.append(" AND ");
        }

        // 開始日付の指定が行われていれば、条件の設定を行います。
        String startDate = getStartDate();
        if (null != startDate)
        {
            searchSQL.append(tableName);
            searchSQL.append(".SUMMARY_DATE>=");
            searchSQL.append(DBFormat.format(startDate));
            searchSQL.append(" AND ");
        }

        // 終了日付の指定が行われていれば、条件の設定を行います。
        String endDate = getEndDate();
        if (null != endDate)
        {
            searchSQL.append(tableName);
            searchSQL.append(".SUMMARY_DATE<=");
            searchSQL.append(DBFormat.format(endDate));
            searchSQL.append(" AND ");
        }

        // 作業単位数マスタの読み込み条件を設定します。
        searchSQL.append("DMWORKINGUNIT.CONSIGNOR_CODE(+)=");
        searchSQL.append(tableName);
        searchSQL.append(".CONSIGNOR_CODE AND ");
        searchSQL.append("DMWORKINGUNIT.ITEM_CODE(+)=");
        searchSQL.append(tableName);
        searchSQL.append(".ITEM_CODE AND ");

        // 商品マスタの読込条件を設定します。
        searchSQL.append("DMITEM.CONSIGNOR_CODE(+)=");
        searchSQL.append(tableName);
        searchSQL.append(".CONSIGNOR_CODE AND ");
        searchSQL.append("DMITEM.ITEM_CODE(+)=");
        searchSQL.append(tableName);
        searchSQL.append(".ITEM_CODE");

        // 集計グループの設定を行います。
        searchSQL.append(" GROUP BY ");
        searchSQL.append(tableName);
        searchSQL.append(".ITEM_CODE,");
        searchSQL.append("DMWORKINGUNIT.WORK_UNIT_QTY,");
        searchSQL.append("DMITEM.ENTERING_QTY,");
        searchSQL.append("DMITEM.ITEM_NAME");

        return String.valueOf(searchSQL);
    }

    /**
     * 履歴情報を検索する検索条件をSQL文字列として取り出します。<BR>
     * @return 検索条件SQL文字列<BR>
     */
    public String getSearchSQL()
    {
        // 検索SQL文を生成します。
        StringBuffer strSQL = new StringBuffer("SELECT ");
        strSQL.append("ITEM_CODE,");
        strSQL.append("SUM(WORKING_QTY) AS WORKING_QTY,");
        strSQL.append("SUM(SHIPPING_QTY) AS SHIPPING_QTY,");
        strSQL.append("SUM(SHIPPING_CNT) AS SHIPPING_CNT,");
        strSQL.append("WORK_UNIT_QTY,");
        strSQL.append("ENTERING_QTY,");
        strSQL.append("ITEM_NAME");
        strSQL.append(" FROM (");

        // 検索対象履歴テーブルを取得し検索対象データの集合を生成します。
        String[] tableName = getTargetTables();
        if (0 == tableName.length)
        {
            // 検索対象履歴テーブルがない場合は、空文字を返します。
            return "";
        }
        strSQL.append(getSearchSQL(tableName[0]));

        // SQL文を編集します。
        for (int i = 1; i < tableName.length; i++)
        {
            strSQL.append(" UNION ALL ");
            strSQL.append(getSearchSQL(tableName[i]));
        }
        strSQL.append(")");

        // 読込順を設定します。
        strSQL.append(" GROUP BY ");
        strSQL.append("ITEM_CODE,");
        strSQL.append("WORK_UNIT_QTY,");
        strSQL.append("ENTERING_QTY,");
        strSQL.append("ITEM_NAME");
        strSQL.append(" ORDER BY ");
        strSQL.append("ITEM_CODE");

        return String.valueOf(strSQL);
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * @return consignorCodeを返します。<BR>
     */
    public String getConsignorCode()
    {
        return p_consignorCode;
    }

    /**
     * @param consignorCode consignorCodeを設定します。<BR>
     */
    public void setConsignorCode(String consignorCode)
    {
        p_consignorCode = consignorCode;
    }

    /**
     * @return customerCodeを返します。<BR>
     */
    public String getCustomerCode()
    {
        return p_customerCode;
    }

    /**
     * @param customerCode customerCodeを設定します。<BR>
     */
    public void setCustomerCode(String customerCode)
    {
        p_customerCode = customerCode;
    }

    /**
     * @return startDateを返します。<BR>
     */
    public String getStartDate()
    {
        return p_startDate;
    }

    /**
     * @param startDate startDateを設定します。<BR>
     */
    public void setStartDate(String startDate)
    {
        p_startDate = startDate;
    }

    /**
     * @return endDateを返します。<BR>
     */
    public String getEndDate()
    {
        return p_endDate;
    }

    /**
     * @param endDate endDateを設定します。<BR>
     */
    public void setEndDate(String endDate)
    {
        p_endDate = endDate;
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
        return "$Id: ShippingHistSearchParameter.java 87 2008-10-04 03:07:38Z admin $";
    }
}
