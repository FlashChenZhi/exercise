// $Id: AbstractHistorySearchResult.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.analysis.operator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * 履歴データ検索結果の抽象クラスです。<br>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2006/06/24</td><td nowrap>清水 正人</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  清水 正人
 * @author  Last commit: $Author: admin $
 */


public abstract class AbstractHistorySearchResult
        implements HistorySearchResult
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
    private HistorySearchParamater p_param = null;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    private List<Object> _resultList;

    private int _pos = 0;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ
     */
    public AbstractHistorySearchResult()
    {
        _resultList = Collections.synchronizedList(new ArrayList<Object>());
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 検索結果を内部リストに追加格納します。<BR>
     * @param result 検索結果オブジェクト<BR>
     */
    protected void addResult(Object result)
    {
        _resultList.add(result);
        first();
    }

    /**
     * パラメータで指定された位置に格納されている検索結果を取得します。<BR>
     * 該当する検索結果が無い場合には<code>null</code>を返します。<BR>
     * @param pos 取得位置<BR>
     * @return 検索結果<BR>
     */
    public Object getResult(int pos)
    {
        // 取得位置が、格納されている検索結果リストの範囲外であればnullを返します。
        if (0 > pos || count() < pos)
        {
            return null;
        }

        return _resultList.get(pos);
    }

    /**
     * 次の検索結果を取得します。<BR>
     * これ以上検索結果が無い場合には<code>null</code>を返します。<BR>
     * 検索結果を取得する位置は<code>first()</code>で初期化する事が出来ます。<BR>
     * @return 検索結果<BR>
     */
    public Object next()
    {
        if (count() <= _pos)
        {
            return null;
        }

        return _resultList.get(_pos++);
    }

    /**
     * 内部で保持している検索位置を先頭にします。<BR>
     */
    public void first()
    {
        _pos = 0;
    }

    /**
     * 検索した履歴データの件数を通知します。<BR>
     * @return 結果件数<BR>
     */
    public int count()
    {
        return _resultList.size();
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 履歴データの検索条件を設定します。<BR>
     * @param searchParam 検索条件<BR>
     */
    public void setSearchParamater(HistorySearchParamater searchParam)
    {
        p_param = searchParam;
    }

    /**
     * 履歴データの検索条件を取得します。<BR>
     * @return 検索条件<BR>
     */
    public HistorySearchParamater getSearchParamater()
    {
        return p_param;
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 検索結果オブジェクトを生成します。<BR>
     * @return 検索結果オブジェクト<BR>
     */
    protected abstract Object createResult();

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
        return "$Id: AbstractHistorySearchResult.java 87 2008-10-04 03:07:38Z admin $";
    }
}
