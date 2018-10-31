// $Id: LstRetrievalPlanMntDASCH.java 4808 2009-08-10 06:32:12Z shibamoto $
package jp.co.daifuku.wms.retrieval.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.retrieval.dasch.LstRetrievalPlanMntDASCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 出庫予定検索に必要なリストボックスや帳票の検索処理を行います。
 * 
 * @version $Revision: 4808 $, $Date: 2009-08-10 15:32:12 +0900 (月, 10 8 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstRetrievalPlanMntDASCH
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
    public LstRetrievalPlanMntDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
        // 出庫予定情報ファインダー
        _finder = new RetrievalPlanFinder(getConnection());
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
        // 出庫予定情報エンティティ
        RetrievalPlan[] ents = (RetrievalPlan[])_finder.getEntities(1);
        // パラメータ配列の生成
        Params p = new Params();

        // 取得した件数分、繰り返す
        for (RetrievalPlan ent : ents)
        {
            // 出庫予定日
            p.set(PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            // 伝票No.
            p.set(SHIP_TICKET_NO, ent.getShipTicketNo());

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
        // 出庫予定情報データベースハンドラ
        RetrievalPlanHandler handler = new RetrievalPlanHandler(getConnection());

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
        // 出庫予定情報エンティティの生成
        RetrievalPlan[] ents = (RetrievalPlan[])_finder.getEntities(start, start + cnt);
        // 返却用の空配列を生成
        List<Params> params = new ArrayList<Params>();
        // パラメータ配列
        Params p = null;

        // 取得した件数分、繰り返す
        for (RetrievalPlan ent : ents)
        {
            // パラメータ配列の生成
            p = new Params();

            // 取得データのセット
            // ボタン番号
            p.set(SELECT, String.valueOf(params.size() + 1 + start));
            // 出庫予定日
            p.set(PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            // 伝票No.
            p.set(SHIP_TICKET_NO, ent.getShipTicketNo());

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
        // 出庫予定情報検索キー
        RetrievalPlanSearchKey rKey = new RetrievalPlanSearchKey();

        // 検索キーのセット
        // 出庫予定日
        if (!StringUtil.isBlank(param.getString(PLAN_DAY)))
        {
            rKey.setPlanDay(WmsFormatter.toParamDate(param.getDate(PLAN_DAY)));
        }
        // 伝票No.
        if (!StringUtil.isBlank(param.getString(SHIP_TICKET_NO)))
        {
            rKey.setShipTicketNo(param.getString(SHIP_TICKET_NO));
        }
        // 荷主コード
        rKey.setConsignorCode(param.getString(CONSIGNOR_CODE));
        // 状態フラグ
        if (!StringUtil.isBlank(param.getString(STATUS_FLAG)))
        {
            rKey.setStatusFlag(param.getString(STATUS_FLAG));
        }
        // 該当しない場合は削除以外
        else
        {
            // 状態フラグ(削除ではない)
            rKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
        }

        // スケジュールフラグ
        if (!StringUtil.isBlank(param.getString(SCHEDULE_FLAG)))
        {
            rKey.setSchFlag(param.getString(SCHEDULE_FLAG));
        }

        // 集約キーのセット
        // 予定日
        rKey.setPlanDayGroup();
        // 伝票No
        rKey.setShipTicketNoGroup();

        // 件数確認ではない場合
        if (isSet)
        {
            // ソートキーのセット
            // 出庫予定日
            rKey.setPlanDayOrder(true);
            // 伝票No.
            rKey.setShipTicketNoOrder(true);

            // 取得キーのセット
            // 出庫予定日
            rKey.setPlanDayCollect();
            // 伝票No.
            rKey.setShipTicketNoCollect();
        }
        // 設定した検索キーを返却
        return rKey;
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
