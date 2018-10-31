// $Id: RFTStateSCH.java 3478 2009-03-16 02:21:54Z okayama $
package jp.co.daifuku.pcart.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.pcart.system.schedule.RFTStateSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.RftFinder;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * Pカート作業状態メンテナンスのスケジュール処理を行います。
 *
 * Designer : H.Okayama<BR>
 * Maker : H.Okayama<BR>
 * @version $Revision: 3478 $, $Date:: 2009-03-16 11:21:54 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class RFTStateSCH
        extends AbstractSCH
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
    public RFTStateSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
            finder = new RftFinder(getConnection());
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
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        // RFT管理情報検索キー
        RftSearchKey searchKey = new RftSearchKey();

        // 検索条件
        // 号機No.
        if (!StringUtil.isBlank(p.getString(RFT_NO)))
        {
            searchKey.setRftNo(p.getString(RFT_NO));
        }

        // 結合条件
        // RFT管理情報.ユーザID = ログインユーザ設定.ユーザID(+)
        searchKey.setJoin(Rft.USER_ID, "", Com_loginuser.USERID, "(+)");

        // 取得項目
        // 号機No.
        searchKey.setRftNoCollect();
        // 端末区分
        searchKey.setTerminalTypeCollect();
        // 状態
        searchKey.setStatusFlagCollect();
        // ユーザ名称
        searchKey.setCollect(Com_loginuser.USERNAME);
        // エリアNo.
        searchKey.setAreaNoCollect();
        // 作業状態
        searchKey.setJobTypeCollect();

        // ソート順
        // 号機No.(昇順)
        searchKey.setRftNoOrder(true);

        // 生成した検索キーを返却
        return searchKey;
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
        // RFT管理情報エンティティにて検索結果を取得
        Rft[] entities = (Rft[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        // パラメータ配列の生成
        List<Params> result = new ArrayList<Params>();
        // パラメータの生成
        Params param = null;
        // 選択番号
        int i = 1;

        for (Rft ent : entities)
        {
            // パラメータの生成
            param = new Params();

            // 選択
            param.set(SELECT, String.valueOf(i));
            // 詳細
            param.set(DETAIL, DisplayText.getText("LBL-T0085"));
            // 号機No.
            param.set(RFT_NO, ent.getRftNo());
            // 端末区分
            param.set(TERMINAL_TYPE, DisplayResource.getTerminalType(ent.getTerminalType()));
            // 状態
            param.set(STATUS, DisplayResource.getPCTJobType(ent.getJobType()));
            // ユーザ名称
            param.set(USER_NAME, ent.getValue(Com_loginuser.USERNAME, ""));
            // エリアNo.
            param.set(AREA_NO, ent.getAreaNo());

            // 作業区分が"未開始"の場合
            if (SystemDefine.JOB_TYPE_UNSTART.equals(ent.getJobType()))
            {
                // 作業フラグが"停止中"の場合
                if (SystemDefine.RFT_STATUS_FLAG_STOP.equals(ent.getStatusFlag()))
                {
                    // 状態
                    param.set(STATUS, "");
                    // ユーザ名称
                    param.set(USER_NAME, "");
                    // エリアNo.
                    param.set(AREA_NO, "");
                }
            }
            // 生成したパラメータを配列に格納
            result.add(param);

            // 選択番号をインクリメント
            i++;
        }
        // 生成したパラメータを返却
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
