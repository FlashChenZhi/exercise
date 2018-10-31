// $Id: RFTStateInquirySCH.java 4274 2009-05-13 04:57:26Z okayama $
package jp.co.daifuku.pcart.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.system.schedule.RFTStateInquirySCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
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
 * Pカート作業状態表示のスケジュール処理を行います。
 *
 * @version $Revision: 4274 $, $Date:: 2009-05-13 13:57:26 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class RFTStateInquirySCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 全完了
     */
    public static final String ODERDISPLAY_RFTNO = "0";

    /**
     * 全欠品完了
     */
    public static final String ODERDISPLAY_AREA = "1";

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
    public RFTStateInquirySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
        RftSearchKey searchKey = new RftSearchKey();

        // RFT号機No.
        if (!StringUtil.isBlank(p.getString(RFT_NO)))
        {
            searchKey.setRftNo(p.getString(RFT_NO));
        }
        // ソート順をセット
        if (ODERDISPLAY_RFTNO.equals(p.getString(COLLECT_CONDITION)))
        {
            searchKey.setRftNoOrder(true);
            searchKey.setAreaNoOrder(true);
        }
        else if (ODERDISPLAY_AREA.equals(p.getString(COLLECT_CONDITION)))
        {
            searchKey.setAreaNoOrder(true);
            searchKey.setRftNoOrder(true);
        }

        // 外部結合
        // RFT管理情報.ユーザID = ユーザ設定情報.ユーザID(+)
        searchKey.setJoin(Rft.USER_ID, "", Com_loginuser.USERID, "(+)");

        // 取得項目
        // 号機No.
        searchKey.setRftNoCollect();
        // 端末区分
        searchKey.setTerminalTypeCollect();
        // 状態
        searchKey.setStatusFlagCollect();
        // ﾕｰｻﾞ名称
        searchKey.setCollect(Com_loginuser.USERNAME);
        // ｴﾘｱNo.
        searchKey.setAreaNoCollect();
        // 作業区分
        searchKey.setJobTypeCollect();
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
        // 最大表示件数分検索結果を取得する
        Rft[] entities = (Rft[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (Rft ent : entities)
        {
            Params param = new Params();

            // RFT号機No.
            param.set(RFT_NO, ent.getRftNo());
            // 端末区分
            param.set(TERMINAL_FLAG, DisplayResource.getTerminalType(ent.getTerminalType()));
            // RFT状態
            param.set(STATUS, DisplayResource.getPCTJobType(ent.getJobType()));
            // ﾕｰｻﾞ名称
            param.set(USER_NAME, String.valueOf(ent.getValue(Com_loginuser.USERNAME, "")));
            // ｴﾘｱNo.
            param.set(AREA_NO, ent.getAreaNo());

            // RFT状態が[未開始]の場合
            if (SystemDefine.JOB_TYPE_UNSTART.equals(ent.getJobType()))
            {
                // RFT作業状態が[停止中]
                if (SystemDefine.RFT_STATUS_FLAG_STOP.equals(ent.getStatusFlag()))
                {
                    // RFT状態に空白をセットする
                    param.set(STATUS, "");
                    // ﾕｰｻﾞ名称
                    param.set(USER_NAME, "");
                    // ｴﾘｱNo.
                    param.set(AREA_NO, "");
                }
            }
            result.add(param);
        }
        // 6001013 = 表示しました。
        setMessage(WmsMessageFormatter.format(6001013));
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
