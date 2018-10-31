// $Id: FaDataLoadDASCH.java 5687 2009-11-12 02:27:48Z okamura $
package jp.co.daifuku.wms.base.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.base.dasch.FaDataLoadDASCHParams.*;

import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.ExchangeHistoryFinder;
import jp.co.daifuku.wms.base.dbhandler.ExchangeHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.ExchangeHistorySearchKey;
import jp.co.daifuku.wms.base.entity.ExchangeHistory;
import jp.co.daifuku.wms.base.entity.LoadErrorInfo;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * BusiTuneで生成されたDASCHクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5687 $, $Date:: 2009-11-12 11:27:48 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okamura $
 */
public class FaDataLoadDASCH
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
    public FaDataLoadDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new ExchangeHistoryFinder(getConnection());
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
        // get Next entity from finder class
        ExchangeHistory[] ents = (ExchangeHistory[])_finder.getEntities(1);
        Params p = new Params();
        // conver Entity to Param object
        for (ExchangeHistory ent : ents)
        {
            p.set(DFK_DS_NO, getDsNumber());
            p.set(DFK_USER_ID, getUserId());
            p.set(DFK_USER_NAME, getUserName());
            p.set(SYS_DATE, getPrintDate());

            p.set(LOAD_TYPE, DisplayResource.getLoadDataType(ent.getJobType(), getLocale()));
            p.set(FILE_NAME, ent.getFileName());
            p.set(START_DATE, ent.getStartDate());

            p.set(STATUS, DisplayResource.getExchangeStatus(ent.getStatus(), getLocale()));
            
            p.set(FILE_LINE_NO, ent.getValue(LoadErrorInfo.FILE_LINE_NO, ""));
            p.set(ERROR_LEVEL, DisplayResource.getErrorLevel((String)ent.getValue(LoadErrorInfo.ERROR_LEVEL, ""), getLocale()));
            if (StringUtil.isBlank(ent.getValue(LoadErrorInfo.ERROR_FLAG, "")))
            {
                p.set(ERROR_DETAIL, "");
            }
            else
            {
                p.set(ERROR_DETAIL, DisplayText.getText(getLocale(), (String)ent.getValue(LoadErrorInfo.ERROR_FLAG, "")));
            }
            p.set(ITEM_NO, (String)ent.getValue(LoadErrorInfo.ITEM_NO, ""));
            p.set(DATA, (String)ent.getValue(LoadErrorInfo.DATA, ""));
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
        ExchangeHistoryHandler handler = new ExchangeHistoryHandler(getConnection());

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
        throw new RuntimeException("This method is not implemented.");
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
        ExchangeHistorySearchKey key = new ExchangeHistorySearchKey();

        // 検索条件、集約条件をセットする
        // where, group by
        // 交換データ通信履歴が「正常完了」以外のデータを対象とする。
        key.setStatus(ExchangeHistory.STATUS_NORMAL, "!=");
        key.setFileName(param.getString(FILE_NAME));
        key.setStartDate(param.getDate(START_LOAD_DATE));

        key.setJoin(ExchangeHistory.START_DATE, "", LoadErrorInfo.START_DATE, "(+)");
        
        if (isSet)
        {
            // 登録日時が同じ場合もあるので行も条件に追加
            key.setOrder(LoadErrorInfo.REGIST_DATE, true);
            key.setOrder(LoadErrorInfo.FILE_LINE_NO, true);
            
            key.setCollect(new FieldName(ExchangeHistory.STORE_NAME, FieldName.ALL_FIELDS));
            // ファイル行No.
            key.setCollect(LoadErrorInfo.FILE_LINE_NO);
            // エラーレベル
            key.setCollect(LoadErrorInfo.ERROR_LEVEL);
            // エラー区分
            key.setCollect(LoadErrorInfo.ERROR_FLAG);
            // 項目番号
            key.setCollect(LoadErrorInfo.ITEM_NO);
            // データ
            key.setCollect(LoadErrorInfo.DATA);
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
