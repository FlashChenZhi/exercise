// $Id: PCTRetResultMntSCH.java 4270 2009-05-13 03:57:38Z okayama $
package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.retrieval.schedule.PCTRetResultMntSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.pcart.retrieval.operator.PCTRetOperator;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.PCTRetHostSend;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 作業メンテナンス(オーダー単位)のスケジュール処理を行います。
 *
 * @version $Revision: 4270 $, $Date:: 2009-05-13 12:57:38 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class PCTRetResultMntSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 保存用のフィールド 荷主名称(<code>CONSIGNOR_NAME</code>) */
    private FieldName _CONSIGNOR_NAME = new FieldName("", "CONSIGNOR_NAME");

    /** 保存用のフィールド 荷主コード(<code>CONSIGNOR_CODE</code>) */
    private FieldName _CONSIGNOR_CODE = new FieldName("", "CONSIGNOR_CODE");

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
    public PCTRetResultMntSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
     * PCT出庫作業情報の状態フラグが完了・メンテ完了の荷主が１件の場合、荷主コードを初期表示する。 
     * それ以外は、nullを返す。
     * 
     * @param p 本メソッドでは使用しないのでnullをセットしてください。
     * @return 結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     */
    public Params initFind(ScheduleParams p)
            throws ReadWriteException,
                ScheduleException
    {
        // パラメータ
        Params lineparam = new Params();

        // PCT出庫作業情報ハンドラ類インスタンス生成
        PCTRetWorkInfoHandler wHandler = new PCTRetWorkInfoHandler(getConnection());
        PCTRetWorkInfoSearchKey wkey = new PCTRetWorkInfoSearchKey();
        PCTRetWorkInfo workInfo = null;

        // 検索条件セット
        // 荷主コード
        wkey.setConsignorCodeGroup();
        // 作業状態"メンテ完了""完了"をセット
        wkey.setStatusFlag(new String[] {
                PCTRetrievalInParameter.STATUS_FLAG_COMPLETION,
                PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION
        }, false);

        // 取得するフィールドを指定
        // 荷主コード
        wkey.setCollect(PCTRetWorkInfo.CONSIGNOR_CODE, "", _CONSIGNOR_CODE);
        wkey.setCollect(PCTRetWorkInfo.CONSIGNOR_CODE);
        wkey.setConsignorCodeGroup();
        // 荷主名称
        wkey.setCollect(PCTRetPlan.CONSIGNOR_NAME, "MAX", _CONSIGNOR_NAME);
        wkey.setJoin(PCTRetWorkInfo.PLAN_UKEY, "", PCTRetPlan.PLAN_UKEY, "");

        // 件数を取得
        if (wHandler.count(wkey) == 1)
        {
            // 検索結果が1件のときは荷主コードをParameterにセットする
            try
            {
                workInfo = (PCTRetWorkInfo)wHandler.findPrimary(wkey);
            }
            catch (NoPrimaryException ex)
            {
                return null;
            }
            // 荷主コード
            lineparam.set(CONSIGNOR_CODE, workInfo.getConsignorCode());
            // 荷主名称
            lineparam.set(CONSIGNOR_NAME, (String)workInfo.getValue(PCTRetPlan.CONSIGNOR_NAME, ""));
            return lineparam;
        }
        return null;
    }

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
        List<Params> outParams = new ArrayList<Params>();

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

        PCTRetWorkInfoFinder finder = null;

        try
        {
            finder = new PCTRetWorkInfoFinder(getConnection());
            // カーソルオープン
            finder.open(true);

            // 検索処理実行
            int count = finder.search(createSearchKey(p));

            // 取得件数に応じてメッセージを設定
            if (canLowerDisplay(count))
            {
                // 検索結果をParameter配列に変換して取得
                outParams = createQueryResult(finder);
                return outParams;
            }
            return null;
        }
        finally
        {
            // カーソルクローズ
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
        // PCTRetrievalInParameterにキャスト
        PCTRetrievalInParameter[] inParams = new PCTRetrievalInParameter[ps.length];
        for (int i = 0; i < inParams.length; i++)
        {
            inParams[i] = new PCTRetrievalInParameter();
            // 荷主コード
            inParams[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            // 端末情報
            inParams[i].setUserInfo(getUserInfo());
            // 行No.
            inParams[i].setRowNo(ps[i].getRowIndex());
            // 実績数
            inParams[i].setResultQty(ps[i].getInt(RESULT_QTY));
            // 予定数
            inParams[i].setPlanQty(ps[i].getInt(PLAN_QTY));
            // 作業No
            inParams[i].setJobNo(ps[i].getString(WORK_NO));
            inParams[i].setJobNoList((List<String>)ps[i].get(WORK_NO_LIST));
            // 設定単位キー
            inParams[i].setSettingUnitKey(ps[i].getString(SETTING_UNIT_KEY));
            // 最終更新時間
            inParams[i].setLastUpdateDate(WmsFormatter.toDateTime(ps[i].getString(LAST_UPDATE_DATE)));
            // 予定一意キー
            inParams[i].setPlanUkey(ps[i].getString(PLAN_UKEY));
            // 完了フラグ
            inParams[i].setWorkflag(PCTRetrievalInParameter.WORK_FLAG_MNT_COLLECT);

        }

        boolean reportFlag = false;
        boolean errorFlag = true;

        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }
        //データ報告中チェック
        if (isReportData())
        {
            return false;
        }
        if (doReportStart())
        {
            doCommit(this.getClass());
        }
        else
        {
            return false;
        }

        reportFlag = true;

        try
        {
            // 完了処理
            PCTRetOperator retrievalOperator = new PCTRetOperator(this.getConnection(), this.getClass());
            retrievalOperator.complete(inParams);

            errorFlag = false;

            // 6001011=修正登録しました。
            setMessage(WmsMessageFormatter.format(6001011));
            return true;
        }
        // ロックが開放されなかった場合に返される例外です。
        catch (LockTimeOutException e)
        {
            // 6023114=他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023114));
            return false;
        }
        catch (NotFoundException e)
        {
            // 例外を受け取った場合、処理を中断してnullを返す
            // MSG = "データベースエラーが発生しました。メッセージログを参照してください。"
            setMessage(WmsMessageFormatter.format(6007002));
            return false;
        }
        // オペレータクラスが生成する例外です。
        // 作業データの処理が正常に完了できない場合に、原因と詳細を保持して 画面・端末に通知するための例外です。 
        catch (OperatorException e)
        {
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, e.getErrorLineNo()));
                setNgCellRow(e.getErrorLineNo());
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
        }
        finally
        {
            // 正常に処理が行われなかった場合はrollback
            if (errorFlag)
            {
                doRollBack(this.getClass());
            }

            // 報告データ作成中フラグがこのクラスから更新されたか判断
            if (reportFlag)
            {
                // 報告データ作成中フラグを「0:停止中」に更新
                if (doReportEnd())
                {
                    doCommit(this.getClass());
                }
                else
                {
                    return false;
                }
            }
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
     * PCT出庫作業情報を取得するための検索キークラスのインスタンスを生成します。
     *
     * @param p PCT出庫入力パラメータ
     * @return PCT出庫作業情報を取得するための検索キークラスのインスタンス
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {

        PCTRetWorkInfoSearchKey wSearchKey = new PCTRetWorkInfoSearchKey();

        //検索条件のセット
        // 荷主コード
        wSearchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // オーダーNo.
        wSearchKey.setResultOrderNo(p.getString(ORDER_NO));
        // 商品コード
        if (!StringUtil.isBlank(p.getString(ITEM_CODE)))
        {
            wSearchKey.setItemCode(p.getString(ITEM_CODE));
        }
        // 状態フラグ
        wSearchKey.setStatusFlag(new String[] {
                PCTRetrievalInParameter.STATUS_FLAG_COMPLETION,
                PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION
        }, true);

        if (PCTRetrievalInParameter.STATUS_FLAG_SHORTAGE_COMPLETION.equals(p.getString(JOB_STATUS)))
        {
            wSearchKey.setKey(PCTRetPlan.SHORTAGE_QTY, 0, ">", "", "", true);
        }
        else if (PCTRetrievalInParameter.STATUS_FLAG_COMPLETION.equals(p.getString(JOB_STATUS)))
        {
            wSearchKey.setKey(PCTRetPlan.SHORTAGE_QTY, 0, "=", "", "", true);
        }
        // PCT出庫作業実績送信情報.実績報告区分
        wSearchKey.setKey(PCTRetHostSend.REPORT_FLAG, SystemDefine.REPORT_FLAG_NOT_REPORT);

        // 取得項目
        wSearchKey.setCollect(PCTRetPlan.CONSIGNOR_CODE);
        wSearchKey.setCollect(PCTRetPlan.CONSIGNOR_NAME);
        wSearchKey.setCollect(PCTRetPlan.ITEM_NAME);
        // 作業No
        wSearchKey.setCollect(PCTRetWorkInfo.JOB_NO);
        // 予定一意キー
        wSearchKey.setCollect(PCTRetWorkInfo.PLAN_UKEY);
        // 設定単位キー
        wSearchKey.setCollect(PCTRetWorkInfo.SETTING_UNIT_KEY);
        // 予定日
        wSearchKey.setCollect(PCTRetWorkInfo.PLAN_DAY);
        // バッチNo.
        wSearchKey.setCollect(PCTRetWorkInfo.BATCH_NO);
        // バッチSeqNo.
        wSearchKey.setCollect(PCTRetWorkInfo.BATCH_SEQ_NO);
        // 予定エリア
        wSearchKey.setCollect(PCTRetWorkInfo.PLAN_AREA_NO);
        // エリア名称
        wSearchKey.setJoin((PCTRetWorkInfo.PLAN_AREA_NO), Area.AREA_NO);
        wSearchKey.setCollect(Area.AREA_NAME);
        // 得意先コード
        wSearchKey.setCollect(PCTRetWorkInfo.REGULAR_CUSTOMER_CODE);
        // 得意先名称
        wSearchKey.setCollect(PCTRetPlan.REGULAR_CUSTOMER_NAME);
        // 出荷先コード
        wSearchKey.setCollect(PCTRetWorkInfo.CUSTOMER_CODE);
        // 出荷先名称
        wSearchKey.setCollect(PCTRetPlan.CUSTOMER_NAME);
        // SeqNo.
        wSearchKey.setCollect(PCTRetWorkInfo.ORDER_SEQ);
        // 商品コード
        wSearchKey.setCollect(PCTRetWorkInfo.ITEM_CODE);
        // 商品名称
        wSearchKey.setCollect(PCTRetPlan.ITEM_NAME);
        // 予定数
        wSearchKey.setCollect(PCTRetWorkInfo.PLAN_QTY);
        // 実績数
        wSearchKey.setCollect(PCTRetWorkInfo.RESULT_QTY);
        // ロット入数
        wSearchKey.setCollect(PCTRetPlan.LOT_ENTERING_QTY);
        // 予定ゾーン
        wSearchKey.setCollect(PCTRetWorkInfo.PLAN_ZONE_NO);
        // 棚No.
        wSearchKey.setCollect(PCTRetWorkInfo.PLAN_LOCATION_NO);
        // 予定オーダーNo.
        wSearchKey.setCollect(PCTRetWorkInfo.PLAN_ORDER_NO);
        // 実績オーダーNo.
        wSearchKey.setCollect(PCTRetWorkInfo.RESULT_ORDER_NO);
        // 最終更新日時
        wSearchKey.setCollect(PCTRetWorkInfo.LAST_UPDATE_DATE);

        // バルーン用
        // ユーザID
        wSearchKey.setCollect(PCTRetWorkInfo.USER_ID);
        // ユーザ名称
        wSearchKey.setCollect(PCTRetHostSend.USER_NAME);
        // 号機NO.
        wSearchKey.setCollect(PCTRetWorkInfo.TERMINAL_NO);
        // JANコード
        wSearchKey.setCollect(PCTRetPlan.JAN);
        // ケースITF
        wSearchKey.setCollect(PCTRetPlan.ITF);
        // ボールITF
        wSearchKey.setCollect(PCTRetPlan.BUNDLE_ITF);

        // 昇順
        // 商品コード
        wSearchKey.setItemCodeOrder(true);
        // ロット入数
        wSearchKey.setOrder(PCTRetPlan.LOT_ENTERING_QTY, true);
        // ゾーンNo.
        wSearchKey.setPlanZoneNoOrder(true);
        // 棚No.
        wSearchKey.setPlanLocationNoOrder(true);

        // 表結合        
        wSearchKey.setJoin(PCTRetWorkInfo.PLAN_UKEY, "", PCTRetPlan.PLAN_UKEY, "");
        wSearchKey.setJoin(PCTRetWorkInfo.JOB_NO, "", PCTRetHostSend.JOB_NO, "");

        return wSearchKey;
    }

    /**
     * @param finder PCT出庫作業情報検索クラス
     * @return params
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected List<Params> createQueryResult(PCTRetWorkInfoFinder finder)
            throws CommonException
    {
        // 作業情報取得結果        
        PCTRetWorkInfo[] workInfoEntity = (PCTRetWorkInfo[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> outparams = new ArrayList<Params>();
        List<String> jobNo = null;
        int j = -1;

        // 件数文繰返し
        for (int i = 0; i < workInfoEntity.length; i++)
        {
            Params param = new Params();
            // 集約
            if (i > 0 && hasSameKey(workInfoEntity[j], workInfoEntity[i]))
            {
                outparams.get(j).set(PLAN_QTY, outparams.get(j).getInt(PLAN_QTY) + workInfoEntity[i].getPlanQty());
                outparams.get(j).set(RESULT_QTY, outparams.get(j).getInt(RESULT_QTY) + workInfoEntity[i].getResultQty());
                jobNo.add(workInfoEntity[i].getJobNo());
                outparams.get(j).set(WORK_NO_LIST, jobNo);
                if (workInfoEntity[j].getLastUpdateDate().before(workInfoEntity[i].getLastUpdateDate()))
                {
                    outparams.get(j).set(
                            LAST_UPDATE_DATE,
                            WmsFormatter.toParamDateTime(workInfoEntity[j].getLastUpdateDate(),
                                    WmsFormatter.MILISEC_LENGTH));
                }
                continue;
            }
            else
            {
                jobNo = new ArrayList<String>();
                j = j + 1;
            }

            // 荷主コード
            param.set(CONSIGNOR_CODE, workInfoEntity[i].getConsignorCode());
            // 荷主名称
            param.set(CONSIGNOR_NAME, (String)workInfoEntity[i].getValue(PCTRetPlan.CONSIGNOR_NAME, ""));
            // 作業No.
            jobNo.add(workInfoEntity[i].getJobNo());
            param.set(WORK_NO, workInfoEntity[i].getJobNo());
            param.set(WORK_NO_LIST, jobNo);
            // 予定一意キー
            param.set(PLAN_UKEY, workInfoEntity[i].getPlanUkey());
            // 設定単位キー
            param.set(SETTING_UNIT_KEY, workInfoEntity[i].getSettingUnitKey());
            // 予定日
            param.set(PLAN_DAY, WmsFormatter.toDispDate(workInfoEntity[i].getPlanDay(), getLocale()));
            // バッチSeqNo.
            param.set(BATCH_SEQ_NO, workInfoEntity[i].getBatchSeqNo());
            // バッチNo.
            param.set(BATCH_NO, workInfoEntity[i].getBatchNo());
            // 予定エリア
            param.set(AREA_NO, workInfoEntity[i].getPlanAreaNo());
            // エリア名称
            param.set(AREA_NAME, (String)workInfoEntity[i].getValue(Area.AREA_NAME, ""));
            // 得意先コード
            param.set(REGULAR_CUSTOMER_CODE, workInfoEntity[i].getRegularCustomerCode());
            // 得意先名称
            param.set(REGULAR_CUSTOMER_NAME, (String)workInfoEntity[i].getValue(PCTRetPlan.REGULAR_CUSTOMER_NAME, ""));
            // 出荷先コード
            param.set(CUSTOMER_CODE, workInfoEntity[i].getCustomerCode());
            // 出荷先名称
            param.set(CUSTOMER_NAME, (String)workInfoEntity[i].getValue(PCTRetPlan.CUSTOMER_NAME, ""));
            // 商品コード
            param.set(ITEM_CODE, workInfoEntity[i].getItemCode());
            // 商品名称
            param.set(ITEM_NAME, (String)workInfoEntity[i].getValue(PCTRetPlan.ITEM_NAME, ""));
            // 予定数
            param.set(PLAN_QTY, workInfoEntity[i].getPlanQty());
            // 実績数
            param.set(RESULT_QTY, workInfoEntity[i].getResultQty());
            // ロット入数
            param.set(LOT_QTY, Integer.parseInt(String.valueOf(workInfoEntity[i].getValue(PCTRetPlan.LOT_ENTERING_QTY,
                    ""))));
            // 予定ゾーン
            param.set(ZONE_NO, workInfoEntity[i].getPlanZoneNo());
            // 棚No.
            param.set(LOCATION_NO, workInfoEntity[i].getPlanLocationNo());
            // 実績オーダーNo.
            param.set(ORDER_NO, workInfoEntity[i].getResultOrderNo());

            // バルーン用
            // ユーザ名称
            param.set(USER_NAME, (String)workInfoEntity[i].getValue(PCTRetHostSend.USER_NAME, ""));
            // 号機No.
            param.set(RFT_NO, workInfoEntity[i].getTerminalNo());
            // JANコード
            param.set(JAN, (String)workInfoEntity[i].getValue(PCTRetPlan.JAN, ""));
            // ケースITF
            param.set(ITF, (String)workInfoEntity[i].getValue(PCTRetPlan.ITF, ""));
            // ボールITF
            param.set(BUNDLE_ITF, (String)workInfoEntity[i].getValue(PCTRetPlan.BUNDLE_ITF, ""));

            // 最終更新日時
            param.set(LAST_UPDATE_DATE, workInfoEntity[i].getLastUpdateDate());

            outparams.add(param);
        }
        return outparams;
    }

    /**
     * 引数で指定された作業情報が同一キーかを比較します。
     * @param workInfo1
     * @param workInfo2
     * @return true：同じ、false：異なる
     */
    protected boolean hasSameKey(PCTRetWorkInfo workInfo1, PCTRetWorkInfo workInfo2)
    {
        // 商品コード
        if (!workInfo1.getItemCode().equals(workInfo2.getItemCode()))
        {
            return false;
        }
        // エリアNo.
        if (!workInfo1.getPlanAreaNo().equals(workInfo2.getPlanAreaNo()))
        {
            return false;
        }
        // ゾーンNo.
        if (!workInfo1.getPlanZoneNo().equals(workInfo2.getPlanZoneNo()))
        {
            return false;
        }
        // 棚No.
        if (!workInfo1.getPlanLocationNo().equals(workInfo2.getPlanLocationNo()))
        {
            return false;
        }
        // ロット入数
        if (!String.valueOf(workInfo1.getValue(PCTRetPlan.LOT_ENTERING_QTY, "")).equals(
                String.valueOf(workInfo2.getValue(PCTRetPlan.LOT_ENTERING_QTY, ""))))
        {
            return false;
        }
        // オーダーNo.
        if (!workInfo1.getResultOrderNo().equals(workInfo2.getResultOrderNo()))
        {
            return false;
        }
        return true;
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
