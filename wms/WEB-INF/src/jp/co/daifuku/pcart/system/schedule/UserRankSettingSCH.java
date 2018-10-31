// $Id: UserRankSettingSCH.java 4274 2009-05-13 04:57:26Z okayama $
package jp.co.daifuku.pcart.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.system.schedule.UserRankSettingSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTUserRankingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTUserRankingFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTUserRankingHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTUserRankingSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.PCTUserRanking;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * ユーザレベル設定のスケジュール処理を行います。
 *
 * @version $Revision: 4274 $, $Date: 2009-05-13 13:57:26 +0900 (水, 13 5 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class UserRankSettingSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 全ランク
     */
    private static final String ALL_RANK = "99";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public UserRankSettingSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
        // ハンドラインスタンス生成
        AbstractDBFinder finder = null;
        try
        {
            finder = new PCTUserRankingFinder(getConnection());
            finder.open(true);
            // 検索処理実行
            // 取得件数に応じてメッセージを設定
            if (!canLowerDisplay(finder.search(createSearchKey(p))))
            {
                return new ArrayList<Params>();
            }

            // エンティティを画面表示用にパラメータクラスにセットし返す
            return getDisplayData(finder);
        }
        finally
        {
            // 検索で使用したFinderをcloseする
            closeFinder(finder);
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
     * 検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return searchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        PCTUserRankingSearchKey searchKey = new PCTUserRankingSearchKey();

        // PCTユーザランク情報の検索キーをセット
        // パラメータで受け取ったPCTSystemInParameterに以下をセットする
        String[] tmp = WmsFormatter.getFromTo(p.getString(USER_ID_FROM), p.getString(USER_ID_TO));
        String from = tmp[0];
        String to = tmp[1];

        // ユーザ(FROM)
        if (!StringUtil.isBlank(from))
        {
            searchKey.setUserId(from, ">=");
        }
        // ユーザ(TO)
        if (!StringUtil.isBlank(to))
        {
            searchKey.setUserId(to, "<=");
        }

        // ランク
        if (!StringUtil.isBlank(p.getString(LEVEL)))
        {
            // 全ランク以外が選ばれている場合
            if (!ALL_RANK.equals(p.getString(LEVEL)))
            {
                searchKey.setRank(p.getString(LEVEL));
            }
        }

        // DAIFUKUユーザ以外
        // ユーザIDが指定されていたら、それ以降全てを検索
        searchKey.setUserId("DAIFUKU", "!=", "", "", false);

        //------------------------------------------------------------
        // 結合条件の指定
        //------------------------------------------------------------
        searchKey.setJoin(PCTUserRanking.USER_ID, Com_loginuser.USERID);

        //------------------------------------------------------------
        // 順序の指定
        //------------------------------------------------------------
        // ランク
        searchKey.setRankOrder(true);
        // ユーザID
        searchKey.setUserIdOrder(true);

        //------------------------------------------------------------
        // 取得項目の指定
        //------------------------------------------------------------
        // ユーザID
        searchKey.setUserIdCollect();

        // ユーザ名称
        searchKey.setCollect(Com_loginuser.USERNAME);

        // ランク
        searchKey.setRankCollect();

        // 最終更新日時
        searchKey.setLastUpdateDateCollect();

        return searchKey;
    }

    /**
     * ユーザーランク更新処理<BR>
     * startParamsで指定するParameterクラスはPCTSystemInParameter型であること。<BR>
     * @param startParams 開始条件
     * @return 正常：<CODE>true</CODE>異常：<CODE>false</CODE>
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        //日次更新チェック
        if (!canStart())
        {
            return false;
        }
        // 取り込み中チェック
        if (isLoadData())
        {
            return false;
        }

        PCTUserRankingHandler userRankingHandler = new PCTUserRankingHandler(getConnection());
        PCTUserRankingAlterKey userRankingAKey = new PCTUserRankingAlterKey();
        PCTUserRankingSearchKey userRankingSKey = new PCTUserRankingSearchKey();
        PCTUserRanking[] pctUserRankEnt;

        try
        {
            if (ArrayUtil.isEmpty(ps))
            {
                // 6003013=修正対象データがありませんでした。
                setMessage(WmsMessageFormatter.format(6003013));
                return false;
            }

            for (int i = 0; i < ps.length; i++)
            {
                // ユーザランク情報の検索キークリア
                userRankingSKey.clear();
                // ユーザID
                userRankingSKey.setUserId(ps[i].getString(USER_ID));

                // 更新対象データをロック
                pctUserRankEnt = (PCTUserRanking[])userRankingHandler.findForUpdate(userRankingSKey);

                // 更新対象データが存在する場合は、更新処理を行なう。
                if (!ArrayUtil.isEmpty(pctUserRankEnt))
                {
                    userRankingAKey.clear();

                    // 更新条件
                    // ユーザID
                    userRankingAKey.setUserId(ps[i].getString(USER_ID));
                    // 最終更新日
                    userRankingAKey.setLastUpdateDate(ps[i].getDate(LAST_UPDATE_DATE));

                    // 更新項目
                    // ランク
                    userRankingAKey.updateRank(ps[i].getString(SET_LEVEL));
                    // 最終更新処理名
                    userRankingAKey.updateLastUpdatePname(this.getClass().getSimpleName());
                    // 更新処理
                    userRankingHandler.modify(userRankingAKey);
                }
            }
            // 6001006=設定しました。
            setMessage(WmsMessageFormatter.format(6001006));
            return true;
        }
        catch (NotFoundException e)
        {
            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, ps[0].getRowIndex()));
            setErrorRowIndex(ps[0].getRowIndex());
            setNgCellRow(ps[0].getRowIndex());

            return false;
        }
    }

    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder)
            throws ReadWriteException
    {
        // 最大表示件数分検索結果を取得する
        PCTUserRanking[] entities = (PCTUserRanking[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (PCTUserRanking ent : entities)
        {
            Params param = new Params();

            // 返却データをセットする
            // ユーザID
            param.set(USER_ID, ent.getUserId());
            // ユーザ名称
            param.set(USER_NAME, String.valueOf(ent.getValue(Com_loginuser.USERNAME, "")));
            // 現在ランク
            param.set(PRE_LEVEL, ent.getRank());
            // 最終更新日
            param.set(LAST_UPDATE_DATE, ent.getLastUpdateDate());

            result.add(param);
        }
        return result;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

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
