// $Id: RetrievalResultMntSCH.java 7597 2010-03-16 01:16:06Z ota $
package jp.co.daifuku.wms.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import java.math.BigDecimal;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.HostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendFinder;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.rft.IdSchException;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.SearchKey;
import static jp.co.daifuku.wms.retrieval.schedule.RetrievalResultMntSCHParams.*;

/**
 * 出庫実績メンテナンスのスケジュール処理を行います。
 *
 * @version $Revision: 7597 $, $Date: 2010-03-16 10:16:06 +0900 (火, 16 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class RetrievalResultMntSCH
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
    public RetrievalResultMntSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
            finder = new HostSendFinder(getConnection());
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

        // DFKLOOK ここから
        RetrievalInParameter[] inParams = new RetrievalInParameter[ps.length];

        for (int i = 0; i < ps.length; i++)
        {
            RetrievalResultMntSCHParams startParam = (RetrievalResultMntSCHParams)ps[i];
            inParams[i] = new RetrievalInParameter(getWmsUserInfo());

            inParams[i].setRowNo(startParam.getRowIndex());
            inParams[i].setRetrievalPlanDay(startParam.getString(RETRIEVAL_PLAN_DATE));
            inParams[i].setOrderNo(startParam.getString(ORDER_NO));
            inParams[i].setRetrievalAreaNo(startParam.getString(RESULT_AREA_NO));
            inParams[i].setRetrievalLocation(startParam.getString(RESULT_LOCATION_NO));
            inParams[i].setItemCode(startParam.getString(ITEM_CODE));
            inParams[i].setItemName(startParam.getString(ITEM_NAME));
            inParams[i].setResultLotNo(startParam.getString(RESULT_LOT_NO));
            inParams[i].setEnteringQty(startParam.getInt(ENTERING_QTY));
            inParams[i].setPlanCaseQty(startParam.getInt(PLAN_CASE_QTY));
            inParams[i].setPlanPieceQty(startParam.getInt(PLAN_PIECE_QTY));
            inParams[i].setResultCaseQty(startParam.getInt(RESULT_CASE_QTY));
            inParams[i].setResultPieceQty(startParam.getInt(RESULT_PIECE_QTY));
            inParams[i].setBatchNo(startParam.getString(BATCH_NO));
            inParams[i].setBranchNo(startParam.getInt(BRANCH));
            inParams[i].setCustomerCode(startParam.getString(CUSTOMER_CODE));
            inParams[i].setCustomerName(startParam.getString(CUSTOMER_NAME));
            inParams[i].setJobNo(startParam.getString(JOB_NO));
            inParams[i].setLastUpdateDate(startParam.getDate(LASTUPDATE));
            inParams[i].setLineNo(startParam.getInt(LINE));
            inParams[i].setPlanQty(startParam.getInt(PLAN));
            inParams[i].setPlanUKey(startParam.getString(PLANUKEY));
            inParams[i].setPlanUKey(startParam.getString(RESULT_LOT_NO));

            inParams[i].setWmsUserInfo(getWmsUserInfo());

        }

        int rowNum = 0;

        Connection conn = getConnection();

        // 報告中フラグ true:報告中 false:報告停止
        boolean report_flag = false;
        boolean errorFlag = true;

        HostSendFinder hostSendFinder = null;

        try
        {

            //データ報告中チェック
            // 「0:停止中」であれば報告データ作成中フラグを「1:起動中」に更新
            // 「1:起動中」であればfalseを返す。
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

            report_flag = true;

            // リストエリアの入力チェックを行う。
            if (!checkList(conn, inParams))
            {
                return false;
            }

            //DNHOSTSEND用インスタンス生成
            HostSendHandler hosySendHandle = new HostSendHandler(conn);
            HostSendAlterKey hosySendAltKey = new HostSendAlterKey();

            //DNWORKINFO用インスタンス生成
            WorkInfoHandler wiHandle = new WorkInfoHandler(conn);
            WorkInfoAlterKey wiAltKey = new WorkInfoAlterKey();

            //DNRetrievalPLAN用インスタンス生成
            RetrievalPlanHandler retrievalPlanHandle = new RetrievalPlanHandler(conn);
            RetrievalPlanAlterKey retrievalPlanAltKey = new RetrievalPlanAlterKey();


            //パラメータの配列要素数だけ呼び出しを繰り返す
            for (rowNum = 0; rowNum < inParams.length; rowNum++)
            {
                // ----------------------------------
                // 更新テーブルのロック処理
                hostSendFinder = new HostSendFinder(getConnection());
                HostSendSearchKey sKey = new HostSendSearchKey();

                hostSendFinder.open(true);

                // 検索条件
                sKey.setJobNo(inParams[rowNum].getJobNo());
                sKey.setLastUpdateDate(inParams[rowNum].getLastUpdateDate());

                // 結合条件
                sKey.setJoin(HostSend.JOB_NO, WorkInfo.JOB_NO);
                sKey.setJoin(HostSend.PLAN_UKEY, RetrievalPlan.PLAN_UKEY);

                // ロック
                int count = hostSendFinder.searchForUpdate(sKey, HostSendFinder.WAIT_SEC_DEFAULT);

                // 不正な件数のとき
                if (count == 0)
                {
                    throw new NotFoundException();
                }
                else if (count >= 2)
                {
                    throw new NoPrimaryException();
                }

                // ロックしたデータを取得
                HostSend lockEntity = (HostSend)hostSendFinder.getEntities(1)[0];

                // 予定一意キーをセット
                inParams[rowNum].setPlanUKey(lockEntity.getPlanUkey());

                // 実績値の差分(入力値とDB格納値)
                int diffResultQty = inParams[rowNum].getResultQty() - lockEntity.getResultQty();

                hosySendAltKey.clear();
                wiAltKey.clear();
                retrievalPlanAltKey.clear();

                //----------------------------------
                //入出庫実績情報更新(DNHOSTSEND)

                // 検索条件を設定する
                // 作業No.(inParam)
                hosySendAltKey.setJobNo(inParams[rowNum].getJobNo());
                // 最終更新日時
                hosySendAltKey.setLastUpdateDate(inParams[rowNum].getLastUpdateDate());

                // 実績数
                hosySendAltKey.updateResultQty(inParams[rowNum].getResultQty());
                // 欠品数（実績予定数ー作業実績数）
                if (inParams[rowNum].getPlanQty() - inParams[rowNum].getResultQty() < 0)
                {
                    setErrorRowIndex(inParams[rowNum].getRowNo());
                    // 6006040=データの不整合が発生しました。{0}
                    setMessage(WmsMessageFormatter.format(6006040, inParams[rowNum].getRowNo()));
                    return false;
                }
                hosySendAltKey.updateShortageQty(inParams[rowNum].getPlanQty() - inParams[rowNum].getResultQty());
                // ロットNo.
                hosySendAltKey.updateResultLotNo(inParams[rowNum].getResultLotNo());
                // ユーザID
                hosySendAltKey.updateUserId(inParams[rowNum].getUserId());
                // ユーザ名称
                hosySendAltKey.updateUserName(inParams[rowNum].getUserName());
                // 端末No
                hosySendAltKey.updateTerminalNo(inParams[rowNum].getTerminalNo());
                // 最終更新処理名
                hosySendAltKey.updateLastUpdatePname(getClass().getSimpleName());

                // 作業情報の更新
                hosySendHandle.modify(hosySendAltKey);

                //------------------------------------
                //入出庫作業情報更新(DNWORKINFO)
                // 検索条件を設定する。
                // 検索条件を設定する。
                // 作業No.(inParam)
                wiAltKey.setJobNo(inParams[rowNum].getJobNo());

                // 更新条件を設定する。
                // 作業実績数
                wiAltKey.updateResultQty(inParams[rowNum].getResultQty());
                // 欠品数（実績予定数ー作業実績数）
                if (inParams[rowNum].getPlanQty() - inParams[rowNum].getResultQty() < 0)
                {
                    setErrorRowIndex(inParams[rowNum].getRowNo());
                    // 6006040=データの不整合が発生しました。{0}
                    setMessage(WmsMessageFormatter.format(6006040, inParams[rowNum].getRowNo()));
                    return false;
                }
                wiAltKey.updateShortageQty(inParams[rowNum].getPlanQty() - inParams[rowNum].getResultQty());
                // ロットNo.
                wiAltKey.updateResultLotNo(inParams[rowNum].getLotNo());
                // ユーザID
                wiAltKey.updateUserId(inParams[rowNum].getUserId());
                // 端末No
                wiAltKey.updateTerminalNo(inParams[rowNum].getTerminalNo());
                // 最終更新処理名
                wiAltKey.updateLastUpdatePname(getClass().getSimpleName());

                // 作業情報の更新
                wiHandle.modify(wiAltKey);

                //------------------------------------
                //出庫予定情報更新(DNRETRIEVALPLAN)
                // 更新条件を設定する。
                // PLAN_UKEY
                retrievalPlanAltKey.setPlanUkey(inParams[rowNum].getPlanUKey());

                // 実績数
                retrievalPlanAltKey.setUpdateWithColumn(RetrievalPlan.RESULT_QTY, RetrievalPlan.RESULT_QTY,
                        new BigDecimal(diffResultQty));

                // 欠品数
                retrievalPlanAltKey.setUpdateWithColumn(RetrievalPlan.SHORTAGE_QTY, RetrievalPlan.SHORTAGE_QTY,
                        new BigDecimal(-diffResultQty));

                // 最終更新処理名
                retrievalPlanAltKey.updateLastUpdatePname(getClass().getSimpleName());

                // 作業情報の更新
                retrievalPlanHandle.modify(retrievalPlanAltKey);
            }

            errorFlag = false;
            // 修正登録メッセージ
            setMessage("6001011");

            return true;

        }
        catch (NotFoundException e)
        {
            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, inParams[rowNum].getRowNo()));
            setErrorRowIndex(inParams[rowNum].getRowNo());
            return false;
        }
        finally
        {
            closeFinder(hostSendFinder);

            // 正常に処理が行われなかった場合はrollback
            if (errorFlag)
            {
                doRollBack(this.getClass());
            }

            if (report_flag)
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
        // ここまで
    }

    /**
     * リストセル入力値に対してチェックを行います。
     * エリア登録チェック、棚登録チェック、実績数登録チェック
     * @param searchParam 入庫変更パラメータ
     * @return boolean
     * @throws CommonException 変更パラメータチェック時エラー
     */
    public boolean check(ScheduleParams... ps)
            throws CommonException
    {
        // DFKLOOK ここから
        RetrievalInParameter[] inParams = new RetrievalInParameter[ps.length];

        for (int i = 0; i < ps.length; i++)
        {
            RetrievalResultMntSCHParams startParam = (RetrievalResultMntSCHParams)ps[i];
            inParams[i] = new RetrievalInParameter(getWmsUserInfo());

            inParams[i].setRowNo(startParam.getRowIndex());
            inParams[i].setRetrievalPlanDay(startParam.getString(RETRIEVAL_PLAN_DATE));
            inParams[i].setOrderNo(startParam.getString(ORDER_NO));
            inParams[i].setRetrievalAreaNo(startParam.getString(RESULT_AREA_NO));
            inParams[i].setRetrievalLocation(startParam.getString(RESULT_LOCATION_NO));
            inParams[i].setItemCode(startParam.getString(ITEM_CODE));
            inParams[i].setItemName(startParam.getString(ITEM_NAME));
            inParams[i].setResultLotNo(startParam.getString(RESULT_LOT_NO));
            inParams[i].setEnteringQty(startParam.getInt(ENTERING_QTY));
            inParams[i].setPlanCaseQty(startParam.getInt(PLAN_CASE_QTY));
            inParams[i].setPlanPieceQty(startParam.getInt(PLAN_PIECE_QTY));
            inParams[i].setResultCaseQty(startParam.getInt(RESULT_CASE_QTY));
            inParams[i].setResultPieceQty(startParam.getInt(RESULT_PIECE_QTY));
            inParams[i].setBatchNo(startParam.getString(BATCH_NO));
            inParams[i].setBranchNo(startParam.getInt(BRANCH));
            inParams[i].setCustomerCode(startParam.getString(CUSTOMER_CODE));
            inParams[i].setCustomerName(startParam.getString(CUSTOMER_NAME));
            inParams[i].setJobNo(startParam.getString(JOB_NO));
            inParams[i].setLastUpdateDate(startParam.getDate(LASTUPDATE));
            inParams[i].setLineNo(startParam.getInt(LINE));
            inParams[i].setPlanQty(startParam.getInt(PLAN));
            inParams[i].setPlanUKey(startParam.getString(PLANUKEY));
            inParams[i].setPlanUKey(startParam.getString(RESULT_LOT_NO));

            inParams[i].setWmsUserInfo(getWmsUserInfo());

        }

        for (int i = 0; i < inParams.length; i++)
        {
            // オーバーフローチェック
            long result =
                    ((long)inParams[i].getEnteringQty() * (long)inParams[i].getResultCaseQty())
                            + (long)inParams[i].getResultPieceQty();
            if ((long)WmsParam.MAX_TOTAL_QTY < result)
            {
                // 6023207=No.{0} 出庫数には作業上限数{1}以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023207, inParams[i].getRowNo(),
                        WmsFormatter.getNumFormat(WmsParam.MAX_TOTAL_QTY)));
                setErrorRowIndex(inParams[i].getRowNo());
                return false;
            }

            // 出庫数＞出庫予定数の場合
            if (inParams[i].getPlanQty() < result)
            {
                // 6023192=No.{0} 出庫数には出庫予定数以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023192, inParams[i].getRowNo()));
                setErrorRowIndex(inParams[i].getRowNo());
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
     * @return HostSendSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        HostSendSearchKey searchKey = new HostSendSearchKey();

        RetrievalPlanSearchKey retPlanKey = new RetrievalPlanSearchKey();


        // DFKLOOK ここから
        // 作業区分
        retPlanKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        // 予定一意キー
        retPlanKey.setPlanUkeyCollect();

        /* 検索条件の指定 */
        // 出庫予定日
        if (!StringUtil.isBlank(p.getString(RETRIEVAL_PLAN_DATE)))
        {
            searchKey.setPlanDay(WmsFormatter.toParamDate(p.getDate(RETRIEVAL_PLAN_DATE)));
        }
        // バッチNo
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            searchKey.setBatchNo(p.getString(BATCH_NO));
        }
        // オーダNo
        if (!StringUtil.isBlank(p.getString(ORDER_NO)))
        {
            searchKey.setOrderNo(p.getString(ORDER_NO));
        }
        // 出荷先コード
        if (!StringUtil.isBlank(p.getString(CUSTOMER_CODE)))
        {
            searchKey.setCustomerCode(p.getString(CUSTOMER_CODE));
        }
        // エリア(実績)
        if (!p.getString(RETRIEVAL_AREA).equals(WmsParam.ALL_AREA_NO))
        {
            searchKey.setResultAreaNo(p.getString(RETRIEVAL_AREA));
        }

        //結合
        searchKey.setJoin(HostSend.RESULT_AREA_NO, "", Area.AREA_NO, "(+)");

        // 荷主コード
        searchKey.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        // 作業区分(出庫)
        searchKey.setJobType(HostSend.JOB_TYPE_RETRIEVAL);

        searchKey.setKey(HostSend.PLAN_UKEY, retPlanKey);

        /* ソート順の指定 */
        // 出庫予定日
        searchKey.setPlanDayOrder(true);
        // オーダNo
        searchKey.setOrderNoOrder(true);
        //出庫エリア
        searchKey.setResultAreaNoOrder(true);
        //出庫棚
        searchKey.setResultLocationNoOrder(true);
        // 商品コード
        searchKey.setItemCodeOrder(true);
        //ロットNo.
        searchKey.setResultLotNoOrder(true);

        searchKey.setCollect(new FieldName(HostSend.STORE_NAME, FieldName.ALL_FIELDS));
        searchKey.setCollect(Area.AREA_NAME);

        // ここまで
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
        HostSend[] entities = (HostSend[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (HostSend ent : entities)
        {
            Params param = new Params();

            // DFKLOOK ここから

            // 出庫予定日
            param.set(PLAN_DAY, WmsFormatter.toDate(ent.getPlanDay()));
            // オーダNo
            param.set(ORDER_NO, ent.getOrderNo());
            // 商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            // 商品名称
            param.set(ITEM_NAME, ent.getItemName());
            // 実績エリア
            param.set(RESULT_AREA_NO, ent.getResultAreaNo());
            //実績棚
            param.set(RESULT_LOCATION_NO, ent.getResultLocationNo());
            //ケース入数
            int caseQty = ent.getEnteringQty();
            param.set(ENTERING_QTY, caseQty);
            //予定数
            int planQty = ent.getPlanQty();
            //予定ケース数
            param.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(planQty, caseQty));
            //予定ピース数
            param.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(planQty, caseQty));
            //実績数
            int resultQty = ent.getResultQty();
            //実績ケース数
            param.set(RESULT_CASE_QTY, DisplayUtil.getCaseQty(resultQty, caseQty));
            //実績ピース数
            param.set(RESULT_PIECE_QTY, DisplayUtil.getPieceQty(resultQty, caseQty));
            //実績ロットNo
            param.set(RESULT_LOT_NO, ent.getResultLotNo());
            //実績報告区分０，１，２
            if (ent.getReportFlag().equals(HostSend.REPORT_FLAG_REPORT))
            {
                param.set(REPORT_FLAG, DisplayResource.getReportFlag(SystemDefine.REPORT_FLAG_REPORT));
            }
            else if(ent.getReportFlag().equals(HostSend.REPORT_FLAG_CARRY))
            {
                param.set(REPORT_FLAG, DisplayResource.getReportFlag(SystemDefine.REPORT_FLAG_CARRY));            	
            }
            else
            {
                param.set(REPORT_FLAG, DisplayResource.getReportFlag(SystemDefine.REPORT_FLAG_NOT_REPORT));
            }
            param.set(HIDDEN_FLAG, ent.getReportFlag()); 
            //作業No.
            param.set(JOB_NO, ent.getJobNo());
            //予定一意キー
            param.set(PLANUKEY, ent.getPlanUkey());
            //予定数
            param.set(PLAN, planQty);
            //最終更新日時
            param.set(LASTUPDATE, ent.getLastUpdateDate());

            //toolTip用
            //バッチNo
            param.set(BATCH_NO, ent.getBatchNo());
            //伝票No
            param.set(TICKET, ent.getShipTicketNo());
            //行No
            param.set(LINE, ent.getShipLineNo());
            //作業枝番
            param.set(BRANCH, ent.getShipBranchNo());
            //出荷先コード
            param.set(CUSTOMER_CODE, ent.getCustomerCode());
            //出荷先名称
            param.set(CUSTOMER_NAME, ent.getCustomerName());
            //出庫エリア名称
            param.set(AREA_NAME, (String)ent.getValue(Area.AREA_NAME, ""));

            // ｹｰｽ数
            param.set(CASE, DisplayUtil.getCaseQty(resultQty, caseQty));
            // ﾋﾟｰｽ数
            param.set(PIECE, DisplayUtil.getPieceQty(resultQty, caseQty));
            // ﾛｯﾄ
            param.set(LOT, ent.getResultLotNo());


            // ここまで

            result.add(param);
        }

        return result;
    }

    /**
     * DFKLOOK
     * 画面から入力された内容をパラメータとして受け取り、ためうちエリアの入力チェックを行います。
     *
     * @param conn データベースとのコネクションを保持するインスタンス。
     * @param checkParam 画面から入力された内容を持つRetrievalSupportParameterクラスのインスタンス。
     * @return 入力エラーが無ければtrueを、入力エラー発生した場合はfalseを返します。
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected boolean checkList(Connection conn, Parameter[] checkParam)
            throws CommonException
    {
        // 出庫パラメータに変換
        RetrievalInParameter[] inParam = (RetrievalInParameter[])checkParam;

        for (int i = 0; i < inParam.length; i++)
        {
            // オーバーフローチェック
            long result =
                    ((long)inParam[i].getEnteringQty() * (long)inParam[i].getResultCaseQty())
                            + (long)inParam[i].getResultPieceQty();
            if ((long)WmsParam.MAX_TOTAL_QTY < result)
            {
                // 6023207=No.{0} 出庫数には作業上限数{1}以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023207, inParam[i].getRowNo(),
                        WmsFormatter.getNumFormat(WmsParam.MAX_TOTAL_QTY)));
                setErrorRowIndex(inParam[i].getRowNo());
                return false;
            }

            // 出庫数＞出庫予定数の場合
            if (inParam[i].getPlanQty() < result)
            {
                // 6023192=No.{0} 出庫数には出庫予定数以下の値を入力してください。
                setMessage(WmsMessageFormatter.format(6023192, inParam[i].getRowNo()));
                setErrorRowIndex(inParam[i].getRowNo());
                return false;
            }
            else
            {
                inParam[i].setResultQty((int)result);
            }
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
