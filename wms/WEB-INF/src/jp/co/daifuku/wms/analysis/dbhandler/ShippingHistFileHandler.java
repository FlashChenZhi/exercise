// $Id: ShippingHistFileHandler.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.analysis.dbhandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.analysis.entity.HistoryEntity;
import jp.co.daifuku.wms.analysis.entity.ShippingHistoryEntity;
import jp.co.daifuku.wms.analysis.operator.HistorySearchParamater;
import jp.co.daifuku.wms.analysis.operator.ShippingHistSearchParameter;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.entity.ShippingHist;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 出荷履歴テーブルハンドラです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2006/06/22</td><td nowrap>清水 正人</td><td>Class created.</td></tr>
 * <tr><td nowrap>2008/01/20</td><td nowrap>Shimizu(Softecs)</td><td>v3.2移行</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  清水 正人
 * @author  Last commit: $Author: admin $
 */

public class ShippingHistFileHandler
        extends AbstractHistoryHandler
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    private static final String INSERT_SQL =
            " (" + "CONSIGNOR_CODE," + "CUSTOMER_CODE," + "ITEM_CODE," + "SUMMARY_DATE," + "SHIPPING_QTY,"
                    + "WORKING_QTY" + ")" + " VALUES " + "(?,?,?,?,?,?)";

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
    public ShippingHistFileHandler(Connection conn)
    {
        super(conn);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 指定された検索条件に従って履歴情報テーブルを検索し、履歴情報を取得します。<BR>
     * @param param 履歴情報検索条件<BR>
     * @return 検索履歴情報<BR>
     * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
     */
    @Override
    public HistoryEntity[] search(HistorySearchParamater param)
            throws ReadWriteException
    {
        HistoryEntity[] historyArr = new HistoryEntity[0];
        Statement statement = null;
        ResultSet resultset = null;

        // 検索SQL文を取得します。
        ShippingHistSearchParameter shippingParam = (ShippingHistSearchParameter)param;
        String strSQL = shippingParam.getSearchSQL();
        if (StringUtil.isBlank(strSQL))
        {
            return historyArr;
        }

        // 検索SQL文を実行し、結果を取得します。
        try
        {
            statement = getConnection().createStatement();
            DEBUG.MSG(DEBUG.HANDLER, strSQL);
            resultset = statement.executeQuery(strSQL);
            historyArr = convertResultSet(resultset);
        }
        catch (SQLException e)
        {
            // Exceptionをロギングします。
            logException(PLACE_SEARCH, e);
        }
        finally
        {
            // 結果セットとステートメントの後始末をします。
            try
            {
                close(resultset, statement);
            }
            catch (SQLException e)
            {
                // Exceptionをロギングします。
                logException(PLACE_SEARCH, e);
            }
        }
        return historyArr;
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
     * 履歴情報テーブルの(末尾の月を表す数字を除いた)テーブル名を取得します。<BR>
     * @return 履歴情報テーブル名<BR>
     */
    @Override
    protected String getTableName()
    {
        return ShippingHist.STORE_NAME;
    }

    /**
     * 履歴情報エンティティを生成します。<BR>
     * @return 履歴情報エンティティ<BR>
     */
    @Override
    protected HistoryEntity createEntity()
    {
        return new ShippingHistoryEntity();
    }

    /**
     * データベース格納バッチ処理を行う為のSQL文を取得します。<BR>
     * パラメータで指定された月によって、テーブルを決定します。<BR>
     * @param month バッチ処理を生成する月<BR>
     * @return バッチ処理用SQL文<BR>
     */
    @Override
    protected String getBatchSQL(int month)
    {
        // 編集対象のテーブル名を取得します。
        DecimalFormat format = new DecimalFormat("00");
        StringBuffer tableName = new StringBuffer(getTableName());
        tableName.append(format.format(month));

        // バッチ処理用SQLを編集します。
        StringBuffer batchSql = new StringBuffer("INSERT INTO ");
        batchSql.append(String.valueOf(tableName));
        batchSql.append(INSERT_SQL);

        return String.valueOf(batchSql);
    }

    /**
     * データベース格納バッチ処理のデータ設定を行ないます。<BR>
     * @param statement バッチ処理用ステートメント<BR>
     * @param entity  履歴情報<BR>
     * @throws SQLException  データベースアクセスエラーまたはその他のエラーが発生した場合に通知されます。<BR>
     */
    @Override
    protected void setValues(PreparedStatement statement, HistoryEntity entity)
            throws SQLException
    {
        // 履歴情報は、出荷履歴として扱います。
        ShippingHistoryEntity history = (ShippingHistoryEntity)entity;
        int ix = 0;

        // 荷主コード
        statement.setString(++ix, history.getConsignorCode());
        // 出荷先コード
        statement.setString(++ix, history.getCustomerCode());
        // 商品コード
        statement.setString(++ix, history.getItemCode());
        // 集計日
        statement.setString(++ix, history.getSummaryDate());
        // 当日出荷数
        statement.setInt(++ix, history.getShippingQty());
        // 当日作業数
        statement.setInt(++ix, history.getWorkingQty());
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
        return "$Id: ShippingHistFileHandler.java 87 2008-10-04 03:07:38Z admin $";
    }
}
