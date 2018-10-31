// $Id: MaxWorkCountSettingSCH.java 4259 2009-05-12 05:33:55Z okayama $
package jp.co.daifuku.pcart.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.schedule.MaxWorkCountSettingSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTMaxWorkCountAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTMaxWorkCountFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTMaxWorkCountHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTMaxWorkCountSearchKey;
import jp.co.daifuku.wms.base.entity.PCTMaxWorkCount;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 最大作業数設定のスケジュール処理を行います。
 *
 * @version $Revision: 4259 $, $Date: 2009-05-12 14:33:55 +0900 (火, 12 5 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class MaxWorkCountSettingSCH
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
    public MaxWorkCountSettingSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
            finder = new PCTMaxWorkCountFinder(getConnection());
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
     * 最大作業数設定スケジュール開始処理<BR>
     * startParamsで指定するParameterクラスはMaxWorkCountSettingSCHParams型であること。<BR>
     * @param startParams 開始条件
     * @return 正常：<CODE>true</CODE>異常：<CODE>false</CODE>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean startSCH(MaxWorkCountSettingSCHParams startParam)
            throws CommonException
    {
        // 最大作業数設定処理
        PCTMaxWorkCountHandler handler = new PCTMaxWorkCountHandler(getConnection());
        // 最大作業数検索キー
        PCTMaxWorkCountSearchKey skey = new PCTMaxWorkCountSearchKey();

        // クラス名
        String cName = this.getClass().getSimpleName();

        try
        {
            // 日次更新・取込中チェック
            if (!canStart() || isLoadData())
            {
                return false;
            }

            // エリアNo
            skey.setAreaNo(startParam.getString(AREA));

            // 検索
            PCTMaxWorkCount ent = (PCTMaxWorkCount)handler.findPrimaryForUpdate(skey);

            // 検索結果が無い場合
            if (ent == null)
            {
                PCTMaxWorkCount pctMaxWorkCount = new PCTMaxWorkCount();
                // エリアNo.
                pctMaxWorkCount.setAreaNo(startParam.getString(AREA));
                // 最大作業数
                pctMaxWorkCount.setMaxWorkCnt(startParam.getInt(MAX_COUNT));
                // 登録処理名
                pctMaxWorkCount.setRegistPname(cName);
                // 最終更新処理名
                pctMaxWorkCount.setLastUpdatePname(cName);

                // 登録処理
                handler.create(pctMaxWorkCount);

                // 6001003=登録しました。
                setMessage("6001003");

                return true;
            }

            // 排他チェック
            if (!isNoModify(ent))
            {
                return false;
            }

            // 最大作業数設定処理を行う
            // 更新キーを生成
            PCTMaxWorkCountAlterKey akey = new PCTMaxWorkCountAlterKey();

            // 更新条件
            // エリア
            akey.setAreaNo(startParam.getString(AREA));
            // 最終更新日時
            akey.setLastUpdateDate(startParam.getDate(LAST_UPDATE_DATE));

            // 更新値
            // 最大作業数
            akey.updateMaxWorkCnt(startParam.getInt(MAX_COUNT));

            // 最終更新処理名
            akey.updateLastUpdatePname(this.getClass().getSimpleName());

            // 更新処理
            handler.modify(akey);

            // 6001006=設定しました。
            setMessage("6001006");

            return true;
        }
        catch (NotFoundException e)
        {
            // 6003006=このデータは、他の端末で更新されたため処理できません。
            setMessage("6003006");
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
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {

        PCTMaxWorkCountSearchKey searchKey = new PCTMaxWorkCountSearchKey();

        searchKey.setAreaNo(p.getString(AREA));

        searchKey.setCollect(PCTMaxWorkCount.AREA_NO);
        searchKey.setCollect(PCTMaxWorkCount.MAX_WORK_CNT);
        searchKey.setCollect(PCTMaxWorkCount.LAST_UPDATE_DATE);

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
        PCTMaxWorkCount[] entities = (PCTMaxWorkCount[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (PCTMaxWorkCount ent : entities)
        {
            Params param = new Params();

            // エリアNo.
            param.set(AREA, ent.getAreaNo());
            // 最大作業数
            param.set(MAX_COUNT, ent.getMaxWorkCnt());
            // 最終更新日時
            param.set(LAST_UPDATE_DATE, ent.getLastUpdateDate());

            result.add(param);
        }

        return result;
    }

    /**
     * 最大作業数更新処理チェック<BR>
     * checkParam内の条件に合致する情報が他の端末で更新されていないか確認します。<BR>
     * 他の処理で情報が更新されていなかった場合はtrueを、更新されていた場合はfalseを返します。<BR>
     * 
     * @param entity チェック条件
     * @return 更新されていない：<CODE>True</CODE><BR>
     *          更新されている：<CODE>False</CODE><BR>
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected boolean isNoModify(PCTMaxWorkCount entity)
            throws CommonException
    {
        // 最大作業数管理情報の検索
        // 検索キー
        PCTMaxWorkCountHandler handler = new PCTMaxWorkCountHandler(getConnection());
        PCTMaxWorkCountSearchKey skey = new PCTMaxWorkCountSearchKey();

        // エリアNo
        skey.setAreaNo(entity.getAreaNo());
        // 最終更新日
        skey.setLastUpdateDate(entity.getLastUpdateDate());

        // 検索処理
        if (handler.count(skey) > 0)
        {
            return true;
        }

        // 6027008=このデータは他の端末で更新中のため、処理できません。
        setMessage("6027008");
        return false;
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
