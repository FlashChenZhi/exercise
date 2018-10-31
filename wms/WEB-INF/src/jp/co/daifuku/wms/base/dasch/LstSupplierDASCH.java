// $Id: LstSupplierDASCH.java 6322 2009-12-03 01:27:57Z okamura $
package jp.co.daifuku.wms.base.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.base.dasch.LstSupplierDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.SupplierFinder;
import jp.co.daifuku.wms.base.dbhandler.SupplierHandler;
import jp.co.daifuku.wms.base.dbhandler.SupplierSearchKey;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.base.common.InParameter;

/**
 * 仕入先検索に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 6322 $, $Date: 2009-12-03 10:27:57 +0900 (木, 03 12 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okamura $
 */
public class LstSupplierDASCH
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
    public LstSupplierDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // 仕入先マスタファインダー
        _finder = new SupplierFinder(getConnection());
        _finder.open(isForwardOnly());

        // 生成した検索キーでDB検索
        _finder.search(createSearchKey(p, true));

        // 現在点の初期化
        _current = -1;
    }

    /**
     * 次のデータが存在するかどうかを判定します。
     * 帳票出力で使用します。
     * @return データが存在する場合はtrueを返します。
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean next()
            throws CommonException
    {
        // 次データへ移動
        _current++;

        // 全行数が現在行数より大きい場合はtrueを返却
        // 上記以外はfalseを返却
        return _total > _current;
    }

    /**
     * データを1件返します。
     * 帳票出力で使用します。
     * @return 取得データ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public Params get()
            throws CommonException
    {
        // 仕入先マスタエンティティの生成
        Supplier[] ents = (Supplier[])_finder.getEntities(1);
        // パラメータ配列の生成
        Params p = new Params();

        // 取得した件数分、繰り返す
        for (Supplier ent : ents)
        {
            // 取得データのセット
            // 仕入先コード
            p.set(SUPPLIER_CODE, ent.getSupplierCode());
            // 仕入先名称
            p.set(SUPPLIER_NAME, ent.getValue(Supplier.SUPPLIER_NAME, ""));

            // for文抜け(一件のみ取得)
            break;
        }
        // 生成した配列を返却
        return p;
    }

    /**
     * 生成したファインダーとコネクションの破棄処理
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
        // 仕入先マスタデータベースハンドラ
        SupplierHandler handler = new SupplierHandler(getConnection());

        // 対象情報のデータ件数を取得
        _total = handler.count(createSearchKey(p, false));

        // 取得したデータ件数を返却
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
        // 仕入先マスタエンティティの生成
        Supplier[] ents = (Supplier[])_finder.getEntities(start, start + cnt);
        // 返却用の空配列を生成
        List<Params> params = new ArrayList<Params>();
        // パラメータ配列
        Params p = null;

        // 取得した件数分、繰り返す
        for (Supplier ent : ents)
        {
            // パラメータ配列の生成
            p = new Params();

            // 取得データのセット
            // ボタン番号
            p.set(SELECT, String.valueOf(params.size() + 1 + start));
            // 仕入先コード
            p.set(SUPPLIER_CODE, ent.getSupplierCode());
            // 仕入先名称
            p.set(SUPPLIER_NAME, ent.getValue(Supplier.SUPPLIER_NAME, ""));

            // 設定した値を配列に格納
            params.add(p);
        }
        // 設定した配列を返却
        return params;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 検索条件のセットを行います。
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        // 仕入先マスタ検索キー
        SupplierSearchKey sKey = new SupplierSearchKey();

        // 検索キーのセット
        // 荷主コード
        sKey.setConsignorCode(param.getString(CONSIGNOR_CODE));
        // 開始終了フラグのチェック
        if (!StringUtil.isBlank(param.getString(FROM_TO_FLAG)))
        {
            if (InParameter.FROM_TO_FLAG_FROM.equals(param.getString(FROM_TO_FLAG)))
            {
                // 開始側の検索の場合
                if (!StringUtil.isBlank(param.getString(SUPPLIER_CODE)))
                {
                    // 開始仕入先コードが入力されている場合
                    sKey.setSupplierCode(param.getString(SUPPLIER_CODE), ">=");
                }
                if (!StringUtil.isBlank(param.getString(TO_SUPPLIER_CODE)))
                {
                    // 終了仕入先コードが入力されている場合
                    sKey.setSupplierCode(param.getString(TO_SUPPLIER_CODE), "<=");
                }
            }
            else if (InParameter.FROM_TO_FLAG_TO.equals(param.getString(FROM_TO_FLAG)))
            {
                // 終了側の検索の場合
                if (!StringUtil.isBlank(param.getString(TO_SUPPLIER_CODE)))
                {
                    // 終了仕入先コードが入力されている場合
                    sKey.setSupplierCode(param.getString(TO_SUPPLIER_CODE), ">=");
                }
                else if (!StringUtil.isBlank(param.getString(SUPPLIER_CODE)))
                {
                    // 開始仕入先コードが入力されている場合
                    sKey.setSupplierCode(param.getString(SUPPLIER_CODE), ">=");
                }
            }
        }
        else
        {
            // 範囲指定の無い検索の場合
            if (!StringUtil.isBlank(param.getString(SUPPLIER_CODE)))
            {
                sKey.setSupplierCode(param.getString(SUPPLIER_CODE), ">=");
            }
        }

        // 件数確認ではない場合
        if (isSet)
        {
            // ソートキーのセット
            // 仕入先コード
            sKey.setSupplierCodeOrder(true);
        }
        // 設定した検索キーを返却
        return sKey;
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