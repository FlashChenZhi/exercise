// $Id: SoftZoneMasterMntSCH.java 4995 2009-09-08 09:57:54Z shibamoto $
package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.master.schedule.SoftZoneMasterMntSCHParams.*;

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Shelf;

/**
 * ソフトゾーンメンテナンス（ソフトゾーン範囲）のスケジュール処理を行います。
 *
 * @version $Revision: 4995 $, $Date:: 2009-09-08 18:57:54 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class SoftZoneMasterMntSCH
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
    // AS/RS棚情報
    private ShelfHandler _shelfHandl = null;

    private ShelfSearchKey _shelfKey = null;

    private ShelfAlterKey _shelfAKey = null;

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
    public SoftZoneMasterMntSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
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
            // 問題が発生したためfalseを返却
            return false;
        }

        // 搬送データクリアチェック
        if (isAllocationClear())
        {
            return false;
        }

        try
        {
            // リスト件数分、処理を行う
            for (ScheduleParams p : ps)
            {
                // AS/RS棚情報を更新
                updateShelf(p);
            }

            // 6121004=編集しました。
            setMessage(WmsMessageFormatter.format(6121004));

            // 問題ない場合はtrueを返却
            return true;
        }
        catch (DataExistsException e)
        {
            // 6023115=他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015));

            // 問題が発生したためfalseを返却
            return false;
        }
        catch (ReadWriteException e)
        {
            // 6023115=他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015));

            // 問題が発生したためfalseを返却
            return false;
        }
        catch (NotFoundException e)
        {
            // 6023115=他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015));

            // 問題が発生したためfalseを返却
            return false;
        }
        catch (InvalidDefineException e)
        {
            // 6023115=他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015));

            // 問題が発生したためfalseを返却
            return false;
        }
    }

    /**
     * 画面から入力された内容とリストセルエリアのデータをパラメータとして受け取り、
     * チェックを行います。<BR>
     *
     * @param p 入力パラメータ
     * @param ps リストセルエリアのパラメータ
     * @return 入力チェック、オーバーフロー、重複、商品マスタ・入庫棚エラーでない場合はtrueを返す。
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public boolean check(ScheduleParams p, ScheduleParams... ps)
            throws CommonException
    {
        // バンク、ベイ、レベルに0以上の値が設定されているかチェック
        if (p.getInt(BANK_FROM) <= 0 || p.getInt(BANK_TO) <= 0 || p.getInt(BAY_FROM) <= 0 || p.getInt(BAY_TO) <= 0
                || p.getInt(LEVEL_FROM) <= 0 || p.getInt(LEVEL_TO) <= 0)
        {
            // 6123071=範囲には１以上の値を指定してください。
            setMessage("6123071");

            // 問題が発生したためfalseを返却
            return false;
        }

        // 棚範囲チェック
        int range[] = getMaxShelf(p);
        if (p.getInt(BANK_TO) > range[0] || p.getInt(BAY_TO) > range[1] || p.getInt(LEVEL_TO) > range[2])
        {
            // 6123092=ゾーンID（{0}）は、バンク、ベイ、レベルの範囲が倉庫の範囲を超えています。
            setMessage(WmsMessageFormatter.format(6123092, p.getString(SOFT_ZONE)));

            // 問題が発生したためfalseを返却
            return false;
        }

        // 棚存在チェック
        if (getShelfCount(p) <= 0)
        {
            // 6123204=入力された範囲に棚情報が存在しません。
            setMessage("6123204");

            // 問題が発生したためfalseを返却
            return false;
        }

        // ためうちエリアとの重複チェック
        if (ps != null)
        {
            for (ScheduleParams schParam : ps)
            {
                // エリアが同一
                if (p.getString(AREA_NO).equals(schParam.getString(AREA_NO)))
                {
                    // バンクが範囲内に存在
                    if ((p.getInt(BANK_FROM) <= schParam.getInt(BANK_FROM) && p.getInt(BANK_TO) >= schParam.getInt(BANK_FROM))
                            || (p.getInt(BANK_FROM) <= schParam.getInt(BANK_TO) && p.getInt(BANK_TO) >= schParam.getInt(BANK_TO)))
                    {
                        // ベイが範囲内に存在
                        if ((p.getInt(BAY_FROM) <= schParam.getInt(BAY_FROM) && p.getInt(BAY_TO) >= schParam.getInt(BAY_FROM))
                                || (p.getInt(BAY_FROM) <= schParam.getInt(BAY_TO) && p.getInt(BAY_TO) >= schParam.getInt(BAY_TO)))
                        {
                            // レベルが範囲内に存在
                            if ((p.getInt(LEVEL_FROM) <= schParam.getInt(LEVEL_FROM) && p.getInt(LEVEL_TO) >= schParam.getInt(LEVEL_FROM))
                                    || (p.getInt(LEVEL_FROM) <= schParam.getInt(LEVEL_TO) && p.getInt(LEVEL_TO) >= schParam.getInt(LEVEL_TO)))
                            {
                                // 6123077=ゾーン範囲が重複しているため設定できません。
                                setMessage("6123077");

                                // 問題が発生したためfalseを返却
                                return false;
                            }
                        }
                    }
                }
            }
        }
        // 6001019=入力を受け付けました。
        setMessage("6001019");

        // 問題ない場合はtrueを返却
        return true;
    }

    /**
     * "xxx - xxx"のフォーマットを行う
     * 
     * @param from 前の値
     * @param to 後ろの値
     * @return "xxx - xxx"形式の文字列
     */
    public String fromToFormat(String from, String to)
    {
        // fromの場合、3桁未満だったら後に空白を挿入
        // Toの場合、3桁未満だったら前に空白を挿入
        for (int i = 0; i <= 2; i++)
        {
            // fromとToが双方共3桁の場合は処理抜け
            if (from.length() == 3 && to.length() == 3)
            {
                break;
            }

            // fromが3桁未満の場合
            if (from.length() < 3)
            {
                from = from.concat(" ");
            }

            // Toが3桁未満の場合
            if (to.length() < 3)
            {
                to = " ".concat(to);
            }
        }
        // 生成した文字列を返却
        return from + " - " + to;
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
     * 指定エリアに紐づく最大棚範囲を取得する。
     * 
     * @param p 検索条件を含むScheduleParams
     * @return int[] 最大値(バンク、ベイ、レベル)
     * @throws CommonException
     */
    protected int[] getMaxShelf(ScheduleParams p)
            throws CommonException
    {
        // AS/RS棚情報データベースハンドラ
        _shelfHandl = new ShelfHandler(getConnection());
        // AS/RS棚情報検索キー
        _shelfKey = new ShelfSearchKey();

        // 返却パラメータ
        int range[] = new int[3];

        // SQLの生成(取得項目)
        _shelfKey.clear();
        // バンク(最大値)
        _shelfKey.setCollect(Shelf.BANK_NO, "MAX", Shelf.BANK_NO);
        // ベイ(最大値)
        _shelfKey.setCollect(Shelf.BAY_NO, "MAX", Shelf.BAY_NO);
        // レベル(最大値)
        _shelfKey.setCollect(Shelf.LEVEL_NO, "MAX", Shelf.LEVEL_NO);

        // SQLの生成(検索条件)
        // エリア情報.エリアNo.と画面.エリアNo.が同一
        _shelfKey.setKey(Area.AREA_NO, p.getString(AREA_NO));

        // SQLの生成(結合条件)
        // エリア情報.倉庫ステーションNo.とAS/RS棚情報.倉庫ステーションNo.が同一
        _shelfKey.setJoin(Area.WHSTATION_NO, Shelf.WH_STATION_NO);

        // 検索実行
        Shelf[] shelfs = (Shelf[])_shelfHandl.find(_shelfKey);

        // 検索結果を取得
        for (int i = 0; i < shelfs.length; i++)
        {
            range[0] = shelfs[i].getBankNo();
            range[1] = shelfs[i].getBayNo();
            range[2] = shelfs[i].getLevelNo();
        }

        // 最大棚範囲を返却
        return range;
    }

    /**
     * 指定エリアと入力された情報に紐づく棚範囲の件数を取得する。
     * 
     * @param p 検索条件を含むScheduleParams
     * @return 入力されたバンク、ベイ、レベル内の棚範囲件数
     * @throws CommonException
     */
    protected int getShelfCount(ScheduleParams p)
            throws CommonException
    {
        // SQLの生成(取得項目)
        _shelfKey.clear();
        _shelfKey.setStationNoCollect();

        // SQLの生成(検索条件)
        // エリア情報.エリアNo.と画面.エリアNo.が同一
        _shelfKey.setKey(Area.AREA_NO, p.getString(AREA_NO));
        _shelfKey.setBankNo(p.getInt(BANK_FROM), ">=", "(", "", true);
        _shelfKey.setBankNo(p.getInt(BANK_TO), "<=", "", ")", true);
        _shelfKey.setBayNo(p.getInt(BAY_FROM), ">=", "(", "", true);
        _shelfKey.setBayNo(p.getInt(BAY_TO), "<=", "", ")", true);
        _shelfKey.setLevelNo(p.getInt(LEVEL_FROM), ">=", "(", "", true);
        _shelfKey.setLevelNo(p.getInt(LEVEL_TO), "<=", "", ")", true);

        // SQLの生成(結合条件)
        // エリア情報.倉庫ステーションNo.とAS/RS棚情報.倉庫ステーションNo.が同一
        _shelfKey.setJoin(Area.WHSTATION_NO, Shelf.WH_STATION_NO);

        // 検索を実行し結果件数を返却
        return _shelfHandl.count(_shelfKey);
    }

    /**
     * 対象データに紐づくソフトゾーンIDを更新する。
     * 
     * @param p 検索条件を含むScheduleParams
     * @throws CommonException
     */
    protected void updateShelf(ScheduleParams p)
            throws CommonException
    {
        // AS/RS棚情報データベースハンドラ
        _shelfHandl = new ShelfHandler(getConnection());
        // AS/RS棚情報更新キー
        _shelfAKey = new ShelfAlterKey();
        // エリア情報検索キー
        AreaSearchKey areaSKey = new AreaSearchKey();

        // SQLの生成(更新値)
        _shelfAKey.updateSoftZoneId(p.getString(SOFT_ZONE));

        // SQLの生成(更新条件)
        _shelfAKey.setBankNo(p.getInt(BANK_FROM), ">=", "(", "", true);
        _shelfAKey.setBayNo(p.getInt(BAY_FROM), ">=", "", "", true);
        _shelfAKey.setLevelNo(p.getInt(LEVEL_FROM), ">=", "", ")", true);
        _shelfAKey.setBankNo(p.getInt(BANK_TO), "<=", "(", "", true);
        _shelfAKey.setBayNo(p.getInt(BAY_TO), "<=", "", "", true);
        _shelfAKey.setLevelNo(p.getInt(LEVEL_TO), "<=", "", ")", true);

        areaSKey.clear();
        areaSKey.setWhstationNoCollect();
        areaSKey.setAreaNo(p.getString(AREA_NO));
        _shelfAKey.setKey(Shelf.WH_STATION_NO, areaSKey);

        // SQLの実行
        _shelfHandl.modify(_shelfAKey);
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
