// $Id: RankSettingSCH.java 4274 2009-05-13 04:57:26Z okayama $
package jp.co.daifuku.pcart.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.system.schedule.RankSettingSCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.ConsignorFinder;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTAllUserResultAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTAllUserResultHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTAllUserResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTPickingResultHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTPickingResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemSearchKey;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.PCTAllUserResult;
import jp.co.daifuku.wms.base.entity.PCTPickingResult;
import jp.co.daifuku.wms.base.entity.PCTSystem;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * 作業基準値設定のスケジュール処理を行います。
 *
 * @version $Revision: 4274 $, $Date:: 2009-05-13 13:57:26 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class RankSettingSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** ランクA */
    private final int KEY_LANK_A = 0;

    /** ランクB */
    private final int KEY_LANK_B = 1;

    /** ランクC */
    private final int KEY_LANK_C = 2;

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
    public RankSettingSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
        // PCTシステム情報を取得する
        List<Params> outPCTSystemData = getPCTSystemData();
        if (!canLowerDisplay(outPCTSystemData.size()))
        {
            // PCTシステム情報がなかったら空オブジェクトを返す
            return outPCTSystemData;
        }

        // 検索条件がセットされていなければ、PCTシステム情報のみを返す
        // 荷主コード
        if (StringUtil.isBlank(p.getString(CONSIGNOR_CODE)))
        {
            setMessage("");
            // PCTシステム情報のみを返す
            return outPCTSystemData;
        }
        // エリアNo.
        if (StringUtil.isBlank(p.getString(AREA)))
        {
            setMessage("");
            // PCTシステム情報のみを返す
            return outPCTSystemData;
        }

        // 空のパラメータオブジェクトを返す
        List<Params> outParam = new ArrayList<Params>();

        // 荷主マスタ存在チェック
        if (!isConsignorOK(p.getString(CONSIGNOR_CODE)))
        {
            // 荷主コードはセットされているが、荷主マスタが存在しない場合は、

            return outParam;
        }

        // PCT全ユーザー実績情報を取得する
        List<Params> outRankListData = getRankListData(p);
        if (outRankListData.size() == 0)
        {
            // 出力パラメータの先頭にPCTシステム情報をセットする
            outParam = outPCTSystemData;
            // 6123001=対象データはありませんでした。
            setMessage(WmsMessageFormatter.format(6123001));
        }
        else
        {
            // 出力パラメータにPCT全ユーザー実績情報をセットする
            outParam = outRankListData;
            setMessage(WmsMessageFormatter.format(6001013));
        }

        return outParam;
    }

    /**
     * ランク基準設定「自動計算」処理<BR>
     * ピッキング実績集計情報よりランクB(基準値)を自動算出し、結果を取得します。<BR>
     * 算出方法<BR>
     * ★ランクBの値を基準(100%)とするため、まず以下の方法で時間当り数量を算出し、<BR>
     * この値をランクBの結果とする。<BR>
     * ◇ピッキング実績集計情報より以下の情報を読込み<BR>
     * ・総ロット数(作業数量)を獲得<BR>
     * ・総オーダー作業数(作業回数「オーダー」)を獲得<BR>
     * ・総行数(作業回数「明細数」)を獲得<BR>
     * ・総稼働時間を獲得(秒)<BR>
     * ・総バッテリ交換時間(秒)<BR>
     * ・総休憩時間(秒)<BR>
     * 
     * ◇総実稼働時間を算出<BR>
     * ・総実稼働時間 = (総稼働時間 - 総バッテリ交換時間 - 総休憩時間) / 3600秒<BR>
     * 
     * ◆ランクBの時間当り数量を算出<BR>
     * ・ロット数/h(ZZ,ZZ9.9) = 総ロット数 / 総実稼働時間<BR>
     * ・オーダー数/h(ZZ,ZZ9.9) = 総オーダー作業数 / 総実稼働時間<BR>
     * ・行数/h(ZZ,ZZ9.9) = 総行数 / 総実稼働時間<BR>
     * 
     * ★ランクBの算出結果を基に、ランクAの時間当り数量を算出<BR>
     * ・ロット数/h(ZZ,ZZ9.9) = (ランクBのロット数/h * PCTシステム情報のランクA基準値(%)) / 100%<BR>
     * ・オーダー数/h(ZZ,ZZ9.9) = (ランクBのオーダー数/h * PCTシステム情報のランクA基準値(%)) / 100%<BR>
     * ・行数/h(ZZ,ZZ9.9) = (ランクBの行数/h * PCTシステム情報のランクA基準値(%)) / 100%<BR>
     * 
     * ★ランクBの算出結果を基に、ランクCの時間当り数量を算出<BR>
     * ・ロット数/h(ZZ,ZZ9.9) = (ランクBのロット数/h * PCTシステム情報のランクB基準値(%)) / 100%<BR>
     * ・オーダー数/h(ZZ,ZZ9.9) = (ランクBのオーダー数/h * PCTシステム情報のランクB基準値(%)) / 100%<BR>
     * ・行数/h(ZZ,ZZ9.9) = (ランクBの行数/h * PCTシステム情報のランクB基準値(%)) / 100%<BR>
     * startParamsで指定するParameterクラスはPCTSystemInParameter型であること。<BR>
     * 
     * @param searchParam
     *            検索条件パラメータ
     * @return ランク基準設定一覧表示パラメータ
     * @throws CommonException
     *             何らかの例外が発生した場合に通知されます。
     */
    public List<Params> autoCalculation(ScheduleParams... p)
            throws CommonException
    {
        try
        {

            // ピッキング実績集計情報より、データを取得する
            // ピッキング実績集計情報の検索
            PCTPickingResultHandler handler = new PCTPickingResultHandler(getConnection());
            PCTPickingResultSearchKey shKy = new PCTPickingResultSearchKey();

            /*
             * ピッキング実績集計情報の検索キーセット
             */
            // 荷主コード
            shKy.setConsignorCode(p[0].getString(CONSIGNOR_CODE));
            // エリアNo.
            shKy.setAreaNo(p[0].getString(AREA));

            /*
             * PCT全ユーザ実績情報の読込み対象項目セット
             */
            // 総ロット数(作業数)
            shKy.setWorkQtyCollect("SUM");
            // 総オーダー作業数(作業回数「オーダー」)
            shKy.setOrderCntCollect("SUM");
            // 総行数(作業回数「明細数」)
            shKy.setWorkCntCollect("SUM");
            // 総稼働時間
            shKy.setOperateTimeCollect("SUM");
            // 総バッテリ交換時間
            shKy.setBatteryChangeTimeCollect("SUM");
            // 総休憩時間
            shKy.setBreakTimeCollect("SUM");

            // ﾛｯﾄ/H
            double lotStdVal = 0;
            // ｵｰﾀﾞｰ/H
            double orderStdVal = 0;
            // 行/H
            double lineStdVal = 0;

            // 検索結果を取得
            PCTPickingResult[] entity = (PCTPickingResult[])handler.find(shKy);
            if (!ArrayUtil.isEmpty(entity))
            {
                // 総実稼働時間
                double realOperateTime =
                        (double)entity[0].getOperateTime() - (double)entity[0].getBatteryChangeTime()
                                - (double)entity[0].getBreakTime();
                if (realOperateTime > 0)
                {
                    realOperateTime = realOperateTime / 3600;
                }

                // ロット数/h
                lotStdVal = getPerHour(entity[0].getWorkQty(), realOperateTime);
                // オーダー数/h
                orderStdVal = getPerHour(entity[0].getOrderCnt(), realOperateTime);
                // 行数/h
                lineStdVal = getPerHour(entity[0].getWorkCnt(), realOperateTime);
            }

            // PCTシステム情報を取得する
            List<Params> SystemData = getPCTSystemData();
            if (!canLowerDisplay(SystemData.size()))
            {
                // PCTシステム情報がなかったら空オブジェクトを返す
                return SystemData;
            }
            Params sysparam = new Params();
            sysparam = SystemData.get(0);

            List<Params> outParam = new ArrayList<Params>();

            Params param = new Params();
            param = SystemData.get(0);

            // PCT全ユーザー実績情報を取得する
            // 存在すれば更新時、存在しなければ新規登録時
            List<Params> outRankListData = getRankListData(p[0]);

            /*
             * ランクAの算出結果
             */
            // レベル
            param.set(LEVEL, DisplayResource.getLevelName(SystemDefine.RANK_NO_A));
            // ロット数/h
            param.set(LOT_COUNT, getRate(lotStdVal, sysparam.getInt(A_RANK_STANDARD_VALUE)));
            // オーダー数/h
            param.set(ORDER_COUNT, getRate(orderStdVal, sysparam.getInt(A_RANK_STANDARD_VALUE)));
            // 行数/h
            param.set(LINE_COUNT, getRate(lineStdVal, sysparam.getInt(A_RANK_STANDARD_VALUE)));
            // PCT全ユーザ実績情報が存在していた場合
            if (outRankListData.size() != 0)
            {
                // 最終更新日時(ランクA)をセット
                param.set(LAST_UP_DATE, outRankListData.get(0).getDate(LAST_UP_DATE));
            }
            outParam.add(param);

            param = new Params();

            /*
             * ランクBの算出結果
             */
            // レベル
            param.set(LEVEL, DisplayResource.getLevelName(SystemDefine.RANK_NO_B));
            // ロット数/h
            param.set(LOT_COUNT, lotStdVal);
            // オーダー数/h
            param.set(ORDER_COUNT, orderStdVal);
            // 行数/h
            param.set(LINE_COUNT, lineStdVal);
            // PCT全ユーザ実績情報が存在していた場合
            if (outRankListData.size() != 0)
            {
                // 最終更新日時(ランクB)をセット
                param.set(LAST_UP_DATE, outRankListData.get(1).getDate(LAST_UP_DATE));
            }
            outParam.add(param);

            param = new Params();

            /*
             * ランクCの算出結果
             */
            // レベル
            param.set(LEVEL, DisplayResource.getLevelName(SystemDefine.RANK_NO_C));
            // ロット数/h
            param.set(LOT_COUNT, getRate(lotStdVal, sysparam.getInt(B_RANK_STANDARD_VALUE)));
            // オーダー数/h
            param.set(ORDER_COUNT, getRate(orderStdVal, sysparam.getInt(B_RANK_STANDARD_VALUE)));
            // 行数/h
            param.set(LINE_COUNT, getRate(lineStdVal, sysparam.getInt(B_RANK_STANDARD_VALUE)));
            // PCT全ユーザ実績情報が存在していた場合
            if (outRankListData.size() != 0)
            {
                // 最終更新日時(ランクC)をセット
                param.set(LAST_UP_DATE, outRankListData.get(2).getDate(LAST_UP_DATE));
            }
            outParam.add(param);

            return outParam;
        }
        catch (NotFoundException e)
        {
            // 6003008=該当情報が存在しません
            setMessage(WmsMessageFormatter.format(6003008));
            return new ArrayList<Params>();
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

            // 取り込み中チェック
            if (isLoadData())
            {
                return false;
            }

            // 入力妥当性チェック

            // PCTシステム情報を取得する
            List<Params> outPCTSystemData = getPCTSystemData();
            if (!canLowerDisplay(outPCTSystemData.size()))
            {
                // PCTシステム情報がなかったら空オブジェクトを返す
                return false;
            }

            Params param = new Params();
            param = outPCTSystemData.get(0);

            // PCT全ユーザ実績情報ハンドラ生成
            PCTAllUserResultHandler allUserResultHandler = new PCTAllUserResultHandler(getConnection());
            PCTAllUserResultAlterKey allUserResultAKey = new PCTAllUserResultAlterKey();
            PCTAllUserResultSearchKey allUserResultSKey = new PCTAllUserResultSearchKey();

            /*
             * ランク毎の値をセット
             */
            // ﾛｯﾄ/H(ランクB)
            double lotStdVal_B = Double.valueOf(ps[0].getString(S_LOT_COUNT));
            // ｵｰﾀﾞｰ/H(ランクB)
            double orderStdVal_B = Double.valueOf(ps[0].getString(S_ORDER_COUNT));
            // 行/H(ランクB)
            double lineStdVal_B = Double.valueOf(ps[0].getString(S_LINE_COUNT));

            // ﾛｯﾄ/H(ランクA)
            double maxStandardValue = WmsParam.MAX_STANDARD_VALUE;
            double lotStdVal_A = getRate(lotStdVal_B, param.getInt(A_RANK_STANDARD_VALUE));
            if (lotStdVal_A > maxStandardValue)
            {
                // 6023615={0}の値が基準値上限数{1}を超えないよう{2}を入力してください。
                setMessage(WmsMessageFormatter.format(6023615,
                        DisplayResource.getLevelName(PCTAllUserResult.LEVEL_NO_A),
                        WmsFormatter.getNumFormat(maxStandardValue), DisplayText.getText("LBL-P0176")));
                return false;
            }
            // ｵｰﾀﾞｰ/H(ランクA)
            double orderStdVal_A = getRate(orderStdVal_B, param.getInt(A_RANK_STANDARD_VALUE));
            if (orderStdVal_A > maxStandardValue)
            {
                // 6023615={0}の値が基準値上限数{1}を超えないよう{2}を入力してください。
                setMessage(WmsMessageFormatter.format(6023615,
                        DisplayResource.getLevelName(PCTAllUserResult.LEVEL_NO_A),
                        WmsFormatter.getNumFormat(maxStandardValue), DisplayText.getText("LBL-P0177")));
                return false;
            }
            // 行/H(ランクA)
            double lineStdVal_A = getRate(lineStdVal_B, param.getInt(A_RANK_STANDARD_VALUE));
            if (lineStdVal_A > maxStandardValue)
            {
                // 6023615={0}の値が基準値上限数{1}を超えないよう{2}を入力してください。
                setMessage(WmsMessageFormatter.format(6023615,
                        DisplayResource.getLevelName(PCTAllUserResult.LEVEL_NO_A),
                        WmsFormatter.getNumFormat(maxStandardValue), DisplayText.getText("LBL-P0178")));
                return false;
            }

            // ﾛｯﾄ/H(ランクC)
            double lotStdVal_C = getRate(lotStdVal_B, param.getInt(B_RANK_STANDARD_VALUE));
            // ｵｰﾀﾞｰ/H(ランクC)
            double orderStdVal_C = getRate(orderStdVal_B, param.getInt(B_RANK_STANDARD_VALUE));
            // 行/H(ランクC)
            double lineStdVal_C = getRate(lineStdVal_B, param.getInt(B_RANK_STANDARD_VALUE));

            // PCT全ユーザ実績情報の検索キークリア
            allUserResultSKey.clear();
            // 荷主コード
            allUserResultSKey.setConsignorCode(ps[0].getString(CONSIGNOR_CODE));
            // エリア
            allUserResultSKey.setAreaNo(ps[0].getString(AREA));

            // 更新対象データが存在する場合は、更新処理を行なう。
            if (allUserResultHandler.count(allUserResultSKey) > 0)
            {
                /*
                 * 更新条件(ランクB)
                 */
                allUserResultAKey.clear();
                // 荷主コード
                allUserResultAKey.setConsignorCode(ps[KEY_LANK_B].getString(CONSIGNOR_CODE));
                // エリア
                allUserResultAKey.setAreaNo(ps[KEY_LANK_B].getString(AREA));
                // ランクB
                allUserResultAKey.setRank(SystemDefine.RANK_NO_B);
                // 最終更新日時
                allUserResultAKey.setLastUpdateDate(ps[KEY_LANK_B].getDate(LAST_UP_DATE));

                /*
                 * 更新値(ランクB)
                 */
                // ﾛｯﾄ/H
                allUserResultAKey.updateLotStandardValue(lotStdVal_B);
                // ｵｰﾀﾞｰ/H
                allUserResultAKey.updateOrderStandardValue(orderStdVal_B);
                // 行/H
                allUserResultAKey.updateLineStandardValue(lineStdVal_B);
                // 最終更新処理名
                allUserResultAKey.updateLastUpdatePname(this.getClass().getSimpleName());
                // 更新処理
                allUserResultHandler.modify(allUserResultAKey);

                /*
                 * 更新条件(ランクA)
                 */
                allUserResultAKey.clear();
                // 荷主コード
                allUserResultAKey.setConsignorCode(ps[KEY_LANK_A].getString(CONSIGNOR_CODE));
                // エリア
                allUserResultAKey.setAreaNo(ps[KEY_LANK_A].getString(AREA));
                // ランクB
                allUserResultAKey.setRank(SystemDefine.RANK_NO_A);
                // 最終更新日時
                allUserResultAKey.setLastUpdateDate(ps[KEY_LANK_A].getDate(LAST_UP_DATE));

                /*
                 * 更新値(ランクA)
                 */
                // ﾛｯﾄ/H
                allUserResultAKey.updateLotStandardValue(lotStdVal_A);
                // ｵｰﾀﾞｰ/H
                allUserResultAKey.updateOrderStandardValue(orderStdVal_A);
                // 行/H
                allUserResultAKey.updateLineStandardValue(lineStdVal_A);
                // 最終更新処理名
                allUserResultAKey.updateLastUpdatePname(this.getClass().getSimpleName());
                // 更新処理
                allUserResultHandler.modify(allUserResultAKey);

                /*
                 * 更新条件(ランクC)
                 */
                allUserResultAKey.clear();
                // 荷主コード
                allUserResultAKey.setConsignorCode(ps[KEY_LANK_C].getString(CONSIGNOR_CODE));
                // エリア
                allUserResultAKey.setAreaNo(ps[KEY_LANK_C].getString(AREA));
                // ランクB
                allUserResultAKey.setRank(SystemDefine.RANK_NO_C);
                // 最終更新日時
                allUserResultAKey.setLastUpdateDate(ps[KEY_LANK_C].getDate(LAST_UP_DATE));

                /*
                 * 更新値(ランクC)
                 */
                // ﾛｯﾄ/H
                allUserResultAKey.updateLotStandardValue(lotStdVal_C);
                // ｵｰﾀﾞｰ/H
                allUserResultAKey.updateOrderStandardValue(orderStdVal_C);
                // 行/H
                allUserResultAKey.updateLineStandardValue(lineStdVal_C);
                // 最終更新処理名
                allUserResultAKey.updateLastUpdatePname(this.getClass().getSimpleName());
                // 更新処理
                allUserResultHandler.modify(allUserResultAKey);
            }
            // 更新対象データが存在しない場合は、登録処理を行なう。
            else
            {
                /*
                 * 共通項目セット
                 */
                PCTAllUserResult allUserResult = new PCTAllUserResult();

                // 荷主コード
                allUserResult.setConsignorCode(ps[0].getString(CONSIGNOR_CODE));
                // エリア
                allUserResult.setAreaNo(ps[0].getString(AREA));
                // 登録処理名
                allUserResult.setRegistPname(this.getClass().getSimpleName());
                // 最終更新処理名
                allUserResult.setLastUpdatePname(this.getClass().getSimpleName());

                /*
                 * 登録値(ランクB)
                 */
                // ランクB
                allUserResult.setRank(SystemDefine.RANK_NO_B);
                // ﾛｯﾄ/H
                allUserResult.setLotStandardValue(lotStdVal_B);
                // ｵｰﾀﾞｰ/H
                allUserResult.setOrderStandardValue(orderStdVal_B);
                // 行/H
                allUserResult.setLineStandardValue(lineStdVal_B);
                // 登録処理
                allUserResultHandler.create(allUserResult);

                /*
                 * 登録値(ランクA)
                 */
                // ランクA
                allUserResult.setRank(SystemDefine.RANK_NO_A);
                // ﾛｯﾄ/H
                allUserResult.setLotStandardValue(lotStdVal_A);
                // ｵｰﾀﾞｰ/H
                allUserResult.setOrderStandardValue(orderStdVal_A);
                // 行/H
                allUserResult.setLineStandardValue(lineStdVal_A);
                // 登録処理
                allUserResultHandler.create(allUserResult);

                /*
                 * 登録値(ランクC)
                 */
                // ランクC
                allUserResult.setRank(SystemDefine.RANK_NO_C);
                // ﾛｯﾄ/H
                allUserResult.setLotStandardValue(lotStdVal_C);
                // ｵｰﾀﾞｰ/H
                allUserResult.setOrderStandardValue(orderStdVal_C);
                // 行/H
                allUserResult.setLineStandardValue(lineStdVal_C);
                // 登録処理
                allUserResultHandler.create(allUserResult);
            }

            // 6001006=設定しました。
            setMessage(WmsMessageFormatter.format(6001006));
            return true;
        }
        catch (NotFoundException e)
        {
            // 6023115 = 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023115));
            return false;
        }
    }

    /**
     * 初期表示を行います。<BR>
     * <BR>
     * 概要：PCT出庫作業情報に該当荷主が1件しかない場合は初期表示を行います。<BR>
     * 
     * @param searchParam 検索条件
     * @return 表示パラメータ データがない場合、nullを返す。
     * @throws CommonException 全ての例外を報告します。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        // 荷主マスタ情報
        ConsignorHandler consignorHandler = new ConsignorHandler(getConnection());
        ConsignorSearchKey skey = new ConsignorSearchKey();

        // 検索条件
        skey.setConsignorCodeCollect();
        skey.setConsignorCodeGroup();

        if (consignorHandler.count(skey) == 1)
        {
            Params outParam = new Params();
            Consignor consignor = (Consignor)consignorHandler.findPrimary(skey);
            // 荷主コード
            outParam.set(RankSettingSCHParams.CONSIGNOR_CODE, consignor.getConsignorCode());

            return outParam;
        }
        return null;
    }

    /**
     * 初期表示・一覧クリア時にランク基準値を取得します。<BR>
     * <BR>
     * 概要：初期表示・一覧クリア時にランク基準値の表示を行います。<BR>
     * 
     * @param searchParam 検索条件
     * @return 表示パラメータ データがない場合、nullを返す。
     * @throws CommonException 全ての例外を報告します。
     */
    public Params rankDisp(ScheduleParams pr)
            throws CommonException
    {
        // PCTシステム情報より、データを取得する
        // PCTシステムの検索
        PCTSystemHandler sysHandler = new PCTSystemHandler(getConnection());
        PCTSystemSearchKey sysSkey = new PCTSystemSearchKey();

        if (sysHandler.count(sysSkey) == 1)
        {
            Params outParam = new Params();
            PCTSystem pctSystemEnt = (PCTSystem)sysHandler.findPrimary(sysSkey);

            // ランクA基準値
            outParam.set(A_RANK_STANDARD_VALUE, pctSystemEnt.getARankStandardValue());
            // ランクB基準値
            outParam.set(B_RANK_STANDARD_VALUE, pctSystemEnt.getBRankStandardValue());

            return outParam;


        }
        return null;
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
     * PCTシステム情報を取得します。<BR>
     * 
     * @return PCTシステム情報(ランクA基準値、ランクB基準値)
     * @throws CommonException 全ての例外をスローします。
     */
    protected List<Params> getPCTSystemData()
            throws CommonException
    {
        // PCTシステム情報より、データを取得する
        // PCTシステムの検索
        PCTSystemHandler sysHandler = new PCTSystemHandler(getConnection());
        PCTSystemSearchKey sysSkey = new PCTSystemSearchKey();
        PCTSystem pctSystemEnt = (PCTSystem)sysHandler.findPrimaryForUpdate(sysSkey);

        // 出力用パラメータを宣言
        List<Params> outParam = new ArrayList<Params>();

        if (pctSystemEnt != null)
        {
            Params sysparam = new Params();

            // ランクA基準値
            sysparam.set(A_RANK_STANDARD_VALUE, pctSystemEnt.getARankStandardValue());
            // ランクB基準値
            sysparam.set(B_RANK_STANDARD_VALUE, pctSystemEnt.getBRankStandardValue());

            outParam.add(sysparam);
            return outParam;
        }

        // 6003008=該当情報が存在しません
        setMessage(WmsMessageFormatter.format(6003008));
        return outParam;
    }

    /**
     * PCT全ユーザ実績情報からランク毎の作業数を取得します。<BR>
     * ・ロット数/h<BR>
     * ・オーダー数/h<BR>
     * ・行数/h<BR>
     * 
     * @param inParam 検索パラメータ
     * @return PCTシステム情報(ランクA基準値、ランクB基準値)
     * @throws CommonException 全ての例外をスローします。
     */
    protected List<Params> getRankListData(ScheduleParams p)
            throws CommonException
    {
        // PCT全ユーザ実績情報より、データを取得する
        // PCT全ユーザ実績情報の検索
        PCTAllUserResultHandler handler = new PCTAllUserResultHandler(getConnection());
        PCTAllUserResultSearchKey shKy = new PCTAllUserResultSearchKey();

        /*
         * PCT全ユーザ実績情報の検索キーセット
         */
        // 荷主コード
        shKy.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // エリア
        shKy.setAreaNo(p.getString(AREA));

        /*
         * PCT全ユーザ実績情報の読込み対象項目セット
         */
        // ランク
        shKy.setRankCollect();
        // ロット数/h
        shKy.setLotStandardValueCollect();
        // オーダー数/h
        shKy.setOrderStandardValueCollect();
        // 行数/h
        shKy.setLineStandardValueCollect();
        // 最終更新日時
        shKy.setLastUpdateDateCollect();

        /*
         * PCT全ユーザ実績情報の読込み順セット
         */
        // ランク
        shKy.setRankOrder(true);

        // 
        // 検索結果を取得
        PCTAllUserResult[] entity = (PCTAllUserResult[])handler.find(shKy);
        if (entity == null || entity.length == 0)
        {
            // 条件に一致するデータが１件も存在しない場合
            return new ArrayList<Params>();
        }

        // 出力用パラメータを宣言
        List<Params> outParam = new ArrayList<Params>();

        // 出力パラメータにPCTシステム情報から読込んだデータをセットする
        for (int i = 0; i < entity.length; i++)
        {
            Params rankparam = new Params();

            // レベル
            rankparam.set(LEVEL, DisplayResource.getLevelName(entity[i].getRank()));
            // ロット数/h
            rankparam.set(LOT_COUNT, entity[i].getLotStandardValue());
            // オーダー数/h
            rankparam.set(ORDER_COUNT, entity[i].getOrderStandardValue());
            // 行数/h
            rankparam.set(LINE_COUNT, entity[i].getLineStandardValue());
            // 最終更新日時
            rankparam.set(LAST_UP_DATE, entity[i].getLastUpdateDate());
            outParam.add(rankparam);
        }

        return outParam;
    }

    /**
     * 該当荷主コードがマスタに存在するかチェックします。<BR>
     * 
     * @param consignor 荷主コード
     * @return true or false
     * @throws CommonException 全ての例外をスローします。
     */
    protected boolean isConsignorOK(String consignor)
            throws CommonException
    {
        // 荷主マスタより、データ件数を取得する
        // 荷主マスタの検索
        ConsignorFinder finder = new ConsignorFinder(getConnection());
        ConsignorSearchKey shKy = new ConsignorSearchKey();

        // 荷主コード
        shKy.setConsignorCode(consignor);

        try
        {
            // カーソルのオープン
            finder.open(true);

            int cnt = finder.search(shKy);

            if (cnt == 0)
            {
                // 6023040=荷主コードがマスタに登録されていません。
                setMessage(WmsMessageFormatter.format(6023040));
                return false;
            }
            return true;
        }
        catch (Exception ex)
        {
            // 例外を受け取った場合、処理を中断してnullを返す
            // 6007002=データベースエラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            return false;
        }
        finally
        {
            // クローズ処理
            finder.close();
        }
    }


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    // -----------------------------------------------------------
    // private methods
    // -----------------------------------------------------------
    /**
     * パラメータを基に時間当りの数量を算出します。<BR>
     * 算出結果(ZZ,ZZ9.9) = 数量 / 時間<BR>
     * ※小数点第一位まで、第二位を四捨五入<BR>
     * 
     * @param qty 数量
     * @param time 時間
     * @return 算出結果
     */
    private double getPerHour(double qty, double time)
    {
        if (qty == 0 || time == 0)
        {
            return 0;
        }
        else
        {
            BigDecimal bdQty = new BigDecimal(qty);
            BigDecimal bdRealTime = new BigDecimal(time);
            return bdQty.divide(bdRealTime, 1, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }

    /**
     * パラメータを基に基準値からの比率を算出します。<BR>
     * 算出結果(ZZ,ZZ9.9) = 数量 * 基準値 / 100%<BR>
     * ※小数点第一位まで、第二位を四捨五入<BR>
     * 
     * @param qty 数量
     * @param baseRate 基準値
     * @return 算出結果
     */
    private double getRate(double qty, double baseRate)
    {
        if (qty == 0 || baseRate == 0)
        {
            return 0;
        }
        else
        {
            BigDecimal bdQty = new BigDecimal(qty * baseRate);
            return bdQty.divide(new BigDecimal(100), 1, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
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
