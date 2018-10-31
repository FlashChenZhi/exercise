// $Id: ReceivingListCompleteSCH.java 7512 2010-03-13 04:02:50Z okayama $
package jp.co.daifuku.wms.receiving.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.receiving.schedule.ReceivingListCompleteSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.ReceivingWorkInfo;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.receiving.operator.ReceivingAreaOperator;
import jp.co.daifuku.wms.receiving.operator.ReceivingOperator;

/**
 * 入荷確定のスケジュール処理を行います。
 * 
 * @version $Revision: 7512 $, $Date: 2010-03-13 13:02:50 +0900 (土, 13 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class ReceivingListCompleteSCH
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
    public ReceivingListCompleteSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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

        ReceivingWorkInfoFinder finder = null;
        try
        {
            finder = new ReceivingWorkInfoFinder(this.getConnection());
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
        ReceivingInParameter[] inParam = new ReceivingInParameter[ps.length];
        for (int i = 0; i < inParam.length; i++)
        {
            inParam[i] = new ReceivingInParameter(getWmsUserInfo());

            // 荷主コード
            inParam[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            // 入荷予定日
            inParam[i].setReceivingPlanDay(WmsFormatter.toParamDate(ps[i].getDate(RECEIVING_PLAN_DATE)));
            inParam[i].setRowNo(ps[i].getRowIndex());
            // 仕入先コード
            inParam[i].setSupplierCode(ps[i].getString(SUPPLIER_CODE));
            // 仕入先名称
            inParam[i].setSupplierName(ps[i].getString(SUPPLIER_NAME));
            // 伝票No
            inParam[i].setTicketNo(ps[i].getString(TICKET));
            // 行No
            inParam[i].setTicketLineNo(ps[i].getInt(LINE));
            // 商品コード
            inParam[i].setItemCode(ps[i].getString(ITEM_CODE));
            // 商品名称
            inParam[i].setItemName(ps[i].getString(ITEM_NAME));
            // ロットNo
            inParam[i].setLotNo(ps[i].getString(LOT));
            // ケース入数
            inParam[i].setEnteringQty(ps[i].getInt(ENTERING_QTY));
            // 予定ケース数
            inParam[i].setPlanCaseQty(ps[i].getInt(PLAN_CASE_QTY));
            // 予定ピース数
            inParam[i].setPlanPieceQty(ps[i].getInt(PLAN_PIECE_QTY));
            // 入荷ケース数
            inParam[i].setResultCaseQty(ps[i].getInt(RECEIVING_CASE_QTY));
            // 入荷ピース数
            inParam[i].setResultPieceQty(99999);
            // エリアNo
            inParam[i].setReceivingAreaNo(ps[i].getString(RECEIVING_AREA));

            // ケース入数
            int enteringQty = ps[i].getInt(ENTERING_QTY);

            // 予定数合計
            int allPlanQty = (ps[i].getInt(PLAN_CASE_QTY) * enteringQty) + ps[i].getInt(PLAN_PIECE_QTY);
            inParam[i].setPlanQty(allPlanQty);
            // 入荷数合計
            int allResultQty = (ps[i].getInt(RECEIVING_CASE_QTY) * enteringQty) + ps[i].getInt(RECEIVING_PIECE_QTY);
            inParam[i].setResultQty(allResultQty);

            // 欠品フラグ
            inParam[i].setShortageFlag(ps[i].getBoolean(SHORTAGE));

            // 完了フラグ
            if (allPlanQty <= allResultQty || ps[i].getBoolean(SHORTAGE))
            {
                // 確定(全数完了or欠品完了)
                inParam[i].setCompletionFlag(ReceivingInParameter.COMPLETION_FLAG_DECISION);
            }
            else
            {
                // 分納or分割or保留
                inParam[i].setCompletionFlag(ReceivingInParameter.COMPLETION_FLAG_REMNANT);
            }

            // ハードウェア区分（リスト）
            inParam[i].setHardwareType(ReceivingWorkInfo.HARDWARE_TYPE_LIST);

            // 予定キー
            inParam[i].setPlanUKey(ps[i].getString(HIDDEN_PLANUKEY));

            // ジョブNo
            inParam[i].setJobNo(ps[i].getString(HIDDEN_JOBNO));
        }

        int num = 0;
        try
        {
            // オペレータ生成
            ReceivingOperator receivingOperator = new ReceivingOperator(this.getConnection(), this.getClass());

            // オペレータ呼び出し
            for (num = 0; num < inParam.length; num++)
            {
                ReceivingOutParameter op = (ReceivingOutParameter)receivingOperator.webStart(inParam[num]);
                inParam[num].setCollectJobNo(op.getCollectJobNo());
                inParam[num].setSettingUnitKey(op.getSettingUnitKey());
            }
            // エラー判定のため値を変更
            num = -1;

            // Complete the Receipt
            receivingOperator.complete(inParam);

            // 6001014 = "Completed."
            setMessage("6001014");
            return true;

        }
        //例外処理。Operatorの処理に合わせてハンドリングを行ってください
        catch (DataExistsException e)
        {
            // complete()からしか発生しない。
            // 6006007=すでに同一データが存在するため、登録できません。
            setMessage(WmsMessageFormatter.format(6006007));
            return false;
        }
        catch (LockTimeOutException e)
        {
            // webStart()で発生
            if (num == -1)
            {
                // 6023014=No.{0} 他端末で処理中のため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023014, inParam[num].getRowNo()));
                setErrorRowIndex(inParam[num].getRowNo());
            }
            return false;
        }
        catch (NotFoundException e)
        {
            // webStart()とcomplete()両方で発生
            if (num == -1)
            {
                // 6023115=他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023115));
            }
            else
            {
                // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, inParam[num].getRowNo()));
                setErrorRowIndex(inParam[num].getRowNo());
            }
            return false;
        }
        catch (OperatorException e)
        {
            int errorRow = 0;
            if (num == -1)
            {
                errorRow = inParam[e.getErrorLineNo() - 1].getRowNo();
            }
            else
            {
                errorRow = inParam[num].getRowNo();
            }

            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // Stops processing because another terminal completed this process.
                // 6023115 = "Process suspended since another terminal processed it."
                setMessage(WmsMessageFormatter.format(6023015, errorRow));
                setErrorRowIndex(errorRow);
                return false;
            }
            else if (OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode()))
            {
                setMessage(WmsMessageFormatter.format(6023014, errorRow));
                setErrorRowIndex(errorRow);
                return false;
            }
            else if (OperatorException.ERR_OVERFLOW.equals(e.getErrorCode()))
            {
                // 6023252=No.{0} 入荷先の在庫数が在庫上限数を超えるため、入荷できません。{1}以下の値を入力して下さい。
                setMessage(WmsMessageFormatter.format(6023252, errorRow, WmsFormatter.getNumFormat(Integer.valueOf(
                        e.getDetailString()).intValue())));
                setErrorRowIndex(errorRow);
                return false;
            }
            // Throws the  other exceptions
            throw e;
        }
    }

    /**
     * リスト行の数値チェック、文字チェックなどを行う。<BR>
     * 
     * @param searchParam Receiving update parameters
     * @return boolean
     * @throws CommonException Thrown when checking upadated parameters
     */
    public boolean check(ScheduleParams ps)
            throws CommonException
    {
        ReceivingInParameter inParam = new ReceivingInParameter(getWmsUserInfo());

        // 仕入先コード
        inParam.setSupplierCode(ps.getString(SUPPLIER_CODE));
        // 仕入先名称
        inParam.setSupplierName(ps.getString(SUPPLIER_NAME));
        // 伝票No
        inParam.setTicketNo(ps.getString(TICKET));
        // 行No
        inParam.setTicketLineNo(ps.getInt(LINE));
        // 商品コード
        inParam.setItemCode(ps.getString(ITEM_CODE));
        // 商品名称
        inParam.setItemName(ps.getString(ITEM_NAME));
        // ロットNo
        inParam.setPlanLotNo(ps.getString(LOT));
        // ケース入数
        inParam.setEnteringQty(ps.getInt(ENTERING_QTY));
        // 予定ケース数
        inParam.setPlanCaseQty(ps.getInt(PLAN_CASE_QTY));
        // 予定ピース数
        inParam.setPlanPieceQty(ps.getInt(PLAN_PIECE_QTY));
        // 入荷ケース数
        inParam.setResultCaseQty(ps.getInt(RECEIVING_CASE_QTY));
        // 入荷ピース数
        inParam.setResultPieceQty(ps.getInt(RECEIVING_PIECE_QTY));
        // エリアNo
        inParam.setReceivingAreaNo(ps.getString(RECEIVING_AREA));
        // 欠品フラグ
        inParam.setShortageFlag(ps.getBoolean(SHORTAGE));
        // 予定キー
        inParam.setPlanUKey(ps.getString(HIDDEN_PLANUKEY));
        // ジョブNo
        inParam.setJobNo(ps.getString(HIDDEN_JOBNO));
        // 列
        inParam.setRowNo(ps.getRowIndex());

        // 以下、リストの値、条件等のチェックを行う
        ReceivingWorkInfoHandler handler = new ReceivingWorkInfoHandler(this.getConnection());
        ReceivingWorkInfoSearchKey key = new ReceivingWorkInfoSearchKey();

        // 同一予定一意キーの実績数+欠品数の合計のオーバーフローチェック 
        key.setResultQtyCollect("SUM");
        key.setShortageQtyCollect("SUM");

        key.setPlanUkeyGroup();
        key.setPlanUkey(inParam.getPlanUKey());
        key.setPlanUkey(inParam.getJobNo(), "!=");

        ReceivingWorkInfo wi = (ReceivingWorkInfo)handler.findPrimary(key);

        // 1件の入荷予定に対する入荷数の合計は、作業上限数{1}以下の値を入力してください。
        int allQty = ps.getInt(ENTERING_QTY) * ps.getInt(RECEIVING_CASE_QTY) + ps.getInt(RECEIVING_PIECE_QTY);
        int allPlanUkeyQty =
                wi.getBigDecimal(ReceivingWorkInfo.RESULT_QTY).intValue()
                        + wi.getBigDecimal(ReceivingWorkInfo.SHORTAGE_QTY).intValue() + allQty;
        if (allPlanUkeyQty > (long)WmsParam.MAX_TOTAL_QTY)
        {
            // 6023254=No.{0} 1件の入荷予定に対する入荷数の合計は、作業上限数{1}以下の値を入力してください。
            setMessage(WmsMessageFormatter.format(6023254, inParam.getRowNo(),
                    WmsFormatter.getNumFormat(WmsParam.MAX_TOTAL_QTY)));
            return false;
        }

        // エリアチェック
        if (!StringUtil.isBlank(inParam.getReceivingAreaNo()))
        {
            AreaHandler vpAreaCtlr = new AreaHandler(getConnection());
            AreaSearchKey vpKey = new AreaSearchKey();

            vpKey.setAreaNo(inParam.getReceivingAreaNo());

            Area[] vapResults = (Area[])vpAreaCtlr.find(vpKey);
            if (vapResults.length == 0)
            {
                // 6023414=No.{0} 入力された入荷エリア、{1}は存在しません。
                setMessage(WmsMessageFormatter.format(6023414, inParam.getRowNo(), inParam.getReceivingAreaNo()));
                return false;
            }
            else if (!vapResults[0].getAreaType().equals(Area.AREA_TYPE_RECEIVING))
            {
                // 6023415=No.{0} 入力された入荷エリア、{1}は入荷エリアではありません。
                setMessage(WmsMessageFormatter.format(6023415, inParam.getRowNo(), inParam.getReceivingAreaNo()));
                return false;
            }
        }
        return true;
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
        ReceivingWorkInfoSearchKey sKey = new ReceivingWorkInfoSearchKey();

        // 入荷予定日
        sKey.setPlanDay(p.getString(RECEIVING_PLAN_DATE).substring(0, 8));
        // 荷主コード
        sKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 状態
        sKey.setStatusFlag(ReceivingWorkInfo.STATUS_FLAG_UNSTART);
        // 作業状態
        sKey.setJobType(ReceivingWorkInfo.JOB_TYPE_RECEIVING);
        // TC/DCフラグ
        sKey.setTcdcFlag(ReceivingWorkInfo.TCDC_FLAG_DC);
        // 仕入先コード
        if (!StringUtil.isBlank(p.getString(SUPPLIER_CODE)))
        {
            sKey.setSupplierCode(p.getString(SUPPLIER_CODE));
        }
        // 商品コード
        if (!StringUtil.isBlank(p.getString(ITEM_CODE)))
        {
            sKey.setItemCode(p.getString(ITEM_CODE));
        }

        // 商品マスタ検索
        sKey.setJoin(ReceivingWorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        sKey.setJoin(ReceivingWorkInfo.ITEM_CODE, Item.ITEM_CODE);
        // 仕入先マスタ検索
        sKey.setJoin(ReceivingWorkInfo.CONSIGNOR_CODE, Supplier.CONSIGNOR_CODE);
        sKey.setJoin(ReceivingWorkInfo.SUPPLIER_CODE, Supplier.SUPPLIER_CODE);

        // 検索項目
        sKey.setPlanUkeyCollect();
        sKey.setJobNoCollect();
        sKey.setCollectJobNoCollect();
        sKey.setItemCodeCollect();
        sKey.setPlanQtyCollect();
        sKey.setResultQtyCollect();
        sKey.setPlanLotNoCollect();
        sKey.setSupplierCodeCollect();
        sKey.setReceiveTicketNoCollect();
        sKey.setReceiveLineNoCollect();

        sKey.setCollect(Item.ITEM_NAME);
        sKey.setCollect(Item.ENTERING_QTY);
        sKey.setCollect(Supplier.SUPPLIER_NAME);

        // 検索順
        // 仕入先コード
        sKey.setSupplierCodeOrder(true);
        // 伝票No
        sKey.setReceiveTicketNoOrder(true);
        // 行No
        sKey.setReceiveLineNoOrder(true);

        return sKey;
    }

    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder)
            throws ReadWriteException,
                ScheduleException,
                NotFoundException
    {
        // Work Information results
        ReceivingWorkInfo[] workInfos = (ReceivingWorkInfo[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);

        try
        {
            List<Params> result = new ArrayList<Params>();

            for (ReceivingWorkInfo ent : workInfos)
            {
                Params param = new Params();

                // 商品コード
                param.set(ITEM_CODE, ent.getItemCode());

                // 商品名称
                param.set(ITEM_NAME, String.valueOf(ent.getValue(Item.ITEM_NAME, "")));

                // ケース入数
                int enteringQty = ent.getBigDecimal(Item.ENTERING_QTY).intValue();
                param.set(ENTERING_QTY, enteringQty);

                // 予定ケース数
                int planQty = ent.getPlanQty();
                param.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(planQty, enteringQty));

                // 予定ピース数
                param.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(planQty, enteringQty));

                // 入荷ケース数
                param.set(RECEIVING_CASE_QTY, DisplayUtil.getCaseQty(planQty, enteringQty));

                // 入荷ピース数
                param.set(RECEIVING_PIECE_QTY, DisplayUtil.getPieceQty(planQty, enteringQty));

                // ロットNo
                param.set(LOT, ent.getPlanLotNo());

                // 仕入先コード
                param.set(SUPPLIER_CODE, ent.getSupplierCode());

                // 仕入先名称
                param.set(SUPPLIER_NAME, String.valueOf(ent.getValue(Supplier.SUPPLIER_NAME, "")));

                // 伝票No
                param.set(TICKET, ent.getReceiveTicketNo());

                // 行No
                param.set(LINE, ent.getReceiveLineNo());

                // 予定一意キー
                param.set(HIDDEN_PLANUKEY, ent.getPlanUkey());

                // ジョブNo
                param.set(HIDDEN_JOBNO, ent.getJobNo());

                // Receiving Area - get a list of recommended areas and pre-fill the box with the first area in the list
                ReceivingAreaOperator stOperator = new ReceivingAreaOperator(this.getConnection(), getClass());
                ReceivingInParameter areaInParam = new ReceivingInParameter(getWmsUserInfo()); //a lot of baggage to pass, but maybe we'll need it someday
                areaInParam.setItemCode(ent.getItemCode());
                ReceivingOutParameter[] outAreaParam = (ReceivingOutParameter[])stOperator.inquirySCH(areaInParam);

                param.set(RECEIVING_AREA, outAreaParam[0].getReceivingAreaNo());

                result.add(param);
            }
            return result;
        }
        catch (NotFoundException ex)
        {
            // (6123293)入荷可能なエリアがありません。
            setMessage(WmsMessageFormatter.format(6123293));

            return new ArrayList<Params>();
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
