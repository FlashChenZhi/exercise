// $Id: PCTUserWorkInquirySCH.java 4270 2009-05-13 03:57:38Z okayama $
package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.pcart.retrieval.schedule.PCTUserWorkInquirySCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.PCTAllUserResultHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTAllUserResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTOrderInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTOrderInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTUserResultHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTUserResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.PCTAllUserResult;
import jp.co.daifuku.wms.base.entity.PCTOrderInfo;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;
import jp.co.daifuku.wms.base.entity.PCTUserResult;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * ユーザ別作業照会のスケジュール処理を行います。
 *
 * @version $Revision: 4270 $, $Date:: 2009-05-13 12:57:38 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class PCTUserWorkInquirySCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** カラム定義 オーダー数(<code>ORDER_COUNT</code>) */
    private static final FieldName ORDER_COUNT = new FieldName(PCTRetWorkInfo.STORE_NAME, "ORDER_COUNT");

    /** カラム定義 行数(<code>LINE_COUNT</code>) */
    private static final FieldName LINE_COUNT = new FieldName(PCTRetWorkInfo.STORE_NAME, "LINE_COUNT");

    /** カラム定義 基準ロット総数(<code>STDLOT_COUNT</code>) */
    private static final FieldName STDLOT_COUNT = new FieldName(PCTAllUserResult.STORE_NAME, "STDLOT_COUNT");

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** システム日付 */
    private String _sysDay = null;

    /** システム通算秒 */
    private long _sysTotalSec = 0;

    /** 基準ロット数(平均) */
    private double _stdLotValueAvg = 0;

    /** 実稼働時間 */
    private long _realOperateTime = 0;

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
    public PCTUserWorkInquirySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> query()
            throws CommonException
    {
        // 日次更新チェック
        if (!canStart())
        {
            return null;
        }

        // システム日付獲得
        _sysTotalSec = (new Date()).getTime();

        // 現在の作業日を取得します。
        WarenaviSystemController wmsContorl = new WarenaviSystemController(getConnection(), getClass());
        _sysDay = wmsContorl.getWorkDay();

        try
        {
            // 基準ロット数(平均)の獲得
            _stdLotValueAvg = getLotStdValue();

            // 検索結果をParameter配列に変換して取得
            List<Params> outParam = createQueryResult(createSearchKey());
            return outParam;
        }
        catch (Exception ex)
        {
            // 例外を受け取った場合、処理を中断してnullを返す
            // 6007002=データベースエラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            return null;
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
     * PCT出庫作業情報を取得するための検索キークラスのインスタンスを生成します。<BR>
     * <BR>
     * @return PCT出庫作業情報を取得するための検索キークラスのインスタンス
     */
    protected SearchKey createSearchKey()
    {
        try
        {
            /*
             * 副問合せの検索条件のセット
             */
            PCTUserResultSearchKey subSearchKey = new PCTUserResultSearchKey();
            // 作業日(PCTユーザ実績情報)
            subSearchKey.setWorkDate(_sysDay);

            /*
             * 副問合せの取得項目セット
             */
            // 設定単位キー
            subSearchKey.setSettingUnitKeyCollect();

            /*
             * 検索条件のセット
             */
            PCTRetWorkInfoSearchKey wSearchKey = new PCTRetWorkInfoSearchKey();

            // 完了かつ作業日が当日
            wSearchKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_COMPLETION, "=", "((", "", true);
            wSearchKey.setWorkDay(_sysDay, "=", "", ")", false);

            // 作業済みかつユーザ実績の作業日が当日
            wSearchKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_EXIST_WORKED, "=", "(", "", true);
            /*
             * 副問合せ条件
             */
            // 設定単位キー
            wSearchKey.setKey(PCTRetWorkInfo.SETTING_UNIT_KEY, subSearchKey, "=", "", "))", true);

            /*
             * 取得項目のセット
             */
            // ユーザID(ログインユーザ設定テーブル)
            wSearchKey.setUserIdCollect();
            // ユーザ名(ログインユーザ設定テーブル)
            wSearchKey.setCollect(Com_loginuser.USERNAME, "MAX", Com_loginuser.USERNAME);
            // ロット数(作業数量)
            wSearchKey.setResultQtyCollect("SUM");
            // オーダー数
            wSearchKey.setCollect(PCTRetWorkInfo.PLAN_ORDER_NO, "COUNT(DISTINCT {0})", ORDER_COUNT);
            // 行数
            wSearchKey.setCollect(PCTRetWorkInfo.PLAN_ORDER_NO, "COUNT", LINE_COUNT);

            /*
             * グループ項目のセット
             */
            // ユーザID
            wSearchKey.setUserIdGroup();

            /*
             * 表結合
             */
            // PCT出庫作業情報のユーザID = ログインユーザ設定のユーザID
            wSearchKey.setJoin(PCTRetWorkInfo.USER_ID, "", Com_loginuser.USERID, "(+)");

            /*
             * 読込み順のセット
             */
            // ユーザID
            // 注）表示処理にてセットされた降順に並び直すので、ここでも降順にする
            wSearchKey.setUserIdOrder(false);

            return wSearchKey;
        }
        catch (Exception ex)
        {
            // 例外を受け取った場合、処理を中断してnullを返す
            // 6007002=データベースエラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            return null;
        }
    }

    /**
     * 検索した結果をパラメータにセットします。<BR>
     * <BR>
     * @param skey PCT出庫作業情報検索クラス
     * @return outParam
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected List<Params> createQueryResult(SearchKey skey)
            throws CommonException
    {
        List<Params> outParams = new ArrayList<Params>();
        // 作業情報取得結果
        PCTRetWorkInfoHandler handler = new PCTRetWorkInfoHandler(getConnection());
        PCTRetWorkInfo[] workInfoEntity = (PCTRetWorkInfo[])handler.find(skey);

        if (workInfoEntity == null || workInfoEntity.length == 0)
        {
            // 6003011=対象データはありませんでした。
            setMessage("6003011");
            return null;
        }

        // 件数文繰返し
        for (int i = 0; i < workInfoEntity.length; i++)
        {
            Params param = new Params();

            // 基準ロット数(平均)
            param.set(LOT_STANDARD_VALUE, _stdLotValueAvg);

            // ユーザID
            param.set(USER_ID, workInfoEntity[i].getUserId());
            // ユーザ名称
            param.set(USER_NAME, (String)workInfoEntity[i].getValue(Com_loginuser.USERNAME, ""));
            // ロット数(作業数量)
            param.set(LOT_COUNT, workInfoEntity[i].getResultQty());
            // オーダー数
            param.set(ORDER_QTY, Integer.parseInt(String.valueOf(workInfoEntity[i].getValue(ORDER_COUNT, ""))));
            // 行数
            param.set(PCTUserWorkInquirySCHParams.LINE_COUNT,
                    Integer.parseInt(String.valueOf(workInfoEntity[i].getValue(LINE_COUNT, ""))));
            try
            {
                // 生産率取得
                double productionRate = getProductionRate(param.getString(USER_ID), param.getInt(LOT_COUNT));
                // 生産率(文字型 %付き)
                param.set(PRODUCTION_RATE, WmsFormatter.toProductionRate(productionRate));
                // 生産率(double型)
                param.set(PRODUCTION_RATEVAL, productionRate);
            }
            catch (Exception ex)
            {
                throw new ReadWriteException(ex);
            }
            // 実稼働時間
            param.set(REAL_TIME, (int)_realOperateTime);

            outParams.add(param);
        }

        // 6001013=表示しました。
        setMessage("6001013");
        return outParams;
    }

    /**
     * 該当作業日の指定ユーザに対する生産率を算出します。<BR>
     * <BR>
     * @param  userId ユーザID
     * @param  lotQty ロット数(作業数量)
     * @return 生産率
     * @throws ReadWriteException 例外が発生した場合に通知されます。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected double getProductionRate(String userId, int lotQty)
            throws ReadWriteException,
                CommonException
    {
        /* ---------------------------------------------------------------------
         * １．ユーザ実績情報から作業当日の総作業時間を取得する
         * ---------------------------------------------------------------------
         */
        // PCTユーザ実績情報より、データを取得する
        // PCTユーザ実績情報の検索
        PCTUserResultHandler handler = new PCTUserResultHandler(getConnection());
        PCTUserResultSearchKey shKy = new PCTUserResultSearchKey();
        PCTUserResult[] entity = null;

        // 総作業時間
        long totalWorkTime = 0;
        try
        {
            /*
             * PCTユーザ実績情報の検索キーセット
             */
            // 作業日
            shKy.setWorkDate(_sysDay);
            // ユーザID
            shKy.setUserId(userId);
            // 運用区分
            shKy.setResultType(PCTRetrievalInParameter.RESULT_TYPE_OPERATION);

            /*
             * PCTユーザ実績情報の読込み対象項目セット
             */
            // 作業日
            shKy.setWorkDateCollect();
            // ユーザID
            shKy.setUserIdCollect();
            // 総作業時間(秒)
            shKy.setWorkTimeCollect("SUM");

            /*
             * グループ項目のセット
             */
            // 作業日
            shKy.setGroup(PCTUserResult.WORK_DATE);
            // ユーザID
            shKy.setGroup(PCTUserResult.USER_ID);

            // 検索結果を取得
            entity = (PCTUserResult[])handler.find(shKy);
            if (entity == null || entity.length == 0)
            {
                // 条件に一致するデータが１件も存在しない場合
                return 0;
            }

            // 総作業時間
            totalWorkTime = entity[0].getWorkTime();
        }
        catch (Exception ex)
        {
            // 例外を受け取った場合、処理を中断します
            // 6007002=データベースエラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            throw new ReadWriteException(ex);
        }

        /* ---------------------------------------------------------------------
         * ２．ユーザ実績情報から作業当日で現在作業中のデータがあれば、
         *     開始時間からの経過時間を取得する(作業中データの経過時間)
         * ---------------------------------------------------------------------
         */
        // 作業中データの経過時間
        long workProgressTime = 0;
        try
        {
            /*
             * PCTユーザ実績情報の検索キーセット
             */
            // 検索条件クリア
            shKy.clear();
            // 作業日
            shKy.setWorkDate(_sysDay);
            // ユーザID
            shKy.setUserId(userId);
            // 開始時刻がnull以外でかつ終了時刻がnull
            shKy.setKey(PCTUserResult.WORK_STARTTIME, null, "!=", "", "", true);
            shKy.setKey(PCTUserResult.WORK_ENDTIME, "");

            // 運用区分
            shKy.setResultType(PCTRetrievalInParameter.RESULT_TYPE_OPERATION);

            /*
             * PCTユーザ実績情報の読込み対象項目セット
             */
            // 開始時間
            shKy.setWorkStarttimeCollect();

            // 検索結果を取得
            entity = (PCTUserResult[])handler.find(shKy);
            if (entity == null || entity.length == 0)
            {
                // 作業中データの経過時間
                workProgressTime = 0;
            }
            else
            {
                // 作業中データの経過時間(システム時刻 - 作業中データの開始時間
                workProgressTime = (_sysTotalSec - entity[0].getWorkStarttime().getTime()) / 1000;
            }
        }
        catch (Exception ex)
        {
            // 例外を受け取った場合、処理を中断します
            // 6007002=データベースエラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            throw new ReadWriteException(ex);
        }

        /* ---------------------------------------------------------------------
         * ３．ユーザ実績情報から作業当日の休憩時間とバッテリ交換時間を取得する
         * ---------------------------------------------------------------------
         */
        // ロスタイム(休憩時間とバッテリ交換時間)
        long lostTime = 0;
        try
        {
            /*
             * PCTユーザ実績情報の検索キーセット
             */
            // 検索条件クリア
            shKy.clear();
            // 作業日
            shKy.setWorkDate(_sysDay);
            // ユーザID
            shKy.setUserId(userId);
            // 運用区分
            shKy.setResultType(new String[] {
                    PCTRetrievalInParameter.RESULT_TYPE_BREAK,
                    PCTRetrievalInParameter.RESULT_TYPE_BATTERY
            }, true);

            /*
             * PCTユーザ実績情報の読込み対象項目セット
             */
            // 作業日
            shKy.setWorkDateCollect();
            // ユーザID
            shKy.setUserIdCollect();
            // ロスタイム(休憩時間とバッテリ交換時間)
            shKy.setWorkTimeCollect("SUM");

            /*
             * グループ項目のセット
             */
            // 作業日
            shKy.setGroup(PCTUserResult.WORK_DATE);
            // ユーザID
            shKy.setGroup(PCTUserResult.USER_ID);

            // 検索結果を取得
            entity = (PCTUserResult[])handler.find(shKy);
            if (entity == null || entity.length == 0)
            {
                // ロスタイム(休憩時間とバッテリ交換時間)
                lostTime = 0;
            }
            else
            {
                // ロスタイム(休憩時間とバッテリ交換時間)
                lostTime = entity[0].getWorkTime();
            }
        }
        catch (Exception ex)
        {
            // 例外を受け取った場合、処理を中断します
            // 6007002=データベースエラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            throw new ReadWriteException(ex);
        }

        /* ---------------------------------------------------------------------
         * ４．ユーザ実績情報から作業当日で現在作業中のデータがあれば、
         * 作業当日の休憩時間とバッテリ交換時間を取得する
         * ---------------------------------------------------------------------
         */
        // ロスタイム(休憩時間とバッテリ交換時間)
        long lostProgressTime = 0;
        try
        {
            /*
             * PCTユーザ実績情報の検索キーセット
             */
            // 検索条件クリア
            shKy.clear();
            // 作業日
            shKy.setWorkDate(_sysDay);
            // ユーザID
            shKy.setUserId(userId);
            // 運用区分
            shKy.setResultType(new String[] {
                    PCTRetrievalInParameter.RESULT_TYPE_BREAK,
                    PCTRetrievalInParameter.RESULT_TYPE_BATTERY
            }, true);

            // 開始時刻がnull以外でかつ終了時刻がnull
            shKy.setKey(PCTUserResult.WORK_STARTTIME, null, "!=", "", "", true);
            shKy.setKey(PCTUserResult.WORK_ENDTIME, "");

            /*
             * PCTユーザ実績情報の読込み対象項目セット
             */
            // 開始時間
            shKy.setWorkStarttimeCollect();

            // 検索結果を取得
            entity = (PCTUserResult[])handler.find(shKy);
            if (entity == null || entity.length == 0)
            {
                lostProgressTime = 0;
            }
            else
            {
                // 作業中データの経過時間(システム時刻 - 作業中データの開始時間
                lostProgressTime = (_sysTotalSec - entity[0].getWorkStarttime().getTime()) / 1000;
                lostTime += lostProgressTime;
            }
        }
        catch (Exception ex)
        {
            // 例外を受け取った場合、処理を中断します
            // 6007002=データベースエラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            throw new ReadWriteException(ex);
        }
        /* ---------------------------------------------------------------------
         * ５．作業当日の実稼働時間を取得する
         *      (総作業時間 + 作業中データの経過時間) - ロスタイム
         * ---------------------------------------------------------------------
         */
        _realOperateTime = (totalWorkTime + workProgressTime) - lostTime;

        /* ---------------------------------------------------------------------
         * ６．時間当たりの作業数を取得する
         *      ロット数/h = (ロット数 / 実稼働時間) * 60 * 60
         * ---------------------------------------------------------------------
         */
        double lotPerH = ((double)lotQty / (double)_realOperateTime) * 60 * 60;

        /* ---------------------------------------------------------------------
         * ７．ユーザIDよりロット作業基準値(ランクB)を取得します。
         * ---------------------------------------------------------------------
         */
        double lotStdVal = 0;
        try
        {
            // 当初ユーザ毎に基準ロット数を算出していたが、全ユーザの平均と
            // なったので、以下に変更
            // lotStdVal = getLotStdValue(userId);      // 旧バージョン
            lotStdVal = _stdLotValueAvg;
        }
        catch (Exception ex)
        {
            throw new ReadWriteException(ex);
        }

        /* ---------------------------------------------------------------------
         * ８．ロット作業基準値(ランクB)より、生産率を取得します。
         *      生産率 = (ロット数/h / ロット作業基準値) / 100
         * ---------------------------------------------------------------------
         */
        // 生産率
        double productionRate = 0;
        if (lotPerH != 0 && lotStdVal != 0)
        {
            BigDecimal dbLotQty = new BigDecimal((lotPerH / lotStdVal) * 100);
            productionRate = dbLotQty.divide(new BigDecimal(1), 1, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        return productionRate;
    }

    /**
     * ユーザIDをキーに荷主・エリアを読込み、ロット作業基準値(ランクB)を取得します。<BR>
     * <BR>
     * @param  userId    ユーザID
     * @return ロット作業基準値
     * @throws ReadWriteException 例外が発生した場合に通知されます。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected double getLotStdValue(String userId)
            throws ReadWriteException,
                CommonException
    {
        // 荷主コード
        String consignorCode = null;
        // エリアNo.
        String areaNo = null;

        /* ---------------------------------------------------------------------
         * １．RFT管理情報から現在作業中の荷主コード・エリアNo.を取得する
         * ---------------------------------------------------------------------
         */
        // RFT管理情報より、データを取得する
        // RFT管理情報の検索
        RftHandler rftHandler = new RftHandler(getConnection());
        RftSearchKey rftShKy = new RftSearchKey();
        Rft[] rftEntity = null;

        try
        {
            /*
             * RFT管理情報の検索キーセット
             */
            // ユーザID
            rftShKy.setUserId(userId);

            /*
             * RFT管理情報の読込み対象項目セット
             */
            // 荷主コード
            rftShKy.setConsignorCodeCollect();
            // エリアNo.
            rftShKy.setAreaNoCollect();

            // 検索結果を取得
            rftEntity = (Rft[])rftHandler.find(rftShKy);
            if (rftEntity == null || rftEntity.length == 0)
            {
                // 荷主コード
                consignorCode = null;
                // エリアNo.
                areaNo = null;
            }
            else
            {
                // 荷主コード
                consignorCode = rftEntity[0].getConsignorCode();
                // エリアNo.
                areaNo = rftEntity[0].getAreaNo();
            }
        }
        catch (Exception ex)
        {
            // 例外を受け取った場合、処理を中断します
            // 6007002=データベースエラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            throw new ReadWriteException(ex);
        }

        /* ---------------------------------------------------------------------
         * ２．該当ユーザが作業中でない(RFT管理情報に該当ユーザのデータがない)
         *     場合、PCTオーダー情報から最終更新日時の荷主コード・エリアNo.を取得する
         * ---------------------------------------------------------------------
         */
        if (consignorCode == null)
        {
            // PCTオーダー情報より、データを取得する
            // PCTオーダー情報の検索
            PCTOrderInfoHandler orderHandler = new PCTOrderInfoHandler(getConnection());
            PCTOrderInfoSearchKey orderShKy = new PCTOrderInfoSearchKey();
            PCTOrderInfo[] orderEntity = null;

            try
            {
                /*
                 * PCTオーダー情報の検索キーセット
                 */
                // ユーザID
                orderShKy.setUserId(userId);

                /*
                 * PCTユーザ実績情報の読込み対象項目セット
                 */
                // 最終更新日時
                orderShKy.setLastUpdateDateCollect("MAX");
                // 荷主コード
                orderShKy.setConsignorCodeCollect();
                // エリアNo.
                orderShKy.setAreaNoCollect();

                /*
                 * グループ項目のセット
                 */
                // 荷主コード
                orderShKy.setGroup(PCTOrderInfo.CONSIGNOR_CODE);
                // エリアNo.
                orderShKy.setGroup(PCTOrderInfo.AREA_NO);

                // 検索結果を取得
                orderEntity = (PCTOrderInfo[])orderHandler.find(orderShKy);
                if (orderEntity == null || orderEntity.length == 0)
                {
                    // 条件に一致するデータが１件も存在しない場合
                    return 0;
                }
                else
                {
                    // 荷主コード
                    consignorCode = orderEntity[0].getConsignorCode();
                    // エリアNo.
                    areaNo = orderEntity[0].getAreaNo();
                }
            }
            catch (Exception ex)
            {
                // 例外を受け取った場合、処理を中断します
                // 6007002=データベースエラーが発生しました。ログを参照してください。
                setMessage(WmsMessageFormatter.format(6007002));
                throw new ReadWriteException(ex);
            }
        }

        /* ---------------------------------------------------------------------
         * ３．上記で取得した荷主コード・エリアNo.を基に、PCT全ユーザ実績情報
         *     を読込みランクBのロット作業基準値を取得する
         * ---------------------------------------------------------------------
         */
        // PCT全ユーザ実績情報より、データを取得する
        // PCT全ユーザ実績情報の検索
        PCTAllUserResultHandler allUserHandler = new PCTAllUserResultHandler(getConnection());
        PCTAllUserResultSearchKey allUserShKy = new PCTAllUserResultSearchKey();
        PCTAllUserResult[] allUserEntity = null;

        try
        {
            /*
             * PCT全ユーザ実績情報の検索キーセット
             */
            // 検索条件クリア
            allUserShKy.clear();
            // 荷主コード
            allUserShKy.setConsignorCode(consignorCode);
            // エリアNo.
            allUserShKy.setAreaNo(areaNo);
            // ランク(ランクB)
            allUserShKy.setRank(SystemDefine.RANK_NO_B);

            /*
             * PCT全ユーザ実績情報の読込み対象項目セット
             */
            // ロット作業基準値
            allUserShKy.setLotStandardValueCollect();

            // 検索結果を取得
            allUserEntity = (PCTAllUserResult[])allUserHandler.find(allUserShKy);
            if (allUserEntity == null || allUserEntity.length == 0)
            {
                // 条件に一致するデータが１件も存在しない場合
                return 0;
            }
            else
            {
                return (double)allUserEntity[0].getLotStandardValue();
            }
        }
        catch (Exception ex)
        {
            // 例外を受け取った場合、処理を中断します
            // 6007002=データベースエラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            throw new ReadWriteException(ex);
        }
    }

    /**
     * 全荷主・エリアを対象にロット作業基準値(ランクB)の平均を取得します。<BR>
     * <BR>
     * @return ロット作業基準値の平均
     * @throws ReadWriteException 例外が発生した場合に通知されます。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected double getLotStdValue()
            throws ReadWriteException,
                CommonException
    {
        /* ---------------------------------------------------------------------
         * PCT全ユーザ実績情報より、
         *     を読込みランクBのロット作業基準値を取得する
         * ---------------------------------------------------------------------
         */
        // PCT全ユーザ実績情報より、データを取得する
        // PCT全ユーザ実績情報の検索
        PCTAllUserResultHandler allUserHandler = new PCTAllUserResultHandler(getConnection());
        PCTAllUserResultSearchKey allUserShKy = new PCTAllUserResultSearchKey();
        PCTAllUserResult[] allUserEntity = null;

        try
        {
            /*
             * PCT全ユーザ実績情報の検索キーセット
             */
            // 検索条件クリア
            allUserShKy.clear();
            // ランク(ランクB)
            allUserShKy.setRank(SystemDefine.RANK_NO_B);

            /*
             * PCT全ユーザ実績情報の読込み対象項目セット
             */
            // レコード数
            allUserShKy.setCollect(PCTAllUserResult.LOT_STANDARD_VALUE, "COUNT", STDLOT_COUNT);
            // ロット作業基準値(総数)
            allUserShKy.setLotStandardValueCollect("SUM");

            // 検索結果を取得
            allUserEntity = (PCTAllUserResult[])allUserHandler.find(allUserShKy);
            if (allUserEntity == null
                    || allUserEntity[0].getBigDecimal(STDLOT_COUNT, new BigDecimal(0)).intValue() == 0)
            {
                // 条件に一致するデータが１件も存在しない場合
                return 0;
            }
            else
            {
                BigDecimal dbLotQty =
                        new BigDecimal((double)allUserEntity[0].getLotStandardValue()
                                / (double)Integer.parseInt(String.valueOf(allUserEntity[0].getValue(STDLOT_COUNT, ""))));
                return dbLotQty.divide(new BigDecimal(1), 1, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
        }
        catch (Exception ex)
        {
            // 例外を受け取った場合、処理を中断します
            // 6007002=データベースエラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            throw new ReadWriteException(ex);
        }
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
