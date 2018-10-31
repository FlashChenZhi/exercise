// $Id: CarryCompleteOperator.java 8055 2013-05-24 10:17:10Z kishimoto $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights
 * Reserved.
 *
 * This software is the proprietary information of
 * DAIFUKU Co.,Ltd. Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.location.FreeAllocationShelfOperator;
import jp.co.daifuku.wms.asrs.location.ShelfOperator;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.common.AbstractOperator;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.AsStockController;
import jp.co.daifuku.wms.base.controller.AsWorkInfoController;
import jp.co.daifuku.wms.base.controller.HostSendController;
import jp.co.daifuku.wms.base.controller.StockController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.controller.AreaController.TEMPORARY_AREA_TYPE;
import jp.co.daifuku.wms.base.dbhandler.AisleAlterKey;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.ArrivalHandler;
import jp.co.daifuku.wms.base.dbhandler.ArrivalSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.InOutResultAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InOutResultHandler;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.InOutResult;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.ReStoringPlan;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.StockHistory;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 入出庫コントロールに関する処理を行うクラスが共通で使用するユーティリティークラスです。 <BR>
 * 搬送情報に伴う作業情報、AS/RS棚情報、パレット情報、在庫情報を更新します。<BR>
 * また、稼動実績を作成します。
 *
 * @version $Revision: 8055 $, $Date: 2013-05-24 19:17:10 +0900 (金, 24 5 2013) $
 * @author ssuzuki@softecs.jp
 * @author Last commit: $Author: kishimoto $
 */
