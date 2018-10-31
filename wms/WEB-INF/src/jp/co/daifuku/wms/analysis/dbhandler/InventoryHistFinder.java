// $Id: InventoryHistFinder.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.analysis.dbhandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.analysis.entity.InventoryHistoryEntity;
import jp.co.daifuku.wms.analysis.operator.InventoryHistAccessor;
import jp.co.daifuku.wms.analysis.operator.InventoryHistSearchParameter;
import jp.co.daifuku.wms.analysis.schedule.AnalysisInParameter;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.DatabaseFinder;
import jp.co.daifuku.wms.handler.db.SQLErrors;
import jp.co.daifuku.wms.handler.field.FieldName;


/**
 * <CODE>InventoryHistFinder</CODE>クラスは、InventoryHistテーブルより商品一覧を取得するためのクラスです。<BR>
 * InventoryHistテーブルは、月ごとに分かれた特殊なテーブルなので、AbstractDBFinderクラスを流用して作成しました。
 * <BR>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2006/06/22</td><td nowrap>Y.Kato</td><td>新規作成</td></tr>
 * <tr><td nowrap>2008/01/20</td><td nowrap>Shimizu(Softecs)</td><td>v3.2移行</td></tr>
 *
 * @version $Revision, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Y.Kato
 * @author  Last commit: $Author: admin $
 */
