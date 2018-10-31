// $Id: LstRegistCustomerDASCH.java 4803 2009-08-10 06:24:09Z shibamoto $
package jp.co.daifuku.pcart.master.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.dasch.LstRegistCustomerDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.master.schedule.PCTMasterInParameter;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 出荷先検索リストボックス(登録用)に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 4803 $, $Date:: 2009-08-10 15:24:09 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstRegistCustomerDASCH
        extends AbstractWmsDASCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    /** 保存用のフィールド 荷主名称(<code>CONSIGNOR_NAME</code>) */
    private FieldName _CUSTOMER_NAME = new FieldName("", "CUSTOMER_NAME");

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
    public LstRegistCustomerDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // PCT出庫予定情報
        _finder = new PCTRetPlanFinder(getConnection());
        // Initialize record counts
        _finder.open(isForwardOnly());
        // 生成した検索キーでDB検索
        _finder.search(createSearchKey(p, true));

        // 現在点の初期化
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
        //  PCT出庫予定情報エンティティ
        PCTRetPlan[] ents = (PCTRetPlan[])_finder.getEntities(1);
        // パラメータ配列の生成
        Params p = new Params();

        // 取得した件数分、繰り返す
        for (PCTRetPlan ent : ents)
        {
            // 出荷先コード
            p.set(CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名称
            p.set(CUSTOMER_NAME, ent.getValue(_CUSTOMER_NAME));

            // for文抜け(一件のみ取得)
            break;
        }

        // 生成した配列を返却
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
        // PCT出庫予定情報データベースハンドラ
        PCTRetPlanHandler handler = new PCTRetPlanHandler(getConnection());

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
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        // 返却用の空配列を生成
        List<Params> params = new ArrayList<Params>();

        // PCT出庫予定情報エンティティの生成
        PCTRetPlan[] ents = (PCTRetPlan[])_finder.getEntities(start, start + cnt);

        for (PCTRetPlan ent : ents)
        {
            // パラメータ配列の生成
            Params p = new Params();

            // 取得データのセット
            // ボタン番号
            p.set(SELECT, String.valueOf(params.size() + 1 + start));
            // 出荷先コード
            p.set(CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名称
            p.set(CUSTOMER_NAME, ent.getValue(_CUSTOMER_NAME));
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
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        PCTRetPlanSearchKey key = new PCTRetPlanSearchKey();

        // 検索条件、集約条件をセットする
        // 検索キーのセット
        // 荷主コード
        // 荷主コード
        if (!StringUtil.isBlank(param.getString(CONSIGNOR_CODE)))
        {
            key.setConsignorCode(param.getString(CONSIGNOR_CODE));
        }
        // 出荷先コード
        if (!StringUtil.isBlank(param.getString(CUSTOMER_CODE)))
        {
            if (DBFormat.isPatternMatching(param.getString(CUSTOMER_CODE)))
            {
                // ワイルドカード検索
                key.setCustomerCode(param.getString(CUSTOMER_CODE));
            }
            else
            {
                // 入力された値以上を条件とする
                key.setCustomerCode(param.getString(CUSTOMER_CODE), ">=");
            }
        }

        // 状態フラグ
        key.setStatusFlag(PCTMasterInParameter.STATUS_FLAG_DELETE, "!=");

        // 集約キーのセット
        key.setCustomerCodeGroup();

        if (isSet)
        {
            // OrderByや、collect項目を記載する
            // 昇順にソート
            key.setCustomerCodeOrder(true);

            // 取得キーのセット
            key.setCustomerCodeCollect();
            key.setCollect(PCTRetPlan.CUSTOMER_NAME, "MAX", _CUSTOMER_NAME);
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
