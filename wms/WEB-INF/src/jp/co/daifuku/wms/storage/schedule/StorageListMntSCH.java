// $Id: StorageListMntSCH.java 4555 2009-07-01 00:15:00Z ota $
package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.storage.schedule.StorageListMntSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.storage.operator.StorageOperator;

/**
 * 入庫リスト作業メンテナンスの画面処理を行います。
 *
 * @version $Revision: 4555 $, $Date:: 2009-07-01 09:15:00 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class StorageListMntSCH
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
    public StorageListMntSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
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
            finder = new WorkInfoFinder(getConnection());
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

        // オペレータパラメータ生成
        StorageInParameter[] inParams = new StorageInParameter[ps.length];
        for (int i = 0; i < inParams.length; i++)
        {
            inParams[i] = new StorageInParameter(getWmsUserInfo());

            inParams[i].setSettingUnitKey(ps[i].getString(SETTING_UNIT_KEY));
        }

        try
        {
            // オペレータ生成
            StorageOperator operator = new StorageOperator(getConnection(), getClass());

            for (StorageInParameter inParam : inParams)
            {
                // オペレータ呼び出し
                operator.cancel(inParam);
            }
            // 設定しました。
            setMessage(WmsMessageFormatter.format(6121004));
            return true;

        }
        catch (LockTimeOutException e)
        {
            // 6023114=他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023114));
            return false;
        }
        catch (OperatorException e)
        {
            // Operatorからの例外をcatchし、該当するエラーメッセージを表示する

            // 「他端末で更新済み」か「他端末で作業中」か「作業完了済み」
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, inParams[e.getErrorLineNo() - 1].getRowNo()));
                setErrorRowIndex(ps[e.getErrorLineNo() - 1].getRowIndex());
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
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
        WorkInfoSearchKey searchKey = new WorkInfoSearchKey();

        // 入庫予定日
        if (!StringUtil.isBlank(p.getDate(PLAN_DAY)))
        {
            searchKey.setPlanDay(WmsFormatter.toParamDate(p.getDate(PLAN_DAY)));
        }

        String[] tmp = WmsFormatter.getFromTo(p.getString(FROM_ITEM_CODE), p.getString(TO_ITEM_CODE));
        String from = tmp[0];
        String to = tmp[1];

        // 開始オーダーNo.
        if (!StringUtil.isBlank(from))
        {
            searchKey.setItemCode(from, ">=");
        }
        // 終了オーダーNo.
        if (!StringUtil.isBlank(to))
        {
            searchKey.setItemCode(to, "<=");
        }
        // 荷主コード
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 状態フラグ(作業中)
        searchKey.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING);
        // 作業区分(入庫)
        searchKey.setJobType(SystemDefine.JOB_TYPE_STORAGE);
        // ハードウェア区分(リスト)
        searchKey.setHardwareType(SystemDefine.HARDWARE_TYPE_LIST);

        /* 結合条件の指定 */
        // 荷主コード
        searchKey.setJoin(WorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        // 商品コード
        searchKey.setJoin(WorkInfo.ITEM_CODE, Item.ITEM_CODE);

        /* 取得項目の指定 */
        // 設定単位キー(リスト作業No.)
        searchKey.setSettingUnitKeyCollect();
        // 入庫予定日
        searchKey.setPlanDayCollect();
        // 商品コード
        searchKey.setItemCodeCollect();
        // 商品名称
        searchKey.setCollect(Item.ITEM_NAME);

        /* ソート順の指定 */
        // 設定単位キー(リスト作業No.)
        searchKey.setSettingUnitKeyOrder(true);
        // 入庫予定日
        searchKey.setPlanDayOrder(true);
        // 商品コード
        searchKey.setItemCodeOrder(true);

        /* 集約条件の指定 */
        // リスト作業No.
        searchKey.setSettingUnitKeyGroup();
        // 入庫予定日
        searchKey.setPlanDayGroup();
        // 商品コード
        searchKey.setItemCodeGroup();
        // 商品名称
        searchKey.setGroup(Item.ITEM_NAME);

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
        WorkInfo[] entities = (WorkInfo[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (WorkInfo ent : entities)
        {
            Params param = new Params();

            // 設定単位キー(リスト作業No.)
            param.set(SETTING_UNIT_KEY, ent.getSettingUnitKey());
            // 入庫予定日
            param.set(PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            param.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));

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
