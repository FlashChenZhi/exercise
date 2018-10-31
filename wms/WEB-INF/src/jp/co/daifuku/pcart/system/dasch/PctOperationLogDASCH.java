// $Id: PctOperationLogDASCH.java 4804 2009-08-10 06:26:35Z shibamoto $
package jp.co.daifuku.pcart.system.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.pcart.system.dasch.PctOperationLogDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.PCTOperationLogFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTOperationLogHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTOperationLogSearchKey;
import jp.co.daifuku.wms.base.entity.PCTOperationLog;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 画面操作履歴一覧に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4804 $, $Date:: 2009-08-10 15:26:35 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class PctOperationLogDASCH
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
    public PctOperationLogDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        //throw new ScheduleException("This method is not implemented.");
        // get Next entity from finder class
        PCTOperationLog[] ents = (PCTOperationLog[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (PCTOperationLog ent : ents)
        {
            // 出力日
            p.set(LOG_DAY, WmsFormatter.toDispDate(ent.getLogDate(), getLocale()));
            // 出力時間
            p.set(LOG_TIME, WmsFormatter.toDispTime(WmsFormatter.toParamDateTime(ent.getLogDate(), 3), getLocale()));
            // 出力日時
            p.set(HIDDEN_LOG_DAY, ent.getLogDate());
            // 操作区分(名称)
            p.set(OPERATION_TYPE, DisplayResource.getOperationType(String.valueOf(ent.getOperationType())));
            // 操作区分
            p.set(HIDDEN_OPERATION_TYPE, ent.getOperationType());
            // ユーザID
            p.set(USER_ID, ent.getUserId());
            // ユーザ名称
            p.set(USER_NAME, ent.getUserName());
            // IPアドレス
            p.set(IP_ADDRESS, ent.getIpaddress());
            // 端末名称
            p.set(TERMINAL_NAME, ent.getTerminalName());
            // DS番号
            p.set(DS_NO, ent.getDsNo());
            // 画面名称
            p.set(SCREEN_NAME, DisplayText.getText((String)ent.getPagenameresourcekey()));
            // ページリソースキー
            p.set(PAGENAME_RESOURCE_KEY, ent.getPagenameresourcekey());
            break;
        }
        // return Pram objstc
        return p;
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
            // 番号
            p.set(LINE_NO, String.valueOf(params.size() + 1 + start));
            // 出力日
            p.set(LOG_DAY, WmsFormatter.toDispDate(ent.getLogDate(), getLocale()));
            // 出力時間
            p.set(LOG_TIME, WmsFormatter.toDispTime(ent.getLogDate(), getLocale()));
            // 出力日時
            p.set(HIDDEN_LOG_DAY, ent.getLogDate());
            // 操作区分(名称)
            p.set(OPERATION_TYPE, DisplayResource.getOperationType(String.valueOf(ent.getOperationType())));
            // 操作区分
            p.set(HIDDEN_OPERATION_TYPE, ent.getOperationType());
            // ユーザID
            p.set(USER_ID, ent.getUserId());
            // ユーザ名称
            p.set(USER_NAME, ent.getUserName());
            // IPアドレス
            p.set(IP_ADDRESS, ent.getIpaddress());
            // 端末名称
            p.set(TERMINAL_NAME, ent.getTerminalName());
            // DS番号
            p.set(DS_NO, ent.getDsNo());
            // 画面名称
            p.set(SCREEN_NAME, DisplayText.getText((String)ent.getPagenameresourcekey()));
            // ページリソースキー
            p.set(PAGENAME_RESOURCE_KEY, ent.getPagenameresourcekey());

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

        // 検索条件、集約条件をセットする
        // where, group by
        String fromDate = WmsFormatter.toParamDate(param.getDate(FROM_SEARCH_DATE));
        String fromTime = WmsFormatter.toParamTime(param.getDate(FROM_SEARCH_TIME));
        String toDate = WmsFormatter.toParamDate(param.getDate(TO_SEARCH_DATE));
        String toTime = WmsFormatter.toParamTime(param.getDate(TO_SEARCH_TIME));

        int comparison = 0;
        String[] dateList = WmsFormatter.getSearchDayTime(fromDate, fromTime, toDate, toTime, true);

        // 出力日時
        if (!StringUtil.isBlank(dateList[0]) && !StringUtil.isBlank(dateList[2]))
        {
            comparison = fromDate.compareTo(dateList[2]);
            if (0 == comparison)
            {
                comparison = fromTime.compareTo(dateList[3]);
                if (0 == comparison)
                {
                    key.setLogDate(WmsFormatter.getSearchFromDate(dateList[0], dateList[1]), ">=", "", "", true);
                    key.setLogDate(WmsFormatter.getSearchToDate(dateList[2], dateList[3]), "<", "", "", true);
                }
                else
                {
                    if (0 > comparison)
                    {
                        key.setLogDate(WmsFormatter.getSearchFromDate(dateList[0], dateList[1]), ">=", "", "", true);
                        key.setLogDate(WmsFormatter.getSearchFromDate(dateList[2], dateList[3]), "<", "", "", true);
                    }
                    else
                    {
                        key.setLogDate(WmsFormatter.getSearchFromDate(dateList[2], dateList[3]), ">=", "", "", true);
                        key.setLogDate(WmsFormatter.getSearchFromDate(dateList[0], dateList[1]), "<", "", "", true);
                    }
                }
            }
            else
            {
                if (0 > comparison)
                {
                    key.setLogDate(WmsFormatter.getSearchFromDate(dateList[0], dateList[1]), ">=", "", "", true);
                    key.setLogDate(WmsFormatter.getSearchFromDate(dateList[2], dateList[3]), "<", "", "", true);
                }
                else
                {
                    key.setLogDate(WmsFormatter.getSearchFromDate(dateList[2], dateList[3]), ">=", "", "", true);
                    key.setLogDate(WmsFormatter.getSearchFromDate(dateList[0], dateList[1]), "<", "", "", true);
                }
            }
        }
        else
        {
            if (!StringUtil.isBlank(dateList[0]))
            {
                key.setLogDate(WmsFormatter.getSearchFromDate(dateList[0], dateList[1]), ">=", "", "", true);
            }
            else
            {
                if (!StringUtil.isBlank(dateList[2]))
                {
                    key.setLogDate(WmsFormatter.getSearchFromDate(dateList[2], dateList[3]), "<", "", "", true);
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
            key.setDsNo(param.getString(DS_NO), "=", "", "", true);
        }

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            key.setLogDateOrder(true);
            key.setLogDateCollect();
            key.setOperationTypeCollect();
            key.setUserIdCollect();
            key.setUserNameCollect();
            key.setIpaddressCollect();
            key.setTerminalNameCollect();
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
