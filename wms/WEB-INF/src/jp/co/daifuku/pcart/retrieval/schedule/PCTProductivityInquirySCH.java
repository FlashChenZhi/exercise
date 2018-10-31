// $Id: PCTProductivityInquirySCH.java 4270 2009-05-13 03:57:38Z okayama $
package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTAllUserResultHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTAllUserResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTOrderInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTOrderInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoFinder;
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
 * ユーザ別生産性照会スケジュールクラス<BR>
 * 指定されたパラメータの内容を元に、ユーザ別生産性照会を行う。<BR>
 * ユーザ別生産性照会画面（PCTProductivityInquiryBusiness)<BR>
 * の各種操作に合わせたメソッドを持つ。<BR>
 *
 * Desiger : H.Okayama<BR>
 * maker: H.Okayama<BR>
 * @version $Revision: 4270 $, $Date: 2009-05-13 12:57:38 +0900 (水, 13 5 2009) $
 * @author  $Author: okayama $
 */
public class PCTProductivityInquirySCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** カラム定義 オーダー数(<code>ORDER_COUNT</code>) */
    private static final FieldName ORDER_COUNT = new FieldName(PCTRetWorkInfo.STORE_NAME, "ORDER_COUNT");

    /** カラム定義 行数(<code>LINE_COUNT</code>) */
    private static final FieldName LINE_COUNT = new FieldName(PCTRetWorkInfo.STORE_NAME, "LINE_COUNT");

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
    public PCTProductivityInquirySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);

        // システム日付獲得
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        _sysDay = fmt.format(new Date());
        _sysTotalSec = (new Date()).getTime();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * ユーザ別生産性照会の表示データ取得<BR>
     * startParamsで指定されたパラメータの内容をもとに、以下テーブルよりデータの取得処理を行う。<BR>
     *   ・PCT出庫作業情報(DNPCTRETWORKINFO)
     *   ・PCTユーザ実績情報(DNPCTUSERRESULT)
     *   ・PCT全ユーザ実績情報(DNPCTALLUSERRESULT)
     *   ・RFT管理情報(DMRFT)
     *   ・PCTオーダー情報(DNPCTORDERINFO)
     * 正常完了した場合は検索結果を、PCTRetrievalOutParameterオブジェクト形式で返す。<BR>
     * 処理を中断した場合は、NULLを返す。<BR>
     * 処理結果のメッセージはgetMessage()メソッドで取得する。<BR>
     * @param seachParam 検索条件
     * @return 検索結果
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    public Parameter[] query(Parameter seachParam)
            throws CommonException
    {
        // 日次更新チェック
        if (!canStart())
        {
            return null;
        }

        //データ報告中チェック
        if (isReportData())
        {
            return null;
        }

        PCTRetrievalInParameter inParam = (PCTRetrievalInParameter)seachParam;
        PCTRetWorkInfoFinder finder = null;

        try
        {
            finder = new PCTRetWorkInfoFinder(getConnection());
            // カーソルオープン
            finder.open(true);

            // 検索処理実行
            int count = finder.search(createSearchKey(inParam), WmsParam.MAX_NUMBER_OF_DISP);

            // 取得件数に応じてメッセージを設定
            if (canLowerDisplay(count))
            {
                // 検索結果をParameter配列に変換して取得
                Parameter[] outParam = createQueryResult(finder);
                return outParam;
            }
            return null;
        }
        finally
        {
            // カーソルクローズ
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
     * PCT出庫作業情報を取得するための検索キークラスのインスタンスを生成します。<BR>
     * <BR>
     * @param inParam PCT出庫入力パラメータ
     * @return PCT出庫作業情報を取得するための検索キークラスのインスタンス
     */
    protected SearchKey createSearchKey(PCTRetrievalInParameter inParam)
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
            // ユーザID(FROM)
            if (!StringUtil.isBlank(inParam.getUserIdFrom()))
            {
                if (inParam.isRangeFlag())
                {
                    // 範囲指定あり
                    wSearchKey.setUserId(inParam.getUserIdFrom(), ">=");
                }
                else
                {
                    // 範囲指定なし
                    wSearchKey.setUserId(inParam.getUserIdFrom(), "=");
                }
            }
            // ユーザID(TO)
            if (!StringUtil.isBlank(inParam.getUserIdTo()))
            {
                if (inParam.isRangeFlag())
                {
                    // 範囲指定あり
                    wSearchKey.setUserId(inParam.getUserIdTo(), "<=");
                }
            }
            // 状態フラグ
            wSearchKey.setStatusFlag(new String[] {
                    PCTRetrievalInParameter.STATUS_FLAG_EXIST_WORKED,
                    PCTRetrievalInParameter.STATUS_FLAG_COMPLETION
            }, true);

            /*
             * 副問合せ条件
             */
            // 設定単位キー
            wSearchKey.setKey(PCTRetWorkInfo.SETTING_UNIT_KEY, subSearchKey);

            /*
             * 取得項目のセット
             */
            // ユーザID(ログインユーザ設定テーブル)
            wSearchKey.setCollect(Com_loginuser.USERID);
            // ユーザ名(ログインユーザ設定テーブル)
            wSearchKey.setCollect(Com_loginuser.USERNAME, "", Com_loginuser.USERNAME);
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
            wSearchKey.setGroup(Com_loginuser.USERID);
            // ユーザ名
            wSearchKey.setGroup(Com_loginuser.USERNAME);

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
            wSearchKey.setOrder(Com_loginuser.USERID, false);

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
     * @param finder PCT出庫作業情報検索クラス
     * @return params
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected Parameter[] createQueryResult(PCTRetWorkInfoFinder finder)
            throws CommonException
    {
        // 作業情報取得結果        
        PCTRetWorkInfo[] workInfoEntity = (PCTRetWorkInfo[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        PCTRetrievalOutParameter[] outParam = new PCTRetrievalOutParameter[workInfoEntity.length];

        // 件数文繰返し
        for (int i = 0; i < outParam.length; i++)
        {
            outParam[i] = new PCTRetrievalOutParameter();
            // ユーザID
            outParam[i].setUserId((String)workInfoEntity[i].getValue(Com_loginuser.USERID));
            // ユーザ名称
            outParam[i].setUserName((String)workInfoEntity[i].getValue(Com_loginuser.USERNAME, ""));
            // ロット数(作業数量)
            outParam[i].setLotQty(workInfoEntity[i].getResultQty());
            // オーダー数
            outParam[i].setOrderCnt(Integer.parseInt(String.valueOf(workInfoEntity[i].getValue(ORDER_COUNT, ""))));
            // 行数
            outParam[i].setLineCnt(Integer.parseInt(String.valueOf(workInfoEntity[i].getValue(LINE_COUNT, ""))));

            try
            {
                // 生産率
                outParam[i].setProductionRate(WmsFormatter.toProductionRate(getProductionRate(outParam[i].getUserId(),
                        outParam[i].getLotQty())));
            }
            catch (Exception ex)
            {
                throw new ReadWriteException(ex);
            }
        }
        return outParam;
    }

    /**
     * 該当作業日の指定ユーザに対する生産率を算出します。<BR>
     * <BR>
     * @param  userId ユーザID
     * @param  lotQty ロット数(作業数量)
     * @return 生産率
     * @throws CommonException 例外が発生した場合に通知されます。
     * @throws ReadWriteException 例外が発生した場合に通知されます。
     */
    protected double getProductionRate(String userId, int lotQty)
            throws CommonException,
                ReadWriteException
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
            // 総作業時間(秒)
            shKy.setWorkTimeCollect("SUM");

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
            // 開始時刻＝終了時刻
            shKy.setJoin(PCTUserResult.WORK_STARTTIME, "", PCTUserResult.WORK_ENDTIME, "");
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
            // ロスタイム(休憩時間とバッテリ交換時間)
            shKy.setWorkTimeCollect("SUM");

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
         * ４．作業当日の実稼働時間を取得する
         *      (総作業時間 + 作業中データの経過時間) - ロスタイム
         * ---------------------------------------------------------------------
         */
        long realOperateTime = (totalWorkTime + workProgressTime) - lostTime;

        /* ---------------------------------------------------------------------
         * ５．時間当たりの作業数を取得する
         *      ロット数/h = (ロット数 / 実稼働時間) * 60 * 60
         * ---------------------------------------------------------------------
         */
        double lotPerH = ((double)lotQty / (double)realOperateTime) * 60 * 60;

        /* ---------------------------------------------------------------------
         * ６．ユーザIDよりロット作業基準値(ランクB)を取得します。
         * ---------------------------------------------------------------------
         */
        double lotStdVal = 0;
        try
        {
            lotStdVal = getLotStdValue(userId);
        }
        catch (Exception ex)
        {
            throw new ReadWriteException(ex);
        }

        /* ---------------------------------------------------------------------
         * ７．ロット作業基準値(ランクB)より、生産率を取得します。
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
     * @throws CommonException 例外が発生した場合に通知されます。
     * @throws ReadWriteException 例外が発生した場合に通知されます。
     */
    protected double getLotStdValue(String userId)
            throws CommonException,
                ReadWriteException
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
}
