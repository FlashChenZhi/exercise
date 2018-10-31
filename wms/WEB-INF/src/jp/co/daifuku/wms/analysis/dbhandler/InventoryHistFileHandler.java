// $Id: InventoryHistFileHandler.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.analysis.dbhandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;

import jp.co.daifuku.wms.analysis.entity.HistoryEntity;
import jp.co.daifuku.wms.analysis.entity.InventoryHistoryEntity;
import jp.co.daifuku.wms.base.entity.InventoryHist;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 在庫履歴テーブルハンドラです。<br>
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


public class InventoryHistFileHandler
        extends AbstractHistoryHandler
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    private static final String INSERT_SQL = " ("
        + "CONSIGNOR_CODE,"
        + "ITEM_CODE,"
        + "LOT_NO,"
        + "SUMMARY_DATE,"
        + "STOCK_QTY,"
        + "STORAGE_QTY,"
        + "RETRIEVAL_QTY,"
        + ")"
        + " VALUES "
        + "(?,?,?,?,?,?,?)";

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
    public InventoryHistFileHandler(Connection conn)
    {
        super(conn);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

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
        return InventoryHist.STORE_NAME;
    }

    /**
     * 履歴情報エンティティを生成します。<BR>
     * @return 履歴情報エンティティ<BR>
     */
    @Override
    protected HistoryEntity createEntity()
    {
        return new InventoryHistoryEntity();
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
     * @param entity 履歴情報<BR>
     * @throws SQLException  データベースアクセスエラーまたはその他のエラーが発生した場合に通知されます。<BR>
     */
    @Override
    protected void setValues(PreparedStatement statement, HistoryEntity entity)
            throws SQLException
    {
        // 履歴情報は、在庫履歴として扱います。
        InventoryHistoryEntity history = (InventoryHistoryEntity)entity;
        int ix = 0;

        // 荷主コード
        statement.setString(++ix, history.getConsignorCode());
        // 商品コード
        statement.setString(++ix, history.getItemCode());
        // ロットNo
        statement.setString(++ix, history.getLotNo());
        // 集計日
        statement.setString(++ix, history.getSummaryDate());
        // 当日在庫数
        statement.setInt(++ix, history.getStockQty());
        // 当日入庫数
        statement.setInt(++ix, history.getStorageQty());
        // 当日出庫数
        statement.setInt(++ix, history.getRetrievalQty());
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
        return "$Id: InventoryHistFileHandler.java 87 2008-10-04 03:07:38Z admin $";
    }
}
