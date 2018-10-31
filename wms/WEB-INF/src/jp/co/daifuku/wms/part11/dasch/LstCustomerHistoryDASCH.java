// $Id: LstCustomerHistoryDASCH.java,v 1.2 2009/02/24 02:31:58 ose Exp $
package jp.co.daifuku.wms.part11.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.part11.dasch.LstCustomerHistoryDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.CustomerHistoryFinder;
import jp.co.daifuku.wms.base.dbhandler.CustomerHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.CustomerHistoryImpFinder;
import jp.co.daifuku.wms.base.dbhandler.CustomerHistoryImpHandler;
import jp.co.daifuku.wms.base.dbhandler.CustomerHistoryImpSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CustomerHistorySearchKey;
import jp.co.daifuku.wms.base.entity.CustomerHistory;
import jp.co.daifuku.wms.base.entity.CustomerHistoryImp;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.db.DatabaseHandler;
import jp.co.daifuku.wms.part11.listbox.Part11ListBoxDefine;

/**
 * 出荷先マスタ更新履歴詳細一覧に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:31:58 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class LstCustomerHistoryDASCH
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

    /**
     * DB検索先
     */
    private String _dbtype = null;

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
    public LstCustomerHistoryDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // TODO : Implement for export
        // 検索先のテーブルを記憶
        _dbtype = p.getString(TABLE_NAME);
        // Create Finder Object
        if (Part11ListBoxDefine.DBTYPE_DB.equals(p.getString(TABLE_NAME)))
        {
            // 出荷先マスタ更新履歴用
            _finder = new CustomerHistoryFinder(getConnection());
        }
        else if (Part11ListBoxDefine.DBTYPE_IMP.equals(p.getString(TABLE_NAME)))
        {
            // 取込用出荷先マスタ更新履歴用
            _finder = new CustomerHistoryImpFinder(getConnection());
        }
        else
        {
            throw new ScheduleException();
        }

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
        // TODO : Implement for export
        //throw new ScheduleException("This method is not implemented.");
        // get Next entity from finder class
        Params p = new Params();
        if (Part11ListBoxDefine.DBTYPE_DB.equals(_dbtype))
        {
            CustomerHistory[] ents = (CustomerHistory[])_finder.getEntities(1);

            // conver Entity to Param object
            p.set(LOGDATE_DAY, ents[0].getLogDate());
            p.set(LOGDATE_TIME, ents[0].getLogDate());
            p.set(UPDATE_KIND, Part11ListBoxDefine.getDevisionName(ents[0].getUpdateKind()));
            p.set(USER_ID, ents[0].getUserId());
            p.set(USER_NAME, ents[0].getUserName());
            p.set(IP_ADDRESS, ents[0].getIpAddress());
            p.set(TERMINAL_NAME, ents[0].getTerminalName());
            p.set(CUSTOMER_CODE, ents[0].getCustomerCode());
            p.set(CUSTOMER_NAME, ents[0].getCustomerName());
            p.set(UPDATE_CUSTOMER_NAME, ents[0].getUpdateCustomerName());
            p.set(ROUTE, ents[0].getRoute());
            p.set(UPDATE_ROUTE, ents[0].getUpdateRoute());
            p.set(POSTAL_CODE, ents[0].getPostalCode());
            p.set(UPDATE_POSTAL_CODE, ents[0].getUpdatePostalCode());
            p.set(PREFECTURE, ents[0].getPrefecture());
            p.set(UPDATE_PREFECTURE, ents[0].getUpdatePrefecture());
            p.set(ADDRESS1, ents[0].getAddress1());
            p.set(UPDATE_ADDRESS1, ents[0].getUpdateAddress1());
            p.set(ADDRESS2, ents[0].getAddress2());
            p.set(UPDATE_ADDRESS2, ents[0].getUpdateAddress2());
            p.set(TELEPHONE, ents[0].getTelephone());
            p.set(UPDATE_TELEPHONE, ents[0].getUpdateTelephone());
            p.set(CONTACT1, ents[0].getContact1());
            p.set(UPDATE_CONTACT1, ents[0].getUpdateContact1());
            p.set(CONTACT2, ents[0].getContact2());
            p.set(UPDATE_CONTACT2, ents[0].getUpdateContact2());
            p.set(SORTING_PLACE, ents[0].getSortingPlace());
            p.set(UPDATE_SORTING_PLACE, ents[0].getUpdateSortingPlace());
        }
        else if (Part11ListBoxDefine.DBTYPE_IMP.equals(_dbtype))
        {
            CustomerHistoryImp[] ents = (CustomerHistoryImp[])_finder.getEntities(1);

            // conver Entity to Param object
            p.set(LOGDATE_DAY, ents[0].getLogDate());
            p.set(LOGDATE_TIME, ents[0].getLogDate());
            p.set(UPDATE_KIND, Part11ListBoxDefine.getDevisionName(ents[0].getUpdateKind()));
            p.set(USER_ID, ents[0].getUserId());
            p.set(USER_NAME, ents[0].getUserName());
            p.set(IP_ADDRESS, ents[0].getIpAddress());
            p.set(TERMINAL_NAME, ents[0].getTerminalName());
            p.set(CUSTOMER_CODE, ents[0].getCustomerCode());
            p.set(CUSTOMER_NAME, ents[0].getCustomerName());
            p.set(UPDATE_CUSTOMER_NAME, ents[0].getUpdateCustomerName());
            p.set(ROUTE, ents[0].getRoute());
            p.set(UPDATE_ROUTE, ents[0].getUpdateRoute());
            p.set(POSTAL_CODE, ents[0].getPostalCode());
            p.set(UPDATE_POSTAL_CODE, ents[0].getUpdatePostalCode());
            p.set(PREFECTURE, ents[0].getPrefecture());
            p.set(UPDATE_PREFECTURE, ents[0].getUpdatePrefecture());
            p.set(ADDRESS1, ents[0].getAddress1());
            p.set(UPDATE_ADDRESS1, ents[0].getUpdateAddress1());
            p.set(ADDRESS2, ents[0].getAddress2());
            p.set(UPDATE_ADDRESS2, ents[0].getUpdateAddress2());
            p.set(TELEPHONE, ents[0].getTelephone());
            p.set(UPDATE_TELEPHONE, ents[0].getUpdateTelephone());
            p.set(CONTACT1, ents[0].getContact1());
            p.set(UPDATE_CONTACT1, ents[0].getUpdateContact1());
            p.set(CONTACT2, ents[0].getContact2());
            p.set(UPDATE_CONTACT2, ents[0].getUpdateContact2());
            p.set(SORTING_PLACE, ents[0].getSortingPlace());
            p.set(UPDATE_SORTING_PLACE, ents[0].getUpdateSortingPlace());
        }

        // return Param object
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
        // TODO : Implement for export or listcell
        //throw new ScheduleException("This method is not implemented.");
        DatabaseHandler handler = null;
        if (Part11ListBoxDefine.DBTYPE_DB.equals(p.getString(TABLE_NAME)))
        {
            // 出荷先マスタ更新履歴用
            handler = new CustomerHistoryHandler(getConnection());
        }
        else if (Part11ListBoxDefine.DBTYPE_IMP.equals(p.getString(TABLE_NAME)))
        {
            // 取込用出荷先マスタ更新履歴用
            handler = new CustomerHistoryImpHandler(getConnection());
        }

        // find count
        _total = handler.count(createSearchKey(p, false));

        if (_total == 0)
        {
            // 6003011=対象データはありませんでした。
            setMessage("6003011");
        }
        else if (_total > CommonParam.getIntParam("MAX_NUMBER_OF_DISP_LISTBOX"))
        {
            // 6001023={0}件該当しました。表示可能件数を超えるため、先頭{1}件の情報を表示します。
            setMessage(WmsMessageFormatter.format(6001023, WmsFormatter.getNumFormat(_total),
                    WmsFormatter.getNumFormat(CommonParam.getIntParam("MAX_NUMBER_OF_DISP_LISTBOX"))));
        }
        else
        {
            // 6001022={0}件該当しました。
            setMessage(WmsMessageFormatter.format(6001022, WmsFormatter.getNumFormat(_total)));
        }
        return _total;
    }

    /**
     * 検索したデータのうち、start番目からcntで指定された件数のデータを返します。
     * 一覧表示で使用します。
     *
     * @param start 開始インデックス
     * @param cnt 件数
     * @return 対象データのリスト
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        // TODO : Implement for listcell
        //throw new RuntimeException("This method is not implemented.");
        List<Params> params = new ArrayList<Params>();

        if (Part11ListBoxDefine.DBTYPE_DB.equals(_dbtype))
        {
            CustomerHistory[] ents = (CustomerHistory[])_finder.getEntities(start, start + cnt);

            // conver Entity to Param object
            for (CustomerHistory ent : ents)
            {
                start++;

                Params p = new Params();
                p.set(NO, start);
                p.set(LOGDATE_DAY, ent.getLogDate());
                p.set(LOGDATE_TIME, ent.getLogDate());
                p.set(UPDATE_KIND, Part11ListBoxDefine.getDevisionName(ent.getUpdateKind()));
                p.set(USER_ID, ent.getUserId());
                p.set(USER_NAME, ent.getUserName());
                p.set(IP_ADDRESS, ent.getIpAddress());
                p.set(TERMINAL_NAME, ent.getTerminalName());
                p.set(CUSTOMER_CODE, ent.getCustomerCode());
                p.set(CUSTOMER_NAME, ent.getCustomerName());
                p.set(UPDATE_CUSTOMER_NAME, ent.getUpdateCustomerName());
                p.set(ROUTE, ent.getRoute());
                p.set(UPDATE_ROUTE, ent.getUpdateRoute());
                p.set(POSTAL_CODE, ent.getPostalCode());
                p.set(UPDATE_POSTAL_CODE, ent.getUpdatePostalCode());
                p.set(PREFECTURE, ent.getPrefecture());
                p.set(UPDATE_PREFECTURE, ent.getUpdatePrefecture());
                p.set(ADDRESS1, ent.getAddress1());
                p.set(UPDATE_ADDRESS1, ent.getUpdateAddress1());
                p.set(ADDRESS2, ent.getAddress2());
                p.set(UPDATE_ADDRESS2, ent.getUpdateAddress2());
                p.set(TELEPHONE, ent.getTelephone());
                p.set(UPDATE_TELEPHONE, ent.getUpdateTelephone());
                p.set(CONTACT1, ent.getContact1());
                p.set(UPDATE_CONTACT1, ent.getUpdateContact1());
                p.set(CONTACT2, ent.getContact2());
                p.set(UPDATE_CONTACT2, ent.getUpdateContact2());
                p.set(SORTING_PLACE, ent.getSortingPlace());
                p.set(UPDATE_SORTING_PLACE, ent.getUpdateSortingPlace());

                params.add(p);
            }
        }
        else if (Part11ListBoxDefine.DBTYPE_IMP.equals(_dbtype))
        {
            CustomerHistoryImp[] ents = (CustomerHistoryImp[])_finder.getEntities(start, start + cnt);

            // conver Entity to Param object
            for (CustomerHistoryImp ent : ents)
            {
                start++;

                Params p = new Params();
                p.set(NO, start);
                p.set(LOGDATE_DAY, ent.getLogDate());
                p.set(LOGDATE_TIME, ent.getLogDate());
                p.set(UPDATE_KIND, Part11ListBoxDefine.getDevisionName(ent.getUpdateKind()));
                p.set(USER_ID, ent.getUserId());
                p.set(USER_NAME, ent.getUserName());
                p.set(IP_ADDRESS, ent.getIpAddress());
                p.set(TERMINAL_NAME, ent.getTerminalName());
                p.set(CUSTOMER_CODE, ent.getCustomerCode());
                p.set(CUSTOMER_NAME, ent.getCustomerName());
                p.set(UPDATE_CUSTOMER_NAME, ent.getUpdateCustomerName());
                p.set(ROUTE, ent.getRoute());
                p.set(UPDATE_ROUTE, ent.getUpdateRoute());
                p.set(POSTAL_CODE, ent.getPostalCode());
                p.set(UPDATE_POSTAL_CODE, ent.getUpdatePostalCode());
                p.set(PREFECTURE, ent.getPrefecture());
                p.set(UPDATE_PREFECTURE, ent.getUpdatePrefecture());
                p.set(ADDRESS1, ent.getAddress1());
                p.set(UPDATE_ADDRESS1, ent.getUpdateAddress1());
                p.set(ADDRESS2, ent.getAddress2());
                p.set(UPDATE_ADDRESS2, ent.getUpdateAddress2());
                p.set(TELEPHONE, ent.getTelephone());
                p.set(UPDATE_TELEPHONE, ent.getUpdateTelephone());
                p.set(CONTACT1, ent.getContact1());
                p.set(UPDATE_CONTACT1, ent.getUpdateContact1());
                p.set(CONTACT2, ent.getContact2());
                p.set(UPDATE_CONTACT2, ent.getUpdateContact2());
                p.set(SORTING_PLACE, ent.getSortingPlace());
                p.set(UPDATE_SORTING_PLACE, ent.getUpdateSortingPlace());

                params.add(p);
            }
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
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        // 検索日時の補完処理
        Date[] tmp =
                WmsFormatter.getFromTo(WmsFormatter.toDate(param.getString(DISPFROMDAY_KEY)),
                        WmsFormatter.toTime(param.getString(DISPFROMTIME_KEY)),
                        WmsFormatter.toDate(param.getString(DISPTODAY_KEY)),
                        WmsFormatter.toTime(param.getString(DISPTOTIME_KEY)));
        Date from = tmp[0];
        Date to = tmp[1];

        if (Part11ListBoxDefine.DBTYPE_DB.equals(param.getString(TABLE_NAME)))
        {
            // 出荷先マスタ更新履歴用
            CustomerHistorySearchKey key = new CustomerHistorySearchKey();

            // 検索条件、集約条件をセットする
            // where, group by
            // 開始出力日
            if (!StringUtil.isBlank(from))
            {
                key.setLogDate(from, ">=");
            }
            // 終了出力日
            if (!StringUtil.isBlank(to))
            {
                key.setLogDate(to, "<=");
            }
            // ユーザID
            if (!StringUtil.isBlank(param.getString(USERID_KEY)))
            {
                key.setUserId(param.getString(USERID_KEY));
            }
            // DS番号
            if (!StringUtil.isBlank(param.getString(DSNUMBER_KEY)))
            {
                key.setDsNo(param.getString(DSNUMBER_KEY));
            }

            if (isSet)
            {
                // OrderByや、collect項目を記載する
                // 読込み順序指定
                // 出力日時の昇順
                key.setLogDateOrder(true);
            }

            return key;

        }
        else if (Part11ListBoxDefine.DBTYPE_IMP.equals(param.getString(TABLE_NAME)))
        {
            // 取込用出荷先マスタ更新履歴用
            CustomerHistoryImpSearchKey key = new CustomerHistoryImpSearchKey();

            // 検索条件、集約条件をセットする
            // where, group by
            // 開始出力日
            if (!StringUtil.isBlank(from))
            {
                key.setLogDate(from, ">=");
            }
            // 終了出力日
            if (!StringUtil.isBlank(to))
            {
                key.setLogDate(to, "<=");
            }
            // ユーザID
            if (!StringUtil.isBlank(param.getString(USERID_KEY)))
            {
                key.setUserId(param.getString(USERID_KEY));
            }
            // DS番号
            if (!StringUtil.isBlank(param.getString(DSNUMBER_KEY)))
            {
                key.setDsNo(param.getString(DSNUMBER_KEY));
            }

            if (isSet)
            {
                // OrderByや、collect項目を記載する
                // 読込み順序指定
                // 出力日時の昇順
                key.setLogDateOrder(true);
            }

            return key;
        }
        else
        {
            // DBTypeがセットされているはずなので、ここは通らないはず
            throw new ScheduleException();
        }
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
