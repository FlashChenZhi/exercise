// $Id: PCTSystemSettingSCH.java 7162 2010-02-19 09:32:59Z shibamoto $
package jp.co.daifuku.pcart.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.system.schedule.PCTSystemSettingSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.pcart.base.util.PCTDisplayUtil;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemSearchKey;
import jp.co.daifuku.wms.base.entity.PCTSystem;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * Pカートシステム設定のスケジュール処理を行います。
 *
 * @version $Revision: 7162 $, $Date: 2010-02-19 18:32:59 +0900 (金, 19 2 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class PCTSystemSettingSCH
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
    public PCTSystemSettingSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
            finder = new PCTSystemFinder(getConnection());
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

    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param startParams 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        try
        {
            // 日次更新チェック
            if (!canStart())
            {
                return false;
            }

            // 入力妥当性チェック
            if (ps[0].getBoolean(SETTING_FLAG))

            {
                if (ps[0].getInt(A_RANK_STANDARD_VALUE) < ps[0].getInt(B_RANK_STANDARD_VALUE))
                {
                    // Bランクの値がAランクの値を上回る場合
                    // 6023630=Bランク基準値がAランク基準値を上回っています。
                    setMessage("6023630");
                    return false;
                }
                if (ps[0].getInt(A_RANK_STANDARD_VALUE) < PCTSystemInParameter.RANK_A_LOWER_VALUE)
                {
                    // Aランクの値が101より小さい場合
                    // 6023616={ランクA基準値}には{101}以上の値を入力してください。
                    setMessage(WmsMessageFormatter.format(6023616, DispResources.getText("LBL-P0183"),
                            PCTSystemInParameter.RANK_A_LOWER_VALUE));
                    return false;
                }
                if (ps[0].getInt(B_RANK_STANDARD_VALUE) > PCTSystemInParameter.RANK_B_UPPER_VALUE)
                {
                    // Bランクの値が99より大きい場合
                    // 6003020={ランクB基準値}には{99}以下の値を入力してください。
                    setMessage(WmsMessageFormatter.format(6003020, DispResources.getText("LBL-P0184"),
                            PCTSystemInParameter.RANK_B_UPPER_VALUE));
                    return false;
                }
            }

            // PCTシステム情報ハンドラの生成
            PCTSystemHandler systemHandler = new PCTSystemHandler(getConnection());
            PCTSystemAlterKey systemAlterKey = new PCTSystemAlterKey();

            // 更新値セット
            // 設定ボタン押下時
            if (ps[0].getBoolean(SETTING_FLAG))
            {
                // 作業ランク設定方法
                systemAlterKey.updateRankSettingFlag(ps[0].getString(RANK_SETTING_FLAG));
                // ランクA基準値
                systemAlterKey.updateARankStandardValue(ps[0].getInt(A_RANK_STANDARD_VALUE));
                // ランクB基準値
                systemAlterKey.updateBRankStandardValue(ps[0].getInt(B_RANK_STANDARD_VALUE));
                // 重量誤差率初期値
                systemAlterKey.updateDefultDistinctRate(ps[0].getInt(DEFULT_DISTINCT_RATE));
                // オーダー最大重量
                systemAlterKey.updateOrderMaxWeight(ps[0].getInt(ORICON_MAX_WEIGHT));
                // センター名
                systemAlterKey.updateCenterName(ps[0].getString(CENTER_NAME));
            }
            // 未処理ボタン押下時
            else if (!StringUtil.isBlank(ps[0].getString(PCTMASTER_LOAD_FLAG)))
            {
                systemAlterKey.updatePctmasterLoadFlag(SystemDefine.PCTMASTER_LOAD_FLAG_STOP);
            }

            // 最終更新処理名
            systemAlterKey.updateLastUpdatePname(this.getClass().getSimpleName());
            // 更新処理
            systemHandler.modify(systemAlterKey);

            // 6001006=設定しました。
            setMessage(WmsMessageFormatter.format(6001006));
            return true;
        }
        catch (ReadWriteException e)
        {
            // データベースエラーが発生しました。メッセージログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            throw e;
        }
        catch (NotFoundException e)
        {
            // 6023115 = 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023115));
            return false;
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
     * @return PCTSystemSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        PCTSystemSearchKey searchKey = new PCTSystemSearchKey();

        searchKey.setCollect(new FieldName(PCTSystem.STORE_NAME, FieldName.ALL_FIELDS));

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
        PCTSystem[] entities = (PCTSystem[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (PCTSystem ent : entities)
        {
            Params param = new Params();

            // 返却データをセットする
            // 作業基準値設定方法
            param.set(RANK_SETTING_FLAG, ent.getRankSettingFlag());
            // ランクA基準値
            param.set(A_RANK_STANDARD_VALUE, ent.getARankStandardValue());
            // ランクB基準値
            param.set(B_RANK_STANDARD_VALUE, ent.getBRankStandardValue());
            // 重量誤差率初期値
            param.set(DEFULT_DISTINCT_RATE, ent.getDefultDistinctRate());
            // オリコン最大重量
            param.set(ORICON_MAX_WEIGHT, ent.getOrderMaxWeight());
            // センター名
            param.set(CENTER_NAME, ent.getCenterName());
            // PCT商品マスタ取込フラグ
            param.set(PCTMASTER_LOAD_FLAG, ent.getPctmasterLoadFlag());
            // PCT商品マスタ取込表示用
            param.set(IN_LOAD_PCTITEM_MASTER, PCTDisplayUtil.getPCTMasterFlag(ent.getPctmasterLoadFlag()));

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
