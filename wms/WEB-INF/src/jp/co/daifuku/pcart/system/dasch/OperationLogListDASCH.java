// $Id: OperationLogListDASCH.java 4804 2009-08-10 06:26:35Z shibamoto $
package jp.co.daifuku.pcart.system.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.system.dasch.OperationLogListDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.PCTOperationLogFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTOperationLogHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTOperationLogSearchKey;
import jp.co.daifuku.wms.base.entity.PCTOperationLog;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 画面操作履歴照会に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4804 $, $Date:: 2009-08-10 15:26:35 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class OperationLogListDASCH
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
    public OperationLogListDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
            p.set(ITEM_DATA_1, ent.getItem1());
            p.set(ITEM_DATA_2, ent.getItem2());
            p.set(ITEM_DATA_3, ent.getItem3());
            p.set(ITEM_DATA_4, ent.getItem4());
            p.set(ITEM_DATA_5, ent.getItem5());
            p.set(ITEM_DATA_6, ent.getItem6());
            p.set(ITEM_DATA_7, ent.getItem7());
            p.set(ITEM_DATA_8, ent.getItem8());
            p.set(ITEM_DATA_9, ent.getItem9());
            p.set(ITEM_DATA_10, ent.getItem10());
            p.set(ITEM_DATA_11, ent.getItem11());
            p.set(ITEM_DATA_12, ent.getItem12());
            p.set(ITEM_DATA_13, ent.getItem13());
            p.set(ITEM_DATA_14, ent.getItem14());
            p.set(ITEM_DATA_15, ent.getItem15());
            p.set(ITEM_DATA_16, ent.getItem16());
            p.set(ITEM_DATA_17, ent.getItem17());
            p.set(ITEM_DATA_18, ent.getItem18());
            p.set(ITEM_DATA_19, ent.getItem19());
            p.set(ITEM_DATA_20, ent.getItem20());
            p.set(ITEM_DATA_21, ent.getItem21());
            p.set(ITEM_DATA_22, ent.getItem22());
            p.set(ITEM_DATA_23, ent.getItem23());
            p.set(ITEM_DATA_24, ent.getItem24());
            p.set(ITEM_DATA_25, ent.getItem25());
            p.set(ITEM_DATA_26, ent.getItem26());
            p.set(ITEM_DATA_27, ent.getItem27());
            p.set(ITEM_DATA_28, ent.getItem28());
            p.set(ITEM_DATA_29, ent.getItem29());
            p.set(ITEM_DATA_30, ent.getItem30());
            p.set(ITEM_DATA_31, ent.getItem31());
            p.set(ITEM_DATA_32, ent.getItem32());
            p.set(ITEM_DATA_33, ent.getItem33());
            p.set(ITEM_DATA_34, ent.getItem34());
            p.set(ITEM_DATA_35, ent.getItem35());
            p.set(ITEM_DATA_36, ent.getItem36());
            p.set(ITEM_DATA_37, ent.getItem37());
            p.set(ITEM_DATA_38, ent.getItem38());
            p.set(ITEM_DATA_39, ent.getItem39());
            p.set(ITEM_DATA_40, ent.getItem40());

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
            p.set(ITEM_DATA_1, ent.getItem1());
            p.set(ITEM_DATA_2, ent.getItem2());
            p.set(ITEM_DATA_3, ent.getItem3());
            p.set(ITEM_DATA_4, ent.getItem4());
            p.set(ITEM_DATA_5, ent.getItem5());
            p.set(ITEM_DATA_6, ent.getItem6());
            p.set(ITEM_DATA_7, ent.getItem7());
            p.set(ITEM_DATA_8, ent.getItem8());
            p.set(ITEM_DATA_9, ent.getItem9());
            p.set(ITEM_DATA_10, ent.getItem10());
            p.set(ITEM_DATA_11, ent.getItem11());
            p.set(ITEM_DATA_12, ent.getItem12());
            p.set(ITEM_DATA_13, ent.getItem13());
            p.set(ITEM_DATA_14, ent.getItem14());
            p.set(ITEM_DATA_15, ent.getItem15());
            p.set(ITEM_DATA_16, ent.getItem16());
            p.set(ITEM_DATA_17, ent.getItem17());
            p.set(ITEM_DATA_18, ent.getItem18());
            p.set(ITEM_DATA_19, ent.getItem19());
            p.set(ITEM_DATA_20, ent.getItem20());
            p.set(ITEM_DATA_21, ent.getItem21());
            p.set(ITEM_DATA_22, ent.getItem22());
            p.set(ITEM_DATA_23, ent.getItem23());
            p.set(ITEM_DATA_24, ent.getItem24());
            p.set(ITEM_DATA_25, ent.getItem25());
            p.set(ITEM_DATA_26, ent.getItem26());
            p.set(ITEM_DATA_27, ent.getItem27());
            p.set(ITEM_DATA_28, ent.getItem28());
            p.set(ITEM_DATA_29, ent.getItem29());
            p.set(ITEM_DATA_30, ent.getItem30());
            p.set(ITEM_DATA_31, ent.getItem31());
            p.set(ITEM_DATA_32, ent.getItem32());
            p.set(ITEM_DATA_33, ent.getItem33());
            p.set(ITEM_DATA_34, ent.getItem34());
            p.set(ITEM_DATA_35, ent.getItem35());
            p.set(ITEM_DATA_36, ent.getItem36());
            p.set(ITEM_DATA_37, ent.getItem37());
            p.set(ITEM_DATA_38, ent.getItem38());
            p.set(ITEM_DATA_39, ent.getItem39());
            p.set(ITEM_DATA_40, ent.getItem40());

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
        // 出力日時
        if (!StringUtil.isBlank(param.getString(HIDDEN_LOG_DAY)))
        {
            key.setLogDate(param.getDate(HIDDEN_LOG_DAY));

        }

        // 操作区分
        if (!StringUtil.isBlank(param.getString(HIDDEN_OPERATION_TYPE)))
        {
            key.setOperationType(param.getInt(HIDDEN_OPERATION_TYPE));
        }

        // ユーザID
        if (!StringUtil.isBlank(param.getString(USER_ID)))
        {
            key.setUserId(param.getString(USER_ID));
        }

        // ユーザ名称
        if (!StringUtil.isBlank(param.getString(USER_NAME)))
        {
            key.setUserName(param.getString(USER_NAME));
        }

        // DS番号
        if (!StringUtil.isBlank(param.getString(DS_NO)))
        {
            key.setDsNo(param.getString(DS_NO));
        }

        // 画面名称
        if (!StringUtil.isBlank(param.getString(PAGENAME_RESOURCE_KEY)))
        {
            key.setPagenameresourcekey(param.getString(PAGENAME_RESOURCE_KEY));
        }

        // IPアドレス
        if (!StringUtil.isBlank(param.getString(IP_ADDRESS)))
        {
            key.setIpaddress(param.getString(IP_ADDRESS));
        }

        // 端末名称
        if (!StringUtil.isBlank(param.getString(TERMINAL_NAME)))
        {
            key.setTerminalName(param.getString(TERMINAL_NAME));
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
