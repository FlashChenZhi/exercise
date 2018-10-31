package jp.co.daifuku.wms.replenish.operator;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.AbstractOperator;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.controller.MoveWorkInfoController;
import jp.co.daifuku.wms.base.controller.StockController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.AbstractDBHandler;
import jp.co.daifuku.wms.handler.db.BasicDatabaseFinder;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.replenish.schedule.ReplenishInParameter;
import jp.co.daifuku.wms.retrieval.allocate.ReleaseAllocateOperator;


/**
 * 補充作業のオペレータクラスです。
 * 
 * <!-- !!注意!!
 * 
 * このクラスでは、インスタンスに保持しているハンドラ・コントローラの 生成を必要時に行うようにしています。
 * したがって、このクラスのコンストラクタを呼び出した直後には
 * ハンドラ・コントローラのインスタンスは初期化されておらず、アクセッサ
 * を使用してハンドラ・コントローラを取得した段階でインスタンス化が
 * 行われるため、かならずアクセッサを通してハンドラ・コントローラを 取得・使用するようにしてください。
 * -->
 * 
 * Designer :  T.Aoike<BR>
 * Maker :  T.Aoike<BR>
 * @version $Revision: 383 $
 * @author Last commit: $Author: okamura $
 */
public class ReplenishOperator
        extends AbstractOperator
{
    // ------------------------------------------------------------
    // fields (upper case only)
    // ------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /** カラム定義 搬送状態集約キー */
    public static final FieldName CMD_STATUS_MIN = new FieldName(CarryInfo.CMD_STATUS.toString(), "CMD_STATUS_MIN");

    /** カラム定義 作業No.集約キー */
    public static final FieldName JOB_NO_MIN = new FieldName(WorkInfo.JOB_NO.toString(), "JOB_NO_MIN");

    // ------------------------------------------------------------
    // class variables (prefix '$')
    // ------------------------------------------------------------
    // private static String $classVar ;

    // ------------------------------------------------------------
    // instance variables (prefix '_')
    // ------------------------------------------------------------
    // ----------- WARNING -----------
    // DO 'NOT' USE HANDLER/CONTROLLER DIRECTLY.
    // PLEASE USE getXXX() METHOD TO GET THESE
    // HANSLER/CONTROLLER.
    // ----------- WARNING -----------
    /**
     * キャンセル処理時に使用
     * 処理対象件数
     */
    private int _searchCount = 0;

    /**
     * キャンセル処理時に使用
     * 処理実施件数
     */
    private int _resultCount = 0;


    // ------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public ReplenishOperator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    // ------------------------------------------------------------
    // public methods
    // ------------------------------------------------------------
    /**
     * 補充完了処理。
     * 
     * @param params 完了処理パラメータ<br>
     * @throws ReadWriteException データベースエラー
     * @throws ScheduleException Warenavisystemテーブルに不整合
     * @throws LockTimeOutException ロックタイムアウト
     * @throws OperatorException 処理が続行できないとき理由が通知されます
     * @throws NotFoundException 対象移動作業なし
     * @throws NoPrimaryException 対象データが複数存在した場合
     * @throws DataExistsException 在庫更新履歴登録済み
     */
    public void webComplete(ReplenishInParameter[] params)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException,
                ScheduleException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException
    {
        int rowNum = 0;
        ReplenishInParameter param = null;

        Stock[] resultDestLock = null;
        MoveWorkInfo mwinfo = null;

        try
        {
            // 対象データをロックする
            MoveWorkInfoController mWrkCtlr = new MoveWorkInfoController(getConnection(), getCaller());
            WorkInfo[] locks = mWrkCtlr.lockReplenishStart(params[0].getSettingUnitKey());

            // １件も対象データがなかった場合
            if (ArrayUtil.isEmpty(locks))
            {
                throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
            }

            StockController sCon = new StockController(getConnection(), getCaller());
            for (rowNum = 0; rowNum < params.length; rowNum++)
            {
                param = params[rowNum];
                param.getWmsUserInfo().setHardwareType(SystemDefine.HARDWARE_TYPE_LIST);

                // 作業No.にマッチする移動作業情報オブジェクトを取得
                mwinfo = getMoveWorkInfo(param.getJobNo());

                // 対象データが別の端末により変更されていないか検証する
                // (status_flag=1:出庫作業中 AND hardware_type=1:リスト）   
                //  OR 
                //（status_flag=2:出庫済入庫待ち AND hardware_type=3:ASRS） 
                if (!(MoveWorkInfo.STATUS_FLAG_MOVE_RETRIEVAL_WORKING.equals(mwinfo.getStatusFlag()) && MoveWorkInfo.HARDWARE_TYPE_LIST.equals(mwinfo.getHardwareType()))
                        && !(MoveWorkInfo.STATUS_FLAG_MOVE_STORAGE_WAITING.equals(mwinfo.getStatusFlag()) && MoveWorkInfo.HARDWARE_TYPE_ASRS.equals(mwinfo.getHardwareType())))
                {
                    throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                }

                // 移動元在庫の更新を行う（出庫処理）

                // 移動元在庫をロックする
                Stock sourceLock = new Stock();
                sourceLock.setStockId(mwinfo.getStockId());
                Stock[] resultSourceLock = sCon.lock(sourceLock);

                // 在庫の出庫可能数が補充実績数よりも小さい場合
                if (resultSourceLock[0].getStockQty() < param.getReplenishQty())
                {
                    throw new OperatorException(OperatorException.ERR_SHORTAGE_ALLOCATION_QTY);
                }

                WarenaviSystemController wsCon = new WarenaviSystemController(getConnection(), getCaller());
                // 出庫処理を行う
                // 出庫済入庫待ちの場合、moveエリアに引当数0で在庫を作成、
                // 作業中の場合、補充開始で引当数を減算しているため、ここで、引当数の減算は不要
                sCon.retrieval(resultSourceLock[0], param.getJobType(), param.getReplenishQty(), 0,
                        wsCon.getWorkDay(), false, param.getWmsUserInfo());

                // 移動先在庫の更新を行う（入庫処理）

                // 移動先在庫をロックする
                Stock destLock = new Stock();
                destLock.setAreaNo(mwinfo.getStorageAreaNo());
                destLock.setLocationNo(mwinfo.getStorageLocationNo());
                destLock.setConsignorCode(mwinfo.getConsignorCode());
                destLock.setItemCode(mwinfo.getItemCode());
                destLock.setLotNo(mwinfo.getLotNo());
                resultDestLock = sCon.lock(destLock);

                // ロックを行ったStockインスタンスに必要なデータをセットする
                destLock.setStockQty(param.getReplenishQty());
                destLock.setAllocationQty(param.getReplenishQty());

                // 補充先の在庫ID
                String stockId;
                // resultDestLockがnullまたは配列数0の場合
                if (ArrayUtil.isEmpty(resultDestLock))
                {
                    // 移動元在庫の入庫日を登録する
                    destLock.setStorageDay(resultSourceLock[0].getStorageDay());

                    // 移動元在庫の入庫日時を登録する
                    destLock.setStorageDate(resultSourceLock[0].getStorageDate());

                    // 移動元在庫の最終出庫日を登録する
                    destLock.setRetrievalDay(resultSourceLock[0].getRetrievalDay());

                    // 移動先の在庫がないということなので新規入庫の処理を行う
                    stockId = sCon.initStorage(destLock, mwinfo.getJobType(), param.getWmsUserInfo(), SystemDefine.DEFAULT_REASON_TYPE);

                }
                else
                {
                    // 移動先の在庫がある場合、積み増し入庫を行う

                    // 配列数が1意外の場合
                    if (resultDestLock.length != 1)
                    {
                        throw new NoPrimaryException();
                    }

                    // 入庫先の在庫と出庫元の在庫のどちらの日付を積み増し先の在庫にセットするかを決定し、インスタンスを取得する
                    Stock insertStock = sCon.getMoveStockDate(resultSourceLock[0], resultDestLock[0]);
                    insertStock.setStockQty(param.getReplenishQty());
                    insertStock.setAllocationQty(param.getReplenishQty());

                    // 積み増し処理を行う
                    sCon.addStorage(resultDestLock[0], insertStock, mwinfo.getJobType(), false, param.getWmsUserInfo(), SystemDefine.DEFAULT_REASON_TYPE);

                    stockId = resultDestLock[0].getStockId();
                }

                // 移動作業情報を完了状態に更新、付随する実績情報を作成
                MoveWorkInfoController mwiCon = new MoveWorkInfoController(getConnection(), getCaller());
                mwinfo.setStockId(stockId);
                mwiCon.completeReplenish(mwinfo, stockId, mwinfo.getStorageAreaNo(), mwinfo.getStorageLocationNo(),
                        param.getReplenishQty(), wsCon.getWorkDay(), 0, param.getWmsUserInfo());
            }
        }
        catch (OperatorException e)
        {
            if (param != null)
            {
                e.setErrorLineNo(param.getRowNo());
            }

            if (OperatorException.ERR_OVERFLOW.equals(e.getErrorCode()))
            {
                List<Integer> list = new ArrayList<Integer>(2);
                list.add(resultDestLock[0].getStockQty());
                list.add(mwinfo.getPlanQty());

                e.setDetail(list);
            }

            throw e;
        }
        catch (NotFoundException e)
        {
            OperatorException oe = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
            if (param != null)
            {
                oe.setErrorLineNo(param.getRowNo());
            }

            throw oe;
        }
        finally
        {
        }
    }

    /**
     * 補充キャンセルを行う。<BR>
     * 設定単位キーに含まれる作業で未作業のものをキャンセルする。<BR>
     * また自動倉庫からの引当だった場合は、搬送指示送信前のもののみ対象とする。<BR>
     * また、該当件数中何件処理を行ったかを内部の変数に保持する。<BR>
     * @param param 単位設定キーを含めたパラメータ
     * @throws OperatorException 処理が続行できないとき理由が通知されます
     * @throws ReadWriteException データベースエラー
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public void cancel(ReplenishInParameter param)
            throws OperatorException,
                ReadWriteException,
                CommonException
    {
        _searchCount = 0;
        _resultCount = 0;

        BasicDatabaseFinder finder = null;
        try
        {
            finder = getMoveWorkInfoSearchSettingKey(param.getSettingUnitKey());

            // 処理対象が0件の場合は、他端末エラーとする
            if (_searchCount == 0)
            {
                throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
            }

            int datacnt = 0;
            MoveWorkInfo[] entities;
            ReleaseAllocateOperator opeator = new ReleaseAllocateOperator(getConnection(), getCaller());
            // データを展開する
            while (finder.hasNext())
            {
                entities = (MoveWorkInfo[])finder.getEntities(datacnt, datacnt + 1);
                datacnt++;

                if (!entities[0].getStatusFlag().equals(MoveWorkInfo.STATUS_FLAG_UNSTART)
                        && !entities[0].getStatusFlag().equals(MoveWorkInfo.STATUS_FLAG_MOVE_RETRIEVAL_WORKING))
                {
                    continue;
                }
                
                // 移動出庫エリアが定義されている場合は搬送状態を取得する
                String carryKey = "";
                if (!StringUtil.isBlank(entities[0].getWorkConnKey()))
                {
                    // 搬送状態が「引当」か「開始」であるか判定
                    Entity entity = getCarryInfo(entities[0].getWorkConnKey());

                    // データを取得できなかった場合
                    if (entity == null)
                    {
                        throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                    }

                    CarryInfo info = (CarryInfo)entity;
                    carryKey = info.getCarryKey();
                    // 搬送状態が「引当」か「開始」か判断します。
                    if (!SystemDefine.CMD_STATUS_ALLOCATION.equals(info.getCmdStatus())
                            && !SystemDefine.CMD_STATUS_START.equals(info.getCmdStatus()))
                    {
                        continue;
                    }
                }

                // 在庫の引当を解除する
                if (!opeator.allocateClear(entities[0].getStockId(), entities[0].getPlanQty(), carryKey))
                {
                    throw new LockTimeOutException();
                }

                // 作業情報の引当を解除する
                opeator.allocateWorkDelete(entities[0].getJobNo(), entities[0].getWorkConnKey(), carryKey);

                // 処理件数を追加
                _resultCount++;
            }
            
            if (_resultCount == 0)
            {
                throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
            }
            
        }
        finally
        {
            closeFinder(finder);
        }
    }

    /**
     * キャンセル処理の処理実施件数を返します。
     * @return 処理実施件数
     */
    public int getResultCount()
    {
        return _resultCount;
    }

    /**
     * キャンセル処理の処理対象件数を返します。
     * @return 処理対象件数
     */
    public int getSearchCount()
    {
        return _searchCount;
    }

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------

    /**
     * 作業No.にマッチするMoveWorkInfoオブジェクトを取得します。
     * 
     * @param jobNo 取得したいMoveWorkInfoの作業No.
     * @return 作業No.に一致するMoveWorkInfoオブジェクト
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException 対象移動作業なし
     * @throws NoPrimaryException 対象データが複数存在した場合
     */
    protected MoveWorkInfo getMoveWorkInfo(String jobNo)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException
    {
        MoveWorkInfoSearchKey wKey = new MoveWorkInfoSearchKey();

        // select
        // 前項目取得の為、指定なし

        // where
        wKey.setKey(MoveWorkInfo.JOB_NO, jobNo);

        // 作業情報取得結果
        AbstractDBHandler handler = new MoveWorkInfoHandler(getConnection());
        MoveWorkInfo moveWorkInfo = (MoveWorkInfo)handler.findPrimary(wKey);

        if (moveWorkInfo == null)
        {
            throw new NotFoundException();
        }

        return moveWorkInfo;
    }

    /**
     * 単位設定キーにマッチするBasicDatabaseFinderオブジェクトを取得します。
     * 
     * @param settingUnitKey 取得したいMoveWorkInfoの単位設定キー
     * @return BasicDatabaseFinderオブジェクト
     * @throws ReadWriteException データベースエラー
     * @throws LockTimeOutException ロックタイムアウト時に通知
     */
    protected BasicDatabaseFinder getMoveWorkInfoSearchSettingKey(String settingUnitKey)
            throws ReadWriteException,
                LockTimeOutException
    {
        MoveWorkInfoSearchKey wKey = new MoveWorkInfoSearchKey();

        // select
        // 前項目取得の為、指定なし

        // where
        wKey.setKey(MoveWorkInfo.SETTING_UNIT_KEY, settingUnitKey);

        // 作業情報取得結果
        MoveWorkInfoFinder finder = new MoveWorkInfoFinder(getConnection());
        finder.open(true);

        // 検索件数を設定
        _searchCount = finder.searchForUpdate(wKey, MoveWorkInfoFinder.WAIT_SEC_DEFAULT);

        return finder;
    }

    /**
     * 入出庫作業情報接続キーを共に搬送情報を取得します。
     * 
     * @param WorkConnKey 入出庫作業情報接続キー
     * @return 搬送情報
     * @throws ReadWriteException データベースエラー
     * @throws NoPrimaryException 対象データが複数存在した場合
     */
    protected Entity getCarryInfo(String workConnKey)
            throws ReadWriteException,
                NoPrimaryException
    {
        CarryInfoSearchKey key = new CarryInfoSearchKey();

        // where
        key.setKey(WorkInfo.JOB_NO, workConnKey);

        // join
        key.setJoin(WorkInfo.SYSTEM_CONN_KEY, "", CarryInfo.CARRY_KEY, "");

        // 作業情報取得結果
        CarryInfoHandler hander = new CarryInfoHandler(getConnection());
        return hander.findPrimary(key);
    }

    /**
     * DBFinderのclose処理を呼び出します。
     * 引数で指定されたfinderがnullかどうかチェックを行ってからcloseします。
     * 
     * @param finder dbfinder
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void closeFinder(BasicDatabaseFinder finder)
            throws CommonException
    {
        if (finder != null)
        {
            finder.close();
            DEBUG.MSG(DEBUG.HANDLER, "finder close" + this.getClass().getName());
        }
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

}
