// $Id: ShortageOperator.java 6285 2009-12-01 10:48:02Z kishimoto $
package jp.co.daifuku.wms.retrieval.allocate;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.schedule.AsrsInParameter;
import jp.co.daifuku.wms.base.common.AbstractOperator;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.AllocatePriority;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.ShortageInfo;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.handler.db.DefaultDDBFinder;
import jp.co.daifuku.wms.handler.db.DirectDBFinder;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalInParameter;


/**
 * 出庫予定情報より欠品情報を作成します。<br>
 *
 *
 * @version $Revision: 6285 $, $Date: 2009-12-01 19:48:02 +0900 (火, 01 12 2009) $
 * @author  Y.Okamura
 * @author  Last commit: $Author: kishimoto $
 */
public class ShortageOperator
        extends AbstractOperator
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    /**
     * 引当パタンNo.
     */
    private String _pattern = null;

    /**
     * 欠品が発生したかのチェックを行う行の入力パラメータ
     */
    private RetrievalInParameter[] _inParams = null;

    /**
     * 現在処理中の行
     */
    private int _nowLow = -1;

    /**
     * 今回の処理に対する出庫開始単位キー
     */
    private String _startUnitKey;

    /**
     * 出庫開始日時
     */
    private Date _retrievalStartDate;

    /**
     * 出庫引当オペレータ
     */
    private RetrievalAllocateOperator _allocateOperator;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ
     * @param conn DBコネクション
     * @param inParams 欠品チェックを行う行データ
     * @param pattern 引当パタンNo.
     * @param startUnitKey 出庫開始単位キー
     * @param retrievalStartDate 出庫開始日時
     * @param caller 呼び出し元クラス
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    public ShortageOperator(Connection conn, RetrievalInParameter[] inParams, String pattern, String startUnitKey,
            Date retrievalStartDate, Class caller) throws CommonException
    {
        super(conn, caller);

        _inParams = inParams;
        _pattern = pattern;
        _startUnitKey = startUnitKey;
        _retrievalStartDate = retrievalStartDate;
        _allocateOperator = new RetrievalAllocateOperator(conn, pattern, caller);
    }
    
    /**
     * コンストラクタ
     * @param conn DBコネクション
     * @param inParams 欠品チェックを行う行データ
     * @param pattern 引当パタンNo.
     * @param startUnitKey 出庫開始単位キー
     * @param retrievalStartDate 出庫開始日時
     * @param modeCheck 引当ステーションのモードチェック（出庫モード、中断中チェック）を行うかどうか
     * @param caller 呼び出し元クラス
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    public ShortageOperator(Connection conn, RetrievalInParameter[] inParams, String pattern, String startUnitKey,
            Date retrievalStartDate, boolean modeCheck, Class caller) throws CommonException
    {
        super(conn, caller);

        _inParams = inParams;
        _pattern = pattern;
        _startUnitKey = startUnitKey;
        _retrievalStartDate = retrievalStartDate;
        _allocateOperator = new RetrievalAllocateOperator(conn, pattern, modeCheck, caller);
    }

    /**
     * コンストラクタ
     * @param conn DBコネクション
     * @param pattern 引当パタンNo.
     * @param startUnitKey 出庫開始単位キー
     * @param caller 呼び出し元クラス
     * @param retrievalStartDate 出庫開始日時
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    public ShortageOperator(Connection conn, String pattern, String startUnitKey, Date retrievalStartDate, Class caller) throws CommonException
    {
        super(conn, caller);
        _pattern = pattern;
        _startUnitKey = startUnitKey;
        _retrievalStartDate = retrievalStartDate;
    }

    /**
     * コンストラクタ
     * @param conn DBコネクション
     * @param settingUnitKey 設定単位キー
     * @param settingDate 設定日時
     * @param caller 呼び出し元クラス
     */
    public ShortageOperator(Connection conn, String settingUnitKey, Date settingDate, Class caller)
    {
        super(conn, caller);
        _startUnitKey = settingUnitKey;
        _retrievalStartDate = settingDate;
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 欠品情報作成処理を行います。<BR>
     * 出庫開始画面用の欠品情報作成処理です。
     * 画面より複数オーダが指定された場合、全てのオーダを通して欠品がいくつあるかを求めます。
     * 
     * @param finder 出庫予定情報
     * @param hasReplenishmentArea 引当パターンの補充エリア有無フラグ ture : 補充エリアあり false : 補充エリア無し
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    public void create(RetrievalPlanFinder finder, boolean hasReplenishmentArea)
            throws CommonException
    {
        // 現在の処理行を１つ進める
        _nowLow++;

        // 比較用変数
        String tempConsignor = "";
        String tempItem = "";
        String tempLotno = "";

        // アイテム、ロット毎の引当数の合計値
        int allocatedQtyItemLotUnit = 0;
        // アイテム毎の引当数の合計値
        int allocatedQtyItemUnit = 0;

        while (finder.hasNext())
        {
            // 予定情報は荷主、アイテム、ロットの昇順でソート(ロット指定ありの予定の方が先です)
            RetrievalPlan[] plans = (RetrievalPlan[])finder.getEntities(100);

            for (int i = 0; i < plans.length; i++)
            {
                // 荷主、商品が異がなる予定情報の場合、アイテム毎の引当数の累積をリセットする
                if (!plans[i].getConsignorCode().equals(tempConsignor) || !plans[i].getItemCode().equals(tempItem))
                {
                    allocatedQtyItemUnit = 0;
                }
                // 荷主、商品、ロットが異なる予定情報の場合、アイテム、ロット毎の引当数の累積をリセットする
                if (!plans[i].getConsignorCode().equals(tempConsignor) || !plans[i].getItemCode().equals(tempItem)
                        || !plans[i].getPlanLotNo().equals(tempLotno))
                {
                    allocatedQtyItemLotUnit = 0;
                }

                tempConsignor = plans[i].getConsignorCode();
                tempItem = plans[i].getItemCode();
                tempLotno = plans[i].getPlanLotNo();

                // 引当済数を求める
                // ロット指定なしの場合はgetShortageQty()で引当可能在庫を求める条件にロットを含めないため
                // ロット指定ありの在庫を2重引当してしまう可能性がある(予定はロット指定ありの方が先に処理される)
                // よってロット指定なしの予定の場合は荷主、商品毎の引当数合計値(totalQtyItemUnit)を引当済数とする
                // ロット指定ありの場合は荷主、商品、ロット毎の引当数合計値(totalQtyItemLotUnit)を引当済数とする
                int allocatedQty = StringUtil.isBlank(plans[i].getPlanLotNo()) ? allocatedQtyItemUnit
                                                                              : allocatedQtyItemLotUnit;
                // この予定に対する今回引当数を求める
                int assigningQty = getAssigningQty(plans[i]);
                // この予定に対し、今回引当を行っても欠品する数量
                int shortageQty =
                        getShortageQty(plans[i].getConsignorCode(), plans[i].getItemCode(), plans[i].getPlanLotNo(),
                                allocatedQty, assigningQty);

                if (shortageQty > 0)
                {
                    createShortage(shortageQty, plans[i], hasReplenishmentArea);
                }

                // 引当数の加算(欠品が発生した場合は、今回引当数から欠品数を引くことで実際に引当てた数量を求める)
                allocatedQtyItemLotUnit += (assigningQty - shortageQty);
                allocatedQtyItemUnit += (assigningQty - shortageQty);
            }
        }
    }

    /**
     * 欠品完了用の欠品情報の作成を行います。<BR>
     * 
     * @param shortageQty 欠品数
     * @param thisTimePlanQty 今回予定数
     * @param allocatedQty 引当済数
     * @param plan 出庫予定情報
     * @throws ReadWriteException データベースエラーが発生した場合に通知されます。
     * @throws DataExistsException 欠品情報が既に作成されていた場合に通知されます。
     */
    public void createShortageComplete(int shortageQty, int thisTimePlanQty, int allocatedQty, RetrievalPlan plan)
            throws ReadWriteException,
                    DataExistsException
    {
        ShortageInfo si = editShortageInfo(plan);
    
        // 今回予定数
        si.setThisTimePlanQty(thisTimePlanQty);
        // 在庫数
        si.setStockQty(0);
        // 引当済み数
        si.setAllocatedQty(allocatedQty);
        // 欠品フラグ
        si.setShortageFlag(ShortageInfo.SHORTAGE_FLAG_SHORTAGE_COMPLETE);
        // 欠品数
        si.setShortageQty(shortageQty);
        // 補充数
        si.setReplenishmentQty(0);
        // 補充区分(補充無し)
        si.setReplenishmentFlag(ShortageInfo.REPLENISHMENT_FLAG_OFF);
    
        new ShortageInfoHandler(getConnection()).create(si);
    }

    /**
     * 欠品発生時の作業判定が可能な作業は出庫するだった場合の欠品情報作成処理を行います。<BR>
     * FA版の出庫開始画面用の欠品情報作成処理です。<BR>
     * 
     * @param shortageQty 欠品数
     * @param thisTimePlanQty 今回予定数
     * @param allocatedQty 引当済数
     * @param plan 出庫予定情報
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    public void createForPossibleRetrieval(int shortageQty, int thisTimePlanQty, int allocatedQty, RetrievalPlan plan)
            throws CommonException
    {
        ShortageInfo si = editShortageInfo(plan);
    
        // 今回予定数
        si.setThisTimePlanQty(thisTimePlanQty);
        // 在庫数
        si.setStockQty(0);
        // 引当済み数
        si.setAllocatedQty(allocatedQty);
        // 欠品フラグ
        si.setShortageFlag(ShortageInfo.SHORTAGE_FLAG_SHORTAGE_NORMAL);
        // 欠品数
        si.setShortageQty(shortageQty);
        // 補充数
        si.setReplenishmentQty(0);
        // 補充区分(補充無し)
        si.setReplenishmentFlag(ShortageInfo.REPLENISHMENT_FLAG_OFF);
    
        new ShortageInfoHandler(getConnection()).create(si);
    }

    /**
     * 商品コード指定出庫用の欠品情報の作成を行います。
     * @param shortageQty 欠品数
     * @param param 
     * @throws CommonException 
     */
    public void createItemRetrievalShortage(int shortageQty, AsrsInParameter param) 
    		throws CommonException
    {
        ShortageInfo si = editShortageInfo(param);
        
        // 予定数
        si.setPlanQty(param.getRetrievalQty());
        // 今回予定数
        si.setThisTimePlanQty(param.getRetrievalQty());
        // 在庫数
        si.setStockQty(0);
        // 引当済み数
        si.setAllocatedQty(param.getRetrievalQty() - shortageQty);
        // ロットNo.
        si.setPlanLotNo(param.getLotNo());
        // 欠品フラグ
        si.setShortageFlag(ShortageInfo.SHORTAGE_FLAG_SHORTAGE_NORMAL);
        // 欠品数
        si.setShortageQty(shortageQty);
        // 補充数
        si.setReplenishmentQty(0);
        // 補充区分(補充無し)
        si.setReplenishmentFlag(ShortageInfo.REPLENISHMENT_FLAG_OFF);
    
        new ShortageInfoHandler(getConnection()).create(si);
    }

    /**
     * <pre>
     * 欠品数の取得を行います。
     * 欠品数は以下のように求めます。
     * ・(引当可能数 - 既に処理済オーダーの引当数 - 同一オーダーの引当数) &lt;= 0
     *     今回の引当要求数
     * ・(引当可能数 - 既に処理済オーダーの引当数 - 同一オーダーの引当数 - 今回の引当数) &lt; 0
     *     引当可能数 - 既に処理済オーダーの引当数 - 同一オーダーの引当数 - 今回の引当数の絶対値
     * </pre>
     * 
     * @param consignorCode 荷主コード
     * @param itemCode 商品コード
     * @param lotNo ロットNo.
     * @param allocatedQty オーダ内の、同一荷主、商品、ロットNo.での引当数の合計
     * @param assigningQty 出庫予定に対する今回引当数
     * @return 欠品数
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    protected int getShortageQty(String consignorCode, String itemCode, String lotNo, int allocatedQty, int assigningQty)
            throws CommonException
    {
        // 既に欠品情報があるかをチェック
        // あれば今回も欠品情報を作成する
        if (hasShortageInfo(consignorCode, itemCode, lotNo))
        {
            return assigningQty;
        }
        else
        {
            if (_nowLow >= _inParams.length)
            {
                throw new ScheduleException();
            }

            RetrievalPlan[] rps = null;
            // 既に処理済の行に、同一商品があるかをチェック
            // 同一商品があった場合は、その情報も加味して欠品数を求める
            // 在庫の引当可能数 
            //  - 既にチェック済オーダーの同一商品の引当要求数 
            //  - 今回の同一オーダー内で既に引当済の同一商品の引当数

            // 既にチェック済のオーダーの同一商品の引当数を求める　ここから↓↓↓↓
            int checkedQty = 0;
            for (int i = 0; i < _nowLow; i++)
            {
                RetrievalPlanSearchKey rpKey = new RetrievalPlanSearchKey();
                // set where 
                rpKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
                rpKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION, "!=");
                rpKey.setSchFlag(RetrievalPlan.SCH_FLAG_NOT_SCHEDULE);
                rpKey.setConsignorCode(consignorCode);
                rpKey.setItemCode(itemCode);
                rpKey.setPlanLotNo(lotNo);
                rpKey.setPlanDay(_inParams[i].getRetrievalPlanDay());
                rpKey.setBatchNo(_inParams[i].getBatchNo());
                rpKey.setOrderNo(_inParams[i].getOrderNo());
                // set collect
                rpKey.setPlanQtyCollect();
                rpKey.setPlanUkeyCollect();
                rps = (RetrievalPlan[])new RetrievalPlanHandler(getConnection()).find(rpKey);
                if (rps == null || rps.length == 0)
                {
                    continue;
                }

                for (int j = 0; j < rps.length; j++)
                {
                    WorkInfo[] wis = null;

                    WorkInfoSearchKey wiKey = new WorkInfoSearchKey();
                    // set where
                    wiKey.setPlanUkey(rps[j].getPlanUkey());
                    // set collect
                    wiKey.setPlanQtyCollect("SUM");
                    // find
                    wis = (WorkInfo[])new WorkInfoHandler(getConnection()).find(wiKey);
                    if (wis == null || wis.length == 0)
                    {
                        checkedQty += rps[j].getPlanQty();
                    }
                    else
                    {
                        checkedQty += rps[j].getPlanQty() - wis[0].getPlanQty();
                    }
                }
            }
            // 既にチェック済のオーダーの同一商品の引当数を求める ↑ここまで↑↑↑↑↑↑

            // 引当可能な在庫数を求める
            int allowableQty =
                    getAllowableQty(consignorCode, itemCode, lotNo, _pattern,
                            AllocatePriority.REPLENISHMENT_AREA_TYPE_NORMAL_AREA);

            // 引当可能数 - 既に処理済オーダーの引当数 - 同一オーダーの引当数
            // 今回の引当対象の予定情報以外ですでに欠品している場合は、今回の引当要求数を欠品数とする
            if ((allowableQty - checkedQty - allocatedQty) <= 0)
            {
                return assigningQty;
            }
            else
            {
                // 今回の引当対象の予定情報以外では欠品していない場合、さらに引当数を減算し
                // 0より小さい場合は、その絶対値を返し、それ以外は0を返す
                // 引当可能数 - 既に処理済オーダーの引当数 - 同一オーダーの引当数 - 今回の引当数
                if ((allowableQty - checkedQty - allocatedQty - assigningQty) < 0)
                {
                    // 絶対値を返す
                    return Math.abs(allowableQty - checkedQty - allocatedQty - assigningQty);
                }
                else
                {
                    return 0;
                }
            }
        }
    }

    /**
     * 指定されたパラメータに該当する在庫の引当可能数を求めます。<BR>
     * 
     * @param consignorCode 荷主
     * @param itemCode 商品
     * @param lotNo ロット
     * @param pattern 引当パターン
     * @param replenishmentAreaType 補充元エリア区分 
     * @return 引当可能数
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    protected int getAllowableQty(String consignorCode, String itemCode, String lotNo, String pattern,
            String replenishmentAreaType)
            throws CommonException
    {
        DirectDBFinder ddbfinder = null;
        
        try
        {
            ddbfinder = new DefaultDDBFinder(getConnection(), new Stock());
            
            _allocateOperator.searchStocks(ddbfinder, consignorCode, itemCode, lotNo, replenishmentAreaType);
    
            int result = 0;
            
            while (ddbfinder.hasNext())
            {
                Stock[] stks = (Stock[])ddbfinder.getEntities(100);
                for (Stock stock : stks)
                {
                    // 搬送ルートチェックがNGの場合はスルー
                    if (!_allocateOperator.canTransport(stock))
                    {
                        continue;
                    }
                    result += stock.getAllocationQty();
                }
            }
            return result;
        }
        finally
        {
            if (ddbfinder != null)
            {
                ddbfinder.close();
            }
        }
    }

    /**
     * 指定された荷主コード、商品コード、ロットNo.、インスタンス変数ので欠品情報が存在するかどうかを判定します。<BR>
     * 
     * @param consignorCode 荷主コード
     * @param itemCode 商品コード
     * @param lotNo ロットNo.
     * @return 同一の在庫を引当てる条件で、すでに欠品情報が存在するか否か
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    protected boolean hasShortageInfo(String consignorCode, String itemCode, String lotNo)
            throws ReadWriteException
    {
        // 同一バッチ内の欠品情報があるかを確認
        ShortageInfoSearchKey siKey = new ShortageInfoSearchKey();
        siKey.setConsignorCode(consignorCode);
        siKey.setItemCode(itemCode);
        siKey.setPlanLotNo(lotNo);
        siKey.setStartUnitKey(_startUnitKey);
        if (new ShortageInfoHandler(getConnection()).count(siKey) > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 欠品情報の作成を行います。<BR>
     * 欠品完了処理の場合は<code>createShortageComplete(int, int, int, RetrievalPlan)</code>を呼び出してください。
     * 
     * @param shortageQty 欠品数
     * @param plan 出庫予定情報
     * @param hasReplenishmentArea 引当パターンの補充エリア有無フラグ ture : 補充エリアあり false : 補充エリア無し
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    protected void createShortage(int shortageQty, RetrievalPlan plan, boolean hasReplenishmentArea)
            throws CommonException
    {
        ShortageInfo si = editShortageInfo(plan);

        // 今回予定数
        si.setThisTimePlanQty(getAssigningQty(plan));
        // 在庫数
        si.setStockQty(si.getThisTimePlanQty() - shortageQty);
        // 引当済み数
        si.setAllocatedQty(0);
        // 欠品フラグ
        si.setShortageFlag(ShortageInfo.SHORTAGE_FLAG_SHORTAGE_NORMAL);
        // 欠品数
        si.setShortageQty(shortageQty);
        // 補充数(補充を行わない場合はゼロを設定)
        si.setReplenishmentQty(hasReplenishmentArea ? shortageQty
                                                   : 0);
        // 補充区分(補充を行わない場合は補充無しで作成、補充する場合はとりあえず補充ありで作成(補充欠品が発生する場合などは後に必要に応じてUpdate))
        si.setReplenishmentFlag(hasReplenishmentArea ? ShortageInfo.REPLENISHMENT_FLAG_ON
                                                    : ShortageInfo.REPLENISHMENT_FLAG_OFF);

        new ShortageInfoHandler(getConnection()).create(si);
    }

    /**
     * 指定されたパラメータから欠品情報を編集して返します。
     * 
     * @param plan 出庫予定情報
     * @return 編集後の欠品情報
     */
    protected ShortageInfo editShortageInfo(RetrievalPlan plan)
    {
        ShortageInfo si = new ShortageInfo();

        // 予定一意キー
        si.setPlanUkey(plan.getPlanUkey());
        // 取込単位キー
        si.setLoadUnitKey(plan.getLoadUnitKey());
        // 出庫開始単位キー
        si.setStartUnitKey(_startUnitKey);
        /// 作業区分(出庫)
        si.setJobType(ShortageInfo.JOB_TYPE_RETRIEVAL);
        // 予定日
        si.setPlanDay(plan.getPlanDay());
        // 荷主コード
        si.setConsignorCode(plan.getConsignorCode());
        // 出荷先コード
        si.setCustomerCode(plan.getCustomerCode());
        // 出荷伝票No.
        si.setShipTicketNo(plan.getShipTicketNo());
        // 出荷伝票行No.
        si.setShipLineNo(plan.getShipLineNo());
        // 作業枝番
        si.setBranchNo(plan.getBranchNo());
        // バッチNo.
        si.setBatchNo(plan.getBatchNo());
        // オーダーNo.
        si.setOrderNo(plan.getOrderNo());
        // 予定エリア
        si.setPlanAreaNo(plan.getPlanAreaNo());
        // 予定棚
        si.setPlanLocationNo(plan.getPlanLocationNo());
        // 商品コード
        si.setItemCode(plan.getItemCode());
        // 予定ロット
        si.setPlanLotNo(plan.getPlanLotNo());
        // 予定数
        si.setPlanQty(plan.getPlanQty());
        // 引当パターン
        si.setAllocateNo(_pattern);
        // 出庫開始日時
        si.setRetrievalStartDate(_retrievalStartDate);
        // 登録処理名
        si.setRegistPname(getCallerName());
        // 最終更新処理名
        si.setLastUpdatePname(getCallerName());

        return si;
    }

    /**
     * 指定されたパラメータから欠品情報を編集して返します。
     * 
     * @param plan 出庫予定情報
     * @return 編集後の欠品情報
     */
    protected ShortageInfo editShortageInfo(AsrsInParameter param)
    {
        ShortageInfo si = new ShortageInfo();

        // 予定一意キー
        si.setPlanUkey("");
        // 出庫開始単位キー
        si.setStartUnitKey(_startUnitKey);
        // 作業区分(予定外出庫)
        si.setJobType(ShortageInfo.JOB_TYPE_NOPLAN_RETRIEVAL);
        // 荷主コード
        si.setConsignorCode(param.getConsignorCode());
        // 予定エリア
        si.setPlanAreaNo(param.getPlanAreaNo());
        // 商品コード
        si.setItemCode(param.getItemCode());
        // 予定ロット
        si.setPlanLotNo(param.getPlanLotNo());
        // 予定数
        si.setPlanQty(param.getPlanQty());
        // 出庫開始日時
        si.setRetrievalStartDate(_retrievalStartDate);
        // 登録処理名
        si.setRegistPname(getCallerName());
        // 最終更新処理名
        si.setLastUpdatePname(getCallerName());

        return si;
    }

    /**
     * 今回引当数を取得します。<BR>
     * 
     * @param plan 出庫予定情報
     * @return 今回引当数
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    protected int getAssigningQty(RetrievalPlan plan)
            throws ReadWriteException
    {
        return AbstractAllocateOperator.getAllocationQty(getConnection(), plan);
    }

//    /**
//     * 引当済数を取得します。<BR>
//     * 
//     * @param plan 出庫予定情報
//     * @return 引当済数を
//     * @throws CommonException 処理エラーが発生した場合にスローされます。
//     */
//    protected int getAllocatedQty(RetrievalPlan plan)
//            throws CommonException
//    {
//        WorkInfoSearchKey wiKey = new WorkInfoSearchKey();
//        wiKey.setPlanUkey(plan.getPlanUkey());
//        wiKey.setPlanQtyCollect("SUM");
//
//        WorkInfo workInfo = ((WorkInfo[])new WorkInfoHandler(getConnection()).find(wiKey))[0];
//
//        return workInfo.getPlanQty();
//    }
//
    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: ShortageOperator.java 6285 2009-12-01 10:48:02Z kishimoto $";
    }

}
