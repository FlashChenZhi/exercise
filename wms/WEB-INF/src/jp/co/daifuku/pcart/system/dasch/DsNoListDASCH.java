// $Id: DsNoListDASCH.java 4804 2009-08-10 06:26:35Z shibamoto $
package jp.co.daifuku.pcart.system.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


import static jp.co.daifuku.pcart.system.dasch.DsNoListDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.PCTOperationLogFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTOperationLogHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTOperationLogSearchKey;
import jp.co.daifuku.wms.base.entity.PCTOperationLog;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * DS番号一覧に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4804 $, $Date:: 2009-08-10 15:26:35 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class DsNoListDASCH
        extends AbstractWmsDASCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /**
     * DB Finder
     */
    private AbstractDBFinder _finder = null;

    /**
     * 現在点
     */
    private int _current = -1;

    /**
     * レコード総数
     */
    private int _total = -1;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでDASCHを作成します。
     * @param conn Database DBコネクション
     * @param parent Caller 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     */
    public DsNoListDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * DBの検索を行います。
     * 帳票出力、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public void search(Params p)
            throws CommonException
    {
        // Create Finder Object
        _finder = new PCTOperationLogFinder(getConnection());
        // Initialize record counts
        _finder.open(isForwardOnly());
        // Create Search Key and search for Records
        _finder.search(createSearchKey(p, true));

        _current = -1;
    }

    /**
     * 次のデータが存在するかどうかを判定します。<BR>
     * 帳票出力で使用します。
     * @return データが存在する場合はtrueを返します。
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean next()
            throws CommonException
    {
        _current++;
        return _total > _current;
    }

    /**
     * データを1件返します。<BR>
     * 帳票出力で使用します。
     * @return 取得データ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public Params get()
            throws CommonException
    {
        throw new ScheduleException("This method is not implemented.");
    }

    /**
     *
     * finder,Connection close
     */
    public void close()
    {
        if (_finder != null)
        {
            _finder.close();
        }
        super.close();
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * データ件数を返します。
     * 帳票発行、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @return データ件数
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected int actualCount(Params p)
            throws CommonException
    {
        PCTOperationLogHandler handler = new PCTOperationLogHandler(getConnection());

        // find count
        _total = handler.count(createSearchKey(p, false));

        return _total;
    }

    /**
     * 検索したデータのうち、start番目からcntで指定された件数のデータを返します。
     * 一覧表示で使用します。
     *
     * @param start 開始インデックス
     * @param cnt 件数
     * @return 対象データのリスト
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        //throw new RuntimeException("This method is not implemented.");
        List<Params> params = new ArrayList<Params>();
        PCTOperationLog[] ents = (PCTOperationLog[])_finder.getEntities(start, start + cnt);

        for (PCTOperationLog ent : ents)
        {
            Params p = new Params();
            // ボタン番号
            p.set(SELECT, String.valueOf(params.size() + 1 + start));
            p.set(DS_NO, ent.getDsNo());
            p.set(SCREEN_NAME, DisplayText.getText((String)ent.getPagenameresourcekey()));

            params.add(p);
        }
        return params;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        PCTOperationLogSearchKey key = new PCTOperationLogSearchKey();

        String fromDate = WmsFormatter.toParamDate(param.getDate(FROM_SEARCH_DATE));
        String fromTime = WmsFormatter.toParamTime(param.getDate(FROM_SEARCH_TIME));
        String toDate = WmsFormatter.toParamDate(param.getDate(TO_SEARCH_DATE));
        String toTime = WmsFormatter.toParamTime(param.getDate(TO_SEARCH_TIME));

        int comparison = 0;
        // 出力日時
        if (!StringUtil.isBlank(fromDate) && !StringUtil.isBlank(toDate))
        {
            comparison = fromDate.compareTo(toDate);
            if (0 == comparison)
            {
                comparison = fromTime.compareTo(toTime);
                if (0 == comparison)
                {
                    key.setLogDate(WmsFormatter.getSearchToDate(fromDate, fromTime), "=", "", "", true);
                }
                else
                {
                    if (0 > comparison)
                    {
                        key.setLogDate(WmsFormatter.getSearchToDate(fromDate, fromTime), ">=", "", "", true);
                        key.setLogDate(WmsFormatter.getSearchToDate(toDate, toTime), "<=", "", "", true);
                    }
                    else
                    {
                        key.setLogDate(WmsFormatter.getSearchToDate(toDate, toTime), ">=", "", "", true);
                        key.setLogDate(WmsFormatter.getSearchToDate(fromDate, fromTime), "<=", "", "", true);
                    }
                }

            }
            else
            {
                if (0 > comparison)
                {
                    key.setLogDate(WmsFormatter.getSearchToDate(fromDate, fromTime), ">=", "", "", true);
                    key.setLogDate(WmsFormatter.getSearchToDate(toDate, toTime), "<=", "", "", true);
                }
                else
                {
                    key.setLogDate(WmsFormatter.getSearchToDate(toDate, toTime), ">=", "", "", true);
                    key.setLogDate(WmsFormatter.getSearchToDate(fromDate, fromTime), "<=", "", "", true);
                }
            }
        }
        else
        {
            if (!StringUtil.isBlank(fromDate))
            {
                key.setLogDate(WmsFormatter.getSearchToDate(fromDate, fromTime), ">=", "", "", true);
            }
            else
            {
                if (!StringUtil.isBlank(toDate))
                {
                    key.setLogDate(WmsFormatter.getSearchToDate(toDate, toTime), "<=", "", "", true);
                }
            }
        }

        // ユーザID
        if (!StringUtil.isBlank(param.getString(USER_ID)))
        {
            key.setUserId(param.getString(USER_ID));
        }

        // DS番号
        if (!StringUtil.isBlank(param.getString(DS_NO)))
        {
            key.setDsNo(param.getString(DS_NO), ">=", "", "", true);
        }
        key.setDsNoGroup();
        key.setPagenameresourcekeyGroup();
        if (isSet)
        {
            // OrderByや、collect項目を記載する
            // DS番号昇順
            key.setDsNoOrder(true);
            key.setDsNoCollect();
            key.setPagenameresourcekeyCollect();
        }

        return key;
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
