// $Id: ShippingHistAccessor.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.analysis.operator;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.wms.analysis.dbhandler.AbstractHistoryHandler;
import jp.co.daifuku.wms.analysis.dbhandler.ShippingHistFileHandler;


/**
 * 出荷履歴データ検索クラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2006/06/28</td><td nowrap>清水 正人</td><td>Class created.</td></tr>
 * <tr><td nowrap>2008/01/20</td><td nowrap>Shimizu(Softecs)</td><td>v3.2移行</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  清水 正人
 * @author  Last commit: $Author: admin $
 */


public class ShippingHistAccessor
        extends AbstractHistoryAccessor
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

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションを指定してインスタンス化します。<BR>
     * @param conn データベースコネクション<BR>
     */
    public ShippingHistAccessor(Connection conn)
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
     * 検索結果オブジェクトを生成し返します。<BR>
     * @return 検索結果オブジェクト<BR>
     */
    @Override
    protected HistorySearchResult getHistorySearchResult()
    {
        return new ShippingHistSearchResult();
    }

    /**
     * 履歴データのハンドラを取得します。<BR>
     * @param conn データベースコネクションオブジェクト<BR>
     * @return 履歴データハンドラ<BR>
     */
    @Override
    protected AbstractHistoryHandler getHistoryHandler(Connection conn)
    {
        ShippingHistFileHandler handler = new ShippingHistFileHandler(conn);
        return handler;
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
        return "$Id: ShippingHistAccessor.java 87 2008-10-04 03:07:38Z admin $";
    }
}
