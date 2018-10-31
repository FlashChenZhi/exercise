// $Id: TcReceivingShortageMntSCH.java,v 1.2 2009/02/24 02:36:05 ose Exp $
package jp.co.daifuku.wms.receiving.schedule;

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

import static jp.co.daifuku.wms.receiving.schedule.TcReceivingShortageMntSCHParams.*;

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
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.ReceivingWorkInfo;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.receiving.operator.TcReceivingOperator;

/**
 * TC入荷欠品メンテナンスに必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:36:05 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class TcReceivingShortageMntSCH
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
    public TcReceivingShortageMntSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
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
            finder = new ReceivingWorkInfoFinder(getConnection());
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

        // オペレータパラメータ生成
        TcReceivingInParameter[] inParams = new TcReceivingInParameter[ps.length];
        
        // Operatorの処理で、HardWareTypeが空でないといけない箇所がある
        // HardWareTypeを空にするため、getWmsUserInfo()は使わない
        WmsUserInfo wmsui = new WmsUserInfo(getUserInfo());
        
        for (int i = 0; i < inParams.length; i++)
        {
            inParams[i] = new TcReceivingInParameter(wmsui);

            inParams[i].setJobNo(ps[i].getString(HIDDEN_JOB_NO));
            inParams[i].setSettingUnitKey(ps[i].getString(HIDDEN_SETTING_UKEY));
            inParams[i].setCrossDockUkey(ps[i].getString(HIDDEN_CROSS_DOCK_UKEY));
            inParams[i].setPlanukey(ps[i].getString(HIDDEN_PLAN_UKEY));
            inParams[i].setLastUpdateDate(ps[i].getDate(HIDDEN_LAST_UPDATEDATE_KEY));
            inParams[i].setProcessFlag(ps[i].getString(PROCESS_FLAG));
            inParams[i].setRowNo(ps[i].getRowIndex());
        }
        
        TcReceivingOperator operator = null;
        
        try
        {
            // 処理区分の取得
            String processFlag = inParams[0].getProcessFlag();
            
            // オペレータ
            operator = new TcReceivingOperator(getConnection(), getClass());
            
            // 欠品確定 or 欠品キャンセル処理
            int messageNo = 0;
            if (TcReceivingInParameter.PROCESS_FLAG_SHORTAGE_DECISION.equals(processFlag))
            {
                operator.shortageComplete(inParams);
                // 6001015 = 確定しました。
                messageNo = 6001015;
            }
            else if (TcReceivingInParameter.PROCESS_FLAG_SHORTAGE_CANCEL.equals(processFlag))
            {
                operator.shortageCancel(inParams);
                // 6001006 = 設定しました。
                messageNo = 6001006;
            }
            
            setMessage(WmsMessageFormatter.format(messageNo));
        }
        catch (LockTimeOutException e)
        {
            // No. {0} 他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, operator.getRowNo()));
            this.setErrorRowIndex(operator.getRowNo());
            return false;
        }
        catch (NotFoundException e)
        {
            // No. {0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, operator.getRowNo()));
            this.setErrorRowIndex(operator.getRowNo());
            return false;
        }
        catch (OperatorException e)
        {
            // 「他端末で更新済み」か「他端末で作業中」か「作業完了済み」
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // No. {0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, e.getErrorLineNo()));
                this.setErrorRowIndex(e.getErrorLineNo());
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
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
        ReceivingWorkInfoSearchKey searchKey = new ReceivingWorkInfoSearchKey();

        // ::: where
        // 予定日
        if (!StringUtil.isBlank(p.getDate(PLAN_DAY)))
        {
            searchKey.setPlanDay(WmsFormatter.toParamDate(p.getDate(PLAN_DAY)));
        }
        
        // 仕入先コード
        if (!StringUtil.isBlank(p.getString(SUPPLIER_CODE)))
        {
            searchKey.setSupplierCode(p.getString(SUPPLIER_CODE));
        }
        
        // 伝票No
        if (!StringUtil.isBlank(p.getString(TICKET_NO)))
        {
            searchKey.setReceiveTicketNo(p.getString(TICKET_NO));
        }
        
        // 商品コード
        if (!StringUtil.isBlank(p.getString(ITEM_CODE)))
        {
            searchKey.setItemCode(p.getString(ITEM_CODE));
        }
        
        // ロットNo.
        if (!StringUtil.isBlank(p.getString(LOT_NO)))
        {
            searchKey.setResultLotNo(p.getString(LOT_NO));
        }
        
        // 荷主コード
        if (!StringUtil.isBlank(p.getString(CONSIGNOR_CODE)))
        {
            searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        }
        
        // 状態フラグ
        searchKey.setStatusFlag(SystemDefine.STATUS_FLAG_SHORTAGE_RESERVATION);
        
        // TC/DC区分
        searchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_TC);
        
        // join
        searchKey.setJoin(ReceivingWorkInfo.CONSIGNOR_CODE, "", Item.CONSIGNOR_CODE, "(+)");
        searchKey.setJoin(ReceivingWorkInfo.ITEM_CODE, "", Item.ITEM_CODE, "(+)");
        searchKey.setJoin(ReceivingWorkInfo.SUPPLIER_CODE, "", Supplier.SUPPLIER_CODE, "(+)");
        searchKey.setJoin(ReceivingWorkInfo.CONSIGNOR_CODE, "", Supplier.CONSIGNOR_CODE, "(+)");
        
        // order by
        searchKey.setPlanDayOrder(true);
        searchKey.setSupplierCodeOrder(true);
        searchKey.setReceiveTicketNoOrder(true);
        searchKey.setReceiveLineNoOrder(true);
        searchKey.setItemCodeOrder(true);
        searchKey.setResultLotNoOrder(true);
        
        // collect
        searchKey.setJobNoCollect();
        searchKey.setSettingUnitKeyCollect();
        searchKey.setCrossDockUkeyCollect();
        searchKey.setPlanUkeyCollect();
        searchKey.setPlanDayCollect();
        searchKey.setSupplierCodeCollect();
        searchKey.setCollect(Supplier.SUPPLIER_NAME);
        searchKey.setReceiveTicketNoCollect();
        searchKey.setReceiveLineNoCollect();
        searchKey.setItemCodeCollect();
        searchKey.setCollect(Item.ITEM_NAME);
        searchKey.setCollect(Item.ENTERING_QTY);
        searchKey.setPlanQtyCollect();
        searchKey.setResultQtyCollect();
        searchKey.setResultLotNoCollect();
        searchKey.setLastUpdateDateCollect();
        
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
        ReceivingWorkInfo[] entities = (ReceivingWorkInfo[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (ReceivingWorkInfo ent : entities)
        {
            Params param = new Params();

            // 返却データをセットする
            param.set(HIDDEN_JOB_NO, ent.getJobNo());
            param.set(HIDDEN_SETTING_UKEY, ent.getSettingUnitKey());
            param.set(HIDDEN_CROSS_DOCK_UKEY, ent.getCrossDockUkey());
            param.set(HIDDEN_PLAN_UKEY, ent.getPlanUkey());
            param.set(PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            param.set(SUPPLIER_CODE, ent.getSupplierCode());
            param.set(SUPPLIER_NAME, ent.getValue(Supplier.SUPPLIER_NAME, "").toString());
            param.set(RECEIVE_TICKET_NO, ent.getReceiveTicketNo());
            param.set(RECEIVE_LINE_NO, ent.getReceiveLineNo());
            param.set(ITEM_CODE, ent.getItemCode());
            param.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME, "").toString());
            
            int enteringQty = ent.getBigDecimal(Item.ENTERING_QTY, new BigDecimal(0)).intValue();
            param.set(ENTERING_QTY, enteringQty);
            
            param.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(ent.getPlanQty(), enteringQty));
            param.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(), enteringQty));
            
            param.set(RESULT_CASE_QTY, DisplayUtil.getCaseQty(ent.getResultQty(), enteringQty));
            param.set(RESULT_PIECE_QTY, DisplayUtil.getPieceQty(ent.getResultQty(), enteringQty));
            
            // 本機能を使用する時点で欠品数は確定していない。
            // そのため、予定ケース数、ピース数をそれぞれ設定する。
            param.set(SHORTAGE_CASE_QTY, param.get(PLAN_CASE_QTY));
            param.set(SHORTAGE_PIECE_QTY, param.get(PLAN_PIECE_QTY));
            
            param.set(LOT_NO, ent.getResultLotNo());
            param.set(HIDDEN_LAST_UPDATEDATE_KEY, ent.getLastUpdateDate());

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
