// $Id: InventoryHistSearchParameter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.analysis.operator;

import java.text.DecimalFormat;

import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.entity.InventoryHist;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 在庫履歴情報を検索する条件を指定するクラスです。<br>
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


public class InventoryHistSearchParameter
        implements HistorySearchParamater
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * <code>SUMMARY_TYPE_MONTH</code><br>
     * 集計単位：月<BR>
     */
    public static final String SUMMARY_TYPE_MONTH = "0";

    /**
     * <code>SUMMARY_TYPE_DAY</code><br>
     * 集計単位：日<BR>
     */
    public static final String SUMMARY_TYPE_DAY = "1";

    /**
     * <code>SUMMARY_TYPE_ITEM</code><br>
     * 集計単位：商品（商品一覧用）<BR>
     */
    public static final String SUMMARY_TYPE_ITEM = "2";

    /**
     * <code>SEARCH_TYPE_ITEM_ALL</code><br>
     * 商品一覧用検索条件：全て<BR>
     */
    public static final String SEARCH_TYPE_ITEM_ALL = "1";

    /**
     * <code>SEARCH_TYPE_ITEM_OUTOFRANGE</code><br>
     * 商品一覧用検索条件：上下限在庫範囲外のみ<BR>
     */
    public static final String SEARCH_TYPE_ITEM_OUTOFRANGE = "2";

    /**
     * <code>SEARCH_TYPE_ITEM_LIMITOVER</code><br>
     * 商品一覧用検索条件：上限在庫範囲外のみ<BR>
     */

    public static final String SEARCH_TYPE_ITEM_LIMITOVER = "3";

    /**
     * <code>SEARCH_TYPE_ITEM_LIMITUNDER</code><br>
     * 商品一覧用検索条件：下限在庫範囲外のみ<BR>
     */
    public static final String SEARCH_TYPE_ITEM_LIMITUNDER = "4";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;
    private String p_consignorCode = null;

    private String p_itemCode = null;

    private String p_lotNo = null;

    private String p_areaNo = null;

    private String p_summaryType = SUMMARY_TYPE_ITEM;

    private String p_searchType = SEARCH_TYPE_ITEM_ALL;

    private String p_startDate = null;

    private String p_endDate = null;

    private int p_itemCount = 0;

    private String p_limitcheck = null;

    private boolean p_listboxSearch = false;

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

        // 検索開始年月と検索終了年月が同じ場合、該当月のテーブルのみが検索対象となります。
        if (startYear == endYear && startMon == endMon)
        {
            StringBuffer tableName = new StringBuffer(InventoryHist.STORE_NAME);
            tableName.append(decFormat.format(startMon));
            String[] tableNames = new String[1];
            tableNames[0] = String.valueOf(tableName);
            return tableNames;
        }

        // 検索対象テーブル数を求めます。
        int count = endMon - startMon + 1;
        if (0 >= count)
        {
            count += 12;
        }
        // 1テーブルのみが検索対象となっていても、検索年が異なっていれば12テーブルを
        // 検索対象テーブルとします。
        if (1 == count && startYear != endYear)
        {
            count = 12;
        }
        String[] tableNames = new String[count];

        // 検索対象テーブル名を編集します。
        for (int i = 0; i < count; i++)
        {
            StringBuffer tableName = new StringBuffer(InventoryHist.STORE_NAME);
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
     * 検索条件取得（商品一覧専用）。<BR>
     * 履歴情報を検索する検索条件をSQL文字列として取り出します。<BR>
     * 本メソッドを修正する場合は、getSearchSQL()も同じように修正すること。<BR>
     * @return 検索条件SQL文字列<BR>
     */
    public String getCountSQL()
    {
        String searchType = getSearchType();

        // 検索SQL文を生成します。
        StringBuffer strSQL = new StringBuffer("SELECT ");
        strSQL.append("COUNT(*) AS COUNT");
        strSQL.append(" FROM (");

        strSQL.append("SELECT ");
        strSQL.append("CONSIGNOR_CODE,");
        strSQL.append("ITEM_CODE,");
        strSQL.append("STOCK_QTY,");
        strSQL.append("STOCK_UBQTY,");
        strSQL.append("STOCK_LBQTY,");
        strSQL.append("ITEM_NAME");
        strSQL.append(" FROM (");

        // 検索対象履歴テーブルを取得し検索対象データの集合を生成します。
        String[] tableName = getTargetTables();
        strSQL.append(getSearchSQL(tableName[0]));

        // SQL文を編集します。
        for (int i = 1; i < tableName.length; i++)
        {
            strSQL.append(" UNION ALL ");
            strSQL.append(getSearchSQL(tableName[i]));
        }
        strSQL.append(")");

        if (searchType.equals(SEARCH_TYPE_ITEM_OUTOFRANGE))
        {
            strSQL.append(" WHERE (STOCK_UBQTY>'0' AND STOCK_QTY>STOCK_UBQTY) OR (STOCK_LBQTY>'0' AND STOCK_QTY<STOCK_LBQTY)");
            strSQL.append(")");
        }
        else if (searchType.equals(SEARCH_TYPE_ITEM_LIMITOVER))
        {
            strSQL.append(" WHERE (STOCK_UBQTY>'0' AND STOCK_QTY>STOCK_UBQTY)");
            strSQL.append(")");
        }
        else if (searchType.equals(SEARCH_TYPE_ITEM_LIMITUNDER))
        {
            strSQL.append(" WHERE (STOCK_LBQTY>'0' AND STOCK_QTY<STOCK_LBQTY)");
            strSQL.append(")");
        }

        // グループを設定します。
        strSQL.append(" GROUP BY ");
        strSQL.append("CONSIGNOR_CODE,");
        strSQL.append("ITEM_CODE,");
        strSQL.append("ITEM_NAME");

        // オーダーを設定します。
        strSQL.append(" ORDER BY ");
        strSQL.append("CONSIGNOR_CODE,");
        strSQL.append("ITEM_CODE");

        return String.valueOf(strSQL);
    }

    /**
     * 検索条件取得（商品一覧専用）。<BR>
     * 履歴情報を検索する検索条件をSQL文字列として取り出します。<BR>
     * 本メソッドを修正する場合は、getCountSQL()も同じように修正すること。<BR>
     * @return 検索条件SQL文字列<BR>
     */
    public String getSearchSQL()
    {
        String searchType = getSearchType();

        // 検索SQL文を生成します。
        StringBuffer strSQL = new StringBuffer("SELECT ");
        strSQL.append("CONSIGNOR_CODE,");
        strSQL.append("ITEM_CODE,");
        strSQL.append("ITEM_NAME");
        strSQL.append(",COUNT(*) AS RANGE_OVER");
        strSQL.append(" FROM (");

        strSQL.append("SELECT ");
        strSQL.append("CONSIGNOR_CODE,");
        strSQL.append("ITEM_CODE,");
        strSQL.append("STOCK_QTY,");
        strSQL.append("STOCK_UBQTY,");
        strSQL.append("STOCK_LBQTY,");
        strSQL.append("ITEM_NAME");
        strSQL.append(" FROM (");

        // 検索対象履歴テーブルを取得し検索対象データの集合を生成します。
        String[] tableName = getTargetTables();
        strSQL.append(getSearchSQL(tableName[0]));

        // SQL文を編集します。
        for (int i = 1; i < tableName.length; i++)
        {
            strSQL.append(" UNION ALL ");
            strSQL.append(getSearchSQL(tableName[i]));
        }
        strSQL.append(")");

        if (searchType.equals(SEARCH_TYPE_ITEM_OUTOFRANGE))
        {
            strSQL.append(" WHERE (STOCK_UBQTY>'0' AND STOCK_QTY>STOCK_UBQTY) OR (STOCK_LBQTY>'0' AND STOCK_QTY<STOCK_LBQTY)");
            strSQL.append(")");
        }
        else if (searchType.equals(SEARCH_TYPE_ITEM_LIMITOVER))
        {
            strSQL.append(" WHERE (STOCK_UBQTY>'0' AND STOCK_QTY>STOCK_UBQTY)");
            strSQL.append(")");
        }
        else if (searchType.equals(SEARCH_TYPE_ITEM_LIMITUNDER))
        {
            strSQL.append(" WHERE (STOCK_LBQTY>'0' AND STOCK_QTY<STOCK_LBQTY)");
            strSQL.append(")");
        }

        // グループを設定します。
        strSQL.append(" GROUP BY ");
        strSQL.append("CONSIGNOR_CODE,");
        strSQL.append("ITEM_CODE,");
        strSQL.append("ITEM_NAME");

        // オーダーを設定します。
        strSQL.append(" ORDER BY ");
        strSQL.append("CONSIGNOR_CODE,");
        strSQL.append("ITEM_CODE");

        return String.valueOf(strSQL);
    }

    /**
     * 検索条件取得（在庫推移分析・商品一覧兼用）。<BR>
     * 履歴情報を検索する検索条件をSQL文字列として取り出します。<BR>
     * @param tableName テーブル名<BR>
     * @return 検索条件SQL文字列<BR>
     */
    public String getSearchSQL(String tableName)
    {
        StringBuffer searchSQL = new StringBuffer("SELECT ");
        String summaryType = getSummaryType();

        if (summaryType.equals(SUMMARY_TYPE_ITEM))
        {
            searchSQL.append("DISTINCT ");
        }

        searchSQL.append(tableName);
        searchSQL.append(".CONSIGNOR_CODE AS CONSIGNOR_CODE,");
        searchSQL.append(tableName);
        searchSQL.append(".ITEM_CODE AS ITEM_CODE,");
        searchSQL.append("DMITEM.ITEM_NAME AS ITEM_NAME,");
        searchSQL.append("DMITEM.UPPER_QTY AS STOCK_UBQTY,");
        searchSQL.append("DMITEM.LOWER_QTY AS STOCK_LBQTY");

        // ロットNo.については、
        // "", nullを指定した場合、検索キーとしません。
        {
            // ロットNo.の指定が行われていれば、読み込み設定を行います。
            if (!StringUtil.isBlank(getLotNo()))
            {
                searchSQL.append(",");
                searchSQL.append(tableName);
                searchSQL.append(".LOT_NO AS LOT_NO");
            }
        }

        // 各項目を集計します。
        if (summaryType.equals(SUMMARY_TYPE_MONTH))
        {
            searchSQL.append(",");
            searchSQL.append("SUBSTR(");
            searchSQL.append(tableName);
            searchSQL.append(".SUMMARY_DATE,0,6) AS SUMMARY_DATE");
        }
        else if (summaryType.equals(SUMMARY_TYPE_DAY))
        {
            searchSQL.append(",");
            searchSQL.append(tableName);
            searchSQL.append(".SUMMARY_DATE AS SUMMARY_DATE");
        }

        if (!summaryType.equals(SUMMARY_TYPE_ITEM))
        {
            searchSQL.append(",SUM(");
            searchSQL.append(tableName);
            searchSQL.append(".STOCK_QTY) AS STOCK_QTY");
            searchSQL.append(",SUM(");
            searchSQL.append(tableName);
            searchSQL.append(".STORAGE_QTY) AS STORAGE_QTY");
            searchSQL.append(",SUM(");
            searchSQL.append(tableName);
            searchSQL.append(".RETRIEVAL_QTY) AS RETRIEVAL_QTY");
        }
        else
        {
            searchSQL.append(",SUM(");
            searchSQL.append(tableName);
            searchSQL.append(".STOCK_QTY) AS STOCK_QTY");
        }

        searchSQL.append(" FROM ");
        searchSQL.append(tableName);
        searchSQL.append(", DMITEM");
        searchSQL.append(" WHERE ");

        // 荷主コードの指定が行われていれば、条件の設定を行います。
        String consignorCode = getConsignorCode();
        if (null != consignorCode && consignorCode.length() > 0)
        {
            if (DBFormat.isPatternMatching(consignorCode))
            {
                searchSQL.append(tableName);
                searchSQL.append(".CONSIGNOR_CODE LIKE ");
                searchSQL.append(DBFormat.format(consignorCode.replaceAll("[" + WmsParam.PATTERNMATCHING + "]", "%")));
            }
            else
            {
                searchSQL.append(tableName);
                searchSQL.append(".CONSIGNOR_CODE=");
                searchSQL.append(DBFormat.format(consignorCode));
            }
        }

        // 商品コードの指定が行われていれば、条件の設定を行います。
        String itemCode = getItemCode();
        if (null != itemCode && itemCode.length() > 0)
        {
            if (DBFormat.isPatternMatching(itemCode))
            {
                searchSQL.append(" AND ");
                searchSQL.append(tableName);
                searchSQL.append(".ITEM_CODE LIKE ");
                searchSQL.append(DBFormat.format(itemCode.replaceAll("[" + WmsParam.PATTERNMATCHING + "]", "%")));
            }
            else
            {
                searchSQL.append(" AND ");
                searchSQL.append(tableName);
                if (isListboxSearch())
                {
                    searchSQL.append(".ITEM_CODE>=");
                }
                else
                {
                    searchSQL.append(".ITEM_CODE=");
                }
                searchSQL.append(DBFormat.format(itemCode));
            }
        }

        // ロットNo.については、
        // ""を指定した場合、検索キーとしません。
        // nullを指定した場合、「IS NULL」で検索する。
        {
            // ロットNo.の指定が行われていれば、条件の設定を行います。
            String lotNo = getLotNo();
            if (null != lotNo && !lotNo.equals(""))
            {
                searchSQL.append(" AND ");
                searchSQL.append(tableName);
                searchSQL.append(".LOT_NO=");
                searchSQL.append(DBFormat.format(lotNo));
            }
            else if (null == lotNo)
            {
                searchSQL.append(" AND ");
                searchSQL.append(tableName);
                searchSQL.append(".LOT_NO IS NULL");
            }
        }

        // 開始日付の指定が行われていれば、条件の設定を行います。
        String startDate = getStartDate();
        if (null != startDate)
        {
            searchSQL.append(" AND ");
            searchSQL.append(tableName);
            searchSQL.append(".SUMMARY_DATE>=");
            searchSQL.append(DBFormat.format(startDate));
        }

        // 終了日付の指定が行われていれば、条件の設定を行います。
        String endDate = getEndDate();
        if (null != endDate)
        {
            searchSQL.append(" AND ");
            searchSQL.append(tableName);
            searchSQL.append(".SUMMARY_DATE<=");
            searchSQL.append(DBFormat.format(endDate));
        }

        // 商品マスタの読み込み条件を設定します。
        searchSQL.append(" AND ");
        searchSQL.append("DMITEM.CONSIGNOR_CODE(+)=");
        searchSQL.append(tableName);
        searchSQL.append(".CONSIGNOR_CODE AND ");
        searchSQL.append("DMITEM.ITEM_CODE(+)=");
        searchSQL.append(tableName);
        searchSQL.append(".ITEM_CODE");

        // 集計グループを指定します。
        searchSQL.append(" GROUP BY ");
        searchSQL.append(tableName);
        searchSQL.append(".CONSIGNOR_CODE,");
        searchSQL.append(tableName);
        searchSQL.append(".ITEM_CODE,");
        searchSQL.append("DMITEM.ITEM_NAME,");
        searchSQL.append("DMITEM.UPPER_QTY,");
        searchSQL.append("DMITEM.LOWER_QTY");

        // ロットNo.については、
        // "", nullを指定した場合、集計グループ条件としません。
        {
            // ロットNo.の指定が行われていれば、集計グループ条件設定を行います。
            if (!StringUtil.isBlank(getLotNo()))
            {
                searchSQL.append(",");
                searchSQL.append(tableName);
                searchSQL.append(".LOT_NO");
            }
        }

        if (summaryType.equals(SUMMARY_TYPE_MONTH))
        {
            searchSQL.append(",");
            searchSQL.append("SUBSTR(");
            searchSQL.append(tableName);
            searchSQL.append(".SUMMARY_DATE,0,6)");
        }
        else if (summaryType.equals(SUMMARY_TYPE_DAY) || summaryType.equals(SUMMARY_TYPE_ITEM))
        {
            searchSQL.append(",");
            searchSQL.append(tableName);
            searchSQL.append(".SUMMARY_DATE");
        }

        // 読み込み順を設定します。
        if (!summaryType.equals(SUMMARY_TYPE_ITEM))
        {
            searchSQL.append(" ORDER BY ");
            if (SUMMARY_TYPE_MONTH == summaryType)
            {
                searchSQL.append("SUBSTR(");
                searchSQL.append(tableName);
                searchSQL.append(".SUMMARY_DATE,0,6)");
            }
            else
            {
                searchSQL.append(tableName);
                searchSQL.append(".SUMMARY_DATE");
            }
        }

        return String.valueOf(searchSQL);
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
     * @return itemCodeを返します。<BR>
     */
    public String getItemCode()
    {
        return p_itemCode;
    }

    /**
     * @param itemCode itemCodeを設定します。<BR>
     */
    public void setItemCode(String itemCode)
    {
        p_itemCode = itemCode;
    }

    /**
     * @return lotNoを返します。<BR>
     */
    public String getLotNo()
    {
        return p_lotNo;
    }

    /**
     * @param lotNo lotNoを設定します。<BR>
     */
    public void setLotNo(String lotNo)
    {
        p_lotNo = lotNo;
    }

    /**
     * @return areaNoを返します。<BR>
     */
    public String getAreaNo()
    {
        return p_areaNo;
    }

    /**
     * @param areaNo areaNoを設定します。<BR>
     */
    public void setAreaNo(String areaNo)
    {
        p_areaNo = areaNo;
    }

    /**
     * @return summaryTypeを返します。<BR>
     */
    public String getSummaryType()
    {
        return p_summaryType;
    }

    /**
     * @param summaryType summaryTypeを設定します。<BR>
     */
    public void setSummaryType(String summaryType)
    {
        p_summaryType = summaryType;
    }

    /**
     * @return searchTypeを返します。<BR>
     */
    public String getSearchType()
    {
        return p_searchType;
    }

    /**
     * @param searchType searchTypeを設定します。<BR>
     */
    public void setSearchType(String searchType)
    {
        p_searchType = searchType;
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

    /**
     * @return itemCountを返します。<BR>
     */
    public int getItemCount()
    {
        return p_itemCount;
    }

    /**
     * @param cnt itemCountを設定します。<BR>
     */
    public void setItemCount(int cnt)
    {
        p_itemCount = cnt;
    }

    /**
     * @return limitcheckを返します。<BR>
     */
    public String getLimitCheck()
    {
        return p_limitcheck;
    }

    /**
     * @param limitcheck limitcheckを設定します。<BR>
     */
    public void setLimitCheck(String limitcheck)
    {
        p_limitcheck = limitcheck;
    }

    /**
     * @return リストボックス用検索指定が行われているか返します。
     */
    public boolean isListboxSearch()
    {
        return p_listboxSearch;
    }

    /**
     * @param listboxSearch リストボックス用検索指定を行う場合は<code>true</code>を指定します。
     */
    public void setListboxSearch(boolean listboxSearch)
    {
        p_listboxSearch = listboxSearch;
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
        return "$Id: InventoryHistSearchParameter.java 87 2008-10-04 03:07:38Z admin $";
    }
}
