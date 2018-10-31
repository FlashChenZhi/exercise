// $Id: RetrievalPlanModifySCH.java,v 1.2 2009/02/24 02:40:28 ose Exp $
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

import static jp.co.daifuku.wms.retrieval.schedule.RetrievalPlanModifySCHParams.*;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.CustomerController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.db.DatabaseHandler;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 出庫予定修正・削除のスケジュール処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:40:28 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class RetrievalPlanModifySCH
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
    public RetrievalPlanModifySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * １画面目の検索条件より検索された該当データの件数チェックを行います。<BR>
     * 処理が正常に行われた場合はtrueを返します。<BR>
     * 該当データが最大表示件数以上の場合、メッセージをセットしfalseを返します。<BR>
     * 
     * @param p 検索条件パラメータ
     * @return boolean 正常完了時はtrue、条件エラーなど場合はfalseを返す。<BR>
     * @throws CommonException データベース処理でエラーが発生した場合にthrowする。<BR>
     *                          処理中に何らかのエラーが発生した場合にthrowする。<BR>
     */
    public boolean nextCheck(ScheduleParams p)
            throws CommonException
    {
        // 出庫予定情報Handler類のインスタンス生成
        RetrievalPlanSearchKey retrievalPlanSearchKey = new RetrievalPlanSearchKey();
        RetrievalPlanHandler retrievalPlanHandler = new RetrievalPlanHandler(getConnection());

        //検索条件のセット
        // 荷主コードのセット
        retrievalPlanSearchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        //出庫予定日のセット
        retrievalPlanSearchKey.setPlanDay(WmsFormatter.toParamDate(p.getDate(PLAN_DAY)));
        //伝票No.のセット
        retrievalPlanSearchKey.setShipTicketNo(p.getString(SLIP_NUMBER));
        //行No.に入力があれば、セット
        if (p.get(LINE_NO) != null && (p.getInt(LINE_NO)) >= 0)
        {
            retrievalPlanSearchKey.setShipLineNo(p.getInt(LINE_NO));
        }
        //作業状態"未作業"をセット
        retrievalPlanSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

        // 在庫パッケージの有無で検索対象が変化
        WarenaviSystemController wmsControl = new WarenaviSystemController(getConnection(), this.getClass());
        if (wmsControl.hasStockPack())
        {
            // スケジュールフラグ(未スケジュール)
            retrievalPlanSearchKey.setSchFlag(SystemDefine.SCH_FLAG_NOT_SCHEDULE);
        }
        else
        {
            //在庫パッケージ無の場合
            // スケジュールフラグ
            retrievalPlanSearchKey.setSchFlag(SystemDefine.SCH_FLAG_SCHEDULE);
        }
        
        //該当データ件数取得
        int cnt = retrievalPlanHandler.count(retrievalPlanSearchKey);

        if (!canLowerDisplay(cnt))
        {
            return false;
        }

        return true;
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

        try
        {
            //在庫パッケージの有無によってロック処理を行う
            WarenaviSystemController wmsControl = new WarenaviSystemController(getConnection(), getClass());
            // 在庫パッケージなしの場合のみ
            if (!wmsControl.hasStockPack())
            {
                //ロック処理を行う(予定・作業データ)
                if (!lockRetrievalHasNotStockPack(ps))
                {
                    return false;
                }
            }
            else
            {
                //ロック処理を行う(予定データ)
                if (!lockRetrievalHasStockPack(ps))
                {
                    return false;
                }
            }

            //処理判別
            //修正処理
            if (ProcessFlag.UPDATE.equals(ps[0].getProcessFlag()))
            {
                return modify(ps);
            }
            //削除処理
            else if (ProcessFlag.DELETE.equals(ps[0].getProcessFlag()))
            {
                delete(ps[0]);
                return true;
            }
            //全削除処理
            else if (ProcessFlag.DELETE_ALL.equals(ps[0].getProcessFlag()))
            {
                allDelete(ps);
                return true;
            }
            return false;
        }
        catch (LockTimeOutException e)
        {
            // 他端末で変更が行われました。前画面に戻り再度検索を行ってください。
            setMessage(WmsMessageFormatter.format(6023004));
            setErrorRowIndex(ps[0].getRowIndex());
            return false;
        }
        
    }

    /**
     * 画面から入力された内容のチェックを行います。<BR>
     * 修正対象データが未作業であるか、または更新が行われていないか、選択エリアに入力棚No.が存在するかチェックする。
     * @param p 表示データ取得条件を持つ<CODE>RetrievalInParameter</CODE>クラスのインスタンス。<BR>
     *         <CODE>RetrievalInParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
     * @return boolean 正常完了時はtrue、条件エラーなど場合はfalseを返す。<BR>
     * @throws CommonException  データベースとの接続で異常が発生した場合に通知されます。
     *                           チェック処理内で予期しない例外が発生した場合に通知されます。
     */
    public boolean check(ScheduleParams p)
            throws CommonException
    {
        return true;
    }
    
    /**
     * 画面から入力された内容とリストセルエリアのデータをパラメータとして受け取り、
     * チェックを行います。<BR>
     *
     * @param p 入力パラメータ
     * @param ps リストセルエリアのパラメータ
     * @return 入力チェック、オーバーフロー、重複、商品マスタ・入庫棚エラーでない場合はtrueを返す。
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public boolean check(ScheduleParams p, ScheduleParams... ps)
            throws CommonException
    {
        //出庫予定情報Handler類のインスタンス生成
        RetrievalPlanHandler retrievalPlanHandler = new RetrievalPlanHandler(getConnection());
        RetrievalPlanSearchKey retrievalPlanSearchKey = new RetrievalPlanSearchKey();
        //検索条件をセット
        //予定一意キーのセット
        retrievalPlanSearchKey.setPlanUkey(p.getString(PLAN_U_KEY));
        // 最終更新日時
        retrievalPlanSearchKey.setLastUpdateDate(p.getDate(LAST_UPDATE_DATE));

        if (retrievalPlanHandler.count(retrievalPlanSearchKey) == 0)
        {
            //他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return false;
        }
        
        // 6001019=入力を受け付けました。
        setMessage("6001019");
        return true;
    }
    
    /**
     * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
     * @param searchParam 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
     * @return 検索結果が含まれた<CODE>Parameter</CODE>クラスを実装したインスタンス
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public Params initFind(ScheduleParams searchParam)
            throws CommonException
    {
        Params outParam = new Params();

        WarenaviSystemController wmsControl = new WarenaviSystemController(getConnection(), this.getClass());

        //マスタ管理導入チェック
        if (wmsControl.hasMasterPack())
        {
            //導入済みの場合、パラメータの先頭アドレスにtrueをセット
            outParam.set(MASTER, true);
        }
        else
        {
            //未導入の場合、falseをセット
            outParam.set(MASTER, false);
        }

        //在庫管理導入チェック
        if (wmsControl.hasStockPack())
        {
            //導入済みの場合、パラメータの先頭アドレスにtrueをセット
            outParam.set(STOCKPACK, true);
        }
        else
        {
            //未導入の場合、falseをセット
            outParam.set(STOCKPACK, false);
        }

        return outParam;
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
    protected SearchKey createSearchKey(ScheduleParams p) throws CommonException
    {
        RetrievalPlanSearchKey searchKey = new RetrievalPlanSearchKey();

        WarenaviSystemController wmsControl = new WarenaviSystemController(getConnection(), this.getClass());
        
        //検索条件をセット
        //荷主コードのセット
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        //出庫予定日のセット
        searchKey.setPlanDay(WmsFormatter.toParamDate(p.getDate(PLAN_DAY)));
        //伝票No.のセット
        searchKey.setShipTicketNo(p.getString(SLIP_NUMBER));
        //行No.のセットする
        if (p.get(LINE_NO) != null && p.getInt(LINE_NO) >= 0)
        {
            searchKey.setShipLineNo(p.getInt(LINE_NO));
        }

        //作業状態を未作業にセット
        searchKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

        //商品マスタテーブルを検索
        searchKey.setJoin(RetrievalPlan.CONSIGNOR_CODE, "", Item.CONSIGNOR_CODE, "(+)");
        searchKey.setJoin(RetrievalPlan.ITEM_CODE, "", Item.ITEM_CODE, "(+)");

        //出荷先マスタテーブルを検索
        searchKey.setJoin(RetrievalPlan.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
        searchKey.setJoin(RetrievalPlan.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");

        //エリアマスタテーブルを検索
        searchKey.setJoin(RetrievalPlan.PLAN_AREA_NO, "", Area.AREA_NO, "(+)");

        //作業情報テーブルを検索
        if (wmsControl.hasStockPack())
        {
            // スケジュールフラグ(未スケジュール)
            searchKey.setSchFlag(SystemDefine.SCH_FLAG_NOT_SCHEDULE);
        }
        else
        {
            //在庫パッケージ無の場合
            // スケジュールフラグ
            searchKey.setSchFlag(SystemDefine.SCH_FLAG_SCHEDULE);
        }

        //ソート順をセットする
        searchKey.setShipLineNoOrder(true);
        searchKey.setBranchNoOrder(true);
        
        // 取得項目
        searchKey.setCollect(Item.ITEM_NAME);
        searchKey.setCollect(Item.ENTERING_QTY);
        searchKey.setCollect(Item.JAN);
        searchKey.setCollect(Item.ITF);
        searchKey.setCollect(Customer.CUSTOMER_NAME);
        searchKey.setCollect(Area.AREA_NAME);
        searchKey.setCollect(new FieldName(RetrievalPlan.STORE_NAME, FieldName.ALL_FIELDS));
        
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

            //予定一意キー
            param.set(PLAN_U_KEY, ent.getPlanUkey());
            //行No.
            param.set(FILE_LINE_NO, ent.getShipLineNo());
            //作業枝番
            param.set(BRANCH_NO, ent.getBranchNo());
            //バッチNo.
            param.set(BATCH_NO, ent.getBatchNo());
            //オーダーNo.
            param.set(ORDER_NO, ent.getOrderNo());
            //出荷先コード
            param.set(CUSTOMER_CODE, ent.getCustomerCode());
            //出荷先名称
            param.set(CUSTOMER_NAME, ent.getValue(Customer.CUSTOMER_NAME, ""));
            //商品コード
            param.set(ITEM_CODE, ent.getItemCode());
            //商品名称
            param.set(ITEM_NAME, ent.getValue(Item.ITEM_NAME, ""));
            //ケース入数
            int enteringQty = (ent.getBigDecimal(Item.ENTERING_QTY)).intValue();
            param.set(ENTERING_QTY, enteringQty);
            //予定ケース数
            param.set(PLAN_CASE_QTY, DisplayUtil.getCaseQty(ent.getPlanQty(), enteringQty));
            //予定ピース数
            param.set(PLAN_PIECE_QTY, DisplayUtil.getPieceQty(ent.getPlanQty(), enteringQty));
            //ロットNo.
            param.set(LOT_NO, ent.getPlanLotNo());
            //JANコード
            param.set(JAN, ent.getValue(Item.JAN, ""));
            //ケースITF
            param.set(ITF, ent.getValue(Item.ITF, ""));

            //出庫エリアNo.
            param.set(PLAN_AREA_NO, ent.getPlanAreaNo());
            //出庫エリア名称
            param.set(AREA_NAME, ent.getValue(Area.AREA_NAME, ""));
            //出庫棚
            param.set(PLAN_LOCATION_NO, ent.getPlanLocationNo());
            //最終更新日時
            param.set(LAST_UPDATE_DATE, ent.getLastUpdateDate());

            result.add(param);
        }

        return result;
    }
    
    /**
     * 修正・削除用に未作業の出庫予定情報をロックします。<BR>
     * ロック対象が無かった場合falseを返します。<BR>
     *
     * @param inputParam ロック対象データ
     * @return 実行結果（正常：true 異常：false)
     * @throws LockTimeOutException でにレコードがロックされていた場合にスローされます。
     * @throws NoPrimaryException 対象データが1件でないときスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    protected boolean lockRetrievalHasNotStockPack(ScheduleParams[] inputParam)
            throws LockTimeOutException,
                NoPrimaryException,
                ReadWriteException
    {
        //出庫予定情報Handler類のインスタンス生成
        RetrievalPlanHandler retrievalPlanHandler = new RetrievalPlanHandler(getConnection());
        RetrievalPlanSearchKey retrievalPlanSearchKey = new RetrievalPlanSearchKey();

        // ロック処理を行う
        for (int i = 0; i < inputParam.length; i++)
        {
            retrievalPlanSearchKey.clear();
            // 予定一意キーをセット
            retrievalPlanSearchKey.setPlanUkey(inputParam[i].getString(PLAN_U_KEY));
            // 最終更新日時
            retrievalPlanSearchKey.setLastUpdateDate(inputParam[i].getDate(LAST_UPDATE_DATE));
            // Join
            retrievalPlanSearchKey.setJoin(RetrievalPlan.PLAN_UKEY, WorkInfo.PLAN_UKEY);

            //ロック処理
            if (retrievalPlanHandler.findPrimaryForUpdate(retrievalPlanSearchKey, DatabaseHandler.WAIT_SEC_NOWAIT) == null)
            {
                // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, inputParam[i].getRowIndex()));
                setErrorRowIndex(inputParam[i].getRowIndex());
                return false;
            }
        }

        return true;
    }

    /**
     * 修正・削除用に未作業の出庫予定情報をロックします。<BR>
     * ロック対象が無かった場合falseを返します。<BR>
     *
     * @param inputParam ロック対象データ
     * @return 実行結果（正常：true 異常：false)
     * @throws LockTimeOutException でにレコードがロックされていた場合にスローされます。
     * @throws NoPrimaryException 対象データが1件でないときスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    protected boolean lockRetrievalHasStockPack(ScheduleParams[] inputParam)
            throws LockTimeOutException,
                NoPrimaryException,
                ReadWriteException
    {
        //出庫予定情報Handler類のインスタンス生成
        RetrievalPlanHandler retrievalPlanHandler = new RetrievalPlanHandler(getConnection());
        RetrievalPlanSearchKey retrievalPlanSearchKey = new RetrievalPlanSearchKey();

        // ロック処理を行う
        for (int i = 0; i < inputParam.length; i++)
        {
            retrievalPlanSearchKey.clear();
            // 予定一意キーをセット
            retrievalPlanSearchKey.setPlanUkey(inputParam[i].getString(PLAN_U_KEY));
            // 最終更新日時
            retrievalPlanSearchKey.setLastUpdateDate(inputParam[i].getDate(LAST_UPDATE_DATE));

            //ロック処理
            if (retrievalPlanHandler.findPrimaryForUpdate(retrievalPlanSearchKey, DatabaseHandler.WAIT_SEC_NOWAIT) == null)
            {
                // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, inputParam[i].getRowIndex()));
                setErrorRowIndex(inputParam[i].getRowIndex());
                return false;
            }
        }

        return true;
    }
    
    /**
     * 出庫予定データの修正を行います。<BR>
     * パラメータの内容をもとに、DNRETRIEVALPLANとDNWORKINFOの更新処理を行います。
     * マスタ管理パッケージが導入されていない場合、DMITEMの更新も行います。
     * @param startParam 表示データ取得条件を持つ<CODE>RetrievalInParameter</CODE>クラスのインスタンス。<BR>
     *         <CODE>RetrievalInParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
     * @return boolean 正常完了時はtrue、条件エラーなど場合はfalseを返す。<BR>
     * @throws CommonException  データベースとの接続で異常が発生した場合に通知されます。
     *                           チェック処理内で予期しない例外が発生した場合に通知されます。
     */
    protected boolean modify(ScheduleParams[] startParam)
            throws CommonException
    {
        int i = 0;
        try
        {
            RetrievalPlanHandler retrievalPlanHandler = new RetrievalPlanHandler(getConnection());
            WorkInfoHandler workInfoHandler = new WorkInfoHandler(getConnection());

            WarenaviSystemController wmsControl = new WarenaviSystemController(getConnection(), getClass());

            // WmsUserInfoの取得
            WmsUserInfo wmsUser = getWmsUserInfo();
            
            for (i = 0; i < startParam.length; i++)
            {

                // 入出庫作業情報更新項目のセット
                // 出庫予定数
                int qty =
                        startParam[i].getInt(PLAN_CASE_QTY) * startParam[i].getInt(ENTERING_QTY)
                                + startParam[i].getInt(PLAN_PIECE_QTY);

                // 在庫パッケージなしの場合のみ
                if (!wmsControl.hasStockPack())
                {
                    WorkInfoAlterKey workInfoAlterKey = new WorkInfoAlterKey();

                    // 入出庫作業情報検索キーのセット
                    // 予定一意キー
                    workInfoAlterKey.setPlanUkey(startParam[i].getString(PLAN_U_KEY));
                    workInfoAlterKey.updatePlanQty(qty);
                    // 出庫予定エリアNo.
                    workInfoAlterKey.updatePlanAreaNo(startParam[i].getString(PLAN_AREA_NO));
                    // 出庫予定棚No.
                    workInfoAlterKey.updatePlanLocationNo(startParam[i].getString(PLAN_LOCATION_NO));
                    // 予定ﾛｯﾄNo.
                    workInfoAlterKey.updatePlanLotNo(startParam[i].getString(LOT_NO));
                    // 最終更新処理名
                    workInfoAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                    // 更新処理
                    workInfoHandler.modify(workInfoAlterKey);
                }

                //出庫予定情報更新処理
                RetrievalPlanAlterKey retrievalPlanAlterKey = new RetrievalPlanAlterKey();

                //出庫予定情報検索キーのセット
                //予定一意キー
                retrievalPlanAlterKey.setPlanUkey(startParam[i].getString(PLAN_U_KEY));
                //出庫予定情報更新項目のセット
                retrievalPlanAlterKey.updatePlanQty(qty);
                //ロットNo.
                retrievalPlanAlterKey.updatePlanLotNo(startParam[i].getString(LOT_NO));
                //出庫予定エリアNo.
                retrievalPlanAlterKey.updatePlanAreaNo(startParam[i].getString(PLAN_AREA_NO));
                //出庫予定棚No.
                retrievalPlanAlterKey.updatePlanLocationNo(startParam[i].getString(PLAN_LOCATION_NO));
                //最終更新処理名
                retrievalPlanAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                //更新処理
                retrievalPlanHandler.modify(retrievalPlanAlterKey);

                //マスタ管理導入済みチェック
                if (!wmsControl.hasMasterPack())
                {
                    //商品マスタ更新処理
                    ItemController itemControl = new ItemController(getConnection(), this.getClass());
                    Item item = new Item();

                    //荷主コード
                    item.setConsignorCode(startParam[i].getString(CONSIGNOR_CODE));
                    //商品コード
                    item.setItemCode(startParam[i].getString(ITEM_CODE));
                    //商品名称
                    item.setItemName(startParam[i].getString(ITEM_NAME));
                    //ケース入数
                    item.setEnteringQty(startParam[i].getInt(ENTERING_QTY));
                    //JANコード
                    item.setJan(startParam[i].getString(JAN));
                    //ITFコード
                    item.setItf(startParam[i].getString(ITF));
                    //更新処理
                    itemControl.autoCreate(item, wmsUser);

                    if (!StringUtil.isBlank(startParam[i].getString(CUSTOMER_CODE)))
                    {
                        //出荷先マスタ更新処理
                        CustomerController customerControl = new CustomerController(getConnection(), this.getClass());
                        Customer customer = new Customer();

                        //荷主コード
                        customer.setConsignorCode(startParam[i].getString(CONSIGNOR_CODE));
                        //出荷先コード
                        customer.setCustomerCode(startParam[i].getString(CUSTOMER_CODE));
                        //出荷先名称
                        customer.setCustomerName(startParam[i].getString(CUSTOMER_NAME));
                        //更新処理
                        customerControl.autoCreate(customer, wmsUser);
                    }
                }

            }
            // 6001004=修正しました。
            setMessage("6001004");

            return true;
        }
        catch (LockTimeOutException e)
        {
            // 6023014=No.{0} 他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, startParam[i].getRowIndex()));
            setErrorRowIndex(startParam[i].getRowIndex());
            return false;
        }
        catch (DataExistsException e)
        {
            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, startParam[i].getRowIndex()));
            setErrorRowIndex(startParam[i].getRowIndex());
            return false;
        }
        catch (NotFoundException e)
        {
            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023015, startParam[i].getRowIndex()));
            setErrorRowIndex(startParam[i].getRowIndex());
            return false;
        }
        catch (NoPrimaryException e)
        {
            // 6006040=データの不整合が発生しました。{0}
            setMessage(WmsMessageFormatter.format(6006040, startParam[i].getRowIndex()));
            setErrorRowIndex(startParam[i].getRowIndex());
            return false;
        }
    }

    /**
     * 出庫予定データの削除を行います。<BR>
     * パラメータの内容をもとに、DNRETRIEVALPLANとDNWORKINFOの削除処理を行います。
     * @param startParam 表示データ取得条件を持つ<CODE>RetrievalInParameter</CODE>クラスのインスタンス。<BR>
     *         <CODE>RetrievalInParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
     * @throws CommonException  データベースとの接続で異常が発生した場合に通知されます。
     *                           チェック処理内で予期しない例外が発生した場合に通知されます。
     */
    protected void delete(ScheduleParams startParam)
            throws CommonException
    {

        WarenaviSystemController wmsControl = new WarenaviSystemController(getConnection(), getClass());
        // 在庫パッケージなしの場合のみ更新
        if (!wmsControl.hasStockPack())
        {
            //出庫作業情報Handler類のインスタンス生成
            WorkInfoHandler workInfoHandler = new WorkInfoHandler(getConnection());
            WorkInfoAlterKey workInfoAlterKey = new WorkInfoAlterKey();

            // 入出庫作業情報検索キーのセット
            // 予定一意キー
            workInfoAlterKey.setPlanUkey(startParam.getString(PLAN_U_KEY));
            // 入出庫作業情報更新項目のセット
            // 作業状態（9:削除）
            workInfoAlterKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
            // 最終更新処理名
            workInfoAlterKey.updateLastUpdatePname(getClass().getSimpleName());
            // 更新処理（論理削除）
            workInfoHandler.modify(workInfoAlterKey);
        }

        //出庫予定情報Handler類のインスタンス生成
        RetrievalPlanHandler retrievalPlanHandler = new RetrievalPlanHandler(getConnection());
        RetrievalPlanAlterKey retrievalPlanAlterKey = new RetrievalPlanAlterKey();

        //出庫予定情報検索キーのセット
        //予定一意キー
        retrievalPlanAlterKey.setPlanUkey(startParam.getString(PLAN_U_KEY));
        //出庫予定情報更新項目のセット
        //作業状態（9:削除）
        retrievalPlanAlterKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        //最終更新処理名
        retrievalPlanAlterKey.updateLastUpdatePname(getClass().getSimpleName());
        //更新処理（論理削除）
        retrievalPlanHandler.modify(retrievalPlanAlterKey);

        // 6001005=削除しました。
        setMessage("6001005");

    }

    /**
     * 出庫予定データの全削除を行います。<BR>
     * パラメータの内容をもとに、DNSTORAGEPLANとDNWORKINFOの削除処理を行います。
     * @param startParams 表示データ取得条件を持つ<CODE>RetrievalInParameter</CODE>クラスのインスタンス。<BR>
     *         <CODE>RetrievalInParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
     * @throws CommonException  データベースとの接続で異常が発生した場合に通知されます。
     *                           チェック処理内で予期しない例外が発生した場合に通知されます。
     */
    protected void allDelete(ScheduleParams[] startParams)
            throws CommonException
    {
        //出庫予定情報Handler類のインスタンス生成
        RetrievalPlanHandler retrievalPlanHandler = new RetrievalPlanHandler(getConnection());
        //入出庫作業情報Handler類のインスタンス生成
        WorkInfoHandler workInfoHandler = new WorkInfoHandler(getConnection());

        WarenaviSystemController wmsControl = new WarenaviSystemController(getConnection(), getClass());

        //出庫予定情報全削除
        for (ScheduleParams startParam : startParams)
        {
            // 在庫パッケージなしの場合のみ更新
            if (!wmsControl.hasStockPack())
            {
                WorkInfoAlterKey workInfoAlterKey = new WorkInfoAlterKey();

                // 入出庫作業情報検索キーのセット
                // 予定一意キー
                workInfoAlterKey.setPlanUkey(startParam.getString(PLAN_U_KEY));
                // 入出庫作業情報更新項目のセット
                // 作業状態（9:削除）
                workInfoAlterKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
                // 最終更新処理名
                workInfoAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                // 更新処理（論理削除）
                workInfoHandler.modify(workInfoAlterKey);
            }

            RetrievalPlanAlterKey retrievalPlanAlterKey = new RetrievalPlanAlterKey();

            //出庫予定情報検索キーのセット
            //予定一意キー
            retrievalPlanAlterKey.setPlanUkey(startParam.getString(PLAN_U_KEY));
            //出庫予定情報更新項目のセット
            //作業状態（9:削除）
            retrievalPlanAlterKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
            //最終更新処理名
            retrievalPlanAlterKey.updateLastUpdatePname(getClass().getSimpleName());
            //更新処理（論理削除）
            retrievalPlanHandler.modify(retrievalPlanAlterKey);
        }
        // 6001005=削除しました。
        setMessage("6001005");

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
