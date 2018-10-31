// $Id: ZoneMentenanceSCH.java 4259 2009-05-12 05:33:55Z okayama $
package jp.co.daifuku.pcart.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.schedule.ZoneMentenanceSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ZoneAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ZoneFinder;
import jp.co.daifuku.wms.base.dbhandler.ZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.ZoneSearchKey;
import jp.co.daifuku.wms.base.entity.Zone;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * ゾーンメンテナンス(エリア、ゾーン)のスケジュール処理を行います。
 *
 * @version $Revision: 4259 $, $Date: 2009-05-12 14:33:55 +0900 (火, 12 5 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class ZoneMentenanceSCH
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
    public ZoneMentenanceSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
            finder = new ZoneFinder(getConnection());
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
        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }

        // 取り込み中チェック
        if (isLoadData())
        {
            return false;
        }

        // 処理区分にて
        if (PCTMasterInParameter.PROCESS_FLAG_DELETE.equals(ps[0].getString(PROCESS_FLAG)))
        {
            // 削除処理
            for (int lc = 0; lc < ps.length; lc++)
            {
                boolean w_status = zoneDelete(ps[lc]);
                if (!w_status)
                {
                    return false;
                }
            }

            // 6001005=削除しました。
            setMessage("6001005");
            return true;
        }
        else if (PCTMasterInParameter.PROCESS_FLAG_MODIFY.equals(ps[0].getString(PROCESS_FLAG)))
        {
            // 修正処理
            for (int lc = 0; lc < ps.length; lc++)
            {
                boolean w_status = zoneModify(ps[lc]);
                if (!w_status)
                {
                    return false;
                }
            }

            // 6001006=設定しました。
            setMessage("6001006");
            return true;
        }

        // 6001006=設定しました。
        setMessage("6001006");
        return true;
    }

    /**
     * メンテナンスチェック<BR>
     * checkParam内の条件に合致するゾーンマスタ情報が他のマスタテーブルに存在するか確認します。<BR>
     * 他のテーブルにゾーンマスタ関連情報が存在する場合はtrueを、見つからない場合はfalseを返します。<BR>
     * 画面側の設定事前チェック処理で使用します。<BR>
     * <BR>
     * 
     * @param ps
     *            チェック内容
     * @throws CommonException
     *             全てのユーザ定義例外を報告します。
     * @return inParam
     */
    public boolean deleteCheck(ScheduleParams... ps)
            throws CommonException
    {
        return existRelation(ps[0]);
    }

    /**
     * メンテナンスチェック<BR>
     * checkParam内の条件に合致するゾーンマスタ情報が他のマスタテーブルに存在するか確認します。<BR>
     * 他のテーブルにゾーンマスタ関連情報が存在する場合はtrueを、見つからない場合はfalseを返します。<BR>
     * 画面側の設定事前チェック処理で使用します。<BR>
     * <BR>
     * 
     * @param ps
     * @return boolean 更新可能時：true 更新不可時：false
     * @throws CommonException
     *             全てのユーザ定義例外を報告します。
     */
    public boolean check(ScheduleParams... ps)
            throws CommonException
    {

        for (int lc = 0; lc < ps.length; lc++)
        {
            if (!Boolean.parseBoolean(ps[lc].getString(NEW_DATA)))
            {
                if (!existRelation(ps[lc]))
                {
                    return false;
                }
            }
        }

        return rangeCheck(ps);
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
        ZoneSearchKey searchKey = new ZoneSearchKey();

        // エリア
        searchKey.setAreaNo(p.getString(AREA_NO));
        // 取得順序
        // 作業ゾーンNoの昇順
        searchKey.setWorkZoneNoOrder(true);
        // ゾーンNoの昇順
        searchKey.setZoneNoOrder(true);


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
        Zone[] entities = (Zone[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (Zone ent : entities)
        {
            Params param = new Params();

            // 作業ゾーンNo
            param.set(WORK_ZONE_NO, ent.getWorkZoneNo());
            // ゾーンNo
            param.set(ZONE_NO, ent.getZoneNo());
            // エリアNo
            param.set(AREA_NO, ent.getAreaNo());
            // 最終更新日時
            param.set(LAST_UPDATE_DATE, ent.getLastUpdateDate());
            //            param.set(LAST_UPDATE_DATE, WmsFormatter.toParamDateTime(ent.getLastUpdateDate(), WmsFormatter.MILISEC_LENGTH));

            result.add(param);
        }

        return result;
    }

    /**
     * ゾーンマスタ更新処理<BR>
     * 
     * @param inputParam
     *            処理情報
     * @return 削除完了時：<CODE>True</CODE><BR>
     *         該当データが存在しない：<CODE>False</CODE><BR>
     * @throws CommonException
     *             全てのユーザ定義例外を報告します。
     */
    protected boolean zoneModify(ScheduleParams... inParam)
            throws CommonException
    {
        try
        {
            // 新規情報時、登録処理
            if (Boolean.parseBoolean(inParam[0].getString(NEW_DATA)))
            {
                // ゾーンマスタハンドラ
                ZoneHandler zHandler = new ZoneHandler(getConnection());
                // ゾーンマスタの登録
                Zone zone = new Zone();

                // エリア
                zone.setAreaNo(inParam[0].getString(AREA_NO));
                // 作業ゾーンNo
                zone.setWorkZoneNo(inParam[0].getString(WORK_ZONE_NO));
                // ゾーンNo
                zone.setZoneNo(inParam[0].getString(ZONE_NO));
                // システム管理区分：ユーザ
                zone.setManagementType(PCTMasterInParameter.MANAGEMENT_TYPE_USER);

                String pname = this.getClass().getSimpleName();
                // 登録処理名
                zone.setRegistPname(pname);
                // 最終更新処理名
                zone.setLastUpdatePname(pname);

                zHandler.create(zone);
            }
            else
            {
                // ゾーンマスタの更新
                // 検索キー
                ZoneHandler zHandler = new ZoneHandler(getConnection());
                ZoneAlterKey zAlterKey = new ZoneAlterKey();

                // エリア
                if (!StringUtil.isBlank(inParam[0].getString(AREA_NO)))
                {
                    zAlterKey.setAreaNo(inParam[0].getString(AREA_NO));
                }

                // 作業ゾーンNo
                if (!StringUtil.isBlank(inParam[0].getString(HIDDEN_WORK_ZONE_NO)))
                {
                    zAlterKey.setWorkZoneNo(inParam[0].getString(HIDDEN_WORK_ZONE_NO));
                }

                // ゾーンNo
                if (!StringUtil.isBlank(inParam[0].getString(HIDDEN_ZONE_NO)))
                {
                    zAlterKey.setZoneNo(inParam[0].getString(HIDDEN_ZONE_NO));
                }

                // 最終更新日
                if (!StringUtil.isBlank(inParam[0].getString(LAST_UPDATE_DATE)))
                {
                    zAlterKey.setLastUpdateDate(inParam[0].getDate(LAST_UPDATE_DATE));
                }

                // 作業ゾーンNo：更新値
                zAlterKey.updateWorkZoneNo(inParam[0].getString(WORK_ZONE_NO));
                // ゾーンNo：更新値
                zAlterKey.updateZoneNo(inParam[0].getString(ZONE_NO));
                // 最終更新処理名：更新値
                zAlterKey.updateLastUpdatePname(this.getClass().getSimpleName());

                // 該当ゾーン情報を更新します。
                zHandler.modify(zAlterKey);
            }
        }
        catch (NotFoundException ex)
        {
            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, inParam[0].getRowIndex()));
            setErrorRowIndex(inParam[0].getRowIndex());
            setNgCellRow(inParam[0].getRowIndex());
            return false;
        }

        return true;
    }

    /**
     * ゾーンマスタ削除処理<BR>
     * 
     * @param inputParam
     *            処理情報
     * @return 削除完了時：<CODE>True</CODE><BR>
     *         該当データが存在しない：<CODE>False</CODE><BR>
     * @throws CommonException
     *             全てのユーザ定義例外を報告します。
     */
    protected boolean zoneDelete(ScheduleParams... inParam)
            throws CommonException
    {
        try
        {
            // ゾーンマスタの削除
            // 検索キー
            ZoneHandler zHandler = new ZoneHandler(getConnection());
            ZoneSearchKey zSearchKey = new ZoneSearchKey();

            // エリア
            if (!StringUtil.isBlank(inParam[0].getString(AREA_NO)))
            {
                zSearchKey.setAreaNo(inParam[0].getString(AREA_NO));
            }

            // ゾーンNo.
            if (!StringUtil.isBlank(inParam[0].getString(HIDDEN_ZONE_NO)))
            {
                zSearchKey.setZoneNo(inParam[0].getString(HIDDEN_ZONE_NO));
            }

            // 作業ゾーンNo
            if (!StringUtil.isBlank(inParam[0].getString(HIDDEN_WORK_ZONE_NO)))
            {
                zSearchKey.setWorkZoneNo(inParam[0].getString(HIDDEN_WORK_ZONE_NO));
            }

            // 最終更新日時
            if (!StringUtil.isBlank(inParam[0].getString(LAST_UPDATE_DATE)))
            {
                zSearchKey.setLastUpdateDate(inParam[0].getDate(LAST_UPDATE_DATE));
            }

            // 該当ゾーン情報を削除します。
            zHandler.drop(zSearchKey);
        }
        catch (NotFoundException ex)
        {
            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, inParam[0].getRowIndex()));
            setErrorRowIndex(inParam[0].getRowIndex());
            setNgCellRow(inParam[0].getRowIndex());
            return false;
        }

        return true;
    }

    /**
     * 範囲チェック<BR>
     * checkParam内の条件にて、範囲チェックを行ないます。<BR>
     * ゾーンマスタ情報に範囲が重複した場合はfalseを返します。<BR>
     * 
     * @param checkParam
     *            チェック条件
     * @return 重複なし：<CODE>True</CODE><BR>
     *         重複あり：<CODE>False</CODE><BR>
     * @throws CommonException
     *             全てのユーザ定義例外を報告します。
     */
    protected boolean rangeCheck(ScheduleParams... checkParam)
            throws CommonException
    {
        // 選択情報内での範囲チェック
        for (int lc = 0; lc < checkParam.length; lc++)
        {
            for (int lp = lc + 1; lp < checkParam.length; lp++)
            {
                if (checkParam[lc].getString(WORK_ZONE_NO).equals(checkParam[lp].getString(WORK_ZONE_NO))
                        && checkParam[lc].getString(ZONE_NO).equals(checkParam[lp].getString(ZONE_NO)))
                {
                    // 6023620=No.{0} 入力された作業ゾーン、ゾーンが重複しています。
                    setMessage(WmsMessageFormatter.format(6023620, checkParam[lc].getRowIndex()));
                    setErrorRowIndex(checkParam[lc].getRowIndex());
                    setNgCellRow(checkParam[lc].getRowIndex());
                    return false;
                }
            }
        }

        // ゾーンマスタの検索
        // 検索キー：全件取得
        ZoneHandler zHandler = new ZoneHandler(getConnection());
        ZoneSearchKey zSearchKey = new ZoneSearchKey();

        // 対象情報取得:レーコードロック指定
        Zone[] r_Zone = (Zone[])zHandler.findForUpdate(zSearchKey);

        // チェック情報の変更前情報をカットする。
        for (int lc = 0; lc < r_Zone.length; lc++)
        {
            boolean s_flag = false;
            for (int ll = 0; ll < checkParam.length; ll++)
            {
                // 新規情報時、既存情報とのチェック不要
                if (Boolean.parseBoolean(checkParam[ll].getString(NEW_DATA)))
                {
                    break;
                }
                // エリア＋作業ゾーンNo＋ゾーンNoが一致する情報以外を取得する。
                if ((checkParam[ll].getString(AREA_NO).equals(r_Zone[lc].getAreaNo()))
                        && (checkParam[ll].getString(HIDDEN_WORK_ZONE_NO).equals(r_Zone[lc].getWorkZoneNo()))
                        && (checkParam[ll].getString(HIDDEN_ZONE_NO).equals(r_Zone[lc].getZoneNo())))
                {
                    s_flag = true;
                    break;
                }
            }
            // チェック条件情報に一致情報なし
            if (!s_flag)
            {
                for (int cpt = 0; cpt < checkParam.length; cpt++)
                {
                    // エリア＋作業ゾーンNo＋ゾーンNoが一致する情報以外を取得する。
                    if ((checkParam[cpt].getString(WORK_ZONE_NO).equals(r_Zone[lc].getWorkZoneNo()))
                            && (checkParam[cpt].getString(ZONE_NO).equals(r_Zone[lc].getZoneNo())))
                    {
                        // 6023618=No.{0} 入力された作業ゾーン、ゾーンはエリアNo.{1}に登録されています。
                        setMessage(WmsMessageFormatter.format(6023618, checkParam[cpt].getRowIndex(),
                                r_Zone[lc].getAreaNo()));
                        setErrorRowIndex(checkParam[cpt].getRowIndex());
                        setNgCellRow(checkParam[cpt].getRowIndex());
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * ゾーンマスタ関連テーブルチェック<BR>
     * checkParam内の条件に合致するゾーン情報関連項目が他のマスタに存在するか確認します。<BR>
     * 他のテーブルにゾーン情報が存在する場合は<CODE>true</CODE>を、<BR>
     * 見つからない場合は<CODE>false</CODE>を返します。<BR>
     * 画面側の削除事前チェック処理で使用します。<BR>
     * 
     * @param checkParam
     *            検索条件
     * @return 索結果が1件以上の場合:<CODE>true</CODE> 検索結果が0件の場合：<CODE>false</CODE><BR>
     * @throws CommonException
     *             全てのユーザ定義例外を報告します。
     */
    protected boolean existRelation(ScheduleParams... checkParam)
            throws CommonException
    {
        // ----------PCT出庫予定情報----------
        if (!existRetrievalPlan(checkParam))
        {
            if (checkParam[0].getString(PROCESS_FLAG).equals(PCTMasterInParameter.PROCESS_FLAG_DELETE))
            {
                // 6023619=行No{0}の作業ゾーンは、使用されているため削除できません。
                setMessage(WmsMessageFormatter.format(6023619, checkParam[0].getRowIndex()));
                setErrorRowIndex(checkParam[0].getRowIndex());
                setNgCellRow(checkParam[0].getRowIndex());
            }
            else
            {
                // 6023621=行No{0}の作業ゾーンは、使用されているため修正できません。
                setMessage(WmsMessageFormatter.format(6023621, checkParam[0].getRowIndex()));
                setErrorRowIndex(checkParam[0].getRowIndex());
                setNgCellRow(checkParam[0].getRowIndex());
            }
            return false;
        }
        return true;
    }


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    /**
     * PCT出庫予定情報にパラメータの条件に合致するデータが存在しないかチェックします。<BR>
     * 該当データが存在する場合は<CODE>false</CODE>を、存在しない場合は<CODE>true</CODE>を 返します。<BR>
     * 
     * @param inParam
     *            チェック条件
     * @return 検索結果が0件の場合（処理の続行が可能な場合）：<CODE>true</CODE><BR>
     *         検索結果が1件以上の場合（処理の続行が不可能な場合）:<CODE>false</CODE>
     * @throws CommonException
     *             全てのユーザ定義例外を報告します。
     */
    private boolean existRetrievalPlan(ScheduleParams... inParam)
            throws CommonException
    {
        // PCT出庫予定情報の検索
        // 検索キー
        PCTRetPlanHandler pctRetPlanHandler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey pctRetPlanKey = new PCTRetPlanSearchKey();

        // エリアNo
        pctRetPlanKey.setPlanAreaNo(inParam[0].getString(AREA_NO));
        // ゾーンNo.
        pctRetPlanKey.setPlanZoneNo(inParam[0].getString(HIDDEN_ZONE_NO));
        // 作業ゾーンNo
        pctRetPlanKey.setWorkZoneNo(inParam[0].getString(HIDDEN_WORK_ZONE_NO));
        // 状態フラグ
        pctRetPlanKey.setStatusFlag(PCTMasterInParameter.STATUS_FLAG_DELETE, "!=");

        // 検索処理
        if (pctRetPlanHandler.count(pctRetPlanKey) > 0)
        {
            return false;
        }

        return true;
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
