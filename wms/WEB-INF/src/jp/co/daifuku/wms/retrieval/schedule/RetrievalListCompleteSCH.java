// $Id: RetrievalListCompleteSCH.java 7827 2010-04-16 09:46:04Z shibamoto $
package jp.co.daifuku.wms.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.retrieval.schedule.RetrievalListCompleteSCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsChecker;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.retrieval.operator.RetrievalOperator;

/**
 * 出庫リスト作業結果入力のスケジュール処理を行います。
 * 
 * @version $Revision: 7827 $, $Date: 2010-04-16 18:46:04 +0900 (金, 16 4 2010) $
 * @author BusiTune 1.0 Generator.
 * @author Last commit: $Author: shibamoto $
 */
public class RetrievalListCompleteSCH
        extends AbstractSCH
{
    // ------------------------------------------------------------
    // fields (upper case only)
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // class variables (prefix '$')
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // instance variables (prefix '_')
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * 
     * @param conn
     *            DBコネクション
     * @param parent
     *            呼び出し元クラスクラス情報
     * @param locale
     *            ロケール
     * @param ui
     *            ユーザ情報
     * @throws CommonException
     *             ユーザ定義の例外を通知します
     */
    public RetrievalListCompleteSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    // ------------------------------------------------------------
    // public methods
    // ------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、 リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     * 
     * @param p
     *            表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *         該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *         検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *         入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException
     *             チェック処理内で予期しない例外が発生した場合に通知します。
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
            return getDisplayData(finder, p);
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
     * @param startParams
     *            設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
     * @throws CommonException
     *             全ての例外を報告します
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
        RetrievalInParameter[] inParams = new RetrievalInParameter[ps.length];
        int pLength = 0;
        for (int i = 0; i < ps.length; i++)
        {

            // パラメータクラス生成
            inParams[pLength] = new RetrievalInParameter(getWmsUserInfo());

            // 荷主コード
            inParams[pLength].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            // 設定単位キー
            inParams[pLength].setSettingUnitKey(ps[i].getString(WORK_NO));
            // 商品コード
            inParams[pLength].setItemCode(ps[i].getString(ITEM_CODE));
            // 入庫エリア
            inParams[pLength].setRetrievalAreaNo(ps[i].getString(AREA_NO));
            // 実績ロットNo.
            inParams[pLength].setResultLotNo(ps[i].getString(LOT_NO));
            // 実績棚
            inParams[pLength].setRetrievalLocation(ps[i].getString(LOCATION_NO));
            // ケース入数
            inParams[pLength].setEnteringQty(ps[i].getInt(ENTERING_QTY));
            // 出庫予定ケース数
            inParams[pLength].setPlanCaseQty(ps[i].getInt(PLAN_CASE_QTY));
            // 出庫予定ピース数
            inParams[pLength].setPlanPieceQty(ps[i].getInt(PLAN_PIECE_QTY));
            // 出庫ケース数
            inParams[pLength].setResultCaseQty(ps[i].getInt(RESULT_CASE_QTY));
            // 出庫ピース数
            inParams[pLength].setResultPieceQty(ps[i].getInt(RESULT_PIECE_QTY));
            // 予定数
            int planQty = ps[i].getInt(ENTERING_QTY) * ps[i].getInt(PLAN_CASE_QTY) + ps[i].getInt(PLAN_PIECE_QTY);
            inParams[pLength].setPlanQty(planQty);
            // 実績数
            int resultQty = ps[i].getInt(ENTERING_QTY) * ps[i].getInt(RESULT_CASE_QTY) + ps[i].getInt(RESULT_PIECE_QTY);
            inParams[pLength].setResultQty(resultQty);
            // 欠品
            inParams[pLength].setShortageCompletionFlag(ps[i].getBoolean(SHORTAGE));
            // 完了フラグ(1:確定(全数完了 or 欠品完了))
            inParams[pLength].setCompletionFlag(RetrievalInParameter.COMPLETION_FLAG_DECISION);
            // 集約作業No.
            inParams[pLength].setCollectJobNo(ps[i].getString(COLLECT_JOBNO));
            // ハードウェア区分
            inParams[pLength].setHardwareType(WorkInfo.HARDWARE_TYPE_LIST);
            // ユーザ情報
            inParams[pLength].setWmsUserInfo(getWmsUserInfo());

            // インクリメント
            pLength++;
        }

        try
        {
            // オペレータ生成
            RetrievalOperator operator = new RetrievalOperator(getConnection(), getClass());
            // オペレータ呼び出し
            operator.complete(inParams);

            // (6001014)完了しました。
            setMessage("6001014");

            // 正常の場合はtrueを返却
            return true;

        }
        catch (LockTimeOutException e)
        {
            // (6023114)他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023114));

            // 異常の場合はfalseを返却
            return false;
        }
        catch (NotFoundException e)
        {
            // (6023115)他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015));

            // 異常の場合はfalseを返却
            return false;
        }
        catch (OperatorException e)
        {
            // エラー行番号(オペレータ内で+1されるため-1する)
            int errorRow = inParams[e.getErrorLineNo() - 1].getRowNo();

            // 「他端末で更新済み」か「他端末で作業中」か「作業完了済み」
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // (6023015)No.{0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, errorRow));
                setErrorRowIndex(ps[e.getErrorLineNo() - 1].getRowIndex());

                // 異常の場合はfalseを返却
                return false;
            }
            // 在庫の引当で不足数が発生した場合
            else if (OperatorException.ERR_SHORTAGE_ALLOCATION_QTY.equals(e.getErrorCode()))
            {
                // (6023189)No.{0} 出庫数には引当可能数以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023189, errorRow));
                setErrorRowIndex(ps[e.getErrorLineNo() - 1].getRowIndex());

                // 異常の場合はfalseを返却
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
        }
    }

    // ------------------------------------------------------------
    // accessor methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // package methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // protected methods
    // ------------------------------------------------------------
    /**
     * 検索条件をセットします。
     * 
     * @param p
     *            検索条件を含むScheduleParams
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        WorkInfoSearchKey searchKey = new WorkInfoSearchKey();

        // set where
        // 荷主コード
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 出庫予定日
        if (!StringUtil.isBlank(p.getString(RETRIEVAL_PLAN_DATE)))
        {
            searchKey.setPlanDay(WmsFormatter.toParamDate(p.getDate(RETRIEVAL_PLAN_DATE)));
        }
        // 設定単位キー
        if (!StringUtil.isBlank(p.getString(LIST_WORK_NO)))
        {
            searchKey.setSettingUnitKey(p.getString(LIST_WORK_NO));
        }
        // 状態フラグ(作業中)
        searchKey.setStatusFlag(WorkInfo.STATUS_FLAG_NOWWORKING);
        // ハードウェア区分(リスト)
        searchKey.setHardwareType(WorkInfo.HARDWARE_TYPE_LIST);
        // 作業区分(出庫)
        searchKey.setJobType(WorkInfo.JOB_TYPE_RETRIEVAL);

        // set join
        // 商品マスタ情報取得
        searchKey.setJoin(WorkInfo.CONSIGNOR_CODE, "", Item.CONSIGNOR_CODE, "(+)");
        searchKey.setJoin(WorkInfo.ITEM_CODE, "", Item.ITEM_CODE, "(+)");
        // エリアマスタ情報取得
        searchKey.setJoin(WorkInfo.PLAN_AREA_NO, "", Area.AREA_NO, "(+)");

        // set order by
        // 出庫エリア
        searchKey.setPlanAreaNoOrder(true);
        // 出庫棚
        searchKey.setPlanLocationNoOrder(true);
        // 商品コード
        searchKey.setItemCodeOrder(true);
        // ロットNo.
        searchKey.setPlanLotNoOrder(true);

        // set collect

        // リスト作業No.
        searchKey.setSettingUnitKeyCollect();
        searchKey.setSettingUnitKeyGroup();

        // オーダーNo.
        searchKey.setOrderNoCollect();
        searchKey.setOrderNoGroup();

        // 出庫エリア
        searchKey.setPlanAreaNoCollect();
        searchKey.setPlanAreaNoGroup();

        // 出庫エリア名称
        searchKey.setCollect(Area.AREA_NAME);

        // 予定棚
        searchKey.setPlanLocationNoCollect();
        searchKey.setPlanLocationNoGroup();

        // 商品コード
        searchKey.setItemCodeCollect();
        searchKey.setItemCodeGroup();

        // 集約作業No.
        searchKey.setCollectJobNoCollect();
        searchKey.setCollectJobNoGroup();

        // 予定数
        searchKey.setPlanQtyCollect("SUM");

        // 出庫数
        searchKey.setResultQtyCollect("SUM");

        // ロットNo.
        searchKey.setPlanLotNoCollect();
        searchKey.setPlanLotNoGroup();

        // 商品マスタから商品名称
        searchKey.setCollect(Item.ITEM_NAME);
        searchKey.setGroup(Item.ITEM_NAME);

        // 商品マスタから入数
        searchKey.setCollect(Item.ENTERING_QTY);
        searchKey.setGroup(Item.ENTERING_QTY);

        // エリアマスタからエリア名称を取得
        searchKey.setCollect(Area.AREA_NAME);
        searchKey.setGroup(Area.AREA_NAME);

        return searchKey;
    }

    /**
     * 表示情報を取得します。
     * 
     * @param finder
     *            検索結果を含むFinder
     * @return List<Params>
     * @throws ReadWriteException
     *             データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder, ScheduleParams p)
            throws ReadWriteException
    {
        // 最大表示件数分検索結果を取得する
        WorkInfo[] entities = (WorkInfo[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        // 返却用の配列を生成
        List<Params> result = new ArrayList<Params>();

        // 取得データ件数分、繰り返す
        for (WorkInfo ent : entities)
        {
            // パラメータクラス生成
            Params param = new Params();

            // 返却データをセット
            // 入庫予定日
            param.set(RETRIEVAL_PLAN_DATE, p.get(RETRIEVAL_PLAN_DATE));
            // 荷主コード
            param.set(CONSIGNOR_CODE, p.get(CONSIGNOR_CODE));
            // 集約作業No
            param.set(COLLECT_JOBNO, ent.getCollectJobNo());
            // リスト作業No.
            param.set(WORK_NO, ent.getSettingUnitKey());
            // オーダーNo.
            param.set(ORDER_NO, ent.getOrderNo());
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            param.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME, ""));
            // ロットNO.
            param.set(LOT_NO, ent.getPlanLotNo());
            // 出庫予定エリア
            param.set(AREA_NO, ent.getPlanAreaNo());
            // 出庫予定エリア名称
            param.set(AREA_NAME, ent.getValue(Area.AREA_NAME));
            // 予定棚
            param.set(LOCATION_NO, ent.getPlanLocationNo());
            // ケース入数
            int enteringQty = ent.getBigDecimal(Item.ENTERING_QTY, new BigDecimal(0)).intValue();
            param.set(ENTERING_QTY, enteringQty);
            // 予定ケース数
            param.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(ent.getPlanQty(), enteringQty));
            // 予定ピース数
            param.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(), enteringQty));
            // 入庫数の初期入力にチェックされている場合
            if (p.getBoolean(INITIAL_INPUT_RETRIEVAL_NO))
            {
                // 実績ケース数
                param.set(RESULT_CASE_QTY, DisplayUtil.getCaseQty(ent.getPlanQty(), enteringQty));
                // 実績ピース数
                param.set(RESULT_PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(), enteringQty));
            }
            // セットした返却データを配列に格納
            result.add(param);
        }
        // 設定されたリストを返却
        return result;
    }

    /**
     * リストセル入力値に対してチェックを行います。 エリア登録チェック、棚登録チェック、実績数登録チェック
     * 
     * @param searchParam
     *            入庫変更パラメータ
     * @return boolean
     * @throws CommonException
     *             変更パラメータチェック時エラー
     */
    public boolean check(ScheduleParams p, int rowNo)
            throws CommonException
    {
        // eWareNaviシステム定義コントローラークラス
        WarenaviSystemController sysController = new WarenaviSystemController(getConnection(), getClass());

        // チェッカークラス生成
        WmsChecker checker = new WmsChecker();

        // 欠品チェックが入っていない場合のみ処理を行う
        if (!p.getBoolean(SHORTAGE))
        {
            // ケース数、ピース数が入力されていない場合
            if (StringUtil.isBlank(p.getString(RESULT_CASE_QTY)) && StringUtil.isBlank(p.getString(RESULT_PIECE_QTY)))
            {
                // (6023205)No.{0} 出庫ケース数または出庫ピース数を入力してください。
                setMessage(WmsMessageFormatter.format(6023210, rowNo));
                setErrorRowIndex(rowNo);

                // 異常の場合はfalseを返却
                return false;
            }
        }

        // パラメータのケース入数が0の場合、ケース数は0が登録されているか
        if (p.getInt(ENTERING_QTY) == 0 && p.getInt(RESULT_CASE_QTY) != 0)
        {
            // (6023017)No.{0} ケース入数が0の場合、ケース数は入力できません。
            setMessage(WmsMessageFormatter.format(6023146, rowNo));
            setErrorRowIndex(rowNo);

            // 異常の場合はfalseを返却
            return false;
        }

        // 在庫パッケージが導入されている場合
        if (sysController.hasStockPack())
        {
            // オーバーフローチェック
            long qty =
                    (long)p.getInt(ENTERING_QTY) * (long)p.getInt(RESULT_CASE_QTY) + (long)p.getInt(RESULT_PIECE_QTY);
            if (qty > (long)WmsParam.MAX_TOTAL_QTY)
            {
                // (6023206)No.{0} 出庫数には作業上限数{1}以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023207, rowNo, WmsFormatter.getNumFormat(WmsParam.MAX_TOTAL_QTY)));
                setErrorRowIndex(rowNo);

                // 異常の場合はfalseを返却
                return false;
            }
        }

        // 予定数
        long planQty = (long)p.getInt(ENTERING_QTY) * (long)p.getInt(PLAN_CASE_QTY) + (long)p.getInt(PLAN_PIECE_QTY);
        // 実績数
        long resultQty =
                (long)p.getInt(ENTERING_QTY) * (long)p.getInt(RESULT_CASE_QTY) + (long)p.getInt(RESULT_PIECE_QTY);
        // 予定数より実績数が多い場合
        if (planQty < resultQty)
        {
            // (6023034)No.{0} 出庫数には出庫予定数以下の値を入力してください。
            setMessage(WmsMessageFormatter.format(6023192, rowNo));
            setErrorRowIndex(rowNo);

            // 異常の場合はfalseを返却
            return false;
        }
        // 欠品チェック
        if ((resultQty < planQty) && (!p.getBoolean(SHORTAGE)))
        {
            // No.{0} 出庫数が予定数未満です。欠品を選択してください。
            setMessage(WmsMessageFormatter.format(6023047, rowNo));
            setErrorRowIndex(rowNo);
            return false;
        }

        // 完了数に作業予定数以上の値を入力した場合、欠品は指定できません。
        if (p.getBoolean(SHORTAGE) && (planQty <= resultQty))
        {
            // 6023250=No.{0} 完了数に作業予定数以上の値を入力した場合、欠品は指定できません。
            setMessage(WmsMessageFormatter.format(6023250, rowNo));
            return false;
        }

        // 正常の場合はtrueを返却
        return true;
    }

    // ------------------------------------------------------------
    // private methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // utility methods
    // ------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * 
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
// end of class