public class InventoryHistFinder
        implements DatabaseFinder
{
    /**
     * 参照テーブル名
     */
    private static final String TABLENAME = "DNInventoryHist";

    /**
     * 商品一覧検索範囲（作業日－ｎヶ月）
     */
    private static final int SEARCH_RANGE_OFFSET = 3;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    /**
     * 検索結果を保持する変数。
     */
    private ResultSet _resultSet = null;

    /**
     * データベース接続用のConnectionインスタンス。<BR>
     * トランザクション管理は、このクラスでは行わない。
     */
    private Connection _conn;

    // Class variables -----------------------------------------------

    // Constructors --------------------------------------------------
    /**
     * データベースコネクションオブジェクトを指定してインスタンス化します。
     *
     * @param conn データベースコネクションオブジェクト
     */
    public InventoryHistFinder(Connection conn)
    {
        setConnection(conn);
    }

    // Class method --------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: InventoryHistFinder.java 87 2008-10-04 03:07:38Z admin $";
    }

    // Public methods ------------------------------------------------
    /**
     * データベース接続用の<code>Connection</code>を設定します。
     * @param conn 設定するConnection
     */
    public void setConnection(Connection conn)
    {
        _conn = conn;
    }

    /**
     * データベース接続用の<code>Connection</code>を取得します。
     * @return 現在保持している<code>Connection</code>
     */
    public Connection getConnection()
    {
        return (_conn);
    }

    /**
     * {@inheritDoc}
     */
    public void open()
    {
        // 何もしません
    }

    /**
     * 結果セットをマッピングします。
     *
     * @param start 検索結果の指定された開始位置
     * @param end 検索結果の指定された終了位置
     * @return エンティティ配列
     * @throws ReadWriteException データベース接続で発生した例外をそのまま通知します。
     */
    public Entity[] getEntities(int start, int end)
            throws ReadWriteException
    {
        Vector<AbstractEntity> entityList = new Vector<AbstractEntity>();
        AbstractEntity tmpEntity = createEntity();
        try
        {
            // 表示件数
            int count = end - start;
            if (_resultSet.absolute(start + 1))
            {
                for (int lc = 0; lc < count; lc++)
                {
                    if (lc > 0)
                    {
                        _resultSet.next();
                    }
                    for (int i = 1; i <= _resultSet.getMetaData().getColumnCount(); i++)
                    {
                        String colname = _resultSet.getMetaData().getColumnName(i);
                        FieldName field = new FieldName(TABLENAME, colname);

                        // 日付時刻のクラスはgetTimestamp()でないとミリ秒を取り漏らすことがある。
                        Object value = _resultSet.getObject(i);
                        if (value instanceof java.util.Date)
                        {
                            value = _resultSet.getTimestamp(i);
                        }

                        tmpEntity.setValue(field, value, false);
                    }
                    entityList.addElement(tmpEntity);

                    tmpEntity = createEntity();
                }

                // テーブル毎のEntity[]へキャストできるようにします。
                AbstractEntity[] entityArr =
                        (AbstractEntity[])java.lang.reflect.Array.newInstance(tmpEntity.getClass(), entityList.size());
                entityList.copyInto(entityArr);
                return entityArr;
            }
            else
            {
                // 指定された行が正しくありません。
                RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, this.getClass().getName(), null);
                throw new ReadWriteException();
            }
        }
        catch (SQLException e)
        {
            // 6006002 = データベースエラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
            // ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。
            // 6006039 = {0}の検索に失敗しました。ログを参照して下さい。
            //throw (new ReadWriteException("6006039" + MessageResource.DELIM + p_TableName));
            throw new ReadWriteException(e);
        }
    }

    /**
     * このファインダーで使用するエンティティを生成します。
     * @return エンティティ
     */
    protected AbstractEntity createEntity()
    {
        return new InventoryHistoryEntity();
    }

    /**
     * ステートメントをクローズします。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public void close()
            throws ReadWriteException
    {
        try
        {
            if (_resultSet != null)
            {
                _resultSet.close();
                _resultSet = null;
            }
        }
        catch (SQLException e)
        {
            // 6006002 = データベースエラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
            throw (new ReadWriteException(e));
        }
    }

    /**
     * ダミー実装。
     * @param key 検索のためのKey
     * @return 検索結果の件数
     */
    public int search(SearchKey key)
    {
        return 0;
    }

    /**
     * データベースを検索し、取得します。
     * @param key 検索のためのKey
     * @return 検索結果の件数
     * @throws CommonException データベースとの接続で発生した例外
     * またはWarenaviSystemControllerの初期化に失敗したとき通知します。
     */
    public int search(AnalysisInParameter key)
            throws CommonException
    {
        InventoryHistAccessor histAccessor = new InventoryHistAccessor(_conn);
        InventoryHistSearchParameter paramHist = new InventoryHistSearchParameter();

        // 検索パラメータをセットします。
        paramHist.setSummaryType(InventoryHistSearchParameter.SUMMARY_TYPE_ITEM);
        paramHist.setSearchType(key.getItemListCondition());
        paramHist.setConsignorCode(key.getConsignorCode());
        paramHist.setItemCode(key.getItemCode());
        paramHist.setListboxSearch(key.isListboxSearch());
        // ""（空文字列）をセットすることで全件対象となる。（InventoryHistSearchParameterの仕様）
        paramHist.setLotNo("");

        // 検索範囲を設定します。
        String[] strDate = getWorkDate(SEARCH_RANGE_OFFSET);
        paramHist.setStartDate(strDate[1]);
        paramHist.setEndDate(strDate[0]);

        _resultSet = histAccessor.getItemList(paramHist);
        return paramHist.getItemCount();
    }

    /** 
     * 作業日とｎヶ月前の日付文字列を返す。
     * 
     * @param offset ｎヶ月前のｎ
     * @return [0]：作業日 [1]：ｎヶ月前の日付
     * @throws CommonException WarenaviSystemControllerの初期化に失敗したときスローされます。
     */
    private String[] getWorkDate(int offset)
            throws CommonException
    {
        Calendar curDate = Calendar.getInstance();
        String[] planDate = new String[2];

        // 作業日を読み込みます。
        String workDate = null;

        WarenaviSystemController controller = new WarenaviSystemController(_conn, getClass());
        workDate = controller.getWorkDay();
        Date targetDate = WmsFormatter.toDate(workDate);
        curDate.setTime(targetDate);

        if (workDate != null)
        {
            // 作業日をセットします
            planDate[0] = workDate;
            // 作業日の前日のｎヶ月前を取得します
            curDate.add(Calendar.DATE, -1);
            curDate.add(Calendar.MONTH, offset * -1);
            Date date = curDate.getTime();
            planDate[1] = WmsFormatter.toParamDate(date);
        }
        return planDate;
    }

    /**
     * {@inheritDoc}
     */
    public int search(SearchKey key, int limit)
    {
        // 何もしません
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    public int searchForUpdate(SearchKey key, int waitsec)
    {
        // 何もしません
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    public Entity[] getEntities(int numRec)
    {
        // 何もしません
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public SQLErrors getSQLErrors()
    {
        // 何もしません
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasNext()
    {
        // 何もしません
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public void open(boolean forwardOnly)
    {
        // 何もしません
    }
}
