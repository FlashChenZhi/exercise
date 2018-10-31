// $Id: LstAreaDASCH.java 4805 2009-08-10 06:28:04Z shibamoto $
package jp.co.daifuku.wms.base.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.base.dasch.LstAreaDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.AreaFinder;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * エリア検索に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 4805 $, $Date: 2009-08-10 15:28:04 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstAreaDASCH
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
    public LstAreaDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // エリアマスタファインダー
        _finder = new AreaFinder(getConnection());
        _finder.open(isForwardOnly());

        // 生成した検索キーでDB検索
        _finder.search(createSearchKey(p, true));

        // 現在点を初期化
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
        // 次データへ移動
        _current++;

        // 全行数が現在行数より大きい場合はtrueを返却
        // 上記以外はfalseを返却
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
        // エリアマスタエンティティの生成
        Area[] ents = (Area[])_finder.getEntities(1);
        // パラメータ配列の生成
        Params p = new Params();

        // 取得した件数分、繰り返す
        for (Area ent : ents)
        {
            // 取得データのセット
            // エリアNo.
            p.set(AREA_NO, ent.getAreaNo());
            // エリア名称
            p.set(AREA_NAME, ent.getValue(Area.AREA_NAME, ""));

            // for抜け(一件のみ取得)
            break;
        }
        // 生成した配列を返却
        return p;
    }

    /**
     * 生成したファインダーとコネクションの破棄処理<BR>
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
        // エリアマスタデータベースハンドラ
        AreaHandler handler = new AreaHandler(getConnection());

        // 対象のデータ件数を取得
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
        // エリアマスタエンティティの生成
        Area[] ents = (Area[])_finder.getEntities(start, start + cnt);
        // 返却用の空配列の生成
        List<Params> params = new ArrayList<Params>();
        // パラメータ配列
        Params p = null;

        // 取得した件数分、繰り返す
        for (Area ent : ents)
        {
            // パラメータ配列の生成
            p = new Params();

            // 取得データのセット
            // ボタン番号
            p.set(SELECT, String.valueOf(params.size() + 1 + start));
            // エリアNo.
            p.set(AREA_NO, ent.getAreaNo());
            // エリア名称
            p.set(AREA_NAME, ent.getValue(Area.AREA_NAME, ""));

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
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        // エリアマスタ検索キー
        AreaSearchKey aKey = new AreaSearchKey();

        // 検索キーのセット
        // エリアNo.
        if (!StringUtil.isBlank(param.getString(AREA_NO)))
        {
            aKey.setAreaNo(param.getString(AREA_NO), ">=");
        }

        // 件数確認ではない場合
        if (isSet)
        {
            // ソートキーのセット
            // エリアNo.
            aKey.setAreaNoOrder(true);
        }
        // 設定した検索キーの返却
        return aKey;
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
