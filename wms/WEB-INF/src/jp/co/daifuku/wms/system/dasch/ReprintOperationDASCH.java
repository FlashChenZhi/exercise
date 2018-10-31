// $Id: Dasch_ja.java 5085 2009-10-01 08:09:16Z okamura $
package jp.co.daifuku.wms.system.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.system.dasch.ReprintOperationDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PrintHistoryFinder;
import jp.co.daifuku.wms.base.dbhandler.PrintHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.PrintHistorySearchKey;
import jp.co.daifuku.wms.base.entity.PrintHistory;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 再発行に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 5085 $, $Date:: 2009-10-01 17:09:16 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okamura $
 */
public class ReprintOperationDASCH
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
    public ReprintOperationDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        _finder = new PrintHistoryFinder(getConnection());
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
        // 帳票発行履歴エンティティ配列
        PrintHistory[] ents = (PrintHistory[])_finder.getEntities(1);
        // 返却パラメータ
        Params p = new Params();

        // 件数分繰り返す
        for (PrintHistory ent : ents)
        {
            // リスト名称
            p.set(LIST_NAME, DisplayText.getText(ent.getListResourcekey()));
            // 発行日時
            p.set(PRINTED_ON, ent.getPrintDate());
            // 発行画面
            p.set(PRINTED_IN_SCREEN, DisplayText.getText(ent.getPagenameResourcekey()));
            // ファイル名
            p.set(FILE_NAME, ent.getFileName());
            // エクスポート定義XMLファイル名
            p.set(XML_FILE_NAME, ent.getXmlFileName());

            // 処理抜け
            break;
        }

        // 生成したパラメータを返却
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
        // 帳票発行履歴ハンドラの生成
        PrintHistoryHandler handler = new PrintHistoryHandler(getConnection());

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
        // 返却パラメータ配列の生成
        List<Params> params = new ArrayList<Params>();
        // 長y法発行履歴エンティティ配列の生成
        PrintHistory[] ents = (PrintHistory[])_finder.getEntities(start, start + cnt);

        // 件数分繰り返し
        for (PrintHistory ent : ents)
        {
            // 返却パラメータの生成
            Params p = new Params();
            // NO
            p.set(NO, String.valueOf(params.size() + 1 + start));
            // リスト名称
            p.set(LIST_NAME, DisplayText.getText(ent.getListResourcekey()));
            // 発行日時
            p.set(PRINTED_ON, ent.getPrintDate());
            // 発行画面
            p.set(PRINTED_IN_SCREEN, DisplayText.getText(ent.getPagenameResourcekey()));
            // ファイル名
            p.set(FILE_NAME, ent.getFileName());
            // エクスポート定義XMLファイル名
            p.set(XML_FILE_NAME, ent.getXmlFileName());

            // 生成したパラメータを配列に格納
            params.add(p);
        }

        // 生成したパラメータ配列を返却
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
        // 帳票発行履歴検索キーの生成
        PrintHistorySearchKey key = new PrintHistorySearchKey();

        // 検索条件、集約条件をセットする
        // リスト名称
        if (!StringUtil.isBlank(param.getString(LIST_NAME)) && !WmsParam.ALL_AREA_NO.equals(param.getString(LIST_NAME)))
        {
            key.setListResourcekey(param.getString(LIST_NAME));
        }

        // 件数確認ではない場合のみ
        if (isSet)
        {
            // 取得項目
            // リスト名称
            key.setListResourcekeyCollect();
            // 発行日時
            key.setPrintDateCollect();
            // 発行画面
            key.setPagenameResourcekeyCollect();
            // ファイル名
            key.setFileNameCollect();
            // XMLファイル名
            key.setXmlFileNameCollect();

            // 並び順
            // 発行日時
            key.setPrintDateOrder(false);
        }

        // 生成した検索キーを返却
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
