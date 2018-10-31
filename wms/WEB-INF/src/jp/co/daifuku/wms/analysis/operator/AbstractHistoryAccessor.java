// $Id: AbstractHistoryAccessor.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.analysis.operator;

import java.sql.Connection;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.wms.analysis.dbhandler.AbstractHistoryHandler;
import jp.co.daifuku.wms.analysis.entity.HistoryEntity;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 履歴データ検索処理の抽象クラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2006/06/27</td><td nowrap>Softecs</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Softecs
 * @author  Last commit: $Author: admin $
 */


public abstract class AbstractHistoryAccessor
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
    private Connection _conn = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションを指定してインスタンス化します。<BR>
     * @param conn データベースコネクション<BR>
     */
    public AbstractHistoryAccessor(Connection conn)
    {
        _conn = conn;
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * パラメータで指定された検索条件をもとに履歴データを検索し、検索結果を返します。<BR>
     * @param param 検索条件<BR>
     * @return 検索結果<BR>
     * @throws CommonException データベースの接続でエラーを検出した場合に通知されます。
     */
    public HistorySearchResult search(HistorySearchParamater param)
            throws CommonException
    {
        // 検索結果オブジェクトを生成し、指定された検索条件を設定します。
        HistorySearchResult result = getHistorySearchResult();
        result.setSearchParamater(param);

        // 検索条件により、履歴データを検索します。
        AbstractHistoryHandler handler = getHistoryHandler(getConnection());
        HistoryEntity[] histories = handler.search(param);

        // 検索した履歴データを検索結果に設定します。
        result.setResult(histories);

        return result;
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
     * コンストラクタで指定されたデータベースコネクションオブジェクトを取得します。<BR>
     * @return データベースコネクション<BR>
     */
    protected Connection getConnection()
    {
        return _conn;
    }

    /**
     * 検索結果オブジェクトを生成して返します。<BR>
     * @return 検索結果オブジェクト<BR>
     */
    protected abstract HistorySearchResult getHistorySearchResult();

    /**
     * 履歴データのハンドラを取得します。<BR>
     * @param conn データベースコネクションオブジェクト<BR>
     * @return 履歴データハンドラ<BR>
     */
    protected abstract AbstractHistoryHandler getHistoryHandler(Connection conn);

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
        return "$Id: AbstractHistoryAccessor.java 87 2008-10-04 03:07:38Z admin $";
    }
}
