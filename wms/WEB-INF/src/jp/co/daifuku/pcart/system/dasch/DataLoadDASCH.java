// $Id: DataLoadDASCH.java 4804 2009-08-10 06:26:35Z shibamoto $
package jp.co.daifuku.pcart.system.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.pcart.system.dasch.DataLoadDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.LoadErrorInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.LoadErrorInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.LoadErrorInfoSearchKey;
import jp.co.daifuku.wms.base.entity.LoadErrorInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 取り込みエラー情報検索のDASCHクラスです。<BR>
 * 検索条件を設定し、取り込みエラーチェックリストの検索を行います。<BR>
 * このクラスは、予定データ取り込み・マスタデータ取り込みともに使用されます。
 *
 * @version $Revision: 4804 $, $Date: 2009-08-10 15:26:35 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class DataLoadDASCH
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
    public DataLoadDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new LoadErrorInfoFinder(getConnection());
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
        LoadErrorInfo[] ents = (LoadErrorInfo[])_finder.getEntities(1);
        Params p = new Params();

        // conver Entity to Param object
        for (LoadErrorInfo ent : ents)
        {
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());

            // 取込機能名
            p.set(LOAD_TYPE, DisplayResource.getLoadDataType(ent.getJobType(), getLocale()));
            // 取込ファイル名
            p.set(FILE_NAME, ent.getFileName());
            // 取込日
            p.set(START_DAY, ent.getStartDate());
            // 取込時間
            p.set(START_TIME, ent.getStartDate());
            // ファイル行No.
            p.set(FILE_LINE_NO, ent.getFileLineNo());
            // エラーレベル
            p.set(ERROR_LEVEL, DisplayResource.getErrorLevel(ent.getErrorLevel(), getLocale()));
            // エラー詳細
            p.set(ERROR_DETAIL, DisplayResource.getErrorFlag(ent.getErrorFlag(), getLocale()));
            // 項目
            p.set(ITEM_NO, ent.getItemNo());
            // データ内容
            p.set(DATA, ent.getData());
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

    /**
     *
     * finder close
     */
    public void closeFinder()
    {
        if (_finder != null)
        {
            _finder.close();
        }
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
        //throw new ScheduleException("This method is not implemented.");
        LoadErrorInfoHandler handler = new LoadErrorInfoHandler(getConnection());

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
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        //throw new RuntimeException("This method is not implemented.");
        List<Params> params = new ArrayList<Params>();
        LoadErrorInfo[] ents = (LoadErrorInfo[])_finder.getEntities(start, start + cnt);

        for (LoadErrorInfo ent : ents)
        {
            Params p = new Params();
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());

            // 取込機能名
            p.set(LOAD_TYPE, DisplayResource.getLoadDataType(ent.getJobType()));
            // 取込ファイル名
            p.set(FILE_NAME, ent.getFileName());
            // 取込日
            p.set(START_DAY, ent.getStartDate());
            // 取込時間
            p.set(START_TIME, ent.getStartDate());
            // ファイル行No.
            p.set(FILE_LINE_NO, ent.getFileLineNo());
            // エラーレベル
            p.set(ERROR_LEVEL, DisplayResource.getErrorLevel(ent.getErrorLevel(), getLocale()));
            // エラー詳細
            p.set(ERROR_DETAIL, DisplayResource.getErrorFlag(ent.getErrorFlag(), getLocale()));
            // 項目
            p.set(ITEM_NO, ent.getItemNo());
            // データ内容
            p.set(DATA, ent.getData());

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
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        LoadErrorInfoSearchKey key = new LoadErrorInfoSearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        key.setStartDate(param.getDate(LOAD_START_DATE));
        key.setJobType(param.getString(DATA_TYPE));

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            key.setFileLineNoOrder(true);
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