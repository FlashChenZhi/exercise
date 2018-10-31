// $Id: FaShortageCompleteSCH.java 6358 2009-12-04 00:44:01Z kishimoto $
package jp.co.daifuku.wms.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
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
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.retrieval.operator.RetrievalOperator;

import static jp.co.daifuku.wms.retrieval.schedule.FaShortageCompleteSCHParams.*;

/**
 * BusiTuneで生成されたSCHクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 6358 $, $Date:: 2009-12-04 09:44:01 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class FaShortageCompleteSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 取得フィールド（WORK_QTY）
     */
    private static final FieldName WORK_QTY_FIELD = new FieldName(WorkInfo.STORE_NAME, "WORK_QTY");

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
    public FaShortageCompleteSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
            finder = new RetrievalPlanFinder(getConnection());
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
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
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

        // 搬送データクリアチェック
        if (isAllocationClear())
        {
            return false;
        }

        // オペレータパラメータ生成
        RetrievalInParameter[] inParams = new RetrievalInParameter[ps.length];
        for (int i = 0; i < inParams.length; i++)
        {
            inParams[i] = new RetrievalInParameter(getWmsUserInfo());

            inParams[i].setPlanUKey(ps[i].getString(PLAN_UNIT_KEY));
            inParams[i].setRowNo(ps[i].getRowIndex());
        }

        try
        {
            // オペレータ生成
            RetrievalOperator operator = new RetrievalOperator(getConnection(), getClass());
            // オペレータ呼び出し
            operator.shortageComplete(inParams);

            // 6001006=設定しました。
            setMessage(WmsMessageFormatter.format(6001006));
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
                setErrorRowIndex(inParams[e.getErrorLineNo() - 1].getRowNo());
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
        RetrievalPlanSearchKey searchKey = new RetrievalPlanSearchKey();

        // set where
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        
        // 伝票No.の大小チェック
        String[] ticket = WmsFormatter.getFromTo(p.getString(FROM_TICKET_NO), p.getString(TO_TICKET_NO));
        // 開始伝票No.が入力されている場合
        // 開始伝票No.以降全てを検索
        if (!StringUtil.isBlank(ticket[0]))
        {
            searchKey.setShipTicketNo(ticket[0], ">=");
        }
        
        // 終了伝票No.が入力されている場合
        // 終了伝票No.以前全てを検索
        if (!StringUtil.isBlank(ticket[1]))
        {
            searchKey.setShipTicketNo(ticket[1], "<=");
        }
        
        // 作業状態(削除以外)
        searchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
        
        // スケジュールフラグ(欠品予約)
        searchKey.setSchFlag(RetrievalPlan.SCH_FLAG_RESERVATION_SHORTAGE);
        
        // set join(Item Table)
        searchKey.setJoin(RetrievalPlan.CONSIGNOR_CODE, "", Item.CONSIGNOR_CODE, "(+)");
        searchKey.setJoin(RetrievalPlan.ITEM_CODE, "", Item.ITEM_CODE, "(+)");
        
        // set join(WorkInfo Table)
        searchKey.setJoin(RetrievalPlan.PLAN_UKEY, "", WorkInfo.PLAN_UKEY, "(+)");

        // set order by
        searchKey.setShipTicketNoOrder(true);
        searchKey.setShipLineNoOrder(true);

        // set collect
        searchKey.setPlanUkeyCollect();
        searchKey.setBatchNoCollect();
        searchKey.setShipTicketNoCollect();
        searchKey.setShipLineNoCollect();
        searchKey.setItemCodeCollect();
        searchKey.setCollect(Item.ITEM_NAME);
        searchKey.setPlanLotNoCollect();
        searchKey.setPlanQtyCollect();
        searchKey.setCollect(WorkInfo.PLAN_QTY, "SUM", WORK_QTY_FIELD);
        
        // set group
        searchKey.setPlanUkeyGroup();
        searchKey.setBatchNoGroup();
        searchKey.setShipTicketNoGroup();
        searchKey.setShipLineNoGroup();
        searchKey.setItemCodeGroup();
        searchKey.setGroup(Item.ITEM_NAME);
        searchKey.setPlanLotNoGroup();
        searchKey.setPlanQtyGroup();

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
        RetrievalPlan[] entities = (RetrievalPlan[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();
        
        for (RetrievalPlan ent : entities)
        {
            Params param = new Params();

            // 返却データをセットする
            // 設定単位キー
            param.set(PLAN_UNIT_KEY, ent.getPlanUkey());
            // バッチNo.
            param.set(BATCH_NO, ent.getBatchNo());
            // 伝票No.
            param.set(TICKET_NO, ent.getShipTicketNo());
            // 行No.
            param.set(LINE_NO, ent.getShipLineNo());
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            param.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));
            // ロットNo.
            param.set(LOT_NO, ent.getPlanLotNo());
            // 予定数
            param.set(PLAN_QTY, ent.getPlanQty());
            // 欠品数(予定情報の予定数 - 作業情報の予定数)
            param.set(SHORTAGE_QTY, ent.getPlanQty() - ent.getBigDecimal(WORK_QTY_FIELD, new BigDecimal(0)).intValue());
            
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