public class CarryCompleteOperator
        extends AbstractOperator
{
    // ------------------------------------------------------------
    // fields (upper case only)
    // ------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * <jp> 実績作成を行なわない作業を<code>String</code>の配列に定義します。
     * 配列にセットする文字列にはInOuteResult</code>クラスで定義されている作業区分の内容をセットします。
     * デフォルトでは以下の作業をセットしています。追加が必要な場合はこの配列内に追加してください。
     * </jp>
     */
    private static final String[] NO_RESULT_OPERATIONS = {
    // 空棚確認の機能を実装する場合は、以下のコメントをコメントアウトしてください。
            // //<jp> 空棚確認</jp>
            // //<en> empty location check</en>
            // InOutResult.KIND_FREECHECK
            };

    /** 作業の完了区分 */
    public enum CARRY_COMPLETE
    {
        /** <code>NORMAL</code>(通常完了) */
        NORMAL(1),
        /** <code>SHORTAGE</code>(欠品完了) */
        SHORTAGE(0),
        /** <code>ERROR_SHORTAGE</code>(空出荷欠品完了) */
        ERROR_SHORTAGE(2),
        // 2010/06/24 Ohta ADD START
        /** <code>IRREGULAR</code>(異常) */
        IRREGULAR(3);
        // 2010/06/24 Ohta ADD END

        /**
         * パラメータのint表現を保持します。
         * @param value セットするint表現
         */
        CARRY_COMPLETE(int value)
        {
            _value = value;
        }

        /** int表現を保持します */
        private int _value;

        /**
         * int表現を返します。
         *
         * @return int表現を返します。
         */
        public int getValue()
        {
            return _value;
        }
    };

    // ------------------------------------------------------------
    // class variables (prefix '$')
    // ------------------------------------------------------------
    // private static String $classVar ;

    // ------------------------------------------------------------
    // instance variables (prefix '_')
    // ------------------------------------------------------------
    // Handlers (create instence in prepare() at
    // construct)
    private CarryInfoHandler _ciH = null;

    private PalletHandler _plH = null;

    private WorkInfoHandler _wiH = null;

    private StockHandler _stockH = null;

    private ShelfHandler _shelfH = null;

    private StoragePlanHandler _splanH = null;

    private RetrievalPlanHandler _rplanH = null;

    private ReStoringPlanHandler _rsplanH = null;

    private AisleHandler _aisleH = null;

    private AsStockController _stockCtlr;

    private InOutResultHandler _inoutResultH;

    private MoveWorkInfoHandler _mwiH = null;

    // controllers (create instace in getter)
    private WarenaviSystemController _warenaviSysCtlr;

    private AreaController _areaCtlr;


    // ------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------
    /**
     * データベースコネクションと呼び出し元クラスを指定して インスタンスを生成します。
     *
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     * @throws ReadWriteException
     * @throws ScheduleException
     */
    public CarryCompleteOperator(Connection conn, Class caller)
            throws ReadWriteException,
                ScheduleException
    {
        super(conn, caller);
        prepare();
    }

    /**
     * ハンドラの生成などインスタンスの初期化を行います。
     *
     * @throws ReadWriteException
     * @throws ScheduleException
     */
    private void prepare()
            throws ReadWriteException,
                ScheduleException
    {
        Connection conn = getConnection();
        _warenaviSysCtlr = getWarenaviSysCtlr();

        _ciH = new CarryInfoHandler(conn);
        _plH = new PalletHandler(conn);
        _wiH = new WorkInfoHandler(conn);
        _stockH = new StockHandler(conn);
        _shelfH = new ShelfHandler(conn);
        _rplanH = new RetrievalPlanHandler(conn);
        _inoutResultH = new InOutResultHandler(conn);
        _aisleH = new AisleHandler(conn);
        _rsplanH = new ReStoringPlanHandler(conn);

        // DA版の場合のみ生成を行う
        if (!_warenaviSysCtlr.isFaDaEnabled())
        {
            _splanH = new StoragePlanHandler(conn);
            _mwiH = new MoveWorkInfoHandler(conn);
        }
    }

    // ------------------------------------------------------------
    // public methods
    // ------------------------------------------------------------
    /**
     * <jp> 搬送データのユニット出庫在庫更新処理を行います。
     * ciに含まれるCarryInformation、WorkInformation、Pallet、Stockをデータベースから削除し、実績データの作成を行います。
     * 作業データに含まれない在庫については、通常の実績とは別に、強制払出しの区分で実績が作成されます。
     * 又、isRestoringがtrueの場合に在庫残があるときは、再入庫予定データを作成します。
     *
     * @param ci 搬送データ
     * @param isRestoring
     *        trueの場合は作業データに含まれない在庫より再入庫予定データを作成する。<br>
     *        falseの場合は再入庫予定データを作成しない。
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         削除すべきデータが見つからない場合に通知されます。
     * @throws InvalidDefineException
     *         データベースの更新条件に不整合があった場合に通知されます。
     */
    public void unitRetrieval(CarryInfo ci, boolean isRestoring)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        try
        {
            // ユニット出庫かチェックし、そうでなければ強制払い出しとする
            boolean isUnitRetrieval = checkUnitRetrieval(ci);
            String workType = (isUnitRetrieval) ? ci.getWorkType()
                                               : InOutResult.WORK_TYPE_EXPENDITURE;

            // 再入庫予定データ作成を指定された場合のみ作成処理を行う
            if (isRestoring)
            {
                // 強制払出しかつ搬送区分:入庫でない(荷姿不一致でリジェクトST払出しなど)とき
                if (!isUnitRetrieval && !CarryInfo.CARRY_FLAG_STORAGE.equals(ci.getCarryFlag()))
                {
                    // FAのみ対応とする
                    if (getWarenaviSysCtlr().isFaDaEnabled())
                    {
                        insertReStoringData(ci);
                    }
                }
            }

            drop(ci, workType, true, CARRY_COMPLETE.NORMAL);
        }
        catch (ScheduleException e)
        {
            throw new InvalidDefineException();
        }
    }

    /**
     * <jp>
     * 搬送データの在庫更新処理を行います。CarryInformation内の作業情報を元にStockの更新を行います。
     * CarryInformation内で保持するWorkInformationの入出庫区分、作業数を元にStock内の在庫数の加算、減算を行います。
     * 在庫数の更新の結果、在庫数が0以下になった場合はStockを削除します。
     * 作業情報がStock内に見つからない場合は、詰合入庫データと判断し新規にStockの追加を行います。
     * CarryInformation内の全てのWorkInformation更新後、
     * Stockが全て削除された場合は同じパレットIDで空パレットデータを生成します。
     *
     * @param ci 在庫更新の対象となるCarryInformationインスタンス
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         更新、削除すべきデータが見つからない場合に通知されます。
     * @throws InvalidDefineException
     *         更新条件に不整合があった場合に通知されます。
     * @throws InvalidStatusException
     *         検索条件の内容が範囲外であった場合に通知します。 </jp>
     */
    public void updateStock(CarryInfo ci)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException,
                InvalidStatusException
    {
        Connection conn = getConnection();

        PalletHandler plh = _plH;

        WorkInfoHandler wih = _wiH;
        WorkInfoSearchKey wiKey = new WorkInfoSearchKey();

        // 搬送情報から、作業情報とパレット情報を取得する
        wiKey.setSystemConnKey(ci.getCarryKey());
        WorkInfo[] works = (WorkInfo[])wih.find(wiKey);

        Pallet pl = getPallet(ci.getPalletId());

        // <jp> 搬送データの入出庫区分のチェックを行う。区分が入庫又は直行の場合に</jp>
        // <jp>
        // 出庫指示詳細に以下の値がセットされていれば戻り入庫データと判断し、在庫更新は行わない。</jp>
        // <jp> 0.在庫確認</jp>
        // <jp> 1.ユニット出庫</jp>
        // <jp> 2.ピッキング出庫</jp>
        // <jp> 3.積増入庫</jp>
        // <en> Checks the In/Out classificaiton in
        // Carry data. If the classification is either
        // 'storage' or</en>
        // <en> 'direct travel', and if following
        // values are set in retrieval instruction
        // detail, it determines</en>
        // <en> the data is the returned storage data
        // and does not update stocks.</en>
        // <en> 0.inventory check</en>
        // <en> 1.unit retrieval</en>
        // <en> 3.replenishment storage</en>

        boolean needUpdate = true;

        String retrDetail = ci.getRetrievalDetail();
        String carryFlag = ci.getCarryFlag();
        if ((CarryInfo.CARRY_FLAG_STORAGE.equals(carryFlag)))
        {
            if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(retrDetail)
                    || CarryInfo.RETRIEVAL_DETAIL_UNIT.equals(retrDetail)
                    || CarryInfo.RETRIEVAL_DETAIL_PICKING.equals(retrDetail)
                    || CarryInfo.RETRIEVAL_DETAIL_ADD_STORING.equals(retrDetail))
            {
                needUpdate = false;
            }
        }
        else if (CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(carryFlag))
        {
            needUpdate = false;

            // 直行の場合在庫の更新だけを行う
            for (WorkInfo work : works)
            {
                updateStock(work, carryFlag, CARRY_COMPLETE.NORMAL, ci);
            }
        }

        if (needUpdate)
        {
            // 実績記録が必要な作業の場合、実績データを作成する
            if (isResultOperation(ci.getWorkType()))
            {
                insertInOutResult(ci, pl, ci.getWorkType());
            }

            String resultLocNo = "";

            // 在庫確認の時は出庫ステーションNoから結果ロケーションNo.を取得する。
            if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals((ci.getRetrievalDetail())))
            {
                resultLocNo = ci.getRetrievalStationNo();
            }
            else
            {
                String jobType = works[0].getJobType();
                // 入庫（積増以外）の作業情報を完了に更新し、実績を作成する

                boolean isNewStorage =
                        (WorkInfo.JOB_TYPE_STORAGE.equals(jobType) || WorkInfo.JOB_TYPE_NOPLAN_STORAGE.equals(jobType) || WorkInfo.JOB_TYPE_RESTORING.equals(jobType))
                                && (!CarryInfo.RETRIEVAL_DETAIL_ADD_STORING.equals(retrDetail));

                // 入庫の場合（積増以外）は、搬送先ステーションを実績におとす
                // 出庫または積増入庫の作業情報を完了に更新し、実績を作成する
                resultLocNo = (isNewStorage) ? ci.getDestStationNo()
                                            : ci.getSourceStationNo();
            }

            // 出庫または積増入庫の作業情報を完了に更新し、実績を作成する
            completeWork(ci, carryFlag, resultLocNo, CARRY_COMPLETE.NORMAL);

            // パレットIDに紐付く在庫がない場合、空パレットに置き換える
            if (!isExistStock(pl.getPalletId()))
            {
                insertEmptyPallet(conn, pl);
            }
        }

        // 以下、空パレットに在庫・パレットを積み増したことを考慮しての処理です
        // 在庫数が1で、商品コードが空パレットの場合
        StockHandler stkh = _stockH;

        StockSearchKey stkKey = new StockSearchKey();

        stkKey.clear();
        stkKey.setPalletId(pl.getPalletId());
        stkKey.setStockQty(1, ">=");

        Stock[] stocks = (Stock[])stkh.find(stkKey);

        if (ArrayUtil.isEmpty(stocks))
        {
            // no stocks for the pallet, exit this
            // method
            return;
        }

        Stock fstStock = stocks[0];
        if (1 == stocks.length && AsStockController.isEmptyPB(fstStock))
        {
            String empFlag = (fstStock.getStockQty() > 1) ? Pallet.EMPTY_FLAG_NORMAL // 空パレットの段積み
                                                         : Pallet.EMPTY_FLAG_EMPTY; // 空パレット
            updatePalletEmpty(pl.getPalletId(), empFlag);
        }
        else
        {
            // <jp> 複数の在庫がパレットに混載されているとき。</jp>
            // <en> If stocks are mixed and loaded in
            // the pallet,</en>
            for (Stock stock : stocks)
            {
                // 空パレット用在庫のほかに、通常在庫があるはずなので
                // 空パレット用在庫を削除する
                if (AsStockController.isEmptyPB(stock))
                {
                    if (1 == stock.getStockQty())
                    {
                        // Stockを削除する
                        stkKey.clear();
                        stkKey.setStockQty(1, ">=");
                        stkKey.setStockId(stock.getStockId());
                        stkh.drop(stkKey);

                        String palletId = stock.getPalletId();
                        // 今、削除した在庫以外に混載している在庫がない場合、パレットを削除する
                        if (!isExistStock(palletId))
                        {
                            PalletSearchKey plKey = new PalletSearchKey();
                            plKey.setPalletId(palletId);
                            plh.drop(plKey);
                        }
                        else
                        {
                            // <jp>
                            // パレットの状態を通常パレットにする。</jp>
                            // <en> Alters the pallet
                            // status to 'normal'.</en>
                            updatePalletEmpty(pl.getPalletId(), Pallet.EMPTY_FLAG_NORMAL);
                        }
                        break;
                    }
                    else
                    {
                        // <jp> パレットの状態を通常パレットにする。</jp>
                        // <en> Alters the pallet
                        // status to 'normal'.</en>
                        updatePalletEmpty(pl.getPalletId(), Pallet.EMPTY_FLAG_NORMAL);
                    }
                }
            }
        }
    }

    /**
     * <jp> 搬送データ削除処理を行います。
     * ciに含まれるCarryInformation、WorkInformation、Pallet、Stockをデータベースから削除します。
     * 作業情報は欠品完了します。
     * 実績データの作成を行う場合，実績データの作業種別は"搬送データ削除"で作成されます。
     *
     * @param ci 搬送データ
     * @param needResult 実績データの作成の有無 true:実績を作成する
     *        false:実績を作成しない
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         削除すべきデータが見つからない場合に通知されます。
     * @throws InvalidDefineException
     *         データベースの更新条件に不整合があった場合に通知されます。
     */
    public void drop(CarryInfo ci, boolean needResult)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        // <jp> 作業区分に搬送データ削除の区分をセットし、搬送データを削除する。</jp>
        // <en> Sets the 'Delete Carry data' to the job
        // type and deletes the Carry data.</en>
        drop(ci, InOutResult.WORK_TYPE_CARRYINFODELETE, needResult, CARRY_COMPLETE.SHORTAGE);
    }

    /**
     * <jp> 搬送データ削除処理を行います。
     * 搬送データの削除、紐づく作業情報、在庫情報、パレット情報の更新を行います。
     * 実績データの作成を行う場合の作業種別については、worktypeで指示された値を使用します。
     * 強制払い出し時にマルチ引き当て在庫が存在する場合、作業メンテナンスリストを出力します。
     * CarryInromationが在庫確認データの場合は、他に在庫確認データが同じアイルに存在しない場合、
     * 在庫確認中フラグを在庫確認未作業にします。 在庫情報、パレット情報、作業表示情報を削除します。
     *
     * @param ci 搬送データ
     * @param workType 作業種別
     * @param needResult 実績データの作成の有無 true:実績を作成する
     *        false:実績を作成しない
     *
     * @param compDivision 作業完了区分（欠品完了か通常完了か空出荷か）
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         更新、削除すべきデータが見つからない場合に通知されます。
     * @throws InvalidDefineException
     *         データベースの更新条件に不整合があった場合に通知されます。
     */
    public void drop(CarryInfo ci, String workType, boolean needResult, CARRY_COMPLETE compDivision)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        CarryInfoHandler ciH = _ciH;
        WorkInfoHandler wiH = _wiH;
        // 在庫保持フラグ（TRUE:在庫を保持する、FALSE:在庫を削除する）
        boolean isKeepStock = ((CARRY_COMPLETE.ERROR_SHORTAGE == compDivision && !WmsParam.EMPTY_RETRIEVAL_DELETE_STOCK));

        // '09/06/17 積み増し入庫データが作業メンテナンスで「引当取り消し」した時の対応を追加 (ここから)
        if (CarryInfo.RETRIEVAL_DETAIL_ADD_STORING.equals(ci.getRetrievalDetail()))
        {
            // 積み増しで「引当取り消し」ならば、在庫保持フラグをTRUE（在庫を保持する）にする
            isKeepStock = (CarryInfo.CANCEL_REQUEST_RELEASE.equals(ci.getCancelRequest())) ? true
                                                                                          : false;
        }
        // 作業区分が入庫作業
        boolean isStorage =
                (CarryInfo.WORK_TYPE_STORAGE.equals(ci.getWorkType())
                        || CarryInfo.WORK_TYPE_NOPLAN_STORAGE.equals(ci.getWorkType()) || CarryInfo.WORK_TYPE_RESTORING.equals(ci.getWorkType()));
        boolean createStock = false;
        if (isStorage)
        {
            // 作業区分が入庫作業の場合、搬送状態が指示済み移行か
            createStock =
                    (CarryInfo.CMD_STATUS_INSTRUCTION.equals(ci.getCmdStatus()) || CarryInfo.CMD_STATUS_PICKUP.equals(ci.getCmdStatus()));
        }
        // 搬送状態、搬送区分によって実績送信情報作成タイミングを変更
        boolean creatHostSendFlag = false;

        Connection conn = getConnection();
        // 搬送データに紐付くパレットを取得する
        Pallet pl = getPallet(ci.getPalletId());
        // 搬送データに紐付く作業情報を取得する
        WorkInfoSearchKey wiKey = new WorkInfoSearchKey();

        String carryKey = ci.getCarryKey();
        String carryFlag = ci.getCarryFlag();
        String carryWorkType = ci.getWorkType();

        wiKey.setSystemConnKey(carryKey);
        WorkInfo[] works = (WorkInfo[])wiH.find(wiKey);
        // build user info
        String userId = works[0].getUserId();
        String termNo = works[0].getTerminalNo();
        WmsUserInfo ui = WmsUserInfo.buildUserInfo(conn, userId, SystemDefine.HARDWARE_TYPE_ASRS, termNo);

        HostSendController hsc = new HostSendController(conn, getCaller());


        // 搬送データ削除又は強制払出しの場合、実績送信情報を作成
        if (isStorage
                && !createStock
                && (SystemDefine.WORK_TYPE_EXPENDITURE.equals(workType) || SystemDefine.WORK_TYPE_CARRYINFODELETE.equals(workType)))
        {
            for (WorkInfo work : works)
            {
                work.setJobType(workType);
                try
                {
                    hsc.insertByWorkInfo(work, workType);
                }
                catch (Exception e)
                {
                    throw new ReadWriteException(e);
                }
            }
            creatHostSendFlag = true;
        }

        // 在庫を削除する場合のみShelfを更新する
        if (!isKeepStock)
        {
            // 搬送元または搬送先の棚の状態を更新する
            updateShelf(conn, ci, pl.getCurrentStationNo());
        }
        else
        {
            if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(carryFlag))
            {
                // 棚間移動データの場合は、搬送先の棚を空棚にする。
                updateShelStatusFlagForDirect(conn, ci.getDestStationNo());
            }
        }
        if (CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(carryFlag))
        {
            if (needResult)
            {
                insertInOutResult(ci, pl, workType);
            }
            // 搬送情報の作業情報の完了、在庫の更新処理を行います。
            // 作業に対する実績送信情報を作成し、紐付く予定情報の更新を行います。
            String resultLocNo = ci.getDestStationNo();

            // 作業情報を完了し、実績を作成します。
            for (WorkInfo work : works)
            {
                completeWorkinfo(work, resultLocNo, compDivision);
            }
        }
        else if (isCompletableWork(carryKey))
        {
            if (needResult && isResultOperation(carryWorkType))
            {
                insertInOutResult(ci, pl, workType);
            }
            // 搬送情報の作業情報の完了、在庫の更新処理を行います。
            // 作業に対する実績送信情報を作成し、紐付く予定情報の更新を行います。
            String resultLocNo = "";
            if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals((ci.getRetrievalDetail())))
            {
                // 在庫確認の時は出庫ステーションNoから結果ロケーションNo.を取得する。
                resultLocNo = ci.getRetrievalStationNo();
            }
            else if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(carryFlag)
                    || CarryInfo.CARRY_FLAG_STORAGE.equals(carryFlag))
            {
                // 棚間移動又は、入庫の時は搬送先（移動先）ステーションから結果ロケーションNo.を取得する。
                resultLocNo = ci.getDestStationNo();
            }
            else
            {
                // 搬送元ステーションから結果ロケーションNo.を取得する。
                resultLocNo = ci.getSourceStationNo();
            }
            // 入庫作業で搬送状態が応答待ち以前の場合、未作業状態に戻す。
            if (isStorage && !createStock)
            {
                undoneStorage(ci);
            }
            else
            {
                completeWork(ci, carryFlag, resultLocNo, compDivision);
            }
        }
        else
        {
            // 搬送区分が"入庫"搬送状態が"指示済み"の場合、入庫を完了扱いとする。マルチ引当分の減算を行った後払出し削除とする。
            if (isStorage && createStock)
            {

                ShelfOperator sop = new ShelfOperator(conn, ci.getDestStationNo());
                // 入庫完了
                // 実際の完了処理
                // 入庫棚の予約を解除し、在荷をONにする。
                sop.alterPresence(Shelf.LOCATION_STATUS_FLAG_STORAGED);

                // パレットの更新
                PalletAlterKey palkey = new PalletAlterKey();
                palkey.setPalletId(ci.getPalletId());
                palkey.updateCurrentStationNo(ci.getDestStationNo());
                palkey.updateLastUpdatePname(getClass().getSimpleName());
                _plH.modify(palkey);
            }
            else
            {
                // "完了済み"作業の出庫指示詳細が "積増入庫"または "指定なし" で、あれば、
                // 作業情報と 実績送信情報を書き換える
                if (CarryInfo.RETRIEVAL_DETAIL_ADD_STORING.equals(ci.getRetrievalDetail())
                        || CarryInfo.RETRIEVAL_DETAIL_UNKNOWN.equals(ci.getRetrievalDetail()))
                {
                    AsWorkInfoController wic = new AsWorkInfoController(getConnection(), getCaller());

                    AreaController areaC = getAreaCtlr();
                    try
                    {
                        String areaNo = areaC.getAreaNoOfWarehouse(pl.getWhStationNo());
                        // 仮置在庫作成時は仮置棚を実績にセット
                        if (TEMPORARY_AREA_TYPE.CREATE_TMP_ANY == areaC.getTemporaryAreaType(areaNo))
                        {
                            String tmpAreaNo = areaC.getTemporaryArea(areaNo);
                            wic.updateResultLocation(works, "", tmpAreaNo, WmsParam.DEFAULT_LOCATION_NO);
                            hsc.updateResultLocation(works, "", tmpAreaNo, WmsParam.DEFAULT_LOCATION_NO);
                        }
                        // 仮置在庫を作成しない時は欠品完了
                        else
                        {
                            // 作業情報の実績数 <-> 欠品数 入れ替え
                            wic.updateToShortage(works);
                            // 作業に関連する予定情報を更新
                            Set<String> puKeySet = new HashSet<String>();
                            for (WorkInfo work : works)
                            {
                                String puKey = work.getPlanUkey();
                                String jobType = work.getJobType();

                                // check already processed Plan UKEY?
                                if (!puKeySet.contains(puKey))
                                {
                                    // save plan ukey to check
                                    // processed
                                    puKeySet.add(puKey);
                                    // 予定情報の更新
                                    updatePlan(puKey, jobType);
                                }

                            }
                            // HostSendの実績数 <-> 欠品数 入れ替え
                            hsc.updateToShortage(works);
                        }
                    }
                    catch (Exception e)
                    {
                        throw new ReadWriteException(e);
                    }
                }
                // InOutResult(KEY: CARRY_KEY)
                // の強制払い出しフラグを変更
                inOutResultToShortage(ci, workType);
            }
        }

        // 搬送データを削除する
        CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
        ciKey.clear();
        ciKey.setCarryKey(carryKey);

        ciH.drop(ciKey);

        // 実績送信情報の報告フラグを未報告に更新
        hsc.updateReportFlag(ci);

        // 不要な到着情報を削除
        dropArrivalWithCarryKey(ci.getCarryKey());

        // <jp> マルチ引き当てのときに強制払い出しや、トラッキング削除を行う時には</jp>
        // <jp> 同一パレットIDのCarryInformationを削除する</jp>
        // <jp> ＜処理内容＞</jp>
        // <jp> １．元のCarryInformationからパレットIDと搬送キーを取得</jp>
        // <jp> ２．CarryInfo表をパレットIDと搬送キーをキーに検索し、異なる搬送キーの同一パレットが存在するか確認</jp>
        // <jp> ３．存在する場合ログを書き込み、マルチ引当の搬送データ毎に４.以降の処理を行う。</jp>
        // <jp> ４．削除対象作業に対して作業メンテナンスリストを出力</jp>
        // <jp> ５．搬送データに紐づく作業情報、在庫情報、予定情報の更新</jp>
        // <jp> ６．搬送データの削除、</jp>
        // <jp> ７．ASInventoryCheck表の状態更新</jp>
        // 同一パレットへのマルチ引当てのデータを削除する
        // 同一パレットIDで今回作業以外の搬送データを取得する
        ciKey.clear();
        ciKey.setPalletId(pl.getPalletId());
        ciKey.setCarryKey(carryKey, "!=");
        // マルチ引当だった場合
        if (ciH.count(ciKey) > 0)
        {

            // マルチ引当ての搬送データの削除、搬送データに紐づく作業情報、在庫情報、予定情報の更新を行う
            ciKey.setCarryKeyOrder(true);
            CarryInfo[] cinfos = (CarryInfo[])ciH.find(ciKey);

            // データの更新を行なう
            for (int i = 0; i < cinfos.length; i++)
            {

                CARRY_COMPLETE division =
                        (CARRY_COMPLETE.ERROR_SHORTAGE == compDivision) ? CARRY_COMPLETE.ERROR_SHORTAGE
                                                                       : CARRY_COMPLETE.NORMAL;
                completeWork(cinfos[i], cinfos[i].getCarryFlag(), cinfos[i].getSourceStationNo(), division);

                // 搬送データを削除する
                ciKey.clear();
                ciKey.setCarryKey(cinfos[i].getCarryKey());
                ciH.drop(ciKey);
                // <jp>
                // 削除された搬送データのスケジュールNoをキーにCarryInfoを検索し</jp>
                // <jp>
                // 同一スケジュールNoの在庫確認又は空棚確認搬送データが無ければ</jp>
                // <jp>
                // そのスケジュールNoのInventoryCheck表のStatusを未作業に変更します。</jp>
                // アイルに在庫確認又は空棚確認搬送データが無ければ、Aisle表のinventory_check_flagを未作業に変更します。</jp>
                // <en> Conduct search in Carry Info based on the schedule No. of deleted carry data as a key.</en>
                // <en> check to see if the carry data for inventory check or empty location check exist in the same schedule No. </en>
                // <en> If there is no data, change the status of InventoryCheck table of that schedule No. to "Undone".</en>
                updateInventoryCheckInfo(cinfos[i]);

                // 実績送信情報の報告フラグを未報告に更新
                hsc.updateReportFlag(cinfos[i]);
            }
        }

        // 実績送信情報が未作成の場合作成する
        if (!creatHostSendFlag)
        {
            // 搬送データ削除又は強制払出しの場合、実績送信情報を作成
            if (SystemDefine.WORK_TYPE_EXPENDITURE.equals(workType)
                    || SystemDefine.WORK_TYPE_CARRYINFODELETE.equals(workType))
            {
                for (WorkInfo work : works)
                {
                    work.setJobType(workType);
                    try
                    {
                        hsc.insertByWorkInfo(work, workType);
                    }
                    catch (Exception e)
                    {
                        throw new ReadWriteException(e);
                    }
                }
            }
        }
        // 2010/06/24 Ohta MODIFY START
        if ((isStorage && createStock) && CARRY_COMPLETE.IRREGULAR != compDivision)
        // 2010/06/24 Ohta MODIFY END
        {
            ShelfOperator sop = new ShelfOperator(conn, ci.getDestStationNo());
            // マルチ引当処理後、AS/RS棚状態を空棚に変更する
            sop.alterPresence(Shelf.LOCATION_STATUS_FLAG_EMPTY);
        }

        // パレット更新
        // 在庫を保持する場合は実棚に更新、削除する場合はパレット上在庫およびパレット削除
        updatePallet(workType, pl, ui, isKeepStock, ci.getWorkType());

        // 作業表示データがあれば削除する
        dropOperationDisplay(conn, carryKey, carryFlag);

        // 在庫確認、空棚確認に関係するテーブルをチェック・更新します。
        updateInventoryCheckInfo(ci);

    }

    /**<jp>
     * ステーションのCARRYKEY、荷姿情報、BCデータをクリアします。
     * 搬送指示応答の受信、データキャンセルなどで到着情報が不要になった場合に使用されます。
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      削除すべきデータが見つからない場合に通知されます。
     </jp>*/
    public void dropArrivalWithCarryKey(String carrykey)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        ArrivalSearchKey skey = new ArrivalSearchKey();
        skey.setCarryKey(carrykey);
        ArrivalHandler handl = new ArrivalHandler(getConnection());
        // 到着情報が存在する場合のみ
        if (handl.count(skey) > 0)
        {
            handl.drop(skey);
        }
    }


    /**
     * パレットの更新を行います。
     * 在庫を保持する場合は実棚に更新、削除する場合はパレット上在庫およびパレットを削除します。
     *
     * @param workType     作業種別
     * @param pl           パレットデータ
     * @param ui           ユーザ情報
     * @param isKeepStock  在庫保持フラグ（TRUE:在庫を保持する、FALSE:在庫を削除する）
     * @param inoutWorkType 稼動実績の作業区分
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         対象となるStockがデータベースに見つからなかった場合に通知されます。
     */
    private void updatePallet(String workType, Pallet pl, WmsUserInfo ui, boolean isKeepStock, String inoutWorkType)
            throws ReadWriteException,
                NotFoundException
    {
        StockHandler stkH = _stockH;
        PalletHandler plH = _plH;

        try
        {
            // 在庫を保持する場合は実棚に更新する
            if (isKeepStock)
            {
                PalletAlterKey plAKey = new PalletAlterKey();
                plAKey.updateStatusFlag(Pallet.PALLET_STATUS_REGULAR);
                plAKey.updateAllocationFlag(Pallet.ALLOCATION_FLAG_NOT_ALLOCATED);
                plAKey.setPalletId(pl.getPalletId());
                plAKey.updateLastUpdatePname(getCallerName());

                plH.modify(plAKey);
            }
            // 在庫を削除する場合はパレット上在庫およびパレットを削除する
            else
            {
                AsStockController asstockCtlr = new AsStockController(getConnection(), getCaller());

                // get pallet
                PalletSearchKey plKey = new PalletSearchKey();
                plKey.setPalletId(pl.getPalletId());

                Pallet pallet = (Pallet)plH.findPrimaryForUpdate(plKey);

                // get stocks for this pallet
                StockSearchKey stkKey = new StockSearchKey();
                if (!CarryInfo.WORK_TYPE_DIRECT_TRAVEL.equals(inoutWorkType))
                {
                    stkKey.setStockQty(0, ">");
                }
                stkKey.setPalletId(pl.getPalletId());

                Stock[] stocks = (Stock[])stkH.findForUpdate(stkKey);

                for (Stock stock : stocks)
                {
                    // 空出荷の場合はメンテ減
                    boolean isErrorShort = (CarryInfo.WORK_TYPE_EMPTYRETRIEVAL.equals(workType));
                    String jobType = (isErrorShort) ? StockHistory.JOB_TYPE_MAINTENANCE_MINUS
                                                   : workType;
                    // 簡易直行作業フラグ
                    boolean isSimpleDirect =
                            CarryInfo.WORK_TYPE_DIRECT_TRAVEL.equals(workType)
                                    && WmsParam.SIMPLEDIRECTTRANSFER_ITEMCODE.equals(stock.getItemCode()) ? true
                                                                                                         : false;
                    // 作業以外の在庫を削除
                    // 作業以外の在庫を削除 （在庫数>0かつ簡易直行以外の場合のみ在庫更新履歴を登録する）
                    String incType =
                            (stock.getStockQty() > 0 && !(isSimpleDirect)) ? StockHistory.INC_DEC_TYPE_STOCK_DECREMENT
                                                                          : null;

                    // remove each stock (W/O work) for this palette
                    // パレットの削除はすべての在庫削除が終わった後で行います
                    asstockCtlr.delete(stock, null, incType, jobType, inoutWorkType, ui);
                }

                // パレットが空になっていれば削除
                asstockCtlr.deleteEmptyPallet(pallet);
            }
        }
        catch (RuntimeException e)
        {
            throw e;
        }
        catch (OperatorException e)
        {
            if (OperatorException.ERR_OVERFLOW.equals(e.getErrorCode()))
            {
                // <jp> 6006124=オーバーフローが発生しました。{0}
                RmiMsgLogClient.write(new TraceHandler(6006002, e), "CarryCompleteOperator");
            }
            throw new NotFoundException();
        }
        catch (Exception e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * CARRY_KEYをキーとしてAS/RS稼動実績情報の強制払い出し区分を更新します<br>
     * トラッキング削除によって、完了済み作業を欠品に変更した場合に使用します。
     *
     * @param ci 対象パレット
     * @param rmFlag 強制払い出し区分
     *
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 対象実績が見つからない
     */
    private void inOutResultToShortage(CarryInfo ci, String rmFlag)
            throws NotFoundException,
                ReadWriteException
    {
        InOutResultAlterKey akey = new InOutResultAlterKey();

        // 更新内容のセット
        akey.updateRemoveFlag(rmFlag);
        akey.updateLastUpdatePname(getCallerName());

        // 条件のセット
        akey.setCarryKey(ci.getCarryKey());

        InOutResultHandler handler = new InOutResultHandler(getConnection());
        handler.modify(akey);
    }

    /**
     * <jp> 出庫搬送データ削除処理を行います。
     * Palletの引当中Flagは他に引当がかかっている場合落としません。
     * 作業メンテナンスの搬送データ削除など、通常運用で削除する場合はこのメソッドを呼んでください。
     * ciに含まれるCarryInformation、WorkInformationをデータベースから削除します。
     *
     * @param conn データベース接続用 Connection
     * @param ci 搬送データ
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         更新、削除すべきデータが見つからない場合に通知されます。
     * @throws InvalidDefineException
     *         データ更新条件に不整合があった場合に通知します。
     * @throws LockTimeOutException
     *         ロックタイムアウトが発生した場合に通知します。 </jp>
     */
    /**
     * <en> Processing the deletion of retrieval carry
     * data. While the allocation of Pallet is in
     * process, keep Flag on as long as allocation
     * last. Please call this method if deletion of
     * normal operation is required, e.g., deletion of
     * carry data due to work maintenance, etc. It
     * should delete CarryInformation contained in ci
     * and WorkInformation from database.
     *
     * @param ci :carry data
     * @throws ReadWriteException :Notifies if
     *         exception occured when processing for
     *         database.
     * @throws NotFoundException :Notifies if there are
     *         no data to delete or to update.
     * @throws InvalidDefineException :Notifies if
     *         there are any inconsistency in condition
     *         of data update.
     * @throws LockTimeOutException :Notifies if lock
     *         timeout occured. </en>
     */
    public void dropRetrieval(CarryInfo ci)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException,
                LockTimeOutException
    {
        // 各テーブル操作クラスを生成する
        CarryInfoHandler cih = _ciH;
        StockHandler stkh = _stockH;
        PalletHandler plh = _plH;

        Connection conn = getConnection();
        try
        {
            CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
            ciKey.clear();
            ciKey.setCarryKey(ci.getCarryKey());
            CarryInfo checkCarry = (CarryInfo)cih.findPrimaryForUpdate(ciKey, CarryInfoHandler.WAIT_SEC_UNLIMITED);
            if (null == checkCarry)
            {
                // <jp>
                // 6026038=指定されたmckeyの搬送データが存在しません。mckey={0}</jp>
                // <en> 6026038=Transfer data for the
                // designated MCKey does not exist.
                // mckey={0}</en>
                Object[] tObj = {
                    ci.getCarryKey(),
                };
                RmiMsgLogClient.write(6026038, LogMessage.F_INFO, "CarryCompleteOperator.dropRetrieval", tObj);
                throw new NotFoundException();
            }
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException();
        }

        // 搬送データに紐付く、作業情報とパレットを取得する
        WorkInfoSearchKey wiKey = new WorkInfoSearchKey();
        WorkInfoHandler wih = _wiH;

        wiKey.clear();
        wiKey.setSystemConnKey(ci.getCarryKey());
        WorkInfo[] winfos = (WorkInfo[])wih.find(wiKey);

        Pallet pl = getPallet(ci.getPalletId());

        // <jp> パレットに対する他の引当がないか確認</jp>
        // <en> Checks whether/not the other allocation
        // has not been given.</en>
        CarryInfoSearchKey ciKey = new CarryInfoSearchKey();

        ciKey.clear();
        ciKey.setPalletId(pl.getPalletId());
        ciKey.setCarryKey(ci.getCarryKey(), "!=");

        String callerName = getCallerName();

        // 他に引当がない場合
        // パレットと移動先棚の更新を行う
        if (0 == cih.count(ciKey))
        {
            // パレットの更新を行う
            PalletAlterKey plAKey = new PalletAlterKey();
            plAKey.clear();
            plAKey.setPalletId(pl.getPalletId());
            plAKey.updateAllocationFlag(Pallet.ALLOCATION_FLAG_NOT_ALLOCATED);
            // 空棚確認の機能を実装する場合は、以下のコメントをコメントアウトしてください。
            // //<jp> 空棚確認設定以外のときに棚の状態を実棚に戻します。</jp>
            // //<en> It returns the location status to
            // 'loaded' if not setting the empty
            // location check.</en>
            // if
            // (!ci.getWorkType().equals(InOutResult.KIND_FREECHECK))
            // {
            // <jp> 商品コードが二重格納用商品コードであれば、異常棚に変更する。</jp>
            // <en> if the item code is found the
            // double occupation item codes, it shuold
            // alter to error location.</en>
            StockSearchKey stkKey = new StockSearchKey();
            stkKey.clear();
            stkKey.setStockQty(1, ">=");
            stkKey.setPalletId(pl.getPalletId());
            Stock[] stocks = (Stock[])stkh.find(stkKey);
            if (stocks[0].getItemCode().equals(WmsParam.IRREGULAR_ITEMCODE))
            {
                plAKey.updateStatusFlag(Pallet.PALLET_STATUS_IRREGULAR);
            }
            else
            {
                plAKey.updateStatusFlag(Pallet.PALLET_STATUS_REGULAR);
            }
            // }
            plAKey.updateLastUpdatePname(callerName);
            plh.modify(plAKey);
            // パレットの更新ここまで

            // 棚間移動データの場合は、搬送先の棚を空棚にする。
            if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
            {
                updateShelStatusFlagForDirect(conn, ci.getDestStationNo());
            }
        }
        else
        {
            // 他に引当がある場合
            // パレットと搬送データの更新を行う
            // <jp> パレットの状態が入庫中であれば何もしない。</jp>
            // <en>Do nothing, when status of Pallet
            // is storing.</en>
            if (!Pallet.PALLET_STATUS_STORAGE_PLAN.equals(pl.getStatusFlag()))
            {
                // <jp>
                // 搬送区分が出庫で、搬送状態が応答待ち、指示済み、掬い完了、完了、到着のデータを</jp>
                // <jp>
                // カウントする。一件でも存在すれば、パレットの状態はそのままにしておく</jp>
                // <en> Count CarryInformation data
                // that carrykind is RETRIEVAL and
                // cmdstatus is RESPONSE_WAIT or
                // INSTRUCTION </en>
                // <en> or LOAD_PIC_UP_COMPLETION or
                // RETRIVAL_COMPLETION or ARRIVAL</en>
                String[] strobj = {
                    CarryInfo.CMD_STATUS_WAIT_RESPONSE,
                    CarryInfo.CMD_STATUS_INSTRUCTION,
                    CarryInfo.CMD_STATUS_PICKUP,
                    CarryInfo.CMD_STATUS_COMP_RETRIEVAL,
                    CarryInfo.CMD_STATUS_ARRIVAL,
                };
                ciKey.clear();
                ciKey.setCarryKey(ci.getCarryKey(), "!=");
                ciKey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL);
                ciKey.setCmdStatus(strobj, true);
                ciKey.setPalletId(pl.getPalletId());
                if (cih.count(ciKey) == 0)
                {
                    // <jp> パレットの引当フラグを出庫予約に変更</jp>
                    // <en> change the status of
                    // pallet to RETRIEVAL_PLAN</en>
                    PalletAlterKey plAKey = new PalletAlterKey();
                    plAKey.clear();
                    plAKey.updateStatusFlag(Pallet.PALLET_STATUS_RETRIEVAL_PLAN);
                    plAKey.setPalletId(pl.getPalletId());
                    plAKey.updateLastUpdatePname(callerName);
                    plh.modify(plAKey);
                }
            }

            // <jp>同一パレットIDで作業が複数存在する場合ﾕﾆｯﾄ出庫作業が存在するか確認する。</jp>
            // <jp>（例）ﾏﾙﾁ引当で、1件目ﾋﾟｯｷﾝｸﾞ2件目ﾕﾆｯﾄ出庫のデータがある場合。1件目のﾋﾟｯｷﾝｸﾞをｷｬﾝｾﾙした時に2件目のﾃﾞｰﾀをﾋﾟｯｷﾝｸﾞに変更する。</jp>
            // <en>Change the retrievaldetail of multi
            // allocated carryinfo from Unit to
            // Picking</en>
            ciKey.clear();
            ciKey.setCarryKey(ci.getCarryKey(), "!=");
            ciKey.setPalletId(pl.getPalletId());
            ciKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_UNIT);
            ciKey.setCarryKeyCollect();
            if (cih.count(ciKey) > 0)
            {
                // ﾏﾙﾁ引当時、同一パレットIDでﾕﾆｯﾄ出庫作業が存在する場合、そのﾃﾞｰﾀをﾋﾟｯｷﾝｸﾞに変更する。
                CarryInfo[] cidata = (CarryInfo[])cih.find(ciKey);

                CarryInfoAlterKey ciAKey = new CarryInfoAlterKey();
                ciAKey.clear();
                ciAKey.setCarryKey(cidata[0].getCarryKey());
                ciAKey.updateRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_PICKING);
                ciAKey.updateLastUpdatePname(callerName);
                cih.modify(ciAKey);
            }
        }

        // <jp> 出庫可能数をもとにもどす。</jp>
        // <en> Setting back the available arrival
        // quantity of retrieval.</en>
        for (int i = 0; i < winfos.length; i++)
        {
            cancelRetrievalStock(winfos[i], ci.getRetrievalDetail());
        }

        // 同一パレット上に在庫が存在しない場合、パレットを削除する
        if (!isExistStock(ci.getPalletId()))
        {
            PalletSearchKey plKey = new PalletSearchKey();
            plKey.setPalletId(ci.getPalletId());
            plh.drop(plKey);
        }

        // 今回分の作業情報を削除状態に更新する
        WorkInfoAlterKey wiAKey = new WorkInfoAlterKey();
        wiAKey.setSystemConnKey(ci.getCarryKey());
        wiAKey.updateStatusFlag(WorkInfo.STATUS_FLAG_DELETE);
        wiAKey.updateLastUpdatePname(callerName);

        wih.modify(wiAKey);

        // 今回分の搬送情報を削除する
        ciKey.clear();
        ciKey.setCarryKey(ci.getCarryKey());
        cih.drop(ciKey);

        // 在庫確認、空棚確認に関係するテーブルをチェック・更新します。
        updateInventoryCheckInfo(ci);

        // 実績送信情報の報告フラグを未報告に更新
        HostSendController hsc = new HostSendController(getConnection(), getCaller());
        hsc.updateReportFlag(ci);
    }

    /**
     * <jp> 出庫データ指示キャンセル処理を行い、指定された<code>CarryInformation</code>を未指示の状態に変更します。
     * 未指示の状態に変更された<code>CarryInformation</code>は出庫指示の送信対象となります。
     * 対象となる<code>CarryInformation</code>の搬送状態が応答待ちまたは指示済み以外の場合,
     * 注意メッセージをログに出力し、キャンセル処理は行ないません。 更新内容
     * CarryInformation ・搬送状態を「開始」に変更 Pallet
     * ・状態を「出庫予約」に変更
     *
     * @param conn データベース接続用 Connection
     * @param ci 搬送データ
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         更新、削除すべきデータが見つからない場合に通知されます。
     * @throws InvalidDefineException
     *         更新条件に不整合があった場合に通知されます。 </jp>
     */
    /**
     * <en> Process the cancelation of retrieval data
     * instruction, and alter the status of specified
     * <code>CarryInformation</code> to 'to be
     * released'. This <code>CarryInformation</code>,
     * waiting to be released, will be included in the
     * retrieval instructions to submit. If the carry
     * status of this <code>CarryInformation</code>
     * is anything other than 'wait for response' or
     * 'already released', it should output the message
     * of caution in the log and it will not process
     * the cancelation. Detail of update
     * CarryInformation - alters the carry status to
     * 'start' Pallet - alters the status to 'reserved
     * for retrieval'
     *
     * @param ci : carry data
     * @throws ReadWriteException :Notifies if
     *         exception occured when processing for
     *         database.
     * @throws NotFoundException :Notifies if there are
     *         no data to delete or to update.
     * @throws InvalidDefineException :Notifies if
     *         there are any inconsistency in condition
     *         of data update. </en>
     */
    public void cancelRetrieval(CarryInfo ci)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        // 各テーブル操作クラスを生成する
        CarryInfoHandler cih = _ciH;
        PalletHandler plh = _plH;

        // <jp> 搬送状態を確認</jp>
        // <en> Checks the carry status.</en>

        String callerName = getCallerName();

        if (CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(ci.getCmdStatus())
                || CarryInfo.CMD_STATUS_INSTRUCTION.equals(ci.getCmdStatus()))
        {
            // <jp> 応答待ち、指示済みのとき搬送状態を「開始」に変更</jp>
            // <en> Alters the carry status to 'start'
            // if 'wait for repll' or 'instruction
            // released'.</en>
            CarryInfoAlterKey ciAKey = new CarryInfoAlterKey();
            ciAKey.updateCmdStatus(CarryInfo.CMD_STATUS_START);
            ciAKey.setCarryKey(ci.getCarryKey());
            ciAKey.updateLastUpdatePname(callerName);
            cih.modify(ciAKey);

            // <jp> パレットの状態を出庫予約にする。</jp>
            // <en> Alters the pallet status to
            // 'reserved for retrieval'.</en>
            PalletAlterKey plAKey = new PalletAlterKey();
            plAKey.updateStatusFlag(Pallet.PALLET_STATUS_RETRIEVAL_PLAN);
            plAKey.setPalletId(ci.getPalletId());
            plAKey.updateLastUpdatePname(callerName);
            plh.modify(plAKey);

            // <jp> 6020008=出庫Data Cancel 処理実施
            // mckey={0}</jp>
            // <en> 6020008=Picking data was canceled.
            // mckey={0}</en>
            Object[] tObj = {
                ci.getCarryKey(),
            };
            RmiMsgLogClient.write(6020008, LogMessage.F_INFO, "CarryCompleteOperator", tObj);
        }
        else
        {
            // <jp>
            // 搬送状態が応答待ちまたは指示済み以外の場合は注意メッセージをログに出力し、</jp>
            // <jp> キャンセル処理は行なわない。</jp>
            // <en> If the carry status is anything
            // other than 'wait for response' or
            // 'instruction released', </en>
            // <en> it should output the message of
            // caution in the log, and it will not
            // proces cancelation.</en>
            // <jp>
            // 6022016=受信テキストCancel結果->該当Dataはすでに搬送済みのため、Cancel不可。mckey={0}</jp>
            // <en> 6022016=Result of received text
            // cancel request -> Unable to cancel. Data
            // has been transferred. mckey={0}</en>
            Object[] dObj = {
                ci.getCarryKey(),
            };
            RmiMsgLogClient.write(6022016, LogMessage.F_NOTICE, "CarryCompleteOperator", dObj);
        }
    }

    /**
     * <jp> 入庫データキャンセル処理を行います。 ・搬送状態を「開始」に更新
     *
     * @param conn データベース接続用 Connection
     * @param ci 搬送データ
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         更新すべきデータが見つからない場合に通知されます。
     * @throws InvalidDefineException
     *         更新条件に不整合があった場合に通知されます。 </jp>
     */
    /**
     * <en> Processing the storage data cancelation.
     * -updates the carry status to 'start'
     *
     * @param ci :carry data
     * @throws ReadWriteException :Notifies if
     *         exception occured when processing for
     *         database.
     * @throws NotFoundException :Notifies if there are
     *         no data to update.
     * @throws InvalidDefineException :Notifies if
     *         there are any inconsistency in condition
     *         of data update. </en>
     */
    public void cancelStorage(CarryInfo ci)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        // <jp> 搬送状態を確認</jp>
        // <en> Checks the status of carry.</en>
        CarryInfoHandler cih = _ciH;

        if (CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(ci.getCmdStatus())
                || CarryInfo.CMD_STATUS_INSTRUCTION.equals(ci.getCmdStatus()))
        {
            // <jp> 応答待ち、指示済みのとき搬送状態を「開始」に変更</jp>
            // <en> Alters the status of carry to
            // 'start' when wiating for response or the
            // instruction was already released.</en>
            CarryInfoAlterKey ciAKey = new CarryInfoAlterKey();
            ciAKey.updateCmdStatus(CarryInfo.CMD_STATUS_START);
            ciAKey.setCarryKey(ci.getCarryKey());
            ciAKey.updateLastUpdatePname(getCallerName());
            cih.modify(ciAKey);

            // <jp> 6020007=入庫Data Cancel 処理実施
            // mckey={0}</jp>
            // <en> 6020007=Storage data was canceled.
            // mckey={0}</en>
            Object[] tObj = {
                ci.getCarryKey(),
            };
            RmiMsgLogClient.write(6020007, LogMessage.F_INFO, "CarryCompleteOperator", tObj);
        }
        else
        {
            // <jp>
            // 6022016=受信テキストCancel結果->該当Dataはすでに搬送済みのため、Cancel不可。mckey={0}</jp>
            // <en> 6022016=Result of received text
            // cancel request -> Unable to cancel. Data
            // has been transferred. mckey={0}</en>
            Object[] tObj = {
                ci.getCarryKey(),
            };
            RmiMsgLogClient.write(6022016, LogMessage.F_NOTICE, "CarryCompleteOperator", tObj);
        }
    }

    /**
     * <jp>
     * 指示された搬送データの引当解除処理を行います。出庫データの場合CarryInformation,WorkInformationの削除を行います。
     * 入庫の場合はCarryInformationから参照されるPallet、Stockの削除も行います。
     * 戻り値には削除されたCarryInformationが保持していたアイルステーションNoを返します。
     *
     * @param conn データベース接続用 Connection
     * @param ciarray 引当解除を行うCarryInformationの一覧
     * @return 削除されたCarryInformationが保持していたアイルステーションNoの一覧
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         削除すべきデータが見つからない場合に通知されます。
     * @throws InvalidDefineException
     *         更新時にデータの不整合が発生した場合に通知されます。
     * @throws LockTimeOutException
     *         ロックタイムアウトが発生した場合に通知します。 </jp>
     */
    /**
     * <en> Releasing the allocation of specified carry
     * data. If it is a retrieval data, it also deletes
     * CarryInformation and WorkInformation. In case of
     * storage, it also deletes Pallet and Stock which
     * the CarryInformation refers to. For the return
     * value, it returns aisle station no. that deleted
     * CarryInformation had preserved.
     *
     * @param ciarray :List of CarryInformation which
     *        the allocation to be released
     * @return :List of aisle station no, that deleted
     *         CarryInformation ahd preserved
     * @throws ReadWriteException :Notifies if
     *         exception occured when processing for
     *         database.
     * @throws NotFoundException :Notifies if there are
     *         no data to delete.
     * @throws InvalidDefineException :Notifies if
     *         there are any inconsistency in condition
     *         of data update.
     * @throws LockTimeOutException :Notifies if lock
     *         timeout occured. </en>
     */
    public String[] releaseAllocation(CarryInfo[] ciarray)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException,
                LockTimeOutException
    {
        List<String> aisleStNoList = new LinkedList<String>();

        for (int i = 0; i < ciarray.length; i++)
        {
            // <jp>
            // 搬送状態を確認し、引当又は開始状態のCarryInformationを削除する。</jp>
            // <en> Checks the carry status and deletes
            // CarryInformation of 'allocated' or
            // 'start' status.</en>

            CarryInfo carry = ciarray[i];
            String cmdStatus = carry.getCmdStatus();
            String aisleStationNo = carry.getAisleStationNo();
            String carryFlag = carry.getCarryFlag();

            if (CarryInfo.CMD_STATUS_ALLOCATION.equals(cmdStatus) || CarryInfo.CMD_STATUS_START.equals(cmdStatus))
            {
                // <jp> 入庫、直行</jp>
                // <en> Storage, direct travel</en>
                if (CarryInfo.CARRY_FLAG_STORAGE.equals(carryFlag)
                        || CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(carryFlag))
                {
                    // 在庫確認の戻り入庫時を考慮し
                    // 在庫確認データの場合は、削除しないように変更
                    if (!(CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(carry.getRetrievalDetail())))
                    {
                        dropStorage(carry);
                        if (!StringUtil.isBlank(aisleStationNo))
                        {
                            aisleStNoList.add(aisleStationNo);
                        }
                    }
                }
                // <jp> 出庫、棚間移動</jp>
                // <en> Retrieval, location to location
                // move</en>
                else if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(carryFlag)
                        || CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(carryFlag))
                {
                    try
                    {
                        dropRetrieval(carry);
                    }
                    catch (NotFoundException e)
                    {
                        // 対象データなしの場合、エラーにしません
                    }

                    if (!StringUtil.isBlank(aisleStationNo))
                    {
                        aisleStNoList.add(aisleStationNo);
                    }
                }
                else
                {
                    // 処理なし
                }
            }
            // 処理なしの条件 (参考のためコメントとして保存) (2007-05-08)
            // else if
            // (CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(cmdStatus)
            // ||
            // CarryInfo.CMD_STATUS_INSTRUCTION.equals(cmdStatus)
            // ||
            // CarryInfo.CMD_STATUS_PICKUP.equals(cmdStatus)
            // ||
            // CarryInfo.CMD_STATUS_COMP_RETRIEVAL.equals(cmdStatus)
            // ||
            // CarryInfo.CMD_STATUS_ARRIVAL.equals(cmdStatus)
            // ||
            // CarryInfo.CMD_STATUS_ERROR.equals(cmdStatus))
            // {
            // // 処理なし
            // }
        }

        String[] aisles = aisleStNoList.toArray(new String[aisleStNoList.size()]);
        return aisles;

    }

    /**
     * <jp> 指定されたCarryInformationが空棚確認搬送データの場合、
     * CarryInformationと同じアイルステーション中に、空棚確認の搬送データが存在するか確認します。
     * 存在しない場合、アイルステーションの在庫確認中フラグを在庫確認未作業に変更します。
     *
     * @param conn データベース接続用 Connection
     * @param ci 搬送データ
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         変更すべき情報が見つからない場合に通知されます。 </jp>
     */
    /**
     * <en> If the specified CarryInformation is the
     * carry data for empty location check, check to
     * see if the empty location check carry data
     * exists in the same aisle station as
     * CarryInformation. If there is no data, alter hte
     * inventory checking flag to 'to be checked'.
     *
     * @param ci :carry data
     * @throws ReadWriteException :Notifies if
     *         exception occured when processing for
     *         database.
     * @throws NotFoundException :Notifies if data to
     *         modify cannot be found. </en>
     */
    public void emptyLocationCheckOff(CarryInfo ci)
            throws ReadWriteException,
                NotFoundException
    {
        // 搬送データ操作クラスを生成する
        CarryInfoHandler cih = _ciH;
        String retrievalDetail = ci.getRetrievalDetail();
        // <jp>
        // CarryInforrmationが空棚確認データであれば他の空棚確認データを数える。</jp>
        // <en> If the CarryInforrmation is the empty
        // location check data, count the other empty
        // location check data.</en>
        if (CarryInfo.PRIORITY_CHECK_EMPTY.equals(retrievalDetail))
        {
            CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
            ciKey.setAisleStationNo(ci.getAisleStationNo());
            ciKey.setCmdStatus(CarryInfo.CMD_STATUS_COMP_RETRIEVAL, "<");
            ciKey.setPriority(CarryInfo.PRIORITY_CHECK_EMPTY);

            if (0 == cih.count(ciKey))
            {
                // アイル表の空棚検索フラグをOFFにする
                updateAisleInventoryCheckFlag(ci.getAisleStationNo(), Station.INVENTORY_CHECK_FLAG_UNSTART);
            }
        }
    }

    /**
     * 在庫確認、空棚確認のチェック、更新を行います。
     * アイル内に在庫確認、空棚確認の作業データが存在しない場合、アイルを在庫確認未作業にします。
     * 対象搬送データの同一スケジュールNo.内に、在庫確認データ、空棚確認データが存在しない場合、在庫確認情報の状態を更新します。
     *
     * @param ci 搬送データ
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         変更すべき情報が見つからない場合に通知されます。
     */
    public void updateInventoryCheckInfo(CarryInfo ci)
            throws NotFoundException,
                ReadWriteException
    {
        Connection conn = getConnection();
        // <jp>
        // 在庫確認データチェックを行い、在庫確認、空棚確認の作業データが存在しない場合はアイルを在庫確認未作業にする。</jp>
        // <en> Checks the inventory check data; if
        // there is no data of inventory check jobs or
        // data of empty location check jobs,</en>
        // <en> switch the status to 'to be
        // checked'.</en>
        updateAisleInventoryCheckOff(ci);

        // <jp> 搬送データのスケジュールNoをキーにCarryInfoを検索し</jp>
        // <jp>
        // 搬送データと同じスケジュールNo.の在庫確認又は空棚確認の搬送データが存在しない場合、</jp>
        // <jp>
        // そのスケジュールNoのInventoryCheck表のStatusを未作業に変更します。</jp>
        // <en> It searches in CarryInfor according to
        // the schedule no. in Carry data as a
        // key.</en>
        // <en> If the carry data for incentory check
        // or empty location check exist in the same
        // schedule no., </en>
        // <en> it should alter the Status of
        // InventoryCheck table of that schedule no. to
        // 'to be processed.'</en>
        updateInventoryCheckStatusOff(conn, ci);
    }

    /**
     * AS/RS稼動実績情報の作成を行います。<BR>
     *
     * @param ci 搬送情報
     * @param plt パレット情報
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     */
    public void registInOutResult(CarryInfo ci, Pallet plt)
            throws ReadWriteException
    {
        // 実績記録が必要な作業の場合、実績データを作成する
        if (isResultOperation(ci.getWorkType()))
        {
            // AS/RS稼動実績を出力する。
            insertInOutResult(ci, plt, ci.getWorkType());
        }
    }

    /**
     * AS/RS稼動実績情報の作成を行います。<BR>
     * 搬送キーに紐付く作業の完了処理を行います。<BR>
     * 作業情報を完了にし、在庫の更新を行います。<BR>
     * また、作業情報に対する実績送信情報の作成、紐付く予定情報の更新を行います。<BR>
     *
     * @param ci 搬送情報
     * @param plt パレット情報
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         変更、削除すべきデータが見つからない場合に通知されます。
     * @throws InvalidDefineException
     *         データベースの更新条件に不整合があった場合に通知されます。
     */
    public void completeWorkInfo(CarryInfo ci, Pallet plt)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        // 実績記録が必要な作業の場合、実績データを作成する
        if (isResultOperation(ci.getWorkType()))
        {
            // AS/RS稼動実績を出力する。
            insertInOutResult(ci, plt, ci.getWorkType());
        }

        // 入出庫作業情報、入出庫実績送信情報を更新する。
        completeWork(ci, ci.getCarryFlag(), ci.getDestStationNo(), CARRY_COMPLETE.NORMAL);
    }

    /**
     * 棚間移動入庫時のPallet、Stock、Shelfの更新を行います。
     * また、移動入庫したパレットに他の搬送データが有る場合は搬送データの搬送元を
     * 棚間移動先の棚に変更します。
     *
     * @param ci 搬送情報
     * @param plt パレット情報
     * @param srcLocation 棚間移動出庫棚（AS/RS形式の棚No）
     * @param destLocation 棚間移動入庫棚（AS/RS形式の棚No）
     * @throws ReadWriteException データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException 変更、削除すべきデータが見つからない場合に通知されます。
     * @throws InvalidDefineException データベースの更新条件に不整合があった場合に通知されます。
     */
    public void storageRackToRackMove(CarryInfo ci, Pallet plt, String srcLocation, String destLocation)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        PalletSearchKey pltKey = new PalletSearchKey();
        PalletHandler pltH = new PalletHandler(getConnection());

        ShelfAlterKey shelfAltKey = new ShelfAlterKey();
        ShelfHandler shelfH = new ShelfHandler(getConnection());

        CarryInfoSearchKey carykey = new CarryInfoSearchKey();
        CarryInfoAlterKey caryAKey = new CarryInfoAlterKey();
        CarryInfoHandler caryH = new CarryInfoHandler(getConnection());

        if (!ci.getDestStationNo().equals(destLocation))
        {
            // 棚違い完了した場合、搬送情報の搬送先を変更する。
            ci.setDestStationNo(destLocation);
        }

        // 搬送情報を検索し、今回の棚間移動したパレットに在庫確認予定の搬送情報が有るかチェックする
        // 在庫確認予定の搬送情報がある場合は、この棚間移動は在庫確認に伴う棚間移動なので、在庫情報更新
        // する場合でも、在庫更新履歴を出力しない。
        carykey.setPalletId(ci.getPalletId());
        carykey.setWorkType(CarryInfo.WORK_TYPE_INVENTORYCHECK);
        carykey.setCmdStatus(CarryInfo.CMD_STATUS_START);
        CarryInfo[] invntCi = (CarryInfo[])caryH.find(carykey);
        boolean history = false;
        if (ArrayUtil.isEmpty(invntCi))
        {
            // 在庫確認の棚間移動でなければ在庫更新履歴を登録する
            history = true;
        }
        // 棚変更（パレット、在庫、マルチ引当分作業情報）
        CarryInfo newCarry = (CarryInfo)ci.clone();
        newCarry.setDestStationNo(destLocation);
        try
        {
            getStockCtlr().updateAsrsLocation(newCarry, null, history);
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException(e);
        }
        catch (DataExistsException e)
        {
            throw new ReadWriteException(e);
        }

        //同一パレットに対して搬送データがある場合、その搬送データの搬送元を棚間移動先に変更。
        carykey.clear();
        carykey.setPalletId(plt.getPalletId());
        CarryInfo[] multiCI = (CarryInfo[])caryH.find(carykey);
        if (multiCI != null && multiCI.length > 0)
        {
            for (int multiCount = 0; multiCount < multiCI.length; multiCount++)
            {
                caryAKey.clear();
                caryAKey.setCarryKey(multiCI[multiCount].getCarryKey());
                // 在庫確認のデータの場合は出庫ステーションNoにもともとのステーションをセットする。
                if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(multiCI[multiCount].getRetrievalDetail()))
                {
                    caryAKey.updateRetrievalStationNo(srcLocation);
                }
                pltKey.clear();
                pltKey.setPalletId(multiCI[multiCount].getPalletId());
                Pallet[] mPallet = (Pallet[])pltH.find(pltKey);
                caryAKey.updateSourceStationNo(mPallet[0].getCurrentStationNo());
                caryAKey.updateLastUpdatePname(getCallerName());
                caryH.modify(caryAKey);
            }
        }

        // 棚間移動先の棚の棚状態を更新する。
        shelfAltKey.clear();
        shelfAltKey.setStationNo(destLocation);
        shelfAltKey.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_STORAGED);
        shelfH.modify(shelfAltKey);

        completeWorkInfo(ci, plt);
    }

    /**
     * 搬送情報とパレット情報を元に在庫の棚No.を変更します。<br>
     * 在庫に紐付く作業が有る場合、作業情報の在庫IDを更新します。<br>
     * 在庫更新履歴の作成を行うため、削除,追加と更新します。
     *
     * @param ci 搬送情報
     * @param pl パレット情報
     * @param newLocation 新しい棚No.(ParamLocationフォーマット)
     * @param jobType 作業区分
     * @param result 実績送信情報の更新有無
     *
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException 削除対象在庫なし
     * @throws DataExistsException 在庫更新履歴登録済み
     * @throws NoPrimaryException マスタデータ重複
     * @throws LockTimeOutException ロックタイムアウト
     */
    public void updateStockLocation(CarryInfo ci, Pallet pl, String newLocation, String[] jobType, boolean result)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException,
                LockTimeOutException
    {
        StockController stockC = getStockCtlr();

        Connection conn = getConnection();

        // 対象在庫を取得します
        StockHandler stockH = new StockHandler(conn);
        StockSearchKey stkKey = new StockSearchKey();
        stkKey.setPalletId(pl.getPalletId());

        Stock[] stocks = (Stock[])stockH.findForUpdate(stkKey);

        // 対象作業を取得します
        WorkInfoHandler wiH = new WorkInfoHandler(conn);
        WorkInfoSearchKey wiKey = new WorkInfoSearchKey();
        wiKey.setSystemConnKey(ci.getCarryKey());

        WorkInfo[] works = (WorkInfo[])wiH.find(wiKey);

        WorkInfo priWork = works[0]; // 代表作業として1件目を使用

        String userId = priWork.getUserId();
        String termNo = priWork.getTerminalNo();

        WmsUserInfo ui = WmsUserInfo.buildUserInfo(conn, userId, SystemDefine.HARDWARE_TYPE_ASRS, termNo);

        for (Stock stock : stocks)
        {
            // 旧在庫を削除
            stockC.delete(stock, StockHistory.INC_DEC_TYPE_STOCK_DECREMENT, jobType[0], ui);

            // 棚No.を更新して登録
            stock.setLocationNo(newLocation);
            String stockId =
                    stockC.insert(stock, StockHistory.INC_DEC_TYPE_STOCK_INCREMENT, jobType[1], ui,
                            SystemDefine.DEFAULT_REASON_TYPE);

            // 作業と紐付いている在庫のIDが変更になったので、現在処理中の入出庫作業情報の
            // 在庫IDと実績を更新 (在庫ID)
            try
            {
                WorkInfoAlterKey wiAKey = new WorkInfoAlterKey();
                wiAKey.setSystemConnKey(ci.getCarryKey());
                wiAKey.setStockId(stock.getStockId());
                wiAKey.updateStockId(stockId);
                wiAKey.updateLastUpdatePname(getCallerName());
                wiH.modify(wiAKey);
            }
            catch (NotFoundException e)
            {
                // ignore not found
            }

            if (result)
            {
                // 実績送信情報を更新する
                try
                {
                    // 実績と紐付いている在庫のIDが変更になったので、実績を更新 (在庫ID)
                    HostSendHandler hsH = new HostSendHandler(conn);
                    HostSendAlterKey hAKey = new HostSendAlterKey();
                    hAKey.setSystemConnKey(ci.getCarryKey());
                    hAKey.setStockId(stock.getStockId());
                    hAKey.updateStockId(stockId);
                    hAKey.updateLastUpdatePname(getCallerName());
                    hsH.modify(hAKey);
                }
                catch (NotFoundException e)
                {
                    // ignore not found
                }
            }

            // 作業と紐付いている在庫のIDが変更になったので、未開始、又は、作業中の入出庫作業情報の
            // 在庫IDと予定を更新 (在庫ID)
            try
            {
                WorkInfoAlterKey wiAKey = new WorkInfoAlterKey();
                wiAKey.setStatusFlag(WorkInfo.STATUS_FLAG_UNSTART, "=", "(", "", false);
                wiAKey.setStatusFlag(WorkInfo.STATUS_FLAG_NOWWORKING, "=", "", ")", true);
                wiAKey.setSystemConnKey(ci.getCarryKey(), "<>");
                wiAKey.setStockId(stock.getStockId());
                wiAKey.updateStockId(stockId);
                wiAKey.updatePlanLocationNo(newLocation);
                wiAKey.updateLastUpdatePname(getCallerName());
                wiH.modify(wiAKey);
            }
            catch (NotFoundException e)
            {
                // ignore not found
            }
        }
    }

    /**
     * 棚間移動出庫時の棚状態の更新、AS/RS稼動実績の出力を行います。
     * この処理で搬送情報の更新は行いません。
     *
     * @param ci 搬送情報
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException 削除対象在庫なし
     */
    public void retrievalRackToRackMove(CarryInfo ci)
            throws ReadWriteException,
                NotFoundException
    {
        CarryInfoSearchKey carykey = new CarryInfoSearchKey();
        CarryInfoHandler caryh = new CarryInfoHandler(getConnection());

        PalletSearchKey pltKey = new PalletSearchKey();
        PalletHandler plth = new PalletHandler(getConnection());

        ShelfAlterKey shelfKey = new ShelfAlterKey();
        ShelfHandler shelfh = new ShelfHandler(getConnection());

        pltKey.setPalletId(ci.getPalletId());
        Pallet[] pallet = (Pallet[])plth.find(pltKey);

        // 棚間移動出庫の処理。
        //同一パレットに対して在庫確認の開始状態の搬送データがあるか確認する。
        carykey.setPalletId(pallet[0].getPalletId());
        carykey.setWorkType(CarryInfo.WORK_TYPE_INVENTORYCHECK);
        carykey.setCmdStatus(CarryInfo.CMD_STATUS_START);
        if (caryh.count(carykey) > 0)
        {
            // 棚間移動出庫した棚を予約棚にする。
            shelfKey.setStationNo(ci.getSourceStationNo());
            shelfKey.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_RESERVATION);
            shelfh.modify(shelfKey);
        }
        else
        {
            FreeAllocationShelfOperator freeshelfop =
                    new FreeAllocationShelfOperator(getConnection(), ci.getSourceStationNo());
            // フリーアロケーション運用の場合、荷幅、棚使用フラグをフリーに更新
            if (freeshelfop.isFreeAllocationStation())
            {
                freeshelfop.alterFreeWidth();
            }

            // 棚間移動出庫した棚を空棚にする。
            shelfKey.setStationNo(ci.getSourceStationNo());
            shelfKey.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
            shelfh.modify(shelfKey);
        }

        // AS/RS稼動実績を出力する。
        registInOutResult(ci, pallet[0]);
    }

    /**
     * <jp> 入庫データを未作業に戻す処理を行います。
     *
     * @param conn データベース接続用 Connection
     * @param ci 搬送データ
     * @throws ReadWriteException データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException 更新すべきデータが見つからない場合に通知されます。
     * @throws InvalidDefineException 更新条件に不整合があった場合に通知されます。
     */
    public void undoneStorage(CarryInfo ci)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        // パレット情報
        PalletHandler pHandler = _plH;
        PalletSearchKey pKey = new PalletSearchKey();
        // 在庫情報
        StockHandler sHandler = _stockH;
        StockSearchKey sKey = new StockSearchKey();
        // 作業情報
        WorkInfoHandler wHandler = _wiH;
        WorkInfoSearchKey wkey = new WorkInfoSearchKey();

        // パレット情報検索条件セット
        pKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
        pKey.setKey(CarryInfo.PALLET_ID, ci.getPalletId());
        pKey.setKey(CarryInfo.CARRY_KEY, ci.getCarryKey());
        // パレット情報検索
        Pallet[] pallets = (Pallet[])pHandler.find(pKey);

        for (Pallet pallet : pallets)
        {
            // 在庫情報検索条件セット
            sKey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
            sKey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
            sKey.setJoin(Stock.ITEM_CODE, WorkInfo.ITEM_CODE);
            sKey.setJoin(WorkInfo.SYSTEM_CONN_KEY, CarryInfo.CARRY_KEY);
            sKey.setKey(CarryInfo.CARRY_KEY, ci.getCarryKey());
            sKey.setKey(Pallet.PALLET_ID, pallet.getPalletId());
            // 在庫情報検索
            Stock[] stocks = (Stock[])sHandler.find(sKey);

            for (Stock stock : stocks)
            {
                // 搬送情報に紐付く、作業情報を取得
                wkey.clear();
                wkey.setSystemConnKey(ci.getCarryKey());
                wkey.setStockId(stock.getStockId());
                WorkInfo[] workInfos = (WorkInfo[])wHandler.find(wkey);
                for (WorkInfo workInfo : workInfos)
                {
                    if (StringUtil.isBlank(workInfo.getPlanUkey()))
                    {
                        // 作業情報の削除
                        wkey.clear();
                        wkey.setJobNo(workInfo.getJobNo());
                        if (wHandler.count(wkey) > 0)
                        {
                            wHandler.drop(wkey);
                        }
                    }
                    else
                    {
                        if (CarryInfo.WORK_TYPE_STORAGE.equals(ci.getWorkType()))
                        {
                            // 作業情報に紐付く予定情報取得
                            StoragePlanHandler stHandler = _splanH;
                            StoragePlanSearchKey spKey = new StoragePlanSearchKey();

                            spKey.setPlanUkey(workInfo.getPlanUkey());

                            StoragePlan[] stPlan = (StoragePlan[])stHandler.find(spKey);

                            // 作業情報更新
                            cancelWorkInfo(stPlan[0], workInfo);
                            // 予定情報更新
                            cancelPlanStorage(stPlan[0], workInfo);
                        }
                        else if (CarryInfo.WORK_TYPE_RESTORING.equals(ci.getWorkType()))
                        {
                            // 作業情報に紐付く予定情報取得
                            ReStoringPlanHandler rsHandler = _rsplanH;
                            ReStoringPlanAlterKey aKey = new ReStoringPlanAlterKey();
                            ReStoringPlanSearchKey reKey = new ReStoringPlanSearchKey();

                            reKey.setPlanUkey(workInfo.getPlanUkey());

                            ReStoringPlan[] rsPlan = (ReStoringPlan[])rsHandler.find(reKey);

                            // 更新条件
                            aKey.updateStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
                            aKey.updateLastUpdatePname(getClass().getSimpleName());

                            // 検索条件
                            aKey.setPlanUkey(rsPlan[0].getPlanUkey());
                            // 再入庫予定情報キャンセル
                            rsHandler.modify(aKey);
                            // 作業情報キャンセル
                            cancelWorkInfo(workInfo, rsPlan[0]);
                        }
                    }
                }
                // 予定在庫の削除
                sKey.clear();
                sKey.setStockId(stock.getStockId());
                if (sHandler.count(sKey) > 0)
                {
                    sHandler.drop(sKey);
                }
            }
            // パレット情報削除
            pKey.clear();
            pKey.setPalletId(pallet.getPalletId());
            if (pHandler.count(pKey) > 0)
            {
                pHandler.drop(pKey);
            }
            // 搬送元ステーションの棚を空棚にする
            updateShelfStatusFlag(pallet.getCurrentStationNo(), Shelf.LOCATION_STATUS_FLAG_EMPTY);
        }

    }

    // ------------------------------------------------------------
    // accessor methods
    // ------------------------------------------------------------

    /**
     * AsStockController を返します。<br>
     * インスタンスが生成されていないときは生成します。
     *
     * @return AsStockController
     * @throws ReadWriteException データベースアクセスエラー
     */
    protected AsStockController getStockCtlr()
            throws ReadWriteException
    {
        if (null == _stockCtlr)
        {
            try
            {
                _stockCtlr = new AsStockController(getConnection(), getCaller());
            }
            catch (ScheduleException e)
            {
                throw new ReadWriteException(e);
            }
        }
        return _stockCtlr;
    }

    // ------------------------------------------------------------
    // package methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // private methods
    // ------------------------------------------------------------

    /**
     * AS/RS稼動実績データの編集を行い、データベースに登録を行います。
     * AS/RS稼動実績データの編集は搬送情報及びパレットを元に行います。<BR>
     * また、resultWorkTypeで渡された作業区分で、AS/RS稼動実績を作成します。
     * その他の場合は、各搬送区分などから判断してAS/RS稼動実績を作成します。<BR>
     *
     * @param ci 搬送データ
     * @param pl 完了パレット情報
     * @param compWorkType 完了時の作業種別
     *
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     *
     * @since V3.0 (2007-05-08)
     */
    private void insertInOutResult(CarryInfo ci, Pallet pl, String compWorkType)
            throws ReadWriteException
    {
        InOutResultHandler iorH = _inoutResultH;
        InOutResult ioresult = new InOutResult();

        try
        {
            // build result about locations
            fillIOResultLocations(ci, pl, ioresult);

            // setup others of result entity
            ioresult.setWorkType(ci.getWorkType());
            ioresult.setRetrievalDetail(ci.getRetrievalDetail());
            ioresult.setWorkNo(ci.getWorkNo());
            ioresult.setPalletId(pl.getPalletId());
            ioresult.setCarryKey(ci.getCarryKey());
            ioresult.setRestoringFlag(ci.getRestoringFlag());

            String workDay = getWarenaviSysCtlr().getWorkDay();
            ioresult.setWorkDay(workDay);

            // ci.getWorkType()とcompWorkTypeが同じときは正常
            boolean isNormalComp = ci.getWorkType().equals(compWorkType);
            String rmFlag = (isNormalComp) ? InOutResult.IN_OUT_RESULT_REMOVE_NORMAL
                                          : compWorkType;

            ioresult.setRemoveFlag(rmFlag);

            ioresult.setRegistPname(getCallerName());
            ioresult.setLastUpdatePname(getCallerName());

            iorH.create(ioresult);

            return;
        }
        catch (ScheduleException e)
        {
            throw new ReadWriteException(e);
        }
        catch (DataExistsException e)
        {
            // nothing to do (never caught this
            // exception)
        }
    }

    /**
     * 搬送情報とパレット情報から実績作成区分とステーション関連情報を
     * AS/RS稼動実績エンティティにセットします。
     *
     * @param ci 搬送情報
     *        <ol>
     *        以下の情報が参照されます。
     *        <li>搬送区分
     *        <li>出庫指示詳細
     *        <li>出庫ロケーションNo.
     *        <li>搬送元ステーションNo.
     *        <li>搬送先ステーションNo.
     *        <li>アイルステーションNo.
     *        </ol>
     *
     * @param pl パレット情報
     *        <ol>
     *        以下の情報が参照されます。
     *        <li>現在ステーションNo.
     *        <li>倉庫ステーションNo.
     *        </ol>
     *
     * @param ior AS/RS稼動実績エンティティ
     *        <ol>
     *        以下の情報が更新されます。
     *        <li>実績作成区分
     *        <li>ステーションNo.
     *        <li>棚No.
     *        <li>倉庫ステーションNo.
     *        <li>アイルステーションNo.
     *        </ol>
     */
    private void fillIOResultLocations(CarryInfo ci, Pallet pl, InOutResult ior)
    {
        // pickup carry info
        String carryFlag = ci.getCarryFlag();
        String retrvalDetail = ci.getRetrievalDetail();
        String aisleStNo = ci.getAisleStationNo();

        String retrvalStNo = ci.getRetrievalStationNo();
        String srcStNo = ci.getSourceStationNo();
        String destStNo = ci.getDestStationNo();

        // pickup pallet info
        String currStNo = pl.getCurrentStationNo();
        String whStNo = pl.getWhStationNo();

        String resKind = "";
        String stNo = "";
        String locNo = "";

        if (CarryInfo.CARRY_FLAG_STORAGE.equals(carryFlag))
        {
            // 入庫
            resKind = InOutResult.RESULT_KIND_STORAGE;

            stNo = srcStNo;
            locNo = destStNo;
        }
        else if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(carryFlag))
        {
            // 出庫
            resKind = InOutResult.RESULT_KIND_RETRIEVAL;
            stNo = destStNo;

            // 在庫確認作業の棚Noは搬送元棚Noとする為、出庫ロケーションNoをセット
            boolean isInvCheck = CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(retrvalDetail);
            locNo = (isInvCheck) ? retrvalStNo
                                : currStNo;
        }
        else if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(carryFlag))
        {
            if (CarryInfo.CMD_STATUS_COMP_RETRIEVAL.equals(ci.getCmdStatus()))
            {
                // 棚間移動 (入庫)
                resKind = InOutResult.RESULT_KIND_STORAGE;
                stNo = currStNo;
                locNo = destStNo;
            }
            else
            {
                // 棚間移動 (出庫)
                resKind = InOutResult.RESULT_KIND_RETRIEVAL;
                stNo = destStNo;
                locNo = currStNo;
            }
        }
        else if (CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(carryFlag))
        {
            // 直行 (入庫)
            resKind = InOutResult.RESULT_KIND_NO_VARIATION;
            stNo = srcStNo;
            locNo = destStNo;
        }
        else
        {
            throw new RuntimeException("Invalid carry flag on CarryInfo. : " + carryFlag);
        }

        // setup in out result
        ior.setResultKind(resKind);
        ior.setStationNo(stNo);
        ior.setLocationNo(locNo);

        ior.setWhStationNo(whStNo);
        ior.setAisleStationNo(aisleStNo);
    }

    /**
     * 強制払い出し時、再入庫予定データを作成します。<BR>
     * 作業情報の予定数を減算した結果在庫数が０より多い在庫に対してと、
     * 作業対象外で、パレットに混載されていて払い出された在庫に対して 再入庫予定データを作成します。
     *
     * @param ci 搬送情報
     * @throws ReadWriteException
     *         データベースに対する処理で発生した例外を通知します。
     * @throws NotFoundException 対象データが見つからない
     */
    private void insertReStoringData(CarryInfo ci)
            throws ReadWriteException,
                NotFoundException
    {
        try
        {
            // パレット上在庫取得
            StockHandler stockH = new StockHandler(getConnection());
            // get Stock info
            StockSearchKey sskey = new StockSearchKey();

            sskey.setPalletId(ci.getPalletId());

            Stock[] stocks = (Stock[])stockH.find(sskey);

            if (stocks == null)
            {
                throw new NotFoundException();
            }

            WorkInfoHandler workH = new WorkInfoHandler(getConnection());
            WorkInfoSearchKey wskey = new WorkInfoSearchKey();
            ReStoringPlanHandler restoringH = new ReStoringPlanHandler(getConnection());
            WMSSequenceHandler seqHandler = new WMSSequenceHandler(getConnection());

            String pname = getCallerName();
            String workday = getWarenaviSysCtlr().getWorkDay();
            Date remove_date = WmsFormatter.toDateTime(DbDateUtil.getSystemDateTime());
            for (Stock stock : stocks)
            {
                wskey.clear();

                wskey.setSystemConnKey(ci.getCarryKey());
                wskey.setStockId(stock.getStockId());

                WorkInfo work = (WorkInfo)workH.findPrimary(wskey);

                // set plan_qty
                int plan_qty = 0;
                if (work != null)
                {
                    plan_qty = stock.getStockQty() - work.getPlanQty();
                }
                else
                {
                    plan_qty = stock.getStockQty();
                }

                // 全数で出庫された在庫は、再入庫データを作成しない
                if (plan_qty == 0)
                {
                    continue;
                }

                ReStoringPlan entPlan = new ReStoringPlan();

                String planukey = seqHandler.nextReStoringPlanUkey(); // 採番
                entPlan.setPlanUkey(planukey);
                entPlan.setStatusFlag(ReStoringPlan.STATUS_FLAG_UNSTART);
                entPlan.setStationNo(ci.getDestStationNo());
                entPlan.setWorkNo(ci.getWorkNo());
                entPlan.setPlanDay(workday);
                entPlan.setConsignorCode(stock.getConsignorCode());
                entPlan.setItemCode(stock.getItemCode());
                entPlan.setPlanLotNo(stock.getLotNo());
                entPlan.setStorageDay(stock.getStorageDay());
                entPlan.setStorageDate(stock.getStorageDate());
                entPlan.setRetrievalDay(workday); // 最終出庫日は更新する
                entPlan.setRemoveDate(remove_date);
                entPlan.setPlanQty(plan_qty);
                entPlan.setReportFlag(ReStoringPlan.REPORT_FLAG_NOT_REPORT);
                entPlan.setRegistDate(new SysDate());
                entPlan.setRegistPname(pname);
                entPlan.setLastUpdatePname(pname);

                // create plan
                restoringH.create(entPlan);

                WorkInfo entWork = new WorkInfo();

                entWork.setJobNo(seqHandler.nextWorkInfoJobNo()); // 採番
                entWork.setJobType(WorkInfo.JOB_TYPE_RESTORING); // 再入庫
                entWork.setStatusFlag(WorkInfo.STATUS_FLAG_UNSTART);
                entWork.setHardwareType(WorkInfo.HARDWARE_TYPE_UNSTART);
                entWork.setPlanUkey(planukey);
                entWork.setPlanDay(workday);
                entWork.setConsignorCode(stock.getConsignorCode());
                entWork.setItemCode(stock.getItemCode());
                entWork.setPlanLotNo(stock.getLotNo());
                entWork.setPlanQty(plan_qty);
                entWork.setRegistDate(new SysDate());
                entWork.setRegistPname(pname);
                entWork.setLastUpdatePname(pname);

                // create work
                workH.create(entWork);
            }
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException(e);
        }
        catch (DataExistsException e)
        {
            throw new ReadWriteException(e);
        }
        catch (ScheduleException e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * <jp> 空パレットとそれに紐付く在庫情報を作成します。
     *
     * @param conn データベースコネクション
     * @param pl パレット情報
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws InvalidDefineException
     *         データベースの更新条件に不整合があった場合に通知されます。
     * @throws InvalidStatusException
     *         検索条件の内容が範囲外であった場合に通知します。 </jp>
     */
    /**
     * <en> Creating the empty pallet data.
     *
     * @param conn :Connection with database
     * @param pl :pallet data
     * @throws ReadWriteException :Notifies if
     *         exception occured when processing for
     *         database.
     * @throws InvalidDefineException :Notifies if
     *         there are any inconsistency in condition
     *         of data update.
     * @throws InvalidStatusException :Notifies if the
     *         search condition is invalid. </en>
     */
    private void insertEmptyPallet(Connection conn, Pallet pl)
            throws ReadWriteException,
                InvalidDefineException,
                InvalidStatusException
    {
        WMSSequenceHandler seqHandler = new WMSSequenceHandler(conn);
        StockHandler stockH = _stockH;
        PalletHandler palletH = _plH;
        String pName = getCallerName();
        try
        {
            String stockId_seq = seqHandler.nextStockId();

            // <jp> Stockインスタンス生成</jp>
            // <jp> 空パレットデータ登録</jp>
            // <en> Generaete the Stock instance.</en>
            // <en> Registering the empty pallet
            // data.</en>
            Stock stk = new Stock();
            stk.setStockId(stockId_seq);
            stk.setPalletId(pl.getPalletId());
            stk.setAreaNo(getAreaCtlr().getAreaNoOfWarehouse(pl.getWhStationNo()));
            stk.setConsignorCode(WmsParam.EMPTYPB_CONSIGNORCODE);
            stk.setItemCode(WmsParam.EMPTYPB_ITEMCODE);
            stk.setLocationNo(getAreaCtlr().toParamLocation(pl.getCurrentStationNo()));
            stk.setStockQty(1);
            stk.setAllocationQty(1);
            stk.setStorageDate(new SysDate());
            stk.setRegistPname(pName);
            stk.setLastUpdatePname(pName);

            stockH.create(stk);

            // 2010/05/11 Y.Osawa ADD ST
            // ソフトゾーン取得
            String softZone = "";
            ItemHandler itemH = new ItemHandler(getConnection());
            ItemSearchKey iskey = new ItemSearchKey();
            iskey.setConsignorCode(WmsParam.EMPTYPB_CONSIGNORCODE);
            iskey.setItemCode(WmsParam.EMPTYPB_ITEMCODE);
            Item item = (Item)itemH.findPrimary(iskey);
            if (item != null)
            {
                softZone = item.getSoftZoneId();
            }
            // 2010/05/11 Y.Osawa ADD ED

            // <jp> Palletインスタンス生成</jp>
            // <en> Generates the Pallet
            // instance.</en>
            Pallet plt = new Pallet();
            plt.setPalletId(pl.getPalletId());
            plt.setCurrentStationNo(pl.getCurrentStationNo());
            plt.setWhStationNo(pl.getWhStationNo());
            plt.setAllocationFlag(Pallet.ALLOCATION_FLAG_ALLOCATED);
            plt.setHeight(pl.getHeight());
            plt.setBcrData(pl.getBcrData());
            plt.setEmptyFlag(Pallet.EMPTY_FLAG_EMPTY);
            plt.setRegistPname(pName);
            plt.setLastUpdatePname(pName);
            // 2010/05/11 Y.Osawa ADD ST
            if (!StringUtil.isBlank(softZone))
            {
                plt.setSoftZoneId(softZone);
            }
            // 2010/05/11 Y.Osawa ADD ED

            palletH.create(plt);
        }
        catch (DataExistsException e)
        {
            // <jp> 同一パレットIDで既に存在</jp>
            // <en> The data already exists with same
            // pallet ID.</en>
            throw new InvalidDefineException(e.getMessage());
        }
        catch (ScheduleException e)
        {
            // パレット情報の倉庫ステーションNoが不正の場合、若しくは空の場合
            throw new InvalidDefineException(e.getMessage());
        }
        catch (NoPrimaryException e)
        {
            // パレット情報の倉庫ステーションNoにに紐付くエリアが複数存在する場合
            throw new InvalidDefineException(e.getMessage());
        }
    }

    /**
     * 完了処理を行える作業情報かどうかをチェックします。
     * 同一搬送キーの中に複数の作業状態があるデータは不正とします。
     * 作業情報に完了のデータがある場合は完了処理を行えないものとします。
     *
     * @param carryKey 搬送キー
     *
     * @return 完了処理を行えるか否か true:行える false:行えない
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws InvalidDefineException
     *         データベースの更新条件に不整合があった場合に通知されます。
     */
    private boolean isCompletableWork(String carryKey)
            throws ReadWriteException,
                InvalidDefineException
    {
        // 搬送データに紐付く作業情報がない場合、または複数の状態の作業情報がある場合は、エラーとする
        WorkInfoHandler workInfoH = _wiH;
        WorkInfoSearchKey wiKey = new WorkInfoSearchKey();

        wiKey.setStatusFlagCollect();
        wiKey.setSystemConnKey(carryKey);
        wiKey.setStatusFlag(WorkInfo.STATUS_FLAG_DELETE, "!=");

        wiKey.setStatusFlagGroup();

        if (1 != workInfoH.count(wiKey))
        {
            throw new InvalidDefineException();
        }

        wiKey.clear();
        wiKey.setSystemConnKey(carryKey);
        wiKey.setStatusFlag(WorkInfo.STATUS_FLAG_COMPLETION);
        wiKey.setStatusFlag(WorkInfo.STATUS_FLAG_DELETE, "!=");

        // 作業情報がすでに完了している場合、更新処理は行いません。
        return (0 == workInfoH.count(wiKey));
    }

    /**
     * 搬送キーに紐付く作業の完了処理を行います。<BR>
     * 作業情報を完了にし、在庫の更新を行います。<BR>
     * また、作業情報に対する実績送信情報の作成、紐付く予定情報の更新を行います。<BR>
     *
     * @param carryKey 搬送キー
     * @param carryKind 搬送データの搬送区分
     * @param resultLocation 作業情報にセットする結果ロケーションNo.
     * @param compDivision 作業完了区分（欠品完了か通常完了か空出荷か）
     *
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         変更、削除すべきデータが見つからない場合に通知されます。
     * @throws InvalidDefineException
     *         データベースの更新条件に不整合があった場合に通知されます。
     */
    private void completeWork(CarryInfo ci, String carryKind, String resultLocation, CARRY_COMPLETE compDivision)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        WorkInfoSearchKey wiKey = new WorkInfoSearchKey();
        wiKey.setSystemConnKey(ci.getCarryKey());
        wiKey.setPlanUkeyOrder(true);
        WorkInfo[] works = (WorkInfo[])_wiH.find(wiKey);

        if (ArrayUtil.isEmpty(works))
        {
            return;
        }

        int numWorks = works.length;
        for (int i = 0; i < numWorks; i++)
        {
            WorkInfo currWork = works[i];
            // 作業情報を完了し、実績を作成します。
            WorkInfo upedWinfo = completeWorkinfo(currWork, resultLocation, compDivision);
            // 在庫を更新します。
            updateStock(upedWinfo, carryKind, compDivision, ci);

            // 紐付く予定情報を更新する
            // 同一予定に紐付く作業情報の更新がすべて処理されてから予定情報の更新を行います。
            if (numWorks > 1)
            {
                if (i == 0)
                {
                    continue;
                }
                else
                {
                    WorkInfo prevWork = works[i - 1];
                    if (!prevWork.getPlanUkey().equals(currWork.getPlanUkey()))
                    {
                        updatePlan(prevWork.getPlanUkey(), prevWork.getJobType());
                    }
                }
            }
            if (i == (numWorks - 1))
            {
                updatePlan(currWork.getPlanUkey(), currWork.getJobType());
            }
        }
    }

    /**
     * 作業情報の完了処理を行います。 同時に、実績を作成します。
     *
     * @param winfo 完了する作業情報
     * @param asResultLocation 結果ロケーションNo.
     * @param compDivision 作業完了区分（欠品完了か通常完了か空出荷か）
     * @return 更新後の作業情報
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         変更すべきデータが見つからない場合に通知されます。
     * @throws InvalidDefineException
     *         データベースの更新条件に不整合があった場合に通知されます。
     */
    private WorkInfo completeWorkinfo(WorkInfo winfo, String asResultLocation, CARRY_COMPLETE compDivision)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        Connection conn = getConnection();
        AsWorkInfoController wic = new AsWorkInfoController(conn, getCaller());
        AreaController areaC = getAreaCtlr();
        try
        {
            // AS/RS型ロケーションをParamLocationに変換
            String resultLoc = areaC.toParamLocation(asResultLocation);

            // 作業日の取得
            String workDay = getWarenaviSysCtlr().getWorkDay();

            // set result work info
            WorkInfo resultWork = new WorkInfo();
            int planQty = winfo.getPlanQty();

            // 欠品完了フラグ
            boolean isShortComp = false;
            if ((CARRY_COMPLETE.SHORTAGE == compDivision) || (CARRY_COMPLETE.ERROR_SHORTAGE == compDivision))
            {
                isShortComp = true;
            }
            if (winfo.getJobType().equals(WorkInfo.JOB_TYPE_EMERGENCY_REPLENISHMENT)
                    || winfo.getJobType().equals(WorkInfo.JOB_TYPE_NORMAL_REPLENISHMENT))
            {
                isShortComp = false;
            }

            // check aera type
            String planAreaNo = winfo.getPlanAreaNo();

            // 仮置在庫を作成するエリア
            boolean isTempAreaType = (TEMPORARY_AREA_TYPE.CREATE_TMP_ANY == areaC.getTemporaryAreaType(planAreaNo));

            // DB更新用のエンティティのセット --------------------------------------------
            // 欠品かつ仮置エリアに移動しない場合、または空出荷の場合は欠品数を計上します
            if ((isShortComp) && (!isTempAreaType) || (CARRY_COMPLETE.ERROR_SHORTAGE == compDivision))
            {
                resultWork.setShortageQty(planQty);
            }
            else
            {
                resultWork.setResultQty(planQty);
            }

            // 欠品かつ仮置在庫を作成するエリアかつ作業区分が入庫、予定外入庫の場合は実績を仮置棚とする
            // 空出荷の場合は除外
            if ((isShortComp)
                    && (isTempAreaType)
                    && (CARRY_COMPLETE.ERROR_SHORTAGE != compDivision)
                    && (WorkInfo.JOB_TYPE_STORAGE.equals(winfo.getJobType())
                            || WorkInfo.JOB_TYPE_NOPLAN_STORAGE.equals(winfo.getJobType()) || WorkInfo.JOB_TYPE_RESTORING.equals(winfo.getJobType())))
            {
                resultWork.setStockId("");
                resultWork.setResultAreaNo(areaC.getTemporaryArea(planAreaNo));
                resultWork.setResultLocationNo(WmsParam.DEFAULT_LOCATION_NO);
            }
            else
            {
                resultWork.setResultAreaNo(planAreaNo);
                resultWork.setResultLocationNo(resultLoc);
            }
            resultWork.setResultLotNo(winfo.getPlanLotNo());
            resultWork.setReasonType(winfo.getReasonType());

            resultWork.setWorkDay(workDay);

            // build user info. for this work
            String userId = winfo.getUserId();
            String terminalNo = winfo.getTerminalNo();
            WmsUserInfo ui = WmsUserInfo.buildUserInfo(conn, userId, SystemDefine.HARDWARE_TYPE_ASRS, terminalNo);

            // complete work
            wic.completeWorkInfo(winfo, resultWork, WorkInfo.STATUS_FLAG_COMPLETION, ui);

            // 戻り値用のエンティティセット -------------------------------------
            // 在庫を更新するため、元作業の書き換えを行う
            // 欠品の場合は欠品数を計上する
            if (isShortComp)
            {
                winfo.setShortageQty(winfo.getPlanQty());
            }
            else
            {
                winfo.setResultQty(winfo.getPlanQty());
            }
            winfo.setStatusFlag(WorkInfo.STATUS_FLAG_COMPLETION);

            // 欠品かつ仮置在庫を作成するエリアかつ作業区分が入庫の場合は実績を仮置棚とする
            // 空出荷の場合は除外
            // 在庫更新で使用するのでStockIdは更新しない
            if ((isShortComp)
                    && (isTempAreaType)
                    && (CARRY_COMPLETE.ERROR_SHORTAGE != compDivision)
                    && (WorkInfo.JOB_TYPE_STORAGE.equals(winfo.getJobType())
                            || WorkInfo.JOB_TYPE_NOPLAN_STORAGE.equals(winfo.getJobType()) || WorkInfo.JOB_TYPE_RESTORING.equals(winfo.getJobType())))
            {
                winfo.setResultAreaNo(areaC.getTemporaryArea(planAreaNo));
                winfo.setResultLocationNo(WmsParam.DEFAULT_LOCATION_NO);
            }
            else
            {
                winfo.setResultAreaNo(planAreaNo);
                winfo.setResultLocationNo(resultLoc);
            }
            winfo.setResultLotNo(winfo.getPlanLotNo());
            winfo.setWorkDay(workDay);

            winfo.setLastUpdatePname(getCallerName());

            return winfo;
        }
        catch (DataExistsException e)
        {
            throw new InvalidDefineException();
        }
        catch (NoPrimaryException e)
        {
            throw new InvalidDefineException();
        }
        catch (ScheduleException e)
        {
            throw new InvalidDefineException();
        }
    }

    /**
     * AreaControllerを返します。
     *
     * @return AreaController
     * @throws ReadWriteException データベースアクセスエラー
     */
    private AreaController getAreaCtlr()
            throws ReadWriteException
    {
        if (null == _areaCtlr)
        {
            _areaCtlr = new AreaController(getConnection(), getCaller());
        }
        return _areaCtlr;
    }

    /**
     * 作業情報に紐付く在庫の更新を行います。
     *
     * @param winfo 搬送データに紐付く作業情報
     * @param carryKind 搬送データの搬送区分
     * @param compDivision 作業完了区分（欠品完了か通常完了か空出荷か）
     *
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         変更対象となるStockがデータベースに見つからなかった場合に通知されます。
     * @throws InvalidDefineException
     *         データベースの更新条件に不整合があった場合に通知されます。
     */
    private void updateStock(WorkInfo winfo, String carryKind, CARRY_COMPLETE compDivision, CarryInfo ci)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        Connection conn = getConnection();

        // 作業に紐付く在庫を検索する
        Stock stock = getStock(winfo.getStockId());
        String jobType = winfo.getJobType();

        String userId = winfo.getUserId();
        String termNo = winfo.getTerminalNo();
        WmsUserInfo ui = WmsUserInfo.buildUserInfo(conn, userId, SystemDefine.HARDWARE_TYPE_ASRS, termNo);

        if (WorkInfo.JOB_TYPE_STORAGE.equals(jobType) || WorkInfo.JOB_TYPE_NOPLAN_STORAGE.equals(jobType)
                || WorkInfo.JOB_TYPE_RESTORING.equals(jobType) || CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(carryKind))
        {
            // 作業情報が入庫、または搬送データが直行の場合、在庫数の加算を行う
            if (null == stock.getLocationNo())
            {
                stock.setLocationNo(winfo.getResultLocationNo());
            }
            updateStockPlus(winfo, stock, carryKind, ui, compDivision, ci);
        }
        else
        {
            // 出庫の場合在庫を減算する
            updateStockMinus(winfo, stock, ui);
        }
    }

    /**
     * 指定された予定一意キーの予定情報の更新処理を行います。<BR>
     * 作業種別が、作業情報の入庫または出庫以外の場合、予定情報はないとみなし、処理は行いません。<BR>
     * 一意キーに紐付く作業情報を取得し、実績数（実績数＋欠品数）が
     * 予定情報の予定数より少ない場合は一部完了など各状態に、予定数分作業が行われた場合は、完了にします。<BR>
     *
     * @param planUkey 予定一意キー
     * @param jobType 作業種別
     *
     * @throws ReadWriteException
     *         データベースに対する処理で異常が発生した場合に通知します。
     * @throws InvalidDefineException
     *         データベースの更新条件に不整合があった場合に通知されます。
     */
    private void updatePlan(String planUkey, String jobType)
            throws ReadWriteException,
                InvalidDefineException
    {
        // 入庫、出庫、再入庫以外の場合は、予定情報がないので、処理を行いません
        if (WorkInfo.JOB_TYPE_STORAGE.equals(jobType))
        {
            updateStoragePlan(planUkey);
        }
        else if (WorkInfo.JOB_TYPE_RETRIEVAL.equals(jobType))
        {
            updateRetrievalPlan(planUkey);
        }
        else if (WorkInfo.JOB_TYPE_RESTORING.equals(jobType))
        {
            updateReStoringPlan(planUkey);
        }
    }

    /**
     * 予定一意キーに紐付く作業情報から、実績数を取得します。<BR>
     * また、該当する作業情報がない場合、０を返します。<BR>
     *
     * @param planUkey 予定一意キー
     *
     * @return 予定一意キーで集約した実績数
     * @throws ReadWriteException
     *         データベースに対する処理で異常が発生した場合に通知します。
     */
    private int getResultQty(String planUkey)
            throws ReadWriteException
    {
        return getQty(planUkey, WorkInfo.RESULT_QTY);
    }

    /**
     * 予定一意キーに紐付く作業情報から、欠品数を取得します。<BR>
     * また、該当する作業情報がない場合、０を返します。<BR>
     *
     * @param planUkey 予定一意キー
     *
     * @return 予定一意キーで集約した欠品数
     * @throws ReadWriteException
     *         データベースに対する処理で異常が発生した場合に通知します。
     */
    private int getShortageQty(String planUkey)
            throws ReadWriteException
    {
        return getQty(planUkey, WorkInfo.SHORTAGE_QTY);
    }

    /**
     * 作業情報から予定一意キーごとの数量を合計して返します。
     *
     * @param planUkey 予定一意キー
     * @param sumField 数量フィールド(実績数, 欠品数)
     * @return 合計数
     * @throws ReadWriteException
     *         データベースに対する処理で異常が発生した場合に通知します。
     * @since 2007-05-08
     */
    private int getQty(String planUkey, FieldName sumField)
            throws ReadWriteException
    {
        WorkInfoHandler workInfoH = _wiH;
        WorkInfoSearchKey wiKey = new WorkInfoSearchKey();

        try
        {
            wiKey.setPlanUkey(planUkey);
            wiKey.setCollect(sumField, "SUM", sumField);
            wiKey.setPlanUkeyGroup();

            WorkInfo winfo = (WorkInfo)workInfoH.findPrimary(wiKey);

            // 作業情報がない場合、数量は0を返します。
            if (null == winfo)
            {
                return 0;
            }
            return winfo.getBigDecimal(sumField, BigDecimal.ZERO).intValue();
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * 入庫予定情報に紐付く作業情報から実績数を求め、入庫予定情報の更新を行います。<BR>
     * 実績数が予定数未満の場合は一部完了に、それ以外の場合は完了に更新します。<BR>
     * 一部完了に更新する場合は同一予定一意キーで作業情報を検索し、開始済、作業中のデータが存在する場合は更新を行いません。<BR>
     *
     * @param planUkey 予定一意キー
     *
     * @throws ReadWriteException
     *         データベースに対する処理で異常が発生した場合に通知します。
     * @throws InvalidDefineException
     *         データベースの更新条件に不整合があった場合に通知されます。
     */
    private void updateStoragePlan(String planUkey)
            throws ReadWriteException,
                InvalidDefineException
    {
        // TODO use storage plan controller
        StoragePlanHandler storagePlanH = _splanH;
        try
        {
            // 入庫予定情報を取得する
            StoragePlanSearchKey splanKey = new StoragePlanSearchKey();
            splanKey.setPlanUkey(planUkey);

            StoragePlan sp = (StoragePlan)storagePlanH.findPrimary(splanKey);
            if (null == sp)
            {
                throw new NotFoundException();
            }

            // 作業情報から作業結果数を取得する
            int resultQty = getResultQty(planUkey);
            int shortageQty = getShortageQty(planUkey);

            String workDay = getWarenaviSysCtlr().getWorkDay();

            // 入庫予定情報を更新する
            StoragePlanAlterKey splanAKey = new StoragePlanAlterKey();
            splanAKey.setPlanUkey(planUkey);

            // 実績が予定より少ない場合は作業中に更新する
            if ((resultQty + shortageQty) < sp.getPlanQty())
            {
                splanAKey.updateStatusFlag(StoragePlan.STATUS_FLAG_NOWWORKING);
            }
            else
            {
                splanAKey.updateStatusFlag(StoragePlan.STATUS_FLAG_COMPLETION);

                if (WmsParam.EMPTYPB_CONSIGNORCODE.equals(sp.getConsignorCode())
                        && WmsParam.EMPTYPB_ITEMCODE.equals(sp.getItemCode()))
                {
                    // 空パレットの場合、報告フラグをONにする
                    splanAKey.updateReportFlag(StoragePlan.REPORT_FLAG_REPORT);
                }
            }
            splanAKey.updateResultQty(resultQty);
            splanAKey.updateShortageQty(shortageQty);
            splanAKey.updateWorkDay(workDay);
            splanAKey.updateLastUpdatePname(getCallerName());

            storagePlanH.modify(splanAKey);
        }
        catch (ScheduleException e)
        {
            throw new InvalidDefineException();
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException(e);
        }
        catch (NotFoundException e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * 出庫予定情報に紐付く作業情報から実績数を求め、出庫予定情報の更新を行います。<BR>
     * 実績数が予定数未満の場合は同一予定一意キーで作業情報を検索し各状態に、それ以外の場合は完了に更新します。<BR>
     * 予定数未満の場合は作業中＞開始済＞一部完了の優先順で更新を行います。<BR>
     *
     * @param planUkey 予定一意キー
     *
     * @throws ReadWriteException
     *         データベースに対する処理で異常が発生した場合に通知します。
     * @throws InvalidDefineException
     *         データベースの更新条件に不整合があった場合に通知されます。
     */
    private void updateRetrievalPlan(String planUkey)
            throws ReadWriteException,
                InvalidDefineException
    {
        RetrievalPlanHandler retrievalPlanH = _rplanH;
        try
        {
            // 出庫予定情報を取得する
            RetrievalPlanSearchKey rpKey = new RetrievalPlanSearchKey();
            rpKey.setPlanUkey(planUkey);

            RetrievalPlan rp = (RetrievalPlan)retrievalPlanH.findPrimary(rpKey);
            if (null == rp)
            {
                throw new NotFoundException();
            }

            // 作業情報から作業結果数を取得する
            int resultQty = getResultQty(planUkey);
            int shortageQty = getShortageQty(planUkey);

            String workDay = getWarenaviSysCtlr().getWorkDay();

            // 出庫予定情報を更新する
            RetrievalPlanAlterKey rpAKey = new RetrievalPlanAlterKey();
            rpAKey.setPlanUkey(planUkey);

            // 実績が予定より少ない場合は作業中に更新する
            if ((resultQty + shortageQty) < rp.getPlanQty())
            {
                rpAKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_NOWWORKING);
            }
            else
            {
                rpAKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);

                if (WmsParam.EMPTYPB_CONSIGNORCODE.equals(rp.getConsignorCode())
                        && WmsParam.EMPTYPB_ITEMCODE.equals(rp.getItemCode()))
                {
                    // 空パレットの場合、報告フラグをONにする
                    rpAKey.updateReportFlag(RetrievalPlan.REPORT_FLAG_REPORT);
                }
            }
            rpAKey.updateResultQty(resultQty);
            rpAKey.updateShortageQty(shortageQty);
            rpAKey.updateWorkDay(workDay);
            rpAKey.updateLastUpdatePname(getCallerName());

            retrievalPlanH.modify(rpAKey);
        }
        catch (ScheduleException e)
        {
            throw new InvalidDefineException();
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException(e);
        }
        catch (NotFoundException e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * 再入庫予定情報に紐付く作業情報から実績数を求め、再入庫予定情報の更新を行います。<BR>
     *
     * @param planUkey 予定一意キー
     *
     * @throws ReadWriteException
     *         データベースに対する処理で異常が発生した場合に通知します。
     * @throws InvalidDefineException
     *         データベースの更新条件に不整合があった場合に通知されます。
     */
    private void updateReStoringPlan(String planUkey)
            throws ReadWriteException,
                InvalidDefineException
    {
        ReStoringPlanHandler restoringPlanH = _rsplanH;
        try
        {
            // 再入庫予定情報を取得する
            ReStoringPlanSearchKey rsplanKey = new ReStoringPlanSearchKey();
            rsplanKey.setPlanUkey(planUkey);

            ReStoringPlan sp = (ReStoringPlan)restoringPlanH.findPrimary(rsplanKey);
            if (null == sp)
            {
                throw new NotFoundException();
            }

            // 作業情報から作業結果数を取得する
            int resultQty = getResultQty(planUkey);
            int shortageQty = getShortageQty(planUkey);

            String workDay = getWarenaviSysCtlr().getWorkDay();

            // 再入庫予定情報を更新する
            ReStoringPlanAlterKey rsplanAKey = new ReStoringPlanAlterKey();
            rsplanAKey.setPlanUkey(planUkey);

            rsplanAKey.updateStatusFlag(ReStoringPlan.STATUS_FLAG_COMPLETION);
            rsplanAKey.updateResultQty(resultQty);
            rsplanAKey.updateShortageQty(shortageQty);
            rsplanAKey.updateWorkDay(workDay);
            rsplanAKey.updateLastUpdatePname(getCallerName());

            restoringPlanH.modify(rsplanAKey);
        }
        catch (ScheduleException e)
        {
            throw new InvalidDefineException();
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException(e);
        }
        catch (NotFoundException e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * 搬送データ削除時に、搬送データの搬送区分をもとに棚の状態を更新します。
     *
     * @param conn データベースコネクション
     * @param ci 搬送データ
     * @param shelfNumber 棚No.
     * @throws ReadWriteException
     *         データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException
     *         更新内容がセットされていない場合に通知されます。
     * @throws NotFoundException
     *         変更すべき情報が見つからない場合に通知されます。
     */
    private void updateShelf(Connection conn, CarryInfo ci, String shelfNumber)
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException
    {
        // <jp> CarryInformationの搬送区分を元に棚の開放を行う。</jp>
        // <en> It releases the shelves according to
        // the carrykind of CarryInformation.</en>

        // <jp> 入庫の場合、搬送先の棚を空棚にする</jp>
        // <en> If it is a storage, it alters the
        // receiving location to 'empty
        // locations'.</en>
        if (CarryInfo.CARRY_FLAG_STORAGE.equals(ci.getCarryFlag()))
        {
            // <jp> 入庫先の棚が確定している場合のみ空棚にする。</jp>
            // <jp>
            // 棚決定前の入庫データ、二重格納、空出荷で搬送先がない場合を考慮して</jp>
            // <jp> 搬送先はパレットの現在位置より判断する。</jp>
            // <en> It alters to empty locations only
            // when the location at the recieving
            // station is fixed.</en>
            // <en> Taking the storage data before
            // location fix, or the possible case of no
            // destination due</en>
            // <en> to double occupation or the empty
            // retrieval into account, the destination
            // will be determined based on the current
            // position of the pallet.</en>
            // 同一棚に再入庫しないステーションへ出庫したピッキング出庫または積増入庫の戻り入庫で、
            // 入庫棚が未決定（到着または開始）の場合は空棚に更新しない
            if (!(CarryInfo.RESTORING_FLAG_NOT_SAME_LOC.equals(ci.getRestoringFlag())
                    && (CarryInfo.RETRIEVAL_DETAIL_PICKING.equals(ci.getRetrievalDetail()) || CarryInfo.RETRIEVAL_DETAIL_ADD_STORING.equals(ci.getRetrievalDetail())) && (CarryInfo.CMD_STATUS_ARRIVAL.equals(ci.getCmdStatus()) || CarryInfo.CMD_STATUS_START.equals(ci.getCmdStatus()))))
            {
                updateShelfStatusFlag(shelfNumber, Shelf.LOCATION_STATUS_FLAG_EMPTY);
            }
        }
        // <jp> 出庫の場合、搬送元の棚を空棚にする</jp>
        // <en> If it is a retrieval, it alters the
        // sending location to 'empty locations'.</en>
        else if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(ci.getCarryFlag()))
        {
            // <jp>
            // 出庫完了より前の状態、または異常の場合、搬送元の棚を空棚にする</jp>
            // <en> If the Carry process has not
            // proceeded to retrieval completion or
            // error status, it should alter the
            // sending location to 'empty'.</en>
            if (Integer.parseInt(ci.getCmdStatus()) < Integer.parseInt(CarryInfo.CMD_STATUS_COMP_RETRIEVAL)
                    || CarryInfo.CMD_STATUS_ERROR.equals(ci.getCmdStatus()))
            {
                updateShelfStatusFlag(shelfNumber, Shelf.LOCATION_STATUS_FLAG_EMPTY);
            }
            // <jp>
            // 搬送状態が完了以降であれば、出庫指示詳細がユニット出庫以外、再入庫フラグが同一棚に再入庫の場合のみ</jp>
            // <jp> 棚が開放されていないので、出庫棚を空棚にする。</jp>
            // <en> If the Carry process has proceeded
            // to completion or further, the location
            // could possibly</en>
            // <en> be yet be released if the retrieval
            // instruction detail is anything other
            // than unit retrieval</en>
            // <en> and if re-storage flag is being
            // re-stored to the same location.</en>
            else if ((!CarryInfo.RETRIEVAL_DETAIL_UNIT.equals(ci.getRetrievalDetail()))
                    && (CarryInfo.RESTORING_FLAG_SAME_LOC.equals(ci.getRestoringFlag())))
            {
                updateShelfStatusFlag(shelfNumber, Shelf.LOCATION_STATUS_FLAG_EMPTY);
            }

            // <jp>
            // ダブルディープの在庫確認は同一棚に再入庫しないになってるので、出庫棚を空棚にする</jp>
            if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(ci.getRetrievalDetail())
                    && CarryInfo.RESTORING_FLAG_NOT_SAME_LOC.equals(ci.getRestoringFlag()))
            {
                updateShelfStatusFlag(ci.getRetrievalStationNo(), Shelf.LOCATION_STATUS_FLAG_EMPTY);
            }
        }
        // <jp> 棚間移動の場合、搬送元、搬送先の棚を空棚にする</jp>
        // <en> If it is a location to location move,
        // it alters the sending location and receiving
        // location to 'empty locations'.</en>
        else if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
        {
            // 搬送元ステーションの棚を空棚にする
            updateShelfStatusFlag(shelfNumber, Shelf.LOCATION_STATUS_FLAG_EMPTY);

            // 棚間移動データの場合は、搬送先の棚を空棚にする。
            updateShelStatusFlagForDirect(conn, ci.getDestStationNo());
        }
    }

    /**
     * 出庫搬送データ削除時の在庫の更新を行います。<BR>
     * 指定された出庫指示詳細によって、更新方法が異なります。
     *
     * @param winfo 削除する作業情報
     * @param retrievalDetail 出庫指示詳細
     *
     * @return 更新前の在庫情報
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         変更、削除すべきデータが見つからない場合に通知されます。
     * @throws InvalidDefineException
     *         データ更新条件に不整合があった場合に通知します。
     */
    private Stock cancelRetrievalStock(WorkInfo winfo, String retrievalDetail)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        Stock stk = getStock(winfo.getStockId());

        String pName = getCallerName();
        StockHandler stockH = _stockH;

        // <jp> 積み増し入庫の時</jp>
        // <en> If replenishing,</en>
        if (CarryInfo.RETRIEVAL_DETAIL_ADD_STORING.equals(retrievalDetail))
        {
            // <jp> 積み合わせの時</jp>
            // <en> If loading the cargo mixed, </en>
            if (0 == stk.getStockQty())
            {
                // <jp> Stockテーブルから削除</jp>
                // <en> Deleting from Stock table.</en>
                StockSearchKey stkKey = new StockSearchKey();
                stkKey.setStockId(stk.getStockId());

                stockH.drop(stkKey);
            }
            // <jp> 積み増しの時</jp>
            // <en> If it is a replenishiment, </en>
            else
            {
                // 入庫予定数を更新する
                StockAlterKey stkAKey = new StockAlterKey();
                stkAKey.setStockId(stk.getStockId());
                stkAKey.updatePlanQty(stk.getPlanQty() - winfo.getPlanQty());
                stkAKey.updateLastUpdatePname(pName);

                stockH.modify(stkAKey);
            }
        }
        else
        {
            // 在庫確認時、作業数は０のため、更新は行いません
            if (0 == winfo.getPlanQty())
            {
                return stk;
            }
            // <jp> 出庫作業の時</jp>
            // <en> If it is a retrieval,</en>
            StockAlterKey stkAKey = new StockAlterKey();
            stkAKey.setStockId(stk.getStockId());
            stkAKey.updateAllocationQty(winfo.getPlanQty() + stk.getAllocationQty());
            stkAKey.updateLastUpdatePname(pName);

            stockH.modify(stkAKey);
        }

        return stk;
    }

    /**
     * 搬送キーに紐付く作業表示データを削除します。
     * また、搬送区分が直行の場合は削除時にエラーが起こっても例外を通知しません。
     *
     * @param conn データベースコネクション
     * @param carryKey 搬送キー
     * @param carryKind 搬送区分
     * @throws ReadWriteException
     *         データベースに対する処理で異常が発生した場合に通知します。
     * @throws NotFoundException 対象データがない場合に通知します。
     */
    private void dropOperationDisplay(Connection conn, String carryKey, String carryKind)
            throws ReadWriteException,
                NotFoundException
    {
        // <jp> 作業表示データの有無を確認する。作業表示データがあれば削除する。</jp>
        // <en> Checks whether/not the data of on-line
        // indication. Delete if there are any.</en>
        OperationDisplayHandler odh = new OperationDisplayHandler(conn);
        OperationDisplaySearchKey odkey = new OperationDisplaySearchKey();
        odkey.setCarryKey(carryKey);
        if (odh.count(odkey) != 0)
        {
            // 直行処理の場合は、例外を通知しません
            if (CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(carryKind))
            {
                try
                {
                    odh.drop(odkey);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                odh.drop(odkey);
            }
        }
    }

    // ------------------------------------------------------------
    // private methods
    // ------------------------------------------------------------
    /**
     * <jp> 指定された作業種別が実績を作成しない作業かどうか検証します。
     * 実績を作成しない作業はこのクラスのstatic変数<code>NO_RESULT_OPERATIONS</code>に規定されており
     * そこに登録されている作業種別であればtrue、存在しなければfalseを返します。
     *
     * @param workType 作業種別 <code>InOutResult</code>の作業種別で規定されている値である必要があります。
     * @return 指定されたworkTypeが実績を作成しない作業種別であればtrue、そうでなければfalse
     *         </jp>
     */
    /**
     * <en> Examines whether/not the specified work
     * type will not create the result data. The work
     * which will not create the result data is defined
     * in static variable
     * <code>NO_RESULT_OPERATIONS</code> of this
     * class. If the work type is registered by that
     * variable, it returns true and false if not.
     *
     * @param workType work type -it needs to be the
     *        value defined by work type of
     *        <code>InOutResult</code>.
     * @return :If specified workType will not create
     *         the resuld data, it returns true, or
     *         false if not. </en>
     */
    private boolean isResultOperation(String workType)
    {
        for (int i = 0; i < NO_RESULT_OPERATIONS.length; i++)
        {
            if (NO_RESULT_OPERATIONS[i].equals(workType))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * パレットIDをキーに在庫を検索します。 該当する在庫情報が存在する場合、trueを返します。
     * 該当する在庫情報が存在しない場合、falseをかえします。
     *
     * @param palletID パレットID
     * @return 該当する在庫情報が存在するか否か
     * @throws ReadWriteException
     *         データベースに対する処理で異常が発生した場合に通知します。
     */
    private boolean isExistStock(String palletID)
            throws ReadWriteException
    {
        StockSearchKey stkKey = new StockSearchKey();
        StockHandler stkh = _stockH;

        stkKey.setPalletId(palletID);
        stkKey.setStockQty(0, ">");
        return (stkh.count(stkKey) > 0);
    }

    /**
     * 棚間移動搬送先ステーションの棚状態を空棚にします。<BR>
     * 搬送データキャンセル時に搬送先ステーションの棚状態を変更します。
     *
     * @param conn データベース接続用 Connection
     * @param destStationNo 搬送先ステーション
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     */
    private void updateShelStatusFlagForDirect(Connection conn, String destStationNo)
            throws ReadWriteException
    {
        try
        {
            Station dirst = StationFactory.makeStation(conn, destStationNo);
            if (dirst instanceof Shelf)
            {
                Shelf dirshf = (Shelf)dirst;
                String statusFlag = dirshf.getStatusFlag();
                String stationNo = dirshf.getStationNo();

                if (Shelf.LOCATION_STATUS_FLAG_RESERVATION.equals(statusFlag))
                {
                    updateShelfStatusFlag(stationNo, Shelf.LOCATION_STATUS_FLAG_EMPTY);
                }
            }
        }
        catch (NotFoundException e)
        {
            throw new ReadWriteException(e);
        }
        catch (InvalidDefineException e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * 指定された棚No.の棚状態を更新します
     *
     * @param stno 棚No.
     * @param presence 棚状態
     * @throws ReadWriteException
     *         データベースとの接続で異常が発生した場合に通知されます。
     */
    private void updateShelfStatusFlag(String stno, String presence)
            throws ReadWriteException
    {
        try
        {
            Station updSt = StationFactory.makeStation(getConnection(), stno);
            // 棚の場合のみ更新を行う
            if (updSt instanceof Shelf)
            {
                ShelfHandler shelfH = _shelfH;
                ShelfAlterKey shelfAKey = new ShelfAlterKey();

                // 空棚に更新する場合
                if (Shelf.LOCATION_STATUS_FLAG_EMPTY.equals(presence))
                {
                    FreeAllocationShelfOperator freeshelfop = new FreeAllocationShelfOperator(getConnection(), stno);
                    // フリーアロケーション運用の場合、荷幅、棚使用フラグをフリーに更新
                    if (freeshelfop.isFreeAllocationStation())
                    {
                        freeshelfop.alterFreeWidth();
                    }
                }

                shelfAKey.setStationNo(stno);
                shelfAKey.updateStatusFlag(presence);

                shelfH.modify(shelfAKey);
            }
        }
        catch (NotFoundException e)
        {
            // 該当棚が存在しない場合は、処理を行いません。
        }
        catch (InvalidDefineException e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * 指定されたパレットの状態を更新します。
     *
     * @param palletId パレットID
     * @param status パレットの状態
     *
     * @throws ReadWriteException
     *         データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException
     *         更新内容がセットされていない場合に通知されます。
     * @throws NotFoundException
     *         変更すべき情報が見つからない場合に通知されます。
     */
    private void updatePalletEmpty(String palletId, String status)
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException
    {
        PalletHandler palletH = _plH;

        PalletAlterKey plAKey = new PalletAlterKey();
        plAKey.updateEmptyFlag(status);
        plAKey.setPalletId(palletId);
        plAKey.updateLastUpdatePname(getCallerName());

        palletH.modify(plAKey);
    }

    /**
     * アイル情報の在庫確認中フラグをOFFに更新します。<BR>
     *
     * 指定された搬送データが在庫確認又は空棚確認搬送データの場合、
     * 同じアイル中に、在庫確認又は空棚確認の搬送データが存在しなければ
     * アイル情報の在庫確認中フラグを在庫確認未作業に変更します。
     *
     * @param ci 搬送データ
     *
     * @throws ReadWriteException
     *         データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException
     *         変更すべき情報が見つからない場合に通知されます。
     */
    private void updateAisleInventoryCheckOff(CarryInfo ci)
            throws ReadWriteException,
                NotFoundException
    {
        CarryInfoHandler carryInfoH = _ciH;

        // <jp>
        // CarryInforrmationが在庫確認または空棚確認データであれば他の在庫確認データを数える。</jp>
        // <en> If the CarryInforrmation is either
        // inventory check data or empty location check
        // data, count the other inventory check data.
        // </en>
        if (isInvCheck(ci) || isEmpCheck(ci))
        {
            CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
            ciKey.setAisleStationNo(ci.getAisleStationNo());
            ciKey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL);
            ciKey.setCmdStatus(CarryInfo.CMD_STATUS_ARRIVAL, "<");
            ciKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK, "=", "(", "", false);
            ciKey.setPriority(CarryInfo.PRIORITY_CHECK_EMPTY, "=", "", ")", true);

            if (0 == carryInfoH.count(ciKey))
            {
                // アイル表の在庫確認中フラグをOFFにする
                updateAisleInventoryCheckFlag(ci.getAisleStationNo(), Station.INVENTORY_CHECK_FLAG_UNSTART);
            }
        }
    }

    /**
     * アイル情報の在庫確認中フラグを指定された値で更新します。<BR>
     *
     * @param aisleStNo アイルステーションNo.
     * @param inventoryCheckFlag 在庫確認中フラグの更新値
     *
     * @throws ReadWriteException
     *         データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException
     *         変更すべき情報が見つからない場合に通知されます。
     */
    private void updateAisleInventoryCheckFlag(String aisleStNo, String inventoryCheckFlag)
            throws ReadWriteException,
                NotFoundException
    {
        AisleHandler aisleh = _aisleH;
        AisleAlterKey ak = new AisleAlterKey();

        ak.setStationNo(aisleStNo);
        ak.updateInventoryCheckFlag(inventoryCheckFlag);
        aisleh.modify(ak);
    }

    /**
     * <jp> ASInventoryCheck表の状態を更新します。<BR>
     * 指定された搬送データが在庫確認又は空棚確認搬送データの場合、
     * 搬送データと同じスケジュールNo.の在庫確認又は空棚確認の搬送データが存在しない場合、
     * ASInventoryCheck表のStatusを処理済に変更します。
     *
     * @param conn データベース接続用 Connection
     * @param ci 搬送データ
     * @throws ReadWriteException
     *         データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException
     *         変更すべき情報が見つからない場合に通知されます。 </jp>
     */
    /**
     * <en> If the specified CarryInformation was the
     * inventory check data or the empty location check
     * carry data, check if the carry data for
     * inventory check or empty location check exist in
     * the same schedule no. as CarryInformation. If
     * such data cannot be found, alter the Status of
     * InventoryCheck table 'to be checked'.
     *
     * @param conn : Connection with database
     * @param ci : carry data
     * @throws ReadWriteException :Notifies if
     *         exception occured when processing for
     *         database.
     * @throws NotFoundException :Notifies if data to
     *         modify cannot be found. </en>
     */
    private void updateInventoryCheckStatusOff(Connection conn, CarryInfo ci)
            throws ReadWriteException,
                NotFoundException
    {
        CarryInfoHandler ciH = _ciH;

        // <jp>
        // CarryInforrmationが在庫確認または空棚確認データであれば他の在庫確認データを数える。</jp>
        // <en> If CarryInforrmation was the data for
        // inventory check or empty location check
        // data, count otherinventory check data.</en>
        if (isInvCheck(ci) || isEmpCheck(ci))
        {
            CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
            ciKey.setScheduleNo(ci.getScheduleNo());
            ciKey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL);
            ciKey.setCmdStatus(CarryInfo.CMD_STATUS_ARRIVAL, "<");
            ciKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK, "=", "(", "", false);
            ciKey.setPriority(CarryInfo.PRIORITY_CHECK_EMPTY, "=", "", ")", true);

            if (0 == ciH.count(ciKey))
            {
                InventoryCheckHandler invChkHandler = new InventoryCheckHandler(conn);
                InventoryCheckAlterKey invChkAKey = new InventoryCheckAlterKey();

                invChkAKey.setScheduleNo(ci.getScheduleNo());
                invChkAKey.updateStatusFlag(InventoryCheck.INVENTORY_CHECK_FLAG_UNSTART);
                invChkAKey.updateLastUpdatePname(getCallerName());

                invChkHandler.modify(invChkAKey);
            }
        }
    }

    /**
     * 出庫指示詳細が在庫確認のとき true を返します。
     *
     * @param ci CarryInfo
     * @return 在庫確認のとき true
     */
    private boolean isInvCheck(CarryInfo ci)
    {
        return CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(ci.getRetrievalDetail());
    }

    /**
     * 優先区分が空棚確認のとき、trueを返します。
     *
     * @param ci CarryInfo
     * @return 空棚確認のとき、true
     */
    private boolean isEmpCheck(CarryInfo ci)
    {
        return CarryInfo.PRIORITY_CHECK_EMPTY.equals(ci.getPriority());
    }

    /**
     * 在庫に作業数を加算し、在庫更新を行います。<BR>
     * 作業が欠品完了し、在庫数が計上されない場合は、完了在庫に更新します。<BR>
     * 以下、センター在庫への更新処理。 <DIR> ・入庫待ち在庫をセンター在庫に更新<BR>
     * ・在庫数を加算<BR>
     * ・予定数を減算<BR>
     * ・入庫日がセットされていない場合、現在日時をセット<BR>
     * ・引当て数を加算（直行の場合、引当て対象外になるため、加算しません）<BR>
     * </DIR>
     *
     * @param work 更新元作業
     * @param stk 更新対象在庫
     * @param carryKind 搬送データの搬送区分
     * @param ui ユーザ情報
     * @param compDivision 作業完了区分（欠品完了か通常完了か空出荷か）
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         対象となるStockがデータベースに見つからなかった場合に通知されます。
     * @throws InvalidDefineException
     *         データベースの更新条件に不整合があった場合に通知されます。
     */
    private void updateStockPlus(WorkInfo work, Stock stk, String carryKind, WmsUserInfo ui,
            CARRY_COMPLETE compDivision, CarryInfo ci)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        try
        {
            int workQty = work.getResultQty();
            int shortageQty = work.getShortageQty();

            int planQty = stk.getPlanQty() - (workQty + shortageQty);
            if (planQty < 0)
            {
                planQty = 0;
            }
            // 更新後の在庫数を求める
            int stockQty = stk.getStockQty() + workQty;

            AsStockController stockC = getStockCtlr();

            // 在庫数が計上されない場合は、予定データが欠品完了されたとみなし完了に更新する
            // 既存在庫への積み増しの場合は、既存在庫の在庫数があるため、必ずセンター在庫になります
            if (0 == stockQty && 0 == planQty)
            {
                // 仮置在庫作成のため、数量を書き換える
                Stock tmpStock = (Stock)stk.clone();
                int tempStockQty = stk.getStockQty() + workQty + shortageQty;
                int tempAllocQty = stk.getAllocationQty() + workQty + shortageQty;
                tmpStock.setStockQty(tempStockQty);
                tmpStock.setAllocationQty(tempAllocQty);
                tmpStock.setPlanQty(planQty);

                // 空出荷の場合は仮置在庫を作成しない
                String jobType =
                        (CARRY_COMPLETE.ERROR_SHORTAGE == compDivision) ? StockHistory.JOB_TYPE_MAINTENANCE_MINUS
                                                                       : work.getJobType();
                stockC.delete(tmpStock, null, null, jobType, work.getJobType(), ui);
                return;
            }

            String workDay = getWarenaviSysCtlr().getWorkDay();
            // 欠品完了で仮置在庫作成エリアの場合、予定数分仮置在庫を作成する。
            // 空出荷の場合は除外
            AreaController areaC = getAreaCtlr();
            if ((shortageQty > 0) && (CARRY_COMPLETE.ERROR_SHORTAGE != compDivision)
                    && (TEMPORARY_AREA_TYPE.CREATE_TMP_ANY == areaC.getTemporaryAreaType(work.getPlanAreaNo())))
            {
                // 仮置在庫作成のため、数量を書き換える
                Stock tmpStock = (Stock)stk.clone();
                tmpStock.setStockQty(work.getShortageQty());
                tmpStock.setAllocationQty(work.getShortageQty());
                tmpStock.setPlanQty(0);
                stockC.insertTempStock(tmpStock, work.getJobType(), workDay, ui);
            }

            Stock nstock = new Stock();
            nstock.setPlanQty(planQty);
            nstock.setStockQty(stockQty);

            if (stk.getStockQty() == 0 && !CarryInfo.WORK_TYPE_RESTORING.equals(ci.getWorkType())
                    && !CarryInfo.RETRIEVAL_DETAIL_ADD_STORING.equals(ci.getRetrievalDetail()))
            {
                nstock.setStorageDay(workDay);
                nstock.setStorageDate(new SysDate());
            }

            // 増減区分
            String incdec_type = StockHistory.INC_DEC_TYPE_STOCK_INCREMENT;
            if (!CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(carryKind))
            {
                // 直行の場合、搬送中に引当てをかけられないように引当て可能数を計上しない
                nstock.setAllocationQty(stk.getAllocationQty() + workQty);
            }
            else
            {
                // 直行の場合、在庫数、予定数のみを計上する
                nstock.setStockQty(stockQty + planQty);
                nstock.setPlanQty(planQty - planQty);

                // 簡易直行の場合、在庫更新履歴に登録しない
                if (WmsParam.SIMPLEDIRECTTRANSFER_ITEMCODE.equals(stk.getItemCode()))
                {
                    incdec_type = null;
                }
            }
            stockC.update(stk, nstock, incdec_type, work.getJobType(), ui, work.getReasonType());
        }
        catch (ScheduleException e)
        {
            throw new InvalidDefineException();
        }
        catch (OperatorException e)
        {
            throw new NotFoundException();
        }
        catch (NoPrimaryException e)
        {
            throw new InvalidDefineException();
        }
        catch (DataExistsException e)
        {
            // do nothing.
        }
        catch (LockTimeOutException e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * 在庫から作業数を減算し、在庫更新を行います。
     * <ul>
     * <li>作業数＋欠品数が0の場合、更新を行わない
     * <li>在庫数が0になる場合、在庫を削除する
     * <li>削除した在庫のパレットに他に在庫がない場合、パレットを削除する
     * <li>残在庫数が１以上の場合、在庫数を更新する
     * </ul>
     * @param work 作業情報
     * @param stk 更新対象在庫
     * @param ui ユーザ情報
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         対象となるStockがデータベースに見つからなかった場合に通知されます。
     * @throws InvalidDefineException
     *         データベースの更新条件に不整合があった場合に通知されます。
     */
    private void updateStockMinus(WorkInfo work, Stock stk, WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        int workQty = work.getResultQty();
        int shortageQty = work.getShortageQty();

        int totalResult = (workQty + shortageQty);
        // 作業数＋欠品数が０の場合、処理を行わない
        // 在庫確認時など、作業数が0になります。
        if (0 == totalResult)
        {
            return;
        }

        try
        {
            StockController stockCtlr = getStockCtlr();

            String jobType = work.getJobType();
            String workDay = work.getWorkDay();

            stockCtlr.retrieval(stk, jobType, workQty, shortageQty, workDay, false, ui);

            if (!work.getJobType().equals(MoveWorkInfo.JOB_TYPE_EMERGENCY_REPLENISHMENT)
                    && !work.getJobType().equals(MoveWorkInfo.JOB_TYPE_NORMAL_REPLENISHMENT))
            {
                return;
            }
            MoveWorkInfoSearchKey mwikey = new MoveWorkInfoSearchKey();
            mwikey.setWorkConnKey(work.getJobNo());
            if (_mwiH.count(mwikey) > 0)
            {
                // 補充の場合移動中在庫を作成する
                AreaController areaCon = getAreaCtlr();
                // 移動先エリアを取得
                String moveArea = areaCon.getMovingArea();
                String moveLoc = WmsParam.DEFAULT_LOCATION_NO;

                // 入庫先の在庫をロックする
                Stock storStock = new Stock();
                storStock.setAreaNo(moveArea);
                storStock.setLocationNo(moveLoc);
                storStock.setConsignorCode(stk.getConsignorCode());
                storStock.setItemCode(stk.getItemCode());
                storStock.setLotNo(stk.getLotNo());
                Stock[] stStocks = stockCtlr.lock(storStock);

                // 入庫先に在庫があった場合積み増し、なかった場合新規入庫
                boolean initStorage = (ArrayUtil.isEmpty(stStocks));

                // 今から入庫しようとしている在庫インスタンスを作成する
                // 出庫した在庫をコピーして作成する
                Stock addStock = (Stock)stk.clone();
                addStock.setAreaNo(moveArea);
                addStock.setLocationNo(moveLoc);
                addStock.setStockQty(workQty);
                // 補充用の在庫なので、引当数は0で作成する
                addStock.setAllocationQty(0);
                addStock.setPalletId("");

                String stockID = "";
                if (initStorage)
                {
                    stockID = stockCtlr.initStorage(addStock, jobType, ui, SystemDefine.DEFAULT_REASON_TYPE);
                }
                else
                {
                    Stock destStk = stStocks[0];
                    // get move date
                    MoveWorkInfo mwi = new MoveWorkInfo();
                    mwi.setStorageDay(destStk.getStorageDay());
                    mwi.setStorageDate(destStk.getStorageDate());
                    mwi.setRetrievalDay(destStk.getRetrievalDay());

                    addStock = stockCtlr.getMoveStockDate(addStock, mwi);

                    // DFKLOOK 2009/10/30 在庫の入庫日時について
                    // 補充の場合は、移動になるため移動元と移動先の最新入庫日時となる仕様
                    // 入庫日時を更新したくない場合は、addStockの入庫日時をクリアすればOK
                    stockID =
                            stockCtlr.addStorage(destStk, addStock, jobType, true, ui, SystemDefine.DEFAULT_REASON_TYPE);
                }

                // 補充出庫完了処理を行う
                MoveWorkInfoAlterKey mwAkey = new MoveWorkInfoAlterKey();

                mwAkey.setWorkConnKey(work.getJobNo());
                mwAkey.updateStockId(stockID);
                mwAkey.updateStatusFlag(MoveWorkInfo.STATUS_FLAG_MOVE_STORAGE_WAITING);
                mwAkey.updateRetrievalUserId(ui.getUserId());
                mwAkey.updateRetrievalTerminalNo(ui.getTerminalNo());
                mwAkey.updateRetrievalWorkDate(new SysDate());
                mwAkey.updateRetrievalResultQty(workQty);
                mwAkey.updateLastUpdatePname(this.getClass().getSimpleName());
                _mwiH.modify(mwAkey);

            }
            return;

        }
        catch (ScheduleException e)
        {
            throw new InvalidDefineException();
        }
        catch (OperatorException e)
        {
            if (OperatorException.ERR_OVERFLOW.equals(e.getErrorCode()))
            {
                // <jp> 6006124=オーバーフローが発生しました。{0}
                RmiMsgLogClient.write(new TraceHandler(6006002, e), "CarryCompleteOperator");
            }
            throw new NotFoundException();
        }
        catch (NoPrimaryException e)
        {
            throw new InvalidDefineException();
        }
        catch (DataExistsException e)
        {
            // do nothing.
        }
        catch (LockTimeOutException e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * パレットIDよりパレットを取得します
     *
     * @param palletId パレットID
     * @return パレットインスタンス
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     */
    private Pallet getPallet(String palletId)
            throws ReadWriteException
    {
        try
        {
            PalletHandler plh = _plH;
            PalletSearchKey plKey = new PalletSearchKey();
            plKey.setPalletId(palletId);

            return (Pallet)plh.findPrimary(plKey);
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * 在庫IDより在庫情報を取得します
     *
     * @param stockId 在庫ID
     * @return 在庫インスタンス
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     */
    private Stock getStock(String stockId)
            throws ReadWriteException
    {
        try
        {
            StockSearchKey stkKey = new StockSearchKey();
            StockHandler stkh = _stockH;
            stkKey.setStockId(stockId);

            return (Stock)stkh.findPrimary(stkKey);
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * <jp> 入庫搬送データ削除処理を行います。<BR>
     * 搬送データに紐付くパレット、作業情報、在庫情報を削除します。<BR>
     * また、搬送先が棚の場合棚情報の棚状態も空棚に更新します。<BR>
     * 実績の作成は行いません。
     *
     *
     * 現在使用されていません。
     *
     * @param ci 搬送データ
     *
     * @throws ReadWriteException
     *         データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException
     *         更新、削除すべきデータが見つからない場合に通知されます。
     * @throws InvalidDefineException
     *         データベースの更新条件に不整合があった場合に通知されます。 </jp>
     */
    private void dropStorage(CarryInfo ci)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        // 搬送先が棚の場合、空棚に更新する
        updateShelfStatusFlag(ci.getDestStationNo(), Shelf.LOCATION_STATUS_FLAG_EMPTY);

        // 各テーブル操作クラスを生成する
        WorkInfoHandler wih = _wiH;
        StockHandler stkh = _stockH;
        PalletHandler plh = _plH;

        // 作業情報に紐付く在庫情報を削除する
        WorkInfoSearchKey wiKey = new WorkInfoSearchKey();
        wiKey.setSystemConnKey(ci.getCarryKey());

        WorkInfo[] works = (WorkInfo[])wih.find(wiKey);

        for (WorkInfo work : works)
        {
            // Stockを削除する
            StockSearchKey stkKey = new StockSearchKey();
            stkKey.setStockId(work.getStockId());
            stkh.drop(stkKey);
        }

        // パレットに対して、在庫情報が１件もない場合、パレットを削除する
        if (!isExistStock(ci.getPalletId()))
        {
            PalletSearchKey plKey = new PalletSearchKey();
            plKey.setPalletId(ci.getPalletId());
            plh.drop(plKey);
        }

        // 搬送データ、作業情報を削除する
        wiKey.clear();
        wiKey.setSystemConnKey(ci.getCarryKey());
        wih.drop(wiKey);

        CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
        CarryInfoHandler cih = _ciH;

        ciKey.setCarryKey(ci.getCarryKey());
        cih.drop(ciKey);
    }

    /**
     * ユニット出庫かどうかチェックを行います。
     * 指定された搬送情報に紐づく作業情報でパレット上在庫を全て払い出す場合をユニット出庫とみなします。
     *
     * @param ci 搬送データ
     * @return 指定された搬送情報に紐づく作業情報でパレット上在庫を全て払い出す場合はtrue、<BR>
     *          そうでなければfalse<BR>
     *          荷姿不一致でリジェクトステーションに払いだされた場合など、搬送区分が入庫のものはfalse
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 対象搬送情報が見つからない
     */
    private boolean checkUnitRetrieval(CarryInfo ci)
            throws ReadWriteException,
                NotFoundException
    {
        try
        {
            // 搬送区分が入庫の場合はfalseを返す。
            if (CarryInfo.CARRY_FLAG_STORAGE.equals(ci.getCarryFlag()))
            {
                return false;
            }

            // パレット上在庫数取得
            StockHandler stockH = new StockHandler(getConnection());
            // get Stock info
            StockSearchKey sskey = new StockSearchKey();

            sskey.setPalletId(ci.getPalletId());
            sskey.setStockQtyCollect("SUM");

            Stock retrStock = (Stock)stockH.findPrimary(sskey);

            if (null == retrStock)
            {
                throw new NotFoundException();
            }

            // 作業予定数取得
            WorkInfoHandler workH = new WorkInfoHandler(getConnection());
            // get Stock info
            WorkInfoSearchKey wskey = new WorkInfoSearchKey();

            wskey.setSystemConnKey(ci.getCarryKey());
            wskey.setPlanQtyCollect("SUM");

            WorkInfo retrWork = (WorkInfo)workH.findPrimary(wskey);

            if (null == retrWork)
            {
                throw new NotFoundException();
            }

            // 作業情報の予定数と在庫情報の在庫数が同じ場合はユニット出庫
            return (retrStock.getStockQty() == retrWork.getPlanQty());
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException();
        }
    }

    /**
     * 作業情報をキャンセルするメソッドです。
     * @param stPlan 入庫予定情報
     * @param workInfo 作業情報
     * @throws NotFoundException 対象情報が見つからない
     * @throws ReadWriteException データベースアクセスエラー
     */
    private void cancelWorkInfo(StoragePlan stPlan, WorkInfo workInfo)
            throws ReadWriteException,
                NotFoundException
    {

        WorkInfoHandler workhandler = _wiH;
        WorkInfoAlterKey aKey = new WorkInfoAlterKey();
        WorkInfoSearchKey sKey = new WorkInfoSearchKey();

        sKey.setPlanUkey(stPlan.getPlanUkey());
        sKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        sKey.setHardwareType(SystemDefine.HARDWARE_TYPE_UNSTART);

        // 分割作業の場合の場合
        if (workhandler.count(sKey) == 0)
        {
            // 更新条件
            aKey.updateSettingUnitKey("");
            aKey.updateCollectJobNo("");
            aKey.updateSystemConnKey("");
            aKey.updateStockId("");
            aKey.updateRftStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
            aKey.updateOrderSerialNo("");
            aKey.updateSkipCnt(0);
            aKey.updatePlanAreaNo(stPlan.getPlanAreaNo());
            aKey.updatePlanLocationNo(stPlan.getPlanLocationNo());
            aKey.updatePlanLotNo(stPlan.getPlanLotNo());
            aKey.updateResultAreaNo("");
            aKey.updateResultLocationNo("");
            aKey.updateResultLotNo("");
            aKey.updateResultQty(0);
            aKey.updateShortageQty(0);
            aKey.updateWorkSecond(0);
            aKey.updateStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
            aKey.updateHardwareType(SystemDefine.HARDWARE_TYPE_UNSTART);
            aKey.updateWorkDay("");
            aKey.updateUserId("");
            aKey.updateTerminalNo("");
            aKey.updateLastUpdatePname(getClass().getSimpleName());
            // 検索条件
            aKey.setJobNo(workInfo.getJobNo());
            // 更新

            workhandler.modify(aKey);
        }
        // 入庫作業が分割された場合
        else
        {
            WorkInfo baseWI;
            try
            {
                baseWI = (WorkInfo)workhandler.findPrimary(sKey);


                // 更新条件
                aKey.updateSettingUnitKey("");
                aKey.updateCollectJobNo("");
                aKey.updateSystemConnKey("");
                aKey.updateStockId("");
                aKey.updateRftStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
                aKey.updateOrderSerialNo("");
                aKey.updateSkipCnt(0);
                aKey.updatePlanAreaNo(stPlan.getPlanAreaNo());
                aKey.updatePlanLocationNo(stPlan.getPlanLocationNo());
                aKey.updatePlanQty(baseWI.getPlanQty() + workInfo.getPlanQty());
                aKey.updateResultAreaNo("");
                aKey.updateResultLocationNo("");
                aKey.updateResultLotNo("");
                aKey.updateResultQty(0);
                aKey.updateShortageQty(0);
                aKey.updateWorkSecond(0);
                aKey.updateStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
                aKey.updateHardwareType(SystemDefine.HARDWARE_TYPE_UNSTART);
                aKey.updateWorkDay("");
                aKey.updateUserId("");
                aKey.updateTerminalNo("");
                aKey.updateLastUpdatePname(getClass().getSimpleName());
                // 検索条件
                aKey.setJobNo(workInfo.getJobNo());
                // 更新
                workhandler.modify(aKey);

                // 分割作業を削除する
                sKey.setJobNo(baseWI.getJobNo());
                if (workhandler.count(sKey) > 0)
                {
                    workhandler.drop(sKey);
                }
            }
            catch (NoPrimaryException e)
            {
                throw new NotFoundException();
            }
        }
    }

    /**
     * 入庫予定情報をキャンセルするメソッドです。
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 対象情報が見つからない
     * @pparam stPlan 入庫予定情報
     */
    private void cancelPlanStorage(StoragePlan stPlan, WorkInfo workInfo)
            throws NotFoundException,
                ReadWriteException
    {
        StoragePlanHandler stHandler = new StoragePlanHandler(getConnection());
        StoragePlanAlterKey aKey = new StoragePlanAlterKey();

        WorkInfoHandler woHandler = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey sKey = new WorkInfoSearchKey();
        sKey.setPlanUkey(stPlan.getPlanUkey());

        boolean splitFlag = (woHandler.count(sKey) > 1);

        // 更新条件
        aKey.updateStatusFlag(splitFlag ? SystemDefine.STATUS_FLAG_NOWWORKING
                                       : SystemDefine.STATUS_FLAG_UNSTART);
        aKey.updateResultQty(stPlan.getResultQty() - workInfo.getResultQty());
        aKey.updateLastUpdatePname(getClass().getSimpleName());
        if (!splitFlag)
        {
            // 分割作業が無くなる場合
            aKey.updateWorkDay("");
        }
        // 検索条件
        aKey.setPlanUkey(stPlan.getPlanUkey());
        // 入庫予定情報キャンセル

        stHandler.modify(aKey);

    }

    /**
     * 作業情報をキャンセルするメソッドです。
     * @param workInfo 作業情報
     * @param rsPlan 再入庫予定情報
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 対象情報が見つからない
     */
    private void cancelWorkInfo(WorkInfo workInfo, ReStoringPlan rsPlan)
            throws NotFoundException,
                ReadWriteException
    {
        WorkInfoHandler workhandler = new WorkInfoHandler(getConnection());
        WorkInfoAlterKey aKey = new WorkInfoAlterKey();

        // 更新条件
        aKey.updateSettingUnitKey("");
        aKey.updateCollectJobNo("");
        aKey.updateSystemConnKey("");
        aKey.updateStockId("");
        aKey.updateRftStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        aKey.updateOrderSerialNo("");
        aKey.updateSkipCnt(0);
        aKey.updatePlanAreaNo(rsPlan.getPlanAreaNo());
        aKey.updatePlanLocationNo(rsPlan.getPlanLocationNo());
        aKey.updatePlanLotNo(rsPlan.getPlanLotNo());
        aKey.updateResultAreaNo("");
        aKey.updateResultLocationNo("");
        aKey.updateResultLotNo("");
        aKey.updateResultQty(0);
        aKey.updateShortageQty(0);
        aKey.updateWorkSecond(0);
        aKey.updateStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        aKey.updateHardwareType(SystemDefine.HARDWARE_TYPE_UNSTART);
        aKey.updateWorkDay("");
        aKey.updateUserId("");
        aKey.updateTerminalNo("");
        aKey.updateLastUpdatePname(getClass().getSimpleName());

        // 検索条件
        aKey.setJobNo(workInfo.getJobNo());
        // 更新
        workhandler.modify(aKey);
    }


    /**
     * WarenaviSystemControllerを返します。
     *
     * @return WarenaviSystemControllerを返します。
     * @throws ScheduleException システム定義が異常
     * @throws ReadWriteException データベースアクセスエラー
     */
    public WarenaviSystemController getWarenaviSysCtlr()
            throws ReadWriteException,
                ScheduleException
    {
        if (null == _warenaviSysCtlr)
        {
            _warenaviSysCtlr = new WarenaviSystemController(getConnection(), getCaller());
        }
        return _warenaviSysCtlr;
    }

    // ------------------------------------------------------------
    // utility methods
    // ------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     *
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: CarryCompleteOperator.java 8055 2013-05-24 10:17:10Z kishimoto $";
    }
}
